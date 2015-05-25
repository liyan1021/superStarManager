package com.liyan.superstar.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;




import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.liyan.superstar.model.User;
import com.liyan.common.AppContext;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorizationInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		 ActionContext actionContext = invocation.getInvocationContext();
	     HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
	     Map<String, Object> session = actionContext.getSession();
		 // 取得请求的Action名
		 String name = invocation.getInvocationContext().getName(); // action
		 boolean flag =false; 
		 // 获取到namespace
		 String namespace = invocation.getProxy().getNamespace();
		 namespace = namespace.substring(1);
		 String key = AppContext.getInstance().getString("key.session.current.user");
	     User currentUser = (User) session.get(key);
	     if(currentUser !=null ){
	    	 //曲库管理员
			 if(namespace.equals("library")&& currentUser.getRole_id().equals("1")){
				 return invocation.invoke(); 
			 }else 
			 //网络管理
			 if(namespace.equals("interface")&& currentUser.getRole_id().equals("2")){
				 return invocation.invoke(); 
			 }else
			 //设备管理
			 if(namespace.equals("tools")&& currentUser.getRole_id().equals("3")){
				 return invocation.invoke(); 
			 }else 
			 //用户管理员
	    	 if(namespace.equals("config") && currentUser.getRole_id().equals("4")){
	    		 return invocation.invoke(); 
			 }else{
				 return Action.ERROR;
			 }
	     }else{
	    	 return Action.LOGIN;
	     }
		
	}

}
