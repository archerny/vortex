[#](./trade_get-margin-ratio.md#869)
 获取融资融券数据
======================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_margin_ratio(code_list)`

*   **介绍**
    
    查询股票的融资融券数据。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 股票代码列表<br>(ℹ️ 每次最多可请求 100 个标的)  <br>list 内元素类型为 str |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回融资融券数据 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   融资融券数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | is\_long\_permit | bool | 是否允许融资 |
        | is\_short\_permit | bool | 是否允许融券 |
        | short\_pool\_remain | float | 卖空池剩余<br>(ℹ️ 单位：股) |
        | short\_fee\_rate | float | 融券参考利率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | alert\_long\_ratio | float | 融资预警比率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | alert\_short\_ratio | float | 融券预警比率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | im\_long\_ratio | float | 融资初始保证金率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | im\_short\_ratio | float | 融券初始保证金率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | mcm\_long\_ratio | float | 融资 margin call 保证金率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | mcm\_short\_ratio | float | 融券 margin call 保证金率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | mm\_long\_ratio | float | 融资维持保证金率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | mm\_short\_ratio | float | 融券维持保证金率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.get_margin_ratio(code_list=['HK.00700','HK.09988'])  
    if ret == RET_OK:
        print(data)
        print(data['is_long_permit'][0])  # 取第一条的是否允许融资
        print(data['im_short_ratio'].values.tolist())  # 转为 list
    else:
        print('error:', data)
    trd_ctx.close()  # 结束后记得关闭当条连接，防止连接条数用尽
    

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

*   **Output**

           code  is_long_permit  is_short_permit  short_pool_remain  short_fee_rate  alert_long_ratio  alert_short_ratio  im_long_ratio  im_short_ratio  mcm_long_ratio  mcm_short_ratio  mm_long_ratio  mm_short_ratio
    0  HK.00700            True             True          1826900.0            0.89              33.0               56.0           35.0            60.0            32.0             53.0           25.0            40.0
    1  HK.09988            True             True          1150600.0            0.95              48.0               46.0           50.0            50.0            47.0             43.0           40.0            30.0
    True
    [60.0, 50.0]
    

1  
2  
3  
4  
5  

[#](./trade_get-margin-ratio.md#4388)
 Trd\_GetMarginRatio.proto
--------------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询股票的融资融券数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Qot_Common.Security securityList = 2; //股票
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message MarginRatioInfo
    {
    	required Qot_Common.Security security = 1; //股票
    	optional bool   isLongPermit = 2; //是否允许融资
    	optional bool   isShortPermit = 3; //是否允许融券
    	optional double shortPoolRemain = 4; //卖空池剩余（股）
    	optional double shortFeeRate = 5; //融券参考利率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertLongRatio = 6; //融资预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertShortRatio = 7; //融券预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imLongRatio = 8; //融资初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imShortRatio = 9; //融券初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmLongRatio = 10; //融资 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmShortRatio = 11; //融券 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmLongRatio = 12; //融资维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmShortRatio = 13; //融券维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated MarginRatioInfo marginRatioInfoList = 2; //账户资金
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明 在 InitConnect.proto 中
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    2223
    

`uint GetMarginRatio(TrdGetMarginRatio.Request req);`  
`virtual void OnReply_GetMarginRatio(FTAPI_Conn client, uint nSerialNo, TrdGetMarginRatio.Response rsp);`

*   **介绍**
    
    查询股票的融资融券数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Qot_Common.Security securityList = 2; //股票
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **回调**

    message MarginRatioInfo
    {
    	required Qot_Common.Security security = 1; //股票
    	optional bool   isLongPermit = 2; //是否允许融资
    	optional bool   isShortPermit = 3; //是否允许融券
    	optional double shortPoolRemain = 4; //卖空池剩余（股）
    	optional double shortFeeRate = 5; //融券参考利率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertLongRatio = 6; //融资预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertShortRatio = 7; //融券预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imLongRatio = 8; //融资初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imShortRatio = 9; //融券初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmLongRatio = 10; //融资 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmShortRatio = 11; //融券 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmLongRatio = 12; //融资维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmShortRatio = 13; //融券维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated MarginRatioInfo marginRatioInfoList = 2; //账户资金
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明 在 InitConnect.proto 中
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
                    .SetTrdEnv(TrdCommon.TrdEnv.TrdEnv_Real_VALUE)
                    .SetTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)
                    .Build();
            QotCommon.Security security = QotCommon.Security.CreateBuilder()
                    .SetCode("00700")
                    .SetMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .Build();
            TrdGetMarginRatio.C2S c2s = TrdGetMarginRatio.C2S.CreateBuilder()
                    .SetHeader(header)
                    .AddSecurityList(security)
                    .Build();
    
            TrdGetMarginRatio.Request req = TrdGetMarginRatio.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetMarginRatio(req);
            Console.Write("Send TrdGetMarginRatio: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetMarginRatio(FTAPI_Conn client, uint nSerialNo, TrdGetMarginRatio.Response rsp)
        {
            Console.Write("Reply: TrdGetMarginRatio: {0}\n", nSerialNo);
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
60  
61  

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6826814786778581486
    Send TrdGetMarginRatio: 3
    Reply: TrdGetMarginRatio: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int getMarginRatio(TrdGetMarginRatio.Request req);`  
`void onReply_GetMarginRatio(FTAPI_Conn client, int nSerialNo, TrdGetMarginRatio.Response rsp);`

*   **介绍**
    
    查询股票的融资融券数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Qot_Common.Security securityList = 2; //股票
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **回调**

    message MarginRatioInfo
    {
    	required Qot_Common.Security security = 1; //股票
    	optional bool   isLongPermit = 2; //是否允许融资
    	optional bool   isShortPermit = 3; //是否允许融券
    	optional double shortPoolRemain = 4; //卖空池剩余（股）
    	optional double shortFeeRate = 5; //融券参考利率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertLongRatio = 6; //融资预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertShortRatio = 7; //融券预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imLongRatio = 8; //融资初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imShortRatio = 9; //融券初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmLongRatio = 10; //融资 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmShortRatio = 11; //融券 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmLongRatio = 12; //融资维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmShortRatio = 13; //融券维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated MarginRatioInfo marginRatioInfoList = 2; //账户资金
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明 在 InitConnect.proto 中
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
            QotCommon.Security security = QotCommon.Security.newBuilder()
                    .setCode("00700")
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .build();
            TrdGetMarginRatio.C2S c2s = TrdGetMarginRatio.C2S.newBuilder()
                    .setHeader(header)
                    .addSecurityList(security)
                    .build();
            TrdGetMarginRatio.Request req = TrdGetMarginRatio.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getMarginRatio(req);
            System.out.printf("Send TrdGetMarginRatio: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetMarginRatio(FTAPI_Conn client, int nSerialNo, TrdGetMarginRatio.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetMarginRatio failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetMarginRatio: %s\n", json);
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

    Send TrdGetMarginRatio: 2
    Receive TrdGetMarginRatio: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "marginRatioInfoList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "isLongPermit": true,\
          "isShortPermit": true,\
          "shortPoolRemain": 1987700.0,\
          "shortFeeRate": 0.9,\
          "alertLongRatio": 33.0,\
          "alertShortRatio": 56.00000000000001,\
          "imLongRatio": 35.0,\
          "imShortRatio": 60.0,\
          "mcmLongRatio": 32.0,\
          "mcmShortRatio": 53.0,\
          "mmLongRatio": 25.0,\
          "mmShortRatio": 40.0\
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

`Futu::u32_t GetMarginRatio(const Trd_GetMarginRatio::Request &stReq);`  
`virtual void OnReply_GetMarginRatio(Futu::u32_t nSerialNo, const Trd_GetMarginRatio::Response &stRsp) = 0;`

*   **介绍**
    
    查询股票的融资融券数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Qot_Common.Security securityList = 2; //股票
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **回调**

    message MarginRatioInfo
    {
    	required Qot_Common.Security security = 1; //股票
    	optional bool   isLongPermit = 2; //是否允许融资
    	optional bool   isShortPermit = 3; //是否允许融券
    	optional double shortPoolRemain = 4; //卖空池剩余（股）
    	optional double shortFeeRate = 5; //融券参考利率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertLongRatio = 6; //融资预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertShortRatio = 7; //融券预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imLongRatio = 8; //融资初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imShortRatio = 9; //融券初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmLongRatio = 10; //融资 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmShortRatio = 11; //融券 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmLongRatio = 12; //融资维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmShortRatio = 13; //融券维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated MarginRatioInfo marginRatioInfoList = 2; //账户资金
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明 在 InitConnect.proto 中
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    		Trd_GetMarginRatio::Request req;
    		Trd_GetMarginRatio::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetMarginRatioSerialNo = m_pTrdApi->GetMarginRatio(req);
            cout << "Request GetMarginRatio SerialNo: " << m_GetMarginRatioSerialNo << endl;
    	}
    
    	virtual void OnReply_GetMarginRatio(Futu::u32_t nSerialNo, const Trd_GetMarginRatio::Response &stRsp){
            if(nSerialNo == m_GetMarginRatioSerialNo)
            {
                cout << "OnReply_GetMarginRatio SerialNo: " << nSerialNo << endl;
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

*   **Output**

    connect
    Request GetMarginRatio SerialNo: 4
    OnReply_GetMarginRatio SerialNo: 4
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
      "marginRatioInfoList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "00700"\
        },\
        "isLongPermit": true,\
        "isShortPermit": true,\
        "shortPoolRemain": 1853100,\
        "shortFeeRate": 0.9,\
        "alertLongRatio": 33,\
        "alertShortRatio": 56.000000000000007,\
        "imLongRatio": 35,\
        "imShortRatio": 60,\
        "mcmLongRatio": 32,\
        "mcmShortRatio": 53,\
        "mmLongRatio": 25,\
        "mmShortRatio": 40\
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

`GetMarginRatio(req);`

*   **介绍**
    
    查询股票的融资融券数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Qot_Common.Security securityList = 2; //股票
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message MarginRatioInfo
    {
    	required Qot_Common.Security security = 1; //股票
    	optional bool   isLongPermit = 2; //是否允许融资
    	optional bool   isShortPermit = 3; //是否允许融券
    	optional double shortPoolRemain = 4; //卖空池剩余（股）
    	optional double shortFeeRate = 5; //融券参考利率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertLongRatio = 6; //融资预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertShortRatio = 7; //融券预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imLongRatio = 8; //融资初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imShortRatio = 9; //融券初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmLongRatio = 10; //融资 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmShortRatio = 11; //融券 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmLongRatio = 12; //融资维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmShortRatio = 13; //融券维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated MarginRatioInfo marginRatioInfoList = 2; //账户资金
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明 在 InitConnect.proto 中
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetMarginRatio(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        const { TrdEnv, TrdMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ced92e472b40c92a'];
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
                                securityList:[{\
                                    market: QotMarket.QotMarket_HK_Security,\
                                    code: "00700",\
                                },],
                            },
                        };
    
                        websocket.GetMarginRatio(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetMarginRatio: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
75  

*   **Output**

    GetMarginRatio: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6684972",
        "trdMarket": 1
      },
      "marginRatioInfoList": [{\
        "security": {\
          "market": 1,\
          "code": "00700"\
        },\
        "isLongPermit": true,\
        "isShortPermit": true,\
        "shortPoolRemain": 3082200,\
        "shortFeeRate": 0.88,\
        "alertLongRatio": 33,\
        "alertShortRatio": 46,\
        "imLongRatio": 35,\
        "imShortRatio": 50,\
        "mcmLongRatio": 32,\
        "mcmShortRatio": 43,\
        "mmLongRatio": 25,\
        "mmShortRatio": 30\
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

接口限制

*   单用户ID 每 30 秒内最多请求 10 次获取融资融券数据接口。
*   每次请求，接口参数股票代码列表，支持传入的标的数量上限是 100 个。
*   仅支持港股正股和美股正股的查询。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_margin_ratio(code_list)`

*   **介绍**
    
    查询股票的融资融券数据。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 股票代码列表<br>(ℹ️ 每次最多可请求 100 个标的)  <br>list 内元素类型为 str |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回融资融券数据 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   融资融券数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | is\_long\_permit | bool | 是否允许融资 |
        | is\_short\_permit | bool | 是否允许融券 |
        | short\_pool\_remain | float | 卖空池剩余<br>(ℹ️ 单位：股) |
        | short\_fee\_rate | float | 融券参考利率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | alert\_long\_ratio | float | 融资预警比率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | alert\_short\_ratio | float | 融券预警比率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | im\_long\_ratio | float | 融资初始保证金率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | im\_short\_ratio | float | 融券初始保证金率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | mcm\_long\_ratio | float | 融资 margin call 保证金率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | mcm\_short\_ratio | float | 融券 margin call 保证金率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | mm\_long\_ratio | float | 融资维持保证金率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | mm\_short\_ratio | float | 融券维持保证金率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        
*   **Example**
    

    from moomoo import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    ret, data = trd_ctx.get_margin_ratio(code_list=['US.AAPL','US.FUTU'])  
    if ret == RET_OK:
        print(data)
        print(data['is_long_permit'][0])  # 取第一条的是否允许融资
        print(data['im_short_ratio'].values.tolist())  # 转为 list
    else:
        print('error:', data)
    trd_ctx.close()  # 结束后记得关闭当条连接，防止连接条数用尽
    

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

*   **Output**

           code  is_long_permit  is_short_permit  short_pool_remain  short_fee_rate  alert_long_ratio  alert_short_ratio  im_long_ratio  im_short_ratio  mcm_long_ratio  mcm_short_ratio  mm_long_ratio  mm_short_ratio
    0  US.AAPL            True             True          1826900.0            0.89              33.0               56.0           35.0            60.0            32.0             53.0           25.0            40.0
    1  US.FUTU            True             True          1150600.0            0.95              48.0               46.0           50.0            50.0            47.0             43.0           40.0            30.0
    True
    [60.0, 50.0]
    

1  
2  
3  
4  
5  

[#](./trade_get-margin-ratio.md#4388-2)
 Trd\_GetMarginRatio.proto
----------------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询股票的融资融券数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Qot_Common.Security securityList = 2; //股票
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message MarginRatioInfo
    {
    	required Qot_Common.Security security = 1; //股票
    	optional bool   isLongPermit = 2; //是否允许融资
    	optional bool   isShortPermit = 3; //是否允许融券
    	optional double shortPoolRemain = 4; //卖空池剩余（股）
    	optional double shortFeeRate = 5; //融券参考利率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertLongRatio = 6; //融资预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertShortRatio = 7; //融券预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imLongRatio = 8; //融资初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imShortRatio = 9; //融券初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmLongRatio = 10; //融资 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmShortRatio = 11; //融券 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmLongRatio = 12; //融资维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmShortRatio = 13; //融券维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated MarginRatioInfo marginRatioInfoList = 2; //账户资金
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明 在 InitConnect.proto 中
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    2223
    

`uint GetMarginRatio(TrdGetMarginRatio.Request req);`  
`virtual void OnReply_GetMarginRatio(MMAPI_Conn client, uint nSerialNo, TrdGetMarginRatio.Response rsp);`

*   **介绍**
    
    查询股票的融资融券数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Qot_Common.Security securityList = 2; //股票
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **回调**

    message MarginRatioInfo
    {
    	required Qot_Common.Security security = 1; //股票
    	optional bool   isLongPermit = 2; //是否允许融资
    	optional bool   isShortPermit = 3; //是否允许融券
    	optional double shortPoolRemain = 4; //卖空池剩余（股）
    	optional double shortFeeRate = 5; //融券参考利率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertLongRatio = 6; //融资预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertShortRatio = 7; //融券预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imLongRatio = 8; //融资初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imShortRatio = 9; //融券初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmLongRatio = 10; //融资 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmShortRatio = 11; //融券 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmLongRatio = 12; //融资维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmShortRatio = 13; //融券维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated MarginRatioInfo marginRatioInfoList = 2; //账户资金
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明 在 InitConnect.proto 中
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
                    .SetTrdEnv(TrdCommon.TrdEnv.TrdEnv_Real_VALUE)
                    .SetTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)
                    .Build();
            QotCommon.Security security = QotCommon.Security.CreateBuilder()
                    .SetCode("00700")
                    .SetMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .Build();
            TrdGetMarginRatio.C2S c2s = TrdGetMarginRatio.C2S.CreateBuilder()
                    .SetHeader(header)
                    .AddSecurityList(security)
                    .Build();
    
            TrdGetMarginRatio.Request req = TrdGetMarginRatio.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetMarginRatio(req);
            Console.Write("Send TrdGetMarginRatio: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetMarginRatio(MMAPI_Conn client, uint nSerialNo, TrdGetMarginRatio.Response rsp)
        {
            Console.Write("Reply: TrdGetMarginRatio: {0}\n", nSerialNo);
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
60  
61  

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6826814786778581486
    Send TrdGetMarginRatio: 3
    Reply: TrdGetMarginRatio: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int getMarginRatio(TrdGetMarginRatio.Request req);`  
`void onReply_GetMarginRatio(MMAPI_Conn client, int nSerialNo, TrdGetMarginRatio.Response rsp);`

*   **介绍**
    
    查询股票的融资融券数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Qot_Common.Security securityList = 2; //股票
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **回调**

    message MarginRatioInfo
    {
    	required Qot_Common.Security security = 1; //股票
    	optional bool   isLongPermit = 2; //是否允许融资
    	optional bool   isShortPermit = 3; //是否允许融券
    	optional double shortPoolRemain = 4; //卖空池剩余（股）
    	optional double shortFeeRate = 5; //融券参考利率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertLongRatio = 6; //融资预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertShortRatio = 7; //融券预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imLongRatio = 8; //融资初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imShortRatio = 9; //融券初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmLongRatio = 10; //融资 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmShortRatio = 11; //融券 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmLongRatio = 12; //融资维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmShortRatio = 13; //融券维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated MarginRatioInfo marginRatioInfoList = 2; //账户资金
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明 在 InitConnect.proto 中
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
            QotCommon.Security security = QotCommon.Security.newBuilder()
                    .setCode("00700")
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .build();
            TrdGetMarginRatio.C2S c2s = TrdGetMarginRatio.C2S.newBuilder()
                    .setHeader(header)
                    .addSecurityList(security)
                    .build();
            TrdGetMarginRatio.Request req = TrdGetMarginRatio.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getMarginRatio(req);
            System.out.printf("Send TrdGetMarginRatio: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetMarginRatio(MMAPI_Conn client, int nSerialNo, TrdGetMarginRatio.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetMarginRatio failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetMarginRatio: %s\n", json);
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

    Send TrdGetMarginRatio: 2
    Receive TrdGetMarginRatio: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "marginRatioInfoList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "isLongPermit": true,\
          "isShortPermit": true,\
          "shortPoolRemain": 1987700.0,\
          "shortFeeRate": 0.9,\
          "alertLongRatio": 33.0,\
          "alertShortRatio": 56.00000000000001,\
          "imLongRatio": 35.0,\
          "imShortRatio": 60.0,\
          "mcmLongRatio": 32.0,\
          "mcmShortRatio": 53.0,\
          "mmLongRatio": 25.0,\
          "mmShortRatio": 40.0\
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

`moomoo::u32_t GetMarginRatio(const Trd_GetMarginRatio::Request &stReq);`  
`virtual void OnReply_GetMarginRatio(moomoo::u32_t nSerialNo, const Trd_GetMarginRatio::Response &stRsp) = 0;`

*   **介绍**
    
    查询股票的融资融券数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Qot_Common.Security securityList = 2; //股票
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **回调**

    message MarginRatioInfo
    {
    	required Qot_Common.Security security = 1; //股票
    	optional bool   isLongPermit = 2; //是否允许融资
    	optional bool   isShortPermit = 3; //是否允许融券
    	optional double shortPoolRemain = 4; //卖空池剩余（股）
    	optional double shortFeeRate = 5; //融券参考利率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertLongRatio = 6; //融资预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertShortRatio = 7; //融券预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imLongRatio = 8; //融资初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imShortRatio = 9; //融券初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmLongRatio = 10; //融资 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmShortRatio = 11; //融券 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmLongRatio = 12; //融资维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmShortRatio = 13; //融券维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated MarginRatioInfo marginRatioInfoList = 2; //账户资金
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明 在 InitConnect.proto 中
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    		Trd_GetMarginRatio::Request req;
    		Trd_GetMarginRatio::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetMarginRatioSerialNo = m_pTrdApi->GetMarginRatio(req);
            cout << "Request GetMarginRatio SerialNo: " << m_GetMarginRatioSerialNo << endl;
    	}
    
    	virtual void OnReply_GetMarginRatio(moomoo::u32_t nSerialNo, const Trd_GetMarginRatio::Response &stRsp){
            if(nSerialNo == m_GetMarginRatioSerialNo)
            {
                cout << "OnReply_GetMarginRatio SerialNo: " << nSerialNo << endl;
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

*   **Output**

    connect
    Request GetMarginRatio SerialNo: 4
    OnReply_GetMarginRatio SerialNo: 4
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
      "marginRatioInfoList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "00700"\
        },\
        "isLongPermit": true,\
        "isShortPermit": true,\
        "shortPoolRemain": 1853100,\
        "shortFeeRate": 0.9,\
        "alertLongRatio": 33,\
        "alertShortRatio": 56.000000000000007,\
        "imLongRatio": 35,\
        "imShortRatio": 60,\
        "mcmLongRatio": 32,\
        "mcmShortRatio": 53,\
        "mmLongRatio": 25,\
        "mmShortRatio": 40\
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

`GetMarginRatio(req);`

*   **介绍**
    
    查询股票的融资融券数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Qot_Common.Security securityList = 2; //股票
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message MarginRatioInfo
    {
    	required Qot_Common.Security security = 1; //股票
    	optional bool   isLongPermit = 2; //是否允许融资
    	optional bool   isShortPermit = 3; //是否允许融券
    	optional double shortPoolRemain = 4; //卖空池剩余（股）
    	optional double shortFeeRate = 5; //融券参考利率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertLongRatio = 6; //融资预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double alertShortRatio = 7; //融券预警比率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imLongRatio = 8; //融资初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double imShortRatio = 9; //融券初始保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmLongRatio = 10; //融资 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mcmShortRatio = 11; //融券 margin call 保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmLongRatio = 12; //融资维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double mmShortRatio = 13; //融券维持保证金率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated MarginRatioInfo marginRatioInfoList = 2; //账户资金
    }
    
    message Response
    {
    	//以下3个字段每条协议都有，注释说明 在 InitConnect.proto 中
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetMarginRatio(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        const { TrdEnv, TrdMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ced92e472b40c92a'];
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
                                securityList:[{\
                                    market: QotMarket.QotMarket_HK_Security,\
                                    code: "00700",\
                                },],
                            },
                        };
    
                        websocket.GetMarginRatio(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetMarginRatio: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
75  

*   **Output**

    GetMarginRatio: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6684972",
        "trdMarket": 1
      },
      "marginRatioInfoList": [{\
        "security": {\
          "market": 1,\
          "code": "00700"\
        },\
        "isLongPermit": true,\
        "isShortPermit": true,\
        "shortPoolRemain": 3082200,\
        "shortFeeRate": 0.88,\
        "alertLongRatio": 33,\
        "alertShortRatio": 46,\
        "imLongRatio": 35,\
        "imShortRatio": 50,\
        "mcmLongRatio": 32,\
        "mcmShortRatio": 43,\
        "mmLongRatio": 25,\
        "mmShortRatio": 30\
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

接口限制

*   单用户ID 每 30 秒内最多请求 10 次获取融资融券数据接口。
*   每次请求，接口参数股票代码列表，支持传入的标的数量上限是 100 个。
*   支持美国、香港、A股市场的股票和ETF。

← [查询持仓](./trade_get-position-list.md) [查询账户现金流水](./trade_get-acc-cash-flow.md)
 →

[获取融资融券数据](./trade_get-margin-ratio.md)