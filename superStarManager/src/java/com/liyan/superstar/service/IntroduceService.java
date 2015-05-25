package com.liyan.superstar.service;

import java.util.List;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.Introduce;
import com.liyan.superstar.model.Song;

public interface IntroduceService {

	/**
	 * 分页查询新歌推荐
	 * @param introduce
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<Introduce> query(Introduce introduce, Integer page, Integer rows);

	/**
	 * 分页查询新歌推荐 并记录日志
	 * @param introduce
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<Introduce> search(Introduce introduce, Integer page, Integer rows);

	/**
	 * 新增新歌推荐
	 * @param introduce
	 * @return
	 */
	MsgBean create(Introduce introduce);

	/**
	 * 编辑新歌推荐
	 * @param introduce
	 * @return
	 */
	MsgBean editIntroduce(Introduce introduce);

	/**
	 * 删除新歌歌推荐
	 * @param list
	 * @return
	 */
	MsgBean removeIntroduce(List<String> list);

	MsgBean pushIntroduce(String ids);

	MsgBean checkIntroductSort(Introduce introduce);

	Pagination<Song> queryMusicList(Song song, Integer page, Integer rows,
			String sort, String order);

}
