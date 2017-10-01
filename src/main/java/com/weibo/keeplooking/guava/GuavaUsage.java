package com.weibo.keeplooking.guava;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

/**
 * Usage demo for Guava.
 * 
 * @author johnny.wang
 *
 */
public class GuavaUsage {

    @Test
    public void testStrJoin() {
        List<String> parts = new ArrayList<String>();
        String allParts = Joiner.on("|").join(parts);
        Assert.assertEquals("", allParts);
        parts.add("part_1");
        parts.add("part_2");
        parts.add("part_3");
        allParts = Joiner.on("|").join(parts);
        Assert.assertEquals("part_1|part_2|part_3", allParts);
    }

}
