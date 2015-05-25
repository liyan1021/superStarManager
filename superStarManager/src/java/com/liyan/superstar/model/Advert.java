package com.liyan.superstar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Administrator
 * 广告发布
 */
@Entity
@Table(name="vod_store_advert")
public class Advert implements Serializable{
	
	@Id
	@Column(name="ADVERT_ID")
	private String advert_id ; 
	
	@Column(name="ADVERT_NAME")
	private String advert_name; 
	
	@Column(name="ONLINE_TIME")
	private String online_time ;
	
	@Column(name="OFFLINE_TIME")
	private String offline_time ;
	
	@Column(name="STORE")
	private String store ;
	
	@Column(name="ADVERT_CONTENT")
	private String advert_content ;
	
	@Column(name="STATE")
	private String state ;
	
	@Column(name="FILE_PATH")
	private String file_path ;
	
	@Column(name="ABSOLUTE_PATH")
	private String absolute_path ;  
	
	@Column(name="IS_ACTIVITY")
	private String  is_activity; 
	
	@Column(name="ADVERT_TIME")
	private String advert_time ;
	
	@Column(name="ADVERT_SORT")
	private String advert_sort ; 

	@Column(name="update_time")
	private String update_time ; 
	
	@Column(name="is_index_advert")
	private String is_index_advert ; 
	
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
	public String getAdvert_name() {
		return advert_name;
	}
	public void setAdvert_name(String advert_name) {
		this.advert_name = advert_name;
	}
	
	public String getIs_activity() {
		return is_activity;
	}
	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
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
	public String getAdvert_content() {
		return advert_content;
	}
	public void setAdvert_content(String advert_content) {
		this.advert_content = advert_content;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAdvert_time() {
		return advert_time;
	}
	public void setAdvert_time(String advert_time) {
		this.advert_time = advert_time;
	}
	public String getAdvert_sort() {
		return advert_sort;
	}
	public void setAdvert_sort(String advert_sort) {
		this.advert_sort = advert_sort;
	}
	public String getAbsolute_path() {
		return absolute_path;
	}
	public void setAbsolute_path(String absolute_path) {
		this.absolute_path = absolute_path;
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
	public String getIs_index_advert() {
		return is_index_advert;
	}
	public void setIs_index_advert(String is_index_advert) {
		this.is_index_advert = is_index_advert;
	}

	
	
	
}
