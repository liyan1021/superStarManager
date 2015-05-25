package com.liyan.superstar.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.dto.JukeRankDto;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.MainStar;
import com.liyan.superstar.model.Singer;
@Repository
public class MainStarDao extends GenericDaoImpl<MainStar, String>{

	public Pagination<MainStar> query(MainStar mainStar, Integer page,
			Integer rows) {
		QueryCriteria query = new QueryCriteria();
		if(mainStar!=null){
			
			if(!CommonUtils.isEmpty(mainStar.getSinger().getStar_name())){
				query.addEntry("singer.star_name", "like", "%" + mainStar.getSinger().getStar_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(mainStar.getSinger().getStar_type())){
				query.addEntry("singer.star_type", "=",  mainStar.getSinger().getStar_type());
				
			}
			if(!CommonUtils.isEmpty(mainStar.getSinger().getArea())){
				query.addEntry("singer.area", "like", "%" + mainStar.getSinger().getArea() + "%");
				
			}
		}
		query.addEntry("introduce_type", "=", CommonFiled.MAINSTAR_MAIN);  //获取主打歌星
		query.addEntry("is_activity", "=", "0");	
		return this.findPage(query,page,rows);
	}

	public Pagination<Singer> queryUnMainStar(Singer singer, Integer page,
			Integer rows) {
		
		Pagination<Singer> pagination = new Pagination<Singer>();
		pagination.setPageNo(page);
		pagination.setPageSize(rows);
		
		StringBuffer sb = new StringBuffer("from Singer a where 1=1");
		sb.append(" and is_activity = '0' ");
		if(singer!=null){
			if(!CommonUtils.isEmpty(singer.getStar_name())){
				sb.append(" and star_name like '%"+singer.getStar_name()+"%'");
			}
			if(!CommonUtils.isEmpty(singer.getStar_type())){
				sb.append(" and star_type ="+singer.getStar_type());
			}
		}
		sb.append(" and not EXISTS (from  MainStar b where b.introduce_type ="+CommonFiled.MAINSTAR_MAIN+" and a.star_id = b.singer.star_id and b.is_activity ='0')");
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
	public Pagination<Singer> queryUnNewStar(Singer singer, Integer page,
			Integer rows) {
		
		Pagination<Singer> pagination = new Pagination<Singer>();
		pagination.setPageNo(page);
		pagination.setPageSize(rows);
		
		StringBuffer sb = new StringBuffer("from Singer a where 1=1");
		sb.append(" and is_activity = '0' ");
		if(singer!=null){
			if(!CommonUtils.isEmpty(singer.getStar_name())){
				sb.append(" and star_name like '%"+singer.getStar_name()+"%'");
			}
			if(!CommonUtils.isEmpty(singer.getStar_type())){
				sb.append(" and star_type ="+singer.getStar_type());
			}
		}
		
		sb.append(" and not EXISTS (from  MainStar b where b.introduce_type ="+CommonFiled.MAINSTAR_NEW+" and a.star_id = b.singer.star_id and b.is_activity ='0')");
		Query query = entityManager.createQuery(sb.toString());
						
		//获取总数
		pagination.setRecordCount(query.getResultList().size());
		//分页
		query.setFirstResult(pagination.getPageSize() * (pagination.getPageNo() - 1));
        query.setMaxResults(pagination.getPageSize());
		
		pagination.setResultList(query.getResultList());
		return pagination;
	}

	public Pagination<MainStar> queryNewStar(MainStar mainStar, Integer page,
			Integer rows) {
		QueryCriteria query = new QueryCriteria();
		if(mainStar!=null){
			
			if(!CommonUtils.isEmpty(mainStar.getSinger().getStar_name())){
				query.addEntry("singer.star_name", "like", "%" + mainStar.getSinger().getStar_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(mainStar.getSinger().getStar_type())){
				query.addEntry("singer.star_type", "=",  mainStar.getSinger().getStar_type());
				
			}
			if(!CommonUtils.isEmpty(mainStar.getSinger().getArea())){
				query.addEntry("singer.area", "like", "%" + mainStar.getSinger().getArea() + "%");
				
			}
		}
		query.addEntry("introduce_type", "=", CommonFiled.MAINSTAR_NEW);  //获取新星
			
		return this.findPage(query,page,rows);
	}

	public List<MainStar> findBySort(MainStar mainStar) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("introduce_sort", "=",mainStar.getIntroduce_sort());
		query.addEntry("is_activity", "=", "0");
		return this.find(query);
	}

	public List<MainStar> getMainStarList() {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("is_activity", "=", "0");
		query.addEntry("state", "=", CommonFiled.PUSH_STATE_ALREADY);
		return this.find(query);
	}

}
