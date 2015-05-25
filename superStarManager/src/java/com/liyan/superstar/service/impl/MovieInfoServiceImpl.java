package com.liyan.superstar.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

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
import com.liyan.superstar.dao.MovieInfoDao;
import com.liyan.superstar.model.Game;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.MovieInfo;
import com.liyan.superstar.model.Singer;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.MovieInfoService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class MovieInfoServiceImpl  implements MovieInfoService{
	@Autowired
	private MovieInfoDao movieInfoDao; 
	
	@Autowired
	private MsgBean msgBean ;
	
	@Autowired
	private OperationLogService optLogService ;

	public MovieInfoDao getMovieInfoDao() {
		return movieInfoDao;
	}

	public void setMovieInfoDao(MovieInfoDao movieInfoDao) {
		this.movieInfoDao = movieInfoDao;
	}

	public MsgBean getMsgBean() {
		return msgBean;
	}

	public void setMsgBean(MsgBean msgBean) {
		this.msgBean = msgBean;
	}

	public OperationLogService getOptLogService() {
		return optLogService;
	}

	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}


	@Override
	public Pagination<MovieInfo> query(MovieInfo movieInfo, Integer page,
			Integer rows, String sort, String order) {
		return this.movieInfoDao.query(movieInfo, page, rows, sort, order);
	}
	@Override
	public Pagination<MovieInfo> search(MovieInfo movieInfo, Integer page,
			Integer rows, String sort, String order) {
		Pagination<MovieInfo> query = this.movieInfoDao.query(movieInfo, page, rows, sort, order);
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "影视管理" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH; 
		//操作时间
		this.optLogService.setLog(currentUser,function,type);
		return query;
	}

	@Override
	public MsgBean saveMovieInfo(MovieInfo movieInfo, File image,
			String imageFileName) {
		String path ="" ;
		try{
			movieInfo.setMovie_id(UUID.randomUUID().toString());
			movieInfo.setState(CommonFiled.PUSH_STATE_NO);
			movieInfo.setIs_activity(CommonFiled.DEL_STATE_N);
			movieInfo.setUpdate_time(CommonUtils.currentDateTimeString());
			if (image != null) {
				//生成文件路径
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = movieInfo.getMovie_id()+imageFileName ;
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\movie";
				//写入指定目录
				path = CommonUtils.generateFile(subPath,image,imageFileName);
	            
				movieInfo.setAbsolute_path(path); //保存绝对路径
		        movieInfo.setFile_path("upload/movie/"+imageFileName); //存放目录
	        }
			this.movieInfoDao.create(movieInfo);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "影视管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_ADD ; 
			getOptLogService().setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("保存成功");
		}catch(Exception e){
			e.printStackTrace();
			new File(path).delete();
			msgBean.setSign(false);
			msgBean.setMsg("保存失败");
		}
		return msgBean ; 
	}

	@Override
	public MsgBean removeMovieInfo(String ids) {
		try{
			String [] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String id : list){
				MovieInfo movieInfo = this.movieInfoDao.find(id);
				String fileName = movieInfo.getAbsolute_path();
				if(fileName!=null){
					new File(fileName).delete();
				}
				movieInfo.setIs_activity(CommonFiled.DEL_STATE_Y);
				movieInfo.setUpdate_time(CommonUtils.currentDateTimeString());
				this.movieInfoDao.modify(movieInfo);
			}
//			this.movieInfoDao.batchRemove(list);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "影视管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_DEL; 
			//操作时间
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
	public MsgBean pushMovie(String ids) {
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String interface_id : list){
				MovieInfo find = this.movieInfoDao.find(interface_id);
				find.setState("1"); //设置为已发布
				this.movieInfoDao.modify(find);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_MOVIE,ids);
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "影视管理" ; 		
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
	public MsgBean editMovieInfo(MovieInfo movieInfo, File image,
			String imageFileName) {
		String path ="" ;
		try{
			MovieInfo find = this.movieInfoDao.find(movieInfo.getMovie_id());
			find.setMovie_name(movieInfo.getMovie_name());
			find.setMovie_info(movieInfo.getMovie_info());
			find.setRow_num(movieInfo.getRow_num());
			find.setSort(movieInfo.getSort());
			find.setColumn_num(movieInfo.getColumn_num());
			find.setUpdate_time(CommonUtils.currentDateTimeString());
			if (image != null) {
				//生成文件路径
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = movieInfo.getMovie_id()+imageFileName ;
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\movie";
				//写入指定目录
				path = CommonUtils.generateFile(subPath,image,imageFileName);
	            
				find.setAbsolute_path(path); //保存绝对路径
				find.setFile_path("upload/movie/"+imageFileName); //存放目录
	        }
			this.movieInfoDao.modify(find);
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "影视管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 
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
	public MsgBean checkAdvertSort(MovieInfo movieInfo) {
		try{
			MovieInfo find = this.movieInfoDao.find(movieInfo.getMovie_id());
			//修改验证唯一
			if(find!=null){
				//1：先判断是否修改
				if(!find.getSort().equals(movieInfo.getSort())){ 
					//2：验证修改的名称是否存在数据库
					List<MovieInfo> list = this.movieInfoDao.findBySort(movieInfo);
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
				List<MovieInfo> list = this.movieInfoDao.findBySort(movieInfo);
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
