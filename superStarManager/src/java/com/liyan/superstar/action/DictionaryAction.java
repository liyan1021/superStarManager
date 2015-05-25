package com.liyan.superstar.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.liyan.superstar.dao.DictionaryDao;
import com.liyan.superstar.model.Dictionary;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.DictionaryService;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.common.AppContext;
import com.liyan.common.action.MsgBean;
import com.liyan.common.action.PageAwareActionSupport;
import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils; 
import com.opensymphony.xwork2.Action;

/**
 * 字典管理
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class DictionaryAction  extends PageAwareActionSupport<Dictionary>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6387994909520095959L;
	@Autowired
	private DictionaryDao dictDao ;
	@Autowired
	private OperationLogService optLogService ;
	@Autowired
	private DictionaryService dictService;
	private Dictionary dict ;
	private String ids; 
	private String type ; 
	private File image; // 上传的文件
	private String imageFileName; // 文件名称
	private String imageContentType; // 文件类型
	private String order ;
	private String sort; 
	/**
	 * 初始化页面
	 * @return
	 */
	public String init(){
		return Action.SUCCESS;
	}
	
	/**
	 * 初始化LIST
	 */
	public void dictList(){
		try{
			Pagination<Dictionary> result = getDictDao().query(getDict(),page,rows,sort,order);
			List<Dictionary> resultList = result.getResultList();
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", resultList);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 *  保存字典
	 */
	public void saveDict(){
		try{
			dict.setId(UUID.randomUUID().toString());
			dict.setDict_code(UUID.randomUUID().toString());
			dict.setImport_time(CommonUtils.currentDateTimeString());
			dict.setIs_activity(CommonFiled.DEL_STATE_N);
			if(dict.getDict_type_code().equals("theme")){
				dict.setDict_type("主题");
			}else if(dict.getDict_type_code().equals("song_type")){
				dict.setDict_type("曲种");
			}
			if (image != null) {
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = dict.getDict_code() +imageFileName ;
	        	//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\dict";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
			
				dict.setFile_path("upload/dict/"+imageFileName); //存放目录
            
			}
			getDictDao().create(dict);
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("message", "保存成功");
			
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "字典管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_ADD ; 
			
			getOptLogService().setLog(currentUser,function,type);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 删除字典
	 */
	public void removeDict(){
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String dict_id : list){
				Dictionary find = this.dictDao.find(dict_id);
				find.setIs_activity(CommonFiled.DEL_STATE_Y);
				this.dictDao.modify(find);
			}
//			getDictDao().batchRemove(list);
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("message", "删除成功");
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "字典管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_DEL; 
			getOptLogService().setLog(currentUser,function,type);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 查询字典
	 */
	public void searchDictList(){
		try{
			Pagination<Dictionary> result = this.dictDao.query(getDict(),page,rows,sort,order);
			List<Dictionary> resultList = result.getResultList();
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", resultList);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
//			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "字典管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_SEARCH ; 
			getOptLogService().setLog(currentUser,function,type);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 编辑字典
	 */
	public void editDict(){
		try{
			Dictionary findDict = getDictDao().find(dict.getId());
//			findDict.setDict_type(dict.getDict_type()); 
			findDict.setDict_sort(dict.getDict_sort());
			findDict.setDict_value(dict.getDict_value());
			if (image != null) {
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = dict.getDict_code() +imageFileName ;
	        	//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\dict";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
			
				findDict.setFile_path("upload/dict/"+imageFileName); //存放目录
            
			}
			getDictDao().modify(findDict);
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("message", "编辑成功");
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "字典管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 
			getOptLogService().setLog(currentUser,function,type);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 通过类别获取字典
	 */
	@SuppressWarnings("unchecked")
	public void getDictList(){
		
		try {
			Map<String, Map<String, String>> dictMap = (Map<String, Map<String, String>>) AppContext.getInstance().get("key.dict"); //取出所有字典表
			Map<String, String> hashMap = dictMap.get(type);
			Set<Entry<String, String>> entrySet = hashMap.entrySet();
			Iterator<Entry<String, String>> it = entrySet.iterator();
			JSONArray jsona = new JSONArray();
			JSONObject json1 = new JSONObject();
			json1.accumulate("dict_code","");
			json1.accumulate("dict_value", "请选择");
			jsona.add(json1);
			while(it.hasNext()){
				JSONObject json = new JSONObject();
				Entry<String, String> next = it.next();
				json.accumulate("dict_code", next.getKey());
				json.accumulate("dict_value", next.getValue());
				
				jsona.add(json);
			}
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jsona.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void checkDictSort(){
		try{
			MsgBean msgBean = this.dictService.checkDictSort(dict);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(msgBean.isSign());
			out.flush();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void checkDictValue(){
		try{
			MsgBean msgBean = this.dictService.checkDictValue(dict);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(msgBean.isSign());
			out.flush();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 通过类别获取字典
	 */
	@SuppressWarnings("unchecked")
	public void getDictListS(){
		
		try {
			Map<String, Map<String, String>> dictMap = (Map<String, Map<String, String>>) AppContext.getInstance().get("key.dict"); //取出所有字典表
			Map<String, String> hashMap = dictMap.get(type);
			Set<Entry<String, String>> entrySet = hashMap.entrySet();
			Iterator<Entry<String, String>> it = entrySet.iterator();
			JSONArray jsona = new JSONArray();
			JSONObject json1 = new JSONObject();
			json1.accumulate("dict_code","");
			json1.accumulate("dict_value", "全部");
			jsona.add(json1);
			while(it.hasNext()){
				JSONObject json = new JSONObject();
				Entry<String, String> next = it.next();
				json.accumulate("dict_code", next.getKey());
				json.accumulate("dict_value", next.getValue());
				
				jsona.add(json);
			}
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jsona.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Dictionary getDict() {
		return dict;
	}

	public void setDict(Dictionary dict) {
		this.dict = dict;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DictionaryDao getDictDao() {
		return dictDao;
	}

	public void setDictDao(DictionaryDao dictDao) {
		this.dictDao = dictDao;
	}

	public OperationLogService getOptLogService() {
		return optLogService;
	}

	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public DictionaryService getDictService() {
		return dictService;
	}

	public void setDictService(DictionaryService dictService) {
		this.dictService = dictService;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}
