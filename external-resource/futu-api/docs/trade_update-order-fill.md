[#](./trade_update-order-fill.md#210)
 响应成交推送回调
=======================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`on_recv_rsp(self, rsp_pb)`

*   **介绍**
    
    响应成交推送，异步处理 OpenD 推送过来的成交状态信息。  
    在收到 OpenD 推送过来的成交状态信息后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Trd\_UpdateOrderFill\_pb2.Response | 派生类中不需要直接处理该参数 |
    
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回交易成交列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   交易成交列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_side | [TrdSide](./trade_trade.md#5815) | 交易方向 |
        | deal\_id | str | 成交号 |
        | order\_id | str | 订单号 |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | qty | float | 成交数量<br>(ℹ️ 期权期货单位是"张") |
        | price | float | 成交价格 |
        | create\_time | str | 创建时间<br>(ℹ️ 期货时区指定，请参见 [FutuOpenD 配置](./quick_opend-base.md#6724)) |
        | counter\_broker\_id | int | 对手经纪号<br>(ℹ️ 仅港股有效) |
        | counter\_broker\_name | str | 对手经纪名称<br>(ℹ️ 仅港股有效) |
        | status | [DealStatus](./trade_trade.md#8317) | 成交状态 |
        
*   **Example**
    

    from futu import *
    from time import sleep
    class TradeDealTest(TradeDealHandlerBase):
        """ order update push"""
        def on_recv_rsp(self, rsp_pb):
            ret, content = super(TradeDealTest, self).on_recv_rsp(rsp_pb)
            if ret == RET_OK:
                print("TradeDealTest content={}".format(content))
            return ret, content
    
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    trd_ctx.set_handler(TradeDealTest())
    print(trd_ctx.place_order(price=595.0, qty=100, code="HK.00700", trd_side=TrdSide.BUY))
    
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

    TradeDealTest content=  trd_env      code stock_name              deal_id             order_id    qty  price trd_side              create_time  counter_broker_id counter_broker_name trd_market status
    0    REAL  HK.00700       腾讯控股  2511067564122483295  8561504228375901919  100.0  518.0      BUY  2021-11-04 11:29:41.595                  5                   5         HK     OK
    

1  
2  

[#](./trade_update-order-fill.md#5508)
 Trd\_UpdateOrderFill.proto
----------------------------------------------------------------------------------------------------------

*   **介绍**
    
    响应成交推送，异步处理 OpenD 推送过来的成交状态信息。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.OrderFill orderFill = 2; //成交结构
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   成交结构参见 [OrderFill](./trade_trade.md#1253)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    2218
    

`virtual void OnReply_UpdateOrderFill(FTAPI_Conn client, uint nSerialNo, TrdUpdateOrderFill.Response rsp);`

*   **介绍**
    
    响应成交推送，异步处理 OpenD 推送过来的成交状态信息。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.OrderFill orderFill = 2; //成交结构
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   成交结构参见 [OrderFill](./trade_trade.md#1253)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
            Console.Write("OnReply_SubAccPush: {0}\n", nSerialNo, rsp.ToString());
        }
    
        public void OnReply_UpdateOrderFill(FTAPI_Conn client, uint nSerialNo, TrdUpdateOrderFill.Response rsp)
        {
            Console.Write("Push: TrdUpdateOrderFill: {0}\n", nSerialNo);
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

    Trd onInitConnect: ret=0 desc= connID=6827940248993029021
    Send TrdSubAccPush: 3
    OnReply_SubAccPush: 3
    Push: TrdUpdateOrderFill: 1
    retMsg:
    

1  
2  
3  
4  
5  

`void onPush_UpdateOrderFill(FTAPI_Conn client, int nSerialNo, TrdUpdateOrderFill.Response rsp);`

*   **介绍**
    
    响应成交推送，异步处理 OpenD 推送过来的成交状态信息。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.OrderFill orderFill = 2; //成交结构
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   成交结构参见 [OrderFill](./trade_trade.md#1253)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
        public void onPush_UpdateOrderFill(FTAPI_Conn client, TrdUpdateOrderFill.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdUpdateOrderFill failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdUpdateOrderFill: %s\n", json);
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

    Send TrdUpdateOrderFill: 2
    Receive TrdUpdateOrderFill: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 2
        },
        "orderFillList": [{\
          "trdSide": 1,\
          "fillID": "449150869556176742",\
          "fillIDEx": "20210330_15680495_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1.4",\
          "orderID": "6664320708369556828",\
          "orderIDEx": "20210330_15680495_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1",\
          "code": "FUTU",\
          "name": "富途控股",\
          "qty": 34.0,\
          "price": 127.64,\
          "createTime": "2021-03-30 09:34:24.019",\
          "secMarket": 2,\
          "createTimestamp": 1.617111264019109E9,\
          "updateTimestamp": 1.617111264019109E9,\
          "status": 0\
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

`virtual void OnPush_UpdateOrderFill(const Trd_UpdateOrderFill::Response &stRsp) = 0;`

*   **介绍**
    
    响应成交推送，异步处理 OpenD 推送过来的成交状态信息。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.OrderFill orderFill = 2; //成交结构
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   成交结构参见 [OrderFill](./trade_trade.md#1253)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
    	virtual void OnPush_UpdateOrderFill(const Trd_UpdateOrderFill::Response &stRsp)
    	{
    		cout << "OnPush_UpdateOrderFill:" << endl;
    		// print response
    		// ProtoBufToBodyData and UTF8ToLocal refer to tool.h in Samples
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
    OnPush_UpdateOrderFill:
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756455988249902",
        "trdMarket": 1
      },
      "orderFill": {
        "trdSide": 1,
        "fillID": "932511865781776209",
        "fillIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1.2",
        "orderID": "4883217202603317248",
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",
        "code": "00700",
        "name": "腾讯控股",
        "qty": 100,
        "price": 480,
        "createTime": "2021-09-13 16:45:00.606",
        "counterBrokerID": 5,
        "counterBrokerName": "",
        "secMarket": 1,
        "createTimestamp": 1631522700.605828,
        "updateTimestamp": 1631522700.605828,
        "status": 0
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

`OnPush(cmd,res)`

*   **介绍**
    
    响应成交推送，异步处理 OpenD 推送过来的成交状态信息。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.OrderFill orderFill = 2; //成交结构
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   成交结构参见 [OrderFill](./trade_trade.md#1253)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdUpdateOrderFill(){
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
            if(ftCmdID.TrdUpdateOrderFill.cmd == cmd){ // 成交通知推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    let data = beautify(JSON.stringify(s2c), {
                        indent_size: 2,
                        space_in_empty_paren: true,
                    });
                    console.log("TrdUpdateOrderFill:");
                    console.log(data);
                } else {
                    console.log("TrdUpdateOrderFillTest: error")
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
    TrdUpdateOrderFill:
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756455988249902",
        "trdMarket": 1
      },
      "orderFill": {
        "trdSide": 1,
        "fillID": "932511865781776209",
        "fillIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1.2",
        "orderID": "4883217202603317248",
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",
        "code": "00700",
        "name": "腾讯控股",
        "qty": 100,
        "price": 480,
        "createTime": "2021-09-13 16:45:00.606",
        "counterBrokerID": 5,
        "counterBrokerName": "",
        "secMarket": 1,
        "createTimestamp": 1631522700.605828,
        "updateTimestamp": 1631522700.605828,
        "status": 0
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

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`on_recv_rsp(self, rsp_pb)`

*   **介绍**
    
    响应成交推送，异步处理 OpenD 推送过来的成交状态信息。  
    在收到 OpenD 推送过来的成交状态信息后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Trd\_UpdateOrderFill\_pb2.Response | 派生类中不需要直接处理该参数 |
    
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回交易成交列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   交易成交列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_side | [TrdSide](./trade_trade.md#5815) | 交易方向 |
        | deal\_id | str | 成交号 |
        | order\_id | str | 订单号 |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | qty | float | 成交数量<br>(ℹ️ 期权期货单位是"张") |
        | price | float | 成交价格 |
        | create\_time | str | 创建时间<br>(ℹ️ 期货时区指定，请参见 [OpenD 配置](./quick_opend-base.md#6724)) |
        | counter\_broker\_id | int | 对手经纪号<br>(ℹ️ 仅港股有效) |
        | counter\_broker\_name | str | 对手经纪名称<br>(ℹ️ 仅港股有效) |
        | status | [DealStatus](./trade_trade.md#8317) | 成交状态 |
        
*   **Example**
    

    from moomoo import *
    from time import sleep
    class TradeDealTest(TradeDealHandlerBase):
        """ order update push"""
        def on_recv_rsp(self, rsp_pb):
            ret, content = super(TradeDealTest, self).on_recv_rsp(rsp_pb)
            if ret == RET_OK:
                print("TradeDealTest content={}".format(content))
            return ret, content
    
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    trd_ctx.set_handler(TradeDealTest())
    print(trd_ctx.place_order(price=595.0, qty=100, code="US.AAPL", trd_side=TrdSide.BUY))
    
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

    TradeDealTest content=  trd_env      code stock_name              deal_id             order_id    qty  price trd_side              create_time  counter_broker_id counter_broker_name trd_market status
    0    REAL  US.AAPL        苹果  2511067564122483295  8561504228375901919  100.0  518.0      BUY  2021-11-04 11:29:41.595                  5                   5         US     OK
    

1  
2  

[#](./trade_update-order-fill.md#5508-2)
 Trd\_UpdateOrderFill.proto
------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    响应成交推送，异步处理 OpenD 推送过来的成交状态信息。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.OrderFill orderFill = 2; //成交结构
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   成交结构参见 [OrderFill](./trade_trade.md#1253)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    2218
    

`virtual void OnReply_UpdateOrderFill(FTAPI_Conn client, uint nSerialNo, TrdUpdateOrderFill.Response rsp);`

*   **介绍**
    
    响应成交推送，异步处理 OpenD 推送过来的成交状态信息。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.OrderFill orderFill = 2; //成交结构
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   成交结构参见 [OrderFill](./trade_trade.md#1253)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
            Console.Write("OnReply_SubAccPush: {0}\n", nSerialNo, rsp.ToString());
        }
    
        public void OnReply_UpdateOrderFill(MMAPI_Conn client, uint nSerialNo, TrdUpdateOrderFill.Response rsp)
        {
            Console.Write("Push: TrdUpdateOrderFill: {0}\n", nSerialNo);
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

    Trd onInitConnect: ret=0 desc= connID=6827940248993029021
    Send TrdSubAccPush: 3
    OnReply_SubAccPush: 3
    Push: TrdUpdateOrderFill: 1
    retMsg:
    

1  
2  
3  
4  
5  

`void onPush_UpdateOrderFill(FTAPI_Conn client, int nSerialNo, TrdUpdateOrderFill.Response rsp);`

*   **介绍**
    
    响应成交推送，异步处理 OpenD 推送过来的成交状态信息。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.OrderFill orderFill = 2; //成交结构
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   成交结构参见 [OrderFill](./trade_trade.md#1253)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
        public void onPush_UpdateOrderFill(MMAPI_Conn client, TrdUpdateOrderFill.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdUpdateOrderFill failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdUpdateOrderFill: %s\n", json);
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

    Send TrdUpdateOrderFill: 2
    Receive TrdUpdateOrderFill: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 2
        },
        "orderFillList": [{\
          "trdSide": 1,\
          "fillID": "449150869556176742",\
          "fillIDEx": "20210330_15680495_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1.4",\
          "orderID": "6664320708369556828",\
          "orderIDEx": "20210330_15680495_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1",\
          "code": "FUTU",\
          "name": "富途控股",\
          "qty": 34.0,\
          "price": 127.64,\
          "createTime": "2021-03-30 09:34:24.019",\
          "secMarket": 2,\
          "createTimestamp": 1.617111264019109E9,\
          "updateTimestamp": 1.617111264019109E9,\
          "status": 0\
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

`virtual void OnPush_UpdateOrderFill(const Trd_UpdateOrderFill::Response &stRsp) = 0;`

*   **介绍**
    
    响应成交推送，异步处理 OpenD 推送过来的成交状态信息。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.OrderFill orderFill = 2; //成交结构
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   成交结构参见 [OrderFill](./trade_trade.md#1253)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
    
    	virtual void OnInitConnect(MMAPI_Conn* pConn, moomoo::i64_t nErrCode, const char* strDesc) {
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
    
    	virtual void OnPush_UpdateOrderFill(const Trd_UpdateOrderFill::Response &stRsp)
    	{
    		cout << "OnPush_UpdateOrderFill:" << endl;
    		// print response
    		// ProtoBufToBodyData and UTF8ToLocal refer to tool.h in Samples
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
    OnPush_UpdateOrderFill:
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756455988249902",
        "trdMarket": 1
      },
      "orderFill": {
        "trdSide": 1,
        "fillID": "932511865781776209",
        "fillIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1.2",
        "orderID": "4883217202603317248",
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",
        "code": "00700",
        "name": "腾讯控股",
        "qty": 100,
        "price": 480,
        "createTime": "2021-09-13 16:45:00.606",
        "counterBrokerID": 5,
        "counterBrokerName": "",
        "secMarket": 1,
        "createTimestamp": 1631522700.605828,
        "updateTimestamp": 1631522700.605828,
        "status": 0
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

`OnPush(cmd,res)`

*   **介绍**
    
    响应成交推送，异步处理 OpenD 推送过来的成交状态信息。  
    该接口只支持实盘交易，不支持模拟交易。
    
*   **参数**
    

    message S2C
    {
            required Trd_Common.TrdHeader header = 1; //交易公共参数头
            required Trd_Common.OrderFill orderFill = 2; //成交结构
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   成交结构参见 [OrderFill](./trade_trade.md#1253)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function TrdUpdateOrderFill(){
        const { RetType } = Common
        const { TrdEnv, OrderType } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ec16fde057a2e7a0'];
        let websocket = new mmWebsocket();
    
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
            if(ftCmdID.TrdUpdateOrderFill.cmd == cmd){ // 成交通知推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    let data = beautify(JSON.stringify(s2c), {
                        indent_size: 2,
                        space_in_empty_paren: true,
                    });
                    console.log("TrdUpdateOrderFill:");
                    console.log(data);
                } else {
                    console.log("TrdUpdateOrderFillTest: error")
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
    TrdUpdateOrderFill:
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756455988249902",
        "trdMarket": 1
      },
      "orderFill": {
        "trdSide": 1,
        "fillID": "932511865781776209",
        "fillIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1.2",
        "orderID": "4883217202603317248",
        "orderIDEx": "20210913_5915950_OD|pM+9NqXZAaxnZYpScrsjT4zHWtlk1",
        "code": "00700",
        "name": "腾讯控股",
        "qty": 100,
        "price": 480,
        "createTime": "2021-09-13 16:45:00.606",
        "counterBrokerID": 5,
        "counterBrokerName": "",
        "secMarket": 1,
        "createTimestamp": 1631522700.605828,
        "updateTimestamp": 1631522700.605828,
        "status": 0
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

← [查询历史成交](./trade_get-history-order-fill-list.md) [交易定义](./trade_trade.md)
 →

[响应成交推送回调](./trade_update-order-fill.md)