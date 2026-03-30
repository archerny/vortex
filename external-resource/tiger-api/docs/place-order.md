# 下单交易

place\_order 下单

[](./place-order.md#place_order-%E4%B8%8B%E5%8D%95)

-------------------------------------------------------------------------------------------------

`TradeClient.place_order():`

**说明**

交易下单接口。关于如何选择标的、订单类型、方向数量等，请见下方说明。

**请在运行程序前结合本文档的[用户与账户类型](./account-type.md)
 部分及[订单与交易规则](./trade-rules.md)
 部分，检查您的账户是否支持所请求的订单，以及确认是否是实盘（综合）或者模拟账户，并检查交易规则是否允许在程序运行时段对特定标的下单**。若下单失败，可首先阅读文档[错误码](./error-code.md)
 部分排查

`place_order` 调用成功后，参数里的order对象的订单id即被填充（`order.id`)，可用于后续的查询或撤单，**此时此方法会返回订单id，但此时只表示订单提交成功，不代表订单已被成功执行**，订单的执行是异步的，提交订单后，订单会根据情况进入下一步状态，比如成交、被拒绝等。因此，建议在对订单进行下一步操作之前，使用`get_order`或`get_orders`方法查询对应订单的状态，

> ⚠️
> 
> **CAUTION**
> 
> 1.  市价单（MKT）和止损单（STP）不支持盘前盘后阶段交易，在调用下单接口时，需要把 outside\_rth 设置为 false
> 2.  可做空标的，目前不支持锁仓，无法同时持有同一标的的多头与空头头寸
> 3.  附加订单的主订单类型，目前仅支持限价单
> 4.  市价单（MKT）和模拟账号，不支持将参数 time\_in\_force 设置为 GTC
> 5.  模拟账号暂不支持窝轮和牛熊证的订单

**订单状态说明**

1.  如何判断综合和模拟账号的部分成交状态？
    
    当订单状态不是 FILLED（有可能是NEW，CANCELLED，EXPIRED，REJECTED其中一种）时，都有可能是部分成交的状态，可以通过订单成交数量是否大于 0 来判断
    
2.  如何判断环球账号部分成交状态？
    
    订单状态是 FILLED，且订单成交数量大于 0
    

**订单状态变化流程:**

