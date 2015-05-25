package com.liyan.superstar.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyan.common.util.CommonSQLite;
import com.liyan.common.util.CommonUtils;
import com.liyan.common.util.Param;
import com.liyan.superstar.dao.AdvertDao;
import com.liyan.superstar.dao.AdvertMusicDao;
import com.liyan.superstar.dao.AdvertVideoDao;
import com.liyan.superstar.dao.AdvertVideoDao1;
import com.liyan.superstar.dao.DictionaryDao;
import com.liyan.superstar.dao.GameDao;
import com.liyan.superstar.dao.InterfaceDao;
import com.liyan.superstar.dao.IntroduceDao;
import com.liyan.superstar.dao.MainStarDao;
import com.liyan.superstar.dao.MarketingActivityDao;
import com.liyan.superstar.dao.PassMusicDao;
import com.liyan.superstar.dao.ResearchTypeDao;
import com.liyan.superstar.dao.SingerDao;
import com.liyan.superstar.dao.SongDao;
import com.liyan.superstar.dto.KappIntroduceMusic;
import com.liyan.superstar.dto.KappPassMusic;
import com.liyan.superstar.dto.KappSong;
import com.liyan.superstar.model.Advert;
import com.liyan.superstar.model.AdvertMusic;
import com.liyan.superstar.model.AdvertVideo;
import com.liyan.superstar.model.AdvertVideo1;
import com.liyan.superstar.model.Dictionary;
import com.liyan.superstar.model.Game;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.MainStar;
import com.liyan.superstar.model.MarketingActivity;
import com.liyan.superstar.model.ResearchType;
import com.liyan.superstar.model.Singer;
import com.liyan.superstar.service.SQLiteService;
@Service
@Transactional
public class SQLiteServiceImpl implements SQLiteService {

	private static Logger log = Logger.getLogger(SQLiteServiceImpl.class);
	@Autowired
	private AdvertDao advertDao;
	@Autowired
	private InterfaceDao interfaceDao;
	@Autowired
	private SongDao songDao ; 
	@Autowired
	private SingerDao singerDao ; 
	@Autowired
	private AdvertDao adverDao ; 
	@Autowired
	private AdvertMusicDao advertMusicDao ; 
	@Autowired
	private MarketingActivityDao activityDao ;
	@Autowired
	private AdvertVideoDao1 advertVideoDao ; 
	@Autowired
	private PassMusicDao passMusicDao;
	@Autowired
	private DictionaryDao dictDao ; 
	@Autowired
	private GameDao gameDao;
	@Autowired
	private IntroduceDao introduceDao ;
	@Autowired
	private ResearchTypeDao researchTypeDao ; 
	@Autowired
	private MainStarDao mainStarDao ; 
	Connection conn = null ;
	
	public Connection getConnection(String dbName) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:/" + dbName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void exeCreateTable(String sql, Connection conn) {
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.execute(sql);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateUserVersion(Connection conn, String sql) {
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.execute(sql);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getVersion() {
		String str = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new java.util.Date());
		return str;
	}

