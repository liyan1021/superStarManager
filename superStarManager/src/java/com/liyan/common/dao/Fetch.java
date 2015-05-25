package com.liyan.common.dao;

public class Fetch {

	public static final int JOINTYPE_INNER = 0;
    public static final int JOINTYPE_LEFT = 1;
    public static final int JOINTYPE_RIGHT = 2;

    private String targetObjectName;

    private int joinType;

    private Fetch(String targetObjectName, int joinType) {
        this.targetObjectName = targetObjectName;
        this.joinType = joinType;
    }
    
    public static Fetch innerFetch(String targetObjectName) {
        return new Fetch(targetObjectName, JOINTYPE_INNER);
    }
    
    public static Fetch leftFetch(String targetObjectName) {
        return new Fetch(targetObjectName, JOINTYPE_LEFT);
    }

    public static Fetch rightFetch(String targetObjectName) {
        return new Fetch(targetObjectName, JOINTYPE_RIGHT);
    }
    
	public String getTargetObjectName() {
		return targetObjectName;
	}

	public void setTargetObjectName(String targetObjectName) {
		this.targetObjectName = targetObjectName;
	}

	public int getJoinType() {
		return joinType;
	}

	public void setJoinType(int joinType) {
		this.joinType = joinType;
	}
   
}
