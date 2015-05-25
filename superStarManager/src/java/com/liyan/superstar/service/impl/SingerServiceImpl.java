package com.liyan.superstar.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.liyan.common.AppContext;
import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.common.util.ExcelUtil;
import com.liyan.superstar.dao.DictionaryDao;
import com.liyan.superstar.dao.SingerDao;
import com.liyan.superstar.model.Dictionary;
import com.liyan.superstar.model.Singer;
import com.liyan.superstar.model.Song;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.superstar.service.SingerService;

@Service
@Transactional
public class SingerServiceImpl implements SingerService {
	@Autowired
	private OperationLogService optLogService ;
	@Autowired
	private MsgBean msgBean ; 
	@Autowired
	private SingerDao singerDao;
	@Autowired
	private DictionaryDao dictDao ;
	@Override
	public Pagination<Singer> query(Singer singer, Integer page, Integer rows,String sortName, String sortOrder) {
		
		return this.singerDao.query(singer, page, rows,sortName,sortOrder);
	}

	@Override
	public Pagination<Singer> search(Singer singer, Integer page, Integer rows,String sortName, String sortOrder) {
		Pagination<Singer> query = this.singerDao.query(singer, page, rows,sortName,sortOrder);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "歌星管理" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH ; 
		getOptLogService().setLog(currentUser,function,type);
		
		return query;
	}

