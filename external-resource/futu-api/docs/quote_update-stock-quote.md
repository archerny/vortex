 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/update-stock-quote.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/update-stock-quote.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/update-stock-quote.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/update-stock-quote.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
    *   [行情接口总览](https://openapi.futunn.com/futu-api-doc/quote/overview.html)
        
    *   [行情对象](https://openapi.futunn.com/futu-api-doc/quote/base.html)
        
    *   实时行情
        
        *   订阅
            
        *   推送回调
            
            *   [实时报价回调](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html)
                
            *   [实时摆盘回调](https://openapi.futunn.com/futu-api-doc/quote/update-order-book.html)
                
            *   [实时 K 线回调](https://openapi.futunn.com/futu-api-doc/quote/update-kl.html)
                
            *   [实时分时回调](https://openapi.futunn.com/futu-api-doc/quote/update-rt.html)
                
            *   [实时逐笔回调](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html)
                
            *   [实时经纪队列回调](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html)
                
            
        *   拉取
            
        
    *   基本数据
        
    *   相关衍生品
        
    *   全市场筛选
        
    *   个性化
        
    *   [行情定义](https://openapi.futunn.com/futu-api-doc/quote/quote.html)
        
    
*   交易接口
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html#1544)
 实时报价回调
=======================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`on_recv_rsp(self, rsp_pb)`

*   **介绍**
    
    实时报价回调，异步处理已订阅股票的实时报价推送。  
    在收到实时报价数据推送后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Qot\_UpdateBasicQot\_pb2.Response | 派生类中不需要直接处理该参数 |
    
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
    

    import time
    from futu import *
    
    class StockQuoteTest(StockQuoteHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, data = super(StockQuoteTest,self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("StockQuoteTest: error, msg: %s" % data)
                return RET_ERROR, data
            print("StockQuoteTest ", data) # StockQuoteTest 自己的处理逻辑
            return RET_OK, data
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = StockQuoteTest()
    quote_ctx.set_handler(handler)  # 设置实时报价回调
    ret, data = quote_ctx.subscribe(['US.AAPL'], [SubType.QUOTE])  # 订阅实时报价类型，OpenD 开始持续收到服务器的推送
    if ret == RET_OK:
        print(data)
    else:
        print('error:', data)
    time.sleep(15)  #  设置脚本接收 OpenD 的推送持续时间为15秒
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
17  
18  
19  
20  
21  

*   **Output**

    StockQuoteTest        code name data_date data_time  last_price  open_price  high_price  low_price  prev_close_price  volume  turnover  turnover_rate  amplitude  suspension listing_date  price_spread dark_status sec_status strike_price contract_size open_interest implied_volatility premium delta gamma vega theta  rho net_open_interest expiry_date_distance contract_nominal_value owner_lot_multiplier option_area_type contract_multiplier last_settle_price position position_change index_option_type pre_price pre_high_price pre_low_price pre_volume pre_turnover pre_change_val pre_change_rate pre_amplitude after_price after_high_price after_low_price after_volume after_turnover after_change_val after_change_rate after_amplitude overnight_price overnight_high_price overnight_low_price overnight_volume overnight_turnover overnight_change_val overnight_change_rate overnight_amplitude
    0  US.AAPL   苹果                             0.0         0.0         0.0        0.0               0.0       0       0.0            0.0        0.0       False                        0.0         N/A     NORMAL          N/A           N/A           N/A                N/A     N/A   N/A   N/A  N/A   N/A  N/A               N/A                  N/A                    N/A                  N/A              N/A                 N/A               N/A      N/A             N/A               N/A       N/A            N/A           N/A        N/A          N/A            N/A             N/A           N/A         N/A              N/A             N/A          N/A            N/A              N/A               N/A             N/A             N/A                  N/A                 N/A              N/A                N/A                  N/A                   N/A                 N/A
    

1  
2  

[#](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html#3237)
 Qot\_UpdateBasicQot.proto
----------------------------------------------------------------------------------------------------------

*   **介绍**
    
    实时报价回调，异步处理已订阅股票的实时报价推送。
    
*   **参数**
    

    
    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3005
    

`virtual void OnReply_UpdateBasicQuote(FTAPI_Conn client, QotUpdateBasicQot.Response rsp);`

*   **介绍**
    
    实时报价回调，异步处理已订阅股票的实时报价推送。
    
*   **参数**
    

    
    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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
                    .SetIsRegOrUnRegPush(true)
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
        }
        
        public void OnReply_UpdateBasicQuote(FTAPI_Conn client, uint nSerialNo,  QotUpdateBasicQot.Response rsp) {
            Console.Write("Push: UpdateBasicQuote: {0}\n", nSerialNo);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825326119957149692
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Push: UpdateBasicQuote: 1
    curPrice: 493
    ...
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

`void onPush_UpdateBasicQuote(FTAPI_Conn client, QotUpdateBasicQot.Response rsp);`

*   **介绍**
    
    实时报价回调，异步处理已订阅股票的实时报价推送。
    
*   **参数**
    

    
    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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
                    .setIsRegOrUnRegPush(true)
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
            if (rsp.getRetType() != 0) {
                System.out.printf("QotSub failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotSub: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        @Override
        public void onPush_UpdateBasicQuote(FTAPI_Conn client, QotUpdateBasicQot.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotUpdateBasicQuote failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotUpdateBasicQuote: %s\n", json);
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

*   **Output**

    Receive UpdateBasicQuote: {
      "retType": 0,
      "s2c": {
        "basicQotList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "isSuspended": false,\
          "listTime": "2004-06-16",\
          "priceSpread": 0.5,\
          "updateTime": "2021-06-25 10:23:13",\
          "highPrice": 588.5,\
          "openPrice": 588.5,\
          "lowPrice": 584.0,\
          "curPrice": 586.5,\
          "lastClosePrice": 583.0,\
          "volume": "3860588",\
          "turnover": 2.265269305E9,\
          "turnoverRate": 0.04,\
          "amplitude": 0.772,\
          "darkStatus": 0,\
          "listTimestamp": 1.0873152E9,\
          "updateTimestamp": 1.624587793E9,\
          "secStatus": 1\
        }]
      }
    }
    Receive UpdateBasicQuote: {
      "retType": 0,
      "s2c": {
        "basicQotList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "isSuspended": false,\
          "listTime": "2004-06-16",\
          "priceSpread": 0.5,\
          "updateTime": "2021-06-25 10:23:13",\
          "highPrice": 588.5,\
          "openPrice": 588.5,\
          "lowPrice": 584.0,\
          "curPrice": 586.5,\
          "lastClosePrice": 583.0,\
          "volume": "3860688",\
          "turnover": 2.265327955E9,\
          "turnoverRate": 0.04,\
          "amplitude": 0.772,\
          "darkStatus": 0,\
          "listTimestamp": 1.0873152E9,\
          "updateTimestamp": 1.624587793E9,\
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

`virtual void OnPush_UpdateBasicQot(const Qot_UpdateBasicQot::Response &stRsp) = 0;`

*   **介绍**
    
    实时报价回调，异步处理已订阅股票的实时报价推送。
    
*   **参数**
    

    
    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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
            }
    	}
    
    	virtual void OnPush_UpdateBasicQot(const Qot_UpdateBasicQot::Response &stRsp) {
    		cout << "OnPush_UpdateBasicQot: " << endl;
    		// 解析内部结构打印出来
    		// ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
    		string resp_str;
    		ProtoBufToBodyData(stRsp, resp_str);
    		cout << UTF8ToLocal(resp_str) << endl;
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_SubSerialNo;
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

*   **Output**

    connect
    Request Sub SerialNo: 3
    OnReply_Sub SerialNo: 3
    OnPush_UpdateBasicQot: 
    {
     "retType": 0,
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
        "updateTime": "2021-06-09 11:05:41",\
        "highPrice": 605.5,\
        "openPrice": 600,\
        "lowPrice": 597.5,\
        "curPrice": 605,\
        "lastClosePrice": 601,\
        "volume": "2828591",\
        "turnover": 1702610650,\
        "turnoverRate": 0.029,\
        "amplitude": 1.331,\
        "darkStatus": 0,\
        "listTimestamp": 1087315200,\
        "updateTimestamp": 1623207941,\
        "secStatus": 1\
       }\
      ]
     }
    }
    
    OnPush_UpdateBasicQot: 
    {
     "retType": 0,
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
        "updateTime": "2021-06-09 11:05:41",\
        "highPrice": 605.5,\
        "openPrice": 600,\
        "lowPrice": 597.5,\
        "curPrice": 605,\
        "lastClosePrice": 601,\
        "volume": "2828691",\
        "turnover": 1702671150,\
        "turnoverRate": 0.029,\
        "amplitude": 1.331,\
        "darkStatus": 0,\
        "listTimestamp": 1087315200,\
        "updateTimestamp": 1623207941,\
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

`OnPush(cmd,res)`

*   **介绍**
    
    实时报价回调，异步处理已订阅股票的实时报价推送。
    
*   **参数**
    

    
    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    function UpdateBasicQot(){
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
                    subTypeList: [ SubType.SubType_Basic ], // 订阅实时报价类型
                    isSubOrUnSub: true, // 订阅 true, 反订阅 false
                    isRegOrUnRegPush: true, // 注册推送 true, 反注册推送 false
                    },
                }; // 订阅参数
    
                websocket.Sub(req) //# 订阅, OpenD 开始持续收到服务器的推送
                .then((res) => { })
                .catch((error) => {
                    if ("retMsg" in error) {
                        console.log("error:", error.retMsg);
                    }
                });
            } else {
                console.log("error", msg);
            }
        };
    
        websocket.onPush = (cmd, res)=>{
            if(ftCmdID.QotUpdateBasicQot.cmd == cmd){ // 实时报价推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    console.log("StockQuoteTest", JSON.stringify(s2c));
                } else {
                    console.log("StockQuoteTest: error")
                }
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
    
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 5000); // 接收 OpenD 的推送持续时间为5秒,5秒后断开
    }
    

1  
2  
3  
4  
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

    StockQuoteTest {"basicQotList":[{"security":{"market":1,"code":"00700"},"isSuspended":false,"listTime":"2004-06-16","priceSpread":0.2,"updateTime":"2021-09-09 16:08:17","highPrice":511.5,"openPrice":509,"lowPrice":479,"curPrice":480,"lastClosePrice":524.5,"volume":"54090872","turnover":26716845932,"turnoverRate":0.563,"amplitude":6.196,"darkStatus":0,"listTimestamp":1087315200,"updateTimestamp":1631174897,"secStatus":1}]}
    StockQuoteTest { ... }
    ...
    ...
    stop
    

1  
2  
3  
4  
5  

提示

*   此接口提供了持续获取推送数据的功能，如需一次性获取实时数据，请参考 [获取实时报价](https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html)
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
    
    实时报价回调，异步处理已订阅股票的实时报价推送。  
    在收到实时报价数据推送后会回调到该函数，您需要在派生类中覆盖 on\_recv\_rsp。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | rsp\_pb | Qot\_UpdateBasicQot\_pb2.Response | 派生类中不需要直接处理该参数 |
    
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
    

    import time
    from moomoo import *
    
    class StockQuoteTest(StockQuoteHandlerBase):
        def on_recv_rsp(self, rsp_pb):
            ret_code, data = super(StockQuoteTest,self).on_recv_rsp(rsp_pb)
            if ret_code != RET_OK:
                print("StockQuoteTest: error, msg: %s" % data)
                return RET_ERROR, data
            print("StockQuoteTest ", data) # StockQuoteTest 自己的处理逻辑
            return RET_OK, data
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = StockQuoteTest()
    quote_ctx.set_handler(handler)  # 设置实时报价回调
    ret, data = quote_ctx.subscribe(['US.AAPL'], [SubType.QUOTE])  # 订阅实时报价类型，OpenD 开始持续收到服务器的推送
    if ret == RET_OK:
        print(data)
    else:
        print('error:', data)
    time.sleep(15)  #  设置脚本接收 OpenD 的推送持续时间为15秒
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
17  
18  
19  
20  
21  

*   **Output**

    StockQuoteTest        code name data_date data_time  last_price  open_price  high_price  low_price  prev_close_price  volume  turnover  turnover_rate  amplitude  suspension listing_date  price_spread dark_status sec_status strike_price contract_size open_interest implied_volatility premium delta gamma vega theta  rho net_open_interest expiry_date_distance contract_nominal_value owner_lot_multiplier option_area_type contract_multiplier last_settle_price position position_change index_option_type pre_price pre_high_price pre_low_price pre_volume pre_turnover pre_change_val pre_change_rate pre_amplitude after_price after_high_price after_low_price after_volume after_turnover after_change_val after_change_rate after_amplitude overnight_price overnight_high_price overnight_low_price overnight_volume overnight_turnover overnight_change_val overnight_change_rate overnight_amplitude
    0  US.AAPL   苹果                             0.0         0.0         0.0        0.0               0.0       0       0.0            0.0        0.0       False                        0.0         N/A     NORMAL          N/A           N/A           N/A                N/A     N/A   N/A   N/A  N/A   N/A  N/A               N/A                  N/A                    N/A                  N/A              N/A                 N/A               N/A      N/A             N/A               N/A       N/A            N/A           N/A        N/A          N/A            N/A             N/A           N/A         N/A              N/A             N/A          N/A            N/A              N/A               N/A             N/A             N/A                  N/A                 N/A              N/A                N/A                  N/A                   N/A                 N/A
    

1  
2  

[#](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html#3237-2)
 Qot\_UpdateBasicQot.proto
------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    实时报价回调，异步处理已订阅股票的实时报价推送。
    
*   **参数**
    

    
    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3005
    

`virtual void OnReply_UpdateBasicQuote(MMAPI_Conn client, QotUpdateBasicQot.Response rsp);`

*   **介绍**
    
    实时报价回调，异步处理已订阅股票的实时报价推送。
    
*   **参数**
    

    
    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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
                    .SetIsRegOrUnRegPush(true)
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
        }
        
        public void OnReply_UpdateBasicQuote(MMAPI_Conn client, uint nSerialNo,  QotUpdateBasicQot.Response rsp) {
            Console.Write("Push: UpdateBasicQuote: {0}\n", nSerialNo);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825326119957149692
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    Push: UpdateBasicQuote: 1
    curPrice: 493
    ...
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

`void onPush_UpdateBasicQuote(MMAPI_Conn client, QotUpdateBasicQot.Response rsp);`

*   **介绍**
    
    实时报价回调，异步处理已订阅股票的实时报价推送。
    
*   **参数**
    

    
    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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
                    .setIsRegOrUnRegPush(true)
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
            if (rsp.getRetType() != 0) {
                System.out.printf("QotSub failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotSub: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        @Override
        public void onPush_UpdateBasicQuote(MMAPI_Conn client, QotUpdateBasicQot.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotUpdateBasicQuote failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotUpdateBasicQuote: %s\n", json);
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

*   **Output**

    Receive UpdateBasicQuote: {
      "retType": 0,
      "s2c": {
        "basicQotList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "isSuspended": false,\
          "listTime": "2004-06-16",\
          "priceSpread": 0.5,\
          "updateTime": "2021-06-25 10:23:13",\
          "highPrice": 588.5,\
          "openPrice": 588.5,\
          "lowPrice": 584.0,\
          "curPrice": 586.5,\
          "lastClosePrice": 583.0,\
          "volume": "3860588",\
          "turnover": 2.265269305E9,\
          "turnoverRate": 0.04,\
          "amplitude": 0.772,\
          "darkStatus": 0,\
          "listTimestamp": 1.0873152E9,\
          "updateTimestamp": 1.624587793E9,\
          "secStatus": 1\
        }]
      }
    }
    Receive UpdateBasicQuote: {
      "retType": 0,
      "s2c": {
        "basicQotList": [{\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "isSuspended": false,\
          "listTime": "2004-06-16",\
          "priceSpread": 0.5,\
          "updateTime": "2021-06-25 10:23:13",\
          "highPrice": 588.5,\
          "openPrice": 588.5,\
          "lowPrice": 584.0,\
          "curPrice": 586.5,\
          "lastClosePrice": 583.0,\
          "volume": "3860688",\
          "turnover": 2.265327955E9,\
          "turnoverRate": 0.04,\
          "amplitude": 0.772,\
          "darkStatus": 0,\
          "listTimestamp": 1.0873152E9,\
          "updateTimestamp": 1.624587793E9,\
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

`virtual void OnPush_UpdateBasicQot(const Qot_UpdateBasicQot::Response &stRsp) = 0;`

*   **介绍**
    
    实时报价回调，异步处理已订阅股票的实时报价推送。
    
*   **参数**
    

    
    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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
    
    
    	virtual void OnInitConnect(MMAPI_Conn* pConn, moomoo::i64_t nErrCode, const char* strDesc) {
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
            }
    	}
    
    	virtual void OnPush_UpdateBasicQot(const Qot_UpdateBasicQot::Response &stRsp) {
    		cout << "OnPush_UpdateBasicQot: " << endl;
    		// 解析内部结构打印出来
    		// ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
    		string resp_str;
    		ProtoBufToBodyData(stRsp, resp_str);
    		cout << UTF8ToLocal(resp_str) << endl;
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_SubSerialNo;
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

*   **Output**

    connect
    Request Sub SerialNo: 3
    OnReply_Sub SerialNo: 3
    OnPush_UpdateBasicQot: 
    {
     "retType": 0,
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
        "updateTime": "2021-06-09 11:05:41",\
        "highPrice": 605.5,\
        "openPrice": 600,\
        "lowPrice": 597.5,\
        "curPrice": 605,\
        "lastClosePrice": 601,\
        "volume": "2828591",\
        "turnover": 1702610650,\
        "turnoverRate": 0.029,\
        "amplitude": 1.331,\
        "darkStatus": 0,\
        "listTimestamp": 1087315200,\
        "updateTimestamp": 1623207941,\
        "secStatus": 1\
       }\
      ]
     }
    }
    
    OnPush_UpdateBasicQot: 
    {
     "retType": 0,
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
        "updateTime": "2021-06-09 11:05:41",\
        "highPrice": 605.5,\
        "openPrice": 600,\
        "lowPrice": 597.5,\
        "curPrice": 605,\
        "lastClosePrice": 601,\
        "volume": "2828691",\
        "turnover": 1702671150,\
        "turnoverRate": 0.029,\
        "amplitude": 1.331,\
        "darkStatus": 0,\
        "listTimestamp": 1087315200,\
        "updateTimestamp": 1623207941,\
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

`OnPush(cmd,res)`

*   **介绍**
    
    实时报价回调，异步处理已订阅股票的实时报价推送。
    
*   **参数**
    

    
    message S2C
    {
    	repeated Qot_Common.BasicQot basicQotList = 1; //股票基本行情
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

> *   基础报价结构参见 [BasicQot](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8209)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    function UpdateBasicQot(){
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
                    subTypeList: [ SubType.SubType_Basic ], // 订阅实时报价类型
                    isSubOrUnSub: true, // 订阅 true, 反订阅 false
                    isRegOrUnRegPush: true, // 注册推送 true, 反注册推送 false
                    },
                }; // 订阅参数
    
                websocket.Sub(req) //# 订阅, OpenD 开始持续收到服务器的推送
                .then((res) => { })
                .catch((error) => {
                    if ("retMsg" in error) {
                        console.log("error:", error.retMsg);
                    }
                });
            } else {
                console.log("error", msg);
            }
        };
    
        websocket.onPush = (cmd, res)=>{
            if(mmCmdID.QotUpdateBasicQot.cmd == cmd){ // 实时报价推送的处理逻辑
                let { retType, s2c } = res
                if(retType == RetType.RetType_Succeed){
                    console.log("StockQuoteTest", JSON.stringify(s2c));
                } else {
                    console.log("StockQuoteTest: error")
                }
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
    
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 5000); // 接收 OpenD 的推送持续时间为5秒,5秒后断开
    }
    

1  
2  
3  
4  
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

    StockQuoteTest {"basicQotList":[{"security":{"market":1,"code":"00700"},"isSuspended":false,"listTime":"2004-06-16","priceSpread":0.2,"updateTime":"2021-09-09 16:08:17","highPrice":511.5,"openPrice":509,"lowPrice":479,"curPrice":480,"lastClosePrice":524.5,"volume":"54090872","turnover":26716845932,"turnoverRate":0.563,"amplitude":6.196,"darkStatus":0,"listTimestamp":1087315200,"updateTimestamp":1631174897,"secStatus":1}]}
    StockQuoteTest { ... }
    ...
    ...
    stop
    

1  
2  
3  
4  
5  

提示

*   此接口提供了持续获取推送数据的功能，如需一次性获取实时数据，请参考 [获取实时报价](https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html)
     接口
*   获取实时数据 和 实时数据回调 的差别，请参考 [如何通过订阅接口获取实时行情？](https://openapi.futunn.com/futu-api-doc/qa/quote.html#2692)
    

← [获取订阅状态](https://openapi.futunn.com/futu-api-doc/quote/query-subscription.html) [实时摆盘回调](https://openapi.futunn.com/futu-api-doc/quote/update-order-book.html)
 →

[实时报价回调](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html)