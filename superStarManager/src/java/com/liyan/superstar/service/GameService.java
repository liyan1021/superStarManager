package com.liyan.superstar.service;

import java.io.File;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.Game;

public interface GameService {

	Pagination<Game> query(Game game,String start_time,String end_time,  Integer page, Integer rows,String sort,String order);

	MsgBean removeGame(String ids);

	MsgBean importGame(File game_file, String game_fileFileName);

	Pagination<Game> search(Game game, String start_time,String end_time, Integer page, Integer rows, String sort,
			String order);

	MsgBean pushGame(String ids);

	MsgBean saveGame(Game game);

	MsgBean editGame(Game game);

}