	@Override
	public MsgBean removeSinger(List<String> list) {
		try{
			
			//删除对应歌星头像
			for(String singerId : list){
				Singer singer = this.singerDao.find(singerId);
				String path = singer.getStar_head_absolute();
				if(path!=null){
					new File(path).delete();
				}
				singer.setIs_activity(CommonFiled.DEL_STATE_Y);
			}
//			this.singerDao.batchRemove(list);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key); 
			//操作功能
			String function = "歌星管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_DEL; 
			getOptLogService().setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("删除成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("删除失败");
		}
		return msgBean ; 
	}

	@Override
	public MsgBean create(Singer singer, File image, String imageFileName, HttpServletRequest request) {
		String path = "" ; 
		try{
			singer.setStar_id(UUID.randomUUID().toString());
			singer.setIs_activity(CommonFiled.DEL_STATE_N);
			if (image != null) {
				//生成文件路径
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = singer.getStar_id()+imageFileName ;
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload")+"\\star_head";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(rootPath,image,imageFileName);
	            
				singer.setStar_head("upload/star_head/"+imageFileName);
				singer.setStar_head_absolute(absolute_path);
	        }
			//歌星根据名字进行判重。重名歌星，加上“_1”、“_2”。
			String star_name = singer.getStar_name();
			if(!CommonUtils.isEmpty(star_name)){
				List<Singer> singerList = this.singerDao.findLikeByName(star_name);
				if(singerList.size()!=0){
					singer.setStar_name(star_name+"_"+singerList.size());
				}
			}
			singer.setStar_name(singer.getStar_name().trim());
			this.singerDao.create(singer);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "歌星管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_ADD ; 
			getOptLogService().setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("保存成功");
		}catch(Exception e){
			e.printStackTrace();
			new File(path).delete();
			msgBean.setSign(false);
			msgBean.setMsg("保存失败");
		}
		return msgBean ; 
	}

	@Override
	public MsgBean editSinger(Singer singer, File image, String imageFileName,
			HttpServletRequest request) {
		try{
			Singer findSinger = singerDao.find(singer.getStar_id());
			findSinger.setStar_name(singer.getStar_name().trim());
			findSinger.setOther_name(singer.getOther_name());
			findSinger.setSpell_first_letter_abbreviation(singer.getSpell_first_letter_abbreviation());
			findSinger.setStar_type(singer.getStar_type());
			findSinger.setArea(singer.getArea());
//			findSinger.setClick_number(singer.getClick_number());
			findSinger.setRemark(singer.getRemark());

			if (image != null) {
				//生成文件路径
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = singer.getStar_id()+imageFileName ;
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload")+"\\star_head";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(rootPath,image,imageFileName);
	            
	            findSinger.setStar_head("upload/star_head/"+imageFileName);
				findSinger.setStar_head_absolute(absolute_path);
	        }
			singerDao.modify(findSinger);
			
			//操作日志录入
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "歌星管理" ; 		
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
	@SuppressWarnings("deprecation")
	@Override
	public void exportSinger(Singer singer) {
		ArrayList<Singer> result  = this.singerDao.query(singer);
		Map<String, Map<String, String>> dictMap = (Map<String, Map<String, String>>) AppContext.getInstance().get("key.dict");
		try {
			String heads = "歌星名字,别名,歌星字头,歌星类别,歌星归属地,点唱次数,备注";
			String fileName = "歌星管理.XLS" ; 
			HttpServletResponse response = ServletActionContext.getResponse();
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet("歌星管理");
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
			Iterator<Singer> it = result.iterator();
			while (it.hasNext()) {
				HSSFRow rows = sheet.createRow(i);
				Singer t_singer = it.next();
				for (short j = 0; j < headers.length; j++) {
					
					if (headers[j].equals("歌星名字")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_singer.getStar_name());

					} 
					if (headers[j].equals("别名")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_singer.getOther_name());
						
					} 
					if (headers[j].equals("歌星字头")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_singer.getSpell_first_letter_abbreviation());
						
					} 
					if (headers[j].equals("歌星类别")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						String star_type = t_singer.getStar_type();
						if(CommonFiled.SINGER_TYPE_MALE.equals(star_type)){
							cell.setCellValue("男");
						}else if(CommonFiled.SINGER_TYPE_FEMALE.equals(star_type)){
							cell.setCellValue("女");
						}else if(CommonFiled.SINGER_TYPE_TEAM.equals(star_type)){
							cell.setCellValue("组合");
						}else if(CommonFiled.SINGER_TYPE_ALL.equals(star_type)){
							cell.setCellValue("群星");
						}
						
					} 
					if (headers[j].equals("歌星归属地")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
//						Dictionary dict = dictDao.getDictByTypeCode(headers[j],t_singer.getArea());
						String value = dictMap.get("area").get(t_singer.getArea());
						cell.setCellValue(value);
					} 
					if (headers[j].equals("点唱次数")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_singer.getClick_number());
						
					} 
					if (headers[j].equals("备注")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_singer.getRemark());
						
					} 
					
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
			String function = "歌星管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_EXPORT ; 
			getOptLogService().setLog(currentUser,function,type);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Override
	public MsgBean importSinger(File importFile) {
			String alertStr = "";
			HSSFWorkbook workbook;
			
			Map<String, Map<String, String>> dictMap = (Map<String, Map<String, String>>) AppContext.getInstance().get("key.dict");
			try {
				workbook = new HSSFWorkbook(new FileInputStream(importFile));
			
	    		HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
//	    		ArrayList<Singer> list = new ArrayList<Singer>();
	    		for(int i = 2 ; i <= sheet.getLastRowNum() ; i++){
	    			HSSFRow row = sheet.getRow(i);
	    			if (row == null) {
	    				msgBean.setSign(false);
	    				msgBean.setMsg("上传失败：文件为空");
						return msgBean ;
					}
	    			Singer singer = new Singer();
	    			singer.setStar_id(UUID.randomUUID().toString());
	    			singer.setIs_activity(CommonFiled.DEL_STATE_N);
	    			//歌星名字 验证
	    			if(CommonUtils.isEmpty(ExcelUtil.getCellFormatValue(row.getCell(1)).trim())){
	    				msgBean.setSign(false);
	    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：歌星名字不能为空");
						return msgBean ;
	    			}
	    			List<Singer> singerList = this.singerDao.findByName(ExcelUtil.getCellFormatValue(row.getCell(1)).trim());
	    			if(singerList.size()>0){
	    				msgBean.setSign(false);
	    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：歌星名字已经存在");
						return msgBean ;
	    			}
	    			if(ExcelUtil.getCellFormatValue(row.getCell(1)).trim().length()>64){
	    				msgBean.setSign(false);
	    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：歌星名字长度不能超过64");
						return msgBean ;
	    			}
	    			String star_name = ExcelUtil.getCellFormatValue(row.getCell(1)).trim(); //歌星名字
	    			singer.setStar_name(star_name.trim());
	    			//别名验证
	    			/*if(CommonUtils.isEmpty(ExcelUtil.getCellFormatValue(row.getCell(2)).trim())){
	    				msgBean.setSign(false);
	    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：别名不能为空");
						return msgBean ;
	    			}*/
	    			if(ExcelUtil.getCellFormatValue(row.getCell(2)).trim().length()>20){
	    				msgBean.setSign(false);
	    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：别名长度不能超过20");
						return msgBean ;
	    			}
	    			singer.setOther_name( ExcelUtil.getCellFormatValue(row.getCell(2)).trim());//别名
	    			//拼音字头验证
	    			if(ExcelUtil.getCellFormatValue(row.getCell(3)).trim().matches("/^([a-zA-Z]+)$/")){
	    				msgBean.setSign(false);
	    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：拼音字头只能输入字母(字符A-Z, a-z)");
						return msgBean ;
	    			}
/*	    			if(CommonUtils.isEmpty(ExcelUtil.getCellFormatValue(row.getCell(3)).trim())){
	    				msgBean.setSign(false);
	    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：拼音字头不能为空");
						return msgBean ;
	    			}*/
	    			if(ExcelUtil.getCellFormatValue(row.getCell(3)).trim().length()>20){
	    				msgBean.setSign(false);
	    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：拼音字头长度不能超过20");
						return msgBean ;
	    			}
	    			singer.setSpell_first_letter_abbreviation( ExcelUtil.getCellFormatValue(row.getCell(3)).trim());//歌星拼音字头
	    			
	    			//歌星类别验证
	    			String star_type = ExcelUtil.getCellFormatValue(row.getCell(4)).trim();
	    			if(star_type.equals("男")){
	    				star_type = "1" ;
	    			}else if(star_type.equals("女")){
	    				star_type = "2" ;
	    			}else if(star_type.equals("组合")){
	    				star_type = "3" ;
	    			}else if(star_type.equals("群星")){
	    				star_type = "4" ;
	    			}else if(CommonUtils.isEmpty(star_type)){
	    				msgBean.setSign(false);
	    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：歌星类别不能为空");
						return msgBean ;
	    			}else{
	    				msgBean.setSign(false);
	    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：歌星类别不存在");
						return msgBean ;
	    			}
	    			singer.setStar_type(star_type);//歌星类别
	    			
	    			//归属地验证
	    			if(CommonUtils.isEmpty(ExcelUtil.getCellFormatValue(row.getCell(5)).trim())){
	    				msgBean.setSign(false);
	    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：归属地不能为空");
						return msgBean ;
	    			}
	    			Dictionary dict = this.dictDao.getDictByTypeName("area",ExcelUtil.getCellFormatValue(row.getCell(5)).trim());
	    			
	    			if(dict==null){
	    				msgBean.setSign(false);
	    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：归属地不存在");
						return msgBean ;
	    			}
	    			singer.setArea(dict.getDict_code()); //归属地
//	    			list.add(singer);
	    			this.singerDao.create(singer);
	    		}
//	    		this.singerDao.batchCreate(list);
	    		msgBean.setSign(true);
				msgBean.setMsg("上传成功:<br>"+alertStr);
			
			} catch (FileNotFoundException e) {
				msgBean.setSign(false);
				msgBean.setMsg("上传失败:文件未找到");
				e.printStackTrace();
				//手动回滚事务
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			} catch (Exception e) {
				msgBean.setSign(false);
				msgBean.setMsg("上传失败:文件错误");
				//手动回滚事务
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			} 
		return msgBean;
	}

	@Override
	public MsgBean checkSingerName(Singer singer) {
		try{
			Singer findSinger = this.singerDao.find(singer.getStar_id());
			//修改验证唯一
			if(findSinger!=null){
				//1：先判断是否修改
				if(!singer.getStar_name().equals(findSinger.getStar_name())){ 
					//2：验证修改的名称是否存在数据库
					List<Singer> list = this.singerDao.findByName(singer.getStar_name());
					if(list.size()!=0){
						msgBean.setSign(true);
					}else{
						msgBean.setSign(false);
					}
				}else{
					msgBean.setSign(false);
				}
				
			}else{
				//新增验证是否存在数据库
				List<Singer> list = this.singerDao.findByName(singer.getStar_name());
				if(list.size()!=0){
					msgBean.setSign(true);
				}else{
					msgBean.setSign(false);
				}
				return msgBean ; 
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return msgBean;
	}

	public OperationLogService getOptLogService() {
		return optLogService;
	}

	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}

	public SingerDao getSingerDao() {
		return singerDao;
	}

	public void setSingerDao(SingerDao singerDao) {
		this.singerDao = singerDao;
	}

	public MsgBean getMsgBean() {
		return msgBean;
	}

	public void setMsgBean(MsgBean msgBean) {
		this.msgBean = msgBean;
	}

	public static void main(String args[]){
		System.out.println(!"SH".matches("/^([a-zA-Z]+)$/"));
	}

	public DictionaryDao getDictDao() {
		return dictDao;
	}

	public void setDictDao(DictionaryDao dictDao) {
		this.dictDao = dictDao;
	}
}
