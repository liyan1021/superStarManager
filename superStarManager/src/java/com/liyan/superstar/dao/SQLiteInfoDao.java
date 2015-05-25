package com.liyan.superstar.dao;


import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.SQLiteInfo;
@Repository
public class SQLiteInfoDao  extends GenericDaoImpl<SQLiteInfo, String>{

	public Pagination<SQLiteInfo> query(SQLiteInfo sqliteInfo,
			String start_time, String end_time, Integer page, Integer rows,String sort,String order) {
		QueryCriteria query = new QueryCriteria();
		try{
			if(sqliteInfo!=null){
				if(!CommonUtils.isEmpty(sqliteInfo.getSqlite_version())){
					query.addEntry("sqlite_version", "like", "%"+sqliteInfo.getSqlite_version()+"%");
				}
				if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(start_time))){
					query.addEntry("create_time", ">=", start_time+" 00:00:00");
				}
				if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(end_time))){
					query.addEntry("create_time", "<=", end_time + " 23:59:59");
				}
			}
			query.addEntry("is_activity", "=", "0");
			  if(!CommonUtils.isEmpty(sort)){
				  if(order.equals("asc")){
					 query.asc(sort);  
				  }
				  else if(order.equals("desc")){
					  query.desc(sort);
				  }
			  }
		}catch(Exception e){
			e.printStackTrace();
		}

		return this.findPage(query, page, rows);
	}


}
