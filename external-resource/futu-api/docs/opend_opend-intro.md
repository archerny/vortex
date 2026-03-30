[#](./opend_opend-intro.md#8831)
 概述
============================================================================

*   OpenD 是 Futu API 的网关程序，运行于您的本地电脑或云端服务器，负责中转协议请求到富途服务器，并将处理后的数据返回。是运行 Futu API 程序必要的前提。
*   OpenD 支持 Windows、MacOS、CentOS、Ubuntu 四个平台。
*   OpenD 集成了登录功能。运行时，可以使用 **平台账号**（牛牛号）、**邮箱**、**手机号** 和 **登录密码** 进行登录。
*   OpenD 登录成功后，会启动 Socket 服务以供 Futu API 连接和通信。

*   OpenD 是 moomoo API 的网关程序，运行于您的本地电脑或云端服务器，负责中转协议请求到富途服务器，并将处理后的数据返回。是运行 moomoo API 程序必要的前提。
*   OpenD 支持 Windows、MacOS、CentOS、Ubuntu 四个平台。
*   OpenD 集成了登录功能。运行时，需要使用 **平台账号**（moomoo 号）、**邮箱**、**手机号** 和 **登录密码** 进行登录。
*   OpenD 登录成功后，会启动 Socket 服务以供 moomoo API 连接和通信。

[#](./opend_opend-intro.md#2578)
 运行方式
------------------------------------------------------------------------------

OpenD 目前提供两种安装运行方式，您可选择任一方式：

*   可视化 OpenD：提供界面化应用程序，操作便捷，尤其适合入门用户，安装和运行请参考 [可视化 OpenD](./quick_opend-base.md)
    。
*   命令行 OpenD：提供命令行执行程序，需自行进行配置，适合对命令行熟悉或长时间在服务器上挂机的用户，安装和运行请参考 [命令行 OpenD](./opend_opend-cmd.md)
    。

OpenD 目前提供两种安装运行方式，您可选择任一方式：

*   可视化 OpenD：提供界面化应用程序，操作便捷，尤其适合入门用户，安装和运行请参考 [可视化 OpenD](./quick_opend-base.md)
    。
*   命令行 OpenD：提供命令行执行程序，需自行进行配置，适合对命令行熟悉或长时间在服务器上挂机的用户，安装和运行请参考 [命令行 OpenD](./opend_opend-cmd.md)
    。

[#](./opend_opend-intro.md#7658)
 运行时操作
-------------------------------------------------------------------------------

OpenD 在运行过程中，可以查看用户额度、行情权限、链接状态、延迟统计，以及操作关闭 API 连接、重登录、退出登录等运维操作。  
具体方法可以查看下表：

| 方式  | 可视化 OpenD | 命令行 OpenD |
| --- | --- | --- |
| 直接方式 | 界面查看或操作 | 命令行发送 [运维命令](./opend_opend-operate.md) |
| 间接方式 | 通过 Telnet 发送 [运维命令](./opend_opend-operate.md) | 通过 Telnet 发送 [运维命令](./opend_opend-operate.md) |

OpenD 在运行过程中，可以查看用户额度、行情权限、链接状态、延迟统计，以及操作关闭 API 连接、重登录、退出登录等运维操作。  
具体方法可以查看下表：

| 方式  | 可视化 OpenD | 命令行 OpenD |
| --- | --- | --- |
| 直接方式 | 界面查看或操作 | 命令行发送 [运维命令](./opend_opend-operate.md) |
| 间接方式 | 通过 Telnet 发送 [运维命令](./opend_opend-operate.md) | 通过 Telnet 发送 [运维命令](./opend_opend-operate.md) |

← [交易策略搭建示例](./quick_strategy-sample.md) [命令行 OpenD](./opend_opend-cmd.md)
 →

[概述](./opend_opend-intro.md)

*   [运行方式](./opend_opend-intro.md#2578)
    
*   [运行时操作](./opend_opend-intro.md#7658)