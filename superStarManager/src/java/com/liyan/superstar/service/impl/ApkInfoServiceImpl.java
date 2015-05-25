package com.liyan.superstar.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
import com.liyan.superstar.dao.ApkInfoDao;
import com.liyan.superstar.dao.RoomDao;
import com.liyan.superstar.model.ApkInfo;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.Room;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.ApkInfoService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class ApkInfoServiceImpl implements ApkInfoService{
	@Autowired
	private ApkInfoDao apkInfoDao ;
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private MsgBean msgBean ; 
	
	@Autowired
	private OperationLogService optLogService ;
	
	
	@Override
	public Pagination<ApkInfo> query(ApkInfo apkInfo,String start_time,String end_time, Integer page, Integer rows,String sort,String order) {
		
		return this.apkInfoDao.query(apkInfo,start_time,end_time,page,rows,sort,order);
	}

	@Override
	public Pagination<ApkInfo> searchApkInfo(ApkInfo apkInfo, String start_time,String end_time,Integer page,
			Integer rows) {
		
		Pagination<ApkInfo> query = this.apkInfoDao.query(apkInfo, start_time, end_time, page, rows,null,null);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "apk版本管理" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH; 

		this.optLogService.setLog(currentUser,function,type);
		return query;
	}

	@Override
	public MsgBean removeApkInfo(String[] idsList) {
		try{
		
			List<String> list = Arrays.asList(idsList);
			for(String id : list){
				ApkInfo apkInfo = this.apkInfoDao.find(id);
				String fileName = apkInfo.getApk_path();
				if(!CommonUtils.isEmpty(fileName)){
					CommonUtils.deleteFile(new File(fileName.substring(0, fileName.lastIndexOf("\\"))));
				}
				//new File(fileName).delete();
				apkInfo.setIs_activity(CommonFiled.DEL_STATE_Y);
				this.apkInfoDao.modify(apkInfo);
			}
//			this.apkInfoDao.batchRemove(list);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "apk版本管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_DEL; 
			//操作时间
			this.optLogService.setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("删除成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(true);
			msgBean.setMsg("删除失败");
		}
		return msgBean ;
	}

	@Override
	public MsgBean saveApkInfo(ApkInfo apkInfo, File apk_file, String fileName,
			String contentType) {
		try{
			if(apkInfo!=null){
				apkInfo.setApk_id(UUID.randomUUID().toString());
				apkInfo.setCreate_time(CommonUtils.currentDateTimeString()); 
				String version =getVersion();
				apkInfo.setApk_version(version);
				apkInfo.setApk_name(fileName);
				//生成文件路径
				String fileType = fileName.substring(fileName.lastIndexOf("."),fileName.length());
				fileName = fileName.substring(0,fileName.lastIndexOf("."));
				String root_path = ServletActionContext.getServletContext().getRealPath("upload");
				String file_path = root_path+"\\apk"+"\\"+version;
				file_path = CommonUtils.generateFile(file_path, apk_file, fileName+"_"+apkInfo.getApk_version()+fileType);
				apkInfo.setApk_path(file_path);
				apkInfo.setApk_file_path("upload/apk/"+version+"/"+fileName+"_"+apkInfo.getApk_version()+fileType); 
				apkInfo.setIs_activity(CommonFiled.DEL_STATE_N);
				apkInfo.setState(CommonFiled.PUSH_STATE_NO);
				this.apkInfoDao.create(apkInfo);
				//操作日志录入
				HttpSession session = ServletActionContext.getRequest().getSession();
				String key = AppContext.getInstance().getString("key.session.current.user");
			    //操作人
				User currentUser = (User) session.getAttribute(key);
				//操作功能
				String function = "apk版本管理" ; 		
			    //操作类型
				String type = CommonFiled.OPT_LOG_IMPORT; 

				this.optLogService.setLog(currentUser,function,type);
				
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
	public MsgBean checkApkVersionSort(ApkInfo apkInfo) {
		//新增验证是否存在数据库
		List<ApkInfo> list = this.apkInfoDao.findBySort(apkInfo);
		if(list.size()!=0){
			msgBean.setSign(true);
		}else{
			msgBean.setSign(false);
		}
		return msgBean ; 
	}
    @Override
	public MsgBean pushApk(String ids) {
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String apkid : list){
				ApkInfo find = this.apkInfoDao.find(apkid);
				find.setState(CommonFiled.PUSH_STATE_ALREADY); //设置为已发布
				this.apkInfoDao.modify(find);
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
			String function = "apk版本管理" ; 		
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
	public MsgBean pushApkReady(String ids,String room_id,String room_no) {
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String apkid : list){
				ApkInfo find = this.apkInfoDao.find(apkid);
				find.setState(CommonFiled.PUSH_STATE_READY); //设置为预发布
				find.setRoom_id(room_id);
				find.setRoom_num(room_no);
				this.apkInfoDao.modify(find);
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
			String function = "apk版本管理" ; 		
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
	
	//根据导入时间生成版本号
	public String getVersion() {
		String str = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new java.util.Date());
		return str;
	}
	@Override
	public Pagination<Room> queryRoom(Room room, int page, int rows) {
		// TODO Auto-generated method stub
		return this.roomDao.query(room,page,rows);
	} 
	public ApkInfoDao getApkInfoDao() {
		return apkInfoDao;
	}

	public void setApkInfoDao(ApkInfoDao apkInfoDao) {
		this.apkInfoDao = apkInfoDao;
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

	public RoomDao getRoomDao() {
		return roomDao;
	}

	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}	
}
