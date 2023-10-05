package com.krystal;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.junit.Test;

/**
 * @author krystal
 * @create 2023-10-04 22:47
 */
public class Log4jTest {
    //快速入门
    @Test
    public void testQuick(){
        //开启log4j内置日志记录
        LogLog.setInternalDebugging(true);

        //初始化配置信息，在入门案例中暂不使用配置文件
//        BasicConfigurator.configure();

        //获取日志记录器对象
        Logger logger = Logger.getLogger(Log4jTest.class);

        //日志记录输出
        logger.info("hello log4j");
        
//        for (int i = 1; i <=  100; i++) {
            //日志级别
            logger.fatal("fatal");  // 严重错误，一般会造成系统崩溃和终止运行

            logger.error("error");  // 错误信息，但不会影响系统运行
            logger.warn("warn");    // 警告信息，可能会发生问题
            logger.info("info");    // 程序运行信息，数据库的连接、网络、IO操作等
            logger.debug("debug");  // 调试信息，一般在开发阶段使用，记录程序的变量、参数等

            logger.trace("trace");  // 追踪信息，记录程序的所有流程信息
//        }

        //再创建一个日志记录器对象
        Logger logger1 = Logger.getLogger(Logger.class);

        //日志级别
        logger1.fatal("fatal logger1");  // 严重错误，一般会造成系统崩溃和终止运行

        logger1.error("error logger1");  // 错误信息，但不会影响系统运行
        logger1.warn("warn logger1");    // 警告信息，可能会发生问题
        logger1.info("info logger1");    // 程序运行信息，数据库的连接、网络、IO操作等
        logger1.debug("debug logger1");  // 调试信息，一般在开发阶段使用，记录程序的变量、参数等

        logger1.trace("trace logger1");  // 追踪信息，记录程序的所有流程信息


    }

}





















