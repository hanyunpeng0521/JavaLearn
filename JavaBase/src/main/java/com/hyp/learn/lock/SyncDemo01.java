package com.hyp.learn.lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.lock
 * hyp create at 19-11-10
 **/
public class SyncDemo01 {

    private static L l = new L();
    private static ReentrantLock reentrantLock = new ReentrantLock();


    public static void main(String[] args) {

        lockTest();

    }

    public static void lockTest() {
//        reentrantLock.lock();
//        System.out.println("1111111");
//        reentrantLock.unlock();

        System.out.println(ClassLayout.parseInstance(l).toPrintable());
        synchronized (l) {
            System.out.println(ClassLayout.parseInstance(l).toPrintable());
            System.out.println("22222");
        }
        System.out.println(ClassLayout.parseInstance(l).toPrintable());

    }

}
