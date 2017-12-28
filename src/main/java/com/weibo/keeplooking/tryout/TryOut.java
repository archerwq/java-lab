package com.weibo.keeplooking.tryout;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.ws.rs.core.UriBuilder;

/**
 * Add code snips here that I want to try out quickly.
 *
 * @author Johnny
 */
public class TryOut {

    private static Logger LOGGER = LoggerFactory.getLogger(TryOut.class);

    @Test
    public void testEmptyStream() {
        System.out.println("result:" + new ArrayList<String>().stream().collect(Collectors.joining(",")));
    }

    @Test
    public void getSysProperties() {
        System.out.println(System.getProperty("jsse.enableSNIExtension"));
    }

    @Test
    public void testUrl() throws URISyntaxException {
        URI uri = new URI("data://www.domain.com");
        System.out.println(uri.getScheme() + "," + uri.getHost() + "," + uri.getPath());
    }

    @SuppressWarnings("unused")
    private String sanitizeUrl(String retUrl) {
        UriBuilder builder = UriBuilder.fromUri(retUrl);
        builder.scheme(null);
        builder.host(null);
        String sanitizedUri = builder.build().toString();
        return sanitizedUri;
    }

    @Test
    public void testSubstring() {
        System.out.println("abc".substring(3, 3));
    }

    @Test
    public void printIntArrayAsString() {
        byte[] input = {80, 79, 83, 84, 32, 47, 122, 117, 111, 114, 97, 47, 110, 101, 119, 45, 115, 117, 98, 115, 99, 114, 105, 112, 116, 105, 111, 110, 32, 72,
                84, 84, 80, 47, 49, 46, 49, 13, 10, 85, 115, 101, 114, 45, 65, 103, 101, 110, 116, 58, 32, 74, 97, 107, 97, 114, 116, 97, 32, 67, 111, 109, 109,
                111, 110, 115, 45, 72, 116, 116, 112, 67, 108, 105, 101, 110, 116, 47, 51, 46, 49, 13, 10, 72, 111, 115, 116, 58, 32, 110, 111, 116, 105, 102,
                121, 46, 100, 101, 118, 46, 103, 114, 97, 100, 119, 101, 108, 108, 46, 110, 101, 116, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45, 76, 101,
                110, 103, 116, 104, 58, 32, 49, 50, 53, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45, 84, 121, 112, 101, 58, 32, 116, 101, 120, 116, 47, 120,
                109, 108, 59, 32, 99, 104, 97, 114, 115, 101, 116, 61, 117, 116, 102, 45, 56, 13, 10, 13, 10, 60, 63, 120, 109, 108, 32, 118, 101, 114, 115,
                105, 111, 110, 61, 34, 49, 46, 48, 34, 32, 101, 110, 99, 111, 100, 105, 110, 103, 61, 34, 85, 84, 70, 45, 56, 34, 63, 62, 60, 99, 97, 108, 108,
                111, 117, 116, 62, 60, 112, 97, 114, 97, 109, 101, 116, 101, 114, 32, 110, 97, 109, 101, 61, 34, 69, 120, 112, 111, 114, 116, 67, 114, 101, 97,
                116, 101, 100, 68, 97, 116, 101, 34, 62, 48, 54, 47, 48, 50, 47, 50, 48, 49, 55, 32, 49, 52, 58, 50, 56, 58, 48, 56, 60, 47, 112, 97, 114, 97,
                109, 101, 116, 101, 114, 62, 10, 60, 47, 99, 97, 108, 108, 111, 117, 116, 62};
        System.out.print(new String(input));

        byte[] input2 = {80, 79, 83, 84, 32, 47, 122, 117, 111, 114, 97, 47, 110, 101, 119, 45, 115, 117, 98, 115, 99, 114, 105, 112, 116, 105, 111, 110, 32,
                72, 84, 84, 80, 47, 49, 46, 49, 13, 10, 85, 115, 101, 114, 45, 65, 103, 101, 110, 116, 58, 32, 74, 97, 107, 97, 114, 116, 97, 32, 67, 111, 109,
                109, 111, 110, 115, 45, 72, 116, 116, 112, 67, 108, 105, 101, 110, 116, 47, 51, 46, 49, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45, 76, 101,
                110, 103, 116, 104, 58, 32, 49, 50, 53, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45, 84, 121, 112, 101, 58, 32, 116, 101, 120, 116, 47, 120,
                109, 108, 59, 32, 99, 104, 97, 114, 115, 101, 116, 61, 117, 116, 102, 45, 56, 13, 10, 65, 117, 116, 104, 111, 114, 105, 122, 97, 116, 105, 111,
                110, 58, 32, 66, 97, 115, 105, 99, 32, 99, 50, 70, 117, 90, 71, 74, 118, 101, 70, 112, 49, 98, 51, 74, 104, 81, 88, 66, 112, 86, 88, 78, 108,
                99, 106, 112, 119, 89, 88, 78, 122, 100, 50, 57, 121, 90, 68, 69, 121, 77, 119, 61, 61, 13, 10, 72, 111, 115, 116, 58, 32, 110, 111, 116, 105,
                102, 121, 46, 100, 101, 118, 46, 103, 114, 97, 100, 119, 101, 108, 108, 46, 110, 101, 116, 13, 10, 13, 10, 60, 63, 120, 109, 108, 32, 118, 101,
                114, 115, 105, 111, 110, 61, 34, 49, 46, 48, 34, 32, 101, 110, 99, 111, 100, 105, 110, 103, 61, 34, 85, 84, 70, 45, 56, 34, 63, 62, 60, 99, 97,
                108, 108, 111, 117, 116, 62, 60, 112, 97, 114, 97, 109, 101, 116, 101, 114, 32, 110, 97, 109, 101, 61, 34, 69, 120, 112, 111, 114, 116, 67, 114,
                101, 97, 116, 101, 100, 68, 97, 116, 101, 34, 62, 48, 54, 47, 48, 50, 47, 50, 48, 49, 55, 32, 49, 52, 58, 50, 56, 58, 48, 56, 60, 47, 112, 97,
                114, 97, 109, 101, 116, 101, 114, 62, 10, 60, 47, 99, 97, 108, 108, 111, 117, 116, 62};
        System.out.print(new String(input2));
    }

