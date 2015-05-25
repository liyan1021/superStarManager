package com.liyan.superstar.action;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.liyan.common.AppContext;
import com.liyan.common.action.MsgBean;
import com.liyan.common.action.PageAwareActionSupport;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.DateUtil;
import com.liyan.superstar.model.DynamicTitle;
import com.liyan.superstar.service.DynamicTitleService;
import com.opensymphony.xwork2.Action;
/**
 * @author Administrator
 *走马灯字幕管理
 */
@Controller
@Scope("prototype")
public class DynamicTitleAction extends PageAwareActionSupport<DynamicTitle> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7952864465904999816L;
	@Autowired
	private DynamicTitleService dynamicTitleService ; 
	private String ids ; 
	private DynamicTitle dynamicTitle ; 
	private String start_time ;
	private String end_time ;
	
	/**
	 * 初始化页面
	 * @return
	 */
	public String init(){
		return Action.SUCCESS;
	}
	
	
	/**
	 * 初始化列表
	 */
	public void dynamicTitleList(){
		try{
			Pagination<DynamicTitle> result = dynamicTitleService.query(dynamicTitle,getStart_time(),getEnd_time(),page,rows);
			List<DynamicTitle> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(DynamicTitle title : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("title_id", title.getTitle_id());
				jsonObject.element("title_time_length", title.getTitle_time_length());
				jsonObject.element("title_number",title.getTitle_number());
				jsonObject.element("title_date_time", title.getTitle_date_time());
				jsonObject.element("title_content", title.getTitle_content());
				jsonObject.element("state", title.getState());
				jsonObject.element("room_id", title.getRoom_id());
				jsonObject.element("room_num", title.getRoom_num());
				
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
	 * 保存字幕
	 */
	public void saveTitle(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();
			
			MsgBean msgBean = this.dynamicTitleService.create(dynamicTitle);

			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

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
	 * 编辑字幕
	 */
	public void editTitle(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = dynamicTitleService.editTitle(dynamicTitle);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
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
	 * 删除字幕
	 */
	public void removeTitle(){
		try{
			String[] idsList = ids.split(",");
			MsgBean msgBean = this.dynamicTitleService.removeTitle(idsList);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
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
	 * 查询字幕
	 */
	public void searchTitle(){
		try{
			Pagination<DynamicTitle> result = dynamicTitleService.search(dynamicTitle,getStart_time(),getEnd_time(),page,rows);
			List<DynamicTitle> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(DynamicTitle title : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("title_id", title.getTitle_id());
				jsonObject.element("title_time_length", title.getTitle_time_length());
				jsonObject.element("title_number",title.getTitle_number());
				jsonObject.element("title_date_time", title.getTitle_date_time());
				jsonObject.element("title_content", title.getTitle_content());
				jsonObject.element("state", title.getState());
				jsonObject.element("room_id", title.getRoom_id());
				jsonObject.element("room_num", title.getRoom_num());
	
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
	 * 发布走马灯设置
	 */
	public void pushTitle(){
		try{
			MsgBean msgBean = this.dynamicTitleService.pushDynamicTitle(ids);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
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

	public void getRoomList()
	{

		try {
			@SuppressWarnings("unchecked")
			Map<String, String> roomMap = (Map<String,  String>) AppContext.getInstance().get("key.room");
			Set<Entry<String, String>> entrySet = roomMap.entrySet();
			Iterator<Entry<String, String>> it = entrySet.iterator();
			JSONArray jsona = new JSONArray();
			JSONObject json1 = new JSONObject();
			json1.accumulate("room_id","");
			json1.accumulate("room_num", "全部");
			jsona.add(json1);
			while(it.hasNext()){
				JSONObject json = new JSONObject();
				Entry<String, String> next = it.next();
				json.accumulate("room_id", next.getKey());
				json.accumulate("room_num", next.getValue());
				
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
	
	public DynamicTitleService getDynamicTitleService() {
		return dynamicTitleService;
	}

	public void setDynamicTitleService(DynamicTitleService dynamicTitleService) {
		this.dynamicTitleService = dynamicTitleService;
	}

	public DynamicTitle getDynamicTitle() {
		return dynamicTitle;
	}

	public void setDynamicTitle(DynamicTitle dynamicTitle) {
		this.dynamicTitle = dynamicTitle;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}


	public String getStart_time() {
		return start_time;
	}


	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}


	public String getEnd_time() {
		return end_time;
	}


	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
}
