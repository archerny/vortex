# 期权

获取期权到期日

[](./quote-option-java.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E5%88%B0%E6%9C%9F%E6%97%A5)

--------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：OptionExpirationQueryRequest**

**说明**

获取指定股票的期权到期日信息，批量请求单次最多30条。

**参数**

| 参数  | 是否必填 | 类型  | 描述  |
| --- | --- | --- | --- |
| symbols | Yes | array | 股票代码列表，上限为：30 |
| market | Yes | string | US / HK港股 |

**返回** `com.tigerbrokers.stock.openapi.client.https.response.option.OptionExpirationResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/option/OptionExpirationResponse.java)

结构如下：

Java

    public class OptionExpirationResponse extends TigerResponse {
      @JSONField(name = "data")
      private List<OptionExpirationItem> optionExpirationItems;
    }

返回数据可通过`OptionExpirationResponse.getOptionExpirationItems()`方法访问，返回`OptionExpirationItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionExpirationItem` 属性如下:

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| count | int | 过期日期个数 |
| dates | array | 过期时间,日期格式，如：2024-06-28 |
| timestamps | array | 过期日期，时间戳格式，如：1544763600000(美国NewYork时间对应的时间戳) |
| periodTags | array | 期权周期标签，m为月期权，w为周期权 |
| optionSymbols | array | 对应期权四要素的symbol |

具体字段可通过对象的get方法，如`getSymbol()`进行访问，或通过对象的`toString()`方法转换为字符串

**示例**

Java

    List<String> symbols = new ArrayList<>();
    symbols.add("VIX");
    OptionExpirationResponse response = client.execute(
            new OptionExpirationQueryRequest(symbols, Market.US));
    // HK market opition. market parameter must be Market.HK
    // symbols.add("PAI.HK");
    // OptionExpirationResponse response = client.execute(
    //        new OptionExpirationQueryRequest(symbols, Market.HK));
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
                "count": 12,\
                "dates": [\
                    "2024-12-24",\
                    "2024-12-31",\
                    "2025-01-08",\
                    "2025-01-15",\
                    "2025-01-22",\
                    "2025-02-19",\
                    "2025-03-18",\
                    "2025-04-16",\
                    "2025-05-21",\
                    "2025-06-18",\
                    "2025-07-16",\
                    "2025-08-20"\
                ],\
                "optionSymbols": [\
                    "VIXW",\
                    "VIXW",\
                    "VIXW",\
                    "VIXW",\
                    "VIX",\
                    "VIX",\
                    "VIX",\
                    "VIX",\
                    "VIX",\
                    "VIX",\
                    "VIX",\
                    "VIX"\
                ],\
                "periodTags": [\
                    "w",\
                    "q",\
                    "w",\
                    "w",\
                    "m",\
                    "m",\
                    "m",\
                    "m",\
                    "m",\
                    "m",\
                    "m",\
                    "m"\
                ],\
                "symbol": "VIX",\
                "timestamps": [\
                    1735016400000,\
                    1735621200000,\
                    1736312400000,\
                    1736917200000,\
                    1737522000000,\
                    1739941200000,\
                    1742270400000,\
                    1744776000000,\
                    1747800000000,\
                    1750219200000,\
                    1752638400000,\
                    1755662400000\
                ]\
            }\
        ],
        "message": "success",
        "sign": "jBxaUFSd6e1qIsEeb6l7/Wb8R7kMcNvvglzC6PsJpd7VMP12HKyKMJH8+4g1ePLyT/TzVhyJpCEpXcUAkGrg7hVMMhLoD397vW2Xf0VKedE5mKMy4I+yFK2PneZXr4xKyfCc/+Yb3dc//1gOEEvk9EQHjDzXp6bmy/dFD4020h0=",
        "success": true,
        "timestamp": 1735048291531
    }

  

\*\* 关于 标普500 .SPX 的期权符号 \*\*

月度期权符号是 `SPX`, 周期权和季度期权的符号都是 `SPXW`

* * *

  

获取期权链

[](./quote-option-java.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E9%93%BE)

------------------------------------------------------------------------------------------------------------

**对应的请求类：OptionChainQueryV3Request**

**说明**

获取期权链。注意：返回的希腊值并非实时数据，而是上一交易日收盘时的最后更新值，盘中可使用[期权指标计算](./quote-option-java.md#option-indicator-calculation)

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | string | Yes | 股票代码，symbol和expiry组合上限为：30 |
| expiry | String | Yes | 期权过期日，示例：'2022-01-01' |
| market | string | Yes | US/HK港股 |

筛选参数：

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| implied\_volatility | double | No  | 隐含波动率 |
| in\_the\_money | boolean | No  | 是否价内 |
| open\_interest | int | No  | 未平仓量 |
| delta | double | No  | delta |
| gamma | double | No  | gamma |
| theta | double | No  | theta |
| vega | double | No  | vega |
| rho | double | No  | rho |

返回希腊值参数：默认不返回

Java

    OptionChainQueryV3Request request  = null;
    request.setReturnGreekValue(true);

**返回** `com.tigerbrokers.stock.openapi.client.https.response.option.OptionChainResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/option/OptionChainResponse.java)

结构如下：

Java

    public class OptionChainResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<OptionChainItem> optionChainItems;
    }

返回数据可通过`OptionChainResponse.getOptionChainItems()`方法访问，返回`OptionChainItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionChainItem` 属性如下:

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 标的股票代码 |
| expiry | long | 期权过期日 |
| items | `List<OptionRealTimeQuoteGroup>` | 列表，包含 OptionRealTimeQuoteGroup 对象，保存期权链数据，说明见下文 |

