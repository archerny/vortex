# 对象

PortfolioAccount 资产(综合/模拟账户)

[](./appendix-object-detail.md#portfolioaccount-%E8%B5%84%E4%BA%A7%E7%BB%BC%E5%90%88%E6%A8%A1%E6%8B%9F%E8%B4%A6%E6%88%B7)

====================================================================================================================================================================================

**说明**

账户资产对象，适用于综合/模拟账户。包含账户的总资产、盈亏、持仓市值、现金、可用资金、保证金、杠杆等相关信息。

**对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 对应的账户 id |
| update\_timestamp | int | 更新时间, 毫秒为单位的13位数字时间戳 |
| segments | tigeropen.trade.domain.prime\_account.Segment | 按照交易品种区分的账户信息。内容是一个dict，分别有两个key，'S'表示证券，'C' 表示期货，value均为[Segment对象](./appendix-object-detail.md#segment-%E5%88%86%E5%93%81%E7%A7%8D%E8%B5%84%E4%BA%A7%E7%BB%BC%E5%90%88%E6%A8%A1%E6%8B%9F%E8%B4%A6%E6%88%B7) |

  

Segment 分品种资产(综合/模拟账户)

[](./appendix-object-detail.md#segment-%E5%88%86%E5%93%81%E7%A7%8D%E8%B5%84%E4%BA%A7%E7%BB%BC%E5%90%88%E6%A8%A1%E6%8B%9F%E8%B4%A6%E6%88%B7)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**说明**

将资产按照股票/期货交易品种划分，每部分为一个Segment。

**对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| currency | str | 币种, 如 USD, HKD |
| capability | str | 账户类型, 保证金账户: RegTMargin, 现金账户: Cash。保证金账户支持融资融券功能，T+0交易次数不受限制，最大购买力于日内最高4倍，隔日最高2倍。 |
| category | str | 交易品种分类 C: (Commodities 期货), S: (Securities 股票), F: (基金) |
| cash\_balance | float | 现金额。现金额就是当前所有币种的现金余额之和。如果您当前帐户产生了融资或借款，需要注意的是利息一般是按天算，按月结，具体以融资天数计算。每日累计，下个月初5号左右统一扣除，所以在扣除利息之前，用户看到的现金余额是没有扣除利息的，如果扣除利息前现金余额为零，可能扣除后会产生欠款，即现金余额变为负值。 |
| cash\_available\_for\_trade | float | 可用资金。可用资金用来检查是否可以**开仓**或打新。开仓是指做多买入股票、做空融券卖出股票等交易行为。需要注意的是可用资金不等于可用现金，是用总资产、期权持仓市值、冻结资金和初始保证金等指标算出来的一个值，当可用资金大于0时，代表该账户可以开仓，可用资金\_4 为账户的最大可用购买力。算法，可用资金=总资产-美股期权市值-当前总持仓的初始保证金-冻结资金。其中初始保证金的算法是∑（持仓个股市值\_当前股票的开仓保证金比例）。举例：小虎当前账户总资产为10000美元，持有1000美元美股期权，持有总市值为2000美元的苹果，苹果的初始保证金比例当前为45%，没有冻结资金，小虎的可用资金=10000-1000-2000\*45%=8100美元 |
| cash\_available\_for\_withdrawal | float | 当前账号内可以出金的现金金额 |
| buying\_power | float | 最大购买力。 最大购买力是账户最大的可用购买金额。可以用最大购买力的值来估算账户最多可以买多少金额的股票，但是由于每只股票的最多可加的杠杆倍数不一样，所以实际购买单支股票时可用的购买力要根据具体股票保证金比例来计算。算法，最大购买力=4\*可用资金。举例：小虎的可用资金是10万美元，那么小虎的最大购买力是40万美元，假设当前苹果股价是250美元一股，苹果的初始保证金比例是30%，小虎最多可以买100000/30%=33.34万美金的苹果，假设苹果的初始保证金比例为25%，小虎最多可以买100000/25%=40万美金的苹果。保证金账户日内最多有四倍于资金（未被占用做保证金的资金）的购买力 隔夜最多有两倍的购买力 |
| gross\_position\_value | float | 证券总价值，是账户持仓证券的总市值之和，即全部持仓的总市值。算法，持仓证券总市值之和；备注：所有持仓市值都会按主币种计算；举例1，若小虎同时持有市值3000美元苹果（也就是做多），持有市值-1000美元的谷歌（也就是做空），小虎证券总价值=3000美元苹果+（-1000美元谷歌）=2000美元；举例2，小虎持有市值1万美元的苹果，市值5000美元的苹果做多期权，小虎证券总价值=10000+5000=15000美元 |
| equity\_with\_loan | float | 含贷款价值总权益，即ELV，ELV是用来计算开仓和平仓的数据指标；算法，现金账户=现金余额，保证金账户=现金余额+证券总市值-美股期权市值；ELV = 总资产 - 美股期权 |
| net\_liquidation | float | 总资产(净清算值)。总资产就是我们账户的净清算现金余额和证券总市值之和，通常用来表示目前账户中有多少资产。算法，总资产=证券总市值+现金余额+应计分红-应计利息；举例：小虎账户有1000美元现金，持仓价值1000美元的苹果（即做多苹果），没有待发放的股息和融资利息和未扣除的利息，那么小虎的总资产=现金1000+持仓价值1000=2000美元，若小虎账户有1000美元现金，做空1000美元的苹果，此时证券总市值-1000美元，现金2000美元，用户总资产=现金2000+（持仓市值-1000），账户总资产共计1000美元。 |
| init\_margin | float | 初始保证金。当前所有持仓合约所需的初始保证金要求之和。初次执行交易时，只有当含贷款价值总权益大于初始保证金才允许开仓。为满足监管机构的保证金要求，我们会在临近收盘前15分钟提升初始保证金与维持保证金要求至最低50%。 |
| maintain\_margin | float | 维持保证金。当前所有持仓合约所需的维持保证金要求之和。持有头寸时，当含贷款价值总权益小于维持保证金会引发强平。为满足监管机构的保证金要求，我们会在临近收盘前15分钟提升初始保证金与维持保证金要求至最低50%。 |
| overnight\_margin | float | 隔夜保证金。隔夜保证金是在收盘前15分钟开始检查账户所需的保证金，为满足监管机构的保证金要求，我们会在临近收盘前15分钟提升初始保证金与维持保证金要求至最低50%。老虎国际的隔夜保证金比例均在50%以上。如果账户含货款价值总权益低于隔夜保证金，账户在收盘前15分钟存在被强制平仓的风险。算法，∑（持仓个股隔夜时段的维持保证金）。个股的维持保证金率用户能够在“个股详情页-报价区-点击融资融券标识"查询。举例：小虎账户总资产为10万美元，苹果开仓保证金比例是40%，日内维持保证金是20%，日内全仓买入苹果共25万美元，到收盘前15分钟（隔夜时段）维持保证金比例将被提高到50%，此时的用户隔夜保证金为：25万\*50%=12.5万，用户的含贷款价值总权益为10万小于12.5万，用户将会被平仓部分股票。 |
| unrealized\_pl | float | 持仓盈亏。定义，持仓个股、衍生品的未实现盈亏金额；算法，当前价\*股数-持仓成本 |
| realized\_pl | float | 已实现盈亏是当前持仓周期内已减仓的个股、衍生品的已实现盈亏总和。已实现盈亏= Σ【（卖出价-买入价）\*卖出股数-手续费 |
| excess\_liquidation | float | 当前剩余流动性。当前剩余流动性是衡量当前账户潜在的被平仓风险的指标，当前剩余流动性越低账户被平仓的风险越高，当小于0时会被强制平掉部分持仓。具体的算法为：当前剩余流动性=含货款价值总权益(equity\_with\_loan)-账户维持保证金(maintain\_margin) 。为满足监管机构的保证金要求，我们会在临近收盘前15分钟提升初始保证金与维持保证金要求至最低50%。举例：(1)、客户总资产1万美金，买入苹果12000美金（假设苹果开仓和维持保证金比例为50%）。当前剩余流动性=总资产10000-账户维持保证金6000=4000。(2)、随着股票的下跌，假设股票市值跌到8000，这个时候当前剩余流动性=总资产 （-2000+8000）-账户维持保证金4000=2000。(3)、此时用户又买入了1000美金的美股期权，那么账户的当前剩余流动性还剩下2000-1000=1000。若您的账户被强制平仓，则会以市价单在强制平仓时进行成交，强平的股票对象由券商自行决定，请您注意风控值和杠杆等指标。 |
| overnight\_liquidation | float | 隔夜剩余流动性。隔夜剩余流动性是指用 含货款价值总权益(equity\_with\_loan)-隔夜保证金(overnight\_margin) 算出来的值。为满足监管机构的保证金要求，我们会在临近收盘前15分钟提升初始保证金与维持保证金要求至最低50%。如果账户的隔夜剩余流动性低于0，在收盘前15分钟起账户存在被强行平掉部分持仓的风险。若您的账户被强制平仓，则会以市价单在强制平仓时进行成交，强平的股票对象由券商自行决定，请您注意风控值和杠杆等指标。 |
| leverage | float | 杠杆。杠杆是衡量账户风险程度的重要指标，可以帮助用户快速了解账户融资比例和风险程度；算法，杠杆=证券市值绝对值之和/总资产；备注1，保证金账户日内最大杠杆4倍，隔夜2倍；备注2，老虎考虑到历史波动、流动性和风险等因素，并不是每只股票都能4倍杠杆买入，一般做多保证金比例范围在25%-100%之间，保证金比例等于25%的股票，可以理解为4倍杠杆买入，保证金比例等于100%的股票，可以理解为0倍杠杆买入，即全部用现金买入。需要留意做空保证金可能会大于100%。备注3，个股保证金比例用户能够在“个股详情页-报价区-点击融资融券标识”查询；举例，小虎账户总资产10万美元，想买苹果，苹果当前个股做多初始保证金比例50%（1/50%=2倍杠杆），小虎最多只能买入20万市值的苹果股票；小虎想做空谷歌，谷歌做空保证金比例200%，小虎最多能做空10/200%=5万谷歌股票；小虎想买入微软，微软初始保证金100%（1/100%=1倍杠杆），小虎最多只能买入10万美元的微软股票 |
| currency\_assets | dict | 按照交易币种区分的账户资产信息，是以币种为 key 的 dict, 值为 CurrencyAsset 对象 |
| uncollected | float | 在途资金 |
| locked\_funds | float | 锁定资金 |

  

CurrencyAsset 分币种资产(综合/模拟账户)

[](./appendix-object-detail.md#currencyasset-%E5%88%86%E5%B8%81%E7%A7%8D%E8%B5%84%E4%BA%A7%E7%BB%BC%E5%90%88%E6%A8%A1%E6%8B%9F%E8%B4%A6%E6%88%B7)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**说明**

按币种区分的资产。

**对象属性**

| 属性名 | 类型  | 描述  | 备注  |
| --- | --- | --- | --- |
| currency | str | 当前的货币币种，常用货币包括： USD-美元，HKD-港币，SGD-新加坡币，CNH-人民币 |     |
| cash\_balance | float | 可以交易的现金，加上已锁定部分的现金（如已购买但还未成交的股票，还包括其他一些情形也会有锁定现金情况） |     |
| cash\_available\_for\_trade | float | 当前账号内可以交易的现金金额 |     |
| forex\_rate | float | 当前币种对 base\_currency 的汇率。比如 base\_currency=USD, currency=HKD, forex\_rate=0.128 | 3.5.0 新增 |

  

PortfolioAccount 资产(环球账户)

[](./appendix-object-detail.md#portfolioaccount-%E8%B5%84%E4%BA%A7%E7%8E%AF%E7%90%83%E8%B4%A6%E6%88%B7)

===============================================================================================================================================================

**说明**

账户资产对象，适用于环球账户。包含账户的总资产、盈亏、持仓市值、现金、可用资金、保证金、杠杆等相关信息

**对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 对应的账户 id |
| summary | tigeropen.trade.domain.account.Account | 账户汇总信息，对segments的统计 |
| segments | dict | 按照交易品种区分的账户信息。分别有两个key，'S'对应证券，value为一个 |

  

Account 汇总资产(环球账户)

[](./appendix-object-detail.md#account-%E6%B1%87%E6%80%BB%E8%B5%84%E4%BA%A7%E7%8E%AF%E7%90%83%E8%B4%A6%E6%88%B7)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------

**说明**

账户各交易品种资产汇总信息。

**对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| accrued\_cash | float | 当前月份的累积应付利息，按照日频更新 |
| accrued\_dividend | float | 累计分红. 指的是所有已执行但仍未支付的分红累加值 |
| available\_funds | float | 可用资金（可用于交易）。 计算方法为 equity\_with\_loan - initial\_margin\_requirement |
| ∆ buying\_power | float | 购买力: 预估您还可以购入多少美元的股票资产。保证金账户日内最多有四倍于资金（未被占用做保证金的资金）的购买力。隔夜最多有两倍的购买力 |
| cash | float | 现金量 |
| currency | str | 币种, 含义参考枚举参数-币种 |
| cushion | float | 剩余流动性占总资产的比例，计算方法为:excess\_liquidity/net\_liquidation |
| ∆ day\_trades\_remaining | int | 当日剩余日内交易次数， -1 表示无限制 |
| equity\_with\_loan | float | 含借贷值股权(含贷款价值资产) 。 证券 Segment: 现金价值 + 股票价值 。 期货 Segment: 现金价值 - 维持保证金 |
| excess\_liquidity | float | 剩余流动性- 证券 Segment: 计算方法: equity\_with\_loan - maintenance\_margin\_requirement- 期货 Segment: 计算方法: net\_liquidation - maintenance\_margin\_requirement |
| ∆ gross\_position\_value | float | 证券总价值: 做多股票的价值+做空股票价值+做多期权价值+做空期权价值。 |
| initial\_margin\_requirement | float | 初始保证金 |
| maintenance\_margin\_requirement | float | 维持保证金 |
| realized\_pnl | float | 本日已实现盈亏 |
| unrealized\_pnl | float | 浮动盈亏 |
| net\_liquidation | float | 总资产(净清算价值)。 证券 Segment: 现金价值 + 股票价值 + 股票期权价值。 期货 Segment: 现金价值 + 盯市盈亏 |
| ∆ regt\_equity | float | 仅针对证券Segment，即根据 Regulation T 法案计算的 equity with loan（含借贷股权值） |
| ∆ regt\_margin | float | 仅针对证券Segment， 即根据 Regulation T 法案计算的 initial margin requirements（初始保证金） |
| ∆ sma | float | 仅针对证券Segment。隔夜风控值，每个交易日收盘前10分钟左右对账户持仓的隔夜风险进行检查，隔夜风控值需要大于0，否则会在收盘前对账户部分头寸强制平仓。如果交易日盘中出现隔夜风控值低于0，而时间未到收盘前10分钟，账户不会出发强平。 |
| timestamp | int | 更新时间 |

  

SecuritySegment 股票资产(环球账户)

[](./appendix-object-detail.md#securitysegment-%E8%82%A1%E7%A5%A8%E8%B5%84%E4%BA%A7%E7%8E%AF%E7%90%83%E8%B4%A6%E6%88%B7)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**说明**

股票资产信息。

**对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| accrued\_cash | float | 当前月份的累积应付利息，按照日频更新。 |
| accrued\_dividend | float | 累计分红. 指的是所有已执行但仍未支付的分红累加值 |
| available\_funds | float | 可用资金（可用于交易）。 计算方法为 equity\_with\_loan - initial\_margin\_requirement |
| cash | float | 现金  |
| equity\_with\_loan | float | 含借贷值股权(含贷款价值资产)。计算方法： 现金价值 + 股票价值 |
| excess\_liquidity | float | 剩余流动性。计算方法: equity\_with\_loan - maintenance\_margin\_requirement |
| gross\_position\_value | float | 证券总价值: 做多股票的价值+做空股票价值+做多期权价值+做空期权价值。 |
| initial\_margin\_requirement | float | 初始保证金 |
| maintenance\_margin\_requirement | float维持保证金 |     |
| leverage | float | 仅用于证券 Segment gross\_position\_value / net\_liquidation |
| net\_liquidation | float | 总资产(净清算价值)。 计算方法： 现金价值 + 股票价值 + 股票期权价值 |
| ∆ regt\_equity | float | 仅针对证券Segment，即根据 Regulation T 法案计算的 equity with loan（含借贷股权值） |
| ∆ regt\_margin | float | 仅针对证券Segment， 即根据 Regulation T 法案计算的 initial margin requirements（初始保证金） |
| ∆ sma | float | 仅针对证券Segment。隔夜风控值，每个交易日收盘前10分钟左右对账户持仓的隔夜风险进行检查，隔夜风控值需要大于0，否则会在收盘前对账户部分头寸强制平仓。如果交易日盘中出现隔夜风控值低于0，而时间未到收盘前10分钟，账户不会出发强平。 |
| timestamp | int | 更新时间 |

  

CommoditySegment 期货资产(环球账户)

[](./appendix-object-detail.md#commoditysegment-%E6%9C%9F%E8%B4%A7%E8%B5%84%E4%BA%A7%E7%8E%AF%E7%90%83%E8%B4%A6%E6%88%B7)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**说明**

期货资产信息。

**对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| accrued\_cash | float | 当前月份的累积应付利息，按照日频更新。 |
| accrued\_dividend | float | 累计分红. 指的是所有已执行但仍未支付的分红累加值 |
| available\_funds | float | 可用资金（可用于交易）。 计算方法为 equity\_with\_loan - initial\_margin\_requirement |
| cash | float | 现金  |
| equity\_with\_loan | float | 含借贷值股权(含贷款价值资产)计算方法：现金价值 - 维持保证金 |
| excess\_liquidity | float | 剩余流动性。计算方法: net\_liquidation - maintenance\_margin\_requirement |
| initial\_margin\_requirement | float | 初始保证金 |
| maintenance\_margin\_requirement | float | 维持保证金 |
| net\_liquidation | float | 总资产(净清算价值)。计算方法：现金价值 + 盯市盈亏 |
| timestamp | int | 更新时间 |

  

MarketValue 分币种资产(环球账户)

[](./appendix-object-detail.md#marketvalue-%E5%88%86%E5%B8%81%E7%A7%8D%E8%B5%84%E4%BA%A7%E7%8E%AF%E7%90%83%E8%B4%A6%E6%88%B7)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**说明**

按币种区分的资产信息。

**对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| currency | str | 货币单位 |
| net\_liquidation | float | 总资产(净清算价值) |
| cash\_balance | float | 现金  |
| stock\_market\_value | float | 股票市值 |
| option\_market\_value | float | 期权市值 |
| warrant\_value | float | 窝轮市值 |
| futures\_pnl | float | 盯市盈亏 |
| unrealized\_pnl | float | 未实现盈亏 |
| realized\_pnl | float | 已实现盈亏 |
| exchange\_rate | float | 对账户主币种的汇率 |
| net\_dividend | float | 应付股息与应收股息的净值 |
| timestamp | int | 更新时间 |

  

Position 持仓

[](./appendix-object-detail.md#position-%E6%8C%81%E4%BB%93)

=====================================================================================================

**说明**

账户持仓信息。包括持仓的合约标的、持仓数量、成本、盈亏等信息。

**对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 对应的账户ID |
| contract | tigeropen.trade.domain.contract.Contract | 合约对象 |
| position\_qty | float | 持仓数量 |
| quantity | int | 持仓数量 (废弃)，在实际持仓有小数时需要结合 position\_scale 处理 |
| position\_scale | int | 持仓数量小数偏移 (废弃) 如 position=11123， positionScale=2，那么实际 position=11123\*10^(-2)=111.23 |
| average\_cost | float | 含佣金的平均成本 |
| market\_value | float | 市值  |
| salable\_qty | float | 可卖数量 |
| average\_cost\_of\_carry | float | 累计持仓成本(A股模式计算方式) |
| market\_price | float | 市价  |
| is\_level0\_price | boolean | 是否为lv0（延迟）行情 |
| realized\_pnl | float | fifo模式下已实现盈亏 |
| unrealized\_pnl | float | 浮动盈亏 |
| unrealized\_pnl\_by\_cost\_of\_carry | float | 浮动盈亏(A股模式计算方式) |
| unrealized\_pnl\_percent\_by\_cost\_of\_carry | float | 浮动盈亏率(A股模式计算方式) |
| today\_pnl | float | 今日盈亏额 |
| today\_pnl\_percent | float | 今日盈亏率 |
| yesterday\_pnl | float | 基金昨日盈亏 |
| last\_close\_price | float | 最后盘中收盘价(前复权)，美股盘中为上个交易日的收盘价 |

  

Order 订单

[](./appendix-object-detail.md#order-%E8%AE%A2%E5%8D%95)

===============================================================================================

**说明**

订单对象。查询订单会返回该对象，下单改单的参数也需要使用该对象。

**对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 订单所属的账户 |
| id  | long | 全局订单 id |
| order\_id | int | 账户自增订单号，已废弃 |
| parent\_id | long | 母订单id，目前只用于 TigerTrade App端的附加订单中 |
| order\_time | int | 下单时间，毫秒单位13位数字时间戳 |
| trade\_time | int | 订单状态更新时间，对于已成交订单，代表成交时间，对于已撤销订单，代表成功撤销的时间，毫秒单位13位数字时间戳 |
| update\_time | int | 订单更新时间, 订单的属性发生变化时(比如改单改价), 此时间会更新，毫秒单位13位数字时间戳 |
| expire\_time | int | GTD订单过期时间, 毫秒单位13位数字时间戳 |
| reason | str | 下单失败时，会返回失败原因的描述 |
| action | str | 交易方向， 'BUY' / 'SELL' |
| quantity | int | 下单数量 |
| quantity\_scale | int | 下单数量的偏移量，默认为0。碎股单的 quantity 和 quantity\_scale 结合起来代表真实下单数量，如 quantity=111 quantity\_scale=2，那么真实 quantity=111\*10^(-2)=1.11 |
| total\_cash\_amount | float | 下单总金额，按股数下单时为 None |
| filled\_cash\_amount | float | 已成交金额，按股数下单时为 None |
| refund\_cash\_amount | float | 退回金额，等于下单总金额-已成交金额。按股数下单或订单未终结时为 None |
| filled | int | 成交数量 |
| avg\_fill\_price | float | 不含佣金的平均成交价 |
| commission | float | 包含佣金、印花税、证监会费等系列费用 |
| gst | float | 税费 Goods and Service Tax |
| realized\_pnl | float | 实现盈亏 |
| trail\_stop\_price | float | 跟踪止损价格 |
| limit\_price | float | 限价单价格 |
| aux\_price | float | 在止损单中，表示出发止损单的价格， 在移动止损单中， 表示跟踪的价差 |
| trailing\_percent | float | 跟踪止损单-百分比，取值范围为0-100 |
| percent\_offset | float | <该字段未使用> |
| order\_type | str | 订单类型, 'MKT'市价单/'LMT'限价单/'STP'止损单/'STP\_LMT'止损限价单/'TRAIL'跟踪止损单 |
| time\_in\_force | str | 有效期,'DAY'日内有效/'GTC'撤销前有效/'GTD'日期前有效(需额外指定expire\_time) |
| outside\_rth | bool | 是否支持盘前盘后交易，美股专属。 |
| trading\_session\_type | str | 夜盘交易. |
| contract | Contract |     |

`tigeropen.trade.domain.order.Charge` 对象属性

| 属性  | 类型  | 描述  |
| --- | --- | --- |
| category | str | 费用类型（TIGER/THIRD\_PARTY） |
| category\_desc | str | 费用类别描述：Tiger Charge; Third Parties |
| total | float | 当前类别费用总额 |
| details | tigeropen.trade.domain.order.ChargeDetail | 费用明细 |

`tigeropen.trade.domain.order.ChargeDetail` 对象属性

| 属性  | 类型  | 描述  |
| --- | --- | --- |
| type | str | 费用类型：SETTLEMENT\_FEE/STAMP\_DUTY/TRANSACTION\_LEVY/EXCHANGE\_FEE/FRC\_TRANSACTION\_LEVY |
| type\_desc | str | 费用类型描述：Settlement Fee(结算费); Stamp Duty（印花税）; Transaction Levy（交易征费）; Exchange Fee（交易所费用）; AFRC Transaction Levy（会计及才会局交易征费） |
| original\_amount | float | 费用金额 |
| after\_discount\_amount | float | 抵扣后的费用 |

\*\*构建方法 \*\*：

通过 SDK 中的 [tigeropen.common.util.order\_utils](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/common/util/order_utils.py)
 在本地生成订单对象： order\_utils 仅提供了常用的参数，如果需要额外的参数，可生成订单对象后，修改其属性即可

Python

    from tigeropen.common.util.contract_utils import stock_contract
    from tigeropen.common.util.order_utils import (market_order,        # 市价单
                                                limit_order,         # 限价单
                                                stop_order,          # 止损单
                                                stop_limit_order,    # 限价止损单
                                                trail_order,         # 移动止损单
                                                order_leg)           # 附加订单
                            
    contract = stock_contract('AAPL', currency='USD')
    order = limit_order('your account', contract, 'BUY', 100, 150.5)
    order.time_in_force = 'GTC' # 设置订单属性
         
    # 后续操作...                                    

  

market\_order 市价单

[](./appendix-object-detail.md#market_order-%E5%B8%82%E4%BB%B7%E5%8D%95)

------------------------------------------------------------------------------------------------------------------------

Python

    market_order(account, contract, action, quantity)

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 下单账户， 可以使用综合账户、环球账户或模拟账户 |
| contract | tigeropen.trade.domain.contract.Contract | 要交易的 |

**返回**

`Order` 对象

  

limit\_order 限价单

[](./appendix-object-detail.md#limit_order-%E9%99%90%E4%BB%B7%E5%8D%95)

----------------------------------------------------------------------------------------------------------------------

`limit_order(account, contract, action, quantity, limit_price)`

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 下单账户， 可以使用综合账户、环球账户或模拟账户 |
| contract | tigeropen.trade.domain.contract.Contract | 要交易的 |

**返回**

`Order` 对象

  

stop\_order 止损单

[](./appendix-object-detail.md#stop_order-%E6%AD%A2%E6%8D%9F%E5%8D%95)

--------------------------------------------------------------------------------------------------------------------

`stop_order(account, contract, action, quantity, aux_price)`

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 下单账户， 可以使用综合账户、环球账户或模拟账户 |
| contract | tigeropen.trade.domain.contract.Contract | 要交易的 |

**返回**

`Order` 对象

  

stop\_limit\_order 限价止损单

[](./appendix-object-detail.md#stop_limit_order-%E9%99%90%E4%BB%B7%E6%AD%A2%E6%8D%9F%E5%8D%95)

-----------------------------------------------------------------------------------------------------------------------------------------------------

`stop_limit_order(account, contract, action, quantity, limit_price, aux_price)`

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 下单账户， 可以使用综合账户、环球账户或模拟账户 |
| contract | tigeropen.trade.domain.contract.Contract | 要交易的 |

**返回**

`Order` 对象

  

trail\_order 移动止损单

[](./appendix-object-detail.md#trail_order-%E7%A7%BB%E5%8A%A8%E6%AD%A2%E6%8D%9F%E5%8D%95)

------------------------------------------------------------------------------------------------------------------------------------------

`trail_order(account, contract, action, quantity, trailing_percent=None, aux_price=None)`

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 下单账户， 可以使用综合账户、环球账户或模拟账户 |
| contract | tigeropen.trade.domain.contract.Contract | 要交易的 |

**返回**

`Order` 对象

  

order\_leg 附加订单

[](./appendix-object-detail.md#order_leg-%E9%99%84%E5%8A%A0%E8%AE%A2%E5%8D%95)

----------------------------------------------------------------------------------------------------------------------------

`order_leg(leg_type, price, time_in_force='DAY', outside_rth=None)`

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| leg\_type | str | 附加订单类型. 'PROFIT' 止盈单类型, 'LOSS' 止损单类型 |
| price | float | 附加订单价格 |
| time\_in\_force | str | 附加订单有效期. 'DAY'（当日有效）和'GTC'（取消前有效 Good-Til-Canceled). |
| outside\_rth | bool | 附加订单是否允许盘前盘后交易(美股专属). True 允许, False 不允许 |

**返回**

`OrderLeg` 对象 `tigeropen.trade.domain.order.OrderLeg`

  

auction\_limit\_order 竞价限价单

[](./appendix-object-detail.md#auction_limit_order-%E7%AB%9E%E4%BB%B7%E9%99%90%E4%BB%B7%E5%8D%95)

-----------------------------------------------------------------------------------------------------------------------------------------------------------

`auction_limit_order(account, contract, action, quantity, limit_price, time_in_force)`

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 下单账户， 可以使用综合账户或模拟账户 |
| contract | tigeropen.trade.domain.contract.Contract | 要交易的 |

**返回**

`Order` 对象

  

auction\_market\_order 竞价市价单

[](./appendix-object-detail.md#auction_market_order-%E7%AB%9E%E4%BB%B7%E5%B8%82%E4%BB%B7%E5%8D%95)

-------------------------------------------------------------------------------------------------------------------------------------------------------------

`auction_market_order(account, contract, action, quantity, time_in_force)`

**参数**

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 下单账户， 可以使用综合账户或模拟账户 |
| contract | tigeropen.trade.domain.contract.Contract | 要交易的 |

**返回**

`Order` 对象

  

Transaction 成交记录

[](./appendix-object-detail.md#transaction-%E6%88%90%E4%BA%A4%E8%AE%B0%E5%BD%95)

===============================================================================================================================

`tigeropen.trade.domain.order.Transaction`

**说明**

订单的成交记录

**对象属性**

| 对象属性 | 类型  | 描述  | 备注  |
| --- | --- | --- | --- |
| account | str | 账户  |     |
| order\_id | int | 订单id |     |
| contract | Contract | [合约对象](https://docs.itigerup.com/zh/python/appendix1/object.md#contract-%E5%90%88%E7%BA%A6) |     |
| id  | int | 成交记录id |     |
| action | str | 订单方向 |     |
| filled\_quantity | int | 成交数量 |     |
| filled\_quantity\_scale | int | 成交数量的偏移量，默认为0。filledQuantity 和 filledQuantityScale 结合起来代表一个下单数量，如 qty=111 scale=2，那么真实 qty=111\*10^(-2)=1.11 | 版本 3.5.0 新增 |
| filled\_price | float | 成交价格 |     |
| filled\_amount | float | 成交金额 |     |
| transacted\_at | str | 成交时间 |     |

  

OrderLeg 附加订单

[](./appendix-object-detail.md#orderleg-%E9%99%84%E5%8A%A0%E8%AE%A2%E5%8D%95)

=========================================================================================================================

`tigeropen.trade.domain.order.OrderLeg`

**说明**

下主订单同时携带的附加订单

**对象属性**

| 对象属性 | 类型  | 描述  |
| --- | --- | --- |
| leg\_type | str | 附加订单类型. 'PROFIT' 止盈单类型, 'LOSS' 止损单类型 |
| price | float | 附加订单价格. |
| time\_in\_force | str | 附加订单有效期. 'DAY'（当日有效）和'GTC'（取消前有效 Good-Til-Canceled). |
| outside\_rth | bool | 附加订单是否允许盘前盘后交易(美股专属). True 允许, False 不允许. |

  

AlgoParams 算法订单参数

[](./appendix-object-detail.md#algoparams-%E7%AE%97%E6%B3%95%E8%AE%A2%E5%8D%95%E5%8F%82%E6%95%B0)

=================================================================================================================================================

`tigeropen.trade.domain.order.AlgoParams`

**说明**

算法订单(VWAP/TWAP)参数对象

**对象属性**

| 对象属性 | 类型  | 描述  |
| --- | --- | --- |
| start\_time | str或int | 生效开始时间(时间字符串或时间戳，TWAP和VWAP专用)，如 '2020-11-19 23:00:00' 或 1640159945678 |
| end\_time | str或int | 失效时间(时间字符串或时间戳 TWAP和VWAP专用) |
| no\_take\_liq | bool | 是否尽可能减少交易次数(VWAP订单专用) |
| allow\_past\_end\_time | bool | 是否允许失效时间后继续完成成交(TWAP和VWAP专用) |
| participation\_rate | float | 参与率(VWAP专用,0.01-0.5) |

  

Contract

[](./appendix-object-detail.md#contract)

===============================================================================

`tigeropen.trade.domain.contract.Contract` [source](https://github.com/tigerfintech/openapi-python-sdk/blob/master/tigeropen/trade/domain/contract.py)

**说明**

合约是指交易的买卖对象或者标的物（比如一只股票，或者一个期权），合约是由交易所统一制定的。比如购买老虎证券的股票，可以通过TIGR这个字母代号和市场信息（即market=’US‘，美国市场）来唯一标识。类似的在购买期权或者期货产品时，可能会需要用到其他一些标识字段。通过合约信息，我们在下单或者获取行情时就可以唯一的确定一个标的物了。在Open API Python SDK中，合约信息通过 tigeropen.trade.domain.contract.Contract 对象来保存。Contract 对象可传入构造 Order 订单对象的工具函数中创建 Order 对象，用于下单

常见的合约包括股票合约，期权合约，期货合约等，大部分合约包括如下几个要素:

*   标的代码(symbol)，一般美股、英股等合约代码都是英文字母，港股、A股等合约代码是数字，比如老虎证券的symbol是TIGR。
*   合约类型(security type)，常见合约类型包括：STK（股票），OPT（期权），FUT（期货），CASH（外汇），比如老虎证券股票的合约类型是STK。
*   货币类型(currency)，常见货币包括 USD（美元），HKD（港币）。
*   交易所(exchange)，STK类型的合约一般不会用到交易所字段，订单会自动路由，期货合约都用到交易所字段。

绝大多数股票，差价合约，指数或外汇对可以通过这四个属性来唯一确定。由于其性质，更复杂的合约（如期权和期货）需要一些额外的信息。以下是几种常见类型合约，以及其由哪些要素构成：

股票：

[](./appendix-object-detail.md#%E8%82%A1%E7%A5%A8)

------------------------------------------------------------------------------------

Python

    from tigeropen.common.util.contract_utils import stock_contract
    contract = stock_contract(symbol='TIGR', currency='USD')
    contract1 = stock_contract(symbol='00700', currency='HKD')

期权

[](./appendix-object-detail.md#%E6%9C%9F%E6%9D%83)

-----------------------------------------------------------------------------------

Python

    from tigeropen.common.util.contract_utils import option_contract, option_contract_by_symbol
    contract = option_contract(identifier='AAPL  190118P00160000')
    contract = option_contract_by_symbol('JD', expiry='20211015', strike=45.0, put_call='PUT', currency='USD')
    

期货

[](./appendix-object-detail.md#%E6%9C%9F%E8%B4%A7)

-----------------------------------------------------------------------------------

Python

    from tigeropen.common.util.contract_utils import future_contract
    contract = future_contract(symbol='CL', currency='USD', expiry='20190328', multiplier=1.0, exchange='SGX')

港股窝轮

[](./appendix-object-detail.md#%E6%B8%AF%E8%82%A1%E7%AA%9D%E8%BD%AE)

-------------------------------------------------------------------------------------------------------

Python

    from tigeropen.common.util.contract_utils import war_contract_by_symbol
    contract = war_contract_by_symbol('01810', '20221116', 14.52, 'CALL', local_symbol='14759', multiplier=2000,
    currency='HKD')

港股牛熊证

[](./appendix-object-detail.md#%E6%B8%AF%E8%82%A1%E7%89%9B%E7%86%8A%E8%AF%81)

-----------------------------------------------------------------------------------------------------------------

Python

    from tigeropen.common.util.contract_utils import iopt_contract_by_symbol
    contract = iopt_contract_by_symbol('02318', '20200420', 87.4, 'CALL', local_symbol='63379', currency='HKD')

具体字段及构造方法见下文

**对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| identifier | str | 唯一标识，股票identifier和symbol相同，期权为21位标识符，如：'AAPL 220729C00150000'，期货identifier |
| symbol | str | 股票代码，期权合约的symbol为对应标的物代码 |
| sec\_type | str | STK 股票/OPT 期权/FUT 期货/WAR 窝轮/IOPT 牛熊证等，默认 STK |
| name | str | 合约名称 |
| currency | str | 币种，USD/HKD/CNH |
| exchange | str | 交易所 |
| expiry | str | **期权和期货专有**，期权或期货过期日 |
| strike | float | **期权专有**，期权的行权价格 |
| multiplier | float | 每手数量 |
| put\_call | str | **期权专有**，期权方向，CALL 或者 PUT |
| local\_symbol | str | **环球账户专有**，港股用于识别窝轮和牛熊证 |
| short\_margin | float | 做空保证金比例(将废弃，请使用short\_initial\_margin代替) |
| short\_initial\_margin | float | 做空初始保证金比例 |
| short\_maintenance\_margin | float | 做空维持保证金比例（综合账号有值，环球账号合约没有值） |
| short\_fee\_rate | float | 做空费率 |
| shortable | int | 做空池剩余 |
| long\_initial\_margin | float | 做多初始保证金 |
| long\_maintenance\_margin | float | 做多维持保证金 |
| contract\_month | str | 合约月份， 如202201，表示2022年1月 |
| primary\_exchange | str | 股票上市交易所 |
| market | str | 市场 /US/HK/CN |
| min\_tick | float | 最小报价单位 |
| tickSizes | list | **股票专有**，最小报价单位价格区间，即当挂单价格在begin和end区间时，要满足tickSize要求，begin：价格左区间，end：价格右区间，type：区间类型 OPEN/OPEN\_CLOSED/CLOSED/CLOSED\_OPEN(开区间/左开右闭/闭区间/左闭右开)，tickSize：最小价格单位. 示例：`[{"begin":"0","end":"1","tickSize":1.0E-4,"type":"CLOSED"},{"begin":"1","end":"Infinity","tickSize":0.01,"type":"OPEN"}]` |
| continuous | bool | **期货专有**，是否为连续合约 |
| trading\_class | str | 合约的交易级别名称 |
| status | str | 合约状态, 0 不可交易，1 可交易 |
| trade | bool | **期货专有**，是否可交易 |
| last\_trading\_date | str | **期货专有**，最后交易日，如 '20211220'，表示2021年12月20日 |
| first\_notice\_date | str | **期货专有**，第一通知日，合约在第一通知日后无法开多仓. 已有的多仓会在第一通知日之前（通常为前三个交易日）被强制平仓，如 '20211222'，表示2021年12月22日 |
| last\_bidding\_close\_time | int | **期货专有**，竞价截止时间戳 |
| is\_etf | bool | 是否是ETF |
| etf\_leverage | int | ETF杠杆倍数，仅当合约为ETF时会存在该值 |
| discounted\_day\_initial\_margin | float | **Futures only**, Intraday initial margin discount |
| discounted\_day\_maintenance\_margin | float | **Futures only**, Intraday maintenance margin discount |
| discounted\_time\_zone\_code | float | **Futures only**, Intraday margin discount period time zone |
| discounted\_start\_at | float | **Futures only**, Intraday margin discount start time |
| discounted\_end\_at | float | **Futures only**, Intraday margin discount end time |
| lot\_size | float | 单笔交易中可交易的最小资产数量 |
| support\_overnight\_trading | bool | 是否支持夜盘交易 |

> ⚠️
> 
> print时只会显示部分属性, 可以用 `print(contract.to_str())` 打印全部属性

**通过接口获取**

Contract 对象可通过以下方法查询:

获取合约信息 `get_contract`/`get_contracts`

**参数**:

| 参数  | 是否必填 | 描述  |
| --- | --- | --- |
| symbol | Yes | 合约代码 如 00700/AAPL |
| sec\_type | Yes | 合约类型 如 SecurityType.STK/SecurityType.OPT |
| currency | No  | 币种 如 Currency.USD/Currency.HKD |
| exchange | No  | 交易所 如 SMART/SEHK |
| expiry | No  | 到期日 交易品种是期权时必传 yyyyMMdd |
| strike | No  | 行权价 交易品种是期权时必传 |
| put\_call | No  | CALL/PUT 交易品种是期权时必传 |
| secret\_key | No  | 机构交易员密钥，机构用户专有，需要在client\_config中配置 |

**返回**

get\_contract 返回 Contract 对象; get\_contracts 返回 Contract 对象列表. 对象属性参见上文的说明部分

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import get_client_config
    client_config = get_client_config(private_key_path='私钥路径', tiger_id='your tiger id', account='your account', secret_key='机构交易员专有密钥')
    trade_client = TradeClient(client_config)
    
    # 获取股票合约
    contract = trade_client.get_contract('FB', sec_type=SecurityType.STK)
    contracts = trade_client.get_contracts(['AAPL', 'TSLA'], sec_type=SecurityType.STK)
    
    # 获取期货合约
    fut_contract = trade_client.get_contract('CL', sec_type=SecurityType.FUT)
    
    # 获取期权合约
    opt_contract = trade_client.get_contract('SPY', sec_type=SecurityType.OPT, expiry='20231215', strike=435.0, put_call='CALL')

  

MarketStatus 市场状态

[](./appendix-object-detail.md#marketstatus-%E5%B8%82%E5%9C%BA%E7%8A%B6%E6%80%81)

=================================================================================================================================

`tigeropen.quote.domain.market_status.MarketStatus`

**说明**

市场交易状态

**对象属性**

| 对象属性 | 类型  | 描述  |
| --- | --- | --- |
| market | str | 市场。（US:美股，CN:沪深，HK:港股） |
| trading\_status | str | 市场交易状态码。 未开盘 NOT\_YET\_OPEN; 盘前交易 PRE\_HOUR\_TRADING; 交易中 TRADING; 午间休市 MIDDLE\_CLOSE; 盘后交易 POST\_HOUR\_TRADING; 已收盘 CLOSING; 夜盘 OVERNIGHT\_TRADING；提前休市 EARLY\_CLOSED; 休市 MARKET\_CLOSED; |
| status | str | 市场状态描述(未开盘，交易中，休市等） |
| open\_time | datetime.datetime | 最近开盘时间 |

  

OptionFilter 期权链过滤器

[](./appendix-object-detail.md#optionfilter-%E6%9C%9F%E6%9D%83%E9%93%BE%E8%BF%87%E6%BB%A4%E5%99%A8)

=====================================================================================================================================================

`tigeropen.quote.domain.filter.OptionFilter`

**说明**

期权过滤参数对象

**对象属性**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| implied\_volatility | float | No  | 隐含波动率, 反映市场预期的未来股价波动情况, 隐含波动率越高, 说明预期股价波动越剧烈 |
| in\_the\_money | bool | No  | 是否价内 |
| open\_interest | int | No  | 未平仓量, 每个交易日完结时市场参与者手上尚未平仓的合约数. 反映市场的深度和流动性 |
| delta | float | No  | delta, 反映股票价格变化对期权价格变化对影响. 股价每变化1元, 期权价格大约变化 delta. 取值 -1.0 ~ 1.0 |
| gamma | float | No  | gamma, 反映股票价格变化对于delta的影响. 股价每变化1元, delta变化gamma |
| theta | float | No  | theta, 反映时间变化对期权价格变化的影响. 时间每减少一天, 期权价格大约变化 theta |
| vega | float | No  | vega, 反映波动率对期权价格变化的影响. 波动率每变化1%, 期权价格大约变化 vega |
| rho | float | No  | rho, 反映无风险利率对期权价格变化的影响. 无风险利率每变化1%, 期权价格大约变化 rho |

  

订单变动

[](./appendix-object-detail.md#%E8%AE%A2%E5%8D%95%E5%8F%98%E5%8A%A8)

=======================================================================================================

| 字段  | 说明  |
| --- | --- |
| id  | 订单号 |
| account | 资金账号 |
| symbol | 持仓标的代码，如 'AAPL', '00700', 'ES', 'CN' |
| expiry | 仅支持期权、窝轮、牛熊证 |
| strike | 仅支持期权、窝轮、牛熊证 |
| right | 仅支持期权、窝轮、牛熊证 |
| identifier | 标的标识符。股票的identifier与symbol相同。期货的会带有合约月份，如 'CN2201' |
| multiplier | 每手数量，仅限 futures, options, warrants, CBBC |
| action | 买卖方向。BUY表示买入，SELL表示卖出。 |
| market | 市场。US、HK |
| currency | 币种。USD美元，HKD港币 |
| segType | 按交易品种划分的分类。S表示股票，C表示期货 |
| secType | STK Stocks, OPT Options, WAR Warrants, IOPT CBBC, CASH FOREX, FUT Futures, FOP Future Options |
| orderType | 订单类型。'MKT'市价单/'LMT'限价单/'STP'止损单/'STP\_LMT'止损限价单/'TRAIL'跟踪止损单 |
| isLong | 是否多头持仓 |
| totalQuantity | 下单数量 |
| totalQuantityScale | 下单数量偏移量，如 totalQuantity=111， totalQuantityScale=2，那么真实 totalQuantity=111\*10^(-2)=1.11 |
| filledQuantity | 成交总数量（订单分多笔成交的，filledQuantity为累计成交总数） |
| filledQuantityScale | 成交总数量偏移量 |
| avgFillPrice | 成交均价 |
| limitPrice | 限价单价格 |
| stopPrice | 止损价格 |
| realizedPnl | 已实现盈亏（只有综合账号有这个字段） |
| status | [订单状态](./appendix-enum.md#orderstatus) |
| replaceStatus | [订单改单状态](./appendix-enum.md#orderreplacestatus) |
| cancelStatus | [订单撤单状态](./appendix-enum.md#ordercancelstatus) |
| outsideRth | 是否允许盘前盘后交易，仅适用于美股 |
| canModify | 是否能修改 |
| canCancel | 是否能取消 |
| liquidation | 是否为平仓订单 |
| name | 标的名称 |
| source | 订单来源(from 'OpenApi', or other) |
| errorMsg | 错误信息 |
| attrDesc | 订单描述信息 |
| commissionAndFee | 佣金费用总计 |
| openTime | 下单时间 |
| timestamp | 订单状态最后更新时间 |

  

持仓变动

[](./appendix-object-detail.md#%E6%8C%81%E4%BB%93%E5%8F%98%E5%8A%A8)

=======================================================================================================

| 字段  | 说明  |
| --- | --- |
| account | 用户账户 |
| symbol | 股票代码 |
| expiry | 过期日 期权、窝轮、牛熊证专属 |
| strike | 底层价格 期权、窝轮、牛熊证专属 |
| right | 期权方向 PUT/CALL 期权、窝轮、牛熊证专属 |
| identifier | 标的标识符 |
| multiplier | 1手单位 期权、窝轮、牛熊证专属 |
| market | 交易市场 |
| currency | 货币类型 |
| segType | 按交易品种划分的分类。S表示股票，C表示期货 |
| secType | 合约类型 |
| position | 持仓  |
| positionScale | 持仓数量的偏移量 |
| averageCost | 平均成本 |
| latestPrice | 最新价 |
| marketValue | 市值  |
| unrealizedPnl | 浮动盈亏 |
| name | 标的名称 |
| timestamp | 服务器时间 |

  

资产变动

[](./appendix-object-detail.md#%E8%B5%84%E4%BA%A7%E5%8F%98%E5%8A%A8)

=======================================================================================================

| 字段  | 说明  |
| --- | --- |
| account | 用户账户 |
| currency | 货币类型 |
| segType | 按交易品种划分的分类。S表示股票，C表示期货 |
| availableFunds | 可用资金(含借贷股权-初始保证金) |
| excessLiquidity | 剩余流动性(借贷值股权-维持保证金) |
| netLiquidation | 净清算值 |
| equityWithLoan | 含借贷值股权(含贷款价值资产) |
| buyingPower | 购买力 |
| cashBalance | 账户现金余额 |
| grossPositionValue | 持仓市值 |
| initMarginReq | 当前初始保证金 |
| maintMarginReq | 当前维持保证金 |
| timestamp | 服务器时间 |

  

行情变动

[](./appendix-object-detail.md#%E8%A1%8C%E6%83%85%E5%8F%98%E5%8A%A8)

=======================================================================================================

*   基本行情

| 字段  | 二级字段 | 说明  |
| --- | --- | --- |
| symbol |     | 股票代码 |
| type |     | 类型  |
| timestamp |     | 行情数据时间 |
| serverTimestamp |     | 服务器时间 |
| avgPrice |     | 平均价格 |
| latestPrice |     | 最新价格 |
| latestPriceTimestamp |     | 最新价格的时间戳(盘前,盘后没有) |
| latestTime |     | 最新价格的时间 |
| preClose |     | 昨日收盘价 |
| volume |     | 当日累计成交量 |
| amount |     | 当日累计成交量金额 (期权期货不支持) |
| open |     | 开盘价 |
| high |     | 最高价格 |
| low |     | 最低价格 |
| hourTradingTag |     | 盘前盘后标识(美股盘中没有值)，取值：盘前/盘后 |
| marketStatus |     | 市场状态 取值：Trading/WaitingOpen/OpeningAuction/InAuction/ClosingAuction/Closed/MiddayBreak/AfterHoursTrading/AfterHoursMatching/ExtendedTrading/PreMarket/Overnight |
| identifier |     | 标的标识符（仅限期权） |
| openInt |     | 未平仓数（仅限期权） |
| tradeTime |     | 交易时间（仅限期货） |
| preSettlement |     | 上个结清价（仅限期货） |
| minTick |     | 最小变动价（仅限期货） |
| mi  | p   | 分钟价格 |
| mi  | a   | 分钟平均价格 |
| mi  | t   | 分钟时间 |
| mi  | v   | 分钟成交量 |
| mi  | o   | 分钟开盘价（仅限期货） |
| mi  | h   | 分钟最高价（仅限期货） |
| mi  | l   | 分钟最低价（仅限期货） |

*   盘口最优买卖价行情

| 字段  | 说明  |
| --- | --- |
| askPrice | 卖盘价格 |
| askSize | 卖盘数量 |
| askTimestamp | ask timestamp(Pre/Post-Mkt data not support) |
| bidPrice | 买盘价格 |
| bidSize | 买盘数量 |
| bidTimestamp | bid timestamp(Pre/Post-Mkt data not support) |

  
