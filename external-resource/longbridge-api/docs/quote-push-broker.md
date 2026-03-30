实时经纪队列推送
========

已订阅标的的实时经纪队列数据推送。

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.set\_on\_brokers](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.set_on_brokers) |
| Rust | [longbridge::quote::QuoteContext#set\_on\_brokers](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.set_on_brokers) |
| Go  | [QuoteContext.OnBrokers](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.OnBrokers) |
| Node.js | [QuoteContext#setOnBrokers](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#setonbrokers) |
| Java | [QuoteContext.setOnBrokers](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#setOnBrokers(com.longbridge.quote.BrokersHandler)) |
| C++ | [longbridge::quote::QuoteContext::set\_on\_brokers](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#af647e715a4ed7ff5d36b51237ff1dbcf) |

Info

[业务指令](./socket-protocol-push.md)
：`103`

数据格式 [​](./quote-push-broker.md#%E6%95%B0%E6%8D%AE%E6%A0%BC%E5%BC%8F)

--------------------------------------------------------------------------------------------------------

### Properties [​](./quote-push-broker.md#properties)

| Name | Type | Description |
| --- | --- | --- |
| symbol | string | 标的代码，例如：`AAPL.US` |
| sequence | int64 | 序列号 |
| ask\_brokers | object\[\] | 卖盘经纪队列 |
| ∟ position | int32 | 档位  |
| ∟ broker\_ids | int32\[\] | [券商席位 Id](./quote-pull-broker-ids.md) |
| bid\_brokers | object\[\] | 买盘经纪队列 |
| ∟ position | int32 | 档位  |
| ∟ broker\_ids | int32\[\] | [券商席位 Id](./quote-pull-broker-ids.md) |

### Protobuf [​](./quote-push-broker.md#protobuf)

protobuf

    message PushBrokers {
      string symbol = 1;
      int64 sequence = 2;
      repeated Brokers ask_brokers = 3;
      repeated Brokers bid_brokers = 4;
    }
    
    message Brokers {
      int32 position = 1;
      repeated int32 broker_ids = 2;
    }

### Example [​](./quote-push-broker.md#example)

python

    from time import sleep
    from longbridge.openapi import QuoteContext, Config, SubType, PushBrokers, OAuthBuilder
    
    def on_brokers(symbol: str, event: PushBrokers):
        print(symbol, event)
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    ctx.set_on_brokers(on_brokers)
    
    ctx.subscribe(["700.HK", "AAPL.US"], [SubType.Brokers])
    sleep(30)

### JSON Example [​](./quote-push-broker.md#json-example)

json

    {
      "symbol": "700.HK",
      "sequence": 160808750000000,
      "ask_brokers": [\
        {\
          "position": 1,\
          "broker_ids": [7358, 9057, 9028, 7364]\
        },\
        {\
          "position": 2,\
          "broker_ids": [6968, 3448, 3348, 1049, 4973, 6997, 3448, 5465, 6997]\
        }\
      ],
      "bid_brokers": [\
        {\
          "position": 1,\
          "broker_ids": [6996, 5465, 8026, 8304, 4978]\
        },\
        {\
          "position": 2,\
          "broker_ids": [7358, 9057, 9028, 7364]\
        }\
      ]
    }

[LLMs Text](https://open.longbridge.com/docs/quote/push/broker.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/push/broker.md)

最后更新于:

Pager

[上一页实时盘口推送](./quote-push-depth.md)

[下一页实时成交明细推送](./quote-push-trade.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
