# WebSocket Streaming — 实时流式数据

## 概述

Schwab Streaming API 是基于 WebSocket 的实时数据推送接口，提供毫秒级的市场数据更新。支持 Level 1/2 报价、K 线图表、期权、期货和外汇数据。

**WebSocket 端点**: `wss://` (通过 REST API 获取 `streamerSocketUrl`)

## 工作流程

### 1. 获取 Streaming 配置

通过 REST API 获取 WebSocket URL 和认证信息：

```python
from schwab.streaming import StreamClient

client = easy_client(...)  # 已认证的 HTTP 客户端
stream_client = StreamClient(client, account_id=1234567890)
```

### 2. 登录

```python
await stream_client.login()
```

> 与 HTTP Client 不同，Streaming Client 认证的是整个连接而非单个请求。

### 3. 注册消息处理器

```python
def handler(message):
    print(json.dumps(message, indent=4))

stream_client.add_level_one_equity_handler(handler)
```

> ⚠️ **重要**：必须在订阅之前注册处理器，因为很多流在订阅成功后会立即开始发送数据，没有处理器的消息会被丢弃。

### 4. 订阅数据流

```python
await stream_client.level_one_equity_subs(['AAPL', 'GOOG'])
```

### 5. 处理消息

```python
while True:
    await stream_client.handle_message()
```

### 6. 登出

```python
await stream_client.logout()
```

## 完整示例

```python
from schwab.auth import easy_client
from schwab.streaming import StreamClient
import asyncio
import json

client = easy_client(
    api_key='YOUR_API_KEY',
    app_secret='YOUR_APP_SECRET',
    callback_url='https://127.0.0.1',
    token_path='/path/to/token.json')

stream_client = StreamClient(client, account_id=1234567890)

async def read_stream():
    await stream_client.login()

    def print_message(message):
        print(json.dumps(message, indent=4))

    stream_client.add_nasdaq_book_handler(print_message)
    await stream_client.nasdaq_book_subs(['GOOG'])

    while True:
        await stream_client.handle_message()

asyncio.run(read_stream())
```

---

## 数据流类型

### 已确认可用的流

| 类型 | 说明 | 状态 |
|------|------|------|
| **OHLCV Charts** | K 线图表（股票和期货） | ✅ 确认可用 |
| **Level One Quotes** | 一级报价（股票、期权、期货、外汇） | ✅ 确认可用 |
| **Level Two Order Book** | 二级盘口（NYSE、NASDAQ、期权） | ✅ 确认可用 |
| **Screener** | 涨跌幅/成交量筛选器 | ✅ 确认可用 |
| **Account Activity** | 账户活动通知 | ✅ 确认可用 |

---

## 一、OHLCV Charts（K 线图表）

逐分钟的 OHLCV（开高低收量）数据。

### Equity Charts（股票 K 线）

```python
# 订阅
await stream_client.chart_equity_subs(['AAPL', 'MSFT'])

# 追加标的
await stream_client.chart_equity_add(['GOOG'])

# 取消订阅
await stream_client.chart_equity_unsubs(['AAPL'])

# 注册处理器
stream_client.add_chart_equity_handler(handler)
```

**ChartEquityFields（字段）**：

| 字段 | 说明 |
|------|------|
| `OPEN_PRICE` | 开盘价 |
| `HIGH_PRICE` | 最高价 |
| `LOW_PRICE` | 最低价 |
| `CLOSE_PRICE` | 收盘价 |
| `VOLUME` | 成交量 |
| `SEQUENCE` | 序列号 |
| `CHART_TIME_MILLIS` | K 线时间（毫秒） |
| `CHART_DAY` | K 线日期 |

### Futures Charts（期货 K 线）

```python
await stream_client.chart_futures_subs(['/ES', '/NQ'])
await stream_client.chart_futures_add(['/CL'])
await stream_client.chart_futures_unsubs(['/ES'])
stream_client.add_chart_futures_handler(handler)
```

---

## 二、Level One Quotes（一级报价）

提供最新的 Bid/Ask/Volume 数据，实时更新。

