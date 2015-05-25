package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Administrator
 *字典
 */
@Entity
@Table(name = "vod_store_dict")
public class Dictionary implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@Column (name="id")
	private String id;
	@Column(name="dict_type_code")
	private String dict_type_code ; 
	
	@Column (name="dict_type")
	private String dict_type;
	
	@Column (name="dict_code")
	private String dict_code;
	
	@Column (name="dict_value")
	private String dict_value;

	@Column (name="import_time")
	private String import_time;
	
	@Column(name="file_path")
	private String file_path ; 
	
	@Column(name="dict_sort")
	private String dict_sort ; 
	
	@Column(name="is_activity")
	private String is_activity ;
	
	public String getDict_type() {
		return dict_type;
	}

	public void setDict_type(String dict_type) {
		this.dict_type = dict_type;
	}

	public String getDict_code() {
		return dict_code;
	}

	public void setDict_code(String dict_code) {
		this.dict_code = dict_code;
	}

	public String getDict_value() {
		return dict_value;
	}

	public void setDict_value(String dict_value) {
		this.dict_value = dict_value;
	}

	public String getImport_time() {
		return import_time;
	}

	public void setImport_time(String import_time) {
		this.import_time = import_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getDict_type_code() {
		return dict_type_code;
	}

	public void setDict_type_code(String dict_type_code) {
		this.dict_type_code = dict_type_code;
	}

	public String getDict_sort() {
		return dict_sort;
	}

	public void setDict_sort(String dict_sort) {
		this.dict_sort = dict_sort;
	}

	public String getIs_activity() {
		return is_activity;
	}

	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}

	
}
