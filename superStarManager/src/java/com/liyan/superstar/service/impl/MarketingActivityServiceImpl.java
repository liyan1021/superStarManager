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
import com.liyan.superstar.dao.MarketingActivityDao;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.model.MarketingActivity;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.MarketingActivityService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class MarketingActivityServiceImpl implements MarketingActivityService{
	@Autowired
	private MarketingActivityDao  marketingActivityDao;
	
	@Autowired
	private OperationLogService optLogService ;
	
	@Autowired
	private MsgBean msgBean ;

	public MarketingActivityDao getMarketingActivityDao() {
		return marketingActivityDao;
	}

	public void setMarketingActivityDao(MarketingActivityDao marketingActivityDao) {
		this.marketingActivityDao = marketingActivityDao;
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
	public Pagination<MarketingActivity> query(
			MarketingActivity marketingActivity, Integer page, Integer rows,
			String sort, String order) {
		return this.marketingActivityDao.query(marketingActivity,page,rows,sort,order);
	}

	@Override
	public MsgBean removeMarketingActivity(List<String> list) {
		try{
			
			for(String intefaceId : list){
				MarketingActivity activity = this.marketingActivityDao.find(intefaceId);
				String file1 = activity.getAbsolute_path_1();
				String file2 = activity.getAbsolute_path_2();
				if(!CommonUtils.isEmpty(file1)){
					new File(file1).delete();
				}
				if(!CommonUtils.isEmpty(file2)){
					new File(file2).delete();
				}
				activity.setIs_activity(CommonFiled.DEL_STATE_Y);
				activity.setUpdate_time(CommonUtils.currentDateTimeString());
			}
//			this.marketingActivityDao.batchRemove(list);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
	  
			//操作功能
			String function = "营销活动" ; 		
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
	public MsgBean create(MarketingActivity marketingActivity,
			List<File> image, List<String> imageFileName, List<String> imageContentType) {
		try{
			
			marketingActivity.setActivity_id(UUID.randomUUID().toString());
			marketingActivity.setState(CommonFiled.PUSH_STATE_NO); //初始状态为未发布
			marketingActivity.setCreate_time(CommonUtils.currentDateTimeString());
			marketingActivity.setIs_activity(CommonFiled.DEL_STATE_N);
			marketingActivity.setUpdate_time(CommonUtils.currentDateTimeString());
			HttpServletRequest request = ServletActionContext.getRequest();
			if (image != null) {
				File file1 = image.get(1);
				File file2 = image.get(0);
				String imageName1 = imageFileName.get(1);
				String imageName2 = imageFileName.get(0);
				if(file1!=null){
					imageName1 = imageName1.substring(imageName1.lastIndexOf("."),imageName1.length());
					imageName1 = marketingActivity.getActivity_id()+"_1"+imageName1 ;
					//生成文件路径
					String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
					String subPath = rootPath + "\\activity";
					//写入指定目录
					String absolute_path = CommonUtils.generateFile(subPath,file1,imageName1);
					marketingActivity.setActivity_path_1("upload/activity/"+imageName1);
					marketingActivity.setAbsolute_path_1(absolute_path); //保存绝对路径
					
				}
				if(file2!=null){
					imageName2 = imageName2.substring(imageName2.lastIndexOf("."),imageName2.length());
					imageName2 = marketingActivity.getActivity_id()+"_2"+imageName2 ;
					//生成文件路径
					String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
					String subPath = rootPath + "\\activity";
					//写入指定目录
					String absolute_path = CommonUtils.generateFile(subPath,file2,imageName2);
					marketingActivity.setActivity_path_2("upload/activity/"+imageName2);
					marketingActivity.setAbsolute_path_2(absolute_path); //保存绝对路径
					
				}
	            
	            
	        }
			
			this.marketingActivityDao.create(marketingActivity);
			
			//操作日志录入
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "营销活动" ; 		
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
	public Pagination<MarketingActivity> search(
			MarketingActivity marketingActivity, Integer page, Integer rows,String sort, String order) {
		Pagination<MarketingActivity> query = this.marketingActivityDao.query(marketingActivity,page,rows,sort,order);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "营销活动" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH ; 
 
		getOptLogService().setLog(currentUser,function,type);
		
		return query;
	}

	@Override
	public MsgBean editMarketingActivity(MarketingActivity marketingActivity,
			List<File> image, List<String> imageFileName, List<String> imageContentType) {
		try{
			
			HttpServletRequest request = ServletActionContext.getRequest();
			MarketingActivity find = this.marketingActivityDao.find(marketingActivity.getActivity_id());
			find.setActivity_name(marketingActivity.getActivity_name());
			find.setActivity_introduce(marketingActivity.getActivity_introduce());
			find.setActivity_sort(marketingActivity.getActivity_sort());
			find.setActivity_time(marketingActivity.getActivity_time());
			find.setUpdate_time(CommonUtils.currentDateTimeString());
			find.setUpdate_time(CommonUtils.currentDateTimeString());
			if (image != null) {
				File file1 = image.get(0);
				File file2 = image.get(1);
				String imageName1 = imageFileName.get(0);
				String imageName2 = imageFileName.get(1);
				if(file1!=null){
					imageName1 = imageName1.substring(imageName1.lastIndexOf("."),imageName1.length());
					imageName1 = marketingActivity.getActivity_id()+"_1"+imageName1 ;
					//生成文件路径
					String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
					String subPath = rootPath + "\\activity";
					//写入指定目录
					String absolute_path = CommonUtils.generateFile(subPath,file1,imageName1);
					find.setActivity_path_1("upload/activity/"+imageName1);
					find.setAbsolute_path_1(absolute_path); //保存绝对路径
					
				}
				if(file2!=null){
					imageName2 = imageName2.substring(imageName2.lastIndexOf("."),imageName2.length());
					imageName2 = marketingActivity.getActivity_id()+"_2"+imageName2 ;
					//生成文件路径
					String rootPath = ServletActionContext.getServletContext().getRealPath("upload");
					String subPath = rootPath + "\\activity";
					//写入指定目录
					String absolute_path = CommonUtils.generateFile(subPath,file2,imageName2);
					find.setActivity_path_1("upload/activity/"+imageName2);
					find.setAbsolute_path_1(absolute_path); //保存绝对路径
					
				}
	            
	            
	        }
			this.marketingActivityDao.modify(find);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "营销活动" ; 		
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
	public MsgBean pushMarketingActivity(String ids) {
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String interface_id : list){
				MarketingActivity find = this.marketingActivityDao.find(interface_id);
				find.setState(CommonFiled.PUSH_STATE_ALREADY); //设置为已发布
				this.marketingActivityDao.modify(find);
			}
			//发送消息
			SocketUtil.pushSoket(Commons.METHOD_ACTION_UPDATE_ACTIVITY,ids);
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "营销活动" ; 		
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
	public MsgBean checkActivitySort(MarketingActivity marketingActivity) {
		try{
			MarketingActivity find = this.marketingActivityDao.find(marketingActivity.getActivity_id());
			//修改验证唯一
			if(find!=null){
				//1：先判断是否修改
				if(!find.getActivity_sort().equals(marketingActivity.getActivity_sort())){ 
					//2：验证修改的名称是否存在数据库
					List<MarketingActivity> list = this.marketingActivityDao.findBySort(marketingActivity);
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
				List<MarketingActivity> list = this.marketingActivityDao.findBySort(marketingActivity);
				if(list.size()!=0){
					msgBean.setSign(true);  //数据已存在
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
