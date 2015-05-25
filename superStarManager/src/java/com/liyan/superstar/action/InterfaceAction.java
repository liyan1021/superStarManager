package com.liyan.superstar.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.liyan.common.action.MsgBean;
import com.liyan.common.action.PageAwareActionSupport;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.DateUtil;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.service.InterfaceService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class InterfaceAction extends PageAwareActionSupport<Interface>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 984395982975125968L;
	@Autowired
	private InterfaceService  interfaceService ;
	private Interface itf ; 
	private String ids; 
	private File image; // 上传的文件
	private String imageFileName; // 文件名称
	private String imageContentType; // 文件类型
	private String path;
	private String theme;
	private String onTime;
	private String offTime;
	private String time;
	/**
	 * 开始界面初始化
	 * @return
	 */
	public String init(){
		return Action.SUCCESS;
	}
	
	/**
	 * 开始界面初始化列表
	 */
	public void interfaceList(){
		try{
			Pagination<Interface> result = this.interfaceService.query(itf,page,rows);
			List<Interface> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Interface itf : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("interface_id", itf.getInterface_id());
				jsonObject.element("interface_name", itf.getInterface_name());
				jsonObject.element("online_time", itf.getOnline_time());
				jsonObject.element("offline_time",itf.getOffline_time());
				jsonObject.element("state", itf.getState());
				jsonObject.element("is_activity",itf.getIs_activity());
				jsonObject.element("file_path",itf.getFile_path());
				jsonObject.element("interface_time",itf.getInterface_time());
				jsonObject.element("interface_sort",itf.getInterface_sort());
				
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
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
	 * 查询初始界面设置
	 */
	public void searchInterface(){
		try{
			Pagination<Interface> result = this.interfaceService.search(itf,page,rows);
			List<Interface> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Interface itf : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("interface_id", itf.getInterface_id());
				jsonObject.element("interface_name", itf.getInterface_name());
				jsonObject.element("online_time",itf.getOnline_time());
				jsonObject.element("offline_time",itf.getOffline_time());
				jsonObject.element("state", itf.getState());
				jsonObject.element("is_activity",itf.getIs_activity());
				jsonObject.element("file_path",itf.getFile_path());
				jsonObject.element("interface_time",itf.getInterface_time());
				jsonObject.element("interface_sort",itf.getInterface_sort());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
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
	 * 删除初始界面设置
	 */
	public void removeInterface(){
		try {
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			MsgBean msgBean = this.interfaceService.removeInterface(list);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 编辑初始界面设置
	 */
	public void editInterface(){
		try {
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = interfaceService.editInterface(itf,image,imageFileName,imageContentType);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 保存初始界面设置
	 */
	public void saveInterface(){
		try {
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.interfaceService.create(itf,image,imageFileName,imageContentType);

			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化屏保界面
	 * @return
	 */
	public String sleepInit(){
		
		return Action.SUCCESS;
	}
	/**
	 * 初始化屏保界面设置列表
	 */
	public void sleepInterfaceList(){
		try{
			Pagination<Interface> result = this.interfaceService.querySlepp(itf,page,rows);
			List<Interface> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Interface itf : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("interface_id", itf.getInterface_id());
				jsonObject.element("interface_name", itf.getInterface_name());
				jsonObject.element("online_time", itf.getOnline_time());
				jsonObject.element("offline_time",itf.getOffline_time());
				jsonObject.element("state", itf.getState());
				jsonObject.element("is_activity",itf.getIs_activity());
				jsonObject.element("file_path",itf.getFile_path());
				jsonObject.element("interface_time",itf.getInterface_time());
				jsonObject.element("interface_sort",itf.getInterface_sort());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
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
	 * 查询屏保界面设置
	 */
	public void searchSleepInterface(){
		try{
			Pagination<Interface> result = this.interfaceService.searchSlepp(itf,page,rows);
			List<Interface> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Interface itf : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("interface_id", itf.getInterface_id());
				jsonObject.element("interface_name", itf.getInterface_name());
				jsonObject.element("online_time", itf.getOnline_time());
				jsonObject.element("offline_time",itf.getOffline_time());
				jsonObject.element("state", itf.getState());
				jsonObject.element("is_activity",itf.getIs_activity());
				jsonObject.element("file_path",itf.getFile_path());
				jsonObject.element("interface_time",itf.getInterface_time());
				jsonObject.element("interface_sort",itf.getInterface_sort());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
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
	 * 删除屏保界面 设置
	 */
	public void removeSleepInterface(){
		try {
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			MsgBean msgBean = this.interfaceService.removeSleppInterface(list);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 编辑屏保界面设置
	 */
	public void editSleepInterface(){
		try {
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = interfaceService.editSleppInterface(itf,image,imageFileName,imageContentType);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 保存屏保界面设置
	 */
	public void saveSleepInterface(){
		try {
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.interfaceService.createSlepp(itf,image,imageFileName,imageContentType); 

			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 批量保存屏保界面设置
	 */
	public void saveSleepInterfaceList(){
		try {
			MsgBean msgBean = null;
			HttpServletResponse response = ServletActionContext.getResponse();
				int max = this.interfaceService.getMaxSortValue();
				onTime=itf.getOnline_time();
				offTime=itf.getOffline_time();
				time=itf.getInterface_time();
			    
				File root = new File(path);
			    File[] files = root.listFiles();
			    for(File file:files){ 
			    	max ++ ; 
			    
			    	imageFileName=file.getName();
			    	theme=imageFileName.substring(0,imageFileName.lastIndexOf("."));
			    	itf = new Interface();
			    	itf.setInterface_name(theme);
			    	itf.setInterface_time(time);
			    	itf.setInterface_sort(max+"");
			    	itf.setOnline_time(onTime);
			    	itf.setOffline_time(offTime);
			    	msgBean = this.interfaceService.createSleepList(itf,file,imageFileName); 
			    }
			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void MaxValue(){
		
	}
	
	/**
	 *  发布初始界面设置
	 */
	public void pushStartInterface(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			
			MsgBean msgBean = this.interfaceService.pushStartInterface(ids); 

			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *  发布屏保设置
	 */
	public  void pushSleepInterface(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.interfaceService.pushSleepInterface(ids); 

			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void checkInterfaceSort(){
		try{
			MsgBean msgBean = this.interfaceService.checkInterfaceSort(itf);
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
	public InterfaceService getInterfaceService() {
		return interfaceService;
	}
	public void setInterfaceService(InterfaceService interfaceService) {
		this.interfaceService = interfaceService;
	}
	public Interface getItf() {
		return itf;
	}
	public void setItf(Interface itf) {
		this.itf = itf;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
