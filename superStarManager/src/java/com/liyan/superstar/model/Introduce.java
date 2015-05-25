package com.liyan.superstar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Administrator
 * 新歌推荐
 */
@Entity
@Table(name="vod_store_music_introduce")
public class Introduce implements Serializable {
	
	@Id
	@Column(name="INTRODUCE_ID")
	private String introduce_id ;
	
	@JoinColumn(name="music_id")
	@ManyToOne
	private Song song ; 
	
	@Column(name="music_name")
	private String music_name ;
	
	@Column(name="STATE")
	private String state ; 
	
	@Column(name="INTRODUCE_TIME")
	private String introduce_time ; 
	
	@Column(name="INTRODUCE_SORT")
	private String introduce_sort ; 
	
	@Column(name="REMARK")
	private String remark ;

	@Column(name="is_activity")
	private String is_activity ; 
	
	@Column(name="update_time")
	private String update_time ; 
	public String getIntroduce_id() {
		return introduce_id;
	}

	public void setIntroduce_id(String introduce_id) {
		this.introduce_id = introduce_id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getIntroduce_time() {
		return introduce_time;
	}

	public void setIntroduce_time(String introduce_time) {
		this.introduce_time = introduce_time;
	}

	public String getIntroduce_sort() {
		return introduce_sort;
	}

	public void setIntroduce_sort(String introduce_sort) {
		this.introduce_sort = introduce_sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public String getMusic_name() {
		return music_name;
	}

	public void setMusic_name(String music_name) {
		this.music_name = music_name;
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
	
	
}
