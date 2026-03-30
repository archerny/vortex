交易命名词典
======

OrderType [​](./trade-trade-definition.md#ordertype)

---------------------------------------------------------------------------------------

*   说明：港股支持订单类型

| 枚举值 | 描述  |
| --- | --- |
| LO  | 限价单 |
| ELO | 增强限价单 |
| MO  | 市价单 |
| AO  | 竞价市价单 |
| ALO | 竞价限价单 |
| ODD | 碎股单挂单 |
| LIT | 触价限价单 |
| MIT | 触价市价单 |
| TSLPAMT | 跟踪止损限价单 (跟踪金额) |
| TSLPPCT | 跟踪止损限价单 (跟踪涨跌幅) |
| SLO | 特殊限价单，不支持改单 |

*   说明：美股支持订单类型

| 枚举值 | 描述  |
| --- | --- |
| LO  | 限价单 |
| MO  | 市价单 |
| LIT | 触价限价单 |
| MIT | 触价市价单 |
| TSLPAMT | 跟踪止损限价单 (跟踪金额) |
| TSLPPCT | 跟踪止损限价单 (跟踪涨跌幅) |

OrderStatus [​](./trade-trade-definition.md#orderstatus)

-------------------------------------------------------------------------------------------

*   说明：订单状态

| 枚举值 | 描述  |
| --- | --- |
| NotReported | 待提交 |
| ReplacedNotReported | 待提交 (改单成功) |
| ProtectedNotReported | 待提交 (保价订单) |
| VarietiesNotReported | 待提交 (条件单) |
| FilledStatus | 已成交 |
| WaitToNew | 已提待报 |
| NewStatus | 已委托 |
| WaitToReplace | 修改待报 |
| PendingReplaceStatus | 待修改 |
| ReplacedStatus | 已修改 |
| PartialFilledStatus | 部分成交 |
| WaitToCancel | 撤销待报 |
| PendingCancelStatus | 待撤回 |
| RejectedStatus | 已拒绝 |
| CanceledStatus | 已撤单 |
| ExpiredStatus | 已过期 |
| PartialWithdrawal | 部分撤单 |

Market [​](./trade-trade-definition.md#market)

---------------------------------------------------------------------------------

*   说明：市场

| 枚举值 | 描述  |
| --- | --- |
| HK  | 港股  |
| US  | 美股  |

WebSocket 推送通知 [​](./trade-trade-definition.md#websocket-%E6%8E%A8%E9%80%81%E9%80%9A%E7%9F%A5)

---------------------------------------------------------------------------------------------------------------------------------

*   WebSocket 推送通知字段说明

| 字段名 | 类型  | 注释  |
| --- | --- | --- |
| side | string | 买卖方向  <br>  <br>**可选值**  <br>`Buy` - 买入  <br>`Sell` - 卖出 |
| stock\_name | string | 公司名称 |
| submitted\_quantity | string | 委托数量 |
| symbol | string | 订单标的 |
| order\_type | string | [订单类型](./trade-trade-definition.md#ordertype) |
| submitted\_price | string | 委托价格 |
| executed\_quantity | string | 成交数量 |
| executed\_price | string | 成交价格 |
| order\_id | string | 订单 id |
| currency | string | 结算货币 |
| status | string | [订单状态](./trade-trade-definition.md#orderstatus) |
| submitted\_at | string | 下单时间，格式为时间戳 (秒) |
| updated\_at | string | 最近更新时间 |
| trigger\_price | string | 触发价格 |
| msg | string | 拒绝理由，备注信息 |
| tag | string | 订单标记  <br>  <br>**可选值**  <br>`Normal` - 普通订单  <br>`GTC` - 长期单  <br>`Grey` - 暗盘单 |
| trigger\_status | string | 条件单触发状态  <br>  <br>**可选值**  <br>`NOT_USED` - 未激活  <br>`DEACTIVE` - 已失效  <br>`ACTIVE` - 已激活  <br>`RELEASED` - 已触发 |
| trigger\_at | string | 触发时间 |
| trailing\_amount | string | 条件单跟踪金额 |
| trailing\_percent | string | 条件单跟踪涨跌幅 |
| limit\_offset | string | 指定价差 |
| account\_no | string | 用户端账号 |
| remark | string | 备注  |
| last\_share | string | 最新成交数量 |
| last\_price | string | 最新成交价格 |

### 示例 [​](./trade-trade-definition.md#%E7%A4%BA%E4%BE%8B)

json

    {
    	"event": "order_changed_lb",
    	"data": {
    		"side": "Buy",
    		"stock_name": "腾讯控股",
    		"submitted_quantity": "1000",
    		"symbol": "700.HK",
    		"order_type": "LO",
    		"submitted_price": "213.2",
    		"executed_quantity": "1000",
    		"executed_price": "213.2",
    		"order_id": "27",
    		"currency": "HKD",
    		"status": "NewStatus",
    		"submitted_at": "1562761893",
    		"updated_at": "1562761893",
    		"trigger_price": "213.0",
    		"msg": "Insufficient Qty - 1000",
    		"tag": "GTC",
    		"trigger_status": "ACTIVE",
    		"trigger_at": "1562761893",
    		"trailing_amount": "5",
    		"trailing_percent": "1",
    		"limit_offset": "0.01",
    		"account_no": "HK123445",
    		"last_share": "100",
    		"last_price": "234",
    		"remark": "abc"
    	}
    }

[LLMs Text](https://open.longbridge.com/docs/trade/trade-definition.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/definition.md)

最后更新于:

Pager

[上一页概览](./trade-trade-overview.md)

[下一页获取历史成交明细](./trade-execution-history_executions.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
