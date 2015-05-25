package com.liyan.superstar.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.Advert;
import com.liyan.superstar.model.AdvertMusic;

@Repository
public class AdvertMusicDao extends GenericDaoImpl<AdvertMusic, String> {

	public Pagination<AdvertMusic> queryAdvertSong(Advert advert, Integer page,
			Integer rows) {
		QueryCriteria query = new QueryCriteria();
		if(advert!=null){
			if(!CommonUtils.isEmpty(advert.getAdvert_id())){
				query.addEntry("advert_id", "=", advert.getAdvert_id());
			}
		}
		return this.findPage(query, page, rows);
	}

	public List<AdvertMusic> getAdvertMusicList() {
		StringBuffer sb = new StringBuffer("");
		sb.append("select a from AdvertMusic a,Song b where a.song.song_id= b.song_id and b.is_activity = '0'");
		Query query = entityManager.createQuery(sb.toString());
		return query.getResultList();
	}
	
}
