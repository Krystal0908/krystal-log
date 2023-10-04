package com.krystal;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.*;

/**
 * @author krystal
 * @create 2023-10-03 22:15
 */
public class JULTest {
    //快速入门
    @Test
    public void testQuick(){
        //1.获取日志记录对象
        Logger logger = Logger.getLogger("com.krystal.JULTest");//com.krystal.JULTest唯一标识

        //2.日志记录输出
        logger.info("hello jul");

        //通用方法进行日志记录
        logger.log(Level.INFO,"info msg");

        //通过占位符输出变量值
        String name = "krystal";
        Integer age = 27;

        logger.log(Level.INFO,"用户信息:{0}，{1}",new Object[]{name,age});

    }

    //日志级别
    @Test
    public void testLogLevel(){
        //1.获取日志记录对象
        Logger logger = Logger.getLogger("com.krystal.JULTest");//com.krystal.JULTest唯一标识

        //2.日志记录输出
        logger.severe("server");
        logger.warning("warning");
        logger.info("info");//jul默认的日志级别是 info

        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

    //自定义日志级别
    @Test
    public void testLogConfig() throws IOException {
        //1.获取日志记录对象
        Logger logger = Logger.getLogger("com.krystal.JULTest");//com.krystal.JULTest唯一标识

        //关闭系统默认配置
        logger.setUseParentHandlers(false);

        //自定义配置日志级别

        //创建ConsoleHandler
        ConsoleHandler consoleHandler = new ConsoleHandler();

        //创建简单格式转换对象
        SimpleFormatter simpleFormatter = new SimpleFormatter();

        //进行关联
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);

        //配置日志级别
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);

        //创建FileHandler文件输出
        FileHandler fileHandler = new FileHandler("D:\\Study\\JavaFileOutPut\\logs\\jul.log");
        fileHandler.setFormatter(simpleFormatter);

        //进行关联
        fileHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);


        //2.日志记录输出
        logger.severe("server");
        logger.warning("warning");
        logger.info("info");//jul默认的日志级别是 info

        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

    @Test
    //Logger对象父子关系
    public void testLogParent(){
        //1.获取日志记录对象
        Logger logger1 = Logger.getLogger("com.krystal");
        Logger logger2 = Logger.getLogger("com");

        //测试
        System.out.println(logger1.getParent() == logger2);

        //所有日志记录器的顶级父元素 LogManager$RootLogger,name：“”
        System.out.println("logger2 Parent"+logger2.getParent() + ",name:" + logger2.getParent().getName());

        //关闭系统默认配置
        logger2.setUseParentHandlers(false);

        //设置logger2的日志级别
        //自定义配置日志级别
        //创建ConsoleHandler
        ConsoleHandler consoleHandler = new ConsoleHandler();

        //创建简单格式转换对象
        SimpleFormatter simpleFormatter = new SimpleFormatter();

        //进行关联
        consoleHandler.setFormatter(simpleFormatter);
        logger2.addHandler(consoleHandler);

        //配置日志级别
        logger2.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);

        //2.日志记录输出
        logger1.severe("server");
        logger1.warning("warning");
        logger1.info("info");//jul默认的日志级别是 info

        logger1.config("config");
        logger1.fine("fine");
        logger1.finer("finer");
        logger1.finest("finest");
    }

    @Test
    //加载自定义配置文件
    public void test01() throws IOException {
        //读取配置文件，通过类加载器
        InputStream ins = JULTest.class.getClassLoader().getResourceAsStream("logging.properties");

        //创建LogManager
        LogManager logManager = LogManager.getLogManager();

        //创建LogManager加载配置文件
        logManager.readConfiguration(ins);

        //创建日志记录器
        Logger logger = Logger.getLogger("com.krystal");

        //日志记录输出
        logger.severe("server");
        logger.warning("warning");
        logger.info("info");//jul默认的日志级别是 info

        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");

        Logger logger2 = Logger.getLogger("test");

        //日志记录输出
        logger2.severe("server test");
        logger2.warning("warning test");
        logger2.info("info test");//jul默认的日志级别是 info

        logger2.config("config test");
        logger2.fine("fine test");
        logger2.finer("finer test");
        logger2.finest("finest test");

    }

}