    @Test
    public void testMapCollector() {
        String[] strings = {"hello", "java", "world"};
        Map<String, Integer> result = Arrays.asList(strings).stream().collect(Collectors.toMap(str -> str, str -> str.length()));
        System.out.println(result);
    }

    @Test
    public void testLocales() {
        Arrays.asList(Locale.getAvailableLocales()).forEach(locale -> System.out.println(locale + " | " + locale.getCountry()));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void displayNumber() {
        Locale currentLocale = new Locale("lt", "LT", "");
		Integer quantity = new Integer(123456);
        Double amount = new Double(345987.246);

        NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
        String quantityOut = numberFormatter.format(quantity);
        String amountOut = numberFormatter.format(amount);
        System.out.println(quantityOut + "--->" + currentLocale.toString());
        System.out.println(amountOut + "--->" + currentLocale.toString());
    }

    @Test
    public void testClassName() {
        List<String> list = new ArrayList<>();
        System.out.println(list.getClass().getName());
    }

    @Test
    public void testDecimalRegex() {
        Pattern pattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
        Matcher valueMatcher = pattern.matcher("100.00");
        if (valueMatcher.matches()) {
            System.out.println(valueMatcher.group().trim());
        }
    }

    @Test
    public void testStringSplit() {
        Arrays.asList(Locale.getAvailableLocales()).forEach(locale -> System.out.println(locale + " -> " + locale.getCountry() + ", " + locale.getLanguage()));
        System.out.println(Arrays.asList("NAE$com.domain.symentic$AccountActivated".split("\\$")));

        System.out.println("11e64eef-ad99-4b20-9658-00259058c29c".replaceAll("-", ""));
    }

    @Test
    public void testSleepInExecutorPool() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        threadPool.submit(() -> {
            while (true) {
                System.out.println("start processing...");
                Thread.sleep(1000);
                System.out.println("processing done");
            }
        });

        Thread.sleep(500000);
    }

    @Test
    public void testLocalLanguage() {
        Arrays.asList(Locale.getAvailableLocales()).forEach(locale -> {
            if (!Strings.isNullOrEmpty(locale.toString())) {
                Assert.assertEquals(locale.getLanguage(), locale.toString().substring(0, 2));
                // System.out.println(locale.getLanguage());
                // System.out.println(locale.getDisplayLanguage());

                if (locale.getLanguage().equals("en") || locale.getLanguage().equals("ja") || locale.getLanguage().equals("fr") || locale.getLanguage().equals(
                        "de")) {
                    // System.out.println(locale);
                }
            }
        });

        Arrays.asList(Locale.getISOLanguages()).forEach(language -> {
            // System.out.println(language);
        });

        String[] supportedLangs = new String[] {"en_US", "ja_JP", "fr_FR", "de_DE"};
        for (String lang : supportedLangs) {
            Locale inLocale = Locale.forLanguageTag(lang);
            System.out.println(inLocale);
            System.out.println(inLocale.getDisplayLanguage(inLocale));
        }
    }

