# 附录二：枚举参数对照表

关于枚举参数及常用字段参数含义，请参考本节

Language

[](./appendix-enum.md#language)

======================================================================

**语言** `tigeropen.common.consts.Language` [source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/consts/__init__.py)

| 标识  | 语言  |
| --- | --- |
| zh\_CN | 简体中文 |
| zh\_TW | 繁体中文 |
| en\_US | 英文  |

Market

[](./appendix-enum.md#market)

==================================================================

**市场** `tigeropen.common.consts.Market` [source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/consts/__init__.py)

| 标识  | 市场  |
| --- | --- |
| ALL | 全部  |
| US  | 美股  |
| HK  | 港股  |
| CN  | A股  |
| SG  | 新加坡 |
| AU  | 澳大利亚 |
| NZ  | 新西兰 |

CapitalPeriod

[](./appendix-enum.md#capitalperiod)

================================================================================

**资金周期**

| 标识  | 市场  |
| --- | --- |
| INTRADAY | intraday |
| DAY | day |
| WEEK | week |
| MONTH | month |
| YEAR | year |
| QUARTER | quarter |
| HALFAYEAR | 6month |

合约类型

[](./appendix-enum.md#%E5%90%88%E7%BA%A6%E7%B1%BB%E5%9E%8B)

==============================================================================================

`tigeropen.common.consts.SecurityType` [source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/consts/__init__.py)

| 标识  | 合约类型 |
| --- | --- |
| STK | 股票  |
| OPT | 美股期权 |
| WAR | 港股窝轮 |
| IOPT | 港股牛熊证 |
| CASH | 外汇  |
| FUT | 期货  |
| FOP | 期货期权 |
| FUND | 基金  |

货币类型

[](./appendix-enum.md#%E8%B4%A7%E5%B8%81%E7%B1%BB%E5%9E%8B)

==============================================================================================

`tigeropen.common.consts.Currency` [source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/consts/__init__.py)

| 标识  | 货币类型 |
| --- | --- |
| ALL | 全部  |
| USD | 美元  |
| HKD | 港币  |
| CNH | 人民币 |
| SGD | 新加坡元 |
| AUD | 澳大利亚元 |
| JPY | 日元  |
| EUR | 欧元  |
| GBP | 英镑  |
| CAD | 加拿大元 |
| NZD | 新西兰元 |

订单状态

[](./appendix-enum.md#%E8%AE%A2%E5%8D%95%E7%8A%B6%E6%80%81)

==============================================================================================

`tigeropen.common.consts.OrderStatus` [source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/consts/__init__.py)
.

sdk 通过 `tigeropen.common.util.order_utils.get_order_status` 将状态值处理成枚举标识。[source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/util/order_utils.py#L134)

| 枚举标识 | 状态值 | 状态码 | 说明  |
| --- | --- | --- | --- |
| EXPIRED | Invalid | \-2 | 非法状态 |
| NEW | Initial | \-1 | 订单初始状态 |
| CANCELLED | Cancelled | 4   | 已取消 |
| HELD | Submitted | 5   | 订单已经提交 |
| PARTIALLY\_FILLED | PartiallyFilled | 2, 5, 8 | 部分成交 |
| FILLED | Filled | 6   | 完全成交 |
| REJECTED | Inactive | 7   | 已失效 |

> 注意：部分成交的状态比较特殊，有可能是HELD，CANCELLED，EXPIRED，REJECTED的任意一种状态 另外可参见 sdk 内处理订单状态的方法 `tigeropen.trade.domain.order.Order.status`, 即如果服务端返回的订单状态为 HELD(submitted), 且有部分数量的成交，则此时订单状态为 PARTIALLY\_FILLED

Python

    class Order:
        ...
        ...
        @property
        def status(self):
            if not self.remaining and self.filled:
                return OrderStatus.FILLED
            elif self._status == OrderStatus.HELD and self.filled:
                return OrderStatus.PARTIALLY_FILLED
            else:
                return self._status

订单改单状态

[](./appendix-enum.md#%E8%AE%A2%E5%8D%95%E6%94%B9%E5%8D%95%E7%8A%B6%E6%80%81)

==================================================================================================================

| 状态  | 说明  |
| --- | --- |
| NONE | 默认状态 or 订单终结 |
| RECEIVED | 改单已接收（pretrade检查通过） |
| REPLACED | 改单成功（上手已确认） |
| FAILED | 改单失败 (收到上手拒绝报告） |

订单撤单状态

[](./appendix-enum.md#%E8%AE%A2%E5%8D%95%E6%92%A4%E5%8D%95%E7%8A%B6%E6%80%81)

==================================================================================================================

| 状态  | 说明  |
| --- | --- |
| NONE | 默认状态 or 订单终结 |
| RECEIVED | 撤单已接收（pretrade检查通过） |
| FAILED | 撤单失败 (收到上手拒绝报告） |

订单类型

[](./appendix-enum.md#%E8%AE%A2%E5%8D%95%E7%B1%BB%E5%9E%8B)

==============================================================================================

| 类型  | 说明  |
| --- | --- |
| MKT | 市价单 |
| LMT | 限价单 |
| STP | 止损单 |
| STP\_LMT | 止损限价单 |
| TRAIL | 跟踪止损单 |
| AM  | 竞价市价单（港股） |
| AL  | 竞价限价单（港股） |

附加订单类型

[](./appendix-enum.md#%E9%99%84%E5%8A%A0%E8%AE%A2%E5%8D%95%E7%B1%BB%E5%9E%8B)

------------------------------------------------------------------------------------------------------------------

| 类型  | 说明  |
| --- | --- |
| PROFIT | 附加止盈单 |
| LOSS | 附加止损单 |
| BRACKETS | 附加括号单 |

  

订单时段

[](./appendix-enum.md#%E8%AE%A2%E5%8D%95%E6%97%B6%E6%AE%B5)

----------------------------------------------------------------------------------------------

`tigeropen.common.consts.TradingSessionType`

| 类型  | 说明  |
| --- | --- |
| RTH | 盘中交易 |
| PRE\_RTH\_POST | 盘前/盘中/盘后交易 |
| OVERNIGHT | 夜盘交易 |
| FULL | 全时段 |
| HK\_AUC | 竞价时段交易(港股) |
| HK\_CTS | 持续交易时段交易(港股) |
| HK\_AUC\_CTS | 竞价/持续交易时段交易(港股) |

  

账户类型

[](./appendix-enum.md#%E8%B4%A6%E6%88%B7%E7%B1%BB%E5%9E%8B)

==============================================================================================

| 类型  | 说明  |
| --- | --- |
| CASH | 现金账户 |
| RegTMargin | Reg T 保证金账户 |
| PMGRN | 投资组合保证金 |

  

账户划分

[](./appendix-enum.md#%E8%B4%A6%E6%88%B7%E5%88%92%E5%88%86)

==============================================================================================

`tigeropen.common.consts.SegmentType`

| 标识  | 合约类型 |
| --- | --- |
| ALL | 全部类型(部分接口支持) |
| SEC | 证券  |
| FUT | 期货  |
| FUND | 基金  |

账户状态

[](./appendix-enum.md#%E8%B4%A6%E6%88%B7%E7%8A%B6%E6%80%81)

==============================================================================================

| 状态  | 说明  |
| --- | --- |
| New | 新账户 |
| Funded | 已入金 |
| Open | 已开通 |
| Pending | 待确认 |
| Abandoned | 已废弃 |
| Rejected | 已拒绝 |
| Closed | 已关闭 |
| Unknown | 未知  |

  

K线类型

[](./appendix-enum.md#k%E7%BA%BF%E7%B1%BB%E5%9E%8B)

======================================================================================

`tigeropen.common.consts.BarPeriod` [source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/consts/__init__.py)

| 枚举类型 | 枚举值 | 说明  |
| --- | --- | --- |
| DAY | day | 日K  |
| WEEK | week | 周K  |
| MONTH | month | 月K  |
| YEAR | year | 年K  |
| ONE\_MINUTE | 1min | 1分钟 |
| THREE\_MINUTES | 3min | 3分钟 |
| FIVE\_MINUTES | 5min | 5分钟 |
| TEN\_MINUTES | 10min | 10分钟 |
| FIFTEEN\_MINUTES | 15min | 15分钟 |
| HALF\_HOUR | 30min | 30分钟 |
| FORTY\_FIVE\_MINUTES | 45min | 45分钟 |
| ONE\_HOUR | 60min | 60分钟 |
| TWO\_HOURS | 2hour | 2小时 |
| THREE\_HOURS | 3hour | 3小时 |
| FOUR\_HOURS | 4hour | 4小时 |
| SIX\_HOURS | 6hour | 6小时 |

行情权限

[](./appendix-enum.md#%E8%A1%8C%E6%83%85%E6%9D%83%E9%99%90)

==============================================================================================

| 字段  | 说明  |
| --- | --- |
| usQuoteBasic | Nasdaq Basic |
| usStockQuoteLv2Totalview | Nasdaq Basic+TotalView |
| hkStockQuoteLv2 | 港股L2深度行情 |
| usOptionQuote | 期权L1实时行情 |
| NYMEXFuturesQuoteLv2 | 纽约商业交易所L2 |
| HKEXFuturesQuoteLv2 | 香港期货交易所L2 |
| SGXFuturesQuoteLv2 | 新加坡期货交易所L2 |
| OSEFuturesQuoteLv2 | 大阪期货交易所L2 |
| CBOEFuturesQuoteLv2 | 芝加哥期权交易所L2 |

逐笔成交条件描述

[](./appendix-enum.md#%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4%E6%9D%A1%E4%BB%B6%E6%8F%8F%E8%BF%B0)

======================================================================================================================================

| 描述信息 | 市场  | 说明  |
| --- | --- | --- |
| US\_REGULAR\_SALE | 美国  | 自动对盘 |
| US\_BUNCHED\_TRADE | 美国  | 批量交易 |
| US\_CASH\_TRADE | 美国  | 现金交易 |
| US\_INTERMARKET\_SWEEP | 美国  | 跨市场交易 |
| US\_BUNCHED\_SOLD\_TRADE | 美国  | 批量卖出 |
| US\_PRICE\_VARIATION\_TRADE | 美国  | 离价交易 |
| US\_ODD\_LOT\_TRADE | 美国  | 碎股交易 |
| US\_RULE\_127\_OR\_155\_TRADE | 美国  | 纽交所 第127条交易 或 第155条交易 |
| US\_SOLD\_LAST | 美国  | 延迟交易 |
| US\_MARKET\_CENTER\_CLOSE\_PRICE | 美国  | 中央收市价 |
| US\_NEXT\_DAY\_TRADE | 美国  | 隔日交易 |
| US\_MARKET\_CENTER\_OPENING\_TRADE | 美国  | 中央开盘价交易 |
| US\_PRIOR\_REFERENCE\_PRICE | 美国  | 前参考价 |
| US\_MARKET\_CENTER\_OPEN\_PRICE | 美国  | 中央开盘价 |
| US\_SELLER | 美国  | 卖方  |
| US\_FORM\_T | 美国  | 盘前盘后交易 |
| US\_EXTENDED\_TRADING\_HOURS | 美国  | 延长交易时段 |
| US\_CONTINGENT\_TRADE | 美国  | 合单交易 |
| US\_AVERAGE\_PRICE\_TRADE | 美国  | 均价交易 |
| US\_CROSS\_TRADE | 美国  | US\_CROSS\_TRADE |
| US\_SOLD\_OUT\_OF\_SEQUENCE | 美国  | 场外售出 |
| US\_ODD\_LOST\_CROSS\_TRADE | 美国  | 碎股跨市场交易 |
| US\_DERIVATIVELY\_PRICED | 美国  | 衍生工具定价 |
| US\_MARKET\_CENTER\_RE\_OPENING\_TRADE | 美国  | 再开盘定价 |
| US\_MARKET\_CENTER\_CLOSING\_TRADE | 美国  | 收盘定价 |
| US\_QUALIFIED\_CONTINGENT\_TRADE | 美国  | 合单交易 |
| US\_CONSOLIDATED\_LAST\_PRICE\_PER\_LISTING\_PACKET | 美国  | 综合延迟价格 |
| HK\_AUTOMATCH\_NORMAL | 香港  | 自动对盘 |
| HK\_ODD\_LOT\_TRADE | 香港  | 碎股交易 |
| HK\_AUCTION\_TRADE | 香港  | 竞价交易 |
| HK\_OVERSEAS\_TRADE | 香港  | 场外交易 |
| HK\_LATE\_TRADE\_OFF\_EXCHG | 香港  | 开市前成交 |
| HK\_NON\_DIRECT\_OFF\_EXCHG\_TRADE | 香港  | 非自动对盘 |
| HK\_DIRECT\_OFF\_EXCHG\_TRADE | 香港  | 同券商自动对盘 |
| HK\_AUTOMATIC\_INTERNALIZED | 香港  | 同券商非自动对盘 |

期权交易所

[](./appendix-enum.md#%E6%9C%9F%E6%9D%83%E4%BA%A4%E6%98%93%E6%89%80)

========================================================================================================

| 交易所 | 说明  |
| --- | --- |
| AMEX | NYSE MKTOptions Exchange |
| BOX | Boston Options Exchange |
| CBOE | Chicago Board of Options Exchange |
| EMLD | MIAX Emerald |
| EDGX | BATS EDGX |
| GEM | ISE Gemini |
| ISE | International Securities Exchange |
| MCRY | ISE Mercury |
| MIAX | Miami Options Exchange |
| ARCA | NYSE-ARCA Options Exchange |
| MPRL | MIAX-Pearl |
| NSDQ | NASDAQ |
| BX  | NASDAQ OMX BX |
| C2  | CBOE C2 Options |
| PHLX | Philadelphia Options Exchange |
| BZX | CBOE BZX / BATS Options |

  

选股器

[](./appendix-enum.md#%E9%80%89%E8%82%A1%E5%99%A8)

====================================================================================

StockField

[](./appendix-enum.md#stockfield)

--------------------------------------------------------------------------

**选股器-基础指标筛选字段**

Python

    class StockField(FilterField):
        # 最新价*（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间
        CurPrice = 2, "latestPrice"
        # 买入价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间
        BidPrice = 3, "bidPrice"
        # 卖出价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间
        AskPrice = 4, "askPrice"
        # 今开价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间
        OpenPrice = 5, "open"
        # 昨收价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间
        PreClosePrice = 6, "preClose"
        # 最高价
        HighPrice = 7, "high"
        # 最低价
        LowPrice = 8, "low"
        # 盘前价*（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间
        HourTradingPrePrice = 9, "hourTradingPrePrice"
        # 盘后价*（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间
        HourTradingAfterPrice = 10, "hourTradingAfterPrice"
        # 成交量*
        Volume = 11, "volume"
        # 成交额*
        Amount = 12, "amount"
        # 流通股本*
        FloatShare = 13, "floatShares"
        # 52周最高价格*
        Week52High = 14, "week52High"
        # 52周最低价格*
        Week52Low = 15, "week52Low"
        # 通市值* FloatMarketVal  自己计算 FloatShare* 当前价格
        FloatMarketVal = 16, "floatMarketCap"
        # 总市值*  MarketVal  shares * 当前价格
        MarketValue = 17, "marketValue"
        # 盘前涨跌幅   (curPrice-盘前左收）自己计算 最新价-close / close
        preHourTradingChangeRate = 18, "preHourTradingChangeRate"
        # 盘后涨跌幅 自己计算
        postHourTradingChangeRate = 19, "postHourTradingChangeRate"
        # 每股收益 滚动市盈率 TTM=过去12个月  Last Twelve Month  通过hermes获取 eps
        ttm_Eps = 20, "ttmEps"
        # 量比*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        VolumeRatio = 21, "volumeRatio"
        # 委比*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        BidAskRatio = 22, "committee"
        # 下次财报日期 *
        EarningDate = 23, "earningDate"
        # 市盈率* TTM（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        PeTTM = 24, "peRate"
        # 市净率*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        PbRate = 25, "pbRate"
        # 股息   hermes $
        DividePrice = 26, "dividePrice"
        # 股息收益率 选股服务自身计算
        DivideRate = 27, "divideRate"
        # 股票交易市场
        Exchange = 29, "exchange"
        # 换手率*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        TurnoverRate = 30, "turnoverRate"
        # 上市时间
        ListingDate = 31, "listingDate"
        # 市盈率LYR* TTM（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        LyrPeRate = 32, "LyrPeRate"
        # 总股本*
        Share = 33, "shares"
        # 上市价格*
        ListingPrice = 34, "listingPrice"
        # 交易币种*
        TradeCurrency = 35, "tradeCurrency"
        # 最新价-发行价*
        DiffBetweenLastPriceAndListPrice = 36, "DiffBetweenLastPriceAndListPrice"
        # 每股收益 lyr=Last Year Ratio 静态市盈率
        lyr_Eps = 37, "lyrEps"
        # 未平仓做空量
        Open_Short_Interest = 38, "OpenShortInterest"
        # 未平仓做空比例 = 未平仓做空量/总股本
        Open_Short_Interest_Ratio = 39, "OpenShortInterestRatio"
        # 产权比率 = Liability/Equity 总负债/股东
        Equity_Ratio = 40, "EquityRatio"
        # 权益乘数 = Asset/Equity
        Equity_Multiplier = 41, "EquityMultiplier"
        # 最新股东数
        Holder_Nums = 42, "holderNums"
        # 最新股东户数增长率
        Holder_Nums_Ratio = 43, "holderRatio"
        # 户均持股数量
        Per_Hold_Nums = 44, "perHolderNums"
        # 户均持股金额
        Per_Hold_Money = 45, "perHolderMoney"
        # 户均持股数半年增长率
        HalfYear_Holder_Nums_Ratio = 46, "HalfYearholderRatio"
        # 发行时间 - ETF
        InceptionDate = 47, "inceptionDate"
        # 申购费用 - ETF
        CreationFee = 48, "creationFee"
        # 管理费用 - ETF
        ManagementFee = 49, "managementFee"
        # 成分股Top10 占比 - ETF
        Top10_Composition_Rate = 50, "Top10CompoRate"
        # 成分股Top15 占比 - ETF
        Top15_Composition_Rate = 51, "Top15CompoRate"
        # 成分股Top20 占比 - ETF
        Top20_Composition_Rate = 52, "Top20CompoRate"
        # 溢价率(折扣率) - ETF
        DiscountPremium = 53, "discountPremium"
        # 股息率 - ETF
        dividend_Rate = 54, "dividendRate"
        # 资产规模-净值 - ETF
        Net_Worth_Aum = 55, "aum"
        # 资产规模-现价 - ETF
        assetSize = 56, "assetSize"
        # 振幅
        Amplitude = 57, "Amplitude"

AccumulateField

[](./appendix-enum.md#accumulatefield)

------------------------------------------------------------------------------------

**选股器-累积指标筛选字段**

Python

    
    class AccumulateField(FilterField):
        # 涨跌幅*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        ChangeRate = 1, "changeRate"
        # 涨跌额*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        ChangeValue = 2, "change"
        # 总负债增长率
        TotalLiabilities_Ratio_Annual = 3, "totalLiabilitiesRatio"
        # 净资产增长率
        TotalCommonEquity_Ratio_Annual = 4, "totalCommonEquityRatio"
        # 每股收益同比增长率
        BasicEps_Ratio_Annual = 5, "basicEpsRatio"
        # 净利润同比增长率
        NetIncome_Ratio_Annual = 6, "netIncomeRatio"
        # 营业利润同比增长率
        OperatingIncome_Ratio_Annual = 7, "opeIncomeratio"
        # 每股收益
        Eps = 8, "eps"
        # 每股净资产
        NetAsset_PerShare = 9, "bookValueshare"
        # 净利润
        Net_Income = 10, "netIncome"
        # 营业利润
        Operating_Income = 11, "operatingIncome"
        # 营业收入
        Total_Revenue = 12, "total_revenue"
        # ROE = 资产回报率
        ROE = 13, "ROE"
        # ROA = 净资产收益率
        ROA = 14, "ROA"
        # 股息   hermes $
        DividePrice = 15, "dividePrice"
        # 股息收益率 选股服务自身计算
        DivideRate = 16, "divideRate"
        # 毛利率
        GrossProfitRate = 17, "grossMargin"
        # 净利率*
        NetProfitRate = 18, "netIncomeMargin"
        # 总资产*
        TotalAssets = 19, "totalAssets"
        # 流动比率
        CurrentRatio = 20, "currentRatio"
        # 速动比率
        QuickRatio = 21, "quickRatio"
        # 经营现金流
        CashFromOps = 22, "cash4Ops"
        # 投资现金流
        CashFromInvesting = 23, "cash4Invest"
        # 筹资现金流
        CashFromFinancing = 24, "cash4Finance"
        # 资产负债率
        TotalLiabilitiesToTotalAssets = 25, "allLiabAndAssets"
        # 经营现金流同比增长率; （T期CFO-T-1期CFO）/T-1期CFO *100%
        CashFromOps_yearOnYear_Ratio = 26, "cash4OpsYearOnYearRatio"
        # 净资产收益率ROE同比增长率  （T期ROE-T-1期ROE）/T-1期ROE *100%
        ROE_yearOnYear_Ratio = 27, "netIncomeYearOnYearRatio"
    

FinancialField

[](./appendix-enum.md#financialfield)

----------------------------------------------------------------------------------

**选股器-财务指标筛选字段**

Python

    
    class FinancialField(FilterField):
        # 毛利率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        GrossProfitRate = 1, "grossMargin"
        # 净利率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        NetProfitRate = 2, "netIncomeMargin"
        # 扣非净利润率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        EarningsFromContOpsMargin = 3, "earningsFromContOpsMargin"
        # 总负债/股东权益 (单位：元)
        TotalDebtToEquity = 4, "totalDebtToEquity"
        # 长期负债/股东权益
        LongTermDebtToEquity = 5, "ltDebtToEquity"
        # EBIT/利息支出
        EbitToInterestExp = 6, "ebitToInterestExp"
        # 总负债/总资产
        TotalLiabilitiesToTotalAssets = 7, "totalLiabilitiesToTotalAssets"
        # 总资产周转率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        TotalAssetTurnover = 8, "totalAssetTurnover"
        # 应收帐款周转率
        AccountsReceivableTurnover = 9, "accountsReceivableTurnover"
        # 存货周转率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        InventoryTurnover = 10, "inventoryTurnover"
        # 流动比率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        CurrentRatio = 11, "currentRatio"
        # 速动比率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        QuickRatio = 12, "quickRatio"
        # 资产回报率 总资产收益率 *$ TTM（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        ROATTM = 13, "roa"
        # 净资产收益率 $（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        ReturnOnEquityRate = 14, "roe"
        # 营业收入一年增长率 或者 营收增长率
        TotalRevenues1YrGrowth = 15, "totalRevenues1YrGrowth"
        # 毛利润率一年增长率  营业利润增长率
        GrossProfit1YrGrowth = 16, "grossProfit1YrGrowth"
        # 净利润一年增长率
        NetIncome1YrGrowth = 17, "netIncome1YrGrowth"
        # 应收帐款一年增长率
        AccountsReceivable1YrGrowth = 18, "accountsReceivable1YrGrowth"
        # 存货一年增长率
        Inventory1YrGrowth = 19, "inventory1YrGrowth"
        # 总资产一年增长率
        TotalAssets1YrGrowth = 20, "totalAssets1YrGrowth"
        # 有形资产一年增长率
        TangibleBookValue1YrGrowth = 21, "tangibleBookValue1YrGrowth"
        # 经营现金流一年增长率
        CashFromOperations1YrGrowth = 22, "cashFromOperations1YrGrowth"
        # 资本开支一年增长率
        CapitalExpenditures1YrGrowth = 23, "capitalExpenditures1YrGrowth"
        # 营业收入三年增长率 或者叫 营收3年复合增长率
        TotalRevenues3YrCagr = 24, "totalRevenues3YrCagr"
        # 毛利润率三年增长率
        GrossProfit3YrCagr = 25, "grossProfit3YrCagr"
        # 净利润三年增长率
        NetIncome3YrCagr = 26, "netIncome3YrCagr"
        # 应收帐款三年增长率
        AccountsReceivable3YrCagr = 27, "accountsReceivable3YrCagr"
        # 存货三年增长率
        Inventory3YrCagr = 28, "inventory3YrCagr"
        # 总资产三年增长率
        TotalAssets3YrCagr = 29, "totalAssets3YrCagr"
        # 有形资产三年增长率
        TangibleBookValue3YrCagr = 30, "tangibleBookValue3YrCagr"
        # 经营现金流三年增长率
        CashFromOps3YrCagr = 31, "cashFromOps3YrCagr"
        # 资本开支三年增长率
        CapitalExpenditures3YrCagr = 32, "capitalExpenditures3YrCagr"
        # 净利润
        NetIncomeToCompany = 33, "netIncomeToCompany"
        # 经营现金流
        CashFromOperations = 34, "cashFromOps"
        # 投资现金流
        CashFromInvesting = 35, "cashFromInvesting"
        # 筹资现金流
        CashFromFinancing = 36, "cashFromFinancing"
        # 净利润2年复合增长率
        NormalizedNetIncome2YrCagr = 37, "normalizedNetIncome2YrCagr"
        # 营收2年复合增长率
        TotalRevenues2YrCagr = 38, "totalRevenues2YrCagr"
        # 净利润5年复合增长率
        NetIncome5YrCagr = 39, "netIncome5YrCagr"
        # 营收5年复合增长率
        TotalRevenues5YrCagr = 40, "totalRevenues5YrCagr"
        # 总资产
        TotalAssets = 41, "totalAssets"
        # 固定资产周转率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间
        FixedAssetTurnover = 42, "fixedAssetTurnover"
        # 营业利润
        OperatingIncome = 43, "operatingIncome"
        # 营业总收入
        TotalRevenue = 44, "totalRevenue"
        # 市盈率LYR PE =price-to-earnings ratio
        LYR_PE = 45, "LyrPE"
        # 市盈率TTM PE =price-to-earnings ratio
        TTM_PE = 46, "ttmPE"
        # 市销率LYR PS =Price-to-sales Ratio
        LYR_PS = 47, "LyrPS"
        # 市销率TTM PS =Price-to-sales Ratio
        TTM_PS = 48, "ttmPS"
        # 市净率LYR PB =price/book value ratio
        LYR_PB = 47, "LyrPB"
        # 市净率TTM PB =price/book value ratio
        TTM_PB = 48, "ttmPB"
        # 当日主力净流入额
        LargeInflowAmountToday = 49, "largeInflowAmountToday"
        # 当日主力增仓占比
        LargeInflowAmountTodayPre = 50, "largeInflowAmountTodayPre"
        # 未平仓做空量
        ShortInterest = 51, "shortInterest"
        # 未平仓做空比例
        ShortInterestPre = 52, "shortInterestPre"
        # 港股通持股比例=港股通(深)持股比例=港股通(沪)持股比例
        HK_StockConnectRate = 53, "hkStockConnectRate"
        # 沪股通持股比例
        SH_StockConnectRate = 54, "shStockConnectRate"
        # 深股通持股比例
        SZ_StockConnectRate = 55, "szStockConnectRate"
        # 营业利润占比
        Operating_Profits_Rate = 56, "operatingProfitsRate"
        # 港股通(沪)净买入额
        HK_StockShConnectInflow = 57, "hkStockShConnectInflow"
        # 港股通(深)净买入额
        HK_StockSzConnectInflow = 58, "hkStockSzConnectInflow"
        # 沪股通净买入额
        SH_StockConnectInflow = 59, "shStockConnectInflow"
        # 深股通净买入额
        SZ_StockConnectInflow = 60, "szStockConnectInflow"
        # 上市以来年化收益率 ETF
        ListingAnnualReturn = 61, "listingAnnualReturn"
        # 近1年年化收益率  ETF
        LstYearAnnualReturn = 62, "lstYearAnnualReturn"
        # 近2年年化收益率  ETF
        Lst2YearAnnualReturn = 63, "lst2YearAnnualReturn"
        # 近5年年化收益率  ETF
        Lst5YearAnnualReturn = 64, "lst5YearAnnualReturn"
        # 上市以来年化波动率  ETF
        ListingAnnualVolatility = 65, "listingAnnualVolatility"
        # 近1年年化波动率  ETF
        LstYearAnnualVolatility = 66, "lstYearAnnualVolatility"
        # 近2年年化波动率  ETF
        Lst2YearAnnualVolatility = 67, "lst2YearAnnualVolatility"
        # 近5年年化波动率  ETF
        Lst5YearAnnualVolatility = 68, "lst5YearAnnualVolatility"
    

MultiTagField

[](./appendix-enum.md#multitagfield)

--------------------------------------------------------------------------------

**选股器-多标签筛选字段**

Python

    
    class MultiTagField(FilterField):
        # 所属行业
        Industry = 1, "industry"
        # 所属概念
        Concept = 2, "concept"
        # 是否为otc股票.1=是，0=否
        isOTC = 3, "isOTC"
        StockCode = 4, "symbol"
        # 股票类型 stock or etf ;股票类型,非0表示该股票是ETF,1表示不带杠杆的etf,2表示2倍杠杆etf,3表示3倍etf杠杆,负值表示反向的ETF
        Type = 5, "type"
        # 成交量异常.1=是，0=否 ;当日实时成交量> 5* 最近一年的平均成交量
        Volume_Spike = 6, "volSpike"
        # 破净股票；市净率PB<1
        Net_Broken = 7, "netBroken"
        # 破发股票 ； 最新价<发行价
        Issue_Price_Broken = 8, "issuePriceBroken"
        # 跟踪指数/资产 - ETF
        PrimaryBenchmark = 9, "primaryBenchmark"
        # 发行人 - ETF
        Issuer = 10, "issuer"
        # 托管人 - ETF
        Custodian = 11, "custodian"
        # 分红频率 - ETF
        DistributionFrequency = 12, "distributionFrequency"
        # 是否支持期权 - ETF ; 1=是，0=否
        OptionsAvailable = 13, "optionsAvailable"
        # 今日创历史新高 - ETF 1=是，0=否
        Today_HistoryHigh = 14, "todayHistoryHigh"
        # 今日创历史新低 - ETF 1=是，0=否
        Today_HistoryLow = 15, "todayHistoryLow"
        # 股票包
        Stock_Package = 16, "StockPkg"
        # 52周最高 0 否 1是*
        Week52HighFlag = 17, "week52HighFlag"
        # 52周最低 0 否 1是
        Week52LowFlag = 18, "week52LowFlag"
    

SortDirection

[](./appendix-enum.md#sortdirection)

--------------------------------------------------------------------------------

**选股器-排序方向**

Python

    class SortDirection(Enum):
        NO = 'SortDir_No'  # 不排序
        ASC = 'SortDir_Ascend'  # 升序
        DESC = 'SortDir_Descend'  # 降序

  

AssetQuoteType

[](./appendix-enum.md#assetquotetype)

----------------------------------------------------------------------------------

**资产计价行情类型**

Python

    class AssetQuoteType(Enum):
        # 包含盘前、盘中、盘后行情，夜盘时段使用T-1日盘后收盘价计算
        ETH = "ETH"
        # 仅盘中行情，盘前、盘后、夜盘时段使用盘中收盘价计算
        RTH = "RTH"
        # 只包含夜盘交易数据。盘前、盘中、盘后交易使用夜盘收盘价计算。
        OVERNIGHT = "OVERNIGHT"

  
