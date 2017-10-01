package com.weibo.keeplooking.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo exception in executor service.
 * 
 * @author Johnny
 */
public class FutureExcepion {
    private static final Logger logger = LoggerFactory
            .getLogger(FutureExcepion.class);

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @SuppressWarnings("rawtypes")
    @Test
    public void test() {
        Future future = executor.submit(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("Can you see me?");
            }
        });

        try {
            future.get();
        } catch (InterruptedException e) {
            logger.error("Thread was interrupted.", e);
        } catch (ExecutionException e) {
            logger.error("Exception was throw in executor service.", e);
        } finally {
            executor.shutdown();
        }
    }

}
