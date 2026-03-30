# 附录二：枚举参数对照表

### 

语言

[](./appendix-enum-java.md#%E8%AF%AD%E8%A8%80)

`com.tigerbrokers.stock.openapi.client.struct.enums.Language`

| 标识  | 语言  |
| --- | --- |
| zh\_CN | 简体中文 |
| zh\_TW | 繁体中文 |
| en\_US | 英文  |

### 

市场

[](./appendix-enum-java.md#%E5%B8%82%E5%9C%BA)

`com.tigerbrokers.stock.openapi.client.struct.enums.Market`

| 标识  | 市场  |
| --- | --- |
| ALL | 全部  |
| US  | 美股  |
| HK  | 港股  |
| CN  | A股  |
| SG  | 新加坡 |
| AU  | 澳大利亚 |
| NZ  | 新西兰 |

### 

合约类型

[](./appendix-enum-java.md#%E5%90%88%E7%BA%A6%E7%B1%BB%E5%9E%8B)

`com.tigerbrokers.stock.openapi.client.struct.enums.SecType`

| 标识  | 合约类型 |
| --- | --- |
| STK | 股票  |
| OPT | 美股期权 |
| WAR | 港股窝轮 |
| IOPT | 港股牛熊证 |
| CASH | 外汇  |
| FUT | 期货  |
| FOP | 期货期权 |
| FUND | 基金  |

### 

账户划分

[](./appendix-enum-java.md#%E8%B4%A6%E6%88%B7%E5%88%92%E5%88%86)

`com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType`

| 标识  | 合约类型 |
| --- | --- |
| ALL | 全部  |
| SEC | 证券  |
| FUT | 期货  |
| FUND | 基金  |

### 

资金周期

[](./appendix-enum-java.md#%E8%B5%84%E9%87%91%E5%91%A8%E6%9C%9F)

`com.tigerbrokers.stock.openapi.client.struct.enums.CapitalPeriod`

| 标识  | 说明  |
| --- | --- |
| INTRADAY | intraday |
| DAY | day |
| WEEK | week |
| MONTH | month |
| YEAR | year |
| QUARTER | quarter |
| HALFAYEAR | 6month |

### 

货币类型

[](./appendix-enum-java.md#%E8%B4%A7%E5%B8%81%E7%B1%BB%E5%9E%8B)

`com.tigerbrokers.stock.openapi.client.struct.enums.Currency`

| 标识  | 货币类型 |
| --- | --- |
| ALL | 全部  |
| USD | 美元  |
| HKD | 港币  |
| CNH | 人民币 |
| SGD | 新加坡元 |
| AUD | 澳大利亚元 |
| JPY | 日元  |
| EUR | 欧元  |
| GBP | 英镑  |
| CAD | 加拿大元 |
| NZD | 新西兰元 |

### 

订单状态

[](./appendix-enum-java.md#%E8%AE%A2%E5%8D%95%E7%8A%B6%E6%80%81)

`com.tigerbrokers.stock.openapi.client.struct.enums.OrderStatus`

| 状态  | 状态码 | 说明  |
| --- | --- | --- |
| Invalid | \-2 | 非法状态 |
| Initial | \-1 | 订单初始状态 |
| PendingCancel | 3   | 待取消(综合账号和模拟账号没有) |
| Cancelled | 4   | 已取消 |
| PendingSubmit | 5   | 订单已经提交，环球账号为Submitted |
| Filled | 6   | 完全成交 |
| Inactive | 7   | 已失效 |

订单部分成交的状态比较复杂，当订单状态不是Filled（有可能是PendingSubmit，Cancelled，Invalid，Inactive其中一种）时，都有可能是部分成交的状态，可以通过订单成交数量是否大于0来判断

### 

订单改单状态

[](./appendix-enum-java.md#%E8%AE%A2%E5%8D%95%E6%94%B9%E5%8D%95%E7%8A%B6%E6%80%81)

| 状态  | 说明  |
| --- | --- |
| NONE | 默认状态 or 订单终结 |
| RECEIVED | 改单已接收（pretrade检查通过） |
| REPLACED | 改单成功（上手已确认） |
| FAILED | 改单失败 (收到上手拒绝报告） |

### 

订单撤单状态

[](./appendix-enum-java.md#%E8%AE%A2%E5%8D%95%E6%92%A4%E5%8D%95%E7%8A%B6%E6%80%81)

| 状态  | 说明  |
| --- | --- |
| NONE | 默认状态 or 订单终结 |
| RECEIVED | 撤单已接收（pretrade检查通过） |
| FAILED | 撤单失败 (收到上手拒绝报告） |

  

枚举参数及常用字段参数含义，请参考本节

### 

Language

[](./appendix-enum-java.md#language)

**语言** `tigeropen.common.consts.Language` [source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/consts/__init__.py)

| 标识  | 语言  |
| --- | --- |
| zh\_CN | 简体中文 |
| zh\_TW | 繁体中文 |
| en\_US | 英文  |

### 

Market

[](./appendix-enum-java.md#market)

**市场** `tigeropen.common.consts.Market` [source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/consts/__init__.py)

| 标识  | 市场  |
| --- | --- |
| ALL | 全部  |
| US  | 美股  |
| HK  | 港股  |
| CN  | A股  |
| SG  | 新加坡 |
| AU  | 澳大利亚 |
| NZ  | 新西兰 |

### 

CapitalPeriod

[](./appendix-enum-java.md#capitalperiod)

**资金周期**

| 标识  | 市场  |
| --- | --- |
| INTRADAY | intraday |
| DAY | day |
| WEEK | week |
| MONTH | month |
| YEAR | year |
| QUARTER | quarter |
| HALFAYEAR | 6month |

### 

合约类型

[](./appendix-enum-java.md#%E5%90%88%E7%BA%A6%E7%B1%BB%E5%9E%8B-1)

`tigeropen.common.consts.SecurityType` [source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/consts/__init__.py)

| 标识  | 合约类型 |
| --- | --- |
| STK | 股票  |
| OPT | 美股期权 |
| WAR | 港股窝轮 |
| IOPT | 港股牛熊证 |
| CASH | 外汇  |
| FUT | 期货  |
| FOP | 期货期权 |
| FUND | 基金  |

### 

货币类型

[](./appendix-enum-java.md#%E8%B4%A7%E5%B8%81%E7%B1%BB%E5%9E%8B-1)

`tigeropen.common.consts.Currency` [source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/consts/__init__.py)

| 标识  | 货币类型 |
| --- | --- |
| ALL | 全部  |
| USD | 美元  |
| HKD | 港币  |
| CNH | 人民币 |
| SGD | 新加坡元 |
| AUD | 澳大利亚元 |
| JPY | 日元  |
| EUR | 欧元  |
| GBP | 英镑  |
| CAD | 加拿大元 |
| NZD | 新西兰元 |

### 

订单状态

[](./appendix-enum-java.md#%E8%AE%A2%E5%8D%95%E7%8A%B6%E6%80%81-1)

`tigeropen.common.consts.OrderStatus` [source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/consts/__init__.py)
.

sdk 通过 `tigeropen.common.util.order_utils.get_order_status` 将状态值处理成枚举标识。[source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/util/order_utils.py#L134)

| 枚举标识 | 状态值 | 状态码 | 说明  |
| --- | --- | --- | --- |
| EXPIRED | Invalid | \-2 | 非法状态 |
| NEW | Initial | \-1 | 订单初始状态 |
| CANCELLED | Cancelled | 4   | 已取消 |
| HELD | Submitted | 5   | 订单已经提交 |
| PARTIALLY\_FILLED | PartiallyFilled | 2, 5, 8 | 部分成交 |
| FILLED | Filled | 6   | 完全成交 |
| REJECTED | Inactive | 7   | 已失效 |

> 注意：部分成交的状态比较特殊，有可能是HELD，CANCELLED，EXPIRED，REJECTED的任意一种状态 另外可参见 sdk 内处理订单状态的方法 `tigeropen.trade.domain.order.Order.status`, 即如果服务端返回的订单状态为 HELD(submitted), 且有部分数量的成交，则此时订单状态为 PARTIALLY\_FILLED

Python

    class Order:
        ...
        ...
        @property
        def status(self):
            if not self.remaining and self.filled:
                return OrderStatus.FILLED
            elif self._status == OrderStatus.HELD and self.filled:
                return OrderStatus.PARTIALLY_FILLED
            else:
                return self._status

### 

订单改单状态

[](./appendix-enum-java.md#%E8%AE%A2%E5%8D%95%E6%94%B9%E5%8D%95%E7%8A%B6%E6%80%81-1)

| 状态  | 说明  |
| --- | --- |
| NONE | 默认状态 or 订单终结 |
| RECEIVED | 改单已接收（pretrade检查通过） |
| REPLACED | 改单成功（上手已确认） |
| FAILED | 改单失败 (收到上手拒绝报告） |

### 

订单撤单状态

[](./appendix-enum-java.md#%E8%AE%A2%E5%8D%95%E6%92%A4%E5%8D%95%E7%8A%B6%E6%80%81-1)

| 状态  | 说明  |
| --- | --- |
| NONE | 默认状态 or 订单终结 |
| RECEIVED | 撤单已接收（pretrade检查通过） |
| FAILED | 撤单失败 (收到上手拒绝报告） |

### 

订单类型

[](./appendix-enum-java.md#%E8%AE%A2%E5%8D%95%E7%B1%BB%E5%9E%8B)

| 类型  | 说明  |
| --- | --- |
| MKT | 市价单 |
| LMT | 限价单 |
| STP | 止损单 |
| STP\_LMT | 止损限价单 |
| TRAIL | 跟踪止损单 |
| AM  | 竞价市价单（港股） |
| AL  | 竞价限价单（港股） |

### 

附加订单类型

[](./appendix-enum-java.md#%E9%99%84%E5%8A%A0%E8%AE%A2%E5%8D%95%E7%B1%BB%E5%9E%8B)

| 类型  | 说明  |
| --- | --- |
| PROFIT | 附加止盈单 |
| LOSS | 附加止损单 |
| BRACKETS | 附加括号单 |

### 

订单时段

[](./appendix-enum-java.md#%E8%AE%A2%E5%8D%95%E6%97%B6%E6%AE%B5)

| 类型  | 说明  |
| --- | --- |
| RTH | 盘中交易 |
| PRE\_RTH\_POST | 盘前/盘中/盘后交易 |
| OVERNIGHT | 夜盘交易 |
| FULL | 全时段 |
| HK\_AUC | 竞价时段交易(港股) |
| HK\_CTS | 持续交易时段交易(港股) |
| HK\_AUC\_CTS | 竞价/持续交易时段交易(港股) |

### 

账户状态

[](./appendix-enum-java.md#%E8%B4%A6%E6%88%B7%E7%8A%B6%E6%80%81)

`com.tigerbrokers.stock.openapi.client.struct.enums.AccountStatus`

| 状态  | 说明  |
| --- | --- |
| New | 新账户 |
| Funded | 已入金 |
| Open | 已开通 |
| Pending | 待确认 |
| Abandoned | 已废弃 |
| Rejected | 已拒绝 |
| Closed | 已关闭 |
| Unknown | 未知  |

### 

订单类型

[](./appendix-enum-java.md#%E8%AE%A2%E5%8D%95%E7%B1%BB%E5%9E%8B-1)

`com.tigerbrokers.stock.openapi.client.struct.enums.OrderType`

| 类型  | 说明  |
| --- | --- |
| MKT | 市价单 |
| LMT | 限价单 |
| STP | 止损单 |
| STP\_LMT | 止损限价单 |
| TRAIL | 跟踪止损单 |
| AM  | 竞价市价单（港股） |
| AL  | 竞价限价单（港股） |

### 

附加订单类型

[](./appendix-enum-java.md#%E9%99%84%E5%8A%A0%E8%AE%A2%E5%8D%95%E7%B1%BB%E5%9E%8B-1)

`com.tigerbrokers.stock.openapi.client.struct.enums.AttachType`

| 类型  | 说明  |
| --- | --- |
| PROFIT | 附加止盈单 |
| LOSS | 附加止损单 |
| BRACKETS | 附加括号单 |

### 

订单时段

[](./appendix-enum-java.md#%E8%AE%A2%E5%8D%95%E6%97%B6%E6%AE%B5-1)

`com.tigerbrokers.stock.openapi.client.struct.enums.TradingSessionType`

| 类型  | 说明  |
| --- | --- |
| PRE\_RTH\_POST | 包含盘前，盘中，盘后 |
| OVERNIGHT | 夜盘  |
| RTH | 盘中  |
| FULL | 全时段 |

### 

保证金账户类型

[](./appendix-enum-java.md#%E4%BF%9D%E8%AF%81%E9%87%91%E8%B4%A6%E6%88%B7%E7%B1%BB%E5%9E%8B)

| 类型  | 说明  |
| --- | --- |
| CASH | 现金账户 |
| RegTMargin | Reg T 保证金账户 |

### 

资产行情模式

[](./appendix-enum-java.md#%E8%B5%84%E4%BA%A7%E8%A1%8C%E6%83%85%E6%A8%A1%E5%BC%8F)

`com.tigerbrokers.stock.openapi.client.struct.enums.AssetQuoteType`

| 类型  | 说明  |
| --- | --- |
| ETH | 包含盘前、盘中、盘后行情，夜盘时段使用T-1日盘后收盘价计算 |
| RTH | 仅盘中行情，盘前、盘后、夜盘时段使用盘中收盘价计算 |
| OVERNIGHT | 包含夜盘行情，夜盘时段使用夜盘行情计算 |

### 

订阅主题

[](./appendix-enum-java.md#%E8%AE%A2%E9%98%85%E4%B8%BB%E9%A2%98)

交易订阅主题：`com.tigerbrokers.stock.openapi.client.struct.enums.Subject` 行情订阅主题：`com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject`

| Subject | 说明  |
| --- | --- |
| OrderStatus | 订单变化 |
| Asset | 资产  |
| Position | 持仓  |
| Quote | 股票行情 |
| Option | 期权行情 |
| Future | 期货行情 |
| QuoteDepth | 股票深度行情 |
| TradeTick | 股票逐笔成交 |

### 

K线类型

[](./appendix-enum-java.md#k%E7%BA%BF%E7%B1%BB%E5%9E%8B)

`com.tigerbrokers.stock.openapi.client.struct.enums.KType`

| 类型  | 说明  |
| --- | --- |
| day | 日K  |
| week | 周K  |
| month | 月K  |
| year | 年K  |
| min1 | 1分钟 |
| min3 | 3分钟 |
| min5 | 5分钟 |
| min15 | 15分钟 |
| min30 | 30分钟 |
| min60 | 60分钟 |

### 

订单变动

[](./appendix-enum-java.md#%E8%AE%A2%E5%8D%95%E5%8F%98%E5%8A%A8)

| 字段  | 说明  |
| --- | --- |
| id  | 订单号 |
| account | 资金账号 |
| symbol | 持仓标的代码，如 'AAPL', '00700', 'ES', 'CN' |
| expiry | 仅支持期权、窝轮、牛熊证 |
| strike | 仅支持期权、窝轮、牛熊证 |
| right | 仅支持期权、窝轮、牛熊证 |
| identifier | 标的标识符。股票的identifier与symbol相同。期货的会带有合约月份，如 'CN2201' |
| multiplier | 每手数量，仅限 futures, options, warrants, CBBC |
| action | 买卖方向。BUY表示买入，SELL表示卖出。 |
| market | 市场。US、HK |
| currency | 币种。USD美元，HKD港币 |
| segType | 按交易品种划分的分类。S表示股票，C表示期货 |
| secType | STK Stocks, OPT Options, WAR Warrants, IOPT CBBC, CASH FOREX, FUT Futures, FOP Future Options |
| orderType | 订单类型。'MKT'市价单/'LMT'限价单/'STP'止损单/'STP\_LMT'止损限价单/'TRAIL'跟踪止损单 |
| isLong | 是否多头持仓 |
| totalQuantity | 下单数量 |
| totalQuantityScale | 下单数量偏移量，如 totalQuantity=111， totalQuantityScale=2，那么真实 totalQuantity=111\*10^(-2)=1.11 |
| filledQuantity | 成交总数量（订单分多笔成交的，filledQuantity为累计成交总数） |
| filledQuantityScale | 成交总数量偏移量 |
| avgFillPrice | 成交均价 |
| limitPrice | 限价单价格 |
| stopPrice | 止损价格 |
| realizedPnl | 已实现盈亏（只有综合账号有这个字段） |
| status | [订单状态](./appendix-enum-java.md#order-status) |
| replaceStatus | [订单改单状态](./appendix-enum-java.md#order-replace-status) |
| cancelStatus | [订单撤单状态](./appendix-enum-java.md#order-cancel-status) |
| outsideRth | 是否允许盘前盘后交易，仅适用于美股 |
| canModify | 是否能修改 |
| canCancel | 是否能取消 |
| liquidation | 是否为平仓订单 |
| name | 标的名称 |
| source | 订单来源(from 'OpenApi', or other) |
| errorMsg | 错误信息 |
| attrDesc | 订单描述信息 |
| commissionAndFee | 佣金费用总计 |
| openTime | 下单时间 |
| timestamp | 订单状态最后更新时间 |
| userMark | 自定义标注信息 |
| totalCashAmount | 下单总金额（仅限金额订单） |
| filledCashAmount | 成交金额（仅限金额订单） |

### 

持仓变动

[](./appendix-enum-java.md#%E6%8C%81%E4%BB%93%E5%8F%98%E5%8A%A8)

| 字段  | 说明  |
| --- | --- |
| account | 用户账户 |
| symbol | 股票代码 |
| expiry | 过期日 期权、窝轮、牛熊证专属 |
| strike | 底层价格 期权、窝轮、牛熊证专属 |
| right | 期权方向 PUT/CALL 期权、窝轮、牛熊证专属 |
| identifier | 标的标识符 |
| multiplier | 1手单位 期权、窝轮、牛熊证专属 |
| market | 交易市场 |
| currency | 货币类型 |
| segType | 按交易品种划分的分类。S表示股票，C表示期货 |
| secType | 合约类型 |
| position | 持仓  |
| positionScale | 持仓数量的偏移量 |
| averageCost | 平均成本 |
| latestPrice | 最新价 |
| marketValue | 市值  |
| unrealizedPnl | 浮动盈亏 |
| name | 标的名称 |
| timestamp | 服务器时间 |

### 

资产变动

[](./appendix-enum-java.md#%E8%B5%84%E4%BA%A7%E5%8F%98%E5%8A%A8)

| 字段  | 说明  |
| --- | --- |
| account | 用户账户 |
| currency | 货币类型 |
| segType | 按交易品种划分的分类。S表示股票，C表示期货 |
| availableFunds | 可用资金(含借贷股权-初始保证金) |
| excessLiquidity | 剩余流动性(借贷值股权-维持保证金) |
| netLiquidation | 净清算值 |
| equityWithLoan | 含借贷值股权(含贷款价值资产) |
| buyingPower | 购买力 |
| cashBalance | 账户现金余额 |
| grossPositionValue | 持仓市值 |
| initMarginReq | 当前初始保证金 |
| maintMarginReq | 当前维持保证金 |
| timestamp | 服务器时间 |

### 

行情变动

[](./appendix-enum-java.md#%E8%A1%8C%E6%83%85%E5%8F%98%E5%8A%A8)

*   基本行情

| 字段  | 二级字段 | 说明  |
| --- | --- | --- |
| symbol |     | 股票代码 |
| type |     | 类型  |
| timestamp |     | 行情数据时间 |
| serverTimestamp |     | 服务器时间 |
| avgPrice |     | 平均价格 |
| latestPrice |     | 最新价格 |
| latestPriceTimestamp |     | 最新价格的时间戳(盘前,盘后没有) |
| latestTime |     | 最新价格的时间 |
| preClose |     | 昨日收盘价 |
| volume |     | 当日累计成交量 |
| amount |     | 当日累计成交量金额 (期权期货不支持) |
| open |     | 开盘价 |
| high |     | 最高价格 |
| low |     | 最低价格 |
| hourTradingTag |     | 盘前盘后标识(美股盘中没有值)，取值：PreMarket/AfterHours |
| marketStatus |     | 市场状态, 取值：Trading/WaitingOpen/OpeningAuction/InAuction/ClosingAuction/Closed/MiddayBreak/AfterHoursTrading/AfterHoursMatching/ExtendedTrading/PreMarket/Overnight |
| identifier |     | 标的标识符（仅限期权） |
| openInt |     | 未平仓数（仅限期权） |
| tradeTime |     | 交易时间（仅限期货） |
| preSettlement |     | 上个结清价（仅限期货） |
| minTick |     | 最小变动价（仅限期货） |
| mi  | p   | 分钟最新价格 |
| mi  | a   | 分钟平均价格 |
| mi  | t   | 分钟时间 |
| mi  | v   | 分钟成交量 |
| mi  | o   | 分钟开盘价 |
| mi  | h   | 分钟最高价 |
| mi  | l   | 分钟最低价 |

*   盘口最优买卖价行情

| 字段  | 说明  |
| --- | --- |
| askPrice | 卖盘价格 |
| askSize | 卖盘数量 |
| askTimestamp | ask timestamp(Pre/Post-Mkt data not support) |
| bidPrice | 买盘价格 |
| bidSize | 买盘数量 |
| bidTimestamp | bid timestamp(Pre/Post-Mkt data not support) |

### 

期权交易所

[](./appendix-enum-java.md#%E6%9C%9F%E6%9D%83%E4%BA%A4%E6%98%93%E6%89%80)

| 交易所 | 说明  |
| --- | --- |
| AMEX | NYSE MKTOptions Exchange |
| BOX | Boston Options Exchange |
| CBOE | Chicago Board of Options Exchange |
| EMLD | MIAX Emerald |
| EDGX | BATS EDGX |
| GEM | ISE Gemini |
| ISE | International Securities Exchange |
| MCRY | ISE Mercury |
| MIAX | Miami Options Exchange |
| ARCA | NYSE-ARCA Options Exchange |
| MPRL | MIAX-Pearl |
| NSDQ | NASDAQ |
| BX  | NASDAQ OMX BX |
| C2  | CBOE C2 Options |
| PHLX | Philadelphia Options Exchange |
| BZX | CBOE BZX / BATS Options |
| MEMX | MEMX |

### 

行情权限

[](./appendix-enum-java.md#%E8%A1%8C%E6%83%85%E6%9D%83%E9%99%90)

| 字段  | 说明  |
| --- | --- |
| usQuoteBasic | Nasdaq Basic |
| usStockQuote | usStockQuote |
| usStockQuoteLv2Totalview | Nasdaq Basic+TotalView |
| hkStockQuoteLv2 | 港股L2深度行情 |
| usOptionQuote | 期权L1实时行情 |
| HKEXFuturesQuoteLv2 | 香港期货交易所L2 |
| SGXFuturesQuoteLv2 | 新加坡期货交易所L2 |
| OSEFuturesQuoteLv2 | 大阪期货交易所L2 |
| CBOEFuturesQuoteLv2 | 芝加哥期权交易所L2 |

### 

订单描述

[](./appendix-enum-java.md#%E8%AE%A2%E5%8D%95%E6%8F%8F%E8%BF%B0)

| 描述信息 | 说明  |
| --- | --- |
| Exercise | 期权行权 |
| Expiry | 期权过期 |
| Assignment | 期权被动行权 |

### 

逐笔成交条件描述

[](./appendix-enum-java.md#%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4%E6%9D%A1%E4%BB%B6%E6%8F%8F%E8%BF%B0)

| 描述信息 | 市场  | 说明  |
| --- | --- | --- |
| US\_REGULAR\_SALE | 美国  | 自动对盘 |
| US\_BUNCHED\_TRADE | 美国  | 批量交易 |
| US\_CASH\_TRADE | 美国  | 现金交易 |
| US\_INTERMARKET\_SWEEP | 美国  | 跨市场交易 |
| US\_BUNCHED\_SOLD\_TRADE | 美国  | 批量卖出 |
| US\_PRICE\_VARIATION\_TRADE | 美国  | 离价交易 |
| US\_ODD\_LOT\_TRADE | 美国  | 碎股交易 |
| US\_RULE\_127\_OR\_155\_TRADE | 美国  | 纽交所 第127条交易 或 第155条交易 |
| US\_SOLD\_LAST | 美国  | 延迟交易 |
| US\_MARKET\_CENTER\_CLOSE\_PRICE | 美国  | 中央收市价 |
| US\_NEXT\_DAY\_TRADE | 美国  | 隔日交易 |
| US\_MARKET\_CENTER\_OPENING\_TRADE | 美国  | 中央开盘价交易 |
| US\_PRIOR\_REFERENCE\_PRICE | 美国  | 前参考价 |
| US\_MARKET\_CENTER\_OPEN\_PRICE | 美国  | 中央开盘价 |
| US\_SELLER | 美国  | 卖方  |
| US\_FORM\_T | 美国  | 盘前盘后交易 |
| US\_EXTENDED\_TRADING\_HOURS | 美国  | 延长交易时段 |
| US\_CONTINGENT\_TRADE | 美国  | 合单交易 |
| US\_AVERAGE\_PRICE\_TRADE | 美国  | 均价交易 |
| US\_CROSS\_TRADE | 美国  | US\_CROSS\_TRADE |
| US\_SOLD\_OUT\_OF\_SEQUENCE | 美国  | 场外售出 |
| US\_ODD\_LOST\_CROSS\_TRADE | 美国  | 碎股跨市场交易 |
| US\_DERIVATIVELY\_PRICED | 美国  | 衍生工具定价 |
| US\_MARKET\_CENTER\_RE\_OPENING\_TRADE | 美国  | 再开盘定价 |
| US\_MARKET\_CENTER\_CLOSING\_TRADE | 美国  | 收盘定价 |
| US\_QUALIFIED\_CONTINGENT\_TRADE | 美国  | 合单交易 |
| US\_CONSOLIDATED\_LAST\_PRICE\_PER\_LISTING\_PACKET | 美国  | 综合延迟价格 |
| HK\_AUTOMATCH\_NORMAL | 香港  | 自动对盘 |
| HK\_ODD\_LOT\_TRADE | 香港  | 碎股交易 |
| HK\_AUCTION\_TRADE | 香港  | 竞价交易 |
| HK\_OVERSEAS\_TRADE | 香港  | 场外交易 |
| HK\_LATE\_TRADE\_OFF\_EXCHG | 香港  | 开市前成交 |
| HK\_NON\_DIRECT\_OFF\_EXCHG\_TRADE | 香港  | 非自动对盘 |
| HK\_DIRECT\_OFF\_EXCHG\_TRADE | 香港  | 同券商自动对盘 |
| HK\_AUTOMATIC\_INTERNALIZED | 香港  | 同券商非自动对盘 |

### 

选股器-基础指标筛选字段

[](./appendix-enum-java.md#%E9%80%89%E8%82%A1%E5%99%A8-%E5%9F%BA%E7%A1%80%E6%8C%87%E6%A0%87%E7%AD%9B%E9%80%89%E5%AD%97%E6%AE%B5)

Java

    public enum StockField {
        /** 股票代码*，不能填区间上下限值。 */
        /** 最新价*（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
        StockField_CurPrice(2, "latestPrice"),
        /** 买入价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
        StockField_BidPrice(3, "bidPrice"),
        /** 卖出价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
        StockField_AskPrice(4, "askPrice"),
        /** 今开价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
        StockField_OpenPrice(5, "open"),
        /** 昨收价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
        StockField_PreClosePrice(6, "preClose"),
        /** 最高价 */
        StockField_HighPrice(7, "high"),
        /** 最低价 */
        StockField_LowPrice(8, "low"),
        /** 盘前价*（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
        StockField_HourTradingPrePrice(9, "hourTradingPrePrice"),
        /** 盘后价*（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
        StockField_HourTradingAfterPrice(10, "hourTradingAfterPrice"),
        /** 成交量* */
        StockField_Volume(11, "volume"),
        /** 成交额* */
        StockField_Amount(12, "amount"),
        /** 流通股本* */
        StockField_FloatShare(13, "floatShares"),
        /** 52周最高价格* */
        StockField_Week52High(14, "week52High"),
        /** 52周最低价格* */
        StockField_Week52Low(15, "week52Low"),
        /** 通市值* FloatMarketVal  自己计算 FloatShare* 当前价格 */
        StockField_FloatMarketVal(16, "floatMarketCap"),
        /** 总市值*  MarketVal  shares * 当前价格 */
        StockField_MarketValue(17, "marketValue"),
        /** 盘前涨跌幅   (curPrice-盘前左收）自己计算 最新价-close / close */
        StockField_preHourTradingChangeRate(18, "preHourTradingChangeRate"),
        /** 盘后涨跌幅 自己计算 */
        StockField_postHourTradingChangeRate(19, "postHourTradingChangeRate"),
        /** 每股收益 滚动市盈率 TTM=过去12个月  Last Twelve Month  通过hermes获取 eps */
        StockField_ttm_Eps(20, "ttmEps"),
        /** 量比*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        StockField_VolumeRatio(21, "volumeRatio"),
        /** 委比*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        StockField_BidAskRatio(22, "committee"),
        /** 下次财报日期 * */
        StockField_EarningDate(23, "earningDate"),
        /** 市盈率* TTM（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        StockField_PeTTM(24, "peRate"),
        /** 股息   hermes $ */
        StockField_DividePrice(26, "dividePriceVal"),
        /** 股息收益率 选股服务自身计算 */
        StockField_DivideRate(27, "divideRateVal"),
        /** 股票交易市场 */
        StockField_Exchange(29, "exchange"),
        /** 换手率*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        StockField_TurnoverRate(30, "turnoverRate"),
        /** 上市时间 */
        StockField_ListingDate(31, "listingDate"),
        /** 总股本* */
        StockField_Share(33, "shares"),
        /** 上市价格* */
        StockField_ListingPrice(34, "listingPrice"),
        /** 最新价-发行价* */
        StockField_DiffBetweenLastPriceAndListPrice(36, "DiffBetweenLastPriceAndListPrice"),
        /** 每股收益 lyr=Last Year Ratio 静态市盈率 */
        StockField_lyr_Eps(37, "lyrEps"),
        /** 未平仓做空量 */
        StockField_Open_Short_Interest(38, "OpenShortInterestVal"),
        /** 未平仓做空比例 = 未平仓做空量/总股本 */
        StockField_Open_Short_Interest_Ratio(39, "OpenShortInterestRatio"),
        /** 产权比率 = Liability/Equity 总负债/股东 */
        StockField_Equity_Ratio(40, "totalDebtToEquity"),
        /** 权益乘数 = Asset/Equity */
        StockField_Equity_Multiplier(41, "totalLiabilitiesToTotalAssets"),
        /** 最新股东数 */
        StockField_Holder_Nums(42, "holderNums"),
        /** 最新股东户数增长率 */
        StockField_Holder_Nums_Ratio(43, "holderRatio"),
        /** 户均持股数量 */
        StockField_Per_Hold_Nums(44, "perHolderNums"),
        /** 户均持股金额 */
        StockField_Per_Hold_Money(45, "perHolderMoney"),
        /** 户均持股数半年增长率 */
        StockField_HalfYear_Holder_Nums_Ratio(46, "HalfYearholderRatio"),
        /** 发行时间 - ETF */
        StockField_InceptionDate(47, "inceptionDate"),
        /** 申购费用 - ETF */
        StockField_CreationFee(48, "creationFee"),
        /** 管理费用 - ETF */
        StockField_ManagementFee(49, "managementFee"),
        /** 成分股Top10 占比 - ETF */
        StockField_Top10_Composition_Rate(50, "Top10CompoRate"),
        /** 成分股Top15 占比 - ETF */
        StockField_Top15_Composition_Rate(51, "Top15CompoRate"),
        /** 成分股Top20 占比 - ETF */
        StockField_Top20_Composition_Rate(52, "Top20CompoRate"),
        /** 溢价率(折扣率) - ETF */
        StockField_DiscountPremium(53, "discountPremium"),
        /** 资产规模-净值 - ETF */
        StockField_Net_Worth_Aum(55, "aum"),
        /** 资产规模-现价 - ETF */
        StockField_assetSize(56, "assetSize"),
        /** 振幅 */
        StockField_Amplitude(57, "Amplitude"),
        /** 盘前涨跌幅 */
        StockField_Pre_ChangeRate(58, "preChangeRate"),
        /** 盘中涨跌幅 */
        StockField_current_ChangeRate(59, "curChangeRate"),
        /** 盘后涨跌幅 */
        StockField_Post_ChangeRate(60, "postChangeRate"),
        /** 成分变动 - etf */
        StockField_ETF_LastHoldingChangeDay(61, "LastHoldingChangeDay"),
        /** 持仓数量 - etf */
        StockField_ETF_HoldingCount(62, "etfHoldingCount"),
        /** 净利润 不带周期 */
        StockField_Net_Income(63, "netIncomeVal"),
        ;
    }

### 

选股器-累积指标筛选字段

[](./appendix-enum-java.md#%E9%80%89%E8%82%A1%E5%99%A8-%E7%B4%AF%E7%A7%AF%E6%8C%87%E6%A0%87%E7%AD%9B%E9%80%89%E5%AD%97%E6%AE%B5)

Java

    public enum AccumulateField {
        /** 涨跌幅*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间  */
        AccumulateField_ChangeRate(1, "changeRate"),
        /** 涨跌额*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        AccumulateField_ChangeValue(2, "changeVal"),
        /** 总负债增长率 */
        AccumulateField_TotalLiabilities_Ratio_Annual(3, "totalLiabilitiesRatio"),
        /** 净资产增长率 */
        AccumulateField_TotalCommonEquity_Ratio_Annual(4, "totalCommonEquityRatio"),
        /** 每股收益同比增长率 */
        AccumulateField_BasicEps_Ratio_Annual(5, "basicEpsRatio"),
        /** 净利润同比增长率 */
        AccumulateField_NetIncome_Ratio_Annual(6, "netIncomeRatio"),
        /** 营业利润同比增长率 */
        AccumulateField_OperatingIncome_Ratio_Annual(7, "opeIncomeratio"),
        /** 每股收益 */
        AccumulateField_Eps(8, "eps"),
        /** 每股净资产 */
        AccumulateField_NetAsset_PerShare(9, "bookValueshare"),
        /** 净利润 */
        AccumulateField_Net_Income(10, "netIncome"),
        /** 营业利润 */
        AccumulateField_Operating_Income(11, "operatingIncome"),
        /** 营业收入 */
        AccumulateField_Total_Revenue(12, "total_revenue"),
        /** ROE = 资产回报率 */
        AccumulateField_ROE(13, "ROE"),
        /** ROA =净资产收益率 */
        AccumulateField_ROA(14, "ROA"),
        /** 毛利率 */
        AccumulateField_GrossProfitRate(17, "grossMargin"),
        /** 净利率* */
        AccumulateField_NetProfitRate(18, "netIncomeMargin"),
        /** 总资产* */
        AccumulateField_TotalAssets(19, "totalAssets"),
        /** 流动比率 */
        AccumulateField_CurrentRatio(20, "currentRatio"),
        /** 速动比率 */
        AccumulateField_QuickRatio(21, "quickRatio"),
        /** 经营现金流同比率 */
        AccumulateField_CashFromOpsRatio(22, "cash4OpsRatio"),
        /** 投资现金流 */
        AccumulateField_CashFromInvesting(23, "cash4Invest"),
        /** 筹资现金流 */
        AccumulateField_CashFromFinancing(24, "cash4Finance"),
        /** 资产负债率 */
        AccumulateField_TotalLiabilitiesToTotalAssets(25, "allLiabAndAssets"),
        /** 净资产收益率ROE同比增长率  （T期ROE-T-1期ROE）/T-1期ROE *100%*/
        AccumulateField_ROE_yearOnYear_Ratio(27, "netIncomeYearOnYearRatio"),
        /** 营业利润占比 */
        AccumulateField_Operating_Profits_Ratio(28, "OperatingProfitsRatio"),
        /** 经营现金流  */
        AccumulateField_CashFromOpsVal(29, "cash4OpsVal"),
        ;
    }

### 

选股器-财务指标筛选字段

[](./appendix-enum-java.md#%E9%80%89%E8%82%A1%E5%99%A8-%E8%B4%A2%E5%8A%A1%E6%8C%87%E6%A0%87%E7%AD%9B%E9%80%89%E5%AD%97%E6%AE%B5)

Java

    public enum FinancialField {
        /** 毛利率*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        FinancialField_GrossProfitRate(1, "grossMarginVal"),
        /** 净利率*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        FinancialField_NetProfitRate(2, "netIncomeMarginVal"),
        /** 扣非净利润率  *（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        FinancialField_EarningsFromContOpsMargin(3, "earningsFromContOpsMargin"),
        /** 长期负债/股东权益 **/
        FinancialField_LongTermDebtToEquity(5, "ltDebtToEquity"),
        /** EBIT/利息支出 **/
        FinancialField_EbitToInterestExp(6, "ebitToInterestExp"),
        /** 总资产周转率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        FinancialField_TotalAssetTurnover(8, "totalAssetTurnover"),
        /** 应收帐款周转率 */
        FinancialField_AccountsReceivableTurnover(9, "accountsReceivableTurnover"),
        /** 存货周转率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        FinancialField_InventoryTurnover(10, "inventoryTurnover"),
        /** 流动比率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        FinancialField_CurrentRatio(11, "currentRatioVal"),
        /** 速动比率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        FinancialField_QuickRatio(12, "quickRatioVal"),
        /** 资产回报率 总资产收益率 *$ TTM（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        FinancialField_ROATTM(13, "roa"),
        /** 净资产收益率 $（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        FinancialField_ReturnOnEquityRate(14, "roe"),
        /** 营业收入一年增长率 或者 营收增长率 */
        FinancialField_TotalRevenues1YrGrowth(15, "totalRevenues1YrGrowth"),
        /** 毛利润率一年增长率  营业利润增长率 */
        FinancialField_GrossProfit1YrGrowth(16, "grossProfit1YrGrowth"),
        /** 净利润一年增长率 */
        FinancialField_NetIncome1YrGrowth(17, "netIncome1YrGrowth"),
        /** 应收帐款一年增长率 */
        FinancialField_AccountsReceivable1YrGrowth(18, "accountsReceivable1YrGrowth"),
        /** 存货一年增长率 */
        FinancialField_Inventory1YrGrowth(19, "inventory1YrGrowth"),
        /** 总资产一年增长率 */
        FinancialField_TotalAssets1YrGrowth(20, "totalAssets1YrGrowth"),
        /** 有形资产一年增长率 */
        FinancialField_TangibleBookValue1YrGrowth(21, "tangibleBookValue1YrGrowth"),
        /** 经营现金流一年增长率 = 经营现金流同比增长率 */
        FinancialField_CashFromOperations1YrGrowth(22, "cashFromOperations1YrGrowth"),
        /** 资本开支一年增长率 */
        FinancialField_CapitalExpenditures1YrGrowth(23, "capitalExpenditures1YrGrowth"),
        /** 营业收入三年增长率 或者叫 营收3年复合增长率 */
        FinancialField_TotalRevenues3YrCagr(24, "totalRevenues3YrCagr"),
        /** 毛利润率三年增长率 */
        FinancialField_GrossProfit3YrCagr(25, "grossProfit3YrCagr"),
        /** 净利润三年增长率 */
        FinancialField_NetIncome3YrCagr(26, "netIncome3YrCagr"),
        /** 应收帐款三年增长率 */
        FinancialField_AccountsReceivable3YrCagr(27, "accountsReceivable3YrCagr"),
        /** 存货三年增长率 */
        FinancialField_Inventory3YrCagr(28, "inventory3YrCagr"),
        /** 总资产三年增长率 */
        FinancialField_TotalAssets3YrCagr(29, "totalAssets3YrCagr"),
        /** 有形资产三年增长率 */
        FinancialField_TangibleBookValue3YrCagr(30, "tangibleBookValue3YrCagr"),
        /** 经营现金流三年增长率 */
        FinancialField_CashFromOps3YrCagr(31, "cashFromOps3YrCagr"),
        /** 资本开支三年增长率 */
        FinancialField_CapitalExpenditures3YrCagr(32, "capitalExpenditures3YrCagr"),
        /** 净利润 */
        FinancialField_NetIncomeToCompany(33, "netIncomeToCompany"),
        /** 经营现金流 */
        FinancialField_CashFromOperations(34, "cashFromOps"),
        /** 投资现金流 */
        FinancialField_CashFromInvesting(35, "cashFromInvesting"),
        /** 筹资现金流 */
        FinancialField_CashFromFinancing(36, "cashFromFinancing"),
        /** 净利润2年复合增长率 */
        FinancialField_NormalizedNetIncome2YrCagr(37, "netIncome2YrCagr"),
        /** 营收2年复合增长率 */
        FinancialField_TotalRevenues2YrCagr(38, "totalRevenues2YrCagr"),
        /** 净利润5年复合增长率 */
        FinancialField_NetIncome5YrCagr(39, "netIncome5YrCagr"),
        /** 营收5年复合增长率 */
        FinancialField_TotalRevenues5YrCagr(40, "totalRevenues5YrCagr"),
        /** 总资产 */
        FinancialField_TotalAssets(41, "totalAssetsVal"),
        /** 固定资产周转率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
        FinancialField_FixedAssetTurnover(42, "fixedAssetTurnover"),
        /** 营业利润 */
        FinancialField_OperatingIncome(43, "operatingIncomeVal"),
        /** 营业总收入 */
        FinancialField_TotalRevenue(44, "totalRevenue"),
        /** 市盈率LYR PE =price-to-earnings ratio */
        FinancialField_LYR_PE(45, "LyrPE"),
        /** 市盈率TTM PE =price-to-earnings ratio */
        FinancialField_TTM_PE(46, "ttmPE"),
        /** 市销率LYR PS =Price-to-sales Ratio */
        FinancialField_LYR_PS(47, "LyrPS"),
        /** 市销率TTM PS =Price-to-sales Ratio */
        FinancialField_TTM_PS(48, "ttmPS"),
        /** 当日主力净流入额 */
        FinancialField_LargeInflowAmountToday(49, "largeInflowAmountToday"),
        /** 当日主力增仓占比 */
        FinancialField_LargeInflowAmountTodayPre(50, "largeInflowAmountTodayPre"),
        /** 未平仓做空量 */
        FinancialField_ShortInterest(51, "shortInterest"),
        /** 未平仓做空比例 */
        FinancialField_ShortInterestPre(52, "shortInterestPre"),
        /** 港股通持股比例=港股通(深)持股比例=港股通(沪)持股比例 */
        FinancialField_HK_StockConnectRate(53, "hkStockConnectRate"),
        /** 沪股通持股比例 */
        FinancialField_SH_StockConnectRate(54, "shStockConnectRate"),
        /** 深股通持股比例 */
        FinancialField_SZ_StockConnectRate(55, "szStockConnectRate"),
        /** 营业利润占比 */
        FinancialField_Operating_Profits_Rate(56, "operatingProfitsRate"),
        /** 港股通(沪)净买入额 */
        FinancialField_HK_StockShConnectInflow(57, "hkStockShConnectInflow"),
        /** 港股通(深)净买入额 */
        FinancialField_HK_StockSzConnectInflow(58, "hkStockSzConnectInflow"),
        /** 沪股通净买入额 */
        FinancialField_SH_StockConnectInflow(59, "shStockConnectInflow"),
        /** 深股通净买入额 */
        FinancialField_SZ_StockConnectInflow(60, "szStockConnectInflow"),
        /** 上市以来年化收益率 ETF */
        FinancialField_ListingAnnualReturn(61, "listingAnnualReturn"),
        /** 近1年年化收益率  ETF */
        FinancialField_LstYearAnnualReturn(62, "lstYearAnnualReturn"),
        /** 近2年年化收益率  ETF */
        FinancialField_Lst2YearAnnualReturn(63, "lst2YearAnnualReturn"),
        /** 近5年年化收益率  ETF */
        FinancialField_Lst5YearAnnualReturn(64, "lst5YearAnnualReturn"),
        /** 上市以来年化波动率  ETF */
        FinancialField_ListingAnnualVolatility(65, "listingAnnualVolatility"),
        /** 近1年年化波动率  ETF */
        FinancialField_LstYearAnnualVolatility(66, "lstYearAnnualVolatility"),
        /** 近2年年化波动率  ETF */
        FinancialField_Lst2YearAnnualVolatility(67, "lst2YearAnnualVolatility"),
        /** 近5年年化波动率  ETF */
        FinancialField_Lst5YearAnnualVolatility(68, "lst5YearAnnualVolatility"),
        /** 市净率LYR PB =price/book value ratio */
        FinancialField_LYR_PB(69, "LyrPB"),
        /** 市净率TTM PB =price/book value ratio */
        FinancialField_TTM_PB(70, "ttmPB"),
        ;
    }

### 

选股器-多标签筛选字段

[](./appendix-enum-java.md#%E9%80%89%E8%82%A1%E5%99%A8-%E5%A4%9A%E6%A0%87%E7%AD%BE%E7%AD%9B%E9%80%89%E5%AD%97%E6%AE%B5)

Java

    public enum MultiTagField {
        /** 所属行业 */
        MultiTagField_Industry(1, "industry"),
        /** 所属概念 */
        MultiTagField_Concept(2, "concept"),
        /** 是否为otc股票.1=是，0=否 */
        MultiTagField_isOTC(3, "isOTC"),
        MultiTagField_StockCode(4, "symbol"),
        /** 股票类型 stock or etf ;股票类型,非0表示该股票是ETF,1表示不带杠杆的etf,2表示2倍杠杆etf,3表示3倍etf杠杆,负值表示反向的ETF */
        MultiTagField_Type(5, "type"),
        /** 成交量异常.1=是，0=否 ;当日实时成交量> 5* 最近一年的平均成交量 */
        MultiTagField_Volume_Spike(6, "volSpike"),
        /** 破净股票；市净率PB<1 */
        MultiTagField_Net_Broken(7, "netBroken"),
        /** 破发股票 ； 最新价<发行价 */
        MultiTagField_Issue_Price_Broken(8, "issuePriceBroken"),
        /** 跟踪指数/资产 - ETF */
        MultiTagField_PrimaryBenchmark(9, "primaryBenchmark"),
        /** 发行人 - ETF */
        MultiTagField_Issuer(10, "issuer"),
        /** 托管人 - ETF */
        MultiTagField_Custodian(11, "custodian"),
        /** 分红频率 - ETF */
        MultiTagField_DistributionFrequency(12, "distributionFrequency"),
        /** 是否支持期权 - ETF ; 1=是，0=否 */
        MultiTagField_OptionsAvailable(13, "optionsAvailable"),
        /** 今日创历史新高 - ETF 1=是，0=否 */
        MultiTagField_Today_HistoryHigh(14, "todayHistoryHigh"),
        /** 今日创历史新低 - ETF 1=是，0=否 */
        MultiTagField_Today_HistoryLow(15, "todayHistoryLow"),
        /** 股票包 */
        MultiTagField_Stock_Package(16, "StockPkg"),
        /** 52周最高 0 否 1是* */
        MultiTagField_Week52HighFlag(17, "week52HighFlag"),
        /** 52周最低 0 否 1是 */
        MultiTagField_Week52LowFlag(18, "week52LowFlag"),
        /** 交易币种 ,需要具体币种 */
        MultiTagField_TradeCurrency(19, "tradeCurrency"),
        /** ETF类型 ，需要具体类型 */
        MultiTagField_ETF_TYPE(20, "etfType"),
        /** 股票市场，这里支持多个市场 ，需要具体类型 QotMarket股票市场,传递里面的value值 */
        MultiTagField_Market_Name(21, "marketName"),
        /** 一级行业级别  需要传递具体sectorId */
        MultiTagField_One_Sectors_Level(22, "oneSectorsLevel"),
        ;
    }

### 

排序方向

[](./appendix-enum-java.md#%E6%8E%92%E5%BA%8F%E6%96%B9%E5%90%91)

Java

    public enum SortDir {
        SortDir_No(0),
        SortDir_Ascend(1),
        SortDir_Descend(2);
    }
