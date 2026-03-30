# 订阅反订阅 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quote/sub.html

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#8723)
 订阅反订阅
=======================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2263)
 **订阅**
------------------------------------------------------------------------

`subscribe(code_list, subtype_list, is_first_push=True, subscribe_push=True, is_detailed_orderbook=False, extended_time=False, session=Session.NONE)`

*   **介绍**
    
    订阅注册需要的实时信息，指定股票和订阅的数据类型即可。  
    香港市场（含正股、窝轮、牛熊、期权、期货）订阅，需要 LV1 及以上的权限，BMP 权限下不支持订阅。  
    美股市场（含正股、ETFs）夜盘行情订阅，需要 LV1 及以上的权限，BMP 权限下不支持订阅。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 需要订阅的股票代码列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 str |
    | subtype\_list | list | 需要订阅的数据类型列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878) |
    | is\_first\_push | bool | 订阅成功之后是否立即推送一次缓存数据<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   True：推送缓存  <br>    当脚本和 OpenD 之间出现断线重连，重新订阅时若设置为 True，会再次推送断线前的最后一条数据<br>*   False：不推送缓存。等待服务器的最新推送 |
    | subscribe\_push | bool | 订阅后是否推送<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>订阅后，OpenD 提供了[两种取数据的方式](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)<br>，如果您仅使用 **获取实时数据** 的方式，选择 False 可以节省一部分性能消耗<br><br>*   True：推送。如果使用 **实时数据回调** 的方式，则必须设置为 True<br>*   False：不推送。如果**仅**使用 **获取实时数据** 的方式，则建议设置为 False |
    | is\_detailed\_orderbook | bool | 是否订阅详细的摆盘订单明细<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   仅用于港股 SF 行情权限下订阅港股 ORDER\_BOOK 类型<br>*   美股美期 LV2 权限下不提供详细摆盘订单明细 |
    | extended\_time | bool | 是否允许美股盘前盘后数据<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅用于订阅美股实时 K 线、实时分时、实时逐笔 |
    | session | [Session](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9152) | 美股订阅时段<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   仅用于订阅美股实时 K 线、实时分时、实时逐笔<br>*   订阅美股行情不支持入参OVERNIGHT<br>*   最低OpenD版本：9.2.4207 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | err\_message | NoneType | 当 ret == RET\_OK 时，返回 None |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
*   **Example**
    

    import time
    from futu import *
    class OrderBookTest(OrderBookHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, data = super(OrderBookTest,self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("OrderBookTest: error, msg: %s" % data)
                return RET_ERROR, data
            print("OrderBookTest ", data) # OrderBookTest 自己的处理逻辑
            return RET_OK, data
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = OrderBookTest()
    quote_ctx.set_handler(handler)  # 设置实时摆盘回调
    quote_ctx.subscribe(['US.AAPL'], [SubType.ORDER_BOOK])  # 订阅买卖摆盘类型，OpenD 开始持续收到服务器的推送
    time.sleep(15)  #  设置脚本接收 OpenD 的推送持续时间为15秒
    quote_ctx.close()  # 关闭当条连接，OpenD 会在1分钟后自动取消相应股票相应类型的订阅
    

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

*   **Output**

    OrderBookTest  {'code': 'US.AAPL', 'name': '苹果', 'svr_recv_time_bid': '2025-04-07 05:00:52.266', 'svr_recv_time_ask': '2025-04-07 05:00:53.973', 'Bid': [(180.2, 15, 3, {}), (180.19, 1, 1, {}), (180.18, 11, 2, {}), (180.14, 200, 1, {}), (180.13, 3, 2, {}), (180.1, 99, 3, {}), (180.05, 3, 1, {}), (180.03, 400, 1, {}), (180.02, 10, 1, {}), (180.01, 100, 1, {}), (180.0, 441, 24, {})], 'Ask': [(180.3, 100, 1, {}), (180.38, 4, 2, {}), (180.4, 100, 1, {}), (180.42, 200, 1, {}), (180.46, 29, 1, {}), (180.5, 1019, 2, {}), (180.6, 1000, 1, {}), (180.8, 2001, 3, {}), (180.84, 15, 2, {}), (181.0, 2036, 4, {}), (181.2, 2000, 2, {}), (181.3, 3, 1, {}), (181.4, 2021, 3, {}), (181.5, 59, 2, {}), (181.79, 9, 1, {}), (181.8, 20, 1, {}), (181.9, 94, 4, {}), (181.98, 20, 1, {}), (182.0, 150, 7, {})]}
    
    

1  
2  

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4908)
 **取消订阅**
--------------------------------------------------------------------------

`unsubscribe(code_list, subtype_list, unsubscribe_all=False)`

*   **介绍**
    
    取消订阅
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 取消订阅的股票代码列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 str |
    | subtype\_list | list | 需要订阅的数据类型列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878) |
    | unsubscribe\_all | bool | 取消所有订阅<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>为 True 时忽略其他参数 |
    

*   **Return**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | err\_message | NoneType | 当 ret == RET\_OK, 返回 None |
    | str | 当 ret != RET\_OK, 返回错误描述 |
    
*   **Example**
    

    from futu import *
    import time
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    print('current subscription status :', quote_ctx.query_subscription())  # 查询初始订阅状态
    ret_sub, err_message = quote_ctx.subscribe(['US.AAPL'], [SubType.QUOTE, SubType.TICKER], subscribe_push=False, session=Session.ALL)
    # 先订阅了AAPL全时段 QUOTE 和 TICKER 两个类型。订阅成功后 OpenD 将持续收到服务器的推送，False 代表暂时不需要推送给脚本
    if ret_sub == RET_OK:   # 订阅成功
        print('subscribe successfully！current subscription status :', quote_ctx.query_subscription())  # 订阅成功后查询订阅状态
        time.sleep(60)  # 订阅之后至少1分钟才能取消订阅
        ret_unsub, err_message_unsub = quote_ctx.unsubscribe(['US.AAPL'], [SubType.QUOTE])
        if ret_unsub == RET_OK:
            print('unsubscribe successfully！current subscription status:', quote_ctx.query_subscription())  # 取消订阅后查询订阅状态
        else:
            print('unsubscription failed！', err_message_unsub)
    else:
        print('subscription failed', err_message)
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

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

*   **Output**

    current subscription status : (0, {'total_used': 0, 'remain': 1000, 'own_used': 0, 'sub_list': {}})
    subscribe successfully！current subscription status : (0, {'total_used': 2, 'remain': 998, 'own_used': 2, 'sub_list': {'QUOTE': ['US.AAPL'], 'TICKER': ['US.AAPL']}})
    unsubscribe successfully！current subscription status: (0, {'total_used': 1, 'remain': 999, 'own_used': 1, 'sub_list': {'TICKER': ['US.AAPL']}})
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2489)
 **取消所有订阅**
----------------------------------------------------------------------------

`unsubscribe_all()`

*   **介绍**