`OptionRealTimeQuoteGroup`对象结构：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| put | OptionRealTimeQuote | 看跌期权 |
| call | OptionRealTimeQuote | 看涨期权 |

`OptionRealTimeQuote`对象结构：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| identifier | string | 期权标识，如:AAPL 210115C00095000 |
| strike | double | 行权价 |
| right | string | 期权方向，PUT/CALL |
| askPrice | double | 卖盘价格 |
| askSize | int | 卖盘数量 |
| bidPrice | double | 买盘价格 |
| bidSize | int | 买盘数量 |
| lastTimestamp | long | 最新成交时间，如: 1543343800698 |
| latestPrice | double | 最新价 |
| multiplier | double | 乘数，US 期权默认 100 |
| openInterest | int | 未平仓量 |
| preClose | double | 前一交易日的收盘价 |
| volume | long | 成交量 |
| impliedVol | double | 隐含波动率 |
| delta | double | delta |
| gamma | double | gamma |
| theta | double | theta |
| vega | double | vega |
| rho | double | rho |

具体字段可通过对象的get方法，如`getSymbol()`进行访问，或通过 json 方法转换为字符串。

**示例**

Java

    OptionChainModel basicModel = new OptionChainModel("AAPL", "2024-07-26",  TimeZoneId.NewYork);
    OptionChainFilterModel filterModel = new OptionChainFilterModel()
      .inTheMoney(true)
      .impliedVolatility(0.1537, 0.8282)
      .openInterest(10, 50000)
      .greeks(new OptionChainFilterModel.Greeks()
        .delta(-0.8, 0.6)
        .gamma(0.024, 0.30)
        .vega(0.019, 0.343)
        .theta(-0.1, 0.1)
        .rho(-0.096, 0.101)
    );
    OptionChainQueryV3Request request = OptionChainQueryV3Request.of(basicModel, filterModel, Market.US);
    
    OptionChainResponse response = client.execute(request);
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
          "expiry": 1721966400000,\
          "items": [\
            {\
              "put": {\
                "askPrice": 4.65,\
                "askSize": 2,\
                "bidPrice": 4.5,\
                "bidSize": 66,\
                "delta": -0.503388,\
                "gamma": 0.037062,\
                "identifier": "AAPL  240726P00210000",\
                "impliedVol": 0.183129,\
                "lastTimestamp": 1719345582586,\
                "latestPrice": 4.6,\
                "multiplier": 100,\
                "openInterest": 1858,\
                "preClose": 5.26,\
                "rho": -0.072819,\
                "right": "put",\
                "strike": "210.0",\
                "theta": -0.060326,\
                "vega": 0.24135,\
                "volume": 404\
              }\
            },\
            {\
              "put": {\
                "askPrice": 7.75,\
                "askSize": 59,\
                "bidPrice": 7.4,\
                "bidSize": 260,\
                "delta": -0.686314,\
                "gamma": 0.03523,\
                "identifier": "AAPL  240726P00215000",\
                "impliedVol": 0.18012,\
                "lastTimestamp": 1719345365800,\
                "latestPrice": 7.8,\
                "multiplier": 100,\
                "openInterest": 1222,\
                "preClose": 8.5,\
                "rho": -0.084955,\
                "right": "put",\
                "strike": "215.0",\
                "theta": -0.048649,\
                "vega": 0.21153,\
                "volume": 134\
              }\
            }\
          ],\
          "symbol": "AAPL"\
        }\
      ],
      "message": "success",
      "sign": "dA0ngWPHWC0GOme2h0/FPma5UAcLSKiR5vWw9ldZ1wz8sHiRG7QMroO78JXqK5A+J3m6XWT9esAXagwbs8D6mI/3vhFU5QCJQbDd58lQRJRWeAK/G+7eQOwAQIqvgLttBtMBbRKlHMchhHATgUi9U7v/eu4NG5tDHr04InQy0cc=",
      "success": true,
      "timestamp": 1719407360986
    }

* * *

获取期权实时行情

[](./quote-option-java.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E5%AE%9E%E6%97%B6%E8%A1%8C%E6%83%85)

------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：OptionBriefQueryV2Request**

**说明**

获取期权实时行情接口，批量请求单次最多30 条。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | Market | Yes | 市场，支持 US、HK |
| option\_basic | `List<OptionCommonModel>` | Yes | 期权四要素列表，最大 30 |

`OptionCommonModel`参数结构如下

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | string | Yes | 股票代码，港股请参考“获取港股期权名称”接口返回的 symbol |
| right | string | Yes | 看多或看空（CALL/PUT） |
| expiry | long | Yes | 到期时间 |
| strike | string | Yes | 行权价（小数位须和期权链一致，否则可能无数据，1.美股至少1位小数，有2位小数的可能 2. 港股个股期权固定2位小数 3. 港股指数期权无小数位） |

**返回** `com.tigerbrokers.stock.openapi.client.https.request.option.OptionBriefResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/option/OptionBriefResponse.java)

结构如下：

Java

    public class OptionBriefResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<OptionBriefItem> optionBriefItems;
    }

返回数据可通过`TigerResponse.getOptionBriefItems()`方法访问，返回`OptionBriefItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionBriefItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| strike | string | 行权价 |
| bidPrice | double | 买盘价格 |
| bidSize | int | 买盘数量 |
| askPrice | double | 卖盘价格 |
| askSize | int | 卖盘数量 |
| latestPrice | double | 最新价格 |
| timestamp | long | 最新成交时间 |
| volume | int | 成交量 |
| high | double | 最高价 |
| low | double | 最低价 |
| open | double | 开盘价 |
| preClose | double | 前一交易日收盘价 |
| openInterest | int | 未平仓量 |
| change | double | 涨跌额 |
| multiplier | int | 乘数，us期权默认100 |
| ratesBonds | double | 一年期美国国债利率，每天更新一次，如：0.0078 表示实际利率为：0.78% |
| right | string | 方向 (PUT/CALL) |
| volatility | string | 历史波动率 |
| expiry | long | 到期时间（毫秒，当天 0 点） |
| midPrice | double | 中间价 |
| midTimestamp | long | 中间价时间戳 |
| markPrice | double | 标记价 |
| markTimestamp | long | 标记价时间戳 |
| preMarkPrice | double | 昨标记价 |
| sellingReturn | double | 卖出年化收益 |

