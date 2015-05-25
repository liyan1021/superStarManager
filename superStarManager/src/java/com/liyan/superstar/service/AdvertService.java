package com.liyan.superstar.service;

import java.io.File;
import java.util.List;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.Advert;
import com.liyan.superstar.model.AdvertMusic;

public interface AdvertService {

	/**
	 * 查询广告，不进行日志记录
	 * @param advert
	 * @param page
	 * @param rows
	 * @param order 
	 * @param sort 
	 * @return
	 */
	Pagination<Advert> query(Advert advert, Integer page, Integer rows, String sort, String order);

	/**
	 * 搜索广告
	 * @param advert
	 * @param page
	 * @param rows
	 * @param order 
	 * @param sort 
	 * @return
	 */
	Pagination<Advert> search(Advert advert, Integer page, Integer rows, String sort, String order);

	/**
	 * 新增广告
	 * @param advert
	 * @param image
	 * @param imageFileName
	 * @param imageContentType
	 * @return 
	 */
	MsgBean create(Advert advert, File image, String imageFileName,
			String imageContentType);

	/**
	 * 编辑广告
	 * @param advert
	 * @param image
	 * @param imageFileName
	 * @param imageContentType
	 * @return 
	 */
	MsgBean editAdvert(Advert advert, File image, String imageFileName,
			String imageContentType);

	/**
	 * 删除广告
	 * @param list
	 * @return 
	 */
	MsgBean removeAdvert(List<String> list);

	/**
	 * 发布广告
	 * @param ids
	 * @return
	 */
	MsgBean pushAdvert(String ids);

	/**
	 * 查询所属广告的歌曲
	 * @param advert
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<AdvertMusic> searchAdvertSong(Advert advert, Integer page,
			Integer rows);

	/**
	 * 删除所属广告歌曲
	 * @param advert_music_id
	 * @return
	 */
	MsgBean removeAdvertMusic(String advert_music_id);

	/**
	 * 增加所属广告歌曲
	 * @param ids
	 * @return
	 */
	MsgBean saveAdvertMusic(String ids,Advert advert);

	/**
	 * 校验广告顺序
	 * @param advert
	 * @return
	 */
	MsgBean checkAdvertSort(Advert advert);

}