取消所有订阅

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | err\_message | NoneType | 当 ret == RET\_OK, 返回 None |
    | str | 当 ret != RET\_OK, 返回错误描述 |
    
*   **Example**
    

    from futu import *
    import time
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    print('current subscription status :', quote_ctx.query_subscription())  # 查询初始订阅状态
    ret_sub, err_message = quote_ctx.subscribe(['US.AAPL'], [SubType.QUOTE, SubType.TICKER], subscribe_push=False, session=Session.None)
    # 先订阅了AAPL全时段 QUOTE 和 TICKER 两个类型。订阅成功后 OpenD 将持续收到服务器的推送，False 代表暂时不需要推送给脚本
    if ret_sub == RET_OK:  # 订阅成功
        print('subscribe successfully！current subscription status :', quote_ctx.query_subscription())  # 订阅成功后查询订阅状态
        time.sleep(60)  # 订阅之后至少1分钟才能取消订阅
        ret_unsub, err_message_unsub = quote_ctx.unsubscribe_all()  # 取消所有订阅
        if ret_unsub == RET_OK:
            print('unsubscribe all successfully！current subscription status:', quote_ctx.query_subscription())  # 取消订阅后查询订阅状态
        else:
            print('Failed to cancel all subscriptions！', err_message_unsub)
    else:
        print('subscription failed', err_message)
    quote_ctx.close()  # 结束后记得关闭当条连接，防止连接条数用尽
    

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

*   **Output**

    current subscription status : (0, {'total_used': 0, 'remain': 1000, 'own_used': 0, 'sub_list': {}})
    subscribe successfully！current subscription status : (0, {'total_used': 2, 'remain': 998, 'own_used': 2, 'sub_list': {'QUOTE': ['US.AAPL'], 'TICKER': ['US.AAPL']}})
    unsubscribe all successfully！current subscription status: (0, {'total_used': 0, 'remain': 1000, 'own_used': 0, 'sub_list': {}})
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#3789)
 Qot\_Sub.proto
--------------------------------------------------------------------------------

*   **介绍**
    
    订阅注册需要的实时信息，指定股票和订阅的数据类型即可。  
    香港市场（含正股、窝轮、牛熊、期权、期货）订阅，需要 LV1 及以上的权限，BMP 权限下不支持订阅。  
    美股市场（含正股、ETFs）夜盘行情订阅，需要 LV1 及以上的权限，BMP 权限下不支持订阅。
    
