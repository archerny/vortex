 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/get-order-list.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/get-order-list.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/get-order-list.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/get-order-list.html)
    

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
    

[#](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html#5646)
 查询未完成订单
====================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`order_list_query(order_id="", order_market=TrdMarket.NONE, status_filter_list=[], code='', start='', end='', trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, refresh_cache=False)`

*   **介绍**
    
    查询指定交易业务账户的未完成订单列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | order\_id | str | 订单号过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   返回指定订单号的数据<br>*   默认状态时，返回所有数据 |
    | order\_market | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 订单标的所属市场过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   订单标的市场过滤，会返回该市场下的标的订单<br>*   默认值为NONE，会返回账户下所有市场的订单数据 |
    | status\_filter\_list | list | 订单状态过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   返回指定状态的订单数据<br>*   默认状态时，返回所有数据<br>*   list 中元素类型是 [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797) |
    | code | str | 代码过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   返回指定代码的数据<br>*   默认状态时，返回所有数据 |
    | start | str | 开始时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传<br>*   期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
    | end | str | 结束时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传<br>*   期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
    | trd\_env | [TrdEnv](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | refresh\_cache | bool | 是否刷新缓存<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   True：立即向富途服务器重新请求数据，不使用 OpenD 的缓存，此时会受到接口限频的限制<br>*   False：使用 OpenD 的缓存（特殊情况导致缓存没有及时更新才需要刷新） |
    

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
        | order\_market | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 订单标的所属市场 |
        | qty | float | 订单数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
        | price | float | 订单价格<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分四舍五入 |
        | currency | [Currency](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8019) | 交易货币 |
        | create\_time | str | 创建时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
        | updated\_time | str | 最后更新时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
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
        | jp\_acc\_type | [SubAccType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6112) | 日本账户类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅对日本券商生效 |
        
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.order_list_query()
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果订单列表不为空
            print(data['order_id'][0])  # 获取未完成订单的第一个订单号
            print(data['order_id'].values.tolist())  # 转为 list
    else:
        print('order_list_query error: ', data)
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

*   **Output**

            code stock_name  order_market   trd_side           order_type   order_status             order_id    qty  price              create_time             updated_time  dealt_qty  dealt_avg_price last_err_msg      remark time_in_force fill_outside_rth session aux_price trail_type trail_value trail_spread currency jp_acc_type
    0   HK.00700        HK         BUY           NORMAL  CANCELLED_ALL  6644468615272262086  100.0  520.0  2021-09-06 10:17:52.465  2021-09-07 16:10:22.806        0.0              0.0               asdfg+=@@@           GTC        N/A      N/A       560        N/A         N/A          N/A      HKD        N/A
    6644468615272262086
    ['6644468615272262086']
    

1  
2  
3  
4  

[#](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html#8225)
 Trd\_GetOrderList.proto
----------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询指定交易业务账户的未完成订单列表
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
        repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
        optional bool refreshCache = 4; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
        //正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
        //如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单状态结构，参见 [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **返回**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        repeated Trd_Common.Order orderList = 2; //订单列表
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构，参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2201
    

`uint GetOrderList(TrdGetOrderList.Request req);`  
`virtual void OnReply_GetOrderList(FTAPI_Conn client, uint nSerialNo, TrdGetOrderList.Response rsp);`

*   **介绍**

查询指定交易业务账户的未完成订单列表

*   **参数**

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
        repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
        optional bool refreshCache = 4; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
        //正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
        //如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单状态结构，参见 [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **回调**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        repeated Trd_Common.Order orderList = 2; //订单列表
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构，参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
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
                    .SetTrdEnv((int)TrdCommon.TrdEnv.TrdEnv_Real)
                    .SetTrdMarket((int)TrdCommon.TrdMarket.TrdMarket_HK)
                    .Build();
            TrdGetOrderList.C2S c2s = TrdGetOrderList.C2S.CreateBuilder()
                    .SetHeader(header)
                .Build();
            TrdGetOrderList.Request req = TrdGetOrderList.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetOrderList(req);
            Console.Write("Send TrdGetOrderList: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_GetOrderList(FTAPI_Conn client, uint nSerialNo, TrdGetOrderList.Response rsp)
        {
            Console.Write("Reply: TrdGetOrderList: {0}\n", nSerialNo);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6827793030286254504
    Send TrdGetOrderList: 3
    Reply: TrdGetOrderList: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int getOrderList(TrdGetOrderList.Request req);`  
`void onReply_GetOrderList(FTAPI_Conn client, int nSerialNo, TrdGetOrderList.Response rsp);`

*   **介绍**

查询指定交易业务账户的未完成订单列表

*   **参数**

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
        repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
        optional bool refreshCache = 4; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
        //正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
        //如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单状态结构，参见 [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **回调**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        repeated Trd_Common.Order orderList = 2; //订单列表
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构，参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
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
                    .setTrdEnv(TrdCommon.TrdEnv.TrdEnv_Real_VALUE)
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)
                    .build();
            TrdGetOrderList.C2S c2s = TrdGetOrderList.C2S.newBuilder()
                    .setHeader(header)
                .build();
            TrdGetOrderList.Request req = TrdGetOrderList.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getOrderList(req);
            System.out.printf("Send TrdGetOrderList: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetOrderList(FTAPI_Conn client, int nSerialNo, TrdGetOrderList.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetOrderList failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetOrderList: %s\n", json);
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

*   **Output**

    Send TrdGetOrderList: 2
    Receive TrdGetOrderList: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 0,
          "accID": "3450309",
          "trdMarket": 1
        },
        "orderList": [{\
          "trdSide": 1,\
          "orderType": 5,\
          "orderStatus": 11,\
          "orderID": "5185261023022402893",\
          "orderIDEx": "2691191",\
          "code": "00700",\
          "name": "腾讯控股",\
          "qty": 100.0,\
          "price": 594.0,\
          "createTime": "2021-06-25 11:38:30",\
          "updateTime": "2021-06-25 11:38:30",\
          "fillQty": 100.0,\
          "fillAvgPrice": 594.0,\
          "secMarket": 1,\
          "createTimestamp": 1.62459231E9,\
          "updateTimestamp": 1.62459231E9,\
          "remark": "",\
          "timeInForce": 0\
        }]
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

`Futu::u32_t GetOrderList(const Trd_GetOrderList::Request &stReq);`  
`virtual void OnReply_GetOrderList(Futu::u32_t nSerialNo, const Trd_GetOrderList::Response &stRsp) = 0;`

*   **介绍**
    
    查询指定交易业务账户的未完成订单列表
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
        repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
        optional bool refreshCache = 4; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
        //正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
        //如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单状态结构，参见 [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **回调**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        repeated Trd_Common.Order orderList = 2; //订单列表
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构，参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
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
    		Trd_GetOrderList::Request req;
    		Trd_GetOrderList::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    		auto filterStatusList = c2s->mutable_filterstatuslist();
    		filterStatusList->Add(Trd_Common::OrderStatus::OrderStatus_Filled_All);
    
            m_GetOrderListSerialNo = m_pTrdApi->GetOrderList(req);
            cout << "Request GetOrderList SerialNo: " << m_GetOrderListSerialNo << endl;
    	}
    
    	virtual void OnReply_GetOrderList(Futu::u32_t nSerialNo, const Trd_GetOrderList::Response &stRsp){
            if(nSerialNo == m_GetOrderListSerialNo)
            {
                cout << "OnReply_GetOrderList SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_GetOrderListSerialNo;
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

*   **Output**

    connect
    Request GetOrderList SerialNo: 4
    OnReply_GetOrderList SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "header": {
       "trdEnv": 0,
       "accID": "3637840",
       "trdMarket": 1
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

`GetOrderList(req);`

*   **介绍**
    
    查询指定交易业务账户的未完成订单列表
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
        repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
        optional bool refreshCache = 4; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
        //正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
        //如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单状态结构，参见 [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **返回**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        repeated Trd_Common.Order orderList = 2; //订单列表
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构，参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetOrderList(){
        const { RetType } = Common
        const { TrdEnv, TrdMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ec16fde057a2e7a0'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.GetAccList({
                    c2s: {
                        userID: 0,
                    },
                }).then((res) => {
                    let { retType,s2c: { accList } } = res
                    if(retType == RetType.RetType_Succeed){
                        let acc = accList.filter((item)=>{ 
                            return item.trdEnv == TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})
                        })[0]; // 样例取第一个香港市场虚拟环境账户
    
                        const req = {
                            c2s: {
                                header: {
                                    trdEnv: acc.trdEnv,
                                    accID: acc.accID,
                                    trdMarket: TrdMarket.TrdMarket_HK,
                                },
                            },
                        };
    
                        websocket.GetOrderList(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetOrderList: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                            if(retType == RetType.RetType_Succeed){
                                let data = beautify(JSON.stringify(s2c), {
                                    indent_size: 2,
                                    space_in_empty_paren: true,
                                });
                                console.log(data);
                            }
                        })
                        .catch((error) => {
                            console.log("error:", error);
                        });
    
                    }
                })
                .catch((error) => {
                    console.log("GetAccList error:", error);
                });
            } else {
                console.log("error", msg);
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

*   **Output**

    GetOrderList: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6378",
        "trdMarket": 1
      },
      "orderList": [{\
        "trdSide": 1,\
        "orderType": 1,\
        "orderStatus": 2,\
        "orderID": "6520476875838699625",\
        "orderIDEx": "262973",\
        "code": "00700",\
        "name": "腾讯控股",\
        "qty": 100,\
        "price": 480,\
        "createTime": "2021-09-13 17:07:10",\
        "updateTime": "2021-09-13 17:07:10",\
        "fillQty": 0,\
        "fillAvgPrice": 0,\
        "secMarket": 1,\
        "createTimestamp": 1631524030,\
        "updateTimestamp": 1631524030,\
        "remark": "",\
        "timeInForce": 0\
      }]
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

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询未完成订单接口
*   调用此接口，只有在刷新缓存时，才受到限频限制

提示

*   未完成订单，按照时间的“顺序”进行排列，即：先提交的订单在前，后提交的订单在后

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`order_list_query(order_id="", order_market=TrdMarket.NONE, status_filter_list=[], code='', start='', end='', trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, refresh_cache=False)`

*   **介绍**
    
    查询指定交易业务账户的未完成订单列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | order\_id | str | 订单号过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   返回指定订单号的数据<br>*   默认状态时，返回所有数据 |
    | order\_market | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 订单标的所属市场过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   订单标的市场过滤，会返回该市场下的标的订单<br>*   默认值为NONE，会返回账户下所有市场的订单数据 |
    | status\_filter\_list | list | 订单状态过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   返回指定状态的订单数据<br>*   默认状态时，返回所有数据<br>*   list 中元素类型是 [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797) |
    | code | str | 代码过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   返回指定代码的数据<br>*   默认状态时，返回所有数据 |
    | start | str | 开始时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传<br>*   期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
    | end | str | 结束时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传<br>*   期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
    | trd\_env | [TrdEnv](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | refresh\_cache | bool | 是否刷新缓存<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   True：立即向 moomoo 服务器重新请求数据，不使用 OpenD 的缓存，此时会受到接口限频的限制<br>*   False：使用 OpenD 的缓存（特殊情况导致缓存没有及时更新才需要刷新） |
    

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
        | order\_market | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 订单标的所属市场 |
        | qty | float | 订单数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
        | price | float | 订单价格<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分四舍五入 |
        | currency | [Currency](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8019) | 交易货币 |
        | create\_time | str | 创建时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
        | updated\_time | str | 最后更新时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
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
        | jp\_acc\_type | [SubAccType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6112) | 日本账户类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅对日本券商生效 |
        
*   **Example**
    

    from moomoo import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    ret, data = trd_ctx.order_list_query()
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果订单列表不为空
            print(data['order_id'][0])  # 获取未完成订单的第一个订单号
            print(data['order_id'].values.tolist())  # 转为 list
    else:
        print('order_list_query error: ', data)
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

*   **Output**

            code stock_name   order_amrket      trd_side           order_type   order_status             order_id    qty  price              create_time             updated_time  dealt_qty  dealt_avg_price last_err_msg      remark time_in_force fill_outside_rth session aux_price trail_type trail_value trail_spread currency jp_acc_type
    0   US.AAPL         US          BUY           NORMAL  CANCELLED_ALL  6644468615272262086  100.0  520.0  2021-09-06 10:17:52.465  2021-09-07 16:10:22.806        0.0              0.0               asdfg+=@@@           GTC      N/A        N/A       560        N/A         N/A          N/A      USD        N/A
    6644468615272262086
    ['6644468615272262086']
    

1  
2  
3  
4  

[#](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html#8225-2)
 Trd\_GetOrderList.proto
------------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询指定交易业务账户的未完成订单列表
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
        repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
        optional bool refreshCache = 4; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
        //正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
        //如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单状态结构，参见 [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **返回**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        repeated Trd_Common.Order orderList = 2; //订单列表
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构，参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2201
    

`uint GetOrderList(TrdGetOrderList.Request req);`  
`virtual void OnReply_GetOrderList(MMAPI_Conn client, uint nSerialNo, TrdGetOrderList.Response rsp);`

*   **介绍**

查询指定交易业务账户的未完成订单列表

*   **参数**

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
        repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
        optional bool refreshCache = 4; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
        //正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
        //如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单状态结构，参见 [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **回调**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        repeated Trd_Common.Order orderList = 2; //订单列表
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构，参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
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
                    .SetTrdEnv((int)TrdCommon.TrdEnv.TrdEnv_Real)
                    .SetTrdMarket((int)TrdCommon.TrdMarket.TrdMarket_HK)
                    .Build();
            TrdGetOrderList.C2S c2s = TrdGetOrderList.C2S.CreateBuilder()
                    .SetHeader(header)
                .Build();
            TrdGetOrderList.Request req = TrdGetOrderList.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetOrderList(req);
            Console.Write("Send TrdGetOrderList: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_GetOrderList(MMAPI_Conn client, uint nSerialNo, TrdGetOrderList.Response rsp)
        {
            Console.Write("Reply: TrdGetOrderList: {0}\n", nSerialNo);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6827793030286254504
    Send TrdGetOrderList: 3
    Reply: TrdGetOrderList: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int getOrderList(TrdGetOrderList.Request req);`  
`void onReply_GetOrderList(MMAPI_Conn client, int nSerialNo, TrdGetOrderList.Response rsp);`

*   **介绍**

查询指定交易业务账户的未完成订单列表

*   **参数**

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
        repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
        optional bool refreshCache = 4; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
        //正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
        //如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单状态结构，参见 [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **回调**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        repeated Trd_Common.Order orderList = 2; //订单列表
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构，参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
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
                    .setTrdEnv(TrdCommon.TrdEnv.TrdEnv_Real_VALUE)
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)
                    .build();
            TrdGetOrderList.C2S c2s = TrdGetOrderList.C2S.newBuilder()
                    .setHeader(header)
                .build();
            TrdGetOrderList.Request req = TrdGetOrderList.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getOrderList(req);
            System.out.printf("Send TrdGetOrderList: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetOrderList(MMAPI_Conn client, int nSerialNo, TrdGetOrderList.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetOrderList failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetOrderList: %s\n", json);
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

*   **Output**

    Send TrdGetOrderList: 2
    Receive TrdGetOrderList: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 0,
          "accID": "3450309",
          "trdMarket": 1
        },
        "orderList": [{\
          "trdSide": 1,\
          "orderType": 5,\
          "orderStatus": 11,\
          "orderID": "5185261023022402893",\
          "orderIDEx": "2691191",\
          "code": "00700",\
          "name": "腾讯控股",\
          "qty": 100.0,\
          "price": 594.0,\
          "createTime": "2021-06-25 11:38:30",\
          "updateTime": "2021-06-25 11:38:30",\
          "fillQty": 100.0,\
          "fillAvgPrice": 594.0,\
          "secMarket": 1,\
          "createTimestamp": 1.62459231E9,\
          "updateTimestamp": 1.62459231E9,\
          "remark": "",\
          "timeInForce": 0\
        }]
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

`moomoo::u32_t GetOrderList(const Trd_GetOrderList::Request &stReq);`  
`virtual void OnReply_GetOrderList(moomoo::u32_t nSerialNo, const Trd_GetOrderList::Response &stRsp) = 0;`

*   **介绍**
    
    查询指定交易业务账户的未完成订单列表
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
        repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
        optional bool refreshCache = 4; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
        //正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
        //如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单状态结构，参见 [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **回调**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        repeated Trd_Common.Order orderList = 2; //订单列表
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构，参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
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
    		Trd_GetOrderList::Request req;
    		Trd_GetOrderList::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    		auto filterStatusList = c2s->mutable_filterstatuslist();
    		filterStatusList->Add(Trd_Common::OrderStatus::OrderStatus_Filled_All);
    
            m_GetOrderListSerialNo = m_pTrdApi->GetOrderList(req);
            cout << "Request GetOrderList SerialNo: " << m_GetOrderListSerialNo << endl;
    	}
    
    	virtual void OnReply_GetOrderList(moomoo::u32_t nSerialNo, const Trd_GetOrderList::Response &stRsp){
            if(nSerialNo == m_GetOrderListSerialNo)
            {
                cout << "OnReply_GetOrderList SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Trd *m_pTrdApi;
    
        moomoo::u32_t m_GetOrderListSerialNo;
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

*   **Output**

    connect
    Request GetOrderList SerialNo: 4
    OnReply_GetOrderList SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "header": {
       "trdEnv": 0,
       "accID": "3637840",
       "trdMarket": 1
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

`GetOrderList(req);`

*   **介绍**
    
    查询指定交易业务账户的未完成订单列表
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
        repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
        optional bool refreshCache = 4; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
        //正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
        //如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单状态结构，参见 [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **返回**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
        repeated Trd_Common.Order orderList = 2; //订单列表
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构，参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetOrderList(){
        const { RetType } = Common
        const { TrdEnv, TrdMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ec16fde057a2e7a0'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.GetAccList({
                    c2s: {
                        userID: 0,
                    },
                }).then((res) => {
                    let { retType,s2c: { accList } } = res
                    if(retType == RetType.RetType_Succeed){
                        let acc = accList.filter((item)=>{ 
                            return item.trdEnv == TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})
                        })[0]; // 样例取第一个香港市场虚拟环境账户
    
                        const req = {
                            c2s: {
                                header: {
                                    trdEnv: acc.trdEnv,
                                    accID: acc.accID,
                                    trdMarket: TrdMarket.TrdMarket_HK,
                                },
                            },
                        };
    
                        websocket.GetOrderList(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetOrderList: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                            if(retType == RetType.RetType_Succeed){
                                let data = beautify(JSON.stringify(s2c), {
                                    indent_size: 2,
                                    space_in_empty_paren: true,
                                });
                                console.log(data);
                            }
                        })
                        .catch((error) => {
                            console.log("error:", error);
                        });
    
                    }
                })
                .catch((error) => {
                    console.log("GetAccList error:", error);
                });
            } else {
                console.log("error", msg);
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

*   **Output**

    GetOrderList: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6378",
        "trdMarket": 1
      },
      "orderList": [{\
        "trdSide": 1,\
        "orderType": 1,\
        "orderStatus": 2,\
        "orderID": "6520476875838699625",\
        "orderIDEx": "262973",\
        "code": "00700",\
        "name": "腾讯控股",\
        "qty": 100,\
        "price": 480,\
        "createTime": "2021-09-13 17:07:10",\
        "updateTime": "2021-09-13 17:07:10",\
        "fillQty": 0,\
        "fillAvgPrice": 0,\
        "secMarket": 1,\
        "createTimestamp": 1631524030,\
        "updateTimestamp": 1631524030,\
        "remark": "",\
        "timeInForce": 0\
      }]
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

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询未完成订单接口
*   调用此接口，只有在刷新缓存时，才受到限频限制

提示

*   未完成订单，按照时间的“顺序”进行排列，即：先提交的订单在前，后提交的订单在后

← [改单撤单](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html) [查询历史订单](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-list.html)
 →

[查询未完成订单](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html)