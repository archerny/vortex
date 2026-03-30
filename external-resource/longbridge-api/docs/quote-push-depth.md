实时盘口推送
======

已订阅标的的实时盘口数据推送。

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.set\_on\_depth](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.set_on_depth) |
| Rust | [longbridge::quote::QuoteContext#set\_on\_depth](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.set_on_depth) |
| Go  | [QuoteContext.OnDepth](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.OnDepth) |
| Node.js | [QuoteContext#setOnDepth](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#setondepth) |
| Java | [QuoteContext.setOnDepth](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#setOnDepth(com.longbridge.quote.DepthHandler)) |
| C++ | [longbridge::quote::QuoteContext::set\_on\_depth](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a96e2a7f2f510c3a5dcfb5ac24d656fb7) |

Info

[业务指令](./socket-protocol-push.md)
：`102`

数据格式 [​](./quote-push-depth.md#%E6%95%B0%E6%8D%AE%E6%A0%BC%E5%BC%8F)

-------------------------------------------------------------------------------------------------------

### Properties [​](./quote-push-depth.md#properties)

| Name | Type | Description |
| --- | --- | --- |
| symbol | string | 标的代码，例如：`AAPL.US` |
| sequence | int64 | 序列号 |
| ask | object\[\] | 卖盘  |
| ∟ position | int32 | 档位  |
| ∟ price | string | 价格  |
| ∟ volume | int64 | 挂单量 |
| ∟ order\_num | int64 | 订单数量 |
| bid | object\[\] | 买盘  |
| ∟ position | int32 | 档位  |
| ∟ price | string | 价格  |
| ∟ volume | int64 | 挂单量 |
| ∟ order\_num | int64 | 订单数量 |

### Protobuf [​](./quote-push-depth.md#protobuf)

protobuf

    message PushDepth {
      string symbol = 1;
      int64 sequence = 2;
      repeated Depth ask = 3;
      repeated Depth bid = 4;
    }
    
    message Depth {
      int32 position = 1;
      string price = 2;
      int64 volume = 3;
      int64 order_num = 4;
    }

### Example [​](./quote-push-depth.md#example)

python

    from time import sleep
    from longbridge.openapi import QuoteContext, Config, SubType, PushDepth, OAuthBuilder
    
    def on_depth(symbol: str, event: PushDepth):
        print(symbol, event)
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    ctx.set_on_depth(on_depth)
    
    ctx.subscribe(["700.HK", "AAPL.US"], [SubType.Depth])
    sleep(30)

### JSON Example [​](./quote-push-depth.md#json-example)

json

    {
      "symbol": "700.HK",
      "sequence": 160808750000000,
      "ask": [\
        {\
          "position": 1,\
          "price": "335.000",\
          "volume": 500,\
          "order_num": 1\
        },\
        {\
          "position": 2,\
          "price": "335.200",\
          "volume": 400,\
          "order_num": 1\
        },\
        {\
          "position": 3,\
          "price": "335.400",\
          "volume": 500,\
          "order_num": 2\
        },\
        {\
          "position": 4,\
          "price": "335.600",\
          "volume": 1200,\
          "order_num": 3\
        },\
        {\
          "position": 5,\
          "price": "335.800",\
          "volume": 14000,\
          "order_num": 8\
        }\
      ],
      "bid": [\
        {\
          "position": 1,\
          "price": "334.800",\
          "volume": 69400,\
          "order_num": 13\
        },\
        {\
          "position": 2,\
          "price": "334.600",\
          "volume": 266600,\
          "order_num": 27\
        },\
        {\
          "position": 3,\
          "price": "334.400",\
          "volume": 61300,\
          "order_num": 29\
        },\
        {\
          "position": 4,\
          "price": "334.200",\
          "volume": 125900,\
          "order_num": 31\
        },\
        {\
          "position": 5,\
          "price": "334.000",\
          "volume": 194600,\
          "order_num": 94\
        }\
      ]
    }

[LLMs Text](https://open.longbridge.com/docs/quote/push/depth.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/push/depth.md)

最后更新于:

Pager

[上一页实时价格推送](./quote-push-quote.md)

[下一页实时经纪队列推送](./quote-push-broker.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
