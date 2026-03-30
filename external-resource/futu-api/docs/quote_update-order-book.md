[#](./quote_update-order-book.md#878)
 实时摆盘回调
=====================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`on_recv_rsp(self, rsp_pb)`

*   **介绍**
    
    实时摆盘回调，异步处理已订阅股票的实时摆盘推送。 在收到实时摆盘数据推送后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Qot\_UpdateOrderBook\_pb2.Response | 派生类中不需要直接处理该参数 |
    
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
    

    import time
    from futu import *
    class OrderBookTest(OrderBookHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, data = super(OrderBookTest,self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("OrderBookTest: error, msg: %s" % data)
                return RET_ERROR, data
            print("OrderBookTest ", data) # OrderBookTest 自己的处理逻辑
            return RET_OK, data
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = OrderBookTest()
    quote_ctx.set_handler(handler)  # 设置实时摆盘回调
    ret, data = quote_ctx.subscribe(['US.AAPL'], [SubType.ORDER_BOOK])  # 订阅买卖摆盘类型，OpenD 开始持续收到服务器的推送
    if ret == RET_OK:
        print(data)
    else:
        print('error:', data)
    time.sleep(15)  #  设置脚本接收 OpenD 的推送持续时间为15秒
    quote_ctx.close()  # 关闭当条连接，OpenD 会在1分钟后自动取消相应股票相应类型的订阅
    

1  
2  
3  
4  
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

*   **Output**

    OrderBookTest  {'code': 'US.AAPL', 'name': '苹果', 'svr_recv_time_bid': '', 'svr_recv_time_ask': '', 'Bid': [(179.77, 100, 1, {}), (179.68, 200, 1, {}), (179.65, 2, 2, {}), (179.64, 27, 1, {}), (179.6, 9, 2, {}), (179.58, 39, 2, {}), (179.5, 13, 4, {}), (179.48, 331, 2, {}), (179.4, 1002, 2, {}), (179.38, 330, 1, {}), (179.37, 2, 1, {}), (179.3, 47, 1, {}), (179.28, 330, 1, {}), (179.21, 2, 1, {}), (179.2, 1000, 1, {}), (179.18, 330, 1, {}), (179.17, 100, 1, {}), (179.16, 1, 1, {}), (179.13, 400, 1, {}), (179.1, 3000, 1, {}), (179.08, 330, 1, {}), (179.05, 125, 2, {}), (179.01, 17, 2, {}), (179.0, 81, 7, {})], 'Ask': [(179.95, 400, 2, {}), (180.0, 360, 2, {}), (180.05, 20, 1, {}), (180.1, 246, 4, {}), (180.18, 20, 1, {}), (180.2, 2030, 3, {}), (180.23, 20, 1, {}), (180.3, 23, 1, {}), (180.33, 15, 1, {}), (180.4, 2000, 2, {}), (180.49, 5, 1, {}), (180.59, 253, 1, {}), (180.6, 2000, 2, {}), (180.8, 2010, 3, {}), (181.0, 2018, 4, {}), (181.08, 1, 1, {}), (181.2, 1009, 2, {}), (181.3, 17, 3, {}), (181.4, 1, 1, {}), (181.5, 50, 1, {}), (181.79, 9, 1, {}), (181.9, 66, 2, {})]}
    

1  

[#](./quote_update-order-book.md#4825)
 Qot\_UpdateOrderBook.proto
----------------------------------------------------------------------------------------------------------

*   **介绍**
    
    实时摆盘回调，异步处理已订阅股票的实时摆盘推送。
    
*   **参数**
    

    
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
20  
21  

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   买卖盘结构参见 [OrderBook](./quote_quote.md#6364)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3013
    

`virtual void OnReply_UpdateOrderBook(FTAPI_Conn client, QotUpdateOrderBook.Response rsp);`

*   **介绍**

实时摆盘回调，异步处理已订阅股票的实时摆盘推送。

*   **参数**

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
20  

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
        
        public void OnReply_UpdateOrderBook(FTAPI_Conn client, uint nSerialNo, QotUpdateOrderBook.Response rsp) {
            Console.Write("Push: UpdateOrderBook: {0}\n", nSerialNo);
            Console.Write("price : {0}\n", rsp.S2C.OrderBookAskListList[0].Price);
            Console.Write("volume : {0}\n", rsp.S2C.OrderBookAskListList[0].Volume);        
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

    Qot onInitConnect: ret=0 desc= connID=6825335709542277011
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Push: UpdateOrderBook: 1
    price : 490
    volume : 3228700
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
10  

`void onPush_UpdateOrderBook(FTAPI_Conn client, QotUpdateOrderBook.Response rsp);`

*   **介绍**

实时摆盘回调，异步处理已订阅股票的实时摆盘推送。

*   **参数**

    
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
20  
21  

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
        public void onPush_UpdateOrderBook(FTAPI_Conn client, QotUpdateOrderBook.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotUpdateOrderBook failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotUpdateOrderBook: %s\n", json);
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

    Receive QotUpdateOrderBook: {
      "retType": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "orderBookAskList": [{\
          "price": 587.5,\
          "volume": "200",\
          "orederCount": 2\
        }, ... {\
          "price": 582.5,\
          "volume": "2600",\
          "orederCount": 13\
        }],
        "svrRecvTimeBid": "2021-06-25 10:07:32.841",
        "svrRecvTimeBidTimestamp": 1.624586852841E9,
        "svrRecvTimeAsk": "2021-06-25 10:07:32.841",
        "svrRecvTimeAskTimestamp": 1.624586852841E9
      }
    }
    Receive QotUpdateOrderBook: {
      "retType": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "orderBookAskList": [{\
          "price": 587.5,\
          "volume": "200",\
          "orederCount": 2\
        }, ... {\
          "price": 582.5,\
          "volume": "2600",\
          "orederCount": 13\
        }],
        "svrRecvTimeBid": "2021-06-25 10:07:32.941",
        "svrRecvTimeBidTimestamp": 1.624586852941E9,
        "svrRecvTimeAsk": "2021-06-25 10:07:32.941",
        "svrRecvTimeAskTimestamp": 1.624586852941E9
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

`virtual void OnPush_UpdateOrderBook(const Qot_UpdateOrderBook::Response &stRsp) = 0;`

*   **介绍**
    
    实时摆盘回调，异步处理已订阅股票的实时摆盘推送。
    
*   **参数**
    

    
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
20  
21  

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
                if (stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "Sub Failed" << endl;
                    return;
                }
            }
    	}
    
    	virtual void OnPush_UpdateOrderBook(const Qot_UpdateOrderBook::Response &stRsp) {
    		cout << "OnPush_UpdateOrderBook: " << endl;
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
    OnPush_UpdateOrderBook: 
    {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "orderBookAskList": [\
       {\
        "price": 605.5,\
        "volume": "51000",\
        "orederCount": 54\
       },\
    ...\
       {\
        "price": 610,\
        "volume": "70900",\
        "orederCount": 147\
       }\
      ],
      "orderBookBidList": [\
       {\
        "price": 605,\
        "volume": "14600",\
        "orederCount": 23\
       },\
    ...\
       {\
        "price": 600.5,\
        "volume": "19100",\
        "orederCount": 32\
       }\
      ],
      "svrRecvTimeBid": "2021-06-09 11:09:51.493",
      "svrRecvTimeBidTimestamp": 1623208191.493,
      "svrRecvTimeAsk": "2021-06-09 11:09:51.493",
      "svrRecvTimeAskTimestamp": 1623208191.493
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

`OnPush(cmd,res)`

*   **介绍**
    
    实时摆盘回调，异步处理已订阅股票的实时摆盘推送。
    
*   **参数**
    

    
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
20  
21  

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
    
    function UpdateOrderBook(){
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
                    subTypeList: [ SubType.SubType_OrderBook ], // 订阅实时摆盘类型
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
            if(ftCmdID.QotUpdateOrderBook.cmd == cmd){ // 实时摆盘推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    console.log("OrderBookTest", JSON.stringify(s2c));
                } else {
                    console.log("OrderBookTest: error")
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

    OrderBookTest {"security":{"market":1,"code":"00700"},"orderBookAskList":[{"price":480.4,"volume":"700","orederCount":2},{"price":480.6,"volume":"385900","orederCount":14},{"price":480.8,"volume":"4200","orederCount":3},{"price":481,"volume":"15000","orederCount":14},{"price":481.2,"volume":"4600","orederCount":5},{"price":481.4,"volume":"13400","orederCount":4},{"price":481.6,"volume":"1800","orederCount":6},{"price":481.8,"volume":"3000","orederCount":8},{"price":482,"volume":"23700","orederCount":25},{"price":482.2,"volume":"1200","orederCount":3}],"orderBookBidList":[{"price":480,"volume":"55300","orederCount":39},{"price":479.8,"volume":"92400","orederCount":61},{"price":479.6,"volume":"106100","orederCount":41},{"price":479.4,"volume":"85900","orederCount":48},{"price":479.2,"volume":"78100","orederCount":34},{"price":479,"volume":"50200","orederCount":150},{"price":478.8,"volume":"51300","orederCount":44},{"price":478.6,"volume":"92700","orederCount":33},{"price":478.4,"volume":"5400","orederCount":16},{"price":478.2,"volume":"102500","orederCount":66}]}
    OrderBookTest { ... }
     ...
     ...
    stop
    

1  
2  
3  
4  
5  

提示

*   此接口提供了持续获取推送数据的功能，如需一次性获取实时数据，请参考 [获取实时摆盘](./quote_get-order-book.md)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](./qa_quote.md#2692)
    
*   美股市场实时摆盘回调，会持续推送当前交易时段的实时摆盘，无需设置时段。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`on_recv_rsp(self, rsp_pb)`

*   **介绍**
    
    实时摆盘回调，异步处理已订阅股票的实时摆盘推送。 在收到实时摆盘数据推送后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Qot\_UpdateOrderBook\_pb2.Response | 派生类中不需要直接处理该参数 |
    
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
        | svr\_recv\_time\_bid | str | moomoo 服务器从交易所收到买盘数据的时间<br>(ℹ️ 部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据) |
        | svr\_recv\_time\_ask | str | moomoo 服务器从交易所收到卖盘数据的时间<br>(ℹ️ 部分数据的接收时间为零，例如服务器重启或第一次推送的缓存数据) |
        | Bid | list | 每个元祖包含如下信息：委托价格，委托数量，委托订单数，委托订单明细<br>(ℹ️ 委托订单明细)<br><br>*   明细内容：交易所订单 ID，单笔委托数量<br>*   港股 SF 权限下最多支持 1000 笔委托订单明细；  <br>    其余行情权限不支持获取此类数据 |
        | Ask | list | 每个元祖包含如下信息：委托价格，委托数量，委托订单数，委托订单明细<br>(ℹ️ 委托订单明细)<br><br>*   明细内容：交易所订单 ID，单笔委托数量<br>*   港股 SF 权限下最多支持 1000 笔委托订单明细；  <br>    其余行情权限不支持获取此类数据 |
        
        其中，Bid 和 Ask 字段的结构如下：
        
            'Bid': [ (bid_price1, bid_volume1, order_num, {'orderid1': order_volume1, 'orderid2': order_volume2, …… }), (bid_price2, bid_volume2, order_num,  {'orderid1': order_volume1, 'orderid2': order_volume2, …… }),…]
            'Ask': [ (ask_price1, ask_volume1，order_num, {'orderid1': order_volume1, 'orderid2': order_volume2, …… }), (ask_price2, ask_volume2, order_num, {'orderid1': order_volume1, 'orderid2': order_volume2, …… }),…] 
            
        
*   **Example**
    

    import time
    from moomoo import *
    class OrderBookTest(OrderBookHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, data = super(OrderBookTest,self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("OrderBookTest: error, msg: %s" % data)
                return RET_ERROR, data
            print("OrderBookTest ", data) # OrderBookTest 自己的处理逻辑
            return RET_OK, data
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = OrderBookTest()
    quote_ctx.set_handler(handler)  # 设置实时摆盘回调
    ret, data = quote_ctx.subscribe(['US.AAPL'], [SubType.ORDER_BOOK])  # 订阅买卖摆盘类型，OpenD 开始持续收到服务器的推送
    if ret == RET_OK:
        print(data)
    else:
        print('error:', data)
    time.sleep(15)  #  设置脚本接收 OpenD 的推送持续时间为15秒
    quote_ctx.close()  # 关闭当条连接，OpenD 会在1分钟后自动取消相应股票相应类型的订阅
    

1  
2  
3  
4  
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

*   **Output**

    OrderBookTest  {'code': 'US.AAPL', 'name': '苹果', 'svr_recv_time_bid': '', 'svr_recv_time_ask': '', 'Bid': [(179.77, 100, 1, {}), (179.68, 200, 1, {}), (179.65, 2, 2, {}), (179.64, 27, 1, {}), (179.6, 9, 2, {}), (179.58, 39, 2, {}), (179.5, 13, 4, {}), (179.48, 331, 2, {}), (179.4, 1002, 2, {}), (179.38, 330, 1, {}), (179.37, 2, 1, {}), (179.3, 47, 1, {}), (179.28, 330, 1, {}), (179.21, 2, 1, {}), (179.2, 1000, 1, {}), (179.18, 330, 1, {}), (179.17, 100, 1, {}), (179.16, 1, 1, {}), (179.13, 400, 1, {}), (179.1, 3000, 1, {}), (179.08, 330, 1, {}), (179.05, 125, 2, {}), (179.01, 17, 2, {}), (179.0, 81, 7, {})], 'Ask': [(179.95, 400, 2, {}), (180.0, 360, 2, {}), (180.05, 20, 1, {}), (180.1, 246, 4, {}), (180.18, 20, 1, {}), (180.2, 2030, 3, {}), (180.23, 20, 1, {}), (180.3, 23, 1, {}), (180.33, 15, 1, {}), (180.4, 2000, 2, {}), (180.49, 5, 1, {}), (180.59, 253, 1, {}), (180.6, 2000, 2, {}), (180.8, 2010, 3, {}), (181.0, 2018, 4, {}), (181.08, 1, 1, {}), (181.2, 1009, 2, {}), (181.3, 17, 3, {}), (181.4, 1, 1, {}), (181.5, 50, 1, {}), (181.79, 9, 1, {}), (181.9, 66, 2, {})]}
    

1  

[#](./quote_update-order-book.md#4825-2)
 Qot\_UpdateOrderBook.proto
------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    实时摆盘回调，异步处理已订阅股票的实时摆盘推送。
    
*   **参数**
    

    
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
20  
21  

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   买卖盘结构参见 [OrderBook](./quote_quote.md#6364)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3013
    

`virtual void OnReply_UpdateOrderBook(MMAPI_Conn client, QotUpdateOrderBook.Response rsp);`

*   **介绍**

实时摆盘回调，异步处理已订阅股票的实时摆盘推送。

*   **参数**

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
20  

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
        
        public void OnReply_UpdateOrderBook(MMAPI_Conn client, uint nSerialNo, QotUpdateOrderBook.Response rsp) {
            Console.Write("Push: UpdateOrderBook: {0}\n", nSerialNo);
            Console.Write("price : {0}\n", rsp.S2C.OrderBookAskListList[0].Price);
            Console.Write("volume : {0}\n", rsp.S2C.OrderBookAskListList[0].Volume);        
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

    Qot onInitConnect: ret=0 desc= connID=6825335709542277011
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Push: UpdateOrderBook: 1
    price : 490
    volume : 3228700
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
10  

`void onPush_UpdateOrderBook(MMAPI_Conn client, QotUpdateOrderBook.Response rsp);`

*   **介绍**

实时摆盘回调，异步处理已订阅股票的实时摆盘推送。

*   **参数**

    
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
20  
21  

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
        public void onPush_UpdateOrderBook(MMAPI_Conn client, QotUpdateOrderBook.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotUpdateOrderBook failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotUpdateOrderBook: %s\n", json);
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

    Receive QotUpdateOrderBook: {
      "retType": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "orderBookAskList": [{\
          "price": 587.5,\
          "volume": "200",\
          "orederCount": 2\
        }, ... {\
          "price": 582.5,\
          "volume": "2600",\
          "orederCount": 13\
        }],
        "svrRecvTimeBid": "2021-06-25 10:07:32.841",
        "svrRecvTimeBidTimestamp": 1.624586852841E9,
        "svrRecvTimeAsk": "2021-06-25 10:07:32.841",
        "svrRecvTimeAskTimestamp": 1.624586852841E9
      }
    }
    Receive QotUpdateOrderBook: {
      "retType": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "orderBookAskList": [{\
          "price": 587.5,\
          "volume": "200",\
          "orederCount": 2\
        }, ... {\
          "price": 582.5,\
          "volume": "2600",\
          "orederCount": 13\
        }],
        "svrRecvTimeBid": "2021-06-25 10:07:32.941",
        "svrRecvTimeBidTimestamp": 1.624586852941E9,
        "svrRecvTimeAsk": "2021-06-25 10:07:32.941",
        "svrRecvTimeAskTimestamp": 1.624586852941E9
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

`virtual void OnPush_UpdateOrderBook(const Qot_UpdateOrderBook::Response &stRsp) = 0;`

*   **介绍**
    
    实时摆盘回调，异步处理已订阅股票的实时摆盘推送。
    
*   **参数**
    

    
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
20  
21  

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
    
    
    	virtual void OnInitConnect(MMAPI_Conn* pConn, Futu::i64_t nErrCode, const char* strDesc) {
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
                if (stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "Sub Failed" << endl;
                    return;
                }
            }
    	}
    
    	virtual void OnPush_UpdateOrderBook(const Qot_UpdateOrderBook::Response &stRsp) {
    		cout << "OnPush_UpdateOrderBook: " << endl;
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
    OnPush_UpdateOrderBook: 
    {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "orderBookAskList": [\
       {\
        "price": 605.5,\
        "volume": "51000",\
        "orederCount": 54\
       },\
    ...\
       {\
        "price": 610,\
        "volume": "70900",\
        "orederCount": 147\
       }\
      ],
      "orderBookBidList": [\
       {\
        "price": 605,\
        "volume": "14600",\
        "orederCount": 23\
       },\
    ...\
       {\
        "price": 600.5,\
        "volume": "19100",\
        "orederCount": 32\
       }\
      ],
      "svrRecvTimeBid": "2021-06-09 11:09:51.493",
      "svrRecvTimeBidTimestamp": 1623208191.493,
      "svrRecvTimeAsk": "2021-06-09 11:09:51.493",
      "svrRecvTimeAskTimestamp": 1623208191.493
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

`OnPush(cmd,res)`

*   **介绍**
    
    实时摆盘回调，异步处理已订阅股票的实时摆盘推送。
    
*   **参数**
    

    
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
20  
21  

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
    
    function UpdateOrderBook(){
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
                    subTypeList: [ SubType.SubType_OrderBook ], // 订阅实时摆盘类型
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
            if(ftCmdID.QotUpdateOrderBook.cmd == cmd){ // 实时摆盘推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    console.log("OrderBookTest", JSON.stringify(s2c));
                } else {
                    console.log("OrderBookTest: error")
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

    OrderBookTest {"security":{"market":1,"code":"00700"},"orderBookAskList":[{"price":480.4,"volume":"700","orederCount":2},{"price":480.6,"volume":"385900","orederCount":14},{"price":480.8,"volume":"4200","orederCount":3},{"price":481,"volume":"15000","orederCount":14},{"price":481.2,"volume":"4600","orederCount":5},{"price":481.4,"volume":"13400","orederCount":4},{"price":481.6,"volume":"1800","orederCount":6},{"price":481.8,"volume":"3000","orederCount":8},{"price":482,"volume":"23700","orederCount":25},{"price":482.2,"volume":"1200","orederCount":3}],"orderBookBidList":[{"price":480,"volume":"55300","orederCount":39},{"price":479.8,"volume":"92400","orederCount":61},{"price":479.6,"volume":"106100","orederCount":41},{"price":479.4,"volume":"85900","orederCount":48},{"price":479.2,"volume":"78100","orederCount":34},{"price":479,"volume":"50200","orederCount":150},{"price":478.8,"volume":"51300","orederCount":44},{"price":478.6,"volume":"92700","orederCount":33},{"price":478.4,"volume":"5400","orederCount":16},{"price":478.2,"volume":"102500","orederCount":66}]}
    OrderBookTest { ... }
     ...
     ...
    stop
    

1  
2  
3  
4  
5  

提示

*   此接口提供了持续获取推送数据的功能，如需一次性获取实时数据，请参考 [获取实时摆盘](./quote_get-order-book.md)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](./qa_quote.md#2692)
    
*   美股市场实时摆盘回调，会持续推送当前交易时段的实时摆盘，无需设置时段。

← [实时报价回调](./quote_update-stock-quote.md) [实时 K 线回调](./quote_update-kl.md)
 →

[实时摆盘回调](./quote_update-order-book.md)