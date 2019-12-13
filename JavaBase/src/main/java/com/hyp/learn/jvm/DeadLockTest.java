package com.hyp.learn.jvm;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.jvm
 * hyp create at 19-11-7
 **/
public class DeadLockTest {
    static class SycnAddRunnable implements Runnable {
        int a, b;

        public SycnAddRunnable(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            synchronized (Integer.valueOf(a)) {
                synchronized (Integer.valueOf(b)) {
                    System.out.println(a + b);
                }

            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new SycnAddRunnable(1, 2)).start();
            new Thread(new SycnAddRunnable(2, 1)).start();
        }
    }
}
