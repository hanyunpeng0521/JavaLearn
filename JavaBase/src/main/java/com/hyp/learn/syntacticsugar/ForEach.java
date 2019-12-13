package com.hyp.learn.syntacticsugar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.syntacticsugar
 * hyp create at 19-12-1
 **/
public class ForEach {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        List<String> list = new ArrayList<String>();
        list.add("AAA");
        list.add("BBB");

        for (String l : list) {
            System.out.print(l + "~");
        }

        Set<String> set = new HashSet<String>();
        set.add("CCC");
        set.add("DDD");

        for (String s : set) {
            System.out.print(s + "~");
        }
    }

    public static void print(String... params) {
        System.out.println();
        for (String s : params) {
            System.out.print(s + "~");
        }
    }
}
