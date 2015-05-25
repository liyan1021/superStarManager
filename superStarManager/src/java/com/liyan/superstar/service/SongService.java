package com.liyan.superstar.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.PassMusic;
import com.liyan.superstar.model.Singer;
import com.liyan.superstar.model.Song;

public interface SongService {

	/**
	 * 导出曲库
	 * @param song
	 */
	void exportMusic(Song song);

	/**
	 * 分页查询歌曲
	 * @param song
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<Song> query(Song song, Integer page, Integer rows,String sort,String order);
	/**
	 * 非过路歌曲列表
	 * @param singer
	 * @param page
	 * @param rows
	 * @return
	 */
	//获取非过路歌曲
	Pagination<Song> unPassMusicList(Song song, Integer page, Integer rows);
	
	/**
	 * 编辑歌曲
	 * @param song
	 * @return
	 */
	MsgBean editMusic(Song song);

	/**
	 * 导入曲库
	 * @param importFile
	 * @return
	 */
	MsgBean importMusic(File importFile);
	
	MsgBean newImportMusic(File importFile);
	
	void checkAndInsertMusic(int sheetIndex, int curRow, HashMap<String, String> rowMap);

}
