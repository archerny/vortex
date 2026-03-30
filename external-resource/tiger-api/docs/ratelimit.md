# 请求频率与限制

为保护服务器，防止恶意攻击，所有需要向老虎 OpenAPI 服务器发送请求的接口，都会有频率限制。

限流规则

[](./ratelimit.md#%E9%99%90%E6%B5%81%E8%A7%84%E5%88%99)

------------------------------------------------------------------------------------------

*   **限流维度**: 按 **TigerId + 接口** 进行独立计数，即同一个 TigerId 下每个接口单独计算调用次数，不同接口之间互不影响
*   **计数窗口**: 连续 60 秒的滚动窗口
*   **频率等级**: 接口按调用频率分为三个等级

| 等级  | 频率上限 |
| --- | --- |
| 高频  | 120 次/分钟 |
| 中频  | 60 次/分钟 |
| 低频  | 10 次/分钟 |

触发限流的错误信息如下:

    code=4 msg=rate limit error(current limiting interface:<接口名称>, up to 60 times per minute)
    

> ⚠️
> 
> **CAUTION**
> 
> 1.  如果接口请求频率过高，且一直在持续请求，账号有可能会被系统自动加入黑名单，被加入黑名单的账号将不能继续发起接口调用。
> 2.  如确有高频接口的调用需求，可与我们单独进行 [沟通](./contact.md#/)
>     

  

高频接口列表（120次/分钟）

[](./ratelimit.md#%E9%AB%98%E9%A2%91%E6%8E%A5%E5%8F%A3%E5%88%97%E8%A1%A8120%E6%AC%A1%E5%88%86%E9%92%9F)

-----------------------------------------------------------------------------------------------------------------------------------------------------

| 接口方法 |
| --- |
| 获取订单号(create\_order) |
| 创建订单(place\_order) |
| 修改订单(modify\_order) |
| 取消订单(cancel\_order) |
| 查询订单(get\_orders/get\_order) |
| 查询未成交订单(get\_open\_orders) |
| 查询已撤销订单(get\_cancelled\_orders) |
| 查询已成交订单(get\_filled\_orders) |
| 分时(get\_timeline) |
| 实时行情(get\_stock\_briefs) |
| 逐笔成交(get\_trade\_ticks) |
| 期权行情摘要(get\_option\_briefs) |
| 期权逐笔成交(get\_option\_trade\_ticks) |
| 期货实时行情(get\_future\_briefs) |

  

中频接口列表（60次/分钟）

[](./ratelimit.md#%E4%B8%AD%E9%A2%91%E6%8E%A5%E5%8F%A3%E5%88%97%E8%A1%A860%E6%AC%A1%E5%88%86%E9%92%9F)

---------------------------------------------------------------------------------------------------------------------------------------------------

| 接口方法 |
| --- |
| 期权链(get\_option\_chain) |
| 期权过期日(get\_option\_expirations) |
| 深度行情(get\_depth\_quote) |
| 单个合约(get\_contract) |
| 批量合约(get\_contracts) |
| 衍生合约(get\_derivative\_contracts) |
| 可做空股票列表(quote\_shortable\_stocks) |
| 股票交易信息(get\_trade\_metas) |
| 获取期货可交易日期(get\_future\_trading\_times) |
| 期货当前合约(get\_current\_future\_contract) |
| 获取账号列表(get\_managed\_accounts) |
| 获取环球账号资产(get\_assets) |
| 获取综合、模拟账号资产(get\_prime\_assets) |
| 获取持仓(get\_positions) |
| 获取订单成交报告(get\_transactions) |
| 获取历史分时数据(get\_timeline\_history) |
| 股票K线(get\_bars) |
| 期权K线(get\_option\_bars) |
| 期货k线(get\_future\_bars) |
| 期权分析(get\_option\_analysis) |

  

低频接口列表（10次/分钟）

[](./ratelimit.md#%E4%BD%8E%E9%A2%91%E6%8E%A5%E5%8F%A3%E5%88%97%E8%A1%A810%E6%AC%A1%E5%88%86%E9%92%9F)

---------------------------------------------------------------------------------------------------------------------------------------------------

| 接口方法 |
| --- |
| 行情抢占(grab\_quote\_permission) |
| 行情权限列表(get\_quote\_permission) |
| 市场状态(get\_market\_status) |
| 股票代号(get\_symbols) |
| 股票代号名称(get\_symbol\_names) |
| 股票行情(get\_stock\_details) |
| 期货交易所(get\_future\_exchanges) |
| 热门交易榜(get\_trade\_rank) |
