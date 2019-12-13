package com.hyp.learn.reflect;

import org.reflections.Reflections;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.reflect
 * hyp create at 19-11-9
 **/
public class ReflectionsUseCase {
    public static void main(String[] args) {
        Reflections reflections=new Reflections("com.hyp.learn");

        System.out.println(reflections.getAllTypes());
    }
}
