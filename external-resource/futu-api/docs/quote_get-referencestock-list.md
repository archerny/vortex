 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-referencestock-list.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-referencestock-list.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-referencestock-list.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-referencestock-list.html)
    

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
    

[#](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html#8657)
 获取窝轮和期货列表
===============================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_referencestock_list(code, reference_type)`

*   **介绍**
    
    获取证券的关联数据，如：获取正股相关窝轮、获取期货相关合约
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 证券代码 |
    | reference\_type | [SecurityReferenceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2911) | 要获得的相关数据 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回证券的关联数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   证券的关联数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 证券代码 |
        | lot\_size | int | 每手股数，期货表示合约乘数 |
        | stock\_type | [SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325) | 证券类型 |
        | stock\_name | str | 证券名字 |
        | list\_time | str | 上市时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间 |
        | wrt\_valid | bool | 是否是窝轮<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>若为 True，下面 wrt 开头的字段有效 |
        | wrt\_type | [WrtType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926) | 窝轮类型 |
        | wrt\_code | str | 所属正股 |
        | future\_valid | bool | 是否是期货<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>若为 True，以下 future 开头的字段有效 |
        | future\_main\_contract | bool | 是否主连合约<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货特有字段 |
        | future\_last\_trade\_time | str | 最后交易时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货特有字段  <br>主连，当月，下月等无该字段 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    # 获取正股相关的窝轮
    ret, data = quote_ctx.get_referencestock_list('HK.00700', SecurityReferenceType.WARRANT)
    if ret == RET_OK:
        print(data)
        print(data['code'][0])    # 取第一条的股票代码
        print(data['code'].values.tolist())   # 转为 list
    else:
        print('error:', data)
    print('******************************************')
    # 港期相关合约
    ret, data = quote_ctx.get_referencestock_list('HK.A50main', SecurityReferenceType.FUTURE)
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

*   **Output**

            code  lot_size stock_type stock_name   list_time  wrt_valid wrt_type  wrt_code  future_valid  future_main_contract  future_last_trade_time
    0     HK.24719      1000    WARRANT    腾讯东亚九四沽A  2018-07-20       True      PUT  HK.00700         False                   NaN                     NaN
    ..         ...       ...        ...                ...       ...        ...       ...       ...           ...                   ...                    ...
    1617  HK.63402     10000    WARRANT    腾讯高盛一八牛Y  2020-11-26       True     BULL  HK.00700         False                   NaN                     NaN
    
    [1618 rows x 11 columns]
    HK.24719
    ['HK.24719', 'HK.27886', 'HK.28621', 'HK.14339', 'HK.27952', 'HK.18693', 'HK.20306', 'HK.53635', 'HK.47269', 'HK.27227', \
    ...        ...       ...        ...        ...         ...        ...      ...       ... \
    'HK.63402']
    ******************************************
            code  lot_size stock_type         stock_name list_time  wrt_valid  wrt_type  wrt_code  future_valid  future_main_contract future_last_trade_time
    0  HK.A50main      5000     FUTURE      安硕富时 A50 ETF主连(2012)                False       NaN       NaN          True                  True                       
    ..         ...       ...        ...                ...       ...        ...       ...       ...           ...                   ...                    ...
    5  HK.A502106      5000     FUTURE      安硕富时 A50 ETF2106                False       NaN       NaN          True                 False             2021-06-29
    
    [6 rows x 11 columns]
    HK.A50main
    ['HK.A50main', 'HK.A502011', 'HK.A502012', 'HK.A502101', 'HK.A502103', 'HK.A502106']
    

1  
2  
3  
4  
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

[#](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html#6602)
 Qot\_GetReference.proto
-------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取证券的关联数据，如：获取正股相关窝轮、获取期货相关合约
    
*   **协议 ID**
    
    3206
    
*   **参数**
    

    enum ReferenceType
    {
    	ReferenceType_Unknow = 0; 
    	ReferenceType_Warrant = 1; //正股相关的窝轮
    	ReferenceType_Future = 2; //期货主连的相关合约
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 referenceType = 2; // ReferenceType，相关类型
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 2; //相关股票列表
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3206
    

`uint GetReference(QotGetReference.Request req);`  
`virtual void OnReply_GetReference(FTAPI_Conn client, uint nSerialNo, QotGetReference.Response rsp);`

*   **介绍**
    
    获取证券的关联数据，如：获取正股相关窝轮、获取期货相关合约
    
*   **参数**
    

    enum ReferenceType
    {
    	ReferenceType_Unknow = 0; 
    	ReferenceType_Warrant = 1; //正股相关的窝轮
    	ReferenceType_Future = 2; //期货主连的相关合约
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 referenceType = 2; // ReferenceType，相关类型
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 2; //相关股票列表
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
            QotGetReference.C2S c2s = QotGetReference.C2S.CreateBuilder()
                    .SetSecurity(sec)
                    .SetReferenceType(QotGetReference.ReferenceType.ReferenceType_Warrant)
                .Build();
            QotGetReference.Request req = QotGetReference.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetReference(req);
            Console.Write("Send QotGetReference: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetReference(FTAPI_Conn client, uint nSerialNo, QotGetReference.Response rsp)
        {
            Console.Write("Reply: QotGetReference: {0}\n", nSerialNo);
            Console.Write("name: {0}, secType: {1} \n", rsp.S2C.StaticInfoListList[0].Basic.Name,
                rsp.S2C.StaticInfoListList[0].Basic.SecType);
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

    Qot onInitConnect: ret=0 desc= connID=6825716009073059706
    Send QotGetReference: 3
    Reply: QotGetReference: 3
    name: 腾讯东亚九四沽A, secType: 5
    

1  
2  
3  
4  

`int getReference(QotGetReference.Request req);`  
`void onReply_GetReference(FTAPI_Conn client, int nSerialNo, QotGetReference.Response rsp);`

*   **介绍**
    
    获取证券的关联数据，如：获取正股相关窝轮、获取期货相关合约
    
*   **参数**
    

    enum ReferenceType
    {
    	ReferenceType_Unknow = 0; 
    	ReferenceType_Warrant = 1; //正股相关的窝轮
    	ReferenceType_Future = 2; //期货主连的相关合约
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 referenceType = 2; // ReferenceType，相关类型
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 2; //相关股票列表
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
            QotGetReference.C2S c2s = QotGetReference.C2S.newBuilder()
                    .setSecurity(sec)
                    .setReferenceType(QotGetReference.ReferenceType.ReferenceType_Warrant_VALUE)
                .build();
            QotGetReference.Request req = QotGetReference.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getReference(req);
            System.out.printf("Send QotGetReference: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetReference(FTAPI_Conn client, int nSerialNo, QotGetReference.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetReference failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetReference: %s\n", json);
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

    Send QotGetReference: 2
    Receive QotGetReference: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "staticInfoList": [{\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "24719"\
            },\
            "id": "76128295346319",\
            "lotSize": 1000,\
            "secType": 5,\
            "name": "腾讯东亚九四沽A",\
            "listTime": "2018-07-20",\
            "delisting": false,\
            "listTimestamp": 1.532016E9\
          },\
          "warrantExData": {\
            "type": 2,\
            "owner": {\
              "market": 1,\
              "code": "00700"\
            }\
          }\
        }, ... {\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "55138"\
            },\
            "id": "80749680187234",\
            "lotSize": 5000,\
            "secType": 5,\
            "name": "腾讯瑞通二一牛E",\
            "listTime": "2021-06-28",\
            "delisting": false,\
            "listTimestamp": 1.6248096E9\
          },\
          "warrantExData": {\
            "type": 3,\
            "owner": {\
              "market": 1,\
              "code": "00700"\
            }\
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
46  
47  
48  
49  
50  
51  

`Futu::u32_t GetReference(const Qot_GetReference::Request &stReq);`  
`virtual void OnReply_GetReference(Futu::u32_t nSerialNo, const Qot_GetReference::Response &stRsp) = 0;`

*   **介绍**
    
    获取证券的关联数据，如：获取正股相关窝轮、获取期货相关合约
    
*   **参数**
    

    enum ReferenceType
    {
    	ReferenceType_Unknow = 0; 
    	ReferenceType_Warrant = 1; //正股相关的窝轮
    	ReferenceType_Future = 2; //期货主连的相关合约
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 referenceType = 2; // ReferenceType，相关类型
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 2; //相关股票列表
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
    		Qot_GetReference::Request req;
    		Qot_GetReference::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->set_referencetype(1);
    
    	    m_GetReferenceSerialNo = m_pQotApi->GetReference(req);
    	    cout << "Request GetReference SerialNo: " << m_GetReferenceSerialNo << endl;
    	}
    
    	virtual void OnReply_GetReference(Futu::u32_t nSerialNo, const Qot_GetReference::Response &stRsp){
    	    if(nSerialNo == m_GetReferenceSerialNo)
    	    {
    	        cout << "OnReply_GetReference SerialNo: " << nSerialNo << endl;
    	        // 解析内部结构打印出来
    	        // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
    	        string resp_str;
    	        ProtoBufToBodyData(stRsp, resp_str);
    	        cout << UTF8ToLocal(resp_str) << endl;
    	    }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
      Futu::u32_t m_GetReferenceSerialNo;
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
    Request GetReference SerialNo: 4
    OnReply_GetReference SerialNo: 4
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
          "code": "24719"\
         },\
         "id": "76128295346319",\
         "lotSize": 1000,\
         "secType": 5,\
         "name": "腾讯东亚九四沽A",\
         "listTime": "2018-07-20",\
         "delisting": false,\
         "listTimestamp": 1532016000\
        },\
        "warrantExData": {\
         "type": 2,\
         "owner": {\
          "market": 1,\
          "code": "00700"\
         }\
        }\
       },\
    ...\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "69950"\
         },\
         "id": "80685255692606",\
         "lotSize": 5000,\
         "secType": 5,\
         "name": "腾讯摩利二二熊B",\
         "listTime": "2021-06-11",\
         "delisting": false,\
         "listTimestamp": 1623340800\
        },\
        "warrantExData": {\
         "type": 4,\
         "owner": {\
          "market": 1,\
          "code": "00700"\
         }\
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
55  
56  
57  
58  

`GetReference(req);`

*   **介绍**
    
    获取证券的关联数据，如：获取正股相关窝轮、获取期货相关合约
    
*   **参数**
    

    enum ReferenceType
    {
    	ReferenceType_Unknow = 0; 
    	ReferenceType_Warrant = 1; //正股相关的窝轮
    	ReferenceType_Future = 2; //期货主连的相关合约
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 referenceType = 2; // ReferenceType，相关类型
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 2; //相关股票列表
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Qot_GetReference } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetReference(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        const { ReferenceType } = Qot_GetReference
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        security:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                        referenceType: ReferenceType.ReferenceType_Warrant,
                    },
                };
    
                websocket.GetReference(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("Reference: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    Reference: errCode 0, retMsg , retType 0
    {
      "staticInfoList": [{\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "24719"\
          },\
          "id": "76128295346319",\
          "lotSize": 1000,\
          "secType": 5,\
          "name": "腾讯东亚九四沽A",\
          "listTime": "2018-07-20",\
          "delisting": false,\
          "listTimestamp": 1532016000\
        },\
        "warrantExData": {\
          "type": 2,\
          "owner": {\
            "market": 1,\
            "code": "00700"\
          }\
        }\
      }, {\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "27227"\
          },\
          "id": "77919296711259",\
          "lotSize": 10000,\
          "secType": 5,\
          "name": "腾讯麦银一九购A.C",\
          "listTime": "2019-09-10",\
          "delisting": false,\
          "listTimestamp": 1568044800\
        },\
        "warrantExData": {\
          "type": 1,\
          "owner": {\
            "market": 1,\
            "code": "00700"\
          }\
        }\
      }, ..., {\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "53501"\
          },\
          "id": "81084687634685",\
          "lotSize": 5000,\
          "secType": 5,\
          "name": "腾讯法兴二五牛I",\
          "listTime": "2021-09-14",\
          "delisting": false,\
          "listTimestamp": 1631548800\
        },\
        "warrantExData": {\
          "type": 3,\
          "owner": {\
            "market": 1,\
            "code": "00700"\
          }\
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
64  
65  
66  
67  
68  

接口限制

*   每 30 秒内最多请求 10 次获取证券关联数据接口
*   当获取正股相关窝轮时，不受上述限频限制

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_referencestock_list(code, reference_type)`

*   **介绍**
    
    获取证券的关联数据，如：获取正股相关窝轮、获取期货相关合约
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 证券代码 |
    | reference\_type | [SecurityReferenceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2911) | 要获得的相关数据 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回证券的关联数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   证券的关联数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 证券代码 |
        | lot\_size | int | 每手股数，期货表示合约乘数 |
        | stock\_type | [SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325) | 证券类型 |
        | stock\_name | str | 证券名字 |
        | list\_time | str | 上市时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间 |
        | wrt\_valid | bool | 是否是窝轮<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>若为 True，下面 wrt 开头的字段有效 |
        | wrt\_type | [WrtType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926) | 窝轮类型 |
        | wrt\_code | str | 所属正股 |
        | future\_valid | bool | 是否是期货<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>若为 True，以下 future 开头的字段有效 |
        | future\_main\_contract | bool | 是否主连合约<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货特有字段 |
        | future\_last\_trade\_time | str | 最后交易时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货特有字段  <br>主连，当月，下月等无该字段 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    # 获取正股相关的窝轮
    ret, data = quote_ctx.get_referencestock_list('HK.00700', SecurityReferenceType.WARRANT)
    if ret == RET_OK:
        print(data)
        print(data['code'][0])    # 取第一条的股票代码
        print(data['code'].values.tolist())   # 转为 list
    else:
        print('error:', data)
    print('******************************************')
    # 港期相关合约
    ret, data = quote_ctx.get_referencestock_list('HK.A50main', SecurityReferenceType.FUTURE)
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

*   **Output**

            code  lot_size stock_type stock_name   list_time  wrt_valid wrt_type  wrt_code  future_valid  future_main_contract  future_last_trade_time
    0     HK.24719      1000    WARRANT    腾讯东亚九四沽A  2018-07-20       True      PUT  HK.00700         False                   NaN                     NaN
    ..         ...       ...        ...                ...       ...        ...       ...       ...           ...                   ...                    ...
    1617  HK.63402     10000    WARRANT    腾讯高盛一八牛Y  2020-11-26       True     BULL  HK.00700         False                   NaN                     NaN
    
    [1618 rows x 11 columns]
    HK.24719
    ['HK.24719', 'HK.27886', 'HK.28621', 'HK.14339', 'HK.27952', 'HK.18693', 'HK.20306', 'HK.53635', 'HK.47269', 'HK.27227', \
    ...        ...       ...        ...        ...         ...        ...      ...       ... \
    'HK.63402']
    ******************************************
            code  lot_size stock_type         stock_name list_time  wrt_valid  wrt_type  wrt_code  future_valid  future_main_contract future_last_trade_time
    0  HK.A50main      5000     FUTURE      安硕富时 A50 ETF主连(2012)                False       NaN       NaN          True                  True                       
    ..         ...       ...        ...                ...       ...        ...       ...       ...           ...                   ...                    ...
    5  HK.A502106      5000     FUTURE      安硕富时 A50 ETF2106                False       NaN       NaN          True                 False             2021-06-29
    
    [6 rows x 11 columns]
    HK.A50main
    ['HK.A50main', 'HK.A502011', 'HK.A502012', 'HK.A502101', 'HK.A502103', 'HK.A502106']
    

1  
2  
3  
4  
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

[#](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html#6602-2)
 Qot\_GetReference.proto
---------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取证券的关联数据，如：获取正股相关窝轮、获取期货相关合约
    
*   **协议 ID**
    
    3206
    
*   **参数**
    

    enum ReferenceType
    {
    	ReferenceType_Unknow = 0; 
    	ReferenceType_Warrant = 1; //正股相关的窝轮
    	ReferenceType_Future = 2; //期货主连的相关合约
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 referenceType = 2; // ReferenceType，相关类型
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 2; //相关股票列表
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3206
    

`uint GetReference(QotGetReference.Request req);`  
`virtual void OnReply_GetReference(MMAPI_Conn client, uint nSerialNo, QotGetReference.Response rsp);`

*   **介绍**
    
    获取证券的关联数据，如：获取正股相关窝轮、获取期货相关合约
    
*   **参数**
    

    enum ReferenceType
    {
    	ReferenceType_Unknow = 0; 
    	ReferenceType_Warrant = 1; //正股相关的窝轮
    	ReferenceType_Future = 2; //期货主连的相关合约
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 referenceType = 2; // ReferenceType，相关类型
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 2; //相关股票列表
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
            QotGetReference.C2S c2s = QotGetReference.C2S.CreateBuilder()
                    .SetSecurity(sec)
                    .SetReferenceType(QotGetReference.ReferenceType.ReferenceType_Warrant)
                .Build();
            QotGetReference.Request req = QotGetReference.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetReference(req);
            Console.Write("Send QotGetReference: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetReference(MMAPI_Conn client, uint nSerialNo, QotGetReference.Response rsp)
        {
            Console.Write("Reply: QotGetReference: {0}\n", nSerialNo);
            Console.Write("name: {0}, secType: {1} \n", rsp.S2C.StaticInfoListList[0].Basic.Name,
                rsp.S2C.StaticInfoListList[0].Basic.SecType);
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

    Qot onInitConnect: ret=0 desc= connID=6825716009073059706
    Send QotGetReference: 3
    Reply: QotGetReference: 3
    name: 腾讯东亚九四沽A, secType: 5
    

1  
2  
3  
4  

`int getReference(QotGetReference.Request req);`  
`void onReply_GetReference(MMAPI_Conn client, int nSerialNo, QotGetReference.Response rsp);`

*   **介绍**
    
    获取证券的关联数据，如：获取正股相关窝轮、获取期货相关合约
    
*   **参数**
    

    enum ReferenceType
    {
    	ReferenceType_Unknow = 0; 
    	ReferenceType_Warrant = 1; //正股相关的窝轮
    	ReferenceType_Future = 2; //期货主连的相关合约
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 referenceType = 2; // ReferenceType，相关类型
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 2; //相关股票列表
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
            QotGetReference.C2S c2s = QotGetReference.C2S.newBuilder()
                    .setSecurity(sec)
                    .setReferenceType(QotGetReference.ReferenceType.ReferenceType_Warrant_VALUE)
                .build();
            QotGetReference.Request req = QotGetReference.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getReference(req);
            System.out.printf("Send QotGetReference: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetReference(MMAPI_Conn client, int nSerialNo, QotGetReference.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetReference failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetReference: %s\n", json);
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

    Send QotGetReference: 2
    Receive QotGetReference: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "staticInfoList": [{\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "24719"\
            },\
            "id": "76128295346319",\
            "lotSize": 1000,\
            "secType": 5,\
            "name": "腾讯东亚九四沽A",\
            "listTime": "2018-07-20",\
            "delisting": false,\
            "listTimestamp": 1.532016E9\
          },\
          "warrantExData": {\
            "type": 2,\
            "owner": {\
              "market": 1,\
              "code": "00700"\
            }\
          }\
        }, ... {\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "55138"\
            },\
            "id": "80749680187234",\
            "lotSize": 5000,\
            "secType": 5,\
            "name": "腾讯瑞通二一牛E",\
            "listTime": "2021-06-28",\
            "delisting": false,\
            "listTimestamp": 1.6248096E9\
          },\
          "warrantExData": {\
            "type": 3,\
            "owner": {\
              "market": 1,\
              "code": "00700"\
            }\
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
46  
47  
48  
49  
50  
51  

`moomoo::u32_t GetReference(const Qot_GetReference::Request &stReq);`  
`virtual void OnReply_GetReference(moomoo::u32_t nSerialNo, const Qot_GetReference::Response &stRsp) = 0;`

*   **介绍**
    
    获取证券的关联数据，如：获取正股相关窝轮、获取期货相关合约
    
*   **参数**
    

    enum ReferenceType
    {
    	ReferenceType_Unknow = 0; 
    	ReferenceType_Warrant = 1; //正股相关的窝轮
    	ReferenceType_Future = 2; //期货主连的相关合约
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 referenceType = 2; // ReferenceType，相关类型
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 2; //相关股票列表
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
    		Qot_GetReference::Request req;
    		Qot_GetReference::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->set_referencetype(1);
    
    	    m_GetReferenceSerialNo = m_pQotApi->GetReference(req);
    	    cout << "Request GetReference SerialNo: " << m_GetReferenceSerialNo << endl;
    	}
    
    	virtual void OnReply_GetReference(moomoo::u32_t nSerialNo, const Qot_GetReference::Response &stRsp){
    	    if(nSerialNo == m_GetReferenceSerialNo)
    	    {
    	        cout << "OnReply_GetReference SerialNo: " << nSerialNo << endl;
    	        // 解析内部结构打印出来
    	        // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
    	        string resp_str;
    	        ProtoBufToBodyData(stRsp, resp_str);
    	        cout << UTF8ToLocal(resp_str) << endl;
    	    }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
      moomoo::u32_t m_GetReferenceSerialNo;
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
    Request GetReference SerialNo: 4
    OnReply_GetReference SerialNo: 4
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
          "code": "24719"\
         },\
         "id": "76128295346319",\
         "lotSize": 1000,\
         "secType": 5,\
         "name": "腾讯东亚九四沽A",\
         "listTime": "2018-07-20",\
         "delisting": false,\
         "listTimestamp": 1532016000\
        },\
        "warrantExData": {\
         "type": 2,\
         "owner": {\
          "market": 1,\
          "code": "00700"\
         }\
        }\
       },\
    ...\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "69950"\
         },\
         "id": "80685255692606",\
         "lotSize": 5000,\
         "secType": 5,\
         "name": "腾讯摩利二二熊B",\
         "listTime": "2021-06-11",\
         "delisting": false,\
         "listTimestamp": 1623340800\
        },\
        "warrantExData": {\
         "type": 4,\
         "owner": {\
          "market": 1,\
          "code": "00700"\
         }\
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
55  
56  
57  
58  

`GetReference(req);`

*   **介绍**
    
    获取证券的关联数据，如：获取正股相关窝轮、获取期货相关合约
    
*   **参数**
    

    enum ReferenceType
    {
    	ReferenceType_Unknow = 0; 
    	ReferenceType_Warrant = 1; //正股相关的窝轮
    	ReferenceType_Future = 2; //期货主连的相关合约
    }
    
    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 referenceType = 2; // ReferenceType，相关类型
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 2; //相关股票列表
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Qot_GetReference } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetReference(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        const { ReferenceType } = Qot_GetReference
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        security:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                        referenceType: ReferenceType.ReferenceType_Warrant,
                    },
                };
    
                websocket.GetReference(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("Reference: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    Reference: errCode 0, retMsg , retType 0
    {
      "staticInfoList": [{\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "24719"\
          },\
          "id": "76128295346319",\
          "lotSize": 1000,\
          "secType": 5,\
          "name": "腾讯东亚九四沽A",\
          "listTime": "2018-07-20",\
          "delisting": false,\
          "listTimestamp": 1532016000\
        },\
        "warrantExData": {\
          "type": 2,\
          "owner": {\
            "market": 1,\
            "code": "00700"\
          }\
        }\
      }, {\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "27227"\
          },\
          "id": "77919296711259",\
          "lotSize": 10000,\
          "secType": 5,\
          "name": "腾讯麦银一九购A.C",\
          "listTime": "2019-09-10",\
          "delisting": false,\
          "listTimestamp": 1568044800\
        },\
        "warrantExData": {\
          "type": 1,\
          "owner": {\
            "market": 1,\
            "code": "00700"\
          }\
        }\
      }, ..., {\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "53501"\
          },\
          "id": "81084687634685",\
          "lotSize": 5000,\
          "secType": 5,\
          "name": "腾讯法兴二五牛I",\
          "listTime": "2021-09-14",\
          "delisting": false,\
          "listTimestamp": 1631548800\
        },\
        "warrantExData": {\
          "type": 3,\
          "owner": {\
            "market": 1,\
            "code": "00700"\
          }\
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
64  
65  
66  
67  
68  

接口限制

*   每 30 秒内最多请求 10 次获取证券关联数据接口
*   当获取正股相关窝轮时，不受上述限频限制

← [筛选窝轮](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html) [获取期货合约资料](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html)
 →

[获取窝轮和期货列表](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html)