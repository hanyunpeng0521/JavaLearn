package com.hyp.learn.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author hyp
 * Project name is javaLearn
 * Include in com.hyp.learn.listener
 * hyp create at 19-12-13
 **/
@WebListener("This is only a ServletContext listener")
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("调用MyServletContextListener.contextInitialized");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("调用MyServletContextListener.contextDestroyed");

    }
}
