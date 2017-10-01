package com.weibo.keeplooking.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread interrupt demo.
 * 
 * @author Johnny
 */
public class InterruptTest {
    private static final Logger logger = LoggerFactory
            .getLogger(InterruptTest.class);

    private Thread producer;

    @Before
    public void setup() {
        final BlockingQueue<String> queue = new LinkedBlockingQueue<String>(5);

        producer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        queue.put("data");
                        logger.info("Data was added to queue.");
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        logger.error("queue.put was interrupted.", e);
                    }
                }
            }
        });
    }

    @Test
    public void testInterrupt() throws InterruptedException {
        producer.start();
        producer.interrupt(); // will continue adding data after interruption
        Thread.sleep(5000);
    }

}
