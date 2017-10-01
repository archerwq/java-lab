package com.weibo.keeplooking.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to show basic info of test class.
 * 
 * @author Johnny
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface TestInfo {

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    Priority priority() default Priority.MEDIUM;

    String createdBy() default "Johnny";

    String[] tags() default "";

    String lastModified();

}
