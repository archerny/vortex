 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-ipo-list.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-ipo-list.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-ipo-list.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-ipo-list.html)
    

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
    

[#](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html#7768)
 获取 IPO 信息
====================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_ipo_list(market)`

*   **介绍**
    
    获取指定市场的 IPO 信息
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | market | [Market](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427) | 市场标识<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>注意：这里不区分沪和深，输入沪或者深都会返回沪深市场的股票 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回 IPO 数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   IPO 数据
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | list\_time | str | 上市日期，美股是预计上市日期<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd |
        | list\_timestamp | float | 上市日期时间戳，美股是预计上市日期时间戳 |
        | apply\_code | str | 申购代码（A 股适用） |
        | issue\_size | int | 发行总数（A 股适用）；发行量（美股适用） |
        | online\_issue\_size | int | 网上发行量（A 股适用） |
        | apply\_upper\_limit | int | 申购上限（A 股适用） |
        | apply\_limit\_market\_value | int | 顶格申购需配市值（A 股适用） |
        | is\_estimate\_ipo\_price | bool | 是否预估发行价（A 股适用） |
        | ipo\_price | float | 发行价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新<br><br>（A 股适用） |
        | industry\_pe\_rate | float | 行业市盈率（A 股适用） |
        | is\_estimate\_winning\_ratio | bool | 是否预估中签率（A 股适用） |
        | winning\_ratio | float | 中签率<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新<br>*   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%<br><br>（A 股适用） |
        | issue\_pe\_rate | float | 发行市盈率（A 股适用） |
        | apply\_time | str | 申购日期字符串<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd<br><br>（A 股适用） |
        | apply\_timestamp | float | 申购日期时间戳（A 股适用） |
        | winning\_time | str | 公布中签日期字符串<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd<br><br>（A 股适用） |
        | winning\_timestamp | float | 公布中签日期时间戳（A 股适用） |
        | is\_has\_won | bool | 是否已经公布中签号（A 股适用） |
        | winning\_num\_data | str | 中签号（A 股适用）<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式类似：  <br>末"五"位数：12345，12346  <br>末"六"位数：123456 |
        | ipo\_price\_min | float | 最低发售价（港股适用）；最低发行价（美股适用） |
        | ipo\_price\_max | float | 最高发售价（港股适用）；最高发行价（美股适用） |
        | list\_price | float | 上市价（港股适用） |
        | lot\_size | int | 每手股数 |
        | entrance\_price | float | 入场费（港股适用） |
        | is\_subscribe\_status | bool | 是否为认购状态<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：认购中  <br>False：待上市 |
        | apply\_end\_time | str | 截止认购日期字符串<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd<br><br>（港股适用） |
        | apply\_end\_timestamp | float | 截止认购日期时间戳 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_ipo_list(Market.HK)
    if ret == RET_OK:
        print(data)
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

*   **Output**

        code      name   list_time  list_timestamp apply_code issue_size online_issue_size apply_upper_limit apply_limit_market_value is_estimate_ipo_price ipo_price industry_pe_rate is_estimate_winning_ratio winning_ratio issue_pe_rate apply_time apply_timestamp winning_time winning_timestamp is_has_won winning_num_data  ipo_price_min  ipo_price_max  list_price  lot_size  entrance_price  is_subscribe_status apply_end_time  apply_end_timestamp
    0  HK.06666  恒大物业  2020-12-02    1.606838e+09        N/A        N/A               N/A               N/A                      N/A                   N/A       N/A              N/A                       N/A           N/A           N/A        N/A             N/A          N/A               N/A        N/A              N/A          8.500           9.75         0.0       500         4924.12                 True     2020-11-26         1.606352e+09
    1  HK.02110  裕勤控股  2020-12-07    1.607270e+09        N/A        N/A               N/A               N/A                      N/A                   N/A       N/A              N/A                       N/A           N/A           N/A        N/A             N/A          N/A               N/A        N/A              N/A          0.225           0.27         0.0     10000         2727.21                 True     2020-11-27         1.606439e+09
    HK.06666
    ['HK.06666', 'HK.02110']
    

1  
2  
3  
4  
5  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html#4600)
 Qot\_GetIpoList.proto
------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取指定市场的 IPO 信息
    
*   **参数**
    

    message C2S
    {
    	required int32 market = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
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

> *   行情市场参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // IPO 基本数据
    message BasicIpoData
    {
    	required Qot_Common.Security security = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	required string name = 2; // 股票名称
    	optional string listTime = 3; // 上市日期字符串（格式：yyyy-MM-dd）
    	optional double listTimestamp = 4; // 上市日期时间戳
    };
    
    // A 股 IPO 列表额外数据
    message CNIpoExData 
    {
    	required string applyCode = 1; // 申购代码
    	required int64 issueSize = 2; // 发行总数
    	required int64 onlineIssueSize = 3; // 网上发行量
    	required int64 applyUpperLimit = 4; // 申购上限
    	required int64 applyLimitMarketValue = 5; // 顶格申购需配市值
    	required bool isEstimateIpoPrice = 6; // 是否预估发行价
    	required double ipoPrice = 7; // 发行价 预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double industryPeRate = 8; // 行业市盈率
    	required bool isEstimateWinningRatio = 9; // 是否预估中签率
    	required double winningRatio = 10; // 中签率 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double issuePeRate = 11; // 发行市盈率
    	optional string applyTime = 12; // 申购日期字符串（格式：yyyy-MM-dd）
    	optional double applyTimestamp = 13; // 申购日期时间戳
    	optional string winningTime = 14; // 公布中签日期字符串（格式：yyyy-MM-dd）
    	optional double winningTimestamp = 15; // 公布中签日期时间戳
    	required bool isHasWon = 16; // 是否已经公布中签号
    	repeated WinningNumData winningNumData = 17; // Qot_GetIpoList::WinningNumData 中签号数据，对应 PC 中"公布中签日期的已公布"
    };
    
    // 中签号数据
    message WinningNumData
    {
    	required string winningName = 1; // 分组名
        required string winningInfo = 2; // 中签号信息
    }
    
    // 港股 IPO 列表额外数据
    message HKIpoExData
    {
    	required double ipoPriceMin = 1; // 最低发售价
    	required double ipoPriceMax = 2; // 最高发售价
    	required double listPrice = 3; // 上市价
    	required int32 lotSize = 4; // 每手股数
    	required double entrancePrice = 5; // 入场费
    	required bool isSubscribeStatus = 6; // 是否为认购状态，True-认购中，False-待上市
    	optional string applyEndTime = 7; // 截止认购日期字符串（格式：yyyy-MM-dd）
    	optional double applyEndTimestamp = 8; // 截止认购日期时间戳 因需处理认购手续，富途认购截止时间会早于交易所公布的日期。
    };
    
    // 美股 IPO 列表额外数据
    message USIpoExData  
    {
    	required double ipoPriceMin = 1; // 最低发行价
    	required double ipoPriceMax = 2; // 最高发行价
    	required int64 issueSize = 3; // 发行量
    };
    
    // 新股 IPO 数据
    message IpoData
    {	
    	required BasicIpoData basic = 1; // IPO 基本数据	
    	optional CNIpoExData cnExData = 2; // A 股 IPO 额外数据
    	optional HKIpoExData hkExData = 3; // 港股 IPO 额外数据
    	optional USIpoExData usExData = 4; // 美股 IPO 额外数据
    };
    
    message S2C
    {
    	repeated IpoData ipoList = 1; // 新股 IPO 数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3217
    

`uint GetIpoList(QotGetIpoList.Request req);`  
`virtual void OnReply_GetIpoList(FTAPI_Conn client, uint nSerialNo, QotGetIpoList.Response rsp);`

*   **介绍**

获取指定市场的 IPO 信息

*   **参数**

    message C2S
    {
    	required int32 market = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
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

> *   行情市场参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // IPO 基本数据
    message BasicIpoData
    {
    	required Qot_Common.Security security = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	required string name = 2; // 股票名称
    	optional string listTime = 3; // 上市日期字符串（格式：yyyy-MM-dd）
    	optional double listTimestamp = 4; // 上市日期时间戳
    };
    
    // A 股 IPO 列表额外数据
    message CNIpoExData 
    {
    	required string applyCode = 1; // 申购代码
    	required int64 issueSize = 2; // 发行总数
    	required int64 onlineIssueSize = 3; // 网上发行量
    	required int64 applyUpperLimit = 4; // 申购上限
    	required int64 applyLimitMarketValue = 5; // 顶格申购需配市值
    	required bool isEstimateIpoPrice = 6; // 是否预估发行价
    	required double ipoPrice = 7; // 发行价 预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double industryPeRate = 8; // 行业市盈率
    	required bool isEstimateWinningRatio = 9; // 是否预估中签率
    	required double winningRatio = 10; // 中签率 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double issuePeRate = 11; // 发行市盈率
    	optional string applyTime = 12; // 申购日期字符串（格式：yyyy-MM-dd）
    	optional double applyTimestamp = 13; // 申购日期时间戳
    	optional string winningTime = 14; // 公布中签日期字符串（格式：yyyy-MM-dd）
    	optional double winningTimestamp = 15; // 公布中签日期时间戳
    	required bool isHasWon = 16; // 是否已经公布中签号
    	repeated WinningNumData winningNumData = 17; // Qot_GetIpoList::WinningNumData 中签号数据，对应 PC 中"公布中签日期的已公布"
    };
    
    // 中签号数据
    message WinningNumData
    {
    	required string winningName = 1; // 分组名
        required string winningInfo = 2; // 中签号信息
    }
    
    // 港股 IPO 列表额外数据
    message HKIpoExData
    {
    	required double ipoPriceMin = 1; // 最低发售价
    	required double ipoPriceMax = 2; // 最高发售价
    	required double listPrice = 3; // 上市价
    	required int32 lotSize = 4; // 每手股数
    	required double entrancePrice = 5; // 入场费
    	required bool isSubscribeStatus = 6; // 是否为认购状态，True-认购中，False-待上市
    	optional string applyEndTime = 7; // 截止认购日期字符串（格式：yyyy-MM-dd）
    	optional double applyEndTimestamp = 8; // 截止认购日期时间戳 因需处理认购手续，富途认购截止时间会早于交易所公布的日期。
    };
    
    // 美股 IPO 列表额外数据
    message USIpoExData  
    {
    	required double ipoPriceMin = 1; // 最低发行价
    	required double ipoPriceMax = 2; // 最高发行价
    	required int64 issueSize = 3; // 发行量
    };
    
    // 新股 IPO 数据
    message IpoData
    {	
    	required BasicIpoData basic = 1; // IPO 基本数据	
    	optional CNIpoExData cnExData = 2; // A 股 IPO 额外数据
    	optional HKIpoExData hkExData = 3; // 港股 IPO 额外数据
    	optional USIpoExData usExData = 4; // 美股 IPO 额外数据
    };
    
    message S2C
    {
    	repeated IpoData ipoList = 1; // 新股 IPO 数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
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
    
            QotGetIpoList.C2S c2s = QotGetIpoList.C2S.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_CNSH_Security)
                    .Build();
            QotGetIpoList.Request req = QotGetIpoList.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetIpoList(req);
            Console.Write("Send QotGetIpoList: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetIpoList(FTAPI_Conn client, uint nSerialNo, QotGetIpoList.Response rsp)
        {
            Console.Write("Reply: QotGetIpoList: {0}\n", nSerialNo);
            if(rsp.S2C.IpoListCount > 0)
            {
                Console.Write("name: {0} \n", rsp.S2C.IpoListList[0].Basic.Name);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826061149989914129
    Send QotGetIpoList: 3
    Reply: QotGetIpoList: 3
    name: 久祺股份
    

1  
2  
3  
4  

`int getIpoList(QotGetIpoList.Request req);`  
`void onReply_GetIpoList(FTAPI_Conn client, int nSerialNo, QotGetIpoList.Response rsp);`

*   **介绍**

获取指定市场的 IPO 信息

*   **参数**

    message C2S
    {
    	required int32 market = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
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

> *   行情市场参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // IPO 基本数据
    message BasicIpoData
    {
    	required Qot_Common.Security security = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	required string name = 2; // 股票名称
    	optional string listTime = 3; // 上市日期字符串（格式：yyyy-MM-dd）
    	optional double listTimestamp = 4; // 上市日期时间戳
    };
    
    // A 股 IPO 列表额外数据
    message CNIpoExData 
    {
    	required string applyCode = 1; // 申购代码
    	required int64 issueSize = 2; // 发行总数
    	required int64 onlineIssueSize = 3; // 网上发行量
    	required int64 applyUpperLimit = 4; // 申购上限
    	required int64 applyLimitMarketValue = 5; // 顶格申购需配市值
    	required bool isEstimateIpoPrice = 6; // 是否预估发行价
    	required double ipoPrice = 7; // 发行价 预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double industryPeRate = 8; // 行业市盈率
    	required bool isEstimateWinningRatio = 9; // 是否预估中签率
    	required double winningRatio = 10; // 中签率 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double issuePeRate = 11; // 发行市盈率
    	optional string applyTime = 12; // 申购日期字符串（格式：yyyy-MM-dd）
    	optional double applyTimestamp = 13; // 申购日期时间戳
    	optional string winningTime = 14; // 公布中签日期字符串（格式：yyyy-MM-dd）
    	optional double winningTimestamp = 15; // 公布中签日期时间戳
    	required bool isHasWon = 16; // 是否已经公布中签号
    	repeated WinningNumData winningNumData = 17; // Qot_GetIpoList::WinningNumData 中签号数据，对应 PC 中"公布中签日期的已公布"
    };
    
    // 中签号数据
    message WinningNumData
    {
    	required string winningName = 1; // 分组名
        required string winningInfo = 2; // 中签号信息
    }
    
    // 港股 IPO 列表额外数据
    message HKIpoExData
    {
    	required double ipoPriceMin = 1; // 最低发售价
    	required double ipoPriceMax = 2; // 最高发售价
    	required double listPrice = 3; // 上市价
    	required int32 lotSize = 4; // 每手股数
    	required double entrancePrice = 5; // 入场费
    	required bool isSubscribeStatus = 6; // 是否为认购状态，True-认购中，False-待上市
    	optional string applyEndTime = 7; // 截止认购日期字符串（格式：yyyy-MM-dd）
    	optional double applyEndTimestamp = 8; // 截止认购日期时间戳 因需处理认购手续，富途认购截止时间会早于交易所公布的日期。
    };
    
    // 美股 IPO 列表额外数据
    message USIpoExData  
    {
    	required double ipoPriceMin = 1; // 最低发行价
    	required double ipoPriceMax = 2; // 最高发行价
    	required int64 issueSize = 3; // 发行量
    };
    
    // 新股 IPO 数据
    message IpoData
    {	
    	required BasicIpoData basic = 1; // IPO 基本数据	
    	optional CNIpoExData cnExData = 2; // A 股 IPO 额外数据
    	optional HKIpoExData hkExData = 3; // 港股 IPO 额外数据
    	optional USIpoExData usExData = 4; // 美股 IPO 额外数据
    };
    
    message S2C
    {
    	repeated IpoData ipoList = 1; // 新股 IPO 数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
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
    
            QotGetIpoList.C2S c2s = QotGetIpoList.C2S.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .build();
            QotGetIpoList.Request req = QotGetIpoList.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getIpoList(req);
            System.out.printf("Send QotGetIpoList: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetIpoList(FTAPI_Conn client, int nSerialNo, QotGetIpoList.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetIpoList failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetIpoList: %s\n", json);
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

*   **Output**

    Send QotGetIpoList: 2
    Receive QotGetIpoList: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "ipoList": [{\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "02219"\
            },\
            "name": "朝聚眼科",\
            "listTime": "2021-07-07",\
            "listTimestamp": 1.6255872E9\
          },\
          "hkExData": {\
            "ipoPriceMin": 9.48,\
            "ipoPriceMax": 10.6,\
            "listPrice": 0.0,\
            "lotSize": 500,\
            "entrancePrice": 5353.41,\
            "isSubscribeStatus": true,\
            "applyEndTime": "2021-06-29",\
            "applyEndTimestamp": 1.6249284E9\
          }\
        }, ... {\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "00606"\
            },\
            "name": "中骏商管",\
            "listTime": "2021-07-02",\
            "listTimestamp": 1.6251552E9\
          },\
          "hkExData": {\
            "ipoPriceMin": 3.7,\
            "ipoPriceMax": 4.6,\
            "listPrice": 0.0,\
            "lotSize": 1000,\
            "entrancePrice": 4646.35,\
            "isSubscribeStatus": false,\
            "applyEndTime": "2021-06-24",\
            "applyEndTimestamp": 1.6244964E9\
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

`Futu::u32_t GetIpoList(const Qot_GetIpoList::Request &stReq);`  
`virtual void OnReply_GetIpoList(Futu::u32_t nSerialNo, const Qot_GetIpoList::Response &stRsp) = 0;`

*   **介绍**
    
    获取指定市场的 IPO 信息
    
*   **参数**
    

    message C2S
    {
    	required int32 market = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
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

> *   行情市场参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // IPO 基本数据
    message BasicIpoData
    {
    	required Qot_Common.Security security = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	required string name = 2; // 股票名称
    	optional string listTime = 3; // 上市日期字符串（格式：yyyy-MM-dd）
    	optional double listTimestamp = 4; // 上市日期时间戳
    };
    
    // A 股 IPO 列表额外数据
    message CNIpoExData 
    {
    	required string applyCode = 1; // 申购代码
    	required int64 issueSize = 2; // 发行总数
    	required int64 onlineIssueSize = 3; // 网上发行量
    	required int64 applyUpperLimit = 4; // 申购上限
    	required int64 applyLimitMarketValue = 5; // 顶格申购需配市值
    	required bool isEstimateIpoPrice = 6; // 是否预估发行价
    	required double ipoPrice = 7; // 发行价 预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double industryPeRate = 8; // 行业市盈率
    	required bool isEstimateWinningRatio = 9; // 是否预估中签率
    	required double winningRatio = 10; // 中签率 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double issuePeRate = 11; // 发行市盈率
    	optional string applyTime = 12; // 申购日期字符串（格式：yyyy-MM-dd）
    	optional double applyTimestamp = 13; // 申购日期时间戳
    	optional string winningTime = 14; // 公布中签日期字符串（格式：yyyy-MM-dd）
    	optional double winningTimestamp = 15; // 公布中签日期时间戳
    	required bool isHasWon = 16; // 是否已经公布中签号
    	repeated WinningNumData winningNumData = 17; // Qot_GetIpoList::WinningNumData 中签号数据，对应 PC 中"公布中签日期的已公布"
    };
    
    // 中签号数据
    message WinningNumData
    {
    	required string winningName = 1; // 分组名
        required string winningInfo = 2; // 中签号信息
    }
    
    // 港股 IPO 列表额外数据
    message HKIpoExData
    {
    	required double ipoPriceMin = 1; // 最低发售价
    	required double ipoPriceMax = 2; // 最高发售价
    	required double listPrice = 3; // 上市价
    	required int32 lotSize = 4; // 每手股数
    	required double entrancePrice = 5; // 入场费
    	required bool isSubscribeStatus = 6; // 是否为认购状态，True-认购中，False-待上市
    	optional string applyEndTime = 7; // 截止认购日期字符串（格式：yyyy-MM-dd）
    	optional double applyEndTimestamp = 8; // 截止认购日期时间戳 因需处理认购手续，富途认购截止时间会早于交易所公布的日期。
    };
    
    // 美股 IPO 列表额外数据
    message USIpoExData  
    {
    	required double ipoPriceMin = 1; // 最低发行价
    	required double ipoPriceMax = 2; // 最高发行价
    	required int64 issueSize = 3; // 发行量
    };
    
    // 新股 IPO 数据
    message IpoData
    {	
    	required BasicIpoData basic = 1; // IPO 基本数据	
    	optional CNIpoExData cnExData = 2; // A 股 IPO 额外数据
    	optional HKIpoExData hkExData = 3; // 港股 IPO 额外数据
    	optional USIpoExData usExData = 4; // 美股 IPO 额外数据
    };
    
    message S2C
    {
    	repeated IpoData ipoList = 1; // 新股 IPO 数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
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
    		Qot_GetIpoList::Request req;
    		Qot_GetIpoList::C2S *c2s = req.mutable_c2s();
    		c2s->set_market(1);
    
            m_GetIpoListSerialNo = m_pQotApi->GetIpoList(req);
            cout << "Request GetIpoList SerialNo: " << m_GetIpoListSerialNo << endl;
    	}
    
    	virtual void OnReply_GetIpoList(Futu::u32_t nSerialNo, const Qot_GetIpoList::Response &stRsp){
            if(nSerialNo == m_GetIpoListSerialNo)
            {
                cout << "OnReply_GetIpoList SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetIpoListSerialNo;
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
    Request GetIpoList SerialNo: 4
    OnReply_GetIpoList SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "ipoList": [\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "06699"\
         },\
         "name": "时代天使",\
         "listTime": "2021-06-16",\
         "listTimestamp": 1623772800\
        },\
        "hkExData": {\
         "ipoPriceMin": 147,\
         "ipoPriceMax": 173,\
         "listPrice": 0,\
         "lotSize": 200,\
         "entrancePrice": 34948.66,\
         "isSubscribeStatus": false,\
         "applyEndTime": "2021-06-08",\
         "applyEndTimestamp": 1623119400\
        }\
       },\
    ...\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "04246"\
         },\
         "name": "政府债券二四零六",\
         "listTime": "2021-06-24",\
         "listTimestamp": 1624464000\
        },\
        "hkExData": {\
         "ipoPriceMin": 100,\
         "ipoPriceMax": 100,\
         "listPrice": 0,\
         "lotSize": 100,\
         "entrancePrice": 10000,\
         "isSubscribeStatus": true,\
         "applyEndTime": "2021-06-11",\
         "applyEndTimestamp": 1623378600\
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

`GetIpoList(req);`

*   **介绍**
    
    获取指定市场的 IPO 信息
    
*   **参数**
    

    message C2S
    {
    	required int32 market = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
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

> *   行情市场参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // IPO 基本数据
    message BasicIpoData
    {
    	required Qot_Common.Security security = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	required string name = 2; // 股票名称
    	optional string listTime = 3; // 上市日期字符串（格式：yyyy-MM-dd）
    	optional double listTimestamp = 4; // 上市日期时间戳
    };
    
    // A 股 IPO 列表额外数据
    message CNIpoExData 
    {
    	required string applyCode = 1; // 申购代码
    	required int64 issueSize = 2; // 发行总数
    	required int64 onlineIssueSize = 3; // 网上发行量
    	required int64 applyUpperLimit = 4; // 申购上限
    	required int64 applyLimitMarketValue = 5; // 顶格申购需配市值
    	required bool isEstimateIpoPrice = 6; // 是否预估发行价
    	required double ipoPrice = 7; // 发行价 预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double industryPeRate = 8; // 行业市盈率
    	required bool isEstimateWinningRatio = 9; // 是否预估中签率
    	required double winningRatio = 10; // 中签率 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double issuePeRate = 11; // 发行市盈率
    	optional string applyTime = 12; // 申购日期字符串（格式：yyyy-MM-dd）
    	optional double applyTimestamp = 13; // 申购日期时间戳
    	optional string winningTime = 14; // 公布中签日期字符串（格式：yyyy-MM-dd）
    	optional double winningTimestamp = 15; // 公布中签日期时间戳
    	required bool isHasWon = 16; // 是否已经公布中签号
    	repeated WinningNumData winningNumData = 17; // Qot_GetIpoList::WinningNumData 中签号数据，对应 PC 中"公布中签日期的已公布"
    };
    
    // 中签号数据
    message WinningNumData
    {
    	required string winningName = 1; // 分组名
        required string winningInfo = 2; // 中签号信息
    }
    
    // 港股 IPO 列表额外数据
    message HKIpoExData
    {
    	required double ipoPriceMin = 1; // 最低发售价
    	required double ipoPriceMax = 2; // 最高发售价
    	required double listPrice = 3; // 上市价
    	required int32 lotSize = 4; // 每手股数
    	required double entrancePrice = 5; // 入场费
    	required bool isSubscribeStatus = 6; // 是否为认购状态，True-认购中，False-待上市
    	optional string applyEndTime = 7; // 截止认购日期字符串（格式：yyyy-MM-dd）
    	optional double applyEndTimestamp = 8; // 截止认购日期时间戳 因需处理认购手续，富途认购截止时间会早于交易所公布的日期。
    };
    
    // 美股 IPO 列表额外数据
    message USIpoExData  
    {
    	required double ipoPriceMin = 1; // 最低发行价
    	required double ipoPriceMax = 2; // 最高发行价
    	required int64 issueSize = 3; // 发行量
    };
    
    // 新股 IPO 数据
    message IpoData
    {	
    	required BasicIpoData basic = 1; // IPO 基本数据	
    	optional CNIpoExData cnExData = 2; // A 股 IPO 额外数据
    	optional HKIpoExData hkExData = 3; // 港股 IPO 额外数据
    	optional USIpoExData usExData = 4; // 美股 IPO 额外数据
    };
    
    message S2C
    {
    	repeated IpoData ipoList = 1; // 新股 IPO 数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetIpoList(){
    	const { RetType } = Common
    	const { QotMarket } = Qot_Common
    	let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
    	let websocket = new ftWebsocket();
    
    	websocket.onlogin = (ret, msg)=>{
    		if (ret) { // 登录成功
    
    			const req = {
    				c2s: {
    					market: QotMarket.QotMarket_US_Security,
    				},
    			};
    
    			websocket.GetIpoList(req)
    			.then((res) => {
    				let { errCode, retMsg, retType,s2c } = res
    				console.log("IpoList: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    IpoList: errCode 0, retMsg , retType 0
    {
      "ipoList": [{\
        "basic": {\
          "security": {\
            "market": 11,\
            "code": "FHLTU"\
          },\
          "name": "Future Health ESG Corp.",\
          "listTime": "2021-09-10",\
          "listTimestamp": 1631246400\
        },\
        "usExData": {\
          "ipoPriceMin": 10,\
          "ipoPriceMax": 10,\
          "issueSize": "20000000"\
        }\
      }, {\
        "basic": {\
          "security": {\
            "market": 11,\
            "code": "FLAG.U"\
          },\
          "name": "FIRST LIGHT ACQUISITION GROUP, INC.",\
          "listTime": "2021-09-10",\
          "listTimestamp": 1631246400\
        },\
        "usExData": {\
          "ipoPriceMin": 10,\
          "ipoPriceMax": 10,\
          "issueSize": "20000000"\
        }\
      }, ..., {\
        "basic": {\
          "security": {\
            "market": 11,\
            "code": "ROXA"\
          },\
          "name": "ROX FINANCIAL LP",\
          "listTime": "2021-09-30",\
          "listTimestamp": 1632974400\
        },\
        "usExData": {\
          "ipoPriceMin": 10,\
          "ipoPriceMax": 10,\
          "issueSize": "8300000"\
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
48  
49  
50  

接口限制

*   每 30 秒内最多请求 10 次获取 IPO 信息接口

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_ipo_list(market)`

*   **介绍**
    
    获取指定市场的 IPO 信息
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | market | [Market](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427) | 市场标识<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>注意：这里不区分沪和深，输入沪或者深都会返回沪深市场的股票 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回 IPO 数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   IPO 数据
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | list\_time | str | 上市日期，美股是预计上市日期<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd |
        | list\_timestamp | float | 上市日期时间戳，美股是预计上市日期时间戳 |
        | apply\_code | str | 申购代码（A 股适用） |
        | issue\_size | int | 发行总数（A 股适用）；发行量（美股适用） |
        | online\_issue\_size | int | 网上发行量（A 股适用） |
        | apply\_upper\_limit | int | 申购上限（A 股适用） |
        | apply\_limit\_market\_value | int | 顶格申购需配市值（A 股适用） |
        | is\_estimate\_ipo\_price | bool | 是否预估发行价（A 股适用） |
        | ipo\_price | float | 发行价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新<br><br>（A 股适用） |
        | industry\_pe\_rate | float | 行业市盈率（A 股适用） |
        | is\_estimate\_winning\_ratio | bool | 是否预估中签率（A 股适用） |
        | winning\_ratio | float | 中签率<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新<br>*   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%<br><br>（A 股适用） |
        | issue\_pe\_rate | float | 发行市盈率（A 股适用） |
        | apply\_time | str | 申购日期字符串<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd<br><br>（A 股适用） |
        | apply\_timestamp | float | 申购日期时间戳（A 股适用） |
        | winning\_time | str | 公布中签日期字符串<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd<br><br>（A 股适用） |
        | winning\_timestamp | float | 公布中签日期时间戳（A 股适用） |
        | is\_has\_won | bool | 是否已经公布中签号（A 股适用） |
        | winning\_num\_data | str | 中签号（A 股适用）<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式类似：  <br>末"五"位数：12345，12346  <br>末"六"位数：123456 |
        | ipo\_price\_min | float | 最低发售价（港股适用）；最低发行价（美股适用） |
        | ipo\_price\_max | float | 最高发售价（港股适用）；最高发行价（美股适用） |
        | list\_price | float | 上市价（港股适用） |
        | lot\_size | int | 每手股数 |
        | entrance\_price | float | 入场费（港股适用） |
        | is\_subscribe\_status | bool | 是否为认购状态<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：认购中  <br>False：待上市 |
        | apply\_end\_time | str | 截止认购日期字符串<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd<br><br>（港股适用） |
        | apply\_end\_timestamp | float | 截止认购日期时间戳 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_ipo_list(Market.HK)
    if ret == RET_OK:
        print(data)
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

*   **Output**

        code      name   list_time  list_timestamp apply_code issue_size online_issue_size apply_upper_limit apply_limit_market_value is_estimate_ipo_price ipo_price industry_pe_rate is_estimate_winning_ratio winning_ratio issue_pe_rate apply_time apply_timestamp winning_time winning_timestamp is_has_won winning_num_data  ipo_price_min  ipo_price_max  list_price  lot_size  entrance_price  is_subscribe_status apply_end_time  apply_end_timestamp
    0  HK.06666  恒大物业  2020-12-02    1.606838e+09        N/A        N/A               N/A               N/A                      N/A                   N/A       N/A              N/A                       N/A           N/A           N/A        N/A             N/A          N/A               N/A        N/A              N/A          8.500           9.75         0.0       500         4924.12                 True     2020-11-26         1.606352e+09
    1  HK.02110  裕勤控股  2020-12-07    1.607270e+09        N/A        N/A               N/A               N/A                      N/A                   N/A       N/A              N/A                       N/A           N/A           N/A        N/A             N/A          N/A               N/A        N/A              N/A          0.225           0.27         0.0     10000         2727.21                 True     2020-11-27         1.606439e+09
    HK.06666
    ['HK.06666', 'HK.02110']
    

1  
2  
3  
4  
5  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html#4600-2)
 Qot\_GetIpoList.proto
--------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取指定市场的 IPO 信息
    
*   **参数**
    

    message C2S
    {
    	required int32 market = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
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

> *   行情市场参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // IPO 基本数据
    message BasicIpoData
    {
    	required Qot_Common.Security security = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	required string name = 2; // 股票名称
    	optional string listTime = 3; // 上市日期字符串（格式：yyyy-MM-dd）
    	optional double listTimestamp = 4; // 上市日期时间戳
    };
    
    // A 股 IPO 列表额外数据
    message CNIpoExData 
    {
    	required string applyCode = 1; // 申购代码
    	required int64 issueSize = 2; // 发行总数
    	required int64 onlineIssueSize = 3; // 网上发行量
    	required int64 applyUpperLimit = 4; // 申购上限
    	required int64 applyLimitMarketValue = 5; // 顶格申购需配市值
    	required bool isEstimateIpoPrice = 6; // 是否预估发行价
    	required double ipoPrice = 7; // 发行价 预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double industryPeRate = 8; // 行业市盈率
    	required bool isEstimateWinningRatio = 9; // 是否预估中签率
    	required double winningRatio = 10; // 中签率 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double issuePeRate = 11; // 发行市盈率
    	optional string applyTime = 12; // 申购日期字符串（格式：yyyy-MM-dd）
    	optional double applyTimestamp = 13; // 申购日期时间戳
    	optional string winningTime = 14; // 公布中签日期字符串（格式：yyyy-MM-dd）
    	optional double winningTimestamp = 15; // 公布中签日期时间戳
    	required bool isHasWon = 16; // 是否已经公布中签号
    	repeated WinningNumData winningNumData = 17; // Qot_GetIpoList::WinningNumData 中签号数据，对应 PC 中"公布中签日期的已公布"
    };
    
    // 中签号数据
    message WinningNumData
    {
    	required string winningName = 1; // 分组名
        required string winningInfo = 2; // 中签号信息
    }
    
    // 港股 IPO 列表额外数据
    message HKIpoExData
    {
    	required double ipoPriceMin = 1; // 最低发售价
    	required double ipoPriceMax = 2; // 最高发售价
    	required double listPrice = 3; // 上市价
    	required int32 lotSize = 4; // 每手股数
    	required double entrancePrice = 5; // 入场费
    	required bool isSubscribeStatus = 6; // 是否为认购状态，True-认购中，False-待上市
    	optional string applyEndTime = 7; // 截止认购日期字符串（格式：yyyy-MM-dd）
    	optional double applyEndTimestamp = 8; // 截止认购日期时间戳 因需处理认购手续，富途认购截止时间会早于交易所公布的日期。
    };
    
    // 美股 IPO 列表额外数据
    message USIpoExData  
    {
    	required double ipoPriceMin = 1; // 最低发行价
    	required double ipoPriceMax = 2; // 最高发行价
    	required int64 issueSize = 3; // 发行量
    };
    
    // 新股 IPO 数据
    message IpoData
    {	
    	required BasicIpoData basic = 1; // IPO 基本数据	
    	optional CNIpoExData cnExData = 2; // A 股 IPO 额外数据
    	optional HKIpoExData hkExData = 3; // 港股 IPO 额外数据
    	optional USIpoExData usExData = 4; // 美股 IPO 额外数据
    };
    
    message S2C
    {
    	repeated IpoData ipoList = 1; // 新股 IPO 数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3217
    

`uint GetIpoList(QotGetIpoList.Request req);`  
`virtual void OnReply_GetIpoList(MMAPI_Conn client, uint nSerialNo, QotGetIpoList.Response rsp);`

*   **介绍**

获取指定市场的 IPO 信息

*   **参数**

    message C2S
    {
    	required int32 market = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
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

> *   行情市场参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // IPO 基本数据
    message BasicIpoData
    {
    	required Qot_Common.Security security = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	required string name = 2; // 股票名称
    	optional string listTime = 3; // 上市日期字符串（格式：yyyy-MM-dd）
    	optional double listTimestamp = 4; // 上市日期时间戳
    };
    
    // A 股 IPO 列表额外数据
    message CNIpoExData 
    {
    	required string applyCode = 1; // 申购代码
    	required int64 issueSize = 2; // 发行总数
    	required int64 onlineIssueSize = 3; // 网上发行量
    	required int64 applyUpperLimit = 4; // 申购上限
    	required int64 applyLimitMarketValue = 5; // 顶格申购需配市值
    	required bool isEstimateIpoPrice = 6; // 是否预估发行价
    	required double ipoPrice = 7; // 发行价 预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double industryPeRate = 8; // 行业市盈率
    	required bool isEstimateWinningRatio = 9; // 是否预估中签率
    	required double winningRatio = 10; // 中签率 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double issuePeRate = 11; // 发行市盈率
    	optional string applyTime = 12; // 申购日期字符串（格式：yyyy-MM-dd）
    	optional double applyTimestamp = 13; // 申购日期时间戳
    	optional string winningTime = 14; // 公布中签日期字符串（格式：yyyy-MM-dd）
    	optional double winningTimestamp = 15; // 公布中签日期时间戳
    	required bool isHasWon = 16; // 是否已经公布中签号
    	repeated WinningNumData winningNumData = 17; // Qot_GetIpoList::WinningNumData 中签号数据，对应 PC 中"公布中签日期的已公布"
    };
    
    // 中签号数据
    message WinningNumData
    {
    	required string winningName = 1; // 分组名
        required string winningInfo = 2; // 中签号信息
    }
    
    // 港股 IPO 列表额外数据
    message HKIpoExData
    {
    	required double ipoPriceMin = 1; // 最低发售价
    	required double ipoPriceMax = 2; // 最高发售价
    	required double listPrice = 3; // 上市价
    	required int32 lotSize = 4; // 每手股数
    	required double entrancePrice = 5; // 入场费
    	required bool isSubscribeStatus = 6; // 是否为认购状态，True-认购中，False-待上市
    	optional string applyEndTime = 7; // 截止认购日期字符串（格式：yyyy-MM-dd）
    	optional double applyEndTimestamp = 8; // 截止认购日期时间戳 因需处理认购手续，富途认购截止时间会早于交易所公布的日期。
    };
    
    // 美股 IPO 列表额外数据
    message USIpoExData  
    {
    	required double ipoPriceMin = 1; // 最低发行价
    	required double ipoPriceMax = 2; // 最高发行价
    	required int64 issueSize = 3; // 发行量
    };
    
    // 新股 IPO 数据
    message IpoData
    {	
    	required BasicIpoData basic = 1; // IPO 基本数据	
    	optional CNIpoExData cnExData = 2; // A 股 IPO 额外数据
    	optional HKIpoExData hkExData = 3; // 港股 IPO 额外数据
    	optional USIpoExData usExData = 4; // 美股 IPO 额外数据
    };
    
    message S2C
    {
    	repeated IpoData ipoList = 1; // 新股 IPO 数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
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
    
            QotGetIpoList.C2S c2s = QotGetIpoList.C2S.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_CNSH_Security)
                    .Build();
            QotGetIpoList.Request req = QotGetIpoList.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetIpoList(req);
            Console.Write("Send QotGetIpoList: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetIpoList(MMAPI_Conn client, uint nSerialNo, QotGetIpoList.Response rsp)
        {
            Console.Write("Reply: QotGetIpoList: {0}\n", nSerialNo);
            if(rsp.S2C.IpoListCount > 0)
            {
                Console.Write("name: {0} \n", rsp.S2C.IpoListList[0].Basic.Name);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826061149989914129
    Send QotGetIpoList: 3
    Reply: QotGetIpoList: 3
    name: 久祺股份
    

1  
2  
3  
4  

`int getIpoList(QotGetIpoList.Request req);`  
`void onReply_GetIpoList(MMAPI_Conn client, int nSerialNo, QotGetIpoList.Response rsp);`

*   **介绍**

获取指定市场的 IPO 信息

*   **参数**

    message C2S
    {
    	required int32 market = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
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

> *   行情市场参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // IPO 基本数据
    message BasicIpoData
    {
    	required Qot_Common.Security security = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	required string name = 2; // 股票名称
    	optional string listTime = 3; // 上市日期字符串（格式：yyyy-MM-dd）
    	optional double listTimestamp = 4; // 上市日期时间戳
    };
    
    // A 股 IPO 列表额外数据
    message CNIpoExData 
    {
    	required string applyCode = 1; // 申购代码
    	required int64 issueSize = 2; // 发行总数
    	required int64 onlineIssueSize = 3; // 网上发行量
    	required int64 applyUpperLimit = 4; // 申购上限
    	required int64 applyLimitMarketValue = 5; // 顶格申购需配市值
    	required bool isEstimateIpoPrice = 6; // 是否预估发行价
    	required double ipoPrice = 7; // 发行价 预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double industryPeRate = 8; // 行业市盈率
    	required bool isEstimateWinningRatio = 9; // 是否预估中签率
    	required double winningRatio = 10; // 中签率 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double issuePeRate = 11; // 发行市盈率
    	optional string applyTime = 12; // 申购日期字符串（格式：yyyy-MM-dd）
    	optional double applyTimestamp = 13; // 申购日期时间戳
    	optional string winningTime = 14; // 公布中签日期字符串（格式：yyyy-MM-dd）
    	optional double winningTimestamp = 15; // 公布中签日期时间戳
    	required bool isHasWon = 16; // 是否已经公布中签号
    	repeated WinningNumData winningNumData = 17; // Qot_GetIpoList::WinningNumData 中签号数据，对应 PC 中"公布中签日期的已公布"
    };
    
    // 中签号数据
    message WinningNumData
    {
    	required string winningName = 1; // 分组名
        required string winningInfo = 2; // 中签号信息
    }
    
    // 港股 IPO 列表额外数据
    message HKIpoExData
    {
    	required double ipoPriceMin = 1; // 最低发售价
    	required double ipoPriceMax = 2; // 最高发售价
    	required double listPrice = 3; // 上市价
    	required int32 lotSize = 4; // 每手股数
    	required double entrancePrice = 5; // 入场费
    	required bool isSubscribeStatus = 6; // 是否为认购状态，True-认购中，False-待上市
    	optional string applyEndTime = 7; // 截止认购日期字符串（格式：yyyy-MM-dd）
    	optional double applyEndTimestamp = 8; // 截止认购日期时间戳 因需处理认购手续，富途认购截止时间会早于交易所公布的日期。
    };
    
    // 美股 IPO 列表额外数据
    message USIpoExData  
    {
    	required double ipoPriceMin = 1; // 最低发行价
    	required double ipoPriceMax = 2; // 最高发行价
    	required int64 issueSize = 3; // 发行量
    };
    
    // 新股 IPO 数据
    message IpoData
    {	
    	required BasicIpoData basic = 1; // IPO 基本数据	
    	optional CNIpoExData cnExData = 2; // A 股 IPO 额外数据
    	optional HKIpoExData hkExData = 3; // 港股 IPO 额外数据
    	optional USIpoExData usExData = 4; // 美股 IPO 额外数据
    };
    
    message S2C
    {
    	repeated IpoData ipoList = 1; // 新股 IPO 数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
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
    
            QotGetIpoList.C2S c2s = QotGetIpoList.C2S.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .build();
            QotGetIpoList.Request req = QotGetIpoList.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getIpoList(req);
            System.out.printf("Send QotGetIpoList: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetIpoList(MMAPI_Conn client, int nSerialNo, QotGetIpoList.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetIpoList failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetIpoList: %s\n", json);
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

*   **Output**

    Send QotGetIpoList: 2
    Receive QotGetIpoList: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "ipoList": [{\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "02219"\
            },\
            "name": "朝聚眼科",\
            "listTime": "2021-07-07",\
            "listTimestamp": 1.6255872E9\
          },\
          "hkExData": {\
            "ipoPriceMin": 9.48,\
            "ipoPriceMax": 10.6,\
            "listPrice": 0.0,\
            "lotSize": 500,\
            "entrancePrice": 5353.41,\
            "isSubscribeStatus": true,\
            "applyEndTime": "2021-06-29",\
            "applyEndTimestamp": 1.6249284E9\
          }\
        }, ... {\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "00606"\
            },\
            "name": "中骏商管",\
            "listTime": "2021-07-02",\
            "listTimestamp": 1.6251552E9\
          },\
          "hkExData": {\
            "ipoPriceMin": 3.7,\
            "ipoPriceMax": 4.6,\
            "listPrice": 0.0,\
            "lotSize": 1000,\
            "entrancePrice": 4646.35,\
            "isSubscribeStatus": false,\
            "applyEndTime": "2021-06-24",\
            "applyEndTimestamp": 1.6244964E9\
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

`moomoo::u32_t GetIpoList(const Qot_GetIpoList::Request &stReq);`  
`virtual void OnReply_GetIpoList(moomoo::u32_t nSerialNo, const Qot_GetIpoList::Response &stRsp) = 0;`

*   **介绍**
    
    获取指定市场的 IPO 信息
    
*   **参数**
    

    message C2S
    {
    	required int32 market = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
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

> *   行情市场参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // IPO 基本数据
    message BasicIpoData
    {
    	required Qot_Common.Security security = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	required string name = 2; // 股票名称
    	optional string listTime = 3; // 上市日期字符串（格式：yyyy-MM-dd）
    	optional double listTimestamp = 4; // 上市日期时间戳
    };
    
    // A 股 IPO 列表额外数据
    message CNIpoExData 
    {
    	required string applyCode = 1; // 申购代码
    	required int64 issueSize = 2; // 发行总数
    	required int64 onlineIssueSize = 3; // 网上发行量
    	required int64 applyUpperLimit = 4; // 申购上限
    	required int64 applyLimitMarketValue = 5; // 顶格申购需配市值
    	required bool isEstimateIpoPrice = 6; // 是否预估发行价
    	required double ipoPrice = 7; // 发行价 预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double industryPeRate = 8; // 行业市盈率
    	required bool isEstimateWinningRatio = 9; // 是否预估中签率
    	required double winningRatio = 10; // 中签率 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double issuePeRate = 11; // 发行市盈率
    	optional string applyTime = 12; // 申购日期字符串（格式：yyyy-MM-dd）
    	optional double applyTimestamp = 13; // 申购日期时间戳
    	optional string winningTime = 14; // 公布中签日期字符串（格式：yyyy-MM-dd）
    	optional double winningTimestamp = 15; // 公布中签日期时间戳
    	required bool isHasWon = 16; // 是否已经公布中签号
    	repeated WinningNumData winningNumData = 17; // Qot_GetIpoList::WinningNumData 中签号数据，对应 PC 中"公布中签日期的已公布"
    };
    
    // 中签号数据
    message WinningNumData
    {
    	required string winningName = 1; // 分组名
        required string winningInfo = 2; // 中签号信息
    }
    
    // 港股 IPO 列表额外数据
    message HKIpoExData
    {
    	required double ipoPriceMin = 1; // 最低发售价
    	required double ipoPriceMax = 2; // 最高发售价
    	required double listPrice = 3; // 上市价
    	required int32 lotSize = 4; // 每手股数
    	required double entrancePrice = 5; // 入场费
    	required bool isSubscribeStatus = 6; // 是否为认购状态，True-认购中，False-待上市
    	optional string applyEndTime = 7; // 截止认购日期字符串（格式：yyyy-MM-dd）
    	optional double applyEndTimestamp = 8; // 截止认购日期时间戳 因需处理认购手续，富途认购截止时间会早于交易所公布的日期。
    };
    
    // 美股 IPO 列表额外数据
    message USIpoExData  
    {
    	required double ipoPriceMin = 1; // 最低发行价
    	required double ipoPriceMax = 2; // 最高发行价
    	required int64 issueSize = 3; // 发行量
    };
    
    // 新股 IPO 数据
    message IpoData
    {	
    	required BasicIpoData basic = 1; // IPO 基本数据	
    	optional CNIpoExData cnExData = 2; // A 股 IPO 额外数据
    	optional HKIpoExData hkExData = 3; // 港股 IPO 额外数据
    	optional USIpoExData usExData = 4; // 美股 IPO 额外数据
    };
    
    message S2C
    {
    	repeated IpoData ipoList = 1; // 新股 IPO 数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
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
    		Qot_GetIpoList::Request req;
    		Qot_GetIpoList::C2S *c2s = req.mutable_c2s();
    		c2s->set_market(1);
    
            m_GetIpoListSerialNo = m_pQotApi->GetIpoList(req);
            cout << "Request GetIpoList SerialNo: " << m_GetIpoListSerialNo << endl;
    	}
    
    	virtual void OnReply_GetIpoList(moomoo::u32_t nSerialNo, const Qot_GetIpoList::Response &stRsp){
            if(nSerialNo == m_GetIpoListSerialNo)
            {
                cout << "OnReply_GetIpoList SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetIpoListSerialNo;
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
    Request GetIpoList SerialNo: 4
    OnReply_GetIpoList SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "ipoList": [\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "06699"\
         },\
         "name": "时代天使",\
         "listTime": "2021-06-16",\
         "listTimestamp": 1623772800\
        },\
        "hkExData": {\
         "ipoPriceMin": 147,\
         "ipoPriceMax": 173,\
         "listPrice": 0,\
         "lotSize": 200,\
         "entrancePrice": 34948.66,\
         "isSubscribeStatus": false,\
         "applyEndTime": "2021-06-08",\
         "applyEndTimestamp": 1623119400\
        }\
       },\
    ...\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "04246"\
         },\
         "name": "政府债券二四零六",\
         "listTime": "2021-06-24",\
         "listTimestamp": 1624464000\
        },\
        "hkExData": {\
         "ipoPriceMin": 100,\
         "ipoPriceMax": 100,\
         "listPrice": 0,\
         "lotSize": 100,\
         "entrancePrice": 10000,\
         "isSubscribeStatus": true,\
         "applyEndTime": "2021-06-11",\
         "applyEndTimestamp": 1623378600\
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

`GetIpoList(req);`

*   **介绍**
    
    获取指定市场的 IPO 信息
    
*   **参数**
    

    message C2S
    {
    	required int32 market = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
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

> *   行情市场参见 [QotMarket](https://openapi.futunn.com/futu-api-doc/quote/quote.html#427)
>     

*   **返回**

    // IPO 基本数据
    message BasicIpoData
    {
    	required Qot_Common.Security security = 1; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	required string name = 2; // 股票名称
    	optional string listTime = 3; // 上市日期字符串（格式：yyyy-MM-dd）
    	optional double listTimestamp = 4; // 上市日期时间戳
    };
    
    // A 股 IPO 列表额外数据
    message CNIpoExData 
    {
    	required string applyCode = 1; // 申购代码
    	required int64 issueSize = 2; // 发行总数
    	required int64 onlineIssueSize = 3; // 网上发行量
    	required int64 applyUpperLimit = 4; // 申购上限
    	required int64 applyLimitMarketValue = 5; // 顶格申购需配市值
    	required bool isEstimateIpoPrice = 6; // 是否预估发行价
    	required double ipoPrice = 7; // 发行价 预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double industryPeRate = 8; // 行业市盈率
    	required bool isEstimateWinningRatio = 9; // 是否预估中签率
    	required double winningRatio = 10; // 中签率 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    	required double issuePeRate = 11; // 发行市盈率
    	optional string applyTime = 12; // 申购日期字符串（格式：yyyy-MM-dd）
    	optional double applyTimestamp = 13; // 申购日期时间戳
    	optional string winningTime = 14; // 公布中签日期字符串（格式：yyyy-MM-dd）
    	optional double winningTimestamp = 15; // 公布中签日期时间戳
    	required bool isHasWon = 16; // 是否已经公布中签号
    	repeated WinningNumData winningNumData = 17; // Qot_GetIpoList::WinningNumData 中签号数据，对应 PC 中"公布中签日期的已公布"
    };
    
    // 中签号数据
    message WinningNumData
    {
    	required string winningName = 1; // 分组名
        required string winningInfo = 2; // 中签号信息
    }
    
    // 港股 IPO 列表额外数据
    message HKIpoExData
    {
    	required double ipoPriceMin = 1; // 最低发售价
    	required double ipoPriceMax = 2; // 最高发售价
    	required double listPrice = 3; // 上市价
    	required int32 lotSize = 4; // 每手股数
    	required double entrancePrice = 5; // 入场费
    	required bool isSubscribeStatus = 6; // 是否为认购状态，True-认购中，False-待上市
    	optional string applyEndTime = 7; // 截止认购日期字符串（格式：yyyy-MM-dd）
    	optional double applyEndTimestamp = 8; // 截止认购日期时间戳 因需处理认购手续，富途认购截止时间会早于交易所公布的日期。
    };
    
    // 美股 IPO 列表额外数据
    message USIpoExData  
    {
    	required double ipoPriceMin = 1; // 最低发行价
    	required double ipoPriceMax = 2; // 最高发行价
    	required int64 issueSize = 3; // 发行量
    };
    
    // 新股 IPO 数据
    message IpoData
    {	
    	required BasicIpoData basic = 1; // IPO 基本数据	
    	optional CNIpoExData cnExData = 2; // A 股 IPO 额外数据
    	optional HKIpoExData hkExData = 3; // 港股 IPO 额外数据
    	optional USIpoExData usExData = 4; // 美股 IPO 额外数据
    };
    
    message S2C
    {
    	repeated IpoData ipoList = 1; // 新股 IPO 数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetIpoList(){
    	const { RetType } = Common
    	const { QotMarket } = Qot_Common
    	let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
    	let websocket = new mmWebsocket();
    
    	websocket.onlogin = (ret, msg)=>{
    		if (ret) { // 登录成功
    
    			const req = {
    				c2s: {
    					market: QotMarket.QotMarket_US_Security,
    				},
    			};
    
    			websocket.GetIpoList(req)
    			.then((res) => {
    				let { errCode, retMsg, retType,s2c } = res
    				console.log("IpoList: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    IpoList: errCode 0, retMsg , retType 0
    {
      "ipoList": [{\
        "basic": {\
          "security": {\
            "market": 11,\
            "code": "FHLTU"\
          },\
          "name": "Future Health ESG Corp.",\
          "listTime": "2021-09-10",\
          "listTimestamp": 1631246400\
        },\
        "usExData": {\
          "ipoPriceMin": 10,\
          "ipoPriceMax": 10,\
          "issueSize": "20000000"\
        }\
      }, {\
        "basic": {\
          "security": {\
            "market": 11,\
            "code": "FLAG.U"\
          },\
          "name": "FIRST LIGHT ACQUISITION GROUP, INC.",\
          "listTime": "2021-09-10",\
          "listTimestamp": 1631246400\
        },\
        "usExData": {\
          "ipoPriceMin": 10,\
          "ipoPriceMax": 10,\
          "issueSize": "20000000"\
        }\
      }, ..., {\
        "basic": {\
          "security": {\
            "market": 11,\
            "code": "ROXA"\
          },\
          "name": "ROX FINANCIAL LP",\
          "listTime": "2021-09-30",\
          "listTimestamp": 1632974400\
        },\
        "usExData": {\
          "ipoPriceMin": 10,\
          "ipoPriceMax": 10,\
          "issueSize": "8300000"\
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
48  
49  
50  

接口限制

*   每 30 秒内最多请求 10 次获取 IPO 信息接口

← [获取静态数据](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html) [获取全局市场状态](https://openapi.futunn.com/futu-api-doc/quote/get-global-state.html)
 →

[获取 IPO 信息](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html)