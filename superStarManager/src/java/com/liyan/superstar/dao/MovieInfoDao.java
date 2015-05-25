package com.liyan.superstar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.MovieInfo;

@Repository
public class MovieInfoDao  extends GenericDaoImpl<MovieInfo, String>{

	public Pagination<MovieInfo> query(MovieInfo movieInfo, Integer page,
			Integer rows, String sort, String order) {
		QueryCriteria query = new QueryCriteria();
		if(movieInfo !=null){
			if(!CommonUtils.isEmpty(movieInfo.getMovie_name())){
				query.addEntry("movie_name", "like", "%"+movieInfo.getMovie_name()+"%");
			}
			if(!CommonUtils.isEmpty(movieInfo.getMovie_info())){
				query.addEntry("movie_info", "like", "%"+movieInfo.getMovie_info()+"%");
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

	public List<MovieInfo> findBySort(MovieInfo movieInfo) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("sort", "=",movieInfo.getSort());
		query.addEntry("is_activity", "=", "0");
		return this.find(query);
	}

}
