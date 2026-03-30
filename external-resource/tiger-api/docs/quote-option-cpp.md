# 期权行情

get\_option\_expiration 获取期权到期日

[](./quote-option-cpp.md#get_option_expiration-%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E5%88%B0%E6%9C%9F%E6%97%A5)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_option_expiration(const value &symbols)`

**说明**

获取指定标的的所有期权到期日

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 标的代码数组，如 `value::array({value::string(U("AAPL"))})` |

**返回**

`web::json::value` JSON 对象，包含到期日列表

**示例**

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value symbols = value::array();
    symbols[0] = value::string(U("AAPL"));
    
    value result = quote_client.get_option_expiration(symbols);
    ucout << result.serialize() << std::endl;

* * *

get\_option\_chain 获取期权链

[](./quote-option-cpp.md#get_option_chain-%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E9%93%BE)

-----------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_option_chain(const utility::string_t symbol, utility::string_t expiry, value option_filter)`

**说明**

获取指定标的、到期日的期权链数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | utility::string\_t | Yes | 标的代码，如 U("AAPL") |
| expiry | time\_t 或 utility::string\_t | Yes | 到期日，时间戳（毫秒）或日期字符串如 U("2024-06-21") |
| option\_filter | value | No  | 筛选条件 JSON 对象，默认 value::null() |

**option\_filter 筛选条件**

| 字段名 | 类型  | 描述  |
| --- | --- | --- |
| implied\_volatility\_min | double | 隐含波动率最小值 |
| implied\_volatility\_max | double | 隐含波动率最大值 |
| delta\_min | double | Delta 最小值 |
| delta\_max | double | Delta 最大值 |
| open\_interest\_min | int | 未平仓合约数最小值 |
| open\_interest\_max | int | 未平仓合约数最大值 |
| in\_the\_money | bool | 是否为价内期权 |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    // 不带筛选条件
    value result = quote_client.get_option_chain(U("AAPL"), U("2024-06-21"));
    ucout << result.serialize() << std::endl;
    
    // 带筛选条件
    value filter = value::object();
    filter[U("in_the_money")] = value::boolean(true);
    value result2 = quote_client.get_option_chain(U("AAPL"), U("2024-06-21"), filter);

* * *

get\_option\_brief 获取期权行情快照

[](./quote-option-cpp.md#get_option_brief-%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E8%A1%8C%E6%83%85%E5%BF%AB%E7%85%A7)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_option_brief(value identifiers)`

**说明**

获取期权合约的实时行情快照

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| identifiers | value 或 utility::string\_t | Yes | 期权标识符，支持单个字符串或数组。如 U("AAPL 240621C00190000") |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value result = quote_client.get_option_brief(U("AAPL  240621C00190000"));
    ucout << result.serialize() << std::endl;

* * *

get\_option\_kline 获取期权K线

[](./quote-option-cpp.md#get_option_kline-%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83k%E7%BA%BF)

-------------------------------------------------------------------------------------------------------------------------------------------------

`vector<Kline> QuoteClient::get_option_kline(value identifiers, time_t begin_time, time_t end_time)`

**说明**

获取期权合约的K线数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| identifiers | value | Yes | 期权标识符数组 |
| begin\_time | time\_t | Yes | 起始时间戳（毫秒） |
| end\_time | time\_t | No  | 结束时间戳（毫秒），默认 4070880000000 |

**返回**

`web::json::value` JSON 对象，或 `vector<Kline>` Kline对象列表

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value identifiers = value::array();
    identifiers[0] = value::string(U("AAPL  240621C00190000"));
    
    vector<Kline> klines = quote_client.get_option_kline(identifiers, 1700000000000);

* * *

get\_option\_trade\_tick 获取期权逐笔成交

[](./quote-option-cpp.md#get_option_trade_tick-%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_option_trade_tick(value identifiers)`

**说明**

获取期权合约的逐笔成交数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| identifiers | value | Yes | 期权标识符数组 |

**返回**

`web::json::value` JSON 对象

* * *

get\_option\_depth 获取期权深度行情

[](./quote-option-cpp.md#get_option_depth-%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E6%B7%B1%E5%BA%A6%E8%A1%8C%E6%83%85)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_option_depth(const value &symbols, utility::string_t market)`

**说明**

获取期权的深度行情数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 期权标识符数组 |
| market | utility::string\_t | No  | 市场，默认 U("US") |

**返回**

`web::json::value` JSON 对象

* * *

get\_option\_timeline 获取期权分时

[](./quote-option-cpp.md#get_option_timeline-%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E5%88%86%E6%97%B6)

---------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_option_timeline(const value &symbols, utility::string_t market, time_t begin_time)`

**说明**

获取期权的分时数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 期权标识符数组 |
| market | utility::string\_t | No  | 市场，默认 U("US") |
| begin\_time | time\_t | No  | 起始时间戳，默认 -1 |

**返回**

`web::json::value` JSON 对象
