 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-user-security.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-user-security.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-user-security.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-user-security.html)
    

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
        
    *   个性化
        
        *   [获取历史 K 线额度使用明细](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html)
            
        *   [设置到价提醒](https://openapi.futunn.com/futu-api-doc/quote/set-price-reminder.html)
            
        *   [获取到价提醒列表](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html)
            
        *   [获取自选股列表](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html)
            
        *   [获取自选股分组](https://openapi.futunn.com/futu-api-doc/quote/get-user-security-group.html)
            
        *   [修改自选股列表](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html)
            
        *   [到价提醒回调](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html)
            
        
    *   [行情定义](https://openapi.futunn.com/futu-api-doc/quote/quote.html)
        
    
*   交易接口
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html#6081)
 获取自选股列表
=======================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_user_security(group_name)`

*   **介绍**
    
    获取指定分组的自选股列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | group\_name | str | 需要查询的自选股分组名称 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回自选股数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   自选股数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 名字  |
        | lot\_size | int | 每手股数，期权表示每份合约股数，期货表示合约乘数 |
        | stock\_type | [SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325) | 股票类型 |
        | stock\_child\_type | [WrtType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926) | 窝轮子类型 |
        | stock\_owner | str | 窝轮所属正股的代码，或期权标的股的代码 |
        | option\_type | [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713) | 期权类型 |
        | strike\_time | str | 期权行权日<br>(ℹ️ 格式：yyyy-MM-dd  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | strike\_price | float | 期权行权价 |
        | suspension | bool | 期权是否停牌<br>(ℹ️ True：停牌) |
        | listing\_date | str | 上市时间<br>(ℹ️ 格式：yyyy-MM-dd) |
        | stock\_id | int | 股票 ID |
        | delisting | bool | 是否退市 |
        | main\_contract | bool | 是否主连合约 |
        | last\_trade\_time | str | 最后交易时间<br>(ℹ️ 主连，当月，下月等期货没有此字段) |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_user_security("A")
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果自选股列表不为空
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

*   **Output**

        code    name  lot_size stock_type stock_child_type stock_owner option_type strike_time strike_price suspension listing_date        stock_id  delisting  main_contract last_trade_time
    0  HK.HSImain  恒指期货主连        50     FUTURE              N/A                                              N/A        N/A                     71000662      False           True                
    1  HK.00700    腾讯控股       100      STOCK              N/A                                              N/A        N/A   2004-06-16  54047868453564      False          False                
    HK.HSImain
    ['HK.HSImain', 'HK.00700']
    

1  
2  
3  
4  
5  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html#2143)
 Qot\_GetUserSecurity.proto
----------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取指定分组的自选股列表
    
*   **参数**
    

    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序首个
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

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //自选股分组下的股票列表
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
    
    3213
    

`uint GetUserSecurity(QotGetUserSecurity.Request req);`  
`virtual void OnReply_GetUserSecurity(FTAPI_Conn client, uint nSerialNo, QotGetUserSecurity.Response rsp);`

*   **介绍**
    
    获取指定分组的自选股列表
    
*   **参数**
    

    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序首个
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

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //自选股分组下的股票列表
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
    
            QotGetUserSecurity.C2S c2s = QotGetUserSecurity.C2S.CreateBuilder()
                    .SetGroupName("some_group")
                .Build();
            QotGetUserSecurity.Request req = QotGetUserSecurity.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetUserSecurity(req);
            Console.Write("Send QotGetUserSecurity: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetUserSecurity(FTAPI_Conn client, uint nSerialNo, QotGetUserSecurity.Response rsp)
        {
            Console.Write("Reply: QotGetUserSecurity: {0}\n", nSerialNo);
            if(rsp.S2C.StaticInfoListCount > 0)
            {
                Console.Write("name: {0} \n", rsp.S2C.StaticInfoListList[0].Basic.Name);
            }            
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826787829878316131
    Send QotGetUserSecurity: 3
    Reply: QotGetUserSecurity: 3
    name: 腾讯控股
    

1  
2  
3  
4  

`int getUserSecurity(QotGetUserSecurity.Request req);`  
`void onReply_GetUserSecurity(FTAPI_Conn client, int nSerialNo, QotGetUserSecurity.Response rsp);`

*   **介绍**
    
    获取指定分组的自选股列表
    
*   **参数**
    

    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序首个
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

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //自选股分组下的股票列表
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
    
            QotGetUserSecurity.C2S c2s = QotGetUserSecurity.C2S.newBuilder()
                    .setGroupName("some_group")
                .build();
            QotGetUserSecurity.Request req = QotGetUserSecurity.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getUserSecurity(req);
            System.out.printf("Send QotGetUserSecurity: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetUserSecurity(FTAPI_Conn client, int nSerialNo, QotGetUserSecurity.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetUserSecurity failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetUserSecurity: %s\n", json);
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

*   **Output**

    Send QotGetUserSecurity: 2
    Receive QotGetUserSecurity: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "staticInfoList": [{\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "09626"\
            },\
            "id": "80328773346714",\
            "lotSize": 20,\
            "secType": 3,\
            "name": "哔哩哔哩-SW",\
            "listTime": "2021-03-29",\
            "delisting": false,\
            "listTimestamp": 1.6169472E9\
          }\
        }, ... {\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "800000"\
            },\
            "id": "800000",\
            "lotSize": 0,\
            "secType": 6,\
            "name": "恒生指数",\
            "listTime": "1970-01-01",\
            "delisting": false,\
            "listTimestamp": 0.0\
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

`Futu::u32_t GetUserSecurity(const Qot_GetUserSecurity::Request &stReq);`  
`virtual void OnReply_GetUserSecurity(Futu::u32_t nSerialNo, const Qot_GetUserSecurity::Response &stRsp) = 0;`

*   **介绍**
    
    获取指定分组的自选股列表
    
*   **参数**
    

    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序首个
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

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //自选股分组下的股票列表
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
    		Qot_GetUserSecurity::Request req;
    		Qot_GetUserSecurity::C2S *c2s = req.mutable_c2s();
    		c2s->set_groupname("some_group");
    
    		m_GetUserSecuritySerialNo = m_pQotApi->GetUserSecurity(req);
    		cout << "Request GetUserSecurity SerialNo: " << m_GetUserSecuritySerialNo << endl;
    	}
    
    	virtual void OnReply_GetUserSecurity(Futu::u32_t nSerialNo, const Qot_GetUserSecurity::Response &stRsp){
            if(nSerialNo == m_GetUserSecuritySerialNo)
            {
                cout << "OnReply_GetUserSecurity SerialNo:" << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetUserSecuritySerialNo;
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

*   **Output**

    connect
    Request GetUserSecurity SerialNo: 4
    OnReply_GetUserSecurity SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "staticInfoList": [\
       {\
        "basic": {\
         "security": {\
          "market": 11,\
          "code": "FUTU"\
         },\
         "id": "78103980495165",\
         "lotSize": 1,\
         "secType": 3,\
         "name": "富途控股",\
         "listTime": "2019-03-08",\
         "delisting": false,\
         "listTimestamp": 1552021200\
        }\
       },\
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
         "listTimestamp": 1087315200\
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

`GetUserSecurity(req);`

*   **介绍**
    
    获取指定分组的自选股列表
    
*   **参数**
    

    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序首个
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

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //自选股分组下的股票列表
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
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetUserSecurity(){
        const { RetType } = Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        groupName: "港股",
                    },
                };
                
                websocket.GetUserSecurity(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("UserSecurity: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    UserSecurity: errCode 0, retMsg , retType 0
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
      }, {\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "09988"\
          },\
          "id": "78224239372036",\
          "lotSize": 100,\
          "secType": 3,\
          "name": "阿里巴巴-SW",\
          "listTime": "2019-11-26",\
          "delisting": false,\
          "listTimestamp": 1574697600\
        }\
      }, {\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "800000"\
          },\
          "id": "800000",\
          "lotSize": 0,\
          "secType": 6,\
          "name": "恒生指数",\
          "listTime": "1970-01-01",\
          "delisting": false,\
          "listTimestamp": 0\
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

提示

系统分组的中英文对应名称如下

| 中文  | 英文  |
| --- | --- |
| 全部  | All |
| 沪深  | CN  |
| 港股  | HK  |
| 美股  | US  |
| 期权  | Options |
| 港股期权 | HK options |
| 美股期权 | US options |
| 特别关注 | Starred |
| 期货  | Futures |

接口限制

*   每 30 秒内最多请求 10 次获取自选股列表接口
*   不支持持仓（Positions），基金宝（Mutual Fund），外汇（Forex）分组的查询

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_user_security(group_name)`

*   **介绍**
    
    获取指定分组的自选股列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | group\_name | str | 需要查询的自选股分组名称 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回自选股数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   自选股数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 名字  |
        | lot\_size | int | 每手股数，期权表示每份合约股数，期货表示合约乘数 |
        | stock\_type | [SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325) | 股票类型 |
        | stock\_child\_type | [WrtType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926) | 窝轮子类型 |
        | stock\_owner | str | 窝轮所属正股的代码，或期权标的股的代码 |
        | option\_type | [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713) | 期权类型 |
        | strike\_time | str | 期权行权日<br>(ℹ️ 格式：yyyy-MM-dd  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | strike\_price | float | 期权行权价 |
        | suspension | bool | 期权是否停牌<br>(ℹ️ True：停牌) |
        | listing\_date | str | 上市时间<br>(ℹ️ 格式：yyyy-MM-dd) |
        | stock\_id | int | 股票 ID |
        | delisting | bool | 是否退市 |
        | main\_contract | bool | 是否主连合约 |
        | last\_trade\_time | str | 最后交易时间<br>(ℹ️ 主连，当月，下月等期货没有此字段) |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_user_security("A")
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果自选股列表不为空
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

*   **Output**

        code    name  lot_size stock_type stock_child_type stock_owner option_type strike_time strike_price suspension listing_date        stock_id  delisting  main_contract last_trade_time
    0  HK.HSImain  恒指期货主连        50     FUTURE              N/A                                              N/A        N/A                     71000662      False           True                
    1  HK.00700    腾讯控股       100      STOCK              N/A                                              N/A        N/A   2004-06-16  54047868453564      False          False                
    HK.HSImain
    ['HK.HSImain', 'HK.00700']
    

1  
2  
3  
4  
5  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html#2143-2)
 Qot\_GetUserSecurity.proto
------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取指定分组的自选股列表
    
*   **参数**
    

    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序首个
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

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //自选股分组下的股票列表
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
    
    3213
    

`uint GetUserSecurity(QotGetUserSecurity.Request req);`  
`virtual void OnReply_GetUserSecurity(MMAPI_Conn client, uint nSerialNo, QotGetUserSecurity.Response rsp);`

*   **介绍**
    
    获取指定分组的自选股列表
    
*   **参数**
    

    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序首个
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

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //自选股分组下的股票列表
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
    
            QotGetUserSecurity.C2S c2s = QotGetUserSecurity.C2S.CreateBuilder()
                    .SetGroupName("some_group")
                .Build();
            QotGetUserSecurity.Request req = QotGetUserSecurity.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetUserSecurity(req);
            Console.Write("Send QotGetUserSecurity: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetUserSecurity(MMAPI_Conn client, uint nSerialNo, QotGetUserSecurity.Response rsp)
        {
            Console.Write("Reply: QotGetUserSecurity: {0}\n", nSerialNo);
            if(rsp.S2C.StaticInfoListCount > 0)
            {
                Console.Write("name: {0} \n", rsp.S2C.StaticInfoListList[0].Basic.Name);
            }            
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826787829878316131
    Send QotGetUserSecurity: 3
    Reply: QotGetUserSecurity: 3
    name: 腾讯控股
    

1  
2  
3  
4  

`int getUserSecurity(QotGetUserSecurity.Request req);`  
`void onReply_GetUserSecurity(MMAPI_Conn client, int nSerialNo, QotGetUserSecurity.Response rsp);`

*   **介绍**
    
    获取指定分组的自选股列表
    
*   **参数**
    

    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序首个
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

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //自选股分组下的股票列表
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
    
            QotGetUserSecurity.C2S c2s = QotGetUserSecurity.C2S.newBuilder()
                    .setGroupName("some_group")
                .build();
            QotGetUserSecurity.Request req = QotGetUserSecurity.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getUserSecurity(req);
            System.out.printf("Send QotGetUserSecurity: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetUserSecurity(MMAPI_Conn client, int nSerialNo, QotGetUserSecurity.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetUserSecurity failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetUserSecurity: %s\n", json);
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

*   **Output**

    Send QotGetUserSecurity: 2
    Receive QotGetUserSecurity: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "staticInfoList": [{\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "09626"\
            },\
            "id": "80328773346714",\
            "lotSize": 20,\
            "secType": 3,\
            "name": "哔哩哔哩-SW",\
            "listTime": "2021-03-29",\
            "delisting": false,\
            "listTimestamp": 1.6169472E9\
          }\
        }, ... {\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "800000"\
            },\
            "id": "800000",\
            "lotSize": 0,\
            "secType": 6,\
            "name": "恒生指数",\
            "listTime": "1970-01-01",\
            "delisting": false,\
            "listTimestamp": 0.0\
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

`moomoo::u32_t GetUserSecurity(const Qot_GetUserSecurity::Request &stReq);`  
`virtual void OnReply_GetUserSecurity(moomoo::u32_t nSerialNo, const Qot_GetUserSecurity::Response &stRsp) = 0;`

*   **介绍**
    
    获取指定分组的自选股列表
    
*   **参数**
    

    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序首个
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

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //自选股分组下的股票列表
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
    		Qot_GetUserSecurity::Request req;
    		Qot_GetUserSecurity::C2S *c2s = req.mutable_c2s();
    		c2s->set_groupname("some_group");
    
    		m_GetUserSecuritySerialNo = m_pQotApi->GetUserSecurity(req);
    		cout << "Request GetUserSecurity SerialNo: " << m_GetUserSecuritySerialNo << endl;
    	}
    
    	virtual void OnReply_GetUserSecurity(moomoo::u32_t nSerialNo, const Qot_GetUserSecurity::Response &stRsp){
            if(nSerialNo == m_GetUserSecuritySerialNo)
            {
                cout << "OnReply_GetUserSecurity SerialNo:" << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetUserSecuritySerialNo;
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

*   **Output**

    connect
    Request GetUserSecurity SerialNo: 4
    OnReply_GetUserSecurity SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "staticInfoList": [\
       {\
        "basic": {\
         "security": {\
          "market": 11,\
          "code": "FUTU"\
         },\
         "id": "78103980495165",\
         "lotSize": 1,\
         "secType": 3,\
         "name": "富途控股",\
         "listTime": "2019-03-08",\
         "delisting": false,\
         "listTimestamp": 1552021200\
        }\
       },\
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
         "listTimestamp": 1087315200\
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

`GetUserSecurity(req);`

*   **介绍**
    
    获取指定分组的自选股列表
    
*   **参数**
    

    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序首个
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

*   **返回**

    message S2C
    {
    	repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //自选股分组下的股票列表
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
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetUserSecurity(){
        const { RetType } = Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        groupName: "港股",
                    },
                };
                
                websocket.GetUserSecurity(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("UserSecurity: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    UserSecurity: errCode 0, retMsg , retType 0
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
      }, {\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "09988"\
          },\
          "id": "78224239372036",\
          "lotSize": 100,\
          "secType": 3,\
          "name": "阿里巴巴-SW",\
          "listTime": "2019-11-26",\
          "delisting": false,\
          "listTimestamp": 1574697600\
        }\
      }, {\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "800000"\
          },\
          "id": "800000",\
          "lotSize": 0,\
          "secType": 6,\
          "name": "恒生指数",\
          "listTime": "1970-01-01",\
          "delisting": false,\
          "listTimestamp": 0\
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

提示

系统分组的中英文对应名称如下

| 中文  | 英文  |
| --- | --- |
| 全部  | All |
| 沪深  | CN  |
| 港股  | HK  |
| 美股  | US  |
| 期权  | Options |
| 港股期权 | HK options |
| 美股期权 | US options |
| 特别关注 | Starred |
| 期货  | Futures |

接口限制

*   每 30 秒内最多请求 10 次获取自选股列表接口
*   不支持持仓（Positions），基金宝（Mutual Fund），外汇（Forex）分组的查询

← [获取到价提醒列表](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html) [获取自选股分组](https://openapi.futunn.com/futu-api-doc/quote/get-user-security-group.html)
 →

[获取自选股列表](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html)