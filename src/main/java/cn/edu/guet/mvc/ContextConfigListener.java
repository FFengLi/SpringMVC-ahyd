package cn.edu.guet.mvc;

/**
 * @Author liwei
 * @Date 2020/9/5 10:57
 * @Version 1.0
 */

import cn.edu.guet.ioc.BeanFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.net.URISyntaxException;
import java.util.Map;

@WebListener()
public class ContextConfigListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public ContextConfigListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed).
         You can initialize servlet context related data here.
      */
        try {
            /*
            加载工厂类，注入Bean
             */
            Class.forName("cn.edu.guet.ioc.BeanFactory");

            Map<String,ControllerMapping> controllerMapping=new Configuration().config();
            /*
            服务端给网页（jsp）传数据，把数据放入某个作用域：request、session、application
             */
            sce.getServletContext().setAttribute("cn.guet.web.controller",controllerMapping);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context
         (the Web application) is undeployed or
         Application Server shuts down.
      */
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