具体字段可通过对象的get方法，如`getSymbol()`进行访问， 或通过对象的`toString()`方法转换为字符串

**示例**

Java

    OptionCommonModel model = new OptionCommonModel();
    model.setSymbol("TSLA");
    model.setStrike("437.5");
    model.setRight("PUT");
    model.setExpiry("2026-01-30", TimeZoneId.NewYork);
    List<OptionCommonModel> models = new ArrayList<>();
    models.add(model);
    
    OptionBriefQueryV2Request request = new OptionBriefQueryV2Request(models, Market.US);
    OptionBriefResponse response = client.execute(request);
    if (response. isSuccess()) {
       System.out.println(JSONObject.toJSONString(response));
    } else {
       System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code" : 0,
      "message" : "success",
      "timestamp" : 1769061919677,
      "optionBriefItems" : [ {\
        "lang" : null,\
        "identifier" : "TSLA  260130P00437500",\
        "symbol" : "TSLA",\
        "strike" : "437.5",\
        "bidPrice" : 17.45,\
        "bidSize" : 10,\
        "askPrice" : 17.65,\
        "askSize" : 10,\
        "latestPrice" : 17.6,\
        "volume" : 967,\
        "high" : 25.35,\
        "low" : 14.5,\
        "open" : 25.35,\
        "preClose" : 25.44,\
        "openInterest" : 679,\
        "change" : -7.84,\
        "multiplier" : 100,\
        "right" : "put",\
        "volatility" : "31.10%",\
        "expiry" : 1769749200000,\
        "ratesBonds" : 0.035227,\
        "midPrice" : 17.55,\
        "midTimestamp" : 1769029200460,\
        "markPrice" : 17.6,\
        "markTimestamp" : 1769029200460,\
        "preMarkPrice" : 26.125,\
        "sellingReturn" : 1.065105,\
        "timestamp" : 1769028900019,\
        "latestTime" : "2026-01-21 15:55:00.019",\
        "account" : null\
      } ],
      "success" : true
    }

* * *

获取期权深度行情

[](./quote-option-java.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E6%B7%B1%E5%BA%A6%E8%A1%8C%E6%83%85)

------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：OptionDepthQueryRequest**

**说明**

获取期权深度行情数据，支持美国和香港市场期权，批量请求单次最多30条。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | Market | Yes | 市场，US/HK |
| option\_basic | `List<OptionCommonModel>` | Yes | 期权四要素列表，最大 30 |

`OptionCommonModel`参数结构如下

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | string | Yes | 股票代码 |
| right | string | Yes | 看多或看空（CALL/PUT） |
| expiry | long | Yes | 到期时间（当天 0 点所对应的毫秒值） |
| strike | string | Yes | 行权价（小数位须和期权链一致，否则可能无数据，1.美股至少1位小数，有2位小数的可能 2. 港股个股期权固定2位小数 3. 港股指数期权无小数位） |

