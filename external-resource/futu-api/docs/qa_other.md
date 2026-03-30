 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/qa/other.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/qa/other.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/qa/other.html)
    

下载

*   [PDF](https://openapi.futunn.com/pdfs/Futu-API-Doc-zh-Python.pdf)
    
*   [Markdown](https://openapi.futunn.com/mds/Futu-API-Doc-zh-Python.md)
    
*   [Skills](https://openapi.futunn.com/skills/opend-skills.zip)
    

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/qa/other.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/qa/other.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/qa/other.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
*   交易接口
    
*   基础接口
    
*   Q&A
    
    *   [OpenD 相关](https://openapi.futunn.com/futu-api-doc/qa/opend.html)
        
    *   [行情相关](https://openapi.futunn.com/futu-api-doc/qa/quote.html)
        
    *   [交易相关](https://openapi.futunn.com/futu-api-doc/qa/trade.html)
        
    *   [其他](https://openapi.futunn.com/futu-api-doc/qa/other.html)
        
    

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#8691)
 其他
===================================================================

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#4792)
 Q1：如何编译C++ API？
--------------------------------------------------------------------------------

A: futu api c++ SDK支持Windows/MacOS/Linux，每个系统提供了以下编译环境生成的库文件：

| 操作系统 | 编译工具 |
| --- | --- |
| Windows | Visual Studio 2013 |
| Centos 7 | g++ 4.8.5 |
| Ubuntu 16.04 | g++ 5.4.0 |
| MacOS | XCode 11 |

如果编译器版本不同，或依赖的protobuf版本不同，则可能需要自己使用源码重新编译FTAPI和protobuf，源码位置见下图目录：

    FTAPI目录结构：
    +---Bin                               存放各个系统默认编译环境编译出的依赖库
    +---Include                           存放公共头文件，以及proto协议生成的.h/.cc文件
    +---Sample                            示例工程
    \---Src
        +---FTAPI                         FTAPI源码
        +---protobuf-all-3.5.1.tar.gz     protobuf源码
    

1  
2  
3  
4  
5  
6  
7  

#### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#9437)
 编译步骤：

1.  重新编译protobuf：生成libprotobuf静态库
2.  从协议proto文件中生成C++文件
3.  重新编译FTAPI: 源码在Src/FTAPI，生成libFTAPI静态库

#### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#6632)
 步骤1： 重新编译protobuf：

*   Windows：
    *   安装CMake
    *   打开VS命令行工具，cd到protobuf/cmake目录
    *   执行：cmake -G "Visual Studio 12 2019" -DCMAKE\_INSTALL\_PREFIX=install -Dprotobuf\_BUILD\_TESTS=OFF  这样会生成Visual Studio 2019的项目文件，其它版本Visual Studio请修改-G参数
    *   打开生成的Visual Studio项目文件，平台工具集设置为v120\_xp，编译即可
*   Linux（参考protobuf/src/README）
    *   执行 ./autogen.sh
    *   执行 CXXFLAGS="-std=gnu++11" ./configure --disable-shared
    *   执行 make
    *   将生成的libprotobuf.a放入Bin/Linux目录
*   MacOS（参考protobuf/src/README）
    *   使用brew安装这些依赖库：autoconf automake libtool
    *   执行./configure CC=clang CXX="clang++ -std=gnu++11 -stdlib=libc++" --disable-shared

#### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#2227)
 步骤2: 重新生成proto代码

*   上面编译Protobuf后会同时生成可执行文件protoc。用protoc将Include/Proto下面的.proto文件生成对应的.h和.cc文件。例如命令以下命令会从Common.proto生成对应的Common.pb.h和Common.pb.cc
    *   protoc -I="FTAPI路径/Include/Proto" --cpp\_out="." FTAPI路径/Include/Proto/Common.proto
*   将生成的.h和.cc文件放到Include/Proto下面

#### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#4984)
 步骤3: 重新编译FTAPI

*   Windows：新建Visual Studio C++静态库工程，将Src/FTAPI和Include下的源码加入工程中，平台工具集设置为v120\_xp，然后编译
*   Mac：新建XCode C++静态库工程，将Src/FTAPI和Include下的源码加入工程中，然后编译
*   Linux：使用CMake编译FTAPI静态库，在FTAPI路径/Src目录下执行：
    *   cmake -DTARGET\_OS=Linux

A: moomoo api c++ SDK支持Windows/MacOS/Linux，每个系统提供了以下编译环境生成的库文件：

| 操作系统 | 编译工具 |
| --- | --- |
| Windows | Visual Studio 2013 |
| Centos 7 | g++ 4.8.5 |
| Ubuntu 16.04 | g++ 5.4.0 |
| MacOS | XCode 11 |

如果编译器版本不同，或依赖的protobuf版本不同，则可能需要自己使用源码重新编译MMAPI和protobuf，源码位置见下图目录：

    MMAPI目录结构：
    +---Bin                               存放各个系统默认编译环境编译出的依赖库
    +---Include                           存放公共头文件，以及proto协议生成的.h/.cc文件
    +---Sample                            示例工程
    \---Src
        +---MMAPI                         MMAPI源码
        +---protobuf-all-3.5.1.tar.gz     protobuf源码
    

1  
2  
3  
4  
5  
6  
7  

#### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#9437-2)
 编译步骤：

1.  重新编译protobuf：生成libprotobuf静态库
2.  从协议proto文件中生成C++文件
3.  重新编译MMAPI: 源码在Src/MMAPI，生成libMMAPI静态库

#### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#6632-2)
 步骤1： 重新编译protobuf：

*   Windows：
    *   安装CMake
    *   打开VS命令行工具，cd到protobuf/cmake目录
    *   执行：cmake -G "Visual Studio 12 2019" -DCMAKE\_INSTALL\_PREFIX=install -Dprotobuf\_BUILD\_TESTS=OFF  这样会生成Visual Studio 2019的项目文件，其它版本Visual Studio请修改-G参数
    *   打开生成的Visual Studio项目文件，平台工具集设置为v120\_xp，编译即可
*   Linux（参考protobuf/src/README）
    *   执行 ./autogen.sh
    *   执行 CXXFLAGS="-std=gnu++11" ./configure --disable-shared
    *   执行 make
    *   将生成的libprotobuf.a放入Bin/Linux目录
*   MacOS（参考protobuf/src/README）
    *   使用brew安装这些依赖库：autoconf automake libtool
    *   执行./configure CC=clang CXX="clang++ -std=gnu++11 -stdlib=libc++" --disable-shared

#### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#2227-2)
 步骤2: 重新生成proto代码

*   上面编译Protobuf后会同时生成可执行文件protoc。用protoc将Include/Proto下面的.proto文件生成对应的.h和.cc文件。例如命令以下命令会从Common.proto生成对应的Common.pb.h和Common.pb.cc
    *   protoc -I="MMAPI路径/Include/Proto" --cpp\_out="." MMAPI路径/Include/Proto/Common.proto
*   将生成的.h和.cc文件放到Include/Proto下面

#### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#1970)
 步骤3: 重新编译MMAPI

*   Windows：新建Visual Studio C++静态库工程，将Src/MMAPI和Include下的源码加入工程中，平台工具集设置为v120\_xp，然后编译
*   Mac：新建XCode C++静态库工程，将Src/MMAPI和Include下的源码加入工程中，然后编译
*   Linux：使用CMake编译MMAPI静态库，在MMAPI路径/Src目录下执行：
    *   cmake -DTARGET\_OS=Linux

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#1578)
 Q2：有没有更完整的策略样例可以参考？
------------------------------------------------------------------------------------

A:

*   Python 策略样例在 /futu/examples/ 文件夹下。您可以通过执行如下命令，找到 Python API 的安装路径：
    
        import futu
        print(futu.__file__)
        
    
    1  
    2  
    
*   C# 策略样例在 /FTAPI4NET/Sample/ 文件夹下
*   Java 策略样例在 /FTAPI4J/sample/ 文件夹下
*   C++ 策略样例在 /FTAPI4CPP/Sample/ 文件夹下
*   JavaScript 策略样例在 /FTAPI4JS/sample/ 文件夹下

A:

*   Python 策略样例在 /moomoo/examples/ 文件夹下。您可以通过执行如下命令，找到 Python API 的安装路径：
    
        import moomoo
        print(moomoo.__file__)
        
    
    1  
    2  
    
*   C# 策略样例在 /MMAPI4NET/Sample/ 文件夹下
*   Java 策略样例在 /MMAPI4J/sample/ 文件夹下
*   C++ 策略样例在 /MMAPI4CPP/Sample/ 文件夹下
*   JavaScript 策略样例在 /MMAPI4JS/sample/ 文件夹下

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#4857)
 Q3：使用 python API 导入异常
--------------------------------------------------------------------------------------

A：

**场景一**：已经在 Python 环境中安装了 futu 模块，仍然提示 No module named 'futu'？  
很可能是因为当前 IDE 所使用的 interpreter 并不是你装过 futu 模块的 interpreter。也就是说，您的电脑可能装了两个以上的 Python 环境。 您可以操作如下两步：

1.  在 Python 中运行如下代码，得到当前 interpreter 的路径：

    import sys
    print(sys.executable)
    

1  
2  

示例图：  
![No module named 'futu'](<Base64-Image-Removed>)

2.  在命令行中，执行 `$ D:\software\anaconda3\python.exe -m pip install futu-api`（其中前半部分的文件路径来自第 1 步打印的路径）。 这样就可以在当前的 interpreter 中也安装一份 futu 模块。

**场景一**：已经在 Python 环境中安装了 moomoo 模块，仍然提示 No module named 'moomoo'？  
很可能是因为当前 IDE 所使用的 interpreter 并不是你装过 moomoo 模块的 interpreter。也就是说，您的电脑可能装了两个以上的 Python 环境。 您可以操作如下两步：

1.  在 Python 中运行如下代码，得到当前 interpreter 的路径：

    import sys
    print(sys.executable)
    

1  
2  

示例图：  
![No module named 'moomoo'](<Base64-Image-Removed>)

2.  在命令行中，执行 `$ D:\software\anaconda3\python.exe -m pip install moomoo-api`（其中前半部分的文件路径来自第 1 步打印的路径）。 这样就可以在当前的 interpreter 中也安装一份 moomoo 模块。

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#100)
 Q4： import 成功了，仍然调用不了相关接口？
------------------------------------------------------------------------------------------

A：通常遇到这种情况，需要确认一下：成功导入的 futu，是不是真正的 Futu API 模块。以下几种场景也可能 import 成功。

**场景一**：存在与“futu”重名的文件

1.  当前文件名是 futu.py
2.  当前文件所在目录下存在另一个名为 futu.py 的文件
3.  当前文件所在目录下存在名为 `/futu` 的文件夹

因此，我们强烈建议您，在给文件 / 文件夹 / 工程起名的时候，不要起名叫“futu”。重名一时爽，查 bug 两行泪。

**场景二**：误装了一个名为“futu”的第三方库

Futu API 的正确名称为`futu-api`，而非“futu”。

如果您安装过名为“futu”的第三方库，请将其卸载，并 [下载 futu-api](https://openapi.futunn.com/futu-api-doc/quick/demo.html#4688)
。

以 PyCharm 为例：查看第三方库的安装情况。

![settings](https://openapi.futunn.com/futu-api-doc/assets/img/settings.a4197355.png)  
![futuku](https://openapi.futunn.com/futu-api-doc/assets/img/futuku.5d8124c1.png)

A：通常遇到这种情况，需要确认一下：成功导入的 moomoo，是不是真正的 moomoo API 模块。以下几种场景也可能 import 成功。

**场景一**：存在与“moomoo”重名的文件

1.  当前文件名是 moomoo.py
2.  当前文件所在目录下存在另一个名为 moomoo.py 的文件
3.  当前文件所在目录下存在名为 `/moomoo` 的文件夹

因此，我们强烈建议您，在给文件 / 文件夹 / 工程起名的时候，不要起名叫“moomoo”。重名一时爽，查 bug 两行泪。

**场景二**：误装了一个名为“moomoo”的第三方库

moomoo API 的正确名称为`moomoo-api`，而非“moomoo”。

如果您安装过名为“moomoo”的第三方库，请将其卸载，并 [下载 moomoo-api](https://openapi.futunn.com/futu-api-doc/quick/demo.html#4688)
。

以 PyCharm 为例：查看第三方库的安装情况。

![settings](https://openapi.futunn.com/futu-api-doc/assets/img/settings.a4197355.png)  
![moomooku](https://openapi.futunn.com/futu-api-doc/assets/img/mmku.b7d92af2.png)

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#4601)
 Q5：协议加密相关
--------------------------------------------------------------------------

A：

### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#8831)
 概述

您可以使用非对称加密算法 RSA，对策略程序（Futu API）与 OpenD 之间的请求和返回内容进行加密，以保证通信安全。  
如果您的策略程序（Futu API）与 FutuOpenD 在同一台电脑上，则通常无需加密。

### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#7493)
 协议加密流程

您可以尝试通过以下步骤解决此问题：

1.  通过第三方 web 平台自动生成密钥文件。
    
    *   具体方法：在 baidu 或 google 上搜索“RSA 在线生成”，**密钥格式**设置为 PKCS#1，**密钥长度**设置为 1024 bit，不需要设置私钥密码，点击**生成密钥对**。  
        ![ui-config](https://openapi.futunn.com/futu-api-doc/assets/img/create_rsa.abc82b84.png)
2.  将生成的 **RSA 加密私钥** 复制粘贴至 txt 记事本，并保存至 OpenD 所在电脑的指定路径。
    
3.  在 OpenD 所在的电脑中，指定 **RSA 加密私钥** 的路径。
    
    *   方式一：在 [可视化 OpenD](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#4147)
         启动界面右侧的“加密私钥”一栏，指定上一步骤中放置 **RSA 加密私钥** 的路径。如下图所示：  
        ![ui-config](https://openapi.futunn.com/futu-api-doc/assets/img/nnrsa_ui-config.fbe741a1.png)
    *   方式二：在 [命令行 OpenD](https://openapi.futunn.com/futu-api-doc/opend/opend-cmd.html#8799)
         启动文件 OpenD.xml 中，找到参数`rsa_private_key`，将其配置为第 2 步中 **RSA 加密私钥** 的路径。如下图所示：  
        ![ui-config](https://openapi.futunn.com/futu-api-doc/assets/img/nnrsa_xml.6ed36914.png)
4.  将第 2 步中 txt 文件另存至策略程序（Futu API）所在电脑的指定路径， 并在策略程序中将此路径 [设置为私钥路径](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#5641)
    。
    
5.  在策略程序（Futu API）中启用协议加密。 启用协议加密的方式有两种，其中方式二的优先级更高。
    
    *   方式一：对单条的连接加密（通用）。在对 [行情对象](https://openapi.futunn.com/futu-api-doc/quote/base.html#7902)
         或 [交易对象](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902)
         创建连接时，通过 **是否启用加密** 参数设置加密。
    *   方式二：对所有的连接加密（仅 Python）。通过`enable_proto_encrypt`接口设置加密，详见 [这里](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#319)
        。

提示

*   在 OpenD 或策略程序（Futu API）中指定 **RSA 加密私钥** 路径时，需指定至 txt 文件本身。
*   RSA 加密公钥无需保存，可通过私钥计算得到。

A:

### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#8831-2)
 概述

您可以使用非对称加密算法 RSA，对策略程序（moomoo API）与 OpenD 之间的请求和返回内容进行加密，以保证通信安全。  
如果您的策略程序（moomoo API）与 OpenD 在同一台电脑上，则通常无需加密。

### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#7493-2)
 协议加密流程

您可以尝试通过以下步骤解决此问题：

1.  通过第三方 web 平台自动生成密钥文件。
    
    *   具体方法：在 baidu 或 google 上搜索“RSA 在线生成”，**密钥格式**设置为 PKCS#1，**密钥长度**设置为 1024 bit，不需要设置私钥密码，点击**生成密钥对**。  
        ![ui-config](https://openapi.futunn.com/futu-api-doc/assets/img/create_rsa.abc82b84.png)
2.  将生成的 **RSA 加密私钥** 复制粘贴至 txt 记事本，并保存至 OpenD 所在电脑的指定路径。
    
3.  在 OpenD 所在的电脑中，指定 **RSA 加密私钥** 的路径。
    
    *   方式一：在 [可视化 OpenD](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#4147)
         启动界面右侧的“加密私钥”一栏，指定上一步骤中放置 **RSA 加密私钥** 的路径。如下图所示：  
        ![ui-config](https://openapi.futunn.com/futu-api-doc/assets/img/mmrsa_ui-config.4cb56b13.png)
    *   方式二：在 [命令行 OpenD](https://openapi.futunn.com/futu-api-doc/opend/opend-cmd.html#8799)
         启动文件 OpenD.xml 中，找到参数`rsa_private_key`，将其配置为第 2 步中 **RSA 加密私钥** 的路径。如下图所示：  
        ![ui-config](https://openapi.futunn.com/futu-api-doc/assets/img/mmrsa_xml.5faae8a3.png)
4.  将第 2 步中 txt 文件另存至策略程序（moomoo API）所在电脑的指定路径， 并在策略程序中将此路径 [设置为私钥路径](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#5641)
    。
    
5.  在策略程序（moomoo API）中启用协议加密。 启用协议加密的方式有两种，其中方式二的优先级更高。
    
    *   方式一：对单条的连接加密（通用）。在对 [行情对象](https://openapi.futunn.com/futu-api-doc/quote/base.html#7902)
         或 [交易对象](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902)
         创建连接时，通过 **是否启用加密** 参数设置加密。
    *   方式二：对所有的连接加密（仅 Python）。通过`enable_proto_encrypt`接口设置加密，详见 [这里](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#319)
        。

提示

*   在 OpenD 或策略程序（moomoo API）中指定 **RSA 加密私钥** 路径时，需指定至 txt 文件本身。
*   RSA 加密公钥无需保存，可通过私钥计算得到。

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#6418)
 Q6：为什么我获取的 DataFrame 数据，只能展示一部分 ？
--------------------------------------------------------------------------------------------------

A：打印 pandas.DataFrame 数据的时候，如果行列数过多，pandas 默认会将数据折叠，导致看起来显示不全。  
因此，并不是接口返回数据真的不全。您只需要在 Python 脚本前面加上如下代码即可解决。

    import pandas as pd
    pd.options.display.max_rows=5000
    pd.options.display.max_columns=5000
    pd.options.display.width=1000
    

1  
2  
3  
4  

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#3535)
 Q7：Mac 机器使用 C++ 语言的 API，遇到 “无法打开 libFTAPIChannel.dylib” 的问题
----------------------------------------------------------------------------------------------------------------------------

A：在对应库目录中执行以下命令即可解决:`$ xattr -r -d com.apple.quarantine libFTAPIChannel.dylib`。

A：在对应库目录中执行以下命令即可解决:`$ xattr -r -d com.apple.quarantine libAPIChannel.dylib`。

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#4750)
 Q8：Python 用户，为什么在 OpenD 配置文件中设置了日志级别为 no 后，log 文件夹下仍然持续产生超大容量的日志文件？
------------------------------------------------------------------------------------------------------------------------------------

A：OpenD 配置文件中的日志级别参数，只用来控制 OpenD 产生的日志。而 Python API 默认也会产生日志，如果您不希望希望 Python API 产生日志，可以在 Python 脚本加上如下语句：

    logger.file_level = logging.FATAL  # 用于关闭 Python API 日志
    logger.console_level = logging.FATAL  # 用于关闭 Python 运行时的控制台日志
    

1  
2  

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#5759)
 Q9：对于 5.4 及以上的版本，Java API 的库名和配置方式的变更
------------------------------------------------------------------------------------------------------

A: \* 如果您是 Java API 5.3 及以下版本的用户，在更新版本时，请注意以下变更：

**配置流程的变更**：

1.  通过 [富途牛牛官网](https://www.futunn.com/download/OpenAPI)
     下载 Futu API。
2.  解压下载好的 FTAPI 文件，`/FTAPI4J` 是 Java API 的目录，将目录结构中的 `/lib/futu-api-.x.y.z.jar` 添加到您的工程设置中。创建 futu-api 工程请参考 [这里](https://openapi.futunn.com/futu-api-doc/quick/demo.html#2927)
    。

**目录结构的变更**：

1.  Futu API 的 Java 版本，库名由之前的 ftapi4j.jar 变更为 `futu-api-x.y.z.jar`，其中 “x.y.z” 表示版本号。
2.  第三方库的引用中，去掉了 /lib/jna.jar 和 /lib/jna-platform.jar 依赖，增加了 `/lib/bcprov-jdk15on-1.68.jar` 和 `/lib/bcpkix-jdk15on-1.68.jar` 依赖。

    ```
    +---ftapi4j                      futu-api 源码，如果所用 JDK 版本不兼容可以用这里的工程重新编译出 futu-api.jar
    +---lib                          存放公共库文件
    |    futu-api-x.y.z.jar          Futu API 的 Java 版本
    |    bcprov-jdk15on-1.68.jar     第三方库，用于加解密
    |    bcpkix-jdk15on-1.68.jar     第三方库，用于加解密
    |    protobuf-java-3.5.1.jar     第三方库，用于解析 protobuf 数据
    +---sample                       示例工程
    +---resources                    maven 工程默认生成的目录
    ```
    

*   如果您第一次接触 Futu API，我们提供了更便捷的通过 maven 仓库配置 Java API 的方式。配置流程请参考 [这里](https://openapi.futunn.com/futu-api-doc/quick/demo.html#5757)
    。

A: \* 如果您是 Java API 5.3 及以下版本的用户，在更新版本时，请注意以下变更：

**配置流程的变更**：

1.  通过 [moomoo 官网](https://www.moomoo.com/download/)
     下载 moomoo API。
2.  解压下载好的 mmAPI 文件，`/MMAPI4J` 是 Java API 的目录，将目录结构中的 `/lib/moomoo-api-.x.y.z.jar` 添加到您的工程设置中。创建 moomoo-api 工程请参考 [这里](https://openapi.futunn.com/futu-api-doc/quick/demo.html#2927)
    。

**目录结构的变更**：

1.  moomoo API 的 Java 版本，库名由之前的 mmapi4j.jar 变更为 `moomoo-api-x.y.z.jar`，其中 “x.y.z” 表示版本号。
2.  第三方库的引用中，去掉了 /lib/jna.jar 和 /lib/jna-platform.jar 依赖，增加了 `/lib/bcprov-jdk15on-1.68.jar` 和 `/lib/bcpkix-jdk15on-1.68.jar` 依赖。

    ```
    +---mmapi4j                      moomoo-api 源码，如果所用 JDK 版本不兼容可以用这里的工程重新编译出 moomoo-api.jar
    +---lib                          存放公共库文件
    |    moomoo-api-x.y.z.jar        moomoo API 的 Java 版本
    |    bcprov-jdk15on-1.68.jar     第三方库，用于加解密
    |    bcpkix-jdk15on-1.68.jar     第三方库，用于加解密
    |    protobuf-java-3.5.1.jar     第三方库，用于解析 protobuf 数据
    +---sample                       示例工程
    +---resources                    maven 工程默认生成的目录
    ```
    

*   如果您第一次接触 moomoo API，我们提供了更便捷的通过 maven 仓库配置 Java API 的方式。配置流程请参考 [这里](https://openapi.futunn.com/futu-api-doc/quick/demo.html#5757)
    。

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#5449)
 Q10：Python 用户，使用 pyinstaller 打包脚本时报错：找不到 Common\_pb2 模块
------------------------------------------------------------------------------------------------------------------------

A：你可以尝试通过以下步骤解决此问题：

1.  假设你需要对 main.py 进行打包。使用命令行语句，运行代码：pyinstaller main.py，不要加参数 “- F”（path 为 main.py 的所在路径）

    pyinstaller path\main.py
    

1  

打包成功后，main.py 所在目录下的 /dist 中，会生成 /main 文件夹，main.exe 就在这个文件夹中。  
![dist](<Base64-Image-Removed>)  
2\. 运行以下代码，找到 futu-api 的安装目录。

    import futu
    print(futu.__file__)
    

1  
2  

运行结果:

    C:\Users\ceciliali\Anaconda3\lib\site-packages\futu\__init__.py
    

1  

![path_futu](https://openapi.futunn.com/futu-api-doc/assets/img/path_futu.bbe49791.png)

3.  打开上图文件夹中的 /common/pb，将所有文件全部复制到 /main 中。
    
4.  在 /main 中创建文件夹，命名为 futu，将上图文件夹中的 `VERSION.txt` 文件复制到 /main/futu 中。  
    ![main_futu](https://openapi.futunn.com/futu-api-doc/assets/img/main_futu.ef121317.png)
    
5.  再次尝试运行 main.exe
    

A：你可以尝试通过以下步骤解决此问题：

1.  假设你需要对 main.py 进行打包。使用命令行语句，运行代码：pyinstaller main.py，不要加参数 “- F”（path 为 main.py 的所在路径）

    pyinstaller path\main.py
    

1  

打包成功后，main.py 所在目录下的 /dist 中，会生成 /main 文件夹，main.exe 就在这个文件夹中。  
![dist](https://openapi.futunn.com/futu-api-doc/assets/img/mmdist.fde7a91b.png)  
2\. 运行以下代码，找到 moomoo-api 的安装目录。

    import moomoo
    print(moomoo.__file__)
    

1  
2  

运行结果:

    C:\Users\ceciliali\Anaconda3\lib\site-packages\moomoo\__init__.py
    

1  

![path_futu](https://openapi.futunn.com/futu-api-doc/assets/img/pathmoomoo.30151621.png)

3.  打开上图文件夹中的 /common/pb，将所有文件全部复制到 /main 中。
    
4.  在 /main 中创建文件夹，命名为 moomoo，将上图文件夹中的 `VERSION.txt` 文件复制到 /main/moomoo 中。  
    ![main_futu](https://openapi.futunn.com/futu-api-doc/assets/img/main_moomoo.45ec72a8.png)
    
5.  再次尝试运行 main.exe
    

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#7695)
 Q11：接口调用结果正常，但其返回表现不符合预期？
------------------------------------------------------------------------------------------

A:

*   接口调用结果正常，表示富途已经成功收到并响应了您的请求，但接口返回表现可能与您的预期不符。
    
    例如：若您在非交易时段调用 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
     接口，虽然您的请求可以被成功响应，并且接口调用结果正常，但在非交易时段下，交易所无行情数据变动，所以您将暂时无法收到行情数据推送，直至市场重新回到交易时段。
    
*   接口调用结果可以通过返回字段（定义参见：[接口调用结果](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
    ）查看，返回字段为 0 代表接口调用正常，非 0 代表接口调用失败。
    
    对于 Python 用户，下面两种写法等价：
    
        if ret_code == RET_OK:
        
    
    1  
    
        if ret_code == 0:
        
    
    1  
    

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#6319)
 Q12：WebSocket相关
--------------------------------------------------------------------------------

A：

### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#8831-3)
 概述

Futu API 中，WebSocket 主要用于以下两方面：

*   可视化 OpenD 中，UI 界面跟底层的命令行 OpenD 的通信使用 WebSocket 方式。
*   JavaScript API 跟 OpenD 之间的通信使用 WebSocket 方式。

![WebSocket-struct](https://openapi.futunn.com/futu-api-doc/assets/img/WebSocket-struct.0c95e270.png)

*   当 WebSocket 启动时，命令行 OpenD 会与 **FTWebSocket 中转服务** 建立 Socket 连接（TCP），这一连接会用到默认的 **监听地址** 和 **API 协议监听端口**。
*   同时，JavaScript API 会与 **FTWebSocket 中转服务** 建立 WebSocket 连接（HTTP），这一连接会用到 **WebSocket 监听地址** 和 **WebSocket 端口**。

### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#2830)
 使用

为保证账户安全，当 WebSocket 监听来自非本地请求时，我们强烈建议您启用 SSL 并配置 **WebSocket 鉴权密钥**。

SSL 通过在配置 **WebSocket 证书** 以及 **WebSocket 私钥** 来启用。  
命令行 OpenD 可通过配置 OpenD.xml 或配置命令行参数来设置文件路径。可视化 OpenD 点击【更多选项】下拉菜单，可以看到设置项。

![ui-more-config](https://openapi.futunn.com/futu-api-doc/assets/img/ui-more-config.c768dd92.png)

提示

如果证书是自签的，则需要在调用 JavaScript 接口所在机器上安装该证书，或者设置不验证证书。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#2347)
 生成自签证书

自签证书生成详细资料不便在此文档展开，请自行查阅。  
在此提供较简单可用的生成步骤：

1.  安装 openssl。
2.  修改 openssl.cnf，在 alt\_names 节点下加上 OpenD 所在机器 IP 地址或域名。  
    例如：IP.2 = xxx.xxx.xxx.xxx, DNS.2 = www.xxx.com
3.  生成私钥以及证书（PEM）。

**证书生成参数参考如下**：  
`openssl req -x509 -newkey rsa:2048 -out futu.cer -outform PEM -keyout futu.key -days 10000 -verbose -config openssl.cnf -nodes -sha256 -subj "/CN=Futu CA" -reqexts v3_req -extensions v3_req`

提示

*   openssl.cnf 需要放到系统路径下，或在生成参数中指定绝对路径。
*   注意生成私钥需要指定不设置密码（-nodes）。

附上本地自签证书以及生成证书的配置文件供测试：

*   [openssl.cnf](https://openapi.futunn.com/futu-api-doc/file/openssl.cnf)
    
*   [futu.cer](https://openapi.futunn.com/futu-api-doc/file/cer)
    
*   [futu.key](https://openapi.futunn.com/futu-api-doc/file/key)
    

Moomoo API 中，WebSocket 主要用于以下两方面：

*   可视化 OpenD 中，UI 界面跟底层的命令行 OpenD 的通信使用 WebSocket 方式。
*   JavaScript API 跟 OpenD 之间的通信使用 WebSocket 方式。

![WebSocket-struct](https://openapi.futunn.com/futu-api-doc/assets/img/WebSocket-struct.0c95e270.png)

*   当 WebSocket 启动时，命令行 OpenD 会与 **MMWebSocket 中转服务** 建立 Socket 连接（TCP），这一连接会用到默认的 **监听地址** 和 **API 协议监听端口**。
*   同时，JavaScript API 会与 **MMWebSocket 中转服务** 建立 WebSocket 连接（HTTP），这一连接会用到 **WebSocket 监听地址** 和 **WebSocket 端口**。

### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#2830-2)
 使用

为保证账户安全，当 WebSocket 监听来自非本地请求时，我们强烈建议您启用 SSL 并配置 **WebSocket 鉴权密钥**。

SSL 通过在配置 **WebSocket 证书** 以及 **WebSocket 私钥** 来启用。  
命令行 OpenD 可通过配置 OpenD.xml 或配置命令行参数来设置文件路径。可视化 OpenD 点击【更多选项】下拉菜单，可以看到设置项。

![ui-more-config](https://openapi.futunn.com/futu-api-doc/assets/img/mmui-more-config.d056d842.png)

提示

如果证书是自签的，则需要在调用 JavaScript 接口所在机器上安装该证书，或者设置不验证证书。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#2347-2)
 生成自签证书

自签证书生成详细资料不便在此文档展开，请自行查阅。  
在此提供较简单可用的生成步骤：

1.  安装 openssl。
2.  修改 openssl.cnf，在 alt\_names 节点下加上 OpenD 所在机器 IP 地址或域名。  
    例如：IP.2 = xxx.xxx.xxx.xxx, DNS.2 = www.xxx.com
3.  生成私钥以及证书（PEM）。

**证书生成参数参考如下**：  
`openssl req -x509 -newkey rsa:2048 -out moomoo.cer -outform PEM -keyout moomoo.key -days 10000 -verbose -config openssl.cnf -nodes -sha256 -subj "/CN=moomoo CA" -reqexts v3_req -extensions v3_req`

提示

*   openssl.cnf 需要放到系统路径下，或在生成参数中指定绝对路径。
*   注意生成私钥需要指定不设置密码（-nodes）。

附上本地自签证书以及生成证书的配置文件供测试：

*   [openssl.cnf](https://openapi.futunn.com/futu-api-doc/file/openssl.cnf)
    
*   [moomoo.cer](https://openapi.futunn.com/futu-api-doc/file/cer)
    
*   [moomoo.key](https://openapi.futunn.com/futu-api-doc/file/key)
    

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#9759)
 Q13：API 的行情和交易服务分别部署在哪里？
-----------------------------------------------------------------------------------------

A：

*   行情：

| 平台账号 | 行情服务器所在地 |
| --- | --- |
| 牛牛号 | 腾讯云广州和香港 |
| moomoo 号 | 腾讯云美国弗吉尼亚和新加坡 |

*   交易：

| 所属券商 | 交易服务器所在地 |
| --- | --- |
| 富途证券(香港) | 香港  |
| moomoo证券(美国) | 腾讯云美国弗吉尼亚 |
| moomoo证券(新加坡) | 腾讯云新加坡 |
| moomoo证券(澳大利亚) | 腾讯云新加坡 |
| moomoo证券(马来西亚) | 阿里云马来西亚 |
| moomoo证券(加拿大) | AWS加拿大 |
| moomoo证券(日本) | 腾讯云日本 |

[#](https://openapi.futunn.com/futu-api-doc/qa/other.html#7297)
 Q14：关于综合账户升级的过渡指引
----------------------------------------------------------------------------------

### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#6379)
 1. [**综合账户升级**](https://www.futuhk.com/hans/support/topic2_1734)

综合账户支持以多种货币在同一个账户内交易不同市场品类。从单币种账户升级到综合账户，是在您原来的牛牛号下，进行账户迁移。主要包括：

*   创建新的综合账户
*   将您原来单币种业务账户里的资产，转移到综合账户里
*   关闭原来的单币种账户

### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#2164)
 2. **OpenD版本升级**

我们会在 2024年9月14日、15日 集中为 Futu API 客户的账户做升级，请提前检查 OpenD 和 API 版本号：

*   **7.01 及以下版本**  
    OpenD 因版本过旧，将于 2024/09/14 停止服务。届时，已登录的账户会被强制退出登录。我们建议您在 9/14 之前升级 [OpenD](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#4147)
     和 [API](https://openapi.futunn.com/futu-api-doc/quick/demo.html#4688)
     至最新版本，且不要在 9/14~9/15 期间跨周末运行策略。
*   **7.02 ~ 8.2 版本**  
    OpenD 版本较旧，无法获取综合账户。我们建议您在 9/14 之前升级 [OpenD](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#4147)
     和 [API](https://openapi.futunn.com/futu-api-doc/quick/demo.html#4688)
     至最新版本，且不要在 9/14~9/15 期间跨周末运行策略。
*   **8.3 及以上版本**  
    可以正常使用，我们建议您不要在 9/14~9/15 期间跨周末运行策略。

综合账户升级时，您的资产会转移到新的综合账户，如果策略指定旧的账户，可能会运行异常。同时，在实盘交易之前，建议您进行必要的检查与测试，确保一切设置正常。

### [#](https://openapi.futunn.com/futu-api-doc/qa/other.html#7686)
 3. **账户升级后，Futu API有哪些表现？**

*   Python API 将不再支持使用 OpenHKTradeContext, OpenUSTradeContext, OpenHKCCTradeContext, OpenCNTradeContext 创建交易对象，请参考 [创建交易对象连接](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902)
     改用 OpenSecTradeContext。
*   非Python API用户，在使用 Trd\_GetAccList 接口时，需要将 needGeneralSecAccount 参数设为 true，才能获取到综合账户的相关信息。
*   账户新增 [账户状态](https://openapi.futunn.com/futu-api-doc/trade/trade.html#121)
    : 在使用 [获取交易业务账户列表](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html#5754)
     时，返回结果新增了账户状态 。综合账户标记为 `ACTIVE` 生效账户，被停用的单币种账户标记为 `DISABLED` 失效账户。
*   [下单](https://openapi.futunn.com/futu-api-doc/trade/place-order.html#4080)
    、[改单撤单](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html#7408)
    、[查询最大可买可卖](https://openapi.futunn.com/futu-api-doc/trade/get-max-trd-qtys.html#2713)
     等交易接口表现
    *   支持使用 `ACTIVE` 生效账户所对应的 acc\_id或acc\_index 进行购买力查询与交易。
    *   不支持使用 `DISABLED` 失效账户所对应的 acc\_id或acc\_index 进行购买力查询与交易，若使用，将会出现报错信息。
    *   Python API用户：在接口入参中，请指定 acc\_id 为升级后的综合账户。
    *   非Python API用户：在 TrdHeader 中，请指定accID为升级后的综合账户。

← [交易相关](https://openapi.futunn.com/futu-api-doc/qa/trade.html)

[其他](https://openapi.futunn.com/futu-api-doc/qa/other.html)

*   [Q1：如何编译C++ API？](https://openapi.futunn.com/futu-api-doc/qa/other.html#4792)
    
*   [Q2：有没有更完整的策略样例可以参考？](https://openapi.futunn.com/futu-api-doc/qa/other.html#1578)
    
*   [Q3：使用 python API 导入异常](https://openapi.futunn.com/futu-api-doc/qa/other.html#4857)
    
*   [Q4： import 成功了，仍然调用不了相关接口？](https://openapi.futunn.com/futu-api-doc/qa/other.html#100)
    
*   [Q5：协议加密相关](https://openapi.futunn.com/futu-api-doc/qa/other.html#4601)
    
*   [Q6：为什么我获取的 DataFrame 数据，只能展示一部分 ？](https://openapi.futunn.com/futu-api-doc/qa/other.html#6418)
    
*   [Q7：Mac 机器使用 C++ 语言的 API，遇到 “无法打开 libFTAPIChannel.dylib” 的问题](https://openapi.futunn.com/futu-api-doc/qa/other.html#3535)
    
*   [Q8：Python 用户，为什么在 OpenD 配置文件中设置了日志级别为 no 后，log 文件夹下仍然持续产生超大容量的日志文件？](https://openapi.futunn.com/futu-api-doc/qa/other.html#4750)
    
*   [Q9：对于 5.4 及以上的版本，Java API 的库名和配置方式的变更](https://openapi.futunn.com/futu-api-doc/qa/other.html#5759)
    
*   [Q10：Python 用户，使用 pyinstaller 打包脚本时报错：找不到 Common\_pb2 模块](https://openapi.futunn.com/futu-api-doc/qa/other.html#5449)
    
*   [Q11：接口调用结果正常，但其返回表现不符合预期？](https://openapi.futunn.com/futu-api-doc/qa/other.html#7695)
    
*   [Q12：WebSocket相关](https://openapi.futunn.com/futu-api-doc/qa/other.html#6319)
    
*   [Q13：API 的行情和交易服务分别部署在哪里？](https://openapi.futunn.com/futu-api-doc/qa/other.html#9759)
    
*   [Q14：关于综合账户升级的过渡指引](https://openapi.futunn.com/futu-api-doc/qa/other.html#7297)