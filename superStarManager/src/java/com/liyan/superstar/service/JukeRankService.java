package com.liyan.superstar.service;

import com.liyan.common.dao.Pagination;
import com.liyan.superstar.dto.JukeRankDto;
import com.liyan.superstar.model.JukeRank;

public interface JukeRankService {

	/**
	 * 统计点唱排行
	 * @param jukeRank
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<JukeRankDto> statJukeRank(JukeRank jukeRank,Integer page,Integer rows);

	Pagination<JukeRank> searchJuke(JukeRank jukeRank, Integer page,
			Integer rows);

	Pagination<JukeRank> query(JukeRank jukeRank, Integer page, Integer rows);

}
