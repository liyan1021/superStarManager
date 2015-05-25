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
import com.liyan.superstar.model.Members;
import com.liyan.superstar.service.MembersService;
import com.opensymphony.xwork2.Action;
/**
 * @author Administrator
 * 会员管理
 */
@Controller
@Scope("prototype")
public class MembersAction  extends PageAwareActionSupport<Members>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6013775667430473508L;
	private String ids ; 
	@Autowired
	private MembersService membersService ;
	
	private Members members;  
	
	/**
	 * 会员管理初始化页面
	 * @return
	 */
	public String init(){
		
		return Action.SUCCESS;
	}
	
	/**
	 * 会员管理初始化列表
	 */
	public void memberList(){
		try {
			Pagination<Members> result = this.membersService.memberList(members,page,rows);
			List<Members> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for (Members member : resultList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("member_name", member.getMember_name());
				jsonObject.element("member_code", member.getMember_code());
				jsonObject.element("member_tel", member.getMember_tel());
				jsonObject.element("member_id", member.getMember_id());
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
	 * 查询会员
	 */
	public void searchMember(){
		try {
			Pagination<Members> result = this.membersService.searchMember(members, page, rows);
			List<Members> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for (Members member : resultList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("member_name", member.getMember_name());
				jsonObject.element("member_code", member.getMember_code());
				jsonObject.element("member_tel", member.getMember_tel());
				jsonObject.element("member_id", member.getMember_id());
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
	 * 删除会员
	 */
	public void removeMembers(){
		try{
			String[] idsList = ids.split(",");
			MsgBean msgBean = this.membersService.removeMember(idsList);
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
	 * 保存会员
	 */
	public void saveMembers(){
		try{
			MsgBean msgBean = this.membersService.saveMembers(members);
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
	 * 编辑会员
	 */
	public void editMembers(){
		try{
			MsgBean msgBean = this.membersService.editMembers(members);
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
	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}


	public MembersService getMembersService() {
		return membersService;
	}


	public void setMembersService(MembersService membersService) {
		this.membersService = membersService;
	}

	public Members getMembers() {
		return members;
	}

	public void setMembers(Members members) {
		this.members = members;
	}
	
	
	
	
}
