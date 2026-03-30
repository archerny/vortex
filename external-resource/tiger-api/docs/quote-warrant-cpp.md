# 窝轮牛熊证

get\_warrant\_filter 轮证筛选器

[](./quote-warrant-cpp.md#get_warrant_filter-%E8%BD%AE%E8%AF%81%E7%AD%9B%E9%80%89%E5%99%A8)

----------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_warrant_filter(const utility::string_t symbol, int page, int page_size, utility::string_t sort_field_name, utility::string_t sort_dir, value filter_params)`

**说明**

获取窝轮牛熊证行情列表数据，支持按不同字段排序和筛选轮证。

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | utility::string\_t | Yes | 正股股票代码 |
| page | int | No  | 页码，从0开始，默认为0 |
| page\_size | int | No  | 每页数量，默认50 |
| sort\_field\_name | utility::string\_t | No  | 排序字段，默认expireDate（参照返回对象中的字段） |
| sort\_dir | utility::string\_t | No  | 排序顺序，U("ASC") 或 U("DESC")，默认 ASC |
| filter\_params | value | No  | 过滤参数 JSON 对象，默认 value::null() |

**filter\_params 过滤参数**

| 字段名 | 类型  | 描述  |
| --- | --- | --- |
| issuer\_name | string | 发行商（参照返回中bounds的issuerName字段），默认全部 |
| expire\_ym | string | 到期日，格式为 yyyy-MM |
| state | int | 状态，0 全部, 1 正常, 2 终止交易, 3 等待上市，默认0 |
| warrant\_type | array\[int\] | 类型（1:Call, 2: Put, 3: Bull, 4: Bear, 0: All），默认全部 |
| in\_out\_price | array\[int\] | 1：价内（包含平值），-1：价外 |
| lot\_size | array\[int\] | 每手股数 |
| entitlement\_ratio | array\[double\] | 换股比率 |
| strike\_min | double | 行权价区间最小值 |
| strike\_max | double | 行权价区间最大值 |
| effective\_leverage\_min | double | 有效杠杆区间最小值 |
| effective\_leverage\_max | double | 有效杠杆区间最大值 |
| leverage\_ratio\_min | double | 杠杆比例区间最小值 |
| leverage\_ratio\_max | double | 杠杆比例区间最大值 |
| call\_price\_min | double | 回收价区间最小值 |
| call\_price\_max | double | 回收价区间最大值 |
| volume\_min | int | 成交量区间最小值 |
| volume\_max | int | 成交量区间最大值 |
| premium\_min | double | 溢价区间最小值 |
| premium\_max | double | 溢价区间最大值 |
| outstanding\_ratio\_min | double | 街货比区间最小值 |
| outstanding\_ratio\_max | double | 街货比区间最大值 |
| implied\_volatility\_min | double | 隐含波动率区间最小值 |
| implied\_volatility\_max | double | 隐含波动率区间最大值 |

**返回**

`web::json::value` JSON 对象

返回字段说明：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| page | int | 页码  |
| totalPage | int | 总页数 |
| totalCount | int | 数据总数 |
| bounds | object | 请求可设置的过滤条件，说明见下文 |
| items | array | 轮证数据列表，字段见下方说明 |

bounds 对象结构：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| issuer\_name | array\[string\] | 发行商名称 |
| expire\_date | array\[string\] | 到期日 |
| lot\_size | array\[int\] | 每手股数 |
| entitlement\_ratio | array\[double\] | 换股比率 |
| leverage\_ratio | object | 杠杆比率范围 |
| strike | object | 行权价范围 |
| premium | object | 溢价范围 |
| outstanding\_ratio | object | 街货比范围 |
| implied\_volatility | object | 隐含波动率范围 |
| effective\_leverage | object | 有效杠杆范围 |
| call\_price | object | 回收价范围 |

items 数组中每个元素的字段说明：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 标的股票代码 |
| name | string | 标的名称 |
| type | int | 类型，1：认购，2：认沽，3：牛证，4：熊证 |
| sec\_type | string | 合约类型，窝轮：war/牛熊证：iopt |
| market | string | 市场，hk |
| entitlement\_ratio | double | 换股比率 |
| entitlement\_price | double | 换股价 |
| premium | double | 溢价  |
| breakeven\_point | double | 到期盈亏平衡点 |
| call\_price | double | 回收价（仅牛熊证） |
| before\_call\_level | double | 距回收价（百分比，比如0.196875，含义为19.6875%） |
| expire\_date | string | 到期日 |
| last\_trading\_date | string | 最后交易日 |
| state | int | 状态，1 正常, 2 终止交易, 3 等待上市 |
| change\_rate | double | 涨跌幅 |
| change | double | 涨跌额 |
| latest\_price | double | 最新价 |
| volume | long | 成交量 |
| outstanding\_ratio | double | 街货比 |
| lot\_size | int | 每手股数 |
| strike | double | 行权价 |
| in\_out\_price | double | 价内（大于0）/价外（小于0） |
| delta | double | 对冲值 |
| leverage\_ratio | double | 杠杆比率 |
| effective\_leverage | double | 有效杠杆 |
| implied\_volatility | double | 隐含波动率 |

**示例**

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    // 构造过滤参数
    value filter_params = value::object();
    filter_params[U("issuer_name")] = value::string(U("摩利"));
    filter_params[U("expire_ym")] = value::string(U("2024-06"));
    // 0 All, 1 Normal, 2 Terminate Trades, 3 Waiting to be listed
    filter_params[U("state")] = value::number(1);
    
    value lot_size_arr = value::array();
    lot_size_arr[0] = value::number(1000);
    lot_size_arr[1] = value::number(5000);
    filter_params[U("lot_size")] = lot_size_arr;
    
    // -1: out the money, 1: in the money
    value in_out_price_arr = value::array();
    in_out_price_arr[0] = value::number(1);
    in_out_price_arr[1] = value::number(-1);
    filter_params[U("in_out_price")] = in_out_price_arr;
    
    // 1: Call, 2: Put, 3: Bull, 4: Bear, 0: All
    value warrant_type_arr = value::array();
    warrant_type_arr[0] = value::number(2);
    warrant_type_arr[1] = value::number(4);
    filter_params[U("warrant_type")] = warrant_type_arr;
    
    filter_params[U("premium_min")] = value::number(0.0);
    filter_params[U("premium_max")] = value::number(1.0);
    filter_params[U("strike_min")] = value::number(100.0);
    filter_params[U("strike_max")] = value::number(500.0);
    filter_params[U("implied_volatility_min")] = value::number(1.0);
    filter_params[U("implied_volatility_max")] = value::number(100.0);
    
    value result = quote_client.get_warrant_filter(
        U("00700"), 0, 10,
        U("implied_volatility"), U("DESC"),
        filter_params);
    ucout << result.serialize() << std::endl;

**返回示例**

JSON

    {
      "page": 0,
      "totalPage": 1,
      "totalCount": 1,
      "bounds": {
        "issuerName": ["东亚", "法巴", "法兴", "高盛", "国君", "海通", "花旗", "汇丰", "麦银", "摩利", "摩通", "瑞通", "瑞信", "瑞银", "星展", "中银"],
        "expireDate": ["2026-01", "2025-12", "2025-09", "2025-08", "2024-12", "2024-07", "2024-06", "2024-04", "2024-03", "2024-02", "2024-01", "2023-12", "2023-11", "2023-10", "2023-09", "2023-08", "2023-07", "2023-06", "2023-05", "2023-04"],
        "lotSize": [1000, 5000, 10000, 50000],
        "entitlementRatio": [1.053, 47.483, 50.0, 92.166, 94.967, 100.0, 460.829, 474.834, 485.437, 500.0],
        "leverageRatio": {"min": 1.391437, "max": 3056.79342},
        "strike": {"min": 111.301, "max": 717.11},
        "premium": {"min": -0.314923, "max": 0.908085},
        "outstandingRatio": {"min": 0.0, "max": 1.0},
        "impliedVolatility": {"min": 0.0, "max": 131.085},
        "effectiveLeverage": {"min": -46.321801, "max": 20.189945},
        "callPrice": {"min": 113.96, "max": 520.0}
      },
      "items": [\
        {\
          "symbol": "27062",\
          "name": "腾讯摩利三十沽A.P",\
          "type": 2,\
          "secType": "WAR",\
          "market": "HK",\
          "entitlementRatio": 47.483,\
          "entitlementPrice": 1.377007,\
          "premium": 0.605231,\
          "breakevenPoint": 148.669993,\
          "expireDate": "2023-10-20",\
          "lastTradingDate": "2023-10-16",\
          "state": 1,\
          "changeRate": 0.035714,\
          "change": 0.001,\
          "latestPrice": 0.029,\
          "volume": 0,\
          "outstandingRatio": 0.002,\
          "lotSize": 5000,\
          "strike": 150.047,\
          "inOutPrice": 1.50988,\
          "delta": -0.030389,\
          "leverageRatio": 273.491711,\
          "effectiveLeverage": -8.311172,\
          "impliedVolatility": 69.847\
        }\
      ]
    }

* * *

get\_warrant\_briefs 获取轮证行情

[](./quote-warrant-cpp.md#get_warrant_briefs-%E8%8E%B7%E5%8F%96%E8%BD%AE%E8%AF%81%E8%A1%8C%E6%83%85)

--------------------------------------------------------------------------------------------------------------------------------------------------------------

`value QuoteClient::get_warrant_briefs(const value &symbols)`

**说明**

获取窝轮牛熊证实时行情

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | value | Yes | 轮证标的代码数组，上限为50个，如 `value::array({value::string(U("15792"))})` |

**返回**

`web::json::value` JSON 对象

返回字段说明：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 标的代码 |
| name | string | 标的名称 |
| exchange | string | 交易所 |
| market | string | 市场  |
| sec\_type | string | 合约类型 |
| currency | string | 币种  |
| expiry | string | 到期日 yyyy-mm-dd |
| strike | string | 行权价 |
| right | string | 方向 (put/call) |
| multiplier | double | 每手数量 |
| last\_trading\_date | long | 最后交易日时间戳 |
| entitlement\_ratio | double | 换股比率 |
| entitlement\_price | double | 换股价 |
| min\_tick | double | 股价最小变动单位 |
| listing\_date | long | 上市日期的时间戳 |
| call\_price | double | 回收价（仅牛熊证） |
| halted | int | 是否停牌。0: 正常, 3: 停牌, 4: 退市 |
| underlying\_symbol | string | 底层资产代码 |
| timestamp | long | 时间戳 |
| latest\_price | double | 最新价格 |
| pre\_close | double | 前一交易日收盘价 |
| open | double | 开盘价 |
| high | double | 最高价 |
| low | double | 最低价 |
| volume | int | 成交量 |
| amount | double | 成交额 |
| premium | double | 溢价  |
| outstanding\_ratio | double | 街货比 |
| implied\_volatility | double | 隐含波动率（仅窝轮支持） |
| in\_out\_price | double | 价内/价外 |
| delta | double | 对冲值（仅窝轮支持） |
| leverage\_ratio | double | 杠杆率 |
| breakeven\_point | double | 到期盈亏平衡点 |

**示例**

C++

    #include "tigerapi/quote_client.h"
    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(false, U("/path/to/your/properties/"));
    QuoteClient quote_client(config);
    
    value symbols = value::array();
    symbols[0] = value::string(U("15792"));
    symbols[1] = value::string(U("58603"));
    
    value result = quote_client.get_warrant_briefs(symbols);
    ucout << result.serialize() << std::endl;

**返回示例**

JSON

    [\
      {\
        "symbol": "58603",\
        "name": "恒指瑞通五三熊R.P",\
        "exchange": "SEHK",\
        "market": "HK",\
        "secType": "IOPT",\
        "currency": "HKD",\
        "expiry": "2025-03-28",\
        "strike": 16700.0,\
        "right": "PUT",\
        "multiplier": 10000.0,\
        "lastTradingDate": 1743004800000,\
        "entitlementRatio": 10000.0,\
        "entitlementPrice": 1890.00,\
        "minTick": 0.001,\
        "listingDate": 1668009600000,\
        "callPrice": 16600.0,\
        "halted": 0,\
        "underlyingSymbol": "HSI",\
        "timestamp": 1681200546054,\
        "latestPrice": 0.189,\
        "preClose": 0.175,\
        "open": 0.188,\
        "high": 0.189,\
        "low": 0.170,\
        "volume": 444000,\
        "amount": 83378.0,\
        "premium": 0.277040,\
        "outstandingRatio": 0.0375,\
        "leverageRatio": 0.226661,\
        "breakevenPoint": 10.838751,\
        "inOutPrice": 14810.00\
      },\
      {\
        "symbol": "15792",\
        "name": "招行摩通二十沽A.P",\
        "exchange": "SEHK",\
        "market": "HK",\
        "secType": "WAR",\
        "currency": "HKD",\
        "expiry": "2022-10-31",\
        "strike": 39.39,\
        "right": "PUT",\
        "multiplier": 5000.0,\
        "lastTradingDate": 1666627200000,\
        "entitlementRatio": 10.0,\
        "entitlementPrice": 2.28,\
        "minTick": 0.001,\
        "listingDate": 1651075200000,\
        "halted": 0,\
        "underlyingSymbol": "03968",\
        "timestamp": 1681200546030,\
        "latestPrice": 0.228,\
        "preClose": 0.223,\
        "open": 0.227,\
        "high": 0.228,\
        "low": 0.216,\
        "volume": 21572000,\
        "amount": 4795879.0,\
        "outstandingRatio": 0.058122,\
        "inOutPrice": 0.0011,\
        "leverageRatio": 0.000254,\
        "breakevenPoint": 17.280702,\
        "impliedVolatility": 37.11,\
        "delta": -0.9844,\
        "premium": 130.577\
      }\
    ]
