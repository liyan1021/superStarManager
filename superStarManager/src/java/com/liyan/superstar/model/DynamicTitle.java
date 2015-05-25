package com.liyan.superstar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 *走马灯字幕
 */
@Entity
@Table(name="vod_store_dynamic_title")
public class DynamicTitle implements Serializable{
	
	@Id
	@Column(name="TITLE_ID")
	private String title_id ;
	
	@Column(name="TITLE_TIME_LENGTH")
	private String title_time_length ;
	
	@Column(name="TITLE_NUMBER")
	private String title_number ; 

	@Column(name="TITLE_DATE_TIME")
	private String title_date_time ; 

	@Column(name="TITLE_CONTENT")
	private String title_content ;
	
	@Column(name="STATE")
	private String state ; 
	
	@Column(name="update_time")
	private String update_time ;
	
	@Column(name="is_activity")
	private String is_activity ; 
	
	@Column(name="room_id")
	private String room_id ;
	
	@Column(name="room_num")
	private String room_num ; 
	
	
	
	public String getTitle_id() {
		return title_id;
	}

	public void setTitle_id(String title_id) {
		this.title_id = title_id;
	}

	public String getTitle_time_length() {
		return title_time_length;
	}

	public void setTitle_time_length(String title_time_length) {
		this.title_time_length = title_time_length;
	}

	public String getTitle_number() {
		return title_number;
	}

	public void setTitle_number(String title_number) {
		this.title_number = title_number;
	}


	public String getTitle_date_time() {
		return title_date_time;
	}

	public void setTitle_date_time(String title_date_time) {
		this.title_date_time = title_date_time;
	}

	public String getTitle_content() {
		return title_content;
	}

	public void setTitle_content(String title_content) {
		this.title_content = title_content;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
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

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	public String getRoom_num() {
		return room_num;
	}

	public void setRoom_num(String room_num) {
		this.room_num = room_num;
	}

	
}
