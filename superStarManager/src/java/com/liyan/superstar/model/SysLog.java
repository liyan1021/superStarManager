package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 * 设备日志
 */
@Entity
@Table(name="sys_log")
public class SysLog  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="SYS_LOG_ID")
	private String sys_log_id ; 
	
	@Column(name="DEVICE_ID")
	private String device_id ;
	
	@Column(name="DEVICE_NAME")
	private String device_name ; 
	
	@Column(name="RUN_STATE")
	private String run_state ; 
	
	@Column(name="REC_TIME")
	private String rec_time ; 
	
	@Column(name="STORE_CODE")
	private String store_code ; 
	
	@Column(name="ROOM_CODE")
	private String room_code ;
	
	public String getSys_log_id() {
		return sys_log_id;
	}
	public void setSys_log_id(String sys_log_id) {
		this.sys_log_id = sys_log_id;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getRun_state() {
		return run_state;
	}
	public void setRun_state(String run_state) {
		this.run_state = run_state;
	}
	public String getRec_time() {
		return rec_time;
	}
	public void setRec_time(String rec_time) {
		this.rec_time = rec_time;
	}
	public String getStore_code() {
		return store_code;
	}
	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}
	public String getRoom_code() {
		return room_code;
	}
	public void setRoom_code(String room_code) {
		this.room_code = room_code;
	} 
	
}
