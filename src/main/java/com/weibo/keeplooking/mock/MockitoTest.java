package com.weibo.keeplooking.mock;

import static org.mockito.Mockito.*;

import java.util.LinkedList;

import org.junit.Test;

public class MockitoTest {

    @SuppressWarnings("rawtypes")
    @Test
    public void test() {
        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        when(mockedList.get(0)).thenReturn("first").thenReturn("first again");

        // the following prints "first"
        System.out.println(mockedList.get(0));

        // the following prints "first again"
        System.out.println(mockedList.get(0));

        // the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));
    }

}
