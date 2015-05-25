package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 * 
 * 歌曲排行：月排行、年排行、总排行
 */
@Entity
@Table(name="VOD_STORE_MUSIC_RANKING")
public class MusicRanking implements Serializable {
	
	@Id
	@Column(name="music_id")
	private String music_id ; 
	
	@Column(name="music_name")
	private String music_name ;
	
	@Column(name="singer_name")
	private String singer_name ;
	
	@Column(name="singer_picture")
	private String singer_picture ; 
	
	@Column(name="music_all_song_number")
	private String music_all_song_number ;
	
	@Column(name="singer_picture_path")
	private String singer_picture_path;
	
	@Column(name="music_month_song_number")
	private String music_month_song_number ;
	
	@Column(name="music_year_song_number")
	private String music_year_song_number ;

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

	public String getMusic_all_song_number() {
		return music_all_song_number;
	}

	public void setMusic_all_song_number(String music_all_song_number) {
		this.music_all_song_number = music_all_song_number;
	}

	public String getSinger_picture_path() {
		return singer_picture_path;
	}

	public void setSinger_picture_path(String singer_picture_path) {
		this.singer_picture_path = singer_picture_path;
	}

	public String getMusic_month_song_number() {
		return music_month_song_number;
	}

	public void setMusic_month_song_number(String music_month_song_number) {
		this.music_month_song_number = music_month_song_number;
	}

	public String getMusic_year_song_number() {
		return music_year_song_number;
	}

	public void setMusic_year_song_number(String music_year_song_number) {
		this.music_year_song_number = music_year_song_number;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	} 
	
	
}
