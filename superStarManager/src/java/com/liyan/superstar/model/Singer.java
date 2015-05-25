package com.liyan.superstar.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.liyan.common.util.CommonUtils;


/**
* singer 实体类
* 2014-03-07 16:58:26
* create by GenerateJavaBeanUtil 
* 歌星
*/ 
@Entity
@Table(name="vod_store_singer")
public class Singer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="STAR_ID")
	private String star_id;
	
	@Column(name="STAR_NAME")
	private String star_name;
	
	@Column(name="other_name")
	private String other_name ; 
	
	@Column(name="spell_first_letter_abbreviation")
	private String spell_first_letter_abbreviation;
	
	@Column(name="star_type")
	private String star_type ; 
	
	@Column(name="star_head")
	private String star_head ; 
	
	@Column(name="star_head_absolute")
	private String star_head_absolute ; 
	
	@Column(name="area")
	private String area ; 
	
	@Column(name="click_number")
	private String click_number  ; 
	
	@Column(name="remark")
	private String remark ;
	
	@Column(name="IS_ACTIVITY")
	private String is_activity ; 
	
	
	public String getStar_id() {
		return star_id;
	}
	public void setStar_id(String star_id) {
		this.star_id = star_id;
	}
	public String getStar_name() {
		return star_name;
	}
	public void setStar_name(String star_name) {
		
		this.star_name = CommonUtils.nullToBlank(star_name).trim();
	}
	public String getOther_name() {
		return other_name;
	}
	public void setOther_name(String other_name) {
		this.other_name = CommonUtils.nullToBlank(other_name).trim();
	}
	public String getSpell_first_letter_abbreviation() {
		return spell_first_letter_abbreviation;
	}
	public void setSpell_first_letter_abbreviation(
			String spell_first_letter_abbreviation) {
		this.spell_first_letter_abbreviation = CommonUtils.nullToBlank(spell_first_letter_abbreviation).trim();
	}
	public String getStar_type() {
		return star_type;
	}
	public void setStar_type(String star_type) {
		this.star_type = CommonUtils.nullToBlank(star_type).trim();
	}
	public String getStar_head() {
		return star_head;
	}
	public void setStar_head(String star_head) {
		this.star_head = CommonUtils.nullToBlank(star_head).trim();
	}
	public String getStar_head_absolute() {
		return star_head_absolute;
	}
	public void setStar_head_absolute(String star_head_absolute) {
		this.star_head_absolute = CommonUtils.nullToBlank(star_head_absolute).trim();
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getClick_number() {
		return click_number;
	}
	public void setClick_number(String click_number) {
		this.click_number = CommonUtils.nullToBlank(click_number).trim();
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = CommonUtils.nullToBlank(remark).trim();
	}
	public String getIs_activity() {
		return is_activity;
	}
	public void setIs_activity(String is_activity) {
		this.is_activity = CommonUtils.nullToBlank(is_activity).trim();
	}
	public Singer(String star_id, String star_name, String other_name,
			String spell_first_letter_abbreviation, String star_type,
			String star_head, String star_head_absolute, String area,
			String click_number, String remark, String is_activity) {
		super();
		this.star_id = star_id;
		this.star_name = star_name;
		this.other_name = other_name;
		this.spell_first_letter_abbreviation = spell_first_letter_abbreviation;
		this.star_type = star_type;
		this.star_head = star_head;
		this.star_head_absolute = star_head_absolute;
		this.area = area;
		this.click_number = click_number;
		this.remark = remark;
		this.is_activity = is_activity;
	}
	public Singer() {
		super();
		// TODO Auto-generated constructor stub
	}

}
