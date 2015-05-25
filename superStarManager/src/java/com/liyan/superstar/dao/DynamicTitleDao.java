package com.liyan.superstar.dao;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.common.util.DateUtil;
import com.liyan.superstar.model.DynamicTitle;
@Repository
public class DynamicTitleDao extends GenericDaoImpl<DynamicTitle, String> {

	public Pagination<DynamicTitle> query(DynamicTitle dynamicTitle,String start_time,String end_time, Integer page, Integer rows) {
		QueryCriteria query = new QueryCriteria();
		try{
			if(dynamicTitle!=null){
				if(!CommonUtils.isEmpty(dynamicTitle.getTitle_time_length())){
					query.addEntry("title_time_length", "=", dynamicTitle.getTitle_time_length());
				}
				if(!CommonUtils.isEmpty(dynamicTitle.getTitle_number())){
					query.addEntry("title_number", "=", dynamicTitle.getTitle_number());
				}
				if(!CommonUtils.isEmpty(dynamicTitle.getTitle_date_time())){
					query.addEntry("title_date_time", "=", dynamicTitle.getTitle_date_time());
				}
				if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(start_time))){
					query.addEntry("title_date_time", ">=", DateUtil.stringToDate(start_time+" 00:00:00"));
				}
				if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(end_time))){
					query.addEntry("title_date_time", "<=", DateUtil.stringToDate(end_time + " 23:59:59"));
				}
				if(!CommonUtils.isEmpty(dynamicTitle.getTitle_content())){
					query.addEntry("title_content", "like","%"+dynamicTitle.getTitle_content()+"%");
				}
				if(!CommonUtils.isEmpty(dynamicTitle.getRoom_id())){
					query.addEntry("room_id", "like","%"+dynamicTitle.getRoom_id()+"%");
				}
				if(!CommonUtils.isEmpty(dynamicTitle.getRoom_num())){
					query.addEntry("room_num", "like","%"+dynamicTitle.getRoom_num()+"%");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		query.addEntry("is_activity", "=", "0");
		return this.findPage(query, page, rows);
	}

}
