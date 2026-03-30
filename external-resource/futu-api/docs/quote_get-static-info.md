 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-static-info.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-static-info.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-static-info.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-static-info.html)
    

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
    

[#](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html#4898)
 获取静态数据
====================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_stock_basicinfo(market, stock_type=SecurityType.STOCK, code_list=None)`

*   **介绍**
    
    获取静态数据
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | market | [Market](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427) | 市场类型 |
    | stock\_type | [SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325) | 股票类型，但不支持传入 SecurityType.DRVT |
    | code\_list | list | 股票列表<br>(ℹ️ *   默认为 None，代表获取全市场股票的静态信息<br>*   若传入股票列表，只返回指定股票的信息<br>*   list 中元素类型是 str) |
    
    注：当 market 和 code\_list 同时存在时，会忽略 market，仅对 code\_list 进行查询。
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回股票静态数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   股票静态数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | lot\_size | int | 每手股数，期权表示每份合约股数<br>(ℹ️ 指数期权无该字段<br><br>，期货表示合约乘数) |
        | stock\_type | [SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325) | 股票类型 |
        | stock\_child\_type | [WrtType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926) | 窝轮子类型 |
        | stock\_owner | str | 窝轮所属正股的代码，或期权标的股的代码 |
        | option\_type | [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713) | 期权类型 |
        | strike\_time | str | 期权行权日<br>(ℹ️ 格式：yyyy-MM-dd  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | strike\_price | float | 期权行权价 |
        | suspension | bool | 期权是否停牌<br>(ℹ️ True：停牌  <br>False：未停牌) |
        | listing\_date | str | 上市时间<br>(ℹ️ 此字段停止维护，不建议使用  <br>格式：yyyy-MM-dd) |
        | stock\_id | int | 股票 ID |
        | delisting | bool | 是否退市 |
        | index\_option\_type | str | 指数期权类型 |
        | main\_contract | bool | 是否主连合约 |
        | last\_trade\_time | str | 最后交易时间<br>(ℹ️ 主连，当月，下月等期货没有该字段) |
        | exchange\_type | [ExchType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6898) | 所属交易所 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret, data = quote_ctx.get_stock_basicinfo(Market.HK, SecurityType.STOCK)
    if ret == RET_OK:
        print(data)
    else:
        print('error:', data)
    print('******************************************')
    ret, data = quote_ctx.get_stock_basicinfo(Market.HK, SecurityType.STOCK, ['HK.06998', 'HK.00700'])
    if ret == RET_OK:
        print(data)
        print(data['name'][0])  # 取第一条的股票名称
        print(data['name'].values.tolist())  # 转为 list
    else:
        print('error:', data)
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

*   **Output**

            code             name  lot_size stock_type stock_child_type stock_owner option_type strike_time strike_price suspension listing_date        stock_id  delisting index_option_type  main_contract last_trade_time exchange_type
    0      HK.00001               长和       500      STOCK              N/A                     N/A                      N/A        N/A   2015-03-18   4440996184065      False               N/A          False                  HK_MAINBOARD  
    ...         ...              ...       ...        ...              ...         ...         ...         ...          ...        ...          ...             ...        ...               ...            ...             ...
    2592   HK.09979     绿城管理控股      1000      STOCK              N/A                                              N/A        N/A   2020-07-10  79203491915515      False               N/A          False                  HK_MAINBOARD                
    
    [2593 rows x 16 columns]
    ******************************************
            code            name  lot_size stock_type stock_child_type stock_owner option_type strike_time strike_price suspension listing_date        stock_id  delisting index_option_type  main_contract last_trade_time exchange_type
    0  HK.06998     嘉和生物-B       500      STOCK              N/A                                              N/A        N/A   2020-10-07  79572859099990      False               N/A          False                  HK_MAINBOARD                
    1  HK.00700     腾讯控股         100      STOCK              N/A                                              N/A        N/A   2004-06-16  54047868453564      False               N/A          False                  HK_MAINBOARD               
    嘉和生物-B
    ['嘉和生物-B', '腾讯控股']
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html#9277)
 Qot\_GetStaticInfo.proto
------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取静态数据
    
*   **参数**
    

    message C2S
    {
        // 当 market 和 code_list 同时存在时，会忽略 market，仅对 code_list 进行查询。
    	optional int32 market = 1; //Qot_Common.QotMarket，股票市场
    	optional int32 secType = 2; //Qot_Common.SecurityType，股票类型
    	repeated Qot_Common.Security securityList = 3; //股票，若该字段存在，忽略其他字段，只返回该字段股票的静态信息
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

> *   股票结构参见[Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   行情市场参见[QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     
> *   股票类型参见[SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //静态信息
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

> *   股票静态信息结构参见[SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3202
    

`uint GetStaticInfo(QotGetStaticInfo.Request req);`  
`virtual void OnReply_GetStaticInfo(FTAPI_Conn client, uint nSerialNo, QotGetStaticInfo.Response rsp);`

*   **介绍**
    
    获取静态数据
    
*   **参数**
    

    message C2S
    {
        // 当 market 和 code_list 同时存在时，会忽略 market，仅对 code_list 进行查询。
    	optional int32 market = 1; //Qot_Common.QotMarket，股票市场
    	optional int32 secType = 2; //Qot_Common.SecurityType，股票类型
    	repeated Qot_Common.Security securityList = 3; //股票，若该字段存在，忽略其他字段，只返回该字段股票的静态信息
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

> *   股票结构参见[Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   行情市场参见[QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     
> *   股票类型参见[SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //静态信息
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

> *   股票静态信息结构参见[SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
            QotGetStaticInfo.C2S c2s = QotGetStaticInfo.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                .Build();
            QotGetStaticInfo.Request req = QotGetStaticInfo.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetStaticInfo(req);
            Console.Write("Send QotGetStaticInfo: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetStaticInfo(FTAPI_Conn client, uint nSerialNo, QotGetStaticInfo.Response rsp)
        {
            Console.Write("Reply: QotGetStaticInfo: {0}\n", nSerialNo);
            Console.Write("name: {0}, listTime: {1}\n", rsp.S2C.StaticInfoListList[0].Basic.Name,
                rsp.S2C.StaticInfoListList[0].Basic.ListTime);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825962957633551611
    Send QotGetStaticInfo: 3
    Reply: QotGetStaticInfo: 3
    name: 腾讯控股, listTime: 2004-06-16
    

1  
2  
3  
4  

`int getStaticInfo(QotGetStaticInfo.Request req);`  
`void onReply_GetStaticInfo(FTAPI_Conn client, int nSerialNo, QotGetStaticInfo.Response rsp);`

*   **介绍**
    
    获取静态数据
    
*   **参数**
    

    message C2S
    {
        // 当 market 和 code_list 同时存在时，会忽略 market，仅对 code_list 进行查询。
    	optional int32 market = 1; //Qot_Common.QotMarket，股票市场
    	optional int32 secType = 2; //Qot_Common.SecurityType，股票类型
    	repeated Qot_Common.Security securityList = 3; //股票，若该字段存在，忽略其他字段，只返回该字段股票的静态信息
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

> *   股票结构参见[Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   行情市场参见[QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     
> *   股票类型参见[SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //静态信息
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

> *   股票静态信息结构参见[SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
            QotGetStaticInfo.C2S c2s = QotGetStaticInfo.C2S.newBuilder()
                    .addSecurityList(sec)
                .build();
            QotGetStaticInfo.Request req = QotGetStaticInfo.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getStaticInfo(req);
            System.out.printf("Send QotGetStaticInfo: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetStaticInfo(FTAPI_Conn client, int nSerialNo, QotGetStaticInfo.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetStaticInfo failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetStaticInfo: %s\n", json);
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

    Send QotGetStaticInfo: 2
    Receive QotGetStaticInfo: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "staticInfoList": [{\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "00700"\
            },\
            "id": "54047868453564",\
            "lotSize": 100,\
            "secType": 3,\
            "name": "腾讯控股",\
            "listTime": "2004-06-16",\
            "delisting": false,\
            "listTimestamp": 1.0873152E9,\
            "exchType": 1\
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

`Futu::u32_t GetStaticInfo(const Qot_GetStaticInfo::Request &stReq);`  
`virtual void OnReply_GetStaticInfo(Futu::u32_t nSerialNo, const Qot_GetStaticInfo::Response &stRsp) = 0;`

*   **介绍**
    
    获取静态数据
    
*   **参数**
    

    message C2S
    {
        // 当 market 和 code_list 同时存在时，会忽略 market，仅对 code_list 进行查询。
    	optional int32 market = 1; //Qot_Common.QotMarket，股票市场
    	optional int32 secType = 2; //Qot_Common.SecurityType，股票类型
    	repeated Qot_Common.Security securityList = 3; //股票，若该字段存在，忽略其他字段，只返回该字段股票的静态信息
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

> *   股票结构参见[Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   行情市场参见[QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     
> *   股票类型参见[SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //静态信息
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

> *   股票静态信息结构参见[SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
    		Qot_GetStaticInfo::Request req;
    		Qot_GetStaticInfo::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetStaticInfoSerialNo = m_pQotApi->GetStaticInfo(req);
            cout << "Request GetStaticInfo SerialNo: " << m_GetStaticInfoSerialNo << endl;
    	}
    
    	virtual void OnReply_GetStaticInfo(Futu::u32_t nSerialNo, const Qot_GetStaticInfo::Response &stRsp){
            if(nSerialNo == m_GetStaticInfoSerialNo)
            {
                cout << "OnReply_GetStaticInfo SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_StockFilterSerialNo;
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
    Request GetStaticInfo SerialNo: 4
    OnReply_GetStaticInfo SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "staticInfoList": [\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "00700"\
         },\
         "id": "54047868453564",\
         "lotSize": 100,\
         "secType": 3,\
         "name": "腾讯控股",\
         "listTime": "2004-06-16",\
         "delisting": false,\
         "listTimestamp": 1087315200,\
         "exchType": 1\
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

`GetStaticInfo(req);`

*   **介绍**
    
    获取静态数据
    
*   **参数**
    

    message C2S
    {
        // 当 market 和 code_list 同时存在时，会忽略 market，仅对 code_list 进行查询。
    	optional int32 market = 1; //Qot_Common.QotMarket，股票市场
    	optional int32 secType = 2; //Qot_Common.SecurityType，股票类型
    	repeated Qot_Common.Security securityList = 3; //股票，若该字段存在，忽略其他字段，只返回该字段股票的静态信息
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

> *   股票结构参见[Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   行情市场参见[QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     
> *   股票类型参见[SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //静态信息
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

> *   股票静态信息结构参见[SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetStaticInfo(){
        const { RetType } = Common
        const { QotMarket, PlateSetType } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        securityList: [{\
                            market: QotMarket.QotMarket_HK_Security,\
                            code: "00700",\
                        },],
                    },
                };
    
                websocket.GetStaticInfo(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("StaticInfo: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    StaticInfo: errCode 0, retMsg , retType 0
    {
      "staticInfoList": [{\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "id": "54047868453564",\
          "lotSize": 100,\
          "secType": 3,\
          "name": "腾讯控股",\
          "listTime": "2004-06-16",\
          "delisting": false,\
          "listTimestamp": 1087315200\
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

提示

*   当传入程序无法识别的股票时（包括很久之前退市的股票和不存在的股票），此接口仍然返回股票信息，用“是否退市”字段来标识该股票不存在。统一处理为：代码正常显示，股票名显示为“未知股票”，其他字段均为默认值（整型默认是0，字符串默认是空字符串）。
*   此接口与其他的行情接口不同，其他接口遇到程序无法识别的股票时，会拒绝请求并返回错误描述“未知股票”。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_stock_basicinfo(market, stock_type=SecurityType.STOCK, code_list=None)`

*   **介绍**
    
    获取静态数据
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | market | [Market](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427) | 市场类型 |
    | stock\_type | [SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325) | 股票类型，但不支持传入 SecurityType.DRVT |
    | code\_list | list | 股票列表<br>(ℹ️ *   默认为 None，代表获取全市场股票的静态信息<br>*   若传入股票列表，只返回指定股票的信息<br>*   list 中元素类型是 str) |
    
    注：当 market 和 code\_list 同时存在时，会忽略 market，仅对 code\_list 进行查询。
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回股票静态数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   股票静态数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | lot\_size | int | 每手股数，期权表示每份合约股数<br>(ℹ️ 指数期权无该字段<br><br>，期货表示合约乘数) |
        | stock\_type | [SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325) | 股票类型 |
        | stock\_child\_type | [WrtType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926) | 窝轮子类型 |
        | stock\_owner | str | 窝轮所属正股的代码，或期权标的股的代码 |
        | option\_type | [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713) | 期权类型 |
        | strike\_time | str | 期权行权日<br>(ℹ️ 格式：yyyy-MM-dd  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | strike\_price | float | 期权行权价 |
        | suspension | bool | 期权是否停牌<br>(ℹ️ True：停牌  <br>False：未停牌) |
        | listing\_date | str | 上市时间<br>(ℹ️ 此字段停止维护，不建议使用  <br>格式：yyyy-MM-dd) |
        | stock\_id | int | 股票 ID |
        | delisting | bool | 是否退市 |
        | index\_option\_type | str | 指数期权类型 |
        | main\_contract | bool | 是否主连合约 |
        | last\_trade\_time | str | 最后交易时间<br>(ℹ️ 主连，当月，下月等期货没有该字段) |
        | exchange\_type | [ExchType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6898) | 所属交易所 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret, data = quote_ctx.get_stock_basicinfo(Market.HK, SecurityType.STOCK)
    if ret == RET_OK:
        print(data)
    else:
        print('error:', data)
    print('******************************************')
    ret, data = quote_ctx.get_stock_basicinfo(Market.HK, SecurityType.STOCK, ['HK.06998', 'HK.00700'])
    if ret == RET_OK:
        print(data)
        print(data['name'][0])  # 取第一条的股票名称
        print(data['name'].values.tolist())  # 转为 list
    else:
        print('error:', data)
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

*   **Output**

            code             name  lot_size stock_type stock_child_type stock_owner option_type strike_time strike_price suspension listing_date        stock_id  delisting index_option_type  main_contract last_trade_time exchange_type
    0      HK.00001               长和       500      STOCK              N/A                     N/A                      N/A        N/A   2015-03-18   4440996184065      False               N/A          False                  HK_MAINBOARD  
    ...         ...              ...       ...        ...              ...         ...         ...         ...          ...        ...          ...             ...        ...               ...            ...             ...
    2592   HK.09979     绿城管理控股      1000      STOCK              N/A                                              N/A        N/A   2020-07-10  79203491915515      False               N/A          False                  HK_MAINBOARD                
    
    [2593 rows x 16 columns]
    ******************************************
            code            name  lot_size stock_type stock_child_type stock_owner option_type strike_time strike_price suspension listing_date        stock_id  delisting index_option_type  main_contract last_trade_time exchange_type
    0  HK.06998     嘉和生物-B       500      STOCK              N/A                                              N/A        N/A   2020-10-07  79572859099990      False               N/A          False                  HK_MAINBOARD                
    1  HK.00700     腾讯控股         100      STOCK              N/A                                              N/A        N/A   2004-06-16  54047868453564      False               N/A          False                  HK_MAINBOARD               
    嘉和生物-B
    ['嘉和生物-B', '腾讯控股']
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html#9277-2)
 Qot\_GetStaticInfo.proto
--------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取静态数据
    
*   **参数**
    

    message C2S
    {
        // 当 market 和 code_list 同时存在时，会忽略 market，仅对 code_list 进行查询。
    	optional int32 market = 1; //Qot_Common.QotMarket，股票市场
    	optional int32 secType = 2; //Qot_Common.SecurityType，股票类型
    	repeated Qot_Common.Security securityList = 3; //股票，若该字段存在，忽略其他字段，只返回该字段股票的静态信息
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

> *   股票结构参见[Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   行情市场参见[QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     
> *   股票类型参见[SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //静态信息
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

> *   股票静态信息结构参见[SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3202
    

`uint GetStaticInfo(QotGetStaticInfo.Request req);`  
`virtual void OnReply_GetStaticInfo(MMAPI_Conn client, uint nSerialNo, QotGetStaticInfo.Response rsp);`

*   **介绍**
    
    获取静态数据
    
*   **参数**
    

    message C2S
    {
        // 当 market 和 code_list 同时存在时，会忽略 market，仅对 code_list 进行查询。
    	optional int32 market = 1; //Qot_Common.QotMarket，股票市场
    	optional int32 secType = 2; //Qot_Common.SecurityType，股票类型
    	repeated Qot_Common.Security securityList = 3; //股票，若该字段存在，忽略其他字段，只返回该字段股票的静态信息
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

> *   股票结构参见[Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   行情市场参见[QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     
> *   股票类型参见[SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //静态信息
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

> *   股票静态信息结构参见[SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
            QotGetStaticInfo.C2S c2s = QotGetStaticInfo.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                .Build();
            QotGetStaticInfo.Request req = QotGetStaticInfo.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetStaticInfo(req);
            Console.Write("Send QotGetStaticInfo: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetStaticInfo(MMAPI_Conn client, uint nSerialNo, QotGetStaticInfo.Response rsp)
        {
            Console.Write("Reply: QotGetStaticInfo: {0}\n", nSerialNo);
            Console.Write("name: {0}, listTime: {1}\n", rsp.S2C.StaticInfoListList[0].Basic.Name,
                rsp.S2C.StaticInfoListList[0].Basic.ListTime);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825962957633551611
    Send QotGetStaticInfo: 3
    Reply: QotGetStaticInfo: 3
    name: 腾讯控股, listTime: 2004-06-16
    

1  
2  
3  
4  

`int getStaticInfo(QotGetStaticInfo.Request req);`  
`void onReply_GetStaticInfo(MMAPI_Conn client, int nSerialNo, QotGetStaticInfo.Response rsp);`

*   **介绍**
    
    获取静态数据
    
*   **参数**
    

    message C2S
    {
        // 当 market 和 code_list 同时存在时，会忽略 market，仅对 code_list 进行查询。
    	optional int32 market = 1; //Qot_Common.QotMarket，股票市场
    	optional int32 secType = 2; //Qot_Common.SecurityType，股票类型
    	repeated Qot_Common.Security securityList = 3; //股票，若该字段存在，忽略其他字段，只返回该字段股票的静态信息
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

> *   股票结构参见[Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   行情市场参见[QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     
> *   股票类型参见[SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //静态信息
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

> *   股票静态信息结构参见[SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
            QotGetStaticInfo.C2S c2s = QotGetStaticInfo.C2S.newBuilder()
                    .addSecurityList(sec)
                .build();
            QotGetStaticInfo.Request req = QotGetStaticInfo.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getStaticInfo(req);
            System.out.printf("Send QotGetStaticInfo: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetStaticInfo(MMAPI_Conn client, int nSerialNo, QotGetStaticInfo.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetStaticInfo failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetStaticInfo: %s\n", json);
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

    Send QotGetStaticInfo: 2
    Receive QotGetStaticInfo: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "staticInfoList": [{\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "00700"\
            },\
            "id": "54047868453564",\
            "lotSize": 100,\
            "secType": 3,\
            "name": "腾讯控股",\
            "listTime": "2004-06-16",\
            "delisting": false,\
            "listTimestamp": 1.0873152E9,\
            "exchType": 1\
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

`moomoo::u32_t GetStaticInfo(const Qot_GetStaticInfo::Request &stReq);`  
`virtual void OnReply_GetStaticInfo(moomoo::u32_t nSerialNo, const Qot_GetStaticInfo::Response &stRsp) = 0;`

*   **介绍**
    
    获取静态数据
    
*   **参数**
    

    message C2S
    {
        // 当 market 和 code_list 同时存在时，会忽略 market，仅对 code_list 进行查询。
    	optional int32 market = 1; //Qot_Common.QotMarket，股票市场
    	optional int32 secType = 2; //Qot_Common.SecurityType，股票类型
    	repeated Qot_Common.Security securityList = 3; //股票，若该字段存在，忽略其他字段，只返回该字段股票的静态信息
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

> *   股票结构参见[Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   行情市场参见[QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     
> *   股票类型参见[SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //静态信息
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

> *   股票静态信息结构参见[SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
    		Qot_GetStaticInfo::Request req;
    		Qot_GetStaticInfo::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetStaticInfoSerialNo = m_pQotApi->GetStaticInfo(req);
            cout << "Request GetStaticInfo SerialNo: " << m_GetStaticInfoSerialNo << endl;
    	}
    
    	virtual void OnReply_GetStaticInfo(moomoo::u32_t nSerialNo, const Qot_GetStaticInfo::Response &stRsp){
            if(nSerialNo == m_GetStaticInfoSerialNo)
            {
                cout << "OnReply_GetStaticInfo SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_StockFilterSerialNo;
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
    Request GetStaticInfo SerialNo: 4
    OnReply_GetStaticInfo SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "staticInfoList": [\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "00700"\
         },\
         "id": "54047868453564",\
         "lotSize": 100,\
         "secType": 3,\
         "name": "腾讯控股",\
         "listTime": "2004-06-16",\
         "delisting": false,\
         "listTimestamp": 1087315200,\
         "exchType": 1\
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

`GetStaticInfo(req);`

*   **介绍**
    
    获取静态数据
    
*   **参数**
    

    message C2S
    {
        // 当 market 和 code_list 同时存在时，会忽略 market，仅对 code_list 进行查询。
    	optional int32 market = 1; //Qot_Common.QotMarket，股票市场
    	optional int32 secType = 2; //Qot_Common.SecurityType，股票类型
    	repeated Qot_Common.Security securityList = 3; //股票，若该字段存在，忽略其他字段，只返回该字段股票的静态信息
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

> *   股票结构参见[Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   行情市场参见[QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     
> *   股票类型参见[SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //静态信息
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

> *   股票静态信息结构参见[SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetStaticInfo(){
        const { RetType } = Common
        const { QotMarket, PlateSetType } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        securityList: [{\
                            market: QotMarket.QotMarket_HK_Security,\
                            code: "00700",\
                        },],
                    },
                };
    
                websocket.GetStaticInfo(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("StaticInfo: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    StaticInfo: errCode 0, retMsg , retType 0
    {
      "staticInfoList": [{\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "id": "54047868453564",\
          "lotSize": 100,\
          "secType": 3,\
          "name": "腾讯控股",\
          "listTime": "2004-06-16",\
          "delisting": false,\
          "listTimestamp": 1087315200\
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

提示

*   当传入程序无法识别的股票时（包括很久之前退市的股票和不存在的股票），此接口仍然返回股票信息，用“是否退市”字段来标识该股票不存在。统一处理为：代码正常显示，股票名显示为“未知股票”，其他字段均为默认值（整型默认是0，字符串默认是空字符串）。
*   此接口与其他的行情接口不同，其他接口遇到程序无法识别的股票时，会拒绝请求并返回错误描述“未知股票”。

← [获取板块列表](https://openapi.futunn.com/futu-api-doc/quote/get-plate-list.html) [获取 IPO 信息](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html)
 →

[获取静态数据](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html)