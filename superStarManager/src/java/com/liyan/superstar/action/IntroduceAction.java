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
import com.liyan.superstar.model.Introduce;
import com.liyan.superstar.model.PassMusic;
import com.liyan.superstar.model.Song;
import com.liyan.superstar.service.IntroduceService;
import com.opensymphony.xwork2.Action;
/**
 * @author Administrator
 * 新歌推荐
 */
@Controller
@Scope("prototype")
public class IntroduceAction extends PageAwareActionSupport<Introduce> {
	
	@Autowired
	private IntroduceService introduceService ; 
	
	private String ids ; 
	private Introduce introduce ; 
	private Song song ; 
	
	/**
	 * 新歌推荐初始化页面
	 * @return
	 */
	public String init(){
		return Action.SUCCESS;
	}
	
	/**
	 * 新歌推荐初始化列表
	 */
	public void introduceList(){
		try{
			Pagination<Introduce> result = this.introduceService.query(introduce,page,rows);
			List<Introduce> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Introduce intro : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("introduce_id", intro.getIntroduce_id());
				jsonObject.element("music_id", intro.getSong().getSong_id());
				jsonObject.element("music_name", intro.getMusic_name());
				jsonObject.element("singer_name", intro.getSong().getSinger_name());
				jsonObject.element("state", intro.getState());
				jsonObject.element("introduce_time", intro.getIntroduce_time());
				jsonObject.element("introduce_sort", intro.getIntroduce_sort());
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
	 * 查询新歌推荐
	 */
	public void searchIntroduce(){
		try{
			Pagination<Introduce> result = this.introduceService.search(introduce,page,rows);
			List<Introduce> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Introduce intro : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("introduce_id", intro.getIntroduce_id());
				jsonObject.element("music_id", intro.getSong().getSong_id());
				jsonObject.element("music_name", intro.getMusic_name());
				jsonObject.element("singer_name", intro.getSong().getSinger_name());
				jsonObject.element("state", intro.getState());
				jsonObject.element("introduce_time", intro.getIntroduce_time());
				jsonObject.element("introduce_sort", intro.getIntroduce_sort());
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
	 * 保存新歌推荐 
	 */
	public void saveIntroduce(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.introduceService.create(introduce);

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
	 * 编辑新歌推荐
	 */
	public void editIntroduce(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = this.introduceService.editIntroduce(introduce);
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
	 * 删除新歌推荐
	 */
	public void removeIntroduce(){
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			MsgBean msgBean = this.introduceService.removeIntroduce(list);
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
	public void musicList(){
		try{
			Pagination<Song> result = this.introduceService.queryMusicList(song,page,rows,sort,order);
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
			e.printStackTrace();
		}
	}
	public void pushIntroduce(){
		try{
			MsgBean msgBean = this.introduceService.pushIntroduce(ids);
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
	public void checkIntroductSort(){
		try{
			MsgBean msgBean = this.introduceService.checkIntroductSort(introduce);
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
	public IntroduceService getIntroduceService() {
		return introduceService;
	}

	public void setIntroduceService(IntroduceService introduceService) {
		this.introduceService = introduceService;
	}

	public Introduce getIntroduce() {
		return introduce;
	}

	public void setIntroduce(Introduce introduce) {
		this.introduce = introduce;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}
	}
