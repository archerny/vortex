 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-price-reminder.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-price-reminder.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-price-reminder.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-price-reminder.html)
    

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
    

[#](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html#2233)
 获取到价提醒列表
=========================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_price_reminder(code=None, market=None)`

*   **介绍**
    
    获取对指定股票 / 指定市场设置的到价提醒列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票代码 |
    | market | [Market](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427) | 市场类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>输入沪股市场和深股市场，都会认为是 A 股市场 |
    
    注：code 和 market 都存在的情况下，code 优先。
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回到价提醒数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   到价提醒数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | key | int | 标识，用于修改到价提醒 |
        | reminder\_type | [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160) | 到价提醒的类型 |
        | reminder\_freq | [PriceReminderFreq](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1059) | 到价提醒的频率 |
        | value | float | 提醒值 |
        | enable | bool | 是否启用 |
        | note | str | 备注<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅支持 20 个以内的中文字符 |
        | reminder\_session\_list | list | 美股到价提醒时段列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list中元素类型是[PriceReminderMarketStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#482) |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_price_reminder(code='US.AAPL')
    if ret == RET_OK:
        print(data)
        print(data['key'].values.tolist())   # 转为 list
    else:
        print('error:', data)
    print('******************************************')
    ret, data = quote_ctx.get_price_reminder(code=None, market=Market.US)
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果到价提醒列表不为空
            print(data['code'][0])    # 取第一条的股票代码
            print(data['code'].values.tolist())   # 转为 list
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
13  
14  
15  
16  
17  
18  
19  

*   **Output**

    code name                  key   reminder_type reminder_freq   value  enable note                   reminder_session_list
    0  US.AAPL   苹果  1744021708234288125    BID_PRICE_UP        ALWAYS  184.37    True  456                              [US_AFTER]
    1  US.AAPL   苹果  1744022257052794489    BID_PRICE_UP        ALWAYS  185.50    True  456  [OPEN, US_PRE, US_AFTER, US_OVERNIGHT]
    2  US.AAPL   苹果  1744021708211891867  ASK_PRICE_DOWN        ALWAYS  182.54    True  123                              [US_AFTER]
    3  US.AAPL   苹果  1744022257023211123  ASK_PRICE_DOWN        ALWAYS  183.70    True  123  [OPEN, US_PRE, US_AFTER, US_OVERNIGHT]
    [1744021708234288125, 1744022257052794489, 1744021708211891867, 1744022257023211123]
    ******************************************
          code name                  key   reminder_type reminder_freq   value  enable note                   reminder_session_list
    0  US.AAPL   苹果  1744021708234288125    BID_PRICE_UP        ALWAYS  184.37    True  456                              [US_AFTER]
    1  US.AAPL   苹果  1744022257052794489    BID_PRICE_UP        ALWAYS  185.50    True  456  [OPEN, US_PRE, US_AFTER, US_OVERNIGHT]
    2  US.AAPL   苹果  1744021708211891867  ASK_PRICE_DOWN        ALWAYS  182.54    True  123                              [US_AFTER]
    3  US.AAPL   苹果  1744022257023211123  ASK_PRICE_DOWN        ALWAYS  183.70    True  123  [OPEN, US_PRE, US_AFTER, US_OVERNIGHT]
    4  US.NVDA  英伟达  1739697581665326308      PRICE_DOWN        ALWAYS  102.00    True       [OPEN, US_PRE, US_AFTER, US_OVERNIGHT]
    US.AAPL
    ['US.AAPL', 'US.AAPL', 'US.AAPL', 'US.AAPL', 'US.NVDA']
    

1  
2  
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

[#](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html#9077)
 Qot\_GetPriceReminder.proto
------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取对指定股票 / 指定市场设置的到价提醒列表
    
*   **参数**
    

    message C2S
    {
        //security 和 market 二选一，都存在的情况下 security 优先。
    	optional Qot_Common.Security security = 1; // 查询指定股票下的到价提醒
    	optional int32 market = 2; //Qot_Common::QotMarket 市场，查询市场下的到价提醒项，不区分沪深
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   市场类型参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // 提醒信息列表
    message PriceReminderItem
    {
        required int64 key = 1; // 每个提醒的唯一标识
    	required int32 type = 2; // Qot_Common::PriceReminderType 提醒类型
    	required double value = 3; // 提醒参数值
    	required string note = 4; // 备注仅支持 20 个以内的中文字符
    	required int32 freq = 5; // Qot_Common::PriceReminderFreq 提醒频率类型
    	required bool isEnable = 6; // 该提醒设置是否生效。false 不生效，true 生效
    }
    
    message PriceReminder
    {
    	required Qot_Common.Security security = 1; // 股票
    	optional string name = 3; // 股票名称
    	repeated PriceReminderItem itemList = 2; // 提醒信息列表
    }
    
    message S2C
    {
    	repeated PriceReminder priceReminderList = 1; //到价提醒
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
25  
26  
27  
28  
29  
30  
31  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1059)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3221
    

`uint GetPriceReminder(QotGetPriceReminder.Request req);`  
`virtual void OnReply_GetPriceReminder(FTAPI_Conn client, uint nSerialNo, QotGetPriceReminder.Response rsp);`

*   **介绍**
    
    获取对指定股票 / 指定市场设置的到价提醒列表
    
*   **参数**
    

    message C2S
    {
        //security 和 market 二选一，都存在的情况下 security 优先。
    	optional Qot_Common.Security security = 1; // 查询指定股票下的到价提醒
    	optional int32 market = 2; //Qot_Common::QotMarket 市场，查询市场下的到价提醒项，不区分沪深
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   市场类型参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // 提醒信息列表
    message PriceReminderItem
    {
        required int64 key = 1; // 每个提醒的唯一标识
    	required int32 type = 2; // Qot_Common::PriceReminderType 提醒类型
    	required double value = 3; // 提醒参数值
    	required string note = 4; // 备注仅支持 20 个以内的中文字符
    	required int32 freq = 5; // Qot_Common::PriceReminderFreq 提醒频率类型
    	required bool isEnable = 6; // 该提醒设置是否生效。false 不生效，true 生效
    }
    
    message PriceReminder
    {
    	required Qot_Common.Security security = 1; // 股票
    	optional string name = 3; // 股票名称
    	repeated PriceReminderItem itemList = 2; // 提醒信息列表
    }
    
    message S2C
    {
    	repeated PriceReminder priceReminderList = 1; //到价提醒
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
25  
26  
27  
28  
29  
30  
31  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1059)
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
            QotGetPriceReminder.C2S c2s = QotGetPriceReminder.C2S.CreateBuilder()
                    .SetSecurity(sec)
                .Build();
            QotGetPriceReminder.Request req = QotGetPriceReminder.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetPriceReminder(req);
            Console.Write("Send QotGetPriceReminder: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetPriceReminder(FTAPI_Conn client, uint nSerialNo, QotGetPriceReminder.Response rsp)
        {
            Console.Write("Reply: QotGetPriceReminder: {0}  {1}\n", nSerialNo, rsp.ToString());
            if(rsp.S2C.PriceReminderListCount > 0)
            {
                Console.Write("key: {0} \n", rsp.S2C.PriceReminderListList[0].ItemListList[0].Key);
            }            
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

    Reply: QotGetPriceReminder: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      priceReminderList {
        security {
          market: 1
          code: "00700"
        }
        itemList {
          key: 162763183755472101
          type: 1
          value: 5
          note: ""
          freq: 3
          isEnable: true
        }
        itemList {
          key: 162763177551063501
          type: 1
          value: 5
          note: ""
          freq: 3
          isEnable: true
        }
      }
    }
    
    key: 162763183755472101
    

1  
2  
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

`int getPriceReminder(QotGetPriceReminder.Request req);`  
`void onReply_GetPriceReminder(FTAPI_Conn client, int nSerialNo, QotGetPriceReminder.Response rsp);`

*   **介绍**
    
    获取对指定股票 / 指定市场设置的到价提醒列表
    
*   **参数**
    

    message C2S
    {
        //security 和 market 二选一，都存在的情况下 security 优先。
    	optional Qot_Common.Security security = 1; // 查询指定股票下的到价提醒
    	optional int32 market = 2; //Qot_Common::QotMarket 市场，查询市场下的到价提醒项，不区分沪深
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   市场类型参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // 提醒信息列表
    message PriceReminderItem
    {
        required int64 key = 1; // 每个提醒的唯一标识
    	required int32 type = 2; // Qot_Common::PriceReminderType 提醒类型
    	required double value = 3; // 提醒参数值
    	required string note = 4; // 备注仅支持 20 个以内的中文字符
    	required int32 freq = 5; // Qot_Common::PriceReminderFreq 提醒频率类型
    	required bool isEnable = 6; // 该提醒设置是否生效。false 不生效，true 生效
    }
    
    message PriceReminder
    {
    	required Qot_Common.Security security = 1; // 股票
    	optional string name = 3; // 股票名称
    	repeated PriceReminderItem itemList = 2; // 提醒信息列表
    }
    
    message S2C
    {
    	repeated PriceReminder priceReminderList = 1; //到价提醒
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
25  
26  
27  
28  
29  
30  
31  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1059)
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
            QotGetPriceReminder.C2S c2s = QotGetPriceReminder.C2S.newBuilder()
                    .setSecurity(sec)
                .build();
            QotGetPriceReminder.Request req = QotGetPriceReminder.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getPriceReminder(req);
            System.out.printf("Send QotGetPriceReminder: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetPriceReminder(FTAPI_Conn client, int nSerialNo, QotGetPriceReminder.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetPriceReminder failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetPriceReminder: %s\n", json);
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

    Send QotGetPriceReminder: 2
    Receive QotGetPriceReminder: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "priceReminderList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "itemList": [{\
            "key": "162452649832682701",\
            "type": 1,\
            "value": 5.0,\
            "note": "",\
            "freq": 3,\
            "isEnable": true\
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

`Futu::u32_t GetPriceReminder(const Qot_GetPriceReminder::Request &stReq);`  
`virtual void OnReply_GetPriceReminder(Futu::u32_t nSerialNo, const Qot_GetPriceReminder::Response &stRsp) = 0;`

*   **介绍**
    
    获取对指定股票 / 指定市场设置的到价提醒列表
    
*   **参数**
    

    message C2S
    {
        //security 和 market 二选一，都存在的情况下 security 优先。
    	optional Qot_Common.Security security = 1; // 查询指定股票下的到价提醒
    	optional int32 market = 2; //Qot_Common::QotMarket 市场，查询市场下的到价提醒项，不区分沪深
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   市场类型参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // 提醒信息列表
    message PriceReminderItem
    {
        required int64 key = 1; // 每个提醒的唯一标识
    	required int32 type = 2; // Qot_Common::PriceReminderType 提醒类型
    	required double value = 3; // 提醒参数值
    	required string note = 4; // 备注仅支持 20 个以内的中文字符
    	required int32 freq = 5; // Qot_Common::PriceReminderFreq 提醒频率类型
    	required bool isEnable = 6; // 该提醒设置是否生效。false 不生效，true 生效
    }
    
    message PriceReminder
    {
    	required Qot_Common.Security security = 1; // 股票
    	optional string name = 3; // 股票名称
    	repeated PriceReminderItem itemList = 2; // 提醒信息列表
    }
    
    message S2C
    {
    	repeated PriceReminder priceReminderList = 1; //到价提醒
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
25  
26  
27  
28  
29  
30  
31  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1059)
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
    		Qot_GetPriceReminder::Request req;
    		Qot_GetPriceReminder::C2S *c2s = req.mutable_c2s();
    		c2s->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetPriceReminderSerialNo = m_pQotApi->GetPriceReminder(req);
            cout << "Request GetPriceReminder SerialNo: " << m_GetPriceReminderSerialNo << endl;
    	}
    
    	virtual void OnReply_GetPriceReminder(Futu::u32_t nSerialNo, const Qot_GetPriceReminder::Response &stRsp){
            if(nSerialNo == m_GetPriceReminderSerialNo)
            {
                cout << "OnReply_GetPriceReminder SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetPriceReminderSerialNo;
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
    Request GetPriceReminder SerialNo: 4
    OnReply_GetPriceReminder SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "priceReminderList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "00700"\
        },\
        "itemList": [\
         {\
          "key": "162320791658522901",\
          "type": 1,\
          "value": 5,\
          "note": "",\
          "freq": 3,\
          "isEnable": true\
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

`GetPriceReminder(req);`

*   **介绍**
    
    获取对指定股票 / 指定市场设置的到价提醒列表
    
*   **参数**
    

    message C2S
    {
        //security 和 market 二选一，都存在的情况下 security 优先。
    	optional Qot_Common.Security security = 1; // 查询指定股票下的到价提醒
    	optional int32 market = 2; //Qot_Common::QotMarket 市场，查询市场下的到价提醒项，不区分沪深
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   市场类型参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // 提醒信息列表
    message PriceReminderItem
    {
        required int64 key = 1; // 每个提醒的唯一标识
    	required int32 type = 2; // Qot_Common::PriceReminderType 提醒类型
    	required double value = 3; // 提醒参数值
    	required string note = 4; // 备注仅支持 20 个以内的中文字符
    	required int32 freq = 5; // Qot_Common::PriceReminderFreq 提醒频率类型
    	required bool isEnable = 6; // 该提醒设置是否生效。false 不生效，true 生效
    }
    
    message PriceReminder
    {
    	required Qot_Common.Security security = 1; // 股票
    	optional string name = 3; // 股票名称
    	repeated PriceReminderItem itemList = 2; // 提醒信息列表
    }
    
    message S2C
    {
    	repeated PriceReminder priceReminderList = 1; //到价提醒
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
25  
26  
27  
28  
29  
30  
31  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1059)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetPriceReminder(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
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
                
                websocket.GetPriceReminder(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("PriceReminder: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    PriceReminder: errCode 0, retMsg , retType 0
    {
      "priceReminderList": [{\
        "security": {\
          "market": 1,\
          "code": "00700"\
        },\
        "itemList": [{\
          "key": "163126377342664201",\
          "type": 1,\
          "value": 600,\
          "note": "",\
          "freq": 1,\
          "isEnable": true\
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

接口限制

*   每 30 秒内最多请求 10 次获取到价提醒列表接口

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_price_reminder(code=None, market=None)`

*   **介绍**
    
    获取对指定股票 / 指定市场设置的到价提醒列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票代码 |
    | market | [Market](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427) | 市场类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>输入沪股市场和深股市场，都会认为是 A 股市场 |
    
    注：code 和 market 都存在的情况下，code 优先。
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回到价提醒数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   到价提醒数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | key | int | 标识，用于修改到价提醒 |
        | reminder\_type | [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160) | 到价提醒的类型 |
        | reminder\_freq | [PriceReminderFreq](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1059) | 到价提醒的频率 |
        | value | float | 提醒值 |
        | enable | bool | 是否启用 |
        | note | str | 备注<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅支持 20 个以内的中文字符 |
        | reminder\_session\_list | list | 美股到价提醒时段列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list中元素类型是[PriceReminderMarketStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#482) |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_price_reminder(code='US.AAPL')
    if ret == RET_OK:
        print(data)
        print(data['key'].values.tolist())   # 转为 list
    else:
        print('error:', data)
    print('******************************************')
    ret, data = quote_ctx.get_price_reminder(code=None, market=Market.US)
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果到价提醒列表不为空
            print(data['code'][0])    # 取第一条的股票代码
            print(data['code'].values.tolist())   # 转为 list
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
13  
14  
15  
16  
17  
18  
19  

*   **Output**

    code name                  key   reminder_type reminder_freq   value  enable note                   reminder_session_list
    0  US.AAPL   苹果  1744021708234288125    BID_PRICE_UP        ALWAYS  184.37    True  456                              [US_AFTER]
    1  US.AAPL   苹果  1744022257052794489    BID_PRICE_UP        ALWAYS  185.50    True  456  [OPEN, US_PRE, US_AFTER, US_OVERNIGHT]
    2  US.AAPL   苹果  1744021708211891867  ASK_PRICE_DOWN        ALWAYS  182.54    True  123                              [US_AFTER]
    3  US.AAPL   苹果  1744022257023211123  ASK_PRICE_DOWN        ALWAYS  183.70    True  123  [OPEN, US_PRE, US_AFTER, US_OVERNIGHT]
    [1744021708234288125, 1744022257052794489, 1744021708211891867, 1744022257023211123]
    ******************************************
          code name                  key   reminder_type reminder_freq   value  enable note                   reminder_session_list
    0  US.AAPL   苹果  1744021708234288125    BID_PRICE_UP        ALWAYS  184.37    True  456                              [US_AFTER]
    1  US.AAPL   苹果  1744022257052794489    BID_PRICE_UP        ALWAYS  185.50    True  456  [OPEN, US_PRE, US_AFTER, US_OVERNIGHT]
    2  US.AAPL   苹果  1744021708211891867  ASK_PRICE_DOWN        ALWAYS  182.54    True  123                              [US_AFTER]
    3  US.AAPL   苹果  1744022257023211123  ASK_PRICE_DOWN        ALWAYS  183.70    True  123  [OPEN, US_PRE, US_AFTER, US_OVERNIGHT]
    4  US.NVDA  英伟达  1739697581665326308      PRICE_DOWN        ALWAYS  102.00    True       [OPEN, US_PRE, US_AFTER, US_OVERNIGHT]
    US.AAPL
    ['US.AAPL', 'US.AAPL', 'US.AAPL', 'US.AAPL', 'US.NVDA']
    

1  
2  
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

[#](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html#9077-2)
 Qot\_GetPriceReminder.proto
--------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取对指定股票 / 指定市场设置的到价提醒列表
    
*   **参数**
    

    message C2S
    {
        //security 和 market 二选一，都存在的情况下 security 优先。
    	optional Qot_Common.Security security = 1; // 查询指定股票下的到价提醒
    	optional int32 market = 2; //Qot_Common::QotMarket 市场，查询市场下的到价提醒项，不区分沪深
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   市场类型参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // 提醒信息列表
    message PriceReminderItem
    {
        required int64 key = 1; // 每个提醒的唯一标识
    	required int32 type = 2; // Qot_Common::PriceReminderType 提醒类型
    	required double value = 3; // 提醒参数值
    	required string note = 4; // 备注仅支持 20 个以内的中文字符
    	required int32 freq = 5; // Qot_Common::PriceReminderFreq 提醒频率类型
    	required bool isEnable = 6; // 该提醒设置是否生效。false 不生效，true 生效
    }
    
    message PriceReminder
    {
    	required Qot_Common.Security security = 1; // 股票
    	optional string name = 3; // 股票名称
    	repeated PriceReminderItem itemList = 2; // 提醒信息列表
    }
    
    message S2C
    {
    	repeated PriceReminder priceReminderList = 1; //到价提醒
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
25  
26  
27  
28  
29  
30  
31  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1059)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3221
    

`uint GetPriceReminder(QotGetPriceReminder.Request req);`  
`virtual void OnReply_GetPriceReminder(MMAPI_Conn client, uint nSerialNo, QotGetPriceReminder.Response rsp);`

*   **介绍**
    
    获取对指定股票 / 指定市场设置的到价提醒列表
    
*   **参数**
    

    message C2S
    {
        //security 和 market 二选一，都存在的情况下 security 优先。
    	optional Qot_Common.Security security = 1; // 查询指定股票下的到价提醒
    	optional int32 market = 2; //Qot_Common::QotMarket 市场，查询市场下的到价提醒项，不区分沪深
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   市场类型参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // 提醒信息列表
    message PriceReminderItem
    {
        required int64 key = 1; // 每个提醒的唯一标识
    	required int32 type = 2; // Qot_Common::PriceReminderType 提醒类型
    	required double value = 3; // 提醒参数值
    	required string note = 4; // 备注仅支持 20 个以内的中文字符
    	required int32 freq = 5; // Qot_Common::PriceReminderFreq 提醒频率类型
    	required bool isEnable = 6; // 该提醒设置是否生效。false 不生效，true 生效
    }
    
    message PriceReminder
    {
    	required Qot_Common.Security security = 1; // 股票
    	optional string name = 3; // 股票名称
    	repeated PriceReminderItem itemList = 2; // 提醒信息列表
    }
    
    message S2C
    {
    	repeated PriceReminder priceReminderList = 1; //到价提醒
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
25  
26  
27  
28  
29  
30  
31  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1059)
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
            QotGetPriceReminder.C2S c2s = QotGetPriceReminder.C2S.CreateBuilder()
                    .SetSecurity(sec)
                .Build();
            QotGetPriceReminder.Request req = QotGetPriceReminder.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetPriceReminder(req);
            Console.Write("Send QotGetPriceReminder: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetPriceReminder(MMAPI_Conn client, uint nSerialNo, QotGetPriceReminder.Response rsp)
        {
            Console.Write("Reply: QotGetPriceReminder: {0}  {1}\n", nSerialNo, rsp.ToString());
            if(rsp.S2C.PriceReminderListCount > 0)
            {
                Console.Write("key: {0} \n", rsp.S2C.PriceReminderListList[0].ItemListList[0].Key);
            }            
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

    Reply: QotGetPriceReminder: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      priceReminderList {
        security {
          market: 1
          code: "00700"
        }
        itemList {
          key: 162763183755472101
          type: 1
          value: 5
          note: ""
          freq: 3
          isEnable: true
        }
        itemList {
          key: 162763177551063501
          type: 1
          value: 5
          note: ""
          freq: 3
          isEnable: true
        }
      }
    }
    
    key: 162763183755472101
    

1  
2  
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

`int getPriceReminder(QotGetPriceReminder.Request req);`  
`void onReply_GetPriceReminder(MMAPI_Conn client, int nSerialNo, QotGetPriceReminder.Response rsp);`

*   **介绍**
    
    获取对指定股票 / 指定市场设置的到价提醒列表
    
*   **参数**
    

    message C2S
    {
        //security 和 market 二选一，都存在的情况下 security 优先。
    	optional Qot_Common.Security security = 1; // 查询指定股票下的到价提醒
    	optional int32 market = 2; //Qot_Common::QotMarket 市场，查询市场下的到价提醒项，不区分沪深
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   市场类型参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // 提醒信息列表
    message PriceReminderItem
    {
        required int64 key = 1; // 每个提醒的唯一标识
    	required int32 type = 2; // Qot_Common::PriceReminderType 提醒类型
    	required double value = 3; // 提醒参数值
    	required string note = 4; // 备注仅支持 20 个以内的中文字符
    	required int32 freq = 5; // Qot_Common::PriceReminderFreq 提醒频率类型
    	required bool isEnable = 6; // 该提醒设置是否生效。false 不生效，true 生效
    }
    
    message PriceReminder
    {
    	required Qot_Common.Security security = 1; // 股票
    	optional string name = 3; // 股票名称
    	repeated PriceReminderItem itemList = 2; // 提醒信息列表
    }
    
    message S2C
    {
    	repeated PriceReminder priceReminderList = 1; //到价提醒
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
25  
26  
27  
28  
29  
30  
31  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1059)
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
            QotGetPriceReminder.C2S c2s = QotGetPriceReminder.C2S.newBuilder()
                    .setSecurity(sec)
                .build();
            QotGetPriceReminder.Request req = QotGetPriceReminder.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getPriceReminder(req);
            System.out.printf("Send QotGetPriceReminder: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetPriceReminder(MMAPI_Conn client, int nSerialNo, QotGetPriceReminder.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetPriceReminder failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetPriceReminder: %s\n", json);
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

    Send QotGetPriceReminder: 2
    Receive QotGetPriceReminder: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "priceReminderList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "itemList": [{\
            "key": "162452649832682701",\
            "type": 1,\
            "value": 5.0,\
            "note": "",\
            "freq": 3,\
            "isEnable": true\
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

`moomoo::u32_t GetPriceReminder(const Qot_GetPriceReminder::Request &stReq);`  
`virtual void OnReply_GetPriceReminder(moomoo::u32_t nSerialNo, const Qot_GetPriceReminder::Response &stRsp) = 0;`

*   **介绍**
    
    获取对指定股票 / 指定市场设置的到价提醒列表
    
*   **参数**
    

    message C2S
    {
        //security 和 market 二选一，都存在的情况下 security 优先。
    	optional Qot_Common.Security security = 1; // 查询指定股票下的到价提醒
    	optional int32 market = 2; //Qot_Common::QotMarket 市场，查询市场下的到价提醒项，不区分沪深
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   市场类型参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // 提醒信息列表
    message PriceReminderItem
    {
        required int64 key = 1; // 每个提醒的唯一标识
    	required int32 type = 2; // Qot_Common::PriceReminderType 提醒类型
    	required double value = 3; // 提醒参数值
    	required string note = 4; // 备注仅支持 20 个以内的中文字符
    	required int32 freq = 5; // Qot_Common::PriceReminderFreq 提醒频率类型
    	required bool isEnable = 6; // 该提醒设置是否生效。false 不生效，true 生效
    }
    
    message PriceReminder
    {
    	required Qot_Common.Security security = 1; // 股票
    	optional string name = 3; // 股票名称
    	repeated PriceReminderItem itemList = 2; // 提醒信息列表
    }
    
    message S2C
    {
    	repeated PriceReminder priceReminderList = 1; //到价提醒
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
25  
26  
27  
28  
29  
30  
31  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1059)
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
    		Qot_GetPriceReminder::Request req;
    		Qot_GetPriceReminder::C2S *c2s = req.mutable_c2s();
    		c2s->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetPriceReminderSerialNo = m_pQotApi->GetPriceReminder(req);
            cout << "Request GetPriceReminder SerialNo: " << m_GetPriceReminderSerialNo << endl;
    	}
    
    	virtual void OnReply_GetPriceReminder(moomoo::u32_t nSerialNo, const Qot_GetPriceReminder::Response &stRsp){
            if(nSerialNo == m_GetPriceReminderSerialNo)
            {
                cout << "OnReply_GetPriceReminder SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetPriceReminderSerialNo;
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
    Request GetPriceReminder SerialNo: 4
    OnReply_GetPriceReminder SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "priceReminderList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "00700"\
        },\
        "itemList": [\
         {\
          "key": "162320791658522901",\
          "type": 1,\
          "value": 5,\
          "note": "",\
          "freq": 3,\
          "isEnable": true\
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

`GetPriceReminder(req);`

*   **介绍**
    
    获取对指定股票 / 指定市场设置的到价提醒列表
    
*   **参数**
    

    message C2S
    {
        //security 和 market 二选一，都存在的情况下 security 优先。
    	optional Qot_Common.Security security = 1; // 查询指定股票下的到价提醒
    	optional int32 market = 2; //Qot_Common::QotMarket 市场，查询市场下的到价提醒项，不区分沪深
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   市场类型参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // 提醒信息列表
    message PriceReminderItem
    {
        required int64 key = 1; // 每个提醒的唯一标识
    	required int32 type = 2; // Qot_Common::PriceReminderType 提醒类型
    	required double value = 3; // 提醒参数值
    	required string note = 4; // 备注仅支持 20 个以内的中文字符
    	required int32 freq = 5; // Qot_Common::PriceReminderFreq 提醒频率类型
    	required bool isEnable = 6; // 该提醒设置是否生效。false 不生效，true 生效
    }
    
    message PriceReminder
    {
    	required Qot_Common.Security security = 1; // 股票
    	optional string name = 3; // 股票名称
    	repeated PriceReminderItem itemList = 2; // 提醒信息列表
    }
    
    message S2C
    {
    	repeated PriceReminder priceReminderList = 1; //到价提醒
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
25  
26  
27  
28  
29  
30  
31  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   提醒类型枚举参见 [PriceReminderType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5160)
>     
> *   提醒频率枚举参见 [PriceReminderFreq](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1059)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetPriceReminder(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
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
                
                websocket.GetPriceReminder(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("PriceReminder: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    PriceReminder: errCode 0, retMsg , retType 0
    {
      "priceReminderList": [{\
        "security": {\
          "market": 1,\
          "code": "00700"\
        },\
        "itemList": [{\
          "key": "163126377342664201",\
          "type": 1,\
          "value": 600,\
          "note": "",\
          "freq": 1,\
          "isEnable": true\
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

接口限制

*   每 30 秒内最多请求 10 次获取到价提醒列表接口

← [设置到价提醒](https://openapi.futunn.com/futu-api-doc/quote/set-price-reminder.html) [获取自选股列表](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html)
 →

[获取到价提醒列表](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html)