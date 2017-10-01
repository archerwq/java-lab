package com.weibo.keeplooking.reflection;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.weibo.keeplooking.shape.Circle;
import com.weibo.keeplooking.shape.Point;

/**
 * Unit tests for ReflectionAPI.
 * 
 * @author Johnny
 */
public class ReflectionApiTestor {
    private static final double DELTA = 0.0000001d;
    private Circle circle;
    private Point point;

    @Before
    public void setUp() {
        Point center = new Point(5.0d, 5.0d);
        circle = new Circle(center, 5.0d);
        point = new Point(5.0d, 10.0d);
    }

    /**
     * NoSuchFieldException is expected when accessing private field.
     */
    @Test(expected = NoSuchFieldException.class)
    public void accessFieldValue() throws SecurityException,
            IllegalArgumentException, NoSuchFieldException,
            IllegalAccessException {
        Assert.assertEquals(5.0d,
                (Double) ReflectionApi.fieldValue(point, "x"), DELTA);
    }

    @Test
    public void accessPrivateFieldValue() throws SecurityException,
            IllegalArgumentException, NoSuchFieldException,
            IllegalAccessException {
        Assert.assertEquals(5.0d,
                (Double) ReflectionApi.privateFieldValue(point, "x"), DELTA);
    }

    @Test
    public void accessStaticFieldValue() throws SecurityException,
            IllegalArgumentException, ClassNotFoundException,
            NoSuchFieldException, IllegalAccessException {
        Assert.assertTrue((Integer) ReflectionApi.staticFieldValue(
                "com.weibo.keeplooking.shape.Circle", "COUNT") > 0);
    }

    @Test
    public void invokePublicMethod() throws SecurityException,
            IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        Assert.assertEquals(Math.PI * 25d,
                (Double) ReflectionApi.invoke(circle, "area", new Object[0]),
                DELTA);
    }

    @Test
    public void invokePrivateMethod() throws SecurityException,
            IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        Assert.assertEquals(5.0d, (Double) ReflectionApi.invokePrivate(circle,
                "distanceToCenter", new Object[] {point}), DELTA);
    }

    @Test
    public void invokeStaticMethod() throws SecurityException,
            IllegalArgumentException, ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        Assert.assertEquals(5.0d, (Double) ReflectionApi.invokeStatic(
                "com.weibo.keeplooking.shape.Point", "distance", new Object[] {
                        point, circle.getCenter()}), DELTA);
    }

    @Test
    public void newInstance() throws SecurityException,
            IllegalArgumentException, ClassNotFoundException,
            NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        Assert.assertTrue(point.equals(ReflectionApi.newInstance(
                "com.weibo.keeplooking.shape.Point",
                new Object[] {5.0d, 10.0d})));
    }

}
