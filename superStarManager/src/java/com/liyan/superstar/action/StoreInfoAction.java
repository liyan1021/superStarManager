package com.liyan.superstar.action;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.liyan.common.AppContext;
import com.liyan.common.action.MsgBean;
import com.liyan.common.action.PageAwareActionSupport;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.common.util.MD5;
import com.liyan.superstar.dao.StoreInfoDao;
import com.liyan.superstar.model.Dictionary;
import com.liyan.superstar.model.StoreInfo;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.superstar.service.StoreInfoService;
import com.opensymphony.xwork2.Action;

public class StoreInfoAction extends PageAwareActionSupport<StoreInfoAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StoreInfo storeInfo;
	private String ids;
	
	@Autowired
	private StoreInfoService storeInfoService;
	@Autowired
	private OperationLogService optLogService ;
	
	/**
	 * 初始化页面
	 * @return
	 */
	public String init(){
		return Action.SUCCESS;
	}
	public String goStoreInfoList(){
		return Action.SUCCESS;
	}
	/**
	 *  分页加载所有门店信息列表
	 */
	public void StoreInfoList(){
		try{
			Pagination<StoreInfo> result = storeInfoService.query(storeInfo,page,rows,sort,order);
			List<StoreInfo> resultList =  result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(StoreInfo storeInfo : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("store_id", storeInfo.getStore_id());
				jsonObject.element("store_code",storeInfo.getStore_code());
				jsonObject.element("store_name", storeInfo.getStore_name());
				jsonObject.element("store_address",storeInfo.getStore_address() );
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
	 *  保存用户 
	 */
	public void saveStoreInfo(){
		try{
			HttpServletResponse response=ServletActionContext.getResponse();
			MsgBean msgBean = this.storeInfoService.createStoreInfo(storeInfo);
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("message", "保存成功");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 删除门店信息
	 */
	public void removeStoreInfo(){
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			

			MsgBean msgBean =  this.storeInfoService.removeStoreInfo(list) ;
//			storeInfoDao.batchRemove(list);
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("message", "删除成功");
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 查询门店
	 */
	public void searchStoreInfoList(){
		try{
			Pagination<StoreInfo> result = this.storeInfoService.query(storeInfo,page,rows,sort,order);
			List<StoreInfo> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(StoreInfo storeInfo : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("store_id", storeInfo.getStore_id());
				jsonObject.element("store_code",storeInfo.getStore_code());
				jsonObject.element("store_name", storeInfo.getStore_name());
				jsonObject.element("store_address",storeInfo.getStore_address() );
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
	 * 编辑门店信息
	 */
	public void editStoreInfo(){
		try{
			MsgBean msgBean = this.storeInfoService.editStoreInfo(storeInfo);

			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("message", "编辑成功");
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void checkStoreInfoCode(){
		try{
			MsgBean msgBean = storeInfoService.checkStoreInfoCode(storeInfo);
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
	public void getStoreInfoList()
	{
		try {
			@SuppressWarnings("unchecked")
			Map<String,String> storeInfoMap = (Map<String,  String>) AppContext.getInstance().get("key.storeinfo"); 
			Set<Entry<String, String>> entrySet = storeInfoMap.entrySet();
			Iterator<Entry<String, String>> it = entrySet.iterator();
			JSONArray jsona = new JSONArray();
			JSONObject json1 = new JSONObject();
			json1.accumulate("store_code","");
			json1.accumulate("store_name", "请选择");
			jsona.add(json1);
			while(it.hasNext()){
				JSONObject json = new JSONObject();
				Entry<String, String> next = it.next();
				json.accumulate("store_code", next.getKey());
				json.accumulate("store_name", next.getValue());
				
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
	public OperationLogService getOptLogService() {
		return optLogService;
	}
	public StoreInfo getStoreInfo() {
		return storeInfo;
	}
	public void setStoreInfo(StoreInfo storeInfo) {
		this.storeInfo = storeInfo;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
