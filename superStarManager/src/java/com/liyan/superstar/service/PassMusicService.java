package com.liyan.superstar.service;

import java.util.List;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.PassMusic;

public interface PassMusicService {

	/**
	 * 分页查询过路歌曲
	 * @param passMusic
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<PassMusic> query(PassMusic passMusic, Integer page, Integer rows);

	/**
	 * 分页查询过路歌曲
	 * @param passMusic
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<PassMusic> search(PassMusic passMusic, Integer page, Integer rows);

	/**
	 * 新增过路歌曲
	 * @param passMusic
	 * @return
	 */
	MsgBean create(PassMusic passMusic);

	/**
	 * 删除过路歌曲
	 * @param list
	 * @return
	 */
	MsgBean removePassMusic(List<String> list);

	/**
	 * 编辑过路歌曲
	 * @param passMusic
	 * @return
	 */
	MsgBean editPassMusic(PassMusic passMusic);

	/**
	 * 发布过路歌曲
	 * @param ids
	 * @return
	 */
	MsgBean pushPassMusic(String ids);

	MsgBean checkPassSongSort(PassMusic passMusic);

}
