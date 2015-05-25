package com.liyan.superstar.service;

import java.util.HashMap;

public interface SQLiteService {


	HashMap<String, String> generateSql(String dbPath, String FileName) throws Exception;

}
