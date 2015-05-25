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

import com.liyan.superstar.dao.OperationDao;
import com.liyan.superstar.model.Operation;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.common.AppContext;
import com.liyan.common.action.PageAwareActionSupport;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonFiled;
import com.opensymphony.xwork2.Action;
@Controller
@Scope("prototype")
public class OperationAction extends PageAwareActionSupport<Operation> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -846635776491353783L;
	private Operation opt; 
	private String start_time ;
	private String end_time ; 
	private String heads ; 
	@Autowired
	private OperationDao optDao ;
	@Autowired
	private OperationLogService optLogService ;
	
	/**
	 * 操作日志初始化页面
	 * @return
	 */
	public String init(){
		return Action.SUCCESS;
	}
	
	/**
	 * 操作日志初始化列表
	 */
	public void operationList(){
		try{
		
			Pagination<Operation> result = optDao.query(getOpt(),start_time, end_time, page,rows);
			List<Operation> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Operation operation : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("op_log_id", operation.getOp_log_id());
				jsonObject.element("user_id", operation.getUser_id());
				jsonObject.element("user_name",operation.getUser_name());
				jsonObject.element("op_origin", operation.getOp_origin());
				jsonObject.element("op_func_id", operation.getOp_func_id());
				jsonObject.element("op_func", operation.getOp_func());
				jsonObject.element("op_type", operation.getOp_type());
				jsonObject.element("op_time", operation.getOp_time());
				jsonObject.element("store_code", operation.getStore_code());
				jsonObject.element("room_code", operation.getRoom_code());
				
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
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 查询操作日志
	 */
	public void searchOptLogList(){
		try{
			
			Pagination<Operation> result = optDao.query(getOpt(),start_time, end_time, page,rows);
			List<Operation> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Operation operation : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("op_log_id", operation.getOp_log_id());
				jsonObject.element("user_id", operation.getUser_id());
				jsonObject.element("user_name",operation.getUser_name());
				jsonObject.element("op_origin", operation.getOp_origin());
				jsonObject.element("op_func_id", operation.getOp_func_id());
				jsonObject.element("op_func", operation.getOp_func());
				jsonObject.element("op_type", operation.getOp_type());
				jsonObject.element("op_time", operation.getOp_time());
				jsonObject.element("store_code", operation.getStore_code());
				jsonObject.element("room_code", operation.getRoom_code());
				
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
				//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "操作日志" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_SEARCH ; 
			getOptLogService().setLog(currentUser,function,type);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 导出操作日志
	 */
	@SuppressWarnings("deprecation")
	public void exportLog(){
		ArrayList<Operation> result = optDao.query(getOpt(), start_time,
				end_time);
		try {
			heads = "操作人,操作来源,操作功能,操作类型,操作时间,门店,包房";
			String fileName = "操作日志.xls" ; 
			HttpServletResponse response = ServletActionContext.getResponse();
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet("操作日志");
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
			Iterator<Operation> it = result.iterator();
			while (it.hasNext()) {
				HSSFRow rows = sheet.createRow(i);
				Operation opt = it.next();
				for (short j = 0; j < headers.length; j++) {
					if (headers[j].equals("操作人")) {
						
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(opt.getUser_name());
						
					}else if(headers[j].equals("操作来源")){
						
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(opt.getOp_origin());
						
					}else if(headers[j].equals("操作功能")){
						
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(opt.getOp_func());
						
					}else if (headers[j].equals("操作类型")) {
						
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						String type = opt.getOp_type();
						if(type.equals("1")){
							type = "增加" ; 
						}else if(type.equals("2")){
							type = "删除" ;  
						}else if(type.equals("3")){
							type = "修改" ; 
						}else if(type.equals("4")){
							type = "查询" ; 
						}else if(type.equals("5")){
							type = "登录" ; 
						}else if(type.equals("6")){
							type = "导出" ; 
						}else if(type.equals("7")){
							type = "导入" ; 
						}
						cell.setCellValue(type);

					} else if (headers[j].equals("操作时间")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(opt.getOp_time());

					} else if (headers[j].equals("门店")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(opt.getStore_code());
					}else if (headers[j].equals("包房")) {
						HSSFCell cell = rows.createCell(j);
						cell.setCellStyle(style2);
						cell.setCellValue(opt.getRoom_code());
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
			String function = "系统日志" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_EXPORT ; 
			getOptLogService().setLog(currentUser,function,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Operation getOpt() {
		return opt;
	}
	public void setOpt(Operation opt) {
		this.opt = opt;
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

	public String getHeads() {
		return heads;
	}

	public void setHeads(String heads) {
		this.heads = heads;
	}

	public OperationDao getOptDao() {
		return optDao;
	}

	public void setOptDao(OperationDao optDao) {
		this.optDao = optDao;
	}

	public OperationLogService getOptLogService() {
		return optLogService;
	}

	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}


	
}
