# 获取订单信息

get\_order 获取单个订单

[](./orderinfo-cpp.md#get_order-%E8%8E%B7%E5%8F%96%E5%8D%95%E4%B8%AA%E8%AE%A2%E5%8D%95)

---------------------------------------------------------------------------------------------------------------------------------------

`Order TradeClient::get_order(unsigned long long id, bool is_brief)`

**说明**

根据订单号查询单个订单详情，返回 `Order` 结构体对象

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| id  | unsigned long long | Yes | 订单号 |
| is\_brief | bool | No  | 是否简要信息，默认 false |

**返回**

`Order` 对象

**Order 对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| id  | unsigned long long | 订单ID |
| order\_id | long | 外部订单号 |
| account | utility::string\_t | 资金账号 |
| contract | Contract | 合约对象（包含 symbol, sec\_type, market, currency 等） |
| action | utility::string\_t | 买卖方向 BUY/SELL |
| order\_type | utility::string\_t | 订单类型 MKT/LMT/STP/STP\_LMT/TRAIL |
| total\_quantity | long long | 订单总数量 |
| filled\_quantity | long long | 已成交数量 |
| limit\_price | double | 限价  |
| aux\_price | double | 止损触发价 |
| trailing\_percent | double | 跟踪止损百分比 |
| avg\_fill\_price | double | 平均成交价 |
| status | utility::string\_t | 订单状态 |
| time\_in\_force | utility::string\_t | 订单有效期 |
| outside\_rth | bool | 是否盘前盘后 |
| realized\_pnl | double | 已实现盈亏 |
| commission | double | 佣金  |
| open\_time | time\_t | 下单时间 |
| latest\_time | time\_t | 最近成交时间 |
| update\_time | time\_t | 订单更新时间 |
| user\_mark | utility::string\_t | 用户备注 |
| reason | utility::string\_t | 订单失败原因 |

**示例**

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    Order order = trade_client.get_order(14275856193552384);
    ucout << order.to_string() << std::endl;
    std::cout << "Status: " << order.status << std::endl;
    std::cout << "Filled: " << order.filled_quantity << "/" << order.total_quantity << std::endl;

* * *

get\_orders 获取订单列表

[](./orderinfo-cpp.md#get_orders-%E8%8E%B7%E5%8F%96%E8%AE%A2%E5%8D%95%E5%88%97%E8%A1%A8)

-----------------------------------------------------------------------------------------------------------------------------------------

`value TradeClient::get_orders(const utility::string_t &account, const utility::string_t &sec_type, const utility::string_t &market, const utility::string_t &symbol, time_t start_date, time_t end_date, int limit, bool is_brief, const value &states, const utility::string_t &sort_by, const utility::string_t &seg_type)`

**说明**

查询订单列表。此方法提供枚举和字符串两种重载版本。

**参数（字符串版本）**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | utility::string\_t | No  | 资金账号 |
| sec\_type | utility::string\_t | No  | 合约类型，如 U("STK")，默认空 |
| market | utility::string\_t | No  | 市场，默认 U("ALL") |
| symbol | utility::string\_t | No  | 标的代码，默认空 |
| start\_date | time\_t | No  | 起始时间戳（毫秒），默认 -1 |
| end\_date | time\_t | No  | 结束时间戳（毫秒），默认 -1 |
| limit | int | No  | 返回条数限制，默认 100 |
| is\_brief | bool | No  | 是否简要信息，默认 false |
| states | value | No  | 订单状态筛选数组 |
| sort\_by | utility::string\_t | No  | 排序方式，默认空 |
| seg\_type | utility::string\_t | No  | 账户分段，默认空 |

**参数（枚举版本）**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | utility::string\_t | Yes | 资金账号 |
| sec\_type | SecType | No  | 合约类型，默认 SecType::ALL |
| market | Market | No  | 市场，默认 Market::ALL |
| symbol | utility::string\_t | No  | 标的代码 |
| start\_date | time\_t | No  | 起始时间戳（毫秒），默认 -1 |
| end\_date | time\_t | No  | 结束时间戳（毫秒），默认 -1 |
| limit | int | No  | 返回条数限制，默认 100 |
| is\_brief | bool | No  | 是否简要信息，默认 false |
| states | value | No  | 订单状态筛选数组 |
| sort\_by | OrderSortBy | No  | 排序方式，默认 OrderSortBy::LATEST\_STATUS\_UPDATED |
| seg\_type | SegmentType | No  | 账户分段，默认 SegmentType::SEC |

**返回**

`web::json::value` JSON 数组

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    value orders = trade_client.get_orders();
    ucout << orders.serialize() << std::endl;

* * *

get\_active\_orders 获取待成交订单

[](./orderinfo-cpp.md#get_active_orders-%E8%8E%B7%E5%8F%96%E5%BE%85%E6%88%90%E4%BA%A4%E8%AE%A2%E5%8D%95)

------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value TradeClient::get_active_orders(utility::string_t account, utility::string_t sec_type, utility::string_t market, utility::string_t symbol, time_t start_date, time_t end_date, unsigned long long parent_id, utility::string_t sort_by, utility::string_t seg_type)`

**说明**

查询当前待成交（未完成）的订单列表

**参数（字符串版本）**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | utility::string\_t | No  | 资金账号 |
| sec\_type | utility::string\_t | No  | 合约类型，默认空 |
| market | utility::string\_t | No  | 市场，默认 U("ALL") |
| symbol | utility::string\_t | No  | 标的代码 |
| start\_date | time\_t | No  | 起始时间戳，默认 -1 |
| end\_date | time\_t | No  | 结束时间戳，默认 -1 |
| parent\_id | unsigned long long | No  | 父订单ID，默认 0 |
| sort\_by | utility::string\_t | No  | 排序方式，默认空 |
| seg\_type | utility::string\_t | No  | 账户分段，默认空 |

**返回**

`web::json::value` JSON 数组

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    value active_orders = trade_client.get_active_orders();
    ucout << active_orders.serialize() << std::endl;

* * *

get\_inactive\_orders 获取非活跃订单

[](./orderinfo-cpp.md#get_inactive_orders-%E8%8E%B7%E5%8F%96%E9%9D%9E%E6%B4%BB%E8%B7%83%E8%AE%A2%E5%8D%95)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value TradeClient::get_inactive_orders(utility::string_t account, utility::string_t sec_type, utility::string_t market, utility::string_t symbol, time_t start_date, time_t end_date, unsigned long long parent_id, utility::string_t sort_by, utility::string_t seg_type)`

**说明**

查询非活跃的订单列表（已取消、已失效等）

**参数**

参数与 `get_active_orders` 相同

**返回**

`web::json::value` JSON 数组

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    value inactive_orders = trade_client.get_inactive_orders();
    ucout << inactive_orders.serialize() << std::endl;

* * *

get\_filled\_orders 获取已成交订单

[](./orderinfo-cpp.md#get_filled_orders-%E8%8E%B7%E5%8F%96%E5%B7%B2%E6%88%90%E4%BA%A4%E8%AE%A2%E5%8D%95)

------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value TradeClient::get_filled_orders(utility::string_t account, utility::string_t sec_type, utility::string_t market, utility::string_t symbol, time_t start_date, time_t end_date, unsigned long long parent_id, utility::string_t sort_by, utility::string_t seg_type)`

**说明**

查询已成交的订单列表

**参数**

参数与 `get_active_orders` 相同

**返回**

`web::json::value` JSON 数组

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    value filled = trade_client.get_filled_orders();
    ucout << filled.serialize() << std::endl;

* * *

get\_transactions 获取成交记录

[](./orderinfo-cpp.md#get_transactions-%E8%8E%B7%E5%8F%96%E6%88%90%E4%BA%A4%E8%AE%B0%E5%BD%95)

-----------------------------------------------------------------------------------------------------------------------------------------------------

**说明**

查询成交记录。提供两个重载版本：按订单号查询和按标的/条件查询。

**重载一：按订单号查询**

`value TradeClient::get_transactions(utility::string_t account, long long order_id)`

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | utility::string\_t | Yes | 资金账号 |
| order\_id | long long | Yes | 订单号 |

**重载二：按标的/条件查询**

`value TradeClient::get_transactions(utility::string_t account, utility::string_t symbol, utility::string_t sec_type, long start_time, time_t end_time, int limit, utility::string_t expiry, utility::string_t strike, utility::string_t right, long long order_id)`

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | utility::string\_t | Yes | 资金账号 |
| symbol | utility::string\_t | Yes | 标的代码 |
| sec\_type | utility::string\_t | No  | 合约类型，默认空 |
| start\_time | long | No  | 起始时间戳（毫秒），默认 -1 |
| end\_time | time\_t | No  | 结束时间戳（毫秒），默认 -1 |
| limit | int | No  | 返回条数限制，默认 100 |
| expiry | utility::string\_t | No  | 期权到期日 |
| strike | utility::string\_t | No  | 期权行权价 |
| right | utility::string\_t | No  | 期权方向，如 U("PUT")/U("CALL") |
| order\_id | long long | No  | 订单号，默认 0 |

**返回**

`web::json::value` JSON 数组

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    // 按订单号查询
    value transactions = trade_client.get_transactions(config.account, 14275856193552384LL);
    ucout << transactions.serialize() << std::endl;
    
    // 按标的查询
    value trans2 = trade_client.get_transactions(config.account, U("AAPL"));
    ucout << trans2.serialize() << std::endl;

* * *

preview\_order 预览订单

[](./orderinfo-cpp.md#preview_order-%E9%A2%84%E8%A7%88%E8%AE%A2%E5%8D%95)

---------------------------------------------------------------------------------------------------------------------------

`value TradeClient::preview_order(Order &order)`

**说明**

预览订单，返回预估的佣金、保证金等信息，不会实际提交订单

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| order | Order& | Yes | 订单对象 |

**返回**

`web::json::value` JSON 对象，包含预估信息

**示例**

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    #include "tigerapi/contract_util.h"
    #include "tigerapi/order_util.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    Contract contract = ContractUtil::stock_contract(U("AAPL"), U("USD"));
    Order order = OrderUtil::limit_order(config.account, contract, U("BUY"), 100, 150.0);
    
    value preview = trade_client.preview_order(order);
    ucout << preview.serialize() << std::endl;