    @Test
    public void testLocale() {
        System.out.println(DateFormat.getDateInstance(2, new Locale("lt", "LT")).format(new Date()));
    }

    class Parent {
        Parent() {
            System.out.println(getClass());
        }
    }

    class Child extends Parent {
        Child() {
            super();
        }
    }

    @Test
    public void testConstructor() {
        new Child();
    }

    @Test
    public void testJavaMailApi() {
        try {
            InternetAddress.parse("..asdl;;\\//?^&!@*#(%)<>");
        } catch (AddressException e) {
            e.printStackTrace();
            Assert.assertEquals("..asdl;;\\//?^&!@*#(%)<>", e.getRef());
        }
    }

    @Test
    public void testStackTrace() {
        try {
            System.out.println(divid(5, 0));
        } catch (Exception e) {
            StackTraceElement[] exceptionElements = e.getStackTrace();
            // StackTraceElement[] currentElements = Thread.currentThread().getStackTrace();
            System.out.println(exceptionElements[0].getClassName() + "." + exceptionElements[0].getMethodName());
        }
    }

    private int divid(int a, int b) {
        return a / b;
    }

    @Test
    public void mergeFile() throws Exception {
        Map<String, String> pciMap = this.readMap("/tmp/A.properties");
        Map<String, String> newPCIMap = this.readMap("/tmp/A_new.properties");

        newPCIMap.forEach((table, columns) -> {
            if (!pciMap.containsKey(table)) {
                pciMap.put(table, columns);
            } else {
                String columnsOld = pciMap.get(table);
                pciMap.put(table, this.mergeColumns(columnsOld, columns));
            }
        });

        PrintWriter writer = new PrintWriter(new FileWriter("/tmp/A_merged.properties"));
        pciMap.forEach((table, columns) -> writer.println(table + "=" + columns));
        writer.close();
    }

    private String mergeColumns(String columnsOld, String columnsNew) {
        List<String> list1 = Arrays.asList(columnsOld.split(","));
        list1 = list1.stream().map(column -> column.trim()).collect(Collectors.toList());
        List<String> list2 = Arrays.asList(columnsNew.split(","));
        list2 = list2.stream().map(column -> column.trim()).collect(Collectors.toList());

        List<String> merged = new ArrayList<>();
        merged.addAll(list1);
        list2.forEach(column -> {
            if (!merged.contains(column)) {
                merged.add(column);
            }
        });

        return merged.stream().collect(Collectors.joining(","));
    }

