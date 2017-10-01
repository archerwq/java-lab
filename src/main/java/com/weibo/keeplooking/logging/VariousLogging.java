package com.weibo.keeplooking.logging;

import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Demo for slf4j + logback, log4j, j.u.l, common-logging
 * 
 * @author johnny.wang
 *
 */
public class VariousLogging {

    private static final java.util.logging.Logger jucLogger = java.util.logging.Logger.getLogger(VariousLogging.class
            .getName());
    private static final org.apache.log4j.Logger log4jLogger = org.apache.log4j.Logger.getLogger(VariousLogging.class);
    private static final org.apache.commons.logging.Log commonLogger = org.apache.commons.logging.LogFactory
            .getLog(VariousLogging.class);
    private static final org.slf4j.Logger slf4jLogger = org.slf4j.LoggerFactory.getLogger(VariousLogging.class);

    static {
        // optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger(); // (since SLF4J 1.6.5)

        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();
    }

    private void logWithJuc() {
        jucLogger.info("java util logging...");
    }

    private void logWithLog4j() {
        log4jLogger.info("log4j...");
    }

    private void logWithCommonLogging() {
        commonLogger.info("common logging...");
    }

    private void logWithSlf4j() {
        slf4jLogger.info("slf4j...");
    }

    @Test
    public void testLogging() {
        logWithJuc();
        logWithLog4j();
        logWithCommonLogging();
        logWithSlf4j();
    }

}
