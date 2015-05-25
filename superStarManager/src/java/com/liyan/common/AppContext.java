package com.liyan.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取 ActionContext实例
 * @author Administrator
 *
 */
public class AppContext {

    private static final AppContext context = new AppContext();

    private Map<String, Object> contextMap = new HashMap<String, Object>();

    private AppContext() {
    }

    public static AppContext getInstance() {
        return context;
    }

    public Object get(String key) {
        return this.contextMap.get(key);
    }

    public void put(String key, Object value) {
        this.contextMap.put(key, value);
    }

    public String getString(String key) {
        return String.valueOf(this.contextMap.get(key));
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

}
