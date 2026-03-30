# 股票行情

get\_brief 获取股票行情快照

[](./quote-stock-cpp.md#get_brief-%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E8%A1%8C%E6%83%85%E5%BF%AB%E7%85%A7)

-------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_brief(const value &symbols, bool include_hour_trading, bool include_ask_bid, QuoteRight right)`

**说明**

获取股票的实时行情快照，包含最新价、开盘价、最高价、最低价等

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 标的代码数组，如 `value::array({value::string(U("AAPL"))})` |
| include\_hour\_trading | bool | No  | 是否包含盘前盘后数据，默认 false |
| include\_ask\_bid | bool | No  | 是否包含买卖盘口数据，默认 false |
| right | QuoteRight | No  | 复权类型，QuoteRight::br(前复权) 或 QuoteRight::nr(不复权)，默认 br |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value symbols = value::array();
    symbols[0] = value::string(U("AAPL"));
    symbols[1] = value::string(U("TSLA"));
    
    value result = quote_client.get_brief(symbols);
    ucout << result.serialize() << std::endl;

* * *

get\_stock\_detail 获取股票详情

[](./quote-stock-cpp.md#get_stock_detail-%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E8%AF%A6%E6%83%85)

--------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_stock_detail(const value &symbols, utility::string_t lang, utility::string_t sec_type)`

**说明**

获取股票的详细信息

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 标的代码数组 |
| lang | utility::string\_t | No  | 语言，如 U("zh\_CN")，默认空 |
| sec\_type | utility::string\_t | No  | 合约类型，默认空 |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value symbols = value::array();
    symbols[0] = value::string(U("AAPL"));
    
    value result = quote_client.get_stock_detail(symbols);
    ucout << result.serialize() << std::endl;

* * *

get\_timeline 获取分时数据

[](./quote-stock-cpp.md#get_timeline-%E8%8E%B7%E5%8F%96%E5%88%86%E6%97%B6%E6%95%B0%E6%8D%AE)

-----------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_timeline(const value &symbols, bool include_hour_trading, time_t begin_time)`

**说明**

获取当日分时数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 标的代码数组 |
| include\_hour\_trading | bool | No  | 是否包含盘前盘后数据，默认 false |
| begin\_time | time\_t | No  | 起始时间戳（毫秒），默认 -1 |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value symbols = value::array();
    symbols[0] = value::string(U("AAPL"));
    
    value result = quote_client.get_timeline(symbols);
    ucout << result.serialize() << std::endl;

* * *

get\_history\_timeline 获取历史分时数据

[](./quote-stock-cpp.md#get_history_timeline-%E8%8E%B7%E5%8F%96%E5%8E%86%E5%8F%B2%E5%88%86%E6%97%B6%E6%95%B0%E6%8D%AE)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_history_timeline(const value &symbols, utility::string_t date, QuoteRight right)`

**说明**

获取指定日期的历史分时数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 标的代码数组 |
| date | utility::string\_t | Yes | 日期，格式 "yyyy-MM-dd"，如 U("2024-01-15") |
| right | QuoteRight | No  | 复权类型，默认 QuoteRight::br |

**返回**

`web::json::value` JSON 对象

* * *

get\_kline 获取K线数据

[](./quote-stock-cpp.md#get_kline-%E8%8E%B7%E5%8F%96k%E7%BA%BF%E6%95%B0%E6%8D%AE)

---------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_kline(const value &symbols, BarPeriod period, time_t begin_time, time_t end_time, QuoteRight right, int limit, utility::string_t page_token)`

**说明**

获取股票K线数据，支持日K、周K、月K以及分钟K线

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 标的代码数组 |
| period | BarPeriod 或 utility::string\_t | No  | K线周期，如 BarPeriod::DAY 或 U("day")，默认 DAY。可选值：day/week/month/year/1min/3min/5min/10min/15min/30min/60min |
| begin\_time | time\_t | No  | 起始时间戳（毫秒），默认 -1 |
| end\_time | time\_t | No  | 结束时间戳（毫秒），默认 -1 |
| right | QuoteRight 或 utility::string\_t | No  | 复权类型，默认 QuoteRight::br 或 U("br") |
| limit | int | No  | 返回条数上限，默认 251 |
| page\_token | utility::string\_t | No  | 翻页标记，默认空 |

**返回**

`web::json::value` JSON 对象，或 `vector<Kline>` Kline 对象列表（使用对应的重载版本）

**Kline 对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| symbol | utility::string\_t | 标的代码 |
| period | utility::string\_t | K线周期 |
| items | vector<KlineItem> | K线数据列表 |

**KlineItem 对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| open | double | 开盘价 |
| high | double | 最高价 |
| low | double | 最低价 |
| close | double | 收盘价 |
| volume | long long | 成交量 |
| time | time\_t | 时间戳 |

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value symbols = value::array();
    symbols[0] = value::string(U("AAPL"));
    
    // 获取日K线（返回 JSON）
    value result = quote_client.get_kline(symbols, BarPeriod::DAY);
    ucout << result.serialize() << std::endl;
    
    // 获取日K线（返回 Kline 对象列表）
    vector<Kline> klines = quote_client.get_kline(symbols, U("day"));
    for (auto& kline : klines) {
        for (auto& item : kline.items) {
            std::cout << "Time: " << item.time << " Close: " << item.close << std::endl;
        }
    }

* * *

get\_quote\_real\_time 获取实时行情

[](./quote-stock-cpp.md#get_quote_real_time-%E8%8E%B7%E5%8F%96%E5%AE%9E%E6%97%B6%E8%A1%8C%E6%83%85)

---------------------------------------------------------------------------------------------------------------------------------------------------------------

`vector<RealtimeQuote> QuoteClient::get_quote_real_time(const value &symbols)`

**说明**

获取股票的实时行情数据，返回 RealtimeQuote 对象列表

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 标的代码数组 |

**返回**

`vector<RealtimeQuote>` 实时行情对象列表

**RealtimeQuote 对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| symbol | utility::string\_t | 标的代码 |
| open | double | 开盘价 |
| high | double | 最高价 |
| low | double | 最低价 |
| close | double | 收盘价 |
| pre\_close | double | 昨收价 |
| latest\_price | double | 最新价 |
| latest\_time | time\_t | 最新成交时间 |
| volume | long long | 成交量 |
| ask\_price | double | 卖一价 |
| ask\_size | double | 卖一量 |
| bid\_price | double | 买一价 |
| bid\_size | double | 买一量 |
| status | utility::string\_t | 市场状态 |

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value symbols = value::array();
    symbols[0] = value::string(U("AAPL"));
    symbols[1] = value::string(U("TSLA"));
    
    vector<RealtimeQuote> quotes = quote_client.get_quote_real_time(symbols);
    for (auto& q : quotes) {
        ucout << q.symbol << U(" latest_price: ") << q.latest_price << std::endl;
    }

* * *

get\_trade\_tick 获取逐笔成交

[](./quote-stock-cpp.md#get_trade_tick-%E8%8E%B7%E5%8F%96%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4)

----------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_trade_tick(const value &symbols, TradingSession trade_session, long begin_index, long end_index, int limit)`

**说明**

获取股票的逐笔成交数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 标的代码数组 |
| trade\_session | TradingSession 或 utility::string\_t | No  | 交易时段，默认 TradingSession::Regular |
| begin\_index | long | No  | 起始索引，默认 -1 |
| end\_index | long | No  | 结束索引，默认 -1 |
| limit | int | No  | 返回条数上限，默认 100 |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value symbols = value::array();
    symbols[0] = value::string(U("AAPL"));
    
    value result = quote_client.get_trade_tick(symbols);
    ucout << result.serialize() << std::endl;

* * *

get\_quote\_depth 获取深度行情

[](./quote-stock-cpp.md#get_quote_depth-%E8%8E%B7%E5%8F%96%E6%B7%B1%E5%BA%A6%E8%A1%8C%E6%83%85)

------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_quote_depth(const value &symbols, Market market)`

**说明**

获取股票的深度行情（买卖盘口）

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 标的代码数组 |
| market | Market | No  | 市场，默认 Market::US |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value symbols = value::array();
    symbols[0] = value::string(U("AAPL"));
    
    value result = quote_client.get_quote_depth(symbols, Market::US);
    ucout << result.serialize() << std::endl;

* * *

get\_stock\_broker 获取股票经纪商席位

[](./quote-stock-cpp.md#get_stock_broker-%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E7%BB%8F%E7%BA%AA%E5%95%86%E5%B8%AD%E4%BD%8D)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_stock_broker(utility::string_t symbol, int limit, utility::string_t lang, utility::string_t sec_type)`

**说明**

获取港股经纪商买卖席位数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | utility::string\_t | Yes | 港股代码，如 U("00700") |
| limit | int | No  | 返回条数，默认 40 |
| lang | utility::string\_t | No  | 语言，默认空 |
| sec\_type | utility::string\_t | No  | 合约类型，默认空 |

**返回**

`web::json::value` JSON 对象

* * *

get\_capital\_distribution 获取资金分布

[](./quote-stock-cpp.md#get_capital_distribution-%E8%8E%B7%E5%8F%96%E8%B5%84%E9%87%91%E5%88%86%E5%B8%83)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_capital_distribution(utility::string_t symbol, Market market, utility::string_t lang)`

**说明**

获取股票的资金分布数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | utility::string\_t | Yes | 标的代码 |
| market | Market | No  | 市场，默认 Market::US |
| lang | utility::string\_t | No  | 语言，默认空 |

**返回**

`web::json::value` JSON 对象

* * *

get\_capital\_flow 获取资金流向

[](./quote-stock-cpp.md#get_capital_flow-%E8%8E%B7%E5%8F%96%E8%B5%84%E9%87%91%E6%B5%81%E5%90%91)

--------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_capital_flow(utility::string_t symbol, Market market, CapitalPeriod period, time_t begin_time, time_t end_time, int limit)`

**说明**

获取股票的资金流向数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | utility::string\_t | Yes | 标的代码 |
| market | Market 或 utility::string\_t | No  | 市场，默认 Market::US |
| period | CapitalPeriod 或 utility::string\_t | No  | 周期，默认 CapitalPeriod::DAY。可选值：intraday/day/week/month/year/quarter/6month |
| begin\_time | time\_t | No  | 起始时间戳，默认 -1 |
| end\_time | time\_t | No  | 结束时间戳，默认 -1 |
| limit | int | No  | 返回条数，默认 200 |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value result = quote_client.get_capital_flow(U("AAPL"), Market::US, CapitalPeriod::DAY);
    ucout << result.serialize() << std::endl;
