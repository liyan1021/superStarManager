package com.liyan.superstar.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author Administrator
 * apk 版本管理
 */
@Entity
@Table(name="vod_store_apk_info")
public class ApkInfo implements Serializable {
	@Id
	@Column(name="APK_ID")
	private String apk_id ;
	
	@Column(name="APK_NAME")
	private String apk_name ;
	
	@Column(name="APK_VERSION")
	private String apk_version ;
	
	@Column(name="APK_PATH")
	private String apk_path ;
	
	@Column(name="CREATE_TIME")
	private String create_time ;
	
	@Column(name="apk_file_path")
	private String apk_file_path ;
	
	@Column(name="is_activity")
	private String is_activity ; 
	
	@Column(name="state")
	private String state ;
	
	@Column(name="room_id")
	private String room_id;
	
	@Column(name="room_num")
	private String room_num;
	
	public String getApk_id() {
		return apk_id;
	}
	public void setApk_id(String apk_id) {
		this.apk_id = apk_id;
	}
	public String getApk_name() {
		return apk_name;
	}
	public void setApk_name(String apk_name) {
		this.apk_name = apk_name;
	}
	public String getApk_version() {
		return apk_version;
	}
	public void setApk_version(String apk_version) {
		this.apk_version = apk_version;
	}
	public String getApk_path() {
		return apk_path;
	}
	public void setApk_path(String apk_path) {
		this.apk_path = apk_path;
	}
	public String getApk_file_path() {
		return apk_file_path;
	}
	public void setApk_file_path(String apk_file_path) {
		this.apk_file_path = apk_file_path;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getIs_activity() {
		return is_activity;
	}
	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String roomId) {
		this.room_id = roomId;
	}
	public String getRoom_num() {
		return room_num;
	}
	public void setRoom_num(String roomNum) {
		this.room_num = roomNum;
	}
	
	
}
