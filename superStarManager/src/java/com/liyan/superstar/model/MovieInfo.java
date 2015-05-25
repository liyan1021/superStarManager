package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 * 影视管理
 */
@Entity
@Table(name="vod_store_movie")
public class MovieInfo implements Serializable{
	
	@Id
	@Column(name="movie_id")	
	private String movie_id ;
	
	@Column(name="movie_name")	
	private String movie_name ;
	
	@Column(name="movie_info")	
	private String movie_info ;
	
	@Column(name="row_num")	
	private String row_num ;
	
	@Column(name="column_num")	
	private String column_num;
	
	@Column(name="sort")	
	private String sort ;
	
	@Column(name="file_path")	
	private String file_path ; 
	
	@Column(name="absolute_path")
	private String absolute_path ;
	@Column(name="state")	
	private String state;

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

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(String movie_id) {
		this.movie_id = movie_id;
	}

	public String getMovie_name() {
		return movie_name;
	}

	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	public String getMovie_info() {
		return movie_info;
	}

	public void setMovie_info(String movie_info) {
		this.movie_info = movie_info;
	}

	public String getRow_num() {
		return row_num;
	}

	public void setRow_num(String row_num) {
		this.row_num = row_num;
	}

	public String getColumn_num() {
		return column_num;
	}

	public void setColumn_num(String column_num) {
		this.column_num = column_num;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAbsolute_path() {
		return absolute_path;
	}

	public void setAbsolute_path(String absolute_path) {
		this.absolute_path = absolute_path;
	} 
	
}
