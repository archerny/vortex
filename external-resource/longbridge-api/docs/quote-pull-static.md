获取标的基础信息
========

该接口用于获取标的的基础信息。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # Tesla 静态信息（名称、手数、股本等）
    longbridge static TSLA.US
    # 多只美股静态信息
    longbridge static AAPL.US NVDA.US
    # 美港股混合查询
    longbridge static TSLA.US 700.HK

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.static\_info](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.static_info) |
| Rust | [longbridge::quote::QuoteContext#static\_info](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.static_info) |
| Go  | [QuoteContext.StaticInfo](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.StaticInfo) |
| Node.js | [QuoteContext#staticInfo](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#staticinfo) |
| Java | [QuoteContext.getStaticInfo](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getStaticInfo(java.lang.String%5B%5D)) |
| C++ | [longbridge::quote::QuoteContext::static\_info](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#aa7d2e40f0e94848aa229a629343821e8) |

Info

[业务指令](./socket-biz-command.md)
：`10`

Request [​](./quote-pull-static.md#request)

------------------------------------------------------------------------------

### Parameters [​](./quote-pull-static.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string\[\] | 是   | 标的代码列表，使用 `ticker.region` 格式，例如：`[700.HK]`  <br>  <br>**校验规则：**  <br>每次请求支持传入的标的数量上限是 `500` 个 |

### Protobuf [​](./quote-pull-static.md#protobuf)

protobuf

    message MultiSecurityRequest {
      repeated string symbol = 1;
    }

### Request Example [​](./quote-pull-static.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.static_info(["700.HK", "AAPL.US", "TSLA.US", "NFLX.US"])
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.static_info(["700.HK", "AAPL.US", "TSLA.US", "NFLX.US"])
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
      const resp = await ctx.staticInfo(["700.HK", "AAPL.US", "TSLA.US", "NFLX.US"])
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
                SecurityStaticInfo[] resp = ctx.getStaticInfo(new String[] { "700.HK", "AAPL.US", "TSLA.US", "NFLX.US" }).get();
                for (SecurityStaticInfo obj : resp) {
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
        let resp = ctx.static_info(["700.HK", "AAPL.US", "TSLA.US", "NFLX.US"]).await?;
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
    
        std::vector<std::string> symbols = {"700.HK", "AAPL.US", "TSLA.US", "NFLX.US"};
        ctx.static_info(symbols, [](auto res) {
            if (!res) {
                std::cout << "failed: " << *res.status().message() << std::endl;
                return;
            }
            for (const auto& obj : *res) {
                std::cout << obj.symbol << " " << obj.name_cn << " " << obj.name_en << std::endl;
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
    	infos, err := qctx.StaticInfo(context.Background(), []string{"700.HK", "AAPL.US", "TSLA.US", "NFLX.US"})
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Printf("%+v\n", infos[0])
    }

Response [​](./quote-pull-static.md#response)

--------------------------------------------------------------------------------

### Response Properties [​](./quote-pull-static.md#response-properties)

| Name | Type | Description |
| --- | --- | --- |
| secu\_static\_info | object\[\] | 标的基础数据列表 |
| ∟ symbol | string | 标的代码 |
| ∟ name\_cn | string | 中文简体标的名称 |
| ∟ name\_en | string | 英文标的名称 |
| ∟ name\_hk | string | 中文繁体标的名称 |
| ∟ exchange | string | 标的所属交易所 |
| ∟ currency | string | 交易币种  <br>  <br>**可选值：**  <br>`CNY`  <br>`USD`  <br>`SGD`  <br>`HKD` |
| ∟ lot\_size | int32 | 每手股数 |
| ∟ total\_shares | int64 | 总股本 |
| ∟ circulating\_shares | int64 | 流通股本 |
| ∟ hk\_shares | int64 | 港股股本 (仅港股) |
| ∟ eps | string | 每股盈利 |
| ∟ eps\_ttm | string | 每股盈利 (TTM) |
| ∟ bps | string | 每股净资产 |
| ∟ dividend\_yield | string | 股息  |
| ∟ stock\_derivatives | int32\[\] | 如果标的是正股，可提供的衍生品行情类型  <br>  <br>**可选值：**  <br>`1` - 期权  <br>`2` - 轮证 |
| ∟ board | string | 标的所属板块，详见 [Board](./quote-objects.md#board-%E6%A0%87%E7%9A%84%E6%9D%BF%E5%9D%97) |

### Protobuf [​](./quote-pull-static.md#protobuf-1)

protobuf

    message SecurityStaticInfoResponse {
      repeated StaticInfo secu_static_info = 1;
    }
    
    message StaticInfo {
      string symbol = 1;
      string name_cn = 2;
      string name_en = 3;
      string name_hk = 4;
      string listing_date = 5;
      string exchange = 6;
      string currency = 7;
      int32 lot_size = 8;
      int64 total_shares = 9;
      int64 circulating_shares = 10;
      int64 hk_shares = 11;
      string eps = 12;
      string eps_ttm = 13;
      string bps = 14;
      string dividend_yield = 15;
      repeated int32 stock_derivatives = 16;
      string board = 17;
    }

### Response JSON Example [​](./quote-pull-static.md#response-json-example)

json

    {
      "secu_static_info": [\
        {\
          "symbol": "700.HK",\
          "name_cn": "腾讯控股",\
          "name_en": "TENCENT",\
          "name_hk": "騰訊控股",\
          "exchange": "SEHK",\
          "currency": "HKD",\
          "lot_size": 100,\
          "total_shares": 9612464038,\
          "circulating_shares": 9612464038,\
          "hk_shares": 9612464038,\
          "eps": "28.4394",\
          "eps_ttm": "28.4394",\
          "bps": "103.40413",\
          "dividend_yield": "1.6",\
          "stock_derivatives": [2],\
          "board": "HKEquity"\
        },\
        {\
          "symbol": "AAPL.US",\
          "name_cn": "苹果",\
          "name_en": "Apple Inc.",\
          "exchange": "NASD",\
          "currency": "USD",\
          "lot_size": 1,\
          "total_shares": 1631944100,\
          "circulating_shares": 16302661350,\
          "eps": "5.669",\
          "eps_ttm": "6.0771",\
          "bps": "4.40197",\
          "dividend_yield": "0.85",\
          "stock_derivatives": [1],\
          "board": "USMain"\
        }\
      ]
    }

错误码 [​](./quote-pull-static.md#%E9%94%99%E8%AF%AF%E7%A0%81)

----------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |
| 7   | 301607 | 接口限制 | 请求的标的数量超限，请减少单次请求标的数量 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/static.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/static.md)

最后更新于:

Pager

[上一页命名词典](./quote-objects.md)

[下一页获取标的实时行情](./quote-pull-quote.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
