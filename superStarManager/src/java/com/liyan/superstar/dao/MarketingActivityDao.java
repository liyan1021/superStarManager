package com.liyan.superstar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.MarketingActivity;

@Repository
public class MarketingActivityDao extends GenericDaoImpl<MarketingActivity, String> {

	public Pagination<MarketingActivity> query(
			MarketingActivity marketingActivity, Integer page, Integer rows,
			String sort, String order) {
		QueryCriteria query = new QueryCriteria();
		if(marketingActivity!=null){
			if(!CommonUtils.isEmpty(marketingActivity.getActivity_name())){
				query.addEntry("activity_name", "like", "%"+marketingActivity.getActivity_name()+"%");
			}
			if(!CommonUtils.isEmpty(marketingActivity.getActivity_introduce())){
				query.addEntry("activity_introduce", "like", "%"+marketingActivity.getActivity_introduce()+"%");
			}
			if(!CommonUtils.isEmpty(marketingActivity.getActivity_sort())){
				query.addEntry("activity_sort", "like", "%"+marketingActivity.getActivity_sort()+"%");
			}
			if(!CommonUtils.isEmpty(marketingActivity.getActivity_time())){
				query.addEntry("activity_time", "=", marketingActivity.getActivity_time());
			}
			if(!CommonUtils.isEmpty(marketingActivity.getState())){
				
				query.addEntry("state", "=",marketingActivity.getState());
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

	public List<MarketingActivity> findBySort(
			MarketingActivity marketingActivity) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("activity_sort", "=",marketingActivity.getActivity_sort());
		query.addEntry("is_activity", "=", "0");
		return this.find(query);
	}

	public List<MarketingActivity> getActivityList() {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("is_activity", "=", "0");
		query.addEntry("state", "=",CommonFiled.PUSH_STATE_ALREADY);
		return this.find(query);
	}
	
}
