package com.liyan.superstar.service.impl;

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
import com.liyan.superstar.dao.SystemParamDao;
import com.liyan.superstar.model.SystemParam;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.superstar.service.SystemParamService;

@Service
@Transactional
public class SystemParamServiceImpl implements SystemParamService {

	@Autowired
	private SystemParamDao sysParamDao;

	@Autowired
	private OperationLogService optLogService;

	@Autowired
	private MsgBean msgBean;

	public SystemParamDao getSysParamDao() {
		return sysParamDao;
	}

	public void setSysParamDao(SystemParamDao sysParamDao) {
		this.sysParamDao = sysParamDao;
	}

	@Override
	public Pagination<SystemParam> query(SystemParam sysParam, Integer page,
			Integer rows, String sort, String order) {
		return this.sysParamDao.query(sysParam, page, rows, sort, order);
	}

	@Override
	public MsgBean saveSysParam(SystemParam sysParam) {
		try {
			sysParam.setParam_type(UUID.randomUUID().toString());
			sysParam.setUpdate_date(CommonUtils.currentDateTimeString());
			this.sysParamDao.create(sysParam);
			// 操作日志录入
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			String key = AppContext.getInstance().getString(
					"key.session.current.user");
			// 操作人
			User currentUser = (User) session.getAttribute(key);

			// 操作功能
			String function = "系统参数设置";
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
	public MsgBean removeSysParam(List<String> list) {
		try {
			this.sysParamDao.batchRemove(list);
			// 操作日志录入
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			String key = AppContext.getInstance().getString(
					"key.session.current.user");
			// 操作人
			User currentUser = (User) session.getAttribute(key);

			// 操作功能
			String function = "系统参数设置";
			// 操作类型
			String type = CommonFiled.OPT_LOG_DEL;

			getOptLogService().setLog(currentUser, function, type);
			msgBean.setSign(true);
			msgBean.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("删除失败");
		}
		return msgBean;
	}

	@Override
	public Pagination<SystemParam> search(SystemParam sysParam, Integer page,
			Integer rows, String sort, String order) {
		Pagination<SystemParam> query = this.sysParamDao.query(sysParam, page,
				rows, sort, order);

		// 操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString(
				"key.session.current.user");
		// 操作人
		User currentUser = (User) session.getAttribute(key);

		// 操作功能
		String function = "系统参数设置";
		// 操作类型
		String type = CommonFiled.OPT_LOG_SEARCH;

		getOptLogService().setLog(currentUser, function, type);
		return query;
	}

	@Override
	public MsgBean editSysParam(SystemParam sysParam) {

		try {
			SystemParam find = this.sysParamDao.find(sysParam.getParam_type());
			find.setParam_name(sysParam.getParam_name());
			find.setDevice_type(sysParam.getDevice_type());
			find.setParam_value(sysParam.getParam_value());
			find.setUpdate_date(CommonUtils.currentDateTimeString());

			this.sysParamDao.modify(find);

			// 操作日志录入
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			String key = AppContext.getInstance().getString(
					"key.session.current.user");
			// 操作人
			User currentUser = (User) session.getAttribute(key);

			// 操作功能
			String function = "系统参数设置";
			// 操作类型
			String type = CommonFiled.OPT_LOG_MODIFY;

			getOptLogService().setLog(currentUser, function, type);
			msgBean.setSign(true);
			msgBean.setMsg("编辑成功");
		} catch (Exception e) {
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("编辑失败");
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

}
