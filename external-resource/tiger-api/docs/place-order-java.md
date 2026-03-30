# 下单交易

创建订单

[](./place-order-java.md#%E5%88%9B%E5%BB%BA%E8%AE%A2%E5%8D%95)

-------------------------------------------------------------------------------------------------

**对应的请求类：TradeOrderRequest**

**说明**

交易下单接口。关于如何选择标的、订单类型、方向数量等，请见下方说明。

**请在运行程序前结合本文档的 [交易订单类型](./trade-rules.md#order-type)
 部分，检查您的账户是否支持所请求的订单，并检查交易规则是否允许在程序运行时段对特定标的下单**。

> ⚠️
> 
> **CAUTION**
> 
> 1.  市价单（MKT）和止损单（STP）不支持盘前盘后阶段交易，在调用下单接口时，需要把 outside\_rth 设置为 false
> 2.  可做空标的，目前不支持锁仓，无法同时持有同一标的的多头与空头头寸
> 3.  加订单的主订单类型，目前仅支持限价单
> 4.  限价价格不匹配 tickSize 时，可参考合约返回 tickSizes 字段，利用 StockPriceUtils 工具类判断是否匹配，修复价格符合 tickSize 要求
> 5.  市价单（MKT）和模拟账号，不支持将参数 time\_in\_force 设置为 GTC
> 6.  模拟账号暂不支持窝轮和牛熊证的订单

**订单状态说明**

1.  如何判断综合和模拟账号的部分成交状态？
    
    当订单状态不是 Initial 和 Filled（有可能是 PendingSubmit，Cancelled，Invalid，Inactive 其中一种）时，都有可能是部分成交的状态，可以通过订单成交数量是否大于 0 来判断。
    
2.  如何判断环球账号部分成交状态？
    
    订单状态是 Filled，且订单成交数量大于 0。
    

**订单状态变化流程:**

![](https://files.readme.io/678c6372b5147e9f6a62bbb5ba5bcf9bc9c3f7004ded7a0b8861d1770d1c7c18-img_v3_02pn_27fe097d-0f3e-4374-a9b6-f7086379b97g.png)

**其他说明**

*   美国市场部分指数期权合约（如 SPXW），以及 IWM/SPY/QQQ 等 ETF 期权，支持交易周内（周一至周五）到期的期权合约（具体可交易到期日以交易所实际挂牌为准）。
*   禁止直接开立反向头寸。例如，当前持有 100 股多头头寸时，直接卖出 200 股（意图建立 100 股净空头）的操作将被拒绝，需要先平掉现有的 100 股多头头寸，再进行新的卖出操作。

**参数**

| 参数  | 类型  | 描述  | 市价单 | 限价单 | 止损单 | 止损限价单 | 跟踪止损单 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| account | string | 用户授权账户: 402901 | 必填  | 必填  | 必填  | 必填  | 必填  |
| order\_id | int | 订单编号，作用是防止重复下单。可以通过订单号接口获取。如果传0，则服务器端会自动生成订单编号，传0时无法防止重复下单，请谨慎选择 | 选填  | 选填  | 选填  | 选填  | 选填  |
| symbol | string | 股票代码，如：AAPL；（sec\_typ为窝轮牛熊证时，在轮/牛熊证列表中名称下面的5位数字） | 必填  | 必填  | 必填  | 必填  | 必填  |
| sec\_type | string | 合约类型 (STK 股票;OPT 美股期权; WAR 港股窝轮; IOPT 港股牛熊证; FUT 期货; FUND 基金；CC 数字货币) | 必填  | 必填  | 必填  | 必填  | 必填  |
| action | string | 交易方向 BUY/SELL | 必填  | 必填  | 必填  | 必填  | 必填  |
| order\_type | string | 订单类型 MKT (市价单), LMT (限价单), STP(止损单), STP\_LMT (止损限价单), TRAIL (跟踪止损单) | MKT | LMT | STP | STP\_LMT | TRAIL |
| total\_quantity | long | 下单数量 (港股，沪港通，窝轮，牛熊证有最小数量限制) | 必填  | 必填  | 必填  | 必填  | 必填  |
| total\_quantity\_scale | int | 下单数量的偏移量，默认为0。碎股单的total\_quantity 和 total\_quantity\_scale 结合起来代表真实下单数量，如 total\_quantity=111 total\_quantity\_scale=2，那么真实 quantity=111\*10^(-2)=1.11 | 选填  | 选填  | 选填  | 选填  | 选填  |
| cash\_amount | Double | 订单金额 (基金等金额订单) | 选填  | 不填  | 不填  | 不填  | 不填  |
| limit\_price | double | 限价，当 order\_type 为LMT，STP\_LMT时该参数必需 | 不填  | 必填  | 不填  | 必填  | 不填  |
| aux\_price | double | 股票订单止损触发价。含义为价差，与trailing\_percent同时存在时被trailing\_percent覆盖。当 order\_type 为STP，STP\_LMT时该参数必需，当 order\_type 为 TRAIL时，为跟踪额 | 不填  | 不填  | 必填  | 必填  | 选填  |
| trailing\_percent | double | 跟踪止损单-止损百分比 。当 order\_type 为 TRAIL时，aux\_price和trailing\_percent两者互斥，优先使用trailing\_percent | 不填  | 不填  | 不填  | 不填  | 选填  |
| outside\_rth | boolean | true: 允许盘前盘后交易(美股专属)，false: 不允许，默认为允许。（市价单、止损单、跟踪止损单只在盘中有效，将忽略outside\_rth参数） | 不填  | 选填  | 选填  | 不填  | 选填  |
| trading\_session\_type | TradingSessionType | 美股订单时段（仅限价单）。枚举值[订单时段](./appendix-enum-java.md#trading-session-type) | 不填  | 选填  | 不填  | 选填  | 不填  |
| adjust\_limit | double | 价格微调幅度（默认为0表示不调整，正数为向上调整，负数向下调整），对传入价格自动调整到合法价位上。例如：0.001代表向上调整且幅度不超过 0.1%；-0.001代表向下调整且幅度不超过0.1%。默认0表示不调整 | 不填  | 选填  | 选填  | 选填  | 选填  |
| market | string | 市场（美股 US 港股HK 沪港通CN） | 选填  | 选填  | 选填  | 选填  | 选填  |
| currency | string | 货币（美股USD 港股HKD 沪港通CNH） | 选填  | 选填  | 选填  | 选填  | 选填  |
| time\_in\_force | string | 订单有效期，只能是 DAY（当日有效）、GTC（取消前有效）、GTD（在指定时间前有效），默认为 DAY | 选填  | 选填  | 选填  | 选填  | 选填  |
| expire\_time | long | 订单有效的截止时间，13位的时间戳，精准到秒（time\_in\_force为GTC时为必填，其他类型时无效） | 不填  | 选填  | 选填  | 不填  | 选填  |
| exchange | string | 交易所（美股 SMART 港股SEHK 沪港通SEHKNTL 深港通SEHKSZSE）否 | 选填  | 选填  | 选填  | 选填  | 选填  |
| expiry | string | 过期日（期权、窝轮、牛熊证专属） | 选填  | 选填  | 选填  | 选填  | 选填  |
| strike | string | 行权价（期权、窝轮、牛熊证专属） | 选填  | 选填  | 选填  | 选填  | 选填  |
| right | string | 期权方向 PUT/CALL （期权、窝轮、牛熊证专属） | 选填  | 选填  | 选填  | 选填  | 选填  |
| multiplier | float | 乘数，每手对应的数量（期权、窝轮、牛熊证专属） | 选填  | 选填  | 选填  | 选填  | 选填  |
| local\_symbol | string | 窝轮、牛熊证该字段必填，在APP中窝轮/牛熊证列表中名称下面的5位数字 | 选填  | 选填  | 选填  | 选填  | 选填  |
| secret\_key | string | 交易员密钥，机构用户专用 | 选填  | 选填  | 选填  | 选填  | 选填  |
| user\_mark | string | 下单备注信息，下单后不能修改，查询订单时返回userMark信息 | 选填  | 选填  | 选填  | 选填  | 选填  |

  

*   附加订单参数
    
    > 附加订单（Attached Order ）是指能通过附加的子订单对主订单起到止盈或止损效果的订单，可以附加的子订单类型有限价单（可用于止盈）、止损限价单/止损单（可用于止损）。通过增加以下参数可以实现附加订单
    
    | 参数  | 类型  | 描述  | 附加止损 | 附加止盈 | 附加跟踪止损 | 附加括号 |
    | --- | --- | --- | --- | --- | --- | --- |
    | attach\_type | string | 附加订单类型，下附加订单时必填。(order\_type应为LMT): PROFIT-止盈单，LOSS-止损单，BRACKETS-括号订单（包含附加止盈单和附加止损单） | 必填  | 必填  | 必填  | 必填  |
    | profit\_taker\_orderId | int | 止盈单编号，可以通过订单号接口获取。如果传0，则服务器端会自动生成止盈单编号 | 不填  | 选填  | 选填  | 选填  |
    | profit\_taker\_price | double | 止盈单价格，下止盈单时必填 | 不填  | 必填  | 不填  | 必填  |
    | profit\_taker\_tif | string | 同time\_in\_force字段，订单有效期，只能是 DAY（当日有效）和GTC（取消前有效），下止盈单时必填 | 不填  | 必填  | 不填  | 必填  |
    | profit\_taker\_rth | boolean | 同outside\_rth字段 | 不填  | 必填  | 不填  | 必填  |
    | stop\_loss\_orderId | int | 止损单编号，可以通过订单号接口获取。如果传0，则服务器端会自动生成止损单编号 | 必填  | 不填  | 不填  | 必填  |
    | stop\_loss\_price | double | 止损单价格（止损单的触发价），下止损单时必填 | 必填  | 不填  | 不填  | 必填  |
    | stop\_loss\_limit\_price | double | 止损单的执行限价（暂只对综合账号有效）。止损单的限价没有填写时，为附加止损市价单 | 选填  | 不填  | 不填  | 选填  |
    | stop\_loss\_tif | string | 同time\_in\_force字段，订单有效期，只能是 DAY（当日有效）和GTC（取消前有效），下止损单时必填 | 必填  | 不填  | 必填  | 必填  |
    | stop\_loss\_trailing\_percent | double | 跟踪止损单-止损百分比，当下跟踪止损单时，止损百分比（stopLossTrailingPercent）和止损额（stopLossTrailingAmount）其中一项必填，如果都填时，会使用止损百分比作为参数。 | 选填  | 不填  | 二选一 | 选填  |
    | stop\_loss\_trailing\_amount | double | 跟踪止损单-止损额，当下跟踪止损单时，止损百分比（stopLossTrailingPercent）和止损额（stopLossTrailingAmount）其中一项必填，如果都填时，会使用止损百分比作为参数。 | 选填  | 不填  | 二选一 | 选填- - |
    
      
    
*   TWAP/VWAP订单参数
    
    > TWAP/VWAP订单，只支持美股股票标的，只能在盘中下单，不支持预挂单
    
    | 参数  | 类型  | 算法参数 | 描述  | TWAP | VWAP |
    | --- | --- | --- | --- | --- | --- |
    | order\_type | string |     | 订单类型，TWAP/VWAP | 必填  | 必填  |
    | account | string |     | 资金账号 | 必填  | 必填  |
    | symbol | string |     | 股票代码 如：AAPL | 必填  | 必填  |
    | sec\_type | string |     | 只支持STK | 必填  | 必填  |
    | total\_quantity | boolean |     | 订单数量 | 必填  | 必填  |
    | algo\_params | `List<TagValue>` |     | 算法参数 | 选填  | 选填  |
    | \-  | long | start\_time | 策略开始时间(时间戳) | 选填  | 选填  |
    | \-  | long | end\_time | 策略结束时间(时间戳) | 选填  | 选填  |
    | \-  | string | participation\_rate | 最大参与率(成交量为日均成交量的最大比例，0.01-0.5) | 不填  |     |
    
      
    
*   **返回**
    
    | 名称  | 类型  | 说明  |
    | --- | --- | --- |
    | id  | long | 唯一单号ID,可用于查询订单/修改订单/取消订单 |
    | subIds | `List<Long>` | 附加单时，返回子订单号ID列表 |
    | orders | `List<TradeOrder>` | 返回订单详细信息 |
    

* * *

构建合约对象

[](./place-order-java.md#%E6%9E%84%E5%BB%BA%E5%90%88%E7%BA%A6%E5%AF%B9%E8%B1%A1)

---------------------------------------------------------------------------------------------------------------------

Java

    // 美股股票合约
    ContractItem contract = ContractItem.buildStockContract("SPY", "USD");
    
    // 港股股票合约
    ContractItem contract = ContractItem.buildStockContract("00700", "HKD");
    
    // 港股窝轮合约（需要注意同一个symbol，环球账号和综合账号的expiry可能不同）
    ContractItem contract = ContractItem.buildWarrantContract("13745", "20211217", 719.38D, Right.CALL.name());
    // 港股牛熊证合约
    ContractItem contract = ContractItem.buildCbbcContract("50296", "20220331", 457D, Right.CALL.name());
    
    // 美股期权合约
    ContractItem contract = ContractItem.buildOptionContract("AAPL  190118P00160000");
    ContractItem contract = ContractItem.buildOptionContract("AAPL", "20211119", 150.0D, "CALL");
    
    // 期货合约
    // 环球账户
    ContractItem contract = ContractItem.buildFutureContract("CL", "USD", "SGX", "20190328", 1.0D);
    // 综合账户
    ContractItem contract = ContractItem.buildFutureContract("CL2112", "USD");

* * *

构建订单

[](./place-order-java.md#%E6%9E%84%E5%BB%BA%E8%AE%A2%E5%8D%95)

-------------------------------------------------------------------------------------------------

### 

市价单（MKT）

[](./place-order-java.md#%E5%B8%82%E4%BB%B7%E5%8D%95mkt)

Java

    // get contract(use default account)
    ContractRequest contractRequest = ContractRequest.newRequest(new ContractModel("AAPL"));
    ContractResponse contractResponse = client.execute(contractRequest);
    ContractItem contract = contractResponse.getItem();
    // market order(use default account)
    TradeOrderRequest request = TradeOrderRequest.buildMarketOrder(contract, ActionType.BUY, 10);
    TradeOrderResponse response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));
    
    // get contract(use account parameter)
    ContractRequest contractRequest = ContractRequest.newRequest(new ContractModel("AAPL"), "402901");
    ContractResponse contractResponse = client.execute(contractRequest);
    ContractItem contract = contractResponse.getItem();
    // market order(use account parameter)
    request = TradeOrderRequest.buildMarketOrder("402901", contract, ActionType.BUY, 10);
    response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));

### 

限价单（LMT）

[](./place-order-java.md#%E9%99%90%E4%BB%B7%E5%8D%95lmt)

Java

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, ActionType.BUY, 1, 100.0d);
    TradeOrderResponse response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));
    
    // use account parameter
    request = TradeOrderRequest.buildLimitOrder("402901", contract, ActionType.BUY, 1, 100.0d);
    // set user_mark
    request.setUserMark("test001");
    // set GTD order's expire_time
    request.setTimeInForce(TimeInForce.GTD);
    request.setExpireTime(1669363583804L);
    response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));

