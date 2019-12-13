package com.hyp.learn.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.jvm
 * hyp create at 19-11-7
 **/
public class JconsoleHeapTest {
    public static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        try {
            Thread.sleep(5000);
        }catch (Exception e){

        }
        System.out.println("method start ====>");
        fill(1000);
        System.out.println("<==== method end ");
    }

    private static void fill(Integer cnt){
        List<JconsoleHeapTest> jconsoleTests = new ArrayList<JconsoleHeapTest>();
        for(int i = 0; i<cnt; i++){
            try {
                Thread.sleep(100);
            }catch (Exception e){

            }
            jconsoleTests.add(new JconsoleHeapTest());
        }
    }
}
