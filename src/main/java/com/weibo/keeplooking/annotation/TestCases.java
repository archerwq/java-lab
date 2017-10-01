package com.weibo.keeplooking.annotation;


@TestInfo(priority = TestInfo.Priority.HIGH, tags = {"test", "demo"}, lastModified = "2015-08-30 18:57:00")
class TestCases {

    @Test(expected = {ArithmeticException.class})
    public void devideByZero() {
        System.out.println(1 / 0);
    }

    @Test
    void testA() {
        if (true)
            throw new RuntimeException("This test always failed");
    }

    @SuppressWarnings("unused")
    @Test(enabled = false)
    void testB() {
        if (false)
            throw new RuntimeException("This test always passed");
    }

    @Test(enabled = true)
    void testC() {
        if (10 > 1) {
            // do nothing, this test always passed.
        }
    }

}
