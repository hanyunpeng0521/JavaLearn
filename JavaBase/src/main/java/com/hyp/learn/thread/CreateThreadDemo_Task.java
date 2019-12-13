package com.hyp.learn.thread;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.thread
 * hyp create at 19-11-9
 **/
public class CreateThreadDemo_Task implements Runnable {

    private int times;

    public CreateThreadDemo_Task(int times) {
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
        // 实例化线程任务类
        CreateThreadDemo_Task task = new CreateThreadDemo_Task(5);

        // 创建线程对象,并将线程任务类作为构造方法参数传入
        Thread thread = new Thread(task, "test01");

        thread.start();
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
