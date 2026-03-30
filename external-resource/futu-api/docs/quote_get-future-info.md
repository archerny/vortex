 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-future-info.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-future-info.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-future-info.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-future-info.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
    *   [行情接口总览](https://openapi.futunn.com/futu-api-doc/quote/overview.html)
        
    *   [行情对象](https://openapi.futunn.com/futu-api-doc/quote/base.html)
        
    *   实时行情
        
    *   基本数据
        
    *   相关衍生品
        
        *   [获取期权链到期日](https://openapi.futunn.com/futu-api-doc/quote/get-option-expiration-date.html)
            
        *   [获取期权链](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html)
            
        *   [筛选窝轮](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html)
            
        *   [获取窝轮和期货列表](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html)
            
        *   [获取期货合约资料](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html)
            
        
    *   全市场筛选
        
    *   个性化
        
    *   [行情定义](https://openapi.futunn.com/futu-api-doc/quote/quote.html)
        
    
*   交易接口
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html#7447)
 获取期货合约资料
======================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_future_info(code_list)`

*   **介绍**
    
    获取期货合约资料
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 股票代码列表<br>(ℹ️ list 中元素类型是 str) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回期货合约资料数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   期货合约资料数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | owner | str | 标的  |
        | exchange | str | 交易所 |
        | type | str | 合约类型 |
        | size | float | 合约规模 |
        | size\_unit | str | 合约规模单位 |
        | price\_currency | str | 报价货币 |
        | price\_unit | str | 报价单位 |
        | min\_change | float | 最小变动 |
        | min\_change\_unit | str | 最小变动的单位<br>(ℹ️ 该字段已废弃) |
        | trade\_time | str | 交易时间 |
        | time\_zone | str | 时区  |
        | last\_trade\_time | str | 最后交易时间<br>(ℹ️ 主连，当月，下月等期货没有该字段) |
        | exchange\_format\_url | str | 交易所规格链接 url |
        | origin\_code | str | 实际合约代码 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_future_info(["HK.MPImain", "HK.HAImain"])
    if ret == RET_OK:
        print(data)
        print(data['code'][0])    # 取第一条的股票代码
        print(data['code'].values.tolist())   # 转为 list
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

        code      name       owner exchange  type     size size_unit price_currency price_unit  min_change min_change_unit                        trade_time time_zone last_trade_time                                exchange_format_url           origin_code
    0  HK.MPImain   內房期货主连  恒生中国内地地产指数      港交所  股指期货     50.0    指数点×港元             港元        指数点        0.50                (09:15 - 12:00), (13:00 - 16:30)       CCT                  https://sc.hkex.com.hk/TuniS/www.hkex.com.hk/P...           HK.MPI2112
    1  HK.HAImain   海通证券期货主连    HK.06837      港交所  股票期货  10000.0         股             港元      每股/港元        0.01                   (09:30 - 12:00), (13:00 - 16:00)       CCT                  https://sc.hkex.com.hk/TuniS/www.hkex.com.hk/P...           HK.HAI2112
    HK.MPImain
    ['HK.MPImain', 'HK.HAImain']
    

1  
2  
3  
4  
5  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html#8538)
 Qot\_GetFutureInfo.proto
------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取期货合约资料
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    //交易时间
    message TradeTime
    {
        optional double begin = 1; // 开始时间，以分钟为单位
    	optional double end = 2; // 结束时间，以分钟为单位
    }
    
    //期货合约资料的列表
    message FutureInfo
    {
    	required string name = 1; // 合约名称
    	required Qot_Common.Security security = 2; // 合约代码
    	required string lastTradeTime = 3; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 4; //最后交易日时间戳，只有非主连期货合约才有该字段
    	optional Qot_Common.Security owner = 5; //标的股 股票期货和股指期货才有该字段
    	required string ownerOther = 6; //标的 
    	required string exchange = 7; //交易所
    	required string contractType = 8; //合约类型
    	required double contractSize = 9; //合约规模
    	required string contractSizeUnit = 10; //合约规模的单位
    	required string quoteCurrency = 11; //报价货币
    	required double minVar = 12; //最小变动单位
    	required string minVarUnit = 13; //最小变动单位的单位（该字段已废弃）
    	optional string quoteUnit = 14; //报价单位
    	repeated TradeTime tradeTime = 15; //交易时间
    	required string timeZone = 16; //所在时区
    	required string exchangeFormatUrl = 17; //交易所规格
        optional Qot_Common.Security origin = 18; //实际合约代码
    }
    
    message S2C
    {
    	repeated FutureInfo futureInfoList = 1; //期货合约资料的列表
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3218
    

`uint GetFutureInfo(QotGetFutureInfo.Request req);`  
`virtual void OnReply_GetFutureInfo(FTAPI_Conn client, uint nSerialNo, QotGetFutureInfo.Response rsp);`

*   **介绍**

获取期货合约资料

*   **参数**

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    //交易时间
    message TradeTime
    {
        optional double begin = 1; // 开始时间，以分钟为单位
    	optional double end = 2; // 结束时间，以分钟为单位
    }
    
    //期货合约资料的列表
    message FutureInfo
    {
    	required string name = 1; // 合约名称
    	required Qot_Common.Security security = 2; // 合约代码
    	required string lastTradeTime = 3; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 4; //最后交易日时间戳，只有非主连期货合约才有该字段
    	optional Qot_Common.Security owner = 5; //标的股 股票期货和股指期货才有该字段
    	required string ownerOther = 6; //标的 
    	required string exchange = 7; //交易所
    	required string contractType = 8; //合约类型
    	required double contractSize = 9; //合约规模
    	required string contractSizeUnit = 10; //合约规模的单位
    	required string quoteCurrency = 11; //报价货币
    	required double minVar = 12; //最小变动单位
    	required string minVarUnit = 13; //最小变动单位的单位（该字段已废弃）
    	optional string quoteUnit = 14; //报价单位
    	repeated TradeTime tradeTime = 15; //交易时间
    	required string timeZone = 16; //所在时区
    	required string exchangeFormatUrl = 17; //交易所规格
        optional Qot_Common.Security origin = 18; //实际合约代码
    }
    
    message S2C
    {
    	repeated FutureInfo futureInfoList = 1; //期货合约资料的列表
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
                    .SetCode("HSImain")
                    .Build();
            QotGetFutureInfo.C2S c2s = QotGetFutureInfo.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                .Build();
            QotGetFutureInfo.Request req = QotGetFutureInfo.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetFutureInfo(req);
            Console.Write("Send QotGetFutureInfo: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetFutureInfo(FTAPI_Conn client, uint nSerialNo, QotGetFutureInfo.Response rsp)
        {
            Console.Write("Reply: QotGetFutureInfo: {0}\n", nSerialNo);
            Console.Write("name: {0}, exchange: {1} \n", rsp.S2C.FutureInfoListList[0].Name,
                rsp.S2C.FutureInfoListList[0].Exchange);
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

    Qot onInitConnect: ret=0 desc= connID=6825719160020953581
    Send QotGetFutureInfo: 3
    Reply: QotGetFutureInfo: 3
    name: 恒指主连(2108), exchange: 港交所
    

1  
2  
3  
4  

`int getFutureInfo(QotGetFutureInfo.Request req);`  
`void onReply_GetFutureInfo(FTAPI_Conn client, int nSerialNo, QotGetFutureInfo.Response rsp);`

*   **介绍**

获取期货合约资料

*   **参数**

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    //交易时间
    message TradeTime
    {
        optional double begin = 1; // 开始时间，以分钟为单位
    	optional double end = 2; // 结束时间，以分钟为单位
    }
    
    //期货合约资料的列表
    message FutureInfo
    {
    	required string name = 1; // 合约名称
    	required Qot_Common.Security security = 2; // 合约代码
    	required string lastTradeTime = 3; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 4; //最后交易日时间戳，只有非主连期货合约才有该字段
    	optional Qot_Common.Security owner = 5; //标的股 股票期货和股指期货才有该字段
    	required string ownerOther = 6; //标的 
    	required string exchange = 7; //交易所
    	required string contractType = 8; //合约类型
    	required double contractSize = 9; //合约规模
    	required string contractSizeUnit = 10; //合约规模的单位
    	required string quoteCurrency = 11; //报价货币
    	required double minVar = 12; //最小变动单位
    	required string minVarUnit = 13; //最小变动单位的单位（该字段已废弃）
    	optional string quoteUnit = 14; //报价单位
    	repeated TradeTime tradeTime = 15; //交易时间
    	required string timeZone = 16; //所在时区
    	required string exchangeFormatUrl = 17; //交易所规格
        optional Qot_Common.Security origin = 18; //实际合约代码
    }
    
    message S2C
    {
    	repeated FutureInfo futureInfoList = 1; //期货合约资料的列表
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
                    .setCode("HSImain")
                    .build();
            QotGetFutureInfo.C2S c2s = QotGetFutureInfo.C2S.newBuilder()
                    .addSecurityList(sec)
                .build();
            QotGetFutureInfo.Request req = QotGetFutureInfo.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getFutureInfo(req);
            System.out.printf("Send QotGetFutureInfo: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetFutureInfo(FTAPI_Conn client, int nSerialNo, QotGetFutureInfo.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetFutureInfo failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetFutureInfo: %s\n", json);
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

*   **Output**

    Send QotGetFutureInfo: 2
    Receive QotGetFutureInfo: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "futureInfoList": [{\
          "name": "恒指主连(2106)",\
          "security": {\
            "market": 1,\
            "code": "HSImain"\
          },\
          "lastTradeTime": "",\
          "owner": {\
            "market": 1,\
            "code": "800000"\
          },\
          "ownerOther": "恒生指数",\
          "exchange": "港交所",\
          "contractType": "股指期货",\
          "contractSize": 50.0,\
          "contractSizeUnit": "指数点×港元",\
          "quoteCurrency": "港元",\
          "minVar": 1.0,\
          "minVarUnit": "",\
          "quoteUnit": "指数点",\
          "tradeTime": [{\
            "begin": 555.0,\
            "end": 720.0\
          }, {\
            "begin": 780.0,\
            "end": 990.0\
          }, {\
            "begin": 1035.0,\
            "end": 180.0\
          }],\
          "timeZone": "CCT",\
          "exchangeFormatUrl": "https://sc.hkex.com.hk/TuniS/www.hkex.com.hk/Products/Listed-Derivatives/Equity-Index/Hang-Seng-Index-(HSI)/Hang-Seng-Index-Futures?sc_lang=zh-CN#&product=HSI",\
          "origin": {\
            "market": 1,\
            "code": "HSI2112"\
          }\
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

`Futu::u32_t GetFutureInfo(const Qot_GetFutureInfo::Request &stReq);`  
`virtual void OnReply_GetFutureInfo(Futu::u32_t nSerialNo, const Qot_GetFutureInfo::Response &stRsp) = 0;`

*   **介绍**
    
    获取期货合约资料
    
*   **参数**
    

    
    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    //交易时间
    message TradeTime
    {
        optional double begin = 1; // 开始时间，以分钟为单位
    	optional double end = 2; // 结束时间，以分钟为单位
    }
    
    //期货合约资料的列表
    message FutureInfo
    {
    	required string name = 1; // 合约名称
    	required Qot_Common.Security security = 2; // 合约代码
    	required string lastTradeTime = 3; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 4; //最后交易日时间戳，只有非主连期货合约才有该字段
    	optional Qot_Common.Security owner = 5; //标的股 股票期货和股指期货才有该字段
    	required string ownerOther = 6; //标的 
    	required string exchange = 7; //交易所
    	required string contractType = 8; //合约类型
    	required double contractSize = 9; //合约规模
    	required string contractSizeUnit = 10; //合约规模的单位
    	required string quoteCurrency = 11; //报价货币
    	required double minVar = 12; //最小变动单位
    	required string minVarUnit = 13; //最小变动单位的单位（该字段已废弃）
    	optional string quoteUnit = 14; //报价单位
    	repeated TradeTime tradeTime = 15; //交易时间
    	required string timeZone = 16; //所在时区
    	required string exchangeFormatUrl = 17; //交易所规格
        optional Qot_Common.Security origin = 18; //实际合约代码
    }
    
    message S2C
    {
    	repeated FutureInfo futureInfoList = 1; //期货合约资料的列表
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
    		Qot_GetFutureInfo::Request req;
    		Qot_GetFutureInfo::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("HSImain");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetFutureInfoSerialNo = m_pQotApi->GetFutureInfo(req);
            cout << "Request GetFutureInfo SerialNo: " << m_GetFutureInfoSerialNo << endl;
    	}
    
    	virtual void OnReply_GetFutureInfo(Futu::u32_t nSerialNo, const Qot_GetFutureInfo::Response &stRsp){
            if(nSerialNo == m_GetFutureInfoSerialNo)
            {
                cout << "OnReply_GetFutureInfo SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetFutureInfoSerialNo;
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

*   **Output**

    connect
    Request GetFutureInfo SerialNo: 4
    OnReply_GetFutureInfo SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "futureInfoList": [\
       {\
        "name": "恒指主连(2106)",\
        "security": {\
         "market": 1,\
         "code": "HSImain"\
        },\
        "lastTradeTime": "",\
        "owner": {\
         "market": 1,\
         "code": "800000"\
        },\
        "ownerOther": "恒生指数",\
        "exchange": "港交所",\
        "contractType": "股指期货",\
        "contractSize": 50,\
        "contractSizeUnit": "指数点×港元",\
        "quoteCurrency": "港元",\
        "minVar": 1,\
        "minVarUnit": "",\
        "quoteUnit": "指数点",\
        "tradeTime": [\
         {\
          "begin": 555,\
          "end": 720\
         },\
         {\
          "begin": 780,\
          "end": 990\
         },\
         {\
          "begin": 1035,\
          "end": 180\
         }\
        ],\
        "timeZone": "CCT",\
        "exchangeFormatUrl": "https://sc.hkex.com.hk/TuniS/www.hkex.com.hk/Products/Listed-Derivatives/Equity-Index/Hang-Seng-Index-(HSI)/Hang-Seng-Index-Futures?sc_lang=zh-CN#&product=HSI",\
        "security": {\
         "market": 1,\
         "code": "HSI2112"\
        }\
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

`GetFutureInfo(req);`

*   **介绍**
    
    获取期货合约资料
    
*   **参数**
    

    
    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    //交易时间
    message TradeTime
    {
        optional double begin = 1; // 开始时间，以分钟为单位
    	optional double end = 2; // 结束时间，以分钟为单位
    }
    
    //期货合约资料的列表
    message FutureInfo
    {
    	required string name = 1; // 合约名称
    	required Qot_Common.Security security = 2; // 合约代码
    	required string lastTradeTime = 3; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 4; //最后交易日时间戳，只有非主连期货合约才有该字段
    	optional Qot_Common.Security owner = 5; //标的股 股票期货和股指期货才有该字段
    	required string ownerOther = 6; //标的 
    	required string exchange = 7; //交易所
    	required string contractType = 8; //合约类型
    	required double contractSize = 9; //合约规模
    	required string contractSizeUnit = 10; //合约规模的单位
    	required string quoteCurrency = 11; //报价货币
    	required double minVar = 12; //最小变动单位
    	required string minVarUnit = 13; //最小变动单位的单位（该字段已废弃）
    	optional string quoteUnit = 14; //报价单位
    	repeated TradeTime tradeTime = 15; //交易时间
    	required string timeZone = 16; //所在时区
    	required string exchangeFormatUrl = 17; //交易所规格
        optional Qot_Common.Security origin = 18; //实际合约代码
    }
    
    message S2C
    {
    	repeated FutureInfo futureInfoList = 1; //期货合约资料的列表
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetFutureInfo(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        securityList:[{\
                            market: QotMarket.QotMarket_HK_Future,\
                            code: "MPImain",\
                        },{\
                            market: QotMarket.QotMarket_HK_Future,\
                            code: "HAImain",\
                        },],
                    },
                };
    
                websocket.GetFutureInfo(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("FutureInfo: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    FutureInfo: errCode 0, retMsg , retType 0
    {
      "futureInfoList": [{\
        "name": "内房主连(2109)",\
        "security": {\
          "market": 1,\
          "code": "MPImain"\
        },\
        "lastTradeTime": "",\
        "ownerOther": "恒生中国内地地产指数",\
        "exchange": "港交所",\
        "contractType": "股指期货",\
        "contractSize": 50,\
        "contractSizeUnit": "指数点×港元",\
        "quoteCurrency": "港元",\
        "minVar": 0.5,\
        "minVarUnit": "",\
        "quoteUnit": "指数点",\
        "tradeTime": [{\
          "begin": 555,\
          "end": 720\
        }, {\
          "begin": 780,\
          "end": 990\
        }],\
        "timeZone": "CCT",\
        "exchangeFormatUrl": "https://sc.hkex.com.hk/TuniS/www.hkex.com.hk/Products/Listed-Derivatives/Equity-Index/Sector-Index/Sector-Index-Futures?sc_lang=zh-CN"\
      }, {\
        "name": "海通证券主连(2109)",\
        "security": {\
          "market": 1,\
          "code": "HAImain"\
        },\
        "lastTradeTime": "",\
        "owner": {\
          "market": 1,\
          "code": "06837"\
        },\
        "ownerOther": "海通证券",\
        "exchange": "港交所",\
        "contractType": "股票期货",\
        "contractSize": 10000,\
        "contractSizeUnit": "股",\
        "quoteCurrency": "港元",\
        "minVar": 0.01,\
        "minVarUnit": "",\
        "quoteUnit": "每股/港元",\
        "tradeTime": [{\
          "begin": 570,\
          "end": 720\
        }, {\
          "begin": 780,\
          "end": 960\
        }],\
        "timeZone": "CCT",\
        "exchangeFormatUrl": "https://www.hkex.com.hk/Products/Listed-Derivatives/Single-Stock/Stock-Futures?sc_lang=zh-HK",\
        "security": {\
          "market": 1,\
          "code": "HAI2112"\
        }\
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

接口限制

*   每 30 秒内最多请求 30 次获取期货合约资料接口
*   每次请求的代码列表中，期货个数上限为 200 个

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_future_info(code_list)`

*   **介绍**
    
    获取期货合约资料
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 股票代码列表<br>(ℹ️ list 中元素类型是 str) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回期货合约资料数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   期货合约资料数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | owner | str | 标的  |
        | exchange | str | 交易所 |
        | type | str | 合约类型 |
        | size | float | 合约规模 |
        | size\_unit | str | 合约规模单位 |
        | price\_currency | str | 报价货币 |
        | price\_unit | str | 报价单位 |
        | min\_change | float | 最小变动 |
        | min\_change\_unit | str | 最小变动的单位<br>(ℹ️ 该字段已废弃) |
        | trade\_time | str | 交易时间 |
        | time\_zone | str | 时区  |
        | last\_trade\_time | str | 最后交易时间<br>(ℹ️ 主连，当月，下月等期货没有该字段) |
        | exchange\_format\_url | str | 交易所规格链接 url |
        | origin\_code | str | 实际合约代码 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_future_info(["HK.MPImain", "HK.HAImain"])
    if ret == RET_OK:
        print(data)
        print(data['code'][0])    # 取第一条的股票代码
        print(data['code'].values.tolist())   # 转为 list
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

        code      name       owner exchange  type     size size_unit price_currency price_unit  min_change min_change_unit                        trade_time time_zone last_trade_time                                exchange_format_url           origin_code
    0  HK.MPImain   內房期货主连  恒生中国内地地产指数      港交所  股指期货     50.0    指数点×港元             港元        指数点        0.50               (09:15 - 12:00), (13:00 - 16:30)       CCT                  https://sc.hkex.com.hk/TuniS/www.hkex.com.hk/P...           HK.MPI2112
    1  HK.HAImain   海通证券期货主连    HK.06837      港交所  股票期货  10000.0         股             港元      每股/港元        0.01                (09:30 - 12:00), (13:00 - 16:00)       CCT                  https://sc.hkex.com.hk/TuniS/www.hkex.com.hk/P...           HK.HAI2112
    HK.MPImain
    ['HK.MPImain', 'HK.HAImain']
    

1  
2  
3  
4  
5  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html#8538-2)
 Qot\_GetFutureInfo.proto
--------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取期货合约资料
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    //交易时间
    message TradeTime
    {
        optional double begin = 1; // 开始时间，以分钟为单位
    	optional double end = 2; // 结束时间，以分钟为单位
    }
    
    //期货合约资料的列表
    message FutureInfo
    {
    	required string name = 1; // 合约名称
    	required Qot_Common.Security security = 2; // 合约代码
    	required string lastTradeTime = 3; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 4; //最后交易日时间戳，只有非主连期货合约才有该字段
    	optional Qot_Common.Security owner = 5; //标的股 股票期货和股指期货才有该字段
    	required string ownerOther = 6; //标的 
    	required string exchange = 7; //交易所
    	required string contractType = 8; //合约类型
    	required double contractSize = 9; //合约规模
    	required string contractSizeUnit = 10; //合约规模的单位
    	required string quoteCurrency = 11; //报价货币
    	required double minVar = 12; //最小变动单位
    	required string minVarUnit = 13; //最小变动单位的单位（该字段已废弃）
    	optional string quoteUnit = 14; //报价单位
    	repeated TradeTime tradeTime = 15; //交易时间
    	required string timeZone = 16; //所在时区
    	required string exchangeFormatUrl = 17; //交易所规格
        optional Qot_Common.Security origin = 18; //实际合约代码
    }
    
    message S2C
    {
    	repeated FutureInfo futureInfoList = 1; //期货合约资料的列表
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3218
    

`uint GetFutureInfo(QotGetFutureInfo.Request req);`  
`virtual void OnReply_GetFutureInfo(MMAPI_Conn client, uint nSerialNo, QotGetFutureInfo.Response rsp);`

*   **介绍**

获取期货合约资料

*   **参数**

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    //交易时间
    message TradeTime
    {
        optional double begin = 1; // 开始时间，以分钟为单位
    	optional double end = 2; // 结束时间，以分钟为单位
    }
    
    //期货合约资料的列表
    message FutureInfo
    {
    	required string name = 1; // 合约名称
    	required Qot_Common.Security security = 2; // 合约代码
    	required string lastTradeTime = 3; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 4; //最后交易日时间戳，只有非主连期货合约才有该字段
    	optional Qot_Common.Security owner = 5; //标的股 股票期货和股指期货才有该字段
    	required string ownerOther = 6; //标的 
    	required string exchange = 7; //交易所
    	required string contractType = 8; //合约类型
    	required double contractSize = 9; //合约规模
    	required string contractSizeUnit = 10; //合约规模的单位
    	required string quoteCurrency = 11; //报价货币
    	required double minVar = 12; //最小变动单位
    	required string minVarUnit = 13; //最小变动单位的单位（该字段已废弃）
    	optional string quoteUnit = 14; //报价单位
    	repeated TradeTime tradeTime = 15; //交易时间
    	required string timeZone = 16; //所在时区
    	required string exchangeFormatUrl = 17; //交易所规格
        optional Qot_Common.Security origin = 18; //实际合约代码
    }
    
    message S2C
    {
    	repeated FutureInfo futureInfoList = 1; //期货合约资料的列表
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
                    .SetCode("HSImain")
                    .Build();
            QotGetFutureInfo.C2S c2s = QotGetFutureInfo.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                .Build();
            QotGetFutureInfo.Request req = QotGetFutureInfo.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetFutureInfo(req);
            Console.Write("Send QotGetFutureInfo: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetFutureInfo(MMAPI_Conn client, uint nSerialNo, QotGetFutureInfo.Response rsp)
        {
            Console.Write("Reply: QotGetFutureInfo: {0}\n", nSerialNo);
            Console.Write("name: {0}, exchange: {1} \n", rsp.S2C.FutureInfoListList[0].Name,
                rsp.S2C.FutureInfoListList[0].Exchange);
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

    Qot onInitConnect: ret=0 desc= connID=6825719160020953581
    Send QotGetFutureInfo: 3
    Reply: QotGetFutureInfo: 3
    name: 恒指主连(2108), exchange: 港交所
    

1  
2  
3  
4  

`int getFutureInfo(QotGetFutureInfo.Request req);`  
`void onReply_GetFutureInfo(MMAPI_Conn client, int nSerialNo, QotGetFutureInfo.Response rsp);`

*   **介绍**

获取期货合约资料

*   **参数**

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    //交易时间
    message TradeTime
    {
        optional double begin = 1; // 开始时间，以分钟为单位
    	optional double end = 2; // 结束时间，以分钟为单位
    }
    
    //期货合约资料的列表
    message FutureInfo
    {
    	required string name = 1; // 合约名称
    	required Qot_Common.Security security = 2; // 合约代码
    	required string lastTradeTime = 3; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 4; //最后交易日时间戳，只有非主连期货合约才有该字段
    	optional Qot_Common.Security owner = 5; //标的股 股票期货和股指期货才有该字段
    	required string ownerOther = 6; //标的 
    	required string exchange = 7; //交易所
    	required string contractType = 8; //合约类型
    	required double contractSize = 9; //合约规模
    	required string contractSizeUnit = 10; //合约规模的单位
    	required string quoteCurrency = 11; //报价货币
    	required double minVar = 12; //最小变动单位
    	required string minVarUnit = 13; //最小变动单位的单位（该字段已废弃）
    	optional string quoteUnit = 14; //报价单位
    	repeated TradeTime tradeTime = 15; //交易时间
    	required string timeZone = 16; //所在时区
    	required string exchangeFormatUrl = 17; //交易所规格
        optional Qot_Common.Security origin = 18; //实际合约代码
    }
    
    message S2C
    {
    	repeated FutureInfo futureInfoList = 1; //期货合约资料的列表
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
                    .setCode("HSImain")
                    .build();
            QotGetFutureInfo.C2S c2s = QotGetFutureInfo.C2S.newBuilder()
                    .addSecurityList(sec)
                .build();
            QotGetFutureInfo.Request req = QotGetFutureInfo.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getFutureInfo(req);
            System.out.printf("Send QotGetFutureInfo: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetFutureInfo(MMAPI_Conn client, int nSerialNo, QotGetFutureInfo.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetFutureInfo failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetFutureInfo: %s\n", json);
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

*   **Output**

    Send QotGetFutureInfo: 2
    Receive QotGetFutureInfo: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "futureInfoList": [{\
          "name": "恒指主连(2106)",\
          "security": {\
            "market": 1,\
            "code": "HSImain"\
          },\
          "lastTradeTime": "",\
          "owner": {\
            "market": 1,\
            "code": "800000"\
          },\
          "ownerOther": "恒生指数",\
          "exchange": "港交所",\
          "contractType": "股指期货",\
          "contractSize": 50.0,\
          "contractSizeUnit": "指数点×港元",\
          "quoteCurrency": "港元",\
          "minVar": 1.0,\
          "minVarUnit": "",\
          "quoteUnit": "指数点",\
          "tradeTime": [{\
            "begin": 555.0,\
            "end": 720.0\
          }, {\
            "begin": 780.0,\
            "end": 990.0\
          }, {\
            "begin": 1035.0,\
            "end": 180.0\
          }],\
          "timeZone": "CCT",\
          "exchangeFormatUrl": "https://sc.hkex.com.hk/TuniS/www.hkex.com.hk/Products/Listed-Derivatives/Equity-Index/Hang-Seng-Index-(HSI)/Hang-Seng-Index-Futures?sc_lang=zh-CN#&product=HSI",\
          "origin": {\
            "market": 1,\
            "code": "HSI2112"\
          }\
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

`moomoo::u32_t GetFutureInfo(const Qot_GetFutureInfo::Request &stReq);`  
`virtual void OnReply_GetFutureInfo(moomoo::u32_t nSerialNo, const Qot_GetFutureInfo::Response &stRsp) = 0;`

*   **介绍**
    
    获取期货合约资料
    
*   **参数**
    

    
    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    //交易时间
    message TradeTime
    {
        optional double begin = 1; // 开始时间，以分钟为单位
    	optional double end = 2; // 结束时间，以分钟为单位
    }
    
    //期货合约资料的列表
    message FutureInfo
    {
    	required string name = 1; // 合约名称
    	required Qot_Common.Security security = 2; // 合约代码
    	required string lastTradeTime = 3; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 4; //最后交易日时间戳，只有非主连期货合约才有该字段
    	optional Qot_Common.Security owner = 5; //标的股 股票期货和股指期货才有该字段
    	required string ownerOther = 6; //标的 
    	required string exchange = 7; //交易所
    	required string contractType = 8; //合约类型
    	required double contractSize = 9; //合约规模
    	required string contractSizeUnit = 10; //合约规模的单位
    	required string quoteCurrency = 11; //报价货币
    	required double minVar = 12; //最小变动单位
    	required string minVarUnit = 13; //最小变动单位的单位（该字段已废弃）
    	optional string quoteUnit = 14; //报价单位
    	repeated TradeTime tradeTime = 15; //交易时间
    	required string timeZone = 16; //所在时区
    	required string exchangeFormatUrl = 17; //交易所规格
        optional Qot_Common.Security origin = 18; //实际合约代码
    }
    
    message S2C
    {
    	repeated FutureInfo futureInfoList = 1; //期货合约资料的列表
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
    		Qot_GetFutureInfo::Request req;
    		Qot_GetFutureInfo::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("HSImain");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetFutureInfoSerialNo = m_pQotApi->GetFutureInfo(req);
            cout << "Request GetFutureInfo SerialNo: " << m_GetFutureInfoSerialNo << endl;
    	}
    
    	virtual void OnReply_GetFutureInfo(moomoo::u32_t nSerialNo, const Qot_GetFutureInfo::Response &stRsp){
            if(nSerialNo == m_GetFutureInfoSerialNo)
            {
                cout << "OnReply_GetFutureInfo SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetFutureInfoSerialNo;
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

*   **Output**

    connect
    Request GetFutureInfo SerialNo: 4
    OnReply_GetFutureInfo SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "futureInfoList": [\
       {\
        "name": "恒指主连(2106)",\
        "security": {\
         "market": 1,\
         "code": "HSImain"\
        },\
        "lastTradeTime": "",\
        "owner": {\
         "market": 1,\
         "code": "800000"\
        },\
        "ownerOther": "恒生指数",\
        "exchange": "港交所",\
        "contractType": "股指期货",\
        "contractSize": 50,\
        "contractSizeUnit": "指数点×港元",\
        "quoteCurrency": "港元",\
        "minVar": 1,\
        "minVarUnit": "",\
        "quoteUnit": "指数点",\
        "tradeTime": [\
         {\
          "begin": 555,\
          "end": 720\
         },\
         {\
          "begin": 780,\
          "end": 990\
         },\
         {\
          "begin": 1035,\
          "end": 180\
         }\
        ],\
        "timeZone": "CCT",\
        "exchangeFormatUrl": "https://sc.hkex.com.hk/TuniS/www.hkex.com.hk/Products/Listed-Derivatives/Equity-Index/Hang-Seng-Index-(HSI)/Hang-Seng-Index-Futures?sc_lang=zh-CN#&product=HSI",\
        "security": {\
         "market": 1,\
         "code": "HSI2112"\
        }\
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

`GetFutureInfo(req);`

*   **介绍**
    
    获取期货合约资料
    
*   **参数**
    

    
    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    //交易时间
    message TradeTime
    {
        optional double begin = 1; // 开始时间，以分钟为单位
    	optional double end = 2; // 结束时间，以分钟为单位
    }
    
    //期货合约资料的列表
    message FutureInfo
    {
    	required string name = 1; // 合约名称
    	required Qot_Common.Security security = 2; // 合约代码
    	required string lastTradeTime = 3; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 4; //最后交易日时间戳，只有非主连期货合约才有该字段
    	optional Qot_Common.Security owner = 5; //标的股 股票期货和股指期货才有该字段
    	required string ownerOther = 6; //标的 
    	required string exchange = 7; //交易所
    	required string contractType = 8; //合约类型
    	required double contractSize = 9; //合约规模
    	required string contractSizeUnit = 10; //合约规模的单位
    	required string quoteCurrency = 11; //报价货币
    	required double minVar = 12; //最小变动单位
    	required string minVarUnit = 13; //最小变动单位的单位（该字段已废弃）
    	optional string quoteUnit = 14; //报价单位
    	repeated TradeTime tradeTime = 15; //交易时间
    	required string timeZone = 16; //所在时区
    	required string exchangeFormatUrl = 17; //交易所规格
        optional Qot_Common.Security origin = 18; //实际合约代码
    }
    
    message S2C
    {
    	repeated FutureInfo futureInfoList = 1; //期货合约资料的列表
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetFutureInfo(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        securityList:[{\
                            market: QotMarket.QotMarket_HK_Future,\
                            code: "MPImain",\
                        },{\
                            market: QotMarket.QotMarket_HK_Future,\
                            code: "HAImain",\
                        },],
                    },
                };
    
                websocket.GetFutureInfo(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("FutureInfo: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    FutureInfo: errCode 0, retMsg , retType 0
    {
      "futureInfoList": [{\
        "name": "内房主连(2109)",\
        "security": {\
          "market": 1,\
          "code": "MPImain"\
        },\
        "lastTradeTime": "",\
        "ownerOther": "恒生中国内地地产指数",\
        "exchange": "港交所",\
        "contractType": "股指期货",\
        "contractSize": 50,\
        "contractSizeUnit": "指数点×港元",\
        "quoteCurrency": "港元",\
        "minVar": 0.5,\
        "minVarUnit": "",\
        "quoteUnit": "指数点",\
        "tradeTime": [{\
          "begin": 555,\
          "end": 720\
        }, {\
          "begin": 780,\
          "end": 990\
        }],\
        "timeZone": "CCT",\
        "exchangeFormatUrl": "https://sc.hkex.com.hk/TuniS/www.hkex.com.hk/Products/Listed-Derivatives/Equity-Index/Sector-Index/Sector-Index-Futures?sc_lang=zh-CN"\
      }, {\
        "name": "海通证券主连(2109)",\
        "security": {\
          "market": 1,\
          "code": "HAImain"\
        },\
        "lastTradeTime": "",\
        "owner": {\
          "market": 1,\
          "code": "06837"\
        },\
        "ownerOther": "海通证券",\
        "exchange": "港交所",\
        "contractType": "股票期货",\
        "contractSize": 10000,\
        "contractSizeUnit": "股",\
        "quoteCurrency": "港元",\
        "minVar": 0.01,\
        "minVarUnit": "",\
        "quoteUnit": "每股/港元",\
        "tradeTime": [{\
          "begin": 570,\
          "end": 720\
        }, {\
          "begin": 780,\
          "end": 960\
        }],\
        "timeZone": "CCT",\
        "exchangeFormatUrl": "https://www.hkex.com.hk/Products/Listed-Derivatives/Single-Stock/Stock-Futures?sc_lang=zh-HK",\
        "security": {\
          "market": 1,\
          "code": "HAI2112"\
        }\
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

接口限制

*   每 30 秒内最多请求 30 次获取期货合约资料接口
*   每次请求的代码列表中，期货个数上限为 200 个

← [获取窝轮和期货列表](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html) [条件选股](https://openapi.futunn.com/futu-api-doc/quote/get-stock-filter.html)
 →

[获取期货合约资料](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html)