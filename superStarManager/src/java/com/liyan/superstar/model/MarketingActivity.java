package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 * 营销活动
 */
@Entity
@Table(name="VOD_STORE_ACTIVITY")
public class MarketingActivity implements Serializable{
	
	@Id
	@Column(name="activity_id")
	private String activity_id ;
	
	@Column(name="activity_name")
	private String activity_name ;
	
	@Column(name="activity_introduce")
	private String activity_introduce ; 
	
	@Column(name="activity_path_big")
	private String activity_path_1;
	
	@Column(name="activity_path_small")
	private String activity_path_2 ;
	
	@Column(name="absolute_path_big")
	private String absolute_path_1 ;
	
	@Column(name="absolute_path_small")
	private String absolute_path_2 ; 
	
	@Column(name="activity_sort")
	private String activity_sort ;
	
	@Column(name="activity_time")
	private String activity_time ;
	
	@Column(name="create_time")
	private String create_time ;
	
	@Column(name="update_time")
	private String update_time ;
	
	@Column(name="is_activity")
	private String is_activity ;
	public String getIs_activity() {
		return is_activity;
	}

	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}

	@Column(name="state")
	private String state; 
	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	public String getActivity_name() {
		return activity_name;
	}

	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}

	public String getActivity_introduce() {
		return activity_introduce;
	}

	public void setActivity_introduce(String activity_introduce) {
		this.activity_introduce = activity_introduce;
	}

	public String getActivity_path_1() {
		return activity_path_1;
	}

	public void setActivity_path_1(String activity_path_1) {
		this.activity_path_1 = activity_path_1;
	}

	public String getActivity_path_2() {
		return activity_path_2;
	}

	public void setActivity_path_2(String activity_path_2) {
		this.activity_path_2 = activity_path_2;
	}

	public String getAbsolute_path_1() {
		return absolute_path_1;
	}

	public void setAbsolute_path_1(String absolute_path_1) {
		this.absolute_path_1 = absolute_path_1;
	}

	public String getAbsolute_path_2() {
		return absolute_path_2;
	}

	public void setAbsolute_path_2(String absolute_path_2) {
		this.absolute_path_2 = absolute_path_2;
	}

	public String getActivity_sort() {
		return activity_sort;
	}

	public void setActivity_sort(String activity_sort) {
		this.activity_sort = activity_sort;
	}

	public String getActivity_time() {
		return activity_time;
	}

	public void setActivity_time(String activity_time) {
		this.activity_time = activity_time;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
