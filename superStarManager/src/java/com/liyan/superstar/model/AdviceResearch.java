package com.liyan.superstar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Administrator
 * 意见调查
 */
@Entity
@Table(name="vod_store_advice_research")
public class AdviceResearch implements Serializable {
	
	@Id
	@Column(name="advice_id")
	private String advice_id;
	
	@ManyToOne
	@JoinColumn(name="type_id")
	private ResearchType researchType; 
	
	@Column(name="advice_score")
	private String advice_score ;
	
	@Column(name="advice_content")
	private String advice_content ;
	
	@Column(name="member_id")
	private String member_id ; 
	
	@Column(name="room")
	private String room ;
	
	@Column(name="store")
	private String store ;
	
	@Column(name="advice_time")
	private String advice_time ;
	
	public String getAdvice_id() {
		return advice_id;
	}
	public void setAdvice_id(String advice_id) {
		this.advice_id = advice_id;
	}

	public ResearchType getResearchType() {
		return researchType;
	}
	public void setResearchType(ResearchType researchType) {
		this.researchType = researchType;
	}
	public String getAdvice_score() {
		return advice_score;
	}
	public void setAdvice_score(String advice_score) {
		this.advice_score = advice_score;
	}
	public String getAdvice_content() {
		return advice_content;
	}
	public void setAdvice_content(String advice_content) {
		this.advice_content = advice_content;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getAdvice_time() {
		return advice_time;
	}
	public void setAdvice_time(String advice_time) {
		this.advice_time = advice_time;
	} 
	
}
