package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 *会员
 */
@Entity
@Table(name="vod_store_members")
public class Members  implements Serializable{
	
	@Id
	@Column(name="MEMBER_ID")
	private String member_id ; 
	
	@Column(name="MEMBER_NAME")
	private String member_name ; 
	
	@Column(name="MEMBER_CODE")
	private String member_code ; 
	
	@Column(name="MEMBER_PWD")
	private String member_pwd ; 
	
	@Column(name="MEMBER_TEL")
	private String member_tel ;
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_code() {
		return member_code;
	}
	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}
	public String getMember_pwd() {
		return member_pwd;
	}
	public void setMember_pwd(String member_pwd) {
		this.member_pwd = member_pwd;
	}
	public String getMember_tel() {
		return member_tel;
	}
	public void setMember_tel(String member_tel) {
		this.member_tel = member_tel;
	} 
	
	
}
