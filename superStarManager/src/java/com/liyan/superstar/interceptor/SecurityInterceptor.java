package com.liyan.superstar.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.liyan.superstar.model.User;
import com.liyan.common.AppContext;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SecurityInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -1633081447617384727L;


    private static final String LOGIN = Action.LOGIN;
    //private UserDAO userDao;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
        Map<String, Object> session = actionContext.getSession();
        
        String key = AppContext.getInstance().getString("key.session.current.user");
        User currentUser = (User) session.get(key);

        if (currentUser != null) {
            return invocation.invoke();
        }
        return LOGIN;
    }

}
