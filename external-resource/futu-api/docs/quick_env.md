[#](./quick_env.md#1427)
 编程环境搭建
========================================================================

注意

不同的编程语言，编程环境搭建的方法有所不同。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

[#](./quick_env.md#4558)
 Python 环境
---------------------------------------------------------------------------

### [#](./quick_env.md#2847)
 环境要求

*   操作系统要求：
    *   Windows 7/10 的 32 或 64 位操作系统
    *   Mac 10.11 及以上的 64 位操作系统
    *   CentOS 7 及以上的 64 位操作系统
    *   Ubuntu 16.04 以上的 64 位操作系统
*   Python 版本要求：
    *   Python 3.6 及以上

### [#](./quick_env.md#9063)
 环境搭建

#### [#](./quick_env.md#7616)
 1. 安装 Python

为避免因环境问题导致的运行失败，我们推荐 Python 3.8 版本。

下载地址：[Python 下载](https://www.python.org/downloads/)

提示

如下内容提供了两种方式切换为 Python 3.8 环境：

*   方式一  
    把 Python 3.8 的安装路径，添加到环境变量 path 中。
    
*   方式二  
    如果您使用的是 PyCharm，可以在 Project Interpreter 中，将使用的环境配置为 Python 3.8。
    

![pycharm-switch-python](https://openapi.futunn.com/futu-api-doc/assets/img/pycharm-switch-python.ca03dfdc.png)

当安装成功后，执行如下命令来查看是否安装成功:  
`python -V`（Windows） 或 `python3 -V`（Linux 和 Mac）

#### [#](./quick_env.md#110)
 2. 安装 PyCharm（可选）

我们推荐您使用 [PyCharm](https://www.jetbrains.com/pycharm/download/)
 作为 Python IDE（集成开发环境）。

#### [#](./quick_env.md#5507)
 3. 安装 TA-Lib（可选）

TA-Lib 用中文可以称作技术分析库，是一种广泛用在程序化交易中，进行金融市场数据的技术分析的函数库。它提供了多种技术分析的函数，方便我们量化投资中编程工作。

安装方法：在 cmd 中直接使用 pip 安装  
`$ pip install TA-Lib`

提示

*   安装 TA-Lib 非必须，可先跳过该步骤

[#](./quick_env.md#5358)
 C# 环境
-----------------------------------------------------------------------

### [#](./quick_env.md#2847-2)
 环境要求

*   操作系统要求：
    *   Windows 7 及以上的 32 或 64 位操作系统
    *   Mac 10.11 或以上的 64 位操作系统
    *   CentOS 7 及以上的 64 位操作系统
    *   Ubuntu 16.04 及以上的 64 位操作系统
*   官方提供的 SDK 编译环境为： Visual Studio 2013 + .NET Framework 4.5，或者 Visual Studio 2017 + .NET Core 2.1。
*   如需更高版本 Visual Studio 环境，可以升级 FTAPI4Net.sln，并重新从源码编译。

### [#](./quick_env.md#9063-2)
 环境搭建

#### [#](./quick_env.md#5057)
 1. 安装 .NET Framework 或 .NET Core

[.NET Framework](https://dotnet.microsoft.com/download/dotnet-framework/)
 或者 [.NET Core](https://dotnet.microsoft.com/download/dotnet-core)
 ，您可选择一个进行安装。

#### [#](./quick_env.md#3018)
 2. 安装 Visual Studio 开发环境（可选）

我们推荐使用 [Visual Studio](https://visualstudio.microsoft.com/)
 作为 C# IDE（集成开发环境）。

[#](./quick_env.md#1671)
 Java 环境
-------------------------------------------------------------------------

### [#](./quick_env.md#2847-3)
 环境要求

*   操作系统要求：
    *   Windows 7 及以上的 32 或 64 位操作系统
    *   Mac 10.11 及以上的 64 位操作系统
    *   CentOS 7 及以上的 64 位操作系统
    *   Ubuntu 16.04 及以上的 64 位操作系统
*   官方提供的 SDK 编译环境为 OpenJDK 8。如需更高版本 JDK，可以自行设置编译环境并重新从源码编译。

### [#](./quick_env.md#9063-3)
 环境搭建

#### [#](./quick_env.md#265)
 1. 安装 JDK

推荐安装 OpenJDK 8。

#### [#](./quick_env.md#7833)
 2. 安装 IntelliJ IDEA 开发环境（可选）

我们推荐使用 [IntelliJ IDEA](https://www.jetbrains.com/idea/)
 作为 Java IDE（集成开发环境）。

[#](./quick_env.md#3310)
 C++ 环境
------------------------------------------------------------------------

### [#](./quick_env.md#2847-4)
 环境要求

*   官方默认环境：
    *   Windows：
        *   Windows 7 及以上的 32 或 64 位操作系统
        *   官方提供的 SDK 编译环境为 Visual Studio 2013。如需更高版本 VS 环境，建议升级 FTAPI.sln。
    *   MacOS：
        *   64 位操作系统
        *   官方提供的 SDK 编译环境为 MacOS Mojave，Xcode 11。其它版本需要升级 Xcode 工程文件。
    *   Linux：
        *   64 位操作系统
        *   官方提供的 SDK 编译环境为 CentOS 7（gcc 4.8.5）和 Ubuntu 16.04（gcc 5.4.0）。
*   非官方环境：
    *   需要自行编译 FTAPI 和 Protobuf，源码在 `/FTAPI4CPP/Src` 目录下。

[#](./quick_env.md#7649)
 JavaScript 环境
-------------------------------------------------------------------------------

### [#](./quick_env.md#2847-5)
 环境要求

*   操作系统要求：
    *   Windows 7 及以上的 32 或 64 位操作系统
    *   Mac 10.11 及以上的 64 位操作系统
    *   CentOS 7 及以上的 64 位操作系统
    *   Ubuntu 16.04 及以上的 64 位操作系统
*   建议 Chrome 70 及以上版本。

### [#](./quick_env.md#9063-4)
 环境搭建

#### [#](./quick_env.md#4654)
 1. 安装 Node.js

Node.js 是一个基于 Chrome 内核的开源、跨平台的 JavaScript 运行环境，点击 [这里](https://nodejs.org/zh-cn/download/)
 下载。

#### [#](./quick_env.md#1647)
 2. 安装 Visual Studio Code（可选）

我们推荐使用 [Visual Studio Code](https://code.visualstudio.com/)
 作为 JavaScript IDE（集成开发环境）。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

[#](./quick_env.md#4558-2)
 Python 环境
-----------------------------------------------------------------------------

### [#](./quick_env.md#2847-6)
 环境要求

*   操作系统要求：
    *   Windows 7/10 的 32 或 64 位操作系统
    *   Mac 10.11 及以上的 64 位操作系统
    *   CentOS 7 及以上的 64 位操作系统
    *   Ubuntu 16.04 以上的 64 位操作系统
*   Python 版本要求：
    *   Python 3.6 及以上

### [#](./quick_env.md#9063-5)
 环境搭建

#### [#](./quick_env.md#7616-2)
 1. 安装 Python

为避免因环境问题导致的运行失败，我们推荐 Python 3.8 版本。

下载地址：[Python 下载](https://www.python.org/downloads/)

提示

如下内容提供了两种方式切换为 Python 3.8 环境：

*   方式一  
    把 Python 3.8 的安装路径，添加到环境变量 path 中。
    
*   方式二  
    如果您使用的是 PyCharm，可以在 Project Interpreter 中，将使用的环境配置为 Python 3.8。
    

![pycharm-switch-python](https://openapi.futunn.com/futu-api-doc/assets/img/pycharm-switch-python.ca03dfdc.png)

当安装成功后，执行如下命令来查看是否安装成功:  
`python -V`（Windows） 或 `python3 -V`（Linux 和 Mac）

#### [#](./quick_env.md#110-2)
 2. 安装 PyCharm（可选）

我们推荐您使用 [PyCharm](https://www.jetbrains.com/pycharm/download/)
 作为 Python IDE（集成开发环境）。

#### [#](./quick_env.md#5507-2)
 3. 安装 TA-Lib（可选）

TA-Lib 用中文可以称作技术分析库，是一种广泛用在程序化交易中，进行金融市场数据的技术分析的函数库。它提供了多种技术分析的函数，方便我们量化投资中编程工作。

安装方法：在 cmd 中直接使用 pip 安装  
`$ pip install TA-Lib`

提示

*   安装 TA-Lib 非必须，可先跳过该步骤

[#](./quick_env.md#5358-2)
 C# 环境
-------------------------------------------------------------------------

### [#](./quick_env.md#2847-7)
 环境要求

*   操作系统要求：
    *   Windows 7 及以上的 32 或 64 位操作系统
    *   Mac 10.11 或以上的 64 位操作系统
    *   CentOS 7 及以上的 64 位操作系统
    *   Ubuntu 16.04 及以上的 64 位操作系统
*   官方提供的 SDK 编译环境为： Visual Studio 2013 + .NET Framework 4.5，或者 Visual Studio 2017 + .NET Core 2.1。
*   如需更高版本 Visual Studio 环境，可以升级 MMAPI4Net.sln，并重新从源码编译。

### [#](./quick_env.md#9063-6)
 环境搭建

#### [#](./quick_env.md#5057-2)
 1. 安装 .NET Framework 或 .NET Core

[.NET Framework](https://dotnet.microsoft.com/download/dotnet-framework/)
 或者 [.NET Core](https://dotnet.microsoft.com/download/dotnet-core)
 ，您可选择一个进行安装。

#### [#](./quick_env.md#3018-2)
 2. 安装 Visual Studio 开发环境（可选）

我们推荐使用 [Visual Studio](https://visualstudio.microsoft.com/)
 作为 C# IDE（集成开发环境）。

[#](./quick_env.md#1671-2)
 Java 环境
---------------------------------------------------------------------------

### [#](./quick_env.md#2847-8)
 环境要求

*   操作系统要求：
    *   Windows 7 及以上的 32 或 64 位操作系统
    *   Mac 10.11 及以上的 64 位操作系统
    *   CentOS 7 及以上的 64 位操作系统
    *   Ubuntu 16.04 及以上的 64 位操作系统
*   官方提供的 SDK 编译环境为 OpenJDK 8。如需更高版本 JDK，可以自行设置编译环境并重新从源码编译。

### [#](./quick_env.md#9063-7)
 环境搭建

#### [#](./quick_env.md#265-2)
 1. 安装 JDK

推荐安装 OpenJDK 8。

#### [#](./quick_env.md#7833-2)
 2. 安装 IntelliJ IDEA 开发环境（可选）

我们推荐使用 [IntelliJ IDEA](https://www.jetbrains.com/idea/)
 作为 Java IDE（集成开发环境）。

[#](./quick_env.md#3310-2)
 C++ 环境
--------------------------------------------------------------------------

### [#](./quick_env.md#2847-9)
 环境要求

*   官方默认环境：
    *   Windows：
        *   Windows 7 及以上的 32 或 64 位操作系统
        *   官方提供的 SDK 编译环境为 Visual Studio 2013。如需更高版本 VS 环境，建议升级 FTAPI.sln。
    *   MacOS：
        *   64 位操作系统
        *   官方提供的 SDK 编译环境为 MacOS Mojave，Xcode 11。其它版本需要升级 Xcode 工程文件。
    *   Linux：
        *   64 位操作系统
        *   官方提供的 SDK 编译环境为 CentOS 7（gcc 4.8.5）和 Ubuntu 16.04（gcc 5.4.0）。
*   非官方环境：
    *   需要自行编译 MMAPI 和 Protobuf，源码在 `/MMAPI4CPP/Src` 目录下。

[#](./quick_env.md#7649-2)
 JavaScript 环境
---------------------------------------------------------------------------------

### [#](./quick_env.md#2847-10)
 环境要求

*   操作系统要求：
    *   Windows 7 及以上的 32 或 64 位操作系统
    *   Mac 10.11 及以上的 64 位操作系统
    *   CentOS 7 及以上的 64 位操作系统
    *   Ubuntu 16.04 及以上的 64 位操作系统
*   建议 Chrome 70 及以上版本。

### [#](./quick_env.md#9063-8)
 环境搭建

#### [#](./quick_env.md#4654-2)
 1. 安装 Node.js

Node.js 是一个基于 Chrome 内核的开源、跨平台的 JavaScript 运行环境，点击 [这里](https://nodejs.org/zh-cn/download/)
 下载。

#### [#](./quick_env.md#1647-2)
 2. 安装 Visual Studio Code（可选）

我们推荐使用 [Visual Studio Code](https://code.visualstudio.com/)
 作为 JavaScript IDE（集成开发环境）。

← [可视化 OpenD](./quick_opend-base.md) [简易程序运行](./quick_demo.md)
 →

[编程环境搭建](./quick_env.md)