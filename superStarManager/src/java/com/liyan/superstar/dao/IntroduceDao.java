package com.liyan.superstar.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.dto.KappIntroduceMusic;
import com.liyan.superstar.dto.KappPassMusic;
import com.liyan.superstar.model.Introduce;
import com.liyan.superstar.model.Song;

@Repository
public class IntroduceDao extends GenericDaoImpl<Introduce, String> {
	
	@Autowired
	private JdbcTemplate jdbcTemplate ; 
	public Pagination<Introduce> query(Introduce introduce, Integer page,
			Integer rows) {
		QueryCriteria query = new QueryCriteria();
		if(introduce!=null){
			if(!CommonUtils.isEmpty(introduce.getSong().getSong_name())){
				query.addEntry("music_name", "like", "%"+introduce.getSong().getSong_name()+"%");
			}
			if(!CommonUtils.isEmpty(introduce.getSong().getSinger_name())){
				query.addEntry("song.singer_name", "like", "%"+introduce.getSong().getSinger_name()+"%");
			}
			if(!CommonUtils.isEmpty(introduce.getIntroduce_sort())){
				query.addEntry("introduce_sort", "=", introduce.getIntroduce_sort());
			}
		}
		query.addEntry("is_activity", "=", "0");
		return this.findPage(query, page, rows);
	}

	public List<Introduce> findBySort(Introduce introduce) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("introduce_sort", "=",introduce.getIntroduce_sort());
		query.addEntry("is_activity", "=", "0");
		return this.find(query);
	}

	public Pagination<Song> queryMusicList(Song song, Integer page, Integer rows,
			String sort, String order) {
		
		Pagination<Song> pagination = new Pagination<Song>();
		pagination.setPageNo(page);
		pagination.setPageSize(rows);
		
		StringBuffer sb = new StringBuffer("from Song a where 1=1");
		sb.append(" and is_activity = '0' ");
		if(song!=null){
			if(!CommonUtils.isEmpty(song.getSinger_name())){
				sb.append(" and singer_name like '%"+song.getSinger_name()+"%'");
			}
			if(!CommonUtils.isEmpty(song.getSong_name())){
				sb.append(" and song_name like '%"+song.getSong_name()+"%'");
			}
		}
		sb.append(" and not EXISTS (from  Introduce b where a.song_id = b.song.song_id and b.is_activity ='0')");
		Query query = entityManager.createQuery(sb.toString());
	                    /*.setParameter("endTime", endTime)
	                    .setParameter("deptId", deptId);*/
		//获取总数
		pagination.setRecordCount(query.getResultList().size());
		//分页
		query.setFirstResult(pagination.getPageSize() * (pagination.getPageNo() - 1));
        query.setMaxResults(pagination.getPageSize());
		
		pagination.setResultList(query.getResultList());
		return pagination;
	}

	public List<KappIntroduceMusic> getIntroduceList() {
		StringBuffer sb = new StringBuffer("select "
				+" a.MUSIC_ID,"
				+" a.MUSIC_NAME,"
				+" a.INTRODUCE_SORT,"
				+" b.SPELL_FIRST_LETTER_ABBREVIATION,"
				+" b.SINGER_ID,"
				+" b.SINGER_NAME,"
				+" b.SINGER_TYPE,"
				+" c.STAR_HEAD,"
				+" b.LYRIC,"
				+" b.FILE_PATH,"
				+" b.ORIGINAL_TRACK,"
				+" b.ORIGINAL_CHANNEL,"
				+" b.ACCOMPANY_TRACK,"
				+" b.ACCOMPANY_CHANNEL,"
				+" b.ACCOMPANY_VOLUME,"
				+" b.KARAOKE_VOLUME,"
				+" b.ISSUE_YEAR,"
				+" b.SONG_THEME,"
				+" b.MOVIE_TYPE,"
				+" b.SONG_TYPE,"
				+" b.LANGUAGE,"
				+" b.VERSION"
				);
		sb.append(" from vod_store_music_introduce a , vod_store_song b,vod_store_singer c where a.music_id = b.song_id and b.singer_id = c.star_id and a.is_activity = '0' and a.state='1' ");
		final List<KappIntroduceMusic> temp_list =new ArrayList<KappIntroduceMusic>();
		jdbcTemplate.query(sb.toString(), new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) {
				
				try{
					KappIntroduceMusic ks = new KappIntroduceMusic();
					ks.setSong_id(rs.getString("music_id"));
					ks.setSong_name(rs.getString("music_name"));
					ks.setIndroduce_sort(rs.getString("introduce_sort"));
					ks.setSpell_first_letter_abbreviation(rs.getString("spell_first_letter_abbreviation"));
					ks.setSinger_id(rs.getString("singer_id"));
					ks.setSinger_name(rs.getString("singer_name"));
					ks.setSinger_type(rs.getString("singer_type"));
					ks.setSinger_head(rs.getString("star_head"));
					ks.setLyric(rs.getString("lyric"));
					ks.setFile_path(rs.getString("file_path"));
					ks.setOriginal_track(rs.getString("original_track"));
					ks.setOriginal_channel(rs.getString("original_channel"));
					ks.setAccompany_track(rs.getString("accompany_track"));
					ks.setAccompany_channel(rs.getString("accompany_channel"));
					ks.setAccompany_volume(rs.getString("accompany_volume"));
					ks.setKaraoke_volume(rs.getString("karaoke_volume"));
					ks.setIssue_year(rs.getString("issue_year"));
					ks.setSong_theme(rs.getString("song_theme"));
					ks.setMovie_type(rs.getString("movie_type"));
					ks.setSong_type(rs.getString("song_type"));
					ks.setLanguage(rs.getString("language"));
					ks.setVersion(rs.getString("version"));
					
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
