package com.liyan.common.dao;

public class QueryParamEntry {

    private String paramName;
    
    private String queryCase;
    
    private Object value;

    public QueryParamEntry() {
    }

    public QueryParamEntry(String paramName, String queryCase, Object value) {
        this.paramName = paramName;
        this.queryCase = queryCase;
        this.value = value;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

	public String getQueryCase() {
		return queryCase;
	}

	public void setQueryCase(String queryCase) {
		this.queryCase = queryCase;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
    
}
