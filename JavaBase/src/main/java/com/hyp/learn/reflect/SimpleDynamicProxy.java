package com.hyp.learn.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.reflect
 * hyp create at 19-11-9
 **/
public class SimpleDynamicProxy {

    public static void consumer(Interface interf) {
        interf.doSomething();
        interf.doSomethingElse("args");
    }

    public static void main(String[] args) {
        System.out.println("---------- no proxy ----------");
        RealObject real = new RealObject();
        consumer(real);

        System.out.println("---------- simple proxy ----------");
        consumer(new SimpleProxy(real));

        System.out.println("---------- dynamic proxy ----------");

        // 创建代理对象：设置 ClassLoader、接口 Class 对象、InvocationHandler
        Interface proxy = (Interface) Proxy.newProxyInstance(
                Interface.class.getClassLoader(),
                new Class[]{Interface.class}, // 数组里的 Class 对象必须是接口且不能重复
                new DynamicProxyHandler(real));
        consumer(proxy);
    }

    /**
     * 代理对象必须实现自己的 InvocationHandler，所有的调用都会被重定向到这个调用处理器上
     */
    static class DynamicProxyHandler implements InvocationHandler {
        /**
         * 被代理的对象，调用的请求会转发到这个“实际”对象上
         */
        private Object proxied;

        public DynamicProxyHandler(Object proxied) {
            this.proxied = proxied;
        }

        /**
         * 该方法接收三个参数，代理对象的实例、调用的方法的实例以及方法的参数。
         * 我们一般在这里确定调用该方法时所采取的措施。
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Proxy: " + proxy.getClass()
                    + ", method: " + method + ", args: " + args);

            // 如果方法参数不为空则输出参数
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    System.out.println("args[" + i + "] = " + args[i]);
                }
            }

            // 转发请求给被代理对象
            return method.invoke(proxied, args);
        }
    }
}

interface Interface {
    void doSomething();

    void doSomethingElse(String args);
}

class RealObject implements Interface {

    @Override
    public void doSomething() {
        System.out.println("doSomething");
    }

    @Override
    public void doSomethingElse(String args) {
        System.out.println("doSomethingElse " + args);
    }
}

class SimpleProxy implements Interface {

    Interface mInterface;

    public SimpleProxy(Interface anInterface) {
        mInterface = anInterface;
    }

    @Override
    public void doSomething() {
        System.out.println("SimpleProxy doSomething");
        mInterface.doSomething();
    }

    @Override
    public void doSomethingElse(String args) {
        System.out.println("SimpleProxy doSomethingElse");
        mInterface.doSomethingElse(args);
    }
}