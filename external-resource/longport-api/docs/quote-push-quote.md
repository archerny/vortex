实时价格推送
======

已订阅标的的实时价格推送，推送的数据结构中，只有有变化的字段才会填充数据。

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.set\_on\_quote](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.set_on_quote) |
| Rust | [longbridge::quote::QuoteContext#set\_on\_quote](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.set_on_quote) |
| Go  | [QuoteContext.OnQuote](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.OnQuote) |
| Node.js | [QuoteContext#setOnQuote](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#setonquote) |
| Java | [QuoteContext.setOnQuote](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#setOnQuote(com.longbridge.quote.QuoteHandler)) |
| C++ | [longbridge::quote::QuoteContext::set\_on\_quote](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#af4c98ea970f2632e03e63d188b914ff2) |

Info

[业务指令](./socket-protocol-push.md)
：`101`

数据格式 [​](./quote-push-quote.md#%E6%95%B0%E6%8D%AE%E6%A0%BC%E5%BC%8F)

-------------------------------------------------------------------------------------------------------

### Properties [​](./quote-push-quote.md#properties)

| Name | Type | Description |
| --- | --- | --- |
| symbol | string | 标的代码，例如：`AAPL.US` |
| sequence | int64 | 序列号 |
| last\_done | string | 最新价 |
| open | string | 开盘价 |
| high | string | 最高价 |
| low | string | 最低价 |
| timestamp | int64 | 最新成交的时间戳 |
| volume | int64 | 成交量 |
| turnover | string | 成交额 |
| trade\_status | int32 | 交易状态，详见 [TradeStatus](./quote-objects.md#tradestatus-%E4%BA%A4%E6%98%93%E7%8A%B6%E6%80%81) |
| trade\_session | int32 | 交易时段，详见 [TradeSession](./quote-objects.md#tradesession-%E4%BA%A4%E6%98%93%E6%97%B6%E6%AE%B5) |
| current\_volume | int32 | 两次推送之间增加的成交量 |
| current\_turnover | string | 两次推送之间增加的成交额 |
| tag | int32 | 价格数据标签  <br>  <br>**可选值：**  <br>`0` - 实时行情  <br>`1` - 收盘后的修正数据 |

### Protobuf [​](./quote-push-quote.md#protobuf)

protobuf

    message PushQuote {
      string symbol = 1;
      int64 sequence = 2;
      string last_done = 3;
      string open = 4;
      string high = 5;
      string low = 6;
      int64 timestamp = 7;
      int64 volume = 8;
      string turnover = 9;
      TradeStatus trade_status = 10;
      TradeSession trade_session = 11;
    }

### Example [​](./quote-push-quote.md#example)

python

    from time import sleep
    from longbridge.openapi import QuoteContext, Config, SubType, PushQuote, OAuthBuilder
    
    def on_quote(symbol: str, event: PushQuote):
        print(symbol, event)
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    ctx.set_on_quote(on_quote)
    
    ctx.subscribe(["700.HK", "AAPL.US"], [SubType.Quote])
    sleep(30)

### JSON Example [​](./quote-push-quote.md#json-example)

json

    {
      "symbol": "AAPL.US",
      "sequence": 160808750000000,
      "last_done": "156.570",
      "open": "155.910",
      "high": "159.790",
      "low": "155.380",
      "timestamp": 1651089600,
      "volume": 88063191,
      "turnover": "13865092584.000",
      "trade_status": 0,
      "trade_session": 0,
      "current_volume": 111234,
      "current_turnover": "23234343454.000",
      "tag": 0
    }

[LLMs Text](https://open.longbridge.com/docs/quote/push/quote.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/push/quote.md)

最后更新于:

Pager

[上一页获取已订阅标的行情](./quote-subscribe-subscription.md)

[下一页实时盘口推送](./quote-push-depth.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
