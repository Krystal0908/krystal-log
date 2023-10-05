package com.krystal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * @author krystal
 * @create 2023-10-05 21:46
 */
public class JCLTest {
    @Test
    public void testQuick(){
        //获取log日志记录器对象
        Log log = LogFactory.getLog(JCLTest.class);
        //日志记录器
        log.info("hello jcl");
    }


}
