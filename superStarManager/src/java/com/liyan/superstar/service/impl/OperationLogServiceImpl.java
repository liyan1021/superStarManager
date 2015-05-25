package com.liyan.superstar.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyan.common.AppContext;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.dao.OperationDao;
import com.liyan.superstar.model.Operation;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
@Service
@Transactional
public class OperationLogServiceImpl implements OperationLogService{
	@Autowired
	private OperationDao optDao ;
	
	@Override
	public void setLog(User currentUser, String origin, String function,
			String type, String opt_time, String store, String room) {
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
		optDao.create(opt);
	}
	
	@Override
	public void setLog(User currentUser, String function, String type) {
		Operation opt = new Operation();
		opt.setOp_log_id(UUID.randomUUID().toString());
		opt.setOp_func(function);
		opt.setUser_id(currentUser.getUser_id());
		opt.setUser_name(currentUser.getUser_name());
		opt.setOp_origin("平台");
		opt.setOp_func(function);
		opt.setOp_type(type);
		opt.setOp_time(CommonUtils.currentDateTimeString());
		opt.setStore_code(AppContext.getInstance().getString("key.store"));
		opt.setRoom_code("");
		optDao.create(opt);
	}

	public OperationDao getOptDao() {
		return optDao;
	}
	public void setOptDao(OperationDao optDao) {
		this.optDao = optDao;
	}
}

