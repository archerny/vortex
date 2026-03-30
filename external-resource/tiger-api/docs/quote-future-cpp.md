# 期货行情

get\_future\_exchange 获取期货交易所列表

[](./quote-future-cpp.md#get_future_exchange-%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E4%BA%A4%E6%98%93%E6%89%80%E5%88%97%E8%A1%A8)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_future_exchange(SecType sec_type)`

**说明**

获取期货支持的交易所列表

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| sec\_type | SecType | No  | 合约类型，默认 SecType::FUT |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value result = quote_client.get_future_exchange();
    ucout << result.serialize() << std::endl;

* * *

get\_future\_contracts 获取期货合约列表

[](./quote-future-cpp.md#get_future_contracts-%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E5%90%88%E7%BA%A6%E5%88%97%E8%A1%A8)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_future_contracts(utility::string_t type)`

**说明**

获取指定品种的期货合约列表

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| type | utility::string\_t | Yes | 期货品种代码，如 U("CL")（原油）、U("ES")（标普500） |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value result = quote_client.get_future_contracts(U("CL"));
    ucout << result.serialize() << std::endl;

* * *

get\_future\_continuous\_contracts 获取连续合约

[](./quote-future-cpp.md#get_future_continuous_contracts-%E8%8E%B7%E5%8F%96%E8%BF%9E%E7%BB%AD%E5%90%88%E7%BA%A6)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_future_continuous_contracts(utility::string_t type)`

**说明**

获取指定品种的连续合约信息

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| type | utility::string\_t | Yes | 期货品种代码 |

**返回**

`web::json::value` JSON 对象

* * *

get\_future\_current\_contract 获取当前合约

[](./quote-future-cpp.md#get_future_current_contract-%E8%8E%B7%E5%8F%96%E5%BD%93%E5%89%8D%E5%90%88%E7%BA%A6)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_future_current_contract(utility::string_t type)`

**说明**

获取指定品种的当前主力合约

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| type | utility::string\_t | Yes | 期货品种代码 |

**返回**

`web::json::value` JSON 对象

* * *

get\_future\_contract\_by\_contract\_code 通过合约代码获取期货合约

[](./quote-future-cpp.md#get_future_contract_by_contract_code-%E9%80%9A%E8%BF%87%E5%90%88%E7%BA%A6%E4%BB%A3%E7%A0%81%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E5%90%88%E7%BA%A6)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_future_contract_by_contract_code(utility::string_t contract_code)`

**说明**

通过具体的合约代码获取期货合约信息

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contract\_code | utility::string\_t | Yes | 合约代码，如 U("CL2312") |

**返回**

`web::json::value` JSON 对象

* * *

get\_future\_contract\_by\_exchange\_code 通过交易所获取期货合约

[](./quote-future-cpp.md#get_future_contract_by_exchange_code-%E9%80%9A%E8%BF%87%E4%BA%A4%E6%98%93%E6%89%80%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E5%90%88%E7%BA%A6)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_future_contract_by_exchange_code(utility::string_t exchange_code)`

**说明**

通过交易所代码获取期货合约列表

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| exchange\_code | utility::string\_t | Yes | 交易所代码，如 U("CME")、U("NYMEX") |

**返回**

`web::json::value` JSON 对象

* * *

get\_future\_kline 获取期货K线

[](./quote-future-cpp.md#get_future_kline-%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7k%E7%BA%BF)

-------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_future_kline(value contract_codes, utility::string_t period, time_t begin_time, time_t end_time, int limit, utility::string_t page_token)`

**说明**

获取期货合约的K线数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contract\_codes | value | Yes | 合约代码数组 |
| period | BarPeriod 或 utility::string\_t | No  | K线周期，默认 BarPeriod::DAY。可选值：day/week/month/year/1min/5min/15min/30min/60min |
| begin\_time | time\_t | No  | 起始时间戳，默认 -1 |
| end\_time | time\_t | No  | 结束时间戳，默认 -1 |
| limit | int | No  | 返回条数，默认 251 |
| page\_token | utility::string\_t | No  | 翻页标记 |

**返回**

`web::json::value` JSON 对象，或 `vector<Kline>` Kline 对象列表

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value codes = value::array();
    codes[0] = value::string(U("CL2312"));
    
    value result = quote_client.get_future_kline(codes, U("day"));
    ucout << result.serialize() << std::endl;

* * *

get\_future\_real\_time\_quote 获取期货实时行情

[](./quote-future-cpp.md#get_future_real_time_quote-%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E5%AE%9E%E6%97%B6%E8%A1%8C%E6%83%85)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`vector<RealtimeQuote> QuoteClient::get_future_real_time_quote(value contract_codes)`

**说明**

获取期货合约的实时行情数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contract\_codes | value | Yes | 合约代码数组 |

**返回**

`vector<RealtimeQuote>` 实时行情对象列表

**RealtimeQuote 对象属性（期货额外字段）**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| contract\_code | utility::string\_t | 合约代码 |
| open\_interest | long long | 未平仓合约数 |
| limit\_down | int | 跌停价 |
| limit\_up | int | 涨停价 |

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value codes = value::array();
    codes[0] = value::string(U("CL2312"));
    
    vector<RealtimeQuote> quotes = quote_client.get_future_real_time_quote(codes);
    for (auto& q : quotes) {
        ucout << q.contract_code << U(" price: ") << q.latest_price << std::endl;
    }

* * *

get\_future\_tick 获取期货逐笔成交

[](./quote-future-cpp.md#get_future_tick-%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_future_tick(utility::string_t contract_code, long begin_index, long end_index, int limit)`

**说明**

获取期货合约的逐笔成交数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contract\_code | utility::string\_t | Yes | 合约代码 |
| begin\_index | long | No  | 起始索引，默认 0 |
| end\_index | long | No  | 结束索引，默认 100 |
| limit | int | No  | 返回条数，默认 1000 |

**返回**

`web::json::value` JSON 对象

* * *

get\_future\_trading\_date 获取期货交易日

[](./quote-future-cpp.md#get_future_trading_date-%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E4%BA%A4%E6%98%93%E6%97%A5)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_future_trading_date(utility::string_t contract_code, utility::string_t trading_date)`

**说明**

获取期货合约的交易日信息

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contract\_code | utility::string\_t | Yes | 合约代码 |
| trading\_date | utility::string\_t | Yes | 交易日期 |

**返回**

`web::json::value` JSON 对象

* * *

get\_future\_depth 获取期货深度行情

[](./quote-future-cpp.md#get_future_depth-%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E6%B7%B1%E5%BA%A6%E8%A1%8C%E6%83%85)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_future_depth(value contract_codes, utility::string_t lang)`

**说明**

获取期货合约的深度行情数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contract\_codes | value | Yes | 合约代码数组 |
| lang | utility::string\_t | No  | 语言，默认空 |

**返回**

`web::json::value` JSON 对象
