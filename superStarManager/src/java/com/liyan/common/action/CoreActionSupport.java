package com.liyan.common.action;


import com.opensymphony.xwork2.ActionSupport;

public class CoreActionSupport extends ActionSupport {

    private static final long serialVersionUID = 4973826815763525962L;

    protected String jsonMessage;
    
    public String getJsonMessage() {
		return jsonMessage;
	}

	public void setJsonMessage(String jsonMessage) {
		this.jsonMessage = jsonMessage;
	}


}
