package com.liyan.common.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;


public class SQLiteService {

	public static String CREATE_TABLE_SUPERSTAR_ADVERT = "CREATE TABLE `superstar_advert` (`ADVERT_ID` varchar(64) NOT NULL ,`ADVERT_NAME` varchar(64) DEFAULT NULL ,`ONLINE_TIME` date ,`OFFLINE_TIME` date DEFAULT NULL ,`STORE` varchar(64) DEFAULT NULL ,`ADVERT_CONTENT` varchar(64) DEFAULT NULL ,`STATE` varchar(64) DEFAULT NULL ,`FILE_PATH` varchar(255) DEFAULT NULL ,`IS_ACTIVITY` varchar(64) DEFAULT NULL ,`ADVERT_TIME` VARCHAR(20) DEFAULT NULL ,`ADVERT_SORT` varchar(20) DEFAULT NULL ,PRIMARY KEY (`ADVERT_ID`)) ;";
	
	public static String CREATE_TABLE_SUPERSTAR_INTERFACE = "CREATE TABLE `superstar_interface` (`INTERFACE_ID` varchar(64) NOT NULL ,`INTERFACE_NAME` varchar(64) DEFAULT NULL ,`ONLINE_TIME` date DEFAULT NULL ,`OFFLINE_TIME` date DEFAULT NULL,`INTERFACE_TYPE` varchar(64) DEFAULT NULL,`STORE` varchar(64) DEFAULT NULL,`STATE` varchar(64) DEFAULT NULL,`FILE_PATH` varchar(255) DEFAULT NULL,`IS_ACTIVITY` varchar(64) DEFAULT NULL ,`INTERFACE_TIME` varchar(64) DEFAULT NULL ,`INTERFACE_SORT` varchar(64) DEFAULT NULL , PRIMARY KEY (`INTERFACE_ID`));";
	
