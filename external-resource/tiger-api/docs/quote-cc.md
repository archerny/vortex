# 数字货币

get\_symbols 获取所有代码列表

[](./quote-cc.md#get_symbols-%E8%8E%B7%E5%8F%96%E6%89%80%E6%9C%89%E4%BB%A3%E7%A0%81%E5%88%97%E8%A1%A8)

----------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_symbols(sec_type=SecurityType.CC)`

**说明**

获取所有数字货币的代码列表。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| sec\_type | str 或 SecurityType | Yes | 固定为 SecurityType.CC 或 "CC" |

**返回**

类型

`list`

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    from tigeropen.common.consts import SecurityType
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    symbols = quote_client.get_symbols(sec_type=SecurityType.CC)
    print(symbols)

**返回示例**

    ['APT.USD', 'IOTX.USD', 'USDT.USD', 'DYDX.USD', 'DOGE.USD', 'KAIA.USD', 'ATOM.USD', 'COMP.USD', 'UNI.USD', 'AAVE.USD', 'LDO.USD', 'LINK.USD', 'SNX.USD', 'OP.USD', 'DOT.USD', 'POL.USD', 'BTC.USD', 'SOL.USD', 'ARB.USD', 'TON.USD', 'AVAX.USD', 'MKR.USD', 'IMX.USD', 'ETH.USD', 'LTC.USD']
    

get\_cc\_briefs 获取实时行情

[](./quote-cc.md#get_cc_briefs-%E8%8E%B7%E5%8F%96%E5%AE%9E%E6%97%B6%E8%A1%8C%E6%83%85)

-------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_cc_briefs(symbols, sec_type=SecurityType.CC)`

**说明**

获取数字货币实时行情。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | Yes | 代码的列表，最多50只，如 \['BTC', 'ETH'\] |
| sec\_type | str 或 SecurityType | Yes | 固定为 SecurityType.CC 或 "CC" |
| lang | [Language](./appendix-enum.md#/language) | No  | 支持的语言， 可使用tigeropen.common.consts.Language中提供的枚举常量，默认英文 |

**返回**

`pandas.DataFrame`

结构如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| symbol | str | 证券代码 |
| pre\_close | float | 前收价 |
| latest\_price | float | 最新价 |
| latest\_time | int | 最新成交时间，毫秒单位数字时间戳 |
| volume\_decimal | float | 成交量 |
| open | float | 开盘价 |
| high | float | 最高价 |
| low | float | 最低价 |
| change | double | 涨跌额 |
| changeRate | double | 涨跌幅 |

  

**示例**

Python

    import pandas as pd
    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    from tigeropen.common.consts import SecurityType
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    result = quote_client.get_cc_briefs(symbols=['BTC', 'ETH'], sec_type=SecurityType.CC)
    print(result)

**返回示例**

      symbol      open     high       low     close  pre_close  latest_price    latest_time      \
    0    BTC  71127.62  72200.0  69955.91  70932.55   71061.02      70932.55  1770608250067    \
    1    ETH   2107.21   2148.9   2054.05   2089.56    2106.26       2089.56  1770608250068   \
    
     change  change_rate  volume_decimal  
    -128.47    -0.001808       135.82381             
     -16.70    -0.007929      2815.58590             

get\_bars 获取K线数据

[](./quote-cc.md#get_bars-%E8%8E%B7%E5%8F%96k%E7%BA%BF%E6%95%B0%E6%8D%AE)

------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_bars(symbols, sec_type=SecurityType.CC, period=BarPeriod.DAY, begin_time=-1, end_time=-1, limit=251)`

**说明**

获取数字货币K线数据，包括：1分、60分、天、周、月级别。每次请求最多返回 1200 条记录，建议通过循环调用实现更长时间范围的历史数据获取，以保障接口性能和稳定性。接口支持按照日期区间或指定日期查询。

分钟级别K：BTC 支持最早2024年3月27日开始的数据 日K及以上（日/周/月/年）：BTC 支持最早2010年7月13日开始的数据

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | Yes | 证券代码列表，单次上限：50，其中A股上限：30， 如 \['AAPL', 'GOOG'\] |
| sec\_type | str 或 SecurityType | Yes | 固定为 SecurityType.CC 或 "CC" |
| period | BarPeriod | No  | 获取的K线周期。默认 BarPeriod.DAY，可以使用 tigeropen.common.consts.BarPeriod 下提供的枚举常量，1min, 60min, day, week, month |
| begin\_time | int或str | No  | 区间查询的开始时间， 建议使用时间戳, 避免不同市场的时区问题 |
| end\_time | int或str | No  | 区间查询的截止时间 |
| limit | int | No  | 限制数据的条数。默认 251，最大1200 |

  

**返回**

`pandas.DataFrame`

结构如下：

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| time | int | 毫秒时间戳，如 1639371600000 |
| open | float | Bar 的开盘价 |
| close | float | Bar 的收盘价 |
| high | float | Bar 的最高价 |
| low | float | Bar 的最低价 |
| volume\_decimal | float | Bar 的成交量 |

**示例**

Python

    import pandas as pd
    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    from tigeropen.common.consts import SecurityType
    
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    bars = quote_client.get_bars(['ETH'], sec_type=SecurityType.CC)
    

**返回示例**

      symbol      open    close     high      low         volume_decimal           time
    0    ETH   2107.21  2082.07  2148.90  2054.05             4408.5683  1770566400000
    1    ETH   2052.48  2106.26  2143.31  2007.04            11426.7385  1770480000000
    2    ETH   1980.74  2049.89  2118.52  1971.49            17089.0071  1770393600000
    3    ETH   1957.51  1980.75  2014.57  1747.67            26795.9965  1770307200000
    4    ETH   2148.31  1957.20  2189.48  1922.60            31535.3994  1770220800000
    

* * *

  

get\_timeline 获取最近一个交易日的分时数据

[](./quote-cc.md#get_timeline-%E8%8E%B7%E5%8F%96%E6%9C%80%E8%BF%91%E4%B8%80%E4%B8%AA%E4%BA%A4%E6%98%93%E6%97%A5%E7%9A%84%E5%88%86%E6%97%B6%E6%95%B0%E6%8D%AE)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_timeline(symbols, sec_type=SecurityType.CC, begin_time=-1)`

**说明**

获取最近一个交易日的分时数据。分时数据类似于分钟 K 线，每分钟生成一条记录。仅支持查询最新交易日的数据。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | Yes | 代码列表，单次上限10只 |
| begin\_time | str | No  | 获取分时数据的起始时间, 支持毫秒级别的时间戳，或日期时间字符串。如 1639386000000 或 '2019-06-07 23:00:00' 或 '2019-06-07'，**默认返回当天数据** |
| sec\_type | str 或 SecurityType | Yes | 固定为 SecurityType.CC 或 "CC" |

**返回**

`pandas.DataFrame`

结构如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| symbol | str | 证券代码，如 AAPL |
| time | int | 精确到毫秒的时间戳，如 1639386000000 |
| price | float | 当前分钟的收盘价 |
| avg\_price | float | 截至到当前时间的成交量加权均价 |
| volume\_decimal | int | 这一分钟的成交量 |

**示例**

Python

    import pandas as pd
    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    from tigeropen.common.consts import SecurityType
    
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    timeline = quote_client.get_timeline(['ETH'], sec_type=SecurityType.CC)
    
    

**返回示例**

        symbol  pre_close trade_session           time    price     volume_decimal
    0      ETH    2106.26       Regular  1770566400000  2106.19             0.1676
    1      ETH    2106.26       Regular  1770566460000  2104.82            55.0534
    2      ETH    2106.26       Regular  1770566520000  2110.71             1.7288
    3      ETH    2106.26       Regular  1770566580000  2111.03             0.9551
    4      ETH    2106.26       Regular  1770566640000  2112.00             1.1343
    5 
