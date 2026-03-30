[#](./quote_get-market-state.md#5008)
 获取标的市场状态
=======================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_market_state(code_list)`

*   **介绍**
    
    获取指定标的的市场状态
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 需要查询市场状态的股票代码列表<br>(ℹ️ list 中元素类型是 str) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回市场状态数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   市场状态数据
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | market\_state | [MarketState](./quote_quote.md#1252) | 市场状态 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_market_state(['SZ.000001', 'HK.00700'])
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

*   **Output**

        code         stock_name   market_state
    0  SZ.000001    平安银行     AFTERNOON
    1  HK.00700     腾讯控股     AFTERNOON
    

1  
2  
3  

[#](./quote_get-market-state.md#5873)
 Qot\_GetMarketState.proto
--------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取指定标的的市场状态
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

    message MarketInfo
    {
    	required Qot_Common.Security security = 1; //股票代码
    	required string name = 2; // 股票名称
    	required int32 marketState = 3; //Qot_Common.QotMarketState，市场状态
    }
    
    message S2C
    {
    	repeated MarketInfo marketInfoList = 1; // 市场状态信息
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
> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3223
    

`uint GetMarketState(QotGetMarketState.Request req);`  
`virtual void OnReply_GetMarketState(FTAPI_Conn client, uint nSerialNo, QotGetMarketState.Response rsp);`

*   **介绍**

获取指定标的的市场状态

*   **参数**

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

    message MarketInfo
    {
    	required Qot_Common.Security security = 1; //股票代码
    	required string name = 2; // 股票名称
    	required int32 marketState = 3; //Qot_Common.QotMarketState，市场状态
    }
    
    message S2C
    {
    	repeated MarketInfo marketInfoList = 1; // 市场状态信息
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
> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
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
            QotGetMarketState.C2S c2s = QotGetMarketState.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                .Build();
            QotGetMarketState.Request req = QotGetMarketState.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetMarketState(req);
            Console.Write("Send QotGetMarketState: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetMarketState(FTAPI_Conn client, uint nSerialNo, QotGetMarketState.Response rsp)
        {
            Console.Write("Reply: QotGetMarketState: {0}\n", nSerialNo);
            Console.Write("code: {0} , name: {1}\n", rsp.S2C.MarketInfoListList[0].Security.Code,
                rsp.S2C.MarketInfoListList[0].Name);
            Console.Write("marketState: {0}\n", rsp.S2C.MarketInfoListList[0].MarketState);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825681116823902476
    Send QotGetMarketState: 3
    Reply: QotGetMarketState: 3
    code: 00700 , name: 腾讯控股
    marketState: 5
    

1  
2  
3  
4  
5  

`int getMarketState(QotGetMarketState.Request req);`  
`void onReply_GetMarketState(FTAPI_Conn client, int nSerialNo, QotGetMarketState.Response rsp);`

*   **介绍**

获取指定标的的市场状态

*   **参数**

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

    message MarketInfo
    {
    	required Qot_Common.Security security = 1; //股票代码
    	required string name = 2; // 股票名称
    	required int32 marketState = 3; //Qot_Common.QotMarketState，市场状态
    }
    
    message S2C
    {
    	repeated MarketInfo marketInfoList = 1; // 市场状态信息
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
> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
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
            QotGetMarketState.C2S c2s = QotGetMarketState.C2S.newBuilder()
                    .addSecurityList(sec)
                .build();
            QotGetMarketState.Request req = QotGetMarketState.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getMarketState(req);
            System.out.printf("Send QotGetMarketState: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetMarketState(FTAPI_Conn client, int nSerialNo, QotGetMarketState.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetMarketState failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetMarketState: %s\n", json);
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

    Send QotGetMarketState: 2
    Receive QotGetMarketState: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "marketInfoList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "name": "腾讯控股",\
          "marketState": 6\
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

`Futu::u32_t GetMarketState(const Qot_GetMarketState::Request &stReq);`  
`virtual void OnReply_GetMarketState(Futu::u32_t nSerialNo, const Qot_GetMarketState::Response &stRsp) = 0;`

*   **介绍**
    
    获取指定标的的市场状态
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

    message MarketInfo
    {
    	required Qot_Common.Security security = 1; //股票代码
    	required string name = 2; // 股票名称
    	required int32 marketState = 3; //Qot_Common.QotMarketState，市场状态
    }
    
    message S2C
    {
    	repeated MarketInfo marketInfoList = 1; // 市场状态信息
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
> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
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
    		Qot_GetMarketState::Request req;
    		Qot_GetMarketState::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetMarketStateSerialNo = m_pQotApi->GetMarketState(req);
            cout << "Request GetMarketState SerialNo: " << m_GetMarketStateSerialNo << endl;
    	}
    
    	virtual void OnReply_GetMarketState(Futu::u32_t nSerialNo, const Qot_GetMarketState::Response &stRsp){
            if(nSerialNo == m_GetMarketStateSerialNo)
            {
                cout << "OnReply_GetMarketState SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetMarketStateSerialNo;
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

*   **Output**

    connect
    Request GetMarketState SerialNo: 4
    OnReply_GetMarketState SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "marketInfoList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "00700"\
        },\
        "name": "腾讯控股",\
        "marketState": 3\
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

`GetMarketState(req);`

*   **介绍**
    
    获取指定标的的市场状态
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

    message MarketInfo
    {
    	required Qot_Common.Security security = 1; //股票代码
    	required string name = 2; // 股票名称
    	required int32 marketState = 3; //Qot_Common.QotMarketState，市场状态
    }
    
    message S2C
    {
    	repeated MarketInfo marketInfoList = 1; // 市场状态信息
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
> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetMarketState(){
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
                    },
                };
                websocket.GetMarketState(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("MarketState: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    MarketState: errCode 0, retMsg , retType 0
    {
      "marketInfoList": [{\
        "security": {\
          "market": 1,\
          "code": "00700"\
        },\
        "name": "腾讯控股",\
        "marketState": 3\
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

接口限制

*   每 30 秒内最多请求 10 次获取标的市场状态接口。
*   每次请求的股票代码个数上限为 400 个。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_market_state(code_list)`

*   **介绍**
    
    获取指定标的的市场状态
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 需要查询市场状态的股票代码列表<br>(ℹ️ list 中元素类型是 str) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回市场状态数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   市场状态数据
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | market\_state | [MarketState](./quote_quote.md#1252) | 市场状态 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_market_state(['SZ.000001', 'HK.00700'])
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

*   **Output**

        code         stock_name   market_state
    0  SZ.000001    平安银行     AFTERNOON
    1  HK.00700     腾讯控股     AFTERNOON
    

1  
2  
3  

[#](./quote_get-market-state.md#5873-2)
 Qot\_GetMarketState.proto
----------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取指定标的的市场状态
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

    message MarketInfo
    {
    	required Qot_Common.Security security = 1; //股票代码
    	required string name = 2; // 股票名称
    	required int32 marketState = 3; //Qot_Common.QotMarketState，市场状态
    }
    
    message S2C
    {
    	repeated MarketInfo marketInfoList = 1; // 市场状态信息
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
> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3223
    

`uint GetMarketState(QotGetMarketState.Request req);`  
`virtual void OnReply_GetMarketState(MMAPI_Conn client, uint nSerialNo, QotGetMarketState.Response rsp);`

*   **介绍**

获取指定标的的市场状态

*   **参数**

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

    message MarketInfo
    {
    	required Qot_Common.Security security = 1; //股票代码
    	required string name = 2; // 股票名称
    	required int32 marketState = 3; //Qot_Common.QotMarketState，市场状态
    }
    
    message S2C
    {
    	repeated MarketInfo marketInfoList = 1; // 市场状态信息
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
> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
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
            QotGetMarketState.C2S c2s = QotGetMarketState.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                .Build();
            QotGetMarketState.Request req = QotGetMarketState.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetMarketState(req);
            Console.Write("Send QotGetMarketState: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetMarketState(MMAPI_Conn client, uint nSerialNo, QotGetMarketState.Response rsp)
        {
            Console.Write("Reply: QotGetMarketState: {0}\n", nSerialNo);
            Console.Write("code: {0} , name: {1}\n", rsp.S2C.MarketInfoListList[0].Security.Code,
                rsp.S2C.MarketInfoListList[0].Name);
            Console.Write("marketState: {0}\n", rsp.S2C.MarketInfoListList[0].MarketState);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825681116823902476
    Send QotGetMarketState: 3
    Reply: QotGetMarketState: 3
    code: 00700 , name: 腾讯控股
    marketState: 5
    

1  
2  
3  
4  
5  

`int getMarketState(QotGetMarketState.Request req);`  
`void onReply_GetMarketState(MMAPI_Conn client, int nSerialNo, QotGetMarketState.Response rsp);`

*   **介绍**

获取指定标的的市场状态

*   **参数**

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

    message MarketInfo
    {
    	required Qot_Common.Security security = 1; //股票代码
    	required string name = 2; // 股票名称
    	required int32 marketState = 3; //Qot_Common.QotMarketState，市场状态
    }
    
    message S2C
    {
    	repeated MarketInfo marketInfoList = 1; // 市场状态信息
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
> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
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
            QotGetMarketState.C2S c2s = QotGetMarketState.C2S.newBuilder()
                    .addSecurityList(sec)
                .build();
            QotGetMarketState.Request req = QotGetMarketState.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getMarketState(req);
            System.out.printf("Send QotGetMarketState: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetMarketState(MMAPI_Conn client, int nSerialNo, QotGetMarketState.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetMarketState failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetMarketState: %s\n", json);
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

    Send QotGetMarketState: 2
    Receive QotGetMarketState: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "marketInfoList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "name": "腾讯控股",\
          "marketState": 6\
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

`moomoo::u32_t GetMarketState(const Qot_GetMarketState::Request &stReq);`  
`virtual void OnReply_GetMarketState(moomoo::u32_t nSerialNo, const Qot_GetMarketState::Response &stRsp) = 0;`

*   **介绍**
    
    获取指定标的的市场状态
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

    message MarketInfo
    {
    	required Qot_Common.Security security = 1; //股票代码
    	required string name = 2; // 股票名称
    	required int32 marketState = 3; //Qot_Common.QotMarketState，市场状态
    }
    
    message S2C
    {
    	repeated MarketInfo marketInfoList = 1; // 市场状态信息
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
> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
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
    		Qot_GetMarketState::Request req;
    		Qot_GetMarketState::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetMarketStateSerialNo = m_pQotApi->GetMarketState(req);
            cout << "Request GetMarketState SerialNo: " << m_GetMarketStateSerialNo << endl;
    	}
    
    	virtual void OnReply_GetMarketState(moomoo::u32_t nSerialNo, const Qot_GetMarketState::Response &stRsp){
            if(nSerialNo == m_GetMarketStateSerialNo)
            {
                cout << "OnReply_GetMarketState SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetMarketStateSerialNo;
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

*   **Output**

    connect
    Request GetMarketState SerialNo: 4
    OnReply_GetMarketState SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "marketInfoList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "00700"\
        },\
        "name": "腾讯控股",\
        "marketState": 3\
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

`GetMarketState(req);`

*   **介绍**
    
    获取指定标的的市场状态
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票列表
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

    message MarketInfo
    {
    	required Qot_Common.Security security = 1; //股票代码
    	required string name = 2; // 股票名称
    	required int32 marketState = 3; //Qot_Common.QotMarketState，市场状态
    }
    
    message S2C
    {
    	repeated MarketInfo marketInfoList = 1; // 市场状态信息
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
> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetMarketState(){
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
                    },
                };
                websocket.GetMarketState(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("MarketState: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    MarketState: errCode 0, retMsg , retType 0
    {
      "marketInfoList": [{\
        "security": {\
          "market": 1,\
          "code": "00700"\
        },\
        "name": "腾讯控股",\
        "marketState": 3\
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

接口限制

*   每 30 秒内最多请求 10 次获取标的市场状态接口。
*   每次请求的股票代码个数上限为 400 个。

← [获取实时经纪队列](./quote_get-broker.md) [获取资金流向](./quote_get-capital-flow.md)
 →

[获取标的市场状态](./quote_get-market-state.md)