# 介绍 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/

[#](https://openapi.futunn.com/futu-api-doc/#342)
 介绍
=====================================================

[#](https://openapi.futunn.com/futu-api-doc/#8831)
 概述
------------------------------------------------------

量化接口，为您的程序化交易，提供丰富的行情和交易接口，满足每一位开发者的量化投资需求，助力您的宽客梦想。

牛牛用户可以 [点击这里](https://www.futunn.com/OpenAPI)
 了解更多。

Futu API 由 OpenD 和API SDK组成：

*   OpenD 是 Futu API 的网关程序，运行于您的本地电脑或云端服务器，负责中转协议请求到富途后台，并将处理后的数据返回。
*   API SDK是富途为主流的编程语言（Python、Java、C#、C++、JavaScript）封装的SDK，以方便您调用，降低策略开发难度。如果您希望使用的语言没有在上述之列，您仍可自行对接裸协议，完成策略开发。

下面的框架图和时序图，帮助您更好地了解 Futu API。

![openapi-frame](https://openapi.futunn.com/futu-api-doc/assets/img/nnopenapi-frame.97d0ce07.png)

![openapi-interactive](https://openapi.futunn.com/futu-api-doc/assets/img/nnopenapi-interactive.16699b7b.png)

初次接触 Futu API，您需要进行如下两步操作：

第一步，在本地或云端安装并启动一个网关程序 [OpenD](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html)
。

OpenD 以自定义 TCP 协议的方式对外暴露接口，负责中转协议请求到富途服务器，并将处理后的数据返回，该协议接口与编程语言无关。

第二步，下载 Futu API，完成 [环境搭建](https://openapi.futunn.com/futu-api-doc/quick/env.html)
，以便快速调用。

为方便您的使用，富途对主流的编程语言，封装了相应的 API SDK（以下简称 Futu API）。

moomoo 用户可以 [点击这里](https://www.moomoo.com/OpenAPI)
 了解更多。

Moomoo API 由 OpenD 和 API SDK 组成：

*   OpenD 是 Moomoo API 的网关程序，运行于您的本地电脑或云端服务器，负责中转协议请求到富途后台，并将处理后的数据返回。
*   API SDK是富途为主流的编程语言（Python、Java、C#、C++、JavaScript）封装的 API SDK，以方便您调用，降低策略开发难度。如果您希望使用的语言没有在上述之列，您仍可自行对接裸协议，完成策略开发。

下面的框架图和时序图，帮助您更好地了解 Moomoo API。

![openapi-frame](https://openapi.futunn.com/futu-api-doc/assets/img/mmopenapi-frame.3a598816.png)

![openapi-interactive](https://openapi.futunn.com/futu-api-doc/assets/img/mmopenapi-interactive.5073390c.png)

初次接触 Moomoo API，您需要进行如下两步操作：

第一步，在本地或云端安装并启动一个网关程序 [OpenD](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html)
。

OpenD 以自定义 TCP 协议的方式对外暴露接口，负责中转协议请求到富途服务器，并将处理后的数据返回，该协议接口与编程语言无关。

第二步，下载 Moomoo API，完成 [环境搭建](https://openapi.futunn.com/futu-api-doc/quick/env.html)
，以便快速调用。

为方便您的使用，富途对主流的编程语言，封装了相应的 API SDK（以下简称 Moomoo API）。

[#](https://openapi.futunn.com/futu-api-doc/#7836)
 账号
------------------------------------------------------

Futu API 涉及 2 类账号，分别是 \*\*平台账号\*\* 和 \*\*综合账户\*\*。

Moomoo API 涉及 2 类账号，分别是 \*\*平台账号\*\* 和 \*\*综合账户\*\*。

### [#](https://openapi.futunn.com/futu-api-doc/#1200)
 平台账号

平台账号是您在富途的用户 ID（牛牛号），此账号体系适用于富途牛牛 APP、Futu API。 您可以使用平台账号（牛牛号）和登录密码，登录 OpenD 并获取行情。

平台账号是您在 moomoo 的用户 ID（moomoo 号），此账号体系适用于moomoo APP、Moomoo API。 您可以使用平台账号（moomoo 号）和登录密码，登录 OpenD 并获取行情。

### [#](https://openapi.futunn.com/futu-api-doc/#4459)
 综合账户

综合账户支持以多种货币在同一个账户内交易不同市场品类（港股、美股、A股通、基金）。您可以通过一个账户进行全市场交易，不需要再管理多个账户。  
综合账户包括综合账户 - 证券，综合账户 - 期货等业务账户：

*   综合账户 - 证券，用于交易全市场的股票、ETFs、期权等证券类产品。
*   综合账户 - 期货，用于交易全市场的期货产品，目前支持香港市场期货、美国市场 CME Group 期货、新加坡市场期货、日本市场期货。

[#](https://openapi.futunn.com/futu-api-doc/#508)
 功能
-----------------------------------------------------

Futu API 的功能主要有两部分：行情和交易。

Moomoo API 的功能主要有两部分：行情和交易。

### [#](https://openapi.futunn.com/futu-api-doc/#8846)
 行情功能

#### [#](https://openapi.futunn.com/futu-api-doc/#1935)
 行情数据品类

支持香港、美国、A 股市场的行情数据，涉及的品类包括股票、指数、期权、期货等，具体支持的品种见下表。  
获取行情数据需要相关权限，如需了解行情权限的获取方式以及限制规则，请 [点击这里](https://openapi.futunn.com/futu-api-doc/intro/authority.html#2867)
。

| 市场  | 品种  | 牛牛用户 |
| --- | --- | --- |
| 香港市场 | 股票、ETFs、窝轮、牛熊、界内证 | ✓   |
| 期权  | ✓   |
| 期货  | ✓   |
| 指数  | ✓   |
| 板块  | ✓   |
| 美国市场 | 股票、ETFs<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>含纽交所、美交所、纳斯达克上市的股票、ETFs | ✓   |
| OTC 股票 | X   |
| 期权<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>含普通股票期权、指数期权 | ✓   |
| 期货  | ✓   |
| 指数  | X   |
| 板块  | ✓   |
| A 股市场 | 股票、ETFs | ✓   |
| 指数  | ✓   |
| 板块  | ✓   |
| 新加坡市场 | 股票、ETFs、窝轮、REITs、DLCs | X   |
| 期货  | X   |
| 日本市场 | 股票、ETFs、REITs | X   |
| 期货  | X   |
| 澳大利亚市场 | 股票、ETFs | X   |
| 环球市场 | 外汇  | X   |

支持香港、美国、A 股市场的行情数据，涉及的品类包括股票、指数、期权、期货等，具体支持的品种见下表。  
获取行情数据需要相关权限，如需了解行情权限的获取方式以及限制规则，请 [点击这里](https://openapi.futunn.com/futu-api-doc/intro/authority.html#2867)
。

| 市场  | 品种  | moomoo 用户 |
| --- | --- | --- |
| 香港市场 | 股票、ETFs、窝轮、牛熊、界内证 | ✓   |
| 期权  | ✓   |
| 期货  | ✓   |
| 指数  | ✓   |
| 板块  | ✓   |
| 美国市场 | 股票、ETFs<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>含纽交所、美交所、纳斯达克上市的股票、ETFs | ✓   |
| OTC 股票 | X   |
| 期权<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>含普通股票期权、指数期权 | ✓   |
| 期货  | ✓   |
| 指数  | X   |
| 板块  | ✓   |
| A 股市场 | 股票、ETFs | ✓   |
| 指数  | ✓   |
| 板块  | ✓   |
| 新加坡市场 | 股票、ETFs、窝轮、REITs、DLCs | X   |
| 期货  | X   |
| 日本市场 | 股票、ETFs、REITs | X   |
| 期货  | X   |
| 澳大利亚市场 | 股票、ETFs | X   |
| 环球市场 | 外汇  | X   |

#### [#](https://openapi.futunn.com/futu-api-doc/#144)
 行情数据获取方式

*   订阅并接收实时报价、实时 K 线、实时逐笔、实时摆盘等数据推送
*   拉取最新市场快照，历史 K 线等

### [#](https://openapi.futunn.com/futu-api-doc/#1396)
 交易功能

#### [#](https://openapi.futunn.com/futu-api-doc/#5796)
 交易能力

支持香港、美国、A 股、新加坡、日本 5 个市场的交易能力，涉及的品类包括股票、期权、期货等，具体见下表：

| 市场  | 品种  | 模拟交易 | 真实交易 |     |     |     |     |     |     |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| FUTU HK | Moomoo US | Moomoo SG | Moomoo AU | Moomoo MY | Moomoo CA | Moomoo JP |
| 香港市场 | 股票、ETFs、窝轮、牛熊、界内证 | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | X   | X   |
| 期权<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>含指数期权，需使用期货账户交易 | ✓   | ✓   | X   | X   | X   | X   | X   | X   |
| 期货  | ✓   | ✓   | X   | X   | X   | X   | X   | X   |
| 美国市场 | 股票、ETFs | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| 期权  | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| 期货  | ✓   | ✓   | X   | ✓   | X   | ✓   | X   | X   |
| A 股市场 | A 股通股票 | ✓   | ✓   | ✓   | ✓   | X   | ✓   | X   | X   |
| 非 A 股通股票 | ✓   | X   | X   | X   | X   | X   | X   | X   |
| 新加坡市场 | 股票、ETFs、窝轮、REITs、DLCs | X   | X   | X   | X   | X   | X   | X   | X   |
| 期货  | ✓   | ✓   | X   | ✓   | X   | X   | X   | X   |
| 日本市场 | 股票、ETFs、REITs | X   | X   | X   | X   | X   | X   | X   | X   |
| 期货  | ✓   | ✓   | X   | X   | X   | X   | X   | X   |
| 澳大利亚市场 | 股票、ETFs | X   | X   | X   | X   | X   | X   | X   | X   |
| 加拿大市场 | 股票  | X   | X   | X   | X   | X   | X   | X   | X   |

#### [#](https://openapi.futunn.com/futu-api-doc/#3230)
 交易方式

真实交易和模拟交易使用同一套交易接口。

[#](https://openapi.futunn.com/futu-api-doc/#6435)
 特点
------------------------------------------------------

1.  全平台多语言：

*   OpenD 支持 Windows、MacOS、CentOS、Ubuntu
*   Futu API 支持 Python、Java、C#、C++、JavaScript 等主流语言

2.  稳定极速免费：

*   稳定的技术架构，直连交易所一触即达
*   下单最快只需 0.0014 s
*   通过 Futu API 交易无附加收费

3.  丰富的投资品类：

*   支持美国、香港等多个市场的实时行情、实盘交易及模拟交易

4.  专业的机构服务：

*   定制化的行情交易解决方案

1.  全平台多语言：

*   OpenD 支持 Windows、MacOS、CentOS、Ubuntu
*   Moomoo API 支持 Python、Java、C#、C++、JavaScript 等主流语言

2.  稳定极速免费：

*   稳定的技术架构，直连交易所一触即达
*   下单最快只需 0.0014 s
*   通过 Moomoo API 交易无附加收费

3.  丰富的投资品类：

*   支持美国、香港等多个市场的实时行情、实盘交易及模拟交易

4.  专业的机构服务：

*   定制化的行情交易解决方案

[权限和限制](https://openapi.futunn.com/futu-api-doc/intro/authority.html)
 →