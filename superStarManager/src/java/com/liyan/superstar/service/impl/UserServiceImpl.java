package com.liyan.superstar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.util.MD5;
import com.liyan.superstar.dao.UserDao;
import com.liyan.superstar.model.Singer;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao ;
	@Autowired
	private
	MsgBean msgBean ; 
	@Override
	public MsgBean checkUserCode(User user) {
		try{
			User findUser = this.userDao.find(user.getUser_id());
			//修改验证唯一
			if(findUser!=null){
				//1：先判断是否修改
				if(!user.getUser_code().equals(findUser.getUser_code())){ 
					//2：验证修改的名称是否存在数据库
					List<User> list = this.userDao.findByUserCode(user);
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
				List<User> list = this.userDao.findByUserCode(user);
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
	
	@Override
	public MsgBean checkUserPwd(User user, String oldPwd) {
		try{
			User findUser = userDao.find(user.getUser_id());
			String user_pwd = findUser.getUser_pwd();
			oldPwd = MD5.toMD5(oldPwd);
			if(user_pwd.equals(oldPwd)){
				msgBean.setSign(true);
			}else{
				msgBean.setSign(false);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return msgBean;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public MsgBean getMsgBean() {
		return msgBean;
	}
	public void setMsgBean(MsgBean msgBean) {
		this.msgBean = msgBean;
	}
	
}
