# SLF4J 日志门面

## 简单介绍

官方网址：https://www.slf4j.org/

1. SLF4J（ Simple Logging Facade For Java），即 简单日志门面。主要是为了给 Java 日志访问提供一套标准、规范的 API 框架，其主要意义在于提供接口，具体的实现可以交由其他日志框架，例如 Log4j 或 Logback 等。

2. SLF4J 自身也提供了功能较为简单的实现，但是一般很少用到。对于一般的 Java 项目而言，日志框架会选择 slf4j-api 作为门面，配上具体的实现框架，中间使用桥接器完成桥接。所以 SLF4J 最重要的两个功能就是对于日志框架的绑定以及日志框架的桥接。

## 日志级别

|     级别     |     描述     |
| :----------: | :----------: |
|    trace     | 日志追踪信息 |
|    debug     | 日志详细信息 |
| info（默认） | 日志关键信息 |
|     warn     | 日志警告信息 |
|    error     | 日志错误信息 |

## 入门案例

**导入依赖：**

```xml
<dependencies>
    <!-- slf4j核心依赖 -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.25</version>
    </dependency>
    <!-- slf4j自带的简单日志实现 -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>1.7.25</version>
    </dependency>
    <!-- 单元测试 -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
</dependencies>

```

**代码示例：**

```java
package com.slf4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {
    @Test
    public void test01(){
        // 没有集成其它日志实现框架的话，使用自带的简单日志实现框架(slf4j-simple)
        Logger logger = LoggerFactory.getLogger(Slf4jTest.class);
        logger.trace("trace追踪信息");
        logger.debug("debug详细信息");
        logger.info("info关键信息");
        logger.warn("warn警告信息");
        logger.error("error错误信息");
    }
}

```

**运行结果：**

```xml
[main] INFO com.slf4j.Slf4jTest - info关键信息
[main] WARN com.slf4j.Slf4jTest - warn警告信息
[main] ERROR com.slf4j.Slf4jTest - error错误信息

```

## 动态打印

**代码示例：**

```java
@Test
public void test02() {
    Logger logger = LoggerFactory.getLogger(Slf4jTest.class);
    String name = "张三";
    int age = 23;
    // 字符串拼接，麻烦、可读性差
    //logger.info("学生信息：姓名-" + name + "；年龄-" + age + "");
    // 动态信息打印，使用占位符的形式来代替字符串的拼接
    logger.info("学生信息：姓名-{}；年龄-{}", name, age);
}

```

**运行结果：**

```xml
[main] INFO com.slf4j.Slf4jTest - 学生信息：姓名-张三；年龄-23

```

## 异常打印

**代码示例：**

```java
@Test
public void test03() {
    Logger logger = LoggerFactory.getLogger(Slf4jTest.class);
    try {
        Class.forName("User");
    } catch (ClassNotFoundException e) {
        logger.info("异常信息：", e);
    }
}

```

**运行结果：**

```xml
[main] INFO com.slf4j.Slf4jTest - 异常信息：
java.lang.ClassNotFoundException: User
	at java.net.URLClassLoader.findClass(URLClassLoader.java:382)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:418)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:355)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:351)
	at java.lang.Class.forName0(Native Method)
	at java.lang.Class.forName(Class.java:264)
	at com.slf4j.Slf4jTest.test03(Slf4jTest.java:34)
    ...

```

## 日志集成

SLF4J 日志门面共有三种情况对日志实现进行绑定。

- 在没有绑定任何日志实现框架的基础上，日志不能实现任何功能。slf4j-simple 是 SLF4J 官方提供的简单实现，也需要导入依赖，自动绑定到 SLF4J 日志门面上。

- Logback 和 Simple（包括 nop） 是 SLF4 出现后提供的日志实现框架，所以 API 完全遵循 SLF4J 进行设计。只需要导入对应的日志实现依赖，即可与 SLF4J 无缝衔接。nop 虽然也划分到实现中，但它是指不实现日志记录。

- Log4j 和 JUL 是 SLF4J 出现前就已经存在的日志实现框架，所以 API 不遵循 SLF4J 进行设计。需要通过适配桥接的技术，完成的与 SLF4J 的衔接。

**注意：** 在 SLF4J 环境下，若同时导入多个日志实现框架，默认使用先导入的。在实际应用中，一般只集成一种日志实现。

## 集成 logback

**导入依赖：** 注释 slf4j-simple 依赖。

```xml
<!-- logback日志框架 -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.3</version>
</dependency>

```

