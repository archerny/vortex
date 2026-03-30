 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/get-history-order-fill-list.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/get-history-order-fill-list.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/get-history-order-fill-list.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/get-history-order-fill-list.html)
    

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
        
    *   成交
        
        *   [查询当日成交](https://openapi.futunn.com/futu-api-doc/trade/get-order-fill-list.html)
            
        *   [查询历史成交](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html)
            
        *   [响应成交推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html)
            
        
    *   [交易定义](https://openapi.futunn.com/futu-api-doc/trade/trade.html)
        
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html#9015)
 查询历史成交
================================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`history_deal_list_query(code='', deal_market=TrdMarket.NONE, start='', end='', trd_env=TrdEnv.REAL, acc_id=0, acc_index=0)`

*   **介绍**
    
    查询指定交易业务账户的历史成交列表。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 代码过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只返回此代码对应的成交数据  <br>不传则返回所有 |
    | deal\_market | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 成交标的所属市场过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   成交标的市场过滤，会返回该市场下的成交数据<br>*   默认值为NONE，会返回账户下所有市场的成交数据 |
    | start | str | 开始时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传<br>*   期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
    | end | str | 结束时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传<br>*   期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
    | trd\_env | [TrdEnv](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6374) | 交易环境<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅支持 TrdEnv.REAL（真实环境），模拟环境暂不支持查询成交数据 |
    | acc\_id | int | 交易业务账户 ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    
    *   start 和 end 的组合如下
        
        | Start 类型 | End 类型 | 说明  |
        | --- | --- | --- |
        | str | str | start 和 end 分别为指定的日期 |
        | None | str | start 为 end 往前 90 天 |
        | str | None | end 为 start 往后 90 天 |
        | None | None | start 为往前 90 天，end 当前日期 |
        
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回交易成交列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   交易成交列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_side | [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815) | 交易方向 |
        | deal\_id | str | 成交号 |
        | order\_id | str | 订单号 |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | deal\_market | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 成交标的所属市场 |
        | qty | float | 成交数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
        | price | float | 成交价格<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超过部分四舍五入 |
        | create\_time | str | 创建时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
        | counter\_broker\_id | int | 对手经纪号<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅港股有效 |
        | counter\_broker\_name | str | 对手经纪名称<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅港股有效 |
        | status | [DealStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8317) | 成交状态 |
        | jp\_acc\_type | [SubAccType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6112) | 日本账户类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅对日本券商生效 |
        
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.history_deal_list_query()
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果成交列表不为空
            print(data['deal_id'][0])  # 获取历史成交的第一个成交号
            print(data['deal_id'].values.tolist())  # 转为 list
    else:
        print('history_deal_list_query error: ', data)
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

        code stock_name     deal_market         deal_id             order_id    qty  price trd_side              create_time  counter_broker_id counter_broker_name status jp_acc_type
    0  HK.00388      香港交易所    HK  5056208452274069375  4665291631090960915  100.0  370.0      BUY  2020-09-17 21:15:59.979                  5                         OK        N/A
    5056208452274069375
    ['5056208452274069375']
    

1  
2  
3  
4  

[#](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html#7585)
 Trd\_GetHistoryOrderFillList.proto
----------------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询指定交易业务账户的历史成交列表。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.OrderFill orderFillList = 2; //历史成交列表
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
> *   成交结构，参见 [OrderFill](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1253)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2222
    

`uint GetHistoryOrderFillList(TrdGetHistoryOrderFillList.Request req);`  
`virtual void OnReply_GetHistoryOrderFillList(FTAPI_Conn client, uint nSerialNo, TrdGetHistoryOrderFillList.Response rsp);`

*   **介绍**
    
    查询指定交易业务账户的历史成交列表。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.OrderFill orderFillList = 2; //历史成交列表
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
> *   成交结构，参见 [OrderFill](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1253)
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
            TrdCommon.TrdFilterConditions filter = TrdCommon.TrdFilterConditions.CreateBuilder().Build();
            TrdGetHistoryOrderFillList.C2S c2s = TrdGetHistoryOrderFillList.C2S.CreateBuilder()
                    .SetHeader(header)
                    .SetFilterConditions(filter)
                    .Build();
            TrdGetHistoryOrderFillList.Request req = TrdGetHistoryOrderFillList.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetHistoryOrderFillList(req);
            Console.Write("Send TrdGetHistoryOrderFillList: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetHistoryOrderFillList(FTAPI_Conn client, uint nSerialNo, TrdGetHistoryOrderFillListResponse rsp)
        {
            Console.Write("Reply: TrdGetHistoryOrderFillList: {0}\n", nSerialNo);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6827861023441251037
    Send TrdGetHistoryOrderFillList: 3
    Reply: TrdGetHistoryOrderFillList: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int getHistoryOrderFillList(TrdGetHistoryOrderFillList.Request req);`  
`void onReply_GetHistoryOrderFillList(FTAPI_Conn client, int nSerialNo, TrdGetHistoryOrderFillList.Response rsp);`

*   **介绍**
    
    查询指定交易业务账户的历史成交列表。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.OrderFill orderFillList = 2; //历史成交列表
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
> *   成交结构，参见 [OrderFill](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1253)
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
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_US_VALUE)
                    .build();
            TrdCommon.TrdFilterConditions filter = TrdCommon.TrdFilterConditions.newBuilder()
                    .setBeginTime("2021-03-01 00:00:00")
                    .setEndTime("2021-04-01 00:00:00")
                    .build();
            TrdGetHistoryOrderFillList.C2S c2s = TrdGetHistoryOrderFillList.C2S.newBuilder()
                    .setHeader(header)
                    .setFilterConditions(filter)
                    .build();
            TrdGetHistoryOrderFillList.Request req = TrdGetHistoryOrderFillList.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getHistoryOrderFillList(req);
            System.out.printf("Send TrdGetHistoryOrderFillList: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetHistoryOrderFillList(FTAPI_Conn client, int nSerialNo, TrdGetHistoryOrderFillList.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetHistoryOrderFillList failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetHistoryOrderFillList: %s\n", json);
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

*   **Output**

    Send TrdGetHistoryOrderFillList: 2
    Receive TrdGetHistoryOrderFillList: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 2
        },
        "orderFillList": [{\
          "trdSide": 1,\
          "fillID": "449150869556176742",\
          "fillIDEx": "20210330_15680495_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1.4",\
          "orderID": "6664320708369556828",\
          "orderIDEx": "20210330_15680495_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1",\
          "code": "FUTU",\
          "name": "富途控股",\
          "qty": 34.0,\
          "price": 127.64,\
          "createTime": "2021-03-30 09:34:24.019",\
          "secMarket": 2,\
          "createTimestamp": 1.617111264019109E9,\
          "updateTimestamp": 1.617111264019109E9,\
          "status": 0\
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

`Futu::u32_t GetHistoryOrderFillList(const Trd_GetHistoryOrderFillList::Request &stReq);`  
`virtual void OnReply_GetHistoryOrderFillList(Futu::u32_t nSerialNo, const Trd_GetHistoryOrderFillList::Response &stRsp) = 0;`

*   **介绍**
    
    查询指定交易业务账户的历史成交列表。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.OrderFill orderFillList = 2; //历史成交列表
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
> *   成交结构，参见 [OrderFill](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1253)
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
    		Trd_GetHistoryOrderFillList::Request req;
    		Trd_GetHistoryOrderFillList::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(281756456003951537L);
    		header->set_trdenv(1);
    		header->set_trdmarket(1);
    		Trd_Common::TrdFilterConditions *filter = c2s->mutable_filterconditions();
    		filter->set_begintime("2021-05-01 00:00:00");
    		filter->set_endtime("2021-06-01 00:00:00");
    
            m_GetHistoryOrderFillListSerialNo = m_pTrdApi->GetHistoryOrderFillList(req);
            cout << "Request GetHistoryOrderFillList SerialNo: " << m_GetHistoryOrderFillListSerialNo << endl;
    	}
    
    	virtual void OnReply_GetHistoryOrderFillList(Futu::u32_t nSerialNo, const Trd_GetHistoryOrderFillList::Response &stRsp){
            if(nSerialNo == m_GetHistoryOrderFillListSerialNo)
            {
                cout << "OnReply_GetHistoryOrderFillList SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_GetHistoryOrderFillListSerialNo;
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

*   **Output**

    connect
    Request GetHistoryOrderFillList SerialNo: 4
    OnReply_GetHistoryOrderFillList SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "header": {
       "trdEnv": 1,
       "accID": "281756456003951537",
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

`GetHistoryOrderFillList(req);`

*   **介绍**
    
    查询指定交易业务账户的历史成交列表。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.OrderFill orderFillList = 2; //历史成交列表
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
> *   成交结构，参见 [OrderFill](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1253)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetHistoryOrderFillList(){
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
                            return item.trdEnv != TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})
                        })[0]; // 样例取第一个香港市场真实环境账户
    
                        const req = {
                            c2s: {
                                header: {
                                    trdEnv: acc.trdEnv,
                                    accID: acc.accID,
                                    trdMarket: TrdMarket.TrdMarket_HK,
                                },
                                filterConditions:{
                                    beginTime:"2021-09-01 00:00:00",
                                    endTime:"2021-09-30 00:00:00",
                                },
                            },
                        };
    
                        websocket.GetHistoryOrderFillList(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetHistoryOrderFillList: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
71  
72  
73  
74  

*   **Output**

    GetHistoryOrderFillList: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756455988249902",
        "trdMarket": 1
      },
      "orderFillList": [{\
        "trdSide": 1,\
        "fillID": "932511865781776209",\
        "fillIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1.2",\
        "orderID": "4883217202603317248",\
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",\
        "code": "00700",\
        "name": "",\
        "qty": 100,\
        "price": 480,\
        "createTime": "2021-09-13 16:45:00.606",\
        "counterBrokerID": 5,\
        "counterBrokerName": "",\
        "secMarket": 1,\
        "createTimestamp": 1631522700.605828,\
        "updateTimestamp": 1631522700.531,\
        "status": 0\
      }, {\
        "trdSide": 1,\
        "fillID": "2611798069690910040",\
        "fillIDEx": "20210913_5915950_OD|rILqM3WaKl2rXYpRYuigcJmBKtRld_OD|rILqM3WaKl2rXYpRYuigcJmBKtRld.2",\
        "orderID": "9128832000130556480",\
        "orderIDEx": "20210913_5915950_OD|rILqM3WaKl2rXYpRYuigcJmBKtRld",\
        "code": "00700",\
        "name": "",\
        "qty": 100,\
        "price": 480,\
        "createTime": "2021-09-13 16:40:19.183",\
        "counterBrokerID": 5,\
        "counterBrokerName": "",\
        "secMarket": 1,\
        "createTimestamp": 1631522419.18269,\
        "updateTimestamp": 1631522419.005,\
        "status": 0\
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

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询历史成交接口

提示

*   历史成交，按照时间的“倒序”进行排列，即：后成交的记录在前，先成交的记录在后

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`history_deal_list_query(code='', deal_market=TrdMarket.NONE, start='', end='', trd_env=TrdEnv.REAL, acc_id=0, acc_index=0)`

*   **介绍**
    
    查询指定交易业务账户的历史成交列表。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 代码过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只返回此代码对应的成交数据  <br>不传则返回所有 |
    | deal\_market | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 成交标的所属市场过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   成交标的市场过滤，会返回该市场下的成交数据<br>*   默认值为NONE，会返回账户下所有市场的成交数据 |
    | start | str | 开始时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传<br>*   期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
    | end | str | 结束时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传<br>*   期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
    | trd\_env | [TrdEnv](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6374) | 交易环境<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅支持 TrdEnv.REAL（真实环境），模拟环境暂不支持查询成交数据 |
    | acc\_id | int | 交易业务账户 ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    
    *   start 和 end 的组合如下
        
        | Start 类型 | End 类型 | 说明  |
        | --- | --- | --- |
        | str | str | start 和 end 分别为指定的日期 |
        | None | str | start 为 end 往前 90 天 |
        | str | None | end 为 start 往后 90 天 |
        | None | None | start 为往前 90 天，end 当前日期 |
        
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回交易成交列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   交易成交列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_side | [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815) | 交易方向 |
        | deal\_id | str | 成交号 |
        | order\_id | str | 订单号 |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | deal\_market | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 成交标的所属市场 |
        | qty | float | 成交数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
        | price | float | 成交价格<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超过部分四舍五入 |
        | create\_time | str | 创建时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
        | counter\_broker\_id | int | 对手经纪号<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅港股有效 |
        | counter\_broker\_name | str | 对手经纪名称<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅港股有效 |
        | status | [DealStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8317) | 成交状态 |
        | jp\_acc\_type | [SubAccType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6112) | 日本账户类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅对日本券商生效 |
        
*   **Example**
    

    from moomoo import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    ret, data = trd_ctx.history_deal_list_query()
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果成交列表不为空
            print(data['deal_id'][0])  # 获取历史成交的第一个成交号
            print(data['deal_id'].values.tolist())  # 转为 list
    else:
        print('history_deal_list_query error: ', data)
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

        code stock_name         deal_market        deal_id             order_id    qty  price trd_side              create_time  counter_broker_id counter_broker_name status jp_acc_type
    0  US.AAPL       苹果      US   5056208452274069375  4665291631090960915  100.0  370.0      BUY  2020-09-17 21:15:59.979                  5                         OK        N/A
    5056208452274069375
    ['5056208452274069375']
    

1  
2  
3  
4  

[#](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html#7585-2)
 Trd\_GetHistoryOrderFillList.proto
------------------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询指定交易业务账户的历史成交列表。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.OrderFill orderFillList = 2; //历史成交列表
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
> *   成交结构，参见 [OrderFill](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1253)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2222
    

`uint GetHistoryOrderFillList(TrdGetHistoryOrderFillList.Request req);`  
`virtual void OnReply_GetHistoryOrderFillList(MMAPI_Conn client, uint nSerialNo, TrdGetHistoryOrderFillList.Response rsp);`

*   **介绍**
    
    查询指定交易业务账户的历史成交列表。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.OrderFill orderFillList = 2; //历史成交列表
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
> *   成交结构，参见 [OrderFill](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1253)
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
            TrdCommon.TrdFilterConditions filter = TrdCommon.TrdFilterConditions.CreateBuilder().Build();
            TrdGetHistoryOrderFillList.C2S c2s = TrdGetHistoryOrderFillList.C2S.CreateBuilder()
                    .SetHeader(header)
                    .SetFilterConditions(filter)
                    .Build();
            TrdGetHistoryOrderFillList.Request req = TrdGetHistoryOrderFillList.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetHistoryOrderFillList(req);
            Console.Write("Send TrdGetHistoryOrderFillList: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetHistoryOrderFillList(MMAPI_Conn client, uint nSerialNo, TrdGetHistoryOrderFillListResponse rsp)
        {
            Console.Write("Reply: TrdGetHistoryOrderFillList: {0}\n", nSerialNo);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6827861023441251037
    Send TrdGetHistoryOrderFillList: 3
    Reply: TrdGetHistoryOrderFillList: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int getHistoryOrderFillList(TrdGetHistoryOrderFillList.Request req);`  
`void onReply_GetHistoryOrderFillList(MMAPI_Conn client, int nSerialNo, TrdGetHistoryOrderFillList.Response rsp);`

*   **介绍**
    
    查询指定交易业务账户的历史成交列表。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.OrderFill orderFillList = 2; //历史成交列表
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
> *   成交结构，参见 [OrderFill](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1253)
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
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_US_VALUE)
                    .build();
            TrdCommon.TrdFilterConditions filter = TrdCommon.TrdFilterConditions.newBuilder()
                    .setBeginTime("2021-03-01 00:00:00")
                    .setEndTime("2021-04-01 00:00:00")
                    .build();
            TrdGetHistoryOrderFillList.C2S c2s = TrdGetHistoryOrderFillList.C2S.newBuilder()
                    .setHeader(header)
                    .setFilterConditions(filter)
                    .build();
            TrdGetHistoryOrderFillList.Request req = TrdGetHistoryOrderFillList.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getHistoryOrderFillList(req);
            System.out.printf("Send TrdGetHistoryOrderFillList: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetHistoryOrderFillList(MMAPI_Conn client, int nSerialNo, TrdGetHistoryOrderFillList.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetHistoryOrderFillList failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetHistoryOrderFillList: %s\n", json);
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

*   **Output**

    Send TrdGetHistoryOrderFillList: 2
    Receive TrdGetHistoryOrderFillList: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 2
        },
        "orderFillList": [{\
          "trdSide": 1,\
          "fillID": "449150869556176742",\
          "fillIDEx": "20210330_15680495_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1.4",\
          "orderID": "6664320708369556828",\
          "orderIDEx": "20210330_15680495_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1",\
          "code": "FUTU",\
          "name": "富途控股",\
          "qty": 34.0,\
          "price": 127.64,\
          "createTime": "2021-03-30 09:34:24.019",\
          "secMarket": 2,\
          "createTimestamp": 1.617111264019109E9,\
          "updateTimestamp": 1.617111264019109E9,\
          "status": 0\
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

`moomoo::u32_t GetHistoryOrderFillList(const Trd_GetHistoryOrderFillList::Request &stReq);`  
`virtual void OnReply_GetHistoryOrderFillList(moomoo::u32_t nSerialNo, const Trd_GetHistoryOrderFillList::Response &stRsp) = 0;`

*   **介绍**
    
    查询指定交易业务账户的历史成交列表。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.OrderFill orderFillList = 2; //历史成交列表
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
> *   成交结构，参见 [OrderFill](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1253)
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
    
    
    	virtual void OnInitConnect( MMAPI_Conn* pConn, moomoo::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		// 组包
    		Trd_GetHistoryOrderFillList::Request req;
    		Trd_GetHistoryOrderFillList::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(281756456003951537L);
    		header->set_trdenv(1);
    		header->set_trdmarket(1);
    		Trd_Common::TrdFilterConditions *filter = c2s->mutable_filterconditions();
    		filter->set_begintime("2021-05-01 00:00:00");
    		filter->set_endtime("2021-06-01 00:00:00");
    
            m_GetHistoryOrderFillListSerialNo = m_pTrdApi->GetHistoryOrderFillList(req);
            cout << "Request GetHistoryOrderFillList SerialNo: " << m_GetHistoryOrderFillListSerialNo << endl;
    	}
    
    	virtual void OnReply_GetHistoryOrderFillList(moomoo::u32_t nSerialNo, const Trd_GetHistoryOrderFillList::Response &stRsp){
            if(nSerialNo == m_GetHistoryOrderFillListSerialNo)
            {
                cout << "OnReply_GetHistoryOrderFillList SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Trd *m_pTrdApi;
    
        moomoo::u32_t m_GetHistoryOrderFillListSerialNo;
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

*   **Output**

    connect
    Request GetHistoryOrderFillList SerialNo: 4
    OnReply_GetHistoryOrderFillList SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "header": {
       "trdEnv": 1,
       "accID": "281756456003951537",
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

`GetHistoryOrderFillList(req);`

*   **介绍**
    
    查询指定交易业务账户的历史成交列表。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
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

> *   交易公共参数头结构，参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构，参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.OrderFill orderFillList = 2; //历史成交列表
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
> *   成交结构，参见 [OrderFill](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1253)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetHistoryOrderFillList(){
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
                            return item.trdEnv != TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})
                        })[0]; // 样例取第一个香港市场真实环境账户
    
                        const req = {
                            c2s: {
                                header: {
                                    trdEnv: acc.trdEnv,
                                    accID: acc.accID,
                                    trdMarket: TrdMarket.TrdMarket_HK,
                                },
                                filterConditions:{
                                    beginTime:"2021-09-01 00:00:00",
                                    endTime:"2021-09-30 00:00:00",
                                },
                            },
                        };
    
                        websocket.GetHistoryOrderFillList(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetHistoryOrderFillList: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
71  
72  
73  
74  

*   **Output**

    GetHistoryOrderFillList: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756455988249902",
        "trdMarket": 1
      },
      "orderFillList": [{\
        "trdSide": 1,\
        "fillID": "932511865781776209",\
        "fillIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1.2",\
        "orderID": "4883217202603317248",\
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",\
        "code": "00700",\
        "name": "",\
        "qty": 100,\
        "price": 480,\
        "createTime": "2021-09-13 16:45:00.606",\
        "counterBrokerID": 5,\
        "counterBrokerName": "",\
        "secMarket": 1,\
        "createTimestamp": 1631522700.605828,\
        "updateTimestamp": 1631522700.531,\
        "status": 0\
      }, {\
        "trdSide": 1,\
        "fillID": "2611798069690910040",\
        "fillIDEx": "20210913_5915950_OD|rILqM3WaKl2rXYpRYuigcJmBKtRld_OD|rILqM3WaKl2rXYpRYuigcJmBKtRld.2",\
        "orderID": "9128832000130556480",\
        "orderIDEx": "20210913_5915950_OD|rILqM3WaKl2rXYpRYuigcJmBKtRld",\
        "code": "00700",\
        "name": "",\
        "qty": 100,\
        "price": 480,\
        "createTime": "2021-09-13 16:40:19.183",\
        "counterBrokerID": 5,\
        "counterBrokerName": "",\
        "secMarket": 1,\
        "createTimestamp": 1631522419.18269,\
        "updateTimestamp": 1631522419.005,\
        "status": 0\
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

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询历史成交接口

提示

*   历史成交，按照时间的“倒序”进行排列，即：后成交的记录在前，先成交的记录在后

← [查询当日成交](https://openapi.futunn.com/futu-api-doc/trade/get-order-fill-list.html) [响应成交推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html)
 →

[查询历史成交](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html)