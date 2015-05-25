package com.liyan.superstar.service;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.MusicRanking;

public interface MusicRankingService {

	Pagination<MusicRanking> query(MusicRanking musicRanking, Integer page,
			Integer rows, String sort, String order);

	Pagination<MusicRanking> searchMusicRank(MusicRanking musicRanking,
			Integer page, Integer rows, String sort, String order);

	MsgBean editMusicRank(MusicRanking musicRanking, int song_number);

}
