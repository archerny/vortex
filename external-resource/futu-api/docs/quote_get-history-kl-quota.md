 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-history-kl-quota.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-history-kl-quota.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-history-kl-quota.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-history-kl-quota.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
    *   [行情接口总览](https://openapi.futunn.com/futu-api-doc/quote/overview.html)
        
    *   [行情对象](https://openapi.futunn.com/futu-api-doc/quote/base.html)
        
    *   实时行情
        
    *   基本数据
        
    *   相关衍生品
        
    *   全市场筛选
        
    *   个性化
        
        *   [获取历史 K 线额度使用明细](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html)
            
        *   [设置到价提醒](https://openapi.futunn.com/futu-api-doc/quote/set-price-reminder.html)
            
        *   [获取到价提醒列表](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html)
            
        *   [获取自选股列表](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html)
            
        *   [获取自选股分组](https://openapi.futunn.com/futu-api-doc/quote/get-user-security-group.html)
            
        *   [修改自选股列表](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html)
            
        *   [到价提醒回调](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html)
            
        
    *   [行情定义](https://openapi.futunn.com/futu-api-doc/quote/quote.html)
        
    
*   交易接口
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html#4184)
 获取历史 K 线额度使用明细
=================================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_history_kl_quota(get_detail=False)`

*   **介绍**
    
    获取历史 K 线额度使用明细
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | get\_detail | bool | 是否返回拉取历史 K 线的详细纪录<br>(ℹ️ True：返回  <br>False：不返回) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | tuple | 当 ret == RET\_OK，返回历史 K 线额度数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   历史 K 线额度数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | used\_quota | int | 已用额度<br>(ℹ️ 即当前周期内已经下载过多少只股票) |
        | remain\_quota | int | 剩余额度 |
        | detail\_list | list | 拉取历史 K 线的详细纪录，含股票代码和拉取时间<br>(ℹ️ list 中元素类型是 dict) |
        
        *   detail\_list 数据列格式如下
            
            | 字段  | 类型  | 说明  |
            | --- | --- | --- |
            | code | str | 股票代码 |
            | name | str | 股票名称 |
            | request\_time | str | 最后一次拉取的时间字符串<br>(ℹ️ 格式：yyyy-MM-dd HH:mm:ss) |
            
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_history_kl_quota(get_detail=True)  # 设置 true 代表需要返回详细的拉取历史 K 线的记录
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

    (2, 98, {'code': 'HK.00123', 'name': '越秀地产', 'request_time': '2023-06-20 19:59:00'}, {'code': 'HK.00700', 'name': '腾讯控股', 'request_time': '2023-07-19 17:48:16'}])
    

1  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html#4658)
 Qot\_RequestHistoryKLQuota.proto
-------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取历史 K 线额度使用明细
    
*   **参数**
    

    message C2S
    {
    	optional bool bGetDetail = 2;	//是否返回详细拉取过的历史纪录
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

    message DetailItem
    {
    	required Qot_Common.Security security = 1;	//拉取的股票
        optional string name = 4; //股票名称
    	required string requestTime = 2;    	//拉取的时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	optional int64 requestTimeStamp = 3;        //拉取的时间戳
    }
    
    message S2C
    {
    	required int32 usedQuota = 1;	      //已使用过的额度，即当前周期内已经下载过多少只股票。
    	required int32 remainQuota = 2;       //剩余额度
    	repeated DetailItem detailList = 3;	  //每只拉取过的股票的下载时间
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
21  
22  
23  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3104
    

`uint RequestHistoryKLQuota(QotRequestHistoryKLQuota.Request req);`  
`virtual void OnReply_RequestHistoryKLQuota(FTAPI_Conn client, uint nSerialNo, QotRequestHistoryKLQuota.Response rsp);`

*   **介绍**
    
    获取历史 K 线额度使用明细
    
*   **参数**
    

    message C2S
    {
    	optional bool bGetDetail = 2;	//是否返回详细拉取过的历史纪录
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

    
    message DetailItem
    {
    	required Qot_Common.Security security = 1;	//拉取的股票
        optional string name = 4; //股票名称
    	required string requestTime = 2;    	//拉取的时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	optional int64 requestTimeStamp = 3;        //拉取的时间戳
    }
    
    message S2C
    {
    	required int32 usedQuota = 1;	      //已使用过的额度，即当前周期内已经下载过多少只股票。
    	required int32 remainQuota = 2;       //剩余额度
    	repeated DetailItem detailList = 3;	  //每只拉取过的股票的下载时间
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
21  
22  
23  
24  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
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
    
            QotRequestHistoryKLQuota.C2S c2s = QotRequestHistoryKLQuota.C2S.CreateBuilder()
                .Build();
            QotRequestHistoryKLQuota.Request req = QotRequestHistoryKLQuota.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.RequestHistoryKLQuota(req);
            Console.Write("Send QotRequestHistoryKLQuota: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_RequestHistoryKLQuota(FTAPI_Conn client, uint nSerialNo, QotRequestHistoryKLQuota.Response rsp) {
            Console.Write("Reply: QotRequestHistoryKLQuota: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("usedQuota: {0} \n", rsp.S2C.UsedQuota);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826782079934349591
    Send QotRequestHistoryKLQuota: 3
    Reply: QotRequestHistoryKLQuota: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      usedQuota: 4
      remainQuota: 296
    }
    
    usedQuota: 4
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

`int requestHistoryKLQuota(QotRequestHistoryKLQuota.Request req);`  
`void onReply_RequestHistoryKLQuota(FTAPI_Conn client, int nSerialNo, QotRequestHistoryKLQuota.Response rsp);`

*   **介绍**
    
    获取历史 K 线额度使用明细
    
*   **参数**
    

    message C2S
    {
    	optional bool bGetDetail = 2;	//是否返回详细拉取过的历史纪录
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

    message DetailItem
    {
    	required Qot_Common.Security security = 1;	//拉取的股票
        optional string name = 4; //股票名称
    	required string requestTime = 2;    	//拉取的时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	optional int64 requestTimeStamp = 3;        //拉取的时间戳
    }
    
    message S2C
    {
    	required int32 usedQuota = 1;	      //已使用过的额度，即当前周期内已经下载过多少只股票。
    	required int32 remainQuota = 2;       //剩余额度
    	repeated DetailItem detailList = 3;	  //每只拉取过的股票的下载时间
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
21  
22  
23  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
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
    
            QotRequestHistoryKLQuota.C2S c2s = QotRequestHistoryKLQuota.C2S.newBuilder()
                .build();
            QotRequestHistoryKLQuota.Request req = QotRequestHistoryKLQuota.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.requestHistoryKLQuota(req);
            System.out.printf("Send QotRequestHistoryKLQuota: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_RequestHistoryKLQuota(FTAPI_Conn client, int nSerialNo, QotRequestHistoryKLQuota.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotRequestHistoryKLQuota failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotRequestHistoryKLQuota: %s\n", json);
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

*   **Output**

    Send QotRequestHistoryKLQuota: 2
    Receive QotRequestHistoryKLQuota: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "usedQuota": 0,
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

`Futu::u32_t RequestHistoryKLQuota(const Qot_RequestHistoryKLQuota::Request &stReq);`  
`virtual void OnReply_RequestHistoryKLQuota(Futu::u32_t nSerialNo, const Qot_RequestHistoryKLQuota::Response &stRsp) = 0;`

*   **介绍**
    
    获取已使用过的历史 K 线额度，即当前周期内已经下载过多少只股票
    
*   **参数**
    

    message C2S
    {
    	optional bool bGetDetail = 2;	//是否返回详细拉取过的历史纪录
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

    message DetailItem
    {
    	required Qot_Common.Security security = 1;	//拉取的股票
        optional string name = 4; //股票名称
    	required string requestTime = 2;    	//拉取的时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	optional int64 requestTimeStamp = 3;        //拉取的时间戳
    }
    
    message S2C
    {
    	required int32 usedQuota = 1;	      //已使用过的额度，即当前周期内已经下载过多少只股票。
    	required int32 remainQuota = 2;       //剩余额度
    	repeated DetailItem detailList = 3;	  //每只拉取过的股票的下载时间
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
21  
22  
23  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
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
    		Qot_RequestHistoryKLQuota::Request req;
    		Qot_RequestHistoryKLQuota::C2S *c2s = req.mutable_c2s();
    
            m_RequestHistoryKLQuotaSerialNo = m_pQotApi->RequestHistoryKLQuota(req);
            cout << "Request RequestHistoryKLQuota SerialNo: " << m_RequestHistoryKLQuotaSerialNo << endl;
    	}
    
    	virtual void OnReply_RequestHistoryKLQuota(Futu::u32_t nSerialNo, const Qot_RequestHistoryKLQuota::Response &stRsp){
            if(nSerialNo == m_RequestHistoryKLQuotaSerialNo)
            {
                cout << "OnReply_RequestHistoryKLQuota SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_RequestHistoryKLQuotaSerialNo;
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
    Request RequestHistoryKLQuota SerialNo: 4
    OnReply_RequestHistoryKLQuota SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "usedQuota": 4,
      "remainQuota": 296
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

`RequestHistoryKLQuota(req);`

*   **介绍**
    
    获取历史 K 线额度使用明细
    
*   **参数**
    

    message C2S
    {
    	optional bool bGetDetail = 2;	//是否返回详细拉取过的历史纪录
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

    message DetailItem
    {
    	required Qot_Common.Security security = 1;	//拉取的股票
        optional string name = 4; //股票名称
    	required string requestTime = 2;    	//拉取的时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	optional int64 requestTimeStamp = 3;        //拉取的时间戳
    }
    
    message S2C
    {
    	required int32 usedQuota = 1;	      //已使用过的额度，即当前周期内已经下载过多少只股票。
    	required int32 remainQuota = 2;       //剩余额度
    	repeated DetailItem detailList = 3;	  //每只拉取过的股票的下载时间
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
21  
22  
23  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotRequestHistoryKLQuota(){
        const { RetType } = Common
        const {  } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        bGetDetail: true,
                    },
                };
    
                websocket.RequestHistoryKLQuota(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("HistoryKLQuota: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    HistoryKLQuota: errCode 0, retMsg , retType 0
    {
      "usedQuota": 1,
      "remainQuota": 99,
      "detailList": [{\
        "security": {\
          "market": 1,\
          "code": "00700"\
        },\
        "requestTime": "2021-09-10 14:17:17",\
        "requestTimeStamp": "1631254637"\
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

接口限制

*   我们会根据您账户的资产和交易的情况，下发历史 K 线额度。因此，30 天内您只能获取有限只股票的历史 K 线数据。具体规则参见 [订阅额度 & 历史 K 线额度](https://openapi.futunn.com/futu-api-doc/intro/authority.html#1314)
    。
*   您当日消耗的历史 K 线额度，会在 30 天后自动释放。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_history_kl_quota(get_detail=False)`

*   **介绍**
    
    获取历史 K 线额度使用明细
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | get\_detail | bool | 是否返回拉取历史 K 线的详细纪录<br>(ℹ️ True：返回  <br>False：不返回) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | tuple | 当 ret == RET\_OK，返回历史 K 线额度数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   历史 K 线额度数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | used\_quota | int | 已用额度<br>(ℹ️ 即当前周期内已经下载过多少只股票) |
        | remain\_quota | int | 剩余额度 |
        | detail\_list | list | 拉取历史 K 线的详细纪录，含股票代码和拉取时间<br>(ℹ️ list 中元素类型是 dict) |
        
        *   detail\_list 数据列格式如下
            
            | 字段  | 类型  | 说明  |
            | --- | --- | --- |
            | code | str | 股票代码 |
            | name | str | 股票名称 |
            | request\_time | str | 最后一次拉取的时间字符串<br>(ℹ️ 格式：yyyy-MM-dd HH:mm:ss) |
            
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_history_kl_quota(get_detail=True)  # 设置 true 代表需要返回详细的拉取历史 K 线的记录
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

    (2, 98, {'code': 'HK.00123', 'name': '越秀地产', 'request_time': '2023-06-20 19:59:00'}, {'code': 'HK.00700', 'name': '腾讯控股', 'request_time': '2023-07-19 17:48:16'}])
    

1  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html#4658-2)
 Qot\_RequestHistoryKLQuota.proto
---------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取历史 K 线额度使用明细
    
*   **参数**
    

    message C2S
    {
    	optional bool bGetDetail = 2;	//是否返回详细拉取过的历史纪录
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

    message DetailItem
    {
    	required Qot_Common.Security security = 1;	//拉取的股票
        optional string name = 4; //股票名称
    	required string requestTime = 2;    	//拉取的时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	optional int64 requestTimeStamp = 3;        //拉取的时间戳
    }
    
    message S2C
    {
    	required int32 usedQuota = 1;	      //已使用过的额度，即当前周期内已经下载过多少只股票。
    	required int32 remainQuota = 2;       //剩余额度
    	repeated DetailItem detailList = 3;	  //每只拉取过的股票的下载时间
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
21  
22  
23  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3104
    

`uint RequestHistoryKLQuota(QotRequestHistoryKLQuota.Request req);`  
`virtual void OnReply_RequestHistoryKLQuota(MMAPI_Conn client, uint nSerialNo, QotRequestHistoryKLQuota.Response rsp);`

*   **介绍**
    
    获取历史 K 线额度使用明细
    
*   **参数**
    

    message C2S
    {
    	optional bool bGetDetail = 2;	//是否返回详细拉取过的历史纪录
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

    
    message DetailItem
    {
    	required Qot_Common.Security security = 1;	//拉取的股票
        optional string name = 4; //股票名称
    	required string requestTime = 2;    	//拉取的时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	optional int64 requestTimeStamp = 3;        //拉取的时间戳
    }
    
    message S2C
    {
    	required int32 usedQuota = 1;	      //已使用过的额度，即当前周期内已经下载过多少只股票。
    	required int32 remainQuota = 2;       //剩余额度
    	repeated DetailItem detailList = 3;	  //每只拉取过的股票的下载时间
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
21  
22  
23  
24  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
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
    
            QotRequestHistoryKLQuota.C2S c2s = QotRequestHistoryKLQuota.C2S.CreateBuilder()
                .Build();
            QotRequestHistoryKLQuota.Request req = QotRequestHistoryKLQuota.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.RequestHistoryKLQuota(req);
            Console.Write("Send QotRequestHistoryKLQuota: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_RequestHistoryKLQuota(MMAPI_Conn client, uint nSerialNo, QotRequestHistoryKLQuota.Response rsp) {
            Console.Write("Reply: QotRequestHistoryKLQuota: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("usedQuota: {0} \n", rsp.S2C.UsedQuota);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826782079934349591
    Send QotRequestHistoryKLQuota: 3
    Reply: QotRequestHistoryKLQuota: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      usedQuota: 4
      remainQuota: 296
    }
    
    usedQuota: 4
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

`int requestHistoryKLQuota(QotRequestHistoryKLQuota.Request req);`  
`void onReply_RequestHistoryKLQuota(MMAPI_Conn client, int nSerialNo, QotRequestHistoryKLQuota.Response rsp);`

*   **介绍**
    
    获取历史 K 线额度使用明细
    
*   **参数**
    

    message C2S
    {
    	optional bool bGetDetail = 2;	//是否返回详细拉取过的历史纪录
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

    message DetailItem
    {
    	required Qot_Common.Security security = 1;	//拉取的股票
        optional string name = 4; //股票名称
    	required string requestTime = 2;    	//拉取的时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	optional int64 requestTimeStamp = 3;        //拉取的时间戳
    }
    
    message S2C
    {
    	required int32 usedQuota = 1;	      //已使用过的额度，即当前周期内已经下载过多少只股票。
    	required int32 remainQuota = 2;       //剩余额度
    	repeated DetailItem detailList = 3;	  //每只拉取过的股票的下载时间
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
21  
22  
23  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
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
    
            QotRequestHistoryKLQuota.C2S c2s = QotRequestHistoryKLQuota.C2S.newBuilder()
                .build();
            QotRequestHistoryKLQuota.Request req = QotRequestHistoryKLQuota.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.requestHistoryKLQuota(req);
            System.out.printf("Send QotRequestHistoryKLQuota: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_RequestHistoryKLQuota(MMAPI_Conn client, int nSerialNo, QotRequestHistoryKLQuota.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotRequestHistoryKLQuota failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotRequestHistoryKLQuota: %s\n", json);
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

*   **Output**

    Send QotRequestHistoryKLQuota: 2
    Receive QotRequestHistoryKLQuota: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "usedQuota": 0,
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

`moomoo::u32_t RequestHistoryKLQuota(const Qot_RequestHistoryKLQuota::Request &stReq);`  
`virtual void OnReply_RequestHistoryKLQuota(moomoo::u32_t nSerialNo, const Qot_RequestHistoryKLQuota::Response &stRsp) = 0;`

*   **介绍**
    
    获取已使用过的历史 K 线额度，即当前周期内已经下载过多少只股票
    
*   **参数**
    

    message C2S
    {
    	optional bool bGetDetail = 2;	//是否返回详细拉取过的历史纪录
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

    message DetailItem
    {
    	required Qot_Common.Security security = 1;	//拉取的股票
        optional string name = 4; //股票名称
    	required string requestTime = 2;    	//拉取的时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	optional int64 requestTimeStamp = 3;        //拉取的时间戳
    }
    
    message S2C
    {
    	required int32 usedQuota = 1;	      //已使用过的额度，即当前周期内已经下载过多少只股票。
    	required int32 remainQuota = 2;       //剩余额度
    	repeated DetailItem detailList = 3;	  //每只拉取过的股票的下载时间
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
21  
22  
23  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
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
    		Qot_RequestHistoryKLQuota::Request req;
    		Qot_RequestHistoryKLQuota::C2S *c2s = req.mutable_c2s();
    
            m_RequestHistoryKLQuotaSerialNo = m_pQotApi->RequestHistoryKLQuota(req);
            cout << "Request RequestHistoryKLQuota SerialNo: " << m_RequestHistoryKLQuotaSerialNo << endl;
    	}
    
    	virtual void OnReply_RequestHistoryKLQuota(moomoo::u32_t nSerialNo, const Qot_RequestHistoryKLQuota::Response &stRsp){
            if(nSerialNo == m_RequestHistoryKLQuotaSerialNo)
            {
                cout << "OnReply_RequestHistoryKLQuota SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_RequestHistoryKLQuotaSerialNo;
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
    Request RequestHistoryKLQuota SerialNo: 4
    OnReply_RequestHistoryKLQuota SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "usedQuota": 4,
      "remainQuota": 296
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

`RequestHistoryKLQuota(req);`

*   **介绍**
    
    获取历史 K 线额度使用明细
    
*   **参数**
    

    message C2S
    {
    	optional bool bGetDetail = 2;	//是否返回详细拉取过的历史纪录
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

    message DetailItem
    {
    	required Qot_Common.Security security = 1;	//拉取的股票
        optional string name = 4; //股票名称
    	required string requestTime = 2;    	//拉取的时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	optional int64 requestTimeStamp = 3;        //拉取的时间戳
    }
    
    message S2C
    {
    	required int32 usedQuota = 1;	      //已使用过的额度，即当前周期内已经下载过多少只股票。
    	required int32 remainQuota = 2;       //剩余额度
    	repeated DetailItem detailList = 3;	  //每只拉取过的股票的下载时间
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
21  
22  
23  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotRequestHistoryKLQuota(){
        const { RetType } = Common
        const {  } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        bGetDetail: true,
                    },
                };
    
                websocket.RequestHistoryKLQuota(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("HistoryKLQuota: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    HistoryKLQuota: errCode 0, retMsg , retType 0
    {
      "usedQuota": 1,
      "remainQuota": 99,
      "detailList": [{\
        "security": {\
          "market": 1,\
          "code": "00700"\
        },\
        "requestTime": "2021-09-10 14:17:17",\
        "requestTimeStamp": "1631254637"\
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

接口限制

*   我们会根据您账户的资产和交易的情况，下发历史 K 线额度。因此，30 天内您只能获取有限只股票的历史 K 线数据。具体规则参见 [订阅额度 & 历史 K 线额度](https://openapi.futunn.com/futu-api-doc/intro/authority.html#1314)
    。
*   您当日消耗的历史 K 线额度，会在 30 天后自动释放。

← [获取交易日历](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html) [设置到价提醒](https://openapi.futunn.com/futu-api-doc/quote/set-price-reminder.html)
 →

[获取历史 K 线额度使用明细](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html)