 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/get-position-list.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/get-position-list.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/get-position-list.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/get-position-list.html)
    

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
    

[#](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html#4697)
 查询持仓
====================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`position_list_query(code='', position_market=TrdMarket.NONE, pl_ratio_min=None, pl_ratio_max=None, trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, refresh_cache=False, asset_category=AssetCategory.NONE)`

*   **介绍**
    
    查询交易业务账户的持仓列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 代码过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   只返回此代码对应的持仓数据。不传则返回所有<br>*   注意：期货持仓的代码过滤，需要传入含具体月份的合约代码，无法通过主连合约代码进行过滤 |
    | position\_market | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 持仓所属市场过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   返回指定市场的持仓数据<br>*   默认状态时，返回所有市场持仓数据 |
    | pl\_ratio\_min | float | 当前盈亏比例下限过滤，仅返回高于此比例的持仓<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例  <br>例如：传入 10，则返回盈亏比例大于 +10% 的持仓 |
    | pl\_ratio\_max | float | 当前盈亏比例上限过滤，低于此比例的会返回<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例  <br>例如：传入 10，返回盈亏比例小于 +10% 的持仓 |
    | trd\_env | [TrdEnv](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | refresh\_cache | bool | 是否刷新缓存<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   True：立即向富途服务器重新请求数据，不使用 OpenD 的缓存，此时会受到接口限频的限制<br>*   False：使用 OpenD 的缓存（特殊情况导致缓存没有及时更新才需要刷新） |
    | asset\_category | [AssetCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4752) | 资产类别<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅对日本券商生效 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回持仓列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   持仓列表
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | position\_side | [PositionSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#2972) | 持仓方向 |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | position\_market | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 持仓所属市场 |
        | qty | float | 持有数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权和期货的单位是“张” |
        | can\_sell\_qty | float | 可用数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>可用数量，是指持有的可平仓的数量。  <br>可用数量=持有数量-冻结数量  <br>期权和期货的单位是“张”。 |
        | currency | [Currency](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8019) | 交易货币 |
        | nominal\_price | float | 市价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分四舍五入 |
        | cost\_price | float | 摊薄成本价（证券账户），平均开仓价（期货账户）<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>建议使用 average\_cost，diluted\_cost 字段获取持仓成本价 |
        | cost\_price\_valid | bool | 成本价是否有效<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：有效  <br>False：无效 |
        | average\_cost | float | 平均成本价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>模拟证券账户不适用  <br>最低OpenD版本要求：9.2.5208 |
        | diluted\_cost | float | 摊薄成本价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货账户不适用  <br>最低OpenD版本要求：9.2.5208 |
        | market\_val | float | 市值<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精度：3 位小数（A 股 2 位小数，期货 0 位小数） |
        | pl\_ratio | float | 盈亏比例（摊薄成本价模式）<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货不适用  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
        | pl\_ratio\_valid | bool | 盈亏比例是否有效<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：有效  <br>False：无效 |
        | pl\_ratio\_avg\_cost | float | 盈亏比例（平均成本价模式）<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>模拟证券账户不适用  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%  <br>最低OpenD版本要求：9.2.5208 |
        | pl\_val | float | 盈亏金额<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精度：3 位小数（A 股 2 位小数） |
        | pl\_val\_valid | bool | 盈亏金额是否有效<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：有效  <br>False：无效 |
        | today\_pl\_val | float | 今日盈亏金额<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只在真实交易环境下有效  <br>精度：3 位小数（A 股 2 位小数，期货 2 位小数） |
        | today\_trd\_val | float | 今日交易金额<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只在真实交易环境下有效  <br>精度：3 位小数（A 股 2 位小数）  <br>期货不适用 |
        | today\_buy\_qty | float | 今日买入总量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只在真实交易环境下有效  <br>精度：3 位小数（A 股 2 位小数）  <br>期货不适用 |
        | today\_buy\_val | float | 今日买入总额<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只在真实交易环境下有效  <br>精度：3 位小数（A 股 2 位小数）  <br>期货不适用 |
        | today\_sell\_qty | float | 今日卖出总量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只在真实交易环境下有效  <br>精度：3 位小数（A 股 2 位小数）  <br>期货不适用 |
        | today\_sell\_val | float | 今日卖出总额<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只在真实交易环境下有效  <br>精度：3 位小数（A 股 2 位小数）  <br>期货不适用 |
        | unrealized\_pl | float | 未实现盈亏<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>模拟证券账户不适用  <br>综合证券账户，返回平均成本价模式下的未实现盈亏金额 |
        | realized\_pl | float | 已实现盈亏<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>模拟证券账户不适用  <br>综合证券账户，返回平均成本价模式下的已实现盈亏金额 |
        | position\_id | int | 持仓ID |
        
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.position_list_query()
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果持仓列表不为空
            print(data['stock_name'][0])  # 获取持仓第一个股票名称
            print(data['stock_name'].values.tolist())  # 转为 list
    else:
        print('position_list_query error: ', data)
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
10  
11  

*   **Output**

           code stock_name position_market    qty  can_sell_qty  cost_price  cost_price_valid average_cost  diluted_cost  market_val  nominal_price  pl_ratio  pl_ratio_valid pl_ratio_avg_cost  pl_val  pl_val_valid today_buy_qty today_buy_val today_pl_val today_trd_val today_sell_qty today_sell_val position_side unrealized_pl realized_pl currency asset_category position_id
    0  HK.01810     小米集团-W              HK  400.0         400.0      53.975              True          53.975        53.975     19820.0          49.55  -8.19824            True            -8.19824    -1770.0          True           0.0           0.0          0.0           0.0            0.0            0.0          LONG           0.0         0.0      HKD      N/A      6596101776329286054
    小米集团-W
    ['小米集团-W']
    

1  
2  
3  
4  

[#](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html#3557)
 Trd\_GetPositionList.proto
----------------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询交易业务账户的持仓列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	optional double filterPLRatioMin = 3; //过滤盈亏百分比下限，高于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional double filterPLRatioMax = 4; //过滤盈亏百分比上限，低于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional bool refreshCache = 5; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 assetCategory = 6; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4752)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Position positionList = 2; //持仓列表
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   持仓结构参见 [Position](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3117)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2102
    

`uint GetPositionList(TrdGetPositionList.Request req);`  
`virtual void OnReply_GetPositionList(FTAPI_Conn client, uint nSerialNo, TrdGetPositionList.Response rsp);`

*   **介绍**
    
    查询交易业务账户的持仓列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	optional double filterPLRatioMin = 3; //过滤盈亏百分比下限，高于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional double filterPLRatioMax = 4; //过滤盈亏百分比上限，低于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional bool refreshCache = 5; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 assetCategory = 6; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4752)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Position positionList = 2; //持仓列表
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   持仓结构参见 [Position](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3117)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program: FTSPI_Trd, FTSPI_Conn {
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
            TrdGetPositionList.C2S c2s = TrdGetPositionList.C2S.CreateBuilder()
                    .SetHeader(header)
                .Build();
            TrdGetPositionList.Request req = TrdGetPositionList.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetPositionList(req);
            Console.Write("Send TrdGetPositionList: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetPositionList(FTAPI_Conn client, uint nSerialNo, TrdGetPositionList.Response rsp)
        {
            Console.Write("Reply: TrdGetPositionList: {0}\n", nSerialNo);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6826813359715968249
    Send TrdGetPositionList: 3
    Reply: TrdGetPositionList: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int getPositionList(TrdGetPositionList.Request req);`  
`void onReply_GetPositionList(FTAPI_Conn client, int nSerialNo, TrdGetPositionList.Response rsp);`

*   **介绍**
    
    查询交易业务账户的持仓列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	optional double filterPLRatioMin = 3; //过滤盈亏百分比下限，高于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional double filterPLRatioMax = 4; //过滤盈亏百分比上限，低于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional bool refreshCache = 5; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 assetCategory = 6; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4752)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Position positionList = 2; //持仓列表
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   持仓结构参见 [Position](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3117)
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
            TrdGetPositionList.C2S c2s = TrdGetPositionList.C2S.newBuilder()
                    .setHeader(header)
                .build();
            TrdGetPositionList.Request req = TrdGetPositionList.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getPositionList(req);
            System.out.printf("Send TrdGetPositionList: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetPositionList(FTAPI_Conn client, int nSerialNo, TrdGetPositionList.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetPositionList failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetPositionList: %s\n", json);
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

    Send TrdGetPositionList: 2
    Receive TrdGetPositionList: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 0,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "positionList": [{\
          "positionID": "804953599703017051",\
          "positionSide": 0,\
          "code": "00700",\
          "name": "腾讯控股",\
          "qty": 100.0,\
          "canSellQty": 100.0,\
          "price": 594.0,\
          "costPrice": 594.0,\
          "val": 59400.0,\
          "plVal": 0.0,\
          "plRatio": 0.0,\
          "secMarket": 1,\
          "dilutedCostPrice": 594.0,\
          "averageCostPrice": 594.0,\
          "averagePlRatio": 0.0\
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

`Futu::u32_t GetPositionList(const Trd_GetPositionList::Request &stReq);`  
`virtual void OnReply_GetPositionList(Futu::u32_t nSerialNo, const Trd_GetPositionList::Response &stRsp) = 0;`

*   **介绍**
    
    查询交易业务账户的持仓列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	optional double filterPLRatioMin = 3; //过滤盈亏百分比下限，高于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional double filterPLRatioMax = 4; //过滤盈亏百分比上限，低于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional bool refreshCache = 5; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 assetCategory = 6; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4752)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Position positionList = 2; //持仓列表
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   持仓结构参见 [Position](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3117)
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
    		Trd_GetPositionList::Request req;
    		Trd_GetPositionList::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
            
            m_GetPositionListSerialNo = m_pTrdApi->GetPositionList(req);
            cout << "Request GetPositionList SerialNo: " << m_GetPositionListSerialNo << endl;
    	}
    
    	virtual void OnReply_GetPositionList(Futu::u32_t nSerialNo, const Trd_GetPositionList::Response &stRsp){
            if(nSerialNo == m_GetPositionListSerialNo)
            {
                cout << "OnReply_GetPositionList SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_GetPositionListSerialNo;
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

*   **Output**

    connect
    Request GetPositionList SerialNo: 4
    OnReply_GetPositionList SerialNo: 4
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
      "positionList": [\
       {\
        "positionID": "806833430706896474",\
        "positionSide": 0,\
        "code": "00700",\
        "name": "腾讯控股",\
        "qty": 300,\
        "canSellQty": 300,\
        "price": 604.5,\
        "costPrice": 611.5,\
        "val": 181350,\
        "plVal": -2100,\
        "plRatio": -0.011447260834015,\
        "secMarket": 1,\
        "dilutedCostPrice": 611.5,\
        "averageCostPrice": 611.5,\
        "averagePlRatio": -0.011447260834015\
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

`GetPositionList(req);`

*   **介绍**
    
    查询交易业务账户的持仓列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	optional double filterPLRatioMin = 3; //过滤盈亏百分比下限，高于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional double filterPLRatioMax = 4; //过滤盈亏百分比上限，低于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional bool refreshCache = 5; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 assetCategory = 6; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4752)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Position positionList = 2; //持仓列表
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   持仓结构参见 [Position](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3117)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetPositionList(){
        const { RetType } = Common
        const { TrdEnv, TrdMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ced92e472b40c92a'];
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
                            },
                        };
    
                        websocket.GetPositionList(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetPositionList: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    GetPositionList: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6684972",
        "trdMarket": 1
      },
      "positionList": [{\
        "positionID": "3411713033831199757",\
        "positionSide": 0,\
        "code": "00700",\
        "name": "腾讯控股",\
        "qty": 1900,\
        "canSellQty": 1900,\
        "price": 479.8,\
        "costPrice": 454.558,\
        "val": 911620,\
        "plVal": 47960,\
        "plRatio": 0.05553111178009899,\
        "secMarket": 1,\
        "dilutedCostPrice": 454.558,\
        "averageCostPrice": 454.558,\
        "averagePlRatio": 0.05553111178009899\
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

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询持仓接口
*   调用此接口，只有在刷新缓存时，才受到限频限制

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`position_list_query(code='', position_market=TrdMarket.NONE, pl_ratio_min=None, pl_ratio_max=None, trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, refresh_cache=False, asset_category=AssetCategory.NONE)`

*   **介绍**
    
    查询交易业务账户的持仓列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 代码过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   只返回此代码对应的持仓数据。不传则返回所有<br>*   注意：期货持仓的代码过滤，需要传入含具体月份的合约代码，无法通过主连合约代码进行过滤 |
    | position\_market | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 持仓所属市场过滤<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   返回指定市场的持仓数据<br>*   默认状态时，返回所有市场持仓数据 |
    | pl\_ratio\_min | float | 当前盈亏比例下限过滤，仅返回高于此比例的持仓<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例  <br>例如：传入 10，则返回盈亏比例大于 +10% 的持仓 |
    | pl\_ratio\_max | float | 当前盈亏比例上限过滤，低于此比例的会返回<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例  <br>例如：传入 20，返回盈亏比例小于 +20% 的持仓 |
    | trd\_env | [TrdEnv](https://openapi.futunn.com/futu-api-doc/trade/trade.html#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | refresh\_cache | bool | 是否刷新缓存<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   True：立即向富途服务器重新请求数据，不使用 OpenD 的缓存，此时会受到接口限频的限制<br>*   False：使用 OpenD 的缓存（特殊情况导致缓存没有及时更新才需要刷新） |
    | asset\_category | [AssetCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4752) | 资产类别<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅对日本券商生效 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回持仓列表 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   持仓列表
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | position\_side | [PositionSide](https://openapi.futunn.com/futu-api-doc/trade/trade.html#2972) | 持仓方向 |
        | code | str | 股票代码 |
        | stock\_name | str | 股票名称 |
        | position\_market | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 持仓所属市场 |
        | qty | float | 持有数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期权和期货的单位是“张” |
        | can\_sell\_qty | float | 可用数量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>可用数量，是指持有的可平仓的数量。  <br>可用数量=持有数量-冻结数量  <br>期权和期货的单位是“张”。 |
        | currency | [Currency](https://openapi.futunn.com/futu-api-doc/trade/trade.html#8019) | 交易货币 |
        | nominal\_price | float | 市价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分四舍五入 |
        | cost\_price | float | 摊薄成本价（证券账户），平均开仓价（期货账户）<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>建议使用 average\_cost，diluted\_cost 字段获取持仓成本价 |
        | cost\_price\_valid | bool | 成本价是否有效<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：有效  <br>False：无效 |
        | average\_cost | float | 平均成本价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>模拟证券账户不适用  <br>最低OpenD版本要求：9.2.5208 |
        | diluted\_cost | float | 摊薄成本价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货账户不适用  <br>最低OpenD版本要求：9.2.5208 |
        | market\_val | float | 市值<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精度：3 位小数（A 股 2 位小数，期货 0 位小数） |
        | pl\_ratio | float | 盈亏比例（摊薄成本价模式）<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货不适用  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
        | pl\_ratio\_valid | bool | 盈亏比例是否有效<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：有效  <br>False：无效 |
        | pl\_ratio\_avg\_cost | float | 盈亏比例（平均成本价模式）<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>模拟证券账户不适用  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%  <br>最低OpenD版本要求：9.2.5208 |
        | pl\_val | float | 盈亏金额<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精度：3 位小数（A 股 2 位小数） |
        | pl\_val\_valid | bool | 盈亏金额是否有效<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：有效  <br>False：无效 |
        | today\_pl\_val | float | 今日盈亏金额<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只在真实交易环境下有效  <br>精度：3 位小数（A 股 2 位小数，期货 2 位小数） |
        | today\_trd\_val | float | 今日交易金额<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只在真实交易环境下有效  <br>精度：3 位小数（A 股 2 位小数）  <br>期货不适用 |
        | today\_buy\_qty | float | 今日买入总量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只在真实交易环境下有效  <br>精度：3 位小数（A 股 2 位小数）  <br>期货不适用 |
        | today\_buy\_val | float | 今日买入总额<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只在真实交易环境下有效  <br>精度：3 位小数（A 股 2 位小数）  <br>期货不适用 |
        | today\_sell\_qty | float | 今日卖出总量<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只在真实交易环境下有效  <br>精度：3 位小数（A 股 2 位小数）  <br>期货不适用 |
        | today\_sell\_val | float | 今日卖出总额<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>只在真实交易环境下有效  <br>精度：3 位小数（A 股 2 位小数）  <br>期货不适用 |
        | unrealized\_pl | float | 未实现盈亏<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>模拟证券账户不适用  <br>综合证券账户，返回平均成本价模式下的未实现盈亏金额 |
        | realized\_pl | float | 已实现盈亏<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>模拟证券账户不适用  <br>综合证券账户，返回平均成本价模式下的已实现盈亏金额 |
        | position\_id | int | 持仓ID |
        
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    ret, data = trd_ctx.position_list_query()
    if ret == RET_OK:
        print(data)
        if data.shape[0] > 0:  # 如果持仓列表不为空
            print(data['stock_name'][0])  # 获取持仓第一个股票名称
            print(data['stock_name'].values.tolist())  # 转为 list
    else:
        print('position_list_query error: ', data)
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
10  
11  

*   **Output**

           code stock_name position_market    qty  can_sell_qty  cost_price  cost_price_valid average_cost  diluted_cost  market_val  nominal_price  pl_ratio  pl_ratio_valid pl_ratio_avg_cost  pl_val  pl_val_valid today_buy_qty today_buy_val today_pl_val today_trd_val today_sell_qty today_sell_val position_side unrealized_pl realized_pl currency asset_category position_id
    0  US.AAPL      苹果                 HK  400.0         400.0      53.975              True          N/A        53.975     19720.0           49.3 -8.661417            True               N/A -1870.0          True           N/A           N/A          N/A           N/A            N/A            N/A          LONG           N/A         N/A      HKD      N/A      6596101776329286054
    苹果
    ['苹果']
    

1  
2  
3  
4  

[#](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html#3557-2)
 Trd\_GetPositionList.proto
------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    查询交易业务账户的持仓列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	optional double filterPLRatioMin = 3; //过滤盈亏百分比下限，高于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional double filterPLRatioMax = 4; //过滤盈亏百分比上限，低于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional bool refreshCache = 5; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 assetCategory = 6; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4752)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Position positionList = 2; //持仓列表
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   持仓结构参见 [Position](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3117)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2102
    

`uint GetPositionList(TrdGetPositionList.Request req);`  
`virtual void OnReply_GetPositionList(FTAPI_Conn client, uint nSerialNo, TrdGetPositionList.Response rsp);`

*   **介绍**
    
    查询交易业务账户的持仓列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	optional double filterPLRatioMin = 3; //过滤盈亏百分比下限，高于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional double filterPLRatioMax = 4; //过滤盈亏百分比上限，低于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional bool refreshCache = 5; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 assetCategory = 6; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4752)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Position positionList = 2; //持仓列表
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   持仓结构参见 [Position](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3117)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program: FTSPI_Trd, FTSPI_Conn {
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
            TrdGetPositionList.C2S c2s = TrdGetPositionList.C2S.CreateBuilder()
                    .SetHeader(header)
                .Build();
            TrdGetPositionList.Request req = TrdGetPositionList.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetPositionList(req);
            Console.Write("Send TrdGetPositionList: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetPositionList(FTAPI_Conn client, uint nSerialNo, TrdGetPositionList.Response rsp)
        {
            Console.Write("Reply: TrdGetPositionList: {0}\n", nSerialNo);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6826813359715968249
    Send TrdGetPositionList: 3
    Reply: TrdGetPositionList: 3
    accID: 281756457888247915
    

1  
2  
3  
4  

`int getPositionList(TrdGetPositionList.Request req);`  
`void onReply_GetPositionList(FTAPI_Conn client, int nSerialNo, TrdGetPositionList.Response rsp);`

*   **介绍**
    
    查询交易业务账户的持仓列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	optional double filterPLRatioMin = 3; //过滤盈亏百分比下限，高于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional double filterPLRatioMax = 4; //过滤盈亏百分比上限，低于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional bool refreshCache = 5; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 assetCategory = 6; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4752)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Position positionList = 2; //持仓列表
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   持仓结构参见 [Position](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3117)
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
            TrdGetPositionList.C2S c2s = TrdGetPositionList.C2S.newBuilder()
                    .setHeader(header)
                .build();
            TrdGetPositionList.Request req = TrdGetPositionList.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getPositionList(req);
            System.out.printf("Send TrdGetPositionList: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetPositionList(FTAPI_Conn client, int nSerialNo, TrdGetPositionList.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetPositionList failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetPositionList: %s\n", json);
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

    Send TrdGetPositionList: 2
    Receive TrdGetPositionList: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 0,
          "accID": "281756457888247915",
          "trdMarket": 1
        },
        "positionList": [{\
          "positionID": "804953599703017051",\
          "positionSide": 0,\
          "code": "00700",\
          "name": "腾讯控股",\
          "qty": 100.0,\
          "canSellQty": 100.0,\
          "price": 594.0,\
          "costPrice": 594.0,\
          "val": 59400.0,\
          "plVal": 0.0,\
          "plRatio": 0.0,\
          "secMarket": 1,\
          "dilutedCostPrice": 594.0,\
          "averageCostPrice": 594.0,\
          "averagePlRatio": 0.0\
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

`Futu::u32_t GetPositionList(const Trd_GetPositionList::Request &stReq);`  
`virtual void OnReply_GetPositionList(Futu::u32_t nSerialNo, const Trd_GetPositionList::Response &stRsp) = 0;`

*   **介绍**
    
    查询交易业务账户的持仓列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	optional double filterPLRatioMin = 3; //过滤盈亏百分比下限，高于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional double filterPLRatioMax = 4; //过滤盈亏百分比上限，低于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional bool refreshCache = 5; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 assetCategory = 6; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4752)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Position positionList = 2; //持仓列表
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   持仓结构参见 [Position](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3117)
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
    		Trd_GetPositionList::Request req;
    		Trd_GetPositionList::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
            
            m_GetPositionListSerialNo = m_pTrdApi->GetPositionList(req);
            cout << "Request GetPositionList SerialNo: " << m_GetPositionListSerialNo << endl;
    	}
    
    	virtual void OnReply_GetPositionList(Futu::u32_t nSerialNo, const Trd_GetPositionList::Response &stRsp){
            if(nSerialNo == m_GetPositionListSerialNo)
            {
                cout << "OnReply_GetPositionList SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_GetPositionListSerialNo;
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

*   **Output**

    connect
    Request GetPositionList SerialNo: 4
    OnReply_GetPositionList SerialNo: 4
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
      "positionList": [\
       {\
        "positionID": "806833430706896474",\
        "positionSide": 0,\
        "code": "00700",\
        "name": "腾讯控股",\
        "qty": 300,\
        "canSellQty": 300,\
        "price": 604.5,\
        "costPrice": 611.5,\
        "val": 181350,\
        "plVal": -2100,\
        "plRatio": -0.011447260834015,\
        "secMarket": 1,\
        "dilutedCostPrice": 611.5,\
        "averageCostPrice": 611.5,\
        "averagePlRatio": -0.011447260834015\
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

`GetPositionList(req);`

*   **介绍**
    
    查询交易业务账户的持仓列表
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.TrdFilterConditions filterConditions = 2; //过滤条件
    	optional double filterPLRatioMin = 3; //过滤盈亏百分比下限，高于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional double filterPLRatioMax = 4; //过滤盈亏百分比上限，低于此比例的会返回。证券账户使用摊薄成本价的盈亏比例，期货账户使用平均成本价的盈亏比例
    	optional bool refreshCache = 5; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 assetCategory = 6; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   过滤条件结构参见 [TrdFilterConditions](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3894)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](https://openapi.futunn.com/futu-api-doc/trade/trade.html#4752)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	repeated Trd_Common.Position positionList = 2; //持仓列表
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

> *   交易公共参数头结构参见 [TrdHeader](https://openapi.futunn.com/futu-api-doc/trade/trade.html#1138)
>     
> *   持仓结构参见 [Position](https://openapi.futunn.com/futu-api-doc/trade/trade.html#3117)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdGetPositionList(){
        const { RetType } = Common
        const { TrdEnv, TrdMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, 'ced92e472b40c92a'];
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
                            },
                        };
    
                        websocket.GetPositionList(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetPositionList: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    GetPositionList: errCode 0, retMsg , retType 0
    {
      "header": {
        "trdEnv": 0,
        "accID": "6684972",
        "trdMarket": 1
      },
      "positionList": [{\
        "positionID": "3411713033831199757",\
        "positionSide": 0,\
        "code": "00700",\
        "name": "腾讯控股",\
        "qty": 1900,\
        "canSellQty": 1900,\
        "price": 479.8,\
        "costPrice": 454.558,\
        "val": 911620,\
        "plVal": 47960,\
        "plRatio": 0.05553111178009899,\
        "secMarket": 1,\
        "dilutedCostPrice": 454.558,\
        "averageCostPrice": 454.558,\
        "averagePlRatio": 0.05553111178009899\
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

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询持仓接口
*   调用此接口，只有在刷新缓存时，才受到限频限制

← [查询最大可买可卖](https://openapi.futunn.com/futu-api-doc/trade/get-max-trd-qtys.html) [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)
 →

[查询持仓](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html)