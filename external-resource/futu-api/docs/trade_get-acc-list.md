 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/get-acc-list.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/get-acc-list.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/get-acc-list.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/get-acc-list.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
*   交易接口
    
    *   [交易接口总览](https://openapi.futunn.com/futu-api-doc/trade/overview.html)
        
    *   [交易对象](https://openapi.futunn.com/futu-api-doc/trade/base.html)
        
    *   账户
        
        *   [获取交易业务账户列表](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html)
            
        *   [解锁交易](https://openapi.futunn.com/futu-api-doc/trade/unlock.html)
            
        
    *   资产持仓
        
    *   订单
        
    *   成交
        
    *   [交易定义](https://openapi.futunn.com/futu-api-doc/trade/trade.html)
        
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html#5754)
 获取交易业务账户列表
=====================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_acc_list()`

*   **介绍**
    
    获取交易业务账户列表。  
    要调用其他交易接口前，请先获取此列表，确认要操作的交易业务账户无误。
    
*   **参数**
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回交易业务账户列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   交易业务账户列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | acc\_id | int | 交易业务账户 |
        | trd\_env | [TrdEnv](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6374) | 交易环境 |
        | acc\_type | [TrdAccType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3974) | 账户类型 |
        | uni\_card\_num | str | 综合账户卡号，同移动端内的展示 |
        | card\_num | str | 业务账户卡号<br>(ℹ️ 综合账户下包含一个或多个业务账户（综合证券账户、综合期货账户等等），与交易品种有关) |
        | security\_firm | [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572) | 所属券商 |
        | sim\_acc\_type | [SimAccType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6449) | 模拟账户类型<br>(ℹ️ 仅模拟账户适用) |
        | trdmarket\_auth | list | 交易市场权限<br>(ℹ️ list 中元素类型是 [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719)) |
        | acc\_status | [TrdAccStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#121) | 账户状态 |
        | acc\_role | [TrdAccRole](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6395) | 账户结构<br>(ℹ️ 用于区分主子账户结构<br><br>*   MASTER: 主账户<br>*   NORMAL: 普通账户<br>*   IPO: 马来西亚 IPO 账户) |
        | jp\_acc\_type | list | 日本账户类型<br>(ℹ️ list 中元素类型是[SubAccType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6112)<br>，仅对日本券商生效) |
        
*   **说明**
    
    获取港股模拟交易账户，需要指定 filter\_trdmarket 为 TrdMarket.HK，此时会返回2个模拟交易账号。其中 sim\_acc\_type = STOCK 为港股模拟账户，sim\_acc\_type = OPTION 为港股期权模拟账户，sim\_acc\_type = FUTURES 为港股期货模拟账户。  
    获取美股模拟交易账户，需要指定 filter\_trdmarket 为 TrdMarket.US，sim\_acc\_type = STOCK\_AND\_OPTION 代表美股融资融券模拟账户，可以模拟交易股票和期权。sim\_acc\_type = FUTURES 为美国期货模拟账户。
    
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.get_acc_list()
    if ret == RET_OK:
        print(data)
        print(data['acc_id'][0])  # 取第一个账号
        print(data['acc_id'].values.tolist())  # 转为 list
    else:
        print('get_acc_list error: ', data)
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

*   **Output**

                   acc_id   trd_env acc_type       uni_card_num           card_num    security_firm   sim_acc_type                           trdmarket_auth    acc_status    acc_role    jp_acc_type
    0  281756479345015383      REAL   MARGIN   1001289516908051   1001329805025007   FUTUSECURITIES            N/A    [HK, US, HKCC, SG, HKFUND, USFUND, JP]       ACTIVE      NORMAL             []
    1             8377516  SIMULATE     CASH                N/A                N/A              N/A          STOCK                                      [HK]       ACTIVE         N/A             []
    2            10741586  SIMULATE   MARGIN                N/A                N/A              N/A         OPTION                                      [HK]       ACTIVE         N/A             []
    3  281756455983234027      REAL   MARGIN                N/A   1001100321720699   FUTUSECURITIES            N/A                                      [HK]     DISABLED      NORMAL             []
    281756479345015383
    [281756479345015383, 8377516, 10741586, 281756455983234027]
    

1  
2  
3  
4  
5  
6  
7  

[#](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html#3852)
 Trd\_GetAccList.proto
------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取交易业务账户列表
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
    	optional int32 trdCategory = 2; //交易品类，参考 Trd_Common.TrdCategory
    	optional bool needGeneralSecAccount = 3; //是否返回综合账户 （适用于 HK/US/SG/AU 综合账户体系）
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
11  

*   **返回**

    message S2C
    {
    	repeated Trd_Common.TrdAcc accList = 1; //交易业务账户列表
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

> *   交易品类 [TrdCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6504)
>     
> *   交易业务账户结构参见 [TrdAcc](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8680)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2001
    

`uint GetAccList(TrdGetAccList.Request req);`  
`virtual void OnReply_GetAccList(FTAPI_Conn client, uint nSerialNo, TrdGetAccList.Response rsp);`

*   **介绍**
    
    获取交易业务账户列表
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
    	optional int32 trdCategory = 2; //交易品类，参考 Trd_Common.TrdCategory
    	optional bool needGeneralSecAccount = 3; //是否返回综合账户 （适用于 HK/US/SG/AU 综合账户体系）
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
11  

*   **回调**

    message S2C
    {
    	repeated Trd_Common.TrdAcc accList = 1; //交易业务账户列表
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

> *   交易品类 [TrdCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6504)
>     
> *   交易业务账户结构参见 [TrdAcc](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8680)
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
    
            TrdGetAccList.C2S c2s = TrdGetAccList.C2S.CreateBuilder().SetUserID(5972312)
    				.SetTrdCategory((int)TrdCommon.TrdCategory.TrdCategory_Security)
    				.SetNeedGeneralSecAccount(true)
                    .Build();
            TrdGetAccList.Request req = TrdGetAccList.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetAccList(req);
            Console.Write("Send TrdGetAccList: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetAccList(FTAPI_Conn client, uint nSerialNo, TrdGetAccList.Response rsp)
        {
            Console.Write("Reply: TrdGetAccList: {0}\n", nSerialNo);
            Console.Write("accID: {0}\n", rsp.S2C.AccListList[0].AccID);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6826806647571888999
    Send TrdGetAccList: 3
    Reply: TrdGetAccList: 3
    accID: 281756455989723220
    

1  
2  
3  
4  

`int getAccList(TrdGetAccList.Request req);`  
`void onReply_GetAccList(FTAPI_Conn client, int nSerialNo, TrdGetAccList.Response rsp);`

*   **介绍**
    
    获取交易业务账户列表
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
    	optional int32 trdCategory = 2; //交易品类，参考 Trd_Common.TrdCategory
    	optional bool needGeneralSecAccount = 3; //是否返回综合账户 （适用于 HK/US/SG/AU 综合账户体系）
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
11  

*   **回调**

    message S2C
    {
    	repeated Trd_Common.TrdAcc accList = 1; //交易业务账户列表
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

> *   交易品类 [TrdCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6504)
>     
> *   交易业务账户结构参见 [TrdAcc](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8680)
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
    
            TrdGetAccList.C2S c2s = TrdGetAccList.C2S.newBuilder().setUserID(900053)
                    .setTrdCategory(TrdCommon.TrdCategory.TrdCategory_Security_VALUE)
                    .setNeedGeneralSecAccount(true)
                    .build();
            TrdGetAccList.Request req = TrdGetAccList.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getAccList(req);
            System.out.printf("Send TrdGetAccList: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetAccList(FTAPI_Conn client, int nSerialNo, TrdGetAccList.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetAccList failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetAccList: %s\n", json);
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

    Send TrdGetAccList: 2
    Receive TrdGetAccList: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "accList": [{\
          "trdEnv": 1,\
          "accID": "281756466778014447",\
          "trdMarketAuthList": [1],\
          "accType": 2,\
          "uniCardNum":"1001263856121256",\
          "cardNum": "1002233560482767",\
          "securityFirm": 1\
        }, ... {\
          "trdEnv": 0,\
          "accID": "3547832",\
          "trdMarketAuthList": [2],\
          "accType": 2,\
          "securityFirm": 0,\
          "simAccType": 2\
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

`Futu::u32_t GetAccList(const Trd_GetAccList::Request &stReq);`  
`virtual void OnReply_GetAccList(Futu::u32_t nSerialNo, const Trd_GetAccList::Response &stRsp) = 0;`

*   **介绍**
    
    获取交易业务账户列表
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
    	optional int32 trdCategory = 2; //交易品类，参考 Trd_Common.TrdCategory
    	optional bool needGeneralSecAccount = 3; //是否返回综合账户 （适用于 HK/US/SG/AU 综合账户体系）
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
11  

*   **回调**

    message S2C
    {
    	repeated Trd_Common.TrdAcc accList = 1; //交易业务账户列表
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

> *   交易品类 [TrdCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6504)
>     
> *   交易业务账户结构参见 [TrdAcc](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8680)
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
    
    		// 组包
    		Trd_GetAccList::Request req;
    		Trd_GetAccList::C2S *c2s = req.mutable_c2s();
    		c2s->set_userid(12345678);
    		c2s->set_trdcategory(Trd_Common::TrdCategory::TrdCategory_Security);
            c2s->set_needgeneralsecaccount(true);
    
            m_GetAccListSerialNo = m_pTrdApi->GetAccList(req);
            cout << "Request GetAccList SerialNo: " << m_GetAccListSerialNo << endl;
    	}
    
    	virtual void OnReply_GetAccList(Futu::u32_t nSerialNo, const Trd_GetAccList::Response &stRsp){
            if(nSerialNo == m_GetAccListSerialNo)
            {
                cout << "OnReply_GetAccList SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_GetAccListSerialNo;
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

*   **Output**

    connect
    Request GetAccList SerialNo: 4
    OnReply_GetAccList SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "accList": [\
       {\
        "trdEnv": 1,\
        "accID": "281756456003951537",\
        "trdMarketAuthList": [\
         1\
        ],\
        "accType": 2,\
        "uniCardNum":"1001263856121256",\
        "cardNum": "1001100320714209",\
        "securityFirm": 1\
       },\
    ...\
       {\
        "trdEnv": 0,\
        "accID": "3637844",\
        "trdMarketAuthList": [\
         2\
        ],\
        "accType": 2,\
        "securityFirm": 0,\
        "simAccType": 2\
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

`GetAccList(req);`

*   **介绍**
    
    获取交易业务账户列表
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
    	optional int32 trdCategory = 2; //交易品类，参考 Trd_Common.TrdCategory
    	optional bool needGeneralSecAccount = 3; //是否返回综合账户 （适用于 HK/US/SG/AU 综合账户体系）
    
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

*   **返回**

    message S2C
    {
    	repeated Trd_Common.TrdAcc accList = 1; //交易业务账户列表
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

> *   交易品类 [TrdCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6504)
>     
> *   交易业务账户结构参见 [TrdAcc](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8680)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetAccList(){
        const { RetType } = Common
        const { SubType, QotMarket } = Qot_Common
        const { TrdCategory } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        userID: 0,
                        trdCategory: TrdCategory.TrdCategory_Security,
                        needGeneralSecAccount: true,
                    },
                };
                websocket.GetAccList(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("AccList: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    AccList: errCode 0, retMsg , retType 0
    {
      "accList": [{\
        "trdEnv": 1,\
        "accID": "281756456008357727",\
        "trdMarketAuthList": [1],\
        "accType": 2,\
        "uniCardNum":"1001263856121256",\
        "cardNum": "1001100321506782",\
        "securityFirm": 1\
      }, {\
        "trdEnv": 1,\
        "accID": "281756460303325023",\
        "trdMarketAuthList": [2],\
        "accType": 2,\
        "uniCardNum":"1001256386562384",\
        "cardNum": "1001100521938385",\
        "securityFirm": 1\
      }, ..., {\
        "trdEnv": 0,\
        "accID": "6684976",\
        "trdMarketAuthList": [2],\
        "accType": 2,\
        "securityFirm": 0,\
        "simAccType": 2\
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

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_acc_list()`

*   **介绍**
    
    获取交易业务账户列表。  
    要调用其他交易接口前，请先获取此列表，确认要操作的交易业务账户无误。
    
*   **参数**
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回交易业务账户列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   交易业务账户列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | acc\_id | int | 交易业务账户 |
        | trd\_env | [TrdEnv](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6374) | 交易环境 |
        | acc\_type | [TrdAccType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3974) | 账户类型 |
        | uni\_card\_num | str | 综合账户卡号，同移动端内的展示 |
        | card\_num | str | 业务账户卡号<br>(ℹ️ 综合账户下包含一个或多个业务账户（综合证券账户、综合期货账户等等），与交易品种有关) |
        | security\_firm | [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572) | 所属券商 |
        | sim\_acc\_type | [SimAccType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6449) | 模拟账户类型<br>(ℹ️ 仅模拟账户适用) |
        | trdmarket\_auth | list | 交易市场权限<br>(ℹ️ list 中元素类型是 [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719)) |
        | acc\_status | [TrdAccStatus](https://openapi.futunn.com/futu-api-doc/trade/trade.html#121) | 账户状态 |
        | acc\_role | [TrdAccRole](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6395) | 账户结构<br>(ℹ️ 用于区分主子账户结构<br><br>*   MASTER: 主账户<br>*   NORMAL: 普通账户<br>*   IPO: 马来西亚 IPO 账户) |
        | jp\_acc\_type | list | 日本账户类型<br>(ℹ️ list 中元素类型是[SubAccType](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6112)<br>，仅对日本券商生效) |
        
*   **说明**
    
    获取港股模拟交易账户，需要指定 filter\_trdmarket 为 TrdMarket.HK，此时会返回2个模拟交易账号。其中 sim\_acc\_type = STOCK 为港股模拟账户，sim\_acc\_type = OPTION 为港股期权模拟账户，sim\_acc\_type = FUTURES 为港股期货模拟账户。  
    获取美股模拟交易账户，需要指定 filter\_trdmarket 为 TrdMarket.US，sim\_acc\_type = STOCK\_AND\_OPTION 代表美股融资融券模拟账户，可以模拟交易股票和期权。sim\_acc\_type = FUTURES 为美国期货模拟账户。
    
*   **Example**
    

    from moomoo import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    ret, data = trd_ctx.get_acc_list()
    if ret == RET_OK:
        print(data)
        print(data['acc_id'][0])  # 取第一个账号
        print(data['acc_id'].values.tolist())  # 转为 list
    else:
        print('get_acc_list error: ', data)
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

*   **Output**

                   acc_id   trd_env acc_type       uni_card_num           card_num    security_firm   sim_acc_type                           trdmarket_auth    acc_status    acc_role    jp_acc_type
    0  281756420273981734      REAL   MARGIN  10018561211263256   1001100530724347          FUTUINC            N/A    [HK, US, HKCC, SG, HKFUND, USFUND, JP]       ACTIVE      NORMAL             []
    1             3450310  SIMULATE     CASH                N/A                N/A              N/A          STOCK                                      [HK]       ACTIVE         N/A             []
    2             3548732  SIMULATE   MARGIN                N/A                N/A              N/A         OPTION                                      [HK]       ACTIVE         N/A             []
    281756420273981734
    [281756420273981734, 3450310, 3548732]
    

1  
2  
3  
4  
5  
6  

[#](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html#3852-2)
 Trd\_GetAccList.proto
--------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取交易业务账户列表
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
    	optional int32 trdCategory = 2; //交易品类，参考 Trd_Common.TrdCategory
    	optional bool needGeneralSecAccount = 3; //是否返回综合账户 （适用于 HK/US/SG/AU 综合账户体系）
    
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

*   **返回**

    message S2C
    {
    	repeated Trd_Common.TrdAcc accList = 1; //交易业务账户列表
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

> *   交易品类 [TrdCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6504)
>     
> *   交易业务账户结构参见 [TrdAcc](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8680)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2001
    

`uint GetAccList(TrdGetAccList.Request req);`  
`virtual void OnReply_GetAccList(MMAPI_Conn client, uint nSerialNo, TrdGetAccList.Response rsp);`

*   **介绍**
    
    获取交易业务账户列表
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
    	optional int32 trdCategory = 2; //交易品类，参考 Trd_Common.TrdCategory
    	optional bool needGeneralSecAccount = 3; //是否返回综合账户 （适用于 HK/US/SG/AU 综合账户体系）
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
11  

*   **回调**

    message S2C
    {
    	repeated Trd_Common.TrdAcc accList = 1; //交易业务账户列表
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

> *   交易品类 [TrdCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6504)
>     
> *   交易业务账户结构参见 [TrdAcc](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8680)
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
    
            TrdGetAccList.C2S c2s = TrdGetAccList.C2S.CreateBuilder().SetUserID(5972312)
    				.SetTrdCategory((int)TrdCommon.TrdCategory.TrdCategory_Security)
    				.SetNeedGeneralSecAccount(true)		
                    .Build();
            TrdGetAccList.Request req = TrdGetAccList.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetAccList(req);
            Console.Write("Send TrdGetAccList: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetAccList(MMAPI_Conn client, uint nSerialNo, TrdGetAccList.Response rsp)
        {
            Console.Write("Reply: TrdGetAccList: {0}\n", nSerialNo);
            Console.Write("accID: {0}\n", rsp.S2C.AccListList[0].AccID);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6826806647571888999
    Send TrdGetAccList: 3
    Reply: TrdGetAccList: 3
    accID: 281756455989723220
    

1  
2  
3  
4  

`int getAccList(TrdGetAccList.Request req);`  
`void onReply_GetAccList(MMAPI_Conn client, int nSerialNo, TrdGetAccList.Response rsp);`

*   **介绍**
    
    获取交易业务账户列表
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
    	optional int32 trdCategory = 2; //交易品类，参考 Trd_Common.TrdCategory
    	optional bool needGeneralSecAccount = 3; //是否返回综合账户 （适用于 HK/US/SG/AU 综合账户体系）
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
11  

*   **回调**

    message S2C
    {
    	repeated Trd_Common.TrdAcc accList = 1; //交易业务账户列表
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

> *   交易品类 [TrdCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6504)
>     
> *   交易业务账户结构参见 [TrdAcc](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8680)
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
    
            TrdGetAccList.C2S c2s = TrdGetAccList.C2S.newBuilder().setUserID(900053)
                    .setTrdCategory(TrdCommon.TrdCategory.TrdCategory_Security_VALUE)
                    .setNeedGeneralSecAccount(true)
                    .build();
            TrdGetAccList.Request req = TrdGetAccList.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getAccList(req);
            System.out.printf("Send TrdGetAccList: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetAccList(MMAPI_Conn client, int nSerialNo, TrdGetAccList.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetAccList failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetAccList: %s\n", json);
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

    Send TrdGetAccList: 2
    Receive TrdGetAccList: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "accList": [{\
          "trdEnv": 1,\
          "accID": "281756466778014447",\
          "trdMarketAuthList": [1],\
          "accType": 2,\
          "uniCardNum":"1001263856121256",\
          "cardNum": "1002233560482767",\
          "securityFirm": 1\
        }, ... {\
          "trdEnv": 0,\
          "accID": "3547832",\
          "trdMarketAuthList": [2],\
          "accType": 2,\
          "securityFirm": 0,\
          "simAccType": 2\
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

`moomoo::u32_t GetAccList(const Trd_GetAccList::Request &stReq);`  
`virtual void OnReply_GetAccList(moomoo::u32_t nSerialNo, const Trd_GetAccList::Response &stRsp) = 0;`

*   **介绍**
    
    获取交易业务账户列表
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
    	optional int32 trdCategory = 2; //交易品类，参考 Trd_Common.TrdCategory
    	optional bool needGeneralSecAccount = 3; //是否返回综合账户 （适用于 HK/US/SG/AU 综合账户体系）
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
11  

*   **回调**

    message S2C
    {
    	repeated Trd_Common.TrdAcc accList = 1; //交易业务账户列表
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

> *   交易品类 [TrdCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6504)
>     
> *   交易业务账户结构参见 [TrdAcc](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8680)
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
    
    
    	virtual void OnInitConnect(MMAPI_Conn* pConn, moomoo::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		// 组包
    		Trd_GetAccList::Request req;
    		Trd_GetAccList::C2S *c2s = req.mutable_c2s();
    		c2s->set_userid(12345678);
    		c2s->set_trdcategory(Trd_Common::TrdCategory::TrdCategory_Security);
    		c2s->set_needgeneralsecaccount(true);
    
            m_GetAccListSerialNo = m_pTrdApi->GetAccList(req);
            cout << "Request GetAccList SerialNo: " << m_GetAccListSerialNo << endl;
    	}
    
    	virtual void OnReply_GetAccList(moomoo::u32_t nSerialNo, const Trd_GetAccList::Response &stRsp){
            if(nSerialNo == m_GetAccListSerialNo)
            {
                cout << "OnReply_GetAccList SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Trd *m_pTrdApi;
    
        moomoo::u32_t m_GetAccListSerialNo;
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

*   **Output**

    connect
    Request GetAccList SerialNo: 4
    OnReply_GetAccList SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "accList": [\
       {\
        "trdEnv": 1,\
        "accID": "281756456003951537",\
        "trdMarketAuthList": [\
         1\
        ],\
        "accType": 2,\
        "uniCardNum":"1001263856121256",\
        "cardNum": "1001100320714209",\
        "securityFirm": 1\
       },\
    ...\
       {\
        "trdEnv": 0,\
        "accID": "3637844",\
        "trdMarketAuthList": [\
         2\
        ],\
        "accType": 2,\
        "securityFirm": 0,\
        "simAccType": 2\
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

`GetAccList(req);`

*   **介绍**
    
    获取交易业务账户列表
    
*   **参数**
    

    message C2S
    {
    	required uint64 userID = 1; //历史原因，目前已废弃，填0即可
    	optional int32 trdCategory = 2; //交易品类，参考 Trd_Common.TrdCategory
    	optional bool needGeneralSecAccount = 3; //是否返回综合账户 （适用于 HK/US/SG/AU 综合账户体系）
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
11  

*   **返回**

    message S2C
    {
    	repeated Trd_Common.TrdAcc accList = 1; //交易业务账户列表
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

> *   交易品类 [TrdCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6504)
>     
> *   交易业务账户结构参见 [TrdAcc](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8680)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetAccList(){
        const { RetType } = Common
        const { SubType, QotMarket } = Qot_Common
        const { TrdCategory } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        userID: 0,
                        trdCategory: TrdCategory.TrdCategory_Security,
                        needGeneralSecAccount: true,
                    },
                };
                websocket.GetAccList(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("AccList: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    AccList: errCode 0, retMsg , retType 0
    {
      "accList": [{\
        "trdEnv": 1,\
        "accID": "281756456008357727",\
        "trdMarketAuthList": [1],\
        "accType": 2,\
        "uniCardNum":"1001263856121256",\
        "cardNum": "1001100321506782",\
        "securityFirm": 1\
      }, {\
        "trdEnv": 1,\
        "accID": "281756460303325023",\
        "trdMarketAuthList": [2],\
        "accType": 2,\
        "uniCardNum":"1001256386562384",\
        "cardNum": "1001100521938385",\
        "securityFirm": 1\
      }, ..., {\
        "trdEnv": 0,\
        "accID": "6684976",\
        "trdMarketAuthList": [2],\
        "accType": 2,\
        "securityFirm": 0,\
        "simAccType": 2\
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

← [交易对象](https://openapi.futunn.com/futu-api-doc/trade/base.html) [解锁交易](https://openapi.futunn.com/futu-api-doc/trade/unlock.html)
 →

[获取交易业务账户列表](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html)