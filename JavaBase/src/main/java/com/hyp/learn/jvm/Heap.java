package com.hyp.learn.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.jvm
 * hyp create at 19-12-10
 **/
public class Heap {
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        int i = 0;
        boolean flag = true;
        while (flag) {
            try {
                i++;
                list.add(new byte[1024 * 1024]);
                Thread.sleep(40);
            } catch (Throwable t) {
                t.printStackTrace();
                flag = false;
                System.out.println("count: " + i);
            }
        }
    }
}
