[#](./quote_get-order-book.md#7798)
 获取实时摆盘
===================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_order_book(code, num=10)`

*   **介绍**
    
    获取已订阅股票的实时摆盘，必须要先订阅。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票代码 |
    | name | str | 股票名称 |
    | num | int | 请求摆盘档数<br>(ℹ️ 摆盘档数获取上限请参见 [摆盘档数明细](./qa_quote.md#5336)) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | dict | 当 ret == RET\_OK，返回摆盘数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   摆盘数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | svr\_recv\_time\_bid | str | 富途服务器从交易所收到买盘数据的时间<br>(ℹ️ 部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据) |
        | svr\_recv\_time\_ask | str | 富途服务器从交易所收到卖盘数据的时间<br>(ℹ️ 部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据) |
        | Bid | list | 每个元祖包含如下信息：委托价格，委托数量，委托订单数，委托订单明细<br>(ℹ️ 委托订单明细)<br><br>*   明细内容：交易所订单 ID，单笔委托数量<br>*   港股 SF 权限下最多支持 1000 笔委托订单明细；  <br>    其余行情权限不支持获取此类数据 |
        | Ask | list | 每个元祖包含如下信息：委托价格，委托数量，委托订单数，委托订单明细<br>(ℹ️ 委托订单明细)<br><br>*   明细内容：交易所订单 ID，单笔委托数量<br>*   港股 SF 权限下最多支持 1000 笔委托订单明细；  <br>    其余行情权限不支持获取此类数据 |
        
        其中，Bid 和 Ask 字段的结构如下：
        
             'Bid': [ (bid_price1, bid_volume1, order_num, {'orderid1': order_volume1, 'orderid2': order_volume2, …… }), (bid_price2, bid_volume2, order_num,  {'orderid1': order_volume1, 'orderid2': order_volume2, …… }),…]
             'Ask': [ (ask_price1, ask_volume1，order_num, {'orderid1': order_volume1, 'orderid2': order_volume2, …… }), (ask_price2, ask_volume2, order_num, {'orderid1': order_volume1, 'orderid2': order_volume2, …… }),…] 
            
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret_sub = quote_ctx.subscribe(['US.AAPL'], [SubType.ORDER_BOOK], subscribe_push=False)[0]
    # 先订阅买卖摆盘类型。订阅成功后 OpenD 将持续收到服务器的推送，False 代表暂时不需要推送给脚本
    if ret_sub == RET_OK:  # 订阅成功
        ret, data = quote_ctx.get_order_book('US.AAPL', num=3)  # 获取一次 3 档实时摆盘数据
        if ret == RET_OK:
            print(data)
        else:
            print('error:', data)
    else:
        print('subscription failed')
    quote_ctx.close()  # 关闭当条连接，OpenD 会在 1 分钟后自动取消相应股票相应类型的订阅
    

1  
2  
3  
4  
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

    {'code': 'US.AAPL', 'name': '苹果', 'svr_recv_time_bid': '2025-04-07 05:39:20.352', 'svr_recv_time_ask': '2025-04-07 05:39:20.352', 'Bid': [(181.17, 227, 2, {}), (181.15, 2, 2, {}), (181.12, 100, 1, {})], 'Ask': [(181.71, 200, 1, {}), (181.79, 9, 1, {}), (181.9, 616, 3, {})]}
    

1  

[#](./quote_get-order-book.md#2297)
 Qot\_GetOrderBook.proto
----------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取已订阅股票的实时摆盘，必须要先订阅。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 num = 2; //请求的摆盘个数
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

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 8; //股票名称
    	repeated Qot_Common.OrderBook orderBookAskList = 2; //卖盘
    	repeated Qot_Common.OrderBook orderBookBidList = 3; //买盘
    	optional string svrRecvTimeBid = 4; // 富途服务器从交易所收到数据的时间(for bid)部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据。该字段暂时只支持港股。
    	optional double svrRecvTimeBidTimestamp = 5; // 富途服务器从交易所收到数据的时间戳(for bid)
    	optional string svrRecvTimeAsk = 6; // 富途服务器从交易所收到数据的时间(for ask)
    	optional double svrRecvTimeAskTimestamp = 7; // 富途服务器从交易所收到数据的时间戳(for ask)
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   买卖盘结构参见 [OrderBook](./quote_quote.md#6364)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3012
    

`uint GetOrderBook(QotGetOrderBook.Request req);`  
`virtual void OnReply_GetOrderBook(FTAPI_Conn client, uint nSerialNo, QotGetOrderBook.Response rsp);`

*   **介绍**

获取已订阅股票的实时摆盘，必须要先订阅。

*   **参数**

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 num = 2; //请求的摆盘个数
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

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 8; //股票名称
    	repeated Qot_Common.OrderBook orderBookAskList = 2; //卖盘
    	repeated Qot_Common.OrderBook orderBookBidList = 3; //买盘
    	optional string svrRecvTimeBid = 4; // 富途服务器从交易所收到数据的时间(for bid)部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据。该字段暂时只支持港股。
    	optional double svrRecvTimeBidTimestamp = 5; // 富途服务器从交易所收到数据的时间戳(for bid)
    	optional string svrRecvTimeAsk = 6; // 富途服务器从交易所收到数据的时间(for ask)
    	optional double svrRecvTimeAskTimestamp = 7; // 富途服务器从交易所收到数据的时间戳(for ask)
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   买卖盘结构参见 [OrderBook](./quote_quote.md#6364)
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
                    .AddSubTypeList((int)QotCommon.SubType.SubType_OrderBook)
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
            QotGetOrderBook.C2S c2s = QotGetOrderBook.C2S.CreateBuilder()
                    .SetSecurity(sec)
                    .SetNum(10)
                    .Build();
            QotGetOrderBook.Request req = QotGetOrderBook.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetOrderBook(req);
            Console.Write("Send QotGetOrderBook: {0}\n", seqNo);
        }
    
        public void OnReply_GetOrderBook(FTAPI_Conn client, uint nSerialNo, QotGetOrderBook.Response rsp)
        {
            Console.Write("Reply: QotGetOrderBook: {0}\n", nSerialNo);
            Console.Write("price: {0}\n", rsp.S2C.OrderBookAskListList[0].Price);
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
75  

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825669765086569309
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Send QotGetOrderBook: 4
    Reply: QotGetOrderBook: 4
    price: 457
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

`int getOrderBook(QotGetOrderBook.Request req);`  
`void onReply_GetOrderBook(FTAPI_Conn client, int nSerialNo, QotGetOrderBook.Response rsp);`

*   **介绍**

获取已订阅股票的实时摆盘，必须要先订阅。

*   **参数**

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 num = 2; //请求的摆盘个数
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

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 8; //股票名称
    	repeated Qot_Common.OrderBook orderBookAskList = 2; //卖盘
    	repeated Qot_Common.OrderBook orderBookBidList = 3; //买盘
    	optional string svrRecvTimeBid = 4; // 富途服务器从交易所收到数据的时间(for bid)部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据。该字段暂时只支持港股。
    	optional double svrRecvTimeBidTimestamp = 5; // 富途服务器从交易所收到数据的时间戳(for bid)
    	optional string svrRecvTimeAsk = 6; // 富途服务器从交易所收到数据的时间(for ask)
    	optional double svrRecvTimeAskTimestamp = 7; // 富途服务器从交易所收到数据的时间戳(for ask)
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   买卖盘结构参见 [OrderBook](./quote_quote.md#6364)
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
                    .addSubTypeList(QotCommon.SubType.SubType_OrderBook_VALUE)
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
            QotGetOrderBook.C2S c2s = QotGetOrderBook.C2S.newBuilder()
                    .setSecurity(sec)
                    .setNum(10)
                    .build();
            QotGetOrderBook.Request req = QotGetOrderBook.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getOrderBook(req);
            System.out.printf("Send QotGetOrderBook: %d\n", seqNo);
        }
    
        @Override
        public void onReply_GetOrderBook(FTAPI_Conn client, int nSerialNo, QotGetOrderBook.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetOrderBook failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetOrderBook: %s\n", json);
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
88  

*   **Output**

    Send QotGetOrderBook: 3
    Receive QotGetOrderBook: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "orderBookAskList": [{\
          "price": 583.5,\
          "volume": "142300",\
          "orederCount": 25\
        }, {\
          "price": 584.0,\
          "volume": "56600",\
          "orederCount": 45\
        }, ... {\
          "price": 578.5,\
          "volume": "61300",\
          "orederCount": 27\
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

`Futu::u32_t GetOrderBook(const Qot_GetOrderBook::Request &stReq);`  
`virtual void OnReply_GetOrderBook(Futu::u32_t nSerialNo, const Qot_GetOrderBook::Response &stRsp) = 0;`

*   **介绍**
    
    获取已订阅股票的实时摆盘，必须要先订阅。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 num = 2; //请求的摆盘个数
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

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 8; //股票名称
    	repeated Qot_Common.OrderBook orderBookAskList = 2; //卖盘
    	repeated Qot_Common.OrderBook orderBookBidList = 3; //买盘
    	optional string svrRecvTimeBid = 4; // 富途服务器从交易所收到数据的时间(for bid)部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据。该字段暂时只支持港股。
    	optional double svrRecvTimeBidTimestamp = 5; // 富途服务器从交易所收到数据的时间戳(for bid)
    	optional string svrRecvTimeAsk = 6; // 富途服务器从交易所收到数据的时间(for ask)
    	optional double svrRecvTimeAskTimestamp = 7; // 富途服务器从交易所收到数据的时间戳(for ask)
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   买卖盘结构参见 [OrderBook](./quote_quote.md#6364)
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
    		c2s->add_subtypelist(Qot_Common::SubType::SubType_OrderBook);
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
                Qot_GetOrderBook::Request req;
                Qot_GetOrderBook::C2S *c2s = req.mutable_c2s();
                Qot_Common::Security *sec = c2s->mutable_security();
                sec->set_code("00700");
                sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
                c2s->set_num(5);
    
                m_GetOrderBookSerialNo = m_pQotApi->GetOrderBook(req);
                cout << "Request GetOrderBook SerialNo: " << m_GetOrderBookSerialNo << endl;
            }
    	}
    
    	virtual void OnReply_GetOrderBook(Futu::u32_t nSerialNo, const Qot_GetOrderBook::Response &stRsp){
            if(nSerialNo == m_GetOrderBookSerialNo)
            {
                cout << "OnReply_GetOrderBook SerialNo: " << nSerialNo << endl;
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
        Futu::u32_t m_GetOrderBookSerialNo;
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
100  

*   **Output**

    connect
    Request Sub SerialNo: 3
    OnReply_Sub SerialNo: 3
    Request GetOrderBook SerialNo: 4
    OnReply_GetOrderBook SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "orderBookAskList": [\
       {\
        "price": 604.5,\
        "volume": "31100",\
        "orederCount": 27\
       },\
       {\
        "price": 605,\
        "volume": "47600",\
        "orederCount": 77\
       },\
       {\
        "price": 605.5,\
        "volume": "51600",\
        "orederCount": 36\
       },\
       {\
        "price": 606,\
        "volume": "49000",\
        "orederCount": 125\
       },\
       {\
        "price": 606.5,\
        "volume": "16900",\
        "orederCount": 33\
       }\
      ],
      "orderBookBidList": [\
       {\
        "price": 604,\
        "volume": "9100",\
        "orederCount": 12\
       },\
       {\
        "price": 603.5,\
        "volume": "16400",\
        "orederCount": 24\
       },\
       {\
        "price": 603,\
        "volume": "16000",\
        "orederCount": 11\
       },\
       {\
        "price": 602.5,\
        "volume": "33900",\
        "orederCount": 8\
       },\
       {\
        "price": 602,\
        "volume": "25500",\
        "orederCount": 15\
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

`GetOrderBook(req);`

*   **介绍**
    
    获取已订阅股票的实时摆盘，必须要先订阅。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 num = 2; //请求的摆盘个数
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

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 8; //股票名称
    	repeated Qot_Common.OrderBook orderBookAskList = 2; //卖盘
    	repeated Qot_Common.OrderBook orderBookBidList = 3; //买盘
    	optional string svrRecvTimeBid = 4; // 富途服务器从交易所收到数据的时间(for bid)部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据。该字段暂时只支持港股。
    	optional double svrRecvTimeBidTimestamp = 5; // 富途服务器从交易所收到数据的时间戳(for bid)
    	optional string svrRecvTimeAsk = 6; // 富途服务器从交易所收到数据的时间(for ask)
    	optional double svrRecvTimeAskTimestamp = 7; // 富途服务器从交易所收到数据的时间戳(for ask)
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   买卖盘结构参见 [OrderBook](./quote_quote.md#6364)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetOrderBook(){
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
                    subTypeList: [ SubType.SubType_OrderBook ], // 订阅实时摆盘类型
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
                            num: 2,
                        },
                    };
                    websocket.GetOrderBook(req)
                    .then((res) => {
                        let { errCode, retMsg, retType,s2c } = res
                        console.log("OrderBook: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                        if(retType == RetType.RetType_Succeed){
                            let data = beautify(JSON.stringify(s2c), {
                                indent_size: 2,
                                space_in_empty_paren: true,
                            });
                            console.log(data);
                        }
                    })
                    .catch((error) => {
                        console.log("getorderbook error:", error);
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

    OrderBook: errCode 0, retMsg , retType 0
    {
      "security": {
        "market": 1,
        "code": "00700"
      },
      "orderBookAskList": [{\
        "price": 480.4,\
        "volume": "700",\
        "orederCount": 2\
      }, {\
        "price": 480.6,\
        "volume": "385900",\
        "orederCount": 14\
      }],
      "orderBookBidList": [{\
        "price": 480,\
        "volume": "55300",\
        "orederCount": 39\
      }, {\
        "price": 479.8,\
        "volume": "92400",\
        "orederCount": 61\
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

接口限制

*   富途服务器从交易所收到数据的时间字段，仅支持A股正股、港股正股、ETFs、窝轮、牛熊，且仅开盘时间才有此数据。
*   富途服务器从交易所收到数据的时间字段，部分情况下接收时间可能为零，例如：服务器重启或第一次推送的缓存数据。

提示

*   此接口提供了一次性获取实时数据的功能，如需持续获取推送数据，请参考 [实时摆盘回调](./quote_update-order-book.md)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](./qa_quote.md#2692)
    
*   美股市场，会返回当前交易时段的实时摆盘数据，无需设置时段。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_order_book(code, num=10)`

*   **介绍**
    
    获取已订阅股票的实时摆盘，必须要先订阅。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票代码 |
    | name | str | 股票名称 |
    | num | int | 请求摆盘档数<br>(ℹ️ 摆盘档数获取上限请参见 [摆盘档数明细](./qa_quote.md#5336)) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | dict | 当 ret == RET\_OK，返回摆盘数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   摆盘数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | svr\_recv\_time\_bid | str | 富途服务器从交易所收到买盘数据的时间<br>(ℹ️ 部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据) |
        | svr\_recv\_time\_ask | str | 富途服务器从交易所收到卖盘数据的时间<br>(ℹ️ 部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据) |
        | Bid | list | 每个元祖包含如下信息：委托价格，委托数量，委托订单数，委托订单明细<br>(ℹ️ 委托订单明细)<br><br>*   明细内容：交易所订单 ID，单笔委托数量<br>*   港股 SF 权限下最多支持 1000 笔委托订单明细；  <br>    其余行情权限不支持获取此类数据 |
        | Ask | list | 每个元祖包含如下信息：委托价格，委托数量，委托订单数，委托订单明细<br>(ℹ️ 委托订单明细)<br><br>*   明细内容：交易所订单 ID，单笔委托数量<br>*   港股 SF 权限下最多支持 1000 笔委托订单明细；  <br>    其余行情权限不支持获取此类数据 |
        
        其中，Bid 和 Ask 字段的结构如下：
        
             'Bid': [ (bid_price1, bid_volume1, order_num, {'orderid1': order_volume1, 'orderid2': order_volume2, …… }), (bid_price2, bid_volume2, order_num,  {'orderid1': order_volume1, 'orderid2': order_volume2, …… }),…]
             'Ask': [ (ask_price1, ask_volume1，order_num, {'orderid1': order_volume1, 'orderid2': order_volume2, …… }), (ask_price2, ask_volume2, order_num, {'orderid1': order_volume1, 'orderid2': order_volume2, …… }),…] 
            
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret_sub = quote_ctx.subscribe(['US.AAPL'], [SubType.ORDER_BOOK], subscribe_push=False)[0]
    # 先订阅买卖摆盘类型。订阅成功后 OpenD 将持续收到服务器的推送，False 代表暂时不需要推送给脚本
    if ret_sub == RET_OK:  # 订阅成功
        ret, data = quote_ctx.get_order_book('US.AAPL', num=3)  # 获取一次 3 档实时摆盘数据
        if ret == RET_OK:
            print(data)
        else:
            print('error:', data)
    else:
        print('subscription failed')
    quote_ctx.close()  # 关闭当条连接，OpenD 会在 1 分钟后自动取消相应股票相应类型的订阅
    

1  
2  
3  
4  
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

    {'code': 'US.AAPL', 'name': '苹果', 'svr_recv_time_bid': '2025-04-07 05:39:20.352', 'svr_recv_time_ask': '2025-04-07 05:39:20.352', 'Bid': [(181.17, 227, 2, {}), (181.15, 2, 2, {}), (181.12, 100, 1, {})], 'Ask': [(181.71, 200, 1, {}), (181.79, 9, 1, {}), (181.9, 616, 3, {})]}
    

1  

[#](./quote_get-order-book.md#2297-2)
 Qot\_GetOrderBook.proto
------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取已订阅股票的实时摆盘，必须要先订阅。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 num = 2; //请求的摆盘个数
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

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 8; //股票名称
    	repeated Qot_Common.OrderBook orderBookAskList = 2; //卖盘
    	repeated Qot_Common.OrderBook orderBookBidList = 3; //买盘
    	optional string svrRecvTimeBid = 4; // moomoo 服务器从交易所收到数据的时间(for bid)部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据。该字段暂时只支持港股。
    	optional double svrRecvTimeBidTimestamp = 5; // moomoo 服务器从交易所收到数据的时间戳(for bid)
    	optional string svrRecvTimeAsk = 6; // moomoo 服务器从交易所收到数据的时间(for ask)
    	optional double svrRecvTimeAskTimestamp = 7; // moomoo 服务器从交易所收到数据的时间戳(for ask)
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   买卖盘结构参见 [OrderBook](./quote_quote.md#6364)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3012
    

`uint GetOrderBook(QotGetOrderBook.Request req);`  
`virtual void OnReply_GetOrderBook(MMAPI_Conn client, uint nSerialNo, QotGetOrderBook.Response rsp);`

*   **介绍**

获取已订阅股票的实时摆盘，必须要先订阅。

*   **参数**

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 num = 2; //请求的摆盘个数
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

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 8; //股票名称
    	repeated Qot_Common.OrderBook orderBookAskList = 2; //卖盘
    	repeated Qot_Common.OrderBook orderBookBidList = 3; //买盘
    	optional string svrRecvTimeBid = 4; // moomoo服务器从交易所收到数据的时间(for bid)部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据。该字段暂时只支持港股。
    	optional double svrRecvTimeBidTimestamp = 5; // moomoo服务器从交易所收到数据的时间戳(for bid)
    	optional string svrRecvTimeAsk = 6; // moomoo服务器从交易所收到数据的时间(for ask)
    	optional double svrRecvTimeAskTimestamp = 7; // moomoo服务器从交易所收到数据的时间戳(for ask)
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   买卖盘结构参见 [OrderBook](./quote_quote.md#6364)
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
                    .AddSubTypeList((int)QotCommon.SubType.SubType_OrderBook)
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
            QotGetOrderBook.C2S c2s = QotGetOrderBook.C2S.CreateBuilder()
                    .SetSecurity(sec)
                    .SetNum(10)
                    .Build();
            QotGetOrderBook.Request req = QotGetOrderBook.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetOrderBook(req);
            Console.Write("Send QotGetOrderBook: {0}\n", seqNo);
        }
    
        public void OnReply_GetOrderBook(MMAPI_Conn client, uint nSerialNo, QotGetOrderBook.Response rsp)
        {
            Console.Write("Reply: QotGetOrderBook: {0}\n", nSerialNo);
            Console.Write("price: {0}\n", rsp.S2C.OrderBookAskListList[0].Price);
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
75  

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825669765086569309
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Send QotGetOrderBook: 4
    Reply: QotGetOrderBook: 4
    price: 457
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

`int getOrderBook(QotGetOrderBook.Request req);`  
`void onReply_GetOrderBook(MMAPI_Conn client, int nSerialNo, QotGetOrderBook.Response rsp);`

*   **介绍**

获取已订阅股票的实时摆盘，必须要先订阅。

*   **参数**

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 num = 2; //请求的摆盘个数
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

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 8; //股票名称
    	repeated Qot_Common.OrderBook orderBookAskList = 2; //卖盘
    	repeated Qot_Common.OrderBook orderBookBidList = 3; //买盘
    	optional string svrRecvTimeBid = 4; // moomoo 服务器从交易所收到数据的时间(for bid)部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据。该字段暂时只支持港股。
    	optional double svrRecvTimeBidTimestamp = 5; // moomoo 服务器从交易所收到数据的时间戳(for bid)
    	optional string svrRecvTimeAsk = 6; // moomoo 服务器从交易所收到数据的时间(for ask)
    	optional double svrRecvTimeAskTimestamp = 7; // moomoo服务器从交易所收到数据的时间戳(for ask)
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   买卖盘结构参见 [OrderBook](./quote_quote.md#6364)
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
                    .addSubTypeList(QotCommon.SubType.SubType_OrderBook_VALUE)
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
            QotGetOrderBook.C2S c2s = QotGetOrderBook.C2S.newBuilder()
                    .setSecurity(sec)
                    .setNum(10)
                    .build();
            QotGetOrderBook.Request req = QotGetOrderBook.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getOrderBook(req);
            System.out.printf("Send QotGetOrderBook: %d\n", seqNo);
        }
    
        @Override
        public void onReply_GetOrderBook(MMAPI_Conn client, int nSerialNo, QotGetOrderBook.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetOrderBook failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetOrderBook: %s\n", json);
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
88  

*   **Output**

    Send QotGetOrderBook: 3
    Receive QotGetOrderBook: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "orderBookAskList": [{\
          "price": 583.5,\
          "volume": "142300",\
          "orederCount": 25\
        }, {\
          "price": 584.0,\
          "volume": "56600",\
          "orederCount": 45\
        }, ... {\
          "price": 578.5,\
          "volume": "61300",\
          "orederCount": 27\
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

`moomoo::u32_t GetOrderBook(const Qot_GetOrderBook::Request &stReq);`  
`virtual void OnReply_GetOrderBook(moomoo::u32_t nSerialNo, const Qot_GetOrderBook::Response &stRsp) = 0;`

*   **介绍**
    
    获取已订阅股票的实时摆盘，必须要先订阅。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 num = 2; //请求的摆盘个数
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

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 8; //股票名称
    	repeated Qot_Common.OrderBook orderBookAskList = 2; //卖盘
    	repeated Qot_Common.OrderBook orderBookBidList = 3; //买盘
    	optional string svrRecvTimeBid = 4; // moomoo 服务器从交易所收到数据的时间(for bid)部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据。该字段暂时只支持港股。
    	optional double svrRecvTimeBidTimestamp = 5; // moomoo 服务器从交易所收到数据的时间戳(for bid)
    	optional string svrRecvTimeAsk = 6; // moomoo 服务器从交易所收到数据的时间(for ask)
    	optional double svrRecvTimeAskTimestamp = 7; // moomoo 服务器从交易所收到数据的时间戳(for ask)
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   买卖盘结构参见 [OrderBook](./quote_quote.md#6364)
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
    		c2s->add_subtypelist(Qot_Common::SubType::SubType_OrderBook);
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
                Qot_GetOrderBook::Request req;
                Qot_GetOrderBook::C2S *c2s = req.mutable_c2s();
                Qot_Common::Security *sec = c2s->mutable_security();
                sec->set_code("00700");
                sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
                c2s->set_num(5);
    
                m_GetOrderBookSerialNo = m_pQotApi->GetOrderBook(req);
                cout << "Request GetOrderBook SerialNo: " << m_GetOrderBookSerialNo << endl;
            }
    	}
    
    	virtual void OnReply_GetOrderBook(moomoo::u32_t nSerialNo, const Qot_GetOrderBook::Response &stRsp){
            if(nSerialNo == m_GetOrderBookSerialNo)
            {
                cout << "OnReply_GetOrderBook SerialNo: " << nSerialNo << endl;
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
        moomoo::u32_t m_GetOrderBookSerialNo;
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
100  

*   **Output**

    connect
    Request Sub SerialNo: 3
    OnReply_Sub SerialNo: 3
    Request GetOrderBook SerialNo: 4
    OnReply_GetOrderBook SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "orderBookAskList": [\
       {\
        "price": 604.5,\
        "volume": "31100",\
        "orederCount": 27\
       },\
       {\
        "price": 605,\
        "volume": "47600",\
        "orederCount": 77\
       },\
       {\
        "price": 605.5,\
        "volume": "51600",\
        "orederCount": 36\
       },\
       {\
        "price": 606,\
        "volume": "49000",\
        "orederCount": 125\
       },\
       {\
        "price": 606.5,\
        "volume": "16900",\
        "orederCount": 33\
       }\
      ],
      "orderBookBidList": [\
       {\
        "price": 604,\
        "volume": "9100",\
        "orederCount": 12\
       },\
       {\
        "price": 603.5,\
        "volume": "16400",\
        "orederCount": 24\
       },\
       {\
        "price": 603,\
        "volume": "16000",\
        "orederCount": 11\
       },\
       {\
        "price": 602.5,\
        "volume": "33900",\
        "orederCount": 8\
       },\
       {\
        "price": 602,\
        "volume": "25500",\
        "orederCount": 15\
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

`GetOrderBook(req);`

*   **介绍**
    
    获取已订阅股票的实时摆盘，必须要先订阅。
    
*   **参数**
    

    message C2S
    {
    	required Qot_Common.Security security = 1; //股票
    	required int32 num = 2; //请求的摆盘个数
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

*   **返回**

    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 8; //股票名称
    	repeated Qot_Common.OrderBook orderBookAskList = 2; //卖盘
    	repeated Qot_Common.OrderBook orderBookBidList = 3; //买盘
    	optional string svrRecvTimeBid = 4; // moomoo 服务器从交易所收到数据的时间(for bid)部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据。该字段暂时只支持港股。
    	optional double svrRecvTimeBidTimestamp = 5; // moomoo 服务器从交易所收到数据的时间戳(for bid)
    	optional string svrRecvTimeAsk = 6; // moomoo 服务器从交易所收到数据的时间(for ask)
    	optional double svrRecvTimeAskTimestamp = 7; // moomoo 服务器从交易所收到数据的时间戳(for ask)
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   买卖盘结构参见 [OrderBook](./quote_quote.md#6364)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetOrderBook(){
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
                    subTypeList: [ SubType.SubType_OrderBook ], // 订阅实时摆盘类型
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
                            num: 2,
                        },
                    };
                    websocket.GetOrderBook(req)
                    .then((res) => {
                        let { errCode, retMsg, retType,s2c } = res
                        console.log("OrderBook: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                        if(retType == RetType.RetType_Succeed){
                            let data = beautify(JSON.stringify(s2c), {
                                indent_size: 2,
                                space_in_empty_paren: true,
                            });
                            console.log(data);
                        }
                    })
                    .catch((error) => {
                        console.log("getorderbook error:", error);
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

    OrderBook: errCode 0, retMsg , retType 0
    {
      "security": {
        "market": 1,
        "code": "00700"
      },
      "orderBookAskList": [{\
        "price": 480.4,\
        "volume": "700",\
        "orederCount": 2\
      }, {\
        "price": 480.6,\
        "volume": "385900",\
        "orederCount": 14\
      }],
      "orderBookBidList": [{\
        "price": 480,\
        "volume": "55300",\
        "orederCount": 39\
      }, {\
        "price": 479.8,\
        "volume": "92400",\
        "orederCount": 61\
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

接口限制

*   moomoo 服务器从交易所收到数据的时间字段，仅支持A股正股、港股正股、ETFs、窝轮、牛熊，且仅开盘时间才有此数据。
*   moomoo 服务器从交易所收到数据的时间字段，部分情况下接收时间可能为零，例如：服务器重启或第一次推送的缓存数据。

提示

*   此接口提供了一次性获取实时数据的功能，如需持续获取推送数据，请参考 [实时摆盘回调](./quote_update-order-book.md)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](./qa_quote.md#2692)
    
*   美股市场，会返回当前交易时段的实时摆盘数据，无需设置时段。

← [获取实时报价](./quote_get-stock-quote.md) [获取实时 K 线](./quote_get-kl.md)
 →

[获取实时摆盘](./quote_get-order-book.md)