package com.liyan.superstar.service;

import java.util.List;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.StoreInfo;

public interface StoreInfoService {

	
	MsgBean checkStoreInfoCode(StoreInfo storeInfo);

	Pagination<StoreInfo> query(StoreInfo storeInfo, Integer page,Integer rows, String sort, String order);

	MsgBean createStoreInfo(StoreInfo storeInfo);
	
	MsgBean removeStoreInfo(List<String> list);
	
	Pagination<StoreInfo> searchStoreInfo(StoreInfo storeInfo, Integer page,Integer rows, String sort, String order);
	
	MsgBean editStoreInfo(StoreInfo storeInfo);

}
