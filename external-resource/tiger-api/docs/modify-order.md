# 取消或修改订单

cancel\_order 取消订单

[](./modify-order.md#cancel_order-%E5%8F%96%E6%B6%88%E8%AE%A2%E5%8D%95)

------------------------------------------------------------------------------------------------------------------------

`TradeClient.cancel_order(account=None, id=None, order_id=None)`

**说明**

撤销已下的订单。撤单同下单类似，为异步执行，调用本命令后表示撤单请求发送成功，撤单的执行会异步处理 使用 `TradeClient.place_order` 提交订单后，被提交的订单会根据不同情况进入多种状态。已成交或被系统拒绝的订单无法被撤销，只有订单处于已提交或部分成交的状态，才可被撤销。请参考 [TradeClient.get\_order](./orderinfo.md#get_orders)
 方法的说明来了解可能出现的订单状态。

对于批量下出的订单，可用 `TradeClient.get_open_orders` 取得待成交的订单列表，依次撤单。对于已经请求过撤单的订单，不要重复请求，可在撤单命令执行后，等待一段时间再次检查待成交的订单。

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | str | No  | 账户id，**若不填则使用 client\_config 中的默认 account** |
| id  | int | Yes | 全局订单id，取消订单操作建议使用id字段，在 place\_order 之后，可以通过 Order.id 获取 |
| order\_id | int | No  | 本地订单order\_id， 可以通过 Order.order\_id 获取 |

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 假设订单id为 26731241425469440
    trade_client.cancel_order(id=26731241425469440)

* * *

modify\_order 修改订单

[](./modify-order.md#modify_order-%E4%BF%AE%E6%94%B9%E8%AE%A2%E5%8D%95)

------------------------------------------------------------------------------------------------------------------------

`TradeClient.modify_order(order, quantity=None, limit_price=None, aux_price=None, trail_stop_price=None, trailing_percent=None, percent_offset=None, time_in_force=None, outside_rth=None, **kwargs)`

**说明**

修改已下订单，订单类型不支持修改。具体可修改的字段见下表

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| order | Order | Yes | 要修改的 Order 对象(tigeropen.trade.domain.order.Order) |
| quantity | int | No  | 修改后的下单股数 |
| limit\_price | float | No  | 修改后的限价，**当 order\_type 为LMT,STP,STP\_LMT时该参数必需** |
| aux\_price | float | No  | 对于限价止损单，表示触发价， 对于移动止损单，表示价差。**当 order\_type 为STP,STP\_LMT时该参数必需** |
| trail\_stop\_price | float | No  | **当 order\_type 为 TRAIL 时必须**，为触发止损单的价格 |
| trailing\_percent | float | No  | 跟踪止损单-百分比 ，**当 order\_type 为 TRAIL时,aux\_price和trailing\_percent两者互斥** |
| time\_in\_force | str | No  | 订单有效期，只能是 'DAY'（当日有效）和'GTC'（取消前有效），默认为'DAY' |
| outside\_rth | bool | No  | 是否允许盘前盘后交易 (美股专属) |

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # 假设订单id为：26731241425469440
    order = trade_client.get_order(id=26731241425469440)
    trade_client.modify_order(order, quantity=2, limit_price=105.0)
