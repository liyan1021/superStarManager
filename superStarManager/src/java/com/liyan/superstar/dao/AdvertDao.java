package com.liyan.superstar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.Advert;
import com.liyan.superstar.model.Interface;

@Repository
public class AdvertDao extends GenericDaoImpl<Advert, String>{


	public Pagination<Advert> query(Advert advert, Integer page, Integer rows, String sort, String order) {
		QueryCriteria query = new QueryCriteria();
		if(advert!=null){
			if(!CommonUtils.isEmpty(advert.getAdvert_name())){
				query.addEntry("advert_name", "like","%"+ advert.getAdvert_name()+ "%");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(advert.getOnline_start_time()))){
				query.addEntry("online_time", ">=", advert.getOnline_start_time() +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(advert.getOnline_end_time()))){
				query.addEntry("online_time", "<=", advert.getOnline_end_time() +" 23:59:59");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(advert.getOffline_start_time()))){
				query.addEntry("offline_time", ">=", advert.getOffline_start_time() +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(advert.getOffline_end_time()))){
				query.addEntry("offline_time", "<=", advert.getOffline_end_time() +" 23:59:59");
			}
			if(!CommonUtils.isEmpty(advert.getAdvert_content())){
				query.addEntry("advert_content", "like","%"+advert.getAdvert_content()+"%");
			}
			if(!CommonUtils.isEmpty(advert.getIs_activity())){
				
				query.addEntry("is_activity", "=",advert.getIs_activity());
			}
			if(!CommonUtils.isEmpty(advert.getAdvert_time())){
				
				query.addEntry("advert_time", "=",advert.getAdvert_time());
			}
			if(!CommonUtils.isEmpty(advert.getAdvert_sort())){
				
				query.addEntry("advert_sort", "=",advert.getAdvert_sort());
			}
			if(!CommonUtils.isEmpty(advert.getState())){
				
				query.addEntry("state", "=",advert.getState());
			}
			if(!CommonUtils.isEmpty(advert.getIs_index_advert())){
				
				query.addEntry("is_index_advert", "=",advert.getIs_index_advert());
			}
			
		}
		if(!CommonUtils.isEmpty(sort)){
			if(order.equals("asc")){
				query.asc(sort);
			}else if(order.equals("desc")){
				query.desc(sort);
			}
		}
		query.addEntry("is_activity", "=", "0");
		return this.findPage(query, page, rows);
	}

	public List<Advert> findBySort(Advert advert) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("advert_sort", "=",advert.getAdvert_sort());
		query.addEntry("is_activity", "=", "0");
		query.addEntry("is_index_advert", "=", advert.getIs_index_advert());
		return this.find(query);
	}

	public List<Advert> getAdvertList() {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("is_activity", "=", "0");
		query.addEntry("state", "=",CommonFiled.PUSH_STATE_ALREADY);
		return this.find(query);
	}
	
}
