package com.liyan.superstar.model;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @ClassName: GenerateJavaBeanUtil
 * @author A18ccms a18ccms_gmail_com
 * @date 2012-11-11 上午12:55:54
 * 映射工具类
 *
 */
public class GenerateJavaBeanUtil {
	private static final Log log = LogFactory.getLog(GenerateJavaBeanUtil.class);
	private String tableName;// 表名
	private String[] colnames; // 列名数组
	private String[] colTypes; // 列名类型数组
	private int[] colSizes; // 列名大小数组
	private boolean f_util = false; // 是否需要导入包java.util.Date
	private boolean f_sql = false; // 是否需要导入包java.sql.*
	private String dataBaseUrl = "jdbc:mysql://localhost:3306/test";
	private String userName = "root";
	private String password = "root";
	private String driver = "com.mysql.jdbc.Driver";
	private String packagePath;
	
	public void generateJavaBean() {
		Connection con = null;
		String sql = "select * from " + tableName;
		PreparedStatement pStemt = null;
		PrintWriter pw =null;
		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e1) {
				log.error(e1);
			}
			con = DriverManager.getConnection(dataBaseUrl, userName, password);
			pStemt = con.prepareStatement(sql);
			ResultSetMetaData rsmd = pStemt.getMetaData();
			int size = rsmd.getColumnCount(); // 统计列
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < size; i++) {
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1);
				if (colTypes[i].equalsIgnoreCase("datetime")) {
					f_util = true;
				}
				if (colTypes[i].equalsIgnoreCase("image")
						|| colTypes[i].equalsIgnoreCase("text")) {
					f_sql = true;
				}
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
			}
			String content = parse(colnames, colTypes, colSizes, packagePath);
			try {
				File directory = new File("");
				String path = this.getClass().getResource("").getPath();
				log.info("Generate JavaBean path="+path);

				log.info("src/?/"
						+ path.substring(path.lastIndexOf("/com/",
								path.length())));
				FileWriter fw = new FileWriter(directory.getAbsolutePath()
						+ "/src/"
						+ path.substring(
								path.lastIndexOf("/java/com/", path.length()),
								path.length()) + initcap(tableName) + ".java");
				pw = new PrintWriter(fw);
				pw.println(content);
				pw.flush();
				pw.close();
				log.info("Generate Success!");
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			log.error(e);
		} finally {
			try {
				con.close();
				pStemt.close();
				pw.close();
			} catch (SQLException e) {
				log.error(e);
			}
		}
	}

	
	/**
	 * @Title: parse
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @param packagePath
	 * @return
	 * @return String  返回类型
	 * @throws
	 */
	private String parse(String[] colnames, String[] colTypes, int[] colSizes,
			String packagePath) {
		StringBuffer sb = new StringBuffer();
		if (f_util) {
			sb.append("import java.util.Date;\r\n");
		}
		if (f_sql) {
			sb.append("import java.sql.*;\r\n");
		}
		sb.append("package ").append(packagePath).append(";");
		sb.append("\r\n\r\n");
		sb.append("/**\r\n");
		sb.append("* " + tableName + " 实体类\r\n");
		sb.append("* " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r\n");
		sb.append("* create by GenerateJavaBeanUtil "  + "\r\n");
		sb.append("*/ \r");
		sb.append("public class " + initcap(tableName) + "{\r\n");
		processAllAttrs(sb);// 属性
		processAllMethod(sb);// get set方法
		sb.append("}\r\n");
		return sb.toString();
	}


	/**
	 * @Title: processAllAttrs
	 * @param sb
	 * @return void  返回类型
	 * @throws
	 */
	private void processAllAttrs(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " "
					+ colnames[i] + ";\r\n");
		}
	}

	
	/**
	 * @Title: processAllMethod
	 * @param sb
	 * @return void  返回类型
	 * @throws
	 */
	private void processAllMethod(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tpublic void set" + initcap(colnames[i]) + "("
					+ sqlType2JavaType(colTypes[i]) + " " +
					colnames[i] + "){\r\n");
			sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
			sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get"
					+ initcap(colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
		}
	}

	
	/**
	 * @Title: initcap
	 * @param str
	 * @return
	 * @return String  返回类型
	 * @throws
	 */
	private String initcap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}
	
	/**
	 * @Title: sqlType2JavaType
	 * @param sqlType
	 * @return
	 * @return String  返回类型
	 * @throws
	 */
	private String sqlType2JavaType(String sqlType) {
		if (sqlType.equalsIgnoreCase("bit")) {
			return "boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "int";
		} else if (sqlType.equalsIgnoreCase("bigint")
				|| sqlType.equalsIgnoreCase("timestamp")) {
			return "long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("decimal")
				|| sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real")
				|| sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("varchar")
				|| sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar")
				|| sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blod";
		}else if(sqlType.equalsIgnoreCase("tinyint")){
			return "byte";
		}
		return null;

	}

	/**
	 * 
	 * @Title: main
	 * @param args
	 * @return void  返回类型
	 * @throws
	 */
	public static void main(String[] args) {
		GenerateJavaBeanUtil generate =new GenerateJavaBeanUtil();
		generate.setDataBaseUrl("jdbc:mysql://localhost:3306/test");
		generate.setDriver("com.mysql.jdbc.Driver");
		generate.setUserName("root");
		generate.setPassword("root");
		generate.setPackagePath("com.liyan.bigsinger.model");
		generate.setTableName("singer");
		generate.generateJavaBean();
	}


	public String getDataBaseUrl() {
		return dataBaseUrl;
	}


	public void setDataBaseUrl(String dataBaseUrl) {
		this.dataBaseUrl = dataBaseUrl;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getDriver() {
		return driver;
	}


	public void setDriver(String driver) {
		this.driver = driver;
	}


	public String getPackagePath() {
		return packagePath;
	}


	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}