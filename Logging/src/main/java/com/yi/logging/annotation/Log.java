package com.yi.logging.annotation;

import java.lang.annotation.*;

/**
 * Created by caihongwei on 2018/5/2 16:40.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String title();
    String api() default "";
    Operate operate();
    String desc() default "";

    enum Operate {
        CREATE, UPDATE, DELETE, SELECT
    }
}
