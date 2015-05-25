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
import com.liyan.superstar.model.MusicRanking;
import com.liyan.superstar.model.NewMusicRanking;
import com.liyan.superstar.service.NewMusicRankingService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class NewMusicRankingAction extends PageAwareActionSupport<NewMusicRanking>{
	
	private NewMusicRanking musicRanking ;
	@Autowired
	private NewMusicRankingService newMusicRankingService ;
	private int song_number ; 
	public NewMusicRanking getMusicRanking() {
		return musicRanking;
	}

	public void setMusicRanking(NewMusicRanking musicRanking) {
		this.musicRanking = musicRanking;
	}
	
	public String init(){
		
		return Action.SUCCESS;
	}
	public void musicRankingList(){
		try{
			
			Pagination<NewMusicRanking> result =  this.newMusicRankingService.query(musicRanking,page,rows,sort,order);
			List<NewMusicRanking> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(NewMusicRanking musicRanking : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("music_id", musicRanking.getMusic_id());
				jsonObject.element("music_name", musicRanking.getMusic_name());
				jsonObject.element("singer_name", musicRanking.getSinger_name());
				jsonObject.element("music_song_number",musicRanking.getMusic_song_number());
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
	public void searchMusicRank(){
		try{
			
			Pagination<NewMusicRanking> result =  this.newMusicRankingService.searchMusicRank(musicRanking,page,rows,sort,order);
			List<NewMusicRanking> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(NewMusicRanking musicRanking : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("music_id", musicRanking.getMusic_id());
				jsonObject.element("music_name", musicRanking.getMusic_name());
				jsonObject.element("singer_name", musicRanking.getSinger_name());
				jsonObject.element("music_song_number",musicRanking.getMusic_song_number());
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
	public void editMusicRank(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.newMusicRankingService.editMusicRank(musicRanking,song_number);

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

	public int getSong_number() {
		return song_number;
	}

	public void setSong_number(int song_number) {
		this.song_number = song_number;
	}
}
