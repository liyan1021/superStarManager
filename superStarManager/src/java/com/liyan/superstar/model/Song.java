package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
* song_manage 实体类
* 2014-03-07 10:10:36
* create by GenerateJavaBeanUtil
* 曲库管理 
*/ 

@Entity
@Table(name="vod_store_song")
public class Song implements Serializable{
	
	@Id
	@Column(name="song_id")
	private String song_id ; //歌曲ID
	
	@Column(name="file_name")
	private String file_name ; //文件名称
	
	@Column(name="superstar_id")
	private String superstar_id ; //大歌星编号
	
	@Column(name="unsuperstar_id")
	private String unsuperstar_id ; //非大歌星编号
	
	@Column(name="song_name")
	private String song_name ; //歌曲名
	
	@Column(name="spell_first_letter_abbreviation")
	private String spell_first_letter_abbreviation ; //拼音
	
	@Column(name="singer_id")
	private String singer_id ; //歌星
	
	@Column(name="singer_name")
	private String singer_name ; //歌星名称
	
	@Column(name="singer_type")
	private String singer_type ; //歌星类别
	
	@Column(name="version")
	private String version ; //版本
	
	@Column(name="accompany_volume")
	private String accompany_volume ; //伴唱音量
	
	@Column(name="karaoke_volume")
	private String karaoke_volume ; //原唱音量
	
	@Column(name="lyric")
	private String lyric ; //歌词
	
	@Column(name="issue_year")
	private String issue_year ; //年代
	
	@Column(name="resolution")
	private String resolution ; //分辨率
	
	@Column(name="record_company")
	private String record_company ; //唱片公司
	
	@Column(name="song_theme")
	private String song_theme ; //主题
	
	@Column(name="movie_type")
	private String movie_type ; //影视类别
	
	@Column(name="movie_type_info")
	private String movie_type_info ; //影视类别说明
	
	@Column(name="song_type")
	private String song_type ; //曲种
	
	@Column(name="language")
	private String language ; //语种
	
	@Column(name="click_number")
	private Integer click_number ; //点唱次数
	
	@Column(name="collection_number")
	private Integer collection_number ; //收藏次数
	
	@Column(name="authors")
	private String authors ; //作词
	
	@Column(name="compose")
	private String compose ; //作曲
	
	@Column(name="original_track")
	private String original_track ; //原唱音轨
	
	@Column(name="original_channel")
	private String original_channel ; //原唱声道
	
	@Column(name="accompany_track")
	private String accompany_track ; //伴唱音轨
	
	@Column(name="accompany_channel")
	private String accompany_channel ; //伴唱声道
	
	@Column(name="storage_type")
	private String storage_type ; //存储类型
	
	@Column(name="file_path")
	private String file_path ; //文件路径
	
	@Column(name="file_length")
	private String file_length ; //文件大小
	
	@Column(name="file_format")
	private String file_format ; //文件格式
	
	@Column(name="light_control_set")
	private String light_control_set ; //灯光设置
	
	@Column(name="play_time")
	private String  play_time; //播放时长
	
	@Column(name="flow_type")
	private String flow_type ; //流格式
	
	@Column(name="song_bit_rate")
	private String song_bit_rate ; //码率
	
	@Column(name="import_time")
	private String import_time ; //入库时间
	
	@Column(name="idx_sha1")
	private String idx_sha1 ; //IDX_SHA1
	
	@Column(name="dgx_sha1")
	private String dgx_sha1 ; //DGX_SHA1
	
	@Column(name="remark")
	private String remark ; //备注
	
	@Column(name="is_activity")
	private String is_activity ;
	
	@Column(name="is_new")
	private String is_new ; 
	
