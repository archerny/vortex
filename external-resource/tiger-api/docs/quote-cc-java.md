# 数字货币

获取代码列表

[](./quote-cc-java.md#%E8%8E%B7%E5%8F%96%E4%BB%A3%E7%A0%81%E5%88%97%E8%A1%A8)

------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteSymbolRequest**

**说明**

获取所有数字货币的代码列表。

**参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| sec\_type | string | 是   | 必为 `CC` |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolResponse` [source](https://github.com/tigerfintech/openapi-java-sdk/blob/71631121961002aa1dadf601e922a494d14f5ed0/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteSymbolResponse.java)

具体结构如下

Java

    public class QuoteSymbolResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<String> symbols;
    }

返回数据可以通过`QuoteSymbolResponse.getSymbols()`方法调用，结果为包含返回数字货币代码数据的List

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
      ClientConfig.DEFAULT_CONFIG);
    
    QuoteSymbolRequest request = QuoteSymbolRequest.newCcRequest();
    
    QuoteSymbolResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getSymbols().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code" : 0,
      "message" : "success",
      "timestamp" : 1770033519398,
      "symbols" : [ "APT.USD", "IOTX.USD", "USDT.USD", "DYDX.USD", "DOGE.USD", "KAIA.USD", "ATOM.USD", "COMP.USD", "UNI.USD", "AAVE.USD", "LDO.USD", "LINK.USD", "SNX.USD", "OP.USD", "DOT.USD", "POL.USD", "BTC.USD", "SOL.USD", "ARB.USD", "TON.USD", "AVAX.USD", "MKR.USD", "IMX.USD", "ETH.USD", "LTC.USD" ],
      "success" : true
    }

* * *

获取实时行情

[](./quote-cc-java.md#%E8%8E%B7%E5%8F%96%E5%AE%9E%E6%97%B6%E8%A1%8C%E6%83%85)

------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteRealTimeQuoteRequest**

**说明**

获取数字货币实时行情。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | list | Yes | 数字货币代码列表（单次上限50） |
| sec\_type | string | Yes | 必为 `CC` |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteTradeCalendarResponse.java)

返回数据可通过`QuoteRealTimeQuoteResponse.getRealTimeQuoteItems()`方法访问，返回`RealTimeQuoteItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.RealTimeQuoteItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 数字货币代码 |
| open | double | 开盘价 |
| high | double | 最高价 |
| low | double | 最低价 |
| close | double | 收盘价 |
| preClose | double | 前一交易日收盘价 |
| latestPrice | double | 最新价 |
| latestTime | long | 最新成交时间 |
| volumeDecimal | double | 成交量，原始的成交量值，保留完整的小数精度 |
| change | double | 涨跌额 |
| changeRate | double | 涨跌幅 |

**示例**

Java

    List<String> symbols = new ArrayList<>();
    symbols.add("BTC.USD");
    symbols.add("ETH.USD");
    QuoteRealTimeQuoteRequest request = QuoteRealTimeQuoteRequest.newCcRequest(symbols);
    
    QuoteRealTimeQuoteResponse response = client.execute(request);
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
      "timestamp" : 1770032332142,
      "realTimeQuoteItems" : [ {\
        "symbol" : "BTC.USD",\
        "open" : 77517.15,\
        "high" : 78366.03,\
        "low" : 74550.0,\
        "close" : 77517.15,\
        "preClose" : 77517.15,\
        "latestPrice" : 77839.45,\
        "latestTime" : 1770032330750,\
        "volumeDecimal" : 697.55996,\
        "change" : 322.3,\
        "changeRate" : 0.004157789598817811\
      }, {\
        "symbol" : "ETH.USD",\
        "open" : 2314.18,\
        "high" : 2374.87,\
        "low" : 2156.8,\
        "close" : 2313.05,\
        "preClose" : 2313.05,\
        "latestPrice" : 2286.79,\
        "latestTime" : 1770032330750,\
        "volumeDecimal" : 25668.0994,\
        "change" : -26.26,\
        "changeRate" : -0.011352975508527702\
      } ],
      "success" : true
    }

