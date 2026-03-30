# 证券

获取市场状态

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E5%B8%82%E5%9C%BA%E7%8A%B6%E6%80%81)

---------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteMarketRequest**

**说明**

获取指定市场的交易状态（如开盘、盘前交易、盘后交易等），并获取该市场的最近开盘时间。

**参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| market | Market | Yes | 常用市场枚举值：US 美股，HK港股，CN A股，ALL 所有，枚举值详见： [市场枚举](./appendix-enum-java.md#market) |
| lang | Language | No  | 语言枚举值：zh\_CN，zh\_TW，en\_US，默认：en\_US，枚举值详见：[语言枚举](./appendix-enum-java.md#lang) |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteMarketResponse` [source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteMarketResponse.java)

其结构如下:

Java

    public class QuoteMarketResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<MarketItem> marketItems;
    }

返回数据可用`QuoteMarketResponse.getMarketItems()`方法访问，返回为`marketItem`对象列表，其中 `com.tigerbrokers.stock.openapi.client.https.domain.quote.item.MarketItem` 属性如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| market | string | 市场代码（US:美股，HK:港股，CN:沪深） |
| marketStatus | string | 市场状态描述，非固定值，包括节假日信息等，如："Not Yet Opened"，"Closed"，"Trading"，"Closed Independence Day"，通过 lang 参数区分中英文文案，该字段可以用来做市场状态的提醒展示。 |
| status | string | 市场状态（NOT\_YET\_OPEN：未开盘，PRE\_HOUR\_TRADING：盘前交易，TRADING：交易中，MIDDLE\_CLOSE：午间休市，POST\_HOUR\_TRADING：盘后交易，CLOSING：已收盘，OVERNIGHT\_TRADING：夜盘, EARLY\_CLOSED：提前休市，MARKET\_CLOSED：休市） |
| openTime | string | 最近开盘、交易时间 MM-dd HH:mm:ss ，例如：当 market=US 时，openTime="07-04 09:30:00 EDT"，其中EDT为美东时区。当 market 为 CN，HK 时，openTime="01-13 09:30:00"，openTime 不会返回时区。当 market 为其他市场时，会返回对应市场的时区。 |

`MarketItem`的具体字段可通过对象的 get 方法，如`getMarket()`进行访问，或通过`toString()`方法转换为字符串的形式。

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    QuoteMarketResponse response = client.execute(QuoteMarketRequest.newRequest(Market.US));
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getMarketItems().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code": 0,
      "message": "success",
      "timestamp": 1525938835697,
      "data": [\
        {\
          "market": "US",\
          "marketStatus": "Closed Independence Day",\
          "status": "CLOSING",\
          "openTime": "07-04 09:30:00 EDT"\
        }\
      ]
    }

* * *

获取市场交易日历

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E5%B8%82%E5%9C%BA%E4%BA%A4%E6%98%93%E6%97%A5%E5%8E%86)

-----------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteTradeCalendarRequest**

**说明**

提供自 2015 年以来至本年度末的市场交易日历（排除周末及该市场法定节假日，但未排除临时休市日期）。如所选时间超出可提供范围，起止时间将自动调整至可用数据范围内。

**参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| market | string | Yes | US 美股，HK港股,CN A股 |
| begin\_date | string | No  | 日历起始日期（包含）。yyyy-MM-dd 格式，如 '2022-06-01' |
| end\_date | string | No  | 日历截至日期（不包含）。yyyy-MM-dd 格式 |

begin\_time 和 end\_time 传参处理如下:

| begin\_time是否传递 | end\_time是否传递 | 说明  |
| --- | --- | --- |
| Yes | es  | begin\_time和end\_time分别为所传值 |
| Yes | No  | end\_time为begin\_time往后365天 |
| No  | Yes | begin\_time为end\_time往前365天 |
| No  | No  | begin\_time为当前日期，end\_time为begin\_time往后30天 |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeCalendarResponse` [source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteTradeCalendarResponse.java)

其结构如下:

Java

    public class QuoteTradeCalendarResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<TradeCalendar> items;
    }

返回数据可用`QuoteTradeCalendarResponse.getItems()`方法访问，返回为`TradeCalendar`对象列表，其中 `com.tigerbrokers.stock.openapi.client.https.domain.quote.item.TradeCalendar` 属性如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| date | string | 交易日日期 |
| type | string | 交易日类型：TRADING为正常交易日，全天交易; EARLY\_CLOSE为提前休市 |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    QuoteTradeCalendarRequest request = QuoteTradeCalendarRequest.newRequest(
            Market.US, "2022-06-01", "2022-06-30");
    QuoteTradeCalendarResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getItems().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code": 0,
      "message": "success",
      "timestamp": 1655968809209,
      "data": [\
        {\
          "date": "2022-06-01",\
          "type": "TRADING"\
        },\
        {\
          "date": "2022-06-02",\
          "type": "TRADING"\
        },\
        {\
          "date": "2022-06-03",\
          "type": "TRADING"\
        },\
        {\
          "date": "2022-06-06",\
          "type": "TRADING"\
        },\
        {\
          "date": "2022-06-07",\
          "type": "TRADING"\
        },\
        {\
          "date": "2022-06-08",\
          "type": "TRADING"\
        }\
      ]
    }

* * *

  

获取股票代码列表

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E4%BB%A3%E7%A0%81%E5%88%97%E8%A1%A8)

-----------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteSymbolRequest**

**说明**

获取指定市场所有证券的代码列表，包含已退市或暂不可交易的证券，以及指数代码。

**参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| market | string | Yes | US 美股，HK港股,CN A股 |
| include\_otc | boolean | No  | 是否包含OTC标的，默认：false |
| lang | string | No  | 语言支持: zh\_CN，zh\_TW，en\_US；默认：en\_US |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolResponse` [source](https://github.com/tigerfintech/openapi-java-sdk/blob/71631121961002aa1dadf601e922a494d14f5ed0/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteSymbolResponse.java)

具体结构如下

Java

    public class QuoteSymbolResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<String> symbols;
    }

返回数据可以通过`QuoteSymbolResponse.getSymbols()`方法调用，结果为包含返回股票代码数据的List

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    QuoteSymbolResponse response = client.execute(QuoteSymbolRequest.newRequest(Market.US).includeOTC(false));
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getSymbols().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code": 0,
      "message": "success",
      "timestamp": 1525938835697,
      "data": ["A", "A.W", "AA", "AA-B", "AAAP", "AABA", "AAC", "AADR", "AAIT", "AAL", "AALCP", "AAMC", "AAME"]
    }

* * *

获取股票代码和名称列表

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E4%BB%A3%E7%A0%81%E5%92%8C%E5%90%8D%E7%A7%B0%E5%88%97%E8%A1%A8)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteSymbolNameRequest**

**说明**

获取指定市场所有证券的代码及对应名称。

