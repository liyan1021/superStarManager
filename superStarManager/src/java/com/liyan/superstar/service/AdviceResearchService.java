package com.liyan.superstar.service;

import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.AdviceResearch;

public interface AdviceResearchService {

	Pagination<AdviceResearch> query(AdviceResearch adviceResearch,
			Integer page, Integer rows);

}
