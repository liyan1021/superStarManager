package com.liyan.superstar.service;

import java.io.File;
import java.util.List;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.MarketingActivity;

public interface MarketingActivityService {

	Pagination<MarketingActivity> query(MarketingActivity marketingActivity,
			Integer page, Integer rows, String sort, String order);

	MsgBean removeMarketingActivity(List<String> list);

	MsgBean create(MarketingActivity marketingActivity, List<File> image,
			List<String> imageFileName, List<String> imageContentType);

	Pagination<MarketingActivity> search(MarketingActivity marketingActivity,
			Integer page, Integer rows, String sort, String order);

	MsgBean editMarketingActivity(MarketingActivity marketingActivity,
			List<File> image, List<String> imageFileName, List<String> imageContentType);

	MsgBean pushMarketingActivity(String ids);

	MsgBean checkActivitySort(MarketingActivity marketingActivity);

}
