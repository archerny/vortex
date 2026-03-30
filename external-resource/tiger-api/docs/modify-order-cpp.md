# 取消或修改订单

cancel\_order 取消订单

[](./modify-order-cpp.md#cancel_order-%E5%8F%96%E6%B6%88%E8%AE%A2%E5%8D%95)

----------------------------------------------------------------------------------------------------------------------------

`value TradeClient::cancel_order(unsigned long long id)`

**说明**

取消一个未成交的订单

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| id  | unsigned long long | Yes | 订单号，即 order.id |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    // 取消指定订单
    value result = trade_client.cancel_order(14275856193552384);
    ucout << result.serialize() << std::endl;

* * *

modify\_order 修改订单

[](./modify-order-cpp.md#modify_order-%E4%BF%AE%E6%94%B9%E8%AE%A2%E5%8D%95)

----------------------------------------------------------------------------------------------------------------------------

**说明**

修改一个未成交的订单。提供两种重载方式：直接修改 Order 对象属性后提交，或通过参数指定修改内容。

**方式一：修改 Order 对象后提交**

`value TradeClient::modify_order(Order &order)`

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| order | Order& | Yes | 修改后的订单对象 |

**方式二：通过参数指定修改内容**

`value TradeClient::modify_order(Order &order, double limit_price, long total_quantity, double aux_price, double trail_stop_price, double trailing_percent, double percent_offset, utility::string_t time_in_force, bool outside_rth, time_t expire_time)`

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| order | Order& | Yes | 原始订单对象 |
| limit\_price | double | No  | 新限价，默认 0（不修改） |
| total\_quantity | long | No  | 新数量，默认 0（不修改） |
| aux\_price | double | No  | 新止损触发价，默认 0（不修改） |
| trail\_stop\_price | double | No  | 新跟踪止损价，默认 0 |
| trailing\_percent | double | No  | 新跟踪百分比，默认 0 |
| percent\_offset | double | No  | 百分比偏移，默认 0 |
| time\_in\_force | utility::string\_t | No  | 订单有效期，默认空 |
| outside\_rth | bool | No  | 是否允许盘前盘后，默认 false |
| expire\_time | time\_t | No  | 过期时间，默认 0 |

**可修改字段**

| 字段名 | 描述  |
| --- | --- |
| total\_quantity | 订单总数量 |
| limit\_price | 限价  |
| aux\_price | 止损触发价 |
| trailing\_percent | 跟踪止损百分比 |
| time\_in\_force | 订单有效期 |
| outside\_rth | 是否允许盘前盘后交易 |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    #include "tigerapi/contract_util.h"
    #include "tigerapi/order_util.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    // 先下单
    Contract contract = ContractUtil::stock_contract(U("AAPL"), U("USD"));
    Order order = OrderUtil::limit_order(config.account, contract, U("BUY"), 100, 150.0);
    trade_client.place_order(order);
    
    // 方式一：直接修改 Order 对象属性
    order.limit_price = 155.0;
    order.total_quantity = 200;
    value result = trade_client.modify_order(order);
    ucout << result.serialize() << std::endl;
    
    // 方式二：通过参数修改
    value result2 = trade_client.modify_order(order, 160.0, 300);
    ucout << result2.serialize() << std::endl;

* * *

完整示例：下单、查询、修改、取消

[](./modify-order-cpp.md#%E5%AE%8C%E6%95%B4%E7%A4%BA%E4%BE%8B%E4%B8%8B%E5%8D%95%E6%9F%A5%E8%AF%A2%E4%BF%AE%E6%94%B9%E5%8F%96%E6%B6%88)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    #include "tigerapi/contract_util.h"
    #include "tigerapi/order_util.h"
    
    using namespace TIGER_API;
    
    int main() {
        ClientConfig config(false, U("/path/to/your/properties/"));
        TradeClient trade_client(config);
    
        // 1. 构建合约
        Contract contract = ContractUtil::stock_contract(U("AAPL"), U("USD"));
    
        // 2. 下限价单
        Order order = OrderUtil::limit_order(config.account, contract, U("BUY"), 100, 150.0);
        trade_client.place_order(order);
        std::cout << "Order placed, ID: " << order.id << std::endl;
    
        // 3. 查询订单状态（返回 Order 对象）
        Order order_info = trade_client.get_order(order.id);
        ucout << U("Order status: ") << order_info.status << std::endl;
    
        // 4. 修改订单
        order.limit_price = 155.0;
        trade_client.modify_order(order);
        std::cout << "Order modified" << std::endl;
    
        // 5. 取消订单
        trade_client.cancel_order(order.id);
        std::cout << "Order cancelled" << std::endl;
    
        return 0;
    }
