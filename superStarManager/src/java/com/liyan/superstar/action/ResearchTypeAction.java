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

import com.liyan.common.action.MsgBean;
import com.liyan.common.action.PageAwareActionSupport;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonUtils;
import com.liyan.common.util.DateUtil;
import com.liyan.superstar.model.ApkInfo;
import com.liyan.superstar.model.Dictionary;
import com.liyan.superstar.model.ResearchType;
import com.liyan.superstar.service.ResearchTypeService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class ResearchTypeAction extends PageAwareActionSupport<ResearchType>{
	
	@Autowired
	private ResearchTypeService researchService ; 
	
	private ResearchType researchType ; 
	
	private String ids ; 
	
	public String init(){
		
		return Action.SUCCESS;
	}
	public void researchTypeList(){
		try{
			Pagination<ResearchType> result = this.researchService.query(researchType,page,rows);
			List<ResearchType> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(ResearchType type : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("question_id", type.getQuestion_id());
				jsonObject.element("question_name", type.getQuestion_name());
				jsonObject.element("question_type", type.getQuestion_type());
				jsonObject.element("question_score", type.getQuestion_score());
				jsonObject.element("option_a", type.getOption_a());
				jsonObject.element("option_a_score", CommonUtils.nullToBlank(type.getOption_a_score()));
				jsonObject.element("option_b", type.getOption_b());
				jsonObject.element("option_b_score", CommonUtils.nullToBlank(type.getOption_b_score()));
				jsonObject.element("option_c", type.getOption_c());
				jsonObject.element("option_c_score", CommonUtils.nullToBlank(type.getOption_c_score()));
				jsonObject.element("option_d", type.getOption_d());
				jsonObject.element("option_d_score", CommonUtils.nullToBlank(type.getOption_d_score()));
				jsonObject.element("option_e", type.getOption_e());
				jsonObject.element("option_e_score", CommonUtils.nullToBlank(type.getOption_e_score()));
				jsonObject.element("option_f", type.getOption_f());
				jsonObject.element("option_f_score", CommonUtils.nullToBlank(type.getOption_f_score()));
				jsonObject.element("create_time",type.getCreate_time());
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
	public void saveResearchType(){
		try{
			MsgBean msgBean = this.researchService.saveResearchType(researchType);
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
	public void editResearchType(){
		try{
			MsgBean msgBean = this.researchService.editResearchType(researchType);
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
	public void removeResearchType(){
		try{
			String[] idsList = ids.split(",");
			MsgBean msgBean = this.researchService.removeResearchType(idsList);
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
	public ResearchTypeService getResearchService() {
		return researchService;
	}

	public void setResearchService(ResearchTypeService researchService) {
		this.researchService = researchService;
	}
	public ResearchType getResearchType() {
		return researchType;
	}
	public void setResearchType(ResearchType researchType) {
		this.researchType = researchType;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
