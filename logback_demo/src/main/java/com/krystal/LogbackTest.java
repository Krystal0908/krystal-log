package com.krystal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krystal
 * @create 2023-10-06 11:55
 */
public class LogbackTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(LogbackTest.class);

    //快速入门
    @Test
    public void test01(){
        for (int i = 1; i <=  10000; i++) {

            //日志输出
            LOGGER.error("error");
            LOGGER.warn("warn");
            LOGGER.info("info");
            LOGGER.debug("debug");//默认级别
            LOGGER.trace("trace");
        }


    }
}
