# 基本功能示例

老虎Open API C++ SDK提供了丰富的接口来调用老虎的服务，本章节将对老虎API的核心功能进行一一演示：包括查询行情，订阅行情，以及调用API进行交易

查询行情

[](./basic-cpp.md#%E6%9F%A5%E8%AF%A2%E8%A1%8C%E6%83%85)

------------------------------------------------------------------------------------------

以下为一个最简单的调用老虎API的示例，演示了如何调用Open API来主动查询股票行情。

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    using namespace web::json;
    
    int main() {
        // 初始化配置
        ClientConfig config(false, U("/path/to/your/properties/"));
    
        // 初始化行情客户端
        QuoteClient quote_client(config);
    
        // 查询股票行情快照
        value symbols = value::array();
        symbols[0] = value::string(U("00700"));
        value result = quote_client.get_brief(symbols);
    
        // 输出结果
        ucout << result.serialize() << std::endl;
    
        return 0;
    }

**返回示例**

JSON

    {
      "symbol": "00700",
      "askPrice": 326.4,
      "askSize": 15300,
      "bidPrice": 326.2,
      "bidSize": 26100,
      "preClose": 321.80,
      "latestPrice": 326.4,
      "volume": 2593802,
      "open": 325.00,
      "high": 326.80,
      "low": 323.20,
      "status": "NORMAL"
    }

订阅行情

[](./basic-cpp.md#%E8%AE%A2%E9%98%85%E8%A1%8C%E6%83%85)

------------------------------------------------------------------------------------------

除了选择主动查询的方式，Open API还支持订阅-接受推送的方式来接收行情等信息。此示例实现了订阅苹果与AMD股票行情，将行情快照输出在控制台，持续30秒后取消订阅，并且断开与服务器的连接。

C++

    #include <iostream>
    #include <thread>
    #include <chrono>
    #include "tigerapi/push_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    int main() {
        // 初始化配置
        ClientConfig config(false, U("/path/to/your/properties/"));
    
        // 创建推送客户端
        auto push_client = IPushClient::create_push_client(config);
    
        // 设置连接回调
        push_client->set_connected_callback([]() {
            std::cout << "Connected" << std::endl;
        });
    
        // 设置断开回调
        push_client->set_disconnected_callback([]() {
            std::cout << "Disconnected" << std::endl;
        });
    
        // 设置行情变动回调
        push_client->set_quote_changed_callback([](const tigeropen::push::pb::QuoteBasicData& data) {
            std::cout << "Symbol: " << data.symbol()
                      << " Price: " << data.latestprice()
                      << " Volume: " << data.volume()
                      << std::endl;
        });
    
        // 建立连接
        push_client->connect();
    
        // 订阅行情
        std::vector<std::string> symbols = {"AAPL", "AMD"};
        push_client->subscribe_quote(symbols);
    
        // 等待推送
        std::this_thread::sleep_for(std::chrono::seconds(30));
    
        // 取消订阅
        push_client->unsubscribe_quote(symbols);
    
        // 断开连接
        push_client->disconnect();
    
        return 0;
    }

交易下单

[](./basic-cpp.md#%E4%BA%A4%E6%98%93%E4%B8%8B%E5%8D%95)

------------------------------------------------------------------------------------------

交易是Open API的另一个主要功能。此例展示了如何使用Open API对美股老虎证券TIGR下限价单:

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    #include "tigerapi/contract_util.h"
    #include "tigerapi/order_util.h"
    
    using namespace TIGER_API;
    
    int main() {
        // 初始化配置
        ClientConfig config(false, U("/path/to/your/properties/"));
    
        // 初始化交易客户端
        TradeClient trade_client(config);
    
        // 方法1: 本地构造合约对象
        Contract contract = ContractUtil::stock_contract(U("TIGR"), U("USD"));
    
        // 方法2: 通过接口查询获取合约信息
        // value contract_json = trade_client.get_contract(U("TIGR"), U("STK"));
    
        // 创建限价单
        Order order = OrderUtil::limit_order(
            config.account,  // 下单账户
            contract,        // 合约对象
            U("BUY"),        // 买入方向
            100,             // 数量
            5.0              // 限价
        );
    
        // 提交订单
        value result = trade_client.place_order(order);
        ucout << result.serialize() << std::endl;
    
        // 下单成功后，order.id 会被填充为实际的订单号
        std::cout << "Order ID: " << order.id << std::endl;
    
        return 0;
    }

**返回示例**

JSON

    {
      "id": 14275856193552384,
      "orderId": 0,
      "account": "164644",
      "symbol": "TIGR",
      "secType": "STK",
      "action": "BUY",
      "orderType": "LMT",
      "totalQuantity": 100,
      "limitPrice": 5.0,
      "status": "NEW"
    }

**补充说明**

对于支持的其他类型的订单，请参考文档交易类-下单交易部分中的说明
