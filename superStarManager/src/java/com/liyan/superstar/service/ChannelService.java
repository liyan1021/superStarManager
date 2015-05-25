package com.liyan.superstar.service;

import java.io.File;
import java.util.List;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.Channel;
import com.liyan.superstar.model.Interface;

public interface ChannelService {

	MsgBean removeChannel(List<String> list);

	Pagination<Channel> query(Channel channel, Integer page, Integer rows,
			String sort, String order);

	MsgBean saveChannel(Channel channel, File image, String imageFileName);

	Pagination<Channel> searchChannel(Channel channel, Integer page,
			Integer rows, String sort, String order);

	MsgBean editChannel(Channel channel, File image, String imageFileName);

	MsgBean pushChannel(String ids);

	MsgBean checkChannelSort(Channel channel);

}
