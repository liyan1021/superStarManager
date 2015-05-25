package com.liyan.superstar.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 * 过路歌曲
 */
@Entity
@Table(name="vod_store_pass")
public class PassMusic implements Serializable{

	@Id
	@Column(name="PASS_ID")
	private String pass_id ; 
	
	@Column(name="SONG_ID")
	private String song_id ;
	
	@Column(name="SONG_NAME")
	private String song_name ;
	
	@Column(name="SINGER_ID")
	private String singer_id ; 
	
	@Column(name="SINGER_NAME")
	private  String singer_name ; 
	
	@Column(name="PASS_SORT")
	private String pass_sort; 
	
	@Column(name="STATE")
	private String state ;
	
	@Column(name="CREATE_TIME")
	private Date create_time ;
	
	@Column(name="REMARK")
	private String remark ; 
	
	@Column(name="IS_ACTIVITY")
	private String is_activity ;
	
	@Column(name="update_time")
	private String update_time ; 
	public String getPass_id() {
		return pass_id;
	}
	public void setPass_id(String pass_id) {
		this.pass_id = pass_id;
	}
	
	public String getPass_sort() {
		return pass_sort;
	}
	public void setPass_sort(String pass_sort) {
		this.pass_sort = pass_sort;
	}
	public String getIs_activity() {
		return is_activity;
	}
	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}
	public String getSinger_id() {
		return singer_id;
	}
	public void setSinger_id(String singer_id) {
		this.singer_id = singer_id;
	}
	public String getSinger_name() {
		return singer_name;
	}
	public void setSinger_name(String singer_name) {
		this.singer_name = singer_name;
	}
	public String getSong_id() {
		return song_id;
	}
	public void setSong_id(String song_id) {
		this.song_id = song_id;
	}
	public String getSong_name() {
		return song_name;
	}
	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}
