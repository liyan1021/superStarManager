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
import com.liyan.superstar.model.Room;
import com.liyan.superstar.model.SQLiteInfo;
import com.liyan.superstar.service.SQLiteInfoService;
import com.opensymphony.xwork2.Action;

/**
 * @author Administrator
 *SQLite版本管理
 */
@Controller
@Scope("prototype") 
public class SqliteInfoAction extends PageAwareActionSupport<SQLiteInfo>{
	@Autowired
	private SQLiteInfoService sqliteInfoService ; 
	private SQLiteInfo sqliteInfo ; 
	private String ids ; 
	private Room room;
	private String room_id;
	private String room_num;
	private String start_time ;
	private String end_time ;
	private File sqlite_file ;
	private String sqlite_fileFileName; // 文件名称
	private String sqlite_fileContentType; // 文件类型
	/**
	 * SQLite版本管理初始化
	 * @return
	 */
	public String init(){
		return Action.SUCCESS;
	}
	/**
	 * SQLite版本管理 初始化列表
	 */
	public void sqliteInfoList(){
		try{
			
			Pagination<SQLiteInfo> result = this.sqliteInfoService.query(sqliteInfo,start_time,end_time,page,rows,sort,order);
			List<SQLiteInfo> resultList = result.getResultList();
			
			JSONArray jsonArray = new JSONArray();
			for(SQLiteInfo sqliteInfo : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("sqlite_id", sqliteInfo.getSqlite_id());
				jsonObject.element("sqlite_version",sqliteInfo.getSqlite_version());
				jsonObject.element("sqlite_path", sqliteInfo.getSqlite_path());
				jsonObject.element("create_time", sqliteInfo.getCreate_time().toString());
				jsonObject.element("state", sqliteInfo.getState()); 
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
	 * 查询导入的SQLite
	 */
	public void searchSqliteInfo(){
		try{
			
			Pagination<SQLiteInfo> result = this.sqliteInfoService.searchSqliteInfo(sqliteInfo,start_time,end_time,page,rows);
			List<SQLiteInfo> resultList = result.getResultList();
			
			JSONArray jsonArray = new JSONArray();
			for(SQLiteInfo sqliteInfo : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("sqlite_id", sqliteInfo.getSqlite_id());
				jsonObject.element("sqlite_version",sqliteInfo.getSqlite_version());
				jsonObject.element("sqlite_path", sqliteInfo.getSqlite_path());
				jsonObject.element("create_time", sqliteInfo.getCreate_time().toString());
				jsonObject.element("state", sqliteInfo.getState());
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
	 * 删除导入的SQLite
	 */
	public void removeSqliteInfo(){
		try{
			String[] idsList = ids.split(",");
			MsgBean msgBean = this.sqliteInfoService.removeSqliteInfo(idsList);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response=ServletActionContext.getResponse();
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
	 * 上传SQLite
	 */
	public void saveSqliteInfo(){
		try{
			MsgBean msgBean = this.sqliteInfoService.saveSqliteInfo(sqliteInfo,sqlite_file,sqlite_fileFileName,sqlite_fileContentType);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response=ServletActionContext.getResponse();
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
	 * 生成SQLite文件
	 */
	public void generateSqlite(){
		try{
			MsgBean msgBean = this.sqliteInfoService.generateSqlite();
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void pushSqlite(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			
			MsgBean msgBean = this.sqliteInfoService.pushSqlite(ids); 

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
	public void pushSqliteReady(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			
			MsgBean msgBean = this.sqliteInfoService.pushSqliteReady(ids,room_id,room_num); 

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
	/**
	 * 获取包房编号列表
	 */
	public void roomList(){
		try{
			Pagination<Room> result = this.sqliteInfoService.queryRoom(room, page, rows);
			List<Room> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Room room : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("oid", room.getOid());
				jsonObject.element("room_no", room.getRoom_no());
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
	public SQLiteInfoService getSqliteInfoService() {
		return sqliteInfoService;
	}

	public void setSqliteInfoService(SQLiteInfoService sqliteInfoService) {
		this.sqliteInfoService = sqliteInfoService;
	}

	public SQLiteInfo getSqliteInfo() {
		return sqliteInfo;
	}

	public void setSqliteInfo(SQLiteInfo sqliteInfo) {
		this.sqliteInfo = sqliteInfo;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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

	public File getSqlite_file() {
		return sqlite_file;
	}

	public void setSqlite_file(File sqlite_file) {
		this.sqlite_file = sqlite_file;
	}

	public String getSqlite_fileFileName() {
		return sqlite_fileFileName;
	}

	public void setSqlite_fileFileName(String sqlite_fileFileName) {
		this.sqlite_fileFileName = sqlite_fileFileName;
	}

	public String getSqlite_fileContentType() {
		return sqlite_fileContentType;
	}

	public void setSqlite_fileContentType(String sqlite_fileContentType) {
		this.sqlite_fileContentType = sqlite_fileContentType;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String roomId) {
		room_id = roomId;
	}
	public String getRoom_num() {
		return room_num;
	}
	public void setRoom_num(String roomNum) {
		room_num = roomNum;
	}
	
}