### 

夜盘/ 全时段订单（overnight/full-time）

[](./place-order-java.md#%E5%A4%9C%E7%9B%98-%E5%85%A8%E6%97%B6%E6%AE%B5%E8%AE%A2%E5%8D%95overnightfull-time)

仅支持美股

Java

    // place overnight order in the US market
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder("402901", contract, ActionType.BUY, 1, 200.0d);
    request.setTradingSessionType(TradingSessionType.OVERNIGHT);
    TradeOrderResponse response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));
    
    // place full-time order in the US market
    request = TradeOrderRequest.buildLimitOrder("402901", contract, ActionType.BUY, 1, 200.0d);
    request.setTradingSessionType(TradingSessionType.FULL);
    response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));

### 

竞价单（AM/AL）

[](./place-order-java.md#%E7%AB%9E%E4%BB%B7%E5%8D%95amal)

Java

    // auction order in hk market
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder("402901", contract, ActionType.BUY, 100, 100.0d);
    // 盘前竞价: AM or AL + OPG, 如果未成交参与盘中交易; 盘后竞价: AM or AL + DAY
    // participate in the pre-market auction, set auction limit order
    request.setAuctionOrder(OrderType.AL, TimeInForce.OPG);
    response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));
    
    request = TradeOrderRequest.buildMarketOrder("402901", contract, ActionType.BUY, 100);
    // Participate in the after-hours auction, set auction market order
    request.setAuctionOrder(OrderType.AM, TimeInForce.OPG);
    response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));

