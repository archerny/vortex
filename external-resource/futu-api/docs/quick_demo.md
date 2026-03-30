[#](./quick_demo.md#7090)
 简易程序运行
=========================================================================

*   Python
*   C#
*   Java
*   C++
*   Proto
*   JavaScript

[#](./quick_demo.md#7846)
 Python 示例
----------------------------------------------------------------------------

### [#](./quick_demo.md#5519)
 第一步：下载安装登录 OpenD

请参考 [这里](./quick_opend-base.md)
，完成 OpenD 的下载、安装和登录。

### [#](./quick_demo.md#4688)
 第二步：下载 Python API

*   方式一：在 cmd 中直接使用 pip 安装。
    
    *   初次安装：Windows 系统 `$ pip install futu-api`，Linux/Mac系统 `$ pip3 install futu-api`。
    *   二次升级：Windows 系统 `$ pip install futu-api --upgrade`，Linux/Mac系统 `$ pip3 install futu-api --upgrade`。
*   方式二：点击下载最新版本的 [Python API](https://www.futunn.com/download/fetch-lasted-link?name=openapi-python)
     安装包。
    

### [#](./quick_demo.md#4480)
 第三步：创建新项目

打开 PyCharm，在 Welcome to PyCharm 窗口中，点击 New Project。如果你已经创建了一个项目，可以选择打开该项目。

![demo-newproject](https://openapi.futunn.com/futu-api-doc/assets/img/demo-newproject.2fb2c25b.png)

### [#](./quick_demo.md#6733)
 第四步：创建新文件

在该项目下，创建新 Python 文件，并把下面的示例代码拷贝到文件里。  
示例代码功能包括查看行情快照、模拟交易下单。

    from futu import *
    
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)  # 创建行情对象
    print(quote_ctx.get_market_snapshot('HK.00700'))  # 获取港股 HK.00700 的快照数据
    quote_ctx.close() # 关闭对象，防止连接条数用尽
    
    
    trd_ctx = OpenSecTradeContext(host='127.0.0.1', port=11111)  # 创建交易对象
    print(trd_ctx.place_order(price=500.0, qty=100, code="HK.00700", trd_side=TrdSide.BUY, trd_env=TrdEnv.SIMULATE))  # 模拟交易，下单（如果是真实环境交易，在此之前需要先解锁交易密码）
    
    trd_ctx.close()  # 关闭对象，防止连接条数用尽
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

### [#](./quick_demo.md#1469)
 第五步：运行文件

右键点击运行，可以看到运行成功的返回信息如下：

    2020-11-05 17:09:29,705 [open_context_base.py] _socket_reconnect_and_wait_ready:255: Start connecting: host=127.0.0.1; port=11111;
    2020-11-05 17:09:29,705 [open_context_base.py] on_connected:344: Connected : conn_id=1; 
    2020-11-05 17:09:29,706 [open_context_base.py] _handle_init_connect:445: InitConnect ok: conn_id=1; info={'server_version': 218, 'login_user_id': 7157878, 'conn_id': 6730043337026687703, 'conn_key': '3F17CF3EEF912C92', 'conn_iv': 'C119DDDD6314F18A', 'keep_alive_interval': 10, 'is_encrypt': False};
    (0,        code          update_time  last_price  open_price  high_price  ...  after_high_price  after_low_price  after_change_val  after_change_rate  after_amplitude
    0  HK.00700  2020-11-05 16:08:06       625.0       610.0       625.0  ...               N/A              N/A               N/A                N/A              N/A
    
    [1 rows x 132 columns])
    2020-11-05 17:09:29,739 [open_context_base.py] _socket_reconnect_and_wait_ready:255: Start connecting: host=127.0.0.1; port=11111;
    2020-11-05 17:09:29,739 [network_manager.py] work:366: Close: conn_id=1
    2020-11-05 17:09:29,739 [open_context_base.py] on_connected:344: Connected : conn_id=2; 
    2020-11-05 17:09:29,740 [open_context_base.py] _handle_init_connect:445: InitConnect ok: conn_id=2; info={'server_version': 218, 'login_user_id': 7157878, 'conn_id': 6730043337169705045, 'conn_key': 'A624CF3EEF91703C', 'conn_iv': 'BF1FF3806414617B', 'keep_alive_interval': 10, 'is_encrypt': False};
    (0,        code stock_name trd_side order_type order_status  ... dealt_avg_price  last_err_msg  remark time_in_force fill_outside_rth
    0  HK.00700       腾讯控股      BUY     NORMAL   SUBMITTING  ...             0.0                                 DAY              N/A
    
    [1 rows x 16 columns])
    2020-11-05 17:09:32,843 [network_manager.py] work:366: Close: conn_id=2
    (0,        code stock_name trd_side      order_type order_status  ... dealt_avg_price  last_err_msg  remark time_in_force fill_outside_rth
    0  HK.00700       腾讯控股      BUY  ABSOLUTE_LIMIT    SUBMITTED  ...             0.0                                 DAY              N/A
    
    [1 rows x 16 columns])
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  

[#](./quick_demo.md#1169)
 C# 示例
------------------------------------------------------------------------

### [#](./quick_demo.md#5519-2)
 第一步：下载安装登录 OpenD

请参考 [这里](./quick_opend-base.md)
，完成 OpenD 的下载、安装和登录。

### [#](./quick_demo.md#7339)
 第二步：下载 C# API

我们提供了两种方式下载和升级 C# API，您可以任选其一：

*   方式一：在 cmd 中直接使用 nuget 下载或升级 `$ dotnet add package futu-api`（nuget 仅支持 C# API 源码下载，如需获取更多的示例代码，请参考方式二）。
    
*   方式二：点击下载最新版本的 [C# API](https://www.futunn.com/download/fetch-lasted-link?name=openapi-csharp)
     安装包。解压下载好的 FTAPI 文件，`/FTAPI4NET` 是 C# API 的目录，具体目录结构请参见 [FTAPI4NET 目录结构](./quick_demo.md#1735)
    。
    

#### [#](./quick_demo.md#1735)
 FTAPI4NET 目录结构

    +---FTAPI4Net                                          FTAPI4NET 的源码。如果所用 .NET 版本不兼容，可以用源码重新编译出 FTAPI4Net.dll
    +---lib
        +---net4.5                                         .NET 4.5 下依赖的库
        |       FTAPI4Net.dll                              Futu API 的 C# 版本
        |       Google.ProtocolBuffers.dll                 第三方库，用于解析 protobuf 数据
        |       Google.ProtocolBuffers.Serialization.dll   第三方库，用于解析 protobuf 数据
        |
        +---netcore2.1                                     .NET Core 2.1 下依赖的库
        |       FTAPI4Net.dll                              Futu API 的 C# 版本
        |       Google.ProtocolBuffers.dll                 第三方库，用于解析 protobuf 数据
        |       Google.ProtocolBuffers.Serialization.dll   第三方库，用于解析 protobuf 数据
        |
        FTAPIChannel                                       FTAPI4NET 依赖的 C 动态库，用于 API 与 OpenD 通信，存放于各个操作系统命名的目录下。运行 .NET 程序前，需要将对应平台下的动态库复制到程序运行的当前目录中。
    +---Sample                                             示例工程
    +---tools                                              第三方工具，用于将 proto 协议转为 C# 代码，详见此目录下的 readme.md 说明
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

### [#](./quick_demo.md#3373)
 第三步：导入示例项目

打开 Visual Studio 开发环境，导入目录下的项目文件 FTAPI4NetCore.sln。

### [#](./quick_demo.md#4390)
 第四步：运行

首先，检查文件中跑的示例程序中使用的接口配置是否跟 OpenD 的配置一致。

其次，检查 Sample 项目下的 Program.cs 文件中 Main 函数里需要运行的功能。运行后，可以看到如下打印信息：

    InitConnected
    Send QotGetUserSecurity: 3
    {"staticInfoList":[{"basic":{"security":{"market":1,"code":"08311"},"id":69179038244983,"lotSize":10000,"secType":3,"name":"\u5706\u7f8e\u5149\u7535","listTime":"2014-02-07","delisting":false,"listTimestamp":1391702400}},{"basic":{"security":{"market":1,"code":"02127"},"id":79925046413391,"lotSize":2000,"secType":3,"name":"\u6c47\u68ee\u5bb6\u5c45","listTime":"2020-12-\
    29","delisting":false,"listTimestamp":1609171200}},{"basic":{"security":{"market":1,"code":"EVG2101"},"id":71002729,"lotSize":2000,"secType":10,"name":"\u4e2d\u56fd\u6052\u5927\u96c6\u56e22101","listTime":"","delisting":false},"futureExData":{"lastTradeTime":"2021-01-28","lastTradeTimestamp":1611820800,"isMainContract":false}},{"basic":{"security":{"market":1,"code":"0\
    2333"},"id":53257594472733,"lotSize":500,"secType":3,"name":"\u957f\u57ce\u6c7d\u8f66","listTime":"2003-12-15","delisting":false,"listTimestamp":1071417600}},{"basic":{"security":{"market":1,"code":"06993"},"id":79882096745297,"lotSize":500,"secType":3,"name":"\u84dd\u6708\u4eae\u96c6\u56e2","listTime":"2020-12-16","delisting":false,"listTimestamp":1608048000}},{"basic\
    ":{"security":{"market":1,"code":"01810"},"id":76033806042898,"lotSize":200,"secType":3,"name":"\u5c0f\u7c73\u96c6\u56e2-W","listTime":"2018-07-09","delisting":false,"listTimestamp":1531065600}},{"basic":{"security":{"market":1,"code":"09992"},"id":79869211846408,"lotSize":200,"secType":3,"name":"\u6ce1\u6ce1\u739b\u7279","listTime":"2020-12-11","delisting":false,"list\
    Timestamp":1607616000}},{"basic":{"security":{"market":1,"code":"03948"},"id":66709432045420,"lotSize":100,"secType":3,"name":"\u4f0a\u6cf0\u7164\u70ad","listTime":"2012-07-12","delisting":false,"listTimestamp":1342022400}},{"basic":{"security":{"market":1,"code":"01398"},"id":57754425230710,"lotSize":1000,"secType":3,"name":"\u5de5\u5546\u94f6\u884c","listTime":"2006-\
    10-27","delisting":false,"listTimestamp":1161878400}},{"basic":{"security":{"market":1,"code":"06618"},"id":79843442039258,"lotSize":50,"secType":3,"name":"\u4eac\u4e1c\u5065\u5eb7","listTime":"2020-12-08","delisting":false,"listTimestamp":1607356800}},{"basic":{"security":{"market":1,"code":"09698"},"id":79693118186978,"lotSize":100,"secType":3,"name":"\u4e07\u56fd\u6\
    570\u636e-SW","listTime":"2020-11-02","delisting":false,"listTimestamp":1604246400}},{"basic":{"security":{"market":1,"code":"03690"},"id":76364518526570,"lotSize":100,"secType":3,"name":"\u7f8e\u56e2-W","listTime":"2018-09-20","delisting":false,"listTimestamp":1537372800}},{"basic":{"security":{"market":1,"code":"09988"},"id":78224239372036,"lotSize":100,"secType":3,"\
    name":"\u963f\u91cc\u5df4\u5df4-SW","listTime":"2019-11-26","delisting":false,"listTimestamp":1574697600}},{"basic":{"security":{"market":1,"code":"800000"},"id":800000,"lotSize":0,"secType":6,"name":"\u6052\u751f\u6307\u6570","listTime":"1970-01-01","delisting":false,"listTimestamp":0}},{"basic":{"security":{"market":1,"code":"00005"},"id":5,"lotSize":400,"secType":3,\
    "name":"\u6c47\u4e30\u63a7\u80a1","listTime":"1970-01-01","delisting":false,"listTimestamp":0}},{"basic":{"security":{"market":1,"code":"00175"},"id":4930622455983,"lotSize":1000,"secType":3,"name":"\u5409\u5229\u6c7d\u8f66","listTime":"1973-02-23","delisting":false,"listTimestamp":99244800}},{"basic":{"security":{"market":1,"code":"09677"},"id":79598628906445,"lotSize\
    ":1000,"secType":3,"name":"\u5a01\u6d77\u94f6\u884c","listTime":"2020-10-12","delisting":false,"listTimestamp":1602432000}},{"basic":{"security":{"market":1,"code":"06055"},"id":77494094927783,"lotSize":1000,"secType":3,"name":"\u4e2d\u70df\u9999\u6e2f","listTime":"2019-06-12","delisting":false,"listTimestamp":1560268800}},{"basic":{"security":{"market":1,"code":"00788\
    "},"id":76175539962644,"lotSize":2000,"secType":3,"name":"\u4e2d\u56fd\u94c1\u5854","listTime":"2018-08-08","delisting":false,"listTimestamp":1533657600}},{"basic":{"security":{"market":1,"code":"02318"},"id":54082228193550,"lotSize":500,"secType":3,"name":"\u4e2d\u56fd\u5e73\u5b89","listTime":"2004-06-24","delisting":false,"listTimestamp":1088006400}},{"basic":{"secur\
    ity":{"market":1,"code":"BK1011"},"id":10001011,"lotSize":0,"secType":7,"name":"\u5316\u80a5\u53ca\u519c\u7528\u5316\u5408\u7269","listTime":"1970-01-01","delisting":false,"listTimestamp":0}},{"basic":{"security":{"market":1,"code":"BK1095"},"id":10001095,"lotSize":0,"secType":7,"name":"\u697c\u5b87\u5efa\u9020","listTime":"1970-01-01","delisting":false,"listTimestamp"\
    :0}},{"basic":{"security":{"market":1,"code":"01328"},"id":59274843653424,"lotSize":10000,"secType":3,"name":"\u91d1\u6d8c\u6295\u8d44","listTime":"2007-10-16","delisting":false,"listTimestamp":1192464000}},{"basic":{"security":{"market":1,"code":"00900"},"id":40312563041156,"lotSize":2000,"secType":3,"name":"AEON\u4fe1\u8d37","listTime":"1995-09-14","delisting":false,\
    "listTimestamp":811008000}},{"basic":{"security":{"market":1,"code":"00030"},"id":34144990003230,"lotSize":2000,"secType":3,"name":"\u4e07\u9686\u63a7\u80a1\u96c6\u56e2","listTime":"1991-10-09","delisting":false,"listTimestamp":686937600}},{"basic":{"security":{"market":1,"code":"00028"},"id":26989574488092,"lotSize":1000,"secType":3,"name":"\u5929\u5b89","listTime":"1\
    987-03-18","delisting":false,"listTimestamp":542995200}},{"basic":{"security":{"market":1,"code":"00519"},"id":25447681229319,"lotSize":5000,"secType":3,"name":"\u5b9e\u529b\u5efa\u4e1a","listTime":"1986-03-24","delisting":false,"listTimestamp":511977600}},{"basic":{"security":{"market":1,"code":"00276"},"id":4140348473620,"lotSize":3000,"secType":3,"name":"\u8499\u53e\
    4\u80fd\u6e90","listTime":"1972-08-23","delisting":false,"listTimestamp":83347200}},{"basic":{"security":{"market":1,"code":"06862"},"id":76385993366222,"lotSize":1000,"secType":3,"name":"\u6d77\u5e95\u635e","listTime":"2018-09-26","delisting":false,"listTimestamp":1537891200}},{"basic":{"security":{"market":1,"code":"00451"},"id":34866544509379,"lotSize":2000,"secType\
    ":3,"name":"\u534f\u946b\u65b0\u80fd\u6e90","listTime":"1992-03-25","delisting":false,"listTimestamp":701452800}},{"basic":{"security":{"market":1,"code":"02899"},"id":53291954211667,"lotSize":2000,"secType":3,"name":"\u7d2b\u91d1\u77ff\u4e1a","listTime":"2003-12-23","delisting":false,"listTimestamp":1072108800}},{"basic":{"security":{"market":1,"code":"08133"},"id":71\
    098888626117,"lotSize":20000,"secType":3,"name":"\u94f8\u80fd\u63a7\u80a1","listTime":"2015-04-30","delisting":false,"listTimestamp":1430323200}},{"basic":{"security":{"market":1,"code":"01432"},"id":69857643070872,"lotSize":1000,"secType":3,"name":"\u4e2d\u56fd\u5723\u7267","listTime":"2014-07-15","delisting":false,"listTimestamp":1405353600}},{"basic":{"security":{"m\
    arket":1,"code":"09933"},"id":78426102834893,"lotSize":4000,"secType":3,"name":"GHW INTL","listTime":"2020-01-21","delisting":false,"listTimestamp":1579536000}},{"basic":{"security":{"market":1,"code":"07500"},"id":77494094929228,"lotSize":100,"secType":4,"name":"\u5357\u65b9\u4e24\u500d\u770b\u7a7a\u6052\u6307","listTime":"2019-05-28","delisting":false,"listTimestamp"\
    :1558972800}},{"basic":{"security":{"market":1,"code":"00981"},"id":53661321397205,"lotSize":500,"secType":3,"name":"\u4e2d\u82af\u56fd\u9645","listTime":"2004-03-18","delisting":false,"listTimestamp":1079539200}}]}
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  

提示

*   如果遇到找不到 FTAPIChannel 动态库文件，需要从 lib 文件夹里的对应平台的 FTAPIChannel 文件，拷贝到程序运行的当前目录下。

[#](./quick_demo.md#3369)
 Java 示例
--------------------------------------------------------------------------

### [#](./quick_demo.md#5519-3)
 第一步：下载安装登录 OpenD

请参考 [这里](./quick_opend-base.md)
，完成 OpenD 的下载、安装和登录。

### [#](./quick_demo.md#4042)
 第二步：下载 Java API

我们提供了两种方式下载和升级 Java API，您可以任选其一：

#### [#](./quick_demo.md#5757)
 方式一：通过 maven 仓库配置

1.  在 [maven](https://search.maven.org/artifact/com.futunn.openapi/futu-api)
     中选择最新的 futu-api 版本，点击进入。
2.  将网页右侧的相关配置复制添加至您的工程设置中。  
    举例：使用 Apache Maven 来管理项目的用户，可以将下图红框中的代码复制至您的工程设置中。 ![maven](https://openapi.futunn.com/futu-api-doc/assets/img/maven.75366f6c.png)

#### [#](./quick_demo.md#3963)
 方式二：下载 Java API

1.  点击下载最新版本的 [Java API](https://www.futunn.com/download/fetch-lasted-link?name=openapi-java)
     安装包。
2.  解压下载好的 FTAPI 文件，`/FTAPI4J` 是 Java API 的目录，将 [FTAPI4J 目录结构](./quick_demo.md#4144)
     中的 `/lib/futu-api-.x.y.z.jar` 添加到您的工程设置中。

#### [#](./quick_demo.md#4144)
 FTAPI4J 目录结构

    +---ftapi4j                      futu-api 源码，如果所用 JDK 版本不兼容，您可以用这里的工程重新编译出 futu-api.jar
    +---lib                          存放公共库文件
    |    futu-api-x.y.z.jar          Futu API 的 Java 版本
    |    bcprov-jdk15on-1.68.jar     第三方库，用于加解密
    |    bcpkix-jdk15on-1.68.jar     第三方库，用于加解密
    |    protobuf-java-3.5.1.jar     第三方库，用于解析 protobuf 数据
    +---sample                       示例工程
    +---resources                    maven 工程默认生成的目录
    

1  
2  
3  
4  
5  
6  
7  
8  

### [#](./quick_demo.md#2927)
 第三步：创建 futu-api 工程

以 IntelliJ IDEA 为例：

1.  新建工程：  
    ![new_java](https://openapi.futunn.com/futu-api-doc/assets/img/nnnew_java.66996fc6.png)
    
2.  选择 maven 项目：  
    ![maven_proj](https://openapi.futunn.com/futu-api-doc/assets/img/maven_proj.68228f60.png)
    
3.  选择项目路径：  
    ![loc_java](https://openapi.futunn.com/futu-api-doc/assets/img/nnloc_java.6dbdfa89.png)
    
4.  选择 maven 工具，这里采用 IDEA 的自带工具：  
    ![tool_java](https://openapi.futunn.com/futu-api-doc/assets/img/tool_java.641bfef3.png)
    
5.  进入项目，等待项目初始化完成，完成后下方窗口出现 “BUILD SUCCESS” 字样：  
    ![suc_java](https://openapi.futunn.com/futu-api-doc/assets/img/suc_java.04e106fd.png)
    
6.  配置 futu-api
    

*   若您是通过 maven 仓库进行配置：  
    直接编辑 pom.xml 文件，在 dependencies 中插入futu-api 依赖（x.y.z 替换为版本号）：  
    ![maven_futu](https://openapi.futunn.com/futu-api-doc/assets/img/maven_futu.12353f70.png)
    
*   若您是通过官网下载 Java API：  
    （1）在项目下创建 /lib/ 目录，将 **/FTAPI4J/lib/futu-api-x.y.z.jar** 文件复制到 /lib/ 目录下（例如：下图中的 futu-api-5.4.1607.jar）。  
    （2）编辑 pom.xml 文件，在 dependencies 中插入 futu-api 依赖。  
    ![maven_api](https://openapi.futunn.com/futu-api-doc/assets/img/maven_api.e3682d1f.png)
    

### [#](./quick_demo.md#6041)
 第四步：导入示例项目

示例提供了 maven 编译脚本，可以用支持 maven 的 IDE 导入 `/sample` 目录下的工程。

### [#](./quick_demo.md#9038)
 第五步：运行

1.  检查 main.java 文件中跑的示例程序中使用的配置是否跟 OpenD 的配置一致。
    
2.  运行 `/sample` 目录下的 main.java 文件，可以看到运行成功的返回信息如下：
    

    Qot onInitConnect: ret=true desc=Succeed! connID=6750011030360491012
    onReply_GetMarketState: retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      marketInfoList {
        security {
          market: 1
          code: "00700"
        }
        name: "\350\205\276\350\256\257\346\216\247\350\202\241"
        marketState: 6
      }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

提示

*   如果遇到找不到 FTAPIChannel 动态库文件，需要将 `/lib` 文件夹里的对应平台的 FTAPIChannel 文件，拷贝到程序运行的当前目录下。

[#](./quick_demo.md#5895)
 C++ 示例
-------------------------------------------------------------------------

### [#](./quick_demo.md#5519-4)
 第一步：下载安装登录 OpenD

请参考 [这里](./quick_opend-base.md)
，完成 OpenD 的下载、安装和登录。

### [#](./quick_demo.md#4792)
 第二步：下载 C++ API

下载最新版本的 [C++ API](https://www.futunn.com/download/fetch-lasted-link?name=openapi-cpp)
 安装包。

解压下载好的 FTAPI 文件，`/FTAPI4CPP` 是 C++ API 的目录，具体目录结构请参见 [FTAPI4CPP 目录结构](./quick_demo.md#227)
。

#### [#](./quick_demo.md#227)
 FTAPI4CPP 目录结构

    +---Bin                  存放各种系统默认编译环境编译出的公共库文件
    +---Include              存放公共头文件，以及 proto 协议生成的 .h/.cc 文件的目录
    +---Sample               示例工程
    +---Src                  源码
        +---FTAPI            FTAPI 源码
        +---protobuf         protobuf 源码
    

1  
2  
3  
4  
5  
6  

### [#](./quick_demo.md#3373-2)
 第三步：导入示例项目

示例提供了 Visual Studio 和 XCode 的工程文件，可以用 Visual Studio 或 XCode 开发环境导入 Sample 项目。

### [#](./quick_demo.md#4390-2)
 第四步：运行

1.  检查 simpleSample.cpp 文件中跑的示例程序中使用的接口配置是否跟 OpenD 的配置一致。
    
2.  运行后，可以看到运行成功的返回信息如下：
    

    Run GetSecuritySnapshotDemo
    InitQot, suc = 1
    GetHKEqtySecList, count = 2698
    GetSecuritySnapshot, i = 0
    GetSecuritySnapshot, i = 1
    GetSecuritySnapshot, i = 2
    GetSecuritySnapshot, i = 3
    GetSecuritySnapshot, i = 4
    GetSecuritySnapshot, i = 5
    GetSecuritySnapshot, i = 6
    GetSecuritySnapshot, i = 7
    GetSecuritySnapshot, i = 8
    GetSecuritySnapshot, i = 9
    GetSecuritySnapshot, i = 10
    GetSecuritySnapshot, i = 11
    GetSecuritySnapshot, i = 12
    GetSecuritySnapshot, i = 13
    ParseHKEqtySecQot, first = (HK.00001, 56.65)
    GetSecuritySnapshotDemo End
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  

点击下载最新版本的[Protobuf API](https://www.futunn.com/download/fetch-lasted-link?name=openapi-protobuf)
 安装包。

[#](./quick_demo.md#6969)
 JavaScript 示例
--------------------------------------------------------------------------------

### [#](./quick_demo.md#5519-5)
 第一步：下载安装登录 OpenD

请参考 [这里](./quick_opend-base.md)
，完成 OpenD 的下载、安装和登录。

### [#](./quick_demo.md#7122)
 第二步：下载 JavaScript API

*   方式一：在 cmd 中直接使用 npm 安装或升级（npm 仅支持 JavaScript API 源码的下载，如需获取更多的示例代码，请参考方式二）。
    
    *   初次安装： `$ npm install --save futu-api`。
    *   二次升级：`$ npm update futu-api`。
*   方式二：点击下载最新版本的 [JavaScript API](https://www.futunn.com/download/fetch-lasted-link?name=openapi-js)
     安装包。
    

解压下载好的 FTAPI 文件，`/FTAPI4JS`是 JavaScript API 的目录，具体目录结构请参见 [FTAPI4JS 目录结构](./quick_demo.md#147)
。

#### [#](./quick_demo.md#147)
 FTAPI4JS 目录结构

    +---Sample               示例工程    
    +---src                  源码
    

1  
2  

### [#](./quick_demo.md#1856)
 第三步：运行示例

1.  使用 Visual Studio Code 打开示例代码目录 `FTAPI4JS/sample`。
    
2.  找到每个 Demo 文件中启动 WebSocket 相关代码，如下图，修改成 OpenD 对应的配置（注意：可视化 OpenD 默认会启动 WebSocket，命令行 OpenD 则需要 [配置参数](./opend_opend-cmd.md#8799)
     自行启动 WebSocket）。 ![websocket-demo-config](https://openapi.futunn.com/futu-api-doc/assets/img/websocket-demo-config.ceb49a91.png)
    
3.  在 Visual Studio Code 控制台中输入 `npm install` 安装项目依赖。 ![websocket-demo-depend](https://openapi.futunn.com/futu-api-doc/assets/img/websocket-demo-depend.a7efb4a8.png)
    
4.  在 Visual Studio Code 控制台中输 `npm run serve` 运行项目。 ![websocket-demo-run](https://openapi.futunn.com/futu-api-doc/assets/img/websocket-demo-run.aad8450c.png)
    
5.  项目运行成功后，打开对应的服务地址。 ![websocket-demo-suc](https://openapi.futunn.com/futu-api-doc/assets/img/websocket-demo-suc.3a16cde3.png)
    
6.  在页面上体验相关功能。  
    ![websocket-demo-page](https://openapi.futunn.com/futu-api-doc/assets/img/websocket-demo-page.880d727c.png)
    

*   Python
*   C#
*   Java
*   C++
*   Proto
*   JavaScript

[#](./quick_demo.md#7846-2)
 Python 示例
------------------------------------------------------------------------------

### [#](./quick_demo.md#5519-6)
 第一步：下载安装登录 OpenD

请参考 [这里](./quick_opend-base.md)
，完成 OpenD 的下载、安装和登录。

### [#](./quick_demo.md#4688-2)
 第二步：下载 Python API

*   方式一：在 cmd 中直接使用 pip 安装。
    
    *   初次安装：Windows 系统 `$ pip install moomoo-api`，Linux/Mac系统 `$ pip3 install moomoo-api`。
    *   二次升级：Windows 系统 `$ pip install moomoo-api --upgrade`，Linux/Mac系统 `$ pip3 install moomoo-api --upgrade`。
*   方式二：通过 [moomoo 官网](https://www.moomoo.com/download/OpenAPI)
     下载最新版本的 Python API。
    

### [#](./quick_demo.md#4480-2)
 第三步：创建新项目

打开 PyCharm，在 Welcome to PyCharm 窗口中，点击 New Project。如果你已经创建了一个项目，可以选择打开该项目。

![demo-newproject](https://openapi.futunn.com/futu-api-doc/assets/img/demo-newproject.2fb2c25b.png)

### [#](./quick_demo.md#6733-2)
 第四步：创建新文件

在该项目下，创建新 Python 文件，并把下面的示例代码拷贝到文件里。  
示例代码功能包括查看行情快照、模拟交易下单。

    from moomoo import *
    
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)  # 创建行情对象
    print(quote_ctx.get_market_snapshot('HK.00700'))  # 获取港股 HK.00700 的快照数据
    quote_ctx.close() # 关闭对象，防止连接条数用尽
    
    
    trd_ctx = OpenSecTradeContext(host='127.0.0.1', port=11111)  # 创建交易对象
    print(trd_ctx.place_order(price=500.0, qty=100, code="HK.00700", trd_side=TrdSide.BUY, trd_env=TrdEnv.SIMULATE))  # 模拟交易，下单（如果是真实环境交易，在此之前需要先解锁交易密码）
    
    trd_ctx.close()  # 关闭对象，防止连接条数用尽
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

### [#](./quick_demo.md#1469-2)
 第五步：运行文件

右键点击运行，可以看到运行成功的返回信息如下：

    2020-11-05 17:09:29,705 [open_context_base.py] _socket_reconnect_and_wait_ready:255: Start connecting: host=127.0.0.1; port=11111;
    2020-11-05 17:09:29,705 [open_context_base.py] on_connected:344: Connected : conn_id=1; 
    2020-11-05 17:09:29,706 [open_context_base.py] _handle_init_connect:445: InitConnect ok: conn_id=1; info={'server_version': 218, 'login_user_id': 7157878, 'conn_id': 6730043337026687703, 'conn_key': '3F17CF3EEF912C92', 'conn_iv': 'C119DDDD6314F18A', 'keep_alive_interval': 10, 'is_encrypt': False};
    (0,        code          update_time  last_price  open_price  high_price  ...  after_high_price  after_low_price  after_change_val  after_change_rate  after_amplitude
    0  HK.00700  2020-11-05 16:08:06       625.0       610.0       625.0  ...               N/A              N/A               N/A                N/A              N/A
    
    [1 rows x 132 columns])
    2020-11-05 17:09:29,739 [open_context_base.py] _socket_reconnect_and_wait_ready:255: Start connecting: host=127.0.0.1; port=11111;
    2020-11-05 17:09:29,739 [network_manager.py] work:366: Close: conn_id=1
    2020-11-05 17:09:29,739 [open_context_base.py] on_connected:344: Connected : conn_id=2; 
    2020-11-05 17:09:29,740 [open_context_base.py] _handle_init_connect:445: InitConnect ok: conn_id=2; info={'server_version': 218, 'login_user_id': 7157878, 'conn_id': 6730043337169705045, 'conn_key': 'A624CF3EEF91703C', 'conn_iv': 'BF1FF3806414617B', 'keep_alive_interval': 10, 'is_encrypt': False};
    (0,        code stock_name trd_side order_type order_status  ... dealt_avg_price  last_err_msg  remark time_in_force fill_outside_rth
    0  HK.00700       腾讯控股      BUY     NORMAL   SUBMITTING  ...             0.0                                 DAY              N/A
    
    [1 rows x 16 columns])
    2020-11-05 17:09:32,843 [network_manager.py] work:366: Close: conn_id=2
    (0,        code stock_name trd_side      order_type order_status  ... dealt_avg_price  last_err_msg  remark time_in_force fill_outside_rth
    0  HK.00700       腾讯控股      BUY  ABSOLUTE_LIMIT    SUBMITTED  ...             0.0                                 DAY              N/A
    
    [1 rows x 16 columns])
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  

[#](./quick_demo.md#1169-2)
 C# 示例
--------------------------------------------------------------------------

### [#](./quick_demo.md#5519-7)
 第一步：下载安装登录 OpenD

请参考 [这里](./quick_opend-base.md)
，完成 OpenD 的下载、安装和登录。

### [#](./quick_demo.md#7339-2)
 第二步：下载 C# API

我们提供了两种方式下载和升级 C# API，您可以任选其一：

*   方式一：在 cmd 中直接使用 nuget 下载或升级 `$ dotnet add package moomoo-api`（nuget 仅支持 C# API 源码下载，如需获取更多的示例代码，请参考方式二）。
    
*   方式二：通过 [moomoo 官网](https://www.moomoo.com/download/OpenAPI)
     下载最新版本的 C# API。解压下载好的 MMAPI 文件，`/MMAPI4NET` 是 C# API 的目录，具体目录结构请参见 [MMAPI4NET 目录结构](./quick_demo.md#1735)
    。
    

#### [#](./quick_demo.md#1883)
 MMAPI4NET 目录结构

    +---MMAPI4Net                                          MMAPI4NET 的源码。如果所用 .NET 版本不兼容，可以用源码重新编译出 MMAPI4Net.dll
    +---lib
        +---net4.5                                         .NET 4.5 下依赖的库
        |       MMAPI4Net.dll                              Moomoo API 的 C# 版本
        |       Google.ProtocolBuffers.dll                 第三方库，用于解析 protobuf 数据
        |       Google.ProtocolBuffers.Serialization.dll   第三方库，用于解析 protobuf 数据
        |
        +---netcore2.1                                     .NET Core 2.1 下依赖的库
        |       MMAPI4Net.dll                              Moomoo API 的 C# 版本
        |       Google.ProtocolBuffers.dll                 第三方库，用于解析 protobuf 数据
        |       Google.ProtocolBuffers.Serialization.dll   第三方库，用于解析 protobuf 数据
        |
        MMAPIChannel                                       MMAPI4NET 依赖的 C 动态库，用于 API 与 OpenD 通信，存放于各个操作系统命名的目录下。运行 .NET 程序前，需要将对应平台下的动态库复制到程序运行的当前目录中。
    +---Sample                                             示例工程
    +---tools                                              第三方工具，用于将 proto 协议转为 C# 代码，详见此目录下的 readme.md 说明
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

### [#](./quick_demo.md#3373-3)
 第三步：导入示例项目

打开 Visual Studio 开发环境，导入目录下的项目文件 mmAPI4NetCore.sln。

### [#](./quick_demo.md#4390-3)
 第四步：运行

首先，检查文件中跑的示例程序中使用的接口配置是否跟 OpenD 的配置一致。

其次，检查 Sample 项目下的 Program.cs 文件中 Main 函数里需要运行的功能。运行后，可以看到如下打印信息：

    InitConnected
    Send QotGetUserSecurity: 3
    {"staticInfoList":[{"basic":{"security":{"market":1,"code":"08311"},"id":69179038244983,"lotSize":10000,"secType":3,"name":"\u5706\u7f8e\u5149\u7535","listTime":"2014-02-07","delisting":false,"listTimestamp":1391702400}},{"basic":{"security":{"market":1,"code":"02127"},"id":79925046413391,"lotSize":2000,"secType":3,"name":"\u6c47\u68ee\u5bb6\u5c45","listTime":"2020-12-\
    29","delisting":false,"listTimestamp":1609171200}},{"basic":{"security":{"market":1,"code":"EVG2101"},"id":71002729,"lotSize":2000,"secType":10,"name":"\u4e2d\u56fd\u6052\u5927\u96c6\u56e22101","listTime":"","delisting":false},"futureExData":{"lastTradeTime":"2021-01-28","lastTradeTimestamp":1611820800,"isMainContract":false}},{"basic":{"security":{"market":1,"code":"0\
    2333"},"id":53257594472733,"lotSize":500,"secType":3,"name":"\u957f\u57ce\u6c7d\u8f66","listTime":"2003-12-15","delisting":false,"listTimestamp":1071417600}},{"basic":{"security":{"market":1,"code":"06993"},"id":79882096745297,"lotSize":500,"secType":3,"name":"\u84dd\u6708\u4eae\u96c6\u56e2","listTime":"2020-12-16","delisting":false,"listTimestamp":1608048000}},{"basic\
    ":{"security":{"market":1,"code":"01810"},"id":76033806042898,"lotSize":200,"secType":3,"name":"\u5c0f\u7c73\u96c6\u56e2-W","listTime":"2018-07-09","delisting":false,"listTimestamp":1531065600}},{"basic":{"security":{"market":1,"code":"09992"},"id":79869211846408,"lotSize":200,"secType":3,"name":"\u6ce1\u6ce1\u739b\u7279","listTime":"2020-12-11","delisting":false,"list\
    Timestamp":1607616000}},{"basic":{"security":{"market":1,"code":"03948"},"id":66709432045420,"lotSize":100,"secType":3,"name":"\u4f0a\u6cf0\u7164\u70ad","listTime":"2012-07-12","delisting":false,"listTimestamp":1342022400}},{"basic":{"security":{"market":1,"code":"01398"},"id":57754425230710,"lotSize":1000,"secType":3,"name":"\u5de5\u5546\u94f6\u884c","listTime":"2006-\
    10-27","delisting":false,"listTimestamp":1161878400}},{"basic":{"security":{"market":1,"code":"06618"},"id":79843442039258,"lotSize":50,"secType":3,"name":"\u4eac\u4e1c\u5065\u5eb7","listTime":"2020-12-08","delisting":false,"listTimestamp":1607356800}},{"basic":{"security":{"market":1,"code":"09698"},"id":79693118186978,"lotSize":100,"secType":3,"name":"\u4e07\u56fd\u6\
    570\u636e-SW","listTime":"2020-11-02","delisting":false,"listTimestamp":1604246400}},{"basic":{"security":{"market":1,"code":"03690"},"id":76364518526570,"lotSize":100,"secType":3,"name":"\u7f8e\u56e2-W","listTime":"2018-09-20","delisting":false,"listTimestamp":1537372800}},{"basic":{"security":{"market":1,"code":"09988"},"id":78224239372036,"lotSize":100,"secType":3,"\
    name":"\u963f\u91cc\u5df4\u5df4-SW","listTime":"2019-11-26","delisting":false,"listTimestamp":1574697600}},{"basic":{"security":{"market":1,"code":"800000"},"id":800000,"lotSize":0,"secType":6,"name":"\u6052\u751f\u6307\u6570","listTime":"1970-01-01","delisting":false,"listTimestamp":0}},{"basic":{"security":{"market":1,"code":"00005"},"id":5,"lotSize":400,"secType":3,\
    "name":"\u6c47\u4e30\u63a7\u80a1","listTime":"1970-01-01","delisting":false,"listTimestamp":0}},{"basic":{"security":{"market":1,"code":"00175"},"id":4930622455983,"lotSize":1000,"secType":3,"name":"\u5409\u5229\u6c7d\u8f66","listTime":"1973-02-23","delisting":false,"listTimestamp":99244800}},{"basic":{"security":{"market":1,"code":"09677"},"id":79598628906445,"lotSize\
    ":1000,"secType":3,"name":"\u5a01\u6d77\u94f6\u884c","listTime":"2020-10-12","delisting":false,"listTimestamp":1602432000}},{"basic":{"security":{"market":1,"code":"06055"},"id":77494094927783,"lotSize":1000,"secType":3,"name":"\u4e2d\u70df\u9999\u6e2f","listTime":"2019-06-12","delisting":false,"listTimestamp":1560268800}},{"basic":{"security":{"market":1,"code":"00788\
    "},"id":76175539962644,"lotSize":2000,"secType":3,"name":"\u4e2d\u56fd\u94c1\u5854","listTime":"2018-08-08","delisting":false,"listTimestamp":1533657600}},{"basic":{"security":{"market":1,"code":"02318"},"id":54082228193550,"lotSize":500,"secType":3,"name":"\u4e2d\u56fd\u5e73\u5b89","listTime":"2004-06-24","delisting":false,"listTimestamp":1088006400}},{"basic":{"secur\
    ity":{"market":1,"code":"BK1011"},"id":10001011,"lotSize":0,"secType":7,"name":"\u5316\u80a5\u53ca\u519c\u7528\u5316\u5408\u7269","listTime":"1970-01-01","delisting":false,"listTimestamp":0}},{"basic":{"security":{"market":1,"code":"BK1095"},"id":10001095,"lotSize":0,"secType":7,"name":"\u697c\u5b87\u5efa\u9020","listTime":"1970-01-01","delisting":false,"listTimestamp"\
    :0}},{"basic":{"security":{"market":1,"code":"01328"},"id":59274843653424,"lotSize":10000,"secType":3,"name":"\u91d1\u6d8c\u6295\u8d44","listTime":"2007-10-16","delisting":false,"listTimestamp":1192464000}},{"basic":{"security":{"market":1,"code":"00900"},"id":40312563041156,"lotSize":2000,"secType":3,"name":"AEON\u4fe1\u8d37","listTime":"1995-09-14","delisting":false,\
    "listTimestamp":811008000}},{"basic":{"security":{"market":1,"code":"00030"},"id":34144990003230,"lotSize":2000,"secType":3,"name":"\u4e07\u9686\u63a7\u80a1\u96c6\u56e2","listTime":"1991-10-09","delisting":false,"listTimestamp":686937600}},{"basic":{"security":{"market":1,"code":"00028"},"id":26989574488092,"lotSize":1000,"secType":3,"name":"\u5929\u5b89","listTime":"1\
    987-03-18","delisting":false,"listTimestamp":542995200}},{"basic":{"security":{"market":1,"code":"00519"},"id":25447681229319,"lotSize":5000,"secType":3,"name":"\u5b9e\u529b\u5efa\u4e1a","listTime":"1986-03-24","delisting":false,"listTimestamp":511977600}},{"basic":{"security":{"market":1,"code":"00276"},"id":4140348473620,"lotSize":3000,"secType":3,"name":"\u8499\u53e\
    4\u80fd\u6e90","listTime":"1972-08-23","delisting":false,"listTimestamp":83347200}},{"basic":{"security":{"market":1,"code":"06862"},"id":76385993366222,"lotSize":1000,"secType":3,"name":"\u6d77\u5e95\u635e","listTime":"2018-09-26","delisting":false,"listTimestamp":1537891200}},{"basic":{"security":{"market":1,"code":"00451"},"id":34866544509379,"lotSize":2000,"secType\
    ":3,"name":"\u534f\u946b\u65b0\u80fd\u6e90","listTime":"1992-03-25","delisting":false,"listTimestamp":701452800}},{"basic":{"security":{"market":1,"code":"02899"},"id":53291954211667,"lotSize":2000,"secType":3,"name":"\u7d2b\u91d1\u77ff\u4e1a","listTime":"2003-12-23","delisting":false,"listTimestamp":1072108800}},{"basic":{"security":{"market":1,"code":"08133"},"id":71\
    098888626117,"lotSize":20000,"secType":3,"name":"\u94f8\u80fd\u63a7\u80a1","listTime":"2015-04-30","delisting":false,"listTimestamp":1430323200}},{"basic":{"security":{"market":1,"code":"01432"},"id":69857643070872,"lotSize":1000,"secType":3,"name":"\u4e2d\u56fd\u5723\u7267","listTime":"2014-07-15","delisting":false,"listTimestamp":1405353600}},{"basic":{"security":{"m\
    arket":1,"code":"09933"},"id":78426102834893,"lotSize":4000,"secType":3,"name":"GHW INTL","listTime":"2020-01-21","delisting":false,"listTimestamp":1579536000}},{"basic":{"security":{"market":1,"code":"07500"},"id":77494094929228,"lotSize":100,"secType":4,"name":"\u5357\u65b9\u4e24\u500d\u770b\u7a7a\u6052\u6307","listTime":"2019-05-28","delisting":false,"listTimestamp"\
    :1558972800}},{"basic":{"security":{"market":1,"code":"00981"},"id":53661321397205,"lotSize":500,"secType":3,"name":"\u4e2d\u82af\u56fd\u9645","listTime":"2004-03-18","delisting":false,"listTimestamp":1079539200}}]}
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  

提示

*   如果遇到找不到 MMAPIChannel 动态库文件，需要从 lib 文件夹里的对应平台的 MMAPIChannel 文件，拷贝到程序运行的当前目录下。

[#](./quick_demo.md#3369-2)
 Java 示例
----------------------------------------------------------------------------

### [#](./quick_demo.md#5519-8)
 第一步：下载安装登录 OpenD

请参考 [这里](./quick_opend-base.md)
，完成 OpenD 的下载、安装和登录。

### [#](./quick_demo.md#4042-2)
 第二步：下载 Java API

我们提供了两种方式下载和升级 Java API，您可以任选其一：

#### [#](./quick_demo.md#5757-2)
 方式一：通过 maven 仓库配置

1.  在 [maven](https://search.maven.org/artifact/com.moomoo.openapi/moomoo-api)
     中选择最新的 moomoo-api 版本，点击进入。
2.  将网页右侧的相关配置复制添加至您的工程设置中。  
    举例：使用 Apache Maven 来管理项目的用户，可以将下图红框中的代码复制至您的工程设置中。 ![maven](https://openapi.futunn.com/futu-api-doc/assets/img/mm-maven.95db9106.png)

#### [#](./quick_demo.md#6826)
 方式二：通过官网下载 Java API

1.  进入 [moomoo 官网](https://www.moomoo.com/download/OpenAPI)
     下载最新版本的 Java API。
2.  解压下载好的 MMAPI 文件，`/MMAPI4J` 是 Java API 的目录，将 [MMAPI4J 目录结构](./quick_demo.md#4144)
     中的 `/lib/moomoo-api-.x.y.z.jar` 添加到您的工程设置中。

#### [#](./quick_demo.md#5224)
 MMAPI4J 目录结构

    +---mmapi4j                      moomoo-api 源码，如果所用 JDK 版本不兼容，您可以用这里的工程重新编译出 moomoo-api.jar
    +---lib                          存放公共库文件
    |    moomoo-api-x.y.z.jar        moomoo API 的 Java 版本
    |    bcprov-jdk15on-1.68.jar     第三方库，用于加解密
    |    bcpkix-jdk15on-1.68.jar     第三方库，用于加解密
    |    protobuf-java-3.5.1.jar     第三方库，用于解析 protobuf 数据
    +---sample                       示例工程
    +---resources                    maven 工程默认生成的目录
    

1  
2  
3  
4  
5  
6  
7  
8  

### [#](./quick_demo.md#5950)
 第三步：创建 moomoo-api 工程

以 IntelliJ IDEA 为例：

1.  新建工程：  
    ![new_java](https://openapi.futunn.com/futu-api-doc/assets/img/new_java.8a9550c5.png)
    
2.  选择 maven 项目：  
    ![maven_proj](https://openapi.futunn.com/futu-api-doc/assets/img/maven_proj.68228f60.png)
    
3.  选择项目路径：  
    ![loc_java](https://openapi.futunn.com/futu-api-doc/assets/img/mm-loc_java.62455662.png)
    
4.  选择 maven 工具，这里采用 IDEA 的自带工具：  
    ![tool_java](https://openapi.futunn.com/futu-api-doc/assets/img/mm-tool_java.625eaea3.png)
    
5.  进入项目，等待项目初始化完成，完成后下方窗口出现 “BUILD SUCCESS” 字样：  
    ![suc_java](https://openapi.futunn.com/futu-api-doc/assets/img/mm-suc_java.f851386d.png)
    
6.  配置 moomoo-api
    

*   若您是通过 maven 仓库进行配置：  
    直接编辑 pom.xml 文件，在 dependencies 中插入moomoo-api 依赖（x.y.z 替换为版本号）：  
    ![maven_moomoo](https://openapi.futunn.com/futu-api-doc/assets/img/mm-maven_futu.f25e47e7.png)
    
*   若您是通过官网下载 Java API：  
    （1）在项目下创建 /lib/ 目录，将 **/MMAPI4J/lib/moomoo-api-x.y.z.jar** 文件复制到 /lib/ 目录下（例如：下图中的 moomoo-api-5.4.1607.jar）。  
    （2）编辑 pom.xml 文件，在 dependencies 中插入 moomoo-api 依赖。  
    ![maven_api](https://openapi.futunn.com/futu-api-doc/assets/img/mm-maven_api.2cffc3d9.png)
    

### [#](./quick_demo.md#6041-2)
 第四步：导入示例项目

示例提供了 maven 编译脚本，可以用支持 maven 的 IDE 导入 `/sample` 目录下的工程。

### [#](./quick_demo.md#9038-2)
 第五步：运行

1.  检查 main.java 文件中跑的示例程序中使用的配置是否跟 OpenD 的配置一致。
    
2.  运行 `/sample` 目录下的 main.java 文件，可以看到运行成功的返回信息如下：
    

    Qot onInitConnect: ret=true desc=Succeed! connID=6750011030360491012
    onReply_GetMarketState: retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      marketInfoList {
        security {
          market: 1
          code: "00700"
        }
        name: "\350\205\276\350\256\257\346\216\247\350\202\241"
        marketState: 6
      }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

提示

*   如果遇到找不到 MMAPIChannel 动态库文件，需要将 `/lib` 文件夹里的对应平台的 MMAPIChannel 文件，拷贝到程序运行的当前目录下。

[#](./quick_demo.md#5895-2)
 C++ 示例
---------------------------------------------------------------------------

### [#](./quick_demo.md#5519-9)
 第一步：下载安装登录 OpenD

请参考 [这里](./quick_opend-base.md)
，完成 OpenD 的下载、安装和登录。

### [#](./quick_demo.md#4792-2)
 第二步：下载 C++ API

通过 [moomoo 官网](https://www.moomoo.com/download/OpenAPI)
 下载最新版本的 C++ API。

解压下载好的 MMAPI 文件，`/MMAPI4CPP` 是 C++ API 的目录，具体目录结构请参见 [MMAPI4CPP 目录结构](./quick_demo.md#227)
。

#### [#](./quick_demo.md#267)
 MMAPI4CPP 目录结构

    +---Bin                  存放各种系统默认编译环境编译出的公共库文件
    +---Include              存放公共头文件，以及 proto 协议生成的 .h/.cc 文件的目录
    +---Sample               示例工程
    +---Src                  源码
        +---MMAPI            MMAPI 源码
        +---protobuf         protobuf 源码
    

1  
2  
3  
4  
5  
6  

### [#](./quick_demo.md#3373-4)
 第三步：导入示例项目

示例提供了 Visual Studio 和 XCode 的工程文件，可以用 Visual Studio 或 XCode 开发环境导入 Sample 项目。

### [#](./quick_demo.md#4390-4)
 第四步：运行

1.  检查 simpleSample.cpp 文件中跑的示例程序中使用的接口配置是否跟 OpenD 的配置一致。
    
2.  运行后，可以看到运行成功的返回信息如下：
    

    Run GetSecuritySnapshotDemo
    InitQot, suc = 1
    GetHKEqtySecList, count = 2698
    GetSecuritySnapshot, i = 0
    GetSecuritySnapshot, i = 1
    GetSecuritySnapshot, i = 2
    GetSecuritySnapshot, i = 3
    GetSecuritySnapshot, i = 4
    GetSecuritySnapshot, i = 5
    GetSecuritySnapshot, i = 6
    GetSecuritySnapshot, i = 7
    GetSecuritySnapshot, i = 8
    GetSecuritySnapshot, i = 9
    GetSecuritySnapshot, i = 10
    GetSecuritySnapshot, i = 11
    GetSecuritySnapshot, i = 12
    GetSecuritySnapshot, i = 13
    ParseHKEqtySecQot, first = (HK.00001, 56.65)
    GetSecuritySnapshotDemo End
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  

您可以通过 [moomoo 官网](https://www.moomoo.com/download/OpenAPI)
 下载最新版本的 Protobuf API。

[#](./quick_demo.md#6969-2)
 JavaScript 示例
----------------------------------------------------------------------------------

### [#](./quick_demo.md#5519-10)
 第一步：下载安装登录 OpenD

请参考 [这里](./quick_opend-base.md)
，完成 OpenD 的下载、安装和登录。

### [#](./quick_demo.md#7122-2)
 第二步：下载 JavaScript API

*   方式一：在 cmd 中直接使用 npm 安装或升级（npm 仅支持 JavaScript API 源码的下载，如需获取更多的示例代码，请参考方式二）。
    
    *   初次安装： `$ npm install --save moomoo-api`。
    *   二次升级：`$ npm update moomoo-api`。
*   方式二：通过 [moomoo 官网](https://www.moomoo.com/download/OpenAPI)
     下载最新版本的 JavaScript API。
    

解压下载好的 MMAPI 文件，`/MMAPI4JS`是 JavaScript API 的目录，具体目录结构请参见 [MMAPI4JS 目录结构](./quick_demo.md#147)
。

#### [#](./quick_demo.md#1291)
 MMAPI4JS 目录结构

    +---Sample               示例工程    
    +---src                  源码
    

1  
2  

### [#](./quick_demo.md#1856-2)
 第三步：运行示例

1.  使用 Visual Studio Code 打开示例代码目录 `MMAPI4JS/sample`。
    
2.  找到每个 Demo 文件中启动 WebSocket 相关代码，如下图，修改成 OpenD 对应的配置（注意：可视化 OpenD 默认会启动 WebSocket，命令行 OpenD 则需要 [配置参数](./opend_opend-cmd.md#8799)
     自行启动 WebSocket）。 ![websocket-demo-config](https://openapi.futunn.com/futu-api-doc/assets/img/mm-websocket-demo-config.e4b9ca5c.png)
    
3.  在 Visual Studio Code 控制台中输入 `npm install` 安装项目依赖。 ![websocket-demo-depend](https://openapi.futunn.com/futu-api-doc/assets/img/mm-websocket-demo-depend.d1365ef4.png)
    
4.  在 Visual Studio Code 控制台中输 `npm run serve` 运行项目。 ![websocket-demo-run](https://openapi.futunn.com/futu-api-doc/assets/img/mm-websocket-demo-run.677381c2.png)
    
5.  项目运行成功后，打开对应的服务地址。 ![websocket-demo-suc](https://openapi.futunn.com/futu-api-doc/assets/img/mm-websocket-demo-suc.b677bded.png)
    
6.  在页面上体验相关功能。  
    ![websocket-demo-page](https://openapi.futunn.com/futu-api-doc/assets/img/mm-websocket-demo-page.8967fce9.png)
    

← [编程环境搭建](./quick_env.md) [交易策略搭建示例](./quick_strategy-sample.md)
 →

[简易程序运行](./quick_demo.md)