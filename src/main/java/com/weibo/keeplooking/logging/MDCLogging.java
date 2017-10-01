package com.weibo.keeplooking.logging;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Demo for MDC.
 * 
 * @author Johnny
 *
 */
public class MDCLogging {

    protected static Logger logger = LoggerFactory.getLogger(MDCLogging.class);

    @Before
    public void setup() {
        MDC.put("t_source", "pc_www");
        MDC.put("t_global_id", "a49ba55b6c58be16aa30bafa55f30b71");
        MDC.put("t_trace_id", "fbfc9a5f996f4e819fe055140784a5aa");
        MDC.put("t_referer", "http://xxx.yyy.com/stationToStation.htm");
        MDC.put("t_user_nickname", "johnny");
        MDC.put("t_user_ip", "10.86.140.250");
        MDC.put("t_order_no", "");
        MDC.put("t_action", "search");
        MDC.put("t_content", "深圳-乌鲁木齐-20150228");
    }

    @Test
    public void testMDC() {
        logger.info("MDC testing...");
    }

}
