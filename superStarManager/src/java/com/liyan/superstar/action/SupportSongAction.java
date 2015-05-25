package com.liyan.superstar.action;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.Operation;
import com.liyan.superstar.model.SupportSong;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.superstar.service.SupportSongService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class SupportSongAction extends PageAwareActionSupport<SupportSong> {

	private String ids;
	private String sign ; 
	private SupportSong support;
	private String start_time;
	private String end_time;

	@Autowired
	private SupportSongService supportService;
	/**
	 * @return
	 * 补歌清单
	 */
	public String init() {

		return Action.SUCCESS;
	}

	/**
	 * 补歌清单初始化列表
	 */
	public void supportList() {
		try {
			Pagination<SupportSong> result = this.supportService.query(support,start_time, end_time, page, rows,sort,order);
			List<SupportSong> resultList = result.getResultList();
			
			JSONArray jsonArray = new JSONArray();
			for (SupportSong song : resultList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("support_id", song.getSupport_id());
				jsonObject.element("song_name", song.getSong_name());
				jsonObject.element("song_year", song.getSong_year());
				jsonObject.element("star_name", song.getStar_name());
				jsonObject.element("song_state", song.getSong_state());
				jsonObject.element("support_time", song.getSupport_time());
				jsonObject.element("handler_person", song.getHandler_person());
				jsonObject.element("handler_time", song.getHandler_time());
				jsonObject.element("version", song.getVersions());
				jsonObject.element("language", song.getLanguages());
				
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除补歌清单
	 */
	public void removeSupport(){
		try{
			String[] idsList = ids.split(",");
			MsgBean msgBean = this.supportService.removeSupport(idsList);
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
	 * 查询补歌清单
	 */
	public void searchSupportList(){
		try {
			Pagination<SupportSong> result = this.supportService.search(support,start_time, end_time, page, rows,sort,order);
			List<SupportSong> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for (SupportSong song : resultList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("support_id", song.getSupport_id());
				jsonObject.element("song_name", song.getSong_name());
				jsonObject.element("song_year", song.getSong_year());
				jsonObject.element("star_name", song.getStar_name());
				jsonObject.element("song_state", song.getSong_state());
				jsonObject.element("support_time", song.getSupport_time());
				jsonObject.element("handler_person", song.getHandler_person());
				jsonObject.element("handler_time", song.getHandler_time());
				jsonObject.element("version", song.getVersions());
				jsonObject.element("language", song.getLanguages());
				
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();

			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更改补歌状态
	 */
	public void updateSupport(){
		try{
			String[] idsList = ids.split(",");
			MsgBean msgBean = this.supportService.updateSupport(idsList,sign);
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
	public void supportSong(){
		try{
			MsgBean msgBean = this.supportService.supportSong(support);
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
	public void exportSupport(){
		this.supportService.exportSupport(this.support,start_time,end_time);
	}
	public SupportSongService getSupportService() {
		return supportService;
	}

	public void setSupportService(SupportSongService supportService) {
		this.supportService = supportService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public SupportSong getSupport() {
		return support;
	}

	public void setSupport(SupportSong support) {
		this.support = support;
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
