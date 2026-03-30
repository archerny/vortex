# 枚举与常量

枚举类型

[](./appendix-enum-cpp.md#%E6%9E%9A%E4%B8%BE%E7%B1%BB%E5%9E%8B)

--------------------------------------------------------------------------------------------------

C++ SDK 的枚举类型定义在 `tigerapi/enums.h` 中，命名空间为 `TIGER_API`。所有枚举均为 `enum class` 类型，可通过 `enum_to_str()` 函数转换为字符串。

* * *

Market 市场

[](./appendix-enum-cpp.md#market-%E5%B8%82%E5%9C%BA)

--------------------------------------------------------------------------------------------

C++

    enum class Market {
        ALL,  // 全部
        US,   // 美国
        HK,   // 香港
        CN,   // 中国
        SG    // 新加坡
    };

**使用示例**

C++

    Market market = Market::US;
    utility::string_t market_str = enum_to_str(market);  // "US"

* * *

SecType 合约类型

[](./appendix-enum-cpp.md#sectype-%E5%90%88%E7%BA%A6%E7%B1%BB%E5%9E%8B)

------------------------------------------------------------------------------------------------------------------

C++

    enum class SecType {
        ALL,   // 全部
        STK,   // 股票
        OPT,   // 期权
        WAR,   // 窝轮
        IOPT,  // 牛熊证
        FUT,   // 期货
        FOP,   // 期货期权
        CASH   // 外汇
    };

* * *

Currency 币种

[](./appendix-enum-cpp.md#currency-%E5%B8%81%E7%A7%8D)

------------------------------------------------------------------------------------------------

C++

    enum class Currency {
        ALL,  // 全部
        USD,  // 美元
        HKD,  // 港币
        CNH,  // 离岸人民币
        SGD   // 新加坡元
    };

* * *

SegmentType 账户分段

[](./appendix-enum-cpp.md#segmenttype-%E8%B4%A6%E6%88%B7%E5%88%86%E6%AE%B5)

--------------------------------------------------------------------------------------------------------------------------

C++

    enum class SegmentType {
        ALL,  // 全部
        SEC,  // 证券
        FUT   // 期货
    };

* * *

BarPeriod K线周期

[](./appendix-enum-cpp.md#barperiod-k%E7%BA%BF%E5%91%A8%E6%9C%9F)

--------------------------------------------------------------------------------------------------------------

C++

    enum class BarPeriod {
        DAY,              // 日K
        WEEK,             // 周K
        MONTH,            // 月K
        YEAR,             // 年K
        ONE_MINUTE,       // 1分钟
        THREE_MINUTES,    // 3分钟
        FIVE_MINUTES,     // 5分钟
        TEN_MINUTES,      // 10分钟
        FIFTEEN_MINUTES,  // 15分钟
        HALF_HOUR,        // 30分钟
        FORTY_FIVE_MINUTES, // 45分钟
        ONE_HOUR,         // 60分钟
        TWO_HOURS,        // 2小时
        THREE_HOURS,      // 3小时
        FOUR_HOURS,       // 4小时
        SIX_HOURS         // 6小时
    };

**字符串映射**

| 枚举值 | 字符串 |
| --- | --- |
| DAY | "day" |
| WEEK | "week" |
| MONTH | "month" |
| YEAR | "year" |
| ONE\_MINUTE | "1min" |
| THREE\_MINUTES | "3min" |
| FIVE\_MINUTES | "5min" |
| TEN\_MINUTES | "10min" |
| FIFTEEN\_MINUTES | "15min" |
| HALF\_HOUR | "30min" |
| FORTY\_FIVE\_MINUTES | "45min" |
| ONE\_HOUR | "60min" |
| TWO\_HOURS | "2hour" |
| THREE\_HOURS | "3hours" |
| FOUR\_HOURS | "4hour" |
| SIX\_HOURS | "6hour" |

* * *

CapitalPeriod 资金流向周期

[](./appendix-enum-cpp.md#capitalperiod-%E8%B5%84%E9%87%91%E6%B5%81%E5%90%91%E5%91%A8%E6%9C%9F)

--------------------------------------------------------------------------------------------------------------------------------------------------

C++

    enum class CapitalPeriod {
        INTRADAY,   // 日内
        DAY,        // 日
        WEEK,       // 周
        MONTH,      // 月
        YEAR,       // 年
        QUARTER,    // 季度
        HALFAYEAR   // 半年
    };

**字符串映射**

| 枚举值 | 字符串 |
| --- | --- |
| INTRADAY | "intraday" |
| DAY | "day" |
| WEEK | "week" |
| MONTH | "month" |
| YEAR | "year" |
| QUARTER | "quarter" |
| HALFAYEAR | "6month" |

* * *

TimelinePeriod 分时周期

[](./appendix-enum-cpp.md#timelineperiod-%E5%88%86%E6%97%B6%E5%91%A8%E6%9C%9F)

--------------------------------------------------------------------------------------------------------------------------------

C++

    enum class TimelinePeriod {
        DAY = 1,       // 当日分时
        FIVE_DAYS = 2  // 5日分时
    };

* * *

TradingSession 交易时段

[](./appendix-enum-cpp.md#tradingsession-%E4%BA%A4%E6%98%93%E6%97%B6%E6%AE%B5)

--------------------------------------------------------------------------------------------------------------------------------

C++

    enum class TradingSession {
        PreMarket,   // 盘前
        Regular,     // 盘中
        AfterHours   // 盘后
    };

* * *

QuoteRight 复权类型

[](./appendix-enum-cpp.md#quoteright-%E5%A4%8D%E6%9D%83%E7%B1%BB%E5%9E%8B)

------------------------------------------------------------------------------------------------------------------------

C++

    enum class QuoteRight {
        br,  // 前复权
        nr   // 不复权
    };

* * *

Right 期权方向

[](./appendix-enum-cpp.md#right-%E6%9C%9F%E6%9D%83%E6%96%B9%E5%90%91)

--------------------------------------------------------------------------------------------------------------

C++

    enum class Right {
        PUT,   // 看跌
        CALL,  // 看涨
        ALL    // 全部
    };

* * *

OrderStatus 订单状态

[](./appendix-enum-cpp.md#orderstatus-%E8%AE%A2%E5%8D%95%E7%8A%B6%E6%80%81)

--------------------------------------------------------------------------------------------------------------------------

C++

    enum class OrderStatus {
        PendingNew,      // 待提交
        PendingSubmit,   // 提交中
        Initial,         // 初始
        Submitted,       // 已提交（未成交）
        PartiallyFilled, // 部分成交
        Filled,          // 全部成交
        Cancelled,       // 已取消
        PendingCancel,   // 取消中
        Inactive,        // 未激活
        Invalid          // 无效
    };

* * *

OrderSortBy 订单排序方式

[](./appendix-enum-cpp.md#ordersortby-%E8%AE%A2%E5%8D%95%E6%8E%92%E5%BA%8F%E6%96%B9%E5%BC%8F)

----------------------------------------------------------------------------------------------------------------------------------------------

C++

    enum class OrderSortBy {
        LATEST_CREATED,        // 按创建时间排序
        LATEST_STATUS_UPDATED  // 按状态更新时间排序
    };

* * *

License 牌照

[](./appendix-enum-cpp.md#license-%E7%89%8C%E7%85%A7)

----------------------------------------------------------------------------------------------

C++

    enum class License {
        TBNZ,  // 新西兰
        TBSG,  // 新加坡
        TBHK,  // 香港
        TBAU,  // 澳大利亚
        TBUS   // 美国
    };

* * *

Language 语言

[](./appendix-enum-cpp.md#language-%E8%AF%AD%E8%A8%80)

------------------------------------------------------------------------------------------------

C++

    enum class Language {
        zh_CN,  // 简体中文
        zh_TW,  // 繁体中文
        en_US   // 英文
    };

* * *

TickSizeType 最小报价单位区间类型

[](./appendix-enum-cpp.md#ticksizetype-%E6%9C%80%E5%B0%8F%E6%8A%A5%E4%BB%B7%E5%8D%95%E4%BD%8D%E5%8C%BA%E9%97%B4%E7%B1%BB%E5%9E%8B)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

C++

    enum class TickSizeType {
        OPEN,         // 开区间 (begin, end)
        OPEN_CLOSED,  // 左开右闭 (begin, end]
        CLOSED_OPEN,  // 左闭右开 [begin, end)\
        CLOSED        // 闭区间 [begin, end]\
    };\
\
* * *\
\
常量\
\
[](./appendix-enum-cpp.md#%E5%B8%B8%E9%87%8F)\
\
------------------------------------------------------------------------------\
\
### \
\
交易所部分代码映射 (PART\_CODE\_MAP)\
\
[](./appendix-enum-cpp.md#%E4%BA%A4%E6%98%93%E6%89%80%E9%83%A8%E5%88%86%E4%BB%A3%E7%A0%81%E6%98%A0%E5%B0%84-part_code_map)\
\
| 代码  | 交易所缩写 | 交易所全称 |\
| --- | --- | --- |\
| a   | AMEX | NYSE American, LLC |\
| b   | BX  | NASDAQ OMX BX, Inc. |\
| c   | NSX | NYSE National, Inc. |\
| n   | NYSE | New York Stock Exchange, LLC |\
| p   | ARCA | NYSE Arca, Inc. |\
| t   | NSDQ | NASDAQ Stock Market, LLC |\
| v   | IEX | Investors' Exchange, LLC |\
| z   | BZX | Cboe BZX Exchange, Inc. |\
\
### \
\
美股成交条件 (US\_TRADE\_COND\_MAP)\
\
[](./appendix-enum-cpp.md#%E7%BE%8E%E8%82%A1%E6%88%90%E4%BA%A4%E6%9D%A1%E4%BB%B6-us_trade_cond_map)\
\
| 代码  | 含义  |\
| --- | --- |\
| (空格) | 正常成交 (Regular Sale) |\
| F   | 跨市场扫单 (Intermarket Sweep) |\
| I   | 碎股成交 (Odd Lot Trade) |\
| T   | 盘前盘后成交 (Form T) |\
| U   | 延长交易时段 (Extended Trading Hours) |\
| X   | 交叉成交 (Cross Trade) |\
\
### \
\
港股成交条件 (HK\_TRADE\_COND\_MAP)\
\
[](./appendix-enum-cpp.md#%E6%B8%AF%E8%82%A1%E6%88%90%E4%BA%A4%E6%9D%A1%E4%BB%B6-hk_trade_cond_map)\
\
| 代码  | 含义  |\
| --- | --- |\
| (空格) | 自动对盘正常成交 |\
| D   | 碎股成交 |\
| U   | 竞价成交 |\
| P   | 收市后交易 |\
| M   | 非自动对盘 |\
| X   | 同券商自动对盘 |\

