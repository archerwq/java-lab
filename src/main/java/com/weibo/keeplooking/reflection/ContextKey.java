package com.weibo.keeplooking.reflection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class ContextKey {

    private static final Pattern REGEX_CONTEXT_KEY = Pattern.compile("\\w+\\.\\w+");

    @Test
    public void testADotB() {
        Matcher matcher = REGEX_CONTEXT_KEY.matcher("Account.Id");
        Assert.assertTrue(matcher.matches());
    }

}
