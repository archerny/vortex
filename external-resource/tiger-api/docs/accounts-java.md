# 查询账户信息

账户列表

[](./accounts-java.md#%E8%B4%A6%E6%88%B7%E5%88%97%E8%A1%A8)

----------------------------------------------------------------------------------------------

**对应的请求类：TigerHttpRequest(MethodName.ACCOUNTS)**

**说明**

获取管理的账户列表，机构账户返回主账户及所有子账户。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | No  | 用户授权账号（不支持传入模拟账号），不传会返回全部账号，包括：综合，环球，模拟。 |

**返回**

具体数据保存在返回的data字段中，具体含义如下

| 字段  | 示例  | 说明  |
| --- | --- | --- |
| account | 综合账号：50129912，环球：U5755619，模拟账号：20191221901212121 | 交易的资金账户，其中综合账号为 5 到 10 位数字，模拟账号为 17 位数字，环球账号以字母 U 开头 |
| capability | RegTMargin | 账户类型（CASH：现金账户，RegTMargin：Reg T 保证金账户，PMGRN：投资组合保证金） |
| status | Funded | 账号状态，大部分场景会返回已入金（Funded）。状态包括： Funded（已入金），Open（已开户），Pending（待开户），Rejected（开户被拒绝），Closed（已注销） |
| accountType | STANDARD | 账户分类（GLOBAL：环球账号，STANDARD：综合账号，PAPER：模拟账号） |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ACCOUNTS);
    
    String bizContent = AccountParamBuilder.instance()
            .account("123456")
            .buildJsonWithoutDefaultAccount();
    # 查询ClientConfig.DEFAULT_CONFIG配置的默认账号
    # String bizContent = AccountParamBuilder.instance().buildJson();
    # 查询所有账号列表
    # String bizContent = AccountParamBuilder.instance().buildJsonWithoutDefaultAccount();
    
    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);
    
    # 获取具体字段数据
    JSONArray accounts = JSON.parseObject(response.getData()).getJSONArray("items");
    JSONObject account1 = accounts.getJSONObject(0);
    String capability = account1.getString("capability");
    String accountType = account1.getString("accountType");
    String account = account1.getString("account");
    String status = account1.getString("status");

**返回示例**

JSON

    {
      "code": 0,
      "message": "success",
      "data": {
        "items": [\
          {\
            "account": "123456",\
            "capability": "RegTMargin",\
            "status": "Funded",\
            "accountType": "STANDARD"\
          }\
        ]
      }
    }
    

* * *

账户持仓

