package com.weibo.keeplooking.exception;

/**
 * Demo: self-defined exception should be constructed with more details.
 * 
 * @author Johnny
 *
 */
public class IndexOutOfBoundsException extends RuntimeException {
    private static final long serialVersionUID = 4639437332237653902L;
    private int lowerBound;
    private int upperBound;
    private int index;

    /**
     * Construct an IndexOutOfBoundsException
     * 
     * @param lowerBound
     *        the lowest legal index value.
     * @param upperBound
     *        the highest legal index value plus one.
     * @param index
     *        the actual index value.
     */
    public IndexOutOfBoundsException(int lowerBound, int upperBound, int index) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.index = index;
    }

    @Override
    public String getMessage() {
        return "Lower bound: " + lowerBound + ", Upper bound: " + upperBound
                + ", Index: " + index;
    }
}
