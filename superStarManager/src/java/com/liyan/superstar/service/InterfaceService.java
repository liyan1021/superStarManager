package com.liyan.superstar.service;

import java.io.File;
import java.util.List;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.Interface;

public interface InterfaceService {

	/**
	 * 开始界面分页查询
	 * @param itf
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<Interface> query(Interface itf, Integer page, Integer rows);

	/**
	 * 初始界面分页查询 并记录日志
	 * @param itf
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<Interface> search(Interface itf, Integer page, Integer rows);

	/**
	 * 删除初始界面设置
	 * @param list
	 * @return
	 */
	MsgBean removeInterface(List<String> list);

	/**
	 * 编辑初始界面设置
	 * @param itf
	 * @param image
	 * @param imageFileName
	 * @param imageContentType
	 * @return
	 */
	MsgBean editInterface(Interface itf, File image, String imageFileName, String imageContentType);
	/**
	 * 编辑场所介绍设置
	 * @param itf
	 * @param image
	 * @param imageFileName
	 * @param imageContentType
	 * @return
	 */
	MsgBean editPlaceInterface(Interface itf, File image, String imageFileName, String imageContentType);
	/**
	 * 编辑消防图解设置
	 * @param itf
	 * @param image
	 * @param imageFileName
	 * @param imageContentType
	 * @return
	 */
	MsgBean editFirePicInterface(Interface itf, File image, String imageFileName, String imageContentType);
	/**
	 * 新增初始界面设置
	 * @param itf
	 * @param image
	 * @param imageFileName
	 * @param imageContentType
	 * @return
	 */
	MsgBean create(Interface itf, File image, String imageFileName, String imageContentType);

	/**
	 * 分页查询屏保界面
	 * @param itf
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<Interface> querySlepp(Interface itf, Integer page, Integer rows);
	/**
	 * 查询场所介绍界面
	 * @param itf
	 * @return
	 */
	List<Interface> queryPlace(Interface itf);
    /**
     * 查询消防图解界面
     * @param itf
     * @return
     */
	List<Interface> queryFirePic(Interface itf);

	/**
	 * 分页查询屏保界面 并记录日志
	 * @param itf
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<Interface> searchSlepp(Interface itf, Integer page, Integer rows);
	/**
	 * 删除屏保界面设置
	 * @param list
	 * @return
	 */
	MsgBean removeSleppInterface(List<String> list);

	/**
	 * 编辑屏保界面设置
	 * @param itf
	 * @param image
	 * @param imageFileName
	 * @param imageContentType
	 * @return
	 */
	MsgBean editSleppInterface(Interface itf, File image, String imageFileName,
			String imageContentType);
	/**
	 * 创建屏保界面设置
	 * @param itf
	 * @param image
	 * @param imageFileName
	 * @param imageContentType
	 * @return
	 */
	MsgBean createSlepp(Interface itf, File image, String imageFileName,
			String imageContentType);
	
	MsgBean createSleepList(Interface itf, File image, String imageFileName);
	/**
	 * 创建场所介绍界面
	 * @param itf
	 * @param image
	 * @param imageFileName
	 * @param imageContentType
	 * @return
	 */
	MsgBean createPlace(Interface itf, File image, String imageFileName,
			String imageContentType);
	/**
	 * 创建消防图解界面
	 * @param itf
	 * @param image
	 * @param imageFileName
	 * @param imageContentType
	 * @return
	 */
	MsgBean createFirePic(Interface itf, File image, String imageFileName,
			String imageContentType);
	/**
	 * 发布屏保设置
	 * @param ids
	 * @return
	 */
	MsgBean pushSleepInterface(String ids);
	/**
	 * 发布场所介绍设置
	 * @param ids
	 * @return
	 */
	MsgBean pushPlaceInterface(String ids);
	/**
	 * 发布消防图解设置
	 * @param ids
	 * @return
	 */
	MsgBean pushFirePicInterface(String ids);

	/**
	 * 发布初始界面设置
	 * @param ids
	 * @return
	 */
	MsgBean pushStartInterface(String ids);

	/**
	 * 验证顺序是否唯一
	 * @param itf
	 * @return
	 */
	MsgBean checkInterfaceSort(Interface itf);
    /**
     * 获取最大的排序值
     * @return
     */
	int getMaxSortValue();
		
	
}
