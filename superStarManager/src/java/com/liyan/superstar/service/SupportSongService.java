package com.liyan.superstar.service;

import java.util.List;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.SupportSong;

public interface SupportSongService {

	/**
	 * 分页查询补歌清单
	 * @param support
	 * @param start_time
	 * @param end_time
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<SupportSong> query(SupportSong support, String start_time,
			String end_time, Integer page, Integer rows,String sortName,String sortOrder);

	/**
	 * 删除补歌清单
	 * @param idsList
	 * @return
	 */
	MsgBean removeSupport(String[] idsList);

	/**
	 * 分页查询补歌清单，并记录日志
	 * @param support
	 * @param start_time
	 * @param end_time
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<SupportSong> search(SupportSong support, String start_time,
			String end_time, Integer page, Integer rows,String sortName,String sortOrder);

	/**
	 * 修改补歌清单
	 * @param idsList
	 * @param sign
	 * @return
	 */
	MsgBean updateSupport(String[] idsList, String sign);

	/**
	 * 导出补歌清单
	 * @param support
	 * @param start_time
	 * @param end_time
	 */
	void exportSupport(SupportSong support, String start_time, String end_time);

	MsgBean supportSong(SupportSong support);

}
