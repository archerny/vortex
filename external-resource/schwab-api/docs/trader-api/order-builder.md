# OrderBuilder Reference — 订单构建器参考

## 概述

`OrderBuilder` 是构建复杂订单规范（Order Spec）的工具类。Schwab API 使用统一的 JSON 格式来表达所有类型的订单，从简单的股票市价单到复杂的多腿期权条件单。

如果只需要常用订单类型，推荐使用 [订单模板](./order-templates.md)。`OrderBuilder` 适用于需要精细控制的高级用法。

## 从历史订单生成代码

可以先在 thinkorswim 或 Schwab 网页端下一个订单，然后用脚本自动生成对应的代码：

```bash
schwab-order-codegen.py \
  --token_file <token文件路径> \
  --api_key <API Key>
```

这会输出最近一笔订单对应的 `schwab-py` 代码，可以直接复制修改使用。

---

## 订单类型（Order Types）

| 类型 | 说明 |
|------|------|
| `MARKET` | 市价单 |
| `LIMIT` | 限价单 |
| `STOP` | 止损单 |
| `STOP_LIMIT` | 止损限价单 |
| `TRAILING_STOP` | 追踪止损单 |
| `TRAILING_STOP_LIMIT` | 追踪止损限价单 |
| `CABINET` | Cabinet 单 |
| `NON_MARKETABLE` | 非市价单 |
| `MARKET_ON_CLOSE` | 收盘市价单 |
| `EXERCISE` | 行权 |
| `NET_DEBIT` | 净借方（期权价差） |
| `NET_CREDIT` | 净贷方（期权价差） |
| `NET_ZERO` | 净零（期权价差） |
| `LIMIT_ON_CLOSE` | 收盘限价单 |

```python
builder.set_order_type(OrderType.LIMIT)
builder.clear_order_type()
```

## 交易时段和有效期

### 交易时段（Session）

| 值 | 说明 |
|------|------|
| `NORMAL` | 正常交易时段 |
| `AM` | 盘前 |
| `PM` | 盘后 |
| `SEAMLESS` | 全时段 |

### 有效期（Duration）

| 值 | 说明 |
|------|------|
| `DAY` | 当日有效 |
| `GOOD_TILL_CANCEL` | GTC，直到取消 |
| `FILL_OR_KILL` | 全部成交或取消 |
| `IMMEDIATE_OR_CANCEL` | 立即成交或取消 |
| `END_OF_WEEK` | 周末前有效 |
| `END_OF_MONTH` | 月末前有效 |
| `NEXT_END_OF_MONTH` | 下月末前有效 |

```python
builder.set_duration(Duration.GOOD_TILL_CANCEL)
builder.set_session(Session.SEAMLESS)
```

## 价格

对于股票和简单期权限价单，`price` 是期望的买入/卖出价格。

对于复合期权限价单（如价差），`price` 是期望的总净借方/贷方金额。

> **价格截断规则**：
> - 绝对值小于 1 的价格：截断到 4 位小数（如 `0.186992` → `0.1869`）
> - 其他价格：截断到 2 位小数（如 `190.926` → `190.92`）
> - 可以传入字符串类型来避免截断

```python
builder.set_price(190.50)
builder.copy_price(other_builder)
builder.clear_price()
```

## 订单腿（Order Legs）

### 股票腿

```python
builder.add_equity_leg(EquityInstruction.BUY, 'AAPL', 100)
```

**股票指令**: `BUY`, `SELL`, `BUY_TO_COVER`, `SELL_SHORT`

### 期权腿

```python
builder.add_option_leg(OptionInstruction.BUY_TO_OPEN, option_symbol, 1)
```

**期权指令**: `BUY_TO_OPEN`, `BUY_TO_CLOSE`, `SELL_TO_OPEN`, `SELL_TO_CLOSE`

```python
builder.clear_order_legs()
```

## 目标交易所

```python
builder.set_destination_link_name(Destination.INET)  # 指定交易所
builder.clear_destination_link_name()
```

> Schwab 默认将订单路由到最优价格的交易所。

## 特殊指令

| 指令 | 说明 |
|------|------|
| `ALL_OR_NONE` | 全部成交或不成交 |
| `DO_NOT_REDUCE` | 不减少 |
| `ALL_OR_NONE_DO_NOT_REDUCE` | 组合 |

```python
builder.set_special_instruction(SpecialInstruction.ALL_OR_NONE)
```

## 复杂期权策略

支持的策略类型：

`VERTICAL`, `CALENDAR`, `STRANGLE`, `STRADDLE`, `BUTTERFLY`, `CONDOR`, `DIAGONAL`, `COLLAR`, `ROLL`, `COVERED`, `BACK_RATIO`, `CUSTOM`

```python
builder.set_complex_order_strategy_type(ComplexOrderStrategyType.VERTICAL)
```

## 复合订单

### OCO（One Cancels Other）

```python
builder.set_order_strategy_type(OrderStrategyType.OCO)
```

### Trigger

```python
builder.set_order_strategy_type(OrderStrategyType.TRIGGER)
```

## 止损配置

```python
builder.set_stop_price(250.00)
builder.set_stop_price_link_basis(StopPriceLinkBasis.MANUAL)
builder.set_stop_price_link_type(StopPriceLinkType.VALUE)
builder.set_stop_price_offset(5.00)
builder.set_stop_type(StopType.STANDARD)
```

## 相关文档

- [订单模板](./order-templates.md)
- [API 端点参考](./api-reference.md)
