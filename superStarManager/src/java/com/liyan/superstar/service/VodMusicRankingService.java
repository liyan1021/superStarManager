package com.liyan.superstar.service;

import java.io.File;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.MusicRanking;
import com.liyan.superstar.model.VodMusicRanking;

public interface VodMusicRankingService {

	Pagination<VodMusicRanking> query(VodMusicRanking musicRanking,
			Integer page, Integer rows, String sort, String order);

	Pagination<VodMusicRanking> searchMusicRank(VodMusicRanking musicRanking,
			Integer page, Integer rows, String sort, String order);

	MsgBean editMusicRank(VodMusicRanking musicRanking, int song_number);

	MsgBean importRank(File importFile);

}