**返回** `com.tigerbrokers.stock.openapi.client.https.response.option.OptionDepthResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/option/OptionDepthResponse.java)

结构如下：

Java

    public class OptionDepthResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<OptionDepthItem> optionDepthItems;
    }

返回数据为盘中17个交易所的实时报价。 如果报价为0表示该交易所没有报价

返回数据可通过`OptionDepthResponse.getOptionDepthItems()`方法访问，返回`OptionDepthItem`对象列表，其中`com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionDepthItem` 属性如下:

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 标的股票代码 |
| expiry | long | 到期时间 |
| strike | string | strike price |
| right | string | PUT 或 CALL |
| timestamp | long | 数据时间戳 |
| ask | `List<OptionDepthOrderBook>` | 卖盘挂单数据 |
| bid | `List<OptionDepthOrderBook>` | 买盘挂单数据 |

OptionDepthOrderBook对象结构如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| price | double | 委托价 |
| code | string | 期权交易所 Code |
| timestamp | long | 交易所时间 |
| volume | int | 委托量 |

具体字段可通过对象的get方法，如`getSymbol()`进行访问, 或通过对象的`toString()`方法转换为字符串

**示例**

Java

    OptionCommonModel model = new OptionCommonModel();
    model.setSymbol("AAPL");
    model.setRight("PUT");
    model.setStrike("210.0");
    model.setExpiry("2024-06-28", TimeZoneId.NewYork);
    
    OptionDepthQueryRequest request = OptionDepthQueryRequest.of(model).market(Market.US);
    OptionDepthResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSONString(response);
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
    	"code": 0,
    	"data": [{\
    		"ask": [{\
    			"code": "CBOE",\
    			"price": 1.19,\
    			"volume": 10,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "BZX",\
    			"price": 1.19,\
    			"volume": 10,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "AMEX",\
    			"price": 1.19,\
    			"volume": 2,\
    			"timestamp": 1718654400000\
    		},\
    		{\
    			"code": "NSDQ",\
    			"price": 1.19,\
    			"volume": 2,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "BX",\
    			"price": 1.19,\
    			"volume": 2,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "PHLX",\
    			"price": 1.2,\
    			"volume": 54,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "BOX",\
    			"price": 1.2,\
    			"volume": 31,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "GEM",\
    			"price": 1.2,\
    			"volume": 24,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "MCRY",\
    			"price": 1.2,\
    			"volume": 24,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "MIAX",\
    			"price": 1.2,\
    			"volume": 24,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "EDGX",\
    			"price": 1.2,\
    			"volume": 23,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "EMLD",\
    			"price": 1.2,\
    			"volume": 18,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "ISE",\
    			"price": 1.2,\
    			"volume": 18,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "MPRL",\
    			"price": 1.2,\
    			"volume": 8,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "C2",\
    			"price": 1.2,\
    			"volume": 6,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "ARCA",\
    			"price": 1.2,\
    			"volume": 1,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "MEMX",\
    			"price": 0.0,\
    			"volume": 0,\
    			"timestamp": 1718654402000\
    		}],\
    		"bid": [{\
    			"code": "PHLX",\
    			"price": 1.12,\
    			"volume": 48,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "MIAX",\
    			"price": 1.12,\
    			"volume": 37,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "BX",\
    			"price": 1.12,\
    			"volume": 34,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "BOX",\
    			"price": 1.12,\
    			"volume": 32,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "CBOE",\
    			"price": 1.12,\
    			"volume": 29,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "MPRL",\
    			"price": 1.12,\
    			"volume": 22,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "GEM",\
    			"price": 1.12,\
    			"volume": 21,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "EDGX",\
    			"price": 1.12,\
    			"volume": 18,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "EMLD",\
    			"price": 1.12,\
    			"volume": 16,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "ISE",\
    			"price": 1.12,\
    			"volume": 15,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "C2",\
    			"price": 1.12,\
    			"volume": 10,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "BZX",\
    			"price": 1.12,\
    			"volume": 10,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "MCRY",\
    			"price": 1.12,\
    			"volume": 8,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "AMEX",\
    			"price": 1.12,\
    			"volume": 4,\
    			"timestamp": 1718654400000\
    		},\
    		{\
    			"code": "ARCA",\
    			"price": 1.12,\
    			"volume": 4,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "NSDQ",\
    			"price": 1.12,\
    			"volume": 4,\
    			"timestamp": 1718654399000\
    		},\
    		{\
    			"code": "MEMX",\
    			"price": 0.0,\
    			"volume": 0,\
    			"timestamp": 1718654402000\
    		}],\
    		"expiry": 1719547200000,\
    		"right": "PUT",\
    		"strike": "210.0",\
    		"timestamp": 1718654402000\
    	}],
    	"message": "success",
    	"sign": "tlxKbPzgJBN2Q2oUz8GBwpAJ/aUFlNrM3V/uh1fTWd2r3lHfD2TvTul/i6yBtvxR+G7gwfkpE7yoVVo74JacJPOA724zLdSkkHDuC5K2Q9WzIi/C1z0vdRZYtQSPpKsIrDSGc5g9D6m1IYz7HJNSeDa4a5WwyggDetNO86M1PeE=",
    	"success": true,
    	"timestamp": 1718712279180
    }

* * *

获取期权逐笔成交

[](./quote-option-java.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4)

------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：OptionTradeTickQueryRequest**

**说明**

获取期权逐笔成交数据，只支持美国市场期权，批量请求单次最多30条。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | string | Yes | 股票代码 |
| right | string | Yes | 看多或看空（call/put） |
| expiry | long | Yes | 到期时间（美国 NewYork 时间当天 0 点所对应的毫秒值） |
| strike | string | Yes | 行权价（小数位须和期权链一致，否则可能无数据，1.美股至少1位小数，有2位小数的可能 2. 港股个股期权固定2位小数 3. 港股指数期权无小数位） |

**返回** `com.tigerbrokers.stock.openapi.client.https.response.option.OptionTradeTickResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/option/OptionTradeTickResponse.java)

结构如下：

Java

    public class OptionTradeTickResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<OptionTradeTickItem> optionTradeTickItems;
    }

开盘前半小时可以取到前一个交易日的全部，开盘后是新一天的数据。

返回数据可通过`OptionTradeTickResponse.getOptionTradeTickItems()`方法访问，返回`OptionTradeTickItem`对象列表，其中`com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionTradeTickItem` 属性如下:

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 标的股票代码 |
| expiry | long | 到期时间 |
| strike | string | strike price |
| right | string | PUT 或 CALL |
| items | `List<TradeTickPoint>` | TradeTickPoint 对象列表，每个 TradeTickPoint 对象对应单条逐笔成交数据 |

TradeTickPoint对象结构如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| price | double | 成交价格 |
| time | long | 成交时间 |
| volume | long | 成交量 |

具体字段可通过对象的get方法，如`getSymbol()`进行访问, 或通过对象的`toString()`方法转换为字符串

**示例**

