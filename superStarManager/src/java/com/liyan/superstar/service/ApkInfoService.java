package com.liyan.superstar.service;

import java.io.File;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.ApkInfo;
import com.liyan.superstar.model.PassMusic;
import com.liyan.superstar.model.Room;

public interface ApkInfoService {

	/**
	 * 分页查询APK版本列表
	 * @param apkInfo
	 * @param start_time
	 * @param end_time
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<ApkInfo> query(ApkInfo apkInfo,String start_time,String end_time, Integer page, Integer rows,String sort,String order);

	/**
	 * 分页查询APK版本列表 并记录日志
	 * @param apkInfo
	 * @param start_time
	 * @param end_time
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<ApkInfo> searchApkInfo(ApkInfo apkInfo,String start_time,String end_time, Integer page,
			Integer rows);

	/**
	 * 删除APK
	 * @param idsList
	 * @return
	 */
	MsgBean removeApkInfo(String[] idsList);

	/**
	 * 上传APK
	 * @param apkInfo
	 * @param apk_file
	 * @param fileName
	 * @param contentType
	 * @return
	 */
	MsgBean saveApkInfo(ApkInfo apkInfo, File apk_file, String fileName,
			String contentType);

	MsgBean checkApkVersionSort(ApkInfo apkInfo);
    MsgBean pushApk(String ids);
	MsgBean pushApkReady(String ids,String room_id,String room_no);
   
	Pagination<Room> queryRoom(Room room, int page,int rows);
}
