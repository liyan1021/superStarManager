package com.liyan.superstar.dao;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.MusicRanking;
import com.liyan.superstar.model.NewMusicRanking;

@Repository
public class NewMusicRankingDao extends GenericDaoImpl<NewMusicRanking, String>{
	
	public Pagination<NewMusicRanking> query(NewMusicRanking musicRanking,
			Integer page, Integer rows, String sort, String order) {
		QueryCriteria query = new QueryCriteria();
		if(musicRanking!=null){
			if(!CommonUtils.isEmpty(musicRanking.getMusic_name())){
				query.addEntry("music_name", "like","%"+musicRanking.getMusic_name()+"%" );
			}
			if(!CommonUtils.isEmpty(musicRanking.getSinger_name())){
				query.addEntry("singer_name", "like","%"+musicRanking.getSinger_name()+"%" );
			}
		}
		if(!CommonUtils.isEmpty(sort)){
			if(order.equals("asc")){
				query.asc(sort);
			}else if(order.equals("desc")){
				query.desc(sort);
			}
		}
		return this.findPage(query, page, rows);
	}
}
