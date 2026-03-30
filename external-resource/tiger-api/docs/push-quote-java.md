# 行情订阅推送

订阅股票行情

[](./push-quote-java.md#%E8%AE%A2%E9%98%85%E8%82%A1%E7%A5%A8%E8%A1%8C%E6%83%85)

--------------------------------------------------------------------------------------------------------------------

`subscribeQuote(Set<String> symbols)`

  
**取消方法**  
`cancelSubscribeQuote(Set<String> symbols)`

**说明**

订阅API，提供了股票行情数据订阅服务，可以实时获取行情的变化信息。 行情订阅推送接口是异步接口，通过实现`ApiComposeCallback`接口可以获得异步请求结果。 回调接口返回结果类型为基本行情`QuoteBasicData`对象，最优报价`QuoteBBOData`对象。

**输入参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| symbols | Set<String> | Yes | 股票代码列表，股票 symbol 格式： 如`AAPL`，`00700` |

**返回值**

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| id  | string | SDK 订阅请求时本地生成的 ID，序列递增。在`subscribeEnd(int id, String subject, String result)`回调方法中返回请求 ID 和订阅是否成功的结果 |

**回调接口**

*   `void quoteChange(QuoteBasicData data)`
*   `void quoteAskBidChange(QuoteBBOData data)`

**示例**

定义回调接口，订阅成功后，会在该回调类的对应接口中接收到数据。

Java

    package com.tigerbrokers.stock.openapi.demo;
    
    import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
    import com.tigerbrokers.stock.openapi.client.socket.data.TradeTick;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.AssetData;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.KlineData;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.OptionTopData;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderStatusData;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderTransactionData;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.PositionData;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBBOData;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBasicData;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteDepthData;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.StockTopData;
    import com.tigerbrokers.stock.openapi.client.socket.data.pb.TickData;
    import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;
    import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
    import com.tigerbrokers.stock.openapi.client.util.ProtoMessageUtil;
    
    public class DefaultApiComposeCallback implements ApiComposeCallback {
    
      /*股票基本行情回调*/
      @Override
      public void quoteChange(QuoteBasicData data) {
        ApiLogger.info("quoteChange:" + ProtoMessageUtil.toJson(data));
      }
      /*股票最优买卖价行情回调*/
      @Override
      public void quoteAskBidChange(QuoteBBOData data) {
        ApiLogger.info("quoteAskBidChange:" + ProtoMessageUtil.toJson(data));
      }
      
      /*期权行情回调*/
      @Override
      public void optionChange(QuoteBasicData data) {
        ApiLogger.info("optionChange:" + ProtoMessageUtil.toJson(data));
      }
      /*期权最优买卖价行情回调*/
      @Override
      public void optionAskBidChange(QuoteBBOData data) {
        ApiLogger.info("optionAskBidChange:" + ProtoMessageUtil.toJson(data));
      }
    
      /*期货行情回调*/
      @Override
      public void futureChange(QuoteBasicData data) {
        ApiLogger.info("futureChange:" + ProtoMessageUtil.toJson(data));
      }
      /*期货最优买卖价行情回调*/
      @Override
      public void futureAskBidChange(QuoteBBOData data) {
        ApiLogger.info("futureAskBidChange:" + ProtoMessageUtil.toJson(data));
      }
    
      /*深度行情回调*/
      @Override
      public void depthQuoteChange(QuoteDepthData data) {
        ApiLogger.info("depthQuoteChange:" + ProtoMessageUtil.toJson(data));
      }
      
      /*逐笔成交行情回调*/
      @Override
      public void tradeTickChange(TradeTick data) {
        ApiLogger.info("tradeTickChange:" + JacksonUtil.writeValueAsString(data));
      }
      /*全量逐笔成交行情回调*/
      @Override
      public void fullTickChange(TickData data) {
          ApiLogger.info("fullTickChange:" + ProtoMessageUtil.toJson(data));
      }
      /*分钟K线数据回调*/
      @Override
      public void klineChange(KlineData data) {
        ApiLogger.info("klineChange:" + ProtoMessageUtil.toJson(data));
      }
    
      /**股票行情榜单数据推送*/
      @Override
      public void stockTopPush(StockTopData data) {
        ApiLogger.info("stockTopPush, market:" + data.getMarket());
        for (StockTopData.TopData topData : data.getTopDataList()) {
          ApiLogger.info("stockTopPush, targetName:" + topData.getTargetName()
              + ", topList:" + ProtoMessageUtil.toJson(topData));
        }
      }
    
      /**期权行情榜单数据推送*/
      @Override
      public void optionTopPush(OptionTopData data) {
        ApiLogger.info("optionTopPush, market:" + data.getMarket());
        for (OptionTopData.TopData topData : data.getTopDataList()) {
          ApiLogger.info("optionTopPush, targetName:" + topData.getTargetName()
              + ", topList:" + ProtoMessageUtil.toJson(topData));
        }
      }
      
      /*订阅成功回调*/
      @Override
      public void subscribeEnd(int id, String subject, String result) {
        ApiLogger.info("subscribe " + subject + " end. id:" + id + ", " + result);
      }
    
      /*取消订阅回调*/
      @Override
      public void cancelSubscribeEnd(int id, String subject, String result) {
        ApiLogger.info("cancel subscribe " + subject + " end. id:" + id + ", " + result);
      }
    
      /*查询已订阅symbol回调*/
      @Override
      public void getSubscribedSymbolEnd(SubscribedSymbol subscribedSymbol) {
        ApiLogger.info("getSubscribedSymbolEnd:" + JSONObject.toJSONString(subscribedSymbol));
      }
    }

进行订阅

Java

    public class WebSocketDemo {
    
    //实际订阅时需要填充tigerId和privateKey，并实现ApiComposeCallback接口，示例里面为DefaultApiComposeCallback
      private static ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
      private static WebSocketClient client;
      static {
        //从开发者信息页面导出的配置文件tiger_openapi_config.properties、tiger_openapi_token.properties存放路径
        clientConfig.configFilePath = "/data/tiger_config";
        // clientConfig.secretKey = "xxxxxx";// institutional trader private key
        client = WebSocketClient.getInstance().clientConfig(clientConfig).apiComposeCallback(new DefaultApiComposeCallback());
      }
    
      public static void subscribe() {
        client.connect();
        Set<String> symbols = new HashSet<>();
    
        //股票订阅
        symbols.add("AAPL");
        symbols.add("SPY");
    
        //订阅相关symbol
        client.subscribeQuote(symbols);
    
        //订阅深度数据（只支持股票）
        client.subscribeDepthQuote(symbols);
    
        //查询订阅详情
        client.getSubscribedSymbols();
    
        //等待接收数据
        TimeUnit.SECONDS.sleep(60000);
        //取消订阅
        client.cancelSubscribeQuote(symbols);
        client.cancelSubscribeDepthQuote(symbols);
        //取消全部标的（股票，期权，期货）的行情订阅
        client.cancelSubscribeQuote(new HashSet<>());
        //取消全部标的（股票，期货）的深度行情订阅
        client.cancelSubscribeDepthQuote(new HashSet<>());
        //取消全部标的（股票，期货）的逐笔行情订阅
        client.cancelSubscribeTradeTick(new HashSet<>());
    
        //注意：主动断开连接时会清除所有的订阅数据
        //client.disconnect();
      }
    }

**返回数据**

股票行情回调数据全部字段参考：[行情推送数据](./appendix-enum-java.md#quote-change)

> ⚠️
> 
> **CAUTION**
> 
> 股票行情回调数据有两种类型：基本行情`QuoteBasicData`，最优报价`QuoteBBOData`，两种类型数据返回的字段不一样 。

港股股票行情数据示例：

JSON

    {
        "symbol":"00700",
        "type":"BASIC",
        "timestamp":"1684721758123",
        "serverTimestamp":"1684721758228",
        "avgPrice":330.493,
        "latestPrice":332.2,
        "latestPriceTimestamp":"1684721758103",
        "latestTime":"05-22 10:15:58",
        "preClose":333.2,
        "volume":"4400026",
        "amount":1454057948,
        "open":331.8,
        "high":334.2,
        "low":328.2,
        "marketStatus":"Trading",
        "mi":{
            "p":332.2,
            "a":330.493,
            "t":"1684721700000",
            "v":"75400",
            "o":331.6,
            "h":332.2,
            "l":331.4
        }
    }

港股盘口最优买卖价行情数据示例：

JSON

    {
        "symbol":"00700",
        "type":"BBO",
        "timestamp":"1684721757927",
        "askPrice":332.2,
        "askSize":"32100",
        "askTimestamp":"1684721757344",
        "bidPrice":332,
        "bidSize":"3500",
        "bidTimestamp":"1684721757773"
    }

美股股票行情数据示例：

JSON

    {
        "symbol":"AAPL",
        "type":"BASIC",
        "timestamp":"1684766012120",
        "serverTimestamp":"1684766012129",
        "avgPrice":174.1721,
        "latestPrice":174.175,
        "latestPriceTimestamp":"1684766011918",
        "latestTime":"05-22 10:33:31 EDT",
        "preClose":175.16,
        "volume":"12314802",
        "amount":2144365591.410586,
        "open":173.98,
        "high":174.71,
        "low":173.45,
        "marketStatus":"Trading",
        "mi":{
            "p":174.175,
            "a":174.1721,
            "t":"1684765980000",
            "v":"57641",
            "o":174.21,
            "h":174.22,
            "l":174.14
        }
    }

美股股票盘口数据示例：

JSON

    {
        "symbol":"AAPL",
        "type":"BBO",
        "timestamp":"1676992715509",
        "askPrice":149.96,
        "askSize":"200",
        "askTimestamp":"1676992715367",
        "bidPrice":149.94,
        "bidSize":"700",
        "bidTimestamp":"1676992715367"
    }

美股股票盘前交易数据示例：

> ⚠️
> 
> **CAUTION**
> 
> 美股盘前盘后的字段和盘中不一样

JSON

    {
        "symbol":"AAPL",
        "type":"BASIC",
        "timestamp":"1684753559744",
        "serverTimestamp":"1684753559752",
        "latestPrice":173.66,
        "latestPriceTimestamp":"1684753559744",
        "latestTime":"07:05 EDT",
        "preClose":175.16,
        "volume":"366849",
        "amount":63731858.18000001,
        "hourTradingTag":"PreMarket",
        "mi":{
            "p":173.66,
            "a":173.72891,
            "t":"1684753500000",
            "v":"5604",
            "o":173.64,
            "h":173.67,
            "l":173.63
        }
    }

* * *

订阅期权行情

[](./push-quote-java.md#%E8%AE%A2%E9%98%85%E6%9C%9F%E6%9D%83%E8%A1%8C%E6%83%85)

--------------------------------------------------------------------------------------------------------------------

`subscribeOption(Set<String> symbols)`

  
**取消方法**  
`cancelSubscribeOption(Set<String> symbols)`

**说明**

订阅期权行情（支持美国和香港市场期权）。 行情订阅推送接口是异步接口，通过实现 `ApiComposeCallback` 接口可以获得异步请求结果。 回调接口返回结果类型为基本行情`QuoteBasicData`对象，最优报价`QuoteBBOData`对象。

**请求参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| symbols | Set<String> | Yes | 期权代码列表 |

期权 symbol 格式：期权 symbol 支持2种格式

*   一种是 symbol 名称 + 到期日 + 行权价格 + 方向，以空格分隔。如：(AAPL 20190329 182.5 PUT)。
*   另一种为 identifier，查询期权行情时返回该字段。如：(SPY 190508C00290000)

**返回值**

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| id  | string | SDK 订阅请求时本地生成的 ID，序列递增。在`subscribeEnd(int id, String subject, String result)`回调方法中返回请求 ID 和订阅是否成功的结果 |

**订阅示例**

Java

    Set<String> symbols = new HashSet<>();
    //期权的一种订阅方式
    symbols.add("AAPL 20230317 150.0 CALL");
    symbols.add("ALB.HK 20250730 117.50 CALL");
    //期权另外一种订阅方式
    symbols.add("SPY   190508C00290000");
    
    client.subscribeOption(symbols);
    
    //Wait to receive the data
    TimeUnit.SECONDS.sleep(120000);
    // Cancel subscription
    client.cancelSubscribeOption(symbols);

**对应回调接口**

*   `void optionChange(QuoteBasicData data)`
*   `void optionAskBidChange(QuoteBBOData data)`

**返回数据**

期权交易数据示例：

JSON

    {
        "symbol":"AAPL 20230317 150.0 CALL",
        "type":"BASIC",
        "timestamp":"1676994444927",
        "latestPrice":4.83,
        "latestPriceTimestamp":"1676994444927",
        "latestTime":"",
        "preClose":6.21,
        "volume":"3181",
        "amount":939117.0060634613,
        "open":4.85,
        "high":5.6,
        "low":4.64,
        "identifier":"AAPL  230317C00150000",
        "openInt":"82677"
    }

期权盘口数据示例：

JSON

    {
        "symbol":"AAPL 20230317 150.0 CALL",
        "type":"BBO",
        "timestamp":"1676994393156",
        "askPrice":4.85,
        "askSize":"11",
        "askTimestamp":"1676994393156",
        "bidPrice":4.8,
        "bidSize":"992",
        "bidTimestamp":"1676994390931"
    }

* * *

订阅期货行情

[](./push-quote-java.md#%E8%AE%A2%E9%98%85%E6%9C%9F%E8%B4%A7%E8%A1%8C%E6%83%85)

--------------------------------------------------------------------------------------------------------------------

`subscribeFuture(Set<String> symbols)`

  
**取消方法**  
`cancelSubscribeFuture(Set<String> symbols)`

**说明**

订阅期货行情。 行情订阅推送接口是异步接口，通过实现`ApiComposeCallback`接口可以获得异步请求结果。 回调接口返回结果类型为基本行情`QuoteBasicData`对象，最优报价`QuoteBBOData`对象。

**请求参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| symbols | Set<String> | Yes | 期货代码列表 |

**返回值**

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| id  | string | SDK 订阅请求时本地生成的 ID，序列递增。在`subscribeEnd(int id, String subject, String result)`回调方法中返回请求 ID 和订阅是否成功的结果 |

**示例**

Java

    Set<String> symbols = new HashSet<>();
    //期货订阅
    symbols.add("ESmain");
    symbols.add("ES2306");
    
    client.subscribeFuture(symbols);
    
    //Wait to receive the data
    TimeUnit.SECONDS.sleep(120000);
    // Cancel subscription
    client.cancelSubscribeFuture(symbols);

**对应回调接口**

*   `void futureChange(QuoteBasicData data)`
*   `void futureAskBidChange(QuoteBBOData data)`

**返回示例**

期货行情数据示例：

JSON

    {
        "symbol":"ESmain",
        "type":"BASIC",
        "timestamp":"1684766824130",
        "avgPrice":4206.476,
        "latestPrice":4202.5,
        "latestPriceTimestamp":"1684766824000",
        "latestTime":"05-22 09:47:04 -0500",
        "preClose":4204.75,
        "volume":"557570",
        "open":4189,
        "high":4221.75,
        "low":4186.5,
        "marketStatus":"Trading",
        "tradeTime":"1684766824000",
        "preSettlement":4204.75,
        "minTick":0.25,
        "mi":{
            "p":4202.25,
            "a":4206.476,
            "t":"1684766820000",
            "v":"96",
            "o":4202.25,
            "h":4202.5,
            "l":4202.0
        }
    }

期货盘口数据示例：

JSON

    {
        "symbol":"ESmain",
        "type":"BBO",
        "timestamp":"1684766824130",
        "askPrice":4202.75,
        "askSize":"70",
        "askTimestamp":"1684766824129",
        "bidPrice":4202.5,
        "bidSize":"2",
        "bidTimestamp":"1684766824130"
    }

* * *

订阅数字货币行情

[](./push-quote-java.md#%E8%AE%A2%E9%98%85%E6%95%B0%E5%AD%97%E8%B4%A7%E5%B8%81%E8%A1%8C%E6%83%85)

----------------------------------------------------------------------------------------------------------------------------------------

`subscribeCc(Set<String> symbols)`

  
**取消方法**  
`cancelSubscribeCc(Set<String> symbols)`

**说明**

订阅API，提供了数字货币行情数据订阅服务，可以实时获取行情的变化信息。 行情订阅推送接口是异步接口，通过实现`ApiComposeCallback`接口可以获得异步请求结果。 回调接口返回结果类型为基本行情`QuoteBasicData`对象，最优报价`QuoteBBOData`对象。

**输入参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| symbols | Set<String> | Yes | 数字货币代码列表，格式： 如`ETH.USD`，`BTC.USD` |

**返回值**

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| id  | string | SDK 订阅请求时本地生成的 ID，序列递增。在`subscribeEnd(int id, String subject, String result)`回调方法中返回请求 ID 和订阅是否成功的结果 |

**回调接口**

*   `void ccChange(QuoteBasicData data)`
*   `void ccAskBidChange(QuoteBBOData data)`

**示例**

定义回调接口，订阅成功后，会在该回调类的对应接口中接收到数据。

Java

    public class DefaultApiComposeCallback implements ApiComposeCallback {
    
      @Override
      public void ccChange(QuoteBasicData data) {
        String json = ProtoMessageUtil.toJson(data);
        log.info("ccChange:" + json);
      }
    
      @Override
      public void ccAskBidChange(QuoteBBOData data) {
        String json = ProtoMessageUtil.toJson(data);
        log.info("ccAskBidChange:" + json);
      }
    }

进行订阅

Java

    public class WebSocketDemo {
    
    //实际订阅时需要填充tigerId和privateKey，并实现ApiComposeCallback接口，示例里面为DefaultApiComposeCallback
      private static ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
      private static WebSocketClient client;
      static {
        //从开发者信息页面导出的配置文件tiger_openapi_config.properties、tiger_openapi_token.properties存放路径
        clientConfig.configFilePath = "/data/tiger_config";
        // clientConfig.secretKey = "xxxxxx";// institutional trader private key
        client = WebSocketClient.getInstance().clientConfig(clientConfig).apiComposeCallback(new DefaultApiComposeCallback());
      }
    
      public static void subscribe() {
        client.connect();
    
        Set<String> ccSymbols = new HashSet<>();
        ccSymbols.add("BTC.USD");
        ccSymbols.add("ETH.USD");
    
        //订阅数字货币
        client.subscribeCc(ccSymbols);
    
        //查询订阅详情
        client.getSubscribedSymbols();
    
        //等待接收数据
        TimeUnit.SECONDS.sleep(60000);
      
        //注意：主动断开连接时会清除所有的订阅数据
        client.disconnect();
      }
    }

**返回数据**

回调数据全部字段参考：[行情推送数据](./appendix-enum-java.md#quote-change)

> ⚠️
> 
> **CAUTION**
> 
> 股票行情回调数据有两种类型：基本行情`QuoteBasicData`，最优报价`QuoteBBOData`，两种类型数据返回的字段不一样 。

行情数据示例：

JSON

    {
    	"symbol": "ETH.USD",
    	"type": "BASIC",
    	"timestamp": "1770041127619",
    	"serverTimestamp": "1770041127816",
    	"latestPrice": 2322.97,
    	"latestPriceTimestamp": "1770041127619",
    	"latestTime": "02-02 22:05:27 HKT",
    	"preClose": 2313.05,
    	"volumeDecimal": 27711.5,
    	"amount": 6.246553826751E7,
    	"open": 2314.18,
    	"high": 2374.87,
    	"low": 2156.8,
    	"marketStatus": "TRADING"
    }

盘口最优买卖价行情数据示例：

JSON

    {
    	"symbol": "ETH.USD",
    	"type": "BBO",
    	"timestamp": "1770041127619",
    	"askPrice": 2322.97,
    	"askTimestamp": "1770041127605",
    	"bidPrice": 2322.95,
    	"bidSize": "1",
    	"bidTimestamp": "1770041127605"
    }

  

* * *

订阅深度行情

[](./push-quote-java.md#%E8%AE%A2%E9%98%85%E6%B7%B1%E5%BA%A6%E8%A1%8C%E6%83%85)

--------------------------------------------------------------------------------------------------------------------

`subscribeDepthQuote(Set<String> symbols)`

  
**取消方法**  
`cancelSubscribeDepthQuote(Set<String> symbols)`

**说明**

订阅多档深度行情，支持股票（美股/港股）、期权（美股/港股）和期货。**美股深度行情推送频率为 300 ms，港股深度行情推送频率为 2s，返回最高 40 档的买卖盘挂单数据（港股只有 10 档）**。 行情订阅推送接口是异步接口，通过实现`ApiComposeCallback`接口可以获得异步请求结果。 回调接口返回结果类型为`QuoteDepthData`对象。

**请求参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| symbols | Set<String> | Yes | 股票、期权、期货代码列表 |

**返回值**

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| id  | string | SDK 订阅请求时本地生成的 ID，序列递增。在`subscribeEnd(int id, String subject, String result)`回调方法中返回请求 ID 和订阅是否成功的结果 |

**示例**

Java

    Set<String> symbols = new HashSet<>();
    //深度行情订阅
    symbols.add("AAPL");
    symbols.add("ESmain");
    symbols.add("AAPL 20240209 180.0 CALL");
    
    client.subscribeDepthQuote(symbols);
    
    //Wait to receive the data
    TimeUnit.SECONDS.sleep(120000);
    // Cancel subscription
    client.cancelSubscribeDepthQuote(symbols);

**回调接口**

*   `void depthQuoteChange(QuoteDepthData data)`

**返回数据**

数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票标的 |
| timestamp | long | 深度数据时间 |
| ask | `List<OrderBook>` | 卖盘数据 |
| bid | `List<OrderBook>` | 买盘数据 |

OrderBook的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| price | double | 每档价格 |
| volume | long | 挂单量 |
| orderCount | int | 订单数（只有港股股票有值） |
| exchange | string | 期权数据源（只有期权有值），如果 price 或 volume 为 0，表示这个数据源的报价已失效。枚举值详见： [期权交易所](./appendix-enum-java.md#future-exchange) |
| time | long | 期权交易所挂单时间戳（只有期权有值） |

**回调结果示例**

JSON

    // US Market
    {
      "symbol": "AAPL",
      "timestamp": "1676993368405",
      "ask": {
        "price": [\
          149.69,\
          149.69,\
          149.69,\
          149.69,\
          149.69,\
          149.69,\
          149.7,\
          149.7,\
          149.7,\
          149.7,\
          149.7,\
          149.7,\
          149.7,\
          149.7,\
          149.7,\
          149.7,\
          149.71,\
          149.71,\
          149.71,\
          149.71,\
          149.71,\
          149.71,\
          149.71,\
          149.71,\
          149.72,\
          149.72,\
          149.72,\
          149.72,\
          149.72,\
          149.72,\
          149.72,\
          149.72,\
          149.72,\
          149.72,\
          149.73,\
          149.73,\
          149.73,\
          149.73,\
          149.73,\
          149.73\
        ],
        "volume": [\
          "100",\
          "100",\
          "23",\
          "200",\
          "100",\
          "100",\
          "200",\
          "100",\
          "100",\
          "100",\
          "82",\
          "100",\
          "100",\
          "200",\
          "25",\
          "100",\
          "185",\
          "100",\
          "100",\
          "82",\
          "87",\
          "25",\
          "100",\
          "100",\
          "100",\
          "100",\
          "76",\
          "200",\
          "100",\
          "100",\
          "16",\
          "87",\
          "100",\
          "100",\
          "100",\
          "100",\
          "200",\
          "100",\
          "76",\
          "100"\
        ]
      },
      "bid": {
        "price": [\
          149.68,\
          149.68,\
          149.68,\
          149.68,\
          149.67,\
          149.67,\
          149.67,\
          149.67,\
          149.67,\
          149.67,\
          149.67,\
          149.67,\
          149.66,\
          149.66,\
          149.66,\
          149.66,\
          149.66,\
          149.66,\
          149.66,\
          149.66,\
          149.66,\
          149.66,\
          149.66,\
          149.66,\
          149.66,\
          149.65,\
          149.65,\
          149.65,\
          149.65,\
          149.65,\
          149.65,\
          149.65,\
          149.65,\
          149.65,\
          149.65,\
          149.64,\
          149.64,\
          149.64,\
          149.64,\
          149.64\
        ],
        "volume": [\
          "84",\
          "87",\
          "100",\
          "100",\
          "100",\
          "49",\
          "100",\
          "100",\
          "87",\
          "200",\
          "100",\
          "100",\
          "100",\
          "100",\
          "100",\
          "20",\
          "1",\
          "4",\
          "1",\
          "200",\
          "100",\
          "87",\
          "25",\
          "100",\
          "200",\
          "200",\
          "100",\
          "1",\
          "25",\
          "87",\
          "100",\
          "100",\
          "100",\
          "25",\
          "100",\
          "100",\
          "100",\
          "1",\
          "87",\
          "100"\
        ]
      }
    }
    
    // HK Market
    {
      "symbol": "00700",
      "timestamp": "1670465696884",
      "ask": {
        "price": [\
          311.4,\
          311.6,\
          311.8,\
          312.0,\
          312.2,\
          312.4,\
          312.6,\
          312.8,\
          313.0,\
          313.2\
        ],
        "volume": [\
          "15600",\
          "5700",\
          "16600",\
          "33800",\
          "61100",\
          "14800",\
          "28300",\
          "28400",\
          "61100",\
          "39200"\
        ],
        "orderCount": [\
          16,\
          13,\
          19,\
          79,\
          39,\
          29,\
          66,\
          56,\
          160,\
          27\
        ]
      },
      "bid": {
        "price": [\
          311.2,\
          311.0,\
          310.8,\
          310.6,\
          310.4,\
          310.2,\
          310.0,\
          309.8,\
          309.6,\
          309.4\
        ],
        "volume": [\
          "2300",\
          "8300",\
          "18000",\
          "8800",\
          "7700",\
          "8500",\
          "26700",\
          "11700",\
          "13700",\
          "22600"\
        ],
        "orderCount": [\
          10,\
          15,\
          18,\
          9,\
          6,\
          11,\
          17,\
          30,\
          10,\
          5\
        ]
      }
    }

* * *

订阅逐笔成交数据

[](./push-quote-java.md#%E8%AE%A2%E9%98%85%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4%E6%95%B0%E6%8D%AE)

----------------------------------------------------------------------------------------------------------------------------------------

`subscribeTradeTick(Set<String> symbols)`

  
**取消方法**  
`cancelSubscribeTradeTick(Set<String> symbols)`

**说明**

订阅股票的逐笔成交数据，**逐笔推送间隔为 200ms，采用快照方式推送，每次推送最新的 50 条逐笔记录**。 逐笔成交订阅推送接口是异步接口，通过实现`ApiComposeCallback`接口可以获得异步请求结果。 回调接口返回结果类型为`TradeTick`对象。

支持美国和香港市场股票、期货标的订阅

**请求参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| symbols | Set<String> | Yes | 股票、期货代码列表 |

**返回值**

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| id  | string | SDK 订阅请求时本地生成的 ID，序列递增。在`subscribeEnd(int id, String subject, String result)`回调方法中返回请求 ID 和订阅是否成功的结果 |

**订阅示例**

Java

    Set<String> symbols = new HashSet<>();
    //逐笔成交股票标的订阅
    symbols.add("AAPL");
    symbols.add("00700");
    symbols.add("ESmain");
    
    client.subscribeTradeTick(symbols);
    
    //Wait to receive the data
    TimeUnit.SECONDS.sleep(120000);
    // Cancel subscription
    client.cancelSubscribeTradeTick(symbols);

**对应回调接口**

*   `void tradeTickChange(TradeTick data)`

TradeTick 数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票标的、期货标的 |
| secType | SecType | STK/FUT |
| quoteLevel | string | 数据来自的行情权限级别（对于美股，usQuoteBasic 的逐笔数据量比 usStockQuote 的要少）；期货没有级别区分 |
| timestamp | long | 数据时间戳 |
| ticks | `List<Tick>` | 逐笔成交数据集合 |

ticks 数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| sn  | long | 逐笔序号 |
| volume | long | 成交量 |
| tickType | string | \*表示中性，+表示主动买入，-表示主动卖出（期货逐笔没有） |
| price | double | 成交价 |
| time | long | 交易时间戳 |
| cond | string | 每笔数据的成交条件列表，如果数组为空表示当前批量的每笔都为自动对盘成交（期货逐笔没有） |
| partCode | string | 每笔交易的交易所 code（仅美股股票） |
| partName | string | 每笔交易的交易所名（仅美股股票） |

**回调结果示例**

JSON

    // 美股
    {
      "symbol": "AAPL",
      "secType": "STK",
      "quoteLevel": "usQuoteBasic",
      "timestamp": 1676993925700,
      "ticks": [\
        {\
          "sn": 116202,\
          "volume": 50,\
          "tickType": "*",\
          "price": 149.665,\
          "time": 1676993924289,\
          "cond": "US_REGULAR_SALE"\
        },\
        {\
          "sn": 116203,\
          "volume": 1,\
          "tickType": "*",\
          "price": 149.68,\
          "time": 1676993924459,\
          "cond": "US_REGULAR_SALE"\
        },\
        {\
          "sn": 116204,\
          "volume": 1,\
          "tickType": "*",\
          "price": 149.67,\
          "time": 1676993925200,\
          "cond": "US_REGULAR_SALE"\
        },\
        {\
          "sn": 116205,\
          "volume": 5,\
          "tickType": "*",\
          "price": 149.6652,\
          "time": 1676993925410,\
          "cond": "US_REGULAR_SALE"\
        }\
      ]
    }
    
    // 港股
    {
      "symbol": "00700",
      "secType": "STK",
      "quoteLevel": "hkStockQuoteLv2",
      "timestamp": 1669345639970,
      "ticks": [\
        {\
          "sn": 35115,\
          "volume": 300,\
          "tickType": "+",\
          "price": 269.2,\
          "time": 1669345639496,\
          "cond": "HK_AUTOMATCH_NORMAL"\
        },\
        {\
          "sn": 35116,\
          "volume": 200,\
          "tickType": "+",\
          "price": 269.2,\
          "time": 1669345639610,\
          "cond": "HK_AUTOMATCH_NORMAL"\
        }\
      ]
    }
    
    // Futures trade tick
    {
      "symbol": "HSImain",
      "secType": "FUT",
      "timestamp": 1669345640575,
      "ticks": [\
        {\
          "sn": 261560,\
          "volume": 1,\
          "price": 17465.0,\
          "time": 1669345639000\
        },\
        {\
          "sn": 261561,\
          "volume": 1,\
          "price": 17465.0,\
          "time": 1669345639000\
        },\
        {\
          "sn": 261562,\
          "volume": 1,\
          "price": 17465.0,\
          "time": 1669345639000\
        },\
        {\
          "sn": 261563,\
          "volume": 1,\
          "price": 17465.0,\
          "time": 1669345639000\
        }\
      ]
    }

* * *

订阅全量逐笔成交数据

[](./push-quote-java.md#%E8%AE%A2%E9%98%85%E5%85%A8%E9%87%8F%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4%E6%95%B0%E6%8D%AE)

------------------------------------------------------------------------------------------------------------------------------------------------------------

`subscribeTradeTick(Set<String> symbols)`

  
**取消方法**  
`cancelSubscribeTradeTick(Set<String> symbols)`

**说明**

订阅股票的全量逐笔成交数据。**需要找管理员申请开通权限，区分之前的快照逐笔数据，需要在开通权限后，本地配置`ClientConfig.DEFAULT_CONFIG.useFullTick = true;`** 逐笔成交订阅推送接口是异步接口，通过实现`ApiComposeCallback`接口可以获得异步请求结果。 回调接口返回结果类型为`TickData`对象。

支持美国和香港市场股票的订阅

**请求参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| symbols | Set<String> | Yes | 股票代码列表 |

**返回值**

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| id  | string | SDK 订阅请求时本地生成的 ID，序列递增。在`subscribeEnd(int id, String subject, String result)`回调方法中返回请求 ID 和订阅是否成功的结果 |

**订阅示例**

Java

    Set<String> symbols = new HashSet<>();
    //逐笔成交股票标的订阅
    symbols.add("AAPL");
    symbols.add("00700");
    
    client.subscribeTradeTick(symbols);
    
    //Wait to receive the data
    TimeUnit.SECONDS.sleep(120000);
    // Cancel subscription
    client.cancelSubscribeTradeTick(symbols);

**对应回调接口**

*   `void fullTickChange(TickData data)`

TickData数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票标的 |
| timestamp | long | 数据时间戳 |
| ticks | `List<Tick>` | 逐笔成交数据集合 |

ticks的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| sn  | long | 逐笔序号 |
| time | long | 交易时间戳 |
| price | float | 成交价 |
| volume | long | 成交量 |
| type | string | \*表示中性，+表示主动买入，-表示主动卖出 |
| cond | string | 每笔数据的成交条件列表，如果数组为空表示当前批量的每笔都为自动对盘成交，可能为 null |
| partCode | string | 每笔交易的交易所code（仅美股股票），可能为 null |

**回调结果示例**

JSON

    {"symbol":"AAPL","ticks":[{"sn":"69745","time":"1712585464248","price":168.96,"volume":26,"type\
    ":"+","partCode":"t"},{"sn":"69746","time":"1712585464248","price":168.96,"volume":22,"type":"+","partCode":"t"}],"timestamp":"1712585464415"
    ,"source":"NLS"}

* * *

订阅分钟K线数据

[](./push-quote-java.md#%E8%AE%A2%E9%98%85%E5%88%86%E9%92%9Fk%E7%BA%BF%E6%95%B0%E6%8D%AE)

--------------------------------------------------------------------------------------------------------------------------------

`subscribeKline(Set<String> symbols)`

  
**取消方法**  
`cancelSubscribeKline(Set<String> symbols)`

**说明**

订阅股票的分钟K线数据。 分钟K线订阅推送接口是异步接口，通过实现`ApiComposeCallback`接口可以获得异步请求结果。 回调接口返回结果类型为`KlineData`对象。

支持美国和香港市场股票的订阅

**请求参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| symbols | Set<String> | Yes | 股票代码列表 |

**返回值**

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| id  | string | SDK 订阅请求时本地生成的 ID，序列递增。在`subscribeEnd(int id, String subject, String result)`回调方法中返回请求 ID 和订阅是否成功的结果 |

**订阅示例**

Java

    Set<String> symbols = new HashSet<>();
    //分钟K线股票标的订阅
    symbols.add("AAPL");
    symbols.add("00700");
    
    client.subscribeKline(symbols);
    
    //Wait to receive the data
    TimeUnit.SECONDS.sleep(120000);
    // Cancel subscription
    client.cancelSubscribeKline(symbols);

**对应回调接口**

*   `void klineChange(KlineData data)`

KlineData数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| time | long | 分钟时间戳 |
| open | float | 开始价 |
| high | float | 最高价 |
| low | float | 最低价 |
| close | float | 最终价 |
| avg | float | 平均价 |
| volume | long | 成交量 |
| count | int | 成交笔数 |
| symbol | string | 股票标的 |
| amount | double | 成交金额 |
| serverTimestamp | long | 服务器推送时间戳 |

**回调结果示例**

JSON

    {
      "time": "1712584560000",
      "open": 168.9779,
      "high": 169.0015,
      "low": 168.9752,
      "close": 169.0,
      "avg": 168.778,
      "volume": "3664",
      "count": 114,
      "symbol": "AAPL",
      "amount": 617820.6508,
      "serverTimestamp": "1712584569746"
    }

* * *

订阅股票行情榜单数据

[](./push-quote-java.md#%E8%AE%A2%E9%98%85%E8%82%A1%E7%A5%A8%E8%A1%8C%E6%83%85%E6%A6%9C%E5%8D%95%E6%95%B0%E6%8D%AE)

------------------------------------------------------------------------------------------------------------------------------------------------------------

`subscribeStockTop(Market market, Set<Indicator> indicators)`

  
**取消方法**  
`cancelSubscribeStockTop(Market market, Set<Indicator> indicators)`

**说明**

订阅股票的行情榜单数据，**非交易时间不推送，推送间隔为 30s，每次推送订阅指标 Top30 的标的数据**。 推送接口是异步回调，通过实现`ApiComposeCallback`接口可以获得异步请求结果。 回调接口返回结果类型为`StockTopData`对象。各指标数据按指标值倒排序。

支持美国和香港市场股票指标的订阅，盘中数据榜单有StockRankingIndicator所有指标，美股盘前盘后只有涨幅(changeRate)和5分钟涨幅(changeRate5Min)两个指标的榜单数据

**请求参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| market | Market | Yes | 市场，支持 US，HK |
| indicators | `Set<Indicator>` | No  | 股票榜单指标，默认为全部指标，参考枚举 StockRankingIndicator的值（changeRate: 当天涨幅，changeRate5Min: 5分钟涨幅，turnoverRate: 换手率，amount: 当日成交额，volume: 当日成交量，amplitude: 当日振幅） |

**返回值**

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| id  | string | SDK 订阅请求时本地生成的ID，序列递增。在`subscribeEnd(int id, String subject, String result)`回调方法中返回请求 ID 和订阅是否成功的结果 |

**订阅示例**

Java

    Market market = Market.US;
    Set<Indicator> indicators = new HashSet<>();
    indicators.add(StockRankingIndicator.Amplitude);
    indicators.add(StockRankingIndicator.TurnoverRate);
    //订阅市场所有指标
    client.subscribeStockTop(market, null)
    
    //Wait to receive the data
    TimeUnit.SECONDS.sleep(120000);
    // Cancel subscription of ’amplitude‘ and 'turnoverRate'
    client.cancelSubscribeStockTop(market, indicators);
    // Cancel all indicators's subscription
    client.cancelSubscribeStockTop(market, null);

**对应回调接口**

*   `void stockTopPush(StockTopData data)`

StockTopData数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| market | string | 市场：US/HK |
| timestamp | long | 时间戳 |
| topData | `List<TopData>` | 各指标榜单数据列表 |

TopData的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| targetName | string | 指标名（changeRate, changeRate5Min, turnoverRate, amount, volume, amplitude） |
| item | `List<StockItem>` | 该指标维度下的榜单数据列表 |

StockItem的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 标的  |
| latestPrice | double | 最新价 |
| targetValue | double | 对应指标值 |

**回调结果示例**

JSON

    {
        "market":"US",
        "timestamp":"1687271010482",
        "topData":[\
            {\
                "targetName":"changeRate",\
                "item":[\
                    {\
                        "symbol":"ICAD",\
                        "latestPrice":1.63,\
                        "targetValue":0.393162\
                    },\
                    {\
                        "symbol":"DICE",\
                        "latestPrice":46.54,\
                        "targetValue":0.374889\
                    },\
                    {\
                        "symbol":"VCIG",\
                        "latestPrice":3.88,\
                        "targetValue":0.371025\
                    },\
                    {\
                        "symbol":"LYRA",\
                        "latestPrice":3.75,\
                        "targetValue":0.237624\
                    },\
                    {\
                        "symbol":"CANO",\
                        "latestPrice":1.4847,\
                        "targetValue":0.18776\
                    }\
                    // ......\
                ]\
            },\
            {\
                "targetName":"turnoverRate",\
                "item":[\
                    {\
                        "symbol":"SBBA",\
                        "latestPrice":24.8,\
                        "targetValue":191.046512\
                    },\
                    {\
                        "symbol":"VCIG",\
                        "latestPrice":3.88,\
                        "targetValue":13.82794\
                    },\
                    {\
                        "symbol":"BOIL",\
                        "latestPrice":3.225,\
                        "targetValue":10.681214\
                    },\
                    {\
                        "symbol":"GDV",\
                        "latestPrice":20.86,\
                        "targetValue":8.257162\
                    },\
                    {\
                        "symbol":"NUWE",\
                        "latestPrice":3.1611,\
                        "targetValue":6.755784\
                    }\
                    // ......\
                ]\
            },\
            {\
                "targetName":"amount",\
                "item":[\
                    {\
                        "symbol":"TSLA",\
                        "latestPrice":263.21,\
                        "targetValue":10629393179.8\
                    },\
                    {\
                        "symbol":"SPY",\
                        "latestPrice":435.64,\
                        "targetValue":5839415251.67\
                    },\
                    {\
                        "symbol":"NVDA",\
                        "latestPrice":428.3801,\
                        "targetValue":5123997584.1\
                    },\
                    {\
                        "symbol":"QQQ",\
                        "latestPrice":364.72,\
                        "targetValue":3979912590.29\
                    },\
                    {\
                        "symbol":"BRK.A",\
                        "latestPrice":509004,\
                        "targetValue":2529965164.19\
                    }\
                    // ......\
                ]\
            },\
            {\
                "targetName":"volume",\
                "item":[\
                    {\
                        "symbol":"TSLA",\
                        "latestPrice":263.21,\
                        "targetValue":40190416\
                    },\
                    {\
                        "symbol":"NKLA",\
                        "latestPrice":1.2586,\
                        "targetValue":33326008\
                    },\
                    {\
                        "symbol":"FISV",\
                        "latestPrice":114.23,\
                        "targetValue":31689406\
                    },\
                    {\
                        "symbol":"SQQQ",\
                        "latestPrice":19.93,\
                        "targetValue":31339556\
                    },\
                    {\
                        "symbol":"PLTR",\
                        "latestPrice":15.98,\
                        "targetValue":30249797\
                    }\
                    // ......\
                ]\
            },\
            {\
                "targetName":"amplitude",\
                "item":[\
                    {\
                        "symbol":"ICAD",\
                        "latestPrice":1.63,\
                        "targetValue":0.333333\
                    },\
                    {\
                        "symbol":"VCIG",\
                        "latestPrice":3.88,\
                        "targetValue":0.293286\
                    },\
                    {\
                        "symbol":"GRCL",\
                        "latestPrice":3.8285,\
                        "targetValue":0.281059\
                    },\
                    {\
                        "symbol":"ZJYL",\
                        "latestPrice":10.2165,\
                        "targetValue":0.278427\
                    },\
                    {\
                        "symbol":"NUWE",\
                        "latestPrice":3.1611,\
                        "targetValue":0.262799\
                    }\
                    // ......\
                ]\
            },\
            {\
                "targetName":"changeRate5Min",\
                "item":[\
                    {\
                        "symbol":"ICAD",\
                        "latestPrice":1.63,\
                        "targetValue":0.077419\
                    },\
                    {\
                        "symbol":"EUDA",\
                        "latestPrice":1.3,\
                        "targetValue":0.072\
                    },\
                    {\
                        "symbol":"WEL",\
                        "latestPrice":10.75,\
                        "targetValue":0.070233\
                    },\
                    {\
                        "symbol":"TYGO",\
                        "latestPrice":17.255,\
                        "targetValue":0.068901\
                    },\
                    {\
                        "symbol":"SSU",\
                        "latestPrice":3.2512,\
                        "targetValue":0.065967\
                    }\
                    // ......\
                ]\
            }\
        ]
    }

* * *

订阅期权行情榜单数据

[](./push-quote-java.md#%E8%AE%A2%E9%98%85%E6%9C%9F%E6%9D%83%E8%A1%8C%E6%83%85%E6%A6%9C%E5%8D%95%E6%95%B0%E6%8D%AE)

------------------------------------------------------------------------------------------------------------------------------------------------------------

`subscribeOptionTop(Market market, Set<Indicator> indicators)`

  
**取消方法**  
`cancelSubscribeOptionTop(Market market, Set<Indicator> indicators)`

**说明**

订阅期权的行情榜单数据，**非交易时间不推送，推送间隔为 30 s，每次推送订阅指标 Top50 的标的数据**。 推送接口是异步回调，通过实现`ApiComposeCallback`接口可以获得异步请求结果。 回调接口返回结果类型为`OptionTopData`对象。其中异动大单为单笔成交量大于 1000，按成交时间倒排序的数据，其他指标数据为交易日累计指标值的倒序数据。

支持美国市场期权指标的订阅，盘中数据榜单有OptionRankingIndicator所有指标。

**请求参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| market | Market | Yes | 市场，支持 US |
| indicators | `Set<Indicator>` | No  | 期权榜单指标，默认为全部指标，参考枚举 OptionRankingIndicator 的值（bigOrder: 异动大单，volume: 当日累计成交量，amount: 当日累计成交额，openInt: 未平仓量） |

**返回值**

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| id  | string | SDK 订阅请求时本地生成的 ID，序列递增。在`subscribeEnd(int id, String subject, String result)`回调方法中返回请求 ID 和订阅是否成功的结果 |

**订阅示例**

Java

    Market market = Market.US;
    Set<Indicator> indicators = new HashSet<>();
    indicators.add(OptionRankingIndicator.Amount);
    indicators.add(OptionRankingIndicator.OpenInt);
    //订阅市场所有指标
    client.subscribeOptionTop(market, null)
    
    //Wait to receive the data
    TimeUnit.SECONDS.sleep(120000);
    // Cancel subscription of ’amount‘ and 'openInt'
    client.cancelSubscribeOptionTop(market, indicators);
    // Cancel all indicators's subscription
    client.cancelSubscribeOptionTop(market, null);

**对应回调接口**

*   `void optionTopPush(OptionTopData data)`

OptionTopData数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| market | string | 市场：US |
| timestamp | long | 时间戳 |
| topData | `List<TopData>` | 各指标榜单数据列表 |

TopData的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| targetName | string | 指标名（bigOrder, volume, amount, openInt） |
| bigOrder | `List<BigOrder>` | 异动大单指标(bigOrder)数据列表 |
| item | `List<OptionItem>` | 该指标维度下的榜单数据列表 |

BigOrder的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票、ETF标的 |
| expiry | string | 过期日，格式：yyyyMMdd |
| strike | string | 行权价 |
| right | string | CALL/PUT |
| dir | string | 买卖方向：BUY/SELL/NONE |
| volume | double | 成交量 |
| price | double | 成交价 |
| amount | double | 成交额 |
| tradeTime | long | 成交时间戳 |

OptionItem的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票、ETF标的 |
| expiry | string | 过期日，格式：yyyyMMdd |
| strike | string | 行权价 |
| right | string | CALL/PUT |
| totalAmount | double | 成交额 |
| totalVolume | double | 成交量 |
| totalOpenInt | double | 未平仓量 |
| volumeToOpenInt | double | 成交量/未平仓量 |
| latestPrice | double | 最新价 |
| updateTime | long | 指标数据更新时间戳 |

**回调结果示例**

JSON

    {
        "market":"US",
        "timestamp":"1687277160445",
        "topData":[\
            {\
                "targetName":"volume",\
                "item":[\
                    {\
                        "symbol":"SPY",\
                        "expiry":"20230620",\
                        "strike":"435.0",\
                        "right":"PUT",\
                        "totalAmount":5394115,\
                        "totalVolume":212478,\
                        "totalOpenInt":16377,\
                        "volumeToOpenInt":0.012467,\
                        "latestPrice":0.25,\
                        "updateTime":"1687277254390"\
                    },\
                    {\
                        "symbol":"SPY",\
                        "expiry":"20230620",\
                        "strike":"436.0",\
                        "right":"PUT",\
                        "totalAmount":7754077,\
                        "totalVolume":194423,\
                        "totalOpenInt":13403,\
                        "volumeToOpenInt":0.011408,\
                        "latestPrice":0.58,\
                        "updateTime":"1687277213603"\
                    },\
                    {\
                        "symbol":"SPY",\
                        "expiry":"20230620",\
                        "strike":"437.0",\
                        "right":"PUT",\
                        "totalAmount":10420625,\
                        "totalVolume":182078,\
                        "totalOpenInt":13973,\
                        "volumeToOpenInt":0.010683,\
                        "latestPrice":1.17,\
                        "updateTime":"1687277213602"\
                    },\
                    {\
                        "symbol":"SPY",\
                        "expiry":"20230620",\
                        "strike":"438.0",\
                        "right":"CALL",\
                        "totalAmount":4482482,\
                        "totalVolume":181899,\
                        "totalOpenInt":961,\
                        "volumeToOpenInt":0.010673,\
                        "latestPrice":0.09,\
                        "updateTime":"1687277213603"\
                    },\
                    {\
                        "symbol":"SPY",\
                        "expiry":"20230620",\
                        "strike":"436.0",\
                        "right":"CALL",\
                        "totalAmount":7331667,\
                        "totalVolume":150604,\
                        "totalOpenInt":238,\
                        "volumeToOpenInt":0.008837,\
                        "latestPrice":0.66,\
                        "updateTime":"1687277208599"\
                    }\
                    // ......\
                ]\
            },\
            {\
                "targetName":"amount",\
                "item":[\
                    {\
                        "symbol":"TSLA",\
                        "expiry":"20230721",\
                        "strike":"5.0",\
                        "right":"CALL",\
                        "totalAmount":34061561,\
                        "totalVolume":1812,\
                        "totalOpenInt":18,\
                        "volumeToOpenInt":0.00023,\
                        "latestPrice":259.99,\
                        "updateTime":"1687276953360"\
                    },\
                    {\
                        "symbol":"TSLA",\
                        "expiry":"20230721",\
                        "strike":"500.0",\
                        "right":"PUT",\
                        "totalAmount":30877216,\
                        "totalVolume":1960,\
                        "volumeToOpenInt":0.000248,\
                        "latestPrice":234.97,\
                        "updateTime":"1687276953360"\
                    },\
                    {\
                        "symbol":"TSLA",\
                        "expiry":"20230623",\
                        "strike":"265.0",\
                        "right":"CALL",\
                        "totalAmount":27928028,\
                        "totalVolume":66361,\
                        "totalOpenInt":12928,\
                        "volumeToOpenInt":0.008405,\
                        "latestPrice":6.5,\
                        "updateTime":"1687277264395"\
                    },\
                    {\
                        "symbol":"SPY",\
                        "expiry":"20230721",\
                        "strike":"420.0",\
                        "right":"CALL",\
                        "totalAmount":21629503,\
                        "totalVolume":11105,\
                        "totalOpenInt":46931,\
                        "volumeToOpenInt":0.000652,\
                        "latestPrice":19.27,\
                        "updateTime":"1687273142271"\
                    },\
                    {\
                        "symbol":"TSLA",\
                        "expiry":"20230623",\
                        "strike":"270.0",\
                        "right":"CALL",\
                        "totalAmount":17657903,\
                        "totalVolume":61012,\
                        "totalOpenInt":14302,\
                        "volumeToOpenInt":0.007728,\
                        "latestPrice":4.52,\
                        "updateTime":"1687277254390"\
                    }\
                    // ......\
                ]\
            },\
            {\
                "targetName":"openInt",\
                "item":[\
                    {\
                        "symbol":"AMC",\
                        "expiry":"20230721",\
                        "strike":"10.0",\
                        "right":"CALL",\
                        "totalAmount":4933,\
                        "totalVolume":750,\
                        "totalOpenInt":340843,\
                        "volumeToOpenInt":0.00022,\
                        "latestPrice":0.1,\
                        "updateTime":"1687276788220"\
                    },\
                    {\
                        "symbol":"AMC",\
                        "expiry":"20230721",\
                        "strike":"10.0",\
                        "right":"PUT",\
                        "totalVolume":1,\
                        "totalOpenInt":321814,\
                        "latestPrice":6.2,\
                        "updateTime":"1687276853278"\
                    },\
                    {\
                        "symbol":"AMC",\
                        "expiry":"20230721",\
                        "strike":"4.0",\
                        "right":"PUT",\
                        "totalAmount":117982,\
                        "totalVolume":2748,\
                        "totalOpenInt":242101,\
                        "volumeToOpenInt":0.000806,\
                        "latestPrice":0.81,\
                        "updateTime":"1687277034280"\
                    },\
                    {\
                        "symbol":"ATVI",\
                        "expiry":"20240119",\
                        "strike":"85.0",\
                        "right":"PUT",\
                        "totalAmount":3500,\
                        "totalVolume":26,\
                        "totalOpenInt":230702,\
                        "volumeToOpenInt":0.000016,\
                        "latestPrice":7,\
                        "updateTime":"1687274092822"\
                    },\
                    {\
                        "symbol":"EEM",\
                        "expiry":"20231215",\
                        "strike":"47.0",\
                        "right":"CALL",\
                        "totalAmount":310,\
                        "totalVolume":15,\
                        "totalOpenInt":183054,\
                        "volumeToOpenInt":0.000003,\
                        "latestPrice":0.18,\
                        "updateTime":"1687269619956"\
                    }\
                    // ......\
                ]\
            },\
            {\
                "targetName":"bigOrder",\
                "bigOrder":[\
                    {\
                        "symbol":"AMC",\
                        "expiry":"20230818",\
                        "strike":"10.0",\
                        "right":"PUT",\
                        "dir":"Buy",\
                        "volume":1000,\
                        "price":6.94,\
                        "amount":694000,\
                        "tradeTime":"1687276860753"\
                    },\
                    {\
                        "symbol":"GLPI",\
                        "expiry":"20230818",\
                        "strike":"50.0",\
                        "right":"CALL",\
                        "dir":"Sell",\
                        "volume":1094,\
                        "price":1.2,\
                        "amount":131280,\
                        "tradeTime":"1687276744519"\
                    },\
                    {\
                        "symbol":"AMD",\
                        "expiry":"20230818",\
                        "strike":"140.0",\
                        "right":"CALL",\
                        "dir":"Buy",\
                        "volume":1700,\
                        "price":3.25,\
                        "amount":552500,\
                        "tradeTime":"1687276467421"\
                    },\
                    {\
                        "symbol":"AAPL",\
                        "expiry":"20230915",\
                        "strike":"185.0",\
                        "right":"PUT",\
                        "dir":"Sell",\
                        "volume":1500,\
                        "price":6.65,\
                        "amount":997500,\
                        "tradeTime":"1687276413267"\
                    },\
                    {\
                        "symbol":"BABA",\
                        "expiry":"20240119",\
                        "strike":"75.0",\
                        "right":"PUT",\
                        "dir":"Sell",\
                        "volume":1500,\
                        "price":4.8,\
                        "amount":720000,\
                        "tradeTime":"1687276036749"\
                    }\
                    // ......\
                ]\
            }\
        ]
    }

* * *

查询已订阅的标的

[](./push-quote-java.md#%E6%9F%A5%E8%AF%A2%E5%B7%B2%E8%AE%A2%E9%98%85%E7%9A%84%E6%A0%87%E7%9A%84)

----------------------------------------------------------------------------------------------------------------------------------------

`getSubscribedSymbols()`

**说明**

查询已经订阅过的标的信息

**请求参数**

无

**订阅示例**

Java

    client.getSubscribedSymbols();

**对应回调接口**

Java

    public void getSubscribedSymbolEnd(SubscribedSymbol subscribedSymbol) {
        System.out.println(JSONObject.toJSONString(subscribedSymbol));
    }

回调数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| limit | int | 订阅行情标的（股票、期权、期货）限制最大数 |
| used | int | 已订阅行情标的（股票、期权、期货）数 |
| subscribedSymbols | array | 已订阅行情标的（股票、期权、期货）标的 |
| askBidLimit | int | 订阅深度行情股票限制最大数 |
| askBidUsed | int | 已订阅深度行情股票数 |
| subscribedAskBidSymbols | array | 已订阅深度行情股票标的 |
| tradeTickLimit | int | 订阅逐笔成交股票限制最大数 |
| tradeTickUsed | int | 已订阅逐笔成交股票数 |
| subscribedTradeTickSymbols | array | 已订阅逐笔成交股票标的 |

**回调结果示例**

JSON

    {
        "askBidLimit":10,
        "askBidUsed":0,
        "limit":20,
        "subscribedAskBidSymbols":[\
    \
        ],
        "subscribedSymbols":[\
    \
        ],
        "subscribedTradeTickSymbols":[\
            "PDD",\
            "AMD",\
            "SPY",\
            "01810"\
        ],
        "tradeTickLimit":20,
        "tradeTickUsed":4,
        "used":0
    }

  
