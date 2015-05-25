package com.liyan.superstar.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.liyan.superstar.model.PassMusic;
import com.liyan.superstar.model.Song;
import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.dto.KappSong;
@Repository
public class SongDao extends GenericDaoImpl<Song, String>{
	@Autowired
	private JdbcTemplate jdbcTemplate ;
	
	public Pagination<Song> query(Song song, Integer page, Integer rows, String sort, String order) {
		QueryCriteria query = new QueryCriteria();
		if(song != null){
			if(!CommonUtils.isEmpty(song.getSong_name())){
				query.addEntry("song_name", "like", "%" + song.getSong_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(song.getSpell_first_letter_abbreviation())){
				query.addEntry("spell_first_letter_abbreviation", "like", "%"+song.getSpell_first_letter_abbreviation() + "%" );
			}
			if(!CommonUtils.isEmpty(song.getSinger_name())){
				query.addEntry("singer_name", "like","%"+ song.getSinger_name()+"%" );
			}if(!CommonUtils.isEmpty(song.getSinger_type()) ){
				query.addEntry("singer_type", "like","%"+ song.getSinger_type()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getVersion())){
				query.addEntry("version", "=",song.getVersion() );
			}
			if(!CommonUtils.isEmpty(song.getLyric())){
				query.addEntry("lyric", "like","%"+ song.getLyric()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getIssue_year()) ){
				query.addEntry("issue_year", "=",song.getIssue_year() );
			}
			if(!CommonUtils.isEmpty(song.getResolution())){
				query.addEntry("resolution", "like","%"+ song.getResolution()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getRecord_company())){
				query.addEntry("record_company", "like","%"+ song.getRecord_company()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getSong_theme())){
				query.addEntry("song_theme", "like","%"+ song.getSong_theme()+"%" );
			}
			
			if(!CommonUtils.isEmpty(song.getMovie_type())){
				query.addEntry("movie_type", "like","%"+ song.getMovie_type()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getMovie_type_info())){
				query.addEntry("movie_type_info", "like","%"+ song.getMovie_type_info()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getSong_type())){
				query.addEntry("song_type", "like","%"+ song.getSong_type()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getLanguage())){
				query.addEntry("language", "like","%"+ song.getLanguage()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getClick_number())){
				query.addEntry("click_number", "like","%"+ song.getClick_number()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getCollection_number())){
				query.addEntry("collection_number", "like","%"+ song.getCollection_number()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getAuthors())){
				query.addEntry("authors", "like","%"+ song.getAuthors()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getCompose())){
				query.addEntry("compose", "like","%"+ song.getCompose()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getOriginal_track())){
				query.addEntry("original_track", "like","%"+ song.getOriginal_track()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getOriginal_channel())){
				query.addEntry("original_channel", "like","%"+ song.getOriginal_channel()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getAccompany_track())){
				query.addEntry("accompany_track", "like","%"+ song.getAccompany_track()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getAccompany_channel())){
				query.addEntry("accompany_channel", "like","%"+ song.getAccompany_channel()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getStorage_type())){
				query.addEntry("storage_type", "like","%"+ song.getStorage_type()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getPlay_time())){
				query.addEntry("play_time", "like","%"+ song.getPlay_time()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getFlow_type())){
				query.addEntry("flow_type", "like","%"+ song.getFlow_type()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getSong_bit_rate())){
				query.addEntry("song_bit_rate", "like","%"+ song.getSong_bit_rate()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getRemark())){
				query.addEntry("remark", "like","%"+ song.getRemark()+"%" );
			}
			 
		}		 
		//获取未删除的歌曲
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
	public Pagination<Song> query(Song song,PassMusic music,Integer page, Integer rows){
		QueryCriteria query = new QueryCriteria();
		  query.leftFetch(music.getPass_id());
		  return this.findPage(query, page, rows);
		}
	//获取非过路歌曲列表
	public Pagination<Song> unPassMusicList(Song song, Integer page,
			Integer rows) {
		
		Pagination<Song> pagination = new Pagination<Song>();
		pagination.setPageNo(page);
		pagination.setPageSize(rows);
		
		StringBuffer sb = new StringBuffer("from Song a where 1=1");
		sb.append(" and is_activity = '0' ");
		if(song!=null){
			if(!CommonUtils.isEmpty(song.getSong_name())){
				sb.append(" and song_name like '%"+song.getSong_name()+"%'");
			}
			if(!CommonUtils.isEmpty(song.getSinger_name())){
				sb.append(" and singer_name like '%"+song.getSinger_name()+"%'");
			}
		}
		sb.append(" and not EXISTS (from  PassMusic b where a.song_id = b.song_id and b.is_activity ='0')");
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
    public List<Song> query(Song song){
		QueryCriteria query = new QueryCriteria();
		if(song != null){
			if(!CommonUtils.isEmpty(song.getSong_name())){
				query.addEntry("song_name", "like", "%" + song.getSong_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(song.getSpell_first_letter_abbreviation())){
				query.addEntry("spell_first_letter_abbreviation", "like", "%"+song.getSpell_first_letter_abbreviation() + "%" );
			}
			if(!CommonUtils.isEmpty(song.getSinger_name())){
				query.addEntry("singer_name", "like","%"+ song.getSinger_name()+"%" );
			}if(!CommonUtils.isEmpty(song.getSinger_type())){
				query.addEntry("singer_type", "like","%"+ song.getSinger_type()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getVersion())){
				query.addEntry("version", "like","%"+ song.getVersion()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getLyric())){
				query.addEntry("lyric", "like","%"+ song.getLyric()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getIssue_year())){
				query.addEntry("issue_year", "like","%"+ song.getIssue_year()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getResolution())){
				query.addEntry("resolution", "like","%"+ song.getResolution()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getRecord_company())){
				query.addEntry("record_company", "like","%"+ song.getRecord_company()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getSong_theme())){
				query.addEntry("song_theme", "like","%"+ song.getSong_theme()+"%" );
			}
			
			if(!CommonUtils.isEmpty(song.getMovie_type())){
				query.addEntry("movie_type", "like","%"+ song.getMovie_type()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getMovie_type_info())){
				query.addEntry("movie_type_info", "like","%"+ song.getMovie_type_info()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getSong_type())){
				query.addEntry("song_type", "like","%"+ song.getSong_type()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getLanguage())){
				query.addEntry("language", "like","%"+ song.getLanguage()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getClick_number())){
				query.addEntry("click_number", "like","%"+ song.getClick_number()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getCollection_number())){
				query.addEntry("collection_number", "like","%"+ song.getCollection_number()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getAuthors())){
				query.addEntry("authors", "like","%"+ song.getAuthors()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getCompose())){
				query.addEntry("compose", "like","%"+ song.getCompose()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getOriginal_track())){
				query.addEntry("original_track", "like","%"+ song.getOriginal_track()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getOriginal_channel())){
				query.addEntry("original_channel", "like","%"+ song.getOriginal_channel()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getAccompany_track())){
				query.addEntry("accompany_track", "like","%"+ song.getAccompany_track()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getAccompany_channel())){
				query.addEntry("accompany_channel", "like","%"+ song.getAccompany_channel()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getStorage_type())){
				query.addEntry("storage_type", "like","%"+ song.getStorage_type()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getPlay_time())){
				query.addEntry("play_time", "like","%"+ song.getPlay_time()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getFlow_type())){
				query.addEntry("flow_type", "like","%"+ song.getFlow_type()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getSong_bit_rate())){
				query.addEntry("song_bit_rate", "like","%"+ song.getSong_bit_rate()+"%" );
			}
			if(!CommonUtils.isEmpty(song.getRemark())){
				query.addEntry("remark", "like","%"+ song.getRemark()+"%" );
			}
		}
		//获取未删除的歌曲
		query.addEntry("is_activity", "=", "0");
		return this.find(query);
	}

	public  Song findById(String trim) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("song_id", "=", trim);
		//获取未删除的歌曲
		query.addEntry("is_activity", "=", "0");
		List<Song> find = this.find(query);
		if(find.size()>0){
			return find.get(0);
		}else{
			return null ; 
		}
	}

	public Object findByName(String trim) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("file_name", "=", trim);
		//获取未删除的歌曲
		query.addEntry("is_activity", "=", "0");
		List<Song> find = this.find(query);
		if(find.size()>0){
			return find.get(0);
		}else{
			return null ; 
		}
	}
	
	public List<KappSong> getSongList() {
		StringBuffer sb = new StringBuffer("select "
				+" a.song_id,"
				+" a.song_name,"
				+" a.spell_first_letter_abbreviation,"
				+" b.star_id,"
				+" a.singer_name,"
				+" b.star_type,"
				+" b.star_head,"
				+" a.lyric,"
				+" a.file_path,"
				+" a.original_track,"
				+" a.original_channel,"
				+" a.accompany_track,"
				+" a.accompany_channel,"
				+" a.accompany_volume,"
				+" a.karaoke_volume,"
				+" a.issue_year,"
				+" a.song_theme,"
				+" a.movie_type,"
				+" a.song_type,"
				+" a.language,"
				+" a.is_new,"
				+" a.version,"
				+" a.click_number ");
		sb.append(" from vod_store_song a left join vod_store_singer b on a.singer_id = b.star_id and a.is_activity = '0' ");
		final List<KappSong> temp_list =new ArrayList<KappSong>();
		jdbcTemplate.query(sb.toString(), new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) {
				
				try{
					KappSong ks = new KappSong();
					ks.setSong_id(rs.getString("song_id"));
					ks.setSong_name(rs.getString("song_name"));
					ks.setSpell_first_letter_abbreviation(rs.getString("spell_first_letter_abbreviation"));
					ks.setStar_id(rs.getString("star_id"));
					ks.setStar_name(rs.getString("singer_name"));
					ks.setStar_type(rs.getString("star_type"));
					ks.setStar_head(rs.getString("star_head"));
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
					ks.setIs_new(rs.getString("is_new"));
					ks.setVersion(rs.getString("version"));
					
					temp_list.add(ks);
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		return temp_list;
	}

}
