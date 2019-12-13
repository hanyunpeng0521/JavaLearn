package com.hyp.learn.reflect;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.reflect
 * hyp create at 19-11-9
 **/
public class ReflectClass {
    private final static String TAG = "peter.log.ReflectClass";

    public static void main(String[] args) {

        t6();
    }

    public static void t1() {
        Class clazz = null;

        //1.通过类名
        clazz = Book.class;

        //2.通过对象名
        //这种方式是用在传进来一个对象，却不知道对象类型的时候使用
        Book book = new Book();
        clazz = book.getClass();

        //上面这个例子的意义不大，因为已经知道book类型是Book类，再这样写就没有必要了
        //如果传进来是一个Object类，这种做法就是应该的
        Object obj = new Book();
        clazz = obj.getClass();

        //3.通过全类名(会抛出异常)
        //一般框架开发中这种用的比较多，
        // 因为配置文件中一般配的都是全类名，通过这种方式可以得到Class实例

        String clazzName = "com.hyp.learn.reflect.Book";
        try {
            clazz = Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void t2() {
        Class clazz = null;
        String clazzName = "com.hyp.learn.reflect.Book";
        try {
            clazz = Class.forName(clazzName);

            //1.使用Constructor获取Book类实例,绑定对应的构造函数，可以使用所有可访问的构造函数
            Constructor constructor1 = clazz.getConstructor();
            Constructor constructor2 = clazz.getConstructor(String.class, String.class);

            //只能使用绑定的构造函数
            Book book1 = (Book) constructor1.newInstance();

            System.out.println(book1.toString());

            Book book2 = (Book) constructor2.newInstance("Java从入门到成神", "YY");

            System.out.println(book2);

            //2. 使用Class的newInstance，只能创建无参数构造函数
            Book book3 = (Book) clazz.newInstance();
            System.out.println(book3.toString());


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void t3() {
        Class clazz = null;
        String clazzName = "com.hyp.learn.reflect.Book";
        try {
            clazz = Class.forName(clazzName);

            //1. 获取可访问属性
            Field[] fields = clazz.getFields();
            System.out.println("clazz.getFields");
            if (null != fields) {
                for (Field field : fields) {
                    System.out.println(field.getModifiers() + ":" + field.getType() + ":" + field.getName());
                }
            }
            //2. 获取所有属性
            Field[] declaredFields = clazz.getDeclaredFields();
            System.out.println("clazz.getDeclaredFields");
            if (null != declaredFields) {
                for (Field field : declaredFields) {
                    System.out.println(field.getModifiers() + ":" + field.getType() + ":" + field.getName());
                }
            }
            //3. 通过Class函数获取
            clazz.getFields();
            clazz.getDeclaredFields();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void t4() {
        Class clazz = null;
        String clazzName = "com.hyp.learn.reflect.Book";

        try {
            clazz = Class.forName(clazzName);

            Class[] interfaces = clazz.getInterfaces();

            for (Class a : interfaces) {
                System.out.println(a);
            }

            AnnotatedType[] annotatedInterfaces = clazz.getAnnotatedInterfaces();

            for (AnnotatedType anInterface : annotatedInterfaces) {
                System.out.println(anInterface.getType());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void t5() {
        Class clazz = null;
        String clazzName = "com.hyp.learn.reflect.Book";

        try {
            clazz = Class.forName(clazzName);

            Annotation[] annotations = clazz.getAnnotations();

            for (Annotation annotation : annotations) {
                System.out.println(annotation.toString());
            }

            Annotation[] annotations1 = clazz.getDeclaredAnnotations();
            for (Annotation annotation : annotations1) {
                System.out.println(annotation.toString());
            }

            System.out.println(clazz.getDeclaredAnnotation(Resource.class));


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void t6() {
        Class clazz = null;
        String clazzName = "com.hyp.learn.reflect.Book";

        try {
            clazz = Class.forName(clazzName);
            Method[] methods = clazz.getMethods();
            if (null != methods) {
                for (Method method : methods) {
                    System.out.println(method.toString());
                }
            }
            Method method = clazz.getMethod("setName", String.class);
            Book book = new Book();
            System.out.println(book);
            method.invoke(book, "hello");
            System.out.println(book);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
