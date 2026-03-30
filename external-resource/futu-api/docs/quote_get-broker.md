[#](./quote_get-broker.md#9883)
 获取实时经纪队列
=================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_broker_queue(code)`

*   **介绍**
    
    获取已订阅股票的实时经纪队列数据，必须要先订阅。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票代码 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | bid\_frame\_table | pd.DataFrame | 当 ret == RET\_OK，bid\_frame\_table 返回买盘经纪队列数据 |
    | str | 当 ret != RET\_OK，bid\_frame\_table 返回错误描述 |
    | ask\_frame\_table | pd.DataFrame | 当 ret == RET\_OK，ask\_frame\_table 返回卖盘经纪队列数据 |
    | str | 当 ret != RET\_OK，ask\_frame\_table 返回错误描述 |
    
    *   买盘经纪队列格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | bid\_broker\_id | int | 经纪买盘 ID |
        | bid\_broker\_name | str | 经纪买盘名称 |
        | bid\_broker\_pos | int | 经纪档位 |
        | order\_id | int | 交易所订单 ID<br>(ℹ️ *   不是下单接口返回的订单 ID)<br>*   只有港股 SF 行情权限支持返回该字段 |
        | order\_volume | int | 单笔委托数量<br>(ℹ️ 只有港股 SF 行情权限支持返回该字段) |
        
    *   卖盘经纪队列格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | ask\_broker\_id | int | 经纪卖盘 ID |
        | ask\_broker\_name | str | 经纪卖盘名称 |
        | ask\_broker\_pos | int | 经纪档位 |
        | order\_id | int | 交易所订单 ID<br>(ℹ️ *   不是下单接口返回的订单 ID)<br>*   只有港股 SF 行情权限支持返回该字段 |
        | order\_volume | int | 单笔委托数量<br>(ℹ️ 只有港股 SF 行情权限支持返回该字段) |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret_sub, err_message = quote_ctx.subscribe(['HK.00700'], [SubType.BROKER], subscribe_push=False)
    # 先订阅经纪队列类型。订阅成功后 OpenD 将持续收到服务器的推送，False 代表暂时不需要推送给脚本
    if ret_sub == RET_OK:   # 订阅成功
        ret, bid_frame_table, ask_frame_table = quote_ctx.get_broker_queue('HK.00700')   # 获取一次经纪队列数据
        if ret == RET_OK:
            print(bid_frame_table)
        else:
            print('error:', bid_frame_table)
    else:
        print(err_message)
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

            code  name  bid_broker_id bid_broker_name  bid_broker_pos order_id order_volume
    0   HK.00700  腾讯控股           5338          J.P.摩根               1      N/A          N/A
    ..       ...   ...            ...             ...             ...      ...          ...
    36  HK.00700  腾讯控股           8305  富途证券国际(香港)有限公司               4      N/A          N/A
    
    [37 rows x 7 columns]
    

1  
2  
3  
4  
5  
6  

[#](./quote_get-broker.md#2229)
 Qot\_GetBroker.proto
---------------------------------------------------------------------------------------------

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
    	optional string name = 4; // 股票名称
        repeated Qot_Common.Broker brokerAskList = 2; //经纪 Ask(卖)盘
    	repeated Qot_Common.Broker brokerBidList = 3; //经纪 Bid(买)盘
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   经纪队列结构参见 [Broker](./quote_quote.md#4135)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3014
    

`uint GetBroker(QotGetBroker.Request req);`  
`virtual void OnReply_GetBroker(FTAPI_Conn client, uint nSerialNo, QotGetBroker.Response rsp);`

*   **介绍**

获取已订阅股票的实时报价，必须要先订阅。

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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
    	optional string name = 4; // 股票名称
        repeated Qot_Common.Broker brokerAskList = 2; //经纪 Ask(卖)盘
    	repeated Qot_Common.Broker brokerBidList = 3; //经纪 Bid(买)盘
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   经纪队列结构参见 [Broker](./quote_quote.md#4135)
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
            QotSub.C2S c2s = QotSub.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                    .AddSubTypeList((int)QotCommon.SubType.SubType_Broker)
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
            QotGetBroker.C2S c2s = QotGetBroker.C2S.CreateBuilder()
                    .SetSecurity(sec)
                    .Build();
            QotGetBroker.Request req = QotGetBroker.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetBroker(req);
            Console.Write("Send QotGetBroker: {0}\n", seqNo);
        }
    
        public void OnReply_GetBroker(FTAPI_Conn client, uint nSerialNo, QotGetBroker.Response rsp)
        {
            Console.Write("Reply: QotGetBroker: {0}\n", nSerialNo);
            Console.Write("id: {0}, name: {1}\n", rsp.S2C.BrokerAskListList[0].Id, rsp.S2C.BrokerAskListList[0].Name);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825676366648053042
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Send QotGetBroker: 4
    Reply: QotGetBroker: 4
    id: 5465, name: 富途证券国际(香港)有限公司
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

`int getBroker(QotGetBroker.Request req);`  
`void onReply_GetBroker(FTAPI_Conn client, int nSerialNo, QotGetBroker.Response rsp);`

*   **介绍**

获取已订阅股票的实时报价，必须要先订阅。

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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
    	optional string name = 4; // 股票名称
        repeated Qot_Common.Broker brokerAskList = 2; //经纪 Ask(卖)盘
    	repeated Qot_Common.Broker brokerBidList = 3; //经纪 Bid(买)盘
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   经纪队列结构参见 [Broker](./quote_quote.md#4135)
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
            QotSub.C2S c2s = QotSub.C2S.newBuilder()
                    .addSecurityList(sec)
                    .addSubTypeList(QotCommon.SubType.SubType_Broker_VALUE)
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
            QotGetBroker.C2S c2s = QotGetBroker.C2S.newBuilder()
                    .setSecurity(sec)
                    .build();
            QotGetBroker.Request req = QotGetBroker.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getBroker(req);
            System.out.printf("Send QotGetBroker: %d\n", seqNo);
        }
    
        @Override
        public void onReply_GetBroker(FTAPI_Conn client, int nSerialNo, QotGetBroker.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetBroker failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetBroker: %s\n", json);
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

    Send QotGetBroker: 3
    Receive QotGetBroker: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "brokerAskList": [{\
          "id": "8908",\
          "name": "中银国际证券有限公司",\
          "pos": 1\
        }, ... {\
          "id": "8305",\
          "name": "富途证券国际(香港)有限公司",\
          "pos": 2\
        }],
        "brokerBidList": [{\
          "id": "4108",\
          "name": "法国巴黎证券(亚洲)有限公司",\
          "pos": 1\
        }, ... {\
          "id": "9033",\
          "name": "招商证券(香港)有限公司",\
          "pos": 3\
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

`Futu::u32_t GetBroker(const Qot_GetBroker::Request &stReq);`  
`virtual void OnReply_GetBroker(Futu::u32_t nSerialNo, const Qot_GetBroker::Response &stRsp) = 0;`

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
    	optional string name = 4; // 股票名称
        repeated Qot_Common.Broker brokerAskList = 2; //经纪 Ask(卖)盘
    	repeated Qot_Common.Broker brokerBidList = 3; //经纪 Bid(买)盘
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   经纪队列结构参见 [Broker](./quote_quote.md#4135)
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
    
    		// 这个接口要先订阅
    		Qot_Sub::Request req;
    		Qot_Sub::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->add_subtypelist(Qot_Common::SubType::SubType_Broker);
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
                Qot_GetBroker::Request req;
                Qot_GetBroker::C2S *c2s = req.mutable_c2s();
                Qot_Common::Security *sec = c2s->mutable_security();
                sec->set_code("00700");
                sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
                m_GetBrokerSerialNo = m_pQotApi->GetBroker(req);
                cout << "Request GetBroker SerialNo: " << m_GetBrokerSerialNo << endl;
            }
    	}
    
    	virtual void OnReply_GetBroker(Futu::u32_t nSerialNo, const Qot_GetBroker::Response &stRsp){
            if(nSerialNo == m_GetBrokerSerialNo)
            {
                cout << "OnReply_GetBroker SerialNo: " << nSerialNo << endl;
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
        Futu::u32_t m_GetBrokerSerialNo;
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
    Request GetBroker SerialNo: 4
    OnReply_GetBroker SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "brokerAskList": [\
       {\
        "id": "6996",\
        "name": "中国投资信息有限公司",\
        "pos": 1\
       },\
       {\
        "id": "8037",\
        "name": "中信里昂证券有限公司",\
        "pos": 1\
       },\
    ...\
       {\
        "id": "4978",\
        "name": "法国兴业证券(香港)有限公司",\
        "pos": 3\
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

`GetBroker(req);`

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
    	optional string name = 4; // 股票名称
        repeated Qot_Common.Broker brokerAskList = 2; //经纪 Ask(卖)盘
    	repeated Qot_Common.Broker brokerBidList = 3; //经纪 Bid(买)盘
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   经纪队列结构参见 [Broker](./quote_quote.md#4135)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetBroker(){
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
                    subTypeList: [ SubType.SubType_Broker ], // 订阅实时经纪队列类型
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
                    
                    websocket.GetBroker(req)
                    .then((res) => {
                        let { errCode, retMsg, retType,s2c } = res
                        console.log("Broker: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    Broker: errCode 0, retMsg , retType 0
    {
      "security": {
        "market": 1,
        "code": "00700"
      },
      "brokerAskList": [{\
        "id": "6429",\
        "name": "中信証券经纪(香港)有限公司",\
        "pos": 1\
      }, {\
        "id": "4973",\
        "name": "法国兴业证券(香港)有限公司",\
        "pos": 1\
      }, ..., {\
        "id": "2310",\
        "name": "恒生证券有限公司",\
        "pos": 5\
      }],
      "brokerBidList": [{\
        "id": "2846",\
        "name": "麦格理资本股份有限公司",\
        "pos": 1\
      }, {\
        "id": "8578",\
        "name": "汇丰证券经纪(亚洲)有限公司",\
        "pos": 1\
      }, ..., {\
        "id": "2245",\
        "name": "富途证券国际(香港)有限公司",\
        "pos": 1\
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

提示

*   此接口提供了一次性获取实时数据的功能，如需持续获取推送数据，请参考 [实时经纪队列回调](./quote_update-broker.md)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](./qa_quote.md#2692)
    
*   港股 BMP及LV1 权限下，不支持获取经纪队列数据

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_broker_queue(code)`

*   **介绍**
    
    获取已订阅股票的实时经纪队列数据，必须要先订阅。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票代码 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | bid\_frame\_table | pd.DataFrame | 当 ret == RET\_OK，bid\_frame\_table 返回买盘经纪队列数据 |
    | str | 当 ret != RET\_OK，bid\_frame\_table 返回错误描述 |
    | ask\_frame\_table | pd.DataFrame | 当 ret == RET\_OK，ask\_frame\_table 返回卖盘经纪队列数据 |
    | str | 当 ret != RET\_OK，ask\_frame\_table 返回错误描述 |
    
    *   买盘经纪队列格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | bid\_broker\_id | int | 经纪买盘 ID |
        | bid\_broker\_name | str | 经纪买盘名称 |
        | bid\_broker\_pos | int | 经纪档位 |
        | order\_id | int | 交易所订单 ID<br>(ℹ️ *   不是下单接口返回的订单 ID)<br>*   只有港股 SF 行情权限支持返回该字段 |
        | order\_volume | int | 单笔委托数量<br>(ℹ️ 只有港股 SF 行情权限支持返回该字段) |
        
    *   卖盘经纪队列格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | ask\_broker\_id | int | 经纪卖盘 ID |
        | ask\_broker\_name | str | 经纪卖盘名称 |
        | ask\_broker\_pos | int | 经纪档位 |
        | order\_id | int | 交易所订单 ID<br>(ℹ️ *   不是下单接口返回的订单 ID)<br>*   只有港股 SF 行情权限支持返回该字段 |
        | order\_volume | int | 单笔委托数量<br>(ℹ️ 只有港股 SF 行情权限支持返回该字段) |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret_sub, err_message = quote_ctx.subscribe(['HK.00700'], [SubType.BROKER], subscribe_push=False)
    # 先订阅经纪队列类型。订阅成功后 OpenD 将持续收到服务器的推送，False 代表暂时不需要推送给脚本
    if ret_sub == RET_OK:   # 订阅成功
        ret, bid_frame_table, ask_frame_table = quote_ctx.get_broker_queue('HK.00700')   # 获取一次经纪队列数据
        if ret == RET_OK:
            print(bid_frame_table)
        else:
            print('error:', bid_frame_table)
    else:
        print(err_message)
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

            code  name  bid_broker_id bid_broker_name  bid_broker_pos order_id order_volume
    0   HK.00700  腾讯控股           5338          J.P.摩根               1      N/A          N/A
    ..       ...   ...            ...             ...             ...      ...          ...
    36  HK.00700  腾讯控股           8305  富途证券国际(香港)有限公司               4      N/A          N/A
    
    [37 rows x 7 columns]
    

1  
2  
3  
4  
5  
6  

[#](./quote_get-broker.md#2229-2)
 Qot\_GetBroker.proto
-----------------------------------------------------------------------------------------------

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
    	optional string name = 4; // 股票名称
        repeated Qot_Common.Broker brokerAskList = 2; //经纪 Ask(卖)盘
    	repeated Qot_Common.Broker brokerBidList = 3; //经纪 Bid(买)盘
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   经纪队列结构参见 [Broker](./quote_quote.md#4135)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3014
    

`uint GetBroker(QotGetBroker.Request req);`  
`virtual void OnReply_GetBroker(MMAPI_Conn client, uint nSerialNo, QotGetBroker.Response rsp);`

*   **介绍**

获取已订阅股票的实时报价，必须要先订阅。

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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
    	optional string name = 4; // 股票名称
        repeated Qot_Common.Broker brokerAskList = 2; //经纪 Ask(卖)盘
    	repeated Qot_Common.Broker brokerBidList = 3; //经纪 Bid(买)盘
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   经纪队列结构参见 [Broker](./quote_quote.md#4135)
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
            QotSub.C2S c2s = QotSub.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                    .AddSubTypeList((int)QotCommon.SubType.SubType_Broker)
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
            QotGetBroker.C2S c2s = QotGetBroker.C2S.CreateBuilder()
                    .SetSecurity(sec)
                    .Build();
            QotGetBroker.Request req = QotGetBroker.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetBroker(req);
            Console.Write("Send QotGetBroker: {0}\n", seqNo);
        }
    
        public void OnReply_GetBroker(MMAPI_Conn client, uint nSerialNo, QotGetBroker.Response rsp)
        {
            Console.Write("Reply: QotGetBroker: {0}\n", nSerialNo);
            Console.Write("id: {0}, name: {1}\n", rsp.S2C.BrokerAskListList[0].Id, rsp.S2C.BrokerAskListList[0].Name);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825676366648053042
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Send QotGetBroker: 4
    Reply: QotGetBroker: 4
    id: 5465, name: 富途证券国际(香港)有限公司
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

`int getBroker(QotGetBroker.Request req);`  
`void onReply_GetBroker(MMAPI_Conn client, int nSerialNo, QotGetBroker.Response rsp);`

*   **介绍**

获取已订阅股票的实时报价，必须要先订阅。

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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
    	optional string name = 4; // 股票名称
        repeated Qot_Common.Broker brokerAskList = 2; //经纪 Ask(卖)盘
    	repeated Qot_Common.Broker brokerBidList = 3; //经纪 Bid(买)盘
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   经纪队列结构参见 [Broker](./quote_quote.md#4135)
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
            QotSub.C2S c2s = QotSub.C2S.newBuilder()
                    .addSecurityList(sec)
                    .addSubTypeList(QotCommon.SubType.SubType_Broker_VALUE)
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
            QotGetBroker.C2S c2s = QotGetBroker.C2S.newBuilder()
                    .setSecurity(sec)
                    .build();
            QotGetBroker.Request req = QotGetBroker.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getBroker(req);
            System.out.printf("Send QotGetBroker: %d\n", seqNo);
        }
    
        @Override
        public void onReply_GetBroker(MMAPI_Conn client, int nSerialNo, QotGetBroker.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetBroker failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetBroker: %s\n", json);
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

    Send QotGetBroker: 3
    Receive QotGetBroker: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "brokerAskList": [{\
          "id": "8908",\
          "name": "中银国际证券有限公司",\
          "pos": 1\
        }, ... {\
          "id": "8305",\
          "name": "富途证券国际(香港)有限公司",\
          "pos": 2\
        }],
        "brokerBidList": [{\
          "id": "4108",\
          "name": "法国巴黎证券(亚洲)有限公司",\
          "pos": 1\
        }, ... {\
          "id": "9033",\
          "name": "招商证券(香港)有限公司",\
          "pos": 3\
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

`moomoo::u32_t GetBroker(const Qot_GetBroker::Request &stReq);`  
`virtual void OnReply_GetBroker(moomoo::u32_t nSerialNo, const Qot_GetBroker::Response &stRsp) = 0;`

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
    	optional string name = 4; // 股票名称
        repeated Qot_Common.Broker brokerAskList = 2; //经纪 Ask(卖)盘
    	repeated Qot_Common.Broker brokerBidList = 3; //经纪 Bid(买)盘
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   经纪队列结构参见 [Broker](./quote_quote.md#4135)
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
    
    		// 这个接口要先订阅
    		Qot_Sub::Request req;
    		Qot_Sub::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->add_subtypelist(Qot_Common::SubType::SubType_Broker);
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
                if(stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "Sub Failed" << endl;
                    return;
                }        
                
                // 组包
                Qot_GetBroker::Request req;
                Qot_GetBroker::C2S *c2s = req.mutable_c2s();
                Qot_Common::Security *sec = c2s->mutable_security();
                sec->set_code("00700");
                sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
                m_GetBrokerSerialNo = m_pQotApi->GetBroker(req);
                cout << "Request GetBroker SerialNo: " << m_GetBrokerSerialNo << endl;
            }
    	}
    
    	virtual void OnReply_GetBroker(moomoo::u32_t nSerialNo, const Qot_GetBroker::Response &stRsp){
            if(nSerialNo == m_GetBrokerSerialNo)
            {
                cout << "OnReply_GetBroker SerialNo: " << nSerialNo << endl;
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
        moomoo::u32_t m_GetBrokerSerialNo;
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
    Request GetBroker SerialNo: 4
    OnReply_GetBroker SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "brokerAskList": [\
       {\
        "id": "6996",\
        "name": "中国投资信息有限公司",\
        "pos": 1\
       },\
       {\
        "id": "8037",\
        "name": "中信里昂证券有限公司",\
        "pos": 1\
       },\
    ...\
       {\
        "id": "4978",\
        "name": "法国兴业证券(香港)有限公司",\
        "pos": 3\
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

`GetBroker(req);`

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
    	optional string name = 4; // 股票名称
        repeated Qot_Common.Broker brokerAskList = 2; //经纪 Ask(卖)盘
    	repeated Qot_Common.Broker brokerBidList = 3; //经纪 Bid(买)盘
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   经纪队列结构参见 [Broker](./quote_quote.md#4135)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetBroker(){
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
                    subTypeList: [ SubType.SubType_Broker ], // 订阅实时经纪队列类型
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
                    
                    websocket.GetBroker(req)
                    .then((res) => {
                        let { errCode, retMsg, retType,s2c } = res
                        console.log("Broker: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    Broker: errCode 0, retMsg , retType 0
    {
      "security": {
        "market": 1,
        "code": "00700"
      },
      "brokerAskList": [{\
        "id": "6429",\
        "name": "中信証券经纪(香港)有限公司",\
        "pos": 1\
      }, {\
        "id": "4973",\
        "name": "法国兴业证券(香港)有限公司",\
        "pos": 1\
      }, ..., {\
        "id": "2310",\
        "name": "恒生证券有限公司",\
        "pos": 5\
      }],
      "brokerBidList": [{\
        "id": "2846",\
        "name": "麦格理资本股份有限公司",\
        "pos": 1\
      }, {\
        "id": "8578",\
        "name": "汇丰证券经纪(亚洲)有限公司",\
        "pos": 1\
      }, ..., {\
        "id": "2245",\
        "name": "富途证券国际(香港)有限公司",\
        "pos": 1\
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

提示

*   此接口提供了一次性获取实时数据的功能，如需持续获取推送数据，请参考 [实时经纪队列回调](./quote_update-broker.md)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](./qa_quote.md#2692)
    
*   港股 LV1 权限下，不支持获取经纪队列数据

← [获取实时逐笔](./quote_get-ticker.md) [获取标的市场状态](./quote_get-market-state.md)
 →

[获取实时经纪队列](./quote_get-broker.md)