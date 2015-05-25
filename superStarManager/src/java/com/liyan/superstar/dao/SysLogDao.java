package com.liyan.superstar.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.liyan.superstar.model.SysLog;
import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
@Repository
public class SysLogDao extends GenericDaoImpl<SysLog, String>{

	public Pagination<SysLog> query(SysLog sysLog,String start_time,String end_time, Integer page, Integer rows) {
		QueryCriteria query = new QueryCriteria();
		
		if(sysLog!=null){
			
			if(!CommonUtils.isEmpty(sysLog.getDevice_name())){
				query.addEntry("device_name", "like", "%" + sysLog.getDevice_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(sysLog.getRun_state()) && !sysLog.getRun_state().equals("0")){
				query.addEntry("run_state", "=",  sysLog.getRun_state());
				
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(start_time))){
				query.addEntry("rec_time", ">=", start_time + " 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(end_time))){
				query.addEntry("rec_time", "<=", end_time+ " 23:59:59");
			}
			
			if(!CommonUtils.isEmpty(sysLog.getStore_code())){
				query.addEntry("opt_desc", "like", "%" + sysLog.getStore_code() + "%");
				
			}
			if(!CommonUtils.isEmpty(sysLog.getRoom_code())){
				query.addEntry("opt_desc", "like", "%" + sysLog.getRoom_code() + "%");
				
			}
		}
		query.desc("rec_time");

		
		return this.findPage(query, page, rows);
	}

	public ArrayList<SysLog> query(SysLog sysLog, String start_time,
			String end_time) {
		QueryCriteria query = new QueryCriteria();
		
		if(sysLog!=null){
			
			if(!CommonUtils.isEmpty(sysLog.getDevice_name())){
				query.addEntry("device_name", "like", "%" + sysLog.getDevice_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(sysLog.getRun_state()) && !sysLog.getRun_state().equals("0")){
				query.addEntry("run_state", "=",  sysLog.getRun_state());
				
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(start_time))){
				query.addEntry("rec_time", ">=", start_time);
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(end_time))){
				query.addEntry("rec_time", "<=", end_time);
			}
			
			if(!CommonUtils.isEmpty(sysLog.getStore_code())){
				query.addEntry("opt_desc", "like", "%" + sysLog.getStore_code() + "%");
				
			}
			if(!CommonUtils.isEmpty(sysLog.getRoom_code())){
				query.addEntry("opt_desc", "like", "%" + sysLog.getRoom_code() + "%");
				
			}
		}
		query.desc("rec_time");

		
		return (ArrayList<SysLog>) this.find(query);
	}

}
