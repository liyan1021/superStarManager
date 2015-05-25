package com.liyan.superstar.service.impl;

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
import com.liyan.superstar.dao.PassMusicDao;
import com.liyan.superstar.dao.SingerDao;
import com.liyan.superstar.dao.SongDao;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.PassMusic;
import com.liyan.superstar.model.Song;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.superstar.service.PassMusicService;

@Service
@Transactional
public class PassMusicServiceImpl implements PassMusicService {

	@Autowired
	private PassMusicDao passMusicDao ; 
	@Autowired
	private OperationLogService optLogService ;
	@Autowired
	private MsgBean msgBean ; 
	@Autowired
	private SongDao songDao ;
	@Autowired
	private SingerDao singerDao ;
	
	
	public OperationLogService getOptLogService() {
		return optLogService;
	}
	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}
	public PassMusicDao getPassMusicDao() {
		return passMusicDao;
	}
	public void setPassMusicDao(PassMusicDao passMusicDao) {
		this.passMusicDao = passMusicDao;
	}
	@Override
	public Pagination<PassMusic> query(PassMusic passMusic, Integer page,
			Integer rows) {
		return this.passMusicDao.query(passMusic,page,rows);
	}
	@Override
	public Pagination<PassMusic> search(PassMusic passMusic, Integer page,
			Integer rows) {
		Pagination<PassMusic> query = this.passMusicDao.query(passMusic,page,rows);
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "过路歌曲" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH ; 
		getOptLogService().setLog(currentUser,function,type);
		
		return query;
	}
	@Override
	public MsgBean create(PassMusic passMusic) {
		try{
			
		
			passMusic.setPass_id(UUID.randomUUID().toString());
			passMusic.setCreate_time(new java.sql.Date(new Date().getTime()));
			passMusic.setState(CommonFiled.PUSH_STATE_NO);
			passMusic.setUpdate_time(CommonUtils.currentDateTimeString());
			passMusic.setIs_activity(CommonFiled.DEL_STATE_N);
			this.passMusicDao.create(passMusic);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "过路歌曲" ; 		
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
		return msgBean ; 
		
	}
	@Override
	public MsgBean removePassMusic(List<String> list) {
		try{
			
			for(String id : list){
				PassMusic find = this.passMusicDao.find(id);
				find.setIs_activity(CommonFiled.DEL_STATE_Y);
				find.setUpdate_time(CommonUtils.currentDateTimeString());
				this.passMusicDao.modify(find);
			}
//			this.passMusicDao.batchRemove(list);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "过路歌曲" ; 		
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
	public MsgBean editPassMusic(PassMusic passMusic) {
		try{
			
		
			PassMusic findPassMusic = this.passMusicDao.find(passMusic.getPass_id());
			findPassMusic.setSong_id(passMusic.getSong_id());
			findPassMusic.setSong_name(passMusic.getSong_name());
			findPassMusic.setPass_sort(passMusic.getPass_sort());
			findPassMusic.setSinger_id(passMusic.getSinger_id());
			findPassMusic.setSinger_name(passMusic.getSinger_name());
	//		findPassMusic.setState(CommonFiled.PUSH_STATE_NO);
			findPassMusic.setUpdate_time(CommonUtils.currentDateTimeString());
			this.passMusicDao.modify(findPassMusic);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "过路歌曲" ; 		
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
	public MsgBean pushPassMusic(String ids){
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String interface_id : list){
				PassMusic find = this.passMusicDao.find(interface_id);
				find.setState(CommonFiled.PUSH_STATE_ALREADY); //设置为已发布
				find.setUpdate_time(CommonUtils.currentDateTimeString());
				this.passMusicDao.modify(find);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_PASS,ids);
			
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "过路歌曲" ; 		
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
	
	
	@Override
	public MsgBean checkPassSongSort(PassMusic passMusic) {
		try{
			PassMusic find = this.passMusicDao.find(passMusic.getPass_id());
			//修改验证唯一
			if(find!=null){
				//1：先判断是否修改
				if(!find.getPass_sort().equals(passMusic.getPass_sort())){ 
					//2：验证修改的名称是否存在数据库
					List<PassMusic> list = this.passMusicDao.findBySort(passMusic);
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
				List<PassMusic> list = this.passMusicDao.findBySort(passMusic);
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
	public MsgBean getMsgBean() {
		return msgBean;
	}
	public void setMsgBean(MsgBean msgBean) {
		this.msgBean = msgBean;
	}
	
	
	
}