### 

止损单（STP）

[](./place-order-java.md#%E6%AD%A2%E6%8D%9F%E5%8D%95stp)

Java

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildStopOrder(contract, ActionType.BUY, 1, 120.0d);
    TradeOrderResponse response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));
    
    // use account parameter
    request = TradeOrderRequest.buildStopOrder("402901", contract, ActionType.BUY, 1, 120.0d);
    response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));

### 

止损限价单(STP\_LMT)

[](./place-order-java.md#%E6%AD%A2%E6%8D%9F%E9%99%90%E4%BB%B7%E5%8D%95stp_lmt)

Java

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildStopLimitOrder(contract, ActionType.BUY, 1,150d,130.0d);
    TradeOrderResponse response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));
    
    // use account parameter
    request = TradeOrderRequest.buildStopLimitOrder("402901", contract, ActionType.BUY, 1,150d,130.0d);
    response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));

### 

跟踪止损单(TRAIL)

[](./place-order-java.md#%E8%B7%9F%E8%B8%AA%E6%AD%A2%E6%8D%9F%E5%8D%95trail)

Java

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildTrailOrder(contract, ActionType.BUY, 1,10d,130.0d);
    TradeOrderResponse response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));
    
    // use account parameter. standard account currently not supported
    request = TradeOrderRequest.buildTrailOrder("402901", contract, ActionType.BUY, 1, 10d, 130.0d);
    response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));