    private Map<String, String> readMap(String filePath) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        Map<String, String> pciMap = new LinkedHashMap<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if (!line.contains("=")) {
                continue;
            }
            String[] temp = line.split("=");
            Preconditions.checkArgument(temp.length == 2);
            pciMap.put(temp[0], temp[1]);
        }
        reader.close();
        return pciMap;
    }

    @Test
    public void testFileIO() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("/tmp/objects.txt"));
        List<String> lines = new ArrayList<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            line = "\"" + line + "\"";
            System.out.println(line);
            lines.add(line);
        }
        reader.close();

        PrintWriter writer = new PrintWriter(new FileWriter("/tmp/objects_formatted.txt"));
        String formatted = lines.stream().collect(Collectors.joining(" "));
        System.out.println(formatted);
        writer.println(formatted);
        writer.close();
    }

    public int locateInsertPosition(String topLevelCategory, Map<String, Integer> topLevelIndexes, int optionCount) {
        checkNotNull(topLevelCategory, "topLevelCategory should not be null");
        checkNotNull(topLevelIndexes, "topLevelIndexes should not be null");

        int currentTopIndex = topLevelIndexes.get(topLevelCategory);
        int nextTopIndex = Integer.MAX_VALUE;
        for (int index : topLevelIndexes.values()) {
            if (index > currentTopIndex && index < nextTopIndex) {
                nextTopIndex = index;
            }
        }
        if (nextTopIndex == Integer.MAX_VALUE) {
            nextTopIndex = optionCount;
        }
        return nextTopIndex - 1;
    }

    @Test
    public void testLocateInsertPosition() {
        Map<String, Integer> topLevelIndexes = new HashMap<String, Integer>();
        topLevelIndexes.put("A", 1);
        topLevelIndexes.put("B", 22);
        topLevelIndexes.put("C", 27);
        Assert.assertEquals(21, locateInsertPosition("A", topLevelIndexes, 37));
        Assert.assertEquals(26, locateInsertPosition("B", topLevelIndexes, 37));
        Assert.assertEquals(36, locateInsertPosition("C", topLevelIndexes, 37));
    }

    @Test
    public void testListAdd() {
        List<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList("A", "B", "C"));
        list.add(0, "D");
        System.out.println(list);
    }

    @Test
    public void testSetEquals() {
        Set<String> s1 = new HashSet<>();
        Set<String> s2 = new HashSet<>();
        Assert.assertTrue(s1.equals(s2));
    }

    @Test
    public void forEachReturn() {
        Arrays.asList(1, 2, 3).forEach(num -> {
            if (num == 2) {
                return;
            }
            System.out.println(num);
        });
    }

    @Test
    public void testBigInteger() {
        try {
            new BigDecimal("2c9380d654a8c6130154a8cd9234000e");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJodaTime() {
        System.out.println(LocalDate.parse(null));
    }

    @Test
    public void testMapOrderring() {
        Map<Integer, String> om = new LinkedHashMap<Integer, String>();
        om.put(11, "one");
        om.put(12, "two");
        om.put(13, "three");
        om.put(14, "four");
        om.put(15, "five");
        om.put(1, "one");
        om.put(2, "two");
        om.put(3, "three");
        om.put(4, "four");
        om.put(5, "five");
        om.put(6, null);
        om.put(null, "seven");

        for (Integer key : om.keySet()) {
            System.out.println(key + " -> " + om.get(key));
        }

        Assert.assertTrue(null == om.get(6));
        Assert.assertTrue(null == om.get(7));
        Assert.assertTrue("seven".equals(om.get(null)));
    }

    @Test
    public void uuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        System.out.println(uuid + ", " + uuid.length());
    }

    @Test
    public void caculateDurationInMs() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
        Date start = format.parse("2016-01-14_11:33:25.675");
        Date end = format.parse("2016-01-14_11:34:10.241");
        System.out.println(end.getTime() - start.getTime());
    }

    @Test
    public void testAny() throws Exception {
        String result = "";
        BufferedReader br = new BufferedReader(new FileReader("/tmp/setting_classes.txt"));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.length() > 0) {
                result += line.trim() + ".class, ";
            }
        }
        br.close();
        System.out.println(result);
    }

    @Test
    public void testRegex() throws ParseException {
        Assert.assertTrue(Pattern.compile("\\d{4}-\\d{2}-\\d{2}")
                .matcher("12015-09-27").matches());
    }

    @Test
    public void testDevide() {
        System.out.println(1 / 0);
    }

    @Test
    public void caculator() {
        System.out.println(1173058 - 589966);
    }

    @Test
    public void sysProperties() {
        Properties properties = System.getProperties();
        properties.list(System.out);
    }

    @Test
    public void testChar() {
        String str = "hello world";
        char c = str.charAt(2);
        System.out.println(c - '0');
    }

    @Test
    public void testListToArray() {
        List<String> seats = null;
        seats = new ArrayList<String>();
        String[] seatsArr = seats.toArray(new String[seats.size()]);
        Assert.assertTrue(seatsArr.length == 0);
        seats.add("first class");
        seatsArr = seats.toArray(new String[seats.size()]);
        Assert.assertTrue(seatsArr.length == 1);
    }

    @Test
    public void testReference() {
        TripInfo trip = new TripInfo();
        List<String> seats = new ArrayList<String>();
        seats.add("first class");
        trip.setSeats(seats);

        List<String> tempSeats = trip.getSeats();
        tempSeats = new ArrayList<String>();

        Assert.assertTrue(tempSeats.size() == 0);
        Assert.assertTrue(trip.getSeats().size() == 1);
    }

    private class TripInfo {
        private List<String> seats;

        public List<String> getSeats() {
            return seats;
        }

        public void setSeats(List<String> seats) {
            this.seats = seats;
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testConcurrentModification() {
        List<Integer> result = new LinkedList<Integer>();
        result.add(1);
        result.add(2);
        Iterator<Integer> it = result.iterator();
        while (it.hasNext()) {
            result.add(3 ^ it.next());
        }
    }

    @SuppressWarnings("deprecation")
	@Test
    public void testIntEqual() {
        Assert.assertTrue((new Integer(1)) == 1);
    }

    @Test(expected = NullPointerException.class)
    public void nullToBoolean() {
        Assert.assertTrue((Boolean) null != false);
    }

    @Test
    public void testIteratorRemove() {
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        List<Integer> numbers = new ArrayList<Integer>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        map.put("numbers", numbers);
        System.out.println(map);

        List<Integer> numbersNew = map.get("numbers");
        Iterator<Integer> it = numbersNew.iterator();
        while (it.hasNext()) {
            if (it.next().equals(3)) {
                it.remove();
            }
        }
        map.put("numbers", numbersNew);
        System.out.println(map);
    }

    @Test
    public void testInstanceof() {
        Assert.assertTrue(new ArrayList<String>() instanceof Collection);
    }

    @Test
    public void testLog4j() {
        LOGGER.error("{} - {}", 1, 2, new Exception("test"));
    }

    @Test(expected = Exception.class)
    public void testThrowException() throws Exception {
        int a = 1, b = 0;
        try {
            System.out.println(a / b);
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void checkDuplicated() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(
                "D:/tmp/es_orders.txt"));
        Set<String> orderSet = new HashSet<String>();
        Map<String, Integer> orderMap = new HashMap<String, Integer>();
        String line = null;
        while (true) {
            line = br.readLine();
            System.out.println(line);
            if (line == null || "".equals(line)) {
                break;
            }
            line = line.trim();
            orderSet.add(line);
            if (orderMap.get(line) != null) {
                Integer count = orderMap.get(line) + 1;
                orderMap.put(line, count);
            } else {
                orderMap.put(line, 1);
            }
        }
        br.close();

        for (Entry<String, Integer> entry : orderMap.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
        }

        System.out.println(orderSet.size());
    }

    @Test
    public void diffOrderNos() throws IOException {
        List<String> dbOrders = new LinkedList<String>();
        List<String> esOrders = new LinkedList<String>();

        BufferedReader br = new BufferedReader(new FileReader(
                "D:/tmp/es_orders.txt"));
        String line = null;
        while (true) {
            line = br.readLine();
            System.out.println(line);
            if (line == null || "".equals(line)) {
                break;
            }
            line = line.trim();
            dbOrders.add(line);
        }
        br.close();

        br = new BufferedReader(new FileReader("D:/tmp/online_orders.txt"));
        line = null;
        while (true) {
            line = br.readLine();
            System.out.println(line);
            if (line == null || "".equals(line)) {
                break;
            }
            line = line.trim();
            esOrders.add(line);
        }
        br.close();

        System.out.println("DB count: " + dbOrders.size());
        System.out.println("ES count: " + esOrders.size());

        for (String orderNo : dbOrders) {
            if (!esOrders.contains(orderNo)) {
                System.out.println("in excel but not in es -> " + orderNo);
            }
        }

        for (String orderNo : esOrders) {
            if (!dbOrders.contains(orderNo)) {
                System.out.println("in es but not in excel -> " + orderNo);
            }
        }
    }

    @Test
    public void testSumPrice() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(
                "D:/tmp/xcslw_es_price.txt"));
        String line = null;
        float sum = 0;
        while (true) {
            line = br.readLine();
            System.out.println(line);
            if (line == null || "".equals(line)) {
                break;
            }
            float price = Float.parseFloat(line);
            sum += price;
        }
        br.close();
        System.out.println("SUM: " + sum);
    }

    @Test
    public void parseFloat() {
        Float.parseFloat("");
    }

    @Test
    public void shortToInt() {
        Short num = Short.parseShort("1");
        Assert.assertTrue(num instanceof Short);
        int num_new = (int) num;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("num_new", num_new);
        Assert.assertTrue(map.get("num_new") instanceof Integer);
    }

    @Test
    public void subList() {
        List<String> orderNoList = new ArrayList<String>();
        orderNoList.add("1");
        orderNoList.add("2");
        orderNoList.add("3");
        orderNoList.add("4");
        orderNoList.add("5");
        orderNoList.add("6");
        orderNoList.add("7");
        orderNoList.add("8");
        orderNoList.add("9");
        orderNoList.add("10");

        int batchSize = 11;
        int count = orderNoList.size();
        int batchCount = (count % batchSize == 0 ? count / batchSize : count
                / batchSize + 1);
        for (int i = 0; i < batchCount; i++) {
            int begin = i * batchSize;
            int end = (count - begin) > batchSize ? begin + batchSize : count;
            List<String> subOrderNoList = orderNoList.subList(begin, end);
            System.out.println(subOrderNoList);
        }
    }

}
