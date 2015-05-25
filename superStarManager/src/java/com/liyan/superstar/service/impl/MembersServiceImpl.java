package com.liyan.superstar.service.impl;

import java.util.Arrays;
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
import com.liyan.common.util.MD5;
import com.liyan.superstar.dao.MembersDao;
import com.liyan.superstar.model.Members;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.MembersService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class MembersServiceImpl implements MembersService{
	@Autowired
	private MembersDao membersDao ; 
	@Autowired
	private OperationLogService optLogService ;
	@Autowired
	private MsgBean msgBean ; 
	@Override
	public Pagination<Members> memberList(Members members, Integer page,
			Integer rows) {
		return membersDao.query(members,page,rows);
	}

	@Override
	public Pagination<Members> searchMember(Members members, Integer page,
			Integer rows) {
		Pagination<Members> query = membersDao.query(members,page,rows);
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "会员管理" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH; 

		getOptLogService().setLog(currentUser,function,type);
		return query ;
	}

	@Override
	public MsgBean removeMember(String[] idsList) {
		try{
			
			List<String> list = Arrays.asList(idsList);
			this.membersDao.batchRemove(list);
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "会员管理" ; 		
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
	public MsgBean saveMembers(Members members) {
		try{
			
			members.setMember_id(UUID.randomUUID().toString());
			members.setMember_pwd(MD5.toMD5("123456")); //初始密码为123456
			this.membersDao.create(members);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "会员管理" ; 		
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
	public MsgBean editMembers(Members members) {
		try{
			
			Members findMember = this.membersDao.find(members.getMember_id());
			findMember.setMember_name(members.getMember_name());
			findMember.setMember_tel(members.getMember_tel());
			findMember.setMember_code(members.getMember_code());
			
			this.membersDao.modify(findMember);
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
	
			//操作功能
			String function = "会员管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY; 
	
			getOptLogService().setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("编辑成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("保存失败");
		}
		return msgBean ; 
	}

	public MembersDao getMembersDao() {
		return membersDao;
	}

	public void setMembersDao(MembersDao membersDao) {
		this.membersDao = membersDao;
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
