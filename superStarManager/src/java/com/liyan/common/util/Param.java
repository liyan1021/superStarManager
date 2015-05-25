package com.liyan.common.util;

import java.nio.charset.Charset;

public class Param {
	
	public static final int SERVER_PORT = 9123;
	
	public static final int BYTE_DEFAULT_SIZE = 400;
	
	public static final int BYTE_BUFFER_ALLOCATE_SIZE = 1024 * 1024 * 10;
	
	public static final int CHAR_DEFAULT_SIZE = 2048;
	
	public static final int SOCKET_CANCEL_DELAY_TIME = 2000;
	
	public static final Charset CHARSET_UTF8 = Charset.forName("utf-8");
	
	public static final String SOCKET_RESPONSE_TYPE_FILE = "file";
	
	public static final String SOCKET_RESPONSE_TYPE_COMMAND = "command";
	
	public static final String SQLITE_CREATE_TABLE_MUSIC = "Create TABLE testtable( data1 bigint,data2 bigint,data3 varchar(100),Primary Key(data1,data2));";
	
	public static final String SQLITE_UPDATE_USER_VERSION = "pragma user_version=";
	
	//public static String CLIENT_APK_PATH = Environment.getExternalStorageDirectory() + "/vod_ckt";
	
	//public static String CLIENT_SQLITE_PATH = Environment.getExternalStorageDirectory() + "/vod_ckt/db";
	
	public static final String CLIENT_APK_NAME = "VOD_CKT.apk";
	
	public static final String CLIENT_SQLITE_NAME = "VOD_CKT.db";
	
	public static final int HEART_BEAT_DELAY_TIME = 15 * 1000;
	
	public static final int SERVER_BOTH_IDLE_TIME = 10;
	
	public static final int SERVER_WRITER_TIMEOUT = 10000;
	
	public static final boolean SERVER_USE_READ_OPERATION = true;
	
	public static final int TEXT_DECODER_MAX_LENGTH = 2048000;
	
	public static final int TEXT_ENCODER_MAX_LENGTH = 2048000;
	
	public static final int CLIENT_CONNECT_TIMEOUT = 30000;
	
	public static final String SUPERSTAR_INTERFACE_TYPE_HOME = "1";
	
	public static final String SUPERSTAR_INTERFACE_TYPE_SCREEN = "2";
	
	public static final String TEST_APK_PATH = "F:\\test";
	
	public static final String TEST_SQLITE_PATH = "F:\\test";
	
	public static final String SERVER_IP = "192.168.1.111";
	
	public static final int SOCKET_KLT_PORT = 9000;
	
}
