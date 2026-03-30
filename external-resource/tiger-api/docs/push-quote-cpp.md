# 行情推送

行情订阅推送

[](./push-quote-cpp.md#%E8%A1%8C%E6%83%85%E8%AE%A2%E9%98%85%E6%8E%A8%E9%80%81)

-------------------------------------------------------------------------------------------------------------------

C++ SDK 通过 `IPushClient` 实现行情的订阅推送功能。推送使用 Protobuf 协议进行数据传输，回调函数异步触发。

初始化推送客户端

[](./push-quote-cpp.md#%E5%88%9D%E5%A7%8B%E5%8C%96%E6%8E%A8%E9%80%81%E5%AE%A2%E6%88%B7%E7%AB%AF)

---------------------------------------------------------------------------------------------------------------------------------------

C++

    #include "tigerapi/push_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    auto push_client = IPushClient::create_push_client(config);
    
    // 设置连接/断开回调
    push_client->set_connected_callback([]() {
        std::cout << "Connected" << std::endl;
    });
    
    push_client->set_disconnected_callback([]() {
        std::cout << "Disconnected" << std::endl;
    });
    
    // 设置错误回调
    push_client->set_inner_error_callback([](std::string err) {
        std::cout << "Error: " << err << std::endl;
    });
    
    // 建立连接
    push_client->connect();

* * *

subscribe\_quote 订阅股票行情

[](./push-quote-cpp.md#subscribe_quote-%E8%AE%A2%E9%98%85%E8%82%A1%E7%A5%A8%E8%A1%8C%E6%83%85)

----------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_quote(const std::vector<std::string> &symbols)`

**说明**

订阅股票的实时行情推送

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | vector<string> | Yes | 标的代码列表 |

**回调设置**

使用 `set_quote_changed_callback` 设置行情变动回调：

C++

    push_client->set_quote_changed_callback([](const tigeropen::push::pb::QuoteBasicData& data) {
        std::cout << "Symbol: " << data.symbol()
                  << " Price: " << data.latestprice()
                  << " Volume: " << data.volume()
                  << std::endl;
    });

**示例**

C++

    std::vector<std::string> symbols = {"AAPL", "TSLA"};
    push_client->subscribe_quote(symbols);

* * *

unsubscribe\_quote 取消订阅股票行情

[](./push-quote-cpp.md#unsubscribe_quote-%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85%E8%82%A1%E7%A5%A8%E8%A1%8C%E6%83%85)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::unsubscribe_quote(const std::vector<std::string> &symbols)`

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | vector<string> | Yes | 标的代码列表 |

* * *

subscribe\_future\_quote 订阅期货行情

[](./push-quote-cpp.md#subscribe_future_quote-%E8%AE%A2%E9%98%85%E6%9C%9F%E8%B4%A7%E8%A1%8C%E6%83%85)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_future_quote(const std::vector<std::string> &symbols)`

**说明**

订阅期货合约的实时行情推送，使用与股票行情相同的 `set_quote_changed_callback` 回调

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | vector<string> | Yes | 期货合约代码列表，如 CL2312 |

* * *

subscribe\_option\_quote 订阅期权行情

[](./push-quote-cpp.md#subscribe_option_quote-%E8%AE%A2%E9%98%85%E6%9C%9F%E6%9D%83%E8%A1%8C%E6%83%85)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_option_quote(const std::vector<std::string> &symbols)`

**说明**

订阅期权合约的实时行情推送

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | vector<string> | Yes | 期权标识符列表 |

* * *

subscribe\_quote\_depth 订阅深度行情

[](./push-quote-cpp.md#subscribe_quote_depth-%E8%AE%A2%E9%98%85%E6%B7%B1%E5%BA%A6%E8%A1%8C%E6%83%85)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_quote_depth(const std::vector<std::string> &symbols)`

**说明**

订阅深度行情（买卖盘口）推送

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | vector<string> | Yes | 标的代码列表 |

**回调设置**

C++

    push_client->set_quote_depth_changed_callback([](const tigeropen::push::pb::QuoteDepthData& data) {
        std::cout << "Depth data received" << std::endl;
    });

* * *

unsubscribe\_quote\_depth 取消订阅深度行情

[](./push-quote-cpp.md#unsubscribe_quote_depth-%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85%E6%B7%B1%E5%BA%A6%E8%A1%8C%E6%83%85)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::unsubscribe_quote_depth(const std::vector<std::string> &symbols)`

* * *

subscribe\_kline 订阅K线

[](./push-quote-cpp.md#subscribe_kline-%E8%AE%A2%E9%98%85k%E7%BA%BF)

------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_kline(const std::vector<std::string> &symbols)`

**说明**

订阅K线数据推送

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | vector<string> | Yes | 标的代码列表 |

**回调设置**

C++

    push_client->set_kline_changed_callback([](const tigeropen::push::pb::KlineData& data) {
        std::cout << "Kline data received" << std::endl;
    });

* * *

unsubscribe\_kline 取消订阅K线

[](./push-quote-cpp.md#unsubscribe_kline-%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85k%E7%BA%BF)

------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::unsubscribe_kline(const std::vector<std::string> &symbols)`

* * *

subscribe\_tick 订阅逐笔成交

[](./push-quote-cpp.md#subscribe_tick-%E8%AE%A2%E9%98%85%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4)

--------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_tick(const std::vector<std::string> &symbols)`

**说明**

订阅逐笔成交数据推送

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | vector<string> | Yes | 标的代码列表 |

**回调设置**

C++

    // 使用 TradeTick 对象回调
    push_client->set_tick_changed_callback([](const TradeTick& data) {
        std::cout << "Symbol: " << data.symbol << " ticks: " << data.ticks.size() << std::endl;
    });
    
    // 或使用完整 Protobuf TickData 回调
    push_client->set_full_tick_changed_callback([](const tigeropen::push::pb::TickData& data) {
        std::cout << "Full tick data received" << std::endl;
    });

* * *

unsubscribe\_tick 取消订阅逐笔成交

[](./push-quote-cpp.md#unsubscribe_tick-%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::unsubscribe_tick(const std::vector<std::string> &symbols)`

* * *

subscribe\_market 订阅整个市场

[](./push-quote-cpp.md#subscribe_market-%E8%AE%A2%E9%98%85%E6%95%B4%E4%B8%AA%E5%B8%82%E5%9C%BA)

------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_market(const std::string &market)`

**说明**

订阅整个市场的行情推送

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | string | Yes | 市场，如 "US"、"HK" |

* * *

unsubscribe\_market 取消订阅整个市场

[](./push-quote-cpp.md#unsubscribe_market-%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85%E6%95%B4%E4%B8%AA%E5%B8%82%E5%9C%BA)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::unsubscribe_market(const std::string &market)`

* * *

subscribe\_stock\_top 订阅股票排行榜

[](./push-quote-cpp.md#subscribe_stock_top-%E8%AE%A2%E9%98%85%E8%82%A1%E7%A5%A8%E6%8E%92%E8%A1%8C%E6%A6%9C)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_stock_top(const std::string &market, const std::vector<std::string> &indicators)`

**说明**

订阅股票排行榜数据推送

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | string | Yes | 市场，如 "US"、"HK" |
| indicators | vector<string> | No  | 排行指标列表，默认空 |

**回调设置**

C++

    push_client->set_stock_top_changed_callback([](const tigeropen::push::pb::StockTopData& data) {
        std::cout << "Stock top data received" << std::endl;
    });

* * *

subscribe\_option\_top 订阅期权排行榜

[](./push-quote-cpp.md#subscribe_option_top-%E8%AE%A2%E9%98%85%E6%9C%9F%E6%9D%83%E6%8E%92%E8%A1%8C%E6%A6%9C)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_option_top(const std::string &market, const std::vector<std::string> &indicators)`

**说明**

订阅期权排行榜数据推送

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | string | Yes | 市场  |
| indicators | vector<string> | No  | 排行指标列表 |

**回调设置**

C++

    push_client->set_option_top_changed_callback([](const tigeropen::push::pb::OptionTopData& data) {
        std::cout << "Option top data received" << std::endl;
    });

* * *

subscribe\_cc 订阅数字货币行情

[](./push-quote-cpp.md#subscribe_cc-%E8%AE%A2%E9%98%85%E6%95%B0%E5%AD%97%E8%B4%A7%E5%B8%81%E8%A1%8C%E6%83%85)

------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_cc(const std::vector<std::string> &symbols)`

**说明**

订阅数字货币的实时行情推送

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | vector<string> | Yes | 数字货币代码列表 |

* * *

unsubscribe\_cc 取消订阅数字货币行情

[](./push-quote-cpp.md#unsubscribe_cc-%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85%E6%95%B0%E5%AD%97%E8%B4%A7%E5%B8%81%E8%A1%8C%E6%83%85)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::unsubscribe_cc(const std::vector<std::string> &symbols)`

* * *

query\_subscribed\_symbols 查询已订阅标的

[](./push-quote-cpp.md#query_subscribed_symbols-%E6%9F%A5%E8%AF%A2%E5%B7%B2%E8%AE%A2%E9%98%85%E6%A0%87%E7%9A%84)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::query_subscribed_symbols()`

**说明**

查询当前已订阅的所有标的列表

**回调设置**

C++

    push_client->set_query_subscribed_symbols_changed_callback([](const tigeropen::push::pb::Response& resp) {
        std::cout << "Subscribed symbols: " << resp.DebugString() << std::endl;
    });
    
    push_client->query_subscribed_symbols();

* * *

完整示例

[](./push-quote-cpp.md#%E5%AE%8C%E6%95%B4%E7%A4%BA%E4%BE%8B)

-----------------------------------------------------------------------------------------------

C++

    #include <iostream>
    #include <thread>
    #include <chrono>
    #include "tigerapi/push_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    int main() {
        ClientConfig config(false, U("/path/to/your/properties/"));
        auto push_client = IPushClient::create_push_client(config);
    
        // 连接回调
        push_client->set_connected_callback([]() {
            std::cout << "Connected" << std::endl;
        });
    
        push_client->set_disconnected_callback([]() {
            std::cout << "Disconnected" << std::endl;
        });
    
        // 订阅成功/失败回调
        push_client->set_subscribe_callback([](const tigeropen::push::pb::Response& resp) {
            std::cout << "Subscribe result: " << resp.DebugString() << std::endl;
        });
    
        // 行情变动回调
        push_client->set_quote_changed_callback([](const tigeropen::push::pb::QuoteBasicData& data) {
            std::cout << "Symbol: " << data.symbol()
                      << " Price: " << data.latestprice()
                      << std::endl;
        });
    
        // 逐笔成交回调
        push_client->set_tick_changed_callback([](const TradeTick& data) {
            std::cout << "Tick: " << data.symbol << " count: " << data.ticks.size() << std::endl;
        });
    
        // 连接
        push_client->connect();
    
        // 订阅
        std::vector<std::string> symbols = {"AAPL", "TSLA"};
        push_client->subscribe_quote(symbols);
        push_client->subscribe_tick(symbols);
    
        // 等待推送
        std::this_thread::sleep_for(std::chrono::seconds(60));
    
        // 取消订阅并断开
        push_client->unsubscribe_quote(symbols);
        push_client->unsubscribe_tick(symbols);
        push_client->disconnect();
    
        return 0;
    }
