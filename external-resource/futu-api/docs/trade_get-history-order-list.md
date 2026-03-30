[#](./trade_get-history-order-list.md#3352)
 查询历史订单
===========================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`history_order_list_query(status_filter_list=[], code='', order_market=TrdMarket.NONE, start='', end='', trd_env=TrdEnv.REAL, acc_id=0, acc_index=0)`

*   **介绍**
    
    查询指定交易业务账户的历史订单列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | status\_filter\_list | list | 订单状态过滤<br>(ℹ️ *   返回指定状态的订单数据)<br>*   默认状态时，返回所有数据<br>*   list 中元素类型是 [OrderStatus](./trade_trade.md#797) |
    | code | str | 代码过滤<br>(ℹ️ *   返回指定代码的数据)<br>*   默认状态时，返回所有数据 |
    | order\_market | [TrdMarket](./trade_trade.md#719) | 订单标的所属市场过滤<br>(ℹ️ *   订单标的市场过滤，会返回该市场下的标的订单)<br>*   默认值为NONE，会返回账户下所有市场的订单数据 |
    | start | str | 开始时间<br>(ℹ️ *   严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传)<br>*   期货时区指定，请参见 [OpenD 配置](./quick_opend-base.md#6724) |
    | end | str | 结束时间<br>(ℹ️ *   严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传)<br>*   期货时区指定，请参见 [OpenD 配置](./quick_opend-base.md#6724) |
    | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。)<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。)<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    
    *   start 和 end 的组合如下
        
        | Start 类型 | End 类型 | 说明  |
        | --- | --- | --- |
        | str | str | start 和 end 分别为指定的日期 |
        | None | str | start 为 end 往前 90 天 |
        | str | None | end 为 start 往后 90 天 |
        | None | None | start 为往前 90 天，end 当前日期 |
        
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回订单列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   订单列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_side | [TrdSide](./trade_trade.md#5815) | 交易方向 |
        | order\_type | [OrderType](./trade_trade.md#4181) | 订单类型 |
        | order\_status | [OrderStatus](./trade_trade.md#797) | 订单状态 |
        | order\_id | str | 订单号 |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | order\_market | [TrdMarket](./trade_trade.md#719) | 订单标的所属市场 |
        | qty | float | 订单数量<br>(ℹ️ 期权期货单位是"张") |
        | price | float | 订单价格<br>(ℹ️ 精确到小数点后 3 位，超出部分四舍五入) |
        | currency | [Currency](./trade_trade.md#8019) | 交易货币 |
        | create\_time | str | 创建时间<br>(ℹ️ 期货时区指定，请参见 [OpenD 配置](./quick_opend-base.md#6724)) |
        | updated\_time | str | 最后更新时间<br>(ℹ️ 期货时区指定，请参见 [OpenD 配置](./quick_opend-base.md#6724)) |
        | dealt\_qty | float | 成交数量<br>(ℹ️ 期权期货单位是"张") |
        | dealt\_avg\_price | float | 成交均价<br>(ℹ️ 无精度限制) |
        | last\_err\_msg | str | 最后的错误描述<br>(ℹ️ 如果有错误，会返回最后一次错误的原因)  <br>如果无错误，返回空字符串 |
        | remark | str | 下单时备注的标识<br>(ℹ️ 详见 [place\_order](./trade_place-order.md))<br> 接口参数中的 remark |
        | time\_in\_force | [TimeInForce](./trade_trade.md#4241) | 有效期限 |
        | fill\_outside\_rth | bool | 是否允许盘前盘后（用于港股盘前竞价与美股盘前盘后）<br>(ℹ️ True：允许)  <br>False：不允许 |
        | session | [Session](./quote_quote.md#9152) | 交易订单时段（仅用于美股） |
        | aux\_price | float | 触发价格 |
        | trail\_type | [TrailType](./trade_trade.md#5644) | 跟踪类型 |
        | trail\_value | float | 跟踪金额/百分比 |
        | trail\_spread | float | 指定价差 |
        | jp\_acc\_type | [SubAccType](./trade_trade.md#6112) | 日本账户类型<br>(ℹ️ 仅对日本券商生效) |
        
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    ret, data = trd_ctx.history_order_list_query()
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果订单列表不为空
            print(data['order_id'][0])  # 获取持仓第一个订单号
            print(data['order_id'].values.tolist())  # 转为 list
    else:
        print('history_order_list_query error: ', data)
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

*   **Output**

            code stock_name order_market    trd_side           order_type   order_status             order_id    qty  price              create_time             updated_time  dealt_qty  dealt_avg_price last_err_msg      remark time_in_force fill_outside_rth session aux_price trail_type trail_value trail_spread currency jp_acc_type
    0   US.AAPL        US          BUY           NORMAL  CANCELLED_ALL  6644468615272262086  100.0  520.0  2021-09-06 10:17:52.465  2021-09-07 16:10:22.806        0.0              0.0               asdfg+=@@@           GTC      N/A        N/A       560        N/A         N/A          N/A      USD        N/A
    6644468615272262086
    ['6644468615272262086']
    

1  
2  
3  
4  

[#](./trade_get-history-order-list.md#5878)
 Trd\_GetHistoryOrderList.proto
-------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询指定交易业务账户的历史订单列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](./trade_trade.md#3894)
>     
> *   订单状态枚举参见 [OrderStatus](./trade_trade.md#797)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Order orderList = 2; //历史订单列表
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
> *   订单结构参见 [Order](./trade_trade.md#1935)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    2221
    

`uint GetHistoryOrderList(TrdGetHistoryOrderList.Request req);`  
`virtual void OnReply_GetHistoryOrderList(FTAPI_Conn client, uint nSerialNo, TrdGetHistoryOrderList.Response rsp);`

*   **介绍**
    
    查询指定交易业务账户的历史订单列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](./trade_trade.md#3894)
>     
> *   订单状态枚举参见 [OrderStatus](./trade_trade.md#797)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Order orderList = 2; //历史订单列表
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
> *   订单结构参见 [Order](./trade_trade.md#1935)
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
            TrdCommon.TrdFilterConditions filter = TrdCommon.TrdFilterConditions.CreateBuilder().Build();
            TrdGetHistoryOrderList.C2S c2s = TrdGetHistoryOrderList.C2S.CreateBuilder()
                    .SetHeader(header)
                    .SetFilterConditions(filter)
                    .Build();
            TrdGetHistoryOrderList.Request req = TrdGetHistoryOrderList.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetHistoryOrderList(req);
            Console.Write("Send TrdGetHistoryOrderList: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetHistoryOrderList(FTAPI_Conn client, uint nSerialNo, TrdGetHistoryOrderList.Response rsp)
        {
            Console.Write("Reply: TrdGetHistoryOrderList: {0}\n", nSerialNo);
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

    Trd onInitConnect: ret=0 desc= connID=6827794279500987418
    Send TrdGetHistoryOrderList: 3
    Reply: TrdGetHistoryOrderList: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int getHistoryOrderList(TrdGetHistoryOrderList.Request req);`  
`void onReply_GetHistoryOrderList(FTAPI_Conn client, int nSerialNo, TrdGetHistoryOrderList.Response rsp);`

*   **介绍**
    
    查询指定交易业务账户的历史订单列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](./trade_trade.md#3894)
>     
> *   订单状态枚举参见 [OrderStatus](./trade_trade.md#797)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Order orderList = 2; //历史订单列表
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
> *   订单结构参见 [Order](./trade_trade.md#1935)
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
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_US_VALUE)
                    .build();
            TrdCommon.TrdFilterConditions filter = TrdCommon.TrdFilterConditions.newBuilder().build();
            TrdGetHistoryOrderList.C2S c2s = TrdGetHistoryOrderList.C2S.newBuilder()
                    .setHeader(header)
                    .setFilterConditions(filter)
                    .build();
            TrdGetHistoryOrderList.Request req = TrdGetHistoryOrderList.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getHistoryOrderList(req);
            System.out.printf("Send TrdGetHistoryOrderList: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetHistoryOrderList(FTAPI_Conn client, int nSerialNo, TrdGetHistoryOrderList.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetHistoryOrderList failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetHistoryOrderList: %s\n", json);
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

*   **Output**

    Send TrdGetHistoryOrderList: 2
    Receive TrdGetHistoryOrderList: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 2
        },
        "orderList": [{\
          "trdSide": 1,\
          "orderType": 2,\
          "orderStatus": 11,\
          "orderID": "6664320708369556828",\
          "orderIDEx": "20210330_15680495_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1",\
          "code": "FUTU",\
          "name": "富途控股",\
          "qty": 234.0,\
          "price": 0.0,\
          "createTime": "2021-03-30 09:34:23.628",\
          "updateTime": "2021-03-30 09:34:24.016",\
          "fillQty": 234.0,\
          "fillAvgPrice": 127.635726495,\
          "secMarket": 2,\
          "createTimestamp": 1.617111263627814E9,\
          "updateTimestamp": 1.617111264016447E9,\
          "remark": "",\
          "timeInForce": 0,\
          "fillOutsideRTH": false,\
          "session": RTH,\
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

`Futu::u32_t GetHistoryOrderList(const Trd_GetHistoryOrderList::Request &stReq);`  
`virtual void OnReply_GetHistoryOrderList(Futu::u32_t nSerialNo, const Trd_GetHistoryOrderList::Response &stRsp) = 0;`

*   **介绍**
    
    查询指定交易业务账户的历史订单列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](./trade_trade.md#3894)
>     
> *   订单状态枚举参见 [OrderStatus](./trade_trade.md#797)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Order orderList = 2; //历史订单列表
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
> *   订单结构参见 [Order](./trade_trade.md#1935)
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
    		Trd_GetHistoryOrderList::Request req;
    		Trd_GetHistoryOrderList::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    		Trd_Common::TrdFilterConditions *filter = c2s->mutable_filterconditions();
    		filter->set_begintime("2021-05-01 00:00:00");
    		filter->set_endtime("2021-06-01 00:00:00");
    		auto filterStatusList = c2s->mutable_filterstatuslist();
    		filterStatusList->Add(Trd_Common::OrderStatus::OrderStatus_Filled_All);
    
            m_GetHistoryOrderListSerialNo = m_pTrdApi->GetHistoryOrderList(req);
            cout << "Request GetHistoryOrderList SerialNo: " << m_GetHistoryOrderListSerialNo << endl;
    	}
    
    	virtual void OnReply_GetHistoryOrderList(Futu::u32_t nSerialNo, const Trd_GetHistoryOrderList::Response &stRsp){
            if(nSerialNo == m_GetHistoryOrderListSerialNo)
            {
                cout << "OnReply_GetHistoryOrderList SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_GetHistoryOrderListSerialNo;
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

*   **Output**

    connect
    Request GetHistoryOrderList SerialNo: 4
    OnReply_GetHistoryOrderList SerialNo: 4
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
      "orderList": [\
       {\
        "trdSide": 1,\
        "orderType": 5,\
        "orderStatus": 11,\
        "orderID": "200810789995260636",\
        "orderIDEx": "1689799",\
        "code": "00700",\
        "name": "腾讯控股",\
        "qty": 100,\
        "price": 649.5,\
        "createTime": "2021-05-31 21:01:21",\
        "updateTime": "2021-06-01 09:30:07",\
        "fillQty": 100,\
        "fillAvgPrice": 618.5,\
        "secMarket": 1,\
        "createTimestamp": 1622466081,\
        "updateTimestamp": 1622511007,\
        "remark": "buy00700",\
        "timeInForce": 0\
       },\
    ...\
       {\
        "trdSide": 1,\
        "orderType": 5,\
        "orderStatus": 11,\
        "orderID": "8091353323268200353",\
        "orderIDEx": "1672200",\
        "code": "00700",\
        "name": "腾讯控股",\
        "qty": 400,\
        "price": 605,\
        "createTime": "2021-05-27 12:29:38",\
        "updateTime": "2021-05-27 13:00:08",\
        "fillQty": 400,\
        "fillAvgPrice": 605,\
        "secMarket": 1,\
        "createTimestamp": 1622089778,\
        "updateTimestamp": 1622091608,\
        "remark": "",\
        "timeInForce": 0\
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

`GetHistoryOrderList(req);`

*   **介绍**
    
    查询指定交易业务账户的历史订单列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](./trade_trade.md#3894)
>     
> *   订单状态枚举参见 [OrderStatus](./trade_trade.md#797)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Order orderList = 2; //历史订单列表
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
> *   订单结构参见 [Order](./trade_trade.md#1935)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetHistoryOrderList(){
        const { RetType } = Common
        const { TrdEnv, TrdMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ec16fde057a2e7a0'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.GetAccList({
                    c2s: {
                        userID: 0,
                    },
                }).then((res) => {
                    let { retType,s2c: { accList } } = res
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
                                filterConditions:{
                                    beginTime:"2021-09-01 00:00:00",
                                    endTime:"2021-09-30 00:00:00",
                                },
                            },
                        };
    
                        websocket.GetHistoryOrderList(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetHistoryOrderList: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    GetHistoryOrderList: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6378",
        "trdMarket": 1
      },
      "orderList": [{\
        "trdSide": 1,\
        "orderType": 5,\
        "orderStatus": 11,\
        "orderID": "6520476875838699625",\
        "orderIDEx": "262973",\
        "code": "00700",\
        "name": "腾讯控股",\
        "qty": 100,\
        "price": 480,\
        "createTime": "2021-09-13 17:07:10",\
        "updateTime": "2021-09-13 17:07:12",\
        "fillQty": 100,\
        "fillAvgPrice": 478,\
        "secMarket": 1,\
        "createTimestamp": 1631524030,\
        "updateTimestamp": 1631524032,\
        "remark": "",\
        "timeInForce": 0\
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

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询历史订单接口

提示

*   历史订单，按照时间的“倒序”进行排列，即：后提交的订单在前，先提交的订单在后

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`history_order_list_query(status_filter_list=[], code='', order_market=TrdMarket.NONE, start='', end='', trd_env=TrdEnv.REAL, acc_id=0, acc_index=0)`

*   **介绍**
    
    查询指定交易业务账户的历史订单列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | status\_filter\_list | list | 订单状态过滤<br>(ℹ️ *   返回指定状态的订单数据)<br>*   默认状态时，返回所有数据<br>*   list 中元素类型是 [OrderStatus](./trade_trade.md#797) |
    | code | str | 代码过滤<br>(ℹ️ *   返回指定代码的数据)<br>*   默认状态时，返回所有数据 |
    | order\_market | [TrdMarket](./trade_trade.md#719) | 订单标的所属市场过滤<br>(ℹ️ *   订单标的市场过滤，会返回该市场下的标的订单)<br>*   默认值为NONE，会返回账户下所有市场的订单数据 |
    | start | str | 开始时间<br>(ℹ️ *   严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传)<br>*   期货时区指定，请参见 [OpenD 配置](./quick_opend-base.md#6724) |
    | end | str | 结束时间<br>(ℹ️ *   严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传)<br>*   期货时区指定，请参见 [OpenD 配置](./quick_opend-base.md#6724) |
    | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。)<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。)<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    
    *   start 和 end 的组合如下
        
        | Start 类型 | End 类型 | 说明  |
        | --- | --- | --- |
        | str | str | start 和 end 分别为指定的日期 |
        | None | str | start 为 end 往前 90 天 |
        | str | None | end 为 start 往后 90 天 |
        | None | None | start 为往前 90 天，end 当前日期 |
        
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回订单列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   订单列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | trd\_side | [TrdSide](./trade_trade.md#5815) | 交易方向 |
        | order\_type | [OrderType](./trade_trade.md#4181) | 订单类型 |
        | order\_status | [OrderStatus](./trade_trade.md#797) | 订单状态 |
        | order\_id | str | 订单号 |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | order\_market | [TrdMarket](./trade_trade.md#719) | 订单标的所属市场 |
        | qty | float | 订单数量<br>(ℹ️ 期权期货单位是"张") |
        | price | float | 订单价格<br>(ℹ️ 精确到小数点后 3 位，超出部分四舍五入) |
        | currency | [Currency](./trade_trade.md#8019) | 交易货币 |
        | create\_time | str | 创建时间<br>(ℹ️ 期货时区指定，请参见 [OpenD 配置](./quick_opend-base.md#6724)) |
        | updated\_time | str | 最后更新时间<br>(ℹ️ 期货时区指定，请参见 [OpenD 配置](./quick_opend-base.md#6724)) |
        | dealt\_qty | float | 成交数量<br>(ℹ️ 期权期货单位是"张") |
        | dealt\_avg\_price | float | 成交均价<br>(ℹ️ 无精度限制) |
        | last\_err\_msg | str | 最后的错误描述<br>(ℹ️ 如果有错误，会返回最后一次错误的原因)  <br>如果无错误，返回空字符串 |
        | remark | str | 下单时备注的标识<br>(ℹ️ 详见 [place\_order](./trade_place-order.md))<br> 接口参数中的 remark |
        | time\_in\_force | [TimeInForce](./trade_trade.md#4241) | 有效期限 |
        | fill\_outside\_rth | bool | 是否允许盘前盘后（用于港股盘前竞价与美股盘前盘后）<br>(ℹ️ True：允许)  <br>False：不允许 |
        | session | [Session](./quote_quote.md#9152) | 交易订单时段（仅用于美股） |
        | aux\_price | float | 触发价格 |
        | trail\_type | [TrailType](./trade_trade.md#5644) | 跟踪类型 |
        | trail\_value | float | 跟踪金额/百分比 |
        | trail\_spread | float | 指定价差 |
        | jp\_acc\_type | [SubAccType](./trade_trade.md#6112) | 日本账户类型<br>(ℹ️ 仅对日本券商生效) |
        
*   **Example**
    

    from moomoo import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.history_order_list_query()
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果订单列表不为空
            print(data['order_id'][0])  # 获取持仓第一个订单号
            print(data['order_id'].values.tolist())  # 转为 list
    else:
        print('history_order_list_query error: ', data)
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

*   **Output**

            code stock_name order_market   trd_side           order_type   order_status             order_id    qty  price              create_time             updated_time  dealt_qty  dealt_avg_price last_err_msg      remark time_in_force fill_outside_rth session aux_price trail_type trail_value trail_spread currency jp_acc_type
    0   HK.00700        HK          BUY           NORMAL  CANCELLED_ALL  6644468615272262086  100.0  520.0  2021-09-06 10:17:52.465  2021-09-07 16:10:22.806        0.0              0.0               asdfg+=@@@           GTC      N/A        N/A       560        N/A         N/A          N/A      HKD        N/A
    6644468615272262086
    ['6644468615272262086']
    

1  
2  
3  
4  

[#](./trade_get-history-order-list.md#5878-2)
 Trd\_GetHistoryOrderList.proto
---------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询指定交易业务账户的历史订单列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](./trade_trade.md#3894)
>     
> *   订单状态枚举参见 [OrderStatus](./trade_trade.md#797)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Order orderList = 2; //历史订单列表
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
> *   订单结构参见 [Order](./trade_trade.md#1935)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    2221
    

`uint GetHistoryOrderList(TrdGetHistoryOrderList.Request req);`  
`virtual void OnReply_GetHistoryOrderList(MMAPI_Conn client, uint nSerialNo, TrdGetHistoryOrderList.Response rsp);`

*   **介绍**
    
    查询指定交易业务账户的历史订单列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](./trade_trade.md#3894)
>     
> *   订单状态枚举参见 [OrderStatus](./trade_trade.md#797)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Order orderList = 2; //历史订单列表
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
> *   订单结构参见 [Order](./trade_trade.md#1935)
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
            TrdCommon.TrdFilterConditions filter = TrdCommon.TrdFilterConditions.CreateBuilder().Build();
            TrdGetHistoryOrderList.C2S c2s = TrdGetHistoryOrderList.C2S.CreateBuilder()
                    .SetHeader(header)
                    .SetFilterConditions(filter)
                    .Build();
            TrdGetHistoryOrderList.Request req = TrdGetHistoryOrderList.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetHistoryOrderList(req);
            Console.Write("Send TrdGetHistoryOrderList: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetHistoryOrderList(MMAPI_Conn client, uint nSerialNo, TrdGetHistoryOrderList.Response rsp)
        {
            Console.Write("Reply: TrdGetHistoryOrderList: {0}\n", nSerialNo);
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

    Trd onInitConnect: ret=0 desc= connID=6827794279500987418
    Send TrdGetHistoryOrderList: 3
    Reply: TrdGetHistoryOrderList: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int getHistoryOrderList(TrdGetHistoryOrderList.Request req);`  
`void onReply_GetHistoryOrderList(MMAPI_Conn client, int nSerialNo, TrdGetHistoryOrderList.Response rsp);`

*   **介绍**
    
    查询指定交易业务账户的历史订单列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](./trade_trade.md#3894)
>     
> *   订单状态枚举参见 [OrderStatus](./trade_trade.md#797)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Order orderList = 2; //历史订单列表
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
> *   订单结构参见 [Order](./trade_trade.md#1935)
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
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_US_VALUE)
                    .build();
            TrdCommon.TrdFilterConditions filter = TrdCommon.TrdFilterConditions.newBuilder().build();
            TrdGetHistoryOrderList.C2S c2s = TrdGetHistoryOrderList.C2S.newBuilder()
                    .setHeader(header)
                    .setFilterConditions(filter)
                    .build();
            TrdGetHistoryOrderList.Request req = TrdGetHistoryOrderList.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getHistoryOrderList(req);
            System.out.printf("Send TrdGetHistoryOrderList: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetHistoryOrderList(MMAPI_Conn client, int nSerialNo, TrdGetHistoryOrderList.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetHistoryOrderList failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetHistoryOrderList: %s\n", json);
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

*   **Output**

    Send TrdGetHistoryOrderList: 2
    Receive TrdGetHistoryOrderList: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 2
        },
        "orderList": [{\
          "trdSide": 1,\
          "orderType": 2,\
          "orderStatus": 11,\
          "orderID": "6664320708369556828",\
          "orderIDEx": "20210330_15680495_SQSWWgSYCStLVb7BDmx7kgAARgy31Nc1",\
          "code": "FUTU",\
          "name": "富途控股",\
          "qty": 234.0,\
          "price": 0.0,\
          "createTime": "2021-03-30 09:34:23.628",\
          "updateTime": "2021-03-30 09:34:24.016",\
          "fillQty": 234.0,\
          "fillAvgPrice": 127.635726495,\
          "secMarket": 2,\
          "createTimestamp": 1.617111263627814E9,\
          "updateTimestamp": 1.617111264016447E9,\
          "remark": "",\
          "timeInForce": 0,\
          "fillOutsideRTH": false\
          "session": RTH\
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

`moomoo::u32_t GetHistoryOrderList(const Trd_GetHistoryOrderList::Request &stReq);`  
`virtual void OnReply_GetHistoryOrderList(moomoo::u32_t nSerialNo, const Trd_GetHistoryOrderList::Response &stRsp) = 0;`

*   **介绍**
    
    查询指定交易业务账户的历史订单列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](./trade_trade.md#3894)
>     
> *   订单状态枚举参见 [OrderStatus](./trade_trade.md#797)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Order orderList = 2; //历史订单列表
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
> *   订单结构参见 [Order](./trade_trade.md#1935)
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
    		Trd_GetHistoryOrderList::Request req;
    		Trd_GetHistoryOrderList::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    		Trd_Common::TrdFilterConditions *filter = c2s->mutable_filterconditions();
    		filter->set_begintime("2021-05-01 00:00:00");
    		filter->set_endtime("2021-06-01 00:00:00");
    		auto filterStatusList = c2s->mutable_filterstatuslist();
    		filterStatusList->Add(Trd_Common::OrderStatus::OrderStatus_Filled_All);
    
            m_GetHistoryOrderListSerialNo = m_pTrdApi->GetHistoryOrderList(req);
            cout << "Request GetHistoryOrderList SerialNo: " << m_GetHistoryOrderListSerialNo << endl;
    	}
    
    	virtual void OnReply_GetHistoryOrderList(moomoo::u32_t nSerialNo, const Trd_GetHistoryOrderList::Response &stRsp){
            if(nSerialNo == m_GetHistoryOrderListSerialNo)
            {
                cout << "OnReply_GetHistoryOrderList SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Trd *m_pTrdApi;
    
        moomoo::u32_t m_GetHistoryOrderListSerialNo;
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

*   **Output**

    connect
    Request GetHistoryOrderList SerialNo: 4
    OnReply_GetHistoryOrderList SerialNo: 4
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
      "orderList": [\
       {\
        "trdSide": 1,\
        "orderType": 5,\
        "orderStatus": 11,\
        "orderID": "200810789995260636",\
        "orderIDEx": "1689799",\
        "code": "00700",\
        "name": "腾讯控股",\
        "qty": 100,\
        "price": 649.5,\
        "createTime": "2021-05-31 21:01:21",\
        "updateTime": "2021-06-01 09:30:07",\
        "fillQty": 100,\
        "fillAvgPrice": 618.5,\
        "secMarket": 1,\
        "createTimestamp": 1622466081,\
        "updateTimestamp": 1622511007,\
        "remark": "buy00700",\
        "timeInForce": 0\
       },\
    ...\
       {\
        "trdSide": 1,\
        "orderType": 5,\
        "orderStatus": 11,\
        "orderID": "8091353323268200353",\
        "orderIDEx": "1672200",\
        "code": "00700",\
        "name": "腾讯控股",\
        "qty": 400,\
        "price": 605,\
        "createTime": "2021-05-27 12:29:38",\
        "updateTime": "2021-05-27 13:00:08",\
        "fillQty": 400,\
        "fillAvgPrice": 605,\
        "secMarket": 1,\
        "createTimestamp": 1622089778,\
        "updateTimestamp": 1622091608,\
        "remark": "",\
        "timeInForce": 0\
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

`GetHistoryOrderList(req);`

*   **介绍**
    
    查询指定交易业务账户的历史订单列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	repeated int32 filterStatusList = 3; //Trd_Common::OrderStatus，需要过滤的订单状态列表
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](./trade_trade.md#3894)
>     
> *   订单状态枚举参见 [OrderStatus](./trade_trade.md#797)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Order orderList = 2; //历史订单列表
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
> *   订单结构参见 [Order](./trade_trade.md#1935)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetHistoryOrderList(){
        const { RetType } = Common
        const { TrdEnv, TrdMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ec16fde057a2e7a0'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.GetAccList({
                    c2s: {
                        userID: 0,
                    },
                }).then((res) => {
                    let { retType,s2c: { accList } } = res
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
                                filterConditions:{
                                    beginTime:"2021-09-01 00:00:00",
                                    endTime:"2021-09-30 00:00:00",
                                },
                            },
                        };
    
                        websocket.GetHistoryOrderList(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetHistoryOrderList: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    GetHistoryOrderList: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6378",
        "trdMarket": 1
      },
      "orderList": [{\
        "trdSide": 1,\
        "orderType": 5,\
        "orderStatus": 11,\
        "orderID": "6520476875838699625",\
        "orderIDEx": "262973",\
        "code": "00700",\
        "name": "腾讯控股",\
        "qty": 100,\
        "price": 480,\
        "createTime": "2021-09-13 17:07:10",\
        "updateTime": "2021-09-13 17:07:12",\
        "fillQty": 100,\
        "fillAvgPrice": 478,\
        "secMarket": 1,\
        "createTimestamp": 1631524030,\
        "updateTimestamp": 1631524032,\
        "remark": "",\
        "timeInForce": 0\
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

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询历史订单接口

提示

*   历史订单，按照时间的“倒序”进行排列，即：后提交的订单在前，先提交的订单在后

← [查询未完成订单](./trade_get-order-list.md) [响应订单推送回调](./trade_update-order.md)
 →

[查询历史订单](./trade_get-history-order-list.md)