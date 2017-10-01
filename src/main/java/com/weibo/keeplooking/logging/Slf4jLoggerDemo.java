package com.weibo.keeplooking.logging;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * API demo for Slf4j Logger.
 *
 * @author Johnny
 */
public class Slf4jLoggerDemo {

    private static final Logger logger = LoggerFactory
            .getLogger(Slf4jLoggerDemo.class);

    @Test
    public void testDiffParams() {
        logger.info("Message only.");
        logger.info("Message with one parameter: {}.", "hello world");
        Exception e = new Exception("Something wrong!");
        logger.error(
                "Message with one parameter: {}, and exception stack trace.", "hello world", e);
        logger.error(
                "Message with one parameter: {}, and exception stack trace.",
                new Object[] {"hello world", e});
        logger.error(
                "Message with two parameter: {} - {}, and exception stack trace.",
                new Object[] {"hello world", "bye world"}, e); // wrong
        logger.error(
                "Message with two parameter: {} - {}, and exception stack trace.",
                new Object[] {"hello world", "bye world", e});
    }

}
