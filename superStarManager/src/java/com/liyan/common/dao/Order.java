package com.liyan.common.dao;

public class Order {

    public static final String ORDER_ASC = "ASC";
    public static final String ORDER_DESC = "DESC";

    private String paramName;

    private String order;

    public Order(String paramName, String order) {
        this.paramName = paramName;
        this.order = order;
    }

    public static Order asc(String paramName) {
        return new Order(paramName, ORDER_ASC);
    }

    public static Order desc(String paramName) {
        return new Order(paramName, ORDER_DESC);
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
