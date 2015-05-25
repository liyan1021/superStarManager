package com.liyan.superstar.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.transaction.annotation.Transactional;

import com.liyan.common.AppContext;
import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.dao.SupportSongDao;
import com.liyan.superstar.model.SupportSong;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.superstar.service.SupportSongService;

@Service
@Transactional
public class SupportSongServiceImpl implements SupportSongService {

	@Autowired
	private SupportSongDao supportDao ; 
	@Autowired
	private OperationLogService optLogService ; 	
	@Autowired
	private MsgBean msgBean ; 
	@Override
	public Pagination<SupportSong> query(SupportSong support,
			String start_time, String end_time, Integer page, Integer rows,String sortName,String sortOrder) {
		
		return supportDao.query(support,start_time,end_time,page,rows,sortName,sortOrder);
		
	}

	/**
	 * 逻辑删除补歌清单 
	 * */
	@Override
	public MsgBean removeSupport(String[] idsList) {
		try{
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
			//操作人
			User currentUser = (User) session.getAttribute(key);
		
			List<String> list = Arrays.asList(idsList);
			for(String id :list){
				SupportSong find = this.supportDao.find(id);
				find.setIs_activity(CommonFiled.DEL_STATE_Y);
				find.setUpdate_time(CommonUtils.currentDateTimeString());
				find.setHandler_person(currentUser.getUser_name());  //设置操作人
				find.setHandler_time(CommonUtils.currentDateTimeString()); //设置操作时间
				this.supportDao.modify(find);
			}
			
		
		    
			//操作功能
			String function = "补歌清单" ; 		
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
	public Pagination<SupportSong> search(SupportSong support,
			String start_time, String end_time, Integer page, Integer rows,String sortName, String sortOrder) {
		
		Pagination<SupportSong> query = supportDao.query(support,start_time,end_time,page,rows, sortName,sortOrder);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "补歌清单" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH; 
		getOptLogService().setLog(currentUser,function,type);
		return query ;
	}

	@Override
	public MsgBean updateSupport(String[] idsList, String sign) {
		try{
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			
			List<String> list = Arrays.asList(idsList);
			ArrayList<SupportSong> supportList = new ArrayList<SupportSong>();
			for(String support_id : list){
				SupportSong song = this.supportDao.find(support_id);
				song.setSong_state(sign);
				song.setHandler_person(currentUser.getUser_name());
				song.setHandler_time(CommonUtils.currentDateTimeString());
				supportList.add(song);
			}
			this.supportDao.batchModify(supportList);
			
			//操作功能
			String function = "补歌清单" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY; 
	
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
	public void exportSupport(SupportSong support, String start_time,
			String end_time) {
		ArrayList<SupportSong> result  = this.supportDao.query(support,start_time,end_time);
		try {
			String heads = "歌曲名称,歌曲年代,歌星名称,补歌状态,提交时间";
			String fileName = "补歌清单.xls" ; 
			HttpServletResponse response = ServletActionContext.getResponse();
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet("补歌清单");
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
			Iterator<SupportSong> it = result.iterator();
			while (it.hasNext()) {
				HSSFRow rows = sheet.createRow(i);
				SupportSong t_song = it.next();
				for (short j = 0; j < headers.length; j++) {
					
					if (headers[j].equals("歌曲名称")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSong_name());

					} else if (headers[j].equals("歌曲年代")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						String type = t_song.getSong_year();
						cell.setCellValue(type);

					} else if (headers[j].equals("歌星名称")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getStar_name());

					} else if (headers[j].equals("补歌状态")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						String song_state = t_song.getSong_state();
						if (song_state.equals("0")){
							song_state  = "未处理";
						} else if(song_state.equals("1")) {
							song_state  = "已补歌曲";
						}else if(song_state.equals("2")) {
							song_state  = "忽略";
						}
						cell.setCellValue(song_state);
					}else if (headers[j].equals("提交时间")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getSupport_time());
					}else if (headers[j].equals("歌曲版本")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getVersions());
					}
					else if (headers[j].equals("语种")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(t_song.getLanguages());
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
	public MsgBean supportSong(SupportSong support) {
		
		try{
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			support.setSupport_id(UUID.randomUUID().toString());
			support.setSupport_time(CommonUtils.currentDateTimeString());
			support.setUpdate_time(CommonUtils.currentDateTimeString());
			support.setIs_activity(CommonFiled.DEL_STATE_N);
			support.setHandler_time(CommonUtils.currentDateTimeString());
			support.setHandler_person(currentUser.getUser_name());
			support.setSong_state("0");
			this.supportDao.create(support);
			
			//操作功能
			String function = "补歌清单" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_ADD; 
	
			getOptLogService().setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("保存成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("保存失败");
		}
		return msgBean ; 
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
	
	
}
