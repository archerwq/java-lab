package com.weibo.keeplooking.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Various utility methods.
 * 
 * @author Johnny
 *
 */
public class ToolBoxUtils {

    /**
     * Generate hash code for long value.
     * 
     * @param value
     *        long value
     * @return hash code for the value
     */
    public static int hashCodeForLong(double value) {
        long valueLong = Double.doubleToLongBits(value);
        return (int) (valueLong ^ (valueLong >>> 32));
    }

    /**
     * Generate MD5 value for message.
     * 
     * @param message
     * @return MD5 value
     * @throws NoSuchAlgorithmException
     */
    public static String md5Digest(String message)
            throws NoSuchAlgorithmException {
        byte[] defaultBytes = message.getBytes();
        MessageDigest algorithm = MessageDigest.getInstance("MD5");
        algorithm.reset();
        algorithm.update(defaultBytes);
        byte[] messageDigest = algorithm.digest(); // 16 bytes

        /* to Hex */
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            String hex = Integer.toHexString(0xff & messageDigest[i]);
            if (hex.length() == 1) {
                hexString.append("0");
            }
            hexString.append(hex);
        }

        return hexString.toString().toUpperCase();
    }

}
