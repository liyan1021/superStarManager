package com.liyan.superstar.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.Singer;

public interface SingerService {

	Pagination<Singer> query(Singer singer, Integer page, Integer rows, String sortName, String sortOrder);

	Pagination<Singer> search(Singer singer, Integer page, Integer rows,String sortName, String sortOrder);

	MsgBean removeSinger(List<String> list);

	MsgBean create(Singer singer, File image, String imageFileName, HttpServletRequest request);

	MsgBean editSinger(Singer singer, File image, String imageFileName,
			HttpServletRequest request);

	
	/**
	 * 导出歌星
	 * @param singer
	 */
	void exportSinger(Singer singer);

	/**
	 * 导入歌星
	 * @param importFile
	 * @return
	 */
	MsgBean importSinger(File importFile);

	MsgBean checkSingerName(Singer singer);
	

}
