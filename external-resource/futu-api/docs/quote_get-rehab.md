# 获取复权因子 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quote/get-rehab.html

[#](https://openapi.futunn.com/futu-api-doc/quote/get-rehab.html#770)
 获取复权因子
=============================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_rehab(code)`

*   **介绍**
    
    获取股票的复权因子
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票代码 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回复权数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   复权数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | ex\_div\_date | str | 除权除息日 |
        | split\_base | float | 拆股分子<br>(ℹ️ 拆股比例=拆股分子/拆股分母) |
        | split\_ert | float | 拆股分母 |
        | join\_base | float | 合股分子<br>(ℹ️ 合股比例=合股分子/合股分母) |
        | join\_ert | float | 合股分母 |
        | split\_ratio | float | 拆合股比例<br>(ℹ️ \- 当公司出现合股，5股合1股时，合股分子=5，合股分母=1，拆合股比例=合股分子/合股分母=5/1  <br>\- 当公司出现拆股，1股拆5股时，拆股分子=1，拆股分母=5，拆合股比例=拆股分子/拆股分母=1/5) |
        | per\_cash\_div | float | 每股派现 |
        | bonus\_base | float | 送股分子<br>(ℹ️ 送股比例=送股分子/送股分母) |
        | bonus\_ert | float | 送股分母 |
        | per\_share\_div\_ratio | float | 送股比例<br>(ℹ️ \- 当公司出现送股，5股送1股时，送股分子=5，送股分母=1，送股比例=送股分子/送股分母=5/1) |
        | transfer\_base | float | 转增股分子<br>(ℹ️ 转增股比例=转增股分子/转增股分母) |
        | transfer\_ert | float | 转增股分母 |
        | per\_share\_trans\_ratio | float | 转增股比例<br>(ℹ️ \- 当公司出现转增股，10股转增3股时，转增股分子=10，转增股分母=3，转增股比例=转增股分子/转增股分母=10/3) |
        | allot\_base | float | 配股分子<br>(ℹ️ 配股比例=配股分子/配股分母) |
        | allot\_ert | float | 配股分母 |
        | allotment\_ratio | float | 配股比例<br>(ℹ️ \- 当公司出现配股，5股配1股时，配股分子=5，配股分母=1，配股比例=配股分子/配股分母=5/1) |
        | allotment\_price | float | 配股价 |
        | add\_base | float | 增发股分子<br>(ℹ️ 增发股比例=增发股分子/增发股分母) |
        | add\_ert | float | 增发股分母 |
        | stk\_spo\_ratio | float | 增发比例<br>(ℹ️ \- 当公司出现增发股，1股增发5股时，增发股分子=1，增发股分母=5，增发股比例=增发股分子/增发股分母=1/5) |
        | stk\_spo\_price | float | 增发价格 |
        | spin\_off\_base | float | 分立分子 |
        | spin\_off\_ert | float | 分立分母 |
        | spin\_off\_ratio | float | 分立比例 |
        | forward\_adj\_factorA | float | 前复权因子 A |
        | forward\_adj\_factorB | float | 前复权因子 B |
        | backward\_adj\_factorA | float | 后复权因子 A |
        | backward\_adj\_factorB | float | 后复权因子 B |
        
        前复权价格 = 不复权价格 × 前复权因子 A + 前复权因子 B  
        后复权价格 = 不复权价格 × 后复权因子 A + 后复权因子 B
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_rehab("HK.00700")
    if ret == RET_OK:
        print(data)
        print(data['ex_div_date'][0])    # 取第一条的除权除息日
        print(data['ex_div_date'].values.tolist())   # 转为 list
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

        ex_div_date  split_ratio  per_cash_div  per_share_div_ratio  per_share_trans_ratio  allotment_ratio  allotment_price  stk_spo_ratio  stk_spo_price  spin_off_base   spin_off_ert   spin_off_ratio   forward_adj_factorA  forward_adj_factorB  backward_adj_factorA  backward_adj_factorB
    0   2005-04-19          NaN          0.07                  NaN                    NaN              NaN              NaN            NaN            NaN         NaN         NaN          NaN         1.0                -0.07                   1.0                  0.07
    ..         ...          ...           ...                  ...                    ...              ...              ...            ...            ...                  ...                  ...                   ...                   ...
    15  2019-05-17          NaN          1.00                  NaN                    NaN              NaN              NaN            NaN            NaN         NaN        NaN        NaN           1.0                -1.00                   1.0                  1.00
    
    [16 rows x 16 columns]
    2005-04-19
    ['2005-04-19', '2006-05-15', '2007-05-09', '2008-05-06', '2009-05-06', '2010-05-05', '2011-05-03', '2012-05-18', '2013-05-20', '2014-05-15', '2014-05-16', '2015-05-15', '2016-05-20', '2017-05-19', '2018-05-18', '2019-05-17']
    

1  
2  
3  
4  
5  
6  
7  
8  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-rehab.html#6618)
 Qot\_RequestRehab.proto
-----------------------------------------------------------------------------------------------

*   **介绍**
    
    获取股票的复权因子
    
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
    	repeated Qot_Common.Rehab rehabList = 1; //复权信息
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

> *   复权结构参见 [Rehab](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7370)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3105
    

`uint RequestRehab(QotRequestRehab.Request req);`  
`virtual void OnReply_RequestRehab(FTAPI_Conn client, uint nSerialNo, QotRequestRehab.Response rsp);`

*   **介绍**
    
    获取股票的复权因子
    
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
    	repeated Qot_Common.Rehab rehabList = 1; //复权信息
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

> *   复权结构参见 [Rehab](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7370)
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
    
            QotCommon.Security sec = QotCommon.Security.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetCode("00700")
                    .Build();
            QotRequestRehab.C2S c2s = QotRequestRehab.C2S.CreateBuilder()
                    .SetSecurity(sec)
                .Build();
            QotRequestRehab.Request req = QotRequestRehab.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.RequestRehab(req);
            Console.Write("Send QotRequestRehab: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_RequestRehab(FTAPI_Conn client, uint nSerialNo, QotRequestRehab.Response rsp) {
            Console.Write("Reply: QotRequestRehab: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("fwdFactorA: {0}\n",
                rsp.S2C.RehabListList[0].FwdFactorA);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6819078887638898428
    Send QotRequestRehab: 3
    Reply: QotRequestRehab: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      rehabList {
        time: "2005-04-19"
        companyActFlag: 64
        fwdFactorA: 1
        fwdFactorB: -0.07
        bwdFactorA: 1
        bwdFactorB: 0.07
        dividend: 0.07
        timestamp: 1113840000
      }
      ...
      rehabList {
        time: "2021-05-24"
        companyActFlag: 64
        fwdFactorA: 1
        fwdFactorB: -1.6
        bwdFactorA: 1
        bwdFactorB: 1.6
        dividend: 1.6
        timestamp: 1621785600
      }
    }
    
    fwdFactorA: 1
    

1  
2  
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

`int requestRehab(QotRequestRehab.Request req);`  
`void onReply_RequestRehab(FTAPI_Conn client, int nSerialNo, QotRequestRehab.Response rsp);`

*   **介绍**
    
    获取股票的复权因子
    
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
    	repeated Qot_Common.Rehab rehabList = 1; //复权信息
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

> *   复权结构参见 [Rehab](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7370)
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
            QotRequestRehab.C2S c2s = QotRequestRehab.C2S.newBuilder()
                    .setSecurity(sec)
                .build();
            QotRequestRehab.Request req = QotRequestRehab.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.requestRehab(req);
            System.out.printf("Send QotRequestRehab: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_RequestRehab(FTAPI_Conn client, int nSerialNo, QotRequestRehab.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotRequestRehab failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotRequestRehab: %s\n", json);
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

    Send QotRequestRehab: 2
    Receive QotRequestRehab: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "rehabList": [{\
          "time": "2005-04-19",\
          "companyActFlag": "64",\
          "fwdFactorA": 1.0,\
          "fwdFactorB": -0.07,\
          "bwdFactorA": 1.0,\
          "bwdFactorB": 0.07,\
          "dividend": 0.07,\
          "timestamp": 1.11384E9\
        }, ... {\
          "time": "2021-05-24",\
          "companyActFlag": "64",\
          "fwdFactorA": 1.0,\
          "fwdFactorB": -1.6,\
          "bwdFactorA": 1.0,\
          "bwdFactorB": 1.6,\
          "dividend": 1.6,\
          "timestamp": 1.6217856E9\
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

`Futu::u32_t RequestRehab(const Qot_RequestRehab::Request &stReq);`  
`virtual void OnReply_RequestRehab(Futu::u32_t nSerialNo, const Qot_RequestRehab::Response &stRsp) = 0;`

*   **介绍**
    
    获取股票的复权因子
    
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
    	repeated Qot_Common.Rehab rehabList = 1; //复权信息
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

> *   复权结构参见 [Rehab](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7370)
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
    		Qot_RequestRehab::Request req;
    		Qot_RequestRehab::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_RequestRehabSerialNo = m_pQotApi->RequestRehab(req);
            cout << "Request RequestRehab SerialNo: " << m_RequestRehabSerialNo << endl;
    	}
    
    	virtual void OnReply_RequestRehab(Futu::u32_t nSerialNo, const Qot_RequestRehab::Response &stRsp){
            if(nSerialNo == m_RequestRehabSerialNo)
            {
                cout << "OnReply_RequestRehab SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_RequestRehabSerialNo;
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
    Request RequestRehab SerialNo: 4
    OnReply_RequestRehab SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "rehabList": [\
       {\
        "time": "2005-04-19",\
        "companyActFlag": "64",\
        "fwdFactorA": 1,\
        "fwdFactorB": -0.07,\
        "bwdFactorA": 1,\
        "bwdFactorB": 0.07,\
        "dividend": 0.07,\
        "timestamp": 1113840000\
       },\
    ...\
       {\
        "time": "2021-05-24",\
        "companyActFlag": "64",\
        "fwdFactorA": 1,\
        "fwdFactorB": -1.6,\
        "bwdFactorA": 1,\
        "bwdFactorB": 1.6,\
        "dividend": 1.6,\
        "timestamp": 1621785600\
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

`RequestRehab(req);`

*   **介绍**
    
    获取股票的复权因子
    
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
    	repeated Qot_Common.Rehab rehabList = 1; //复权信息
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

> *   复权结构参见 [Rehab](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7370)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotRequestRehab(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        security:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                    },
                };
    
                websocket.RequestRehab(req)
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

*   **Output**

    OwnerPlate: errCode 0, retMsg , retType 0
    {
      "rehabList": [{\
        "time": "2005-04-19",\
        "companyActFlag": "64",\
        "fwdFactorA": 1,\
        "fwdFactorB": -0.07,\
        "bwdFactorA": 1,\
        "bwdFactorB": 0.07,\
        "dividend": 0.07,\
        "timestamp": 1113840000\
      }, {\
        "time": "2006-05-15",\
        "companyActFlag": "64",\
        "fwdFactorA": 1,\
        "fwdFactorB": -0.08,\
        "bwdFactorA": 1,\
        "bwdFactorB": 0.08,\
        "dividend": 0.08,\
        "timestamp": 1147622400\
      }, ..., {\
        "time": "2021-05-24",\
        "companyActFlag": "64",\
        "fwdFactorA": 1,\
        "fwdFactorB": -1.6,\
        "bwdFactorA": 1,\
        "bwdFactorB": 1.6,\
        "dividend": 1.6,\
        "timestamp": 1621785600\
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

*   每 30 秒内最多请求 60 次获取复权因子接口。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_rehab(code)`

*   **介绍**
    
    获取股票的复权因子
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票代码 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回复权数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   复权数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | ex\_div\_date | str | 除权除息日 |
        | split\_base | float | 拆股分子<br>(ℹ️ 拆股比例=拆股分子/拆股分母) |
        | split\_ert | float | 拆股分母 |
        | join\_base | float | 合股分子<br>(ℹ️ 合股比例=合股分子/合股分母) |
        | join\_ert | float | 合股分母 |
        | split\_ratio | float | 拆合股比例<br>(ℹ️ \- 当公司出现合股，5股合1股时，合股分子=5，合股分母=1，拆合股比例=合股分子/合股分母=5/1  <br>\- 当公司出现拆股，1股拆5股时，拆股分子=1，拆股分母=5，拆合股比例=拆股分子/拆股分母=1/5) |
        | per\_cash\_div | float | 每股派现 |
        | bonus\_base | float | 送股分子<br>(ℹ️ 送股比例=送股分子/送股分母) |
        | bonus\_ert | float | 送股分母 |
        | per\_share\_div\_ratio | float | 送股比例<br>(ℹ️ \- 当公司出现送股，5股送1股时，送股分子=5，送股分母=1，送股比例=送股分子/送股分母=5/1) |
        | transfer\_base | float | 转增股分子<br>(ℹ️ 转增股比例=转增股分子/转增股分母) |
        | transfer\_ert | float | 转增股分母 |
        | per\_share\_trans\_ratio | float | 转增股比例<br>(ℹ️ \- 当公司出现转增股，10股转增3股时，转增股分子=10，转增股分母=3，转增股比例=转增股分子/转增股分母=10/3) |
        | allot\_base | float | 配股分子<br>(ℹ️ 配股比例=配股分子/配股分母) |
        | allot\_ert | float | 配股分母 |
        | allotment\_ratio | float | 配股比例<br>(ℹ️ \- 当公司出现配股，5股配1股时，配股分子=5，配股分母=1，配股比例=配股分子/配股分母=5/1) |
        | allotment\_price | float | 配股价 |
        | add\_base | float | 增发股分子<br>(ℹ️ 增发股比例=增发股分子/增发股分母) |
        | add\_ert | float | 增发股分母 |
        | stk\_spo\_ratio | float | 增发比例<br>(ℹ️ \- 当公司出现增发股，1股增发5股时，增发股分子=1，增发股分母=5，增发股比例=增发股分子/增发股分母=1/5) |
        | stk\_spo\_price | float | 增发价格 |
        | spin\_off\_base | float | 分立分子 |
        | spin\_off\_ert | float | 分立分母 |
        | spin\_off\_ratio | float | 分立比例 |
        | forward\_adj\_factorA | float | 前复权因子 A |
        | forward\_adj\_factorB | float | 前复权因子 B |
        | backward\_adj\_factorA | float | 后复权因子 A |
        | backward\_adj\_factorB | float | 后复权因子 B |
        
        前复权价格 = 不复权价格 × 前复权因子 A + 前复权因子 B  
        后复权价格 = 不复权价格 × 后复权因子 A + 后复权因子 B
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_rehab("HK.00700")
    if ret == RET_OK:
        print(data)
        print(data['ex_div_date'][0])    # 取第一条的除权除息日
        print(data['ex_div_date'].values.tolist())   # 转为 list
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

        ex_div_date  split_ratio  per_cash_div  per_share_div_ratio  per_share_trans_ratio  allotment_ratio  allotment_price  stk_spo_ratio  stk_spo_price  spin_off_base     spin_off_ert      spin_off_ratio   forward_adj_factorA  forward_adj_factorB  backward_adj_factorA  backward_adj_factorB
    0   2005-04-19          NaN          0.07                  NaN                    NaN              NaN              NaN            NaN            NaN          NaN          NaN        NaN        1.0                -0.07                   1.0                  0.07
    ..         ...          ...           ...                  ...                    ...              ...              ...            ...            ...                  ...                  ...                   ...                   ...
    15  2019-05-17          NaN          1.00                  NaN                    NaN              NaN              NaN            NaN            NaN         NaN         NaN        NaN         1.0                -1.00                   1.0                  1.00
    
    [16 rows x 16 columns]
    2005-04-19
    ['2005-04-19', '2006-05-15', '2007-05-09', '2008-05-06', '2009-05-06', '2010-05-05', '2011-05-03', '2012-05-18', '2013-05-20', '2014-05-15', '2014-05-16', '2015-05-15', '2016-05-20', '2017-05-19', '2018-05-18', '2019-05-17']
    

1  
2  
3  
4  
5  
6  
7  
8  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-rehab.html#6618-2)
 Qot\_RequestRehab.proto
-------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取股票的复权因子
    
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
    	repeated Qot_Common.Rehab rehabList = 1; //复权信息
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

> *   复权结构参见 [Rehab](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7370)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3105
    

`uint RequestRehab(QotRequestRehab.Request req);`  
`virtual void OnReply_RequestRehab(MMAPI_Conn client, uint nSerialNo, QotRequestRehab.Response rsp);`

*   **介绍**
    
    获取股票的复权因子
    
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
    	repeated Qot_Common.Rehab rehabList = 1; //复权信息
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

> *   复权结构参见 [Rehab](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7370)
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
    
            QotCommon.Security sec = QotCommon.Security.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetCode("00700")
                    .Build();
            QotRequestRehab.C2S c2s = QotRequestRehab.C2S.CreateBuilder()
                    .SetSecurity(sec)
                .Build();
            QotRequestRehab.Request req = QotRequestRehab.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.RequestRehab(req);
            Console.Write("Send QotRequestRehab: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_RequestRehab(MMAPI_Conn client, uint nSerialNo, QotRequestRehab.Response rsp) {
            Console.Write("Reply: QotRequestRehab: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("fwdFactorA: {0}\n",
                rsp.S2C.RehabListList[0].FwdFactorA);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6819078887638898428
    Send QotRequestRehab: 3
    Reply: QotRequestRehab: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      rehabList {
        time: "2005-04-19"
        companyActFlag: 64
        fwdFactorA: 1
        fwdFactorB: -0.07
        bwdFactorA: 1
        bwdFactorB: 0.07
        dividend: 0.07
        timestamp: 1113840000
      }
      ...
      rehabList {
        time: "2021-05-24"
        companyActFlag: 64
        fwdFactorA: 1
        fwdFactorB: -1.6
        bwdFactorA: 1
        bwdFactorB: 1.6
        dividend: 1.6
        timestamp: 1621785600
      }
    }
    
    fwdFactorA: 1
    

1  
2  
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

`int requestRehab(QotRequestRehab.Request req);`  
`void onReply_RequestRehab(MMAPI_Conn client, int nSerialNo, QotRequestRehab.Response rsp);`

*   **介绍**
    
    获取股票的复权因子
    
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
    	repeated Qot_Common.Rehab rehabList = 1; //复权信息
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

> *   复权结构参见 [Rehab](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7370)
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
            QotRequestRehab.C2S c2s = QotRequestRehab.C2S.newBuilder()
                    .setSecurity(sec)
                .build();
            QotRequestRehab.Request req = QotRequestRehab.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.requestRehab(req);
            System.out.printf("Send QotRequestRehab: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_RequestRehab(MMAPI_Conn client, int nSerialNo, QotRequestRehab.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotRequestRehab failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotRequestRehab: %s\n", json);
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

    Send QotRequestRehab: 2
    Receive QotRequestRehab: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "rehabList": [{\
          "time": "2005-04-19",\
          "companyActFlag": "64",\
          "fwdFactorA": 1.0,\
          "fwdFactorB": -0.07,\
          "bwdFactorA": 1.0,\
          "bwdFactorB": 0.07,\
          "dividend": 0.07,\
          "timestamp": 1.11384E9\
        }, ... {\
          "time": "2021-05-24",\
          "companyActFlag": "64",\
          "fwdFactorA": 1.0,\
          "fwdFactorB": -1.6,\
          "bwdFactorA": 1.0,\
          "bwdFactorB": 1.6,\
          "dividend": 1.6,\
          "timestamp": 1.6217856E9\
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

`moomoo::u32_t RequestRehab(const Qot_RequestRehab::Request &stReq);`  
`virtual void OnReply_RequestRehab(moomoo::u32_t nSerialNo, const Qot_RequestRehab::Response &stRsp) = 0;`

*   **介绍**
    
    获取股票的复权因子
    
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
    	repeated Qot_Common.Rehab rehabList = 1; //复权信息
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

> *   复权结构参见 [Rehab](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7370)
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
    
    
    	virtual void OnInitConnect(MMAPI_Conn* pConn, Futu::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		// 组包
    		Qot_RequestRehab::Request req;
    		Qot_RequestRehab::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_RequestRehabSerialNo = m_pQotApi->RequestRehab(req);
            cout << "Request RequestRehab SerialNo: " << m_RequestRehabSerialNo << endl;
    	}
    
    	virtual void OnReply_RequestRehab(Futu::u32_t nSerialNo, const Qot_RequestRehab::Response &stRsp){
            if(nSerialNo == m_RequestRehabSerialNo)
            {
                cout << "OnReply_RequestRehab SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_RequestRehabSerialNo;
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
    Request RequestRehab SerialNo: 4
    OnReply_RequestRehab SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "rehabList": [\
       {\
        "time": "2005-04-19",\
        "companyActFlag": "64",\
        "fwdFactorA": 1,\
        "fwdFactorB": -0.07,\
        "bwdFactorA": 1,\
        "bwdFactorB": 0.07,\
        "dividend": 0.07,\
        "timestamp": 1113840000\
       },\
    ...\
       {\
        "time": "2021-05-24",\
        "companyActFlag": "64",\
        "fwdFactorA": 1,\
        "fwdFactorB": -1.6,\
        "bwdFactorA": 1,\
        "bwdFactorB": 1.6,\
        "dividend": 1.6,\
        "timestamp": 1621785600\
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

`RequestRehab(req);`

*   **介绍**
    
    获取股票的复权因子
    
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
    	repeated Qot_Common.Rehab rehabList = 1; //复权信息
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

> *   复权结构参见 [Rehab](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7370)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotRequestRehab(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        security:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                    },
                };
    
                websocket.RequestRehab(req)
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

*   **Output**

    OwnerPlate: errCode 0, retMsg , retType 0
    {
      "rehabList": [{\
        "time": "2005-04-19",\
        "companyActFlag": "64",\
        "fwdFactorA": 1,\
        "fwdFactorB": -0.07,\
        "bwdFactorA": 1,\
        "bwdFactorB": 0.07,\
        "dividend": 0.07,\
        "timestamp": 1113840000\
      }, {\
        "time": "2006-05-15",\
        "companyActFlag": "64",\
        "fwdFactorA": 1,\
        "fwdFactorB": -0.08,\
        "bwdFactorA": 1,\
        "bwdFactorB": 0.08,\
        "dividend": 0.08,\
        "timestamp": 1147622400\
      }, ..., {\
        "time": "2021-05-24",\
        "companyActFlag": "64",\
        "fwdFactorA": 1,\
        "fwdFactorB": -1.6,\
        "bwdFactorA": 1,\
        "bwdFactorB": 1.6,\
        "dividend": 1.6,\
        "timestamp": 1621785600\
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

*   每 30 秒内最多请求 60 次获取复权因子接口。

← [获取历史 K 线](https://openapi.futunn.com/futu-api-doc/quote/request-history-kline.html) [获取期权链到期日](https://openapi.futunn.com/futu-api-doc/quote/get-option-expiration-date.html)
 →