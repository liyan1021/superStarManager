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
import com.liyan.superstar.model.AdvertVideo;
import com.liyan.superstar.model.AdvertVideo1;
import com.liyan.superstar.model.Song;
import com.liyan.superstar.service.AdvertVedioService1;
import com.opensymphony.xwork2.Action;
@Controller
@Scope("prototype")
public class advertVideoAction1 extends PageAwareActionSupport<AdvertVideo1> {
	@Autowired
	private AdvertVedioService1 advertVideoService;
	private AdvertVideo1 advertVideo;
	private String ids ;
	private Song song ;
    //获取宣传片管理列表
	public String init(){
		return Action.SUCCESS;
	}
	public void advertVideoList(){
		try{
			Pagination<AdvertVideo1> result = this.advertVideoService.query(advertVideo,page,rows);
			List<AdvertVideo1> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(AdvertVideo1 video : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("advert_id", video.getAdvert_id());
				jsonObject.element("advert_theme", video.getAdvert_theme());
				jsonObject.element("advert_content", video.getAdvert_content());
				jsonObject.element("music_id", video.getMusic_id());
				jsonObject.element("music_name", video.getMusic_name());
				jsonObject.element("online_time",video.getOnline_time());
				jsonObject.element("offline_time",video.getOffline_time());
				jsonObject.element("is_activity",video.getIs_activity());
				jsonObject.element("state",video.getState());
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
	
	public void saveAdvertVideo(){
		try{
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.advertVideoService.create(this.advertVideo);

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
	public void editAdvertVideo(){
		try{
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = advertVideoService.editVideo(advertVideo);
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
	public void removeAdvertVideo(){
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			MsgBean msgBean = this.advertVideoService.removeAdvertVideo(list);
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
	public void searchAdvertVideo(){
		try{
			
			Pagination<AdvertVideo1> result = this.advertVideoService.search(advertVideo,page,rows);
			List<AdvertVideo1> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(AdvertVideo1 video : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("advert_id", video.getAdvert_id());
				jsonObject.element("advert_theme", video.getAdvert_theme());
				jsonObject.element("advert_content", video.getAdvert_content());
				jsonObject.element("online_time",video.getOnline_time());
				jsonObject.element("offline_time",video.getOffline_time());
				jsonObject.element("music_name", video.getMusic_name());
				jsonObject.element("is_activity",video.getIs_activity());
				jsonObject.element("state",video.getState());
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
	public void pushAdvertVideo(){
		try{
			MsgBean msgBean = this.advertVideoService.pushAdvertVideo(ids);
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
	
	/**
	 * 获取非过路歌曲列表
	 */
	public void unAdvertVideoList(){
		try{
			Pagination<Song> result = this.advertVideoService.unAdvertVideoList(song, page, rows);
			List<Song> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Song music : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("song_id", music.getSong_id());
				jsonObject.element("song_name", music.getSong_name());
				jsonObject.element("singer_id",music.getSinger_id());
				jsonObject.element("singer_name", music.getSinger_name());
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
	public AdvertVedioService1 getAdvertVideoService() {
		return advertVideoService;
	}
	public void setAdvertVideoService(AdvertVedioService1 advertVideoService) {
		this.advertVideoService = advertVideoService;
	}
	public AdvertVideo1 getAdvertVideo() {
		return advertVideo;
	}
	public void setAdvertVideo(AdvertVideo1 advertVideo) {
		this.advertVideo = advertVideo;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public Song getSong1() {
		return song;
	}
	public void setSong1(Song song) {
		this.song = song;
	}
	
}
