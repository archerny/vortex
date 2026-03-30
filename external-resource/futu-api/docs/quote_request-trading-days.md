 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/request-trading-days.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/request-trading-days.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/request-trading-days.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/request-trading-days.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
    *   [行情接口总览](https://openapi.futunn.com/futu-api-doc/quote/overview.html)
        
    *   [行情对象](https://openapi.futunn.com/futu-api-doc/quote/base.html)
        
    *   实时行情
        
    *   基本数据
        
    *   相关衍生品
        
    *   全市场筛选
        
        *   [条件选股](https://openapi.futunn.com/futu-api-doc/quote/get-stock-filter.html)
            
        *   [获取板块内股票列表](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html)
            
        *   [获取板块列表](https://openapi.futunn.com/futu-api-doc/quote/get-plate-list.html)
            
        *   [获取静态数据](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html)
            
        *   [获取 IPO 信息](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html)
            
        *   [获取全局市场状态](https://openapi.futunn.com/futu-api-doc/quote/get-global-state.html)
            
        *   [获取交易日历](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html)
            
        
    *   个性化
        
    *   [行情定义](https://openapi.futunn.com/futu-api-doc/quote/quote.html)
        
    
*   交易接口
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html#2298)
 获取交易日历
=========================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`request_trading_days(market=None, start=None, end=None, code=None)`

*   **介绍**
    
    请求指定市场 / 指定标的的交易日历。  
    注意：该交易日是通过自然日剔除周末和节假日得到，未剔除临时休市数据。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | market | [TradeDateMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#940) | 市场类型 |
    | start | str | 起始日期<br>(ℹ️ 格式：yyyy-MM-dd  <br>例如：“2018-01-01”) |
    | end | str | 结束日期<br>(ℹ️ 格式：yyyy-MM-dd  <br>例如：“2018-01-01”) |
    | code | str | 股票代码 |
    
    注：当 market 和 code 同时存在时，会忽略 market，仅对 code 进行查询。
    
    *   start 和 end 的组合如下
        
        | Start 类型 | End 类型 | 说明  |
        | --- | --- | --- |
        | str | str | start 和 end 分别为指定的日期 |
        | None | str | start 为 end 往前 365 天 |
        | str | None | end 为 start 往后 365 天 |
        | None | None | start 为往前 365 天，end 当前日期 |
        

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | list | 当 ret == RET\_OK 时，返回交易日数据。list 中元素类型为 dict |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   交易日数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | time | str | 时间<br>(ℹ️ 格式：yyyy-MM-dd) |
        | trade\_date\_type | [TradeDateType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6676) | 交易日类型 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.request_trading_days(market=TradeDateMarket.HK, start='2020-04-01', end='2020-04-10')
    if ret == RET_OK:
        print('HK market calendar:', data)
    else:
        print('error:', data)
    print('******************************************')
    ret, data = quote_ctx.request_trading_days(start='2020-04-01', end='2020-04-10', code='HK.00700')
    if ret == RET_OK:
        print('HK.00700 calendar:', data)
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
12  
13  
14  
15  

*   **Output**

    HK market calendar: [{'time': '2020-04-01', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-02', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-03', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-06', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-07', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-08', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-09', 'trade_date_type': 'WHOLE'}]
    ******************************************
    HK.00700 calendar: [{'time': '2020-04-01', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-02', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-03', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-06', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-07', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-08', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-09', 'trade_date_type': 'WHOLE'}]
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html#6520)
 Qot\_RequestTradeDate.proto
--------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    请求指定市场 / 指定标的的交易日历。  
    注意：该交易日是通过自然日剔除周末和节假日得到，未剔除临时休市数据。
    
*   **参数**
    

    message C2S
    {
    	//当 market 和 security 同时存在时，会忽略 market，仅对 security 进行查询。
        required int32 market = 1; //Qot_Common.TradeDateMarket，要查询的市场
    	required string beginTime = 2; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 3; //结束时间字符串（格式：yyyy-MM-dd）
        optional Qot_Common.Security security = 4; // 指定标的
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   交易日市场类型枚举参见 [TradeDateMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#940)
>     

*   **返回**

    message TradeDate
    {
    	required string time = 1; //时间字符串（格式：yyyy-MM-dd）
    	optional double timestamp = 2; //时间戳
    	optional int32 tradeDateType = 3; //Qot_Common.TradeDateType，交易时间类型
    }
    
    message S2C
    {
    	repeated TradeDate tradeDateList = 1; //交易日，注意该交易日是通过自然日去除周末以及节假日得到，不包括临时休市数据。
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
14  
15  
16  
17  
18  
19  
20  

> *   交易日类型枚举参见 [TradeDateType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6676)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3219
    

`uint RequestTradeDate(QotRequestTradeDate.Request req);`  
`virtual void OnReply_RequestTradeDate(FTAPI_Conn client, uint nSerialNo, QotRequestTradeDate.Response rsp);`

*   **介绍**
    
    请求指定市场 / 指定标的的交易日历。  
    注意：该交易日是通过自然日剔除周末和节假日得到，未剔除临时休市数据。
    
*   **参数**
    

    message C2S
    {
    	//当 market 和 security 同时存在时，会忽略 market，仅对 security 进行查询。
        required int32 market = 1; //Qot_Common.TradeDateMarket，要查询的市场
    	required string beginTime = 2; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 3; //结束时间字符串（格式：yyyy-MM-dd）
        optional Qot_Common.Security security = 4; // 指定标的
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   交易日市场类型枚举参见 [TradeDateMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#940)
>     

*   **返回**

    message TradeDate
    {
    	required string time = 1; //时间字符串（格式：yyyy-MM-dd）
    	optional double timestamp = 2; //时间戳
    	optional int32 tradeDateType = 3; //Qot_Common.TradeDateType，交易时间类型
    }
    
    message S2C
    {
    	repeated TradeDate tradeDateList = 1; //交易日，注意该交易日是通过自然日去除周末以及节假日得到，不包括临时休市数据。
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
14  
15  
16  
17  
18  
19  
20  

> *   交易日类型枚举参见 [TradeDateType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6676)
>     
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
            QotRequestTradeDate.C2S c2s = QotRequestTradeDate.C2S.CreateBuilder()
                .SetMarket((int)QotCommon.TradeDateMarket.TradeDateMarket_HK)
                .SetBeginTime("2021-07-01")
                .SetEndTime("2021-07-05")
                .SetSecurity(sec)
                .Build();
            QotRequestTradeDate.Request req = QotRequestTradeDate.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.RequestTradeDate(req);
            Console.Write("Send QotRequestTradeDate: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_RequestTradeDate(FTAPI_Conn client, uint nSerialNo, QotRequestTradeDate.Response rsp) {
            Console.Write("Reply: QotRequestTradeDate: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("time: {0}, tradeDateType: {1} \n", rsp.S2C.TradeDateListList[0].Time, rsp.S2C.TradeDateListList[0].TradeDateType);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826779607624163711
    Send QotRequestTradeDate: 3
    Reply: QotRequestTradeDate: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      tradeDateList {
        time: "2021-07-02"
        timestamp: 1625155200
        tradeDateType: 0
      }
      tradeDateList {
        time: "2021-07-05"
        timestamp: 1625414400
        tradeDateType: 0
      }
    }
    
    time: 2021-07-02, tradeDateType: 0
    

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

`int requestTradeDate(QotRequestTradeDate.Request req);`  
`void onReply_RequestTradeDate(FTAPI_Conn client, int nSerialNo, QotRequestTradeDate.Response rsp);`

*   **介绍**
    
    请求指定市场 / 指定标的的交易日历。  
    注意：该交易日是通过自然日剔除周末和节假日得到，未剔除临时休市数据。
    
*   **参数**
    

    message C2S
    {
    	//当 market 和 security 同时存在时，会忽略 market，仅对 security 进行查询。
        required int32 market = 1; //Qot_Common.TradeDateMarket，要查询的市场
    	required string beginTime = 2; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 3; //结束时间字符串（格式：yyyy-MM-dd）
        optional Qot_Common.Security security = 4; // 指定标的
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   交易日市场类型枚举参见 [TradeDateMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#940)
>     

*   **返回**

    message TradeDate
    {
    	required string time = 1; //时间字符串（格式：yyyy-MM-dd）
    	optional double timestamp = 2; //时间戳
    	optional int32 tradeDateType = 3; //Qot_Common.TradeDateType，交易时间类型
    }
    
    message S2C
    {
    	repeated TradeDate tradeDateList = 1; //交易日，注意该交易日是通过自然日去除周末以及节假日得到，不包括临时休市数据。
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
14  
15  
16  
17  
18  
19  
20  

> *   交易日类型枚举参见 [TradeDateType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6676)
>     
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
            QotRequestTradeDate.C2S c2s = QotRequestTradeDate.C2S.newBuilder()
                .setMarket(QotCommon.TradeDateMarket.TradeDateMarket_HK)
                .setBeginTime("2020-08-01")
                .setEndTime("2020-09-01")
                .setSecurity(sec)
                .build();
            QotRequestTradeDate.Request req = QotRequestTradeDate.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.requestTradeDate(req);
            System.out.printf("Send QotRequestTradeDate: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_RequestTradeDate(FTAPI_Conn client, int nSerialNo, QotRequestTradeDate.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotRequestTradeDate failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotRequestTradeDate: %s\n", json);
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

*   **Output**

    Send QotRequestTradeDate: 2
    Receive QotRequestTradeDate: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "tradeDateList": [{\
          "time": "2020-08-03",\
          "timestamp": 1.596384E9,\
          "tradeDateType": 0\
        }, ... {\
          "time": "2020-09-01",\
          "timestamp": 1.5988896E9,\
          "tradeDateType": 0\
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

`Futu::u32_t RequestTradeDate(const Qot_RequestTradeDate::Request &stReq);`  
`virtual void OnReply_RequestTradeDate(Futu::u32_t nSerialNo, const Qot_RequestTradeDate::Response &stRsp) = 0;`

*   **介绍**
    
    请求指定市场 / 指定标的的交易日历。  
    注意：该交易日是通过自然日剔除周末和节假日得到，未剔除临时休市数据。
    
*   **参数**
    

    message C2S
    {
    	//当 market 和 security 同时存在时，会忽略 market，仅对 security 进行查询。
        required int32 market = 1; //Qot_Common.TradeDateMarket，要查询的市场
    	required string beginTime = 2; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 3; //结束时间字符串（格式：yyyy-MM-dd）
        optional Qot_Common.Security security = 4; // 指定标的
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   交易日市场类型枚举参见 [TradeDateMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#940)
>     

*   **返回**

    message TradeDate
    {
    	required string time = 1; //时间字符串（格式：yyyy-MM-dd）
    	optional double timestamp = 2; //时间戳
    	optional int32 tradeDateType = 3; //Qot_Common.TradeDateType，交易时间类型
    }
    
    message S2C
    {
    	repeated TradeDate tradeDateList = 1; //交易日，注意该交易日是通过自然日去除周末以及节假日得到，不包括临时休市数据。
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
14  
15  
16  
17  
18  
19  
20  

> *   交易日类型枚举参见 [TradeDateType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6676)
>     
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
    
    		// 组包
    		Qot_RequestTradeDate::Request req;
    		Qot_RequestTradeDate::C2S *c2s = req.mutable_c2s();
    		c2s->set_market((int)Qot_Common::TradeDateMarket::TradeDateMarket_HK);
    		c2s->set_begintime("2021-07-01");
    		c2s->set_endtime("2021-07-05");
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market((int)Qot_Common::QotMarket::QotMarket_HK_Security);
            
            m_RequestTradeDateSerialNo = m_pQotApi->RequestTradeDate(req);
            cout << "Request RequestTradeDate SerialNo: " << m_RequestTradeDateSerialNo << endl;
    	}
    
    	virtual void OnReply_RequestTradeDate(Futu::u32_t nSerialNo, const Qot_RequestTradeDate::Response &stRsp){
            if(nSerialNo == m_RequestTradeDateSerialNo)
            {
                cout << "OnReply_RequestTradeDate SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
        
        Futu::u32_t m_RequestTradeDateSerialNo;
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
    Request RequestTradeDate SerialNo: 3
    OnReply_RequestTradeDate SerialNo: 3
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "tradeDateList": [\
       {\
        "time": "2021-07-02",\
        "timestamp": 1625155200,\
        "tradeDateType": 0\
       },\
       {\
        "time": "2021-07-05",\
        "timestamp": 1625414400,\
        "tradeDateType": 0\
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

`RequestTradeDate(req);`

*   **介绍**
    
    请求指定市场 / 指定标的的交易日历。  
    注意：该交易日是通过自然日剔除周末和节假日得到，未剔除临时休市数据。
    
*   **参数**
    

    message C2S
    {
    	//当 market 和 security 同时存在时，会忽略 market，仅对 security 进行查询。
        required int32 market = 1; //Qot_Common.TradeDateMarket，要查询的市场
    	required string beginTime = 2; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 3; //结束时间字符串（格式：yyyy-MM-dd）
        optional Qot_Common.Security security = 4; // 指定标的
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   交易日市场类型枚举参见 [TradeDateMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#940)
>     

*   **返回**

    message TradeDate
    {
    	required string time = 1; //时间字符串（格式：yyyy-MM-dd）
    	optional double timestamp = 2; //时间戳
    	optional int32 tradeDateType = 3; //Qot_Common.TradeDateType，交易时间类型
    }
    
    message S2C
    {
    	repeated TradeDate tradeDateList = 1; //交易日，注意该交易日是通过自然日去除周末以及节假日得到，不包括临时休市数据。
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
14  
15  
16  
17  
18  
19  
20  

> *   交易日类型枚举参见 [TradeDateType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6676)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotRequestTradeDate(){
        const { RetType } = Common
        const { TradeDateMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        market: TradeDateMarket.TradeDateMarket_HK,
                        beginTime: "2021-08-05",
                        endTime: "2021-08-10",
                    },
                };
    
                websocket.RequestTradeDate(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("TradeDate: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    TradeDate: errCode 0, retMsg , retType 0
    {
      "tradeDateList": [{\
        "time": "2021-08-05",\
        "timestamp": 1628092800,\
        "tradeDateType": 0\
      }, {\
        "time": "2021-08-06",\
        "timestamp": 1628179200,\
        "tradeDateType": 0\
      }, {\
        "time": "2021-08-09",\
        "timestamp": 1628438400,\
        "tradeDateType": 0\
      }, {\
        "time": "2021-08-10",\
        "timestamp": 1628524800,\
        "tradeDateType": 0\
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

接口限制

*   每 30 秒内最多请求 30 次获取交易日接口。
*   历史交易日历提供过去 10 年的数据，未来交易日历提供到今年 12 月 31 日
    (ℹ️ 举例：今天的日期是 2021 年 7 月 6 日，我们仅提供 2011-07-06 到 2021-12-31 期间的交易日历)
    
    。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`request_trading_days(market=None, start=None, end=None, code=None)`

*   **介绍**
    
    请求指定市场 / 指定标的的交易日历。  
    注意：该交易日是通过自然日剔除周末和节假日得到，未剔除临时休市数据。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | market | [TradeDateMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#940) | 市场类型 |
    | start | str | 起始日期<br>(ℹ️ 格式：yyyy-MM-dd  <br>例如：“2018-01-01”) |
    | end | str | 结束日期<br>(ℹ️ 格式：yyyy-MM-dd  <br>例如：“2018-01-01”) |
    | code | str | 股票代码 |
    
    注：当 market 和 code 同时存在时，会忽略 market，仅对 code 进行查询。
    
    *   start 和 end 的组合如下
        
        | Start 类型 | End 类型 | 说明  |
        | --- | --- | --- |
        | str | str | start 和 end 分别为指定的日期 |
        | None | str | start 为 end 往前 365 天 |
        | str | None | end 为 start 往后 365 天 |
        | None | None | start 为往前 365 天，end 当前日期 |
        

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | list | 当 ret == RET\_OK 时，返回交易日数据。list 中元素类型为 dict |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   交易日数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | time | str | 时间<br>(ℹ️ 格式：yyyy-MM-dd) |
        | trade\_date\_type | [TradeDateType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6676) | 交易日类型 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.request_trading_days(market=TradeDateMarket.HK, start='2020-04-01', end='2020-04-10')
    if ret == RET_OK:
        print('HK market calendar:', data)
    else:
        print('error:', data)
    print('******************************************')
    ret, data = quote_ctx.request_trading_days(start='2020-04-01', end='2020-04-10', code='HK.00700')
    if ret == RET_OK:
        print('HK.00700 calendar:', data)
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
12  
13  
14  
15  

*   **Output**

    HK market calendar: [{'time': '2020-04-01', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-02', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-03', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-06', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-07', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-08', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-09', 'trade_date_type': 'WHOLE'}]
    ******************************************
    HK.00700 calendar: [{'time': '2020-04-01', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-02', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-03', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-06', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-07', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-08', 'trade_date_type': 'WHOLE'}, {'time': '2020-04-09', 'trade_date_type': 'WHOLE'}]
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html#6520-2)
 Qot\_RequestTradeDate.proto
----------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    请求指定市场 / 指定标的的交易日历。  
    注意：该交易日是通过自然日剔除周末和节假日得到，未剔除临时休市数据。
    
*   **参数**
    

    message C2S
    {
    	//当 market 和 security 同时存在时，会忽略 market，仅对 security 进行查询。
        required int32 market = 1; //Qot_Common.TradeDateMarket，要查询的市场
    	required string beginTime = 2; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 3; //结束时间字符串（格式：yyyy-MM-dd）
        optional Qot_Common.Security security = 4; // 指定标的
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   交易日市场类型枚举参见 [TradeDateMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#940)
>     

*   **返回**

    message TradeDate
    {
    	required string time = 1; //时间字符串（格式：yyyy-MM-dd）
    	optional double timestamp = 2; //时间戳
    	optional int32 tradeDateType = 3; //Qot_Common.TradeDateType，交易时间类型
    }
    
    message S2C
    {
    	repeated TradeDate tradeDateList = 1; //交易日，注意该交易日是通过自然日去除周末以及节假日得到，不包括临时休市数据。
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
14  
15  
16  
17  
18  
19  
20  

> *   交易日类型枚举参见 [TradeDateType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6676)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3219
    

`uint RequestTradeDate(QotRequestTradeDate.Request req);`  
`virtual void OnReply_RequestTradeDate(MMAPI_Conn client, uint nSerialNo, QotRequestTradeDate.Response rsp);`

*   **介绍**
    
    请求指定市场 / 指定标的的交易日历。  
    注意：该交易日是通过自然日剔除周末和节假日得到，未剔除临时休市数据。
    
*   **参数**
    

    message C2S
    {
    	//当 market 和 security 同时存在时，会忽略 market，仅对 security 进行查询。
        required int32 market = 1; //Qot_Common.TradeDateMarket，要查询的市场
    	required string beginTime = 2; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 3; //结束时间字符串（格式：yyyy-MM-dd）
        optional Qot_Common.Security security = 4; // 指定标的
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   交易日市场类型枚举参见 [TradeDateMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#940)
>     

*   **返回**

    message TradeDate
    {
    	required string time = 1; //时间字符串（格式：yyyy-MM-dd）
    	optional double timestamp = 2; //时间戳
    	optional int32 tradeDateType = 3; //Qot_Common.TradeDateType，交易时间类型
    }
    
    message S2C
    {
    	repeated TradeDate tradeDateList = 1; //交易日，注意该交易日是通过自然日去除周末以及节假日得到，不包括临时休市数据。
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
14  
15  
16  
17  
18  
19  
20  

> *   交易日类型枚举参见 [TradeDateType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6676)
>     
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
            QotRequestTradeDate.C2S c2s = QotRequestTradeDate.C2S.CreateBuilder()
                .SetMarket((int)QotCommon.TradeDateMarket.TradeDateMarket_HK)
                .SetBeginTime("2021-07-01")
                .SetEndTime("2021-07-05")
                .SetSecurity(sec)
                .Build();
            QotRequestTradeDate.Request req = QotRequestTradeDate.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.RequestTradeDate(req);
            Console.Write("Send QotRequestTradeDate: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_RequestTradeDate(MMAPI_Conn client, uint nSerialNo, QotRequestTradeDate.Response rsp) {
            Console.Write("Reply: QotRequestTradeDate: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("time: {0}, tradeDateType: {1} \n", rsp.S2C.TradeDateListList[0].Time, rsp.S2C.TradeDateListList[0].TradeDateType);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826779607624163711
    Send QotRequestTradeDate: 3
    Reply: QotRequestTradeDate: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      tradeDateList {
        time: "2021-07-02"
        timestamp: 1625155200
        tradeDateType: 0
      }
      tradeDateList {
        time: "2021-07-05"
        timestamp: 1625414400
        tradeDateType: 0
      }
    }
    
    time: 2021-07-02, tradeDateType: 0
    

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

`int requestTradeDate(QotRequestTradeDate.Request req);`  
`void onReply_RequestTradeDate(MMAPI_Conn client, int nSerialNo, QotRequestTradeDate.Response rsp);`

*   **介绍**
    
    请求指定市场 / 指定标的的交易日历。  
    注意：该交易日是通过自然日剔除周末和节假日得到，未剔除临时休市数据。
    
*   **参数**
    

    message C2S
    {
    	//当 market 和 security 同时存在时，会忽略 market，仅对 security 进行查询。
        required int32 market = 1; //Qot_Common.TradeDateMarket，要查询的市场
    	required string beginTime = 2; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 3; //结束时间字符串（格式：yyyy-MM-dd）
        optional Qot_Common.Security security = 4; // 指定标的
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   交易日市场类型枚举参见 [TradeDateMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#940)
>     

*   **返回**

    message TradeDate
    {
    	required string time = 1; //时间字符串（格式：yyyy-MM-dd）
    	optional double timestamp = 2; //时间戳
    	optional int32 tradeDateType = 3; //Qot_Common.TradeDateType，交易时间类型
    }
    
    message S2C
    {
    	repeated TradeDate tradeDateList = 1; //交易日，注意该交易日是通过自然日去除周末以及节假日得到，不包括临时休市数据。
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
14  
15  
16  
17  
18  
19  
20  

> *   交易日类型枚举参见 [TradeDateType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6676)
>     
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
            QotRequestTradeDate.C2S c2s = QotRequestTradeDate.C2S.newBuilder()
                .setMarket(QotCommon.TradeDateMarket.TradeDateMarket_HK)
                .setBeginTime("2020-08-01")
                .setEndTime("2020-09-01")
                .setSecurity(sec)
                .build();
            QotRequestTradeDate.Request req = QotRequestTradeDate.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.requestTradeDate(req);
            System.out.printf("Send QotRequestTradeDate: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_RequestTradeDate(MMAPI_Conn client, int nSerialNo, QotRequestTradeDate.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotRequestTradeDate failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotRequestTradeDate: %s\n", json);
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

*   **Output**

    Send QotRequestTradeDate: 2
    Receive QotRequestTradeDate: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "tradeDateList": [{\
          "time": "2020-08-03",\
          "timestamp": 1.596384E9,\
          "tradeDateType": 0\
        }, ... {\
          "time": "2020-09-01",\
          "timestamp": 1.5988896E9,\
          "tradeDateType": 0\
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

`moomoo::u32_t RequestTradeDate(const Qot_RequestTradeDate::Request &stReq);`  
`virtual void OnReply_RequestTradeDate(moomoo::u32_t nSerialNo, const Qot_RequestTradeDate::Response &stRsp) = 0;`

*   **介绍**
    
    请求指定市场 / 指定标的的交易日历。  
    注意：该交易日是通过自然日剔除周末和节假日得到，未剔除临时休市数据。
    
*   **参数**
    

    message C2S
    {
    	//当 market 和 security 同时存在时，会忽略 market，仅对 security 进行查询。
        required int32 market = 1; //Qot_Common.TradeDateMarket，要查询的市场
    	required string beginTime = 2; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 3; //结束时间字符串（格式：yyyy-MM-dd）
        optional Qot_Common.Security security = 4; // 指定标的
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   交易日市场类型枚举参见 [TradeDateMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#940)
>     

*   **返回**

    message TradeDate
    {
    	required string time = 1; //时间字符串（格式：yyyy-MM-dd）
    	optional double timestamp = 2; //时间戳
    	optional int32 tradeDateType = 3; //Qot_Common.TradeDateType，交易时间类型
    }
    
    message S2C
    {
    	repeated TradeDate tradeDateList = 1; //交易日，注意该交易日是通过自然日去除周末以及节假日得到，不包括临时休市数据。
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
14  
15  
16  
17  
18  
19  
20  

> *   交易日类型枚举参见 [TradeDateType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6676)
>     
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
    
    		// 组包
    		Qot_RequestTradeDate::Request req;
    		Qot_RequestTradeDate::C2S *c2s = req.mutable_c2s();
    		c2s->set_market((int)Qot_Common::TradeDateMarket::TradeDateMarket_HK);
    		c2s->set_begintime("2021-07-01");
    		c2s->set_endtime("2021-07-05");
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market((int)Qot_Common::QotMarket::QotMarket_HK_Security);
            
            m_RequestTradeDateSerialNo = m_pQotApi->RequestTradeDate(req);
            cout << "Request RequestTradeDate SerialNo: " << m_RequestTradeDateSerialNo << endl;
    	}
    
    	virtual void OnReply_RequestTradeDate(moomoo::u32_t nSerialNo, const Qot_RequestTradeDate::Response &stRsp){
            if(nSerialNo == m_RequestTradeDateSerialNo)
            {
                cout << "OnReply_RequestTradeDate SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
        
        moomoo::u32_t m_RequestTradeDateSerialNo;
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
    Request RequestTradeDate SerialNo: 3
    OnReply_RequestTradeDate SerialNo: 3
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "tradeDateList": [\
       {\
        "time": "2021-07-02",\
        "timestamp": 1625155200,\
        "tradeDateType": 0\
       },\
       {\
        "time": "2021-07-05",\
        "timestamp": 1625414400,\
        "tradeDateType": 0\
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

`RequestTradeDate(req);`

*   **介绍**
    
    请求指定市场 / 指定标的的交易日历。  
    注意：该交易日是通过自然日剔除周末和节假日得到，未剔除临时休市数据。
    
*   **参数**
    

    message C2S
    {
    	//当 market 和 security 同时存在时，会忽略 market，仅对 security 进行查询。
        required int32 market = 1; //Qot_Common.TradeDateMarket，要查询的市场
    	required string beginTime = 2; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 3; //结束时间字符串（格式：yyyy-MM-dd）
        optional Qot_Common.Security security = 4; // 指定标的
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   交易日市场类型枚举参见 [TradeDateMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#940)
>     

*   **返回**

    message TradeDate
    {
    	required string time = 1; //时间字符串（格式：yyyy-MM-dd）
    	optional double timestamp = 2; //时间戳
    	optional int32 tradeDateType = 3; //Qot_Common.TradeDateType，交易时间类型
    }
    
    message S2C
    {
    	repeated TradeDate tradeDateList = 1; //交易日，注意该交易日是通过自然日去除周末以及节假日得到，不包括临时休市数据。
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
14  
15  
16  
17  
18  
19  
20  

> *   交易日类型枚举参见 [TradeDateType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6676)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotRequestTradeDate(){
        const { RetType } = Common
        const { TradeDateMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        market: TradeDateMarket.TradeDateMarket_HK,
                        beginTime: "2021-08-05",
                        endTime: "2021-08-10",
                    },
                };
    
                websocket.RequestTradeDate(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("TradeDate: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    TradeDate: errCode 0, retMsg , retType 0
    {
      "tradeDateList": [{\
        "time": "2021-08-05",\
        "timestamp": 1628092800,\
        "tradeDateType": 0\
      }, {\
        "time": "2021-08-06",\
        "timestamp": 1628179200,\
        "tradeDateType": 0\
      }, {\
        "time": "2021-08-09",\
        "timestamp": 1628438400,\
        "tradeDateType": 0\
      }, {\
        "time": "2021-08-10",\
        "timestamp": 1628524800,\
        "tradeDateType": 0\
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

接口限制

*   每 30 秒内最多请求 30 次获取交易日接口。
*   历史交易日历提供过去 10 年的数据，未来交易日历提供到今年 12 月 31 日
    (ℹ️ 举例：今天的日期是 2021 年 7 月 6 日，我们仅提供 2011-07-06 到 2021-12-31 期间的交易日历)
    
    。

← [获取全局市场状态](https://openapi.futunn.com/futu-api-doc/quote/get-global-state.html) [获取历史 K 线额度使用明细](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html)
 →

[获取交易日历](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html)