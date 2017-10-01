package com.weibo.keeplooking.reactivex;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import rx.Observable;
import rx.functions.Action1;

// def myOnNext = { item -> /* do something useful with item */ };
// def myError = { throwable -> /* react sensibly to a failed call */ };
// def myComplete = { /* clean up after the final response */ };
// def myObservable = someMethod(itsParameters);
// myObservable.subscribe(myOnNext, myError, myComplete);
// go on about my business

public class RxHelloWorld {

    public void helloworld(String... names) {
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(String.format("[%s]: Hello %s", Thread.currentThread().getName(), s));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println(String.format("[%s]: Bye %s", Thread.currentThread().getName(), s));
            }
        });

        System.out.println(String.format("[%s]: go on about my business...", Thread.currentThread().getName()));
    }

    public void timeInterval() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        Observable.interval(1, TimeUnit.SECONDS).subscribe(n -> {
            System.out.println(String.format("[%s]: %d -> Hello World!", Thread.currentThread().getName(), n));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println(String.format("[%s]: %d -> Bye World!", Thread.currentThread().getName(), n));
            latch.countDown();
        });

        System.out.println(String.format("[%s]: go on about my business...", Thread.currentThread().getName()));
        latch.await();
    }

    @Test
    public void test() throws InterruptedException {
        // helloworld("William", "Leo");
        timeInterval();
    }

}
