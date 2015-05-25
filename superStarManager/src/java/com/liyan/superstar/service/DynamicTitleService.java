package com.liyan.superstar.service;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.DynamicTitle;

public interface DynamicTitleService {

	/**
	 * 分页查询字幕
	 * @param dynamicTitle
	 * @param start_time
	 * @param end_time
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<DynamicTitle> query(DynamicTitle dynamicTitle,String start_time,String end_time, Integer page,
			Integer rows);

	/**
	 * 保存字幕
	 * @param dynamicTitle
	 * @return
	 */
	MsgBean create(DynamicTitle dynamicTitle);

	/**
	 * 编辑字幕
	 * @param dynamicTitle
	 * @return
	 */
	MsgBean editTitle(DynamicTitle dynamicTitle);

	/**
	 * 删除字幕
	 * @param idsList
	 * @return
	 */
	MsgBean removeTitle(String[] idsList);

	/**
	 * 分页查询字幕 并记录日志
	 * @param dynamicTitle
	 * @param start_time
	 * @param end_time
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<DynamicTitle> search(DynamicTitle dynamicTitle,String start_time,String end_time, Integer page,
			Integer rows);

	MsgBean pushDynamicTitle(String ids);

}
