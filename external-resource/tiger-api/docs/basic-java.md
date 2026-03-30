# 基本功能示例

老虎 Open API SDK 提供了丰富的接口来调用老虎的服务，本章节将对老虎 API 的核心功能进行一一演示：包括查询行情，交易下单，订阅行情。

查询行情

[](./basic-java.md#%E6%9F%A5%E8%AF%A2%E8%A1%8C%E6%83%85)

-------------------------------------------------------------------------------------------

以下为一个最简单的调用老虎API的示例，演示了如何调用 Open API 来主动查询股票行情。接下来的例子分别演示了如何调用 Open API 来进行交易与订阅行情。除上述基础功能外，Open API 还支持查询、交易多个市场的不同标的，以及其他复杂请求。对于其他 Open API 支持的接口和请求，请在快速入门后阅读文档正文获取列表及使用方法，并参考快速入门以及文档中的例子进行调用。

_为方便直接复制运行，以下的说明采用注释的形式_

Java

    import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
    import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
    import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteKlineRequest;
    import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse;
    import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
    import com.tigerbrokers.stock.openapi.client.struct.enums.RightOption;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    
    public class QuoteDemo {
    
      private static final TigerHttpClient client;
    
      static {
        ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
        // 开发者平台导出的配置文件 tiger_openapi_config.properties 的存放路径，TBHK 牌照的 tiger_openapi_token.properties 文件也在该目录下
        clientConfig.configFilePath = "./src/main/resources";
        // clientConfig.secretKey = "xxxxxx"; // 机构账号交易员必填字段 secret key
        client = TigerHttpClient.getInstance().clientConfig(clientConfig);
      }
    
      public static void main(String[] args) {
        new QuoteDemo().kline();
      }
    
      public void kline() {
        List<String> symbols = new ArrayList<>();
        symbols.add("AAPL");
        QuoteKlineRequest request =
            QuoteKlineRequest.newRequest(symbols, KType.day, "2022-10-01", "2022-12-25")
                .withLimit(1000)
                .withRight(RightOption.br);
        QuoteKlineResponse response = client.execute(request);
        if (response.isSuccess()) {
          System.out.println(Arrays.toString(response.getKlineItems().toArray()));
        } else {
          System.out.println("response error:" + response.getMessage());
        }
      }
    }

下单

[](./basic-java.md#%E4%B8%8B%E5%8D%95)

-----------------------------------------------------------------------

交易是 Open API 的另一个主要功能。此例展示了如何使用 Open API 对美股 AAPL 下限价单:

Java

    import com.alibaba.fastjson.JSONObject;
    import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
    import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
    import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
    import com.tigerbrokers.stock.openapi.client.https.request.trade.TradeOrderRequest;
    import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
    import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
    
    public class TradeDemo {
    
      private static final TigerHttpClient client;
    
      static {
        ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
        // 开发者平台导出的配置文件 tiger_openapi_config.properties 的存放路径，TBHK 牌照的 tiger_openapi_token.properties 文件也在该目录下
        clientConfig.configFilePath = "./src/main/resources";
        // clientConfig.secretKey = "xxxxxx"; // 机构账号交易员必填字段 secret key
        client = TigerHttpClient.getInstance().clientConfig(clientConfig);
      }
    
      public static void main(String[] args) {
        new TradeDemo().placeUsStockOrder();
      }
    
      public void placeUsStockOrder() {
        ContractItem contract = ContractItem.buildStockContract("AAPL", "USD");
        TradeOrderRequest request =
            TradeOrderRequest.buildLimitOrder(contract, ActionType.BUY, 1, 100.0);
        TradeOrderResponse response = client.execute(request);
        System.out.println(JSONObject.toJSONString(response));
      }
    }

订阅

[](./basic-java.md#%E8%AE%A2%E9%98%85)

-----------------------------------------------------------------------

除主动查询外，Open API 还支持以**​​订阅-推送**​​模式获取行情和交易数据。使用方式如下例所示。

*   **异步处理机制​​**：所有订阅请求均采用异步方式处理。
*   **回调函数​​**：您需自定义回调函数，并将其与指定事件绑定。
*   **推送触发**​​：当事件触发或服务器推送最新信息时，系统将自动调用您的回调函数，并传入返回数据。
*   **数据处理**​​：请在自定义回调函数中实现所需的数据处理逻辑。

1.  定义回调接口

Java

    import com.alibaba.fastjson.JSONObject;
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
      public void quoteChange(QuoteBasicData data) {
        ApiLogger.info("quoteChange:" + ProtoMessageUtil.toJson(data));
      }
      @Override
      public void quoteAskBidChange(QuoteBBOData data) {
        ApiLogger.info("quoteAskBidChange:" + ProtoMessageUtil.toJson(data));
      }
    
      @Override
      public void tradeTickChange(TradeTick data) {
        ApiLogger.info("tradeTickChange:" + JSONObject.toJSONString(data));
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
    
      @Override
      public void optionChange(QuoteBasicData data) {
        ApiLogger.info("optionChange:" + ProtoMessageUtil.toJson(data));
      }
      @Override
      public void optionAskBidChange(QuoteBBOData data) {
        ApiLogger.info("optionAskBidChange:" + ProtoMessageUtil.toJson(data));
      }
    
      @Override
      public void futureChange(QuoteBasicData data) {
        ApiLogger.info("futureChange:" + ProtoMessageUtil.toJson(data));
      }
      @Override
      public void futureAskBidChange(QuoteBBOData data) {
        ApiLogger.info("futureAskBidChange:" + ProtoMessageUtil.toJson(data));
      }
    
      @Override
      public void depthQuoteChange(QuoteDepthData data) {
        ApiLogger.info("depthQuoteChange:" + ProtoMessageUtil.toJson(data));
      }
    
      @Override
      public void stockTopPush(StockTopData data) {
        ApiLogger.info("stockTopPush, market:" + data.getMarket());
        for (StockTopData.TopData topData : data.getTopDataList()) {
          ApiLogger.info("stockTopPush, targetName:" + topData.getTargetName()
              + ", topList:" + ProtoMessageUtil.toJson(topData));
        }
      }
    
      @Override
      public void optionTopPush(OptionTopData data) {
        ApiLogger.info("optionTopPush, market:" + data.getMarket());
        for (OptionTopData.TopData topData : data.getTopDataList()) {
          ApiLogger.info("optionTopPush, targetName:" + topData.getTargetName()
              + ", topList:" + ProtoMessageUtil.toJson(topData));
        }
      }
    
      @Override
      public void subscribeEnd(int id, String subject, String result) {
        ApiLogger.info("subscribe " + subject + " end. id:" + id + ", " + result);
      }
    
      @Override
      public void cancelSubscribeEnd(int id, String subject, String result) {
        ApiLogger.info("cancel subscribe " + subject + " end. id:" + id + ", " + result);
      }
    
      @Override
      public void getSubscribedSymbolEnd(SubscribedSymbol subscribedSymbol) {
        ApiLogger.info("getSubscribedSymbolEnd:" + JSONObject.toJSONString(subscribedSymbol));
      }
    
      @Override
      public void error(String errorMsg) {
        ApiLogger.info("receive error:" + errorMsg);
      }
    
      @Override
      public void error(int id, int errorCode, String errorMsg) {
        ApiLogger.info("receive error id:" + id + ",errorCode:" + errorCode + ",errorMsg:" + errorMsg);
      }
    
      @Override
      public void connectionClosed() {
        ApiLogger.info("connection closed.");
      }
    
      @Override
      public void connectionKickout(int errorCode, String errorMsg) {
        ApiLogger.info(errorMsg + " and the connection is closed.");
      }
    
      @Override
      public void connectionAck() {
        ApiLogger.info("connect success.");
      }
    
      @Override
      public void connectionAck(int serverSendInterval, int serverReceiveInterval) {
        ApiLogger.info(
            "connect success,send interval:" + serverSendInterval + ",receive interval:" + serverReceiveInterval);
      }
    
      @Override
      public void hearBeat(String heartBeatContent) {
        ApiLogger.info(heartBeatContent);
      }
    
      @Override
      public void serverHeartBeatTimeOut(String channelId) {
        ApiLogger.info("serverHeartBeatTimeOut，channelId=" + channelId);
      }
    }

2.  进行订阅

Java

    
    import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
    import com.tigerbrokers.stock.openapi.client.socket.WebSocketClient;
    import com.tigerbrokers.stock.openapi.client.struct.enums.License;
    
    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;
    
    public class SubscribeDemo {
    
        //实际订阅时需要读取tiger_openapi_token.properties文件来填充tigerId和privateKey，并实现ApiComposeCallback接口，本示例中为DefaultApiComposeCallback，实现参考以上代码，仅将返回数据简单输出。实际可根据需要调整@Override下方的回调函数
        private static ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
        private static WebSocketClient client;
        static {
            //从开发者信息页面导出的配置文件tiger_openapi_config.properties、tiger_openapi_token.properties存放路径
            clientConfig.configFilePath = "/data/tiger_config";
            // clientConfig.secretKey = "xxxxxx";// 机构账号交易员必填
            client = WebSocketClient.getInstance().clientConfig(clientConfig).apiComposeCallback(new DefaultApiComposeCallback());
        }
    
        public static void main(String[] args) {
            WebSocketDemo webSocketDemo = new WebSocketDemo();
            webSocketDemo.subscribe();
        }
    
        public void subscribe() {
    
            client.connect();
    
            Set<String> symbols = new HashSet<>();
    
            //股票订阅
            symbols.add("AAPL");
            symbols.add("SPY");
            client.subscribeQuote(symbols);
    
            //期货订阅
            symbols.add("ESmain");
            symbols.add("ES1906");
            client.subscribeFuture(symbols);
    
            //期权的一种形式
            symbols.add("TSLA 20190614 200.0 CALL");
            //期权另外一种形式（21位）
            symbols.add("SPY   190508C00290000");
            client.subscribeOption(symbols);
    
            //订阅深度数据
            client.subscribeDepthQuote(symbols);
    
            //订阅逐笔数据
            client.subscribeTradeTick(symbols);
    
            //订阅期权榜单
            Market market = Market.US;
            Set<Indicator> indicators = new HashSet<>();
            //期权当日大单指标
            indicators.add(OptionRankingIndicator.BigOrder);
            //期权当日累计成交额指标
            indicators.add(OptionRankingIndicator.Amount);
            client.subscribeOptionTop(market, indicators);
    
            //查询订阅详情
            client.getSubscribedSymbols();
    
            //建议交易时间过后断开连接，断开连接时会自动注销之前的订阅记录
            //client.disconnect();
        }
    }

  