Java

    List<OptionCommonModel> modelList = new ArrayList<>();
    OptionCommonModel model1 = new OptionCommonModel();
    model1.setSymbol("AAPL");
    model1.setRight("PUT");
    model1.setStrike("185.0");
    model1.setExpiry("2024-03-08", TimeZoneId.NewYork);
    modelList.add(model1);
    
    OptionCommonModel model2 = new OptionCommonModel();
    model2.setSymbol("AAPL");
    model2.setRight("CALL");
    model2.setStrike("185.0");
    model2.setExpiry("2024-03-08", TimeZoneId.NewYork);
    modelList.add(model2);
    
    OptionTradeTickResponse response = client.execute(OptionTradeTickQueryRequest.of(modelList));
    
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSONString(response);
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
    	"code": 0,
    	"data": [{\
    		"expiry": 1709874000000,\
    		"items": [{\
    			"price": 2.63,\
    			"time": 1708698601086,\
    			"volume": 4\
    		}, {\
    			"price": 2.62,\
    			"time": 1708698602594,\
    			"volume": 6\
    		}, {\
    			"price": 2.73,\
    			"time": 1708698606317,\
    			"volume": 4\
    		}, {\
    			"price": 2.72,\
    			"time": 1708698607576,\
    			"volume": 38\
    		}, {\
    			"price": 2.72,\
    			"time": 1708698610488,\
    			"volume": 7\
    		}],\
    		"right": "put",\
    		"strike": "185.0",\
    		"symbol": "AAPL"\
    	}, {\
    		"expiry": 1709874000000,\
    		"items": [{\
    			"price": 2.98,\
    			"time": 1708698600473,\
    			"volume": 1\
    		}, {\
    			"price": 2.98,\
    			"time": 1708698601051,\
    			"volume": 5\
    		}, {\
    			"price": 2.98,\
    			"time": 1708698601051,\
    			"volume": 23\
    		}, {\
    			"price": 2.98,\
    			"time": 1708698601051,\
    			"volume": 5\
    		}, {\
    			"price": 2.99,\
    			"time": 1708698601051,\
    			"volume": 11\
    		}],\
    		"right": "call",\
    		"strike": "185.0",\
    		"symbol": "AAPL"\
    	}],
    	"message": "success",
    	"success": true,
    	"timestamp": 1708918385248
    }

* * *

获取期权K线

[](./quote-option-java.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83k%E7%BA%BF)

--------------------------------------------------------------------------------------------------------------

**对应的请求类：OptionKlineQueryV2Request**

**说明**

获取期权K线，批量请求单次最多30条。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | Market | Yes | 市场，支持 US、HK |
| option\_query | `List<OptionKlineModel>` | Yes | 期权K线查询条件列表，最大 30 |

