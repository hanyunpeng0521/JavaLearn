package com.hyp.learn.reflect;

import java.lang.annotation.*;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.reflect
 * hyp create at 19-12-1
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotNull {
}
