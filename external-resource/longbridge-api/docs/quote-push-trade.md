实时成交明细推送
========

已订阅的标的的实时逐笔成交明细推送。

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.set\_on\_trades](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.set_on_trades) |
| Rust | [longbridge::quote::QuoteContext#set\_on\_trades](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.set_on_trades) |
| Go  | [QuoteContext.OnTrade](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.OnTrade) |
| Node.js | [QuoteContext#setOnTrades](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#setontrades) |
| Java | [QuoteContext.setOnTrades](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#setOnTrades(com.longbridge.quote.TradesHandler)) |
| C++ | [longbridge::quote::QuoteContext::set\_on\_trades](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a4217749d54ebce011e42a925bef61de8) |

Info

[业务指令](./socket-protocol-push.md)
：`104`

数据格式 [​](./quote-push-trade.md#%E6%95%B0%E6%8D%AE%E6%A0%BC%E5%BC%8F)

-------------------------------------------------------------------------------------------------------

### Properties [​](./quote-push-trade.md#properties)

| Name | Type | Description |
| --- | --- | --- |
| symbol | string | 标的代码，例如：`AAPL.US` |
| sequence | int64 | 序列号 |
| trades | object\[\] | 逐笔明细数据 |
| ∟ price | string | 价格  |
| ∟ volume | int64 | 成交量 |
| ∟ timestamp | int64 | 成交时间 |
| ∟ trade\_type | string | [交易类型说明](./quote-push-trade.md#%E4%BA%A4%E6%98%93%E7%B1%BB%E5%9E%8B) |
| ∟ direction | int32 | 交易方向  <br>  <br>**可选值：**  <br>`0` - neutral  <br>`1` - down  <br>`2` - up |
| ∟ trade\_session | int32 | 交易时段，详见 [TradeSession](./quote-objects.md#tradesession-%E4%BA%A4%E6%98%93%E6%97%B6%E6%AE%B5) |

#### 交易类型 [​](./quote-push-trade.md#%E4%BA%A4%E6%98%93%E7%B1%BB%E5%9E%8B)

港股

*   `*` - 场外交易
*   `D` - 碎股交易
*   `M` - 非自动对盘
*   `P` - 开市前成交盘
*   `U` - 竞价交易
*   `X` - 同一券商非自动对盘
*   `Y` - 同一券商自动对盘
*   \- 自动对盘

美股

*   \- 自动对盘
*   `A` - 收购
*   `B` - 批量交易
*   `D` - 分配
*   `F` - 跨市扫盘单
*   `G` - 批量卖出
*   `H` - 离价交易
*   `I` - 碎股交易
*   `K` - 第 155 条交易（纽交所规则）
*   `M` - 交易所收盘价
*   `P` - 前参考价
*   `Q` - 交易所开盘价
*   `S` - 拆单交易
*   `V` - 附属交易
*   `W` - 平均价成交
*   `X` - 跨市场交易
*   `1` - 停售股票（常规交易）

### Protobuf [​](./quote-push-trade.md#protobuf)

protobuf

    message PushTrade {
      string symbol = 1;
      int64 sequence = 2;
      repeated Trade trade = 3;
    }
    
    message Trade {
      string price = 1;
      int64 volume = 2;
      int64 timestamp = 3;
      string trade_type = 4;
      int32 direction = 5;
      TradeSession trade_session = 6;
    }

### Example [​](./quote-push-trade.md#example)

python

    from time import sleep
    from longbridge.openapi import QuoteContext, Config, SubType, PushTrades, OAuthBuilder
    
    def on_trades(symbol: str, event: PushTrades):
        print(symbol, event)
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    ctx.set_on_trades(on_trades)
    
    ctx.subscribe(["700.HK", "AAPL.US"], [SubType.Trade])
    sleep(30)

### JSON Example [​](./quote-push-trade.md#json-example)

json

    {
      "symbol": "700.HK",
      "sequence": 160808750000000,
      "trades": [\
        {\
          "price": "158.760",\
          "volume": 1,\
          "timestamp": 1651103979,\
          "trade_type": "I",\
          "direction": 0,\
          "trade_session": 2\
        },\
        {\
          "price": "158.745",\
          "volume": 1,\
          "timestamp": 1651103985,\
          "trade_type": "I",\
          "direction": 0,\
          "trade_session": 2\
        },\
        {\
          "price": "158.800",\
          "volume": 1,\
          "timestamp": 1651103995,\
          "trade_type": "I",\
          "direction": 0,\
          "trade_session": 2\
        }\
      ]
    }

[LLMs Text](https://open.longbridge.com/docs/quote/push/trade.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/push/trade.md)

最后更新于:

Pager

[上一页实时经纪队列推送](./quote-push-broker.md)

[下一页创建自选股分组](./quote-individual-watchlist_create_group.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
