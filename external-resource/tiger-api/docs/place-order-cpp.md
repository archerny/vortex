# 下单交易

place\_order 下单

[](./place-order-cpp.md#place_order-%E4%B8%8B%E5%8D%95)

-----------------------------------------------------------------------------------------------------

`value TradeClient::place_order(Order &order)`

**说明**

交易下单接口。

`place_order` 调用成功后，order 对象的 id 即被填充，可用于后续的查询或撤单。**此时只表示订单提交成功，不代表订单已被成功执行**。订单的执行是异步的，提交订单后需使用 `get_order` 或 `get_orders` 方法查询对应订单的状态。

> ⚠️
> 
> **注意**
> 
> 1.  市价单（MKT）和止损单（STP）不支持盘前盘后阶段交易
> 2.  可做空标的，目前不支持锁仓，无法同时持有同一标的的多头与空头头寸
> 3.  禁止直接开立反向头寸

**参数**

`Order` 对象，通过 `OrderUtil` 工具类构建

**返回**

`web::json::value` JSON 对象，下单成功返回订单信息

* * *

构建合约对象示例

[](./place-order-cpp.md#%E6%9E%84%E5%BB%BA%E5%90%88%E7%BA%A6%E5%AF%B9%E8%B1%A1%E7%A4%BA%E4%BE%8B)

----------------------------------------------------------------------------------------------------------------------------------------

C++

    #include "tigerapi/contract_util.h"
    
    using namespace TIGER_API;
    
    // 美股
    Contract contract = ContractUtil::stock_contract(U("TIGR"), U("USD"));
    
    // 港股
    Contract contract = ContractUtil::stock_contract(U("00700"), U("HKD"));
    
    // 期权
    Contract contract = ContractUtil::option_contract(U("AAPL  240621C00190000"));
    // 或
    Contract contract = ContractUtil::option_contract(U("AAPL"), U("20240621"), U("190"), U("CALL"));
    
    // 期货
    Contract contract = ContractUtil::future_contract(U("CL2312"), U("USD"));

* * *

限价单 (LMT)

[](./place-order-cpp.md#%E9%99%90%E4%BB%B7%E5%8D%95-lmt)

------------------------------------------------------------------------------------------------

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    #include "tigerapi/contract_util.h"
    #include "tigerapi/order_util.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    // 生成股票合约
    Contract contract = ContractUtil::stock_contract(U("AAPL"), U("USD"));
    
    // 生成限价单
    Order order = OrderUtil::limit_order(
        config.account,  // 下单账户
        contract,        // 合约对象
        U("BUY"),        // 买入
        100,             // 数量
        150.0            // 限价
    );
    
    // 下单
    value result = trade_client.place_order(order);
    ucout << result.serialize() << std::endl;
    
    // 获取订单ID
    std::cout << "Order ID: " << order.id << std::endl;

* * *

市价单 (MKT)

[](./place-order-cpp.md#%E5%B8%82%E4%BB%B7%E5%8D%95-mkt)

------------------------------------------------------------------------------------------------

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    #include "tigerapi/contract_util.h"
    #include "tigerapi/order_util.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    // 生成股票合约
    Contract contract = ContractUtil::stock_contract(U("AAPL"), U("USD"));
    
    // 生成市价单
    Order order = OrderUtil::market_order(
        config.account,  // 下单账户
        contract,        // 合约对象
        U("BUY"),        // 买入
        100              // 数量
    );
    
    // 下单
    value result = trade_client.place_order(order);

* * *

止损单 (STP)

[](./place-order-cpp.md#%E6%AD%A2%E6%8D%9F%E5%8D%95-stp)

------------------------------------------------------------------------------------------------

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    #include "tigerapi/contract_util.h"
    #include "tigerapi/order_util.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    Contract contract = ContractUtil::stock_contract(U("AAPL"), U("USD"));
    
    // 生成止损单
    Order order = OrderUtil::stop_order(
        config.account,  // 下单账户
        contract,        // 合约对象
        U("SELL"),       // 卖出
        100,             // 数量
        140.0            // 止损触发价
    );
    
    value result = trade_client.place_order(order);

* * *

止损限价单 (STP\_LMT)

[](./place-order-cpp.md#%E6%AD%A2%E6%8D%9F%E9%99%90%E4%BB%B7%E5%8D%95-stp_lmt)

-----------------------------------------------------------------------------------------------------------------------------

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    #include "tigerapi/contract_util.h"
    #include "tigerapi/order_util.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    Contract contract = ContractUtil::stock_contract(U("AAPL"), U("USD"));
    
    // 生成止损限价单
    Order order = OrderUtil::stop_limit_order(
        config.account,  // 下单账户
        contract,        // 合约对象
        U("SELL"),       // 卖出
        100,             // 数量
        139.0,           // 限价
        140.0            // 止损触发价
    );
    
    value result = trade_client.place_order(order);

* * *

跟踪止损单 (TRAIL)

[](./place-order-cpp.md#%E8%B7%9F%E8%B8%AA%E6%AD%A2%E6%8D%9F%E5%8D%95-trail)

------------------------------------------------------------------------------------------------------------------------

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    #include "tigerapi/contract_util.h"
    #include "tigerapi/order_util.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    Contract contract = ContractUtil::stock_contract(U("AAPL"), U("USD"));
    
    // 跟踪止损单 - 按跟踪金额
    Order order = OrderUtil::trail_order(
        config.account,  // 下单账户
        contract,        // 合约对象
        U("SELL"),       // 卖出
        100,             // 数量
        5.0,             // 跟踪金额 (aux_price)
        0                // 跟踪百分比 (trailing_percent, 0表示不使用)
    );
    
    // 跟踪止损单 - 按百分比
    Order order2 = OrderUtil::trail_order(
        config.account,
        contract,
        U("SELL"),
        100,
        0,               // aux_price, 0表示不使用
        8.0              // trailing_percent 8%
    );
    
    value result = trade_client.place_order(order);

* * *

下单港股

[](./place-order-cpp.md#%E4%B8%8B%E5%8D%95%E6%B8%AF%E8%82%A1)

------------------------------------------------------------------------------------------------

港股交易中，委托数量必须为该股票「每手股数」的整数倍。

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    Contract contract = ContractUtil::stock_contract(U("00700"), U("HKD"));
    
    Order order = OrderUtil::limit_order(
        config.account,
        contract,
        U("BUY"),
        100,           // 腾讯每手100股
        400.0
    );
    
    value result = trade_client.place_order(order);

* * *

下单期货

[](./place-order-cpp.md#%E4%B8%8B%E5%8D%95%E6%9C%9F%E8%B4%A7)

------------------------------------------------------------------------------------------------

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    Contract contract = ContractUtil::future_contract(U("CL2312"), U("USD"));
    
    Order order = OrderUtil::limit_order(
        config.account,
        contract,
        U("BUY"),
        1,
        70.0
    );
    
    value result = trade_client.place_order(order);

* * *

下单期权

[](./place-order-cpp.md#%E4%B8%8B%E5%8D%95%E6%9C%9F%E6%9D%83)

------------------------------------------------------------------------------------------------

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    // 使用期权标识符构建合约
    Contract contract = ContractUtil::option_contract(U("AAPL  240621C00190000"));
    
    Order order = OrderUtil::limit_order(
        config.account,
        contract,
        U("BUY"),
        1,
        2.5
    );
    
    value result = trade_client.place_order(order);

* * *

校正下单价格

[](./place-order-cpp.md#%E6%A0%A1%E6%AD%A3%E4%B8%8B%E5%8D%95%E4%BB%B7%E6%A0%BC)

--------------------------------------------------------------------------------------------------------------------

使用 `PriceUtil` 校正下单价格。合约价格处于不同档位时，会有不同的精度，当指定不合适精度的价格作为下单价格时，会返回错误。

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    #include "tigerapi/contract_util.h"
    #include "tigerapi/order_util.h"
    #include "tigerapi/price_util.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    // 查询合约获取 tickSizes 信息
    value contract_info = trade_client.get_contract(U("AAPL"), U("STK"));
    value tick_sizes = contract_info[U("tickSizes")];
    
    double price = 150.173;
    
    // 检查价格是否符合 tick 规格
    bool is_ok = PriceUtil::match_tick_size(price, tick_sizes);
    
    // 修正价格（默认向下调整）
    double fixed_price = PriceUtil::fix_price_by_tick_size(price, tick_sizes);
    // fixed_price = 150.17
    
    // 修正价格（向上调整）
    double fixed_price_up = PriceUtil::fix_price_by_tick_size(price, tick_sizes, true);
    // fixed_price_up = 150.18
    
    // 使用修正后的价格下单
    Contract contract = ContractUtil::stock_contract(U("AAPL"), U("USD"));
    Order order = OrderUtil::limit_order(config.account, contract, U("BUY"), 1, fixed_price);
    trade_client.place_order(order);

* * *

Order 对象属性

[](./place-order-cpp.md#order-%E5%AF%B9%E8%B1%A1%E5%B1%9E%E6%80%A7)

------------------------------------------------------------------------------------------------------------

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| id  | unsigned long long | 订单ID |
| order\_id | long | 外部订单ID |
| account | utility::string\_t | 资金账号 |
| contract | Contract | 合约对象（包含 symbol, sec\_type, market, currency） |
| action | utility::string\_t | 交易方向 BUY/SELL |
| order\_type | utility::string\_t | 订单类型 MKT/LMT/STP/STP\_LMT/TRAIL |
| total\_quantity | long long | 订单总数量 |
| total\_quantity\_scale | long | 数量精度因子 |
| limit\_price | double | 限价  |
| aux\_price | double | 止损触发价 |
| trail\_stop\_price | double | 跟踪止损价 |
| trailing\_percent | double | 跟踪止损百分比 |
| percent\_offset | double | 百分比偏移 |
| time\_in\_force | utility::string\_t | 订单有效期 DAY/GTC/GTD |
| outside\_rth | bool | 是否允许盘前盘后交易 |
| adjust\_limit | double | 价格调整范围 |
| user\_mark | utility::string\_t | 备注信息 |
| expire\_time | time\_t | 过期时间 |
| status | utility::string\_t | 订单状态 |
| filled\_quantity | long long | 已成交数量 |
| avg\_fill\_price | double | 平均成交价 |
| realized\_pnl | double | 已实现盈亏 |
| commission | double | 佣金  |
| open\_time | time\_t | 下单时间 |
| latest\_time | time\_t | 最近成交时间 |
| update\_time | time\_t | 订单更新时间 |
| reason | utility::string\_t | 订单失败原因 |
