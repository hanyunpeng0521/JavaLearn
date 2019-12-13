package com.hyp.learn.thread;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.thread
 * hyp create at 19-11-9
 **/
public class CreateThreadDemo extends Thread {

    private int times;


    public CreateThreadDemo(String name, int times) {
        super(name);
        if (times > 0) {
            this.times = times;
        }
    }

    @Override
    public void run() {
        // 每隔1s中输出一次当前线程的名字
        for (int i = 0; i < times; i++) {
            // 输出线程的名字，与主线程名称相区分
            printThreadInfo();
            try {
                // 线程休眠一秒
                Thread.sleep(1000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }

    public static void main(String[] args) throws Exception {
        // 注意这里，要调用start方法才能启动线程，不能调用run方法
        new CreateThreadDemo("myThread", 5).start();
        // 演示主线程继续向下执行

        for (int i = 0; i < 6; i++) {
            printThreadInfo();
            Thread.sleep(1000);
        }


    }

    /**
     * 输出当前线程的信息
     */
    private synchronized static void printThreadInfo() {
        Thread curThread = Thread.currentThread();
        String curThreadName = curThread.getName();
        System.out.println("---------------" + curThread.getName() + "---------------");
        System.out.println("返回当前线程" + curThreadName + "的线程组中活动线程的数目：" + Thread.activeCount());
        System.out.println("返回该线程" + curThreadName + "的标识符：" + curThread.getId());
        System.out.println("返回线程" + curThreadName + "的优先级:" + curThread.getPriority());
        System.out.println("返回该线程" + curThreadName + "的状态:" + curThread.getState());
        System.out.println("返回该线程" + curThreadName + "所属的线程组:" + curThread.getThreadGroup());
        System.out.println("测试线程" + curThreadName + "是否处于活动状态:" + curThread.isAlive());
        System.out.println("测试线程" + curThreadName + "是否测试该线程是否为守护线程:" + curThread.isDaemon());
    }
}
