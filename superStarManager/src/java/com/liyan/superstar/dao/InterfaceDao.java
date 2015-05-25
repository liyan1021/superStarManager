package com.liyan.superstar.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.common.util.DateUtil;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.Song;

@Repository
public class InterfaceDao extends GenericDaoImpl<Interface, String> {

	public Pagination<Interface> query(Interface itf, Integer page, Integer rows) {
		QueryCriteria query = new QueryCriteria();
		if(itf!=null){
			if(!CommonUtils.isEmpty(itf.getInterface_name())){
				query.addEntry("interface_name", "like","%"+ itf.getInterface_name()+ "%");
			}
			/*if(!CommonUtils.isEmpty(itf.getOnline_time())){
				query.addEntry("online_time", ">=", itf.getOnline_time());
			}
			if(!CommonUtils.isEmpty(itf.getOffline_time())){
				query.addEntry("offline_time", ">=", itf.getOffline_time());
			}*/
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(itf.getOnline_start_time()))){
				query.addEntry("online_time", ">=", itf.getOnline_start_time() +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(itf.getOnline_end_time()))){
				query.addEntry("online_time", "<=", itf.getOnline_end_time() +" 23:59:59");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(itf.getOffline_start_time()))){
				query.addEntry("offline_time", ">=", itf.getOffline_start_time() +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(itf.getOffline_end_time()))){
				query.addEntry("offline_time", "<=", itf.getOffline_end_time() +" 23:59:59");
			}
			if(!CommonUtils.isEmpty(itf.getIs_activity())){
				
				query.addEntry("is_activity", "=",itf.getIs_activity());
			}
			if(!CommonUtils.isEmpty(itf.getInterface_time())){
				
				query.addEntry("interface_time", "=",itf.getInterface_time());
			}
			if(!CommonUtils.isEmpty(itf.getInterface_sort())){
				
				query.addEntry("interface_sort", "=",itf.getInterface_sort());
			}
			if(!CommonUtils.isEmpty(itf.getState())){
				
				query.addEntry("state", "=",itf.getState());
			}
		}
			query.addEntry("interface_type", "=","1");
			query.addEntry("is_activity", "=", "0");
		return  this.findPage(query,page,rows);
	}

	public Pagination<Interface> querySleep(Interface itf, Integer page,
			Integer rows) {
		QueryCriteria query = new QueryCriteria();
		if(itf!=null){
			if(!CommonUtils.isEmpty(itf.getInterface_name())){
				query.addEntry("interface_name", "like","%"+ itf.getInterface_name()+ "%");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(itf.getOnline_start_time()))){
				query.addEntry("online_time", ">=", itf.getOnline_start_time() +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(itf.getOnline_end_time()))){
				query.addEntry("online_time", "<=", itf.getOnline_end_time() +" 23:59:59");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(itf.getOffline_start_time()))){
				query.addEntry("offline_time", ">=", itf.getOffline_start_time() +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(itf.getOffline_end_time()))){
				query.addEntry("offline_time", "<=", itf.getOffline_end_time() +" 23:59:59");
			}
			if(!CommonUtils.isEmpty(itf.getIs_activity())){
				
				query.addEntry("is_activity", "=",itf.getIs_activity());
			}
			if(!CommonUtils.isEmpty(itf.getInterface_time())){
				
				query.addEntry("interface_time", "=",itf.getInterface_time());
			}
			if(!CommonUtils.isEmpty(itf.getInterface_sort())){
				
				query.addEntry("interface_sort", "=",itf.getInterface_sort());
			}
			if(!CommonUtils.isEmpty(itf.getState())){
				
				query.addEntry("state", "=",itf.getState()); 
			}
		}
			query.addEntry("interface_type", "=","2");
			query.addEntry("is_activity", "=", "0");
		return  this.findPage(query,page,rows);
	}

	public List<Interface> queryPlace(Interface itf) {
		QueryCriteria query = new QueryCriteria();
		if(itf!=null){
			if(!CommonUtils.isEmpty(itf.getInterface_name())){
				query.addEntry("interface_name", "like","%"+ itf.getInterface_name()+ "%");
			}
			if(!CommonUtils.isEmpty(itf.getState())){
				
				query.addEntry("state", "=",itf.getState()); 
			}
		}
			query.addEntry("interface_type", "=","3");
			query.addEntry("is_activity", "=", "0");
		return  this.find(query);
	}	
	  
	public List<Interface> queryFirePic(Interface itf) {
		QueryCriteria query = new QueryCriteria();
		if(itf!=null){
			if(!CommonUtils.isEmpty(itf.getInterface_name())){
				query.addEntry("interface_name", "like","%"+ itf.getInterface_name()+ "%");
			}
			if(!CommonUtils.isEmpty(itf.getState())){
				
				query.addEntry("state", "=",itf.getState()); 
			}
		}
			query.addEntry("interface_type", "=","4");
			query.addEntry("is_activity", "=", "0");
		return  this.find(query);
	}
	public List<Interface> findBySort(Interface itf) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("interface_sort", "=",itf.getInterface_sort());
		query.addEntry("is_activity", "=", "0");
		query.addEntry("interface_type", "=", itf.getInterface_type());
		return this.find(query);
	}

	public List<Interface> getInterfaceList() {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("is_activity", "=", "0");
		query.addEntry("state", "=", CommonFiled.PUSH_STATE_ALREADY);
		return this.find(query);
	}
	//获取非过路歌曲列表
	public int getMaxSortValue() {
		int sort = 0 ; 	
	    QueryCriteria query = new QueryCriteria();
	    query.addEntry("interface_type", "=", "2");
	    query.addEntry("is_activity", "=", "0");
	    query.desc("interface_sort+0");
	    List<Interface> find = this.find(query);
	    	
	    if(find.size()>0){
	    	Interface itf = find.get(0);
	    	sort = new Integer(itf.getInterface_sort());
	    }
	    return sort ; 
	}

}
