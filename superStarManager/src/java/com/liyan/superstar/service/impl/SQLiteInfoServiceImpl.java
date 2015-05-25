package com.liyan.superstar.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyan.common.AppContext;
import com.liyan.common.Commons;
import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.common.util.SocketUtil;
import com.liyan.superstar.dao.RoomDao;
import com.liyan.superstar.dao.SQLiteInfoDao;
import com.liyan.superstar.model.ApkInfo;
import com.liyan.superstar.model.Room;
import com.liyan.superstar.model.SQLiteInfo;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.superstar.service.SQLiteInfoService;
import com.liyan.superstar.service.SQLiteService;
import com.mysql.jdbc.Connection;
@Service
@Transactional
public class SQLiteInfoServiceImpl implements SQLiteInfoService{
	@Autowired
	private SQLiteInfoDao sqliteDao ;
	@Autowired
	private OperationLogService optLogService ;
	@Autowired
	private MsgBean msgBean ; 
	
	@Autowired
	private RoomDao roomDao;
	@Autowired
	private SQLiteService sqliteService ; 
	public SQLiteInfoDao getSqliteDao() {
		return sqliteDao;
	}

	public void setSqliteDao(SQLiteInfoDao sqliteDao) {
		this.sqliteDao = sqliteDao;
	}

	@Override
	public Pagination<SQLiteInfo> query(SQLiteInfo sqliteInfo,
			String start_time, String end_time, Integer page, Integer rows,String sort,String order) {
		return this.sqliteDao.query(sqliteInfo,start_time,end_time,page,rows,sort,order);
	}

	@Override
	public Pagination<SQLiteInfo> searchSqliteInfo(SQLiteInfo sqliteInfo,
			String start_time, String end_time, Integer page, Integer rows) {
		Pagination<SQLiteInfo> query = this.sqliteDao.query(sqliteInfo, start_time, end_time, page, rows,null,null);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "SQLite版本管理" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH; 
		getOptLogService().setLog(currentUser,function,type);
		return query;
	}

	@Override
	public MsgBean removeSqliteInfo(String[] idsList) {
		try{
			
		
			List<String> list = Arrays.asList(idsList);
			for(String id : list){
				SQLiteInfo sqliteInfo = this.sqliteDao.find(id);
				String fileName = sqliteInfo.getSqlite_path();
				if(!CommonUtils.isEmpty(fileName)){
					CommonUtils.deleteFile(new File(fileName.substring(0, fileName.lastIndexOf("\\"))));
				}
				sqliteInfo.setIs_activity(CommonFiled.DEL_STATE_Y);
				this.sqliteDao.modify(sqliteInfo);
			}
//			this.sqliteDao.batchRemove(list);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "SQLite版本管理" ; 		
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
	public MsgBean saveSqliteInfo(SQLiteInfo sqliteInfo, File sqlite_file,
			String fileName, String sqlite_fileContentType) {
		MsgBean msgBean = new MsgBean();
		try{
			if(sqliteInfo!=null){
				sqliteInfo.setSqlite_id(UUID.randomUUID().toString());
				sqliteInfo.setCreate_time(CommonUtils.currentDateTimeString()); 
				//生成文件路径
				String fileType = fileName.substring(fileName.lastIndexOf("."),fileName.length());
				fileName = fileName.substring(0,fileName.lastIndexOf("."));
				String root_path = ServletActionContext.getServletContext().getRealPath("upload");
				String file_path = root_path+"\\SQLite" ;
				file_path = CommonUtils.generateFile(file_path, sqlite_file, fileName+"_"+sqliteInfo.getSqlite_version()+fileType);
				sqliteInfo.setSqlite_path(file_path);
				sqliteInfo.setIs_activity(CommonFiled.DEL_STATE_N);
				sqliteInfo.setState(CommonFiled.DEL_STATE_N);
				this.sqliteDao.create(sqliteInfo);
				//操作日志录入
				HttpSession session = ServletActionContext.getRequest().getSession();
				String key = AppContext.getInstance().getString("key.session.current.user");
			    //操作人
				User currentUser = (User) session.getAttribute(key);
				//操作功能
				String function = "SQLite版本管理" ; 		
			    //操作类型
				String type = CommonFiled.OPT_LOG_IMPORT; 
				getOptLogService().setLog(currentUser,function,type);
				
				msgBean.setSign(true);
				msgBean.setMsg("上传成功");
			}else{
				msgBean.setSign(false);
				msgBean.setMsg("上传失败：APK为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("上传失败：异常");
		}
		return msgBean; 
	}

	@Override
	public MsgBean generateSqlite() {
		try{
			String root_path = ServletActionContext.getServletContext().getRealPath("upload");
			String file_path = root_path+"\\SQLite" ;
			String fileName = "ckt.db" ; 
			HashMap<String, String> hm = sqliteService.generateSql(file_path,fileName);
			SQLiteInfo sqliteInfo = new SQLiteInfo();
			sqliteInfo.setSqlite_id(UUID.randomUUID().toString());
			sqliteInfo.setCreate_time(CommonUtils.currentDateTimeString()); 
			sqliteInfo.setSqlite_version(hm.get("version"));
			sqliteInfo.setState(CommonFiled.PUSH_STATE_NO);
			sqliteInfo.setSqlite_path(hm.get("dbName"));
			sqliteInfo.setSqlite_file_path("upload/SQLite/"+hm.get("version")+"/"+fileName); 
			sqliteInfo.setIs_activity(CommonFiled.DEL_STATE_N);
			sqliteInfo.setState(CommonFiled.DEL_STATE_N);
			this.sqliteDao.create(sqliteInfo);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "SQLite版本管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_IMPORT; 
			getOptLogService().setLog(currentUser,function,type);
			
			msgBean.setSign(true);
			msgBean.setMsg("生成成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("上传失败");
		}
		return msgBean;
	}
	
	@Override
	public MsgBean pushSqlite(String ids) {
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String sqliteid : list){
				SQLiteInfo find = this.sqliteDao.find(sqliteid);
				find.setState(CommonFiled.PUSH_STATE_ALREADY); //设置为已发布
				this.sqliteDao.modify(find);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_SCREEN,ids);
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "SQLite版本管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 
			getOptLogService().setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("发布成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("发布失败");
		}
		return msgBean;
	}
	
	@Override
	public MsgBean pushSqliteReady(String ids,String room_id,String room_no) {
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String sqliteid : list){
				SQLiteInfo find = this.sqliteDao.find(sqliteid);
				find.setState(CommonFiled.PUSH_STATE_READY); //设置为预发布
				find.setRoom_id(room_id);
				find.setRoom_num(room_no);
				this.sqliteDao.modify(find);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_SCREEN,ids);
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "SQLite版本管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 
			getOptLogService().setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("预发布成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("预发布失败");
		}
		return msgBean;
	}

	@Override
	public Pagination<Room> queryRoom(Room room, int page, int rows) {
		// TODO Auto-generated method stub
		return this.roomDao.query(room,page,rows);
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

	public SQLiteService getSqliteService() {
		return sqliteService;
	}

	public void setSqliteService(SQLiteService sqliteService) {
		this.sqliteService = sqliteService;
	}

	public RoomDao getRoomDao() {
		return roomDao;
	}

	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}

	
}
