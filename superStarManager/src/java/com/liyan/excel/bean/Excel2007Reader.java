package com.liyan.excel.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.liyan.common.util.CommonFiled;
import com.liyan.superstar.service.SongService;
import com.liyan.superstar.service.impl.SongServiceImpl;


/**
 * 抽象Excel2007读取器，excel2007的底层数据结构是xml文件，采用SAX的事件驱动的方法解析
 * xml，需要继承DefaultHandler，在遇到文件内容时，事件会触发，这种做法可以大大降低
 * 内存的耗费，特别使用于大数据量的文件。
 *
 */
public class Excel2007Reader extends DefaultHandler {
	//共享字符串表
	private SharedStringsTable sst;
	//上一次的内容
	private String lastContents;
	private boolean nextIsString;

	private int sheetIndex = -1;
	private List<String> rowlist = new ArrayList<String>();
	private HashMap<String,String> rowMap= new HashMap<String,String>();
	//当前行
	private int curRow = 0;
	//当前列
	private int curCol = 0;
	//日期标志
	private boolean dateFlag;
	//数字标志
	private boolean numberFlag;
	
	private boolean isTElement;
	
	//开始验证的行数
	private long start_row ;
	private String check_method;
	private Object obj ;
	private String row_value;
	private OPCPackage pkg;
    
	/**
	 * 遍历工作簿中所有的电子表格
	 * @param filename
	 * @param Object 
	 * @throws Exception
	 */
	public void process(String filename,long start_row,String check_method, Object obj) throws Exception {
		pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		Iterator<InputStream> sheets = r.getSheetsData();
		this.start_row = start_row; 
		this.check_method = check_method ;
		this.obj = obj ; 
		while (sheets.hasNext()) {
			curRow = 0;
			sheetIndex++;
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
		}
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst)
			throws SAXException {
		XMLReader parser = XMLReaderFactory
				.createXMLReader("org.apache.xerces.parsers.SAXParser");
		
		this.sst = sst;
		parser.setContentHandler(this);
		return parser;
	}
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		//c => 单元格
		if(name.equals("c")){  
            String type = attributes.getValue("t");  
            if(StringUtils.isNotBlank(type) && type.equals("s")){  
            	nextIsString = true;  
            }else{  
            	nextIsString = false;  
            }  
            row_value = attributes.getValue("r");
        }
		// 置空
		lastContents = "";
	}
	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		
		// 根据SST的索引值的到单元格的真正要存储的字符串
		// 这时characters()方法可能会被调用多次
		if (nextIsString) {
			try {
				int idx = Integer.parseInt(lastContents);
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
			} catch (Exception e) {

			}
		}
		if ("v".equals(name)) {
			String value = lastContents.trim();
			value = value.equals("")?" ":value;
//			rowlist.add(curCol, value);
			rowMap.put(row_value.replaceAll("\\d+",""), value); 
			curCol++;
		}else {
			//如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
			if (name.equals("row")) {
//				rowReader.getRows(sheetIndex,curRow,rowlist);
				if(check_method.equals(CommonFiled.CHECK_INSERT_MUSIC)){
					if(curRow>=start_row){
						SongService songSerivce = (SongService)obj;
						songSerivce.checkAndInsertMusic(sheetIndex,curRow,rowMap);
					}
				}
				
//				rowlist.clear();
				rowMap.clear();
				curRow++;
				curCol = 0;
			}
		}
		
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//得到单元格内容的值
		lastContents += new String(ch, start, length);
	}
	
	
	@Override
	public void endDocument() throws SAXException {
		 try {  
	            pkg.flush();  
	            pkg.close();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	}

	/*public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		//ExcelReaderUtil.readExcel(reader, "F://te03.xls");
		long start_row = 0;  
		ExcelReaderService.readExcel("D://歌曲导入模板55000.xlsx",start_row);
		long end = System.currentTimeMillis();
		System.out.println("----------"+(start-end)+"ms--------------");
	}*/
	public static void main(String args[]){
		 String str="AH11"; 
		 System.out.println(); 

	}
}
