[#](./quote_get-option-expiration-date.md#7390)
 获取期权链到期日
=================================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_option_expiration_date(code, index_option_type=IndexOptionType.NORMAL)`

*   **介绍**
    
    通过标的股票，查询期权链的所有到期日。如需获取完整期权链，请配合 [获取期权链](./quote_get-option-chain.md)
     接口使用。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 标的股票代码 |
    | index\_option\_type | [IndexOptionType](./quote_quote.md#5149) | 指数期权类型<br>(ℹ️ 仅对港股指数期权筛选有效，正股、ETFs、美股指数期权可忽略此参数) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回期权链到期日相关数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   期权链到期日数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | strike\_time | str | 期权链行权日<br>(ℹ️ 格式：yyyy-MM-dd)  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间 |
        | option\_expiry\_date\_distance | int | 距离到期日天数<br>(ℹ️ 负数表示已过期) |
        | expiration\_cycle | [ExpirationCycle](./quote_quote.md#2235) | 交割周期<br>(ℹ️ 支持香港指数期权、美股指数期权) |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret, data = quote_ctx.get_option_expiration_date(code='HK.00700')
    if ret == RET_OK:
        print(data)
        print(data['strike_time'].values.tolist())  # 转为 list
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

*   **Output**

      strike_time  option_expiry_date_distance expiration_cycle
    0  2021-04-29                            4              N/A
    1  2021-05-28                           33              N/A
    2  2021-06-29                           65              N/A
    3  2021-07-29                           95              N/A
    4  2021-09-29                          157              N/A
    5  2021-12-30                          249              N/A
    6  2022-03-30                          339              N/A
    ['2021-04-29', '2021-05-28', '2021-06-29', '2021-07-29', '2021-09-29', '2021-12-30', '2022-03-30']
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

[#](./quote_get-option-expiration-date.md#4915)
 Qot\_GetOptionExpirationDate.proto
---------------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    通过标的股票，查询期权链的所有到期日。如需获取完整期权链，请配合 [获取期权链](./quote_get-option-chain.md)
     接口使用。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 2; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   指数期权类别枚举参见 [IndexOptionType](./quote_quote.md#5149)
>     

*   **返回**

    message OptionExpirationDate
    {
    	optional string strikeTime = 1; //期权链行权日（格式：yyyy-MM-dd，港股和 A 股市场默认是北京时间，美股市场默认是美东时间）
    	optional double strikeTimestamp = 2; //行权日时间戳
    	required int32 optionExpiryDateDistance = 3; //距离到期日天数，负数表示已过期
    	optional int32 cycle = 4; //Qot_Common.ExpirationCycle,交割周期（支持香港指数期权、美股指数期权）
    }
     
    message S2C
    {
    	repeated OptionExpirationDate dateList = 1; //期权链行权日
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

> *   期权交割周期枚举参见 [ExpirationCycle](./quote_quote.md#2235)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3224
    
*   **数据类型**
    
    *   股票结构参见 [Security](./quote_quote.md#1377)
        
    *   指数期权类别枚举参见 [IndexOptionType](./quote_quote.md#5149)
        
    *   期权交割周期枚举参见 [ExpirationCycle](./quote_quote.md#2235)
        

`uint GetOptionExpirationDate(GetOptionExpirationDate.Request req);`  
`virtual void OnReply_GetOptionExpirationDate(FTAPI_Conn client, uint nSerialNo, GetOptionExpirationDate.Response rsp);`

*   **介绍**
    
    通过标的股票，查询期权链的所有到期日。如需获取完整期权链，请配合 [获取期权链](./quote_get-option-chain.md)
     接口使用。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 2; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   指数期权类别枚举参见 [IndexOptionType](./quote_quote.md#5149)
>     

*   **返回**

    message OptionExpirationDate
    {
    	optional string strikeTime = 1; //期权链行权日（格式：yyyy-MM-dd，港股和 A 股市场默认是北京时间，美股市场默认是美东时间）
    	optional double strikeTimestamp = 2; //行权日时间戳
    	required int32 optionExpiryDateDistance = 3; //距离到期日天数，负数表示已过期
    	optional int32 cycle = 4; //Qot_Common.ExpirationCycle,交割周期（支持香港指数期权、美股指数期权）
    }
     
    message S2C
    {
    	repeated OptionExpirationDate dateList = 1; //期权链行权日
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

> *   期权交割周期枚举参见 [ExpirationCycle](./quote_quote.md#2235)
>     
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
            QotGetOptionExpirationDate.C2S c2s = QotGetOptionExpirationDate.C2S.CreateBuilder()
                    .SetOwner(sec)
                .Build();
            QotGetOptionExpirationDate.Request req = QotGetOptionExpirationDate.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetOptionExpirationDate(req);
            Console.Write("Send GetOptionExpirationDate: {0}\n", seqNo);
        }
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_GetOptionExpirationDate(FTAPI_Conn client, uint nSerialNo, QotGetOptionExpirationDate.Response rsp) {
            Console.Write("Reply: QotGetOptionExpirationDate: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("strikeTime: {0}\n", rsp.S2C.DateListList[0].StrikeTime);
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

**Output**

    Qot onInitConnect: ret=0 desc= connID=6819092719404484544
    Send QotGetOptionExpirationDate: 3
    Reply: QotGetOptionExpirationDate: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      dateList {
        strikeTime: "2021-07-29"
        strikeTimestamp: 1627488000
        optionExpiryDateDistance: 20
      }
      ...
      dateList {
        strikeTime: "2022-06-29"
        strikeTimestamp: 1656432000
        optionExpiryDateDistance: 355
      }
    }
    
    strikeTime: 2021-07-29
    

1  
2  
3  
4  
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

`int getOptionExpirationDate(QotGetOptionExpirationDate.Request req);`  
`void onReply_GetOptionExpirationDate(FTAPI_Conn client, int nSerialNo, QotGetOptionExpirationDate.Response rsp);`

*   **介绍**
    
    通过标的股票，查询期权链的所有到期日。如需获取完整期权链，请配合 [获取期权链](./quote_get-option-chain.md)
     接口使用。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 2; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   指数期权类别枚举参见 [IndexOptionType](./quote_quote.md#5149)
>     

*   **返回**

    message OptionExpirationDate
    {
    	optional string strikeTime = 1; //期权链行权日（格式：yyyy-MM-dd，港股和 A 股市场默认是北京时间，美股市场默认是美东时间）
    	optional double strikeTimestamp = 2; //行权日时间戳
    	required int32 optionExpiryDateDistance = 3; //距离到期日天数，负数表示已过期
    	optional int32 cycle = 4; //Qot_Common.ExpirationCycle,交割周期（支持香港指数期权、美股指数期权）
    }
     
    message S2C
    {
    	repeated OptionExpirationDate dateList = 1; //期权链行权日
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

> *   期权交割周期枚举参见 [ExpirationCycle](./quote_quote.md#2235)
>     
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
            QotGetOptionExpirationDate.C2S c2s = QotGetOptionExpirationDate.C2S.newBuilder()
                    .setOwner(sec)
                .build();
            QotGetOptionExpirationDate.Request req = QotGetOptionExpirationDate.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getOptionExpirationDate(req);
            System.out.printf("Send QotGetOptionExpirationDate: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetOptionExpirationDate(FTAPI_Conn client, int nSerialNo, QotGetOptionExpirationDate.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetOptionExpirationDate failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetOptionExpirationDate: %s\n", json);
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

    Send QotGetOptionExpirationDate: 2
    Receive QotGetOptionExpirationDate: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "dateList": [{\
          "strikeTime": "2021-06-29",\
          "strikeTimestamp": 1.624896E9,\
          "optionExpiryDateDistance": 5\
        }, ... {\
          "strikeTime": "2022-06-29",\
          "strikeTimestamp": 1.656432E9,\
          "optionExpiryDateDistance": 370\
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

`Futu::u32_t GetOptionExpirationDate(const Qot_GetOptionExpirationDate::Request &stReq);`  
`virtual void OnReply_GetOptionExpirationDate(Futu::u32_t nSerialNo, const Qot_GetOptionExpirationDate::Response &stRsp) = 0;`

*   **介绍**
    
    通过标的股票，查询期权链的所有到期日。如需获取完整期权链，请配合 [获取期权链](./quote_get-option-chain.md)
     接口使用。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 2; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   指数期权类别枚举参见 [IndexOptionType](./quote_quote.md#5149)
>     

*   **返回**

    message OptionExpirationDate
    {
    	optional string strikeTime = 1; //期权链行权日（格式：yyyy-MM-dd，港股和 A 股市场默认是北京时间，美股市场默认是美东时间）
    	optional double strikeTimestamp = 2; //行权日时间戳
    	required int32 optionExpiryDateDistance = 3; //距离到期日天数，负数表示已过期
    	optional int32 cycle = 4; //Qot_Common.ExpirationCycle,交割周期（支持香港指数期权、美股指数期权）
    }
     
    message S2C
    {
    	repeated OptionExpirationDate dateList = 1; //期权链行权日
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

> *   期权交割周期枚举参见 [ExpirationCycle](./quote_quote.md#2235)
>     
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
    		Qot_GetOptionExpirationDate::Request req;
    		Qot_GetOptionExpirationDate::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_owner();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetOptionExpirationDateSerialNo = m_pQotApi->GetOptionExpirationDate(req);
            cout << "Request GetOptionExpirationDate SerialNo: " << m_GetOptionExpirationDateSerialNo << endl;
    	}
    
    	virtual void OnReply_GetOptionExpirationDate(Futu::u32_t nSerialNo, const Qot_GetOptionExpirationDate::Response &stRsp){
            if(nSerialNo == m_GetOptionExpirationDateSerialNo)
            {
                cout << "OnReply_GetOptionExpirationDate SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetOptionExpirationDateSerialNo;
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

*   **Output**

    connect
    Request GetOptionExpirationDate SerialNo: 4
    OnReply_GetOptionExpirationDate SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "dateList": [\
       {\
        "strikeTime": "2021-06-29",\
        "strikeTimestamp": 1624896000,\
        "optionExpiryDateDistance": 20\
       },\
    ...\
       {\
        "strikeTime": "2022-06-29",\
        "strikeTimestamp": 1656432000,\
        "optionExpiryDateDistance": 385\
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

`GetOptionExpirationDate(req);`

*   **介绍**
    
    通通过标的股票，查询期权链的所有到期日。如需获取完整期权链，请配合 [获取期权链](./quote_get-option-chain.md)
     接口使用。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 2; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   指数期权类别枚举参见 [IndexOptionType](./quote_quote.md#5149)
>     

*   **返回**

    message OptionExpirationDate
    {
    	optional string strikeTime = 1; //期权链行权日（格式：yyyy-MM-dd，港股和 A 股市场默认是北京时间，美股市场默认是美东时间）
    	optional double strikeTimestamp = 2; //行权日时间戳
    	required int32 optionExpiryDateDistance = 3; //距离到期日天数，负数表示已过期
    	optional int32 cycle = 4; //Qot_Common.ExpirationCycle,交割周期（支持香港指数期权、美股指数期权）
    }
     
    message S2C
    {
    	repeated OptionExpirationDate dateList = 1; //期权链行权日
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

> *   期权交割周期枚举参见 [ExpirationCycle](./quote_quote.md#2235)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetOptionExpirationDate(){
        const { RetType } = Common
        const { IndexOptionType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        owner:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                        indexOptionType: IndexOptionType.IndexOptionType_Unknown,
                    },
                };
    
                websocket.GetOptionExpirationDate(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("OptionExpirationDate: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    OptionExpirationDate: errCode 0, retMsg , retType 0
    {
      "dateList": [{\
        "strikeTime": "2021-09-29",\
        "strikeTimestamp": 1632844800,\
        "optionExpiryDateDistance": 19\
      }, {\
        "strikeTime": "2021-10-28",\
        "strikeTimestamp": 1635350400,\
        "optionExpiryDateDistance": 48\
      }, ..., {\
        "strikeTime": "2022-09-29",\
        "strikeTimestamp": 1664380800,\
        "optionExpiryDateDistance": 384\
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

接口限制

*   每 30 秒内最多请求 60 次获取期权链到期日接口

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_option_expiration_date(code, index_option_type=IndexOptionType.NORMAL)`

*   **介绍**
    
    通过标的股票，查询期权链的所有到期日。如需获取完整期权链，请配合 [获取期权链](./quote_get-option-chain.md)
     接口使用。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 标的股票代码 |
    | index\_option\_type | [IndexOptionType](./quote_quote.md#5149) | 指数期权类型<br>(ℹ️ 仅对港股指数期权筛选有效，正股、ETFs、美股指数期权可忽略此参数) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回期权链到期日相关数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   期权链到期日数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | strike\_time | str | 期权链行权日<br>(ℹ️ 格式：yyyy-MM-dd)  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间 |
        | option\_expiry\_date\_distance | int | 距离到期日天数<br>(ℹ️ 负数表示已过期) |
        | expiration\_cycle | [ExpirationCycle](./quote_quote.md#2235) | 交割周期<br>(ℹ️ 支持香港指数期权、美股指数期权) |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret, data = quote_ctx.get_option_expiration_date(code='HK.00700')
    if ret == RET_OK:
        print(data)
        print(data['strike_time'].values.tolist())  # 转为 list
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

*   **Output**

      strike_time  option_expiry_date_distance expiration_cycle
    0  2021-04-29                            4              N/A
    1  2021-05-28                           33              N/A
    2  2021-06-29                           65              N/A
    3  2021-07-29                           95              N/A
    4  2021-09-29                          157              N/A
    5  2021-12-30                          249              N/A
    6  2022-03-30                          339              N/A
    ['2021-04-29', '2021-05-28', '2021-06-29', '2021-07-29', '2021-09-29', '2021-12-30', '2022-03-30']
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

[#](./quote_get-option-expiration-date.md#4915-2)
 Qot\_GetOptionExpirationDate.proto
-----------------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    通过标的股票，查询期权链的所有到期日。如需获取完整期权链，请配合 [获取期权链](./quote_get-option-chain.md)
     接口使用。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 2; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   指数期权类别枚举参见 [IndexOptionType](./quote_quote.md#5149)
>     

*   **返回**

    message OptionExpirationDate
    {
    	optional string strikeTime = 1; //期权链行权日（格式：yyyy-MM-dd，港股和 A 股市场默认是北京时间，美股市场默认是美东时间）
    	optional double strikeTimestamp = 2; //行权日时间戳
    	required int32 optionExpiryDateDistance = 3; //距离到期日天数，负数表示已过期
    	optional int32 cycle = 4; //Qot_Common.ExpirationCycle,交割周期（支持香港指数期权、美股指数期权）
    }
     
    message S2C
    {
    	repeated OptionExpirationDate dateList = 1; //期权链行权日
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

> *   期权交割周期枚举参见 [ExpirationCycle](./quote_quote.md#2235)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3224
    
*   **数据类型**
    
    *   股票结构参见 [Security](./quote_quote.md#1377)
        
    *   指数期权类别枚举参见 [IndexOptionType](./quote_quote.md#5149)
        
    *   期权交割周期枚举参见 [ExpirationCycle](./quote_quote.md#2235)
        

`uint GetOptionExpirationDate(GetOptionExpirationDate.Request req);`  
`virtual void OnReply_GetOptionExpirationDate(MMAPI_Conn client, uint nSerialNo, GetOptionExpirationDate.Response rsp);`

*   **介绍**
    
    通过标的股票，查询期权链的所有到期日。如需获取完整期权链，请配合 [获取期权链](./quote_get-option-chain.md)
     接口使用。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 2; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   指数期权类别枚举参见 [IndexOptionType](./quote_quote.md#5149)
>     

*   **返回**

    message OptionExpirationDate
    {
    	optional string strikeTime = 1; //期权链行权日（格式：yyyy-MM-dd，港股和 A 股市场默认是北京时间，美股市场默认是美东时间）
    	optional double strikeTimestamp = 2; //行权日时间戳
    	required int32 optionExpiryDateDistance = 3; //距离到期日天数，负数表示已过期
    	optional int32 cycle = 4; //Qot_Common.ExpirationCycle,交割周期（支持香港指数期权、美股指数期权）
    }
     
    message S2C
    {
    	repeated OptionExpirationDate dateList = 1; //期权链行权日
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

> *   期权交割周期枚举参见 [ExpirationCycle](./quote_quote.md#2235)
>     
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
            QotGetOptionExpirationDate.C2S c2s = QotGetOptionExpirationDate.C2S.CreateBuilder()
                    .SetOwner(sec)
                .Build();
            QotGetOptionExpirationDate.Request req = QotGetOptionExpirationDate.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetOptionExpirationDate(req);
            Console.Write("Send GetOptionExpirationDate: {0}\n", seqNo);
        }
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_GetOptionExpirationDate(MMAPI_Conn client, uint nSerialNo, QotGetOptionExpirationDate.Response rsp) {
            Console.Write("Reply: QotGetOptionExpirationDate: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("strikeTime: {0}\n", rsp.S2C.DateListList[0].StrikeTime);
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

**Output**

    Qot onInitConnect: ret=0 desc= connID=6819092719404484544
    Send QotGetOptionExpirationDate: 3
    Reply: QotGetOptionExpirationDate: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      dateList {
        strikeTime: "2021-07-29"
        strikeTimestamp: 1627488000
        optionExpiryDateDistance: 20
      }
      ...
      dateList {
        strikeTime: "2022-06-29"
        strikeTimestamp: 1656432000
        optionExpiryDateDistance: 355
      }
    }
    
    strikeTime: 2021-07-29
    

1  
2  
3  
4  
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

`int getOptionExpirationDate(QotGetOptionExpirationDate.Request req);`  
`void onReply_GetOptionExpirationDate(MMAPI_Conn client, int nSerialNo, QotGetOptionExpirationDate.Response rsp);`

*   **介绍**
    
    通过标的股票，查询期权链的所有到期日。如需获取完整期权链，请配合 [获取期权链](./quote_get-option-chain.md)
     接口使用。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 2; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   指数期权类别枚举参见 [IndexOptionType](./quote_quote.md#5149)
>     

*   **返回**

    message OptionExpirationDate
    {
    	optional string strikeTime = 1; //期权链行权日（格式：yyyy-MM-dd，港股和 A 股市场默认是北京时间，美股市场默认是美东时间）
    	optional double strikeTimestamp = 2; //行权日时间戳
    	required int32 optionExpiryDateDistance = 3; //距离到期日天数，负数表示已过期
    	optional int32 cycle = 4; //Qot_Common.ExpirationCycle,交割周期（支持香港指数期权、美股指数期权）
    }
     
    message S2C
    {
    	repeated OptionExpirationDate dateList = 1; //期权链行权日
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

> *   期权交割周期枚举参见 [ExpirationCycle](./quote_quote.md#2235)
>     
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
            QotGetOptionExpirationDate.C2S c2s = QotGetOptionExpirationDate.C2S.newBuilder()
                    .setOwner(sec)
                .build();
            QotGetOptionExpirationDate.Request req = QotGetOptionExpirationDate.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getOptionExpirationDate(req);
            System.out.printf("Send QotGetOptionExpirationDate: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetOptionExpirationDate(MMAPI_Conn client, int nSerialNo, QotGetOptionExpirationDate.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetOptionExpirationDate failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetOptionExpirationDate: %s\n", json);
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

    Send QotGetOptionExpirationDate: 2
    Receive QotGetOptionExpirationDate: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "dateList": [{\
          "strikeTime": "2021-06-29",\
          "strikeTimestamp": 1.624896E9,\
          "optionExpiryDateDistance": 5\
        }, ... {\
          "strikeTime": "2022-06-29",\
          "strikeTimestamp": 1.656432E9,\
          "optionExpiryDateDistance": 370\
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

`moomoo::u32_t GetOptionExpirationDate(const Qot_GetOptionExpirationDate::Request &stReq);`  
`virtual void OnReply_GetOptionExpirationDate(moomoo::u32_t nSerialNo, const Qot_GetOptionExpirationDate::Response &stRsp) = 0;`

*   **介绍**
    
    通过标的股票，查询期权链的所有到期日。如需获取完整期权链，请配合 [获取期权链](./quote_get-option-chain.md)
     接口使用。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 2; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   指数期权类别枚举参见 [IndexOptionType](./quote_quote.md#5149)
>     

*   **返回**

    message OptionExpirationDate
    {
    	optional string strikeTime = 1; //期权链行权日（格式：yyyy-MM-dd，港股和 A 股市场默认是北京时间，美股市场默认是美东时间）
    	optional double strikeTimestamp = 2; //行权日时间戳
    	required int32 optionExpiryDateDistance = 3; //距离到期日天数，负数表示已过期
    	optional int32 cycle = 4; //Qot_Common.ExpirationCycle,交割周期（支持香港指数期权、美股指数期权）
    }
     
    message S2C
    {
    	repeated OptionExpirationDate dateList = 1; //期权链行权日
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

> *   期权交割周期枚举参见 [ExpirationCycle](./quote_quote.md#2235)
>     
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
    		Qot_GetOptionExpirationDate::Request req;
    		Qot_GetOptionExpirationDate::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_owner();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetOptionExpirationDateSerialNo = m_pQotApi->GetOptionExpirationDate(req);
            cout << "Request GetOptionExpirationDate SerialNo: " << m_GetOptionExpirationDateSerialNo << endl;
    	}
    
    	virtual void OnReply_GetOptionExpirationDate(moomoo::u32_t nSerialNo, const Qot_GetOptionExpirationDate::Response &stRsp){
            if(nSerialNo == m_GetOptionExpirationDateSerialNo)
            {
                cout << "OnReply_GetOptionExpirationDate SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetOptionExpirationDateSerialNo;
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

*   **Output**

    connect
    Request GetOptionExpirationDate SerialNo: 4
    OnReply_GetOptionExpirationDate SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "dateList": [\
       {\
        "strikeTime": "2021-06-29",\
        "strikeTimestamp": 1624896000,\
        "optionExpiryDateDistance": 20\
       },\
    ...\
       {\
        "strikeTime": "2022-06-29",\
        "strikeTimestamp": 1656432000,\
        "optionExpiryDateDistance": 385\
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

`GetOptionExpirationDate(req);`

*   **介绍**
    
    通通过标的股票，查询期权链的所有到期日。如需获取完整期权链，请配合 [获取期权链](./quote_get-option-chain.md)
     接口使用。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 2; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   指数期权类别枚举参见 [IndexOptionType](./quote_quote.md#5149)
>     

*   **返回**

    message OptionExpirationDate
    {
    	optional string strikeTime = 1; //期权链行权日（格式：yyyy-MM-dd，港股和 A 股市场默认是北京时间，美股市场默认是美东时间）
    	optional double strikeTimestamp = 2; //行权日时间戳
    	required int32 optionExpiryDateDistance = 3; //距离到期日天数，负数表示已过期
    	optional int32 cycle = 4; //Qot_Common.ExpirationCycle,交割周期（支持香港指数期权、美股指数期权）
    }
     
    message S2C
    {
    	repeated OptionExpirationDate dateList = 1; //期权链行权日
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

> *   期权交割周期枚举参见 [ExpirationCycle](./quote_quote.md#2235)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetOptionExpirationDate(){
        const { RetType } = Common
        const { IndexOptionType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        owner:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                        indexOptionType: IndexOptionType.IndexOptionType_Unknown,
                    },
                };
    
                websocket.GetOptionExpirationDate(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("OptionExpirationDate: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    OptionExpirationDate: errCode 0, retMsg , retType 0
    {
      "dateList": [{\
        "strikeTime": "2021-09-29",\
        "strikeTimestamp": 1632844800,\
        "optionExpiryDateDistance": 19\
      }, {\
        "strikeTime": "2021-10-28",\
        "strikeTimestamp": 1635350400,\
        "optionExpiryDateDistance": 48\
      }, ..., {\
        "strikeTime": "2022-09-29",\
        "strikeTimestamp": 1664380800,\
        "optionExpiryDateDistance": 384\
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

接口限制

*   每 30 秒内最多请求 60 次获取期权链到期日接口

← [获取复权因子](./quote_get-rehab.md) [获取期权链](./quote_get-option-chain.md)
 →

[获取期权链到期日](./quote_get-option-expiration-date.md)