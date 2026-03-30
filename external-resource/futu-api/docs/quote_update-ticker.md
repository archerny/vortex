 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/update-ticker.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/update-ticker.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/update-ticker.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/update-ticker.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
    *   [行情接口总览](https://openapi.futunn.com/futu-api-doc/quote/overview.html)
        
    *   [行情对象](https://openapi.futunn.com/futu-api-doc/quote/base.html)
        
    *   实时行情
        
        *   订阅
            
        *   推送回调
            
            *   [实时报价回调](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html)
                
            *   [实时摆盘回调](https://openapi.futunn.com/futu-api-doc/quote/update-order-book.html)
                
            *   [实时 K 线回调](https://openapi.futunn.com/futu-api-doc/quote/update-kl.html)
                
            *   [实时分时回调](https://openapi.futunn.com/futu-api-doc/quote/update-rt.html)
                
            *   [实时逐笔回调](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html)
                
            *   [实时经纪队列回调](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html)
                
            
        *   拉取
            
        
    *   基本数据
        
    *   相关衍生品
        
    *   全市场筛选
        
    *   个性化
        
    *   [行情定义](https://openapi.futunn.com/futu-api-doc/quote/quote.html)
        
    
*   交易接口
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html#6925)
 实时逐笔回调
==================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`on_recv_rsp(self, rsp_pb)`

*   **介绍**
    
    实时逐笔回调，异步处理已订阅股票的实时逐笔推送。  
    在收到实时逐笔数据推送后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Qot\_UpdateTicker\_pb2.Response | 派生类中不需要直接处理该参数 |
    
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回逐笔数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   逐笔数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | sequence | int | 逐笔序号 |
        | time | str | 成交时间<br>(ℹ️ 格式：yyyy-MM-dd HH:mm:ss:xxx  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | price | float | 成交价格 |
        | volume | int | 成交数量<br>(ℹ️ 股数) |
        | turnover | float | 成交金额 |
        | ticker\_direction | [TickerDirect](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8723) | 逐笔方向 |
        | type | [TickerType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2358) | 逐笔类型 |
        | push\_data\_type | [PushDataType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7025) | 数据来源 |
        
*   **Example**
    

    import time
    from futu import *
    
    class TickerTest(TickerHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, data = super(TickerTest,self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("TickerTest: error, msg: %s" % data)
                return RET_ERROR, data
            print("TickerTest ", data) # TickerTest 自己的处理逻辑
            return RET_OK, data
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = TickerTest()
    quote_ctx.set_handler(handler)  # 设置实时逐笔推送回调
    ret, data = quote_ctx.subscribe(['US.AAPL'], [SubType.TICKER], session=Session.ALL) # 订阅逐笔类型，OpenD 开始持续收到服务器的推送
    if ret == RET_OK:
        print(data)
    else:
        print('error:', data)
    time.sleep(15)  # 设置脚本接收 OpenD 的推送持续时间为15秒
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
14  
15  
16  
17  
18  
19  
20  
21  

*   **Output**

    TickerTest        code name                     time   price  volume  turnover ticker_direction             sequence     type push_data_type
    0  US.AAPL   苹果  2025-04-07 05:25:44.116  179.81       9   1618.29          NEUTRAL  7490500033117159426  ODD_LOT          CACHE
    
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html#1454)
 Qot\_UpdateTicker.proto
---------------------------------------------------------------------------------------------------

*   **介绍**
    
    实时逐笔回调，异步处理已订阅股票的实时逐笔推送。
    
*   **参数**
    

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.Ticker tickerList = 2; //推送的逐笔数据结构体
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
> *   逐笔结构参见 [Ticker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9044)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3011
    

`virtual void OnReply_UpdateTicker(FTAPI_Conn client, QotUpdateTicker.Response rsp);`

*   **介绍**

实时逐笔回调，异步处理已订阅股票的实时逐笔推送。

*   **参数**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.Ticker tickerList = 2; //推送的逐笔数据结构体
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
> *   逐笔结构参见 [Ticker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9044)
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
    
        public void StaTicker() {
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
                    .AddSubTypeList((int)QotCommon.SubType.SubType_Ticker)
                    .SetIsSubOrUnSub(true)
                    .SetIsRegOrUnRegPush(true)
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
        }
    
        public void OnReply_UpdateTicker(FTAPI_Conn client, uint nSerialNo, QotUpdateTicker.Response rsp)
        {
            Console.Write("Push: UpdateTicker: {0}\n", nSerialNo);
            Console.Write("price: {0}\n", rsp.S2C.TickerListList[0].Price);
        }
    
        public static void Main(String[] args) {
            FTAPI.Init();
            Program qot = new Program();
            qot.StaTicker();
    
    
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

    Qot onInitConnect: ret=0 desc= connID=6825408105210218283
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Push: UpdateTicker: 8
    price: 490
    ...
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

`void onPush_UpdateTicker(FTAPI_Conn client, QotUpdateTicker.Response rsp);`

*   **介绍**

实时逐笔回调，异步处理已订阅股票的实时逐笔推送。

*   **参数**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.Ticker tickerList = 2; //推送的逐笔数据结构体
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
> *   逐笔结构参见 [Ticker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9044)
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
    
        public void staTicker() {
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
                    .addSubTypeList(QotCommon.SubType.SubType_Ticker_VALUE)
                    .setIsSubOrUnSub(true)
                    .setIsRegOrUnRegPush(true)
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
            if (rsp.getRetType() != 0) {
                System.out.printf("QotSub failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotSub: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        @Override
        public void onPush_UpdateTicker(FTAPI_Conn client, QotUpdateTicker.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotUpdateTicker failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotUpdateTicker: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public static void main(String[] args) {
            FTAPI.init();
            QotDemo qot = new QotDemo();
            qot.staTicker();
    
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

*   **Output**

    Receive QotUpdateTicker: {
      "retType": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "tickerList": [{\
          "time": "2021-06-25 10:33:47",\
          "sequence": "6977554163425089259",\
          "dir": 1,\
          "price": 587.5,\
          "volume": "1000",\
          "turnover": 587500.0,\
          "recvTime": 0.0,\
          "type": 1,\
          "typeSign": 32,\
          "pushDataType": 3,\
          "timestamp": 1.624588427E9\
        }]
      }
    }
    Receive QotUpdateTicker: {
      "retType": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "tickerList": [{\
          "time": "2021-06-25 10:33:48",\
          "sequence": "6977554167720056556",\
          "dir": 2,\
          "price": 587.0,\
          "volume": "100",\
          "turnover": 58700.0,\
          "recvTime": 1.624588428173763E9,\
          "type": 1,\
          "typeSign": 32,\
          "pushDataType": 1,\
          "timestamp": 1.624588428E9\
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

`virtual void OnPush_UpdateTicker(const Qot_UpdateTicker::Response &stRsp) = 0;`

*   **介绍**
    
    实时逐笔回调，异步处理已订阅股票的实时逐笔推送。
    
*   **参数**
    

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.Ticker tickerList = 2; //推送的逐笔数据结构体
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
> *   逐笔结构参见 [Ticker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9044)
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
    		c2s->add_subtypelist(Qot_Common::SubType::SubType_Ticker);
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
                if (stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "Sub Failed" << endl;
                    return;
                }
            }
    	}
    
    	virtual void OnPush_UpdateTicker(const Qot_UpdateTicker::Response &stRsp) {
    		cout << "OnPush_UpdateTicker: " << endl;
    		// 解析内部结构打印出来
    		// ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
    		string resp_str;
    		ProtoBufToBodyData(stRsp, resp_str);
    		cout << UTF8ToLocal(resp_str) << endl;
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
        
        Futu::u32_t m_SubSerialNo;
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

*   **Output**

    connect
    Request Sub SerialNo: 3
    OnReply_Sub SerialNo: 3
    OnPush_UpdateTicker: 
    {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "tickerList": [\
       {\
        "time": "2021-06-09 11:13:24",\
        "sequence": "6971627009772359401",\
        "dir": 1,\
        "price": 605,\
        "volume": "400",\
        "turnover": 242000,\
        "recvTime": 0,\
        "type": 1,\
        "typeSign": 32,\
        "pushDataType": 3,\
        "timestamp": 1623208404\
       }\
      ]
     }
    }
    
    OnPush_UpdateTicker: 
    {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "tickerList": [\
       {\
        "time": "2021-06-09 11:13:38",\
        "sequence": "6971627069901901546",\
        "dir": 1,\
        "price": 605,\
        "volume": "100",\
        "turnover": 60500,\
        "recvTime": 1623208418.622081,\
        "type": 1,\
        "typeSign": 32,\
        "pushDataType": 1,\
        "timestamp": 1623208418\
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

`OnPush(cmd,res)`

*   **介绍**
    
    实时逐笔回调，异步处理已订阅股票的实时逐笔推送。
    
*   **参数**
    

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.Ticker tickerList = 2; //推送的逐笔数据结构体
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
> *   逐笔结构参见 [Ticker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9044)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    
    function QotUpdateTicker(){
        const { RetType } = Common
        const { SubType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                const req = {
                    c2s: {
                    securityList: [\
                        {\
                            market: QotMarket.QotMarket_HK_Security,\
                            code: "00700",\
                        },\
                    ],
                    subTypeList: [ SubType.SubType_Ticker ], // 订阅实时逐笔类型
                    isSubOrUnSub: true, // 订阅 true, 反订阅 false
                    isRegOrUnRegPush: true, // 注册推送 true, 反注册推送 false
                    },
                }; // 订阅参数
    
                websocket.Sub(req) //# 订阅, OpenD 开始持续收到服务器的推送
                .then((res) => { })
                .catch((error) => {
                    if ("retMsg" in error) {
                        console.log("error:", error.retMsg);
                    }
                });
            } else {
                console.log("error", msg);
            }
        };
    
        websocket.onPush = (cmd, res)=>{
            if(ftCmdID.QotUpdateTicker.cmd == cmd){ // 实时逐笔推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    console.log("TickerTest", JSON.stringify(s2c));
                } else {
                    console.log("TickerTest: error")
                }
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
    
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 5000); // 接收 OpenD 的推送持续时间为5秒,5秒后断开
    }
    

1  
2  
3  
4  
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

    TickerTest {"security":{"market":1,"code":"00700"},"tickerList":[{"time":"2021-09-09 16:08:12","sequence":"7005842815196387657","dir":3,"price":480,"volume":"4561200","turnover":2189376000,"recvTime":0,"type":7,"typeSign":85,"pushDataType":3,"timestamp":1631174892}]}
    TickerTest { ... }
     ...
     ...
    stop
    

1  
2  
3  
4  
5  

提示

*   此接口提供了持续获取推送数据的功能，如需一次性获取实时数据，请参考 [获取实时逐笔](https://openapi.futunn.com/futu-api-doc/quote/get-ticker.html)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)
    
*   行情连接断开重连后，OpenD 拉取断开期间，距离当前最近的（最多 50 根）逐笔数据并推送，可通过逐笔推送类型字段区分

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`on_recv_rsp(self, rsp_pb)`

*   **介绍**
    
    实时逐笔回调，异步处理已订阅股票的实时逐笔推送。  
    在收到实时逐笔数据推送后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Qot\_UpdateTicker\_pb2.Response | 派生类中不需要直接处理该参数 |
    
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回逐笔数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   逐笔数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | sequence | int | 逐笔序号 |
        | time | str | 成交时间<br>(ℹ️ 格式：yyyy-MM-dd HH:mm:ss  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | price | float | 成交价格 |
        | volume | int | 成交数量<br>(ℹ️ 股数) |
        | turnover | float | 成交金额 |
        | ticker\_direction | [TickerDirect](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8723) | 逐笔方向 |
        | type | [TickerType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2358) | 逐笔类型 |
        | push\_data\_type | [PushDataType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7025) | 数据来源 |
        
*   **Example**
    

    import time
    from moomoo import *
    
    class TickerTest(TickerHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, data = super(TickerTest,self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("TickerTest: error, msg: %s" % data)
                return RET_ERROR, data
            print("TickerTest ", data) # TickerTest 自己的处理逻辑
            return RET_OK, data
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = TickerTest()
    quote_ctx.set_handler(handler)  # 设置实时逐笔推送回调
    ret, data = quote_ctx.subscribe(['US.AAPL'], [SubType.TICKER], session=Session.ALL) # 订阅逐笔类型，OpenD 开始持续收到服务器的推送
    if ret == RET_OK:
        print(data)
    else:
        print('error:', data)
    time.sleep(15)  # 设置脚本接收 OpenD 的推送持续时间为15秒
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
14  
15  
16  
17  
18  
19  
20  
21  

*   **Output**

    TickerTest        code name                     time   price  volume  turnover ticker_direction             sequence     type push_data_type
    0  US.AAPL   苹果  2025-04-07 05:25:44.116  179.81       9   1618.29          NEUTRAL  7490500033117159426  ODD_LOT          CACHE
    
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html#1454-2)
 Qot\_UpdateTicker.proto
-----------------------------------------------------------------------------------------------------

*   **介绍**
    
    实时逐笔回调，异步处理已订阅股票的实时逐笔推送。
    
*   **参数**
    

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.Ticker tickerList = 2; //推送的逐笔数据结构体
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
> *   逐笔结构参见 [Ticker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9044)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3011
    

`virtual void OnReply_UpdateTicker(MMAPI_Conn client, QotUpdateTicker.Response rsp);`

*   **介绍**

实时逐笔回调，异步处理已订阅股票的实时逐笔推送。

*   **参数**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.Ticker tickerList = 2; //推送的逐笔数据结构体
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
> *   逐笔结构参见 [Ticker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9044)
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
    
        public void StaTicker() {
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
                    .AddSubTypeList((int)QotCommon.SubType.SubType_Ticker)
                    .SetIsSubOrUnSub(true)
                    .SetIsRegOrUnRegPush(true)
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
        }
    
        public void OnReply_UpdateTicker(MMAPI_Conn client, uint nSerialNo, QotUpdateTicker.Response rsp)
        {
            Console.Write("Push: UpdateTicker: {0}\n", nSerialNo);
            Console.Write("price: {0}\n", rsp.S2C.TickerListList[0].Price);
        }
    
        public static void Main(String[] args) {
            MMAPI.Init();
            Program qot = new Program();
            qot.StaTicker();
    
    
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

    Qot onInitConnect: ret=0 desc= connID=6825408105210218283
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Push: UpdateTicker: 8
    price: 490
    ...
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

`void onPush_UpdateTicker(MMAPI_Conn client, QotUpdateTicker.Response rsp);`

*   **介绍**

实时逐笔回调，异步处理已订阅股票的实时逐笔推送。

*   **参数**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.Ticker tickerList = 2; //推送的逐笔数据结构体
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
> *   逐笔结构参见 [Ticker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9044)
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
    
        public void staTicker() {
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
                    .addSubTypeList(QotCommon.SubType.SubType_Ticker_VALUE)
                    .setIsSubOrUnSub(true)
                    .setIsRegOrUnRegPush(true)
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
            if (rsp.getRetType() != 0) {
                System.out.printf("QotSub failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotSub: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        @Override
        public void onPush_UpdateTicker(MMAPI_Conn client, QotUpdateTicker.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotUpdateTicker failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotUpdateTicker: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public static void main(String[] args) {
            MMAPI.init();
            QotDemo qot = new QotDemo();
            qot.staTicker();
    
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

*   **Output**

    Receive QotUpdateTicker: {
      "retType": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "tickerList": [{\
          "time": "2021-06-25 10:33:47",\
          "sequence": "6977554163425089259",\
          "dir": 1,\
          "price": 587.5,\
          "volume": "1000",\
          "turnover": 587500.0,\
          "recvTime": 0.0,\
          "type": 1,\
          "typeSign": 32,\
          "pushDataType": 3,\
          "timestamp": 1.624588427E9\
        }]
      }
    }
    Receive QotUpdateTicker: {
      "retType": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "tickerList": [{\
          "time": "2021-06-25 10:33:48",\
          "sequence": "6977554167720056556",\
          "dir": 2,\
          "price": 587.0,\
          "volume": "100",\
          "turnover": 58700.0,\
          "recvTime": 1.624588428173763E9,\
          "type": 1,\
          "typeSign": 32,\
          "pushDataType": 1,\
          "timestamp": 1.624588428E9\
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

`virtual void OnPush_UpdateTicker(const Qot_UpdateTicker::Response &stRsp) = 0;`

*   **介绍**
    
    实时逐笔回调，异步处理已订阅股票的实时逐笔推送。
    
*   **参数**
    

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.Ticker tickerList = 2; //推送的逐笔数据结构体
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
> *   逐笔结构参见 [Ticker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9044)
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
    
    		// 这个接口要先订阅
    		Qot_Sub::Request req;
    		Qot_Sub::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->add_subtypelist(Qot_Common::SubType::SubType_Ticker);
    		c2s->set_isregorunregpush(true);
    		c2s->set_issuborunsub(true);
    
            m_SubSerialNo = m_pQotApi->Sub(req);
            cout << "Request Sub SerialNo: " << m_SubSerialNo << endl;
    	}
    
    	virtual void OnReply_Sub(moomoo::u32_t nSerialNo, const Qot_Sub::Response &stRsp)
    	{
            if(nSerialNo == m_SubSerialNo)
            {
                cout << "OnReply_Sub SerialNo: " << nSerialNo << endl;
                if (stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "Sub Failed" << endl;
                    return;
                }
            }
    	}
    
    	virtual void OnPush_UpdateTicker(const Qot_UpdateTicker::Response &stRsp) {
    		cout << "OnPush_UpdateTicker: " << endl;
    		// 解析内部结构打印出来
    		// ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
    		string resp_str;
    		ProtoBufToBodyData(stRsp, resp_str);
    		cout << UTF8ToLocal(resp_str) << endl;
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
        
        moomoo::u32_t m_SubSerialNo;
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

*   **Output**

    connect
    Request Sub SerialNo: 3
    OnReply_Sub SerialNo: 3
    OnPush_UpdateTicker: 
    {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "tickerList": [\
       {\
        "time": "2021-06-09 11:13:24",\
        "sequence": "6971627009772359401",\
        "dir": 1,\
        "price": 605,\
        "volume": "400",\
        "turnover": 242000,\
        "recvTime": 0,\
        "type": 1,\
        "typeSign": 32,\
        "pushDataType": 3,\
        "timestamp": 1623208404\
       }\
      ]
     }
    }
    
    OnPush_UpdateTicker: 
    {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "tickerList": [\
       {\
        "time": "2021-06-09 11:13:38",\
        "sequence": "6971627069901901546",\
        "dir": 1,\
        "price": 605,\
        "volume": "100",\
        "turnover": 60500,\
        "recvTime": 1623208418.622081,\
        "type": 1,\
        "typeSign": 32,\
        "pushDataType": 1,\
        "timestamp": 1623208418\
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

`OnPush(cmd,res)`

*   **介绍**
    
    实时逐笔回调，异步处理已订阅股票的实时逐笔推送。
    
*   **参数**
    

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; //股票名称
    	repeated Qot_Common.Ticker tickerList = 2; //推送的逐笔数据结构体
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
> *   逐笔结构参见 [Ticker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9044)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    
    function QotUpdateTicker(){
        const { RetType } = Common
        const { SubType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                const req = {
                    c2s: {
                    securityList: [\
                        {\
                            market: QotMarket.QotMarket_HK_Security,\
                            code: "00700",\
                        },\
                    ],
                    subTypeList: [ SubType.SubType_Ticker ], // 订阅实时逐笔类型
                    isSubOrUnSub: true, // 订阅 true, 反订阅 false
                    isRegOrUnRegPush: true, // 注册推送 true, 反注册推送 false
                    },
                }; // 订阅参数
    
                websocket.Sub(req) //# 订阅, OpenD 开始持续收到服务器的推送
                .then((res) => { })
                .catch((error) => {
                    if ("retMsg" in error) {
                        console.log("error:", error.retMsg);
                    }
                });
            } else {
                console.log("error", msg);
            }
        };
    
        websocket.onPush = (cmd, res)=>{
            if(mmCmdID.QotUpdateTicker.cmd == cmd){ // 实时逐笔推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    console.log("TickerTest", JSON.stringify(s2c));
                } else {
                    console.log("TickerTest: error")
                }
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
    
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 5000); // 接收 OpenD 的推送持续时间为5秒,5秒后断开
    }
    

1  
2  
3  
4  
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

    TickerTest {"security":{"market":1,"code":"00700"},"tickerList":[{"time":"2021-09-09 16:08:12","sequence":"7005842815196387657","dir":3,"price":480,"volume":"4561200","turnover":2189376000,"recvTime":0,"type":7,"typeSign":85,"pushDataType":3,"timestamp":1631174892}]}
    TickerTest { ... }
     ...
     ...
    stop
    

1  
2  
3  
4  
5  

提示

*   此接口提供了持续获取推送数据的功能，如需一次性获取实时数据，请参考 [获取实时逐笔](https://openapi.futunn.com/futu-api-doc/quote/get-ticker.html)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)
    
*   行情连接断开重连后，OpenD 拉取断开期间，距离当前最近的（最多 50 根）逐笔数据并推送，可通过逐笔推送类型字段区分

← [实时分时回调](https://openapi.futunn.com/futu-api-doc/quote/update-rt.html) [实时经纪队列回调](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html)
 →

[实时逐笔回调](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html)