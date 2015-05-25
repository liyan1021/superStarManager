package com.liyan.superstar.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
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
import com.liyan.common.util.Param;
import com.liyan.common.util.SocketUtil;
import com.liyan.superstar.dao.InterfaceDao;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.StoreRequest;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.InterfaceService;
import com.liyan.superstar.service.OperationLogService;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import org.apache.log4j.Logger;

@Service
@Transactional
public class InterfaceServiceImpl implements InterfaceService{
	@Autowired
	private InterfaceDao interfaceDao ;
	@Autowired
	private OperationLogService optLogService ;
	@Autowired
	private MsgBean msgBean ; 
	private Logger logger = Logger.getLogger(InterfaceServiceImpl.class);
	@Override
	public Pagination<Interface> query(Interface itf,Integer page, Integer rows) {
		
		return this.interfaceDao.query(itf,page,rows);
	}

	@Override
	public Pagination<Interface> search(Interface itf, Integer page,
			Integer rows) {
		
		Pagination<Interface> query = this.interfaceDao.query(itf,page,rows);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);

		//操作功能
		String function = "初始界面设置" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH ; 

		getOptLogService().setLog(currentUser,function,type);
		
		return query;
	}

	@Override
	public MsgBean removeInterface(List<String> list) {
		try{
			
			//删除对应歌星头像
			for(String intefaceId : list){
				Interface itf = this.interfaceDao.find(intefaceId);
				String file = itf.getAbsolute_path();
				if(!CommonUtils.isEmpty(file)){
					new File(file).delete();
				}
				itf.setIs_activity(CommonFiled.DEL_STATE_Y);
				itf.setUpdate_time(CommonUtils.currentDateTimeString());
				//modify 改为逻辑删除
				this.interfaceDao.modify(itf);
			}
			//this.interfaceDao.batchRemove(list);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
	  
			//操作功能
			String function = "初始界面设置" ; 		
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
	public MsgBean editInterface(Interface itf,File image, String imageFileName, String imageContentType) {
		try{
		
			HttpServletRequest request = ServletActionContext.getRequest();
			Interface findItf = this.interfaceDao.find(itf.getInterface_id());
			findItf.setInterface_name(itf.getInterface_name());
			findItf.setOnline_time(itf.getOnline_time());
			findItf.setOffline_time(itf.getOffline_time());
//			findItf.setIs_activity(itf.getIs_activity());
			findItf.setInterface_time(itf.getInterface_time());
			findItf.setInterface_sort(itf.getInterface_sort());
			findItf.setUpdate_time(CommonUtils.currentDateTimeString());
			if (image != null) {
	//			generateFile(path,image,imageFileName);
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = itf.getInterface_id() +imageFileName ;
	        	//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\interface";
				//删除原有图片
				String file = itf.getAbsolute_path();
				if(!CommonUtils.isEmpty(file)){
					new File(file).delete();
				}
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
			
	            findItf.setAbsolute_path(absolute_path); //保存绝对路径
	            findItf.setFile_path("upload/interface/"+imageFileName); //存放目录
	            
	        }
			this.interfaceDao.modify(findItf);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "初始界面设置" ; 		
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
	public MsgBean editPlaceInterface(Interface itf,File image, String imageFileName, String imageContentType) {
		try{
		
			HttpServletRequest request = ServletActionContext.getRequest();
			Interface findItf = this.interfaceDao.find(itf.getInterface_id());
			findItf.setInterface_name(itf.getInterface_name());
			findItf.setUpdate_time(CommonUtils.currentDateTimeString());
			if (image != null) {
	//			generateFile(path,image,imageFileName);
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = itf.getInterface_id() +imageFileName ;
	        	//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\interface";
				//删除原有图片
				String file = itf.getAbsolute_path();
				if(!CommonUtils.isEmpty(file)){
					new File(file).delete();
				}
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
			
	            findItf.setAbsolute_path(absolute_path); //保存绝对路径
	            findItf.setFile_path("upload/interface/"+imageFileName); //存放目录
	            
	        }
			this.interfaceDao.modify(findItf);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "场所介绍界面设置" ; 		
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
	public MsgBean editFirePicInterface(Interface itf,File image, String imageFileName, String imageContentType) {
		try{
		
			HttpServletRequest request = ServletActionContext.getRequest();
			Interface findItf = this.interfaceDao.find(itf.getInterface_id());
			findItf.setInterface_name(itf.getInterface_name());
			findItf.setUpdate_time(CommonUtils.currentDateTimeString());
			if (image != null) {
	//			generateFile(path,image,imageFileName);
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = itf.getInterface_id() +imageFileName ;
	        	//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\interface";
				//删除原有图片
				String file = itf.getAbsolute_path();
				if(!CommonUtils.isEmpty(file)){
					new File(file).delete();
				}
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
			
	            findItf.setAbsolute_path(absolute_path); //保存绝对路径
	            findItf.setFile_path("upload/interface/"+imageFileName); //存放目录
	            
	        }
			this.interfaceDao.modify(findItf);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "消防图解界面设置" ; 		
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
	public MsgBean create(Interface itf,File image, String imageFileName, String imageContentType) {
		try{
			
			itf.setInterface_id(UUID.randomUUID().toString());
			itf.setInterface_type("1");
			itf.setState(CommonFiled.PUSH_STATE_NO); //初始状态为未发布
			itf.setUpdate_time(CommonUtils.currentDateTimeString());
			itf.setIs_activity(CommonFiled.DEL_STATE_N);
			HttpServletRequest request = ServletActionContext.getRequest();
			if (image != null) {
	//			generateFile(path,image,imageFileName);
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = itf.getInterface_id() +imageFileName ;
				//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\interface";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
	            
	            itf.setFile_path("upload/interface/"+imageFileName);
	            itf.setAbsolute_path(absolute_path); //保存绝对路径
	            
	        }
			
			
			
			this.interfaceDao.create(itf);
			
			//操作日志录入
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "初始界面设置" ; 		
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
	public Pagination<Interface> querySlepp(Interface itf, Integer page,
			Integer rows) {
		return this.interfaceDao.querySleep(itf,page,rows);
	}
	public List<Interface>queryPlace(Interface itf) {
		  return this.interfaceDao.queryPlace(itf);
	}
	public List<Interface>queryFirePic(Interface itf) {
		  return this.interfaceDao.queryFirePic(itf);
	}
	
	
	//查询屏保界面设置
	@Override
	public Pagination<Interface> searchSlepp(Interface itf, Integer page,
			Integer rows) {
		Pagination<Interface> query = this.interfaceDao.querySleep(itf,page,rows);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "屏保界面设置" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH ; 
 
		getOptLogService().setLog(currentUser,function,type);
		
		return query;
	}
	
	
	//删除屏保界面
	@Override
	public MsgBean removeSleppInterface(List<String> list) {
		try{
		
			//删除对应歌星头像
			for(String intefaceId : list){
				Interface itf = this.interfaceDao.find(intefaceId);
				String fileName = itf.getFile_path();
				if(!CommonUtils.isEmpty(fileName)){
					fileName = fileName.substring(fileName.lastIndexOf("/"),fileName.length());
					String path = ServletActionContext.getServletContext().getRealPath("upload");
					path = path +"\\interface\\"+ fileName ;
					new File(path).delete();
				}
				//modify 改为逻辑删除
				itf.setIs_activity(CommonFiled.DEL_STATE_Y);
				itf.setUpdate_time(CommonUtils.currentDateTimeString());
				this.interfaceDao.modify(itf);
			}
//			this.interfaceDao.batchRemove(list);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "屏保界面设置" ; 		
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
	//编辑屏保界面
	@Override
	public MsgBean editSleppInterface(Interface itf, File image,
			String imageFileName, String imageContentType) {
		try{
			
			HttpServletRequest request = ServletActionContext.getRequest();
			Interface findItf = this.interfaceDao.find(itf.getInterface_id());
			findItf.setInterface_name(itf.getInterface_name());
			findItf.setOnline_time(itf.getOnline_time());
			findItf.setOffline_time(itf.getOffline_time());
//			findItf.setIs_activity(itf.getIs_activity());
			findItf.setInterface_time(itf.getInterface_time());
			findItf.setInterface_sort(itf.getInterface_sort());
			findItf.setUpdate_time(CommonUtils.currentDateTimeString());
			if (image != null) {
	//			generateFile(path,image,imageFileName);
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = itf.getInterface_id() +imageFileName ;
	        	//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\interface";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
				
	           
	            findItf.setAbsolute_path(absolute_path);
	            findItf.setFile_path("upload/interface/"+imageFileName);
	            
	        }
			this.interfaceDao.modify(findItf);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "屏保界面设置" ; 		
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

	//保存屏保界面
	@Override
	public MsgBean createSlepp(Interface itf, File image, String imageFileName,
			String imageContentType) {
		try{			
			itf.setInterface_id(UUID.randomUUID().toString());
			itf.setInterface_type("2");
			itf.setState(CommonFiled.PUSH_STATE_NO); //初始状态为未发布
			itf.setUpdate_time(CommonUtils.currentDateTimeString());
			itf.setIs_activity(CommonFiled.DEL_STATE_N);
			HttpServletRequest request = ServletActionContext.getRequest();
			if (image != null) {
	//			generateFile(path,image,imageFileName);
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = itf.getInterface_id() +imageFileName ;
				//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\interface";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
	         
	            itf.setAbsolute_path(absolute_path);
	            itf.setFile_path("upload/interface/"+imageFileName);
	        }
			
			
			
			this.interfaceDao.create(itf);
			
			//操作日志录入
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "屏保界面设置" ; 		
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
	
	//保存屏保界面
	@Override
	public MsgBean createSleepList(Interface itf, File image, String imageFileName) {
		try{			
			itf.setInterface_id(UUID.randomUUID().toString());
			itf.setInterface_type("2");
			itf.setState(CommonFiled.PUSH_STATE_NO); //初始状态为未发布
			itf.setUpdate_time(CommonUtils.currentDateTimeString());
			itf.setIs_activity(CommonFiled.DEL_STATE_N);
			HttpServletRequest request = ServletActionContext.getRequest();
			if (image != null) {
	//			generateFile(path,image,imageFileName);
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = itf.getInterface_id() +imageFileName ;
				//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\interface";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
	         
	            itf.setAbsolute_path(absolute_path);
	            itf.setFile_path("upload/interface/"+imageFileName);
	        }
			
			
			
			this.interfaceDao.create(itf);
			this.interfaceDao.flushAndClear();
			
			//操作日志录入
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "屏保界面设置" ; 		
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
	public MsgBean createPlace(Interface itf, File image, String imageFileName,
			String imageContentType) {
		try{
			itf.setInterface_id(UUID.randomUUID().toString());
			itf.setInterface_type("3");
			itf.setState(CommonFiled.PUSH_STATE_NO); //初始状态为未发布
			itf.setUpdate_time(CommonUtils.currentDateTimeString());
			itf.setIs_activity(CommonFiled.DEL_STATE_N);
			HttpServletRequest request = ServletActionContext.getRequest();			
			if (image != null) {
	//			generateFile(path,image,imageFileName);
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = itf.getInterface_id() +imageFileName ;
				//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\interface";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
	         
	            itf.setAbsolute_path(absolute_path);
	            itf.setFile_path("upload/interface/"+imageFileName);
	        }			
			this.interfaceDao.create(itf);
			
			//操作日志录入
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "场所介绍界面设置" ; 		
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
	public MsgBean createFirePic(Interface itf, File image, String imageFileName,
			String imageContentType) {
		try{
			
			
			itf.setInterface_id(UUID.randomUUID().toString());
			itf.setInterface_type("4");
			itf.setState(CommonFiled.PUSH_STATE_NO); //初始状态为未发布
			itf.setUpdate_time(CommonUtils.currentDateTimeString());
			itf.setIs_activity(CommonFiled.DEL_STATE_N);
			HttpServletRequest request = ServletActionContext.getRequest();
			if (image != null) {
	//			generateFile(path,image,imageFileName);
				imageFileName = imageFileName.substring(imageFileName.lastIndexOf("."),imageFileName.length());
				imageFileName = itf.getInterface_id() +imageFileName ;
				//生成文件路径
				String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
				String subPath = rootPath + "\\interface";
				//写入指定目录
				String absolute_path = CommonUtils.generateFile(subPath,image,imageFileName);
	         
	            itf.setAbsolute_path(absolute_path);
	            itf.setFile_path("upload/interface/"+imageFileName);
	        }
			
			
			
			this.interfaceDao.create(itf);
			
			//操作日志录入
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "消防图解界面设置" ; 		
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
	//发布屏保界面
	@Override
	public MsgBean pushSleepInterface(String ids) {
		
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String interface_id : list){
				Interface find = this.interfaceDao.find(interface_id);
				find.setState(CommonFiled.PUSH_STATE_ALREADY); //设置为已发布
				find.setUpdate_time(CommonUtils.currentDateTimeString());
				this.interfaceDao.modify(find);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_SCREEN,ids);
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "屏保界面设置" ; 		
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

    public MsgBean pushPlaceInterface(String ids) {
		
		try{
				Interface find = this.interfaceDao.find(ids);
				find.setState(CommonFiled.PUSH_STATE_ALREADY); //设置为已发布
				find.setUpdate_time(CommonUtils.currentDateTimeString());
				this.interfaceDao.modify(find);
			
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_SCREEN,ids);
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "场所介绍界面设置" ; 		
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

    public MsgBean pushFirePicInterface(String ids) {
		
		try{		
				Interface find = this.interfaceDao.find(ids);
				find.setState(CommonFiled.PUSH_STATE_ALREADY); //设置为已发布
				find.setUpdate_time(CommonUtils.currentDateTimeString());
				this.interfaceDao.modify(find);
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_SCREEN,ids);
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "消防图解界面设置" ; 		
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
	public MsgBean pushStartInterface(String ids) {
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String interface_id : list){
				Interface find = this.interfaceDao.find(interface_id);
				find.setState(CommonFiled.PUSH_STATE_ALREADY); //设置为已发布
				find.setUpdate_time(CommonUtils.currentDateTimeString());
				this.interfaceDao.modify(find);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_HOME_PAGE,ids);
			
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "初始界面设置" ; 		
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
	public MsgBean checkInterfaceSort(Interface itf){
		try{
			Interface findItf = this.interfaceDao.find(itf.getInterface_id());
			//修改验证唯一
			if(findItf!=null){
				//1：先判断是否修改
				if(!findItf.getInterface_sort().equals(itf.getInterface_sort())){ 
					//2：验证修改的名称是否存在数据库
					List<Interface> list = this.interfaceDao.findBySort(itf);
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
				List<Interface> list = this.interfaceDao.findBySort(itf);
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
	
	public int getMaxSortValue(){
		return this.interfaceDao.getMaxSortValue();
	}
	
	public MsgBean getMsgBean() {
		return msgBean;
	}

	public void setMsgBean(MsgBean msgBean) {
		this.msgBean = msgBean;
	}

	public InterfaceDao getInterfaceDao() {
		return interfaceDao;
	}

	public void setInterfaceDao(InterfaceDao interfaceDao) {
		this.interfaceDao = interfaceDao;
	}

	public OperationLogService getOptLogService() {
		return optLogService;
	}

	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}

}
