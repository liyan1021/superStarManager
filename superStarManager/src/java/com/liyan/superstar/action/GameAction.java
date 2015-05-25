package com.liyan.superstar.action;

import java.io.File;
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
import com.liyan.superstar.model.Game;
import com.liyan.superstar.service.GameService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class GameAction extends PageAwareActionSupport<Game> {
	
	
	@Autowired
	private GameService gameService ;
	private Game game ; 
	private String ids ; 
	private File game_file ;
	private String game_fileFileName; // 文件名称
	private String game_fileContentType; // 文件类型
	private String start_time ;
	private String end_time;  
	public String init(){
		
		return Action.SUCCESS;
	}
	
	public void gameList(){
		try{
			Pagination<Game> result = this.gameService.query(game,start_time,end_time,page,rows,sort,order);
			List<Game> resultList = result.getResultList();
			
			JSONArray jsonArray = new JSONArray();
			for(Game game : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("game_id",game.getGame_id() );
				jsonObject.element("game_name",game.getGame_name());
				jsonObject.element("import_time",game.getImport_time());
				jsonObject.element("state",game.getState());
				jsonObject.element("game_uri", game.getGame_uri());
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
	public void removeGame(){
		try{
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.gameService.removeGame(ids);

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
	public void importGame(){
		try{
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.gameService.importGame(game_file,game_fileFileName);

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
	public void searchGame(){
		try{
			Pagination<Game> result = this.gameService.search(game,start_time,end_time,page,rows,sort,order);
			List<Game> resultList = result.getResultList();
			
			JSONArray jsonArray = new JSONArray();
			for(Game game : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("game_id",game.getGame_id() );
				jsonObject.element("game_name",game.getGame_name());
				jsonObject.element("game_uri", game.getGame_uri());
				jsonObject.element("import_time",game.getImport_time());
				jsonObject.element("state",game.getState());
//				jsonObject.element("file_path",game.getFile_path());
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
	public void pushGame(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.gameService.pushGame(ids);
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
	public void saveGame(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.gameService.saveGame(game);
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
	public void editGame(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.gameService.editGame(game);
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
	public GameService getGameService() {
		return gameService;
	}

	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public File getGame_file() {
		return game_file;
	}

	public void setGame_file(File game_file) {
		this.game_file = game_file;
	}

	public String getGame_fileFileName() {
		return game_fileFileName;
	}

	public void setGame_fileFileName(String game_fileFileName) {
		this.game_fileFileName = game_fileFileName;
	}

	public String getGame_fileContentType() {
		return game_fileContentType;
	}

	public void setGame_fileContentType(String game_fileContentType) {
		this.game_fileContentType = game_fileContentType;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	
}