**代码示例：**

```java
@Test
public void test01() {
    Logger logger = LoggerFactory.getLogger(Slf4jTest.class);
    logger.trace("trace追踪信息");
    logger.debug("debug详细信息");
    logger.info("info关键信息");
    logger.warn("warn警告信息");
    logger.error("error错误信息");
}

```

**运行结果：**

```xml
12:26:18.489 [main] DEBUG com.slf4j.Slf4jTest - debug详细信息
12:26:18.492 [main] INFO com.slf4j.Slf4jTest - info关键信息
12:26:18.492 [main] WARN com.slf4j.Slf4jTest - warn警告信息
12:26:18.492 [main] ERROR com.slf4j.Slf4jTest - error错误信息

```

## 集成 slf4j-nop

**导入依赖：** 注释 logback 依赖。

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-nop</artifactId>
    <version>1.7.25</version>
</dependency>

```

**代码示例：**

```java
@Test
public void test01() {
    Logger logger = LoggerFactory.getLogger(Slf4jTest.class);
    logger.trace("trace追踪信息");
    logger.debug("debug详细信息");
    logger.info("info关键信息");
    logger.warn("warn警告信息");
    logger.error("error错误信息");
}

```

**运行结果：** 可以看到没有日志输出，其主要作用是禁止日志打印。

```xml

```

## 集成 log4j

**导入依赖：** 注释 slf4j-nop 依赖。

```xml
<!-- log4j日志框架 -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>

```

**代码示例：**

```java
@Test
public void test01() {
    Logger logger = LoggerFactory.getLogger(Slf4jTest.class);
    logger.trace("trace追踪信息");
    logger.debug("debug详细信息");
    logger.info("info关键信息");
    logger.warn("warn警告信息");
    logger.error("error错误信息");
}

```

**运行结果：** 报错了。

```xml
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.

```

因为 Log4j 是 SLF4J 出现前就已经存在的日志实现框架，所以 API 不遵循 SLF4J 进行设计。需要通过适配桥接的技术来完成的与 SLF4J 的衔接。

**导入依赖：**

```xml
<!-- log4j适配器 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.25</version>
</dependency>

```

**再次运行：** 虽然日志信息没有打印出来，但根据警告信息可看出使用了 Log4j 日志框架。

```xml
log4j:WARN No appenders could be found for logger (com.slf4j.Slf4jTest).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.

```

**创建配置：** log4j.properties 文件。

```properties
#配置日志级别,输出器
log4j.rootLogger=INFO,console
#配置控制台输出器
log4j.appender.console=org.apache.log4j.ConsoleAppender
#配置自定义格式器
log4j.appender.console.layout=org.apache.log4j.PatternLayout
#配置自定义转换模式
log4j.appender.console.layout.conversionPattern=[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5p] [%t] [%-4rms] [%c#%M-%L] %m%n

```

**再次运行：**

```xml
[2022-07-01 12:44:21.977] [INFO ] [main] [0   ms] [com.slf4j.Slf4jTest#test01-14] info关键信息
[2022-07-01 12:44:21.979] [WARN ] [main] [2   ms] [com.slf4j.Slf4jTest#test01-15] warn警告信息
[2022-07-01 12:44:21.979] [ERROR] [main] [2   ms] [com.slf4j.Slf4jTest#test01-16] error错误信息

```

## 集成 jul

因为 JUL 日志框架是 JDK 内置的工具包，无需导入依赖。JUL 是 SLF4J 出现前就已经存在的日志实现框架，所以 API 不遵循 SLF4J 进行设计。需要通过适配桥接的技术，完成的与 SLF4J 的衔接。

**导入依赖：**

 

```xml
<!-- jul适配器 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-jdk14</artifactId>
    <version>1.7.25</version>
</dependency>

```

**代码示例：**

```java
@Test
public void test01() {
    Logger logger = LoggerFactory.getLogger(Slf4jTest.class);
    logger.trace("trace追踪信息");
    logger.debug("debug详细信息");
    logger.info("info关键信息");
    logger.warn("warn警告信息");
    logger.error("error错误信息");
}

```

**运行结果：**

```xml
七月 01, 2022 12:50:56 下午 com.slf4j.Slf4jTest test01
信息: info关键信息
七月 01, 2022 12:50:56 下午 com.slf4j.Slf4jTest test01
警告: warn警告信息
七月 01, 2022 12:50:56 下午 com.slf4j.Slf4jTest test01
严重: error错误信息

```





























































