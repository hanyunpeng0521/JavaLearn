package com.hyp.learn.reflect;


import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.reflect
 * hyp create at 19-11-9
 **/
@Resource
public class Book implements Serializable {
    private final static String TAG = "BookTag";
    @NotNull
    public String ID;
    @NotNull
    private String name;

    private String author;

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public Book() {
    }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String declaredMethod(int index) {
        String string = null;
        switch (index) {
            case 0:
                string = "I am declaredMethod 1 !";
                break;
            case 1:
                string = "I am declaredMethod 2 !";
                break;
            default:
                string = "I am declaredMethod 1 !";
        }
        return string;
    }
}
