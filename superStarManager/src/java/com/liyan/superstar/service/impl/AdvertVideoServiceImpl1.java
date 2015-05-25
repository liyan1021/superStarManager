package com.liyan.superstar.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
import com.liyan.superstar.dao.AdvertVideoDao1;
import com.liyan.superstar.model.AdvertVideo1;
import com.liyan.superstar.model.Song;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.AdvertVedioService1;
import com.liyan.superstar.service.OperationLogService;
@Service
@Transactional
public class AdvertVideoServiceImpl1 implements AdvertVedioService1 {
    @Autowired
	private AdvertVideoDao1 advertVideoDao;
    @Autowired
    private OperationLogService optLogService ;
    @Autowired
    private MsgBean msgBean ;

	
	public Pagination<AdvertVideo1> query(AdvertVideo1 advertVideo,
			Integer page, Integer rows) {
		// TODO Auto-generated method stub
		return this.advertVideoDao.query(advertVideo,page,rows);
	}
	
	@Override
	public Pagination<AdvertVideo1> search(AdvertVideo1 advertVideo,
			Integer page, Integer rows) {
		
		Pagination<AdvertVideo1> query = this.advertVideoDao.query(advertVideo,page,rows);
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key); 
		//操作功能
		String function = "宣传片管理" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH ; 

		this.optLogService.setLog(currentUser,function,type);
		return query;
	}
	
	public MsgBean create(AdvertVideo1 advertVideo) {
		try{
			advertVideo.setAdvert_id(UUID.randomUUID().toString());
			advertVideo.setState(CommonFiled.PUSH_STATE_NO);
			advertVideo.setUpdate_time(CommonUtils.currentDateTimeString());
			advertVideo.setIs_activity(CommonFiled.DEL_STATE_N);        
			this.advertVideoDao.create(advertVideo);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "宣传片管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_ADD ; 

			this.optLogService.setLog(currentUser,function,type);
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
	public MsgBean editVideo(AdvertVideo1 advertVideo) {
		try{
			AdvertVideo1 find = this.advertVideoDao.find(advertVideo.getAdvert_id());
			find.setAdvert_theme(advertVideo.getAdvert_theme());
			find.setAdvert_content(advertVideo.getAdvert_content());
			find.setAdvert_sort(advertVideo.getAdvert_sort());
			find.setMusic_name(advertVideo.getMusic_name());
			find.setMusic_id(advertVideo.getMusic_id());
			find.setOnline_time(advertVideo.getOnline_time());
			find.setOffline_time(advertVideo.getOffline_time());
//			find.setState(CommonFiled.PUSH_STATE_NO);
			find.setUpdate_time(CommonUtils.currentDateTimeString());	            
			this.advertVideoDao.modify(find);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "宣传片管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 

			this.optLogService.setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("编辑成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("编辑失败");
		}
		return msgBean ;
	}


	public MsgBean removeAdvertVideo(List<String> list) {
		// TODO Auto-generated method stub
    try{
			
			//删除对应歌星
			for(String advertId : list){
				AdvertVideo1 advert = this.advertVideoDao.find(advertId);
				advert.setIs_activity(CommonFiled.DEL_STATE_Y);
				advert.setUpdate_time(CommonUtils.currentDateTimeString());
			}
//			this.advertVideoDao.batchRemove(list);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "宣传片管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_DEL ; 

			this.optLogService.setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("删除成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("删除失败");
		}
		return msgBean ;
	}
	

	public MsgBean pushAdvertVideo(String ids) {
		// TODO Auto-generated method stub
      try{
			
			String[] split = ids.split(",");
			List<String> list = Arrays.asList(split);
			for(String adverId : list){
				AdvertVideo1 advert = this.advertVideoDao.find(adverId);
				advert.setState(CommonFiled.PUSH_STATE_ALREADY); //已发布
				advert.setUpdate_time(CommonUtils.currentDateTimeString());
				this.advertVideoDao.modify(advert);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_VIDEO, ids);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "宣传片管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 

			this.optLogService.setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("发布成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("发布失败");
		}
		return msgBean ;
	}
	
	public MsgBean checkAdvertSort(AdvertVideo1 advertVideo) {
		// TODO Auto-generated method stub
		try{
			AdvertVideo1 find = this.advertVideoDao.find(advertVideo.getAdvert_id());
			//修改验证唯一
			if(find!=null){
				//1：先判断是否修改
				if(!find.getAdvert_sort().equals(advertVideo.getAdvert_sort())){ 
					//2：验证修改的名称是否存在数据库
					List<AdvertVideo1> list = this.advertVideoDao.findBySort(advertVideo);
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
				List<AdvertVideo1> list = this.advertVideoDao.findBySort(advertVideo);
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
	
	@Override
	public Pagination<Song> unAdvertVideoList(Song song, Integer page,
			Integer rows) {
		// TODO Auto-generated method stub
		return this.advertVideoDao.unAdvertVideoList(song, page, rows);
	}
	
	
	public AdvertVideoDao1 getAdvertVideoDao1() {
		return advertVideoDao;
	}
	public void setAdvertVideoDao1(AdvertVideoDao1 advertVideoDao) {
		this.advertVideoDao = advertVideoDao;
	}
	public OperationLogService getOptLogService1() {
		return optLogService;
	}
	public void setOptLogService1(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}
}
