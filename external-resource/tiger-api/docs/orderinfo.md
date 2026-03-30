# 获取订单信息

get\_orders 获取订单列表

[](./orderinfo.md#get_orders-%E8%8E%B7%E5%8F%96%E8%AE%A2%E5%8D%95%E5%88%97%E8%A1%A8)

-------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_orders(account=None, sec_type=None, market=Market.ALL, symbol=None, start_time=None, end_time=None, limit=100, is_brief=False, states=None, sort_by=None, seg_type=None, page_token=None)`

**说明**

获取账户的订单记录，包括所有订单状态、各个证券类型的订单，可以传参数进行筛选

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | str | No  | 账户id，若不填则使用 client\_config 中的默认 account |
| sec\_type | SecurityType | No  | 证券类型， 可以使用 tigeropen.common.consts.SecurityType 下的常量 |
| market | Market | No  | 所属市场，可以使用 tigeropen.common.consts.Market 下的常量 |
| symbol | str | No  | 证券代码 |
| start\_time | str或int | No  | 起始时间(闭区间)。毫秒单位时间戳或日期字符串，如 1643346000000 或 '2019-01-01' 或 '2019-01-01 12:00:00，（当 sort\_by=LATEST\_STATUS\_UPDATED 时，按订单状态更新时间进行过滤） |
| end\_time | str或int | No  | 截至时间(开区间)。毫秒单位时间戳或日期字符串，如 1653346000000 或 '2019-11-01' 或 '2019-11-01 15:00:00, （当 sort\_by=LATEST\_STATUS\_UPDATED 时，按订单状态更新时间进行过滤） |
| limit | int | No  | 每次获取订单的数量，默认:100，最大值:300 |
| is\_brief | bool | No  | 【仅支持环球账户】 是否返回精简的订单数据 |
| status | list\[OrderStatus\] | No  | 【仅支持环球账户】 订单状态，可以使用 tigeropen.common.consts.OrderStatus 的枚举 |
| sort\_by | OrderSortBy | No  | 【仅支持综合账户】 排序和起止时间作用字段，LATEST\_CREATED/LATEST\_STATUS\_UPDATED; 默认值：LATEST\_CREATED |
| seg\_type | SegmentType | No  | 账户分段。可用：SegmentType.SEC 代表证券；SegmentType.FUT 代表商品，可以从中导入 `tigeropen.common.consts.SegmentType` |
| page\_token | str | No  | 分页令牌。注意：若设置该字段(首次传空串，后续传返回对象中 next\_page\_token 的值)，则返回结构会改变, 不再返回订单列表，而是返回OrdersResponse对象， 带有订单列表 result 和 page\_token 字段 |

**返回**

`list` 或 `OrdersResponse`

如果请求时 page\_token 不为None，则返回 OrdersResponse 对象（OrdersResponse.result 字段为订单列表， OrdersResponse.next\_page\_token 为下一页的分页令牌）， 否则返回订单列表

列表中的每个元素都是一个 Order 对象（tigeropen.trade.domain.order.Order），具体字段含义详见 [Order 对象](./appendix-object-detail.md#order)

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    orders = trade_client.get_orders(sec_type=SecurityType.STK, market=Market.ALL)
    
    # 限制返回条数；按下单时间过滤（如果 start_time/end_time 传字符串, 服务端默认当北京时间处理；sdk的 client_config.timezone 可设置时区，设置后，时间字符串会当作该时区的时间)
    orders = trade_client.get_orders(limit=10,
                                     start_time='2022-09-02 01:00:00', end_time='2022-11-08 00:00:00',
                                     seg_type='SEC'
                                     )
    orders = trade_client.get_orders(limit=10,
                                     start_time=1656224964000, end_time=1666224964000,
                                     seg_type='SEC'
                                     )
    # 查看订单属性
    order1 = orders[0]
    print(order1.status)  # 订单状态
    print(order1.id)  # 订单号
    print(order1.contract.symbol) # 合约代码
    print(order1.contract.sec_type) # 合约类型

用 page\_token 分页获取订单

Python

    
    def test_get_orders_by_page():
        results = []
        params = {
            'sec_type': 'STK',
            'start_time': int(datetime.strptime('2023-01-01', '%Y-%m-%d').timestamp() * 1000),
            'end_time': int(datetime.strptime('2025-08-01', '%Y-%m-%d').timestamp() * 1000),
            'limit': 10,
            'page_token': ''
        }
        
        page = 1
        while True:
            response: OrdersResponse = trade_client.get_orders(**params)
            print(f'page {page}, size {len(response.result)}, next_page_token: {response.next_page_token}')
            page += 1
                        
            if response.result:
                results.extend(response.result)
                
            if not response.next_page_token:
                break
                
            params['page_token'] = response.next_page_token
            
        print(f'total: {len(results)}, results: {results}')
    

按时间分页获取订单

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    def test_get_orders_by_page():
        result = list()
        # 每次返回数量(需 <= 300)
        limit = 300
        conditions = {
            'limit': limit,
            'start_time': '2025-01-01',
            'end_time': '2025-02-12',
            #  返回数据是按照时间逆序，即最新的数据在前。此处按照下单时间 order_time 排序
            'sort_by': OrderSortBy.LATEST_CREATED,
            # 可继续添加其他筛选条件
        }
        orders_page = trade_client.get_orders(**conditions)
        result.extend(orders_page)
        while len(orders_page) == limit:
            next_order_time = orders_page[-1].order_time
            conditions.pop('end_time', None)
            orders_page = trade_client.get_orders(**conditions, end_time=next_order_time)
            result.extend(orders_page)
        print(f'total order size: {len(result)}')
        return result

**返回示例**

Python

    [Order({'account': '1', 'id': 162998104807903232, 'order_id': 341, 'parent_id': 0, 'order_time': 1557972846184, 'reason': '136:Order is already being cancelled.', 'trade_time': 1557975394512, 'action': 'BUY', 'quantity': 2, 'filled': 0, 'avg_fill_price': 0, 'commission': 0, 'realized_pnl': 0, 'trail_stop_price': None, 'limit_price': 0.1, 'aux_price': None, 'trailing_percent': None, 'percent_offset': None, 'order_type': 'LMT', 'time_in_force': 'DAY', 'outside_rth': True, 'contract': SPY, 'status': 'CANCELLED', 'remaining': 2}),\
    Order({'account': '1', 'id': 162998998620389376, 'order_id': 344, 'parent_id': 0, 'order_time': 1557973698590, 'reason': '136:Order is already being cancelled.', 'trade_time': 1557973773622, 'action': 'BUY', 'quantity': 1, 'filled': 0, 'avg_fill_price': 0, 'commission': 0, 'realized_pnl': 0, 'trail_stop_price': None, 'limit_price': 0.1, 'aux_price': None, 'trailing_percent': None, 'percent_offset': None, 'order_type': 'LMT', 'time_in_force': 'DAY', 'outside_rth': True, 'contract': SPY, 'status': 'CANCELLED', 'remaining': 1}),\
    Order({'account': '1', 'id': 152239266327625728, 'order_id': 230, 'parent_id': 0, 'order_time': 1547712418243, 'reason': '201:Order rejected - Reason: YOUR ORDER IS NOT ACCEPTED. IN ORDER TO OBTAIN THE DESIRED POSITION YOUR EQUITY WITH LOAN VALUE [1247.90 USD] MUST EXCEED THE INITIAL MARGIN [4989.99 USD]', 'trade_time': 1547712418275, 'action': 'BUY', 'quantity': 100, 'filled': 0, 'avg_fill_price': 0, 'commission': 0, 'realized_pnl': 0, 'trail_stop_price': None, 'limit_price': 5, 'aux_price': None, 'trailing_percent': None, 'percent_offset': None, 'order_type': 'LMT', 'time_in_force': 'DAY', 'outside_rth': True, 'contract': AAPL, 'status': 'REJECTED', 'remaining': 100})]

* * *

get\_order 获取指定订单

[](./orderinfo.md#get_order-%E8%8E%B7%E5%8F%96%E6%8C%87%E5%AE%9A%E8%AE%A2%E5%8D%95)

-----------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_order(account=None, id=None, order_id=None, is_brief=False, show_charges=None)`

**说明**

通过id获取指定的订单

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | str | No  | 账户id，若不填则使用 client\_config 中的默认 account |
| id  | int | Yes | 在提交订单后返回的全局订单id |
| order\_id | int | No  | 【仅支持环球账户】 本地订单id |
| is\_brief | bool | No  | 【仅支持环球账户】 是否返回精简的订单数据 |
| show\_charges | bool | No  | 是否显示 tigeropen.trade.domain.order.Charge 的列表 |

**返回**

`Order`对象

具体字段含义详见 [Order 对象](./appendix-object-detail.md#order)

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    print(trade_client.get_order(id=31059079170361344))

**返回示例**

    Order({'account': '111111', 'id': 31059079170361344, 'order_id': 948, 'parent_id': None, 'order_time': 1685861168000, 
    'reason': '订单已过期', 'trade_time': 1686010200000, 'action': 'BUY', 'quantity': 1, 'filled': 0, 'avg_fill_price': 0.0, 
    'commission': 0.0, 'realized_pnl': 0.0, 'trail_stop_price': None, 'limit_price': 100.0, 'aux_price': None, 
    'trailing_percent': None, 'percent_offset': None, 'order_type': 'LMT', 'time_in_force': 'DAY', 'outside_rth': True, 
    'order_legs': None, 'algo_params': None, 'algo_strategy': 'LMT', 'secret_key': '', 'liquidation': False, 'discount': 0, 
    'attr_desc': None, 'source': 'OpenApi', 'adjust_limit': None, 'sub_ids': None, 'user_mark': '', 
    'update_time': 1686010200000, 'expire_time': None, 'can_modify': False, 'external_id': '948', 'combo_type': None, 
    'combo_type_desc': None, 'is_open': True, 'contract_legs': None, 'filled_scale': 0, 'total_cash_amount': None, 
    'filled_cash_amount': 0.0, 'refund_cash_amount': None, 'attr_list': ['ALGORITHM', 'NON_TRADING_HOURS', 'TEST_ORDER'], 
    'latest_price': 215.82, 'orders': None, 'gst': 0.0, 'quantity_scale': 0, 'trading_session_type': None, 'charges': None, 
    'contract': AAPL/STK/USD, 'status': 'CANCELLED', 'remaining': 1})

* * *

get\_open\_orders 获取待成交的订单列表

[](./orderinfo.md#get_open_orders-%E8%8E%B7%E5%8F%96%E5%BE%85%E6%88%90%E4%BA%A4%E7%9A%84%E8%AE%A2%E5%8D%95%E5%88%97%E8%A1%A8)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_open_orders(account=None, sec_type=None, market=Market.ALL, symbol=None, start_time=None, end_time=None, parent_id=None, sort_by=None, seg_type=None, **kwargs)`

**说明**

获取待成交的订单列表，可能会包含部分成交订单，其未成交部分依然处于待成交状态。

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | str | No  | 账户id，若不填则使用 client\_config 中的默认 account |
| sec\_type | SecurityType | No  | 证券类型，可以使用 tigeropen.common.consts.SecurityType 下的常量 |
| market | Market | No  | 所属市场，可以使用 tigeropen.common.consts.Market 下的常量，如 Market.US |
| symbol | str | No  | 证券代码 |
| start\_time | str或int | No  | 开始时间。毫秒级别的时间戳，或日期时间字符串，如 1639386000000 或 '2019-06-07 23:00:00' 或 '2019-06-07' |
| end\_time | str或int | No  | 截至时间。毫秒级别的时间戳，或日期时间字符串，如 1639386000000 或 '2019-06-07 23:00:00' 或 '2019-06-07' |
| parent\_id | int | No  | 主订单ID |
| sort\_by | OrderSortBy | No  | 用于排序和筛选 start\_time 和 end\_time 的字段， 可从 tigeropen.common.consts 导入可用值，如 LATEST\_CREATED 或 LATEST\_STATUS\_UPDATED |
| seg\_type | SegmentType | No  | 账户分段。可用：SegmentType.SEC 代表证券；SegmentType.FUT 代表商品，可以从 tigeropen.common.consts.SegmentType 导入 |

**返回**

`list`

列表中的每个元素是一个 Order 对象（tigeropen.trade.domain.order.Order），具体字段含义详见[Order对象](./appendix-object-detail.md#order)

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    open_orders = trade_client.get_open_orders(sec_type=SecurityType.STK, market=Market.ALL)

**返回示例**

同 get\_orders

* * *

get\_cancelled\_orders 获取已撤销的订单列表

[](./orderinfo.md#get_cancelled_orders-%E8%8E%B7%E5%8F%96%E5%B7%B2%E6%92%A4%E9%94%80%E7%9A%84%E8%AE%A2%E5%8D%95%E5%88%97%E8%A1%A8)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_cancelled_orders(account=None, sec_type=None, market=Market.ALL, symbol=None, start_time=None, end_time=None, sort_by=None, seg_type=None, **kwargs)`

**说明**

获取已撤销的订单列表。包括：主动撤销、系统撤销、已失效的订单等。可能会包含部分成交，其未成交部分被撤销的订单。

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | str | No  | 账户id，若不填则使用 client\_config 中的默认 account |
| sec\_type | SecurityType | No  | 证券类型，可以使用 tigeropen.common.consts.SecurityType 下的常量 |
| market | Market | No  | 所属市场，可以使用 tigeropen.common.consts.Market 下的常量，如 Market.US |
| symbol | str | No  | 证券代码 |
| start\_time | str或int | No  | 开始时间。毫秒级别的时间戳，或日期时间字符串，如 1639386000000 或 '2019-06-07 23:00:00' 或 '2019-06-07' |
| end\_time | str或int | No  | 截至时间。毫秒级别的时间戳，或日期时间字符串，如 1639386000000 或 '2019-06-07 23:00:00' 或 '2019-06-07' |
| sort\_by | OrderSortBy | No  | 用于排序和筛选 start\_time 和 end\_time 的字段， 可从 tigeropen.common.consts 导入可用值，如 LATEST\_CREATED 或 LATEST\_STATUS\_UPDATED |
| seg\_type | SegmentType | No  | 账户分段。可用：SegmentType.SEC 代表证券；SegmentType.FUT 代表商品，可以从 tigeropen.common.consts.SegmentType 导入 |

**返回**

`list`

list 中的每个元素都是一个 Order 对象，具体字段含义详见[Order对象](./appendix-object-detail.md#order)

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    cancelled_orders = trade_client.get_cancelled_orders(sec_type=SecurityType.STK, market=Market.ALL)

**返回示例**

同 get\_orders

* * *

get\_filled\_orders 获取已成交的订单列表

[](./orderinfo.md#get_filled_orders-%E8%8E%B7%E5%8F%96%E5%B7%B2%E6%88%90%E4%BA%A4%E7%9A%84%E8%AE%A2%E5%8D%95%E5%88%97%E8%A1%A8)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_filled_orders(account=None, sec_type=None, market=Market.ALL, symbol=None, start_time=None, end_time=None, sort_by=None, seg_type=None, **kwargs)`

**说明**

获取已成交订单列表

订单可能会有部分成交的状态，此时订单状态比较特殊，有可能是HELD，CANCELLED，EXPIRED，REJECTED中的任意一种状态。

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | str | No  | 账户id，若不填则使用 client\_config 中的默认 account |
| sec\_type | SecurityType | No  | 证券类型，可以使用 tigeropen.common.consts.SecurityType 下的常量 |
| market | Market | No  | 所属市场，可以使用 tigeropen.common.consts.Market 下的常量，如 Market.US |
| symbol | str | No  | 证券代码 |
| start\_time | str或int | Yes | 开始时间。毫秒级别的时间戳，或日期时间字符串，如 1639386000000 或 '2019-06-07 23:00:00' 或 '2019-06-07' |
| end\_time | str或int | Yes | 截至时间。毫秒级别的时间戳，或日期时间字符串，如 1639386000000 或 '2019-06-07 23:00:00' 或 '2019-06-07' |
| sort\_by | OrderSortBy | No  | 用于排序和筛选 start\_time 和 end\_time 的字段， 可从 tigeropen.common.consts 导入可用值，如 LATEST\_CREATED 或 LATEST\_STATUS\_UPDATED |
| seg\_type | SegmentType | No  | 账户分段。可用：SegmentType.SEC 代表证券；SegmentType.FUT 代表商品，可以从 tigeropen.common.consts.SegmentType 导入 |

**注意**：start\_time 和 end\_time 之间的间隔不能超过 90 天

**返回** `list`

list 中的每个元素都是一个 Order 对象，具体字段含义详见[Order对象](./appendix-object-detail.md#order)

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    print(trade_client.get_filled_orders(start_time='2025-01-05',end_time='2025-03-29'))

**返回示例**

同 get\_orders

* * *

get\_transactions 获取订单成交记录

[](./orderinfo.md#get_transactions-%E8%8E%B7%E5%8F%96%E8%AE%A2%E5%8D%95%E6%88%90%E4%BA%A4%E8%AE%B0%E5%BD%95)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_transactions(account=None, order_id=None, symbol=None, sec_type=None, start_time=None, end_time=None, limit=100, expiry=None, strike=None, put_call=None)`

**说明** 获取已成交订单的详细成交记录（仅适用于综合/模拟账户）。

**参数**

**备注**：请求时，参数需二选一提供：

*   `order_id`
*   或 `symbol` 和 `sec_type`

| 参数名 | 类型  | 是否必填 | 描述  | 备注  |
| --- | --- | --- | --- | --- |
| account | str | No  | 账户id，若不填则使用 client\_config 中的默认 account |     |
| order\_id | int | Yes/No(当symbol+sec\_type未传时本参数必填) | 下单后返回的全局订单ID，非本地订单ID |     |
| symbol | str | Yes/No(当order\_id未传时本参数必填) | 标的代码。使用symbol查询时sec\_type为必传 |     |
| sec\_type | SecurityType | Yes/No(当order\_id未传时本参数必填) | 标的类型。使用symbol查询时sec\_type为必传 | 证券类型， 可以使用 tigeropen.common.consts.SecurityType 下的常量 |
| start\_time | str或int | No  | 成交时间的起始时间。毫秒单位时间戳或日期字符串(闭区间)，如 1643346000000 或 '2019-01-01' 或 '2019-01-01 12:00:00。数据量较大时，查询速度会比较慢，推荐使用 since\_date+to\_date |     |
| end\_time | str或int | No  | 成交时间的截至时间。毫秒单位时间戳或日期字符串(开区间)，如 1653346000000 或 '2019-11-01' 或 '2019-11-01 15:00:00 |     |
| since\_date | str | No  | 成交时间的起始日期(闭区间, 即返回数据里包括since\_date), 格式 "20250101" | 需要 sdk 版本 >= 3.5.0 |
| to\_date | str | No  | 成交时间的截至日期(开区间, 即返回数据里不包括to\_date)，格式 "20250201" | 需要 sdk 版本 >= 3.5.0 |
| limit | int | No  | 每次获取记录的数量，最多 100，默认为 20 |     |
| expiry | str | No  | 过期日(适用于期权)。 形式 'yyyyMMdd', 比如 '220121' |     |
| strike | float | No  | 行权价(适用于期权)。比如 100.5 |     |
| put\_call | str | No  | 看涨或看跌(适用于期权)。'PUT' 或 'CALL' |     |
| page\_token | str | No  | 分页令牌。注意：若设置该字段(首次传空串，后续传 next\_page\_token 的值)，则返回结构会改变, 不再返回成交记录列表，而是返回 TransactionsResponse 对象， 带有成交记录列表 result 和 page\_token 字段 |     |

**返回** `list` 或 `TransactionsResponse` 如果请求时 page\_token 不为None，则返回 TransactionsResponse 对象（TransactionsResponse.result 字段为成交记录列表， TransactionsResponse.next\_page\_token 为下一页的分页令牌）， 否则返回成交记录列表

列表中每个元素为 `Transaction` 对象，具体字段含义见 [Transaction](./appendix-object-detail.md#transaction)

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    filled_orders = trade_client.get_transactions(symbol='AAPL', sec_type=SecurityType.STK)

用 page\_token 分页获取成交记录

Python

    def test_get_transaction_by_page():
        """获取所有交易记录
        通过分页方式获取指定时间范围内的所有交易记录
        """
        results = []
        params = {
            'symbol': 'AAPL',
            'start_time': int(datetime.strptime('2023-01-01', '%Y-%m-%d').timestamp() * 1000),
            'end_time': int(datetime.strptime('2025-08-01', '%Y-%m-%d').timestamp() * 1000),
            'limit': 15,  
            'page_token': ''
        }
        
        page = 1
        while True:
            response: TransactionsResponse = trade_client.get_transactions(**params)
            print(f'page {page}, size {len(response.result)}, next_page_token: {response.next_page_token}')
            page += 1
                        
            if response.result:
                results.extend(response.result)
                
            if not response.next_page_token:
                break
                
            params['page_token'] = response.next_page_token
            
        print(f'total: {len(results)}, results: {results}')

**返回示例**

    Transaction({'account': 111111, 'order_id': 20947299719447552, 'contract': AAPL/STK/USD, 'id': 20947300069016576, 'action': 'BUY', 'filled_quantity': 1, 'filled_quantity_scale': 0, 'filled_price': 132.25, 'filled_amount': 132.25, 'transacted_at': '2020-12-23 17:06:54'}), 
    
    Transaction({'account': 111111, 'order_id': 19837920138101760, 'contract': AAPL/STK/USD, 'id': 19837920740508672, 'action': 'BUY', 'filled_quantity': 1, 'filled_quantity_scale': 0,  'filled_price': 116.21, 'filled_amount': 116.21, 'transacted_at': '2020-09-16 18:02:00'})]
            

* * *

preview\_order 预览订单

[](./orderinfo.md#preview_order-%E9%A2%84%E8%A7%88%E8%AE%A2%E5%8D%95)

-----------------------------------------------------------------------------------------------------------------------

`TradeClient.preview_order(order)`

**说明**

预览订单，返回是否可提交订单（即，订单是否可成交），以及资产信息

**参数** 同下单接口， 为 `Order` 对象。 暂不支持 OCA订单、附加订单

**返回**

| 字段  | 类型  | 描述  |
| --- | --- | --- |
| account | str | 账户id |
| init\_margin | float | 下单后初始保证金, 不支持期货 |
| maint\_margin | float | 下单后维持保证金, 不支持期货 |
| equity\_with\_loan | float | 下单后可借贷资产, 不支持期货 |
| init\_margin\_before | float | 下单前初始保证金, 不支持期货 |
| maint\_margin\_before | float | 下单前维持保证金, 不支持期货 |
| equity\_with\_loan\_before | float | 下单前可借贷资产, 不支持期货 |
| margin\_currency | str | 保证金币种 |
| commission | float | 预估佣金 |
| gst | float | 预估消费税 |
| commission\_currency | str | 预估佣金币种 |
| available\_ee | float | 可用剩余资产 不支持期货 |
| excess\_liquidity | float | 剩余流动性 不支持期货 |
| overnight\_liquidation | float | 隔夜剩余流动性 不支持期货 |
| is\_pass | bool | 是否可提交订单 |
| message | str | 不可提交订单的错误原因 |

**示例**

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    contract = stock_contract(symbol='AAPL', currency='USD')
    order = limit_order(account=client_config.account, contract=contract, action='BUY', limit_price=170, quantity=5)
    
    result = trade_client.preview_order(order)
    print(result)

**返回示例**

Python

    {"account": "111111", "init_margin": 3937.6255463, "maint_margin": 3437.6255463, "equity_with_loan": 1250864.63784,
      "init_margin_before": 937.6255463, "maint_margin_before": 937.6255463, "equity_with_loan_before": 1250890.48784,
      "margin_currency": "USD", "commission": 23.72, "commission_currency": "USD",
      "available_ee": 1246927.0122936, "excess_liquidity": 1247427.0122936, "overnight_liquidation": 1247427.0122936,
      "gst": 2.13, "is_pass": True
    }
    
    {'account': '111111', 'init_margin': 10000004816.402893, 'maint_margin': 10000004816.402893,
     'equity_with_loan': -24603909.5121613, 'init_margin_before': 937.6255463,
     'maint_margin_before': 937.6255463, 'equity_with_loan_before': 1250890.48784, 
     'margin_currency': 'USD', 'commission': 23720000.0, 'commission_currency': 'USD',
     'available_ee': -10024608725.915054, 'excess_liquidity': -10024608725.915054, 
     'overnight_liquidation': -10024608725.915054, 'gst': 2134800.0,
     'is_pass': False, 'message': '您的可用资金或者可用购买力不足'}
