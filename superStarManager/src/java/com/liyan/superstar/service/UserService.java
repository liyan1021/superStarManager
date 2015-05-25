package com.liyan.superstar.service;

import com.liyan.common.action.MsgBean;
import com.liyan.superstar.model.User;

public interface UserService {

	MsgBean checkUserCode(User user);

	MsgBean checkUserPwd(User user, String oldPwd);

}
