 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/update-order.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/update-order.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/update-order.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/update-order.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/update-order.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/update-order.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
*   交易接口
    
    *   [交易接口总览](https://openapi.futunn.com/futu-api-doc/trade/overview.html)
        
    *   [交易对象](https://openapi.futunn.com/futu-api-doc/trade/base.html)
        
    *   账户
        
    *   资产持仓
        
    *   订单
        
        *   [下单](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)
            
        *   [改单撤单](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html)
            
        *   [查询未完成订单](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html)
            
        *   [查询历史订单](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-list.html)
            
        *   [响应订单推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order.html)
            
        *   [查询订单费用](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html)
            
        *   [订阅交易推送](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html)
            
        
    *   成交
        
    *   [交易定义](https://openapi.futunn.com/futu-api-doc/trade/trade.html)
        
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/trade/update-order.html#4653)
 响应订单推送回调
===================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`on_recv_rsp(self, rsp_pb)`

*   **介绍**
    
    响应订单推送，异步处理 OpenD 推送过来的订单状态信息。  
    在收到 OpenD 推送过来的订单状态信息后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Trd\_UpdateOrder\_pb2.Response | 派生类中不需要直接处理该参数 |
    
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回订单列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   订单列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_side | [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815) | 交易方向 |
        | order\_type | [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181) | 订单类型 |
        | order\_status | [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797) | 订单状态 |
        | order\_id | str | 订单号 |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | qty | float | 订单数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
        | price | float | 订单价格<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分四舍五入 |
        | currency | [Currency](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8019) | 交易货币 |
        | create\_time | str | 创建时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
        | updated\_time | str | 最后更新时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
        | dealt\_qty | float | 成交数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
        | dealt\_avg\_price | float | 成交均价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>无精度限制 |
        | last\_err\_msg | str | 最后的错误描述<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>如果有错误，会返回最后一次错误的原因  <br>如果无错误，返回空字符串 |
        | remark | str | 下单时备注的标识<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>详见 [place\_order](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)<br> 接口参数中的 remark |
        | time\_in\_force | [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241) | 有效期限 |
        | fill\_outside\_rth | bool | 是否允许盘前盘后（仅用于美股）<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：允许  <br>False：不允许 |
        | session | [Session](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9152) | 交易订单时段（仅用于美股） |
        | aux\_price | float | 触发价格 |
        | trail\_type | [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644) | 跟踪类型 |
        | trail\_value | float | 跟踪金额/百分比 |
        | trail\_spread | float | 指定价差 |
        
*   **Example**
    

    from futu import *
    from time import sleep
    class TradeOrderTest(TradeOrderHandlerBase):
        """ order update push"""
        def on_recv_rsp(self, rsp_pb):
            ret, content = super(TradeOrderTest, self).on_recv_rsp(rsp_pb)
            if ret == RET_OK:
                print("* TradeOrderTest content={}\n".format(content))
            return ret, content
    
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    trd_ctx.set_handler(TradeOrderTest())
    print(trd_ctx.place_order(price=518.0, qty=100, code="HK.00700", trd_side=TrdSide.SELL))
    
    sleep(15)
    trd_ctx.close()
    

1  
2  
3  
4  
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

    * TradeOrderTest content=  trd_env      code stock_name  dealt_avg_price  dealt_qty    qty           order_id order_type  price order_status          create_time         updated_time trd_side last_err_msg trd_market remark time_in_force fill_outside_rth session aux_price trail_type trail_value trail_spread currency
    0    REAL  HK.00700       腾讯控股              0.0        0.0  100.0  72625263708670783     NORMAL  518.0   SUBMITTING  2021-11-04 11:26:27  2021-11-04 11:26:27      BUY                      HK                  DAY      N/A        N/A       N/A        N/A         N/A          N/A      HKD
    

1  
2  

[#](https://openapi.futunn.com/futu-api-doc/trade/update-order.html#3291)
 Trd\_UpdateOrder.proto
-------------------------------------------------------------------------------------------------

*   **介绍**
    
    响应订单推送，异步处理 OpenD 推送过来的订单状态信息。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.Order order = 2; //订单结构
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2208
    

`virtual void OnReply_UpdateOrder(FTAPI_Conn client, TrdUpdateOrder.Response rsp);`

*   **介绍**
    
    响应订单推送，异步处理 OpenD 推送过来的订单状态信息。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.Order order = 2; //订单结构
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : FTSPI_Trd, FTSPI_Conn {
        FTAPI_Trd trd = new FTAPI_Trd();
    
        public Program() {
            trd.SetClientInfo("csharp", 1);  //设置客户端信息
            trd.SetConnCallback(this);  //设置连接回调
            trd.SetTrdCallback(this);   //设置交易回调
        }
    
        public void Start() {
            trd.InitConnect("127.0.0.1", (ushort)11111, false);
        }
    
        
        public void OnInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Trd onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
            if (errCode != 0)
                return;
    
            //先订阅才会有推送
            TrdSubAccPush.C2S c2s = TrdSubAccPush.C2S.CreateBuilder()
                    .AddAccIDList(281753457989306260L)
                    .Build();
            TrdSubAccPush.Request req = TrdSubAccPush.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.SubAccPush(req);
            Console.Write("Send TrdSubAccPush: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_SubAccPush(FTAPI_Conn client, uint nSerialNo, TrdSubAccPush.Response rsp)
        {
            Console.Write("OnReply_SubAccPush: {0}\n", nSerialNo);
            Console.Write("retMsg: {0}\n", rsp.RetMsg);
        }
    
        public void OnReply_UpdateOrder(FTAPI_Conn client, uint nSerialNo, TrdUpdateOrder.Response rsp)
        {
            Console.Write("Push: TrdUpdateOrder: {0}\n", nSerialNo);
            Console.Write("retMsg: {0}\n", rsp.RetMsg);
        }
    
        public static void Main(String[] args) {
            FTAPI.Init();
            Program trd = new Program();
            trd.Start();
    
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6827943584315573569
    Send TrdSubAccPush: 3
    OnReply_SubAccPush: 3
    retMsg:
    Push: TrdUpdateOrder: 1
    retMsg:
    

1  
2  
3  
4  
5  
6  

`void onPush_UpdateOrder(FTAPI_Conn client, TrdUpdateOrder.Response rsp);`

*   **介绍**
    
    响应订单推送，异步处理 OpenD 推送过来的订单状态信息。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.Order order = 2; //订单结构
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class TrdDemo implements FTSPI_Trd, FTSPI_Conn {
        FTAPI_Conn_Trd trd = new FTAPI_Conn_Trd();
    
        public TrdDemo() {
            trd.setClientInfo("javaclient", 1);  //设置客户端信息
            trd.setConnSpi(this);  //设置连接回调
            trd.setTrdSpi(this);   //设置交易回调
        }
    
        public void start() {
            trd.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Trd onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
            if (errCode != 0)
                return;
    
            //先订阅才会有推送
            TrdSubAccPush.C2S c2s = TrdSubAccPush.C2S.newBuilder()
                    .addAccIDList(281753457989306260L)
                    .build();
            TrdSubAccPush.Request req = TrdSubAccPush.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.subAccPush(req);
            System.out.printf("Send TrdSubAccPush: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onPush_UpdateOrder(FTAPI_Conn client, TrdUpdateOrder.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdUpdateOrder failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdUpdateOrder: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public static void main(String[] args) {
            FTAPI.init();
            TrdDemo trd = new TrdDemo();
            trd.start();
    
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

*   **Output**

    Send TrdUpdateOrder: 2
    Receive TrdUpdateOrder: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 0,
          "accID": "3450309",
          "trdMarket": 1
        },
        "orderList": [{\
          "trdSide": 1,\
          "orderType": 5,\
          "orderStatus": 11,\
          "orderID": "5185261023022402893",\
          "orderIDEx": "2691191",\
          "code": "00700",\
          "name": "腾讯控股",\
          "qty": 100.0,\
          "price": 594.0,\
          "createTime": "2021-06-25 11:38:30",\
          "updateTime": "2021-06-25 11:38:30",\
          "fillQty": 100.0,\
          "fillAvgPrice": 594.0,\
          "secMarket": 1,\
          "createTimestamp": 1.62459231E9,\
          "updateTimestamp": 1.62459231E9,\
          "remark": "",\
          "timeInForce": 0\
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

`virtual void OnPush_UpdateOrder(const Trd_UpdateOrder::Response &stRsp) = 0;`

*   **介绍**
    
    响应订单推送，异步处理 OpenD 推送过来的订单状态信息。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.Order order = 2; //订单结构
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    class Program : public FTSPI_Qot, public FTSPI_Trd, public FTSPI_Conn
    {
    public:
    
    	Program() {
    		m_pTrdApi = FTAPI::CreateTrdApi();
    		m_pTrdApi->RegisterTrdSpi(this);
    		m_pTrdApi->RegisterConnSpi(this);
    	}
    
    	~Program() {
    		if (m_pTrdApi != nullptr)
    		{
    			m_pTrdApi->UnregisterTrdSpi();
    			m_pTrdApi->UnregisterConnSpi();
    			FTAPI::ReleaseTrdApi(m_pTrdApi);
    			m_pTrdApi = nullptr;
    		}
    	}
    
    	void Start() {
    		m_pTrdApi->InitConnect("127.0.0.1", 11111, false);
    	}
    
    
    	virtual void OnInitConnect(FTAPI_Conn* pConn, Futu::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		Trd_SubAccPush::Request req;
    		Trd_SubAccPush::C2S *c2s = req.mutable_c2s();
    		c2s->add_accidlist(3637840);
    
            m_SubAccPushSerialNo = m_pTrdApi->SubAccPush(req);
            cout << "Request SubAccPush SerialNo: " << m_SubAccPushSerialNo << endl;
    	}
    
    	virtual void OnReply_SubAccPush(Futu::u32_t nSerialNo, const Trd_SubAccPush::Response &stRsp) {
            if(nSerialNo == m_SubAccPushSerialNo)
            {
                cout << "OnReply_SubAccPush SerialNo: " << nSerialNo << endl;
                if (stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "SubAccPush Failed" << endl;
                    return;
                }
            }
    	}
    
    	virtual void OnPush_UpdateOrder(const Trd_UpdateOrder::Response &stRsp)
    	{
    		cout << "OnPush_UpdateOrder:" << endl;
    		// 解析内部结构打印出来
    		// ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
    		string resp_str;
    		ProtoBufToBodyData(stRsp, resp_str);
    		cout << UTF8ToLocal(resp_str) << endl;
    	}
    
    protected:
    	FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_SubAccPushSerialNo;
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

*   **Output**

    connect
    Request SubAccPush SerialNo: 4
    OnReply_SubAccPush SerialNo: 4
    OnPush_UpdateOrder:
    {
     "retType": 0,
     "s2c": {
      "header": {
       "trdEnv": 0,
       "accID": "3637840",
       "trdMarket": 1
      },
      "order": {
       "trdSide": 1,
       "orderType": 5,
       "orderStatus": 5,
       "orderID": "3964487190993133422",
       "orderIDEx": "1741803",
       "code": "00700",
       "name": "Tencent",
       "qty": 100,
       "price": 607.5,
       "createTime": "2021-06-09 16:00:06",
       "updateTime": "2021-06-09 16:00:06",
       "fillQty": 0,
       "fillAvgPrice": 0,
       "secMarket": 1,
       "createTimestamp": 1623225606,
       "updateTimestamp": 1623225606,
       "remark": "",
       "timeInForce": 0
      }
     }
    }
    
    OnPush_UpdateOrder:
    {
     "retType": 0,
     "s2c": {
      "header": {
       "trdEnv": 0,
       "accID": "3637840",
       "trdMarket": 1
      },
      "order": {
       "trdSide": 1,
       "orderType": 5,
       "orderStatus": 15,
       "orderID": "3964487190993133422",
       "orderIDEx": "1741803",
       "code": "00700",
       "name": "Tencent",
       "qty": 100,
       "price": 607.5,
       "createTime": "2021-06-09 16:00:06",
       "updateTime": "2021-06-09 16:00:22",
       "fillQty": 0,
       "fillAvgPrice": 0,
       "secMarket": 1,
       "createTimestamp": 1623225606,
       "updateTimestamp": 1623225622,
       "remark": "",
       "timeInForce": 0
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

`OnPush(cmd,res)`

*   **介绍**
    
    响应订单推送，异步处理 OpenD 推送过来的订单状态信息。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.Order order = 2; //订单结构
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdUpdateOrder(){
        const { RetType } = Common
        const { TrdEnv, OrderType } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ec16fde057a2e7a0'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.GetAccList({
                    c2s: {
                        userID: 0,
                    },
                }).then((res) => {
                    let { retType,s2c: { accList } } = res
                    if(retType == RetType.RetType_Succeed){
                        let accIDList = accList.map((acc)=>{ return acc.accID }); // 订阅所有账号的交易推送
                        
                        const req = {
                            c2s: {
                                accIDList: accIDList,
                            },
                        };
    
                        websocket.SubAccPush(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("SubAccPush: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
    
                    }
                })
                .catch((error) => {
                    console.log("GetAccList error:", error);
                });
            } else {
                console.log("error", msg);
            }
        };
    
        websocket.onPush = (cmd, res)=>{
            if(ftCmdID.TrdUpdateOrder.cmd == cmd){ // 订单状态变动通知推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    let data = beautify(JSON.stringify(s2c), {
                        indent_size: 2,
                        space_in_empty_paren: true,
                    });
                    console.log("TrdUpdateOrder:");
                    console.log(data);
                } else {
                    console.log("TrdUpdateOrderTest: error")
                }
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
        
        //关闭行情连接，连接不再使用之后，要关闭，否则占用不必要资源
        //同时OpenD也限制了最多128条连接
        //也可以一个页面或者一个项目维护一条连接，这里范例请求一次创建一条连接
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 3600*1000); // 3600秒后断开
    }
    

1  
2  
3  
4  
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

*   **Output**

    SubAccPush: errCode 0, retMsg , retType 0
    null
    TrdUpdateOrder:
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756455988249902",
        "trdMarket": 1
      },
      "order": {
        "trdSide": 1,
        "orderType": 1,
        "orderStatus": 2,
        "orderID": "4883217202603317248",
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",
        "code": "00700",
        "name": "腾讯控股",
        "qty": 100,
        "price": 480,
        "createTime": "2021-09-13 16:45:00",
        "updateTime": "2021-09-13 16:45:00",
        "fillQty": 0,
        "fillAvgPrice": 0,
        "secMarket": 1,
        "createTimestamp": 1631522700,
        "updateTimestamp": 1631522700,
        "remark": "",
        "timeInForce": 0
      }
    }
    TrdUpdateOrder:
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756455988249902",
        "trdMarket": 1
      },
      "order": {
        "trdSide": 1,
        "orderType": 1,
        "orderStatus": 5,
        "orderID": "4883217202603317248",
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",
        "code": "00700",
        "name": "腾讯控股",
        "qty": 100,
        "price": 480,
        "createTime": "2021-09-13 16:45:00.313",
        "updateTime": "2021-09-13 16:45:00.568",
        "fillQty": 0,
        "fillAvgPrice": 0,
        "secMarket": 1,
        "createTimestamp": 1631522700.312849,
        "updateTimestamp": 1631522700.567732,
        "remark": "",
        "timeInForce": 0
      }
    }
    TrdUpdateOrder:
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756455988249902",
        "trdMarket": 1
      },
      "order": {
        "trdSide": 1,
        "orderType": 1,
        "orderStatus": 11,
        "orderID": "4883217202603317248",
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",
        "code": "00700",
        "name": "腾讯控股",
        "qty": 100,
        "price": 480,
        "createTime": "2021-09-13 16:45:00.313",
        "updateTime": "2021-09-13 16:45:00.604",
        "fillQty": 100,
        "fillAvgPrice": 480,
        "secMarket": 1,
        "createTimestamp": 1631522700.312849,
        "updateTimestamp": 1631522700.604215,
        "remark": "",
        "timeInForce": 0
      }
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

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`on_recv_rsp(self, rsp_pb)`

*   **介绍**
    
    响应订单推送，异步处理 OpenD 推送过来的订单状态信息。  
    在收到 OpenD 推送过来的订单状态信息后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Trd\_UpdateOrder\_pb2.Response | 派生类中不需要直接处理该参数 |
    
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回订单列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   订单列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_side | [TrdSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5815) | 交易方向 |
        | order\_type | [OrderType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4181) | 订单类型 |
        | order\_status | [OrderStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#797) | 订单状态 |
        | order\_id | str | 订单号 |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | qty | float | 订单数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
        | price | float | 订单价格<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分四舍五入 |
        | currency | [Currency](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8019) | 交易货币 |
        | create\_time | str | 创建时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
        | updated\_time | str | 最后更新时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货时区指定，请参见 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html#6724) |
        | dealt\_qty | float | 成交数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权期货单位是"张" |
        | dealt\_avg\_price | float | 成交均价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>无精度限制 |
        | last\_err\_msg | str | 最后的错误描述<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>如果有错误，会返回最后一次错误的原因  <br>如果无错误，返回空字符串 |
        | remark | str | 下单时备注的标识<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>详见 [place\_order](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)<br> 接口参数中的 remark |
        | time\_in\_force | [TimeInForce](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4241) | 有效期限 |
        | fill\_outside\_rth | bool | 是否允许盘前盘后（仅用于美股）<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：允许  <br>False：不允许 |
        | session | [Session](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9152) | 交易订单时段（仅用于美股） |
        | aux\_price | float | 触发价格 |
        | trail\_type | [TrailType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#5644) | 跟踪类型 |
        | trail\_value | float | 跟踪金额/百分比 |
        | trail\_spread | float | 指定价差 |
        
*   **Example**
    

    from moomoo import *
    from time import sleep
    class TradeOrderTest(TradeOrderHandlerBase):
        """ order update push"""
        def on_recv_rsp(self, rsp_pb):
            ret, content = super(TradeOrderTest, self).on_recv_rsp(rsp_pb)
            if ret == RET_OK:
                print("* TradeOrderTest content={}\n".format(content))
            return ret, content
    
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    trd_ctx.set_handler(TradeOrderTest())
    print(trd_ctx.place_order(price=518.0, qty=100, code="US.AAPL", trd_side=TrdSide.SELL))
    
    sleep(15)
    trd_ctx.close()
    

1  
2  
3  
4  
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

    * TradeOrderTest content=  trd_env      code stock_name  dealt_avg_price  dealt_qty    qty           order_id order_type  price order_status          create_time         updated_time trd_side last_err_msg trd_market remark time_in_force fill_outside_rth session aux_price trail_type trail_value trail_spread currency
    0    REAL  US.AAPL       苹果                0.0        0.0  100.0  72625263708670783     NORMAL  518.0   SUBMITTING  2021-11-04 11:26:27  2021-11-04 11:26:27      BUY                      US                  DAY     N/A         N/A       N/A        N/A         N/A          N/A      USD
    

1  
2  

[#](https://openapi.futunn.com/futu-api-doc/trade/update-order.html#3291-2)
 Trd\_UpdateOrder.proto
---------------------------------------------------------------------------------------------------

*   **介绍**
    
    响应订单推送，异步处理 OpenD 推送过来的订单状态信息。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.Order order = 2; //订单结构
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2208
    

`virtual void OnReply_UpdateOrder(MMAPI_Conn client, TrdUpdateOrder.Response rsp);`

*   **介绍**
    
    响应订单推送，异步处理 OpenD 推送过来的订单状态信息。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.Order order = 2; //订单结构
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : MMSPI_Trd, MMSPI_Conn {
        MMAPI_Trd trd = new MMAPI_Trd();
    
        public Program() {
            trd.SetClientInfo("csharp", 1);  //设置客户端信息
            trd.SetConnCallback(this);  //设置连接回调
            trd.SetTrdCallback(this);   //设置交易回调
        }
    
        public void Start() {
            trd.InitConnect("127.0.0.1", (ushort)11111, false);
        }
    
        
        public void OnInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Trd onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
            if (errCode != 0)
                return;
    
            //先订阅才会有推送
            TrdSubAccPush.C2S c2s = TrdSubAccPush.C2S.CreateBuilder()
                    .AddAccIDList(281753457989306260L)
                    .Build();
            TrdSubAccPush.Request req = TrdSubAccPush.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.SubAccPush(req);
            Console.Write("Send TrdSubAccPush: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_SubAccPush(MMAPI_Conn client, uint nSerialNo, TrdSubAccPush.Response rsp)
        {
            Console.Write("OnReply_SubAccPush: {0}\n", nSerialNo);
            Console.Write("retMsg: {0}\n", rsp.RetMsg);
        }
    
        public void OnReply_UpdateOrder(MMAPI_Conn client, uint nSerialNo, TrdUpdateOrder.Response rsp)
        {
            Console.Write("Push: TrdUpdateOrder: {0}\n", nSerialNo);
            Console.Write("retMsg: {0}\n", rsp.RetMsg);
        }
    
        public static void Main(String[] args) {
            MMAPI.Init();
            Program trd = new Program();
            trd.Start();
    
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6827943584315573569
    Send TrdSubAccPush: 3
    OnReply_SubAccPush: 3
    retMsg:
    Push: TrdUpdateOrder: 1
    retMsg:
    

1  
2  
3  
4  
5  
6  

`void onPush_UpdateOrder(MMAPI_Conn client, TrdUpdateOrder.Response rsp);`

*   **介绍**
    
    响应订单推送，异步处理 OpenD 推送过来的订单状态信息。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.Order order = 2; //订单结构
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class TrdDemo implements MMSPI_Trd, MMSPI_Conn {
        MMAPI_Conn_Trd trd = new MMAPI_Conn_Trd();
    
        public TrdDemo() {
            trd.setClientInfo("javaclient", 1);  //设置客户端信息
            trd.setConnSpi(this);  //设置连接回调
            trd.setTrdSpi(this);   //设置交易回调
        }
    
        public void start() {
            trd.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Trd onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
            if (errCode != 0)
                return;
    
            //先订阅才会有推送
            TrdSubAccPush.C2S c2s = TrdSubAccPush.C2S.newBuilder()
                    .addAccIDList(281753457989306260L)
                    .build();
            TrdSubAccPush.Request req = TrdSubAccPush.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.subAccPush(req);
            System.out.printf("Send TrdSubAccPush: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onPush_UpdateOrder(MMAPI_Conn client, TrdUpdateOrder.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdUpdateOrder failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdUpdateOrder: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public static void main(String[] args) {
            MMAPI.init();
            TrdDemo trd = new TrdDemo();
            trd.start();
    
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

*   **Output**

    Send TrdUpdateOrder: 2
    Receive TrdUpdateOrder: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 0,
          "accID": "3450309",
          "trdMarket": 1
        },
        "orderList": [{\
          "trdSide": 1,\
          "orderType": 5,\
          "orderStatus": 11,\
          "orderID": "5185261023022402893",\
          "orderIDEx": "2691191",\
          "code": "00700",\
          "name": "腾讯控股",\
          "qty": 100.0,\
          "price": 594.0,\
          "createTime": "2021-06-25 11:38:30",\
          "updateTime": "2021-06-25 11:38:30",\
          "fillQty": 100.0,\
          "fillAvgPrice": 594.0,\
          "secMarket": 1,\
          "createTimestamp": 1.62459231E9,\
          "updateTimestamp": 1.62459231E9,\
          "remark": "",\
          "timeInForce": 0\
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

`virtual void OnPush_UpdateOrder(const Trd_UpdateOrder::Response &stRsp) = 0;`

*   **介绍**
    
    响应订单推送，异步处理 OpenD 推送过来的订单状态信息。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.Order order = 2; //订单结构
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    class Program : public MMSPI_Qot, public MMSPI_Trd, public MMSPI_Conn
    {
    public:
    
    	Program() {
    		m_pTrdApi = MMAPI::CreateTrdApi();
    		m_pTrdApi->RegisterTrdSpi(this);
    		m_pTrdApi->RegisterConnSpi(this);
    	}
    
    	~Program() {
    		if (m_pTrdApi != nullptr)
    		{
    			m_pTrdApi->UnregisterTrdSpi();
    			m_pTrdApi->UnregisterConnSpi();
    			MMAPI::ReleaseTrdApi(m_pTrdApi);
    			m_pTrdApi = nullptr;
    		}
    	}
    
    	void Start() {
    		m_pTrdApi->InitConnect("127.0.0.1", 11111, false);
    	}
    
    
    	virtual void OnInitConnect(FTAPI_Conn* pConn, moomoo::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		Trd_SubAccPush::Request req;
    		Trd_SubAccPush::C2S *c2s = req.mutable_c2s();
    		c2s->add_accidlist(3637840);
    
            m_SubAccPushSerialNo = m_pTrdApi->SubAccPush(req);
            cout << "Request SubAccPush SerialNo: " << m_SubAccPushSerialNo << endl;
    	}
    
    	virtual void OnReply_SubAccPush(moomoo::u32_t nSerialNo, const Trd_SubAccPush::Response &stRsp) {
            if(nSerialNo == m_SubAccPushSerialNo)
            {
                cout << "OnReply_SubAccPush SerialNo: " << nSerialNo << endl;
                if (stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "SubAccPush Failed" << endl;
                    return;
                }
            }
    	}
    
    	virtual void OnPush_UpdateOrder(const Trd_UpdateOrder::Response &stRsp)
    	{
    		cout << "OnPush_UpdateOrder:" << endl;
    		// 解析内部结构打印出来
    		// ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
    		string resp_str;
    		ProtoBufToBodyData(stRsp, resp_str);
    		cout << UTF8ToLocal(resp_str) << endl;
    	}
    
    protected:
    	MMAPI_Trd *m_pTrdApi;
    
        moomoo::u32_t m_SubAccPushSerialNo;
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

*   **Output**

    connect
    Request SubAccPush SerialNo: 4
    OnReply_SubAccPush SerialNo: 4
    OnPush_UpdateOrder:
    {
     "retType": 0,
     "s2c": {
      "header": {
       "trdEnv": 0,
       "accID": "3637840",
       "trdMarket": 1
      },
      "order": {
       "trdSide": 1,
       "orderType": 5,
       "orderStatus": 5,
       "orderID": "3964487190993133422",
       "orderIDEx": "1741803",
       "code": "00700",
       "name": "Tencent",
       "qty": 100,
       "price": 607.5,
       "createTime": "2021-06-09 16:00:06",
       "updateTime": "2021-06-09 16:00:06",
       "fillQty": 0,
       "fillAvgPrice": 0,
       "secMarket": 1,
       "createTimestamp": 1623225606,
       "updateTimestamp": 1623225606,
       "remark": "",
       "timeInForce": 0
      }
     }
    }
    
    OnPush_UpdateOrder:
    {
     "retType": 0,
     "s2c": {
      "header": {
       "trdEnv": 0,
       "accID": "3637840",
       "trdMarket": 1
      },
      "order": {
       "trdSide": 1,
       "orderType": 5,
       "orderStatus": 15,
       "orderID": "3964487190993133422",
       "orderIDEx": "1741803",
       "code": "00700",
       "name": "Tencent",
       "qty": 100,
       "price": 607.5,
       "createTime": "2021-06-09 16:00:06",
       "updateTime": "2021-06-09 16:00:22",
       "fillQty": 0,
       "fillAvgPrice": 0,
       "secMarket": 1,
       "createTimestamp": 1623225606,
       "updateTimestamp": 1623225622,
       "remark": "",
       "timeInForce": 0
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

`OnPush(cmd,res)`

*   **介绍**
    
    响应订单推送，异步处理 OpenD 推送过来的订单状态信息。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.Order order = 2; //订单结构
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   订单结构参见 [Order](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1935)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function TrdUpdateOrder(){
        const { RetType } = Common
        const { TrdEnv, OrderType } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ec16fde057a2e7a0'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.GetAccList({
                    c2s: {
                        userID: 0,
                    },
                }).then((res) => {
                    let { retType,s2c: { accList } } = res
                    if(retType == RetType.RetType_Succeed){
                        let accIDList = accList.map((acc)=>{ return acc.accID }); // 订阅所有账号的交易推送
                        
                        const req = {
                            c2s: {
                                accIDList: accIDList,
                            },
                        };
    
                        websocket.SubAccPush(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("SubAccPush: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
    
                    }
                })
                .catch((error) => {
                    console.log("GetAccList error:", error);
                });
            } else {
                console.log("error", msg);
            }
        };
    
        websocket.onPush = (cmd, res)=>{
            if(mmCmdID.TrdUpdateOrder.cmd == cmd){ // 订单状态变动通知推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    let data = beautify(JSON.stringify(s2c), {
                        indent_size: 2,
                        space_in_empty_paren: true,
                    });
                    console.log("TrdUpdateOrder:");
                    console.log(data);
                } else {
                    console.log("TrdUpdateOrderTest: error")
                }
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
        
        //关闭行情连接，连接不再使用之后，要关闭，否则占用不必要资源
        //同时OpenD也限制了最多128条连接
        //也可以一个页面或者一个项目维护一条连接，这里范例请求一次创建一条连接
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 3600*1000); // 3600秒后断开
    }
    

1  
2  
3  
4  
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

*   **Output**

    SubAccPush: errCode 0, retMsg , retType 0
    null
    TrdUpdateOrder:
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756455988249902",
        "trdMarket": 1
      },
      "order": {
        "trdSide": 1,
        "orderType": 1,
        "orderStatus": 2,
        "orderID": "4883217202603317248",
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",
        "code": "00700",
        "name": "腾讯控股",
        "qty": 100,
        "price": 480,
        "createTime": "2021-09-13 16:45:00",
        "updateTime": "2021-09-13 16:45:00",
        "fillQty": 0,
        "fillAvgPrice": 0,
        "secMarket": 1,
        "createTimestamp": 1631522700,
        "updateTimestamp": 1631522700,
        "remark": "",
        "timeInForce": 0
      }
    }
    TrdUpdateOrder:
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756455988249902",
        "trdMarket": 1
      },
      "order": {
        "trdSide": 1,
        "orderType": 1,
        "orderStatus": 5,
        "orderID": "4883217202603317248",
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",
        "code": "00700",
        "name": "腾讯控股",
        "qty": 100,
        "price": 480,
        "createTime": "2021-09-13 16:45:00.313",
        "updateTime": "2021-09-13 16:45:00.568",
        "fillQty": 0,
        "fillAvgPrice": 0,
        "secMarket": 1,
        "createTimestamp": 1631522700.312849,
        "updateTimestamp": 1631522700.567732,
        "remark": "",
        "timeInForce": 0
      }
    }
    TrdUpdateOrder:
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756455988249902",
        "trdMarket": 1
      },
      "order": {
        "trdSide": 1,
        "orderType": 1,
        "orderStatus": 11,
        "orderID": "4883217202603317248",
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",
        "code": "00700",
        "name": "腾讯控股",
        "qty": 100,
        "price": 480,
        "createTime": "2021-09-13 16:45:00.313",
        "updateTime": "2021-09-13 16:45:00.604",
        "fillQty": 100,
        "fillAvgPrice": 480,
        "secMarket": 1,
        "createTimestamp": 1631522700.312849,
        "updateTimestamp": 1631522700.604215,
        "remark": "",
        "timeInForce": 0
      }
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

← [查询历史订单](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-list.html) [查询订单费用](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html)
 →

[响应订单推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order.html)