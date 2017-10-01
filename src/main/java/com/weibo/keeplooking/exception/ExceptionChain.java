package com.weibo.keeplooking.exception;

import java.util.Random;

import org.junit.Test;

/**
 * Translate low level checked exception into high level unchecked exception.
 * 
 * @author Johnny
 * 
 */
public class ExceptionChain {

    public void doSomething() {
        try {
            Random rand = new Random();
            int value = rand.nextInt(10);
            if (value < 5) {
                throw new LowLevelExceptionA();
            } else {
                throw new LowLevelExceptionB();
            }
        } catch (LowLevelExceptionA lea) {
            // option A: Throwable.initCause(Throwable cause)
            throw (HighLevelException) new HighLevelException("rand < 5")
                    .initCause(lea);
        } catch (LowLevelExceptionB leb) {
            // option B: Throwable(String message, Throwable cause) or
            // Throwable(Throwable cause)
            throw new HighLevelException("rand >= 5", leb);
        }

    }

    public static void main(String[] args) {
        new ExceptionChain().doSomething();
    }

    @Test(expected = HighLevelException.class)
    public void test() {
        doSomething();
    }
}


@SuppressWarnings("serial")
class LowLevelExceptionA extends Exception {

}


@SuppressWarnings("serial")
class LowLevelExceptionB extends Exception {

}


@SuppressWarnings("serial")
class HighLevelException extends RuntimeException {
    public HighLevelException(String message) {
        super(message);
    }

    public HighLevelException(String message, Throwable t) {
        super(message, t);
    }
}