	@Transient
	private String alert_str; 
	public String getSong_id() {
	
		return song_id;
	}
	public void setSong_id(String song_id) {
		this.song_id = song_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getSuperstar_id() {
		return superstar_id;
	}
	public void setSuperstar_id(String superstar_id) {
		this.superstar_id = superstar_id;
	}
	public String getUnsuperstar_id() {
		return unsuperstar_id;
	}
	public void setUnsuperstar_id(String unsuperstar_id) {
		this.unsuperstar_id = unsuperstar_id;
	}
	public String getSong_name() {
		return song_name;
	}
	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}
	public String getSpell_first_letter_abbreviation() {
		return spell_first_letter_abbreviation;
	}
	public void setSpell_first_letter_abbreviation(
			String spell_first_letter_abbreviation) {
		this.spell_first_letter_abbreviation = spell_first_letter_abbreviation;
	}
	public String getSinger_id() {
		return singer_id;
	}
	public void setSinger_id(String singer_id) {
		this.singer_id = singer_id;
	}
	public String getSinger_name() {
		return singer_name;
	}
	public void setSinger_name(String singer_name) {
		this.singer_name = singer_name;
	}
	public String getSinger_type() {
		return singer_type;
	}
	public void setSinger_type(String singer_type) {
		this.singer_type = singer_type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAccompany_volume() {
		return accompany_volume;
	}
	public void setAccompany_volume(String accompany_volume) {
		this.accompany_volume = accompany_volume;
	}
	public String getKaraoke_volume() {
		return karaoke_volume;
	}
	public void setKaraoke_volume(String karaoke_volume) {
		this.karaoke_volume = karaoke_volume;
	}
	public String getLyric() {
		return lyric;
	}
	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
	public String getIssue_year() {
		return issue_year;
	}
	public void setIssue_year(String issue_year) {
		this.issue_year = issue_year;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getRecord_company() {
		return record_company;
	}
	public void setRecord_company(String record_company) {
		this.record_company = record_company;
	}
	public String getSong_theme() {
		return song_theme;
	}
	public void setSong_theme(String song_theme) {
		this.song_theme = song_theme;
	}
	public String getMovie_type() {
		return movie_type;
	}
	public void setMovie_type(String movie_type) {
		this.movie_type = movie_type;
	}
	public String getMovie_type_info() {
		return movie_type_info;
	}
	public void setMovie_type_info(String movie_type_info) {
		this.movie_type_info = movie_type_info;
	}
	public String getSong_type() {
		return song_type;
	}
	public void setSong_type(String song_type) {
		this.song_type = song_type;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public Integer getClick_number() {
		return click_number;
	}
	public void setClick_number(Integer click_number) {
		this.click_number = click_number;
	}
	
	public Integer getCollection_number() {
		return collection_number;
	}
	public void setCollection_number(Integer collection_number) {
		this.collection_number = collection_number;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getCompose() {
		return compose;
	}
	public void setCompose(String compose) {
		this.compose = compose;
	}
	public String getOriginal_track() {
		return original_track;
	}
	public void setOriginal_track(String original_track) {
		this.original_track = original_track;
	}
	public String getOriginal_channel() {
		return original_channel;
	}
	public void setOriginal_channel(String original_channel) {
		this.original_channel = original_channel;
	}
	public String getAccompany_track() {
		return accompany_track;
	}
	public void setAccompany_track(String accompany_track) {
		this.accompany_track = accompany_track;
	}
	public String getAccompany_channel() {
		return accompany_channel;
	}
	public void setAccompany_channel(String accompany_channel) {
		this.accompany_channel = accompany_channel;
	}
	public String getStorage_type() {
		return storage_type;
	}
	public void setStorage_type(String storage_type) {
		this.storage_type = storage_type;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getFile_length() {
		return file_length;
	}
	public void setFile_length(String file_length) {
		this.file_length = file_length;
	}
	public String getFile_format() {
		return file_format;
	}
	public void setFile_format(String file_format) {
		this.file_format = file_format;
	}
	public String getLight_control_set() {
		return light_control_set;
	}
	public void setLight_control_set(String light_control_set) {
		this.light_control_set = light_control_set;
	}
	public String getPlay_time() {
		return play_time;
	}
	public void setPlay_time(String play_time) {
		this.play_time = play_time;
	}
	public String getFlow_type() {
		return flow_type;
	}
	public void setFlow_type(String flow_type) {
		this.flow_type = flow_type;
	}
	public String getSong_bit_rate() {
		return song_bit_rate;
	}
	public void setSong_bit_rate(String song_bit_rate) {
		this.song_bit_rate = song_bit_rate;
	}
	public String getImport_time() {
		return import_time;
	}
	public void setImport_time(String import_time) {
		this.import_time = import_time;
	}
	public String getIdx_sha1() {
		return idx_sha1;
	}
	public void setIdx_sha1(String idx_sha1) {
		this.idx_sha1 = idx_sha1;
	}
	public String getDgx_sha1() {
		return dgx_sha1;
	}
	public void setDgx_sha1(String dgx_sha1) {
		this.dgx_sha1 = dgx_sha1;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIs_activity() {
		return is_activity;
	}
	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}
	public String getIs_new() {
		return is_new;
	}
	public void setIs_new(String is_new) {
		this.is_new = is_new;
	}
	public String getAlert_str() {
		return alert_str;
	}
	public void setAlert_str(String alert_str) {
		this.alert_str = alert_str;
	}
	

}
