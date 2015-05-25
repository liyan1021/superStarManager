package com.liyan.superstar.dao;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.DeviceMonitor;

@Repository
public class DeviceMonitorDao extends GenericDaoImpl<DeviceMonitor, String> {

	public Pagination<DeviceMonitor> query(DeviceMonitor deviceMonitor, Integer page, Integer rows,
			String sort, String order) {
		QueryCriteria query = new QueryCriteria();
		if(deviceMonitor!=null){
			if(!CommonUtils.isEmpty(deviceMonitor.getDevice_id())){
				query.addEntry("device_id", "like", "%"+deviceMonitor.getDevice_id()+"%");
			}
			if(!CommonUtils.isEmpty(deviceMonitor.getDevice_name())){
				query.addEntry("device_name", "like", "%"+deviceMonitor.getDevice_name()+"%");			
			}
			if(!CommonUtils.isEmpty(deviceMonitor.getNet_state()) && ! deviceMonitor.getNet_state().equals("0")){
				
				query.addEntry("net_state", "=", deviceMonitor.getNet_state());			
			}
			if(!CommonUtils.isEmpty(deviceMonitor.getDevice_state())&& ! deviceMonitor.getDevice_state().equals("0")){
				query.addEntry("device_state", "=", deviceMonitor.getDevice_state());			
				
			}
			if(!CommonUtils.isEmpty(deviceMonitor.getRoom_state())&& ! deviceMonitor.getRoom_state().equals("0")){
				query.addEntry("room_state", "=", deviceMonitor.getRoom_state());			
				
			}
			if(!CommonUtils.isEmpty(deviceMonitor.getRoom())){
				query.addEntry("room", "like", "%"+deviceMonitor.getRoom()+"%");			
				
			}
			if(!CommonUtils.isEmpty(deviceMonitor.getStore())){
				query.addEntry("store", "like", "%"+deviceMonitor.getStore()+"%");			
				
			}
		}
		if(!CommonUtils.isEmpty(sort)){
			if(order.equals("asc")){
				query.asc(sort);
			}else if(order.equals("desc")){
				query.desc(sort);
			}
		}
		return this.findPage(query, page, rows);
	}
	
}