**参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| market | string | Yes | US 美股，HK港股, CN A股 |
| include\_otc | boolean | No  | 是否包含OTC标的，默认：false |
| lang | string | No  | 语言支持: zh\_CN，zh\_TW，en\_US；默认：en\_US |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolNameResponse` [source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteSymbolNameResponse.java)

具体结构如下

Java

    public class QuoteSymbolNameResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<SymbolNameItem> symbolNameItems;
    }

返回数据可通过`QuoteSymbolNameResponse.getSymbolNameItems()`方法访问，返回为包含`SymbolNameItem`对象的List，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.symbolNameItems` 属性如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| name | string | 股票名称 |
| symbol | string | 股票代码 |

`SymbolNameItem`的具体字段可通过对象的get方法，如`getName()`进行访问，或通过`toString()`方法转换为字符串的形式

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    QuoteSymbolNameResponse response = client.execute(QuoteSymbolNameRequest.newRequest(Market.US).includeOTC(false));
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getSymbolNameItems().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code": 0,
      "message": "success",
      "timestamp": 1525938835697,
      "data": [{"name":"CKH Holdings","symbol":"00001"},{"name":"CLP","symbol":"00002"}]
    }

* * *

获取实时行情

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E5%AE%9E%E6%97%B6%E8%A1%8C%E6%83%85)

---------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteRealTimeQuoteRequest**

**说明**

获取股票实时行情。使用该接口前需购买相应行情**权限**，每次请求最多支持 50 只股票。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | array | Yes | 股票代码列表（单次上限50） |
| includeHourTrading | Boolean | No  | 是否包含美股盘前盘后数据，默认：false |
| lang | string | No  | 语言支持: zh\_CN，zh\_TW，en\_US；默认：en\_US |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteTradeCalendarResponse.java)

返回数据可通过`QuoteRealTimeQuoteResponse.getRealTimeQuoteItems()`方法访问，返回`RealTimeQuoteItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.RealTimeQuoteItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| open | double | 开盘价 |
| high | double | 最高价 |
| low | double | 最低价 |
| close | double | 收盘价 |
| preClose | double | 前一交易日收盘价 |
| latestPrice | double | 最新价 |
| latestTime | long | 最新成交时间 |
| askPrice | double | 卖盘价 |
| askSize | long | 卖盘数量 |
| bidPrice | double | 买盘价 |
| bidSize | long | 买盘数量 |
| volume | long | 成交量 |
| status | string | 交易状态 |
| change | double | 涨跌额 |
| changeRate | double | 涨跌幅 |
| amplitude | double | 振幅  |
| hourTrading | object | 美股盘前盘后数据（includeHourTrading参数为true，且盘前盘后时间段才有数据） |

其中`hourTrading`对象包含的字段：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| tag | string | 盘前("Pre-Mkt")、盘后("Post-Mkt")标识 |
| latestPrice | double | 最新价 |
| preClose | double | 昨收价 |
| latestTime | string | 最新成交时间(美东时间) |
| volume | long | 成交量 |
| change | double | 涨跌额 |
| changeRate | double | 涨跌幅 |
| amplitude | double | 振幅  |
| timestamp | long | 最新成交时间 |

具体字段可通过对象的get方法，如`getTag()`，进行访问

status(交易状态) 取值:

*   "UNKNOWN": 未知
*   "NORMAL": 正常
*   "HALTED": 停牌
*   "DELIST": 退市
*   "NEW": 新股
*   "ALTER": 变更
*   "CIRCUIT\_BREAKER": 熔断
*   "ST": 特别处理

**示例**

Java

    QuoteRealTimeQuoteResponse response = client.execute(QuoteRealTimeQuoteRequest.newRequest(List.of("AAPL"), true));
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getRealTimeQuoteItems().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code" : 0,
      "message" : "success",
      "timestamp" : 1769062464074,
      "realTimeQuoteItems" : [ {\
        "lang" : null,\
        "symbol" : "AAPL",\
        "open" : 248.7,\
        "high" : 251.56,\
        "low" : 245.18,\
        "close" : 247.65,\
        "preClose" : 246.7,\
        "latestPrice" : 247.65,\
        "latestTime" : 1769029200000,\
        "askPrice" : 0.0,\
        "askSize" : 0,\
        "bidPrice" : 0.0,\
        "bidSize" : 0,\
        "volume" : 54641725,\
        "status" : "NORMAL",\
        "change" : 0.95,\
        "changeRate" : 0.003850830968788071,\
        "amplitude" : 0.025861,\
        "hourTrading" : {\
          "tag" : "Post-Mkt",\
          "latestPrice" : 248.7,\
          "preClose" : 247.65,\
          "latestTime" : "19:59 EST",\
          "volume" : 4273405,\
          "change" : 1.05,\
          "changeRate" : 0.00424,\
          "amplitude" : 0.007511,\
          "timestamp" : 1769043588222\
        },\
        "account" : null\
      } ],
      "success" : true
    }

* * *

获取深度行情数据

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E6%B7%B1%E5%BA%A6%E8%A1%8C%E6%83%85%E6%95%B0%E6%8D%AE)

-----------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteDepthRequest**

**说明**

获取指定证券的买卖 N 档挂单数据，包括委托价格、数量及订单数。每次请求最多支持 50 只证券。

> ⚠️
> 
> **CAUTION**
> 
> 港股：交易日收市竞价时间为 16:00-16:10，实际收市时间在 16:08 至 16:10 之间随机。当天最后一条深度行情数据通常在 16:10 之后的一到两分钟更新。
> 
> 美股：深度行情包含盘前和盘后交易信息，无需传入额外参数，实时请求即可获取。

**请求频率**

频率限制请参考：[请求频率限制](./ratelimit.md#/)

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | array | Yes | 股票代码列表（单次上限50） |
| market | string | Yes | US 美股，HK港股，CN A股，ALL 所有（参考Market枚举类） |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDepthResponse` [source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDepthResponse.java)

具体结构如下

Java

    public class QuoteDepthResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<QuoteDepthItem> quoteDepthItems;
    }

