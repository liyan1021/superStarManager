package com.liyan.superstar.service;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.ResearchType;

public interface ResearchTypeService {

	Pagination<ResearchType> query(ResearchType researchType, Integer page,
			Integer rows);

	MsgBean removeResearchType(String[] idsList);

	MsgBean editResearchType(ResearchType researchType);

	MsgBean saveResearchType(ResearchType researchType);

}
