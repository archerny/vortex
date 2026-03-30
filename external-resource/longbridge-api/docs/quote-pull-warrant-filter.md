获取轮证筛选列表
========

该接口用于获取轮证行情列表数据，支持按不同字段排序和筛选轮证。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 腾讯相关权证列表
    longbridge warrant-list 700.HK
    # 阿里巴巴相关权证列表
    longbridge warrant-list 9988.HK
    # 京东相关权证列表
    longbridge warrant-list 9618.HK

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.warrant\_list](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.warrant_list) |
| Rust | [longbridge::quote::QuoteContext#warrant\_list](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.warrant_list) |
| Go  | [QuoteContext.WarrantList](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.WarrantList) |
| Node.js | [QuoteContext#warrantList](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#warrantlist) |
| Java | [QuoteContext.queryWarrantList](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#queryWarrantList(com.longbridge.quote.QueryWarrantOptions)) |
| C++ | [longbridge::quote::QuoteContext::warrant\_list](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#aa10434c7eac18b124b763424c0e22f40) |

Info

[业务指令](./socket-biz-command.md)
：`23`

Request [​](./quote-pull-warrant-filter.md#request)

--------------------------------------------------------------------------------------

### Parameters [​](./quote-pull-warrant-filter.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | 是   | 标的代码，使用 `ticker.region` 格式，例如：`700.HK` |
| filter\_config | object | 是   | 筛选条件 |
| ∟ sort\_by | int32 | 是   | 根据哪一项数据进行排序，例如：`0`，序号见响应数据 `OrderSequence` 字段。 |
| ∟ sort\_order | int32 | 是   | 升降顺序，例如：`1`  <br>  <br>**可选值：**  <br>`0` - 升序  <br>`1` - 降序 |
| ∟ sort\_offset | int32 | 是   | 分页的第一条数据偏移量，例如 `0` |
| ∟ sort\_count | int32 | 是   | 分页的每一页数量，例如 `20`, 填 `0` 时不分页 |
| ∟ type | int32\[\] | 否   | 筛选轮证类型 例如：`[0,1]`  <br>  <br>**可选值：**  <br>`0` - 认购  <br>`1` - 认沽  <br>`2` - 牛证  <br>`3` - 熊证  <br>`4` - 界内证 |
| ∟ issuer | int32\[\] | 否   | 筛选发行商，例如：`[12,14]`，[发行商 ID](./quote-pull-issuer.md)<br> 通过接口获取 |
| ∟ expiry\_date | int32\[\] | 否   | 筛选轮证过期时间，例如：`[1]`  <br>  <br>**可选值：**  <br>`1` - 低于 3 个月  <br>`2` - 3 - 6 个月  <br>`3` - 6 - 12 个月  <br>`4` - 大于 12 个月 |
| ∟ price\_type | int32\[\] | 否   | 筛选价内价外，例如：`[2]`  <br>  <br>**可选值：**  <br>`1` - 价内  <br>`2` - 价外 |
| ∟ status | int32\[\] | 否   | 筛选状态，例如：`[2]`  <br>  <br>**可选值：**  <br>`2`\- 终止交易  <br>`3` - 等待上市  <br>`4` - 正常 |
| language | int32 | 是   | 响应的语言，例如：`[1]`  <br>  <br>**可选值：**  <br>`0` - 简体  <br>`1` - English  <br>`2` - 繁体 |

### Protobuf [​](./quote-pull-warrant-filter.md#protobuf)

protobuf

    message WarrantFilterListRequest {
      string symbol = 1;
      FilterConfig filter_config = 2;
      int32 language = 3;
    }
    
    message FilterConfig {
      int32 sort_by = 1;
      int32 sort_order = 2;
      int32 sort_offset = 3;
      int32 sort_count = 4;
      repeated int32 type = 5;
      repeated int32 issuer = 6;
      repeated int32 expiry_date = 7;
      repeated int32 price_type = 8;
      repeated int32 status = 9;
    }

### Request Example [​](./quote-pull-warrant-filter.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, WarrantSortBy, SortOrderType, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.warrant_list("700.HK", WarrantSortBy.LastDone, SortOrderType.Ascending)
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, WarrantSortBy, SortOrderType, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.warrant_list("700.HK", WarrantSortBy.LastDone, SortOrderType.Ascending)
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth, WarrantSortBy, SortOrderType } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.warrantList("700.HK", WarrantSortBy.LastDone, SortOrderType.Ascending)
      console.log(resp)
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.quote.*;
    
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id").build(url -> System.out.println("Open to authorize: " + url)).get();
                 Config config = Config.fromOAuth(oauth);
                 QuoteContext ctx = QuoteContext.create(config)) {
                WarrantInfo[] resp = ctx.queryWarrantList(new QueryWarrantOptions("700.HK", WarrantSortBy.LastDone, SortOrderType.Ascending)).get();
                for (WarrantInfo w : resp) System.out.println(w);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::{QuoteContext, WarrantSortBy, SortOrderType}, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let resp = ctx.warrant_list("700.HK", WarrantSortBy::LastDone, SortOrderType::Ascending, None, None, None, None, None).await?;
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
    
        ctx.warrant_list("700.HK", WarrantSortBy::Turnover, SortOrderType::Descending, {}, {}, {}, {}, {}, [](auto res) {
            if (!res) { std::cout << "failed: " << *res.status().message() << std::endl; return; }
            std::cout << "warrants: " << res->size() << std::endl;
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
    	list, err := qctx.WarrantList(context.Background(), "700.HK", quote.WarrantFilter{
    		SortBy: quote.WarrantVolume, SortOrder: quote.WarrantDesc, SortOffset: 0, SortCount: 10,
    	}, quote.WarrantEN)
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("warrants:", len(list))
    }

Response [​](./quote-pull-warrant-filter.md#response)

----------------------------------------------------------------------------------------

### Response Properties [​](./quote-pull-warrant-filter.md#response-properties)

| Name | Type | Description | OrderSequence | Support\_Call/Put | Support\_Bull/Bear | Support\_Inline |
| --- | --- | --- | --- | --- | --- | --- |
| warrant\_list | object\[\] | 涡轮筛选数据列表 |     |     |     |     |
| ∟ symbol | string | 标的代码 |     | true | true | true |
| ∟ name | string | 标的名称 |     | true | true | true |
| ∟ last\_done | string | 最新价 | 0   | true | true | true |
| ∟ change\_rate | string | 涨跌幅 | 1   | true | true | true |
| ∟ change\_val | string | 涨跌额 | 2   | true | true | true |
| ∟ volume | int64 | 成交量 | 3   | true | true | true |
| ∟ turnover | string | 成交额 | 4   | true | true | true |
| ∟ expiry\_date | string | 到期日，使用 `YYMMDD` 格式 | 5   | true | true | true |
| ∟ strike\_price | string | 行权价 | 6   | true | true | false |
| ∟ upper\_strike\_price | string | 上限价 | 7   | false | false | true |
| ∟ lower\_strike\_price | string | 下限价 | 8   | false | false | true |
| ∟ outstanding\_qty | string | 街货量 | 9   | true | true | true |
| ∟ outstanding\_ratio | string | 街货比 | 10  | true | true | true |
| ∟ premium | string | 溢价率 | 11  | true | true | true |
| ∟ itm\_otm | string | 价内/价外 | 12  | true | true | false |
| ∟ implied\_volatility | string | 引伸波幅 | 13  | true | false | false |
| ∟ delta | string | 对冲值 | 14  | true | false | false |
| ∟ call\_price | string | 收回价 | 15  | false | true | false |
| ∟ to\_call\_price | string | 距收回价 | 16  | false | true | false |
| ∟ effective\_leverage | string | 有效杠杆 | 17  | true | false | false |
| ∟ leverage\_ratio | string | 杠杆比率 | 18  | true | true | true |
| ∟ conversion\_ratio | string | 换股比率 | 19  | true | true | false |
| ∟ balance\_point | string | 打和点 | 20  | true | true | false |
| ∟ status | int32 | 状态，  <br>  <br>**可选值：**  <br>`2`\- 终止交易  <br>`3` - 等待上市  <br>`4` - 正常交易 | 21  | true | true | true |
| total\_count | int32 | 符合条件的轮证总数量 |     |     |     |     |

### Protobuf [​](./quote-pull-warrant-filter.md#protobuf-1)

protobuf

    message WarrantFilterListResponse {
      repeated FilterWarrant warrant_list = 1;
      int32 total_count = 2;
    }
    
    message FilterWarrant {
      string symbol = 1;
      string name = 2;
      string last_done = 3;
      string change_rate = 4;
      string change_val = 5;
      int64 volume = 6;
      string turnover = 7;
      string expiry_date = 8;
      string strike_price = 9;
      string upper_strike_price = 10;
      string lower_strike_price = 11;
      string outstanding_qty = 12;
      string outstanding_ratio = 13;
      string premium = 14;
      string itm_otm = 15;
      string implied_volatility = 16;
      string delta = 17;
      string call_price = 18;
      string to_call_price = 19;
      string effective_leverage = 20;
      string leverage_ratio = 21;
      string conversion_ratio = 22;
      string balance_point = 23;
      string status = 24;
    }

### Response JSON Example [​](./quote-pull-warrant-filter.md#response-json-example)

json

    {
      "warrant_list": [\
        {\
          "symbol": "13157.HK",\
          "name": "腾讯麦银二七沽 A",\
          "last_done": "2.26",\
          "change_rate": "-0.0216450216450218",\
          "change_val": "-0.050000000000000266",\
          "turnover": "0",\
          "expiry_date": "20220705",\
          "strike_price": "442.233",\
          "upper_strike_price": "0",\
          "lower_strike_price": "0",\
          "outstanding_qty": "5000",\
          "outstanding_ratio": "0.0003",\
          "premium": "0.016784269662921222",\
          "itm_otm": "0.23524476916014864",\
          "implied_volatility": "0.5275",\
          "delta": "-0.8524",\
          "call_price": "0",\
          "effective_leverage": "-2.627683451852457",\
          "leverage_ratio": "3.0826882353970637",\
          "conversion_ratio": "48.544",\
          "balance_point": "332.52356000000003",\
          "status": 4\
        },\
        {\
          "symbol": "13649.HK",\
          "name": "腾讯摩通二五沽 A",\
          "last_done": "1.14",\
          "change_rate": "0",\
          "change_val": "0",\
          "turnover": "0",\
          "expiry_date": "20220518",\
          "strike_price": "445.223",\
          "upper_strike_price": "0",\
          "lower_strike_price": "0",\
          "outstanding_qty": "80000",\
          "outstanding_ratio": "0.0004",\
          "premium": "0.010810703725606",\
          "itm_otm": "0.24038066317328624",\
          "implied_volatility": "0.5997",\
          "delta": "-0.7964",\
          "call_price": "0",\
          "effective_leverage": "-2.4335424241487873",\
          "leverage_ratio": "3.055678583813144",\
          "conversion_ratio": "97.087",\
          "balance_point": "334.54382000000004",\
          "status": 4\
        }\
      ],
      "total_count": 1197
    }

错误码 [​](./quote-pull-warrant-filter.md#%E9%94%99%E8%AF%AF%E7%A0%81)

------------------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |
| 7   | 301600 | 请求标的不存在 | 检查请求的 `symbol` 是否正确 |
| 7   | 301603 | 标的无行情 | 标的没有请求的行情数据 |
| 7   | 301604 | 无权限 | 没有获取标的行情的权限 |
| 7   | 301607 | 接口限制 | 减少每页数据数量 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/warrant-filter.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/warrant-filter.md)

最后更新于:

Pager

[上一页获取轮证发行商 ID](./quote-pull-issuer.md)

[下一页获取各市场当日交易时段](./quote-pull-trade-session.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
