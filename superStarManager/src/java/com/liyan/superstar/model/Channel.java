package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 * 视频转播
 */
@Entity
@Table(name="VOD_STORE_VIDEO_CAST")
public class Channel  implements Serializable{
	
	@Id
	@Column(name="channel_id")
	private String channel_id ; 
	
	@Column(name="channel_name")
	private String channel_name ;
	
	@Column(name="channel_url")
	private String channel_url ;
	
	@Column(name="channel_content")
	private String channel_content;
	
	@Column(name="state")
	private String state;
	
	@Column(name="file_path")
	private String file_path ;
	
	@Column(name="absolute_path")
	private String absolute_path ; 
	
	@Column(name="sort")
	private String sort ;

	@Column(name="is_activity")
	private String is_activity ;
	
	@Column(name="update_time")
	private String update_time ;
	
	public String getIs_activity() {
		return is_activity;
	}

	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public String getChannel_url() {
		return channel_url;
	}

	public void setChannel_url(String channel_url) {
		this.channel_url = channel_url;
	}

	public String getChannel_content() {
		return channel_content;
	}

	public void setChannel_content(String channel_content) {
		this.channel_content = channel_content;
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

	public String getAbsolute_path() {
		return absolute_path;
	}

	public void setAbsolute_path(String absolute_path) {
		this.absolute_path = absolute_path;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}

