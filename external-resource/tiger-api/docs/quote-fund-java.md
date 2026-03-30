# 基金

获取基金代码列表

[](./quote-fund-java.md#%E8%8E%B7%E5%8F%96%E5%9F%BA%E9%87%91%E4%BB%A3%E7%A0%81%E5%88%97%E8%A1%A8)

----------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FundSymbolRequest**

**说明**

获取所有基金代码列表。

**参数**

无

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.fund.FundSymbolResponse` [source](https://github.com/tigerfintech/openapi-java-sdk/blob/71631121961002aa1dadf601e922a494d14f5ed0/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/fund/FundSymbolResponse.java)

具体结构如下

Java

    public class FundSymbolResponse extends TigerResponse {
      @JSONField(name = "data")
      private List<String> symbols;
    }

返回数据可以通过`FundSymbolResponse.getSymbols()`方法调用，结果为包含返回基金代码数据的List

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    FundSymbolResponse response = client.execute(FundSymbolRequest.newRequest());
    if (response.isSuccess()) {
      ApiLogger.info(JSONObject.toJSONString(response));
    } else {
      ApiLogger.info("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
        "code":0,
        "data":[\
            "IE00B11XZ988.USD",\
            "IE00B7SZLL34.SGD",\
            "LU0790902711.USD",\
            "LU0476943708.HKD",\
            "LU0098860793.USD",\
            "SG9999014039.USD"\
            // ....\
        ],
        "message":"success",
        "sign":"zCIL+uNR083O4FoEUYKudVcstKCzMlZQD2WxRLhjf7pj2qaxhGCaJUtFY95zsmvK0kEI7YL/MyyTMsQU8dfd4/oF9FdDQdWWZRQxAiyJe8+aQuBdRg+BxN8xOl3TahTx2pPklyAxQIMKRJBU3ZgY9OPb040kucXTWgNyP8O643w=",
        "success":true,
        "timestamp":1690858442132
    }

* * *

获取基金合约信息

[](./quote-fund-java.md#%E8%8E%B7%E5%8F%96%E5%9F%BA%E9%87%91%E5%90%88%E7%BA%A6%E4%BF%A1%E6%81%AF)

----------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FundContractsRequest**

**说明**

批量获取基金的合约信息。

**输入参数**

`com.tigerbrokers.stock.openapi.client.https.request.fund.FundContractsRequest`

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `List<String>` | Yes | 基金代码列表 如："IE00B11XZ988.USD" / "LU0790902711.USD" |
| lang | string | No  | 语言支持: zh\_CN,zh\_TW,en\_US, 默认: en\_US |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.fund.FundContractsResponse`

其中数据项字段如下 `com.tigerbrokers.stock.openapi.client.https.domain.fund.item.FundContractItem`

| 名称  | 示例  | 说明  |
| --- | --- | --- |
| symbol | IE00B464Q616.USD | 基金代码，后缀为货币 |
| name | ASIA STRATEGIC INTEREST BOND FUND "E" (USD) INC MONTHLY | 基金名称 |
| companyName | PIMCO Global Advisors (Ireland) Limited | 基金名称 |
| market | US  | 市场 /US/HK/CN |
| secType | FUND | 合约类别 |
| currency | USD | USD/HKD/CNH |
| tradeable | true | 是否可交易 |
| subType | Fixed Income | 子类别 |
| dividendType | INC | 分红类型 |
| tigerVault | false | 是否为老虎钱袋子 |

**示例**

Java

    List<String> symbols = new ArrayList<>();
    symbols.add("IE00B11XZ988.USD");
    symbols.add("LU0476943708.HKD");
    FundContractsRequest request = FundContractsRequest.newRequest(symbols, Language.zh_CN);
    FundContractsResponse response = client.execute(request);
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
                "companyName":"太平洋全球顾问(爱尔兰)有限公司",\
                "currency":"USD",\
                "dividendType":"ACC",\
                "market":"MF",\
                "name":"PIMCO总回报债券基金 E Acc",\
                "secType":"FUND",\
                "subType":"Fixed Income",\
                "symbol":"IE00B11XZ988.USD",\
                "tigerVault":false,\
                "tradeable":true\
            },\
            {\
                "companyName":"富兰克林邓普顿投资",\
                "currency":"HKD",\
                "dividendType":"INC",\
                "market":"MF",\
                "name":"邓普顿环球总收益基金A (Mdis)HKD",\
                "secType":"FUND",\
                "subType":"Fixed Income",\
                "symbol":"LU0476943708.HKD",\
                "tigerVault":false,\
                "tradeable":true\
            }\
        ],
        "message":"success",
        "sign":"yx8Wv8c1VlcdyWunteBWpW+xdQQ8pBxN4LGCi/SS230saV26rWjV4xupE04uSmjOBnXz+IUlMGEsgHeIXI18XuZh89n4aeC4djkDbmFzchSCTtgaN8icav8H5SKYUuzrkriKQPsZOUpCREJyDSplvVwnhC+yJIK81M7XILgDTMU=",
        "success":true,
        "timestamp":1690871506499
    }

* * *

获取基金最新行情

