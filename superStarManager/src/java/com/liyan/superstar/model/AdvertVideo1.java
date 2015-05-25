package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="vod_store_advert_video")
public class AdvertVideo1 implements Serializable {
	@Id
	@Column(name="advert_id")
	private String advert_id;
	
	@Column(name="advert_theme")
	private String advert_theme;
    
	@Column(name="advert_content")
	private String advert_content;
	
	@Column(name="music_id")
	private String music_id;
	
	@Column(name="music_name")
	private String music_name;
	
	@Column(name="online_time")
	private String online_time ;
	
	@Column(name="offline_time")
	private String offline_time ;
	
	@Column(name="store")
	private String store ; 
	
	@Column(name="state")
	private String state ; 
		
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
	public void setAdvert_id(String advertId) {
		advert_id = advertId;
	}
	public String getAdvert_theme() {
		return advert_theme;
	}
	public void setAdvert_theme(String advertTheme) {
		advert_theme = advertTheme;
	}
	public String getAdvert_content() {
		return advert_content;
	}
	public void setAdvert_content(String advertContent) {
		advert_content = advertContent;
	}
	public String getMusic_id() {
		return music_id;
	}
	public void setMusic_id(String musicId) {
		music_id = musicId;
	}
	public String getMusic_name() {
		return music_name;
	}
	public void setMusic_name(String musicName) {
		music_name = musicName;
	}
	public String getOnline_time() {
		return online_time;
	}
	public void setOnline_time(String onlineTime) {
		online_time = onlineTime;
	}
	public String getOffline_time() {
		return offline_time;
	}
	public void setOffline_time(String offlineTime) {
		offline_time = offlineTime;
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
	public String getIs_activity() {
		return is_activity;
	}
	public void setIs_activity(String isActivity) {
		is_activity = isActivity;
	}
	public String getAdvert_sort() {
		return advert_sort;
	}
	public void setAdvert_sort(String advertSort) {
		advert_sort = advertSort;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String updateTime) {
		update_time = updateTime;
	}
	public String getOnline_start_time() {
		return online_start_time;
	}
	public void setOnline_start_time(String onlineStartTime) {
		online_start_time = onlineStartTime;
	}
	public String getOnline_end_time() {
		return online_end_time;
	}
	public void setOnline_end_time(String onlineEndTime) {
		online_end_time = onlineEndTime;
	}
	public String getOffline_start_time() {
		return offline_start_time;
	}
	public void setOffline_start_time(String offlineStartTime) {
		offline_start_time = offlineStartTime;
	}
	public String getOffline_end_time() {
		return offline_end_time;
	}
	public void setOffline_end_time(String offlineEndTime) {
		offline_end_time = offlineEndTime;
	} 
	
	
}
