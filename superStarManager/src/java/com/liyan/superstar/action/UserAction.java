package com.liyan.superstar.action;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.liyan.superstar.dao.UserDao;
import com.liyan.superstar.model.Operation;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.superstar.service.UserService;
import com.liyan.common.AppContext;
import com.liyan.common.action.MsgBean;
import com.liyan.common.action.PageAwareActionSupport;
import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.common.util.MD5;
import com.liyan.common.util.OperationLog;
import com.opensymphony.xwork2.Action;
@Controller
@Scope("prototype")
public class UserAction  extends PageAwareActionSupport<User>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private UserDao userDao ; 
	private User user ; 
	private String ids ; 
	private String start_time ;
	private String end_time ; 
	private String oldPwd ; 
	@Autowired
	private OperationLogService optLogService ;
	
	@Autowired
	private UserService userService ;
	public String goUserList(){
		return Action.SUCCESS;
	}
	public String index(){
		return Action.SUCCESS;
	}
	/**
	 *  分页加载所有用户列表
	 */
	public void userList(){
		try{
			Pagination<User> result = userDao.query(user,start_time,end_time,page,rows,sort,order);
			List<User> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(User user : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("user_id", user.getUser_id());
				jsonObject.element("user_name", user.getUser_name());
				jsonObject.element("user_code",user.getUser_code());
				jsonObject.element("tel_num", user.getTel_num());
				jsonObject.element("create_time", user.getCreate_time().toString());
				jsonObject.element("org_code", user.getOrg_code());
				jsonObject.element("org_name", user.getOrg_name());
				jsonObject.element("offi_name", user.getOffi_name());
				jsonObject.element("role_id", user.getRole_id());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 查询用户
	 */
	public void searchUserList(){
		try{
			Pagination<User> result = userDao.query(user,start_time,end_time,page,rows,sort,order);
			List<User> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(User user : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("user_id", user.getUser_id());
				jsonObject.element("user_name", user.getUser_name());
				jsonObject.element("user_code",user.getUser_code());
				jsonObject.element("tel_num", user.getTel_num());
				jsonObject.element("create_time", user.getCreate_time().toString());
				jsonObject.element("org_code", user.getOrg_code());
				jsonObject.element("org_name", user.getOrg_name());
				jsonObject.element("offi_name", user.getOffi_name());
				jsonObject.element("role_id", user.getRole_id());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "用户管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_SEARCH ; 
			
			getOptLogService().setLog(currentUser,function,type);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 *  保存用户 
	 */
	public void saveUser(){
		try{
			user.setUser_id(UUID.randomUUID().toString());
			user.setCreate_time(CommonUtils.currentDateTimeString());
			String password = MD5.toMD5("1");
			user.setUser_pwd(password);
			user.setIs_activity(CommonFiled.DEL_STATE_N);
			user.setModify_time(CommonUtils.currentDateTimeString());
			userDao.create(user);
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("message", "保存成功");
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "用户管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_ADD ; 

			getOptLogService().setLog(currentUser,function,type);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//编辑用户
	public void editUser(){
		try{
			User findUser = userDao.find(user.getUser_id());
			findUser.setUser_name(user.getUser_name());  //姓名
			findUser.setUser_code(user.getUser_code());  //帐号
			
			if(!CommonUtils.isEmpty(user.getTel_num())){
				findUser.setTel_num(user.getTel_num());//电话
			}
			if(!CommonUtils.isEmpty(user.getOffi_name())){
				findUser.setOffi_name(user.getOffi_name()); //职务
			}
			if(!CommonUtils.isEmpty(user.getRole_id())){
				findUser.setRole_id(user.getRole_id() ) ;//角色
			}
			if(!CommonUtils.isEmpty(user.getOrg_name())){
				findUser.setOrg_name(user.getOrg_name());//门店
			}
			findUser.setModify_time(CommonUtils.currentDateTimeString());
			if(!CommonUtils.isEmpty(user.getUser_pwd())){
				findUser.setUser_pwd( MD5.toMD5(user.getUser_pwd()));
				//放入session
				HttpSession session = ServletActionContext.getRequest().getSession();
		        session.setAttribute(AppContext.getInstance().getString("key.session.current.user"),findUser);
			}
			userDao.modify(findUser);
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("message", "编辑成功");
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "用户管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 
			getOptLogService().setLog(currentUser,function,type);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 获取当前用户
	 */
	public void loadUser(){
		try{
			user = userDao.load(user.getUser_id());
			JSONObject json = JSONObject.fromObject(user);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 删除用户
	 */
	public void removeUser(){
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			for(String user_id :list){
				User find = this.userDao.find(user_id);
				find.setIs_activity(CommonFiled.DEL_STATE_Y);
				this.userDao.modify(find);
			}
//			userDao.batchRemove(list);
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("message", "删除成功");
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "用户管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_DEL; 
			getOptLogService().setLog(currentUser,function,type);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String login(){
		
		return Action.SUCCESS;
	}
	/**
	 * 用户登录
	 */
	public void userLogin() {
		try {
			String user_code = user.getUser_code();
//			user_code = new String(user_code.getBytes("ISO-8859-1"),"UTF-8");
			User loginUser = userDao.getUserByUsercode(user_code);
			JSONObject json = new JSONObject();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			if (loginUser == null) {
				// 没有该用户
				json.put("success", false);
				json.put("message", "没有该用户！");
				out.println(json.toString());
				out.flush();
				out.close();
			} else if (!MD5.toMD5(user.getUser_pwd()).equals(loginUser.getUser_pwd())) {
				// 密码不正确
				json.put("success", false);
				json.put("message", "密码不正确！");
				out.println(json.toString());
				out.flush();
				out.close();
			} else {
				//放入session
				HttpSession session = ServletActionContext.getRequest().getSession();
		        session.setAttribute(AppContext.getInstance().getString("key.session.current.user"), loginUser);
				json.put("success", true);
				json.put("message", "");
				out.println(json.toString());
				out.flush();
				out.close();
				
				//操作日志录入
				//操作功能
				String function = "登录" ; 		
			    //操作类型
				String type = CommonFiled.OPT_LOG_LOGIN ; 
				getOptLogService().setLog(loginUser,function,type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 退出
	 * @return
	 */
	public String logout(){
		
		HttpSession session = ServletActionContext.getRequest().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Action.SUCCESS;
	}
	public void checkUserCode(){
		try{
			MsgBean msgBean = userService.checkUserCode(user);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(msgBean.isSign());
			out.flush();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 *  密码修改
	 */
	public void updatePwd(){
		try{
			
			User findUser = userDao.find(user.getUser_id());
			findUser.setModify_time(CommonUtils.currentDateTimeString());
			if(!CommonUtils.isEmpty(user.getUser_pwd())){
				findUser.setUser_pwd( MD5.toMD5(user.getUser_pwd()));
				//放入session
				HttpSession session = ServletActionContext.getRequest().getSession();
		        session.setAttribute(AppContext.getInstance().getString("key.session.current.user"),findUser);
			}
			userDao.modify(findUser);
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("message", "编辑成功");
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
			
			//操作日志录入
			HttpSession session = ServletActionContext.getRequest().getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "用户管理" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 
			getOptLogService().setLog(currentUser,function,type);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void checkUserPwd(){
		try{
			MsgBean msgBean = userService.checkUserPwd(user,oldPwd);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(msgBean.isSign());
			out.flush();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public OperationLogService getOptLogService() {
		return optLogService;
	}
	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
}
