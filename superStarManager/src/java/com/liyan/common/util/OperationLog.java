package com.liyan.common.util;

import java.util.UUID;

import com.liyan.superstar.dao.OperationDao;
import com.liyan.superstar.model.Operation;
import com.liyan.superstar.model.User;

public class OperationLog {

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
			String type, String opt_time, String store, String room) {
		OperationDao operationDao = new OperationDao();
		Operation opt = new Operation();
		opt.setOp_log_id(UUID.randomUUID().toString());
		opt.setOp_func(function);
		opt.setUser_id(currentUser.getUser_id());
		opt.setUser_name(currentUser.getUser_name());
		opt.setOp_origin(origin);
		opt.setOp_func(function);
		opt.setOp_type(type);
		opt.setOp_time(opt_time);
		opt.setStore_code(store);
		opt.setRoom_code(room);
		operationDao.create(opt);
	}
	
	
}
