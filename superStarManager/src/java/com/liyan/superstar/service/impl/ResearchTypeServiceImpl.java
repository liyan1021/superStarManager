package com.liyan.superstar.service.impl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyan.common.AppContext;
import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.dao.ResearchTypeDao;
import com.liyan.superstar.model.ResearchType;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.superstar.service.ResearchTypeService;

@Service
@Transactional
public class ResearchTypeServiceImpl implements ResearchTypeService {
	
	@Autowired
	private ResearchTypeDao researchTypeDao ;
	
	@Autowired
	private OperationLogService optLogService ;

	@Autowired
	private MsgBean msgBean ; 
	
	
	@Override
	public Pagination<ResearchType> query(ResearchType researchType,
			Integer page, Integer rows) {
		
		return this.researchTypeDao.query(researchType, page, rows);
		
	}

	@Override
	public MsgBean removeResearchType(String[] idsList) {
		
		try{
			List<String> list = Arrays.asList(idsList);
			for(String id :list ){
				ResearchType find = this.researchTypeDao.find(id);
				find.setIs_activity(CommonFiled.DEL_STATE_Y);
				find.setUpdate_time(CommonUtils.currentDateTimeString());
				this.researchTypeDao.modify(find);
			}
			/*this.researchTypeDao.batchRemove(list);*/
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "意见调查类型管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_DEL ; 
			getOptLogService().setLog(currentUser,function,type);
			
			msgBean.setSign(true);
			msgBean.setMsg("删除成功");
		}catch(Exception e){
			msgBean.setSign(false);
			msgBean.setMsg("删除失败");
		}
		return msgBean ; 
	}

	@Override
	public MsgBean editResearchType(ResearchType researchType) {
		try{
			ResearchType find = this.researchTypeDao.find(researchType.getQuestion_id());
			find.setQuestion_name(researchType.getQuestion_name());
			find.setQuestion_type(researchType.getQuestion_type());
			find.setQuestion_score(researchType.getQuestion_score());
			find.setOption_a(researchType.getOption_a());
			find.setOption_a_score(researchType.getOption_a_score());
			find.setOption_b(researchType.getOption_b());
			find.setOption_b_score(researchType.getOption_b_score());
			find.setOption_c(researchType.getOption_c());
			find.setOption_c_score(researchType.getOption_c_score());
			find.setOption_d(researchType.getOption_d());
			find.setOption_d_score(researchType.getOption_d_score());
			find.setOption_e(researchType.getOption_e());
			find.setOption_e_score(researchType.getOption_e_score());
			find.setOption_f(researchType.getOption_f());
			find.setOption_f_score(researchType.getOption_f_score());
			find.setUpdate_time(CommonUtils.currentDateTimeString());
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "意见调查类型管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 
			getOptLogService().setLog(currentUser,function,type);
			
			msgBean.setSign(true);
			msgBean.setMsg("编辑成功");
		}catch(Exception e){
			msgBean.setSign(false);
			msgBean.setMsg("编辑失败");
		}
		return msgBean ; 
	}

	@Override
	public MsgBean saveResearchType(ResearchType researchType) {
		try{
			researchType.setQuestion_id(UUID.randomUUID().toString());
			String store = AppContext.getInstance().getString("key.store");
			researchType.setStore(store);
			researchType.setCreate_time(CommonUtils.currentDateTimeString());
			researchType.setIs_activity(CommonFiled.DEL_STATE_N);
			researchType.setUpdate_time(CommonUtils.currentDateTimeString());
			this.researchTypeDao.create(researchType);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "意见调查类型管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_ADD ; 
			getOptLogService().setLog(currentUser,function,type);
			
			msgBean.setSign(true);
			msgBean.setMsg("保存成功");
		}catch(Exception e){
			msgBean.setSign(false);
			msgBean.setMsg("保存失败");
		}
		return msgBean ; 
	}

	public ResearchTypeDao getResearchTypeDao() {
		return researchTypeDao;
	}

	public void setResearchTypeDao(ResearchTypeDao researchTypeDao) {
		this.researchTypeDao = researchTypeDao;
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
