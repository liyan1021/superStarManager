package com.liyan.superstar.dao;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.Room;

@Repository
public class RoomDao extends GenericDaoImpl<Room, String> {
	public Pagination<Room> query(Room room, int page, int rows) {
		QueryCriteria query = new QueryCriteria();
		try {
			if (room != null) {
				if (!CommonUtils.isEmpty(room.getDevice_id())) {
					query.addEntry("device_id", "like", "%"
							+ room.getDevice_id() + "%");
				}
				if (!CommonUtils.isEmpty(room.getRoom_no())) {
					query.addEntry("room_no", "like", "%" + room.getRoom_no()
							+ "%");
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return this.findPage(query, page, rows);
	}
}
