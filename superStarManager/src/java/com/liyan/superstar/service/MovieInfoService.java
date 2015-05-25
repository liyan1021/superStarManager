package com.liyan.superstar.service;

import java.io.File;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.MovieInfo;

public interface MovieInfoService {

	MsgBean pushMovie(String ids);

	Pagination<MovieInfo> search(MovieInfo movieInfo, Integer page,
			Integer rows, String sort, String order);

	MsgBean saveMovieInfo(MovieInfo movieInfo, File image, String imageFileName);

	MsgBean removeMovieInfo(String ids);

	Pagination<MovieInfo> query(MovieInfo movieInfo, Integer page,
			Integer rows, String sort, String order);

	MsgBean editMovieInfo(MovieInfo movieInfo, File image, String imageFileName);

	MsgBean checkAdvertSort(MovieInfo movieInfo);

}
