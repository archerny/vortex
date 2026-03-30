# 窝轮牛熊证

轮证筛选器

[](./quote-warrant-java.md#%E8%BD%AE%E8%AF%81%E7%AD%9B%E9%80%89%E5%99%A8)

-------------------------------------------------------------------------------------------------------------

**对应的请求类：WarrantFilterRequest**

**说明**

获取窝轮牛熊证行情列表数据，支持按不同字段排序和筛选轮证。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | string | Yes | 正股股票代码 |
| page | int | No  | 页码，从0开始，默认为0 |
| page\_size | int | No  | 每页数量，默认50 |
| sort\_field\_name | string | No  | 排序字段，默认expireDate（参照返回对象WarrantItem的字段） |
| sort\_dir | SortDir | No  | 排序顺序，枚举SortDir\_Ascend/SortDir\_Descend ,默认SortDir\_Ascend |
| warrant\_type | `Set<Integer>` | No  | 类型WarrantType（1:Call, 2: Put, 3: Bull, 4: Bear, 0: All），默认全部 |
| issuer\_name | string | No  | 发行商（参照返回FilterBounds实例的issuerName字段），默认全部 |
| expire\_ym | string | No  | 到期日，格式为\_yyyy-MM\_ |
| state | int | No  | 状态，0 全部, 1 正常, 2 终止交易, 3 等待上市，默认0 |
| in\_out\_price | `Set<Integer>` | No  | 1：价内（包含平值），-1：价外 |
| lot\_size | `Set<Integer>` | No  | 每手股数 |
| entitlement\_ratio | `Set<Double>` | No  | 换股比率 |
| strike | `Range<Double>` | No  | 行权价区间 |
| effective\_leverage | `Range<Double>` | No  | 有效杠杆区间 |
| leverage\_ratio | `Range<Double>` | No  | 杠杆比例区间 |
| call\_price | `Range<Double>` | No  | 回收价区间 |
| volume | `Range<Long>` | No  | 成交量区间 |
| premium | `Range<Double>` | No  | 溢价区间 |
| outstanding\_ratio | `Range<Double>` | No  | 街货比区间 |
| implied\_volatility | `Range<Double>` | No  | 隐含波动率区间 |

**返回** `com.tigerbrokers.stock.openapi.client.https.response.option.WarrantFilterResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/option/WarrantFilterResponse.java)

结构如下：

Java

    public class WarrantFilterResponse extends TigerResponse {
      @JSONField(name = "data")
      private WarrantFilterItem item;
    }

返回数据可通过`WarrantFilterResponse.getItem()`方法访问，返回`WarrantFilterItem`对象，其中`com.tigerbrokers.stock.openapi.client.https.domain.option.item.WarrantFilterItem` 属性如下:

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| page | int | 页码  |
| totalPage | int | 总页数 |
| totalCount | int | 数据总数 |
| bounds | FilterBounds | 请求可设置的过滤条件，说明见下文 |
| items | `List<WarrantItem>` | 列表，包含WarrantItem对象，轮证数据，说明见下文 |

`FilterBounds`对象结构：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| issuerName | `List<String>` | 发行商名称 |
| expireDate | `List<String>` | 到期日 |
| lotSize | `List<Integer>` | 每手股数 |
| entitlementRatio | `List<Double>` | 换股比率 |
| leverageRatio | `Range<Double>` | 杠杆比率范围 |
| strike | `Range<Double>` | 行权价范围 |
| premium | `Range<Double>` | 溢价范围 |
| outstandingRatio | `Range<Double>` | 街货比范围 |
| impliedVolatility | `Range<Double>` | 隐含波动率范围 |
| effectiveLeverage | `Range<Double>` | 有效杠杆范围 |
| callPrice | `Range<Double>` | 回收价范围 |

`WarrantItem`对象结构：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 标的股票代码 |
| name | string | 标的名称 |
| type | WarrantType | 类型，1：认购，2：认沽，3：牛证，4：熊证 |
| secType | string | 合约类型，窝轮：WAR/牛熊证：IOPT |
| market | string | 市场，HK |
| entitlementRatio | double | 换股比率 |
| entitlementPrice | double | 换股价 |
| premium | double | 溢价  |
| breakevenPoint | double | 到期盈亏平衡点 |
| callPrice | double | 回收价（仅牛熊证） |
| beforeCallLevel | double | 距回收价（百分比，比如**0.196875**，含义为19.6875%） |
| expireDate | string | 到期日 |
| lastTradingDate | string | 最后交易日 |
| state | WarrantState | 状态，1 正常, 2 终止交易, 3 等待上市 |
| changeRate | double | 涨跌幅 |
| change | double | 涨跌额 |
| latestPrice | double | 最新价 |
| volume | long | 成交量 |
| outstandingRatio | double | 街货比 |
| lotSize | int | 每手股数 |
| strike | double | 行权价 |
| inOutPrice | double | 价内（大于0）/价外（小于0） |
| delta | double | 对冲值 |
| leverageRatio | double | 杠杆比率 |
| effectiveLeverage | double | 有效杠杆 |
| impliedVolatility | double | 隐含波动率 |

具体字段可通过对象的get方法，如`getSymbol()`进行访问

**示例**

Java

        WarrantFilterRequest request = WarrantFilterRequest.newRequest("00700");
        request.lang(Language.zh_CN);
        request.sortFieldName("expireDate");
        request.sortDir(SortDir.SortDir_Descend);
        request.warrantType(WarrantType.Bull);
        request.issuerName("高盛");
        request.strike(300.0, 320.0);
        request.pageSize(10);
        WarrantFilterResponse response = client.execute(request);
        if (response.isSuccess()) {
          System.out.println(JSONObject.toJSONString(response.getItem()));
        } else {
          System.out.println("response error:" + response.getMessage());
        }

**返回示例**

JSON

    {
        "code":0,
        "data":{
            "bounds":{
                "callPrice":{
                    "max":520,
                    "min":113.96
                },
                "effectiveLeverage":{
                    "max":16.116,
                    "min":-14.076
                },
                "entitlementRatio":[\
                    47.483,\
                    92.166,\
                    94.967,\
                    100,\
                    460.829,\
                    474.834,\
                    485.437,\
                    500\
                ],
                "expireDate":[\
                    "2026-01",\
                    "2025-12",\
                    "2025-08",\
                    "2024-12",\
                    "2024-08",\
                    "2024-07",\
                    "2024-06",\
                    "2024-04",\
                    "2024-03",\
                    "2024-02",\
                    "2024-01",\
                    "2023-12",\
                    "2023-11",\
                    "2023-10",\
                    "2023-09",\
                    "2023-08",\
                    "2023-07",\
                    "2023-06",\
                    "2023-05",\
                    "2023-04",\
                    "2023-03",\
                    "2023-02"\
                ],
                "impliedVolatility":{
                    "max":344.093,
                    "min":0
                },
                "issuerName":[\
                    "东亚",\
                    "法巴",\
                    "法兴",\
                    "高盛",\
                    "国君",\
                    "海通",\
                    "花旗",\
                    "汇丰",\
                    "麦银",\
                    "摩利",\
                    "摩通",\
                    "瑞通",\
                    "瑞信",\
                    "瑞银",\
                    "星展",\
                    "中银"\
                ],
                "leverageRatio":{
                    "max":823.452604,
                    "min":1.372409
                },
                "lotSize":[\
                    1000,\
                    5000,\
                    10000,\
                    50000\
                ],
                "outstandingRatio":{
                    "max":1,
                    "min":0
                },
                "premium":{
                    "max":0.848338,
                    "min":-0.340153
                },
                "strike":{
                    "max":717.11,
                    "min":111.301
                }
            },
            "items":[\
                {\
                    "amount":391370,\
                    "beforeCallLevel":0.221875,\
                    "breakevenPoint":401.2,\
                    "callPrice":320,\
                    "change":0.033,\
                    "changeRate":0.244444,\
                    "entitlementPrice":84,\
                    "entitlementRatio":500,\
                    "expireDate":"2024-03-28",\
                    "inOutPrice":0.232661,\
                    "lastTradingDate":"2024-03-27",\
                    "latestPrice":0.168,\
                    "leverageRatio":4.654762,\
                    "lotSize":5000,\
                    "market":"HK",\
                    "name":"腾讯高盛四三牛B.C",\
                    "outstandingRatio":0.0023,\
                    "premium":0.026087,\
                    "secType":"IOPT",\
                    "state":"Normal",\
                    "strike":"317.2",\
                    "symbol":"68723",\
                    "type":"Bull",\
                    "volume":2460000\
                },\
                {\
                    "amount":341700,\
                    "beforeCallLevel":0.26129,\
                    "breakevenPoint":402.7,\
                    "callPrice":310,\
                    "change":0.035,\
                    "changeRate":0.224359,\
                    "entitlementPrice":95.5,\
                    "entitlementRatio":500,\
                    "expireDate":"2024-03-28",\
                    "inOutPrice":0.272786,\
                    "lastTradingDate":"2024-03-27",\
                    "latestPrice":0.191,\
                    "leverageRatio":4.094241,\
                    "lotSize":5000,\
                    "market":"HK",\
                    "name":"腾讯高盛四三牛A.C",\
                    "outstandingRatio":0.0111,\
                    "premium":0.029923,\
                    "secType":"IOPT",\
                    "state":"Normal",\
                    "strike":"307.2",\
                    "symbol":"68722",\
                    "type":"Bull",\
                    "volume":1870000\
                }\
            ],
            "page":0,
            "totalCount":2,
            "totalPage":1
        },
        "message":"success",
        "sign":"l8TsgnaHBThS1X3oQKyLAt+rKEqnY/bW4S/e2U0o5Zmvpr0NgcR86cbWXHSwr1eNIh9ffR0hPlE1j6cbZp2ClrUzDvBT/rIu+mSfIZDg7qTZ9I340VTlk1pIWQzc28Iickx+pzVd0k9tMsIF/hnoiBXfpiyywYa7pa/pLhIcHo8=",
        "success":true,
        "timestamp":1676522644671
    }

* * *

获取轮证行情

[](./quote-warrant-java.md#%E8%8E%B7%E5%8F%96%E8%BD%AE%E8%AF%81%E8%A1%8C%E6%83%85)

-----------------------------------------------------------------------------------------------------------------------

**对应的请求类：WarrantQuoteRequest**

**说明**

获取窝轮牛熊证实时行情。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `List<String>` | Yes | 轮证标的代码，上限为50个 |

**返回** `com.tigerbrokers.stock.openapi.client.https.response.option.WarrantQuoteResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/option/WarrantQuoteResponse.java)

