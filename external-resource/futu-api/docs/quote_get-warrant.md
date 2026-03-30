 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-warrant.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-warrant.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/get-warrant.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/get-warrant.html)
    

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
    

[#](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html#2037)
 筛选窝轮
==============================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_warrant(stock_owner='', req=None)`

*   **介绍**
    
    筛选窝轮（仅用于筛选香港市场的窝轮、牛熊证、界内证）
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | stock\_owner | str | 所属正股的股票代码 |
    | req | WarrantRequest | 筛选参数组合 |
    
    *   WarrantRequest 类型字段说明如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | begin | int | 数据起始点 |
        | num | int | 请求数据个数<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>最大 200 |
        | sort\_field | [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930) | 根据哪个字段排序 |
        | ascend | bool | 排序方向<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：升序  <br>False：降序 |
        | type\_list | list | 窝轮类型过滤列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 [WrtType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926) |
        | issuer\_list | list | 发行人过滤列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363) |
        | maturity\_time\_min | str | 到期日过滤范围的开始时间 |
        | maturity\_time\_max | str | 到期日过滤范围的结束时间 |
        | ipo\_period | [IpoPeriod](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9546) | 上市时段 |
        | price\_type | [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407) | 价内/价外<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>暂不支持界内证的界内外筛选 |
        | status | [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556) | 窝轮状态 |
        | cur\_price\_min | float | 最新价的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | cur\_price\_max | float | 最新价的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | strike\_price\_min | float | 行使价的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | strike\_price\_max | float | 行使价的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | street\_min | float | 街货占比的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | street\_max | float | 街货占比的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | conversion\_min | float | 换股比率的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | conversion\_max | float | 换股比率的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | vol\_min | int | 成交量的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞ |
        | vol\_max | int | 成交量的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞ |
        | premium\_min | float | 溢价的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | premium\_max | float | 溢价的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | leverage\_ratio\_min | float | 杠杆比率的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | leverage\_ratio\_max | float | 杠杆比率的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞ |
        | delta\_min | float | 对冲值的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅认购认沽支持此字段过滤  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | delta\_max | float | 对冲值的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅认购认沽支持此字段过滤  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | implied\_min | float | 引伸波幅的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅认购认沽支持此字段过滤  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | implied\_max | float | 引伸波幅的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅认购认沽支持此字段过滤  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | recovery\_price\_min | float | 收回价的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅牛熊证支持此字段过滤  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | recovery\_price\_max | float | 收回价的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅牛熊证支持此字段过滤  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | price\_recovery\_ratio\_min | float | 正股距收回价的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅牛熊证支持此字段过滤  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | price\_recovery\_ratio\_max | float | 正股距收回价的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅牛熊证支持此字段过滤  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | tuple | 当 ret == RET\_OK，返回窝轮数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   窝轮数据组成如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | warrant\_data\_list | pd.DataFrame | 筛选后的窝轮数据 |
        | last\_page | bool | 是否是最后一页<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：是最后一页  <br>False：不是最后一页 |
        | all\_count | int | 筛选结果中的窝轮总数量 |
        
        *   warrant\_data\_list 返回的 pd dataframe 数据格式：
            
            | 字段  | 类型  | 说明  |
            | --- | --- | --- |
            | stock | str | 窝轮代码 |
            | stock\_owner | str | 所属正股 |
            | type | [WrtType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926) | 窝轮类型 |
            | issuer | [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363) | 发行人 |
            | maturity\_time | str | 到期日<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd |
            | list\_time | str | 上市时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd |
            | last\_trade\_time | str | 最后交易日<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd |
            | recovery\_price | float | 收回价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅牛熊证支持此字段 |
            | conversion\_ratio | float | 换股比率 |
            | lot\_size | int | 每手数量 |
            | strike\_price | float | 行使价 |
            | last\_close\_price | float | 昨收价 |
            | name | str | 名称  |
            | cur\_price | float | 当前价 |
            | price\_change\_val | float | 涨跌额 |
            | status | [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556) | 窝轮状态 |
            | bid\_price | float | 买入价 |
            | ask\_price | float | 卖出价 |
            | bid\_vol | int | 买量  |
            | ask\_vol | int | 卖量  |
            | volume | int | 成交量 |
            | turnover | float | 成交额 |
            | score | float | 综合评分 |
            | premium | float | 溢价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
            | break\_even\_point | float | 打和点 |
            | leverage | float | 杠杆比率<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>单位：倍 |
            | ipop | float | 价内/价外<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
            | price\_recovery\_ratio | float | 正股距收回价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅牛熊证支持此字段  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
            | conversion\_price | float | 换股价 |
            | street\_rate | float | 街货占比<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
            | street\_vol | int | 街货量 |
            | amplitude | float | 振幅<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
            | issue\_size | int | 发行量 |
            | high\_price | float | 最高价 |
            | low\_price | float | 最低价 |
            | implied\_volatility | float | 引伸波幅<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅认购认沽支持此字段 |
            | delta | float | 对冲值<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅认购认沽支持此字段 |
            | effective\_leverage | float | 有效杠杆<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅认购认沽支持此字段 |
            | upper\_strike\_price | float | 上限价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅界内证支持此字段 |
            | lower\_strike\_price | float | 下限价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅界内证支持此字段 |
            | inline\_price\_status | [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407) | 界内界外<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅界内证支持此字段 |
            
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    req = WarrantRequest()
    req.sort_field = SortField.TURNOVER
    req.type_list = WrtType.CALL
    req.cur_price_min = 0.1
    req.cur_price_max = 0.2
    ret, ls = quote_ctx.get_warrant("HK.00700", req)
    if ret == RET_OK:  # 先判断接口返回是否正常，再取数据
        warrant_data_list, last_page, all_count = ls
        print(len(warrant_data_list), all_count, warrant_data_list)
        print(warrant_data_list['stock'][0])    # 取第一条的窝轮代码
        print(warrant_data_list['stock'].values.tolist())   # 转为 list
    else:
        print('error: ', ls)
        
    req = WarrantRequest()
    req.sort_field = SortField.TURNOVER
    req.issuer_list = ['UB','CS','BI']
    ret, ls = quote_ctx.get_warrant(Market.HK, req)
    if ret == RET_OK: 
        warrant_data_list, last_page, all_count = ls
        print(len(warrant_data_list), all_count, warrant_data_list)
    else:
        print('error: ', ls)
    
    quote_ctx.close()  # 所有接口结尾加上这条 close，防止连接条数用尽
    

1  
2  
3  
4  
5  
6  
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

*   **Output**

    2 2 
        stock        name stock_owner  type issuer maturity_time   list_time last_trade_time  recovery_price  conversion_ratio  lot_size  strike_price  last_close_price  cur_price  price_change_val  change_rate  status  bid_price  ask_price   bid_vol  ask_vol    volume   turnover   score  premium  break_even_point  leverage    ipop  price_recovery_ratio  conversion_price  street_rate  street_vol  amplitude  issue_size  high_price  low_price  implied_volatility  delta  effective_leverage  list_timestamp  last_trade_timestamp  maturity_timestamp  upper_strike_price  lower_strike_price  inline_price_status
    0   HK.20306  腾讯麦银零乙购A.C    HK.00700  CALL     MB    2020-12-01  2019-06-27      2020-11-25             NaN              50.0      5000        588.88             0.188      0.188             0.000     0.000000  NORMAL      0.000      0.188         0     10000           0          0.0   0.198    2.008            598.28    62.393  -0.404                   NaN              9.40        4.400     1584000      0.000    36000000       0.000      0.000              31.751  0.479              29.886    1.561565e+09          1.606234e+09        1.606752e+09                 NaN                 NaN                  NaN
    1   HK.16545  腾讯法兴一二购B.C    HK.00700  CALL     SG    2021-02-26  2020-07-14      2021-02-22             NaN             100.0     10000        700.00             0.147      0.144            -0.003    -2.040816  NORMAL      0.141      0.144  28000000  28000000           0          0.0  81.506   21.807            714.40    40.729 -16.214                   NaN             14.40        1.420     2130000      0.000   150000000       0.000      0.000              40.643  0.226               9.204    1.594656e+09          1.613923e+09        1.614269e+09                 NaN                 NaN                  NaN
    HK.20306
    ['HK.20306', 'HK.16545']
    
    200 358
        stock        name stock_owner    type issuer maturity_time   list_time last_trade_time  recovery_price  conversion_ratio  lot_size  strike_price  last_close_price  cur_price  price_change_val  change_rate      status  bid_price  ask_price   bid_vol   ask_vol  volume  turnover   score  premium  break_even_point  leverage     ipop  price_recovery_ratio  conversion_price  street_rate  street_vol  amplitude  issue_size  high_price  low_price  implied_volatility  delta  effective_leverage  list_timestamp  last_trade_timestamp  maturity_timestamp  upper_strike_price  lower_strike_price inline_price_status
    0    HK.19839  平安瑞银零乙购A.C    HK.02318    CALL     UB    2020-12-31  2017-12-11      2020-12-24             NaN             100.0     50000         83.88             0.057      0.046            -0.011   -19.298246      NORMAL      0.043      0.046  30000000  30000000       0       0.0  39.641    1.642            88.480    18.923    3.779                   NaN             4.600         1.25     6250000        0.0   500000000         0.0        0.0              25.129  0.692              13.094    1.512922e+09          1.608739e+09        1.609344e+09                 NaN                 NaN                 NaN
    1    HK.20084  平安中银零乙购A.C    HK.02318    CALL     BI    2020-12-31  2017-12-19      2020-12-24             NaN             100.0     50000         83.88             0.059      0.050            -0.009   -15.254237      NORMAL      0.044      0.050  10000000  10000000       0       0.0   0.064    2.102            88.880    17.410    3.779                   NaN             5.000         0.07      350000        0.0   500000000         0.0        0.0              29.174  0.672              11.699    1.513613e+09          1.608739e+09        1.609344e+09                 NaN                 NaN                 NaN
    ......
    198  HK.56886  恒指瑞银三一牛F.C   HK.800000    BULL     UB    2023-01-30  2020-03-24      2023-01-27         21200.0           20000.0     10000      21100.00             0.230      0.232             0.002     0.869565      NORMAL      0.232      0.233  30000000  30000000       0       0.0  46.627   -2.884         25740.000     5.712   25.613             25.021179          4640.000         0.01       40000        0.0   400000000         0.0        0.0                 NaN    NaN               5.712    1.584979e+09          1.674749e+09        1.675008e+09                 NaN                 NaN                 NaN
    199  HK.56895  小米瑞银零乙牛D.C    HK.01810    BULL     UB    2020-12-30  2020-03-24      2020-12-29             8.0              10.0      2000          7.60             2.010      1.930            -0.080    -3.980100      NORMAL      1.910      1.930   6000000   6000000       0       0.0   0.040    0.938            26.900     1.380  250.657            233.125000            19.300         0.10       60000        0.0    60000000         0.0        0.0                 NaN    NaN               1.380    1.584979e+09          1.609171e+09        1.609258e+09                 NaN                 NaN                 NaN
    
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html#8023)
 Qot\_GetWarrant.proto
-----------------------------------------------------------------------------------------------

*   **介绍**
    
    筛选窝轮（仅用于香港市场）
    
*   **参数**
    

    message C2S
    {
    	required int32 begin = 1; //数据起始点
    	required int32 num =  2; //请求数据个数，最大200
    	required int32 sortField = 3;//Qot_Common.SortField，根据哪个字段排序
    	required bool ascend = 4;//升序 ture，降序 false
    	
    	//以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security owner = 5;	//所属正股
    	repeated int32 typeList = 6; //Qot_Common.WarrantType，窝轮类型过滤列表
    	repeated int32 issuerList = 7; //Qot_Common.Issuer，发行人过滤列表
    	optional string maturityTimeMin = 8; //到期日，到期日范围的开始时间戳
    	optional string maturityTimeMax = 9; //到期日范围的结束时间戳
    	optional int32 ipoPeriod = 10; //Qot_Common.IpoPeriod，上市日
    	optional int32 priceType = 11; //Qot_Common.PriceType，价内/价外（暂不支持界内证的界内外筛选）
    	optional int32 status = 12; //Qot_Common.WarrantStatus，窝轮状态
    	optional double curPriceMin = 13; //最新价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double curPriceMax = 14; //最新价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 	
    	optional double strikePriceMin = 15; //行使价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double strikePriceMax = 16; //行使价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 
    	optional double streetMin = 17; //街货占比的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double streetMax = 18; //街货占比的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMin = 19; //换股比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMax = 20; //换股比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional uint64 volMin = 21; //成交量的过滤下限（闭区间），不传代表下限为 -∞
    	optional uint64 volMax = 22; //成交量的过滤上限（闭区间），不传代表上限为 +∞
    	optional double premiumMin = 23; //溢价的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double premiumMax = 24; //溢价的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMin = 25; //杠杆比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMax = 26; //杠杆比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMin = 27;//对冲值的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 28;//对冲值的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMin = 29; //引伸波幅的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMax = 30; //引伸波幅的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
    	optional double recoveryPriceMin = 31; //收回价的过滤下限（闭区间），仅牛熊证支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double recoveryPriceMax = 32; //收回价的过滤上限（闭区间），仅牛熊证支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMin = 33;//正股距收回价，的过滤下限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMax = 34;//正股距收回价，的过滤上限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序类型参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   上市日类型参见 [IpoPeriod](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9546)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     

*   **返回**

    message WarrantData
    {
    	//静态数据项
        required Qot_Common.Security stock = 1; //股票
        required Qot_Common.Security owner = 2; //所属正股
    	required int32 type = 3; //Qot_Common.WarrantType，窝轮类型
        required int32 issuer = 4; //Qot_Common.Issuer，发行人
    	required string maturityTime = 5; //到期日
    	optional double maturityTimestamp = 6; //到期日时间戳
    	required string listTime = 7; //上市时间（格式：yyyy-MM-dd）
    	optional double listTimestamp = 8; //上市时间戳
    	required string lastTradeTime = 9; //最后交易日
    	optional double lastTradeTimestamp = 10; //最后交易日时间戳
    	optional double recoveryPrice = 11; //收回价，仅牛熊证支持此字段
    	required double conversionRatio = 12; //换股比率
    	required int32 lotSize = 13; //每手数量
    	required double strikePrice = 14; //行使价	
    	required double lastClosePrice = 15; //昨收价        
    	required string name = 16; //名称	
    
    	//动态数据项
    	required double curPrice = 17; //当前价
    	required double priceChangeVal = 18; //涨跌额
        required double changeRate = 19; //涨跌幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int32 status = 20; //Qot_Common.WarrantStatus，窝轮状态	
        required double bidPrice = 21; //买入价	
    	required double askPrice = 22; //卖出价
        required int64 bidVol = 23; //买量
    	required int64 askVol = 24; //卖量
    	required int64 volume = 25; //成交量
        required double turnover = 26; //成交额	
    	required double score = 27; //综合评分
    	required double premium = 28; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double breakEvenPoint = 29; //打和点	
        required double leverage = 30; //杠杆比率（倍）
        required double ipop = 31; //价内/价外，正数表示价内，负数表示价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）        	
    	optional double priceRecoveryRatio = 32; //正股距收回价，仅牛熊证支持此字段（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double conversionPrice = 33; //换股价
    	required double streetRate = 34; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int64 streetVol = 35; //街货量
    	required double amplitude = 36; //振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int64 issueSize = 37; //发行量	        
    	required double highPrice = 39; //最高价
        required double lowPrice = 40; //最低价	
    	optional double impliedVolatility = 41; //引申波幅，仅认购认沽支持此字段
        optional double delta = 42; //对冲值，仅认购认沽支持此字段
    	required double effectiveLeverage = 43; //有效杠杆
    	optional double upperStrikePrice = 44; //上限价，仅界内证支持此字段
    	optional double lowerStrikePrice = 45; //下限价，仅界内证支持此字段
    	optional int32 inLinePriceStatus = 46; //Qot_Common.PriceType，界内界外，仅界内证支持此字段
    }
    
    message S2C
    {
        required bool lastPage = 1; //是否最后一页了，false:非最后一页，还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; //该条件请求所有数据的个数
        repeated WarrantData warrantDataList = 3; //窝轮数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3210
    

`uint GetWarrant(QotGetWarrant.Request req);`  
`virtual void OnReply_GetWarrant(FTAPI_Conn client, uint nSerialNo, QotGetWarrant.Response rsp);`

*   **介绍**
    
    筛选窝轮（仅用于香港市场）
    
*   **参数**
    

    message C2S
    {
    	required int32 begin = 1; //数据起始点
    	required int32 num =  2; //请求数据个数，最大200
    	required int32 sortField = 3;//Qot_Common.SortField，根据哪个字段排序
    	required bool ascend = 4;//升序 ture，降序 false
    	
    	//以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security owner = 5;	//所属正股
    	repeated int32 typeList = 6; //Qot_Common.WarrantType，窝轮类型过滤列表
    	repeated int32 issuerList = 7; //Qot_Common.Issuer，发行人过滤列表
    	optional string maturityTimeMin = 8; //到期日，到期日范围的开始时间戳
    	optional string maturityTimeMax = 9; //到期日范围的结束时间戳
    	optional int32 ipoPeriod = 10; //Qot_Common.IpoPeriod，上市日
    	optional int32 priceType = 11; //Qot_Common.PriceType，价内/价外（暂不支持界内证的界内外筛选）
    	optional int32 status = 12; //Qot_Common.WarrantStatus，窝轮状态
    	optional double curPriceMin = 13; //最新价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double curPriceMax = 14; //最新价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 	
    	optional double strikePriceMin = 15; //行使价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double strikePriceMax = 16; //行使价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 
    	optional double streetMin = 17; //街货占比的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double streetMax = 18; //街货占比的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMin = 19; //换股比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMax = 20; //换股比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional uint64 volMin = 21; //成交量的过滤下限（闭区间），不传代表下限为 -∞
    	optional uint64 volMax = 22; //成交量的过滤上限（闭区间），不传代表上限为 +∞
    	optional double premiumMin = 23; //溢价的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double premiumMax = 24; //溢价的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMin = 25; //杠杆比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMax = 26; //杠杆比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMin = 27;//对冲值的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 28;//对冲值的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMin = 29; //引伸波幅的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMax = 30; //引伸波幅的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
    	optional double recoveryPriceMin = 31; //收回价的过滤下限（闭区间），仅牛熊证支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double recoveryPriceMax = 32; //收回价的过滤上限（闭区间），仅牛熊证支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMin = 33;//正股距收回价，的过滤下限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMax = 34;//正股距收回价，的过滤上限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序类型参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   上市日类型参见 [IpoPeriod](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9546)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     

*   **返回**

    message WarrantData
    {
    	//静态数据项
        required Qot_Common.Security stock = 1; //股票
        required Qot_Common.Security owner = 2; //所属正股
    	required int32 type = 3; //Qot_Common.WarrantType，窝轮类型
        required int32 issuer = 4; //Qot_Common.Issuer，发行人
    	required string maturityTime = 5; //到期日
    	optional double maturityTimestamp = 6; //到期日时间戳
    	required string listTime = 7; //上市时间（格式：yyyy-MM-dd）
    	optional double listTimestamp = 8; //上市时间戳
    	required string lastTradeTime = 9; //最后交易日
    	optional double lastTradeTimestamp = 10; //最后交易日时间戳
    	optional double recoveryPrice = 11; //收回价，仅牛熊证支持此字段
    	required double conversionRatio = 12; //换股比率
    	required int32 lotSize = 13; //每手数量
    	required double strikePrice = 14; //行使价	
    	required double lastClosePrice = 15; //昨收价        
    	required string name = 16; //名称	
    
    	//动态数据项
    	required double curPrice = 17; //当前价
    	required double priceChangeVal = 18; //涨跌额
        required double changeRate = 19; //涨跌幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int32 status = 20; //Qot_Common.WarrantStatus，窝轮状态	
        required double bidPrice = 21; //买入价	
    	required double askPrice = 22; //卖出价
        required int64 bidVol = 23; //买量
    	required int64 askVol = 24; //卖量
    	required int64 volume = 25; //成交量
        required double turnover = 26; //成交额	
    	required double score = 27; //综合评分
    	required double premium = 28; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double breakEvenPoint = 29; //打和点	
        required double leverage = 30; //杠杆比率（倍）
        required double ipop = 31; //价内/价外，正数表示价内，负数表示价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）        	
    	optional double priceRecoveryRatio = 32; //正股距收回价，仅牛熊证支持此字段（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double conversionPrice = 33; //换股价
    	required double streetRate = 34; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int64 streetVol = 35; //街货量
    	required double amplitude = 36; //振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int64 issueSize = 37; //发行量	        
    	required double highPrice = 39; //最高价
        required double lowPrice = 40; //最低价	
    	optional double impliedVolatility = 41; //引申波幅，仅认购认沽支持此字段
        optional double delta = 42; //对冲值，仅认购认沽支持此字段
    	required double effectiveLeverage = 43; //有效杠杆
    	optional double upperStrikePrice = 44; //上限价，仅界内证支持此字段
    	optional double lowerStrikePrice = 45; //下限价，仅界内证支持此字段
    	optional int32 inLinePriceStatus = 46; //Qot_Common.PriceType，界内界外，仅界内证支持此字段
    }
    
    message S2C
    {
        required bool lastPage = 1; //是否最后一页了，false:非最后一页，还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; //该条件请求所有数据的个数
        repeated WarrantData warrantDataList = 3; //窝轮数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
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
    
            QotGetWarrant.C2S c2s = QotGetWarrant.C2S.CreateBuilder()
                    .SetBegin(0)
                    .SetNum(50)
                    .SetSortField((int)QotCommon.SortField.SortField_Code)
                    .SetAscend(true)
                .Build();
            QotGetWarrant.Request req = QotGetWarrant.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetWarrant(req);
            Console.Write("Send QotGetWarrant: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetWarrant(FTAPI_Conn client, uint nSerialNo, QotGetWarrant.Response rsp)
        {
            Console.Write("Reply: QotGetWarrant: {0}\n", nSerialNo);
            Console.Write("retMsg: {0}\n", rsp.RetMsg);
            Console.Write("name: {0}, type: {1}\n", rsp.S2C.WarrantDataListList[0].Name,
                rsp.S2C.WarrantDataListList[0].Type);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825714488755091916
    Send QotGetWarrant: 3
    Reply: QotGetWarrant: 3
    retMsg:
    name: 腾讯麦银零乙购A.C, type: 1
    

1  
2  
3  
4  
5  

`int getWarrant(QotGetWarrant.Request req);`  
`void onReply_GetWarrant(FTAPI_Conn client, int nSerialNo, QotGetWarrant.Response rsp);`

*   **介绍**
    
    筛选窝轮（仅用于香港市场）
    
*   **参数**
    

    message C2S
    {
    	required int32 begin = 1; //数据起始点
    	required int32 num =  2; //请求数据个数，最大200
    	required int32 sortField = 3;//Qot_Common.SortField，根据哪个字段排序
    	required bool ascend = 4;//升序 ture，降序 false
    	
    	//以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security owner = 5;	//所属正股
    	repeated int32 typeList = 6; //Qot_Common.WarrantType，窝轮类型过滤列表
    	repeated int32 issuerList = 7; //Qot_Common.Issuer，发行人过滤列表
    	optional string maturityTimeMin = 8; //到期日，到期日范围的开始时间戳
    	optional string maturityTimeMax = 9; //到期日范围的结束时间戳
    	optional int32 ipoPeriod = 10; //Qot_Common.IpoPeriod，上市日
    	optional int32 priceType = 11; //Qot_Common.PriceType，价内/价外（暂不支持界内证的界内外筛选）
    	optional int32 status = 12; //Qot_Common.WarrantStatus，窝轮状态
    	optional double curPriceMin = 13; //最新价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double curPriceMax = 14; //最新价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 	
    	optional double strikePriceMin = 15; //行使价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double strikePriceMax = 16; //行使价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 
    	optional double streetMin = 17; //街货占比的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double streetMax = 18; //街货占比的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMin = 19; //换股比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMax = 20; //换股比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional uint64 volMin = 21; //成交量的过滤下限（闭区间），不传代表下限为 -∞
    	optional uint64 volMax = 22; //成交量的过滤上限（闭区间），不传代表上限为 +∞
    	optional double premiumMin = 23; //溢价的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double premiumMax = 24; //溢价的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMin = 25; //杠杆比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMax = 26; //杠杆比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMin = 27;//对冲值的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 28;//对冲值的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMin = 29; //引伸波幅的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMax = 30; //引伸波幅的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
    	optional double recoveryPriceMin = 31; //收回价的过滤下限（闭区间），仅牛熊证支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double recoveryPriceMax = 32; //收回价的过滤上限（闭区间），仅牛熊证支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMin = 33;//正股距收回价，的过滤下限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMax = 34;//正股距收回价，的过滤上限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序类型参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   上市日类型参见 [IpoPeriod](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9546)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     

*   **返回**

    message WarrantData
    {
    	//静态数据项
        required Qot_Common.Security stock = 1; //股票
        required Qot_Common.Security owner = 2; //所属正股
    	required int32 type = 3; //Qot_Common.WarrantType，窝轮类型
        required int32 issuer = 4; //Qot_Common.Issuer，发行人
    	required string maturityTime = 5; //到期日
    	optional double maturityTimestamp = 6; //到期日时间戳
    	required string listTime = 7; //上市时间（格式：yyyy-MM-dd）
    	optional double listTimestamp = 8; //上市时间戳
    	required string lastTradeTime = 9; //最后交易日
    	optional double lastTradeTimestamp = 10; //最后交易日时间戳
    	optional double recoveryPrice = 11; //收回价，仅牛熊证支持此字段
    	required double conversionRatio = 12; //换股比率
    	required int32 lotSize = 13; //每手数量
    	required double strikePrice = 14; //行使价	
    	required double lastClosePrice = 15; //昨收价        
    	required string name = 16; //名称	
    
    	//动态数据项
    	required double curPrice = 17; //当前价
    	required double priceChangeVal = 18; //涨跌额
        required double changeRate = 19; //涨跌幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int32 status = 20; //Qot_Common.WarrantStatus，窝轮状态	
        required double bidPrice = 21; //买入价	
    	required double askPrice = 22; //卖出价
        required int64 bidVol = 23; //买量
    	required int64 askVol = 24; //卖量
    	required int64 volume = 25; //成交量
        required double turnover = 26; //成交额	
    	required double score = 27; //综合评分
    	required double premium = 28; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double breakEvenPoint = 29; //打和点	
        required double leverage = 30; //杠杆比率（倍）
        required double ipop = 31; //价内/价外，正数表示价内，负数表示价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）        	
    	optional double priceRecoveryRatio = 32; //正股距收回价，仅牛熊证支持此字段（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double conversionPrice = 33; //换股价
    	required double streetRate = 34; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int64 streetVol = 35; //街货量
    	required double amplitude = 36; //振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int64 issueSize = 37; //发行量	        
    	required double highPrice = 39; //最高价
        required double lowPrice = 40; //最低价	
    	optional double impliedVolatility = 41; //引申波幅，仅认购认沽支持此字段
        optional double delta = 42; //对冲值，仅认购认沽支持此字段
    	required double effectiveLeverage = 43; //有效杠杆
    	optional double upperStrikePrice = 44; //上限价，仅界内证支持此字段
    	optional double lowerStrikePrice = 45; //下限价，仅界内证支持此字段
    	optional int32 inLinePriceStatus = 46; //Qot_Common.PriceType，界内界外，仅界内证支持此字段
    }
    
    message S2C
    {
        required bool lastPage = 1; //是否最后一页了，false:非最后一页，还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; //该条件请求所有数据的个数
        repeated WarrantData warrantDataList = 3; //窝轮数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
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
    
            QotGetWarrant.C2S c2s = QotGetWarrant.C2S.newBuilder()
                    .setBegin(0)
                    .setNum(50)
                    .setSortField(QotCommon.SortField.SortField_Code_VALUE)
                    .setAscend(true)
                .build();
            QotGetWarrant.Request req = QotGetWarrant.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getWarrant(req);
            System.out.printf("Send QotGetWarrant: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetWarrant(FTAPI_Conn client, int nSerialNo, QotGetWarrant.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetWarrant failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetWarrant: %s\n", json);
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

*   **Output**

    Send QotGetWarrant: 2
    Receive QotGetWarrant: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "lastPage": false,
        "allCount": 15552,
        "warrantDataList": [{\
          "stock": {\
            "market": 1,\
            "code": "10071"\
          },\
          "owner": {\
            "market": 11,\
            "code": ".DJI"\
          },\
          "type": 2,\
          "issuer": 7,\
          "maturityTime": "2021-06-18",\
          "maturityTimestamp": 1.6239456E9,\
          "listTime": "2020-11-10",\
          "listTimestamp": 1.6049376E9,\
          "lastTradeTime": "2021-06-11",\
          "lastTradeTimestamp": 1.6233408E9,\
          "conversionRatio": 66000.0,\
          "lotSize": 10000,\
          "strikePrice": 25000.0,\
          "lastClosePrice": 0.01,\
          "name": "道指汇丰一六沽B.P",\
          "curPrice": 0.01,\
          "priceChangeVal": 0.0,\
          "changeRate": 0.0,\
          "status": 3,\
          "bidPrice": 0.0,\
          "askPrice": 0.0,\
          "bidVol": "0",\
          "askVol": "0",\
          "volume": "0",\
          "turnover": 0.0,\
          "score": 0.02,\
          "premium": 0.0,\
          "breakEvenPoint": 24340.0,\
          "leverage": 0.0,\
          "ipop": 100.0,\
          "conversionPrice": 660.0,\
          "streetRate": 6.54,\
          "streetVol": "13080000",\
          "amplitude": 0.0,\
          "issueSize": "200000000",\
          "highPrice": 0.0,\
          "lowPrice": 0.0,\
          "impliedVolatility": 0.0,\
          "delta": 0.0,\
          "effectiveLeverage": 0.0\
        }, ... {\
          "stock": {\
            "market": 1,\
            "code": "11047"\
          },\
          "owner": {\
            "market": 1,\
            "code": "01211"\
          },\
          "type": 2,\
          "issuer": 21,\
          "maturityTime": "2021-11-26",\
          "maturityTimestamp": 1.637856E9,\
          "listTime": "2021-05-17",\
          "listTimestamp": 1.6211808E9,\
          "lastTradeTime": "2021-11-22",\
          "lastTradeTimestamp": 1.6375104E9,\
          "conversionRatio": 50.0,\
          "lotSize": 25000,\
          "strikePrice": 121.0,\
          "lastClosePrice": 0.026,\
          "name": "比迪瑞通一甲沽A.P",\
          "curPrice": 0.026,\
          "priceChangeVal": 0.0,\
          "changeRate": 0.0,\
          "status": 1,\
          "bidPrice": 0.026,\
          "askPrice": 0.029,\
          "bidVol": "125000",\
          "askVol": "600000",\
          "volume": "0",\
          "turnover": 0.0,\
          "score": 0.008,\
          "premium": 47.407,\
          "breakEvenPoint": 119.7,\
          "leverage": 175.076,\
          "ipop": -88.099,\
          "conversionPrice": 1.3,\
          "streetRate": 2.25,\
          "streetVol": "900000",\
          "amplitude": 0.0,\
          "issueSize": "40000000",\
          "highPrice": 0.0,\
          "lowPrice": 0.0,\
          "impliedVolatility": 58.629,\
          "delta": -0.032,\
          "effectiveLeverage": -5.602\
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

`Futu::u32_t GetWarrant(const Qot_GetWarrant::Request &stReq);`  
`virtual void OnReply_GetWarrant(Futu::u32_t nSerialNo, const Qot_GetWarrant::Response &stRsp) = 0;`

*   **介绍**
    
    筛选窝轮（仅用于香港市场）
    
*   **参数**
    

    message C2S
    {
    	required int32 begin = 1; //数据起始点
    	required int32 num =  2; //请求数据个数，最大200
    	required int32 sortField = 3;//Qot_Common.SortField，根据哪个字段排序
    	required bool ascend = 4;//升序 ture，降序 false
    	
    	//以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security owner = 5;	//所属正股
    	repeated int32 typeList = 6; //Qot_Common.WarrantType，窝轮类型过滤列表
    	repeated int32 issuerList = 7; //Qot_Common.Issuer，发行人过滤列表
    	optional string maturityTimeMin = 8; //到期日，到期日范围的开始时间戳
    	optional string maturityTimeMax = 9; //到期日范围的结束时间戳
    	optional int32 ipoPeriod = 10; //Qot_Common.IpoPeriod，上市日
    	optional int32 priceType = 11; //Qot_Common.PriceType，价内/价外（暂不支持界内证的界内外筛选）
    	optional int32 status = 12; //Qot_Common.WarrantStatus，窝轮状态
    	optional double curPriceMin = 13; //最新价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double curPriceMax = 14; //最新价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 	
    	optional double strikePriceMin = 15; //行使价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double strikePriceMax = 16; //行使价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 
    	optional double streetMin = 17; //街货占比的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double streetMax = 18; //街货占比的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMin = 19; //换股比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMax = 20; //换股比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional uint64 volMin = 21; //成交量的过滤下限（闭区间），不传代表下限为 -∞
    	optional uint64 volMax = 22; //成交量的过滤上限（闭区间），不传代表上限为 +∞
    	optional double premiumMin = 23; //溢价的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double premiumMax = 24; //溢价的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMin = 25; //杠杆比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMax = 26; //杠杆比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMin = 27;//对冲值的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 28;//对冲值的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMin = 29; //引伸波幅的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMax = 30; //引伸波幅的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
    	optional double recoveryPriceMin = 31; //收回价的过滤下限（闭区间），仅牛熊证支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double recoveryPriceMax = 32; //收回价的过滤上限（闭区间），仅牛熊证支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMin = 33;//正股距收回价，的过滤下限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMax = 34;//正股距收回价，的过滤上限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序类型参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   上市日类型参见 [IpoPeriod](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9546)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     

*   **返回**

    message WarrantData
    {
    	//静态数据项
        required Qot_Common.Security stock = 1; //股票
        required Qot_Common.Security owner = 2; //所属正股
    	required int32 type = 3; //Qot_Common.WarrantType，窝轮类型
        required int32 issuer = 4; //Qot_Common.Issuer，发行人
    	required string maturityTime = 5; //到期日
    	optional double maturityTimestamp = 6; //到期日时间戳
    	required string listTime = 7; //上市时间（格式：yyyy-MM-dd）
    	optional double listTimestamp = 8; //上市时间戳
    	required string lastTradeTime = 9; //最后交易日
    	optional double lastTradeTimestamp = 10; //最后交易日时间戳
    	optional double recoveryPrice = 11; //收回价，仅牛熊证支持此字段
    	required double conversionRatio = 12; //换股比率
    	required int32 lotSize = 13; //每手数量
    	required double strikePrice = 14; //行使价	
    	required double lastClosePrice = 15; //昨收价        
    	required string name = 16; //名称	
    
    	//动态数据项
    	required double curPrice = 17; //当前价
    	required double priceChangeVal = 18; //涨跌额
        required double changeRate = 19; //涨跌幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int32 status = 20; //Qot_Common.WarrantStatus，窝轮状态	
        required double bidPrice = 21; //买入价	
    	required double askPrice = 22; //卖出价
        required int64 bidVol = 23; //买量
    	required int64 askVol = 24; //卖量
    	required int64 volume = 25; //成交量
        required double turnover = 26; //成交额	
    	required double score = 27; //综合评分
    	required double premium = 28; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double breakEvenPoint = 29; //打和点	
        required double leverage = 30; //杠杆比率（倍）
        required double ipop = 31; //价内/价外，正数表示价内，负数表示价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）        	
    	optional double priceRecoveryRatio = 32; //正股距收回价，仅牛熊证支持此字段（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double conversionPrice = 33; //换股价
    	required double streetRate = 34; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int64 streetVol = 35; //街货量
    	required double amplitude = 36; //振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int64 issueSize = 37; //发行量	        
    	required double highPrice = 39; //最高价
        required double lowPrice = 40; //最低价	
    	optional double impliedVolatility = 41; //引申波幅，仅认购认沽支持此字段
        optional double delta = 42; //对冲值，仅认购认沽支持此字段
    	required double effectiveLeverage = 43; //有效杠杆
    	optional double upperStrikePrice = 44; //上限价，仅界内证支持此字段
    	optional double lowerStrikePrice = 45; //下限价，仅界内证支持此字段
    	optional int32 inLinePriceStatus = 46; //Qot_Common.PriceType，界内界外，仅界内证支持此字段
    }
    
    message S2C
    {
        required bool lastPage = 1; //是否最后一页了，false:非最后一页，还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; //该条件请求所有数据的个数
        repeated WarrantData warrantDataList = 3; //窝轮数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
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
    		Qot_GetWarrant::Request req;
    		Qot_GetWarrant::C2S *c2s = req.mutable_c2s();
    		c2s->set_begin(0);
    		c2s->set_num(50);
    		c2s->set_sortfield(1);
    		c2s->set_ascend(false);
    
    		m_GetWarrantSerialNo = m_pQotApi->GetWarrant(req);
    		cout << "Request GetWarrant SerialNo: " << m_GetWarrantSerialNo << endl;
    	}
    
    	virtual void OnReply_GetWarrant(Futu::u32_t nSerialNo, const Qot_GetWarrant::Response &stRsp){
            if(nSerialNo == m_GetWarrantSerialNo)
            {
                cout << "OnReply_GetWarrant SerialNo:" << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetWarrantSerialNo;
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
    Request GetWarrant SerialNo: 4
    OnReply_GetWarrant SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "lastPage": false,
      "allCount": 15684,
      "warrantDataList": [\
       {\
        "stock": {\
         "market": 1,\
         "code": "69999"\
        },\
        "owner": {\
         "market": 1,\
         "code": "01211"\
        },\
        "type": 3,\
        "issuer": 1,\
        "maturityTime": "2022-02-28",\
        "maturityTimestamp": 1645977600,\
        "listTime": "2021-06-11",\
        "listTimestamp": 1623340800,\
        "lastTradeTime": "2022-02-25",\
        "lastTradeTimestamp": 1645718400,\
        "recoveryPrice": 193,\
        "conversionRatio": 500,\
        "lotSize": 25000,\
        "strikePrice": 189,\
        "lastClosePrice": 0,\
        "name": "比迪法兴二二牛P",\
        "curPrice": 0,\
        "priceChangeVal": 0,\
        "changeRate": 0,\
        "status": 4,\
        "bidPrice": 0,\
        "askPrice": 0,\
        "bidVol": "0",\
        "askVol": "0",\
        "volume": "0",\
        "turnover": 0,\
        "score": 0,\
        "premium": -5.782,\
        "breakEvenPoint": 189,\
        "leverage": 0,\
        "ipop": 6.137,\
        "priceRecoveryRatio": 3.9378238341968879,\
        "conversionPrice": 0,\
        "streetRate": 0,\
        "streetVol": "0",\
        "amplitude": 0,\
        "issueSize": "150000000",\
        "highPrice": 0,\
        "lowPrice": 0,\
        "effectiveLeverage": 0\
       },\
    ...\
       {\
        "stock": {\
         "market": 1,\
         "code": "69897"\
        },\
        "owner": {\
         "market": 1,\
         "code": "00700"\
        },\
        "type": 4,\
        "issuer": 3,\
        "maturityTime": "2021-12-30",\
        "maturityTimestamp": 1640793600,\
        "listTime": "2021-06-11",\
        "listTimestamp": 1623340800,\
        "lastTradeTime": "2021-12-29",\
        "lastTradeTimestamp": 1640707200,\
        "recoveryPrice": 608,\
        "conversionRatio": 500,\
        "lotSize": 5000,\
        "strikePrice": 610.8,\
        "lastClosePrice": 0,\
        "name": "腾讯瑞信一乙熊G",\
        "curPrice": 0,\
        "priceChangeVal": 0,\
        "changeRate": 0,\
        "status": 4,\
        "bidPrice": 0,\
        "askPrice": 0,\
        "bidVol": "0",\
        "askVol": "0",\
        "volume": "0",\
        "turnover": 0,\
        "score": 0,\
        "premium": -1.125,\
        "breakEvenPoint": 610.8,\
        "leverage": 0,\
        "ipop": 1.113,\
        "priceRecoveryRatio": -0.6578947368421052,\
        "conversionPrice": 0,\
        "streetRate": 0,\
        "streetVol": "0",\
        "amplitude": 0,\
        "issueSize": "100000000",\
        "highPrice": 0,\
        "lowPrice": 0,\
        "effectiveLeverage": 0\
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

`GetWarrant(req);`

*   **介绍**
    
    筛选窝轮（仅用于香港市场）
    
*   **参数**
    

    message C2S
    {
    	required int32 begin = 1; //数据起始点
    	required int32 num =  2; //请求数据个数，最大200
    	required int32 sortField = 3;//Qot_Common.SortField，根据哪个字段排序
    	required bool ascend = 4;//升序 ture，降序 false
    	
    	//以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security owner = 5;	//所属正股
    	repeated int32 typeList = 6; //Qot_Common.WarrantType，窝轮类型过滤列表
    	repeated int32 issuerList = 7; //Qot_Common.Issuer，发行人过滤列表
    	optional string maturityTimeMin = 8; //到期日，到期日范围的开始时间戳
    	optional string maturityTimeMax = 9; //到期日范围的结束时间戳
    	optional int32 ipoPeriod = 10; //Qot_Common.IpoPeriod，上市日
    	optional int32 priceType = 11; //Qot_Common.PriceType，价内/价外（暂不支持界内证的界内外筛选）
    	optional int32 status = 12; //Qot_Common.WarrantStatus，窝轮状态
    	optional double curPriceMin = 13; //最新价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double curPriceMax = 14; //最新价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 	
    	optional double strikePriceMin = 15; //行使价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double strikePriceMax = 16; //行使价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 
    	optional double streetMin = 17; //街货占比的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double streetMax = 18; //街货占比的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMin = 19; //换股比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMax = 20; //换股比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional uint64 volMin = 21; //成交量的过滤下限（闭区间），不传代表下限为 -∞
    	optional uint64 volMax = 22; //成交量的过滤上限（闭区间），不传代表上限为 +∞
    	optional double premiumMin = 23; //溢价的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double premiumMax = 24; //溢价的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMin = 25; //杠杆比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMax = 26; //杠杆比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMin = 27;//对冲值的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 28;//对冲值的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMin = 29; //引伸波幅的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMax = 30; //引伸波幅的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
    	optional double recoveryPriceMin = 31; //收回价的过滤下限（闭区间），仅牛熊证支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double recoveryPriceMax = 32; //收回价的过滤上限（闭区间），仅牛熊证支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMin = 33;//正股距收回价，的过滤下限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMax = 34;//正股距收回价，的过滤上限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序类型参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   上市日类型参见 [IpoPeriod](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9546)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     

*   **返回**

    message WarrantData
    {
    	//静态数据项
        required Qot_Common.Security stock = 1; //股票
        required Qot_Common.Security owner = 2; //所属正股
    	required int32 type = 3; //Qot_Common.WarrantType，窝轮类型
        required int32 issuer = 4; //Qot_Common.Issuer，发行人
    	required string maturityTime = 5; //到期日
    	optional double maturityTimestamp = 6; //到期日时间戳
    	required string listTime = 7; //上市时间（格式：yyyy-MM-dd）
    	optional double listTimestamp = 8; //上市时间戳
    	required string lastTradeTime = 9; //最后交易日
    	optional double lastTradeTimestamp = 10; //最后交易日时间戳
    	optional double recoveryPrice = 11; //收回价，仅牛熊证支持此字段
    	required double conversionRatio = 12; //换股比率
    	required int32 lotSize = 13; //每手数量
    	required double strikePrice = 14; //行使价	
    	required double lastClosePrice = 15; //昨收价        
    	required string name = 16; //名称	
    
    	//动态数据项
    	required double curPrice = 17; //当前价
    	required double priceChangeVal = 18; //涨跌额
        required double changeRate = 19; //涨跌幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int32 status = 20; //Qot_Common.WarrantStatus，窝轮状态	
        required double bidPrice = 21; //买入价	
    	required double askPrice = 22; //卖出价
        required int64 bidVol = 23; //买量
    	required int64 askVol = 24; //卖量
    	required int64 volume = 25; //成交量
        required double turnover = 26; //成交额	
    	required double score = 27; //综合评分
    	required double premium = 28; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double breakEvenPoint = 29; //打和点	
        required double leverage = 30; //杠杆比率（倍）
        required double ipop = 31; //价内/价外，正数表示价内，负数表示价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）        	
    	optional double priceRecoveryRatio = 32; //正股距收回价，仅牛熊证支持此字段（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double conversionPrice = 33; //换股价
    	required double streetRate = 34; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int64 streetVol = 35; //街货量
    	required double amplitude = 36; //振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int64 issueSize = 37; //发行量	        
    	required double highPrice = 39; //最高价
        required double lowPrice = 40; //最低价	
    	optional double impliedVolatility = 41; //引申波幅，仅认购认沽支持此字段
        optional double delta = 42; //对冲值，仅认购认沽支持此字段
    	required double effectiveLeverage = 43; //有效杠杆
    	optional double upperStrikePrice = 44; //上限价，仅界内证支持此字段
    	optional double lowerStrikePrice = 45; //下限价，仅界内证支持此字段
    	optional int32 inLinePriceStatus = 46; //Qot_Common.PriceType，界内界外，仅界内证支持此字段
    }
    
    message S2C
    {
        required bool lastPage = 1; //是否最后一页了，false:非最后一页，还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; //该条件请求所有数据的个数
        repeated WarrantData warrantDataList = 3; //窝轮数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetWarrant(){
        const { RetType } = Common
        const { SortField, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        begin: 0,
                        num: 2,
                        sortField: SortField.SortField_CurPrice,
                        ascend: true,
    
                        owner:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                    },
                };
    
                websocket.GetWarrant(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("Warrant: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
58  

*   **Output**

    Warrant: errCode 0, retMsg , retType 0
    {
      "lastPage": false,
      "allCount": 1401,
      "warrantDataList": [{\
        "stock": {\
          "market": 1,\
          "code": "50710"\
        },\
        "owner": {\
          "market": 1,\
          "code": "00700"\
        },\
        "type": 3,\
        "issuer": 7,\
        "maturityTime": "2022-04-14",\
        "maturityTimestamp": 1649865600,\
        "listTime": "2021-09-10",\
        "listTimestamp": 1631203200,\
        "lastTradeTime": "2022-04-13",\
        "lastTradeTimestamp": 1649779200,\
        "recoveryPrice": 502.88,\
        "conversionRatio": 500,\
        "lotSize": 50000,\
        "strikePrice": 498.88,\
        "lastClosePrice": 0,\
        "name": "腾讯汇丰二四牛C.C",\
        "curPrice": 0,\
        "priceChangeVal": 0,\
        "changeRate": 0,\
        "status": 3,\
        "bidPrice": 0,\
        "askPrice": 0,\
        "bidVol": "0",\
        "askVol": "0",\
        "volume": "0",\
        "turnover": 0,\
        "score": 0.199,\
        "premium": 2.819,\
        "breakEvenPoint": 498.88,\
        "leverage": 0,\
        "ipop": -2.742,\
        "priceRecoveryRatio": -3.5157492841234506,\
        "conversionPrice": 0,\
        "streetRate": 0,\
        "streetVol": "0",\
        "amplitude": 0,\
        "issueSize": "100000000",\
        "highPrice": 0,\
        "lowPrice": 0,\
        "effectiveLeverage": 0\
      }, {\
        "stock": {\
          "market": 1,\
          "code": "50833"\
        },\
        "owner": {\
          "market": 1,\
          "code": "00700"\
        },\
        "type": 3,\
        "issuer": 11,\
        "maturityTime": "2022-01-28",\
        "maturityTimestamp": 1643299200,\
        "listTime": "2021-09-10",\
        "listTimestamp": 1631203200,\
        "lastTradeTime": "2022-01-27",\
        "lastTradeTimestamp": 1643212800,\
        "recoveryPrice": 518,\
        "conversionRatio": 100,\
        "lotSize": 10000,\
        "strikePrice": 515,\
        "lastClosePrice": 0,\
        "name": "腾讯瑞银二一牛C.C",\
        "curPrice": 0,\
        "priceChangeVal": 0,\
        "changeRate": 0,\
        "status": 3,\
        "bidPrice": 0,\
        "askPrice": 0,\
        "bidVol": "0",\
        "askVol": "0",\
        "volume": "0",\
        "turnover": 0,\
        "score": 0.196,\
        "premium": 6.141,\
        "breakEvenPoint": 515,\
        "leverage": 0,\
        "ipop": -5.786,\
        "priceRecoveryRatio": -6.3320463320463345,\
        "conversionPrice": 0,\
        "streetRate": 0,\
        "streetVol": "0",\
        "amplitude": 0,\
        "issueSize": "40000000",\
        "highPrice": 0,\
        "lowPrice": 0,\
        "effectiveLeverage": 0\
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

接口限制

*   港股 BMP 权限不支持调用此接口
*   每 30 秒内最多请求 60 次筛选窝轮接口
*   每次请求的数据个数上限为 200 个

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_warrant(stock_owner='', req=None)`

*   **介绍**
    
    筛选窝轮（仅用于筛选香港市场的窝轮、牛熊证、界内证）
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | stock\_owner | str | 所属正股的股票代码 |
    | req | WarrantRequest | 筛选参数组合 |
    
    *   WarrantRequest 类型字段说明如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | begin | int | 数据起始点 |
        | num | int | 请求数据个数<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>最大 200 |
        | sort\_field | [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930) | 根据哪个字段排序 |
        | ascend | bool | 排序方向<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：升序  <br>False：降序 |
        | type\_list | list | 窝轮类型过滤列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 [WrtType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926) |
        | issuer\_list | list | 发行人过滤列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363) |
        | maturity\_time\_min | str | 到期日过滤范围的开始时间 |
        | maturity\_time\_max | str | 到期日过滤范围的结束时间 |
        | ipo\_period | [IpoPeriod](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9546) | 上市时段 |
        | price\_type | [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407) | 价内/价外<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>暂不支持界内证的界内外筛选 |
        | status | [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556) | 窝轮状态 |
        | cur\_price\_min | float | 最新价的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | cur\_price\_max | float | 最新价的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | strike\_price\_min | float | 行使价的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | strike\_price\_max | float | 行使价的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | street\_min | float | 街货占比的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | street\_max | float | 街货占比的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | conversion\_min | float | 换股比率的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | conversion\_max | float | 换股比率的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | vol\_min | int | 成交量的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞ |
        | vol\_max | int | 成交量的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞ |
        | premium\_min | float | 溢价的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | premium\_max | float | 溢价的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | leverage\_ratio\_min | float | 杠杆比率的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | leverage\_ratio\_max | float | 杠杆比率的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>不传代表上限为 +∞ |
        | delta\_min | float | 对冲值的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅认购认沽支持此字段过滤  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | delta\_max | float | 对冲值的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅认购认沽支持此字段过滤  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | implied\_min | float | 引伸波幅的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅认购认沽支持此字段过滤  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | implied\_max | float | 引伸波幅的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅认购认沽支持此字段过滤  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | recovery\_price\_min | float | 收回价的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅牛熊证支持此字段过滤  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | recovery\_price\_max | float | 收回价的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅牛熊证支持此字段过滤  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | price\_recovery\_ratio\_min | float | 正股距收回价的过滤下限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅牛熊证支持此字段过滤  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%  <br>不传代表下限为 -∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        | price\_recovery\_ratio\_max | float | 正股距收回价的过滤上限<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>闭区间  <br>仅牛熊证支持此字段过滤  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20%  <br>不传代表上限为 +∞  <br>精确到小数点后 3 位，超出部分会被舍弃 |
        

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | tuple | 当 ret == RET\_OK，返回窝轮数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   窝轮数据组成如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | warrant\_data\_list | pd.DataFrame | 筛选后的窝轮数据 |
        | last\_page | bool | 是否是最后一页<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>True：是最后一页  <br>False：不是最后一页 |
        | all\_count | int | 筛选结果中的窝轮总数量 |
        
        *   warrant\_data\_list 返回的 pd dataframe 数据格式：
            
            | 字段  | 类型  | 说明  |
            | --- | --- | --- |
            | stock | str | 窝轮代码 |
            | stock\_owner | str | 所属正股 |
            | type | [WrtType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926) | 窝轮类型 |
            | issuer | [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363) | 发行人 |
            | maturity\_time | str | 到期日<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd |
            | list\_time | str | 上市时间<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd |
            | last\_trade\_time | str | 最后交易日<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>格式：yyyy-MM-dd |
            | recovery\_price | float | 收回价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅牛熊证支持此字段 |
            | conversion\_ratio | float | 换股比率 |
            | lot\_size | int | 每手数量 |
            | strike\_price | float | 行使价 |
            | last\_close\_price | float | 昨收价 |
            | name | str | 名称  |
            | cur\_price | float | 当前价 |
            | price\_change\_val | float | 涨跌额 |
            | status | [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556) | 窝轮状态 |
            | bid\_price | float | 买入价 |
            | ask\_price | float | 卖出价 |
            | bid\_vol | int | 买量  |
            | ask\_vol | int | 卖量  |
            | volume | int | 成交量 |
            | turnover | float | 成交额 |
            | score | float | 综合评分 |
            | premium | float | 溢价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
            | break\_even\_point | float | 打和点 |
            | leverage | float | 杠杆比率<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>单位：倍 |
            | ipop | float | 价内/价外<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
            | price\_recovery\_ratio | float | 正股距收回价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅牛熊证支持此字段  <br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
            | conversion\_price | float | 换股价 |
            | street\_rate | float | 街货占比<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
            | street\_vol | int | 街货量 |
            | amplitude | float | 振幅<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>该字段为百分比字段，默认不展示 %，如 20 实际对应 20% |
            | issue\_size | int | 发行量 |
            | high\_price | float | 最高价 |
            | low\_price | float | 最低价 |
            | implied\_volatility | float | 引伸波幅<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅认购认沽支持此字段 |
            | delta | float | 对冲值<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅认购认沽支持此字段 |
            | effective\_leverage | float | 有效杠杆 |
            | upper\_strike\_price | float | 上限价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅界内证支持此字段 |
            | lower\_strike\_price | float | 下限价<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅界内证支持此字段 |
            | inline\_price\_status | [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407) | 界内界外<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>仅界内证支持此字段 |
            
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    req = WarrantRequest()
    req.sort_field = SortField.TURNOVER
    req.type_list = WrtType.CALL
    req.cur_price_min = 0.1
    req.cur_price_max = 0.2
    ret, ls = quote_ctx.get_warrant("HK.00700", req)
    if ret == RET_OK:  # 先判断接口返回是否正常，再取数据
        warrant_data_list, last_page, all_count = ls
        print(len(warrant_data_list), all_count, warrant_data_list)
        print(warrant_data_list['stock'][0])    # 取第一条的窝轮代码
        print(warrant_data_list['stock'].values.tolist())   # 转为 list
    else:
        print('error: ', ls)
        
    req = WarrantRequest()
    req.sort_field = SortField.TURNOVER
    req.issuer_list = ['UB','CS','BI']
    ret, ls = quote_ctx.get_warrant(Market.HK, req)
    if ret == RET_OK: 
        warrant_data_list, last_page, all_count = ls
        print(len(warrant_data_list), all_count, warrant_data_list)
    else:
        print('error: ', ls)
    
    quote_ctx.close()  # 所有接口结尾加上这条 close，防止连接条数用尽
    

