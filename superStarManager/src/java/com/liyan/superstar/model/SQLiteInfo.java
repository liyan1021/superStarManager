package com.liyan.superstar.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 *SQLite
 */
@Entity
@Table(name="vod_store_sqlite_info")
public class SQLiteInfo implements Serializable{
	
	@Id
	@Column(name="SQLITE_ID")
	private String sqlite_id ;
	
	@Column(name="SQLITE_VERSION")
	private String sqlite_version ;
	
	@Column(name="SQLITE_PATH")
	private String sqlite_path ;
	
	@Column(name="CREATE_TIME")
	private String create_time ;
	
	@Column(name="STATE")
	private String state ; 
	
	@Column(name="SQLITE_FILE_PATH")
	private String sqlite_file_path ; 
	
	@Column(name="is_activity")
	private String is_activity ; 
	
	@Column(name="ROOM_ID")
	private String room_id;
	
	@Column(name="ROOM_NUM")
	private String room_num;
	
	public String getSqlite_id() {
		return sqlite_id;
	}
	public void setSqlite_id(String sqlite_id) {
		this.sqlite_id = sqlite_id;
	}
	public String getSqlite_version() {
		return sqlite_version;
	}
	public void setSqlite_version(String sqlite_version) {
		this.sqlite_version = sqlite_version;
	}
	public String getSqlite_path() {
		return sqlite_path;
	}
	public void setSqlite_path(String sqlite_path) {
		this.sqlite_path = sqlite_path;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSqlite_file_path() {
		return sqlite_file_path;
	}
	public void setSqlite_file_path(String sqlite_file_path) {
		this.sqlite_file_path = sqlite_file_path;
	}
	public String getIs_activity() {
		return is_activity;
	}
	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
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
