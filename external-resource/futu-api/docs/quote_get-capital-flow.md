# 获取资金流向 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quote/get-capital-flow.html

[#](https://openapi.futunn.com/futu-api-doc/quote/get-capital-flow.html#3443)
 获取资金流向
=====================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_capital_flow(stock_code, period_type = PeriodType.INTRADAY, start=None, end=None)`

*   **介绍**
    
    获取个股资金流向
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | stock\_code | str | 股票代码 |
    | period\_type | [PeriodType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2644) | 周期类型 |
    | start | str | 开始时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd  <br>例如：“2017-06-20” |
    | end | str | 结束时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd  <br>例如：“2017-06-20” |
    
    *   start 和 end 的组合如下
        
        | start 类型 | end 类型 | 说明  |
        | --- | --- | --- |
        | str | str | start 和 end 分别为指定的日期 |
        | None | str | start 为 end 往前 365 天 |
        | str | None | end 为 start 往后 365 天 |
        | None | None | end 为 当前日期，start 往前 365 天 |
        

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回资金流向数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   资金流向数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | in\_flow | float | 整体净流入 |
        | main\_in\_flow | float | 主力大单净流入<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅历史周期（日、周、月）有效 |
        | super\_in\_flow | float | 特大单净流入 |
        | big\_in\_flow | float | 大单净流入 |
        | mid\_in\_flow | float | 中单净流入 |
        | sml\_in\_flow | float | 小单净流入 |
        | capital\_flow\_item\_time | str | 开始时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd HH:mm:ss  <br>精确到分钟 |
        | last\_valid\_time | str | 数据最后有效时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅实时周期有效 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_capital_flow("HK.00700", period_type = PeriodType.INTRADAY)
    if ret == RET_OK:
        print(data)
        print(data['in_flow'][0])    # 取第一条的净流入的资金额度
        print(data['in_flow'].values.tolist())   # 转为 list
    else:
        print('error:', data)
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

*   **Output**

        last_valid_time       in_flow  ...  main_in_flow  capital_flow_item_time
    0               N/A -1.857915e+08  ... -1.066828e+08     2021-06-08 00:00:00
    ..              ...           ...  ...           ...                     ...
    245             N/A  2.179240e+09  ...  2.143345e+09     2022-06-08 00:00:00
    
    [246 rows x 8 columns]
    -185791500.0
    [-185791500.0, -18315000.0, -672100100.0, -714394350.0, -698391950.0, -818886750.0, 304827400.0, 73026200.0, -2078217500.0, \
    ..                   ...           ...                    ...\
    2031460.0, 638067040.0, 622466600.0, -351788160.0, -328529240.0, 715415020.0, 76749700.0, 2179240320.0]
    

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

[#](https://openapi.futunn.com/futu-api-doc/quote/get-capital-flow.html#2370)
 Qot\_GetCapitalFlow.proto
--------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取资金流向
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	optional int32 periodType = 2; // Qot_Common.PeriodType 周期类型
    	optional string beginTime = 3; // 开始时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
    	optional string endTime = 4; // 结束时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   周期类型参见 [PeriodType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2644)
>     

*   **返回**

    message CapitalFlowItem
    {
    	required double inFlow = 1; // 整体净流入
    	optional string time = 2; //开始时间字符串,以分钟为单位
    	optional double timestamp = 3; //开始时间戳
    	optional double mainInFlow = 4; // 主力大单净流入，仅周期类型不为实时有效
    	optional double superInFlow = 5; // 特大单净流入
    	optional double bigInFlow = 6; // 大单净流入
    	optional double midInFlow = 7; // 中单净流入
    	optional double smlInFlow = 8; // 小单净流入
    }
    
    message S2C
    {
    	repeated CapitalFlowItem flowItemList = 1; //资金流向
    	optional string lastValidTime = 2; //数据最后有效时间字符串
    	optional double lastValidTimestamp = 3; //数据最后有效时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3211
    

`uint GetCapitalFlow(QotGetCapitalFlow.Request req);`  
`virtual void OnReply_GetCapitalFlow(FTAPI_Conn client, uint nSerialNo, QotGetCapitalFlow.Response rsp);`

*   **介绍**

获取资金流向

*   **参数**

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	optional int32 periodType = 2; // Qot_Common.PeriodType 周期类型
    	optional string beginTime = 3; // 开始时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
    	optional string endTime = 4; // 结束时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   周期类型参见 [PeriodType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2644)
>     

*   **返回**

    message CapitalFlowItem
    {
    	required double inFlow = 1; // 整体净流入
    	optional string time = 2; //开始时间字符串,以分钟为单位
    	optional double timestamp = 3; //开始时间戳
    	optional double mainInFlow = 4; // 主力大单净流入，仅周期类型不为实时有效
    	optional double superInFlow = 5; // 特大单净流入
    	optional double bigInFlow = 6; // 大单净流入
    	optional double midInFlow = 7; // 中单净流入
    	optional double smlInFlow = 8; // 小单净流入
    }
    
    message S2C
    {
    	repeated CapitalFlowItem flowItemList = 1; //资金流向
    	optional string lastValidTime = 2; //数据最后有效时间字符串
    	optional double lastValidTimestamp = 3; //数据最后有效时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : FTSPI_Qot, FTSPI_Conn
    {
    	FTAPI_Qot qot = new FTAPI_Qot();
    
    	public Program()
    	{
    		qot.SetClientInfo("csharp", 1);  //设置客户端信息
    		qot.SetConnCallback(this);  //设置连接回调
    		qot.SetQotCallback(this);   //设置交易回调
    	}
    
    	public void Start()
    	{
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
    		QotGetCapitalFlow.C2S c2s = QotGetCapitalFlow.C2S.CreateBuilder()
    				.SetSecurity(sec)
    				.SetPeriodType((int)QotCommon.PeriodType.PeriodType_INTRADAY)
    				.Build();
    		QotGetCapitalFlow.Request req = QotGetCapitalFlow.Request.CreateBuilder().SetC2S(c2s).Build();
    		uint seqNo = qot.GetCapitalFlow(req);
    		Console.Write("Send QotGetCapitalFlow: {0}\n", seqNo);
    	}
    
    
    	public void OnDisconnect(FTAPI_Conn client, long errCode)
    	{
    		Console.Write("Qot onDisConnect: {0}\n", errCode);
    	}
    
    	public void OnReply_GetCapitalFlow(FTAPI_Conn client, uint nSerialNo, QotGetCapitalFlow.Response rsp)
    	{
    		Console.Write("Reply: QotGetCapitalFlow: {0}  {1}\n", nSerialNo, rsp.ToString());
    		Console.Write("inFlow: {0}\n", rsp.S2C.FlowItemListList[0].InFlow);
    	}
    
    	public static void Main(String[] args)
    	{
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
58  

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6939768707708965532
    Send QotGetCapitalFlow: 3
    Reply: QotGetCapitalFlow: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      flowItemList {
        inFlow: 0
        time: "2022-06-07 09:30:00"
        timestamp: 1654565400
        superInFlow: 0
        bigInFlow: 0
        midInFlow: 0
        smlInFlow: 0
      }
      flowItemList {
        inFlow: -48348560
        time: "2022-06-07 09:31:00"
        timestamp: 1654565460
        superInFlow: -32509240
        bigInFlow: -3675200
        midInFlow: -7317740
        smlInFlow: -4846380
      }
      flowItemList {
        inFlow: -21132380
        time: "2022-06-07 09:32:00"
        timestamp: 1654565520
        superInFlow: -32509240
        bigInFlow: -4001600
        midInFlow: 13582880
        smlInFlow: 1795580
      }
      flowItemList {
        inFlow: 44127520
        time: "2022-06-07 09:33:00"
        timestamp: 1654565580
        superInFlow: -9580000
        bigInFlow: 21937100
        midInFlow: 19888960
        smlInFlow: 11881460
      }
    // ... 
      flowItemList {
        inFlow: 22462640
        time: "2022-06-07 10:43:00"
        timestamp: 1654569780
        superInFlow: -56322820
        bigInFlow: -27898060
        midInFlow: 45435080
        smlInFlow: 61248440
      }
      flowItemList {
        inFlow: 15506920
        time: "2022-06-07 10:44:00"
        timestamp: 1654569840
        superInFlow: -56322820
        bigInFlow: -32338060
        midInFlow: 44214740
        smlInFlow: 59953060
      }
      lastValidTime: "2022-06-07 10:43:44"
    }
    
    inFlow: 0
    

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

`int getCapitalFlow(QotGetCapitalFlow.Request req);`  
`void onReply_GetCapitalFlow(FTAPI_Conn client, int nSerialNo, QotGetCapitalFlow.Response rsp);`

*   **介绍**

获取资金流向

*   **参数**

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	optional int32 periodType = 2; // Qot_Common.PeriodType 周期类型
    	optional string beginTime = 3; // 开始时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
    	optional string endTime = 4; // 结束时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   周期类型参见 [PeriodType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2644)
>     

*   **返回**

    message CapitalFlowItem
    {
    	required double inFlow = 1; // 整体净流入
    	optional string time = 2; //开始时间字符串,以分钟为单位
    	optional double timestamp = 3; //开始时间戳
    	optional double mainInFlow = 4; // 主力大单净流入，仅周期类型不为实时有效
    	optional double superInFlow = 5; // 特大单净流入
    	optional double bigInFlow = 6; // 大单净流入
    	optional double midInFlow = 7; // 中单净流入
    	optional double smlInFlow = 8; // 小单净流入
    }
    
    message S2C
    {
    	repeated CapitalFlowItem flowItemList = 1; //资金流向
    	optional string lastValidTime = 2; //数据最后有效时间字符串
    	optional double lastValidTimestamp = 3; //数据最后有效时间戳
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
            QotGetCapitalFlow.C2S c2s = QotGetCapitalFlow.C2S.newBuilder()
                    .setSecurity(sec)
                .build();
            QotGetCapitalFlow.Request req = QotGetCapitalFlow.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getCapitalFlow(req);
            System.out.printf("Send QotGetCapitalFlow: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetCapitalFlow(FTAPI_Conn client, int nSerialNo, QotGetCapitalFlow.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetCapitalFlow failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetCapitalFlow: %s\n", json);
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

*   **Output**

    Send QotGetCapitalFlow: 2
    Receive QotGetCapitalFlow: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "flowItemList": [{\
          "inFlow": 0.0,\
          "time": "2022-06-08 09:30:00",\
          "timestamp": 1.6546518E9,\
          "superInFlow": 0.0,\
          "bigInFlow": 0.0,\
          "midInFlow": 0.0,\
          "smlInFlow": 0.0\
        }, {\
          "inFlow": -4960940.0,\
          "time": "2022-06-08 09:31:00",\
          "timestamp": 1.65465186E9,\
          "superInFlow": 0.0,\
          "bigInFlow": -4857400.0,\
          "midInFlow": -1.004904E7,\
          "smlInFlow": 9945500.0\
        }, {\
          "inFlow": -3260340.0,\
          "time": "2022-06-08 09:32:00",\
          "timestamp": 1.65465192E9,\
          "superInFlow": -8140120.0,\
          "bigInFlow": -4857400.0,\
          "midInFlow": -7800660.0,\
          "smlInFlow": 1.753784E7\
        }, {\
          "inFlow": 5.715794E7,\
          "time": "2022-06-08 09:33:00",\
          "timestamp": 1.65465198E9,\
          "superInFlow": 3.382238E7,\
          "bigInFlow": 3508240.0,\
          "midInFlow": 337820.0,\
          "smlInFlow": 1.94895E7\
        }, {\
          "inFlow": 6.58145E7,\
          "time": "2022-06-08 09:34:00",\
          "timestamp": 1.65465204E9,\
          "superInFlow": 4.114658E7,\
          "bigInFlow": -1.335444E7,\
          "midInFlow": 1.37937E7,\
          "smlInFlow": 2.422866E7\
        }, {\
          "inFlow": 3.258944E7,\
          "time": "2022-06-08 09:35:00",\
          "timestamp": 1.6546521E9,\
          "superInFlow": 4.114658E7,\
          "bigInFlow": -2.083698E7,\
          "midInFlow": 8570020.0,\
          "smlInFlow": 3709820.0\
        }, \
        //...\
        {\
          "inFlow": 1.4041278E9,\
          "time": "2022-06-08 10:46:00",\
          "timestamp": 1.65465636E9,\
          "superInFlow": 5.3349552E8,\
          "bigInFlow": 4.287602E8,\
          "midInFlow": 2.4805104E8,\
          "smlInFlow": 1.9382104E8\
        }, {\
          "inFlow": 1.38049732E9,\
          "time": "2022-06-08 10:47:00",\
          "timestamp": 1.65465642E9,\
          "superInFlow": 5.1425064E8,\
          "bigInFlow": 4.287602E8,\
          "midInFlow": 2.4901564E8,\
          "smlInFlow": 1.8847084E8\
        }],
        "lastValidTime": "2022-06-08 10:47:25"
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
75  
76  
77  

`Futu::u32_t GetCapitalFlow(const Qot_GetCapitalFlow::Request &stReq);`  
`virtual void OnReply_GetCapitalFlow(Futu::u32_t nSerialNo, const Qot_GetCapitalFlow::Response &stRsp) = 0;`

*   **介绍**
    
    获取资金流向
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	optional int32 periodType = 2; // Qot_Common.PeriodType 周期类型
    	optional string beginTime = 3; // 开始时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
    	optional string endTime = 4; // 结束时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   周期类型参见 [PeriodType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2644)
>     

*   **返回**

    message CapitalFlowItem
    {
    	required double inFlow = 1; // 整体净流入
    	optional string time = 2; //开始时间字符串,以分钟为单位
    	optional double timestamp = 3; //开始时间戳
    	optional double mainInFlow = 4; // 主力大单净流入，仅周期类型不为实时有效
    	optional double superInFlow = 5; // 特大单净流入
    	optional double bigInFlow = 6; // 大单净流入
    	optional double midInFlow = 7; // 中单净流入
    	optional double smlInFlow = 8; // 小单净流入
    }
    
    message S2C
    {
    	repeated CapitalFlowItem flowItemList = 1; //资金流向
    	optional string lastValidTime = 2; //数据最后有效时间字符串
    	optional double lastValidTimestamp = 3; //数据最后有效时间戳
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
    
    		// construct request message
    		Qot_GetCapitalFlow::Request req;
    		Qot_GetCapitalFlow::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->set_periodtype(Qot_Common::PeriodType::PeriodType_INTRADAY);
    		m_pQotApi->GetCapitalFlow(req);
    		cout << "GetCapitalFlow" << endl;
    	}
    
    	virtual void OnReply_GetCapitalFlow(Futu::u32_t nSerialNo, const Qot_GetCapitalFlow::Response &stRsp){
    		cout << "OnReply_GetCapitalFlow:" << endl;
    		// print response
    		// ProtoBufToBodyData and UTF8ToLocal refer to tool.h in Samples
    		string resp_str;
    		ProtoBufToBodyData(stRsp, resp_str);
    		cout << UTF8ToLocal(resp_str) << endl;
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi; 
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

*   **Output**

    connect
    GetCapitalFlow
    OnReply_GetCapitalFlow:
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "flowItemList": [\
       {\
        "inFlow": 0,\
        "time": "2022-06-08 09:30:00",\
        "timestamp": 1654651800,\
        "superInFlow": 0,\
        "bigInFlow": 0,\
        "midInFlow": 0,\
        "smlInFlow": 0\
       },\
       {\
        "inFlow": -4960940,\
        "time": "2022-06-08 09:31:00",\
        "timestamp": 1654651860,\
        "superInFlow": 0,\
        "bigInFlow": -4857400,\
        "midInFlow": -10049040,\
        "smlInFlow": 9945500\
       },\
       {\
        "inFlow": -3260340,\
        "time": "2022-06-08 09:32:00",\
        "timestamp": 1654651920,\
        "superInFlow": -8140120,\
        "bigInFlow": -4857400,\
        "midInFlow": -7800660,\
        "smlInFlow": 17537840\
       },\
    // ...\
       {\
        "inFlow": 1404127800,\
        "time": "2022-06-08 10:46:00",\
        "timestamp": 1654656360,\
        "superInFlow": 533495520,\
        "bigInFlow": 428760200,\
        "midInFlow": 248051040,\
        "smlInFlow": 193821040\
       },\
       {\
        "inFlow": 1387351340,\
        "time": "2022-06-08 10:47:00",\
        "timestamp": 1654656420,\
        "superInFlow": 514250640,\
        "bigInFlow": 428760200,\
        "midInFlow": 253136620,\
        "smlInFlow": 191203880\
       }\
      ],
      "lastValidTime": "2022-06-08 10:47:00"
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

`GetCapitalFlow(req);`

*   **介绍**
    
    获取资金流向
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	optional int32 periodType = 2; // Qot_Common.PeriodType 周期类型
    	optional string beginTime = 3; // 开始时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
    	optional string endTime = 4; // 结束时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   周期类型参见 [PeriodType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2644)
>     

*   **返回**

    message CapitalFlowItem
    {
    	required double inFlow = 1; // 整体净流入
    	optional string time = 2; //开始时间字符串,以分钟为单位
    	optional double timestamp = 3; //开始时间戳
    	optional double mainInFlow = 4; // 主力大单净流入，仅周期类型不为实时有效
    	optional double superInFlow = 5; // 特大单净流入
    	optional double bigInFlow = 6; // 大单净流入
    	optional double midInFlow = 7; // 中单净流入
    	optional double smlInFlow = 8; // 小单净流入
    }
    
    message S2C
    {
    	repeated CapitalFlowItem flowItemList = 1; //资金流向
    	optional string lastValidTime = 2; //数据最后有效时间字符串
    	optional double lastValidTimestamp = 3; //数据最后有效时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetCapitalFlow(){
        const { RetType } = Common
        const { PeriodType, QotMarket } = Qot_Common
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
                        periodType: PeriodType.PeriodType_INTRADAY,
                    },
                };
                websocket.GetCapitalFlow(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("CapitalFlow: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    CapitalFlow: errCode 0, retMsg , retType 0
    {
      "flowItemList": [{\
        "inFlow": -4960940,\
        "time": "2022-06-08 09:30:00",\
        "timestamp": 1654651800,\
        "superInFlow": 0.0,\
        "bigInFlow": -4857400.0,\
        "midInFlow": -1004904.0,\
        "smlInFlow": 9945500.0\
      }, ..., {\
        "inFlow": 944752820,\
        "time": "2022-06-08 10:24:00",\
        "timestamp": 1654655040,\
        "superInFlow": 53349552.0,\
        "bigInFlow": 4287602.0,\
        "midInFlow": 24805104.0,\
        "smlInFlow": 19382104.0\
      }],
      "lastValidTime": "2022-06-08 10:24:00"
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

接口限制

*   每 30 秒内最多请求 30 次获取资金流向接口。
*   仅支持正股、窝轮和基金。
*   历史周期（日、月、年）仅提供最近 1 年数据；实时周期仅提供最新一天的数据。
*   返回数据只包括盘中数据，不包含盘前盘后数据。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_capital_flow(stock_code, period_type = PeriodType.INTRADAY, start=None, end=None)`

*   **介绍**
    
    获取个股资金流向
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | stock\_code | str | 股票代码 |
    | period\_type | [PeriodType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2644) | 周期类型 |
    | start | str | 开始时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd  <br>例如：“2017-06-20” |
    | end | str | 结束时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd  <br>例如：“2017-06-20” |
    
    *   start 和 end 的组合如下
        
        | start 类型 | end 类型 | 说明  |
        | --- | --- | --- |
        | str | str | start 和 end 分别为指定的日期 |
        | None | str | start 为 end 往前 365 天 |
        | str | None | end 为 start 往后 365 天 |
        | None | None | end 为 当前日期，start 往前 365 天 |
        

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回资金流向数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   资金流向数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | in\_flow | float | 整体净流入 |
        | main\_in\_flow | float | 主力大单净流入<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅历史周期（日、周、月）有效 |
        | super\_in\_flow | float | 特大单净流入 |
        | big\_in\_flow | float | 大单净流入 |
        | mid\_in\_flow | float | 中单净流入 |
        | sml\_in\_flow | float | 小单净流入 |
        | capital\_flow\_item\_time | str | 开始时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd HH:mm:ss  <br>精确到分钟 |
        | last\_valid\_time | str | 数据最后有效时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅实时周期有效 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_capital_flow("HK.00700", period_type = PeriodType.INTRADAY)
    if ret == RET_OK:
        print(data)
        print(data['in_flow'][0])    # 取第一条的净流入的资金额度
        print(data['in_flow'].values.tolist())   # 转为 list
    else:
        print('error:', data)
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

*   **Output**

        last_valid_time       in_flow  ...  main_in_flow  capital_flow_item_time
    0               N/A -1.857915e+08  ... -1.066828e+08     2021-06-08 00:00:00
    ..              ...           ...  ...           ...                     ...
    245             N/A  2.179240e+09  ...  2.143345e+09     2022-06-08 00:00:00
    
    [246 rows x 8 columns]
    -185791500.0
    [-185791500.0, -18315000.0, -672100100.0, -714394350.0, -698391950.0, -818886750.0, 304827400.0, 73026200.0, -2078217500.0, \
    ..                   ...           ...                    ...\
    2031460.0, 638067040.0, 622466600.0, -351788160.0, -328529240.0, 715415020.0, 76749700.0, 2179240320.0]
    

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

[#](https://openapi.futunn.com/futu-api-doc/quote/get-capital-flow.html#2370-2)
 Qot\_GetCapitalFlow.proto
----------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取资金流向
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	optional int32 periodType = 2; // Qot_Common.PeriodType 周期类型
    	optional string beginTime = 3; // 开始时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
    	optional string endTime = 4; // 结束时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   周期类型参见 [PeriodType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2644)
>     

*   **返回**

    message CapitalFlowItem
    {
    	required double inFlow = 1; // 整体净流入
    	optional string time = 2; //开始时间字符串,以分钟为单位
    	optional double timestamp = 3; //开始时间戳
    	optional double mainInFlow = 4; // 主力大单净流入，仅周期类型不为实时有效
    	optional double superInFlow = 5; // 特大单净流入
    	optional double bigInFlow = 6; // 大单净流入
    	optional double midInFlow = 7; // 中单净流入
    	optional double smlInFlow = 8; // 小单净流入
    }
    
    message S2C
    {
    	repeated CapitalFlowItem flowItemList = 1; //资金流向
    	optional string lastValidTime = 2; //数据最后有效时间字符串
    	optional double lastValidTimestamp = 3; //数据最后有效时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3211
    

`uint GetCapitalFlow(QotGetCapitalFlow.Request req);`  
`virtual void OnReply_GetCapitalFlow(MMAPI_Conn client, uint nSerialNo, QotGetCapitalFlow.Response rsp);`

*   **介绍**

获取资金流向

*   **参数**

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	optional int32 periodType = 2; // Qot_Common.PeriodType 周期类型
    	optional string beginTime = 3; // 开始时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
    	optional string endTime = 4; // 结束时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   周期类型参见 [PeriodType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2644)
>     

*   **返回**

    message CapitalFlowItem
    {
    	required double inFlow = 1; // 整体净流入
    	optional string time = 2; //开始时间字符串,以分钟为单位
    	optional double timestamp = 3; //开始时间戳
    	optional double mainInFlow = 4; // 主力大单净流入，仅周期类型不为实时有效
    	optional double superInFlow = 5; // 特大单净流入
    	optional double bigInFlow = 6; // 大单净流入
    	optional double midInFlow = 7; // 中单净流入
    	optional double smlInFlow = 8; // 小单净流入
    }
    
    message S2C
    {
    	repeated CapitalFlowItem flowItemList = 1; //资金流向
    	optional string lastValidTime = 2; //数据最后有效时间字符串
    	optional double lastValidTimestamp = 3; //数据最后有效时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : MMSPI_Qot, MMSPI_Conn
    {
    	MMAPI_Qot qot = new MMAPI_Qot();
    
    	public Program()
    	{
    		qot.SetClientInfo("csharp", 1);  //设置客户端信息
    		qot.SetConnCallback(this);  //设置连接回调
    		qot.SetQotCallback(this);   //设置交易回调
    	}
    
    	public void Start()
    	{
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
    		QotGetCapitalFlow.C2S c2s = QotGetCapitalFlow.C2S.CreateBuilder()
    				.SetSecurity(sec)
    				.SetPeriodType((int)QotCommon.PeriodType.PeriodType_INTRADAY)
    				.Build();
    		QotGetCapitalFlow.Request req = QotGetCapitalFlow.Request.CreateBuilder().SetC2S(c2s).Build();
    		uint seqNo = qot.GetCapitalFlow(req);
    		Console.Write("Send QotGetCapitalFlow: {0}\n", seqNo);
    	}
    
    
    	public void OnDisconnect(MMAPI_Conn client, long errCode)
    	{
    		Console.Write("Qot onDisConnect: {0}\n", errCode);
    	}
    
    	public void OnReply_GetCapitalFlow(MMAPI_Conn client, uint nSerialNo, QotGetCapitalFlow.Response rsp)
    	{
    		Console.Write("Reply: QotGetCapitalFlow: {0}  {1}\n", nSerialNo, rsp.ToString());
    		Console.Write("inFlow: {0}\n", rsp.S2C.FlowItemListList[0].InFlow);
    	}
    
    	public static void Main(String[] args)
    	{
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
58  

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6939768707708965532
    Send QotGetCapitalFlow: 3
    Reply: QotGetCapitalFlow: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      flowItemList {
        inFlow: 0
        time: "2022-06-07 09:30:00"
        timestamp: 1654565400
        superInFlow: 0
        bigInFlow: 0
        midInFlow: 0
        smlInFlow: 0
      }
      flowItemList {
        inFlow: -48348560
        time: "2022-06-07 09:31:00"
        timestamp: 1654565460
        superInFlow: -32509240
        bigInFlow: -3675200
        midInFlow: -7317740
        smlInFlow: -4846380
      }
      flowItemList {
        inFlow: -21132380
        time: "2022-06-07 09:32:00"
        timestamp: 1654565520
        superInFlow: -32509240
        bigInFlow: -4001600
        midInFlow: 13582880
        smlInFlow: 1795580
      }
      flowItemList {
        inFlow: 44127520
        time: "2022-06-07 09:33:00"
        timestamp: 1654565580
        superInFlow: -9580000
        bigInFlow: 21937100
        midInFlow: 19888960
        smlInFlow: 11881460
      }
    // ... 
      flowItemList {
        inFlow: 22462640
        time: "2022-06-07 10:43:00"
        timestamp: 1654569780
        superInFlow: -56322820
        bigInFlow: -27898060
        midInFlow: 45435080
        smlInFlow: 61248440
      }
      flowItemList {
        inFlow: 15506920
        time: "2022-06-07 10:44:00"
        timestamp: 1654569840
        superInFlow: -56322820
        bigInFlow: -32338060
        midInFlow: 44214740
        smlInFlow: 59953060
      }
      lastValidTime: "2022-06-07 10:43:44"
    }
    
    inFlow: 0
    

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

`int getCapitalFlow(QotGetCapitalFlow.Request req);`  
`void onReply_GetCapitalFlow(MMAPI_Conn client, int nSerialNo, QotGetCapitalFlow.Response rsp);`

*   **介绍**

获取资金流向

*   **参数**

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	optional int32 periodType = 2; // Qot_Common.PeriodType 周期类型
    	optional string beginTime = 3; // 开始时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
    	optional string endTime = 4; // 结束时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   周期类型参见 [PeriodType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2644)
>     

*   **返回**

    message CapitalFlowItem
    {
    	required double inFlow = 1; // 整体净流入
    	optional string time = 2; //开始时间字符串,以分钟为单位
    	optional double timestamp = 3; //开始时间戳
    	optional double mainInFlow = 4; // 主力大单净流入，仅周期类型不为实时有效
    	optional double superInFlow = 5; // 特大单净流入
    	optional double bigInFlow = 6; // 大单净流入
    	optional double midInFlow = 7; // 中单净流入
    	optional double smlInFlow = 8; // 小单净流入
    }
    
    message S2C
    {
    	repeated CapitalFlowItem flowItemList = 1; //资金流向
    	optional string lastValidTime = 2; //数据最后有效时间字符串
    	optional double lastValidTimestamp = 3; //数据最后有效时间戳
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
            QotGetCapitalFlow.C2S c2s = QotGetCapitalFlow.C2S.newBuilder()
                    .setSecurity(sec)
                .build();
            QotGetCapitalFlow.Request req = QotGetCapitalFlow.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getCapitalFlow(req);
            System.out.printf("Send QotGetCapitalFlow: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetCapitalFlow(MMAPI_Conn client, int nSerialNo, QotGetCapitalFlow.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetCapitalFlow failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetCapitalFlow: %s\n", json);
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

*   **Output**

    Send QotGetCapitalFlow: 2
    Receive QotGetCapitalFlow: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "flowItemList": [{\
          "inFlow": 0.0,\
          "time": "2022-06-08 09:30:00",\
          "timestamp": 1.6546518E9,\
          "superInFlow": 0.0,\
          "bigInFlow": 0.0,\
          "midInFlow": 0.0,\
          "smlInFlow": 0.0\
        }, {\
          "inFlow": -4960940.0,\
          "time": "2022-06-08 09:31:00",\
          "timestamp": 1.65465186E9,\
          "superInFlow": 0.0,\
          "bigInFlow": -4857400.0,\
          "midInFlow": -1.004904E7,\
          "smlInFlow": 9945500.0\
        }, {\
          "inFlow": -3260340.0,\
          "time": "2022-06-08 09:32:00",\
          "timestamp": 1.65465192E9,\
          "superInFlow": -8140120.0,\
          "bigInFlow": -4857400.0,\
          "midInFlow": -7800660.0,\
          "smlInFlow": 1.753784E7\
        }, {\
          "inFlow": 5.715794E7,\
          "time": "2022-06-08 09:33:00",\
          "timestamp": 1.65465198E9,\
          "superInFlow": 3.382238E7,\
          "bigInFlow": 3508240.0,\
          "midInFlow": 337820.0,\
          "smlInFlow": 1.94895E7\
        }, {\
          "inFlow": 6.58145E7,\
          "time": "2022-06-08 09:34:00",\
          "timestamp": 1.65465204E9,\
          "superInFlow": 4.114658E7,\
          "bigInFlow": -1.335444E7,\
          "midInFlow": 1.37937E7,\
          "smlInFlow": 2.422866E7\
        }, {\
          "inFlow": 3.258944E7,\
          "time": "2022-06-08 09:35:00",\
          "timestamp": 1.6546521E9,\
          "superInFlow": 4.114658E7,\
          "bigInFlow": -2.083698E7,\
          "midInFlow": 8570020.0,\
          "smlInFlow": 3709820.0\
        }, \
        //...\
        {\
          "inFlow": 1.4041278E9,\
          "time": "2022-06-08 10:46:00",\
          "timestamp": 1.65465636E9,\
          "superInFlow": 5.3349552E8,\
          "bigInFlow": 4.287602E8,\
          "midInFlow": 2.4805104E8,\
          "smlInFlow": 1.9382104E8\
        }, {\
          "inFlow": 1.38049732E9,\
          "time": "2022-06-08 10:47:00",\
          "timestamp": 1.65465642E9,\
          "superInFlow": 5.1425064E8,\
          "bigInFlow": 4.287602E8,\
          "midInFlow": 2.4901564E8,\
          "smlInFlow": 1.8847084E8\
        }],
        "lastValidTime": "2022-06-08 10:47:25"
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
75  
76  
77  

`moomoo::u32_t GetCapitalFlow(const Qot_GetCapitalFlow::Request &stReq);`  
`virtual void OnReply_GetCapitalFlow(moomoo::u32_t nSerialNo, const Qot_GetCapitalFlow::Response &stRsp) = 0;`

*   **介绍**
    
    获取资金流向
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	optional int32 periodType = 2; // Qot_Common.PeriodType 周期类型
    	optional string beginTime = 3; // 开始时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
    	optional string endTime = 4; // 结束时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   周期类型参见 [PeriodType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2644)
>     

*   **返回**

    message CapitalFlowItem
    {
    	required double inFlow = 1; // 整体净流入
    	optional string time = 2; //开始时间字符串,以分钟为单位
    	optional double timestamp = 3; //开始时间戳
    	optional double mainInFlow = 4; // 主力大单净流入，仅周期类型不为实时有效
    	optional double superInFlow = 5; // 特大单净流入
    	optional double bigInFlow = 6; // 大单净流入
    	optional double midInFlow = 7; // 中单净流入
    	optional double smlInFlow = 8; // 小单净流入
    }
    
    message S2C
    {
    	repeated CapitalFlowItem flowItemList = 1; //资金流向
    	optional string lastValidTime = 2; //数据最后有效时间字符串
    	optional double lastValidTimestamp = 3; //数据最后有效时间戳
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
    
    		// construct request message
    		Qot_GetCapitalFlow::Request req;
    		Qot_GetCapitalFlow::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->set_periodtype(Qot_Common::PeriodType::PeriodType_INTRADAY);
    		m_pQotApi->GetCapitalFlow(req);
    		cout << "GetCapitalFlow" << endl;
    	}
    
    	virtual void OnReply_GetCapitalFlow(moomoo::u32_t nSerialNo, const Qot_GetCapitalFlow::Response &stRsp){
    		cout << "OnReply_GetCapitalFlow:" << endl;
    		// print response
    		// ProtoBufToBodyData and UTF8ToLocal refer to tool.h in Samples
    		string resp_str;
    		ProtoBufToBodyData(stRsp, resp_str);
    		cout << UTF8ToLocal(resp_str) << endl;
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi; 
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

*   **Output**

    connect
    GetCapitalFlow
    OnReply_GetCapitalFlow:
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "flowItemList": [\
       {\
        "inFlow": 0,\
        "time": "2022-06-08 09:30:00",\
        "timestamp": 1654651800,\
        "superInFlow": 0,\
        "bigInFlow": 0,\
        "midInFlow": 0,\
        "smlInFlow": 0\
       },\
       {\
        "inFlow": -4960940,\
        "time": "2022-06-08 09:31:00",\
        "timestamp": 1654651860,\
        "superInFlow": 0,\
        "bigInFlow": -4857400,\
        "midInFlow": -10049040,\
        "smlInFlow": 9945500\
       },\
       {\
        "inFlow": -3260340,\
        "time": "2022-06-08 09:32:00",\
        "timestamp": 1654651920,\
        "superInFlow": -8140120,\
        "bigInFlow": -4857400,\
        "midInFlow": -7800660,\
        "smlInFlow": 17537840\
       },\
    // ...\
       {\
        "inFlow": 1404127800,\
        "time": "2022-06-08 10:46:00",\
        "timestamp": 1654656360,\
        "superInFlow": 533495520,\
        "bigInFlow": 428760200,\
        "midInFlow": 248051040,\
        "smlInFlow": 193821040\
       },\
       {\
        "inFlow": 1387351340,\
        "time": "2022-06-08 10:47:00",\
        "timestamp": 1654656420,\
        "superInFlow": 514250640,\
        "bigInFlow": 428760200,\
        "midInFlow": 253136620,\
        "smlInFlow": 191203880\
       }\
      ],
      "lastValidTime": "2022-06-08 10:47:00"
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

`GetCapitalFlow(req);`

*   **介绍**
    
    获取资金流向
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	optional int32 periodType = 2; // Qot_Common.PeriodType 周期类型
    	optional string beginTime = 3; // 开始时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
    	optional string endTime = 4; // 结束时间（格式：yyyy-MM-dd），仅周期类型不为实时有效
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   周期类型参见 [PeriodType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2644)
>     

*   **返回**

    message CapitalFlowItem
    {
    	required double inFlow = 1; // 整体净流入
    	optional string time = 2; //开始时间字符串,以分钟为单位
    	optional double timestamp = 3; //开始时间戳
    	optional double mainInFlow = 4; // 主力大单净流入，仅周期类型不为实时有效
    	optional double superInFlow = 5; // 特大单净流入
    	optional double bigInFlow = 6; // 大单净流入
    	optional double midInFlow = 7; // 中单净流入
    	optional double smlInFlow = 8; // 小单净流入
    }
    
    message S2C
    {
    	repeated CapitalFlowItem flowItemList = 1; //资金流向
    	optional string lastValidTime = 2; //数据最后有效时间字符串
    	optional double lastValidTimestamp = 3; //数据最后有效时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { Common, Qot_Common } from "fmoomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetCapitalFlow(){
        const { RetType } = Common
        const { PeriodType, QotMarket } = Qot_Common
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
                        periodType: PeriodType.PeriodType_INTRADAY,
                    },
                };
                websocket.GetCapitalFlow(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("CapitalFlow: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    CapitalFlow: errCode 0, retMsg , retType 0
    {
      "flowItemList": [{\
        "inFlow": -4960940,\
        "time": "2022-06-08 09:30:00",\
        "timestamp": 1654651800,\
        "superInFlow": 0.0,\
        "bigInFlow": -4857400.0,\
        "midInFlow": -1004904.0,\
        "smlInFlow": 9945500.0\
      }, ..., {\
        "inFlow": 944752820,\
        "time": "2022-06-08 10:24:00",\
        "timestamp": 1654655040,\
        "superInFlow": 53349552.0,\
        "bigInFlow": 4287602.0,\
        "midInFlow": 24805104.0,\
        "smlInFlow": 19382104.0\
      }],
      "lastValidTime": "2022-06-08 10:24:00"
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

接口限制

*   每 30 秒内最多请求 30 次获取资金流向接口。
*   仅支持正股、窝轮和基金。
*   历史周期（日、月、年）仅提供最近 1 年数据；实时周期仅提供最新一天的数据。
*   返回数据只包括盘中数据，不包含盘前盘后数据。

← [获取标的市场状态](https://openapi.futunn.com/futu-api-doc/quote/get-market-state.html) [获取资金分布](https://openapi.futunn.com/futu-api-doc/quote/get-capital-distribution.html)
 →