1  
2  
3  
4  
5  
6  
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

*   **Output**

    2 2 
        stock        name stock_owner  type issuer maturity_time   list_time last_trade_time  recovery_price  conversion_ratio  lot_size  strike_price  last_close_price  cur_price  price_change_val  change_rate  status  bid_price  ask_price   bid_vol  ask_vol    volume   turnover   score  premium  break_even_point  leverage    ipop  price_recovery_ratio  conversion_price  street_rate  street_vol  amplitude  issue_size  high_price  low_price  implied_volatility  delta  effective_leverage  list_timestamp  last_trade_timestamp  maturity_timestamp  upper_strike_price  lower_strike_price  inline_price_status
    0   HK.20306  腾讯麦银零乙购A.C    HK.00700  CALL     MB    2020-12-01  2019-06-27      2020-11-25             NaN              50.0      5000        588.88             0.188      0.188             0.000     0.000000  NORMAL      0.000      0.188         0     10000           0          0.0   0.198    2.008            598.28    62.393  -0.404                   NaN              9.40        4.400     1584000      0.000    36000000       0.000      0.000              31.751  0.479              29.886    1.561565e+09          1.606234e+09        1.606752e+09                 NaN                 NaN                  NaN
    1   HK.16545  腾讯法兴一二购B.C    HK.00700  CALL     SG    2021-02-26  2020-07-14      2021-02-22             NaN             100.0     10000        700.00             0.147      0.144            -0.003    -2.040816  NORMAL      0.141      0.144  28000000  28000000           0          0.0  81.506   21.807            714.40    40.729 -16.214                   NaN             14.40        1.420     2130000      0.000   150000000       0.000      0.000              40.643  0.226               9.204    1.594656e+09          1.613923e+09        1.614269e+09                 NaN                 NaN                  NaN
    HK.20306
    ['HK.20306', 'HK.16545']
    
    200 358
        stock        name stock_owner    type issuer maturity_time   list_time last_trade_time  recovery_price  conversion_ratio  lot_size  strike_price  last_close_price  cur_price  price_change_val  change_rate      status  bid_price  ask_price   bid_vol   ask_vol  volume  turnover   score  premium  break_even_point  leverage     ipop  price_recovery_ratio  conversion_price  street_rate  street_vol  amplitude  issue_size  high_price  low_price  implied_volatility  delta  effective_leverage  list_timestamp  last_trade_timestamp  maturity_timestamp  upper_strike_price  lower_strike_price inline_price_status
    0    HK.19839  平安瑞银零乙购A.C    HK.02318    CALL     UB    2020-12-31  2017-12-11      2020-12-24             NaN             100.0     50000         83.88             0.057      0.046            -0.011   -19.298246      NORMAL      0.043      0.046  30000000  30000000       0       0.0  39.641    1.642            88.480    18.923    3.779                   NaN             4.600         1.25     6250000        0.0   500000000         0.0        0.0              25.129  0.692              13.094    1.512922e+09          1.608739e+09        1.609344e+09                 NaN                 NaN                 NaN
    1    HK.20084  平安中银零乙购A.C    HK.02318    CALL     BI    2020-12-31  2017-12-19      2020-12-24             NaN             100.0     50000         83.88             0.059      0.050            -0.009   -15.254237      NORMAL      0.044      0.050  10000000  10000000       0       0.0   0.064    2.102            88.880    17.410    3.779                   NaN             5.000         0.07      350000        0.0   500000000         0.0        0.0              29.174  0.672              11.699    1.513613e+09          1.608739e+09        1.609344e+09                 NaN                 NaN                 NaN
    ......
    198  HK.56886  恒指瑞银三一牛F.C   HK.800000    BULL     UB    2023-01-30  2020-03-24      2023-01-27         21200.0           20000.0     10000      21100.00             0.230      0.232             0.002     0.869565      NORMAL      0.232      0.233  30000000  30000000       0       0.0  46.627   -2.884         25740.000     5.712   25.613             25.021179          4640.000         0.01       40000        0.0   400000000         0.0        0.0                 NaN    NaN               5.712    1.584979e+09          1.674749e+09        1.675008e+09                 NaN                 NaN                 NaN
    199  HK.56895  小米瑞银零乙牛D.C    HK.01810    BULL     UB    2020-12-30  2020-03-24      2020-12-29             8.0              10.0      2000          7.60             2.010      1.930            -0.080    -3.980100      NORMAL      1.910      1.930   6000000   6000000       0       0.0   0.040    0.938            26.900     1.380  250.657            233.125000            19.300         0.10       60000        0.0    60000000         0.0        0.0                 NaN    NaN               1.380    1.584979e+09          1.609171e+09        1.609258e+09                 NaN                 NaN                 NaN
    
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html#8023-2)
 Qot\_GetWarrant.proto
