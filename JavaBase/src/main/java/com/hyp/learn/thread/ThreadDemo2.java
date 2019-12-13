package com.hyp.learn.thread;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.thread
 * hyp create at 19-11-9
 **/
import java.util.Random;
public class ThreadDemo2 {
    public static Random random=new Random();
    public static void print(){
        Thread curThread = Thread.currentThread() ;
        String curThreadName = curThread.getName();
        System.out.println("---------------"+curThread.getName()+"---------------");
        System.out.println("返回当前线程"+curThreadName+"的线程组中活动线程的数目："+Thread.activeCount());
        System.out.println("返回该线程"+curThreadName+"的标识符："+curThread.getId());
        System.out.println("返回线程"+curThreadName+"的优先级:"+curThread.getPriority());
        System.out.println("返回该线程"+curThreadName+"的状态:"+curThread.getState());
        System.out.println("返回该线程"+curThreadName+"所属的线程组:"+curThread.getThreadGroup());
        System.out.println("测试线程"+curThreadName+"是否处于活动状态:"+curThread.isAlive());
        System.out.println("测试线程"+curThreadName+"是否测试该线程是否为守护线程:"+curThread.isDaemon());

    }
    class ThreadA implements Runnable {
        @Override
        public void run() {
            try {
                //模拟做事情执行了1秒；//以便一会咱们的监控工具监控到啊！
                Thread.sleep((random.nextLong()%1000)+1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ThreadDemo2.print();
        }
    }

    public static void main(String[] args) {
        ThreadDemo2 demo2=new ThreadDemo2();
        ThreadA threadA=demo2.new ThreadA();
        for (int i=0;i<5;i++)
        {
            new Thread(threadA,"Thread"+i).start();
        }

        ThreadDemo2.print();

        try {
            Thread.sleep((random.nextLong()%1000)+1000);//休息10s以便我们的监控工具能监控到
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
