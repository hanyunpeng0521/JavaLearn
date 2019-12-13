package com.hyp.learn.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * @author hyp
 * Project name is javaLearn
 * Include in com.hyp.learn.listener
 * hyp create at 19-12-13
 **/
@WebListener("This is only a ervletRequest listener")
public class MyServletRequestListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        System.out.println("调用MyServletRequestListener.requestDestroyed");
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        System.out.println("调用MyServletRequestListener.requestInitialized");

    }
}
