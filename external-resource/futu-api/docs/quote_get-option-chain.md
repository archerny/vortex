 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-option-chain.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-option-chain.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-option-chain.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-option-chain.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
    *   [行情接口总览](https://openapi.futunn.com/futu-api-doc/quote/overview.html)
        
    *   [行情对象](https://openapi.futunn.com/futu-api-doc/quote/base.html)
        
    *   实时行情
        
    *   基本数据
        
    *   相关衍生品
        
        *   [获取期权链到期日](https://openapi.futunn.com/futu-api-doc/quote/get-option-expiration-date.html)
            
        *   [获取期权链](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html)
            
        *   [筛选窝轮](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html)
            
        *   [获取窝轮和期货列表](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html)
            
        *   [获取期货合约资料](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html)
            
        
    *   全市场筛选
        
    *   个性化
        
    *   [行情定义](https://openapi.futunn.com/futu-api-doc/quote/quote.html)
        
    
*   交易接口
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html#1700)
 获取期权链
====================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_option_chain(code, index_option_type=IndexOptionType.NORMAL, start=None, end=None, option_type=OptionType.ALL, option_cond_type=OptionCondType.ALL, data_filter=None)`

*   **介绍**
    
    通过标的股票查询期权链。此接口仅返回期权链的静态信息，如需获取报价或摆盘等动态信息，请用此接口返回的股票代码，自行 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
     所需要的类型。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 标的股票代码 |
    | index\_option\_type | [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149) | 指数期权类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅对港股指数期权筛选有效，正股、ETFs、美股指数期权可忽略此参数 |
    | start | str | 开始日期，该日期指到期日<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>例如：“2017-08-01” |
    | end | str | 结束日期（包括这一天），该日期指到期日<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>例如：“2017-08-30” |
    | option\_type | [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713) | 期权看涨看跌类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>默认为全部 |
    | option\_cond\_type | [OptionCondType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3227) | 期权价内外类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>默认为全部 |
    | data\_filter | OptionDataFilter | 数据筛选条件<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>默认为不筛选 |
    
    *   start 和 end 的组合如下：
        
        | Start 类型 | End 类型 | 说明  |
        | --- | --- | --- |
        | str | str | start 和 end 分别为指定的日期 |
        | None | str | start 为 end 往前 30 天 |
        | str | None | end 为 start 往后30天 |
        | None | None | start 为当前日期，end 往后 30 天 |
        
    *   OptionDataFilter 字段如下
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | implied\_volatility\_min | float | 隐含波动率过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
        | implied\_volatility\_max | float | 隐含波动率过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
        | delta\_min | float | 希腊值 Delta 过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | delta\_max | float | 希腊值 Delta 过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | gamma\_min | float | 希腊值 Gamma 过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | gamma\_max | float | 希腊值 Gamma 过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | vega\_min | float | 希腊值 Vega 过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | vega\_max | float | 希腊值 Vega 过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | theta\_min | float | 希腊值 Theta 过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | theta\_max | float | 希腊值 Theta 过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | rho\_min | float | 希腊值 Rho 过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | rho\_max | float | 希腊值 Rho 过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | net\_open\_interest\_min | float | 净未平仓合约数过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃 |
        | net\_open\_interest\_max | float | 净未平仓合约数过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃 |
        | open\_interest\_min | float | 未平仓合约数过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃 |
        | open\_interest\_max | float | 未平仓合约数过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃 |
        | vol\_min | float | 成交量过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃 |
        | vol\_max | float | 成交量过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃 |
        

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回期权链数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   期权链数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 名字  |
        | lot\_size | int | 每手股数，期权表示每份合约股数<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>指数期权无该字段 |
        | stock\_type | [SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325) | 股票类型 |
        | option\_type | [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713) | 期权类型 |
        | stock\_owner | str | 标的股 |
        | strike\_time | str | 行权日<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间 |
        | strike\_price | float | 行权价 |
        | suspension | bool | 是否停牌<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：停牌  <br>False：未停牌 |
        | stock\_id | int | 股票 ID |
        | index\_option\_type | [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149) | 指数期权类型 |
        | expiration\_cycle | [ExpirationCycle](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2235) | 交割周期 |
        | option\_standard\_type | [OptionStandardType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8952) | 期权标准类型 |
        | option\_settlement\_mode | [OptionSettlementMode](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1550) | 期权结算方式 |
        
*   **Example**
    

    from futu import *
    import time
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret1, data1 = quote_ctx.get_option_expiration_date(code='HK.00700')
    
    filter1 = OptionDataFilter()
    filter1.delta_min = 0
    filter1.delta_max = 0.1
    
    if ret1 == RET_OK:
        expiration_date_list = data1['strike_time'].values.tolist()
        for date in expiration_date_list:
            ret2, data2 = quote_ctx.get_option_chain(code='HK.00700', start=date, end=date, data_filter=filter1)
            if ret2 == RET_OK:
                print(data2)
                print(data2['code'][0])  # 取第一条的股票代码
                print(data2['code'].values.tolist())  # 转为 list
            else:
                print('error:', data2)
            time.sleep(3)
    else:
        print('error:', data1)
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
20  
21  
22  
23  

*   **Output**

                         code                 name  lot_size stock_type option_type stock_owner strike_time  strike_price  suspension  stock_id index_option_type expiration_cycle option_standard_type option_settlement_mode
    0     HK.TCH210429C350000   腾讯 210429 350.00 购       100       DRVT        CALL    HK.00700  2021-04-29         350.0       False  80235167               N/A        WEEK        STANDARD			N/A        
    1     HK.TCH210429P350000   腾讯 210429 350.00 沽       100       DRVT         PUT    HK.00700  2021-04-29         350.0       False  80235247               N/A        WEEK        STANDARD			N/A        
    2     HK.TCH210429C360000   腾讯 210429 360.00 购       100       DRVT        CALL    HK.00700  2021-04-29         360.0       False  80235163               N/A        WEEK        STANDARD			N/A        
    3     HK.TCH210429P360000   腾讯 210429 360.00 沽       100       DRVT         PUT    HK.00700  2021-04-29         360.0       False  80235246               N/A        WEEK        STANDARD			N/A        
    4     HK.TCH210429C370000   腾讯 210429 370.00 购       100       DRVT        CALL    HK.00700  2021-04-29         370.0       False  80235165               N/A        WEEK        STANDARD			N/A        
    5     HK.TCH210429P370000   腾讯 210429 370.00 沽       100       DRVT         PUT    HK.00700  2021-04-29         370.0       False  80235248               N/A        WEEK        STANDARD			N/A        
    HK.TCH210429C350000
    ['HK.TCH210429C350000', 'HK.TCH210429P350000', 'HK.TCH210429C360000', 'HK.TCH210429P360000', 'HK.TCH210429C370000', 'HK.TCH210429P370000']
    ...
                       code                name  lot_size stock_type option_type stock_owner strike_time  strike_price  suspension  stock_id index_option_type expiration_cycle option_standard_type option_settlement_mode
    0   HK.TCH220330C490000  腾讯 220330 490.00 购       100       DRVT        CALL    HK.00700  2022-03-30         490.0       False  80235143               N/A        WEEK        STANDARD			N/A            
    1   HK.TCH220330P490000  腾讯 220330 490.00 沽       100       DRVT         PUT    HK.00700  2022-03-30         490.0       False  80235193               N/A        WEEK        STANDARD			N/A            
    2   HK.TCH220330C500000  腾讯 220330 500.00 购       100       DRVT        CALL    HK.00700  2022-03-30         500.0       False  80233887               N/A        WEEK        STANDARD			N/A            
    3   HK.TCH220330P500000  腾讯 220330 500.00 沽       100       DRVT         PUT    HK.00700  2022-03-30         500.0       False  80233912               N/A        WEEK        STANDARD			N/A            
    4   HK.TCH220330C510000  腾讯 220330 510.00 购       100       DRVT        CALL    HK.00700  2022-03-30         510.0       False  80233747               N/A        WEEK        STANDARD 			N/A           
    5   HK.TCH220330P510000  腾讯 220330 510.00 沽       100       DRVT         PUT    HK.00700  2022-03-30         510.0       False  80233766               N/A        WEEK        STANDARD 			N/A           
    HK.TCH220330C490000
    ['HK.TCH220330C490000', 'HK.TCH220330P490000', 'HK.TCH220330C500000', 'HK.TCH220330P500000', 'HK.TCH220330C510000', 'HK.TCH220330P510000']
    

1  
2  
3  
4  
5  
6  
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

[#](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html#1122)
 Qot\_GetOptionChain.proto
--------------------------------------------------------------------------------------------------------

*   **介绍**
    
    通过标的股票查询期权链。此接口仅返回期权链的静态信息，如需获取报价或摆盘等动态信息，请用此接口返回的股票代码，自行 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
     所需要的类型。
    
*   **协议 ID**
    
    3209
    
*   **参数**
    

    enum OptionCondType
    {
    	OptionCondType_Unknow = 0;
    	OptionCondType_WithIn = 1; //价内
    	OptionCondType_Outside = 2; //价外
    }
    
    //以下为数据字段筛选，可选字段，不填表示不过滤
    message DataFilter
    {
    	optional double impliedVolatilityMin = 1; //隐含波动率过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double impliedVolatilityMax = 2; //隐含波动率过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double deltaMin = 3; //希腊值 Delta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 4; //希腊值 Delta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double gammaMin = 5; //希腊值 Gamma 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double gammaMax = 6; //希腊值 Gamma 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double vegaMin = 7; //希腊值 Vega 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double vegaMax = 8; //希腊值 Vega 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double thetaMin = 9; //希腊值 Theta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double thetaMax = 10; //希腊值 Theta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double rhoMin = 11; //希腊值 Rho 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double rhoMax = 12; //希腊值 Rho 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double netOpenInterestMin = 13; //净未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double netOpenInterestMax = 14; //净未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double openInterestMin = 15; //未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double openInterestMax = 16; //未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double volMin = 17; //成交量过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double volMax = 18; //成交量过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    }
    
    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 6; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
    	optional int32 type = 2; //Qot_Common.OptionType，期权类型，可选字段，不指定则表示都返回
    	optional int32 condition = 3; //OptionCondType，价内价外，可选字段，不指定则表示都返回
    	required string beginTime = 4; //期权到期日开始时间（格式：yyyy-MM-dd）
    	required string endTime = 5; //期权到期日结束时间，时间跨度最多一个月（格式：yyyy-MM-dd）
    	optional DataFilter dataFilter = 7; //数据字段筛选
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   期权类型枚举参见 [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713)
>     
> *   指数期权类别枚举参见 [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149)
>     

*   **返回**

    message OptionItem
    {
    	optional Qot_Common.SecurityStaticInfo call = 1; //看涨期权，不一定有该字段，由请求条件决定
    	optional Qot_Common.SecurityStaticInfo put = 2; //看跌期权，不一定有该字段，由请求条件决定
    }
    
    message OptionChain
    {
    	required string strikeTime = 1; //行权日（格式：yyyy-MM-dd）
    	repeated OptionItem option = 2; //期权信息
    	optional double strikeTimestamp = 3; //行权日时间戳
    }
    
    message S2C
    {
    	repeated OptionChain optionChain = 1; //期权链
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3209
    

`uint GetOptionChain(QotGetOptionChain.Request req);`  
`virtual void OnReply_GetOptionChain(FTAPI_Conn client, uint nSerialNo, QotGetOptionChain.Response rsp);`

*   **介绍**
    
    通过标的股票查询期权链。此接口仅返回期权链的静态信息，如需获取报价或摆盘等动态信息，请用此接口返回的股票代码，自行 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
     所需要的类型。
    
*   **参数**
    

    enum OptionCondType
    {
    	OptionCondType_Unknow = 0;
    	OptionCondType_WithIn = 1; //价内
    	OptionCondType_Outside = 2; //价外
    }
    
    //以下为数据字段筛选，可选字段，不填表示不过滤
    message DataFilter
    {
    	optional double impliedVolatilityMin = 1; //隐含波动率过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double impliedVolatilityMax = 2; //隐含波动率过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double deltaMin = 3; //希腊值 Delta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 4; //希腊值 Delta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double gammaMin = 5; //希腊值 Gamma 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double gammaMax = 6; //希腊值 Gamma 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double vegaMin = 7; //希腊值 Vega 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double vegaMax = 8; //希腊值 Vega 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double thetaMin = 9; //希腊值 Theta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double thetaMax = 10; //希腊值 Theta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double rhoMin = 11; //希腊值 Rho 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double rhoMax = 12; //希腊值 Rho 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double netOpenInterestMin = 13; //净未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double netOpenInterestMax = 14; //净未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double openInterestMin = 15; //未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double openInterestMax = 16; //未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double volMin = 17; //成交量过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double volMax = 18; //成交量过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    }
    
    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 6; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
    	optional int32 type = 2; //Qot_Common.OptionType，期权类型，可选字段，不指定则表示都返回
    	optional int32 condition = 3; //OptionCondType，价内价外，可选字段，不指定则表示都返回
    	required string beginTime = 4; //期权到期日开始时间（格式：yyyy-MM-dd）
    	required string endTime = 5; //期权到期日结束时间，时间跨度最多一个月（格式：yyyy-MM-dd）
    	optional DataFilter dataFilter = 7; //数据字段筛选
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   期权类型枚举参见 [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713)
>     
> *   指数期权类别枚举参见 [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149)
>     

*   **返回**

    message OptionItem
    {
    	optional Qot_Common.SecurityStaticInfo call = 1; //看涨期权，不一定有该字段，由请求条件决定
    	optional Qot_Common.SecurityStaticInfo put = 2; //看跌期权，不一定有该字段，由请求条件决定
    }
    
    message OptionChain
    {
    	required string strikeTime = 1; //行权日（格式：yyyy-MM-dd）
    	repeated OptionItem option = 2; //期权信息
    	optional double strikeTimestamp = 3; //行权日时间戳
    }
    
    message S2C
    {
    	repeated OptionChain optionChain = 1; //期权链
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
            QotGetOptionChain.C2S c2s = QotGetOptionChain.C2S.CreateBuilder()
                .SetOwner(sec)
                .SetBeginTime("2020-11-01")
                .SetEndTime("2020-12-01")
                .Build();
            QotGetOptionChain.Request req = QotGetOptionChain.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetOptionChain(req);
            Console.Write("Send QotGetOptionChain: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetOptionChain(FTAPI_Conn client, uint nSerialNo, QotGetOptionChain.Response rsp)
        {
            Console.Write("Reply: QotGetOptionChain: {0}\n", nSerialNo);
            Console.Write("strikeTime: {0}, name: {1} \n", 
                rsp.S2C.OptionChainList[0].StrikeTime,
                rsp.S2C.OptionChainList[0].OptionList[0].Call.Basic.Name);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825705573658441031
    Send QotGetOptionChain: 3
    Reply: QotGetOptionChain: 3
    strikeTime: 2021-07-29, name: 腾讯 210729 400.00 购
    

1  
2  
3  
4  

`int getOptionChain(QotGetOptionChain.Request req);`  
`void onReply_GetOptionChain(FTAPI_Conn client, int nSerialNo, QotGetOptionChain.Response rsp);`

*   **介绍**
    
    通过标的股票查询期权链。此接口仅返回期权链的静态信息，如需获取报价或摆盘等动态信息，请用此接口返回的股票代码，自行 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
     所需要的类型。
    
*   **参数**
    

    enum OptionCondType
    {
    	OptionCondType_Unknow = 0;
    	OptionCondType_WithIn = 1; //价内
    	OptionCondType_Outside = 2; //价外
    }
    
    //以下为数据字段筛选，可选字段，不填表示不过滤
    message DataFilter
    {
    	optional double impliedVolatilityMin = 1; //隐含波动率过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double impliedVolatilityMax = 2; //隐含波动率过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double deltaMin = 3; //希腊值 Delta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 4; //希腊值 Delta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double gammaMin = 5; //希腊值 Gamma 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double gammaMax = 6; //希腊值 Gamma 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double vegaMin = 7; //希腊值 Vega 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double vegaMax = 8; //希腊值 Vega 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double thetaMin = 9; //希腊值 Theta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double thetaMax = 10; //希腊值 Theta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double rhoMin = 11; //希腊值 Rho 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double rhoMax = 12; //希腊值 Rho 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double netOpenInterestMin = 13; //净未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double netOpenInterestMax = 14; //净未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double openInterestMin = 15; //未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double openInterestMax = 16; //未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double volMin = 17; //成交量过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double volMax = 18; //成交量过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    }
    
    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 6; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
    	optional int32 type = 2; //Qot_Common.OptionType，期权类型，可选字段，不指定则表示都返回
    	optional int32 condition = 3; //OptionCondType，价内价外，可选字段，不指定则表示都返回
    	required string beginTime = 4; //期权到期日开始时间（格式：yyyy-MM-dd）
    	required string endTime = 5; //期权到期日结束时间，时间跨度最多一个月（格式：yyyy-MM-dd）
    	optional DataFilter dataFilter = 7; //数据字段筛选
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   期权类型枚举参见 [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713)
>     
> *   指数期权类别枚举参见 [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149)
>     

*   **返回**

    message OptionItem
    {
    	optional Qot_Common.SecurityStaticInfo call = 1; //看涨期权，不一定有该字段，由请求条件决定
    	optional Qot_Common.SecurityStaticInfo put = 2; //看跌期权，不一定有该字段，由请求条件决定
    }
    
    message OptionChain
    {
    	required string strikeTime = 1; //行权日（格式：yyyy-MM-dd）
    	repeated OptionItem option = 2; //期权信息
    	optional double strikeTimestamp = 3; //行权日时间戳
    }
    
    message S2C
    {
    	repeated OptionChain optionChain = 1; //期权链
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
            QotGetOptionChain.C2S c2s = QotGetOptionChain.C2S.newBuilder()
                    .setOwner(sec)
                    .setBeginTime("2021-06-01")
                    .setEndTime("2021-07-01")
                .build();
            QotGetOptionChain.Request req = QotGetOptionChain.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getOptionChain(req);
            System.out.printf("Send QotGetOptionChain: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetOptionChain(FTAPI_Conn client, int nSerialNo, QotGetOptionChain.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetOptionChain failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetOptionChain: %s\n", json);
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

*   **Output**

    Send QotGetOptionChain: 2
    Receive QotGetOptionChain: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "optionChain": [{\
          "strikeTime": "2021-06-29",\
          "option": [{\
            "call": {\
              "basic": {\
                "security": {\
                  "market": 1,\
                  "code": "TCH210629C295000"\
                },\
                "id": "80143386",\
                "lotSize": 100,\
                "secType": 8,\
                "name": "腾讯 210629 295.00 购",\
                "listTime": "",\
                "delisting": false\
              },\
              "optionExData": {\
                "type": 1,\
                "owner": {\
                  "market": 1,\
                  "code": "00700"\
                },\
                "strikeTime": "2021-06-29",\
                "strikePrice": 295.0,\
                "suspend": false,\
                "market": "",\
                "strikeTimestamp": 1.624896E9,\
                "expirationCycle": 1,\
                "optionStandardType": 1,\
                "optionSettlementMode": 2,\
              }\
            },\
            "put": {\
              "basic": {\
                "security": {\
                  "market": 1,\
                  "code": "TCH210629P295000"\
                },\
                "id": "80143074",\
                "lotSize": 100,\
                "secType": 8,\
                "name": "腾讯 210629 295.00 沽",\
                "listTime": "",\
                "delisting": false\
              },\
              "optionExData": {\
                "type": 2,\
                "owner": {\
                  "market": 1,\
                  "code": "00700"\
                },\
                "strikeTime": "2021-06-29",\
                "strikePrice": 295.0,\
                "suspend": false,\
                "market": "",\
                "strikeTimestamp": 1.624896E9,\
                "expirationCycle": 1,\
                "optionStandardType": 1,\
                "optionSettlementMode": 2,\
              }\
            }\
          }, ... {\
            "call": {\
              "basic": {\
                "security": {\
                  "market": 1,\
                  "code": "TCH210629C1050000"\
                },\
                "id": "80223489",\
                "lotSize": 100,\
                "secType": 8,\
                "name": "腾讯 210629 1050.00 购",\
                "listTime": "",\
                "delisting": false\
              },\
              "optionExData": {\
                "type": 1,\
                "owner": {\
                  "market": 1,\
                  "code": "00700"\
                },\
                "strikeTime": "2021-06-29",\
                "strikePrice": 1050.0,\
                "suspend": false,\
                "market": "",\
                "strikeTimestamp": 1.624896E9,\
                "expirationCycle": 1,\
                "optionStandardType": 1,\
                "optionSettlementMode": 2,\
              }\
            },\
            "put": {\
              "basic": {\
                "security": {\
                  "market": 1,\
                  "code": "TCH210629P1050000"\
                },\
                "id": "80223488",\
                "lotSize": 100,\
                "secType": 8,\
                "name": "腾讯 210629 1050.00 沽",\
                "listTime": "",\
                "delisting": false\
              },\
              "optionExData": {\
                "type": 2,\
                "owner": {\
                  "market": 1,\
                  "code": "00700"\
                },\
                "strikeTime": "2021-06-29",\
                "strikePrice": 1050.0,\
                "suspend": false,\
                "market": "",\
                "strikeTimestamp": 1.624896E9,\
                "expirationCycle": 1,\
                "optionStandardType": 1,\
                "optionSettlementMode": 2,\
              }\
            }\
          }],\
          "strikeTimestamp": 1.624896E9\
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
101  
102  
103  
104  
105  
106  
107  
108  
109  
110  
111  
112  
113  
114  
115  
116  
117  
118  
119  
120  
121  
122  
123  
124  
125  
126  
127  
128  
129  
130  
131  

`Futu::u32_t GetOptionChain(const Qot_GetOptionChain::Request &stReq);`  
`virtual void OnReply_GetOptionChain(Futu::u32_t nSerialNo, const Qot_GetOptionChain::Response &stRsp) = 0;`

*   **介绍**
    
    通过标的股票查询期权链。此接口仅返回期权链的静态信息，如需获取报价或摆盘等动态信息，请用此接口返回的股票代码，自行 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
     所需要的类型。
    
*   **参数**
    

    enum OptionCondType
    {
    	OptionCondType_Unknow = 0;
    	OptionCondType_WithIn = 1; //价内
    	OptionCondType_Outside = 2; //价外
    }
    
    //以下为数据字段筛选，可选字段，不填表示不过滤
    message DataFilter
    {
    	optional double impliedVolatilityMin = 1; //隐含波动率过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double impliedVolatilityMax = 2; //隐含波动率过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double deltaMin = 3; //希腊值 Delta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 4; //希腊值 Delta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double gammaMin = 5; //希腊值 Gamma 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double gammaMax = 6; //希腊值 Gamma 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double vegaMin = 7; //希腊值 Vega 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double vegaMax = 8; //希腊值 Vega 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double thetaMin = 9; //希腊值 Theta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double thetaMax = 10; //希腊值 Theta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double rhoMin = 11; //希腊值 Rho 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double rhoMax = 12; //希腊值 Rho 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double netOpenInterestMin = 13; //净未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double netOpenInterestMax = 14; //净未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double openInterestMin = 15; //未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double openInterestMax = 16; //未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double volMin = 17; //成交量过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double volMax = 18; //成交量过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    }
    
    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 6; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
    	optional int32 type = 2; //Qot_Common.OptionType，期权类型，可选字段，不指定则表示都返回
    	optional int32 condition = 3; //OptionCondType，价内价外，可选字段，不指定则表示都返回
    	required string beginTime = 4; //期权到期日开始时间（格式：yyyy-MM-dd）
    	required string endTime = 5; //期权到期日结束时间，时间跨度最多一个月（格式：yyyy-MM-dd）
    	optional DataFilter dataFilter = 7; //数据字段筛选
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   期权类型枚举参见 [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713)
>     
> *   指数期权类别枚举参见 [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149)
>     

*   **返回**

    message OptionItem
    {
    	optional Qot_Common.SecurityStaticInfo call = 1; //看涨期权，不一定有该字段，由请求条件决定
    	optional Qot_Common.SecurityStaticInfo put = 2; //看跌期权，不一定有该字段，由请求条件决定
    }
    
    message OptionChain
    {
    	required string strikeTime = 1; //行权日（格式：yyyy-MM-dd）
    	repeated OptionItem option = 2; //期权信息
    	optional double strikeTimestamp = 3; //行权日时间戳
    }
    
    message S2C
    {
    	repeated OptionChain optionChain = 1; //期权链
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
    		Qot_GetOptionChain::Request req;
    		Qot_GetOptionChain::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_owner();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->set_begintime("2021-06-07");
    		c2s->set_endtime("2021-07-01");
    
        m_GetOptionChainSerialNo = m_pQotApi->GetOptionChain(req);
        cout << "Request GetOptionChain SerialNo: " << m_GetOptionChainSerialNo << endl;
    	}
    
    	virtual void OnReply_GetOptionChain(Futu::u32_t nSerialNo, const Qot_GetOptionChain::Response &stRsp){
        if(nSerialNo == m_GetOptionChainSerialNo)
        {
            cout << "OnReply_GetOptionChain SerialNo: " << nSerialNo << endl;
            // 解析内部结构打印出来
            // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
            string resp_str;
            ProtoBufToBodyData(stRsp, resp_str);
            cout << UTF8ToLocal(resp_str) << endl;
        }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
      Futu::u32_t m_GetOptionChainSerialNo;
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
    Request GetOptionChain SerialNo: 4
    OnReply_GetOptionChain SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "optionChain": [\
       {\
        "strikeTime": "2021-06-29",\
        "option": [\
         {\
          "call": {\
           "basic": {\
            "security": {\
             "market": 1,\
             "code": "TCH210629C295000"\
            },\
            "id": "80143386",\
            "lotSize": 100,\
            "secType": 8,\
            "name": "腾讯 210629 295.00 购",\
            "listTime": "",\
            "delisting": false\
           },\
           "optionExData": {\
            "type": 1,\
            "owner": {\
             "market": 1,\
             "code": "00700"\
            },\
            "strikeTime": "2021-06-29",\
            "strikePrice": 295,\
            "suspend": false,\
            "market": "",\
            "strikeTimestamp": 1624896000,\
            "expirationCycle": 1,\
            "optionStandardType": 1,\
            "optionSettlementMode": 2,\
           }\
          },\
          "put": {\
           "basic": {\
            "security": {\
             "market": 1,\
             "code": "TCH210629P295000"\
            },\
            "id": "80143074",\
            "lotSize": 100,\
            "secType": 8,\
            "name": "腾讯 210629 295.00 沽",\
            "listTime": "",\
            "delisting": false\
           },\
           "optionExData": {\
            "type": 2,\
            "owner": {\
             "market": 1,\
             "code": "00700"\
            },\
            "strikeTime": "2021-06-29",\
            "strikePrice": 295,\
            "suspend": false,\
            "market": "",\
            "strikeTimestamp": 1624896000,\
            "expirationCycle": 1,\
            "optionStandardType": 1,\
            "optionSettlementMode": 2,\
           }\
          }\
         },\
    ...\
         {\
          "call": {\
           "basic": {\
            "security": {\
             "market": 1,\
             "code": "TCH210629C1050000"\
            },\
            "id": "80223489",\
            "lotSize": 100,\
            "secType": 8,\
            "name": "腾讯 210629 1050.00 购",\
            "listTime": "",\
            "delisting": false\
           },\
           "optionExData": {\
            "type": 1,\
            "owner": {\
             "market": 1,\
             "code": "00700"\
            },\
            "strikeTime": "2021-06-29",\
            "strikePrice": 1050,\
            "suspend": false,\
            "market": "",\
            "strikeTimestamp": 1624896000,\
            "expirationCycle": 1,\
            "optionStandardType": 1,\
            "optionSettlementMode": 2,\
           }\
          },\
          "put": {\
           "basic": {\
            "security": {\
             "market": 1,\
             "code": "TCH210629P1050000"\
            },\
            "id": "80223488",\
            "lotSize": 100,\
            "secType": 8,\
            "name": "腾讯 210629 1050.00 沽",\
            "listTime": "",\
            "delisting": false\
           },\
           "optionExData": {\
            "type": 2,\
            "owner": {\
             "market": 1,\
             "code": "00700"\
            },\
            "strikeTime": "2021-06-29",\
            "strikePrice": 1050,\
            "suspend": false,\
            "market": "",\
            "strikeTimestamp": 1624896000,\
            "expirationCycle": 1,\
            "optionStandardType": 1,\
            "optionSettlementMode": 2,\
           }\
          }\
         }\
        ],\
        "strikeTimestamp": 1624896000\
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
101  
102  
103  
104  
105  
106  
107  
108  
109  
110  
111  
112  
113  
114  
115  
116  
117  
118  
119  
120  
121  
122  
123  
124  
125  
126  
127  
128  
129  
130  
131  
132  
133  
134  
135  
136  
137  
138  
139  
140  

`GetOptionChain(req);`

*   **介绍**
    
    通过标的股票查询期权链。此接口仅返回期权链的静态信息，如需获取报价或摆盘等动态信息，请用此接口返回的股票代码，自行 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
     所需要的类型。
    
*   **参数**
    

    enum OptionCondType
    {
    	OptionCondType_Unknow = 0;
    	OptionCondType_WithIn = 1; //价内
    	OptionCondType_Outside = 2; //价外
    }
    
    //以下为数据字段筛选，可选字段，不填表示不过滤
    message DataFilter
    {
    	optional double impliedVolatilityMin = 1; //隐含波动率过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double impliedVolatilityMax = 2; //隐含波动率过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double deltaMin = 3; //希腊值 Delta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 4; //希腊值 Delta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double gammaMin = 5; //希腊值 Gamma 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double gammaMax = 6; //希腊值 Gamma 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double vegaMin = 7; //希腊值 Vega 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double vegaMax = 8; //希腊值 Vega 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double thetaMin = 9; //希腊值 Theta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double thetaMax = 10; //希腊值 Theta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double rhoMin = 11; //希腊值 Rho 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double rhoMax = 12; //希腊值 Rho 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double netOpenInterestMin = 13; //净未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double netOpenInterestMax = 14; //净未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double openInterestMin = 15; //未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double openInterestMax = 16; //未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double volMin = 17; //成交量过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double volMax = 18; //成交量过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    }
    
    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 6; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
    	optional int32 type = 2; //Qot_Common.OptionType，期权类型，可选字段，不指定则表示都返回
    	optional int32 condition = 3; //OptionCondType，价内价外，可选字段，不指定则表示都返回
    	required string beginTime = 4; //期权到期日开始时间（格式：yyyy-MM-dd）
    	required string endTime = 5; //期权到期日结束时间，时间跨度最多一个月（格式：yyyy-MM-dd）
    	optional DataFilter dataFilter = 7; //数据字段筛选
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   期权类型枚举参见 [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713)
>     
> *   指数期权类别枚举参见 [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149)
>     

*   **返回**

    message OptionItem
    {
    	optional Qot_Common.SecurityStaticInfo call = 1; //看涨期权，不一定有该字段，由请求条件决定
    	optional Qot_Common.SecurityStaticInfo put = 2; //看跌期权，不一定有该字段，由请求条件决定
    }
    
    message OptionChain
    {
    	required string strikeTime = 1; //行权日（格式：yyyy-MM-dd）
    	repeated OptionItem option = 2; //期权信息
    	optional double strikeTimestamp = 3; //行权日时间戳
    }
    
    message S2C
    {
    	repeated OptionChain optionChain = 1; //期权链
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetOptionChain(){
        const { RetType } = Common
        const {  QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        owner:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                        beginTime: "2021-09-01",
                        endTime: "2021-09-30",
                    },
                };
    
                websocket.GetOptionChain(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("OptionChain: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    OptionChain: errCode 0, retMsg , retType 0
    {
      "optionChain": [{\
        "strikeTime": "2021-09-29",\
        "option": [{\
          "call": {\
            "basic": {\
              "security": {\
                "market": 1,\
                "code": "TCH210929C300000"\
              },\
              "id": "80287116",\
              "lotSize": 100,\
              "secType": 8,\
              "name": "腾讯 210929 300.00 购",\
              "listTime": "",\
              "delisting": false\
            },\
            "optionExData": {\
              "type": 1,\
              "owner": {\
                "market": 1,\
                "code": "00700"\
              },\
              "strikeTime": "2021-09-29",\
              "strikePrice": 300,\
              "suspend": false,\
              "market": "",\
              "strikeTimestamp": 1632844800,\
              "expirationCycle": 1,\
              "optionStandardType": 1,\
              "optionSettlementMode": 2,\
            }\
          },\
          "put": {\
            "basic": {\
              "security": {\
                "market": 1,\
                "code": "TCH210929P300000"\
              },\
              "id": "80287124",\
              "lotSize": 100,\
              "secType": 8,\
              "name": "腾讯 210929 300.00 沽",\
              "listTime": "",\
              "delisting": false\
            },\
            "optionExData": {\
              "type": 2,\
              "owner": {\
                "market": 1,\
                "code": "00700"\
              },\
              "strikeTime": "2021-09-29",\
              "strikePrice": 300,\
              "suspend": false,\
              "market": "",\
              "strikeTimestamp": 1632844800,\
              "expirationCycle": 1,\
              "optionStandardType": 1,\
              "optionSettlementMode": 2,\
            }\
          }\
        }, ..., {\
          "call": {\
            "basic": {\
              "security": {\
                "market": 1,\
                "code": "TCH210929C950000"\
              },\
              "id": "80215136",\
              "lotSize": 100,\
              "secType": 8,\
              "name": "腾讯 210929 950.00 购",\
              "listTime": "",\
              "delisting": false\
            },\
            "optionExData": {\
              "type": 1,\
              "owner": {\
                "market": 1,\
                "code": "00700"\
              },\
              "strikeTime": "2021-09-29",\
              "strikePrice": 950,\
              "suspend": false,\
              "market": "",\
              "strikeTimestamp": 1632844800,\
              "expirationCycle": 1,\
              "optionStandardType": 1,\
              "optionSettlementMode": 2,\
            }\
          },\
          "put": {\
            "basic": {\
              "security": {\
                "market": 1,\
                "code": "TCH210929P950000"\
              },\
              "id": "80215157",\
              "lotSize": 100,\
              "secType": 8,\
              "name": "腾讯 210929 950.00 沽",\
              "listTime": "",\
              "delisting": false\
            },\
            "optionExData": {\
              "type": 2,\
              "owner": {\
                "market": 1,\
                "code": "00700"\
              },\
              "strikeTime": "2021-09-29",\
              "strikePrice": 950,\
              "suspend": false,\
              "market": "",\
              "strikeTimestamp": 1632844800,\
              "expirationCycle": 1,\
              "optionStandardType": 1,\
              "optionSettlementMode": 2,\
            }\
          }\
        }],\
        "strikeTimestamp": 1632844800\
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
101  
102  
103  
104  
105  
106  
107  
108  
109  
110  
111  
112  
113  
114  
115  
116  
117  
118  
119  
120  
121  
122  
123  
124  
125  
126  
127  

接口限制

*   每 30 秒内最多请求 10 次获取期权链接口
*   传入的时间跨度上限为 30 天

提示

*   此接口不支持查询已过期的期权链，**结束日期** 参数请输入今天或未来的日期
*   Open interest (OI) 数据每日更新，更新时点取决于具体交易所。美股期权在盘前时段更新，港股期权在盘后更新。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_option_chain(code, index_option_type=IndexOptionType.NORMAL, start=None, end=None, option_type=OptionType.ALL, option_cond_type=OptionCondType.ALL, data_filter=None)`

*   **介绍**
    
    通过标的股票查询期权链。此接口仅返回期权链的静态信息，如需获取报价或摆盘等动态信息，请用此接口返回的股票代码，自行 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
     所需要的类型。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code | str | 标的股票代码 |
    | index\_option\_type | [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149) | 指数期权类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅对港股指数期权筛选有效，正股、ETFs、美股指数期权可忽略此参数 |
    | start | str | 开始日期，该日期指到期日<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>例如：“2017-08-01” |
    | end | str | 结束日期（包括这一天），该日期指到期日<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>例如：“2017-08-30” |
    | option\_type | [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713) | 期权看涨看跌类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>默认为全部 |
    | option\_cond\_type | [OptionCondType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3227) | 期权价内外类型<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>默认为全部 |
    | data\_filter | OptionDataFilter | 数据筛选条件<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>默认为不筛选 |
    
    *   start 和 end 的组合如下：
        
        | Start 类型 | End 类型 | 说明  |
        | --- | --- | --- |
        | str | str | start 和 end 分别为指定的日期 |
        | None | str | start 为 end 往前 30 天 |
        | str | None | end 为 start 往后30天 |
        | None | None | start 为当前日期，end 往后 30 天 |
        
    *   OptionDataFilter 字段如下
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | implied\_volatility\_min | float | 隐含波动率过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
        | implied\_volatility\_max | float | 隐含波动率过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
        | delta\_min | float | 希腊值 Delta 过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | delta\_max | float | 希腊值 Delta 过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | gamma\_min | float | 希腊值 Gamma 过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | gamma\_max | float | 希腊值 Gamma 过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | vega\_min | float | 希腊值 Vega 过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | vega\_max | float | 希腊值 Vega 过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | theta\_min | float | 希腊值 Theta 过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | theta\_max | float | 希腊值 Theta 过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | rho\_min | float | 希腊值 Rho 过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | rho\_max | float | 希腊值 Rho 过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 3 位，超出部分会被舍弃 |
        | net\_open\_interest\_min | float | 净未平仓合约数过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃 |
        | net\_open\_interest\_max | float | 净未平仓合约数过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃 |
        | open\_interest\_min | float | 未平仓合约数过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃 |
        | open\_interest\_max | float | 未平仓合约数过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃 |
        | vol\_min | float | 成交量过滤起点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃 |
        | vol\_max | float | 成交量过滤终点<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>精确到小数点后 0 位，超出部分会被舍弃 |
        

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回期权链数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   期权链数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 名字  |
        | lot\_size | int | 每手股数，期权表示每份合约股数<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>指数期权无该字段 |
        | stock\_type | [SecurityType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3325) | 股票类型 |
        | option\_type | [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713) | 期权类型 |
        | stock\_owner | str | 标的股 |
        | strike\_time | str | 行权日<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间 |
        | strike\_price | float | 行权价 |
        | suspension | bool | 是否停牌<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：停牌  <br>False：未停牌 |
        | stock\_id | int | 股票 ID |
        | index\_option\_type | [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149) | 指数期权类型 |
        | expiration\_cycle | [ExpirationCycle](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2235) | 交割周期 |
        | option\_standard\_type | [OptionStandardType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8952) | 期权标准类型 |
        | option\_settlement\_mode | [OptionSettlementMode](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1550) | 期权结算方式 |
        
*   **Example**
    

    from moomoo import *
    import time
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    ret1, data1 = quote_ctx.get_option_expiration_date(code='HK.00700')
    
    filter1 = OptionDataFilter()
    filter1.delta_min = 0
    filter1.delta_max = 0.1
    
    if ret1 == RET_OK:
        expiration_date_list = data1['strike_time'].values.tolist()
        for date in expiration_date_list:
            ret2, data2 = quote_ctx.get_option_chain(code='HK.00700', start=date, end=date, data_filter=filter1)
            if ret2 == RET_OK:
                print(data2)
                print(data2['code'][0])  # 取第一条的股票代码
                print(data2['code'].values.tolist())  # 转为 list
            else:
                print('error:', data2)
            time.sleep(3)
    else:
        print('error:', data1)
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
20  
21  
22  
23  

*   **Output**

                         code                 name  lot_size stock_type option_type stock_owner strike_time  strike_price  suspension  stock_id index_option_type expiration_cycle option_standard_type option_settlement_mode
    0     HK.TCH210429C350000   腾讯 210429 350.00 购       100       DRVT        CALL    HK.00700  2021-04-29         350.0       False  80235167               N/A        WEEK        STANDARD			N/A        
    1     HK.TCH210429P350000   腾讯 210429 350.00 沽       100       DRVT         PUT    HK.00700  2021-04-29         350.0       False  80235247               N/A        WEEK        STANDARD			N/A        
    2     HK.TCH210429C360000   腾讯 210429 360.00 购       100       DRVT        CALL    HK.00700  2021-04-29         360.0       False  80235163               N/A        WEEK        STANDARD			N/A        
    3     HK.TCH210429P360000   腾讯 210429 360.00 沽       100       DRVT         PUT    HK.00700  2021-04-29         360.0       False  80235246               N/A        WEEK        STANDARD			N/A        
    4     HK.TCH210429C370000   腾讯 210429 370.00 购       100       DRVT        CALL    HK.00700  2021-04-29         370.0       False  80235165               N/A        WEEK        STANDARD			N/A        
    5     HK.TCH210429P370000   腾讯 210429 370.00 沽       100       DRVT         PUT    HK.00700  2021-04-29         370.0       False  80235248               N/A        WEEK        STANDARD			N/A        
    HK.TCH210429C350000
    ['HK.TCH210429C350000', 'HK.TCH210429P350000', 'HK.TCH210429C360000', 'HK.TCH210429P360000', 'HK.TCH210429C370000', 'HK.TCH210429P370000']
    ...
                       code                name  lot_size stock_type option_type stock_owner strike_time  strike_price  suspension  stock_id index_option_type expiration_cycle option_standard_type option_settlement_mode
    0   HK.TCH220330C490000  腾讯 220330 490.00 购       100       DRVT        CALL    HK.00700  2022-03-30         490.0       False  80235143               N/A        WEEK        STANDARD			N/A            
    1   HK.TCH220330P490000  腾讯 220330 490.00 沽       100       DRVT         PUT    HK.00700  2022-03-30         490.0       False  80235193               N/A        WEEK        STANDARD			N/A            
    2   HK.TCH220330C500000  腾讯 220330 500.00 购       100       DRVT        CALL    HK.00700  2022-03-30         500.0       False  80233887               N/A        WEEK        STANDARD			N/A            
    3   HK.TCH220330P500000  腾讯 220330 500.00 沽       100       DRVT         PUT    HK.00700  2022-03-30         500.0       False  80233912               N/A        WEEK        STANDARD			N/A            
    4   HK.TCH220330C510000  腾讯 220330 510.00 购       100       DRVT        CALL    HK.00700  2022-03-30         510.0       False  80233747               N/A        WEEK        STANDARD 			N/A           
    5   HK.TCH220330P510000  腾讯 220330 510.00 沽       100       DRVT         PUT    HK.00700  2022-03-30         510.0       False  80233766               N/A        WEEK        STANDARD 			N/A           
    HK.TCH220330C490000
    ['HK.TCH220330C490000', 'HK.TCH220330P490000', 'HK.TCH220330C500000', 'HK.TCH220330P500000', 'HK.TCH220330C510000', 'HK.TCH220330P510000']
    

1  
2  
3  
4  
5  
6  
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

[#](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html#1122-2)
 Qot\_GetOptionChain.proto
----------------------------------------------------------------------------------------------------------

*   **介绍**
    
    通过标的股票查询期权链。此接口仅返回期权链的静态信息，如需获取报价或摆盘等动态信息，请用此接口返回的股票代码，自行 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
     所需要的类型。
    
*   **协议 ID**
    
    3209
    
*   **参数**
    

    enum OptionCondType
    {
    	OptionCondType_Unknow = 0;
    	OptionCondType_WithIn = 1; //价内
    	OptionCondType_Outside = 2; //价外
    }
    
    //以下为数据字段筛选，可选字段，不填表示不过滤
    message DataFilter
    {
    	optional double impliedVolatilityMin = 1; //隐含波动率过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double impliedVolatilityMax = 2; //隐含波动率过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double deltaMin = 3; //希腊值 Delta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 4; //希腊值 Delta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double gammaMin = 5; //希腊值 Gamma 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double gammaMax = 6; //希腊值 Gamma 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double vegaMin = 7; //希腊值 Vega 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double vegaMax = 8; //希腊值 Vega 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double thetaMin = 9; //希腊值 Theta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double thetaMax = 10; //希腊值 Theta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double rhoMin = 11; //希腊值 Rho 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double rhoMax = 12; //希腊值 Rho 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double netOpenInterestMin = 13; //净未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double netOpenInterestMax = 14; //净未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double openInterestMin = 15; //未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double openInterestMax = 16; //未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double volMin = 17; //成交量过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double volMax = 18; //成交量过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    }
    
    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 6; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
    	optional int32 type = 2; //Qot_Common.OptionType，期权类型，可选字段，不指定则表示都返回
    	optional int32 condition = 3; //OptionCondType，价内价外，可选字段，不指定则表示都返回
    	required string beginTime = 4; //期权到期日开始时间（格式：yyyy-MM-dd）
    	required string endTime = 5; //期权到期日结束时间，时间跨度最多一个月（格式：yyyy-MM-dd）
    	optional DataFilter dataFilter = 7; //数据字段筛选
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   期权类型枚举参见 [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713)
>     
> *   指数期权类别枚举参见 [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149)
>     

*   **返回**

    message OptionItem
    {
    	optional Qot_Common.SecurityStaticInfo call = 1; //看涨期权，不一定有该字段，由请求条件决定
    	optional Qot_Common.SecurityStaticInfo put = 2; //看跌期权，不一定有该字段，由请求条件决定
    }
    
    message OptionChain
    {
    	required string strikeTime = 1; //行权日（格式：yyyy-MM-dd）
    	repeated OptionItem option = 2; //期权信息
    	optional double strikeTimestamp = 3; //行权日时间戳
    }
    
    message S2C
    {
    	repeated OptionChain optionChain = 1; //期权链
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3209
    

`uint GetOptionChain(QotGetOptionChain.Request req);`  
`virtual void OnReply_GetOptionChain(MMAPI_Conn client, uint nSerialNo, QotGetOptionChain.Response rsp);`

*   **介绍**
    
    通过标的股票查询期权链。此接口仅返回期权链的静态信息，如需获取报价或摆盘等动态信息，请用此接口返回的股票代码，自行 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
     所需要的类型。
    
*   **参数**
    

    enum OptionCondType
    {
    	OptionCondType_Unknow = 0;
    	OptionCondType_WithIn = 1; //价内
    	OptionCondType_Outside = 2; //价外
    }
    
    //以下为数据字段筛选，可选字段，不填表示不过滤
    message DataFilter
    {
    	optional double impliedVolatilityMin = 1; //隐含波动率过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double impliedVolatilityMax = 2; //隐含波动率过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double deltaMin = 3; //希腊值 Delta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 4; //希腊值 Delta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double gammaMin = 5; //希腊值 Gamma 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double gammaMax = 6; //希腊值 Gamma 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double vegaMin = 7; //希腊值 Vega 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double vegaMax = 8; //希腊值 Vega 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double thetaMin = 9; //希腊值 Theta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double thetaMax = 10; //希腊值 Theta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double rhoMin = 11; //希腊值 Rho 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double rhoMax = 12; //希腊值 Rho 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double netOpenInterestMin = 13; //净未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double netOpenInterestMax = 14; //净未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double openInterestMin = 15; //未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double openInterestMax = 16; //未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double volMin = 17; //成交量过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double volMax = 18; //成交量过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    }
    
    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 6; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
    	optional int32 type = 2; //Qot_Common.OptionType，期权类型，可选字段，不指定则表示都返回
    	optional int32 condition = 3; //OptionCondType，价内价外，可选字段，不指定则表示都返回
    	required string beginTime = 4; //期权到期日开始时间（格式：yyyy-MM-dd）
    	required string endTime = 5; //期权到期日结束时间，时间跨度最多一个月（格式：yyyy-MM-dd）
    	optional DataFilter dataFilter = 7; //数据字段筛选
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   期权类型枚举参见 [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713)
>     
> *   指数期权类别枚举参见 [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149)
>     

*   **返回**

    message OptionItem
    {
    	optional Qot_Common.SecurityStaticInfo call = 1; //看涨期权，不一定有该字段，由请求条件决定
    	optional Qot_Common.SecurityStaticInfo put = 2; //看跌期权，不一定有该字段，由请求条件决定
    }
    
    message OptionChain
    {
    	required string strikeTime = 1; //行权日（格式：yyyy-MM-dd）
    	repeated OptionItem option = 2; //期权信息
    	optional double strikeTimestamp = 3; //行权日时间戳
    }
    
    message S2C
    {
    	repeated OptionChain optionChain = 1; //期权链
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
            QotGetOptionChain.C2S c2s = QotGetOptionChain.C2S.CreateBuilder()
                .SetOwner(sec)
                .SetBeginTime("2020-11-01")
                .SetEndTime("2020-12-01")
                .Build();
            QotGetOptionChain.Request req = QotGetOptionChain.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetOptionChain(req);
            Console.Write("Send QotGetOptionChain: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetOptionChain(MMAPI_Conn client, uint nSerialNo, QotGetOptionChain.Response rsp)
        {
            Console.Write("Reply: QotGetOptionChain: {0}\n", nSerialNo);
            Console.Write("strikeTime: {0}, name: {1} \n", 
                rsp.S2C.OptionChainList[0].StrikeTime,
                rsp.S2C.OptionChainList[0].OptionList[0].Call.Basic.Name);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825705573658441031
    Send QotGetOptionChain: 3
    Reply: QotGetOptionChain: 3
    strikeTime: 2021-07-29, name: 腾讯 210729 400.00 购
    

1  
2  
3  
4  

`int getOptionChain(QotGetOptionChain.Request req);`  
`void onReply_GetOptionChain(MMAPI_Conn client, int nSerialNo, QotGetOptionChain.Response rsp);`

*   **介绍**
    
    通过标的股票查询期权链。此接口仅返回期权链的静态信息，如需获取报价或摆盘等动态信息，请用此接口返回的股票代码，自行 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
     所需要的类型。
    
*   **参数**
    

    enum OptionCondType
    {
    	OptionCondType_Unknow = 0;
    	OptionCondType_WithIn = 1; //价内
    	OptionCondType_Outside = 2; //价外
    }
    
    //以下为数据字段筛选，可选字段，不填表示不过滤
    message DataFilter
    {
    	optional double impliedVolatilityMin = 1; //隐含波动率过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double impliedVolatilityMax = 2; //隐含波动率过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double deltaMin = 3; //希腊值 Delta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 4; //希腊值 Delta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double gammaMin = 5; //希腊值 Gamma 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double gammaMax = 6; //希腊值 Gamma 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double vegaMin = 7; //希腊值 Vega 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double vegaMax = 8; //希腊值 Vega 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double thetaMin = 9; //希腊值 Theta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double thetaMax = 10; //希腊值 Theta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double rhoMin = 11; //希腊值 Rho 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double rhoMax = 12; //希腊值 Rho 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double netOpenInterestMin = 13; //净未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double netOpenInterestMax = 14; //净未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double openInterestMin = 15; //未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double openInterestMax = 16; //未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double volMin = 17; //成交量过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double volMax = 18; //成交量过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    }
    
    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 6; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
    	optional int32 type = 2; //Qot_Common.OptionType，期权类型，可选字段，不指定则表示都返回
    	optional int32 condition = 3; //OptionCondType，价内价外，可选字段，不指定则表示都返回
    	required string beginTime = 4; //期权到期日开始时间（格式：yyyy-MM-dd）
    	required string endTime = 5; //期权到期日结束时间，时间跨度最多一个月（格式：yyyy-MM-dd）
    	optional DataFilter dataFilter = 7; //数据字段筛选
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   期权类型枚举参见 [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713)
>     
> *   指数期权类别枚举参见 [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149)
>     

*   **返回**

    message OptionItem
    {
    	optional Qot_Common.SecurityStaticInfo call = 1; //看涨期权，不一定有该字段，由请求条件决定
    	optional Qot_Common.SecurityStaticInfo put = 2; //看跌期权，不一定有该字段，由请求条件决定
    }
    
    message OptionChain
    {
    	required string strikeTime = 1; //行权日（格式：yyyy-MM-dd）
    	repeated OptionItem option = 2; //期权信息
    	optional double strikeTimestamp = 3; //行权日时间戳
    }
    
    message S2C
    {
    	repeated OptionChain optionChain = 1; //期权链
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
            QotGetOptionChain.C2S c2s = QotGetOptionChain.C2S.newBuilder()
                    .setOwner(sec)
                    .setBeginTime("2021-06-01")
                    .setEndTime("2021-07-01")
                .build();
            QotGetOptionChain.Request req = QotGetOptionChain.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getOptionChain(req);
            System.out.printf("Send QotGetOptionChain: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetOptionChain(MMAPI_Conn client, int nSerialNo, QotGetOptionChain.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetOptionChain failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetOptionChain: %s\n", json);
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

*   **Output**

    Send QotGetOptionChain: 2
    Receive QotGetOptionChain: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "optionChain": [{\
          "strikeTime": "2021-06-29",\
          "option": [{\
            "call": {\
              "basic": {\
                "security": {\
                  "market": 1,\
                  "code": "TCH210629C295000"\
                },\
                "id": "80143386",\
                "lotSize": 100,\
                "secType": 8,\
                "name": "腾讯 210629 295.00 购",\
                "listTime": "",\
                "delisting": false\
              },\
              "optionExData": {\
                "type": 1,\
                "owner": {\
                  "market": 1,\
                  "code": "00700"\
                },\
                "strikeTime": "2021-06-29",\
                "strikePrice": 295.0,\
                "suspend": false,\
                "market": "",\
                "strikeTimestamp": 1.624896E9,\
                "expirationCycle": 1,\
                "optionStandardType": 1,\
                "optionSettlementMode": 2,\
              }\
            },\
            "put": {\
              "basic": {\
                "security": {\
                  "market": 1,\
                  "code": "TCH210629P295000"\
                },\
                "id": "80143074",\
                "lotSize": 100,\
                "secType": 8,\
                "name": "腾讯 210629 295.00 沽",\
                "listTime": "",\
                "delisting": false\
              },\
              "optionExData": {\
                "type": 2,\
                "owner": {\
                  "market": 1,\
                  "code": "00700"\
                },\
                "strikeTime": "2021-06-29",\
                "strikePrice": 295.0,\
                "suspend": false,\
                "market": "",\
                "strikeTimestamp": 1.624896E9,\
                "expirationCycle": 1,\
                "optionStandardType": 1,\
                "optionSettlementMode": 2,\
              }\
            }\
          }, ... {\
            "call": {\
              "basic": {\
                "security": {\
                  "market": 1,\
                  "code": "TCH210629C1050000"\
                },\
                "id": "80223489",\
                "lotSize": 100,\
                "secType": 8,\
                "name": "腾讯 210629 1050.00 购",\
                "listTime": "",\
                "delisting": false\
              },\
              "optionExData": {\
                "type": 1,\
                "owner": {\
                  "market": 1,\
                  "code": "00700"\
                },\
                "strikeTime": "2021-06-29",\
                "strikePrice": 1050.0,\
                "suspend": false,\
                "market": "",\
                "strikeTimestamp": 1.624896E9,\
                "expirationCycle": 1,\
                "optionStandardType": 1,\
                "optionSettlementMode": 2,\
              }\
            },\
            "put": {\
              "basic": {\
                "security": {\
                  "market": 1,\
                  "code": "TCH210629P1050000"\
                },\
                "id": "80223488",\
                "lotSize": 100,\
                "secType": 8,\
                "name": "腾讯 210629 1050.00 沽",\
                "listTime": "",\
                "delisting": false\
              },\
              "optionExData": {\
                "type": 2,\
                "owner": {\
                  "market": 1,\
                  "code": "00700"\
                },\
                "strikeTime": "2021-06-29",\
                "strikePrice": 1050.0,\
                "suspend": false,\
                "market": "",\
                "strikeTimestamp": 1.624896E9,\
                "expirationCycle": 1,\
                "optionStandardType": 1,\
                "optionSettlementMode": 2,\
              }\
            }\
          }],\
          "strikeTimestamp": 1.624896E9\
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
101  
102  
103  
104  
105  
106  
107  
108  
109  
110  
111  
112  
113  
114  
115  
116  
117  
118  
119  
120  
121  
122  
123  
124  
125  
126  
127  
128  
129  
130  
131  

`moomoo::u32_t GetOptionChain(const Qot_GetOptionChain::Request &stReq);`  
`virtual void OnReply_GetOptionChain(moomoo::u32_t nSerialNo, const Qot_GetOptionChain::Response &stRsp) = 0;`

*   **介绍**
    
    通过标的股票查询期权链。此接口仅返回期权链的静态信息，如需获取报价或摆盘等动态信息，请用此接口返回的股票代码，自行 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
     所需要的类型。
    
*   **参数**
    

    enum OptionCondType
    {
    	OptionCondType_Unknow = 0;
    	OptionCondType_WithIn = 1; //价内
    	OptionCondType_Outside = 2; //价外
    }
    
    //以下为数据字段筛选，可选字段，不填表示不过滤
    message DataFilter
    {
    	optional double impliedVolatilityMin = 1; //隐含波动率过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double impliedVolatilityMax = 2; //隐含波动率过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double deltaMin = 3; //希腊值 Delta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 4; //希腊值 Delta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double gammaMin = 5; //希腊值 Gamma 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double gammaMax = 6; //希腊值 Gamma 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double vegaMin = 7; //希腊值 Vega 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double vegaMax = 8; //希腊值 Vega 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double thetaMin = 9; //希腊值 Theta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double thetaMax = 10; //希腊值 Theta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double rhoMin = 11; //希腊值 Rho 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double rhoMax = 12; //希腊值 Rho 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double netOpenInterestMin = 13; //净未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double netOpenInterestMax = 14; //净未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double openInterestMin = 15; //未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double openInterestMax = 16; //未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double volMin = 17; //成交量过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double volMax = 18; //成交量过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    }
    
    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 6; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
    	optional int32 type = 2; //Qot_Common.OptionType，期权类型，可选字段，不指定则表示都返回
    	optional int32 condition = 3; //OptionCondType，价内价外，可选字段，不指定则表示都返回
    	required string beginTime = 4; //期权到期日开始时间（格式：yyyy-MM-dd）
    	required string endTime = 5; //期权到期日结束时间，时间跨度最多一个月（格式：yyyy-MM-dd）
    	optional DataFilter dataFilter = 7; //数据字段筛选
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   期权类型枚举参见 [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713)
>     
> *   指数期权类别枚举参见 [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149)
>     

*   **返回**

    message OptionItem
    {
    	optional Qot_Common.SecurityStaticInfo call = 1; //看涨期权，不一定有该字段，由请求条件决定
    	optional Qot_Common.SecurityStaticInfo put = 2; //看跌期权，不一定有该字段，由请求条件决定
    }
    
    message OptionChain
    {
    	required string strikeTime = 1; //行权日（格式：yyyy-MM-dd）
    	repeated OptionItem option = 2; //期权信息
    	optional double strikeTimestamp = 3; //行权日时间戳
    }
    
    message S2C
    {
    	repeated OptionChain optionChain = 1; //期权链
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
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
    		Qot_GetOptionChain::Request req;
    		Qot_GetOptionChain::C2S *c2s = req.mutable_c2s();
    		Qot_Common::Security *sec = c2s->mutable_owner();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    		c2s->set_begintime("2021-06-07");
    		c2s->set_endtime("2021-07-01");
    
        m_GetOptionChainSerialNo = m_pQotApi->GetOptionChain(req);
        cout << "Request GetOptionChain SerialNo: " << m_GetOptionChainSerialNo << endl;
    	}
    
    	virtual void OnReply_GetOptionChain(moomoo::u32_t nSerialNo, const Qot_GetOptionChain::Response &stRsp){
        if(nSerialNo == m_GetOptionChainSerialNo)
        {
            cout << "OnReply_GetOptionChain SerialNo: " << nSerialNo << endl;
            // 解析内部结构打印出来
            // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
            string resp_str;
            ProtoBufToBodyData(stRsp, resp_str);
            cout << UTF8ToLocal(resp_str) << endl;
        }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
      moomoo::u32_t m_GetOptionChainSerialNo;
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

*   **Output**

    connect
    Request GetOptionChain SerialNo: 4
    OnReply_GetOptionChain SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "optionChain": [\
       {\
        "strikeTime": "2021-06-29",\
        "option": [\
         {\
          "call": {\
           "basic": {\
            "security": {\
             "market": 1,\
             "code": "TCH210629C295000"\
            },\
            "id": "80143386",\
            "lotSize": 100,\
            "secType": 8,\
            "name": "腾讯 210629 295.00 购",\
            "listTime": "",\
            "delisting": false\
           },\
           "optionExData": {\
            "type": 1,\
            "owner": {\
             "market": 1,\
             "code": "00700"\
            },\
            "strikeTime": "2021-06-29",\
            "strikePrice": 295,\
            "suspend": false,\
            "market": "",\
            "strikeTimestamp": 1624896000,\
            "expirationCycle": 1,\
            "optionStandardType": 1,\
            "optionSettlementMode": 2,\
           }\
          },\
          "put": {\
           "basic": {\
            "security": {\
             "market": 1,\
             "code": "TCH210629P295000"\
            },\
            "id": "80143074",\
            "lotSize": 100,\
            "secType": 8,\
            "name": "腾讯 210629 295.00 沽",\
            "listTime": "",\
            "delisting": false\
           },\
           "optionExData": {\
            "type": 2,\
            "owner": {\
             "market": 1,\
             "code": "00700"\
            },\
            "strikeTime": "2021-06-29",\
            "strikePrice": 295,\
            "suspend": false,\
            "market": "",\
            "strikeTimestamp": 1624896000,\
            "expirationCycle": 1,\
            "optionStandardType": 1,\
            "optionSettlementMode": 2,\
           }\
          }\
         },\
    ...\
         {\
          "call": {\
           "basic": {\
            "security": {\
             "market": 1,\
             "code": "TCH210629C1050000"\
            },\
            "id": "80223489",\
            "lotSize": 100,\
            "secType": 8,\
            "name": "腾讯 210629 1050.00 购",\
            "listTime": "",\
            "delisting": false\
           },\
           "optionExData": {\
            "type": 1,\
            "owner": {\
             "market": 1,\
             "code": "00700"\
            },\
            "strikeTime": "2021-06-29",\
            "strikePrice": 1050,\
            "suspend": false,\
            "market": "",\
            "strikeTimestamp": 1624896000,\
            "expirationCycle": 1,\
            "optionStandardType": 1,\
            "optionSettlementMode": 2,\
           }\
          },\
          "put": {\
           "basic": {\
            "security": {\
             "market": 1,\
             "code": "TCH210629P1050000"\
            },\
            "id": "80223488",\
            "lotSize": 100,\
            "secType": 8,\
            "name": "腾讯 210629 1050.00 沽",\
            "listTime": "",\
            "delisting": false\
           },\
           "optionExData": {\
            "type": 2,\
            "owner": {\
             "market": 1,\
             "code": "00700"\
            },\
            "strikeTime": "2021-06-29",\
            "strikePrice": 1050,\
            "suspend": false,\
            "market": "",\
            "strikeTimestamp": 1624896000,\
            "expirationCycle": 1,\
            "optionStandardType": 1,\
            "optionSettlementMode": 2,\
           }\
          }\
         }\
        ],\
        "strikeTimestamp": 1624896000\
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
101  
102  
103  
104  
105  
106  
107  
108  
109  
110  
111  
112  
113  
114  
115  
116  
117  
118  
119  
120  
121  
122  
123  
124  
125  
126  
127  
128  
129  
130  
131  
132  
133  
134  
135  
136  
137  
138  
139  
140  

`GetOptionChain(req);`

*   **介绍**
    
    通过标的股票查询期权链。此接口仅返回期权链的静态信息，如需获取报价或摆盘等动态信息，请用此接口返回的股票代码，自行 [订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
     所需要的类型。
    
*   **参数**
    

    enum OptionCondType
    {
    	OptionCondType_Unknow = 0;
    	OptionCondType_WithIn = 1; //价内
    	OptionCondType_Outside = 2; //价外
    }
    
    //以下为数据字段筛选，可选字段，不填表示不过滤
    message DataFilter
    {
    	optional double impliedVolatilityMin = 1; //隐含波动率过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double impliedVolatilityMax = 2; //隐含波动率过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double deltaMin = 3; //希腊值 Delta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 4; //希腊值 Delta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double gammaMin = 5; //希腊值 Gamma 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double gammaMax = 6; //希腊值 Gamma 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double vegaMin = 7; //希腊值 Vega 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double vegaMax = 8; //希腊值 Vega 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double thetaMin = 9; //希腊值 Theta 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double thetaMax = 10; //希腊值 Theta 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double rhoMin = 11; //希腊值 Rho 过滤起点（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double rhoMax = 12; //希腊值 Rho 过滤终点（精确到小数点后 3 位，超出部分会被舍弃）
    
    	optional double netOpenInterestMin = 13; //净未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double netOpenInterestMax = 14; //净未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double openInterestMin = 15; //未平仓合约数过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double openInterestMax = 16; //未平仓合约数过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    
    	optional double volMin = 17; //成交量过滤起点（精确到小数点后 0 位，超出部分会被舍弃）
    	optional double volMax = 18; //成交量过滤终点（精确到小数点后 0 位，超出部分会被舍弃）
    }
    
    message C2S
    {
    	required Qot_Common.Security owner = 1; //期权标的股，目前仅支持传入港美正股以及恒指国指美指
    	optional int32 indexOptionType = 6; //Qot_Common.IndexOptionType，指数期权的类型，仅用于恒指国指
    	optional int32 type = 2; //Qot_Common.OptionType，期权类型，可选字段，不指定则表示都返回
    	optional int32 condition = 3; //OptionCondType，价内价外，可选字段，不指定则表示都返回
    	required string beginTime = 4; //期权到期日开始时间（格式：yyyy-MM-dd）
    	required string endTime = 5; //期权到期日结束时间，时间跨度最多一个月（格式：yyyy-MM-dd）
    	optional DataFilter dataFilter = 7; //数据字段筛选
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   期权类型枚举参见 [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713)
>     
> *   指数期权类别枚举参见 [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149)
>     

*   **返回**

    message OptionItem
    {
    	optional Qot_Common.SecurityStaticInfo call = 1; //看涨期权，不一定有该字段，由请求条件决定
    	optional Qot_Common.SecurityStaticInfo put = 2; //看跌期权，不一定有该字段，由请求条件决定
    }
    
    message OptionChain
    {
    	required string strikeTime = 1; //行权日（格式：yyyy-MM-dd）
    	repeated OptionItem option = 2; //期权信息
    	optional double strikeTimestamp = 3; //行权日时间戳
    }
    
    message S2C
    {
    	repeated OptionChain optionChain = 1; //期权链
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

> *   股票静态信息结构参见 [SecurityStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/quote.html#609)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetOptionChain(){
        const { RetType } = Common
        const {  QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        owner:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                        beginTime: "2021-09-01",
                        endTime: "2021-09-30",
                    },
                };
    
                websocket.GetOptionChain(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("OptionChain: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    OptionChain: errCode 0, retMsg , retType 0
    {
      "optionChain": [{\
        "strikeTime": "2021-09-29",\
        "option": [{\
          "call": {\
            "basic": {\
              "security": {\
                "market": 1,\
                "code": "TCH210929C300000"\
              },\
              "id": "80287116",\
              "lotSize": 100,\
              "secType": 8,\
              "name": "腾讯 210929 300.00 购",\
              "listTime": "",\
              "delisting": false\
            },\
            "optionExData": {\
              "type": 1,\
              "owner": {\
                "market": 1,\
                "code": "00700"\
              },\
              "strikeTime": "2021-09-29",\
              "strikePrice": 300,\
              "suspend": false,\
              "market": "",\
              "strikeTimestamp": 1632844800,\
              "expirationCycle": 1,\
              "optionStandardType": 1,\
              "optionSettlementMode": 2,\
            }\
          },\
          "put": {\
            "basic": {\
              "security": {\
                "market": 1,\
                "code": "TCH210929P300000"\
              },\
              "id": "80287124",\
              "lotSize": 100,\
              "secType": 8,\
              "name": "腾讯 210929 300.00 沽",\
              "listTime": "",\
              "delisting": false\
            },\
            "optionExData": {\
              "type": 2,\
              "owner": {\
                "market": 1,\
                "code": "00700"\
              },\
              "strikeTime": "2021-09-29",\
              "strikePrice": 300,\
              "suspend": false,\
              "market": "",\
              "strikeTimestamp": 1632844800,\
              "expirationCycle": 1,\
              "optionStandardType": 1,\
              "optionSettlementMode": 2,\
            }\
          }\
        }, ..., {\
          "call": {\
            "basic": {\
              "security": {\
                "market": 1,\
                "code": "TCH210929C950000"\
              },\
              "id": "80215136",\
              "lotSize": 100,\
              "secType": 8,\
              "name": "腾讯 210929 950.00 购",\
              "listTime": "",\
              "delisting": false\
            },\
            "optionExData": {\
              "type": 1,\
              "owner": {\
                "market": 1,\
                "code": "00700"\
              },\
              "strikeTime": "2021-09-29",\
              "strikePrice": 950,\
              "suspend": false,\
              "market": "",\
              "strikeTimestamp": 1632844800,\
              "expirationCycle": 1,\
              "optionStandardType": 1,\
              "optionSettlementMode": 2,\
            }\
          },\
          "put": {\
            "basic": {\
              "security": {\
                "market": 1,\
                "code": "TCH210929P950000"\
              },\
              "id": "80215157",\
              "lotSize": 100,\
              "secType": 8,\
              "name": "腾讯 210929 950.00 沽",\
              "listTime": "",\
              "delisting": false\
            },\
            "optionExData": {\
              "type": 2,\
              "owner": {\
                "market": 1,\
                "code": "00700"\
              },\
              "strikeTime": "2021-09-29",\
              "strikePrice": 950,\
              "suspend": false,\
              "market": "",\
              "strikeTimestamp": 1632844800,\
              "expirationCycle": 1,\
              "optionStandardType": 1,\
              "optionSettlementMode": 2,\
            }\
          }\
        }],\
        "strikeTimestamp": 1632844800\
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
101  
102  
103  
104  
105  
106  
107  
108  
109  
110  
111  
112  
113  
114  
115  
116  
117  
118  
119  
120  
121  
122  
123  
124  
125  
126  
127  

接口限制

*   每 30 秒内最多请求 10 次获取期权链接口
*   传入的时间跨度上限为 30 天

提示

*   此接口不支持查询已过期的期权链，**结束日期** 参数请输入今天或未来的日期
*   Open interest (OI) 数据每日更新，更新时点取决于具体交易所。美股期权在盘前时段更新，港股期权在盘后更新。

← [获取期权链到期日](https://openapi.futunn.com/futu-api-doc/quote/get-option-expiration-date.html) [筛选窝轮](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html)
 →

[获取期权链](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html)