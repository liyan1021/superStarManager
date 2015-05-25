package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Administrator
 * 广告对应歌曲表
 */
@Entity
@Table(name="VOD_STORE_ADVERT_MUSIC")
public class AdvertMusic implements Serializable {
	@Id
	@Column(name="ADVERT_MUSIC_ID")
	private String advert_music_id;
	
	@Column(name="ADVERT_ID")
	private String advert_id ;
	
	@ManyToOne
	@JoinColumn(name="song_id")
	private Song song ;
	
	public String getAdvert_music_id() {
		return advert_music_id;
	}

	public void setAdvert_music_id(String advert_music_id) {
		this.advert_music_id = advert_music_id;
	}

	public String getAdvert_id() {
		return advert_id;
	}

	public void setAdvert_id(String advert_id) {
		this.advert_id = advert_id;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	} 
	
	
}
