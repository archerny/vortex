# 获取合约

合约介绍

[](./get-contract-cpp.md#%E5%90%88%E7%BA%A6%E4%BB%8B%E7%BB%8D)

-------------------------------------------------------------------------------------------------

合约是指交易的买卖对象或者标的物（比如一只股票或一只期权），合约是由交易所统一制定的。比如购买老虎证券的股票，可以通过TIGR这个字母代号和市场信息（即market='US'，美国市场）来唯一标识。通过合约信息，我们在下单或者获取行情时就可以唯一的确定一个标的物。常见的合约包括股票合约，期权合约，期货合约等。

大多数合约（如：股票，差价合约，指数或外汇）可通过以下**四个基础**属性唯一确定：

*   标的代码 (symbol)：一般美股、英股等合约代码都是英文字母，港股、A股等合约代码是数字，比如老虎证券的symbol是TIGR。
*   合约类型 (security type)：常见合约类型包括：STK（股票），OPT（期权），FUT（期货），CASH（外汇），比如老虎证券股票的合约类型是STK。
*   货币类型 (currency)：常见货币包括 USD（美元），HKD（港币）。
*   交易所 (exchange)：STK类型的合约一般不会用到交易所字段，订单会自动路由，期货合约都用到交易所字段。

* * *

get\_contract 获取单个合约信息

[](./get-contract-cpp.md#get_contract-%E8%8E%B7%E5%8F%96%E5%8D%95%E4%B8%AA%E5%90%88%E7%BA%A6%E4%BF%A1%E6%81%AF)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value TradeClient::get_contract(utility::string_t symbol, utility::string_t sec_type, utility::string_t currency, utility::string_t exchange, time_t expiry, utility::string_t strike, utility::string_t right)`

`value TradeClient::get_contract(utility::string_t symbol, SecType sec_type, Currency currency, utility::string_t exchange, time_t expiry, utility::string_t strike, Right right)`

**说明**

查询交易所需的单个合约信息。此方法提供字符串和枚举两种重载版本。

**参数（字符串版本）**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | utility::string\_t | Yes | 股票代码，如 U("AAPL") |
| sec\_type | utility::string\_t | Yes | 证券类型，如 U("STK")、U("OPT")、U("FUT") |
| currency | utility::string\_t | No  | 币种，如 U("USD")、U("HKD") |
| exchange | utility::string\_t | No  | 交易所，如 U("CBOE") |
| expiry | time\_t | No  | 合约到期日时间戳（毫秒），默认 -1 |
| strike | utility::string\_t | No  | 行权价（适用于期权） |
| right | utility::string\_t | No  | 看涨看跌（适用于期权），U("PUT") 看跌，U("CALL") 看涨 |

**参数（枚举版本）**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | utility::string\_t | Yes | 股票代码 |
| sec\_type | SecType | No  | 证券类型，默认 SecType::STK |
| currency | Currency | No  | 币种，默认 Currency::ALL |
| exchange | utility::string\_t | No  | 交易所 |
| expiry | time\_t | No  | 合约到期日时间戳，默认 -1 |
| strike | utility::string\_t | No  | 行权价 |
| right | Right | No  | 期权方向，默认 Right::ALL |

**返回**

`web::json::value` JSON 对象，包含合约信息

**对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| identifier | string | 唯一标识，股票identifier和symbol相同，期权为21位标识符 |
| symbol | string | 股票代码 |
| sec\_type | string | STK 股票/OPT 期权/FUT 期货/WAR 窝轮/IOPT 牛熊证等 |
| name | string | 合约名称 |
| currency | string | 币种，如：USD/HKD/CNH |
| exchange | string | 交易所 |
| expiry | string | 期权和期货专有，过期日 |
| strike | float | 期权专有，行权价格 |
| multiplier | float | 乘数，每手对应的数量 |
| put\_call | string | 期权专有，CALL 或者 PUT |
| market | string | 市场，如：US/HK/CN |
| min\_tick | float | 最小报价单位 |
| tickSizes | array | 最小报价单位价格区间 |
| shortable | bool | 是否可做空 |
| marginable | bool | 是否可融资 |
| lot\_size | float | 单笔交易中可交易的最小资产数量 |

**示例**

C++

    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    // 股票合约
    value contract = trade_client.get_contract(U("AAPL"), U("STK"));
    ucout << contract.serialize() << std::endl;
    
    // 期货合约
    value fut_contract = trade_client.get_contract(U("ES2306"), U("FUT"));
    ucout << fut_contract.serialize() << std::endl;

**返回示例**

JSON

    {
      "contractId": 1916,
      "symbol": "AAPL",
      "currency": "USD",
      "secType": "STK",
      "exchange": null,
      "localSymbol": "AAPL",
      "multiplier": 1.0,
      "name": "苹果",
      "shortInitialMargin": 0.35,
      "shortMaintenanceMargin": 0.3,
      "longInitialMargin": 0.3,
      "longMaintenanceMargin": 0.25,
      "identifier": "AAPL",
      "primaryExchange": "NASDAQ",
      "market": "US",
      "tickSizes": [\
        {"begin": "0", "end": "1", "type": "CLOSED", "tickSize": 0.0001},\
        {"begin": "1", "end": "Infinity", "type": "OPEN", "tickSize": 0.01}\
      ],
      "tradingClass": "AAPL",
      "status": 1,
      "marginable": true,
      "trade": true,
      "lotSize": 1.0
    }

* * *

get\_contracts 获取多个合约信息

[](./get-contract-cpp.md#get_contracts-%E8%8E%B7%E5%8F%96%E5%A4%9A%E4%B8%AA%E5%90%88%E7%BA%A6%E4%BF%A1%E6%81%AF)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value TradeClient::get_contracts(const value &symbols, utility::string_t sec_type, utility::string_t currency, utility::string_t exchange, time_t expiry, utility::string_t strike, utility::string_t right)`

**说明**

查询交易所需的多个合约信息，以 JSON 数组形式返回

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 股票代码数组。单次请求上限为50 |
| sec\_type | utility::string\_t | No  | 证券类型，默认 U("STK") |
| currency | utility::string\_t | No  | 币种  |
| exchange | utility::string\_t | No  | 交易所 |
| expiry | time\_t | No  | 合约到期日时间戳，默认 -1 |
| strike | utility::string\_t | No  | 行权价 |
| right | utility::string\_t | No  | 期权方向 |

**返回**

`web::json::value` JSON 数组

**示例**

C++

    ClientConfig config(false, U("/path/to/your/properties/"));
    TradeClient trade_client(config);
    
    value symbols = value::array();
    symbols[0] = value::string(U("AAPL"));
    symbols[1] = value::string(U("TSLA"));
    
    value contracts = trade_client.get_contracts(symbols, U("STK"));
    ucout << contracts.serialize() << std::endl;

* * *

本地生成合约对象

[](./get-contract-cpp.md#%E6%9C%AC%E5%9C%B0%E7%94%9F%E6%88%90%E5%90%88%E7%BA%A6%E5%AF%B9%E8%B1%A1)

-----------------------------------------------------------------------------------------------------------------------------------------

C++ SDK 提供 `ContractUtil` 工具类，可以在本地直接构建合约对象，无需发起网络请求。

**股票**

C++

    #include "tigerapi/contract_util.h"
    
    using namespace TIGER_API;
    
    // 美股
    Contract contract = ContractUtil::stock_contract(U("TIGR"), U("USD"));
    
    // 港股
    Contract contract = ContractUtil::stock_contract(U("00700"), U("HKD"));

**期权**

C++

    #include "tigerapi/contract_util.h"
    
    using namespace TIGER_API;
    
    // 方式一：使用四要素
    Contract contract = ContractUtil::option_contract(
        U("AAPL"),          // symbol
        U("20240621"),       // expiry
        U("190"),            // strike
        U("CALL"),           // right
        U("USD"),            // currency
        100                  // multiplier
    );
    
    // 方式二：使用 OCC 标识符
    Contract contract = ContractUtil::option_contract(U("AAPL  240621C00190000"));
    
    // 解析期权标识符
    auto [symbol, expiry, right, strike] = ContractUtil::extract_option_info(U("AAPL  240621C00190000"));

**期货**

C++

    #include "tigerapi/contract_util.h"
    
    using namespace TIGER_API;
    
    // 使用合约代码
    Contract contract = ContractUtil::future_contract(U("CL2312"), U("USD"));
    
    // 使用完整参数
    Contract contract = ContractUtil::future_contract(
        U("CL"),             // symbol
        U("USD"),            // currency
        U("20231220"),       // expiry
        U("NYMEX"),          // exchange
        U("202312"),         // contract_month
        1000                 // multiplier
    );

Contract 对象

[](./get-contract-cpp.md#contract-%E5%AF%B9%E8%B1%A1)

-----------------------------------------------------------------------------------------------

C++ SDK 的 `Contract` 结构体包含以下主要属性：

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| contract\_id | long | 合约ID |
| symbol | utility::string\_t | 标的代码 |
| sec\_type | utility::string\_t | 合约类型 |
| currency | utility::string\_t | 币种  |
| exchange | utility::string\_t | 交易所 |
| market | utility::string\_t | 市场  |
| expiry | utility::string\_t | 过期日 |
| strike | utility::string\_t | 行权价 |
| right | utility::string\_t | 期权方向 PUT/CALL |
| multiplier | int | 乘数  |
| contract\_month | utility::string\_t | 合约月份（期货） |
| local\_symbol | utility::string\_t | 本地标识 |
| identifier | utility::string\_t | 唯一标识 |
