# 获取合约

合约介绍

[](./get-contract-java.md#%E5%90%88%E7%BA%A6%E4%BB%8B%E7%BB%8D)

--------------------------------------------------------------------------------------------------

合约是指交易的买卖对象或者标的物（比如一只股票或一只期权），合约是由交易所统一制定的。比如购买老虎证券的股票，可以通过TIGR这个字母代号和市场信息（即`market=US`，美国市场）来唯一标识。通过合约信息，我们在下单或者获取行情时就可以唯一的确定一个标的物。常见的合约包括股票合约，期权合约，期货合约等。

大多数合约（如：股票，差价合约，指数或外汇）可通过以下**四个基础**属性唯一确定：

*   标的代码 (symbol)：一般美股、英股等合约代码都是英文字母，港股、A股等合约代码是数字，比如老虎证券的 symbol 是 TIGR。
*   合约类型 (security type)：常见合约类型包括：STK（股票），OPT（期权），FUT（期货），CASH（外汇），比如老虎证券股票的合约类型是 STK。
*   货币类型 (currency)：常见货币包括 USD（美元），HKD（港币）。
*   交易所 (exchange)：STK类型的合约一般不会用到交易所字段，订单会自动路由，期货合约都用到交易所字段。

还有一些合约（如：期权和期货）由于其性质更复杂，需要一些额外的信息才能唯一标识。

以下是几种常见类型合约，以及其由哪些要素构成。

**股票**

Java

    ContractItem contract = new ContractItem();
    contract.setSymbol("TIGR");
    contract.setSecType("STK");
    contract.setCurrency("USD"); //非必填，下单时默认为USD
    contract.setMarket("US"); //非必填，合约市场，包括US（美国市场），HK（香港市场），CN（国内市场）, SG(新加坡市场), AU(澳大利亚市场) 等。下单时默认为US

**期权**

老虎API的期权合约支持**两种**方式：

*   一种是四要素方式，即 symbol（股票代码），expiry（期权过期日），strike（期权行权价格），right（期权方向）。
    
*   另一种是标准OCC期权合约格式，长度固定为21位。包含四部分：
    
    *   相关的股票或ETF的代码，比如（AAPL），固定占六位字符，不足位数由空格填充
    *   期权到期日，6位数字，格式为：yymmdd
    *   期权类型，取值为 P 或者 C, 表示 put 或 call
    *   期权行权价格，取值为 价格 x 1000, 固定占8位数字，前面不足的位数由0填充

![](https://files.readme.io/1fca87ec64fc7a76d313587503789e4373d279fb21332292e985d603522f1fe1-img_v3_02pn_66aa854b-afc4-4d3e-8c80-4ea1a577babg.png)

Java

    ContractItem contract = new ContractItem();
    contract.setSymbol("AAPL");
    contract.setSecType("OPT");
    contract.setCurrency("USD");
    contract.setExpiry("20180821");
    contract.setStrike(30D);
    contract.setRight("CALL");
    contract.setMultiplier(100.0D);
    contract.setMarket("US"); //非必填

**期货**

Java

    ContractItem contract = new ContractItem();
    contract.setSymbol("CL1901");
    contract.setSecType("FUT");
    contract.setExchange("SGX");
    contract.setCurrency("USD");
    contract.setExpiry("20190328");
    contract.setMultiplier(1.0D);

**数字货币**

Java

    ContractItem contract = new ContractItem();
    contract.setSymbol("CC");
    contract.setSecType("BTC.USD");

**基金**

Java

    ContractItem contract = new ContractItem();
    contract.setSymbol("IE00B11XZ988.USD");
    contract.setSecType("FUND");

* * *

获取单个合约信息

[](./get-contract-java.md#%E8%8E%B7%E5%8F%96%E5%8D%95%E4%B8%AA%E5%90%88%E7%BA%A6%E4%BF%A1%E6%81%AF)

------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：ContractRequest**

**说明**

获取交易需要的合约信息。 需要注意环球账户、综合账户返回`ContractItem`字段值数目会不同，建议获取合约和下单使用相同的账户。

**输入参数**

`com.tigerbrokers.stock.openapi.client.https.request.contract.ContractRequest`

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 用户授权账户 如：572386 |
| symbol | string | Yes | 股票代码 如：00700/AAPL |
| sec\_type | string | Yes | STK/OPT/FUT/CC |
| currency | string | No  | USD/HKD/CNH |
| expiry | string | No  | 到期日 交易品种是期权时必传 yyyyMMdd |
| strike | double | No  | 行权价 交易品种是期权时必传 |
| right | string | No  | CALL/PUT 交易品种是期权时必传 |
| exchange | string | No  | 交易所 (美股 SMART 港股 SEHK 沪港通 SEHKNTL 深港通 SEHKSZSE) |
| secret\_key | string | No  | 交易员密钥，机构用户专用 |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.contract.ContractResponse`

其中数据项字段如下 `com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem`

| 名称  | 示例  | 说明  |
| --- | --- | --- |
| identifier | CL2109/AAPL | 唯一标识，股票identifier和symbol相同，期权为21位标识符，如：'AAPL 220729C00150000'，期货identifier |
| symbol | LRN | 股票代码，期权合约的symbol为对应标的物代码 |
| secType | STK | STK 股票/OPT 期权/FUT 期货/WAR 窝轮/IOPT 牛熊证等，默认 STK |
| name | K12 INC | 股票名称 |
| localSymbol | 1033 | **环球账户专有**，港股用于识别窝轮和牛熊证 |
| currency | USD | USD/HKD/CNH |
| exchange | NYSE | 股票交易所 |
| primaryExchange | NYSE | 股票上市交易所 |
| market | US  | 市场， 如：US/HK/CN |
| expiry | 20171117 | **期权和期货专有**，期权或期货过期日 |
| contractMonth | 201804 | **期货专有**，合约交割月份 |
| right | PUT | **期权专有**，期权方向，CALL 或者 PUT |
| strike | 24.0 | **期权专有**，期权的行权价格 |
| multiplier | 0.0 | **期权和期货专有**，乘数，每手对应的数量 |
| lotSize | 100 | 股票每手数量 |
| minTick | 0.001 | 最小报价单位 |
| tickSizes | `[{"begin":"0","end":"1", "tickSize":1.0E-4,"type":"CLOSED"},{"begin":"1","end":"Infinity", "tickSize":0.01,"type":"OPEN"}]` | 最小报价单位价格区间，即当挂单价格在begin和end区间时，要满足tickSize要求，begin：价格左区间，end：价格右区间，type：区间类型 OPEN/OPEN\_CLOSED/CLOSED/CLOSED\_OPEN(开区间/左开右闭/闭区间/左闭右开)，tickSize：最小价格单位 |
| marginable | true | 是否可融资 |
| shortable | true | 能否做空 |
| longInitialMargin | 1   | 做多初始保证金 |
| longMaintenanceMargin | 1   | 做多维持保证金 |
| shortInitialMargin | 0.35 | 做空初始保证金比例 |
| shortMaintenanceMargin | 0.3 | 做空维持保证金比例（综合账号有值，环球账号合约没有值） |
| shortableCount | 10000000 | 可做空数目 |
| shortFeeRate | 0   | 做空费率 |
| tradingClass | LRN | 合约的交易级别名称 |
| tradeable | true | 是否可交易（仅限于STK类别） |
| continuous | false | **期货专有**，是否连续合约 |
| lastTradingDate | 2019-01-01 | **期货专有**，最后交易日 |
| firstNoticeDate | 2019-01-01 | **期货专有**，第一通知日，合约在第一通知日后无法开多仓。已有的多仓会在第一通知日之前（通常为前三个交易日）被强制平仓。 |
| lastBiddingCloseTime | 0   | **期货专有**，竞价截止时间 |
| isEtf | false | 是否是ETF |
| etfLeverage | 3   | ETF杠杆倍数，仅当合约为ETF时会存在该值 |
| discountedDayInitialMargin | 3069.0 | **期货专有**，日内优惠初始保证金比例 |
| discountedDayMaintenanceMargin | 2790.0 | **期货专有**，日内优惠维持保证金比例 |
| discountedTimeZoneCode | CDT | **期货专有**，日内优惠时间时区 |
| discountedStartAt | 17:30:00 | **期货专有**，日内优惠开始时间 |
| discountedEndAt | 14:30:00 | **期货专有**，日内优惠结束时间 |
| supportOvernightTrading | true | 是否支持夜盘交易（仅限美股） |
| supportFractionalShare | true | 是否支持碎股交易（仅适用于综合/模拟账户）,港股碎股最小是1股，美股碎股订单价值最小为1USD,碎股的精度是到0.0001 |

**示例**

Java

    // 初始化client
    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(ClientConfig.DEFAULT_CONFIG);
    
    // 获取股票合约
    ContractRequest contractRequest = ContractRequest.newRequest(new ContractModel("AAPL"));
    ContractResponse contractResponse = client.execute(contractRequest);
    System.out.println("return stock contract:" + JSONObject.toJSONString(contractResponse));
    
    // 获取期权合约
    ContractModel model = new ContractModel("AAPL", SecType.OPT.name(),Currency.USD.name(), "20211126", 150D, Right.CALL.name());
    contractRequest = ContractRequest.newRequest(model);
    contractResponse = client.execute(contractRequest);
    System.out.println("return option contract:" + JSONObject.toJSONString(contractResponse));
    
    // 获取窝轮合约
    ContractModel contractModel = new ContractModel("13745", SecType.WAR.name());
    contractModel.setStrike(719.38D);
    contractModel.setRight(Right.CALL.name());
    contractModel.setExpiry("20211223");
    ContractRequest contractRequest = ContractRequest.newRequest(contractModel);
    ContractResponse contractResponse = client.execute(contractRequest);
    System.out.println("return warrant contract:" + JSONObject.toJSONString(contractResponse));
    
    // 获取期货合约
    ContractRequest contractRequest = ContractRequest.newRequest(
            new ContractModel("JPY2306", SecType.FUT.name()), "572386");
    ContractResponse contractResponse = client.execute(contractRequest);
    System.out.println("return future contract:" + JSONObject.toJSONString(contractResponse));

**返回示例**

JSON

    {
        "code":0,
        "data":{
            "closeOnly":false,
            "currency":"USD",
            "etf":false,
            "identifier":"AAPL",
            "localSymbol":"AAPL",
            "longInitialMargin":0.3,
            "longMaintenanceMargin":0.25,
            "marginable":true,
            "market":"US",
            "multiplier":1,
            "name":"Apple",
            "secType":"STK",
            "shortInitialMargin":0.35,
            "shortMaintenanceMargin":0.3,
            "symbol":"AAPL",
            "tickSizes":[\
                {\
                    "begin":"0",\
                    "end":"1",\
                    "tickSize":0.0001,\
                    "type":"CLOSED"\
                },\
                {\
                    "begin":"1",\
                    "end":"Infinity",\
                    "tickSize":0.01,\
                    "type":"OPEN"\
                }\
            ],
            "tradeable":true,
            "tradingClass":"AAPL"
        },
        "message":"success",
        "sign":"cWxRpcxoN7fICIBBK9WAq18TVtG7ez7dGJaCWOjSLHR8sQINPImZmAly9ctwCseA004K1f/7MgiHz4P9u48YdG9Vm07mUYdiNGaNJ4o79hPMi6Vq5IGMGOYFw3MQ/bZr6ikndgDZS7qQwDpBfeqTTDvzfxWFOh080wHw0SzE+co=",
        "success":true,
        "timestamp":1680838347544
    }

* * *

获取多个合约信息

[](./get-contract-java.md#%E8%8E%B7%E5%8F%96%E5%A4%9A%E4%B8%AA%E5%90%88%E7%BA%A6%E4%BF%A1%E6%81%AF)

------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：ContractsRequest**

**说明**

获取交易需要的合约信息，只支持STK和FUT。 需要注意环球账户、综合账户返回`ContractItem`字段会不同，建议获取合约和下单使用相同的账户。

> ⚠️
> 
> **CAUTION** 综合账户返回的批量合约里没有**可做空数量**和**保证金**字段

**输入参数**

`com.tigerbrokers.stock.openapi.client.https.request.contract.ContractsRequest`

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 用户授权账户 如：572386 |
| symbols | `List<String>` | Yes | 股票代码列表 如：00700 / AAPL，单次请求上限为50 |
| sec\_type | string | Yes | STK/FUT/CC |
| currency | string | No  | USD/HKD/CNH |
| secret\_key | string | No  | 交易员密钥，机构用户专用 |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.contract.ContractsResponse`

其中数据项字段如下 `com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem`

| 名称  | 示例  | 说明  |
| --- | --- | --- |
| identifier | CL2109/AAPL | 唯一标识，股票identifier和symbol相同，期权为21位标识符，如：'AAPL 220729C00150000'，期货identifier |
| symbol | LRN | 股票代码，期权合约的symbol为对应标的物代码 |
| secType | STK | STK 股票/OPT 期权/FUT 期货/WAR 窝轮/IOPT 牛熊证等，默认 STK |
| name | K12 INC | 股票名称 |
| localSymbol | 1033 | **环球账户专有**，港股用于识别窝轮和牛熊证 |
| currency | USD | USD/HKD/CNH |
| exchange | NYSE | 股票交易所 |
| primaryExchange | NYSE | 股票上市交易所 |
| market | US  | 市场 /US/HK/CN |
| expiry | 20171117 | **期权和期货专有**，期权或期货过期日 |
| contractMonth | 201804 | **期货专有**，合约交割月份 |
| right | PUT | **期权专有**，期权方向，CALL 或者 PUT |
| strike | 24.0 | **期权专有**，期权的行权价格 |
| multiplier | 0.0 | **期权和期货专有**，乘数，每手对应的数量 |
| minTick | 0.001 | 最小报价单位 |
| tickSizes | `[{"begin":"0","end":"1", "tickSize":1.0E-4,"type":"CLOSED"},{"begin":"1","end":"Infinity", "tickSize":0.01,"type":"OPEN"}]` | **股票专有**，最小报价单位价格区间，即当挂单价格在begin和end区间时，要满足tickSize要求，begin：价格左区间，end：价格右区间，type：区间类型 OPEN/OPEN\_CLOSED/CLOSED/CLOSED\_OPEN(开区间/左开右闭/闭区间/左闭右开)，tickSize：最小价格单位 |
| marginable | true | 是否可融资 |
| shortable | true | 能否做空 |
| longInitialMargin | 1   | 做多初始保证金 |
| longMaintenanceMargin | 1   | 做多维持保证金 |
| shortInitialMargin | 0.35 | 做空初始保证金比例 |
| shortMaintenanceMargin | 0.3 | 做空维持保证金比例（综合账号有值，环球账号合约没有值） |
| shortableCount | 10000000 | 可做空数目 |
| shortFeeRate | 0   | 做空费率 |
| tradingClass | LRN | 合约的交易级别名称 |
| tradeable | true | 是否可交易（仅限于STK类别） |
| continuous | false | **期货专有**，是否连续合约 |
| lastTradingDate | 2019-01-01 | **期货专有**，最后交易日 |
| firstNoticeDate | 2019-01-01 | **期货专有**，第一通知日，合约在第一通知日后无法开多仓。已有的多仓会在第一通知日之前（通常为前三个交易日）被强制平仓。 |
| lastBiddingCloseTime | 0   | **期货专有**，竞价截止时间 |
| isEtf | false | 是否是ETF |
| etfLeverage | 0   | ETF杠杆倍数，仅当合约为ETF时会存在该值 |
| supportOvernightTrading | true | 是否支持夜盘交易（仅限美股） |

**示例**

Java

    List<String> symbols = new ArrayList<>();
    symbols.add("AAPL");
    symbols.add("TSLA");
    ContractsModel models = new ContractsModel(symbols, SecType.STK.name());
    ContractsRequest contractsRequest = ContractsRequest.newRequest(models, "13810712");
    ContractsResponse contractsResponse = client.execute(contractsRequest);
    System.out.println("return contracts:" + JSONObject.toJSONString(contractsResponse));

**返回示例**

JSON

    {
        "code":0,
        "data":[\
            {\
                "currency":"USD",\
                "etf":false,\
                "identifier":"AAPL",\
                "localSymbol":"AAPL",\
                "market":"US",\
                "multiplier":1,\
                "name":"Apple Inc",\
                "secType":"STK",\
                "symbol":"AAPL",\
                "tickSizes":[\
                    {\
                        "begin":"0",\
                        "end":"1",\
                        "tickSize":0.0001,\
                        "type":"CLOSED"\
                    },\
                    {\
                        "begin":"1",\
                        "end":"Infinity",\
                        "tickSize":0.01,\
                        "type":"OPEN"\
                    }\
                ],\
                "tradeable":true,\
                "tradingClass":"AAPL"\
            },\
            {\
                "currency":"USD",\
                "etf":false,\
                "identifier":"TSLA",\
                "localSymbol":"TSLA",\
                "market":"US",\
                "multiplier":1,\
                "name":"Tesla Motors",\
                "secType":"STK",\
                "symbol":"TSLA",\
                "tickSizes":[\
                    {\
                        "begin":"0",\
                        "end":"1",\
                        "tickSize":0.0001,\
                        "type":"CLOSED"\
                    },\
                    {\
                        "begin":"1",\
                        "end":"Infinity",\
                        "tickSize":0.01,\
                        "type":"OPEN"\
                    }\
                ],\
                "tradeable":true,\
                "tradingClass":"TSLA"\
            }\
        ],
        "message":"success",
        "sign":"Bv8H6BBfKOrMOhdJAanE0hwJiJAoKOk55/cTkJIVSmw9ENd2nmbeBI3cesqRgPq8bJ2dUBrYr+cqLVG65meXwbaFYyEenWlNyigl02IuJVoETgChuSoX1SZBTnafoVivCIj3neWG8BFdhaTNNXfBMbeSYf01+BqI6xYEc6KQxKc=",
        "success":true,
        "timestamp":1684912317872
    }

* * *

获取期权/窝轮/牛熊证合约列表

[](./get-contract-java.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E7%AA%9D%E8%BD%AE%E7%89%9B%E7%86%8A%E8%AF%81%E5%90%88%E7%BA%A6%E5%88%97%E8%A1%A8)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QuoteContractRequest**

**输入参数：**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | string | Yes | 股票代码 |
| sec\_type | string | Yes | 合约类型（OPT：期权，WAR：港股窝轮，IOPT 港股牛熊证） |
| expiry | String | No  | 到期日，格式：yyyyMMdd，如果是 OPT 必须有值 |
| lang | string | No  | 语言支持: en\_US，zh\_CN，zh\_TW，默认: en\_US |

**返回结果：**

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| name | string | 合约名称 |
| exchange | string | 交易所 |
| market | string | 市场  |
| secType | string | 合约类型 |
| currency | string | 币种  |
| expiry | string | 到期日 (期权、窝轮、牛熊证、期货)， 20171117 |
| right | string | 期权方向 (期权、窝轮、牛熊证), PUT/CALL |
| strike | string | 行权价 |
| multiplier | double | 乘数，每手对应的数量 （期权、窝轮、牛熊证、期货） |

**请求示例:**

Java

    QuoteContractResponse response = client.execute(QuoteContractRequest.newRequest("00700", SecType.WAR, "20211223"));
    if (response.isSuccess()) {
      System.out.println(response.getContractItems());
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**响应示例：**

JSON

    {
    	"code": 0,
    	"data": [{\
    		"items": [{\
    			"currency": "HKD",\
    			"exchange": "SEHK",\
    			"expiry": "20211223",\
    			"market": "HK",\
    			"multiplier": 50000.0,\
    			"name": "MSTENCT@EC2112B.C",\
    			"right": "CALL",\
    			"secType": "WAR",\
    			"strike": "719.38",\
    			"symbol": "13745"\
    		}, {\
    			"currency": "HKD",\
    			"exchange": "SEHK",\
    			"expiry": "20211223",\
    			"market": "HK",\
    			"multiplier": 5000.0,\
    			"name": "JPTENCT@EC2112A.C",\
    			"right": "CALL",\
    			"secType": "WAR",\
    			"strike": "900.5",\
    			"symbol": "13680"\
    		}],\
    		"secType": "WAR",\
    		"symbol": "00700"\
    	}],
    	"message": "success",
    	"sign": "bxQhZiWMsT9aSVTNtt2SXVeeh5w8Ypug/6UY3nL9N7LFKB1YxBVpQoKDJ4JloFojyb/CPCGT0fCXTxboDBTZvnA4stjbh1YqbNlz2lNqmHhpxYUKMdE+w2hFKVvoYMlMPCmsY5NqSQ3S/fsSzZrJyxBRPzZ+d+0qb7VSYw9yhho=",
    	"success": true,
    	"timestamp": 1637686550209
    }
