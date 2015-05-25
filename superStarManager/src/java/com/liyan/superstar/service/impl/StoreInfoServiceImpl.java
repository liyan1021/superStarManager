package com.liyan.superstar.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyan.common.AppContext;
import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonFiled;
import com.liyan.superstar.dao.StoreInfoDao;
import com.liyan.superstar.model.StoreInfo;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.superstar.service.StoreInfoService;

@Service
@Transactional
public class StoreInfoServiceImpl implements StoreInfoService {

	@Autowired
	private StoreInfoDao storeInfoDao;
	@Autowired
	private MsgBean msgBean;
	@Autowired
	private OperationLogService optLogService;
	private Logger logger = Logger.getLogger(StoreInfoServiceImpl.class);

	@Override
	public MsgBean checkStoreInfoCode(StoreInfo storeInfo) {
		try {
			StoreInfo findStoreInfo = this.storeInfoDao.find(storeInfo.getStore_id());
			// 修改验证唯一
			if (findStoreInfo != null) {
				// 1：先判断是否修改
				if (!storeInfo.getStore_code().equals(findStoreInfo.getStore_code())) {
					// 2：验证修改的名称是否存在数据库
					List<StoreInfo> list = this.storeInfoDao.findByStoreInfoCode(storeInfo);
					if (list.size() != 0) {
						msgBean.setSign(true);
					} else {
						msgBean.setSign(false);
					}
				} else {
					msgBean.setSign(false);
				}

			} else {
				// 新增验证是否存在数据库
				List<StoreInfo> list = this.storeInfoDao.findByStoreInfoCode(storeInfo);
				if (list.size() != 0) {
					msgBean.setSign(true);
				} else {
					msgBean.setSign(false);
				}
				return msgBean;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msgBean;
	}
	@Override
	public Pagination<StoreInfo> query(StoreInfo storeInfo, Integer page,
			Integer rows, String sort, String order) {

		return this.storeInfoDao.query(storeInfo, page, rows, sort, order);

	}

	@Override
	public MsgBean createStoreInfo(StoreInfo storeInfo) {
		try {
			storeInfo.setStore_id(UUID.randomUUID().toString());
			Map<String,String> storeInfoMap = (Map<String,  String>) AppContext.getInstance().get("key.storeinfo"); 
			storeInfoMap.put(storeInfo.getStore_id(), storeInfo.getStore_name());
			AppContext.getInstance().put(AppContext.getInstance().getString("key.storeinfo"),storeInfoMap );
			HttpServletRequest request = ServletActionContext.getRequest();
			this.storeInfoDao.create(storeInfo);
			// 操作日志录入
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString(
					"key.session.current.user");
			// 操作人
			User currentUser = (User) session.getAttribute(key);
			// 操作功能
			String function = "门店管理";
			// 操作类型
			String type = CommonFiled.OPT_LOG_ADD;

			getOptLogService().setLog(currentUser, function, type);
			msgBean.setSign(true);
			msgBean.setMsg("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("保存失败");
		}
		return msgBean;

	}
	@Override
	public MsgBean removeStoreInfo(List<String> list) {
		try{
			//先将要删除的信息从内存中删除
			@SuppressWarnings("unchecked")
			Map<String,String> storeInfoMap = (Map<String,  String>) AppContext.getInstance().get("key.storeinfo"); 
			for(String temp:list)
			{
				//采用逻辑删除，只是将数据库中的IS_ACTIVITY字段置为1，表示从该表中删除
				StoreInfo find = this.storeInfoDao.find(temp);
				find.setIs_activity(CommonFiled.DEL_STATE_Y);
				this.storeInfoDao.modify(find);
				//从Map列表中删除
				storeInfoMap.remove(temp);  
			}
			AppContext.getInstance().put(AppContext.getInstance().getString("key.storeinfo"),storeInfoMap );
			
		this.storeInfoDao.batchRemove(list);
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "门店管理" ; 				
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
		return msgBean;
	}

	@Override
	public Pagination<StoreInfo> searchStoreInfo(StoreInfo storeInfo, Integer page,
			Integer rows, String sort, String order) {

		
		Pagination<StoreInfo> query = this.storeInfoDao.query(storeInfo, page, rows, sort, order);
		

		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "门店管理" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH ; 
		
		getOptLogService().setLog(currentUser,function,type);
		return query ;
	}
	@Override
	public MsgBean editStoreInfo(StoreInfo storeInfo) {
		try{
		HttpServletRequest request = ServletActionContext.getRequest();
		StoreInfo findStoreInfo = storeInfoDao.find(storeInfo.getStore_id());
		findStoreInfo.setStore_code(storeInfo.getStore_code());
		findStoreInfo.setStore_name(storeInfo.getStore_name());
		findStoreInfo.setStore_address(storeInfo.getStore_address());
		
		storeInfoDao.modify(findStoreInfo);
		@SuppressWarnings("unchecked")
		Map<String,String> storeInfoMap = (Map<String,  String>) AppContext.getInstance().get("key.storeinfo"); 
		storeInfoMap.put(storeInfo.getStore_id(), storeInfo.getStore_name());
		AppContext.getInstance().put(AppContext.getInstance().getString("key.storeinfo"),storeInfoMap );
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "门店管理" ; 		
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

	
	public StoreInfoDao getStoreInfoDao() {
		return storeInfoDao;
	}

	public void setStoreInfoDao(StoreInfoDao storeInfoDao) {
		this.storeInfoDao = storeInfoDao;
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

	
}
