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
import com.liyan.superstar.model.ApkInfo;
import com.liyan.superstar.model.PassMusic;
import com.liyan.superstar.model.Room;
import com.liyan.superstar.model.Song;
import com.liyan.superstar.service.ApkInfoService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class ApkInfoAction extends PageAwareActionSupport<ApkInfo> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8203021741684072629L;
	private String ids ; 
	private ApkInfo apkInfo; 
	private Room room;
	private String room_id;
	private String room_num;
	private String start_time ;
	private String end_time ;
	private File apk_file ;
	private String apk_fileFileName; // 文件名称
	private String apk_fileContentType; // 文件类型
	@Autowired
	private ApkInfoService apkInfoService;
	
	/**
	 * 初始化页面
	 * @return
	 */
	public String init(){
		
		return Action.SUCCESS;
	}
	
	/**
	 * 初始化列表
	 */
	public void apkInfoList(){
		try{
			
			Pagination<ApkInfo> result = this.apkInfoService.query(apkInfo,start_time,end_time,page,rows,sort,order);
			List<ApkInfo> resultList = result.getResultList();
			
			JSONArray jsonArray = new JSONArray();
			for(ApkInfo apkInfo : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("apk_id", apkInfo.getApk_id());
				jsonObject.element("apk_name", apkInfo.getApk_name());
				jsonObject.element("apk_version",apkInfo.getApk_version());
				jsonObject.element("apk_path", apkInfo.getApk_path());
				jsonObject.element("create_time", apkInfo.getCreate_time().toString());
				jsonObject.element("state", apkInfo.getState());
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
	 * 条件搜索APK
	 */
	public void searchApkInfo(){
		try{
			
			Pagination<ApkInfo> result = this.apkInfoService.searchApkInfo(apkInfo,start_time,end_time,page,rows);
			List<ApkInfo> resultList = result.getResultList();
			
			JSONArray jsonArray = new JSONArray();
			for(ApkInfo apkInfo : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("apk_id", apkInfo.getApk_id());
				jsonObject.element("apk_name", apkInfo.getApk_name());
				jsonObject.element("apk_version",apkInfo.getApk_version());
				jsonObject.element("apk_path", apkInfo.getApk_path());
				jsonObject.element("create_time", apkInfo.getCreate_time().toString());
				jsonObject.element("state", apkInfo.getState());
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
	 * 删除APK
	 */
	public void removeApkInfo(){
		try{
			String[] idsList = ids.split(",");
			MsgBean msgBean = this.apkInfoService.removeApkInfo(idsList);
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
	 * 上传APK
	 */
	public void saveApkInfo(){
		try{
			MsgBean msgBean = this.apkInfoService.saveApkInfo(apkInfo,apk_file,apk_fileFileName,apk_fileContentType);
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
	public void checkApkVersionSort(){
		try{
			MsgBean msgBean = this.apkInfoService.checkApkVersionSort(apkInfo);
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
	public void pushApkReady(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			
			MsgBean msgBean = this.apkInfoService.pushApkReady(ids,room_id,room_num); 

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
	public void pushApk(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			
			MsgBean msgBean = this.apkInfoService.pushApk(ids); 

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
			Pagination<Room> result = this.apkInfoService.queryRoom(room, page, rows);
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
	public ApkInfoService getApkInfoService() {
		return apkInfoService;
	}
	public void setApkInfoService(ApkInfoService apkInfoService) {
		this.apkInfoService = apkInfoService;
	}
	public ApkInfo getApkInfo() {
		return apkInfo;
	}
	public void setApkInfo(ApkInfo apkInfo) {
		this.apkInfo = apkInfo;
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

	

	public String getApk_fileFileName() {
		return apk_fileFileName;
	}

	public void setApk_fileFileName(String apk_fileFileName) {
		this.apk_fileFileName = apk_fileFileName;
	}

	public String getApk_fileContentType() {
		return apk_fileContentType;
	}

	public void setApk_fileContentType(String apk_fileContentType) {
		this.apk_fileContentType = apk_fileContentType;
	}

	public File getApk_file() {
		return apk_file;
	}

	public void setApk_file(File apk_file) {
		this.apk_file = apk_file;
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
