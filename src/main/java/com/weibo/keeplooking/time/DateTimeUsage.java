package com.weibo.keeplooking.time;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

/**
 * Demo for data & time API.
 *
 * @author Johnny
 *
 */
public class DateTimeUsage {
    private static final String ISO_8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    @Test
    public void testCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2039, 2, 19, 00, 00, 00);
        long time = calendar.getTimeInMillis();
        System.out.println(time);
        System.out.println(new Date(time));
    }

    @Test
    public void testDate() {
        System.out.println("binlog ->      " + new Date(1471597506000L));
        System.out.println("cdc-slave ->   " + new Date(1471597506428L));
        System.out.println("transformer -> " + "2016-08-19 17:21:03,833");
        System.out.println("now ->      " + new Date());
    }

    @Test
    public void testDate2() {
        System.out.println("binlog ->      " + new Date(1471697084397L));
    }

    @Test
    public void testIso8601() {
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        System.out.println(getIso8601DateTime(timestamp));
        System.out.println(getIso8601DateTime(-1L));
    }

    private String getIso8601DateTime(Long timestamp) {
        Date date = new Date(timestamp);
        TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
        SimpleDateFormat format = new SimpleDateFormat(ISO_8601_PATTERN);
        format.setTimeZone(tz);
        return format.format(date);
    }

    @Test
    public void testISO8601() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ");
        formatter.setTimeZone(tz);
        System.out.println(formatter.format(new Date()));
    }

    @Test
    public void testISO8601Again() {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        System.out.println(LocalDateTime.parse("2016-10-10T14:03:04.095", DateTimeFormatter.ISO_DATE_TIME));
    }

}
