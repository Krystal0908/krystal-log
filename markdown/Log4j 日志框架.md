# Log4j 日志框架

## 简单介绍

Log4j（Log for java）是 Apache 的一个开源项目，通过使用 Log4j，可以控制日志信息输送的目的地是控制台或文件等，也可以控制每一条日志的输出格式。通过定义每一条日志信息的级别，能够更加细致地控制日志的生成过程。这些可以通过一个配置文件来灵活地进行配置，而不需要修改应用的代码。使用 Log4j 技术，主要使用的是其配置文件。

## 组件介绍

### 记录器

Loggers（记录器）控制日志的输出级别，规则是：只输出级别不低于设定级别的日志信息。以及引用 Appenders（输出器）。

| 级别（由低至高） |                描述                |
| :--------------: | :--------------------------------: |
|       ALL        | 打开所有日志记录开关；是最低等级的 |
|      TRACE       | 输出追踪信息；一般情况下并不会使用 |
|  DEBUG（默认）   | 输出调试信息；打印些重要的运行信息 |
|       INFO       | 输出提示信息；突出应用程序运行过程 |
|       WARN       | 输出警告信息；会出现潜在错误的情况 |
|      ERROR       | 输出错误信息；不影响系统的继续运行 |
|      FATAL       | 输出致命错误；会导致应用程序的退出 |
|       OFF        | 关闭所有日志记录开关；是最高等级的 |

### 输出器

Appender（输出器） 通常只负责将日志信息写入目标目的地。将格式化日志信息的责任委托给 Layout（格式器）。定义一个名字以便被 Loggers（记录器）引用。

|                      常用                      |                  描述                  |
| :--------------------------------------------: | :------------------------------------: |
|        ConsoleAppender（控制台输出器）         |         将日志信息输出到控制台         |
|           FileAppender（文件输出器）           |         将日志信息写入指定文件         |
| DailyRollingFileAppender（按天拆分文件输出器） | 将日志信息写入指定文件，每天一个新文件 |
|     RollingFileAppender（拆分文件输出器）      |         将日志信息写入多个文件         |
|          JDBCAppender（数据库输出器）          |         将日志信息写入数据库表         |

### 格式器

Layout（格式器）接收 Appender（输出器）的日志信息将其格式化为满足任何消费日志事件需求的样式。

|             常用              |                       描述                       |
| :---------------------------: | :----------------------------------------------: |
|  SimpleLayout（简单格式器）   | 将日志信息输出为简单格式，默认为 INFO 级别的消息 |
| PatternLayout（自定义格式器） | 根据自定义的转换模式，返回格式化后的日志信息结果 |

Pattern Layout（自定义格式器）转换符：

| 转换符 |                          描述                          | 性能 |
| ------ | :----------------------------------------------------: | ---- |
| %c     |               用于输出日志事件的类别名称               |      |
| %20c   |    如果类别名称的长度少于 20 个字符，则左侧填充空格    |      |
| %.30c  |    如果类别名称的长度少于 20 个字符，则右侧填充空格    |      |
| %-20c  |       如果类别名称超过 30 个字符，则从头开始截断       |      |
| %C     |        用于输出发出日志请求的调用者的全限定类名        | 差   |
| %d     | 用于输出记录事件的日期，如 %d{yyyy-MM-dd HH:mm:ss.SSS} |      |
| %F     |              用于输出发出记录请求的文件名              | 差   |
| %l     |         用于输出产生日志事件的调用者的位置信息         | 差   |
| %L     |               用于输出发出记录请求的行号               | 差   |
| %m     |       用于输出与日志事件关联的应用程序提供的消息       |      |
| %M     |             用于输出发出日志请求的方法名称             | 差   |
| %n     |              输出平台相关的行分隔符或字符              |      |
| %P     |                用于输出日志事件的优先级                |      |
| %r     |     用于输出从构建布局到创建日志事件所经过的毫秒数     |      |
| %t     |             用于输出生成日志事件的线程名称             |      |
| %%     |                   用于输出一个百分号                   |      |

