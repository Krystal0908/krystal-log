# Log4j 2 日志框架

## 简单介绍

Log4j 2 官网：https://logging.apache.org/log4j/2.x/

Apache Log4j 2 是对 Log4j 的升级，它比其前身 Log4j 1.x 提供了显着改进，并提供了 [Logback](https://so.csdn.net/so/search?q=Logback&spm=1001.2101.3001.7020) 中可用的许多改进，同时修复了 Logback 架构中的一些固有问题。

1. **提高性能**： Log4j 2 包含基于 LMAX [Disruptor](https://so.csdn.net/so/search?q=Disruptor&spm=1001.2101.3001.7020) 库的下一代异步记录器。在多线程场景中，异步 Logger 的吞吐量比 Log4j 1.x 和 Logback 高 18 倍，延迟低几个数量级。
2. **自动重新加载配置：** 与 Logback 一样，Log4j 2 可以在修改时自动重新加载其配置。与 Logback 不同，它会在重新配置时这样做而不会丢失日志事件。
3. 高级过滤： 与 Logback 一样，Log4j 2 支持基于上下文数据、标记、正则表达式和 Log 事件中的其他组件进行过滤。可以指定过滤以在传递给 Logger 之前或在它们通过 Appender 时应用于所有事件。此外，过滤器也可以与 Loggers 关联。与 Logback 不同，您可以在任何这些情况下使用通用的 Filter 类。
4. **插件架构：** Log4j 使用插件模式来配置组件。因此，您无需编写代码来创建和配置 Appender、Layout、Pattern [Converter](https://so.csdn.net/so/search?q=Converter&spm=1001.2101.3001.7020) 等。Log4j 自动识别插件并在配置引用它们时使用它们。
5. **无垃圾机制：** 在稳态日志记录期间，Log4j 2在独立应用程序中是无垃圾的，在 Web 应用程序中是低垃圾的。这减少了垃圾收集器的压力，并且可以提供更好的响应时间性能。
6. **与应用服务器集成：** 版本 2.10.0 添加了模块 log4j-appserver 以改进与 Apache Tomcat 和 Eclipse Jetty 的集成。
7. **与 Log4j 1.x 兼容：** Log4j 2 的 Log4j-1.2-api 模块为使用 Log4j 1 日志记录方法的应用程序提供了兼容性。从 Log4j 2.13.0 开始，Log4j 2 还为 Log4j 1.x 配置文件提供实验性支持。

目前市面上最主流的日志门面是 SLF4J，虽然 Log4j 2 也是日志门面，但是它的日志实现功能非常强大，且性能优越。所以我们一般情况下还是将 Log4j 2 看作是日志的实现。

SLF4j + Log4j 2 的组合是市场上最强大的日志功能实现方式，是未来的主流趋势。

## 组件介绍

### 记录器

Loggers（记录器）控制日志的输出级别，规则是：只输出级别不低于设定级别的日志信息。以及引用 Appenders（输出器）。



| **级别（由低至高）** | **说明**                           |
| -------------------- | ---------------------------------- |
| ALL                  | 打开所有日志记录开关；是最低等级的 |
| TRACE                | 输出追踪信息；一般情况下并不会使用 |
| DEBUG                | 输出调试信息；打印些重要的运行信息 |
| INFO                 | 输出提示信息；突出应用程序运行过程 |
| WARN                 | 输出警告信息；会出现潜在错误的情况 |
| ERROR（默认）        | 输出错误信息；不影响系统的继续运行 |
| FATAL                | 输出致命错误；会导致应用程序的退出 |
| OFF                  | 关闭所有日志记录开关；是最高等级的 |

### 输出器

Appender（输出器）通常只负责将日志信息写入目标目的地。将格式化日志信息的责任委托给 Layout（格式器）。定义一个名字以便被 Loggers（记录器）引用。

| 常用                                  | **描述**                   |
| ------------------------------------- | -------------------------- |
| AsyncAppender（异步输出器）           | 将日志信息异步输出到目的地 |
| ConsoleAppender（控制台输出器）       | 将日志信息输出到控制台     |
| FileAppender（文件输出器）            | 将日志信息写入指定文件     |
| JDBCAppender（数据库输出器）          | 将日志信息写入数据库表     |
| RollingFileAppender（拆分文件输出器） | 将日志信息写入多个文件     |

### 格式器

Layout（格式器）接收 Appender（输出器）的日志信息将其格式化为满足任何消费日志事件需求的样式。

| **常用**                       | **描述**                                         |
| ------------------------------ | ------------------------------------------------ |
| HTML Layout（HTML格式器）      | 生成一个HTML页面并将每个日志事件添加到表中的一行 |
| Pattern Layout（自定义格式器） | 根据自定义的转换模式，返回格式化后的日志信息结果 |

Pattern Layout（自定义格式器）常用转换符：

| **转换符**               | **描述**                                                     |
| ------------------------ | ------------------------------------------------------------ |
| %c                       | 输出发布日志事件的记录器的名称                               |
| %20c                     | 如果类别名称的长度少于 20 个字符，则左侧填充空格             |
| %-20c                    | 如果类别名称的长度少于 20 个字符，则右侧填充空格             |
| %.30c                    | 如果类别名称超过 30 个字符，则从头开始截断                   |
| %d                       | 按指定格式输出服务器当前时间，如 %d{yyyy-MM-dd HH:mm:ss.SSS} |
| %l                       | 输出生成日志事件的调用者的位置信息                           |
| %L                       | 输出发出记录请求的行号                                       |
| %m                       | 输出与日志事件关联的应用程序提供的消息                       |
| %M                       | 输出发出记录请求的方法名称                                   |
| %n                       | 输出平台相关的行分隔符或字符                                 |
| %level{级别=值,级别=值…} | 输出日志事件的级别名称映射                                   |
| %-5level                 | 如果级别名称的长度少于 5 个字符，则右填充空格                |
| %r                       | 输出从 JVM 启动到创建日志记录事件所经过的毫秒数              |
| %sn                      | 输出在每个事件中递增的序列号，仅在共享相同转换器类对象的应用程序中是唯一的 |
| %T                       | 输出生成日志事件的线程 ID                                    |
| %t                       | 输出生成日志事件的线程名称                                   |
| %fqcn                    | 输出记录器的完全限定类名                                     |

## 入门案例

**导入依赖：**

```xml
<dependencies>
    <!-- log4j2日志门面 -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-api</artifactId>
        <version>2.12.1</version>
    </dependency>
    <!-- log4j2日志实现 -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.12.1</version>
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
package com.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Log4j2Test {
    @Test
    public void test01(){
        Logger logger = LogManager.getLogger(Log4j2Test.class);
        logger.trace("trace级别信息");
        logger.debug("debug级别信息");
        logger.info("info级别信息");
        logger.warn("warn级别信息");
        logger.error("error级别信息"); // 默认级别
        logger.fatal("fatal级别信息");
    }
}

```

**运行结果：** 提示没有使用 Log4j 2 配置文件。

```xml
ERROR StatusLogger No Log4j 2 configuration file found. Using default configuration (logging only errors to the console), or user programmatically provided configurations. Set system property 'log4j2.debug' to show Log4j 2 internal initialization logging. See https://logging.apache.org/log4j/2.x/manual/configuration.html for instructions on how to configure Log4j 2
13:52:20.705 [main] ERROR com.log4j2.Log4j2Test - error级别信息
13:52:20.708 [main] FATAL com.log4j2.Log4j2Test - fatal级别信息
```

**创建配置：** log4j2.xml 文件。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Configuration>

    <!-- 自定义公共属性 -->
    <Properties>
        <!-- 配置输出格式 -->
        <Property name="pattern" value="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] [%t] [%c#%M-%L] %m%n"/>
    </Properties>

    <!-- 配置输出器 -->
    <Appenders>
        <!-- 控制台输出器 -->
        <Console name="console" target="SYSTEM_OUT">
            <PatternLayout pattern="${pattern}"/>
        </Console>
    </Appenders>

    <!-- 配置记录器 -->
    <Loggers>
        <!-- 配置根记录器 -->
        <Root level="trace">
            <!-- 引用控制台输出器 -->
            <AppenderRef ref="console"/>
        </Root>
    </Loggers>

</Configuration>

```

**再次运行：**

```xml
[2022-07-02 23:49:37.972] [TRACE] [main] [com.log4j2.Log4j2Test#test01-12] trace级别信息
[2022-07-02 23:49:37.975] [DEBUG] [main] [com.log4j2.Log4j2Test#test01-13] debug级别信息
[2022-07-02 23:49:37.975] [INFO ] [main] [com.log4j2.Log4j2Test#test01-14] info级别信息
[2022-07-02 23:49:37.975] [WARN ] [main] [com.log4j2.Log4j2Test#test01-15] warn级别信息
[2022-07-02 23:49:37.975] [ERROR] [main] [com.log4j2.Log4j2Test#test01-16] error级别信息
[2022-07-02 23:49:37.975] [FATAL] [main] [com.log4j2.Log4j2Test#test01-17] fatal级别信息

```

## 日志保存

**一个文件保存，相关配置：**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Configuration xlms="https://logging.apache.org/log4j/2.x/">

    <!-- 自定义公共属性 -->
    <Properties>
        <!-- 配置输出格式 -->
        <Property name="pattern" value="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] [%t] [%c#%M-%L] %m%n"/>
        <!-- 配置文件地址 -->
        <Property name="logDir" value="../LogDir"/>
    </Properties>

    <!-- 配置输出器 -->
    <Appenders>
        <!-- 文件输出器 -->
        <File name="file" fileName="${logDir}/file.log">
            <PatternLayout pattern="${pattern}"/>
        </File>
    </Appenders>

    <!-- 配置记录器 -->
    <Loggers>
        <!-- 配置根记录器 -->
        <Root level="trace">
            <!-- 引用文件输出器 -->
            <AppenderRef ref="file"/>
        </Root>
    </Loggers>

</Configuration>

```

**拆分文件保存，相关配置：**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Configuration xlms="https://logging.apache.org/log4j/2.x/">

    <!-- 自定义公共属性 -->
    <Properties>
        <!-- 配置输出格式 -->
        <Property name="pattern" value="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] [%t] [%c#%M-%L] %m%n"/>
        <!-- 配置文件地址 -->
        <Property name="logDir" value="../LogDir"/>
    </Properties>

    <!-- 配置输出器 -->
    <Appenders>
        <!-- 拆分文件输出器 -->
        <RollingFile name="rollingFile" fileName="${logDir}/rollingFile.log"
                     filePattern="${logDir}/$${date:yyyy-MM-dd}/rollingFile-%d{yyyy年MM月dd日HH时mm分}-%i.log">
            <PatternLayout pattern="${pattern}"/>
            <!-- 触发拆分策略 -->
            <Policies>
                <!-- 系统启动时，触发拆分策略，产生一个日志文件 -->
                <onStartupTriggeringPolicy/>
                <!-- 按照文件大小拆分 -->
                <SizeBasedTriggeringPolicy size="1KB"/>
                <!-- 按照时间节点拆分，规则是filePattern -->
                <TimeBasedTriggeringPolicy/>
            </Policies>
        </RollingFile>
    </Appenders>

    <!-- 配置记录器 -->
    <Loggers>
        <!-- 配置根记录器 -->
        <Root level="trace">
            <!-- 引用拆分文件输出器 -->
            <AppenderRef ref="rollingFile"/>
        </Root>
    </Loggers>

</Configuration>

```

## 异步日志

Log4j 2 最大的特色 - 异步日志，性能的提升主要也是从中受益。提供了两种实现日志的方式，一个是通过 AsyncAppender（异步输出器），一个是通过 AsyncLogger（异步记录器）。这两种不同的实现方式，在设计和源码上都是不同的体现。

如果使用异步日志，AsyncAppender（异步输出器）和 AsyncLogger（异步记录器）不要同时使用，没有这个需求，效果也不会叠加。如果同时出现，效率以 AsyncAppender（异步输出器）为主，即木桶效率。

**导入依赖：**

```xml
<!-- log4j2异步日志 -->
<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.3.7</version>
</dependency>

```

### 异步输出器

AsyncAppender（异步输出器） 是通过引用别的 Appender 来实现的，当有日志事件到达时，会开启另外一个线程来处理它们。需要注意的是，如果在 Appender 的时候出现异常，对应用来说是无法感知的。 AsyncAppender 应该在它引用的 Appender 之后配置，默认使用 java.util.concurrent.ArrayBlockingQueue 实现而不需要其它外部的类库。 当使用此Appender 的时候，在多线程的环境下需要注意，阻塞队列容易受到锁争用的影响，这可能会对性能产生影响。

**相关配置:**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Configuration xlms="https://logging.apache.org/log4j/2.x/">

    <!-- 自定义公共属性 -->
    <Properties>
        <!-- 配置输出格式 -->
        <Property name="pattern" value="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] [%t] [%c#%M-%L] %m%n"/>
        <!-- 配置文件地址 -->
        <Property name="logDir" value="../LogDir"/>
    </Properties>

    <!-- 配置输出器 -->
    <Appenders>
        <!-- 控制台输出器 -->
        <Console name="console" target="SYSTEM_OUT">
            <PatternLayout pattern="${pattern}"/>
        </Console>

        <!-- 配置异步输出器 -->
        <Async name="async">
            <!-- 引用控制台输出器 -->
            <AppenderRef ref="console"/>
        </Async>
    </Appenders>

    <!-- 配置记录器 -->
    <Loggers>
        <!-- 配置根记录器 -->
        <Root level="trace">
            <!-- 引用异步输出器 -->
            <AppenderRef ref="async"/>
        </Root>
    </Loggers>

</Configuration>

```

**代码示例：**

```java
package com.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Log4j2Test {
    @Test
    public void test02() throws InterruptedException {
        Logger logger = LogManager.getLogger(Log4j2Test.class);
        for (int i = 0; i < 100; i++) {
            logger.trace("trace级别信息");
            logger.debug("debug级别信息");
            logger.info("info级别信息");
            logger.warn("warn级别信息");
            logger.error("error级别信息");
            logger.fatal("fatal级别信息");
        }
        for (int i = 0; i < 600; i++) {
            System.out.println("——————————————");
        }
    }
}

```

**运行结果：** 多运行几次，可以看出日志记录和系统打印交替输出，实现了异步操作。

```xml
略...
——————————————
[2022-07-03 02:06:49.888] [DEBUG] [main] [com.log4j2.Log4j2Test#-] debug级别信息
——————————————
——————————————
——————————————
——————————————
[2022-07-03 02:06:49.888] [INFO ] [main] [com.log4j2.Log4j2Test#-] info级别信息
——————————————
——————————————
略...

```

### 异步记录器

AsyncLogger（异步记录器）官方推荐，它可以使调用 Logger.log 返回的更快。有两种选择：全局异步和混合异步。

#### 全局异步

所有的日志都异步的记录，在配置文件上不用做任何改动，只需要在 JVM 启动时增加一个参数即可实现。

**创建配置：** log4j2.component.properties 文件。

```xml
Log4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
```

**相关配置：**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Configuration xlms="https://logging.apache.org/log4j/2.x/">

    <!-- 自定义公共属性 -->
    <Properties>
        <!-- 配置输出格式 -->
        <Property name="pattern" value="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] [%t] [%c#%M-%L] %m%n"/>
        <!-- 配置文件地址 -->
        <Property name="logDir" value="../LogDir"/>
    </Properties>

    <!-- 配置输出器 -->
    <Appenders>
        <!-- 控制台输出器 -->
        <Console name="console" target="SYSTEM_OUT">
            <PatternLayout pattern="${pattern}"/>
        </Console>
    </Appenders>

    <!-- 配置记录器 -->
    <Loggers>
        <!-- 配置根记录器 -->
        <Root level="trace">
            <!-- 引用控制台输出器 -->
            <AppenderRef ref="console"/>
        </Root>
    </Loggers>

</Configuration>

```

**代码示例：**

```java
package com.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Log4j2Test {
    @Test
    public void test02() throws InterruptedException {
        Logger logger = LogManager.getLogger(Log4j2Test.class);
        for (int i = 0; i < 100; i++) {
            logger.trace("trace级别信息");
            logger.debug("debug级别信息");
            logger.info("info级别信息");
            logger.warn("warn级别信息");
            logger.error("error级别信息");
            logger.fatal("fatal级别信息");
        }
        for (int i = 0; i < 600; i++) {
            System.out.println("——————————————");
        }
    }
}

```

**运行结果：** 多运行几次，可以看出日志记录和系统打印交替输出，实现了异步操作。

```xml
略...
——————————————
——————————————
[2022-07-03 02:20:10.356] [FATAL] [main] [com.log4j2.Log4j2Test#-] fatal级别信息
——————————————
——————————————
[2022-07-03 02:20:10.356] [TRACE] [main] [com.log4j2.Log4j2Test#-] trace级别信息
——————————————
——————————————
——————————————
——————————————
[2022-07-03 02:20:10.356] [DEBUG] [main] [com.log4j2.Log4j2Test#-] debug级别信息
——————————————
略...

```

#### 混合异步

可以在应用中同时使用同步日志和异步日志，这使得日志的配置方式更加灵活。虽然 Log4j 2 提供以一套异常处理机制，可以覆盖大部分的状态，但是还是会有一小部分的特殊情况是无法完全处理的。混合异步的方式需要通过修改配置文件来实现，使用 AsyncLogger 标记配置。

**先将 log4j2.component.properties 配置文件内容注释。**

**相关配置：**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Configuration xlms="https://logging.apache.org/log4j/2.x/">

    <!-- 自定义公共属性 -->
    <Properties>
        <!-- 配置输出格式 -->
        <Property name="pattern" value="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] [%t] [%c#%M-%L] %m%n"/>
        <!-- 配置文件地址 -->
        <Property name="logDir" value="../LogDir"/>
    </Properties>

    <!-- 配置输出器 -->
    <Appenders>
        <!-- 控制台输出器 -->
        <Console name="console" target="SYSTEM_OUT">
            <PatternLayout pattern="${pattern}"/>
        </Console>
    </Appenders>

    <!-- 配置记录器 -->
    <Loggers>
        <!-- 配置自定义异步记录器 -->
        <!-- includelocation：去除日志记录中的行号信号，行号信息非常影响日志记录的效率（生产中都不加这个行号）-->
        <!-- additivity：不继承根记录器 -->
        <AsyncLogger name="com.log4j2" level="trace" includelocation="false" additivity="false">
            <!-- 引用控制台输出器 -->
            <AppenderRef ref="console"/>
        </AsyncLogger>

        <!-- 配置根记录器 -->
        <Root level="trace">
            <!-- 引用控制台输出器 -->
            <AppenderRef ref="console"/>
        </Root>
    </Loggers>

</Configuration>

```

**代码示例：**

```java
package com.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Log4j2Test {
    @Test
    public void test02() throws InterruptedException {
        Logger logger = LogManager.getLogger(Log4j2Test.class);
        for (int i = 0; i < 100; i++) {
            logger.trace("trace级别信息");
            logger.debug("debug级别信息");
            logger.info("info级别信息");
            logger.warn("warn级别信息");
            logger.error("error级别信息");
            logger.fatal("fatal级别信息");
        }
        for (int i = 0; i < 600; i++) {
            System.out.println("——————————————");
        }
    }
}

```

**运行结果：** 多运行几次，可以看出日志记录和系统打印交替输出，实现了异步操作。

```xml
略...
——————————————
——————————————
[2022-07-03 02:59:45.493] [TRACE] [main] [com.log4j2.Log4j2Test#-] trace级别信息
——————————————
——————————————
——————————————
——————————————
——————————————
[2022-07-03 02:59:45.493] [DEBUG] [main] [com.log4j2.Log4j2Test#-] debug级别信息
——————————————
略...

```

上面 AsyncLogger 异步记录器配置的是 com.log4j2 包，然后应用程序也是在 com.log4j2 包下。如果我们运行其它包下的应用程序，比如 com.test 包下 Log4j2Test.test02() 方法（复制 com.log4j2.Log4j2Test），会发现日志记录仍然是同步输出。

## 集成 SLF4J

**导入依赖：**

```xml
<!-- slf4j日志门面 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>
<!-- log4j2适配器 -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>2.12.1</version>
</dependency>

```

**代码示例：**

```java
Logger logger = LogManager.getLogger(类名.class);
替换为
Logger logger = LoggerFactory.getLogger(类名.class);    

```

