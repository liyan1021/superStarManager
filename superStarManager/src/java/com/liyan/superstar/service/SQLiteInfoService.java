package com.liyan.superstar.service;

import java.io.File;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.Room;
import com.liyan.superstar.model.SQLiteInfo;

public interface SQLiteInfoService {

	/**
	 * 分页查询SQLite 版本
	 * @param sqliteInfo
	 * @param start_time
	 * @param end_time
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<SQLiteInfo> query(SQLiteInfo sqliteInfo, String start_time,
			String end_time, Integer page, Integer rows,String sort,String order);

	/**
	 * 分页查询SQLite 版本，并记录日志
	 * @param sqliteInfo
	 * @param start_time
	 * @param end_time
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<SQLiteInfo> searchSqliteInfo(SQLiteInfo sqliteInfo,
			String start_time, String end_time, Integer page, Integer rows);

	/**
	 * 删除SQLite
	 * @param idsList
	 * @return
	 */
	MsgBean removeSqliteInfo(String[] idsList);

	/**
	 * 保存SQLite
	 * @param sqliteInfo
	 * @param sqlite_file
	 * @param sqlite_fileFileName
	 * @param sqlite_fileContentType
	 * @return
	 */
	MsgBean saveSqliteInfo(SQLiteInfo sqliteInfo, File sqlite_file,
			String sqlite_fileFileName, String sqlite_fileContentType);

	/**
	 * 生成SQLite文件
	 * @return
	 */
	MsgBean generateSqlite();

	MsgBean pushSqlite(String ids);
	
	MsgBean pushSqliteReady(String ids,String room_id,String room_no);   
	Pagination<Room> queryRoom(Room room, int page,int rows);
}
