package com.weibo.keeplooking.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MetricsUsage {
    private static MetricRegistry metrics = new MetricRegistry();
    private volatile long duration = 0;

    @Test
    public void meter() {
        startReport();
        Meter requests = metrics.meter("requests-rate");
        for (int i = 0; i < 10; i++) {
            requests.mark();
            waitAtMost1Seconds();
            waitAtMost1Seconds();
        }
        waitSeconds(50);
    }

    @Test
    public void count() {
        startReport();
        Counter requests = metrics.counter("requests-count");
        for (int i = 0; i < 10; i++) {
            requests.inc();
            waitAtMost1Seconds();
            waitAtMost1Seconds();
        }
        waitSeconds(20);
    }

    @Test
    public void guage() {
        startReport();
        metrics.register(MetricRegistry.name("requests-time"),
                new Gauge<Long>() {
                    @Override
                    public Long getValue() {
                        return duration;
                    }
                });
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            waitAtMost1Seconds();
            waitAtMost1Seconds();
            duration = System.currentTimeMillis() - start;
        }

        waitSeconds(500);
    }

    @Test
    public void time() {
        startReport();
        Timer requests = metrics.timer("requests-time");
        for (int i = 0; i < 10; i++) {
            final Timer.Context context = requests.time();
            waitAtMost1Seconds();
            waitAtMost1Seconds();
            context.stop();
        }
        waitSeconds(50);
    }

    @Test
    public void histgram() {
        startReport();
        Histogram requests = metrics.histogram("requests-time-distribution");
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            waitAtMost1Seconds();
            waitAtMost1Seconds();
            requests.update(System.currentTimeMillis() - start);
        }
        waitSeconds(50);
    }

    private void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    private void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
        }
    }

    private void waitAtMost1Seconds() {
        Random rand = new Random();
        try {
            Thread.sleep(rand.nextInt(1000));
        } catch (InterruptedException e) {
        }
    }

}
