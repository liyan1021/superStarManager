package com.liyan.superstar.model;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Administrator
 * 用户
 */
@Entity
@Table(name ="vod_store_user")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID")
	private String user_id ;
	
	@Column(name="USER_NAME")
	private String user_name ;
	
	@Column(name="USER_CODE")
	private String user_code ;
	
	@Column(name="USER_PWD")
	private String user_pwd ;
	
	@Column(name="TEL_NUM")
	private String tel_num ; 
	
	@Column(name="OFFI_NAME")
	private String offi_name;
	
	@Column(name="ROLE_ID")
	private String role_id ;
	
	@Column(name="ORG_CODE")
	private String org_code;
	
	@Column(name="ORG_NAME")
	private String org_name;
	
	@Column(name="CREATE_TIME")
	private String create_time ; 
	
	@Column(name="MODIFY_TIME")
	private String modify_time ;
	
	@Column(name="is_activity")
	private String is_activity ; 
	
	public String getTel_num() {
		return tel_num;
	}
	public void setTel_num(String tel_num) {
		this.tel_num = tel_num;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getOffi_name() {
		return offi_name;
	}
	public void setOffi_name(String offi_name) {
		this.offi_name = offi_name;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getIs_activity() {
		return is_activity;
	}
	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}
}