![](https://files.readme.io/9c616da7edf6dd72479f15e0874a9365d382676f30fe6a55f80dc827d15a48c6-img_v3_02pn_821009a8-a4e6-4bc7-b1a2-6d2bd910002g.png)

**其他说明**

*   美国市场部分指数期权合约（如 SPXW），以及 IWM/SPY/QQQ 等 ETF 期权，支持交易周内（周一至周五）到期的期权合约（具体可交易到期日以交易所实际挂牌为准）。
*   禁止直接开立反向头寸。例如，当前持有 100 股多头头寸时，直接卖出 200 股（意图建立 100 股净空头）的操作将被拒绝，需要先平掉现有的 100 股多头头寸，再进行新的卖出操作。

**参数**

`Order`对象 ([`tigeropen.trade.domain.order.Order`](./appendix-object-detail.md#order)
)

可用 `tigeropen.common.util.order_utils` 下的工具函数，如 `limit_order()`, `market_order()`, 根据您需要的具体订单类型和参数，在本地生成订单对象。创建方法详见[Order对象-构建方法](./appendix-object-detail.md#orderbuild)
部分，或者用 `TradeClient.create_order()` 向服务端请求订单号，然后生成订单对象(不推荐)

**参数**

`Order`对象的核心参数按功能分组如下：

**必填参数（所有订单类型）**

| 参数  | 类型  | 描述  |
| --- | --- | --- |
| account | str | 用户授权账户 |
| symbol | str | 股票代码，如 AAPL；窝轮牛熊证时为5位数字代码 |
| sec\_type | str | 合约类型（STK:股票 OPT:期权 WAR:港股窝轮 IOPT:港股牛熊证 FUT:期货 CC:数字货币） |
| action | str | 交易方向 BUY/SELL |
| order\_type | str | 订单类型：MKT（市价单）/ LMT（限价单）/ STP（止损单）/ STP\_LMT（止损限价单）/ TRAIL（跟踪止损单） |
| quantity | int | 订单数量（港股、沪港通、窝轮、牛熊证有最小数量限制） |

**价格参数（按订单类型）**

| 参数  | 类型  | 描述  | 适用订单类型 |
| --- | --- | --- | --- |
| limit\_price | float | 限价  | LMT, STP\_LMT **必填** |
| aux\_price | float | 止损触发价；TRAIL 订单中为跟踪额 | STP, STP\_LMT **必填**；TRAIL 选填 |
| trailing\_percent | float | 跟踪止损百分比，与 aux\_price 互斥，优先使用 | TRAIL 选填 |
| adjust\_limit | float | 价格微调幅度（默认0不调整，正数向上，负数向下），如 0.001 代表向上调整不超过 0.1% | LMT, STP, STP\_LMT, TRAIL 选填 |

**交易时段与有效期**

| 参数  | 类型  | 描述  |
| --- | --- | --- |
| outside\_rth | bool | 是否允许盘前盘后交易（美股专属），默认为允许。市价单、止损单、跟踪止损单只在盘中有效，将忽略此参数 |
| trading\_session\_type | TradingSessionType | 美股订单时段（仅限价单）。枚举值[订单时段](./appendix-enum.md#tradingsessiontype) |
| time\_in\_force | str | 订单有效期：DAY（当日有效）/ GTC（取消前有效，最长180天）/ GTD（指定时间前有效），默认 DAY |
| expire\_time | int | GTD 订单过期时间，13位时间戳（time\_in\_force 为 GTD 时必填） |

**合约标识（通常由 Contract 对象携带）**

| 参数  | 类型  | 描述  |
| --- | --- | --- |
| market | str | 市场（US 美股 / HK 港股 / CN 沪港通） |
| currency | str | 货币（USD / HKD / CNH） |
| exchange | str | 交易所（美股 SMART / 港股 SEHK / 沪港通 SEHKNTL / 深港通 SEHKSZSE） |
| expiry | str | 过期日（期权、窝轮、牛熊证专属） |
| strike | str | 行权价格（期权、窝轮、牛熊证专属） |
| right | str | 期权方向 PUT/CALL（期权、窝轮、牛熊证专属） |
| multiplier | float | 每手数量（期权、窝轮、牛熊证专属） |
| local\_symbol | str | 港交所代码，窝轮牛熊证**必填**，在App列表中名称下方的5位数字 |

**其他参数**

| 参数  | 类型  | 描述  |
| --- | --- | --- |
| order\_id | int | 订单编号，防止重复下单。传 0 则服务端自动生成（无法防重复） |
| quantity\_scale | int | 下单数量的偏移量（碎股专用），默认为 0 |
| total\_cash\_amount | float | 下单总金额（仅按金额下单时使用） |
| secret\_key | str | 交易员密钥（机构用户专用） |
| user\_mark | str | 下单备注信息，下单后不可修改 |

*   附加订单参数
    
    > 附加订单（Attached Order ）是指能通过附加的子订单对主订单起到止盈或止损效果的订单，可以附加的子订单类型有限价单（可用于止盈）、止损限价单/止损单（可用于止损）。通过增加以下参数可以实现附加订单
    
    | 参数  | 类型  | 描述  | 附加止损 | 附加止盈 | 限价/止损/止损限价（仅适用于一取消所有） |
    | --- | --- | --- | --- | --- | --- |
    | leg\_type | str | 附加订单类型. 'PROFIT' 止盈单类型, 'LOSS' 止损单类型 | 必填  | 必填  | 必填  |
    | price | float | 附加订单价格 | 必填  | 必填  | 必填  |
    | time\_in\_force | str | 附加订单有效期. 'DAY'（当日有效）和'GTC'（取消前有效 Good-Til-Canceled) | 选填  | 选填  | 选填  |
    | outside\_rth | bool | 附加订单是否允许盘前盘后交易(美股专属). True 允许, False 不允许 | 选填  | 选填  | 选填  |
    | limit\_price | float | 限价订单价格 | 不填  | 不填  | LMT 和 STP\_LMT 必填 |
    | trailing\_percent | float | "追踪止损订单-止损百分比，当使用追踪止损订单时，必须填写止损百分比（stopLossTrailingPercent）或止损金额（stopLossTrailingAmount）之一，如果两者都填写，则以止损百分比作为参数 | 选填  | 不填  | 不填  |
    | trailing\_amount | float | 追踪止损订单-止损金额，当使用追踪止损订单时，必须填写止损百分比（stopLossTrailingPercent）或止损金额（stopLossTrailingAmount）之一，如果两者都填写，则以止损百分比作为参数 | 选填  | 不填  | 不填  |
    | quantity | int | 订单数量 | 必填  | 必填  | 必填  |
    

  

*   TWAP/VWAP订单参数
    
    > TWAP订单适用于美股股票与期权，VWAP订单则专用于美股股票。请注意，这两种订单类型仅在盘中交易时段有效，不支持预挂单。
    
    | 参数  | 类型  | 算法参数 | 描述  | TWAP | VWAP |
    | --- | --- | --- | --- | --- | --- |
    | order\_type | str |     | 订单类型，TWAP/VWAP | 必填  | 必填  |
    | account | str |     | 资金账号 | 必填  | 必填  |
    | symbol | str |     | 股票代码 如：AAPL | 必填  | 必填  |
    | sec\_type | str |     | VWAP只支持STK，TWAP支持STK和OPT | 必填  | 必填  |
    | total\_quantity | int |     | 订单数量 | 必填  | 必填  |
    | algo\_params | AlgoParams |     | 算法参数, 通过`tigeropen.common.util.order_utils.algo_order_params`生成 | 选填  | 选填  |
    | \-  | int | start\_time | 策略开始时间(时间戳) | 选填  | 选填  |
    | \-  | int | end\_time | 策略结束时间(时间戳) | 选填  | 选填  |
    | \-  | float | participation\_rate | 最大参与率(成交量为日均成交量的最大比例，0.01-0.5) | 不填  | 选   |
    

**返回**

如果下单成功则返回订单id，失败则抛出异常。 若成功下单，则参数Order对象的id会被填充为实际的订单号

* * *

构建合约对象示例

[](./place-order.md#%E6%9E%84%E5%BB%BA%E5%90%88%E7%BA%A6%E5%AF%B9%E8%B1%A1%E7%A4%BA%E4%BE%8B)

------------------------------------------------------------------------------------------------------------------------------------

[Contract](./appendix-object-detail.md#contract)

Python

    from tigeropen.common.util.contract_utils import stock_contract, option_contract, option_contract_by_symbol, \
        future_contract, war_contract_by_symbol, iopt_contract_by_symbol, cc_contract
    
    # 美股
    contract = stock_contract(symbol='TIGR', currency='USD')
    
    # 港股
    contract = stock_contract(symbol='00700', currency='HKD')
    
    # 期权
    contract = option_contract(identifier='AAPL  190118P00160000')
    contract = option_contract_by_symbol('JD', expiry='20211015', strike=45.0, put_call='PUT', currency='USD')
    
    # 期货
    # 综合/模拟
    from tigeropen.common.util.contract_utils import future_contract
    contract = future_contract(symbol='CL2312', currency='USD')
    # 环球
    from tigeropen.common.util.contract_utils import future_contract
    contract = future_contract(symbol='CL', currency='USD', expiry='20190328', multiplier=1.0, exchange='SGX')
    
    # 港股窝轮
    contract = war_contract_by_symbol('01810', '20221116', 14.52, 'CALL', local_symbol='14759', multiplier=2000,
                                      currency='HKD')
    # 港股牛熊证
    contract = iopt_contract_by_symbol('02318', '20200420', 87.4, 'CALL', local_symbol='63379', currency='HKD')
    
    # 数字货币
    contract = cc_contract('BTC')
    

* * *

限价单 (LMT)

[](./place-order.md#%E9%99%90%E4%BB%B7%E5%8D%95-lmt)

--------------------------------------------------------------------------------------------

[limit\_order](./appendix-object-detail.md#limit_order)

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import limit_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 生成股票合约
    contract = stock_contract(symbol='AAPL', currency='USD')
    # 生成订单对象
    order = limit_order(account=client_config.account, contract=contract, action='BUY', limit_price=0.1, quantity=1)
    
    # 下单
    oid = trade_client.place_order(order)
    
    print(order)
    # >>> Order({'account': '111111', 'id': 2498911111111111111, 'order_id': None, 'parent_id': None, 'order_time': None, 'reason': None, 'trade_time': None, 'action': 'BUY', 'quantity': 1, 'filled': 0, 'avg_fill_price': 0, 'commission': None, 'realized_pnl': None, 'trail_stop_price': None, 'limit_price': 0.1, 'aux_price': None, 'trailing_percent': None, 'percent_offset': None, 'order_type': 'LMT', 'time_in_force': None, 'outside_rth': None, 'order_legs': None, 'algo_params': None, 'secret_key': None, 'contract': AAPL/STK/USD, 'status': 'NEW', 'remaining': 1})
    print(order.status)  # 订单状态
    print(order.reason)  # 若下单失败，reason为失败原因
    
    # 若下单成功，则 order.id 为订单的id，此后可用该id查询订单或撤单
    my_order = trade_client.get_order(id=order.id)
    oid = trade_client.cancel_order(id=order.id)
    # 或操作order对象进行改单
    trade_client.modify_order(order, limit_price=190.5)
    

* * *

市价单 (MKT)

[](./place-order.md#%E5%B8%82%E4%BB%B7%E5%8D%95-mkt)

--------------------------------------------------------------------------------------------

[market\_order](./appendix-object-detail.md#market_order)

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import market_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 生成股票合约
    contract = stock_contract(symbol='AAPL', currency='USD')
    # 生成订单对象
    order = market_order(account=client_config.account, contract=contract, action='BUY', quantity=1)
    
    # 下单
    oid = trade_client.place_order(order)

* * *

按金额市价订单 (MKT)

[](./place-order.md#%E6%8C%89%E9%87%91%E9%A2%9D%E5%B8%82%E4%BB%B7%E8%AE%A2%E5%8D%95-mkt)

------------------------------------------------------------------------------------------------------------------------------------

[market\_order](./appendix-object-detail.md#market_order)

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import market_order_by_amount
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # generate stock contracts
    contract = stock_contract(symbol='AAPL', currency='USD')
    # generate order object
    order = market_order_by_amount(account=client_config.account, contract=contract, action='BUY', amount =100)
    
    # order
    oid = trade_client.place_order(order)

* * *

止损单 (STP)

[](./place-order.md#%E6%AD%A2%E6%8D%9F%E5%8D%95-stp)

--------------------------------------------------------------------------------------------

[stop\_order](./appendix-object-detail.md#stop_order)

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import stop_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 生成股票合约
    contract = stock_contract(symbol='AAPL', currency='USD')
    # 生成订单对象
    order = stop_order(account=client_config.account, contract=contract, action='SELL', aux_price=1, quantity=1)
    
    # 下单
    oid = trade_client.place_order(order)

* * *

限价止损单(STP\_LMT)

[](./place-order.md#%E9%99%90%E4%BB%B7%E6%AD%A2%E6%8D%9F%E5%8D%95stp_lmt)

-----------------------------------------------------------------------------------------------------------------------

[stop\_limit\_order](./appendix-object-detail.md#stop_limit_order)

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import stop_limit_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 生成股票合约
    contract = stock_contract(symbol='AAPL', currency='USD')
    # 生成订单对象
    order = stop_limit_order(account=client_config.account, contract=contract, action='SELL', limit_price=200.0, aux_price=180.0, quantity=1)
    
    # 下单
    oid = trade_client.place_order(order)

* * *

移动止损单(TRAIL)

[](./place-order.md#%E7%A7%BB%E5%8A%A8%E6%AD%A2%E6%8D%9F%E5%8D%95trail)

------------------------------------------------------------------------------------------------------------------

[trail\_order](./appendix-object-detail.md#trail_order)

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import trail_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 生成股票合约
    contract = stock_contract(symbol='AAPL', currency='USD')
    # 生成订单对象
    order = trail_order(account=client_config.account, contract=contract, action='SELL', quantity=1, trailing_percent=8.0, )
    
    # 下单
    oid = trade_client.place_order(order)

* * *

附加订单

[](./place-order.md#%E9%99%84%E5%8A%A0%E8%AE%A2%E5%8D%95)

--------------------------------------------------------------------------------------------

[order\_leg](./appendix-object-detail.md#order_leg)

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import limit_order, limit_order_with_legs, order_leg, algo_order_params, \
        algo_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    contract = stock_contract(symbol='BILI', currency=Currency.USD)
    # contract = option_contract(identifier='TCH.HK  260629C00500000')
    
    # 限价单 + 附加订单 (仅主订单为限价单时支持附加订单)
    stop_loss_order_leg = order_leg('LOSS', 8.0, time_in_force='GTC',outside_rth=False)  # 附加止损
    stop_loss_order_leg1 = order_leg('LOSS', 8.0, limit_price=7.5,outside_rth=False)  # 附加止损限价单
    stop_loss_order_leg2 = order_leg('LOSS', 8.0, trailing_percent=0.8,outside_rth=False)  # 附加止损追踪单(按比例)
    stop_loss_order_leg3 = order_leg('LOSS', 8.0, trailing_amount=2.0,outside_rth=False)  # 附加止损追踪单(按金额)
    
    profit_taker_order_leg = order_leg('PROFIT', 12.0, time_in_force='GTC',outside_rth=False)  # 附加止盈
    main_order = limit_order_with_legs(account, contract, 'BUY', 100, limit_price=10.0,
    order_legs=[profit_taker_order_leg, stop_loss_order_leg])
    oid = trade_client.place_order(main_order)
    # 查询主订单所关联的附加订单
    order_legs = trade_client.get_open_orders(account, parent_id=main_order.id)
    print(order_legs)
    

### 

主订单+附加止损单

[](./place-order.md#%E4%B8%BB%E8%AE%A2%E5%8D%95%E9%99%84%E5%8A%A0%E6%AD%A2%E6%8D%9F%E5%8D%95)

附加的止损单可以为市价单，限价单，追踪止损单

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import limit_order, limit_order_with_legs, order_leg, algo_order_params, \
        algo_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    contract = stock_contract(symbol='BILI', currency=Currency.USD)
    # contract = option_contract(identifier='TCH.HK  260629C00500000')
    
    
    # 限价单 + 附加订单 (仅主订单为限价单时支持附加订单)
    stop_loss_order_leg = order_leg('LOSS', 8.0, time_in_force='GTC',outside_rth=False)  # 附加止损市价单
    stop_loss_order_leg1 = order_leg('LOSS', 8.0, limit_price=7.5,outside_rth=False)  # 附加止损限价单
    stop_loss_order_leg2 = order_leg('LOSS', 8.0, trailing_percent=0.8,outside_rth=False)  # 附加止损追踪单(按比例)
    stop_loss_order_leg3 = order_leg('LOSS', 8.0, trailing_amount=2.0,outside_rth=False)  # 附加止损追踪单(按金额)
    
    main_order = limit_order_with_legs(account, contract, 'BUY', 100, limit_price=10.0,
    order_legs=[stop_loss_order_leg])
    oid = trade_client.place_order(main_order)
    # 查询主订单所关联的附加订单
    order_legs = trade_client.get_open_orders(account, parent_id=main_order.id)
    print(order_legs)

### 

主订单+附加止盈单

[](./place-order.md#%E4%B8%BB%E8%AE%A2%E5%8D%95%E9%99%84%E5%8A%A0%E6%AD%A2%E7%9B%88%E5%8D%95)

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import limit_order, limit_order_with_legs, order_leg, algo_order_params, \
        algo_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    contract = stock_contract(symbol='BILI', currency=Currency.USD)
    # contract = option_contract(identifier='TCH.HK  260629C00500000')
    
    # 限价单 + 附加订单 (仅主订单为限价单时支持附加订单)
    profit_taker_order_leg = order_leg('PROFIT', 12.0, time_in_force='GTC',outside_rth=False)  # 附加止盈
    main_order = limit_order_with_legs(account, contract, 'BUY', 100, limit_price=10.0,
    order_legs=[profit_taker_order_leg])
    oid = trade_client.place_order(main_order)
    # 查询主订单所关联的附加订单
    order_legs = trade_client.get_open_orders(account, parent_id=main_order.id)
    print(order_legs)
    

### 

主订单+附加括号单

[](./place-order.md#%E4%B8%BB%E8%AE%A2%E5%8D%95%E9%99%84%E5%8A%A0%E6%8B%AC%E5%8F%B7%E5%8D%95)

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import limit_order, limit_order_with_legs, order_leg, algo_order_params, \
        algo_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    contract = stock_contract(symbol='BILI', currency=Currency.USD)
    # contract = option_contract(identifier='TCH.HK  260629C00500000')
    
    
    # 限价单 + 附加订单 (仅主订单为限价单时支持附加订单)
    stop_loss_order_leg = order_leg('LOSS', 8.0, time_in_force='GTC',outside_rth=False)  # 附加止损
    profit_taker_order_leg = order_leg('PROFIT', 12.0, time_in_force='GTC',outside_rth=False)  # 附加止盈
    main_order = limit_order_with_legs(account, contract, 'BUY', 100, limit_price=10.0,
    order_legs=[profit_taker_order_leg, stop_loss_order_leg])
    oid = trade_client.place_order(main_order)
    # 查询主订单所关联的附加订单
    order_legs = trade_client.get_open_orders(account, parent_id=main_order.id)
    print(order_legs)
    #查询附加子订单
    print（main_order.sub_ids）
    

* * *

OCA 订单

[](./place-order.md#oca-%E8%AE%A2%E5%8D%95)

--------------------------------------------------------------------------------

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import order_leg, oca_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    
    contract = stock_contract(symbol='BILI', currency=Currency.USD)
    # contract = option_contract(identifier='TCH.HK  260629C00500000')
    
    # stop_loss_order_leg = order_leg('STP', price=8.0)
    stop_loss_order_leg = order_leg('STP_LMT', price=8.0, limit_price=7.5,outside_rth=False)
    profit_taker_order_leg = order_leg('LMT', limit_price=30.0,outside_rth=False)
    my_oca_order = oca_order(client_config.account, contract, 'BUY',
                                       order_legs=[stop_loss_order_leg, profit_taker_order_leg],
                                       quantity=1000)
    trade_client.place_order(my_oca_order)
    

* * *

算法订单

[](./place-order.md#%E7%AE%97%E6%B3%95%E8%AE%A2%E5%8D%95)

--------------------------------------------------------------------------------------------

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import limit_order, limit_order_with_legs, order_leg, algo_order_params, \
        algo_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
        
    # 算法订单
    # TWAP 不支持 participation_rate 参数
    # Stock VWAP Order
    contract = stock_contract(symbol='AAPL', currency='USD')
    params = algo_order_params(start_time='2022-01-19 23:00:00', end_time='2022-11-19 23:50:00', participation_rate=0.1)
    order = algo_order(account, contract, 'BUY', 1000, 'VWAP', algo_params=params, limit_price=100.0)
    oid = trade_client.place_order(order)
    
    # Option TWAP Order
    contract = option_contract(identifier='TCH.HK  260629C00500000')
    params = algo_order_params(start_time=1764211804859, end_time=1764230400000,
                                       allow_past_end_time=True,
                                      )
    order = algo_order(client_config.account, contract, 'BUY', 2, 'TWAP', algo_params=params, limit_price=1.5)
    oid = trade_client.place_order(order)
    

* * *

竞价单

[](./place-order.md#%E7%AB%9E%E4%BB%B7%E5%8D%95)

----------------------------------------------------------------------------------

[auction\_limit\_order 竞价限价单](./appendix-object-detail.md#auction_limit_order)
 [auction\_market\_order 竞价市价单](./appendix-object-detail.md#auction_market_order)

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import market_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 生成股票合约
    contract = stock_contract(symbol='01810', currency='HKD')
    # 竞价限价单, time_in_force 设置为 OPG 表示参与盘前竞价，盘前没成交的盘中时间继续挂单，不参与盘后竞价；设置为 DAY 表示只在盘后有效
    order = auction_limit_order(account=client_config.account, contract=contract, action='BUY', quantity=200,
                                limit_price='10', time_in_force='OPG')
    # 竞价市价单
    # order = auction_market_order(account=client_config.account, contract=contract, action='BUY', quantity=200， time_in_force='OPG')
    
    # 下单
    oid = trade_client.place_order(order)

* * *

夜盘/全时段/盘前盘后订单

[](./place-order.md#%E5%A4%9C%E7%9B%98%E5%85%A8%E6%97%B6%E6%AE%B5%E7%9B%98%E5%89%8D%E7%9B%98%E5%90%8E%E8%AE%A2%E5%8D%95)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------

仅支持美股

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.consts import TradingSessionType
    from tigeropen.common.util.order_utils import limit_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 生成股票合约
    contract = stock_contract(symbol='AAPL', currency='USD')
    # 生成订单对象
    order = limit_order(account=client_config.account, contract=contract, action='BUY', limit_price=120, quantity=1)
    
    # 夜盘交易
    # order.trading_session_type = TradingSessionType.OVERNIGHT
    
    # 盘前/盘中/盘后交易
    # ordeer.trading_session_type = TradingSessionType.PRE_RTH_POST
    
    # 全时段交易
    order.trading_session_type = TradingSessionType.FULL
    
    # 下单
    trade_client.place_order(order)
    my_order = trade_client.get_order(id=order.id)
    print(my_order.user_mark)

* * *

其他示例

[](./place-order.md#%E5%85%B6%E4%BB%96%E7%A4%BA%E4%BE%8B)

--------------------------------------------------------------------------------------------

**下单港股**

港股交易中，委托数量必须为该股票「每手股数」的整数倍。以腾讯控股（00700）为例，其每手股数为 100 股，因此可接受的订单数量为 100、200、500 等（即 100 的整数倍）。

可提前用 `QuoteClient.get_trade_metas` 获取股票的每手股数，以确保下单数量符合规则。

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import limit_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    quote_client = QuoteClient(client_config)
    
    symbol = '00700'
    
    # 获取每手股数
    metas = quote_client.get_trade_metas([symbol])
    lot_size = int(metas['lot_size'].iloc[0])
    
    # 生成股票合约
    contract = stock_contract(symbol=symbol, currency='HKD')
    # 生成订单对象
    order = limit_order(account=client_config.account, contract=contract, action='BUY', limit_price=400.0, quantity=2 * log_size)
    # 下单
    oid = trade_client.place_order(order)

**下单期货**

Python

    from tigeropen.common.util.contract_utils import future_contract
    from tigeropen.common.util.order_utils import limit_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 生成期货合约
    contract = future_contract(symbol='CL', currency='USD')
    # 生成订单对象
    order = limit_order(account=client_config.account, contract=contract, action='BUY', limit_price=0.1, quantity=1)
    # 下单
    oid = trade_client.place_order(order)
    

**下单期权**

Python

    from tigeropen.common.util.contract_utils import option_contract, option_contract_by_symbol
    from tigeropen.common.util.order_utils import limit_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
        
    # 生成期权合约
    contract = option_contract(identifier='AAPL  190118P00160000')
    # 或
    contract = option_contract_by_symbol('AAPL', '20200110', strike=280.0, put_call='PUT', currency='USD')
    
    # 生成订单对象
    order = limit_order(account=client_config.account, contract=contract, action='BUY', limit_price=0.1, quantity=1)
    # 下单
    oid = trade_client.place_order(order)

**下单期权组合**

Python

    from tigeropen.common.consts import ComboType
    from tigeropen.common.util.contract_utils import option_contract, option_contract_by_symbol
    from tigeropen.common.util.order_utils import combo_order, contract_leg
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    contract_leg1 = contract_leg(symbol='TSLA', sec_type='OPT', expiry='20230616', strike=220, put_call='CALL',
                                action='BUY', ratio=1)
    contract_leg2 = contract_leg(symbol='TSLA', sec_type='OPT', expiry='20230616', strike=225, put_call='CALL',
                                action='SELL', ratio=1)
    
    order = combo_order(client_config.account, [contract_leg1, contract_leg2], combo_type=ComboType.VERTICAL,
                        action='BUY', quantity=1, order_type='LMT', limit_price=1.0)
    res = trade_client.place_order(order)
    print(res)

**下单基金**

Python

    from tigeropen.common.util.contract_utils import fund_contract
    from tigeropen.common.util.order_utils import market_order_by_amount
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 生成基金合约
    contract = fund_contract(symbol='000001', currency='USD')
    # 生成订单对象
    order = market_order_by_amount(account=client_config.account, contract=contract, action='BUY', amount=100.0)
    # 下单
    oid = trade_client.place_order(order)

**查询合约并下单**

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 获取股票合约（不推荐）
    contract = trade_client.get_contract('AAPL', sec_type=SecurityType.STK)
    # 获取订单对象（不推荐）
    order = trade_client.create_order(account=client_config.account, contract=contract, action='SELL', order_type='LMT', quantity=1, limit_price=200.0)
    # 下单
    oid = trade_client.place_order(order)
    

**设置订单属性** 订单不常用属性使用示例，如 `user_mark`

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.consts import TradingSessionType
    from tigeropen.common.util.order_utils import limit_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 生成股票合约
    contract = stock_contract(symbol='AAPL', currency='USD')
    # 生成订单对象
    order = limit_order(account=client_config.account, contract=contract, action='BUY', limit_price=0.1, quantity=1)
    # 修改订单自定义备注 (环球账号需提前联系平台启用该功能)
    order.user_mark = 'my-custom-remark'
    
    # 修改订单有效期为 GTC
    # order.time_in_force = 'GTC' 
    
    # 夜盘交易
    # order.trading_session_type = TradingSessionType.OVERNIGHT
    
    # 全时段交易
    # order.trading_session_type = TradingSessionType.FULL
    
    # 修改订单为 GTD（good till date，指定日期前有效）
    #order.time_in_force = 'GTD'
    #order.expire_time = 1700496000000 # 必须指定过期时间
    # 如果用时间字符串
    #from tigeropen.common.util.common_utils import date_str_to_timestamp
    #order.expire_time = date_str_to_timestamp('2023-07-01 11:00:00', 'US/Eastern')
    
    # 下单
    trade_client.place_order(order)
    my_order = trade_client.get_order(id=order.id)
    print(my_order.user_mark)

**校正下单价格** 使用合约信息和`PriceUtil`校正下单价格。 合约价格处于不同档位时，会有不同的精度，当指定不合适精度的价格作为下单价格时，会返回错误。 此处使用查询到的合约tick规格数据，用工具修正价格。

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.price_util import PriceUtil
    from tigeropen.common.consts import SecurityType
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 获取股票合约
    contract = trade_client.get_contract(symbol='AAPL', sec_type=SecurityType.STK)
    limit_price = 150.173
    
    # 检查价格是否符合合约的tick规格
    is_price_ok = PriceUtil.match_tick_size(price, contract.tick_sizes)
    
    # 修正价格(若 is_up 参数设置为 True，则价格向上调整. 默认 False， 价格向下调整）
    # 假如合约价格在 1~1000 时，tick规格为 0.01, 那么 150.173 调整后为 150.17; 向上调整后为 150.18
    limit_price = PriceUtil.fix_price_by_tick_size(price, contract.tick_sizes)
    
    # 生成订单对象
    order = limit_order(account=client_config.account, contract=contract, action='BUY', limit_price=limit_price, quantity=1)
    
    # 下单
    trade_client.place_order(order)

**下单碎股**

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import limit_order
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 生成股票合约
    contract = stock_contract(symbol='AAPL', currency='USD')
    # 生成订单对象
    order = limit_order(account=client_config.account, contract=contract, action='BUY', limit_price=170, quantity=5)
    # quantity_scale 设置为 1, 实际下单数量为 5 * 10^-1 = 0.5 股； 若quantity_scale 设置为 2, 实际下单数量为 5 * 10^-2 = 0.05 股
    order.quantity_scale = 1
    
    # 下单
    oid = trade_client.place_order(order)
    
    print(order)

* * *

create\_order 请求创建订单

[](./place-order.md#create_order-%E8%AF%B7%E6%B1%82%E5%88%9B%E5%BB%BA%E8%AE%A2%E5%8D%95)

-------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.create_order():`

**说明**

请求订单号，创建订单对象（不推荐使用）。建议使用 `tigeropen.common.util.order_utils` 下的工具函数本地创建订单，如 `limit_order`, `market_order`.

**参数**

| 参数名 | 类型  | 描述  | 市价单 | 限价单 | 止损单 | 止损限价单 | 跟踪止损单 | 竞价限价单 | 竞价市价单 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| account | str | 账户id，**不传则返回所有关联的 account** | 选填  | 选填  | 选填  | 选填  | 选填  | 选填  | 选填  |
| contract | Contract | 合约对象 | 必填  | 必填  | 必填  | 必填  | 必填  | 必填  | 必填  |
| action | str | 买卖方向, 'BUY':买入, 'SELL':卖出 | 必填  | 必填  | 必填  | 必填  | 必填  | 必填  | 必填  |
| order\_type | str | 订单类型, 'MKT' 市价单 / 'LMT' 限价单 / 'STP' 止损单 / 'STP\_LMT' 止损限价单 / 'TRAIL' 跟踪止损单 | MKT | LMT | STP | STP\_LMT | TRAIL | AL  | AM  |
| quantity | int | 下单数量, 为大于0的整数 | 必填  | 必填  | 必填  | 必填  | 必填  | 必填  | 必填  |
| limit\_price | float | 限价单价格，**当订单类型为LMT或STP\_LMT或AL时必填** |     |     |     |     | 必填  |     |     |
| aux\_price | float | 在止损单表示止损价格; 在跟踪止损单表示价差 | 不填  | 不填  | 必填  | 必填  | 选填  | 选填  | 选填  |
| trail\_stop\_price | float | 跟踪止损单--触发止损单的价格 | 不填  | 不填  | 不填  | 不填  | 不填  | 不填  | 不填  |
| trailing\_percent | float | 跟踪止损单--百分比（跟踪止损单aux\_price和trailing\_percent至少填一个） | 不填  | 不填  | 不填  | 不填  | 选填  | 不填  | 不填  |
| adjust\_limit | float | 价格微调幅度（默认为0表示不调整，正数为向上调整，负数向下调整），对传入价格自动调整到合法价位上。例如：0.001 代表向上调整且幅度不超过 0.1%；-0.001 代表向下调整且幅度不超过 0.1%。默认 0 表示不调整 | 不填  | 选填  | 选填  | 选填  | 选填  | 不填  | 不填  |
| time\_in\_force | str | 订单有效期，只能是 DAY（当日有效）和GTC（取消前有效），默认为DAY. | 选填  | 选填  | 选填  | 选填  | 选填  | 选填 (港股竞价单盘前下单需设置为 OPG，盘后需为 DAY) | 选填 (港股竞价单盘前下单需设置为 OPG，盘后需为 DAY) |
| outside\_rth | bool | True: 允许盘前盘后交易(美股专属), False: 不允许, 默认为True。（市价单只在盘中有效，将忽略outside\_rth参数） | 不填  | 选填  | 选填  | 不填  | 选填  | 必填 (固定为True) | 必填 固定为True) |
| order\_legs | object | 附加订单列表，参数上面示例 | 选填  | 选填  | 选填  | 选填  | 选填  | 不填  | 不填  |
| algo\_params | object | 算法订单参数，参考上面示例 | 选填  | 选填  | 选填  | 选填  | 选填  | 不填  | 不填  |
| secret\_key | str | 机构用户专用，交易员密钥 | 选填  | 选填  | 选填  | 选填  | 选填  | 选填  | 选填  |
| user\_mark | str | 下单备注信息，下单后不能修改，查询订单时返回userMark信息（环球账号需要配置开发者个人信息） | 选填  | 选填  | 选填  | 选填  | 选填  | 选填  | 选填  |

**示例**

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    
    contract = stock_contract(symbol='AAPL', currency='USD')
    order = openapi_client.create_order(account, contract, 'BUY', 'LMT', 100, limit_price=5.0)
    
    trade_client.place_order(order)
    

* * *

place\_forex\_order 换汇下单

[](./place-order.md#place_forex_order-%E6%8D%A2%E6%B1%87%E4%B8%8B%E5%8D%95)

----------------------------------------------------------------------------------------------------------------------------------

`TradeClient.place_forex_order(seg_type, source_currency, target_currency, source_amount)`

**说明**

换汇订单，返回 Order 对象

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| seg\_type | SegmentType | 账户分段。可用：SegmentType.SEC 代表证券；SegmentType.FUT 代表商品，可以从 tigeropen.common.consts.SegmentType 导入 |
| source\_currency | Currency | 待转换货币 |
| target\_currency | Currency | 转换后接收的货币 |
| source\_amount | Float | 待转换的源货币金额 |

**示例**

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    
    order = trade_client.place_forex_order(seg_type='FUT', source_currency='USD', target_currency='HKD',
                                           source_amount=50)
    print(order)

**返回示例**

    Order({'account': '11222', 'id': 30323340487950336, 'order_id': 1447, 'parent_id': None, 'order_time': 1680247928000, 'reason': '', 'trade_time': 1680247928000, 'action': 'SELL', 'quantity': 50, 'filled': 0, 'avg_fill_price': 0.0, 'commission': 0.0, 'realized_pnl': 0.0, 'trail_stop_price': None, 'limit_price': 7.74991, 'aux_price': None, 'trailing_percent': None, 'percent_offset': None, 'order_type': 'LMT', 'time_in_force': 'DAY', 'outside_rth': False, 'order_legs': None, 'algo_params': None, 'algo_strategy': 'LMT', 'secret_key': None, 'liquidation': False, 'discount': 0.0, 'attr_desc': None, 'source': 'OpenApi', 'adjust_limit': None, 'sub_ids': None, 'user_mark': '', 'update_time': 1680247928000, 'expire_time': None, 'can_modify': True, 'contract': USD.HKD/FOREX/HKD, 'status': 'NEW', 'remaining': 50})
