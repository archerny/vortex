 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/sub-acc-push.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/sub-acc-push.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/sub-acc-push.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/sub-acc-push.html)
    

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
    

[#](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html#7365)
 订阅交易推送
=================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

Python 不需要订阅交易推送

[#](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html#6589)
 Trd\_SubAccPush.proto
------------------------------------------------------------------------------------------------

*   **介绍**
    
    订阅接收交易账户的推送数据  
    指定发送该协议的连接接收交易数据（订单状态，成交状态等）推送
    
*   **参数**
    

    message C2S
    {
    	repeated uint64 accIDList = 1; //要接收推送数据的业务账号列表，全量非增量，即使用者请每次传需要接收推送数据的所有业务账号
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

*   **回调**

    message S2C
    {
    	
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2008
    

`uint SubAccPush(TrdSubAccPush.Request req);`  
`virtual void OnReply_SubAccPush(FTAPI_Conn client, uint nSerialNo, TrdSubAccPush.Response rsp);`

*   **介绍**
    
    订阅接收交易账户的推送数据  
    指定发送该协议的连接接收交易数据（订单状态，成交状态等）推送
    
*   **参数**
    

    message C2S
    {
    	repeated uint64 accIDList = 1; //要接收推送数据的业务账号列表，全量非增量，即使用者请每次传需要接收推送数据的所有业务账号
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

*   **回调**

    message S2C
    {
    	
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
            Console.Write("Reply: TrdSubAccPush: {0}\n", nSerialNo);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6827944734875485972
    Send TrdSubAccPush: 3
    Reply: TrdSubAccPush: 3
    retMsg:
    

1  
2  
3  
4  

`int subAccPush(TrdSubAccPush.Request req);`  
`void onReply_SubAccPush(FTAPI_Conn client, int nSerialNo, TrdSubAccPush.Response rsp);`

*   **介绍**
    
    订阅接收交易账户的推送数据  
    指定发送该协议的连接接收交易数据（订单状态，成交状态等）推送
    
*   **参数**
    

    message C2S
    {
    	repeated uint64 accIDList = 1; //要接收推送数据的业务账号列表，全量非增量，即使用者请每次传需要接收推送数据的所有业务账号
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

*   **回调**

    message S2C
    {
    	
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
        public void onReply_SubAccPush(FTAPI_Conn client, int nSerialNo, TrdSubAccPush.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdSubAccPush failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdSubAccPush: %s\n", json);
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

*   **Output**

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

`Futu::u32_t SubAccPush(const Trd_SubAccPush::Request &stReq);`  
`virtual void OnReply_SubAccPush(Futu::u32_t nSerialNo, const Trd_SubAccPush::Response &stRsp) = 0;`

*   **介绍**
    
    订阅接收交易账户的推送数据  
    指定发送该协议的连接接收交易数据（订单状态，成交状态等）推送
    
*   **参数**
    

    message C2S
    {
    	repeated uint64 accIDList = 1; //要接收推送数据的业务账号列表，全量非增量，即使用者请每次传需要接收推送数据的所有业务账号
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

*   **回调**

    message S2C
    {
    	
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

`SubAccPush(req);`

*   **介绍**
    
    订阅接收交易账户的推送数据  
    指定发送该协议的连接接收交易数据（订单状态，成交状态等）推送
    
*   **参数**
    

    message C2S
    {
    	repeated uint64 accIDList = 1; //要接收推送数据的业务账号列表，全量非增量，即使用者请每次传需要接收推送数据的所有业务账号
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdSubAccPush(){
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
            } else if(ftCmdID.TrdUpdateOrder.cmd == cmd){ // 订单状态变动通知推送的处理逻辑
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
101  
102  
103  
104  
105  
106  
107  
108  
109  
110  
111  
112  
113  

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

Python 不需要订阅交易推送

[#](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html#6589-2)
 Trd\_SubAccPush.proto
--------------------------------------------------------------------------------------------------

*   **介绍**
    
    订阅接收交易账户的推送数据  
    指定发送该协议的连接接收交易数据（订单状态，成交状态等）推送
    
*   **参数**
    

    message C2S
    {
    	repeated uint64 accIDList = 1; //要接收推送数据的业务账号列表，全量非增量，即使用者请每次传需要接收推送数据的所有业务账号
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

*   **回调**

    message S2C
    {
    	
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2008
    

`uint SubAccPush(TrdSubAccPush.Request req);`  
`virtual void OnReply_SubAccPush(MMAPI_Conn client, uint nSerialNo, TrdSubAccPush.Response rsp);`

*   **介绍**
    
    订阅接收交易账户的推送数据  
    指定发送该协议的连接接收交易数据（订单状态，成交状态等）推送
    
*   **参数**
    

    message C2S
    {
    	repeated uint64 accIDList = 1; //要接收推送数据的业务账号列表，全量非增量，即使用者请每次传需要接收推送数据的所有业务账号
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

*   **回调**

    message S2C
    {
    	
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
            Console.Write("Reply: TrdSubAccPush: {0}\n", nSerialNo);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6827944734875485972
    Send TrdSubAccPush: 3
    Reply: TrdSubAccPush: 3
    retMsg:
    

1  
2  
3  
4  

`int subAccPush(TrdSubAccPush.Request req);`  
`void onReply_SubAccPush(MMAPI_Conn client, int nSerialNo, TrdSubAccPush.Response rsp);`

*   **介绍**
    
    订阅接收交易账户的推送数据  
    指定发送该协议的连接接收交易数据（订单状态，成交状态等）推送
    
*   **参数**
    

    message C2S
    {
    	repeated uint64 accIDList = 1; //要接收推送数据的业务账号列表，全量非增量，即使用者请每次传需要接收推送数据的所有业务账号
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

*   **回调**

    message S2C
    {
    	
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
        public void onReply_SubAccPush(MMAPI_Conn client, int nSerialNo, TrdSubAccPush.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdSubAccPush failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdSubAccPush: %s\n", json);
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

*   **Output**

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

`moomoo::u32_t SubAccPush(const Trd_SubAccPush::Request &stReq);`  
`virtual void OnReply_SubAccPush(moomoo::u32_t nSerialNo, const Trd_SubAccPush::Response &stRsp) = 0;`

*   **介绍**
    
    订阅接收交易账户的推送数据  
    指定发送该协议的连接接收交易数据（订单状态，成交状态等）推送
    
*   **参数**
    

    message C2S
    {
    	repeated uint64 accIDList = 1; //要接收推送数据的业务账号列表，全量非增量，即使用者请每次传需要接收推送数据的所有业务账号
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

*   **回调**

    message S2C
    {
    	
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

`SubAccPush(req);`

*   **介绍**
    
    订阅接收交易账户的推送数据  
    指定发送该协议的连接接收交易数据（订单状态，成交状态等）推送
    
*   **参数**
    

    message C2S
    {
    	repeated uint64 accIDList = 1; //要接收推送数据的业务账号列表，全量非增量，即使用者请每次传需要接收推送数据的所有业务账号
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function TrdSubAccPush(){
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
            if(mmCmdID.TrdUpdateOrderFill.cmd == cmd){ // 成交通知推送的处理逻辑
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
            } else if(ftCmdID.TrdUpdateOrder.cmd == cmd){ // 订单状态变动通知推送的处理逻辑
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
101  
102  
103  
104  
105  
106  
107  
108  
109  
110  
111  
112  
113  

← [查询订单费用](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html) [查询当日成交](https://openapi.futunn.com/futu-api-doc/trade/get-order-fill-list.html)
 →

[订阅交易推送](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html)