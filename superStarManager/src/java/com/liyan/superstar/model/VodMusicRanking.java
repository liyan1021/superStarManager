package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.liyan.common.dao.Pagination;

@Entity
@Table(name="vod_store_vod_music_ranking")
public class VodMusicRanking implements Serializable {
	@Id
	@Column(name="music_id")
	private String music_id ;
	
	@Column(name="music_name")
	private String music_name ;
	
	@Column(name="singer_name")
	private String singer_name ; 
	
	@Column(name="singer_picture")
	private String singer_picture ;
	
	@Column(name="music_ranking")
	private String music_ranking ;
	
	@Column(name="singer_picture_path")
	private String singer_picture_path ;
	
	@Column(name="update_date")
	private String update_date ;

	public String getMusic_id() {
		return music_id;
	}

	public void setMusic_id(String music_id) {
		this.music_id = music_id;
	}

	public String getMusic_name() {
		return music_name;
	}

	public void setMusic_name(String music_name) {
		this.music_name = music_name;
	}

	public String getSinger_name() {
		return singer_name;
	}

	public void setSinger_name(String singer_name) {
		this.singer_name = singer_name;
	}

	public String getSinger_picture() {
		return singer_picture;
	}

	public void setSinger_picture(String singer_picture) {
		this.singer_picture = singer_picture;
	}

	public String getMusic_ranking() {
		return music_ranking;
	}

	public void setMusic_ranking(String music_ranking) {
		this.music_ranking = music_ranking;
	}

	public String getSinger_picture_path() {
		return singer_picture_path;
	}

	public void setSinger_picture_path(String singer_picture_path) {
		this.singer_picture_path = singer_picture_path;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

}
