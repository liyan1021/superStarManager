package com.liyan.superstar.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * @author Administrator
 *操作日志
 */
@Entity
@Table(name="vod_store_operate_log")
public class Operation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2233284948954622131L;

	
	@Id
    @Column(name="OP_LOG_ID")
	private String op_log_id ; 
   
    @Column(name="USER_ID")
	private String user_id  ;
    
    @Column(name="USER_NAME")
	private String user_name ;
   
    @Column(name="OP_ORIGIN")
	private String op_origin ; 
   
    @Column(name="OP_FUNC_ID")
	private String op_func_id ;
   
    @Column(name="OP_FUNC")
	private String op_func ;
  
    @Column(name="OP_TYPE")
	private String op_type ;
   
    @Column(name="OP_TIME")
	private String op_time ; 
   
    @Column(name="STORE_CODE")
	private String store_code ; 
   
    @Column(name="ROOM_CODE")
	private String room_code ;

   public String getOp_log_id() {
		return op_log_id;
	}
	public void setOp_log_id(String op_log_id) {
		this.op_log_id = op_log_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getOp_origin() {
		return op_origin;
	}
	public void setOp_origin(String op_origin) {
		this.op_origin = op_origin;
	}
	public String getOp_func_id() {
		return op_func_id;
	}
	public void setOp_func_id(String op_func_id) {
		this.op_func_id = op_func_id;
	}
	public String getOp_func() {
		return op_func;
	}
	public void setOp_func(String op_func) {
		this.op_func = op_func;
	}
	public String getOp_type() {
		return op_type;
	}
	public void setOp_type(String op_type) {
		this.op_type = op_type;
	}
	public String getOp_time() {
		return op_time;
	}
	public void setOp_time(String op_time) {
		this.op_time = op_time;
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
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
}
