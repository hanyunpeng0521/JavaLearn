package com.hyp.learn.jvm;

/**
 * 1、stackoverflow：
 * <p>
 * 每当java程序启动一个新的线程时，java虚拟机会为他分配一个栈，java栈以帧为单位保持线程运行状态；当线程调用一个方法是，jvm压入一个新的栈帧到这个线程的栈中，只要这个方法还没返回，这个栈帧就存在。
 * 如果方法的嵌套调用层次太多(如递归调用),随着java栈中的帧的增多，最终导致这个线程的栈中的所有栈帧的大小的总和大于-Xss设置的值，而产生生StackOverflowError溢出异常。
 * <p>
 * 2、outofmemory：
 * <p>
 * 2.1、栈内存溢出
 * <p>
 * java程序启动一个新线程时，没有足够的空间为改线程分配java栈，一个线程java栈的大小由-Xss设置决定；JVM则抛出OutOfMemoryError异常。
 * <p>
 * 2.2、堆内存溢出
 * <p>
 * java堆用于存放对象的实例，当需要为对象的实例分配内存时，而堆的占用已经达到了设置的最大值(通过-Xmx)设置最大值，则抛出OutOfMemoryError异常。
 * <p>
 * 2.3、方法区内存溢出
 * <p>
 * 方法区用于存放java类的相关信息，如类名、访问修饰符、常量池、字段描述、方法描述等。在类加载器加载class文件到内存中的时候，JVM会提取其中的类信息，并将这些类信息放到方法区中。
 * 当需要存储这些类信息，而方法区的内存占用又已经达到最大值（通过-XX:MaxPermSize/-XX）；将会抛出OutOfMemoryError异常对于这种情况的测试，基本的思路是运行时产生大量的类去填满方法区，直到溢出。这里需要借助CGLib直接操作字节码运行时，生成了大量的动态类。
 *
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.jvm
 * hyp create at 19-12-10
 **/
public class ErrorGenerate {

    /**
     * 执行后会报StackOverflowError错误的方法
     */
    public void SOF() {
        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SOF();
    }

    /**
     * 执行后会报OutOfMemoryError错误的方法
     */
    public void OOM() {
        String str = "OOM";
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            str = str + str;
        }
    }


    /**
     * 测试
     */
    public static void main(String[] args) {
        //执行该方法，报错：StackOverflowError
        new ErrorGenerate().SOF();
        //执行该方法，报错：OutOfMemoryError
//        new ErrorGenerate().OOM();
    }

}