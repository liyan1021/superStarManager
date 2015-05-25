package com.liyan.superstar.service.impl;

import java.io.File;
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
import com.liyan.superstar.dao.GameDao;
import com.liyan.superstar.model.ApkInfo;
import com.liyan.superstar.model.Game;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.GameService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class GameServiceImpl implements GameService{

	@Autowired
	private GameDao gameDao ; 
	
	@Autowired
	private OperationLogService optLogService ; 
	
	@Autowired
	private MsgBean msgBean ; 
	@Override
	public Pagination<Game> query(Game game,String start_time,String end_time, Integer page,  Integer rows,String sort,String order) {
		return this.gameDao.query(game,start_time,end_time,page,rows,sort,order);
	}

	@Override
	public MsgBean removeGame(String ids) {
		try{
			String [] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String id : list){
				Game game = this.gameDao.find(id);
				/*String fileName = game.getFile_path();
				new File(fileName).delete();*/
				game.setIs_activity(CommonFiled.DEL_STATE_Y);
				game.setUpdate_time(CommonUtils.currentDateTimeString());
				this.gameDao.modify(game);
			}
//			this.gameDao.batchRemove(list);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "游戏管理" ; 		
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
	public MsgBean importGame(File game_file,String game_fileFileName) {
		try{
			if(game_file!=null){
				Game game = new Game();
				game.setGame_id(UUID.randomUUID().toString());
				game.setImport_time(CommonUtils.currentDateTimeString());
				game.setGame_name(game_fileFileName);
				game.setState(CommonFiled.PUSH_STATE_NO);
				game.setIs_activity(CommonFiled.DEL_STATE_N);
				game.setUpdate_time(CommonUtils.currentDateTimeString());
				//生成文件路径
				game_fileFileName = game_fileFileName.substring(game_fileFileName.lastIndexOf("."),game_fileFileName.length());
				game_fileFileName = game.getGame_id()+game_fileFileName ;
				String root_path = ServletActionContext.getServletContext().getRealPath("upload");
				String file_path = root_path+"\\game" ;
				file_path = CommonUtils.generateFile(file_path, game_file, game_fileFileName);
//				game.setFile_path(file_path);
				this.gameDao.create(game);
				//操作日志录入
				HttpSession session = ServletActionContext.getRequest().getSession();
				String key = AppContext.getInstance().getString("key.session.current.user");
			    //操作人
				User currentUser = (User) session.getAttribute(key);
				//操作功能
				String function = "游戏管理" ; 		
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
	public MsgBean editGame(Game game) {
		try{
			Game find = this.gameDao.find(game.getGame_id());
			find.setGame_name(game.getGame_name());
			find.setGame_uri(game.getGame_uri());
			find.setState(CommonFiled.PUSH_STATE_NO);
			find.setIs_activity(CommonFiled.DEL_STATE_N);
			find.setUpdate_time(CommonUtils.currentDateTimeString());
			this.gameDao.modify(find);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "游戏管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY; 

			this.optLogService.setLog(currentUser,function,type);
			
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
	public MsgBean saveGame(Game game){
		try{
				game.setGame_id(UUID.randomUUID().toString());
				game.setImport_time(CommonUtils.currentDateTimeString());
				game.setState(CommonFiled.PUSH_STATE_NO);
				game.setIs_activity(CommonFiled.DEL_STATE_N);
				game.setUpdate_time(CommonUtils.currentDateTimeString());
				
				this.gameDao.create(game);
				//操作日志录入
				HttpSession session = ServletActionContext.getRequest().getSession();
				String key = AppContext.getInstance().getString("key.session.current.user");
			    //操作人
				User currentUser = (User) session.getAttribute(key);
				//操作功能
				String function = "游戏管理" ; 		
			    //操作类型
				String type = CommonFiled.OPT_LOG_IMPORT; 

				this.optLogService.setLog(currentUser,function,type);
				
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
	public Pagination<Game> search(Game game,String start_time,String end_time, Integer page, Integer rows,
			String sort, String order) {
		Pagination<Game> query = this.gameDao.query(game,start_time,end_time, page, rows, sort, order);
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "游戏管理" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH; 
		//操作时间
		this.optLogService.setLog(currentUser,function,type);
		return query;
	}

	@Override
	public MsgBean pushGame(String ids) {
		
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String interface_id : list){
				Game game = this.gameDao.find(interface_id);
				game.setState(CommonFiled.PUSH_STATE_ALREADY); //设置为已发布
				game.setUpdate_time(CommonUtils.currentDateTimeString());  //修改更新时间
				this.gameDao.modify(game);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_GAME,ids);
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "游戏管理" ; 		
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

	public GameDao getGameDao() {
		return gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		this.gameDao = gameDao;
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
