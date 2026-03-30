# 选股器

market\_scanner 选股器

[](./quote-scanner-cpp.md#market_scanner-%E9%80%89%E8%82%A1%E5%99%A8)

-----------------------------------------------------------------------------------------------------------------------

`value QuoteClient::market_scanner(utility::string_t market, const value &filters, const value &sort_field_data, int page, int page_size, utility::string_t cursor_id)`

**说明**

通过不同的技术指标条件来扫描全市场行情，帮助您筛选出满足特定投资需求的标的列表。

技术指标条件包含如下几类：基础指标、累积指标、财务指标，多标签指标，具体参数含义请参考下面的说明。

**参数**

| 参数名 | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| market | utility::string\_t | Yes | US 美股，SG 新股，HK港股 |
| filters | value (JSON array) | Yes | 过滤器列表，共有四类，见下方说明 |
| sort\_field\_data | value (JSON object) | No  | 排序字段对象，主要属性，如下所示 |
| ∟ field | utility::string\_t | No  | 排序字段，StockField / AccumulateField / FinancialField / MultiTagField 中的字段字符串 |
| ∟ sort\_dir | utility::string\_t | No  | 排序方向，包括：不排序，升序（`SortDir_Ascend`），降序（`SortDir_Descend`） |
| page | int | No  | 当前页码（从0开始). 不推荐使用，请使用 cursor\_id |
| cursor\_id | utility::string\_t | No  | 游标ID，用于基于游标的分页查询，客户端在获取下一页时应将此值作为请求参数传入，首次查询可传空字符串 |
| page\_size | int | No  | 每页返回的数据量，最大支持配置成：200 |

StockFilter 参数说明：

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| field | utility::string\_t | Yes | 共四类 field，见下方说明 |
| filter\_min | double | No  | 区间下限（闭区间），不传代表下限为 -∞ 如果为百分位数，不需要加%，例如10%，数值为10即可 |
| filter\_max | double | No  | 区间上限（闭区间），不传代表上限为 +∞ |
| is\_no\_filter | bool | No  | 是否禁用本Filter，如果为true，则本过滤器不生效 |
| accumulate\_period | utility::string\_t | No  | 累计周期，仅当field为 AccumulateField 时需要指定 |
| financial\_period | utility::string\_t | No  | 财务周期，仅当field为 FinancialField 时需要指定 |
| tag\_list | value (JSON array) | No  | 标签列表，仅当field为MultiTagField时需要指定 |

StockFilter 的field字段有如下枚举类型（以字符串形式传入）

