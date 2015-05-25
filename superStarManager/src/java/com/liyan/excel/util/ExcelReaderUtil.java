/*package com.liyan.excel.util;

import com.liyan.excel.bean.Excel2007Reader;

public class ExcelReaderUtil {
	
	//excel2003扩展名
	public static final String EXCEL03_EXTENSION = ".xls";
	//excel2007扩展名
	public static final String EXCEL07_EXTENSION = ".xlsx";
	
	*//**
	 * 读取Excel文件，可能是03也可能是07版本
	 * @param excel03
	 * @param excel07
	 * @param fileName
	 * @param start_row 
	 * @throws Exception 
	 *//*
	public static void readExcel(String fileName, long start_row,String check_type) throws Exception{
		// 处理excel2003文件
		if (fileName.endsWith(EXCEL03_EXTENSION)){
			Excel2003Reader excel03 = new Excel2003Reader();
			excel03.setRowReader(reader);
			excel03.process(fileName);
		// 处理excel2007文件
		} else if (fileName.endsWith(EXCEL07_EXTENSION)){
			Excel2007Reader excel07 = new Excel2007Reader();
			excel07.process(fileName,start_row,check_type);
		} else {
			throw new  Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
		}
	}
}
*/