## 入门案例

**导入依赖：**

```xml
<dependencies>
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
</dependencies>

```

**代码示例：**

```java
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Test;

public class Log4jTest {
    @Test
    public void test01() {
        BasicConfigurator.configure(); //加载初始化配置
        Logger logger = Logger.getLogger(Log4jTest.class);
        logger.trace("trace信息");
        logger.debug("debug信息"); //默认级别
        logger.info("info信息");
        logger.warn("warn信息");
        logger.error("error信息");
        logger.fatal("fatal信息");
    }
}

```

**运行结果：**

```
0 [main] DEBUG com.Log4jTest  - debug信息
1 [main] INFO com.Log4jTest  - info信息
1 [main] WARN com.Log4jTest  - warn信息
1 [main] ERROR com.Log4jTest  - error信息
1 [main] FATAL com.Log4jTest  - fatal信息

```

## 配置文件

**配置说明：**

```xml
log4j.rootLogger=日志级别,引用输出器
log4j.appender.自定义输出控制器名称=输出方式
log4j.appender.自定义输出控制器名称.layout=输出格式

```

**创建配置：** log4j.properties 文件。

```properties
#配置日志级别,引用输出器
log4j.rootLogger=INFO,console
#配置控制台输出器
log4j.appender.console=org.apache.log4j.ConsoleAppender
#配置简单格式器
log4j.appender.console.layout=org.apache.log4j.SimpleLayout

```

**代码示例：**

```java
@Test
public void test02() {
    // 已有配置文件，无需再加载初始化配置
    //BasicConfigurator.configure();
    Logger logger = Logger.getLogger(Log4jTest.class);
    logger.trace("trace信息");
    logger.debug("debug信息");
    logger.info("info信息");
    logger.warn("warn信息");
    logger.error("error信息");
    logger.fatal("fatal信息");
}

```

**运行结果：**

```
INFO - info信息
WARN - warn信息
ERROR - error信息
FATAL - fatal信息

```

**修改配置：**

```properties
#配置日志级别,引用输出器
log4j.rootLogger=INFO,console

########## 控制台输出器 ##########
#配置控制台输出器
log4j.appender.console=org.apache.log4j.ConsoleAppender
#配置自定义格式器
log4j.appender.console.layout=org.apache.log4j.PatternLayout
#配置自定义转换模式
log4j.appender.console.layout.conversionPattern=[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5p] [%t] [%-4rms] [%c] %M %L %m%n

```

**代码示例：**

```java
@Test
public void test02() {
    Logger logger = Logger.getLogger(Log4jTest.class);
    logger.trace("trace信息");
    logger.debug("debug信息");
    logger.info("info信息");
    logger.warn("warn信息");
    logger.error("error信息");
    logger.fatal("fatal信息");
}

```

**运行结果：**

```
[2022-06-30 21:19:45.421] [INFO ] [main] [0   ms] [com.log4j.Log4jTest] test02 26 info信息
[2022-06-30 21:19:45.406] [WARN ] [main] [985 ms] [com.log4j.Log4jTest] test02 27 warn信息
[2022-06-30 21:19:45.410] [ERROR] [main] [989 ms] [com.log4j.Log4jTest] test02 28 error信息
[2022-06-30 21:19:45.416] [FATAL] [main] [995 ms] [com.log4j.Log4jTest] test02 29 fatal信息

```

## 保存日志

**保存到一个文件中，相关配置：**

```properties
#配置日志级别,输出控制器
log4j.rootLogger=INFO,file

########## 文件输出器 ##########
#配置文件输出器
log4j.appender.file=org.apache.log4j.FileAppender
#配置自定义格式器
log4j.appender.file.layout=org.apache.log4j.PatternLayout
#配置自定义转换模式
log4j.appender.file.layout.conversionPattern=[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5p] [%t] [%-4rms] [%c] %M %L %m%n
#配置文件路径及名称
log4j.appender.file.file=../logDir/file.log
#配置字符编码
log4j.appender.file.encoding=UTF-8

```

