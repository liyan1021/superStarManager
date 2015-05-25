package com.liyan.superstar.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.liyan.superstar.dao.SysLogDao;
import com.liyan.superstar.model.SysLog;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.common.AppContext;
import com.liyan.common.action.PageAwareActionSupport;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonFiled;
import com.opensymphony.xwork2.Action;
/**
 * @author Administrator
 *设备日志
 */
@Controller
@Scope("prototype")
public class SysLogAction extends PageAwareActionSupport<SysLog> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -349933952361847832L;
	@Autowired
	private SysLogDao sysLogDao ; 
	@Autowired
	private OperationLogService optLogService ;
	private SysLog sysLog;
	private String start_time ;
	private String end_time ; 
	private String heads ;
	private boolean log_sign ;  
	public String init() {

		return Action.SUCCESS;

	}
	
	/**
	 *   设备日志初始化列表分页显示
	 */
	public void sysLogList(){
		try{
			
			Pagination<SysLog> result = sysLogDao.query(getSysLog(),start_time,end_time,page,rows);
			List<SysLog> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(SysLog syslog : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("sys_log_id", syslog.getSys_log_id());
				jsonObject.element("device_id", syslog.getDevice_id());
				jsonObject.element("device_name",syslog.getDevice_name());
				jsonObject.element("run_state", syslog.getRun_state());
				jsonObject.element("rec_time", syslog.getRec_time().toString());
				jsonObject.element("store_code", syslog.getStore_code());
				jsonObject.element("room_code", syslog.getRoom_code());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
			if(log_sign){
				//操作日志录入
				HttpSession session = ServletActionContext.getRequest().getSession();
				String key = AppContext.getInstance().getString("key.session.current.user");
			    //操作人
				User currentUser = (User) session.getAttribute(key);
				//操作功能
				String function = "系统日志" ; 		
			    //操作类型
				String type = CommonFiled.OPT_LOG_SEARCH ; 
				getOptLogService().setLog(currentUser,function,type);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	/**
	 * 导出设备日志
	 */
	@SuppressWarnings("deprecation")
	public void exportLog(){
		
		ArrayList<SysLog> result  = sysLogDao.query(this.sysLog,start_time,end_time);
		try {
			heads = "设备名称,运行状态,记录时间,门店,包房";
			String fileName = "系统日志" ; 
			HttpServletResponse response = ServletActionContext.getResponse();
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet("系统日志");
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
			Iterator<SysLog> it = result.iterator();
			while (it.hasNext()) {
				HSSFRow rows = sheet.createRow(i);
				SysLog syslog = it.next();
				for (short j = 0; j < headers.length; j++) {
					if (headers[j].equals("设备名称")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(syslog.getDevice_name());

					} else if (headers[j].equals("运行状态")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						String type = syslog.getRun_state();
						cell.setCellValue(type);

					} else if (headers[j].equals("记录时间")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(syslog.getRec_time().toString());

					} else if (headers[j].equals("门店")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(syslog.getStore_code());
					}else if (headers[j].equals("包房")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(syslog.getRoom_code());
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
			if(log_sign){
				//操作日志录入
				HttpSession session = ServletActionContext.getRequest().getSession();
				String key = AppContext.getInstance().getString("key.session.current.user");
			    //操作人
				User currentUser = (User) session.getAttribute(key);
				//操作功能
				String function = "系统日志" ; 		
			    //操作类型
				String type = CommonFiled.OPT_LOG_EXPORT ; 
				getOptLogService().setLog(currentUser,function,type);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public SysLog getSysLog() {
		return sysLog;
	}

	public void setSysLog(SysLog sysLog) {
		this.sysLog = sysLog;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public boolean getLog_sign() {
		return log_sign;
	}

	public void setLog_sign(boolean log_sign) {
		this.log_sign = log_sign;
	}

	public SysLogDao getSysLogDao() {
		return sysLogDao;
	}

	public void setSysLogDao(SysLogDao sysLogDao) {
		this.sysLogDao = sysLogDao;
	}

	public OperationLogService getOptLogService() {
		return optLogService;
	}

	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}

	
}
