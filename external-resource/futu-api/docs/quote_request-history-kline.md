# 获取历史 K 线 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quote/request-history-kline.html

[#](https://openapi.futunn.com/futu-api-doc/quote/request-history-kline.html#2884)
 获取历史 K 线
============================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`request_history_kline(code, start=None, end=None, ktype=KLType.K_DAY, autype=AuType.QFQ, fields=[KL_FIELD.ALL], max_count=1000, page_req_key=None, extended_time=False, session=Session.NONE)`

*   **介绍**
    
    获取历史 K 线
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票代码 |
    | start | str | 开始时间<br>(ℹ️ 格式：yyyy-MM-dd  <br>例如：“2017-06-20”) |
    | end | str | 结束时间<br>(ℹ️ 格式：yyyy-MM-dd  <br>例如：“2017-07-20”) |
    | ktype | [KLType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4119) | K 线类型 |
    | autype | [AuType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907) | 复权类型 |
    | fields | [KLFields](https://openapi.futunn.com/futu-api-doc/quote/quote.html#481) | 需返回的字段列表 |
    | max\_count | int | 本次请求最大返回的 K 线根数<br>(ℹ️ *   传 None 表示返回 start 和 end 之间所有的数据<br><br>*   注意：OpenD 接收到所有数据后才会下发给脚本，  <br>    如果您要获取的 K 线根数大于 1000 根，建议选择分页，防止出现超时) |
    | page\_req\_key | bytes | 分页请求<br>(ℹ️ 如果 start 和 end 之间的 K 线根数多于 max\_count：  <br>1\. 首页请求时应该传 None  <br>2\. 后续页请求时必须要传入上次调用返回的参数 page\_req\_key) |
    | extended\_time | bool | 是否允许美股盘前盘后数据<br>(ℹ️ False：不允许  <br>True：允许) |
    | session | [Session](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9152) | 获取美股分时段历史K线<br>(ℹ️ *   仅用于获取美股分时段历史K线<br><br>*   获取美股历史K线不支持入参OVERNIGHT<br><br>*   最低OpenD版本要求：9.2.4207) |
    
    *   start 和 end 的组合如下
        
        | Start 类型 | End 类型 | 说明  |
        | --- | --- | --- |
        | str | str | start 和 end 分别为指定的日期 |
        | None | str | start 为 end 往前 365 天 |
        | str | None | end 为 start 往后 365 天 |
        | None | None | end 为当前日期，start 往前 365 天 |
        

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回历史 K 线数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    | page\_req\_key | bytes | 下一页请求的 key |
    
    *   历史 K 线数据格式如下:
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | time\_key | str | K 线时间<br>(ℹ️ 格式：yyyy-MM-dd HH:mm:ss  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | open | float | 开盘价 |
        | close | float | 收盘价 |
        | high | float | 最高价 |
        | low | float | 最低价 |
        | pe\_ratio | float | 市盈率<br>(ℹ️ 该字段为比例字段，默认不展示 %) |
        | turnover\_rate | float | 换手率 |
        | volume | int | 成交量 |
        | turnover | float | 成交额 |
        | change\_rate | float | 涨跌幅 |
        | last\_close | float | 昨收价 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret, data, page_req_key = quote_ctx.request_history_kline('US.AAPL', start='2019-09-11', end='2019-09-18', max_count=5, session=Session.ALL)  # 每页5个，请求第一页
    if ret == RET_OK:
        print(data)
        print(data['code'][0])    # 取第一条的股票代码
        print(data['close'].values.tolist())   # 第一页收盘价转为 list
    else:
        print('error:', data)
    while page_req_key != None:  # 请求后面的所有结果
        print('*************************************')
        ret, data, page_req_key = quote_ctx.request_history_kline('US.AAPL', start='2019-09-11', end='2019-09-18', max_count=5, page_req_key=page_req_key, session=Session.ALL) # 请求翻页后的数据
        if ret == RET_OK:
            print(data)
        else:
            print('error:', data)
    print('All pages are finished!')
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

*   **Output**

    code  name             time_key       open      close       high        low  pe_ratio  turnover_rate    volume      turnover  change_rate  last_close
    0  US.AAPL   苹果  2019-09-11 00:00:00  52.631194  53.963447  53.992409  52.549135    18.773        0.01039  177158584  9.808562e+09     3.179511   52.300545
    ..       ...   ...                  ...        ...        ...        ...        ...       ...            ...       ...           ...          ...         ...
    4  US.AAPL   苹果  2019-09-17 00:00:00  53.087346  53.265945  53.294907  52.884612    18.530        0.00432   73545872  4.046314e+09     0.363802   53.072865
    
    [5 rows x 13 columns]
    US.AAPL
    [53.9634465, 53.84156475, 52.7953125, 53.072865, 53.265945]
    *************************************
           code  name             time_key       open      close       high        low  pe_ratio  turnover_rate   volume      turnover  change_rate  last_close
    0  US.AAPL   苹果  2019-09-18 00:00:00  53.352831  53.76554  53.784847  52.961844    18.704        0.00602  102572372  5.682068e+09     0.937925   53.265945
    All pages are finished!
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

[#](https://openapi.futunn.com/futu-api-doc/quote/request-history-kline.html#4609)
 Qot\_RequestHistoryKL.proto
---------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取历史 K 线
    
*   **参数**
    

    
    message C2S
    {
    	required int32 rehabType = 1; //Qot_Common.RehabType,复权类型
    	required int32 klType = 2; //Qot_Common.KLType,K 线时间周期
    	required Qot_Common.Security security = 3; //股票市场以及股票代码
    	required string beginTime = 4; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 5; //结束时间字符串（格式：yyyy-MM-dd）
    	optional int32 maxAckKLNum = 6; //最多返回多少根 K 线，如果未指定表示不限制
    	optional int64 needKLFieldsFlag = 7; //指定返回 K 线结构体特定某几项数据，KLFields 枚举值或组合，如果未指定返回全部字段
    	optional bytes nextReqKey = 8; //分页请求 key
    	optional bool extendedTime = 9;  //是否获取美股盘前盘后数据，当前仅支持1分 k。
        optional int32 session = 10; //是否获取美股盘前盘后以及夜盘数据，当前仅支持60分钟及以下级别，该参数指定后忽略extendedTime
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
13  
14  
15  
16  
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     
> *   K 线类型参见 [KLType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4119)
>     
> *   K 线字段类型参见 [KLFields](https://openapi.futunn.com/futu-api-doc/quote/quote.html#481)
>     

*   **返回**

    
    message S2C
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 4; // 股票名称
    	repeated Qot_Common.KLine klList = 2; //K 线数据
    	optional bytes nextReqKey = 3; //分页请求 key。一次请求没有返回所有数据时，下次请求带上这个 key，会接着请求
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   K 线结构参见 [KLine](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5151)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3103
    

`uint RequestHistoryKL(QotRequestHistoryKL.Request req);`  
`virtual void OnReply_RequestHistoryKL(FTAPI_Conn client, uint nSerialNo, QotRequestHistoryKL.Response rsp);`

*   **介绍**

获取历史 K 线

*   **参数**

    
    message C2S
    {
    	required int32 rehabType = 1; //Qot_Common.RehabType,复权类型
    	required int32 klType = 2; //Qot_Common.KLType,K 线时间周期
    	required Qot_Common.Security security = 3; //股票市场以及股票代码
    	required string beginTime = 4; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 5; //结束时间字符串（格式：yyyy-MM-dd）
    	optional int32 maxAckKLNum = 6; //最多返回多少根 K 线，如果未指定表示不限制
    	optional int64 needKLFieldsFlag = 7; //指定返回 K 线结构体特定某几项数据，KLFields 枚举值或组合，如果未指定返回全部字段
    	optional bytes nextReqKey = 8; //分页请求 key
    	optional bool extendedTime = 9;  //是否获取美股盘前盘后数据，当前仅支持60分钟及以下级别。
        optional int32 session = 10; //是否获取美股盘前盘后以及夜盘数据，当前仅支持60分钟及以下级别，该参数指定后忽略extendedTime
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
13  
14  
15  
16  
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     
> *   K 线类型参见 [KLType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4119)
>     
> *   K 线字段类型参见 [KLFields](https://openapi.futunn.com/futu-api-doc/quote/quote.html#481)
>     

*   **返回**

    
    message S2C
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 4; // 股票名称
    	repeated Qot_Common.KLine klList = 2; //K 线数据
    	optional bytes nextReqKey = 3; //分页请求 key。一次请求没有返回所有数据时，下次请求带上这个 key，会接着请求
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   K 线结构参见 [KLine](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5151)
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
            QotRequestHistoryKL.C2S c2s = QotRequestHistoryKL.C2S.CreateBuilder()
                    .SetRehabType((int)QotCommon.RehabType.RehabType_Forward)
                    .SetKlType((int)QotCommon.KLType.KLType_1Min)
                    .SetSecurity(sec)
                    .SetBeginTime("2020-09-01")
                    .SetEndTime("2020-09-10")
                .Build();
            QotRequestHistoryKL.Request req = QotRequestHistoryKL.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.RequestHistoryKL(req);
            Console.Write("Send QotRequestHistoryKL: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_RequestHistoryKL(FTAPI_Conn client, uint nSerialNo, QotRequestHistoryKL.Response rsp) {
            Console.Write("Reply: QotRequestHistoryKL: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("Code: {0},  lastClosePrice: {1} \n",
                rsp.S2C.Security.Code,
                rsp.S2C.KlListList[0].LastClosePrice);
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
57  
58  
59  

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6819076272648767075
    Send QotRequestHistoryKL: 3
    Reply: QotRequestHistoryKL: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      security {
        market: 1
        code: "00700"
      }
      klList {
        time: "2020-09-01 09:30:00"
        isBlank: false
        highPrice: 535.9
        openPrice: 535.9
        lowPrice: 535.9
        closePrice: 535.9
        lastClosePrice: 528.9
        volume: 665000
        turnover: 357437500
        turnoverRate: 0
        pe: 0
        changeRate: 1.3235016071090944
        timestamp: 1598923800
      }
      ...
      klList {
        time: "2020-09-09 16:00:00"
        isBlank: false
        highPrice: 503.9
        openPrice: 502.4
        lowPrice: 501.9
        closePrice: 502.4
        lastClosePrice: 502.9
        volume: 1504800
        turnover: 758472600
        turnoverRate: 0
        pe: 0
        changeRate: -0.0994233446013124
        timestamp: 1599638400
      }
    }
    
    Code: 00700,  lastClosePrice: 528.9
    

1  
2  
3  
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

`int requestHistoryKL(QotRequestHistoryKL.Request req);`  
`void onReply_RequestHistoryKL(FTAPI_Conn client, int nSerialNo, QotRequestHistoryKL.Response rsp);`

*   **介绍**

获取历史 K 线

*   **参数**

    
    message C2S
    {
    	required int32 rehabType = 1; //Qot_Common.RehabType,复权类型
    	required int32 klType = 2; //Qot_Common.KLType,K 线时间周期
    	required Qot_Common.Security security = 3; //股票市场以及股票代码
    	required string beginTime = 4; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 5; //结束时间字符串（格式：yyyy-MM-dd）
    	optional int32 maxAckKLNum = 6; //最多返回多少根 K 线，如果未指定表示不限制
    	optional int64 needKLFieldsFlag = 7; //指定返回 K 线结构体特定某几项数据，KLFields 枚举值或组合，如果未指定返回全部字段
    	optional bytes nextReqKey = 8; //分页请求 key
    	optional bool extendedTime = 9;  //是否获取美股盘前盘后数据，当前仅支持60分钟及以下级别。
        optional int32 session = 10; //是否获取美股盘前盘后以及夜盘数据，当前仅支持60分钟及以下级别，该参数指定后忽略extendedTime
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
13  
14  
15  
16  
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     
> *   K 线类型参见 [KLType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4119)
>     
> *   K 线字段类型参见 [KLFields](https://openapi.futunn.com/futu-api-doc/quote/quote.html#481)
>     

*   **返回**

    
    message S2C
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 4; // 股票名称
    	repeated Qot_Common.KLine klList = 2; //K 线数据
    	optional bytes nextReqKey = 3; //分页请求 key。一次请求没有返回所有数据时，下次请求带上这个 key，会接着请求
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   K 线结构参见 [KLine](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5151)
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
            QotRequestHistoryKL.C2S c2s = QotRequestHistoryKL.C2S.newBuilder()
                    .setRehabType(QotCommon.RehabType.RehabType_Forward_VALUE)
                    .setKlType(QotCommon.KLType.KLType_1Min_VALUE)
                    .setSecurity(sec)
                    .setBeginTime("2020-09-01")
                    .setEndTime("2020-09-02")
                .build();
            QotRequestHistoryKL.Request req = QotRequestHistoryKL.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.requestHistoryKL(req);
            System.out.printf("Send QotRequestHistoryKL: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_RequestHistoryKL(FTAPI_Conn client, int nSerialNo, QotRequestHistoryKL.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotRequestHistoryKL failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotRequestHistoryKL: %s\n", json);
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
68  
69  
70  

*   **Output**

    Send QotRequestHistoryKL: 2
    Receive QotRequestHistoryKL: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "klList": [{\
          "time": "2020-09-01 09:30:00",\
          "isBlank": false,\
          "highPrice": 535.9,\
          "openPrice": 535.9,\
          "lowPrice": 535.9,\
          "closePrice": 535.9,\
          "lastClosePrice": 528.9,\
          "volume": "665000",\
          "turnover": 3.574375E8,\
          "turnoverRate": 0.0,\
          "pe": 0.0,\
          "changeRate": 1.3235016071090944,\
          "timestamp": 1.5989238E9\
        }, ... {\
          "time": "2020-09-01 16:00:00",\
          "isBlank": false,\
          "highPrice": 542.4,\
          "openPrice": 542.4,\
          "lowPrice": 537.4,\
          "closePrice": 537.4,\
          "lastClosePrice": 542.4,\
          "volume": "1303900",\
          "turnover": 7.0309225E8,\
          "turnoverRate": 0.0,\
          "pe": 0.0,\
          "changeRate": -0.9218289085545722,\
          "timestamp": 1.5989472E9\
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
38  
39  
40  
41  

`Futu::u32_t RequestHistoryKL(const Qot_RequestHistoryKL::Request &stReq);`  
`virtual void OnReply_RequestHistoryKL(Futu::u32_t nSerialNo, const Qot_RequestHistoryKL::Response &stRsp) = 0;`

*   **介绍**
    
    获取历史 K 线
    
*   **参数**
    

    
    message C2S
    {
    	required int32 rehabType = 1; //Qot_Common.RehabType,复权类型
    	required int32 klType = 2; //Qot_Common.KLType,K 线时间周期
    	required Qot_Common.Security security = 3; //股票市场以及股票代码
    	required string beginTime = 4; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 5; //结束时间字符串（格式：yyyy-MM-dd）
    	optional int32 maxAckKLNum = 6; //最多返回多少根 K 线，如果未指定表示不限制
    	optional int64 needKLFieldsFlag = 7; //指定返回 K 线结构体特定某几项数据，KLFields 枚举值或组合，如果未指定返回全部字段
    	optional bytes nextReqKey = 8; //分页请求 key
    	optional bool extendedTime = 9;  //是否获取美股盘前盘后数据，当前仅支持60分钟及以下级别。
        optional int32 session = 10; //是否获取美股盘前盘后以及夜盘数据，当前仅支持60分钟及以下级别，该参数指定后忽略extendedTime
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
13  
14  
15  
16  
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     
> *   K 线类型参见 [KLType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4119)
>     
> *   K 线字段类型参见 [KLFields](https://openapi.futunn.com/futu-api-doc/quote/quote.html#481)
>     

*   **返回**

    
    message S2C
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 4; // 股票名称
    	repeated Qot_Common.KLine klList = 2; //K 线数据
    	optional bytes nextReqKey = 3; //分页请求 key。一次请求没有返回所有数据时，下次请求带上这个 key，会接着请求
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   K 线结构参见 [KLine](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5151)
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
    		Qot_RequestHistoryKL::Request req;
    		Qot_RequestHistoryKL::C2S *c2s = req.mutable_c2s();
    		c2s->set_rehabtype(1);
    		c2s->set_kltype(Qot_Common::KLType::KLType_1Min);
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->set_begintime("2021-06-07");
    		c2s->set_endtime("2021-07-01");
            
            m_RequestHistoryKLSerialNo = m_pQotApi->RequestHistoryKL(req);
            cout << "Request RequestHistoryKL SerialNo: " << m_RequestHistoryKLSerialNo << endl;
    	}
    
    	virtual void OnReply_RequestHistoryKL(Futu::u32_t nSerialNo, const Qot_RequestHistoryKL::Response &stRsp){
            if(nSerialNo == m_RequestHistoryKLSerialNo)
            {
                cout << "OnReply_RequestHistoryKL SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
        
        Futu::u32_t m_RequestHistoryKLSerialNo;
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
73  
74  
75  

*   **Output**

    connect
    Request RequestHistoryKL SerialNo: 3
    OnReply_RequestHistoryKL SerialNo: 3
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "klList": [\
       {\
        "time": "2021-06-07 09:30:00",\
        "isBlank": false,\
        "highPrice": 611,\
        "openPrice": 611,\
        "lowPrice": 611,\
        "closePrice": 611,\
        "lastClosePrice": 611.5,\
        "volume": "250100",\
        "turnover": 152811100,\
        "turnoverRate": 0,\
        "pe": 0,\
        "changeRate": -0.081766148814390843,\
        "timestamp": 1623029400\
       },\
       {\
        "time": "2021-06-07 09:31:00",\
        "isBlank": false,\
        "highPrice": 611.5,\
        "openPrice": 611,\
        "lowPrice": 609,\
        "closePrice": 609.5,\
        "lastClosePrice": 611,\
        "volume": "230700",\
        "turnover": 140792100,\
        "turnoverRate": 0,\
        "pe": 0,\
        "changeRate": -0.24549918166939444,\
        "timestamp": 1623029460\
       },\
    ...\
       {\
        "time": "2021-06-09 11:04:00",\
        "isBlank": false,\
        "highPrice": 604.5,\
        "openPrice": 604.5,\
        "lowPrice": 604,\
        "closePrice": 604,\
        "lastClosePrice": 604.5,\
        "volume": "17300",\
        "turnover": 10449350,\
        "turnoverRate": 0,\
        "pe": 0,\
        "changeRate": -0.0827129859387924,\
        "timestamp": 1623207840\
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

`RequestHistoryKL(req);`

*   **介绍**
    
    获取历史 K 线
    
*   **参数**
    

    
    message C2S
    {
    	required int32 rehabType = 1; //Qot_Common.RehabType,复权类型
    	required int32 klType = 2; //Qot_Common.KLType,K 线时间周期
    	required Qot_Common.Security security = 3; //股票市场以及股票代码
    	required string beginTime = 4; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 5; //结束时间字符串（格式：yyyy-MM-dd）
    	optional int32 maxAckKLNum = 6; //最多返回多少根 K 线，如果未指定表示不限制
    	optional int64 needKLFieldsFlag = 7; //指定返回 K 线结构体特定某几项数据，KLFields 枚举值或组合，如果未指定返回全部字段
    	optional bytes nextReqKey = 8; //分页请求 key
    	optional bool extendedTime = 9;  //是否获取美股盘前盘后数据，当前仅支持60分钟及以下级别。
        optional int32 session = 10; //是否获取美股盘前盘后以及夜盘数据，当前仅支持60分钟及以下级别，该参数指定后忽略extendedTime
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
13  
14  
15  
16  
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     
> *   K 线类型参见 [KLType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4119)
>     
> *   K 线字段类型参见 [KLFields](https://openapi.futunn.com/futu-api-doc/quote/quote.html#481)
>     

*   **返回**

    
    message S2C
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 4; // 股票名称
    	repeated Qot_Common.KLine klList = 2; //K 线数据
    	optional bytes nextReqKey = 3; //分页请求 key。一次请求没有返回所有数据时，下次请求带上这个 key，会接着请求
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   K 线结构参见 [KLine](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5151)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotRequestHistoryKL(){
        const { RetType } = Common
        const { RehabType, KLType, SubType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        rehabType: RehabType.RehabType_None, 
                        klType: KLType.KLType_Day,
                        security:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                        beginTime: "2021-08-05",
                        endTime: "2021-08-10",
                    },
                };
                
                websocket.RequestHistoryKL(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("HistoryKL: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
55  
56  
57  

*   **Output**

    OwnerPlate: errCode 0, retMsg , retType 0
    {
      "security": {
        "market": 1,
        "code": "00700"
      },
      "klList": [{\
        "time": "2021-08-05 00:00:00",\
        "isBlank": false,\
        "highPrice": 464.4,\
        "openPrice": 452.6,\
        "lowPrice": 432.6,\
        "closePrice": 439,\
        "lastClosePrice": 456.8,\
        "volume": "58625939",\
        "turnover": 26193545519,\
        "turnoverRate": 0.00611,\
        "pe": 22.137,\
        "changeRate": -3.896672504378286,\
        "timestamp": 1628092800\
      }, {\
        "time": "2021-08-06 00:00:00",\
        "isBlank": false,\
        "highPrice": 460.4,\
        "openPrice": 444.8,\
        "lowPrice": 440,\
        "closePrice": 453.6,\
        "lastClosePrice": 439,\
        "volume": "42946505",\
        "turnover": 19468783995,\
        "turnoverRate": 0.00447,\
        "pe": 22.873,\
        "changeRate": 3.3257403189066106,\
        "timestamp": 1628179200\
      }, {\
        "time": "2021-08-09 00:00:00",\
        "isBlank": false,\
        "highPrice": 472.4,\
        "openPrice": 447.8,\
        "lowPrice": 442.6,\
        "closePrice": 461.6,\
        "lastClosePrice": 453.6,\
        "volume": "40956433",\
        "turnover": 18847164570,\
        "turnoverRate": 0.00427,\
        "pe": 23.276,\
        "changeRate": 1.763668430335097,\
        "timestamp": 1628438400\
      }, {\
        "time": "2021-08-10 00:00:00",\
        "isBlank": false,\
        "highPrice": 493,\
        "openPrice": 478,\
        "lowPrice": 473.2,\
        "closePrice": 486.2,\
        "lastClosePrice": 461.6,\
        "volume": "40881986",\
        "turnover": 19749306230,\
        "turnoverRate": 0.00426,\
        "pe": 24.517,\
        "changeRate": 5.329289428076249,\
        "timestamp": 1628524800\
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

接口限制

*   分 K 提供最近 8 年数据，日 K 提供最近 20 年的数据， 日 K 以上不限制。
*   我们会根据您账户的资产和交易的情况，下发历史 K 线额度。因此，30 天内您只能获取有限只股票的历史 K 线数据。具体规则参见 [订阅额度 & 历史 K 线额度](https://openapi.futunn.com/futu-api-doc/intro/authority.html#1314)
    。您当日消耗的历史 K 线额度，会在 30 天后自动释放。
*   每 30 秒内最多请求 60 次历史 K 线接口。注意：如果您是分页获取数据，此限频规则仅适用于每只股票的首页，后续页请求不受限频规则的限制。
*   **换手率**，仅提供日 K 及以上级别。
*   **期权**，仅提供日K, 1分K，5分K，15分K，60分K。
*   美股 **盘前、盘后、夜盘 K 线**，仅支持 60 分钟及以下级别。由于美股盘前盘后和夜盘时段为非常规交易时段，此时段的 K 线数据可能不足 2 年。
*   美股的 **成交额**，仅提供 2015-10-12 之后的数据。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`request_history_kline(code, start=None, end=None, ktype=KLType.K_DAY, autype=AuType.QFQ, fields=[KL_FIELD.ALL], max_count=1000, page_req_key=None, extended_time=False)`

*   **介绍**
    
    获取历史 K 线
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 股票代码 |
    | start | str | 开始时间<br>(ℹ️ 格式：yyyy-MM-dd  <br>例如：“2017-06-20”) |
    | end | str | 结束时间<br>(ℹ️ 格式：yyyy-MM-dd  <br>例如：“2017-07-20”) |
    | ktype | [KLType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4119) | K 线类型 |
    | autype | [AuType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907) | 复权类型 |
    | fields | [KLFields](https://openapi.futunn.com/futu-api-doc/quote/quote.html#481) | 需返回的字段列表 |
    | max\_count | int | 本次请求最大返回的 K 线根数<br>(ℹ️ *   传 None 表示返回 start 和 end 之间所有的数据<br><br>*   注意：OpenD 接收到所有数据后才会下发给脚本，  <br>    如果您要获取的 K 线根数大于 1000 根，建议选择分页，防止出现超时) |
    | page\_req\_key | bytes | 分页请求<br>(ℹ️ 如果 start 和 end 之间的 K 线根数多于 max\_count：  <br>1\. 首页请求时应该传 None  <br>2\. 后续页请求时必须要传入上次调用返回的参数 page\_req\_key) |
    | extended\_time | bool | 是否允许美股盘前盘后数据<br>(ℹ️ False：不允许  <br>True：允许) |
    
    *   start 和 end 的组合如下
        
        | Start 类型 | End 类型 | 说明  |
        | --- | --- | --- |
        | str | str | start 和 end 分别为指定的日期 |
        | None | str | start 为 end 往前 365 天 |
        | str | None | end 为 start 往后 365 天 |
        | None | None | end 为当前日期，start 往前 365 天 |
        

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回历史 K 线数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    | page\_req\_key | bytes | 下一页请求的 key |
    
    *   历史 K 线数据格式如下:
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | time\_key | str | K 线时间<br>(ℹ️ 格式：yyyy-MM-dd HH:mm:ss  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | open | float | 开盘价 |
        | close | float | 收盘价 |
        | high | float | 最高价 |
        | low | float | 最低价 |
        | pe\_ratio | float | 市盈率<br>(ℹ️ 该字段为比例字段，默认不展示 %) |
        | turnover\_rate | float | 换手率 |
        | volume | int | 成交量 |
        | turnover | float | 成交额 |
        | change\_rate | float | 涨跌幅 |
        | last\_close | float | 昨收价 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret, data, page_req_key = quote_ctx.request_history_kline('US.AAPL', start='2019-09-11', end='2019-09-18', max_count=5, session=Session.ALL)  # 每页5个，请求第一页
    if ret == RET_OK:
        print(data)
        print(data['code'][0])    # 取第一条的股票代码
        print(data['close'].values.tolist())   # 第一页收盘价转为 list
    else:
        print('error:', data)
    while page_req_key != None:  # 请求后面的所有结果
        print('*************************************')
        ret, data, page_req_key = quote_ctx.request_history_kline('US.AAPL', start='2019-09-11', end='2019-09-18', max_count=5, page_req_key=page_req_key, session=Session.ALL) # 请求翻页后的数据
        if ret == RET_OK:
            print(data)
        else:
            print('error:', data)
    print('All pages are finished!')
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

*   **Output**

    code  name             time_key       open      close       high        low  pe_ratio  turnover_rate    volume      turnover  change_rate  last_close
    0  US.AAPL   苹果  2019-09-11 00:00:00  52.631194  53.963447  53.992409  52.549135    18.773        0.01039  177158584  9.808562e+09     3.179511   52.300545
    ..       ...   ...                  ...        ...        ...        ...        ...       ...            ...       ...           ...          ...         ...
    4  US.AAPL   苹果  2019-09-17 00:00:00  53.087346  53.265945  53.294907  52.884612    18.530        0.00432   73545872  4.046314e+09     0.363802   53.072865
    
    [5 rows x 13 columns]
    US.AAPL
    [53.9634465, 53.84156475, 52.7953125, 53.072865, 53.265945]
    *************************************
           code  name             time_key       open      close       high        low  pe_ratio  turnover_rate   volume      turnover  change_rate  last_close
    0  US.AAPL   苹果  2019-09-18 00:00:00  53.352831  53.76554  53.784847  52.961844    18.704        0.00602  102572372  5.682068e+09     0.937925   53.265945
    All pages are finished!
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

[#](https://openapi.futunn.com/futu-api-doc/quote/request-history-kline.html#4609-2)
 Qot\_RequestHistoryKL.proto
-----------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取历史 K 线
    
*   **参数**
    

    
    message C2S
    {
    	required int32 rehabType = 1; //Qot_Common.RehabType,复权类型
    	required int32 klType = 2; //Qot_Common.KLType,K 线时间周期
    	required Qot_Common.Security security = 3; //股票市场以及股票代码
    	required string beginTime = 4; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 5; //结束时间字符串（格式：yyyy-MM-dd）
    	optional int32 maxAckKLNum = 6; //最多返回多少根 K 线，如果未指定表示不限制
    	optional int64 needKLFieldsFlag = 7; //指定返回 K 线结构体特定某几项数据，KLFields 枚举值或组合，如果未指定返回全部字段
    	optional bytes nextReqKey = 8; //分页请求 key
    	optional bool extendedTime = 9;  //是否获取美股盘前盘后数据，当前仅支持60分钟及以下级别。
        optional int32 session = 10; //是否获取美股盘前盘后以及夜盘数据，当前仅支持60分钟及以下级别，该参数指定后忽略extendedTime
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
13  
14  
15  
16  
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     
> *   K 线类型参见 [KLType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4119)
>     
> *   K 线字段类型参见 [KLFields](https://openapi.futunn.com/futu-api-doc/quote/quote.html#481)
>     

*   **返回**

    
    message S2C
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 4; // 股票名称
    	repeated Qot_Common.KLine klList = 2; //K 线数据
    	optional bytes nextReqKey = 3; //分页请求 key。一次请求没有返回所有数据时，下次请求带上这个 key，会接着请求
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   K 线结构参见 [KLine](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5151)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3103
    

`uint RequestHistoryKL(QotRequestHistoryKL.Request req);`  
`virtual void OnReply_RequestHistoryKL(MMAPI_Conn client, uint nSerialNo, QotRequestHistoryKL.Response rsp);`

*   **介绍**

获取历史 K 线

*   **参数**

    
    message C2S
    {
    	required int32 rehabType = 1; //Qot_Common.RehabType,复权类型
    	required int32 klType = 2; //Qot_Common.KLType,K 线时间周期
    	required Qot_Common.Security security = 3; //股票市场以及股票代码
    	required string beginTime = 4; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 5; //结束时间字符串（格式：yyyy-MM-dd）
    	optional int32 maxAckKLNum = 6; //最多返回多少根 K 线，如果未指定表示不限制
    	optional int64 needKLFieldsFlag = 7; //指定返回 K 线结构体特定某几项数据，KLFields 枚举值或组合，如果未指定返回全部字段
    	optional bytes nextReqKey = 8; //分页请求 key
    	optional bool extendedTime = 9;  //是否获取美股盘前盘后数据，当前仅支持60分钟及以下级别。
        optional int32 session = 10; //是否获取美股盘前盘后以及夜盘数据，当前仅支持60分钟及以下级别，该参数指定后忽略extendedTime
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
13  
14  
15  
16  
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     
> *   K 线类型参见 [KLType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4119)
>     
> *   K 线字段类型参见 [KLFields](https://openapi.futunn.com/futu-api-doc/quote/quote.html#481)
>     

*   **返回**

    
    message S2C
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 4; // 股票名称
    	repeated Qot_Common.KLine klList = 2; //K 线数据
    	optional bytes nextReqKey = 3; //分页请求 key。一次请求没有返回所有数据时，下次请求带上这个 key，会接着请求
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   K 线结构参见 [KLine](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5151)
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
            QotRequestHistoryKL.C2S c2s = QotRequestHistoryKL.C2S.CreateBuilder()
                    .SetRehabType((int)QotCommon.RehabType.RehabType_Forward)
                    .SetKlType((int)QotCommon.KLType.KLType_1Min)
                    .SetSecurity(sec)
                    .SetBeginTime("2020-09-01")
                    .SetEndTime("2020-09-10")
                .Build();
            QotRequestHistoryKL.Request req = QotRequestHistoryKL.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.RequestHistoryKL(req);
            Console.Write("Send QotRequestHistoryKL: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_RequestHistoryKL(MMAPI_Conn client, uint nSerialNo, QotRequestHistoryKL.Response rsp) {
            Console.Write("Reply: QotRequestHistoryKL: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("Code: {0},  lastClosePrice: {1} \n",
                rsp.S2C.Security.Code,
                rsp.S2C.KlListList[0].LastClosePrice);
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
57  
58  
59  

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6819076272648767075
    Send QotRequestHistoryKL: 3
    Reply: QotRequestHistoryKL: 3  retType: 0
    retMsg: ""
    errCode: 0
    s2c {
      security {
        market: 1
        code: "00700"
      }
      klList {
        time: "2020-09-01 09:30:00"
        isBlank: false
        highPrice: 535.9
        openPrice: 535.9
        lowPrice: 535.9
        closePrice: 535.9
        lastClosePrice: 528.9
        volume: 665000
        turnover: 357437500
        turnoverRate: 0
        pe: 0
        changeRate: 1.3235016071090944
        timestamp: 1598923800
      }
      ...
      klList {
        time: "2020-09-09 16:00:00"
        isBlank: false
        highPrice: 503.9
        openPrice: 502.4
        lowPrice: 501.9
        closePrice: 502.4
        lastClosePrice: 502.9
        volume: 1504800
        turnover: 758472600
        turnoverRate: 0
        pe: 0
        changeRate: -0.0994233446013124
        timestamp: 1599638400
      }
    }
    
    Code: 00700,  lastClosePrice: 528.9
    

1  
2  
3  
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

`int requestHistoryKL(QotRequestHistoryKL.Request req);`  
`void onReply_RequestHistoryKL(MMAPI_Conn client, int nSerialNo, QotRequestHistoryKL.Response rsp);`

*   **介绍**

获取历史 K 线

*   **参数**

    
    message C2S
    {
    	required int32 rehabType = 1; //Qot_Common.RehabType,复权类型
    	required int32 klType = 2; //Qot_Common.KLType,K 线时间周期
    	required Qot_Common.Security security = 3; //股票市场以及股票代码
    	required string beginTime = 4; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 5; //结束时间字符串（格式：yyyy-MM-dd）
    	optional int32 maxAckKLNum = 6; //最多返回多少根 K 线，如果未指定表示不限制
    	optional int64 needKLFieldsFlag = 7; //指定返回 K 线结构体特定某几项数据，KLFields 枚举值或组合，如果未指定返回全部字段
    	optional bytes nextReqKey = 8; //分页请求 key
    	optional bool extendedTime = 9;  //是否获取美股盘前盘后数据，当前仅支持60分钟及以下级别。
        optional int32 session = 10; //是否获取美股盘前盘后以及夜盘数据，当前仅支持60分钟及以下级别，该参数指定后忽略extendedTime
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
13  
14  
15  
16  
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     
> *   K 线类型参见 [KLType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4119)
>     
> *   K 线字段类型参见 [KLFields](https://openapi.futunn.com/futu-api-doc/quote/quote.html#481)
>     

*   **返回**

    
    message S2C
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 4; // 股票名称
    	repeated Qot_Common.KLine klList = 2; //K 线数据
    	optional bytes nextReqKey = 3; //分页请求 key。一次请求没有返回所有数据时，下次请求带上这个 key，会接着请求
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   K 线结构参见 [KLine](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5151)
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
            QotRequestHistoryKL.C2S c2s = QotRequestHistoryKL.C2S.newBuilder()
                    .setRehabType(QotCommon.RehabType.RehabType_Forward_VALUE)
                    .setKlType(QotCommon.KLType.KLType_1Min_VALUE)
                    .setSecurity(sec)
                    .setBeginTime("2020-09-01")
                    .setEndTime("2020-09-02")
                .build();
            QotRequestHistoryKL.Request req = QotRequestHistoryKL.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.requestHistoryKL(req);
            System.out.printf("Send QotRequestHistoryKL: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_RequestHistoryKL(MMAPI_Conn client, int nSerialNo, QotRequestHistoryKL.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotRequestHistoryKL failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotRequestHistoryKL: %s\n", json);
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
68  
69  
70  

*   **Output**

    Send QotRequestHistoryKL: 2
    Receive QotRequestHistoryKL: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "security": {
          "market": 1,
          "code": "00700"
        },
        "klList": [{\
          "time": "2020-09-01 09:30:00",\
          "isBlank": false,\
          "highPrice": 535.9,\
          "openPrice": 535.9,\
          "lowPrice": 535.9,\
          "closePrice": 535.9,\
          "lastClosePrice": 528.9,\
          "volume": "665000",\
          "turnover": 3.574375E8,\
          "turnoverRate": 0.0,\
          "pe": 0.0,\
          "changeRate": 1.3235016071090944,\
          "timestamp": 1.5989238E9\
        }, ... {\
          "time": "2020-09-01 16:00:00",\
          "isBlank": false,\
          "highPrice": 542.4,\
          "openPrice": 542.4,\
          "lowPrice": 537.4,\
          "closePrice": 537.4,\
          "lastClosePrice": 542.4,\
          "volume": "1303900",\
          "turnover": 7.0309225E8,\
          "turnoverRate": 0.0,\
          "pe": 0.0,\
          "changeRate": -0.9218289085545722,\
          "timestamp": 1.5989472E9\
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
38  
39  
40  
41  

`moomoo::u32_t RequestHistoryKL(const Qot_RequestHistoryKL::Request &stReq);`  
`virtual void OnReply_RequestHistoryKL(moomoo::u32_t nSerialNo, const Qot_RequestHistoryKL::Response &stRsp) = 0;`

*   **介绍**
    
    获取历史 K 线
    
*   **参数**
    

    
    message C2S
    {
    	required int32 rehabType = 1; //Qot_Common.RehabType,复权类型
    	required int32 klType = 2; //Qot_Common.KLType,K 线时间周期
    	required Qot_Common.Security security = 3; //股票市场以及股票代码
    	required string beginTime = 4; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 5; //结束时间字符串（格式：yyyy-MM-dd）
    	optional int32 maxAckKLNum = 6; //最多返回多少根 K 线，如果未指定表示不限制
    	optional int64 needKLFieldsFlag = 7; //指定返回 K 线结构体特定某几项数据，KLFields 枚举值或组合，如果未指定返回全部字段
    	optional bytes nextReqKey = 8; //分页请求 key
    	optional bool extendedTime = 9;  //是否获取美股盘前盘后数据，当前仅支持60分钟及以下级别。
        optional int32 session = 10; //是否获取美股盘前盘后以及夜盘数据，当前仅支持60分钟及以下级别，该参数指定后忽略extendedTime
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
13  
14  
15  
16  
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     
> *   K 线类型参见 [KLType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4119)
>     
> *   K 线字段类型参见 [KLFields](https://openapi.futunn.com/futu-api-doc/quote/quote.html#481)
>     

*   **返回**

    
    message S2C
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 4; // 股票名称
    	repeated Qot_Common.KLine klList = 2; //K 线数据
    	optional bytes nextReqKey = 3; //分页请求 key。一次请求没有返回所有数据时，下次请求带上这个 key，会接着请求
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   K 线结构参见 [KLine](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5151)
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
    		Qot_RequestHistoryKL::Request req;
    		Qot_RequestHistoryKL::C2S *c2s = req.mutable_c2s();
    		c2s->set_rehabtype(1);
    		c2s->set_kltype(Qot_Common::KLType::KLType_1Min);
    		Qot_Common::Security *sec = c2s->mutable_security();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->set_begintime("2021-06-07");
    		c2s->set_endtime("2021-07-01");
            
            m_RequestHistoryKLSerialNo = m_pQotApi->RequestHistoryKL(req);
            cout << "Request RequestHistoryKL SerialNo: " << m_RequestHistoryKLSerialNo << endl;
    	}
    
    	virtual void OnReply_RequestHistoryKL(Futu::u32_t nSerialNo, const Qot_RequestHistoryKL::Response &stRsp){
            if(nSerialNo == m_RequestHistoryKLSerialNo)
            {
                cout << "OnReply_RequestHistoryKL SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
        
        moomoo::u32_t m_RequestHistoryKLSerialNo;
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
73  
74  
75  

*   **Output**

    connect
    Request RequestHistoryKL SerialNo: 3
    OnReply_RequestHistoryKL SerialNo: 3
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "security": {
       "market": 1,
       "code": "00700"
      },
      "klList": [\
       {\
        "time": "2021-06-07 09:30:00",\
        "isBlank": false,\
        "highPrice": 611,\
        "openPrice": 611,\
        "lowPrice": 611,\
        "closePrice": 611,\
        "lastClosePrice": 611.5,\
        "volume": "250100",\
        "turnover": 152811100,\
        "turnoverRate": 0,\
        "pe": 0,\
        "changeRate": -0.081766148814390843,\
        "timestamp": 1623029400\
       },\
       {\
        "time": "2021-06-07 09:31:00",\
        "isBlank": false,\
        "highPrice": 611.5,\
        "openPrice": 611,\
        "lowPrice": 609,\
        "closePrice": 609.5,\
        "lastClosePrice": 611,\
        "volume": "230700",\
        "turnover": 140792100,\
        "turnoverRate": 0,\
        "pe": 0,\
        "changeRate": -0.24549918166939444,\
        "timestamp": 1623029460\
       },\
    ...\
       {\
        "time": "2021-06-09 11:04:00",\
        "isBlank": false,\
        "highPrice": 604.5,\
        "openPrice": 604.5,\
        "lowPrice": 604,\
        "closePrice": 604,\
        "lastClosePrice": 604.5,\
        "volume": "17300",\
        "turnover": 10449350,\
        "turnoverRate": 0,\
        "pe": 0,\
        "changeRate": -0.0827129859387924,\
        "timestamp": 1623207840\
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

`RequestHistoryKL(req);`

*   **介绍**
    
    获取历史 K 线
    
*   **参数**
    

    
    message C2S
    {
    	required int32 rehabType = 1; //Qot_Common.RehabType,复权类型
    	required int32 klType = 2; //Qot_Common.KLType,K 线时间周期
    	required Qot_Common.Security security = 3; //股票市场以及股票代码
    	required string beginTime = 4; //开始时间字符串（格式：yyyy-MM-dd）
    	required string endTime = 5; //结束时间字符串（格式：yyyy-MM-dd）
    	optional int32 maxAckKLNum = 6; //最多返回多少根 K 线，如果未指定表示不限制
    	optional int64 needKLFieldsFlag = 7; //指定返回 K 线结构体特定某几项数据，KLFields 枚举值或组合，如果未指定返回全部字段
    	optional bytes nextReqKey = 8; //分页请求 key
    	optional bool extendedTime = 9;  //是否获取美股盘前盘后数据，当前仅支持60分钟及以下级别。
        optional int32 session = 10; //是否获取美股盘前盘后以及夜盘数据，当前仅支持60分钟及以下级别，该参数指定后忽略extendedTime
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
13  
14  
15  
16  
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   复权类型参见 [RehabType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6907)
>     
> *   K 线类型参见 [KLType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4119)
>     
> *   K 线字段类型参见 [KLFields](https://openapi.futunn.com/futu-api-doc/quote/quote.html#481)
>     

*   **返回**

    
    message S2C
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 4; // 股票名称
    	repeated Qot_Common.KLine klList = 2; //K 线数据
    	optional bytes nextReqKey = 3; //分页请求 key。一次请求没有返回所有数据时，下次请求带上这个 key，会接着请求
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   K 线结构参见 [KLine](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5151)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotRequestHistoryKL(){
        const { RetType } = Common
        const { RehabType, KLType, SubType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        rehabType: RehabType.RehabType_None, 
                        klType: KLType.KLType_Day,
                        security:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                        beginTime: "2021-08-05",
                        endTime: "2021-08-10",
                    },
                };
                
                websocket.RequestHistoryKL(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("HistoryKL: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
55  
56  
57  

*   **Output**

    OwnerPlate: errCode 0, retMsg , retType 0
    {
      "security": {
        "market": 1,
        "code": "00700"
      },
      "klList": [{\
        "time": "2021-08-05 00:00:00",\
        "isBlank": false,\
        "highPrice": 464.4,\
        "openPrice": 452.6,\
        "lowPrice": 432.6,\
        "closePrice": 439,\
        "lastClosePrice": 456.8,\
        "volume": "58625939",\
        "turnover": 26193545519,\
        "turnoverRate": 0.00611,\
        "pe": 22.137,\
        "changeRate": -3.896672504378286,\
        "timestamp": 1628092800\
      }, {\
        "time": "2021-08-06 00:00:00",\
        "isBlank": false,\
        "highPrice": 460.4,\
        "openPrice": 444.8,\
        "lowPrice": 440,\
        "closePrice": 453.6,\
        "lastClosePrice": 439,\
        "volume": "42946505",\
        "turnover": 19468783995,\
        "turnoverRate": 0.00447,\
        "pe": 22.873,\
        "changeRate": 3.3257403189066106,\
        "timestamp": 1628179200\
      }, {\
        "time": "2021-08-09 00:00:00",\
        "isBlank": false,\
        "highPrice": 472.4,\
        "openPrice": 447.8,\
        "lowPrice": 442.6,\
        "closePrice": 461.6,\
        "lastClosePrice": 453.6,\
        "volume": "40956433",\
        "turnover": 18847164570,\
        "turnoverRate": 0.00427,\
        "pe": 23.276,\
        "changeRate": 1.763668430335097,\
        "timestamp": 1628438400\
      }, {\
        "time": "2021-08-10 00:00:00",\
        "isBlank": false,\
        "highPrice": 493,\
        "openPrice": 478,\
        "lowPrice": 473.2,\
        "closePrice": 486.2,\
        "lastClosePrice": 461.6,\
        "volume": "40881986",\
        "turnover": 19749306230,\
        "turnoverRate": 0.00426,\
        "pe": 24.517,\
        "changeRate": 5.329289428076249,\
        "timestamp": 1628524800\
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

接口限制

*   分 K 提供最近 8 年数据，日 K 提供最近 20 年的数据，日 K 以上不限制。
*   我们会根据您账户的资产和交易的情况，下发历史 K 线额度。因此，30 天内您只能获取有限只股票的历史 K 线数据。具体规则参见 [订阅额度 & 历史 K 线额度](https://openapi.futunn.com/futu-api-doc/intro/authority.html#1314)
    。您当日消耗的历史 K 线额度，会在 30 天后自动释放。
*   每 30 秒内最多请求 60 次历史 K 线接口。注意：如果您是分页获取数据，此限频规则仅适用于每只股票的首页，后续页请求不受限频规则的限制。
*   **换手率**，仅提供日 K 及以上级别。
*   **期权**，仅提供日K, 1分K，5分K，15分K，60分K。
*   美股 **盘前、盘后、夜盘 K 线**，仅支持 60 分钟及以下级别。由于美股盘前盘后和夜盘时段为非常规交易时段，此时段的 K 线数据可能不足 2 年。
*   美股的 **成交额**，仅提供 2015-10-12 之后的数据。

← [获取股票所属板块](https://openapi.futunn.com/futu-api-doc/quote/get-owner-plate.html) [获取复权因子](https://openapi.futunn.com/futu-api-doc/quote/get-rehab.html)
 →