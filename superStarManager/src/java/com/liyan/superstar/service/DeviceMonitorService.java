package com.liyan.superstar.service;

import java.util.List;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.DeviceMonitor;
import com.liyan.superstar.model.Room;

public interface DeviceMonitorService {

	Pagination<DeviceMonitor> query(DeviceMonitor deviceMonitor, Integer page,
			Integer rows, String sort, String order);

	Pagination<DeviceMonitor> searchDeviceMonitor(DeviceMonitor deviceMonitor,
			Integer page, Integer rows, String sort, String order);

	Pagination<Room> openRoomList(Integer page, Integer rows, String sort,
			String order);

	MsgBean setRoomState(List<String> list, String roomState);

}
