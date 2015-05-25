package com.liyan.superstar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="vod_store_singer_introduce")
public class MainStar implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3468657576156395668L;
	@Id
	@Column(name="INTRODUCE_ID")
	private String introduce_id ; 

	@JoinColumn(name="SINGER_ID")
	@ManyToOne
	private Singer singer ;

	@Column(name="SINGER_NAME")
	private String singer_name ;
	
	
	@Column(name="STATE")
	private String state ;  
	
	
	@Column(name="INTRODUCE_TIME")
	private String introduce_time ;
	
	
	@Column(name="INTRODUCE_SORT")
	private String introduce_sort ; 
	
	
	@Column(name="REMARK")
	private String remark ;
	
	@Column(name="INTRODUCE_TYPE")
	private String introduce_type ;

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


	public Singer getSinger() {
		return singer;
	}


	public void setSinger(Singer singer) {
		this.singer = singer;
	}

	public String getSinger_name() {
		return singer_name;
	}


	public void setSinger_name(String singer_name) {
		this.singer_name = singer_name;
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
	public String getIntroduce_type() {
		return introduce_type;
	}


	public void setIntroduce_type(String introduce_type) {
		this.introduce_type = introduce_type;
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
