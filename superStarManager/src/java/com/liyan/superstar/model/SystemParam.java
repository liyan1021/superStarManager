package com.liyan.superstar.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 *系统参数
 */
@Entity
@Table(name="vod_store_param")
public class SystemParam implements Serializable {
	@Id
	@Column(name="param_type")
	private String param_type ;

	@Column(name="param_name")
	private String param_name ; 

	@Column(name="param_value")
	private String param_value ; 

	@Column(name="device_type")
	private String device_type ;
	
	@Column(name="update_date")
	private String update_date ;

	public String getParam_type() {
		return param_type;
	}
	public void setParam_type(String param_type) {
		this.param_type = param_type;
	}

	public String getParam_name() {
		return param_name;
	}
	public void setParam_name(String param_name) {
		this.param_name = param_name;
	}
	public String getParam_value() {
		return param_value;
	}
	public void setParam_value(String param_value) {
		this.param_value = param_value;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	} 
	
	
}
