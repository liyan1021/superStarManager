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
import com.liyan.superstar.dao.MainStarDao;
import com.liyan.superstar.dao.SingerDao;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.MainStar;
import com.liyan.superstar.model.PassMusic;
import com.liyan.superstar.model.Singer;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.MainStarService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class MainStarServiceImpl implements MainStarService {
	
	@Autowired
	private MainStarDao  mainStarDao ;
	@Autowired
	private SingerDao singerDao ; 
	@Autowired
	private OperationLogService optLogService ;
	@Autowired
	private MsgBean msgBean ; 
	public MainStarDao getMainStarDao() {
		return mainStarDao;
	}

	public void setMainStarDao(MainStarDao mainStarDao) {
		this.mainStarDao = mainStarDao;
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
	public Pagination<MainStar> queryMainStar(MainStar mainStar, Integer page,
			Integer rows) {
		return this.mainStarDao.query(mainStar,page,rows);
	}
	
	@Override
	public Pagination<Singer> unMainStarList(Singer singer, Integer page,
			Integer rows) {
		return this.mainStarDao.queryUnMainStar(singer, page, rows);
	}

	
	@Override
	public MsgBean setMainStar(MainStar mainStar) {
		try{
			
				mainStar.setIntroduce_id(UUID.randomUUID().toString());
				Singer singer = this.singerDao.find(mainStar.getSinger().getStar_id());
				mainStar.setSinger(singer);  //关联字段
				mainStar.setSinger_name(singer.getStar_name());
				mainStar.setState(CommonFiled.PUSH_STATE_NO); //设置未发布
				mainStar.setIntroduce_time(CommonUtils.currentDateTimeString()); //设置推荐日期
				mainStar.setIntroduce_type(CommonFiled.MAINSTAR_MAIN); //设置为主打歌星
				mainStar.setIs_activity(CommonFiled.DEL_STATE_N);
				mainStar.setUpdate_time(CommonUtils.currentDateTimeString());
				mainStarDao.create(mainStar);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "主打歌星" ; 		
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
	public MsgBean removeMainStar(String ids) {
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String id : list){
				MainStar find = this.mainStarDao.find(id);
				find.setIs_activity(CommonFiled.DEL_STATE_Y);
				find.setUpdate_time(CommonUtils.currentDateTimeString());
				this.mainStarDao.modify(find);
			}
//			this.mainStarDao.batchRemove(list);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "主打歌星" ; 		
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
	public Pagination<MainStar> searchMainStarList(MainStar mainStar, Integer page,
			Integer rows) {
		
		Pagination<MainStar> query = this.mainStarDao.query(mainStar,page,rows);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "主打歌星" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH; 
		getOptLogService().setLog(currentUser,function,type);
		
		return query ;
	}

	@Override
	public MsgBean pushMainStar(String ids) {
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String interface_id : list){
				MainStar find = this.mainStarDao.find(interface_id);
				find.setState(CommonFiled.PUSH_STATE_ALREADY); //设置为已发布
				this.mainStarDao.modify(find);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_MAINSTAR,ids);
			
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "主打歌星" ; 		
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
	public Pagination<MainStar> queryNewStar(MainStar mainStar, Integer page,
			Integer rows) {
		return this.mainStarDao.queryNewStar(mainStar, page, rows);
	}

	@Override
	public Pagination<MainStar> searchNewStar(MainStar mainStar, Integer page,
			Integer rows) {
		Pagination<MainStar> query = this.mainStarDao.queryNewStar(mainStar, page, rows);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "新星推荐" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH; 
		getOptLogService().setLog(currentUser,function,type);
		
		return query;
	}

	@Override
	public MsgBean pushNewStar(String ids) {
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String interface_id : list){
				MainStar find = this.mainStarDao.find(interface_id);
				find.setState(CommonFiled.PUSH_STATE_ALREADY); //设置为已发布
				this.mainStarDao.modify(find);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_NEWSTAR,ids);
			
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "新星推荐" ; 		
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
	public MsgBean removeNewStar(String ids) {
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			this.mainStarDao.batchRemove(list);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "新星推荐" ; 		
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
	public MsgBean setNewStar(String ids) {
		try{
			
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String star_id : list){
				MainStar mainStar = new MainStar();
				mainStar.setIntroduce_id(UUID.randomUUID().toString());
				Singer singer = this.singerDao.find(star_id);
				mainStar.setSinger(singer);  //关联字段
				mainStar.setSinger_name(singer.getStar_name());
				mainStar.setState(CommonFiled.PUSH_STATE_NO); //设置未发布
				mainStar.setIntroduce_time(CommonUtils.currentDateTimeString()); //设置推荐日期
				mainStar.setIntroduce_type(CommonFiled.MAINSTAR_NEW); //设置为新星
				mainStarDao.create(mainStar);
			}
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "新星推荐" ; 		
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
	public Pagination<Singer> unNewStarList(Singer singer, Integer page,
			Integer rows) {
		return this.mainStarDao.queryUnNewStar(singer, page, rows);
	}

	@Override
	public MsgBean checkMainStarSort(MainStar mainStar) {
		try{
			MainStar find = this.mainStarDao.find(mainStar.getIntroduce_id());
			//修改验证唯一
			if(find!=null){
				//1：先判断是否修改
				if(!find.getIntroduce_sort().equals(mainStar.getIntroduce_sort())){ 
					//2：验证修改的名称是否存在数据库
					List<MainStar> list = this.mainStarDao.findBySort(mainStar);
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
				List<MainStar> list = this.mainStarDao.findBySort(mainStar);
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

	public SingerDao getSingerDao() {
		return singerDao;
	}

	public void setSingerDao(SingerDao singerDao) {
		this.singerDao = singerDao;
	}

}
