# 获取实时分时 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quote/get-rt.html

[#](https://openapi.futunn.com/futu-api-doc/quote/get-rt.html#8852)
 获取实时分时
===========================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_rt_data(code)`

*   **介绍**
    
    获取已订阅股票的实时分时数据，必须要先订阅。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票  |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回分时数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   分时数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | time | str | 时间<br>(ℹ️ 格式：yyyy-MM-dd HH:mm:ss  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | is\_blank | bool | 数据状态<br>(ℹ️ False：正常数据  <br>True：伪造数据) |
        | opened\_mins | int | 零点到当前多少分钟 |
        | cur\_price | float | 当前价格 |
        | last\_close | float | 昨天收盘的价格 |
        | avg\_price | float | 平均价格<br>(ℹ️ 对于期权，该字段为 N/A) |
        | volume | float | 成交量 |
        | turnover | float | 成交金额 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret_sub, err_message = quote_ctx.subscribe(['US.AAPL'], [SubType.RT_DATA], subscribe_push=False, session=Session.ALL)
    # 先订阅分时数据类型。订阅成功后 OpenD 将持续收到服务器的推送，False 代表暂时不需要推送给脚本
    if ret_sub == RET_OK:   # 订阅成功
        ret, data = quote_ctx.get_rt_data('US.AAPL')   # 获取一次分时数据
        if ret == RET_OK:
            print(data)
        else:
            print('error:', data)
    else:
        print('subscription failed', err_message)
    quote_ctx.close()   # 关闭当条连接，OpenD 会在1分钟后自动取消相应股票相应类型的订阅
    

1  
2  
3  
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

*   **Output**

    code  name                 time  is_blank  opened_mins  cur_price  last_close   avg_price   volume     turnover
    0    US.AAPL   苹果  2025-04-06 20:01:00     False         1201     183.00      188.38  181.643916    9463  1718896.38
    ..      ...    ...                  ...       ...          ...        ...         ...         ...      ...          ...
    586  US.AAPL   苹果  2025-04-07 05:47:00     False          347     181.26      188.38  180.555673     661   119859.75
    
    [587 rows x 10 columns]
    

1  
2  
3  
4  
5  
6  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-rt.html#4930)
 Qot\_GetRT.proto
-------------------------------------------------------------------------------------

*   **介绍**
    
    获取已订阅股票的实时分时数据，必须要先订阅。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
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

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.TimeShare rtList = 2; //分时数据结构体
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   分时结构参见 [TimeShare](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9906)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3008
    

`uint GetRT(QotGetRT.Request req);`  
`virtual void OnReply_GetRT(FTAPI_Conn client, uint nSerialNo, QotGetRT.Response rsp);`

*   **介绍**

获取已订阅股票的实时分时数据，必须要先订阅。

*   **参数**

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
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

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.TimeShare rtList = 2; //分时数据结构体
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   分时结构参见 [TimeShare](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9906)
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
            QotSub.C2S c2s = QotSub.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                    .AddSubTypeList((int)QotCommon.SubType.SubType_RT)
                    .SetIsSubOrUnSub(true)
                    .Build();
            QotSub.Request req = QotSub.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.Sub(req);
            Console.Write("Send QotSub: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_Sub(FTAPI_Conn client, uint nSerialNo, QotSub.Response rsp) {
            Console.Write("Reply: QotSub: {0}  {1}\n", nSerialNo, rsp.ToString());
    
            if (rsp.RetType != (int)Common.RetType.RetType_Succeed)
                return;
    
            QotCommon.Security sec = QotCommon.Security.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetCode("00700")
                    .Build();
            QotGetRT.C2S c2s = QotGetRT.C2S.CreateBuilder()
                    .SetSecurity(sec)
                    .Build();
            QotGetRT.Request req = QotGetRT.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetRT(req);
            Console.Write("Send QotGetRT: {0}\n", seqNo);
        }
    
        public void OnReply_GetRT(FTAPI_Conn client, uint nSerialNo, QotGetRT.Response rsp)
        {
            Console.Write("Reply: QotGetRT: {0}\n", nSerialNo);
            Console.Write("price: {0}\n", rsp.S2C.RtListList[0].Price);
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

    Qot onInitConnect: ret=0 desc= connID=6825673513534857375
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Send QotGetRT: 4
    Reply: QotGetRT: 4
    price: 465.4
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

`int getRT(QotGetRT.Request req);`  
`void onReply_GetRT(FTAPI_Conn client, int nSerialNo, QotGetRT.Response rsp);`

*   **介绍**

获取已订阅股票的实时分时数据，必须要先订阅。

*   **参数**

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
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

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.TimeShare rtList = 2; //分时数据结构体
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   分时结构参见 [TimeShare](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9906)
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
            QotSub.C2S c2s = QotSub.C2S.newBuilder()
                    .addSecurityList(sec)
                    .addSubTypeList(QotCommon.SubType.SubType_RT_VALUE)
                    .setIsSubOrUnSub(true)
                    .build();
            QotSub.Request req = QotSub.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.sub(req);
            System.out.printf("Send QotSub: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_Sub(FTAPI_Conn client, int nSerialNo, QotSub.Response rsp) {
            System.out.printf("Reply: QotSub: %d  %s\n", nSerialNo, rsp.toString());
    
            if (rsp.getRetType() != Common.RetType.RetType_Succeed_VALUE)
                return;
    
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .setCode("00700")
                    .build();
            QotGetRT.C2S c2s = QotGetRT.C2S.newBuilder()
                    .setSecurity(sec)
                    .build();
            QotGetRT.Request req = QotGetRT.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getRT(req);
            System.out.printf("Send QotGetRT: %d\n", seqNo);
        }
    
        @Override
        public void onReply_GetRT(FTAPI_Conn client, int nSerialNo, QotGetRT.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetRT failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetRT: %s\n", json);
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
71  
72  
73  
74  
75  
76  
77  
78  
79  
80  
81  
82  
83  
84  
85  
86  
87  

*   **Output**

    Send QotGetRT: 3
    Receive QotGetRT: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "rtList": [{\
          "time": "2021-06-24 09:30:00",\
          "minute": 570,\
          "isBlank": false,\
          "price": 584.0,\
          "lastClosePrice": 582.5,\
          "avgPrice": 584.0,\
          "volume": "431600",\
          "turnover": 2.520544E8,\
          "timestamp": 1.6244982E9\
        }, ... {\
          "time": "2021-06-24 16:00:00",\
          "minute": 960,\
          "isBlank": false,\
          "price": 583.0,\
          "lastClosePrice": 582.5,\
          "avgPrice": 583.4658532703611,\
          "volume": "1197900",\
          "turnover": 6.98392175E8,\
          "timestamp": 1.6245216E9\
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

`Futu::u32_t GetRT(const Qot_GetRT::Request &stReq);`  
`virtual void OnReply_GetRT(Futu::u32_t nSerialNo, const Qot_GetRT::Response &stRsp) = 0;`

*   **介绍**
    
    获取已订阅股票的实时分时数据，必须要先订阅。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
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

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.TimeShare rtList = 2; //分时数据结构体
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   分时结构参见 [TimeShare](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9906)
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
    
    		// 这个接口要先订阅
    		Qot_Sub::Request req;
    		Qot_Sub::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->add_subtypelist(Qot_Common::SubType::SubType_RT);
    		c2s->set_isregorunregpush(true);
    		c2s->set_issuborunsub(true);
            
            m_SubSerialNo = m_pQotApi->Sub(req);
            cout << "Request Sub SerialNo: " << m_SubSerialNo << endl;
    	}
    
    	virtual void OnReply_Sub(Futu::u32_t nSerialNo, const Qot_Sub::Response &stRsp)
    	{
            if(nSerialNo == m_SubSerialNo)
            {
                cout << "OnReply_Sub SerialNo: " << nSerialNo << endl;
                if(stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "Sub Failed" << endl;
                    return;
                }        
                
                // 组包
    			Qot_GetRT::Request req;
    			Qot_GetRT::C2S *c2s = req.mutable_c2s();
    			Qot_Common::Security *sec = c2s->mutable_security();
    			sec->set_code("00700");
    			sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
                m_GetRTSerialNo = m_pQotApi->GetRT(req);
                cout << "Request GetRT SerialNo: " << m_GetRTSerialNo << endl;
            }
    	}
    
    	virtual void OnReply_GetRT(Futu::u32_t nSerialNo, const Qot_GetRT::Response &stRsp){
            if(nSerialNo == m_GetRTSerialNo)
            {
                cout << "OnReply_GetRT SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_SubSerialNo;
        Futu::u32_t m_GetRTSerialNo;
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
77  
78  
79  
80  
81  
82  
83  
84  
85  
86  
87  
88  
89  
90  
91  
92  
93  
94  
95  
96  
97  
98  
99  

*   **Output**

    connect
    Request Sub SerialNo: 3
    OnReply_Sub SerialNo: 3
    Request GetRT SerialNo: 4
    OnReply_GetRT SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "rtList": [\
       {\
        "time": "2021-06-09 09:30:00",\
        "minute": 570,\
        "isBlank": false,\
        "price": 600,\
        "lastClosePrice": 601,\
        "avgPrice": 600,\
        "volume": "255100",\
        "turnover": 153060000,\
        "timestamp": 1623202200\
       },\
       {\
        "time": "2021-06-09 09:31:00",\
        "minute": 571,\
        "isBlank": false,\
        "price": 599.5,\
        "lastClosePrice": 601,\
        "avgPrice": 599.41146019278824,\
        "volume": "305100",\
        "turnover": 182730300,\
        "timestamp": 1623202260\
       },\
    ...\
       {\
        "time": "2021-06-09 11:02:00",\
        "minute": 662,\
        "isBlank": false,\
        "price": 604.5,\
        "lastClosePrice": 601,\
        "avgPrice": 601.88741226000786,\
        "volume": "5700",\
        "turnover": 3445550,\
        "timestamp": 1623207720\
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

`GetRT(req);`

*   **介绍**
    
    获取已订阅股票的实时分时数据，必须要先订阅。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
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

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.TimeShare rtList = 2; //分时数据结构体
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   分时结构参见 [TimeShare](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9906)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetRT(){
        const { RetType } = Common
        const { SubType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
        
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.Sub({
                    c2s: {
                    securityList: [\
                        {\
                            market: QotMarket.QotMarket_HK_Security,\
                            code: "00700",\
                        },\
                    ],
                    subTypeList: [ SubType.SubType_RT ], // 订阅实时分时类型
                    isSubOrUnSub: true, // 订阅 true, 反订阅 false
                    isRegOrUnRegPush: true, // 注册推送 true, 反注册推送 false
                    },
                })
                .then((res) => { 
    
                    const req = {
                        c2s: {
                            security: {
                                market: QotMarket.QotMarket_HK_Security,
                                code: "00700",
                            },
                        },
                    };
                    
                    websocket.GetRT(req)
                    .then((res) => {
                        let { errCode, retMsg, retType,s2c } = res
                        console.log("RT: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
    
                })
                .catch((error) => {
                    if ("retMsg" in error) {
                        console.log("sub error:", error.retMsg);
                    }
                });
            } else {
                console.log("start error", msg);
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

    RT: errCode 0, retMsg , retType 0
    {
      "security": {
        "market": 1,
        "code": "00700"
      },
      "rtList": [{\
        "time": "2021-09-09 09:30:00",\
        "minute": 570,\
        "isBlank": false,\
        "price": 509,\
        "lastClosePrice": 524.5,\
        "avgPrice": 509,\
        "volume": "941800",\
        "turnover": 479376200,\
        "timestamp": 1631151000\
      }, {\
        "time": "2021-09-09 09:31:00",\
        "minute": 571,\
        "isBlank": false,\
        "price": 506.5,\
        "lastClosePrice": 524.5,\
        "avgPrice": 507.88588572919383,\
        "volume": "974700",\
        "turnover": 493987100,\
        "timestamp": 1631151060\
      }, ..., {\
        "time": "2021-09-09 09:32:00",\
        "minute": 572,\
        "isBlank": false,\
        "price": 504,\
        "lastClosePrice": 524.5,\
        "avgPrice": 507.3999031165602,\
        "volume": "416200",\
        "turnover": 210248454,\
        "timestamp": 1631151120\
      },],
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

提示

*   此接口提供了一次性获取实时数据的功能，如需持续获取推送数据，请参考 [实时分时回调](https://openapi.futunn.com/futu-api-doc/quote/update-rt.html)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)
    

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_rt_data(code)`

*   **介绍**
    
    获取已订阅股票的实时分时数据，必须要先订阅。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票  |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回分时数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   分时数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | time | str | 时间<br>(ℹ️ 格式：yyyy-MM-dd HH:mm:ss  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | is\_blank | bool | 数据状态<br>(ℹ️ False：正常数据  <br>True：伪造数据) |
        | opened\_mins | int | 零点到当前多少分钟 |
        | cur\_price | float | 当前价格 |
        | last\_close | float | 昨天收盘的价格 |
        | avg\_price | float | 平均价格<br>(ℹ️ 对于期权，该字段为 N/A) |
        | volume | float | 成交量 |
        | turnover | float | 成交金额 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret_sub, err_message = quote_ctx.subscribe(['US.AAPL'], [SubType.RT_DATA], subscribe_push=False, session=Session.ALL)
    # 先订阅分时数据类型。订阅成功后 OpenD 将持续收到服务器的推送，False 代表暂时不需要推送给脚本
    if ret_sub == RET_OK:   # 订阅成功
        ret, data = quote_ctx.get_rt_data('US.AAPL')   # 获取一次分时数据
        if ret == RET_OK:
            print(data)
        else:
            print('error:', data)
    else:
        print('subscription failed', err_message)
    quote_ctx.close()   # 关闭当条连接，OpenD 会在1分钟后自动取消相应股票相应类型的订阅
    

1  
2  
3  
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

*   **Output**

    code  name                 time  is_blank  opened_mins  cur_price  last_close   avg_price   volume     turnover
    0    US.AAPL   苹果  2025-04-06 20:01:00     False         1201     183.00      188.38  181.643916    9463  1718896.38
    ..      ...    ...                  ...       ...          ...        ...         ...         ...      ...          ...
    586  US.AAPL   苹果  2025-04-07 05:47:00     False          347     181.26      188.38  180.555673     661   119859.75
    
    [587 rows x 10 columns]
    

1  
2  
3  
4  
5  
6  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-rt.html#4930-2)
 Qot\_GetRT.proto
---------------------------------------------------------------------------------------

*   **介绍**
    
    获取已订阅股票的实时分时数据，必须要先订阅。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
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

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.TimeShare rtList = 2; //分时数据结构体
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   分时结构参见 [TimeShare](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9906)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3008
    

`uint GetRT(QotGetRT.Request req);`  
`virtual void OnReply_GetRT(MMAPI_Conn client, uint nSerialNo, QotGetRT.Response rsp);`

*   **介绍**

获取已订阅股票的实时分时数据，必须要先订阅。

*   **参数**

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
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

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.TimeShare rtList = 2; //分时数据结构体
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   分时结构参见 [TimeShare](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9906)
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
            QotSub.C2S c2s = QotSub.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                    .AddSubTypeList((int)QotCommon.SubType.SubType_RT)
                    .SetIsSubOrUnSub(true)
                    .Build();
            QotSub.Request req = QotSub.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.Sub(req);
            Console.Write("Send QotSub: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_Sub(MMAPI_Conn client, uint nSerialNo, QotSub.Response rsp) {
            Console.Write("Reply: QotSub: {0}  {1}\n", nSerialNo, rsp.ToString());
    
            if (rsp.RetType != (int)Common.RetType.RetType_Succeed)
                return;
    
            QotCommon.Security sec = QotCommon.Security.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetCode("00700")
                    .Build();
            QotGetRT.C2S c2s = QotGetRT.C2S.CreateBuilder()
                    .SetSecurity(sec)
                    .Build();
            QotGetRT.Request req = QotGetRT.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetRT(req);
            Console.Write("Send QotGetRT: {0}\n", seqNo);
        }
    
        public void OnReply_GetRT(MMAPI_Conn client, uint nSerialNo, QotGetRT.Response rsp)
        {
            Console.Write("Reply: QotGetRT: {0}\n", nSerialNo);
            Console.Write("price: {0}\n", rsp.S2C.RtListList[0].Price);
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

    Qot onInitConnect: ret=0 desc= connID=6825673513534857375
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Send QotGetRT: 4
    Reply: QotGetRT: 4
    price: 465.4
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

`int getRT(QotGetRT.Request req);`  
`void onReply_GetRT(MMAPI_Conn client, int nSerialNo, QotGetRT.Response rsp);`

*   **介绍**

获取已订阅股票的实时分时数据，必须要先订阅。

*   **参数**

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
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

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.TimeShare rtList = 2; //分时数据结构体
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   分时结构参见 [TimeShare](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9906)
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
            QotSub.C2S c2s = QotSub.C2S.newBuilder()
                    .addSecurityList(sec)
                    .addSubTypeList(QotCommon.SubType.SubType_RT_VALUE)
                    .setIsSubOrUnSub(true)
                    .build();
            QotSub.Request req = QotSub.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.sub(req);
            System.out.printf("Send QotSub: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_Sub(MMAPI_Conn client, int nSerialNo, QotSub.Response rsp) {
            System.out.printf("Reply: QotSub: %d  %s\n", nSerialNo, rsp.toString());
    
            if (rsp.getRetType() != Common.RetType.RetType_Succeed_VALUE)
                return;
    
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .setCode("00700")
                    .build();
            QotGetRT.C2S c2s = QotGetRT.C2S.newBuilder()
                    .setSecurity(sec)
                    .build();
            QotGetRT.Request req = QotGetRT.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getRT(req);
            System.out.printf("Send QotGetRT: %d\n", seqNo);
        }
    
        @Override
        public void onReply_GetRT(MMAPI_Conn client, int nSerialNo, QotGetRT.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetRT failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetRT: %s\n", json);
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
71  
72  
73  
74  
75  
76  
77  
78  
79  
80  
81  
82  
83  
84  
85  
86  
87  

*   **Output**

    Send QotGetRT: 3
    Receive QotGetRT: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "rtList": [{\
          "time": "2021-06-24 09:30:00",\
          "minute": 570,\
          "isBlank": false,\
          "price": 584.0,\
          "lastClosePrice": 582.5,\
          "avgPrice": 584.0,\
          "volume": "431600",\
          "turnover": 2.520544E8,\
          "timestamp": 1.6244982E9\
        }, ... {\
          "time": "2021-06-24 16:00:00",\
          "minute": 960,\
          "isBlank": false,\
          "price": 583.0,\
          "lastClosePrice": 582.5,\
          "avgPrice": 583.4658532703611,\
          "volume": "1197900",\
          "turnover": 6.98392175E8,\
          "timestamp": 1.6245216E9\
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

`moomoo::u32_t GetRT(const Qot_GetRT::Request &stReq);`  
`virtual void OnReply_GetRT(moomoo::u32_t nSerialNo, const Qot_GetRT::Response &stRsp) = 0;`

*   **介绍**
    
    获取已订阅股票的实时分时数据，必须要先订阅。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
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

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.TimeShare rtList = 2; //分时数据结构体
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   分时结构参见 [TimeShare](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9906)
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
    
    
    	virtual void OnInitConnect(MMAPI_Conn* pConn, Futu::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		// 这个接口要先订阅
    		Qot_Sub::Request req;
    		Qot_Sub::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->add_subtypelist(Qot_Common::SubType::SubType_RT);
    		c2s->set_isregorunregpush(true);
    		c2s->set_issuborunsub(true);
            
            m_SubSerialNo = m_pQotApi->Sub(req);
            cout << "Request Sub SerialNo: " << m_SubSerialNo << endl;
    	}
    
    	virtual void OnReply_Sub(Futu::u32_t nSerialNo, const Qot_Sub::Response &stRsp)
    	{
            if(nSerialNo == m_SubSerialNo)
            {
                cout << "OnReply_Sub SerialNo: " << nSerialNo << endl;
                if(stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "Sub Failed" << endl;
                    return;
                }        
                
                // 组包
    			Qot_GetRT::Request req;
    			Qot_GetRT::C2S *c2s = req.mutable_c2s();
    			Qot_Common::Security *sec = c2s->mutable_security();
    			sec->set_code("00700");
    			sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
                m_GetRTSerialNo = m_pQotApi->GetRT(req);
                cout << "Request GetRT SerialNo: " << m_GetRTSerialNo << endl;
            }
    	}
    
    	virtual void OnReply_GetRT(Futu::u32_t nSerialNo, const Qot_GetRT::Response &stRsp){
            if(nSerialNo == m_GetRTSerialNo)
            {
                cout << "OnReply_GetRT SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_SubSerialNo;
        moomoo::u32_t m_GetRTSerialNo;
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
77  
78  
79  
80  
81  
82  
83  
84  
85  
86  
87  
88  
89  
90  
91  
92  
93  
94  
95  
96  
97  
98  
99  

*   **Output**

    connect
    Request Sub SerialNo: 3
    OnReply_Sub SerialNo: 3
    Request GetRT SerialNo: 4
    OnReply_GetRT SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "rtList": [\
       {\
        "time": "2021-06-09 09:30:00",\
        "minute": 570,\
        "isBlank": false,\
        "price": 600,\
        "lastClosePrice": 601,\
        "avgPrice": 600,\
        "volume": "255100",\
        "turnover": 153060000,\
        "timestamp": 1623202200\
       },\
       {\
        "time": "2021-06-09 09:31:00",\
        "minute": 571,\
        "isBlank": false,\
        "price": 599.5,\
        "lastClosePrice": 601,\
        "avgPrice": 599.41146019278824,\
        "volume": "305100",\
        "turnover": 182730300,\
        "timestamp": 1623202260\
       },\
    ...\
       {\
        "time": "2021-06-09 11:02:00",\
        "minute": 662,\
        "isBlank": false,\
        "price": 604.5,\
        "lastClosePrice": 601,\
        "avgPrice": 601.88741226000786,\
        "volume": "5700",\
        "turnover": 3445550,\
        "timestamp": 1623207720\
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

`GetRT(req);`

*   **介绍**
    
    获取已订阅股票的实时分时数据，必须要先订阅。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
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

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.TimeShare rtList = 2; //分时数据结构体
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   分时结构参见 [TimeShare](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9906)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetRT(){
        const { RetType } = Common
        const { SubType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
        
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.Sub({
                    c2s: {
                    securityList: [\
                        {\
                            market: QotMarket.QotMarket_HK_Security,\
                            code: "00700",\
                        },\
                    ],
                    subTypeList: [ SubType.SubType_RT ], // 订阅实时分时类型
                    isSubOrUnSub: true, // 订阅 true, 反订阅 false
                    isRegOrUnRegPush: true, // 注册推送 true, 反注册推送 false
                    },
                })
                .then((res) => { 
    
                    const req = {
                        c2s: {
                            security: {
                                market: QotMarket.QotMarket_HK_Security,
                                code: "00700",
                            },
                        },
                    };
                    
                    websocket.GetRT(req)
                    .then((res) => {
                        let { errCode, retMsg, retType,s2c } = res
                        console.log("RT: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
    
                })
                .catch((error) => {
                    if ("retMsg" in error) {
                        console.log("sub error:", error.retMsg);
                    }
                });
            } else {
                console.log("start error", msg);
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

    RT: errCode 0, retMsg , retType 0
    {
      "security": {
        "market": 1,
        "code": "00700"
      },
      "rtList": [{\
        "time": "2021-09-09 09:30:00",\
        "minute": 570,\
        "isBlank": false,\
        "price": 509,\
        "lastClosePrice": 524.5,\
        "avgPrice": 509,\
        "volume": "941800",\
        "turnover": 479376200,\
        "timestamp": 1631151000\
      }, {\
        "time": "2021-09-09 09:31:00",\
        "minute": 571,\
        "isBlank": false,\
        "price": 506.5,\
        "lastClosePrice": 524.5,\
        "avgPrice": 507.88588572919383,\
        "volume": "974700",\
        "turnover": 493987100,\
        "timestamp": 1631151060\
      }, ..., {\
        "time": "2021-09-09 09:32:00",\
        "minute": 572,\
        "isBlank": false,\
        "price": 504,\
        "lastClosePrice": 524.5,\
        "avgPrice": 507.3999031165602,\
        "volume": "416200",\
        "turnover": 210248454,\
        "timestamp": 1631151120\
      },],
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

提示

*   此接口提供了一次性获取实时数据的功能，如需持续获取推送数据，请参考 [实时分时回调](https://openapi.futunn.com/futu-api-doc/quote/update-rt.html)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)
    

← [获取实时 K 线](https://openapi.futunn.com/futu-api-doc/quote/get-kl.html) [获取实时逐笔](https://openapi.futunn.com/futu-api-doc/quote/get-ticker.html)
 →