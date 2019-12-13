package com.hyp.learn.jvm;

import java.util.Scanner;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.jvm
 * hyp create at 19-11-7
 **/
public class JconsoleThreadTest {

    public static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.next();

        new Thread(() -> {
            System.out.println("start whileThread ===>");
            while (true){

            }
        }, "whileThread").start();

        scanner.next();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("start watiThread ===>");
                Object o = new Object();
                synchronized (o){
                    try {
                        o.wait();
                    }catch (Exception e){

                    }
                }
            }
        }, "watiThread").start();
    }
}
