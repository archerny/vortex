获取标的计算指标
========

该接口用于获取标的计算指标数据，根据请求指定的计算指标返回数据。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # PE、PB、EPS 等核心指标
    longbridge calc-index TSLA.US NVDA.US
    # 指定查询的指标
    longbridge calc-index AAPL.US --index pe,pb,eps,turnover_rate
    # 市值相关指标
    longbridge calc-index TSLA.US --index pe,total_market_value

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.calc\_indexes](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.calc_indexes) |
| Rust | [longbridge::quote::QuoteContext#calc\_indexes](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.calc_indexes) |
| Go  | [QuoteContext.CalcIndex](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.CalcIndex) |
| Node.js | [QuoteContext#calcIndexes](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#calcindexes) |
| Java | [QuoteContext.getCalcIndexes](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getCalcIndexes(java.lang.String%5B%5D%2Ccom.longbridge.quote.CalcIndex%5B%5D)) |
| C++ | [longbridge::quote::QuoteContext::calc\_indexes](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a32d25607adb4304ffa334dc3467b0d0d) |

Info

[业务指令](./socket-biz-command.md)
：`26`

Request [​](./quote-pull-calc-index.md#request)

----------------------------------------------------------------------------------

### Parameters [​](./quote-pull-calc-index.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbols | string\[\] | 是   | 标的代码列表，使用 `ticker.region` 格式，例如：`[700.HK]`  <br>  <br>**校验规则：**  <br>每次请求支持传入的标的数量上限是 `500` 个 |
| calc\_index | init32\[\] | 是   | 计算指标，例如：`[1,2,3]`，详见 [CalcIndex](./quote-objects.md#calcindex-%E8%AE%A1%E7%AE%97%E6%8C%87%E6%A0%87) |

### Protobuf [​](./quote-pull-calc-index.md#protobuf)

protobuf

    message SecurityCalcQuoteRequest {
      repeated string symbols = 1;
      repeated CalcIndex calc_index = 2;
    }

### Request Example [​](./quote-pull-calc-index.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, CalcIndex, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.calc_indexes(["700.HK", "AAPL.US"], [CalcIndex.LastDone, CalcIndex.ChangeRate])
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, CalcIndex, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.calc_indexes(["700.HK", "AAPL.US"], [CalcIndex.LastDone, CalcIndex.ChangeRate])
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth, CalcIndex } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.calcIndexes(["700.HK", "AAPL.US"], [CalcIndex.LastDone, CalcIndex.ChangeRate])
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
                SecurityCalcIndex[] resp = ctx.getCalcIndexes(new String[] { "700.HK", "AAPL.US" }, new CalcIndex[] { CalcIndex.LastDone, CalcIndex.ChangeRate }).get();
                for (SecurityCalcIndex o : resp) System.out.println(o);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::QuoteContext, Config, quote::CalcIndex};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let resp = ctx.calc_indexes(vec!["700.HK".to_string(), "AAPL.US".to_string()], vec![CalcIndex::LastDone, CalcIndex::ChangeRate]).await?;
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
    
        ctx.calc_indexes(symbols, indexes, [](auto res) {
            if (!res) { std::cout << "failed: " << *res.status().message() << std::endl; return; }
            for (const auto& o : *res) std::cout << o.symbol << std::endl;
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
    	indexes, err := qctx.CalcIndex(context.Background(), []string{"700.HK", "AAPL.US"}, []quote.CalcIndex{quote.CalcIndexLastDone, quote.CalcIndexChangeRate})
    	if err != nil {
    		log.Fatal(err)
    	}
    	for _, o := range indexes {
    		fmt.Println(o.Symbol)
    	}
    }

Response [​](./quote-pull-calc-index.md#response)

------------------------------------------------------------------------------------

### Response Properties [​](./quote-pull-calc-index.md#response-properties)

| Name | Type | Description |
| --- | --- | --- |
| security\_calc\_index | object\[\] | 标的指标数据 |
| ∟ symbol | string | 标的代码 |
| ∟ last\_done | string | 最新价 |
| ∟ change\_val | string | 涨跌额 |
| ∟ change\_rate | string | 涨跌幅 (返回百分比数据，不包含`%`符号) |
| ∟ volume | int64 | 成交量 |
| ∟ turnover | string | 成交额 |
| ∟ ytd\_change\_rate | string | 年初至今涨幅 (返回百分比数据，不包含`%`符号) |
| ∟ turnover\_rate | string | 换手率 (返回百分比数据，不包含`%`符号) |
| ∟ total\_market\_value | string | 总市值 |
| ∟ capital\_flow | string | 流入资金 |
| ∟ amplitude | string | 振幅 (返回百分比数据，不包含`%`符号) |
| ∟ volume\_ratio | string | 量比  |
| ∟ pe\_ttm\_ratio | string | 市盈率 (TTM） |
| ∟ pb\_ratio | string | 市净率 |
| ∟ dividend\_ratio\_ttm | string | 股息率 (TTM) |
| ∟ five\_day\_change\_rate | string | 五日涨幅 (返回百分比数据，不包含`%`符号) |
| ∟ ten\_day\_change\_rate | string | 十日涨幅 (返回百分比数据，不包含`%`符号) |
| ∟ half\_year\_change\_rate | string | 半年涨幅 (返回百分比数据，不包含`%`符号) |
| ∟ five\_minutes\_change\_rate | string | 五分钟涨幅 (返回百分比数据，不包含`%`符号) |
| ∟ expiry\_date | string | 到期日 |
| ∟ strike\_price | string | 行权价 |
| ∟ upper\_strike\_price | string | 上限价 |
| ∟ lower\_strike\_price | string | 下限价 |
| ∟ outstanding\_qty | int64 | 街货量 |
| ∟ outstanding\_ratio | string | 街货比 (返回百分比数据，不包含`%`符号) |
| ∟ premium | string | 溢价率 (返回百分比数据，不包含`%`符号) |
| ∟ itm\_otm | string | 价内/价外 (返回百分比数据，不包含`%`符号) |
| ∟ implied\_volatility | string | 隐含波动率 (返回百分比数据，不包含`%`符号) |
| ∟ warrant\_delta | string | 对冲值 |
| ∟ call\_price | string | 收回价 |
| ∟ to\_call\_price | string | 距收回价 (返回百分比数据，不包含`%`符号) |
| ∟ effective\_leverage | string | 有效杠杆 |
| ∟ leverage\_ratio | string | 杠杆比率 |
| ∟ conversion\_ratio | string | 换股比率 |
| ∟ balance\_point | string | 打和点 |
| ∟ open\_interest | int64 | 未平仓数 |
| ∟ delta | string | Delta |
| ∟ gamma | string | Gamma |
| ∟ theta | string | Theta |
| ∟ vega | string | Vega |
| ∟ rho | string | Rho |

### Protobuf [​](./quote-pull-calc-index.md#protobuf-1)

protobuf

    message SecurityCalcIndex {
      string symbol = 1;
      string last_done = 2;
      string change_val = 3;
      string change_rate = 4;
      int64 volume = 5;
      string turnover = 6;
      string ytd_change_rate = 7;
      string turnover_rate = 8;
      string total_market_value = 9;
      string capital_flow = 10;
      string amplitude = 11;
      string volume_ratio = 12;
      string pe_ttm_ratio = 13;
      string pb_ratio = 14;
      string dividend_ratio_ttm = 15;
      string five_day_change_rate = 16;
      string ten_day_change_rate = 17;
      string half_year_change_rate = 18;
      string five_minutes_change_rate = 19;
      string expiry_date = 20;
      string strike_price = 21;
      string upper_strike_price = 22;
      string lower_strike_price = 23;
      int64  outstanding_qty = 24;
      string outstanding_ratio = 25;
      string premium = 26;
      string itm_otm = 27;
      string implied_volatility = 28;
      string warrant_delta = 29;
      string call_price = 30;
      string to_call_price = 31;
      string effective_leverage = 32;
      string leverage_ratio = 33;
      string conversion_ratio = 34;
      string balance_point = 35;
      int64 open_interest = 36;
      string delta = 37;
      string gamma = 38;
      string theta = 39;
      string vega = 40;
      string rho = 41;
    }
    
    message SecurityCalcQuoteResponse {
      repeated SecurityCalcIndex security_calc_index = 1;
    }

### Response JSON Example [​](./quote-pull-calc-index.md#response-json-example)

json

    {
      "securityCalcIndex": [\
        {\
          "symbol": "AAPL.US",\
          "lastDone": "131.880",\
          "changeVal": "-5.2500",\
          "changeRate": "-3.83",\
          "volume": "122207099",\
          "turnover": "16269088361.000",\
          "ytdChangeRate": "-25.63",\
          "turnoverRate": "0.76",\
          "totalMarketValue": "2134501670280.00",\
          "capitalFlow": "14664053535.556",\
          "amplitude": "2.74",\
          "volumeRatio": "3.22",\
          "peTtmRatio": "21.26",\
          "pbRatio": "31.71",\
          "dividendRatioTtm": "0.64",\
          "fiveDayChangeRate": "-9.76",\
          "tenDayChangeRate": "-11.87",\
          "halfYearChangeRate": "-7.01",\
          "fiveMinutesChangeRate": "0.00"\
        },\
        {\
          "symbol": "69672.HK",\
          "lastDone": "0.010",\
          "changeRate": "0.00",\
          "expiryDate": "20221024",\
          "strikePrice": "379.880",\
          "outstandingQty": "6090000",\
          "outstandingRatio": "7.61",\
          "premium": "0.67",\
          "itmOtm": "0.65",\
          "callPrice": "375.880",\
          "toCallPrice": "-100.00",\
          "leverageRatio": "75.48",\
          "balancePoint": "374.880"\
        },\
        {\
          "symbol": "AAPL220617C137000.US",\
          "lastDone": "1.17",\
          "changeVal": "-2.04",\
          "changeRate": "-63.55",\
          "volume": "23499",\
          "turnover": "3903660.00",\
          "expiryDate": "20220617",\
          "strikePrice": "137.00",\
          "premium": "11709.40",\
          "impliedVolatility": "43.54",\
          "openInterest": "5210",\
          "delta": "0.263",\
          "gamma": "0.043",\
          "theta": "-1.266",\
          "vega": "5.660",\
          "rho": "0.580"\
        },\
        {\
          "symbol": "HSI.HK",\
          "lastDone": "21119.650",\
          "changeVal": "52.070",\
          "changeRate": "0.25",\
          "volume": "96449546281",\
          "turnover": "96449546281.000",\
          "ytdChangeRate": "-9.74",\
          "amplitude": "1.86",\
          "volumeRatio": "0.59",\
          "fiveDayChangeRate": "-1.91",\
          "tenDayChangeRate": "-0.02",\
          "halfYearChangeRate": "-11.83",\
          "fiveMinutesChangeRate": "0.00"\
        }\
      ]
    }

错误码 [​](./quote-pull-calc-index.md#%E9%94%99%E8%AF%AF%E7%A0%81)

--------------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |
| 7   | 301600 | 请求标的不存在 | 检查请求的 `symbol` 是否正确 |
| 7   | 301603 | 标的无行情 | 标的没有请求的行情数据 |
| 7   | 301604 | 无权限 | 没有获取标的行情的权限 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/calc-index.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/calc-index.md)

最后更新于:

Pager

[上一页获取标的当日资金分布](./quote-pull-capital-distribution.md)

[下一页获取标的 K 线](./quote-pull-candlestick.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
