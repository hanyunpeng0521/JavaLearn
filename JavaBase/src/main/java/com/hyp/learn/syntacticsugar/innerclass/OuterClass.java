package com.hyp.learn.syntacticsugar.innerclass;

/**
 * 内部类是个编译时的概念，一旦编译成功后，它就与外围类属于两个完全不同的类（当然他们之间还是有联系的）。
 * 对于一个名为OuterClass的外围类和一个名为InnerClass的内部类，
 * 在编译成功后，会出现这样两个class文件：OuterClass.class和OuterClass$InnerClass.class
 *
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.base.innerclass
 * hyp create at 19-12-1
 **/
public class OuterClass {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public class InnerClass {
        //内部类直接可以使用外部类的成员变量和方法
        // 因此外部类必须在内部成员类声明前被声明
        public InnerClass() {
            name = "pingxin";
            age = 20;
        }

        public void display() {
            //外部类的引用
            OuterClass.this.display();
            //外部类的超类引用
//            OuterClass.super

        }

    }

    public void display() {
        System.out.println("name：" + getName() + "   ;age：" + getAge());
    }

    private String name;
    private Integer age;


    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        //成员内部类的声明方式
        InnerClass innerClass = outerClass.new InnerClass();
        innerClass.display();
    }
}
