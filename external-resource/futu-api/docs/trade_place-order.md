 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/place-order.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/place-order.html)
    

下载

*   [PDF](https://openapi.futunn.com/pdfs/Futu-API-Doc-zh-Python.pdf)
    
*   [Markdown](https://openapi.futunn.com/mds/Futu-API-Doc-zh-Python.md)
    
*   [Skills](https://openapi.futunn.com/skills/opend-skills.zip)
    

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/place-order.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/place-order.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
*   交易接口
    
    *   [交易接口总览](https://openapi.futunn.com/futu-api-doc/trade/overview.html)
        
    *   [交易对象](https://openapi.futunn.com/futu-api-doc/trade/base.html)
        
    *   账户
        
    *   资产持仓
        
    *   订单
        
        *   [下单](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)
            
        *   [改单撤单](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html)
            
        *   [查询未完成订单](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html)
            
        *   [查询历史订单](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-list.html)
            
        *   [响应订单推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order.html)
            
        *   [查询订单费用](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html)
            
        *   [订阅交易推送](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html)
            
        
    *   成交
        
    *   [交易定义](https://openapi.futunn.com/futu-api-doc/trade/trade.html)
        
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/trade/place-order.html#4080)
 下单
============================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`place_order(price, qty, code, trd_side, order_type=OrderType.NORMAL, adjust_limit=0, trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, remark=None, time_in_force=TimeInForce.DAY, fill_outside_rth=False, aux_price=None, trail_type=None, trail_value=None, trail_spread=None, session=Session.NONE, jp_acc_type=SubAccType.JP_GENERAL, position_id=NONE)`

*   **介绍**
    
    下单
    
    提示
    
    Python API 是同步的，但网络收发是异步的。当 place\_order 对应的应答数据包与 [响应成交推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html)
     或 [响应订单推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order.html)
     间隔很短时，就可能出现 place\_order 的数据包先返回，但回调函数先被调用的情况。例如：可能先调用了 [响应订单推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order.html)
    ，然后 place\_order 这个接口才返回。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | price | float | 订单价格<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   当订单是市价单或竞价单类型，仍需对 price 传参，price 可以传入任意值<br>*   精度：<br>    *   期货：整数8位，小数9位，支持负数价格<br>    *   美股期权：小数2位<br>    *   美股：不超过$1，允许小数4位<br>    *   其他：小数3位，超出部分四舍五入 |
    | qty | float | 订单数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
    | code | str | 标的代码<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>如果 code 为期货主连代码，则会自动转为实际对应的合约代码 |
    | trd\_side | [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815) | 交易方向 |
    | order\_type | [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181) | 订单类型 |
    | adjust\_limit | float | 价格微调幅度<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>OpenD 会对传入价格自动调整到合法价位上<br><br>*   正数代表向上调整，负数代表向下调整<br>*   例如：0.015 代表向上调整且幅度不超过 1.5%；-0.01 代表向下调整且幅度不超过 1%。默认 0 表示不调整 |
    | trd\_env | [TrdEnv](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | remark | str | 备注<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   订单会带上此备注字段，方便您标识订单<br>*   转成 utf8 后的长度上限为 64 字节 |
    | time\_in\_force | [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241) | 有效期限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>香港市场、A 股市场和环球期货的市价单，仅支持当日有效 |
    | fill\_outside\_rth | bool | 是否允许盘前盘后<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>用于港股盘前竞价与美股盘前盘后，且盘前盘后时段不支持市价单 |
    | aux\_price | float | 触发价格<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   当订单是**止损市价单**、**止损限价单**、**触及限价单（止盈）**、**触及市价单（止盈）** 时，aux\_price 为必传参数<br>*   同price精度，超过部分四舍五入 |
    | trail\_type | [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644) | 跟踪类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>当订单是**跟踪止损市价单**、**跟踪止损限价单时**，trail\_type 为必传参数 |
    | trail\_value | float | 跟踪金额/百分比<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   当订单是**跟踪止损市价单**、**跟踪止损限价单**时，trail\_value 为必传参数<br>*   当跟踪类型为比例时，该字段为百分比字段，传入 20 实际对应 20%<br>*   当跟踪类型为金额时，整数部分同price；小数部分美股期权固定2位，美股4位，其他同price；超过部分四舍五入<br>*   当跟踪类型为比例时，精确到小数点后 2 位，整数部分同price，超过部分四舍五入 |
    | trail\_spread | float | 指定价差<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   当订单是**跟踪止损限价单**时，trail\_spread 为必传参数<br>*   证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超过部分四舍五入 |
    | session | [Session](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9152) | 美股交易时段<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅对美股生效，支持传入**RTH**、**ETH**、**OVERNIGHT**、**ALL** |
    | jp\_acc\_type | [SubAccType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6112) | 日本账户类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅日本券商适用 |
    | position\_id | int | 持仓ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   日本券商平仓时需要填写<br>*   可通过[查询持仓](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html)<br>    接口获取 |
    
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回订单列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   订单列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_side | [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815) | 交易方向 |
        | order\_type | [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181) | 订单类型 |
        | order\_status | [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797) | 订单状态 |
        | order\_id | str | 订单号 |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | qty | float | 订单数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
        | price | float | 订单价格<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分四舍五入 |
        | create\_time | str | 创建时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd HH:mm:ss  <br>期货时区指定，请参见 [FutuOpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
        | updated\_time | str | 最后更新时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd HH:mm:ss  <br>期货时区指定，请参见 [FutuOpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
        | dealt\_qty | float | 成交数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
        | dealt\_avg\_price | float | 成交均价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>无精度限制 |
        | last\_err\_msg | str | 最后的错误描述<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>如果有错误，会返回最后一次错误的原因  <br>如果无错误，返回空字符串 |
        | remark | str | 下单时备注的标识<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>详见 [place\_order](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)<br> 接口参数中的 remark |
        | time\_in\_force | [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241) | 有效期限 |
        | fill\_outside\_rth | bool | 是否允许盘前盘后（用于港股盘前竞价与美股盘前盘后）<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：允许  <br>False：不允许 |
        | session | [Session](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9152) | 交易订单时段（仅用于美股） |
        | aux\_price | float | 触发价格 |
        | trail\_type | [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644) | 跟踪类型 |
        | trail\_value | float | 跟踪金额/百分比 |
        | trail\_spread | float | 指定价差 |
        
*   **Example**
    

    from futu import *
    pwd_unlock = '123456'
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.unlock_trade(pwd_unlock)  # 若使用真实账户下单，需先对账户进行解锁。此处示例为模拟账户下单，也可省略解锁。
    if ret == RET_OK:
        ret, data = trd_ctx.place_order(price=510.0, qty=100, code="HK.00700", trd_side=TrdSide.BUY, trd_env=TrdEnv.SIMULATE, session=Session.NONE)
        if ret == RET_OK:
            print(data)
            print(data['order_id'][0])  # 获取下单的订单号
            print(data['order_id'].values.tolist())  # 转为 list
        else:
            print('place_order error: ', data)
    else:
        print('unlock_trade failed: ', data)
    trd_ctx.close()
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

*   **Output**

    
           code stock_name trd_side order_type order_status           order_id    qty  price          create_time         updated_time  dealt_qty  dealt_avg_price last_err_msg remark time_in_force fill_outside_rth session aux_price trail_type trail_value trail_spread currency
    0  HK.00700       腾讯控股      BUY     NORMAL   SUBMITTING  38196006548709500  100.0  420.0  2021-11-04 11:38:19  2021-11-04 11:38:19        0.0              0.0                               DAY              N/A       N/A    N/A      N/A         N/A          N/A      HKD
    38196006548709500
    ['38196006548709500']
    

1  
2  
3  
4  
5  

[#](https://openapi.futunn.com/futu-api-doc/trade/place-order.html#8194)
 Trd\_PlaceOrder.proto
-----------------------------------------------------------------------------------------------

*   **介绍**
    
    下单
    
*   **参数**
    

    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required int32 trdSide = 3; //交易方向，参见 Trd_Common.TrdSide 的枚举定义
    	required int32 orderType = 4; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 5; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double qty = 6; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃。期权期货单位是"张"）
    	optional double price = 7; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下2个为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 8; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 9; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 10; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string remark = 11; //用户备注字符串，最多只能传64字节。可用于标识订单唯一信息等，下单填上，订单结构就会带上。
    	optional int32 timeInForce = 12; //订单有效期限，参见 TrdCommon.TimeInForce 的枚举定义（香港市场、A 股市场和环球期货的市价单，仅支持当日有效）
    	optional bool fillOutsideRTH = 13; //是否允许盘前盘后成交（仅用于美股，且盘前盘后时段不支持市价单）。默认 false
    	optional double auxPrice = 14; //触发价格
    	optional int32 trailType = 15; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 16; //跟踪金额/百分比
    	optional double trailSpread = 17; //指定价差
        optional int32 session = 18; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 19; //持仓ID，日本券商平仓时使用
    }
    
    message Request
    {
    	required C2S c2s = 1;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  

> *   请求包标识结构参见 [PacketID](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   交易方向参见 [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815)
>     
> *   订单类型参见 [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181)
>     
> *   股票行情市场参见 [TrdSecMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5084)
>     
> *   订单有效期参见 [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241)
>     
> *   跟踪类型参见 [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644)
>     

*   **返回**

    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
    	required int32 retType = 1 [default = -400];
    	optional string retMsg = 2;
    	optional int32 errCode = 3;
    	
    	optional S2C s2c = 4;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2202
    

`uint PlaceOrder(TrdPlaceOrder.Request req);`  
`virtual void OnReply_PlaceOrder(FTAPI_Conn client, uint nSerialNo, TrdPlaceOrder.Response rsp);`

*   **介绍**
    
    下单
    
*   **参数**
    

    
    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required int32 trdSide = 3; //交易方向，参见 Trd_Common.TrdSide 的枚举定义
    	required int32 orderType = 4; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 5; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double qty = 6; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃。期权期货单位是"张"）
    	optional double price = 7; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下2个为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 8; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 9; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 10; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string remark = 11; //用户备注字符串，最多只能传64字节。可用于标识订单唯一信息等，下单填上，订单结构就会带上。
    	optional int32 timeInForce = 12; //订单有效期限，参见 TrdCommon.TimeInForce 的枚举定义
    	optional bool fillOutsideRTH = 13; //是否允许盘前盘后成交。仅适用于美股限价单。默认 false
    	optional double auxPrice = 14; //触发价格
    	optional int32 trailType = 15; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 16; //跟踪金额/百分比
    	optional double trailSpread = 17; //指定价差
        optional int32 session = 18; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 19; //持仓ID，日本券商平仓时使用
    }
    
    message Request
    {
    	required C2S c2s = 1;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  

> *   请求包标识结构参见 [PacketID](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   交易方向参见 [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815)
>     
> *   订单类型参见 [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181)
>     
> *   股票行情市场参见 [TrdSecMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5084)
>     
> *   订单有效期参见 [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241)
>     
> *   跟踪类型参见 [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644)
>     

*   **回调**

    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //表示服务器订单id，可以用来代替orderID，和orderID二选一		
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
    	required int32 retType = 1 [default = -400];
    	optional string retMsg = 2;
    	optional int32 errCode = 3;
    	
    	optional S2C s2c = 4;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : FTSPI_Trd, FTSPI_Conn {
        FTAPI_Trd trd = new FTAPI_Trd();
    
        public Program() {
            trd.SetClientInfo("csharp", 1);  //设置客户端信息
            trd.SetConnCallback(this);  //设置连接回调
            trd.SetTrdCallback(this);   //设置交易回调
        }
    
        public void Start() {
            trd.InitConnect("127.0.0.1", (ushort)11111, false);
        }
    
        
        public void OnInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Trd onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
            if (errCode != 0)
                return;
    
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.CreateBuilder()
                    .SetAccID(281756457888247915L)
                    .SetTrdEnv((int)TrdCommon.TrdEnv.TrdEnv_Simulate)
                    .SetTrdMarket((int)TrdCommon.TrdMarket.TrdMarket_HK)
                    .Build();
            TrdPlaceOrder.C2S c2s = TrdPlaceOrder.C2S.CreateBuilder()
                    .SetPacketID(trd.NextPacketID())
                    .SetHeader(header)
                    .SetTrdSide((int)TrdCommon.TrdSide.TrdSide_Buy)
                    .SetOrderType((int)TrdCommon.OrderType.OrderType_Normal)
                    .SetCode("00700")
                    .SetQty(100)
                    .SetPrice(520)
                    .SetSecMarket(TrdCommon.TrdSecMarket.TrdSecMarket_HK)
                .Build();
            TrdPlaceOrder.Request req = TrdPlaceOrder.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.PlaceOrder(req);
            Console.Write("Send TrdPlaceOrder: {0}\n", seqNo);
        }
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_PlaceOrder(FTAPI_Conn client, uint nSerialNo, TrdPlaceOrder.Response rsp)
        {
            Console.Write("Reply: TrdPlaceOrder: {0}\n", nSerialNo);
            Console.Write("accID: {0}\n", rsp.S2C.Header.AccID);
        }
    
        public static void Main(String[] args) {
            FTAPI.Init();
            Program trd = new Program();
            trd.Start();
    
            while (true)
                Thread.Sleep(1000 * 600);
        }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  
30  
31  
32  
33  
34  
35  
36  
37  
38  
39  
40  
41  
42  
43  
44  
45  
46  
47  
48  
49  
50  
51  
52  
53  
54  
55  
56  
57  
58  
59  

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6827788355222092042
    Send TrdPlaceOrder: 3
    Reply: TrdPlaceOrder: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int placeOrder(TrdPlaceOrder.Request req);`  
`void onReply_PlaceOrder(FTAPI_Conn client, int nSerialNo, TrdPlaceOrder.Response rsp);`

*   **介绍**
    
    下单
    
*   **参数**
    

    
    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required int32 trdSide = 3; //交易方向，参见 Trd_Common.TrdSide 的枚举定义
    	required int32 orderType = 4; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 5; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double qty = 6; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃。期权期货单位是"张"）
    	optional double price = 7; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下2个为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 8; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 9; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 10; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string remark = 11; //用户备注字符串，最多只能传64字节。可用于标识订单唯一信息等，下单填上，订单结构就会带上。
    	optional int32 timeInForce = 12; //订单有效期限，参见 TrdCommon.TimeInForce 的枚举定义
    	optional bool fillOutsideRTH = 13; //是否允许盘前盘后成交。仅适用于美股限价单。默认 false
    	optional double auxPrice = 14; //触发价格
    	optional int32 trailType = 15; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 16; //跟踪金额/百分比
    	optional double trailSpread = 17; //指定价差
        optional int32 session = 18; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 19; //持仓ID，日本券商平仓时使用
    }
    
    message Request
    {
    	required C2S c2s = 1;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  

> *   请求包标识结构参见 [PacketID](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   交易方向参见 [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815)
>     
> *   订单类型参见 [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181)
>     
> *   股票行情市场参见 [TrdSecMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5084)
>     
> *   订单有效期参见 [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241)
>     
> *   跟踪类型参见 [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644)
>     

*   **回调**

    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //表示服务器订单id，可以用来代替orderID，和orderID二选一		
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
    	required int32 retType = 1 [default = -400];
    	optional string retMsg = 2;
    	optional int32 errCode = 3;
    	
    	optional S2C s2c = 4;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class TrdDemo implements FTSPI_Trd, FTSPI_Conn {
        FTAPI_Conn_Trd trd = new FTAPI_Conn_Trd();
    
        public TrdDemo() {
            trd.setClientInfo("javaclient", 1);  //设置客户端信息
            trd.setConnSpi(this);  //设置连接回调
            trd.setTrdSpi(this);   //设置交易回调
        }
    
        public void start() {
            trd.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Trd onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
            if (errCode != 0)
                return;
    
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.newBuilder()
                    .setAccID(281756457888247915L)
                    .setTrdEnv(TrdCommon.TrdEnv.TrdEnv_Simulate_VALUE)
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)
                    .build();
            TrdPlaceOrder.C2S c2s = TrdPlaceOrder.C2S.newBuilder()
                    .setPacketID(trd.nextPacketID())
                    .setHeader(header)
                    .setTrdSide(TrdCommon.TrdSide.TrdSide_Buy_VALUE)
                    .setOrderType(TrdCommon.OrderType.OrderType_Normal_VALUE)
                    .setSecMarket(TrdCommon.TrdSecMarket.TrdSecMarket_HK_VALUE)
                    .setCode("00700")
                    .setQty(100)
                    .setPrice(580)
                .build();
            TrdPlaceOrder.Request req = TrdPlaceOrder.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.placeOrder(req);
            System.out.printf("Send TrdPlaceOrder: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_PlaceOrder(FTAPI_Conn client, int nSerialNo, TrdPlaceOrder.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdPlaceOrder failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdPlaceOrder: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public static void main(String[] args) {
            FTAPI.init();
            TrdDemo trd = new TrdDemo();
            trd.start();
    
            while (true) {
                try {
                    Thread.sleep(1000 * 600);
                } catch (InterruptedException exc) {
    
                }
            }
        }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  
30  
31  
32  
33  
34  
35  
36  
37  
38  
39  
40  
41  
42  
43  
44  
45  
46  
47  
48  
49  
50  
51  
52  
53  
54  
55  
56  
57  
58  
59  
60  
61  
62  
63  
64  
65  
66  
67  
68  
69  
70  
71  
72  
73  
74  

*   **Output**

    Send TrdPlaceOrder: 2
    Receive TrdPlaceOrder: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 0,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "orderID": "5185481028427965890"
      }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

`Futu::u32_t PlaceOrder(const Trd_PlaceOrder::Request &stReq);`  
`virtual void OnReply_PlaceOrder(Futu::u32_t nSerialNo, const Trd_PlaceOrder::Response &stRsp) = 0;`

*   **介绍**
    
    下单
    
*   **参数**
    

    
    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required int32 trdSide = 3; //交易方向，参见 Trd_Common.TrdSide 的枚举定义
    	required int32 orderType = 4; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 5; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double qty = 6; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃。期权期货单位是"张"）
    	optional double price = 7; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下2个为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 8; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 9; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 10; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string remark = 11; //用户备注字符串，最多只能传64字节。可用于标识订单唯一信息等，下单填上，订单结构就会带上。
    	optional int32 timeInForce = 12; //订单有效期限，参见 TrdCommon.TimeInForce 的枚举定义
    	optional bool fillOutsideRTH = 13; //是否允许盘前盘后成交。仅适用于美股限价单。默认 false
    	optional double auxPrice = 14; //触发价格
    	optional int32 trailType = 15; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 16; //跟踪金额/百分比
    	optional double trailSpread = 17; //指定价差
        optional int32 session = 18; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 19; //持仓ID，日本券商平仓时使用
    }
    
    message Request
    {
    	required C2S c2s = 1;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  

> *   请求包标识结构参见 [PacketID](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   交易方向参见 [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815)
>     
> *   订单类型参见 [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181)
>     
> *   股票行情市场参见 [TrdSecMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5084)
>     
> *   订单有效期参见 [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241)
>     
> *   跟踪类型参见 [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644)
>     

*   **回调**

    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //表示服务器订单id，可以用来代替orderID，和orderID二选一		
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
    	required int32 retType = 1 [default = -400];
    	optional string retMsg = 2;
    	optional int32 errCode = 3;
    	
    	optional S2C s2c = 4;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    class Program : public FTSPI_Qot, public FTSPI_Trd, public FTSPI_Conn
    {
    public:
    
    	Program() {
    		m_pTrdApi = FTAPI::CreateTrdApi();
    		m_pTrdApi->RegisterTrdSpi(this);
    		m_pTrdApi->RegisterConnSpi(this);
    	}
    
    	~Program() {
    		if (m_pTrdApi != nullptr)
    		{
    			m_pTrdApi->UnregisterTrdSpi();
    			m_pTrdApi->UnregisterConnSpi();
    			FTAPI::ReleaseTrdApi(m_pTrdApi);
    			m_pTrdApi = nullptr;
    		}
    	}
    
    	void Start() {
    		m_pTrdApi->InitConnect("127.0.0.1", 11111, false);
    	}
    
    
    	virtual void OnInitConnect(FTAPI_Conn* pConn, Futu::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		// 组包
    		Trd_PlaceOrder::Request req;
    		Trd_PlaceOrder::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    		c2s->set_trdside(1);
    		c2s->set_ordertype(1);
    		c2s->set_code("00700");
    		c2s->set_qty(100);
    		c2s->set_price(1);
    		c2s->set_secmarket(Trd_Common::TrdMarket::TrdMarket_HK);
    
            m_PlaceOrderSerialNo = m_pTrdApi->PlaceOrder(req);
            cout << "Request PlaceOrder SerialNo: " << m_PlaceOrderSerialNo << endl;
    	}
    
    	virtual void OnReply_PlaceOrder(Futu::u32_t nSerialNo, const Trd_PlaceOrder::Response &stRsp){
            if(nSerialNo == m_PlaceOrderSerialNo)
            {
                cout << "OnReply_PlaceOrder SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_PlaceOrderSerialNo;
    };
    
    int32_t main(int32_t argc, char** argv)
    {
    	FTAPI::Init();
    
    	{
    		Program program;
    		program.Start();
    		getchar();
    	}
    
    	protobuf::ShutdownProtobufLibrary();
    	FTAPI::UnInit();
    	return 0;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  
30  
31  
32  
33  
34  
35  
36  
37  
38  
39  
40  
41  
42  
43  
44  
45  
46  
47  
48  
49  
50  
51  
52  
53  
54  
55  
56  
57  
58  
59  
60  
61  
62  
63  
64  
65  
66  
67  
68  
69  
70  
71  
72  
73  
74  
75  
76  
77  
78  

*   **Output**

    connect
    Request PlaceOrder SerialNo: 4
    OnReply_PlaceOrder SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "header": {
       "trdEnv": 0,
       "accID": "3637840",
       "trdMarket": 1
      },
      "orderID": "3954832860392428914"
     }
    }
    
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  

`PlaceOrder(req);`

*   **介绍**
    
    下单
    
*   **参数**
    

    
    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required int32 trdSide = 3; //交易方向，参见 Trd_Common.TrdSide 的枚举定义
    	required int32 orderType = 4; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 5; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double qty = 6; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃。期权期货单位是"张"）
    	optional double price = 7; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下2个为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 8; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 9; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 10; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string remark = 11; //用户备注字符串，最多只能传64字节。可用于标识订单唯一信息等，下单填上，订单结构就会带上。
    	optional int32 timeInForce = 12; //订单有效期限，参见 TrdCommon.TimeInForce 的枚举定义
    	optional bool fillOutsideRTH = 13; //是否允许盘前盘后成交。仅适用于美股限价单。默认 false
    	optional double auxPrice = 14; //触发价格
    	optional int32 trailType = 15; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 16; //跟踪金额/百分比
    	optional double trailSpread = 17; //指定价差
        optional int32 session = 18; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 19; //持仓ID，日本券商平仓时使用
    }
    
    message Request
    {
    	required C2S c2s = 1;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  

> *   请求包标识结构参见 [PacketID](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   交易方向参见 [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815)
>     
> *   订单类型参见 [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181)
>     
> *   股票行情市场参见 [TrdSecMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5084)
>     
> *   订单有效期参见 [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241)
>     
> *   跟踪类型参见 [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644)
>     

*   **返回**

    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //表示服务器订单id，可以用来代替orderID，和orderID二选一		
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
    	required int32 retType = 1 [default = -400];
    	optional string retMsg = 2;
    	optional int32 errCode = 3;
    	
    	optional S2C s2c = 4;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdPlaceOrder(){
        const { RetType, PacketID } = Common
        const { TrdEnv, TrdSide, OrderType, SecurityFirm, TrdMarket, TrdSecMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        let tradeSerialNo = 0;
        websocket.onlogin = async ()=>{
            try{
                let { errCode, retMsg, retType } = await websocket.UnlockTrade({
                    c2s: {
                        unlock: true,
                        securityFirm: SecurityFirm.SecurityFirm_FutuSecurities,
                        pwdMD5: "d0970714757783e6cf17b26fb8e2298f", // 设置为自己账号的交易密码MD5
                    },
                });
                if(retType == RetType.RetType_Succeed && errCode == 0) { // 解锁交易成功
                    let { errCode, retMsg, retType, s2c: { accList } } = await websocket.GetAccList({
                        c2s: {
                            userID: 0,
                        },
                    });
                    if(retType == RetType.RetType_Succeed && errCode == 0) { // 获取账户成功
                        let acc = accList.filter((item)=>{ 
                            return item.trdEnv == TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})
                        })[0]; // 样例取第一个香港市场虚拟环境账户
                        
                        const req = {
                            c2s: {
                                packetID:{
                                    connID: websocket.getConnID(),
                                    serialNo: tradeSerialNo++,
                                },
                                header: {
                                    trdEnv: acc.trdEnv,
                                    accID: acc.accID,
                                    trdMarket: TrdMarket.TrdMarket_HK,
                                },
                                trdSide: TrdSide.TrdSide_Buy,
                                orderType: OrderType.OrderType_Normal,
                                code: "00700",
                                qty: 100,
                                price: 150,
                                secMarket: TrdSecMarket.TrdSecMarket_HK,
                            },
                        };
                        let { errCode, retMsg, retType, s2c } = await websocket.PlaceOrder(req);
                        console.log("PlaceOrder: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                        if(retType == RetType.RetType_Succeed){
                            let data = beautify(JSON.stringify(s2c), {
                                indent_size: 2,
                                space_in_empty_paren: true,
                            });
                            console.log(data);
                        }
                    }
                }
            }
            catch(err){
                console.log(err)
            }
            
        };
    
        websocket.start(addr, port, enable_ssl, key);
        
        //关闭行情连接，连接不再使用之后，要关闭，否则占用不必要资源
        //同时OpenD也限制了最多128条连接
        //也可以一个页面或者一个项目维护一条连接，这里范例请求一次创建一条连接
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 5000); // 5秒后断开
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  
30  
31  
32  
33  
34  
35  
36  
37  
38  
39  
40  
41  
42  
43  
44  
45  
46  
47  
48  
49  
50  
51  
52  
53  
54  
55  
56  
57  
58  
59  
60  
61  
62  
63  
64  
65  
66  
67  
68  
69  
70  
71  
72  
73  
74  
75  
76  
77  
78  
79  

*   **Output**

    PlaceOrder: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6684972",
        "trdMarket": 1
      },
      "orderID": "5870498377428972628"
    }
    stop
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 15 次下单接口，且连续两次请求的间隔不可小于 0.02 秒。
*   真实账户调用下单接口前，需要先进行 [解锁](https://openapi.futunn.com/futu-api-doc/trade/unlock.html)
    ；模拟账户无需解锁。

提示

*   各订单类型对应的必传参数：[点击这里](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2984)
     了解更多
*   各券商针对不同交易品种，对单笔订单股数有所限制，超出限制会导致下单失败：[点击这里](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2984)
     了解更多
*   对于 **可做空标的**，暂不支持锁仓功能，故无法同时持有相同产品的多头头寸和空头头寸。
*   如果希望对 **可做空标的** 进行 **平仓** 操作，需要自行判断持仓头寸的方向，然后提交一笔反向的相同数量的订单完成平仓操作。
*   如果希望对 **可做空标的** 进行 **反手** 操作，需要两步：1. 先判断持仓头寸的方向，并提交一笔反向的相同数量的订单完成平仓操作；2. 提交一笔反向的订单，完成反向订单的提交。  
    举例：A 当前持有 1 手 HK.HSI2012 期货合约的多单，如果希望反手，必须先 卖出 1 手 HK.HSI2012 完成平仓，再卖出 1 手 HK.HSI2012 完成空单的建立。
*   美股全时段交易，仅支持限价单，订单期限可以选择当日有效或撤单前有效。选择全时段，交易者可以一次挂单参与多个时段（夜盘、盘前、盘中、盘后时段）的交易，全时段交易时间是星期日到星期四 20:00 - 次日20:00（美东时间）
*   美股模拟交易不支持盘前盘后与夜盘

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`place_order(price, qty, code, trd_side, order_type=OrderType.NORMAL, adjust_limit=0, trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, remark=None, time_in_force=TimeInForce.DAY, fill_outside_rth=False, aux_price=None, trail_type=None, trail_value=None, trail_spread=None, session=Session.NONE, jp_acc_type=SubAccType.JP_GENERAL, position_id=NONE)`

*   **介绍**
    
    下单
    
    提示
    
    Python API 是同步的，但网络收发是异步的。当 place\_order 对应的应答数据包与 [响应成交推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html)
     或 [响应订单推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order.html)
     间隔很短时，就可能出现 place\_order 的数据包先返回，但回调函数先被调用的情况。例如：可能先调用了 [响应订单推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order.html)
    ，然后 place\_order 这个接口才返回。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | price | float | 订单价格<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   当订单是市价单或竞价单类型，仍需对 price 传参，price 可以传入任意值<br>*   精度：<br>    *   期货：整数8位，小数9位，支持负数价格<br>    *   美股期权：小数2位<br>    *   美股：不超过$1，允许小数4位<br>    *   其他：小数3位，超出部分四舍五入 |
    | qty | float | 订单数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
    | code | str | 标的代码<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>如果 code 为期货主连代码，则会自动转为实际对应的合约代码 |
    | trd\_side | [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815) | 交易方向 |
    | order\_type | [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181) | 订单类型 |
    | adjust\_limit | float | 价格微调幅度<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>OpenD 会对传入价格自动调整到合法价位上<br><br>*   正数代表向上调整，负数代表向下调整<br>*   例如：0.015 代表向上调整且幅度不超过 1.5%；-0.01 代表向下调整且幅度不超过 1%。默认 0 表示不调整 |
    | trd\_env | [TrdEnv](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | remark | str | 备注<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   订单会带上此备注字段，方便您标识订单<br>*   转成 utf8 后的长度上限为 64 字节 |
    | time\_in\_force | [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241) | 有效期限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>香港市场、A 股市场和环球期货的市价单，仅支持当日有效 |
    | fill\_outside\_rth | bool | 是否允许盘前盘后<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>用于港股盘前竞价与美股盘前盘后，且盘前盘后时段不支持市价单 |
    | aux\_price | float | 触发价格<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   当订单是**止损市价单**、**止损限价单**、**触及限价单（止盈）**、**触及市价单（止盈）** 时，aux\_price 为必传参数<br>*   同price精度，超过部分四舍五入 |
    | trail\_type | [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644) | 跟踪类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>当订单是**跟踪止损市价单**、**跟踪止损限价单时**，trail\_type 为必传参数 |
    | trail\_value | float | 跟踪金额/百分比<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   当订单是**跟踪止损市价单**、**跟踪止损限价单**时，trail\_value 为必传参数<br>*   当跟踪类型为比例时，该字段为百分比字段，传入 20 实际对应 20%<br>*   当跟踪类型为金额时，整数部分同price；小数部分美股期权固定2位，美股4位，其他同price；超过部分四舍五入<br>*   当跟踪类型为比例时，精确到小数点后 2 位，整数部分同price，超过部分四舍五入 |
    | trail\_spread | float | 指定价差<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   当订单是**跟踪止损限价单**时，trail\_spread 为必传参数<br>*   证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超过部分四舍五入 |
    | session | [Session](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9152) | 美股交易时段<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅对美股生效，支持传入**RTH**、**ETH**、**OVERNIGHT**、**ALL** |
    | jp\_acc\_type | [SubAccType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6112) | 日本账户类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅日本券商适用 |
    | position\_id | int | 持仓ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   日本券商平仓时需要填写<br>*   可通过[查询持仓](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html)<br>    接口获取 |
    
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回订单列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   订单列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_side | [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815) | 交易方向 |
        | order\_type | [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181) | 订单类型 |
        | order\_status | [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797) | 订单状态 |
        | order\_id | str | 订单号 |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | qty | float | 订单数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
        | price | float | 订单价格<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分四舍五入 |
        | create\_time | str | 创建时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd HH:mm:ss  <br>期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
        | updated\_time | str | 最后更新时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd HH:mm:ss  <br>期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
        | dealt\_qty | float | 成交数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
        | dealt\_avg\_price | float | 成交均价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>无精度限制 |
        | last\_err\_msg | str | 最后的错误描述<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>如果有错误，会返回最后一次错误的原因  <br>如果无错误，返回空字符串 |
        | remark | str | 下单时备注的标识<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>详见 [place\_order](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)<br> 接口参数中的 remark |
        | time\_in\_force | [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241) | 有效期限 |
        | fill\_outside\_rth | bool | 是否允许盘前盘后（用于港股盘前竞价与美股盘前盘后）<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：允许  <br>False：不允许 |
        | aux\_price | float | 触发价格 |
        | trail\_type | [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644) | 跟踪类型 |
        | trail\_value | float | 跟踪金额/百分比 |
        | trail\_spread | float | 指定价差 |
        | session | [Session](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9152) | 交易订单时段（仅用于美股） |
        
*   **Example**
    

    from moomoo import *
    pwd_unlock = '123456'
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    ret, data = trd_ctx.unlock_trade(pwd_unlock)  # 若使用真实账户下单，需先对账户进行解锁。此处示例为模拟账户下单，也可省略解锁。
    if ret == RET_OK:
        ret, data = trd_ctx.place_order(price=510.0, qty=100, code="US.AAPL", trd_side=TrdSide.BUY, trd_env=TrdEnv.SIMULATE, session=Session.NONE)
        if ret == RET_OK:
            print(data)
            print(data['order_id'][0])  # 获取下单的订单号
            print(data['order_id'].values.tolist())  # 转为 list
        else:
            print('place_order error: ', data)
    else:
        print('unlock_trade failed: ', data)
    trd_ctx.close()
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

*   **Output**

    
           code stock_name trd_side order_type order_status           order_id    qty  price          create_time         updated_time  dealt_qty  dealt_avg_price last_err_msg remark time_in_force fill_outside_rth session aux_price trail_type trail_value trail_spread currency
    0  US.AAPL        苹果        BUY     NORMAL   SUBMITTING  38196006548709500  100.0  420.0  2021-11-04 11:38:19  2021-11-04 11:38:19        0.0              0.0                               DAY              N/A       N/A    N/A     N/A         N/A          N/A      USD
    38196006548709500
    ['38196006548709500']
    

1  
2  
3  
4  
5  

[#](https://openapi.futunn.com/futu-api-doc/trade/place-order.html#8194-2)
 Trd\_PlaceOrder.proto
-------------------------------------------------------------------------------------------------

*   **介绍**
    
    下单
    
*   **参数**
    

    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required int32 trdSide = 3; //交易方向，参见 Trd_Common.TrdSide 的枚举定义
    	required int32 orderType = 4; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 5; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double qty = 6; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃。期权期货单位是"张"）
    	optional double price = 7; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下2个为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 8; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 9; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 10; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string remark = 11; //用户备注字符串，最多只能传64字节。可用于标识订单唯一信息等，下单填上，订单结构就会带上。
    	optional int32 timeInForce = 12; //订单有效期限，参见 TrdCommon.TimeInForce 的枚举定义（香港市场、A 股市场和环球期货的市价单，仅支持当日有效）
    	optional bool fillOutsideRTH = 13; //是否允许盘前盘后成交（仅用于美股，且盘前盘后时段不支持市价单）。默认 false
    	optional double auxPrice = 14; //触发价格
    	optional int32 trailType = 15; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 16; //跟踪金额/百分比
    	optional double trailSpread = 17; //指定价差
        optional int32 session = 18; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 19; //持仓ID，日本券商平仓时使用
    }
    
    message Request
    {
    	required C2S c2s = 1;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  

> *   请求包标识结构参见 [PacketID](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   交易方向参见 [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815)
>     
> *   订单类型参见 [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181)
>     
> *   股票行情市场参见 [TrdSecMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5084)
>     
> *   订单有效期参见 [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241)
>     
> *   跟踪类型参见 [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644)
>     

*   **返回**

    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //表示服务器订单id，可以用来代替orderID，和orderID二选一		
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
    	required int32 retType = 1 [default = -400];
    	optional string retMsg = 2;
    	optional int32 errCode = 3;
    	
    	optional S2C s2c = 4;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2202
    

`uint PlaceOrder(TrdPlaceOrder.Request req);`  
`virtual void OnReply_PlaceOrder(MMAPI_Conn client, uint nSerialNo, TrdPlaceOrder.Response rsp);`

*   **介绍**
    
    下单
    
*   **参数**
    

    
    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required int32 trdSide = 3; //交易方向，参见 Trd_Common.TrdSide 的枚举定义
    	required int32 orderType = 4; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 5; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double qty = 6; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃。期权期货单位是"张"）
    	optional double price = 7; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下2个为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 8; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 9; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 10; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string remark = 11; //用户备注字符串，最多只能传64字节。可用于标识订单唯一信息等，下单填上，订单结构就会带上。
    	optional int32 timeInForce = 12; //订单有效期限，参见 TrdCommon.TimeInForce 的枚举定义
    	optional bool fillOutsideRTH = 13; //是否允许盘前盘后成交。仅适用于美股限价单。默认 false
    	optional double auxPrice = 14; //触发价格
    	optional int32 trailType = 15; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 16; //跟踪金额/百分比
    	optional double trailSpread = 17; //指定价差
        optional int32 session = 18; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 19; //持仓ID，日本券商平仓时使用
    }
    
    message Request
    {
    	required C2S c2s = 1;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  

> *   请求包标识结构参见 [PacketID](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   交易方向参见 [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815)
>     
> *   订单类型参见 [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181)
>     
> *   股票行情市场参见 [TrdSecMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5084)
>     
> *   订单有效期参见 [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241)
>     
> *   跟踪类型参见 [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644)
>     

*   **回调**

    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //表示服务器订单id，可以用来代替orderID，和orderID二选一		
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
    	required int32 retType = 1 [default = -400];
    	optional string retMsg = 2;
    	optional int32 errCode = 3;
    	
    	optional S2C s2c = 4;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : MMSPI_Trd, MMSPI_Conn {
        MMAPI_Trd trd = new MMAPI_Trd();
    
        public Program() {
            trd.SetClientInfo("csharp", 1);  //设置客户端信息
            trd.SetConnCallback(this);  //设置连接回调
            trd.SetTrdCallback(this);   //设置交易回调
        }
    
        public void Start() {
            trd.InitConnect("127.0.0.1", (ushort)11111, false);
        }
    
        
        public void OnInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Trd onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
            if (errCode != 0)
                return;
    
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.CreateBuilder()
                    .SetAccID(281756457888247915L)
                    .SetTrdEnv((int)TrdCommon.TrdEnv.TrdEnv_Simulate)
                    .SetTrdMarket((int)TrdCommon.TrdMarket.TrdMarket_HK)
                    .Build();
            TrdPlaceOrder.C2S c2s = TrdPlaceOrder.C2S.CreateBuilder()
                    .SetPacketID(trd.NextPacketID())
                    .SetHeader(header)
                    .SetTrdSide((int)TrdCommon.TrdSide.TrdSide_Buy)
                    .SetOrderType((int)TrdCommon.OrderType.OrderType_Normal)
                    .SetCode("00700")
                    .SetQty(100)
                    .SetPrice(520)
                    .SetSecMarket(TrdCommon.TrdSecMarket.TrdSecMarket_HK)
                .Build();
            TrdPlaceOrder.Request req = TrdPlaceOrder.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.PlaceOrder(req);
            Console.Write("Send TrdPlaceOrder: {0}\n", seqNo);
        }
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_PlaceOrder(MMAPI_Conn client, uint nSerialNo, TrdPlaceOrder.Response rsp)
        {
            Console.Write("Reply: TrdPlaceOrder: {0}\n", nSerialNo);
            Console.Write("accID: {0}\n", rsp.S2C.Header.AccID);
        }
    
        public static void Main(String[] args) {
            MMAPI.Init();
            Program trd = new Program();
            trd.Start();
    
            while (true)
                Thread.Sleep(1000 * 600);
        }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  
30  
31  
32  
33  
34  
35  
36  
37  
38  
39  
40  
41  
42  
43  
44  
45  
46  
47  
48  
49  
50  
51  
52  
53  
54  
55  
56  
57  
58  
59  

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6827788355222092042
    Send TrdPlaceOrder: 3
    Reply: TrdPlaceOrder: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int placeOrder(TrdPlaceOrder.Request req);`  
`void onReply_PlaceOrder(MMAPI_Conn client, int nSerialNo, TrdPlaceOrder.Response rsp);`

*   **介绍**
    
    下单
    
*   **参数**
    

    
    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required int32 trdSide = 3; //交易方向，参见 Trd_Common.TrdSide 的枚举定义
    	required int32 orderType = 4; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 5; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double qty = 6; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃。期权期货单位是"张"）
    	optional double price = 7; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下2个为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 8; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 9; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 10; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string remark = 11; //用户备注字符串，最多只能传64字节。可用于标识订单唯一信息等，下单填上，订单结构就会带上。
    	optional int32 timeInForce = 12; //订单有效期限，参见 TrdCommon.TimeInForce 的枚举定义
    	optional bool fillOutsideRTH = 13; //是否允许盘前盘后成交。仅适用于美股限价单。默认 false
    	optional double auxPrice = 14; //触发价格
    	optional int32 trailType = 15; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 16; //跟踪金额/百分比
    	optional double trailSpread = 17; //指定价差
        optional int32 session = 18; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 19; //持仓ID，日本券商平仓时使用
    }
    
    message Request
    {
    	required C2S c2s = 1;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  

> *   请求包标识结构参见 [PacketID](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   交易方向参见 [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815)
>     
> *   订单类型参见 [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181)
>     
> *   股票行情市场参见 [TrdSecMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5084)
>     
> *   订单有效期参见 [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241)
>     
> *   跟踪类型参见 [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644)
>     

*   **回调**

    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //表示服务器订单id，可以用来代替orderID，和orderID二选一		
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
    	required int32 retType = 1 [default = -400];
    	optional string retMsg = 2;
    	optional int32 errCode = 3;
    	
    	optional S2C s2c = 4;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class TrdDemo implements MMSPI_Trd, MMSPI_Conn {
        MMAPI_Conn_Trd trd = new MMAPI_Conn_Trd();
    
        public TrdDemo() {
            trd.setClientInfo("javaclient", 1);  //设置客户端信息
            trd.setConnSpi(this);  //设置连接回调
            trd.setTrdSpi(this);   //设置交易回调
        }
    
        public void start() {
            trd.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Trd onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
            if (errCode != 0)
                return;
    
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.newBuilder()
                    .setAccID(281756457888247915L)
                    .setTrdEnv(TrdCommon.TrdEnv.TrdEnv_Simulate_VALUE)
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)
                    .build();
            TrdPlaceOrder.C2S c2s = TrdPlaceOrder.C2S.newBuilder()
                    .setPacketID(trd.nextPacketID())
                    .setHeader(header)
                    .setTrdSide(TrdCommon.TrdSide.TrdSide_Buy_VALUE)
                    .setOrderType(TrdCommon.OrderType.OrderType_Normal_VALUE)
                    .setSecMarket(TrdCommon.TrdSecMarket.TrdSecMarket_HK_VALUE)
                    .setCode("00700")
                    .setQty(100)
                    .setPrice(580)
                .build();
            TrdPlaceOrder.Request req = TrdPlaceOrder.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.placeOrder(req);
            System.out.printf("Send TrdPlaceOrder: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_PlaceOrder(MMAPI_Conn client, int nSerialNo, TrdPlaceOrder.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdPlaceOrder failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdPlaceOrder: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public static void main(String[] args) {
            MMAPI.init();
            TrdDemo trd = new TrdDemo();
            trd.start();
    
            while (true) {
                try {
                    Thread.sleep(1000 * 600);
                } catch (InterruptedException exc) {
    
                }
            }
        }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  
30  
31  
32  
33  
34  
35  
36  
37  
38  
39  
40  
41  
42  
43  
44  
45  
46  
47  
48  
49  
50  
51  
52  
53  
54  
55  
56  
57  
58  
59  
60  
61  
62  
63  
64  
65  
66  
67  
68  
69  
70  
71  
72  
73  
74  

*   **Output**

    Send TrdPlaceOrder: 2
    Receive TrdPlaceOrder: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 0,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "orderID": "5185481028427965890"
      }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

`moomoo::u32_t PlaceOrder(const Trd_PlaceOrder::Request &stReq);`  
`virtual void OnReply_PlaceOrder(moomoo::u32_t nSerialNo, const Trd_PlaceOrder::Response &stRsp) = 0;`

*   **介绍**
    
    下单
    
*   **参数**
    

    
    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required int32 trdSide = 3; //交易方向，参见 Trd_Common.TrdSide 的枚举定义
    	required int32 orderType = 4; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 5; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double qty = 6; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃。期权期货单位是"张"）
    	optional double price = 7; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下2个为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 8; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 9; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 10; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string remark = 11; //用户备注字符串，最多只能传64字节。可用于标识订单唯一信息等，下单填上，订单结构就会带上。
    	optional int32 timeInForce = 12; //订单有效期限，参见 TrdCommon.TimeInForce 的枚举定义
    	optional bool fillOutsideRTH = 13; //是否允许盘前盘后成交。仅适用于美股限价单。默认 false
    	optional double auxPrice = 14; //触发价格
    	optional int32 trailType = 15; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 16; //跟踪金额/百分比
    	optional double trailSpread = 17; //指定价差
        optional int32 session = 18; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 19; //持仓ID，日本券商平仓时使用
    }
    
    message Request
    {
    	required C2S c2s = 1;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  

> *   请求包标识结构参见 [PacketID](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   交易方向参见 [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815)
>     
> *   订单类型参见 [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181)
>     
> *   股票行情市场参见 [TrdSecMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5084)
>     
> *   订单有效期参见 [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241)
>     
> *   跟踪类型参见 [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644)
>     

*   **回调**

    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //表示服务器订单id，可以用来代替orderID，和orderID二选一		
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
    	required int32 retType = 1 [default = -400];
    	optional string retMsg = 2;
    	optional int32 errCode = 3;
    	
    	optional S2C s2c = 4;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    class Program : public MMSPI_Qot, public MMSPI_Trd, public MMSPI_Conn
    {
    public:
    
    	Program() {
    		m_pTrdApi = MMAPI::CreateTrdApi();
    		m_pTrdApi->RegisterTrdSpi(this);
    		m_pTrdApi->RegisterConnSpi(this);
    	}
    
    	~Program() {
    		if (m_pTrdApi != nullptr)
    		{
    			m_pTrdApi->UnregisterTrdSpi();
    			m_pTrdApi->UnregisterConnSpi();
    			MMAPI::ReleaseTrdApi(m_pTrdApi);
    			m_pTrdApi = nullptr;
    		}
    	}
    
    	void Start() {
    		m_pTrdApi->InitConnect("127.0.0.1", 11111, false);
    	}
    
    
    	virtual void OnInitConnect(MMAPI_Conn* pConn, moomoo::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		// 组包
    		Trd_PlaceOrder::Request req;
    		Trd_PlaceOrder::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    		c2s->set_trdside(1);
    		c2s->set_ordertype(1);
    		c2s->set_code("00700");
    		c2s->set_qty(100);
    		c2s->set_price(1);
    		c2s->set_secmarket(Trd_Common::TrdMarket::TrdMarket_HK);
    
            m_PlaceOrderSerialNo = m_pTrdApi->PlaceOrder(req);
            cout << "Request PlaceOrder SerialNo: " << m_PlaceOrderSerialNo << endl;
    	}
    
    	virtual void OnReply_PlaceOrder(moomoo::u32_t nSerialNo, const Trd_PlaceOrder::Response &stRsp){
            if(nSerialNo == m_PlaceOrderSerialNo)
            {
                cout << "OnReply_PlaceOrder SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Trd *m_pTrdApi;
    
        moomoo::u32_t m_PlaceOrderSerialNo;
    };
    
    int32_t main(int32_t argc, char** argv)
    {
    	MMAPI::Init();
    
    	{
    		Program program;
    		program.Start();
    		getchar();
    	}
    
    	protobuf::ShutdownProtobufLibrary();
    	MMAPI::UnInit();
    	return 0;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  
30  
31  
32  
33  
34  
35  
36  
37  
38  
39  
40  
41  
42  
43  
44  
45  
46  
47  
48  
49  
50  
51  
52  
53  
54  
55  
56  
57  
58  
59  
60  
61  
62  
63  
64  
65  
66  
67  
68  
69  
70  
71  
72  
73  
74  
75  
76  
77  
78  

*   **Output**

    connect
    Request PlaceOrder SerialNo: 4
    OnReply_PlaceOrder SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "header": {
       "trdEnv": 0,
       "accID": "3637840",
       "trdMarket": 1
      },
      "orderID": "3954832860392428914"
     }
    }
    
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  

`PlaceOrder(req);`

*   **介绍**
    
    下单
    
*   **参数**
    

    
    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required int32 trdSide = 3; //交易方向，参见 Trd_Common.TrdSide 的枚举定义
    	required int32 orderType = 4; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 5; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double qty = 6; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃。期权期货单位是"张"）
    	optional double price = 7; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下2个为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 8; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 9; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 10; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string remark = 11; //用户备注字符串，最多只能传64字节。可用于标识订单唯一信息等，下单填上，订单结构就会带上。
    	optional int32 timeInForce = 12; //订单有效期限，参见 TrdCommon.TimeInForce 的枚举定义
    	optional bool fillOutsideRTH = 13; //是否允许盘前盘后成交。仅适用于美股限价单。默认 false
    	optional double auxPrice = 14; //触发价格
    	optional int32 trailType = 15; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 16; //跟踪金额/百分比
    	optional double trailSpread = 17; //指定价差
        optional int32 session = 18; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 19; //持仓ID，日本券商平仓时使用
    }
    
    message Request
    {
    	required C2S c2s = 1;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  

> *   请求包标识结构参见 [PacketID](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   交易方向参见 [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815)
>     
> *   订单类型参见 [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181)
>     
> *   股票行情市场参见 [TrdSecMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5084)
>     
> *   订单有效期参见 [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241)
>     
> *   跟踪类型参见 [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644)
>     

*   **返回**

    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //表示服务器订单id，可以用来代替orderID，和orderID二选一		
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
    	required int32 retType = 1 [default = -400];
    	optional string retMsg = 2;
    	optional int32 errCode = 3;
    	
    	optional S2C s2c = 4;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function TrdPlaceOrder(){
        const { RetType, PacketID } = Common
        const { TrdEnv, TrdSide, OrderType, SecurityFirm, TrdMarket, TrdSecMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        let tradeSerialNo = 0;
        websocket.onlogin = async ()=>{
            try{
                let { errCode, retMsg, retType } = await websocket.UnlockTrade({
                    c2s: {
                        unlock: true,
                        securityFirm: SecurityFirm.SecurityFirm_FutuSecurities,
                        pwdMD5: "d0970714757783e6cf17b26fb8e2298f", // 设置为自己账号的交易密码MD5
                    },
                });
                if(retType == RetType.RetType_Succeed && errCode == 0) { // 解锁交易成功
                    let { errCode, retMsg, retType, s2c: { accList } } = await websocket.GetAccList({
                        c2s: {
                            userID: 0,
                        },
                    });
                    if(retType == RetType.RetType_Succeed && errCode == 0) { // 获取账户成功
                        let acc = accList.filter((item)=>{ 
                            return item.trdEnv == TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})
                        })[0]; // 样例取第一个香港市场虚拟环境账户
                        
                        const req = {
                            c2s: {
                                packetID:{
                                    connID: websocket.getConnID(),
                                    serialNo: tradeSerialNo++,
                                },
                                header: {
                                    trdEnv: acc.trdEnv,
                                    accID: acc.accID,
                                    trdMarket: TrdMarket.TrdMarket_HK,
                                },
                                trdSide: TrdSide.TrdSide_Buy,
                                orderType: OrderType.OrderType_Normal,
                                code: "00700",
                                qty: 100,
                                price: 150,
                                secMarket: TrdSecMarket.TrdSecMarket_HK,
                            },
                        };
                        let { errCode, retMsg, retType, s2c } = await websocket.PlaceOrder(req);
                        console.log("PlaceOrder: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                        if(retType == RetType.RetType_Succeed){
                            let data = beautify(JSON.stringify(s2c), {
                                indent_size: 2,
                                space_in_empty_paren: true,
                            });
                            console.log(data);
                        }
                    }
                }
            }
            catch(err){
                console.log(err)
            }
            
        };
    
        websocket.start(addr, port, enable_ssl, key);
        
        //关闭行情连接，连接不再使用之后，要关闭，否则占用不必要资源
        //同时OpenD也限制了最多128条连接
        //也可以一个页面或者一个项目维护一条连接，这里范例请求一次创建一条连接
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 5000); // 5秒后断开
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  
30  
31  
32  
33  
34  
35  
36  
37  
38  
39  
40  
41  
42  
43  
44  
45  
46  
47  
48  
49  
50  
51  
52  
53  
54  
55  
56  
57  
58  
59  
60  
61  
62  
63  
64  
65  
66  
67  
68  
69  
70  
71  
72  
73  
74  
75  
76  
77  
78  
79  

*   **Output**

    PlaceOrder: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6684972",
        "trdMarket": 1
      },
      "orderID": "5870498377428972628"
    }
    stop
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 15 次下单接口，且连续两次请求的间隔不可小于 0.02 秒。
*   真实账户调用下单接口前，需要先进行 [解锁](https://openapi.futunn.com/futu-api-doc/trade/unlock.html)
    ；模拟账户无需解锁。

提示

*   各订单类型对应的必传参数：[点击这里](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2984)
     了解更多
*   各券商针对不同交易品种，对单笔订单股数有所限制，超出限制会导致下单失败：[点击这里](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2984)
     了解更多
*   对于 **可做空标的**，暂不支持锁仓功能，故无法同时持有相同产品的多头头寸和空头头寸。
*   如果希望对 **可做空标的** 进行 **平仓** 操作，需要自行判断持仓头寸的方向，然后提交一笔反向的相同数量的订单完成平仓操作。
*   如果希望对 **可做空标的** 进行 **反手** 操作，需要两步：1. 先判断持仓头寸的方向，并提交一笔反向的相同数量的订单完成平仓操作；2. 提交一笔反向的订单，完成反向订单的提交。  
    举例：A 当前持有 1 手 HK.HSI2012 期货合约的多单，如果希望反手，必须先 卖出 1 手 HK.HSI2012 完成平仓，再卖出 1 手 HK.HSI2012 完成空单的建立。
*   美股全时段交易，仅支持限价单，订单期限可以选择当日有效或撤单前有效。选择全时段，交易者可以一次挂单参与多个时段（夜盘、盘前、盘中、盘后时段）的交易，全时段交易时间是星期日到星期四 20:00 - 次日20:00（美东时间）
*   美股模拟交易不支持盘前盘后与夜盘。

← [查询账户现金流水](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html) [改单撤单](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html)
 →

[下单](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)