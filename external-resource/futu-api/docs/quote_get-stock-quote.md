# 获取实时报价 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html

[#](https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html#2619)
 获取实时报价
====================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_stock_quote(code_list)`

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 股票代码列表<br>(ℹ️ list 中元素类型是 str) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回报价数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   报价数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | data\_date | str | 日期  |
        | data\_time | str | 当前价更新时间<br>(ℹ️ 格式：yyyy-MM-dd HH:mm:ss  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | last\_price | float | 最新价格 |
        | open\_price | float | 今日开盘价 |
        | high\_price | float | 最高价格 |
        | low\_price | float | 最低价格 |
        | prev\_close\_price | float | 昨收盘价格 |
        | volume | int | 成交数量 |
        | turnover | float | 成交金额 |
        | turnover\_rate | float | 换手率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | amplitude | int | 振幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | suspension | bool | 是否停牌<br>(ℹ️ True：停牌) |
        | listing\_date | str | 上市日期<br>(ℹ️ 格式：yyyy-MM-dd) |
        | price\_spread | float | 当前向上的价差<br>(ℹ️ 即摆盘数据的卖档的相邻档位的报价差) |
        | dark\_status | [DarkStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1965) | 暗盘交易状态 |
        | sec\_status | [SecurityStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9969) | 股票状态 |
        | strike\_price | float | 行权价 |
        | contract\_size | float | 每份合约数 |
        | open\_interest | int | 未平仓合约数 |
        | implied\_volatility | float | 隐含波动率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | premium | float | 溢价<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | delta | float | 希腊值 Delta |
        | gamma | float | 希腊值 Gamma |
        | vega | float | 希腊值 Vega |
        | theta | float | 希腊值 Theta |
        | rho | float | 希腊值 Rho |
        | index\_option\_type | [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149) | 指数期权类型 |
        | net\_open\_interest | int | 净未平仓合约数<br>(ℹ️ 仅港股期权适用) |
        | expiry\_date\_distance | int | 距离到期日天数<br>(ℹ️ 负数表示已过期) |
        | contract\_nominal\_value | float | 合约名义金额<br>(ℹ️ 仅港股期权适用) |
        | owner\_lot\_multiplier | float | 相等正股手数<br>(ℹ️ 指数期权无该字段 ，仅港股期权适用) |
        | option\_area\_type | [OptionAreaType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7077) | 期权类型（按行权时间） |
        | contract\_multiplier | float | 合约乘数 |
        | pre\_price | float | 盘前价格 |
        | pre\_high\_price | float | 盘前最高价 |
        | pre\_low\_price | float | 盘前最低价 |
        | pre\_volume | int | 盘前成交量 |
        | pre\_turnover | float | 盘前成交额 |
        | pre\_change\_val | float | 盘前涨跌额 |
        | pre\_change\_rate | float | 盘前涨跌幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | pre\_amplitude | float | 盘前振幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | after\_price | float | 盘后价格 |
        | after\_high\_price | float | 盘后最高价 |
        | after\_low\_price | float | 盘后最低价 |
        | after\_volume | int | 盘后成交量<br>(ℹ️ 科创板支持此数据) |
        | after\_turnover | float | 盘后成交额<br>(ℹ️ 科创板支持此数据) |
        | after\_change\_val | float | 盘后涨跌额 |
        | after\_change\_rate | float | 盘后涨跌幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | after\_amplitude | float | 盘后振幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | overnight\_price | float | 夜盘价格 |
        | overnight\_high\_price | float | 夜盘最高价 |
        | overnight\_low\_price | float | 夜盘最低价 |
        | overnight\_volume | int | 夜盘成交量 |
        | overnight\_turnover | float | 夜盘成交额 |
        | overnight\_change\_val | float | 夜盘涨跌额 |
        | overnight\_change\_rate | float | 夜盘涨跌幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | overnight\_amplitude | float | 夜盘振幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | last\_settle\_price | float | 昨结<br>(ℹ️ 期货特有字段) |
        | position | float | 持仓量<br>(ℹ️ 期货特有字段) |
        | position\_change | float | 日增仓<br>(ℹ️ 期货特有字段) |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret_sub, err_message = quote_ctx.subscribe(['US.AAPL'], [SubType.QUOTE], subscribe_push=False)
    # 先订阅 K 线类型。订阅成功后 OpenD 将持续收到服务器的推送，False 代表暂时不需要推送给脚本
    if ret_sub == RET_OK:  # 订阅成功
        ret, data = quote_ctx.get_stock_quote(['US.AAPL'])  # 获取订阅股票报价的实时数据
        if ret == RET_OK:
            print(data)
            print(data['code'][0])   # 取第一条的股票代码
            print(data['code'].values.tolist())   # 转为 list
        else:
            print('error:', data)
    else:
        print('subscription failed', err_message)
    quote_ctx.close()  # 关闭当条连接，OpenD 会在1分钟后自动取消相应股票相应类型的订阅
    

1  
2  
3  
4  
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

    code name   data_date     data_time  last_price  open_price  high_price  low_price  prev_close_price     volume      turnover  turnover_rate  amplitude  suspension listing_date  price_spread dark_status sec_status strike_price contract_size open_interest implied_volatility premium delta gamma vega theta  rho net_open_interest expiry_date_distance contract_nominal_value owner_lot_multiplier option_area_type contract_multiplier last_settle_price position position_change index_option_type  pre_price  pre_high_price  pre_low_price  pre_volume  pre_turnover  pre_change_val  pre_change_rate  pre_amplitude  after_price  after_high_price  after_low_price  after_volume  after_turnover  after_change_val  after_change_rate  after_amplitude  overnight_price  overnight_high_price  overnight_low_price  overnight_volume  overnight_turnover  overnight_change_val  overnight_change_rate  overnight_amplitude
    0  US.AAPL   苹果  2025-04-07  05:37:21.794      188.38      193.89      199.88     187.34            203.19  125910913  2.424473e+10          0.838      6.172       False   1980-12-12          0.01         N/A     NORMAL          N/A           N/A           N/A                N/A     N/A   N/A   N/A  N/A   N/A  N/A               N/A                  N/A                    N/A                  N/A              N/A                 N/A               N/A      N/A             N/A               N/A     181.43          181.98         177.47      288853   52132735.18           -6.95           -3.689          2.394        186.6           188.639           186.44       3151311    5.930968e+08             -1.78             -0.944           1.1673           176.94                 186.5                174.4            533115         94944250.56                -11.44                 -6.072               6.4231
    US.AAPL
    ['US.AAPL']
    

1  
2  
3  
4  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html#5740)
 Qot\_GetBasicQot.proto
----------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
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

    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3004
    

`uint GetBasicQot(QotGetBasicQot.Request req);`  
`virtual void OnReply_GetBasicQot(FTAPI_Conn client, uint nSerialNo, QotGetBasicQot.Response rsp);`

*   **介绍**

获取已订阅股票的实时报价，必须要先订阅。

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

    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
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
            QotSub.C2S c2s = QotSub.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                    .AddSubTypeList((int)QotCommon.SubType.SubType_Basic)
                    .SetIsSubOrUnSub(true)
                    .Build();
            QotSub.Request req = QotSub.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.Sub(req);
            Console.Write("Send QotSub: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_Sub(FTAPI_Conn client, uint nSerialNo, QotSub.Response rsp) {
            Console.Write("Reply: QotSub: {0}  {1}\n", nSerialNo, rsp.ToString());
    
            if (rsp.RetType != (int)Common.RetType.RetType_Succeed)
                return;
    
            QotCommon.Security sec = QotCommon.Security.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetCode("00700")
                    .Build();
            QotGetBasicQot.C2S c2s = QotGetBasicQot.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                    .Build();
            QotGetBasicQot.Request req = QotGetBasicQot.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetBasicQot(req);
            Console.Write("Send QotGetBasicQot: {0}\n", seqNo);
        }
    
        public void OnReply_GetBasicQot(FTAPI_Conn client, uint nSerialNo, QotGetBasicQot.Response rsp)
        {
            Console.Write("Reply: QotGetBasicQot: {0}\n", nSerialNo);
            Console.Write("curPrice: {0}\n", rsp.S2C.BasicQotListList[0].CurPrice);
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

    Qot onInitConnect: ret=0 desc= connID=6825668646977718703
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Send QotGetBasicQot: 4
    Reply: QotGetBasicQot: 4
    curPrice: 459
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

`int getBasicQot(QotGetBasicQot.Request req);`  
`void onReply_GetBasicQot(FTAPI_Conn client, int nSerialNo, QotGetBasicQot.Response rsp);`

*   **介绍**

获取已订阅股票的实时报价，必须要先订阅。

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

    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
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
            QotSub.C2S c2s = QotSub.C2S.newBuilder()
                    .addSecurityList(sec)
                    .addSubTypeList(QotCommon.SubType.SubType_Basic_VALUE)
                    .setIsSubOrUnSub(true)
                    .build();
            QotSub.Request req = QotSub.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.sub(req);
            System.out.printf("Send QotSub: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_Sub(FTAPI_Conn client, int nSerialNo, QotSub.Response rsp) {
            System.out.printf("Reply: QotSub: %d  %s\n", nSerialNo, rsp.toString());
    
            if (rsp.getRetType() != Common.RetType.RetType_Succeed_VALUE)
                return;
    
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .setCode("00700")
                    .build();
            QotGetBasicQot.C2S c2s = QotGetBasicQot.C2S.newBuilder()
                    .addSecurityList(sec)
                    .build();
            QotGetBasicQot.Request req = QotGetBasicQot.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getBasicQot(req);
            System.out.printf("Send QotGetBasicQot: %d\n", seqNo);
        }
    
        @Override
        public void onReply_GetBasicQot(FTAPI_Conn client, int nSerialNo, QotGetBasicQot.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetBasicQot failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetBasicQot: %s\n", json);
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
71  
72  
73  
74  
75  
76  
77  
78  
79  
80  
81  
82  
83  
84  
85  
86  
87  

*   **Output**

    Send QotGetBasicQot: 3
    Receive QotGetBasicQot: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "basicQotList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "isSuspended": false,\
          "listTime": "2004-06-16",\
          "priceSpread": 0.5,\
          "updateTime": "2021-06-24 16:08:14",\
          "highPrice": 587.5,\
          "openPrice": 584.0,\
          "lowPrice": 580.0,\
          "curPrice": 583.0,\
          "lastClosePrice": 582.5,\
          "volume": "10947302",\
          "turnover": 6.387238277E9,\
          "turnoverRate": 0.114,\
          "amplitude": 1.288,\
          "darkStatus": 0,\
          "listTimestamp": 1.0873152E9,\
          "updateTimestamp": 1.624522094E9,\
          "secStatus": 1\
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

`Futu::u32_t GetBasicQot(const Qot_GetBasicQot::Request &stReq);`  
`virtual void OnReply_GetBasicQot(Futu::u32_t nSerialNo, const Qot_GetBasicQot::Response &stRsp) = 0;`

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
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

    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
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
    
    		// 这个接口要先订阅
    		Qot_Sub::Request req;
    		Qot_Sub::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->add_subtypelist(Qot_Common::SubType::SubType_Basic);
    		c2s->set_isregorunregpush(true);
    		c2s->set_issuborunsub(true);
            
            m_SubSerialNo = m_pQotApi->Sub(req);
            cout << "Request Sub SerialNo: " << m_SubSerialNo << endl;
    	}
    
    	virtual void OnReply_Sub(Futu::u32_t nSerialNo, const Qot_Sub::Response &stRsp)
    	{
            if(nSerialNo == m_SubSerialNo)
            {
                cout << "OnReply_Sub SerialNo: " << nSerialNo << endl;
                if (stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "Sub Failed" << endl;
                    return;
                }
    
                // 组包
                Qot_GetBasicQot::Request req;
                Qot_GetBasicQot::C2S *c2s = req.mutable_c2s();
                auto secList = c2s->mutable_securitylist();
                Qot_Common::Security *sec = secList->Add();
                sec->set_code("00700");
                sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
                
                m_GetBasicQotSerialNo = m_pQotApi->GetBasicQot(req);
                cout << "Request GetBasicQot SerialNo: " << m_GetBasicQotSerialNo << endl;
            }
    	}
    
    	virtual void OnReply_GetBasicQot(Futu::u32_t nSerialNo, const Qot_GetBasicQot::Response &stRsp){
            if(nSerialNo == m_GetBasicQotSerialNo)
            {
                cout << "OnReply_GetBasicQot SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_SubSerialNo;
        Futu::u32_t m_GetBasicQotSerialNo;
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
76  
77  
78  
79  
80  
81  
82  
83  
84  
85  
86  
87  
88  
89  
90  
91  
92  
93  
94  
95  
96  
97  
98  
99  
100  

*   **Output**

    connect
    Request Sub SerialNo: 3
    OnReply_Sub SerialNo: 3
    Request GetBasicQot SerialNo: 4
    OnReply_GetBasicQot SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "basicQotList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "00700"\
        },\
        "isSuspended": false,\
        "listTime": "2004-06-16",\
        "priceSpread": 0.5,\
        "updateTime": "2021-06-09 10:57:04",\
        "highPrice": 605.5,\
        "openPrice": 600,\
        "lowPrice": 597.5,\
        "curPrice": 603.5,\
        "lastClosePrice": 601,\
        "volume": "2680864",\
        "turnover": 1613335582,\
        "turnoverRate": 0.028,\
        "amplitude": 1.331,\
        "darkStatus": 0,\
        "listTimestamp": 1087315200,\
        "updateTimestamp": 1623207424,\
        "secStatus": 1\
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

`GetBasicQot(req);`

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
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

    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetBasicQot(){
        const { RetType } = Common
        const { SubType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.Sub({
                    c2s: {
                    securityList: [\
                        {\
                            market: QotMarket.QotMarket_HK_Security,\
                            code: "00700",\
                        },\
                    ],
                    subTypeList: [ SubType.SubType_Basic ], // 订阅实时报价类型
                    isSubOrUnSub: true, // 订阅 true, 反订阅 false
                    isRegOrUnRegPush: true, // 注册推送 true, 反注册推送 false
                    },
                })
                .then((res) => { 
    
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
                    websocket.GetBasicQot(req)
                    .then((res) => {
                        let { errCode, retMsg, retType,s2c } = res
                        console.log("BasicQot: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                        if(retType == RetType.RetType_Succeed){
                            let s2c = beautify(JSON.stringify(s2c), {
                                indent_size: 2,
                                space_in_empty_paren: true,
                            });
                            console.log(s2c);
                        }
                    })
                    .catch((error) => {
                        console.log("error:", error);
                    });
    
                })
                .catch((error) => {
                    if ("retMsg" in error) {
                        console.log("error:", error.retMsg);
                    }
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

    BasicQot: errCode 0, retMsg , retType 0
    {
      "basicQotList": [{\
        "security": {\
          "market": 1,\
          "code": "00700"\
        },\
        "isSuspended": false,\
        "listTime": "2004-06-16",\
        "priceSpread": 0.2,\
        "updateTime": "2021-09-09 16:08:17",\
        "highPrice": 511.5,\
        "openPrice": 509,\
        "lowPrice": 479,\
        "curPrice": 480,\
        "lastClosePrice": 524.5,\
        "volume": "54090872",\
        "turnover": 26716845932,\
        "turnoverRate": 0.563,\
        "amplitude": 6.196,\
        "darkStatus": 0,\
        "listTimestamp": 1087315200,\
        "updateTimestamp": 1631174897,\
        "secStatus": 1\
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

提示

*   此接口提供了一次性获取实时数据的功能，如需持续获取推送数据，请参考 [实时报价回调](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)
    

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_stock_quote(code_list)`

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 股票代码列表<br>(ℹ️ list 中元素类型是 str) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回报价数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   报价数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | data\_date | str | 日期  |
        | data\_time | str | 当前价更新时间<br>(ℹ️ 格式：yyyy-MM-dd HH:mm:ss  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | last\_price | float | 最新价格 |
        | open\_price | float | 今日开盘价 |
        | high\_price | float | 最高价格 |
        | low\_price | float | 最低价格 |
        | prev\_close\_price | float | 昨收盘价格 |
        | volume | int | 成交数量 |
        | turnover | float | 成交金额 |
        | turnover\_rate | float | 换手率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | amplitude | int | 振幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | suspension | bool | 是否停牌<br>(ℹ️ True：停牌) |
        | listing\_date | str | 上市日期<br>(ℹ️ 格式：yyyy-MM-dd) |
        | price\_spread | float | 当前向上的价差<br>(ℹ️ 即摆盘数据的卖档的相邻档位的报价差) |
        | dark\_status | [DarkStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1965) | 暗盘交易状态 |
        | sec\_status | [SecurityStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9969) | 股票状态 |
        | strike\_price | float | 行权价 |
        | contract\_size | float | 每份合约数 |
        | open\_interest | int | 未平仓合约数 |
        | implied\_volatility | float | 隐含波动率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | premium | float | 溢价<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | delta | float | 希腊值 Delta |
        | gamma | float | 希腊值 Gamma |
        | vega | float | 希腊值 Vega |
        | theta | float | 希腊值 Theta |
        | rho | float | 希腊值 Rho |
        | index\_option\_type | [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149) | 指数期权类型 |
        | net\_open\_interest | int | 净未平仓合约数<br>(ℹ️ 仅港股期权适用) |
        | expiry\_date\_distance | int | 距离到期日天数<br>(ℹ️ 负数表示已过期) |
        | contract\_nominal\_value | float | 合约名义金额<br>(ℹ️ 仅港股期权适用) |
        | owner\_lot\_multiplier | float | 相等正股手数<br>(ℹ️ 指数期权无该字段 ，仅港股期权适用) |
        | option\_area\_type | [OptionAreaType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7077) | 期权类型（按行权时间） |
        | contract\_multiplier | float | 合约乘数 |
        | pre\_price | float | 盘前价格 |
        | pre\_high\_price | float | 盘前最高价 |
        | pre\_low\_price | float | 盘前最低价 |
        | pre\_volume | int | 盘前成交量 |
        | pre\_turnover | float | 盘前成交额 |
        | pre\_change\_val | float | 盘前涨跌额 |
        | pre\_change\_rate | float | 盘前涨跌幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | pre\_amplitude | float | 盘前振幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | after\_price | float | 盘后价格 |
        | after\_high\_price | float | 盘后最高价 |
        | after\_low\_price | float | 盘后最低价 |
        | after\_volume | int | 盘后成交量<br>(ℹ️ 科创板支持此数据) |
        | after\_turnover | float | 盘后成交额<br>(ℹ️ 科创板支持此数据) |
        | after\_change\_val | float | 盘后涨跌额 |
        | after\_change\_rate | float | 盘后涨跌幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | after\_amplitude | float | 盘后振幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | overnight\_price | float | 夜盘价格 |
        | overnight\_high\_price | float | 夜盘最高价 |
        | overnight\_low\_price | float | 夜盘最低价 |
        | overnight\_volume | int | 夜盘成交量 |
        | overnight\_turnover | float | 夜盘成交额 |
        | overnight\_change\_val | float | 夜盘涨跌额 |
        | overnight\_change\_rate | float | 夜盘涨跌幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | overnight\_amplitude | float | 夜盘振幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | last\_settle\_price | float | 昨结<br>(ℹ️ 期货特有字段) |
        | position | float | 持仓量<br>(ℹ️ 期货特有字段) |
        | position\_change | float | 日增仓<br>(ℹ️ 期货特有字段) |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret_sub, err_message = quote_ctx.subscribe(['US.AAPL'], [SubType.QUOTE], subscribe_push=False)
    # 先订阅 K 线类型。订阅成功后 OpenD 将持续收到服务器的推送，False 代表暂时不需要推送给脚本
    if ret_sub == RET_OK:  # 订阅成功
        ret, data = quote_ctx.get_stock_quote(['US.AAPL'])  # 获取订阅股票报价的实时数据
        if ret == RET_OK:
            print(data)
            print(data['code'][0])   # 取第一条的股票代码
            print(data['code'].values.tolist())   # 转为 list
        else:
            print('error:', data)
    else:
        print('subscription failed', err_message)
    quote_ctx.close()  # 关闭当条连接，OpenD 会在1分钟后自动取消相应股票相应类型的订阅
    

1  
2  
3  
4  
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

    code name   data_date     data_time  last_price  open_price  high_price  low_price  prev_close_price     volume      turnover  turnover_rate  amplitude  suspension listing_date  price_spread dark_status sec_status strike_price contract_size open_interest implied_volatility premium delta gamma vega theta  rho net_open_interest expiry_date_distance contract_nominal_value owner_lot_multiplier option_area_type contract_multiplier last_settle_price position position_change index_option_type  pre_price  pre_high_price  pre_low_price  pre_volume  pre_turnover  pre_change_val  pre_change_rate  pre_amplitude  after_price  after_high_price  after_low_price  after_volume  after_turnover  after_change_val  after_change_rate  after_amplitude  overnight_price  overnight_high_price  overnight_low_price  overnight_volume  overnight_turnover  overnight_change_val  overnight_change_rate  overnight_amplitude
    0  US.AAPL   苹果  2025-04-07  05:37:21.794      188.38      193.89      199.88     187.34            203.19  125910913  2.424473e+10          0.838      6.172       False   1980-12-12          0.01         N/A     NORMAL          N/A           N/A           N/A                N/A     N/A   N/A   N/A  N/A   N/A  N/A               N/A                  N/A                    N/A                  N/A              N/A                 N/A               N/A      N/A             N/A               N/A     181.43          181.98         177.47      288853   52132735.18           -6.95           -3.689          2.394        186.6           188.639           186.44       3151311    5.930968e+08             -1.78             -0.944           1.1673           176.94                 186.5                174.4            533115         94944250.56                -11.44                 -6.072               6.4231
    US.AAPL
    ['US.AAPL']
    

1  
2  
3  
4  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html#5740-2)
 Qot\_GetBasicQot.proto
------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
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

    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3004
    

`uint GetBasicQot(QotGetBasicQot.Request req);`  
`virtual void OnReply_GetBasicQot(MMAPI_Conn client, uint nSerialNo, QotGetBasicQot.Response rsp);`

*   **介绍**

获取已订阅股票的实时报价，必须要先订阅。

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

    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
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
            QotSub.C2S c2s = QotSub.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                    .AddSubTypeList((int)QotCommon.SubType.SubType_Basic)
                    .SetIsSubOrUnSub(true)
                    .Build();
            QotSub.Request req = QotSub.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.Sub(req);
            Console.Write("Send QotSub: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_Sub(MMAPI_Conn client, uint nSerialNo, QotSub.Response rsp) {
            Console.Write("Reply: QotSub: {0}  {1}\n", nSerialNo, rsp.ToString());
    
            if (rsp.RetType != (int)Common.RetType.RetType_Succeed)
                return;
    
            QotCommon.Security sec = QotCommon.Security.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetCode("00700")
                    .Build();
            QotGetBasicQot.C2S c2s = QotGetBasicQot.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                    .Build();
            QotGetBasicQot.Request req = QotGetBasicQot.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetBasicQot(req);
            Console.Write("Send QotGetBasicQot: {0}\n", seqNo);
        }
    
        public void OnReply_GetBasicQot(MMAPI_Conn client, uint nSerialNo, QotGetBasicQot.Response rsp)
        {
            Console.Write("Reply: QotGetBasicQot: {0}\n", nSerialNo);
            Console.Write("curPrice: {0}\n", rsp.S2C.BasicQotListList[0].CurPrice);
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

    Qot onInitConnect: ret=0 desc= connID=6825668646977718703
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Send QotGetBasicQot: 4
    Reply: QotGetBasicQot: 4
    curPrice: 459
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

`int getBasicQot(QotGetBasicQot.Request req);`  
`void onReply_GetBasicQot(MMAPI_Conn client, int nSerialNo, QotGetBasicQot.Response rsp);`

*   **介绍**

获取已订阅股票的实时报价，必须要先订阅。

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

    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
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
            QotSub.C2S c2s = QotSub.C2S.newBuilder()
                    .addSecurityList(sec)
                    .addSubTypeList(QotCommon.SubType.SubType_Basic_VALUE)
                    .setIsSubOrUnSub(true)
                    .build();
            QotSub.Request req = QotSub.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.sub(req);
            System.out.printf("Send QotSub: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_Sub(MMAPI_Conn client, int nSerialNo, QotSub.Response rsp) {
            System.out.printf("Reply: QotSub: %d  %s\n", nSerialNo, rsp.toString());
    
            if (rsp.getRetType() != Common.RetType.RetType_Succeed_VALUE)
                return;
    
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .setCode("00700")
                    .build();
            QotGetBasicQot.C2S c2s = QotGetBasicQot.C2S.newBuilder()
                    .addSecurityList(sec)
                    .build();
            QotGetBasicQot.Request req = QotGetBasicQot.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getBasicQot(req);
            System.out.printf("Send QotGetBasicQot: %d\n", seqNo);
        }
    
        @Override
        public void onReply_GetBasicQot(MMAPI_Conn client, int nSerialNo, QotGetBasicQot.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetBasicQot failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetBasicQot: %s\n", json);
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
71  
72  
73  
74  
75  
76  
77  
78  
79  
80  
81  
82  
83  
84  
85  
86  
87  

*   **Output**

    Send QotGetBasicQot: 3
    Receive QotGetBasicQot: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "basicQotList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "isSuspended": false,\
          "listTime": "2004-06-16",\
          "priceSpread": 0.5,\
          "updateTime": "2021-06-24 16:08:14",\
          "highPrice": 587.5,\
          "openPrice": 584.0,\
          "lowPrice": 580.0,\
          "curPrice": 583.0,\
          "lastClosePrice": 582.5,\
          "volume": "10947302",\
          "turnover": 6.387238277E9,\
          "turnoverRate": 0.114,\
          "amplitude": 1.288,\
          "darkStatus": 0,\
          "listTimestamp": 1.0873152E9,\
          "updateTimestamp": 1.624522094E9,\
          "secStatus": 1\
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

`moomoo::u32_t GetBasicQot(const Qot_GetBasicQot::Request &stReq);`  
`virtual void OnReply_GetBasicQot(moomoo::u32_t nSerialNo, const Qot_GetBasicQot::Response &stRsp) = 0;`

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
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

    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
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
    
    		// 这个接口要先订阅
    		Qot_Sub::Request req;
    		Qot_Sub::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->add_subtypelist(Qot_Common::SubType::SubType_Basic);
    		c2s->set_isregorunregpush(true);
    		c2s->set_issuborunsub(true);
            
            m_SubSerialNo = m_pQotApi->Sub(req);
            cout << "Request Sub SerialNo: " << m_SubSerialNo << endl;
    	}
    
    	virtual void OnReply_Sub(moomoo::u32_t nSerialNo, const Qot_Sub::Response &stRsp)
    	{
            if(nSerialNo == m_SubSerialNo)
            {
                cout << "OnReply_Sub SerialNo: " << nSerialNo << endl;
                if (stRsp.rettype() != Common::RetType::RetType_Succeed)
                {
                    cout << "Sub Failed" << endl;
                    return;
                }
    
                // 组包
                Qot_GetBasicQot::Request req;
                Qot_GetBasicQot::C2S *c2s = req.mutable_c2s();
                auto secList = c2s->mutable_securitylist();
                Qot_Common::Security *sec = secList->Add();
                sec->set_code("00700");
                sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
                
                m_GetBasicQotSerialNo = m_pQotApi->GetBasicQot(req);
                cout << "Request GetBasicQot SerialNo: " << m_GetBasicQotSerialNo << endl;
            }
    	}
    
    	virtual void OnReply_GetBasicQot(Futu::u32_t nSerialNo, const Qot_GetBasicQot::Response &stRsp){
            if(nSerialNo == m_GetBasicQotSerialNo)
            {
                cout << "OnReply_GetBasicQot SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_SubSerialNo;
        moomoo::u32_t m_GetBasicQotSerialNo;
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
76  
77  
78  
79  
80  
81  
82  
83  
84  
85  
86  
87  
88  
89  
90  
91  
92  
93  
94  
95  
96  
97  
98  
99  
100  

*   **Output**

    connect
    Request Sub SerialNo: 3
    OnReply_Sub SerialNo: 3
    Request GetBasicQot SerialNo: 4
    OnReply_GetBasicQot SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "basicQotList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "00700"\
        },\
        "isSuspended": false,\
        "listTime": "2004-06-16",\
        "priceSpread": 0.5,\
        "updateTime": "2021-06-09 10:57:04",\
        "highPrice": 605.5,\
        "openPrice": 600,\
        "lowPrice": 597.5,\
        "curPrice": 603.5,\
        "lastClosePrice": 601,\
        "volume": "2680864",\
        "turnover": 1613335582,\
        "turnoverRate": 0.028,\
        "amplitude": 1.331,\
        "darkStatus": 0,\
        "listTimestamp": 1087315200,\
        "updateTimestamp": 1623207424,\
        "secStatus": 1\
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

`GetBasicQot(req);`

*   **介绍**
    
    获取已订阅股票的实时报价，必须要先订阅。
    
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

    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetBasicQot(){
        const { RetType } = Common
        const { SubType, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.Sub({
                    c2s: {
                    securityList: [\
                        {\
                            market: QotMarket.QotMarket_HK_Security,\
                            code: "00700",\
                        },\
                    ],
                    subTypeList: [ SubType.SubType_Basic ], // 订阅实时报价类型
                    isSubOrUnSub: true, // 订阅 true, 反订阅 false
                    isRegOrUnRegPush: true, // 注册推送 true, 反注册推送 false
                    },
                })
                .then((res) => { 
    
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
                    websocket.GetBasicQot(req)
                    .then((res) => {
                        let { errCode, retMsg, retType,s2c } = res
                        console.log("BasicQot: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                        if(retType == RetType.RetType_Succeed){
                            let s2c = beautify(JSON.stringify(s2c), {
                                indent_size: 2,
                                space_in_empty_paren: true,
                            });
                            console.log(s2c);
                        }
                    })
                    .catch((error) => {
                        console.log("error:", error);
                    });
    
                })
                .catch((error) => {
                    if ("retMsg" in error) {
                        console.log("error:", error.retMsg);
                    }
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

    BasicQot: errCode 0, retMsg , retType 0
    {
      "basicQotList": [{\
        "security": {\
          "market": 1,\
          "code": "00700"\
        },\
        "isSuspended": false,\
        "listTime": "2004-06-16",\
        "priceSpread": 0.2,\
        "updateTime": "2021-09-09 16:08:17",\
        "highPrice": 511.5,\
        "openPrice": 509,\
        "lowPrice": 479,\
        "curPrice": 480,\
        "lastClosePrice": 524.5,\
        "volume": "54090872",\
        "turnover": 26716845932,\
        "turnoverRate": 0.563,\
        "amplitude": 6.196,\
        "darkStatus": 0,\
        "listTimestamp": 1087315200,\
        "updateTimestamp": 1631174897,\
        "secStatus": 1\
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

提示

*   此接口提供了一次性获取实时数据的功能，如需持续获取推送数据，请参考 [实时报价回调](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)
    

← [获取快照](https://openapi.futunn.com/futu-api-doc/quote/get-market-snapshot.html) [获取实时摆盘](https://openapi.futunn.com/futu-api-doc/quote/get-order-book.html)
 →