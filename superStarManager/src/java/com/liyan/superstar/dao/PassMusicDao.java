package com.liyan.superstar.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.dto.KappPassMusic;
import com.liyan.superstar.dto.KappSong;
import com.liyan.superstar.model.PassMusic;

@Repository
public class PassMusicDao extends GenericDaoImpl<PassMusic, String>{
	
	@Autowired
	private JdbcTemplate jdbcTemplate ; 
	public Pagination<PassMusic> query(PassMusic passMusic, Integer page,
			Integer rows) {
		QueryCriteria query = new QueryCriteria();
		if(passMusic!=null){
			if(!CommonUtils.isEmpty(passMusic.getSong_name())){
				query.addEntry("song_name", "like", "%"+passMusic.getSong_name()+"%");
			}
			if(!CommonUtils.isEmpty(passMusic.getSinger_name())){
				query.addEntry("singer_name", "like", "%"+passMusic.getSinger_name()+"%");
			}
			if(!CommonUtils.isEmpty(passMusic.getPass_sort())){
				query.addEntry("pass_sort", "=", passMusic.getPass_sort());
			}
			if(!CommonUtils.isEmpty(passMusic.getIs_activity())){
				query.addEntry("is_activity", "=", passMusic.getIs_activity());
			}
		}
		query.addEntry("is_activity", "=", "0");
		return this.findPage(query, page, rows);
	}

	public List<PassMusic> findBySort(PassMusic passMusic) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("pass_sort", "=",passMusic.getPass_sort());
		query.addEntry("is_activity", "=", "0");
		return this.find(query);
	}

	public List<KappPassMusic> getPassMusicList() {

		StringBuffer sb = new StringBuffer("select "
				 +" a.SONG_ID,"
				 +" a.SONG_NAME,"
				 +" a.PASS_SORT,"
				 +" b.FILE_PATH,"
				 +" b.ORIGINAL_TRACK,"
				 +" b.ORIGINAL_CHANNEL,"
				 +" b.ACCOMPANY_TRACK,"
				 +" b.ACCOMPANY_CHANNEL,"
				 +" b.ACCOMPANY_VOLUME,"
				 +" b.KARAOKE_VOLUME"
				);
		sb.append(" from vod_store_pass a , vod_store_song b where a.song_id = b.song_id and a.is_activity = '0' and a.state='1' ");
		final List<KappPassMusic> temp_list =new ArrayList<KappPassMusic>();
		jdbcTemplate.query(sb.toString(), new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) {
				
				try{
					KappPassMusic ks = new KappPassMusic();
					ks.setSong_id(rs.getString("song_id"));
					ks.setSong_name(rs.getString("song_name"));
					ks.setPass_sort(rs.getString("pass_sort"));
					ks.setFile_path(rs.getString("file_path"));
					ks.setOriginal_track(rs.getString("original_track"));
					ks.setOriginal_channel(rs.getString("original_channel"));
					ks.setAccompany_track(rs.getString("accompany_track"));
					ks.setAccompany_channel(rs.getString("accompany_channel"));
					ks.setAccompany_volume(rs.getString("accompany_volume"));
					ks.setKaraoke_volume(rs.getString("karaoke_volume"));
					
					temp_list.add(ks);
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		return temp_list;
	
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