返回数据可通过`QuoteDepthResponse.getQuoteDepthItems()`方法访问，返回为包含`QuoteDepthItem`对象的List，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.QuoteDepthItem` 属性如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| asks | list | 卖盘分档 |
| bids | list | 买盘分档 |

其中买卖盘详细字段如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| price | double | 委托价 |
| volume | long | 委托量 |
| count | int | 委托订单数（只有港股市场有，美股市场没有值） |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    
    List<String> symbols = new ArrayList<>();
    symbols.add("DD");
    QuoteDepthResponse response = client.execute(QuoteDepthRequest.newRequest(symbols, Market.US.name()));
    if (response.isSuccess()) {
      for (QuoteDepthItem item : response.getQuoteDepthItems()) {
        System.out.println(item.getSymbol());
        System.out.println(Arrays.toString(item.getAsks().toArray()));
        System.out.println(Arrays.toString(item.getBids().toArray()));
      }
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code": 0,
      "message": "success",
      "timestamp": 1653064415298,
      "data": [\
        {\
          "symbol": "DD",\
          "asks": [\
            {\
              "price": 62.88,\
              "count": 0,\
              "volume": 50\
            },\
            {\
              "price": 62.88,\
              "count": 0,\
              "volume": 27\
            },\
            {\
              "price": 62.89,\
              "count": 0,\
              "volume": 50\
            },\
            {\
              "price": 62.89,\
              "count": 0,\
              "volume": 200\
            }\
            //...\
          ],\
          "bids": [\
            {\
              "price": 62.86,\
              "count": 0,\
              "volume": 50\
            },\
            {\
              "price": 62.86,\
              "count": 0,\
              "volume": 200\
            },\
            {\
              "price": 62.86,\
              "count": 0,\
              "volume": 2\
            },\
            {\
              "price": 62.86,\
              "count": 0,\
              "volume": 6\
            }\
            //...\
          ]\
        }\
      ]
    }

* * *

获取逐笔成交

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4)

---------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteTradeTickRequest**

**说明**

获取逐笔成交数据。该接口既支持收盘后查询当前交易日的全量逐笔记录，也支持盘中获取最新的实时逐笔数据。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | array | Yes | 股票代码列表,支持陆股通股、港股、美股 |
| trade\_session | String | No  | TradeSession 枚举对象，盘前：PreMarket，盘中：Regular，盘后：AfterHours。默认为盘中 |
| beginIndex | long | yes | 起始索引，当日索引值从 0 开始，如果 beginIndex 和 endIndex 设置为 -1，会返回最新的逐笔数据，**后续查询时，可以把上次查询返回的 endIndex 值当做beginIndex，从而进行连续查询**，返回数据为前闭后开集合，比如：beginIndex=1，endIndex=100，会返回从 1 到 99 的记录，下次查询时，设置beginIndex=100，endIndex=200 |
| endIndex | long | yes | 结束索引，**结束索引和起始索引的差值不能大于 2000，大于 2000 时默认返回从起始索引开始的 200 条记录**。当 limit 参数小于起始和结束索引的差值时，只会返回从起始索引开始的 limit 条逐笔数据 |
| limit | integer | No  | 单次请求返回的逐笔数量，如果起始和结束索引的间隔没有超过 2000，会按照实际条数返回，否则会默认为 200 |
| lang | string | No  | 语言支持：en\_US，zh\_CN，zh\_TW，默认: en\_US |

**beginIndex 和 endIndex参数使用说明**

| 查询方式 | beginIndex | endIndex | 描述  |
| --- | --- | --- | --- |
| 查询最新逐笔记录 | \-1 | \-1 | 默认返回limit条最新的逐笔记录。limit 默认为 200 |
| 根据区间查询当日逐笔 | 具体数值 | 具体数值 | 举例：beginIndex=10, endIndex=100 ，会返回包含 10 到 99 的 90 条记录。如果 limit 设置为 20，则会返回 10 到 29 的 20 条记录。 |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeTickResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteTradeTickResponse.java)

结构如下：

Java

    public class QuoteTradeTickResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<TradeTickItem> tradeTickItems;
    }

返回数据可通过`QuoteTradeTickResponse.getTradeTickItems()`方法访问，返回`TradeTickItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.TradeTickItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| beginIndex | long | 返回数据的实际开始索引 |
| endIndex | long | 返回数据的实际结束索引 |
| symbol | string | 请求的股票代码 |
| items | List<TickPoint> | 包含逐笔成交数据的列表，单条逐笔成交数据保存在 TickPoint 对象中 |

其中`TickPoint`对象字段如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| time | long | 交易时间戳 |
| price | double | 成交价 |
| volume | long | 成交量 |
| type | string | "+"表示主动买入，"-"表示主动卖出，"\*"表示中性 |

具体字段可通过对象的get方法，如`getTime()`进行访问, 或通过对象的`toString()`方法转换为字符串

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    List<String> symbols = new ArrayList<>();
    symbols.add("AAPL");
    QuoteTradeTickRequest request = QuoteTradeTickRequest.newRequest(symbols, 0, 30, 10);
    request.setTradeSession(TradeSession.Regular);
    QuoteTradeTickResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSONString(response));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
        "code": 0,
        "data": [\
            {\
                "beginIndex": 0,\
                "endIndex": 10,\
                "items": [\
                    {\
                        "price": 169.59,\
                        "time": 1712323800003,\
                        "type": "*",\
                        "volume": 32\
                    },\
                    {\
                        "price": 169.59,\
                        "time": 1712323800003,\
                        "type": "*",\
                        "volume": 8\
                    },\
                    {\
                        "price": 169.59,\
                        "time": 1712323800003,\
                        "type": "*",\
                        "volume": 148\
                    },\
                    {\
                        "price": 169.59,\
                        "time": 1712323800003,\
                        "type": "*",\
                        "volume": 1010\
                    },\
                    {\
                        "price": 169.59,\
                        "time": 1712323800005,\
                        "type": "*",\
                        "volume": 600\
                    },\
                    {\
                        "price": 169.59,\
                        "time": 1712323800007,\
                        "type": "*",\
                        "volume": 32\
                    },\
                    {\
                        "price": 169.59,\
                        "time": 1712323800008,\
                        "type": "*",\
                        "volume": 33\
                    },\
                    {\
                        "price": 169.59,\
                        "time": 1712323800011,\
                        "type": "*",\
                        "volume": 12\
                    },\
                    {\
                        "price": 169.59,\
                        "time": 1712323800011,\
                        "type": "*",\
                        "volume": 186\
                    },\
                    {\
                        "price": 169.59,\
                        "time": 1712323800012,\
                        "type": "*",\
                        "volume": 40\
                    }\
                ],\
                "symbol": "AAPL"\
            }\
        ],
        "message": "success",
        "sign": "XEEDI1HLzWT8rkZp4boFge4OehCeXOVuXJ6U4Lgve1vUz898Ne8Q4CF/f/OIUVcHfC7XbrjqYmHJBwDdQEdfucRdsKH7g0SimlMAnyimNnya4lKIaQ6CRbfE5faEe2sdopjnwZggFkycnCeB0JhiTK72xR5uTC0gGlcaaPY36o4=",
        "success": true,
        "timestamp": 1712557783317
    }

* * *

获取K线数据

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96k%E7%BA%BF%E6%95%B0%E6%8D%AE)

-------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteKlineRequest**

**说明**

支持获取港股、美股K线数据，包括：日、周、月、年，以及分钟、5分钟、15分钟、30分钟、60分钟等多个级别。每次请求最多返回 1200 条记录，建议通过循环调用实现更长时间范围的历史数据获取，以保障接口性能和稳定性。接口支持按照日期区间或指定日期查询。

