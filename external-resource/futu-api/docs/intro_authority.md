# 权限和限制 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/intro/authority.html

[#](https://openapi.futunn.com/futu-api-doc/intro/authority.html#1428)
 权限和限制
=============================================================================

[#](https://openapi.futunn.com/futu-api-doc/intro/authority.html#8164)
 登录限制
----------------------------------------------------------------------------

### [#](https://openapi.futunn.com/futu-api-doc/intro/authority.html#376)
 开户限制

首先，您需要先在富途牛牛 APP上，完成交易业务账户的开通，才能成功登录 Futu API。

首先，您需要先在moomoo APP上，完成交易业务账户的开通，才能成功登录 Moomoo API。

### [#](https://openapi.futunn.com/futu-api-doc/intro/authority.html#9929)
 合规确认

首次登录成功后，您需要完成问卷评估与协议确认，才能继续使用 Futu API。牛牛用户请 [点击这里](https://www.futunn.com/about/api-disclaimer)
 。

首次登录成功后，您需要完成问卷评估与协议确认，才能继续使用 Moomoo API。moomoo 用户请 [点击这里](https://www.moomoo.com/about/api-disclaimer)
 。

[#](https://openapi.futunn.com/futu-api-doc/intro/authority.html#6081)
 行情数据
----------------------------------------------------------------------------

行情数据的限制主要体现在以下几方面：

*   行情权限 —— 获取相关行情数据的权限
*   接口限频 —— 调用行情接口的频率限制
*   订阅额度 —— 同时订阅的实时行情的数量
*   历史 K 线额度 —— 每 30 天最多可拉取多少个标的的历史 K 线

### [#](https://openapi.futunn.com/futu-api-doc/intro/authority.html#2867)
 行情权限

通过 Futu API 获取行情数据，需要相应的行情权限，Futu API 的行情权限跟 APP 的行情权限不完全一样，不同的权限等级对应不同的时延、摆盘档数以及接口使用权限。

通过 Moomoo API 获取行情数据，需要相应的行情权限，Moomoo API 的行情权限跟 APP 的行情权限不完全一样，不同的权限等级对应不同的时延、摆盘档数以及接口使用权限。

部分品种行情，需要购买行情卡后方可获取，具体获取方式见下表。

| 市场  | 标的类别 | 获取方式 |
| --- | --- | --- |
| 香港市场 | 证券类产品（含股票、ETFs、窝轮、牛熊、界内证） | \* 中国内地IP客户：免费获取 LV2 行情。如需获得 SF 权限，请购买 [港股高级全盘行情](https://qtcard.futunn.com/intro/sf?type=10&is_support_buy=1&clientlang=0)<br>  <br>\* 港澳台及海外IP客户：免费获取 LV1 行情。如需获得 LV2 权限，请购买 [港股 LV2 高级行情](https://qtcard.futunn.com/intro/hklv2?type=1&is_support_buy=1&clientlang=0)<br> 。如需获得 SF 权限，请购买 [港股高级全盘行情](https://qtcard.futunn.com/intro/sf?type=10&is_support_buy=1&clientlang=0) |
| 指数  |
| 板块  |
| 期权  | \* 中国内地IP客户：推广期免费获取 LV2 行情  <br>\* 港澳台及海外IP客户：免费获取 LV1 行情，如需获得 LV2 权限，请购买 [港股期权期货 LV2 高级行情](https://qtcard.futunn.com/intro/hk-derivativeslv2?type=8&clientlang=0&is_support_buy=1) |
| 期货  |
| 美国市场 | 证券类产品（含纽交所、美交所、纳斯达克上市的股票、ETFs） | \* 与客户端行情权限不共用，如需获得 LV1 权限（基本报价，含夜盘），请购买 [Nasdaq Basic](https://qtcardfthk.futufin.com/intro/nasdaq-basic?type=12&is_support_buy=1&clientlang=0)<br> 。  <br>\* 与客户端行情权限不共用，如需获得 LV2 权限（基本报价+深度摆盘，含夜盘深度摆盘），请购买 [Nasdaq Basic+TotalView](https://qtcardfthk.futufin.com/intro/nasdaq-basic?type=18&is_support_buy=1&clientlang=0)<br> 。 |
| 板块  |
| OTC 股票 | 暂不支持获取 |
| 期权（含普通股票期权、指数期权） | \* 达到门槛<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>门槛要求为：总资产大于20000港元<br><br>的客户：免费获得 LV1 权限。  <br>\* 未达到门槛<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>门槛要求为：总资产大于20000港元<br><br>的客户：请购买 [OPRA 期权 LV1 实时行情](https://qtcardfthk.futufin.com/intro/api-usoption-realtime?type=16&is_support_buy=1&clientlang=0)<br> 获得 LV1 权限。 |
| 期货  | \* 已开通期货账户<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   富途证券(香港)/moomoo证券(新加坡) 支持开通期货账户<br>*   moomoo证券(美国) 暂不支持<br><br>的客户：  <br>如需获取 CME Group 行情<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>包含 CME, CBOT, NYMEX, COMEX 行情<br><br>，请购买 [CME Group 期货 LV2](https://qtcardfthk.futufin.com/intro/cme?type=30&clientlang=0&is_support_buy=1)<br>  <br>如需获取 CME 行情，请购买 [CME 期货 LV2](https://qtcardfthk.futufin.com/intro/cme?type=31&clientlang=0&is_support_buy=1)<br>  <br>如需获取 CBOT 行情，请购买 [CBOT 期货 LV2](https://qtcardfthk.futufin.com/intro/cme?type=32&clientlang=0&is_support_buy=1)<br>  <br>如需获取 NYMEX 行情，请购买 [NYMEX 期货 LV2](https://qtcardfthk.futufin.com/intro/cme?type=33&clientlang=0&is_support_buy=1)<br>  <br>如需获取 COMEX 行情，请购买 [COMEX 期货 LV2](https://qtcardfthk.futufin.com/intro/cme?type=34&clientlang=0&is_support_buy=1)<br>  <br>  <br>\* 未开通期货账户的客户：不支持获取 |
| 指数  | 暂不支持获取 |
| A 股市场 | 证券类产品（含股票、ETFs） | \* 中国内地 IP 个人客户：免费获取 LV1 行情  <br>\* 港澳台及海外IP客户/机构客户：暂不支持 |
| 指数  |
| 板块  |
| 新加坡市场 | 期货  | 暂不支持获取 |
| 日本市场 | 期货  | 暂不支持获取 |

| 市场  | 标的类别 | 获取方式 |
| --- | --- | --- |
| 香港市场 | 证券类产品（含股票、ETFs、窝轮、牛熊、界内证） | \* 中国内地IP客户：免费获取 LV2 行情。暂不支持获取 SF 权限。  <br>\* 港澳台及海外IP客户：免费获取 LV1 行情。如需获得 LV2 权限，请购买 [港股 LV2 高级行情](https://qtcard.moomoo.com/intro/hklv2?type=1&clientlang=0&is_support_buy=1)<br> 。暂不支持获取 SF 权限。 |
| 指数  |
| 板块  |
| 期权  | \* 中国内地IP客户：推广期免费获取 LV2 行情。  <br>\* 港澳台及海外IP客户：免费获取 LV1 行情，如需获得 LV2 权限，请购买 [港股 LV2 + 期权期货 LV2 行情](https://qtcard.moomoo.com/intro/hklv2-derivativeslv2?type=9&clientlang=0&is_support_buy=1)<br> 。 |
| 期货  |
| 美国市场 | 证券类产品（含纽交所、美交所、纳斯达克上市的股票、ETFs） | \* 与客户端行情权限不共用，如需获得 LV1 权限（基本报价，含夜盘），请购买 [Nasdaq Basic](https://qtcard.moomoo.com/intro/nasdaq-basic?is_support_buy=1&type=12&goods_type=1022&clientlang=0)<br> 。  <br>\* 与客户端行情权限不共用，如需获得 LV2 权限（基本报价+深度摆盘，含夜盘深度摆盘），请购买 [Nasdaq Basic+TotalView](https://qtcard.moomoo.com/intro/nasdaq-basic?is_support_buy=1&type=16&goods_type=1026&clientlang=0)<br> 。 |
| 板块  |
| OTC 股票 | 暂不支持获取 |
| 期权（含普通股票期权、指数期权） | \* 达到门槛<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>门槛要求为：<br><br>*   港美股总资产大于0<br>*   港美股有过交易<br><br>的客户：免费获得 LV1 权限。  <br>\* 未达到门槛<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>门槛要求为：<br><br>*   港美股总资产大于3000美金<br>*   港美股有过交易<br><br>的客户：请购买 [OPRA 期权 LV1 实时行情](https://qtcard.moomoo.com/intro/api-usoption-realtime?goods_type=1024&type=15&is_support_buy=1&clientlang=0)<br> 获得 LV1 权限。 |
| 期货  | \* 已开通期货账户<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   富途证券(香港)/moomoo证券(新加坡)/moomoo证券(马来西亚) 支持开通期货账户<br>*   moomoo证券(美国)/moomoo证券(日本)/moomoo证券(加拿大)/moomoo证券(澳大利亚) 暂不支持<br><br>的客户：  <br>如需获取 CME Group 行情<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>包含 CME, CBOT, NYMEX, COMEX 行情<br><br>，请购买 [CME Group 期货 LV2](https://qtcard.moomoo.com/intro/cme?type=25&goods_type=1044&is_support_buy=1)<br>  <br>如需获取 CME 行情，请购买 [CME 期货 LV2](https://qtcard.moomoo.com/intro/cme?type=26&goods_type=1046&is_support_buy=1)<br>  <br>如需获取 CBOT 行情，请购买 [CBOT 期货 LV2](https://qtcard.moomoo.com/intro/cme?type=27&goods_type=1048&is_support_buy=1)<br>  <br>如需获取 NYMEX 行情，请购买 [NYMEX 期货 LV2](https://qtcard.moomoo.com/intro/cme?type=28&goods_type=1050&is_support_buy=1)<br>  <br>如需获取 COMEX 行情，请购买 [COMEX 期货 LV2](https://qtcard.moomoo.com/intro/cme?type=29&goods_type=1052&is_support_buy=1)<br>  <br>  <br>\* 未开通期货账户的客户：不支持获取 |
| 指数  | 暂不支持获取 |
| A 股市场 | 证券类产品（含股票、ETFs） | \* 中国内地 IP 个人客户：免费获取 LV1 行情。  <br>\* 港澳台及海外IP客户/机构客户：暂不支持。 |
| 指数  |
| 板块  |

提示

上述表格，中国内地IP客户和港澳台及海外IP客户，以 OpenD 登录的 IP 地址作为区分依据。

上述表格，中国内地IP客户和港澳台及海外IP客户，以 OpenD 登录的 IP 地址作为区分依据。

### [#](https://openapi.futunn.com/futu-api-doc/intro/authority.html#8908)
 接口限频

为保护服务器，防止恶意攻击，所有需要向富途服务器发送请求的接口，都会有频率限制。  
每个接口的限频规则会有不同，具体请参见每个接口页面下面的 `接口限制`。

为保护服务器，防止恶意攻击，所有需要向 moomoo 服务器发送请求的接口，都会有频率限制。  
每个接口的限频规则会有不同，具体请参见每个接口页面下面的 `接口限制`。

举例：  
[快照](https://openapi.futunn.com/futu-api-doc/quote/get-market-snapshot.html)
 接口的限频规则是：每 30 秒内最多请求 60 次快照。您可以每隔 0.5 秒请求一次匀速请求，也可以快速请求 60 次后，休息 30 秒，再请求下一轮。如果超出限频规则，接口会返回错误。

### [#](https://openapi.futunn.com/futu-api-doc/intro/authority.html#1314)
 订阅额度 & 历史 K 线额度

订阅额度和历史 K 线额度限制如下：

| 用户类型 | 订阅额度 | 历史 K 线额度 |
| --- | --- | --- |
| 开户用户 | 100 | 100 |
| 总资产达 1 万 HKD | 300 | 300 |
| 以下三条满足任意一条即可：  <br>1\. 总资产达 50 万 HKD；  <br>2\. 月交易笔数 > 200；  <br>3\. 月交易额 > 200 万 HKD | 1000 | 1000 |
| 以下三条满足任意一条即可：  <br>1\. 总资产达 500 万 HKD；  <br>2\. 月交易笔数 > 2000；  <br>3\. 月交易额 > 2000 万 HKD | 2000 | 2000 |

**1、总资产**  
总资产，是指您在富途证券的所有资产，包括：港、美、A 股证券账户，期货账户，基金资产以及债券资产，按照即时汇率换算成以港元为单位。

**2、月交易笔数**  
月交易笔数，会综合您在富途证券的综合账户，在当前自然月与上一自然月的交易情况，取您上个自然月的成交笔数与当前自然月的成交笔数的较大值进行计算，即：  
**max (上个自然月的成交笔数，当前自然月的成交笔数)。**

**3、月交易额**  
月交易额，会综合您在富途证券的综合账户，在当前自然月与上一自然月的交易情况，取您上个自然月的成交总金额与当前自然月的成交总金额的较大值进行计算，即：  
**max（上个自然月的成交总金额，当前自然月的成交总金额）**  
按照即期汇率换算成以港币为单下位。其中，期货交易额的计算，需要乘以相应的调整系数（默认取 0.1），期货交易额计算公式如：  
**期货交易额=∑（单笔成交数 \* 成交价 \* 合约乘数 \* 汇率 \* 调整系数）**

**1、总资产**  
总资产，是指您在 moomoo 证券的所有资产，包括：港、美、A 股证券账户，期货账户，基金资产以及债券资产，按照即时汇率换算成以港元为单位。

**2、月交易笔数**  
月交易笔数，会综合您在 moomoo 证券的综合账户，在当前自然月与上一自然月的交易情况，取您上个自然月的成交笔数与当前自然月的成交笔数的较大值进行计算，即：  
**max (上个自然月的成交笔数，当前自然月的成交笔数)。**

**3、月交易额**  
月交易额，会综合您在 moomoo 证券的综合账户，在当前自然月与上一自然月的交易情况，取您上个自然月的成交总金额与当前自然月的成交总金额的较大值进行计算，即：  
**max（上个自然月的成交总金额，当前自然月的成交总金额）**  
按照即期汇率换算成以港币为单位。其中，期货交易额的计算，需要乘以相应的调整系数（默认取 0.1），期货交易额计算公式如下：  
**期货交易额=∑（单笔成交数 \* 成交价 \* 合约乘数 \* 汇率 \* 调整系数）**

**4、订阅额度**  
订阅额度，适用于 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
 接口。每只股票订阅一个类型即占用 1 个订阅额度，取消订阅会释放已占用的额度。 举例：  
假设您的订阅额度是 100。 当您同时订阅了 HK.00700 的实时摆盘、US.AAPL 的实时逐笔、SH.600519 的实时报价时，此时订阅额度会占用 3 个，剩余的订阅额度为 97。 这时，如果您取消了 HK.00700 的实时摆盘订阅，您的订阅额度占用将变成 2 个，剩余订阅额度会变成 98。

**5、历史 K 线额度**  
历史 K 线额度，适用于 [获取历史 K 线](https://openapi.futunn.com/futu-api-doc/quote/request-history-kline.html)
 接口。最近 30 天内，每请求 1 只股票的历史 K 线，将会占用 1 个历史 K 线额度。最近 30 天内重复请求同一只股票的历史 K 线，不会重复累计。 同时，订阅同一股票的不同周期的K线只占用1个额度，不会重复累计。 举例：  
假设您的历史 K 线额度是 100，今天是 2020 年 7 月 5 日。 您在 2020 年 6 月 5 日~2020 年 7 月 5 日之间，共计请求了 60 只股票的历史 K 线，则剩余的历史 K 线额度为 40。

提示

*   订阅额度和历史 K 线额度为系统自动分配，不需要手动申请。
*   新入金的账户，额度等级会在 2 小时内自动生效。
*   在途资产
    
    ![](https://openapi.futunn.com/futu-api-doc/img/tip.png)
    
    参与港股新股认购、供股可能会产生在途资产
    
    不会用于额度计算。

[#](https://openapi.futunn.com/futu-api-doc/intro/authority.html#1396)
 交易功能
----------------------------------------------------------------------------

*   进行指定市场的交易时，需要先确认是否已开通该市场的交易业务账户。  
    举例：您只能在美股交易业务账户下进行美股交易，无法在港股交易业务账户下进行美股交易。

← [介绍](https://openapi.futunn.com/futu-api-doc/intro/intro.html) [费用](https://openapi.futunn.com/futu-api-doc/intro/fee.html)
 →