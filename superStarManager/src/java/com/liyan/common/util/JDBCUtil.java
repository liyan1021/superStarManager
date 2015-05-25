package com.liyan.common.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtil {
	private JDBCUtil(){
		
	}
	private static String url;
	private static String user;
	private static String password;
	// 静态代码块做了两件事情: 读取配置文件，获得四要素注册驱动s
	static{
		try{
			// 读取配置文件
			Properties props = new Properties();
			InputStream in = JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
			props.load(in);
			//解析配置文件
			url = props.getProperty("url");
			user = props.getProperty("user");
			password = props.getProperty("password");
			String driverClass = props.getProperty("driverClass");
			// 注册驱动将驱动类加载到内存
			Class.forName(driverClass);
		}catch(Exception e){
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static Connection getConnection() throws SQLException{
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	
	public static void release(Connection conn,Statement stmt, ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
}
