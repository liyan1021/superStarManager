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
import com.liyan.superstar.model.MarketingActivity;
import com.liyan.superstar.service.MarketingActivityService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class MarketingActivityAction extends PageAwareActionSupport<MarketingActivity>{
	
	@Autowired
	private MarketingActivityService marketingActivityService;
	
	private String ids ; 
	private MarketingActivity marketingActivity ;
	
	private List<File> image;
    private List<String> imageFileName;
    private List<String> imageContentType;
	
	public String init(){
		return Action.SUCCESS;
	}
	
	public void activityList(){
		try{
			Pagination<MarketingActivity> result = this.marketingActivityService.query(marketingActivity,page,rows,sort,order);
			List<MarketingActivity> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(MarketingActivity marketingActivity : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("activity_id", marketingActivity.getActivity_id());
				jsonObject.element("activity_name", marketingActivity.getActivity_name());
				jsonObject.element("activity_introduce",marketingActivity.getActivity_introduce() );
				jsonObject.element("activity_path_1",marketingActivity.getActivity_path_1());
				jsonObject.element("activity_path_2",marketingActivity.getActivity_path_2());
				jsonObject.element("activity_sort",marketingActivity.getActivity_sort());
				jsonObject.element("activity_time",marketingActivity.getActivity_time());
				jsonObject.element("state",marketingActivity.getState());
				
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
	public void removeActivity(){
		try {
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			MsgBean msgBean = this.marketingActivityService.removeMarketingActivity(list);
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
	public void saveActivity(){
		try {
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.marketingActivityService.create(marketingActivity,image,imageFileName,imageContentType);

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
	public void searchActivity(){
		try{
			Pagination<MarketingActivity> result = this.marketingActivityService.search(marketingActivity,page,rows,sort,order);
			List<MarketingActivity> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(MarketingActivity marketingActivity : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("activity_id", marketingActivity.getActivity_id());
				jsonObject.element("activity_name", marketingActivity.getActivity_name());
				jsonObject.element("activity_introduce",marketingActivity.getActivity_introduce() );
				jsonObject.element("activity_path_1",marketingActivity.getActivity_path_1());
				jsonObject.element("activity_path_2",marketingActivity.getActivity_path_2());
				jsonObject.element("activity_sort",marketingActivity.getActivity_sort());
				jsonObject.element("activity_time",marketingActivity.getActivity_time());
				jsonObject.element("state",marketingActivity.getState());
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
	public void editActivity(){
		try {
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = marketingActivityService.editMarketingActivity(marketingActivity,image,imageFileName,imageContentType);
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
	public void pushActivity(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			
			MsgBean msgBean = this.marketingActivityService.pushMarketingActivity(ids); 

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
	public void checkActivitySort(){
		try {
			
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = this.marketingActivityService.checkActivitySort(this.marketingActivity);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(msgBean.isSign());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public MarketingActivityService getMarketingActivityService() {
		return marketingActivityService;
	}

	public void setMarketingActivityService(
			MarketingActivityService marketingActivityService) {
		this.marketingActivityService = marketingActivityService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public MarketingActivity getMarketingActivity() {
		return marketingActivity;
	}

	public void setMarketingActivity(MarketingActivity marketingActivity) {
		this.marketingActivity = marketingActivity;
	}

	public List<File> getImage() {
		return image;
	}

	public void setImage(List<File> image) {
		this.image = image;
	}

	public List<String> getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(List<String> imageFileName) {
		this.imageFileName = imageFileName;
	}

	public List<String> getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(List<String> imageContentType) {
		this.imageContentType = imageContentType;
	}
	
	
}
