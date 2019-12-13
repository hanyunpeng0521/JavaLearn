package com.hyp.learn.syntacticsugar;

/**
 * https://www.jianshu.com/p/946b3c4a5db6
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.syntacticsugar
 * hyp create at 19-12-1
 **/
public class Autoboxing {
    public static void main(String[] args) {
        int i = 1;
        Integer a = 1;
        Integer b = 1;
        Long c = 1L;
        System.out.println(a == b);
        System.out.println(a.equals(i));
        System.out.println(c.equals(a));
        // TODO Auto-generated method stub
        if (true) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
