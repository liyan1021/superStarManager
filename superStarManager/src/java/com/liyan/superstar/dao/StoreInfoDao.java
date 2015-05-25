package com.liyan.superstar.dao;

import java.util.List;




import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.StoreInfo;

@Repository
public class StoreInfoDao extends GenericDaoImpl<StoreInfo, String> {

public Pagination<StoreInfo> query(StoreInfo storeInfo,Integer page, Integer rows,String sort,String order) {
		
		QueryCriteria queryCriteria = new QueryCriteria();
		if(storeInfo!=null){
			
			if(!CommonUtils.isEmpty(storeInfo.getStore_code())){
				queryCriteria.addEntry("store_code", "like", "%" + storeInfo.getStore_code() + "%");
				
			}
			if(!CommonUtils.isEmpty(storeInfo.getStore_name())){
				queryCriteria.addEntry("store_name", "like", "%" + storeInfo.getStore_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(storeInfo.getStore_address())){
				queryCriteria.addEntry("store_address", "like", "%" + storeInfo.getStore_address()+ "%");
				
			}
		}
		queryCriteria.addEntry("is_activity", "=", "0");
		return this.findPage(queryCriteria,page,rows);
		
	}

//	public StoreInfo getStoreInfoByStoreInfocode(String storeInfo_code) {
//		StoreInfo result = null;
//		try {
//            result = (StoreInfo)entityManager.createQuery("SELECT o FROM User o WHERE o.user_code = :user_code and o.is_activity = :is_activity")
//                    .setParameter("user_code", storeInfo_code)
//                    .setParameter("is_activity","0")
//                    .getSingleResult();
//            
//        } catch (NoResultException e) {
//        	  e.printStackTrace();
//        }
//        return result;
//	}

	public List<StoreInfo> queryList(StoreInfo storeInfo) {
		QueryCriteria queryCriteria = new QueryCriteria();
	if(storeInfo!=null){
			
			if(!CommonUtils.isEmpty(storeInfo.getStore_code())){
				queryCriteria.addEntry("store_code", "like", "%" + storeInfo.getStore_code() + "%");
				
			}
			if(!CommonUtils.isEmpty(storeInfo.getStore_name())){
				queryCriteria.addEntry("store_name", "like", "%" + storeInfo.getStore_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(storeInfo.getStore_address())){
				queryCriteria.addEntry("store_address", "like", "%" + storeInfo.getStore_address()+ "%");
				
			}
			
		}
		queryCriteria.addEntry("is_activity", "=", "0");
		return this.find(queryCriteria);
	}

	public List<StoreInfo> findByStoreInfoCode(StoreInfo storeInfo) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("store_code", "=",storeInfo.getStore_code());
		return this.find(query);
	}

	
}
