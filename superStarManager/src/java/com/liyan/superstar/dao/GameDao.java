package com.liyan.superstar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.Game;

@Repository
public class GameDao extends GenericDaoImpl<Game, String> {

	public Pagination<Game> query(Game game,String start_time,String end_time , Integer page, Integer rows,
			String sort, String order) {
		QueryCriteria query = new QueryCriteria();
		if(game!=null){
			if(!CommonUtils.isEmpty(game.getGame_name())){
				query.addEntry("game_name", "like", "%"+game.getGame_name()+"%");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(start_time))){
				query.addEntry("import_time", ">=", start_time +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(end_time))){
				query.addEntry("import_time", "<=", end_time +" 23:59:59");
			}
		}
		query.addEntry("is_activity", "=", "0");
		if(!CommonUtils.isEmpty(sort)){
			if(order.equals("asc")){
				query.asc(sort);
			}else if(order.equals("desc")){
				query.desc(sort);
			}
		}
		
		return this.findPage(query, page, rows);
	}

	public List<Game> getGameList() {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("is_activity", "=", "0");
		query.addEntry("state", "=",CommonFiled.PUSH_STATE_ALREADY);
		return this.find(query);
	}

}
