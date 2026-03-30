# 获取订单信息

预览订单

[](./orderinfo-java.md#%E9%A2%84%E8%A7%88%E8%AE%A2%E5%8D%95)

-----------------------------------------------------------------------------------------------

**对应的请求类 `TradeOrderPreviewRequest`**

**说明**

预览订单，返回是否可提交订单（即，订单是否可成交），以及资产信息

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 用户授权账户 |
| symbol | string | Yes | 股票代码 如：AAPL |
| sec\_type | string | Yes | 合约类型 (STK 股票 OPT 美股期权 WAR 港股窝轮 IOPT 港股牛熊证) |
| action | string | Yes | 交易方向 BUY/SELL |
| order\_type | string | Yes | 订单类型 MKT(市价单)，LMT(限价单）， STP(止损单)，STP\_LMT(止损限价单)，TRAIL(跟踪止损单) |
| total\_quantity | int | Yes | 订单数量(港股，沪港通，窝轮，牛熊证有最小数量限制) |
| limit\_price | double | No  | 限价，当 order\_type 为LMT,STP,STP\_LMT时该参数必需 |
| aux\_price | double | No  | 股票止损价。当 order\_type 为STP,STP\_LMT时该参数必需，当 order\_type 为 TRAIL时，为跟踪额 |
| trailing\_percent | double | No  | 跟踪止损单-百分比 ，当 order\_type 为 TRAIL时,aux\_price和trailing\_percent两者互斥 |
| outside\_rth | boolean | No  | true: 允许盘前盘后交易(美股专属)，false: 不允许, 默认允许 |
| market | string | No  | 市场 (美股 US 港股 HK 沪港通 CN) |
| currency | string | No  | 货币(美股 USD 港股 HKD 沪港通 CNH) |
| time\_in\_force | string | No  | 订单有效期，只能是 DAY（当日有效）和GTC（取消前有效），默认为DAY |
| exchange | string | No  | 交易所 (美股 SMART 港股 SEHK 沪港通 SEHKNTL 深港通 SEHKSZSE) |
| expiry | string | No  | 过期日(期权、窝轮、牛熊证专属) |
| strike | string | No  | 行权价(期权、窝轮、牛熊证专属) |
| right | string | No  | 期权方向 PUT/CALL (期权、窝轮、牛熊证专属) |
| multiplier | float | No  | 乘数，每手对应的数量 (期权、窝轮、牛熊证专属) |
| local\_symbol | string | No  | 窝轮、牛熊证该字段必填，在APP中窝轮/牛熊证列表中名称下面的5位数字 |

**返回**

| 字段  | 类型  | 描述  |
| --- | --- | --- |
| account | String | 账户id |
| initMargin | Double | 下单后初始保证金, 不支持期货 |
| maintMargin | Double | 下单后维持保证金, 不支持期货 |
| equityWithLoan | Double | 下单后可借贷资产, 不支持期货 |
| initMarginBefore | Double | 下单前初始保证金, 不支持期货 |
| maintMarginBefore | Double | 下单前维持保证金, 不支持期货 |
| equityWithLoanBefore | Double | 下单前可借贷资产, 不支持期货 |
| marginCurrency | String | 保证金币种 |
| commission | Double | 预估佣金 |
| gst | Double | 预估消费税 |
| commissionCurrency | String | 预估佣金币种 |
| availableEe | Double | 可用剩余资产 不支持期货 |
| excessLiquidity | Double | 剩余流动性 不支持期货 |
| overnightLiquidation | Double | 隔夜剩余流动性 不支持期货 |
| isPass | Boolean | 是否可提交订单 |
| message | String | 不可提交订单的错误原因 |

**示例**

Java

    ContractItem contract = ContractItem.buildStockContract("SPY", "USD");
    
    TradeOrderPreviewRequest request = TradeOrderPreviewRequest.buildLimitOrder(contract, ActionType.BUY, 1, 100.0d);
    TradeOrderPreviewResponse response = client.execute(request);
    System.out.println(JSONObject.toJSONString(response));

**返回示例**

JSON

    {
      "code": 0,
      "message": "success",
      "timestamp": 1748413352204,
      "data": {
        "account": "123456",
        "initMargin": 432.617714,
        "maintMargin": 424.264714,
        "equityWithLoan": 1111.3237541,
        "initMarginBefore": 387.617714,
        "maintMarginBefore": 386.764714,
        "equityWithLoanBefore": 1111.3237541,
        "marginCurrency": "USD",
        "commission": 0.0,
        "commissionCurrency": "USD",
        "availableEE": 677.8311632,
        "excessLiquidity": 687.05904,
        "overnightLiquidation": 687.05904,
        "gst": 0.0,
        "isPass": true
      }
    }

* * *

获取订单

[](./orderinfo-java.md#%E8%8E%B7%E5%8F%96%E8%AE%A2%E5%8D%95)

-----------------------------------------------------------------------------------------------

**对应的请求类：TigerHttpRequest(MethodName.ORDERS)**

**说明**

获取订单

**参数**

获取指定单个订单

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 用户授权账户：572386 |
| id  | int | Yes | 下单成功后返回的订单号 |
| secret\_key | string | No  | 交易员密钥，机构用户专用 |
| show\_charges | bool | No  | 是否返回订单的费用明细 |

获取订单列表

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 用户授权账户: 572386 |
| seg\_type | SegmentType | No  | 账户划分类型, 可选值有: SegmentType.SEC 代表证券; SegmentType.FUT 代表期货; SegmentType.FUND 代表基金, SegmentType.ALL：代表证券+期货+基金。 默认为SegmentType.SEC |
| sec\_type | string | No  | ALL/STK/OPT/FUT/FOP/CASH 默认 ALL |
| market | string | No  | ALL/US/HK/CN 默认 ALL |
| symbol | string | No  | 股票代码 |
| expiry | string | No  | 过期日 (期权、窝轮、牛熊证专属) |
| strike | string | No  | 行权价格 (期权、窝轮、牛熊证专属) |
| right | string | No  | 期权方向 PUT/CALL (期权、窝轮、牛熊证专属) |
| start\_date | string | No  | 订单下单时间的起始时间（当 sort\_by=LATEST\_STATUS\_UPDATED 时，按订单状态更新时间进行过滤），格式为'2018-05-01' 或者 "2018-05-01 10:00:00"（默认东八区，可指定时区），闭区间 |
| end\_date | string | No  | 订单下单时间的截止时间（当 sort\_by=LATEST\_STATUS\_UPDATED 时，按订单状态更新时间进行过滤），格式为'2018-05-15' 或者 "2018-05-01 10:00:00"（默认东八区，可指定时区），开区间 |
| states | array | No  | 仅支持环球账号 订单状态, 默认查有效订单，参考: 订单状态（超链接） |
| isBrief | boolean | No  | 仅支持环球账号 是否返回精简的订单信息 |
| limit | integer | No  | 默认为100，最大限制为300 |
| sort\_by | OrderSortBy | No  | 仅支持综合账号 排序和起止时间作用字段，LATEST\_CREATED/LATEST\_STATUS\_UPDATED; 默认值: LATEST\_CREATED |
| secret\_key | string | No  | 交易员密钥， 机构用户专用 |
| lang | string | No  | 语言支持（枚举类Language）：zh\_CN, zh\_TW, en\_US, 默认：en\_US |
| page\_token | string | No  | 分页查询 token，使用pageToken分页拉取数据时其他查询条件不能变 |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.trade.SingleOrderResponse` 或 `com.tigerbrokers.stock.openapi.client.https.response.trade.BatchOrderResponse`

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| nextPageToken | string | 查询下一页用的token |
| items | array | 订单数组,字段参考下面说明 |

其中订单数据items属性如下：

| 名称  | 示例  | 说明  |
| --- | --- | --- |
| id  | 27363676799501312 | 订单全局唯一ID，下单成功后返回 |
| orderId | 830154374 | 用户本地的自增订单ID，非全局唯一 |
| externalId | 830154374 | 扩展ID, API下单为orderId的值 |
| parentId | 0   | 父订单的订单ID |
| account | 572386 | 交易账户 |
| action | BUY | 交易方向,BUY or SELL |
| orderType | LMT | 订单类型 |
| limitPrice | 108.62 | 限价单价格 |
| auxPrice | 0.0 | 止损单辅助价格-跟踪额 |
| trailingPercent | 5   | 跟踪止损单的跟踪百分比 |
| totalQuantity | 111 | 下单数量 |
| totalQuantityScale | 0   | 下单数量的偏移量，默认为0。碎股单的totalQuantity 和 totalQuantityScale 结合起来代表真实下单数量，如 totalQuantity=111 totalQuantityScale=2，那么真实 quantity=111\*10^(-2)=1.11 |
| timeInForce | DAY | DAY/GTC/GTD |
| expireTime | 1669000183188 | timeInForce为GTD时才有值 |
| outsideRth | true | 是否允许盘前、盘后 |
| filledQuantity | 50  | 成交数量 |
| filledQuantityScale | 0   | 成交数量偏移量，如 filledQuantity=11123， filledQuantityScale=2，那么实际 filledQuantity=11123\*10^(-2)=111.23 |
| totalCashAmount | 100 | 下单总金额，按股数下单时为null |
| filledCashAmount | 100 | 已成交金额，按股数下单时为null |
| refundCashAmount | 0   | 退回金额，等于下单总金额-已成交金额。按股数下单或订单未终结时为null |
| avgFillPrice | 108.62 | 包含佣金的平均成交价 |
| remark | Order is expired | 错误描述 |
| status | Filled | 订单状态,参考:[订单状态](./appendix-enum-java.md#order-status) |
| attrDesc | Exercise | 订单描述信息，参考:[订单描述](./appendix-enum-java.md#order-desc) |
| commission | 0.99 | 包含佣金、印花税、证监会费等系列费用 |
| commissionCurrency | USD | 佣金币种 |
| gst | 1.34 | 消费税 (仅TBSG牌照用户有) |
| realizedPnl | 0.0 | 已实现盈亏 |
| openTime | 1657667486000 | 下单时间 |
| updateTime | 1657670428000 | 最后更新时间 |
| latestTime | 1657670428000 | 状态更新时间 |
| symbol | BABA | 股票代码 |
| currency | USD | 货币  |
| market | US  | 交易市场 |
| multiplier | 0.0 | 乘数，每手对应的数量 |
| secType | STK | 交易类型 |
| userMark | my\_strategy\_1 | 下单时备注参数，会按照设置值返回，长度不能超过200 |
| canModify | false | 是否可修改订单 |
| canCancel | false | 是否可撤销订单 |
| liquidation | false | 是否强制平仓 |
| isOpen | true | 是否为开仓 |
| replaceStatus | NONE | [订单改单状态](./appendix-enum-java.md#order-replace-status) |
| cancelStatus | NONE | [订单撤单状态](./appendix-enum-java.md#order-cancel-status) |
| charges |     | 订单佣金等费用明细（仅限单个订单查询）。详细说明见下方`Charge`描述。 |
| commissionDiscountAmount | 0   | 免佣金额（仅限单个订单查询） |
| orderDiscountAmount | 0   | 订单扣减金额 |
| orderDiscount | 0   | 订单抵扣状态（仅限单个订单查询）。1：待抵扣；2：已抵扣；0：默认值 |
| attrList | \["EXERCISE"\] | 订单属性列表. 各属性含义如下： LIQUIDATION 强平, FRACTIONAL\_SHARE 碎股订单(非整股), EXERCISE 行权, EXPIRE 过期, ASSIGNMENT 被动行权分配, CASH\_SETTLE 现金交割, KNOCK\_OUT 敲出, RECALL 召回订单, ODD\_LOT 碎股订单（非整手）, DEALER 交易员下单, GREY\_MARKET 港股暗盘订单, BLOCK\_TRADE 大宗交易, ATTACHED\_ORDER 附加订单, OCA OCA订单 |

**`Charge`说明：**

| 名称  | 示例  | 说明  |
| --- | --- | --- |
| category | TIGER | 费用类别：TIGER/THIRD\_PARTY |
| categoryDesc | Tiger Charge | 费用类别描述：Tiger Charge; Third Parties |
| total | 18  | 当前类别费用总额 |
| details |     | 费用明细。详细说明见下方`ChargeDetails`描述。 |

**`ChargeDetails`说明：**

| 名称  | 示例  | 说明  |
| --- | --- | --- |
| type | SETTLEMENT\_FEE | 费用类型：SETTLEMENT\_FEE/STAMP\_DUTY/TRANSACTION\_LEVY/EXCHANGE\_FEE/FRC\_TRANSACTION\_LEVY |
| typeDesc | Settlement Fee | 费用类型描述：Settlement Fee(结算费); Stamp Duty（印花税）; Transaction Levy（交易征费）; Exchange Fee（交易所费用）; AFRC Transaction Levy（会计及财汇局交易征费） |
| originalAmount | 4   | 费用金额 |
| afterDiscountAmount | 4   | 抵扣后的费用 |

**示例**

获取单个订单

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    QuerySingleOrderRequest request = new QuerySingleOrderRequest();
    
    String bizContent = AccountParamBuilder.instance()
            .account("572386")
            .id(31227598058424320L)
            .isShowCharges(true)
            .lang(Language.en_US)
            .buildJson();
    
    request.setBizContent(bizContent);
    SingleOrderResponse response = client.execute(request);
    
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSONString(response));
      Long id = response.getItem().getId();
      String action = response.getItem().getAction();
      // ...
    } else {
      System.out.println(response.getMessage());
    }

获取订单列表

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    QueryOrderRequest request = new QueryOrderRequest();
    
    String bizContent = AccountParamBuilder.instance()
        .account("572386")
        .startDate("2023-04-01 00:00:00", TimeZoneId.NewYork)
        .endDate("2023-06-20 23:59:59", TimeZoneId.NewYork)
        .secType(SecType.STK)
        .sortBy(OrderSortBy.LATEST_CREATED)
        .limit(5)
        .buildJson();
    
    request.setBizContent(bizContent);
    BatchOrderResponse response = client.execute(request);
    
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSONString(response));
      List<TradeOrder> orders = response.getItem().getOrders();
      TradeOrder order1 = orders.get(0);
      String symbol = order1.getString("symbol");
      Long id = order1.getLong("id");
      // ...
    } else {
      System.out.println(response.getMessage());
    }

使用 pageToken 分页获取订单

Java

    List<JSONObject> results = new ArrayList<>();
    int page = 1;
    String pageToken = "";
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    long startTime = sdf.parse("2023-01-01").getTime();
    long endTime = sdf.parse("2025-08-01").getTime();
    
    while (true) {
        TigerHttpRequest request = new TigerHttpRequest(MethodName.ORDERS);
        String bizContent = AccountParamBuilder.instance()
            .account("402501")
            .symbol("AAPL")
            .startDate(String.valueOf(startTime))
            .endDate(String.valueOf(endTime))
            .limit(10)
            .pageToken(pageToken)
            .buildJson();
        request.setBizContent(bizContent);
        TigerHttpResponse response = client.execute(request);
    
        JSONObject responseData = JSON.parseObject(response.getData());
        JSONArray items = responseData.getJSONArray("items");
        
        System.out.println("page " + page + ", size " + items.size() + 
            ", next_page_token: " + responseData.getString("nextPageToken"));
        page++;
    
        if (items != null && !items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                results.add(items.getJSONObject(i));
            }
        }
    
        pageToken = responseData.getString("nextPageToken");
        if (StringUtils.isEmpty(pageToken)) {
            break;
        }
    }
    
    System.out.println("total: " + results.size() + ", results: " + results);
    

**返回示例**

单个订单

JSON

    {
        "code": 0,
        "data": {
            "account": "572386",
            "action": "SELL",
            "algoStrategy": "LMT",
            "attrDesc": "",
            "attrList": [\
                "SETTLED"\
            ],
            "avgFillPrice": 3.54,
            "canCancel": false,
            "canModify": false,
            "cancelStatus": "NONE",
            "charges": [\
                {\
                    "category": "TIGER",\
                    "categoryDesc": "Tiger Charge",\
                    "details": [\
                        {\
                            "afterDiscountAmount": 18,\
                            "originalAmount": 18,\
                            "type": "USER_COMMISSION",\
                            "typeDesc": "Commission"\
                        }\
                    ],\
                    "total": 18\
                },\
                {\
                    "category": "THIRD_PARTY",\
                    "categoryDesc": "Third Parties",\
                    "details": [\
                        {\
                            "afterDiscountAmount": 4,\
                            "originalAmount": 4,\
                            "type": "SETTLEMENT_FEE",\
                            "typeDesc": "Settlement Fee"\
                        },\
                        {\
                            "afterDiscountAmount": 22,\
                            "originalAmount": 22,\
                            "type": "STAMP_DUTY",\
                            "typeDesc": "Stamp Duty"\
                        },\
                        {\
                            "afterDiscountAmount": 0.58,\
                            "originalAmount": 0.58,\
                            "type": "TRANSACTION_LEVY",\
                            "typeDesc": "Transaction Levy"\
                        },\
                        {\
                            "afterDiscountAmount": 1.2,\
                            "originalAmount": 1.2,\
                            "type": "EXCHANGE_FEE",\
                            "typeDesc": "Exchange Fee"\
                        },\
                        {\
                            "afterDiscountAmount": 0.04,\
                            "originalAmount": 0.04,\
                            "type": "FRC_TRANSACTION_LEVY",\
                            "typeDesc": "AFRC Transaction Levy"\
                        }\
                    ],\
                    "total": 27.82\
                }\
            ],
            "commission": 45.82,
            "currency": "HKD",
            "discount": 0,
            "externalId": "710344498739626686",
            "filledCashAmount": 21240,
            "filledQuantity": 6000,
            "filledQuantityScale": 0,
            "gst": 0,
            "id": 36810407788938240,
            "identifier": "01177",
            "isOpen": false,
            "latestTime": 1729740324000,
            "limitPrice": 3.54,
            "liquidation": false,
            "market": "HK",
            "name": "SINO BIOPHARM",
            "openTime": 1729740323000,
            "orderDiscount": 0,
            "orderId": 0,
            "orderType": "LMT",
            "outsideRth": false,
            "realizedPnl": -6388.735,
            "remark": "",
            "replaceStatus": "NONE",
            "secType": "STK",
            "source": "android",
            "status": "Filled",
            "symbol": "01177",
            "timeInForce": "GTC",
            "totalQuantity": 6000,
            "totalQuantityScale": 0,
            "tradingSessionType": "RTH",
            "updateTime": 1730045103000,
            "userMark": ""
        },
        "message": "success",
        "sign": "F9xRzsjqgFlfaUJVajSber2jfCOVt1DIovKcE3yxWK9DFqfTPXHxKqCJ3aT8bGPl/8THViWW0A62LlRL1RB41cLt6bsMUyG7+nSQOE2vPIdo29SyZGcPAiSdRHbY8h3Nq9V1PzVQVqs07joUOw5dUuO5M3TgY/R0UHFV0lwxkBM=",
        "success": true,
        "timestamp": 1730971141181
    }

订单列表

JSON

    {
        "code":0,
        "data":{
            "items":[\
                {\
                    "account":"572386",\
                    "action":"BUY",\
                    "algoStrategy":"MKT",\
                    "attrDesc":"",\
                    "avgFillPrice":9.36,\
                    "canCancel":false,\
                    "canModify":false,\
                    "commission":2.4,\
                    "currency":"USD",\
                    "discount":0,\
                    "externalId":"980",\
                    "filledQuantity":10,\
                    "id":31227598058424320,\
                    "identifier":"NIO.SI",\
                    "isOpen":true,\
                    "latestTime":1687146866000,\
                    "liquidation":false,\
                    "market":"SG",\
                    "name":"NIO Inc.",\
                    "openTime":1687146865000,\
                    "orderId":980,\
                    "orderType":"MKT",\
                    "outsideRth":false,\
                    "realizedPnl":0,\
                    "remark":"",\
                    "secType":"STK",\
                    "source":"OpenApi",\
                    "status":"Filled",\
                    "symbol":"NIO.SI",\
                    "timeInForce":"DAY",\
                    "totalQuantity":10,\
                    "updateTime":1687146866000,\
                    "userMark":""\
                },\
                {\
                    "account":"572386",\
                    "action":"BUY",\
                    "algoStrategy":"LMT",\
                    "attrDesc":"",\
                    "avgFillPrice":0,\
                    "canCancel":false,\
                    "canModify":false,\
                    "commission":0,\
                    "currency":"USD",\
                    "discount":0,\
                    "externalId":"979",\
                    "filledQuantity":0,\
                    "id":31227591745209344,\
                    "identifier":"NIO.SI",\
                    "isOpen":true,\
                    "latestTime":1687146817000,\
                    "limitPrice":2,\
                    "liquidation":false,\
                    "market":"SG",\
                    "name":"NIO Inc.",\
                    "openTime":1687146817000,\
                    "orderId":979,\
                    "orderType":"LMT",\
                    "outsideRth":true,\
                    "realizedPnl":0,\
                    "remark":"Order Price exceed max price step (30) limit. For more information, please contact customer service at 400-603-7555.",\
                    "secType":"STK",\
                    "source":"OpenApi",\
                    "status":"Invalid",\
                    "symbol":"NIO.SI",\
                    "timeInForce":"DAY",\
                    "totalQuantity":10,\
                    "updateTime":1687146817000,\
                    "userMark":""\
                },\
                {\
                    "account":"572386",\
                    "action":"BUY",\
                    "algoStrategy":"LMT",\
                    "attrDesc":"",\
                    "avgFillPrice":0,\
                    "canCancel":false,\
                    "canModify":false,\
                    "commission":0,\
                    "currency":"USD",\
                    "discount":0,\
                    "externalId":"978",\
                    "filledQuantity":0,\
                    "id":31227575457809408,\
                    "identifier":"NIO.SI",\
                    "isOpen":true,\
                    "latestTime":1687146693000,\
                    "limitPrice":9,\
                    "liquidation":false,\
                    "market":"SG",\
                    "name":"NIO Inc.",\
                    "openTime":1687146693000,\
                    "orderId":978,\
                    "orderType":"LMT",\
                    "outsideRth":true,\
                    "realizedPnl":0,\
                    "remark":"Order Price exceed max price step (30) limit. For more information, please contact customer service at 400-603-7555.",\
                    "secType":"STK",\
                    "source":"OpenApi",\
                    "status":"Invalid",\
                    "symbol":"NIO.SI",\
                    "timeInForce":"DAY",\
                    "totalQuantity":10,\
                    "updateTime":1687146693000,\
                    "userMark":""\
                },\
                {\
                    "account":"572386",\
                    "action":"BUY",\
                    "algoStrategy":"LMT",\
                    "attrDesc":"",\
                    "avgFillPrice":0,\
                    "canCancel":false,\
                    "canModify":false,\
                    "commission":0,\
                    "currency":"USD",\
                    "discount":0,\
                    "externalId":"977",\
                    "filledQuantity":0,\
                    "id":31175091790938112,\
                    "identifier":"JD",\
                    "isOpen":true,\
                    "latestTime":1686788253000,\
                    "limitPrice":35,\
                    "liquidation":false,\
                    "market":"US",\
                    "name":"JD.com",\
                    "openTime":1686746274000,\
                    "orderId":977,\
                    "orderType":"LMT",\
                    "outsideRth":true,\
                    "realizedPnl":0,\
                    "remark":"Order is expired",\
                    "secType":"STK",\
                    "source":"OpenApi",\
                    "status":"Inactive",\
                    "symbol":"JD",\
                    "timeInForce":"DAY",\
                    "totalQuantity":1,\
                    "updateTime":1686788253000,\
                    "userMark":""\
                },\
                {\
                    "account":"572386",\
                    "action":"BUY",\
                    "algoStrategy":"LMT",\
                    "attrDesc":"",\
                    "avgFillPrice":0,\
                    "canCancel":false,\
                    "canModify":false,\
                    "commission":0,\
                    "currency":"USD",\
                    "discount":0,\
                    "externalId":"976",\
                    "filledQuantity":0,\
                    "id":31175084828133376,\
                    "identifier":"JD",\
                    "isOpen":true,\
                    "latestTime":1686788253000,\
                    "limitPrice":35.9,\
                    "liquidation":false,\
                    "market":"US",\
                    "name":"JD.com",\
                    "openTime":1686746221000,\
                    "orderId":976,\
                    "orderType":"LMT",\
                    "outsideRth":true,\
                    "realizedPnl":0,\
                    "remark":"Order is expired",\
                    "secType":"STK",\
                    "source":"OpenApi",\
                    "status":"Inactive",\
                    "symbol":"JD",\
                    "timeInForce":"DAY",\
                    "totalQuantity":1,\
                    "updateTime":1686788253000,\
                    "userMark":""\
                }\
            ],
            "nextPageToken":"b3JkZXJzfDE2ODAzMjE2MDAwMDB8MTY4NzMxOTk5OTAwMHwzMTE3NTA4NDgyODEzMzM3Ng=="
        },
        "message":"success",
        "sign":"u59vLeh+5Wvim9SwxaW16k9nvTXfnSkZqPqUcq0p0CBtfXQNUFk4nxJXXA6jKXF2RcdfzZn+lkODMpxiI8dGC2bi+/4MoqpnkWGQFAlur/YCSSgTG+TUv1p2mfwZ2CLpKzzNaDk1NEcni+AX1JBeWJeo0GS6bgo8ic22hdS5BLE=",
        "success":true,
        "timestamp":1687251914180
    }

* * *

获取已成交订单列表

[](./orderinfo-java.md#%E8%8E%B7%E5%8F%96%E5%B7%B2%E6%88%90%E4%BA%A4%E8%AE%A2%E5%8D%95%E5%88%97%E8%A1%A8)

-------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QueryOrderRequest(MethodName.FILLED\_ORDERS)**

**说明**

获取状态为成交的订单列表

**参数**

参考获取订单，其中start\_date和end\_date为必传参数。

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    QueryOrderRequest request = new QueryOrderRequest(MethodName.FILLED_ORDERS);
    
    String bizContent = AccountParamBuilder.instance()
            .account("402901")
            .secType(SecType.STK)
            .startDate("2023-05-15 22:34:30")
            .endDate("2023-06-06 22:34:31")
            .buildJson();
    
    request.setBizContent(bizContent);
    BatchOrderResponse response = client.execute(request);

**返回**

参考获取订单

* * *

获取待成交订单列表

[](./orderinfo-java.md#%E8%8E%B7%E5%8F%96%E5%BE%85%E6%88%90%E4%BA%A4%E8%AE%A2%E5%8D%95%E5%88%97%E8%A1%A8)

-------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QueryOrderRequest(MethodName.ACTIVE\_ORDERS)**

**参数**

参考获取订单，可能包含部分成交的订单

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    QueryOrderRequest request = new QueryOrderRequest(MethodName.ACTIVE_ORDERS);
    
    String bizContent = AccountParamBuilder.instance()
            .account("DU575569")
            .secType(SecType.STK)
            .buildJson();
    
    request.setBizContent(bizContent);
    BatchOrderResponse response = client.execute(request);

**返回**

参考获取订单

* * *

获取已撤销订单列表

[](./orderinfo-java.md#%E8%8E%B7%E5%8F%96%E5%B7%B2%E6%92%A4%E9%94%80%E8%AE%A2%E5%8D%95%E5%88%97%E8%A1%A8)

-------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：QueryOrderRequest(MethodName.INACTIVE\_ORDERS)**

**参数**

参考获取订单

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    QueryOrderRequest request = new QueryOrderRequest(MethodName.INACTIVE_ORDERS);
    
    String bizContent = AccountParamBuilder.instance()
            .account("DU575569")
            .secType(SecType.STK)
            .buildJson();
    
    request.setBizContent(bizContent);
    BatchOrderResponse response = client.execute(request);

**返回**

参考获取订单

* * *

获取成交记录

[](./orderinfo-java.md#%E8%8E%B7%E5%8F%96%E6%88%90%E4%BA%A4%E8%AE%B0%E5%BD%95)

-------------------------------------------------------------------------------------------------------------------

**对应的请求类：TigerHttpRequest(MethodName.ORDER\_TRANSACTIONS)**

**说明**

获取订单的成交记录

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | String | Yes | 账户，目前仅支持综合账户 |
| order\_id | long | Yes | 下单成功后返回的全局订单ID，非本地订单ID。 order\_id 和 symbol其中一个必传。 使用orderId后，symbol参数不生效 |
| symbol | String | Yes | 股票代码。order\_id 和 symbol其中一个必传。 |
| sec\_type | String | No, 指定symbol查询时必传 | STK:股票/FUT:期货/OPT:期权/WAR:窝轮/IOPT:牛熊证, 未指定查全部。 |
| expiry | String | No, sect\_type为OPT/WAR/IOPT类型时必传 | 到期日 |
| right | String | No, sect\_type为OPT/WAR/IOPT类型时必传 | CALL/PUT |
| start\_date | long | No  | 起始时间（yyyy-MM-dd HH-mm-ss格式需要转换为毫秒的时间戳） |
| end\_date | long | No  | 截止时间（yyyy-MM-dd HH-mm-ss格式需要转换为毫秒的时间戳） |
| since\_date | String | No  | 起始日期 (yyyyMMdd 格式) |
| to\_date | String | No  | 截止日期 (yyyyMMdd 格式) |
| limit | int | No  | 返回数据数量限制，默认20, 最大100 |
| secretKey | String | No  | 交易员密钥，机构用户专用 |
| page\_token | String | No  | 分页查询token，使用该字段分页拉取数据时其他查询条件不能变 |

**返回**

| 字段  | 示例  | 说明  |
| --- | --- | --- |
| id  | 24653027221308416 | 成交记录ID |
| accountId | 402190 | 账号  |
| orderId | 24637316162520064 | 订单ID |
| secType | STK | 证券类型 |
| symbol | CII | symbol |
| currency | USD | 币种  |
| market | US  | 市场  |
| action | BUY | 动作, BUY/SELL |
| filledQuantity | 100 | 成交数量 |
| filledQuantityScale | 0   | 成交数量的偏移量，默认为0。filledQuantity 和 filledQuantityScale 结合起来代表一个下单数量，如 qty=111 scale=2，那么真实 qty=111\*10^(-2)=1.11 |
| filledPrice | 21  | 成交价 |
| filledAmount | 2167.0 | 成交金额 |
| transactedAt | 2021-11-15 22:34:30 | 成交时间 |
| transactionTime | 1636986870000 | 成交时间戳 |
| nextPageToken | xxxxxx | 下一页token |

**示例**

Java

    // 按照symbol查询
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ORDER_TRANSACTIONS);
    String bizContent = AccountParamBuilder.instance()
        .account("402501")
        .secType(SecType.STK)
        .symbol("CII")
        .limit(30)
        .startDate("2021-11-15 22:34:30")
        .endDate("2021-11-15 22:34:31")
        .buildJson();
    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);
    
    JSONArray data = JSON.parseObject(response.getData()).getJSONArray("items");
    JSONObject trans1 = data.getJSONObject(0);
     
    
    // 按照orderId查询
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ORDER_TRANSACTIONS);
        String bizContent = AccountParamBuilder.instance()
            .account("402501")
            .orderId(24637316162520064L)
            .limit(30)
            .buildJson();
    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);
    
    JSONArray data = JSON.parseObject(response.getData()).getJSONArray("items");
    JSONObject trans1 = data.getJSONObject(0);
    

使用 pageToken 分页获取成交记录

Java

    List<JSONObject> results = new ArrayList<>();
    int page = 1;
    String pageToken = "";
    
    // 构建查询参数
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    long startTime = sdf.parse("2023-01-01").getTime();
    long endTime = sdf.parse("2025-08-01").getTime();
    
    while (true) {
        TigerHttpRequest request = new TigerHttpRequest(MethodName.ORDER_TRANSACTIONS);
        String bizContent = AccountParamBuilder.instance()
            .account("402501")
            .secType(SecType.STK)
            .startDate(String.valueOf(startTime))
            .endDate(String.valueOf(endTime))
            .limit(10)
            .pageToken(pageToken)
            .buildJson();
        request.setBizContent(bizContent);
        TigerHttpResponse response = client.execute(request);
    
        JSONObject responseData = JSON.parseObject(response.getData());
        JSONArray items = responseData.getJSONArray("items");
        
        System.out.println("page " + page + ", size " + items.size() + 
            ", next_page_token: " + responseData.getString("nextPageToken"));
        page++;
    
        if (items != null && !items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                results.add(items.getJSONObject(i));
            }
        }
    
        pageToken = responseData.getString("nextPageToken");
        if (StringUtils.isEmpty(pageToken)) {
            break;
        }
    }
    
    System.out.println("total: " + results.size() + ", results: " + results);

**返回示例**

JSON

    {
      "items": [\
        {\
          "id": 24653027221308416,\
          "accountId": 402901,\
          "orderId": 24637316162520064,\
          "secType": "STK",\
          "symbol": "CII",\
          "currency": "USD",\
          "market": "US",\
          "action": "BUY",\
          "filledQuantity": 100,\
          "filledQuantityScale": 0,\
          "filledPrice": 21.67,\
          "filledAmount": 2167,\
          "transactedAt": "2021-11-15 22:34:30",\
          "transactionTime": 1636986870000\
        }\
      ],
      "nextPageToken": "xxxxxx"
    }

  
