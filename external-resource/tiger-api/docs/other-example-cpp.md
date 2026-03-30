# 其他示例

其他示例

[](./other-example-cpp.md#%E5%85%B6%E4%BB%96%E7%A4%BA%E4%BE%8B)

--------------------------------------------------------------------------------------------------

### 

期权计算工具

[](./other-example-cpp.md#%E6%9C%9F%E6%9D%83%E8%AE%A1%E7%AE%97%E5%B7%A5%E5%85%B7)

C++ SDK 目前不内置期权定价计算库，但可以通过 SDK 提供的行情接口获取期权的盘口价格、隐含波动率、希腊值等信息，再结合第三方数学库（如 [QuantLib](https://www.quantlib.org/)
）进行本地期权定价计算。

以下示例演示了如何通过 C++ SDK 获取期权行情数据，并利用返回的希腊值和隐含波动率进行分析。

> ⚠️
> 
> **注意**
> 
> 如需本地计算期权定价和希腊值，推荐使用 QuantLib C++ 库。以下示例中的本地计算部分需要自行安装 QuantLib。

#### 

通过 SDK 获取期权行情与希腊值

[](./other-example-cpp.md#%E9%80%9A%E8%BF%87-sdk-%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E8%A1%8C%E6%83%85%E4%B8%8E%E5%B8%8C%E8%85%8A%E5%80%BC)

使用 `get_option_brief` 获取期权的实时行情快照，返回数据中包含隐含波动率和希腊值等指标。

C++

    #include <iostream>
    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    using namespace web::json;
    
    int main() {
        // 初始化配置
        ClientConfig config(false, U("/path/to/your/properties/"));
    
        // 初始化行情客户端
        QuoteClient quote_client(config);
    
        // 获取期权行情快照（包含希腊值等指标）
        // 期权标识符格式: "标的代码  到期日方向行权价" (OCC格式)
        value result = quote_client.get_option_brief(U("AAPL  240621C00190000"));
        ucout << result.serialize() << std::endl;
    
        return 0;
    }

**返回示例**

JSON

    {
      "askPrice": 35.5,
      "askSize": 10,
      "bidPrice": 35.0,
      "bidSize": 15,
      "latestPrice": 35.25,
      "volume": 1234,
      "openInterest": 5678,
      "impliedVol": 0.4648,
      "delta": 0.6006,
      "gamma": 0.0246,
      "theta": -0.4430,
      "vega": 0.1304,
      "rho": 0.0265
    }

#### 

获取期权链数据

[](./other-example-cpp.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E9%93%BE%E6%95%B0%E6%8D%AE)

通过 `get_option_chain` 获取指定标的和到期日的完整期权链，可配合筛选条件过滤。

C++

    #include <iostream>
    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    using namespace web::json;
    
    int main() {
        ClientConfig config(false, U("/path/to/your/properties/"));
        QuoteClient quote_client(config);
    
        // 获取期权到期日列表
        value symbols = value::array();
        symbols[0] = value::string(U("AAPL"));
        value expirations = quote_client.get_option_expiration(symbols);
        ucout << U("Expirations: ") << expirations.serialize() << std::endl;
    
        // 获取指定到期日的期权链
        value chain = quote_client.get_option_chain(U("AAPL"), U("2024-06-21"));
        ucout << U("Option chain: ") << chain.serialize() << std::endl;
    
        // 带筛选条件获取期权链（仅价内期权）
        value filter = value::object();
        filter[U("in_the_money")] = value::boolean(true);
        value filtered_chain = quote_client.get_option_chain(U("AAPL"), U("2024-06-21"), filter);
        ucout << U("In-the-money options: ") << filtered_chain.serialize() << std::endl;
    
        return 0;
    }

#### 

结合 QuantLib 进行本地期权定价计算

[](./other-example-cpp.md#%E7%BB%93%E5%90%88-quantlib-%E8%BF%9B%E8%A1%8C%E6%9C%AC%E5%9C%B0%E6%9C%9F%E6%9D%83%E5%AE%9A%E4%BB%B7%E8%AE%A1%E7%AE%97)

如果需要在本地进行期权定价和希腊值计算，可以结合 [QuantLib](https://www.quantlib.org/)
 C++ 库。以下示例演示了使用 QuantLib 计算美式期权的隐含波动率和希腊值。

> 📘
> 
> 使用前需要先安装 QuantLib C++ 库。可通过 vcpkg 安装：`vcpkg install quantlib`，或通过 brew 安装：`brew install quantlib`

C++

    #include <iostream>
    #include <ql/quantlib.hpp>
    
    using namespace QuantLib;
    
    int main() {
        // 设置评估日期
        Date evaluationDate(19, April, 2022);
        Settings::instance().evaluationDate() = evaluationDate;
    
        // 期权参数
        Option::Type optionType = Option::Call;
        Real underlying = 985.0;       // 标的资产价格
        Real strike = 990.0;           // 行权价
        Rate riskFreeRate = 0.017;     // 无风险利率
        Rate dividendRate = 0.0;       // 股息率
        Date settlementDate(14, April, 2022);   // 结算日期
        Date expirationDate(22, April, 2022);   // 期权到期日
    
        // 构建行权方式和标的资产
        auto exercise = ext::make_shared<AmericanExercise>(settlementDate, expirationDate);
        auto payoff = ext::make_shared<PlainVanillaPayoff>(optionType, strike);
        VanillaOption option(payoff, exercise);
    
        // 市场数据句柄
        auto spotHandle = ext::make_shared<SimpleQuote>(underlying);
        auto volHandle = ext::make_shared<SimpleQuote>(0.0); // 临时设为0
        auto rateHandle = ext::make_shared<SimpleQuote>(riskFreeRate);
        auto divHandle = ext::make_shared<SimpleQuote>(dividendRate);
    
        DayCounter dayCounter = Actual365Fixed();
        Calendar calendar = UnitedStates(UnitedStates::NYSE);
    
        auto flatVol = ext::make_shared<BlackConstantVol>(
            evaluationDate, calendar,
            Handle<Quote>(volHandle), dayCounter);
        auto flatRate = ext::make_shared<FlatForward>(
            evaluationDate, Handle<Quote>(rateHandle), dayCounter);
        auto flatDiv = ext::make_shared<FlatForward>(
            evaluationDate, Handle<Quote>(divHandle), dayCounter);
    
        auto bsmProcess = ext::make_shared<BlackScholesMertonProcess>(
            Handle<Quote>(spotHandle),
            Handle<YieldTermStructure>(flatDiv),
            Handle<YieldTermStructure>(flatRate),
            Handle<BlackVolTermStructure>(flatVol));
    
        // 使用有限差分法定价引擎
        option.setPricingEngine(
            ext::make_shared<FdBlackScholesVanillaEngine>(bsmProcess, 100, 100));
    
        // 根据期权价格计算隐含波动率
        Real optionPrice = 33.6148; // 期权市场价格，可用 (ask + bid) / 2
        Volatility impliedVol = option.impliedVolatility(optionPrice, bsmProcess);
        std::cout << "implied volatility: " << impliedVol << std::endl;
    
        // 更新波动率后重新计算希腊值
        volHandle->setValue(impliedVol);
    
        std::cout << "value: " << option.NPV() << std::endl;
        std::cout << "delta: " << option.delta() << std::endl;
        std::cout << "gamma: " << option.gamma() << std::endl;
        std::cout << "theta: " << option.theta() << std::endl;
        std::cout << "vega: " << option.vega() << std::endl;
        std::cout << "rho: " << option.rho() << std::endl;
    
        std::cout << std::endl;
    
        // 直接使用隐含波动率计算期权价格
        Real knownVolatility = 0.6153;
        volHandle->setValue(knownVolatility);
    
        std::cout << "---- 使用已知隐含波动率计算 ----" << std::endl;
        std::cout << "value: " << option.NPV() << std::endl;
        std::cout << "delta: " << option.delta() << std::endl;
        std::cout << "gamma: " << option.gamma() << std::endl;
        std::cout << "theta: " << option.theta() << std::endl;
        std::cout << "vega: " << option.vega() << std::endl;
        std::cout << "rho: " << option.rho() << std::endl;
    
        return 0;
    }

#### 

完整示例：SDK 行情 + QuantLib 计算

[](./other-example-cpp.md#%E5%AE%8C%E6%95%B4%E7%A4%BA%E4%BE%8Bsdk-%E8%A1%8C%E6%83%85--quantlib-%E8%AE%A1%E7%AE%97)

以下示例将 Tiger Open API 行情查询与 QuantLib 本地计算相结合，实现完整的期权分析流程。

C++

    #include <iostream>
    #include <string>
    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    #include <ql/quantlib.hpp>
    
    using namespace TIGER_API;
    using namespace web::json;
    using namespace QuantLib;
    
    int main() {
        // 1. 通过 SDK 获取期权行情数据
        ClientConfig config(false, U("/path/to/your/properties/"));
        QuoteClient quote_client(config);
    
        // 获取期权实时行情
        value brief = quote_client.get_option_brief(U("AAPL  240209P00185000"));
        ucout << U("Option brief: ") << brief.serialize() << std::endl;
    
        // 获取标的股票最新价格
        value symbols = value::array();
        symbols[0] = value::string(U("AAPL"));
        value stock_brief = quote_client.get_brief(symbols);
        ucout << U("Stock brief: ") << stock_brief.serialize() << std::endl;
    
        // 2. 从返回数据中提取关键参数
        // 注意：实际代码中需要根据返回的 JSON 结构解析字段
        double askPrice = 2.50;   // 从 brief 中获取
        double bidPrice = 2.30;   // 从 brief 中获取
        double latestPrice = 185.0; // 从 stock_brief 中获取
    
        // 3. 使用 QuantLib 进行本地计算
        Date evaluationDate(5, February, 2024);
        Settings::instance().evaluationDate() = evaluationDate;
    
        Option::Type optionType = Option::Put;
        Real underlying = latestPrice;
        Real strike = 185.0;
        Rate riskFreeRate = 0.0241;
        Rate dividendRate = 0.0;
        Date settlementDate(5, February, 2024);
        Date expirationDate(9, February, 2024);
    
        auto exercise = ext::make_shared<AmericanExercise>(settlementDate, expirationDate);
        auto payoff = ext::make_shared<PlainVanillaPayoff>(optionType, strike);
        VanillaOption option(payoff, exercise);
    
        auto spotHandle = ext::make_shared<SimpleQuote>(underlying);
        auto volHandle = ext::make_shared<SimpleQuote>(0.0);
        auto rateHandle = ext::make_shared<SimpleQuote>(riskFreeRate);
        auto divHandle = ext::make_shared<SimpleQuote>(dividendRate);
    
        DayCounter dayCounter = Actual365Fixed();
        Calendar calendar = UnitedStates(UnitedStates::NYSE);
    
        auto flatVol = ext::make_shared<BlackConstantVol>(
            evaluationDate, calendar,
            Handle<Quote>(volHandle), dayCounter);
        auto flatRate = ext::make_shared<FlatForward>(
            evaluationDate, Handle<Quote>(rateHandle), dayCounter);
        auto flatDiv = ext::make_shared<FlatForward>(
            evaluationDate, Handle<Quote>(divHandle), dayCounter);
    
        auto bsmProcess = ext::make_shared<BlackScholesMertonProcess>(
            Handle<Quote>(spotHandle),
            Handle<YieldTermStructure>(flatDiv),
            Handle<YieldTermStructure>(flatRate),
            Handle<BlackVolTermStructure>(flatVol));
    
        option.setPricingEngine(
            ext::make_shared<FdBlackScholesVanillaEngine>(bsmProcess, 100, 100));
    
        // 计算隐含波动率 (使用 ask 和 bid 的均值)
        Real optionPrice = (askPrice + bidPrice) / 2.0;
        Volatility impliedVol = option.impliedVolatility(optionPrice, bsmProcess);
        volHandle->setValue(impliedVol);
    
        std::cout << "---- 期权分析结果 ----" << std::endl;
        std::cout << "implied volatility: " << impliedVol << std::endl;
        std::cout << "value: " << option.NPV() << std::endl;
        std::cout << "delta: " << option.delta() << std::endl;
        std::cout << "gamma: " << option.gamma() << std::endl;
        std::cout << "theta: " << option.theta() << std::endl;
        std::cout << "vega: " << option.vega() << std::endl;
        std::cout << "rho: " << option.rho() << std::endl;
    
        return 0;
    }

**示例输出结果**

JSON

    {
    	"delta": -0.4291523762730371,
    	"gamma": 0.06867092999396703,
    	"predictedValue": 1.8448482568095044,
    	"rho": -0.007994670535451135,
    	"theta": -0.27407985875145857,
    	"vega": 0.07649602025704422,
    	"volatility": 0.5919
    }

  

除本使用文档中提供的例子之外，C++ SDK的更多示例正在不断更新中，如有新的例子，我们会及时同步在Github代码仓库。

[https://github.com/tigerfintech/openapi-cpp-sdk](https://github.com/tigerfintech/openapi-cpp-sdk)

我们还会不断添加更多示例，并更新在使用文档和Github仓库中，请持续关注。如果您对SDK的使用有任何疑问，请直接[联系我们](./contact.md#/)
