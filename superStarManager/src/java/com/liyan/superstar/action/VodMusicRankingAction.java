package com.liyan.superstar.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.liyan.superstar.model.VodMusicRanking;
import com.liyan.superstar.service.VodMusicRankingService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class VodMusicRankingAction extends PageAwareActionSupport<VodMusicRanking>{
	
	@Autowired
	private VodMusicRankingService vodMusicRankingService ;
	
	private VodMusicRanking musicRanking ;

	private File importFile ;
	private String importFileContentType;
	private String importFileFileName ;
	private int song_number ; 
	
	public String init(){
		return Action.SUCCESS;
	}
	public void musicRankingList(){
		try{
			
			Pagination<VodMusicRanking> result =  this.vodMusicRankingService.query(musicRanking,page,rows,sort,order);
			List<VodMusicRanking> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(VodMusicRanking musicRanking : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("music_id", musicRanking.getMusic_id());
				jsonObject.element("music_name", musicRanking.getMusic_name());
				jsonObject.element("singer_name", musicRanking.getSinger_name());
				jsonObject.element("music_ranking",musicRanking.getMusic_ranking());
				
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
			
			Pagination<VodMusicRanking> result =  this.vodMusicRankingService.searchMusicRank(musicRanking,page,rows,sort,order);
			List<VodMusicRanking> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(VodMusicRanking musicRanking : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("music_id", musicRanking.getMusic_id());
				jsonObject.element("music_name", musicRanking.getMusic_name());
				jsonObject.element("singer_name", musicRanking.getSinger_name());
				jsonObject.element("music_ranking",musicRanking.getMusic_ranking());
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

			MsgBean msgBean = this.vodMusicRankingService.editMusicRank(musicRanking,getSong_number());

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
	public void importRank(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = this.vodMusicRankingService.importRank(importFile);
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
	
	public VodMusicRankingService getVodMusicRankingService() {
		return vodMusicRankingService;
	}

	public void setVodMusicRankingService(
			VodMusicRankingService vodMusicRankingService) {
		this.vodMusicRankingService = vodMusicRankingService;
	}

	public VodMusicRanking getMusicRanking() {
		return musicRanking;
	}

	public void setMusicRanking(VodMusicRanking musicRanking) {
		this.musicRanking = musicRanking;
	}
	public int getSong_number() {
		return song_number;
	}
	public void setSong_number(int song_number) {
		this.song_number = song_number;
	}
	public File getImportFile() {
		return importFile;
	}
	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}
	public String getImportFileContentType() {
		return importFileContentType;
	}
	public void setImportFileContentType(String importFileContentType) {
		this.importFileContentType = importFileContentType;
	}
	public String getImportFileFileName() {
		return importFileFileName;
	}
	public void setImportFileFileName(String importFileFileName) {
		this.importFileFileName = importFileFileName;
	} 
	
}