### 股票报价

```python
await stream_client.level_one_equity_subs(['AAPL'], fields=[...])
stream_client.add_level_one_equity_handler(handler)
```

> 可选择性地指定 `fields` 参数来限制返回的字段子集。

### 期权报价

```python
await stream_client.level_one_option_subs(['AAPL_012026C150'])
stream_client.add_level_one_option_handler(handler)
```

> 可通过 `get_option_chain()` REST API 获取期权标的代码。

### 期货报价

```python
await stream_client.level_one_futures_subs(['/ES', '/NQ'])
stream_client.add_level_one_futures_handler(handler)
```

### 期货期权报价

```python
await stream_client.level_one_futures_options_subs([...])
stream_client.add_level_one_futures_options_handler(handler)
```

### 外汇报价

```python
await stream_client.level_one_forex_subs(['EUR/USD'])
stream_client.add_level_one_forex_handler(handler)
```

---

## 三、Level Two Order Book（二级盘口）

提供连续订单簿（Order Book）的快照数据。

### NYSE 盘口

```python
await stream_client.nyse_book_subs(['AAPL'])
stream_client.add_nyse_book_handler(handler)
```

### NASDAQ 盘口

```python
await stream_client.nasdaq_book_subs(['GOOG'])
stream_client.add_nasdaq_book_handler(handler)
```

### 期权盘口

```python
await stream_client.options_book_subs([...])
stream_client.add_options_book_handler(handler)
```

> ⚠️ `FOREX_BOOK`、`FUTURES_BOOK`、`FUTURES_OPTIONS_BOOK` 在文档中有提及但实际测试无法连接，可能尚未实现。

---

## 四、Screener（筛选器）

返回指定指数/交易所中涨跌幅/成交量/成交笔数前十的标的。

**标的格式**: `{PREFIX}_{SORTFIELD}_{FREQUENCY}`

**PREFIX**：
- 指数: `$COMPX`, `$DJI`, `$SPX.X`, `INDEX_ALL`
- 交易所: `NYSE`, `NASDAQ`, `OTCBB`, `EQUITY_ALL`
- 期权: `OPTION_PUT`, `OPTION_CALL`, `OPTION_ALL`

**SORTFIELD**: `VOLUME`, `TRADES`, `PERCENT_CHANGE_UP`, `PERCENT_CHANGE_DOWN`, `AVERAGE_PERCENT_VOLUME`

**FREQUENCY**: `0`（全天）, `1`, `5`, `10`, `30`, `60`（分钟）

### 股票筛选器

```python
await stream_client.screener_equity_subs(['NASDAQ_VOLUME_0'])
stream_client.add_screener_equity_handler(handler)
```

### 期权筛选器

```python
await stream_client.screener_option_subs(['OPTION_CALL_PERCENT_CHANGE_UP_0'])
stream_client.add_screener_option_handler(handler)
```

---

## 五、Account Activity（账户活动）

```python
await stream_client.account_activity_sub()
stream_client.add_account_activity_handler(handler)
```

---

## 数据字段重标注

Streaming API 返回的 JSON 使用数字键（如 `"1"`, `"2"` 等），schwab-py 会自动将其转换为可读的字段名：

**原始数据**：

```json
{
  "service": "CHART_EQUITY",
  "content": [{
    "key": "MSFT",
    "1": 779,
    "2": 421.65,
    "3": 421.79,
    "4": 421.65,
    "5": 421.755,
    "6": 26.0,
    "7": 1715903940000
  }]
}
```

**重标注后**：

```json
{
  "service": "CHART_EQUITY",
  "content": [{
    "key": "MSFT",
    "SEQUENCE": 779,
    "OPEN_PRICE": 421.65,
    "HIGH_PRICE": 421.79,
    "LOW_PRICE": 421.65,
    "CLOSE_PRICE": 421.755,
    "VOLUME": 26.0,
    "CHART_TIME_MILLIS": 1715903940000
  }]
}
```

## 相关文档

- [API 端点参考](./api-reference.md)
- [订单模板](./order-templates.md)
