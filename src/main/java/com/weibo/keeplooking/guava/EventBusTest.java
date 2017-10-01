package com.weibo.keeplooking.guava;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventBusTest {

    private EventBus eventBus;

    class EventBusChangeRecorder {
        @Subscribe
        public void recordChange(String change) {
            System.out.println(change);
        }
    }

    class EventBusRootChangeRecorder {
        @Subscribe
        public void recordChange(Object change) {
            System.out.println(change);
        }
    }

    @Before
    public void init() {
        eventBus = new EventBus();
        eventBus.register(new EventBusChangeRecorder());
        eventBus.register(new EventBusRootChangeRecorder());
    }

    @Test
    public void test() {
        eventBus.post("Change A happend.");
        eventBus.post("Change B happend.");
    }

}
