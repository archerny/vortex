# Order Templates — 订单模板

## 概述

Schwab API 的订单规范（Order Spec）格式复杂，即使是简单的订单也需要指定大量字段。订单模板提供了常用订单类型的快速构建方式。

所有模板返回 `OrderBuilder` 对象，可进一步修改后提交。

### 默认设置

- 交易时段: 当前正常交易时段（`NORMAL`）
- 有效期: 当日有效（`DAY`）
- 其他字段保持默认

### 修改默认设置示例

```python
from schwab.orders.equities import equity_buy_limit
from schwab.orders.common import Duration, Session

client.place_order(
    account_hash,
    equity_buy_limit('GOOG', 1, 1250.0)
        .set_duration(Duration.GOOD_TILL_CANCEL)
        .set_session(Session.SEAMLESS)
        .build())
```

---

## 一、股票订单模板

### 买入

```python
from schwab.orders.equities import equity_buy_market, equity_buy_limit

# 市价买入 100 股 AAPL
equity_buy_market('AAPL', 100)

# 限价买入 100 股 AAPL，限价 $150
equity_buy_limit('AAPL', 100, 150.0)
```

### 卖出

```python
from schwab.orders.equities import equity_sell_market, equity_sell_limit

# 市价卖出 100 股 AAPL
equity_sell_market('AAPL', 100)

# 限价卖出 100 股 AAPL，限价 $200
equity_sell_limit('AAPL', 100, 200.0)
```

### 做空

```python
from schwab.orders.equities import equity_sell_short_market, equity_sell_short_limit

equity_sell_short_market('AAPL', 100)
equity_sell_short_limit('AAPL', 100, 200.0)
```

### 买入平仓

```python
from schwab.orders.equities import equity_buy_to_cover_market, equity_buy_to_cover_limit

equity_buy_to_cover_market('AAPL', 100)
equity_buy_to_cover_limit('AAPL', 100, 150.0)
```

---

## 二、期权订单模板

### 构建期权标的代码

```python
from schwab.orders.options import OptionSymbol
import datetime

symbol = OptionSymbol(
    'TSLA',
    datetime.date(year=2026, month=11, day=20),
    'P',  # P=Put, C=Call
    '1360'
).build()
```

> 也可以通过 `get_option_chain()` API 获取真实交易的期权标的代码。

### 单腿期权

```python
from schwab.orders.options import (
    option_buy_to_open_market,
    option_buy_to_open_limit,
    option_sell_to_open_market,
    option_sell_to_open_limit,
    option_buy_to_close_market,
    option_buy_to_close_limit,
    option_sell_to_close_market,
    option_sell_to_close_limit,
)

# 买入开仓（市价）
option_buy_to_open_market(option_symbol, 1)

# 买入开仓（限价）
option_buy_to_open_limit(option_symbol, 1, 5.50)

# 卖出平仓（市价）
option_sell_to_close_market(option_symbol, 1)
```

### 垂直价差（Vertical Spreads）

#### 看涨垂直价差（Call Verticals）

```python
from schwab.orders.options import (
    bull_call_vertical_open,
    bull_call_vertical_close,
    bear_call_vertical_open,
    bear_call_vertical_close,
)

# 牛市看涨价差 — 开仓
bull_call_vertical_open(lower_strike_symbol, upper_strike_symbol, quantity, net_debit)

# 牛市看涨价差 — 平仓
bull_call_vertical_close(lower_strike_symbol, upper_strike_symbol, quantity, net_credit)

# 熊市看涨价差 — 开仓/平仓
bear_call_vertical_open(lower_strike_symbol, upper_strike_symbol, quantity, net_credit)
bear_call_vertical_close(lower_strike_symbol, upper_strike_symbol, quantity, net_debit)
```

#### 看跌垂直价差（Put Verticals）

```python
from schwab.orders.options import (
    bull_put_vertical_open,
    bull_put_vertical_close,
    bear_put_vertical_open,
    bear_put_vertical_close,
)
```

> 规则：行权价较低的期权始终作为第一个参数，较高的作为第二个参数。

---

## 三、复合订单策略

### One Cancels Other（OCO）

两个订单中任一个执行后，另一个自动取消。

```python
from schwab.orders.common import one_cancels_other

one_cancels_other(
    equity_sell_limit('GOOG', 1, 1400),   # 止盈
    equity_sell_limit('GOOG', 1, 1250)     # 止损
        .set_order_type(OrderType.STOP_LIMIT)
        .clear_price()
        .set_stop_price(1250)
)
```

### First Triggers Second

第一个订单执行后，自动触发第二个订单。

```python
from schwab.orders.common import first_triggers_second

first_triggers_second(
    equity_buy_limit('GOOG', 1, 1310),    # 买入
    one_cancels_other(                      # 触发止盈止损
        equity_sell_limit('GOOG', 1, 1400),
        equity_sell_limit('GOOG', 1, 1250)
            .set_order_type(OrderType.STOP_LIMIT)
            .clear_price()
            .set_stop_price(1250)
    )
)
```

> ⚠️ 不要先 `place_order` 然后传入结果，而是直接传入 `OrderBuilder` 对象。

---

## 四、订单规范（Order Spec）结构

### 简单限价买入

```json
{
  "session": "NORMAL",
  "duration": "DAY",
  "orderType": "LIMIT",
  "price": "190.90",
  "orderLegCollection": [
    {
      "instruction": "BUY",
      "instrument": {
        "assetType": "EQUITY",
        "symbol": "MSFT"
      },
      "quantity": 1
    }
  ],
  "orderStrategyType": "SINGLE"
}
```

### 订单类型（orderType）

`MARKET`, `LIMIT`, `STOP`, `STOP_LIMIT`, `TRAILING_STOP`, `TRAILING_STOP_LIMIT`, `CABINET`, `NON_MARKETABLE`, `MARKET_ON_CLOSE`, `EXERCISE`, `NET_DEBIT`, `NET_CREDIT`, `NET_ZERO`, `LIMIT_ON_CLOSE`

### 交易时段（session）

`NORMAL`, `AM`, `PM`, `SEAMLESS`

### 有效期（duration）

`DAY`, `GOOD_TILL_CANCEL`, `FILL_OR_KILL`, `IMMEDIATE_OR_CANCEL`, `END_OF_WEEK`, `END_OF_MONTH`, `NEXT_END_OF_MONTH`, `UNKNOWN`

### 订单策略类型（orderStrategyType）

`SINGLE`, `OCO`, `TRIGGER`

### 股票交易指令（instruction）

`BUY`, `SELL`, `BUY_TO_COVER`, `SELL_SHORT`

### 期权交易指令

`BUY_TO_OPEN`, `BUY_TO_CLOSE`, `SELL_TO_OPEN`, `SELL_TO_CLOSE`

### 资产类型（assetType）

`EQUITY`, `OPTION`

> ⚠️ **已知限制**：Schwab Trader API 目前仅支持**股票和期权**的订单。虽然期货可以在 Schwab 平台交易，但此 API 不支持期货订单。

## 相关文档

- [API 端点参考](./api-reference.md)
- [订单构建器参考](./order-builder.md)
