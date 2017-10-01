package com.weibo.keeplooking.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate a method is a test case.
 * 
 * @author Johnny
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Test {
    boolean enabled() default true;

    Class<? extends Exception>[] expected() default {};
}
