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
 * 界面设置
 */
@Entity
@Table(name="vod_store_interface")
public class Interface implements Serializable{
	
	@Id
	@Column(name="INTERFACE_ID")
	private String interface_id ;	
	
	@Column(name="INTERFACE_NAME")
	private String interface_name ;	
	
	@Column(name="ONLINE_TIME")
	private String online_time ;
	
	@Column(name="OFFLINE_TIME")
	private String offline_time ;
	
	@Column(name="INTERFACE_TYPE")
	private String interface_type ; 	
	
	@Column(name="STORE")
	private String store ;
	
	@Column(name="STATE")
	private String state ;
	
	@Column(name="FILE_PATH")
	private String file_path ;
	
	@Column(name="ABSOLUTE_PATH")
	private String absolute_path ;  
	
	@Column(name="FILE_FORMAT")
	private String file_format ; 

	@Column(name="IS_ACTIVITY")
	private String is_activity ;
	
	@Column(name="INTERFACE_TIME")
	private String interface_time ; 
	
	@Column(name="INTERFACE_SORT")
	private String interface_sort ; 
	
	@Column(name="UPDATE_TIME")
	private String update_time ;
	
	
	@Transient
	private String online_start_time ;
	@Transient
	private String online_end_time ;
	@Transient
	private String offline_start_time ;
	@Transient
	private String offline_end_time ; 

	
	public String getInterface_id() {
		return interface_id;
	}

	public void setInterface_id(String interface_id) {
		this.interface_id = interface_id;
	}

	public String getInterface_name() {
		return interface_name;
	}

	public void setInterface_name(String interface_name) {
		this.interface_name = interface_name;
	}

	public String getIs_activity() {
		return is_activity;
	}

	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}

	public String getInterface_type() {
		return interface_type;
	}

	public void setInterface_type(String interface_type) {
		this.interface_type = interface_type;
	}

	public String getFile_format() {
		return file_format;
	}

	public void setFile_format(String file_format) {
		this.file_format = file_format;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
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

	public String getInterface_time() {
		return interface_time;
	}

	public void setInterface_time(String interface_time) {
		this.interface_time = interface_time;
	}

	public String getInterface_sort() {
		return interface_sort;
	}

	public void setInterface_sort(String interface_sort) {
		this.interface_sort = interface_sort;
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
	
	
}
