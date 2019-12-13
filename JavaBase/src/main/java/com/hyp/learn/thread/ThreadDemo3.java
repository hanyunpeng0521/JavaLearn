package com.hyp.learn.thread;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.thread
 * hyp create at 19-11-9
 **/
public class ThreadDemo3 {
    class ThreadA extends Thread {
        private int times;


        public ThreadA(String name, int times) {
            super(name);
            if (times>0) {
                this.times = times;
            }
        }


        @Override
        public void run() {
            for (long i = 0; i < times; i++) {
                System.out.println("后台线程"+getName()+"第" + i + "次执行！");
                try {
                    Thread.sleep(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadDemo3 demo3=new ThreadDemo3();
        Thread tA = demo3.new ThreadA("A",20);
        Thread tB = demo3.new ThreadA("B",10);
        tA.setDaemon(true); // 设置为守护线程,注意一定要在开始之前调用

        tB.start();
        tA.start();
        Thread mainThread = Thread.currentThread();
        System.out.println("线程A是不是守护线程:"+tA.isDaemon());
        System.out.println("线程b是不是守护线程:"+tB.isDaemon());
        System.out.println("线程main是不是守护线程:"+mainThread.isDaemon());
    }
}
