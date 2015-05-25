package com.liyan.servlet.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.liyan.servlet.bean.DictBean;

public class DictService {

	public static List<DictBean> findDictList(JdbcTemplate jdbcTemplate){
		final List<DictBean> dictList = new ArrayList<DictBean>();
		String q_sql = "select id,dict_type_code,dict_type,dict_code,dict_value,import_time,file_path,dict_sort from vod_store_dict where dict_type_code in ('theme','song_type') and is_activity='0' ";
		jdbcTemplate.query(q_sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) {
				try {
					//while(rs.next()){
					DictBean dictBean = new DictBean();
					dictBean.setId(rs.getString("id"));
					dictBean.setDictTypeCode(rs.getString("dict_type_code"));
					dictBean.setDictType(rs.getString("dict_type"));
					dictBean.setDictCode(rs.getString("dict_code"));
					dictBean.setDictValue(rs.getString("dict_value"));
					dictBean.setImportTime(rs.getString("import_time"));
					dictBean.setFilePath(rs.getString("file_path"));
					dictBean.setDictSort(rs.getString("dict_sort"));
					dictList.add(dictBean);
					//}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		return dictList;
	}
}
