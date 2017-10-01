package com.weibo.keeplooking.shape;

import com.weibo.keeplooking.util.ToolBoxUtils;

/**
 * Point, immutable.
 * 
 * @author Johnny
 */
public class Point implements Comparable<Point> {

    private final double x;
    private final double y;

    public Point(final Double x, final Double y) {
        this.x = x;
        this.y = y;
    }

    public Point(final Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(x);
        sb.append(",");
        sb.append(y);
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point other = (Point) obj;
        return Double.compare(x, other.x) == 0
                && Double.compare(y, other.y) == 0;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + ToolBoxUtils.hashCodeForLong(x);
        result = result * 31 + ToolBoxUtils.hashCodeForLong(y);
        return result;
    }

    /**
     * Compare by x coordination, inconsistent with equals.
     */
    @Override
    public int compareTo(Point other) {
        return Double.compare(x, other.x);
    }

    /**
     * Caculate the distance of two points.
     * 
     * @param point1
     * @param point2
     * @return the distance of two points
     */
    public static double distance(Point point1, Point point2) {
        if (point1 == null || point2 == null) {
            throw new IllegalArgumentException("point1=" + point1 + ", point2="
                    + point2);
        }
        double xGap = point1.x - point2.x;
        double yGap = point1.y - point2.y;
        return Math.sqrt(xGap * xGap + yGap * yGap);
    }

}