* * *

获取K线数据

[](./quote-cc-java.md#%E8%8E%B7%E5%8F%96k%E7%BA%BF%E6%95%B0%E6%8D%AE)

----------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteKlineRequest**

**说明**

获取数字货币K线数据，包括：1分、60分、天、周、月级别。每次请求最多返回 1200 条记录，建议通过循环调用实现更长时间范围的历史数据获取，以保障接口性能和稳定性。接口支持按照日期区间或指定日期查询。

*   分钟级别K：BTC 支持最早2024年3月27日开始的数据
*   日K及以上（日/周/月/年）：BTC 支持最早2010年7月13日开始的数据

**参数**

| 参数  | 类型  | 描述  |
| --- | --- | --- |
| symbols | list | 数字货币代码列表，上限：10 |
| period | string | K 线类型，取值范围: 1min, 60min, day, week, month |
| begin\_time | long | 区间查询的开始时间 |
| end\_time | long | 区间查询的截止时间 |
| limit | int | 单次请求返回 K 线数量，不传默认是 300，limit 不能超过 1200，如果 limit 设置大于1200，只会返回 1200 条数据 |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteKlineResponse.java)

返回数据可通过`QuoteKlineResponse.getKlineItems()`方法访问，返回`KlineItem`对象列表，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlineItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 数字货币代码 |
| period | string | K线周期 |
| items | array | K 线对象 `KlinePoint` 的数组，字段参考下面说明 |

其中 K线数据 `KlinePoint` 的属性如下：

| K线字段 | 类型  | 说明  |
| --- | --- | --- |
| close | double | 收盘价 |
| high | double | 最高价 |
| low | double | 最低价 |
| open | double | 开盘价 |
| time | long | 时间  |
| volumeDecimal | double | 成交量，原始的成交量值，保留完整的小数精度 |

具体字段可通过对象的 get 方法，如`getSymbol()`，进行访问

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    
    List<String> symbols = Arrays.asList("BTC.USD", "ETH.USD");
    KType kType = KType.min1;
    String startTime = "2026-01-29";
    String endTime = "2026-01-30";
    TimeZoneId timeZoneId = TimeZoneId.NewYork;
    int limit = 200;
    QuoteKlineRequest request = QuoteKlineRequest.newRequest(symbols, kType, startTime, endTime, timeZoneId)
            .withLimit(limit)
            .withSecType(SecType.CC);
    QuoteKlineResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSONString(response));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code" : 0,
      "message" : "success",
      "timestamp" : 1770037196690,
      "klineItems" : [ {\
        "symbol" : "BTC.USD",\
        "period" : "1min",\
        "items" : [ {\
          "open" : 82808.12,\
          "close" : 82780.21,\
          "high" : 82808.12,\
          "low" : 82772.25,\
          "time" : 1769749140000,\
          "volumeDouble" : 0.29135\
        }, {\
          "open" : 82810.81,\
          "close" : 82803.37,\
          "high" : 82810.81,\
          "low" : 82803.37,\
          "time" : 1769749080000,\
          "volumeDouble" : 0.04455\
        }, {\
          "open" : 82823.91,\
          "close" : 82810.17,\
          "high" : 82823.91,\
          "low" : 82791.82,\
          "time" : 1769749020000,\
          "volumeDecimal" : 0.0493\
        } ],\
      }, {\
        "symbol" : "ETH.USD",\
        "period" : "1min",\
        "items" : [ {\
          "open" : 2751.48,\
          "close" : 2748.85,\
          "high" : 2751.75,\
          "low" : 2746.08,\
          "time" : 1769749140000,\
          "volumeDecimal" : 21.495\
        }, {\
          "open" : 2751.84,\
          "close" : 2751.83,\
          "high" : 2751.84,\
          "low" : 2751.83,\
          "time" : 1769749080000,\
          "volumeDecimal" : 0.022\
        }, {\
          "open" : 2753.08,\
          "close" : 2753.07,\
          "high" : 2753.08,\
          "low" : 2753.07,\
          "time" : 1769749020000,\
          "volumeDecimal" : 0.4104\
        } ],\
      } ],
      "success" : true
    }

