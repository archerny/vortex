# 证券

get\_market\_status 获取市场状态

[](./quote-stock.md#get_market_status-%E8%8E%B7%E5%8F%96%E5%B8%82%E5%9C%BA%E7%8A%B6%E6%80%81)

------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_market_status(market=Market.ALL, lang=None)`

**说明**

获取指定市场的交易状态（如盘中、盘前、盘后等），并获取该市场的最近开盘时间。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | [Market](./appendix-enum.md#/market) | Yes | 查询的市场，可以使用 tigeropen.common.consts.Market 下提供的枚举常量，如 Market.US，参见枚举参数部分 |
| lang | [Language](./appendix-enum.md#/language) | No  | 支持的语言，可以使用 tigeropen.common.consts.Language 下提供的枚举常量，如 Language.zh\_CN，**默认是Language.en\_US**，参见枚举参数部分 |

**返回**

`list`

元素为 [MarketStatus](./appendix-object-detail.md#/marketstatus)
 对象，MarketStatus结构如下：

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| market | str | 市场名称 |
| trading\_status | str | 交易状态码：未开盘 NOT\_YET\_OPEN；盘前交易 PRE\_HOUR\_TRADING；交易中 TRADING；午间休市 MIDDLE\_CLOSE；盘后交易 POST\_HOUR\_TRADING；已收盘 CLOSING；夜盘 OVERNIGHT\_TRADING；提前休市 EARLY\_CLOSED；休市 MARKET\_CLOSED |
| status | str | 交易状态描述 |
| open\_time | datetime | 最近的开盘交易时间，带时区信息 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    market_status = quote_client.get_market_status(Market.US)
    
    # 查看属性
    print(market_status)

**返回示例**

    [MarketStatus({'market': 'US', 'status': '盘前交易', 'open_time': datetime.datetime(2019, 1, 7, 9, 30, tzinfo=<DstTzInfo 'US/Eastern' EST-1 day, 19:00:00 STD>), 'trading_status': 'PRE_HOUR_TRADING'})]

* * *

get\_trading\_calendar 获取市场交易日历

[](./quote-stock.md#get_trading_calendar-%E8%8E%B7%E5%8F%96%E5%B8%82%E5%9C%BA%E4%BA%A4%E6%98%93%E6%97%A5%E5%8E%86)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_trading_calendar(market, begin_date=None, end_date=None)`

**说明**

提供自 2015 年以来至本年度末的市场交易日历（排除周末及该市场法定节假日，但未排除临时休市日期）。如所选时间超出可提供范围，起止时间将自动调整至可用数据范围内。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | [Market](./appendix-enum.md#/market) | Yes | 查询的市场，可以使用 tigeropen.common.consts.Market 下提供的枚举常量，如 Market.US，参见枚举参数部分 |
| begin\_date | str | No  | 日历起始时间， 结果里包含本日。yyyy-MM-dd 格式，如 '2022-06-01' |
| end\_date | str | No  | 日历截至时间，结果里不包含本日。yyyy-MM-dd 格式，如 '2022-06-01' |

begin\_time 和 end\_time 传参处理如下:

| begin\_time是否传递 | end\_time是否传递 | 说明  |
| --- | --- | --- |
| yes | yes | begin\_time和end\_time分别为所传值 |
| yes | no  | end\_time为begin\_time往后365天 |
| no  | yes | begin\_time为end\_time往前365天 |
| no  | no  | begin\_time为当前日期，end\_time为begin\_time往后30天 |

**返回**

`list`

每个元素为 dict, key 的含义如下

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| date | str | 交易日日期 |
| type | str | 交易日类型：TRADING为正常交易日，全天交易；EARLY\_CLOSE为提前休市 |
| open\_time | str | 开盘时间，如 "09:30:00", 取市场所在时区 |
| close\_time | str | 收盘时间, 如 "16:00:00", 取市场所在时区 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    calendar = quote_client.get_trading_calendar(Market.US, begin_date='2022-11-01', end_date='2022-12-01')
    print(calendar)

**返回示例**

JSON

    [\
        {\
            "date": "2022-11-01",\
            "type": "TRADING"\
        },\
        {\
            "date": "2022-11-02",\
            "type": "TRADING"\
        },\
        {\
            "date": "2022-11-03",\
            "type": "TRADING"\
        }\
    ]

* * *

get\_symbols 获取所有证券代码列表

[](./quote-stock.md#get_symbols-%E8%8E%B7%E5%8F%96%E6%89%80%E6%9C%89%E8%AF%81%E5%88%B8%E4%BB%A3%E7%A0%81%E5%88%97%E8%A1%A8)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_symbols(market=Market.ALL, include_otc=False)`

**说明**

获取指定市场所有证券的代码列表，包含已退市或暂不可交易的证券，以及指数代码。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | [Market](./appendix-enum.md#/market) | No  | 查询的市场， 可以使用tigeropen.common.consts.Market下提供的枚举常量，如Market.US |
| include\_otc | bool | No  | 是否包含OTC证券 |

**返回**

类型

`list`

元素为市场所有证券的symbol，包含退市和不可交易的部分代码。其中以`.`开头的代码为指数， 如 `.DJI` 表示道琼斯指数。

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    symbols = quote_client.get_symbols(Market.US)
    print(symbols)

**返回示例**

    ['.DJI', '.IXIC', '.SPX', 'A', 'AA', 'AAA', 'AAAU', 'AAC', 'AAC.U', 'AAC.WS', 'AACG', 'AACI', 'AACIU', 'AACIW', 'AACQW', 'AADI', 'AADR', 'AAIC', 'AAIN', 'AAL', 'AAMC', 'AAME', 'AAN', 'AAOI', 'AAON', 'AAP', 'AAPL',....,'ZYME', 'ZYNE', 'ZYXI']

* * *

get\_symbol\_names 获取代码及名称列表

[](./quote-stock.md#get_symbol_names-%E8%8E%B7%E5%8F%96%E4%BB%A3%E7%A0%81%E5%8F%8A%E5%90%8D%E7%A7%B0%E5%88%97%E8%A1%A8)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_symbol_names(market=Market.ALL, lang=None, include_otc=False)`

**说明**

获取指定市场所有证券的代码及对应名称。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | [Market](./appendix-enum.md#/market) | Yes | 查询的市场，可以使用tigeropen.common.consts.Market下提供的枚举常量，如 Market.US |
| lang | [Language](./appendix-enum.md#/language) | No  | 支持的语言，可以使用tigeropen.common.consts.Language下提供的枚举常量，默认为英文 |
| include\_otc | bool | No  | 是否包含OTC证券 |

**返回**

`list`

列表（list）中的每个元素为一个元组（tuple），其中第一个元素为 symbol，第二个元素为 name。

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    symbol_names = quote_client.get_symbol_names(market=Market.ALL)
    print(symbol_names)

**返回示例**

    [('AAAP', 'Advanced Accelerator Applications SA'), ('AAAU', 'Perth Mint Physical Gold ETF'), ('AABA', 'Altaba'), ('AAC', 'AAC Holdings Inc')]

* * *

get\_stock\_briefs 获取股票实时行情

[](./quote-stock.md#get_stock_briefs-%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E5%AE%9E%E6%97%B6%E8%A1%8C%E6%83%85)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_stock_briefs(symbols, include_hour_trading=False, lang=None)`

**说明**

获取股票实时行情。使用该接口前需购买相应行情权限，每次请求最多支持 50 只股票。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | Yes | 证券代码的列表，最多50只，如 \['AAPL', 'MSFT'\] |
| include\_hour\_trading | bool | No  | 是否返回盘前盘后数据 |
| lang | [Language](./appendix-enum.md#/language) | No  | 支持的语言， 可使用tigeropen.common.consts.Language中提供的枚举常量，默认英文 |

**返回**

`pandas.DataFrame`

结构如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| symbol | str | 证券代码 |
| ask\_price | float | 卖一价 |
| ask\_size | int | 卖一量 |
| bid\_price | float | 买一价 |
| bid\_size | int | 买一量 |
| pre\_close | float | 前收价 |
| latest\_price | float | 最新价 |
| latest\_time | int | 最新成交时间，毫秒单位数字时间戳 |
| volume | int | 成交量 |
| open | float | 开盘价 |
| high | float | 最高价 |
| low | float | 最低价 |
| status | str | 交易状态 |
| change | double | 涨跌额 |
| changeRate | double | 涨跌幅 |
| amplitude | double | 振幅  |
| adj\_pre\_close | str | 复权调整后的昨收价格 |
| hour\_trading\_tag | str | 盘前("Pre-Mkt")、盘后("Post-Mkt")标识 |
| hour\_trading\_latest\_price | float | 盘前盘后实时价格 |
| hour\_trading\_latest\_time | str | 最新成交时间(美东时间) |
| hour\_trading\_volume | int | 盘前盘后成交量 |
| hour\_trading\_timestamp | int | 最新成交时间 |

status(交易状态) 取值：

*   "UNKNOWN"：未知
*   "NORMAL"：正常
*   "HALTED"：停牌
*   "DELIST"：退市
*   "NEW"：新股
*   "ALTER"：变更
*   "CIRCUIT\_BREAKER"：熔断
*   "ST"：特别处理

**示例**

Python

    import pandas as pd
    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    briefs = quote_client.get_stock_briefs(['00700'])
    print(briefs)
    
    # 将 latest_time 转换为对应时区的日期时间
    briefs['cn_date'] = pd.to_datetime(briefs['latest_time'], unit='ms').dt.tz_localize('UTC').dt.tz_convert('Asia/Shanghai')
    briefs['us_date'] = pd.to_datetime(briefs['latest_time'], unit='ms').dt.tz_localize('UTC').dt.tz_convert('US/Eastern')

**返回示例**

      symbol     open   high       low   close  pre_close  latest_price    latest_time  \
        AAPL  241.225  246.3  240.2106  245.50     237.88        245.50  1758312000000   
    
       ask_price  ask_size  bid_price  bid_size     volume  status  adj_pre_close  \
             0.0         0        0.0         0  163796191  NORMAL         237.88   
    
      hour_trading_tag  hour_trading_latest_price  hour_trading_pre_close  \
              Post-Mkt                     245.69                  245.50   
    
      hour_trading_latest_time  hour_trading_volume  hour_trading_timestamp  
                     19:59 EDT             23831923           1758326399684  
    

* * *

get\_depth\_quote 获取深度行情

[](./quote-stock.md#get_depth_quote-%E8%8E%B7%E5%8F%96%E6%B7%B1%E5%BA%A6%E8%A1%8C%E6%83%85)

--------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_depth_quote(symbols, market)`

**说明**

获取指定证券的买卖 N 档挂单数据，包括委托价格、数量及订单数。每次请求最多支持 50 只证券。

> ⚠️
> 
> **CAUTION**
> 
> 港股：交易日收市竞价时间为 16:00-16:10，实际收市时间在 16:08 至 16:10 之间随机。当天最后一条深度行情数据通常在 16:10 之后的一到两分钟更新。
> 
> 美股：深度行情包含盘前和盘后交易信息，无需传入额外参数，实时请求即可获取。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | Yes | 证券代码列表，单次上限50只 |
| market | [Market](./appendix-enum.md#/market) | Yes | 查询的市场， 可使用 tigeropen.common.consts.Market 下提供的枚举常量 |

**返回**

`dict`

数据示例:

若返回单个 symbol:

Python

    {'symbol': '03833', 
     'asks': [(1.81, 139000, 7), (1.82, 211000, 9), (1.83, 245000, 9), (1.84, 59000, 6), (1.85, 83000, 7), (1.86, 30000, 2), (1.87, 54000, 4), (1.88, 141000, 6), (1.89, 180000, 4), (1.9, 117000, 7)], 
     'bids': [(1.8, 355000, 6), (1.79, 242000, 5), (1.78, 71000, 5), (1.77, 142000, 8), (1.76, 616000, 10), (1.75, 120000, 7), (1.74, 90000, 4), (1.73, 80000, 2), (1.72, 210000, 7), (1.71, 258000, 6)]}

若返回多个 symbol:

Python

    {'03833': 
      {'symbol': '03833', 
       'asks': [(1.81, 131000, 7), (1.82, 164000, 8), (1.83, 245000, 9), (1.84, 59000, 6), (1.85, 83000, 7), (1.86, 30000, 2), (1.87, 54000, 4), (1.88, 141000, 6), (1.89, 180000, 4), (1.9, 117000, 7)], 
       'bids': [(1.8, 356000, 7), (1.79, 242000, 5), (1.78, 71000, 5), (1.77, 142000, 8), (1.76, 616000, 10), (1.75, 120000, 7), (1.74, 90000, 4), (1.73, 80000, 2), (1.72, 210000, 7), (1.71, 258000, 6)]}, 
    
    '01810': 
     {'symbol': '01810', 
      'asks': [(55.8, 306800, 134), (55.85, 242400, 78), (55.9, 394000, 87), (55.95, 421000, 69), (56.0, 526000, 158), (56.05, 320600, 42), (56.1, 327000, 71), (56.15, 277400, 46), (56.2, 116800, 74), (56.25, 163200, 42)], 
      'bids': [(55.75, 71200, 46), (55.7, 1013000, 211), (55.65, 574200, 202), (55.6, 1297800, 749), (55.55, 1136200, 485), (55.5, 2075400, 1030), (55.45, 386800, 149), (55.4, 484800, 216), (55.35, 267000, 107), (55.3, 328200, 230)]
    }
    }

asks 和 bids 对应的列表项数据含义为 (委托价格，委托数量，委托订单数) :

Python

    [(ask_price1, ask_volume1, order_count), (ask_price2, ask_volume2, order_count), ...]
    [(bid_price1, bid_volume2, order_count), (bid_price2, bid_volume2, order_count), ...]

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    depth_quote = quote_client.get_depth_quote(['02833'], Market.HK)

**返回示例**

Python

    {'symbol': '02833',
    'asks': [(27.4, 300, 2), (27.45, 500, 1), (27.5, 4400, 1), (27.55, 0, 0), (27.6, 5700, 3), (27.65, 0, 0),\
            (27.7, 500, 1), (27.75, 0, 0), (27.8, 0, 0), (27.85, 0, 0)],
    'bids': [(27, 4000, 3), (26.95, 200, 1), (26.9, 0, 0), (26.85, 400, 1), (26.8, 0, 0), (26.75, 0, 0),\
            (26.7, 0, 0), (26.65, 0, 0), (26.6, 0, 0), (26.55, 0, 0)]
    }

* * *

get\_trade\_ticks 获取逐笔成交数据

[](./quote-stock.md#get_trade_ticks-%E8%8E%B7%E5%8F%96%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4%E6%95%B0%E6%8D%AE)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_trade_ticks(symbols, trade_session=None, begin_index=None, end_index=None, limit=None, lang=None)`

**说明**

获取逐笔成交数据。该接口既支持收盘后查询当前交易日的全量逐笔记录，也支持盘中获取最新的实时逐笔数据。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | Yes | 证券代码列表，最多50个 |
| trade\_session | TradingSession | No  | 交易时段，可使用 tigeropen.common.consts.TradingSession中提供的枚举常量，默认返回盘中（常规交易时段）数据 |
| begin\_index | int | No  | 起始索引，当日索引值从0开始，如果begin\_index和end\_index设置为-1，会返回最新的逐笔数据，**后续查询时，可以把上次查询返回的end\_index值当做begin\_index，从而进行连续查询**，返回数据为前闭后开集合，比如：begin\_index=1，end\_index=100，会返回从1到99的记录，下次查询时，设置begin\_index=100，end\_index=200。 |
| end\_index | int | No  | 结束索引，**结束索引和起始索引的差值不能大于2000，大于2000时默认返回从起始索引开始的200条记录**。当limit参数小于起始和结束索引的差值时，只会返回从起始索引开始的limit条逐笔数据。 |
| limit | int | No  | 返回条数限制，默认返回：200，最大限制：2000 |
| lang | [Language](./appendix-enum.md#/language) | No  | 支持的语言， 可使用 tigeropen.common.consts.Language 中提供的枚举常量，默认英文 |

**begin\_index 和 end\_index参数使用说明**

| 查询方式 | begin\_index | end\_index | 描述  |
| --- | --- | --- | --- |
| 查询最新逐笔记录 | \-1 | \-1 | 默认返回limit条最新的逐笔记录。limit 默认为200 |
| 根据区间查询当日逐笔 | 具体数值 | 具体数值 | 举例：begin\_index=10, end\_index=100 ，会返回包含10到99的 90条记录。如果limit设置为20，则会返回10到29的20条记录。 |

**返回**

`pandas.DataFrame`

结构如下：

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| index | int | 索引值 |
| time | int | 毫秒时间戳 |
| price | float | 成交价 |
| volume | int | 成交量 |
| direction | str | 价格变动方向，"+"表示主动买入，"-"表示主动卖出，"\*"表示中性 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.common.consts import TradingSession
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    ticks = quote_client.get_trade_ticks(['00700'])
    
    # 查询最新tick数据
    ticks = quote_client.get_trade_ticks(['AAPL'], begin_index=-1, end_index=-1)
    
    # 请求盘前tick数据
    ticks = quote_client.get_trade_ticks(['AAPL'], trade_session=TradingSession.PreMarket)
    # 请求盘后tick数据
    ticks = quote_client.get_trade_ticks(['00700'], trade_session=TradingSession.AfterHours)
    
    
    

**返回示例**

Python

        symbol           time   volume   price direction   index
    0     AAPL  1712347199527      291  169.53         -  521645
    1     AAPL  1712347199564      200  169.53         -  521646
    2     AAPL  1712347199564      400  169.53         -  521647
    3     AAPL  1712347199564      500  169.53         -  521648
    4     AAPL  1712347199566      900  169.53         -  521649
    ..     ...            ...      ...     ...       ...     ...
    395    AMD  1712347200234       40  170.40         *  556800
    396    AMD  1712347200273       55  170.39         *  556801
    397    AMD  1712347200274        5  170.39         *  556802
    398    AMD  1712347200488  1577655  170.42         *  556803
    399    AMD  1712347200489       33  170.42         *  556804
    
    

* * *

get\_bars 获取K线数据

[](./quote-stock.md#get_bars-%E8%8E%B7%E5%8F%96k%E7%BA%BF%E6%95%B0%E6%8D%AE)

---------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_bars(symbols, period=BarPeriod.DAY, begin_time=-1, end_time=-1, right=QuoteRight.BR, limit=251, lang=NONE, page_token=None, trade_session=None)`

**说明**

支持获取港股、美股K线数据，包括：日、周、月、年，以及分钟、5分钟、15分钟、30分钟、60分钟等多个级别。每次请求最多返回1200条记录，建议通过循环调用实现更长时间范围的历史数据获取，以保障接口性能和稳定性。接口支持按照日期区间或指定日期查询。

*   分钟级别K（1/5/15/30/60分钟）：支持近10年历史数据
*   日K及以上（日/周/月/年）：提供完整历史数据
*   美股盘前、盘后仅支持2024年4月后的60分钟及以下级别K线数据
*   使用begin\_time和end\_time取当日K线需要等收盘后两个半小时会刷新成全量数据

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | Yes | 证券代码列表，单次上限：50，其中A股上限：30， 如 \['AAPL', 'GOOG'\] |
| period | BarPeriod | No  | 获取的K线周期。默认 BarPeriod.DAY，可以使用 tigeropen.common.consts.BarPeriod 下提供的枚举常量，如 BarPeriod.DAY。 'day'/'week'/'month'/'year'/'1min'/'5min'/'15min'/'30min'/'60min' |
| begin\_time | int或str | No  | 区间查询的开始时间，参数受限：1分钟及5分钟K线仅支持近1个月数据，15/30/60分钟K线支持近1年数据，如需更早的分钟级K线，请使用指定日期查询。支持毫秒级别的时间戳或日期字符串，如 1639371600000 或 '2019-06-07 23:00:00' 或 '2019-06-07', 如果传日期字符串, 会按照北京时间处理；日期字符串中若省略时间, 默认 0 点. 建议使用时间戳, 避免不同市场的时区问题 |
| end\_time | int或str | No  | 区间查询的截止时间，参数受限：1分钟及5分钟K线仅支持近1个月数据，15/30/60分钟K线支持近1年数据，如需更早的分钟级K线，请使用指定日期查询。支持毫秒级别的时间戳或日期字符串，如 1639371600000 或 '2019-06-07 23:00:00' 或 '2019-06-07', 如果传日期字符串, 会按照北京时间处理；日期字符串中若省略时间, 默认 0 点. 建议使用时间戳, 避免不同市场的时区问题 |
| date | string | No  | 使用该参数（格式:yyyyMMdd）获取指定日期的分钟级别K线数据。<br><br>1.  支持查询近10年分钟级K线数据<br>2.  period参数支持1min, 5min, 15min, 30min, 60min<br>3.  symbols参数只能传入单个股票代码<br>4.  limit、pageToken、begin\_time、end\_time等分页和时间筛选参数将自动失效,无需传递<br>5.  当天查询按照购买数据权限返回，第二天开盘后，查询上一天返回全市场数据 |
| right | QuoteRight | No  | 复权方式。默认前复权，可以使用 tigeropen.common.consts.QuoteRight 下提供的枚举常量，如 QuoteRight.BR 前复权，QuoteRight.NR 不复权 |
| limit | int | No  | 限制数据的条数。默认 251 |
| lang | Language | No  | 支持的语言， 可使用 tigeropen.common.consts.Language 中提供的枚举常量，默认英文 |
| page\_token | str | No  | 分页token，记录分页的位置，上次请求返回的 next\_page\_token 可传入作为下次请求的起始标志 |
| trade\_session | TradingSession | No  | 交易时段， 可以使用 tigeropen.common.consts.TradingSession 下提供的枚举常量， 如 TradingSession.Regular 盘中交易（默认值），PreMarket 盘前交易，AfterHours 盘后交易，OverNight 夜盘（需版本 >= 3.3.1） |
| with\_fundamental | bool | No  | 是否返回市盈率、换手率数据。默认不返回 |

`trade_session` 参数设置为 PreMarket、AfterHours 或 OverNight 时，period 参数必须为以下之一： `BarPeriod.ONE_MINUTE`、`BarPeriod.THREE_MINUTES`、`BarPeriod.FIVE_MINUTES`、`BarPeriod.TEN_MINUTES`、 `BarPeriod.FIFTEEN_MINUTES`、`BarPeriod.HALF_HOUR`、`BarPeriod.ONE_HOUR`、`BarPeriod.TWO_HOURS` 或 `BarPeriod.FOUR_HOURS`，否则 K 线数据无法正确显示。

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
| volume | float | Bar 的成交量 |
| amount | float | Bar 的成交额 |
| turnover\_rate | float | 换手率. 默认不返回 |
| ttm\_pe | float | 滚动(Trailing Twelve Months)市盈率. 默认不返回 |
| lyr\_pe | float | 静态(Last Year Ratio)市盈率. 默认不返回 |
| next\_page\_token | str | 下一页的令牌 |

**示例**

Python

    import pandas as pd
    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    bars = quote_client.get_bars(['AAPL'])
    #trade_session 可选参数值
    # bars = quote_client.get_bars(['AAPL'],period = BarPeriod.ONE_MINUTE, trade_session=TradingSession.PreMarket)
    # bars = quote_client.get_bars(['AAPL'],period = BarPeriod.HALF_HOUR, trade_session=TradingSession.AfterHours)
    # bars = quote_client.get_bars(['AAPL'],period = BarPeriod.ONE_MINUTE,trade_session=TradingSession.OverNight)
    
    print(bars.head())
    # 分钟k线
    bars = quote_client.get_bars(symbols, period=BarPeriod.ONE_MINUTE,
                                 begin_time='2023-06-05 00:30:00',
                                 end_time='2023-06-10 23:31:00'
                                 )
    # 转换 time 格式
    bars['cn_date'] = pd.to_datetime(bars['time'], unit='ms').dt.tz_localize('UTC').dt.tz_convert('Asia/Shanghai')
    bars['us_date'] = pd.to_datetime(bars['time'], unit='ms').dt.tz_localize('UTC').dt.tz_convert('US/Eastern')

**返回示例**

         symbol           time      open      high       low   close    volume   amount
    0     00700  1515474000000  174.5500  175.0600  173.4100  174.33  21583997   1000
    1     00700  1515560400000  173.1600  174.3000  173.0000  174.29  23959895   1000
    2     00700  1515646800000  174.5900  175.4886  174.4900  175.28  18667729   1000
    3     00700  1515733200000  176.1800  177.3600  175.6500  177.09  25418080   1000
    4     00700  1516078800000  177.9000  179.3900  176.1400  176.19  29565947   1000

* * *

get\_bars\_by\_page 分页获取K线数据

[](./quote-stock.md#get_bars_by_page-%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96k%E7%BA%BF%E6%95%B0%E6%8D%AE)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_bars_by_page(symbol, period=BarPeriod.DAY, begin_time=-1, end_time=-1, total=10000, page_size=1000, right=QuoteRight.BR, time_interval=2, lang=None, trade_session=None)`

**说明**

分页获取指定股票的K线数据。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | str | Yes | 证券代码，每次只能查单只标的。 |
| period | BarPeriod | No  | 获取的K线周期。默认 BarPeriod.DAY，可以使用 tigeropen.common.consts.BarPeriod 下提供的枚举常量'day'/'week'/'month'/'year'/'1min'/'5min'/'15min'/'30min'/'60min'等 |
| begin\_time | int或str | No  | 起始时间。支持毫秒级别的时间戳或日期字符串，如 1639371600000 或 '2019-06-07 23:00:00' 或 '2019-06-07' |
| end\_time | int或str | No  | 截至时间。支持毫秒级别的时间戳或日期字符串，如 1639371600000 或 '2019-06-07 23:00:00' 或 '2019-06-07' |
| total | int | No  | 请求bar的总数。默认：10000 |
| page\_size | int | No  | 每页bar的数量。默认：1000 |
| right | QuoteRight | No  | 复权方式。默认前复权，可以使用 tigeropen.common.consts.QuoteRight 下提供的枚举常量，如 QuoteRight.BR 前复权，QuoteRight.NR 不复权 |
| time\_interval | int | No  | 每次请求的时间间隔，单位秒，默认：2秒 |
| lang | Language | No  | 支持的语言， 可使用 `tigeropen.common.consts.Language` 中提供的枚举常量，默认英文 |
| trade\_session | TradingSession | No  | 交易时段， 可以使用 tigeropen.common.consts.TradingSession 下提供的枚举常量， 如 TradingSession.PreMarket 盘前交易，TradingSession.AfterHours 盘后交易，TradingSession.Regular 盘中交易 |

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
| volume | float | Bar 的成交量 |
| next\_page\_token | str | 下一页的page\_token |

**示例**

Python

    import pandas as pd
    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    bars = quote_client.get_bars_by_page(
                ['AAPL'],
                period=BarPeriod.DAY,
                begin_time='2024-06-25 21:30:00',
                end_time='2025-02-02 23:31:00',
                total=1000,
                page_size=10,
                time_interval=0.01,
            )
    print(bars.head().to_string())

**返回示例**

      symbol           time    open      high     low   close    volume        amount       \                                       
    0   AAPL  1719374400000  211.50  214.8600  210.64  213.25  66213186  1.411181e+10       \
    1   AAPL  1719460800000  214.69  215.7395  212.35  214.10  49772707  1.064720e+10       \
    
    next_page_token
    xxxxxx

* * *

get\_timeline 获取最近一个交易日的分时数据

[](./quote-stock.md#get_timeline-%E8%8E%B7%E5%8F%96%E6%9C%80%E8%BF%91%E4%B8%80%E4%B8%AA%E4%BA%A4%E6%98%93%E6%97%A5%E7%9A%84%E5%88%86%E6%97%B6%E6%95%B0%E6%8D%AE)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_timeline(symbols, include_hour_trading=False, begin_time=-1, lang=None, trade_session=None)`

**说明**

获取最近一个交易日的分时数据。分时数据类似于分钟 K 线，每分钟生成一条记录。仅支持查询最新交易日的数据。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | Yes | 证券代码列表，单次上限50只，如 \['AAPL', 'TSLA'\] |
| include\_hour\_trading | bool | No  | 是否包含盘前盘后分时数据，可选填，如 True 或 False |
| begin\_time | str | No  | 获取分时数据的起始时间, 支持毫秒级别的时间戳，或日期时间字符串。如 1639386000000 或 '2019-06-07 23:00:00' 或 '2019-06-07'，**默认返回当天数据** |
| lang | [Language](./appendix-enum.md#/language) | No  | 支持的语言，可以使用tigeropen.common.consts.Language下提供的枚举常量，默认为英文 |
| trade\_session | TradingSession | No  | 交易时段，可使用 tigeropen.common.consts.TradingSession中提供的枚举常量，默认返回盘中（常规交易时段）数据 |

**返回**

`pandas.DataFrame`

结构如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| symbol | str | 证券代码，如 AAPL |
| time | int | 精确到毫秒的时间戳，如 1639386000000 |
| price | float | 当前分钟的收盘价 |
| avg\_price | float | 截至到当前时间的成交量加权均价 |
| pre\_close | float | 昨日收盘价 |
| volume | int | 这一分钟的成交量 |
| trading\_session | str | 字符串， "PreMarket" 表示盘前交易, "Regular" 表示盘中交易, "AfterHours"表示盘后交易。 |

**示例**

Python

    import pandas as pd
    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    timeline = quote_client.get_timeline(['01810'], include_hour_trading=False)
    
    # 将 time 转换为对应时区的日期时间
    timeline['cn_date'] = pd.to_datetime(timeline['time'], unit='ms').dt.tz_localize('UTC').dt.tz_convert('Asia/Shanghai')
    timeline['us_date'] = pd.to_datetime(timeline['time'], unit='ms').dt.tz_localize('UTC').dt.tz_convert('US/Eastern')
    
    # 查看最新一条数据
    print(timeline.iloc[-1].price)
    print(timeline.iloc[-1].volume)
    # 按symbol过滤
    df = timeline.loc[timeline['symbol'] == 'AAPL']
    

**返回示例**

         symbol           time    price  avg_price  pre_close  volume trading_session
    0     01810  1547217000000  23.4700  23.211563       23.4  233000         regular
    1     01810  1547217060000  23.6700  23.408620       23.4  339296         regular
    2     01810  1547217120000  23.5900  23.423038       23.4   46337         regular
    3     01810  1547217180000  23.5000  23.428830       23.4   66697         regular
    4     01810  1547217240000  23.5108  23.433360       23.4   46762         regular

* * *

get\_timeline\_history 获取历史分时数据

[](./quote-stock.md#get_timeline_history-%E8%8E%B7%E5%8F%96%E5%8E%86%E5%8F%B2%E5%88%86%E6%97%B6%E6%95%B0%E6%8D%AE)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_timeline_history(symbols, date, right=QuoteRight.BR,trade_session=none)`

**说明**

获取指定日期的历史分时数据。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | Yes | 证券代码列表，单次上限50只，如 \['AAPL', 'TSLA'\] |
| date | str | Yes | 日期字符串。如 '2022-04-14' |
| right | QuoteRight | No  | 行情复权，默认前复权。可以使用tigeropen.common.consts.QuoteRight中提供的枚举 |
| trade\_session | TradingSession | No  | 交易时段，可使用 tigeropen.common.consts.TradingSession中提供的枚举常量，默认返回盘中（常规交易时段）数据 |

**返回**

`pandas.DataFrame`

结构如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| symbol | str | 证券代码，如 AAPL |
| time | int | 精确到毫秒的时间戳，如 1639386000000 |
| price | float | 当前分钟的收盘价 |
| avg\_price | float | 截至到当前时间的成交量加权均价 |

**示例**

Python

    import pandas as pd
    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    timeline = quote_client.get_timeline_history(['AAPL', 'BABA'], '2021-04-11')
    
    # 将 time 转换为对应时区的日期时间
    timeline['cn_date'] = pd.to_datetime(timeline['time'], unit='ms').dt.tz_localize('UTC').dt.tz_convert('Asia/Shanghai')
    timeline['us_date'] = pd.to_datetime(timeline['time'], unit='ms').dt.tz_localize('UTC').dt.tz_convert('US/Eastern')
    
    # 按symbol过滤
    df = timeline.loc[timeline['symbol'] == 'AAPL']
    
    print(timeline)

**返回示例**

Python

        symbol           time   volume     price   avg_price 
    0     AAPL  1649683800000  1569372  168.5000  168.749760
    1     AAPL  1649683860000   323022  168.5800  168.739030 

* * *

get\_stock\_delay\_briefs 获取股票延迟行情

[](./quote-stock.md#get_stock_delay_briefs-%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E5%BB%B6%E8%BF%9F%E8%A1%8C%E6%83%85)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_stock_delay_briefs(symbols, lang=None)`

**说明**

本接口为免费延迟行情接口，无需购买行情权限，开通开发者账号后可直接请求使用。当前仅支持获取美股延迟行情，相较实时行情**延迟约15分钟**。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | Yes | 证券代码的列表，目前仅支持获取美股延迟行情。 如 \['AAPL', 'MSFT'\]，单次请求上限50 |
| lang | [Language](./appendix-enum.md#/language) | No  | 支持的语言， 可使用tigeropen.common.consts.Language中提供的枚举常量，默认英文 |

**返回**

`pandas.DataFrame`

结构如下：

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| symbol | str | 证券代码 |
| pre\_close | float | 前收价 |
| time | int | 最近成交时间，毫秒为单位的数字时间戳，如 1639429200000 |
| volume | int | 成交量 |
| open | float | 开盘价 |
| high | float | 最高价 |
| low | float | 最低价 |
| close | float | 收盘价 |
| halted | float | 标的状态 (0: 正常 3: 停牌 4: 退市 7: 新股 8: 变更) |

**示例**

Python

    import pandas as pd
    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    briefs = quote_client.get_stock_delay_briefs(['AAPL'])
    print(briefs)
    
    # 将 time 转换为对应时区的日期时间
    briefs['cn_date'] = pd.to_datetime(briefs['time'], unit='ms').dt.tz_localize('UTC').dt.tz_convert('Asia/Shanghai')
    briefs['us_date'] = pd.to_datetime(briefs['time'], unit='ms').dt.tz_localize('UTC').dt.tz_convert('US/Eastern')

**返回示例**

Python

      symbol  pre_close  halted           time    open   high       low  close  \
    0   AAPL     174.33     0.0  1639602000000  175.11  179.5  172.3108  179.3   
      
    
          volume                   cn_date                   us_date  
    0  131063257 2021-12-16 05:00:00+08:00 2021-12-15 16:00:00-05:00 

* * *

get\_trade\_metas 获取股票交易需要的信息

[](./quote-stock.md#get_trade_metas-%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E4%BA%A4%E6%98%93%E9%9C%80%E8%A6%81%E7%9A%84%E4%BF%A1%E6%81%AF)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_trade_metas(symbols)`

**说明**

查询股票交易必需信息，如每手股数。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md#/)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | Yes | 证券代码列表, 上限为50 |

**返回**

`pandas.DataFrame`

结构如下:

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| symbol | str | 证券代码 |
| lot\_size | int | 每手股数 |
| min\_tick | float | 价格最小变动单位 |
| spread\_scale | float | 报价精度 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    trade_metas = quote_client.get_trade_metas(symbols=['00700', '00336'])

**返回示例**

      symbol  lot_size  min_tick  spread_scale
    0  00700       100      0.20             0
    1  00336      1000      0.01             0

* * *

get\_capital\_flow 获取股票资金流数据

[](./quote-stock.md#get_capital_flow-%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E8%B5%84%E9%87%91%E6%B5%81%E6%95%B0%E6%8D%AE)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_capital_flow`

**说明**

获取股票资金净流入数据，包括最近交易日的实时分钟数据以及不同周期的历史净流入数据。支持按日、周、月、季度、半年和年度获取数据，单次请求最多返回 1200 条，默认返回 200 条。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | string | Yes | 股票代码 |
| period | [CapitalPeriod](./appendix-enum.md#/capitalperiod) | Yes | 数据类型，取值范围(intraday:实时，day:日，week:周，month:月，year:年，quarter:季度，6month:半年) |
| market | [Market](./appendix-enum.md#/market) | Yes | US 美股，HK港股，CN A股(实时资金流向不支持A股) |
| begin\_time | int | No  | 开始时间，默认：-1，单位:毫秒(ms),前闭后开区间，即查询结果会包含起始时间数据，如查询周/月/年K线，会返回包含当前周期的数据(如:起始时间为周三，会返回从这周一的数据) |
| end\_time | int | No  | 结束时间，默认：-1，单位:毫秒(ms) |
| limit | int | No  | 单次请求返回数量，不传默认是200，limit不能超过1200，如果limit设置大于1200，只会返回1200条数据 |
| lang | Lang | No  | 语言支持: zh\_CN/zh\_TW/en\_US，默认: en\_US |

**返回**

`pandas.DataFrame`

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| period | string | 周期  |
| time | string | 标的所在市场时区的时间字符串，实时数据时为"11-25 12:48:00 EST"格式，非实时数据为"2022-11-22"格式 |
| timestamp | int | 13位时间戳 |
| net\_inflow | float | 净流入金额，负数表示流出 |

**示例**

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    result = quote_client.get_capital_flow('AAPL', market=Market.US, period=CapitalPeriod.INTRADAY)
    print(result)
    
    # 获取第一行的 net_inflow
    result.iloc[0]['net_inflow']
    # 或 result['net_inflow'].iloc[0]
    
    

**返回示例**

               time      timestamp    net_inflow symbol period
    0    2022-02-24  1645678800000 -5.889058e+08   AAPL    day
    1    2022-02-25  1645765200000 -1.229127e+08   AAPL    day
    2    2022-02-28  1646024400000  1.763644e+08   AAPL    day

* * *

get\_capital\_distribution 获取股票资金分布

[](./quote-stock.md#get_capital_distribution-%E8%8E%B7%E5%8F%96%E8%82%A1%E7%A5%A8%E8%B5%84%E9%87%91%E5%88%86%E5%B8%83)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_capital_distribution`

**说明**

获取股票资金分布。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | str | Yes | 股票代码 |
| market | [Market](./appendix-enum.md#/market) | Yes | US 美股，HK港股，CN A股 |
| lang | Lang | No  | 语言支持: zh\_CN/zh\_TW/en\_US, 默认: en\_US |

**返回**

`tigeropen.quote.domain.capital_distribution.CapitalDistribution`

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | str | 股票代码 |
| net\_inflow | float | 净流入金额(资金总流入 - 资金总流出)，负数表示流出 |
| in\_all | float | 资金流入总额（大单+中单+小单） |
| in\_big | float | 大单流入 |
| in\_mid | float | 中单流入 |
| in\_small | float | 小单流入 |
| out\_all | float | 资金流出总额（大单+中单+小单） |
| out\_big | float | 大单流出 |
| out\_mid | float | 中单流出 |
| out\_small | float | 小单流出 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    result = quote_client.get_capital_distribution('JD', market=Market.US)
    print(result)

**返回示例**

    CapitalDistribution({'symbol': 'JD', 'net_inflow': -14178801.76, 'in_all': 157357147.5,
    'in_big': 25577130.842900004, 'in_mid': 13664116.789999994, 'in_small': 118115899.86410056,
    'out_all': 171535949.25, 'out_big': 22642951.677099995, 'out_mid': 12733553.691200001,
    'out_small': 136159443.88620025})

* * *

get\_stock\_broker 获取港股经纪商买卖席位

[](./quote-stock.md#get_stock_broker-%E8%8E%B7%E5%8F%96%E6%B8%AF%E8%82%A1%E7%BB%8F%E7%BA%AA%E5%95%86%E4%B9%B0%E5%8D%96%E5%B8%AD%E4%BD%8D)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_stock_broker`

**说明**

获取港股经纪商买卖席位。

**参数、**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | str | Yes | 股票代码 |
| limit | int | No  | 单次请求返回买方/卖方席位数量，不传默认是40，limit不能超过60，如果limit设置大于60，只会返回60条数据 |
| lang | Lang | No  | 语言支持: zh\_CN/zh\_TW/en\_US, 默认: en\_US |

**返回**

`tigeropen.quote.domain.stock_broker.StockBroker`

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | str | 股票代码 |
| bid\_broker | LevelBroker | 买方不同价格档位数组，可参考tigeropen.quote.domain.stock\_broker.LevelBroke 说明 |
| ask\_broker | LevelBroker | 卖方不同价格档位数组，可参考 tigeropen.quote.domain.stock\_broker.LevelBroke 说明 |

其中LevelBroker属性如下：

| field | type | desc |
| --- | --- | --- |
| level | int | 价格档位 |
| price | float | 价格  |
| broker\_count | int | 席位数量 |
| broker | lis | 经纪商买卖席位列表, 可参考 tigeropen.quote.domain.stock\_broker.Broker 说明 |

Broker attributes：

| field | type | desc |
| --- | --- | --- |
| id  | str | broker id |
| name | str | broker name |

**Example**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    result = quote_client.get_stock_broker('01810', limit=5)
    print(result)

**Return example**

    StockBroker({'symbol': '01810',
        'bid_broker': [\
            LevelBroker({'level': 1, 'price': 11.46, 'broker_count': 5,\
                'broker': [Broker({'id': '5999', 'name': '中国创盈'}), Broker({'id': '4374', 'name': '巴克莱亚洲'}),\
                        Broker({'id': '1438', 'name': 'Susquehanna'}), Broker({'id': '4821', 'name': '华盛'}),\
                         Broker({'id': '6998', 'name': '中国投资'})]})],
        'ask_broker': [\
            LevelBroker({'level': 1, 'price': 11.48, 'broker_count': 5,\
                'broker': [Broker({'id': '4374', 'name': '巴克莱亚洲'}), Broker({'id': '9056', 'name': '瑞银'}),\
                        Broker({'id': '2027', 'name': '东亚'}), Broker({'id': '4821', 'name': '华盛'}),\
                        Broker({'id': '4374', 'name': '巴克莱亚洲'})]})]})

* * *

get\_broker\_hold 获取港股经纪商持仓市值

[](./quote-stock.md#get_broker_hold-%E8%8E%B7%E5%8F%96%E6%B8%AF%E8%82%A1%E7%BB%8F%E7%BA%AA%E5%95%86%E6%8C%81%E4%BB%93%E5%B8%82%E5%80%BC)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_stock_broker`

**说明**

获取港股经纪商持仓市值。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | [Market](./appendix-enum.md#/market) | Yes | 仅支持 HK 港股 |
| limit | int | No  | 分页每页数量，不传默认是50，limit不能超过500，如果limit设置大于500，只会返回500条数据 |
| page | int | No  | 分页页码，从0开始，默认0 |
| order\_by | str | No  | 排序字段，默认 "marketValue", 可选值 marketValue/sharesHold/buyAmount/buyAmount5/buyAmount20/buyAmount60 |
| direction | str | No  | 排序方向, DESC 降序/ASC 升序, 默认 DESC |
| lang | Lang | No  | 语言支持: zh\_CN/zh\_TW/en\_US, 默认: en\_US |

**返回**

`pd.DataFrame`

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| org\_id | str | 经纪商id |
| org\_name | str | 经纪商名称 |
| date | str | 最近交易日 |
| shares\_hold | int | 持仓数量 |
| market\_value | float | 持仓市值 |
| buy\_amount | int | 1日净买入量 |
| buy\_amount5 | int | 5日净买入量 |
| buy\_amount20 | int | 20日净买入量 |
| buy\_amount60 | int | 60日净买入量 |
| market | str | 市场  |

**Example**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    result = quote_client.get_broker_hold(market=Market.HK, limit=5, order_by='marketValue', direction=SortDirection.ASC, lang=Language.zh_CN)
    print(result)

**Return example**

       org_id  org_name        date   shares_hold  market_value  buy_amount  buy_amount5 \
    0  C00019  香港上海汇丰银行  2025-04-10  697552502881  8.943928e+12 -1405700331  -3893473916 \  
    1  A00003    港股通(沪)  2025-04-10  293590702492  2.493193e+12   843074553   4263428837   \
    2  C00010      花旗银行  2025-04-10  206768714970  1.926757e+12   386287281    483932517   \  
    
    buy_amount20  buy_amount60 market  page  total_page  total_count
     -7103861935  -29489710005     HK     0         135          672
     10882949005   17126490078     HK     0         135          672
       -22419868   -5182619457     HK     0         135          672

* * *

get\_trade\_rank 热门交易榜

[](./quote-stock.md#get_trade_rank-%E7%83%AD%E9%97%A8%E4%BA%A4%E6%98%93%E6%A6%9C)

--------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_trade_rank(market, lang=Language.en_US)`

**说明**

获取股票热门交易榜，数据约每 20 秒更新一次。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | [Market](./appendix-enum.md#/market) | Yes | US 美股，HK港股，SG新加坡股 |
| lang | Lang | No  | 语言支持: zh\_CN/zh\_TW/en\_US, 默认: en\_US |

**返回**

`pandas.DataFrame`

美股返回30条，港股和新加坡股返回10条，结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | str | 股票代码 |
| market | str | 市场  |
| name | str | 名称  |
| sec\_type | str | 证券类型 |
| change\_rate | float | 盘中涨跌幅，如果当前为非盘中交易时段，则为上一交易日的盘中涨跌幅 |
| sell\_order\_rate | float | 卖单占比，当天的累计买卖比例，盘中阶段包含盘前+盘中的累计买卖比例，盘后阶段包含盘前+盘中+盘后的累计买卖比 |
| buy\_order\_rate | float | 买单占比，计算方法同上 |
| hour\_trading\_trading\_status | int | 盘前盘后交易状态（仅美股） |
| hour\_trading\_trade\_session | str | 盘前盘后交易时段，对应状态（仅美股） |
| hour\_trading\_change\_rate | float | 最近一个盘前盘后涨跌幅（仅美股） |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config) 
    
    result = quote_client.get_trade_rank(market=Market.US, lang=Lang.ZH_CN)
    print(result)

**返回示例**

       symbol market                name           sec_type  change_rate  sell_order_rate   \
    0    TSLA     US        Tesla Motors                STK     0.022118         0.490115   \     
    1    NVDA     US              NVIDIA                STK     0.002440         0.418413   \     
    2    INTC     US               Intel                STK    -0.032385         0.401211   \     
    
    buy_order_rate  hour_trading_trading_status hour_trading_trade_session  hour_trading_change_rate
          0.509885                            3                 AfterHours                 -0.001338
          0.581587                            3                 AfterHours                 -0.003792
          0.598789                            3                 AfterHours                 -0.003381
    
