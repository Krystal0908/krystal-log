# JUL 日志框架

## 简单介绍

JUL（Java Util Logging），它是 Java 原生的日志[框架](https://so.csdn.net/so/search?q=框架&spm=1001.2101.3001.7020)，位于 java.util.logging.Logger 包。相对其他的框架使用方便，学习简单，主要是使用在小型项目中。

## 组件介绍

| **组件**            | **描述**                                               |
| ------------------- | ------------------------------------------------------ |
| Logger（记录器）    | 用于记录系统或应用程序的消息，是访问日志系统的入口程序 |
| Handler（处理器）   | 从记录器获取日志消息并输出，决定日志记录最终的输出位置 |
| Filter（过滤器）    | 用于对记录的内容提供细粒度控制，超出日志级别提供的控制 |
| Formatter（格式器） | 提供对日志记录格式化的支持，决定日志记录最终的输出形式 |
| Level（日志级别）   | 定义了一组标准的日志记录级别，可用于控制日志记录的输出 |

## 入门案例

**导入依赖：**

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
</dependency>

```

**代码示例：**

```java
package com.jul.test;

import org.junit.Test;
import java.util.logging.Level;
import java.util.logging.Logger;

// 日志入口程序：java.util.logging.Logger
public class JULTest {
    @Test
    public void test01() {
        // 引入当前类的全路径字符串获取日志记录器
        Logger logger = Logger.getLogger("com.jul.JulTest");

        // 对于日志的输出有两种方式
        // 1、直接调用日志级别的相关方法，方法中传递日志输出信息
        logger.info("info信息1");

        // 2、调用log方法，通过Level类型定义日志级别参数，以及搭配日志输出信息的参数
        logger.log(Level.INFO, "info信息2");

        System.out.println("--------");

        // 打印日志信息并传参
        // 输出学生信息：姓名、年龄
        String name = "张三";
        int age = 23;
        logger.log(Level.INFO, "方式一：学生姓名：" + name + "，学生年龄：" + age);

        // 以上操作中，对于输出消息用字符串拼接弊端很多。拼接麻烦、程序效率低、可读性不强、维护成本高
        // 应该使用动态生成数据的方式生产日志，就是占位符的方式来进行操作
        logger.log(Level.INFO, "方式二：学生姓名：{0}，学生年龄:{1}", new Object[]{name, age});
    }
}

```

**运行结果：**

```xml
六月 14, 2021 10:14:37 下午 com.jul.JulTest test01
信息: info信息1
六月 14, 2021 10:14:37 下午 com.jul.JulTest test01
--------
信息: info信息2
六月 14, 2021 10:14:37 下午 com.jul.JulTest test01
信息: 方式一：学生姓名：张三，学生年龄：23
六月 14, 2021 10:14:37 下午 com.jul.JulTest test01
信息: 方式二：学生姓名：张三，学生年龄:23

```

## 日志级别

| **日志级别** | **数值**          | **说明**                           |
| ------------ | ----------------- | ---------------------------------- |
| OFF          | Integer.MAX_VALUE | 关闭所有消息的日志记录             |
| SEVERE       | 1000              | 错误信息（最高级的日志级别）       |
| WARNING      | 900               | 警告信息                           |
| INFO         | 800               | 默认信息（默认级别）               |
| CONFIG       | 700               | 配置信息                           |
| FINE         | 500               | 详细信息（少）                     |
| FINER        | 400               | 详细信息（中）                     |
| FINEST       | 300               | 详细信息（多）（最低级的日志级别） |
| ALL          | Integer.MIN_VALUE | 启用所有消息的日志记录             |

这个数值的意义在于，设置的日志级别是 INFO 级别 - 800 时，则最终展现的日志信息，必须是数值大于 800 的所有级别信息。

**代码示例：**

```java
@Test
public void test02() {
    // 获取日志记录器
    Logger logger = Logger.getLogger("com.jul.JulTest");
    // 设置日志级别为配置级别
    logger.setLevel(Level.CONFIG);
    // 输出日志信息
    logger.severe("severe：错误信息");
    logger.warning("warning：警告信息");
    logger.info("info：默认信息");
    logger.config("config：配置信息");
    logger.fine("fine：详细信息(少)");
    logger.finer("finer：详细信息(中)");
    logger.finest("finest：详细信息(多)");
}

```

**运行结果：**

- 通过打印结果看到只输出了 INFO 级别以及比 INFO 级别高的日志信息，而比 INFO 级别低的日志信息没有打印，
  说明 INFO 级别的日志信息是系统默认的日志级别。
- 仅通过以上形式来设置日志级别是不够的，还需要搭配处理器 handler 共同设置才会生效。

```xml
六月 14, 2021 10:17:53 下午 com.jul.JulTest test02
严重:  severe：错误信息
六月 14, 2021 10:17:53 下午 com.jul.JulTest test02
警告: warning：警告信息
六月 14, 2021 10:17:53 下午 com.jul.JulTest test02
信息:    info：默认信息

```

## 自定义日志级别

**代码示例：**

```java
@Test
public void test03() {
    // 获取日志记录器
    Logger logger = Logger.getLogger("com.jul.JulTest");
    // 将默认的日志打印方式关闭
    // 参数设置为 false，打印日志的方式就不会按照父 logger 默认的方式去进行操作
    logger.setUseParentHandlers(false);
    // 控制台日志处理器
    ConsoleHandler handler = new ConsoleHandler();
    // 创建日志格式化组件对象
    SimpleFormatter formatter = new SimpleFormatter();
    // 在处理器中设置日志输出格式
    handler.setFormatter(formatter);
    // 在记录器中添加处理器
    logger.addHandler(handler);
    // 设置日志的打印级别
    // 此处必须将日志记录器和处理器的级别进行统一的设置，才会达到日志显示相应级别的效果
    logger.setLevel(Level.ALL);
    handler.setLevel(Level.ALL);
    // 输出日志信息
    logger.severe("severe：错误信息");
    logger.warning("warning：警告信息");
    logger.info("info：默认信息");
    logger.config("config：配置信息");
    logger.fine("fine：详细信息(少)");
    logger.finer("finer：详细信息(中)");
    logger.finest("finest：详细信息(多)");
}

```

**运行结果：**

```xml
六月 14, 2021 10:19:26 下午 com.jul.JulTest test03
严重: severe：错误信息
六月 14, 2021 10:19:26 下午 com.jul.JulTest test03
警告: warning：警告信息
六月 14, 2021 10:19:26 下午 com.jul.JulTest test03
信息: info：默认信息
六月 14, 2021 10:19:26 下午 com.jul.JulTest test03
配置: config：配置信息
六月 14, 2021 10:19:26 下午 com.jul.JulTest test03
详细: fine：详细信息(少)
六月 14, 2021 10:19:26 下午 com.jul.JulTest test03
较详细: finer：详细信息(中)
六月 14, 2021 10:19:26 下午 com.jul.JulTest test03
非常详细: finest：详细信息(多)

```

## 日志打印到文件

**代码示例：**

```java
@Test
public void test04() throws IOException {
    // 获取日志记录器
    Logger logger = Logger.getLogger("com.jul.JulTest");
    // 关闭父记录器打印方式
    logger.setUseParentHandlers(false);

    // 文件日志处理器
    FileHandler handler = new FileHandler("src\\jul.log"); // 指定输出的日志文件
    SimpleFormatter formatter = new SimpleFormatter();
    handler.setFormatter(formatter);
    logger.addHandler(handler);

    // 统一设置日志的打印级别
    logger.setLevel(Level.ALL);
    handler.setLevel(Level.ALL);

    // 输出日志信息
    logger.severe("severe：错误信息");
    logger.warning("warning：警告信息");
    logger.info("info：默认信息");
    logger.config("config：配置信息");
    logger.fine("fine：详细信息(少)");
    logger.finer("finer：详细信息(中)");
    logger.finest("finest：详细信息(多)");
}

```

**运行结果：** 此时控制台中并没有输出日志信息，打开 jul.log 文件，日志信息打印到文件中了。

```xml
六月 14, 2021 10:24:19 下午 com.jul.JulTest test04
严重: severe：错误信息
六月 14, 2021 10:24:19 下午 com.jul.JulTest test04
警告: warning：警告信息
六月 14, 2021 10:24:19 下午 com.jul.JulTest test04
信息: info：默认信息
六月 14, 2021 10:24:19 下午 com.jul.JulTest test04
配置: config：配置信息
六月 14, 2021 10:24:19 下午 com.jul.JulTest test04
详细: fine：详细信息(少)
六月 14, 2021 10:24:19 下午 com.jul.JulTest test04
较详细: finer：详细信息(中)
六月 14, 2021 10:24:19 下午 com.jul.JulTest test04
非常详细: finest：详细信息(多)

```

## 添加多个处理器

用户使用 Logger 来进行日志的记录，使用 Handler 来进行日志的输出，Logger 可以持有多个处理器 Handler，
添加了哪些 Handler 对象，就相当于根据所添加的 Handler 将日志输出到指定的位置上，例如控制台、文件中…

**代码示例：**

```java
@Test
public void test05() throws IOException {
    Logger logger = Logger.getLogger("com.jul.JulTest");
    logger.setUseParentHandlers(false);
    SimpleFormatter formatter = new SimpleFormatter();

    // 文件日志处理器
    FileHandler handler1 = new FileHandler("src\\Jul2.log"); // 指定输出的日志文件
    handler1.setFormatter(formatter);
    logger.addHandler(handler1); // 记录器中添加了一个文件日志处理器

    // 控制台日志处理器
    ConsoleHandler handler2 = new ConsoleHandler();
    handler2.setFormatter(formatter);
    logger.addHandler(handler2); // 记录器中又添加了一个控制台日志处理器

    // 统一设置日志的打印级别
    logger.setLevel(Level.ALL);
    handler1.setLevel(Level.ALL);
    handler2.setLevel(Level.ALL);

    // 输出日志信息
    logger.severe("severe：错误信息");
    logger.warning("warning：警告信息");
    logger.info("info：默认信息");
    logger.config("config：配置信息");
    logger.fine("fine：详细信息(少)");
    logger.finer("finer：详细信息(中)");
    logger.finest("finest：详细信息(多)");
}

```

**运行结果：**

控制台输出了日志信息。

```xml
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
严重: severe：错误信息
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
警告: warning：警告信息
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
信息: info：默认信息
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
配置: config：配置信息
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
详细: fine：详细信息(少)
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
较详细: finer：详细信息(中)
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
非常详细: finest：详细信息(多)

```

Jul2.log 文件中也打印了日志信息。

```xml
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
严重: severe：错误信息
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
警告: warning：警告信息
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
信息: info：默认信息
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
配置: config：配置信息
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
详细: fine：详细信息(少)
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
较详细: finer：详细信息(中)
六月 14, 2021 10:38:28 下午 com.jul.JulTest test05
非常详细: finest：详细信息(多)

```

## 记录器父子关系

1. JUL 中 Logger 记录器之间是存在 “父子” 关系的，这种父子关系不是我们普遍认为的类之间的继承关系，关系是通过树状结构存储的。
2. JUL 在初始化时会创建一个顶层 RootLogger 作为所有 Logger 的父 Logger，RootLogger 是 LogManager 的内部类，默认的名称为空串。
3. 以上的 RootLogger 对象作为树状结构的根节点存在的，将来自定义的父子关系通过路径来进行关联，父子关系同时也是节点之间的挂载关系。

**代码示例：**

```java
@Test
public void test06() {
    // 创建两个 logger 对象，可以认为 logger1 是 logger2 的父亲
    // RootLogger 是所有 logger 对象的顶层 logger，名称默认是一个空的字符串
    Logger logger1 = Logger.getLogger("com.jul");
    Logger logger2 = Logger.getLogger("com.jul.JulTest");

    System.out.println(logger2.getParent() == logger1);
    System.out.println("----");
    
    System.out.println("logger1名称：" + logger1.getName() +
                       "，\n父Logger名称：" + logger1.getParent().getName() +
                       "，\n父Logger引用：" + logger1.getParent());
    System.out.println("----");
    
    System.out.println("logger2名称：" + logger2.getName() +
                       "，\n父Logger名称：" + logger2.getParent().getName() +
                       "，\n父Logger引用：" + logger2.getParent());
    System.out.println("----");

    // 父亲所做的设置，也能够同时作用于儿子
    // 对 logger1 做日志打印相关的设置，然后我们使用 logger2 进行日志的打印
    logger1.setUseParentHandlers(false);

    ConsoleHandler handler = new ConsoleHandler();
    SimpleFormatter formatter = new SimpleFormatter();
    handler.setFormatter(formatter);
    logger1.addHandler(handler);
    handler.setLevel(Level.ALL);
    logger1.setLevel(Level.ALL);

    //儿子做打印
    logger2.severe("severe：错误信息");
    logger2.warning("warning：警告信息");
    logger2.info("info：默认信息");
    logger2.config("config：配置信息");
    logger2.fine("fine：详细信息(少)");
    logger2.finer("finer：详细信息(中)");
    logger2.finest("finest：详细信息(多)");
}

```

**运行结果：**

```xml
true
----
logger1名称：com.jul，
父Logger名称：，
父Logger引用：java.util.logging.LogManager$RootLogger@3b764bce
----
logger2名称：com.jul.JulTest，
父Logger名称：com.jul，
父Logger引用：java.util.logging.Logger@759ebb3d
----
六月 14, 2021 10:41:40 下午 com.jul.JulTest test06
严重: severe：错误信息
六月 14, 2021 10:41:40 下午 com.jul.JulTest test06
警告: warning：警告信息
六月 14, 2021 10:41:40 下午 com.jul.JulTest test06
信息: info：默认信息
六月 14, 2021 10:41:40 下午 com.jul.JulTest test06
配置: config：配置信息
六月 14, 2021 10:41:40 下午 com.jul.JulTest test06
详细: fine：详细信息(少)
六月 14, 2021 10:41:40 下午 com.jul.JulTest test06
较详细: finer：详细信息(中)
六月 14, 2021 10:41:40 下午 com.jul.JulTest test06
非常详细: finest：详细信息(多)

```

## 日志的配置文件

以上所有配置的相关操作，都是以 java 硬编码的形式进行的，我们可以使用配置文件，若没有指定自定义日志配置文件，则使用系统默认的日志配置文件。

默认配置文件位置： `jdk 安装目录下 \ jre \ lib \ logging.properties 文件` 。

**默认配置：**

```
############################################################
#  	默认日志记录配置文件
#
# 您可以通过使用java.util.logging.config.file系统属性指定文件名来使用不同的文件
# 例如 java -Djava.util.logging.config.file=myfile
############################################################

############################################################
#  	全局性质
############################################################

# RootLogger使用的处理器，在获取RootLogger对象时进行的设置
# 可在当前处理器类后，通过指定的英文逗号分隔，添加多个日志处理器
# 这些处理程序将在VM启动期间安装，请注意：这些类必须位于系统类路径上
# 默认情况下，只配置控制台处理程序，默认打印INFO和高于INFO级别消息
handlers = java.util.logging.ConsoleHandler

# 要添加文件处理程序，请使用以下行（多个日志处理器）
#handlers= java.util.logging.FileHandler, java.util.logging.ConsoleHandler

# RootLogger 默认的全局日志记录级别
# 对于这种全局层面的任何特定配置，可以通过配置特定的水平来覆盖
# 如果不手动配置其它的日志级别，则默认输出下述配置的级别以及更高的级别
.level = INFO

############################################################
# 处理器指定属性，描述处理程序的特定配置信息
############################################################

# 文件处理器属性设置
# 默认输出的日志文件路径，位于用户的主目录中
# %h：当前用户系统的默认根路径，C:\用户\用户名\java0.log
# %u：指向默认输出的日志文件数量count，count=1，则：java0.log；count=2，则：java0.log，java1.log...
java.util.logging.FileHandler.pattern = %h/java%u.log
# 默认输出的日志文件大小（单位字节）
java.util.logging.FileHandler.limit = 50000
# 默认输出的日志文件数量
java.util.logging.FileHandler.count = 1
# 默认输出的日志文件格式（XML）
java.util.logging.FileHandler.formatter = java.util.logging.XMLFormatter

# 控制台处理器属性设置
# 默认输出的日志级别
java.util.logging.ConsoleHandler.level = INFO
# 默认输出的日志格式（Simple）
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter

# 示例以自定义简单的格式化器输出格式，以打印这样的单行日志消息:
#     <level>: <log message> [<date/time>]
#
# java.util.logging.SimpleFormatter.format=%4$s: %5$s [%1$tc]%n

############################################################
# 配置特定属性，为每个记录器提供额外的控制
############################################################

# 例如：将日志级别设定到具体的某个包下
com.xyz.foo.level = SEVERE

```

## 自定义配置文件

**创建配置：** logging.properties 文件。

```
############################################################
# 默认日志记录配置文件
############################################################
# 全局性质
############################################################

# 默认配置控制台处理程序，默认打印INFO和高于INFO级别信息
handlers=java.util.logging.ConsoleHandler
# 如果不手动配置其它的日志级别，则默认输出下述配置的级别以及更高的级别
.level = ALL

############################################################
# 处理器指定属性，描述处理程序的特定配置信息
############################################################

# 文件处理器属性设置
# 默认输出的日志文件路径，位于用户的主目录中
java.util.logging.FileHandler.pattern = %h/java%u.log
# 默认输出的日志文件大小（单位字节）
java.util.logging.FileHandler.limit = 50000
# 默认输出的日志文件数量
java.util.logging.FileHandler.count = 1
# 默认输出的日志文件格式（XML）
java.util.logging.FileHandler.formatter = java.util.logging.XMLFormatter

# 控制台处理器属性设置
# 默认输出的日志级别
java.util.logging.ConsoleHandler.level = ALL
# 默认输出的日志格式（Simple）
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter

############################################################
# 配置特定属性，为每个记录器提供额外的控制
############################################################
# 例如：将日志级别设定到具体的某个包下
com.xyz.foo.level = SEVERE

```

**代码示例：**

```java
// 自定义配置文件
@Test
public void test07() throws IOException {
    // 读取自定义日志配置文件
    InputStream input = new FileInputStream("src/logging.properties");
    // 获取日志管理器
    LogManager logManager = LogManager.getLogManager();
    // 日志管理器读取自定义配置文件
    logManager.readConfiguration(input);
    // 日志记录器
    Logger logger = Logger.getLogger("com.jul.JulTest");
    // 输出日志信息
    logger.severe("severe：错误信息");
    logger.warning("warning：警告信息");
    logger.info("info：默认信息");
    logger.config("config：配置信息");
    logger.fine("fine：详细信息(少)");
    logger.finer("finer：详细信息(中)");
    logger.finest("finest：详细信息(多)");
}

```

**运行结果：** 此时控制台输出的日志使用的是我们自定义的日志配置文件，打印了所有级别日志信息。

```
六月 14, 2021 11:00:47 下午 com.jul.JulTest test07
严重: severe：错误信息
六月 14, 2021 11:00:47 下午 com.jul.JulTest test07
警告: warning：警告信息
六月 14, 2021 11:00:47 下午 com.jul.JulTest test07
信息: info：默认信息	
六月 14, 2021 11:00:47 下午 com.jul.JulTest test07
配置: config：配置信息
六月 14, 2021 11:00:47 下午 com.jul.JulTest test07
详细: fine：详细信息(少)
六月 14, 2021 11:00:47 下午 com.jul.JulTest test07
较详细: finer：详细信息(中)
六月 14, 2021 11:00:47 下午 com.jul.JulTest test07
非常详细: finest：详细信息(多)

```

**添加配置：** 在 11 行处添加自定义文件日志处理器的配置信息。

```properties
# 自定义文件日志处理器
com.jul.handlers = java.util.logging.FileHandler
# 自定义输出的日志级别
com.jul.level = WARNING
# 屏蔽父记录器打印方式
com.jul.useParentHandlers = false

```

**再次运行：** 在 `C:\用户\用户名` 目录下会有一个 `java0.log` 文件，打开文件发现日志格式为 XML 格式，这是因为 `java.util.logging.FileHandler.formatter` 指定的格式为 `XMLFormatter` 。

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE log SYSTEM "logger.dtd">
<log>
<record>
  <date>2021-06-14T23:24:06</date>
  <millis>1623684246017</millis>
  <sequence>0</sequence>
  <logger>com.jul.JulTest</logger>
  <level>SEVERE</level>
  <class>com.jul.JulTest</class>
  <method>test07</method>
  <thread>1</thread>
  <message>severe：错误信息</message>
</record>
<record>
  <date>2021-06-14T23:24:06</date>
  <millis>1623684246022</millis>
  <sequence>1</sequence>
  <logger>com.jul.JulTest</logger>
  <level>WARNING</level>
  <class>com.jul.JulTest</class>
  <method>test07</method>
  <thread>1</thread>
  <message>warning：警告信息</message>
</record>
</log>

```

**修改配置：**

```properties
# 指定输出日志内容的日志文件
java.util.logging.FileHandler.pattern = src\\jul3.log
# 将XML格式更改为Simple格式
java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter

```

**再次运行：** 查看 jul3.log 文件中的内容。

```
六月 14, 2021 11:28:58 下午 com.jul.JulTest test07
严重: severe：错误信息
六月 14, 2021 11:28:58 下午 com.jul.JulTest test07
警告: warning：警告信息

```

## 追加的日志信息

将 **test07()** 方法运行多次，会发现每次输出的日志内容会将上次的内容覆盖掉，这明显是不太好。

**添加配置：**

```properties
# 默认输出的日志内容会覆盖上次输出的内容, 设为true改为追加
java.util.logging.FileHandler.append=true

```

**多次运行 test07() 方法：** 发现 jul3.log 文件中内容并没有被覆盖，而是一直往后追加。

```
六月 14, 2021 11:47:17 下午 com.jul.JulTest test07
严重: severe：错误信息
六月 14, 2021 11:47:17 下午 com.jul.JulTest test07
警告: warning：警告信息
六月 14, 2021 11:47:22 下午 com.jul.JulTest test07
严重: severe：错误信息
六月 14, 2021 11:47:22 下午 com.jul.JulTest test07
警告: warning：警告信息
六月 14, 2021 11:47:33 下午 com.jul.JulTest test07
严重: severe：错误信息
六月 14, 2021 11:47:33 下午 com.jul.JulTest test07
警告: warning：警告信息

```

## 操作流程的总结

① 初始化 LogManager，LogManager 加载 logging.properties 配置文件，添加 Logger 到 LogManager 。

② 从单例的 LogManager 获取 Logger 。

③ Level 设置日志级别，在打印的过程中使用到了日志记录的 LogRecord 类。

④ Filter 作为过滤器提供了日志级别之外更细粒度的控制。

⑤ Handler 日志处理器，决定日志的输出格式，例如：XMLFormatter、SimpleFormatter；日志的输出位置，例如：控制台、文件…

⑥ Formatter 是用来格式化输出的日志内容。


















































































































