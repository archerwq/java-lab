package com.weibo.keeplooking.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Annotation process demo.
 * 
 * @author Johnny
 *
 */
public class AnnotationDemo {

    private static final Logger LOG = LoggerFactory
            .getLogger(AnnotationDemo.class);

    public static void main(String[] args) {
        LOG.info("Testing...");

        if (TestCases.class.isAnnotationPresent(TestInfo.class)) {
            TestInfo testInfo = TestCases.class.getAnnotation(TestInfo.class);
            LOG.info("Priority: {}", testInfo.priority());
            LOG.info("Created by: {}", testInfo.createdBy());
            LOG.info("Tags: {}", StringUtils.join(testInfo.tags(), ","));
            LOG.info("Last modified: {}", testInfo.lastModified());
        }

        int total = 0, passed = 0, failed = 0, ignored = 0;

        Method[] methods = TestCases.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                total++;
                Test test = method.getAnnotation(Test.class);

                // for ignored case
                if (!test.enabled()) {
                    ignored++;
                    LOG.info("{} - Test {}.{}() - ignored", total,
                            method.getDeclaringClass(), method.getName());
                    continue;
                }

                try {
                    TestCases cases = new TestCases();
                    method.invoke(cases);
                } catch (InvocationTargetException e) {
                    boolean expected = false;
                    Throwable cause = e.getTargetException();
                    Class<? extends Exception>[] expectedExceptions = test
                            .expected();
                    for (Class<? extends Exception> expectedException : expectedExceptions) {
                        if (expectedException.isInstance(cause)) {
                            expected = true;
                            break;
                        }
                    }
                    if (!expected) {
                        failed++;
                        fail(total, method, e);
                        continue;
                    }
                } catch (Exception e) {
                    failed++;
                    fail(total, method, e);
                    continue;
                }

                passed++;
                LOG.info("{} - Test {}.{}() - passed", total,
                        method.getDeclaringClass(), method.getName());
            }
        }

        LOG.info("Result: Total {}, Passed {}, Failed {}, Ignored {}", total,
                passed, failed, ignored);
    }

    private static void fail(int caseNo, Method method, Exception e) {
        LOG.error(
                String.format("%d - Test %s.%s() - failed", caseNo,
                        method.getDeclaringClass(), method.getName()), e);
    }

}
