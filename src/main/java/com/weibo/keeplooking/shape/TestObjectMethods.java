package com.weibo.keeplooking.shape;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for Object.equals(), Object.hashCode() and Comparable interface.
 * 
 * @author Johnny
 */
public class TestObjectMethods {

    private Point point1;
    private Point point2;
    private Circle circle1;
    private Circle circle2;

    @Before
    public void setUp() {
        point1 = new Point(1.125d, 2.250d);
        point2 = new Point(1.125d, 2.250d);

        circle1 = new Circle(point1, 3.0d);
        circle2 = new Circle(point2, 3.0d);
    }

    @Test
    public void testEquals() {
        Assert.assertTrue(!point1.equals(null));
        Assert.assertTrue(point1.equals(point1));
        Assert.assertTrue(point1.equals(point2));
        Assert.assertTrue(point2.equals(point1));

        Assert.assertTrue(circle1.equals(circle1));
        Assert.assertTrue(circle1.equals(circle2));
        Assert.assertTrue(circle2.equals(circle1));
    }

    @Test
    public void testHashCode() {
        Set<Point> set = new HashSet<Point>();
        Point point3 = new Point(1.125d, 4.50d);
        set.add(point1);
        set.add(point2);
        set.add(point3);
        Assert.assertEquals(2, set.size());

        Map<Circle, String> map = new HashMap<Circle, String>();
        map.put(circle1, "circle 1");
        map.put(circle2, "circle 2");
        Assert.assertEquals(1, map.size());

        Point point4 = new Point(1.125d, 2.250d);
        Circle circle3 = new Circle(point4, 3.0d);
        Assert.assertTrue("circle 2".equals((String) map.get(circle3)));
    }

    @Test
    public void testComparator() {
        Set<Point> set = new TreeSet<Point>();
        Point point3 = new Point(1.125d, 4.50d);
        set.add(point1);
        set.add(point2);
        set.add(point3);
        Assert.assertEquals(1, set.size());
    }

}
