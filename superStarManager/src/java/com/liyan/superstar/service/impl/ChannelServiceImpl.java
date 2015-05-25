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
import com.liyan.superstar.dao.ChannelDao;
import com.liyan.superstar.model.Channel;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.ChannelService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class ChannelServiceImpl implements ChannelService{
	@Autowired
	private ChannelDao channelDao ;

	@Autowired
	private OperationLogService optLogService;
	@Autowired
	private MsgBean msgBean ; 
	
	public ChannelDao getChannelDao() {
		return channelDao;
	}

	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
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


	@Override
	public Pagination<Channel> query(Channel channel, Integer page,
			Integer rows, String sort, String order) {
		
		return this.channelDao.query(channel,page,rows,sort,order);
	}

	@Override
	public MsgBean saveChannel(Channel channel, File image, String imageFileName) {
	
		try{
			
			channel.setChannel_id(UUID.randomUUID().toString());
			channel.setState(CommonFiled.PUSH_STATE_NO); //初始状态为未发布
			channel.setIs_activity(CommonFiled.DEL_STATE_N);
			channel.setUpdate_time(CommonUtils.currentDateTimeString());
			HttpServletRequest request = ServletActionContext.getRequest();
			if (image != null) {
	//			generateFile(path,image,imageFileName);
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = channel.getChannel_id() +imageFileName ;
				//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\channel";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
	            
				channel.setFile_path("upload/channel/"+imageFileName);
				channel.setAbsolute_path(absolute_path); //保存绝对路径
	            
	        }
			
			this.channelDao.create(channel);
			
			//操作日志录入
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "视频转播设置" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_ADD ; 

			getOptLogService().setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("保存成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("保存失败");
		}
		return msgBean;
	}
	
	@Override
	public MsgBean removeChannel(List<String> list) {
		try{
			
			for(String channelId : list){
				Channel channel = this.channelDao.find(channelId);
				String file = channel.getAbsolute_path();
				if(!CommonUtils.isEmpty(file)){
					new File(file).delete();
				}
				channel.setIs_activity(CommonFiled.DEL_STATE_Y);
				channel.setUpdate_time(CommonUtils.currentDateTimeString());
				this.channelDao.modify(channel);
			}
//			this.channelDao.batchRemove(list);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
	  
			//操作功能
			String function = "视频转播设置" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_DEL ; 
	
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
	public Pagination<Channel> searchChannel(Channel channel, Integer page,
			Integer rows, String sort, String order) {
		Pagination<Channel> query = this.channelDao.query(channel,page,rows,sort,order);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "视频转播设置" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH ; 
 
		getOptLogService().setLog(currentUser,function,type);
		
		return query;
	}

	@Override
	public MsgBean editChannel(Channel channel, File image, String imageFileName) {
		try{
			
			HttpServletRequest request = ServletActionContext.getRequest();
			Channel find = this.channelDao.find(channel.getChannel_id());
			find.setChannel_name(channel.getChannel_name());
			find.setChannel_url(channel.getChannel_url());
			find.setChannel_content(channel.getChannel_content());
			find.setSort(channel.getSort());
		//	find.setState(CommonFiled.PUSH_STATE_NO);
			find.setUpdate_time(CommonUtils.currentDateTimeString());
			if (image != null) {
	//			generateFile(path,image,imageFileName);
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = find.getChannel_id() +imageFileName ;
	        	//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\interface";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
			
				find.setAbsolute_path(absolute_path); //保存绝对路径
				find.setFile_path("upload/interface/"+imageFileName); //存放目录
	            
	        }
			this.channelDao.modify(find);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "视频转播设置" ; 		
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
		return msgBean;
	}

	@Override
	public MsgBean pushChannel(String ids) {
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String channelId : list){
				Channel find = this.channelDao.find(channelId);
				find.setState("1"); //设置为已发布
				this.channelDao.modify(find);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_CHANNEL,ids);
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "视频转播设置" ; 		
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
	public MsgBean checkChannelSort(Channel channel) {
		try{
			Channel find = this.channelDao.find(channel.getChannel_id());
			//修改验证唯一
			if(find!=null){
				//1：先判断是否修改
				if(!find.getSort().equals(channel.getSort())){ 
					//2：验证修改的名称是否存在数据库
					List<Channel> list = this.channelDao.findBySort(channel);
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
				List<Channel> list = this.channelDao.findBySort(channel);
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
	
}
