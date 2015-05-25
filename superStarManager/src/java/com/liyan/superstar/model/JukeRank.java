package com.liyan.superstar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author Administrator
 * 点唱记录
 */
@Entity
@Table(name="vod_store_jukebox_record")
public class JukeRank implements Serializable{
	
	@Id
	@Column(name="RECORD_ID")
	private String record_id ; 
	
	@Column(name="MUSIC_ID")
	private String music_id ;

	@Column(name="MUSIC_NAME")
	private String music_name ;
	
	@Column(name="START_TIME")
	private String start_time ;
	
	@Column(name="END_TIME")
	private String end_time ;
	
	@Column(name="play_plan")
	private String play_plan ; 
	
	
	@Column(name="MEMBER_NO")
	private String member_no ;
	
	@Column(name="company")
	private String company ;
	
	@Column(name="store")
	private String store;
	
	@Column(name="room")
	private String room ;
	
	public String getRecord_id() {
		return record_id;
	}
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}
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
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getMember_no() {
		return member_no;
	}
	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}
	public String getPlay_plan() {
		return play_plan;
	}
	public void setPlay_plan(String play_plan) {
		this.play_plan = play_plan;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	
	
	
}
