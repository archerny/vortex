[#](./quote_set-price-reminder.md#9746)
 设置到价提醒
=======================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`set_price_reminder(code, op, key=None, reminder_type=None, reminder_freq=None, value=None, note=None, reminder_session_list=NONE)`

*   **介绍**
    
    新增、删除、修改、启用、禁用指定股票的到价提醒
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票代码 |
    | op  | [SetPriceReminderOp](./quote_quote.md#433) | 操作类型 |
    | key | int | 标识，新增和删除全部的情况不需要填 |
    | reminder\_type | [PriceReminderType](./quote_quote.md#5160) | 到价提醒的类型，删除、启用、禁用的情况下会忽略该入参 |
    | reminder\_freq | [PriceReminderFreq](./quote_quote.md#1059) | 到价提醒的频率，删除、启用、禁用的情况下会忽略该入参 |
    | value | float | 提醒值，删除、启用、禁用的情况下会忽略该入参<br>(ℹ️ 精确到小数点后 3 位，超出部分会被舍弃) |
    | note | str | 用户设置的备注，仅支持 20 个以内的中文字符，删除、启用、禁用的情况下会忽略该入参 |
    | reminder\_session\_list | list | 美股到价提醒的时段列表，删除、启用、禁用的情况下会忽略该入参<br>(ℹ️ *   list中元素类型是[PriceReminderMarketStatus](./quote_quote.md#482))<br>    <br>*   美股默认到价提醒时段：盘中+盘前盘后 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | key | int | 当 ret == RET\_OK 时，返回操作的到价提醒 key |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
*   **Example**
    

    from futu import *
    import time
    class PriceReminderTest(PriceReminderHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, content = super(PriceReminderTest,self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("PriceReminderTest: error, msg: %s" % content)
                return RET_ERROR, content
            print("PriceReminderTest ", content) # PriceReminderTest 自己的处理逻辑
            return RET_OK, content
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = PriceReminderTest()
    quote_ctx.set_handler(handler)
    ret, data = quote_ctx.get_market_snapshot(['US.AAPL'])
    if ret == RET_OK:
        bid_price = data['bid_price'][0]  # 获取实时买一价
        ask_price = data['ask_price'][0]  # 获取实时卖一价
        # 设置当AAPL全时段卖一价低于（ask_price-1）时提醒
        ret_ask, ask_data = quote_ctx.set_price_reminder(code='US.AAPL', op=SetPriceReminderOp.ADD, key=None, reminder_type=PriceReminderType.ASK_PRICE_DOWN, reminder_freq=PriceReminderFreq.ALWAYS, value=(ask_price-1), note='123', reminder_session_list=[PriceReminderMarketStatus.US_PRE, PriceReminderMarketStatus.OPEN, PriceReminderMarketStatus.US_AFTER, PriceReminderMarketStatus.US_OVERNIGHT])
        if ret_ask == RET_OK:
            print('卖一价低于（ask_price-1）时提醒设置成功：', ask_data)
        else:
            print('error:', ask_data)
        # 设置当AAPL全时段买一价高于（bid_price+1）时提醒
        ret_bid, bid_data = quote_ctx.set_price_reminder(code='US.AAPL', op=SetPriceReminderOp.ADD, key=None, reminder_type=PriceReminderType.BID_PRICE_UP, reminder_freq=PriceReminderFreq.ALWAYS, value=(bid_price+1), note='456', reminder_session_list=[PriceReminderMarketStatus.US_PRE, PriceReminderMarketStatus.OPEN, PriceReminderMarketStatus.US_AFTER, PriceReminderMarketStatus.US_OVERNIGHT])
        if ret_bid == RET_OK:
            print('买一价高于（bid_price+1）时提醒设置成功：', bid_data)
        else:
            print('error:', bid_data)
    time.sleep(15)
    quote_ctx.close()
    

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

*   **Output**

    卖一价低于（ask_price-1）时提醒设置成功： 1744022257023211123
    买一价高于（bid_price+1）时提醒设置成功： 1744022257052794489
    

1  
2  

[#](./quote_set-price-reminder.md#2552)
 Qot\_SetPriceReminder.proto
------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    新增、删除、修改、启用、禁用指定股票的到价提醒
    
*   **参数**
    

    enum SetPriceReminderOp
    {
    	SetPriceReminderOp_Unknown = 0;
    	SetPriceReminderOp_Add = 1; //新增
    	SetPriceReminderOp_Del = 2; //删除
    	SetPriceReminderOp_Enable = 3; //启用
    	SetPriceReminderOp_Disable = 4; //禁用
    	SetPriceReminderOp_Modify = 5; //修改
    	SetPriceReminderOp_DelAll = 6; //删除该支股票下所有到价提醒
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; // 股票
    	required int32 op = 2; // SetPriceReminderOp，操作类型
    	optional int64 key = 3; // 到价提醒的标识，GetPriceReminder 协议可获得，用于指定要操作的到价提醒项，对于新增的情况不需要填
    	optional int32 type = 4; // Qot_Common::PriceReminderType，提醒类型，删除、启用、禁用的情况下会忽略该字段
    	optional int32 freq = 7; // Qot_Common::PriceReminderFreq，提醒频率类型，删除、启用、禁用的情况下会忽略该字段
    	optional double value = 5; // 提醒值，删除、启用、禁用的情况下会忽略该字段（精确到小数点后 3 位，超出部分会被舍弃）
    	optional string note = 6; // 用户设置到价提醒时的标注，仅支持 20 个以内的中文字符，删除、启用、禁用的情况下会忽略该字段
        repeated int32 reminderSessionList = 8; // 到价提醒的时段列表，删除、启用、禁用的情况下会忽略该入参,枚举参考Qot_Common::PriceReminderMarketStatus
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](./quote_quote.md#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](./quote_quote.md#1059)
>     

*   **返回**

    message S2C
    {
    	required int64 key = 1; //设置成功的情况下返回对应的 key，不成功返回0
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; //RetType，返回结果
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3220
    

`uint SetPriceReminder(QotSetPriceReminder.Request req);`  
`virtual void OnReply_SetPriceReminder(FTAPI_Conn client, uint nSerialNo, QotSetPriceReminder.Response rsp);`

*   **介绍**
    
    新增、删除、修改、启用、禁用指定股票的到价提醒
    
*   **参数**
    

    enum SetPriceReminderOp
    {
    	SetPriceReminderOp_Unknown = 0;
    	SetPriceReminderOp_Add = 1; //新增
    	SetPriceReminderOp_Del = 2; //删除
    	SetPriceReminderOp_Enable = 3; //启用
    	SetPriceReminderOp_Disable = 4; //禁用
    	SetPriceReminderOp_Modify = 5; //修改
    	SetPriceReminderOp_DelAll = 6; //删除该支股票下所有到价提醒
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; // 股票
    	required int32 op = 2; // SetPriceReminderOp，操作类型
    	optional int64 key = 3; // 到价提醒的标识，GetPriceReminder 协议可获得，用于指定要操作的到价提醒项，对于新增的情况不需要填
    	optional int32 type = 4; // Qot_Common::PriceReminderType，提醒类型，删除、启用、禁用的情况下会忽略该字段
    	optional int32 freq = 7; // Qot_Common::PriceReminderFreq，提醒频率类型，删除、启用、禁用的情况下会忽略该字段
    	optional double value = 5; // 提醒值，删除、启用、禁用的情况下会忽略该字段（精确到小数点后 3 位，超出部分会被舍弃）
    	optional string note = 6; // 用户设置到价提醒时的标注，仅支持 20 个以内的中文字符，删除、启用、禁用的情况下会忽略该字段
        repeated int32 reminderSessionList = 8; // 到价提醒的时段列表，删除、启用、禁用的情况下会忽略该入参,枚举参考Qot_Common::PriceReminderMarketStatus
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](./quote_quote.md#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](./quote_quote.md#1059)
>     

*   **返回**

    message S2C
    {
    	required int64 key = 1; //设置成功的情况下返回对应的 key，不成功返回0
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; //RetType，返回结果
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
            QotSetPriceReminder.C2S c2s = QotSetPriceReminder.C2S.CreateBuilder()
                    .SetSecurity(sec)
                    .SetOp(QotSetPriceReminder.SetPriceReminderOp.SetPriceReminderOp_Add)
                    .SetType((int)QotCommon.PriceReminderType.PriceReminderType_PriceUp)
                    .SetFreq((int)QotCommon.PriceReminderFreq.PriceReminderFreq_OnlyOnce)
                    .SetValue(5)
                .Build();
            QotSetPriceReminder.Request req = QotSetPriceReminder.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.SetPriceReminder(req);
            Console.Write("Send QotSetPriceReminder: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_SetPriceReminder(FTAPI_Conn client, uint nSerialNo, QotSetPriceReminder.Response rsp)
        {
            Console.Write("Reply: QotSetPriceReminder: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("key: {0} \n", rsp.S2C.Key);
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
55  
56  
57  

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826782725947038940
    Send QotSetPriceReminder: 3
    Reply: QotSetPriceReminder: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      key: 162763183755472101
    }
    
    key: 162763183755472101
    

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

`int setPriceReminder(QotSetPriceReminder.Request req);`  
`void onReply_SetPriceReminder(FTAPI_Conn client, int nSerialNo, QotSetPriceReminder.Response rsp);`

*   **介绍**
    
    新增、删除、修改、启用、禁用指定股票的到价提醒
    
*   **参数**
    

    enum SetPriceReminderOp
    {
    	SetPriceReminderOp_Unknown = 0;
    	SetPriceReminderOp_Add = 1; //新增
    	SetPriceReminderOp_Del = 2; //删除
    	SetPriceReminderOp_Enable = 3; //启用
    	SetPriceReminderOp_Disable = 4; //禁用
    	SetPriceReminderOp_Modify = 5; //修改
    	SetPriceReminderOp_DelAll = 6; //删除该支股票下所有到价提醒
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; // 股票
    	required int32 op = 2; // SetPriceReminderOp，操作类型
    	optional int64 key = 3; // 到价提醒的标识，GetPriceReminder 协议可获得，用于指定要操作的到价提醒项，对于新增的情况不需要填
    	optional int32 type = 4; // Qot_Common::PriceReminderType，提醒类型，删除、启用、禁用的情况下会忽略该字段
    	optional int32 freq = 7; // Qot_Common::PriceReminderFreq，提醒频率类型，删除、启用、禁用的情况下会忽略该字段
    	optional double value = 5; // 提醒值，删除、启用、禁用的情况下会忽略该字段（精确到小数点后 3 位，超出部分会被舍弃）
    	optional string note = 6; // 用户设置到价提醒时的标注，仅支持 20 个以内的中文字符，删除、启用、禁用的情况下会忽略该字段
        repeated int32 reminderSessionList = 8; // 到价提醒的时段列表，删除、启用、禁用的情况下会忽略该入参,枚举参考Qot_Common::PriceReminderMarketStatus
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](./quote_quote.md#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](./quote_quote.md#1059)
>     

*   **返回**

    message S2C
    {
    	required int64 key = 1; //设置成功的情况下返回对应的 key，不成功返回0
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; //RetType，返回结果
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
            QotSetPriceReminder.C2S c2s = QotSetPriceReminder.C2S.newBuilder()
                    .setSecurity(sec)
                    .setOp(QotSetPriceReminder.SetPriceReminderOp.SetPriceReminderOp_Add_VALUE)
                    .setType(QotCommon.PriceReminderType.PriceReminderType_PriceUp_VALUE)
                    .setFreq(QotCommon.PriceReminderFreq.PriceReminderFreq_OnlyOnce_VALUE)
                    .setValue(5)
                .build();
            QotSetPriceReminder.Request req = QotSetPriceReminder.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.setPriceReminder(req);
            System.out.printf("Send QotSetPriceReminder: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_SetPriceReminder(FTAPI_Conn client, int nSerialNo, QotSetPriceReminder.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotSetPriceReminder failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotSetPriceReminder: %s\n", json);
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
69  
70  

*   **Output**

    Send QotSetPriceReminder: 2
    Receive QotSetPriceReminder: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "key": "162452649832682701"
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

`Futu::u32_t SetPriceReminder(const Qot_SetPriceReminder::Request &stReq);`  
`virtual void OnReply_SetPriceReminder(Futu::u32_t nSerialNo, const Qot_SetPriceReminder::Response &stRsp) = 0;`

*   **介绍**
    
    新增、删除、修改、启用、禁用指定股票的到价提醒
    
*   **参数**
    

    enum SetPriceReminderOp
    {
    	SetPriceReminderOp_Unknown = 0;
    	SetPriceReminderOp_Add = 1; //新增
    	SetPriceReminderOp_Del = 2; //删除
    	SetPriceReminderOp_Enable = 3; //启用
    	SetPriceReminderOp_Disable = 4; //禁用
    	SetPriceReminderOp_Modify = 5; //修改
    	SetPriceReminderOp_DelAll = 6; //删除该支股票下所有到价提醒
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; // 股票
    	required int32 op = 2; // SetPriceReminderOp，操作类型
    	optional int64 key = 3; // 到价提醒的标识，GetPriceReminder 协议可获得，用于指定要操作的到价提醒项，对于新增的情况不需要填
    	optional int32 type = 4; // Qot_Common::PriceReminderType，提醒类型，删除、启用、禁用的情况下会忽略该字段
    	optional int32 freq = 7; // Qot_Common::PriceReminderFreq，提醒频率类型，删除、启用、禁用的情况下会忽略该字段
    	optional double value = 5; // 提醒值，删除、启用、禁用的情况下会忽略该字段（精确到小数点后 3 位，超出部分会被舍弃）
    	optional string note = 6; // 用户设置到价提醒时的标注，仅支持 20 个以内的中文字符，删除、启用、禁用的情况下会忽略该字段
        repeated int32 reminderSessionList = 8; // 到价提醒的时段列表，删除、启用、禁用的情况下会忽略该入参,枚举参考Qot_Common::PriceReminderMarketStatus
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](./quote_quote.md#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](./quote_quote.md#1059)
>     

*   **返回**

    message S2C
    {
    	required int64 key = 1; //设置成功的情况下返回对应的 key，不成功返回0
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; //RetType，返回结果
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
    		// 组包
    		Qot_SetPriceReminder::Request req;
    		Qot_SetPriceReminder::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->set_op(1);
    		c2s->set_type(Qot_Common::PriceReminderType::PriceReminderType_PriceUp);
    		c2s->set_freq(Qot_Common::PriceReminderFreq::PriceReminderFreq_OnlyOnce);
    		c2s->set_value(5);
            
            m_SetPriceReminderSerialNo = m_pQotApi->SetPriceReminder(req);
            cout << "Request SetPriceReminder SerialNo: " << m_SetPriceReminderSerialNo << endl;
    	}
    
    	virtual void OnReply_SetPriceReminder(Futu::u32_t nSerialNo, const Qot_SetPriceReminder::Response &stRsp){
            if(nSerialNo == m_SetPriceReminderSerialNo)
            {
                cout << "OnReply_SetPriceReminder SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
        
        Futu::u32_t m_SetPriceReminderSerialNo;
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
    Request SetPriceReminder SerialNo: 3
    OnReply_SetPriceReminder SerialNo: 3
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "key": "162320791658522901"
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

`SetPriceReminder(req);`

*   **介绍**
    
    新增、删除、修改、启用、禁用指定股票的到价提醒
    
*   **参数**
    

    enum SetPriceReminderOp
    {
    	SetPriceReminderOp_Unknown = 0;
    	SetPriceReminderOp_Add = 1; //新增
    	SetPriceReminderOp_Del = 2; //删除
    	SetPriceReminderOp_Enable = 3; //启用
    	SetPriceReminderOp_Disable = 4; //禁用
    	SetPriceReminderOp_Modify = 5; //修改
    	SetPriceReminderOp_DelAll = 6; //删除该支股票下所有到价提醒
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; // 股票
    	required int32 op = 2; // SetPriceReminderOp，操作类型
    	optional int64 key = 3; // 到价提醒的标识，GetPriceReminder 协议可获得，用于指定要操作的到价提醒项，对于新增的情况不需要填
    	optional int32 type = 4; // Qot_Common::PriceReminderType，提醒类型，删除、启用、禁用的情况下会忽略该字段
    	optional int32 freq = 7; // Qot_Common::PriceReminderFreq，提醒频率类型，删除、启用、禁用的情况下会忽略该字段
    	optional double value = 5; // 提醒值，删除、启用、禁用的情况下会忽略该字段（精确到小数点后 3 位，超出部分会被舍弃）
    	optional string note = 6; // 用户设置到价提醒时的标注，仅支持 20 个以内的中文字符，删除、启用、禁用的情况下会忽略该字段
        repeated int32 reminderSessionList = 8; // 到价提醒的时段列表，删除、启用、禁用的情况下会忽略该入参,枚举参考Qot_Common::PriceReminderMarketStatus
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](./quote_quote.md#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](./quote_quote.md#1059)
>     

*   **返回**

    message S2C
    {
    	required int64 key = 1; //设置成功的情况下返回对应的 key，不成功返回0
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; //RetType，返回结果
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Qot_SetPriceReminder } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotSetPriceReminder(){
        const { RetType } = Common
        const { PriceReminderType, PriceReminderFreq, QotMarket } = Qot_Common
        const { SetPriceReminderOp } = Qot_SetPriceReminder
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        security: {
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                        op: SetPriceReminderOp.SetPriceReminderOp_Add,
                        type: PriceReminderType.PriceReminderType_PriceUp,
                        freq: PriceReminderFreq.PriceReminderFreq_Always,
                        value: 600,
                    },
                };
                websocket.SetPriceReminder(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("PriceReminder: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    PriceReminder: errCode 0, retMsg , retType 0
    {
      "key": "163126377342664201"
    }
    stop
    

1  
2  
3  
4  
5  

提示

*   API 中成交量设置统一以股为单位。但是牛牛客户端中，A 股是以手为单位展示
    
*   到价提醒类型，存在最小精度，如下：
    
    TURNOVER\_UP：成交额最小精度为 10 元（人民币元，港元，美元）。传入的数值会自动向下取整到最小精度的整数倍。如果设置【00700成交额102元提醒】，设置后会得到【00700成交额100元提醒】；如果设置【00700 成交额 8 元提醒】，设置后会得到【00700 成交额 0 元提醒】。
    
    VOLUME\_UP：A 股成交量最小精度为 1000 股，其他市场股票成交量最小精度为 10 股。传入的数值会自动向下取整到最小精度的整数倍。
    
    BID\_VOL\_UP、ASK\_VOL\_UP：A 股的买一卖一量最小精度为 100 股。传入的数值会自动向下取整到最小精度的整数倍。
    
    其余到价提醒类型精度支持到小数点后 3 位
    

接口限制

*   每 30 秒内最多请求 60 次设置到价提醒接口
*   每只股票每种类型可设置的提醒上限是 10 个

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`set_price_reminder(code, op, key=None, reminder_type=None, reminder_freq=None, value=None, note=None)`

*   **介绍**
    
    新增、删除、修改、启用、禁用指定股票的到价提醒
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票代码 |
    | op  | [SetPriceReminderOp](./quote_quote.md#433) | 操作类型 |
    | key | int | 标识，新增和删除全部的情况不需要填 |
    | reminder\_type | [PriceReminderType](./quote_quote.md#5160) | 到价提醒的类型，删除、启用、禁用的情况下会忽略该入参 |
    | reminder\_freq | [PriceReminderFreq](./quote_quote.md#1059) | 到价提醒的频率，删除、启用、禁用的情况下会忽略该入参 |
    | value | float | 提醒值，删除、启用、禁用的情况下会忽略该入参<br>(ℹ️ 精确到小数点后 3 位，超出部分会被舍弃) |
    | note | str | 用户设置的备注，仅支持 20 个以内的中文字符，删除、启用、禁用的情况下会忽略该入参 |
    | reminder\_session\_list | list | 美股到价提醒的时段列表，删除、启用、禁用的情况下会忽略该入参<br>(ℹ️ *   list中元素类型是[PriceReminderMarketStatus](./quote_quote.md#482))<br>    <br>*   美股默认到价提醒时段：盘中+盘前盘后 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | key | int | 当 ret == RET\_OK 时，返回操作的到价提醒 key |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
*   **Example**
    

    from moomoo import *
    import time
    class PriceReminderTest(PriceReminderHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, content = super(PriceReminderTest,self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("PriceReminderTest: error, msg: %s" % content)
                return RET_ERROR, content
            print("PriceReminderTest ", content) # PriceReminderTest 自己的处理逻辑
            return RET_OK, content
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = PriceReminderTest()
    quote_ctx.set_handler(handler)
    ret, data = quote_ctx.get_market_snapshot(['US.AAPL'])
    if ret == RET_OK:
        bid_price = data['bid_price'][0]  # 获取实时买一价
        ask_price = data['ask_price'][0]  # 获取实时卖一价
        # 设置当AAPL全时段卖一价低于（ask_price-1）时提醒
        ret_ask, ask_data = quote_ctx.set_price_reminder(code='US.AAPL', op=SetPriceReminderOp.ADD, key=None, reminder_type=PriceReminderType.ASK_PRICE_DOWN, reminder_freq=PriceReminderFreq.ALWAYS, value=(ask_price-1), note='123', reminder_session_list=[PriceReminderMarketStatus.US_PRE, PriceReminderMarketStatus.OPEN, PriceReminderMarketStatus.US_AFTER, PriceReminderMarketStatus.US_OVERNIGHT])
        if ret_ask == RET_OK:
            print('卖一价低于（ask_price-1）时提醒设置成功：', ask_data)
        else:
            print('error:', ask_data)
        # 设置当AAPL全时段买一价高于（bid_price+1）时提醒
        ret_bid, bid_data = quote_ctx.set_price_reminder(code='US.AAPL', op=SetPriceReminderOp.ADD, key=None, reminder_type=PriceReminderType.BID_PRICE_UP, reminder_freq=PriceReminderFreq.ALWAYS, value=(bid_price+1), note='456', reminder_session_list=[PriceReminderMarketStatus.US_PRE, PriceReminderMarketStatus.OPEN, PriceReminderMarketStatus.US_AFTER, PriceReminderMarketStatus.US_OVERNIGHT])
        if ret_bid == RET_OK:
            print('买一价高于（bid_price+1）时提醒设置成功：', bid_data)
        else:
            print('error:', bid_data)
    time.sleep(15)
    quote_ctx.close()
    

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

*   **Output**

    卖一价低于（ask_price-1）时提醒设置成功： 1744022257023211123
    买一价高于（bid_price+1）时提醒设置成功： 1744022257052794489
    

1  
2  

[#](./quote_set-price-reminder.md#2552-2)
 Qot\_SetPriceReminder.proto
--------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    新增、删除、修改、启用、禁用指定股票的到价提醒
    
*   **参数**
    

    enum SetPriceReminderOp
    {
    	SetPriceReminderOp_Unknown = 0;
    	SetPriceReminderOp_Add = 1; //新增
    	SetPriceReminderOp_Del = 2; //删除
    	SetPriceReminderOp_Enable = 3; //启用
    	SetPriceReminderOp_Disable = 4; //禁用
    	SetPriceReminderOp_Modify = 5; //修改
    	SetPriceReminderOp_DelAll = 6; //删除该支股票下所有到价提醒
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; // 股票
    	required int32 op = 2; // SetPriceReminderOp，操作类型
    	optional int64 key = 3; // 到价提醒的标识，GetPriceReminder 协议可获得，用于指定要操作的到价提醒项，对于新增的情况不需要填
    	optional int32 type = 4; // Qot_Common::PriceReminderType，提醒类型，删除、启用、禁用的情况下会忽略该字段
    	optional int32 freq = 7; // Qot_Common::PriceReminderFreq，提醒频率类型，删除、启用、禁用的情况下会忽略该字段
    	optional double value = 5; // 提醒值，删除、启用、禁用的情况下会忽略该字段（精确到小数点后 3 位，超出部分会被舍弃）
    	optional string note = 6; // 用户设置到价提醒时的标注，仅支持 20 个以内的中文字符，删除、启用、禁用的情况下会忽略该字段
        repeated int32 reminderSessionList = 8; // 到价提醒的时段列表，删除、启用、禁用的情况下会忽略该入参,枚举参考Qot_Common::PriceReminderMarketStatus
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](./quote_quote.md#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](./quote_quote.md#1059)
>     

*   **返回**

    message S2C
    {
    	required int64 key = 1; //设置成功的情况下返回对应的 key，不成功返回0
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; //RetType，返回结果
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3220
    

`uint SetPriceReminder(QotSetPriceReminder.Request req);`  
`virtual void OnReply_SetPriceReminder(MMAPI_Conn client, uint nSerialNo, QotSetPriceReminder.Response rsp);`

*   **介绍**
    
    新增、删除、修改、启用、禁用指定股票的到价提醒
    
*   **参数**
    

    enum SetPriceReminderOp
    {
    	SetPriceReminderOp_Unknown = 0;
    	SetPriceReminderOp_Add = 1; //新增
    	SetPriceReminderOp_Del = 2; //删除
    	SetPriceReminderOp_Enable = 3; //启用
    	SetPriceReminderOp_Disable = 4; //禁用
    	SetPriceReminderOp_Modify = 5; //修改
    	SetPriceReminderOp_DelAll = 6; //删除该支股票下所有到价提醒
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; // 股票
    	required int32 op = 2; // SetPriceReminderOp，操作类型
    	optional int64 key = 3; // 到价提醒的标识，GetPriceReminder 协议可获得，用于指定要操作的到价提醒项，对于新增的情况不需要填
    	optional int32 type = 4; // Qot_Common::PriceReminderType，提醒类型，删除、启用、禁用的情况下会忽略该字段
    	optional int32 freq = 7; // Qot_Common::PriceReminderFreq，提醒频率类型，删除、启用、禁用的情况下会忽略该字段
    	optional double value = 5; // 提醒值，删除、启用、禁用的情况下会忽略该字段（精确到小数点后 3 位，超出部分会被舍弃）
    	optional string note = 6; // 用户设置到价提醒时的标注，仅支持 20 个以内的中文字符，删除、启用、禁用的情况下会忽略该字段
        repeated int32 reminderSessionList = 8; // 到价提醒的时段列表，删除、启用、禁用的情况下会忽略该入参,枚举参考Qot_Common::PriceReminderMarketStatus
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](./quote_quote.md#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](./quote_quote.md#1059)
>     

*   **返回**

    message S2C
    {
    	required int64 key = 1; //设置成功的情况下返回对应的 key，不成功返回0
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; //RetType，返回结果
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
            QotSetPriceReminder.C2S c2s = QotSetPriceReminder.C2S.CreateBuilder()
                    .SetSecurity(sec)
                    .SetOp(QotSetPriceReminder.SetPriceReminderOp.SetPriceReminderOp_Add)
                    .SetType((int)QotCommon.PriceReminderType.PriceReminderType_PriceUp)
                    .SetFreq((int)QotCommon.PriceReminderFreq.PriceReminderFreq_OnlyOnce)
                    .SetValue(5)
                .Build();
            QotSetPriceReminder.Request req = QotSetPriceReminder.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.SetPriceReminder(req);
            Console.Write("Send QotSetPriceReminder: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_SetPriceReminder(MMAPI_Conn client, uint nSerialNo, QotSetPriceReminder.Response rsp)
        {
            Console.Write("Reply: QotSetPriceReminder: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("key: {0} \n", rsp.S2C.Key);
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
55  
56  
57  

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826782725947038940
    Send QotSetPriceReminder: 3
    Reply: QotSetPriceReminder: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      key: 162763183755472101
    }
    
    key: 162763183755472101
    

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

`int setPriceReminder(QotSetPriceReminder.Request req);`  
`void onReply_SetPriceReminder(MMAPI_Conn client, int nSerialNo, QotSetPriceReminder.Response rsp);`

*   **介绍**
    
    新增、删除、修改、启用、禁用指定股票的到价提醒
    
*   **参数**
    

    enum SetPriceReminderOp
    {
    	SetPriceReminderOp_Unknown = 0;
    	SetPriceReminderOp_Add = 1; //新增
    	SetPriceReminderOp_Del = 2; //删除
    	SetPriceReminderOp_Enable = 3; //启用
    	SetPriceReminderOp_Disable = 4; //禁用
    	SetPriceReminderOp_Modify = 5; //修改
    	SetPriceReminderOp_DelAll = 6; //删除该支股票下所有到价提醒
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; // 股票
    	required int32 op = 2; // SetPriceReminderOp，操作类型
    	optional int64 key = 3; // 到价提醒的标识，GetPriceReminder 协议可获得，用于指定要操作的到价提醒项，对于新增的情况不需要填
    	optional int32 type = 4; // Qot_Common::PriceReminderType，提醒类型，删除、启用、禁用的情况下会忽略该字段
    	optional int32 freq = 7; // Qot_Common::PriceReminderFreq，提醒频率类型，删除、启用、禁用的情况下会忽略该字段
    	optional double value = 5; // 提醒值，删除、启用、禁用的情况下会忽略该字段（精确到小数点后 3 位，超出部分会被舍弃）
    	optional string note = 6; // 用户设置到价提醒时的标注，仅支持 20 个以内的中文字符，删除、启用、禁用的情况下会忽略该字段
        repeated int32 reminderSessionList = 8; // 到价提醒的时段列表，删除、启用、禁用的情况下会忽略该入参,枚举参考Qot_Common::PriceReminderMarketStatus
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](./quote_quote.md#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](./quote_quote.md#1059)
>     

*   **返回**

    message S2C
    {
    	required int64 key = 1; //设置成功的情况下返回对应的 key，不成功返回0
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; //RetType，返回结果
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
            QotSetPriceReminder.C2S c2s = QotSetPriceReminder.C2S.newBuilder()
                    .setSecurity(sec)
                    .setOp(QotSetPriceReminder.SetPriceReminderOp.SetPriceReminderOp_Add_VALUE)
                    .setType(QotCommon.PriceReminderType.PriceReminderType_PriceUp_VALUE)
                    .setFreq(QotCommon.PriceReminderFreq.PriceReminderFreq_OnlyOnce_VALUE)
                    .setValue(5)
                .build();
            QotSetPriceReminder.Request req = QotSetPriceReminder.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.setPriceReminder(req);
            System.out.printf("Send QotSetPriceReminder: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_SetPriceReminder(MMAPI_Conn client, int nSerialNo, QotSetPriceReminder.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotSetPriceReminder failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotSetPriceReminder: %s\n", json);
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
69  
70  

*   **Output**

    Send QotSetPriceReminder: 2
    Receive QotSetPriceReminder: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "key": "162452649832682701"
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

`moomoo::u32_t SetPriceReminder(const Qot_SetPriceReminder::Request &stReq);`  
`virtual void OnReply_SetPriceReminder(moomoo::u32_t nSerialNo, const Qot_SetPriceReminder::Response &stRsp) = 0;`

*   **介绍**
    
    新增、删除、修改、启用、禁用指定股票的到价提醒
    
*   **参数**
    

    enum SetPriceReminderOp
    {
    	SetPriceReminderOp_Unknown = 0;
    	SetPriceReminderOp_Add = 1; //新增
    	SetPriceReminderOp_Del = 2; //删除
    	SetPriceReminderOp_Enable = 3; //启用
    	SetPriceReminderOp_Disable = 4; //禁用
    	SetPriceReminderOp_Modify = 5; //修改
    	SetPriceReminderOp_DelAll = 6; //删除该支股票下所有到价提醒
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; // 股票
    	required int32 op = 2; // SetPriceReminderOp，操作类型
    	optional int64 key = 3; // 到价提醒的标识，GetPriceReminder 协议可获得，用于指定要操作的到价提醒项，对于新增的情况不需要填
    	optional int32 type = 4; // Qot_Common::PriceReminderType，提醒类型，删除、启用、禁用的情况下会忽略该字段
    	optional int32 freq = 7; // Qot_Common::PriceReminderFreq，提醒频率类型，删除、启用、禁用的情况下会忽略该字段
    	optional double value = 5; // 提醒值，删除、启用、禁用的情况下会忽略该字段（精确到小数点后 3 位，超出部分会被舍弃）
    	optional string note = 6; // 用户设置到价提醒时的标注，仅支持 20 个以内的中文字符，删除、启用、禁用的情况下会忽略该字段
        repeated int32 reminderSessionList = 8; // 到价提醒的时段列表，删除、启用、禁用的情况下会忽略该入参,枚举参考Qot_Common::PriceReminderMarketStatus
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](./quote_quote.md#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](./quote_quote.md#1059)
>     

*   **返回**

    message S2C
    {
    	required int64 key = 1; //设置成功的情况下返回对应的 key，不成功返回0
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; //RetType，返回结果
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
    		// 组包
    		Qot_SetPriceReminder::Request req;
    		Qot_SetPriceReminder::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->set_op(1);
    		c2s->set_type(Qot_Common::PriceReminderType::PriceReminderType_PriceUp);
    		c2s->set_freq(Qot_Common::PriceReminderFreq::PriceReminderFreq_OnlyOnce);
    		c2s->set_value(5);
            
            m_SetPriceReminderSerialNo = m_pQotApi->SetPriceReminder(req);
            cout << "Request SetPriceReminder SerialNo: " << m_SetPriceReminderSerialNo << endl;
    	}
    
    	virtual void OnReply_SetPriceReminder(moomoo::u32_t nSerialNo, const Qot_SetPriceReminder::Response &stRsp){
            if(nSerialNo == m_SetPriceReminderSerialNo)
            {
                cout << "OnReply_SetPriceReminder SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
        
        moomoo::u32_t m_SetPriceReminderSerialNo;
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
    Request SetPriceReminder SerialNo: 3
    OnReply_SetPriceReminder SerialNo: 3
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "key": "162320791658522901"
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

`SetPriceReminder(req);`

*   **介绍**
    
    新增、删除、修改、启用、禁用指定股票的到价提醒
    
*   **参数**
    

    enum SetPriceReminderOp
    {
    	SetPriceReminderOp_Unknown = 0;
    	SetPriceReminderOp_Add = 1; //新增
    	SetPriceReminderOp_Del = 2; //删除
    	SetPriceReminderOp_Enable = 3; //启用
    	SetPriceReminderOp_Disable = 4; //禁用
    	SetPriceReminderOp_Modify = 5; //修改
    	SetPriceReminderOp_DelAll = 6; //删除该支股票下所有到价提醒
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; // 股票
    	required int32 op = 2; // SetPriceReminderOp，操作类型
    	optional int64 key = 3; // 到价提醒的标识，GetPriceReminder 协议可获得，用于指定要操作的到价提醒项，对于新增的情况不需要填
    	optional int32 type = 4; // Qot_Common::PriceReminderType，提醒类型，删除、启用、禁用的情况下会忽略该字段
    	optional int32 freq = 7; // Qot_Common::PriceReminderFreq，提醒频率类型，删除、启用、禁用的情况下会忽略该字段
    	optional double value = 5; // 提醒值，删除、启用、禁用的情况下会忽略该字段（精确到小数点后 3 位，超出部分会被舍弃）
    	optional string note = 6; // 用户设置到价提醒时的标注，仅支持 20 个以内的中文字符，删除、启用、禁用的情况下会忽略该字段
        repeated int32 reminderSessionList = 8; // 到价提醒的时段列表，删除、启用、禁用的情况下会忽略该入参,枚举参考Qot_Common::PriceReminderMarketStatus
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](./quote_quote.md#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](./quote_quote.md#1059)
>     

*   **返回**

    message S2C
    {
    	required int64 key = 1; //设置成功的情况下返回对应的 key，不成功返回0
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; //RetType，返回结果
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Qot_SetPriceReminder } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotSetPriceReminder(){
        const { RetType } = Common
        const { PriceReminderType, PriceReminderFreq, QotMarket } = Qot_Common
        const { SetPriceReminderOp } = Qot_SetPriceReminder
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        security: {
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                        op: SetPriceReminderOp.SetPriceReminderOp_Add,
                        type: PriceReminderType.PriceReminderType_PriceUp,
                        freq: PriceReminderFreq.PriceReminderFreq_Always,
                        value: 600,
                    },
                };
                websocket.SetPriceReminder(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("PriceReminder: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    PriceReminder: errCode 0, retMsg , retType 0
    {
      "key": "163126377342664201"
    }
    stop
    

1  
2  
3  
4  
5  

提示

*   API 中成交量设置统一以股为单位。但是 moomoo 客户端中，A 股是以手为单位展示
    
*   到价提醒类型，存在最小精度，如下：
    
    TURNOVER\_UP：成交额最小精度为 10 元（人民币元，港元，美元）。传入的数值会自动向下取整到最小精度的整数倍。如果设置【00700成交额102元提醒】，设置后会得到【00700成交额100元提醒】；如果设置【00700 成交额 8 元提醒】，设置后会得到【00700 成交额 0 元提醒】。
    
    VOLUME\_UP：A 股成交量最小精度为 1000 股，其他市场股票成交量最小精度为 10 股。传入的数值会自动向下取整到最小精度的整数倍。
    
    BID\_VOL\_UP、ASK\_VOL\_UP：A 股的买一卖一量最小精度为 100 股。传入的数值会自动向下取整到最小精度的整数倍。
    
    其余到价提醒类型精度支持到小数点后 3 位
    

接口限制

*   每 30 秒内最多请求 60 次设置到价提醒接口
*   每只股票每种类型可设置的提醒上限是 10 个

← [获取历史 K 线额度使用明细](./quote_get-history-kl-quota.md) [获取到价提醒列表](./quote_get-price-reminder.md)
 →

[设置到价提醒](./quote_set-price-reminder.md)