结构如下：

Java

    public class WarrantQuoteResponse extends TigerResponse {
      @JSONField(name = "data")
      private WarrantQuoteItem item;
    }

返回数据可通过`TigerResponse.getItem()`方法访问，返回`WarrantQuoteItem`对象，其中items属性列表的`com.tigerbrokers.stock.openapi.client.https.domain.option.item.WarrantQuote` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 标的代码 |
| name | string | 标的名称 |
| exchange | string | 交易所 |
| market | string | 市场  |
| secType | string | 合约类型 |
| currency | string | 币种  |
| expiry | string | 到期日 yyyy-MM-dd |
| strike | string | 行权价 |
| right | string | 方向 (PUT/CALL) |
| multiplier | double | 每手数量 |
| lastTradingDate | long | 最后交易日时间戳 |
| entitlementRatio | double | 换股比率 |
| entitlementPrice | double | 换股价 |
| minTick | double | 股价最小变动单位 |
| listingDate | long | 上市日期的时间戳 |
| callPrice | double | 回收价\*（仅牛熊证）\* |
| halted | HaltedStatus | 是否停牌。0: 正常 3: 停牌 4: 退市 |
| underlyingSymbol | string | 底层资产代码 |
| timestamp | long | 时间戳 |
| latestPrice | double | 最新价格 |
| preClose | double | 前一交易日收盘价 |
| open | double | 开盘价 |
| high | double | 最高价 |
| low | double | 最低价 |
| volume | long | 成交量 |
| amount | double | 成交额 |
| premium | double | 溢价  |
| outstandingRatio | double | 街货比 |
| impliedVolatility | double | 隐含波动率（仅窝轮支持） |
| inOutPrice | double | 价内/价外 |
| delta | double | 对冲值（仅窝轮支持） |
| leverageRatio | double | 杠杆率 |
| breakevenPoint | double | 到期盈亏平衡点 |

