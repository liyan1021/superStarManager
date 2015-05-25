package com.liyan.superstar.service;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.NewMusicRanking;

public interface NewMusicRankingService {

	Pagination<NewMusicRanking> query(NewMusicRanking musicRanking,
			Integer page, Integer rows, String sort, String order);

	Pagination<NewMusicRanking> searchMusicRank(NewMusicRanking musicRanking,
			Integer page, Integer rows, String sort, String order);

	MsgBean editMusicRank(NewMusicRanking musicRanking, int song_number);

}
