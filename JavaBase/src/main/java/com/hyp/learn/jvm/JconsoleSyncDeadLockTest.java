package com.hyp.learn.jvm;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.jvm
 * hyp create at 19-11-7
 **/
public class JconsoleSyncDeadLockTest {
    private static Object locka = new Object();
    private static Object lockb = new Object();

    public static void main(String[] args){
        new JconsoleSyncDeadLockTest().deadLock();
    }

    private void deadLock(){
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (locka){
                    try{
                        System.out.println(Thread.currentThread().getName()+" get locka ing!");
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName()+" after sleep 500ms!");
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" need lockb!Just waiting!");
                    synchronized (lockb){
                        System.out.println(Thread.currentThread().getName()+" get lockb ing!");
                    }
                }
            }
        },"thread1");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lockb){
                    try{
                        System.out.println(Thread.currentThread().getName()+" get lockb ing!");
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName()+" after sleep 500ms!");
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" need locka! Just waiting!");
                    synchronized (locka){
                        System.out.println(Thread.currentThread().getName()+" get locka ing!");
                    }
                }
            }
        },"thread2");

        thread1.start();
        thread2.start();
    }
}