### 

主订单+附加止盈单

[](./place-order-java.md#%E4%B8%BB%E8%AE%A2%E5%8D%95%E9%99%84%E5%8A%A0%E6%AD%A2%E7%9B%88%E5%8D%95)

Java

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, ActionType.BUY, 1, 199d);
    TradeOrderRequest.addProfitTakerOrder(request, 250D, TimeInForce.DAY, Boolean.FALSE);
    TradeOrderResponse response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));
    
    // use account parameter
    request = TradeOrderRequest.buildLimitOrder("402901", contract, ActionType.BUY, 1, 199d);
    TradeOrderRequest.addProfitTakerOrder(request, 250D, TimeInForce.DAY, Boolean.FALSE);
    response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));

### 

主订单+附加止损单

[](./place-order-java.md#%E4%B8%BB%E8%AE%A2%E5%8D%95%E9%99%84%E5%8A%A0%E6%AD%A2%E6%8D%9F%E5%8D%95)

Java

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, ActionType.BUY, 1, 129d);
    TradeOrderRequest.addStopLossOrder(request, 100D, TimeInForce.DAY);
    TradeOrderResponse response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));
    
    // use account parameter
    request = TradeOrderRequest.buildLimitOrder("402901", contract, ActionType.BUY, 1, 129d);
    // 添加附加止损市价单，附加止损价格是触发价(不支持期权标的)
    TradeOrderRequest.addStopLossOrder(request, 100D, TimeInForce.DAY);
    response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));
    
    // 期权可以使用附加止损限价单
    ContractItem optionContract = ContractItem.buildOptionContract("AAPL", "20211231", 175.0D, "CALL");
    request = TradeOrderRequest.buildLimitOrder("402901", optionContract, ActionType.BUY, 1, 2.0d);
    // 添加附加止损限价单，其中第一个价格1.7是触发价，第二个价格1.69是附加止损单的挂单限价(暂只支持综合账号)
    TradeOrderRequest.addStopLossLimitOrder(request, 1.7D, 1.69D, TimeInForce.DAY);
    response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));

