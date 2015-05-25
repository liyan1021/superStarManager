package com.liyan.superstar.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="vod_store_advert_video")
public class AdvertVideo implements Serializable {
	
	@Id
	@Column(name="advert_id")
	private String advert_id ; 
	
	@Column(name="advert_theme")
	private String advert_theme ; 
	
	@Column(name="advert_content")
	private String advert_content ; 
	
	@Column(name="online_time")
	private String online_time ;
	
	@Column(name="offline_time")
	private String offline_time ;
	
	@Column(name="store")
	private String store ; 
	
	@Column(name="state")
	private String state ; 
	
	@Column(name="file_path")
	private String file_path ; 
	
	@Column(name="is_activity")
	private String is_activity ; 
	
	@Column(name="advert_sort")
	private String advert_sort ;
	
	@Column(name="update_time")
	private String update_time ; 
	
	@Transient
	private String online_start_time ;
	@Transient
	private String online_end_time ;
	@Transient
	private String offline_start_time ;
	@Transient
	private String offline_end_time ; 
	
	public String getAdvert_id() {
		return advert_id;
	}
	public void setAdvert_id(String advert_id) {
		this.advert_id = advert_id;
	}
	public String getAdvert_theme() {
		return advert_theme;
	}
	public void setAdvert_theme(String advert_theme) {
		this.advert_theme = advert_theme;
	}
	
	public String getOnline_time() {
		return online_time;
	}
	public void setOnline_time(String online_time) {
		this.online_time = online_time;
	}
	public String getOffline_time() {
		return offline_time;
	}
	public void setOffline_time(String offline_time) {
		this.offline_time = offline_time;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getIs_activity() {
		return is_activity;
	}
	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}
	public String getAdvert_sort() {
		return advert_sort;
	}
	public void setAdvert_sort(String advert_sort) {
		this.advert_sort = advert_sort;
	}
	public String getAdvert_content() {
		return advert_content;
	}
	public void setAdvert_content(String advert_content) {
		this.advert_content = advert_content;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getOnline_start_time() {
		return online_start_time;
	}
	public void setOnline_start_time(String online_start_time) {
		this.online_start_time = online_start_time;
	}
	public String getOnline_end_time() {
		return online_end_time;
	}
	public void setOnline_end_time(String online_end_time) {
		this.online_end_time = online_end_time;
	}
	public String getOffline_start_time() {
		return offline_start_time;
	}
	public void setOffline_start_time(String offline_start_time) {
		this.offline_start_time = offline_start_time;
	}
	public String getOffline_end_time() {
		return offline_end_time;
	}
	public void setOffline_end_time(String offline_end_time) {
		this.offline_end_time = offline_end_time;
	} 
	
}
