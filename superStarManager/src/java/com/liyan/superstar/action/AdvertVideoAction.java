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
import com.liyan.common.util.DateUtil;
import com.liyan.superstar.model.Advert;
import com.liyan.superstar.model.AdvertVideo;
import com.liyan.superstar.service.AdvertVideoService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class AdvertVideoAction extends PageAwareActionSupport<AdvertVideo>{
	@Autowired
	private AdvertVideoService advertVideoService ;
	private AdvertVideo advertVideo ; 
	private String ids ; 
	private File video ; 
	private String videoFileName ;
	private String videoContentType ; 
	public String init(){
		return Action.SUCCESS;
	}
	public void advertVideoList(){
		try{
			
			Pagination<AdvertVideo> result = this.advertVideoService.query(advertVideo,page,rows);
			List<AdvertVideo> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(AdvertVideo video : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("advert_id", video.getAdvert_id());
				jsonObject.element("advert_theme", video.getAdvert_theme());
				jsonObject.element("advert_content", video.getAdvert_content());
				jsonObject.element("online_time",video.getOnline_time());
				jsonObject.element("offline_time",video.getOffline_time());
				jsonObject.element("is_activity",video.getIs_activity());
				jsonObject.element("state",video.getState());
				jsonObject.element("file_path",video.getFile_path());
				jsonObject.element("advert_sort",video.getAdvert_sort());
				
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
//			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
			
		}catch(Exception e){
			
		}
	}
	public void saveVideo(){
		try{
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.advertVideoService.create(advertVideo,video,videoFileName);

			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			
		}
	}
	public void editVideo(){
		try{
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = advertVideoService.editVideo(advertVideo,video,videoFileName);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			
		}
	}
	public void removeVideo(){
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			MsgBean msgBean = this.advertVideoService.removeVideo(list);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			
		}
	}
	public void searchVideo(){
		try{
			
			Pagination<AdvertVideo> result = this.advertVideoService.search(advertVideo,page,rows);
			List<AdvertVideo> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(AdvertVideo video : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("advert_id", video.getAdvert_id());
				jsonObject.element("advert_theme", video.getAdvert_theme());
				jsonObject.element("advert_content", video.getAdvert_content());
				jsonObject.element("online_time",video.getOnline_time());
				jsonObject.element("offline_time",video.getOffline_time());
				jsonObject.element("is_activity",video.getIs_activity());
				jsonObject.element("state",video.getState());
				jsonObject.element("file_path",video.getFile_path());
				jsonObject.element("advert_sort",video.getAdvert_sort());
				
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
//			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void pushVideo(){
		try{
			MsgBean msgBean = this.advertVideoService.pushVideo(ids);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			
		}
	}
	public void checkAdvertVideoSort(){
		try{
			MsgBean msgBean = this.advertVideoService.checkAdvertSort(advertVideo);
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
	public AdvertVideoService getAdvertVideoService() {
		return advertVideoService;
	}

	public void setAdvertVideoService(AdvertVideoService advertVideoService) {
		this.advertVideoService = advertVideoService;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public File getVideo() {
		return video;
	}
	public void setVideo(File video) {
		this.video = video;
	}
	public String getVideoFileName() {
		return videoFileName;
	}
	public void setVideoFileName(String videoFileName) {
		this.videoFileName = videoFileName;
	}
	public AdvertVideo getAdvertVideo() {
		return advertVideo;
	}
	public void setAdvertVideo(AdvertVideo advertVideo) {
		this.advertVideo = advertVideo;
	}
	public String getVideoContentType() {
		return videoContentType;
	}
	public void setVideoContentType(String videoContentType) {
		this.videoContentType = videoContentType;
	} 
	
}
