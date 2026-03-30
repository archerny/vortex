# 行情订阅推送

订阅股票行情

[](./push-quote.md#%E8%AE%A2%E9%98%85%E8%82%A1%E7%A5%A8%E8%A1%8C%E6%83%85)

---------------------------------------------------------------------------------------------------------------

`PushClient.subscribe_quote(symbols)`

**取消方法**

`PushClient.unsubscribe_quote(symbols)`

**说明**

股票行情的订阅与取消接口，返回的数据为实时更新，即每次价格或挂单数据更新就会有数据推送 回调接口返回结果类型为基本行情 QuoteBasicData (tigeropen.push.pb.QuoteBasicData\_pb2.QuoteBasicData) 对象， 最优报价 QuoteBBOData (tigeropen.push.pb.QuoteBasicData\_pb2.QuoteBBOData) 对象

本接口为异步返回，使用`PushClient.quote_changed` 响应基本行情 QuoteBasicData 对象； 使用 `PushClient.quote_bbo_changed` 响应最优报价 QuoteBBOData 对象

**无法订阅港股/美股的指数行情**

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| symbols | list\[str\] | 证券代码列表，如 \['AAPL', 'BABA'\]，英文代码应使用大写 |

**取消方法参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| symbols | list\[str\] | 证券代码列表，如 \['AAPL', 'BABA'\] |

**返回数据**

股票行情回调数据全部字段参考：[行情变动](./appendix-object-detail.md#/%E8%A1%8C%E6%83%85%E5%8F%98%E5%8A%A8)

> ⚠️
> 
> **CAUTION**
> 
> 股票行情回调数据有两种类型：交易数据和盘口数据，两种类型数据返回的字段不一样

**示例**

Python

    from tigeropen.push.push_client import PushClient
    from tigeropen.push.pb.QuoteBBOData_pb2 import QuoteBBOData
    from tigeropen.push.pb.QuoteBasicData_pb2 import QuoteBasicData
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    # 初始化PushClient
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'), use_protobuf=True)
    
    #定义回调方法
    def on_quote_changed(frame: QuoteBasicData):
        """行情基本数据回调
        """
        print(f'quote basic change: {frame}')
    
    def on_quote_bbo_changed(frame: QuoteBBOData):
        """行情最优报价，ask/bid 
        """
        print(f'quote bbo changed: {frame}')
      
    # 绑定回调方法
    push_client.quote_changed = on_quote_changed
    push_client.quote_bbo_changed = on_quote_bbo_changed
    
    # 建立连接        
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅
    push_client.subscribe_quote(['AAPL', 'BABA'])
    
    # 取消订阅
    push_client.unsubscribe_quote(['AAPL', 'BABA'])
    # 断开连接, 会取消所有订阅
    push_client.disconnect()

**回调数据示例**

tigeropen.push.pb.QuoteBasicData\_pb2.QuoteBasicData 数据示例：

    symbol: "00700"
    type: BASIC
    timestamp: 1677742483530
    serverTimestamp: 1677742483586
    avgPrice: 365.37
    latestPrice: 363.8
    latestPriceTimestamp: 1677742483369
    latestTime: "03-02 15:34:43"
    preClose: 368.8
    volume: 12674730
    amount: 4630947968
    open: 368.2
    high: 369
    low: 362.4
    marketStatus: "交易中"
    mi {
      p: 363.8
      a: 365.37
      t: 1677742440000
      v: 27300
    }
    

tigeropen.push.pb.QuoteBasicData\_pb2.QuoteBBOData 数据示例：

    symbol: "01810"
    type: BBO
    timestamp: 1677741267291
    serverTimestamp: 1677741267329
    askPrice: 12.54
    askSize: 397600
    askTimestamp: 1677741266304
    bidPrice: 12.52
    bidSize: 787400
    bidTimestamp: 1677741266916

* * *

订阅期权行情

[](./push-quote.md#%E8%AE%A2%E9%98%85%E6%9C%9F%E6%9D%83%E8%A1%8C%E6%83%85)

---------------------------------------------------------------------------------------------------------------

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| symbols | list\[str\] | 由期权四要素组成, 空格分隔，分别是：标的代码，过期日(YYYYMMDD)，行权价，期权类型(看涨CALL/看跌PUT) |

**回调数据**

需通过`push_client.quote_changed`绑定回调方法，回调方法同股票

**示例**

`push_client.subscribe_option(['AAPL 20230120 150.0 CALL', 'SPY 20220930 470.0 PUT'])`或`push_client.subscribe_quote(['AAPL 20230120 150.0 CALL', 'SPY 20220930 470.0 PUT'])`

Python

    from tigeropen.push.push_client import PushClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    # 初始化PushClient
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'))
    
    #定义回调方法
    def on_quote_changed(frame: QuoteBasicData):
        """行情基本数据回调
        """
        print(f'quote basic change: {frame}')
    
    def on_quote_bbo_changed(frame: QuoteBBOData):
        """行情最优报价，ask/bid 
        """
        print(f'quote bbo changed: {frame}')
    
    # 绑定回调方法
    push_client.quote_changed = on_quote_changed
    push_client.quote_bbo_changed = on_quote_bbo_changed
    
    # 建立连接        
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅期权行情
    push_client.subscribe_option(['AAPL 20240119 155.0 PUT', 'SPY 20221118 386.0 CALL'])
    
    
    # 取消订阅
    push_client.unsubscribe_quote(['AAPL 20240119 155.0 PUT', 'SPY 20221118 386.0 CALL'])
    # 断开连接， 会取消所有订阅
    push_client.disconnect()

* * *

订阅期货行情

[](./push-quote.md#%E8%AE%A2%E9%98%85%E6%9C%9F%E8%B4%A7%E8%A1%8C%E6%83%85)

---------------------------------------------------------------------------------------------------------------

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| symbols | `list[str]` | 期货标的列表，如 'CLmain', 'ES2209' |

**回调数据**

需通过`push_client.quote_changed`绑定回调方法，回调方法同股票

**示例**

Python

    push_client.subscribe_future(['CLmain', 'CN2209'])
    # 或
    push_client.subscribe_quote(['VIXmain', 'ES2209'])

Python

    from tigeropen.push.push_client import PushClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    # 初始化PushClient
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'))
    
    #定义回调方法
    def on_quote_changed(frame: QuoteBasicData):
        """行情基本数据回调
        """
        print(f'quote basic change: {frame}')
    
    def on_quote_bbo_changed(frame: QuoteBBOData):
        """行情最优报价，ask/bid 
        """
        print(f'quote bbo changed: {frame}')
    
    # 绑定回调方法
    push_client.quote_changed = on_quote_changed
    push_client.quote_bbo_changed = on_quote_bbo_changed
    
    # 建立连接        
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅期货行情
    push_client.subscribe_future(['BTCmain', 'CLmain'])
    
    
    # 取消订阅
    push_client.unsubscribe_quote(['BTCmain', 'CLmain'])
    # 断开连接
    push_client.disconnect()

**回调数据示例**

基础行情

    symbol: "BTCmain"
    type: BASIC
    timestamp: 1677571147018
    avgPrice: 23562.4
    latestPrice: 23355
    latestPriceTimestamp: 1677571045000
    latestTime: "02-28 01:57:25 -0600"
    preClose: 23445
    volume: 900
    open: 23585
    high: 23710
    low: 23350
    marketStatus: "交易中"
    tradeTime: 1677571045000
    preSettlement: 23445
    minTick: 5
    mi {
      p: 23355
      a: 23562.4
      t: 1677571020000
      v: 3
      o: 23350
      h: 23355
      l: 23350
    }

最优报价

    symbol: "BTCmain"
    type: BBO
    timestamp: 1677571147018
    askPrice: 23365
    askSize: 3
    askTimestamp: 1677571147018
    bidPrice: 23355
    bidSize: 1
    bidTimestamp: 1677571141150
    

* * *

订阅数字货币行情

[](./push-quote.md#%E8%AE%A2%E9%98%85%E6%95%B0%E5%AD%97%E8%B4%A7%E5%B8%81%E8%A1%8C%E6%83%85)

-----------------------------------------------------------------------------------------------------------------------------------

`PushClient.subscribe_cc(symbols)`

**取消方法**

`PushClient.unsubscribe_cc(symbols)`

**说明**

股票行情的订阅与取消接口，返回的数据为实时更新，即每次价格或挂单数据更新就会有数据推送 回调接口返回结果类型为基本行情 QuoteBasicData (tigeropen.push.pb.QuoteBasicData\_pb2.QuoteBasicData) 对象， 最优报价 QuoteBBOData (tigeropen.push.pb.QuoteBasicData\_pb2.QuoteBBOData) 对象

本接口为异步返回，使用`PushClient.quote_changed` 响应基本行情 QuoteBasicData 对象； 使用 `PushClient.quote_bbo_changed` 响应最优报价 QuoteBBOData 对象

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| symbols | list\[str\] | 代码列表，如 \['BTC'\]，英文代码应使用大写 |

**取消方法参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| symbols | list\[str\] | 代码列表，如 \['BTC'\] |

**返回数据**

行情回调数据全部字段参考：[行情变动](./appendix-object-detail.md#/%E8%A1%8C%E6%83%85%E5%8F%98%E5%8A%A8)

> ⚠️
> 
> **CAUTION**
> 
> 行情回调数据有两种类型：交易数据和盘口数据，两种类型数据返回的字段不一样

**示例**

Python

    from tigeropen.push.push_client import PushClient
    from tigeropen.push.pb.QuoteBBOData_pb2 import QuoteBBOData
    from tigeropen.push.pb.QuoteBasicData_pb2 import QuoteBasicData
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    # 初始化PushClient
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'), use_protobuf=True)
    
    #定义回调方法
    def on_quote_changed(frame: QuoteBasicData):
        """行情基本数据回调
        """
        print(f'quote basic change: {frame}')
    
    def on_quote_bbo_changed(frame: QuoteBBOData):
        """行情最优报价，ask/bid 
        """
        print(f'quote bbo changed: {frame}')
      
    # 绑定回调方法
    push_client.quote_changed = on_quote_changed
    push_client.quote_bbo_changed = on_quote_bbo_changed
    
    # 建立连接        
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅
    push_client.subscribe_cc(['BTC'])
    
    # 取消订阅
    push_client.unsubscribe_cc(['BTC'])
    # 断开连接, 会取消所有订阅
    push_client.disconnect()

**回调数据示例**

tigeropen.push.pb.QuoteBasicData\_pb2.QuoteBasicData 数据示例：

    	"symbol": "ETH.USD",
    	"type": "BASIC",
    	"timestamp": "1770041127619",
    	"serverTimestamp": "1770041127816",
    	"latestPrice": 2322.97,
    	"latestPriceTimestamp": "1770041127619",
    	"latestTime": "02-02 22:05:27 HKT",
    	"preClose": 2313.05,
    	"volume": "27711",
    	"amount": 6.246553826751E7,
    	"open": 2314.18,
    	"high": 2374.87,
    	"low": 2156.8,
    	"marketStatus": "TRADING"
    

tigeropen.push.pb.QuoteBasicData\_pb2.QuoteBBOData 数据示例：

    
    
    "symbol": "ETH.USD",
        "type": "BBO",
        "timestamp": "1770041127619",
        "askPrice": 2322.97,
        "askTimestamp": "1770041127605",
        "bidPrice": 2322.95,
        "bidSize": "1",
        "bidTimestamp": "1770041127605"
    
    

* * *

订阅深度行情

[](./push-quote.md#%E8%AE%A2%E9%98%85%E6%B7%B1%E5%BA%A6%E8%A1%8C%E6%83%85)

---------------------------------------------------------------------------------------------------------------

`Push_client.subscribe_depth_quote(symbols)`

**取消方法**

`PushClient.unsubscribe_depth_quote(symbols)`

**说明**

订阅深度行情，支持股票（美股/港股）、期权（美股/港股）和期货。**美股深度行情推送频率为300ms，港股深度行情推送频率为2s，返回最高40档的买卖盘挂单数据**, 返回的数据为实时更新，即挂单数据更新就会有数据推送。 本接口为异步返回，使用`PushClient.quote_depth_changed` 响应深度行情 QuoteDepthData (tigeropen.push.pb.QuoteDepthData\_pb2.QuoteDepthData) 对象；

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| symbols | `list[str]` | 证券代码列表，如 `['AAPL', 'BABA', 'ESmain', 'AAPL 20240209 180.0 CALL']` |

**取消方法参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| symbols | `list[str]` | 证券代码列表，如 `['AAPL', 'BABA']` |

**示例**

Python

    from tigeropen.push.push_client import PushClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    # 初始化PushClient
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'))
    
    #定义回调方法
    def on_quote_depth_changed(frame: QuoteDepthData):
        print(f'quote depth changed: {frame}')
        # 打印价格
        print(f'ask price: {frame.ask.price}')
        # 第一档价格
        print(f'ask price item 0: {frame.ask.price[0]}')
    
        
    #绑定回调方法
    push_client.quote_depth_changed = on_quote_depth_changed
      
    # 建立连接        
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅深度行情
    push_client.subscribe_depth_quote(['AMD', 'MSFT'])
    
    
    # 取消订阅
    push_client.unsubscribe_depth_quote(['AMD', 'MSFT'])
    # 断开连接， 会取消所有订阅
    push_client.disconnect()

**回调数据**

`tigeropen.push.pb.QuoteDepthData_pb2.QuoteDepthData`

> ⚠️
> 
> **CAUTION**
> 
> 此接口最多只会推送买卖前 40 档数据

数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票标的 |
| timestamp | long | 深度数据时间 |
| ask | OrderBook | 卖盘数据 |
| bid | OrderBook | 买盘数据 |

OrderBook的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| price | `list[float]` | 每档价格 |
| volume | `list[int]` | 挂单量 |
| orderCount | `list[int]` | 订单数（只有港股有） |
| exchange | string | 期权数据源（只有期权有值），如果price或volume为0，表示这个数据源的报价已失效。枚举值详见： [期权交易所](./appendix-enum.md#/%E6%9C%9F%E6%9D%83%E4%BA%A4%E6%98%93%E6%89%80) |
| time | long | 期权交易所挂单时间戳只有期权有值） |

**回调数据示例**

深度行情 items 数据示例，相邻档位价格可能一样， 其中 count 是可选的

    symbol: "00700"
    timestamp: 1677742734822
    ask {
      price: 363.8
      price: 364
      price: 364.2
      price: 364.4
      price: 364.6
      price: 364.8
      price: 365
      price: 365.2
      price: 365.4
      price: 365.6
      volume: 26900
      volume: 14800
      volume: 15200
      volume: 31500
      volume: 15800
      volume: 7700
      volume: 29400
      volume: 6300
      volume: 6000
      volume: 5500
      orderCount: 27
      orderCount: 20
      orderCount: 19
      orderCount: 22
      orderCount: 14
      orderCount: 10
      orderCount: 20
      orderCount: 12
      orderCount: 10
      orderCount: 11
    }
    bid {
      price: 363.6
      price: 363.4
      price: 363.2
      price: 363
      price: 362.8
      price: 362.6
      price: 362.4
      price: 362.2
      price: 362
      price: 361.8
      volume: 9400
      volume: 19900
      volume: 35300
      volume: 74200
      volume: 26300
      volume: 16700
      volume: 22500
      volume: 21100
      volume: 40500
      volume: 5600
      orderCount: 16
      orderCount: 23
      orderCount: 36
      orderCount: 79
      orderCount: 30
      orderCount: 32
      orderCount: 31
      orderCount: 34
      orderCount: 143
      orderCount: 26
    }
    

* * *

订阅逐笔成交数据

[](./push-quote.md#%E8%AE%A2%E9%98%85%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4%E6%95%B0%E6%8D%AE)

-----------------------------------------------------------------------------------------------------------------------------------

`Push_client.subscribe_tick(symbols)`

**取消方法**

`PushClient.unsubscribe_tick(symbols)`

**说明**

逐笔成交订阅推送接口是异步接口，通过实现 PushClient.tick\_changed 接口可以获得异步请求结果。 由于tick数据需要处理压缩格式，回调数据类型与其他行情不同，不是原始的protobuf类型，而是转换后的 tigeropen.push.pb.trade\_tick.TradeTick 股票和期货均使用该方法。

**逐笔推送频率为200ms，采用快照方式推送，每次推送最新的50条逐笔记录**。

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| symbols | list\[str\] | 证券代码列表，如 \['AAPL', 'BABA'\] |

**取消方法参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| symbols | list\[str\] | 证券代码列表，如 \['AAPL', 'BABA'\] |

**示例**

Python

    from tigeropen.push.pb.trade_tick import TradeTick
    from tigeropen.push.push_client import PushClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    # 初始化PushClient
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'))
    
    # 订阅回调方法
    def on_tick_changed(data: TradeTick):
        print(f'tick changed: {data}')
    
        
    #绑定回调方法
    push_client.tick_changed = on_tick_changed   
      
    # 建立连接        
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅逐笔成交数据
    push_client.subscribe_tick(['AMD', 'MSFT'])
    # 订阅期货逐笔
    push_client.subscribe_tick(['HSImain', 'CNmain'])
    
    
    time.sleep(10)
    # 取消订阅
    push_client.unsubscribe_tick(['AMD', 'MSFT'])
    # 断开连接，会取消所有订阅
    push_client.disconnect()

**回调数据**

`tigeropen.push.pb.trade_tick.TradeTick`

TradeTick数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | str | 股票标的、期货标的 |
| secType | str | STK/FUT |
| quoteLevel | str | 数据来自的行情权限级别（对于美股，usQuoteBasic的逐笔数据量比usStockQuote的要少）；期货没有级别区分 |
| timestamp | int | 数据时间戳 |
| ticks | `list[TradeTickItem]` | 逐笔成交数据集合 |

ticks的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| sn  | int | 逐笔序号 |
| volume | int | 成交量 |
| tickType | str | \*表示不变，+表示涨，-表示跌（期货逐笔没有） |
| price | double | 成交价 |
| time | int | 交易时间戳 |
| cond | str | 每笔数据的成交条件列表，如果数组为空表示当前批量的每笔都为自动对盘成交（期货逐笔没有） |
| partCode | str | 每笔交易的交易所code（仅美股股票） |
| partName | str | 每笔交易的交易所名（仅美股股票） |

**回调数据示例**

    symbol: "00700"
    type: "-+"
    sn: 37998
    priceBase: 3636
    priceOffset: 1
    time: 1677742815311
    time: 69
    price: 0
    price: 2
    volume: 500
    volume: 100
    quoteLevel: "hkStockQuoteLv2"
    timestamp: 1677742815776
    secType: "STK"
    

* * *

订阅全量逐笔成交数据

[](./push-quote.md#%E8%AE%A2%E9%98%85%E5%85%A8%E9%87%8F%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4%E6%95%B0%E6%8D%AE)

-------------------------------------------------------------------------------------------------------------------------------------------------------

`Push_client.subscribe_tick(symbols)`

**取消方法**

`PushClient.unsubscribe_tick(symbols)`

**说明**

逐笔成交订阅推送接口是异步接口，通过实现 PushClient.full\_tick\_changed 接口可以获得异步请求结果。 回调对象 `tigeropen.push.pb.TickData_pb2.TickData`

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| symbols | `list[str]` | 证券代码列表，如 `['AAPL', 'BABA']` |

同时，修改配置 `client_config.use_full_tick = True`, 开启全量 tick 模式, 并在 `PushClient` 初始化时传入 `client_config=client_config`

**取消方法参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| symbols | `list[str]` | 证券代码列表，如 `['AAPL', 'BABA']` |

**示例**

Python

    from tigeropen.push.pb.TickData_pb2 import TickData
    from tigeropen.push.push_client import PushClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    client_config.use_full_tick = True
    # 初始化PushClient
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'), client_config=client_config)
    
    # 订阅回调方法
    def on_full_tick_changed(frame: TickData):
        print(f'full tick changed: {frame}')
    
        
    #绑定回调方法
    push_client.full_tick_changed = on_full_tick_changed
      
    # 建立连接        
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅逐笔成交数据
    push_client.subscribe_tick(['AMD', 'MSFT', '00700'])
    # 订阅期货逐笔
    push_client.subscribe_tick(['HSImain', 'CNmain'])
    
    
    time.sleep(10)
    # 取消订阅
    push_client.unsubscribe_tick(['AMD', 'MSFT'])
    # 断开连接，会取消所有订阅
    push_client.disconnect()

**回调数据**

`tigeropen.push.pb.TickData_pb2.TickData`

TickData 数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | str | 股票标的、期货标的 |
| source | str | STK/FUT |
| timestamp | int | 数据时间戳 |
| ticks | `list[Tick]` | 逐笔成交数据集合 |

ticks 每一项 Tick 的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| sn  | int | 逐笔序号 |
| volume | int | 成交量 |
| type | str | \*表示不变，+表示涨，-表示跌（期货逐笔没有） |
| price | double | 成交价 |
| time | int | 交易时间戳 |
| partCode | str | 每笔交易的交易所code（仅美股股票） |

**回调数据示例**

    symbol: "NVDA"
    ticks {
      sn: 2381
      time: 1712669401076
      price: 874.1
      volume: 10
      type: "*"
      partCode: "t"
    }
    ticks {
      sn: 2382
      time: 1712669401076
      price: 874.1
      volume: 11
      type: "*"
      partCode: "t"
    }
    ticks {
      sn: 2383
      time: 1712669401076
      price: 874.1
      volume: 3
      type: "*"
      partCode: "t"
    }
    timestamp: 1712669403808
    source: "NLS"
    

* * *

订阅分钟K线数据

[](./push-quote.md#%E8%AE%A2%E9%98%85%E5%88%86%E9%92%9Fk%E7%BA%BF%E6%95%B0%E6%8D%AE)

---------------------------------------------------------------------------------------------------------------------------

`PushClient.subscribe_kline(symbols=None)`

**取消方法**

  

`PushClient.unsubscribe_kline(symbols)`

**说明**

订阅股票的分钟K线数据。

**请求参数**

| 参数  | 类型  | 说明  |
| --- | --- | --- |
| symbols | list\[str\] | 股票代码列表 |

**对应回调接口**

Need to use `PushClient.kline_changed` to respond to the return result

**订阅示例**

Python

    from tigeropen.push.push_client import PushClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'))
    
    def on_kline_changed(frame: KlineData):
        print(f'kline changed: {frame}')
        
    push_client.kline_changed = on_kline_changed
    
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    symbols = ['AAPL']
    
    push_client.subscribe_kline(symbols)

**回调结果示例**

JSON

    {
      "time":"1712584560000",
      "open":168.9779,
      "high":169.0015,
      "low":168.9752,
      "close":169.0,
      "avg":168.778,
      "volume":"3664",
      "count":114,
      "symbol":"AAPL",
      "amount":617820.6508,
      "serverTimestamp":"1712584569746"
    }

* * *

订阅股票热门交易榜

[](./push-quote.md#%E8%AE%A2%E9%98%85%E8%82%A1%E7%A5%A8%E7%83%AD%E9%97%A8%E4%BA%A4%E6%98%93%E6%A6%9C)

---------------------------------------------------------------------------------------------------------------------------------------------

`PushClient.subscribe_stock_top(market, indicators=None)`

**取消方法**

`PushClient.unsubscribe_stock_top(market, indicators=None)`

**说明**

订阅股票的行情榜单数据，**非交易时间不推送，推送间隔为30s，每次推送订阅指标Top30的标的数据**。 推送接口是异步回调，通过实现ApiComposeCallback接口可以获得异步请求结果。 回调接口返回结果类型为`StockTopData`对象。各指标数据按指标值倒排序。

支持美国和香港市场股票指标的订阅，盘中数据榜单有StockRankingIndicator所有指标，美股盘前盘后只有涨幅(changeRate)和5分钟涨幅(changeRate5Min)两个指标的榜单数据

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | str or Market enum | Yes | 市场，支持 HK, US |
| indicators | `list[tigeropen.common.consts.StockRankingIndicator]` | No  | 股票榜单指标，默认为全部指标，参考枚举 StockRankingIndicator的值(changeRate:当天涨幅; changeRate5Min:5分钟涨幅; turnoverRate:换手率; amount:当日成交额; volume:当日成交量; amplitude:当日振幅) |

**回调数据**

需通过`push_client.stock_top_changed`绑定回调方法

StockTopData数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| market | str | 市场:US/HK |
| timestamp | int | 时间戳 |
| topData | `list[TopData]` | 各指标榜单数据列表 |

TopData的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| targetName | str | 指标名(changeRate, changeRate5Min, turnoverRate, amount, volume, amplitude) |
| item | `list[StockItem]` | 该指标维度下的榜单数据列表 |

StockItem的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | str | 标的  |
| latestPrice | float | 最新价 |
| targetValue | float | 对应指标值 |

**示例**

Python

    from tigeropen.push.push_client import PushClient
    from tigeropen.common.consts import OptionRankingIndicator, StockRankingIndicator
    from tigeropen.push.pb.StockTopData_pb2 import StockTopData
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    # 初始化PushClient
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'))
    
    #定义回调方法
    def on_stock_top_changed(frame: StockTopData):
        print(f'stock top changed: {frame}')
    
    # 绑定回调方法
    push_client.stock_top_changed = on_stock_top_changed
    
    # 建立连接        
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅
    push_client.subscribe_stock_top("HK", [StockRankingIndicator.Amount])
    
    # 取消订阅
    push_client.unsubscribe_stock_top("HK", [StockRankingIndicator.Amount])
    
    

**回调数据示例**

    topData {
      targetName: "amount"
      item {
        symbol: "02800"
        latestPrice: 19.55
        targetValue: 3338633741
      }
      item {
        symbol: "00700"
        latestPrice: 338.8
        targetValue: 2950736047
      }
      item {
        symbol: "02828"
        latestPrice: 66.34
        targetValue: 1533436182
      }
      item {
        symbol: "09988"
        latestPrice: 85.2
        targetValue: 1396707122
      }
      item {
        symbol: "02269"
        latestPrice: 37.1
        targetValue: 1238930730
      }
      item {
        symbol: "03690"
        latestPrice: 127.9
        targetValue: 1167532142
      }
      item {
        symbol: "01211"
        latestPrice: 262.6
        targetValue: 716540400
      }
      item {
        symbol: "03033"
        latestPrice: 3.916
        targetValue: 629691374
      }
      item {
        symbol: "01357"
        latestPrice: 3.29
        targetValue: 589222680
      }
      item {
        symbol: "02318"
        latestPrice: 50.25
        targetValue: 572686837
      }
      item {
        symbol: "01299"
        latestPrice: 80.25
        targetValue: 510098294
      }
      item {
        symbol: "09888"
        latestPrice: 139.7
        targetValue: 504564066
      }
      item {
        symbol: "00388"
        latestPrice: 303.8
        targetValue: 488918091
      }
      item {
        symbol: "07226"
        latestPrice: 4.85
        targetValue: 477161727
      }
      item {
        symbol: "01398"
        latestPrice: 4.16
        targetValue: 459215853
      }
      item {
        symbol: "02331"
        latestPrice: 43.2
        targetValue: 439082885
      }
      item {
        symbol: "02015"
        latestPrice: 137.8
        targetValue: 407068273
      }
      item {
        symbol: "09618"
        latestPrice: 143.9
        targetValue: 389690725
      }
      item {
        symbol: "07552"
        latestPrice: 6.59
        targetValue: 387550625
      }
      item {
        symbol: "01024"
        latestPrice: 54.85
        targetValue: 350567971
      }
      item {
        symbol: "00981"
        latestPrice: 20.65
        targetValue: 349594737
      }
      item {
        symbol: "00386"
        latestPrice: 4.47
        targetValue: 347819789
      }
      item {
        symbol: "00883"
        latestPrice: 11.12
        targetValue: 320431605
      }
      item {
        symbol: "09868"
        latestPrice: 44.05
        targetValue: 292605044
      }
      item {
        symbol: "02020"
        latestPrice: 81.95
        targetValue: 285153726
      }
      item {
        symbol: "03968"
        latestPrice: 36.25
        targetValue: 273604906
      }
      item {
        symbol: "00939"
        latestPrice: 5.05
        targetValue: 270755731
      }
      item {
        symbol: "01088"
        latestPrice: 23.6
        targetValue: 265332533
      }
      item {
        symbol: "00020"
        latestPrice: 2.14
        targetValue: 256621941
      }
      item {
        symbol: "00941"
        latestPrice: 63.25
        targetValue: 248440129
      }
    }
    

* * *

订阅期权热门交易榜

[](./push-quote.md#%E8%AE%A2%E9%98%85%E6%9C%9F%E6%9D%83%E7%83%AD%E9%97%A8%E4%BA%A4%E6%98%93%E6%A6%9C)

---------------------------------------------------------------------------------------------------------------------------------------------

`PushClient.subscribe_option_top(market, indicators=None)`

**取消方法**

`PushClient.unsubscribe_option_top(market, indicators=None)`

**说明**

订阅期权的行情榜单数据，**非交易时间不推送，推送间隔为30s，每次推送订阅指标Top50的标的数据**。 推送接口是异步回调，通过实现ApiComposeCallback接口可以获得异步请求结果。 回调接口返回结果类型为`OptionTopData`对象。其中异动大单为单笔成交量大于1000，按成交时间倒排序的数据，其他指标数据为交易日累计指标值的倒序数据。

支持美国市场期权指标的订阅，盘中数据榜单有OptionRankingIndicator所有指标。

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| market | str or Market enum | Yes | 市场，支持 HK, US |
| indicators | `list[tigeropen.common.consts.OptionRankingIndicator]` | No  | 期权榜单指标，默认为全部指标，参考枚举OptionRankingIndicator的值(bigOrder:异动大单, volume:当日累计成交量, amount:当日累计成交额, openInt:未平仓量) |

**回调数据**

需通过`push_client.option_top_changed`绑定回调方法

OptionTopData数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| market | string | 市场:US |
| timestamp | long | 时间戳 |
| topData | `list[TopData]` | 各指标榜单数据列表 |

TopData的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| targetName | string | 指标名(bigOrder, volume, amount, openInt) |
| bigOrder | `list[BigOrder]` | 异动大单指标(bigOrder)数据列表 |
| item | `list[OptionItem]` | 该指标维度下的榜单数据列表 |

BigOrder的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | str | 股票ETF标的 |
| expiry | str | 过期日，格式：yyyyMMdd |
| strike | str | 行权价 |
| right | str | CALL/PUT |
| dir | str | 买卖方向：BUY/SELL/NONE |
| volume | float | 成交量 |
| price | float | 成交价 |
| amount | float | 成交额 |
| tradeTime | int | 成交时间戳 |

OptionItem的数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | str | 股票ETF标的 |
| expiry | str | 过期日，格式：yyyyMMdd |
| strike | str | 行权价 |
| right | str | CALL/PUT |
| totalAmount | float | 成交额 |
| totalVolume | float | 成交量 |
| totalOpenInt | float | 未平仓量 |
| volumeToOpenInt | float | 成交量/未平仓量 |
| latestPrice | float | 最新价 |
| updateTime | int | 指标数据更新时间戳 |

**示例**

Python

    from tigeropen.push.push_client import PushClient
    from tigeropen.common.consts import OptionRankingIndicator
    from tigeropen.push.pb.OptionTopData_pb2 import OptionTopData
    
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    # 初始化PushClient
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'))
    
    #定义回调方法
    def on_option_top_changed(frame: OptionTopData):
        print(f'option top changed: {frame}')
    
    # 绑定回调方法
    push_client.option_top_changed = on_option_top_changed
    
    # 建立连接        
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅
    push_client.subscribe_option_top("US", [OptionRankingIndicator.Amount])
    
    # 取消订阅
    push_client.unsubscribe_option_top("US", [OptionRankingIndicator.Amount])
    
    

**回调数据示例**

JSON

    {
        "market":"US",
        "timestamp":"1687277160445",
        "topData":[\
            {\
                "targetName":"volume",\
                "item":[\
                    {\
                        "symbol":"SPY",\
                        "expiry":"20230620",\
                        "strike":"435.0",\
                        "right":"PUT",\
                        "totalAmount":5394115,\
                        "totalVolume":212478,\
                        "totalOpenInt":16377,\
                        "volumeToOpenInt":0.012467,\
                        "latestPrice":0.25,\
                        "updateTime":"1687277254390"\
                    },\
                    {\
                        "symbol":"SPY",\
                        "expiry":"20230620",\
                        "strike":"436.0",\
                        "right":"PUT",\
                        "totalAmount":7754077,\
                        "totalVolume":194423,\
                        "totalOpenInt":13403,\
                        "volumeToOpenInt":0.011408,\
                        "latestPrice":0.58,\
                        "updateTime":"1687277213603"\
                    },\
                    {\
                        "symbol":"SPY",\
                        "expiry":"20230620",\
                        "strike":"437.0",\
                        "right":"PUT",\
                        "totalAmount":10420625,\
                        "totalVolume":182078,\
                        "totalOpenInt":13973,\
                        "volumeToOpenInt":0.010683,\
                        "latestPrice":1.17,\
                        "updateTime":"1687277213602"\
                    },\
                    {\
                        "symbol":"SPY",\
                        "expiry":"20230620",\
                        "strike":"438.0",\
                        "right":"CALL",\
                        "totalAmount":4482482,\
                        "totalVolume":181899,\
                        "totalOpenInt":961,\
                        "volumeToOpenInt":0.010673,\
                        "latestPrice":0.09,\
                        "updateTime":"1687277213603"\
                    },\
                    {\
                        "symbol":"SPY",\
                        "expiry":"20230620",\
                        "strike":"436.0",\
                        "right":"CALL",\
                        "totalAmount":7331667,\
                        "totalVolume":150604,\
                        "totalOpenInt":238,\
                        "volumeToOpenInt":0.008837,\
                        "latestPrice":0.66,\
                        "updateTime":"1687277208599"\
                    }\
                    // ......\
                ]\
            },\
            {\
                "targetName":"amount",\
                "item":[\
                    {\
                        "symbol":"TSLA",\
                        "expiry":"20230721",\
                        "strike":"5.0",\
                        "right":"CALL",\
                        "totalAmount":34061561,\
                        "totalVolume":1812,\
                        "totalOpenInt":18,\
                        "volumeToOpenInt":0.00023,\
                        "latestPrice":259.99,\
                        "updateTime":"1687276953360"\
                    },\
                    {\
                        "symbol":"TSLA",\
                        "expiry":"20230721",\
                        "strike":"500.0",\
                        "right":"PUT",\
                        "totalAmount":30877216,\
                        "totalVolume":1960,\
                        "volumeToOpenInt":0.000248,\
                        "latestPrice":234.97,\
                        "updateTime":"1687276953360"\
                    },\
                    {\
                        "symbol":"TSLA",\
                        "expiry":"20230623",\
                        "strike":"265.0",\
                        "right":"CALL",\
                        "totalAmount":27928028,\
                        "totalVolume":66361,\
                        "totalOpenInt":12928,\
                        "volumeToOpenInt":0.008405,\
                        "latestPrice":6.5,\
                        "updateTime":"1687277264395"\
                    },\
                    {\
                        "symbol":"SPY",\
                        "expiry":"20230721",\
                        "strike":"420.0",\
                        "right":"CALL",\
                        "totalAmount":21629503,\
                        "totalVolume":11105,\
                        "totalOpenInt":46931,\
                        "volumeToOpenInt":0.000652,\
                        "latestPrice":19.27,\
                        "updateTime":"1687273142271"\
                    },\
                    {\
                        "symbol":"TSLA",\
                        "expiry":"20230623",\
                        "strike":"270.0",\
                        "right":"CALL",\
                        "totalAmount":17657903,\
                        "totalVolume":61012,\
                        "totalOpenInt":14302,\
                        "volumeToOpenInt":0.007728,\
                        "latestPrice":4.52,\
                        "updateTime":"1687277254390"\
                    }\
                    // ......\
                ]\
            },\
            {\
                "targetName":"openInt",\
                "item":[\
                    {\
                        "symbol":"AMC",\
                        "expiry":"20230721",\
                        "strike":"10.0",\
                        "right":"CALL",\
                        "totalAmount":4933,\
                        "totalVolume":750,\
                        "totalOpenInt":340843,\
                        "volumeToOpenInt":0.00022,\
                        "latestPrice":0.1,\
                        "updateTime":"1687276788220"\
                    },\
                    {\
                        "symbol":"AMC",\
                        "expiry":"20230721",\
                        "strike":"10.0",\
                        "right":"PUT",\
                        "totalVolume":1,\
                        "totalOpenInt":321814,\
                        "latestPrice":6.2,\
                        "updateTime":"1687276853278"\
                    },\
                    {\
                        "symbol":"AMC",\
                        "expiry":"20230721",\
                        "strike":"4.0",\
                        "right":"PUT",\
                        "totalAmount":117982,\
                        "totalVolume":2748,\
                        "totalOpenInt":242101,\
                        "volumeToOpenInt":0.000806,\
                        "latestPrice":0.81,\
                        "updateTime":"1687277034280"\
                    },\
                    {\
                        "symbol":"ATVI",\
                        "expiry":"20240119",\
                        "strike":"85.0",\
                        "right":"PUT",\
                        "totalAmount":3500,\
                        "totalVolume":26,\
                        "totalOpenInt":230702,\
                        "volumeToOpenInt":0.000016,\
                        "latestPrice":7,\
                        "updateTime":"1687274092822"\
                    },\
                    {\
                        "symbol":"EEM",\
                        "expiry":"20231215",\
                        "strike":"47.0",\
                        "right":"CALL",\
                        "totalAmount":310,\
                        "totalVolume":15,\
                        "totalOpenInt":183054,\
                        "volumeToOpenInt":0.000003,\
                        "latestPrice":0.18,\
                        "updateTime":"1687269619956"\
                    }\
                    // ......\
                ]\
            },\
            {\
                "targetName":"bigOrder",\
                "bigOrder":[\
                    {\
                        "symbol":"AMC",\
                        "expiry":"20230818",\
                        "strike":"10.0",\
                        "right":"PUT",\
                        "dir":"Buy",\
                        "volume":1000,\
                        "price":6.94,\
                        "amount":694000,\
                        "tradeTime":"1687276860753"\
                    },\
                    {\
                        "symbol":"GLPI",\
                        "expiry":"20230818",\
                        "strike":"50.0",\
                        "right":"CALL",\
                        "dir":"Sell",\
                        "volume":1094,\
                        "price":1.2,\
                        "amount":131280,\
                        "tradeTime":"1687276744519"\
                    },\
                    {\
                        "symbol":"AMD",\
                        "expiry":"20230818",\
                        "strike":"140.0",\
                        "right":"CALL",\
                        "dir":"Buy",\
                        "volume":1700,\
                        "price":3.25,\
                        "amount":552500,\
                        "tradeTime":"1687276467421"\
                    },\
                    {\
                        "symbol":"AAPL",\
                        "expiry":"20230915",\
                        "strike":"185.0",\
                        "right":"PUT",\
                        "dir":"Sell",\
                        "volume":1500,\
                        "price":6.65,\
                        "amount":997500,\
                        "tradeTime":"1687276413267"\
                    },\
                    {\
                        "symbol":"BABA",\
                        "expiry":"20240119",\
                        "strike":"75.0",\
                        "right":"PUT",\
                        "dir":"Sell",\
                        "volume":1500,\
                        "price":4.8,\
                        "amount":720000,\
                        "tradeTime":"1687276036749"\
                    }\
                    // ......\
                ]\
            }\
        ]
    }

* * *

查询已订阅的标的列表

[](./push-quote.md#%E6%9F%A5%E8%AF%A2%E5%B7%B2%E8%AE%A2%E9%98%85%E7%9A%84%E6%A0%87%E7%9A%84%E5%88%97%E8%A1%A8)

-------------------------------------------------------------------------------------------------------------------------------------------------------

`PushClient.query_subscribed_quote()`

**说明**

查询已订阅的标的列表

本接口为异步返回， 需要使用`PushClient.query_subscribed_callback`响应返回结果

**参数**

无

**回调数据**

需要使用`PushClient.query_subscribed_callback`响应返回结果

回调数据结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| limit | int | 订阅行情标的（股票、期权、期货）限制最大数 |
| used | int | 已订阅行情标的（股票、期权、期货）数 |
| subscribedSymbols | list | 已订阅行情标的（股票、期权、期货）标的 |
| askBidLimit | int | 订阅深度行情股票限制最大数 |
| askBidUsed | int | 已订阅深度行情股票数 |
| subscribedAskBidSymbols | list | 已订阅深度行情股票标的 |
| tradeTickLimit | int | 订阅逐笔成交股票限制最大数 |
| tradeTickUsed | int | 已订阅逐笔成交股票数 |
| subscribedTradeTickSymbols | list | 已订阅逐笔成交股票标的 |

**示例**

Python

    from tigeropen.push.push_client import PushClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    # 初始化PushClient
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'), use_protobuf=True)
    
    #定义回调方法
    def query_subscribed_callback(data):
        """
        data example:
            {'subscribed_symbols': ['QQQ'], 'limit': 1200, 'used': 1, 'symbol_focus_keys': {'qqq': ['open', 'prev_close', 'low', 'volume', 'latest_price', 'close', 'high']},
             'subscribed_quote_depth_symbols': ['NVDA'], 'quote_depth_limit': 20, 'quote_depth_used': 1,
             'subscribed_trade_tick_symbols': ['QQQ', 'AMD', '00700'], 'trade_tick_limit': 1200, 'trade_tick_used': 3}
        """
        print(f'subscribed data:{data}')
        print(f'subscribed symbols:{data["subscribed_symbols"]}')
        
    
    #绑定回调方法
    push_client.query_subscribed_callback = query_subscribed_callback   
      
    # 建立连接        
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅行情
    push_client.subscribe_quote(['QQQ'])
    # 订阅深度行情
    push_client.subscribe_depth_quote(['NVDA'])
    push_client.subscribe_depth_quote(['HSImain'])
    
    # 订阅tick数据
    push_client.subscribe_tick(['QQQ'])
    push_client.subscribe_tick(['HSImain'])
    
    # 查询已订阅合约
    push_client.query_subscribed_quote()
    
    time.sleep(10)

**回调数据示例**

Python

    {'subscribed_symbols': ['QQQ'], 'limit': 1200, 'used': 1,
     'subscribed_ask_bid_symbols': ['NVDA'], 'ask_bid_limit': 20, 'ask_bid_used': 1,
     'subscribed_trade_tick_symbols': ['QQQ', 'AMD', '00700'], 'trade_tick_limit': 1200, 'trade_tick_used': 3
     }
