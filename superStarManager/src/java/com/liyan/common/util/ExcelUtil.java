package com.liyan.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	static Logger log = Logger.getLogger(ExcelUtil.class);
	 /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    public static String getStringCellValue(Cell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     * 
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    public static String getDateCellValue(Cell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == Cell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == Cell.CELL_TYPE_STRING) {
                result = getStringCellValue(cell);
//                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == Cell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据Cell类型设置数据
     * @param cell
     * @return
     */
    public static String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case Cell.CELL_TYPE_NUMERIC:
            case Cell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是Date类型则，转化为Data格式
                    
                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
                    
                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                    
                }else{
                	// 如果是纯数字
                    // 取得当前Cell的数值
                	double number = cell.getNumericCellValue();
                	//整数
                	 if(number % 1.0 == 0){
     			        return String.valueOf((long)number);
                	 }else{
                		number = new Double(number);
                	 }
                    cellvalue = String.valueOf(number);
                }
                break;
            }
            // 如果当前Cell的Type为STRIN
            case Cell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
    public static void main(String args[]){
    	try{
	    	//使用File占用内存较低
	    	File file = new File("D:\\歌曲导入模板55000.xlsx");
	    	log.info("------------导入模版-------------"+file.getName());
	    	log.info("------------开始读取模版-------------");
	    	long start = System.currentTimeMillis();
	    	/*OPCPackage pkg = OPCPackage.open(file.getAbsolutePath());
	    	XSSFWorkbook wb = new XSSFWorkbook(pkg);*/
	    	Workbook wb = WorkbookFactory.create(new File("D:\\歌曲导入模板.xls"));
	        Sheet sheet = wb.getSheetAt(0);
	        for (Row row : sheet) {
	          StringBuffer sb = new StringBuffer("==== 行号："+row.getRowNum());
	          for (Cell cell : row) {
	        	  sb.append(","+getStringCellValue(cell));
	          }
	          log.info(sb.toString());
	        }
	        long end = System.currentTimeMillis();
//	    	pkg.close();
	    	log.info("------------读取结束-------------");
	    	log.info("------------"+(end-start)+"ms-------------");
    	} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
