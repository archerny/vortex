 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/qa/quote.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/qa/quote.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/qa/quote.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/qa/quote.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/qa/quote.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/qa/quote.html)
    

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
        
    

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#390)
 行情相关
====================================================================

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#8880)
 Q1：订阅失败
------------------------------------------------------------------------

A: 订阅接口返回错误，有以下两类常见情况：

*   订阅额度不足：
    
    订阅额度规则参见 [订阅额度 & 历史 K 线额度](https://openapi.futunn.com/futu-api-doc/intro/authority.html#1314)
    
*   订阅权限不足：
    
    支持订阅的行情权限见下表
    
      
    
    | 市场  | 品种  | 支持订阅的行情权限 |
    | --- | --- | --- |
    | 香港市场 | 股票  | LV1, LV2, SF |
    | 期权  | LV1, LV2 |
    | 期货  | LV1, LV2 |
    | 美国市场 | 股票  | LV1, LV2 |
    | 期权  | LV1 |
    | 期货  | LV1, LV2 |
    | A 股市场 | 股票  | LV1 |
    
    获取行情权限的方式参见 [行情权限](https://openapi.futunn.com/futu-api-doc/intro/authority.html#2867)
    
    注意：若账号拥有上述权限，但仍订阅失败，可能存在被其他终端 [踢掉行情权限](https://openapi.futunn.com/futu-api-doc/qa/opend.html#1228)
     的情况。
    

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#3273)
 Q2：反订阅失败
-------------------------------------------------------------------------

A: 订阅至少一分钟后才能反订阅。

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#9683)
 Q3：反订阅成功但没释放额度
-------------------------------------------------------------------------------

A: 所有连接都对该行情反订阅，才会释放额度。

举例：A 连接和 B 连接都在订阅 HK.00700 的摆盘，当 A 连接反订阅后，由于 B 连接仍在调用腾讯的摆盘数据，因此 OpenD 的额度不会释放，直至所有连接都反订阅 HK.00700 的摆盘。

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#9319)
 Q4：订阅不足一分钟关闭脚本连接，会释放额度吗？
-----------------------------------------------------------------------------------------

A: 不会。连接关闭后，订阅时长不足一分钟的标的类型，会在达到一分钟后才自动反订阅，并释放相应的订阅额度。

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#3487)
 Q5：请求限频的具体限制逻辑是怎样？
-----------------------------------------------------------------------------------

A: 30 秒内最多 n 次，是指第 1 次和第 n+1 次请求间隔需要大于 30 秒。

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#1236)
 Q6：自选股添加不上是什么原因？
---------------------------------------------------------------------------------

A: 请先检查是否有超出上限，或者删除一部分自选。

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#5528)
 Q7：为什么 API 端的美股报价和牛牛显示端的全美综合报价有不同？
---------------------------------------------------------------------------------------------------

A: 由于美股交易分散在很多家交易所，富途有提供两种美股基本报价行情，一种是 Nasdaq Basic（Nasdaq 交易所的报价），另一种是全美综合报价（全美13家交易所的报价）。而 Futu API 的美股正股行情目前仅支持通过行情卡购买的方式获取 Nasdaq Basic，不支持全美综合报价。因此，如果您同时购买了显示端的全美综合报价行情卡，和仅用于 Futu API 的 Nasdaq Basic 行情卡，确实有可能出现牛牛显示端和 Futu API 端的报价差异。  
因此，如果您发现美股当天开盘价与客户端显示不一致，这是因为Futu API实时上游行情仅会获取 Nasdaq Basic 数据。

A: 由于美股交易分散在很多家交易所，富途有提供两种美股基本报价行情，一种是 Nasdaq Basic（Nasdaq 交易所的报价），另一种是全美综合报价（全美13家交易所的报价）。而 Moomoo API 的美股正股行情目前仅支持通过行情卡购买的方式获取 Nasdaq Basic，不支持全美综合报价。因此，如果您同时购买了显示端的全美综合报价行情卡，和仅用于 Moomoo API 的 Nasdaq Basic 行情卡，确实有可能出现牛牛显示端和 Moomoo API 端的报价差异。  
因此，如果您发现美股当天开盘价与客户端显示不一致，这是因为Moomoo API实时上游行情仅会获取 Nasdaq Basic 数据。

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#887)
 Q8：API 行情卡在哪里购买？
--------------------------------------------------------------------------------

A:

*   港股市场
    
    *   [港股 LV2 高级行情（仅港澳台及海外 IP）](https://qtcardfthk.futufin.com/buy?market_id=1&channel=2&good_type=1#/)
        
    *   [港股期权期货 LV2高级行情（仅港澳台及海外 IP）](https://qtcardfthk.futufin.com/buy?market_id=1&channel=2&good_type=8#/)
        
    *   [港股 LV2 + 期权期货 LV2 行情（仅港澳台及海外 IP）](https://qtcardfthk.futufin.com/buy?market_id=1&channel=2&good_type=9#/)
        
    *   [港股高级全盘行情（SF 行情）](https://qtcardfthk.futufin.com/buy?market_id=1&channel=2&good_type=10#/)
        
*   美股市场
    
    *   [Nasdaq Basic](https://qtcardfthk.futufin.com/buy?market_id=2&channel=2&good_type=12#/)
        
    *   [Nasdaq Basic+TotalView (Non-Pro)](https://qtcardfthk.futufin.com/buy?market_id=2&good_type=18&channel=2#/)
        
    *   [Nasdaq Basic+TotalView (Pro)](https://qtcardfthk.futufin.com/buy?market_id=2&good_type=19&channel=2#/)
        
    *   [期权 OPRA 实时行情](https://qtcardfthk.futufin.com/buy?market_id=2&good_type=16&qtcard_channel=2#/)
        

A:

*   港股市场
    
    *   [港股 LV2 高级行情（仅港澳台及海外 IP）](https://qtcard.moomoo.com/buy?market_id=1&good_type=1012&area_type=oversea#/)
        
    *   [港股 LV2 + 期权期货 LV2 行情（仅港澳台及海外 IP）](https://qtcard.moomoo.com/buy?market_id=1&good_type=1013&area_type=oversea#/)
        
*   美股市场
    
    *   [Nasdaq Basic](https://qtcard.moomoo.com/buy?market_id=2&qtcard_channel=2&good_type=1022#/)
        
    *   [Nasdaq Basic+TotalView (Non-Pro)](https://qtcard.moomoo.com/buy?market_id=2&qtcard_channel=2&good_type=1026#/)
        
    *   [Nasdaq Basic+TotalView (Pro)](https://qtcard.moomoo.com/buy?market_id=2&qtcard_channel=2&good_type=1027#/)
        
    *   [期权 OPRA 实时行情](https://qtcard.moomoo.com/buy?market_id=2&qtcard_channel=2&good_type=1024#/)
        

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#7600)
 Q9：为什么有时候，获取实时数据的 get 接口响应比较慢？
-----------------------------------------------------------------------------------------------

A: 因为获取实时数据的 get 接口需要先订阅，并依赖后台给 OpenD 的推送。如果用户刚订阅就立刻用 get 接口请求，OpenD 有可能尚未收到后台推送。为了防止这种情况的发生，get 接口内置了等待逻辑，3 秒内收到推送会立刻返回给脚本，超过 3 秒仍未收到后台推送，才会给脚本返回空数据。  
涉及的 get 接口包括：get\_rt\_ticker、get\_rt\_data、get\_cur\_kline、get\_order\_book、get\_broker\_queue、get\_stock\_quote。因此，当发现获取实时数据的 get 接口响应比较慢时，可以先检查一下是否是无成交数据的原因。

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2962)
 Q10：购买 API 美股 Nasdaq Basic 行情卡后，可以获取哪些数据？
----------------------------------------------------------------------------------------------------------

A: Nasdaq Basic 行情卡购买激活后，可以获取的品类涵盖 Nasdaq、NYSE、NYSE MKT 交易所上市证券（包括美股正股和 ETF，不包括美股期货和美股期权）。  
支持的数据接口包括：快照，历史 K 线，实时逐笔订阅，实时一档摆盘订阅，实时 K 线订阅，实时报价订阅，实时分时订阅，到价提醒。

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#5336)
 Q11：各个行情品类的摆盘支持多少档？
------------------------------------------------------------------------------------

A:

| 行情品类 | LV1 | LV2 | SF  |
| --- | --- | --- | --- |
| 港股（含正股、窝轮、牛熊、界内证） | /   | 10  | 全盘+千笔明细 |
| 港股期权期货 | 1   | 10  | /   |
| 美股（含 ETF） | 1   | 60档 | /   |
| 美股期权 | 1   | /   | /   |
| 美股期货 | /   | 40档 | /   |
| A 股 | 5   | /   | /   |

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#311)
 Q12：为什么我购买激活了行情卡之后，OpenD 仍然没有行情权限？
--------------------------------------------------------------------------------------------------

A:

1.  由于 Futu API 的行情权限跟 APP 的行情权限不完全一样，部分行情卡仅适用于 APP 端（例如：Futu API美股行情卡需单独购买）。请先确认您所购买的行情卡是否是 OpenD 适用的。  
    我们已将 Futu API 适用的 **所有** 行情卡列在《权限与限制》一节，请点击 [这里](https://openapi.futunn.com/futu-api-doc/intro/authority.html#2867)
     查看。
2.  行情卡购买激活成功后，是立即生效的。请 **重新启动 OpenD** 后，再次查看权限状态。

A:

1.  由于 Moomoo API 的行情权限跟 APP 的行情权限不完全一样，部分行情卡仅适用于 APP 端（例如：Moomoo API美股行情卡需单独购买）。请先确认您所购买的行情卡是否是 OpenD 适用的。  
    我们已将 Moomoo API 适用的 **所有** 行情卡列在《权限与限制》一节，请点击 [这里](https://openapi.futunn.com/futu-api-doc/intro/authority.html#2867)
     查看。
2.  行情卡购买激活成功后，是立即生效的。请 **重新启动 OpenD** 后，再次查看权限状态。

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)
 Q13：如何通过订阅接口获取实时行情？
------------------------------------------------------------------------------------

**第一步：订阅**

将标的的代码和数据类型传入 [订阅接口](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
，完成订阅。

订阅接口支持了实时报价、实时摆盘、实时逐笔、实时分时、实时 K 线、实时经纪队列数据的获取。订阅成功后，OpenD 会持续收到富途服务器的实时数据推送。

注意：订阅额度会根据您的总资产、交易笔数和交易量，来进行分配，具体规则参见 [订阅额度 & 历史 K 线额度](https://openapi.futunn.com/futu-api-doc/intro/authority.html#1314)
。所以，如果您的订阅额度不足，可以先检查一下是否有无用的订阅在占用额度，及时 [反订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
 即可释放已占用的订阅额度。

**第二步：取数据**

如何将订阅推送的数据从 OpenD 取回脚本呢？我们提供了如下两种方式：

**方式 1：实时数据回调**  
设置相应的回调函数，来异步处理 OpenD 收到的数据推送。

设置好回调函数后，OpenD 会将收到的实时数据，立即推给脚本的回调函数进行处理。

如果所订阅的标的比较活跃，此时的推送数据可能数据量较大且频率较高。如果您希望适当降低 OpenD 给脚本的推送频率，建议在 [OpenD 启动参数](https://openapi.futunn.com/futu-api-doc/opend/opend-cmd.html#8799)
 中配置 API 推送频率（`qot_push_frequency`）。

方式 1 涉及的接口包括：[实时报价回调](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html)
、[实时摆盘回调](https://openapi.futunn.com/futu-api-doc/quote/update-order-book.html)
、[实时 K 线回调](https://openapi.futunn.com/futu-api-doc/quote/update-kl.html)
、[实时分时回调](https://openapi.futunn.com/futu-api-doc/quote/update-rt.html)
、[实时逐笔回调](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html)
、[实时经纪队列回调](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html)
。

**方式 2：获取实时数据**  
通过获取实时数据接口，可以将 OpenD 收到的最新的数据，取回脚本。这种方式更加灵活，脚本不需要处理海量的推送。只要 OpenD 在持续接收富途服务器的推送，脚本可以随用随取，不用不取。

由于是从 OpenD 接收的推送数据中取，所以这类接口没有频率限制。

方式 2 涉及的接口包括：[获取实时报价](https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html)
、[获取实时摆盘](https://openapi.futunn.com/futu-api-doc/quote/get-order-book.html)
、[获取实时 K 线](https://openapi.futunn.com/futu-api-doc/quote/get-kl.html)
、[获取实时分时](https://openapi.futunn.com/futu-api-doc/quote/get-rt.html)
、[获取实时逐笔](https://openapi.futunn.com/futu-api-doc/quote/get-ticker.html)
、[获取实时经纪队列](https://openapi.futunn.com/futu-api-doc/quote/get-broker.html)
。

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2090)
 Q14：各个市场状态对应什么时间段？
-----------------------------------------------------------------------------------

A:

| 市场  | 品类  | 市场状态 | 时间段（当地时间） |
| --- | --- | --- | --- |
| 香港市场 | 证券类产品（含股票、ETFs、窝轮、牛熊、界内证） | \* NONE：无交易 | CST 08:55 - 09:00 |
| \* AUCTION：盘前竞价 | CST 09:00 - 09:20 |
| \* WAITING\_OPEN：等待开盘 | CST 09:20 - 09:30 |
| \* MORNING：早盘 | CST 09:30 - 12:00 |
| \* REST: 午间休市 | CST 12:00 - 13:00 |
| \* AFTERNOON：午盘 | CST 13:00 - 16:00 |
| \* HK\_CAS：港股盘后竞价（港股市场增加 CAS 机制对应的市场状态） | CST 16:00 - 16:08 |
| \* CLOSED：收盘 | CST 16:08 - 08:55（T+1） |
| 期权、期货（仅日市） | \* NONE：期权待开盘 | CST 08:55 - 09:30 |
| \* MORNING：早盘 | CST 09:30 - 12:00 |
| \* REST: 午间休市 | CST 12:00 - 13:00 |
| \* AFTERNOON：午盘 | CST 13:00 - 16:00 |
| \* CLOSED：收盘 | CST 16:00 - 08:55（T+1） |
| 期货（日夜市） | \* FUTURE\_DAY\_WAIT\_FOR\_OPEN：期货待开盘 | 不同品种交易时间不同 |
| \* NIGHT\_OPEN: 夜市交易时段 |
| \* NIGHT\_END：夜市收盘 |
| \* FUTURE\_DAY\_WAIT\_FOR\_OPEN：期货待开盘 |
| \* FUTURE\_DAY\_OPEN：日市交易时段 |
| \* FUTURE\_DAY\_CLOSE：日市收盘 |
| 美国市场 | 证券类产品（含股票、ETFs） | \* PRE\_MARKET\_BEGIN：美股盘前交易时段 | EST 04:00 - 09:30 |
| \* AFTERNOON：美股持续交易时段 | EST 09:30 - 16:00 |
| \* AFTER\_HOURS\_BEGIN：美股盘后交易时段 | EST 16:00 - 20:00 |
| \* AFTER\_HOURS\_END：美股盘后收盘 | EST 20:00 - 04:00（T+1） |
| \* OVERNIGHT：美股夜盘交易时段 | EST 20:00 - 04:00（T+1） |
| 期权  | \* NONE：期权待开盘 | 不同品种交易时间不同 |
| \* REST：美指期权午间休市 |
| \* AFTERNOON：美股持续交易时段 |
| \* TRADE\_AT\_LAST：美指期权盘尾交易时段 |
| \* NIGHT：美指期权夜市交易时段 |
| \* CLOSED：收盘 |
| 期货  | \* FUTURE\_SWITCH\_DATE：美期待开盘 | 不同品种交易时间不同 |
| \* FUTURE\_OPEN：美期交易时段 |
| \* FUTURE\_BREAK：美期中盘休息 |
| \* FUTRUE\_BREAK\_OVER：美期休息后交易时段 |
| \* FUTURE\_CLOSE：美期收盘 |
| A股市场 | 证券类产品（含股票、ETFs） | \* NONE：无交易 | CST 08:55 - 09:15 |
| \* Auction：盘前竞价 | CST 09:15 - 09:25 |
| \* WAITING\_OPEN：等待开盘 | CST 09:25 - 09:30 |
| \* MORNING：早盘 | CST 09:30 - 11:30 |
| \* REST：午间休市 | CST 11:30 - 13:00 |
| \* AFTERNOON：午盘 | CST 13:00 - 15:00 |
| \* CLOSED：收盘 | CST 15:00 - 08:55（T+1） |
| 新加坡市场 | 期货  | \* FUTURE\_DAY\_WAIT\_FOR\_OPEN：期货待开盘 | 不同品种交易时间不同 |
| \* NIGHT\_OPEN：夜市交易时段 |
| \* NIGHT\_END：夜市收盘 |
| \* FUTURE\_DAY\_OPEN：日市交易时段 |
| \* FUTURE\_DAY\_CLOSE：日市收盘 |
| 日本市场 | 期货  | \* FUTURE\_DAY\_WAIT\_FOR\_OPEN：期货待开盘 | JST 16:25（T-1）- 16:30（T-1） |
| \* NIGHT\_OPEN：夜市交易时段 | JST 16:30（T-1） - 05:30 |
| \* NIGHT\_END：夜市收盘 | JST 05:30 - 08:45 |
| \* FUTURE\_DAY\_OPEN：日市交易时段 | JST 08:45 - 15:15 |
| \* FUTURE\_DAY\_CLOSE：日市收盘 | JST 15:15 - 16:25 |

\* CST, EST, JST 分别表示中国时间，美东时间，日本时间

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#6241)
 Q15：接口参数股票代码的格式
--------------------------------------------------------------------------------

A：

*   使用不同编程语言的用户，需要的股票代码的格式不同：
    
    *   **Python 用户**  
        股票代码 code 格式：`行情市场.代码`。  
        例如：腾讯控股，参数 code 传入'HK.00700'。
        
    *   **非 Python 用户**  
        股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
        。  
        例如：腾讯控股，参数 market 传入 QotMarket\_HK\_Security，参数 code 传入'00700'。
        
*   查询方式：  
    通过 APP 查看代码和行情市场：行情 > 自选 > 全部。  
    行情市场定义，请参考 [这里](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
    。  
    ![code](https://openapi.futunn.com/futu-api-doc/assets/img/code.cee27ed6.png)
    

[#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#4226)
 Q16：复权因子相关
---------------------------------------------------------------------------

A：

### [#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#8831)
 概述

所谓 [复权](https://openapi.futunn.com/futu-api-doc/quote/get-rehab.html#770)
 就是对股价和成交量进行权息修复，按照股票的实际涨跌绘制股价走势图，并把成交量调整为相同的股本口径。  
公司行动（如：拆股、合股、送股、转增股、配股、增发股、分红）均可能对股价产生影响，而复权计算可对量价进行调整，剔除公司行动的影响，保持股价走势的连续性。

### [#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#6434)
 名词解释

*   公司行动：上市公司进行一些股权、股票等影响公司股价和股东持仓变化的行为。
*   前复权：保持现有的股价不变，以当前的股价为基准，对以前的股价进行复权计算。
*   后复权：保持先前的股价不变，以过去的股价为基准，对以后的股价进行复权计算。
*   复权因子：即权息修复比例，用于计算复权后的价格及持仓数量。
*   除权除息日：即股权登记日下一个交易日。在股票的除权除息日，证券交易所都要计算出股票的除权除息价，以作为股民在除权除息日开盘的参考。其意义是股票股利分配给股东的日期。

### [#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#6836)
 复权方法

主流的复权计算方法分为两种：事件法和连乘法；而 Futu API 针对不同市场使用不同的计算方法。

主流的复权计算方法分为两种：事件法和连乘法；而 Moomoo API 针对不同市场使用不同的计算方法。

\- 事件复权法：通过还原除权除息的各类事件进行复权；存在两个复权因子（复权因子 A 和 复权因子 B），复权因子 B 主要调整现金分红对股价的影响，而复权因子 A 调整其他公司行动对股价的影响。 - 连乘复权法：通过复权因子连乘的方式进行复权，只保留 复权因子 A（或将 复权因子 B 置为0），复权因子 A 为 除权除息日前收盘价/该日经权息调整后的前收盘价。

提示

*   API 对美股前复权使用连乘法，即将 复权因子 B 置为0。
*   API 对除美股以外的标的（A股、港股、新加坡股票等）及美股后复权使用事件法。

### [#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#4211)
 计算公式

#### [#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#9449)
 单次复权

*   前复权：  
    前复权价格 = 不复权价格 × 前复权因子 A + 前复权因子 B
*   后复权：  
    后复权价格 = 不复权价格 × 后复权因子 A + 后复权因子 B

#### [#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#1077)
 多次复权

*   前复权：按照时间顺序，筛选出大于计算日期的复权因子，优先使用时间较早的复权因子进行复权计算。以两次复权为例：
    
    ![code](https://openapi.futunn.com/futu-api-doc/assets/img/forward_fomula.9794c20c.png)
    
*   后复权：按照时间倒序，筛选出小于等于计算日期的复权因子，优先使用时间较晚的复权因子进行复权计算。以两次复权为例：
    
    ![code](https://openapi.futunn.com/futu-api-doc/assets/img/backward_fomula.3b5077da.png)
    

### [#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#4239)
 示例

#### [#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#4652)
 单次前复权示例

以牧原股份为例：

*   筛选复权因子如下：

| 除权除息日 | 股票代码 | 方案说明 | 前复权因子 A | 前复权因子 B |
| --- | --- | --- | --- | --- |
| 2021/06/03 | SZ.002714 | 10转4.0股派14.61元（含税） | 0.71429 | \-1.04357 |

*   不复权数据如下：

| 日期  | 股票代码 | 不复权收盘价 |
| --- | --- | --- |
| 2021/06/02 | SZ.002714 | 93.11 |
| 2021/06/03 | SZ.002714 | 66.25 |

*   前复权数据如下：

| 日期  | 股票代码 | 前复权收盘价 |
| --- | --- | --- |
| 2021/06/02 | SZ.002714 | 65.4639719 |
| 2021/06/03 | SZ.002714 | 66.25 |

*   前复权数据计算方法：  
    牧原股份在 2021/06/03 进行拆股及现金分红行动（10转4.0股派14.61元），根据前复权计算公式对 2021/06/02 的收盘价进行调整计算，则：前复权价格（65.4639719） = 不复权价格（93.11） × 前复权因子 A（0.71429） + 前复权因子 B（-1.04357）
    
    ![code](https://openapi.futunn.com/futu-api-doc/assets/img/forward_example.9bff79f5.png)
    

#### [#](https://openapi.futunn.com/futu-api-doc/qa/quote.html#7881)
 多次后复权示例

接上一个例子，计算牧原股份在 2021/06/02 的后复权价格：

*   筛选复权因子如下：

| 除权除息日 | 股票代码 | 方案说明 | 后复权因子 A | 后复权因子 B |
| --- | --- | --- | --- | --- |
| 2014/07/04 | SZ.002714 | 10派2.34元（含税） | 1   | 0.234 |
| 2015-06-10 | SZ.002714 | 10转10.0股派0.61元（含税） | 2   | 0.061 |
| 2016-07-08 | SZ.002714 | 10转10.0股派3.53元（含税） | 2   | 0.353 |
| 2017-07-11 | SZ.002714 | 10转8.0股派6.9元（含税） | 1.8 | 0.69 |
| 2018-07-03 | SZ.002714 | 10派6.91元（含税） | 1   | 0.691 |
| 2019-07-04 | SZ.002714 | 10派0.5元（含税） | 1   | 0.05 |
| 2020-06-04 | SZ.002714 | 10转7.0股派5.5元（含税） | 1.7 | 0.55 |

*   不复权数据如下：

| 日期  | 股票代码 | 不复权收盘价 |
| --- | --- | --- |
| 2021/06/02 | SZ.002714 | 93.11 |

*   后复权数据如下：

| 日期  | 股票代码 | 后复权收盘价 |
| --- | --- | --- |
| 2021/06/02 | SZ.002714 | 1152.7226 |

*   后复权数据计算方法：  
    为了计算牧原股份在 2021/06/02 的后复权价格，需要将早于 2021/06/02 的复权事件进行一一复权，得到最后的后复权价格，具体计算如下：
    
    ![code](https://openapi.futunn.com/futu-api-doc/assets/img/backward_example.fc9d3401.jpg)
    

← [OpenD 相关](https://openapi.futunn.com/futu-api-doc/qa/opend.html) [交易相关](https://openapi.futunn.com/futu-api-doc/qa/trade.html)
 →

[行情相关](https://openapi.futunn.com/futu-api-doc/qa/quote.html)

*   [Q1：订阅失败](https://openapi.futunn.com/futu-api-doc/qa/quote.html#8880)
    
*   [Q2：反订阅失败](https://openapi.futunn.com/futu-api-doc/qa/quote.html#3273)
    
*   [Q3：反订阅成功但没释放额度](https://openapi.futunn.com/futu-api-doc/qa/quote.html#9683)
    
*   [Q4：订阅不足一分钟关闭脚本连接，会释放额度吗？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#9319)
    
*   [Q5：请求限频的具体限制逻辑是怎样？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#3487)
    
*   [Q6：自选股添加不上是什么原因？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#1236)
    
*   [Q7：为什么 API 端的美股报价和牛牛显示端的全美综合报价有不同？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#5528)
    
*   [Q8：API 行情卡在哪里购买？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#887)
    
*   [Q9：为什么有时候，获取实时数据的 get 接口响应比较慢？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#7600)
    
*   [Q10：购买 API 美股 Nasdaq Basic 行情卡后，可以获取哪些数据？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2962)
    
*   [Q11：各个行情品类的摆盘支持多少档？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#5336)
    
*   [Q12：为什么我购买激活了行情卡之后，OpenD 仍然没有行情权限？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#311)
    
*   [Q13：如何通过订阅接口获取实时行情？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)
    
*   [Q14：各个市场状态对应什么时间段？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2090)
    
*   [Q15：接口参数股票代码的格式](https://openapi.futunn.com/futu-api-doc/qa/quote.html#6241)
    
*   [Q16：复权因子相关](https://openapi.futunn.com/futu-api-doc/qa/quote.html#4226)