*   分钟级别K（1/5/15/30/60分钟）：支持近 10 年历史数据
*   日K及以上（日/周/月/年）：提供完整历史数据
*   美股盘前、盘后仅支持 2024 年 4 月后的 60 分钟及以下级别K线数据
*   使用begin\_time和end\_time取当日K线需要等收盘后两个半小时会刷新成全量数据

需要注意的是，可以请求的K线数量可能会存在限制，具体请见[历史行情限制&订阅限制](./permission.md)

**参数**

| 参数  | 类型  | 描述  |
| --- | --- | --- |
| symbols | array | 股票代码列表，上限：50，其中 A 股上限：30 |
| period | string | K 线类型，取值范围（day:日K，week:周K，month:月K，year:年K，1min:1分钟，3min:3分钟，5min:5分钟，15min:15分钟，30min:30分钟，60min:60分钟，120min:2小时，240min:4小时） |
| trade\_session | string | TradeSession 枚举对象，盘前：PreMarket，盘中：Regular，盘后：AfterHours，夜盘：OverNight。默认为盘中，盘前盘后仅支持1分钟K线 |
| right | string | 复权选项 ，br: 前复权（默认），nr: 不复权 |
| begin\_time | long | 区间查询的开始时间，参数受限：1 分钟及 5 分钟 K 线仅支持近 1 个月数据，15/30/60 分钟 K 线支持近 1 年数据，如需更早的分钟级 K 线，请使用指定日期查询。参数默认：-1，单位:毫秒(ms)，前闭后开区间，即查询结果会包含起始时间数据，如查询周/月/年 K 线，会返回包含当前周期的数据（如:起始时间为周三，会返回从这周一的 K 线） |
| end\_time | long | 区间查询的截止时间，参数受限：1 分钟及 5 分钟 K 线仅支持近 1 个月数据，15/30/60分钟K线支持近1年数据，如需更早的分钟级 K 线，请使用指定日期查询。参数默认：-1，单位:毫秒(ms) |
| date | string | 日期，使用该参数（格式: yyyyMMdd）获取指定日期的分钟级别K线数据。<br><br>1.  支持查询近 10 年分钟级 K 线数据<br>    <br>2.  period 参数支持 1min，5min，15min， 30min，60min<br>    <br>3.  symbols 参数只能传入单个股票代码<br>    <br>4.  limit、pageToken、begin\_time、end\_time 等分页和时间筛选参数将自动失效，无需传递<br>    <br>5.  当天查询按照购买数据权限返回，第二天开盘后，查询上一天返回全市场数据当天按照购买数据权限返回，过了当天返回全市场数据 |
| limit | integer | 单次请求返回 K 线数量，不传默认是 300，limit 不能超过 1200，如果 limit 设置大于1200，只会返回 1200 条数据 |
| lang | string | 语言支持: en\_US，zh\_CN，zh\_TW， 默认: en\_US |
| withFundamental | boolean | 是否返回市盈率换手率，默认不返回 |
| pageToken | string | 分页查询 token（只支持单个 symbol，指定 begin\_time 的查询），使用 pageToken 分页拉取数据时其他查询条件不能改变 |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteKlineResponse.java)

返回数据可通过`QuoteKlineResponse.getKlineItems()`方法访问，返回`KlineItem`对象列表，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlineItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| period | string | K线周期 |
| nextPageToken | string | 查询下一页的 token（只支持单个symbol，begin\_time 不为 -1 时才有效），如果没有更多数据返回 null |
| items | array | K 线对象 KlinePoint 的数组,字段参考下面说明 |

其中 K线数据 KlinePoint 的属性如下：

| K线字段 | 类型  | 说明  |
| --- | --- | --- |
| close | double | 收盘价 |
| high | double | 最高价 |
| low | double | 最低价 |
| open | double | 开盘价 |
| time | long | 时间  |
| volume | long | 成交量 |
| amount | double | 成交额 |
| turnoverRate | double | 换手率. 默认不返回 |
| ttmPe | double | 滚动(Trailing Twelve Months)市盈率. 默认不返回 |
| lyrPe | double | 静态(Last Year Ratio)市盈率. 默认不返回 |

具体字段可通过对象的 get 方法，如`getSymbol()`，进行访问

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    List<String> symbols = new ArrayList<>();
    symbols.add("AAPL");
    QuoteKlineResponse response = client.execute(QuoteKlineRequest.newRequest(symbols, KType.day, "2023-05-16", "2023-05-19")
            .withLimit(1000)
            .withRight(RightOption.br));
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSONString(response));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
        "code":0,
        "data":[\
            {\
                "items":[\
                    {\
                        "amount":7140962535.379684,\
                        "close":172.07,\
                        "high":173.1383,\
                        "low":171.7991,\
                        "open":171.99,\
                        "time":1684209600000,\
                        "volume":42110293\
                    },\
                    {\
                        "amount":9878512463.233082,\
                        "close":172.69,\
                        "high":172.925,\
                        "low":170.4201,\
                        "open":171.71,\
                        "time":1684296000000,\
                        "volume":57951604\
                    },\
                    {\
                        "amount":9839606365.96335,\
                        "close":175.05,\
                        "high":175.24,\
                        "low":172.58,\
                        "open":173,\
                        "time":1684382400000,\
                        "volume":65496657\
                    }\
                ],\
                "period":"day",\
                "symbol":"AAPL"\
            }\
        ],
        "message":"success",
        "sign":"vayBuY47WEr6fJeKKlylYqtpNHlWLbguhO9EbUQNR8Y0MdN51ju2BnPuGnNMyBZkiwBL+rG0KuUW0vbQYXGGmiIV3gfxAJA2NwXaLvOK2iQiSOzc6fAuBzyGe1etDtcvnn5apBFB5opLKVqb+lpLEhsPLzcv8rPV6I1ZCLMeQhU=",
        "success":true,
        "timestamp":1684831488647
    }

**PageToken示例**

Java

        List<String> symbols = new ArrayList<>();
        symbols.add("AAPL");
        QuoteKlineRequest request = QuoteKlineRequest.newRequest(symbols, KType.min1,
            "2022-04-25 00:00:00",
            "2022-04-28 00:00:00", TimeZoneId.NewYork);
        request.withLimit(200);
        request.withRight(RightOption.br);
    
        int count = 1;
        while (true) {
          QuoteKlineResponse response = client.execute(request);
          System.out.println(
              "search time:" + count + ", success:" + response.isSuccess() + ", msg:" + response.getMessage());
          if (!response.isSuccess()) {
            break;
          }
          System.out.println(response.getKlineItems());
          if (response.getKlineItems().size() == 0) {
            break;
          }
          KlineItem klineItem = response.getKlineItems().get(0);
          if (klineItem.getNextPageToken() == null) {
            break;
          }
          count++;
          // 10 times per minute
          try {
            TimeUnit.SECONDS.sleep(6);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          // set pagination token then query the next page
          request.withPageToken(klineItem.getNextPageToken());
        }

**PageToken工具类**

默认每页 1000，分页查询 10000 条数据在客户端合并后返回。可以参考工具类提供的几个重载方法，工具方法最多一次汇集 10000 条数据；另外注意每页数据大小设置，每次分页请求服务器会计算一次请求，会影响接口调用频率。可参照示例自行实现，注意翻页边界避免死循环。

Java

      List<KlinePoint> list =
          PageTokenUtil.getKlineByPage("AAPL", KType.min1,
              "2022-04-25 00:00:00",
              "2022-04-28 00:00:00", TimeZoneId.NewYork,
              RightOption.br, 10, 30, PageTokenUtil.DEFAULT_TIME_INTERVAL);
      for (KlinePoint item : list) {
        System.out.println("content:" + item);
      }

* * *

获取分时数据

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E5%88%86%E6%97%B6%E6%95%B0%E6%8D%AE)

