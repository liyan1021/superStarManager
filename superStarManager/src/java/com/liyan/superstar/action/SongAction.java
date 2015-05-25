package com.liyan.superstar.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.liyan.superstar.dao.SongDao;
import com.liyan.superstar.model.Song;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.superstar.service.SongService;
import com.liyan.common.AppContext;
import com.liyan.common.action.MsgBean;
import com.liyan.common.action.PageAwareActionSupport;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonFiled;
import com.opensymphony.xwork2.Action;
/**
 * @author Administrator
 *	曲库管理
 */
@Controller
@Scope("prototype")
public class SongAction extends PageAwareActionSupport<Song> {

	/**
	 * 
	 */
	@Autowired
	private SongDao songDao ; 
	@Autowired
	private SongService songService ; 
	private static final long serialVersionUID = 1L;
	private Song song ; 
	private String ids ; 
	private File importFile ;
	private String importFileContentType;
	private String importFileFileName ;
	
	private String sort ; 
	private String order; 
	
	@Autowired
	private OperationLogService optLogService ;
	/**
	 * 曲库管理初始化页面
	 * @return
	 */
	public String init(){
		return Action.SUCCESS;
	}
	/**
	 * 曲库管理初始化列表
	 */
	public void musicList(){
		try{
			
			Pagination<Song> pageList = songDao.query(song,page,rows,sort,order);
			List<Song> resultList = pageList.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Song song : resultList){
				JSONObject jsonObject = new JSONObject();
				/*jsonObject.element("song_id",song.getSong_id() );
				jsonObject.element("song_track",song.getSong_track() );
				jsonObject.element("song_channel",song.getSong_channel() );
				jsonObject.element("is_stereo",song.getIs_stereo() );
				jsonObject.element("song_name",song.getSong_name() );
				jsonObject.element("movie_info",song.getMovie_info() );
				jsonObject.element("song_set",song.getSong_set() );
				jsonObject.element("accompany_volume",song.getAccompany_volume() );
				jsonObject.element("karaoke_volume",song.getKaraoke_volume() );
				jsonObject.element("language",song.getLanguage() );
				jsonObject.element("song_type",song.getSong_type() );
				jsonObject.element("song_word_count",song.getSong_word_count() );
				jsonObject.element("singer_number",song.getSinger_number() );
				jsonObject.element("singer_name",song.getSinger_name() );
				jsonObject.element("singer_sex",song.getSinger_sex() );
				jsonObject.element("spell_first_letter_abbreviation",song.getSpell_first_letter_abbreviation() );
				jsonObject.element("spell_first_letter_traditional",song.getSpell_first_letter_traditional() );
				jsonObject.element("movie_spell_first_letter",song.getMovie_spell_first_letter() );
				jsonObject.element("click_number",song.getClick_number() );
				jsonObject.element("song_version",song.getSong_version() );
				jsonObject.element("light_control_set",song.getLight_control_set() );
				jsonObject.element("audio_effect_set",song.getAudio_effect_set() );
				jsonObject.element("file_format",song.getFile_format() );
				jsonObject.element("song_bit_rate",song.getSong_bit_rate() );
				jsonObject.element("song_theme",song.getSong_theme() );
				jsonObject.element("first_word",song.getFirst_word() );
				jsonObject.element("local_path",song.getLocal_path() );
				jsonObject.element("server_path",song.getServer_path() );
				jsonObject.element("server_path1",song.getServer_path1() );
				jsonObject.element("relative_path",song.getRelative_path() );
				jsonObject.element("file_size",song.getFile_size() );
				jsonObject.element("video_saturation",song.getVideo_saturation() );
				jsonObject.element("video_luminance",song.getVideo_luminance() );
				jsonObject.element("video_contrast",song.getVideo_contrast() );
				jsonObject.element("lyric",song.getLyric() );
				jsonObject.element("word_head_code",song.getWord_head_code() );
				jsonObject.element("culture_code",song.getCulture_code() );
				jsonObject.element("issue_year",song.getIssue_year() );
				jsonObject.element("new_song",song.getNew_song() ); */

				jsonObject.element("song_id",song.getSong_id() );
				jsonObject.element("file_name",song.getFile_name() );
				jsonObject.element("superstar_id",song.getSuperstar_id() );
				jsonObject.element("unsuperstar_id",song.getUnsuperstar_id() );
				jsonObject.element("song_name",song.getSong_name() );
				jsonObject.element("spell_first_letter_abbreviation",song.getSpell_first_letter_abbreviation() );
				jsonObject.element("singer_id",song.getSinger_id() );
				jsonObject.element("singer_name",song.getSinger_name() );
				jsonObject.element("singer_type",song.getSinger_type() );
				jsonObject.element("version",song.getVersion() );
				jsonObject.element("accompany_volume",song.getAccompany_volume() );
				jsonObject.element("karaoke_volume",song.getKaraoke_volume() );
				jsonObject.element("lyric",song.getLyric() );
				jsonObject.element("issue_year",song.getIssue_year() );
				jsonObject.element("resolution",song.getResolution() );
				jsonObject.element("record_company",song.getRecord_company() );
				jsonObject.element("song_theme",song.getSong_theme() );
				jsonObject.element("movie_type",song.getMovie_type() );
				jsonObject.element("movie_type_info",song.getMovie_type_info() );
				jsonObject.element("song_type",song.getSong_type() );
				jsonObject.element("language",song.getLanguage() );
				jsonObject.element("click_number",song.getClick_number() );
				jsonObject.element("collection_number",song.getCollection_number() );
				jsonObject.element("authors",song.getAuthors() );
				jsonObject.element("compose",song.getCompose() );
				jsonObject.element("original_track",song.getOriginal_track() );
				jsonObject.element("original_channel",song.getOriginal_channel() );
				jsonObject.element("accompany_track",song.getAccompany_track() );
				jsonObject.element("accompany_channel",song.getAccompany_channel() );
				jsonObject.element("storage_type",song.getStorage_type() );
				jsonObject.element("file_path",song.getFile_path() );
				jsonObject.element("file_length",song.getFile_length() );
				jsonObject.element("file_format",song.getFile_format() );
				jsonObject.element("light_control_set",song.getLight_control_set() );
				jsonObject.element("play_time",song.getPlay_time());
				jsonObject.element("flow_type",song.getFlow_type() );
				jsonObject.element("song_bit_rate",song.getSong_bit_rate() );
				jsonObject.element("import_time",song.getImport_time() );
				jsonObject.element("idx_sha1",song.getIdx_sha1() );
				jsonObject.element("dgx_sha1",song.getDgx_sha1() );
				jsonObject.element("remark",song.getRemark() );

				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", pageList.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 查询曲库
	 */
	public void searchMusicList(){
		try{
			Pagination<Song> pageList = songDao.query(song,page,rows,sort,order);
			List<Song> resultList = pageList.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Song song : resultList){
				JSONObject jsonObject = new JSONObject();
				/*jsonObject.element("song_id",song.getSong_id() );
				jsonObject.element("song_track",song.getSong_track() );
				jsonObject.element("song_channel",song.getSong_channel() );
				jsonObject.element("is_stereo",song.getIs_stereo() );
				jsonObject.element("song_name",song.getSong_name() );
				jsonObject.element("movie_info",song.getMovie_info() );
				jsonObject.element("song_set",song.getSong_set() );
				jsonObject.element("accompany_volume",song.getAccompany_volume() );
				jsonObject.element("karaoke_volume",song.getKaraoke_volume() );
				jsonObject.element("language",song.getLanguage() );
				jsonObject.element("song_type",song.getSong_type() );
				jsonObject.element("song_word_count",song.getSong_word_count() );
				jsonObject.element("singer_number",song.getSinger_number() );
				jsonObject.element("singer_name",song.getSinger_name() );
				jsonObject.element("singer_sex",song.getSinger_sex() );
				jsonObject.element("spell_first_letter_abbreviation",song.getSpell_first_letter_abbreviation() );
				jsonObject.element("spell_first_letter_traditional",song.getSpell_first_letter_traditional() );
				jsonObject.element("movie_spell_first_letter",song.getMovie_spell_first_letter() );
				jsonObject.element("click_number",song.getClick_number() );
				jsonObject.element("song_version",song.getSong_version() );
				jsonObject.element("light_control_set",song.getLight_control_set() );
				jsonObject.element("audio_effect_set",song.getAudio_effect_set() );
				jsonObject.element("file_format",song.getFile_format() );
				jsonObject.element("song_bit_rate",song.getSong_bit_rate() );
				jsonObject.element("song_theme",song.getSong_theme() );
				jsonObject.element("first_word",song.getFirst_word() );
				jsonObject.element("local_path",song.getLocal_path() );
				jsonObject.element("server_path",song.getServer_path() );
				jsonObject.element("server_path1",song.getServer_path1() );
				jsonObject.element("relative_path",song.getRelative_path() );
				jsonObject.element("file_size",song.getFile_size() );
				jsonObject.element("video_saturation",song.getVideo_saturation() );
				jsonObject.element("video_luminance",song.getVideo_luminance() );
				jsonObject.element("video_contrast",song.getVideo_contrast() );
				jsonObject.element("lyric",song.getLyric() );
				jsonObject.element("word_head_code",song.getWord_head_code() );
				jsonObject.element("culture_code",song.getCulture_code() );
				jsonObject.element("issue_year",song.getIssue_year() );
				jsonObject.element("new_song",song.getNew_song() );*/ 
				
				jsonObject.element("song_id",song.getSong_id() );
				jsonObject.element("file_name",song.getFile_name() );
				jsonObject.element("superstar_id",song.getSuperstar_id() );
				jsonObject.element("unsuperstar_id",song.getUnsuperstar_id() );
				jsonObject.element("song_name",song.getSong_name() );
				jsonObject.element("spell_first_letter_abbreviation",song.getSpell_first_letter_abbreviation() );
				jsonObject.element("singer_id",song.getSinger_id() );
				jsonObject.element("singer_name",song.getSinger_name() );
				jsonObject.element("singer_type",song.getSinger_type() );
				jsonObject.element("version",song.getVersion() );
				jsonObject.element("accompany_volume",song.getAccompany_volume() );
				jsonObject.element("karaoke_volume",song.getKaraoke_volume() );
				jsonObject.element("lyric",song.getLyric() );
				jsonObject.element("issue_year",song.getIssue_year() );
				jsonObject.element("resolution",song.getResolution() );
				jsonObject.element("record_company",song.getRecord_company() );
				jsonObject.element("song_theme",song.getSong_theme() );
				jsonObject.element("movie_type",song.getMovie_type() );
				jsonObject.element("movie_type_info",song.getMovie_type_info() );
				jsonObject.element("song_type",song.getSong_type() );
				jsonObject.element("language",song.getLanguage() );
				jsonObject.element("click_number",song.getClick_number() );
				jsonObject.element("collection_number",song.getCollection_number() );
				jsonObject.element("authors",song.getAuthors() );
				jsonObject.element("compose",song.getCompose() );
				jsonObject.element("original_track",song.getOriginal_track() );
				jsonObject.element("original_channel",song.getOriginal_channel() );
				jsonObject.element("accompany_track",song.getAccompany_track() );
				jsonObject.element("accompany_channel",song.getAccompany_channel() );
				jsonObject.element("storage_type",song.getStorage_type() );
				jsonObject.element("file_path",song.getFile_path() );
				jsonObject.element("file_length",song.getFile_length() );
				jsonObject.element("file_format",song.getFile_format() );
				jsonObject.element("light_control_set",song.getLight_control_set() );
				jsonObject.element("play_time",song.getPlay_time());
				jsonObject.element("flow_type",song.getFlow_type() );
				jsonObject.element("song_bit_rate",song.getSong_bit_rate() );
				jsonObject.element("import_time",song.getImport_time() );
				jsonObject.element("idx_sha1",song.getIdx_sha1() );
				jsonObject.element("dgx_sha1",song.getDgx_sha1() );
				jsonObject.element("remark",song.getRemark() );
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", pageList.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "曲库管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_SEARCH ; 
			getOptLogService().setLog(currentUser,function,type);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 删除曲库
	 */
	public void removeMusic(){
		try{
			String[] idsList = getIds().split(",");
			List<String> list = Arrays.asList(idsList);
			for(String song_id:list){
				Song find = this.songDao.find(song_id);
				find.setIs_activity("1");
				songDao.modify(find);
			}
			/*songDao.batchRemove(list);*/
			
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("message", "删除成功");
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "曲库管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_DEL; 
			getOptLogService().setLog(currentUser,function,type);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 编辑曲库歌曲
	 */
	public void editMusic(){
		try{
			MsgBean msgBean = this.songService.editMusic(song); 
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 导出曲库列表
	 */
	public void exportMusic(){
		this.songService.exportMusic(song);
	}
	/**
	 * 导入曲库
	 */
	public void importMusic(){
		try{
			//2003Excel导入方式  目前只允许10000条数据导入否则内存溢出
			MsgBean msgBean = this.songService.importMusic(importFile);
			//2007Excel导入方式 数据量大小不限，但导入时间较长
			//MsgBean msgBean = this.songService.newImportMusic(importFile); 
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public SongDao getSongDao() {
		return songDao;
	}
	public void setSongDao(SongDao songDao) {
		this.songDao = songDao;
	}
	public OperationLogService getOptLogService() {
		return optLogService;
	}
	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}
	public File getImportFile() {
		return importFile;
	}
	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}
	public SongService getSongService() {
		return songService;
	}
	public void setSongService(SongService songService) {
		this.songService = songService;
	}
	public String getImportFileContentType() {
		return importFileContentType;
	}
	public void setImportFileContentType(String importFileContentType) {
		this.importFileContentType = importFileContentType;
	}
	public String getImportFileFileName() {
		return importFileFileName;
	}
	public void setImportFileFileName(String importFileFileName) {
		this.importFileFileName = importFileFileName;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