### 

主订单+附加跟踪止损单

[](./place-order-java.md#%E4%B8%BB%E8%AE%A2%E5%8D%95%E9%99%84%E5%8A%A0%E8%B7%9F%E8%B8%AA%E6%AD%A2%E6%8D%9F%E5%8D%95)

Java

    ContractItem contract = ContractItem.buildStockContract("AAPL", "USD");
    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, ActionType.BUY, 1, 165D);
    TradeOrderRequest.addStopLossTrailOrder(request, 10.0D, null, TimeInForce.DAY);
    TradeOrderResponse response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));
    
    // use account parameter
    request = TradeOrderRequest.buildLimitOrder("402901", contract, ActionType.BUY, 1, 165D);
    TradeOrderRequest.addStopLossTrailOrder(request, 10.0D, null, TimeInForce.DAY);
    response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));

### 

主订单+附加括号订单

[](./place-order-java.md#%E4%B8%BB%E8%AE%A2%E5%8D%95%E9%99%84%E5%8A%A0%E6%8B%AC%E5%8F%B7%E8%AE%A2%E5%8D%95)

Java

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, ActionType.BUY, 1, 199d);
    TradeOrderRequest.addBracketsOrder(request, 250D, TimeInForce.DAY, Boolean.FALSE, 180D, TimeInForce.GTC);
    TradeOrderResponse response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));
    
    // use account parameter
    request = TradeOrderRequest.buildLimitOrder("13810712", contract, ActionType.BUY, 1, 199d);
    TradeOrderRequest.addBracketsOrder(request, 250D, TimeInForce.DAY, Boolean.FALSE, 180D, TimeInForce.GTC);
    response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));