---------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteTimelineRequest**

**说明**

获取最近一个交易日的分时数据。分时数据类似于分钟 K 线，每分钟生成一条记录。仅支持查询最新交易日的数据。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | array | Yes | 股票代码列表 |
| period | string | Yes | 分时周期，包含：day 和 day5 |
| trade\_session | String | No  | TradeSession枚举对象，盘前：PreMarket, 盘中：Regular, 盘后：AfterHours。默认为盘中 |
| begin\_time | long | No  | 开始时间(毫秒时间戳),默认返回当天数据 |
| lang | string | No  | 语言支持: zh\_CN,zh\_TW,en\_US, 默认: en\_US |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTimelineResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteTimelineResponse.java)

具体结构如下：

返回数据可通过`QuoteTimelineResponse.getTimelineItems()`方法访问，返回为包含`TimelineItem`对象的List，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.TimelineItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| period | string | 周期 day or 5day |
| preClose | double | 昨日收盘价 |
| intraday | object | 盘中分时数组,字段参考下面说明 |
| preMarket | object | (仅美股) 盘前分时数组和起始结束时间,字段参考下面说明 |
| afterHours | object | (仅美股) 盘后分时数组和起始结束时间,字段参考下面说明 |

`TimelineItem`的具体字段可通过对象的get方法，如`getSymbol()`，进行访问

分时数据intraday字段：

| 分时字段 | 说明  |
| --- | --- |
| volume | 成交量 |
| avgPrice | 平均成交价格 |
| price | 最新价格 |
| time | 当前分时时间 |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    QuoteTimelineResponse response = client.execute(QuoteTimelineRequest.newRequest(List.of("AAPL"), 1544129760000L));
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getTimelineItems().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code": 0,
      "data": [\
        {\
          "symbol": "AAPL",\
          "preMarket": {\
            "endTime": 1544106600000,\
            "beginTime": 1544086800000,\
            "items": [\
               \
            ]\
          },\
          "period": "day",\
          "preClose": 176.69000244140625,\
          "afterHours": {\
            "endTime": 1544144400000,\
            "beginTime": 1544130000000,\
            "items": [\
              {\
                "volume": 872772,\
                "avgPrice": 174.71916,\
                "price": 174.69,\
                "time": 1544130000000\
              },\
              {\
                "volume": 3792,\
                "avgPrice": 174.71893,\
                "price": 174.66,\
                "time": 1544130060000\
              }\
            ]\
          },\
          "intraday": [\
            {\
              "items": [\
                {\
                  "volume": 201594,\
                  "avgPrice": 172.0327,\
                  "price": 174.34,\
                  "time": 1544129760000\
                },\
                {\
                  "volume": 139040,\
                  "avgPrice": 172.03645,\
                  "price": 174.4156,\
                  "time": 1544129820000\
                },\
                {\
                  "volume": 178427,\
                  "avgPrice": 172.0413,\
                  "price": 174.44,\
                  "time": 1544129880000\
                },\
                {\
                  "volume": 2969567,\
                  "avgPrice": 172.21619,\
                  "price": 174.72,\
                  "time": 1544129940000\
                }\
              ]\
            }\
          ]\
        }\
      ],
      "timestamp": 1544185099595,
      "message": "success"
    }

* * *

获取历史分时数据

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E5%8E%86%E5%8F%B2%E5%88%86%E6%97%B6%E6%95%B0%E6%8D%AE)

-----------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteHistoryTimelineRequest**

**说明**

获取指定日期的历史分时数据。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | array | Yes | 股票代码列表 |
| date | string | Yes | 年月日yyyyMMdd，例如20220420 |
| right | RightOption | No  | 前复权：br, 不复权：nr；默认：nr |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteHistoryTimelineResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteHistoryTimelineResponse.java)

具体结构如下：

返回数据可通过`QuoteHistoryTimelineResponse.getTimelineItems()`方法访问，返回为包含`HistoryTimelineItem`对象的List，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.HistoryTimelineItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| items | TimelinePoint | 盘中分时数组,字段参考下面说明 |

`TimelineItem`的具体字段可通过对象的get方法，如`getSymbol()`，进行访问

分时数据items字段TimelinePoint：

| 分时字段 | 说明  |
| --- | --- |
| volume | 成交量 |
| avgPrice | 平均成交价格 |
| price | 最新价格 |
| time | 当前分时时间 |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    List<String> symbols = new ArrayList<>();
    symbols.add("AAPL");
    QuoteHistoryTimelineRequest request = QuoteHistoryTimelineRequest.newRequest(symbols, "20220420");
    request.withRight(RightOption.br);
    QuoteHistoryTimelineResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getTimelineItems().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
        "code":0,
        "message":"success",
        "timestamp":1651737871819,
        "data":[\
            {\
                "symbol":"AAPL",\
                "items":[\
                    {\
                        "time":1650461400000,\
                        "volume":1414143,\
                        "price":168.75,\
                        "avgPrice":168.66885\
                    },\
                    {\
                        "time":1650461460000,\
                        "volume":392862,\
                        "price":168.25,\
                        "avgPrice":168.65086\
                    },\
                    // ....\
                    {\
                        "time":1650484680000,\
                        "volume":443549,\
                        "price":167.24,\
                        "avgPrice":167.47902\
                    },\
                    {\
                        "time":1650484740000,\
                        "volume":6457118,\
                        "price":167.23,\
                        "avgPrice":167.45808\
                    }\
                ]\
            }\
        ]
    }

* * *

获取股票延迟行情

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E5%BB%B6%E8%BF%9F%E8%A1%8C%E6%83%85)

-----------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteDelayRequest**

**说明**

本接口为免费延迟行情接口，无需购买行情权限，开通开发者账号后可直接请求使用。当前仅支持获取美股延迟行情，相较实时行情延迟约15分钟。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | array | Yes | 股票代码（单次请求上限50），目前仅支持获取美股延迟行情。 如 \['AAPL', 'MSFT'\] |

**示例**

