package com.hyp.learn.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author hyp
 * Project name is javaLearn
 * Include in com.hyp.learn.listener
 * hyp create at 19-12-13
 **/
@WebListener("This is only a HttpSession listener")
public class MyHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("创建session ,调用MyHttpSessionListener.sessionCreated");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("销毁session ,调用MyHttpSessionListener.sessionDestroyed");
    }

}