* * *

获取分时数据

[](./quote-cc-java.md#%E8%8E%B7%E5%8F%96%E5%88%86%E6%97%B6%E6%95%B0%E6%8D%AE)

------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteTimelineRequest**

**说明**

获取最近一个交易日的分时数据。分时数据类似于分钟 K 线，每分钟生成一条记录。仅支持查询最新交易日的数据。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | array | Yes | 数字货币代码列表 |
| period | string | Yes | 分时周期，取值范围：day 和 day5 |
| begin\_time | long | No  | 开始时间(毫秒时间戳),默认返回当天数据 |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTimelineResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/QuoteTimelineResponse.java)

具体结构如下：

返回数据可通过`QuoteTimelineResponse.getTimelineItems()`方法访问，返回为包含`TimelineItem`对象的List，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.TimelineItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 数字货币代码 |
| period | string | 周期，day or 5day |
| preClose | double | 昨日收盘价 |
| intraday | object | 分时数组，字段参考下面说明 |

`TimelineItem`的具体字段可通过对象的get方法，如`getSymbol()`，进行访问

分时数据`intraday`字段：

| 分时字段 | 说明  |
| --- | --- |
| avgPrice | 平均成交价格 |
| price | 最新价格 |
| time | 当前分时时间 |
| volumeDecimal | 成交量，原始的成交量值，保留完整的小数精度 |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
      ClientConfig.DEFAULT_CONFIG);
    
    List<String> symbols = new ArrayList<>();
    symbols.add("BTC.USD");
    symbols.add("ETH.USD");
    QuoteTimelineRequest request =
            QuoteTimelineRequest.newCcRequest(symbols, System.currentTimeMillis() - 60 * 60 * 1000);
    
    QuoteTimelineResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getTimelineItems().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code" : 0,
      "message" : "success",
      "timestamp" : 1770040737146,
      "timelineItems" : [ {\
        "symbol" : "BTC.USD",\
        "period" : "day",\
        "preClose" : 77517.15,\
        "intraday" : {\
          "items" : [ {\
            "price" : 77917.75,\
            "avgPrice" : null,\
            "time" : 1770040440000,\
            "volumeDecimal" : 0.01902\
          }, {\
            "price" : 77999.83,\
            "avgPrice" : null,\
            "time" : 1770040500000,\
            "volumeDecimal" : 0.06813\
          }, {\
            "price" : 78150.0,\
            "avgPrice" : null,\
            "time" : 1770040560000,\
            "volumeDecimal" : 0.06568\
          }, {\
            "price" : 78159.46,\
            "avgPrice" : null,\
            "time" : 1770040620000,\
            "volumeDecimal" : 0.07281\
          } ],\
          "beginTime" : 1770040435799,\
          "endTime" : 1770040737143\
        }\
      }, {\
        "symbol" : "ETH.USD",\
        "period" : "day",\
        "preClose" : 2313.05,\
        "intraday" : {\
          "items" : [ {\
            "price" : 2321.92,\
            "avgPrice" : null,\
            "time" : 1770040440000,\
            "volumeDecimal" : 8.9763\
          }, {\
            "price" : 2322.16,\
            "avgPrice" : null,\
            "time" : 1770040500000,\
            "volumeDecimal" : 1.1757\
          }, {\
            "price" : 2325.55,\
            "avgPrice" : null,\
            "time" : 1770040560000,\
            "volumeDecimal" : 25.7984\
          }, {\
            "price" : 2326.31,\
            "avgPrice" : null,\
            "time" : 1770040620000,\
            "volumeDecimal" : 13.1475\
          } ],\
          "beginTime" : 1770040435799,\
          "endTime" : 1770040737143\
        }\
      "success" : true\
    }\
\
* * *\
\
  \

