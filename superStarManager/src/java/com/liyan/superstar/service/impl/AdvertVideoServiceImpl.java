package com.liyan.superstar.service.impl;

import java.io.File;
import java.util.Arrays;
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
import com.liyan.superstar.dao.AdvertVideoDao;
import com.liyan.superstar.model.Advert;
import com.liyan.superstar.model.AdvertVideo;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.AdvertVideoService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class AdvertVideoServiceImpl  implements AdvertVideoService{
	@Autowired
	private AdvertVideoDao advertVideoDao ;
	@Autowired
	private OperationLogService optLogService ;
	@Autowired
	private MsgBean msgBean ; 
	@Override
	public Pagination<AdvertVideo> query(AdvertVideo advertVideo, Integer page,
			Integer rows) {
		return this.advertVideoDao.query(advertVideo,page,rows);
	}

	@Override
	public Pagination<AdvertVideo> search(AdvertVideo advertVideo,
			Integer page, Integer rows) {
		
		Pagination<AdvertVideo> query = this.advertVideoDao.query(advertVideo,page,rows);
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key); 
		//操作功能
		String function = "宣传片发布" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH ; 

		this.optLogService.setLog(currentUser,function,type);
		return query;
	}

	@Override
	public MsgBean create(AdvertVideo advertVideo, File video,
			String videoFileName) {
		try{
			advertVideo.setAdvert_id(UUID.randomUUID().toString());
			advertVideo.setState(CommonFiled.PUSH_STATE_NO);
			advertVideo.setUpdate_time(CommonUtils.currentDateTimeString());
			advertVideo.setIs_activity(CommonFiled.DEL_STATE_N);
			if (video != null) {
				//将ID作为文件名称
				videoFileName = videoFileName.substring(videoFileName.lastIndexOf("."),videoFileName.length());
				videoFileName = advertVideo.getAdvert_id() +videoFileName ;
				//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\video";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,video,videoFileName);
				
	            
				advertVideo.setFile_path(absolute_path);
	            
	        }
			this.advertVideoDao.create(advertVideo);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "宣传片发布" ; 		
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
	public MsgBean editVideo(AdvertVideo advertVideo, File video,
			String videoFileName) {
		try{
			AdvertVideo find = this.advertVideoDao.find(advertVideo.getAdvert_id());
			find.setAdvert_theme(advertVideo.getAdvert_theme());
			find.setAdvert_content(advertVideo.getAdvert_content());
			find.setAdvert_sort(advertVideo.getAdvert_sort());
			find.setOnline_time(advertVideo.getOnline_time());
			find.setOffline_time(advertVideo.getOffline_time());
		//	find.setState(CommonFiled.PUSH_STATE_NO);
			find.setUpdate_time(CommonUtils.currentDateTimeString());
			if (video != null) {
				//将ID作为文件名称
				videoFileName = videoFileName.substring(videoFileName.lastIndexOf("."),videoFileName.length());
				videoFileName = advertVideo.getAdvert_id() +videoFileName ;
				//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\video";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,video,videoFileName);
				
	            
				find.setFile_path(absolute_path);
	            
	        }
			this.advertVideoDao.modify(find);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "宣传片发布" ; 		
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

	@Override
	public MsgBean removeVideo(List<String> list) {
		try{
			
			//删除对应歌星头像
			for(String intefaceId : list){
				AdvertVideo advert = this.advertVideoDao.find(intefaceId);
				String file = advert.getFile_path();
				if(!CommonUtils.isEmpty(file)){
					new File(file).delete();
				}
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
			String function = "宣传片发布" ; 		
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

	@Override
	public MsgBean pushVideo(String ids) {
		try{
			
			String[] split = ids.split(",");
			List<String> list = Arrays.asList(split);
			for(String adverId : list){
				AdvertVideo advert = this.advertVideoDao.find(adverId);
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
			String function = "宣传片发布" ; 		
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
	@Override
	public MsgBean checkAdvertSort(AdvertVideo advertVideo){
		try{
			AdvertVideo find = this.advertVideoDao.find(advertVideo.getAdvert_id());
			//修改验证唯一
			if(find!=null){
				//1：先判断是否修改
				if(!find.getAdvert_sort().equals(advertVideo.getAdvert_sort())){ 
					//2：验证修改的名称是否存在数据库
					List<AdvertVideo> list = this.advertVideoDao.findBySort(advertVideo);
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
				List<AdvertVideo> list = this.advertVideoDao.findBySort(advertVideo);
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
	public AdvertVideoDao getAdvertVideoDao() {
		return advertVideoDao;
	}

	public void setAdvertVideoDao(AdvertVideoDao advertVideoDao) {
		this.advertVideoDao = advertVideoDao;
	}

	public OperationLogService getOptLogService() {
		return optLogService;
	}

	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}
}
