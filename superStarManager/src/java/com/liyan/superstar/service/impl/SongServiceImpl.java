package com.liyan.superstar.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.liyan.common.AppContext;
import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.common.util.ExcelUtil;
import com.liyan.excel.bean.Excel2007Reader;
import com.liyan.superstar.dao.DictionaryDao;
import com.liyan.superstar.dao.SingerDao;
import com.liyan.superstar.dao.SongDao;
import com.liyan.superstar.model.Dictionary;
import com.liyan.superstar.model.PassMusic;
import com.liyan.superstar.model.Singer;
import com.liyan.superstar.model.Song;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.superstar.service.SongService;

@Service
@Transactional
public class SongServiceImpl implements SongService{
	
	@Autowired
	private SongDao songDao ; 
	@Autowired
	private OperationLogService optLogService ;
	@Autowired
	private SingerDao singerDao ; 
	@Autowired
	private MsgBean msgBean ; 
	
	@Autowired
	private DictionaryDao dictDao ; 
	private Logger log = Logger.getLogger(SongServiceImpl.class);
	//验证错误歌曲列表
	private ArrayList<Song> songList = new ArrayList<Song>();
	@SuppressWarnings("deprecation")
	@Override
	public void exportMusic(Song song) {
		ArrayList<Song> result  = (ArrayList<Song>) songDao.query(song);
		Map<String, Map<String, String>> dictMap = (Map<String, Map<String, String>>) AppContext.getInstance().get("key.dict");
		try {
			String heads = "文件名称,大歌星编号,非大歌星编号,歌曲名,拼音,歌星,"
					+ "歌星名称,歌星类别,版本,伴唱音量,原唱音量,歌词,年代,分辨率,唱片公司"
					+ ",主题,影视类别,影视类别说明,曲种,语种,点唱次数,收藏次数,作词,作曲,"
					+ "原唱音轨,原唱声道,伴唱音轨,伴唱声道,存储类型,文件路径,文件大小,文件格式,"
					+ "灯光设置,播放时长,流格式,码率,入库时间,IDX_SHA1,DGX_SHA1,备注";
			String fileName = "歌曲曲库.xls" ; 
			HttpServletResponse response = ServletActionContext.getResponse();
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet("曲库");
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth((short) 15);
			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 生成一个字体
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.VIOLET.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式
			style.setFont(font);
			// 生成并设置另一个样式
			HSSFCellStyle style2 = workbook.createCellStyle();
			style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 生成另一个字体
			HSSFFont font2 = workbook.createFont();
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			// 把字体应用到当前的样式
			style2.setFont(font2);

			String[] headers = heads.split(",");

			// 产生表格标题行
			HSSFRow row = sheet.createRow(0);
			for (short i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				cell.setCellValue(headers[i]);
			}
			int i = 1;
			Iterator<Song> it = result.iterator();
			while (it.hasNext()) {
				HSSFRow rows = sheet.createRow(i);
				Song t_song = it.next();
				for (short j = 0; j < headers.length; j++) {
					if (headers[j].equals("文件名称")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getFile_name());

					}else if (headers[j].equals("大歌星编号")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getSuperstar_id();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("非大歌星编号")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getUnsuperstar_id();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("歌曲名")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getSong_name();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("拼音")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getSpell_first_letter_abbreviation();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("歌星")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getSinger_name();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("歌星名称")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getSinger_name();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("歌星类别")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getSinger_type();
						cell.setCellStyle(style2);
						/*Dictionary dict = dictDao.getDictByTypeCode(headers[j],type);
						if(dict==null){
							cell.setCellValue(type);
						}else{
							cell.setCellValue(dict.getDict_value());
							String value  = dictMap.get("singer_type").get(type);
							cell.setCellValue(value);
						}
						 */
						if(CommonUtils.isEmpty(type)){
							cell.setCellValue(type);
						}else{
							String value  = dictMap.get("singer_type").get(type);
							cell.setCellValue(value);
						}
						
					}else if (headers[j].equals("版本")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getVersion();
						cell.setCellStyle(style2);
					
//						Dictionary dict = dictDao.getDictByTypeCode(headers[j],type);
						/*if(dict==null){
							cell.setCellValue(type);
						}else{
							cell.setCellValue(dict.getDict_value());
						}*/
						if(CommonUtils.isEmpty(type)){
							cell.setCellValue(type);
						}else{
							String value = dictMap.get("version").get(type);
							cell.setCellValue(value);
						}

					}else if (headers[j].equals("伴唱音量")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getAccompany_volume();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("原唱音量")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getKaraoke_volume();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("歌词")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getLyric();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("年代")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getIssue_year();
						cell.setCellStyle(style2);
						/*Dictionary dict = dictDao.getDictByTypeCode(headers[j],type);
						if(dict==null){
							cell.setCellValue(type);
						}else{
							cell.setCellValue(dict.getDict_value());
						}*/
						if(CommonUtils.isEmpty(type)){
							cell.setCellValue(type);
						}else{
							String value = dictMap.get("issue_year").get(type);
							cell.setCellValue(value);
						}

					}else if (headers[j].equals("分辨率")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getResolution();
						cell.setCellStyle(style2);
						
						/*Dictionary dict = dictDao.getDictByTypeCode(headers[j],type);
						if(dict==null){
							cell.setCellValue(type);
						}else{
							cell.setCellValue(dict.getDict_value());
						}*/
						if(CommonUtils.isEmpty(type)){
							cell.setCellValue(type);
						}else{
							String value = dictMap.get("resolution").get(type);
							cell.setCellValue(value);
						}

					}else if (headers[j].equals("唱片公司")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getRecord_company();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("主题")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getSong_theme();
						cell.setCellStyle(style2);
						
//						cell.setCellValue(type);
						/*Dictionary dict = dictDao.getDictByTypeCode(headers[j],type);
						if(dict==null){
							cell.setCellValue(type);
						}else{
							cell.setCellValue(dict.getDict_value());
						}*/
						if(CommonUtils.isEmpty(type)){
							cell.setCellValue(type);
						}else{
							String value = dictMap.get("theme").get(type);
							cell.setCellValue(value);
						}

					}else if (headers[j].equals("影视类别")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getMovie_type();
						cell.setCellStyle(style2);
						
//						cell.setCellValue(type);
						/*Dictionary dict = dictDao.getDictByTypeCode(headers[j],type);
						if(dict==null){
							cell.setCellValue(type);
						}else{
							cell.setCellValue(dict.getDict_value());
						}*/
						if(CommonUtils.isEmpty(type)){
							cell.setCellValue(type);
						}else{
							String value = dictMap.get("movie_type").get(type);
							cell.setCellValue(value);
						}

					}else if (headers[j].equals("影视类别说明")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getMovie_type_info();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("曲种")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getSong_type();
						cell.setCellStyle(style2);
						
//						cell.setCellValue(type);
						/*Dictionary dict = dictDao.getDictByTypeCode(headers[j],type);
						if(dict==null){
							cell.setCellValue(type);
						}else{
							cell.setCellValue(dict.getDict_value());
						}*/
						if(CommonUtils.isEmpty(type)){
							cell.setCellValue(type);
						}else{
							String value = dictMap.get("song_type").get(type);
							cell.setCellValue(value);
						}

					}else if (headers[j].equals("语种")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getLanguage();
						cell.setCellStyle(style2);
//						cell.setCellValue(type);
						/*Dictionary dict = dictDao.getDictByTypeCode(headers[j],type);
						if(dict==null){
							cell.setCellValue(type);
						}else{
							cell.setCellValue(dict.getDict_value());
						}*/
						if(CommonUtils.isEmpty(type)){
							cell.setCellValue(type);
						}else{
							String value = dictMap.get("language").get(type);
							cell.setCellValue(value);
						}

					}else if (headers[j].equals("点唱次数")) {
						HSSFCell cell = rows.createCell(j);
						Integer type = t_song.getClick_number();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("收藏次数")) {
						HSSFCell cell = rows.createCell(j);
						Integer type = t_song.getCollection_number();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("作词")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getAuthors();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("作曲")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getCompose();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("原唱音轨")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getOriginal_track();
						cell.setCellStyle(style2);
						
//						cell.setCellValue(type);
						/*Dictionary dict = dictDao.getDictByTypeCode(headers[j],type);
						if(dict==null){
							cell.setCellValue(type);
						}else{
							cell.setCellValue(dict.getDict_value());
						}*/
						if(CommonUtils.isEmpty(type)){
							cell.setCellValue(type);
						}else{
							String value = dictMap.get("track").get(type);
							cell.setCellValue(value);
						}


					}else if (headers[j].equals("原唱声道")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getOriginal_channel();
						cell.setCellStyle(style2);
						
						/*Dictionary dict = dictDao.getDictByTypeCode(headers[j],type);
						if(dict==null){
							cell.setCellValue(type);
						}else{
							cell.setCellValue(dict.getDict_value());
						}*/
						if(CommonUtils.isEmpty(type)){
							cell.setCellValue(type);
						}else{
							String value = dictMap.get("channel").get(type);
							cell.setCellValue(value);
						}
					}else if (headers[j].equals("伴唱音轨")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getAccompany_track();
						cell.setCellStyle(style2);
						
//						cell.setCellValue(type);
						/*Dictionary dict = dictDao.getDictByTypeCode(headers[j],type);
						if(dict==null){
							cell.setCellValue(type);
						}else{
							cell.setCellValue(dict.getDict_value());
						}*/
						if(CommonUtils.isEmpty(type)){
							cell.setCellValue(type);
						}else{
							String value = dictMap.get("track").get(type);
							cell.setCellValue(value);
						}
						

					}else if (headers[j].equals("伴唱声道")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getOriginal_channel();
						cell.setCellStyle(style2);
//						cell.setCellValue(type);
						/*Dictionary dict = dictDao.getDictByTypeCode(headers[j],type);
						if(dict==null){
							cell.setCellValue(type);
						}else{
							cell.setCellValue(dict.getDict_value());
						}*/
						if(CommonUtils.isEmpty(type)){
							cell.setCellValue(type);
						}else{
							String value = dictMap.get("channel").get(type);
							cell.setCellValue(value);
						}

					}else if (headers[j].equals("存储类型")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getStorage_type();
						cell.setCellStyle(style2);
						/*Dictionary dict = dictDao.getDictByTypeCode(headers[j],type);
						if(dict==null){
							cell.setCellValue(type);
						}else{
							cell.setCellValue(dict.getDict_value());
						}*/
						if(CommonUtils.isEmpty(type)){
							cell.setCellValue(type);
						}else{
							String value = dictMap.get("storage_type").get(type);
							cell.setCellValue(value);
						}

					}else if (headers[j].equals("文件路径")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getFile_path();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("文件大小")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getFile_length();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("文件格式")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getFile_format();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("灯光设置")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getLight_control_set();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("播放时长")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getPlay_time();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("流格式")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getFlow_type();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("码率")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getSong_bit_rate();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("入库时间")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getImport_time();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("IDX_SHA1")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getIdx_sha1();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("DGX_SHA1")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getDgx_sha1();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}else if (headers[j].equals("备注")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getRemark();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					}
					
					/*if (headers[j].equals("歌曲名称")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSong_name());

					} else if (headers[j].equals("歌星名字")) {
						HSSFCell cell = rows.createCell(j);
						String type = t_song.getSinger_name();
						cell.setCellStyle(style2);
						cell.setCellValue(type);

					} else if (headers[j].equals("歌星性别")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						String singer_sex = t_song.getSinger_sex();
						if(singer_sex.equals("1")){
							singer_sex = "男" ;
						}else if(singer_sex.equals("2")){
							singer_sex = "女" ;
						}
						cell.setCellValue(singer_sex);

					} else if (headers[j].equals("语种")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getLanguage());
					}else if (headers[j].equals("曲种")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSong_type());
					}else if (headers[j].equals("主题")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSong_theme());
					}else if (headers[j].equals("歌曲年代")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getIssue_year());
					}else if (headers[j].equals("拼音字头")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSpell_first_letter_abbreviation());
					}else if (headers[j].equals("繁体拼音字头")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSpell_first_letter_traditional());
					}else if (headers[j].equals("字数")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSong_word_count());
					}else if (headers[j].equals("演唱人数")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSinger_number());
					}else if (headers[j].equals("笔画")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getFirst_word());
					}else if (headers[j].equals("立体声")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getIs_stereo());
					}else if (headers[j].equals("音轨")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSong_track());
					}else if (headers[j].equals("声道")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSong_channel());
					}else if (headers[j].equals("影视信息")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getMovie_info());
					}else if (headers[j].equals("歌曲设置")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSong_set());
					}else if (headers[j].equals("伴唱音量")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getAccompany_volume());
					}else if (headers[j].equals("原唱音量")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getKaraoke_volume());
					}else if (headers[j].equals("影视字头")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getMovie_spell_first_letter());
					}else if (headers[j].equals("点击次数")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getClick_number());
					}else if (headers[j].equals("版本号")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSong_version());
					}else if (headers[j].equals("灯光设置")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getLight_control_set());
					}else if (headers[j].equals("音效设置")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getAudio_effect_set());
					}else if (headers[j].equals("文件格式")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getFile_format());
					}else if (headers[j].equals("歌曲码流")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSong_bit_rate());
					}else if (headers[j].equals("本地路径")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getLocal_path());
					}else if (headers[j].equals("服务器路径")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getServer_path());
					}else if (headers[j].equals("服务器路径1")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getServer_path1());
					}else if (headers[j].equals("具体路径")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getRelative_path());
					}else if (headers[j].equals("文件大小")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getFile_size());
					}else if (headers[j].equals("视频饱和度")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getVideo_saturation());
					}else if (headers[j].equals("视频亮度")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getVideo_luminance());
					}else if (headers[j].equals("视频对比度")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getVideo_contrast());
					}else if (headers[j].equals("歌词")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getLyric());
					}else if (headers[j].equals("字头编号")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getWord_head_code());
					}else if (headers[j].equals("文化部编码")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getCulture_code());
					}else if (headers[j].equals("新歌字段")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getNew_song());
					}*/
				}
				i++ ;
			}
			response.setContentType("application/vnd.ms-excel;charset=utf-8");  
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes(), "ISO8859-1"));//设定输出文件头
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.close();
			out.flush();
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "曲库管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_EXPORT ; 
			getOptLogService().setLog(currentUser,function,type);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public MsgBean editMusic(Song song) {
		try{
			
			Song findSong= songDao.find(song.getSong_id());
	//		findSong.setSong_id(song.getSong_id());
			/*findSong.setFile_name(song.getFile_name());
			findSong.setSuperstar_id(song.getSuperstar_id());
			findSong.setUnsuperstar_id(song.getUnsuperstar_id());*/
			findSong.setSong_name(song.getSong_name());
			findSong.setSpell_first_letter_abbreviation(song.getSpell_first_letter_abbreviation());
	/*		findSong.setSinger_id(song.getSinger_id());
			findSong.setSinger_name(song.getSinger_name());
			findSong.setSinger_type(song.getSinger_type());
	*/		findSong.setVersion(song.getVersion());
			findSong.setAccompany_volume(song.getAccompany_volume());
			findSong.setKaraoke_volume(song.getKaraoke_volume());
			findSong.setLyric(song.getLyric());
			findSong.setIssue_year(song.getIssue_year());
			findSong.setResolution(song.getResolution());
			findSong.setRecord_company(song.getRecord_company());
			findSong.setSong_theme(song.getSong_theme());
			findSong.setMovie_type(song.getMovie_type());
			findSong.setMovie_type_info(song.getMovie_type_info());
			findSong.setSong_type(song.getSong_type());
			findSong.setLanguage(song.getLanguage());
//			findSong.setClick_number(song.getClick_number());
//			findSong.setCollection_number(song.getCollection_number());
			findSong.setAuthors(song.getAuthors());
			findSong.setCompose(song.getCompose());
			findSong.setOriginal_track(song.getOriginal_track());
			findSong.setOriginal_channel(song.getOriginal_channel());
			findSong.setAccompany_track(song.getAccompany_track());
			findSong.setAccompany_channel(song.getAccompany_channel());
			findSong.setStorage_type(song.getStorage_type());
			findSong.setPlay_time(song.getPlay_time());
			findSong.setFlow_type(song.getFlow_type());
			findSong.setSong_bit_rate(song.getSong_bit_rate());
			findSong.setRemark(song.getRemark());
	
	
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "曲库管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 
			getOptLogService().setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("编辑成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("编辑失败");
		}
		return msgBean ; 
	}

	@Override
	public Pagination<Song> query(Song song, Integer page, Integer rows,String sort,String order) {
		return this.songDao.query(song, page, rows, sort, order);
	}
	//获取非过路歌曲
	@Override
	public Pagination<Song> unPassMusicList(Song song, Integer page,
			Integer rows) {
		return this.songDao.unPassMusicList(song, page, rows);
	}
	
	@Override
	public MsgBean importMusic(File importFile) {
		try{
			/*Workbook workbook = null;
	        try {
	        	workbook = new XSSFWorkbook(new FileInputStream(importFile));
	        } catch (Exception ex) {
	        	workbook = new HSSFWorkbook(new FileInputStream(importFile));
	        }*/
			String alertStr = "";
			Dictionary dict  = null ; 
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(importFile));
    		HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
    		ArrayList<Song> list = new ArrayList<Song>();
    		for(int i = 2 ; i <= sheet.getLastRowNum() ; i ++){
    			HSSFRow row = sheet.getRow(i);
    			if (row == null) {
    				msgBean.setSign(false);
    				msgBean.setMsg("上传失败：文件为空");
					return msgBean ;
				}
    			if(ExcelUtil.getCellFormatValue(row.getCell(1)).trim().isEmpty() &&ExcelUtil.getCellFormatValue(row.getCell(2)).trim().isEmpty() 
    					&&ExcelUtil.getCellFormatValue(row.getCell(3)).trim().isEmpty() &&ExcelUtil.getCellFormatValue(row.getCell(4)).trim().isEmpty() ){
    				alertStr += "该行歌曲为空"+":错误行："+i+"<br>" ;
    				continue ; 
    			}
    			Song song = new Song();
    			
    			song.setSong_id(UUID.randomUUID().toString());
    			//判断歌曲是否存在
    			if(songDao.findByName(ExcelUtil.getCellFormatValue(row.getCell(2)).trim())!=null){
    				alertStr += "歌曲已存在"+":错误行："+i+"<br>" ;
    				continue ; 
    			}
    			song.setFile_name(ExcelUtil.getCellFormatValue(row.getCell(2)).trim());
    			//验证大歌星ID
    			if(ExcelUtil.getCellFormatValue(row.getCell(3)).trim().length()>64){
    				alertStr += "大歌星ID长度不能超过64"+":错误行："+i+"<br>" ;
    				continue ; 
    			}
    			song.setSuperstar_id(ExcelUtil.getCellFormatValue(row.getCell(3)).trim());
    			//验证非大歌星ID
    			if(ExcelUtil.getCellFormatValue(row.getCell(4)).trim().length()>64){
    				alertStr += "非大歌星ID长度不能超过64"+":错误行："+i+"<br>" ;
    				continue ; 
    			}
    			song.setUnsuperstar_id(ExcelUtil.getCellFormatValue(row.getCell(4)).trim());
    			//验证歌曲名称
    			if(CommonUtils.isEmpty(ExcelUtil.getCellFormatValue(row.getCell(5)).trim())){
    				alertStr += "歌曲名称不能为空"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			if(ExcelUtil.getCellFormatValue(row.getCell(5)).trim().length()>100){
    				alertStr += "歌曲名称长度不能超过100"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			
    			song.setSong_name(ExcelUtil.getCellFormatValue(row.getCell(5)).trim());
    			
    			//验证拼音
    			if(ExcelUtil.getCellFormatValue(row.getCell(6)).trim().length()>20){
    				alertStr += "拼音长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			/*if(CommonUtils.isEmpty(ExcelUtil.getCellFormatValue(row.getCell(6)).trim())){
    				alertStr += "拼音不能为空"+":错误行："+i+"<br>" ;
    				continue ;
    			}*/
    			song.setSpell_first_letter_abbreviation(ExcelUtil.getCellFormatValue(row.getCell(6)).trim());
    			//验证歌星
    			List<Singer> singerList = singerDao.findByName(ExcelUtil.getCellFormatValue(row.getCell(7)).trim());
    			if(singerList.size()!=0){
    				song.setSinger_id(singerList.get(0).getStar_id());
    			}
    			if(ExcelUtil.getCellFormatValue(row.getCell(7)).trim().length()>20){
    				alertStr += "歌星名字长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			/*if(CommonUtils.isEmpty(ExcelUtil.getCellFormatValue(row.getCell(7)).trim())){
    				alertStr += "歌星名字不能为空"+":错误行："+i+"<br>" ;
    				continue ;
    			}*/
    			song.setSinger_name(ExcelUtil.getCellFormatValue(row.getCell(7)).trim());
    			//验证歌星类型
    			String  singer_type = ExcelUtil.getCellFormatValue(row.getCell(8)).trim() ; 
    			/*if(ExcelUtil.getCellFormatValue(row.getCell(8)).trim().isEmpty()){
    				alertStr += "歌星类型不能为空"+":错误行："+i+"<br>" ;
    				continue ;
    			}*/
    			if(!singer_type.isEmpty()){
//    				dict = this.dictDao.getDictByTypeName("singer_type",singer_type);
    				if(dict == null ){
        				alertStr += "歌星类型不存在"+":错误行："+i+"<br>" ;
        				continue ;
        			}
    				song.setSinger_type(CommonUtils.nullToBlank(dict.getDict_code()));
    			}
    			
    			//验证版本
    			String version = ExcelUtil.getCellFormatValue(row.getCell(9)).trim();
    			if(version.length()>20){
    				alertStr += "版本长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			if(!version.isEmpty()){
	    			dict = this.dictDao.getDictByTypeName("version",version);
	    			if(dict == null ){
	    				alertStr += "版本不存在"+":错误行："+i+"<br>" ;
	    				continue ;
	    			}
	    			song.setVersion(CommonUtils.nullToBlank(dict.getDict_code()));
    			}
    			
    			
    			//验证原唱音量
    			String accompany_volume = ExcelUtil.getCellFormatValue(row.getCell(10)).trim();
    			if(accompany_volume.length()>20){
    				alertStr += "原唱音量长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setAccompany_volume(accompany_volume);
    			//验证伴唱音量
    			String karaoke_volume = ExcelUtil.getCellFormatValue(row.getCell(11)).trim();
    			if(karaoke_volume.length()>20){
    				alertStr += "伴唱音量长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setKaraoke_volume(karaoke_volume);
    			//验证歌词
    			song.setLyric(ExcelUtil.getCellFormatValue(row.getCell(12)).trim());
    			//验证年代
    			String issue_year = ExcelUtil.getCellFormatValue(row.getCell(13)).trim();
    			if(!issue_year.isEmpty()){
	    			dict = this.dictDao.getDictByTypeName("issue_year",issue_year);
	    			if(dict == null ){
	    				alertStr += "年代不存在"+":错误行："+i+"<br>" ;
	    				continue ;
	    			}
	    			song.setIssue_year(CommonUtils.nullToBlank(dict.getDict_code()));
    			}
    			
    			//验证分辨率
    			String resolution = ExcelUtil.getCellFormatValue(row.getCell(14)).trim();
    			if(!resolution.isEmpty()){
	    			dict = this.dictDao.getDictByTypeName("resolution",resolution);
	    			if(dict == null ){
	    				alertStr += "分辨率不存在"+":错误行："+i+"<br>" ;
	    				continue ;
	    			}
	    			song.setResolution(CommonUtils.nullToBlank(dict.getDict_code()));
    			}
    			
    			//验证唱片公司
    			String record_company = ExcelUtil.getCellFormatValue(row.getCell(15)).trim();
    			if(record_company.length()>20){
    				alertStr += "唱片公司长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			
    			//歌曲主题
    			String song_theme = ExcelUtil.getCellFormatValue(row.getCell(16)).trim();
    			if(!song_theme.isEmpty()){
    				dict = this.dictDao.getDictByTypeName("theme",song_theme);
        			if(dict == null ){
        				alertStr += "主题不存在"+":错误行："+i+"<br>" ;
        				continue ;
        			}
        			song.setSong_theme(CommonUtils.nullToBlank(dict.getDict_code()));
    			}
    			
    			//验证影视类型
    			
    			String movie_type = ExcelUtil.getCellFormatValue(row.getCell(17)).trim();
    			
    			if(movie_type.length()>20){
    				alertStr += "影视类型长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			if(!movie_type.isEmpty()){
    				dict = this.dictDao.getDictByTypeName("movie_type",movie_type);
        			if(dict == null ){
        				alertStr += "影视类型不存在"+":错误行："+i+"<br>" ;
        				continue ;
        			}
        			song.setMovie_type(CommonUtils.nullToBlank(dict.getDict_code()));
    			}
    			
    			//验证影视类型说明
    			String movie_type_info =  ExcelUtil.getCellFormatValue(row.getCell(18)).trim();
    			if(movie_type_info.length()>20){
    				alertStr += "影视类型说明长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setMovie_type_info(movie_type_info);
    			
    			//验证曲种

    			String song_type = ExcelUtil.getCellFormatValue(row.getCell(19)).trim();
    			if(!song_type.isEmpty()){
    				dict = this.dictDao.getDictByTypeName("song_type",song_type);
        			if(dict == null ){
        				alertStr += "曲种不存在"+":错误行："+i+"<br>" ;
        				continue ;
        			}
        			song.setSong_type(CommonUtils.nullToBlank(dict.getDict_code()));
    			}
    			
    			//验证语种
    			String language = ExcelUtil.getCellFormatValue(row.getCell(20)).trim();
    			if(!language.isEmpty()){
    				dict = this.dictDao.getDictByTypeName("language",language);
        			if(dict == null ){
        				alertStr += "语种不存在"+":错误行："+i+"<br>" ;
        				continue ;
        			}
        			song.setLanguage(CommonUtils.nullToBlank(dict.getDict_code()));
    			}
    			
    			//验证点播次数
    			song.setClick_number(Integer.parseInt(ExcelUtil.getCellFormatValue(row.getCell(21)).trim()));
    			//验证收藏次数
    			song.setCollection_number(Integer.parseInt(ExcelUtil.getCellFormatValue(row.getCell(22)).trim()));
    			//验证作词
    			String authors = ExcelUtil.getCellFormatValue(row.getCell(23)).trim();
    			if(authors.length()>20){
    				alertStr += "作词长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setAuthors(authors);
    			
    			//验证作曲
    			String compose = ExcelUtil.getCellFormatValue(row.getCell(24)).trim();
    			if(compose.length()>20){
    				alertStr += "作曲长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setCompose(compose);
    			
    			//验证原唱音轨
    			String original_track = ExcelUtil.getCellFormatValue(row.getCell(25)).trim();
    			if(!original_track.isEmpty()){
    				dict = this.dictDao.getDictByTypeName("track",original_track);
        			if(dict == null ){
        				alertStr += "音轨不存在"+":错误行："+i+"<br>" ;
        				continue ;
        			}
        			song.setOriginal_track(CommonUtils.nullToBlank(dict.getDict_code()));
    			}
    			
    			//验证原唱声道
    			String original_channel = ExcelUtil.getCellFormatValue(row.getCell(26)).trim();
    			if(!original_channel.isEmpty()){
    				dict = this.dictDao.getDictByTypeName("channel",original_channel);
    				if(dict == null ){
    					alertStr += "声道不存在"+":错误行："+i+"<br>" ;
    					continue ;
    				}
    				song.setOriginal_channel(CommonUtils.nullToBlank(dict.getDict_code()));
    			}
    			
    			//验证伴唱音轨
    			String accompany_track = ExcelUtil.getCellFormatValue(row.getCell(27)).trim();
    			if(!accompany_track.isEmpty()){
    				dict = this.dictDao.getDictByTypeName("track",accompany_track);
    				if(dict == null ){
    					alertStr += "音轨不存在"+":错误行："+i+"<br>" ;
    					continue ;
    				}
    				song.setAccompany_track(CommonUtils.nullToBlank(dict.getDict_code()));
    			}
    			
    			//验证伴唱声道
    			String accompany_channel = ExcelUtil.getCellFormatValue(row.getCell(28)).trim() ;
    			if(!accompany_channel.isEmpty()){
    				
    				dict = this.dictDao.getDictByTypeName("channel",accompany_channel);
    				if(dict == null ){
    					alertStr += "声道不存在"+":错误行："+i+"<br>" ;
    					continue ;
    				}
    				song.setAccompany_channel(CommonUtils.nullToBlank(dict.getDict_code()));
    			}
    			
    			//验证存储类型
    			dict = this.dictDao.getDictByTypeName("storage_type",ExcelUtil.getCellFormatValue(row.getCell(29)).trim());
    			if(!ExcelUtil.getCellFormatValue(row.getCell(29)).trim().isEmpty()){
	    			if(dict == null ){
	    				alertStr += "存储类型不存在"+":错误行："+i+"<br>" ;
	    				continue ;
	    			}
	    			song.setStorage_type(CommonUtils.nullToBlank(dict.getDict_code()));
    			}
    			//验证文件路径

    			String file_path = ExcelUtil.getCellFormatValue(row.getCell(30)).trim();
    			if(file_path.length()>100){
    				alertStr += "文件路径不能超过100"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setFile_path(file_path);
    			//验证文件大小
    			String file_length = ExcelUtil.getCellFormatValue(row.getCell(31)).trim();
    			if(file_length.length()>20){
    				alertStr += "文件大小不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setFile_length(file_length);
    			//验证文件格式
    			String file_format = ExcelUtil.getCellFormatValue(row.getCell(32)).trim() ;
    			if(file_format.length()>20){
    				alertStr += "文件类型不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setFile_format(file_format);
    			//验证灯光设置
    			String light_control_set = ExcelUtil.getCellFormatValue(row.getCell(33)).trim();
    			if(light_control_set.length()>20){
    				alertStr += "灯光设置长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setLight_control_set(light_control_set);
    			//验证播放时长
    			String play_time = ExcelUtil.getCellFormatValue(row.getCell(34)).trim();
    			if(play_time.length()>20){
    				alertStr += "播放时长长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setPlay_time(play_time);
    			//验证流格式
    			String flow_type = ExcelUtil.getCellFormatValue(row.getCell(35)).trim();
    			if(flow_type.length()>20){
    				alertStr += "播放时长长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setFlow_type(flow_type);
    			//验证码率
    			String bit_rate = ExcelUtil.getCellFormatValue(row.getCell(36)).trim();
    			if(bit_rate.length()>20){
    				alertStr += "码率长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setSong_bit_rate(bit_rate);
    			//验证导入时间
    			String import_time = ExcelUtil.getCellFormatValue(row.getCell(37)).trim();
    			if(bit_rate.length()>20){
    				alertStr += "导入时间长度不能超过20"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setImport_time(import_time);
    			
    			//验证IDX_SHA
    			String idx_sha = ExcelUtil.getCellFormatValue(row.getCell(38)).trim();
    			if(idx_sha.length()>20){
    				alertStr += "idx_sha长度不能超过100"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setIdx_sha1(idx_sha);
    			//验证DGX_SHA
    			String dgx_sha = ExcelUtil.getCellFormatValue(row.getCell(39)).trim();
    			if(dgx_sha.length()>20){
    				alertStr += "dgx_sha长度不能超过100"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setDgx_sha1(dgx_sha);
    			//验证备注
    			String remark = ExcelUtil.getCellFormatValue(row.getCell(40)).trim();
    			if(remark.length()>100){
    				alertStr += "备注长度不能超过100"+":错误行："+i+"<br>" ;
    				continue ;
    			}
    			song.setRemark(remark);
    			song.setIs_activity(CommonFiled.DEL_STATE_N);
    			list.add(song);
    		}
    		this.songDao.batchCreate(list);
    		msgBean.setSign(true);
			msgBean.setMsg("上传成功:<br>"+alertStr);
		} catch (FileNotFoundException e) {
			msgBean.setSign(false);
			msgBean.setMsg("上传失败:文件未找到");
			e.printStackTrace();
		} catch (IOException e) {
			msgBean.setSign(false);
			msgBean.setMsg("上传失败:文件错误");
		}
		return msgBean;
	}
	@Override
	public MsgBean newImportMusic(File importFile){
		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			long start_row = 2;  
			String check_method = CommonFiled.CHECK_INSERT_MUSIC ; 
//			ExcelReaderUtil.readExcel("D://歌曲导入模板55000.xlsx",start_row,check_type);
			Excel2007Reader excel07 = new Excel2007Reader();
			excel07.process("D://歌曲导入模板55000.xlsx",start_row,check_method,this);
			stopWatch.stop();
			System.out.println(stopWatch.getTotalTimeMillis());
			if(songList.size()>0){
				//验证错误列表Excel返回给用户
				for(int i = 0 ; i<songList.size();i++){
					System.out.println("------------------------------------------");
					System.out.println(songList.get(i).getSong_name()+":"+songList.get(i).getAlert_str()+",");
					System.out.println("------------------------------------------");
				}
			}
			
			msgBean.setSign(true);
			msgBean.setMsg("上传成功");
		} catch (Exception e) {
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("上传失败:文件错误");
		}
		return msgBean;
	}
	@Override
	public void checkAndInsertMusic(int sheetIndex, int curRow,HashMap<String, String> rowMap){
		String alertStr = "" ;
		Dictionary dict  = null ; 
//		log.info("---------开始验证第"+curRow+"行-----------");
		if(CommonUtils.nullToBlank(rowMap.get("A")).trim().isEmpty() &&(CommonUtils.nullToBlank(rowMap.get("B"))).trim().isEmpty() 
				&&(CommonUtils.nullToBlank(rowMap.get("C"))).trim().isEmpty() &&(CommonUtils.nullToBlank((rowMap.get("D"))).trim().isEmpty())){
			alertStr += "该行歌曲为空," ;
			//continue ; 
		}
		Song song = new Song();
		
		song.setSong_id(UUID.randomUUID().toString());
		//判断歌曲是否存在
		/*if(songDao.findByName((CommonUtils.nullToBlank(rowMap.get("C"))).trim())!=null){
			alertStr += "歌曲已存在," ;
			//continue ; 
		}*/
		song.setFile_name((CommonUtils.nullToBlank(rowMap.get("C")).trim()));
		//验证大歌星ID
		if((CommonUtils.nullToBlank(rowMap.get("D"))).trim().length()>64){
			alertStr += "大歌星ID长度不能超过64," ;
			//continue ; 
		}
		song.setSuperstar_id((CommonUtils.nullToBlank(rowMap.get("D")).trim()));
		//验证非大歌星ID
		if((CommonUtils.nullToBlank(rowMap.get("E"))).trim().length()>64){
			alertStr += "非大歌星ID长度不能超过64," ;
			//continue ; 
		}
		song.setUnsuperstar_id((CommonUtils.nullToBlank(rowMap.get("E"))).trim());
		//验证歌曲名称
		if(CommonUtils.isEmpty((CommonUtils.nullToBlank(rowMap.get("F"))).trim())){
			alertStr += "歌曲名称不能为空," ;
			//continue ;
		}
		if((CommonUtils.nullToBlank(rowMap.get("F"))).trim().length()>100){
			alertStr += "歌曲名称长度不能超过100," ;
			//continue ;
		}
		
		song.setSong_name((CommonUtils.nullToBlank(rowMap.get("F")).trim()));
		
		//验证拼音
		if((CommonUtils.nullToBlank(rowMap.get("G"))).trim().length()>20){
			alertStr += "拼音长度不能超过20," ;
			//continue ;
		}
		if(CommonUtils.isEmpty((CommonUtils.nullToBlank(rowMap.get("G")).trim()))){
			alertStr += "拼音不能为空," ;
			//continue ;
		}
		song.setSpell_first_letter_abbreviation(CommonUtils.nullToBlank(rowMap.get("G")).trim());
		//验证歌星
		List<Singer> singerList = singerDao.findByName(CommonUtils.nullToBlank(rowMap.get("H")).trim());
		if(singerList.size()!=0){
			song.setSinger_id(singerList.get(0).getStar_id());
		}
		if((CommonUtils.nullToBlank(rowMap.get("H"))).trim().length()>20){
			alertStr += "歌星名字长度不能超过20," ;
			//continue ;
		}
		if(CommonUtils.isEmpty((CommonUtils.nullToBlank(rowMap.get("H"))).trim())){
			alertStr += "歌星名字不能为空," ;
			//continue ;
		}
		song.setSinger_name((CommonUtils.nullToBlank(rowMap.get("H"))).trim());
		//验证歌星类型
		String  singer_type = (CommonUtils.nullToBlank(rowMap.get("I"))).trim() ; 
		if((CommonUtils.nullToBlank(rowMap.get("H")).trim().isEmpty())){
			alertStr += "歌星类型不能为空," ;
			//continue ;
		}
		if(!singer_type.isEmpty()){
			dict = this.dictDao.getDictByTypeName("singer_type",singer_type);
			if(dict==null){
				alertStr += "歌星类型不存在," ;
				song.setSinger_type(singer_type);
			}else{
				
				song.setSinger_type(CommonUtils.nullToBlank(dict.getDict_code()));
			}
		}
		
		//验证版本
		String version = (CommonUtils.nullToBlank(rowMap.get("J"))).trim();
		if(version.length()>20){
			alertStr += "版本长度不能超过20," ;
			//continue ;
		}
		if(!version.isEmpty()){
			dict = this.dictDao.getDictByTypeName("version",version);
			if(dict == null ){
				alertStr += "版本不存在," ;
				song.setVersion(version);
			}else{
				song.setVersion(CommonUtils.nullToBlank(dict.getDict_code()));
			}
		}
		
		
		//验证原唱音量
		String accompany_volume = (CommonUtils.nullToBlank(rowMap.get("L"))).trim();
		if(accompany_volume.length()>20){
			alertStr += "原唱音量长度不能超过20," ;
			//continue ;
		}
		song.setAccompany_volume(accompany_volume);
		//验证伴唱音量
		String karaoke_volume = (CommonUtils.nullToBlank(rowMap.get("K"))).trim();
		if(karaoke_volume.length()>20){
			alertStr += "伴唱音量长度不能超过20," ;
			//continue ;
		}
		song.setKaraoke_volume(karaoke_volume);
		//验证歌词
		song.setLyric((CommonUtils.nullToBlank(rowMap.get("M"))).trim());
		//验证年代
		String issue_year = (CommonUtils.nullToBlank(rowMap.get("N"))).trim();
		if(!issue_year.isEmpty()){
			dict = this.dictDao.getDictByTypeName("issue_year",issue_year);
			if(dict == null ){
				alertStr += "年代不存在," ;
				song.setIssue_year(issue_year);
			}else{
				song.setIssue_year(CommonUtils.nullToBlank(dict.getDict_code()));
			}
		}
		
		//验证分辨率
		String resolution = (CommonUtils.nullToBlank(rowMap.get("O"))).trim();
		if(!resolution.isEmpty()){
			dict = this.dictDao.getDictByTypeName("resolution",resolution);
			if(dict == null ){
				alertStr += "分辨率不存在," ;
				song.setResolution(resolution);
			}else{
				song.setResolution(CommonUtils.nullToBlank(dict.getDict_code()));
			}
		}
		
		//验证唱片公司
		String record_company = (CommonUtils.nullToBlank(rowMap.get("P"))).trim();
		if(record_company.length()>20){
			alertStr += "唱片公司长度不能超过20," ;
			//continue ;
		}
		
		//歌曲主题
		String song_theme = (CommonUtils.nullToBlank(rowMap.get("Q"))).trim();
		if(!song_theme.isEmpty()){
			dict = this.dictDao.getDictByTypeName("theme",song_theme);
			if(dict == null ){
				alertStr += "主题不存在," ;
				song.setSong_theme(song_theme);
			}else{
				song.setSong_theme(CommonUtils.nullToBlank(dict.getDict_code()));
			}
		}
		
		//验证影视类型
		
		String movie_type = (CommonUtils.nullToBlank(rowMap.get("R"))).trim();
		
		if(movie_type.length()>20){
			alertStr += "影视类型长度不能超过20," ;
			//continue ;
		}
		if(!movie_type.isEmpty()){
			dict = this.dictDao.getDictByTypeName("movie_type",movie_type);
			if(dict == null ){
				alertStr += "影视类型不存在," ;
				song.setMovie_type(movie_type);
			}else{
				song.setMovie_type(CommonUtils.nullToBlank(dict.getDict_code()));
			}
		}
		
		//验证影视类型说明
		String movie_type_info =  (CommonUtils.nullToBlank(rowMap.get("S"))).trim();
		if(movie_type_info.length()>20){
			alertStr += "影视类型说明长度不能超过20," ;
			//continue ;
		}
		song.setMovie_type_info(movie_type_info);
		
		//验证曲种

		String song_type = (CommonUtils.nullToBlank(rowMap.get("T"))).trim();
		if(!song_type.isEmpty()){
			dict = this.dictDao.getDictByTypeName("song_type",song_type);
			if(dict == null ){
				alertStr += "曲种不存在," ;
				song.setSong_type(song_type);
			}else{
				song.setSong_type(CommonUtils.nullToBlank(dict.getDict_code()));
			}
		}
		
		//验证语种
		String language = (CommonUtils.nullToBlank(rowMap.get("U"))).trim();
		if(!language.isEmpty()){
			dict = this.dictDao.getDictByTypeName("language",language);
			if(dict == null ){
				alertStr += "语种不存在," ;
				song.setLanguage(language);
			}else{
				song.setLanguage(CommonUtils.nullToBlank(dict.getDict_code()));
			}
		}
		
		//验证点播次数
		song.setClick_number(null);
		//验证收藏次数
		song.setCollection_number(null);
		//验证作词
		String authors = (CommonUtils.nullToBlank(rowMap.get("X"))).trim();
		if(authors.length()>20){
			alertStr += "作词长度不能超过20," ;
			//continue ;
		}
		song.setAuthors(authors);
		
		//验证作曲
		String compose = (CommonUtils.nullToBlank(rowMap.get("Y"))).trim();
		if(compose.length()>20){
			alertStr += "作曲长度不能超过20," ;
			//continue ;
		}
		song.setCompose(compose);
		
		//验证原唱音轨
		String original_track = (CommonUtils.nullToBlank(rowMap.get("Z"))).trim();
		if(!original_track.isEmpty()){
			dict = this.dictDao.getDictByTypeName("track",original_track);
			if(dict == null ){
				alertStr += "音轨不存在," ;
				song.setOriginal_track(original_track);
			}else{
				song.setOriginal_track(CommonUtils.nullToBlank(dict.getDict_code()));
			}
		}
		
		//验证原唱声道
		String original_channel = (CommonUtils.nullToBlank(rowMap.get("AA"))).trim();
		if(!original_channel.isEmpty()){
			dict = this.dictDao.getDictByTypeName("channel",original_channel);
			if(dict == null ){
				alertStr += "声道不存在," ;
				song.setOriginal_channel(original_channel);
			}else{
				song.setOriginal_channel(CommonUtils.nullToBlank(dict.getDict_code()));
			}
		}
		
		//验证伴唱音轨
		String accompany_track = (CommonUtils.nullToBlank(rowMap.get("AB"))).trim();
		if(!accompany_track.isEmpty()){
			dict = this.dictDao.getDictByTypeName("track",accompany_track);
			if(dict == null ){
				alertStr += "音轨不存在," ;
				//continue ;
				song.setAccompany_track(accompany_track);
			}else{
				song.setAccompany_track(CommonUtils.nullToBlank(dict.getDict_code()));
			}
		}
		
		//验证伴唱声道
		String accompany_channel = (CommonUtils.nullToBlank(rowMap.get("AC"))).trim() ;
		if(!accompany_channel.isEmpty()){
			
			dict = this.dictDao.getDictByTypeName("channel",accompany_channel);
			if(dict == null ){
				alertStr += "声道不存在," ;
				//continue ;
				song.setAccompany_channel(accompany_channel);
			}else{
				song.setAccompany_channel(CommonUtils.nullToBlank(dict.getDict_code()));
			}
		}
		
		//验证存储类型
		dict = this.dictDao.getDictByTypeName("storage_type",CommonUtils.nullToBlank(rowMap.get("AD")).trim());
		if(!(CommonUtils.nullToBlank(rowMap.get("AD")).trim().isEmpty())){
			if(dict == null ){
				alertStr += "存储类型不存在," ;
				song.setStorage_type(CommonUtils.nullToBlank(rowMap.get("AD").trim()));
			}else{
				song.setStorage_type(CommonUtils.nullToBlank(dict.getDict_code()));
			}
		}
		//验证文件路径

		String file_path = (CommonUtils.nullToBlank(rowMap.get("AE"))).trim();
		if(file_path.length()>100){
			alertStr += "文件路径不能超过100," ;
			//continue ;
		}
		song.setFile_path(file_path);
		//验证文件大小
		String file_length = (CommonUtils.nullToBlank(rowMap.get("AF"))).trim();
		if(file_length.length()>20){
			alertStr += "文件大小不能超过20," ;
			//continue ;
		}
		song.setFile_length(file_length);
		//验证文件格式
		String file_format = (CommonUtils.nullToBlank(rowMap.get("AG"))).trim() ;
		if(file_format.length()>20){
			alertStr += "文件类型不能超过20," ;
			//continue ;
		}
		song.setFile_format(file_format);
		//验证灯光设置
		String light_control_set = (CommonUtils.nullToBlank(rowMap.get("AH"))).trim();
		if(light_control_set.length()>20){
			alertStr += "灯光设置长度不能超过20," ;
			//continue ;
		}
		song.setLight_control_set(light_control_set);
		//验证播放时长
		String play_time = (CommonUtils.nullToBlank(rowMap.get("AI"))).trim();
		if(play_time.length()>20){
			alertStr += "播放时长长度不能超过20," ;
			//continue ;
		}
		song.setPlay_time(play_time);
		//验证流格式
		String flow_type = (CommonUtils.nullToBlank(rowMap.get("AJ"))).trim();
		if(flow_type.length()>20){
			alertStr += "播放时长长度不能超过20," ;
			//continue ;
		}
		song.setFlow_type(flow_type);
		//验证码率
		String bit_rate = (CommonUtils.nullToBlank(rowMap.get("AK"))).trim();
		if(bit_rate.length()>20){
			alertStr += "码率长度不能超过20," ;
			//continue ;
		}
		song.setSong_bit_rate(bit_rate);
		//验证导入时间
		String import_time = (CommonUtils.nullToBlank(rowMap.get("AL"))).trim();
		if(bit_rate.length()>20){
			alertStr += "导入时间长度不能超过20," ;
			//continue ;
		}
		song.setImport_time(import_time);
		
		//验证IDX_SHA
		String idx_sha = (CommonUtils.nullToBlank(rowMap.get("AM"))).trim();
		if(idx_sha.length()>20){
			alertStr += "idx_sha长度不能超过100," ;
			//continue ;
		}
		song.setIdx_sha1(idx_sha);
		//验证DGX_SHA
		String dgx_sha = (CommonUtils.nullToBlank(rowMap.get("AN"))).trim();
		if(dgx_sha.length()>20){
			alertStr += "dgx_sha长度不能超过100," ;
			//continue ;
		}
		song.setDgx_sha1(dgx_sha);
		//验证备注
		String remark = (CommonUtils.nullToBlank(rowMap.get("AO"))).trim();
		if(remark.length()>100){
			alertStr += "备注长度不能超过100," ;
			//continue ;
		}
		song.setRemark(remark);
		song.setIs_activity(CommonFiled.DEL_STATE_N);
		if(alertStr.equals("")){
			//验证成功保存
			songDao.create(song);
		}else{
			//验证错误写出excel
			song.setAlert_str(alertStr);
			songList.add(song);
		}
	}
	public SongDao getSongDao() {
		return songDao;
	}
	public void setSongDao(SongDao songDao) {
		this.songDao = songDao;
	}
	public OperationLogService getOptLogService() {
		return optLogService;
	}
	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}
	public MsgBean getMsgBean() {
		return msgBean;
	}
	public void setMsgBean(MsgBean msgBean) {
		this.msgBean = msgBean;
	}
	public SingerDao getSingerDao() {
		return singerDao;
	}
	public void setSingerDao(SingerDao singerDao) {
		this.singerDao = singerDao;
	}
	public DictionaryDao getDictDao() {
		return dictDao;
	}
	public void setDictDao(DictionaryDao dictDao) {
		this.dictDao = dictDao;
	}
	/*public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		//ExcelReaderUtil.readExcel(reader, "F://te03.xls");
		long start_row = 0;  
		ExcelReaderService.readExcel("D://歌曲导入模板55000.xlsx",start_row);
		long end = System.currentTimeMillis();
		System.out.println("----------"+(start-end)+"ms--------------");
	}*/
}