`OptionKlineModel`参数结构如下

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | string | Yes | 股票代码 |
| right | string | Yes | 看多或看空（CALL/PUT） |
| expiry | long | Yes | 到期时间 |
| strike | string | Yes | 行权价（小数位须和期权链一致，否则可能无数据，1.美股至少1位小数，有2位小数的可能 2. 港股个股期权固定2位小数 3. 港股指数期权无小数位） |
| begin\_time | long | Yes | 开始时间 |
| end\_time | long | Yes | 结束时间 |
| period | string | No  | K线类型，取值范围(day:日K，1min:1分钟，5min:5分钟，30min:30分钟，60min:60分钟) |
| limit | int | No  | 分钟线返回最近记录数，默认 300，最大 1200。如果 limit 设置大于 1200，只会返回1200条数据。日K线暂不支持 |
| sort\_dir | string | No  | 排序方向，包括：升序，降序，排序方向枚举：[排序方向](./appendix-enum-java.md#sort-dir) |

**返回** `com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionKlineResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/option/OptionKlineResponse.java)

结构如下：

Java

    public class OptionKlineResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<OptionKlineItem> klineItems;
    }

返回数据可通过`OptionKlineResponse.getKlineItems()`方法访问，返回`OptionKlineItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionKlineItem` 属性如下:

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| period | string | 周期类型 |
| right | string | 看多或看空，取值CALL/PUT |
| strike | string | 行权价（小数位须和期权链一致，否则可能无数据，1.美股至少1位小数，有2位小数的可能 2. 港股个股期权固定2位小数 3. 港股指数期权无小数位） |
| expiry | long | 到期时间，毫秒 |
| items | `List<OptionKlinePoint>` | 包含OptionKlinePoint的List，OptionKlinePoint为k线数组，包含的具体数据见下文 |

OptionKlinePoint对象属性如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| high | double | 最高价 |
| low | double | 最低价 |
| open | double | 开盘价 |
| close | double | 收盘价 |
| time | long | k线时间 |
| volume | int | 成交量 |
| openInterest | int | 未平仓量（只有日K线有值） |

具体字段可通过对象的get方法，如`getSymbol()`进行访问, 或通过对象的`toString()`方法转换为字符串

**示例**

Java

    OptionKlineModel model = new OptionKlineModel();
    model.setSymbol("AAPL");
    model.setRight("CALL");
    model.setStrike("170.0");
    model.setExpiry("2024-06-28", TimeZoneId.NewYork);
    model.setBeginTime("2024-06-26", TimeZoneId.NewYork);
    model.setEndTime("2024-06-26 12:59:59", TimeZoneId.NewYork);
    
    model.setPeriod(OptionKType.min1.getValue());
    model.setLimit(10);
    model.setSortDir(SortDir.SortDir_Descend);
    OptionKlineQueryV2Request request = OptionKlineQueryV2Request.of(model).market(Market.US);
    
    OptionKlineResponse response = client.execute(request);
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
                "expiry": 1719547200000,\
                "items": [\
                    {\
                        "close": 43.13,\
                        "high": 43.13,\
                        "low": 43.13,\
                        "open": 43.13,\
                        "time": 1719419340000,\
                        "volume": 0\
                    },\
                    {\
                        "close": 43.13,\
                        "high": 43.13,\
                        "low": 43.13,\
                        "open": 43.13,\
                        "time": 1719419280000,\
                        "volume": 0\
                    },\
                    {\
                        "close": 43.13,\
                        "high": 43.13,\
                        "low": 43.13,\
                        "open": 43.13,\
                        "time": 1719419220000,\
                        "volume": 0\
                    },\
                    {\
                        "close": 43.13,\
                        "high": 43.13,\
                        "low": 43.13,\
                        "open": 43.13,\
                        "time": 1719419160000,\
                        "volume": 0\
                    },\
                    {\
                        "close": 43.13,\
                        "high": 43.13,\
                        "low": 43.13,\
                        "open": 43.13,\
                        "time": 1719419100000,\
                        "volume": 0\
                    },\
                    {\
                        "close": 43.13,\
                        "high": 43.13,\
                        "low": 43.13,\
                        "open": 43.13,\
                        "time": 1719419040000,\
                        "volume": 0\
                    },\
                    {\
                        "close": 43.13,\
                        "high": 43.13,\
                        "low": 43.13,\
                        "open": 43.13,\
                        "time": 1719418980000,\
                        "volume": 0\
                    },\
                    {\
                        "close": 43.13,\
                        "high": 43.13,\
                        "low": 43.13,\
                        "open": 43.13,\
                        "time": 1719418920000,\
                        "volume": 0\
                    },\
                    {\
                        "close": 43.13,\
                        "high": 43.13,\
                        "low": 43.13,\
                        "open": 43.13,\
                        "time": 1719418860000,\
                        "volume": 0\
                    },\
                    {\
                        "close": 43.13,\
                        "high": 43.13,\
                        "low": 43.13,\
                        "open": 43.13,\
                        "time": 1719418800000,\
                        "volume": 0\
                    }\
                ],\
                "period": "1min",\
                "right": "CALL",\
                "strike": "170.0",\
                "symbol": "AAPL"\
            }\
        ],
        "message": "success",
        "sign": "Hpb51+k2OzC8HmcstBV+bCLTbpflPKpR/AxXwCLd9nhhzuiZquPNGbNOhLYzzihJzRrmfCPWQeXM4ldMGLtbXUluLW79vcKBHdoPghENu+68Zod9dqzsH/InAXt444HOSsRXiubITZ+d9OWil+gvitjn9w7x4kn916KlT6R7hYg=",
        "success": true,
        "timestamp": 1719419362447
    }

* * *

获取期权分时数据

[](./quote-option-java.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E5%88%86%E6%97%B6%E6%95%B0%E6%8D%AE)

------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：OptionTimelineRequest**

**说明**

获取期权的分时数据。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| optionTimelineModels | `List<OptionTimelineModel>` | Yes | 期权列表 |
| market | Market | No  | 市场，默认 HK，仅支持 HK |

`OptionTimelineModel`参数结构如下：

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | string | Yes | 股票代码 |
| right | string | Yes | 看多或看空（CALL/PUT） |
| expiry | long | Yes | 到期时间 |
| strike | string | Yes | 行权价（小数位须和期权链一致，否则可能无数据，1.美股至少1位小数，有2位小数的可能 2. 港股个股期权固定2位小数 3. 港股指数期权无小数位） |

**返回**

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| right | string | 看多或看空（CALL/PUT） |
| expiry | long | 到期时间 |
| strike | string | 行权价 |
| preClose | double | 昨日收盘价 |
| openAndCloseTimeList | `List<List<Long>>` | 交易时间段列表 |
| minutes | `List<OptionTimelinePoint>` | 分时数组，字段参考下面说明 |

分时数据`OptionTimelinePoint`结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| volume | long | 成交量 |
| avgPrice | double | 平均成交价格 |
| price | double | 最新价格 |
| time | long | 当前分时时间 |

**示例**

Java

    OptionTimelineModel model1 = new OptionTimelineModel();
    model1.setSymbol("ALB.HK");
    model1.setExpiry(1753878054000L);
    model1.setStrike("117.50");
    model1.setRight("CALL");
    
    OptionTimelineModel model2 = new OptionTimelineModel();
    model2.setSymbol("LNI.HK");
    model2.setExpiry(1753878054000L);
    model2.setStrike("17.00");
    model2.setRight("PUT");
    
    OptionTimelineRequest request = OptionTimelineRequest.of(model1, model2);
    
    OptionTimelineResponse response = client.execute(request);
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
      "timestamp" : 1750822293909,
      "sign" : "rEiaFf1LYv32Kbu8C+AKpJ/Y9pjrux8usbFA3FFJUEP28EXHv+PnKX9RBtVLalkyrULNqDqS29zP9hF1OZAng7U9KWFQ1Gy/FcGdbypXRNeJxPgKefHt/Fe4rLweO/eWKE41ZprPZlUZX0fxfeMwqkwcTMfhpuu+HQ2/ocmMgeg=",
      "timelineItems" : [ {\
        "lang" : null,\
        "symbol" : "ALB.HK",\
        "expiry" : 1753878054000,\
        "right" : "CALL",\
        "strike" : "117.50",\
        "preClose" : 2.72,\
        "openAndCloseTimeList" : null,\
        "minutes" : [{\
          "price" : 3.4,\
          "avgPrice" : 3.5235946,\
          "time" : 1750822140000,\
          "volume" : 29\
        }, {\
          "price" : 3.4,\
          "avgPrice" : 3.5235946,\
          "time" : 1750822200000,\
          "volume" : 0\
        }, {\
          "price" : 3.4,\
          "avgPrice" : 3.5235946,\
          "time" : 1750822260000,\
          "volume" : 0\
        } ],\
        "account" : null\
      }, {\
        "lang" : null,\
        "symbol" : "LNI.HK",\
        "expiry" : 1753878054000,\
        "right" : "PUT",\
        "strike" : "17.00",\
        "preClose" : 1.28,\
        "openAndCloseTimeList" : null,\
        "minutes" : [ {\
          "price" : 1.21,\
          "avgPrice" : 1.21,\
          "time" : 1750822140000,\
          "volume" : 0\
        }, {\
          "price" : 1.21,\
          "avgPrice" : 1.21,\
          "time" : 1750822200000,\
          "volume" : 0\
        }, {\
          "price" : 1.21,\
          "avgPrice" : 1.21,\
          "time" : 1750822260000,\
          "volume" : 0\
        } ],\
        "account" : null\
      } ],
      "success" : true
    }

* * *

获取港股期权的代码

[](./quote-option-java.md#%E8%8E%B7%E5%8F%96%E6%B8%AF%E8%82%A1%E6%9C%9F%E6%9D%83%E7%9A%84%E4%BB%A3%E7%A0%81)

----------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：OptionSymbolRequest**

**说明**

获取港股期权的代码, 例如 00700 的代码为 TCH.HK。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | Market | Yes | 市场，只支持 HK |
| lang | string | No  | 语言支持: en\_US，zh\_CN，zh\_TW，默认: en\_US |

**返回** `com.tigerbrokers.stock.openapi.client.https.response.option.OptionSymbolResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/option/OptionSymbolResponse.java)