[](./quote-fund-java.md#%E8%8E%B7%E5%8F%96%E5%9F%BA%E9%87%91%E6%9C%80%E6%96%B0%E8%A1%8C%E6%83%85)

----------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FundQuoteRequest**

**说明**

获取基金最新行情。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `List<String>` | Yes | 基金代码，上限为500个 |

**返回** `com.tigerbrokers.stock.openapi.client.https.response.fund.FundQuoteResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/fund/FundQuoteResponse.java)

结构如下：

Java

    public class FundQuoteResponse extends TigerResponse {
      @JSONField(name = "data")
      private List<FundQuoteItem> quoteItems;
    }

返回数据可通过`FundQuoteResponse.getQuoteItems()`方法访问，返回`FundQuoteItem`对象列表，其中items属性列表的`com.tigerbrokers.stock.openapi.client.https.domain.fund.item.FundQuoteItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 标的代码 |
| close | float | 收市价 |
| timestamp | long | 时间戳 |

具体字段可通过对象的get方法，如`getSymbol()`进行访问

**示例**

Java

        List<String> symbols = new ArrayList<>();
        symbols.add("IE00B11XZ988.USD");
        symbols.add("LU0476943708.HKD");
    
        FundQuoteRequest request = FundQuoteRequest.newRequest(symbols);
        FundQuoteResponse response = client.execute(request);
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
                "close":25.33,\
                "symbol":"IE00B11XZ988.USD",\
                "timestamp":1690732800000\
            },\
            {\
                "close":5.36,\
                "symbol":"LU0476943708.HKD",\
                "timestamp":1690732800000\
            }\
        ],
        "message":"success",
        "sign":"lRsJPcIf/NfEoTAKH+cR0S1F3dSNuxntCBZ13BaZKVjrjSbUZEg2epbeaqWrxzxnnoXSl66dbrHuQ7+F3TKmYIjjpO0ZcZbhLMdclO0AQWRrrCtpmytMIQSuLsGWqhO+N0mNAVgMCBGWB8Af4kZOZw3K6IPP9abTLKeteDH5zNk=",
        "success":true,
        "timestamp":1690873154646
    }

* * *

获取基金历史行情

[](./quote-fund-java.md#%E8%8E%B7%E5%8F%96%E5%9F%BA%E9%87%91%E5%8E%86%E5%8F%B2%E8%A1%8C%E6%83%85)

----------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FundHistoryQuoteRequest**

**说明**

获取基金历史行情。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `List<String>` | Yes | 基金代码,上限为500个 |
| begin\_time | Long | Yes | 开始时间戳，单位:毫秒(ms) |
| end\_time | Long | Yes | 结束时间戳，单位:毫秒(ms) |
| limit | integer | No  | 请求返回单个标的数据量 |

**返回** `com.tigerbrokers.stock.openapi.client.https.response.fund.FundHistoryQuoteResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/fund/FundHistoryQuoteResponse.java)

结构如下：

Java

    public class FundHistoryQuoteResponse extends TigerResponse {
      @JSONField(name = "data")
      private List<FundHistoryQuoteItem> quoteItems;
    }

返回数据可通过`FundHistoryQuoteResponse.getQuoteItems()`方法访问，返回`FundHistoryQuoteItem`对象列表，其中items属性列表的`com.tigerbrokers.stock.openapi.client.https.domain.fund.item.FundHistoryQuoteItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 标的代码 |
| items | `List<FundQuotePoint>` | 历史行情列表 |

`FundQuotePoint`属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| nav | double | 净值  |
| time | long | 时间戳 |

具体字段可通过对象的get方法，如`getNav()`进行访问

**示例**

Java

        List<String> symbols = new ArrayList<>();
        symbols.add("IE00B11XZ988.USD");
        symbols.add("LU0476943708.HKD");
    
        FundHistoryQuoteRequest request = FundHistoryQuoteRequest.newRequest(symbols);
        request.beginTime(DateUtils.getTimestamp("2023-07-01", TimeZoneId.Shanghai));
        request.endTime(DateUtils.getTimestamp("2023-07-26", TimeZoneId.Shanghai));
        request.limit(5);
        FundHistoryQuoteResponse response = client.execute(request);
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
                        "nav":25.38,\
                        "time":1690300800000\
                    },\
                    {\
                        "nav":25.31,\
                        "time":1690214400000\
                    },\
                    {\
                        "nav":25.34,\
                        "time":1690128000000\
                    },\
                    {\
                        "nav":25.37,\
                        "time":1689868800000\
                    },\
                    {\
                        "nav":25.36,\
                        "time":1689782400000\
                    }\
                ],\
                "symbol":"IE00B11XZ988.USD"\
            },\
            {\
                "items":[\
                    {\
                        "nav":5.39,\
                        "time":1690300800000\
                    },\
                    {\
                        "nav":5.38,\
                        "time":1690214400000\
                    },\
                    {\
                        "nav":5.39,\
                        "time":1690128000000\
                    },\
                    {\
                        "nav":5.38,\
                        "time":1689868800000\
                    },\
                    {\
                        "nav":5.4,\
                        "time":1689782400000\
                    }\
                ],\
                "symbol":"LU0476943708.HKD"\
            }\
        ],
        "message":"success",
        "sign":"uG/+KZ4w5Rzroex9jQECO57WH5rv0HlkRY3O+otB3nyAS3+L3+owhfBsowNkKABJn7GdIw8VvBgBnMZXW2BdfLaq50S99QwZ4OAW0oxeA708QMdoB/BN3O0CReo+ztgDVpJVyImQDJnOHgsOaqyqzc7cNoDwnc0e2h1ETv6JZtw=",
        "success":true,
        "timestamp":1690875443405
    }