Java

    List<String> symbols = new ArrayList<>();
    symbols.add("AAPL");
    symbols.add("TSLA");
    QuoteDelayRequest delayRequest = QuoteDelayRequest.newRequest(symbols);
    QuoteDelayResponse response = client.execute(delayRequest);

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDelayResponse` [source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteDelayResponse.java)

具体结构如下：

Java

    public class QuoteDelayResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<QuoteDelayItem> quoteDelayItems;
      }

返回数据可通过`TigerResponse.getQuoteDelayItems()`方法访问，返回为包含`QuoteDelayItem`对象的List，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.quoteDelayItem` 属性如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| close | double | 收盘价 |
| high | double | 最高价 |
| low | double | 最低价 |
| open | double | 开盘价 |
| preClose | double | 昨日收盘价 |
| time | long | 时间  |
| volume | long | 成交量 |

`QuoteDelayItem`的具体字段可通过对象的get方法，如`getClose()`进行访问

**返回示例**

JSON

    [\
      {\
        "close": 156.81,\
        "halted": 0,\
        "high": 160.45,\
        "low": 156.36,\
        "open": 159.565,\
        "preClose": 161.94,\
        "symbol": "AAPL",\
        "time": 1637949600000,\
        "volume": 76959752\
      },\
      {\
        "close": 1081.92,\
        "halted": 0,\
        "high": 1108.7827,\
        "low": 1081,\
        "open": 1099.47,\
        "preClose": 1116,\
        "symbol": "TSLA",\
        "time": 1637949600000,\
        "volume": 11680890\
      }\
    ]

* * *

获取股票交易信息

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E4%BA%A4%E6%98%93%E4%BF%A1%E6%81%AF)

-----------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteStockTradeRequest**

**说明**

获取股票交易所需的信息，包括每手股数，报价精度及股价最小变动单位

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | array | Yes | 股票代码列表，上限为：50 |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockTradeResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteStockTradeResponse.java)

结构如下：

Java

    public class QuoteStockTradeResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<QuoteStockTradeItem> stockTradeItems;
    
      public List<QuoteStockTradeItem> getStockTradeItems() {
        return stockTradeItems;
      }
    }

返回数据可通过`QuoteStockTradeResponse.getStockTradeItems()`方法访问，返回`QuoteStockTradeItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.QuoteStockTradeItem` 属性如下:

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| symbol | String | 股票代码 |
| lotSize | Integer | 每手股数 |
| spreadScale | Integer | 报价精度 |
| minTick | Double | 股价最小变动单位 |

具体字段可通过对象的get方法，如`getSymbol()`进行访问, 或通过对象的`toString()`方法转换为字符串

**示例**

Java

    List<String> symbols = new ArrayList<>();
    symbols.add("00700");
    symbols.add("00810");
    QuoteStockTradeResponse response = client.execute(QuoteStockTradeRequest.newRequest(symbols));
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getStockTradeItems().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code": 0,
      "data": [{\
        "lotSize": 100,\
        "minTick": 0.2,\
        "spreadScale": 0,\
        "symbol": "00700"\
      }, {\
        "lotSize": 6000,\
        "minTick": 0.001,\
        "spreadScale": 0,\
        "symbol": "00810"\
      }],
      "message": "success",
      "timestamp": 1546853907390
    }

* * *

获取股票资金流数据

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E8%B5%84%E9%87%91%E6%B5%81%E6%95%B0%E6%8D%AE)

---------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteCapitalFlowRequest**

**说明**

获取股票资金净流入数据，包括最近一个交易日的实时分钟数据，和不同周期的净流入数据。对不同的时间粒度，支持获取以日、周、月、季、半年、年为单位的数据，最高限制为1200条，默认为200条。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | string | Yes | 股票代码 |
| period | string | Yes | 数据类型，取值范围(intraday:实时，day:日，week:周，month:月，year:年，quarter:季度，6month:半年) |
| market | string | Yes | US 美股，HK港股，CN A股(实时资金流向不支持A股) |
| begin\_time | long | No  | 开始时间，默认：-1，单位:毫秒(ms),前闭后开区间，即查询结果会包含起始时间数据，如查询周/月/年K线，会返回包含当前周期的数据(如:起始时间为周三，会返回从这周一的数据) |
| end\_time | long | No  | 结束时间，默认：-1，单位:毫秒(ms) |
| limit | integer | No  | 单次请求返回数量，不传默认是200，limit不能超过1200，如果limit设置大于1200，只会返回1200条数据 |
| lang | string | No  | 语言支持: zh\_CN,zh\_TW,en\_US, 默认: en\_US |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteCapitalFlowResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteCapitalFlowResponse.java)

返回数据可通过`QuoteCapitalFlowResponse.getCapitalFlowItem()`方法访问，返回`CapitalFlowItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.CapitalFlowItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| period | string | 周期  |
| items | array | 资金净流入数组，字段参考下面说明 |

其中具体时间点的数据items属性如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| netInflow | double | 净流入金额，负数表示流出 |
| time | string | 标的所在市场时区的时间字符串，实时数据时为"11-25 12:48:00 EST"格式，非实时数据为"2022-11-22"格式 |
| timestamp | long | 时间  |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    // 实时数据
    QuoteCapitalFlowRequest request = QuoteCapitalFlowRequest.newRequest(
      "AAPL", Market.US, CapitalPeriod.intraday);
    QuoteCapitalFlowResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSON(response.getCapitalFlowItem()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }
    
    // 最近10天资金净流入数据
    QuoteCapitalFlowRequest request = QuoteCapitalFlowRequest.newRequest(
      "00700", Market.HK, CapitalPeriod.day);
    request.setLimit(10);
    QuoteCapitalFlowResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSON(response.getCapitalFlowItem()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    // 实时数据
    {
      "symbol": "AAPL",
      "items": [\
        {\
          "netInflow": -109057032.2042,\
          "time": "11-25 09:30:00 EST",\
          "timestamp": 1669386600000\
        },\
        {\
          "netInflow": -116890065.471,\
          "time": "11-25 09:31:00 EST",\
          "timestamp": 1669386660000\
        }\
      ]
    }
    
    // 最近10天资金净流入数据
    {
      "symbol": "00700",
      "period": "day",
      "items": [\
        {\
          "netInflow": 687742300,\
          "time": "2022-11-14",\
          "timestamp": 1668355200000\
        },\
        {\
          "netInflow": 1926444000,\
          "time": "2022-11-15",\
          "timestamp": 1668441600000\
        },\
        {\
          "netInflow": 1033900840,\
          "time": "2022-11-16",\
          "timestamp": 1668528000000\
        },\
        {\
          "netInflow": 1667729580,\
          "time": "2022-11-17",\
          "timestamp": 1668614400000\
        },\
        {\
          "netInflow": 284728680,\
          "time": "2022-11-18",\
          "timestamp": 1668700800000\
        },\
        {\
          "netInflow": 661046060,\
          "time": "2022-11-21",\
          "timestamp": 1668960000000\
        },\
        {\
          "netInflow": -86129700,\
          "time": "2022-11-22",\
          "timestamp": 1669046400000\
        },\
        {\
          "netInflow": 350223200,\
          "time": "2022-11-23",\
          "timestamp": 1669132800000\
        },\
        {\
          "netInflow": -161005460,\
          "time": "2022-11-24",\
          "timestamp": 1669219200000\
        },\
        {\
          "netInflow": 17355680,\
          "time": "2022-11-25",\
          "timestamp": 1669305600000\
        }\
      ]
    }

* * *

获取股票资金分布

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E8%B5%84%E9%87%91%E5%88%86%E5%B8%83)

-----------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteCapitalDistributionRequest**

**说明**

获取股票资金分布。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | string | Yes | 股票代码 |
| market | string | Yes | US 美股，HK港股，CN A股 |
| lang | string | No  | 语言支持: zh\_CN，zh\_TW，en\_US；默认：en\_US |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteCapitalDistributionResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteCapitalDistributionResponse.java)

返回数据可通过`QuoteCapitalDistributionResponse.getCapitalDistributionItem()`方法访问，返回`CapitalDistributionItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.CapitalDistributionItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| netInflow | double | 净流入金额(资金总流入 - 资金总流出)，负数表示流出 |
| inAll | double | 资金流入总额（大单+中单+小单） |
| inBig | double | 大单流入 |
| inMid | double | 中单流入 |
| inSmall | double | 小单流入 |
| outAll | double | 资金流出总额（大单+中单+小单） |
| outBig | double | 大单流出 |
| outMid | double | 中单流出 |
| outSmall | double | 小单流出 |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    
    QuoteCapitalDistributionRequest request = QuoteCapitalDistributionRequest.newRequest(
        "00700", Market.HK);
    QuoteCapitalDistributionResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSON(response.getCapitalDistributionItem()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "inBig": 721190520,
      "netInflow": 481406280,
      "symbol": "00700",
      "outBig": 464624440,
      "outAll": 3639820460,
      "inAll": 4121226740,
      "inMid": 604918520,
      "inSmall": 2795117700,
      "outMid": 572017220,
      "outSmall": 2603178800
    }

