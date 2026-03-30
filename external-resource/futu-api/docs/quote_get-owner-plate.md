# 获取股票所属板块 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quote/get-owner-plate.html

[#](https://openapi.futunn.com/futu-api-doc/quote/get-owner-plate.html#9960)
 获取股票所属板块
======================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_owner_plate(code_list)`

*   **介绍**
    
    获取单支或多支股票的所属板块信息列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 股票代码列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅支持正股、指数  <br>list 中元素类型是 str |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回所属板块数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   所属板块数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 证券代码 |
        | name | str | 股票名称 |
        | plate\_code | str | 板块代码 |
        | plate\_name | str | 板块名字 |
        | plate\_type | [Plate](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1362) | 板块类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>行业板块或概念板块 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    code_list = ['HK.00001']
    ret, data = quote_ctx.get_owner_plate(code_list)
    if ret == RET_OK:
        print(data)
        print(data['code'][0])    # 取第一条的股票代码
        print(data['plate_code'].values.tolist())   # 板块代码转为 list
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
12  

*   **Output**

            code name          plate_code plate_name plate_type
    0   HK.00001   长和  HK.HSI Constituent      恒指成份股      OTHER
    ..       ...  ...                 ...        ...        ...
    8   HK.00001   长和           HK.BK1983    香港股票ADR      OTHER
    
    [9 rows x 5 columns]
    HK.00001
    ['HK.HSI Constituent', 'HK.GangGuTong', 'HK.BK1000', 'HK.BK1061', 'HK.BK1107', 'HK.BK1331', 'HK.BK1600', 'HK.BK1922', 'HK.BK1983']
    

1  
2  
3  
4  
5  
6  
7  
8  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-owner-plate.html#1138)
 Qot\_GetOwnerPlate.proto
------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取股票的所属板块信息
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

    message SecurityOwnerPlate
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; // 股票名称
    	repeated Qot_Common.PlateInfo plateInfoList = 2; //所属板块
    }
    
    message S2C
    {
    	repeated SecurityOwnerPlate ownerPlateList = 1; //所属板块信息
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   板块信息结构参见 [PlateInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2571)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3207
    

`uint GetOwnerPlate(QotGetOwnerPlate.Request req);`  
`virtual void OnReply_GetOwnerPlate(FTAPI_Conn client, uint nSerialNo, QotGetOwnerPlate.Response rsp);`

*   **介绍**

获取股票的所属板块信息

*   **参数**

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

    message SecurityOwnerPlate
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; // 股票名称
    	repeated Qot_Common.PlateInfo plateInfoList = 2; //所属板块
    }
    
    message S2C
    {
    	repeated SecurityOwnerPlate ownerPlateList = 1; //所属板块信息
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   板块信息结构参见 [PlateInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2571)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program: FTSPI_Qot, FTSPI_Conn {
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
            QotGetOwnerPlate.C2S c2s = QotGetOwnerPlate.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                .Build();
            QotGetOwnerPlate.Request req = QotGetOwnerPlate.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetOwnerPlate(req);
            Console.Write("Send QotGetOwnerPlate: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetOwnerPlate(FTAPI_Conn client, uint nSerialNo, QotGetOwnerPlate.Response rsp)
        {
            Console.Write("Reply: QotGetOwnerPlate: {0}\n", nSerialNo);
            Console.Write("Code: {0}, name: {1},  plateType: {2} \n",
                rsp.S2C.OwnerPlateListList[0].PlateInfoListList[0].Plate.Code,
                rsp.S2C.OwnerPlateListList[0].PlateInfoListList[0].Name,
                rsp.S2C.OwnerPlateListList[0].PlateInfoListList[0].PlateType);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825684997241406142
    Send QotGetOwnerPlate: 3
    Reply: QotGetOwnerPlate: 3
    Code: HSI Constituent, name: 恒指成份股,  plateType: 4
    

1  
2  
3  
4  

`int getOwnerPlate(QotGetOwnerPlate.Request req);`  
`void onReply_GetOwnerPlate(FTAPI_Conn client, int nSerialNo, QotGetOwnerPlate.Response rsp);`

*   **介绍**

获取股票的所属板块信息

*   **参数**

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

    message SecurityOwnerPlate
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; // 股票名称
    	repeated Qot_Common.PlateInfo plateInfoList = 2; //所属板块
    }
    
    message S2C
    {
    	repeated SecurityOwnerPlate ownerPlateList = 1; //所属板块信息
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   板块信息结构参见 [PlateInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2571)
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
            QotGetOwnerPlate.C2S c2s = QotGetOwnerPlate.C2S.newBuilder()
                    .addSecurityList(sec)
                .build();
            QotGetOwnerPlate.Request req = QotGetOwnerPlate.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getOwnerPlate(req);
            System.out.printf("Send QotGetOwnerPlate: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetOwnerPlate(FTAPI_Conn client, int nSerialNo, QotGetOwnerPlate.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetOwnerPlate failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetOwnerPlate: %s\n", json);
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

    Send QotGetOwnerPlate: 2
    Receive QotGetOwnerPlate: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "ownerPlateList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "plateInfoList": [{\
            "plate": {\
              "market": 1,\
              "code": "HSI Constituent"\
            },\
            "name": "恒指成份股",\
            "plateType": 4\
          }, ... {\
            "plate": {\
              "market": 1,\
              "code": "BK1995"\
            },\
            "name": "区块链",\
            "plateType": 3\
          }]\
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

`Futu::u32_t GetOwnerPlate(const Qot_GetOwnerPlate::Request &stReq);`  
`virtual void OnReply_GetOwnerPlate(Futu::u32_t nSerialNo, const Qot_GetOwnerPlate::Response &stRsp) = 0;`

*   **介绍**
    
    获取股票的所属板块信息
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

    message SecurityOwnerPlate
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; // 股票名称
    	repeated Qot_Common.PlateInfo plateInfoList = 2; //所属板块
    }
    
    message S2C
    {
    	repeated SecurityOwnerPlate ownerPlateList = 1; //所属板块信息
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   板块信息结构参见 [PlateInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2571)
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
    		Qot_GetOwnerPlate::Request req;
    		Qot_GetOwnerPlate::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetOwnerPlateSerialNo = m_pQotApi->GetOwnerPlate(req);
            cout << "Request GetOwnerPlate SerialNo: " << m_GetOwnerPlateSerialNo << endl;
    	}
    
    	virtual void OnReply_GetOwnerPlate(Futu::u32_t nSerialNo, const Qot_GetOwnerPlate::Response &stRsp){
            if(nSerialNo == m_GetOwnerPlateSerialNo)
            {
                cout << "OnReply_GetOwnerPlate SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetOwnerPlateSerialNo;
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
    Request GetOwnerPlate SerialNo: 4
    OnReply_GetOwnerPlate SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "ownerPlateList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "00700"\
        },\
        "plateInfoList": [\
         {\
          "plate": {\
           "market": 1,\
           "code": "HSI Constituent"\
          },\
          "name": "恒指成份股",\
          "plateType": 4\
         },\
    ...\
         {\
          "plate": {\
           "market": 1,\
           "code": "BK1995"\
          },\
          "name": "区块链",\
          "plateType": 3\
         }\
        ]\
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

`GetOwnerPlate(req);`

*   **介绍**
    
    获取股票的所属板块信息
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

    message SecurityOwnerPlate
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; // 股票名称
    	repeated Qot_Common.PlateInfo plateInfoList = 2; //所属板块
    }
    
    message S2C
    {
    	repeated SecurityOwnerPlate ownerPlateList = 1; //所属板块信息
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   板块信息结构参见 [PlateInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2571)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetOwnerPlate(){
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
                websocket.GetOwnerPlate(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("OwnerPlate: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    OwnerPlate: errCode 0, retMsg , retType 0
    {
      "ownerPlateList": [{\
        "security": {\
          "market": 1,\
          "code": "00700"\
        },\
        "plateInfoList": [{\
          "plate": {\
            "market": 1,\
            "code": "HSI Constituent"\
          },\
          "name": "恒指成份股",\
          "plateType": 4\
        }, {\
          "plate": {\
            "market": 1,\
            "code": "HSCEI Stock"\
          },\
          "name": "国指成份股",\
          "plateType": 4\
        }, ..., {\
          "plate": {\
            "market": 1,\
            "code": "BK1995"\
          },\
          "name": "区块链",\
          "plateType": 3\
        }]\
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
27  
28  
29  
30  
31  
32  

接口限制

*   每 30 秒内最多请求 10 次获取股票所属板块接口
*   每次请求的股票列表中，股票个数上限为 200 个
*   仅支持正股和指数

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_owner_plate(code_list)`

*   **介绍**
    
    获取单支或多支股票的所属板块信息列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 股票代码列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅支持正股、指数  <br>list 中元素类型是 str |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回所属板块数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   所属板块数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 证券代码 |
        | name | str | 股票名称 |
        | plate\_code | str | 板块代码 |
        | plate\_name | str | 板块名字 |
        | plate\_type | [Plate](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1362) | 板块类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>行业板块或概念板块 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    code_list = ['HK.00001']
    ret, data = quote_ctx.get_owner_plate(code_list)
    if ret == RET_OK:
        print(data)
        print(data['code'][0])    # 取第一条的股票代码
        print(data['plate_code'].values.tolist())   # 板块代码转为 list
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
12  

*   **Output**

            code name          plate_code plate_name plate_type
    0   HK.00001   长和  HK.HSI Constituent      恒指成份股      OTHER
    ..       ...  ...                 ...        ...        ...
    8   HK.00001   长和           HK.BK1983    香港股票ADR      OTHER
    
    [9 rows x 5 columns]
    HK.00001
    ['HK.HSI Constituent', 'HK.GangGuTong', 'HK.BK1000', 'HK.BK1061', 'HK.BK1107', 'HK.BK1331', 'HK.BK1600', 'HK.BK1922', 'HK.BK1983']
    

1  
2  
3  
4  
5  
6  
7  
8  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-owner-plate.html#1138-2)
 Qot\_GetOwnerPlate.proto
--------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取股票的所属板块信息
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

    message SecurityOwnerPlate
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; // 股票名称
    	repeated Qot_Common.PlateInfo plateInfoList = 2; //所属板块
    }
    
    message S2C
    {
    	repeated SecurityOwnerPlate ownerPlateList = 1; //所属板块信息
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   板块信息结构参见 [PlateInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2571)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3207
    

`uint GetOwnerPlate(QotGetOwnerPlate.Request req);`  
`virtual void OnReply_GetOwnerPlate(MMAPI_Conn client, uint nSerialNo, QotGetOwnerPlate.Response rsp);`

*   **介绍**

获取股票的所属板块信息

*   **参数**

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

    message SecurityOwnerPlate
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; // 股票名称
    	repeated Qot_Common.PlateInfo plateInfoList = 2; //所属板块
    }
    
    message S2C
    {
    	repeated SecurityOwnerPlate ownerPlateList = 1; //所属板块信息
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   板块信息结构参见 [PlateInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2571)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program: MMSPI_Qot, MMSPI_Conn {
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
            QotGetOwnerPlate.C2S c2s = QotGetOwnerPlate.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                .Build();
            QotGetOwnerPlate.Request req = QotGetOwnerPlate.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetOwnerPlate(req);
            Console.Write("Send QotGetOwnerPlate: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetOwnerPlate(MMAPI_Conn client, uint nSerialNo, QotGetOwnerPlate.Response rsp)
        {
            Console.Write("Reply: QotGetOwnerPlate: {0}\n", nSerialNo);
            Console.Write("Code: {0}, name: {1},  plateType: {2} \n",
                rsp.S2C.OwnerPlateListList[0].PlateInfoListList[0].Plate.Code,
                rsp.S2C.OwnerPlateListList[0].PlateInfoListList[0].Name,
                rsp.S2C.OwnerPlateListList[0].PlateInfoListList[0].PlateType);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825684997241406142
    Send QotGetOwnerPlate: 3
    Reply: QotGetOwnerPlate: 3
    Code: HSI Constituent, name: 恒指成份股,  plateType: 4
    

1  
2  
3  
4  

`int getOwnerPlate(QotGetOwnerPlate.Request req);`  
`void onReply_GetOwnerPlate(MMAPI_Conn client, int nSerialNo, QotGetOwnerPlate.Response rsp);`

*   **介绍**

获取股票的所属板块信息

*   **参数**

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

    message SecurityOwnerPlate
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; // 股票名称
    	repeated Qot_Common.PlateInfo plateInfoList = 2; //所属板块
    }
    
    message S2C
    {
    	repeated SecurityOwnerPlate ownerPlateList = 1; //所属板块信息
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   板块信息结构参见 [PlateInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2571)
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
            QotGetOwnerPlate.C2S c2s = QotGetOwnerPlate.C2S.newBuilder()
                    .addSecurityList(sec)
                .build();
            QotGetOwnerPlate.Request req = QotGetOwnerPlate.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getOwnerPlate(req);
            System.out.printf("Send QotGetOwnerPlate: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetOwnerPlate(MMAPI_Conn client, int nSerialNo, QotGetOwnerPlate.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetOwnerPlate failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetOwnerPlate: %s\n", json);
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

    Send QotGetOwnerPlate: 2
    Receive QotGetOwnerPlate: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "ownerPlateList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "plateInfoList": [{\
            "plate": {\
              "market": 1,\
              "code": "HSI Constituent"\
            },\
            "name": "恒指成份股",\
            "plateType": 4\
          }, ... {\
            "plate": {\
              "market": 1,\
              "code": "BK1995"\
            },\
            "name": "区块链",\
            "plateType": 3\
          }]\
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

`moomoo::u32_t GetOwnerPlate(const Qot_GetOwnerPlate::Request &stReq);`  
`virtual void OnReply_GetOwnerPlate(moomoo::u32_t nSerialNo, const Qot_GetOwnerPlate::Response &stRsp) = 0;`

*   **介绍**
    
    获取股票的所属板块信息
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

    message SecurityOwnerPlate
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; // 股票名称
    	repeated Qot_Common.PlateInfo plateInfoList = 2; //所属板块
    }
    
    message S2C
    {
    	repeated SecurityOwnerPlate ownerPlateList = 1; //所属板块信息
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   板块信息结构参见 [PlateInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2571)
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
    		Qot_GetOwnerPlate::Request req;
    		Qot_GetOwnerPlate::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetOwnerPlateSerialNo = m_pQotApi->GetOwnerPlate(req);
            cout << "Request GetOwnerPlate SerialNo: " << m_GetOwnerPlateSerialNo << endl;
    	}
    
    	virtual void OnReply_GetOwnerPlate(moomoo::u32_t nSerialNo, const Qot_GetOwnerPlate::Response &stRsp){
            if(nSerialNo == m_GetOwnerPlateSerialNo)
            {
                cout << "OnReply_GetOwnerPlate SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetOwnerPlateSerialNo;
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
    Request GetOwnerPlate SerialNo: 4
    OnReply_GetOwnerPlate SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "ownerPlateList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "00700"\
        },\
        "plateInfoList": [\
         {\
          "plate": {\
           "market": 1,\
           "code": "HSI Constituent"\
          },\
          "name": "恒指成份股",\
          "plateType": 4\
         },\
    ...\
         {\
          "plate": {\
           "market": 1,\
           "code": "BK1995"\
          },\
          "name": "区块链",\
          "plateType": 3\
         }\
        ]\
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

`GetOwnerPlate(req);`

*   **介绍**
    
    获取股票的所属板块信息
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

    message SecurityOwnerPlate
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 3; // 股票名称
    	repeated Qot_Common.PlateInfo plateInfoList = 2; //所属板块
    }
    
    message S2C
    {
    	repeated SecurityOwnerPlate ownerPlateList = 1; //所属板块信息
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   板块信息结构参见 [PlateInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2571)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetOwnerPlate(){
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
                websocket.GetOwnerPlate(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("OwnerPlate: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    OwnerPlate: errCode 0, retMsg , retType 0
    {
      "ownerPlateList": [{\
        "security": {\
          "market": 1,\
          "code": "00700"\
        },\
        "plateInfoList": [{\
          "plate": {\
            "market": 1,\
            "code": "HSI Constituent"\
          },\
          "name": "恒指成份股",\
          "plateType": 4\
        }, {\
          "plate": {\
            "market": 1,\
            "code": "HSCEI Stock"\
          },\
          "name": "国指成份股",\
          "plateType": 4\
        }, ..., {\
          "plate": {\
            "market": 1,\
            "code": "BK1995"\
          },\
          "name": "区块链",\
          "plateType": 3\
        }]\
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
27  
28  
29  
30  
31  
32  

接口限制

*   每 30 秒内最多请求 10 次获取股票所属板块接口
*   每次请求的股票列表中，股票个数上限为 200 个
*   仅支持正股和指数

← [获取资金分布](https://openapi.futunn.com/futu-api-doc/quote/get-capital-distribution.html) [获取历史 K 线](https://openapi.futunn.com/futu-api-doc/quote/request-history-kline.html)
 →