package com.weibo.keeplooking.pattern.singleton;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for singleton implementations.
 * 
 * @author Johnny
 */
public class SingletonTest {

    @Test
    public void testSingletonA() {
        SingletonA instance1 = SingletonA.getInstance();
        SingletonA instance2 = SingletonA.getInstance();
        Assert.assertEquals(instance1, instance2);
    }

    @Test
    public void testSingletonB() {
        SingletonB instance1 = SingletonB.getInstance();
        SingletonB instance2 = SingletonB.getInstance();
        Assert.assertEquals(instance1, instance2);
    }

    @Test
    public void testSingletonC() {
        SingletonC instance1 = SingletonC.getInstance();
        SingletonC instance2 = SingletonC.getInstance();
        Assert.assertEquals(instance1, instance2);
    }

}
