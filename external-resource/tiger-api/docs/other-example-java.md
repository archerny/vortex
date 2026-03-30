# 其他示例

其他示例

[](./other-example-java.md#%E5%85%B6%E4%BB%96%E7%A4%BA%E4%BE%8B)

---------------------------------------------------------------------------------------------------

### 

期权计算工具

[](./other-example-java.md#%E6%9C%9F%E6%9D%83%E8%AE%A1%E7%AE%97%E5%B7%A5%E5%85%B7)

期权计算工具可用于期权希腊值计算、期权预测价格、隐含波动率计算。相关算法基于 `jquantlib` 库。

> ⚠️
> 
> **CAUTION**
> 
> 该工具不支持末日期权的价格预测。

#### 

期权指标计算完整示例

[](./other-example-java.md#%E6%9C%9F%E6%9D%83%E6%8C%87%E6%A0%87%E8%AE%A1%E7%AE%97%E5%AE%8C%E6%95%B4%E7%A4%BA%E4%BE%8B)

Java

    package com.tigerbrokers.stock.openapi.demo.quote;
    
    import com.alibaba.fastjson.JSONObject;
    import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
    import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionBriefItem;
    import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
    import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.RealTimeQuoteItem;
    import com.tigerbrokers.stock.openapi.client.https.request.option.OptionBriefQueryRequest;
    import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteRealTimeQuoteRequest;
    import com.tigerbrokers.stock.openapi.client.https.response.option.OptionBriefResponse;
    import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse;
    import com.tigerbrokers.stock.openapi.client.struct.OptionFundamentals;
    import com.tigerbrokers.stock.openapi.client.struct.enums.Right;
    import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
    import com.tigerbrokers.stock.openapi.client.util.DateUtils;
    import com.tigerbrokers.stock.openapi.client.util.OptionCalcUtils;
    import com.tigerbrokers.stock.openapi.demo.TigerOpenClientConfig;
    
    import java.time.LocalDate;
    import java.util.Arrays;
    import java.util.List;
    
    public class OptionCalcTool {
        public static TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
                TigerOpenClientConfig.getDefaultClientConfig());
    
        public static void main(String[] args) {
            String symbol = "AAPL"; //期权对应的底层标的
            Right optionRight = Right.PUT; //期权方向
            String strike = "185.0"; //期权的行权价格
            String settlementDate = "2024-02-05"; //一般是当前日期
            String expiryDate = "2024-02-09";
            long expiryTimestamp = DateUtils.parseEpochMill(expiryDate, TimeZoneId.NewYork);
    
            //查询期权的盘口价格，以及美国国债利率等信息
            OptionCommonModel model = new OptionCommonModel(symbol, optionRight.name(), strike, expiryTimestamp);
            OptionBriefResponse optionBriefResponse = client.execute(OptionBriefQueryRequest.of(model));
            OptionBriefItem optionBrief = null;
            if (optionBriefResponse != null && optionBriefResponse.isSuccess()) {
                List<OptionBriefItem> briefItems = optionBriefResponse.getOptionBriefItems();
                if (briefItems != null && !briefItems.isEmpty()) {
                    optionBrief = briefItems.get(0);
                } else {
                    throw new RuntimeException("Failed to get the option brief of symbol: " + symbol);
                }
            }
    
            //查询期权对应标的的最新价格
            QuoteRealTimeQuoteResponse quoteRealTimeQuoteResponse =
                    client.execute(QuoteRealTimeQuoteRequest.newRequest(Arrays.asList(symbol)));
            Double latestPrice = 0D;
            if (quoteRealTimeQuoteResponse != null && quoteRealTimeQuoteResponse.isSuccess()) {
                List<RealTimeQuoteItem> realTimeQuoteItems = quoteRealTimeQuoteResponse.getRealTimeQuoteItems();
                if (realTimeQuoteItems != null && !realTimeQuoteItems.isEmpty()) {
                    latestPrice = realTimeQuoteItems.get(0).getLatestPrice();
                    if (latestPrice == null) {
                        throw new RuntimeException("Failed to get the latest price of the stock.");
                    }
                }
            }
    
            // 注意：如果结算日期和期权过期日相同，需要把结算日期改成前一天再进行计算
            // 例如结算日期和期权过期日都是2024-02-09，需要把结算日期改成2024-02-08
            if (settlementDate.equals(expiryDate)) {
                settlementDate = LocalDate.parse(settlementDate).minusDays(1).toString();
            }
    
            OptionFundamentals optionFundamentals = OptionCalcUtils.calcOptionIndex(optionRight, latestPrice, Double.valueOf(strike),
                    optionBrief.getRatesBonds(), 0, optionBrief.getAskPrice(),optionBrief.getBidPrice(),
                    LocalDate.parse(settlementDate) , LocalDate.parse(expiryDate));
    
            // 如果是指数期权（欧式期权），应使用 calcEuropeanOptionIndex 方法：
            // OptionFundamentals optionFundamentals = OptionCalcUtils.calcEuropeanOptionIndex(optionRight, latestPrice, Double.valueOf(strike),
            //         optionBrief.getRatesBonds(), 0, optionBrief.getAskPrice(), optionBrief.getBidPrice(),
            //         LocalDate.parse(settlementDate), LocalDate.parse(expiryDate));
    
            System.out.println(JSONObject.toJSONString(optionFundamentals));
        }
    }

示例输出结果：

JSON

    {
    	"delta": -0.4291523762730371,
    	"gamma": 0.06867092999396703,
    	"historyVolatility": 0.0,
    	"insideValue": 0.0,
    	"leverage": 0.0,
    	"openInterest": 0.0,
    	"predictedValue": 1.8448482568095044,
    	"premiumRate": 0.0,
    	"profitRate": 0.0,
    	"rho": -0.007994670535451135,
    	"theta": -0.27407985875145857,
    	"timeValue": 0.0,
    	"vega": 0.07649602025704422,
    	"volatility": 0.0
    }

#### 

其他使用方式

[](./other-example-java.md#%E5%85%B6%E4%BB%96%E4%BD%BF%E7%94%A8%E6%96%B9%E5%BC%8F)

`FDAmericanDividendOptionHelper` 为美式期权计算类

Java

    import com.tigerbrokers.stock.openapi.client.struct.OptionFundamentals;
    import com.tigerbrokers.stock.openapi.client.struct.enums.Right;
    import com.tigerbrokers.stock.openapi.client.util.OptionCalcUtils;
    import java.time.LocalDate;
    
    public class OptionTool {
      public static void main(String[] args) {
        OptionFundamentals optionIndex = OptionCalcUtils.calcOptionIndex(
                Right.CALL,
                243.35, //对应标的资产的价格
                240,  //期权行权价格
                0.0241,  //无风险利率，这里是取的美国国债利率
                0,  //股息率，大部分标的为0
                0.4648, // 隐含波动率
                LocalDate.of(2022, 8, 12), //对应预测价格的日期，要小于期权到期日
                LocalDate.of(2022, 8, 19));  //期权到期日
        System.out.println("value: " + optionIndex.getPredictedValue()); //计算出的期权预测价格
        System.out.println("delta: " + optionIndex.getDelta());
        System.out.println("gamma " + optionIndex.getGamma());
        System.out.println("theta " + optionIndex.getTheta());
        System.out.println("vega: " + optionIndex.getVega());
        System.out.println("rho: " + optionIndex.getRho());
      }
    }

示例输出结果：

Text

    value: 8.09628869758489
    delta: 0.6005809882595446
    gamma 0.024620648675961896
    theta -0.4429512650168838
    vega: 0.13035018339603335
    rho: 0.026470369092588868

除本使用文档中提供的例子之外，我们还提供了涉及到 Open API 其他功能的使用示例以供用户参考。这部分的代码我们已经更新到了Github代码仓库中：

[https://github.com/tigerfintech/openapi-java-sdk-demo](https://github.com/tigerfintech/openapi-java-sdk-demo)

我们还会不断添加更多示例，并更新在使用文档和 Github 仓库中，请持续关注。
