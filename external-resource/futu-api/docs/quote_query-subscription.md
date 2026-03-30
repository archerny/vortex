# 获取订阅状态 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quote/query-subscription.html

[#](https://openapi.futunn.com/futu-api-doc/quote/query-subscription.html#8593)
 获取订阅状态
=======================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`query_subscription(is_all_conn=True)`

*   **介绍**
    
    获取订阅信息
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | is\_all\_conn | bool | 是否返回所有连接的订阅状态<br>(ℹ️ True：返回所有连接的订阅状态  <br>False：只返回当前连接的订阅状态) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | dict | 当 ret == RET\_OK，返回订阅信息数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   订阅信息数据字典格式如下：
        
              {
                  'total_used': 4,    # 所有连接已使用的订阅额度
                  'own_used': 0,       # 当前连接已使用的订阅额度
                  'remain': 496,       #  剩余的订阅额度
                  'sub_list':          #  每种订阅类型对应的股票列表
                  {
                      '订阅的类型': 该订阅类型下所有已订阅股票列表,
                      …
                  }
              }
            
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    quote_ctx.subscribe(['HK.00700'], [SubType.QUOTE])
    ret, data = quote_ctx.query_subscription()
    if ret == RET_OK:
        print(data)
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
10  

*   **Output**

    {'total_used': 1, 'remain': 999, 'own_used': 1, 'sub_list': {'QUOTE': ['HK.00700']}}
    

1  

[#](https://openapi.futunn.com/futu-api-doc/quote/query-subscription.html#3734)
 Qot\_GetSubInfo.proto
------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取订阅信息
    
*   **参数**
    

    message C2S
    {
    	optional bool isReqAllConn = 1; //是否返回所有连接的订阅状态,不传或者传 false 只返回当前连接数据
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
    	repeated Qot_Common.ConnSubInfo connSubInfoList = 1; //单条连接订阅信息
    	required int32 totalUsedQuota = 2; //FutuOpenD 已使用的订阅额度
    	required int32 remainQuota = 3; //FutuOpenD 剩余订阅额度
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

> *   订阅信息结构参见 [ConnSubInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6578)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3003
    

`uint GetSubInfo(QotGetSubInfo.Request req);`  
`virtual void OnReply_GetSubInfo(FTAPI_Conn client, uint nSerialNo, QotGetSubInfo.Response rsp);`

*   **介绍**

获取订阅信息

*   **参数**

    message C2S
    {
    	optional bool isReqAllConn = 1; //是否返回所有连接的订阅状态,不传或者传 false 只返回当前连接数据
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
    	repeated Qot_Common.ConnSubInfo connSubInfoList = 1; //单条连接订阅信息
    	required int32 totalUsedQuota = 2; //FutuOpenD 已使用的订阅额度
    	required int32 remainQuota = 3; //FutuOpenD 剩余订阅额度
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

> *   订阅信息结构参见 [ConnSubInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6578)
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
    
            QotGetSubInfo.C2S c2s = QotGetSubInfo.C2S.CreateBuilder()
                .Build();
            QotGetSubInfo.Request req = QotGetSubInfo.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetSubInfo(req);
            Console.Write("Send QotGetSubInfo: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_GetSubInfo(FTAPI_Conn client, uint nSerialNo, QotGetSubInfo.Response rsp) {
            Console.Write("Reply: QotGetSubInfo: {0}  {1}\n", nSerialNo, rsp.ToString());
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6819190992235784251
    Send QotGetSubInfo: 3
    Reply: QotGetSubInfo: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      connSubInfoList {
        usedQuota: 0
        isOwnConnData: true
      }
      totalUsedQuota: 0
      remainQuota: 1000
    }
    

1  
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

`int getSubInfo(QotGetSubInfo.Request req);`  
`void onReply_GetSubInfo(FTAPI_Conn client, int nSerialNo, QotGetSubInfo.Response rsp);`

*   **介绍**

获取订阅信息

*   **参数**

    message C2S
    {
    	optional bool isReqAllConn = 1; //是否返回所有连接的订阅状态,不传或者传 false 只返回当前连接数据
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
    	repeated Qot_Common.ConnSubInfo connSubInfoList = 1; //单条连接订阅信息
    	required int32 totalUsedQuota = 2; //OpenD 已使用的订阅额度
    	required int32 remainQuota = 3; //OpenD 剩余订阅额度
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

> *   订阅信息结构参见 [ConnSubInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6578)
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
    
            QotGetSubInfo.C2S c2s = QotGetSubInfo.C2S.newBuilder()
                .build();
            QotGetSubInfo.Request req = QotGetSubInfo.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getSubInfo(req);
            System.out.printf("Send QotGetSubInfo: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetSubInfo(FTAPI_Conn client, int nSerialNo, QotGetSubInfo.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetSubInfo failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetSubInfo: %s\n", json);
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

*   **Output**

    Send QotGetSubInfo: 2
    Receive QotGetSubInfo: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "connSubInfoList": [{\
          "usedQuota": 0,\
          "isOwnConnData": true\
        }],
        "totalUsedQuota": 0,
        "remainQuota": 300
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

`Futu::u32_t GetSubInfo(const Qot_GetSubInfo::Request &stReq);`  
`virtual void OnReply_GetSubInfo(Futu::u32_t nSerialNo, const Qot_GetSubInfo::Response &stRsp) = 0;`

*   **介绍**
    
    获取订阅信息
    
*   **参数**
    

    message C2S
    {
    	optional bool isReqAllConn = 1; //是否返回所有连接的订阅状态,不传或者传 false 只返回当前连接数据
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
    	repeated Qot_Common.ConnSubInfo connSubInfoList = 1; //单条连接订阅信息
    	required int32 totalUsedQuota = 2; //OpenD 已使用的订阅额度
    	required int32 remainQuota = 3; //OpenD 剩余订阅额度
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

> *   订阅信息结构参见 [ConnSubInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6578)
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
    
    		// 组包
    		Qot_GetSubInfo::Request req;
    		Qot_GetSubInfo::C2S *c2s = req.mutable_c2s();
    
    		m_GetSubInfoSerialNo = m_pQotApi->GetSubInfo(req);
    		cout << "Request GetSubInfo SerialNo: " << m_GetSubInfoSerialNo << endl;
    	}
    
    	virtual void OnReply_GetSubInfo(Futu::u32_t nSerialNo, const Qot_GetSubInfo::Response &stRsp){
            if(nSerialNo == m_GetSubInfoSerialNo)
            {
                cout << "OnReply_GetSubInfo SerialNo:" << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetSubInfoSerialNo;
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

*   **Output**

    connect
    Request GetSubInfo SerialNo: 4
    OnReply_GetSubInfo SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "connSubInfoList": [\
       {\
        "usedQuota": 0,\
        "isOwnConnData": true\
       }\
      ],
      "totalUsedQuota": 1,
      "remainQuota": 299
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

`GetSubInfo(req);`

*   **介绍**
    
    获取订阅信息
    
*   **参数**
    

    message C2S
    {
    	optional bool isReqAllConn = 1; //是否返回所有连接的订阅状态,不传或者传 false 只返回当前连接数据
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
    	repeated Qot_Common.ConnSubInfo connSubInfoList = 1; //单条连接订阅信息
    	required int32 totalUsedQuota = 2; //OpenD 已使用的订阅额度
    	required int32 remainQuota = 3; //OpenD 剩余订阅额度
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

> *   订阅信息结构参见 [ConnSubInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6578)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    
    function GetSubInfo(){
        const { RetType } = Common;
        const { SubType, QotMarket } = Qot_Common;
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
                    subTypeList: [ SubType.SubType_OrderBook ],
                    isSubOrUnSub: true,
                    isRegOrUnRegPush: true, 
                    },
                }).then((res) => { 
    
                    const req = {
                        c2s: {
                            isReqAllConn: true,
                        },
                    }; // 获取订阅状态参数
    
                    websocket.GetSubInfo(req)
                    .then((res) => { 
                        let { errCode, retMsg, retType,s2c } = res
                        if(retType == RetType.RetType_Succeed){
                            console.log("GetSubInfo: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                            console.log("GetSubInfo:", JSON.stringify(s2c)); 
                        } else {
                            console.log("GetSubInfo: error")
                        }
                    })
                    .catch((error) => {
                        console.log(error)
                        if ("retMsg" in error) {
                            console.log("error:", error.retMsg);
                        }
                    });
    
                });
            } else {
                console.log("error", msg);
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
    
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

*   **Output**

    GetSubInfo: errCode 0, retMsg , retType 0
    GetSubInfo: {"connSubInfoList":[{"subInfoList":[{"subType":2,"securityList":[{"market":1,"code":"00700"}]}],"usedQuota":1,"isOwnConnData":true}],"totalUsedQuota":1,"remainQuota":99}
    stop
    

1  
2  
3  

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`query_subscription(is_all_conn=True)`

*   **介绍**
    
    获取订阅信息
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | is\_all\_conn | bool | 是否返回所有连接的订阅状态<br>(ℹ️ True：返回所有连接的订阅状态  <br>False：只返回当前连接的订阅状态) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | dict | 当 ret == RET\_OK，返回订阅信息数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   订阅信息数据字典格式如下：
        
              {
                  'total_used': 4,    # 所有连接已使用的订阅额度
                  'own_used': 0,       # 当前连接已使用的订阅额度
                  'remain': 496,       #  剩余的订阅额度
                  'sub_list':          #  每种订阅类型对应的股票列表
                  {
                      '订阅的类型': 该订阅类型下所有已订阅股票列表,
                      …
                  }
              }
            
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    quote_ctx.subscribe(['HK.00700'], [SubType.QUOTE])
    ret, data = quote_ctx.query_subscription()
    if ret == RET_OK:
        print(data)
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
10  

*   **Output**

    {'total_used': 1, 'remain': 999, 'own_used': 1, 'sub_list': {'QUOTE': ['HK.00700']}}
    

1  

[#](https://openapi.futunn.com/futu-api-doc/quote/query-subscription.html#3734-2)
 Qot\_GetSubInfo.proto
--------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取订阅信息
    
*   **参数**
    

    message C2S
    {
    	optional bool isReqAllConn = 1; //是否返回所有连接的订阅状态,不传或者传 false 只返回当前连接数据
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
    	repeated Qot_Common.ConnSubInfo connSubInfoList = 1; //单条连接订阅信息
    	required int32 totalUsedQuota = 2; // OpenD 已使用的订阅额度
    	required int32 remainQuota = 3; // OpenD 剩余订阅额度
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

> *   订阅信息结构参见 [ConnSubInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6578)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3003
    

`uint GetSubInfo(QotGetSubInfo.Request req);`  
`virtual void OnReply_GetSubInfo(MMAPI_Conn client, uint nSerialNo, QotGetSubInfo.Response rsp);`

*   **介绍**

获取订阅信息

*   **参数**

    message C2S
    {
    	optional bool isReqAllConn = 1; //是否返回所有连接的订阅状态,不传或者传 false 只返回当前连接数据
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
    	repeated Qot_Common.ConnSubInfo connSubInfoList = 1; //单条连接订阅信息
    	required int32 totalUsedQuota = 2; // OpenD 已使用的订阅额度
    	required int32 remainQuota = 3; // OpenD 剩余订阅额度
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

> *   订阅信息结构参见 [ConnSubInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6578)
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
    
            QotGetSubInfo.C2S c2s = QotGetSubInfo.C2S.CreateBuilder()
                .Build();
            QotGetSubInfo.Request req = QotGetSubInfo.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetSubInfo(req);
            Console.Write("Send QotGetSubInfo: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_GetSubInfo(MMAPI_Conn client, uint nSerialNo, QotGetSubInfo.Response rsp) {
            Console.Write("Reply: QotGetSubInfo: {0}  {1}\n", nSerialNo, rsp.ToString());
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6819190992235784251
    Send QotGetSubInfo: 3
    Reply: QotGetSubInfo: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      connSubInfoList {
        usedQuota: 0
        isOwnConnData: true
      }
      totalUsedQuota: 0
      remainQuota: 1000
    }
    

1  
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

`int getSubInfo(QotGetSubInfo.Request req);`  
`void onReply_GetSubInfo(MMAPI_Conn client, int nSerialNo, QotGetSubInfo.Response rsp);`

*   **介绍**

获取订阅信息

*   **参数**

    message C2S
    {
    	optional bool isReqAllConn = 1; //是否返回所有连接的订阅状态,不传或者传 false 只返回当前连接数据
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
    	repeated Qot_Common.ConnSubInfo connSubInfoList = 1; //单条连接订阅信息
    	required int32 totalUsedQuota = 2; // OpenD 已使用的订阅额度
    	required int32 remainQuota = 3; // OpenD 剩余订阅额度
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

> *   订阅信息结构参见 [ConnSubInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6578)
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
    
            QotGetSubInfo.C2S c2s = QotGetSubInfo.C2S.newBuilder()
                .build();
            QotGetSubInfo.Request req = QotGetSubInfo.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getSubInfo(req);
            System.out.printf("Send QotGetSubInfo: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetSubInfo(MMAPI_Conn client, int nSerialNo, QotGetSubInfo.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetSubInfo failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetSubInfo: %s\n", json);
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

*   **Output**

    Send QotGetSubInfo: 2
    Receive QotGetSubInfo: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "connSubInfoList": [{\
          "usedQuota": 0,\
          "isOwnConnData": true\
        }],
        "totalUsedQuota": 0,
        "remainQuota": 300
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

`moomoo::u32_t GetSubInfo(const Qot_GetSubInfo::Request &stReq);`  
`virtual void OnReply_GetSubInfo(moomoo::u32_t nSerialNo, const Qot_GetSubInfo::Response &stRsp) = 0;`

*   **介绍**
    
    获取订阅信息
    
*   **参数**
    

    message C2S
    {
    	optional bool isReqAllConn = 1; //是否返回所有连接的订阅状态,不传或者传 false 只返回当前连接数据
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
    	repeated Qot_Common.ConnSubInfo connSubInfoList = 1; //单条连接订阅信息
    	required int32 totalUsedQuota = 2; //OpenD 已使用的订阅额度
    	required int32 remainQuota = 3; //OpenD 剩余订阅额度
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

> *   订阅信息结构参见 [ConnSubInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6578)
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
    
    		// 组包
    		Qot_GetSubInfo::Request req;
    		Qot_GetSubInfo::C2S *c2s = req.mutable_c2s();
    
    		m_GetSubInfoSerialNo = m_pQotApi->GetSubInfo(req);
    		cout << "Request GetSubInfo SerialNo: " << m_GetSubInfoSerialNo << endl;
    	}
    
    	virtual void OnReply_GetSubInfo(moomoo::u32_t nSerialNo, const Qot_GetSubInfo::Response &stRsp){
            if(nSerialNo == m_GetSubInfoSerialNo)
            {
                cout << "OnReply_GetSubInfo SerialNo:" << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetSubInfoSerialNo;
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

*   **Output**

    connect
    Request GetSubInfo SerialNo: 4
    OnReply_GetSubInfo SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "connSubInfoList": [\
       {\
        "usedQuota": 0,\
        "isOwnConnData": true\
       }\
      ],
      "totalUsedQuota": 1,
      "remainQuota": 299
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

`GetSubInfo(req);`

*   **介绍**
    
    获取订阅信息
    
*   **参数**
    

    message C2S
    {
    	optional bool isReqAllConn = 1; //是否返回所有连接的订阅状态,不传或者传 false 只返回当前连接数据
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
    	repeated Qot_Common.ConnSubInfo connSubInfoList = 1; //单条连接订阅信息
    	required int32 totalUsedQuota = 2; //OpenD 已使用的订阅额度
    	required int32 remainQuota = 3; //OpenD 剩余订阅额度
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

> *   订阅信息结构参见 [ConnSubInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6578)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    
    function GetSubInfo(){
        const { RetType } = Common;
        const { SubType, QotMarket } = Qot_Common;
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
                    subTypeList: [ SubType.SubType_OrderBook ],
                    isSubOrUnSub: true,
                    isRegOrUnRegPush: true, 
                    },
                }).then((res) => { 
    
                    const req = {
                        c2s: {
                            isReqAllConn: true,
                        },
                    }; // 获取订阅状态参数
    
                    websocket.GetSubInfo(req)
                    .then((res) => { 
                        let { errCode, retMsg, retType,s2c } = res
                        if(retType == RetType.RetType_Succeed){
                            console.log("GetSubInfo: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                            console.log("GetSubInfo:", JSON.stringify(s2c)); 
                        } else {
                            console.log("GetSubInfo: error")
                        }
                    })
                    .catch((error) => {
                        console.log(error)
                        if ("retMsg" in error) {
                            console.log("error:", error.retMsg);
                        }
                    });
    
                });
            } else {
                console.log("error", msg);
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
    
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

*   **Output**

    GetSubInfo: errCode 0, retMsg , retType 0
    GetSubInfo: {"connSubInfoList":[{"subInfoList":[{"subType":2,"securityList":[{"market":1,"code":"00700"}]}],"usedQuota":1,"isOwnConnData":true}],"totalUsedQuota":1,"remainQuota":99}
    stop
    

1  
2  
3  

← [订阅反订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html) [实时报价回调](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html)
 →