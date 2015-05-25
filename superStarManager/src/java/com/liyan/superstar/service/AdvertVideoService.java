package com.liyan.superstar.service;

import java.io.File;
import java.util.List;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.AdvertVideo;

public interface AdvertVideoService {

	Pagination<AdvertVideo> query(AdvertVideo adverVideo, Integer page,
			Integer rows);

	Pagination<AdvertVideo> search(AdvertVideo advertVideo, Integer page,
			Integer rows);

	MsgBean create(AdvertVideo advertVideo, File video, String videoFileName);

	MsgBean editVideo(AdvertVideo advertVideo, File video, String videoFileName);

	MsgBean removeVideo(List<String> list);

	MsgBean pushVideo(String ids);

	MsgBean checkAdvertSort(AdvertVideo advertVideo);

}
