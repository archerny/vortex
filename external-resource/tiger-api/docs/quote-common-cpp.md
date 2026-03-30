# 行情通用

get\_market\_state 获取市场状态

[](./quote-common-cpp.md#get_market_state-%E8%8E%B7%E5%8F%96%E5%B8%82%E5%9C%BA%E7%8A%B6%E6%80%81)

---------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_market_state(utility::string_t market)`

**说明**

获取指定市场状态，返回市场名称、市场状态（未开盘、交易中、已收盘等）和最近的交易时间。

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | utility::string\_t | Yes | 市场，如 U("US")、U("HK")、U("CN") |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value result = quote_client.get_market_state(U("US"));
    ucout << result.serialize() << std::endl;

* * *

get\_symbols 获取标的列表

[](./quote-common-cpp.md#get_symbols-%E8%8E%B7%E5%8F%96%E6%A0%87%E7%9A%84%E5%88%97%E8%A1%A8)

----------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_symbols(Market market = Market::ALL, bool include_otc = false)`

**说明**

获取指定市场的所有标的代码列表

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | Market | No  | 市场枚举，Market::US / Market::HK / Market::ALL，默认 Market::ALL |
| include\_otc | bool | No  | 是否包含OTC标的，默认 false |

**返回**

`web::json::value` JSON 数组，包含标的代码

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value result = quote_client.get_symbols(Market::US);
    ucout << result.serialize() << std::endl;

* * *

get\_all\_symbol\_names 获取标的名称列表

[](./quote-common-cpp.md#get_all_symbol_names-%E8%8E%B7%E5%8F%96%E6%A0%87%E7%9A%84%E5%90%8D%E7%A7%B0%E5%88%97%E8%A1%A8)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_all_symbol_names(Market market = Market::ALL, bool include_otc = false)`

**说明**

获取指定市场的所有标的代码及名称

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | Market | No  | 市场枚举，Market::US / Market::HK / Market::ALL，默认 Market::ALL |
| include\_otc | bool | No  | 是否包含OTC标的，默认 false |

**返回**

`web::json::value` JSON 数组

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value result = quote_client.get_all_symbol_names(Market::HK);
    ucout << result.serialize() << std::endl;

* * *

get\_trading\_calendar 获取交易日历

[](./quote-common-cpp.md#get_trading_calendar-%E8%8E%B7%E5%8F%96%E4%BA%A4%E6%98%93%E6%97%A5%E5%8E%86)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_trading_calendar(Market market, utility::string_t begin_date, utility::string_t end_date)`

**说明**

获取指定市场的交易日历

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | Market 或 utility::string\_t | Yes | 市场，如 Market::US 或 U("US") |
| begin\_date | utility::string\_t | Yes | 起始日期，格式 "yyyy-MM-dd"，如 U("2024-01-01") |
| end\_date | utility::string\_t | Yes | 结束日期，格式 "yyyy-MM-dd"，如 U("2024-12-31") |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value result = quote_client.get_trading_calendar(Market::US, U("2024-01-01"), U("2024-06-30"));
    ucout << result.serialize() << std::endl;

* * *

get\_quote\_permission 获取行情权限

[](./quote-common-cpp.md#get_quote_permission-%E8%8E%B7%E5%8F%96%E8%A1%8C%E6%83%85%E6%9D%83%E9%99%90)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_quote_permission()`

**说明**

获取当前账户的行情权限

**返回**

`web::json::value` JSON 对象，包含行情权限信息

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value result = quote_client.get_quote_permission();
    ucout << result.serialize() << std::endl;

* * *

get\_kline\_quota 获取K线额度

[](./quote-common-cpp.md#get_kline_quota-%E8%8E%B7%E5%8F%96k%E7%BA%BF%E9%A2%9D%E5%BA%A6)

-----------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_kline_quota(bool with_details = false)`

**说明**

获取K线查询额度信息

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| with\_details | bool | No  | 是否返回详细信息，默认 false |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value result = quote_client.get_kline_quota(true);
    ucout << result.serialize() << std::endl;
