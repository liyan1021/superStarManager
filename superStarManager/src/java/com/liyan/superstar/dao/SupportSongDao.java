package com.liyan.superstar.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.SupportSong;

@Repository
public class SupportSongDao extends GenericDaoImpl<SupportSong, String> {

	public Pagination<SupportSong> query(SupportSong support,
			String start_time, String end_time, Integer page, Integer rows,String sortName, String sortOrder) {

		QueryCriteria query = new QueryCriteria();
		
		if(support!=null){
			
			if(!CommonUtils.isEmpty(support.getSong_name())){
				query.addEntry("song_name", "like","%"+ support.getSong_name()+"%");
			}
			if(!CommonUtils.isEmpty(support.getSong_year())){
				query.addEntry("song_year", "like","%"+ support.getSong_year()+"%");
			}
			if(!CommonUtils.isEmpty(support.getStar_name())){
				query.addEntry("star_name", "like","%"+ support.getStar_name()+"%");
			}
			if(!CommonUtils.isEmpty(support.getSong_state())){
				query.addEntry("song_state", "=", support.getSong_state());
			}
			if(!CommonUtils.isEmpty(support.getHandler_person())){
				query.addEntry("handler_person", "like", "%"+support.getHandler_person()+"%");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(start_time))){
				query.addEntry("support_time", ">=", start_time +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(end_time))){
				query.addEntry("support_time", "<=", end_time +" 23:59:59");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(support.getHandler_start_time()))){
				query.addEntry("handler_time", ">=", support.getHandler_start_time() +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(support.getHandler_end_time()))){
				query.addEntry("handler_time", "<=", support.getHandler_end_time() +" 23:59:59");
			}
			
			if(!CommonUtils.isEmpty(support.getVersions())){
				query.addEntry("version", "like", "%"+support.getVersions()+"%");
			}
			
			if(!CommonUtils.isEmpty(support.getLanguages())){
				query.addEntry("language", "like", "%"+support.getLanguages()+"%");
			}
			
		}
		query.addEntry("is_activity", "=", "0");
		if(!CommonUtils.isEmpty(sortName)){
			if(sortOrder.equals("asc")){
				query.asc(sortName);
			}else if(sortOrder.equals("desc")){
				query.desc(sortName);
			}
		}
		return this.findPage(query, page, rows);
			
	}

	public ArrayList<SupportSong> query(SupportSong support, String start_time, String end_time) {
		QueryCriteria query = new QueryCriteria();
		
		if(support!=null){
			
			if(!CommonUtils.isEmpty(support.getSong_name())){
				query.addEntry("song_name", "like","%"+ support.getSong_name()+"%");
			}
			if(!CommonUtils.isEmpty(support.getSong_year())){
				query.addEntry("song_year", "like","%"+ support.getSong_year()+"%");
			}
			if(!CommonUtils.isEmpty(support.getStar_name())){
				query.addEntry("star_name", "like","%"+ support.getStar_name()+"%");
			}
			if(!CommonUtils.isEmpty(support.getSong_state())){
				query.addEntry("song_state", "=", support.getSong_state());
			}
			if(!CommonUtils.isEmpty(support.getHandler_person())){
				query.addEntry("handler_person", "like", "%"+support.getHandler_person()+"%");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(start_time))){
				query.addEntry("support_time", ">=", start_time +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(end_time))){
				query.addEntry("support_time", "<=", end_time +" 23:59:59");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(support.getHandler_start_time()))){
				query.addEntry("handler_time", ">=", support.getHandler_start_time() +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(support.getHandler_end_time()))){
				query.addEntry("handler_time", "<=", support.getHandler_end_time() +" 23:59:59");
			}
			
			if(!CommonUtils.isEmpty(support.getVersions())){
				query.addEntry("version", "like", "%"+support.getVersions()+"%");
			}
			
			if(!CommonUtils.isEmpty(support.getLanguages())){
				query.addEntry("language", "like", "%"+support.getLanguages()+"%");
			}
		}
		query.addEntry("is_activity", "=", "0");
		return (ArrayList<SupportSong>) this.find(query);
	}

}
