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
import com.liyan.superstar.dao.AdvertDao;
import com.liyan.superstar.dao.AdvertMusicDao;
import com.liyan.superstar.dao.SongDao;
import com.liyan.superstar.model.Advert;
import com.liyan.superstar.model.AdvertMusic;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.AdvertService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class AdvertServiceImpl implements AdvertService{
	@Autowired
	private AdvertDao advertDao ; 
	@Autowired
	private OperationLogService optLogService ;  //日志记录
	
	@Autowired
	private AdvertMusicDao advertMusicDao ; 
	
	@Autowired
	private SongDao songDao ;
	@Autowired
	private MsgBean msgBean ; 
	
	public AdvertDao getAdvertDao() {
		return advertDao;
	}

	public void setAdvertDao(AdvertDao advertDao) {
		this.advertDao = advertDao;
	}

	@Override
	public Pagination<Advert> query(Advert advert, Integer page, Integer rows,String sort, String order) {
		
		return this.advertDao.query(advert,page,rows,sort,order);
	}

	@Override
	public Pagination<Advert> search(Advert advert, Integer page, Integer rows,String sort, String order) {
		
		Pagination<Advert> query = this.advertDao.query(advert, page, rows,sort,order);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key); 
		//操作功能
		String function = "广告发布" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH ; 

		this.optLogService.setLog(currentUser,function,type);
		return query;
	}

	@Override
	public MsgBean create(Advert advert, File image, String imageFileName,
			String imageContentType) {
		MsgBean msgBean = new MsgBean();
		try{
			
			advert.setAdvert_id(UUID.randomUUID().toString());
			advert.setState("0");//TODO 设置发布状态为 未发布
			advert.setUpdate_time(CommonUtils.currentDateTimeString());
			advert.setIs_activity(CommonFiled.DEL_STATE_N);
			if (image != null) {
				//将ID作为文件名称
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = advert.getAdvert_id() +imageFileName ;
				//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\advert";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
				
	            
	            advert.setAbsolute_path(absolute_path);
	            advert.setFile_path("upload/advert/"+imageFileName);
	            
	        }
			
			this.advertDao.create(advert);
			
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "广告发布" ; 		
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
	public MsgBean editAdvert(Advert advert, File image, String imageFileName,
			String imageContentType) {
		MsgBean msgBean = new MsgBean();
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			Advert findAdvert = this.advertDao.find(advert.getAdvert_id());
			findAdvert.setAdvert_name(advert.getAdvert_name());
			findAdvert.setAdvert_content(advert.getAdvert_content());
			findAdvert.setOnline_time(advert.getOnline_time());
			findAdvert.setOffline_time(advert.getOffline_time());
			findAdvert.setAdvert_time(advert.getAdvert_time());
			findAdvert.setAdvert_sort(advert.getAdvert_sort());
			findAdvert.setIs_index_advert(advert.getIs_index_advert());
			findAdvert.setUpdate_time(CommonUtils.currentDateTimeString());
			if (image != null) {
				//将ID作为文件名称
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = advert.getAdvert_id() +imageFileName ;
	        	//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\advert" ;
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
				 
				findAdvert.setAbsolute_path(absolute_path);
				findAdvert.setFile_path("upload/advert/"+imageFileName);
	            
	        }
			this.advertDao.modify(findAdvert);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "广告发布" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 

			this.optLogService.setLog(currentUser,function,type);
			
			msgBean.setSign(true);
			msgBean.setMsg("编辑成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(true);
			msgBean.setMsg("编辑失败");
		}
		return msgBean ; 
	}

	@Override
	public MsgBean removeAdvert(List<String> list) {
		
		MsgBean msgBean = new MsgBean();
		
		try{
		
			//删除对应歌星头像
			for(String adverId : list){
				Advert advert = this.advertDao.find(adverId);
				String fileName = advert.getAbsolute_path();
				if(!CommonUtils.isEmpty(fileName)){
					new File(fileName).delete();
				}
				advert.setIs_activity(CommonFiled.DEL_STATE_Y);
				advert.setUpdate_time(CommonUtils.currentDateTimeString());
				this.advertDao.modify(advert);
			}
//			this.advertDao.batchRemove(list);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "广告发布" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_DEL ; 

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
	public MsgBean pushAdvert(String ids) {
		try{
			String[] split = ids.split(",");
			List<String> list = Arrays.asList(split);
			for(String adverId : list){
				Advert advert = this.advertDao.find(adverId);
				advert.setState(CommonFiled.PUSH_STATE_ALREADY); //已发布
				advert.setUpdate_time(CommonUtils.currentDateTimeString());
				this.advertDao.modify(advert);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_ADS, ids);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "广告发布" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 

			this.optLogService.setLog(currentUser,function,type);
			getMsgBean().setSign(true);
			getMsgBean().setMsg("发布成功");
		}catch(Exception e){
			e.printStackTrace();
			getMsgBean().setSign(true);
			getMsgBean().setMsg("发布失败");
		}
		return getMsgBean() ; 
	}

	@Override
	public Pagination<AdvertMusic> searchAdvertSong(Advert advert,
			Integer page, Integer rows) {
		Pagination<AdvertMusic> result = this.advertMusicDao.queryAdvertSong(advert,page,rows);
		
		return result; 
	}
	
	@Override
	public MsgBean removeAdvertMusic(String advert_music_id) {
		
		try{
			AdvertMusic advertMusic = this.advertMusicDao.find(advert_music_id);
			Advert find = this.advertDao.find(advertMusic.getAdvert_id());
			find.setUpdate_time(CommonUtils.currentDateTimeString());
			this.advertDao.modify(find);
			this.advertMusicDao.remove(advertMusic);
			getMsgBean().setSign(true);
			getMsgBean().setMsg("删除成功");
		}catch(Exception e){
			e.printStackTrace();
			getMsgBean().setSign(true);
			getMsgBean().setMsg("删除失败");
		}
		return getMsgBean() ; 
	}
	
	@Override
	public MsgBean saveAdvertMusic(String ids,Advert advert) {
		try{
			String[] split = ids.split(",");
			List<String> list = Arrays.asList(split);
			for(String songId : list){
				AdvertMusic advertMusic = new AdvertMusic();
				advertMusic.setAdvert_music_id(UUID.randomUUID().toString());
				advertMusic.setAdvert_id(advert.getAdvert_id());
				advertMusic.setSong(songDao.find(songId));
				this.advertMusicDao.create(advertMusic);
			}
			getMsgBean().setSign(true);
			getMsgBean().setMsg("保存成功");
		}catch(Exception e){
			e.printStackTrace();
			getMsgBean().setSign(true);
			getMsgBean().setMsg("保存失败");
		}
		return getMsgBean() ; 
	}

	@Override
	public MsgBean checkAdvertSort(Advert advert) {
		try{
			Advert findAdvert = this.advertDao.find(advert.getAdvert_id());
			//修改验证唯一
			if(findAdvert!=null){
				//1：先判断是否修改
				if(!findAdvert.getAdvert_sort().equals(advert.getAdvert_sort())){ 
					//2：验证修改的名称是否存在数据库
					List<Advert> list = this.advertDao.findBySort(advert);
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
				List<Advert> list = this.advertDao.findBySort(advert);
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

	public AdvertMusicDao getAdvertMusicDao() {
		return advertMusicDao;
	}

	public void setAdvertMusicDao(AdvertMusicDao advertMusicDao) {
		this.advertMusicDao = advertMusicDao;
	}

	public SongDao getSongDao() {
		return songDao;
	}

	public void setSongDao(SongDao songDao) {
		this.songDao = songDao;
	}

}
