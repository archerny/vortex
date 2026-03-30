 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-plate-stock.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-plate-stock.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-plate-stock.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-plate-stock.html)
    

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
        
        *   [条件选股](https://openapi.futunn.com/futu-api-doc/quote/get-stock-filter.html)
            
        *   [获取板块内股票列表](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html)
            
        *   [获取板块列表](https://openapi.futunn.com/futu-api-doc/quote/get-plate-list.html)
            
        *   [获取静态数据](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html)
            
        *   [获取 IPO 信息](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html)
            
        *   [获取全局市场状态](https://openapi.futunn.com/futu-api-doc/quote/get-global-state.html)
            
        *   [获取交易日历](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html)
            
        
    *   个性化
        
    *   [行情定义](https://openapi.futunn.com/futu-api-doc/quote/quote.html)
        
    
*   交易接口
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html#9146)
 获取板块内股票列表
=======================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_plate_stock(plate_code, sort_field=SortField.CODE, ascend=True)`

*   **介绍**
    
    获取指定板块内的股票列表，获取股指的成分股
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | plate\_code | str | 板块代码<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>先利用 [获取板块列表](https://openapi.futunn.com/futu-api-doc/quote/get-plate-list.html)<br> 获取板块代码  <br>例如：“SH.BK0001”，“SH.BK0002” |
    | sort\_field | [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930) | 排序字段 |
    | ascend | bool | 排序方向<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：升序  <br>False：降序 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回板块股票数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   板块股票数据
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | lot\_size | int | 每手股数，期货表示合约乘数 |
        | stock\_name | str | 股票名称 |
        | stock\_type | [SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325) | 股票类型 |
        | list\_time | str | 上市时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间 |
        | stock\_id | int | 股票 ID |
        | main\_contract | bool | 是否主连合约<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货特有字段 |
        | last\_trade\_time | str | 最后交易时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货特有字段  <br>主连，当月，下月等期货没有该字段 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_plate_stock('HK.BK1001')
    if ret == RET_OK:
        print(data)
        print(data['stock_name'][0])    # 取第一条的股票名称
        print(data['stock_name'].values.tolist())   # 转为 list
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

        code  lot_size stock_name  stock_owner  stock_child_type stock_type   list_time        stock_id  main_contract last_trade_time
    0   HK.00462      4000       天然乳品          NaN               NaN      STOCK  2005-06-10  55589761712590          False                
    ..       ...       ...        ...          ...               ...        ...         ...             ...            ...             ...
    9   HK.06186      1000       中国飞鹤          NaN               NaN      STOCK  2019-11-13  78159814858794          False               
    
    [10 rows x 10 columns]
    天然乳品
    ['天然乳品', '现代牧业', '雅士利国际', '原生态牧业', '中国圣牧', '中地乳业', '庄园牧场', '澳优', '蒙牛乳业', '中国飞鹤']
    

1  
2  
3  
4  
5  
6  
7  
8  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html#5662)
 Qot\_GetPlateSecurity.proto
---------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取指定板块内的股票列表，获取股指的成分股
    
*   **参数**
    

    message C2S
    {
        required Qot_Common.Security plate = 1; //板块
        optional int32 sortField = 2;//Qot_Common.SortField，根据哪个字段排序，不填默认 Code 排序
        optional bool ascend = 3;//升序 true，降序 false，不填默认升序
    
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
12  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序字段参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //板块下的股票静态信息
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3205
    

`uint GetPlateSecurity(QotGetPlateSecurity.Request req);`  
`virtual void OnReply_GetPlateSecurity(FTAPI_Conn client, uint nSerialNo, QotGetPlateSecurity.Response rsp);`

*   **介绍**
    
    获取指定板块内的股票列表，获取股指的成分股
    
*   **参数**
    

    message C2S
    {
        required Qot_Common.Security plate = 1; //板块
        optional int32 sortField = 2;//Qot_Common.SortField，根据哪个字段排序，不填默认 Code 排序
        optional bool ascend = 3;//升序 true，降序 false，不填默认升序
    
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
12  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序字段参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //板块下的股票静态信息
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
                    .SetCode("BK1001")
                    .Build();
            QotGetPlateSecurity.C2S c2s = QotGetPlateSecurity.C2S.CreateBuilder()
                    .SetPlate(sec)
                .Build();
            QotGetPlateSecurity.Request req = QotGetPlateSecurity.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetPlateSecurity(req);
            Console.Write("Send QotGetPlateSecurity: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_GetPlateSecurity(FTAPI_Conn client, uint nSerialNo, QotGetPlateSecurity.Response rsp)
        {
            Console.Write("Reply: QotGetPlateSecurity: {0}\n", nSerialNo);
            Console.Write("name: {0}, secType: {1} \n", rsp.S2C.StaticInfoListList[0].Basic.Name, 
                rsp.S2C.StaticInfoListList[0].Basic.SecType);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825732202242203206
    Send QotGetPlateSecurity: 3
    Reply: QotGetPlateSecurity: 3
    name: 天然乳品, secType: 3
    

1  
2  
3  
4  

`int getPlateSecurity(QotGetPlateSecurity.Request req);`  
`void onReply_GetPlateSecurity(FTAPI_Conn client, int nSerialNo, QotGetPlateSecurity.Response rsp);`

*   **介绍**
    
    获取指定板块内的股票列表，获取股指的成分股
    
*   **参数**
    

    message C2S
    {
        required Qot_Common.Security plate = 1; //板块
        optional int32 sortField = 2;//Qot_Common.SortField，根据哪个字段排序，不填默认 Code 排序
        optional bool ascend = 3;//升序 true，降序 false，不填默认升序
    
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
12  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序字段参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //板块下的股票静态信息
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
                    .setCode("BK1001")
                    .build();
            QotGetPlateSecurity.C2S c2s = QotGetPlateSecurity.C2S.newBuilder()
                    .setPlate(sec)
                .build();
            QotGetPlateSecurity.Request req = QotGetPlateSecurity.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getPlateSecurity(req);
            System.out.printf("Send QotGetPlateSecurity: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetPlateSecurity(FTAPI_Conn client, int nSerialNo, QotGetPlateSecurity.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetPlateSecurity failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetPlateSecurity: %s\n", json);
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

    Send QotGetPlateSecurity: 2
    Receive QotGetPlateSecurity: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "staticInfoList": [{\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "00462"\
            },\
            "id": "55589761712590",\
            "lotSize": 4000,\
            "secType": 3,\
            "name": "天然乳品",\
            "listTime": "2005-06-10",\
            "delisting": false,\
            "listTimestamp": 1.1183328E9\
          }\
        }, ... {\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "09858"\
            },\
            "id": "80676665697922",\
            "lotSize": 1000,\
            "secType": 3,\
            "name": "优然牧业",\
            "listTime": "2021-06-18",\
            "delisting": false,\
            "listTimestamp": 1.6239456E9\
          }\
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
30  
31  
32  
33  
34  
35  
36  
37  

`Futu::u32_t GetPlateSecurity(const Qot_GetPlateSecurity::Request &stReq);`  
`virtual void OnReply_GetPlateSecurity(Futu::u32_t nSerialNo, const Qot_GetPlateSecurity::Response &stRsp) = 0;`

*   **介绍**
    
    获取指定板块内的股票列表，获取股指的成分股
    
*   **参数**
    

    message C2S
    {
        required Qot_Common.Security plate = 1; //板块
        optional int32 sortField = 2;//Qot_Common.SortField，根据哪个字段排序，不填默认 Code 排序
        optional bool ascend = 3;//升序 true，降序 false，不填默认升序
    
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
12  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序字段参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //板块下的股票静态信息
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
    		Qot_GetPlateSecurity::Request req;
    		Qot_GetPlateSecurity::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_plate();
    		sec->set_code("BK1001");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetPlateSecuritySerialNo = m_pQotApi->GetPlateSecurity(req);
            cout << "Request GetPlateSecurity SerialNo: " << m_GetPlateSecuritySerialNo << endl;
    	}
    
    	virtual void OnReply_GetPlateSecurity(Futu::u32_t nSerialNo, const Qot_GetPlateSecurity::Response &stRsp){
            if(nSerialNo == m_GetPlateSecuritySerialNo)
            {
                cout << "OnReply_GetPlateSecurity SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetPlateSecuritySerialNo;
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
    Request GetPlateSecurity SerialNo: 4
    OnReply_GetPlateSecurity SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "staticInfoList": [\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "00462"\
         },\
         "id": "55589761712590",\
         "lotSize": 4000,\
         "secType": 3,\
         "name": "天然乳品",\
         "listTime": "2005-06-10",\
         "delisting": false,\
         "listTimestamp": 1118332800\
        }\
       },\
    ...\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "06186"\
         },\
         "id": "78159814858794",\
         "lotSize": 1000,\
         "secType": 3,\
         "name": "中国飞鹤",\
         "listTime": "2019-11-13",\
         "delisting": false,\
         "listTimestamp": 1573574400\
        }\
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
39  
40  
41  
42  
43  
44  

`GetPlateSecurity(req);`

*   **介绍**
    
    获取板块内的股票
    
*   **参数**
    

    message C2S
    {
        required Qot_Common.Security plate = 1; //板块
        optional int32 sortField = 2;//Qot_Common.SortField，根据哪个字段排序，不填默认 Code 排序
        optional bool ascend = 3;//升序 true，降序 false，不填默认升序
    
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
12  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序字段参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //板块下的股票静态信息
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetPlateSecurity(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        plate:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "Motherboard",
                        },
                    },
                };
    
                websocket.GetPlateSecurity(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("PlateSecurity: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    PlateSecurity: errCode 0, retMsg , retType 0
    {
      "staticInfoList": [{\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "00001"\
          },\
          "id": "4440996184065",\
          "lotSize": 500,\
          "secType": 3,\
          "name": "长和",\
          "listTime": "2015-03-18",\
          "delisting": false,\
          "listTimestamp": 1426608000\
        }\
      }, {\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "00002"\
          },\
          "id": "2",\
          "lotSize": 500,\
          "secType": 3,\
          "name": "中电控股",\
          "listTime": "1970-01-01",\
          "delisting": false,\
          "listTimestamp": 0\
        }\
      }, ..., {\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "87001"\
          },\
          "id": "64819646518233",\
          "lotSize": 1000,\
          "secType": 4,\
          "name": "汇贤产业信托",\
          "listTime": "2011-04-29",\
          "delisting": false,\
          "listTimestamp": 1304006400\
        }\
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

接口限制

*   每 30 秒内最多请求 10 次获取板块内股票列表接口

常用的板块、指数代码

| 代码  | 说明  |
| --- | --- |
| HK.HSI Constituent Stocks | 恒指成份股 |
| HK.HSCEI Stock | 国指成份股 |
| HK.Motherboard | 港股主板 |
| HK.GEM | 港股创业板 |
| HK.LIST1910 | 所有港股 |
| HK.LIST1911 | 主板 H 股 |
| HK.LIST1912 | 创业板 H 股 |
| HK.Fund | ETF（港股基金） |
| HK.LIST1600 | 热度榜（港） |
| HK.LIST1921 | 已上市新股-港股 |
| SH.LIST3000000 | 上海主板 |
| SH.LIST0901 | 上证 B 股 |
| SH.LIST0902 | 深证 B 股 |
| SH.LIST3000002 | 沪深指数 |
| SH.LIST3000005 | 全部 A 股（沪深） |
| SH.LIST0600 | 热度榜（沪深） |
| SH.LIST0992 | 科创板 |
| SH.LIST0921 | 已上市新股-A 股 |
| SZ.LIST3000001 | 深证主板 |
| SZ.LIST3000003 | 中小板 |
| SZ.LIST3000004 | 创业板（深） |
| US.USAALL | 全部美股 |

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_plate_stock(plate_code, sort_field=SortField.CODE, ascend=True)`

*   **介绍**
    
    获取指定板块内的股票列表，获取股指的成分股
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | plate\_code | str | 板块代码<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>先利用 [获取板块列表](https://openapi.futunn.com/futu-api-doc/quote/get-plate-list.html)<br> 获取板块代码  <br>例如：“SH.BK0001”，“SH.BK0002” |
    | sort\_field | [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930) | 排序字段 |
    | ascend | bool | 排序方向<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：升序  <br>False：降序 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回板块股票数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   板块股票数据
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | lot\_size | int | 每手股数，期货表示合约乘数 |
        | stock\_name | str | 股票名称 |
        | stock\_type | [SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325) | 股票类型 |
        | list\_time | str | 上市时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间 |
        | stock\_id | int | 股票 ID |
        | main\_contract | bool | 是否主连合约<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货特有字段 |
        | last\_trade\_time | str | 最后交易时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货特有字段  <br>主连，当月，下月等期货没有该字段 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_plate_stock('HK.BK1001')
    if ret == RET_OK:
        print(data)
        print(data['stock_name'][0])    # 取第一条的股票名称
        print(data['stock_name'].values.tolist())   # 转为 list
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

        code  lot_size stock_name  stock_owner  stock_child_type stock_type   list_time        stock_id  main_contract last_trade_time
    0   HK.00462      4000       天然乳品          NaN               NaN      STOCK  2005-06-10  55589761712590          False                
    ..       ...       ...        ...          ...               ...        ...         ...             ...            ...             ...
    9   HK.06186      1000       中国飞鹤          NaN               NaN      STOCK  2019-11-13  78159814858794          False               
    
    [10 rows x 10 columns]
    天然乳品
    ['天然乳品', '现代牧业', '雅士利国际', '原生态牧业', '中国圣牧', '中地乳业', '庄园牧场', '澳优', '蒙牛乳业', '中国飞鹤']
    

1  
2  
3  
4  
5  
6  
7  
8  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html#5662-2)
 Qot\_GetPlateSecurity.proto
-----------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取指定板块内的股票列表，获取股指的成分股
    
*   **参数**
    

    message C2S
    {
        required Qot_Common.Security plate = 1; //板块
        optional int32 sortField = 2;//Qot_Common.SortField，根据哪个字段排序，不填默认 Code 排序
        optional bool ascend = 3;//升序 true，降序 false，不填默认升序
    
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
12  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序字段参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //板块下的股票静态信息
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3205
    

`uint GetPlateSecurity(QotGetPlateSecurity.Request req);`  
`virtual void OnReply_GetPlateSecurity(MMAPI_Conn client, uint nSerialNo, QotGetPlateSecurity.Response rsp);`

*   **介绍**
    
    获取指定板块内的股票列表，获取股指的成分股
    
*   **参数**
    

    message C2S
    {
        required Qot_Common.Security plate = 1; //板块
        optional int32 sortField = 2;//Qot_Common.SortField，根据哪个字段排序，不填默认 Code 排序
        optional bool ascend = 3;//升序 true，降序 false，不填默认升序
    
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
12  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序字段参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //板块下的股票静态信息
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
                    .SetCode("BK1001")
                    .Build();
            QotGetPlateSecurity.C2S c2s = QotGetPlateSecurity.C2S.CreateBuilder()
                    .SetPlate(sec)
                .Build();
            QotGetPlateSecurity.Request req = QotGetPlateSecurity.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetPlateSecurity(req);
            Console.Write("Send QotGetPlateSecurity: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_GetPlateSecurity(MMAPI_Conn client, uint nSerialNo, QotGetPlateSecurity.Response rsp)
        {
            Console.Write("Reply: QotGetPlateSecurity: {0}\n", nSerialNo);
            Console.Write("name: {0}, secType: {1} \n", rsp.S2C.StaticInfoListList[0].Basic.Name, 
                rsp.S2C.StaticInfoListList[0].Basic.SecType);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825732202242203206
    Send QotGetPlateSecurity: 3
    Reply: QotGetPlateSecurity: 3
    name: 天然乳品, secType: 3
    

1  
2  
3  
4  

`int getPlateSecurity(QotGetPlateSecurity.Request req);`  
`void onReply_GetPlateSecurity(MMAPI_Conn client, int nSerialNo, QotGetPlateSecurity.Response rsp);`

*   **介绍**
    
    获取指定板块内的股票列表，获取股指的成分股
    
*   **参数**
    

    message C2S
    {
        required Qot_Common.Security plate = 1; //板块
        optional int32 sortField = 2;//Qot_Common.SortField，根据哪个字段排序，不填默认 Code 排序
        optional bool ascend = 3;//升序 true，降序 false，不填默认升序
    
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
12  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序字段参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //板块下的股票静态信息
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
                    .setCode("BK1001")
                    .build();
            QotGetPlateSecurity.C2S c2s = QotGetPlateSecurity.C2S.newBuilder()
                    .setPlate(sec)
                .build();
            QotGetPlateSecurity.Request req = QotGetPlateSecurity.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getPlateSecurity(req);
            System.out.printf("Send QotGetPlateSecurity: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetPlateSecurity(MMAPI_Conn client, int nSerialNo, QotGetPlateSecurity.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetPlateSecurity failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetPlateSecurity: %s\n", json);
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

    Send QotGetPlateSecurity: 2
    Receive QotGetPlateSecurity: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "staticInfoList": [{\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "00462"\
            },\
            "id": "55589761712590",\
            "lotSize": 4000,\
            "secType": 3,\
            "name": "天然乳品",\
            "listTime": "2005-06-10",\
            "delisting": false,\
            "listTimestamp": 1.1183328E9\
          }\
        }, ... {\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "09858"\
            },\
            "id": "80676665697922",\
            "lotSize": 1000,\
            "secType": 3,\
            "name": "优然牧业",\
            "listTime": "2021-06-18",\
            "delisting": false,\
            "listTimestamp": 1.6239456E9\
          }\
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
30  
31  
32  
33  
34  
35  
36  
37  

`moomoo::u32_t GetPlateSecurity(const Qot_GetPlateSecurity::Request &stReq);`  
`virtual void OnReply_GetPlateSecurity(moomoo::u32_t nSerialNo, const Qot_GetPlateSecurity::Response &stRsp) = 0;`

*   **介绍**
    
    获取指定板块内的股票列表，获取股指的成分股
    
*   **参数**
    

    message C2S
    {
        required Qot_Common.Security plate = 1; //板块
        optional int32 sortField = 2;//Qot_Common.SortField，根据哪个字段排序，不填默认 Code 排序
        optional bool ascend = 3;//升序 true，降序 false，不填默认升序
    
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
12  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序字段参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //板块下的股票静态信息
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
    		Qot_GetPlateSecurity::Request req;
    		Qot_GetPlateSecurity::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_plate();
    		sec->set_code("BK1001");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetPlateSecuritySerialNo = m_pQotApi->GetPlateSecurity(req);
            cout << "Request GetPlateSecurity SerialNo: " << m_GetPlateSecuritySerialNo << endl;
    	}
    
    	virtual void OnReply_GetPlateSecurity(moomoo::u32_t nSerialNo, const Qot_GetPlateSecurity::Response &stRsp){
            if(nSerialNo == m_GetPlateSecuritySerialNo)
            {
                cout << "OnReply_GetPlateSecurity SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetPlateSecuritySerialNo;
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
    Request GetPlateSecurity SerialNo: 4
    OnReply_GetPlateSecurity SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "staticInfoList": [\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "00462"\
         },\
         "id": "55589761712590",\
         "lotSize": 4000,\
         "secType": 3,\
         "name": "天然乳品",\
         "listTime": "2005-06-10",\
         "delisting": false,\
         "listTimestamp": 1118332800\
        }\
       },\
    ...\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "06186"\
         },\
         "id": "78159814858794",\
         "lotSize": 1000,\
         "secType": 3,\
         "name": "中国飞鹤",\
         "listTime": "2019-11-13",\
         "delisting": false,\
         "listTimestamp": 1573574400\
        }\
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
39  
40  
41  
42  
43  
44  

`GetPlateSecurity(req);`

*   **介绍**
    
    获取板块内的股票
    
*   **参数**
    

    message C2S
    {
        required Qot_Common.Security plate = 1; //板块
        optional int32 sortField = 2;//Qot_Common.SortField，根据哪个字段排序，不填默认 Code 排序
        optional bool ascend = 3;//升序 true，降序 false，不填默认升序
    
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
12  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序字段参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.SecurityStaticInfo staticInfoList = 1; //板块下的股票静态信息
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetPlateSecurity(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        plate:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "Motherboard",
                        },
                    },
                };
    
                websocket.GetPlateSecurity(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("PlateSecurity: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    PlateSecurity: errCode 0, retMsg , retType 0
    {
      "staticInfoList": [{\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "00001"\
          },\
          "id": "4440996184065",\
          "lotSize": 500,\
          "secType": 3,\
          "name": "长和",\
          "listTime": "2015-03-18",\
          "delisting": false,\
          "listTimestamp": 1426608000\
        }\
      }, {\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "00002"\
          },\
          "id": "2",\
          "lotSize": 500,\
          "secType": 3,\
          "name": "中电控股",\
          "listTime": "1970-01-01",\
          "delisting": false,\
          "listTimestamp": 0\
        }\
      }, ..., {\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "87001"\
          },\
          "id": "64819646518233",\
          "lotSize": 1000,\
          "secType": 4,\
          "name": "汇贤产业信托",\
          "listTime": "2011-04-29",\
          "delisting": false,\
          "listTimestamp": 1304006400\
        }\
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

接口限制

*   每 30 秒内最多请求 10 次获取板块内股票列表接口

常用的板块、指数代码

| 代码  | 说明  |
| --- | --- |
| HK.HSI Constituent Stocks | 恒指成份股 |
| HK.HSCEI Stock | 国指成份股 |
| HK.Motherboard | 港股主板 |
| HK.GEM | 港股创业板 |
| HK.LIST1910 | 所有港股 |
| HK.LIST1911 | 主板 H 股 |
| HK.LIST1912 | 创业板 H 股 |
| HK.Fund | ETF（港股基金） |
| HK.LIST1600 | 热度榜（港） |
| HK.LIST1921 | 已上市新股-港股 |
| SH.LIST3000000 | 上海主板 |
| SH.LIST0901 | 上证 B 股 |
| SH.LIST0902 | 深证 B 股 |
| SH.LIST3000002 | 沪深指数 |
| SH.LIST3000005 | 全部 A 股（沪深） |
| SH.LIST0600 | 热度榜（沪深） |
| SH.LIST0992 | 科创板 |
| SH.LIST0921 | 已上市新股-A 股 |
| SZ.LIST3000001 | 深证主板 |
| SZ.LIST3000003 | 中小板 |
| SZ.LIST3000004 | 创业板（深） |
| US.USAALL | 全部美股 |

← [条件选股](https://openapi.futunn.com/futu-api-doc/quote/get-stock-filter.html) [获取板块列表](https://openapi.futunn.com/futu-api-doc/quote/get-plate-list.html)
 →

[获取板块内股票列表](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html)