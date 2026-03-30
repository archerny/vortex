# 获取资金分布 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quote/get-capital-distribution.html

[#](https://openapi.futunn.com/futu-api-doc/quote/get-capital-distribution.html#2320)
 获取资金分布
=============================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_capital_distribution(stock_code)`

*   **介绍**
    
    获取资金分布
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | stock\_code | str | 股票代码 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回股票资金分布数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   资金分布数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | capital\_in\_super | float | 流入资金额度，特大单 |
        | capital\_in\_big | float | 流入资金额度，大单 |
        | capital\_in\_mid | float | 流入资金额度，中单 |
        | capital\_in\_small | float | 流入资金额度，小单 |
        | capital\_out\_super | float | 流出资金额度，特大单 |
        | capital\_out\_big | float | 流出资金额度，大单 |
        | capital\_out\_mid | float | 流出资金额度，中单 |
        | capital\_out\_small | float | 流出资金额度，小单 |
        | update\_time | str | 更新时间字符串<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd HH:mm:ss |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_capital_distribution("HK.00700")
    if ret == RET_OK:
        print(data)
        print(data['capital_in_big'][0])    # 取第一条的流入资金额度，大单
        print(data['capital_in_big'].values.tolist())   # 转为 list
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
11  

*   **Output**

       capital_in_super  capital_in_big  ...  capital_out_small          update_time
    0      2.261085e+09    2.141964e+09  ...       2.887413e+09  2022-06-08 15:59:59
    
    [1 rows x 9 columns]
    2141963720.0
    [2141963720.0]
    

1  
2  
3  
4  
5  
6  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-capital-distribution.html#753)
 Qot\_GetCapitalDistribution.proto
-----------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取资金分布
    
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	optional double capitalInSuper = 9; // 流入资金额度，特大单
    	required double capitalInBig = 1; // 流入资金额度，大单
    	required double capitalInMid = 2; // 流入资金额度，中单
    	required double capitalInSmall = 3; // 流入资金额度，小单
    	optional double capitalOutSuper = 10; // 流出资金额度，特大单
    	required double capitalOutBig = 4; // 流出资金额度，大单
    	required double capitalOutMid = 5; // 流出资金额度，中单
    	required double capitalOutSmall = 6; // 流出资金额度，小单
    	optional string updateTime = 7; // 更新时间字符串
    	optional double updateTimestamp = 8; // 更新时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3212
    

`uint GetCapitalDistribution(QotGetCapitalDistribution.Request req);`  
`virtual void OnReply_GetCapitalDistribution(FTAPI_Conn client, uint nSerialNo, QotGetCapitalDistribution.Response rsp);`

*   **介绍**

获取资金分布

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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	optional double capitalInSuper = 9; // 流入资金额度，特大单
    	required double capitalInBig = 1; // 流入资金额度，大单
    	required double capitalInMid = 2; // 流入资金额度，中单
    	required double capitalInSmall = 3; // 流入资金额度，小单
    	optional double capitalOutSuper = 10; // 流出资金额度，特大单
    	required double capitalOutBig = 4; // 流出资金额度，大单
    	required double capitalOutMid = 5; // 流出资金额度，中单
    	required double capitalOutSmall = 6; // 流出资金额度，小单
    	optional string updateTime = 7; // 更新时间字符串
    	optional double updateTimestamp = 8; // 更新时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : FTSPI_Qot, FTSPI_Conn
    {
    	FTAPI_Qot qot = new FTAPI_Qot();
    
    	public Program()
    	{
    		qot.SetClientInfo("csharp", 1);  //设置客户端信息
    		qot.SetConnCallback(this);  //设置连接回调
    		qot.SetQotCallback(this);   //设置交易回调
    	}
    
    	public void Start()
    	{
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
    		QotGetCapitalDistribution.C2S c2s = QotGetCapitalDistribution.C2S.CreateBuilder()
    				.SetSecurity(sec)
    				.Build();
    		QotGetCapitalDistribution.Request req = QotGetCapitalDistribution.Request.CreateBuilder().SetC2S(c2s).Build();
    		uint seqNo = qot.GetCapitalDistribution(req);
    		Console.Write("Send QotGetCapitalDistribution: {0}\n", seqNo);
    	}
    
    
    	public void OnDisconnect(FTAPI_Conn client, long errCode)
    	{
    		Console.Write("Qot onDisConnect: {0}\n", errCode);
    	}
    
    	public void OnReply_GetCapitalDistribution(Futu.OpenApi.FTAPI_Conn client, uint nSerialNo, QotGetCapitalDistribution.Response rsp)
    	{
    		Console.Write("Reply: QotGetCapitalDistribution: {0}  {1}\n", nSerialNo, rsp.ToString());
    		Console.Write("inFlow: {0}\n", rsp.S2C.CapitalInBig);
    	}
    
    	public static void Main(String[] args)
    	{
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6939769465241305991
    Send QotGetCapitalDistribution: 3
    Reply: QotGetCapitalDistribution: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      capitalInBig: 299700820
      capitalInMid: 521403800
      capitalInSmall: 740895620
      capitalOutBig: 332038880
      capitalOutMid: 483401260
      capitalOutSmall: 682124080
      updateTime: "2022-06-07 10:46:02"
      updateTimestamp: 1654569962
      capitalInSuper: 230158560
      capitalOutSuper: 286481380
    }
    
    inFlow: 299700820
    

1  
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

`int getCapitalDistribution(QotGetCapitalDistribution.Request req);`  
`void onReply_GetCapitalDistribution(FTAPI_Conn client, int nSerialNo, QotGetCapitalDistribution.Response rsp);`

*   **介绍**

获取资金分布

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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	optional double capitalInSuper = 9; // 流入资金额度，特大单
    	required double capitalInBig = 1; // 流入资金额度，大单
    	required double capitalInMid = 2; // 流入资金额度，中单
    	required double capitalInSmall = 3; // 流入资金额度，小单
    	optional double capitalOutSuper = 10; // 流出资金额度，特大单
    	required double capitalOutBig = 4; // 流出资金额度，大单
    	required double capitalOutMid = 5; // 流出资金额度，中单
    	required double capitalOutSmall = 6; // 流出资金额度，小单
    	optional string updateTime = 7; // 更新时间字符串
    	optional double updateTimestamp = 8; // 更新时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    class QotDemo implements FTSPI_Qot, FTSPI_Conn {
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
            QotGetCapitalDistribution.C2S c2s = QotGetCapitalDistribution.C2S.newBuilder()
                    .setSecurity(sec)
                    .build();
            QotGetCapitalDistribution.Request req = QotGetCapitalDistribution.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getCapitalDistribution(req);
            System.out.printf("Send QotGetCapitalDistribution: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetCapitalDistribution(FTAPI_Conn client, int nSerialNo, QotGetCapitalDistribution.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetCapitalDistribution failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetCapitalDistribution: %s\n", json);
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

*   **Output**

    Send QotGetCapitalDistribution: 2
    Receive QotGetCapitalDistribution: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "capitalInBig": 8.6653274E8,
        "capitalInMid": 7.6876998E8,
        "capitalInSmall": 1.02038782E9,
        "capitalOutBig": 4.4806512E8,
        "capitalOutMid": 5.0682792E8,
        "capitalOutSmall": 8.1260248E8,
        "updateTime": "2022-06-08 10:44:34",
        "updateTimestamp": 1.654656274E9,
        "capitalInSuper": 8.2496766E8,
        "capitalOutSuper": 2.6721714E8
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

`Futu::u32_t GetCapitalDistribution(const Qot_GetCapitalDistribution::Request &stReq);`  
`virtual void OnReply_GetCapitalDistribution(Futu::u32_t nSerialNo, const Qot_GetCapitalDistribution::Response &stRsp) = 0;`

*   **介绍**
    
    获取资金分布
    
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	optional double capitalInSuper = 9; // 流入资金额度，特大单
    	required double capitalInBig = 1; // 流入资金额度，大单
    	required double capitalInMid = 2; // 流入资金额度，中单
    	required double capitalInSmall = 3; // 流入资金额度，小单
    	optional double capitalOutSuper = 10; // 流出资金额度，特大单
    	required double capitalOutBig = 4; // 流出资金额度，大单
    	required double capitalOutMid = 5; // 流出资金额度，中单
    	required double capitalOutSmall = 6; // 流出资金额度，小单
    	optional string updateTime = 7; // 更新时间字符串
    	optional double updateTimestamp = 8; // 更新时间戳
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
    		Qot_GetCapitalDistribution::Request req;
    		Qot_GetCapitalDistribution::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetCapitalDistributionSerialNo = m_pQotApi->GetCapitalDistribution(req);
            cout << "Request GetCapitalDistribution SerialNo: " << m_GetCapitalDistributionSerialNo << endl;
    	}
    
    	virtual void OnReply_GetCapitalDistribution(Futu::u32_t nSerialNo, const Qot_GetCapitalDistribution::Response &stRsp){
            if(nSerialNo == m_GetCapitalDistributionSerialNo)
            {
                cout << "OnReply_GetCapitalDistribution SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetCapitalDistributionSerialNo;
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
    Request GetCapitalDistribution SerialNo: 3
    OnReply_GetCapitalDistribution SerialNo: 3
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "capitalInBig": 859479380,
      "capitalInMid": 763181580,
      "capitalInSmall": 1011289940,
      "capitalOutBig": 444405720,
      "capitalOutMid": 503977520,
      "capitalOutSmall": 807438760,
      "updateTime": "2022-06-08 10:44:21",
      "updateTimestamp": 1654656261,
      "capitalInSuper": 824967660,
      "capitalOutSuper": 252818140
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

`GetCapitalDistribution(req);`

*   **介绍**
    
    获取资金分布
    
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	optional double capitalInSuper = 9; // 流入资金额度，特大单
    	required double capitalInBig = 1; // 流入资金额度，大单
    	required double capitalInMid = 2; // 流入资金额度，中单
    	required double capitalInSmall = 3; // 流入资金额度，小单
    	optional double capitalOutSuper = 10; // 流出资金额度，特大单
    	required double capitalOutBig = 4; // 流出资金额度，大单
    	required double capitalOutMid = 5; // 流出资金额度，中单
    	required double capitalOutSmall = 6; // 流出资金额度，小单
    	optional string updateTime = 7; // 更新时间字符串
    	optional double updateTimestamp = 8; // 更新时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetCapitalDistribution(){
        const { RetType } = Common
        const { SubType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        security: {
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                    },
                };
                websocket.GetCapitalDistribution(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("CapitalDistribution: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    CapitalDistribution: errCode 0, retMsg , retType 0
    {
      "capitalInBig": 1586950080,
      "capitalInMid": 2861135060,
      "capitalInSmall": 879906120,
      "capitalOutBig": 890005200,
      "capitalOutMid": 2423820040,
      "capitalOutSmall": 799307540,
      "updateTime": "2021-09-10 11:57:30",
      "updateTimestamp": 1631246250,
      "capitalInSuper": 230158560,
      "capitalOutSuper": 286481380
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

接口限制

*   每 30 秒内最多请求 30 次获取资金分布接口。
*   仅支持正股、窝轮和基金。
*   更多资金分布介绍，请参考 [这里](https://support.futunn.com/zh-cn/topic498?lang=zh-CN)
     。
*   返回数据只包括盘中数据，不包含盘前盘后数据。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_capital_distribution(stock_code)`

*   **介绍**
    
    获取资金分布
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | stock\_code | str | 股票代码 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回股票资金分布数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   资金分布数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | capital\_in\_super | float | 流入资金额度，特大单 |
        | capital\_in\_big | float | 流入资金额度，大单 |
        | capital\_in\_mid | float | 流入资金额度，中单 |
        | capital\_in\_small | float | 流入资金额度，小单 |
        | capital\_out\_super | float | 流出资金额度，特大单 |
        | capital\_out\_big | float | 流出资金额度，大单 |
        | capital\_out\_mid | float | 流出资金额度，中单 |
        | capital\_out\_small | float | 流出资金额度，小单 |
        | update\_time | str | 更新时间字符串<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd HH:mm:ss |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_capital_distribution("HK.00700")
    if ret == RET_OK:
        print(data)
        print(data['capital_in_big'][0])    # 取第一条的流入资金额度，大单
        print(data['capital_in_big'].values.tolist())   # 转为 list
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
11  

*   **Output**

       capital_in_super  capital_in_big  ...  capital_out_small          update_time
    0      2.261085e+09    2.141964e+09  ...       2.887413e+09  2022-06-08 15:59:59
    
    [1 rows x 9 columns]
    2141963720.0
    [2141963720.0]
    

1  
2  
3  
4  
5  
6  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-capital-distribution.html#753-2)
 Qot\_GetCapitalDistribution.proto
-------------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取资金分布
    
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	optional double capitalInSuper = 9; // 流入资金额度，特大单
    	required double capitalInBig = 1; // 流入资金额度，大单
    	required double capitalInMid = 2; // 流入资金额度，中单
    	required double capitalInSmall = 3; // 流入资金额度，小单
    	optional double capitalOutSuper = 10; // 流出资金额度，特大单
    	required double capitalOutBig = 4; // 流出资金额度，大单
    	required double capitalOutMid = 5; // 流出资金额度，中单
    	required double capitalOutSmall = 6; // 流出资金额度，小单
    	optional string updateTime = 7; // 更新时间字符串
    	optional double updateTimestamp = 8; // 更新时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3212
    

`uint GetCapitalDistribution(QotGetCapitalDistribution.Request req);`  
`virtual void OnReply_GetCapitalDistribution(MMAPI_Conn client, uint nSerialNo, QotGetCapitalDistribution.Response rsp);`

*   **介绍**

获取资金分布

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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	optional double capitalInSuper = 9; // 流入资金额度，特大单
    	required double capitalInBig = 1; // 流入资金额度，大单
    	required double capitalInMid = 2; // 流入资金额度，中单
    	required double capitalInSmall = 3; // 流入资金额度，小单
    	optional double capitalOutSuper = 10; // 流出资金额度，特大单
    	required double capitalOutBig = 4; // 流出资金额度，大单
    	required double capitalOutMid = 5; // 流出资金额度，中单
    	required double capitalOutSmall = 6; // 流出资金额度，小单
    	optional string updateTime = 7; // 更新时间字符串
    	optional double updateTimestamp = 8; // 更新时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : MMSPI_Qot, MMSPI_Conn
    {
    	MMAPI_Qot qot = new MMAPI_Qot();
    
    	public Program()
    	{
    		qot.SetClientInfo("csharp", 1);  //设置客户端信息
    		qot.SetConnCallback(this);  //设置连接回调
    		qot.SetQotCallback(this);   //设置交易回调
    	}
    
    	public void Start()
    	{
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
    		QotGetCapitalDistribution.C2S c2s = QotGetCapitalDistribution.C2S.CreateBuilder()
    				.SetSecurity(sec)
    				.Build();
    		QotGetCapitalDistribution.Request req = QotGetCapitalDistribution.Request.CreateBuilder().SetC2S(c2s).Build();
    		uint seqNo = qot.GetCapitalDistribution(req);
    		Console.Write("Send QotGetCapitalDistribution: {0}\n", seqNo);
    	}
    
    
    	public void OnDisconnect(MMAPI_Conn client, long errCode)
    	{
    		Console.Write("Qot onDisConnect: {0}\n", errCode);
    	}
    
    	public void OnReply_GetCapitalDistribution(moomoo.OpenApi.MMAPI_Conn client, uint nSerialNo, QotGetCapitalDistribution.Response rsp)
    	{
    		Console.Write("Reply: QotGetCapitalDistribution: {0}  {1}\n", nSerialNo, rsp.ToString());
    		Console.Write("inFlow: {0}\n", rsp.S2C.CapitalInBig);
    	}
    
    	public static void Main(String[] args)
    	{
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6939769465241305991
    Send QotGetCapitalDistribution: 3
    Reply: QotGetCapitalDistribution: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      capitalInBig: 299700820
      capitalInMid: 521403800
      capitalInSmall: 740895620
      capitalOutBig: 332038880
      capitalOutMid: 483401260
      capitalOutSmall: 682124080
      updateTime: "2022-06-07 10:46:02"
      updateTimestamp: 1654569962
      capitalInSuper: 230158560
      capitalOutSuper: 286481380
    }
    
    inFlow: 299700820
    

1  
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

`int getCapitalDistribution(QotGetCapitalDistribution.Request req);`  
`void onReply_GetCapitalDistribution(MMAPI_Conn client, int nSerialNo, QotGetCapitalDistribution.Response rsp);`

*   **介绍**

获取资金分布

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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	optional double capitalInSuper = 9; // 流入资金额度，特大单
    	required double capitalInBig = 1; // 流入资金额度，大单
    	required double capitalInMid = 2; // 流入资金额度，中单
    	required double capitalInSmall = 3; // 流入资金额度，小单
    	optional double capitalOutSuper = 10; // 流出资金额度，特大单
    	required double capitalOutBig = 4; // 流出资金额度，大单
    	required double capitalOutMid = 5; // 流出资金额度，中单
    	required double capitalOutSmall = 6; // 流出资金额度，小单
    	optional string updateTime = 7; // 更新时间字符串
    	optional double updateTimestamp = 8; // 更新时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    class QotDemo implements MMSPI_Qot, MMSPI_Conn {
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
            QotGetCapitalDistribution.C2S c2s = QotGetCapitalDistribution.C2S.newBuilder()
                    .setSecurity(sec)
                    .build();
            QotGetCapitalDistribution.Request req = QotGetCapitalDistribution.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getCapitalDistribution(req);
            System.out.printf("Send QotGetCapitalDistribution: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetCapitalDistribution(MMAPI_Conn client, int nSerialNo, QotGetCapitalDistribution.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetCapitalDistribution failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetCapitalDistribution: %s\n", json);
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

*   **Output**

    Send QotGetCapitalDistribution: 2
    Receive QotGetCapitalDistribution: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "capitalInBig": 8.6653274E8,
        "capitalInMid": 7.6876998E8,
        "capitalInSmall": 1.02038782E9,
        "capitalOutBig": 4.4806512E8,
        "capitalOutMid": 5.0682792E8,
        "capitalOutSmall": 8.1260248E8,
        "updateTime": "2022-06-08 10:44:34",
        "updateTimestamp": 1.654656274E9,
        "capitalInSuper": 8.2496766E8,
        "capitalOutSuper": 2.6721714E8
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

`moomoo::u32_t GetCapitalDistribution(const Qot_GetCapitalDistribution::Request &stReq);`  
`virtual void OnReply_GetCapitalDistribution(moomoo::u32_t nSerialNo, const Qot_GetCapitalDistribution::Response &stRsp) = 0;`

*   **介绍**
    
    获取资金分布
    
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	optional double capitalInSuper = 9; // 流入资金额度，特大单
    	required double capitalInBig = 1; // 流入资金额度，大单
    	required double capitalInMid = 2; // 流入资金额度，中单
    	required double capitalInSmall = 3; // 流入资金额度，小单
    	optional double capitalOutSuper = 10; // 流出资金额度，特大单
    	required double capitalOutBig = 4; // 流出资金额度，大单
    	required double capitalOutMid = 5; // 流出资金额度，中单
    	required double capitalOutSmall = 6; // 流出资金额度，小单
    	optional string updateTime = 7; // 更新时间字符串
    	optional double updateTimestamp = 8; // 更新时间戳
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
    		Qot_GetCapitalDistribution::Request req;
    		Qot_GetCapitalDistribution::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetCapitalDistributionSerialNo = m_pQotApi->GetCapitalDistribution(req);
            cout << "Request GetCapitalDistribution SerialNo: " << m_GetCapitalDistributionSerialNo << endl;
    	}
    
    	virtual void OnReply_GetCapitalDistribution(moomoo::u32_t nSerialNo, const Qot_GetCapitalDistribution::Response &stRsp){
            if(nSerialNo == m_GetCapitalDistributionSerialNo)
            {
                cout << "OnReply_GetCapitalDistribution SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetCapitalDistributionSerialNo;
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
    Request GetCapitalDistribution SerialNo: 3
    OnReply_GetCapitalDistribution SerialNo: 3
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "capitalInBig": 859479380,
      "capitalInMid": 763181580,
      "capitalInSmall": 1011289940,
      "capitalOutBig": 444405720,
      "capitalOutMid": 503977520,
      "capitalOutSmall": 807438760,
      "updateTime": "2022-06-08 10:44:21",
      "updateTimestamp": 1654656261,
      "capitalInSuper": 824967660,
      "capitalOutSuper": 252818140
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

`GetCapitalDistribution(req);`

*   **介绍**
    
    获取资金分布
    
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	optional double capitalInSuper = 9; // 流入资金额度，特大单
    	required double capitalInBig = 1; // 流入资金额度，大单
    	required double capitalInMid = 2; // 流入资金额度，中单
    	required double capitalInSmall = 3; // 流入资金额度，小单
    	optional double capitalOutSuper = 10; // 流出资金额度，特大单
    	required double capitalOutBig = 4; // 流出资金额度，大单
    	required double capitalOutMid = 5; // 流出资金额度，中单
    	required double capitalOutSmall = 6; // 流出资金额度，小单
    	optional string updateTime = 7; // 更新时间字符串
    	optional double updateTimestamp = 8; // 更新时间戳
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetCapitalDistribution(){
        const { RetType } = Common
        const { SubType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        security: {
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                    },
                };
                websocket.GetCapitalDistribution(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("CapitalDistribution: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    CapitalDistribution: errCode 0, retMsg , retType 0
    {
      "capitalInBig": 1586950080,
      "capitalInMid": 2861135060,
      "capitalInSmall": 879906120,
      "capitalOutBig": 890005200,
      "capitalOutMid": 2423820040,
      "capitalOutSmall": 799307540,
      "updateTime": "2021-09-10 11:57:30",
      "updateTimestamp": 1631246250,
      "capitalInSuper": 230158560,
      "capitalOutSuper": 286481380
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

接口限制

*   每 30 秒内最多请求 30 次获取资金分布接口。
*   仅支持正股、窝轮和基金。
*   更多资金分布介绍，请参考 [这里](https://support.futunn.com/zh-cn/topic498?lang=zh-CN)
     。
*   返回数据只包括盘中数据，不包含盘前盘后数据。

← [获取资金流向](https://openapi.futunn.com/futu-api-doc/quote/get-capital-flow.html) [获取股票所属板块](https://openapi.futunn.com/futu-api-doc/quote/get-owner-plate.html)
 →