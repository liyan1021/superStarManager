package com.liyan.superstar.service;

import java.io.File;
import java.util.List;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.AdvertVideo1;
import com.liyan.superstar.model.Song;

  public interface AdvertVedioService1 {
    /**
	 * 查询营销活动
	 * @param advertVideo
	 * @param page
	 * @param rows
	 * @return
	 */
    Pagination<AdvertVideo1> query(AdvertVideo1 advertVideo, Integer page,
			Integer rows);
	
    Pagination<AdvertVideo1> search(AdvertVideo1 advertVideo, Integer page,
			Integer rows);
    /**
	 * 新增营销活动
	 * @param passMusic
	 * @return
	 */
	MsgBean create(AdvertVideo1 advertVideo);
	/**
	 * 编辑营销活动
	 * @param advertVideo
	 * @param video
	 * @param videoFileName
	 * @return
	 */
	MsgBean editVideo(AdvertVideo1 advertVideo);
	/**
	 * 删除营销活动
	 * @param list
	 * @return
	 */
	MsgBean removeAdvertVideo(List<String> list);
    /**
     * 发布营销活动
     * @param ids
     * @return
     */
	MsgBean pushAdvertVideo(String ids);
	/**
	 * 获取未设置为宣传片的歌曲
	 * @param song
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<Song> unAdvertVideoList(Song song, Integer page, Integer rows);
	/**
     * 检查发布顺序是否唯一
     * @param advertVideo
     * @return
     */
	MsgBean checkAdvertSort(AdvertVideo1 advertVideo);
}
