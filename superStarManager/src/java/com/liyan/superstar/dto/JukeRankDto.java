package com.liyan.superstar.dto;

public class JukeRankDto {
	
	
	private String music_id ; 
	private String music_name ; 
	private String singer_id ;
	private String singer_name ;
	private Long juke_number ;
	
	public JukeRankDto(){
		
	}
	public JukeRankDto(String music_id, String music_name, String singer_name,
			Long juke_number) {
		super();
		this.music_id = music_id;
		this.music_name = music_name;
		this.singer_name = singer_name;
		this.juke_number = juke_number;
	}
	public String getMusic_id() {
		return music_id;
	}
	public void setMusic_id(String music_id) {
		this.music_id = music_id;
	}
	public String getMusic_name() {
		return music_name;
	}
	public void setMusic_name(String music_name) {
		this.music_name = music_name;
	}

	public Long getJuke_number() {
		return juke_number;
	}
	public void setJuke_number(Long juke_number) {
		this.juke_number = juke_number;
	}
	public String getSinger_id() {
		return singer_id;
	}
	public void setSinger_id(String singer_id) {
		this.singer_id = singer_id;
	}
	public String getSinger_name() {
		return singer_name;
	}
	public void setSinger_name(String singer_name) {
		this.singer_name = singer_name;
	} 
	
	
}
