package com.hyp.learn.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * @author hyp
 * Project name is javaLearn
 * Include in com.hyp.learn.listener
 * hyp create at 19-12-13
 **/
@WebListener("This is only a ServletRequestAttribute listener")
public class MyServletRequestAttributeListener implements ServletRequestAttributeListener {
    @Override
    public void attributeAdded(ServletRequestAttributeEvent servletRequestAttributeEvent) {
        System.out.println("MyServletRequestAttributeListener.attributeAdded");

    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent servletRequestAttributeEvent) {
        System.out.println("MyServletRequestAttributeListener.attributeRemoved");

    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent servletRequestAttributeEvent) {
        System.out.println("MyServletRequestAttributeListener.attributeReplaced");

    }
}