### 

换汇单

[](./place-order-java.md#%E6%8D%A2%E6%B1%87%E5%8D%95)

Java

    ForexTradeOrderRequest request = ForexTradeOrderRequest.buildRequest("402901", 
        SegmentType.SEC, Currency.HKD, 1000D, Currency.USD);
    
    ForexTradeOrderResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSONString(response));
    } else {
      System.out.println(response.getMessage());
    }

### 

基金金额单

[](./place-order-java.md#%E5%9F%BA%E9%87%91%E9%87%91%E9%A2%9D%E5%8D%95)

Java

        ContractItem contract = ContractItem.buildFundContract("IE00B464Q616.USD", "USD");
        TradeOrderRequest request = TradeOrderRequest.buildAmountOrder(
            "13810712", contract, ActionType.BUY, 100.0D);
        request.setUserMark("test-amount-order");
        TradeOrderResponse response = client.execute(request);
        if (response.isSuccess()) {
          System.out.println(JSONObject.toJSONString(response));
        } else {
          System.out.println(response.getMessage());
        }

### 

TWAP/VWAP订单

[](./place-order-java.md#twapvwap%E8%AE%A2%E5%8D%95)

只支持美股股票，只支持盘中下单。不能改单，可以撤单

Java

    // TWAP order
    TradeOrderRequest twapRequest = TradeOrderRequest.buildTWAPOrder(
        "572386", "DM", ActionType.BUY, 500,
        DateUtils.getTimestamp("2023-06-20 09:30:00", TimeZoneId.NewYork),
        DateUtils.getTimestamp("2023-06-20 11:00:00", TimeZoneId.NewYork),
         1.5D)
      .setUserMark("testTWAP001")
      .setLang(Language.en_US);
    
    TradeOrderResponse twapResponse = client.execute(twapRequest);
    if (twapResponse.isSuccess()) {
      System.out.println(JSONObject.toJSONString(twapResponse));
    } else {
      System.out.println(twapResponse.getMessage());
    }
    
    // VWAP order
    TradeOrderRequest vwapRequest = TradeOrderRequest.buildVWAPOrder(
        "572386", "DM", ActionType.BUY, 500,
        DateUtils.getTimestamp("2023-06-20 09:30:00", TimeZoneId.NewYork),
        DateUtils.getTimestamp("2023-06-20 11:00:00", TimeZoneId.NewYork),
        0.5D, 1.5D)
      .setUserMark("testVWAP001")
      .setLang(Language.en_US);
    
    TradeOrderResponse vwapResponse = client.execute(vwapRequest);
    if (vwapResponse.isSuccess()) {
      System.out.println(JSONObject.toJSONString(vwapResponse));
    } else {
      System.out.println(vwapResponse.getMessage());
    }

### 

期权多腿订单

[](./place-order-java.md#%E6%9C%9F%E6%9D%83%E5%A4%9A%E8%85%BF%E8%AE%A2%E5%8D%95)

  

Java

    List<ContractLeg> contractLegs = new ArrayList<>();
    ContractLeg leg1 = new ContractLeg(SecType.OPT, "AAPL",
        "170.0", "20231013", Right.CALL,
        ActionType.BUY, 1);
    contractLegs.add(leg1);
    ContractLeg leg2 = new ContractLeg(SecType.OPT, "AAPL",
        "170.0", "20231013", Right.PUT,
        ActionType.BUY, 1);
    contractLegs.add(leg2);
    
    TradeOrderRequest request = TradeOrderRequest.buildMultiLegOrder(
        "572386", contractLegs, ComboType.CUSTOM,
            ActionType.BUY, 3,
            OrderType.LMT, 2.01d, null, null)
        .setLang(Language.en_US)
        .setUserMark("test_multi_leg");
    TradeOrderResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSONString(response));
    } else {
      System.out.println(response.getMessage());
    }

### 

OCA括号单

[](./place-order-java.md#oca%E6%8B%AC%E5%8F%B7%E5%8D%95)

模拟盘不支持

OCA括号订单内的两个订单标的相同，一个止盈限价单，另一个为止损单或者止损限价单。其中一个成交时，自动取消另一个订单。下单后返回两个order对象，订单中的`ocaGroupId`相同的为一个组合。

Java

    ContractItem contract = ContractItem.buildStockContract("BILI", "USD");
    TradeOrderRequest request = TradeOrderRequest.buildOCABracketsOrder(
            "13810712", contract, ActionType.SELL, 1,
            17.0D, TimeInForce.DAY, Boolean.TRUE,
            12.0D, null, TimeInForce.DAY, Boolean.FALSE);
    request.setLang(Language.en_US).setUserMark("test-oca");
    
    TradeOrderResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSONString(response));
      // get oca order info
      List<TradeOrder> ocaOrders = response.getItem().getOrders();
    } else {
      System.out.println(response.getMessage());
    }