[](./accounts-java.md#%E8%B4%A6%E6%88%B7%E6%8C%81%E4%BB%93)

----------------------------------------------------------------------------------------------

**对应的请求类：PositionsRequest**

**说明**

查询账户持仓

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 用户授权账户：13810712 |
| sec\_type | string | No  | 证券类型，包括：STK/OPT/FUT/WAR/IOPT/CASH/FOP/FUND/CC， 默认 STK |
| currency | string | No  | 货币类型，包括：ALL/USD/HKD/CNH，默认 ALL |
| market | string | No  | 市场分类，包括：ALL/US/HK/CN，默认 ALL |
| symbol | string | No  | 股票代码 如：600884 / SNAP,期货类型时：CL1901，期权类型时 symbol格式可以传底层股票代码，也可以为identifier，如：'AAPL 190111C00095000'，格式固定为21位，symbol为6位，不足的补空格；窝轮/牛熊证传5位代码、期权传 |
| secret\_key | string | No  | 交易员密钥，机构用户专用 |
| expiry | string | No  | 过期日(适用于期权、窝轮、牛熊证)。 形式 'yyyyMMdd', 比如 '20230818' |
| strike | double | No  | 行权价(适用于期权、窝轮、牛熊证)。如 100.5 |
| right | string | No  | 看涨或看跌(适用于期权、窝轮、牛熊证)。'PUT' 或 'CALL' |
| asset\_quote\_type | string | No  | 资产行情模式(仅限综合账号)，枚举值详见： [资产行情模式枚举](./appendix-enum-java.md#asset-quote-type) |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.trade.PositionsResponse` 可用 `List<PositionDetail> items = response.getItem().getPositions()` 获取持仓列表。具体参见示例代码

**`PositionDetail`说明：**

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| account | string | 交易账户 |
| positionQty | double | 持仓数量 |
| salableQty | double | 可卖数量 |
| position | long | 持仓数量（废弃） |
| positionScale | int | 持仓数量偏移量（废弃），如 position=11123， positionScale=2，那么实际 position=11123\*10^(-2)=111.23 |
| averageCost | double | 平均FIFO成本 |
| averageCostByAverage | double | 平均均价成本 |
| averageCostOfCarry | double | 摊薄持仓成本(A股模式计算方式) |
| latestPrice | double | 市价（在交易时段，为市场价格。美股非交易时间，综合账号为盘后收盘价，环球账号为盘中收盘价） |
| isLevel0Price | boolean | 是否为lv0（延迟）行情 |
| marketValue | double | 市值  |
| realizedPnl | double | 模式下已实现盈亏 |
| realizedPnlByAverage | double | 均价模式下已实现盈亏 |
| unrealizedPnl | double | 浮动盈亏 |
| unrealizedPnlByAverage | double | 均价成本浮动盈亏 |
| unrealizedPnlPercent | double | 浮动收益率 (投资回报率) |
| unrealizedPnlPercentByAverage | double | 均价成本浮动盈亏率 |
| unrealizedPnlByCostOfCarry | double | 浮动盈亏 (A股模式计算方式) |
| unrealizedPnlPercentByCostOfCarry | double | 浮动盈亏率 (A股模式计算方式) |
| multiplier | double | 每手数量 |
| market | string | 市场  |
| currency | string | 交易货币币种 |
| secType | string | 交易类型 |
| identifier | string | 标的标识 |
| symbol | string | 股票代码 |
| strike | double | 期权底层价格 (仅限期权) |
| expiry | string | 期权过期日 (仅限期权) |
| right | string | 期权方向 (仅限期权) |
| updateTimestamp | long | 更新时间 |
| mmPercent | double | 保证金占比 |
| mmValue | double | 维持保证金 |
| todayPnl | double | 今日盈亏额 |
| todayPnlPercent | double | 今日盈亏率 |
| yesterdayPnl | double | 基金昨日盈亏 |
| lastClosePrice | double | 最后盘中收盘价(前复权)，美股盘中为上个交易日的收盘价 |
| categories | `List<string>` | 合约类型 |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    PositionsRequest request = new PositionsRequest();
    
    String bizContent = AccountParamBuilder.instance()
            .account("13810712")
            .secType(SecType.STK)
            .buildJson();
    
    request.setBizContent(bizContent);
    PositionsResponse response = client.execute(request);
    
    # 解析具体字段
    if (response.isSuccess()) {
        System.out.println(JSONObject.toJSONString(response));
        for (PositionDetail detail : response.getItem().getPositions()) {
          String account = detail.getAccount();
          String symbol = detail.getSymbol();
          long position = detail.getPosition();
          // ...
        }
    } else {
        System.out.println(response.getMessage());
    }

**返回示例**

JSON

    {
      "code": 0,
      "data": {
        "items": [\
          {\
            "account": "13810712",\
            "averageCost": 295.8904,\
            "averageCostByAverage": 295.8904,\
            "averageCostOfCarry": 591.7807,\
            "categories": [\
            ],\
            "currency": "USD",\
            "identifier": "AAPL",\
            "lastClosePrice": 232.98,\
            "latestPrice": 232.44,\
            "level0Price": false,\
            "market": "US",\
            "marketValue": -232.44,\
            "mmPercent": 0,\
            "mmValue": 92.976,\
            "multiplier": 1,\
            "position": -1,\
            "positionQty": -1,\
            "positionScale": 0,\
            "realizedPnl": 0,\
            "realizedPnlByAverage": 0,\
            "salableQty": -1,\
            "secType": "STK",\
            "symbol": "AAPL",\
            "todayPnl": 0.54,\
            "todayPnlPercent": 0.0023178,\
            "unrealizedPnl": 63.4504,\
            "unrealizedPnlByAverage": 63.4504,\
            "unrealizedPnlByCostOfCarry": 359.3407,\
            "unrealizedPnlPercent": 0.2144,\
            "unrealizedPnlPercentByAverage": 0.2144,\
            "unrealizedPnlPercentByCostOfCarry": 0.6072,\
            "updateTimestamp": 1720685551097\
          }\
        ]
      },
      "message": "success",
      "sign": "Wjndi3ZrsxQYWWdkrNKSPMASGfkG5trdHbVujTKrcGoVE5cN0QZBInJggnVL2rMgKd5TS00mnGcOov96iR5K5gZKRA0iQmZHUjJHTmK9JY/rEP9A18xDaljFNHMqmJ8vydFjMQXLebXTVzafbkZoI9LS1LIdoiSCD+6VocogpB0=",
      "success": true,
      "timestamp": 1720685551132
    }

* * *

环球账户资产

[](./accounts-java.md#%E7%8E%AF%E7%90%83%E8%B4%A6%E6%88%B7%E8%B5%84%E4%BA%A7)

------------------------------------------------------------------------------------------------------------------

**对应的请求类：TigerHttpRequest(MethodName.ASSETS)**

**说明**

获取环球账户资产，主要适用于环球账户，综合/模拟账号虽然也可使用此接口，但有较多字段为空，建议使用[PrimeAssetRequest](./accounts-java.md#primeasset)
 查询综合/模拟账户资产

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 用户授权账户：DU575569 |
| segment | boolean | No  | 是否包含证券/期货分类， 默认 False |
| market\_value | boolean | No  | 是否包含分市场市值，默认 False，仅环球账户支持 |
| secret\_key | string | No  | 交易员密钥，机构用户专用 |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse` 具体数据为json格式，解析步骤参照下方示例代码。

| 名称  | 示例  | 说明  |
| --- | --- | --- |
| account | DU575569 | 交易账户 |
| capability | RegTMargin | 账户类型，保证金: RegTMargin，现金: Cash |
| netLiquidation | 1233662.93 | 净清算值 |
| equityWithLoan | 1233078.69 | 含借贷值股权(含贷款价值资产)。 证券 Segment: 现金价值 + 股票价值， 期货 Segment: 现金价值 - 维持保证金 |
| initMarginReq | 292046.91 | 初始保证金要求 |
| maintMarginReq | 273170.84 | 维持保证金要求 |
| availableFunds | 941031.78 | 可用资金(可用于交易)，计算方法为 equity\_with\_loan - initial\_margin\_requirement |
| dayTradesRemaining | \-1 | 剩余日内交易次数，-1 表示无限制 |
| excessLiquidity | 960492.09 | 剩余流动性，用来表示日内风险数值。 证券 Segment 计算方法: equity\_with\_loan - maintenance\_margin\_requirement。 期货 Segment 计算方法: net\_liquidation - maintenance\_margin\_requirement |
| buyingPower | 6273545.18 | 购买力。 预估您还可以购入多少美元的股票资产。保证金账户日内最多有四倍于资金（未被占用做保证金的资金）的购买力。隔夜最多有两倍的购买力 |
| cashValue | 469140.39 | 证券账户现金金额+期货账户现金金额 |
| accruedCash | \-763.2 | 当前月份的累积应付利息，按照日频更新。 |
| accruedDividend | 0.0 | 累计分红. 指的是所有已执行但仍未支付的分红累加值 |
| grossPositionValue | 865644.18 | 证券总价值: 做多股票的价值+做空股票价值+做多期权价值+做空期权价值。 |
| SMA | 0.0 | 特殊备忘录账户，隔夜风险数值（App） |
| regTEquity | 0.0 | 仅针对证券Segment，即根据 Regulation T 法案计算的 equity with loan（含借贷股权值） |
| regTMargin | 0.0 | 仅针对证券Segment， 即根据 Regulation T 法案计算的 initial margin requirements（初始保证金） |
| cushion | 0.778569 | 剩余流动性占总资产的比例，计算方法为: excess\_liquidity/net\_liquidation |
| currency | USD | 货币币种 |
| realizedPnl | \-248.72 | 实际盈亏 |
| unrealizedPnl | \-17039.09 | 浮动盈亏 |
| updateTime | 0   | 更新时间 |
| **segments** |     | 按照交易品种区分的账户信息。内容是一个Map，分别有两个key，'S'表示证券， 'C' 表示期货； value 是一个 Account 对象 |
| **marketValues** |     | 分市场的市值信息。内容是一个Map， 'USD' 表示美国市场， 'HKD' 表示香港市场; value 是一个 MarketValue 对象 |

**`segments`说明：**

| 名称  | 示例  | 说明  |
| --- | --- | --- |
| account | DU575569 | 交易账户 |
| category | S   | 底层证券的行业分类 C(US Commodities 期货) or S(US Securities 证券) |
| title | US Securities | 标题  |
| netLiquidation | 1233662.93 | 净清算值 |
| cashValue | 469140.39 | 证券账户金额+期货账户金额 |
| availableFunds | 941031.78 | 可用资金(可用于交易) |
| equityWithLoan | 1233078.69 | 含借贷值股权 |
| excessLiquidity | 960492.09 | 剩余流动性，为保持当前拥有的头寸，必须维持的缓冲保证金的数额，日内风险数值（App） |
| accruedCash | \-763.2 | 净累计利息 |
| accruedDividend | 0.0 | 净累计分红 |
| initMarginReq | 292046.91 | 初始保证金要求 |
| maintMarginReq | 273170.84 | 维持保证金要求 |
| regTEquity | 0.0 | RegT资产 |
| regTMargin | 0.0 | RegT保证金 |
| SMA | 0.0 | 特殊备忘录账户，隔夜风险数值（App） |
| grossPositionValue | 865644.18 | 持仓市值 |
| leverage | 1   | 杠杆  |
| updateTime | 1526368181000 | 更新时间 |

**`marketValues`说明：**

| 名称  | 示例  | 说明  |
| --- | --- | --- |
| account | DU575569 | 交易账户 |
| currency | USD | 货币币种 |
| netLiquidation | 1233662.93 | 总资产 (净清算价值) |
| cashBalance | 469140.39 | 现金  |
| exchangeRate | 0.1273896 | 对账户主币种的汇率 |
| netDividend | 0.0 | 应付股息与应收股息的净值 |
| futuresPnl | 0.0 | 盯市盈亏 |
| realizedPnl | \-248.72 | 已实现盈亏 |
| unrealizedPnl | \-17039.09 | 浮动盈亏 |
| updateTime | 1526368181000 | 更新时间 |
| stockMarketValue | 943588.78 | 股票市值 |
| optionMarketValue | 0.0 | 期权市值 |
| futureOptionValue | 0.0 | 期货市值 |
| warrantValue | 10958.0 | 窝轮市值 |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ASSETS);
    
    String bizContent = AccountParamBuilder.instance()
            .account("DU575569")
            .buildJson();
    
    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);
    
    # 解析具体字段
    JSONArray assets = JSON.parseObject(response.getData()).getJSONArray("items");
    JSONObject asset1 = assets.getJSONObject(0);
    String account = asset1.getString("account");
    Double cashBalance = asset1.getDouble("cashBalance");
    JSONArray segments = asset1.getJSONArray("segments");
    JSONObject segment = segments.getJSONObject(0);
    String category = segment.getString("category"); // "S" 股票， "C" 期货

**返回示例**

JSON

    
    {
    	"code": 0,
    	"message": "success",
    	"data": {
    		"items": [{\
    			"account": "DU575569",\
    			"accruedCash": -763.2,\
    			"accruedDividend": 0.0,\
    			"availableFunds": 941031.78,\
    			"buyingPower": 6273545.18,\
    			"capability": "Reg T Margin",\
    			"cashBalance": 469140.39,\
    			"cashValue": 469140.39,\
    			"currency": "USD",\
    			"cushion": 0.778569,\
    			"dayTradesRemaining": -1,\
    			"equityWithLoan": 1233078.69,\
    			"excessLiquidity": 960492.09,\
    			"grossPositionValue": 865644.18,\
    			"initMarginReq": 292046.91,\
    			"maintMarginReq": 273170.84,\
    			"netLiquidation": 1233662.93,\
    			"netLiquidationUncertainty": 583.55,\
    			"previousEquityWithLoanValue": 1216291.68,\
    			"previousNetLiquidation": 1233648.34,\
    			"realizedPnl": -31.68,\
    			"unrealizedPnl": 1814.01,\
    			"regTEquity": 0.0,\
    			"regTMargin": 0.0,\
    			"SMA": 0.0,\
    			"segments": [{\
    				"account": "DU575569",\
    				"accruedDividend": 0.0,\
    				"availableFunds": 65.55,\
    				"cashValue": 65.55,\
    				"category": "S",\
    				"equityWithLoan": 958.59,\
    				"excessLiquidity": 65.55,\
    				"grossPositionValue": 893.04,\
    				"initMarginReq": 893.04,\
    				"leverage": 0.93,\
    				"maintMarginReq": 893.04,\
    				"netLiquidation": 958.59,\
    				"previousDayEquityWithLoan": 969.15,\
    				"regTEquity": 958.59,\
    				"regTMargin": 446.52,\
    				"sMA": 2172.47,\
    				"title": "US Securities",\
    				"tradingType": "STKMRGN",\
    				"updateTime": 1541124813\
    			}],\
    			"marketValues": [{\
    				"account": "DU575569",\
    				"accruedCash": 0.0,\
    				"cashBalance": -943206.03,\
    				"currency": "HKD",\
    				"exchangeRate": 0.1273896,\
    				"futureOptionValue": 0.0,\
    				"futuresPnl": 0.0,\
    				"netDividend": 0.0,\
    				"netLiquidation": 11223.29,\
    				"optionMarketValue": 0.0,\
    				"realizedPnl": -248.72,\
    				"stockMarketValue": 943588.78,\
    				"unrealizedPnl": -17039.09,\
    				"updateTime": 1526368181000,\
    				"warrantValue": 10958.0\
    			},{\
    				"account": "DU575569",\
    				"accruedCash": 0.0,\
    				"cashBalance": -1635.23,\
    				"currency": "GBP",\
    				"exchangeRate": 1.35566495,\
    				"futureOptionValue": 0.0,\
    				"futuresPnl": 0.0,\
    				"netDividend": 0.0,\
    				"netLiquidation": 170.39,\
    				"optionMarketValue": 0.0,\
    				"realizedPnl": 0.0,\
    				"stockMarketValue": 1805.62,\
    				"unrealizedPnl": 177.58,\
    				"updateTime": 1526368181000,\
    				"warrantValue": 0.0\
    			},{\
    				"account": "DU575569",\
    				"accruedCash": 0.0,\
    				"cashBalance": 703542.12,\
    				"currency": "USD",\
    				"exchangeRate": 1.0,\
    				"futureOptionValue": 0.0,\
    				"futuresPnl": 0.0,\
    				"netDividend": 0.0,\
    				"netLiquidation": 1208880.15,\
    				"optionMarketValue": -64.18,\
    				"realizedPnl": 0.0,\
    				"stockMarketValue": 505780.03,\
    				"unrealizedPnl": 19886.87,\
    				"updateTime": 1526359227000,\
    				"warrantValue": 0.0\
    			}, {\
    				"account": "DU575569",\
    				"accruedCash": 0.0,\
    				"cashBalance": -714823.64,\
    				"currency": "CNH",\
    				"exchangeRate": 0.1576904,\
    				"futureOptionValue": 0.0,\
    				"futuresPnl": 0.0,\
    				"netDividend": 0.0,\
    				"netLiquidation": 142250.72,\
    				"optionMarketValue": 0.0,\
    				"realizedPnl": 0.0,\
    				"stockMarketValue": 859152.75,\
    				"unrealizedPnl": -102371.43,\
    				"updateTime": 1526368181000,\
    				"warrantValue": 0.0\
    		}]\
    	},\
    	"timestamp": 1527830042620\
    }\
\
* * *\
\
综合/模拟账号获取资产\
\
[](./accounts-java.md#%E7%BB%BC%E5%90%88%E6%A8%A1%E6%8B%9F%E8%B4%A6%E5%8F%B7%E8%8E%B7%E5%8F%96%E8%B5%84%E4%BA%A7)\
\
-----------------------------------------------------------------------------------------------------------------------------------------------------------\
\
**对应的请求类：PrimeAssetRequest**\
\
**说明**\
\
查询综合/模拟账户的资产\
\
**参数**\
\
| 参数  | 类型  | 是否必填 | 描述  |\
| --- | --- | --- | --- |\
| account | string | Yes | 用户授权账户: 123123 |\
| base\_currency | string | No  | Currency， HKD/USD |\
| secret\_key | string | No  | 交易员密钥，机构用户专用 |\
| consolidated | boolean | No  | 是否展示聚合Segment资产指标，只有SEC和FUND类别资产会聚合。默认为true |\
\
**返回**\
\
`com.tigerbrokers.stock.openapi.client.https.response.trade.PrimeAssetResponse`\
\
可用 `PrimeAssetItem.Segment segment = primeAssetResponse.getSegment(Category.S)` 获取按交易品种划分的资产； 对于每个品种的资产，可用 `PrimeAssetItem.CurrencyAssets assetByCurrency = segment.getAssetByCurrency(Currency.USD)`获取对应币种的资产。具体参见示例代码\
\
**`PrimeAssetItem.Segment`说明：**\
\
| 名称  | 类型  | 示例  | 说明  |\
| --- | --- | --- | --- |\
| account | String | 123123 | 交易账户 |\
| currency | String | USD | 货币的币种 |\
| category | String | S   | 交易品种分类 C(Commodities 期货) or S(Securities 证券)、F(Funds 基金)、D(Digi 数字货币) |\
| capability | String | RegTMargin | 账户类型, 保证金账户: RegTMargin, 现金账户: Cash。保证金账户支持融资融券功能，T+0交易次数不受限制，最大购买力于日内最高4倍，隔日最高2倍 |\
| buyingPower | Double | 6273545.18 | 最大购买力。 最大购买力是账户最大的可用购买金额。可以用最大购买力的值来估算账户最多可以买多少金额的股票，但是由于每只股票的最多可加的杠杆倍数不一样，所以实际购买单支股票时可用的购买力要根据具体股票保证金比例来计算。算法，最大购买力=4\*可用资金。举例：小虎的可用资金是10万美元，那么小虎的最大购买力是40万美元，假设当前苹果股价是250美元一股，苹果的初始保证金比例是30%，小虎最多可以买100000/30%=33.34万美金的苹果，假设苹果的初始保证金比例为25%，小虎最多可以买100000/25%=40万美金的苹果。保证金账户日内最多有四倍于资金（未被占用做保证金的资金）的购买力 隔夜最多有两倍的购买力 |\
| cashAvailableForTrade | Double | 1233662.1 | 可用资金。可用资金用来检查是否可以开仓或打新。开仓是指做多买入股票、做空融券卖出股票等交易行为。需要注意的是可用资金不等于可用现金，是用总资产、期权持仓市值、冻结资金和初始保证金等指标算出来的一个值，当可用资金大于0时，代表该账户可以开仓，可用资金\_4 为账户的最大可用购买力。算法，可用资金=总资产-美股期权市值-当前总持仓的初始保证金-冻结资金。其中初始保证金的算法是∑（持仓个股市值\_当前股票的开仓保证金比例）。举例：小虎当前账户总资产为10000美元，持有1000美元美股期权，持有总市值为2000美元的苹果，苹果的初始保证金比例当前为45%，没有冻结资金，小虎的可用资金=10000-1000-2000\*45%=8100美元 |\
| cashAvailableForWithdrawal | Double | 1233662.1 | 当前账号内可以出金的现金金额 |\
| cashBalance | Double | 469140.39 | 现金额。现金额就是当前所有币种的现金余额之和。如果您当前帐户产生了融资或借款，需要注意的是利息一般是按天算，按月结，具体以融资天数计算。每日累计，下个月初5号左右统一扣除，所以在扣除利息之前，用户看到的现金余额是没有扣除利息的，如果扣除利息前现金余额为零，可能扣除后会产生欠款，即现金余额变为负值 |\
| grossPositionValue | Double | 865644.18 | 证券总价值，是账户持仓证券的总市值之和，即全部持仓的总市值。算法，持仓证券总市值之和；备注：所有持仓市值都会按主币种计算；举例1，若小虎同时持有市值3000美元苹果（也就是做多），持有市值-1000美元的谷歌（也就是做空），小虎证券总价值=3000美元苹果+（-1000美元谷歌）=2000美元；举例2，小虎持有市值1万美元的苹果，市值5000美元的苹果做多期权，小虎证券总价值=10000+5000=15000美元 |\
| initMargin | Double | 292046.91 | 初始保证金。当前所有持仓合约所需的初始保证金要求之和。初次执行交易时，只有当含贷款价值总权益大于初始保证金才允许开仓。为满足监管机构的保证金要求，我们会在临近收盘前15分钟提升初始保证金与维持保证金要求至最低50% |\
| maintainMargin | Double | 273170.84 | 维持保证金。当前所有持仓合约所需的维持保证金要求之和。持有头寸时，当含贷款价值总权益小于维持保证金会引发强平。为满足监管机构的保证金要求，我们会在临近收盘前15分钟提升初始保证金与维持保证金要求至最低50% |\
| overnightMargin | Double | 273170.84 | 隔夜保证金。隔夜保证金是在收盘前15分钟开始检查账户所需的保证金，为满足监管机构的保证金要求，我们会在临近收盘前15分钟提升初始保证金与维持保证金要求至最低50%。老虎国际的隔夜保证金比例均在50%以上。如果账户含货款价值总权益低于隔夜保证金，账户在收盘前15分钟存在被强制平仓的风险。算法，∑（持仓个股隔夜时段的维持保证金）。个股的维持保证金率用户能够在“个股详情页-报价区-点击融资融券标识"查询。举例：小虎账户总资产为10万美元，苹果开仓保证金比例是40%，日内维持保证金是20%，日内全仓买入苹果共25万美元，到收盘前15分钟（隔夜时段）维持保证金比例将被提高到50%，此时的用户隔夜保证金为：25万\*50%=12.5万，用户的含贷款价值总权益为10万小于12.5万，用户将会被平仓部分股票 |\
| excessLiquidation | Double | 960492.09 | 当前剩余流动性。当前剩余流动性是衡量当前账户潜在的被平仓风险的指标，当前剩余流动性越低账户被平仓的风险越高，当小于0时会被强制平掉部分持仓。具体的算法为：当前剩余流动性=含货款价值总权益(equityWithLoan)-账户维持保证金(maintainMargin) 。为满足监管机构的保证金要求，我们会在临近收盘前15分钟提升初始保证金与维持保证金要求至最低50%。举例：(1)、客户总资产1万美金，买入苹果12000美金（假设苹果开仓和维持保证金比例为50%）。当前剩余流动性=总资产10000-账户维持保证金6000=4000。(2)、随着股票的下跌，假设股票市值跌到8000，这个时候当前剩余流动性=总资产 （-2000+8000）-账户维持保证金4000=2000。(3)、此时用户又买入了1000美金的美股期权，那么账户的当前剩余流动性还剩下2000-1000=1000。若您的账户被强制平仓，则会以市价单在强制平仓时进行成交，强平的股票对象由券商自行决定，请您注意风控值和杠杆等指标 |\
| overnightLiquidation | Double | 1233662.93 | 隔夜剩余流动性。隔夜剩余流动性是指用 含货款价值总权益(equityWithLoan)-隔夜保证金(overnightMargin) 算出来的值。为满足监管机构的保证金要求，我们会在临近收盘前15分钟提升初始保证金与维持保证金要求至最低50%。如果账户的隔夜剩余流动性低于0，在收盘前15分钟起账户存在被强行平掉部分持仓的风险。若您的账户被强制平仓，则会以市价单在强制平仓时进行成交，强平的股票对象由券商自行决定，请您注意风控值和杠杆等指标 |\
| netLiquidation | Double | 1233662.93 | 总资产(净清算值)。总资产就是我们账户的净清算现金余额和证券总市值之和，通常用来表示目前账户中有多少资产。算法，总资产=证券总市值+现金余额+应计分红-应计利息；举例：小虎账户有1000美元现金，持仓价值1000美元的苹果（即做多苹果），没有待发放的股息和融资利息和未扣除的利息，那么小虎的总资产=现金1000+持仓价值1000=2000美元，若小虎账户有1000美元现金，做空1000美元的苹果，此时证券总市值-1000美元，现金2000美元，用户总资产=现金2000+（持仓市值-1000），账户总资产共计1000美元 |\
| equityWithLoan | Double | 1233078.69 | 含贷款价值总权益，即ELV，ELV是用来计算开仓和平仓的数据指标；算法，现金账户=现金余额，保证金账户=现金余额+证券总市值-美股期权市值；ELV = 总资产 - 美股期权 |\
| realizedPL | Double | \-248.72 | 当天已实现盈亏（仅期货有效，计算所有未结算订单的已实现盈亏） |\
| totalTodayPL | Double | 0.0 | 当日盈亏总和 |\
| unrealizedPL | Double | \-17039.09 | 持仓盈亏。定义，持仓个股、衍生品的未实现盈亏金额；算法，当前价\*股数-持仓成本 |\
| leverage | Double | 0.5 | 杠杆。杠杆是衡量账户风险程度的重要指标，可以帮助用户快速了解账户融资比例和风险程度；算法，杠杆=证券市值绝对值之和/总资产；备注1，保证金账户日内最大杠杆4倍，隔夜2倍；备注2，老虎考虑到历史波动、流动性和风险等因素，并不是每只股票都能4倍杠杆买入，一般做多保证金比例范围在25%-100%之间，保证金比例等于25%的股票，可以理解为4倍杠杆买入，保证金比例等于100%的股票，可以理解为0倍杠杆买入，即全部用现金买入。需要留意做空保证金可能会大于100%。备注3，个股保证金比例用户能够在“个股详情页-报价区-点击融资融券标识”查询；举例，小虎账户总资产10万美元，想买苹果，苹果当前个股做多初始保证金比例50%（1/50%=2倍杠杆），小虎最多只能买入20万市值的苹果股票；小虎想做空谷歌，谷歌做空保证金比例200%，小虎最多能做空10/200%=5万谷歌股票；小虎想买入微软，微软初始保证金100%（1/100%=1倍杠杆），小虎最多只能买入10万美元的微软股票 |\
| **currencyAssets** | CurrencyAssets |     | 按照交易币种区分的账户资产信息。详细说明见下方描述 |\
| consolidatedSegTypes | string |     | 聚合的Segment，现只有SEC和FUND会聚合。下面字段为聚合Segment数值的字段：cashAvailableForTrade、initMargin、maintainMargin、overnightMargin、excessLiquidation、overnightLiquidation、buyingPower、lockedFunds、leverage。 |\
| lockedFunds | Double | 0.0 | 锁定资产 |\
| uncollected | Double | 0.0 | 在途资金 |\
\
**`PrimeAssetItem.CurrencyAssets`说明：**\
\
| 名称  | 示例  | 说明  |\
| --- | --- | --- |\
| currency | USD | 当前的货币币种，常用货币包括： USD-美元，HKD-港币，SGD-新加坡币，CNH-人民币 |\
| cashBalance | 469140.39 | 可以交易的现金，加上已锁定部分的现金（如已购买但还未成交的股票，还包括其他一些情形也会有锁定现金情况） |\
| cashAvailableForTrade | 0.1273896 | 当前账号内可以交易的现金金额 |\
| forexRate | 1.0 | 当前币种对 baseCurrency 的汇率。比如 baseCurrency=USD, currency=HKD, forexRate=0.128 |\
\
**示例**\
\
Java\
\
    PrimeAssetRequest assetRequest = PrimeAssetRequest.buildPrimeAssetRequest("572386", Currency.USD);\
    assetRequest.setConsolidated(Boolean.TRUE);\
    PrimeAssetResponse primeAssetResponse = client.execute(assetRequest);\
    //查询证券相关资产信息\
    PrimeAssetItem.Segment segment = primeAssetResponse.getSegment(Category.S);\
    System.out.println("segment: " + JSONObject.toJSONString(segment));\
    //查询账号中美元相关资产信息\
    if (segment != null) {\
      PrimeAssetItem.CurrencyAssets assetByCurrency = segment.getAssetByCurrency(Currency.USD);\
      System.out.println("assetByCurrency: " + JSONObject.toJSONString(assetByCurrency));\
    }\
\
**返回示例**\
\
JSON\
\
    {\
        "code":0,\
        "data":{\
            "accountId":"572386",\
            "segments":[\
                {\
                    "buyingPower":878.52,\
                    "capability":"CASH",\
                    "cashAvailableForTrade":878.52,\
                    "cashBalance":6850.79,\
                    "category":"S",\
                    "consolidatedSegTypes":[\
                        "SEC",\
                        "FUND"\
                    ],\
                    "currency":"USD",\
                    "currencyAssets":[\
                        {\
                            "cashAvailableForTrade":35.89,\
                            "cashBalance":6008.16,\
                            "currency":"USD",\
                            "forexRate": 1.0\
                        },\
                        {\
                            "cashAvailableForTrade":5351.53,\
                            "cashBalance":5351.53,\
                            "currency":"HKD",\
                            "forexRate": 0.128\
                        },\
                        {\
                            "cashAvailableForTrade":1123.95,\
                            "cashBalance":1123.95,\
                            "currency":"CNH"\
                        },\
                        {\
                            "cashAvailableForTrade":0.12,\
                            "cashBalance":0.12,\
                            "currency":"EUR"\
                        }\
                    ],\
                    "equityWithLoan":8891.76,\
                    "excessLiquidation":6850.79,\
                    "grossPositionValue":438.78,\
                    "initMargin":2040.96,\
                    "leverage":0.23,\
                    "maintainMargin":2040.96,\
                    "netLiquidation":7289.57,\
                    "overnightLiquidation":6850.79,\
                    "overnightMargin":2040.96,\
                    "realizedPL":0,\
                    "unrealizedPL":73.29\
                },\
                {\
                    "buyingPower":878.52,\
                    "capability":"CASH",\
                    "cashAvailableForTrade":878.52,\
                    "cashBalance":0,\
                    "category":"F",\
                    "consolidatedSegTypes":[\
                        "SEC",\
                        "FUND"\
                    ],\
                    "currency":"USD",\
                    "currencyAssets":[\
                        {\
                            "cashAvailableForTrade":0,\
                            "cashBalance":0,\
                            "currency":"USD"\
                        },\
                        {\
                            "cashAvailableForTrade":0,\
                            "cashBalance":0,\
                            "currency":"HKD"\
                        },\
                        {\
                            "cashAvailableForTrade":0,\
                            "cashBalance":0,\
                            "currency":"CNH"\
                        }\
                    ],\
                    "equityWithLoan":8891.76,\
                    "excessLiquidation":6850.79,\
                    "grossPositionValue":1602.18,\
                    "initMargin":2040.96,\
                    "leverage":0.23,\
                    "maintainMargin":2040.96,\
                    "netLiquidation":1602.18,\
                    "overnightLiquidation":6850.79,\
                    "overnightMargin":2040.96,\
                    "realizedPL":1.25,\
                    "unrealizedPL":89.35\
                }\
            ],\
            "updateTimestamp":1704355652720\
        },\
        "message":"success",\
        "sign":"ZBp+e3IAcPbujB/BIijAh9F1PXQaIiqn5pxldBTPdz88W/hz6rezJsnmuvG+kLbieBWmRiVCt5Ah3eTM9ynLK4BZjRixo2OGJ0XcKotZf0qGDAF3E34acQSbH1te6xCEZMeDunptXNGUcveTgNW2dscLt121MtsLwoXh8T5bUS0=",\
        "success":true,\
        "timestamp":1704355652720\
    }\
\
* * *\
\
综合/模拟账号获取历史资产分析数据\
\
[](./accounts-java.md#%E7%BB%BC%E5%90%88%E6%A8%A1%E6%8B%9F%E8%B4%A6%E5%8F%B7%E8%8E%B7%E5%8F%96%E5%8E%86%E5%8F%B2%E8%B5%84%E4%BA%A7%E5%88%86%E6%9E%90%E6%95%B0%E6%8D%AE)\
\
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\
\
**对应的请求类：PrimeAnalyticsAssetRequest**\
\
**说明**\
\
获取综合/模拟账户的历史资产分析\
\
**参数**\
\
| 参数  | 类型  | 是否必填 | 描述  |\
| --- | --- | --- | --- |\
| account | string | Yes | 用户授权资金账户: 123123 |\
| start\_date | string | No  | 起始日期， 格式 yyyy-MM-dd, 如 '2022-01-01'。如不传则使用end\_date往前30天的日期 |\
| end\_date | string | No  | 截止日期， 格式 yyyy-MM-dd, 如 '2022-02-01'。如不传则使用当前日期 |\
| seg\_type | SegmentType | No  | 账户划分类型, 可选值有: SegmentType.SEC 代表证券; SegmentType.FUT 代表期货 |\
| currency | Currency | No  | 币种，包括 ALL/USD/HKD/CNH |\
| sub\_account | str | No  | 子账户(仅适用于机构账户)，若传该字段，则返回该子账户的资产 |\
| secret\_key | string | No  | 机构用户专用，交易员密钥。在ClientConfig.DEFAULT\_CONFIG中配置，个人开发者无需指定 |\
\
**返回**\
\
`com.tigerbrokers.stock.openapi.client.https.response.trade.PrimeAnalyticsAssetResponse`\
\
可用 `PrimeAnalyticsAssetItem.Summary summary = primeAssetResponse.getSummary()` 获取资产分析汇总数据； 使用 `List<PrimeAnalyticsAssetItem.HistoryItem> historyItems = primeAssetResponse.getHistory()`获取历史资产列表。具体参见示例代码\
\
**`PrimeAnalyticsAssetItem.Summary`说明：**\
\
| 名称  | 类型  | 说明  |\
| --- | --- | --- |\
| pnl | double | 盈亏金额 |\
| pnlPercentage | double | 收益率 |\
| annualizedReturn | double | 年化收益率(推算) |\
| overUserPercentage | double | 超过用户的百分比 |\
\
**`PrimeAnalyticsAssetItem.HistoryItem`说明：**\
\
| 名称  | 示例  | 说明  |\
| --- | --- | --- |\
| date | long | 日期时间戳，单位毫秒 |\
| pnl | double | 与上一日比的盈亏金额 |\
| pnlPercentage | double | 与上一日比的收益率 |\
| asset | double | 总资产金额 |\
| cashBalance | double | 现金余额 |\
| grossPositionValue | double | 市值  |\
| deposit | double | 入金金额 |\
| withdrawal | double | 出金金额 |\
\
**示例**\
\
Java\
\
        PrimeAnalyticsAssetRequest assetRequest = PrimeAnalyticsAssetRequest.buildPrimeAnalyticsAssetRequest(\
            "402901").segType(SegmentType.SEC).startDate("2021-12-01").endDate("2021-12-07");\
        PrimeAnalyticsAssetResponse primeAssetResponse = client.execute(assetRequest);\
        if (primeAssetResponse.isSuccess()) {\
          JSONObject.toJSONString(primeAssetResponse.getSummary());\
          JSONObject.toJSONString(primeAssetResponse.getHistory());\
        }\
\
**返回示例**\
\
JSON\
\
    {\
        "code":0,\
        "message":"success",\
        "timestamp":1657616435212,\
        "data":{\
            "summary":{\
                "pnl":691.18,\
                "pnlPercentage":0,\
                "annualizedReturn":0,\
                "overUserPercentage":0\
            },\
            "history":[\
                {\
                    "date":1638334800000,\
                    "asset":48827609.65,\
                    "pnl":0,\
                    "pnlPercentage":0,\
                    "cashBalance":48811698.59,\
                    "grossPositionValue":15911.06,\
                    "deposit":0,\
                    "withdrawal":0\
                },\
                {\
                    "date":1638421200000,\
                    "asset":48827687.69,\
                    "pnl":78.04,\
                    "pnlPercentage":0,\
                    "cashBalance":48811698.59,\
                    "grossPositionValue":15989.1,\
                    "deposit":0,\
                    "withdrawal":0\
                },\
                {\
                    "date":1638507600000,\
                    "asset":48827583.18,\
                    "pnl":-26.47,\
                    "pnlPercentage":0,\
                    "cashBalance":48811698.59,\
                    "grossPositionValue":15884.58,\
                    "deposit":0,\
                    "withdrawal":0\
                },\
                {\
                    "date":1638766800000,\
                    "asset":48827804.28,\
                    "pnl":194.63,\
                    "pnlPercentage":0,\
                    "cashBalance":48811698.59,\
                    "grossPositionValue":16105.68,\
                    "deposit":0,\
                    "withdrawal":0\
                },\
                {\
                    "date":1638853200000,\
                    "asset":48828300.83,\
                    "pnl":691.18,\
                    "pnlPercentage":0,\
                    "cashBalance":48811723,\
                    "grossPositionValue":16577.82,\
                    "deposit":0,\
                    "withdrawal":0\
                }\
            ]\
        },\
        "sign":"BFcQVHP4Rh0WAoQMAkVErZq1LKLlhPHx5X+77xNjpsIJF62Zr3T8UXDNvT3fSRA/Pt8cV8Ju3scYq/ollZU169ckh7rVpmeXSxJBaJ8Wfq5tOex7K1BkyHVEcH8i1c6aSph00Nm1yUqcTss/jVvN8uAXYoIBFCELV9nu7r1T4wA="\
    }\
\
* * *\
\
综合/模拟账号获取可转出资金\
\
[](./accounts-java.md#%E7%BB%BC%E5%90%88%E6%A8%A1%E6%8B%9F%E8%B4%A6%E5%8F%B7%E8%8E%B7%E5%8F%96%E5%8F%AF%E8%BD%AC%E5%87%BA%E8%B5%84%E9%87%91)\
\
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\
\
**对应的请求类：SegmentFundAvailableRequest**\
\
**说明**\
\
获取账户在对应Segment下的可转出资金\
\
**参数**\
\
| 参数  | 类型  | 是否必填 | 描述  |\
| --- | --- | --- | --- |\
| account | string | Yes | 用户授权账号，包括标准，模拟账号 |\
| from\_segment | string | Yes | 转出segment, FUT或SEC |\
| currency | string | No  | 转出币种，USD或HKD |\
\
**返回**\
\
`com.tigerbrokers.stock.openapi.client.https.response.trade.SegmentFundAvailableResponse`\
\
可用 `List<SegmentFundAvailableItem> items = response.getSegmentFundAvailableItems()` 获取各Segment可以转出资金金额的列表。 具体参见示例代码\
\
**`SegmentFundAvailableItem`说明：**\
\
| 名称  | 类型  | 说明  |\
| --- | --- | --- |\
| fromSegment | string | 转出segment，FUT或SEC |\
| currency | string | 转出币种，USD或HKD |\
| amount | double | 可转资金，单位：对应币种的元 |\
\
**示例**\
\
Java\
\
    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(\
          ClientConfig.DEFAULT_CONFIG);\
    \
    SegmentFundAvailableRequest request = SegmentFundAvailableRequest.buildRequest(\
            SegmentType.SEC, Currency.HKD);\
    \
    SegmentFundAvailableResponse response = client.execute(request);\
    System.out.println(JSONObject.toJSONString(response));\
    \
    // 获取具体数据\
    for (SegmentFundAvailableItem item : response.getSegmentFundAvailableItems()) {\
      System.out.println(JSONObject.toJSONString(item));\
    }\
\
**返回示例**\
\
JSON\
\
    {\
      "code": 0,\
      "data": [\
        {\
          "amount": 17607412.84,\
          "currency": "HKD",\
          "fromSegment": "SEC"\
        }\
      ],\
      "message": "success",\
      "sign": "jbxGKQiv5staJJOsN9CnMz25TxWk9jq6iZLksLg09aeP60QfFoSkNIGrnwdv3x0cgYc+SHj6vWdJGQ8FRo/DubxR6pyb6N6iiLl+TANQkvct0MERk7nygEhvQiYXD2q5gj2jPuDAfS6fVzkrYLWEaXQp3RrfqBDNJj+TRVhLRiw=",\
      "success": true,\
      "timestamp": 1679902705608\
    }\
\
* * *\
\
综合/模拟账号内部资金转账\
\
[](./accounts-java.md#%E7%BB%BC%E5%90%88%E6%A8%A1%E6%8B%9F%E8%B4%A6%E5%8F%B7%E5%86%85%E9%83%A8%E8%B5%84%E9%87%91%E8%BD%AC%E8%B4%A6)\
\
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\
\
**对应的请求类：SegmentFundTransferRequest**\
\
**说明**\
\
对账户在不同segment下的资金转账，如证券segment转期货segment\
\
**参数**\
\
| 参数  | 类型  | 是否必填 | 描述  |\
| --- | --- | --- | --- |\
| account | string | Yes | 用户授权账号，包括标准，模拟账号 |\
| from\_segment | string | Yes | 转出 segment，FUT 或 SEC |\
| to\_segment | string | Yes | 转入 segment，FUT 或 SEC，必须与 from\_segment 不同 |\
| currency | string | Yes | 转出币种，USD 或 HKD |\
| amount | double | Yes | 转账金额，单位：对应币种的元 |\
\
**返回**\
\
`com.tigerbrokers.stock.openapi.client.https.response.trade.SegmentFundResponse`\
\
可用 `SegmentFundItem item = response.getSegmentFundItem()` 获取转账结果对象。 具体参见示例代码\
\
**`SegmentFundItem`说明：**\
\
| 名称  | 类型  | 说明  |\
| --- | --- | --- |\
| id  | long | 转账记录ID |\
| fromSegment | string | 转出segment，FUT或SEC |\
| toSegment | string | 转入segment，FUT或SEC |\
| currency | string | 转出币种，USD或HKD |\
| amount | double | 转账金额，单位：对应币种的元 |\
| status | string | 状态(NEW/PROC/SUCC/FAIL/CANC) |\
| statusDesc | string | 状态描述(已提交/处理中/已到账/转账失败/已取消) |\
| message | string | 失败信息 |\
| settledAt | long | 到账时间戳 |\
| updatedAt | long | 更新时间戳 |\
| createdAt | long | 创建时间戳 |\
\
**示例**\
\
Java\
\
    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(\
          ClientConfig.DEFAULT_CONFIG);\
    SegmentFundTransferRequest request = SegmentFundTransferRequest.buildRequest(\
            SegmentType.SEC, SegmentType.FUT, Currency.HKD, 1000D);\
    \
    SegmentFundResponse response = client.execute(request);\
    System.out.println(JSONObject.toJSONString(response));\
\
**返回示例**\
\
JSON\
\
    {\
        "code":0,\
        "data":{\
            "amount":1000,\
            "createdAt":1680076000672,\
            "currency":"HKD",\
            "fromSegment":"SEC",\
            "id":30300805635506176,\
            "status":"NEW",\
            "statusDesc":"已提交",\
            "toSegment":"FUT",\
            "updatedAt":1680076000672\
        },\
        "message":"success",\
        "sign":"eBhM+F2Kmc1QA0LX5R5yAoz/Ugi1kizUFjjaY378zsXPj69XkMunOOmmOcUR0evo/toHIG1Scd4AlVameVDE7SWQsVt6B+L7UBjI1iU9top79ewxbXkGTc/e4ketQgHfqqF9aR/eHIdZRZjKOvNEdfWfjzKTmQJiAaHhXNVfFJ0=",\
        "success":true,\
        "timestamp":1680076000819\
    }\
\
* * *\
\
综合/模拟账号取消内部资金转账\
\
[](./accounts-java.md#%E7%BB%BC%E5%90%88%E6%A8%A1%E6%8B%9F%E8%B4%A6%E5%8F%B7%E5%8F%96%E6%B6%88%E5%86%85%E9%83%A8%E8%B5%84%E9%87%91%E8%BD%AC%E8%B4%A6)\
\
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\
\
**对应的请求类：SegmentFundCancelRequest**\
\
**说明**\
\
取消提交的资金转账，如果转账已成功，则无法取消，可以交换segment反向转回来。\
\
**参数**\
\
| 参数  | 类型  | 是否必填 | 描述  |\
| --- | --- | --- | --- |\
| account | string | Yes | 用户授权账号，包括标准，模拟账号 |\
| id  | long | Yes | 转账记录id |\
\
**返回**\
\
`com.tigerbrokers.stock.openapi.client.https.response.trade.SegmentFundResponse`\
\
可用 `SegmentFundItem item = response.getSegmentFundItem()` 获取取消转账结果对象。 具体参见示例代码\
\
**`SegmentFundItem`说明：**\
\
| 名称  | 类型  | 说明  |\
| --- | --- | --- |\
| id  | long | 转账记录ID |\
| fromSegment | string | 转出segment，FUT或SEC |\
| toSegment | string | 转入segment，FUT或SEC |\
| currency | string | 转出币种，USD或HKD |\
| amount | double | 转账金额，单位：对应币种的元 |\
| status | string | 状态(NEW/PROC/SUCC/FAIL/CANC) |\
| statusDesc | string | 状态描述(已提交/处理中/已到账/转账失败/已取消) |\
| message | string | 失败信息 |\
| settledAt | long | 到账时间戳 |\
| updatedAt | long | 更新时间戳 |\
| createdAt | long | 创建时间戳 |\
\
**示例**\
\
Java\
\
    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(\
          ClientConfig.DEFAULT_CONFIG);\
    SegmentFundCancelRequest request = SegmentFundCancelRequest.buildRequest(30300805635506176L);\
    \
    SegmentFundResponse response = client.execute(request);\
    if (response.isSuccess()) {\
      System.out.println(JSONObject.toJSONString(response));\
    } else {\
      System.out.println("cancel fail." + JSONObject.toJSONString(response));\
    }\
\
**返回示例**\
\
JSON\
\
    {\
        "code":1200,\
        "message":"standard account response error(fail:The transfer cannot be cancelled now)",\
        "timestamp":1680077452633\
    }\
\
* * *\
\
综合/模拟账号内部资金转账历史记录查询\
\
[](./accounts-java.md#%E7%BB%BC%E5%90%88%E6%A8%A1%E6%8B%9F%E8%B4%A6%E5%8F%B7%E5%86%85%E9%83%A8%E8%B5%84%E9%87%91%E8%BD%AC%E8%B4%A6%E5%8E%86%E5%8F%B2%E8%AE%B0%E5%BD%95%E6%9F%A5%E8%AF%A2)\
\
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\
\
**对应的请求类：SegmentFundHistoryRequest**\
\
**说明**\
\
查询账号不同Segment间的历史转账记录。按时间倒序排列\
\
**参数**\
\
| 参数  | 类型  | 是否必填 | 描述  |\
| --- | --- | --- | --- |\
| account | string | Yes | 用户授权账号，包括标准，模拟账号 |\
| limit | Integer | No  | 返回最近转账记录数。默认为100，最大500 |\
\
**返回**\
\
`com.tigerbrokers.stock.openapi.client.https.response.trade.SegmentFundsResponse`\
\
可用 `List<SegmentFundItem> items = response.getSegmentFundItems()` 获取各Segment间的历史转账记录列表。具体参见示例代码\
\
**`SegmentFundItem`说明：**\
\
| 名称  | 类型  | 说明  |\
| --- | --- | --- |\
| id  | long | 转账记录 ID |\
| fromSegment | string | 转出 segment，FUT 或 SEC |\
| toSegment | string | 转入 segment，FUT 或 SEC |\
| currency | string | 转出币种，USD 或 HKD |\
| amount | double | 转账金额，单位：对应币种的元 |\
| status | string | 状态（NEW/PROC/SUCC/FAIL/CANC） |\
| statusDesc | string | 状态描述(已提交/处理中/已到账/转账失败/已取消) |\
| message | string | 失败信息 |\
| settledAt | long | 到账时间戳 |\
| updatedAt | long | 更新时间戳 |\
| createdAt | long | 创建时间戳 |\
\
**示例**\
\
Java\
\
    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(\
          ClientConfig.DEFAULT_CONFIG);\
    \
    SegmentFundHistoryRequest request = SegmentFundHistoryRequest.buildRequest(30);\
    SegmentFundsResponse response = client.execute(request);\
    if (response.isSuccess()) {\
      System.out.println(JSONObject.toJSONString(response));\
    } else {\
      System.out.println("cancel fail." + JSONObject.toJSONString(response));\
    }\
\
**返回示例**\
\
JSON\
\
    {\
        "code":0,\
        "data":[\
            {\
                "amount":1000,\
                "createdAt":1680076001000,\
                "currency":"HKD",\
                "fromSegment":"SEC",\
                "id":30300805635506176,\
                "settledAt":1680076001000,\
                "status":"SUCC",\
                "statusDesc":"已到账",\
                "toSegment":"FUT",\
                "updatedAt":1680076001000\
            },\
            {\
                "amount":1000,\
                "createdAt":1679307031000,\
                "currency":"HKD",\
                "fromSegment":"SEC",\
                "id":30200015261794304,\
                "settledAt":1679307032000,\
                "status":"SUCC",\
                "statusDesc":"已到账",\
                "toSegment":"FUT",\
                "updatedAt":1679307031000\
            }\
        ],\
        "message":"success",\
        "sign":"g9B9Q20F56qoUVs1ULaWZMC5h1DYp7E7GeQnkd2TR5tw2R85TnO7xVb79sqB4EFZEEtI+So8gqh71hABiz31VrQy32zGmYGSgFA94jI6sFh4//BJA0IG9vGx1PmO/rv9aomK+17XGJ4PDrqxHBuGczaX4i65Wvoyt5wHdAx2qoU=",\
        "success":true,\
        "timestamp":1680085312913\
    }\
\
* * *\
\
获取最大可交易数量\
\
[](./accounts-java.md#%E8%8E%B7%E5%8F%96%E6%9C%80%E5%A4%A7%E5%8F%AF%E4%BA%A4%E6%98%93%E6%95%B0%E9%87%8F)\
\
------------------------------------------------------------------------------------------------------------------------------------------------\
\
**对应的请求类：EstimateTradableQuantityRequest**\
\
**说明**\
\
查询账户下的对某个标的的最大可买可卖数量，支持股票、期权，暂不支持期货。\
\
**参数**\
\
| 参数  | 类型  | 是否必填 | 描述  |\
| --- | --- | --- | --- |\
| account | String | Yes | 账户，目前仅支持综合账户 |\
| symbol | String | Yes | 股票代码 |\
| expiry | String | No  | 到期日,交易品种是OPT/WAR/IOPT类型时必传时必传 yyyyMMdd |\
| right | String | No  | CALL/PUT，交易品种是OPT/WAR/IOPT类型时必传时必传 |\
| strike | String | No  | 行权价，交易品种是OPT/WAR/IOPT类型时必传时必传 |\
| seg\_type | SegmentType | No  | SEC，暂只支持SEC |\
| sec\_type | SecType | No  | STK: 股票/FUT: 期货/OPT: 期权/WAR: 窝轮/IOPT: 牛熊证，期货暂不支持 |\
| action | ActionType | Yes | 交易方向 BUY/SELL |\
| order\_type | OrderType | Yes | 订单类型 |\
| limit\_price | double | No  | 限价，当 order\_type 为LMT,STP\_LMT时该参数必需 |\
| stop\_price | double | No  | 止损价，当 order\_type 为STP,STP\_LMT时该参数必需 |\
| secretKey | String | No  | 交易员密钥，机构用户专用 |\
\
**返回**\
\
| 字段  | 类型  | 说明  |\
| --- | --- | --- |\
| tradableQuantity | Double | 现金可买可卖数量(如action为BUY，返回为可买数量，反之为可卖数量) |\
| financingQuantity | Double | 融资融券可买可卖数量 (不适用于现金账户) |\
| positionQuantity | Double | 持仓数量 |\
| tradablePositionQuantity | Double | 持仓可交易数量 |\
\
**示例**\
\
Java\
\
    EstimateTradableQuantityRequest request = EstimateTradableQuantityRequest.buildRequest(\
            SecType.STK, "AAPL", ActionType.BUY, OrderType.LMT, 150D, null);\
    \
    EstimateTradableQuantityResponse response = client.execute(request);\
    if (response.isSuccess()) {\
      System.out.println(JSONObject.toJSONString(response));\
    } else {\
      System.out.println("fail." + JSONObject.toJSONString(response));\
    }\
\
**返回示例**\
\
JSON\
\
    {\
        "code":0,\
        "data":{\
            "positionQuantity":1,\
            "tradablePositionQuantity":1,\
            "tradableQuantity":45\
        },\
        "message":"success",\
        "sign":"mBmBqdIum+F8PE9yHis8v64My2P9rBorsYwtLNAr/Hei6oRedvl5YyfBV2H9zHUHOYcJJDukrD74IfnUsJW1PS6YUQdt+MirNc3Bm51gMrjiVFed8JTto4wRqXvuX57wcA3gMCLVDJkbqjU5VD64cCl28A38N8vdgRo7HgcS8pM=",\
        "success":true,\
        "timestamp":1681789228649\
    }\
\
* * *\
\
获取出入金记录\
\
[](./accounts-java.md#%E8%8E%B7%E5%8F%96%E5%87%BA%E5%85%A5%E9%87%91%E8%AE%B0%E5%BD%95)\
\
----------------------------------------------------------------------------------------------------------------------------\
\
**对应的请求类：DepositWithdrawRequest**\
\
**说明**\
\
查询账户的出入金记录\
\
**参数**\
\
| 参数  | 类型  | 是否必填 | 描述  |\
| --- | --- | --- | --- |\
| account | String | Yes | 账户，目前仅支持综合账户 |\
| secretKey | String | No  | 机构用户专用，交易员密钥 |\
| lang | Language | No  | 语言枚举值：en\_US，zh\_CN，zh\_TW，默认：en\_US，枚举值详见：[语言枚举](./appendix-enum-java.md#lang) |\
\
**返回**\
\
| 字段  | 类型  | 说明  |\
| --- | --- | --- |\
| id  | long | ID  |\
| refId | string | 关联业务ID |\
| type | int | 资金类型(1：入金；3：出金；20：出金费用；21：出金退款；22：出金失败-退款；23：出金费用-退款) |\
| typeDesc | string | 资金类型描述 |\
| currency | string | 币种  |\
| amount | double | 金额  |\
| businessDate | string | 业务日期 |\
| completedStatus | bool | 是否结束 |\
| createdAt | long | 创建时间戳 |\
| updatedAt | long | 更新时间戳 |\
\
**示例**\
\
Java\
\
    DepositWithdrawRequest request = DepositWithdrawRequest.newRequest();\
    request.lang(Language.en_US);\
    \
    DepositWithdrawResponse response = client.execute(request);\
    if (response.isSuccess()) {\
      System.out.println(JSONObject.toJSONString(response));\
    } else {\
      System.out.println("fail." + JSONObject.toJSONString(response));\
    }\
\
**返回示例**\
\
JSON\
\
    {\
        "code": 0,\
        "data": [\
            {\
                "amount": 3000,\
                "businessDate": "2024/11/15",\
                "completedStatus": true,\
                "createdAt": 1731663420532,\
                "currency": "SGD",\
                "id": 24924145879575127,\
                "refId": "TEST_API-37062471908982784",\
                "type": 1,\
                "typeDesc": "Deposit",\
                "updatedAt": 1731663420532\
            },\
            {\
                "amount": 300000,\
                "businessDate": "2024/11/15",\
                "completedStatus": true,\
                "createdAt": 1731664787127,\
                "currency": "USD",\
                "id": 24924145879575129,\
                "refId": "TEST_API-37062651031584768",\
                "type": 1,\
                "typeDesc": "Deposit",\
                "updatedAt": 1731664787127\
            }\
        ],\
        "message": "success",\
        "sign": "LTccL5hUIEYzqmQsev2X8gVzwlEJar8/ybIUHMNd9H6K48XrJiG2LgC17GvFhVy1lKUabrV2GZ9YsBvJQlEoDEe1d6EtnbBWn7bAMdJktsUhlAxJiEqS/aVIIdGe6u7Fo/laKqmxx5n1caYIVaU17TvJxjHufD7pfkoPbOoWwbM=",\
        "success": true,\
        "timestamp": 1735134267185\
    }\
\
* * *\
\
获取资金明细\
\
[](./accounts-java.md#%E8%8E%B7%E5%8F%96%E8%B5%84%E9%87%91%E6%98%8E%E7%BB%86)\
\
------------------------------------------------------------------------------------------------------------------\
\
**对应的请求类：FundDetailsRequest**\
\
**说明**\
\
获取资金明细\
\
**参数**\
\
| 参数  | 类型  | 是否必填 | 描述  |\
| --- | --- | --- | --- |\
| segTypes | List<String> | Yes | segment 类型列表, 如：\["SEC", "FUT"\] |\
| account | String | Yes | 账户，目前仅支持综合账户 |\
| fundType | FundType | No  | 资金类型，包括：ALL 全部、DEPOSIT\_WITHDRAW 出入金、TRADE 交易、FEE 费用、FUNDS\_TRANSFER 资金划拨、FOREX 货币兑换、CORPORATE\_ACTION 公司行动、ACTIVITY\_AWARD 活动、OTHER 其他. 默认 ALL |\
| currency | String | No  | 币种  |\
| startDate | String | No  | 开始日期，格式为 yyyy-MM-dd |\
| endDate | String | No  | 结束日期，格式为 yyyy-MM-dd |\
| start | Long | No  | 起始序号，从 0 开始，比如每页设置 limit 50, 前两页共返回了 100 条，则请求第 3 页时，start 需要传 100，即从第 101 条继续查询 |\
| limit | Long | No  | 最大返回条数，默认 50，最大 100 |\
| secretKey | String | No  | 机构用户专用，交易员密钥 |\
| lang | Language | No  | 语言枚举值：en\_US，zh\_CN，zh\_TW，默认：en\_US，枚举值详见：[语言枚举](./appendix-enum-java.md#lang) |\
\
**返回**\
\
| 字段  | 类型  | 说明  |\
| --- | --- | --- |\
| id  | Long | 记录ID |\
| desc | String | 描述  |\
| currency | String | 币种  |\
| segType | String | Segment type |\
| type | String | 资金类型 |\
| amount | Double | 金额  |\
| businessDate | String | 老虎定义的业务日期，所有市场同一交易日的资金变动会记录到同一业务日期中 |\
| updatedAt | Long | 流水更新时间戳 |\
| page | Integer | 当前页码 |\
| limit | Integer | 每页记录数量 |\
| itemCount | Integer | 总记录数量 |\
| pageCount | Integer | 总页数 |\
| timestamp | Long | 时间戳 |\
| contractName | String | 合约名称 |\
\
**示例**\
\
Java\
\
    FundDetailsRequest request = FundDetailsRequest.buildFundDetailsRequest(account,\
        Lists.newArrayList(SegmentType.SEC.name()), 0L, 5L);\
    request.setCurrency("HKD");\
    request.setFundType("ALL");\
    request.setStartDate("2025-01-01");\
    request.setEndDate("2025-04-01");\
    FundDetailsResponse response = client.execute(request);\
    \
    if (response.isSuccess()) {\
      System.out.println(JSONObject.toJSONString(response));\
    } else {\
      System.out.println("fail." + JSONObject.toJSONString(response));\
    }\
\
**返回示例**\
\
JSON\
\
    {\
      "code": 0,\
      "message": "success",\
      "timestamp": 1745840017490,\
      "data": {\
        "page": 1,\
        "limit": 5,\
        "itemCount": 5,\
        "pageCount": 1,\
        "timestamp": 1745840017495,\
        "items": [\
          {\
            "id": "3969803304",\
            "currency": "HKD",\
            "type": "Internal Funds Transfer Out",\
            "segType": "SEC",\
            "amount": -1,\
            "businessDate": "2025-03-25",\
            "updatedAt": 1742875771000\
          },\
          {\
            "id": "3942009038",\
            "currency": "HKD",\
            "type": "Financing Tnterest",\
            "segType": "SEC",\
            "amount": -2.43,\
            "businessDate": "2025-03-06",\
            "updatedAt": 1741247923000\
          },\
          {\
            "id": "3897595901",\
            "currency": "HKD",\
            "type": "Financing Tnterest",\
            "segType": "SEC",\
            "amount": -2.67,\
            "businessDate": "2025-02-06",\
            "updatedAt": 1738828190000\
          },\
          {\
            "id": "3877434674",\
            "currency": "HKD",\
            "type": "Currency Exchange - Quotation Currency",\
            "segType": "SEC",\
            "amount": 7.77,\
            "businessDate": "2025-01-17",\
            "updatedAt": 1737117790000\
          },\
          {\
            "id": "3865693144",\
            "currency": "HKD",\
            "type": "Financing Tnterest",\
            "segType": "SEC",\
            "amount": -2.75,\
            "businessDate": "2025-01-07",\
            "updatedAt": 1736236455000\
          }\
        ]\
      }\
    }\

