package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 * 包房信息
 */
@Entity
@Table(name="VOD_STORE_ROOM_INFO")
public class Room  implements Serializable{
	
	@Id
	@Column(name="oid")
	private String oid ;
	
	@Column(name="room_no")
	private String room_no ; 
	
	@Column(name="ip_add")
	private String ip_add ; 
	
	@Column(name="device_id")
	private String device_id ;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getRoom_no() {
		return room_no;
	}

	public void setRoom_no(String room_no) {
		this.room_no = room_no;
	}

	public String getIp_add() {
		return ip_add;
	}

	public void setIp_add(String ip_add) {
		this.ip_add = ip_add;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	} 
	
	
	
}
