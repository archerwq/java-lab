package com.weibo.keeplooking.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Options: <br/>
 * <code> -Xms30M -Xmx30M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCTimeStamps -XX:+PrintGCDetails </code>
 * 
 * @author Johnny
 * 
 */
public class GCSimulator {
    private static final int _1MB = 1024 * 1024;

    public void testGcBehavior(String[] args) throws InterruptedException {
        List<byte[]> list = new ArrayList<byte[]>();
        System.out.println("Adding 7M...");
        list.add(new byte[7 * _1MB]);
        System.out.println("Done.");
        System.out.println("Adding 1M...");
        list.add(new byte[1 * _1MB]);
        System.out.println("Done.");
        System.out.println("Adding 1M...");
        list.add(new byte[1 * _1MB]);
        System.out.println("Done.");
        System.out.println("Adding 1M...");
        list.add(new byte[1 * _1MB]);
        System.out.println("Done.");
        System.out.println("Adding 1M...");
        list.add(new byte[1 * _1MB]);
        System.out.println("Done.");
        System.out.println("Adding 1M...");
        list.add(new byte[1 * _1MB]);
        System.out.println("Done.");
        System.out.println("Adding 1M...");
        list.add(new byte[1 * _1MB]);
        System.out.println("Done.");
        System.out.println("Adding 1M...");
        list.add(new byte[1 * _1MB]);
        System.out.println("Done.");
        System.out.println("Adding 1M...");
        list.add(new byte[1 * _1MB]);
        System.out.println("Done.");
        System.out.println("Adding 1M...");
        list.add(new byte[1 * _1MB]);
        System.out.println("Done.");
        System.out.println("Adding 5M...");
        list.add(new byte[5 * _1MB]);
        System.out.println("Done.");
        System.out.println("Adding 5M...");
        list.add(new byte[5 * _1MB]);
        System.out.println("Done.");
        System.out.println("Adding 5M...");
        list.add(new byte[5 * _1MB]);
        System.out.println("Done.");
    }

}
