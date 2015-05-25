package com.liyan.superstar.service;

import com.liyan.superstar.model.User;

public interface OperationLogService {

	/**
	 * 存储日志
	 * @param currentUser
	 * @param origin
	 * @param function
	 * @param type
	 * @param opt_time
	 * @param store
	 * @param room
	 */
	public void setLog(User currentUser, String origin, String function,
			String type, String opt_time, String store, String room);

	/**
	 * 存储日志
	 * @param currentUser
	 * @param function
	 * @param type
	 */
	public void setLog(User currentUser, String function, String type);

	
	
}