结构如下：

Java

    public class OptionSymbolResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<OptionSymbolItem> symbolItems;
    }

返回香港市场所有期权的symbol代码及底层资产标的集合

返回数据可通过`OptionSymbolResponse.getSymbolItems()`方法访问，返回`OptionSymbolItem`对象列表，其中`com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionSymbolItem` 属性如下:

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 期权四要素的 symbol |
| name | string | 标的名称 |
| underlyingSymbol | string | 底层资产标的代码 |

具体字段可通过对象的get方法，如`getSymbol()`进行访问, 或通过对象的`toString()`方法转换为字符串

**示例**

Java

    OptionSymbolRequest request = OptionSymbolRequest.newRequest(Market.HK, Language.en_US);
    OptionSymbolResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSONString(response);
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code": 0,
      "data": [\
        {\
          "name": "ALC",\
          "symbol": "ALC.HK",\
          "underlyingSymbol": "02600"\
        },\
        {\
          "name": "CRG",\
          "symbol": "CRG.HK",\
          "underlyingSymbol": "00390"\
        }\
      ],
      "message": "success",
      "sign": "NRvOxhF7cpEM9PS+Hofd6/BduEddep0sUlnYq9o9fPUwcZmAj3spI/D2wXu8L/eZSxvWhSfjnB3BL8y7mrpvqY3m9BGeZhf24ZoA0lbY8YXyQ5JjXa0VHWieUmCItoR9E195Nsr2sWCoawJhz7+yaMFioWEe8VThtGrYMiTYnUE=",
      "success": true,
      "timestamp": 1719401951582
    }

* * *

期权指标计算