具体字段可通过对象的get方法，如`getSymbol()`进行访问

**示例**

Java

        List<String> symbols = new ArrayList<>();
        symbols.add("68723");
        symbols.add("68722");
        WarrantQuoteRequest request = WarrantQuoteRequest.newRequest(symbols);
        request.lang(Language.zh_CN);
        WarrantQuoteResponse response = client.execute(request);
        if (response.isSuccess()) {
          System.out.println(JSONObject.toJSONString(response));
        } else {
          System.out.println("response error:" + response.getMessage());
        }

**返回示例**

JSON

    {
        "code":0,
        "data":{
            "items":[\
                {\
                    "amount":391370,\
                    "breakevenPoint":401.2,\
                    "callPrice":320,\
                    "currency":"HKD",\
                    "entitlementPrice":84,\
                    "entitlementRatio":500,\
                    "exchange":"SEHK",\
                    "expiry":"2024-03-28",\
                    "halted":"Normal",\
                    "high":0.171,\
                    "inOutPrice":0.232661,\
                    "lastTradingDate":1711468800000,\
                    "latestPrice":0.168,\
                    "leverageRatio":4.654762,\
                    "listingDate":1673280000000,\
                    "low":0.142,\
                    "market":"HK",\
                    "minTick":0.001,\
                    "multiplier":5000,\
                    "name":"腾讯高盛四三牛B.C",\
                    "open":0.142,\
                    "outstandingRatio":0.0023,\
                    "preClose":0.135,\
                    "premium":0.026087,\
                    "right":"CALL",\
                    "secType":"IOPT",\
                    "strike":"317.2",\
                    "symbol":"68723",\
                    "timestamp":1676521860314,\
                    "underlyingSymbol":"00700",\
                    "volume":2460000\
                },\
                {\
                    "amount":341700,\
                    "breakevenPoint":402.7,\
                    "callPrice":310,\
                    "currency":"HKD",\
                    "entitlementPrice":95.5,\
                    "entitlementRatio":500,\
                    "exchange":"SEHK",\
                    "expiry":"2024-03-28",\
                    "halted":"Normal",\
                    "high":0.193,\
                    "inOutPrice":0.272786,\
                    "lastTradingDate":1711468800000,\
                    "latestPrice":0.191,\
                    "leverageRatio":4.094241,\
                    "listingDate":1673280000000,\
                    "low":0.172,\
                    "market":"HK",\
                    "minTick":0.001,\
                    "multiplier":5000,\
                    "name":"腾讯高盛四三牛A.C",\
                    "open":0.172,\
                    "outstandingRatio":0.0111,\
                    "preClose":0.156,\
                    "premium":0.029923,\
                    "right":"CALL",\
                    "secType":"IOPT",\
                    "strike":"307.2",\
                    "symbol":"68722",\
                    "timestamp":1676521860314,\
                    "underlyingSymbol":"00700",\
                    "volume":1870000\
                }\
            ]
        },
        "message":"success",
        "sign":"af55ER5QJh0jv3iELe01l+Kfw/qt6DJTGOaLeMgJMgqZ/xWH+ELVfHEEscnTa4YkQE3DDJN78Nln7gUFIaMK9NeoC2JNgT3gUUiDEgHhaWmSGerEd56srOZiEC90yIEKSBn8sIrW2ZTs1c/8jyu9QBraxcWnnb4NoopOTL7hXXg=",
        "success":true,
        "timestamp":1676522864184
    }