* * *

获取港股经纪商买卖席位

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E6%B8%AF%E8%82%A1%E7%BB%8F%E7%BA%AA%E5%95%86%E4%B9%B0%E5%8D%96%E5%B8%AD%E4%BD%8D)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteStockBrokerRequest**

**说明**

获取港股经纪商买卖席位。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | string | Yes | 股票代码 |
| limit | integer | No  | 单次请求返回买方/卖方席位数量，不传默认是 40，limit 不能超过 60，如果 limit 设置大于 60，只会返回 60 条数据 |
| lang | string | No  | 语言支持: en\_US，zh\_CN，zh\_TW， 默认：en\_US |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockBrokerResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteStockBrokerResponse.java)

返回数据可通过`QuoteStockBrokerResponse.getStockBrokerItem()`方法访问，返回`StockBrokerItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.StockBrokerItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| bidBroker | array | 买方不同价格档位数组,字段参考 LevelBroker 说明 |
| askBroker | array | 卖方不同价格档位数组,字段参考 LevelBroker 说明 |

其中LevelBroker属性如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| level | integer | 价格档位 |
| price | double | 价格  |
| brokerCount | integer | 席位数量 |
| broker | array | 经纪商买卖席位列表 |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    
    QuoteStockBrokerRequest request = QuoteStockBrokerRequest.newRequest("00700", 10, Language.zh_CN);
    QuoteStockBrokerResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSON(response.getStockBrokerItem()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
        "bidBroker":[\
            {\
                "level":1,\
                "price":287.8,\
                "brokerCount":7,\
                "broker":[\
                    {\
                        "name":"中国投资",\
                        "id":"6997"\
                    },\
                    {\
                        "name":"中国投资",\
                        "id":"6998"\
                    },\
                    {\
                        "name":"中银",\
                        "id":"8134"\
                    },\
                    {\
                        "name":"中国投资",\
                        "id":"6998"\
                    },\
                    {\
                        "name":"中国创盈",\
                        "id":"5999"\
                    },\
                    {\
                        "name":"中国投资",\
                        "id":"6996"\
                    },\
                    {\
                        "name":"J.P. Morgan",\
                        "id":"5342"\
                    }\
                ]\
            },\
            {\
                "level":2,\
                "price":287.6,\
                "brokerCount":3,\
                "broker":[\
                    {\
                        "name":"中国投资",\
                        "id":"6998"\
                    },\
                    {\
                        "name":"中国投资",\
                        "id":"6999"\
                    },\
                    {\
                        "name":"中国创盈",\
                        "id":"5998"\
                    }\
                ]\
            }\
        ],
        "symbol":"00700",
        "askBroker":[\
            {\
                "level":1,\
                "price":288,\
                "brokerCount":10,\
                "broker":[\
                    {\
                        "name":"FUTU Securities",\
                        "id":"8461"\
                    },\
                    {\
                        "name":"中国创盈",\
                        "id":"5998"\
                    },\
                    {\
                        "name":"工银亚洲",\
                        "id":"8117"\
                    },\
                    {\
                        "name":"中国创盈",\
                        "id":"5998"\
                    },\
                    {\
                        "name":"中国投资",\
                        "id":"6999"\
                    },\
                    {\
                        "name":"汇丰",\
                        "id":"8577"\
                    },\
                    {\
                        "name":"中国投资",\
                        "id":"6996"\
                    },\
                    {\
                        "name":"中国投资",\
                        "id":"6999"\
                    },\
                    {\
                        "name":"FUTU Securities",\
                        "id":"8462"\
                    },\
                    {\
                        "name":"汇丰",\
                        "id":"8575"\
                    }\
                ]\
            }\
        ]
    }

* * *

获取港股经纪商持仓市值

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E6%B8%AF%E8%82%A1%E7%BB%8F%E7%BA%AA%E5%95%86%E6%8C%81%E4%BB%93%E5%B8%82%E5%80%BC)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteBrokerHoldRequest**

**说明**

获取港股经纪商持仓市值。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | Market | Yes | 常用市场枚举值：只支持 HK 港股. 枚举值详见： [市场枚举](./appendix-enum-java.md#market) |
| limit | Integer | No  | 分页每页数量，不传默认是 50，limit 不能超过 500，如果 limit 设置大于 500，只会返回 500 条数据 |
| page | Integer | No  | 分页页码，从 0 开始，默认 0 |
| orderBy | String | No  | 排序字段，默认 `marketValue`, 可选值 marketValue/sharesHold/buyAmount/buyAmount5/buyAmount20/buyAmount60 |
| direction | String | No  | 排序方向, DESC 降序 / ASC 升序, 默认 DESC |
| lang | Language | No  | 语言支持: en\_US，zh\_CN，zh\_TW， 默认: en\_US |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteBrokerHoldResponse`