*   **参数**
    

    
    message C2S
    {
        repeated Qot_Common.Security securityList = 1; //股票数组
        repeated int32 subTypeList = 2; //Qot_Common.SubType，订阅数据类型数组
        required bool isSubOrUnSub = 3; //true 表示订阅，false 表示反订阅
        optional bool isRegOrUnRegPush = 4; //是否注册或反注册该连接上面行情的推送，该参数不指定不做注册反注册操作
        repeated int32 regPushRehabTypeList = 5; //Qot_Common.RehabType，复权类型，注册推送并且是 K 线类型才生效，其他订阅类型忽略该参数，注册 K 线推送时该参数不指定默认前复权
        optional bool isFirstPush = 6; //注册后如果本地已有数据是否首推一次已存在数据，该参数不指定则默认 true
        optional bool isUnsubAll = 7; //一键取消当前连接的所有订阅，当被设置为 True 时忽略其他参数。
        optional bool isSubOrderBookDetail = 8; //订阅摆盘可用，是否订阅摆盘明细，仅支持 SF 行情，该参数不指定则默认 false
        optional bool extendedTime = 9;  // 是否允许美股盘前盘后数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）
        optional int32 session = 10; // 是否允许美股盘前盘后以及夜盘数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）,该参数指定后忽略 extendedTime
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   订阅数据类型参见 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     

*   **返回**

    
    message S2C
    {
    }
    
    message Response
    {
        required int32 retType = 1 [default = -400]; //RetType,返回结果
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3001
    

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4159)
 Sub
---------------------------------------------------------------------

`uint Sub(QotSub.Request req);`  
`virtual void OnReply_Sub(FTAPI_Conn client, uint nSerialNo, QotSub.Response rsp);`

*   **介绍**

订阅注册需要的实时信息，指定股票和订阅的数据类型即可。 香港市场（含正股、窝轮、牛熊、期权、期货）订阅，需要 LV1 及以上的权限，BMP 权限下不支持订阅。  
美股市场（含正股、ETFs）夜盘行情订阅，需要 LV1 及以上的权限，BMP 权限下不支持订阅。

*   **参数**

    
    message C2S
    {
        repeated Qot_Common.Security securityList = 1; //股票数组
        repeated int32 subTypeList = 2; //Qot_Common.SubType，订阅数据类型数组
        required bool isSubOrUnSub = 3; //true 表示订阅，false 表示反订阅
        optional bool isRegOrUnRegPush = 4; //是否注册或反注册该连接上面行情的推送，该参数不指定不做注册反注册操作
        repeated int32 regPushRehabTypeList = 5; //Qot_Common.RehabType，复权类型，注册推送并且是 K 线类型才生效，其他订阅类型忽略该参数，注册 K 线推送时该参数不指定默认前复权
        optional bool isFirstPush = 6; //注册后如果本地已有数据是否首推一次已存在数据，该参数不指定则默认 true
        optional bool isUnsubAll = 7; //一键取消当前连接的所有订阅，当被设置为 True 时忽略其他参数。
        optional bool isSubOrderBookDetail = 8; //订阅摆盘可用，是否订阅摆盘明细，仅支持 SF 行情，该参数不指定则默认 false
        optional bool extendedTime = 9;  // 是否允许美股盘前盘后数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   订阅数据类型参见 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     

*   **返回**

    
    message S2C
    {
    }
    
    message Response
    {
        required int32 retType = 1 [default = -400]; //RetType,返回结果
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : FTSPI_Qot, FTSPI_Conn {
        FTAPI_Qot qot = new FTAPI_Qot();
    
        public Program() {
            qot.SetClientInfo("csharp", 1);  //设置客户端信息
            qot.SetConnCallback(this);  //设置连接回调
            qot.SetQotCallback(this);   //设置交易回调
        }
    
        public void Start() {
            qot.InitConnect("127.0.0.1", (ushort)11111, false);
        }
    
        
        public void OnInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Qot onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
            if (errCode != 0)
                return;
    
            QotCommon.Security sec = QotCommon.Security.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetCode("00700")
                    .Build();
            QotSub.C2S c2s = QotSub.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                    .AddSubTypeList((int)QotCommon.SubType.SubType_Basic)
                    .SetIsSubOrUnSub(true)
                    .Build();
            QotSub.Request req = QotSub.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.Sub(req);
            Console.Write("Send QotSub: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_Sub(FTAPI_Conn client, uint nSerialNo, QotSub.Response rsp) {
            Console.Write("Reply: QotSub: {0}  {1}\n", nSerialNo, rsp.ToString());
        }
    
        public static void Main(String[] args) {
            FTAPI.Init();
            Program qot = new Program();
            qot.Start();
    
    
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

    Qot onInitConnect: ret=0 desc= connID=6819171983431767818
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    

1  
2  
3  
4  
5  

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4159-2)
 sub
-----------------------------------------------------------------------

`int sub(QotSub.Request req);`  
`void onReply_Sub(FTAPI_Conn client, int nSerialNo, QotSub.Response rsp);`

*   **介绍**

订阅注册需要的实时信息，指定股票和订阅的数据类型即可。 香港市场（含正股、窝轮、牛熊、期权、期货）订阅，需要 LV1 及以上的权限，BMP 权限下不支持订阅。  
美股市场（含正股、ETFs）夜盘行情订阅，需要 LV1 及以上的权限，BMP 权限下不支持订阅。

*   **参数**

    
    message C2S
    {
        repeated Qot_Common.Security securityList = 1; //股票数组
        repeated int32 subTypeList = 2; //Qot_Common.SubType，订阅数据类型数组
        required bool isSubOrUnSub = 3; //true 表示订阅，false 表示反订阅
        optional bool isRegOrUnRegPush = 4; //是否注册或反注册该连接上面行情的推送，该参数不指定不做注册反注册操作
        repeated int32 regPushRehabTypeList = 5; //Qot_Common.RehabType，复权类型，注册推送并且是 K 线类型才生效，其他订阅类型忽略该参数，注册 K 线推送时该参数不指定默认前复权
        optional bool isFirstPush = 6; //注册后如果本地已有数据是否首推一次已存在数据，该参数不指定则默认 true
        optional bool isUnsubAll = 7; //一键取消当前连接的所有订阅，当被设置为 True 时忽略其他参数。
        optional bool isSubOrderBookDetail = 8; //订阅摆盘可用，是否订阅摆盘明细，仅支持 SF 行情，该参数不指定则默认 false
        optional bool extendedTime = 9;  // 是否允许美股盘前盘后数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）
        optional int32 session = 10; // 是否允许美股盘前盘后以及夜盘数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）,该参数指定后忽略 extendedTime
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   订阅数据类型参见 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     

*   **返回**

    
    message S2C
    {
    }
    
    message Response
    {
        required int32 retType = 1 [default = -400]; //RetType,返回结果
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class QotDemo implements FTSPI_Qot, FTSPI_Conn {
        FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();
    
        public QotDemo() {
            qot.setClientInfo("javaclient", 1);  //设置客户端信息
            qot.setConnSpi(this);  //设置连接回调
            qot.setQotSpi(this);   //设置交易回调
        }
    
        public void start() {
            qot.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Qot onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
            if (errCode != 0)
                return;
    
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .setCode("00700")
                    .build();
            QotSub.C2S c2s = QotSub.C2S.newBuilder()
                    .addSecurityList(sec)
                    .addSubTypeList(QotCommon.SubType.SubType_Basic_VALUE)
                    .setIsSubOrUnSub(true)
                    .build();
            QotSub.Request req = QotSub.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.sub(req);
            System.out.printf("Send QotSub: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_Sub(FTAPI_Conn client, int nSerialNo, QotSub.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotSub failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotSub: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public static void main(String[] args) {
            FTAPI.init();
            QotDemo qot = new QotDemo();
            qot.start();
    
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

*   **Output**

    Send QotSub: 2
    Receive QotSub: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0
    }
    

1  
2  
3  
4  
5  
6  

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4159-3)
 Sub
-----------------------------------------------------------------------

`Futu::u32_t Sub(const Qot_Sub::Request &stReq);`  
`virtual void OnReply_Sub(Futu::u32_t nSerialNo, const Qot_Sub::Response &stRsp) = 0;`

*   **介绍**
    
    订阅注册需要的实时信息，指定股票和订阅的数据类型即可。  
    香港市场（含正股、窝轮、牛熊、期权、期货）订阅，需要 LV1 及以上的权限，BMP 权限下不支持订阅。  
    美股市场（含正股、ETFs）夜盘行情订阅，需要 LV1 及以上的权限，BMP 权限下不支持订阅。
    
*   **参数**
    

    
    message C2S
    {
        repeated Qot_Common.Security securityList = 1; //股票数组
        repeated int32 subTypeList = 2; //Qot_Common.SubType，订阅数据类型数组
        required bool isSubOrUnSub = 3; //true 表示订阅，false 表示反订阅
        optional bool isRegOrUnRegPush = 4; //是否注册或反注册该连接上面行情的推送，该参数不指定不做注册反注册操作
        repeated int32 regPushRehabTypeList = 5; //Qot_Common.RehabType，复权类型，注册推送并且是 K 线类型才生效，其他订阅类型忽略该参数，注册 K 线推送时该参数不指定默认前复权
        optional bool isFirstPush = 6; //注册后如果本地已有数据是否首推一次已存在数据，该参数不指定则默认 true
        optional bool isUnsubAll = 7; //一键取消当前连接的所有订阅，当被设置为 True 时忽略其他参数。
        optional bool isSubOrderBookDetail = 8; //订阅摆盘可用，是否订阅摆盘明细，仅支持 SF 行情，该参数不指定则默认 false
        optional bool extendedTime = 9;  // 是否允许美股盘前盘后数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）
        optional int32 session = 10; // 是否允许美股盘前盘后以及夜盘数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）,该参数指定后忽略 extendedTime
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   订阅数据类型参见 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     

*   **返回**

    
    message S2C
    {
    }
    
    message Response
    {
        required int32 retType = 1 [default = -400]; //RetType,返回结果
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    class Program : public FTSPI_Qot, public FTSPI_Trd, public FTSPI_Conn
    {
    public:
    
    	Program() {
    		m_pQotApi = FTAPI::CreateQotApi();
    		m_pQotApi->RegisterQotSpi(this);
    		m_pQotApi->RegisterConnSpi(this);
    	}
    
    	~Program() {
    		if (m_pQotApi != nullptr)
    		{
    			m_pQotApi->UnregisterQotSpi();
    			m_pQotApi->UnregisterConnSpi();
    			FTAPI::ReleaseQotApi(m_pQotApi);
    			m_pQotApi = nullptr;
    		}
    	}
    
    	void Start() {
    		m_pQotApi->InitConnect("127.0.0.1", 11111, false);
    	}
    
    
    	virtual void OnInitConnect(FTAPI_Conn* pConn, Futu::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		// 这个接口要先订阅
    		Qot_Sub::Request req;
    		Qot_Sub::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->add_subtypelist(Qot_Common::SubType::SubType_Basic);
    		c2s->set_isregorunregpush(true);
    		c2s->set_issuborunsub(true);
    
            m_SubSerialNo = m_pQotApi->Sub(req);
            cout << "Request Sub SerialNo: " << m_SubSerialNo << endl;
    	}
    
    	virtual void OnReply_Sub(Futu::u32_t nSerialNo, const Qot_Sub::Response &stRsp)
    	{
            if(nSerialNo == m_SubSerialNo)
            {
                cout << "OnReply_Sub SerialNo: " << nSerialNo << endl;
                if(stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "Sub Failed" << endl;
                    return;
                }        
                
                // 组包
                Qot_GetBasicQot::Request req;
                Qot_GetBasicQot::C2S *c2s = req.mutable_c2s();
                auto secList = c2s->mutable_securitylist();
                Qot_Common::Security *sec = secList->Add();
                sec->set_code("00700");
                sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
                m_GetBasicQotSerialNo = m_pQotApi->GetBasicQot(req);
                cout << "Request GetBasicQot SerialNo: " << m_GetBasicQotSerialNo << endl;
            }
    	}
    
    	virtual void OnReply_GetBasicQot(Futu::u32_t nSerialNo, const Qot_GetBasicQot::Response &stRsp){
            if(nSerialNo == m_GetBasicQotSerialNo)
            {
                cout << "OnReply_GetBasicQot SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_SubSerialNo;
        Futu::u32_t m_GetBasicQotSerialNo;
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
79  
80  
81  
82  
83  
84  
85  
86  
87  
88  
89  
90  
91  
92  
93  
94  
95  
96  
97  
98  
99  
100  

*   **Output**

    connect
    Request Sub SerialNo: 3
    OnReply_Sub SerialNo: 3
    Request GetBasicQot SerialNo: 4
    OnReply_GetBasicQot SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "basicQotList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "00700"\
        },\
        "isSuspended": false,\
        "listTime": "2004-06-16",\
        "priceSpread": 0.2,\
        "updateTime": "2021-09-14 16:08:30",\
        "highPrice": 482.8,\
        "openPrice": 476.4,\
        "lowPrice": 468.8,\
        "curPrice": 472.8,\
        "lastClosePrice": 478,\
        "volume": "19905281",\
        "turnover": 9452753751,\
        "turnoverRate": 0.207,\
        "amplitude": 2.929,\
        "darkStatus": 0,\
        "listTimestamp": 1087315200,\
        "updateTimestamp": 1631606910,\
        "secStatus": 1\
       }\
      ]
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

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4159-4)
 Sub
-----------------------------------------------------------------------

`Sub(req);`

*   **介绍**
    
    订阅注册需要的实时信息，指定股票和订阅的数据类型即可。  
    香港市场（含正股窝轮牛熊期权期货）订阅，需要 LV1 及以上的权限，BMP 权限下不支持订阅。  
    美股市场（含正股、ETFs）夜盘行情订阅，需要 LV1 及以上的权限，BMP 权限下不支持订阅。
    
*   **参数**
    

    
    message C2S
    {
        repeated Qot_Common.Security securityList = 1; //股票数组
        repeated int32 subTypeList = 2; //Qot_Common.SubType，订阅数据类型数组
        required bool isSubOrUnSub = 3; //true 表示订阅，false 表示反订阅
        optional bool isRegOrUnRegPush = 4; //是否注册或反注册该连接上面行情的推送，该参数不指定不做注册反注册操作
        repeated int32 regPushRehabTypeList = 5; //Qot_Common.RehabType，复权类型，注册推送并且是 K 线类型才生效，其他订阅类型忽略该参数，注册 K 线推送时该参数不指定默认前复权
        optional bool isFirstPush = 6; //注册后如果本地已有数据是否首推一次已存在数据，该参数不指定则默认 true
        optional bool isUnsubAll = 7; //一键取消当前连接的所有订阅，当被设置为 True 时忽略其他参数。
        optional bool isSubOrderBookDetail = 8; //订阅摆盘可用，是否订阅摆盘明细，仅支持 SF 行情，该参数不指定则默认 false
        optional bool extendedTime = 9;  // 是否允许美股盘前盘后数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）
        optional int32 session = 10; // 是否允许美股盘前盘后以及夜盘数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）,该参数指定后忽略 extendedTime
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   订阅数据类型参见 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     

*   **返回**

    
    message S2C
    {
    }
    
    message Response
    {
        required int32 retType = 1 [default = -400]; //RetType,返回结果
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    
    function Sub(){
        const { RetType } = Common
        const { SubType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'XXXXX'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                const req = {
                    c2s: {
                    securityList: [\
                        {\
                            market: QotMarket.QotMarket_HK_Security,\
                            code: "00700",\
                        },\
                    ],
                    subTypeList: [ SubType.SubType_OrderBook ], // 订阅买卖摆盘类型
                    isSubOrUnSub: true, // 订阅 true, 反订阅 false
                    isRegOrUnRegPush: true, // 注册推送 true, 反注册推送 false
                    },
                }; // 订阅参数
    
                websocket.Sub(req) //# 订阅, OpenD 开始持续收到服务器的推送
                .then((res) => { 
                    let { errCode, retMsg, retType } = res
                    console.log("subscribe: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                })
                .catch((error) => {
                    if ("retMsg" in error) {
                        console.log("error:", error.retMsg);
                    }
                });
            } else {
                console.log("error", msg);
            }
        };
    
        websocket.onPush = (cmd, res)=>{
            if(ftCmdID.QotUpdateOrderBook.cmd == cmd){
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    console.log("OrderBookTest", s2c);
                } else {
                    console.log("OrderBookTest: error")
                }
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
    
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 5000); // 接收 OpenD 的推送持续时间为5秒,5秒后断开
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

*   **Output**

    subscribe: errCode 0, retMsg , retType 0
    OrderBookTest {"security":{"market":1,"code":"00700"},"orderBookAskList":[{"price":492.6,"volume":"32300","orederCount":55},{"price":492.8,"volume":"14100","orederCount":16},{"price":493,"volume":"25200","orederCount":30},{"price":493.2,"volume":"12300","orederCount":11},{"price":493.4,"volume":"9000","orederCount":7},{"price":493.6,"volume":"13500","orederCount":10},{"price":493.8,"volume":"9800","orederCount":12},{"price":494,"volume":"10500","orederCount":18},{"price":494.2,"volume":"6100","orederCount":6},{"price":494.4,"volume":"11400","orederCount":5}],"orderBookBidList":[{"price":492.4,"volume":"30600","orederCount":59},{"price":492.2,"volume":"29800","orederCount":49},{"price":492,"volume":"97900","orederCount":120},{"price":491.8,"volume":"24700","orederCount":55},{"price":491.6,"volume":"35300","orederCount":50},{"price":491.4,"volume":"11200","orederCount":18},{"price":491.2,"volume":"29300","orederCount":39},{"price":491,"volume":"77900","orederCount":229},{"price":490.8,"volume":"14100","orederCount":39},{"price":490.6,"volume":"22800","orederCount":34}]}
    OrderBookTest { ... }
    ...
    ...
    stop
    

1  
2  
3  
4  
5  
6  

接口限制

*   支持多种实时数据类型的订阅，参见 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878)
     ，每支股票订阅一个类型占用一个额度。
*   订阅额度规则请参见 [订阅额度 & 历史 K 线额度](https://openapi.futunn.com/futu-api-doc/intro/authority.html#1314)
    。
*   至少订阅一分钟才可以反订阅。
*   由于港股 SF 行情摆盘数据量较大，为保证 SF 行情的速度和 OpenD 的处理性能，目前 SF 权限用户仅限同时订阅 50 只证券类产品（含 hkex 的正股、窝轮、牛熊）的摆盘，剩余订阅额度仍可用于订阅其他类型，如：逐笔，买卖经纪等。
*   港股期权期货在 LV1 权限下，不支持订阅逐笔类型。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2263-2)
 **订阅**
--------------------------------------------------------------------------

`subscribe(code_list, subtype_list, is_first_push=True, subscribe_push=True, is_detailed_orderbook=False, extended_time=False, session=Session.NONE)`

*   **介绍**
    
    订阅注册需要的实时信息，指定股票和订阅的数据类型即可。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 需要订阅的股票代码列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 str |
    | subtype\_list | list | 需要订阅的数据类型列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878) |
    | is\_first\_push | bool | 订阅成功之后是否立即推送一次缓存数据<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   True：推送缓存  <br>    当脚本和 OpenD 之间出现断线重连，重新订阅时若设置为 True，会再次推送断线前的最后一条数据<br>*   False：不推送缓存。等待服务器的最新推送 |
    | subscribe\_push | bool | 订阅后是否推送<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>订阅后，OpenD 提供了[两种取数据的方式](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)<br>，如果您仅使用 **获取实时数据** 的方式，选择 False 可以节省一部分性能消耗<br><br>*   True：推送。如果使用 **实时数据回调** 的方式，则必须设置为 True<br>*   False：不推送。如果**仅**使用 **获取实时数据** 的方式，则建议设置为 False |
    | is\_detailed\_orderbook | bool | 是否订阅详细的摆盘订单明细<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   仅用于港股 SF 行情权限下订阅港股 ORDER\_BOOK 类型<br>*   美股美期 LV2 权限下不提供详细摆盘订单明细 |
    | extended\_time | bool | 是否允许美股盘前盘后数据<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅用于订阅美股实时 K 线、实时分时、实时逐笔 |
    | session | [Session](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9152) | 美股订阅时段<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   仅用于订阅美股实时 K 线、实时分时、实时逐笔<br>*   订阅美股行情不支持入参OVERNIGHT<br>*   最低OpenD版本：9.2.4207 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | err\_message | NoneType | 当 ret == RET\_OK 时，返回 None |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
*   **Example**
    

    import time
    from moomoo import *
    class OrderBookTest(OrderBookHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, data = super(OrderBookTest,self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("OrderBookTest: error, msg: %s" % data)
                return RET_ERROR, data
            print("OrderBookTest ", data) # OrderBookTest 自己的处理逻辑
            return RET_OK, data
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = OrderBookTest()
    quote_ctx.set_handler(handler)  # 设置实时摆盘回调
    quote_ctx.subscribe(['US.AAPL'], [SubType.ORDER_BOOK])  # 订阅买卖摆盘类型，OpenD 开始持续收到服务器的推送
    time.sleep(15)  #  设置脚本接收 OpenD 的推送持续时间为15秒
    quote_ctx.close()  # 关闭当条连接，OpenD 会在1分钟后自动取消相应股票相应类型的订阅
    

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

*   **Output**

    OrderBookTest  {'code': 'US.AAPL', 'name': '苹果', 'svr_recv_time_bid': '2025-04-07 05:00:52.266', 'svr_recv_time_ask': '2025-04-07 05:00:53.973', 'Bid': [(180.2, 15, 3, {}), (180.19, 1, 1, {}), (180.18, 11, 2, {}), (180.14, 200, 1, {}), (180.13, 3, 2, {}), (180.1, 99, 3, {}), (180.05, 3, 1, {}), (180.03, 400, 1, {}), (180.02, 10, 1, {}), (180.01, 100, 1, {}), (180.0, 441, 24, {})], 'Ask': [(180.3, 100, 1, {}), (180.38, 4, 2, {}), (180.4, 100, 1, {}), (180.42, 200, 1, {}), (180.46, 29, 1, {}), (180.5, 1019, 2, {}), (180.6, 1000, 1, {}), (180.8, 2001, 3, {}), (180.84, 15, 2, {}), (181.0, 2036, 4, {}), (181.2, 2000, 2, {}), (181.3, 3, 1, {}), (181.4, 2021, 3, {}), (181.5, 59, 2, {}), (181.79, 9, 1, {}), (181.8, 20, 1, {}), (181.9, 94, 4, {}), (181.98, 20, 1, {}), (182.0, 150, 7, {})]}
    

1  

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4908-2)
 **取消订阅**
----------------------------------------------------------------------------

`unsubscribe(code_list, subtype_list, unsubscribe_all=False)`

*   **介绍**
    
    取消订阅
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 取消订阅的股票代码列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 str |
    | subtype\_list | list | 需要订阅的数据类型列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878) |
    | unsubscribe\_all | bool | 取消所有订阅<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>为 True 时忽略其他参数 |
    

*   **Return**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | err\_message | NoneType | 当 ret == RET\_OK, 返回 None |
    | str | 当 ret != RET\_OK, 返回错误描述 |
    
*   **Example**
    

    from moomoo import *
    import time
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    print('current subscription status :', quote_ctx.query_subscription())  # 查询初始订阅状态
    ret_sub, err_message = quote_ctx.subscribe(['US.AAPL'], [SubType.QUOTE, SubType.TICKER], subscribe_push=False, session=Session.None)
    # 先订阅了AAPL全时段 QUOTE 和 TICKER 两个类型。订阅成功后 OpenD 将持续收到服务器的推送，False 代表暂时不需要推送给脚本
    if ret_sub == RET_OK:   # 订阅成功
        print('subscribe successfully！current subscription status :', quote_ctx.query_subscription())  # 订阅成功后查询订阅状态
        time.sleep(60)  # 订阅之后至少1分钟才能取消订阅
        ret_unsub, err_message_unsub = quote_ctx.unsubscribe(['US.AAPL'], [SubType.QUOTE])
        if ret_unsub == RET_OK:
            print('unsubscribe successfully！current subscription status:', quote_ctx.query_subscription())  # 取消订阅后查询订阅状态
        else:
            print('unsubscription failed！', err_message_unsub)
    else:
        print('subscription failed', err_message)
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

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

*   **Output**

    current subscription status : (0, {'total_used': 0, 'remain': 1000, 'own_used': 0, 'sub_list': {}})
    subscribe successfully！current subscription status : (0, {'total_used': 2, 'remain': 998, 'own_used': 2, 'sub_list': {'QUOTE': ['US.AAPL'], 'TICKER': ['US.AAPL']}})
    unsubscribe successfully！current subscription status: (0, {'total_used': 1, 'remain': 999, 'own_used': 1, 'sub_list': {'TICKER': ['US.AAPL']}})
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2489-2)
 **取消所有订阅**
------------------------------------------------------------------------------

`unsubscribe_all()`

*   **介绍**

取消所有订阅

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | err\_message | NoneType | 当 ret == RET\_OK, 返回 None |
    | str | 当 ret != RET\_OK, 返回错误描述 |
    
*   **Example**
    

    from moomoo import *
    import time
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    print('current subscription status :', quote_ctx.query_subscription())  # 查询初始订阅状态
    ret_sub, err_message = quote_ctx.subscribe(['US.AAPL'], [SubType.QUOTE, SubType.TICKER], subscribe_push=False, session=Session.None)
    # 先订阅了AAPL全时段 QUOTE 和 TICKER 两个类型。订阅成功后 OpenD 将持续收到服务器的推送，False 代表暂时不需要推送给脚本
    if ret_sub == RET_OK:  # 订阅成功
        print('subscribe successfully！current subscription status :', quote_ctx.query_subscription())  # 订阅成功后查询订阅状态
        time.sleep(60)  # 订阅之后至少1分钟才能取消订阅
        ret_unsub, err_message_unsub = quote_ctx.unsubscribe_all()  # 取消所有订阅
        if ret_unsub == RET_OK:
            print('unsubscribe all successfully！current subscription status:', quote_ctx.query_subscription())  # 取消订阅后查询订阅状态
        else:
            print('Failed to cancel all subscriptions！', err_message_unsub)
    else:
        print('subscription failed', err_message)
    quote_ctx.close()  # 结束后记得关闭当条连接，防止连接条数用尽
    

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

*   **Output**

    current subscription status : (0, {'total_used': 0, 'remain': 1000, 'own_used': 0, 'sub_list': {}})
    subscribe successfully！current subscription status : (0, {'total_used': 2, 'remain': 998, 'own_used': 2, 'sub_list': {'QUOTE': ['US.AAPL'], 'TICKER': ['US.AAPL']}})
    unsubscribe all successfully！current subscription status: (0, {'total_used': 0, 'remain': 1000, 'own_used': 0, 'sub_list': {}})
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#3789-2)
 Qot\_Sub.proto
----------------------------------------------------------------------------------

*   **介绍**
    
    订阅注册需要的实时信息，指定股票和订阅的数据类型即可。
    
*   **参数**
    

    
    message C2S
    {
        repeated Qot_Common.Security securityList = 1; //股票数组
        repeated int32 subTypeList = 2; //Qot_Common.SubType，订阅数据类型数组
        required bool isSubOrUnSub = 3; //true 表示订阅，false 表示反订阅
        optional bool isRegOrUnRegPush = 4; //是否注册或反注册该连接上面行情的推送，该参数不指定不做注册反注册操作
        repeated int32 regPushRehabTypeList = 5; //Qot_Common.RehabType，复权类型，注册推送并且是 K 线类型才生效，其他订阅类型忽略该参数，注册 K 线推送时该参数不指定默认前复权
        optional bool isFirstPush = 6; //注册后如果本地已有数据是否首推一次已存在数据，该参数不指定则默认 true
        optional bool isUnsubAll = 7; //一键取消当前连接的所有订阅，当被设置为 True 时忽略其他参数。
        optional bool isSubOrderBookDetail = 8; //订阅摆盘可用，是否订阅摆盘明细，仅支持 SF 行情，该参数不指定则默认 false
        optional bool extendedTime = 9;  // 是否允许美股盘前盘后数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）
        optional int32 session = 10; // 是否允许美股盘前盘后以及夜盘数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）,该参数指定后忽略 extendedTime
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   订阅数据类型参见 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     

*   **返回**

    
    message S2C
    {
    }
    
    message Response
    {
        required int32 retType = 1 [default = -400]; //RetType,返回结果
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3001
    

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4159-5)
 Sub
-----------------------------------------------------------------------

`uint Sub(QotSub.Request req);`  
`virtual void OnReply_Sub(MMAPI_Conn client, uint nSerialNo, QotSub.Response rsp);`

*   **介绍**

订阅注册需要的实时信息，指定股票和订阅的数据类型即可。

*   **参数**

    
    message C2S
    {
        repeated Qot_Common.Security securityList = 1; //股票数组
        repeated int32 subTypeList = 2; //Qot_Common.SubType，订阅数据类型数组
        required bool isSubOrUnSub = 3; //true 表示订阅，false 表示反订阅
        optional bool isRegOrUnRegPush = 4; //是否注册或反注册该连接上面行情的推送，该参数不指定不做注册反注册操作
        repeated int32 regPushRehabTypeList = 5; //Qot_Common.RehabType，复权类型，注册推送并且是 K 线类型才生效，其他订阅类型忽略该参数，注册 K 线推送时该参数不指定默认前复权
        optional bool isFirstPush = 6; //注册后如果本地已有数据是否首推一次已存在数据，该参数不指定则默认 true
        optional bool isUnsubAll = 7; //一键取消当前连接的所有订阅，当被设置为 True 时忽略其他参数。
        optional bool isSubOrderBookDetail = 8; //订阅摆盘可用，是否订阅摆盘明细，仅支持 SF 行情，该参数不指定则默认 false
        optional bool extendedTime = 9;  // 是否允许美股盘前盘后数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）
        optional int32 session = 10; // 是否允许美股盘前盘后以及夜盘数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）,该参数指定后忽略 extendedTime
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   订阅数据类型参见 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     

*   **返回**

    
    message S2C
    {
    }
    
    message Response
    {
        required int32 retType = 1 [default = -400]; //RetType,返回结果
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : MMSPI_Qot, MMSPI_Conn {
        MMAPI_Qot qot = new MMAPI_Qot();
    
        public Program() {
            qot.SetClientInfo("csharp", 1);  //设置客户端信息
            qot.SetConnCallback(this);  //设置连接回调
            qot.SetQotCallback(this);   //设置交易回调
        }
    
        public void Start() {
            qot.InitConnect("127.0.0.1", (ushort)11111, false);
        }
    
        
        public void OnInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Qot onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
            if (errCode != 0)
                return;
    
            QotCommon.Security sec = QotCommon.Security.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetCode("00700")
                    .Build();
            QotSub.C2S c2s = QotSub.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                    .AddSubTypeList((int)QotCommon.SubType.SubType_Basic)
                    .SetIsSubOrUnSub(true)
                    .Build();
            QotSub.Request req = QotSub.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.Sub(req);
            Console.Write("Send QotSub: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_Sub(MMAPI_Conn client, uint nSerialNo, QotSub.Response rsp) {
            Console.Write("Reply: QotSub: {0}  {1}\n", nSerialNo, rsp.ToString());
        }
    
        public static void Main(String[] args) {
            MMAPI.Init();
            Program qot = new Program();
            qot.Start();
    
    
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

    Qot onInitConnect: ret=0 desc= connID=6819171983431767818
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    

1  
2  
3  
4  
5  

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4159-6)
 sub
-----------------------------------------------------------------------

`int sub(QotSub.Request req);`  
`void onReply_Sub(MMAPI_Conn client, int nSerialNo, QotSub.Response rsp);`

*   **介绍**

订阅注册需要的实时信息，指定股票和订阅的数据类型即可。

*   **参数**

    
    message C2S
    {
        repeated Qot_Common.Security securityList = 1; //股票数组
        repeated int32 subTypeList = 2; //Qot_Common.SubType，订阅数据类型数组
        required bool isSubOrUnSub = 3; //true 表示订阅，false 表示反订阅
        optional bool isRegOrUnRegPush = 4; //是否注册或反注册该连接上面行情的推送，该参数不指定不做注册反注册操作
        repeated int32 regPushRehabTypeList = 5; //Qot_Common.RehabType，复权类型，注册推送并且是 K 线类型才生效，其他订阅类型忽略该参数，注册 K 线推送时该参数不指定默认前复权
        optional bool isFirstPush = 6; //注册后如果本地已有数据是否首推一次已存在数据，该参数不指定则默认 true
        optional bool isUnsubAll = 7; //一键取消当前连接的所有订阅，当被设置为 True 时忽略其他参数。
        optional bool isSubOrderBookDetail = 8; //订阅摆盘可用，是否订阅摆盘明细，仅支持 SF 行情，该参数不指定则默认 false
        optional bool extendedTime = 9;  // 是否允许美股盘前盘后数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）
        optional int32 session = 10; // 是否允许美股盘前盘后以及夜盘数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）,该参数指定后忽略 extendedTime
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   订阅数据类型参见 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     

*   **返回**

    
    message S2C
    {
    }
    
    message Response
    {
        required int32 retType = 1 [default = -400]; //RetType,返回结果
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class QotDemo implements MMSPI_Qot, MMSPI_Conn {
        MMAPI_Conn_Qot qot = new MMAPI_Conn_Qot();
    
        public QotDemo() {
            qot.setClientInfo("javaclient", 1);  //设置客户端信息
            qot.setConnSpi(this);  //设置连接回调
            qot.setQotSpi(this);   //设置交易回调
        }
    
        public void start() {
            qot.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Qot onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
            if (errCode != 0)
                return;
    
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .setCode("00700")
                    .build();
            QotSub.C2S c2s = QotSub.C2S.newBuilder()
                    .addSecurityList(sec)
                    .addSubTypeList(QotCommon.SubType.SubType_Basic_VALUE)
                    .setIsSubOrUnSub(true)
                    .build();
            QotSub.Request req = QotSub.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.sub(req);
            System.out.printf("Send QotSub: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_Sub(MMAPI_Conn client, int nSerialNo, QotSub.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotSub failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotSub: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public static void main(String[] args) {
            MMAPI.init();
            QotDemo qot = new QotDemo();
            qot.start();
    
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

*   **Output**

    Send QotSub: 2
    Receive QotSub: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0
    }
    

1  
2  
3  
4  
5  
6  

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4159-7)
 Sub
-----------------------------------------------------------------------

`moomoo::u32_t Sub(const Qot_Sub::Request &stReq);`  
`virtual void OnReply_Sub(moomoo::u32_t nSerialNo, const Qot_Sub::Response &stRsp) = 0;`

*   **介绍**
    
    订阅注册需要的实时信息，指定股票和订阅的数据类型即可。
    
*   **参数**
    

    
    message C2S
    {
        repeated Qot_Common.Security securityList = 1; //股票数组
        repeated int32 subTypeList = 2; //Qot_Common.SubType，订阅数据类型数组
        required bool isSubOrUnSub = 3; //true 表示订阅，false 表示反订阅
        optional bool isRegOrUnRegPush = 4; //是否注册或反注册该连接上面行情的推送，该参数不指定不做注册反注册操作
        repeated int32 regPushRehabTypeList = 5; //Qot_Common.RehabType，复权类型，注册推送并且是 K 线类型才生效，其他订阅类型忽略该参数，注册 K 线推送时该参数不指定默认前复权
        optional bool isFirstPush = 6; //注册后如果本地已有数据是否首推一次已存在数据，该参数不指定则默认 true
        optional bool isUnsubAll = 7; //一键取消当前连接的所有订阅，当被设置为 True 时忽略其他参数。
        optional bool isSubOrderBookDetail = 8; //订阅摆盘可用，是否订阅摆盘明细，仅支持 SF 行情，该参数不指定则默认 false
        optional bool extendedTime = 9;  // 是否允许美股盘前盘后数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）
        optional int32 session = 10; // 是否允许美股盘前盘后以及夜盘数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）,该参数指定后忽略 extendedTime
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   订阅数据类型参见 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     

*   **返回**

    
    message S2C
    {
    }
    
    message Response
    {
        required int32 retType = 1 [default = -400]; //RetType,返回结果
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    class Program : public MMSPI_Qot, public MMSPI_Trd, public MMSPI_Conn
    {
    public:
    
    	Program() {
    		m_pQotApi = MMAPI::CreateQotApi();
    		m_pQotApi->RegisterQotSpi(this);
    		m_pQotApi->RegisterConnSpi(this);
    	}
    
    	~Program() {
    		if (m_pQotApi != nullptr)
    		{
    			m_pQotApi->UnregisterQotSpi();
    			m_pQotApi->UnregisterConnSpi();
    			MMAPI::ReleaseQotApi(m_pQotApi);
    			m_pQotApi = nullptr;
    		}
    	}
    
    	void Start() {
    		m_pQotApi->InitConnect("127.0.0.1", 11111, false);
    	}
    
    
    	virtual void OnInitConnect(MMAPI_Conn* pConn, moomoo::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		// 这个接口要先订阅
    		Qot_Sub::Request req;
    		Qot_Sub::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->add_subtypelist(Qot_Common::SubType::SubType_Basic);
    		c2s->set_isregorunregpush(true);
    		c2s->set_issuborunsub(true);
    
            m_SubSerialNo = m_pQotApi->Sub(req);
            cout << "Request Sub SerialNo: " << m_SubSerialNo << endl;
    	}
    
    	virtual void OnReply_Sub(moomoo::u32_t nSerialNo, const Qot_Sub::Response &stRsp)
    	{
            if(nSerialNo == m_SubSerialNo)
            {
                cout << "OnReply_Sub SerialNo: " << nSerialNo << endl;
                if(stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "Sub Failed" << endl;
                    return;
                }        
                
                // 组包
                Qot_GetBasicQot::Request req;
                Qot_GetBasicQot::C2S *c2s = req.mutable_c2s();
                auto secList = c2s->mutable_securitylist();
                Qot_Common::Security *sec = secList->Add();
                sec->set_code("00700");
                sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
                m_GetBasicQotSerialNo = m_pQotApi->GetBasicQot(req);
                cout << "Request GetBasicQot SerialNo: " << m_GetBasicQotSerialNo << endl;
            }
    	}
    
    	virtual void OnReply_GetBasicQot(moomoo::u32_t nSerialNo, const Qot_GetBasicQot::Response &stRsp){
            if(nSerialNo == m_GetBasicQotSerialNo)
            {
                cout << "OnReply_GetBasicQot SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_SubSerialNo;
        moomoo::u32_t m_GetBasicQotSerialNo;
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
79  
80  
81  
82  
83  
84  
85  
86  
87  
88  
89  
90  
91  
92  
93  
94  
95  
96  
97  
98  
99  
100  

*   **Output**

    connect
    Request Sub SerialNo: 3
    OnReply_Sub SerialNo: 3
    Request GetBasicQot SerialNo: 4
    OnReply_GetBasicQot SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "basicQotList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "00700"\
        },\
        "isSuspended": false,\
        "listTime": "2004-06-16",\
        "priceSpread": 0.2,\
        "updateTime": "2021-09-14 16:08:30",\
        "highPrice": 482.8,\
        "openPrice": 476.4,\
        "lowPrice": 468.8,\
        "curPrice": 472.8,\
        "lastClosePrice": 478,\
        "volume": "19905281",\
        "turnover": 9452753751,\
        "turnoverRate": 0.207,\
        "amplitude": 2.929,\
        "darkStatus": 0,\
        "listTimestamp": 1087315200,\
        "updateTimestamp": 1631606910,\
        "secStatus": 1\
       }\
      ]
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

[#](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4159-8)
 Sub
-----------------------------------------------------------------------

`Sub(req);`

*   **介绍**
    
    订阅注册需要的实时信息，指定股票和订阅的数据类型即可。
    
*   **参数**
    

    
    message C2S
    {
        repeated Qot_Common.Security securityList = 1; //股票数组
        repeated int32 subTypeList = 2; //Qot_Common.SubType，订阅数据类型数组
        required bool isSubOrUnSub = 3; //true 表示订阅，false 表示反订阅
        optional bool isRegOrUnRegPush = 4; //是否注册或反注册该连接上面行情的推送，该参数不指定不做注册反注册操作
        repeated int32 regPushRehabTypeList = 5; //Qot_Common.RehabType，复权类型，注册推送并且是 K 线类型才生效，其他订阅类型忽略该参数，注册 K 线推送时该参数不指定默认前复权
        optional bool isFirstPush = 6; //注册后如果本地已有数据是否首推一次已存在数据，该参数不指定则默认 true
        optional bool isUnsubAll = 7; //一键取消当前连接的所有订阅，当被设置为 True 时忽略其他参数。
        optional bool isSubOrderBookDetail = 8; //订阅摆盘可用，是否订阅摆盘明细，仅支持 SF 行情，该参数不指定则默认 false
        optional bool extendedTime = 9;  // 是否允许美股盘前盘后数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）
        optional int32 session = 10; // 是否允许美股盘前盘后以及夜盘数据（仅用于订阅美股的实时 K 线、实时分时、实时逐笔）,该参数指定后忽略 extendedTime
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   订阅数据类型参见 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     

*   **返回**

    
    message S2C
    {
    }
    
    message Response
    {
        required int32 retType = 1 [default = -400]; //RetType,返回结果
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    
    function Sub(){
        const { RetType } = Common
        const { SubType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'XXXXX'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                const req = {
                    c2s: {
                    securityList: [\
                        {\
                            market: QotMarket.QotMarket_HK_Security,\
                            code: "00700",\
                        },\
                    ],
                    subTypeList: [ SubType.SubType_OrderBook ], // 订阅买卖摆盘类型
                    isSubOrUnSub: true, // 订阅 true, 反订阅 false
                    isRegOrUnRegPush: true, // 注册推送 true, 反注册推送 false
                    },
                }; // 订阅参数
    
                websocket.Sub(req) //# 订阅, OpenD 开始持续收到服务器的推送
                .then((res) => { 
                    let { errCode, retMsg, retType } = res
                    console.log("subscribe: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                })
                .catch((error) => {
                    if ("retMsg" in error) {
                        console.log("error:", error.retMsg);
                    }
                });
            } else {
                console.log("error", msg);
            }
        };
    
        websocket.onPush = (cmd, res)=>{
            if(ftCmdID.QotUpdateOrderBook.cmd == cmd){
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    console.log("OrderBookTest", s2c);
                } else {
                    console.log("OrderBookTest: error")
                }
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
    
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 5000); // 接收 OpenD 的推送持续时间为5秒,5秒后断开
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

*   **Output**

    subscribe: errCode 0, retMsg , retType 0
    OrderBookTest {"security":{"market":1,"code":"00700"},"orderBookAskList":[{"price":492.6,"volume":"32300","orederCount":55},{"price":492.8,"volume":"14100","orederCount":16},{"price":493,"volume":"25200","orederCount":30},{"price":493.2,"volume":"12300","orederCount":11},{"price":493.4,"volume":"9000","orederCount":7},{"price":493.6,"volume":"13500","orederCount":10},{"price":493.8,"volume":"9800","orederCount":12},{"price":494,"volume":"10500","orederCount":18},{"price":494.2,"volume":"6100","orederCount":6},{"price":494.4,"volume":"11400","orederCount":5}],"orderBookBidList":[{"price":492.4,"volume":"30600","orederCount":59},{"price":492.2,"volume":"29800","orederCount":49},{"price":492,"volume":"97900","orederCount":120},{"price":491.8,"volume":"24700","orederCount":55},{"price":491.6,"volume":"35300","orederCount":50},{"price":491.4,"volume":"11200","orederCount":18},{"price":491.2,"volume":"29300","orederCount":39},{"price":491,"volume":"77900","orederCount":229},{"price":490.8,"volume":"14100","orederCount":39},{"price":490.6,"volume":"22800","orederCount":34}]}
    OrderBookTest { ... }
    ...
    ...
    stop
    

1  
2  
3  
4  
5  
6  

接口限制

*   支持多种实时数据类型的订阅，参见 [SubType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5878)
     ，每支股票订阅一个类型占用一个额度。
*   订阅额度规则请参见 [订阅额度 & 历史 K 线额度](https://openapi.futunn.com/futu-api-doc/intro/authority.html#1314)
    。
*   至少订阅一分钟才可以反订阅。
*   由于港股 SF 行情摆盘数据量较大，为保证 SF 行情的速度和 OpenD 的处理性能，目前 SF 权限用户仅限同时订阅 50 只证券类产品（含 hkex 的正股、窝轮、牛熊）的摆盘、经纪队列，剩余订阅额度仍可用于订阅其他类型，如：逐笔，买卖经纪等。
*   港股期权期货在 LV1 权限下，不支持订阅逐笔类型。

← [行情对象](https://openapi.futunn.com/futu-api-doc/quote/base.html) [获取订阅状态](https://openapi.futunn.com/futu-api-doc/quote/query-subscription.html)
 →