-------------------------------------------------------------------------------------------------

*   **介绍**
    
    筛选窝轮（仅用于香港市场）
    
*   **参数**
    

    message C2S
    {
    	required int32 begin = 1; //数据起始点
    	required int32 num =  2; //请求数据个数，最大200
    	required int32 sortField = 3;//Qot_Common.SortField，根据哪个字段排序
    	required bool ascend = 4;//升序 ture，降序 false
    	
    	//以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security owner = 5;	//所属正股
    	repeated int32 typeList = 6; //Qot_Common.WarrantType，窝轮类型过滤列表
    	repeated int32 issuerList = 7; //Qot_Common.Issuer，发行人过滤列表
    	optional string maturityTimeMin = 8; //到期日，到期日范围的开始时间戳
    	optional string maturityTimeMax = 9; //到期日范围的结束时间戳
    	optional int32 ipoPeriod = 10; //Qot_Common.IpoPeriod，上市日
    	optional int32 priceType = 11; //Qot_Common.PriceType，价内/价外（暂不支持界内证的界内外筛选）
    	optional int32 status = 12; //Qot_Common.WarrantStatus，窝轮状态
    	optional double curPriceMin = 13; //最新价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double curPriceMax = 14; //最新价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 	
    	optional double strikePriceMin = 15; //行使价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double strikePriceMax = 16; //行使价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 
    	optional double streetMin = 17; //街货占比的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double streetMax = 18; //街货占比的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMin = 19; //换股比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMax = 20; //换股比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional uint64 volMin = 21; //成交量的过滤下限（闭区间），不传代表下限为 -∞
    	optional uint64 volMax = 22; //成交量的过滤上限（闭区间），不传代表上限为 +∞
    	optional double premiumMin = 23; //溢价的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double premiumMax = 24; //溢价的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMin = 25; //杠杆比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMax = 26; //杠杆比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMin = 27;//对冲值的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 28;//对冲值的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMin = 29; //引伸波幅的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMax = 30; //引伸波幅的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
    	optional double recoveryPriceMin = 31; //收回价的过滤下限（闭区间），仅牛熊证支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double recoveryPriceMax = 32; //收回价的过滤上限（闭区间），仅牛熊证支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMin = 33;//正股距收回价，的过滤下限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMax = 34;//正股距收回价，的过滤上限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序类型参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   上市日类型参见 [IpoPeriod](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9546)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     

*   **返回**

    message WarrantData
    {
    	//静态数据项
        required Qot_Common.Security stock = 1; //股票
        required Qot_Common.Security owner = 2; //所属正股
    	required int32 type = 3; //Qot_Common.WarrantType，窝轮类型
        required int32 issuer = 4; //Qot_Common.Issuer，发行人
    	required string maturityTime = 5; //到期日
    	optional double maturityTimestamp = 6; //到期日时间戳
    	required string listTime = 7; //上市时间（格式：yyyy-MM-dd）
    	optional double listTimestamp = 8; //上市时间戳
    	required string lastTradeTime = 9; //最后交易日
    	optional double lastTradeTimestamp = 10; //最后交易日时间戳
    	optional double recoveryPrice = 11; //收回价，仅牛熊证支持此字段
    	required double conversionRatio = 12; //换股比率
    	required int32 lotSize = 13; //每手数量
    	required double strikePrice = 14; //行使价	
    	required double lastClosePrice = 15; //昨收价        
    	required string name = 16; //名称	
    
    	//动态数据项
    	required double curPrice = 17; //当前价
    	required double priceChangeVal = 18; //涨跌额
        required double changeRate = 19; //涨跌幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int32 status = 20; //Qot_Common.WarrantStatus，窝轮状态	
        required double bidPrice = 21; //买入价	
    	required double askPrice = 22; //卖出价
        required int64 bidVol = 23; //买量
    	required int64 askVol = 24; //卖量
    	required int64 volume = 25; //成交量
        required double turnover = 26; //成交额	
    	required double score = 27; //综合评分
    	required double premium = 28; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double breakEvenPoint = 29; //打和点	
        required double leverage = 30; //杠杆比率（倍）
        required double ipop = 31; //价内/价外，正数表示价内，负数表示价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）        	
    	optional double priceRecoveryRatio = 32; //正股距收回价，仅牛熊证支持此字段（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double conversionPrice = 33; //换股价
    	required double streetRate = 34; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int64 streetVol = 35; //街货量
    	required double amplitude = 36; //振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int64 issueSize = 37; //发行量	        
    	required double highPrice = 39; //最高价
        required double lowPrice = 40; //最低价	
    	optional double impliedVolatility = 41; //引申波幅，仅认购认沽支持此字段
        optional double delta = 42; //对冲值，仅认购认沽支持此字段
    	required double effectiveLeverage = 43; //有效杠杆
    	optional double upperStrikePrice = 44; //上限价，仅界内证支持此字段
    	optional double lowerStrikePrice = 45; //下限价，仅界内证支持此字段
    	optional int32 inLinePriceStatus = 46; //Qot_Common.PriceType，界内界外，仅界内证支持此字段
    }
    
    message S2C
    {
        required bool lastPage = 1; //是否最后一页了，false:非最后一页，还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; //该条件请求所有数据的个数
        repeated WarrantData warrantDataList = 3; //窝轮数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3210
    

`uint GetWarrant(QotGetWarrant.Request req);`  
`virtual void OnReply_GetWarrant(MMAPI_Conn client, uint nSerialNo, QotGetWarrant.Response rsp);`

*   **介绍**
    
    筛选窝轮（仅用于香港市场）
    
*   **参数**
    

    message C2S
    {
    	required int32 begin = 1; //数据起始点
    	required int32 num =  2; //请求数据个数，最大200
    	required int32 sortField = 3;//Qot_Common.SortField，根据哪个字段排序
    	required bool ascend = 4;//升序 ture，降序 false
    	
    	//以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security owner = 5;	//所属正股
    	repeated int32 typeList = 6; //Qot_Common.WarrantType，窝轮类型过滤列表
    	repeated int32 issuerList = 7; //Qot_Common.Issuer，发行人过滤列表
    	optional string maturityTimeMin = 8; //到期日，到期日范围的开始时间戳
    	optional string maturityTimeMax = 9; //到期日范围的结束时间戳
    	optional int32 ipoPeriod = 10; //Qot_Common.IpoPeriod，上市日
    	optional int32 priceType = 11; //Qot_Common.PriceType，价内/价外（暂不支持界内证的界内外筛选）
    	optional int32 status = 12; //Qot_Common.WarrantStatus，窝轮状态
    	optional double curPriceMin = 13; //最新价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double curPriceMax = 14; //最新价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 	
    	optional double strikePriceMin = 15; //行使价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double strikePriceMax = 16; //行使价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 
    	optional double streetMin = 17; //街货占比的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double streetMax = 18; //街货占比的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMin = 19; //换股比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMax = 20; //换股比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional uint64 volMin = 21; //成交量的过滤下限（闭区间），不传代表下限为 -∞
    	optional uint64 volMax = 22; //成交量的过滤上限（闭区间），不传代表上限为 +∞
    	optional double premiumMin = 23; //溢价的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double premiumMax = 24; //溢价的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMin = 25; //杠杆比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMax = 26; //杠杆比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMin = 27;//对冲值的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 28;//对冲值的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMin = 29; //引伸波幅的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMax = 30; //引伸波幅的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
    	optional double recoveryPriceMin = 31; //收回价的过滤下限（闭区间），仅牛熊证支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double recoveryPriceMax = 32; //收回价的过滤上限（闭区间），仅牛熊证支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMin = 33;//正股距收回价，的过滤下限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMax = 34;//正股距收回价，的过滤上限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序类型参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   上市日类型参见 [IpoPeriod](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9546)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     

*   **返回**

    message WarrantData
    {
    	//静态数据项
        required Qot_Common.Security stock = 1; //股票
        required Qot_Common.Security owner = 2; //所属正股
    	required int32 type = 3; //Qot_Common.WarrantType，窝轮类型
        required int32 issuer = 4; //Qot_Common.Issuer，发行人
    	required string maturityTime = 5; //到期日
    	optional double maturityTimestamp = 6; //到期日时间戳
    	required string listTime = 7; //上市时间（格式：yyyy-MM-dd）
    	optional double listTimestamp = 8; //上市时间戳
    	required string lastTradeTime = 9; //最后交易日
    	optional double lastTradeTimestamp = 10; //最后交易日时间戳
    	optional double recoveryPrice = 11; //收回价，仅牛熊证支持此字段
    	required double conversionRatio = 12; //换股比率
    	required int32 lotSize = 13; //每手数量
    	required double strikePrice = 14; //行使价	
    	required double lastClosePrice = 15; //昨收价        
    	required string name = 16; //名称	
    
    	//动态数据项
    	required double curPrice = 17; //当前价
    	required double priceChangeVal = 18; //涨跌额
        required double changeRate = 19; //涨跌幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int32 status = 20; //Qot_Common.WarrantStatus，窝轮状态	
        required double bidPrice = 21; //买入价	
    	required double askPrice = 22; //卖出价
        required int64 bidVol = 23; //买量
    	required int64 askVol = 24; //卖量
    	required int64 volume = 25; //成交量
        required double turnover = 26; //成交额	
    	required double score = 27; //综合评分
    	required double premium = 28; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double breakEvenPoint = 29; //打和点	
        required double leverage = 30; //杠杆比率（倍）
        required double ipop = 31; //价内/价外，正数表示价内，负数表示价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）        	
    	optional double priceRecoveryRatio = 32; //正股距收回价，仅牛熊证支持此字段（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double conversionPrice = 33; //换股价
    	required double streetRate = 34; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int64 streetVol = 35; //街货量
    	required double amplitude = 36; //振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int64 issueSize = 37; //发行量	        
    	required double highPrice = 39; //最高价
        required double lowPrice = 40; //最低价	
    	optional double impliedVolatility = 41; //引申波幅，仅认购认沽支持此字段
        optional double delta = 42; //对冲值，仅认购认沽支持此字段
    	required double effectiveLeverage = 43; //有效杠杆
    	optional double upperStrikePrice = 44; //上限价，仅界内证支持此字段
    	optional double lowerStrikePrice = 45; //下限价，仅界内证支持此字段
    	optional int32 inLinePriceStatus = 46; //Qot_Common.PriceType，界内界外，仅界内证支持此字段
    }
    
    message S2C
    {
        required bool lastPage = 1; //是否最后一页了，false:非最后一页，还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; //该条件请求所有数据的个数
        repeated WarrantData warrantDataList = 3; //窝轮数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
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
    
            QotGetWarrant.C2S c2s = QotGetWarrant.C2S.CreateBuilder()
                    .SetBegin(0)
                    .SetNum(50)
                    .SetSortField((int)QotCommon.SortField.SortField_Code)
                    .SetAscend(true)
                .Build();
            QotGetWarrant.Request req = QotGetWarrant.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetWarrant(req);
            Console.Write("Send QotGetWarrant: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetWarrant(MMAPI_Conn client, uint nSerialNo, QotGetWarrant.Response rsp)
        {
            Console.Write("Reply: QotGetWarrant: {0}\n", nSerialNo);
            Console.Write("retMsg: {0}\n", rsp.RetMsg);
            Console.Write("name: {0}, type: {1}\n", rsp.S2C.WarrantDataListList[0].Name,
                rsp.S2C.WarrantDataListList[0].Type);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825714488755091916
    Send QotGetWarrant: 3
    Reply: QotGetWarrant: 3
    retMsg:
    name: 腾讯麦银零乙购A.C, type: 1
    

1  
2  
3  
4  
5  

`int getWarrant(QotGetWarrant.Request req);`  
`void onReply_GetWarrant(MMAPI_Conn client, int nSerialNo, QotGetWarrant.Response rsp);`

*   **介绍**
    
    筛选窝轮（仅用于香港市场）
    
*   **参数**
    

    message C2S
    {
    	required int32 begin = 1; //数据起始点
    	required int32 num =  2; //请求数据个数，最大200
    	required int32 sortField = 3;//Qot_Common.SortField，根据哪个字段排序
    	required bool ascend = 4;//升序 ture，降序 false
    	
    	//以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security owner = 5;	//所属正股
    	repeated int32 typeList = 6; //Qot_Common.WarrantType，窝轮类型过滤列表
    	repeated int32 issuerList = 7; //Qot_Common.Issuer，发行人过滤列表
    	optional string maturityTimeMin = 8; //到期日，到期日范围的开始时间戳
    	optional string maturityTimeMax = 9; //到期日范围的结束时间戳
    	optional int32 ipoPeriod = 10; //Qot_Common.IpoPeriod，上市日
    	optional int32 priceType = 11; //Qot_Common.PriceType，价内/价外（暂不支持界内证的界内外筛选）
    	optional int32 status = 12; //Qot_Common.WarrantStatus，窝轮状态
    	optional double curPriceMin = 13; //最新价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double curPriceMax = 14; //最新价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 	
    	optional double strikePriceMin = 15; //行使价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double strikePriceMax = 16; //行使价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 
    	optional double streetMin = 17; //街货占比的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double streetMax = 18; //街货占比的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMin = 19; //换股比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMax = 20; //换股比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional uint64 volMin = 21; //成交量的过滤下限（闭区间），不传代表下限为 -∞
    	optional uint64 volMax = 22; //成交量的过滤上限（闭区间），不传代表上限为 +∞
    	optional double premiumMin = 23; //溢价的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double premiumMax = 24; //溢价的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMin = 25; //杠杆比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMax = 26; //杠杆比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMin = 27;//对冲值的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 28;//对冲值的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMin = 29; //引伸波幅的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMax = 30; //引伸波幅的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
    	optional double recoveryPriceMin = 31; //收回价的过滤下限（闭区间），仅牛熊证支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double recoveryPriceMax = 32; //收回价的过滤上限（闭区间），仅牛熊证支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMin = 33;//正股距收回价，的过滤下限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMax = 34;//正股距收回价，的过滤上限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序类型参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   上市日类型参见 [IpoPeriod](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9546)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     

*   **返回**

    message WarrantData
    {
    	//静态数据项
        required Qot_Common.Security stock = 1; //股票
        required Qot_Common.Security owner = 2; //所属正股
    	required int32 type = 3; //Qot_Common.WarrantType，窝轮类型
        required int32 issuer = 4; //Qot_Common.Issuer，发行人
    	required string maturityTime = 5; //到期日
    	optional double maturityTimestamp = 6; //到期日时间戳
    	required string listTime = 7; //上市时间（格式：yyyy-MM-dd）
    	optional double listTimestamp = 8; //上市时间戳
    	required string lastTradeTime = 9; //最后交易日
    	optional double lastTradeTimestamp = 10; //最后交易日时间戳
    	optional double recoveryPrice = 11; //收回价，仅牛熊证支持此字段
    	required double conversionRatio = 12; //换股比率
    	required int32 lotSize = 13; //每手数量
    	required double strikePrice = 14; //行使价	
    	required double lastClosePrice = 15; //昨收价        
    	required string name = 16; //名称	
    
    	//动态数据项
    	required double curPrice = 17; //当前价
    	required double priceChangeVal = 18; //涨跌额
        required double changeRate = 19; //涨跌幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int32 status = 20; //Qot_Common.WarrantStatus，窝轮状态	
        required double bidPrice = 21; //买入价	
    	required double askPrice = 22; //卖出价
        required int64 bidVol = 23; //买量
    	required int64 askVol = 24; //卖量
    	required int64 volume = 25; //成交量
        required double turnover = 26; //成交额	
    	required double score = 27; //综合评分
    	required double premium = 28; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double breakEvenPoint = 29; //打和点	
        required double leverage = 30; //杠杆比率（倍）
        required double ipop = 31; //价内/价外，正数表示价内，负数表示价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）        	
    	optional double priceRecoveryRatio = 32; //正股距收回价，仅牛熊证支持此字段（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double conversionPrice = 33; //换股价
    	required double streetRate = 34; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int64 streetVol = 35; //街货量
    	required double amplitude = 36; //振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int64 issueSize = 37; //发行量	        
    	required double highPrice = 39; //最高价
        required double lowPrice = 40; //最低价	
    	optional double impliedVolatility = 41; //引申波幅，仅认购认沽支持此字段
        optional double delta = 42; //对冲值，仅认购认沽支持此字段
    	required double effectiveLeverage = 43; //有效杠杆
    	optional double upperStrikePrice = 44; //上限价，仅界内证支持此字段
    	optional double lowerStrikePrice = 45; //下限价，仅界内证支持此字段
    	optional int32 inLinePriceStatus = 46; //Qot_Common.PriceType，界内界外，仅界内证支持此字段
    }
    
    message S2C
    {
        required bool lastPage = 1; //是否最后一页了，false:非最后一页，还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; //该条件请求所有数据的个数
        repeated WarrantData warrantDataList = 3; //窝轮数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
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
    
            QotGetWarrant.C2S c2s = QotGetWarrant.C2S.newBuilder()
                    .setBegin(0)
                    .setNum(50)
                    .setSortField(QotCommon.SortField.SortField_Code_VALUE)
                    .setAscend(true)
                .build();
            QotGetWarrant.Request req = QotGetWarrant.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getWarrant(req);
            System.out.printf("Send QotGetWarrant: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetWarrant(MMAPI_Conn client, int nSerialNo, QotGetWarrant.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetWarrant failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetWarrant: %s\n", json);
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

*   **Output**

    Send QotGetWarrant: 2
    Receive QotGetWarrant: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "lastPage": false,
        "allCount": 15552,
        "warrantDataList": [{\
          "stock": {\
            "market": 1,\
            "code": "10071"\
          },\
          "owner": {\
            "market": 11,\
            "code": ".DJI"\
          },\
          "type": 2,\
          "issuer": 7,\
          "maturityTime": "2021-06-18",\
          "maturityTimestamp": 1.6239456E9,\
          "listTime": "2020-11-10",\
          "listTimestamp": 1.6049376E9,\
          "lastTradeTime": "2021-06-11",\
          "lastTradeTimestamp": 1.6233408E9,\
          "conversionRatio": 66000.0,\
          "lotSize": 10000,\
          "strikePrice": 25000.0,\
          "lastClosePrice": 0.01,\
          "name": "道指汇丰一六沽B.P",\
          "curPrice": 0.01,\
          "priceChangeVal": 0.0,\
          "changeRate": 0.0,\
          "status": 3,\
          "bidPrice": 0.0,\
          "askPrice": 0.0,\
          "bidVol": "0",\
          "askVol": "0",\
          "volume": "0",\
          "turnover": 0.0,\
          "score": 0.02,\
          "premium": 0.0,\
          "breakEvenPoint": 24340.0,\
          "leverage": 0.0,\
          "ipop": 100.0,\
          "conversionPrice": 660.0,\
          "streetRate": 6.54,\
          "streetVol": "13080000",\
          "amplitude": 0.0,\
          "issueSize": "200000000",\
          "highPrice": 0.0,\
          "lowPrice": 0.0,\
          "impliedVolatility": 0.0,\
          "delta": 0.0,\
          "effectiveLeverage": 0.0\
        }, ... {\
          "stock": {\
            "market": 1,\
            "code": "11047"\
          },\
          "owner": {\
            "market": 1,\
            "code": "01211"\
          },\
          "type": 2,\
          "issuer": 21,\
          "maturityTime": "2021-11-26",\
          "maturityTimestamp": 1.637856E9,\
          "listTime": "2021-05-17",\
          "listTimestamp": 1.6211808E9,\
          "lastTradeTime": "2021-11-22",\
          "lastTradeTimestamp": 1.6375104E9,\
          "conversionRatio": 50.0,\
          "lotSize": 25000,\
          "strikePrice": 121.0,\
          "lastClosePrice": 0.026,\
          "name": "比迪瑞通一甲沽A.P",\
          "curPrice": 0.026,\
          "priceChangeVal": 0.0,\
          "changeRate": 0.0,\
          "status": 1,\
          "bidPrice": 0.026,\
          "askPrice": 0.029,\
          "bidVol": "125000",\
          "askVol": "600000",\
          "volume": "0",\
          "turnover": 0.0,\
          "score": 0.008,\
          "premium": 47.407,\
          "breakEvenPoint": 119.7,\
          "leverage": 175.076,\
          "ipop": -88.099,\
          "conversionPrice": 1.3,\
          "streetRate": 2.25,\
          "streetVol": "900000",\
          "amplitude": 0.0,\
          "issueSize": "40000000",\
          "highPrice": 0.0,\
          "lowPrice": 0.0,\
          "impliedVolatility": 58.629,\
          "delta": -0.032,\
          "effectiveLeverage": -5.602\
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

`moomoo::u32_t GetWarrant(const Qot_GetWarrant::Request &stReq);`  
`virtual void OnReply_GetWarrant(moomoo::u32_t nSerialNo, const Qot_GetWarrant::Response &stRsp) = 0;`

*   **介绍**
    
    筛选窝轮（仅用于香港市场）
    
*   **参数**
    

    message C2S
    {
    	required int32 begin = 1; //数据起始点
    	required int32 num =  2; //请求数据个数，最大200
    	required int32 sortField = 3;//Qot_Common.SortField，根据哪个字段排序
    	required bool ascend = 4;//升序 ture，降序 false
    	
    	//以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security owner = 5;	//所属正股
    	repeated int32 typeList = 6; //Qot_Common.WarrantType，窝轮类型过滤列表
    	repeated int32 issuerList = 7; //Qot_Common.Issuer，发行人过滤列表
    	optional string maturityTimeMin = 8; //到期日，到期日范围的开始时间戳
    	optional string maturityTimeMax = 9; //到期日范围的结束时间戳
    	optional int32 ipoPeriod = 10; //Qot_Common.IpoPeriod，上市日
    	optional int32 priceType = 11; //Qot_Common.PriceType，价内/价外（暂不支持界内证的界内外筛选）
    	optional int32 status = 12; //Qot_Common.WarrantStatus，窝轮状态
    	optional double curPriceMin = 13; //最新价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double curPriceMax = 14; //最新价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 	
    	optional double strikePriceMin = 15; //行使价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double strikePriceMax = 16; //行使价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 
    	optional double streetMin = 17; //街货占比的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double streetMax = 18; //街货占比的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMin = 19; //换股比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMax = 20; //换股比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional uint64 volMin = 21; //成交量的过滤下限（闭区间），不传代表下限为 -∞
    	optional uint64 volMax = 22; //成交量的过滤上限（闭区间），不传代表上限为 +∞
    	optional double premiumMin = 23; //溢价的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double premiumMax = 24; //溢价的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMin = 25; //杠杆比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMax = 26; //杠杆比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMin = 27;//对冲值的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 28;//对冲值的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMin = 29; //引伸波幅的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMax = 30; //引伸波幅的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
    	optional double recoveryPriceMin = 31; //收回价的过滤下限（闭区间），仅牛熊证支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double recoveryPriceMax = 32; //收回价的过滤上限（闭区间），仅牛熊证支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMin = 33;//正股距收回价，的过滤下限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMax = 34;//正股距收回价，的过滤上限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序类型参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   上市日类型参见 [IpoPeriod](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9546)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     

*   **返回**

    message WarrantData
    {
    	//静态数据项
        required Qot_Common.Security stock = 1; //股票
        required Qot_Common.Security owner = 2; //所属正股
    	required int32 type = 3; //Qot_Common.WarrantType，窝轮类型
        required int32 issuer = 4; //Qot_Common.Issuer，发行人
    	required string maturityTime = 5; //到期日
    	optional double maturityTimestamp = 6; //到期日时间戳
    	required string listTime = 7; //上市时间（格式：yyyy-MM-dd）
    	optional double listTimestamp = 8; //上市时间戳
    	required string lastTradeTime = 9; //最后交易日
    	optional double lastTradeTimestamp = 10; //最后交易日时间戳
    	optional double recoveryPrice = 11; //收回价，仅牛熊证支持此字段
    	required double conversionRatio = 12; //换股比率
    	required int32 lotSize = 13; //每手数量
    	required double strikePrice = 14; //行使价	
    	required double lastClosePrice = 15; //昨收价        
    	required string name = 16; //名称	
    
    	//动态数据项
    	required double curPrice = 17; //当前价
    	required double priceChangeVal = 18; //涨跌额
        required double changeRate = 19; //涨跌幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int32 status = 20; //Qot_Common.WarrantStatus，窝轮状态	
        required double bidPrice = 21; //买入价	
    	required double askPrice = 22; //卖出价
        required int64 bidVol = 23; //买量
    	required int64 askVol = 24; //卖量
    	required int64 volume = 25; //成交量
        required double turnover = 26; //成交额	
    	required double score = 27; //综合评分
    	required double premium = 28; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double breakEvenPoint = 29; //打和点	
        required double leverage = 30; //杠杆比率（倍）
        required double ipop = 31; //价内/价外，正数表示价内，负数表示价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）        	
    	optional double priceRecoveryRatio = 32; //正股距收回价，仅牛熊证支持此字段（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double conversionPrice = 33; //换股价
    	required double streetRate = 34; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int64 streetVol = 35; //街货量
    	required double amplitude = 36; //振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int64 issueSize = 37; //发行量	        
    	required double highPrice = 39; //最高价
        required double lowPrice = 40; //最低价	
    	optional double impliedVolatility = 41; //引申波幅，仅认购认沽支持此字段
        optional double delta = 42; //对冲值，仅认购认沽支持此字段
    	required double effectiveLeverage = 43; //有效杠杆
    	optional double upperStrikePrice = 44; //上限价，仅界内证支持此字段
    	optional double lowerStrikePrice = 45; //下限价，仅界内证支持此字段
    	optional int32 inLinePriceStatus = 46; //Qot_Common.PriceType，界内界外，仅界内证支持此字段
    }
    
    message S2C
    {
        required bool lastPage = 1; //是否最后一页了，false:非最后一页，还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; //该条件请求所有数据的个数
        repeated WarrantData warrantDataList = 3; //窝轮数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
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
    		Qot_GetWarrant::Request req;
    		Qot_GetWarrant::C2S *c2s = req.mutable_c2s();
    		c2s->set_begin(0);
    		c2s->set_num(50);
    		c2s->set_sortfield(1);
    		c2s->set_ascend(false);
    
    		m_GetWarrantSerialNo = m_pQotApi->GetWarrant(req);
    		cout << "Request GetWarrant SerialNo: " << m_GetWarrantSerialNo << endl;
    	}
    
    	virtual void OnReply_GetWarrant(moomoo::u32_t nSerialNo, const Qot_GetWarrant::Response &stRsp){
            if(nSerialNo == m_GetWarrantSerialNo)
            {
                cout << "OnReply_GetWarrant SerialNo:" << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetWarrantSerialNo;
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

*   **Output**

    connect
    Request GetWarrant SerialNo: 4
    OnReply_GetWarrant SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "lastPage": false,
      "allCount": 15684,
      "warrantDataList": [\
       {\
        "stock": {\
         "market": 1,\
         "code": "69999"\
        },\
        "owner": {\
         "market": 1,\
         "code": "01211"\
        },\
        "type": 3,\
        "issuer": 1,\
        "maturityTime": "2022-02-28",\
        "maturityTimestamp": 1645977600,\
        "listTime": "2021-06-11",\
        "listTimestamp": 1623340800,\
        "lastTradeTime": "2022-02-25",\
        "lastTradeTimestamp": 1645718400,\
        "recoveryPrice": 193,\
        "conversionRatio": 500,\
        "lotSize": 25000,\
        "strikePrice": 189,\
        "lastClosePrice": 0,\
        "name": "比迪法兴二二牛P",\
        "curPrice": 0,\
        "priceChangeVal": 0,\
        "changeRate": 0,\
        "status": 4,\
        "bidPrice": 0,\
        "askPrice": 0,\
        "bidVol": "0",\
        "askVol": "0",\
        "volume": "0",\
        "turnover": 0,\
        "score": 0,\
        "premium": -5.782,\
        "breakEvenPoint": 189,\
        "leverage": 0,\
        "ipop": 6.137,\
        "priceRecoveryRatio": 3.9378238341968879,\
        "conversionPrice": 0,\
        "streetRate": 0,\
        "streetVol": "0",\
        "amplitude": 0,\
        "issueSize": "150000000",\
        "highPrice": 0,\
        "lowPrice": 0,\
        "effectiveLeverage": 0\
       },\
    ...\
       {\
        "stock": {\
         "market": 1,\
         "code": "69897"\
        },\
        "owner": {\
         "market": 1,\
         "code": "00700"\
        },\
        "type": 4,\
        "issuer": 3,\
        "maturityTime": "2021-12-30",\
        "maturityTimestamp": 1640793600,\
        "listTime": "2021-06-11",\
        "listTimestamp": 1623340800,\
        "lastTradeTime": "2021-12-29",\
        "lastTradeTimestamp": 1640707200,\
        "recoveryPrice": 608,\
        "conversionRatio": 500,\
        "lotSize": 5000,\
        "strikePrice": 610.8,\
        "lastClosePrice": 0,\
        "name": "腾讯瑞信一乙熊G",\
        "curPrice": 0,\
        "priceChangeVal": 0,\
        "changeRate": 0,\
        "status": 4,\
        "bidPrice": 0,\
        "askPrice": 0,\
        "bidVol": "0",\
        "askVol": "0",\
        "volume": "0",\
        "turnover": 0,\
        "score": 0,\
        "premium": -1.125,\
        "breakEvenPoint": 610.8,\
        "leverage": 0,\
        "ipop": 1.113,\
        "priceRecoveryRatio": -0.6578947368421052,\
        "conversionPrice": 0,\
        "streetRate": 0,\
        "streetVol": "0",\
        "amplitude": 0,\
        "issueSize": "100000000",\
        "highPrice": 0,\
        "lowPrice": 0,\
        "effectiveLeverage": 0\
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

`GetWarrant(req);`

*   **介绍**
    
    筛选窝轮（仅用于香港市场）
    
*   **参数**
    

    message C2S
    {
    	required int32 begin = 1; //数据起始点
    	required int32 num =  2; //请求数据个数，最大200
    	required int32 sortField = 3;//Qot_Common.SortField，根据哪个字段排序
    	required bool ascend = 4;//升序 ture，降序 false
    	
    	//以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security owner = 5;	//所属正股
    	repeated int32 typeList = 6; //Qot_Common.WarrantType，窝轮类型过滤列表
    	repeated int32 issuerList = 7; //Qot_Common.Issuer，发行人过滤列表
    	optional string maturityTimeMin = 8; //到期日，到期日范围的开始时间戳
    	optional string maturityTimeMax = 9; //到期日范围的结束时间戳
    	optional int32 ipoPeriod = 10; //Qot_Common.IpoPeriod，上市日
    	optional int32 priceType = 11; //Qot_Common.PriceType，价内/价外（暂不支持界内证的界内外筛选）
    	optional int32 status = 12; //Qot_Common.WarrantStatus，窝轮状态
    	optional double curPriceMin = 13; //最新价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double curPriceMax = 14; //最新价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 	
    	optional double strikePriceMin = 15; //行使价的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double strikePriceMax = 16; //行使价的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃） 
    	optional double streetMin = 17; //街货占比的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double streetMax = 18; //街货占比的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMin = 19; //换股比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double conversionMax = 20; //换股比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional uint64 volMin = 21; //成交量的过滤下限（闭区间），不传代表下限为 -∞
    	optional uint64 volMax = 22; //成交量的过滤上限（闭区间），不传代表上限为 +∞
    	optional double premiumMin = 23; //溢价的过滤下限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double premiumMax = 24; //溢价的过滤上限（闭区间），该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMin = 25; //杠杆比率的过滤下限（闭区间），不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double leverageRatioMax = 26; //杠杆比率的过滤上限（闭区间），不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMin = 27;//对冲值的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double deltaMax = 28;//对冲值的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMin = 29; //引伸波幅的过滤下限（闭区间），仅认购认沽支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double impliedMax = 30; //引伸波幅的过滤上限（闭区间），仅认购认沽支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
    	optional double recoveryPriceMin = 31; //收回价的过滤下限（闭区间），仅牛熊证支持此字段过滤，不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double recoveryPriceMax = 32; //收回价的过滤上限（闭区间），仅牛熊证支持此字段过滤，不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMin = 33;//正股距收回价，的过滤下限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表下限为 -∞（精确到小数点后 3 位，超出部分会被舍弃）
    	optional double priceRecoveryRatioMax = 34;//正股距收回价，的过滤上限（闭区间），仅牛熊证支持此字段过滤。该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。不传代表上限为 +∞（精确到小数点后 3 位，超出部分会被舍弃）	
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   排序类型参见 [SortField](https://openapi.futunn.com/futu-api-doc/quote/quote.html#2930)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   上市日类型参见 [IpoPeriod](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9546)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     

*   **返回**

    message WarrantData
    {
    	//静态数据项
        required Qot_Common.Security stock = 1; //股票
        required Qot_Common.Security owner = 2; //所属正股
    	required int32 type = 3; //Qot_Common.WarrantType，窝轮类型
        required int32 issuer = 4; //Qot_Common.Issuer，发行人
    	required string maturityTime = 5; //到期日
    	optional double maturityTimestamp = 6; //到期日时间戳
    	required string listTime = 7; //上市时间（格式：yyyy-MM-dd）
    	optional double listTimestamp = 8; //上市时间戳
    	required string lastTradeTime = 9; //最后交易日
    	optional double lastTradeTimestamp = 10; //最后交易日时间戳
    	optional double recoveryPrice = 11; //收回价，仅牛熊证支持此字段
    	required double conversionRatio = 12; //换股比率
    	required int32 lotSize = 13; //每手数量
    	required double strikePrice = 14; //行使价	
    	required double lastClosePrice = 15; //昨收价        
    	required string name = 16; //名称	
    
    	//动态数据项
    	required double curPrice = 17; //当前价
    	required double priceChangeVal = 18; //涨跌额
        required double changeRate = 19; //涨跌幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int32 status = 20; //Qot_Common.WarrantStatus，窝轮状态	
        required double bidPrice = 21; //买入价	
    	required double askPrice = 22; //卖出价
        required int64 bidVol = 23; //买量
    	required int64 askVol = 24; //卖量
    	required int64 volume = 25; //成交量
        required double turnover = 26; //成交额	
    	required double score = 27; //综合评分
    	required double premium = 28; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double breakEvenPoint = 29; //打和点	
        required double leverage = 30; //杠杆比率（倍）
        required double ipop = 31; //价内/价外，正数表示价内，负数表示价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）        	
    	optional double priceRecoveryRatio = 32; //正股距收回价，仅牛熊证支持此字段（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double conversionPrice = 33; //换股价
    	required double streetRate = 34; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）	
    	required int64 streetVol = 35; //街货量
    	required double amplitude = 36; //振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int64 issueSize = 37; //发行量	        
    	required double highPrice = 39; //最高价
        required double lowPrice = 40; //最低价	
    	optional double impliedVolatility = 41; //引申波幅，仅认购认沽支持此字段
        optional double delta = 42; //对冲值，仅认购认沽支持此字段
    	required double effectiveLeverage = 43; //有效杠杆
    	optional double upperStrikePrice = 44; //上限价，仅界内证支持此字段
    	optional double lowerStrikePrice = 45; //下限价，仅界内证支持此字段
    	optional int32 inLinePriceStatus = 46; //Qot_Common.PriceType，界内界外，仅界内证支持此字段
    }
    
    message S2C
    {
        required bool lastPage = 1; //是否最后一页了，false:非最后一页，还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; //该条件请求所有数据的个数
        repeated WarrantData warrantDataList = 3; //窝轮数据
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   窝轮类型过滤列表参见 [WarrantType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926)
>     
> *   发行人过滤列表参见 [Issuer](https://openapi.futunn.com/futu-api-doc/quote/quote.html#8363)
>     
> *   价内价外类型参见 [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407)
>     
> *   窝轮状态类型参见 [WarrantStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6556)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetWarrant(){
        const { RetType } = Common
        const { SortField, QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        begin: 0,
                        num: 2,
                        sortField: SortField.SortField_CurPrice,
                        ascend: true,
    
                        owner:{
                            market: QotMarket.QotMarket_HK_Security,
                            code: "00700",
                        },
                    },
                };
    
                websocket.GetWarrant(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("Warrant: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
58  

*   **Output**

    Warrant: errCode 0, retMsg , retType 0
    {
      "lastPage": false,
      "allCount": 1401,
      "warrantDataList": [{\
        "stock": {\
          "market": 1,\
          "code": "50710"\
        },\
        "owner": {\
          "market": 1,\
          "code": "00700"\
        },\
        "type": 3,\
        "issuer": 7,\
        "maturityTime": "2022-04-14",\
        "maturityTimestamp": 1649865600,\
        "listTime": "2021-09-10",\
        "listTimestamp": 1631203200,\
        "lastTradeTime": "2022-04-13",\
        "lastTradeTimestamp": 1649779200,\
        "recoveryPrice": 502.88,\
        "conversionRatio": 500,\
        "lotSize": 50000,\
        "strikePrice": 498.88,\
        "lastClosePrice": 0,\
        "name": "腾讯汇丰二四牛C.C",\
        "curPrice": 0,\
        "priceChangeVal": 0,\
        "changeRate": 0,\
        "status": 3,\
        "bidPrice": 0,\
        "askPrice": 0,\
        "bidVol": "0",\
        "askVol": "0",\
        "volume": "0",\
        "turnover": 0,\
        "score": 0.199,\
        "premium": 2.819,\
        "breakEvenPoint": 498.88,\
        "leverage": 0,\
        "ipop": -2.742,\
        "priceRecoveryRatio": -3.5157492841234506,\
        "conversionPrice": 0,\
        "streetRate": 0,\
        "streetVol": "0",\
        "amplitude": 0,\
        "issueSize": "100000000",\
        "highPrice": 0,\
        "lowPrice": 0,\
        "effectiveLeverage": 0\
      }, {\
        "stock": {\
          "market": 1,\
          "code": "50833"\
        },\
        "owner": {\
          "market": 1,\
          "code": "00700"\
        },\
        "type": 3,\
        "issuer": 11,\
        "maturityTime": "2022-01-28",\
        "maturityTimestamp": 1643299200,\
        "listTime": "2021-09-10",\
        "listTimestamp": 1631203200,\
        "lastTradeTime": "2022-01-27",\
        "lastTradeTimestamp": 1643212800,\
        "recoveryPrice": 518,\
        "conversionRatio": 100,\
        "lotSize": 10000,\
        "strikePrice": 515,\
        "lastClosePrice": 0,\
        "name": "腾讯瑞银二一牛C.C",\
        "curPrice": 0,\
        "priceChangeVal": 0,\
        "changeRate": 0,\
        "status": 3,\
        "bidPrice": 0,\
        "askPrice": 0,\
        "bidVol": "0",\
        "askVol": "0",\
        "volume": "0",\
        "turnover": 0,\
        "score": 0.196,\
        "premium": 6.141,\
        "breakEvenPoint": 515,\
        "leverage": 0,\
        "ipop": -5.786,\
        "priceRecoveryRatio": -6.3320463320463345,\
        "conversionPrice": 0,\
        "streetRate": 0,\
        "streetVol": "0",\
        "amplitude": 0,\
        "issueSize": "40000000",\
        "highPrice": 0,\
        "lowPrice": 0,\
        "effectiveLeverage": 0\
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

接口限制

*   港股 BMP 权限不支持调用此接口
*   每 30 秒内最多请求 60 次筛选窝轮接口
*   每次请求的数据个数上限为 200 个

← [获取期权链](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html) [获取窝轮和期货列表](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html)
 →

[筛选窝轮](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html)