	/*public void insertSuperstarAdvert(String dbName) {
		Connection conn = this.getConnection(dbName);
		try {
			String insertSql = "";
			Statement stat = conn.createStatement();
			List<Advert> findAll = advertDao.findAll();
			for (Advert advert : findAll) {

				String filePath = advert.getAbsolute_path();
				File file = new File(filePath);
				if (file.exists()) {
					String fileName = file.getName();
					insertSql = "INSERT INTO 'superstar_advert' VALUES" + " ('"
							+ advert.getAdvert_id() + "','"
							+ advert.getAdvert_name() + "','"
							+ advert.getOnline_time() + "','"
							+ advert.getOffline_time() + "','"
							+ advert.getStore() + "','"
							+ advert.getAdvert_content() + "','"
							+ advert.getState() + "','" + fileName + "','"
							+ advert.getIs_activity() + "','"
							+ advert.getAdvert_time() + "','"
							+ advert.getAdvert_sort() + "')";
					stat.addBatch(insertSql);
				}
			}
			stat.executeBatch();
			// mysqlCon.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void insertSuperstarInterface(String dbName) {

		Connection conn = this.getConnection(dbName);
		try {
			String insertSql = "";
			Statement stat = conn.createStatement();
			List<Interface> findAll = interfaceDao.findAll();
			for (Interface itf : findAll) {
				String filePath = itf.getAbsolute_path();
				File file = new File(filePath);
				if (file.exists()) {
					String fileName = file.getName();
					insertSql = "INSERT INTO 'superstar_interface' VALUES"
							+ " ('"
							+ itf.getInterface_id()
							+ "','"
							+ itf.getInterface_name()
							+ "','"
							+ itf.getOnline_time()
							+ "','"
							+ itf.getOffline_time()
							+ "','"
							+ itf.getInterface_type()
							+ "','"
							+ itf.getStore()
							+ "','"
							+ itf.getState()
							+ "','"
							+ fileName
							+ "','"
							+ itf.getIs_activity()
							+ "','"
							+ itf.getInterface_time()
							+ "','"
							+ itf.getInterface_sort() + "')";
					stat.addBatch(insertSql);
				}
			}
			stat.executeBatch();
			// mysqlCon.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/

	@Override
	public HashMap<String,String> generateSql(String dbPath,String fileName) throws Exception {
		String dbName  = null ;
		HashMap<String,String> hm = new HashMap<String,String>();
		String version = getVersion();
		
		dbName = CommonUtils.generateFile(dbPath+"\\"+version ,null,fileName);
		hm.put("version", version);
		hm.put("dbName", dbName);
		conn = this.getConnection(dbName);
		conn.setAutoCommit(false);
		// 更改用户版本
		String updateVersion = Param.SQLITE_UPDATE_USER_VERSION + version;
		this.updateUserVersion(conn, updateVersion);
		// 创建表
		//设置进度条总数
		this.exeCreateTable(CommonSQLite.VOD_KAPP_SONG, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_SINGER, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_ADVERT, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_ADVERT_MUSIC, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_INTERFACE, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_ACTIVITY, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_ADVERT_VIDEO, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_PASS_SONG, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_SINGER_RECOMMEND, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_RESEARCH_TYPE, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_SONG_RECOMMEND, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_GAME, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_DICT, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_OPERATE_LOG, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_ORDER, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_ORDER_DETAIL, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_MUSIC_RANKING, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_SINGER_RANKING, conn);
		this.exeCreateTable(CommonSQLite.VOD_KAPP_RECORD_MANAGER, conn);
		
		long start_time = System.currentTimeMillis();
		//设置进度条数 
		insertVod_kapp_song();
		insertVod_kapp_singer();
		insertVod_kapp_advert();
		insertVod_kapp_advert_music();
		insertVod_kapp_interface();
		insertVod_kapp_activity();
		insertVod_kapp_advert_video();
		insertVod_kapp_pass();
		insertVod_kapp_singer_introduce();
		insertVod_kapp_research_type();
		insertVod_kapp_music_introduce();
		insertVod_kapp_game();
		insertVod_kapp_dict();
		long end_time = System.currentTimeMillis();
		log.info("------------------------------------------"+(end_time-start_time));
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hm ; 
	}

	private void insertVod_kapp_singer_introduce() {
		try {
			log.info("--------------insert VOD_KAPP_SINGER_RECOMMEND");
			Statement stat = conn.createStatement();
			List<MainStar> findAll = mainStarDao.getMainStarList();
			int i = 0 ;
			for (MainStar star: findAll) {
				StringBuffer insertSql = new StringBuffer("");
				insertSql.append("INSERT INTO 'VOD_KAPP_SINGER_RECOMMEND' VALUES(")
						.append(nullToken(star.getSinger().getStar_id()))
						.append(",")
						.append(nullToken(star.getSinger().getStar_name()))
						.append(",")
						.append(nullToken(star.getSinger().getStar_head()))
						.append(",")
						.append(nullToken(star.getSinger().getStar_type()))
						.append(",")
						.append(nullToken(star.getIntroduce_sort()))
						.append(",")
						.append(nullToken(star.getIntroduce_type()))
						.append(",")
						.append(nullToken(null))//歌曲ID
						.append(",")
						.append(nullToken(null))//歌曲名称
						.append(",")
						.append(nullToken(null))//文件路径
						.append(",")
						.append(nullToken(null))//点唱次数
						.append(",")
						.append(nullToken(null))//收藏次数

						.append(");");
						log.info(insertSql.toString());
						stat.execute(insertSql.toString());
						if(i%100 == 0){
							conn.commit();
						}
						i++; 
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertVod_kapp_research_type() {
		try {
			log.info("--------------insert VOD_KAPP_RESEARCH_TYPE");
			Statement stat = conn.createStatement();
			List<ResearchType> findAll = researchTypeDao.getResearchTypeList();
			int i = 0 ;
			for (ResearchType type: findAll) {
				StringBuffer insertSql = new StringBuffer("");
				insertSql.append("INSERT INTO 'VOD_KAPP_RESEARCH_TYPE' VALUES(")
						.append(nullToken(type.getQuestion_id()))
						.append(",")
						.append(nullToken(type.getQuestion_name()))
						.append(",")
						.append(nullToken(type.getStore()))
						.append(",")
						.append(nullToken(type.getQuestion_type()))
						.append(",")
						.append(type.getQuestion_score())
						.append(",")
						.append(nullToken(type.getOption_a()))
						.append(",")
						.append(type.getOption_a_score())
						.append(",")
						.append(nullToken(type.getOption_b()))
						.append(",")
						.append(type.getOption_b_score())
						.append(",")
						.append(nullToken(type.getOption_c()))
						.append(",")
						.append(type.getOption_c_score())
						.append(",")
						.append(nullToken(type.getOption_d()))
						.append(",")
						.append(type.getOption_d_score())
						.append(",")
						.append(nullToken(type.getOption_e()))
						.append(",")
						.append(type.getOption_e_score())
						.append(",")
						.append(nullToken(type.getOption_f()))
						.append(",")
						.append(type.getOption_f_score())
						.append(");");
						log.info(insertSql.toString());
						stat.execute(insertSql.toString());
						if(i%100 == 0){
							conn.commit();
						}
						i++; 
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertVod_kapp_music_introduce() {
		try {
			log.info("--------------insert VOD_KAPP_SONG_RECOMMEND");
			Statement stat = conn.createStatement();
			List<KappIntroduceMusic> findAll = introduceDao.getIntroduceList();
			int i = 0 ;
			for (KappIntroduceMusic music: findAll) {
				StringBuffer insertSql = new StringBuffer("");
				insertSql.append("INSERT INTO 'VOD_KAPP_SONG_RECOMMEND' VALUES(")
						.append(nullToken(music.getSong_id()))
						.append(",")
						.append(nullToken(music.getSong_name()))
						.append(",")
						.append(nullToken(music.getIndroduce_sort()))
						.append(",")
						.append(nullToken(music.getSpell_first_letter_abbreviation()))
						.append(",")
						.append(nullToken(music.getSinger_id()))
						.append(",")
						.append(nullToken(music.getSinger_name()))
						.append(",")
						.append(nullToken(music.getSinger_type()))
						.append(",")
						.append(nullToken(music.getSinger_head()))
						.append(",")
						.append(nullToken(music.getLyric()))
						.append(",")
						.append(nullToken(music.getFile_path()))
						.append(",")
						.append(nullToken(music.getOriginal_track()))
						.append(",")
						.append(nullToken(music.getOriginal_channel()))
						.append(",")
						.append(nullToken(music.getAccompany_track()))
						.append(",")
						.append(nullToken(music.getAccompany_channel()))
						.append(",")
						.append(nullToken(music.getAccompany_volume()))
						.append(",")
						.append(nullToken(music.getKaraoke_volume()))
						.append(",")
						.append(nullToken(music.getIssue_year()))
						.append(",")
						.append(nullToken(music.getSong_theme()))
						.append(",")
						.append(nullToken(music.getMovie_type()))
						.append(",")
						.append(nullToken(music.getSong_type()))
						.append(",")
						.append(nullToken(music.getLanguage()))
						.append(",")
						.append(nullToken(music.getVersion()))
						.append(");");
						log.info(insertSql.toString());
						stat.execute(insertSql.toString());
						if(i%100 == 0){
							conn.commit();
						}
						i++; 
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertVod_kapp_game() {
		try {
			log.info("--------------insert VOD_KAPP_GAME");
			Statement stat = conn.createStatement();
			List<Game> findAll = gameDao.getGameList();
			int i = 0 ;
			for (Game game: findAll) {
				StringBuffer insertSql = new StringBuffer("");
				insertSql.append("INSERT INTO 'VOD_KAPP_GAME' VALUES(")
						.append(nullToken(game.getGame_id()))
						.append(",")
						.append(nullToken(game.getGame_name()))
						.append(");");
						log.info(insertSql.toString());
						stat.execute(insertSql.toString());
						if(i%100 == 0){
							conn.commit();
						}
						i++; 
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertVod_kapp_dict() {
		try {
			log.info("--------------insert VOD_KAPP_DICT");
			Statement stat = conn.createStatement();
			List<Dictionary> findAll = dictDao.getDictList();
			int i = 0 ;
			for (Dictionary dict: findAll) {
				StringBuffer insertSql = new StringBuffer("");
				insertSql.append("INSERT INTO 'VOD_KAPP_DICT' VALUES(")
						.append(nullToken(dict.getId()))
						.append(",")
						.append(nullToken(dict.getDict_type()))
						.append(",")
						.append(nullToken(dict.getDict_type_code()))
						.append(",")
						.append(nullToken(dict.getDict_code()))
						.append(",")
						.append(nullToken(dict.getDict_value()))
						.append(",")
						.append(nullToken(dict.getImport_time()))
						.append(",")
						.append(nullToken(dict.getFile_path()))
						.append(",")
						.append(nullToken(dict.getDict_sort()))
						.append(");");
						log.info(insertSql.toString());
						stat.execute(insertSql.toString());
						if(i%100 == 0){
							conn.commit();
						}
						i++; 
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertVod_kapp_pass() {
		try {
			log.info("--------------insert VOD_KAPP_PASS_SONG");
			Statement stat = conn.createStatement();
			List<KappPassMusic> findAll = passMusicDao.getPassMusicList();
			int i = 0 ;
			for (KappPassMusic passSong: findAll) {
				StringBuffer insertSql = new StringBuffer("");
				insertSql.append("INSERT INTO 'VOD_KAPP_PASS_SONG' VALUES(")
						.append(nullToken(passSong.getSong_id()))
						.append(",")
						.append(nullToken(passSong.getSong_name()))
						.append(",")
						.append(nullToken(passSong.getPass_sort()))
						.append(",")
						.append(nullToken(passSong.getFile_path()))
						.append(",")
						.append(nullToken(passSong.getOriginal_track()))
						.append(",")
						.append(nullToken(passSong.getOriginal_channel()))
						.append(",")
						.append(nullToken(passSong.getAccompany_track()))
						.append(",")
						.append(nullToken(passSong.getAccompany_channel()))
						.append(",")
						.append(nullToken(passSong.getAccompany_volume()))
						.append(",")
						.append(nullToken(passSong.getKaraoke_volume()))
						.append(");");
						log.info(insertSql.toString());
						stat.execute(insertSql.toString());
						if(i%100 == 0){
							conn.commit();
						}
						i++; 
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void insertVod_kapp_advert_video() {
		try {
			log.info("--------------insert VOD_KAPP_ADVERT_VIDEO");
			Statement stat = conn.createStatement();
			List<AdvertVideo1> findAll = advertVideoDao.getAdvertVideoList();
			int i = 0 ;
			for (AdvertVideo1 advertVideo: findAll) {
				StringBuffer insertSql = new StringBuffer("");
				insertSql.append("INSERT INTO 'VOD_KAPP_ADVERT_VIDEO' VALUES(")
						.append(nullToken(advertVideo.getAdvert_id()))
						.append(",")
						.append(nullToken(advertVideo.getAdvert_theme()))
						.append(",")
						.append(nullToken(advertVideo.getMusic_id()))
						.append(",")
						.append(nullToken(advertVideo.getMusic_name()))
						.append(",")
						.append(nullToken(advertVideo.getAdvert_sort()))
						.append(",")
						.append(nullToken("0"))
						.append(",")
						.append(nullToken(advertVideo.getOnline_time()))
						.append(",")
						.append(nullToken(advertVideo.getOffline_time()))
						.append(");");
						log.info(insertSql.toString());
						stat.execute(insertSql.toString());
						if(i%100 == 0){
							conn.commit();
						}
						i++; 
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertVod_kapp_activity() {
		try {
			log.info("--------------insert VOD_KAPP_ACTIVITY");
			Statement stat = conn.createStatement();
			List<MarketingActivity> findAll = activityDao.getActivityList();
			int i = 0 ;
			for (MarketingActivity activity: findAll) {
				StringBuffer insertSql = new StringBuffer("");
				insertSql.append("INSERT INTO 'VOD_KAPP_ACTIVITY' VALUES(")
						.append(nullToken(activity.getActivity_id()))
						.append(",")
						.append(nullToken(activity.getActivity_name()))
						.append(",")
						.append(nullToken(activity.getActivity_introduce()))
						.append(",")
						.append(nullToken(activity.getActivity_path_2()))
						.append(",")
						.append(nullToken(activity.getActivity_path_1()))
						.append(",")
						.append(nullToken(activity.getActivity_sort()))
						.append(");");
						log.info(insertSql.toString());
						stat.execute(insertSql.toString());
						if(i%100 == 0){
							conn.commit();
						}
						i++; 
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertVod_kapp_interface() {
		try {
			log.info("--------------insert VOD_KAPP_INTERFACE");
			Statement stat = conn.createStatement();
			List<Interface> findAll = interfaceDao.getInterfaceList();
			int i = 0 ;
			for (Interface itf: findAll) {
				StringBuffer insertSql = new StringBuffer("");
				insertSql.append("INSERT INTO 'VOD_KAPP_INTERFACE' VALUES(")
						.append(nullToken(itf.getInterface_id()))
						.append(",")
						.append(nullToken(itf.getInterface_name()))
						.append(",")
						.append(nullToken(itf.getOnline_time()))
						.append(",")
						.append(nullToken(itf.getOffline_time()))
						.append(",")
						.append(nullToken(itf.getInterface_type()))
						.append(",")
						.append(nullToken(itf.getFile_path()))
						.append(",")
						.append(nullToken(itf.getFile_format()))
						.append(",")
						.append(nullToken(itf.getInterface_sort()))
						.append(",")
						.append(nullToken(itf.getInterface_time()))
						.append(");");
						log.info(insertSql.toString());
						stat.execute(insertSql.toString());
						if(i%100 == 0){
							conn.commit();
						}
						i++; 
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertVod_kapp_advert_music() {
		try {
			log.info("--------------insert VOD_KAPP_ADVERT_MUSIC");
			Statement stat = conn.createStatement();
			List<AdvertMusic> findAll = advertMusicDao.getAdvertMusicList();
			int i = 0 ;
			for (AdvertMusic advertMusic: findAll) {
				StringBuffer insertSql = new StringBuffer("");
				insertSql.append("INSERT INTO 'VOD_KAPP_ADVERT_MUSIC' VALUES(")
						.append(nullToken(advertMusic.getAdvert_music_id()))
						.append(",")
						.append(nullToken(advertMusic.getAdvert_id()))
						.append(",")
						.append(nullToken(advertMusic.getSong().getSong_id()))
						.append(");");
						log.info(insertSql.toString());
						stat.execute(insertSql.toString());
						if(i%100 == 0){
							conn.commit();
						}
						i++; 
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertVod_kapp_advert() {
		try {
			log.info("--------------insert VOD_KAPP_ADVERT");
			Statement stat = conn.createStatement();
			List<Advert> findAll = adverDao.getAdvertList();
			int i = 0 ;
			for (Advert advert: findAll) {
				StringBuffer insertSql = new StringBuffer("");
				insertSql.append("INSERT INTO 'VOD_KAPP_ADVERT' VALUES(")
						.append(nullToken(advert.getAdvert_id()))
						.append(",")
						.append(nullToken(advert.getAdvert_name()))
						.append(",")
						.append(nullToken(advert.getOnline_time()))
						.append(",")
						.append(nullToken(advert.getOffline_time()))
						.append(",")
						.append(nullToken(advert.getAdvert_content()))
						.append(",")
						.append(nullToken(advert.getFile_path()))
						.append(",")
						.append(nullToken(advert.getAdvert_sort()))
						.append(",")
						.append(nullToken(advert.getAdvert_time()))
						.append(",")
						.append(nullToken(advert.getIs_index_advert()))
						.append(");");
						log.info(insertSql.toString());
						stat.execute(insertSql.toString());
						if(i%100 == 0){
							conn.commit();
						}
						i++; 
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void insertVod_kapp_singer() {
		try {
			log.info("--------------insert VOD_KAPP_SINGER");
			Statement stat = conn.createStatement();
			List<Singer> findAll = singerDao.getSingerList();
			int i = 0 ;
			for (Singer singer: findAll) {
				StringBuffer insertSql = new StringBuffer("");
				insertSql.append("INSERT INTO 'VOD_KAPP_SINGER' VALUES(")
						.append(nullToken(singer.getStar_id() ))
						.append(",")
						.append(nullToken(singer.getStar_name()))
						.append(",")
						.append(nullToken(singer.getSpell_first_letter_abbreviation()))
						.append(",")
						.append(nullToken(singer.getStar_type()))
						.append(",")
						.append(nullToken(singer.getStar_head()))
						.append(",")
						.append(nullToken(singer.getArea()))
						.append(",")
						.append(nullToken(null)) //门店端 没有收藏次数字段
						.append(",")
						.append(nullToken(singer.getClick_number()))
						 .append(");");
						log.info(insertSql.toString());
						stat.execute(insertSql.toString());
						if(i%100 == 0){
							conn.commit();
						}
						i++; 
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertVod_kapp_song() {
		try {
			log.info("--------------insert VOD_KAPP_SONG");
			Statement stat = conn.createStatement();
			List<KappSong> findAll = songDao.getSongList();
			int i = 0 ; 
			for (KappSong song : findAll) {
				StringBuffer insertSql = new StringBuffer("");
				insertSql.append("INSERT INTO 'VOD_KAPP_SONG' VALUES(")
						 .append(nullToken(song.getSong_id()))
						 .append(",")
						 .append(nullToken(song.getSong_name()))
						 .append(",")
						 .append(nullToken(song.getSpell_first_letter_abbreviation()))
						 .append(",")
						 .append(nullToken(song.getStar_id()))
						 .append(",")
						 .append(nullToken(song.getStar_name()))
						 .append(",")
						 .append(nullToken(song.getStar_type()))
						 .append(",")
						 .append(nullToken(song.getStar_head()))
						 .append(",")
						 .append(nullToken(song.getLyric()))
						 .append(",")
						 .append(nullToken(song.getFile_path()))
						 .append(",")
						 .append(nullToken(song.getOriginal_track()))
						 .append(",")
						 .append(nullToken(song.getOriginal_channel()))
						 .append(",")
						 .append(nullToken(song.getAccompany_track()))
						 .append(",")
						 .append(nullToken(song.getAccompany_channel()))
						 .append(",")
						 .append(nullToken(song.getAccompany_volume()))
						 .append(",")
						 .append(nullToken(song.getKaraoke_volume()))
						 .append(",")
						 .append(nullToken(song.getIssue_year()))
						 .append(",")
						 .append(nullToken(song.getSong_theme()))
						 .append(",")
						 .append(nullToken(song.getMovie_type()))
						 .append(",")
						 .append(nullToken(song.getSong_type()))
						 .append(",")
						 .append(nullToken(song.getLanguage()))
						 .append(",")
						 .append(nullToken(song.getIs_new()))
						 .append(",")
						 .append(nullToken(song.getVersion()))
						 .append(",")
						 .append(nullToken(song.getClick_number()))
						 .append(");");
						log.info(insertSql.toString());
						stat.execute(insertSql.toString());
						if(i%100 == 0){
							conn.commit();
						}
						i++; 
			}
			conn.commit();
			// mysqlCon.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}		
	private String nullToken(String str){
		if(str!=null){
			if(str.indexOf("'")>0){
				str = str.replaceAll("'", "‘");
			}
			str="'"+str+"'";
		}
		return str ;
	}
	public AdvertMusicDao getAdvertMusicDao() {
		return advertMusicDao;
	}

	public void setAdvertMusicDao(AdvertMusicDao advertMusicDao) {
		this.advertMusicDao = advertMusicDao;
	}

	public MainStarDao getMainStarDao() {
		return mainStarDao;
	}

	public void setMainStarDao(MainStarDao mainStarDao) {
		this.mainStarDao = mainStarDao;
	}		
}