| 类型  | 说明  |
| --- | --- |
| StockField | 简单指标筛选条件，包括价格（高开低收，最新价等），成交量，股本，市值，涨跌幅，市盈率，换手率等因子。筛选字段含义说明： [筛选字段说明](./appendix-enum.md#/stockfield) |
| AccumulateField | 累积指标筛选条件，包括累积涨跌幅，资产增长率，净利润增长率，每股收益，净利润，营业利润，营业收入，ROA（净资产收益率），经营现金流，资产负债率等。累积指标周期可以是：近五分钟，近5日，10日，20日，近半年，一年，两年，五年，一季度报，三季度报，中报等等。筛选字段含义说明：[筛选字段说明](./appendix-enum.md#accumulatefield) |
| FinancialField | 财务指标筛选条件，包括毛利，净利率，总负债/股东权益，总负债/总资产，流动比率，资产回报率，净利润，经营现金流，总资产，港股通净买入额，年化收益率等等。财务指标目前只支持LTM（最近12个月的年报指标）类型的财报查询。筛选字段含义说明：[筛选字段说明](./appendix-enum.md#financialfield) |
| MultiTagField | 多标签关联关系筛选条件，基于行业，概念，历史股价新高（当天股价和历史价格相比），52周内股价新高（当天股价和最近52周相比），是否为OTC，是否支持期权，股票类型（股票，ETF），是否破发等指标来选股。筛选字段含义说明：[筛选字段说明](./appendix-enum.md#multitagfield) |

> 筛选参数中的价格字段对应的币种和该标的所在市场的货币类型保持一致，比如美股：USD，港股：HKD，新股：SGD 等。

**返回**

`web::json::value` JSON 对象

结构如下:

JSON

    {
      "page": 0,
      "totalPage": 208,
      "totalCount": 1040,
      "pageSize": 5,
      "cursorId": "xxxxxx",
      "items": [\
        {\
          "symbol": "DNP",\
          "market": "US",\
          "fieldData": { ... }\
        }\
      ],
      "symbols": ["FEN", "DNP", "FDUS", "KYN", "TYG"]
    }

**示例**

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    using namespace web::json;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    // 股票基本数据过滤(is_no_filter为true时表示不启用该过滤器)
    value base_filter1 = value::object();
    base_filter1[U("fieldName")] = value::string(U("FloatShare"));
    base_filter1[U("filterType")] = value::string(U("StockField"));
    base_filter1[U("filterMin")] = value::number(1e7);
    base_filter1[U("filterMax")] = value::number(1e13);
    base_filter1[U("isNoFilter")] = value::boolean(true);
    
    value base_filter2 = value::object();
    base_filter2[U("fieldName")] = value::string(U("MarketValue"));
    base_filter2[U("filterType")] = value::string(U("StockField"));
    base_filter2[U("filterMin")] = value::number(1e8);
    base_filter2[U("filterMax")] = value::number(1e14);
    base_filter2[U("isNoFilter")] = value::boolean(false);
    
    // 周期累计数据过滤
    value accumulate_filter = value::object();
    accumulate_filter[U("fieldName")] = value::string(U("ChangeRate"));
    accumulate_filter[U("filterType")] = value::string(U("AccumulateField"));
    accumulate_filter[U("filterMin")] = value::number(0.01);
    accumulate_filter[U("filterMax")] = value::number(1.0);
    accumulate_filter[U("isNoFilter")] = value::boolean(false);
    accumulate_filter[U("accumulatePeriod")] = value::string(U("Last_Year"));
    
    // 财务数据过滤
    value financial_filter = value::object();
    financial_filter[U("fieldName")] = value::string(U("LYR_PE"));
    financial_filter[U("filterType")] = value::string(U("FinancialField"));
    financial_filter[U("filterMin")] = value::number(1.0);
    financial_filter[U("filterMax")] = value::number(100.0);
    financial_filter[U("isNoFilter")] = value::boolean(false);
    financial_filter[U("financialPeriod")] = value::string(U("LTM"));
    
    // 多标签数据过滤
    value multi_tag_filter = value::object();
    multi_tag_filter[U("fieldName")] = value::string(U("isOTC"));
    multi_tag_filter[U("filterType")] = value::string(U("MultiTagField"));
    value tag_list = value::array();
    tag_list[0] = value::string(U("BK4209"));
    multi_tag_filter[U("tagList")] = tag_list;
    
    // 构建 filters 数组
    value filters = value::array();
    filters[0] = base_filter1;
    filters[1] = base_filter2;
    filters[2] = accumulate_filter;
    filters[3] = financial_filter;
    filters[4] = multi_tag_filter;
    
    // 排序字段
    value sort_field_data = value::object();
    sort_field_data[U("fieldName")] = value::string(U("FloatShare"));
    sort_field_data[U("filterType")] = value::string(U("StockField"));
    sort_field_data[U("sortDir")] = value::string(U("SortDir_Ascend"));
    
    value result = quote_client.market_scanner(U("US"), filters, sort_field_data, 0, 50, U(""));
    ucout << result.serialize() << std::endl;

**返回示例**

JSON

    {
      "page": 0,
      "totalPage": 208,
      "totalCount": 1040,
      "pageSize": 5,
      "cursorId": "xxxxxx",
      "items": [\
        {\
          "symbol": "DNP",\
          "market": "US",\
          "fieldData": {\
            "FloatShare": 0.0,\
            "MarketValue": 3855828898.39,\
            "ChangeRate": 0.043925,\
            "LYR_PE": 7.359675,\
            "isOTC": "0"\
          }\
        },\
        {\
          "symbol": "FEN",\
          "market": "US",\
          "fieldData": {\
            "FloatShare": 0.0,\
            "MarketValue": 278571284.64,\
            "ChangeRate": 0.063893,\
            "LYR_PE": 6.45728,\
            "isOTC": "0"\
          }\
        },\
        {\
          "symbol": "FDUS",\
          "market": "US",\
          "fieldData": {\
            "FloatShare": 0.0,\
            "MarketValue": 462844356.0,\
            "ChangeRate": 0.079202,\
            "LYR_PE": 3.986464,\
            "isOTC": "0"\
          }\
        },\
        {\
          "symbol": "KYN",\
          "market": "US",\
          "fieldData": {\
            "FloatShare": 0.0,\
            "MarketValue": 1181621680.4,\
            "ChangeRate": 0.122898,\
            "LYR_PE": 3.268946,\
            "isOTC": "0"\
          }\
        },\
        {\
          "symbol": "TYG",\
          "market": "US",\
          "fieldData": {\
            "FloatShare": 0.0,\
            "MarketValue": 381692896.0,\
            "ChangeRate": 0.180812,\
            "LYR_PE": 2.853998,\
            "isOTC": "0"\
          }\
        }\
      ],
      "symbols": ["FEN", "DNP", "FDUS", "KYN", "TYG"]
    }

**示例1** 筛选股息率大于 5%， 营收3年复合增长率大于 10% 的股票

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    using namespace web::json;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    // 股息率大于 5%
    value base_filter = value::object();
    base_filter[U("fieldName")] = value::string(U("DivideRate"));
    base_filter[U("filterType")] = value::string(U("StockField"));
    base_filter[U("filterMin")] = value::number(0.05);
    
    // 营业收入三年增长率 或者叫 营收3年复合增长率
    value financial_filter = value::object();
    financial_filter[U("fieldName")] = value::string(U("TotalRevenues3YrCagr"));
    financial_filter[U("filterType")] = value::string(U("FinancialField"));
    financial_filter[U("filterMin")] = value::number(0.1);
    
    value filters = value::array();
    filters[0] = base_filter;
    filters[1] = financial_filter;
    
    value result = quote_client.market_scanner(U("US"), filters, value::null(), 0, 50, U(""));
    ucout << result.serialize() << std::endl;

**示例2 按 ETF 类型筛选**

tag\_list 里可选的 ETF 类型的标签值

Python

    热门关注: package_us_v1_etf_hot
    银行ETF: package_us_v1_etf_bank
    债券ETF: package_us_v1_etf_bond
    缓冲型: package_us_v1_etf_buffer
    宽基指数型: package_us_v1_etf_index
    杠杆&反向型: package_us_v1_etf_leverage
    行业型: package_us_v1_etf_sector
    单只股票杠杆型: package_us_v1_etf_single_stock
    Cap型: package_us_v1_etf_market_cap
    主题类: package_us_v1_etf_thematic
    国际市场: package_us_v1_etf_international
    成长&价值型: package_us_v1_etf_growth
    大宗商品型: package_us_v1_etf_commodity
    ARK木头姐: package_us_v1_etf_ark
    波动率: package_us_v1_etf_volatility
    汇率型: package_us_v1_etf_currency
    另类投资: package_us_v1_etf_alternative

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    using namespace web::json;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    // 按ETF类型筛选,这里筛选成长型和另类投资型ETF
    value multi_tag_filter_etftype = value::object();
    multi_tag_filter_etftype[U("fieldName")] = value::string(U("ETF_TYPE"));
    multi_tag_filter_etftype[U("filterType")] = value::string(U("MultiTagField"));
    value etf_tag_list = value::array();
    etf_tag_list[0] = value::string(U("package_us_v1_etf_growth"));
    etf_tag_list[1] = value::string(U("package_us_v1_etf_alternative"));
    multi_tag_filter_etftype[U("tagList")] = etf_tag_list;
    
    value filters = value::array();
    filters[0] = multi_tag_filter_etftype;
    
    value result = quote_client.market_scanner(U("US"), filters, value::null(), 0, 50, U(""));
    ucout << result.serialize() << std::endl;

* * *

get\_market\_scanner\_tags

[](./quote-scanner-cpp.md#get_market_scanner_tags)

-----------------------------------------------------------------------------------------------------------

`value QuoteClient::get_market_scanner_tags(utility::string_t market, const value &tag_fields)`

**说明**

获取多标签关联筛选字段的标签值，暂只支持获取行业和概念标签集合。

**参数**

| 参数名 | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| market | utility::string\_t | Yes | US 美股，SG 新股，HK港股 |
| tag\_fields | value (JSON array) | Yes | 支持的字段字符串值：`U("Industry")`，`U("Concept")` |

**返回**

`web::json::value` JSON 数组，其中每一项如下

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| market | string | 市场代码（US:美股，CN:沪深，HK:港股） |
| multi\_tag\_field | string | 多标签字段 |
| tag\_list | array | 多标签字段可以用来过滤的标签集合 |

**示例**

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    using namespace web::json;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value tag_fields = value::array();
    tag_fields[0] = value::string(U("Concept"));
    tag_fields[1] = value::string(U("Industry"));
    
    value result = quote_client.get_market_scanner_tags(U("US"), tag_fields);
    ucout << result.serialize() << std::endl;

**返回示例**

JSON

    [\
        {\
            "market": "US",\
            "multi_tag_field": "MultiTagField_Concept",\
            "tag_list": [\
                {\
                    "tag": "BK4565",\
                    "value": "NFT概念"\
                },\
                {\
                    "tag": "BK4564",\
                    "value": "太空概念"\
                },\
                {\
                    "tag": "BK4567",\
                    "value": "ESG概念"\
                },\
                {\
                    "tag": "BK4566",\
                    "value": "资本集团"\
                },\
                {\
                    "tag": "BK4568",\
                    "value": "美国抗疫概念"\
                },\
                {\
                    "tag": "BK4561",\
                    "value": "索罗斯持仓"\
                },\
                {\
                    "tag": "BK4560",\
                    "value": "网络安全概念"\
                }\
            ]\
        }\
    ]
