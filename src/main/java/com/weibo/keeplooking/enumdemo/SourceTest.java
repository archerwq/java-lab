package com.weibo.keeplooking.enumdemo;

import org.junit.Test;

/**
 * Test cases for enum usage.
 *
 * @author Johnny
 *
 */
public class SourceTest {

    @Test
    public void testEnum() {
        for (Source source : Source.values()) {
            // print the name of this enum constant, as declaration
            System.out.println(source.toString());
        }
    }

    @Test
    public void testEnumClassName() {
        for (Source source : Source.values()) {
            System.out.println(source.getClass().getName());
            System.out.println(source.getDeclaringClass().getName() + "  ####");
        }
    }

}
