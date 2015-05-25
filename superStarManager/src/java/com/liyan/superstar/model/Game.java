package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 * 游戏管理
 */
@Entity
@Table(name="vod_store_game")
public class Game implements Serializable{
	
	@Id
	@Column(name="GAME_ID")
	private String game_id ;
	
	@Column(name="GAME_NAME")
	private String game_name ; 
	
	@Column(name="IMPORT_TIME")
	private String import_time ; 
	
	@Column(name="STATE")
	private String 	state ;
	
	@Column(name="GAME_URI")
	private String game_uri;

	@Column(name="is_activity")
	private String is_activity ;
	
	@Column(name="UPDATE_TIME")
	private String update_time ;
	
	public String getGame_id() {
		return game_id;
	}

	public void setGame_id(String game_id) {
		this.game_id = game_id;
	}

	public String getGame_name() {
		return game_name;
	}

	public void setGame_name(String game_name) {
		this.game_name = game_name;
	}

	public String getImport_time() {
		return import_time;
	}

	public void setImport_time(String import_time) {
		this.import_time = import_time;
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

	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getGame_uri() {
		return game_uri;
	}

	public void setGame_uri(String game_uri) {
		this.game_uri = game_uri;
	}
	
	
}

