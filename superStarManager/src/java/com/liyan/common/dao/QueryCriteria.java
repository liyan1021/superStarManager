package com.liyan.common.dao;

import java.util.ArrayList;
import java.util.List;

public class QueryCriteria {

    private List<QueryParamEntry> entries = new ArrayList<QueryParamEntry>();

    private List<Fetch> fetchList = new ArrayList<Fetch>();

    private List<Order> orderList = new ArrayList<Order>();
    
    private String whereClause;
    private List<Object> whereClauseValues = new ArrayList<Object>();
    
    private Integer rowCount;

    public List<QueryParamEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<QueryParamEntry> entries) {
        this.entries = entries;
    }

    public QueryCriteria addEntry(String paramName, String queryCase, Object value) {
        QueryParamEntry queryParamEntry = new QueryParamEntry(paramName, queryCase, value);
        this.entries.add(queryParamEntry);
        return this;
    }

    public List<Fetch> getFetchList() {
        return fetchList;
    }

    public QueryCriteria innerFetch(String targetObjectName) {
        this.fetchList.add(Fetch.innerFetch(targetObjectName));
        return this;
    }
    
    public QueryCriteria leftFetch(String targetObjectName) {
        this.fetchList.add(Fetch.leftFetch(targetObjectName));
        return this;
    }
    
    public QueryCriteria rightFetch(String targetObjectName) {
        this.fetchList.add(Fetch.rightFetch(targetObjectName));
        return this;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public QueryCriteria asc(String paramName) {
        this.orderList.add(Order.asc(paramName));
        return this;
    }
    
    public QueryCriteria desc(String paramName) {
        this.orderList.add(Order.desc(paramName));
        return this;
    }
    
    public QueryCriteria order(String paramName, String order) {
        this.orderList.add(new Order(paramName, order));
        return this;
    }
    
    public Integer getRowCount() {
		return rowCount;
	}

	public QueryCriteria rowCount(Integer rowCount) {
		this.rowCount = rowCount;
		return this;
	}
	
	public QueryCriteria where(String whereClause, Object... values) {
        this.whereClause = whereClause;
        whereClauseValues.clear();
        for (Object value : values) {
        	whereClauseValues.add(value);
		}
        return this;
    }
	
	public String getWhereClause() {
		return whereClause;
	}
	
	public boolean hasWhereClause() {
		return whereClause != null && whereClause.length() > 0;
	}

	public List<Object> getWhereClauseValues() {
		return this.whereClauseValues;
	}
	
    public QueryCriteria clear() {
        this.entries.clear();
        this.fetchList.clear();
        this.orderList.clear();
        this.whereClause = null;
        this.whereClauseValues.clear();
        return this;
    }
}
