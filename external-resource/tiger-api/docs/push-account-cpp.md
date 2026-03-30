# 交易推送

交易推送订阅

[](./push-account-cpp.md#%E4%BA%A4%E6%98%93%E6%8E%A8%E9%80%81%E8%AE%A2%E9%98%85)

---------------------------------------------------------------------------------------------------------------------

C++ SDK 通过 `IPushClient` 实现交易相关数据的订阅推送功能，包括资产变动、持仓变动、订单状态变动和成交回报推送。

初始化推送客户端

[](./push-account-cpp.md#%E5%88%9D%E5%A7%8B%E5%8C%96%E6%8E%A8%E9%80%81%E5%AE%A2%E6%88%B7%E7%AB%AF)

-----------------------------------------------------------------------------------------------------------------------------------------

C++

    #include "tigerapi/push_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    auto push_client = IPushClient::create_push_client(config);
    
    push_client->set_connected_callback([]() {
        std::cout << "Connected" << std::endl;
    });
    
    push_client->connect();

* * *

subscribe\_asset 订阅资产变动

[](./push-account-cpp.md#subscribe_asset-%E8%AE%A2%E9%98%85%E8%B5%84%E4%BA%A7%E5%8F%98%E5%8A%A8)

------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_asset(const std::string &account)`

**说明**

订阅账户资产变动推送，当账户资产发生变化时触发回调

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 资金账号 |

**回调设置**

C++

    push_client->set_asset_changed_callback([](const tigeropen::push::pb::AssetData& data) {
        std::cout << "Asset changed - Account: " << data.account()
                  << std::endl;
    });

**示例**

C++

    push_client->subscribe_asset("your_account_id");

* * *

unsubscribe\_asset 取消订阅资产变动

[](./push-account-cpp.md#unsubscribe_asset-%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85%E8%B5%84%E4%BA%A7%E5%8F%98%E5%8A%A8)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::unsubscribe_asset(const std::string &account)`

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 资金账号 |

* * *

subscribe\_position 订阅持仓变动

[](./push-account-cpp.md#subscribe_position-%E8%AE%A2%E9%98%85%E6%8C%81%E4%BB%93%E5%8F%98%E5%8A%A8)

------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_position(const std::string &account)`

**说明**

订阅账户持仓变动推送

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 资金账号 |

**回调设置**

C++

    push_client->set_position_changed_callback([](const tigeropen::push::pb::PositionData& data) {
        std::cout << "Position changed - Symbol: " << data.symbol()
                  << " Quantity: " << data.quantity()
                  << std::endl;
    });

**示例**

C++

    push_client->subscribe_position("your_account_id");

* * *

unsubscribe\_position 取消订阅持仓变动

[](./push-account-cpp.md#unsubscribe_position-%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85%E6%8C%81%E4%BB%93%E5%8F%98%E5%8A%A8)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::unsubscribe_position(const std::string &account)`

* * *

subscribe\_order 订阅订单状态变动

[](./push-account-cpp.md#subscribe_order-%E8%AE%A2%E9%98%85%E8%AE%A2%E5%8D%95%E7%8A%B6%E6%80%81%E5%8F%98%E5%8A%A8)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_order(const std::string &account)`

**说明**

订阅订单状态变动推送，当订单状态发生改变时（如提交、成交、取消等）触发回调

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 资金账号 |

**回调设置**

C++

    push_client->set_order_changed_callback([](const tigeropen::push::pb::OrderStatusData& data) {
        std::cout << "Order changed - ID: " << data.id()
                  << " Status: " << data.status()
                  << std::endl;
    });

**示例**

C++

    push_client->subscribe_order("your_account_id");

* * *

unsubscribe\_order 取消订阅订单状态变动

[](./push-account-cpp.md#unsubscribe_order-%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85%E8%AE%A2%E5%8D%95%E7%8A%B6%E6%80%81%E5%8F%98%E5%8A%A8)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::unsubscribe_order(const std::string &account)`

* * *

subscribe\_transaction 订阅成交回报

[](./push-account-cpp.md#subscribe_transaction-%E8%AE%A2%E9%98%85%E6%88%90%E4%BA%A4%E5%9B%9E%E6%8A%A5)

------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::subscribe_transaction(const std::string &account)`

**说明**

订阅成交回报推送，当订单产生新的成交记录时触发回调

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 资金账号 |

**回调设置**

C++

    push_client->set_transaction_changed_callback([](const tigeropen::push::pb::OrderTransactionData& data) {
        std::cout << "Transaction - Symbol: " << data.symbol()
                  << " Filled: " << data.filledquantity()
                  << " Price: " << data.filledprice()
                  << std::endl;
    });

**示例**

C++

    push_client->subscribe_transaction("your_account_id");

* * *

unsubscribe\_transaction 取消订阅成交回报

[](./push-account-cpp.md#unsubscribe_transaction-%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85%E6%88%90%E4%BA%A4%E5%9B%9E%E6%8A%A5)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`unsigned int IPushClient::unsubscribe_transaction(const std::string &account)`

* * *

完整示例

[](./push-account-cpp.md#%E5%AE%8C%E6%95%B4%E7%A4%BA%E4%BE%8B)

-------------------------------------------------------------------------------------------------

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
            std::cout << "Connected to push server" << std::endl;
        });
    
        push_client->set_disconnected_callback([]() {
            std::cout << "Disconnected from push server" << std::endl;
        });
    
        // 资产变动回调
        push_client->set_asset_changed_callback([](const tigeropen::push::pb::AssetData& data) {
            std::cout << "Asset changed" << std::endl;
        });
    
        // 持仓变动回调
        push_client->set_position_changed_callback([](const tigeropen::push::pb::PositionData& data) {
            std::cout << "Position: " << data.symbol()
                      << " qty: " << data.quantity()
                      << std::endl;
        });
    
        // 订单状态变动回调
        push_client->set_order_changed_callback([](const tigeropen::push::pb::OrderStatusData& data) {
            std::cout << "Order: " << data.id()
                      << " status: " << data.status()
                      << std::endl;
        });
    
        // 成交回报回调
        push_client->set_transaction_changed_callback([](const tigeropen::push::pb::OrderTransactionData& data) {
            std::cout << "Transaction: " << data.symbol()
                      << " filled qty: " << data.filledquantity()
                      << " price: " << data.filledprice()
                      << std::endl;
        });
    
        // 建立连接
        push_client->connect();
    
        std::string account = "your_account_id";
    
        // 订阅
        push_client->subscribe_asset(account);
        push_client->subscribe_position(account);
        push_client->subscribe_order(account);
        push_client->subscribe_transaction(account);
    
        // 持续运行
        std::this_thread::sleep_for(std::chrono::hours(1));
    
        // 取消订阅
        push_client->unsubscribe_asset(account);
        push_client->unsubscribe_position(account);
        push_client->unsubscribe_order(account);
        push_client->unsubscribe_transaction(account);
    
        push_client->disconnect();
    
        return 0;
    }
