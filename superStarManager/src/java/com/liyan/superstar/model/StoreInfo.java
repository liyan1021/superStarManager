package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator 门店管理
 */
@Entity
@Table(name = "vod_store_information")
public class StoreInfo implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STORE_ID")
	private String store_id;

	@Column(name = "store_code")
	private String store_code;
	
	@Column(name = "store_name")
	private String store_name;
	
	@Column(name = "store_address")
	private String store_address;

	@Column(name = " is_activity ")
	private String is_activity;

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getStore_address() {
		return store_address;
	}

	public void setStore_address(String store_address) {
		this.store_address = store_address;
	}

	public String getIs_activity() {
		return is_activity;
	}

	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}

}
