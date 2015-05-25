package com.liyan.superstar.action;

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
import com.liyan.superstar.model.SystemParam;
import com.liyan.superstar.service.SystemParamService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class SystemParamAction extends PageAwareActionSupport<SystemParam> {
	
	@Autowired
	private SystemParamService sysParamService ;
	
	private SystemParam sysParam ; 
	
	private String ids ; 
	
	public String init(){
		return Action.SUCCESS;
	}
	public void sysParamList(){
		try{
			Pagination<SystemParam> result = this.sysParamService.query(sysParam,page,rows,sort,order);
			List<SystemParam> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(SystemParam sysParam : resultList){
				JSONObject jsonObject = new JSONObject();
				
				jsonObject.element("param_type", sysParam.getParam_type());
				jsonObject.element("param_name", sysParam.getParam_name());
				jsonObject.element("param_value", sysParam.getParam_value());
				jsonObject.element("device_type", sysParam.getDevice_type());
				
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
	public void saveSysParam(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = sysParamService.saveSysParam(sysParam);
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
	public void removeSysParam(){
		try {
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			MsgBean msgBean = this.sysParamService.removeSysParam(list);
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
	public void searchSysParam(){
		try{
			Pagination<SystemParam> result = this.sysParamService.search(sysParam,page,rows,sort,order);
			List<SystemParam> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(SystemParam sysParam : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("param_type", sysParam.getParam_type());
				jsonObject.element("param_name", sysParam.getParam_name());
				jsonObject.element("param_value", sysParam.getParam_value());
				jsonObject.element("device_type", sysParam.getDevice_type());
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
	public void editSysParam(){
		try {
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.sysParamService.editSysParam(sysParam);

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
	public SystemParamService getSysParamService() {
		return sysParamService;
	}
	public void setSysParamService(SystemParamService sysParamService) {
		this.sysParamService = sysParamService;
	}
	public SystemParam getSysParam() {
		return sysParam;
	}
	public void setSysParam(SystemParam sysParam) {
		this.sysParam = sysParam;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
}
