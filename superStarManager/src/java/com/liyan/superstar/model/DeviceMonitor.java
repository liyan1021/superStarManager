package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 *  设备监控 
 */
@Entity
@Table(name="vod_store_device_monitor")
public class DeviceMonitor implements Serializable{
	
	@Id
	@Column(name="monitor_id")
	private String monitor_id ; 

	@Column(name="device_id")
	private String device_id ; 
	
	@Column(name="device_name")
	private String device_name ;
	
	@Column(name="net_state")
	private String net_state; 
	
	@Column(name="device_state")
	private String device_state;

	@Column(name="room_state")
	private String room_state ;

	@Column(name="store")
	private String store ; 
	
	@Column(name="room")
	private String room ;
	
	public String getMonitor_id() {
		return monitor_id;
	}
	public void setMonitor_id(String monitor_id) {
		this.monitor_id = monitor_id;
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
	public String getNet_state() {
		return net_state;
	}
	public void setNet_state(String net_state) {
		this.net_state = net_state;
	}
	public String getDevice_state() {
		return device_state;
	}
	public void setDevice_state(String device_state) {
		this.device_state = device_state;
	}
	public String getRoom_state() {
		return room_state;
	}
	public void setRoom_state(String room_state) {
		this.room_state = room_state;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	} 
	
}
