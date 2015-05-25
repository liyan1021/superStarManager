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
import com.liyan.superstar.dto.JukeRankDto;
import com.liyan.superstar.model.MusicRanking;
import com.liyan.superstar.service.MusicRankingService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class MusicRankingAction extends PageAwareActionSupport<MusicRanking>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8010303938313729065L;
	private MusicRanking musicRanking ; 
	private int song_number ; 
	@Autowired
	private MusicRankingService musicRankingService ;
	
	public String init(){
		return Action.SUCCESS;
	}
	public void musicRankingList(){
		try{
			
			Pagination<MusicRanking> result =  this.musicRankingService.query(musicRanking,page,rows,sort,order);
			List<MusicRanking> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(MusicRanking musicRanking : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("music_id", musicRanking.getMusic_id());
				jsonObject.element("music_name", musicRanking.getMusic_name());
				jsonObject.element("singer_name", musicRanking.getSinger_name());
				jsonObject.element("music_all_song_number",musicRanking.getMusic_all_song_number());
				jsonObject.element("music_month_song_number",musicRanking.getMusic_month_song_number());
				jsonObject.element("music_year_song_number",musicRanking.getMusic_year_song_number());
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
			
			Pagination<MusicRanking> result =  this.musicRankingService.searchMusicRank(musicRanking,page,rows,sort,order);
			List<MusicRanking> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(MusicRanking musicRanking : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("music_id", musicRanking.getMusic_id());
				jsonObject.element("music_name", musicRanking.getMusic_name());
				jsonObject.element("singer_name", musicRanking.getSinger_name());
				jsonObject.element("music_all_song_number",musicRanking.getMusic_all_song_number());
				jsonObject.element("music_month_song_number",musicRanking.getMusic_month_song_number());
				jsonObject.element("music_year_song_number",musicRanking.getMusic_year_song_number());
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

			MsgBean msgBean = this.musicRankingService.editMusicRank(musicRanking,song_number);

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
	public MusicRanking getMusicRanking() {
		return musicRanking;
	}

	public void setMusicRanking(MusicRanking musicRanking) {
		this.musicRanking = musicRanking;
	}

	public MusicRankingService getMusicRankingService() {
		return musicRankingService;
	}

	public void setMusicRankingService(MusicRankingService musicRankingService) {
		this.musicRankingService = musicRankingService;
	}
	public int getSong_number() {
		return song_number;
	}
	public void setSong_number(int song_number) {
		this.song_number = song_number;
	}
	
}
