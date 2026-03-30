[#](./trade_modify-order.md#7408)
 改单撤单
===============================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`modify_order(modify_order_op, order_id, qty, price, adjust_limit=0, trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, aux_price=None, trail_type=None, trail_value=None, trail_spread=None)`

*   **介绍**
    
    修改订单的价格和数量、撤单、操作订单的失效和生效、删除订单等。  
    如果是 A 股通市场，将不支持改单。可撤单。删除订单是 OpenD 本地操作。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | modify\_order\_op | [ModifyOrderOp](./trade_trade.md#2969) | 改单操作类型 |
    | order\_id | str | 订单号 |
    | qty | float | 订单改单后的数量<br>(ℹ️ 期权和期货单位是“张”)  <br>精确到小数点后 0 位，超出部分会被舍弃 |
    | price | float | 订单改单后的价格<br>(ℹ️ 证券账户精确到小数点后 3 位，超出部分会被舍弃)  <br>期货账户精确到小数点后 9 位，超出部分会被舍弃 |
    | adjust\_limit | float | 价格微调幅度<br>(ℹ️ OpenD 会对传入价格自动调整到合法价位上（期货忽略此参数）)<br><br>*   正数代表向上调整，负数代表向下调整<br>*   例如：0.015 代表向上调整且幅度不超过 1.5%；-0.01 代表向下调整且幅度不超过 1%。默认 0 表示不调整 |
    | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。)<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。)<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | aux\_price | float | 触发价格<br>(ℹ️ *   当订单是**止损市价单**、**止损限价单**、**触及限价单（止盈）**、**触及市价单（止盈）** 时，aux\_price 为必传参数)<br>*   证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超过部分四舍五入 |
    | trail\_type | [TrailType](./trade_trade.md#5644) | 跟踪类型<br>(ℹ️ 当订单是**跟踪止损市价单**、**跟踪止损限价单时**，trail\_type 为必传参数) |
    | trail\_value | float | 跟踪金额/百分比<br>(ℹ️ *   当订单是**跟踪止损市价单**、**跟踪止损限价单**时，trail\_value 为必传参数)<br>*   当跟踪类型为比例时，该字段为百分比字段，传入 20 实际对应 20%<br>*   当跟踪类型为金额时，证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超过部分四舍五入<br>*   当跟踪类型为比例时，精确到小数点后 2 位，超过部分四舍五入 |
    | trail\_spread | float | 指定价差<br>(ℹ️ *   当订单是**跟踪止损限价单**时，trail\_spread 为必传参数)<br>*   证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超过部分四舍五入 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回改单信息 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   改单信息格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
        | order\_id | str | 订单号 |
        
*   **Example**
    

    from futu import *
    pwd_unlock = '123456'
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.unlock_trade(pwd_unlock)  # 若使用真实账户改单/撤单，需先对账户进行解锁。此处示例为模拟账户撤单，也可省略解锁。
    if ret == RET_OK:
        order_id = "8851102695472794941"
        ret, data = trd_ctx.modify_order(ModifyOrderOp.CANCEL, order_id, 0, 0)
        if ret == RET_OK:
            print(data)
            print(data['order_id'][0])  # 获取改单的订单号
            print(data['order_id'].values.tolist())  # 转为 list
        else:
            print('modify_order error: ', data)
    else:
        print('unlock_trade failed: ', data)
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
11  
12  
13  
14  
15  
16  

*   **Output**

        trd_env             order_id
    0    REAL      8851102695472794941
    8851102695472794941
    ['8851102695472794941']
    

1  
2  
3  
4  

`cancel_all_order(trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, trdmarket=TrdMarket.NONE)`

*   **介绍**
    
    撤消全部订单。模拟交易以及 A 股通账户暂不支持全部撤单。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br>(ℹ️ 当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准)  <br>当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。)<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | trdmarket | [TrdMarket](./trade_trade.md#719) | 指定交易市场<br>(ℹ️ 撤销指定账户指定市场的订单)  <br>默认状态时，撤销指定账户全部市场的订单 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | str | 接口调用结果。ret == RET\_OK 代表接口调用正常，ret != RET\_OK 代表接口调用失败 |
    | data | str | 当 ret == RET\_OK，返回"success" |
    | 当 ret != RET\_OK，返回错误描述 |
    
    *   全部撤单信息格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
        | order\_id | str | 订单号 |
        
*   **Example**
    

    from futu import *
    pwd_unlock = '123456'
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.unlock_trade(pwd_unlock)  # 若使用真实账户改单/撤单，需先对账户进行解锁。此处示例为模拟账户全部撤单，也可省略解锁。
    if ret == RET_OK:
        ret, data = trd_ctx.cancel_all_order()
        if ret == RET_OK:
            print(data)
        else:
            print('cancel_all_order error: ', data)
    else:
        print('unlock_trade failed: ', data)
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
11  
12  
13  

*   **Output**

    success
    

1  

[#](./trade_modify-order.md#5781)
 Trd\_ModifyOrder.proto
-------------------------------------------------------------------------------------------------

*   **介绍**
    
    修改订单的价格和数量、撤单、操作订单的失效和生效、删除订单等。  
    如果是 A 股通市场，将不支持改单。可撤单。删除订单是本地操作。
    
*   **参数**
    

    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required uint64 orderID = 3; //订单号，forAll 为 true 时，传0
    	required int32 modifyOrderOp = 4; //修改操作类型，参见 Trd_Common.ModifyOrderOp 的枚举定义
    	optional bool forAll = 5; //是否对此业务账户的全部订单操作，true：对全部订单，false：对单个订单，不传默认为对单个订单。批量操作仅支持全部撤单，不支持全部生效、全部失效、全部删除。
    	
    	//下面的字段仅针对单个订单，且 modifyOrderOp 为 ModifyOrderOp_Normal 有效
    	optional double qty = 8; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double price = 9; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 10; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 11; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional double auxPrice = 12; //触发价格
    	optional int32 trailType = 13; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 14; //跟踪金额/百分比
    	optional double trailSpread = 15; //指定价差
        optional string orderIDEx = 16; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
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
21  
22  
23  
24  
25  

> *   请求包标识结构参见 [PacketID](./ftapi_common.md#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   修改操作枚举参见 [ModifyOrderOp](./trade_trade.md#2969)
>     
> *   跟踪类型参见 [TrailType](./trade_trade.md#5644)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //服务器订单id	
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
16  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    2205
    

`uint ModifyOrder(TrdModifyOrder.Request req);`  
`virtual void OnReply_ModifyOrder(FTAPI_Conn client, uint nSerialNo, TrdModifyOrder.Response rsp);`

*   **介绍**
    
    修改订单的价格和数量、撤单、操作订单的失效和生效、删除订单等。  
    如果是 A 股通市场，将不支持改单。可撤单。删除订单是本地操作。
    
*   **参数**
    

    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required uint64 orderID = 3; //订单号，forAll 为 true 时，传0
    	required int32 modifyOrderOp = 4; //修改操作类型，参见 Trd_Common.ModifyOrderOp 的枚举定义
    	optional bool forAll = 5; //是否对此业务账户的全部订单操作，true：对全部订单，false：对单个订单，不传默认为对单个订单。批量操作仅支持全部撤单，不支持全部生效、全部失效、全部删除。
    	
    	//下面的字段仅针对单个订单，且 modifyOrderOp 为 ModifyOrderOp_Normal 有效
    	optional double qty = 8; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double price = 9; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 10; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 11; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional double auxPrice = 12; //触发价格
    	optional int32 trailType = 13; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 14; //跟踪金额/百分比
    	optional double trailSpread = 15; //指定价差
        optional string orderIDEx = 16; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
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
21  
22  
23  
24  
25  

> *   请求包标识结构参见 [PacketID](./ftapi_common.md#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   修改操作枚举参见 [ModifyOrderOp](./trade_trade.md#2969)
>     
> *   跟踪类型参见 [TrailType](./trade_trade.md#5644)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //服务器订单id	
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
16  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
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
            TrdModifyOrder.C2S c2s = TrdModifyOrder.C2S.CreateBuilder()
                    .SetPacketID(trd.NextPacketID())
                    .SetHeader(header)
                    .SetOrderID(1167729267926401492L)
                    .SetModifyOrderOp((int)TrdCommon.ModifyOrderOp.ModifyOrderOp_Normal)
                    .SetPrice(100)
                .Build();
            TrdModifyOrder.Request req = TrdModifyOrder.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.ModifyOrder(req);
            Console.Write("Send TrdModifyOrder: {0}\n", seqNo);
        }
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_ModifyOrder(FTAPI_Conn client, uint nSerialNo, TrdModifyOrder.Response rsp)
        {
            Console.Write("Reply: TrdModifyOrder: {0}\n", nSerialNo);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6827792005034419980
    Send TrdModifyOrder: 3
    Reply: TrdModifyOrder: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int modifyOrder(TrdModifyOrder.Request req);`  
`void onReply_ModifyOrder(FTAPI_Conn client, int nSerialNo, TrdModifyOrder.Response rsp);`

*   **介绍**
    
    修改订单的价格和数量、撤单、操作订单的失效和生效、删除订单等。  
    如果是 A 股通市场，将不支持改单。可撤单。删除订单是本地操作。
    
*   **参数**
    

    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required uint64 orderID = 3; //订单号，forAll 为 true 时，传0
    	required int32 modifyOrderOp = 4; //修改操作类型，参见 Trd_Common.ModifyOrderOp 的枚举定义
    	optional bool forAll = 5; //是否对此业务账户的全部订单操作，true：对全部订单，false：对单个订单，不传默认为对单个订单。批量操作仅支持全部撤单，不支持全部生效、全部失效、全部删除。
    	
    	//下面的字段仅针对单个订单，且 modifyOrderOp 为 ModifyOrderOp_Normal 有效
    	optional double qty = 8; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double price = 9; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 10; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 11; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional double auxPrice = 12; //触发价格
    	optional int32 trailType = 13; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 14; //跟踪金额/百分比
    	optional double trailSpread = 15; //指定价差
        optional string orderIDEx = 16; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
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
21  
22  
23  
24  
25  

> *   请求包标识结构参见 [PacketID](./ftapi_common.md#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   修改操作枚举参见 [ModifyOrderOp](./trade_trade.md#2969)
>     
> *   跟踪类型参见 [TrailType](./trade_trade.md#5644)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //服务器订单id	
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
16  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
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
            TrdModifyOrder.C2S c2s = TrdModifyOrder.C2S.newBuilder()
                    .setPacketID(trd.nextPacketID())
                    .setHeader(header)
                    .setOrderID(1167729267926401492L)
                    .setModifyOrderOp(TrdCommon.ModifyOrderOp.ModifyOrderOp_Normal_VALUE)
                    .setPrice(100)
                .build();
            TrdModifyOrder.Request req = TrdModifyOrder.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.modifyOrder(req);
            System.out.printf("Send TrdModifyOrder: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_ModifyOrder(FTAPI_Conn client, int nSerialNo, TrdModifyOrder.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdModifyOrder failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdModifyOrder: %s\n", json);
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

    Send TrdModifyOrder: 2
    Receive TrdModifyOrder: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 0,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "orderID": "5185260464676654543"
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

`Futu::u32_t ModifyOrder(const Trd_ModifyOrder::Request &stReq);`  
`virtual void OnReply_ModifyOrder(Futu::u32_t nSerialNo, const Trd_ModifyOrder::Response &stRsp) = 0;`

*   **介绍**
    
    修改订单的价格和数量、撤单、操作订单的失效和生效、删除订单等。  
    如果是 A 股通市场，将不支持改单。可撤单。删除订单是本地操作。
    
*   **参数**
    

    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required uint64 orderID = 3; //订单号，forAll 为 true 时，传0
    	required int32 modifyOrderOp = 4; //修改操作类型，参见 Trd_Common.ModifyOrderOp 的枚举定义
    	optional bool forAll = 5; //是否对此业务账户的全部订单操作，true：对全部订单，false：对单个订单，不传默认为对单个订单。批量操作仅支持全部撤单，不支持全部生效、全部失效、全部删除。
    	
    	//下面的字段仅针对单个订单，且 modifyOrderOp 为 ModifyOrderOp_Normal 有效
    	optional double qty = 8; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double price = 9; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 10; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 11; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional double auxPrice = 12; //触发价格
    	optional int32 trailType = 13; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 14; //跟踪金额/百分比
    	optional double trailSpread = 15; //指定价差
        optional string orderIDEx = 16; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
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
21  
22  
23  
24  
25  

> *   请求包标识结构参见 [PacketID](./ftapi_common.md#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   修改操作枚举参见 [ModifyOrderOp](./trade_trade.md#2969)
>     
> *   跟踪类型参见 [TrailType](./trade_trade.md#5644)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //服务器订单id	
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
16  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
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
    		Trd_ModifyOrder::Request req;
    		Trd_ModifyOrder::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    		c2s->set_orderid(3964270595789502688L);
    		c2s->set_modifyorderop(2);
    
            m_ModifyOrderSerialNo = m_pTrdApi->ModifyOrder(req);
            cout << "Request ModifyOrder SerialNo: " << m_ModifyOrderSerialNo << endl;
    	}
    
    	virtual void OnReply_ModifyOrder(Futu::u32_t nSerialNo, const Trd_ModifyOrder::Response &stRsp){
            if(nSerialNo == m_ModifyOrderSerialNo)
            {
                cout << "OnReply_ModifyOrder SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_ModifyOrderSerialNo;
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

*   **Output**

    connect
    Request ModifyOrder SerialNo: 4
    OnReply_ModifyOrder SerialNo: 4
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
      "orderID": "3964270595789502688"
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

`ModifyOrder(req);`

*   **介绍**
    
    修改订单的价格和数量、撤单、操作订单的失效和生效、删除订单等。  
    如果是 A 股通市场，将不支持改单。可撤单。删除订单是本地操作。
    
*   **参数**
    

    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required uint64 orderID = 3; //订单号，forAll 为 true 时，传0
    	required int32 modifyOrderOp = 4; //修改操作类型，参见 Trd_Common.ModifyOrderOp 的枚举定义
    	optional bool forAll = 5; //是否对此业务账户的全部订单操作，true：对全部订单，false：对单个订单，不传默认为对单个订单。批量操作仅支持全部撤单，不支持全部生效、全部失效、全部删除。
    	
    	//下面的字段仅针对单个订单，且 modifyOrderOp 为 ModifyOrderOp_Normal 有效
    	optional double qty = 8; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double price = 9; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 10; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 11; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional double auxPrice = 12; //触发价格
    	optional int32 trailType = 13; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 14; //跟踪金额/百分比
    	optional double trailSpread = 15; //指定价差
        optional string orderIDEx = 16; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
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
21  
22  
23  
24  
25  

> *   请求包标识结构参见 [PacketID](./ftapi_common.md#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   修改操作枚举参见 [ModifyOrderOp](./trade_trade.md#2969)
>     
> *   跟踪类型参见 [TrailType](./trade_trade.md#5644)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //服务器订单id	
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
16  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdModifyOrder(){
        const { RetType, PacketID } = Common
        const { TrdEnv, TrdSide, OrderType, SecurityFirm, TrdMarket, TrdSecMarket, ModifyOrderOp } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        let tradeSerialNo = 0;
        websocket.onlogin = async ()=>{
            try{
                let { errCode, retMsg, retType } = await websocket.UnlockTrade({
                    c2s: {
                        unlock: true,
                        securityFirm: SecurityFirm.SecurityFirm_FutuSecurities,
                        pwdMD5: "d0970714757783e6cf17b26fb8e2298f", // 设置为自己账号的交易密码MD5
                    },
                });
                if(retType == RetType.RetType_Succeed && errCode == 0) { // 解锁交易成功
                    let { errCode, retMsg, retType, s2c: { accList } } = await websocket.GetAccList({
                        c2s: {
                            userID: 0,
                        },
                    });
                    if(retType == RetType.RetType_Succeed && errCode == 0) { // 获取账户成功
                        let acc = accList.filter((item)=>{ 
                            return item.trdEnv == TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})
                        })[0]; // 样例取第一个香港市场虚拟环境账户
                        
                        let { errCode, retMsg, retType, s2c : { orderID } } = await websocket.PlaceOrder({
                            c2s: {
                                packetID:{
                                    connID: websocket.getConnID(),
                                    serialNo: tradeSerialNo++,
                                },
                                header: {
                                    trdEnv: acc.trdEnv,
                                    accID: acc.accID,
                                    trdMarket: TrdMarket.TrdMarket_HK,
                                },
                                trdSide: TrdSide.TrdSide_Buy,
                                orderType: OrderType.OrderType_Normal,
                                code: "00700",
                                qty: 100,
                                price: 150,
                                secMarket: TrdSecMarket.TrdSecMarket_HK,
                            },
                        }); 
                        if(retType == RetType.RetType_Succeed && errCode == 0){
                            const req = {
                                c2s: {
                                    packetID:{
                                        connID: websocket.getConnID(),
                                        serialNo: tradeSerialNo++,
                                    },
                                    header: {
                                        trdEnv: acc.trdEnv,
                                        accID: acc.accID,
                                        trdMarket: TrdMarket.TrdMarket_HK,
                                    },
                                    orderID: orderID,
                                    modifyOrderOp: ModifyOrderOp.ModifyOrderOp_Normal,
                                    qty: 200,
                                    price: 100,
                                },
                            };
                            let { errCode, retMsg, retType, s2c } = await websocket.ModifyOrder(req);
                            console.log("ModifyOrder: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                            if(retType == RetType.RetType_Succeed){
                                let data = beautify(JSON.stringify(s2c), {
                                    indent_size: 2,
                                    space_in_empty_paren: true,
                                });
                                console.log(data);
                            }
                        }
                    }
                }
            }
            catch(err){
                console.log(err)
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

*   **Output**

    ModifyOrder: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6684972",
        "trdMarket": 1
      },
      "orderID": "5870570404030790740"
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

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 20 次改单撤单接口，且连续两次请求的间隔不可小于 0.04 秒。
*   真实账户调用改单撤单接口前，需要先进行 [解锁](./trade_unlock.md)
    ；模拟账户无需解锁。

提示

*   若执行 **修改订单** 操作，各类订单类型对应的必传参数，可 [点击这里](./qa_trade.md#689)
     了解更多。
*   如果希望执行 **改单操作** 去 **修改订单数量**，此接口入参的订单数量 **qty**，应该等于期望成交的总数量。  
    举例： 一笔订单数量是 N 股，已部分成交 n 股。对于暂未成交的 (N-n) 股，如果您希望撤掉其中的 x 股，**modify\_order\_op** 应选择 NORMAL，**qty** 应传 (N-x)。 ![order_quantity](https://openapi.futunn.com/futu-api-doc/assets/img/order_quantity_cn.ef6e3011.png)
*   如果希望执行 **撤单操作**，此接口入参的 **modify\_order\_op** 应该选择 CANCEL。  
    举例： 一笔订单数量是 N 股，已部分成交 n 股。如果希望将未成交的 (N-n) 股全部撤掉，modify\_order\_op 应选择 CANCEL，此时 qty 和 price 的入参会被忽略。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`modify_order(modify_order_op, order_id, qty, price, adjust_limit=0, trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, aux_price=None, trail_type=None, trail_value=None, trail_spread=None)`

*   **介绍**
    
    修改订单的价格和数量、撤单、操作订单的失效和生效、删除订单等。  
    如果是 A 股通市场，将不支持改单。可撤单。删除订单是 OpenD 本地操作。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | modify\_order\_op | [ModifyOrderOp](./trade_trade.md#2969) | 改单操作类型 |
    | order\_id | str | 订单号 |
    | qty | float | 订单改单后的数量<br>(ℹ️ 期权和期货单位是“张”)  <br>精确到小数点后 0 位，超出部分会被舍弃 |
    | price | float | 订单改单后的价格<br>(ℹ️ 证券账户精确到小数点后 3 位，超出部分会被舍弃)  <br>期货账户精确到小数点后 9 位，超出部分会被舍弃 |
    | adjust\_limit | float | 价格微调幅度<br>(ℹ️ OpenD 会对传入价格自动调整到合法价位上（期货忽略此参数）)<br><br>*   正数代表向上调整，负数代表向下调整<br>*   例如：0.015 代表向上调整且幅度不超过 1.5%；-0.01 代表向下调整且幅度不超过 1%。默认 0 表示不调整 |
    | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。)<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。)<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | aux\_price | float | 触发价格<br>(ℹ️ *   当订单是**止损市价单**、**止损限价单**、**触及限价单（止盈）**、**触及市价单（止盈）** 时，aux\_price 为必传参数)<br>*   证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超过部分四舍五入 |
    | trail\_type | [TrailType](./trade_trade.md#5644) | 跟踪类型<br>(ℹ️ 当订单是**跟踪止损市价单**、**跟踪止损限价单时**，trail\_type 为必传参数) |
    | trail\_value | float | 跟踪金额/百分比<br>(ℹ️ *   当订单是**跟踪止损市价单**、**跟踪止损限价单**时，trail\_value 为必传参数)<br>*   当跟踪类型为比例时，该字段为百分比字段，传入 20 实际对应 20%<br>*   当跟踪类型为金额时，证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超过部分四舍五入<br>*   当跟踪类型为比例时，精确到小数点后 2 位，超过部分四舍五入 |
    | trail\_spread | float | 指定价差<br>(ℹ️ *   当订单是**跟踪止损限价单**时，trail\_spread 为必传参数)<br>*   证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超过部分四舍五入 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回改单信息 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   改单信息格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
        | order\_id | str | 订单号 |
        
*   **Example**
    

    from moomoo import *
    pwd_unlock = '123456'
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    ret, data = trd_ctx.unlock_trade(pwd_unlock)  # 若使用真实账户改单/撤单，需先对账户进行解锁。此处示例为模拟账户撤单，也可省略解锁。
    if ret == RET_OK:
        order_id = "8851102695472794941"
        ret, data = trd_ctx.modify_order(ModifyOrderOp.CANCEL, order_id, 0, 0)
        if ret == RET_OK:
            print(data)
            print(data['order_id'][0])  # 获取改单的订单号
            print(data['order_id'].values.tolist())  # 转为 list
        else:
            print('modify_order error: ', data)
    else:
        print('unlock_trade failed: ', data)
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
11  
12  
13  
14  
15  
16  

*   **Output**

        trd_env             order_id
    0    REAL      8851102695472794941
    8851102695472794941
    ['8851102695472794941']
    

1  
2  
3  
4  

`cancel_all_order(trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, trdmarket=TrdMarket.NONE)`

*   **介绍**
    
    撤消全部订单。模拟交易以及 A 股通账户暂不支持全部撤单。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br>(ℹ️ 当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准)  <br>当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。)<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | trdmarket | [TrdMarket](./trade_trade.md#719) | 指定交易市场<br>(ℹ️ 撤销指定账户指定市场的订单)  <br>默认状态时，撤销指定账户全部市场的订单 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | str | 接口调用结果。ret == RET\_OK 代表接口调用正常，ret != RET\_OK 代表接口调用失败 |
    | data | str | 当 ret == RET\_OK，返回"success" |
    | 当 ret != RET\_OK，返回错误描述 |
    
    *   全部撤单信息格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
        | order\_id | str | 订单号 |
        
*   **Example**
    

    from moomoo import *
    pwd_unlock = '123456'
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    ret, data = trd_ctx.unlock_trade(pwd_unlock)  # 若使用真实账户改单/撤单，需先对账户进行解锁。此处示例为模拟账户全部撤单，也可省略解锁。
    if ret == RET_OK:
        ret, data = trd_ctx.cancel_all_order()
        if ret == RET_OK:
            print(data)
        else:
            print('cancel_all_order error: ', data)
    else:
        print('unlock_trade failed: ', data)
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
11  
12  
13  

*   **Output**

    success
    

1  

[#](./trade_modify-order.md#5781-2)
 Trd\_ModifyOrder.proto
---------------------------------------------------------------------------------------------------

*   **介绍**
    
    修改订单的价格和数量、撤单、操作订单的失效和生效、删除订单等。  
    如果是 A 股通市场，将不支持改单。可撤单。删除订单是本地操作。
    
*   **参数**
    

    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required uint64 orderID = 3; //订单号，forAll 为 true 时，传0
    	required int32 modifyOrderOp = 4; //修改操作类型，参见 Trd_Common.ModifyOrderOp 的枚举定义
    	optional bool forAll = 5; //是否对此业务账户的全部订单操作，true：对全部订单，false：对单个订单，不传默认为对单个订单。批量操作仅支持全部撤单，不支持全部生效、全部失效、全部删除。
    	
    	//下面的字段仅针对单个订单，且 modifyOrderOp 为 ModifyOrderOp_Normal 有效
    	optional double qty = 8; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double price = 9; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 10; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 11; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional double auxPrice = 12; //触发价格
    	optional int32 trailType = 13; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 14; //跟踪金额/百分比
    	optional double trailSpread = 15; //指定价差
        optional string orderIDEx = 16; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
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
21  
22  
23  
24  
25  

> *   请求包标识结构参见 [PacketID](./ftapi_common.md#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   修改操作枚举参见 [ModifyOrderOp](./trade_trade.md#2969)
>     
> *   跟踪类型参见 [TrailType](./trade_trade.md#5644)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //服务器订单id	
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
16  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    2205
    

`uint ModifyOrder(TrdModifyOrder.Request req);`  
`virtual void OnReply_ModifyOrder(MMAPI_Conn client, uint nSerialNo, TrdModifyOrder.Response rsp);`

*   **介绍**
    
    修改订单的价格和数量、撤单、操作订单的失效和生效、删除订单等。  
    如果是 A 股通市场，将不支持改单。可撤单。删除订单是本地操作。
    
*   **参数**
    

    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required uint64 orderID = 3; //订单号，forAll 为 true 时，传0
    	required int32 modifyOrderOp = 4; //修改操作类型，参见 Trd_Common.ModifyOrderOp 的枚举定义
    	optional bool forAll = 5; //是否对此业务账户的全部订单操作，true：对全部订单，false：对单个订单，不传默认为对单个订单。批量操作仅支持全部撤单，不支持全部生效、全部失效、全部删除。
    	
    	//下面的字段仅针对单个订单，且 modifyOrderOp 为 ModifyOrderOp_Normal 有效
    	optional double qty = 8; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double price = 9; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 10; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 11; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional double auxPrice = 12; //触发价格
    	optional int32 trailType = 13; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 14; //跟踪金额/百分比
    	optional double trailSpread = 15; //指定价差
        optional string orderIDEx = 16; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
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
21  
22  
23  
24  
25  

> *   请求包标识结构参见 [PacketID](./ftapi_common.md#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   修改操作枚举参见 [ModifyOrderOp](./trade_trade.md#2969)
>     
> *   跟踪类型参见 [TrailType](./trade_trade.md#5644)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //服务器订单id	
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
16  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
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
            TrdModifyOrder.C2S c2s = TrdModifyOrder.C2S.CreateBuilder()
                    .SetPacketID(trd.NextPacketID())
                    .SetHeader(header)
                    .SetOrderID(1167729267926401492L)
                    .SetModifyOrderOp((int)TrdCommon.ModifyOrderOp.ModifyOrderOp_Normal)
                    .SetPrice(100)
                .Build();
            TrdModifyOrder.Request req = TrdModifyOrder.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.ModifyOrder(req);
            Console.Write("Send TrdModifyOrder: {0}\n", seqNo);
        }
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_ModifyOrder(MMAPI_Conn client, uint nSerialNo, TrdModifyOrder.Response rsp)
        {
            Console.Write("Reply: TrdModifyOrder: {0}\n", nSerialNo);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6827792005034419980
    Send TrdModifyOrder: 3
    Reply: TrdModifyOrder: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int modifyOrder(TrdModifyOrder.Request req);`  
`void onReply_ModifyOrder(MMAPI_Conn client, int nSerialNo, TrdModifyOrder.Response rsp);`

*   **介绍**
    
    修改订单的价格和数量、撤单、操作订单的失效和生效、删除订单等。  
    如果是 A 股通市场，将不支持改单。可撤单。删除订单是本地操作。
    
*   **参数**
    

    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required uint64 orderID = 3; //订单号，forAll 为 true 时，传0
    	required int32 modifyOrderOp = 4; //修改操作类型，参见 Trd_Common.ModifyOrderOp 的枚举定义
    	optional bool forAll = 5; //是否对此业务账户的全部订单操作，true：对全部订单，false：对单个订单，不传默认为对单个订单。批量操作仅支持全部撤单，不支持全部生效、全部失效、全部删除。
    	
    	//下面的字段仅针对单个订单，且 modifyOrderOp 为 ModifyOrderOp_Normal 有效
    	optional double qty = 8; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double price = 9; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 10; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 11; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional double auxPrice = 12; //触发价格
    	optional int32 trailType = 13; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 14; //跟踪金额/百分比
    	optional double trailSpread = 15; //指定价差
        optional string orderIDEx = 16; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
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
21  
22  
23  
24  
25  

> *   请求包标识结构参见 [PacketID](./ftapi_common.md#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   修改操作枚举参见 [ModifyOrderOp](./trade_trade.md#2969)
>     
> *   跟踪类型参见 [TrailType](./trade_trade.md#5644)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //服务器订单id	
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
16  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
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
            TrdModifyOrder.C2S c2s = TrdModifyOrder.C2S.newBuilder()
                    .setPacketID(trd.nextPacketID())
                    .setHeader(header)
                    .setOrderID(1167729267926401492L)
                    .setModifyOrderOp(TrdCommon.ModifyOrderOp.ModifyOrderOp_Normal_VALUE)
                    .setPrice(100)
                .build();
            TrdModifyOrder.Request req = TrdModifyOrder.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.modifyOrder(req);
            System.out.printf("Send TrdModifyOrder: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_ModifyOrder(MMAPI_Conn client, int nSerialNo, TrdModifyOrder.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdModifyOrder failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdModifyOrder: %s\n", json);
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

    Send TrdModifyOrder: 2
    Receive TrdModifyOrder: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 0,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "orderID": "5185260464676654543"
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

`moomoo::u32_t ModifyOrder(const Trd_ModifyOrder::Request &stReq);`  
`virtual void OnReply_ModifyOrder(moomoo::u32_t nSerialNo, const Trd_ModifyOrder::Response &stRsp) = 0;`

*   **介绍**
    
    修改订单的价格和数量、撤单、操作订单的失效和生效、删除订单等。  
    如果是 A 股通市场，将不支持改单。可撤单。删除订单是本地操作。
    
*   **参数**
    

    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required uint64 orderID = 3; //订单号，forAll 为 true 时，传0
    	required int32 modifyOrderOp = 4; //修改操作类型，参见 Trd_Common.ModifyOrderOp 的枚举定义
    	optional bool forAll = 5; //是否对此业务账户的全部订单操作，true：对全部订单，false：对单个订单，不传默认为对单个订单。批量操作仅支持全部撤单，不支持全部生效、全部失效、全部删除。
    	
    	//下面的字段仅针对单个订单，且 modifyOrderOp 为 ModifyOrderOp_Normal 有效
    	optional double qty = 8; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double price = 9; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 10; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 11; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional double auxPrice = 12; //触发价格
    	optional int32 trailType = 13; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 14; //跟踪金额/百分比
    	optional double trailSpread = 15; //指定价差
        optional string orderIDEx = 16; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
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
21  
22  
23  
24  
25  

> *   请求包标识结构参见 [PacketID](./ftapi_common.md#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   修改操作枚举参见 [ModifyOrderOp](./trade_trade.md#2969)
>     
> *   跟踪类型参见 [TrailType](./trade_trade.md#5644)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //服务器订单id	
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
16  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
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
    		Trd_ModifyOrder::Request req;
    		Trd_ModifyOrder::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    		c2s->set_orderid(3964270595789502688L);
    		c2s->set_modifyorderop(2);
    
            m_ModifyOrderSerialNo = m_pTrdApi->ModifyOrder(req);
            cout << "Request ModifyOrder SerialNo: " << m_ModifyOrderSerialNo << endl;
    	}
    
    	virtual void OnReply_ModifyOrder(moomoo::u32_t nSerialNo, const Trd_ModifyOrder::Response &stRsp){
            if(nSerialNo == m_ModifyOrderSerialNo)
            {
                cout << "OnReply_ModifyOrder SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Trd *m_pTrdApi;
    
        moomoo::u32_t m_ModifyOrderSerialNo;
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

*   **Output**

    connect
    Request ModifyOrder SerialNo: 4
    OnReply_ModifyOrder SerialNo: 4
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
      "orderID": "3964270595789502688"
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

`ModifyOrder(req);`

*   **介绍**
    
    修改订单的价格和数量、撤单、操作订单的失效和生效、删除订单等。  
    如果是 A 股通市场，将不支持改单。可撤单。删除订单是本地操作。
    
*   **参数**
    

    message C2S
    {
    	required Common.PacketID packetID = 1; //交易写操作防重放攻击
    	required Trd_Common.TrdHeader header = 2; //交易公共参数头
    	required uint64 orderID = 3; //订单号，forAll 为 true 时，传0
    	required int32 modifyOrderOp = 4; //修改操作类型，参见 Trd_Common.ModifyOrderOp 的枚举定义
    	optional bool forAll = 5; //是否对此业务账户的全部订单操作，true：对全部订单，false：对单个订单，不传默认为对单个订单。批量操作仅支持全部撤单，不支持全部生效、全部失效、全部删除。
    	
    	//下面的字段仅针对单个订单，且 modifyOrderOp 为 ModifyOrderOp_Normal 有效
    	optional double qty = 8; //数量，期权单位是"张"（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double price = 9; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    	//以下为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    	optional bool adjustPrice = 10; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    	optional double adjustSideAndLimit = 11; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    	optional double auxPrice = 12; //触发价格
    	optional int32 trailType = 13; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    	optional double trailValue = 14; //跟踪金额/百分比
    	optional double trailSpread = 15; //指定价差
        optional string orderIDEx = 16; //表示服务器订单id，可以用来代替orderID，和orderID二选一	
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
21  
22  
23  
24  
25  

> *   请求包标识结构参见 [PacketID](./ftapi_common.md#4068)
>     
> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   修改操作枚举参见 [ModifyOrderOp](./trade_trade.md#2969)
>     
> *   跟踪类型参见 [TrailType](./trade_trade.md#5644)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required uint64 orderID = 2; //订单号
        optional string orderIDEx = 3; //服务器订单id	
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
16  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function TrdModifyOrder(){
        const { RetType, PacketID } = Common
        const { TrdEnv, TrdSide, OrderType, SecurityFirm, TrdMarket, TrdSecMarket, ModifyOrderOp } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        let tradeSerialNo = 0;
        websocket.onlogin = async ()=>{
            try{
                let { errCode, retMsg, retType } = await websocket.UnlockTrade({
                    c2s: {
                        unlock: true,
                        securityFirm: SecurityFirm.SecurityFirm_FutuSecurities,
                        pwdMD5: "d0970714757783e6cf17b26fb8e2298f", // 设置为自己账号的交易密码MD5
                    },
                });
                if(retType == RetType.RetType_Succeed && errCode == 0) { // 解锁交易成功
                    let { errCode, retMsg, retType, s2c: { accList } } = await websocket.GetAccList({
                        c2s: {
                            userID: 0,
                        },
                    });
                    if(retType == RetType.RetType_Succeed && errCode == 0) { // 获取账户成功
                        let acc = accList.filter((item)=>{ 
                            return item.trdEnv == TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})
                        })[0]; // 样例取第一个香港市场虚拟环境账户
                        
                        let { errCode, retMsg, retType, s2c : { orderID } } = await websocket.PlaceOrder({
                            c2s: {
                                packetID:{
                                    connID: websocket.getConnID(),
                                    serialNo: tradeSerialNo++,
                                },
                                header: {
                                    trdEnv: acc.trdEnv,
                                    accID: acc.accID,
                                    trdMarket: TrdMarket.TrdMarket_HK,
                                },
                                trdSide: TrdSide.TrdSide_Buy,
                                orderType: OrderType.OrderType_Normal,
                                code: "00700",
                                qty: 100,
                                price: 150,
                                secMarket: TrdSecMarket.TrdSecMarket_HK,
                            },
                        }); 
                        if(retType == RetType.RetType_Succeed && errCode == 0){
                            const req = {
                                c2s: {
                                    packetID:{
                                        connID: websocket.getConnID(),
                                        serialNo: tradeSerialNo++,
                                    },
                                    header: {
                                        trdEnv: acc.trdEnv,
                                        accID: acc.accID,
                                        trdMarket: TrdMarket.TrdMarket_HK,
                                    },
                                    orderID: orderID,
                                    modifyOrderOp: ModifyOrderOp.ModifyOrderOp_Normal,
                                    qty: 200,
                                    price: 100,
                                },
                            };
                            let { errCode, retMsg, retType, s2c } = await websocket.ModifyOrder(req);
                            console.log("ModifyOrder: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                            if(retType == RetType.RetType_Succeed){
                                let data = beautify(JSON.stringify(s2c), {
                                    indent_size: 2,
                                    space_in_empty_paren: true,
                                });
                                console.log(data);
                            }
                        }
                    }
                }
            }
            catch(err){
                console.log(err)
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

*   **Output**

    ModifyOrder: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6684972",
        "trdMarket": 1
      },
      "orderID": "5870570404030790740"
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

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 20 次改单撤单接口，且连续两次请求的间隔不可小于 0.04 秒。
*   真实账户调用改单撤单接口前，需要先进行 [解锁](./trade_unlock.md)
    ；模拟账户无需解锁。

提示

*   若执行 **修改订单** 操作，各类订单类型对应的必传参数，可 [点击这里](./qa_trade.md#689)
     了解更多。
*   如果希望执行 **改单操作** 去 **修改订单数量**，此接口入参的订单数量 **qty**，应该等于期望成交的总数量。  
    举例： 一笔订单数量是 N 股，已部分成交 n 股。对于暂未成交的 (N-n) 股，如果您希望撤掉其中的 x 股，**modify\_order\_op** 应选择 NORMAL，**qty** 应传 (N-x)。 ![order_quantity](https://openapi.futunn.com/futu-api-doc/assets/img/order_quantity_cn.ef6e3011.png)
*   如果希望执行 **撤单操作**，此接口入参的 **modify\_order\_op** 应该选择 CANCEL。  
    举例： 一笔订单数量是 N 股，已部分成交 n 股。如果希望将未成交的 (N-n) 股全部撤掉，modify\_order\_op 应选择 CANCEL，此时 qty 和 price 的入参会被忽略。

← [下单](./trade_place-order.md) [查询未完成订单](./trade_get-order-list.md)
 →

[改单撤单](./trade_modify-order.md)