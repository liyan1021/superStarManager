package com.liyan.superstar.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
import com.liyan.superstar.dao.DynamicTitleDao;
import com.liyan.superstar.model.DynamicTitle;
import com.liyan.superstar.model.PassMusic;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.DynamicTitleService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class DynamicTitleServiceImpl implements DynamicTitleService {
	@Autowired
	private DynamicTitleDao  dynamicTitleDao ;
	@Autowired
	private OperationLogService optLogService ; 
	@Autowired
	private MsgBean msgBean ; 
	
	@Override
	public Pagination<DynamicTitle> query(DynamicTitle dynamicTitle,String start_time,String end_time,
			Integer page, Integer rows) {
		return this.dynamicTitleDao.query(dynamicTitle,start_time,end_time,page,rows);
	}
	
	@Override
	public MsgBean create(DynamicTitle dynamicTitle) {
		
		try{
			
			dynamicTitle.setTitle_id(UUID.randomUUID().toString());
			dynamicTitle.setState(CommonFiled.PUSH_STATE_NO);
			dynamicTitle.setUpdate_time(CommonUtils.currentDateTimeString());
			dynamicTitle.setIs_activity(CommonFiled.DEL_STATE_N);
			this.dynamicTitleDao.create(dynamicTitle);
			
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "跑马灯字幕" ; 		
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

	@Override
	public MsgBean editTitle(DynamicTitle dynamicTitle) {
		try{
			
			DynamicTitle findTitle = this.dynamicTitleDao.find(dynamicTitle.getTitle_id());
			findTitle.setTitle_time_length(dynamicTitle.getTitle_time_length());
			findTitle.setTitle_number(dynamicTitle.getTitle_number());
			findTitle.setTitle_date_time(dynamicTitle.getTitle_date_time());
			findTitle.setTitle_content(dynamicTitle.getTitle_content());
		//	findTitle.setState(CommonFiled.PUSH_STATE_NO);
			findTitle.setUpdate_time(CommonUtils.currentDateTimeString());
			findTitle.setRoom_id(dynamicTitle.getRoom_id());
			findTitle.setRoom_num(dynamicTitle.getRoom_num());
			this.dynamicTitleDao.modify(findTitle);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "跑马灯字幕" ; 		
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

	@Override
	public MsgBean removeTitle(String[] idsList) {
		
		try{
			
			List<String> list = Arrays.asList(idsList);
			for(String id : list){
				DynamicTitle find = this.dynamicTitleDao.find(id);
				find.setIs_activity(CommonFiled.DEL_STATE_Y);
				find.setUpdate_time(CommonUtils.currentDateTimeString());
				this.dynamicTitleDao.modify(find);
			}
//			this.dynamicTitleDao.batchRemove(list);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "跑马灯字幕" ; 		
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
	public Pagination<DynamicTitle> search(DynamicTitle dynamicTitle,String start_time,String end_time,
			Integer page, Integer rows) {
		Pagination<DynamicTitle> query = this.dynamicTitleDao.query(dynamicTitle,start_time,end_time,page,rows);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "跑马灯字幕" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH; 
		getOptLogService().setLog(currentUser,function,type);
		
		return query;
	}
	@Override
	public MsgBean pushDynamicTitle(String ids){
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String interface_id : list){
				DynamicTitle find = this.dynamicTitleDao.find(interface_id);
				find.setState(CommonFiled.PUSH_STATE_ALREADY); //设置为已发布
				find.setUpdate_time(CommonUtils.currentDateTimeString());
				this.dynamicTitleDao.modify(find);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_TITLE,ids);
			
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "跑马灯字幕" ; 		
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
		return msgBean ;
	}
	public DynamicTitleDao getDynamicTitleDao() {
		return dynamicTitleDao;
	}

	public void setDynamicTitleDao(DynamicTitleDao dynamicTitleDao) {
		this.dynamicTitleDao = dynamicTitleDao;
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
