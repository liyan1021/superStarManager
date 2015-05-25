package com.liyan.superstar.service;

import java.util.List;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.SystemParam;

public interface SystemParamService {

	Pagination<SystemParam> query(SystemParam sysParam, Integer page,
			Integer rows, String sort, String order);

	MsgBean saveSysParam(SystemParam sysParam);

	MsgBean removeSysParam(List<String> list);

	Pagination<SystemParam> search(SystemParam sysParam, Integer page,
			Integer rows, String sort, String order);

	MsgBean editSysParam(SystemParam sysParam);

}
