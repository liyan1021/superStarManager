package com.liyan.superstar.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.AdvertVideo;
import com.liyan.superstar.model.AdvertVideo1;
import com.liyan.superstar.model.Song;
import com.liyan.common.dao.impl.GenericDaoImpl;

@Repository
public class AdvertVideoDao1 extends GenericDaoImpl<AdvertVideo1, String> {

	public Pagination<AdvertVideo1> query(AdvertVideo1 advertVideo, Integer page,
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
			if(!CommonUtils.isEmpty(advertVideo.getMusic_name())){
				query.addEntry("music_name","like","%"+advertVideo.getMusic_name()+"%");
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
	
	public List<AdvertVideo1> findBySort(AdvertVideo1 advertVideo) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("advert_sort", "=",advertVideo.getAdvert_sort());
		query.addEntry("is_activity", "=", "0");
		return this.find(query);
	}

	public List<AdvertVideo1> getAdvertVideoList() {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("is_activity", "=", "0");
		query.addEntry("state", "=",CommonFiled.PUSH_STATE_ALREADY);
		return this.find(query);
	}
	//获取非过路歌曲列表
	public Pagination<Song> unAdvertVideoList(Song song, Integer page,
			Integer rows) {
		
		Pagination<Song> pagination = new Pagination<Song>();
		pagination.setPageNo(page);
		pagination.setPageSize(rows);
		
		StringBuffer sb = new StringBuffer("from Song a where 1=1");
		sb.append(" and is_activity = '0' ");
		if(song!=null){
			if(!CommonUtils.isEmpty(song.getSong_name())){
				sb.append(" and song_name like '%"+song.getSong_name()+"%'");
			}
			if(!CommonUtils.isEmpty(song.getSinger_name())){
				sb.append(" and singer_name like '%"+song.getSinger_name()+"%'");
			}
		}
		sb.append(" and not EXISTS (from  AdvertVideo1 b where a.song_id = b.music_id and b.is_activity ='0')");
		Query query = entityManager.createQuery(sb.toString());
	                    /*.setParameter("endTime", endTime)
	                    .setParameter("deptId", deptId);*/
		//获取总数
		pagination.setRecordCount(query.getResultList().size());
		//分页
		query.setFirstResult(pagination.getPageSize() * (pagination.getPageNo() - 1));
        query.setMaxResults(pagination.getPageSize());
		
		pagination.setResultList(query.getResultList());
		return pagination;
	}
}
