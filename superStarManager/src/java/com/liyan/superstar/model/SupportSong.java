package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
* support_list 实体类
* 2014-03-12 09:38:32
* create by GenerateJavaBeanUtil 
* 补歌清单
*/ 
@Entity
@Table(name="vod_store_support_list")
public class SupportSong implements Serializable{
	
	@Id
	@Column(name="support_id")
	private String support_id;
	
	@Column(name="song_name")
	private String song_name;
	
	@Column(name="song_year")
	private String song_year;
	
	@Column(name="star_name")
	private String star_name;
	
	@Column(name="song_state")
	private String song_state;

	@Column(name="support_time")
	private String support_time ;
	
	@Column(name="is_activity")
	private String  is_activity ; 
	
	@Column(name="update_time")
	private String update_time ;
	
	@Column(name="handler_person")
	private String handler_person ; 
	
	@Column(name="handler_time")
	private String handler_time ; 
	
	@Column(name="version")
	private String versions ; 
	
	@Column(name="language")
	private String languages; 
	
	@Transient
	private String handler_start_time ; 
	
	@Transient
	private String handler_end_time ;
	
	public String getSupport_id() {
		return support_id;
	}
	public void setSupport_id(String support_id) {
		this.support_id = support_id;
	}
	public String getSong_name() {
		return song_name;
	}
	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}
	public String getSong_year() {
		return song_year;
	}
	public void setSong_year(String song_year) {
		this.song_year = song_year;
	}
	public String getStar_name() {
		return star_name;
	}
	public void setStar_name(String star_name) {
		this.star_name = star_name;
	}
	public String getSong_state() {
		return song_state;
	}
	public void setSong_state(String song_state) {
		this.song_state = song_state;
	}
	public String getSupport_time() {
		return support_time;
	}
	public void setSupport_time(String support_time) {
		this.support_time = support_time;
	}
	public String getIs_activity() {
		return is_activity;
	}
	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getHandler_person() {
		return handler_person;
	}
	public void setHandler_person(String handler_person) {
		this.handler_person = handler_person;
	}
	public String getHandler_time() {
		return handler_time;
	}
	public void setHandler_time(String handler_time) {
		this.handler_time = handler_time;
	}
	public String getHandler_start_time() {
		return handler_start_time;
	}
	public void setHandler_start_time(String handler_start_time) {
		this.handler_start_time = handler_start_time;
	}
	public String getHandler_end_time() {
		return handler_end_time;
	}
	public void setHandler_end_time(String handler_end_time) {
		this.handler_end_time = handler_end_time;
	}
	public String getLanguages() {
		return languages;
	}
	public void setLanguages(String language) {
		this.languages = language;
	}
	public String getVersions() {
		return versions;
	}

	public void setVersions(String version) {
		this.versions = version;
	}

}
