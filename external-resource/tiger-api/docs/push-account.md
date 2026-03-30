# 账户变动推送

**以下全部为异步API，需要指定一个方法响应返回的结果**

Python

    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'))

此外需注意，如果订阅时没有指定账户，回调时会推送关联到本 tiger\_id 的多个账户的数据，在处理回调时需要判断数据属于哪个账户

资产变化的订阅与取消

[](./push-account.md#%E8%B5%84%E4%BA%A7%E5%8F%98%E5%8C%96%E7%9A%84%E8%AE%A2%E9%98%85%E4%B8%8E%E5%8F%96%E6%B6%88)

---------------------------------------------------------------------------------------------------------------------------------------------------------

**订阅方法**

`PushClient.subscribe_asset(account)`

**取消方法**

`PushClient.unsubscribe_asset()`

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 需要订阅的 account id，不传则订阅所有关联的 account. 注意，如果订阅时没有指定账户，回调时会推送多个账户的数据，在处理回调时需要判断数据属于哪个账户 |

**返回**

需要使用 `PushClient.asset_changed` 响应返回结果。返回结果为 tigeropen.push.pb.AssetData\_pb2.AssetData 对象 [对象说明](./appendix-object-detail.md#assetchange)

字段含义可对照参考获取资产接口 [get\_prime\_assets](./accounts.md#get_prime_assets)
， [get\_assets](./accounts.md#get_assets)

详细字段解释参考对象: [PortfolioAccount 综合/模拟资产](./appendix-object-detail.md#portfolioaccount)

[PortfolioAccount 环球资产](./appendix-object-detail.md#portfolioaccountglobal)

**回调数据字段含义**

资产变动回调

| 字段  | 类型  | 描述  |
| --- | --- | --- |
| account | str | 资金账号 |
| currency | str | 币种。USD美元，HKD港币 |
| segType | str | 按交易品种划分的分类。S表示股票，C表示期货 |
| availableFunds | float | 可用资金，隔夜剩余流动性 |
| excessLiquidity | float | 当前剩余流动性 |
| netLiquidation | float | 总资产(净清算值)。总资产就是我们账户的净清算现金余额和证券总市值之和 |
| equityWithLoan | float | 含贷款价值总权益。等于总资产 - 美股期权 |
| buyingPower | float | 购买力。仅适用于股票品种，即segment为S时有意义 |
| cashBalance | float | 现金额。当前所有币种的现金余额之和 |
| grossPositionValue | float | 证券总价值 |
| initMarginReq | float | 初始保证金 |
| maintMarginReq | float | 维持保证金 |
| timestamp | int | 时间戳 |

**示例**

Python

    from tigeropen.push.pb.AssetData_pb2 import AssetData
    from tigeropen.push.push_client import PushClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'))
    
    # 定义回调方法
    def on_asset_changed(frame: AssetData):
        """可进行自定义处理，此处仅打印"""
        print(f'asset change. {frame}')
        # 查看可用资金
        print(frame.availableFunds)
        # 查看持仓市值
        print(frame.grossPositionValue)
    
    # 绑定回调方法
    push_client.asset_changed = on_asset_changed
    # 连接
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅 
    push_client.subscribe_asset(account=client_config.account)
    
    
    # 取消订阅资产变化
    # push_client.unsubscribe_asset()

**回调数据示例**

    account: "111111"
    currency: "USD"
    segType: "S"
    availableFunds: 1593.1191893
    excessLiquidity: 1730.5666908
    netLiquidation: 2856.1016998
    equityWithLoan: 2858.1016998
    buyingPower: 6372.4767571
    cashBalance: 484.1516697
    grossPositionValue: 2373.95003
    initMarginReq: 1264.9825105
    maintMarginReq: 1127.535009
    timestamp: 1677745420121
    

* * *

持仓变化的订阅与取消

[](./push-account.md#%E6%8C%81%E4%BB%93%E5%8F%98%E5%8C%96%E7%9A%84%E8%AE%A2%E9%98%85%E4%B8%8E%E5%8F%96%E6%B6%88)

---------------------------------------------------------------------------------------------------------------------------------------------------------

**订阅方法**

`PushClient.subscribe_position(account)`

**取消方法**

`PushClient.unsubscribe_position()`

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 需要订阅的 account id，不传则订阅所有关联的 account. 注意，如果订阅时没有指定账户，回调时会推送多个账户的数据，在处理回调时需要判断数据属于哪个账户 |

**示例**

Python

    from tigeropen.push.pb.PositionData_pb2 import PositionData
    from tigeropen.push.push_client import PushClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'))
    
    # 定义回调方法
    def on_position_changed(frame: PositionData):
        print(f'position change. {frame}')
        # 持仓标的
        print(frame.symbol)
        # 持仓成本
        print(frame.averageCost)
        
    # 绑定回调方法
    push_client.position_changed = on_position_changed
    # 连接
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅持仓的变化
    push_client.subscribe_position(account=client_config.account)
    # 取消订阅持仓的变化
    push_client.unsubscribe_position()

**返回**

需要使用 `PushClient.position_changed` 响应返回结果，数据类型为 tigeropen.push.pb.PositionData\_pb2.PositionData [对象说明](./appendix-object-detail.md#positionchange)
 ，列表项中的各字段可对照参考 [get\_positions](./accounts.md#get_positions)
 中的 [Position](./appendix-object-detail.md#position)
 对象。

| 字段  | 类型  | 描述  |
| --- | --- | --- |
| account | str | 资金账号 |
| symbol | str | 持仓标的代码，如 'AAPL', '00700', 'ES', 'CN' |
| expiry | str | 仅支持期权、窝轮、牛熊证 |
| strike | str | 仅支持期权、窝轮、牛熊证 |
| right | str | 仅支持期权、窝轮、牛熊证 |
| identifier | str | 标的标识符。股票的identifier与symbol相同。期货的会带有合约月份，如 'CN2201' |
| multiplier | int | 每手数量，仅限 futures, options, warrants, CBBC |
| market | str | 市场。US, HK |
| currency | str | 币种。USD美元，HKD港币 |
| segType | str | 按交易品种划分的分类。S表示股票，C表示期货 |
| secType | str | STK Stocks, OPT Options, WAR Warrants, IOPT CBBC, CASH FOREX, FUT Futures, FOP Future Options |
| position | int | 持仓数量 |
| positionScale | int | 持仓数量的偏移量 |
| averageCost | float | 持仓均价 |
| latestPrice | float | 标的当前价格 |
| marketValue | float | 持仓市值 |
| unrealizedPnl | float | 持仓盈亏 |
| timestamp | int | 时间戳 |

**回调数据示例**

股票持仓变化推送

    account: "111111"
    symbol: "BILI"
    identifier: "BILI"
    multiplier: 1
    market: "US"
    currency: "USD"
    segType: "S"
    secType: "STK"
    position: 100
    averageCost: 80
    latestPrice: 19.83
    marketValue: 1983
    unrealizedPnl: -6017
    timestamp: 1677745420121

* * *

订单变化的订阅和取消

[](./push-account.md#%E8%AE%A2%E5%8D%95%E5%8F%98%E5%8C%96%E7%9A%84%E8%AE%A2%E9%98%85%E5%92%8C%E5%8F%96%E6%B6%88)

---------------------------------------------------------------------------------------------------------------------------------------------------------

**订阅方法**

`PushClient.subscribe_order(account=None)`

**取消方法**

`PushClient.unsubscribe_order()`

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 需要订阅的 account id，不传则订阅所有关联的 account. 注意，如果订阅时没有指定账户，回调时会推送多个账户的数据，在处理回调时需要判断数据属于哪个账户 |

**示例**

Python

    from tigeropen.push.pb.OrderStatusData_pb2 import OrderStatusData
    from tigeropen.push.push_client import PushClient
    from tigeropen.common.consts import OrderStatus
    from tigeropen.common.util.order_utils import get_order_status
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'))
    
    # 定义回调方法
    def on_order_changed(frame: OrderStatusData):
        print(f'order changed: {frame}')
        print(f'order status: {frame.status}')
        # 将订单状态值转换为枚举类型
        status_enum = OrderStatus[frame.status]
        # 或 
        # status_enum = get_order_status(frame.status)
        
    # 绑定回调方法
    push_client.order_changed = on_order_changed
    # 连接
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅订单变化
    push_client.subscribe_order(account=client_config.account)
    # 取消订阅订单变化
    push_client.unsubscribe_order()

**返回**

需要使用 `PushClient.order_changed` 响应返回结果，数据类型为 tigeropen.push.pb.OrderStatusData\_pb2.OrderStatusData [对象说明](./appendix-object-detail.md#orderchange)
，返回结果中的字段含义可对照参考 [get\_order](./orderinfo.md#get_orders)
 中的 [Order](./appendix-object-detail.md#order)
 对象

| 字段  | 类型  | 描述  |
| --- | --- | --- |
| id  | int | 订单号 |
| account | str | 资金账号 |
| symbol | str | 持仓标的代码，如 'AAPL', '00700', 'ES', 'CN' |
| expiry | str | 仅支持期权、窝轮、牛熊证 |
| strike | str | 仅支持期权、窝轮、牛熊证 |
| right | str | 仅支持期权、窝轮、牛熊证 |
| identifier | str | 标的标识符。股票的identifier与symbol相同。期货的会带有合约月份，如 'CN2201' |
| multiplier | int | 每手数量，仅限 futures, options, warrants, CBBC |
| action | str | 买卖方向。BUY表示买入，SELL表示卖出。 |
| market | str | 市场。US、HK |
| currency | str | 币种。USD美元，HKD港币 |
| segType | str | 按交易品种划分的分类。S表示股票，C表示期货 |
| secType | str | STK Stocks, OPT Options, WAR Warrants, IOPT CBBC, CASH FOREX, FUT Futures, FOP Future Options |
| orderType | str | 订单类型。'MKT'市价单/'LMT'限价单/'STP'止损单/'STP\_LMT'止损限价单/'TRAIL'跟踪止损单 |
| isLong | boolean | 是否多头持仓 |
| totalQuantity | int | 下单数量 |
| totalQuantityScale | int | 下单数量偏移量，如 totalQuantity=111， totalQuantityScale=2，那么真实 totalQuantity=111\*10^(-2)=1.11 |
| filledQuantity | int | 成交总数量（订单分多笔成交的，filledQuantity为累计成交总数） |
| filledQuantityScale | int | 成交总数量偏移量 |
| avgFillPrice | float | 成交均价 |
| limitPrice | float | 限价单价格 |
| stopPrice | float | 止损价格 |
| realizedPnl | float | 已实现盈亏（只有综合账号有这个字段） |
| status | str | [订单状态](./appendix-enum.md#orderstatus)<br>。注意，status的值为 OrderStatus的枚举名称， 可使用 `OrderStatus[value]` 转换为枚举类型 |
| replaceStatus | str | [订单改单状态](./appendix-enum.md#orderreplacestatus) |
| cancelStatus | str | [订单撤单状态](./appendix-enum.md#ordercancelstatus) |
| outsideRth | bool | 是否允许盘前盘后交易，仅适用于美股 |
| canModify | bool | 是否能修改 |
| canCancel | bool | 是否能取消 |
| liquidation | bool | 是否为平仓订单 |
| name | str | 标的名称 |
| source | str | 订单来源(from 'OpenApi', or other) |
| errorMsg | str | 错误信息 |
| attrDesc | str | 订单描述信息 |
| commissionAndFee | float | 佣金费用总计 |
| openTime | int | 下单时间 |
| timestamp | int | 订单状态最后更新时间 |
| userMark | str | 订单备注 |
| totalCashAmount | float | 订单总金额 |
| filledCashAmount | float | 成交总金额 |
| attrList | list\[str\] | 订单属性列表，各属性含义如下： LIQUIDATION 强平, FRACTIONAL\_SHARE 碎股订单(非整股), EXERCISE 行权, EXPIRE 过期, ASSIGNMENT 被动行权分配, CASH\_SETTLE 现金交割, KNOCK\_OUT 敲出, RECALL 召回订单, ODD\_LOT 碎股订单（非整手）, DEALER 交易员下单, GREY\_MARKET 港股暗盘订单, BLOCK\_TRADE 大宗交易, ATTACHED\_ORDER 附加订单, OCA OCA订单 |
| timeInForce | str | 订单有效时间。DAY: 当日有效，GTC: 撤销前有效，GTD: 有效至指定日期。 |

**回调数据示例**

股票订单推送示例

    id: 40536123722044416
    account: "12345"
    symbol: "01810"
    identifier: "01810"
    multiplier: 1
    action: "SELL"
    market: "HK"
    currency: "HKD"
    segType: "S"
    secType: "STK"
    orderType: "LMT"
    isLong: true
    totalQuantity: 200
    limitPrice: 100.5
    status: "HELD"
    replaceStatus: "NONE"
    cancelStatus: "NONE"
    outsideRth: true
    canModify: true
    canCancel: true
    name: "XIAOMI-W"
    source: "OpenApi"
    openTime: 1758165280000
    timestamp: 1758165280758
    timeInForce: "DAY"

期货订单推送示例

    {"id":"28875370355884032","account":"12345","symbol":"CL","identifier":"CL2312","multiplier":1000,"action":"BUY",
    "market":"US","currency":"USD","segment":"C","secType":"FUT","orderType":"LMT","isLong":true,"totalQuantity":"1",
    "filledQuantity":"1","avgFillPrice":77.76,"limitPrice":77.76,"status":"FILLED","outsideRth":true,"name":"WTI原油2312",
    "source":"android","commissionAndFee":4.0,"openTime":"1669200792000","timestamp":"1669200782221"}

  

订单执行明细订阅和取消

[](./push-account.md#%E8%AE%A2%E5%8D%95%E6%89%A7%E8%A1%8C%E6%98%8E%E7%BB%86%E8%AE%A2%E9%98%85%E5%92%8C%E5%8F%96%E6%B6%88)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

**订阅方法**

`PushClient.subscribe_transaction(account=client_config.account)`

**取消方法**

`PushClient.unsubscribe_transaction()`

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 需要订阅的 account id，不传则订阅所有关联的 account. 注意，如果订阅时没有指定账户，回调时会推送多个账户的数据，在处理回调时需要判断数据属于哪个账户 |

**示例**

Python

    from tigeropen.push.pb.OrderTransactionData_pb2 import OrderTransactionData
    from tigeropen.push.push_client import PushClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    protocol, host, port = client_config.socket_host_port
    push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'))
    
    # 定义回调方法
    def on_transaction_changed(frame: OrderTransactionData):
        print(f'transaction changed: {frame}')
        
    # 绑定回调方法
    push_client.transaction_changed = on_transaction_changed
    # 连接
    push_client.connect(client_config.tiger_id, client_config.private_key)
    
    # 订阅
    pushClient.subscribe_transaction(account=client_config.account)
    # 取消订阅
    pushClient.unsubscribe_transaction()

**返回**

需要使用 `PushClient.transaction_changed` 响应返回结果，数据类型为tigeropen.push.pb.OrderTransactionData\_pb2.OrderTransactionData，返回结果中的字段含义可对照参考 [get\_transactions](./orderinfo.md#/get_transactions)
 中的 [Transaction](./appendix-object-detail.md#transaction)

| 字段  | 类型  | 描述  |
| --- | --- | --- |
| id  | int | 订单执行ID |
| orderId | int | 订单号 |
| account | str | 资金账号 |
| symbol | str | 持仓标的代码，如 'AAPL', '00700', 'ES', 'CN' |
| identifier | str | 标的标识符。股票的identifier与symbol相同。期货的会带有合约月份，如 'CN2201' |
| multiplier | int | 每手数量(期权、期货专有) |
| action | str | 买卖方向。BUY表示买入，SELL表示卖出。 |
| market | str | 市场。US、HK |
| currency | str | 币种。USD美元，HKD港币 |
| segType | str | 按交易品种划分的分类。S表示股票，C表示期货 |
| secType | str | 交易品种，标的类型。STK表示股票，FUT表示期货 |
| filledPrice | float | 价格  |
| filledQuantity | int | 成交数量 |
| createTime | int | create time |
| updateTime | int | update time |
| transactTime | int | 成交时间 |
| timestamp | int | timestamp |

**回调数据示例**

    
    id: 2999543887211111111
    orderId: 29995438111111111
    account: "1111111"
    symbol: "ZC"
    identifier: "ZC2305"
    multiplier: 5000
    action: "BUY"
    market: "US"
    currency: "USD"
    segType: "C"
    secType: "FUT"
    filledPrice: 6.385
    filledQuantity: 1
    createTime: 1677746237303
    updateTime: 1677746237303
    transactTime: 1677746237289
    timestamp: 1677746237313

* * *
