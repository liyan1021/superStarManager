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
import com.liyan.common.util.DateUtil;
import com.liyan.superstar.model.PassMusic;
import com.liyan.superstar.model.Singer;
import com.liyan.superstar.model.Song;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.PassMusicService;
import com.liyan.superstar.service.SongService;
import com.opensymphony.xwork2.Action;

/**
 * @author Administrator
 * 过路歌曲设置
 */
@Controller
@Scope("prototype")
public class PassMusicAction extends PageAwareActionSupport<PassMusic>{
	
	@Autowired
	private PassMusicService passMusicService ; 
	@Autowired
	private SongService songService; 
	private String  ids ; 
	private PassMusic passMusic ; 
	private Song song ;
	private String sort ; 
	private String order ; 
	/**
	 * 初始化过路歌曲设置界面
	 * @return
	 */
	public String init(){
		
		return Action.SUCCESS;
	}
	/**
	 * 初始化过路歌曲设置列表
	 */
	public void passMusicList(){
		try{
			
			Pagination<PassMusic> result = passMusicService.query(passMusic,page,rows);
			List<PassMusic> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(PassMusic music : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("pass_id", music.getPass_id());
				jsonObject.element("song_name", music.getSong_name());
				jsonObject.element("song_id", music.getSong_id());
				jsonObject.element("singer_name",music.getSinger_name());
				jsonObject.element("singer_id",music.getSinger_id());
				jsonObject.element("pass_sort", music.getPass_sort());
				jsonObject.element("state", music.getState());
				jsonObject.element("create_time", DateUtil.dateTimeToString(music.getCreate_time()));
				jsonObject.element("is_activity", music.getIs_activity());
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
	/**
	 * 查询过路歌曲设置
	 */
	public void searchPassMusic(){
		try{
			Pagination<PassMusic> result = passMusicService.search(passMusic,page,rows);
			List<PassMusic> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(PassMusic music : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("pass_id", music.getPass_id());
				jsonObject.element("song_name", music.getSong_name());
				jsonObject.element("song_id", music.getSong_id());
				jsonObject.element("singer_name",music.getSinger_name());
				jsonObject.element("singer_id",music.getSinger_id());
				jsonObject.element("pass_sort", music.getPass_sort());
				jsonObject.element("state", music.getState());
				jsonObject.element("create_time", DateUtil.dateTimeToString(music.getCreate_time()));
				jsonObject.element("is_activity", music.getIs_activity());
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
	
	/**
	 * 保存过路歌曲设置
	 */
	public void savePassMusic(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.passMusicService.create(this.passMusic);

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
	 * 编辑过路歌曲
	 */
	public void editPassMusic(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = this.passMusicService.editPassMusic(passMusic);
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
	 * 
	 * 删除过路歌曲
	 */
	public void removePassMusic(){
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			MsgBean msgBean = this.passMusicService.removePassMusic(list);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	/**
	 * 获取非过路歌曲列表
	 */
	public void unPassMusicList(){
		try{
			Pagination<Song> result = this.songService.unPassMusicList(song, page, rows);
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
	/**
	 * 获取歌曲列表
	 */
	public void musicList(){
		try{
			Pagination<Song> result = this.songService.query(song, page, rows, sort, order);
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
	public void pushPassMusic(){
		try{
			MsgBean msgBean = this.passMusicService.pushPassMusic(ids);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
		}catch(Exception e){
			
		}
	}
	public void checkPassSongSort(){
		try{
			MsgBean msgBean = this.passMusicService.checkPassSongSort(passMusic);
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
	public PassMusicService getPassMusicService() {
		return passMusicService;
	}
	public void setPassMusicService(PassMusicService passMusicService) {
		this.passMusicService = passMusicService;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public PassMusic getPassMusic() {
		return passMusic;
	}
	public void setPassMusic(PassMusic passMusic) {
		this.passMusic = passMusic;
	}
	public SongService getSongService() {
		return songService;
	}
	public void setSongService(SongService songService) {
		this.songService = songService;
	}
	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	
	
}
