package com.krystal;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author krystal
 * @create 2023-10-05 23:42
 */
public class Log4jTest {
    //定义log4j日志记录器
    public static final Logger LOGGER = Logger.getLogger(Log4jTest.class);

    //测试桥接器
    @Test
    public void test01(){
        LOGGER.info("hello log4j");
    }

}