[](./quote-option-java.md#%E6%9C%9F%E6%9D%83%E6%8C%87%E6%A0%87%E8%AE%A1%E7%AE%97)

----------------------------------------------------------------------------------------------------------------------

**说明**

计算所选期权的各类指标。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| client | object | Yes | SDK Htttp client |
| symbol | string | Yes | 股票代码 |
| right | string | Yes | 看多或看空（CALL/PUT） |
| strike | string | Yes | 行权价 |
| expiry | string | Yes | 到期时间（格式：yyyy-MM-dd） |
| underlyingSymbol | string | No  | 底层资产标的，默认为symbol的值 |

**返回**

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| delta | double | 希腊字母 delta |
| gamma | double | 希腊字母 gamma |
| theta | double | 希腊字母 theta |
| vega | double | 希腊字母 vega |
| insideValue | double | 内在价值 |
| timeValue | double | 时间价值 |
| leverage | double | 杠杆率 |
| openInterest | int | 未平仓量 |
| historyVolatility | double | 历史波动率，百分比数值 |
| premiumRate | double | 溢价率，百分比数值 |
| profitRate | double | 买入盈利率，百分比数值 |
| volatility | double | 隐含波动率，百分比数值 |

**示例**

Java

    OptionFundamentals optionFundamentals = OptionCalcUtils.getOptionFundamentals(client,"BABA", "CALL", "205.0", "2019-11-01");
    System.out.println(JSONObject.toJSONString(optionFundamentals));

**返回示例**

JSON

    {
    	"delta": 0.8573062699731591,
    	"gamma": 0.05151538284065261,
    	"historyVolatility": 24.38,  //百分比形式，表示为 24.38%
    	"insideValue": 4.550000000000011,
    	"leverage": 30.695960907449216,
    	"openInterest": 35417.0,
    	"premiumRate": 0.18619306788885054, //百分比形式，表示为 0.186%
    	"profitRate": 47.138051059662665, //百分比形式，表示为 47.138%
    	"rho": 1.1107261502375654,
    	"theta": -0.17927469728943862,
    	"timeValue": 0.32499999999998863,
    	"vega": 0.034473845504081974,
    	"volatility": 28.62548828125  //百分比形式，表示为 28.62%
    }

  

获取期权分析数据

[](./quote-option-java.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E5%88%86%E6%9E%90%E6%95%B0%E6%8D%AE)

------------------------------------------------------------------------------------------------------------------------------------------

  

**对应的请求类：`OptionAnalysisRequest`**

**说明**

查询期权分析数据。

* * *

\*\* 参数 \*\*

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `List<OptionAnalysisModel>` | Yes | 期权分析查询项列表 |
| market | `Market` | No  | 市场，默认 `US` |

单项参数（`OptionAnalysisModel`）

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | string | Yes | 标的代码（如 `AAPL`） |
| period | string | Yes | 周期（如 `52week`，或 `OptionAnalysisPeriod`） |
| requireVolatilityList | Boolean | No  | 是否返回波动率列表数据（历史波动率、隐含波动率等时间序列） |

* * *

\*\* 返回 \*\*

`OptionAnalysisResponse`

结构如下：

Java

    public class OptionAnalysisResponse extends TigerResponse {
    
      @JSONField(name = "data")
      private List<OptionAnalysisItem> optionAnalysisItems;
    }
    
    public class OptionAnalysisItem extends ApiModel {
    
      private String symbol;
      private Double impliedVol30Days;
      private Double hisVolatility;
      private Double ivHisVRatio;
      private Double callPutRatio;
      private ImpliedVolMetric impliedVolMetric;
      private List<VolatilityItem> volatilityList;
    }
    
    public class ImpliedVolMetric implements Serializable {
    
    
      private String period;
      private Double percentile;
      private Double rank;
    }
    
    public class VolatilityItem implements Serializable {
    
      private Double impliedVol;
      private Double percentile;
      private Double rank;
      private Double hisVolatility;
      private Long timestamp;
    }

  

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| impliedVol30Days | double | 标的资产的隐含波动率。 对期权链上部分期权的隐含波动率进行综合加权计算，得到的值即为该标的资产的 IV。它反映的是未来30天内期权链整体的波动情况，这个数据对正股也有参考作用。 |
| hisVolatility | double | 标的资产的历史波动率。 它反映的是过去30天内，标的资产的实际波动情况，用于衡量标的资产偏离其平均价格的程度。 |
| ivHisVRatio | double | 隐含波动率/历史波动率 比值 |
| callPutRatio | double | Call/Put 比值 |
| percentile | double | 隐含波动率百分位。IV Percentile也是一个相对的指标， 统计过去一年内，有多少天的隐含波动率比现在低。计算公式：<br><br>IV Percentile = 1年内低于当前IV的天数/ 交易天数<br><br>IV Percentile的数值范围是在0%到100%之间波动； 当IV Percentile为0%时，表示过去1年内有0%的交易日的 IV 低于当前 IV； 当IV Percentile 为100%时，表示过去1年内有100%的交易日的 IV 低于当前 IV。 |
| rank | double | 隐含波动率排名。IV Rank 是一个相对的指标， 以该标的资产过去一年内的最高和最低IV值为标准，计算现在IV的相对位置。计算公式：<br><br>IV Rank = (当前IV - 1年内最低IV) / (1年内最高IV - 1年内最低IV)<br><br>IV Rank的数值范围是在0到1之间波动； 当IV Rank为0时，表示当前 IV处于近1年内的较低范围； 当IV Rank 为1时，表示当前IV处于近1年内较高范围 |

`VolatilityItem` 对象结构（`requireVolatilityList=true` 时返回）：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| impliedVol | Double | 隐含波动率 |
| percentile | Double | IV百分位 |
| rank | Double | IV排名 |
| hisVolatility | Double | 历史波动率 |
| timestamp | Long | 时间戳，精确到毫秒 |

* * *

### 

示例

[](./quote-option-java.md#%E7%A4%BA%E4%BE%8B)

Java

    List<OptionAnalysisModel> items = new ArrayList<>();
    items.add(new OptionAnalysisModel("AAPL", OptionAnalysisPeriod.FIFTY_TWO_WEEK));
    items.add(new OptionAnalysisModel("TSLA", OptionAnalysisPeriod.FIFTY_TWO_WEEK));
    OptionAnalysisRequest request = OptionAnalysisRequest.newRequest(items, Market.US);
    OptionAnalysisResponse response =
          client.execute(request);
    
    // 返回波动率列表数据
    OptionAnalysisRequest requestWithVolList =
          OptionAnalysisRequest.of("AAPL", OptionAnalysisPeriod.FIFTY_TWO_WEEK, true, Market.US);
    OptionAnalysisResponse responseWithVolList =
          client.execute(requestWithVolList);

* * *

### 

返回示例

[](./quote-option-java.md#%E8%BF%94%E5%9B%9E%E7%A4%BA%E4%BE%8B)

JSON

    [\
    {\
    "callPutRatio": 0.6,\
    "hisVolatility": 0.1967,\
    "impliedVol30Days": 0.3071,\
    "impliedVolMetric": {\
      "percentile": 0.527363184079602,\
      "period": "52week",\
      "rank": 0.18213875790384876\
    },\
    "ivHisVRatio": 1.5617,\
    "symbol": "AAPL"\
    },\
    {\
    "callPutRatio": 0,\
    "hisVolatility": 0.3603,\
    "impliedVol30Days": 0.5162,\
    "impliedVolMetric": {\
      "percentile": 0.08,\
      "period": "52week",\
      "rank": 0.04194153521422974\
    },\
    "ivHisVRatio": 1.4328,\
    "symbol": "TSLA"\
    }\
    ]

`requireVolatilityList=true` 时返回示例：

JSON

    [\
    {\
    "callPutRatio": 0.6,\
    "hisVolatility": 0.1967,\
    "impliedVol30Days": 0.3071,\
    "impliedVolMetric": {\
      "percentile": 0.527,\
      "period": "52week",\
      "rank": 0.182\
    },\
    "ivHisVRatio": 1.5617,\
    "symbol": "AAPL",\
    "volatilityList": [\
      {"impliedVol": 0.3012, "percentile": 0.512, "rank": 0.175, "hisVolatility": 0.1923, "timestamp": 1709856000000},\
      {"impliedVol": 0.2985, "percentile": 0.498, "rank": 0.168, "hisVolatility": 0.1901, "timestamp": 1709769600000}\
    ]\
    }\
    ]