**返回示例**

附加括号单

JSON

    {
        "id":30325712346546176,
        "orderId":0,
        "subIds":[\
            30325712346546177,\
            30325712346677250\
        ],
        "orders":[\
            {\
                "account":"13810712",\
                "action":"BUY",\
                "algoStrategy":"LMT",\
                "attrDesc":"",\
                "avgFillPrice":0,\
                "canCancel":true,\
                "canModify":true,\
                "commission":0,\
                "currency":"HKD",\
                "discount":0,\
                "filledQuantity":0,\
                "id":30325712346546176,\
                "identifier":"00700",\
                "latestPrice":385.8,\
                "latestTime":1680266023000,\
                "limitPrice":295,\
                "liquidation":false,\
                "market":"HK",\
                "name":"腾讯控股",\
                "openTime":1680266023000,\
                "orderId":91,\
                "orderType":"LMT",\
                "outsideRth":true,\
                "realizedPnl":0,\
                "remark":"",\
                "secType":"STK",\
                "source":"OpenApi",\
                "status":"Initial",\
                "symbol":"00700",\
                "timeInForce":"DAY",\
                "totalQuantity":100,\
                "updateTime":1680266023000,\
                "userMark":"test_bracket"\
            },\
            {\
                "account":"13810712",\
                "action":"SELL",\
                "algoStrategy":"LMT",\
                "attrDesc":"",\
                "avgFillPrice":0,\
                "canCancel":true,\
                "canModify":true,\
                "commission":0,\
                "currency":"HKD",\
                "discount":0,\
                "filledQuantity":0,\
                "id":30325712346546177,\
                "identifier":"00700",\
                "latestPrice":385.8,\
                "latestTime":1680266023000,\
                "limitPrice":320,\
                "liquidation":false,\
                "market":"HK",\
                "name":"腾讯控股",\
                "ocaGroupId":87055,\
                "openTime":1680266023000,\
                "orderId":92,\
                "orderType":"LMT",\
                "outsideRth":true,\
                "parentId":30325712346546176,\
                "realizedPnl":0,\
                "remark":"",\
                "secType":"STK",\
                "source":"OpenApi",\
                "status":"Initial",\
                "symbol":"00700",\
                "timeInForce":"DAY",\
                "totalQuantity":100,\
                "updateTime":1680266023000,\
                "userMark":"test_bracket"\
            },\
            {\
                "account":"13810712",\
                "action":"SELL",\
                "algoStrategy":"STP_LMT",\
                "attrDesc":"",\
                "auxPrice":280,\
                "avgFillPrice":0,\
                "canCancel":true,\
                "canModify":true,\
                "commission":0,\
                "currency":"HKD",\
                "discount":0,\
                "filledQuantity":0,\
                "id":30325712346677248,\
                "identifier":"00700",\
                "latestPrice":385.8,\
                "latestTime":1680266023000,\
                "limitPrice":278,\
                "liquidation":false,\
                "market":"HK",\
                "name":"腾讯控股",\
                "ocaGroupId":87055,\
                "openTime":1680266023000,\
                "orderId":93,\
                "orderType":"STP_LMT",\
                "outsideRth":true,\
                "parentId":30325712346546176,\
                "realizedPnl":0,\
                "remark":"",\
                "secType":"STK",\
                "source":"OpenApi",\
                "status":"Initial",\
                "symbol":"00700",\
                "timeInForce":"DAY",\
                "totalQuantity":100,\
                "updateTime":1680266023000,\
                "userMark":"test_bracket"\
            }\
        ]
    }

  
  
  
