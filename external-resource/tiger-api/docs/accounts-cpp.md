# 资产与持仓

get\_accounts 获取账户列表

[](./accounts-cpp.md#get_accounts-%E8%8E%B7%E5%8F%96%E8%B4%A6%E6%88%B7%E5%88%97%E8%A1%A8)

--------------------------------------------------------------------------------------------------------------------------------------------

`value TradeClient::get_accounts()`

**说明**

获取当前开发者名下的所有账户列表

**返回**

`web::json::value` JSON 数组，包含账户信息

**示例**

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    value accounts = trade_client.get_accounts();
    ucout << accounts.serialize() << std::endl;

* * *

get\_asset 获取账户资产

[](./accounts-cpp.md#get_asset-%E8%8E%B7%E5%8F%96%E8%B4%A6%E6%88%B7%E8%B5%84%E4%BA%A7)

--------------------------------------------------------------------------------------------------------------------------------------

`value TradeClient::get_asset(utility::string_t account, const value &sub_accounts, bool segment, bool market_value)`

**说明**

获取账户的资产信息（适用于综合账户/环球账户/模拟账户）

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | utility::string\_t | No  | 资金账号，不传使用默认账号 |
| sub\_accounts | value | No  | 子账号列表（机构用户），默认 value::array() |
| segment | bool | No  | 是否按分段返回资产，默认 false |
| market\_value | bool | No  | 是否包含持仓市值信息，默认 false |

**返回**

`web::json::value` JSON 对象

**返回属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| account | string | 资金账号 |
| segments | array | 账户分段信息列表 |
| ├─ category | string | 分段类别 S(证券)/C(期货) |
| ├─ currency | string | 币种  |
| ├─ netLiquidation | float | 净清算值 |
| ├─ equityWithLoan | float | 含借贷资产 |
| ├─ initMargin | float | 初始保证金 |
| ├─ maintainMargin | float | 维持保证金 |
| ├─ buyingPower | float | 购买力 |
| ├─ cashBalance | float | 现金  |
| ├─ grossPositionValue | float | 持仓总市值 |
| ├─ unrealizedPL | float | 未实现盈亏 |
| └─ realizedPL | float | 已实现盈亏 |

**示例**

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    // 获取全部资产
    value assets = trade_client.get_asset();
    ucout << assets.serialize() << std::endl;
    
    // 获取带分段信息的资产
    value assets_seg = trade_client.get_asset(config.account, value::array(), true);
    ucout << assets_seg.serialize() << std::endl;

* * *

get\_prime\_asset 获取 Prime 账户资产

[](./accounts-cpp.md#get_prime_asset-%E8%8E%B7%E5%8F%96-prime-%E8%B4%A6%E6%88%B7%E8%B5%84%E4%BA%A7)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------

`value TradeClient::get_prime_asset(const utility::string_t &account, const utility::string_t &base_currency)`

**说明**

获取环球账户（Prime）的资产信息，返回 JSON 格式

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | utility::string\_t | No  | 资金账号，默认使用配置账号 |
| base\_currency | utility::string\_t 或 Currency | No  | 基础币种，默认 U("USD") / Currency::USD |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    value prime_asset = trade_client.get_prime_asset();
    ucout << prime_asset.serialize() << std::endl;

* * *

get\_prime\_portfolio 获取 Prime 账户组合

[](./accounts-cpp.md#get_prime_portfolio-%E8%8E%B7%E5%8F%96-prime-%E8%B4%A6%E6%88%B7%E7%BB%84%E5%90%88)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`PortfolioAccount TradeClient::get_prime_portfolio(const utility::string_t &account, const utility::string_t &base_currency)`

**说明**

获取环球账户的组合资产信息，返回 `PortfolioAccount` 结构体（包含 Segment 和 CurrencyAsset 详细数据）

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | utility::string\_t | No  | 资金账号 |
| base\_currency | utility::string\_t | No  | 基础币种，默认 U("USD") |

**返回**

`PortfolioAccount` 对象

**PortfolioAccount 属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| account | utility::string\_t | 资金账号 |
| update\_timestamp | long | 更新时间戳 |
| segments | vector<Segment> | 分段列表 |

**Segment 属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| category | utility::string\_t | 分段类别 |
| capability | utility::string\_t | 账户能力 |
| currency | utility::string\_t | 币种  |
| buying\_power | double | 购买力 |
| cash\_available\_for\_trade | double | 可交易现金 |
| cash\_available\_for\_withdrawal | double | 可提现金额 |
| cash\_balance | double | 现金余额 |
| equity\_with\_loan | double | 含借贷资产 |
| excess\_liquidation | double | 剩余流动性 |
| gross\_position\_value | double | 持仓总市值 |
| init\_margin | double | 初始保证金 |
| maintain\_margin | double | 维持保证金 |
| net\_liquidation | double | 净清算值 |
| realized\_pl | double | 已实现盈亏 |
| unrealized\_pl | double | 未实现盈亏 |
| currency\_assets | vector<CurrencyAsset> | 分币种资产列表 |

**CurrencyAsset 属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| currency | utility::string\_t | 币种  |
| cash\_balance | double | 现金余额 |
| cash\_available\_for\_trade | double | 可交易现金 |
| gross\_position\_value | double | 持仓市值 |
| stock\_market\_value | double | 股票市值 |
| option\_market\_value | double | 期权市值 |
| realized\_pl | double | 已实现盈亏 |
| unrealized\_pl | double | 未实现盈亏 |

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    PortfolioAccount portfolio = trade_client.get_prime_portfolio();
    std::cout << "Account: " << portfolio.account << std::endl;
    for (auto& seg : portfolio.segments) {
        ucout << U("Segment: ") << seg.category
              << U(" Net Liquidation: ") << seg.net_liquidation
              << std::endl;
        for (auto& asset : seg.currency_assets) {
            ucout << U("  Currency: ") << asset.currency
                  << U(" Cash: ") << asset.cash_balance
                  << std::endl;
        }
    }

* * *

get\_positions 获取持仓

[](./accounts-cpp.md#get_positions-%E8%8E%B7%E5%8F%96%E6%8C%81%E4%BB%93)

--------------------------------------------------------------------------------------------------------------------------

`value TradeClient::get_positions(utility::string_t account, SecType sec_type, Currency currency, Market market, utility::string_t symbol, const value &sub_accounts, time_t expiry, utility::string_t strike, Right right)`

**说明**

获取账户的持仓信息。此方法提供多个重载版本，参数支持枚举类型和字符串类型。

**参数（枚举版本）**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | utility::string\_t | No  | 资金账号 |
| sec\_type | SecType | No  | 合约类型，默认 SecType::ALL |
| currency | Currency | No  | 币种，默认 Currency::ALL |
| market | Market | No  | 市场，默认 Market::ALL |
| symbol | utility::string\_t | No  | 标的代码，默认空 |
| sub\_accounts | value | No  | 子账号列表 |
| expiry | time\_t | No  | 期权到期日时间戳，默认 -1 |
| strike | utility::string\_t | No  | 期权行权价 |
| right | Right | No  | 期权方向，默认 Right::ALL |

**参数（字符串版本）**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | utility::string\_t | No  | 资金账号 |
| sec\_type | utility::string\_t | No  | 合约类型，如 U("STK") |
| currency | utility::string\_t | No  | 币种，默认 U("ALL") |
| market | utility::string\_t | No  | 市场，默认 U("ALL") |
| symbol | utility::string\_t | No  | 标的代码 |
| sub\_accounts | value | No  | 子账号列表 |
| expiry | time\_t | No  | 期权到期日时间戳 |
| strike | utility::string\_t | No  | 期权行权价 |
| right | utility::string\_t | No  | 期权方向，如 U("PUT")/U("CALL") |

**返回**

`web::json::value` JSON 数组，或 `vector<Position>` 持仓对象列表（使用 `get_position_list` 方法）

**Position 对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| account | utility::string\_t | 资金账号 |
| contract | Contract | 合约对象 |
| position | long long | 持仓数量 |
| average\_cost | double | 持仓成本 |
| latest\_price | double | 最新价格 |
| market\_value | double | 持仓市值 |
| unrealized\_pnl | double | 未实现盈亏 |
| realized\_pnl | double | 已实现盈亏 |

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    // 获取全部持仓（JSON 格式）
    value positions = trade_client.get_positions();
    ucout << positions.serialize() << std::endl;
    
    // 获取指定标的持仓（枚举版本）
    value pos_aapl = trade_client.get_positions(
        config.account,
        SecType::STK,
        Currency::ALL,
        Market::ALL,
        U("AAPL")
    );
    ucout << pos_aapl.serialize() << std::endl;
    
    // 获取持仓对象列表
    vector<Position> pos_list = trade_client.get_position_list();
    for (auto& pos : pos_list) {
        ucout << pos.contract.symbol << U(" position: ") << pos.position
              << U(" avg_cost: ") << pos.average_cost << std::endl;
    }

* * *

get\_analytics\_asset 获取资产分析

[](./accounts-cpp.md#get_analytics_asset-%E8%8E%B7%E5%8F%96%E8%B5%84%E4%BA%A7%E5%88%86%E6%9E%90)

-----------------------------------------------------------------------------------------------------------------------------------------------------------

`value TradeClient::get_analytics_asset(utility::string_t account, utility::string_t start_date, utility::string_t end_date, utility::string_t seg_type, utility::string_t currency, utility::string_t sub_account)`

**说明**

获取账户的资产分析数据，包含历史资产变动

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | utility::string\_t | Yes | 资金账号 |
| start\_date | utility::string\_t | Yes | 起始日期，格式 "yyyy-MM-dd" |
| end\_date | utility::string\_t | Yes | 结束日期，格式 "yyyy-MM-dd" |
| seg\_type | utility::string\_t | No  | 账户分段，U("SEC")/U("FUT")，默认 U("SEC") |
| currency | utility::string\_t | No  | 币种，默认 U("USD") |
| sub\_account | utility::string\_t | No  | 子账号，默认空 |

**返回**

`web::json::value` JSON 对象

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    value analytics = trade_client.get_analytics_asset(
        config.account,
        U("2024-01-01"),
        U("2024-06-30")
    );
    ucout << analytics.serialize() << std::endl;

* * *

get\_estimate\_tradable\_quantity 获取预估可交易数量

[](./accounts-cpp.md#get_estimate_tradable_quantity-%E8%8E%B7%E5%8F%96%E9%A2%84%E4%BC%B0%E5%8F%AF%E4%BA%A4%E6%98%93%E6%95%B0%E9%87%8F)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value TradeClient::get_estimate_tradable_quantity(Order &order, utility::string_t seg_type)`

**说明**

预估当前账户对指定标的可交易的最大数量。需要先构建一个 Order 对象传入。

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| order | Order& | Yes | 订单对象（包含合约、方向、价格等信息） |
| seg\_type | utility::string\_t | No  | 账户分段，默认 U("SEC") |

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
    
    Contract contract = ContractUtil::stock_contract(U("AAPL"), U("USD"));
    Order order = OrderUtil::limit_order(config.account, contract, U("BUY"), 100, 150.0);
    
    value result = trade_client.get_estimate_tradable_quantity(order);
    ucout << result.serialize() << std::endl;
