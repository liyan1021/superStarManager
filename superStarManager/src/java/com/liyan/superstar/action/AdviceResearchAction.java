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
import com.liyan.superstar.model.AdviceResearch;
import com.liyan.superstar.model.ApkInfo;
import com.liyan.superstar.service.AdviceResearchService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class AdviceResearchAction extends PageAwareActionSupport<AdviceResearch> {
	
	@Autowired
	private AdviceResearchService adviceResearchService ;
	
	private AdviceResearch adviceResearch ;
	
	public String init(){
		return Action.SUCCESS;
	}
	
	public void adviceList(){
		try{
			
			Pagination<AdviceResearch> result = this.adviceResearchService.query(adviceResearch,page,rows);
			List<AdviceResearch> resultList = result.getResultList();
			
			JSONArray jsonArray = new JSONArray();
			for(AdviceResearch advice : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("advice_id",advice.getAdvice_id() );
				jsonObject.element("type_id",advice.getResearchType().getQuestion_name());
				jsonObject.element("advice_score",advice.getAdvice_score());
				jsonObject.element("advice_content",advice.getAdvice_content());
				jsonObject.element("member_id",advice.getMember_id());
				jsonObject.element("room",advice.getRoom());
				jsonObject.element("store",advice.getStore());
				jsonObject.element("advice_time",advice.getAdvice_time());
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

	public AdviceResearch getAdviceResearch() {
		return adviceResearch;
	}

	public void setAdviceResearch(AdviceResearch adviceResearch) {
		this.adviceResearch = adviceResearch;
	}
}
