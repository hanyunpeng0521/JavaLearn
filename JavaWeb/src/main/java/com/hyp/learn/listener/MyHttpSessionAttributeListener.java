package com.hyp.learn.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * @author hyp
 * Project name is javaLearn
 * Include in com.hyp.learn.listener
 * hyp create at 19-12-13
 **/
@WebListener("This is only a HttpSessionAttribute listener")
public class MyHttpSessionAttributeListener implements HttpSessionAttributeListener {
    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("调用MyHttpSessionAttributeListener.attributeAdded");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("调用MyHttpSessionAttributeListener.attributeRemoved");

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("调用MyHttpSessionAttributeListener.attributeReplaced");

    }
}
