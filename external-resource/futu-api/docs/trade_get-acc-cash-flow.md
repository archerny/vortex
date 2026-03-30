 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/get-acc-cash-flow.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/get-acc-cash-flow.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/get-acc-cash-flow.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/get-acc-cash-flow.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
*   交易接口
    
    *   [交易接口总览](https://openapi.futunn.com/futu-api-doc/trade/overview.html)
        
    *   [交易对象](https://openapi.futunn.com/futu-api-doc/trade/base.html)
        
    *   账户
        
    *   资产持仓
        
        *   [查询账户资金](https://openapi.futunn.com/futu-api-doc/trade/get-funds.html)
            
        *   [查询最大可买可卖](https://openapi.futunn.com/futu-api-doc/trade/get-max-trd-qtys.html)
            
        *   [查询持仓](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html)
            
        *   [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)
            
        *   [查询账户现金流水](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html)
            
        
    *   订单
        
    *   成交
        
    *   [交易定义](https://openapi.futunn.com/futu-api-doc/trade/trade.html)
        
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html#2416)
 查询账户现金流水
========================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_acc_cash_flow(clearing_date='', trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, cashflow_direction=CashFlowDirection.NONE)`

*   **介绍**
    
    查询交易业务账户在指定日期的现金流水数据。数据覆盖出入金、调拨、货币兑换、买卖金融资产、融资融券利息等所有导致现金变动的事项。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | clearing\_date | str | 清算日期<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   如需查询多日，需逐日请求<br>*   格式：yyyy-MM-dd，例如：“2017-06-20” |
    | trd\_env | TrdEnv | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号 |
    | cashflow\_direction | [CashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573) | 筛选现金流方向 |
    
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回交易业务账户现金流水列表格式 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   交易业务账户现金流水列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | cashflow\_id | int | 现金流ID |
        | clearing\_date | str | 清算日期 |
        | settlement\_date | str | 交收日期 |
        | currency | [Currency](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3974) | 币种  |
        | cashflow\_type | str | 现金流类型 |
        | cashflow\_direction | [CashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573) | 现金流方向 |
        | cashflow\_amount | float | 金额（正数表示流入，负数表示流出） |
        | cashflow\_remark | str | 备注  |
        
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.get_acc_cash_flow(clearing_date='2025-02-18', trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, cashflow_direction=CashFlowDirection.NONE)
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果现金流水列表不为空
            print(data['cashflow_type'][0])  # 获取第一条流水的现金流类型
            print(data['cashflow_amount'].values.tolist())  # 转为 list
    else:
        print('get_acc_cash_flow error: ', data)
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

*   **Output**

       cashflow_id     clearing_date     settlement_date     currency     cashflow_type     cashflow_direction     cashflow_amount     cashflow_remark
    0  16308           2025-02-27        2025-02-28          HKD             其他                 N/A                   0.00      Opt ASS-P-JXC250227P13000-20250227
    1  16357           2025-02-27        2025-03-03          HKD             其他                 OUT               -104000.00
    2  16360           2025-02-27        2025-02-27          USD            基金赎回               IN                 23000.00     Fund Redemption#Taikang Kaitai US Dollar Money...
    3  16384           2025-02-27        2025-02-27          HKD            基金赎回               IN                104108.96     Fund Redemption#Taikang Kaitai Hong Kong Dolla...
    其他
    [0.00, -104000.00, 23000.00, 104108.96]
    

1  
2  
3  
4  
5  
6  
7  

[#](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html#5786)
 Trd\_FlowSummary.proto
------------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询交易业务账户在指定日期的现金流水数据。数据覆盖出入金、调拨、货币兑换、买卖金融资产、融资融券利息等所有导致现金变动的事项。
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required string clearingDate = 2; //清算日期，格式 "2017-05-20"
    	optional int32 cashFlowDirection = 3; //现金流方向 TrdCashFlowDirection
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

> *   现金流方向 [TrdCashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573)
>     

*   **返回**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated FlowSummaryInfo flowSummaryInfoList = 2; //现金流水数据
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

> *   现金流水数据 [FlowSummaryInfo](https://openapi.futunn.com/futu-api-doc/trade/trade.html#9378)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2226
    

`uint GetFlowSummary(TrdFlowSummary.Request req);`  
`virtual void OnReply_GetFlowSummary(FTAPI_Conn client, uint nSerialNo, TrdFlowSummary.Response rsp);`

*   **介绍**
    
    查询交易业务账户在指定日期的现金流水数据。数据覆盖出入金、调拨、货币兑换、买卖金融资产、融资融券利息等所有导致现金变动的事项。
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required string clearingDate = 2; //清算日期，格式 "2017-05-20"
    	optional int32 cashFlowDirection = 3; //现金流方向 TrdCashFlowDirection
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

> *   现金流方向 [TrdCashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573)
>     

*   **回调**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated FlowSummaryInfo flowSummaryInfoList = 2; //现金流水数据
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

> *   现金流水数据 [FlowSummaryInfo](https://openapi.futunn.com/futu-api-doc/trade/trade.html#9378)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
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
            TrdFlowSummary.C2S c2s = TrdFlowSummary.C2S.CreateBuilder()
                    .SetHeader(header)
                    .SetClearingDate("2025-02-18")
                    .Build();
            TrdFlowSummary.Request req = TrdFlowSummary.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetFlowSummary(req);
            Console.Write("Send TrdFlowSummary: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetFlowSummary(FTAPI_Conn client, uint nSerialNo, TrdFlowSummary.Response rsp)
        {
            Console.Write("Reply: TrdFlowSummary: {0}\n", nSerialNo);
            Console.Write("CashFlowAmount: {0}\n", rsp.S2C.FlowSummaryInfoListList[0].CashFlowAmount);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6826806647571888999
    Send TrdFlowSummary: 3
    Reply: TrdFlowSummary: 3
    CashFlowAmount: 23000
    

1  
2  
3  
4  

`int getFlowSummary(TrdFlowSummary.Request req);`  
`void onReply_GetFlowSummary(MMAPI_Conn client, int nSerialNo, TrdFlowSummary.Response rsp);`

*   **介绍**
    
    查询交易业务账户在指定日期的现金流水数据。数据覆盖出入金、调拨、货币兑换、买卖金融资产、融资融券利息等所有导致现金变动的事项。
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required string clearingDate = 2; //清算日期，格式 "2017-05-20"
    	optional int32 cashFlowDirection = 3; //现金流方向 TrdCashFlowDirection
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

> *   现金流方向 [TrdCashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573)
>     

*   **回调**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated FlowSummaryInfo flowSummaryInfoList = 2; //现金流水数据
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

> *   现金流水数据 [FlowSummaryInfo](https://openapi.futunn.com/futu-api-doc/trade/trade.html#9378)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
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
            TrdFlowSummary.C2S c2s = TrdFlowSummary.C2S.newBuilder()
                    .setHeader(header)
                    .setClearingDate("2025-02-18")
                .build();
            TrdFlowSummary.Request req = TrdFlowSummary.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getFlowSummary(req);
            System.out.printf("Send TrdFlowSummary: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetFlowSummary(FTAPI_Conn client, int nSerialNo, TrdFlowSummary.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdFlowSummary failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdFlowSummary: %s\n", json);
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

*   **Output**

    Send TrdFlowSummary: 2
    Receive TrdFlowSummary: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "flowSummaryInfoList": {
          "clearingDate": "2025-02-27",
          "settlementDate": "2025-02-28",
          "currency": 1,
          "cashFlowType": "其他",
          "cashFlowDirection": 1,
          "cashFlowAmount": 23000.00,
          "cashFlowRemark": "Fund Redemption#Taikang Kaitai US Dollar Money",
          "cashFlowID": 16328
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

`Futu::u32_t GetFlowSummary(const Trd_FlowSummary::Request& stReq);`  
`virtual OnReply_GetFlowSummary(Futu::u32_t nSerialNo, const Trd_FlowSummary::Response& stRsp) = 0;`

*   **介绍**
    
    查询交易业务账户在指定日期的现金流水数据。数据覆盖出入金、调拨、货币兑换、买卖金融资产、融资融券利息等所有导致现金变动的事项。
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required string clearingDate = 2; //清算日期，格式 "2017-05-20"
    	optional int32 cashFlowDirection = 3; //现金流方向 TrdCashFlowDirection
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

> *   现金流方向 [TrdCashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573)
>     

*   **回调**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated FlowSummaryInfo flowSummaryInfoList = 2; //现金流水数据
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

> *   现金流水数据 [FlowSummaryInfo](https://openapi.futunn.com/futu-api-doc/trade/trade.html#9378)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
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
    		Trd_FlowSummary::Request req;
    		Trd_FlowSummary::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(281756457888247915);
    		header->set_trdenv(1);
    		header->set_trdmarket(1);
    		c2s->set_clearingdate("2025-02-18");
    
            m_GetFlowSummarySerialNo = m_pTrdApi->GetFlowSummary(req);
            cout << "Request GetFlowSummary SerialNo: " << m_GetFlowSummarySerialNo << endl;
        }
    
        virtual void OnReply_GetFlowSummary(Futu::u32_t nSerialNo, const Trd_FlowSummary::Response& stRsp) {
            if(nSerialNo == m_GetFlowSummarySerialNo)
            {
                cout << "OnReply_GetFlowSummary SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
        }
    
    protected:
        FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_GetFlowSummarySerialNo;
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

*   **Output**

    connect
    Request GetAccList SerialNo: 4
    OnReply_GetAccList SerialNo: 4
    {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "flowSummaryInfoList": {
          "clearingDate": "2025-02-27",
          "settlementDate": "2025-02-28",
          "currency": 1,
          "cashFlowType": "其他",
          "cashFlowDirection": 1,
          "cashFlowAmount": 23000.00,
          "cashFlowRemark": "Fund Redemption#Taikang Kaitai US Dollar Money",
          "cashFlowID": 16328
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

`GetAccList(req);`

*   **介绍**
    
    查询交易业务账户在指定日期的现金流水数据。数据覆盖出入金、调拨、货币兑换、买卖金融资产、融资融券利息等所有导致现金变动的事项。
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required string clearingDate = 2; //清算日期，格式 "2017-05-20"
    	optional int32 cashFlowDirection = 3; //现金流方向 TrdCashFlowDirection
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

> *   现金流方向 [TrdCashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573)
>     

*   **返回**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated FlowSummaryInfo flowSummaryInfoList = 2; //现金流水数据
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

> *   现金流水数据 [FlowSummaryInfo](https://openapi.futunn.com/futu-api-doc/trade/trade.html#9378)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetFlowSummary(){
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
                                clearingDate: "2025-02-18", 
                            },
                        };
    
                        websocket.GetFlowSummary(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetFlowSummary: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    GetFlowSummary: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756457888247915",
        "trdMarket": 1
      },
      "flowSummaryInfoList": {
        "clearingDate": "2025-02-27",
        "settlementDate": "2025-02-28",
        "currency": 1,
        "cashFlowType": "其他",
        "cashFlowDirection": 1,
        "cashFlowAmount": 23000.00,
        "cashFlowRemark": "Fund Redemption#Taikang Kaitai US Dollar Money",
        "cashFlowID": 16328
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
15  
16  
17  
18  
19  

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 20 次现金流水接口。
*   现金流水，按照时间的“顺序”进行排列。
*   模拟账户不支持查询现金流水。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_acc_cash_flow(clearing_date='', trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, cashflow_direction=CashFlowDirection.NONE)`

*   **介绍**
    
    查询交易业务账户在指定日期的现金流水数据。数据覆盖出入金、调拨、货币兑换、买卖金融资产、融资融券利息等所有导致现金变动的事项。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | clearing\_date | str | 清算日期<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   如需查询多日，需逐日请求<br>*   格式：yyyy-MM-dd，例如：“2017-06-20” |
    | trd\_env | TrdEnv | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号 |
    | cashflow\_direction | [CashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573) | 筛选现金流方向 |
    
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回交易业务账户现金流水列表格式 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   交易业务账户现金流水列表格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | cashflow\_id | int | 现金流唯一标识 |
        | clearing\_date | str | 清算日期 |
        | settlement\_date | str | 交收日期 |
        | currency | [Currency](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3974) | 币种  |
        | cashflow\_type | str | 现金流类型 |
        | cashflow\_direction | [CashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573) | 现金流方向 |
        | cashflow\_amount | float | 金额（正数表示流入，负数表示流出） |
        | cashflow\_remark | str | 备注  |
        
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    ret, data = trd_ctx.get_acc_cash_flow(clearing_date='2025-02-18', trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, cashflow_direction=CashFlowDirection.NONE)
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果现金流水列表不为空
            print(data['cashflow_type'][0])  # 获取第一条流水的现金流类型
            print(data['cashflow_amount'].values.tolist())  # 转为 list
    else:
        print('get_acc_cash_flow error: ', data)
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

*   **Output**

       cashflow_id     clearing_date     settlement_date     currency     cashflow_type     cashflow_direction     cashflow_amount     cashflow_remark
    0  16308           2025-02-27        2025-02-28          HKD             其他                 N/A                   0.00      Opt ASS-P-JXC250227P13000-20250227
    1  16357           2025-02-27        2025-03-03          HKD             其他                 OUT               -104000.00
    2  16360           2025-02-27        2025-02-27          USD            基金赎回               IN                 23000.00     Fund Redemption#Taikang Kaitai US Dollar Money...
    3  16384           2025-02-27        2025-02-27          HKD            基金赎回               IN                104108.96     Fund Redemption#Taikang Kaitai Hong Kong Dolla...
    其他
    [0.00, -104000.00, 23000.00, 104108.96]
    

1  
2  
3  
4  
5  
6  
7  

[#](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html#5786-2)
 Trd\_FlowSummary.proto
--------------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询交易业务账户在指定日期的现金流水数据。数据覆盖出入金、调拨、货币兑换、买卖金融资产、融资融券利息等所有导致现金变动的事项。
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required string clearingDate = 2; //清算日期，格式 "2017-05-20"
    	optional int32 cashFlowDirection = 3; //现金流方向 TrdCashFlowDirection
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

> *   现金流方向 [TrdCashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573)
>     

*   **返回**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated FlowSummaryInfo flowSummaryInfoList = 2; //现金流水数据
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

> *   现金流水数据 [FlowSummaryInfo](https://openapi.futunn.com/futu-api-doc/trade/trade.html#9378)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2226
    

`uint GetFlowSummary(TrdFlowSummary.Request req);`  
`virtual void OnReply_GetFlowSummary(FTAPI_Conn client, uint nSerialNo, TrdFlowSummary.Response rsp);`

*   **介绍**
    
    查询交易业务账户在指定日期的现金流水数据。数据覆盖出入金、调拨、货币兑换、买卖金融资产、融资融券利息等所有导致现金变动的事项。
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required string clearingDate = 2; //清算日期，格式 "2017-05-20"
    	optional int32 cashFlowDirection = 3; //现金流方向 TrdCashFlowDirection
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

> *   现金流方向 [TrdCashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573)
>     

*   **回调**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated FlowSummaryInfo flowSummaryInfoList = 2; //现金流水数据
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

> *   现金流水数据 [FlowSummaryInfo](https://openapi.futunn.com/futu-api-doc/trade/trade.html#9378)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
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
            TrdFlowSummary.C2S c2s = TrdFlowSummary.C2S.CreateBuilder()
                    .SetHeader(header)
                    .SetClearingDate("2025-02-18")
                    .Build();
            TrdFlowSummary.Request req = TrdFlowSummary.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetFlowSummary(req);
            Console.Write("Send TrdFlowSummary: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetFlowSummary(FTAPI_Conn client, uint nSerialNo, TrdFlowSummary.Response rsp)
        {
            Console.Write("Reply: TrdFlowSummary: {0}\n", nSerialNo);
            Console.Write("CashFlowAmount: {0}\n", rsp.S2C.FlowSummaryInfoListList[0].CashFlowAmount);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6826806647571888999
    Send TrdFlowSummary: 3
    Reply: TrdFlowSummary: 3
    CashFlowAmount: 23000
    

1  
2  
3  
4  

`int getFlowSummary(TrdFlowSummary.Request req);`  
`void onReply_GetFlowSummary(MMAPI_Conn client, int nSerialNo, TrdFlowSummary.Response rsp);`

*   **介绍**
    
    查询交易业务账户在指定日期的现金流水数据。数据覆盖出入金、调拨、货币兑换、买卖金融资产、融资融券利息等所有导致现金变动的事项。
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required string clearingDate = 2; //清算日期，格式 "2017-05-20"
    	optional int32 cashFlowDirection = 3; //现金流方向 TrdCashFlowDirection
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

> *   现金流方向 [TrdCashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573)
>     

*   **回调**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated FlowSummaryInfo flowSummaryInfoList = 2; //现金流水数据
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

> *   现金流水数据 [FlowSummaryInfo](https://openapi.futunn.com/futu-api-doc/trade/trade.html#9378)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
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
            TrdFlowSummary.C2S c2s = TrdFlowSummary.C2S.newBuilder()
                    .setHeader(header)
                    .setClearingDate("2025-02-18")
                .build();
            TrdFlowSummary.Request req = TrdFlowSummary.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getFlowSummary(req);
            System.out.printf("Send TrdFlowSummary: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetFlowSummary(FTAPI_Conn client, int nSerialNo, TrdFlowSummary.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdFlowSummary failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdFlowSummary: %s\n", json);
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

*   **Output**

    Send TrdFlowSummary: 2
    Receive TrdFlowSummary: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "flowSummaryInfoList": {
          "clearingDate": "2025-02-27",
          "settlementDate": "2025-02-28",
          "currency": 1,
          "cashFlowType": "其他",
          "cashFlowDirection": 1,
          "cashFlowAmount": 23000.00,
          "cashFlowRemark": "Fund Redemption#Taikang Kaitai US Dollar Money",
          "cashFlowID": 16328
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

`Futu::u32_t GetFlowSummary(const Trd_FlowSummary::Request& stReq);`  
`virtual OnReply_GetFlowSummary(Futu::u32_t nSerialNo, const Trd_FlowSummary::Response& stRsp) = 0;`

*   **介绍**
    
    查询交易业务账户在指定日期的现金流水数据。数据覆盖出入金、调拨、货币兑换、买卖金融资产、融资融券利息等所有导致现金变动的事项。
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required string clearingDate = 2; //清算日期，格式 "2017-05-20"
    	optional int32 cashFlowDirection = 3; //现金流方向 TrdCashFlowDirection
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

> *   现金流方向 [TrdCashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573)
>     

*   **回调**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated FlowSummaryInfo flowSummaryInfoList = 2; //现金流水数据
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

> *   现金流水数据 [FlowSummaryInfo](https://openapi.futunn.com/futu-api-doc/trade/trade.html#9378)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
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
    		Trd_FlowSummary::Request req;
    		Trd_FlowSummary::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(281756457888247915);
    		header->set_trdenv(1);
    		header->set_trdmarket(1);
    		c2s->set_clearingdate("2025-02-18");
    
            m_GetFlowSummarySerialNo = m_pTrdApi->GetFlowSummary(req);
            cout << "Request GetFlowSummary SerialNo: " << m_GetFlowSummarySerialNo << endl;
        }
    
        virtual void OnReply_GetFlowSummary(Futu::u32_t nSerialNo, const Trd_FlowSummary::Response& stRsp) {
            if(nSerialNo == m_GetFlowSummarySerialNo)
            {
                cout << "OnReply_GetFlowSummary SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
        }
    
    protected:
        FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_GetFlowSummarySerialNo;
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

*   **Output**

    connect
    Request GetAccList SerialNo: 4
    OnReply_GetAccList SerialNo: 4
    {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "flowSummaryInfoList": {
          "clearingDate": "2025-02-27",
          "settlementDate": "2025-02-28",
          "currency": 1,
          "cashFlowType": "其他",
          "cashFlowDirection": 1,
          "cashFlowAmount": 23000.00,
          "cashFlowRemark": "Fund Redemption#Taikang Kaitai US Dollar Money",
          "cashFlowID": 16328
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

`GetAccList(req);`

*   **介绍**
    
    查询交易业务账户在指定日期的现金流水数据。数据覆盖出入金、调拨、货币兑换、买卖金融资产、融资融券利息等所有导致现金变动的事项。
    
*   **参数**
    

    message C2S
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	required string clearingDate = 2; //清算日期，格式 "2017-05-20"
    	optional int32 cashFlowDirection = 3; //现金流方向 TrdCashFlowDirection
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

> *   现金流方向 [TrdCashFlowDirection](https://openapi.futunn.com/futu-api-doc/trade/trade.html#7573)
>     

*   **返回**

    message S2C
    {
        required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated FlowSummaryInfo flowSummaryInfoList = 2; //现金流水数据
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

> *   现金流水数据 [FlowSummaryInfo](https://openapi.futunn.com/futu-api-doc/trade/trade.html#9378)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetFlowSummary(){
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
                                clearingDate: "2025-02-18", 
                            },
                        };
    
                        websocket.GetFlowSummary(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetFlowSummary: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    GetFlowSummary: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 1,
        "accID": "281756457888247915",
        "trdMarket": 1
      },
      "flowSummaryInfoList": {
        "clearingDate": "2025-02-27",
        "settlementDate": "2025-02-28",
        "currency": 1,
        "cashFlowType": "其他",
        "cashFlowDirection": 1,
        "cashFlowAmount": 23000.00,
        "cashFlowRemark": "Fund Redemption#Taikang Kaitai US Dollar Money",
        "cashFlowID": 16328
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
15  
16  
17  
18  
19  

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 20 次现金流水接口。
*   现金流水，按照时间的“顺序”进行排列。
*   模拟交易和 moomoo US 账户暂不支持查询现金流水。

← [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html) [下单](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)
 →

[查询账户现金流水](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html)