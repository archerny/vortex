# 可视化 OpenD | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quick/opend-base.html

[#](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#4147)
 可视化 OpenD
==================================================================================

OpenD 提供可视化和命令行两种运行方式，这里介绍操作比较简单的可视化 OpenD。

如果想要了解命令行的方式请参考 [命令行 OpenD](https://openapi.futunn.com/futu-api-doc/opend/opend-cmd.html)
 。

[#](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#4147-2)
 可视化 OpenD
------------------------------------------------------------------------------------

### [#](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#495)
 第一步 下载

可视化 OpenD 支持 Windows、MacOS、CentOS、Ubuntu 四种系统（点击完成下载）。

*   OpenD - [Windows](https://www.futunn.com/download/fetch-lasted-link?name=opend-windows)
     、[MacOS](https://www.futunn.com/download/fetch-lasted-link?name=opend-macos)
     、[CenOS](https://www.futunn.com/download/fetch-lasted-link?name=opend-centos)
     、[Ubuntu](https://www.futunn.com/download/fetch-lasted-link?name=opend-ubuntu)
    

### [#](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#9666)
 第二步 安装运行

*   解压文件，找到对应的安装文件可一键安装运行。
*   Windows 系统默认安装在 `%appdata%` 目录下。

### [#](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724)
 第三步 配置

*   可视化 OpenD 启动配置在图形界面的右侧，如下图所示：

![ui-config](https://openapi.futunn.com/futu-api-doc/assets/img/ui-config.04197592.png)

**配置项列表**：

| 配置项 | 说明  |
| --- | --- |
| 监听地址 | API 协议监听地址<br>(ℹ️ 可选：  <br>*   127.0.0.1（监听来自本地的连接）<br>*   0.0.0.0（监听来自所有网卡的连接）<br>或填入本机某个网卡地址) |
| 监听端口 | API 协议监听端口 |
| 日志级别 | OpenD 日志级别<br>(ℹ️ 可选：  <br>*   no（无日志）<br>*   debug（最详细）<br>*   info（次详细）) |
| 语言  | 中英语言<br>(ℹ️ 可选：  <br>*   简体中文<br>*   English) |
| 期货交易 API 时区 | 期货交易 API 时区<br>(ℹ️ 使用期货账户调用 **交易 API** 时，涉及的时间按照此时区规则) |
| API 推送频率 | API 订阅数据推送频率控制<br>(ℹ️ *   单位：毫秒<br>*   目前不包括 K 线和分时) |
| Telnet 地址 | 远程操作命令监听地址 |
| Telnet 端口 | 远程操作命令监听端口 |
| 加密私钥路径 | API 协议 [RSA](https://openapi.futunn.com/futu-api-doc/qa/other.html#4601)<br> 加密私钥（PKCS#1）文件绝对路径 |
| WebSocket 监听地址 | WebSocket 服务监听地址<br>(ℹ️ 可选：  <br>*   127.0.0.1（监听来自本地的连接）<br>*   0.0.0.0（监听来自所有网卡的连接）) |
| WebSocket 端口 | WebSocket 服务监听端口 |
| WebSocket 证书 | WebSocket 证书文件路径<br>(ℹ️ 不配置则不启用，需要和私钥同时配置) |
| WebSocket 私钥 | WebSocket 证书私钥文件路径<br>(ℹ️ 私钥不可设置密码，不配置则不启用，需要和证书同时配置) |
| WebSocket 鉴权密钥 | 密钥密文（32 位 MD5 加密 16 进制）<br>(ℹ️ JavaScript 脚本连接时，用于判断是否可信连接) |

提示

*   可视化 OpenD，是通过启动命令行 OpenD 来提供服务，且通过 WebSocket 与命令行 OpenD 交互，所以必定启动 WebSocket 功能。
    
*   为保证您的证券业务账户安全，如果监听地址不是本地，您必须配置私钥才能使用交易接口。行情接口不受此限制。
    
*   当 WebSocket 监听地址不是本地，需配置 SSL 才可以启动，且证书私钥生成不可设置密码。
    
*   密文是明文经过 32 位 MD5 加密后用 16 进制表示的数据，搜索在线 MD5 加密（注意，通过第三方网站计算可能有记录撞库的风险）或下载 MD5 计算工具可计算得到。32 位 MD5 密文如下图红框区域（e10adc3949ba59abbe56e057f20f883e）： ![md5.png](<Base64-Image-Removed>)
    
*   OpenD 默认读取同目录下的 OpenD.xml。在 MacOS 上，由于系统保护机制，OpenD.app 在运行时会被分配一个随机路径，导致无法找到原本的路径。此时有以下方法：
    
    *   执行 tar 包下的 fixrun.sh
    *   用命令行参数`-cfg_file`指定配置文件路径，见下面说明
*   日志级别默认 info 级别，在系统开发阶段，不建议关闭日志或者将日志修改到 warning，error，fatal 级别，防止出现问题时无法定位。
    

### [#](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#9454)
 第四步 登录

*   输入账号密码，点击登录。  
    首次登录，您需要先完成问卷评估与协议确认，完成后重新登录即可。  
    登录成功后，您可以看到自己的账号信息和 [行情权限](https://openapi.futunn.com/futu-api-doc/intro/authority.html#2867)
    。

OpenD 提供可视化和命令行两种运行方式，这里介绍操作比较简单的可视化 OpenD。

如果想要了解命令行的方式请参考 [命令行 OpenD](https://openapi.futunn.com/futu-api-doc/opend/opend-cmd.html)
 。

[#](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#4147-3)
 可视化 OpenD
------------------------------------------------------------------------------------

### [#](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#495-2)
 第一步 下载

*   可视化 OpenD 支持 Windows、MacOS、CentOS、Ubuntu 四种系统。
*   您可以通过 [moomoo 官网](https://www.moomoo.com/download/OpenAPI)
     下载。

### [#](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#9666-2)
 第二步 安装运行

*   解压文件，找到对应的安装文件可一键安装运行。
*   Windows 系统默认安装在 `%appdata%` 目录下。

### [#](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724-2)
 第三步 配置

*   可视化 OpenD 启动配置在图形界面的右侧，如下图所示：

![ui-config](https://openapi.futunn.com/futu-api-doc/assets/img/mmui-config.da06a350.png)

**配置项列表**：

| 配置项 | 说明  |
| --- | --- |
| 监听地址 | API 协议监听地址<br>(ℹ️ 可选：  <br>*   127.0.0.1（监听来自本地的连接）<br>*   0.0.0.0（监听来自所有网卡的连接）<br>或填入本机某个网卡地址) |
| 监听端口 | API 协议监听端口 |
| 日志级别 | OpenD 日志级别<br>(ℹ️ 可选：  <br>*   no（无日志）<br>*   debug（最详细）<br>*   info（次详细）) |
| 语言  | 中英语言<br>(ℹ️ 可选：  <br>*   简体中文<br>*   English) |
| 期货交易 API 时区 | 期货交易 API 时区<br>(ℹ️ 使用期货账户调用 **交易 API** 时，涉及的时间按照此时区规则) |
| API 推送频率 | API 订阅数据推送频率控制<br>(ℹ️ *   单位：毫秒<br>*   目前不包括 K 线和分时) |
| Telnet 地址 | 远程操作命令监听地址 |
| Telnet 端口 | 远程操作命令监听端口 |
| 加密私钥路径 | API 协议 [RSA](https://openapi.futunn.com/futu-api-doc/qa/other.html#4601)<br> 加密私钥（PKCS#1）文件绝对路径 |
| WebSocket 监听地址 | WebSocket 服务监听地址<br>(ℹ️ 可选：  <br>*   127.0.0.1（监听来自本地的连接）<br>*   0.0.0.0（监听来自所有网卡的连接）) |
| WebSocket 端口 | WebSocket 服务监听端口 |
| WebSocket 证书 | WebSocket 证书文件路径<br>(ℹ️ 不配置则不启用，需要和私钥同时配置) |
| WebSocket 私钥 | WebSocket 证书私钥文件路径<br>(ℹ️ 私钥不可设置密码，不配置则不启用，需要和证书同时配置) |
| WebSocket 鉴权密钥 | 密钥密文（32 位 MD5 加密 16 进制）<br>(ℹ️ JavaScript 脚本连接时，用于判断是否可信连接) |

提示

*   可视化 OpenD，是通过启动命令行 OpenD 来提供服务，且通过 WebSocket 与命令行 OpenD 交互，所以必定启动 WebSocket 功能。
    
*   为保证您的证券业务账户安全，如果监听地址不是本地，您必须配置私钥才能使用交易接口。行情接口不受此限制。
    
*   当 WebSocket 监听地址不是本地，需配置 SSL 才可以启动，且证书私钥生成不可设置密码。
    
*   密文是明文经过 32 位 MD5 加密后用 16 进制表示的数据，搜索在线 MD5 加密（注意，通过第三方网站计算可能有记录撞库的风险）或下载 MD5 计算工具可计算得到。32 位 MD5 密文如下图红框区域（e10adc3949ba59abbe56e057f20f883e）： ![md5.png](<Base64-Image-Removed>)
    
*   OpenD 默认读取同目录下的 OpenD.xml。在 MacOS 上，由于系统保护机制，OpenD.app 在运行时会被分配一个随机路径，导致无法找到原本的路径。此时有以下方法：
    
    *   执行 tar 包下的 fixrun.sh
    *   用命令行参数`-cfg_file`指定配置文件路径，见下面说明
*   日志级别默认 info 级别，在系统开发阶段，不建议关闭日志或者将日志修改到 warning，error，fatal 级别，防止出现问题时无法定位。
    

### [#](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#9454-2)
 第四步 登录

*   输入账号密码，点击登录。  
    首次登录，您需要先完成问卷评估与协议确认，完成后重新登录即可。  
    登录成功后，您可以看到自己的账号信息和 [行情权限](https://openapi.futunn.com/futu-api-doc/intro/authority.html#2867)
    。

← [接入 AI 与 OpenClaw](https://openapi.futunn.com/futu-api-doc/intro/ai.html) [编程环境搭建](https://openapi.futunn.com/futu-api-doc/quick/env.html)
 →