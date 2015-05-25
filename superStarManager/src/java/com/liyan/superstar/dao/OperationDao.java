package com.liyan.superstar.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.liyan.superstar.model.Operation;
import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
@Repository
public class OperationDao extends GenericDaoImpl<Operation, String> {

	public Pagination<Operation> query(Operation opt,String start_time,String end_time, Integer page, Integer rows) {

		QueryCriteria query = new QueryCriteria();
		if(opt!=null){
			
			if(!CommonUtils.isEmpty(opt.getUser_name())){
				query.addEntry("user_name", "like", "%" + opt.getUser_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(opt.getOp_origin())){
				query.addEntry("op_origin", "like", "%" + opt.getOp_origin() + "%");
			}
			if(!CommonUtils.isEmpty(opt.getOp_func())){
				query.addEntry("op_func", "like", "%" + opt.getOp_func() + "%");
			}
			if(!CommonUtils.isEmpty(opt.getOp_type()) && !opt.getOp_type().equals("0")){
				query.addEntry("op_type", "=",  opt.getOp_type());
				
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(start_time))){
				query.addEntry("op_time", ">=", start_time +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(end_time))){
				query.addEntry("op_time", "<=", end_time +" 23:59:59");
			}
			if(!CommonUtils.isEmpty(opt.getStore_code())){
				query.addEntry("store_code", "like", "%" + opt.getStore_code() + "%");
			}
			if(!CommonUtils.isEmpty(opt.getRoom_code())){
				query.addEntry("room_code", "like", "%" + opt.getRoom_code() + "%");
			}
		}
		query.desc("op_time");

		return this.findPage(query, page, rows);
	}

	public ArrayList<Operation> query(Operation opt, String start_time,
			String end_time) {
		QueryCriteria query = new QueryCriteria();
		if(opt!=null){
			
			if(!CommonUtils.isEmpty(opt.getUser_name())){
				query.addEntry("user_name", "like", "%" + opt.getUser_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(opt.getOp_origin())){
				query.addEntry("op_origin", "like", "%" + opt.getOp_origin() + "%");
			}
			if(!CommonUtils.isEmpty(opt.getOp_func())){
				query.addEntry("op_func", "like", "%" + opt.getOp_func() + "%");
			}
			if(!CommonUtils.isEmpty(opt.getOp_type()) && !opt.getOp_type().equals("0")){
				query.addEntry("op_type", "=",  opt.getOp_type());
				
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(start_time))){
				query.addEntry("op_time", ">=", start_time +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(end_time))){
				query.addEntry("op_time", "<=", end_time +" 23:59:59");
			}	
			if(!CommonUtils.isEmpty(opt.getStore_code())){
				query.addEntry("store_code", "like", "%" + opt.getStore_code() + "%");
			}
			if(!CommonUtils.isEmpty(opt.getRoom_code())){
				query.addEntry("room_code", "like", "%" + opt.getRoom_code() + "%");
			}
		}
		query.desc("op_time");
		return (ArrayList<Operation>) this.find(query);
	}

}
