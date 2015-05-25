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
import com.liyan.common.util.DateUtil;
import com.liyan.superstar.model.MainStar;
import com.liyan.superstar.model.Singer;
import com.liyan.superstar.service.MainStarService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class MainStarAction extends PageAwareActionSupport<MainStar>{
	
	@Autowired
	private MainStarService mainStarService ; 
	private MainStar mainStar ;
	private String ids; 
	private Singer singer ; 
	
	public String init(){
		return Action.SUCCESS;
	}
	/**
	 * 主打歌星初始化列表
	 */
	public void mainStarList(){
		try{
			
			Pagination<MainStar> pageList = mainStarService.queryMainStar(mainStar, page,rows);
			List<MainStar> resultList = pageList.getResultList();
			JSONArray jsonArray = new JSONArray();
			for (MainStar mainStar : resultList) {
	
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("introduce_id", mainStar.getIntroduce_id());
				jsonObject.element("star_id", mainStar.getSinger().getStar_id());
				jsonObject.element("star_name", mainStar.getSinger_name());
				jsonObject.element("star_type", mainStar.getSinger().getStar_type());
				jsonObject.element("area", mainStar.getSinger().getArea());
				jsonObject.element("state", mainStar.getState());
				jsonObject.element("introduce_time", mainStar.getIntroduce_time());
				jsonObject.element("introduce_sort", mainStar.getIntroduce_sort());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", pageList.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 非主打歌星列表
	 */
	public void unMainStarList(){
		try{
			
			Pagination<Singer> pageList = mainStarService.unMainStarList(singer, page,rows);
			List<Singer> resultList = pageList.getResultList();
			JSONArray jsonArray = new JSONArray();
			for (Singer singer : resultList) {
	
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("star_id", singer.getStar_id());
				jsonObject.element("star_name", singer.getStar_name());
				jsonObject.element("other_name", singer.getStar_name());
				jsonObject.element("spell_first_letter_abbreviation",
						singer.getSpell_first_letter_abbreviation());
				jsonObject.element("star_type", singer.getStar_type());
				jsonObject.element("area", singer.getArea());

				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", pageList.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 设置主打歌星
	 */
	public void setMainStar(){
		try{
			
			
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = this.mainStarService.setMainStar(mainStar);
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
	 * 移除主打歌星
	 */
	public void removeMainStar(){
		try{
			
			
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = this.mainStarService.removeMainStar(ids);
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
	 * 查询主打歌星
	 */
	public void searchMainStarList(){
		try{
			
		
			Pagination<MainStar> pageList = mainStarService.searchMainStarList(mainStar, page,rows);
			List<MainStar> resultList = pageList.getResultList();
			JSONArray jsonArray = new JSONArray();
			for (MainStar mainStar : resultList) { 
	
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("introduce_id", mainStar.getIntroduce_id());
				jsonObject.element("star_id", mainStar.getSinger().getStar_id());
				jsonObject.element("star_name", mainStar.getSinger_name());
				jsonObject.element("star_type", mainStar.getSinger().getStar_type());
				jsonObject.element("area", mainStar.getSinger().getArea());
				jsonObject.element("state", mainStar.getState());
				jsonObject.element("introduce_time", mainStar.getIntroduce_time());
				jsonObject.element("introduce_sort", mainStar.getIntroduce_sort());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", pageList.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void pushMainStar(){
		try{
			
			
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = this.mainStarService.pushMainStar(ids);
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
	
	public String newStarInit(){

		return Action.SUCCESS; 
	}
	
	/**
	 * 新星列表
	 */
	public void newStarList(){
		try{
			
			Pagination<MainStar> pageList = mainStarService.queryNewStar(mainStar, page,rows);
			List<MainStar> resultList = pageList.getResultList();
			JSONArray jsonArray = new JSONArray();
			for (MainStar mainStar : resultList) {
	
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("introduce_id", mainStar.getIntroduce_id());
				jsonObject.element("star_id", mainStar.getSinger().getStar_id());
				jsonObject.element("star_name", mainStar.getSinger_name());
				jsonObject.element("star_type", mainStar.getSinger().getStar_type());
				jsonObject.element("area", mainStar.getSinger().getArea());
				jsonObject.element("state", mainStar.getState());
				jsonObject.element("introduce_time", mainStar.getIntroduce_time());
				jsonObject.element("introduce_sort", mainStar.getIntroduce_sort());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", pageList.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 非新星列表
	 */
	public void unNewStarList (){
		try{
			
			Pagination<Singer> pageList = mainStarService.unNewStarList(singer, page,rows);
			List<Singer> resultList = pageList.getResultList();
			JSONArray jsonArray = new JSONArray();
			for (Singer singer : resultList) {
	
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("star_id", singer.getStar_id());
				jsonObject.element("star_name", singer.getStar_name());
				jsonObject.element("other_name", singer.getStar_name());
				jsonObject.element("spell_first_letter_abbreviation",
						singer.getSpell_first_letter_abbreviation());
				jsonObject.element("star_type", singer.getStar_type());
				jsonObject.element("area", singer.getArea());

				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", pageList.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 *  设置新星
	 */
	public void setNewStar (){
		try{
			
			
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = this.mainStarService.setNewStar(ids);
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
	 * 删除新星
	 */
	public void removeNewStar (){
		try{
			
			
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = this.mainStarService.removeNewStar(ids);
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
	 * 发布新星
	 */
	public void pushNewStar (){
		try{
			
			
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = this.mainStarService.pushNewStar(ids);
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
	 * 查询新星
	 */
	public void searchNewStar (){
		try{
			
			Pagination<MainStar> pageList = mainStarService.searchNewStar(mainStar, page,rows);
			List<MainStar> resultList = pageList.getResultList();
			JSONArray jsonArray = new JSONArray();
			for (MainStar mainStar : resultList) {
	
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("introduce_id", mainStar.getIntroduce_id());
				jsonObject.element("star_id", mainStar.getSinger().getStar_id());
				jsonObject.element("star_name", mainStar.getSinger_name());
				jsonObject.element("star_type", mainStar.getSinger().getStar_type());
				jsonObject.element("area", mainStar.getSinger().getArea());
				jsonObject.element("state", mainStar.getState());
				jsonObject.element("introduce_time", mainStar.getIntroduce_time());
				jsonObject.element("introduce_sort", mainStar.getIntroduce_sort());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", pageList.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void checkMainStarSort(){
		try{
			MsgBean msgBean = this.mainStarService.checkMainStarSort(mainStar);
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
	public MainStarService getMainStarService() {
		return mainStarService;
	}
	public void setMainStarService(MainStarService mainStarService) {
		this.mainStarService = mainStarService;
	}
	public MainStar getMainStar() {
		return mainStar;
	}
	public void setMainStar(MainStar mainStar) {
		this.mainStar = mainStar;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public Singer getSinger() {
		return singer;
	}
	public void setSinger(Singer singer) {
		this.singer = singer;
	}
	
}
