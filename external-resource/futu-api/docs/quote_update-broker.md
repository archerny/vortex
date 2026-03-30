# 实时经纪队列回调 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quote/update-broker.html

[#](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html#4602)
 实时经纪队列回调
====================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`on_recv_rsp(self, rsp_pb)`

*   **介绍**
    
    实时经纪队列回调，异步处理已订阅股票的实时经纪队列推送。  
    在收到实时经纪队列数据推送后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Qot\_UpdateBroker\_pb2.Response | 派生类中不需要直接处理该参数 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | tuple | 当 ret == RET\_OK，返回经纪队列数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   经纪队列元组内容如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | stock\_code | str | 股票  |
        | bid\_frame\_table | pd.DataFrame | 买盘数据 |
        | ask\_frame\_table | pd.DataFrame | 卖盘数据 |
        
        *   bid\_frame\_table 格式如下：
            
            | 字段  | 类型  | 说明  |
            | --- | --- | --- |
            | code | str | 股票代码 |
            | name | str | 股票名称 |
            | bid\_broker\_id | int | 经纪买盘 ID |
            | bid\_broker\_name | str | 经纪买盘名称 |
            | bid\_broker\_pos | int | 经纪档位 |
            | order\_id | int | 交易所订单 ID<br>(ℹ️ *   不是下单接口返回的订单 ID<br>*   只有港股 SF 行情权限支持返回该字段) |
            | order\_volume | int | 单笔委托数量<br>(ℹ️ 只有港股 SF 行情权限支持返回该字段) |
            
        *   ask\_frame\_table 格式如下：
            
            | 字段  | 类型  | 说明  |
            | --- | --- | --- |
            | code | str | 股票代码 |
            | name | str | 股票名称 |
            | ask\_broker\_id | int | 经纪卖盘 ID |
            | ask\_broker\_name | str | 经纪卖盘名称 |
            | ask\_broker\_pos | int | 经纪档位 |
            | order\_id | int | 交易所订单 ID<br>(ℹ️ *   不是下单接口返回的订单 ID<br>*   只有港股 SF 行情权限支持返回该字段) |
            | order\_volume | int | 单笔委托数量<br>(ℹ️ 只有港股 SF 行情权限支持返回该字段) |
            
*   **Example**
    

    import time
    from futu import *
        
    class BrokerTest(BrokerHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, err_or_stock_code, data = super(BrokerTest, self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("BrokerTest: error, msg: {}".format(err_or_stock_code))
                return RET_ERROR, data
            print("BrokerTest: stock: {} data: {} ".format(err_or_stock_code, data))  # BrokerTest 自己的处理逻辑
            return RET_OK, data
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = BrokerTest()
    quote_ctx.set_handler(handler)  # 设置实时经纪推送回调
    ret, data = quote_ctx.subscribe(['HK.00700'], [SubType.BROKER]) # 订阅经纪类型，OpenD 开始持续收到服务器的推送
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

    BrokerTest: stock: HK.00700 data: [        code  name  bid_broker_id bid_broker_name  bid_broker_pos order_id order_volume\
    0   HK.00700  腾讯控股           5338          J.P.摩根               1      N/A          N/A\
    ..       ...   ...            ...             ...             ...      ...          ...\
    36  HK.00700  腾讯控股           8305  富途证券国际(香港)有限公司               4      N/A          N/A\
    \
    [37 rows x 7 columns],         code  name  ask_broker_id ask_broker_name  ask_broker_pos order_id order_volume\
    0   HK.00700  腾讯控股           1179  华泰金融控股(香港)有限公司               1      N/A          N/A\
    ..       ...   ...            ...             ...             ...      ...          ...\
    39  HK.00700  腾讯控股           6996      中国投资信息有限公司               1      N/A          N/A\
    \
    [40 rows x 7 columns]] 
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

[#](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html#2655)
 Qot\_UpdateBroker.proto
---------------------------------------------------------------------------------------------------

*   **介绍**
    
    实时经纪队列回调，异步处理已订阅股票的实时经纪队列推送。
    
*   **参数**
    

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
16  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   经纪队列结构参见 [Broker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4135)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3015
    

`virtual void OnReply_UpdateBroker(FTAPI_Conn client, QotUpdateBroker.Response rsp);`

*   **介绍**

实时经纪队列回调，异步处理已订阅股票的实时经纪队列推送。

*   **参数**

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
16  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   经纪队列结构参见 [Broker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4135)
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
                    .AddSubTypeList((int)QotCommon.SubType.SubType_Broker)
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
        
        public void OnReply_UpdateBroker(FTAPI_Conn client, uint nSerialNo, QotUpdateBroker.Response rsp)
        {
            Console.Write("Push: UpdateBroker: {0}\n", nSerialNo);
            Console.Write("id: {0} , name: {1}\n", rsp.S2C.BrokerAskListList[0].Id, rsp.S2C.BrokerAskListList[0].Name);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825611828715002257
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Push: UpdateBroker: 1
    id: 696 , name: 富途证券国际(香港)有限公司
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

`void onPush_UpdateBroker(FTAPI_Conn client, QotUpdateBroker.Response rsp);`

*   **介绍**

实时经纪队列回调，异步处理已订阅股票的实时经纪队列推送。

*   **参数**

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
16  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   经纪队列结构参见 [Broker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4135)
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
                    .addSubTypeList(QotCommon.SubType.SubType_Broker_VALUE)
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
        public void onPush_UpdateBroker(FTAPI_Conn client, QotUpdateBroker.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotUpdateBroker failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotUpdateBroker: %s\n", json);
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

*   **Output**

    Receive QotUpdateBroker: {
      "retType": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "brokerAskList": [{\
          "id": "1836",\
          "name": "极讯亚太有限公司",\
          "pos": 1\
        }, ... {\
          "id": "1799",\
          "name": "耀才证券国际(香港)有限公司",\
          "pos": 1\
        }],
        "brokerBidList": [{\
          "id": "8304",\
          "name": "富途证券国际(香港)有限公司",\
          "pos": 1\
        }, ... {\
          "id": "2246",\
          "name": "富途证券国际(香港)有限公司",\
          "pos": 2\
        }]
      }
    }
    Receive QotUpdateBroker: {
      "retType": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "brokerAskList": [{\
          "id": "1836",\
          "name": "极讯亚太有限公司",\
          "pos": 1\
        }, ... {\
          "id": "1799",\
          "name": "耀才证券国际(香港)有限公司",\
          "pos": 1\
        }],
        "brokerBidList": [{\
          "id": "8304",\
          "name": "富途证券国际(香港)有限公司",\
          "pos": 1\
        }, ... {\
          "id": "6998",\
          "name": "中国投资信息有限公司",\
          "pos": 2\
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
52  
53  
54  

`virtual void OnPush_UpdateBroker(const Qot_UpdateBroker::Response &stRsp) = 0;`

*   **介绍**
    
    实时经纪队列回调，异步处理已订阅股票的实时经纪队列推送。
    
*   **参数**
    

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
16  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   经纪队列结构参见 [Broker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4135)
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
                if (stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "Sub Failed" << endl;
                    return;
                }
            }
    	}
    
    	virtual void OnPush_UpdateBroker(const Qot_UpdateBroker::Response &stRsp) {
    		cout << "OnPush_UpdateBroker: " << endl;
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
    OnPush_UpdateBroker: 
    {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "brokerAskList": [\
       {\
        "id": "6699",\
        "name": "盈透证券香港有限公司",\
        "pos": 1\
       },\
    ...\
       {\
        "id": "8303",\
        "name": "富途证券国际(香港)有限公司",\
        "pos": 1\
       }\
      ],
      "brokerBidList": [\
       {\
        "id": "5199",\
        "name": "国泰君安证券(香港)有限公司",\
        "pos": 1\
       },\
    ...\
       {\
        "id": "8301",\
        "name": "富途证券国际(香港)有限公司",\
        "pos": 2\
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

`OnPush(cmd,res)`

*   **介绍**
    
    实时经纪队列回调，异步处理已订阅股票的实时经纪队列推送。
    
*   **参数**
    

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
16  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   经纪队列结构参见 [Broker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4135)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    
    function QotUpdateBroker(){
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
                    subTypeList: [ SubType.SubType_Broker ], // 订阅实时经纪队列类型
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
            if(ftCmdID.QotUpdateBroker.cmd == cmd){ // 实时经纪队列推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    console.log("BrokerTest", JSON.stringify(s2c));
                } else {
                    console.log("BrokerTest: error")
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

    BrokerTest {"security":{"market":1,"code":"00700"},"brokerAskList":[{"id":"6429","name":"中信証券经纪(香港)有限公司","pos":1},{"id":"4973","name":"法国兴业证券(香港)有限公司","pos":1},{"id":"1175","name":"华泰金融控股(香港)有限公司","pos":2},{"id":"6389","name":"摩根士丹利","pos":2},{"id":"230","name":"立桥证券有限公司","pos":2},{"id":"9025","name":"瑞银","pos":2},{"id":"4098","name":"瑞士信贷证券(香港)有限公司","pos":2},{"id":"8734","name":"汇丰证券经纪(亚洲)有限公司","pos":2},{"id":"8308","name":"富途证券国际(香港)有限公司","pos":2},{"id":"3439","name":"高盛(亚洲)证券有限公司","pos":2},{"id":"8578","name":"汇丰证券经纪(亚洲)有限公司","pos":2},{"id":"3439","name":"高盛(亚洲)证券有限公司","pos":2},{"id":"3439","name":"高盛(亚洲)证券有限公司","pos":2},{"id":"3439","name":"高盛(亚洲)证券有限公司","pos":2},{"id":"8564","name":"荷银","pos":2},{"id":"8565","name":"荷银","pos":2},{"id":"8464","name":"富途证券国际(香港)有限公司","pos":3},{"id":"1174","name":"华泰金融控股(香港)有限公司","pos":3},{"id":"8578","name":"汇丰证券经纪(亚洲)有限公司","pos":3},{"id":"5666","name":"华侨永亨証券有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":4},{"id":"4098","name":"瑞士信贷证券(香港)有限公司","pos":4},{"id":"4098","name":"瑞士信贷证券(香港)有限公司","pos":4},{"id":"4098","name":"瑞士信贷证券(香港)有限公司","pos":4},{"id":"6998","name":"中国投资信息有限公司","pos":4},{"id":"1292","name":"招银国际证券有限公司","pos":4},{"id":"1177","name":"华泰金融控股(香港)有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":4},{"id":"1175","name":"华泰金融控股(香港)有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":5},{"id":"6999","name":"中国投资信息有限公司","pos":5},{"id":"2310","name":"恒生证券有限公司","pos":5}],"brokerBidList":[{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"8578","name":"汇丰证券经纪(亚洲)有限公司","pos":1},{"id":"6998","name":"中国投资信息有限公司","pos":1},{"id":"2847","name":"麦格理资本股份有限公司","pos":1},{"id":"6997","name":"中国投资信息有限公司","pos":1},{"id":"2847","name":"麦格理资本股份有限公司","pos":1},{"id":"6699","name":"盈透证券香港有限公司","pos":1},{"id":"2974","name":"星展唯高达香港有限公司","pos":1},{"id":"2847","name":"麦格理资本股份有限公司","pos":1},{"id":"1292","name":"招银国际证券有限公司","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"8463","name":"富途证券国际(香港)有限公司","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"5999","name":"中国创盈市场服务有限公司","pos":1},{"id":"5666","name":"华侨永亨証券有限公司","pos":1},{"id":"2847","name":"麦格理资本股份有限公司","pos":1},{"id":"5999","name":"中国创盈市场服务有限公司","pos":1},{"id":"5368","name":"星展唯高达香港有限公司","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"1799","name":"耀才证券国际(香港)有限公司","pos":1},{"id":"690","name":"一通投资者有限公司","pos":1},{"id":"2847","name":"麦格理资本股份有限公司","pos":1},{"id":"229","name":"摩根士丹利","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"8467","name":"富途证券国际(香港)有限公司","pos":1},{"id":"6996","name":"中国投资信息有限公司","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"8574","name":"汇丰证券经纪(亚洲)有限公司","pos":1},{"id":"3643","name":"美林","pos":1},{"id":"6721","name":"宝生证券及期货有限公司","pos":1},{"id":"6727","name":"宝生证券及期货有限公司","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"5999","name":"中国创盈市场服务有限公司","pos":1},{"id":"9139","name":"实德证券有限公司","pos":1},{"id":"2026","name":"东亚证券有限公司","pos":1},{"id":"1978","name":"哈富证券有限公司","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"1799","name":"耀才证券国际(香港)有限公司","pos":1},{"id":"2245","name":"富途证券国际(香港)有限公司","pos":1}]}
    BrokerTest { ... }
     ...
     ...
    stop
    

1  
2  
3  
4  
5  

提示

*   此接口提供了持续获取推送数据的功能，如需一次性获取实时数据，请参考 [获取实时经纪队列](https://openapi.futunn.com/futu-api-doc/quote/get-broker.html)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)
    
*   港股 BMP及LV1 权限下，不支持获取经纪队列数据

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`on_recv_rsp(self, rsp_pb)`

*   **介绍**
    
    实时经纪队列回调，异步处理已订阅股票的实时经纪队列推送。  
    在收到实时经纪队列数据推送后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Qot\_UpdateBroker\_pb2.Response | 派生类中不需要直接处理该参数 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | tuple | 当 ret == RET\_OK，返回经纪队列数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   经纪队列元组内容如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | stock\_code | str | 股票  |
        | bid\_frame\_table | pd.DataFrame | 买盘数据 |
        | ask\_frame\_table | pd.DataFrame | 卖盘数据 |
        
        *   bid\_frame\_table 格式如下：
            
            | 字段  | 类型  | 说明  |
            | --- | --- | --- |
            | code | str | 股票代码 |
            | name | str | 股票名称 |
            | bid\_broker\_id | int | 经纪买盘 ID |
            | bid\_broker\_name | str | 经纪买盘名称 |
            | bid\_broker\_pos | int | 经纪档位 |
            | order\_id | int | 交易所订单 ID<br>(ℹ️ *   不是下单接口返回的订单 ID<br>*   只有港股 SF 行情权限支持返回该字段) |
            | order\_volume | int | 单笔委托数量<br>(ℹ️ 只有港股 SF 行情权限支持返回该字段) |
            
        *   ask\_frame\_table 格式如下：
            
            | 字段  | 类型  | 说明  |
            | --- | --- | --- |
            | code | str | 股票代码 |
            | name | str | 股票名称 |
            | ask\_broker\_id | int | 经纪卖盘 ID |
            | ask\_broker\_name | str | 经纪卖盘名称 |
            | ask\_broker\_pos | int | 经纪档位 |
            | order\_id | int | 交易所订单 ID<br>(ℹ️ *   不是下单接口返回的订单 ID<br>*   只有港股 SF 行情权限支持返回该字段) |
            | order\_volume | int | 单笔委托数量<br>(ℹ️ 只有港股 SF 行情权限支持返回该字段) |
            
*   **Example**
    

    import time
    from moomoo import *
        
    class BrokerTest(BrokerHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, err_or_stock_code, data = super(BrokerTest, self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("BrokerTest: error, msg: {}".format(err_or_stock_code))
                return RET_ERROR, data
            print("BrokerTest: stock: {} data: {} ".format(err_or_stock_code, data))  # BrokerTest 自己的处理逻辑
            return RET_OK, data
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = BrokerTest()
    quote_ctx.set_handler(handler)  # 设置实时经纪推送回调
    ret, data = quote_ctx.subscribe(['HK.00700'], [SubType.BROKER]) # 订阅经纪类型，OpenD 开始持续收到服务器的推送
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

    BrokerTest: stock: HK.00700 data: [        code  name  bid_broker_id bid_broker_name  bid_broker_pos order_id order_volume\
    0   HK.00700  腾讯控股           5338          J.P.摩根               1      N/A          N/A\
    ..       ...   ...            ...             ...             ...      ...          ...\
    36  HK.00700  腾讯控股           8305  富途证券国际(香港)有限公司               4      N/A          N/A\
    \
    [37 rows x 7 columns],         code  name  ask_broker_id ask_broker_name  ask_broker_pos order_id order_volume\
    0   HK.00700  腾讯控股           1179  华泰金融控股(香港)有限公司               1      N/A          N/A\
    ..       ...   ...            ...             ...             ...      ...          ...\
    39  HK.00700  腾讯控股           6996      中国投资信息有限公司               1      N/A          N/A\
    \
    [40 rows x 7 columns]] 
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

[#](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html#2655-2)
 Qot\_UpdateBroker.proto
-----------------------------------------------------------------------------------------------------

*   **介绍**
    
    实时经纪队列回调，异步处理已订阅股票的实时经纪队列推送。
    
*   **参数**
    

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
16  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   经纪队列结构参见 [Broker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4135)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3015
    

`virtual void OnReply_UpdateBroker(MMAPI_Conn client, QotUpdateBroker.Response rsp);`

*   **介绍**

实时经纪队列回调，异步处理已订阅股票的实时经纪队列推送。

*   **参数**

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
16  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   经纪队列结构参见 [Broker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4135)
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
                    .AddSubTypeList((int)QotCommon.SubType.SubType_Broker)
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
        
        public void OnReply_UpdateBroker(MMAPI_Conn client, uint nSerialNo, QotUpdateBroker.Response rsp)
        {
            Console.Write("Push: UpdateBroker: {0}\n", nSerialNo);
            Console.Write("id: {0} , name: {1}\n", rsp.S2C.BrokerAskListList[0].Id, rsp.S2C.BrokerAskListList[0].Name);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825611828715002257
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Push: UpdateBroker: 1
    id: 696 , name: 富途证券国际(香港)有限公司
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

`void onPush_UpdateBroker(MMAPI_Conn client, QotUpdateBroker.Response rsp);`

*   **介绍**

实时经纪队列回调，异步处理已订阅股票的实时经纪队列推送。

*   **参数**

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
16  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   经纪队列结构参见 [Broker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4135)
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
                    .addSubTypeList(QotCommon.SubType.SubType_Broker_VALUE)
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
        public void onPush_UpdateBroker(MMAPI_Conn client, QotUpdateBroker.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotUpdateBroker failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotUpdateBroker: %s\n", json);
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

*   **Output**

    Receive QotUpdateBroker: {
      "retType": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "brokerAskList": [{\
          "id": "1836",\
          "name": "极讯亚太有限公司",\
          "pos": 1\
        }, ... {\
          "id": "1799",\
          "name": "耀才证券国际(香港)有限公司",\
          "pos": 1\
        }],
        "brokerBidList": [{\
          "id": "8304",\
          "name": "富途证券国际(香港)有限公司",\
          "pos": 1\
        }, ... {\
          "id": "2246",\
          "name": "富途证券国际(香港)有限公司",\
          "pos": 2\
        }]
      }
    }
    Receive QotUpdateBroker: {
      "retType": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "brokerAskList": [{\
          "id": "1836",\
          "name": "极讯亚太有限公司",\
          "pos": 1\
        }, ... {\
          "id": "1799",\
          "name": "耀才证券国际(香港)有限公司",\
          "pos": 1\
        }],
        "brokerBidList": [{\
          "id": "8304",\
          "name": "富途证券国际(香港)有限公司",\
          "pos": 1\
        }, ... {\
          "id": "6998",\
          "name": "中国投资信息有限公司",\
          "pos": 2\
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
52  
53  
54  

`virtual void OnPush_UpdateBroker(const Qot_UpdateBroker::Response &stRsp) = 0;`

*   **介绍**
    
    实时经纪队列回调，异步处理已订阅股票的实时经纪队列推送。
    
*   **参数**
    

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
16  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   经纪队列结构参见 [Broker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4135)
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
                if (stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "Sub Failed" << endl;
                    return;
                }
            }
    	}
    
    	virtual void OnPush_UpdateBroker(const Qot_UpdateBroker::Response &stRsp) {
    		cout << "OnPush_UpdateBroker: " << endl;
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
    OnPush_UpdateBroker: 
    {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "brokerAskList": [\
       {\
        "id": "6699",\
        "name": "盈透证券香港有限公司",\
        "pos": 1\
       },\
    ...\
       {\
        "id": "8303",\
        "name": "富途证券国际(香港)有限公司",\
        "pos": 1\
       }\
      ],
      "brokerBidList": [\
       {\
        "id": "5199",\
        "name": "国泰君安证券(香港)有限公司",\
        "pos": 1\
       },\
    ...\
       {\
        "id": "8301",\
        "name": "富途证券国际(香港)有限公司",\
        "pos": 2\
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

`OnPush(cmd,res)`

*   **介绍**
    
    实时经纪队列回调，异步处理已订阅股票的实时经纪队列推送。
    
*   **参数**
    

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
16  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   经纪队列结构参见 [Broker](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4135)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    
    function QotUpdateBroker(){
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
                    subTypeList: [ SubType.SubType_Broker ], // 订阅实时经纪队列类型
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
            if(mmCmdID.QotUpdateBroker.cmd == cmd){ // 实时经纪队列推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    console.log("BrokerTest", JSON.stringify(s2c));
                } else {
                    console.log("BrokerTest: error")
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

    BrokerTest {"security":{"market":1,"code":"00700"},"brokerAskList":[{"id":"6429","name":"中信証券经纪(香港)有限公司","pos":1},{"id":"4973","name":"法国兴业证券(香港)有限公司","pos":1},{"id":"1175","name":"华泰金融控股(香港)有限公司","pos":2},{"id":"6389","name":"摩根士丹利","pos":2},{"id":"230","name":"立桥证券有限公司","pos":2},{"id":"9025","name":"瑞银","pos":2},{"id":"4098","name":"瑞士信贷证券(香港)有限公司","pos":2},{"id":"8734","name":"汇丰证券经纪(亚洲)有限公司","pos":2},{"id":"8308","name":"富途证券国际(香港)有限公司","pos":2},{"id":"3439","name":"高盛(亚洲)证券有限公司","pos":2},{"id":"8578","name":"汇丰证券经纪(亚洲)有限公司","pos":2},{"id":"3439","name":"高盛(亚洲)证券有限公司","pos":2},{"id":"3439","name":"高盛(亚洲)证券有限公司","pos":2},{"id":"3439","name":"高盛(亚洲)证券有限公司","pos":2},{"id":"8564","name":"荷银","pos":2},{"id":"8565","name":"荷银","pos":2},{"id":"8464","name":"富途证券国际(香港)有限公司","pos":3},{"id":"1174","name":"华泰金融控股(香港)有限公司","pos":3},{"id":"8578","name":"汇丰证券经纪(亚洲)有限公司","pos":3},{"id":"5666","name":"华侨永亨証券有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":4},{"id":"4098","name":"瑞士信贷证券(香港)有限公司","pos":4},{"id":"4098","name":"瑞士信贷证券(香港)有限公司","pos":4},{"id":"4098","name":"瑞士信贷证券(香港)有限公司","pos":4},{"id":"6998","name":"中国投资信息有限公司","pos":4},{"id":"1292","name":"招银国际证券有限公司","pos":4},{"id":"1177","name":"华泰金融控股(香港)有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":4},{"id":"1175","name":"华泰金融控股(香港)有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":4},{"id":"6699","name":"盈透证券香港有限公司","pos":5},{"id":"6999","name":"中国投资信息有限公司","pos":5},{"id":"2310","name":"恒生证券有限公司","pos":5}],"brokerBidList":[{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"8578","name":"汇丰证券经纪(亚洲)有限公司","pos":1},{"id":"6998","name":"中国投资信息有限公司","pos":1},{"id":"2847","name":"麦格理资本股份有限公司","pos":1},{"id":"6997","name":"中国投资信息有限公司","pos":1},{"id":"2847","name":"麦格理资本股份有限公司","pos":1},{"id":"6699","name":"盈透证券香港有限公司","pos":1},{"id":"2974","name":"星展唯高达香港有限公司","pos":1},{"id":"2847","name":"麦格理资本股份有限公司","pos":1},{"id":"1292","name":"招银国际证券有限公司","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"8463","name":"富途证券国际(香港)有限公司","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"5999","name":"中国创盈市场服务有限公司","pos":1},{"id":"5666","name":"华侨永亨証券有限公司","pos":1},{"id":"2847","name":"麦格理资本股份有限公司","pos":1},{"id":"5999","name":"中国创盈市场服务有限公司","pos":1},{"id":"5368","name":"星展唯高达香港有限公司","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"1799","name":"耀才证券国际(香港)有限公司","pos":1},{"id":"690","name":"一通投资者有限公司","pos":1},{"id":"2847","name":"麦格理资本股份有限公司","pos":1},{"id":"229","name":"摩根士丹利","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"8467","name":"富途证券国际(香港)有限公司","pos":1},{"id":"6996","name":"中国投资信息有限公司","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"8574","name":"汇丰证券经纪(亚洲)有限公司","pos":1},{"id":"3643","name":"美林","pos":1},{"id":"6721","name":"宝生证券及期货有限公司","pos":1},{"id":"6727","name":"宝生证券及期货有限公司","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"5999","name":"中国创盈市场服务有限公司","pos":1},{"id":"9139","name":"实德证券有限公司","pos":1},{"id":"2026","name":"东亚证券有限公司","pos":1},{"id":"1978","name":"哈富证券有限公司","pos":1},{"id":"2846","name":"麦格理资本股份有限公司","pos":1},{"id":"1799","name":"耀才证券国际(香港)有限公司","pos":1},{"id":"2245","name":"富途证券国际(香港)有限公司","pos":1}]}
    BrokerTest { ... }
     ...
     ...
    stop
    

1  
2  
3  
4  
5  

提示

*   此接口提供了持续获取推送数据的功能，如需一次性获取实时数据，请参考 [获取实时经纪队列](https://openapi.futunn.com/futu-api-doc/quote/get-broker.html)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)
    
*   港股 LV1 权限下，不支持获取经纪队列数据

← [实时逐笔回调](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html) [获取快照](https://openapi.futunn.com/futu-api-doc/quote/get-market-snapshot.html)
 →