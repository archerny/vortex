[#](./quote_get-global-state.md#5035)
 获取全局市场状态
=======================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_global_state()`

*   **介绍**
    
    获取全局状态
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | dict | 当 ret == RET\_OK 时，返回全局状态 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   全局状态字典格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | market\_sz | [MarketState](./quote_quote.md#1252) | 深圳市场状态 |
        | market\_sh | [MarketState](./quote_quote.md#1252) | 上海市场状态 |
        | market\_hk | [MarketState](./quote_quote.md#1252) | 香港市场状态 |
        | market\_hkfuture | [MarketState](./quote_quote.md#1252) | 香港期货市场状态<br>(ℹ️ 不同品种的交易时间存在差异，建议使用 [get\_market\_state](./quote_get-market-state.md))<br> 接口获取指定品种的市场状态 |
        | market\_usfuture | [MarketState](./quote_quote.md#1252) | 美国期货市场状态<br>(ℹ️ 不同品种的交易时间存在差异，建议使用 [get\_market\_state](./quote_get-market-state.md))<br> 接口获取指定品种的市场状态 |
        | market\_us | [MarketState](./quote_quote.md#1252) | 美国市场状态<br>(ℹ️ 不同品种的交易时间存在差异，建议使用 [get\_market\_state](./quote_get-market-state.md))<br> 接口获取指定品种的市场状态 |
        | market\_sgfuture | [MarketState](./quote_quote.md#1252) | 新加坡期货市场状态<br>(ℹ️ 不同品种的交易时间存在差异，建议使用 [get\_market\_state](./quote_get-market-state.md))<br> 接口获取指定品种的市场状态 |
        | market\_jpfuture | [MarketState](./quote_quote.md#1252) | 日本期货市场状态 |
        | server\_ver | str | OpenD 版本号 |
        | trd\_logined | bool | True：已登录交易服务器，False：未登录交易服务器 |
        | qot\_logined | bool | True：已登录行情服务器，False：未登录行情服务器 |
        | timestamp | str | 当前格林威治时间戳<br>(ℹ️ 单位：秒) |
        | local\_timestamp | float | OpenD 运行机器的当前时间戳<br>(ℹ️ 单位：秒) |
        | program\_status\_type | [ProgramStatusType](./ftapi_common.md#6427) | 当前状态 |
        | program\_status\_desc | str | 额外描述 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    print(quote_ctx.get_global_state())
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  
4  

*   **Output**

    (0, {'market_sz': 'MORNING', 'market_us': 'AFTER_HOURS_END', 'market_sh': 'MORNING', 'market_hk': 'MORNING', 'market_hkfuture': 'FUTURE_DAY_OPEN', 'market_usfuture': 'FUTURE_OPEN', 'market_sgfuture': 'FUTURE_DAY_OPEN', 'market_jpfuture': 'FUTURE_DAY_OPEN', 'server_ver': '504', 'trd_logined': True, 'timestamp': '1620962951', 'qot_logined': True, 'local_timestamp': 1620962951.047128, 'program_status_type': 'READY', 'program_status_desc': ''})
    

1  

[#](./quote_get-global-state.md#4500)
 GetGlobalState.proto
---------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取全局状态
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
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
    	required int32 marketHK = 1; //Qot_Common.QotMarketState,港股主板市场状态
    	required int32 marketUS = 2; //Qot_Common.QotMarketState,美股 Nasdaq 市场状态 
    	required int32 marketSH = 3; //Qot_Common.QotMarketState,沪市状态 
    	required int32 marketSZ = 4; //Qot_Common.QotMarketState,深市状态 
    	required int32 marketHKFuture = 5; //Qot_Common.QotMarketState,港股期货市场状态 
    	optional int32 marketUSFuture = 15; //Qot_Common.QotMarketState,美国期货市场状态
    	optional int32 marketSGFuture = 17; //Qot_Common.QotMarketState,新加坡期货市场状态 
    	optional int32 marketJPFuture = 18; //Qot_Common.QotMarketState,日本期货市场状态 
    	required bool qotLogined = 6; //是否登录行情服务器
    	required bool trdLogined = 7; //是否登录交易服务器
    	required int32 serverVer = 8; //版本号
    	required int32 serverBuildNo = 9; //buildNo
    	required int64 time = 10; //当前服务器时间
    	optional double localTime = 11; //当前本地时间
    	optional Common.ProgramStatus programStatus = 12; //当前程序状态
    	optional string qotSvrIpAddr = 13;
    	optional string trdSvrIpAddr = 14;
    	optional uint64 connID = 16; //此连接的连接 ID，连接的唯一标识
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

> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    1002
    

`uint GetGlobalState(GetGlobalState.Request req)`  
`void OnReply_GetGlobalState(FTAPI_Conn client, uint nSerialNo, GetGlobalState.Response rsp)`

*   **介绍**
    
    获取全局状态
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
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
    	required int32 marketHK = 1; //Qot_Common.QotMarketState,港股主板市场状态
    	required int32 marketUS = 2; //Qot_Common.QotMarketState,美股 Nasdaq 市场状态 
    	required int32 marketSH = 3; //Qot_Common.QotMarketState,沪市状态 
    	required int32 marketSZ = 4; //Qot_Common.QotMarketState,深市状态 
    	required int32 marketHKFuture = 5; //Qot_Common.QotMarketState,港股期货市场状态 
    	optional int32 marketUSFuture = 15; //Qot_Common.QotMarketState,美国期货市场状态
    	optional int32 marketSGFuture = 17; //Qot_Common.QotMarketState,新加坡期货市场状态 
    	optional int32 marketJPFuture = 18; //Qot_Common.QotMarketState,日本期货市场状态 
    	required bool qotLogined = 6; //是否登录行情服务器
    	required bool trdLogined = 7; //是否登录交易服务器
    	required int32 serverVer = 8; //版本号
    	required int32 serverBuildNo = 9; //buildNo
    	required int64 time = 10; //当前服务器时间
    	optional double localTime = 11; //当前本地时间
    	optional Common.ProgramStatus programStatus = 12; //当前程序状态
    	optional string qotSvrIpAddr = 13;
    	optional string trdSvrIpAddr = 14;
    	optional uint64 connID = 16; //此连接的连接 ID，连接的唯一标识
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

> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    public class Program : FTSPI_Qot, FTSPI_Conn
        {
            FTAPI_Qot qot = new FTAPI_Qot();
    
            public Program()
            {
                qot.SetClientInfo("FTAPI4NET_Sample", 1);  //设置客户端信息
                qot.SetConnCallback(this);  //设置连接回调
                qot.SetQotCallback(this);   //设置交易回调
            }
    
            public void Start()
            {
                qot.InitConnect("127.0.0.1", (ushort)11111, false); //开始连接 OpenD, 连接结果通过 OnInitConnect 处理
            }
    
    
            public void OnInitConnect(FTAPI_Conn client, long errCode, String desc)
            {
                Console.Write("Qot onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
                if (errCode != 0) //连接失败
                    return;
    
                //连接成功，可以发起请求
                GetGlobalState.C2S c2s = GetGlobalState.C2S.CreateBuilder()
                        .SetUserID(0)
                        .Build();
                GetGlobalState.Request req = GetGlobalState.Request.CreateBuilder().SetC2S(c2s).Build();
                uint seqNo = qot.GetGlobalState(req);
                Console.WriteLine("Send GetGlobalState: {0}", seqNo1);
            }
    
            //断线后会调用此回调
            public void OnDisconnect(FTAPI_Conn client, long errCode)
            {
                Console.Write("Qot onDisConnect: {0}\n", errCode);
                qot.Close(); //释放底层资源
            }
    
            public void OnReply_GetGlobalState(FTAPI_Conn client, uint nSerialNo, GetGlobalState.Response rsp)
            {
                Console.Write("Reply: GetGlobalState: {0}\n", nSerialNo);
                Console.Write("marketHK: {0}, programStatus: {1} \n", rsp.S2C.MarketHK, rsp.S2C.ProgramStatus);
            }
    
            public static void Main(String[] args)
            {
                FTAPI.Init(); //初始化环境，程序启动时调用1次
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

**Output**

    Qot onInitConnect: ret=0 desc= connID=6826777317655602340
    Send GetGlobalState: 3
    Reply: GetGlobalState: 3
    marketHK: 5, programStatus: type: ProgramStatusType_Ready
    

1  
2  
3  
4  

`int getGlobalState(GetGlobalState.Request req);`  
`void onReply_GetGlobalState(FTAPI_Conn client, int nSerialNo, GetGlobalState.Response rsp)`

*   **介绍**
    
    获取全局状态
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
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
    	required int32 marketHK = 1; //Qot_Common.QotMarketState,港股主板市场状态
    	required int32 marketUS = 2; //Qot_Common.QotMarketState,美股 Nasdaq 市场状态 
    	required int32 marketSH = 3; //Qot_Common.QotMarketState,沪市状态 
    	required int32 marketSZ = 4; //Qot_Common.QotMarketState,深市状态 
    	required int32 marketHKFuture = 5; //Qot_Common.QotMarketState,港股期货市场状态 
    	optional int32 marketUSFuture = 15; //Qot_Common.QotMarketState,美国期货市场状态
    	optional int32 marketSGFuture = 17; //Qot_Common.QotMarketState,新加坡期货市场状态 
    	optional int32 marketJPFuture = 18; //Qot_Common.QotMarketState,日本期货市场状态 
    	required bool qotLogined = 6; //是否登录行情服务器
    	required bool trdLogined = 7; //是否登录交易服务器
    	required int32 serverVer = 8; //版本号
    	required int32 serverBuildNo = 9; //buildNo
    	required int64 time = 10; //当前服务器时间
    	optional double localTime = 11; //当前本地时间
    	optional Common.ProgramStatus programStatus = 12; //当前程序状态
    	optional string qotSvrIpAddr = 13;
    	optional string trdSvrIpAddr = 14;
    	optional uint64 connID = 16; //此连接的连接 ID，连接的唯一标识
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
            qot.setQotSpi(this);   //设置行情回调
        }
    
        public void start() throws IOException {
            qot.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Qot onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
            if (errCode != 0)
                return;
    
            GetGlobalState.C2S c2s = GetGlobalState.C2S.newBuilder()
                    .setUserID(0)
                    .build();
            GetGlobalState.Request req = GetGlobalState.Request.newBuilder().setC2S(c2s).build();
            qot.getGlobalState(req);
            System.out.printf("Send QotGetGlobalState: %d\n", seqNo);
        }
    
        @Override
        public void onReply_GetGlobalState(FTAPI_Conn client, int nSerialNo, GetGlobalState.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetGlobalState failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetGlobalState: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        public static void main(String[] args) throws IOException {
            FTAPI.init();
            QotDemo qot = new QotDemo();
            qot.start();
    
            while (true) {
                try {
                    Thread.sleep(1000 * 60);
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

    Send QotGetGlobalState: 2
    Receive QotGetGlobalState: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "marketHK": 6,
        "marketUS": 8,
        "marketSH": 6,
        "marketSZ": 6,
        "marketHKFuture": 17,
        "qotLogined": true,
        "trdLogined": true,
        "serverVer": 505,
        "serverBuildNo": 1707,
        "time": "1624523642",
        "localTime": 1.624523642858697E9,
        "programStatus": {
          "type": "ProgramStatusType_Ready"
        },
        "qotSvrIpAddr": "119.29.43.101",
        "trdSvrIpAddr": "119.29.43.101",
        "marketUSFuture": 23,
        "connID": "6813746013095493825",
        "marketSGFuture": 13,
        "marketJPFuture": 13
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

`Futu::u32_t GetGlobalState(const GetGlobalState::Request &stReq);`  
`virtual void OnReply_GetGlobalState(Futu::u32_t nSerialNo, const GetGlobalState::Response &stRsp) = 0;`

*   **介绍**
    
    获取全局状态
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
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
    	required int32 marketHK = 1; //Qot_Common.QotMarketState,港股主板市场状态
    	required int32 marketUS = 2; //Qot_Common.QotMarketState,美股 Nasdaq 市场状态 
    	required int32 marketSH = 3; //Qot_Common.QotMarketState,沪市状态 
    	required int32 marketSZ = 4; //Qot_Common.QotMarketState,深市状态 
    	required int32 marketHKFuture = 5; //Qot_Common.QotMarketState,港股期货市场状态 
    	optional int32 marketUSFuture = 15; //Qot_Common.QotMarketState,美国期货市场状态
    	optional int32 marketSGFuture = 17; //Qot_Common.QotMarketState,新加坡期货市场状态 
    	optional int32 marketJPFuture = 18; //Qot_Common.QotMarketState,日本期货市场状态 
    	required bool qotLogined = 6; //是否登录行情服务器
    	required bool trdLogined = 7; //是否登录交易服务器
    	required int32 serverVer = 8; //版本号
    	required int32 serverBuildNo = 9; //buildNo
    	required int64 time = 10; //当前服务器时间
    	optional double localTime = 11; //当前本地时间
    	optional Common.ProgramStatus programStatus = 12; //当前程序状态
    	optional string qotSvrIpAddr = 13;
    	optional string trdSvrIpAddr = 14;
    	optional uint64 connID = 16; //此连接的连接 ID，连接的唯一标识
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
    		GetGlobalState::Request req;
    		GetGlobalState::C2S *c2s = req.mutable_c2s();
    		c2s->set_userid(0);
    
            m_GetGlobalStateSerialNo = m_pQotApi->GetGlobalState(req);
            cout << "Request GetGlobalState SerialNo: " << m_GetGlobalStateSerialNo << endl;
    	}
    
    	virtual void OnReply_GetGlobalState(Futu::u32_t nSerialNo, const GetGlobalState::Response &stRsp){
            if(nSerialNo == m_GetGlobalStateSerialNo)
            {
                cout << "OnReply_GetGlobalState SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetGlobalStateSerialNo;
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

*   **Output**

    connect
    Request GetGlobalState SerialNo: 4
    OnReply_GetGlobalState SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "marketHK": 6,
      "marketUS": 8,
      "marketSH": 6,
      "marketSZ": 6,
      "marketHKFuture": 15,
      "qotLogined": true,
      "trdLogined": true,
      "serverVer": 504,
      "serverBuildNo": 1608,
      "time": "1623226833",
      "localTime": 1623226833.8509541,
      "programStatus": {
       "type": 10
      },
      "qotSvrIpAddr": "106.55.66.8",
      "trdSvrIpAddr": "106.55.66.8",
      "marketUSFuture": 23,
      "connID": "6808306802122125817",
      "marketSGFuture": 13,
      "marketJPFuture": 13
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

`GetGlobalState(req);`

*   **介绍**
    
    获取全局状态
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
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
    	required int32 marketHK = 1; //Qot_Common.QotMarketState,港股主板市场状态
    	required int32 marketUS = 2; //Qot_Common.QotMarketState,美股 Nasdaq 市场状态 
    	required int32 marketSH = 3; //Qot_Common.QotMarketState,沪市状态 
    	required int32 marketSZ = 4; //Qot_Common.QotMarketState,深市状态 
    	required int32 marketHKFuture = 5; //Qot_Common.QotMarketState,港股期货市场状态 
    	optional int32 marketUSFuture = 15; //Qot_Common.QotMarketState,美国期货市场状态
    	optional int32 marketSGFuture = 17; //Qot_Common.QotMarketState,新加坡期货市场状态 
    	optional int32 marketJPFuture = 18; //Qot_Common.QotMarketState,日本期货市场状态 
    	required bool qotLogined = 6; //是否登录行情服务器
    	required bool trdLogined = 7; //是否登录交易服务器
    	required int32 serverVer = 8; //版本号
    	required int32 serverBuildNo = 9; //buildNo
    	required int64 time = 10; //当前服务器时间
    	optional double localTime = 11; //当前本地时间
    	optional Common.ProgramStatus programStatus = 12; //当前程序状态
    	optional string qotSvrIpAddr = 13;
    	optional string trdSvrIpAddr = 14;
    	optional uint64 connID = 16; //此连接的连接 ID，连接的唯一标识
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

> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function GetGlobalState(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        userID: 0,
                    },
                };
    
                websocket.GetGlobalState(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("GlobalState: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    GlobalState: errCode 0, retMsg , retType 0
    {
      "marketHK": 6,
      "marketUS": 8,
      "marketSH": 6,
      "marketSZ": 6,
      "marketHKFuture": 15,
      "qotLogined": true,
      "trdLogined": true,
      "serverVer": 507,
      "serverBuildNo": 1908,
      "time": "1631261759",
      "localTime": 1631261759.095172,
      "programStatus": {
        "type": "ProgramStatusType_Ready"
      },
      "qotSvrIpAddr": "119.29.48.17",
      "trdSvrIpAddr": "106.55.66.8",
      "marketUSFuture": 23,
      "connID": "6842007721202028582",
      "marketSGFuture": 13,
      "marketJPFuture": 13
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

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_global_state()`

*   **介绍**
    
    获取全局状态
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | dict | 当 ret == RET\_OK 时，返回全局状态 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   全局状态字典格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | market\_sz | [MarketState](./quote_quote.md#1252) | 深圳市场状态 |
        | market\_sh | [MarketState](./quote_quote.md#1252) | 上海市场状态 |
        | market\_hk | [MarketState](./quote_quote.md#1252) | 香港市场状态 |
        | market\_hkfuture | [MarketState](./quote_quote.md#1252) | 香港期货市场状态<br>(ℹ️ 不同品种的交易时间存在差异，建议使用 [get\_market\_state](./quote_get-market-state.md))<br> 接口获取指定品种的市场状态 |
        | market\_usfuture | [MarketState](./quote_quote.md#1252) | 美国期货市场状态<br>(ℹ️ 不同品种的交易时间存在差异，建议使用 [get\_market\_state](./quote_get-market-state.md))<br> 接口获取指定品种的市场状态 |
        | market\_us | [MarketState](./quote_quote.md#1252) | 美国市场状态<br>(ℹ️ 不同品种的交易时间存在差异，建议使用 [get\_market\_state](./quote_get-market-state.md))<br> 接口获取指定品种的市场状态 |
        | market\_sgfuture | [MarketState](./quote_quote.md#1252) | 新加坡期货市场状态<br>(ℹ️ 不同品种的交易时间存在差异，建议使用 [get\_market\_state](./quote_get-market-state.md))<br> 接口获取指定品种的市场状态 |
        | market\_jpfuture | [MarketState](./quote_quote.md#1252) | 日本期货市场状态 |
        | server\_ver | str | OpenD 版本号 |
        | trd\_logined | bool | True：已登录交易服务器，False：未登录交易服务器 |
        | qot\_logined | bool | True：已登录行情服务器，False：未登录行情服务器 |
        | timestamp | str | 当前格林威治时间戳<br>(ℹ️ 单位：秒) |
        | local\_timestamp | float | OpenD 运行机器的当前时间戳<br>(ℹ️ 单位：秒) |
        | program\_status\_type | [ProgramStatusType](./ftapi_common.md#6427) | 当前状态 |
        | program\_status\_desc | str | 额外描述 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    print(quote_ctx.get_global_state())
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  
4  

*   **Output**

    (0, {'market_sz': 'MORNING', 'market_us': 'AFTER_HOURS_END', 'market_sh': 'MORNING', 'market_hk': 'MORNING', 'market_hkfuture': 'FUTURE_DAY_OPEN', 'market_usfuture': 'FUTURE_OPEN', 'market_sgfuture': 'FUTURE_DAY_OPEN', 'market_jpfuture': 'FUTURE_DAY_OPEN', 'server_ver': '504', 'trd_logined': True, 'timestamp': '1620962951', 'qot_logined': True, 'local_timestamp': 1620962951.047128, 'program_status_type': 'READY', 'program_status_desc': ''})
    

1  

[#](./quote_get-global-state.md#4500-2)
 GetGlobalState.proto
-----------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取全局状态
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
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
    	required int32 marketHK = 1; //Qot_Common.QotMarketState,港股主板市场状态
    	required int32 marketUS = 2; //Qot_Common.QotMarketState,美股 Nasdaq 市场状态 
    	required int32 marketSH = 3; //Qot_Common.QotMarketState,沪市状态 
    	required int32 marketSZ = 4; //Qot_Common.QotMarketState,深市状态 
    	required int32 marketHKFuture = 5; //Qot_Common.QotMarketState,港股期货市场状态 
    	optional int32 marketUSFuture = 15; //Qot_Common.QotMarketState,美国期货市场状态
    	optional int32 marketSGFuture = 17; //Qot_Common.QotMarketState,新加坡期货市场状态 
    	optional int32 marketJPFuture = 18; //Qot_Common.QotMarketState,日本期货市场状态 
    	required bool qotLogined = 6; //是否登录行情服务器
    	required bool trdLogined = 7; //是否登录交易服务器
    	required int32 serverVer = 8; //版本号
    	required int32 serverBuildNo = 9; //buildNo
    	required int64 time = 10; //当前服务器时间
    	optional double localTime = 11; //当前本地时间
    	optional Common.ProgramStatus programStatus = 12; //当前程序状态
    	optional string qotSvrIpAddr = 13;
    	optional string trdSvrIpAddr = 14;
    	optional uint64 connID = 16; //此连接的连接 ID，连接的唯一标识
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

> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    1002
    

`uint GetGlobalState(GetGlobalState.Request req)`  
`void OnReply_GetGlobalState(MMAPI_Conn client, uint nSerialNo, GetGlobalState.Response rsp)`

*   **介绍**
    
    获取全局状态
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
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
    	required int32 marketHK = 1; //Qot_Common.QotMarketState,港股主板市场状态
    	required int32 marketUS = 2; //Qot_Common.QotMarketState,美股 Nasdaq 市场状态 
    	required int32 marketSH = 3; //Qot_Common.QotMarketState,沪市状态 
    	required int32 marketSZ = 4; //Qot_Common.QotMarketState,深市状态 
    	required int32 marketHKFuture = 5; //Qot_Common.QotMarketState,港股期货市场状态 
    	optional int32 marketUSFuture = 15; //Qot_Common.QotMarketState,美国期货市场状态
    	optional int32 marketSGFuture = 17; //Qot_Common.QotMarketState,新加坡期货市场状态 
    	optional int32 marketJPFuture = 18; //Qot_Common.QotMarketState,日本期货市场状态 
    	required bool qotLogined = 6; //是否登录行情服务器
    	required bool trdLogined = 7; //是否登录交易服务器
    	required int32 serverVer = 8; //版本号
    	required int32 serverBuildNo = 9; //buildNo
    	required int64 time = 10; //当前服务器时间
    	optional double localTime = 11; //当前本地时间
    	optional Common.ProgramStatus programStatus = 12; //当前程序状态
    	optional string qotSvrIpAddr = 13;
    	optional string trdSvrIpAddr = 14;
    	optional uint64 connID = 16; //此连接的连接 ID，连接的唯一标识
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

> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    public class Program : MMSPI_Qot, MMSPI_Conn
        {
            MMAPI_Qot qot = new MMAPI_Qot();
    
            public Program()
            {
                qot.SetClientInfo("MMAPI4NET_Sample", 1);  //设置客户端信息
                qot.SetConnCallback(this);  //设置连接回调
                qot.SetQotCallback(this);   //设置交易回调
            }
    
            public void Start()
            {
                qot.InitConnect("127.0.0.1", (ushort)11111, false); //开始连接 OpenD, 连接结果通过 OnInitConnect 处理
            }
    
    
            public void OnInitConnect(MMAPI_Conn client, long errCode, String desc)
            {
                Console.Write("Qot onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
                if (errCode != 0) //连接失败
                    return;
    
                //连接成功，可以发起请求
                GetGlobalState.C2S c2s = GetGlobalState.C2S.CreateBuilder()
                        .SetUserID(0)
                        .Build();
                GetGlobalState.Request req = GetGlobalState.Request.CreateBuilder().SetC2S(c2s).Build();
                uint seqNo = qot.GetGlobalState(req);
                Console.WriteLine("Send GetGlobalState: {0}", seqNo1);
            }
    
            //断线后会调用此回调
            public void OnDisconnect(MMAPI_Conn client, long errCode)
            {
                Console.Write("Qot onDisConnect: {0}\n", errCode);
                qot.Close(); //释放底层资源
            }
    
            public void OnReply_GetGlobalState(MMAPI_Conn client, uint nSerialNo, GetGlobalState.Response rsp)
            {
                Console.Write("Reply: GetGlobalState: {0}\n", nSerialNo);
                Console.Write("marketHK: {0}, programStatus: {1} \n", rsp.S2C.MarketHK, rsp.S2C.ProgramStatus);
            }
    
            public static void Main(String[] args)
            {
                MMAPI.Init(); //初始化环境，程序启动时调用1次
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

**Output**

    Qot onInitConnect: ret=0 desc= connID=6826777317655602340
    Send GetGlobalState: 3
    Reply: GetGlobalState: 3
    marketHK: 5, programStatus: type: ProgramStatusType_Ready
    

1  
2  
3  
4  

`int getGlobalState(GetGlobalState.Request req);`  
`void onReply_GetGlobalState(MMAPI_Conn client, int nSerialNo, GetGlobalState.Response rsp)`

*   **介绍**
    
    获取全局状态
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
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
    	required int32 marketHK = 1; //Qot_Common.QotMarketState,港股主板市场状态
    	required int32 marketUS = 2; //Qot_Common.QotMarketState,美股 Nasdaq 市场状态 
    	required int32 marketSH = 3; //Qot_Common.QotMarketState,沪市状态 
    	required int32 marketSZ = 4; //Qot_Common.QotMarketState,深市状态 
    	required int32 marketHKFuture = 5; //Qot_Common.QotMarketState,港股期货市场状态 
    	optional int32 marketUSFuture = 15; //Qot_Common.QotMarketState,美国期货市场状态
    	optional int32 marketSGFuture = 17; //Qot_Common.QotMarketState,新加坡期货市场状态 
    	optional int32 marketJPFuture = 18; //Qot_Common.QotMarketState,日本期货市场状态 
    	required bool qotLogined = 6; //是否登录行情服务器
    	required bool trdLogined = 7; //是否登录交易服务器
    	required int32 serverVer = 8; //版本号
    	required int32 serverBuildNo = 9; //buildNo
    	required int64 time = 10; //当前服务器时间
    	optional double localTime = 11; //当前本地时间
    	optional Common.ProgramStatus programStatus = 12; //当前程序状态
    	optional string qotSvrIpAddr = 13;
    	optional string trdSvrIpAddr = 14;
    	optional uint64 connID = 16; //此连接的连接 ID，连接的唯一标识
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
            qot.setQotSpi(this);   //设置行情回调
        }
    
        public void start() throws IOException {
            qot.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Qot onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
            if (errCode != 0)
                return;
    
            GetGlobalState.C2S c2s = GetGlobalState.C2S.newBuilder()
                    .setUserID(0)
                    .build();
            GetGlobalState.Request req = GetGlobalState.Request.newBuilder().setC2S(c2s).build();
            qot.getGlobalState(req);
            System.out.printf("Send QotGetGlobalState: %d\n", seqNo);
        }
    
        @Override
        public void onReply_GetGlobalState(MMAPI_Conn client, int nSerialNo, GetGlobalState.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetGlobalState failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetGlobalState: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        public static void main(String[] args) throws IOException {
            MMAPI.init();
            QotDemo qot = new QotDemo();
            qot.start();
    
            while (true) {
                try {
                    Thread.sleep(1000 * 60);
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

    Send QotGetGlobalState: 2
    Receive QotGetGlobalState: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "marketHK": 6,
        "marketUS": 8,
        "marketSH": 6,
        "marketSZ": 6,
        "marketHKFuture": 17,
        "qotLogined": true,
        "trdLogined": true,
        "serverVer": 505,
        "serverBuildNo": 1707,
        "time": "1624523642",
        "localTime": 1.624523642858697E9,
        "programStatus": {
          "type": "ProgramStatusType_Ready"
        },
        "qotSvrIpAddr": "119.29.43.101",
        "trdSvrIpAddr": "119.29.43.101",
        "marketUSFuture": 23,
        "connID": "6813746013095493825",
        "marketSGFuture": 13,
        "marketJPFuture": 13
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

`moomoo::u32_t GetGlobalState(const GetGlobalState::Request &stReq);`  
`virtual void OnReply_GetGlobalState(moomoo::u32_t nSerialNo, const GetGlobalState::Response &stRsp) = 0;`

*   **介绍**
    
    获取全局状态
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
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
    	required int32 marketHK = 1; //Qot_Common.QotMarketState,港股主板市场状态
    	required int32 marketUS = 2; //Qot_Common.QotMarketState,美股 Nasdaq 市场状态 
    	required int32 marketSH = 3; //Qot_Common.QotMarketState,沪市状态 
    	required int32 marketSZ = 4; //Qot_Common.QotMarketState,深市状态 
    	required int32 marketHKFuture = 5; //Qot_Common.QotMarketState,港股期货市场状态 
    	optional int32 marketUSFuture = 15; //Qot_Common.QotMarketState,美国期货市场状态
    	optional int32 marketSGFuture = 17; //Qot_Common.QotMarketState,新加坡期货市场状态 
    	optional int32 marketJPFuture = 18; //Qot_Common.QotMarketState,日本期货市场状态 
    	required bool qotLogined = 6; //是否登录行情服务器
    	required bool trdLogined = 7; //是否登录交易服务器
    	required int32 serverVer = 8; //版本号
    	required int32 serverBuildNo = 9; //buildNo
    	required int64 time = 10; //当前服务器时间
    	optional double localTime = 11; //当前本地时间
    	optional Common.ProgramStatus programStatus = 12; //当前程序状态
    	optional string qotSvrIpAddr = 13;
    	optional string trdSvrIpAddr = 14;
    	optional uint64 connID = 16; //此连接的连接 ID，连接的唯一标识
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
    		GetGlobalState::Request req;
    		GetGlobalState::C2S *c2s = req.mutable_c2s();
    		c2s->set_userid(0);
    
            m_GetGlobalStateSerialNo = m_pQotApi->GetGlobalState(req);
            cout << "Request GetGlobalState SerialNo: " << m_GetGlobalStateSerialNo << endl;
    	}
    
    	virtual void OnReply_GetGlobalState(moomoo::u32_t nSerialNo, const GetGlobalState::Response &stRsp){
            if(nSerialNo == m_GetGlobalStateSerialNo)
            {
                cout << "OnReply_GetGlobalState SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetGlobalStateSerialNo;
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

*   **Output**

    connect
    Request GetGlobalState SerialNo: 4
    OnReply_GetGlobalState SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "marketHK": 6,
      "marketUS": 8,
      "marketSH": 6,
      "marketSZ": 6,
      "marketHKFuture": 15,
      "qotLogined": true,
      "trdLogined": true,
      "serverVer": 504,
      "serverBuildNo": 1608,
      "time": "1623226833",
      "localTime": 1623226833.8509541,
      "programStatus": {
       "type": 10
      },
      "qotSvrIpAddr": "106.55.66.8",
      "trdSvrIpAddr": "106.55.66.8",
      "marketUSFuture": 23,
      "connID": "6808306802122125817",
      "marketSGFuture": 13,
      "marketJPFuture": 13
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

`GetGlobalState(req);`

*   **介绍**
    
    获取全局状态
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
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
    	required int32 marketHK = 1; //Qot_Common.QotMarketState,港股主板市场状态
    	required int32 marketUS = 2; //Qot_Common.QotMarketState,美股 Nasdaq 市场状态 
    	required int32 marketSH = 3; //Qot_Common.QotMarketState,沪市状态 
    	required int32 marketSZ = 4; //Qot_Common.QotMarketState,深市状态 
    	required int32 marketHKFuture = 5; //Qot_Common.QotMarketState,港股期货市场状态 
    	optional int32 marketUSFuture = 15; //Qot_Common.QotMarketState,美国期货市场状态
    	optional int32 marketSGFuture = 17; //Qot_Common.QotMarketState,新加坡期货市场状态 
    	optional int32 marketJPFuture = 18; //Qot_Common.QotMarketState,日本期货市场状态 
    	required bool qotLogined = 6; //是否登录行情服务器
    	required bool trdLogined = 7; //是否登录交易服务器
    	required int32 serverVer = 8; //版本号
    	required int32 serverBuildNo = 9; //buildNo
    	required int64 time = 10; //当前服务器时间
    	optional double localTime = 11; //当前本地时间
    	optional Common.ProgramStatus programStatus = 12; //当前程序状态
    	optional string qotSvrIpAddr = 13;
    	optional string trdSvrIpAddr = 14;
    	optional uint64 connID = 16; //此连接的连接 ID，连接的唯一标识
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

> *   市场状态枚举参见 [QotMarketState](./quote_quote.md#1252)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function GetGlobalState(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        userID: 0,
                    },
                };
    
                websocket.GetGlobalState(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("GlobalState: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    GlobalState: errCode 0, retMsg , retType 0
    {
      "marketHK": 6,
      "marketUS": 8,
      "marketSH": 6,
      "marketSZ": 6,
      "marketHKFuture": 15,
      "qotLogined": true,
      "trdLogined": true,
      "serverVer": 507,
      "serverBuildNo": 1908,
      "time": "1631261759",
      "localTime": 1631261759.095172,
      "programStatus": {
        "type": "ProgramStatusType_Ready"
      },
      "qotSvrIpAddr": "119.29.48.17",
      "trdSvrIpAddr": "106.55.66.8",
      "marketUSFuture": 23,
      "connID": "6842007721202028582",
      "marketSGFuture": 13,
      "marketJPFuture": 13
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

← [获取 IPO 信息](./quote_get-ipo-list.md) [获取交易日历](./quote_request-trading-days.md)
 →

[获取全局市场状态](./quote_get-global-state.md)