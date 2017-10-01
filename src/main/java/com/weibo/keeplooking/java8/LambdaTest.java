package com.weibo.keeplooking.java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaTest {

    @Test
    public void lambda() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.forEach((x) -> {
            System.out.println(x);
        });
        list.stream().filter(x -> x % 2 == 0)
                .forEach(x -> System.out.println(x));
    }

    @Test
    public void testToMapCollector() {
        System.out.println(Arrays.asList("A", "B", "C").stream()
                .collect(Collectors.toMap(str -> str, str -> str + "###")));
    }

    @Test
    public void testEmptyListStream() {
        System.out.println(Arrays.asList().stream()
                .flatMap(str -> Stream.of(str + "1", str + "2"))
                .collect(Collectors.toList()));
    }

}
