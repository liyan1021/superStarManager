package com.liyan.superstar.service;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.MainStar;
import com.liyan.superstar.model.Singer;

public interface MainStarService {

	/**
	 * 查询主打歌星
	 * @param mainStar
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<MainStar> searchMainStarList(MainStar mainStar, Integer page,
			Integer rows);

	/**
	 * 删除主打歌星
	 * @param ids
	 * @return
	 */
	MsgBean removeMainStar(String ids);

	/**
	 * 设置主打歌星
	 * @param mainStar
	 * @return
	 */
	MsgBean setMainStar(MainStar mainStar);

	/**
	 * 非主打歌星
	 * @param singer
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<Singer> unMainStarList(Singer singer, Integer page,
			Integer rows);

	/**
	 * 主打歌星列表
	 * @param mainStar
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<MainStar> queryMainStar(MainStar mainStar, Integer page,
			Integer rows);

	/**
	 * 发布主打歌星
	 * @param ids
	 * @return
	 */
	MsgBean pushMainStar(String ids);

	
	//---------------新星推荐
	
	/**
	 * 新星列表
	 * @param mainStar
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<MainStar> queryNewStar(MainStar mainStar, Integer page,
			Integer rows);

	/**
	 * 新星列表，并记录日志
	 * @param mainStar
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<MainStar> searchNewStar(MainStar mainStar, Integer page,
			Integer rows);

	/**
	 * 发布新星
	 * @param ids
	 * @return
	 */
	MsgBean pushNewStar(String ids);

	/**
	 * 删除新星
	 * @param ids
	 * @return
	 */
	MsgBean removeNewStar(String ids);

	/**
	 * 设置新星
	 * @param ids
	 * @return
	 */
	MsgBean setNewStar(String ids);

	/**
	 * 查询非新星
	 * @param singer
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<Singer> unNewStarList(Singer singer, Integer page, Integer rows);

	MsgBean checkMainStarSort(MainStar mainStar);

}
