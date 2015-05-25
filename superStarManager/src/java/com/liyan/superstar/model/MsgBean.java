package com.liyan.superstar.model;

import com.liyan.common.util.Base64;
import com.liyan.common.util.Param;

import net.sf.json.JSONObject;

public class MsgBean {
	
	private String action;								//处理的Action字符串
	private boolean resultFlag;							//简单对错处理结果
	private String msg;									//处理结果提示信息
	private String showType;							//处理结果显示方式
	private Object resultObject;						//处理结果对象，存放复杂处理结果
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public boolean isResultFlag() {
		return resultFlag;
	}
	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public Object getResultObject() {
		return resultObject;
	}
	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}
	
	public String toEncodeJsonString(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", action);
		jsonObject.put("resultFlag", resultFlag);
		jsonObject.put("msg", msg);
		jsonObject.put("showType", showType);
		jsonObject.put("resultObject", resultObject);
		String str = jsonObject.toString();
		return Base64.encode(str.getBytes(Param.CHARSET_UTF8));
	}
	
}
