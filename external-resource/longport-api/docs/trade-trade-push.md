交易推送
====

客户端可以通过交易长连接网关获取到交易和资产的变更通知。

Example [​](./trade-trade-push.md#example)

-----------------------------------------------------------------------------

python

    from time import sleep
    from decimal import Decimal
    from longbridge.openapi import TradeContext, Config, OrderSide, OrderType, TimeInForceType, PushOrderChanged, TopicType, OAuthBuilder
    
    def on_order_changed(event: PushOrderChanged):
        print(event)
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = TradeContext(config)
    ctx.set_on_order_changed(on_order_changed)
    ctx.subscribe([TopicType.Private])
    
    resp = ctx.submit_order(
        side=OrderSide.Buy,
        symbol="700.HK",
        order_type=OrderType.LO,
        submitted_price=Decimal(50),
        submitted_quantity=Decimal(200),
        time_in_force=TimeInForceType.Day,
        remark="Hello from Python SDK",
    )
    print(resp)
    sleep(5)  # waiting for push event
    
    # Finally, unsubscribe
    ctx.unsubscribe([TopicType.Private])

订阅 [​](./trade-trade-push.md#%E8%AE%A2%E9%98%85)

-----------------------------------------------------------------------------------

{false}
-------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.subscribe](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.subscribe) |
| Rust | [longbridge::trade::TradeContext#subscribe](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.subscribe) |
| Go  | [TradeContext.Subscribe](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.Subscribe) |
| Node.js | [TradeContext#subscribe](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#subscribe) |
| Java | [TradeContext.subscribe](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#subscribe(java.lang.String%5B%5D%2Cint)) |
| C++ | [longbridge::trade::TradeContext::subscribe](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#a6e0f5a81c89a4a351d8289a8f81d5e26) |

Info

指令：`16`

我们可以通过 `subscribe` 方法订阅交易推送，订阅成功后，服务端会将相应的推送消息推送给客户端，SDK 的 `set_on_order_changed` 可以设置推送消息的回调函数，当收到交易推送消息时，会调用该回调函数。

Protobuf 定义如下：

protobuf

    // Sub is Sub command content, command is 16
    message Sub {
      repeated string topics = 1;
    }
    
    // SubResponse is response of Sub Request
    message SubResponse {
      message Fail {
        string topic = 1;
        string reason = 2;
      }
      repeated string success = 1; // 订阅成功
      repeated Fail fail = 2; // 订阅失败
      repeated string current = 3;  // 当前订阅
    }

目前支持的 topic：

*   private - 交易和资产类的私有通知

取消订阅 [​](./trade-trade-push.md#%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85)

-------------------------------------------------------------------------------------------------------

取消订阅用于取消订阅信息，如前面 `subscribe` 订阅成功后，可以通过 `unsubscribe` 函数来取消订阅。

{false}
-------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.unsubscribe](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.unsubscribe) |
| Rust | [longbridge::trade::TradeContext#unsubscribe](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.unsubscribe) |
| Go  | [TradeContext.Unsubscribe](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.Unsubscribe) |
| Node.js | [TradeContext#unsubscribe](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#unsubscribe) |
| Java | [TradeContext.unsubscribe](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#unsubscribe(java.lang.String%5B%5D%2Cint)) |
| C++ | [longbridge::trade::TradeContext::unsubscribe](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#a35158082ee04f2e865aa8bfce39576b9) |

Info

指令：`17`

Protobuf 定义如下：

protobuf

    // Unsub is Unsub command content, command is 17
    message Unsub {
      repeated string topics = 1;
    }
    
    // UnsubResponse is response of Unsub request
    message UnsubResponse {
      repeated string current = 3; // 当前订阅
    }

注册通知推送 [​](./trade-trade-push.md#%E6%B3%A8%E5%86%8C%E9%80%9A%E7%9F%A5%E6%8E%A8%E9%80%81)

---------------------------------------------------------------------------------------------------------------------------

我们可以通过 `set_on_order_changed` 方法（Go 里面为 `OnTrade`）设置推送消息的回调函数，当收到交易推送消息时，会调用该回调函数。

{false}
-------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.set\_on\_order\_changed](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.set_on_order_changed) |
| Rust | [longbridge::trade::TradeContext#set\_on\_order\_changed](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.set_on_order_changed) |
| Go  | [TradeContext.OnTrade](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.OnTrade) |
| Node.js | [TradeContext#setOnOrderChanged](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#setonorderchanged) |
| Java | [TradeContext.getSetOnOrderChanged](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#getSetOnOrderChanged) |
| C++ | [longbridge::trade::TradeContext::set\_on\_order\_changed](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html) |

Info

指令：`18`

Protobuf 定义如下：

protobuf

    // Dispatch type
    enum DispatchType {
      DISPATCH_UNDEFINED = 0;
      DISPATCH_DIRECT = 1;
      DISPATCH_BROADCAST = 2;
    }
    
    enum ContentType {
      CONTENT_UNDEFINED = 0;
      CONTENT_JSON = 1;
      CONTENT_PROTO = 2;
    }
    
    // Notification is push message, command is 18
    message Notification {
      string topic = 1;
      ContentType content_type = 2;
      DispatchType dispatch_type = 3;
      bytes data = 4;
    }

[LLMs Text](https://open.longbridge.com/docs/trade/trade-push.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/trade-push.md)

最后更新于:

Pager

[上一页撤销订单](./trade-order-withdraw.md)

[下一页获取账户资金](./trade-asset-account.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
