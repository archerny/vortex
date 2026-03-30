# 基金

get\_fund\_symbols 获取基金代码列表

[](./quote-fund-cpp.md#get_fund_symbols-%E8%8E%B7%E5%8F%96%E5%9F%BA%E9%87%91%E4%BB%A3%E7%A0%81%E5%88%97%E8%A1%A8)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_fund_symbols()`

**说明**

获取所有基金代码列表

**参数**

无

**返回**

`web::json::value` JSON 对象

**示例**

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value result = quote_client.get_fund_symbols();
    ucout << result.serialize() << std::endl;

**返回示例**

    [\
            "IE00B11XZ988.USD",\
            "IE00B7SZLL34.SGD",\
            "LU0790902711.USD",\
            "LU0476943708.HKD",\
            "LU0098860793.USD",\
            "SG9999014039.USD"\
    ]

* * *

get\_fund\_contracts 获取基金合约信息

[](./quote-fund-cpp.md#get_fund_contracts-%E8%8E%B7%E5%8F%96%E5%9F%BA%E9%87%91%E5%90%88%E7%BA%A6%E4%BF%A1%E6%81%AF)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_fund_contracts(const value &symbols)`

**说明**

批量获取基金的合约信息

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 基金代码数组，如 `value::array({value::string(U("IE00B11XZ988.USD"))})` |

**返回**

`web::json::value` JSON 对象

其中数据项字段如下

| 名称  | 示例  | 说明  |
| --- | --- | --- |
| symbol | IE00B464Q616.USD | 基金代码，后缀为货币 |
| name | ASIA STRATEGIC INTEREST BOND FUND "E" (USD) INC MONTHLY | 基金名称 |
| company\_name | PIMCO Global Advisors (Ireland) Limited | 基金名称 |
| market | US  | 市场 /US/HK/CN |
| sec\_type | FUND | 合约类别 |
| currency | USD | USD/HKD/CNH |
| tradeable | true | 是否可交易 |
| sub\_type | Fixed Income | 子类别 |
| dividend\_type | INC | 分红类型 |
| tiger\_vault | false | 是否为老虎钱袋子 |

**示例**

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value symbols = value::array();
    symbols[0] = value::string(U("IE00B11XZ988.USD"));
    
    value result = quote_client.get_fund_contracts(symbols);
    ucout << result.serialize() << std::endl;

**返回示例**

                 symbol                                name         company_name    market  \
    0  IE00B11XZ988.USD             PIMCO总回报债券基金 E Acc   太平洋全球顾问有限公司        MF   \
    1  LU0476943708.HKD        邓普顿环球总收益基金A (Mdis)HKD         富兰克林邓普顿投资      MF  \
    2  SG9999017602.SGD  United Asian Bond Fund A Acc SGD-H          大华资产管理公司      MF   \
    
    sec_type     currency  tradeable      sub_type dividend_type  tiger_vault
        FUND          USD       True  Fixed Income           ACC        False
        FUND          HKD       True  Fixed Income           INC        False
        FUND          SGD       True  Fixed Income           ACC        False

* * *

get\_fund\_quote 获取基金最新行情

[](./quote-fund-cpp.md#get_fund_quote-%E8%8E%B7%E5%8F%96%E5%9F%BA%E9%87%91%E6%9C%80%E6%96%B0%E8%A1%8C%E6%83%85)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_fund_quote(const value &symbols)`

**说明**

获取基金最新行情

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 基金代码数组，上限为500个 |

**返回**

`web::json::value` JSON 对象

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 标的代码 |
| close | float | 收市价 |
| timestamp | int | 毫秒单位的时间戳 |

**示例**

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value symbols = value::array();
    symbols[0] = value::string(U("IE00B11XZ988.USD"));
    symbols[1] = value::string(U("LU0476943708.HKD"));
    
    value result = quote_client.get_fund_quote(symbols);
    ucout << result.serialize() << std::endl;

**返回示例**

                 symbol  close      timestamp
    0  IE00B11XZ988.USD  25.10  1691596800000
    1  LU0476943708.HKD   5.22  1691596800000

* * *

get\_fund\_history\_quote 获取基金历史行情

[](./quote-fund-cpp.md#get_fund_history_quote-%E8%8E%B7%E5%8F%96%E5%9F%BA%E9%87%91%E5%8E%86%E5%8F%B2%E8%A1%8C%E6%83%85)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_fund_history_quote(const value &symbols, time_t begin_time, time_t end_time, int limit)`

**说明**

获取基金历史行情

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 基金代码数组，上限为500个 |
| begin\_time | time\_t | Yes | 开始时间戳，单位:毫秒(ms) |
| end\_time | time\_t | Yes | 结束时间戳，单位:毫秒(ms) |
| limit | int | No  | 请求返回单个标的数据量 |

**返回**

`web::json::value` JSON 对象

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 标的代码 |
| nav | float | 净值  |
| time | int | 时间戳 |

**示例**

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value symbols = value::array();
    symbols[0] = value::string(U("LU0476943708.HKD"));
    
    value result = quote_client.get_fund_history_quote(symbols, 1691337600000, 1691596800000);
    ucout << result.serialize() << std::endl;

**返回示例**

                   symbol           time   nav
    0    LU0476943708.HKD  1691596800000  5.22
    1    LU0476943708.HKD  1691510400000  5.22
    2    LU0476943708.HKD  1691424000000  5.20
    3    LU0476943708.HKD  1691337600000  5.25
    
