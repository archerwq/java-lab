package com.weibo.keeplooking.io;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * IO usage demo.
 *
 * @author Johnny
 *
 */
public class InputOutputDemo {

    @Test
    public void yuan() throws Exception {
        Map<String, Integer> result = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader("/tmp/yuan_data.txt"));
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] temp = line.split("\\|");
            String[] options = temp[0].split(",");
            int count = Integer.parseInt(temp[1]);
            Arrays.asList(options).forEach(option -> {
                Integer totalCount = result.get(option);
                if (totalCount == null) {
                    result.put(option, count);
                } else {
                    result.put(option, totalCount + count);
                }
            });
        }
        System.out.println(result);
        br.close();
    }

    @Test
    public void calculateCount() throws Exception {
        Map<String, Integer> eventCountMap = new HashMap<>();
        File eventDataDir = new File("/tmp/event_count");
        File[] files = eventDataDir.listFiles();
        for (File file : files) {
            System.out.println(file);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            int row = 0;
            while ((line = br.readLine()) != null) {
                row++;
                if (row > 5) {
                    line = line.replaceAll("\"", "");
                    String[] pair = line.split(",");
                    String event = pair[0];
                    int count = Integer.parseInt(pair[1]);
                    if (eventCountMap.get(event) == null) {
                        eventCountMap.put(event, count);
                    } else {
                        int initCount = eventCountMap.get(event);
                        eventCountMap.put(event, initCount + count);
                    }
                }
            }
            br.close();
        }

        Map<String, String> eventNameMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(
                "/tmp/2017-06-11_21-01-59.csv"));
        String line = null;
        int row = 0;
        while ((line = br.readLine()) != null) {
            row++;
            if (row > 5) {
                line = line.replaceAll("\"", "");
                String[] pair = line.split(",");
                eventNameMap.put(pair[0], pair[1]);
            }
        }
        br.close();

        eventCountMap.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .forEach(entry -> System.out.println(entry.getKey() + ", " + eventNameMap.get(entry.getKey()) + ", " + entry.getValue()));
    }

    @Test
    public void readFileFromClasspath() throws IOException {
        // From ClassLoader, all paths are "absolute" already - there's no context
        // from which they could be relative. Therefore you don't need a leading slash.
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("logback.xml");
        Assert.assertNotNull(in);

        // From Class, the path is relative to the package of the class unless
        // you include a leading slash, so if you don't want to use the current
        // package, include a slash like this:
        in = this.getClass().getResourceAsStream("/logback.xml");
        Assert.assertNotNull(in);

        String text = IOUtils.toString(in);
        Assert.assertNotNull(text);
        System.out.println(text);
    }

    @Test
    public void testScanner() {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int i = 0; i < t; i++) {
            String a = in.next();
            String b = in.next();
            System.out.println(a + b);
        }
        in.close();
    }

}
