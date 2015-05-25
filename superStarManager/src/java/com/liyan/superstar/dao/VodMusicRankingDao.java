package com.liyan.superstar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.NewMusicRanking;
import com.liyan.superstar.model.Singer;
import com.liyan.superstar.model.VodMusicRanking;

@Repository
public class VodMusicRankingDao extends GenericDaoImpl<VodMusicRanking, String>{

	public Pagination<VodMusicRanking> query(VodMusicRanking musicRanking,
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

	public List<VodMusicRanking> findRankByRank(String rank) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("music_ranking", "=", rank);
		List<VodMusicRanking> find = this.find(query);
		/*if(find.size() != 0){
			singer = find.get(0);
		}*/
		return find ; 
	}

}
