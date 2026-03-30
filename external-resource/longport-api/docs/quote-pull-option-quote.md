获取期权实时行情
========

该接口用于获取美股期权标的的实时行情，包括期权的特有数据。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # AAPL 看涨期权 $250 行权价 2026-04-17 到期
    longbridge option-quote AAPL260417C250000.US
    # TSLA 看跌期权 $350 行权价 2026-04-18 到期
    longbridge option-quote TSLA260418P350000.US

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.option\_quote](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.option_quote) |
| Rust | [longbridge::quote::QuoteContext#option\_quote](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.option_quote) |
| Go  | [QuoteContext.OptionQuote](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.OptionQuote) |
| Node.js | [QuoteContext#optionQuote](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#optionquote) |
| Java | [QuoteContext.getOptionQuote](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getOptionQuote(java.lang.String%5B%5D)) |
| C++ | [longbridge::quote::QuoteContext::option\_quote](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a1193a0983647090ef2004e39cad5dae8) |

Info

[业务指令](./socket-biz-command.md)
：`12`

Request [​](./quote-pull-option-quote.md#request)

------------------------------------------------------------------------------------

### Parameters [​](./quote-pull-option-quote.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string\[\] | 是   | 标的代码列表，通过[期权链接口](./quote-pull-optionchain-date-strike.md)<br> 获取期权标的的 symbol，例如：`[BABA230120C160000.US]`  <br>  <br>**校验规则：**  <br>每次请求支持传入的标的数量上限是 `500` 个 |

### Protobuf [​](./quote-pull-option-quote.md#protobuf)

protobuf

    message MultiSecurityRequest {
      repeated string symbol = 1;
    }

### Request Example [​](./quote-pull-option-quote.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.option_quote(["AAPL230317P160000.US"])
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.option_quote(["AAPL230317P160000.US"])
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => {
        console.log("Open this URL to authorize: " + url)
      })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.optionQuote(["AAPL230317P160000.US"])
      for (const obj of resp) {
        console.log(obj.toString())
      }
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.quote.*;
    
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id")
                    .build(url -> System.out.println("Open to authorize: " + url))
                    .get();
                 Config config = Config.fromOAuth(oauth);
                 QuoteContext ctx = QuoteContext.create(config)) {
                OptionQuote[] resp = ctx.getOptionQuote(new String[] { "AAPL230317P160000.US" }).get();
                for (OptionQuote obj : resp) {
                    System.out.println(obj);
                }
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::QuoteContext, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id")
            .build(|url| println!("Open this URL to authorize: {url}"))
            .await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let resp = ctx.option_quote(["AAPL230317P160000.US"]).await?;
        println!("{:?}", resp);
        Ok(())
    }

cpp

    #include <iostream>
    #include <longbridge.hpp>
    
    #ifdef WIN32
    #include <windows.h>
    #endif
    
    using namespace longbridge;
    using namespace longbridge::quote;
    
    static void
    run(const OAuth& oauth)
    {
        Config config = Config::from_oauth(oauth);
        QuoteContext ctx = QuoteContext::create(config);
    
        std::vector<std::string> symbols = {"AAPL230317C160000.US"};
        ctx.option_quote(symbols, [](auto res) {
            if (!res) {
                std::cout << "failed: " << *res.status().message() << std::endl;
                return;
            }
            for (const auto& q : *res) {
                std::cout << q.symbol << " " << (double)q.last_done << std::endl;
            }
        });
    }
    
    int main(int argc, char const* argv[]) {
    #ifdef WIN32
        SetConsoleOutputCP(CP_UTF8);
    #endif
    
        const std::string client_id = "your-client-id";
        OAuthBuilder(client_id).build(
        [](const std::string& url) {
            std::cout << "Open this URL to authorize: " << url << std::endl;
        },
        [](auto res) {
            if (!res) {
                std::cout << "authorization failed: " << *res.status().message() << std::endl;
                return;
            }
            run(*res);
        });
    
        std::cin.get();
        return 0;
    }

go

    package main
    
    import (
    	"context"
    	"fmt"
    	"log"
    
    	"github.com/longbridge/openapi-go/config"
    	"github.com/longbridge/openapi-go/oauth"
    	"github.com/longbridge/openapi-go/quote"
    )
    
    func main() {
    	o := oauth.New("your-client-id").
    		OnOpenURL(func(url string) { fmt.Println("Open this URL to authorize:", url) })
    	if err := o.Build(context.Background()); err != nil {
    		log.Fatal(err)
    	}
    	conf, err := config.New(config.WithOAuthClient(o))
    	if err != nil {
    		log.Fatal(err)
    	}
    	qctx, err := quote.NewFromCfg(conf)
    	if err != nil {
    		log.Fatal(err)
    	}
    	defer qctx.Close()
    	quotes, err := qctx.OptionQuote(context.Background(), []string{"AAPL230317C160000.US"})
    	if err != nil {
    		log.Fatal(err)
    	}
    	for _, q := range quotes {
    		fmt.Println(q.Symbol, q.LastDone)
    	}
    }

Response [​](./quote-pull-option-quote.md#response)

--------------------------------------------------------------------------------------

### Response Properties [​](./quote-pull-option-quote.md#response-properties)

| Name | Type | Description |
| --- | --- | --- |
| secu\_quote | object\[\] | 期权标的行情数据列表 |
| ∟ symbol | string | 标的代码 |
| ∟ last\_done | string | 最新价 |
| ∟ prev\_close | string | 昨收价 |
| ∟ open | string | 开盘价 |
| ∟ high | string | 最高价 |
| ∟ low | string | 最低价 |
| ∟ timestamp | int64 | 最新成交的时间戳 |
| ∟ volume | int64 | 成交量 |
| ∟ turnover | string | 成交额 |
| ∟ trade\_status | int32 | 标的交易状态，详见 [TradeStatus](./quote-objects.md#tradestatus-%E4%BA%A4%E6%98%93%E7%8A%B6%E6%80%81) |
| ∟ option\_extend | object | 期权扩展行情 |
| ∟∟ implied\_volatility | string | 隐含波动率 |
| ∟∟ open\_interest | int64 | 未平仓数 |
| ∟∟ expiry\_date | string | 到期日，使用：`YYMMDD` 格式 |
| ∟∟ strike\_price | string | 行权价 |
| ∟∟ contract\_multiplier | string | 合约乘数 |
| ∟∟ contract\_type | string | 期权类型  <br>  <br>**可选值：**  <br>`A` - 美式  <br>`U` - 欧式 |
| ∟∟ contract\_size | string | 合约规模 |
| ∟∟ direction | string | 方向  <br>  <br>**可选值：**  <br>`P` - put  <br>`C` - call |
| ∟∟ historical\_volatility | string | 对应正股的历史波动率 |
| ∟∟ underlying\_symbol | string | 对应的正股标的代码 |

### Protobuf [​](./quote-pull-option-quote.md#protobuf-1)

protobuf

    message OptionQuoteResponse {
      repeated OptionQuote secu_quote = 1;
    }
    
    message OptionQuote {
      string symbol = 1;
      string last_done = 2;
      string prev_close = 3;
      string open = 4;
      string high = 5;
      string low = 6;
      int64 timestamp = 7;
      int64 volume = 8;
      string turnover = 9;
      TradeStatus trade_status = 10;
      OptionExtend option_extend = 11;
    }
    
    message OptionExtend {
      string implied_volatility = 1;
      int64 open_interest = 2;
      string expiry_date = 3;
      string strike_price = 4;
      string contract_multiplier = 5;
      string contract_type = 6;
      string contract_size = 7;
      string direction = 8;
      string historical_volatility = 9;
      string underlying_symbol = 10;
    }

### Response JSON Example [​](./quote-pull-option-quote.md#response-json-example)

json

    {
      "secu_quote": [\
        {\
          "symbol": "AAPL220429P162500.US",\
          "last_done": "7.78",\
          "prev_close": "4.13",\
          "open": "4.43",\
          "high": "7.80",\
          "low": "4.43",\
          "timestamp": 1651003200,\
          "volume": 3082,\
          "turnover": "1813434.00",\
          "option_extend": {\
            "implied_volatility": "0.592",\
            "open_interest": 11463,\
            "expiry_date": "20220429",\
            "strike_price": "162.50",\
            "contract_multiplier": "100",\
            "contract_type": "A",\
            "contract_size": "100",\
            "direction": "P",\
            "historical_volatility": "0.2750",\
            "underlying_symbol": "AAPL.US"\
          }\
        },\
        {\
          "symbol": "AAPL220429C150000.US",\
          "last_done": "9.25",\
          "prev_close": "13.87",\
          "open": "13.80",\
          "high": "13.80",\
          "low": "9.15",\
          "timestamp": 1651003200,\
          "volume": 413,\
          "turnover": "436835.00",\
          "option_extend": {\
            "implied_volatility": "0.702",\
            "open_interest": 800,\
            "expiry_date": "20220429",\
            "strike_price": "150.00",\
            "contract_multiplier": "100",\
            "contract_type": "A",\
            "contract_size": "100",\
            "direction": "C",\
            "historical_volatility": "0.2750",\
            "underlying_symbol": "AAPL.US"\
          }\
        }\
      ]
    }

错误码 [​](./quote-pull-option-quote.md#%E9%94%99%E8%AF%AF%E7%A0%81)

----------------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |
| 7   | 301607 | 接口限制 | 请求的标的数量超限，请减少单次请求标的数量 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/option-quote.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/option-quote.md)

最后更新于:

Pager

[上一页获取标的实时行情](./quote-pull-quote.md)

[下一页获取轮证实时行情](./quote-pull-warrant-quote.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
