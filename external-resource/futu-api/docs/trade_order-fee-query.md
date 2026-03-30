 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/order-fee-query.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/order-fee-query.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/order-fee-query.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/order-fee-query.html)
    

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
    

[#](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html#5647)
 查询订单费用
====================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`order_fee_query(order_id_list=[], acc_id=0, acc_index=0, trd_env=TrdEnv.REAL)`

*   **介绍**
    
    查询指定订单的收费明细（最低版本要求：8.2.4218）
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | order\_id\_list | list | 订单号列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   每次请求最多查询 400 笔订单<br>*   list 内元素类型为 str |
    | trd\_env | [TrdEnv](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回订单费用列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   订单列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | order\_id | str | 订单号 |
        | fee\_amount | float | 总费用 |
        | fee\_details | list | 收费明细<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   格式：\[('收费项1', 收费项1的金额), ('收费项2', 收费项2的金额), ('收费项3', 收费项3的金额)……\]<br>*   常见的收费项包括：佣金、平台使用费、期权监管费、期权清算费、期权交收费、交收费、证监会规费、交易活动费 |
        
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret1, data1 = trd_ctx.history_order_list_query(status_filter_list=[OrderStatus.FILLED_ALL])
    if ret1 == RET_OK:
        if data1.shape[0] > 0:  # 如果订单列表不为空
            ret2, data2 = trd_ctx.order_fee_query(data1['order_id'].values.tolist())  # 将订单 id 转为 list，查询订单费用
            if ret2 == RET_OK:
                print(data2)
                print(data2['fee_details'][0])  # 打印第一笔订单的收费明细
            else:
                print('order_fee_query error: ', data2)
    else:
        print('order_list_query error: ', data1)
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

*   **Output**

                                                order_id  fee_amount                                        fee_details
    0  v3_20240314_12345678_MTc4NzA5NzY5OTA3ODAzMzMwN       10.46  [(佣金, 5.85), (平台使用费, 2.7), (期权监管费, 0.11), (期权清...\
    1  v3_20240318_12345678_MTM5Nzc5MDYxNDY1NDM1MDI1M        2.25  [(佣金, 0.99), (平台使用费, 1.0), (交收费, 0.15), (证监会规费...\
    [('佣金', 5.85), ('平台使用费', 2.7), ('期权监管费', 0.11), ('期权清算费', 0.18), ('期权交收费', 1.62)]\
    \
\
1  \
2  \
3  \
4  \
\
[#](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html#7662)\
 Trd\_GetOrderFee.proto\
----------------------------------------------------------------------------------------------------\
\
*   **介绍**\
    \
    查询指定订单的收费明细（最低版本要求：8.2.4218）\
    \
*   **参数**\
    \
\
    message C2S\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated string orderIdExList = 2; // 服务器订单ID\
    }\
    \
    message Request\
    {\
        required C2S c2s = 1;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
\
*   **返回**\
\
    message S2C\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated Trd_Common.OrderFee orderFeeList = 2; //订单费用列表\
    }\
    \
    message Response\
    {\
        //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中\
        required int32 retType = 1 [default = -400];\
        optional string retMsg = 2;\
        optional int32 errCode = 3;\
        \
        optional S2C s2c = 4;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
> *   订单费用结构，参见 [OrderFee](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1085)\
>     \
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)\
>     \
\
*   **协议 ID**\
    \
    2225\
    \
\
`uint GetOrderFee(TrdGetOrderFee.Request req);`  \
`virtual void OnReply_GetOrderFee(FTAPI_Conn client, uint nSerialNo, TrdGetOrderFee.Response rsp);`\
\
*   **介绍**\
\
查询指定订单的收费明细（最低版本要求：8.2.4218）\
\
*   **参数**\
\
    message C2S\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated string orderIdExList = 2; // 服务器订单ID\
    }\
    \
    message Request\
    {\
        required C2S c2s = 1;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
\
*   **回调**\
\
    message S2C\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated Trd_Common.OrderFee orderFeeList = 2; //订单费用列表\
    }\
    \
    message Response\
    {\
        //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中\
        required int32 retType = 1 [default = -400];\
        optional string retMsg = 2;\
        optional int32 errCode = 3;\
        \
        optional S2C s2c = 4;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
> *   订单费用结构，参见 [OrderFee](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1085)\
>     \
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)\
>     \
\
*   **Example**\
\
    public class Program : FTSPI_Trd, FTSPI_Conn\
    {\
    	FTAPI_Trd trd = new FTAPI_Trd();\
    	private TrdCommon.TrdEnv trdEnv = TrdCommon.TrdEnv.TrdEnv_Real;\
    	private TrdCommon.TrdMarket trdMkt = TrdCommon.TrdMarket.TrdMarket_US;\
    	public Program()\
    	{\
    		trd.SetClientInfo("csharp", 1);  //设置客户端信息\
    		trd.SetConnCallback(this);  //设置连接回调\
    		trd.SetTrdCallback(this);   //设置交易回调\
    	}\
    	public void Start()\
    	{\
    		trd.InitConnect("127.0.0.1", (ushort)11111, false);\
    	}\
    	public void OnInitConnect(FTAPI_Conn client, long errCode, String desc)\
    	{\
    		Console.Write("Trd onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());\
    		if (errCode != 0)\
    			return;\
    		TrdGetAccList.C2S c2s = TrdGetAccList.C2S.CreateBuilder().SetUserID(0)\
    				.Build();\
    		TrdGetAccList.Request req = TrdGetAccList.Request.CreateBuilder().SetC2S(c2s).Build();\
    		uint seqNo = trd.GetAccList(req);\
    		Console.Write("Send TrdGetAccList: {0}\n", seqNo);\
    	}\
    	public void OnDisconnect(FTAPI_Conn client, long errCode)\
    	{\
    		Console.Write("Trd onDisConnect: {0}\n", errCode);\
    	}\
    	public void OnReply_GetAccList(FTAPI_Conn client, uint nSerialNo, TrdGetAccList.Response rsp)\
    	{\
    		if (rsp.RetType != (int)Common.RetType.RetType_Succeed)\
    		{\
    			Console.WriteLine("ERROR: GetAccList, retMsg = {0}", rsp.RetMsg);\
    			return;\
    		}\
    		Console.Write("Recv GetAccList succeed. accCount: {0}\n", rsp.S2C.AccListCount);\
    		ulong accID = 0;\
    		foreach (TrdCommon.TrdAcc acc in rsp.S2C.AccListList)\
    		{\
    			if (acc.TrdEnv == (int)trdEnv && acc.TrdMarketAuthListList[0] == (int)trdMkt)\
    			{\
    				accID = acc.AccID;\
    				// 打印账户信息\
    				Console.Write("accInfo: accId: {0}, trdEnv: {1}, trdMarketAuthList: {2}, simAccType: {3}\n",\
    					acc.AccID, (TrdCommon.TrdEnv)acc.TrdEnv, (TrdCommon.TrdMarket)acc.TrdMarketAuthListList[0],\
    					(TrdCommon.TrdAccType)acc.SimAccType);\
    				break;\
    			}\
    		}\
    		if (accID == 0)\
    		{\
    			return;\
    		}\
    		string svrOrderId = "20240403_900053_Fy0gPKjKE1ZW1hUuf0z0DABgxvzfmQYq";\
    		TrdCommon.TrdHeader header = TrdCommon.TrdHeader.CreateBuilder().SetTrdEnv((int)trdEnv)\
    			.SetTrdMarket((int)trdMkt)\
    			.SetAccID(accID)\
    			.Build();\
    		TrdGetOrderFee.C2S c2s = TrdGetOrderFee.C2S.CreateBuilder().SetHeader(header)\
    			.AddOrderIdExList(svrOrderId).Build();\
    		TrdGetOrderFee.Request req = TrdGetOrderFee.Request.CreateBuilder().SetC2S(c2s).Build();\
    		uint seqNo = trd.GetOrderFee(req);\
    		Console.Write("Send GetOrderFee: {0}\n", seqNo);\
    	}\
    	public void OnReply_GetOrderFee(FTAPI_Conn client, uint nSerialNo, TrdGetOrderFee.Response rsp)\
    	{\
    		if (rsp.RetType != (int)Common.RetType.RetType_Succeed)\
    		{\
    			Console.WriteLine("ERROR: GetOrderFee, retMsg = {0}", rsp.RetMsg);\
    			return;\
    		}\
    		foreach (TrdCommon.OrderFee ordFee in rsp.S2C.OrderFeeListList)\
    		{\
    			if (ordFee.HasFeeAmount)\
    			{\
    				Console.WriteLine("orderId: {0}, amount: {1}", ordFee.OrderIDEx, ordFee.FeeAmount);\
    			}\
    			else\
    			{\
    				Console.WriteLine("orderId: {0}", ordFee.OrderIDEx);\
    			}\
    			foreach (TrdCommon.OrderFeeItem feeItem in ordFee.FeeListList)\
    			{\
    				Console.WriteLine("title: {0}, fee: {1}", feeItem.Title, feeItem.Value);\
    			}\
    		}\
    	}\
    	public static void Main(String[] args)\
    	{\
    		FTAPI.Init();\
    		Program Trd = new Program();\
    		Trd.Start();\
    		while (true)\
    			Thread.Sleep(1000 * 600);\
    	}\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
35  \
36  \
37  \
38  \
39  \
40  \
41  \
42  \
43  \
44  \
45  \
46  \
47  \
48  \
49  \
50  \
51  \
52  \
53  \
54  \
55  \
56  \
57  \
58  \
59  \
60  \
61  \
62  \
63  \
64  \
65  \
66  \
67  \
68  \
69  \
70  \
71  \
72  \
73  \
74  \
75  \
76  \
77  \
78  \
79  \
80  \
81  \
82  \
83  \
84  \
85  \
86  \
87  \
88  \
89  \
90  \
91  \
92  \
93  \
94  \
95  \
96  \
97  \
98  \
\
*   **Output**\
\
    orderId: 20240403_900053_Fy0gPKjKE1ZW1hUuf0z0DABgxvzfmQYq, amount: 2.01\
    title: 佣金, fee: 0.99\
    title: 平台使用费, fee: 1\
    title: 交收费, fee: 0\
    title: 证监会规费, fee: 0.01\
    title: 交易活动费, fee: 0.01\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
\
`int getOrderFee(TrdGetOrderFee.Request req);`  \
`void onReply_GetOrderFee(FTAPI_Conn client, int nSerialNo, TrdGetOrderFee.Response rsp);`\
\
*   **介绍**\
\
查询指定订单的收费明细（最低版本要求：8.2.4218）\
\
*   **参数**\
\
    message C2S\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated string orderIdExList = 2; // 服务器订单ID\
    }\
    \
    message Request\
    {\
        required C2S c2s = 1;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
\
*   **回调**\
\
    message S2C\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated Trd_Common.OrderFee orderFeeList = 2; //订单费用列表\
    }\
    \
    message Response\
    {\
        //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中\
        required int32 retType = 1 [default = -400];\
        optional string retMsg = 2;\
        optional int32 errCode = 3;\
        \
        optional S2C s2c = 4;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
> *   订单结构，参见 [OrderFee](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1085)\
>     \
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)\
>     \
\
*   **Example**\
\
    public class TrdDemo implements FTSPI_Trd, FTSPI_Conn {\
        FTAPI_Conn_Trd trd = new FTAPI_Conn_Trd();\
    \
        public TrdDemo() {\
            trd.setClientInfo("javaclient", 1);  //设置客户端信息\
            trd.setConnSpi(this);  //设置连接回调\
            trd.setTrdSpi(this);   //设置交易回调\
        }\
    \
        public void start() {\
            trd.initConnect("127.0.0.1", (short)11111, false);\
        }\
    \
        @Override\
        public void onInitConnect(FTAPI_Conn client, long errCode, String desc)\
        {\
            System.out.printf("Trd onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());\
            if (errCode != 0)\
                return;\
    \
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.newBuilder()\
                    .setAccID(281756457888247915L)  //替换成自己的账号\
                    .setTrdEnv(TrdCommon.TrdEnv.TrdEnv_Real_VALUE)\
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)\
                    .build();\
            String orderId = "20210625_123456_OD|IqKozO18ODL1pwZNcLzgvEe9sW8gm"; //替换成自己的订单id\
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.newBuilder()\
                    .setTrdEnv(TrdCommon.TrdEnv.TrdEnv_Real_VALUE)\
                    .setAccID(Config.trdAcc)\
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)\
                    .build();\
            TrdGetOrderFee.C2S c2s = TrdGetOrderFee.C2S.newBuilder()\
                    .setHeader(header)\
                    .addOrderIdExList(orderId)\
                    .build();\
            TrdGetOrderFee.Request req =TrdGetOrderFee.Request.newBuilder().setC2S(c2s).build();\
            int sn = trd.getOrderFee(req);\
            System.out.printf("getOrderFee: sn=%d\n", sn);\
        }\
    \
        @Override\
        public void onDisconnect(FTAPI_Conn client, long errCode) {\
            System.out.printf("Trd onDisConnect: %d\n", errCode);\
        }\
    \
        @Override\
        public void onReply_GetOrderFee(FTAPI_Conn client, int nSerialNo, TrdGetOrderList.Response rsp) {\
            if (rsp.getRetType() != 0) {\
                System.out.printf("TrdGetOrderFee failed: %s\n", rsp.getRetMsg());\
            }\
            else {\
                try {\
                    String json = JsonFormat.printer().print(rsp);\
                    System.out.printf("onReply_GetOrderFee: %s\n", json);\
                } catch (InvalidProtocolBufferException err) {\
                    err.printStackTrace();\
                }\
            }\
        }\
    \
        public static void main(String[] args) {\
            FTAPI.init();\
            TrdDemo trd = new TrdDemo();\
            trd.start();\
    \
            while (true) {\
                try {\
                    Thread.sleep(1000 * 600);\
                } catch (InterruptedException exc) {\
    \
                }\
            }\
        }\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
35  \
36  \
37  \
38  \
39  \
40  \
41  \
42  \
43  \
44  \
45  \
46  \
47  \
48  \
49  \
50  \
51  \
52  \
53  \
54  \
55  \
56  \
57  \
58  \
59  \
60  \
61  \
62  \
63  \
64  \
65  \
66  \
67  \
68  \
69  \
70  \
71  \
72  \
73  \
74  \
\
*   **Output**\
\
    getOrderFee: sn=2\
    onReply_GetOrderFee: {\
      "retType": 0,\
      "retMsg": "",\
      "errCode": 0,\
      "s2c": {\
        "header": {\
          "trdEnv": 1,\
          "accID": "281756455988306264",\
          "trdMarket": 1\
        },\
        "orderFeeList": [{\
          "orderIDEx": "20210625_123456_OD|IqKozO18ODL1pwZNcLzgvEe9sW8gm",\
          "feeAmount": 7.569999999999999,\
          "feeList": [{\
            "title": "佣金",\
            "value": 1.0\
          }, {\
            "title": "平台使用费",\
            "value": 0.0\
          }, {\
            "title": "交易系统使用费",\
            "value": 0.0\
          }, {\
            "title": "交收费",\
            "value": 5.5\
          }, {\
            "title": "印花税",\
            "value": 1.0\
          }, {\
            "title": "交易费",\
            "value": 0.05\
          }, {\
            "title": "交易征费",\
            "value": 0.02\
          }]\
        }]\
      }\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
35  \
36  \
37  \
38  \
39  \
\
`Futu::u32_t GetOrderFee(const Trd_GetOrderFee::Request &stReq);`  \
`virtual void OnReply_GetOrderFee(Futu::u32_t nSerialNo, const Trd_GetOrderFee::Response &stRsp) = 0;`\
\
*   **介绍**\
    \
    查询指定订单的收费明细（最低版本要求：8.2.4218）\
    \
*   **参数**\
    \
\
    message C2S\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated string orderIdExList = 2; // 服务器订单ID\
    }\
    \
    message Request\
    {\
        required C2S c2s = 1;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
\
*   **回调**\
\
    message S2C\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated Trd_Common.OrderFee orderFeeList = 2; //订单费用列表\
    }\
    \
    message Response\
    {\
        //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中\
        required int32 retType = 1 [default = -400];\
        optional string retMsg = 2;\
        optional int32 errCode = 3;\
        \
        optional S2C s2c = 4;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
> *   订单结构，参见 [OrderFee](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1085)\
>     \
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)\
>     \
\
*   **Example**\
\
    class Program : public FTSPI_Qot, public FTSPI_Trd, public FTSPI_Conn\
    {\
    public:\
    \
    	Program() {\
    		m_pTrdApi = FTAPI::CreateTrdApi();\
    		m_pTrdApi->RegisterTrdSpi(this);\
    		m_pTrdApi->RegisterConnSpi(this);\
    	}\
    \
    	~Program() {\
    		if (m_pTrdApi != nullptr)\
    		{\
    			m_pTrdApi->UnregisterTrdSpi();\
    			m_pTrdApi->UnregisterConnSpi();\
    			FTAPI::ReleaseTrdApi(m_pTrdApi);\
    			m_pTrdApi = nullptr;\
    		}\
    	}\
    \
    	void Start() {\
    		m_pTrdApi->InitConnect("127.0.0.1", 11111, false);\
    	}\
    \
    \
    	virtual void OnInitConnect(FTAPI_Conn* pConn, Futu::i64_t nErrCode, const char* strDesc) {\
    		cout << "connect" << endl;\
    \
    		// 组包\
    		Trd_GetOrderFee::Request req;\
    		Trd_GetOrderFee::C2S *c2s = req.mutable_c2s();\
    		Trd_Common::TrdHeader *header = c2s->mutable_header();\
    		header->set_accid(281756455983234005);\
    		header->set_trdenv(1);\
    		header->set_trdmarket(1);\
    		c2s->add_orderidexlist("20240410_900053_OD|kSoBjXk8SRhW4aJfWHwAmrhzYFyJS");\
    \
            m_GetOrderFeeSerialNo = m_pTrdApi->GetOrderFee(req);\
            cout << "Request GetOrderFee SerialNo: " << m_GetOrderFeeSerialNo << endl;\
    	}\
    \
    	virtual void OnReply_GetOrderFee(Futu::u32_t nSerialNo, const Trd_GetOrderFee::Response &stRsp){\
            if(nSerialNo == m_GetOrderFeeSerialNo)\
            {\
                cout << "OnReply_GetOrderFee SerialNo: " << nSerialNo << endl;\
                // 解析内部结构打印出来\
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件\
                string resp_str;\
                ProtoBufToBodyData(stRsp, resp_str);\
                cout << UTF8ToLocal(resp_str) << endl;\
            }\
    	}\
    \
    protected:\
    	FTAPI_Trd *m_pTrdApi;\
    \
        Futu::u32_t m_GetOrderFeeSerialNo;\
    };\
    \
    int32_t main(int32_t argc, char** argv)\
    {\
    	FTAPI::Init();\
    \
    	{\
    		Program program;\
    		program.Start();\
    		getchar();\
    	}\
    \
    	protobuf::ShutdownProtobufLibrary();\
    	FTAPI::UnInit();\
    	return 0;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
35  \
36  \
37  \
38  \
39  \
40  \
41  \
42  \
43  \
44  \
45  \
46  \
47  \
48  \
49  \
50  \
51  \
52  \
53  \
54  \
55  \
56  \
57  \
58  \
59  \
60  \
61  \
62  \
63  \
64  \
65  \
66  \
67  \
68  \
69  \
70  \
71  \
72  \
73  \
\
*   **Output**\
\
    connect\
    Request_GetOrderFee SerialNo: 4\
    OnReply_GetOrderFee SerialNo: 4\
    {\
        "retType": 0,\
        "retMsg": "",\
        "errCode": 0,\
        "s2c": {\
            "header": {\
                "trdEnv": 1,\
                "accID": "281756455983234005",\
                "trdMarket": 1\
            },\
            "orderFeeList": [\
                {\
                    "orderIDEx": "20240410_900053_OD|kSoBjXk8SRhW4aJfWHwAmrhzYFyJS",\
                    "feeAmount": 24.27,\
                    "feeList": [\
                        {\
                            "title": "佣金",\
                            "value": 3\
                        },\
                        {\
                            "title": "平台使用费",\
                            "value": 15\
                        },\
                        {\
                            "title": "交易系统使用费",\
                            "value": 0\
                        },\
                        {\
                            "title": "交收费",\
                            "value": 2\
                        },\
                        {\
                            "title": "印花税",\
                            "value": 4\
                        },\
                        {\
                            "title": "交易费",\
                            "value": 0.18\
                        },\
                        {\
                            "title": "证监会征费",\
                            "value": 0.09\
                        },\
                        {\
                            "title": "财汇局征费",\
                            "value": 0\
                        }\
                    ]\
                }\
            ]\
        }\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
35  \
36  \
37  \
38  \
39  \
40  \
41  \
42  \
43  \
44  \
45  \
46  \
47  \
48  \
49  \
50  \
51  \
52  \
53  \
54  \
55  \
\
`GetOrderFee(req);`\
\
*   **介绍**\
    \
    查询指定订单的收费明细（最低版本要求：8.2.4218）\
    \
*   **参数**\
    \
\
    message C2S\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated string orderIdExList = 2; // 服务器订单ID\
    }\
    \
    message Request\
    {\
        required C2S c2s = 1;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
\
*   **返回**\
\
    message S2C\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated Trd_Common.OrderFee orderFeeList = 2; //订单费用列表\
    }\
    \
    message Response\
    {\
        //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中\
        required int32 retType = 1 [default = -400];\
        optional string retMsg = 2;\
        optional int32 errCode = 3;\
        \
        optional S2C s2c = 4;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
> *   订单结构，参见 [OrderFee](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1085)\
>     \
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)\
>     \
\
*   **Example**\
\
    import ftWebsocket from "futu-api";\
    import { ftCmdID } from "futu-api";\
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";\
    import beautify from "js-beautify";\
    \
    function TrdGetOrderFee(){\
        const { RetType } = Common\
        const { TrdEnv, TrdMarket } = Trd_Common\
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ec16fde057a2e7a0'];\
        let websocket = new ftWebsocket();\
    \
        websocket.onlogin = (ret, msg)=>{\
            if (ret) { // 登录成功\
                websocket.GetAccList({\
                    c2s: {\
                        userID: 0,\
                    },\
                }).then((res) => {\
                    let { retType,s2c: { accList } } = res\
                    if(retType == RetType.RetType_Succeed){\
                        let acc = accList.filter((item)=>{ \
                            return item.trdEnv != TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})\
                        })[0]; // 样例取第一个香港市场真实环境账户\
    \
                        const req = {\
                            c2s: {\
                                header: {\
                                    trdEnv: acc.trdEnv,\
                                    accID: acc.accID,\
                                    trdMarket: TrdMarket.TrdMarket_HK,\
                                },\
                                orderIdExList:["6735626686"],\
                            },\
                        };\
    \
                        websocket.GetOrderFee(req)\
                        .then((res) => {\
                            let { errCode, retMsg, retType,s2c } = res\
                            console.log("GetOrderFee: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); \
                            if(retType == RetType.RetType_Succeed){\
                                let data = beautify(JSON.stringify(s2c), {\
                                    indent_size: 2,\
                                    space_in_empty_paren: true,\
                                });\
                                console.log(data);\
                            }\
                        })\
                        .catch((error) => {\
                            console.log("error:", error);\
                        });\
    \
                    }\
                })\
                .catch((error) => {\
                    console.log("GetAccList error:", error);\
                });\
            } else {\
                console.log("error", msg);\
            }\
        };\
    \
        websocket.start(addr, port, enable_ssl, key);\
    \
        //关闭行情连接，连接不再使用之后，要关闭，否则占用不必要资源\
        //同时OpenD也限制了最多128条连接\
        //也可以一个页面或者一个项目维护一条连接，这里范例请求一次创建一条连接\
        setTimeout(()=>{ \
            websocket.stop();\
            console.log("stop");\
        }, 5000); // 5秒后断开\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
35  \
36  \
37  \
38  \
39  \
40  \
41  \
42  \
43  \
44  \
45  \
46  \
47  \
48  \
49  \
50  \
51  \
52  \
53  \
54  \
55  \
56  \
57  \
58  \
59  \
60  \
61  \
62  \
63  \
64  \
65  \
66  \
67  \
68  \
69  \
70  \
71  \
\
*   **Output**\
\
    GetOrderFee: errCode 0, retMsg , retType 0\
    {\
      "header": {\
        "trdEnv": 1,\
        "accID": "281756455988249902",\
        "trdMarket": 1\
      },\
      "orderFeeList": [{\
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",\
        "feeAmount": 522700.6,\
        "feeList": [\
            {\
                "title":"...",\
                "value":"...",\
            }, ...,{\
                "title":"...",\
                "value":"...",\
            },\
        ]\
      }, ..., {\
        "orderIDEx": "20210913_5915950_OD|rILqM3WaKl2rXYpRYuigcJmBKtRld",\
        "feeAmount": 67356.8,\
        "feeList": [\
            {\
                "title":"...",\
                "value":"...",\
            }, ...,{\
                "title":"...",\
                "value":"...",\
            },\
        ]\
      }]\
    }\
    stop\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
\
接口限制\
\
*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询订单费用接口。\
*   仅支持查询 2018-01-01 之后的订单。\
*   模拟账户不支持查询订单费用。\
*   加拿大券商账户不支持查询订单费用。\
\
*   Python\
*   Proto\
*   C#\
*   Java\
*   C++\
*   JavaScript\
\
`order_fee_query(order_id_list=[], acc_id=0, acc_index=0, trd_env=TrdEnv.REAL)`\
\
*   **介绍**\
    \
    查询指定订单的收费明细（最低版本要求：8.2.4218）\
    \
*   **参数**\
    \
    | 参数  | 类型  | 说明  |\
    | --- | --- | --- |\
    | order\_id\_list | list | 订单号列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   每次请求最多查询 400 笔订单<br>*   list 内元素类型为 str |\
    | trd\_env | [TrdEnv](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6374) | 交易环境 |\
    | acc\_id | int | 交易业务账户 ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |\
    | acc\_index | int | 交易业务账户列表中的账户序号<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |\
    \
\
*   **返回**\
    \
    | 参数  | 类型  | 说明  |\
    | --- | --- | --- |\
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |\
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回订单费用列表 |\
    | str | 当 ret != RET\_OK 时，返回错误描述 |\
    \
    *   订单列表格式如下：\
        \
        | 字段  | 类型  | 说明  |\
        | --- | --- | --- |\
        | order\_id | str | 订单号 |\
        | fee\_amount | float | 总费用 |\
        | fee\_details | list | 收费明细<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   格式：\[('收费项1', 收费项1的金额), ('收费项2', 收费项2的金额), ('收费项3', 收费项3的金额)……\]<br>*   常见的收费项包括：佣金、平台使用费、期权监管费、期权清算费、期权交收费、交收费、证监会规费、交易活动费 |\
        \
*   **Example**\
    \
\
    from moomoo import *\
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)\
    ret1, data1 = trd_ctx.history_order_list_query(status_filter_list=[OrderStatus.FILLED_ALL])\
    if ret1 == RET_OK:\
        if data1.shape[0] > 0:  # 如果订单列表不为空\
            ret2, data2 = trd_ctx.order_fee_query(data1['order_id'].values.tolist())  # 将订单 id 转为 list，查询订单费用\
            if ret2 == RET_OK:\
                print(data2)\
                print(data2['fee_details'][0])  # 打印第一笔订单的收费明细\
            else:\
                print('order_fee_query error: ', data2)\
    else:\
        print('order_list_query error: ', data1)\
    trd_ctx.close()\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
\
*   **Output**\
\
                                                order_id  fee_amount                                        fee_details\
    0  v3_20240314_12345678_MTc4NzA5NzY5OTA3ODAzMzMwN       10.46  [(佣金, 5.85), (平台使用费, 2.7), (期权监管费, 0.11), (期权清...\
    1  v3_20240318_12345678_MTM5Nzc5MDYxNDY1NDM1MDI1M        2.25  [(佣金, 0.99), (平台使用费, 1.0), (交收费, 0.15), (证监会规费...\
    [('佣金', 5.85), ('平台使用费', 2.7), ('期权监管费', 0.11), ('期权清算费', 0.18), ('期权交收费', 1.62)]\
    \
\
1  \
2  \
3  \
4  \
\
[#](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html#7662-2)\
 Trd\_GetOrderFee.proto\
------------------------------------------------------------------------------------------------------\
\
*   **介绍**\
    \
    查询指定订单的收费明细（最低版本要求：8.2.4218）\
    \
*   **参数**\
    \
\
    message C2S\
    {\
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated string orderIdExList = 2; // 服务器订单ID\
    }\
    \
    message Request\
    {\
        required C2S c2s = 1;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
\
*   **返回**\
\
    message S2C\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated Trd_Common.OrderFee orderFeeList = 2; //订单费用列表\
    }\
    \
    message Response\
    {\
        //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中\
        required int32 retType = 1 [default = -400];\
        optional string retMsg = 2;\
        optional int32 errCode = 3;\
        \
        optional S2C s2c = 4;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
> *   订单结构，参见 [OrderFee](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1085)\
>     \
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)\
>     \
\
*   **协议 ID**\
    \
    2225\
    \
\
`uint GetOrderFee(TrdGetOrderFee.Request req);`  \
`virtual void OnReply_GetOrderFee(MMAPI_Conn client, uint nSerialNo, TrdGetOrderFee.Response rsp);`\
\
*   **介绍**\
\
查询指定订单的收费明细（最低版本要求：8.2.4218）\
\
*   **参数**\
\
    message C2S\
    {\
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated string orderIdExList = 2; // 服务器订单ID\
    }\
    \
    message Request\
    {\
        required C2S c2s = 1;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
\
*   **回调**\
\
    message S2C\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated Trd_Common.OrderFee orderFeeList = 2; //订单费用列表\
    }\
    \
    message Response\
    {\
        //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中\
        required int32 retType = 1 [default = -400];\
        optional string retMsg = 2;\
        optional int32 errCode = 3;\
        \
        optional S2C s2c = 4;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
> *   订单结构，参见 [OrderFee](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1085)\
>     \
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)\
>     \
\
*   **Example**\
\
    public class Program : MMSPI_Trd, MMSPI_Conn\
    {\
    	MMAPI_Trd trd = new MMAPI_Trd();\
    	private TrdCommon.TrdEnv trdEnv = TrdCommon.TrdEnv.TrdEnv_Real;\
    	private TrdCommon.TrdMarket trdMkt = TrdCommon.TrdMarket.TrdMarket_HK;\
    	public Program()\
    	{\
    		trd.SetClientInfo("csharp", 1);  //设置客户端信息\
    		trd.SetConnCallback(this);  //设置连接回调\
    		trd.SetTrdCallback(this);   //设置交易回调\
    	}\
    	public void Start()\
    	{\
    		trd.InitConnect("127.0.0.1", (ushort)11111, false);\
    	}\
    	public void OnInitConnect(MMAPI_Conn client, long errCode, String desc)\
    	{\
    		Console.Write("Trd onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());\
    		if (errCode != 0)\
    			return;\
    		TrdGetAccList.C2S c2s = TrdGetAccList.C2S.CreateBuilder().SetUserID(0)\
    				.Build();\
    		TrdGetAccList.Request req = TrdGetAccList.Request.CreateBuilder().SetC2S(c2s).Build();\
    		uint seqNo = trd.GetAccList(req);\
    		Console.Write("Send TrdGetAccList: {0}\n", seqNo);\
    	}\
    	public void OnDisconnect(MMAPI_Conn client, long errCode)\
    	{\
    		Console.Write("Trd onDisConnect: {0}\n", errCode);\
    	}\
    	public void OnReply_GetAccList(MMAPI_Conn client, uint nSerialNo, TrdGetAccList.Response rsp)\
    	{\
    		if (rsp.RetType != (int)Common.RetType.RetType_Succeed)\
    		{\
    			Console.WriteLine("ERROR: GetAccList, retMsg = {0}", rsp.RetMsg);\
    			return;\
    		}\
    		Console.Write("Recv GetAccList succeed. accCount: {0}\n", rsp.S2C.AccListCount);\
    		ulong accID = 0;\
    		foreach (TrdCommon.TrdAcc acc in rsp.S2C.AccListList)\
    		{\
    			if (acc.TrdEnv == (int)trdEnv && acc.TrdMarketAuthListList[0] == (int)trdMkt)\
    			{\
    				accID = acc.AccID;\
    				// 打印账户信息\
    				Console.Write("accInfo: accId: {0}, trdEnv: {1}, trdMarketAuthList: {2}, simAccType: {3}\n",\
    					acc.AccID, (TrdCommon.TrdEnv)acc.TrdEnv, (TrdCommon.TrdMarket)acc.TrdMarketAuthListList[0],\
    					(TrdCommon.TrdAccType)acc.SimAccType);\
    				break;\
    			}\
    		}\
    		if (accID == 0)\
    		{\
    			return;\
    		}\
    		string svrOrderId = "20240409_900062_ODc3ODI3NDQwNTA3NjU1NTkzNTRmNDJk";\
    		TrdCommon.TrdHeader header = TrdCommon.TrdHeader.CreateBuilder().SetTrdEnv((int)trdEnv)\
    			.SetTrdMarket((int)trdMkt)\
    			.SetAccID(accID)\
    			.Build();\
    		TrdGetOrderFee.C2S c2s = TrdGetOrderFee.C2S.CreateBuilder().SetHeader(header)\
    			.AddOrderIdExList(svrOrderId).Build();\
    		TrdGetOrderFee.Request req = TrdGetOrderFee.Request.CreateBuilder().SetC2S(c2s).Build();\
    		uint seqNo = trd.GetOrderFee(req);\
    		Console.Write("Send GetOrderFee: {0}\n", seqNo);\
    	}\
    	public void OnReply_GetOrderFee(MMAPI_Conn client, uint nSerialNo, TrdGetOrderFee.Response rsp)\
    	{\
    		if (rsp.RetType != (int)Common.RetType.RetType_Succeed)\
    		{\
    			Console.WriteLine("ERROR: GetOrderFee, retMsg = {0}", rsp.RetMsg);\
    			return;\
    		}\
    		foreach (TrdCommon.OrderFee ordFee in rsp.S2C.OrderFeeListList)\
    		{\
    			if (ordFee.HasFeeAmount)\
    			{\
    				Console.WriteLine("orderId: {0}, amount: {1}", ordFee.OrderIDEx, ordFee.FeeAmount);\
    			}\
    			else\
    			{\
    				Console.WriteLine("orderId: {0}", ordFee.OrderIDEx);\
    			}\
    			foreach (TrdCommon.OrderFeeItem feeItem in ordFee.FeeListList)\
    			{\
    				Console.WriteLine("title: {0}, fee: {1}", feeItem.Title, feeItem.Value);\
    			}\
    		}\
    	}\
    	public static void Main(String[] args)\
    	{\
    		MMAPI.Init();\
    		Program Trd = new Program();\
    		Trd.Start();\
    		while (true)\
    			Thread.Sleep(1000 * 600);\
    	}\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
35  \
36  \
37  \
38  \
39  \
40  \
41  \
42  \
43  \
44  \
45  \
46  \
47  \
48  \
49  \
50  \
51  \
52  \
53  \
54  \
55  \
56  \
57  \
58  \
59  \
60  \
61  \
62  \
63  \
64  \
65  \
66  \
67  \
68  \
69  \
70  \
71  \
72  \
73  \
74  \
75  \
76  \
77  \
78  \
79  \
80  \
81  \
82  \
83  \
84  \
85  \
86  \
87  \
88  \
89  \
90  \
91  \
92  \
93  \
94  \
95  \
96  \
97  \
98  \
\
*   **Output**\
\
    orderId: 20240409_900062_ODc3ODI3NDQwNTA3NjU1NTkzNTRmNDJk, amount: 28.6\
    title: ..., fee: 3\
    title: ..., fee: 15\
    title: ..., fee: 0\
    title: ..., fee: 2\
    title: ..., fee: 8\
    title: ..., fee: 0.4\
    title: ..., fee: 0.19\
    title: ..., fee: 0.01\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
\
`int getOrderFee(TrdGetOrderFee.Request req);`  \
`void onReply_GetOrderFee(MMAPI_Conn client, int nSerialNo, TrdGetOrderFee.Response rsp);`\
\
*   **介绍**\
\
查询指定订单的收费明细（最低版本要求：8.2.4218）\
\
*   **参数**\
\
    message C2S\
    {\
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated string orderIdExList = 2; // 服务器订单ID\
    }\
    \
    message Request\
    {\
        required C2S c2s = 1;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
\
*   **回调**\
\
    message S2C\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated Trd_Common.OrderFee orderFeeList = 2; //订单费用列表\
    }\
    \
    message Response\
    {\
        //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中\
        required int32 retType = 1 [default = -400];\
        optional string retMsg = 2;\
        optional int32 errCode = 3;\
        \
        optional S2C s2c = 4;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
> *   订单结构，参见 [OrderFee](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1085)\
>     \
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)\
>     \
\
*   **Example**\
\
    public class TrdDemo implements MMSPI_Trd, MMSPI_Conn {\
        MMAPI_Conn_Trd trd = new MMAPI_Conn_Trd();\
    \
        public TrdDemo() {\
            trd.setClientInfo("javaclient", 1);  //设置客户端信息\
            trd.setConnSpi(this);  //设置连接回调\
            trd.setTrdSpi(this);   //设置交易回调\
        }\
    \
        public void start() {\
            trd.initConnect("127.0.0.1", (short)11111, false);\
        }\
    \
        @Override\
        public void onInitConnect(MMSPI_Conn client, long errCode, String desc)\
        {\
            System.out.printf("Trd onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());\
            if (errCode != 0)\
                return;\
    \
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.newBuilder()\
                    .setAccID(281756457888247915L)  //替换成自己的账号\
                    .setTrdEnv(TrdCommon.TrdEnv.TrdEnv_Real_VALUE)\
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)\
                    .build();\
            String orderId = "20210625_123456_OD|IqKozO18ODL1pwZNcLzgvEe9sW8gm"; //替换成自己的订单id\
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.newBuilder()\
                    .setTrdEnv(TrdCommon.TrdEnv.TrdEnv_Real_VALUE)\
                    .setAccID(Config.trdAcc)\
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)\
                    .build();\
            TrdGetOrderFee.C2S c2s = TrdGetOrderFee.C2S.newBuilder()\
                    .setHeader(header)\
                    .addOrderIdExList(orderId)\
                    .build();\
            TrdGetOrderFee.Request req =TrdGetOrderFee.Request.newBuilder().setC2S(c2s).build();\
            int sn = trd.getOrderFee(req);\
            System.out.printf("getOrderFee: sn=%d\n", sn);\
        }\
    \
        @Override\
        public void onDisconnect(MMSPI_Conn client, long errCode) {\
            System.out.printf("Trd onDisConnect: %d\n", errCode);\
        }\
    \
        @Override\
        public void onReply_GetOrderFee(MMSPI_Conn client, int nSerialNo, TrdGetOrderList.Response rsp) {\
            if (rsp.getRetType() != 0) {\
                System.out.printf("TrdGetOrderFee failed: %s\n", rsp.getRetMsg());\
            }\
            else {\
                try {\
                    String json = JsonFormat.printer().print(rsp);\
                    System.out.printf("onReply_GetOrderFee: %s\n", json);\
                } catch (InvalidProtocolBufferException err) {\
                    err.printStackTrace();\
                }\
            }\
        }\
    \
        public static void main(String[] args) {\
            MMAPI.init();\
            TrdDemo trd = new TrdDemo();\
            trd.start();\
    \
            while (true) {\
                try {\
                    Thread.sleep(1000 * 600);\
                } catch (InterruptedException exc) {\
    \
                }\
            }\
        }\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
35  \
36  \
37  \
38  \
39  \
40  \
41  \
42  \
43  \
44  \
45  \
46  \
47  \
48  \
49  \
50  \
51  \
52  \
53  \
54  \
55  \
56  \
57  \
58  \
59  \
60  \
61  \
62  \
63  \
64  \
65  \
66  \
67  \
68  \
69  \
70  \
71  \
72  \
73  \
74  \
\
*   **Output**\
\
    getOrderFee: sn=2\
    onReply_GetOrderFee: {\
      "retType": 0,\
      "retMsg": "",\
      "errCode": 0,\
      "s2c": {\
        "header": {\
          "trdEnv": 1,\
          "accID": "281756455988306264",\
          "trdMarket": 1\
        },\
        "orderFeeList": [{\
          "orderIDEx": "20210625_123456_OD|IqKozO18ODL1pwZNcLzgvEe9sW8gm",\
          "feeAmount": 7.569999999999999,\
          "feeList": [{\
            "title": "佣金",\
            "value": 1.0\
          }, {\
            "title": "平台使用费",\
            "value": 0.0\
          }, {\
            "title": "交易系统使用费",\
            "value": 0.0\
          }, {\
            "title": "交收费",\
            "value": 5.5\
          }, {\
            "title": "印花税",\
            "value": 1.0\
          }, {\
            "title": "交易费",\
            "value": 0.05\
          }, {\
            "title": "交易征费",\
            "value": 0.02\
          }]\
        }]\
      }\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
35  \
36  \
37  \
38  \
39  \
\
`moomoo::u32_t GetOrderFee(const Trd_GetOrderFee::Request &stReq);`  \
`virtual void OnReply_GetOrderFee(moomoo::u32_t nSerialNo, const Trd_GetOrderFee::Response &stRsp) = 0;`\
\
*   **介绍**\
    \
    查询指定订单的收费明细（最低版本要求：8.2.4218）\
    \
*   **参数**\
    \
\
    message C2S\
    {\
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated string orderIdExList = 2; // 服务器订单ID\
    }\
    \
    message Request\
    {\
        required C2S c2s = 1;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
\
*   **回调**\
\
    message S2C\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated Trd_Common.OrderFee orderFeeList = 2; //订单费用列表\
    }\
    \
    message Response\
    {\
        //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中\
        required int32 retType = 1 [default = -400];\
        optional string retMsg = 2;\
        optional int32 errCode = 3;\
        \
        optional S2C s2c = 4;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
> *   订单结构，参见 [OrderFee](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1085)\
>     \
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)\
>     \
\
*   **Example**\
\
    class Program : public MMSPI_Qot, public MMSPI_Trd, public MMSPI_Conn\
    {\
    public:\
    \
    	Program() {\
    		m_pTrdApi = MMAPI::CreateTrdApi();\
    		m_pTrdApi->RegisterTrdSpi(this);\
    		m_pTrdApi->RegisterConnSpi(this);\
    	}\
    \
    	~Program() {\
    		if (m_pTrdApi != nullptr)\
    		{\
    			m_pTrdApi->UnregisterTrdSpi();\
    			m_pTrdApi->UnregisterConnSpi();\
    			MMAPI::ReleaseTrdApi(m_pTrdApi);\
    			m_pTrdApi = nullptr;\
    		}\
    	}\
    \
    	void Start() {\
    		m_pTrdApi->InitConnect("127.0.0.1", 11111, false);\
    	}\
    \
    \
    	virtual void OnInitConnect(MMAPI_Conn* pConn, moomoo::i64_t nErrCode, const char* strDesc) {\
    		cout << "connect" << endl;\
    \
    		// 组包\
    		Trd_GetOrderFee::Request req;\
    		Trd_GetOrderFee::C2S *c2s = req.mutable_c2s();\
    		Trd_Common::TrdHeader *header = c2s->mutable_header();\
    		header->set_accid(281756455983234005);\
    		header->set_trdenv(1);\
    		header->set_trdmarket(1);\
    		c2s->add_orderidexlist("20240410_900053_OD|kSoBjXk8SRhW4aJfWHwAmrhzYFyJS");\
    \
            m_GetOrderFeeSerialNo = m_pTrdApi->GetOrderFee(req);\
            cout << "Request GetOrderFee SerialNo: " << m_GetOrderFeeSerialNo << endl;\
    	}\
    \
    	virtual void OnReply_GetOrderFee(moomoo::u32_t nSerialNo, const Trd_GetOrderFee::Response &stRsp){\
            if(nSerialNo == m_GetOrderFeeSerialNo)\
            {\
                cout << "OnReply_GetOrderFee SerialNo: " << nSerialNo << endl;\
                // 解析内部结构打印出来\
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件\
                string resp_str;\
                ProtoBufToBodyData(stRsp, resp_str);\
                cout << UTF8ToLocal(resp_str) << endl;\
            }\
    	}\
    \
    protected:\
    	MMAPI_Trd *m_pTrdApi;\
    \
        moomoo::u32_t m_GetOrderFeeSerialNo;\
    };\
    \
    int32_t main(int32_t argc, char** argv)\
    {\
    	MMAPI::Init();\
    \
    	{\
    		Program program;\
    		program.Start();\
    		getchar();\
    	}\
    \
    	protobuf::ShutdownProtobufLibrary();\
    	MMAPI::UnInit();\
    	return 0;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
35  \
36  \
37  \
38  \
39  \
40  \
41  \
42  \
43  \
44  \
45  \
46  \
47  \
48  \
49  \
50  \
51  \
52  \
53  \
54  \
55  \
56  \
57  \
58  \
59  \
60  \
61  \
62  \
63  \
64  \
65  \
66  \
67  \
68  \
69  \
70  \
71  \
72  \
73  \
\
*   **Output**\
\
    connect\
    Request_GetOrderFee SerialNo: 4\
    OnReply_GetOrderFee SerialNo: 4\
    {\
    	"retType": 0,\
    	"retMsg": "",\
    	"errCode": 0,\
    	"s2c": {\
    		"header": {\
    			"trdEnv": 1,\
    			"accID": "281756455983234005",\
    			"trdMarket": 1\
    		},\
    		"orderFeeList": [{\
    			"orderIDEx": "20240410_900053_OD|kSoBjXk8SRhW4aJfWHwAmrhzYFyJS",\
    			"feeAmount": 24.27,\
    			"feeList": [{\
    				"title": "Commission",\
    				"value": 3\
    			},\
    			{\
    				"title": "Platform Fee",\
    				"value": 15\
    			},\
    			{\
    				"title": "Trading Tariff",\
    				"value": 0\
    			},\
    			{\
    				"title": "Settlement Fee",\
    				"value": 2\
    			},\
    			{\
    				"title": "Stamp Duty",\
    				"value": 4\
    			},\
    			{\
    				"title": "Trading Fee",\
    				"value": 0.18\
    			},\
    			{\
    				"title": "SFC Levy",\
    				"value": 0.09\
    			},\
    			{\
    				"title": "FRC Levy",\
    				"value": 0\
    			}]\
    		}]\
    	}\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
35  \
36  \
37  \
38  \
39  \
40  \
41  \
42  \
43  \
44  \
45  \
46  \
47  \
48  \
49  \
50  \
51  \
\
`GetOrderFee(req);`\
\
*   **介绍**\
    \
    查询指定订单的收费明细（最低版本要求：8.2.4218）\
    \
*   **参数**\
    \
\
    message C2S\
    {\
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated string orderIdExList = 2; // 服务器订单ID\
    }\
    \
    message Request\
    {\
        required C2S c2s = 1;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
\
*   **返回**\
\
    message S2C\
    {\
        required Trd_Common.TrdHeader header = 1; //交易公共参数头\
        repeated Trd_Common.OrderFee orderFeeList = 2; //订单费用列表\
    }\
    \
    message Response\
    {\
        //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中\
        required int32 retType = 1 [default = -400];\
        optional string retMsg = 2;\
        optional int32 errCode = 3;\
        \
        optional S2C s2c = 4;\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
\
> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)\
>     \
> *   订单结构，参见 [OrderFee](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1085)\
>     \
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)\
>     \
\
*   **Example**\
\
    import mmWebsocket from "moomoo-api";\
    import { mmCmdID } from "moomoo-api";\
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";\
    import beautify from "js-beautify";\
    \
    function TrdGetOrderFee(){\
        const { RetType } = Common\
        const { TrdEnv, TrdMarket } = Trd_Common\
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ec16fde057a2e7a0'];\
        let websocket = new mmWebsocket();\
    \
        websocket.onlogin = (ret, msg)=>{\
            if (ret) { // 登录成功\
                websocket.GetAccList({\
                    c2s: {\
                        userID: 0,\
                    },\
                }).then((res) => {\
                    let { retType,s2c: { accList } } = res\
                    if(retType == RetType.RetType_Succeed){\
                        let acc = accList.filter((item)=>{ \
                            return item.trdEnv != TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})\
                        })[0]; // 样例取第一个香港市场真实环境账户\
    \
                        const req = {\
                            c2s: {\
                                header: {\
                                    trdEnv: acc.trdEnv,\
                                    accID: acc.accID,\
                                    trdMarket: TrdMarket.TrdMarket_HK,\
                                },\
                                orderIdExList:["6735626686"],\
                            },\
                        };\
    \
                        websocket.GetOrderFee(req)\
                        .then((res) => {\
                            let { errCode, retMsg, retType,s2c } = res\
                            console.log("GetOrderFee: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); \
                            if(retType == RetType.RetType_Succeed){\
                                let data = beautify(JSON.stringify(s2c), {\
                                    indent_size: 2,\
                                    space_in_empty_paren: true,\
                                });\
                                console.log(data);\
                            }\
                        })\
                        .catch((error) => {\
                            console.log("error:", error);\
                        });\
    \
                    }\
                })\
                .catch((error) => {\
                    console.log("GetAccList error:", error);\
                });\
            } else {\
                console.log("error", msg);\
            }\
        };\
    \
        websocket.start(addr, port, enable_ssl, key);\
    \
        //关闭行情连接，连接不再使用之后，要关闭，否则占用不必要资源\
        //同时OpenD也限制了最多128条连接\
        //也可以一个页面或者一个项目维护一条连接，这里范例请求一次创建一条连接\
        setTimeout(()=>{ \
            websocket.stop();\
            console.log("stop");\
        }, 5000); // 5秒后断开\
    }\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
35  \
36  \
37  \
38  \
39  \
40  \
41  \
42  \
43  \
44  \
45  \
46  \
47  \
48  \
49  \
50  \
51  \
52  \
53  \
54  \
55  \
56  \
57  \
58  \
59  \
60  \
61  \
62  \
63  \
64  \
65  \
66  \
67  \
68  \
69  \
70  \
71  \
\
*   **Output**\
\
    GetOrderFee: errCode 0, retMsg , retType 0\
    {\
      "header": {\
        "trdEnv": 1,\
        "accID": "281756455988249902",\
        "trdMarket": 1\
      },\
      "orderFeeList": [{\
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",\
        "feeAmount": 522700.6,\
        "feeList": [\
            {\
                "title":"...",\
                "value":"...",\
            }, ...,{\
                "title":"...",\
                "value":"...",\
            },\
        ]\
      }, ..., {\
        "orderIDEx": "20210913_5915950_OD|rILqM3WaKl2rXYpRYuigcJmBKtRld",\
        "feeAmount": 67356.8,\
        "feeList": [\
            {\
                "title":"...",\
                "value":"...",\
            }, ...,{\
                "title":"...",\
                "value":"...",\
            },\
        ]\
      }]\
    }\
    stop\
    \
\
1  \
2  \
3  \
4  \
5  \
6  \
7  \
8  \
9  \
10  \
11  \
12  \
13  \
14  \
15  \
16  \
17  \
18  \
19  \
20  \
21  \
22  \
23  \
24  \
25  \
26  \
27  \
28  \
29  \
30  \
31  \
32  \
33  \
34  \
\
接口限制\
\
*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询订单费用接口。\
*   仅支持查询 2018-01-01 之后的订单。\
*   模拟账户不支持查询订单费用。\
*   加拿大券商账户不支持查询订单费用。\
\
← [响应订单推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order.html) [订阅交易推送](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html)\
 →\
\
[查询订单费用](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html)