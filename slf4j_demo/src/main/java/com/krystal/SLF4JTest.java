package com.krystal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krystal
 * @create 2023-10-05 22:56
 */
public class SLF4JTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(SLF4JTest.class);

    @Test
    public void test01(){
        //日志输出
        LOGGER.error("error");
        LOGGER.warn("warn");
        LOGGER.info("info");
        LOGGER.debug("debug");
        LOGGER.trace("trace");

        //使用占位符输出日志信息
        String name = "krystal";
        Integer age = 25;
        LOGGER.info("用户:{},{}",name,age);

        //将系统的异常信息输出
        try {
            int i = 1 / 0;
        } catch (Exception e) {
           // e.printStackTrace();
            LOGGER.error("出现异常：",e);
        }

    }
}
