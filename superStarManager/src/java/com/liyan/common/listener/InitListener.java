package com.liyan.common.listener;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.liyan.common.AppContext;
import com.liyan.common.Configuation;
import com.liyan.common.dao.HibernateSessionFactory;

public class InitListener implements ServletContextListener {

    private Log log = LogFactory.getLog(InitListener.class);
    private ServletContext servletContext;
    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }
    

    @Override
    public void contextInitialized(ServletContextEvent event) {
    	
        this.servletContext = event.getServletContext();
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(Configuation.CONFIGUATION_FILE());//加载该配置文件
            Properties p = new Properties(); //该类用于读取配置文件
            p.load(in);

            for (Iterator<Object> iterator = p.keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                AppContext.getInstance().put(key, p.getProperty(key)); //将此配置文件加载至内存
            }
            
        } catch (Exception e) {
            log.error("Can not init properties, cause by reading configuation.properties failed", e);
        }
    }
    
}
