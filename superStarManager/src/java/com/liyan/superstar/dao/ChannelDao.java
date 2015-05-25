package com.liyan.superstar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.Channel;

@Repository
public class ChannelDao extends GenericDaoImpl<Channel, String>{

	public Pagination<Channel> query(Channel channel, Integer page,
			Integer rows, String sort, String order) {
		QueryCriteria query = new QueryCriteria();
		if(channel!=null){
			if(!CommonUtils.isEmpty(channel.getChannel_name())){
				query.addEntry("channel_name", "like", "%"+channel.getChannel_name()+"%");
			}
			if(!CommonUtils.isEmpty(channel.getChannel_url())){
				query.addEntry("channel_url", "like", "%"+channel.getChannel_url()+"%");
			}
			if(!CommonUtils.isEmpty(channel.getChannel_content())){
				query.addEntry("channel_content", "like", "%"+channel.getChannel_content()+"%");
			}
			if(!CommonUtils.isEmpty(channel.getState())){
				
				query.addEntry("state", "=",channel.getState()); 
			}
		}
		query.addEntry("is_activity", "=", "0");
		if(!CommonUtils.isEmpty(sort)){
			if(order.equals("asc")){
				query.asc(sort);
			}else if(order.equals("desc")){
				query.desc(sort);
			}
		}
		return this.findPage(query, page, rows);
	}

	public List<Channel> findBySort(Channel channel) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("sort", "=",channel.getSort());
		query.addEntry("is_activity", "=", "0");
		return this.find(query);
	}
	
}
