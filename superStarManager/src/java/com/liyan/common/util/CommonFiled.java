package com.liyan.common.util;

public class CommonFiled {
	public static final String DEL_STATE_Y = "1" ;
	public static final String DEL_STATE_N = "0" ;
	//操作
	public static final String OPT_LOG_ADD  = "1" ; 
	public static final String OPT_LOG_DEL  = "2" ; 
	public static final String OPT_LOG_MODIFY  = "3" ; 
	public static final String OPT_LOG_SEARCH  = "4" ;
	public static final String OPT_LOG_LOGIN = "5" ; 
	public static final String OPT_LOG_EXPORT = "6" ;
	public static final String OPT_LOG_IMPORT = "7";
	
	//发布状态
	
	public static final String PUSH_STATE_NO= "0" ;  //未发布
	public static final String PUSH_STATE_ALREADY = "1" ;  //已发布
	public static final String PUSH_STATE_READY = "2" ;  //预发布
	
	public static final String MAINSTAR_MAIN = "1"; //主打歌星
	public static final String MAINSTAR_NEW  = "2" ;// 新星推荐
	
	
	public static final String NET_STATE_NORMAL = "1" ; //网络状态正常
	public static final String NET_STATE_EXCEPTION = "2" ; //网络状态异常
	public static final String DEVICE_STATE_NORMAL = "1"; //设备状态正常
	public static final String DEVICE_STATE_EXCEPTION = "2"; //设备状态异常
	public static final String ROOM_STATE_OPEN = "1";  //开关房状态正常
	public static final String ROOM_STATE_CLOSE  ="2";  //开关房状态异常
	
	//歌星类别
	public static final String SINGER_TYPE_MALE = "1" ;
    public static final String SINGER_TYPE_FEMALE= "2"; 
	public static final String SINGER_TYPE_TEAM= "3"; 
	public static final String SINGER_TYPE_ALL = "4" ;
	
	//字典类别
	
	public static final String DICT_TYPE_AREA = "1"; //歌星区域
	public static final String DICT_TYPE_THEME ="2" ;//主题
	public static final String DICT_TYPE_SONGTYPE="3" ;//曲种
	public static final String DICT_TYPE_LANGUAGE="4" ;//语种
	//年代
	public static final String DICT_YEAR_60="1";
	public static final String DICT_YEAR_70="2";
	public static final String DICT_YEAR_80="3";
	public static final String DICT_YEAR_90="4";
	public static final String DICT_YEAR_OTHER="5";
	public static final String DICT_THEME_EG = "1"; //儿歌
	public static final String DICT_THEME_JG = "2"; //军歌
	public static final String DICT_THEME_ZF = "3"; //祝福
	public static final String DICT_THEME_DC = "4"; //独创
	public static final String DICT_THEME_YG = "5"; //摇滚
	public static final String DICT_THEME_XY = "6"; //校园歌曲
	public static final String DICT_THEME_QG = "7"; //情歌对唱
	public static final String DICT_THEME_HG = "8"; //红歌
	public static final String DICT_SONG_TYPE_JJ = "1";  //京剧 
	public static final String DICT_SONG_TYPE_HMX = "2"; //黄梅戏
	public static final String DICT_SONG_TYPE_TS = "3"; //通俗
	public static final String DICT_SONG_TYPE_YJ = "4"; //豫剧
	public static final String DICT_SONG_TYPE_KJ = "5"; //昆剧
	public static final String DICT_SONG_TYPE_YUEJ = "6"; //越剧
	
	public static final String DICT_LANGUAGE_GY = "1" ;  //国语
	public static final String DICT_LANGUAGE_MNY = "2"; //闽南语
	public static final String DICT_LANGUAGE_YUEY = "3"; //粤语
	public static final String DICT_LANGUAGE_YINGY = "4"; //英语
	public static final String DICT_LANGUAGE_HY = "5"; //韩语
	public static final String DICT_LANGUAGE_RY = "6"; //日语
	public static final String DICT_LANGUAGE_OTHER = "7"; //其他
	
	public static final String DICT_TRACK_1 = "0" ; 
	public static final String DICT_TRACK_2 = "1" ;
	
	public static final String DICT_CHANNEL_LEFT= "2" ;
	public static final String DICT_CHANNEL_RIGHT= "3" ;
	
	public static final String DICT_VERSION_MTV ="1";  //MTV
	public static final String DICT_VERSION_CONCERT ="2";//演唱会
	public static final String DICT_VERSION_STORY ="3"; //故事情节
	public static final String DICT_VERSION_SCENERY ="4"; //风景
	public static final String DICT_VERSION_PERSON ="5"; //任务
	
	public static final String DICT_MOVIETYPE_TV ="1"; //电视剧
	public static final String DICT_MOVIETYPE_MV ="2"; //电影
	public static final String DICT_MOVIETYPE_YL ="3"; //娱乐节目
	public static final String CHECK_INSERT_MUSIC = "checkAndInsertMusic";
	
	
}
