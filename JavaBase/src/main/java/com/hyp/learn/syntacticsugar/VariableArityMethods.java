package com.hyp.learn.syntacticsugar;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.syntacticsugar
 * hyp create at 19-12-1
 **/
public class VariableArityMethods {
    public static void main(String[] args) {
        String[] params = new String[]{
                "111", "222", "333", "444"
        };
        print("AAA", "BBB", "CCC", "DDD");
        print(params);
    }

    public static void print(String... params) {
        System.out.println();
        for (int i = 0; i < params.length; i++) {
            System.out.print(params[i] + "~");
        }
    }
}

