package com.liyan.superstar.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.dto.JukeRankDto;
import com.liyan.superstar.model.JukeRank;

@Repository
public class JukeRankDao extends GenericDaoImpl<JukeRank, String>{
	
	@SuppressWarnings("unchecked")
	public Pagination<JukeRankDto> statJukeRank(JukeRank jukeRank,Integer page,Integer rows) {
		StringBuffer sb = new StringBuffer("select new com.liyan.superstar.dto.JukeRankDto("
				+ " a.music_id as music_id,"
				+ " a.music_name as music_name,"
				+ " a.singer_name as singer_name,"
				+ " count(a.music_id) as juke_number )");
		sb.append(" from JukeRank a group by a.music_id,a.music_name,a.singer_name order by count(a.music_id) desc  ");
		Query query = entityManager.createQuery(sb.toString());
	                    /*.setParameter("startTime", startTime)
	                    .setParameter("endTime", endTime)
	                    .setParameter("deptId", deptId);*/
		
		Pagination<JukeRankDto> pagination = new Pagination<JukeRankDto>();
		pagination.setPageNo(page);
		pagination.setPageSize(rows);
		pagination.setRecordCount(query.getResultList().size());
		pagination.setResultList(query.getResultList());
		return pagination;
	}

	public Pagination<JukeRank> query(JukeRank jukeRank, Integer page,
			Integer rows) {
		QueryCriteria query = new QueryCriteria();
		if(jukeRank!=null){
			if(!CommonUtils.isEmpty(jukeRank.getMusic_name())){
				query.addEntry("music_name", "like", "%"+jukeRank.getMusic_name()+"%");
			}
			if(!CommonUtils.isEmpty(jukeRank.getStart_time())){
				query.addEntry("start_time", ">=", jukeRank.getStart_time());
			}
			if(!CommonUtils.isEmpty(jukeRank.getEnd_time())){
				query.addEntry("end_time", ">=", jukeRank.getEnd_time());
			}
			if(!CommonUtils.isEmpty(jukeRank.getPlay_plan())){
				query.addEntry("play_plan", "=", jukeRank.getPlay_plan());
			}
			if(!CommonUtils.isEmpty(jukeRank.getMember_no())){
				query.addEntry("member_no", "like","%"+jukeRank.getMember_no()+"%" );
			}
			if(!CommonUtils.isEmpty(jukeRank.getCompany())){
				query.addEntry("company", "like","%"+jukeRank.getCompany()+"%" );
			}
			if(!CommonUtils.isEmpty(jukeRank.getStore())){
				query.addEntry("store", "like","%"+jukeRank.getStore()+"%" );
			}
			if(!CommonUtils.isEmpty(jukeRank.getRoom())){
				query.addEntry("room", "like","%"+jukeRank.getRoom()+"%" );
			}
		}
		return this.findPage(query, page, rows);
	}

}
