package com.liyan.superstar.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 * 意见调查类型
 */
@Entity
@Table(name="vod_store_research_type")
public class ResearchType implements Serializable {
	
	@Id
	@Column(name="question_id")
	private String question_id ;
	
	@Column(name="question_name")
	private String question_name;
	
	@Column(name="store")
	private String store ; 
	
	@Column(name="question_type")
	private String question_type ;
	
	@Column(name="question_score")
	private Integer question_score ; 
	
	@Column(name="option_a")
	private String option_a ; 
	
	@Column(name="option_a_score")
	private Integer option_a_score ;
	
	@Column(name="option_b")
	private String option_b;
	
	@Column(name="option_b_score")
	private Integer option_b_score ;
	
	@Column(name="option_c")
	private String option_c ;
	
	@Column(name="option_c_score")
	private Integer option_c_score ; 
	
	@Column(name="option_d")
	private String option_d;
	
	@Column(name="option_d_score")
	private Integer option_d_score ; 
	
	@Column(name="option_e")
	private String option_e; 
	
	@Column(name="option_e_score")
	private Integer option_e_score; 
	
	@Column(name="option_f")
	private String option_f ;
	
	@Column(name="option_f_score")
	private Integer option_f_score ;
	
	@Column(name="create_time")
	private String create_time ;
	
	@Column(name="is_activity")
	private String is_activity ; 
	
	@Column(name="update_time")
	private String update_time ; 
	
	public String getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}

	public String getQuestion_name() {
		return question_name;
	}

	public void setQuestion_name(String question_name) {
		this.question_name = question_name;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getQuestion_type() {
		return question_type;
	}

	public void setQuestion_type(String question_type) {
		this.question_type = question_type;
	}

	public Integer getQuestion_score() {
		return question_score;
	}

	public void setQuestion_score(Integer question_score) {
		this.question_score = question_score;
	}

	public String getOption_a() {
		return option_a;
	}

	public void setOption_a(String option_a) {
		this.option_a = option_a;
	}

	public Integer getOption_a_score() {
		return option_a_score;
	}

	public void setOption_a_score(Integer option_a_score) {
		this.option_a_score = option_a_score;
	}

	public String getOption_b() {
		return option_b;
	}

	public void setOption_b(String option_b) {
		this.option_b = option_b;
	}

	public Integer getOption_b_score() {
		return option_b_score;
	}

	public void setOption_b_score(Integer option_b_score) {
		this.option_b_score = option_b_score;
	}

	public String getOption_c() {
		return option_c;
	}

	public void setOption_c(String option_c) {
		this.option_c = option_c;
	}

	public Integer getOption_c_score() {
		return option_c_score;
	}

	public void setOption_c_score(Integer option_c_score) {
		this.option_c_score = option_c_score;
	}

	public String getOption_d() {
		return option_d;
	}

	public void setOption_d(String option_d) {
		this.option_d = option_d;
	}

	public Integer getOption_d_score() {
		return option_d_score;
	}

	public void setOption_d_score(Integer option_d_score) {
		this.option_d_score = option_d_score;
	}

	public String getOption_e() {
		return option_e;
	}

	public void setOption_e(String option_e) {
		this.option_e = option_e;
	}

	public Integer getOption_e_score() {
		return option_e_score;
	}

	public void setOption_e_score(Integer option_e_score) {
		this.option_e_score = option_e_score;
	}

	public String getOption_f() {
		return option_f;
	}

	public void setOption_f(String option_f) {
		this.option_f = option_f;
	}

	public Integer getOption_f_score() {
		return option_f_score;
	}

	public void setOption_f_score(Integer option_f_score) {
		this.option_f_score = option_f_score;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
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
