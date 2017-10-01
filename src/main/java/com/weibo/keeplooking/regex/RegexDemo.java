package com.weibo.keeplooking.regex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class RegexDemo {

    private String text;

    @Before
    public void setUp() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("D:/tmp/bus_details.txt"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null && !"".equals(line)) {
                sb.append(line + "\n");
            }
            text = sb.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    @Test
    public void parseHtmlTable() throws IOException {
        String regexp1 = "<tr class=\"bus_item\" >(.*?)</tr>";
        String regexp2 = "<td>(.*?)</td>";
        Pattern pattern1 = Pattern.compile(regexp1, Pattern.DOTALL);
        Pattern pattern2 = Pattern.compile(regexp2, Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(text);
        while (matcher1.find()) {
            String row = matcher1.group();
            Matcher matcher2 = pattern2.matcher(row);
            String[] cells = new String[7];
            int i = 0;
            while (matcher2.find()) {
                cells[i] = matcher2.group(1);
                System.out.println(cells[i]);
                i++;
            }
        }
    }

}
