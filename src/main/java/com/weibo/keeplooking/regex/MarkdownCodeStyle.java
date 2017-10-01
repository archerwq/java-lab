package com.weibo.keeplooking.regex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

/**
 * Change code style of blog written with markdown.
 * 
 * @author Johnny
 */
public class MarkdownCodeStyle {

    private String blog;

    @Before
    public void setUp() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/Users/qwang/dev/git/archerwq.github.io/_posts/2015-9-28-Git学习笔记.md"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            blog = sb.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    @Test
    public void changeStyle() throws IOException {
        String regexp = "^`.*`\\s*#";
        Pattern pattern = Pattern.compile(regexp, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(blog);
        while (matcher.find()) {
            String row = matcher.group();
            System.out.println(row);
        }
    }

}
