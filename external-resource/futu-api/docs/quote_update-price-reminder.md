 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/update-price-reminder.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/update-price-reminder.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/update-price-reminder.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/update-price-reminder.html)
    

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
    

[#](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html#4884)
 到价提醒回调
==========================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`on_recv_rsp(self, rsp_pb)`

*   **介绍**
    
    到价提醒通知回调，异步处理已设置到价提醒的通知推送。  
    在收到实时到价提醒通知推送后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Qot\_UpdatePriceReminder\_pb2.Response | 派生类中不需要直接处理该参数 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | dict | 当 ret == RET\_OK，返回到价提醒 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   到价提醒
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | price | float | 当前价格 |
        | change\_rate | str | 当前涨跌幅 |
        | market\_status | [PriceReminderMarketStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#482) | 触发的时间段 |
        | content | str | 到价提醒文字内容 |
        | note | str | 备注<br>(ℹ️ 仅支持 20 个以内的中文字符) |
        | key | int | 到价提醒标识 |
        | reminder\_type | [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160) | 到价提醒的类型 |
        | set\_value | float | 用户设置的提醒值 |
        | cur\_value | float | 提醒触发时的值 |
        
*   **Example**
    

    import time
    from futu import *
    
    class PriceReminderTest(PriceReminderHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, content = super(PriceReminderTest,self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("PriceReminderTest: error, msg: %s" % content)
                return RET_ERROR, content
            print("PriceReminderTest ", content) # PriceReminderTest 自己的处理逻辑
            return RET_OK, content
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = PriceReminderTest()
    quote_ctx.set_handler(handler)  # 设置到价提醒通知回调
    time.sleep(15)  # 设置脚本接收 OpenD 的推送持续时间为15秒
    quote_ctx.close()   # 关闭当条连接，OpenD 会在1分钟后自动取消相应股票相应类型的订阅
    

1  
2  
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

*   **Output**

    PriceReminderTest  {'code': 'US.AAPL', 'name': '苹果', 'price': 185.750, 'change_rate': 0.11, 'market_status': 'US_PRE', 'content': '买一价高于185.500', 'note': '', 'key': 1744022257052794489, 'reminder_type': 'BID_PRICE_UP', 'set_value': 185.500, 'cur_value': 185.750}
    

1  

[#](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html#3227)
 Qot\_UpdatePriceReminder.proto
------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    到价提醒通知回调，异步处理已设置到价提醒的通知推送。
    
*   **参数**
    

    enum MarketStatus
    {
    	MarketStatus_Unknow = 0;
    	MarketStatus_Open = 1; // 盘中
    	MarketStatus_USPre = 2;  // 美股盘前
    	MarketStatus_USAfter = 3; // 美股盘后
        MarketStatus_USOverNight = 4; // 美股夜盘
    }
    
    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 11; // 股票名称
    	required double price = 2; //价格
    	required double changeRate = 3; //当日涨跌幅
    	required int32 marketStatus = 4; //Qot_Common::MarketStatus 市场状态
    	required string content = 5; //内容
    	required string note = 6; //备注仅支持 20 个以内的中文字符
    	optional int64 key = 7; //到价提醒的标识
    	optional int32 type = 8; //Qot_Common::PriceReminderType，提醒频率类型
    	optional double setValue = 9; //设置的提醒值
    	optional double curValue = 10; //设置的提醒类型触发时当前值
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
31  
32  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3019
    

`virtual void OnReply_UpdatePriceReminder(FTAPI_Conn client, uint nSerialNo, QotUpdatePriceReminder.Response rsp);`

*   **介绍**
    
    到价提醒通知回调，异步处理已设置到价提醒的通知推送。
    
*   **参数**
    

    enum MarketStatus
    {
    	MarketStatus_Unknow = 0;
    	MarketStatus_Open = 1; // 盘中
    	MarketStatus_USPre = 2;  // 美股盘前
    	MarketStatus_USAfter = 3; // 美股盘后
        MarketStatus_USOverNight = 4; // 美股夜盘
    }
    
    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 11; // 股票名称
    	required double price = 2; //价格
    	required double changeRate = 3; //当日涨跌幅
    	required int32 marketStatus = 4; //Qot_Common::MarketStatus 市场状态
    	required string content = 5; //内容
    	required string note = 6; //备注仅支持 20 个以内的中文字符
    	optional int64 key = 7; //到价提醒的标识
    	optional int32 type = 8; //Qot_Common::PriceReminderType，提醒频率类型
    	optional double setValue = 9; //设置的提醒值
    	optional double curValue = 10; //设置的提醒类型触发时当前值
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
31  
32  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
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
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_UpdatePriceReminder(FTAPI_Conn client, uint nSerialNo, QotUpdatePriceReminder.Response rsp)
        {
            Console.Write("Reply: QotUpdatePriceReminder: {0}\n", nSerialNo);
            Console.Write("code: {0}, content: {1}\n", rsp.S2C.Security.Code, rsp.S2C.Content);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826796032557005979
    Reply: QotUpdatePriceReminder: 4
    code: VXmain, content: 价格涨到20.650
    

1  
2  
3  

`void onPush_UpdatePriceReminder(FTAPI_Conn client, int nSerialNo, QotUpdatePriceReminder.Response rsp);`

*   **介绍**
    
    到价提醒通知回调，异步处理已设置到价提醒的通知推送。
    
*   **参数**
    

    enum MarketStatus
    {
    	MarketStatus_Unknow = 0;
    	MarketStatus_Open = 1; // 盘中
    	MarketStatus_USPre = 2;  // 美股盘前
    	MarketStatus_USAfter = 3; // 美股盘后
        MarketStatus_USOverNight = 4; // 美股夜盘
    }
    
    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 11; // 股票名称
    	required double price = 2; //价格
    	required double changeRate = 3; //当日涨跌幅
    	required int32 marketStatus = 4; //Qot_Common::MarketStatus 市场状态
    	required string content = 5; //内容
    	required string note = 6; //备注仅支持 20 个以内的中文字符
    	optional int64 key = 7; //到价提醒的标识
    	optional int32 type = 8; //Qot_Common::PriceReminderType，提醒频率类型
    	optional double setValue = 9; //设置的提醒值
    	optional double curValue = 10; //设置的提醒类型触发时当前值
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
31  
32  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
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
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onPush_UpdatePriceReminder(FTAPI_Conn client, QotUpdatePriceReminder.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotUpdatePriceReminder failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotUpdatePriceReminder: %s\n", json);
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

*   **Output**

    Receive QotUpdatePriceReminder: {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "price": 604,
      "changeRate": 0.499,
      "marketStatus": 1,
      "content": "价格涨到604.000",
      "note": "",
      "key": "162321935858611601",
      "type": 1,
      "setValue": 604,
      "curValue": 604
     }
    }
    Receive QotUpdatePriceReminder: {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "price": 603.5,
      "changeRate": 0.415,
      "marketStatus": 1,
      "content": "价格跌到603.990",
      "note": "",
      "key": "162320791658522901",
      "type": 2,
      "setValue": 603.99,
      "curValue": 603.5
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

`virtual void OnPush_UpdatePriceReminder(const Qot_UpdatePriceReminder::Response &stRsp) = 0;`

*   **介绍**
    
    到价提醒通知回调，异步处理已设置到价提醒的通知推送。
    
*   **参数**
    

    enum MarketStatus
    {
    	MarketStatus_Unknow = 0;
    	MarketStatus_Open = 1; // 盘中
    	MarketStatus_USPre = 2;  // 美股盘前
    	MarketStatus_USAfter = 3; // 美股盘后
        MarketStatus_USOverNight = 4; // 美股夜盘
    }
    
    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 11; // 股票名称
    	required double price = 2; //价格
    	required double changeRate = 3; //当日涨跌幅
    	required int32 marketStatus = 4; //Qot_Common::MarketStatus 市场状态
    	required string content = 5; //内容
    	required string note = 6; //备注仅支持 20 个以内的中文字符
    	optional int64 key = 7; //到价提醒的标识
    	optional int32 type = 8; //Qot_Common::PriceReminderType，提醒频率类型
    	optional double setValue = 9; //设置的提醒值
    	optional double curValue = 10; //设置的提醒类型触发时当前值
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
31  
32  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
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
    
    	}
    
    	virtual void OnPush_UpdatePriceReminder(const Qot_UpdatePriceReminder::Response &stRsp) {
    		cout << "OnPush_UpdatePriceReminder: " << endl;
    		// 解析内部结构打印出来
    		// ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
    		string resp_str;
    		ProtoBufToBodyData(stRsp, resp_str);
    		cout << UTF8ToLocal(resp_str) << endl;
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
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

*   **Output**

    connect
    OnPush_UpdatePriceReminder:
    {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "price": 604,
      "changeRate": 0.499,
      "marketStatus": 1,
      "content": "价格涨到604.000",
      "note": "",
      "key": "162321935858611601",
      "type": 1,
      "setValue": 604,
      "curValue": 604
     }
    }
    
    OnPush_UpdatePriceReminder:
    {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "price": 603.5,
      "changeRate": 0.415,
      "marketStatus": 1,
      "content": "价格跌到603.990",
      "note": "",
      "key": "162320791658522901",
      "type": 2,
      "setValue": 603.99,
      "curValue": 603.5
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

`OnPush(cmd,res)`

*   **介绍**
    
    到价提醒通知回调，异步处理已设置到价提醒的通知推送。
    
*   **参数**
    

    enum MarketStatus
    {
    	MarketStatus_Unknow = 0;
    	MarketStatus_Open = 1; // 盘中
    	MarketStatus_USPre = 2;  // 美股盘前
    	MarketStatus_USAfter = 3; // 美股盘后
        MarketStatus_USOverNight = 4; // 美股夜盘
    }
    
    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 11; // 股票名称
    	required double price = 2; //价格
    	required double changeRate = 3; //当日涨跌幅
    	required int32 marketStatus = 4; //Qot_Common::MarketStatus 市场状态
    	required string content = 5; //内容
    	required string note = 6; //备注仅支持 20 个以内的中文字符
    	optional int64 key = 7; //到价提醒的标识
    	optional int32 type = 8; //Qot_Common::PriceReminderType，提醒频率类型
    	optional double setValue = 9; //设置的提醒值
    	optional double curValue = 10; //设置的提醒类型触发时当前值
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
31  
32  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotUpdatePriceReminder(){
        const { RetType } = Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ec16fde057a2e7a0'];
        let websocket = new ftWebsocket();
        
        // 需要已有设定的到价提醒
    
        websocket.onPush = (cmd, res)=>{
            if(ftCmdID.QotUpdatePriceReminder.cmd == cmd){ // 到价提醒推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    let data = beautify(JSON.stringify(s2c), {
                        indent_size: 2,
                        space_in_empty_paren: true,
                    });
                    console.log("PriceReminderTest:");
                    console.log(data);
                } else {
                    console.log("PriceReminderTest: error")
                }
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
    
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 3600 * 1000); // 接收 OpenD 的推送持续时间为3600秒, 3600秒后断开
    }
    

1  
2  
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

*   **Output**

    PriceReminderTest:
    {
      "security": {
        "market": 1,
        "code": "00700"
      },
      "price": 482.8,
      "changeRate": 1.004,
      "marketStatus": 1,
      "content": "Daily rises more than 1.000%",
      "note": "",
      "key": "163126377342664201",
      "type": 3,
      "setValue": 1,
      "curValue": 1.004
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

提示

*   此接口提供了持续获取推送数据的功能，如需一次性获取实时数据，请参考 [获取到价提醒](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)
    

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`on_recv_rsp(self, rsp_pb)`

*   **介绍**
    
    到价提醒通知回调，异步处理已设置到价提醒的通知推送。  
    在收到实时到价提醒通知推送后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Qot\_UpdatePriceReminder\_pb2.Response | 派生类中不需要直接处理该参数 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | dict | 当 ret == RET\_OK，返回到价提醒 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   到价提醒
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | price | float | 当前价格 |
        | change\_rate | str | 当前涨跌幅 |
        | market\_status | [PriceReminderMarketStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#482) | 触发的时间段 |
        | content | str | 到价提醒文字内容 |
        | note | str | 备注<br>(ℹ️ 仅支持 20 个以内的中文字符) |
        | key | int | 到价提醒标识 |
        | reminder\_type | [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160) | 到价提醒的类型 |
        | set\_value | float | 用户设置的提醒值 |
        | cur\_value | float | 提醒触发时的值 |
        
*   **Example**
    

    import time
    from moomoo import *
    
    class PriceReminderTest(PriceReminderHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, content = super(PriceReminderTest,self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("PriceReminderTest: error, msg: %s" % content)
                return RET_ERROR, content
            print("PriceReminderTest ", content) # PriceReminderTest 自己的处理逻辑
            return RET_OK, content
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = PriceReminderTest()
    quote_ctx.set_handler(handler)  # 设置到价提醒通知回调
    time.sleep(15)  # 设置脚本接收 OpenD 的推送持续时间为15秒
    quote_ctx.close()   # 关闭当条连接，OpenD 会在1分钟后自动取消相应股票相应类型的订阅
    

1  
2  
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

*   **Output**

    PriceReminderTest  {'code': 'US.AAPL', 'name': '苹果', 'price': 185.750, 'change_rate': 0.11, 'market_status': 'US_PRE', 'content': '买一价高于185.500', 'note': '', 'key': 1744022257052794489, 'reminder_type': 'BID_PRICE_UP', 'set_value': 185.500, 'cur_value': 185.750}
    

1  

[#](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html#3227-2)
 Qot\_UpdatePriceReminder.proto
--------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    到价提醒通知回调，异步处理已设置到价提醒的通知推送。
    
*   **参数**
    

    enum MarketStatus
    {
    	MarketStatus_Unknow = 0;
    	MarketStatus_Open = 1; // 盘中
    	MarketStatus_USPre = 2;  // 美股盘前
    	MarketStatus_USAfter = 3; // 美股盘后
        MarketStatus_USOverNight = 4; // 美股夜盘
    }
    
    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 11; // 股票名称
    	required double price = 2; //价格
    	required double changeRate = 3; //当日涨跌幅
    	required int32 marketStatus = 4; //Qot_Common::MarketStatus 市场状态
    	required string content = 5; //内容
    	required string note = 6; //备注仅支持 20 个以内的中文字符
    	optional int64 key = 7; //到价提醒的标识
    	optional int32 type = 8; //Qot_Common::PriceReminderType，提醒频率类型
    	optional double setValue = 9; //设置的提醒值
    	optional double curValue = 10; //设置的提醒类型触发时当前值
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
31  
32  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3019
    

`virtual void OnReply_UpdatePriceReminder(MMAPI_Conn client, uint nSerialNo, QotUpdatePriceReminder.Response rsp);`

*   **介绍**
    
    到价提醒通知回调，异步处理已设置到价提醒的通知推送。
    
*   **参数**
    

    enum MarketStatus
    {
    	MarketStatus_Unknow = 0;
    	MarketStatus_Open = 1; // 盘中
    	MarketStatus_USPre = 2;  // 美股盘前
    	MarketStatus_USAfter = 3; // 美股盘后
        MarketStatus_USOverNight = 4; // 美股夜盘
    }
    
    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 11; // 股票名称
    	required double price = 2; //价格
    	required double changeRate = 3; //当日涨跌幅
    	required int32 marketStatus = 4; //Qot_Common::MarketStatus 市场状态
    	required string content = 5; //内容
    	required string note = 6; //备注仅支持 20 个以内的中文字符
    	optional int64 key = 7; //到价提醒的标识
    	optional int32 type = 8; //Qot_Common::PriceReminderType，提醒频率类型
    	optional double setValue = 9; //设置的提醒值
    	optional double curValue = 10; //设置的提醒类型触发时当前值
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
31  
32  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
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
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_UpdatePriceReminder(MMAPI_Conn client, uint nSerialNo, QotUpdatePriceReminder.Response rsp)
        {
            Console.Write("Reply: QotUpdatePriceReminder: {0}\n", nSerialNo);
            Console.Write("code: {0}, content: {1}\n", rsp.S2C.Security.Code, rsp.S2C.Content);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826796032557005979
    Reply: QotUpdatePriceReminder: 4
    code: VXmain, content: 价格涨到20.650
    

1  
2  
3  

`void onPush_UpdatePriceReminder(MMAPI_Conn client, int nSerialNo, QotUpdatePriceReminder.Response rsp);`

*   **介绍**
    
    到价提醒通知回调，异步处理已设置到价提醒的通知推送。
    
*   **参数**
    

    enum MarketStatus
    {
    	MarketStatus_Unknow = 0;
    	MarketStatus_Open = 1; // 盘中
    	MarketStatus_USPre = 2;  // 美股盘前
    	MarketStatus_USAfter = 3; // 美股盘后
        MarketStatus_USOverNight = 4; // 美股夜盘
    }
    
    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 11; // 股票名称
    	required double price = 2; //价格
    	required double changeRate = 3; //当日涨跌幅
    	required int32 marketStatus = 4; //Qot_Common::MarketStatus 市场状态
    	required string content = 5; //内容
    	required string note = 6; //备注仅支持 20 个以内的中文字符
    	optional int64 key = 7; //到价提醒的标识
    	optional int32 type = 8; //Qot_Common::PriceReminderType，提醒频率类型
    	optional double setValue = 9; //设置的提醒值
    	optional double curValue = 10; //设置的提醒类型触发时当前值
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
31  
32  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
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
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onPush_UpdatePriceReminder(MMAPI_Conn client, QotUpdatePriceReminder.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotUpdatePriceReminder failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotUpdatePriceReminder: %s\n", json);
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

*   **Output**

    Receive QotUpdatePriceReminder: {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "price": 604,
      "changeRate": 0.499,
      "marketStatus": 1,
      "content": "价格涨到604.000",
      "note": "",
      "key": "162321935858611601",
      "type": 1,
      "setValue": 604,
      "curValue": 604
     }
    }
    Receive QotUpdatePriceReminder: {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "price": 603.5,
      "changeRate": 0.415,
      "marketStatus": 1,
      "content": "价格跌到603.990",
      "note": "",
      "key": "162320791658522901",
      "type": 2,
      "setValue": 603.99,
      "curValue": 603.5
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

`virtual void OnPush_UpdatePriceReminder(const Qot_UpdatePriceReminder::Response &stRsp) = 0;`

*   **介绍**
    
    到价提醒通知回调，异步处理已设置到价提醒的通知推送。
    
*   **参数**
    

    enum MarketStatus
    {
    	MarketStatus_Unknow = 0;
    	MarketStatus_Open = 1; // 盘中
    	MarketStatus_USPre = 2;  // 美股盘前
    	MarketStatus_USAfter = 3; // 美股盘后
        MarketStatus_USOverNight = 4; // 美股夜盘
    }
    
    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 11; // 股票名称
    	required double price = 2; //价格
    	required double changeRate = 3; //当日涨跌幅
    	required int32 marketStatus = 4; //Qot_Common::MarketStatus 市场状态
    	required string content = 5; //内容
    	required string note = 6; //备注仅支持 20 个以内的中文字符
    	optional int64 key = 7; //到价提醒的标识
    	optional int32 type = 8; //Qot_Common::PriceReminderType，提醒频率类型
    	optional double setValue = 9; //设置的提醒值
    	optional double curValue = 10; //设置的提醒类型触发时当前值
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
31  
32  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
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
    
    	}
    
    	virtual void OnPush_UpdatePriceReminder(const Qot_UpdatePriceReminder::Response &stRsp) {
    		cout << "OnPush_UpdatePriceReminder: " << endl;
    		// 解析内部结构打印出来
    		// ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
    		string resp_str;
    		ProtoBufToBodyData(stRsp, resp_str);
    		cout << UTF8ToLocal(resp_str) << endl;
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
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

*   **Output**

    connect
    OnPush_UpdatePriceReminder:
    {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "price": 604,
      "changeRate": 0.499,
      "marketStatus": 1,
      "content": "价格涨到604.000",
      "note": "",
      "key": "162321935858611601",
      "type": 1,
      "setValue": 604,
      "curValue": 604
     }
    }
    
    OnPush_UpdatePriceReminder:
    {
     "retType": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "price": 603.5,
      "changeRate": 0.415,
      "marketStatus": 1,
      "content": "价格跌到603.990",
      "note": "",
      "key": "162320791658522901",
      "type": 2,
      "setValue": 603.99,
      "curValue": 603.5
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

`OnPush(cmd,res)`

*   **介绍**
    
    到价提醒通知回调，异步处理已设置到价提醒的通知推送。
    
*   **参数**
    

    enum MarketStatus
    {
    	MarketStatus_Unknow = 0;
    	MarketStatus_Open = 1; // 盘中
    	MarketStatus_USPre = 2;  // 美股盘前
    	MarketStatus_USAfter = 3; // 美股盘后
        MarketStatus_USOverNight = 4; // 美股夜盘
    }
    
    message S2C
    {
    	required Qot_Common.Security security = 1; //股票
        optional string name = 11; // 股票名称
    	required double price = 2; //价格
    	required double changeRate = 3; //当日涨跌幅
    	required int32 marketStatus = 4; //Qot_Common::MarketStatus 市场状态
    	required string content = 5; //内容
    	required string note = 6; //备注仅支持 20 个以内的中文字符
    	optional int64 key = 7; //到价提醒的标识
    	optional int32 type = 8; //Qot_Common::PriceReminderType，提醒频率类型
    	optional double setValue = 9; //设置的提醒值
    	optional double curValue = 10; //设置的提醒类型触发时当前值
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
31  
32  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotUpdatePriceReminder(){
        const { RetType } = Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ec16fde057a2e7a0'];
        let websocket = new mmWebsocket();
        
        // 需要已有设定的到价提醒
    
        websocket.onPush = (cmd, res)=>{
            if(ftCmdID.QotUpdatePriceReminder.cmd == cmd){ // 到价提醒推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    let data = beautify(JSON.stringify(s2c), {
                        indent_size: 2,
                        space_in_empty_paren: true,
                    });
                    console.log("PriceReminderTest:");
                    console.log(data);
                } else {
                    console.log("PriceReminderTest: error")
                }
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
    
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 3600 * 1000); // 接收 FOpenD 的推送持续时间为3600秒, 3600秒后断开
    }
    

1  
2  
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

*   **Output**

    PriceReminderTest:
    {
      "security": {
        "market": 1,
        "code": "00700"
      },
      "price": 482.8,
      "changeRate": 1.004,
      "marketStatus": 1,
      "content": "Daily rises more than 1.000%",
      "note": "",
      "key": "163126377342664201",
      "type": 3,
      "setValue": 1,
      "curValue": 1.004
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

提示

*   此接口提供了持续获取推送数据的功能，如需一次性获取实时数据，请参考 [获取到价提醒](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)
    

← [修改自选股列表](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html) [行情定义](https://openapi.futunn.com/futu-api-doc/quote/quote.html)
 →

[到价提醒回调](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html)