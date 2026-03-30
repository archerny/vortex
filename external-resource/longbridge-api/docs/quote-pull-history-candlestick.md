获取标的历史 K 线
==========

该接口用于获取标的的历史 K 线数据。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 2025 年 Q1 日 K
    longbridge kline-history TSLA.US --start 2025-01-01 --end 2025-03-31
    # 2024 全年周 K
    longbridge kline-history AAPL.US --start 2024-01-01 --end 2024-12-31 --period week
    # 2025 全年日 K
    longbridge kline-history NVDA.US --start 2025-01-01 --end 2025-12-31

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.history\_candlesticks\_by\_offset](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.history_candlesticks_by_offset) |
| Rust | [longbridge::quote::QuoteContext#history\_candlesticks\_by\_offset](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.history_candlesticks_by_offset) |
| Go  | [QuoteContext.HistoryCandlesticksByOffset](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.HistoryCandlesticksByOffset) |
| Node.js | [QuoteContext#historyCandlesticksByOffset](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#historycandlesticksbyoffset) |
| Java | [QuoteContext.getHistoryCandlesticksByOffset](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getHistoryCandlesticksByOffset(java.lang.String%2Ccom.longbridge.quote.Period%2Ccom.longbridge.quote.AdjustType%2Cboolean%2Cjava.time.LocalDateTime%2Cint%2Ccom.longbridge.quote.TradeSessions)) |
| C++ | [longbridge::quote::QuoteContext::history\_candlesticks\_by\_offset](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#ae295ca3676f3b39d1b34103a35c07429) |

Info

[业务指令](./socket-biz-command.md)
：`27`

Request [​](./quote-pull-history-candlestick.md#request)

-------------------------------------------------------------------------------------------

### Parameters [​](./quote-pull-history-candlestick.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | 是   | 标的代码，使用 `ticker.region` 格式，例如：`700.HK` |
| period | int32 | 是   | k 线周期，例如：`1000`，详见 [Period](./quote-objects.md#period-k-%E7%BA%BF%E5%91%A8%E6%9C%9F) |
| adjust\_type | int32 | 是   | 复权类型，例如：`0`，详见 [AdjustType](./quote-objects.md#adjusttype-k-%E7%BA%BF%E5%A4%8D%E6%9D%83%E7%B1%BB%E5%9E%8B) |
| query\_type | int32 | 是   | 查询方式  <br>  <br>**可选值：**  <br>`1` - 按偏移查询  <br>`2` - 按日期区间查询 |
| date\_request | object | 否   | 按日期查询时必填 |
| ∟ start\_date | string | 否   | 开始日期，格式为 `YYYYMMDD`，例如：20231016  <br>  <br>**参数说明：**  <br>1\. start\_date 和 end\_date 均不填：返回最新的 1000 根 K 线；  <br>2\. 仅填 start\_date：返回 start\_date 与最新交易日区间内的 K 线。若此区间内 K 线超过 1000 根，则优先返回靠近 start\_date 的 1000 根 K 线；  <br>3\. 仅填 end\_date：返回 end\_date 及以前的 1000 根 K 线；  <br>4\. start\_date 和 end\_date 均填：返回此区间内的 K 线数据。若此区间内 K 线超过 1000 根，则优先返回靠近 end\_date 的 1000 根 K 线 |
| ∟ end\_date | string | 否   | 结束日期，格式为 `YYYYMMDD`，例如：20231016 |
| offset\_request | object | 否   | 按偏移查询时必填 |
| ∟ direction | int32 | 是   | 查询方向  <br>  <br>**可选值：**  <br>`0` - 向历史数据方向查找  <br>`1` - 向最新数据方向查找 |
| ∟ date | string | 否   | 查询日期，格式为 `YYYYMMDD`，例如：20231016，为空时使用标的所在市场的最新交易日 |
| ∟ minute | string | 否   | 查询时间，格式为 `HHMM`，例如：09:35，仅在查询分钟级别 K 线时有效 |
| ∟ count | int32 | 否   | 查询数量，填写范围 `[1,1000]`，为空时默认查询 `10` 条 |
| trade\_session | int32 | 否   | 交易時段，0: 盤中，100: 所有延長時段（盤前，盤中，盤後，夜盤） |

### Protobuf [​](./quote-pull-history-candlestick.md#protobuf)

protobuf

    message SecurityHistoryCandlestickRequest {
    
      message OffsetQuery {
        Direction direction = 1;
        string date = 2;
        string minute = 3;
        int32 count = 4;
      }
    
      message DateQuery {
        string start_date = 1;
        string end_date = 2;
      }
    
      string symbol = 1;
      Period period = 2;
      AdjustType adjust_type = 3;
      HistoryCandlestickQueryType query_type = 4;
      OffsetQuery offset_request = 5;
      DateQuery date_request = 6;
    }

### Request Example [​](./quote-pull-history-candlestick.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from datetime import datetime, date
    from longbridge.openapi import QuoteContext, Config, Period, AdjustType, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    # Query after 2023-01-01
    resp = ctx.history_candlesticks_by_offset("700.HK", Period.Day, AdjustType.NoAdjust, True, 10, datetime(2023, 1, 1))
    print(resp)
    
    # Query before 2023-01-01
    resp = ctx.history_candlesticks_by_offset("700.HK", Period.Day, AdjustType.NoAdjust, False, 10, datetime(2023, 1, 1))
    print(resp)
    
    # Query 2023-01-01 to 2023-02-01
    resp = ctx.history_candlesticks_by_date("700.HK", Period.Day, AdjustType.NoAdjust, date(2023, 1, 1), date(2023, 2, 1))
    print(resp)

python

    import asyncio
    from datetime import datetime, date
    from longbridge.openapi import AsyncQuoteContext, Config, Period, AdjustType, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        # Query after 2023-01-01
        resp = await ctx.history_candlesticks_by_offset("700.HK", Period.Day, AdjustType.NoAdjust, True, 10, datetime(2023, 1, 1))
        print(resp)
    
        # Query before 2023-01-01
        resp = await ctx.history_candlesticks_by_offset("700.HK", Period.Day, AdjustType.NoAdjust, False, 10, datetime(2023, 1, 1))
        print(resp)
    
        # Query 2023-01-01 to 2023-02-01
        resp = await ctx.history_candlesticks_by_date("700.HK", Period.Day, AdjustType.NoAdjust, date(2023, 1, 1), date(2023, 2, 1))
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth, Period, AdjustType, TradeSessions, NaiveDatetime, NaiveDate, Time } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const datetime = new NaiveDatetime(new NaiveDate(2023, 1, 1), new Time(0, 0, 0))
      const resp = await ctx.historyCandlesticksByOffset("700.HK", Period.Day, AdjustType.NoAdjust, true, datetime, 10, TradeSessions.Intraday)
      console.log(resp)
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.quote.*;
    import java.time.LocalDateTime;
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id").build(url -> System.out.println("Open to authorize: " + url)).get();
                 Config config = Config.fromOAuth(oauth);
                 QuoteContext ctx = QuoteContext.create(config)) {
                Candlestick[] resp = ctx.getHistoryCandlesticksByOffset("700.HK", Period.Day, AdjustType.NoAdjust, true, LocalDateTime.of(2023, 1, 1, 0, 0), 10, TradeSessions.Intraday).get();
                for (Candlestick c : resp) System.out.println(c);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::QuoteContext, Config, quote::{Period, AdjustType, TradeSessions}};
    use time::macros::datetime;
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let dt = datetime!(2023-01-01 00:00);
        let resp = ctx.history_candlesticks_by_offset("700.HK", Period::Day, AdjustType::NoAdjust, true, Some(dt), 10, TradeSessions::Intraday).await?;
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
    
        ctx.history_candlesticks_by_offset("700.HK", Period::Day, AdjustType::NoAdjust, true, std::nullopt, 10, TradeSessions::Intraday, [](auto res) {
            if (!res) { std::cout << "failed: " << *res.status().message() << std::endl; return; }
            std::cout << "candlesticks: " << res->size() << std::endl;
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
    	"time"
    
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
    	dt := time.Date(2023, 1, 1, 0, 0, 0, 0, time.UTC)
    	sticks, err := qctx.HistoryCandlesticksByOffset(context.Background(), "700.HK", quote.PeriodDay, quote.AdjustTypeNo, true, &dt, 10)
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("candlesticks:", len(sticks))
    }

Response [​](./quote-pull-history-candlestick.md#response)

---------------------------------------------------------------------------------------------

### Response Properties [​](./quote-pull-history-candlestick.md#response-properties)

| Name | Type | Description |
| --- | --- | --- |
| symbol | string | 标的代码，例如：`AAPL.US` |
| candlesticks | object\[\] | K 线数据 |
| ∟ close | string | 当前周期收盘价 |
| ∟ open | string | 当前周期开盘价 |
| ∟ low | string | 当前周期最低价 |
| ∟ high | string | 当前周期最高价 |
| ∟ volume | int64 | 当前周期成交量 |
| ∟ turnover | string | 当前周期成交额 |
| ∟ timestamp | int64 | 当前周期的时间戳 |
| ∟ trade\_session | int32 | 交易時段，详见 [TradeSession](./quote-objects.md#tradesession-%E4%BA%A4%E6%98%93%E6%97%B6%E6%AE%B5) |

### Protobuf [​](./quote-pull-history-candlestick.md#protobuf-1)

protobuf

    message SecurityCandlestickResponse {
      string symbol = 1;
      repeated Candlestick candlesticks = 2;
    }
    
    message Candlestick {
      string close = 1;
      string open = 2;
      string low = 3;
      string high = 4;
      int64 volume = 5;
      string turnover = 6;
      int64 timestamp = 7;
    }

### Response JSON Example [​](./quote-pull-history-candlestick.md#response-json-example)

json

    {
      "symbol": "700.HK",
      "candlesticks": [\
        {\
          "close": "362.000",\
          "open": "364.600",\
          "low": "361.600",\
          "high": "368.800",\
          "volume": 10853604,\
          "turnover": "3954556819.000",\
          "timestamp": 1650384000\
        },\
        {\
          "close": "348.000",\
          "open": "352.000",\
          "low": "343.000",\
          "high": "356.200",\
          "volume": 25738562,\
          "turnover": "8981529950.000",\
          "timestamp": 1650470400\
        },\
        {\
          "close": "340.600",\
          "open": "334.800",\
          "low": "334.200",\
          "high": "343.000",\
          "volume": 28031299,\
          "turnover": "9492674293.000",\
          "timestamp": 1650556800\
        },\
        {\
          "close": "327.400",\
          "open": "332.200",\
          "low": "325.200",\
          "high": "338.600",\
          "volume": 25788422,\
          "turnover": "8541441823.000",\
          "timestamp": 1650816000\
        },\
        {\
          "close": "335.800",\
          "open": "332.200",\
          "low": "330.600",\
          "high": "341.600",\
          "volume": 27288328,\
          "turnover": "9166022626.000",\
          "timestamp": 1650902400\
        }\
      ]
    }

权限说明 [​](./quote-pull-history-candlestick.md#%E6%9D%83%E9%99%90%E8%AF%B4%E6%98%8E)

---------------------------------------------------------------------------------------------------------------------

依据用户的资产和交易情况，不同类型的用户每月可查询历史数据的标的数量如下表：

*   额度按照自然月计算，每月初额度加满，上月剩余额度不累计到本月。一个自然月内重复请求同一只标的的历史 K 线，仅统计一次。
*   新入金的账户，额度会在下个交易日自动生效；当账户的总资产或交易笔数增加、且达到更高等级时，额度会在下一个交易日生效。
*   总资产：用户的港股、美股、A 股等证券账户的总资产，按照汇率换算成港元。取用户上个自然月最后一个交易日的总资产与最近一个完整交易日的总资产的较大值。
*   月交易笔数：用户有成交的订单数量，一个订单部分成交、或多次全部成交、或一次全部成交均算 1 笔。取用户上个自然月的成交笔数与当前自然月的成交笔数的较大值。

| 用户类型 | 每月可查询的标的数量上限（只） |
| --- | --- |
| 用户开户 | 100 |
| 总资产达 1 万 HKD | 400 |
| 总资产达 8 万 HKD | 600 |
| 总资产达 40 万 HKD 或 月交易笔数大于 160 笔 | 1000 |
| 总资产达 400 万 HKD 或 月交易笔数大于 1600 笔 | 2000 |
| 总资产达 600 万 HKD 或 月交易笔数大于 2500 笔 | 3000 |

历史 K 线区间说明 [​](./quote-pull-history-candlestick.md#%E5%8E%86%E5%8F%B2-k-%E7%BA%BF%E5%8C%BA%E9%97%B4%E8%AF%B4%E6%98%8E)

---------------------------------------------------------------------------------------------------------------------------------------------------------

| 市场  | 日/周/月/年 K 线 | 分钟 K 线 | 说明  |
| --- | --- | --- | --- |
| 港股  | 2004-6-1 至今 | 2022-09-28 至今 |     |
| 美股  | 2010-6-1 至今 | 2023-12-4 至今 |     |
| 美股期权 | \-  | \-  | 美股期权历史数据目前暂不支持，待后续开放更长时段的数据 |
| A 股 | 1999-11-1 至今 | 2022-08-25 至今 |     |

频次限制 [​](./quote-pull-history-candlestick.md#%E9%A2%91%E6%AC%A1%E9%99%90%E5%88%B6)

---------------------------------------------------------------------------------------------------------------------

Caution

*   每 30 秒内最多请求 60 次历史 K 线接口。

错误码 [​](./quote-pull-history-candlestick.md#%E9%94%99%E8%AF%AF%E7%A0%81)

-----------------------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |
| 7   | 301600 | 请求数据非法 | 检查请求的 `symbol`，`count`，`adjust_type`, `period` 数据是否在正确范围 |
| 7   | 301603 | 标的无行情 | 标的没有请求的行情数据 |
| 7   | 301604 | 无权限 | 没有获取标的行情的权限 |
| 7   | 301607 | 接口限制 | 超过当月能够查询的标的数量上限 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/history-candlestick.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/history-candlestick.md)

最后更新于:

Pager

[上一页获取标的当日分时](./quote-pull-intraday.md)

[下一页获取标的的期权链到期日列表](./quote-pull-optionchain-date.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