返回数据可通过`QuoteBrokerHoldResponse.getBrokerHoldPageItem()`方法访问，返回`BrokerHoldPageItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.BrokerHoldItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| ortId | String | 经纪商id |
| orgName | String | 经纪商名称 |
| date | String | 最近交易日 |
| sharesHold | Long | 持仓数量 |
| marketValue | Double | 持仓市值 |
| buyAmount | Long | 1 日净买入量 |
| buyAmount5 | Long | 5 日净买入量 |
| buyAmount20 | Long | 20 日净买入量 |
| buyAmount60 | Long | 60 日净买入量 |
| market | String | 市场  |

**示例**

Java

    import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
    
    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    
    QuoteBrokerHoldResponse response = client.execute(QuoteBrokerHoldRequest.newRequest(Market.HK, 50, 0, "buyAmount", "DESC"));
    
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSON(response.getBrokerHoldPageItem()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "items": [\
        {\
          "buyAmount": -664008615,\
          "buyAmount20": -7689110984,\
          "buyAmount5": -3624382900,\
          "buyAmount60": -32074599790,\
          "date": "2025-04-14",\
          "market": "HK",\
          "marketValue": 9.194317557765623E12,\
          "orgId": "C00019",\
          "orgName": "HONGKONG SHANGHAI BANKING",\
          "sharesHold": 696720677628\
        },\
        {\
          "buyAmount": -141033766,\
          "buyAmount20": 10883985046,\
          "buyAmount5": 4542732770,\
          "buyAmount60": 17917177125,\
          "date": "2025-04-14",\
          "market": "HK",\
          "marketValue": 2.604522670544478E12,\
          "orgId": "A00003",\
          "orgName": "(SH)-HK Stock Connect",\
          "sharesHold": 294896574932\
        },\
        {\
          "buyAmount": 92657497,\
          "buyAmount20": -120870931,\
          "buyAmount5": -209014086,\
          "buyAmount60": -5366617605,\
          "date": "2025-04-14",\
          "market": "HK",\
          "marketValue": 1.9825098585195176E12,\
          "orgId": "C00010",\
          "orgName": "CITIBANK N.A.",\
          "sharesHold": 206374851472\
        },\
        {\
          "buyAmount": 220232675,\
          "buyAmount20": 5542004542,\
          "buyAmount5": 2942019847,\
          "buyAmount60": 4379764988,\
          "date": "2025-04-14",\
          "market": "HK",\
          "marketValue": 1.7997280891850354E12,\
          "orgId": "A00004",\
          "orgName": "(SZ)-HK Stock Connect",\
          "sharesHold": 199832572853\
        },\
        {\
          "buyAmount": 48566660,\
          "buyAmount20": -3485972222,\
          "buyAmount5": 1044767966,\
          "buyAmount60": -1685601977,\
          "date": "2025-04-14",\
          "market": "HK",\
          "marketValue": 1.0904056485834637E12,\
          "orgId": "B01161",\
          "orgName": "UBS",\
          "sharesHold": 156347776229\
        }\
      ],
      "page": 0,
      "totalCount": 672,
      "totalPage": 135
    }

* * *

获取热门交易榜

[](./quote-stock-java.md#%E8%8E%B7%E5%8F%96%E7%83%AD%E9%97%A8%E4%BA%A4%E6%98%93%E6%A6%9C)

-------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteTradeRankRequest**

**说明**

获取股票热门交易榜，数据约每 20 秒更新一次。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | Market | Yes | 市场，可选值：Market.US 美股，Market.HK 港股，Market.SG 新加坡股 |
| lang | string | No  | 语言，可选值: en\_US，zh\_CN，zh\_TW，默认：en\_US |

**返回**

美股返回 30 条，港股和新加坡股返回 10 条。

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeRankResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteTradeRankResponse.java)

返回数据可通过`QuoteTradeRankResponse.getTradeRankItem()`方法访问，返回`TradeRankItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.TradeRankItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| name | string | 股票名称 |
| market | string | 市场  |
| changeRate | double | 盘中涨跌幅，如果当前为非盘中交易时段，则为上一交易日的盘中涨跌幅 |
| sellOrderRate | double | 卖单占比，当天的累计买卖比例，比如盘中阶段是：盘前+盘中的累计买卖比例，盘后阶段是包括了 盘前+盘中+盘后 |
| buyOrderRate | double | 买单占比，计算方法同上 |
| hourTrading | object | 盘前盘后交易信息 |
| hourTrading.tradingStatus | integer | 盘前盘后交易状态 |
| hourTrading.tradeSession | string | 盘前盘后交易时段 |
| hourTrading.changeRate | double | 最近一个盘前盘后涨跌幅 |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    
    QuoteTradeRankRequest request = QuoteTradeRankRequest.newRequest(Market.US, Language.en_US);
    QuoteTradeRankResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSON(response.getTradeRankItem()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    [\
      {\
        "buyOrderRate": 0.606061,\
        "changeRate": -0.030871,\
        "hourTrading": {\
          "changeRate": -0.009057,\
          "tradingStatus": 1\
        },\
        "market": "US",\
        "name": "NVIDIA",\
        "secType": "STK",\
        "sellOrderRate": 0.393939,\
        "symbol": "NVDA"\
      },\
      {\
        "buyOrderRate": 0.409091,\
        "changeRate": -0.021522,\
        "hourTrading": {\
          "changeRate": -0.004568,\
          "tradingStatus": 1\
        },\
        "market": "US",\
        "name": "Tesla Motors",\
        "secType": "STK",\
        "sellOrderRate": 0.590909,\
        "symbol": "TSLA"\
      },\
      {\
        "buyOrderRate": 0.157895,\
        "changeRate": -0.040058,\
        "hourTrading": {\
          "changeRate": 0.150228,\
          "tradingStatus": 1\
        },\
        "market": "US",\
        "name": "Li Auto",\
        "secType": "STK",\
        "sellOrderRate": 0.842105,\
        "symbol": "LI"\
      },\
      {\
        "buyOrderRate": 0.333333,\
        "changeRate": -0.10233,\
        "hourTrading": {\
          "changeRate": 0.033246,\
          "tradingStatus": 1\
        },\
        "market": "US",\
        "name": "Alibaba",\
        "secType": "STK",\
        "sellOrderRate": 0.666667,\
        "symbol": "BABA"\
      },\
      {\
        "buyOrderRate": 0.333333,\
        "changeRate": -0.079543,\
        "hourTrading": {\
          "changeRate": -0.03759,\
          "tradingStatus": 1\
        },\
        "market": "US",\
        "name": "SUPER MICRO COMPUTER INC",\
        "secType": "STK",\
        "sellOrderRate": 0.666667,\
        "symbol": "SMCI"\
      }\
    ]