	public static String CREATE_TABLE_SUPERSTAR_SONG = "CREATE TABLE `superstar_song` ( "+
			 " `SONG_ID` varchar(64) NOT NULL,"+
			 " `FILE_NAME` varchar(64) DEFAULT NULL,"+
			 " `SUPERSTAR_ID` varchar(64) DEFAULT NULL,"+
			 " `UNSUPERSTAR_ID` varchar(64) DEFAULT NULL,"+
			 " `SONG_NAME` varchar(64) DEFAULT NULL,"+
			 " `SPELL_FIRST_LETTER_ABBREVIATION` varchar(64) DEFAULT NULL,"+
			  " `SINGER_ID` varchar(64) DEFAULT NULL,"+
			  " `SINGER_NAME` varchar(64) DEFAULT NULL,"+
			  " `SINGER_TYPE` varchar(64) DEFAULT NULL,"+
			  " `VERSION` varchar(64) DEFAULT NULL,"+
			  " `ACCOMPANY_VOLUME` varchar(64) DEFAULT NULL,"+
			  " `KARAOKE_VOLUME` varchar(64) DEFAULT NULL,"+
			  " `LYRIC` varchar(64) DEFAULT NULL,"+
			  " `ISSUE_YEAR` varchar(64) DEFAULT NULL,"+
			  " `RESOLUTION` varchar(64) DEFAULT NULL,"+
			  " `RECORD_COMPANY` varchar(64) DEFAULT NULL,"+
			  " `SONG_THEME` varchar(64) DEFAULT NULL,"+
			  " `MOVIE_TYPE` varchar(64) DEFAULT NULL,"+
			  " `MOVIE_TYPE_INFO` varchar(64) DEFAULT NULL,"+
			  " `SONG_TYPE` varchar(64) DEFAULT NULL,"+
			  " `LANGUAGE` varchar(64) DEFAULT NULL,"+
			  " `CLICK_NUMBER` varchar(64) DEFAULT NULL,"+
			  " `COLLECTION_NUMBER` varchar(64) DEFAULT NULL,"+
			  " `AUTHORS` varchar(64) DEFAULT NULL,"+
			  " `COMPOSE` varchar(64) DEFAULT NULL,"+
			  " `ORIGINAL_TRACK` varchar(64) DEFAULT NULL,"+
			  " `ORIGINAL_CHANNEL` varchar(64) DEFAULT NULL,"+
			  " `ACCOMPANY_TRACK` varchar(64) DEFAULT NULL,"+
			  " `ACCOMPANY_CHANNEL` varchar(64) DEFAULT NULL,"+
			  " `STORAGE_TYPE` varchar(64) DEFAULT NULL,"+
			  " `FILE_PATH` varchar(64) DEFAULT NULL,"+
			  " `FILE_LENGTH` varchar(64) DEFAULT NULL,"+
			  " `FILE_FORMAT` varchar(64) DEFAULT NULL,"+
			  " `LIGHT_CONTROL_SET` varchar(64) DEFAULT NULL,"+
			  " `PLAY_TIME` varchar(64) DEFAULT NULL,"+
			  " `FLOW_TYPE` varchar(64) DEFAULT NULL,"+
			  " `SONG_BIT_RATE` varchar(64) DEFAULT NULL,"+
			  " `IMPORT_TIME` varchar(64) DEFAULT NULL,"+
			  " `IDX_SHA1` varchar(64) DEFAULT NULL,"+
			  " `DGX_SHA1` varchar(64) DEFAULT NULL,"+
			  " `REMARK` varchar(64) DEFAULT NULL,"+
			  " PRIMARY KEY (`SONG_ID`))";
	public static String CREATE_TABLE_SUPERSTAR_SINGER = "CREATE TABLE `superstar_singer` ("+
			  " `STAR_ID` varchar(64) DEFAULT NULL ,"+
			  " `STAR_NAME` varchar(64) DEFAULT NULL,"+
			  " `OTHER_NAME` varchar(64) DEFAULT NULL,"+
			  " `SPELL_FIRST_LETTER_ABBREVIATION` varchar(64) DEFAULT NULL ,"+
			  " `STAR_TYPE` varchar(64) DEFAULT NULL,"+
			  " `STAR_HEAD` varchar(255) DEFAULT NULL ,"+
			  " `AREA` varchar(64) DEFAULT NULL,"+
			  " `CLICK_NUMBER` varchar(64) DEFAULT NULL,"+
			  " `REMARK` varchar(64) DEFAULT NULL,"+
			  " PRIMARY KEY (`STAR_ID`) )" ; 
	public static Connection getConnection(String dbName){
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:/" + dbName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void exeCreateTable(String sql, Connection conn) {
			Statement stat;
			try {
				stat = conn.createStatement();
				stat.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public static void updateUserVersion(Connection conn,String sql) {
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
}


	
	public static String getVersion(){
		String str =  new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
		return str;
	}
	
	public static void insertSuperstarAdvert(Connection mysqlCon){
		SQLiteService sqlite = new SQLiteService();
		String dbName = "F:\\sqlite\\ckt.db";
		Connection conn = sqlite.getConnection(dbName);
		try {
			String sql = "select * from superstar_advert";
			String insertSql = "";
			Statement stat = conn.createStatement();
			PreparedStatement mysqlState =  mysqlCon.prepareStatement(sql);
			ResultSet result = mysqlState.executeQuery();
			while(result.next()){
				String filePath = result.getString("ABSOLUTE_PATH");
				File file = new File(filePath);
				if(file.exists()){
					String fileName = file.getName();
					insertSql = "INSERT INTO 'superstar_advert' VALUES"
							+ " ('"
							+ result.getString("ADVERT_ID") + "','" + result.getString("ADVERT_NAME") + "','"
							+ result.getDate("ONLINE_TIME") + "','" + result.getDate("OFFLINE_TIME") + "','"
							+ result.getString("STORE") + "','" + result.getString("ADVERT_CONTENT") + "','"
							+ result.getString("STATE") + "','" + fileName + "','"
							+ result.getString("IS_ACTIVITY") +"','"+result.getString("ADVERT_TIME") +"','"+result.getString("ADVERT_SORT")+"')";
					stat.addBatch(insertSql);
				}
			}
			stat.executeBatch();
			//mysqlCon.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void insertSuperstarInterface(Connection mysqlCon){
		SQLiteService sqlite = new SQLiteService();
		String dbName = "F:\\sqlite\\ckt.db";
		Connection conn = sqlite.getConnection(dbName);
		try {
			String sql = "select * from superstar_interface";
			String insertSql = "";
			Statement stat = conn.createStatement();
			PreparedStatement mysqlState =  mysqlCon.prepareStatement(sql);
			ResultSet result = mysqlState.executeQuery();
			while(result.next()){
				String filePath = result.getString("absolute_path");
				File file = new File(filePath);
				if(file.exists()){
					String fileName = file.getName();
					insertSql = "INSERT INTO 'superstar_interface' VALUES"
							+ " ('"
							+ result.getString("INTERFACE_ID") + "','" + result.getString("INTERFACE_NAME") + "','"
							+ result.getDate("ONLINE_TIME") + "','" + result.getDate("OFFLINE_TIME") + "','"
							+ result.getString("INTERFACE_TYPE") + "','" + result.getString("STORE") + "','"
							+ result.getString("STATE") + "','" + fileName + "','"
							+ result.getString("IS_ACTIVITY") + "','" + result.getString("INTERFACE_TIME") +"','"+result.getString("INTERFACE_SORT")+"')";
					stat.addBatch(insertSql);
				}
			}
			stat.executeBatch();
			//mysqlCon.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private String getSongTable(){
		String sql = "CREATE TABLE `song_manage` ("
				  +"`SONG_ID` varchar(64) NOT NULL ,"
				  +"`SONG_TRACK` varchar(10) DEFAULT NULL ,"
				  +"`SONG_CHANNEL` varchar(10) DEFAULT NULL ,"
				  +"`IS_STEREO` varchar(10) DEFAULT NULL ,"
				  +"`SONG_NAME` varchar(64) DEFAULT NULL ,"
				  +"`MOVIE_INFO` varchar(64) DEFAULT NULL ,"
				  +"`SONG_SET` varchar(64) DEFAULT NULL ,"
				  +"`ACCOMPANY_VOLUME` varchar(64) DEFAULT NULL ,"
				  +"`KARAOKE_VOLUME` varchar(64) DEFAULT NULL ,"
				  +"`LANGUAGE` varchar(64) DEFAULT NULL ,"
				  +"`SONG_TYPE` varchar(64) DEFAULT NULL ,"
				  +"`SONG_WORD_COUNT` varchar(64) DEFAULT NULL ,"
				  +"`SINGER_NUMBER` varchar(64) DEFAULT NULL ,"
				  +"`SINGER_NAME` varchar(64) DEFAULT NULL ,"
				  +"`SINGER_SEX` varchar(64) DEFAULT NULL ,"
				  +"`SPELL_FIRST_LETTER_ABBREVIATION` varchar(64) DEFAULT NULL ,"
				  +"`SPELL_FIRST_LETTER_TRADITIONAL` varchar(64) DEFAULT NULL ,"
				  +"`MOVIE_SPELL_FIRST_LETTER` varchar(64) DEFAULT NULL ,"
				  +"`CLICK_NUMBER` varchar(64) DEFAULT NULL ,"
				  +"`SONG_VERSION` varchar(64) DEFAULT NULL ,"
				  +"`LIGHT_CONTROL_SET` varchar(64) DEFAULT NULL ,"
				  +"`AUDIO_EFFECT_SET` varchar(64) DEFAULT NULL ,"
				  +"`FILE_FORMAT` varchar(64) DEFAULT NULL ,"
				  +"`SONG_BIT_RATE` varchar(64) DEFAULT NULL ,"
				  +"`SONG_THEME` varchar(64) DEFAULT NULL ,"
				  +"`FIRST_WORD` varchar(64) DEFAULT NULL ,"
				  +"`LOCAL_PATH` varchar(64) DEFAULT NULL ,"
				  +"`SERVER_PATH` varchar(64) DEFAULT NULL ,"
				  +"`SERVER_PATH1` varchar(64) DEFAULT NULL ,"
				  +"`RELATIVE_PATH` varchar(64) DEFAULT NULL ,"
				  +"`FILE_SIZE` varchar(64) DEFAULT NULL ,"
				  +"`VIDEO_SATURATION` varchar(64) DEFAULT NULL ,"
				  +"`VIDEO_LUMINANCE` varchar(64) DEFAULT NULL ,"
				  +"`VIDEO_CONTRAST` varchar(64) DEFAULT NULL ,"
				  +"`LYRIC` varchar(64) DEFAULT NULL ,"
				  +"`WORD_HEAD_CODE` varchar(64) DEFAULT NULL ,"
				  +"`CULTURE_CODE` varchar(64) DEFAULT NULL ,"
				  +"`ISSUE_YEAR` varchar(64) DEFAULT NULL ,"
				  +"`NEW_SONG` varchar(64) DEFAULT NULL ,"
				  +"PRIMARY KEY (`SONG_ID`)"
				  +");";
		/*
		 * String sql = "CREATE TABLE `song_manage` ("
				  +"`SONG_ID` varchar(64) NOT NULL COMMENT '歌曲编号（索引）',"
				  +"`SONG_TRACK` varchar(10) DEFAULT NULL COMMENT '1：音轨1是原唱。2：音轨2是原唱',"
				  +"`SONG_CHANNEL` varchar(10) DEFAULT NULL COMMENT '0：左声道原唱；右声道伴唱。1：左声道伴唱；右声道原唱。',"
				  +"`IS_STEREO` varchar(10) DEFAULT NULL COMMENT '0：是；1：否',"
				  +"`SONG_NAME` varchar(64) DEFAULT NULL COMMENT '歌曲名称（索引）',"
				  +"`MOVIE_INFO` varchar(64) DEFAULT NULL COMMENT '歌曲对应的影视信息',"
				  +"`SONG_SET` varchar(64) DEFAULT NULL COMMENT '首唱歌曲和热门歌曲设置',"
				  +"`ACCOMPANY_VOLUME` varchar(64) DEFAULT NULL COMMENT '伴唱音量',"
				  +"`KARAOKE_VOLUME` varchar(64) DEFAULT NULL COMMENT '原唱音量',"
				  +"`LANGUAGE` varchar(64) DEFAULT NULL COMMENT '语种（国、粤、闽、英、日、韩）',"
				  +"`SONG_TYPE` varchar(64) DEFAULT NULL COMMENT '曲种（京剧、通俗、黄梅戏…..）',"
				  +"`SONG_WORD_COUNT` varchar(64) DEFAULT NULL COMMENT '歌名字数（用于字数点歌）',"
				  +"`SINGER_NUMBER` varchar(64) DEFAULT NULL COMMENT '歌曲的演唱人数',"
				  +"`SINGER_NAME` varchar(64) DEFAULT NULL COMMENT '歌星名字',"
				  +"`SINGER_SEX` varchar(64) DEFAULT NULL COMMENT '歌星性别',"
				  +"`SPELL_FIRST_LETTER_ABBREVIATION` varchar(64) DEFAULT NULL COMMENT '歌曲名的拼音字头',"
				  +"`SPELL_FIRST_LETTER_TRADITIONAL` varchar(64) DEFAULT NULL COMMENT '歌曲名的繁体拼音字头',"
				  +"`MOVIE_SPELL_FIRST_LETTER` varchar(64) DEFAULT NULL COMMENT '歌曲对应影视字头',"
				  +"`CLICK_NUMBER` varchar(64) DEFAULT NULL COMMENT '点击次数（用于排行操作）',"
				  +"`SONG_VERSION` varchar(64) DEFAULT NULL COMMENT '歌曲的版本号（MTV、演唱会、人物）',"
				  +"`LIGHT_CONTROL_SET` varchar(64) DEFAULT NULL COMMENT '歌曲灯光设置',"
				  +"`AUDIO_EFFECT_SET` varchar(64) DEFAULT NULL COMMENT '歌曲音效设置',"
				  +"`FILE_FORMAT` varchar(64) DEFAULT NULL COMMENT '文件格式',"
				  +"`SONG_BIT_RATE` varchar(64) DEFAULT NULL COMMENT '歌曲码流',"
				  +"`SONG_THEME` varchar(64) DEFAULT NULL COMMENT '主题字段（祝福歌曲、儿童歌曲）',"
				  +"`FIRST_WORD` varchar(64) DEFAULT NULL COMMENT '笔画（歌名第一个字的笔画数）',"
				  +"`LOCAL_PATH` varchar(64) DEFAULT NULL COMMENT '本地路径',"
				  +"`SERVER_PATH` varchar(64) DEFAULT NULL COMMENT '服务器路径',"
				  +"`SERVER_PATH1` varchar(64) DEFAULT NULL COMMENT '服务器路径1',"
				  +"`RELATIVE_PATH` varchar(64) DEFAULT NULL COMMENT '歌曲具体路径',"
				  +"`FILE_SIZE` varchar(64) DEFAULT NULL COMMENT '歌曲的文件大小',"
				  +"`VIDEO_SATURATION` varchar(64) DEFAULT NULL COMMENT '歌曲视频的饱和度',"
				  +"`VIDEO_LUMINANCE` varchar(64) DEFAULT NULL COMMENT '歌曲视频的亮度',"
				  +"`VIDEO_CONTRAST` varchar(64) DEFAULT NULL COMMENT '歌曲视频的对比度',"
				  +"`LYRIC` varchar(64) DEFAULT NULL COMMENT '歌曲歌词',"
				  +"`WORD_HEAD_CODE` varchar(64) DEFAULT NULL COMMENT '字头编号',"
				  +"`CULTURE_CODE` varchar(64) DEFAULT NULL COMMENT '文化部编码',"
				  +"`ISSUE_YEAR` varchar(64) DEFAULT NULL COMMENT '歌曲年代',"
				  +"`NEW_SONG` varchar(64) DEFAULT NULL COMMENT '新歌字段（用于新歌推荐）',"
				  +"PRIMARY KEY (`SONG_ID`)"
				  +");";
		 */
		return sql;
	}

	public static void main(String[] args) {
		SQLiteService sqlite = new SQLiteService();
		String version = getVersion();
		String dbName = "F:\\sqlite\\ckt.db";
		File path = new File("F:\\sqlite");
		if(!path.exists()){
			path.mkdir();
		}
		File file = new File(dbName);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Connection conn = sqlite.getConnection(dbName);
		try {
			//更改用户版本
			String updateVersion = Param.SQLITE_UPDATE_USER_VERSION + version;
			sqlite.updateUserVersion(conn, updateVersion);
			//创建表
			sqlite.exeCreateTable(CREATE_TABLE_SUPERSTAR_ADVERT, conn);
			sqlite.exeCreateTable(CREATE_TABLE_SUPERSTAR_INTERFACE, conn);
			sqlite.exeCreateTable(CREATE_TABLE_SUPERSTAR_SONG,conn);
			sqlite.exeCreateTable(CREATE_TABLE_SUPERSTAR_SINGER, conn);
			Connection mysqlCon = JDBCUtil.getConnection();
			insertSuperstarAdvert(mysqlCon);
			insertSuperstarInterface(mysqlCon);
			
			mysqlCon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * //写入数据
			Connection mysqlCon = JDBCUtil.getConnection();
			String sql = "select * from song_manage";
			//String insertSql = "INSERT INTO 'song_manage' VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//39
			String insertSql = "";
			Statement stat = conn.createStatement();
			PreparedStatement mysqlState =  mysqlCon.prepareStatement(sql);
			ResultSet result = mysqlState.executeQuery();
			while(result.next()){
				insertSql = "INSERT INTO `song_manage` VALUES"
						+ " ('"
						+ result.getString(1) + "','" + result.getString(2) + "','"
						+ result.getString(3) + "','" + result.getString(4) + "','"
						+ result.getString(5) + "','" + result.getString(6) + "','"
						+ result.getString(7) + "','" + result.getString(8) + "','"
						+ result.getString(9) + "','" + result.getString(10) + "','"
						+ result.getString(11) + "','" + result.getString(12) + "','"
						+ result.getString(13) + "','" + result.getString(14) + "','"
						+ result.getString(15) + "','" + result.getString(16) + "','"
						+ result.getString(17) + "','" + result.getString(18) + "','"
						+ result.getString(19) + "','" + result.getString(20) + "','"
						+ result.getString(21) + "','" + result.getString(22) + "','"
						+ result.getString(23) + "','" + result.getString(24) + "','"
						+ result.getString(25) + "','" + result.getString(26) + "','"
						+ result.getString(27) + "','" + result.getString(28) + "','"
						+ result.getString(29) + "','" + result.getString(30) + "','"
						+ result.getString(31) + "','" + result.getString(32) + "','"
						+ result.getString(33) + "','" + result.getString(34) + "','"
						+ result.getString(35) + "','" + result.getString(36) + "','"
						+ result.getString(37) + "','" + result.getString(38) + "','"
						+ result.getString(39)+"')";
				stat.addBatch(insertSql);
				//stat.executeUpdate(insertSql, columnNames);
				//System.out.println(result.getString(1));
			}
			stat.executeBatch();
			mysqlCon.close();
	 */
}