**按照文件大小拆分，相关配置：**

```properties
#配置日志级别,输出控制器
log4j.rootLogger=INFO,rollingFile

########## 拆分文件输出器 ##########
#配置拆分文件输出器
log4j.appender.rollingFile=org.apache.log4j.RollingFileAppender
#配置自定义格式器
log4j.appender.rollingFile.layout=org.apache.log4j.PatternLayout
#配置自定义转换模式
log4j.appender.rollingFile.layout.conversionPattern=[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5p] [%t] [%-4rms] [%c] %M %L %m%n
#配置文件路径及名称
log4j.appender.rollingFile.file=../logDir/rollingFile.log
#配置字符编码
log4j.appender.rollingFile.encoding=UTF-8
#配置拆分文件大小
log4j.appender.rollingFile.maxFileSize=1KB
#配置拆分文件数量
log4j.appender.rollingFile.maxBackupIndex=2

#若文件大小超过1KB，则生成另外一个文件，数量最多2个。
#若2个文件不够用，则按照时间来进行覆盖，保留新的覆盖旧的。

```

**按照时间进行拆分，相关配置：**

```properties
#配置日志级别,输出控制器
log4j.rootLogger=INFO,dailyRollingFile

########## 按天拆分文件输出器 ##########
#配置每天拆分文件输出器
log4j.appender.dailyRollingFile=org.apache.log4j.DailyRollingFileAppender
#配置自定义格式器
log4j.appender.dailyRollingFile.layout=org.apache.log4j.PatternLayout
#配置自定义转换模式
log4j.appender.dailyRollingFile.layout.conversionPattern=[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5p] [%t] [%-4rms] [%c] %M %L %m%n
#配置文件路径及名称
log4j.appender.dailyRollingFile.file=../logDir/dailyRollingFile.log
#配置字符编码
log4j.appender.dailyRollingFile.encoding=UTF-8
#配置日期格式
log4j.appender.dailyRollingFile.datePattern='.'yyyy-MM-dd

```

**保存日志到数据库，相关配置：**

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.26</version>
</dependency>

```

SQL

```sql
create table log4j
(
    id         int    (11)    not null auto_increment comment '自增ID',
    name       varchar(30)    default null comment '项目名称',
    createTime varchar(30)    default null comment '创建时间',
    level      varchar(10)    default null comment '日志级别',
    thread     varchar(30)    default null comment '线程名称',
    className  varchar(255)   default null comment '全限定名',
    method     varchar(50)    default null comment '方法名称',
    lineNumber int    (5)     default null comment '代码行号',
    message    varchar(10000) default null comment '日志信息',
    primary key (id)
)

```

配置：

```properties
#配置日志级别,输出控制器
log4j.rootLogger=INFO,db

########## 数据库输出器 ##########
#配置数据库输出器
log4j.appender.db=org.apache.log4j.jdbc.JDBCAppender
#配置自定义格式器
log4j.appender.db.layout=org.apache.log4j.PatternLayout
#配置自定义转换模式
log4j.appender.db.layout.conversionPattern=[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5p] [%t] [%-4rms] [%c] %M %L %m%n
#配置数据库
log4j.appender.db.URL=jdbc:mysql://localhost:3306/test
log4j.appender.db.Driver=com.mysql.cj.jdbc.Driver
log4j.appender.db.User=root
log4j.appender.db.Password=root
log4j.appender.db.Sql=insert into log4j (name, createTime, level, thread, className, method, lineNumber, message) \
  values ('log', '%d{yyyy-MM-dd HH:mm:ss.SSS}', '%p', '%t', '%c', '%M', '%L', '%m');

```



























































































