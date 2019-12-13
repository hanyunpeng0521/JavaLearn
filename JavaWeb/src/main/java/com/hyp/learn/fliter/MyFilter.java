package com.hyp.learn.fliter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * @author hyp
 * Project name is javaLearn
 * Include in com.hyp.learn.fliter
 * hyp create at 19-12-13
 **/
@WebFilter(filterName = "filter1", urlPatterns = "/*", asyncSupported = true,
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD},
        initParams = {@WebInitParam(name = "account", value = "1234"), @WebInitParam(name = "hotusm", value = "1234")}
)

public class MyFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFilter.init");

        String account = filterConfig.getInitParameter("account");
        String hotusm = filterConfig.getInitParameter("hotusm");

        System.out.println("account:" + account + " hotusm:" + hotusm);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        System.out.println("MyFilter.doFilter");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("MyFilter.destroy");
    }
}