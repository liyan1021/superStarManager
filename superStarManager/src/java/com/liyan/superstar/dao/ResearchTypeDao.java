package com.liyan.superstar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.superstar.model.ResearchType;

@Repository
public class ResearchTypeDao extends GenericDaoImpl<ResearchType, String>{
	
	public Pagination<ResearchType> query(ResearchType researchType,Integer page,Integer rows){
		
		QueryCriteria query = new QueryCriteria();
		
		query.addEntry("is_activity", "=", "0");
		return this.findPage(query, page,rows);
	}

	public List<ResearchType> getResearchTypeList() {
		QueryCriteria query = new QueryCriteria();
		
		query.addEntry("is_activity", "=", "0");
		return this.find(query);
	} 
}
