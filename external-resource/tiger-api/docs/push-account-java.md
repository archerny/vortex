# 账户变动推送

String subscribe(Subject subject) 订阅

[](./push-account-java.md#string-subscribesubject-subject-%E8%AE%A2%E9%98%85)

------------------------------------------------------------------------------------------------------------------------------------------------

**说明**

交易API提供了交易相关接口，同时也提供了订阅接口，可以实时获取账户的资产、持仓、订单、订单执行明细的变化信息。 交易推送接口是异步接口，通过实现ApiComposeCallback接口可以获得异步请求结果。 回调接口返回值类型，具体回调接口可参见此文档。

**输入参数(订阅主题)**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| account | string | No  | 如不设置订阅资金账号，默认订阅所有的资金账号，包括模拟账号 |
| subject | Subject | Yes | 订阅主题 |

Subject为订阅主题，主要包括四种：OrderStatus(订单)，Asset(资产)，Position(持仓)，OrderTransaction(订单执行明细)：

*   **OrderStatus推送**，是指订单状态发生变化时的推送，订单变化主要包括：Submitted(已提交到交易所)，Cancelled(订单已取消)，Inactive(订单被拒绝)，Filled(订单已成交)，其他中间状态时不会推送。
*   **Asset推送**，是当账户资产发生变化时的推送。
*   **Position推送**，是当账户持仓发生变化时的推送。
*   **OrderTransaction推送**，是订单执行明细报告数据推送。

默认会推送全部资金账号的数据，包括模拟账号，可以按返回数据的account区分，或者使用`subscribe(String account, Subject subject)`方法订阅时指定资金账号。

> ⚠️
> 
> **CAUTION**
> 
> 订阅上述四种类型的任意一个主题，都会推送全部的四种主题数据，取消四种中的任意一个主题，会取消全部的四种主题数据的订阅。

**返回值**

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| id  | string | sdk订阅请求时本地生成的ID，序列递增。在`subscribeEnd(int id, String subject, String result)`回调方法中返回请求id和订阅是否成功的结果 |

**回调数据字段含义**

资产变动回调

| 字段  | 类型  | 描述  |
| --- | --- | --- |
| account | String | 资金账号 |
| currency | String | 币种。USD美元，HKD港币 |
| segType | String | 按交易品种划分的分类。S表示股票，C表示期货 |
| availableFunds | double | 可用资金，隔夜剩余流动性 |
| excessLiquidity | double | 当前剩余流动性 |
| netLiquidation | double | 总资产(净清算值)。总资产就是我们账户的净清算现金余额和证券总市值之和 |
| equityWithLoan | double | 含贷款价值总权益。等于总资产 - 美股期权 |
| buyingPower | double | 购买力。仅适用于股票品种，即segment为S时有意义 |
| cashBalance | double | 现金额。当前所有币种的现金余额之和 |
| grossPositionValue | double | 证券总价值 |
| initMarginReq | double | 初始保证金 |
| maintMarginReq | double | 维持保证金 |
| timestamp | long | 时间戳 |

持仓变动回调

| 字段  | 类型  | 描述  |
| --- | --- | --- |
| account | String | 资金账号 |
| symbol | String | 持仓标的代码，如 'AAPL', '00700', 'ES', 'CN' |
| expiry | String | 仅支持期权、窝轮、牛熊证 |
| strike | String | 仅支持期权、窝轮、牛熊证 |
| right | String | 仅支持期权、窝轮、牛熊证 |
| identifier | String | 标的标识符。股票的identifier与symbol相同。期货的会带有合约月份，如 'CN2201' |
| multiplier | int | 每手数量，仅限 futures, options, warrants, CBBC |
| market | String | 市场。US, HK |
| currency | String | 币种。USD美元，HKD港币 |
| segType | String | 按交易品种划分的分类。S表示股票，C表示期货 |
| secType | String | STK Stocks, OPT Options, WAR Warrants, IOPT CBBC, CASH FOREX, FUT Futures, FOP Future Options |
| positionQty | double | 持仓数量 |
| salableQty | double | 可卖数量 |
| position | long | 持仓数量（废弃） |
| positionScale | int | 持仓数量的偏移量（废弃），比如position值为2135，positionScale为2，实际持仓数为21.35 |
| saleable | long | A股可卖数量（废弃） |
| averageCost | double | 持仓均价 |
| latestPrice | double | 标的当前价格 |
| marketValue | double | 持仓市值 |
| unrealizedPnl | double | 持仓盈亏 |
| timestamp | long | 时间戳 |

订单变动回调

| 字段  | 类型  | 描述  |
| --- | --- | --- |
| id  | long | 订单号 |
| account | String | 资金账号 |
| symbol | String | 持仓标的代码，如 'AAPL', '00700', 'ES', 'CN' |
| expiry | String | 仅支持期权、窝轮、牛熊证 |
| strike | String | 仅支持期权、窝轮、牛熊证 |
| right | String | 仅支持期权、窝轮、牛熊证 |
| identifier | String | 标的标识符。股票的identifier与symbol相同。期货的会带有合约月份，如 'CN2201' |
| multiplier | int | 每手数量，仅限 futures, options, warrants, CBBC |
| action | String | 买卖方向。BUY表示买入，SELL表示卖出。 |
| market | String | 市场。US、HK |
| currency | String | 币种。USD美元，HKD港币 |
| segType | String | 按交易品种划分的分类。S表示股票，C表示期货 |
| secType | String | STK Stocks, OPT Options, WAR Warrants, IOPT CBBC, CASH FOREX, FUT Futures, FOP Future Options |
| orderType | String | 订单类型。'MKT'市价单/'LMT'限价单/'STP'止损单/'STP\_LMT'止损限价单/'TRAIL'跟踪止损单 |
| isLong | boolean | 是否多头持仓 |
| totalQuantity | long | 下单数量 |
| totalQuantityScale | int | 下单数量偏移量，如 totalQuantity=111， totalQuantityScale=2，那么真实 totalQuantity=111\*10^(-2)=1.11 |
| filledQuantity | long | 成交总数量（订单分多笔成交的，filledQuantity为累计成交总数） |
| filledQuantityScale | int | 成交总数量偏移量 |
| avgFillPrice | double | 成交均价 |
| limitPrice | double | 限价单价格 |
| stopPrice | double | 止损价格 |
| realizedPnl | double | 已实现盈亏（只有综合账号有这个字段） |
| status | String | [订单状态](./appendix-enum-java.md#order-status) |
| replaceStatus | String | [订单改单状态](./appendix-enum-java.md#order-replace-status) |
| cancelStatus | String | [订单撤单状态](./appendix-enum-java.md#order-cancel-status) |
| outsideRth | boolean | 是否允许盘前盘后交易，仅适用于美股 |
| canModify | boolean | 是否能修改 |
| canCancel | boolean | 是否能取消 |
| liquidation | boolean | 是否为平仓订单 |
| name | String | 标的名称 |
| source | String | 订单来源(from 'OpenApi', or other) |
| errorMsg | String | 错误信息 |
| attrDesc | String | 订单描述信息 |
| commissionAndFee | float | 佣金费用总计 |
| openTime | long | 下单时间 |
| timestamp | long | 订单状态最后更新时间 |
| userMark | String | 自定义标注信息 |
| totalCashAmount | double | 下单总金额（仅限金额订单） |
| filledCashAmount | double | 成交金额（仅限金额订单） |
| attrList | `List<String>` | 订单属性列表，各属性含义如下： LIQUIDATION 强平, FRACTIONAL\_SHARE 碎股订单(非整股), EXERCISE 行权, EXPIRE 过期, ASSIGNMENT 被动行权分配, CASH\_SETTLE 现金交割, KNOCK\_OUT 敲出, RECALL 召回订单, ODD\_LOT 碎股订单（非整手）, DEALER 交易员下单, GREY\_MARKET 港股暗盘订单, BLOCK\_TRADE 大宗交易, ATTACHED\_ORDER 附加订单, OCA OCA订单 |
| timeInForce | string | 订单有效时间。DAY: 当日有效，GTC: 撤销前有效，GTD: 有效至指定日期。 |

订单执行明细报告回调

| 字段  | 类型  | 描述  |
| --- | --- | --- |
| id  | long | 订单执行ID |
| orderId | long | 订单号 |
| account | String | 资金账号 |
| symbol | String | 持仓标的代码，如 'AAPL', '00700', 'ES', 'CN' |
| identifier | String | 标的标识符。股票的identifier与symbol相同。期货的会带有合约月份，如 'CN2201' |
| multiplier | int | 每手数量(期权、期货专有) |
| action | String | 买卖方向。BUY表示买入，SELL表示卖出。 |
| market | String | 市场。US、HK |
| currency | String | 币种。USD美元，HKD港币 |
| segType | String | 按交易品种划分的分类。S表示股票，C表示期货 |
| secType | String | 交易品种，标的类型。STK表示股票，FUT表示期货 |
| filledPrice | double | 价格  |
| filledQuantity | long | 成交数量 |
| createTime | long | create time |
| updateTime | long | update time |
| transactTime | long | 成交时间 |
| timestamp | long | timestamp |

**回调接口**

实现 `ApiComposeCallback` 的对应方法

Java

    /** 
    * 回调接口： 
    * 根据不同subject调用不同接口
    */
    void assetChange(AssetData data)  //对应subject = Asset
    void positionChange(PositionData data) //对应subject = Position
    void orderStatusChange(OrderStatusData data) //对应subject = OrderStatus
    void orderTransactionChange(OrderTransactionData data) //对应subject = OrderTransaction

**示例**

实现回调接口示例

Java

    package com.tigerbrokers.stock.openapi.demo;
    
    import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.AssetData;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderStatusData;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderTransactionData;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.PositionData;
    import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
    import com.tigerbrokers.stock.openapi.client.util.ProtoMessageUtil;
    
    public class DefaultApiComposeCallback implements ApiComposeCallback {
    
      @Override
      public void orderStatusChange(OrderStatusData data) {
        ApiLogger.info("orderStatusChange:" + ProtoMessageUtil.toJson(data));
      }
    
      @Override
      public void orderTransactionChange(OrderTransactionData data) {
        ApiLogger.info("orderTransactionChange:" + ProtoMessageUtil.toJson(data));
      }
    
      @Override
      public void positionChange(PositionData data) {
        ApiLogger.info("positionChange:" + ProtoMessageUtil.toJson(data));
      }
    
      @Override
      public void assetChange(AssetData data) {
        ApiLogger.info("assetChange:" + ProtoMessageUtil.toJson(data));
      }
    
      @Override
      public void subscribeEnd(int id, String subject, String result) {
        ApiLogger.info("subscribe " + subject + " end. id:" + id + ", " + result);
      }
    
      @Override
      public void cancelSubscribeEnd(int id, String subject, String result) {
        ApiLogger.info("cancel subscribe " + subject + " end. id:" + id + ", " + result);
      }
    }

进行订阅

Java

    public class WebSocketDemo {
    
    //实际订阅时需要填充tigerId和privateKey，并实现callback接口
      private static ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
      private static WebSocketClient client;
      static {
        //从开发者信息页面导出的配置文件tiger_openapi_config.properties、tiger_openapi_token.properties存放路径
        clientConfig.configFilePath = "/data/tiger_config";
        // clientConfig.secretKey = "xxxxxx";// institutional trader private key
    
        // 原使用方式（不使用tiger_openapi_config.properties文件），必须配置tigerId, defaultAccount, privateKey三项，如果同时配置了configFilePath路径properties文件配置内容优先
        // clientConfig.tigerId = "your tiger id";
        // clientConfig.defaultAccount = "your account";
        // clientConfig.privateKey = ConfigUtil.readPrivateKey("/Users/tiger/rsa_private_key_pkcs8.pem");
        client = WebSocketClient.getInstance().clientConfig(clientConfig).apiComposeCallback(new DefaultApiComposeCallback());
      }
    
      public static void subscribe() {
        //创建连接
        client.connect();
    
        //订阅 订单/资产/持仓/订单执行报告
        client.subscribe(Subject.OrderStatus);    
        client.subscribe(Subject.Asset);
        client.subscribe(Subject.Position);
        client.subscribe(Subject.OrderTransaction);
    
        //等待
        TimeUnit.SECONDS.sleep(60000);
        
        // 取消订阅
        client.cancelSubscribe(Subject.Asset);
        client.cancelSubscribe(Subject.Position);
        client.cancelSubscribe(Subject.OrderStatus);
        client.cancelSubscribe(Subject.OrderTransaction);
        
        //非交易时间段建议关闭连接，调用disconnect()方法会自动注销之前的全部订阅信息
        //client.disconnect();
      }
    }

**返回示例**

assetChange:

JSON

    {
      "account": "13810712",
      "currency": "USD",
      "segment": "S",
      "availableFunds": 2285040.5475322,
      "excessLiquidity": 2284942.0475322,
      "netLiquidation": 2285529.3735322,
      "equityWithLoan": 2285418.2835322,
      "buyingPower": 9140162.1901287,
      "cashBalance": 2284275.2435322,
      "grossPositionValue": 1143.04,
      "initMarginReq": 377.736,
      "maintMarginReq": 476.236,
      "timestamp": "1669888806020"
    }
    // position change
    positionChange:{"account":"13810712","symbol":"AAPL","identifier":"AAPL","multiplier":1,"market":"US","currency":"USD","segment":"S","secType":"STK","position":"4","averageCost":75.0,"latestPrice":147.23,"marketValue":588.92,"unrealizedPnl":288.92,"timestamp":"1669888802018"}
    
    // order change
    orderStatusChange:{"id":"28875370355884032","account":"736845","symbol":"CL","identifier":"CL2312","multiplier":1000,"action":"BUY","market":"US","currency":"USD","segment":"C","secType":"FUT","orderType":"LMT","isLong":true,"totalQuantity":"1","filledQuantity":"1","avgFillPrice":77.76,"limitPrice":77.76,"status":"Filled","outsideRth":true,"name":"WTI原油2312","source":"android","commissionAndFee":4.0,"openTime":"1669200792000","timestamp":"1669200782221"}
    
    // OrderTransaction
    orderTransactionChange:{"id":"28875370482237440","orderId":"28875370355884032","account":"736845","symbol":"CL","identifier":"CL2312","multiplier":1000,"action":"BUY","market":"US","currency":"USD","segment":"C","secType":"FUT","filledPrice":77.76,"filledQuantity":"1","createTime":"1669200793664","updateTime":"1669200793664","transactTime":"1669200793593","timestamp":"1669200782233"}

positionChange:

JSON

    {
      "account": "13810712",
      "symbol": "AAPL",
      "identifier": "AAPL",
      "multiplier": 1,
      "market": "US",
      "currency": "USD",
      "segment": "S",
      "secType": "STK",
      "position": "4",
      "averageCost": 75.0,
      "latestPrice": 147.23,
      "marketValue": 588.92,
      "unrealizedPnl": 288.92,
      "timestamp": "1669888802018"
    }

orderStatusChange:

JSON

    {
      "id": "28875370355884032",
      "account": "736845",
      "symbol": "CL",
      "identifier": "CL2312",
      "multiplier": 1000,
      "action": "BUY",
      "market": "US",
      "currency": "USD",
      "segment": "C",
      "secType": "FUT",
      "orderType": "LMT",
      "isLong": true,
      "totalQuantity": "1",
      "filledQuantity": "1",
      "avgFillPrice": 77.76,
      "limitPrice": 77.76,
      "status": "Filled",
      "outsideRth": true,
      "name": "WTI原油2312",
      "source": "android",
      "commissionAndFee": 4.0,
      "openTime": "1669200792000",
      "timestamp": "1669200782221"
    }

orderTransactionChange:

JSON

    {
      "id": "28875370482237440",
      "orderId": "28875370355884032",
      "account": "736845",
      "symbol": "CL",
      "identifier": "CL2312",
      "multiplier": 1000,
      "action": "BUY",
      "market": "US",
      "currency": "USD",
      "segment": "C",
      "secType": "FUT",
      "filledPrice": 77.76,
      "filledQuantity": "1",
      "createTime": "1669200793664",
      "updateTime": "1669200793664",
      "transactTime": "1669200793593",
      "timestamp": "1669200782233"
    }

订单状态通知数据，如果是分多次成交的，filledQuantity为历史累计成交总数，依次返回数据如下：

JSON

    {
      "id": 28557131062709999,
      "symbol": "CN",
      "market": "SI",
      "currency": "USD",
      "secType": "FUT",
      "action": "BUY",
      "isLong": true,
      "totalQuantity": 9,
      "totalQuantityScale": 0,
      "filledQuantity": 1,
      "filledQuantityScale": 0,
      "orderType": "MKT",
      "avgFillPrice": 11824.0,
      "status": "PendingSubmit",
      "subStatusList": [],
      "subStatusDescList": [],
      "realizedPnl": 0.0,
      "commissionAndFee": 3.09,
      "gst": 0.22,
      "replaceStatus": "NONE",
      "cancelStatus": "NONE",
      "source": "OpenApi",
      "canModify": true,
      "canCancel": true,
      "multiplier": 1.0,
      "stockId": 15433673,
      "attr": 0,
      "type": "orderstatus",
      "outsideRth": false,
      "limitPrice": 0.0,
      "errorMsg": "",
      "openTime": 1666772819000,
      "updateTime": 1666772819000,
      "latestTime": 1666772819000,
      "userMark": "",
      "name": "FTSE China A50 Index - Nov 2022",
      "identifier": "CN2211",
      "account": "1234567",
      "timestamp": 1666772819780,
      "segment": "C"
    }
    
    {id=28557131062709999, symbol=CN, market=SI, currency=USD, secType=FUT, action=BUY, isLong=true, totalQuantity=9, totalQuantityScale=0, filledQuantity=3, filledQuantityScale=0, orderType=MKT, avgFillPrice=11824.0, status=PendingSubmit, subStatusList=[], subStatusDescList=[], realizedPnl=0.0, commissionAndFee=9.27, gst=0.65, replaceStatus=NONE, cancelStatus=NONE, source=OpenApi, canModify=true, canCancel=true, multiplier=1.0, stockId=15433673, attr=0, type=orderstatus, outsideRth=false, limitPrice=0.0, errorMsg=, openTime=1666772819000, updateTime=1666772819000, latestTime=1666772819000, userMark=, name=FTSE China A50 Index - Nov 2022, identifier=CN2211, account=1234567, timestamp=1666772819797, segment=C}
    
    {id=28557131062709999, symbol=CN, market=SI, currency=USD, secType=FUT, action=BUY, isLong=true, totalQuantity=9, totalQuantityScale=0, filledQuantity=8, filledQuantityScale=0, orderType=MKT, avgFillPrice=11824.625, status=PendingSubmit, subStatusList=[], subStatusDescList=[], realizedPnl=0.0, commissionAndFee=24.72, gst=1.73, replaceStatus=NONE, cancelStatus=NONE, source=OpenApi, canModify=true, canCancel=true, multiplier=1.0, stockId=15433673, attr=0, type=orderstatus, outsideRth=false, limitPrice=0.0, errorMsg=, openTime=1666772819000, updateTime=1666772819000, latestTime=1666772819000, userMark=, name=FTSE China A50 Index - Nov 2022, identifier=CN2211, account=1234567, timestamp=1666772819808, segment=C}
    
    {id=28557131062709999, symbol=CN, market=SI, currency=USD, secType=FUT, action=BUY, isLong=true, totalQuantity=9, totalQuantityScale=0, filledQuantity=9, filledQuantityScale=0, orderType=MKT, avgFillPrice=11824.6666666667, status=Filled, subStatusList=[], subStatusDescList=[], realizedPnl=0.0, commissionAndFee=27.81, gst=1.95, replaceStatus=NONE, cancelStatus=NONE, source=OpenApi, canModify=false, canCancel=false, multiplier=1.0, stockId=15433673, attr=0, type=orderstatus, outsideRth=false, limitPrice=0.0, errorMsg=, openTime=1666772819000, updateTime=1666772819000, latestTime=1666772819000, userMark=, name=FTSE China A50 Index - Nov 2022, identifier=CN2211, account=1234567, timestamp=1666772819816, segment=C}

* * *

String cancelSubscribe(Subject subject) 取消订阅

[](./push-account-java.md#string-cancelsubscribesubject-subject-%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**说明**

取消订阅账户变动的推送

**输入参数(退订主题)**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| subject | com.tigerbrokers.stock.openapi.client.struct.enums.Subject | Yes | 订阅主题 |

Subject 为要取消订阅的主题，主要包括四种：OrderStatus(订单)，Asset(资产)，Position(持仓)，OrderTransaction(订单执行) 含义同订阅主题一致。

> ⚠️
> 
> **CAUTION**
> 
> 取消任意一个订阅，会把OrderStatus(订单)，Asset(资产)，Position(持仓)，OrderTransaction(订单执行)四种订阅都取消

**示例**

Java

    // 取消订阅
    client.cancelSubscribe(Subject.Asset);
    client.cancelSubscribe(Subject.Position);
    client.cancelSubscribe(Subject.OrderStatus);
    client.cancelSubscribe(Subject.OrderTransaction);
