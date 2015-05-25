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
import com.liyan.superstar.dao.IntroduceDao;
import com.liyan.superstar.dao.SongDao;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.Introduce;
import com.liyan.superstar.model.PassMusic;
import com.liyan.superstar.model.Song;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.IntroduceService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class IntroduceServiceImpl implements IntroduceService{

	@Autowired
	private IntroduceDao introduceDao ; 
	@Autowired
	private OperationLogService optLogService ;
	@Autowired
	private SongDao songDao ;
	@Autowired
	private MsgBean msgBean ; 
	@Override
	public Pagination<Introduce> query(Introduce introduce, Integer page,
			Integer rows) {
		return this.introduceDao.query(introduce,page,rows);
	}

	@Override
	public Pagination<Introduce> search(Introduce introduce, Integer page,
			Integer rows) {
		Pagination<Introduce> query = this.introduceDao.query(introduce,page,rows);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "新歌推荐" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH; 
		getOptLogService().setLog(currentUser,function,type);
		return query;
	}

	@Override
	public MsgBean create(Introduce introduce) {
		try{
			
			introduce.setIntroduce_id(UUID.randomUUID().toString());
			introduce.setIntroduce_time(CommonUtils.currentDateTimeString());
			Song song = songDao.find(introduce.getSong().getSong_id());
			introduce.setSong(song);
			introduce.setMusic_name(introduce.getSong().getSong_name());
			introduce.setState(CommonFiled.PUSH_STATE_NO);
			introduce.setIs_activity(CommonFiled.DEL_STATE_N);
			introduce.setUpdate_time(CommonUtils.currentDateTimeString());
			this.introduceDao.create(introduce);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "新歌推荐" ; 		
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
	public MsgBean editIntroduce(Introduce introduce) {
		try{
		
			String introduce_id = introduce.getIntroduce_id();
			Introduce findIntroduce = introduceDao.find(introduce_id);
			Song song = songDao.find(introduce.getSong().getSong_id());
			findIntroduce.setSong(song);
			findIntroduce.setMusic_name(introduce.getMusic_name());
			findIntroduce.setIntroduce_sort(introduce.getIntroduce_sort());
	//		findIntroduce.setState(CommonFiled.PUSH_STATE_NO);
			findIntroduce.setUpdate_time(CommonUtils.currentDateTimeString());
			this.introduceDao.modify(findIntroduce);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
	
			//操作功能
			String function = "新歌推荐" ; 		
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
	public MsgBean removeIntroduce(List<String> list) {
		try{
			
			for(String id : list){
				Introduce find = this.introduceDao.find(id);
				find.setIs_activity(CommonFiled.DEL_STATE_Y);
				find.setUpdate_time(CommonUtils.currentDateTimeString());
				this.introduceDao.modify(find);
			}
//			this.introduceDao.batchRemove(list);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
	
			//操作功能
			String function = "新歌推荐" ; 		
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
	public MsgBean pushIntroduce(String ids) {
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String interface_id : list){
				Introduce find = this.introduceDao.find(interface_id);
				find.setState(CommonFiled.PUSH_STATE_ALREADY); //设置为已发布
				this.introduceDao.modify(find);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_INTRODUCE,ids);
			
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "新歌推荐" ; 		
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
	public MsgBean checkIntroductSort(Introduce introduce) {
		try{
			Introduce find = this.introduceDao.find(introduce.getIntroduce_id());
			//修改验证唯一
			if(find!=null){
				//1：先判断是否修改
				if(!find.getIntroduce_sort().equals(introduce.getIntroduce_sort())){ 
					//2：验证修改的名称是否存在数据库
					List<Introduce> list = this.introduceDao.findBySort(introduce);
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
				List<Introduce> list = this.introduceDao.findBySort(introduce);
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
	public Pagination<Song> queryMusicList(Song song, Integer page,
			Integer rows, String sort, String order) {
		return this.introduceDao.queryMusicList(song,page,rows,sort,order);
		
	}

	public IntroduceDao getIntroduceDao() {
		return introduceDao;
	}

	public void setIntroduceDao(IntroduceDao introduceDao) {
		this.introduceDao = introduceDao;
	}

	public SongDao getSongDao() {
		return songDao;
	}

	public void setSongDao(SongDao songDao) {
		this.songDao = songDao;
	}

	public OperationLogService getOptLogService() {
		return optLogService;
	}

	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}

}
