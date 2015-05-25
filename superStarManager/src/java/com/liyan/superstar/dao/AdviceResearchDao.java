package com.liyan.superstar.dao;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.superstar.model.AdviceResearch;

@Repository
public class AdviceResearchDao extends GenericDaoImpl<AdviceResearch, String> {

	public Pagination<AdviceResearch> query(AdviceResearch adviceResearch,
			Integer page, Integer rows) {
		QueryCriteria query = new QueryCriteria();
		return this.findPage(query, page, rows);
	}

}
