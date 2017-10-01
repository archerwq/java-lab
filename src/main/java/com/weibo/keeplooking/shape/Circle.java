package com.weibo.keeplooking.shape;

import com.weibo.keeplooking.util.ToolBoxUtils;

/**
 * Circle implementation of shape.
 * 
 * @author Johnny
 */
public class Circle implements Shape {
    private static final double PI = Math.PI;

    public static int COUNT = 0;

    private final Point center;
    private final double radius;

    public Circle(final Point center, final double radius) {
        this.center = center;
        this.radius = radius;
        COUNT++;
    }

    @Override
    public double area() {
        return PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return PI * 2 * radius;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Circle)) {
            return false;
        }
        Circle other = (Circle) obj;
        return center.equals(other.center)
                && Double.compare(radius, other.radius) == 0;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + center.hashCode();
        result = result * 31 + ToolBoxUtils.hashCodeForLong(radius);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("center: ");
        sb.append(center.toString());
        sb.append(" , radius: ");
        sb.append(radius);
        return sb.toString();
    }

    /**
     * Check if the given point is on the circle.
     * 
     * @param point
     *        point to check
     * @return true if the point is on the circle, false else
     */
    public boolean onCircle(Point point) {
        return Double.compare(radius, Math.abs(distanceToCenter(point))) == 0;
    }

    private double distanceToCenter(Point point) {
        return Point.distance(center, point);
    }

}
