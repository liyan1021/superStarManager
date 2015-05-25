package com.liyan.superstar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.common.util.DateUtil;
import com.liyan.superstar.model.ApkInfo;
import com.liyan.superstar.model.Interface;

@Repository
public class ApkInfoDao extends GenericDaoImpl<ApkInfo, String>{

	public Pagination<ApkInfo> query(ApkInfo apkInfo,String start_time,String end_time, Integer page, Integer rows,String sort,String order) {
		QueryCriteria query = new QueryCriteria();
		try{
			if(apkInfo!=null){
				if(!CommonUtils.isEmpty(apkInfo.getApk_name())){
					query.addEntry("apk_name", "like", "%"+apkInfo.getApk_name()+"%") ;
				}
				if(!CommonUtils.isEmpty(apkInfo.getApk_version())){
					query.addEntry("apk_version", "=", apkInfo.getApk_version());
				}
				if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(start_time))){
					query.addEntry("create_time", ">=", DateUtil.stringToDate(start_time+" 00:00:00"));
				}
				if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(end_time))){
					query.addEntry("create_time", "<=", DateUtil.stringToDate(end_time + " 23:59:59"));
				}
			}
			
			query.addEntry("is_activity", "=", "0");
			
			if(!CommonUtils.isEmpty(sort)){
				if(order.equals("asc")){
					query.asc(sort);
				}else if(order.equals("desc")){
					query.desc(sort);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.findPage(query, page, rows);
		
	}

	public List<ApkInfo> findBySort(ApkInfo apkInfo) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("apk_version", "=",apkInfo.getApk_version());
		query.addEntry("is_activity", "=", "0");
		return this.find(query);
	}

}
