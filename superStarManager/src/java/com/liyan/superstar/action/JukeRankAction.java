package com.liyan.superstar.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.liyan.common.action.PageAwareActionSupport;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.DateUtil;
import com.liyan.superstar.dto.JukeRankDto;
import com.liyan.superstar.model.Introduce;
import com.liyan.superstar.model.JukeRank;
import com.liyan.superstar.service.JukeRankService;
import com.opensymphony.xwork2.Action;

/**
 * @author Administrator
 * 点唱记录排行
 */
@Controller
@Scope("prototype")
public class JukeRankAction extends PageAwareActionSupport<JukeRank> {
	
	private JukeRank jukeRank ;
	
	@Autowired
	private JukeRankService jukeRankService ;

	/**
	 * 点唱排行初始化页面
	 * @return
	 */
	public String init(){
		
		return Action.SUCCESS;
	}
	/**
	 * 点唱记录查询
	 * @return
	 */
	public String jukeInit(){
		return Action.SUCCESS;
	}
	/**
	 * 点唱排行所有统计
	 */
	public void jukeRank(){
		try{
			
			Pagination<JukeRankDto> result =  this.jukeRankService.statJukeRank(jukeRank,page,rows);
			List<JukeRankDto> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(JukeRankDto juke : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("music_id", juke.getMusic_id());
				jsonObject.element("music_name", juke.getMusic_name());
				jsonObject.element("singer_name", juke.getSinger_name());
				jsonObject.element("juke_number",juke.getJuke_number());
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
	public void jukeList(){
		try{
			
			Pagination<JukeRank> result =  this.jukeRankService.query(jukeRank,page,rows);
			List<JukeRank> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(JukeRank juke : resultList){
				JSONObject jsonObject = new JSONObject();
				
				jsonObject.element("record_id", juke.getRecord_id());
				jsonObject.element("music_id", juke.getMusic_id());
				jsonObject.element("music_name", juke.getMusic_name());
				jsonObject.element("start_time", juke.getStart_time());
				jsonObject.element("end_time", juke.getEnd_time());
				jsonObject.element("play_plan", juke.getPlay_plan());
				jsonObject.element("member_no", juke.getMember_no());
				jsonObject.element("company", juke.getCompany());
				jsonObject.element("store", juke.getStore());
				jsonObject.element("room", juke.getRoom());
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
	public void searchJuke(){
		try{
			
			Pagination<JukeRank> result =  this.jukeRankService.searchJuke(jukeRank,page,rows);
			List<JukeRank> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(JukeRank juke : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("record_id", juke.getRecord_id());
				jsonObject.element("music_id", juke.getMusic_id());
				jsonObject.element("music_name", juke.getMusic_name());
				jsonObject.element("start_time", juke.getStart_time());
				jsonObject.element("end_time",juke.getEnd_time());
				jsonObject.element("play_plan", juke.getPlay_plan());
				jsonObject.element("member_no", juke.getMember_no());
				jsonObject.element("company", juke.getCompany());
				jsonObject.element("store", juke.getStore());
				jsonObject.element("room", juke.getRoom());
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
	public JukeRank getJukeRank() {
		return jukeRank;
	}

	public void setJukeRank(JukeRank jukeRank) {
		this.jukeRank = jukeRank;
	}

	public JukeRankService getJukeRankService() {
		return jukeRankService;
	}

	public void setJukeRankService(JukeRankService jukeRankService) {
		this.jukeRankService = jukeRankService;
	} 
	
	
	
}
