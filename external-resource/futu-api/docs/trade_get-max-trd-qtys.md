[#](./trade_get-max-trd-qtys.md#2713)
 查询最大可买可卖
=======================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`acctradinginfo_query(order_type, code, price, order_id=None, adjust_limit=0, trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, session=Session.NONE, jp_acc_type=SubAccType.JP_GENERAL, position_id=NONE)`

*   **介绍**
    
    查询指定交易业务账户下的最大可买卖数量，亦可查询指定交易业务账户下指定订单的最大可改成的数量。
    
    现金账户请求期权不适用。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | order\_type | [OrderType](./trade_trade.md#4181) | 订单类型 |
    | code | str | 证券代码<br>(ℹ️ 如果是期货交易，且 code 为期货主连代码，则会自动转为对应的实际合约代码) |
    | price | float | 报价<br>(ℹ️ 证券账户精确到小数点后 3 位，超出部分会被舍弃)  <br>期货账户精确到小数点后 9 位，超出部分会被舍弃 |
    | order\_id | str | 订单号<br>(ℹ️ *   默认传 None，查询的是新下单的最大可买可卖数量)<br>*   如果是改单则要传订单号，此时计算最大可买可卖时，会返回此订单可改成的最大数量<br>*   如果通过此参数，查询某笔订单最大可改成的数量，需要在下单之后，间隔 0.5 秒以上再调用此接口 |
    | adjust\_limit | float | 价格微调幅度<br>(ℹ️ OpenD 会对传入价格自动调整到合法价位上（期货会忽略此参数）)<br><br>*   正数代表向上调整，负数代表向下调整<br>*   例如：0.015 代表向上调整且幅度不超过 1.5%；-0.01 代表向下调整且幅度不超过 1%。默认 0 表示不调整 |
    | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。)<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。)<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | session | [Session](./quote_quote.md#9152) | 美股交易时段<br>(ℹ️ 仅对美股生效，支持传入**RTH**、**ETH**、**OVERNIGHT**、**ALL**) |
    | jp\_acc\_type | [SubAccType](./trade_trade.md#6112) | 日本账户类型<br>(ℹ️ 仅日本券商适用) |
    | position\_id | int | 持仓ID<br>(ℹ️ *   适用于日本衍生品账户查询**持仓可卖**和**平仓需买回**)<br>*   可通过[查询持仓](./trade_get-position-list.md)<br>    接口获取 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回账号列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   账号列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | max\_cash\_buy | float | 现金可买<br>(ℹ️ *   期权的单位是“张”)<br>*   期货账户不适用 |
        | max\_cash\_and\_margin\_buy | float | 最大可买<br>(ℹ️ *   期权的单位是“张”)<br>*   期货账户不适用 |
        | max\_position\_sell | float | 持仓可卖<br>(ℹ️ 期权的单位是"张") |
        | max\_sell\_short | float | 可卖空<br>(ℹ️ *   期权的单位是“张”)<br>*   期货账户不适用 |
        | max\_buy\_back | float | 平仓需买入<br>(ℹ️ *   当持有净空仓时，必须先买回空头持仓的股数，才能再继续买多)<br>*   期货、期权的单位是“张” |
        | long\_required\_im | float | 买 1 张合约所带来的初始保证金变动<br>(ℹ️ *   当前仅期货和期权适用。)<br>*   无持仓时，返回 **买入** 1 张的初始保证金占用（正数）。<br>*   有多仓时，返回 **买入** 1 张的初始保证金占用（正数）。<br>*   有空仓时，返回 **买回** 1 张的初始保证金释放（负数）。 |
        | short\_required\_im | float | 卖 1 张合约所带来的初始保证金变动<br>(ℹ️ *   当前仅期货和期权适用。)<br>*   无持仓时，返回 **卖空** 1 张的初始保证金占用（正数）。<br>*   有多仓时，返回 **卖出** 1 张的初始保证金释放（负数）。<br>*   有空仓时，返回 **卖空** 1 张的初始保证金释放（正数）。 |
        | session | [Session](./quote_quote.md#9152) | 交易订单时段（仅用于美股） |
        
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.acctradinginfo_query(order_type=OrderType.NORMAL, code='HK.00700', price=400)
    if ret == RET_OK:
        print(data)
        print(data['max_cash_and_margin_buy'][0])  # 最大融资可买数量
    else:
        print('acctradinginfo_query error: ', data)
    trd_ctx.close()  # 关闭当条连接
    

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

        max_cash_buy  max_cash_and_margin_buy  max_position_sell  max_sell_short  max_buy_back long_required_im short_required_im    session
    0           0.0                   1500.0                0.0             0.0           0.0              N/A               N/A             N/A
    1500.0
    

1  
2  
3  

[#](./trade_get-max-trd-qtys.md#5308)
 Trd\_GetMaxTrdQtys.proto
-------------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询指定交易业务账户下的最大可买卖数量，亦可查询指定交易业务账户下指定订单的最大可改成的数量。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required int32 orderType = 2; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 3; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double price = 4; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）。如果是竞价、市价单，请也填入一个当前价格，服务器才好计算
    	optional uint64 orderID = 5; //订单号，新下订单不需要，如果是修改订单就需要把原订单号带上才行，因为改单的最大买卖数量会包含原订单数量。
    	//为保证与下单的价格同步，也提供调整价格选项，以下2个为调整价格使用，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 6; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整
    	optional double adjustSideAndLimit = 7; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 8; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string orderIDEx = 9; //表示服务器订单id，可以用来代替orderID，和orderID二选一
    	optional int32 session = 10; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 11; //持仓ID，日本券商查询平仓数量时使用
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
20  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   订单类型结构参见 [OrderType](./trade_trade.md#4181)
>     
> *   交易证券市场结构参见 [TrdSecMarket](./trade_trade.md#5084)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.MaxTrdQtys maxTrdQtys = 2; //最大可交易数量结构
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   最大可交易数量结构参见 [MaxTrdQtys](./trade_trade.md#8065)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    2111
    

`uint GetMaxTrdQtys(TrdGetMaxTrdQtys.Request req);`  
`virtual void OnReply_GetMaxTrdQtys(FTAPI_Conn client, uint nSerialNo, TrdGetMaxTrdQtys.Response rsp);`

*   **介绍**
    
    查询指定交易业务账户下的最大可买卖数量，亦可查询指定交易业务账户下指定订单的最大可改成的数量。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required int32 orderType = 2; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 3; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double price = 4; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）。如果是竞价、市价单，请也填入一个当前价格，服务器才好计算
    	optional uint64 orderID = 5; //订单号，新下订单不需要，如果是修改订单就需要把原订单号带上才行，因为改单的最大买卖数量会包含原订单数量。
    	//为保证与下单的价格同步，也提供调整价格选项，以下2个为调整价格使用，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 6; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整
    	optional double adjustSideAndLimit = 7; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 8; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string orderIDEx = 9; //表示服务器订单id，可以用来代替orderID，和orderID二选一		
    	optional int32 session = 10; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 11; //持仓ID，日本券商查询平仓数量时使用
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
20  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   订单类型结构参见 [OrderType](./trade_trade.md#4181)
>     
> *   交易证券市场结构参见 [TrdSecMarket](./trade_trade.md#5084)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.MaxTrdQtys maxTrdQtys = 2; //最大可交易数量结构
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   最大可交易数量结构参见 [MaxTrdQtys](./trade_trade.md#8065)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.CreateBuilder()
                    .SetAccID(281756457888247915L)
                    .SetTrdEnv((int)TrdCommon.TrdEnv.TrdEnv_Real)
                    .SetTrdMarket((int)TrdCommon.TrdMarket.TrdMarket_HK)
                    .Build();
            TrdGetMaxTrdQtys.C2S c2s = TrdGetMaxTrdQtys.C2S.CreateBuilder()
                    .SetHeader(header)
                    .SetOrderType((int)TrdCommon.OrderType.OrderType_Normal)
                    .SetCode("00700")
                    .SetPrice(520)
                    .SetSecMarket(TrdCommon.TrdSecMarket.TrdSecMarket_HK)
                .Build();
            TrdGetMaxTrdQtys.Request req = TrdGetMaxTrdQtys.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetMaxTrdQtys(req);
            Console.Write("Send TrdGetMaxTrdQtys: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetMaxTrdQtys(FTAPI_Conn client, uint nSerialNo, TrdGetMaxTrdQtys.Response rsp)
        {
            Console.Write("Reply: TrdGetMaxTrdQtys: {0} \n", nSerialNo);
            Console.Write("accID: {0}\n", rsp.S2C.Header.AccID);
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

    Trd onInitConnect: ret=0 desc= connID=6826812678028044696
    Send TrdGetMaxTrdQtys: 3
    Reply: TrdGetMaxTrdQtys: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int getMaxTrdQtys(TrdGetMaxTrdQtys.Request req);`  
`void onReply_GetMaxTrdQtys(FTAPI_Conn client, int nSerialNo, TrdGetMaxTrdQtys.Response rsp);`

*   **介绍**
    
    查询指定交易业务账户下的最大可买卖数量，亦可查询指定交易业务账户下指定订单的最大可改成的数量。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required int32 orderType = 2; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 3; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double price = 4; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）。如果是竞价、市价单，请也填入一个当前价格，服务器才好计算
    	optional uint64 orderID = 5; //订单号，新下订单不需要，如果是修改订单就需要把原订单号带上才行，因为改单的最大买卖数量会包含原订单数量。
    	//为保证与下单的价格同步，也提供调整价格选项，以下2个为调整价格使用，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 6; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整
    	optional double adjustSideAndLimit = 7; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 8; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string orderIDEx = 9; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
    	optional int32 session = 10; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 11; //持仓ID，日本券商查询平仓数量时使用
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
20  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   订单类型结构参见 [OrderType](./trade_trade.md#4181)
>     
> *   交易证券市场结构参见 [TrdSecMarket](./trade_trade.md#5084)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.MaxTrdQtys maxTrdQtys = 2; //最大可交易数量结构
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   最大可交易数量结构参见 [MaxTrdQtys](./trade_trade.md#8065)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.newBuilder()
                    .setAccID(281756457888247915L)
                    .setTrdEnv(TrdCommon.TrdEnv.TrdEnv_Real_VALUE)
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)
                    .build();
            TrdGetMaxTrdQtys.C2S c2s = TrdGetMaxTrdQtys.C2S.newBuilder()
                    .setHeader(header)
                    .setOrderType(TrdCommon.OrderType.OrderType_Normal_VALUE)
                    .setCode("00700")
                    .setPrice(520)
                    .setSecMarket(TrdCommon.TrdSecMarket.TrdSecMarket_HK_VALUE)
                .build();
            TrdGetMaxTrdQtys.Request req = TrdGetMaxTrdQtys.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getMaxTrdQtys(req);
            System.out.printf("Send TrdGetMaxTrdQtys: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetMaxTrdQtys(FTAPI_Conn client, int nSerialNo, TrdGetMaxTrdQtys.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetMaxTrdQtys failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetMaxTrdQtys: %s\n", json);
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
64  
65  
66  
67  
68  
69  
70  
71  

*   **Output**

    Send TrdGetMaxTrdQtys: 2
    Receive TrdGetMaxTrdQtys: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "maxTrdQtys": {
          "maxCashBuy": 0.0,
          "maxCashAndMarginBuy": 1400.0,
          "maxPositionSell": 0.0,
          "maxSellShort": 0.0,
          "maxBuyBack": 0.0
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

`Futu::u32_t GetMaxTrdQtys(const Trd_GetMaxTrdQtys::Request &stReq);`  
`virtual void OnReply_GetMaxTrdQtys(Futu::u32_t nSerialNo, const Trd_GetMaxTrdQtys::Response &stRsp) = 0;`

*   **介绍**
    
    查询指定交易业务账户下的最大可买卖数量，亦可查询指定交易业务账户下指定订单的最大可改成的数量。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required int32 orderType = 2; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 3; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double price = 4; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）。如果是竞价、市价单，请也填入一个当前价格，服务器才好计算
    	optional uint64 orderID = 5; //订单号，新下订单不需要，如果是修改订单就需要把原订单号带上才行，因为改单的最大买卖数量会包含原订单数量。
    	//为保证与下单的价格同步，也提供调整价格选项，以下2个为调整价格使用，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 6; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整
    	optional double adjustSideAndLimit = 7; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 8; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string orderIDEx = 9; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
    	optional int32 session = 10; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 11; //持仓ID，日本券商查询平仓数量时使用
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
20  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   订单类型结构参见 [OrderType](./trade_trade.md#4181)
>     
> *   交易证券市场结构参见 [TrdSecMarket](./trade_trade.md#5084)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.MaxTrdQtys maxTrdQtys = 2; //最大可交易数量结构
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   最大可交易数量结构参见 [MaxTrdQtys](./trade_trade.md#8065)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    		Trd_GetMaxTrdQtys::Request req;
    		Trd_GetMaxTrdQtys::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    		c2s->set_ordertype(1);
    		c2s->set_code("00700");
    		c2s->set_price(520);
            c2s->set_secmarket(Trd_Common::TrdMarket::TrdMarket_HK);
    
            m_GetMaxTrdQtysSerialNo = m_pTrdApi->GetMaxTrdQtys(req);
            cout << "Request GetMaxTrdQtys SerialNo: " << m_GetMaxTrdQtysSerialNo << endl;
    	}
    
    	virtual void OnReply_GetMaxTrdQtys(Futu::u32_t nSerialNo, const Trd_GetMaxTrdQtys::Response &stRsp){
            if(nSerialNo == m_GetMaxTrdQtysSerialNo)
            {
                cout << "OnReply_GetMaxTrdQtys SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_GetMaxTrdQtysSerialNo;
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

*   **Output**

    connect
    Request GetMaxTrdQtys SerialNo: 4
    OnReply_GetMaxTrdQtys SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "header": {
       "trdEnv": 0,
       "accID": "3637840",
       "trdMarket": 1
      },
      "maxTrdQtys": {
       "maxCashBuy": 1500,
       "maxCashAndMarginBuy": 1500,
       "maxPositionSell": 300
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

`GetMaxTrdQtys(req);`

*   **介绍**
    
    查询指定交易业务账户下的最大可买卖数量，亦可查询指定交易业务账户下指定订单的最大可改成的数量。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required int32 orderType = 2; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 3; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double price = 4; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）。如果是竞价、市价单，请也填入一个当前价格，服务器才好计算
    	optional uint64 orderID = 5; //订单号，新下订单不需要，如果是修改订单就需要把原订单号带上才行，因为改单的最大买卖数量会包含原订单数量。
    	//为保证与下单的价格同步，也提供调整价格选项，以下2个为调整价格使用，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 6; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整
    	optional double adjustSideAndLimit = 7; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 8; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string orderIDEx = 9; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
    	optional int32 session = 10; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 11; //持仓ID，日本券商查询平仓数量时使用
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
20  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   订单类型结构参见 [OrderType](./trade_trade.md#4181)
>     
> *   交易证券市场结构参见 [TrdSecMarket](./trade_trade.md#5084)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.MaxTrdQtys maxTrdQtys = 2; //最大可交易数量结构
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   最大可交易数量结构参见 [MaxTrdQtys](./trade_trade.md#8065)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetMaxTrdQtys(){
        const { RetType } = Common
        const { TrdEnv, OrderType, TrdMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.GetAccList({
                    c2s: {
                        userID: 0,
                    },
                }).then((res) => {
                    let { retType, s2c: { accList }  } = res
                    if(retType == RetType.RetType_Succeed){
                        let acc = accList.filter((item)=>{ 
                            return item.trdEnv == TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})
                        })[0]; // 样例取第一个香港市场虚拟环境账户
    
                        const req = {
                            c2s: {
                                header: {
                                    trdEnv: acc.trdEnv,
                                    accID: acc.accID,
                                    trdMarket: TrdMarket.TrdMarket_HK,
                                },
                                orderType: OrderType.OrderType_Normal,
                                code: "00700", // 指定账号对应市场中的代码
                                price: 100,
                                secMarket: TrdSecMarket.TrdSecMarket_HK,
                            },
                        };
    
                        websocket.GetMaxTrdQtys(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetMaxTrdQtys: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
    
                    }
                })
                .catch((error) => {
                    console.log("GetAccList error:", error);
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

*   **Output**

    GetMaxTrdQtys: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6684972",
        "trdMarket": 1
      },
      "maxTrdQtys": {
        "maxCashBuy": 1300,
        "maxCashAndMarginBuy": 1300,
        "maxPositionSell": 1900
      }
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

*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询最大可买可卖接口

提示

*   现金业务账户不支持交易衍生品，因此不支持通过现金业务账户查询期权的最大可买可卖。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`acctradinginfo_query(order_type, code, price, order_id=None, adjust_limit=0, trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, session=Session.NONE, jp_acc_type=SubAccType.JP_GENERAL, position_id=NONE)`

*   **介绍**
    
    查询指定交易业务账户下的最大可买卖数量，亦可查询指定交易业务账户下指定订单的最大可改成的数量。
    
    现金账户请求期权不适用。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | order\_type | [OrderType](./trade_trade.md#4181) | 订单类型 |
    | code | str | 证券代码<br>(ℹ️ 如果是期货交易，且 code 为期货主连代码，则会自动转为对应的实际合约代码) |
    | price | float | 报价<br>(ℹ️ 证券账户精确到小数点后 3 位，超出部分会被舍弃)  <br>期货账户精确到小数点后 9 位，超出部分会被舍弃 |
    | order\_id | str | 订单号<br>(ℹ️ *   默认传 None，查询的是新下单的最大可买可卖数量)<br>*   如果是改单则要传订单号，此时计算最大可买可卖时，会返回此订单可改成的最大数量<br>*   如果通过此参数，查询某笔订单最大可改成的数量，需要在下单之后，间隔 0.5 秒以上再调用此接口 |
    | adjust\_limit | float | 价格微调幅度<br>(ℹ️ OpenD 会对传入价格自动调整到合法价位上（期货会忽略此参数）)<br><br>*   正数代表向上调整，负数代表向下调整<br>*   例如：0.015 代表向上调整且幅度不超过 1.5%；-0.01 代表向下调整且幅度不超过 1%。默认 0 表示不调整 |
    | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。)<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。)<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | session | [Session](./quote_quote.md#9152) | 美股交易时段<br>(ℹ️ 仅对美股生效，支持传入**RTH**、**ETH**、**OVERNIGHT**、**ALL**) |
    | jp\_acc\_type | [SubAccType](./trade_trade.md#6112) | 日本账户类型<br>(ℹ️ 仅日本券商适用) |
    | position\_id | int | 持仓ID<br>(ℹ️ *   适用于日本衍生品账户查询**持仓可卖**和**平仓需买回**)<br>*   可通过[查询持仓](./trade_get-position-list.md)<br>    接口获取 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回账号列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   账号列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | max\_cash\_buy | float | 现金可买<br>(ℹ️ *   期权的单位是“张”)<br>*   期货账户不适用 |
        | max\_cash\_and\_margin\_buy | float | 最大可买<br>(ℹ️ *   期权的单位是“张”)<br>*   期货账户不适用 |
        | max\_position\_sell | float | 持仓可卖<br>(ℹ️ 期权的单位是"张") |
        | max\_sell\_short | float | 可卖空<br>(ℹ️ *   期权的单位是“张”)<br>*   期货账户不适用 |
        | max\_buy\_back | float | 平仓需买入<br>(ℹ️ *   当持有净空仓时，必须先买回空头持仓的股数，才能再继续买多)<br>*   期货、期权的单位是“张” |
        | long\_required\_im | float | 买 1 张合约所带来的初始保证金变动。<br>(ℹ️ *   当前仅期货和期权适用。)<br>*   无持仓时，返回 **买入** 1 张的初始保证金占用（正数）。<br>*   有多仓时，返回 **买入** 1 张的初始保证金占用（正数）。<br>*   有空仓时，返回 **买回** 1 张的初始保证金释放（负数）。 |
        | short\_required\_im | float | 卖 1 张合约所带来的初始保证金变动。<br>(ℹ️ *   当前仅期货和期权适用。)<br>*   无持仓时，返回 **卖空** 1 张的初始保证金占用（正数）。<br>*   有多仓时，返回 **卖出** 1 张的初始保证金释放（负数）。<br>*   有空仓时，返回 **卖空** 1 张的初始保证金释放（正数）。 |
        | session | [Session](./quote_quote.md#9152) | 交易订单时段（仅用于美股） |
        
*   **Example**
    

    from moomoo import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    ret, data = trd_ctx.acctradinginfo_query(order_type=OrderType.NORMAL, code='US.AAPL', price=400)
    if ret == RET_OK:
        print(data)
        print(data['max_cash_and_margin_buy'][0])  # 最大融资可买数量
    else:
        print('acctradinginfo_query error: ', data)
    trd_ctx.close()  # 关闭当条连接
    

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

        max_cash_buy  max_cash_and_margin_buy  max_position_sell  max_sell_short  max_buy_back long_required_im short_required_im   session
    0           0.0                   1500.0                0.0             0.0           0.0              N/A               N/A            N/A
    1500.0
    

1  
2  
3  

[#](./trade_get-max-trd-qtys.md#5308-2)
 Trd\_GetMaxTrdQtys.proto
---------------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询指定交易业务账户下的最大可买卖数量，亦可查询指定交易业务账户下指定订单的最大可改成的数量。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required int32 orderType = 2; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 3; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double price = 4; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）。如果是竞价、市价单，请也填入一个当前价格，服务器才好计算
    	optional uint64 orderID = 5; //订单号，新下订单不需要，如果是修改订单就需要把原订单号带上才行，因为改单的最大买卖数量会包含原订单数量。
    	//为保证与下单的价格同步，也提供调整价格选项，以下2个为调整价格使用，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 6; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整
    	optional double adjustSideAndLimit = 7; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 8; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string orderIDEx = 9; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
    	optional int32 session = 10; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 11; //持仓ID，日本券商查询平仓数量时使用
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
20  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   订单类型结构参见 [OrderType](./trade_trade.md#4181)
>     
> *   交易证券市场结构参见 [TrdSecMarket](./trade_trade.md#5084)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.MaxTrdQtys maxTrdQtys = 2; //最大可交易数量结构
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   最大可交易数量结构参见 [MaxTrdQtys](./trade_trade.md#8065)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    2111
    

`uint GetMaxTrdQtys(TrdGetMaxTrdQtys.Request req);`  
`virtual void OnReply_GetMaxTrdQtys(MMAPI_Conn client, uint nSerialNo, TrdGetMaxTrdQtys.Response rsp);`

*   **介绍**
    
    查询指定交易业务账户下的最大可买卖数量，亦可查询指定交易业务账户下指定订单的最大可改成的数量。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required int32 orderType = 2; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 3; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double price = 4; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）。如果是竞价、市价单，请也填入一个当前价格，服务器才好计算
    	optional uint64 orderID = 5; //订单号，新下订单不需要，如果是修改订单就需要把原订单号带上才行，因为改单的最大买卖数量会包含原订单数量。
    	//为保证与下单的价格同步，也提供调整价格选项，以下2个为调整价格使用，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 6; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整
    	optional double adjustSideAndLimit = 7; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 8; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string orderIDEx = 9; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
    	optional int32 session = 10; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 11; //持仓ID，日本券商查询平仓数量时使用
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
20  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   订单类型结构参见 [OrderType](./trade_trade.md#4181)
>     
> *   交易证券市场结构参见 [TrdSecMarket](./trade_trade.md#5084)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.MaxTrdQtys maxTrdQtys = 2; //最大可交易数量结构
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   最大可交易数量结构参见 [MaxTrdQtys](./trade_trade.md#8065)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.CreateBuilder()
                    .SetAccID(281756457888247915L)
                    .SetTrdEnv((int)TrdCommon.TrdEnv.TrdEnv_Real)
                    .SetTrdMarket((int)TrdCommon.TrdMarket.TrdMarket_HK)
                    .Build();
            TrdGetMaxTrdQtys.C2S c2s = TrdGetMaxTrdQtys.C2S.CreateBuilder()
                    .SetHeader(header)
                    .SetOrderType((int)TrdCommon.OrderType.OrderType_Normal)
                    .SetCode("00700")
                    .SetPrice(520)
                    .SetSecMarket(TrdCommon.TrdSecMarket.TrdSecMarket_HK)
                .Build();
            TrdGetMaxTrdQtys.Request req = TrdGetMaxTrdQtys.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetMaxTrdQtys(req);
            Console.Write("Send TrdGetMaxTrdQtys: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetMaxTrdQtys(MMAPI_Conn client, uint nSerialNo, TrdGetMaxTrdQtys.Response rsp)
        {
            Console.Write("Reply: TrdGetMaxTrdQtys: {0} \n", nSerialNo);
            Console.Write("accID: {0}\n", rsp.S2C.Header.AccID);
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

    Trd onInitConnect: ret=0 desc= connID=6826812678028044696
    Send TrdGetMaxTrdQtys: 3
    Reply: TrdGetMaxTrdQtys: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int getMaxTrdQtys(TrdGetMaxTrdQtys.Request req);`  
`void onReply_GetMaxTrdQtys(MMAPI_Conn client, int nSerialNo, TrdGetMaxTrdQtys.Response rsp);`

*   **介绍**
    
    查询指定交易业务账户下的最大可买卖数量，亦可查询指定交易业务账户下指定订单的最大可改成的数量。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required int32 orderType = 2; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 3; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double price = 4; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）。如果是竞价、市价单，请也填入一个当前价格，服务器才好计算
    	optional uint64 orderID = 5; //订单号，新下订单不需要，如果是修改订单就需要把原订单号带上才行，因为改单的最大买卖数量会包含原订单数量。
    	//为保证与下单的价格同步，也提供调整价格选项，以下2个为调整价格使用，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 6; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整
    	optional double adjustSideAndLimit = 7; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 8; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string orderIDEx = 9; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
    	optional int32 session = 10; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 11; //持仓ID，日本券商查询平仓数量时使用
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
20  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   订单类型结构参见 [OrderType](./trade_trade.md#4181)
>     
> *   交易证券市场结构参见 [TrdSecMarket](./trade_trade.md#5084)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.MaxTrdQtys maxTrdQtys = 2; //最大可交易数量结构
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   最大可交易数量结构参见 [MaxTrdQtys](./trade_trade.md#8065)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.newBuilder()
                    .setAccID(281756457888247915L)
                    .setTrdEnv(TrdCommon.TrdEnv.TrdEnv_Real_VALUE)
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)
                    .build();
            TrdGetMaxTrdQtys.C2S c2s = TrdGetMaxTrdQtys.C2S.newBuilder()
                    .setHeader(header)
                    .setOrderType(TrdCommon.OrderType.OrderType_Normal_VALUE)
                    .setCode("00700")
                    .setPrice(520)
                    .setSecMarket(TrdCommon.TrdSecMarket.TrdSecMarket_HK_VALUE)
                .build();
            TrdGetMaxTrdQtys.Request req = TrdGetMaxTrdQtys.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getMaxTrdQtys(req);
            System.out.printf("Send TrdGetMaxTrdQtys: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetMaxTrdQtys(MMAPI_Conn client, int nSerialNo, TrdGetMaxTrdQtys.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetMaxTrdQtys failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetMaxTrdQtys: %s\n", json);
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
64  
65  
66  
67  
68  
69  
70  
71  

*   **Output**

    Send TrdGetMaxTrdQtys: 2
    Receive TrdGetMaxTrdQtys: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "maxTrdQtys": {
          "maxCashBuy": 0.0,
          "maxCashAndMarginBuy": 1400.0,
          "maxPositionSell": 0.0,
          "maxSellShort": 0.0,
          "maxBuyBack": 0.0
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

`moomoo::u32_t GetMaxTrdQtys(const Trd_GetMaxTrdQtys::Request &stReq);`  
`virtual void OnReply_GetMaxTrdQtys(moomoo::u32_t nSerialNo, const Trd_GetMaxTrdQtys::Response &stRsp) = 0;`

*   **介绍**
    
    查询指定交易业务账户下的最大可买卖数量，亦可查询指定交易业务账户下指定订单的最大可改成的数量。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required int32 orderType = 2; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 3; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double price = 4; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）。如果是竞价、市价单，请也填入一个当前价格，服务器才好计算
    	optional uint64 orderID = 5; //订单号，新下订单不需要，如果是修改订单就需要把原订单号带上才行，因为改单的最大买卖数量会包含原订单数量。
    	//为保证与下单的价格同步，也提供调整价格选项，以下2个为调整价格使用，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 6; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整
    	optional double adjustSideAndLimit = 7; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 8; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string orderIDEx = 9; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
    	optional int32 session = 10; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 11; //持仓ID，日本券商查询平仓数量时使用
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
20  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   订单类型结构参见 [OrderType](./trade_trade.md#4181)
>     
> *   交易证券市场结构参见 [TrdSecMarket](./trade_trade.md#5084)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.MaxTrdQtys maxTrdQtys = 2; //最大可交易数量结构
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   最大可交易数量结构参见 [MaxTrdQtys](./trade_trade.md#8065)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    		Trd_GetMaxTrdQtys::Request req;
    		Trd_GetMaxTrdQtys::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    		c2s->set_ordertype(1);
    		c2s->set_code("00700");
    		c2s->set_price(520);
            c2s->set_secmarket(Trd_Common::TrdMarket::TrdMarket_HK);
    
            m_GetMaxTrdQtysSerialNo = m_pTrdApi->GetMaxTrdQtys(req);
            cout << "Request GetMaxTrdQtys SerialNo: " << m_GetMaxTrdQtysSerialNo << endl;
    	}
    
    	virtual void OnReply_GetMaxTrdQtys(moomoo::u32_t nSerialNo, const Trd_GetMaxTrdQtys::Response &stRsp){
            if(nSerialNo == m_GetMaxTrdQtysSerialNo)
            {
                cout << "OnReply_GetMaxTrdQtys SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Trd *m_pTrdApi;
    
        moomoo::u32_t m_GetMaxTrdQtysSerialNo;
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

*   **Output**

    connect
    Request GetMaxTrdQtys SerialNo: 4
    OnReply_GetMaxTrdQtys SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "header": {
       "trdEnv": 0,
       "accID": "3637840",
       "trdMarket": 1
      },
      "maxTrdQtys": {
       "maxCashBuy": 1500,
       "maxCashAndMarginBuy": 1500,
       "maxPositionSell": 300
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

`GetMaxTrdQtys(req);`

*   **介绍**
    
    查询指定交易业务账户下的最大可买卖数量，亦可查询指定交易业务账户下指定订单的最大可改成的数量。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required int32 orderType = 2; //订单类型，参见 Trd_Common.OrderType 的枚举定义
    	required string code = 3; //代码，港股必须是5位数字，A 股必须是6位数字，美股没限制
    	required double price = 4; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）。如果是竞价、市价单，请也填入一个当前价格，服务器才好计算
    	optional uint64 orderID = 5; //订单号，新下订单不需要，如果是修改订单就需要把原订单号带上才行，因为改单的最大买卖数量会包含原订单数量。
    	//为保证与下单的价格同步，也提供调整价格选项，以下2个为调整价格使用，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 6; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整
    	optional double adjustSideAndLimit = 7; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional int32 secMarket = 8; //证券所属市场，参见 TrdSecMarket 的枚举定义
    	optional string orderIDEx = 9; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
    	optional int32 session = 10; //美股订单时段, 参见Common.Session的枚举定义
    	optional uint64 positionID = 11; //持仓ID，日本券商查询平仓数量时使用
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
20  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   订单类型结构参见 [OrderType](./trade_trade.md#4181)
>     
> *   交易证券市场结构参见 [TrdSecMarket](./trade_trade.md#5084)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.MaxTrdQtys maxTrdQtys = 2; //最大可交易数量结构
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   最大可交易数量结构参见 [MaxTrdQtys](./trade_trade.md#8065)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetMaxTrdQtys(){
        const { RetType } = Common
        const { TrdEnv, OrderType, TrdMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.GetAccList({
                    c2s: {
                        userID: 0,
                    },
                }).then((res) => {
                    let { retType, s2c: { accList }  } = res
                    if(retType == RetType.RetType_Succeed){
                        let acc = accList.filter((item)=>{ 
                            return item.trdEnv == TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})
                        })[0]; // 样例取第一个香港市场虚拟环境账户
    
                        const req = {
                            c2s: {
                                header: {
                                    trdEnv: acc.trdEnv,
                                    accID: acc.accID,
                                    trdMarket: TrdMarket.TrdMarket_HK,
                                },
                                orderType: OrderType.OrderType_Normal,
                                code: "00700", // 指定账号对应市场中的代码
                                price: 100,
                                secMarket: TrdSecMarket.TrdSecMarket_HK,
                            },
                        };
    
                        websocket.GetMaxTrdQtys(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetMaxTrdQtys: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
    
                    }
                })
                .catch((error) => {
                    console.log("GetAccList error:", error);
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

*   **Output**

    GetMaxTrdQtys: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6684972",
        "trdMarket": 1
      },
      "maxTrdQtys": {
        "maxCashBuy": 1300,
        "maxCashAndMarginBuy": 1300,
        "maxPositionSell": 1900
      }
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

*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询最大可买可卖接口

提示

*   现金业务账户不支持交易衍生品，因此不支持通过现金业务账户查询期权的最大可买可卖。
*   期货的最大可买，需自行计算，公式：floor(最大购买力/买 1 张合约所带来的初始保证金变动)。其中，最大购买力来自[查询账户资金](./trade_get-funds.md)
    ，买 1 张合约所带来的初始保证金变动来自本接口。

← [查询账户资金](./trade_get-funds.md) [查询持仓](./trade_get-position-list.md)
 →

[查询最大可买可卖](./trade_get-max-trd-qtys.md)