package com.liyan.superstar.dao;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.SystemParam;

@Repository
public class SystemParamDao extends GenericDaoImpl<SystemParam, String> {

	public Pagination<SystemParam> query(SystemParam sysParam, Integer page,
			Integer rows, String sort, String order) {
		QueryCriteria query = new QueryCriteria();
		if(sysParam!=null){
			if(!CommonUtils.isEmpty(sysParam.getParam_name())){
				query.addEntry("param_name", "like", "%"+sysParam.getParam_name()+"%");
			}
			if(!CommonUtils.isEmpty(sysParam.getDevice_type()) && !sysParam.getDevice_type().equals("0")){
				query.addEntry("device_type", "=", sysParam.getDevice_type());
			}
		}
		if(!CommonUtils.isEmpty(sort)){
			if(order.equals("asc")){
				query.asc(sort);
			}else if(order.equals("desc")){
				query.desc(sort);
			}
		}
		return this.findPage(query, page, rows) ;
	}
	
}
