package com.liyan.superstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyan.common.dao.Pagination;
import com.liyan.superstar.dao.AdviceResearchDao;
import com.liyan.superstar.model.AdviceResearch;
import com.liyan.superstar.service.AdviceResearchService;

@Service
@Transactional
public class AdviceResearchServiceImpl implements AdviceResearchService{

	@Autowired
	private AdviceResearchDao adviceResearchDao ; 
	
	@Override
	public Pagination<AdviceResearch> query(AdviceResearch adviceResearch,
			Integer page, Integer rows) {
		return this.adviceResearchDao.query(adviceResearch,page,rows);
	}

	public AdviceResearchDao getAdviceResearchDao() {
		return adviceResearchDao;
	}

	public void setAdviceResearchDao(AdviceResearchDao adviceResearchDao) {
		this.adviceResearchDao = adviceResearchDao;
	}
	
}
