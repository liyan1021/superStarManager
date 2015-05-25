package com.liyan.superstar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.AdvertVideo;

@Repository
public class AdvertVideoDao extends GenericDaoImpl<AdvertVideo, String>{

	public Pagination<AdvertVideo> query(AdvertVideo advertVideo, Integer page,
			Integer rows) {
		QueryCriteria query = new QueryCriteria();
		if(advertVideo !=null ){
			if(!CommonUtils.isEmpty(advertVideo.getAdvert_theme())){
				query.addEntry("advert_theme", "like","%"+ advertVideo.getAdvert_theme()+ "%");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(advertVideo.getOnline_start_time()))){
				query.addEntry("online_time", ">=", advertVideo.getOnline_start_time());
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(advertVideo.getOnline_end_time()))){
				query.addEntry("online_time", "<=", advertVideo.getOnline_end_time());
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(advertVideo.getOffline_start_time()))){
				query.addEntry("offline_time", ">=", advertVideo.getOffline_start_time() );
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(advertVideo.getOffline_end_time()))){
				query.addEntry("offline_time", "<=", advertVideo.getOffline_end_time());
			}
			if(!CommonUtils.isEmpty(advertVideo.getAdvert_content())){
				query.addEntry("advert_content", "like","%"+advertVideo.getAdvert_content()+"%");
			}
			if(!CommonUtils.isEmpty(advertVideo.getIs_activity())){
				
				query.addEntry("is_activity", "=",advertVideo.getIs_activity());
			}
			if(!CommonUtils.isEmpty(advertVideo.getAdvert_sort())){
				
				query.addEntry("advert_sort", "=",advertVideo.getAdvert_sort());
			}
		}
		query.addEntry("is_activity", "=", "0");
		return this.findPage(query, page, rows);
	}

	public List<AdvertVideo> findBySort(AdvertVideo advertVideo) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("advert_sort", "=",advertVideo.getAdvert_sort());
		query.addEntry("is_activity", "=", "0");
		return this.find(query);
	}

	public List<AdvertVideo> getAdvertVideoList() {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("is_activity", "=", "0");
		query.addEntry("state", "=",CommonFiled.PUSH_STATE_ALREADY);
		return this.find(query);
	}

}
