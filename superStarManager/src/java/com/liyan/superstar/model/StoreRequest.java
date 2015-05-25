package com.liyan.superstar.model;

import com.liyan.common.util.Base64;
import com.liyan.common.util.Param;

import net.sf.json.JSONObject;


public class StoreRequest {

	private String roomNo;
	private String type;
	private Object data;
	
	public StoreRequest(String roomNo, String type, Object data) {
		this.roomNo = roomNo;
		this.type = type;
		this.data = data;
	}
	
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public String toJsonString(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("roomNo", roomNo);
		jsonObject.put("type", type);
		jsonObject.put("data", data);
		String str = jsonObject.toString();
		return str;
	}
	
	public String toEncodeString(){
		String str = toJsonString();
		return Base64.encode(str.getBytes(Param.CHARSET_UTF8));
	}
}
