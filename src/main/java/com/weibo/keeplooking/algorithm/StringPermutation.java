package com.weibo.keeplooking.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Permutations of a string.
 * 
 * @author Johnny
 * 
 */
public class StringPermutation {

    /**
     * Caculate all the permutations for a string with recursive method.
     * 
     * @param str
     *        input string
     * @return list of all the permutations for the input string, null if str is
     *         null or empty
     */
    public List<String> caculatePerms(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        List<String> perms = new ArrayList<String>();
        if (str.length() == 1) {
            perms.add(str);
            return perms;
        }
        char first = str.charAt(0);
        String remainder = str.substring(1);
        List<String> subPerms = caculatePerms(remainder);
        for (String subPerm : subPerms) {
            int length = subPerm.length();
            for (int i = 0; i <= length; i++) {
                String perm = insertBefore(subPerm, i, first);
                perms.add(perm);
            }
        }
        return perms;
    }

    /**
     * Insert a char into string before the specific index
     * 
     * @param str
     *        string to be inserted into
     * @param index
     *        index before which to insert
     * @param c
     *        char to insert
     * @return string after the insertion
     */
    private String insertBefore(String str, int index, char c) {
        char[] chars = new char[str.length() + 1];
        for (int i = 0, j = 0; i < chars.length; i++) {
            if (i == index) {
                chars[i] = c;
            } else {
                chars[i] = str.charAt(j);
                j++;
            }
        }
        return new String(chars);
    }

    @Test
    public void testCaculatePerms() {
        List<String> permutations = caculatePerms("abcdefgh");
        Assert.assertTrue(permutations.size() > 100);

        for (String perm : permutations) {
            System.out.println(perm);
        }
    }
}
