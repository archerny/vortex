[#](./quote_get-stock-filter.md#9310)
 条件选股
===================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_stock_filter(market, filter_list, plate_code=None, begin=0, num=200)`

*   **介绍**
    
    条件选股
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | market | [Market](./quote_quote.md#427) | 市场标识<br>(ℹ️ 不区分沪股和深股，传入沪股或者深股都会返回沪深市场的股票) |
    | filter\_list | list | 筛选条件的列表<br>(ℹ️ 参考下面的表格，列表中元素类型为 SimpleFilter 或 AccumulateFilter 或 FinancialFilter) |
    | plate\_code | str | 板块代码 |
    | begin | int | 数据起始点 |
    | num | int | 请求数据个数 |
    
    *   SimpleFilter 对象相关参数如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | stock\_field | [StockField](./quote_quote.md#860) | 简单属性 |
        | filter\_min | float | 区间下限<br>(ℹ️ 闭区间)  <br>不传默认为 -∞ |
        | filter\_max | float | 区间上限<br>(ℹ️ 闭区间)  <br>不传默认为 +∞ |
        | is\_no\_filter | bool | 该字段是否不需要筛选<br>(ℹ️ True：不筛选)  <br>False：筛选  <br>不传默认不筛选 |
        | sort | [SortDir](./quote_quote.md#5471) | 排序方向<br>(ℹ️ 不传默认为不排序) |
        
    *   AccumulateFilter 对象相关参数如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | stock\_field | [StockField](./quote_quote.md#4370) | 累积属性 |
        | filter\_min | float | 区间下限<br>(ℹ️ 闭区间)  <br>不传默认为 -∞ |
        | filter\_max | float | 区间上限<br>(ℹ️ 闭区间)  <br>不传默认为 +∞ |
        | is\_no\_filter | bool | 该字段是否不需要筛选<br>(ℹ️ True：不筛选)  <br>False：筛选  <br>不传默认不筛选 |
        | sort | [SortDir](./quote_quote.md#5471) | 排序方向<br>(ℹ️ 不传默认为不排序) |
        | days | int | 所筛选的数据的累计天数 |
        
    *   FinancialFilter 对象相关参数如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | stock\_field | [StockField](./quote_quote.md#8542) | 财务属性 |
        | filter\_min | float | 区间下限<br>(ℹ️ 闭区间)  <br>不传默认为 -∞ |
        | filter\_max | float | 区间上限<br>(ℹ️ 闭区间)  <br>不传默认为 +∞ |
        | is\_no\_filter | bool | 该字段是否不需要筛选<br>(ℹ️ True：不筛选)  <br>False：筛选  <br>不传默认不筛选 |
        | sort | [SortDir](./quote_quote.md#5471) | 排序方向<br>(ℹ️ 不传默认为不排序) |
        | quarter | [FinancialQuarter](./quote_quote.md#2253) | 财报累积时间 |
        
    *   CustomIndicatorFilter 对象相关参数如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | stock\_field1 | [StockField](./quote_quote.md#2057) | 自定义技术指标属性 |
        | stock\_field1\_para | list | 自定义技术指标属性参数<br>(ℹ️ 根据指标类型进行传参：)  <br>1\. MA：\[平均移动周期\]  <br>2.EMA：\[指数移动平均周期\]  <br>3.RSI：\[RSI 指标周期\]  <br>4.MACD：\[快速平均线值, 慢速平均线值, DIF值\]  <br>5.BOLL：\[均线周期, 偏移值\]  <br>6.KDJ：\[RSV 周期, K 值计算周期, D 值计算周期\] |
        | relative\_position | [RelativePosition](./quote_quote.md#2453) | 相对位置 |
        | stock\_field2 | [StockField](./quote_quote.md#2057) | 自定义技术指标属性 |
        | stock\_field2\_para | list | 自定义技术指标属性参数<br>(ℹ️ 根据指标类型进行传参：)  <br>1\. MA：\[平均移动周期\]  <br>2.EMA：\[指数移动平均周期\]  <br>3.RSI：\[RSI 指标周期\]  <br>4.MACD：\[快速平均线值, 慢速平均线值, DIF值\]  <br>5.BOLL：\[均线周期, 偏移值\]  <br>6.KDJ：\[RSV 周期, K 值计算周期, D 值计算周期\] |
        | value | float | 自定义数值<br>(ℹ️ 当 stock\_field2 在 [StockField](./quote_quote.md#2057))<br> 中选择自定义数值时，value 为必传参数 |
        | ktype | [KLType](./quote_quote.md#4119) | K线类型 KLType<br>(ℹ️ 仅支持K\_60M，K\_DAY，K\_WEEK，K\_MON 四种时间周期) |
        | consecutive\_period | int | 筛选连续周期（consecutive\_period）都符合条件的数据<br>(ℹ️ 填写范围为\[1,12\]) |
        | is\_no\_filter | bool | 该字段是否不需要筛选<br>(ℹ️ True：不筛选)  <br>False：筛选  <br>不传默认不筛选 |
        
    *   PatternFilter 对象相关参数如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | stock\_field | [StockField](./quote_quote.md#159) | 形态技术指标属性 |
        | ktype | [KLType](./quote_quote.md#4119) | K线类型 KLType （仅支持K\_60M，K\_DAY，K\_WEEK，K\_MON 四种时间周期） |
        | consecutive\_period | int | 筛选连续周期（consecutive\_period）都符合条件的数据<br>(ℹ️ 填写范围为\[1,12\]) |
        | is\_no\_filter | bool | 该字段是否不需要筛选<br>(ℹ️ True：不筛选)  <br>False：筛选  <br>不传默认不筛选 |
        

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | tuple | 当 ret == RET\_OK，返回选股数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   选股数据元组组成如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | last\_page | bool | 是否是最后一页 |
        | all\_count | int | 列表总数量 |
        | stock\_list | list | 选股数据<br>(ℹ️ list 中元素类型是 FilterStockData) |
        
        *   FilterStockData 类型的字段格式：
            
            | 字段  | 类型  | 说明  |
            | --- | --- | --- |
            | stock\_code | str | 股票代码 |
            | stock\_name | str | 股票名字 |
            | cur\_price | float | 最新价 |
            | cur\_price\_to\_highest\_52weeks\_ratio | float | (现价 - 52周最高)/52周最高<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | cur\_price\_to\_lowest\_52weeks\_ratio | float | (现价 - 52周最低)/52周最低<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | high\_price\_to\_highest\_52weeks\_ratio | float | (今日最高 - 52周最高)/52周最高<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | low\_price\_to\_lowest\_52weeks\_ratio | float | (今日最低 - 52周最低)/52周最低<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | volume\_ratio | float | 量比  |
            | bid\_ask\_ratio | float | 委比<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | lot\_price | float | 每手价格 |
            | market\_val | float | 市值  |
            | pe\_annual | float | 市盈率 |
            | pe\_ttm | float | 市盈率 TTM |
            | pb\_rate | float | 市净率 |
            | change\_rate\_5min | float | 五分钟价格涨跌幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | change\_rate\_begin\_year | float | 年初至今价格涨跌幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | ps\_ttm | float | 市销率 TTM<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | pcf\_ttm | float | 市现率 TTM<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | total\_share | float | 总股数<br>(ℹ️ 单位：股) |
            | float\_share | float | 流通股数<br>(ℹ️ 单位：股) |
            | float\_market\_val | float | 流通市值<br>(ℹ️ 单位：元) |
            | change\_rate | float | 涨跌幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | amplitude | float | 振幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | volume | float | 日均成交量 |
            | turnover | float | 日均成交额 |
            | turnover\_rate | float | 换手率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | net\_profit | float | 净利润 |
            | net\_profix\_growth | float | 净利润增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | sum\_of\_business | float | 营业收入 |
            | sum\_of\_business\_growth | float | 营业同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | net\_profit\_rate | float | 净利率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | gross\_profit\_rate | float | 毛利率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | debt\_asset\_rate | float | 资产负债率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | return\_on\_equity\_rate | float | 净资产收益率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | roic | float | 投入资本回报率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | roa\_ttm | float | 资产回报率 TTM<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报) |
            | ebit\_ttm | float | 息税前利润 TTM<br>(ℹ️ 单位：元。仅适用于年报) |
            | ebitda | float | 税息折旧及摊销前利润<br>(ℹ️ 单位：元) |
            | operating\_margin\_ttm | float | 营业利润率 TTM<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报) |
            | ebit\_margin | float | EBIT 利润率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | ebitda\_margin | float | EBITDA 利润率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | financial\_cost\_rate | float | 财务成本率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | operating\_profit\_ttm | float | 营业利润 TTM<br>(ℹ️ 单位：元。仅适用于年报) |
            | shareholder\_net\_profit\_ttm | float | 归属于母公司的净利润<br>(ℹ️ 单位：元。仅适用于年报) |
            | net\_profit\_cash\_cover\_ttm | float | 盈利中的现金收入比例<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报) |
            | current\_ratio | float | 流动比率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | quick\_ratio | float | 速动比率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | current\_asset\_ratio | float | 流动资产率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | current\_debt\_ratio | float | 流动负债率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | equity\_multiplier | float | 权益乘数 |
            | property\_ratio | float | 产权比率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | cash\_and\_cash\_equivalents | float | 现金和现金等价<br>(ℹ️ 单位：元) |
            | total\_asset\_turnover | float | 总资产周转率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | fixed\_asset\_turnover | float | 固定资产周转率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | inventory\_turnover | float | 存货周转率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | operating\_cash\_flow\_ttm | float | 经营活动现金流 TTM<br>(ℹ️ 单位：元。仅适用于年报) |
            | accounts\_receivable | float | 应收账款净额<br>(ℹ️ 单位：元) |
            | ebit\_growth\_rate | float | EBIT 同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | operating\_profit\_growth\_rate | float | 营业利润同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | total\_assets\_growth\_rate | float | 总资产同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | profit\_to\_shareholders\_growth\_rate | float | 归母净利润同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | profit\_before\_tax\_growth\_rate | float | 总利润同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | eps\_growth\_rate | float | EPS 同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | roe\_growth\_rate | float | ROE 同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | roic\_growth\_rate | float | ROIC 同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | nocf\_growth\_rate | float | 经营现金流同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | nocf\_per\_share\_growth\_rate | float | 每股经营现金流同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | operating\_revenue\_cash\_cover | float | 经营现金收入比<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | operating\_profit\_to\_total\_profit | float | 营业利润占比<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | basic\_eps | float | 基本每股收益<br>(ℹ️ 单位：元) |
            | diluted\_eps | float | 稀释每股收益<br>(ℹ️ 单位：元) |
            | nocf\_per\_share | float | 每股经营现金净流量<br>(ℹ️ 单位：元) |
            | price | float | 最新价格 |
            | ma  | float | 简单均线<br>(ℹ️ 根据 MA 参数返回具体的数值) |
            | ma5 | float | 5日简单均线 |
            | ma10 | float | 10日简单均线 |
            | ma20 | float | 20日简单均线 |
            | ma30 | float | 30日简单均线 |
            | ma60 | float | 60日简单均线 |
            | ma120 | float | 120日简单均线 |
            | ma250 | float | 250日简单均线 |
            | rsi | float | RSI的值<br>(ℹ️ 根据 RSI 参数返回具体的数值，RSI 默认参数为12) |
            | ema | float | 指数移动均线<br>(ℹ️ 根据 EMA 参数返回具体的数值) |
            | ema5 | float | 5日指数移动均线 |
            | ema10 | float | 10日指数移动均线 |
            | ema20 | float | 20日指数移动均线 |
            | ema30 | float | 30日指数移动均线 |
            | ema60 | float | 60日指数移动均线 |
            | ema120 | float | 120日指数移动均线 |
            | ema250 | float | 250日指数移动均线 |
            | kdj\_k | float | KDJ 指标的 K 值<br>(ℹ️ 根据 KDJ 参数返回具体的数值，KDJ 默认参数为\[9,3,3\]) |
            | kdj\_d | float | KDJ 指标的 D 值<br>(ℹ️ 根据 KDJ 参数返回具体的数值，KDJ 默认参数为\[9,3,3\]) |
            | kdj\_j | float | KDJ 指标的 J 值<br>(ℹ️ 根据 KDJ 参数返回具体的数值，KDJ 默认参数为\[9,3,3\]) |
            | macd\_diff | float | MACD 指标的 DIFF 值<br>(ℹ️ 根据 MACD 参数返回具体的数值，MACD 默认参数为\[12,26,9\]) |
            | macd\_dea | float | MACD 指标的 DEA 值<br>(ℹ️ 根据 MACD 参数返回具体的数值，MACD 默认参数为\[12,26,9\]) |
            | macd | float | MACD 指标的 MACD 值<br>(ℹ️ 根据 MACD 参数返回具体的数值，MACD 默认参数为\[12,26,9\]) |
            | boll\_upper | float | BOLL 指标的 UPPER 值<br>(ℹ️ 根据 BOLL 参数返回具体的数值，BOLL 默认参数为\[20.2\]) |
            | boll\_middler | float | BOLL 指标的 MIDDLER 值<br>(ℹ️ 根据 BOLL 参数返回具体的数值，BOLL 默认参数为\[20.2\]) |
            | boll\_lower | float | BOLL 指标的 LOWER 值<br>(ℹ️ 根据 BOLL 参数返回具体的数值，BOLL 默认参数为\[20.2\]) |
            
*   **Example**
    

    from futu import *
    import time
    
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    simple_filter = SimpleFilter()
    simple_filter.filter_min = 2
    simple_filter.filter_max = 1000
    simple_filter.stock_field = StockField.CUR_PRICE
    simple_filter.is_no_filter = False
    # simple_filter.sort = SortDir.ASCEND
    
    financial_filter = FinancialFilter()
    financial_filter.filter_min = 0.5
    financial_filter.filter_max = 50
    financial_filter.stock_field = StockField.CURRENT_RATIO
    financial_filter.is_no_filter = False
    financial_filter.sort = SortDir.ASCEND
    financial_filter.quarter = FinancialQuarter.ANNUAL
    
    custom_filter = CustomIndicatorFilter()
    custom_filter.ktype = KLType.K_DAY
    custom_filter.stock_field1 = StockField.KDJ_K
    custom_filter.stock_field1_para = [10,4,4]
    custom_filter.stock_field2 = StockField.KDJ_K
    custom_filter.stock_field2_para = [9,3,3]
    custom_filter.relative_position = RelativePosition.MORE
    custom_filter.is_no_filter = False
    
    nBegin = 0
    last_page = False
    ret_list = list()
    while not last_page:
        nBegin += len(ret_list)
        ret, ls = quote_ctx.get_stock_filter(market=Market.HK, filter_list=[simple_filter, financial_filter, custom_filter], begin=nBegin)  # 对香港市场的股票做简单、财务和指标筛选
        if ret == RET_OK:
            last_page, all_count, ret_list = ls
            print('all count = ', all_count)
            for item in ret_list:
                print(item.stock_code)  # 取股票代码
                print(item.stock_name)  # 取股票名称
                print(item[simple_filter])   # 取 simple_filter 对应的变量值
                print(item[financial_filter])   # 取 financial_filter 对应的变量值
                print(item[custom_filter])  # 获取 custom_filter 的数值
        else:
            print('error: ', ls)
        time.sleep(3)  # 加入时间间隔，避免触发限频
    
    quote_ctx.close()  # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  
4  
5  
6  
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

*   **Output**

    39 39 [ stock_code:HK.08103  stock_name:HMVOD视频  cur_price:2.69  current_ratio(annual):4.413 ,  stock_code:HK.00376  stock_name:云锋金融  cur_price:2.96  current_ratio(annual):12.585 ,  stock_code:HK.09995  stock_name:荣昌生物-B  cur_price:92.65  current_ratio(annual):16.054 ,  stock_code:HK.80737  stock_name:湾区发展-R  cur_price:2.8  current_ratio(annual):17.249 ,  stock_code:HK.00737  stock_name:湾区发展  cur_price:3.25  current_ratio(annual):17.249 ,  stock_code:HK.03939  stock_name:万国国际矿业  cur_price:2.22  current_ratio(annual):17.323 ,  stock_code:HK.01055  stock_name:中国南方航空股份  cur_price:5.17  current_ratio(annual):17.529 ,  stock_code:HK.02638  stock_name:港灯-SS  cur_price:7.68  current_ratio(annual):21.255 ,  stock_code:HK.00670  stock_name:中国东方航空股份  cur_price:3.53  current_ratio(annual):25.194 ,  stock_code:HK.01952  stock_name:云顶新耀-B  cur_price:69.5  current_ratio(annual):26.029 ,  stock_code:HK.00089  stock_name:大生地产  cur_price:4.22  current_ratio(annual):26.914 ,  stock_code:HK.00728  stock_name:中国电信  cur_price:2.81  current_ratio(annual):27.651 ,  stock_code:HK.01372  stock_name:比速科技  cur_price:5.1  current_ratio(annual):28.303 ,  stock_code:HK.00753  stock_name:中国国航  cur_price:6.38  current_ratio(annual):31.828 ,  stock_code:HK.01997  stock_name:九龙仓置业  cur_price:43.75  current_ratio(annual):33.239 ,  stock_code:HK.02158  stock_name:医渡科技  cur_price:39.0  current_ratio(annual):34.046 ,  stock_code:HK.02588  stock_name:中银航空租赁  cur_price:77.0  current_ratio(annual):34.531 ,  stock_code:HK.01330  stock_name:绿色动力环保  cur_price:3.36  current_ratio(annual):35.028 ,  stock_code:HK.01525  stock_name:建桥教育  cur_price:6.28  current_ratio(annual):36.989 ,  stock_code:HK.09908  stock_name:嘉兴燃气  cur_price:10.02  current_ratio(annual):37.848 ,  stock_code:HK.06078  stock_name:海吉亚医疗  cur_price:49.8  current_ratio(annual):39.0 ,  stock_code:HK.01071  stock_name:华电国际电力股份  cur_price:2.16  current_ratio(annual):39.507 ,  stock_code:HK.00357  stock_name:美兰空港  cur_price:34.15  current_ratio(annual):39.514 ,  stock_code:HK.00762  stock_name:中国联通  cur_price:5.15  current_ratio(annual):40.74 ,  stock_code:HK.01787  stock_name:山东黄金  cur_price:15.56  current_ratio(annual):41.604 ,  stock_code:HK.00902  stock_name:华能国际电力股份  cur_price:2.66  current_ratio(annual):42.919 ,  stock_code:HK.00934  stock_name:中石化冠德  cur_price:2.96  current_ratio(annual):43.361 ,  stock_code:HK.01117  stock_name:现代牧业  cur_price:2.3  current_ratio(annual):45.037 ,  stock_code:HK.00177  stock_name:江苏宁沪高速公路  cur_price:8.78  current_ratio(annual):45.93 ,  stock_code:HK.01379  stock_name:温岭工量刃具  cur_price:5.71  current_ratio(annual):46.774 ,  stock_code:HK.01876  stock_name:百威亚太  cur_price:22.5  current_ratio(annual):46.917 ,  stock_code:HK.01907  stock_name:中国旭阳集团  cur_price:4.38  current_ratio(annual):47.129 ,  stock_code:HK.02160  stock_name:心通医疗-B  cur_price:15.54  current_ratio(annual):47.384 ,  stock_code:HK.00293  stock_name:国泰航空  cur_price:7.1  current_ratio(annual):47.983 ,  stock_code:HK.00694  stock_name:北京首都机场股份  cur_price:6.34  current_ratio(annual):47.985 ,  stock_code:HK.09922  stock_name:九毛九  cur_price:26.65  current_ratio(annual):48.278 ,  stock_code:HK.01083  stock_name:港华燃气  cur_price:3.39  current_ratio(annual):49.2 ,  stock_code:HK.00291  stock_name:华润啤酒  cur_price:58.0  current_ratio(annual):49.229 ,  stock_code:HK.00306  stock_name:冠忠巴士集团  cur_price:2.29  current_ratio(annual):49.769 ]
    HK.08103
    HMVOD视频
    2.69
    2.69
    4.413
    ...
    HK.00306
    冠忠巴士集团
    2.29
    2.29
    49.769
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

[#](./quote_get-stock-filter.md#2109)
 Qot\_StockFilter.proto
-----------------------------------------------------------------------------------------------------

*   **介绍**
    
    条件选股
    
*   **参数**
    

    // 使用以下6种结构(BaseFilter、AcumulateFilter、FinancialFilter、BaseData、AcumulateData、FinancialData)的用户请注意，由于属性字段名“field”与C#protocol buf保留的函数名冲突，Futu API将从3.18版本开始将该字段重命名为“fieldName”。请注意修改相应界面中使用的字段名称。
    
    // 简单属性筛选
    message BaseFilter 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    }
    
    // 累积属性筛选
    message AccumulateFilter
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 days = 6; // 近几日，累积时间
    }
    
    // 财务属性筛选
    message FinancialFilter
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 quarter = 6; // FinancialQuarter 财报累积时间
    }
    
    // 形态技术指标属性筛选
    message PatternFilter
    {
    	required int32 fieldName = 1; // PatternField 形态技术指标属性
    	required int32 klType = 2; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
    	optional bool isNoFilter = 3; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        optional int32 consecutivePeriod = 4; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    // 自定义技术指标属性筛选
    message CustomIndicatorFilter
    {
    	required int32 firstFieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required int32 secondFieldName = 2; // CustomIndicatorField 自定义技术指标属性
    	required int32 relativePosition = 3; // RelativePosition 相对位置
    	optional double fieldValue = 4; // 自定义数值
    	required int32 klType = 5; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期	
    	optional bool isNoFilter = 6; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        repeated int32 firstFieldParaList = 7; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	repeated int32 secondFieldParaList = 8; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	optional int32 consecutivePeriod = 9; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    message C2S
    {
        required int32 begin = 1; // 数据起始点
        required int32 num =  2;  // 请求数据个数，最大200        
    	required int32 market= 3; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	// 以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security plate = 4; // 板块
    	repeated BaseFilter baseFilterList = 5; // 简单指标过滤器
    	repeated AccumulateFilter accumulateFilterList = 6; // 累积指标过滤器 累积属性的同一筛选条件数量上限 10 个
    	repeated FinancialFilter financialFilterList = 7; // 财务指标过滤器
    	repeated PatternFilter patternFilterList = 8; // 形态技术指标过滤器
    	repeated CustomIndicatorFilter customIndicatorFilterList = 9; // 自定义技术指标过滤器
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   形态技术指标属性筛选条件参见 [PatternField](./quote_quote.md#159)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   排序方向参见 [SortDir](./quote_quote.md#5471)
>     
> *   相对位置参见 [RelativePosition](./quote_quote.md#2453)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     

*   **返回**

    // 简单属性数据
    message BaseData 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
        required double value = 2;
    }
    
    // 累积属性数据
    message AccumulateData
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
        required double value = 2;
    	required int32 days = 3; // 近几日，累积时间
    }
    
    // 财务属性数据
    message FinancialData
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
        required double value = 2;
    	required int32 quarter = 3; // FinancialQuarter 财报累积时间
    }
    
    // 自定义技术指标属性数据
    message CustomIndicatorData
    {
    	required int32 fieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required double value = 2; 
    	required int32 klType = 3; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
        repeated int32 fieldParaList = 4; // 自定义指标参数 根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    }
    
    // 返回的股票数据
    message StockData  
    {
        required Qot_Common.Security security = 1; // 股票
        required string name = 2; // 股票名称
        repeated BaseData baseDataList = 3; // 筛选后的简单指标属性数据
        repeated AccumulateData accumulateDataList = 4; // 筛选后的累积指标属性数据
        repeated FinancialData financialDataList = 5; // 筛选后的财务指标属性数据
        repeated CustomIndicatorData customIndicatorDataList = 6; // 自定义技术指标属性数据
        // CustomIndicatorFilter 中的 firstFieldName 和 secondFieldName 的数值会在这个列表中分开返回
    }
    
    message S2C
    {
        required bool lastPage = 1; // 是否最后一页了,false:非最后一页,还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; // 该条件请求所有数据的个数
        repeated StockData dataList = 3; // 返回的股票数据列表
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; // RetType,返回结果
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3215
    

`uint StockFilter(QotStockFilter.Request req);`  
`virtual void OnReply_StockFilter(FTAPI_Conn client, uint nSerialNo, QotStockFilter.Response rsp);`

*   **介绍**
    
    条件选股
    
*   **参数**
    

    // 使用以下6种结构(BaseFilter、AcumulateFilter、FinancialFilter、BaseData、AcumulateData、FinancialData)的用户请注意，由于属性字段名“field”与C#protocol buf保留的函数名冲突，Futu API将从3.18版本开始将该字段重命名为“fieldName”。请注意修改相应界面中使用的字段名称。
    
    // 简单属性筛选
    message BaseFilter 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    }
    
    // 累积属性筛选
    message AccumulateFilter
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 days = 6; // 近几日，累积时间
    }
    
    // 财务属性筛选
    message FinancialFilter
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 quarter = 6; // FinancialQuarter 财报累积时间
    }
    
    // 形态技术指标属性筛选
    message PatternFilter
    {
    	required int32 fieldName = 1; // PatternField 形态技术指标属性
    	required int32 klType = 2; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
    	optional bool isNoFilter = 3; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        optional int32 consecutivePeriod = 4; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    // 自定义技术指标属性筛选
    message CustomIndicatorFilter
    {
    	required int32 firstFieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required int32 secondFieldName = 2; // CustomIndicatorField 自定义技术指标属性
    	required int32 relativePosition = 3; // RelativePosition 相对位置
    	optional double fieldValue = 4; // 自定义数值
    	required int32 klType = 5; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期	
    	optional bool isNoFilter = 6; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        repeated int32 firstFieldParaList = 7; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	repeated int32 secondFieldParaList = 8; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	optional int32 consecutivePeriod = 9; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    message C2S
    {
        required int32 begin = 1; // 数据起始点
        required int32 num =  2;  // 请求数据个数，最大200        
    	required int32 market= 3; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	// 以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security plate = 4; // 板块
    	repeated BaseFilter baseFilterList = 5; // 简单指标过滤器
    	repeated AccumulateFilter accumulateFilterList = 6; // 累积指标过滤器 累积属性的同一筛选条件数量上限 10 个
    	repeated FinancialFilter financialFilterList = 7; // 财务指标过滤器
    	repeated PatternFilter patternFilterList = 8; // 形态技术指标过滤器
    	repeated CustomIndicatorFilter customIndicatorFilterList = 9; // 自定义技术指标过滤器
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   形态技术指标属性筛选条件参见 [PatternField](./quote_quote.md#159)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   排序方向参见 [SortDir](./quote_quote.md#5471)
>     
> *   相对位置参见 [RelativePosition](./quote_quote.md#2453)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     

*   **返回**

    // 简单属性数据
    message BaseData 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
        required double value = 2;
    }
    
    // 累积属性数据
    message AccumulateData
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
        required double value = 2;
    	required int32 days = 3; // 近几日，累积时间
    }
    
    // 财务属性数据
    message FinancialData
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
        required double value = 2;
    	required int32 quarter = 3; // FinancialQuarter 财报累积时间
    }
    
    // 自定义技术指标属性数据
    message CustomIndicatorData
    {
    	required int32 fieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required double value = 2; 
    	required int32 klType = 3; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
        repeated int32 fieldParaList = 4; // 自定义指标参数 根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    }
    
    // 返回的股票数据
    message StockData  
    {
        required Qot_Common.Security security = 1; // 股票
    	required string name = 2; // 股票名称
        repeated BaseData baseDataList = 3; // 筛选后的简单指标属性数据
    	repeated AccumulateData accumulateDataList = 4; // 筛选后的累积指标属性数据
    	repeated FinancialData financialDataList = 5; // 筛选后的财务指标属性数据
        repeated CustomIndicatorData customIndicatorDataList = 6; // 自定义技术指标属性数据
        // CustomIndicatorFilter 中的 firstFieldName 和 secondFieldName 的数值会在这个列表中分开返回
    }
    
    message S2C
    {
        required bool lastPage = 1; // 是否最后一页了,false:非最后一页,还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; // 该条件请求所有数据的个数
        repeated StockData dataList = 3; // 返回的股票数据列表
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; // RetType,返回结果
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            QotStockFilter.BaseFilter filter = QotStockFilter.BaseFilter.CreateBuilder()
                    .SetFieldName(QotStockFilter.StockField.StockField_CurPrice)
                    .SetFilterMax(100)
                    .SetFilterMax(200)
                    .Build();
            QotStockFilter.C2S c2s = QotStockFilter.C2S.CreateBuilder()
                    .SetBegin(0)
                    .SetNum(10)
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .AddBaseFilterList(filter)
                .Build();
            QotStockFilter.Request req = QotStockFilter.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.StockFilter(req);
            Console.Write("Send QotStockFilter: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_StockFilter(FTAPI_Conn client, uint nSerialNo, QotStockFilter.Response rsp)
        {
            Console.Write("Reply: QotStockFilter: {0}\n", nSerialNo, rsp.ToString());
            Console.Write("code: {0}, name: {1} \n", rsp.S2C.DataListList[0].Security.Code,
                rsp.S2C.DataListList[0].Name);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825721743616225277
    Send QotStockFilter: 3
    Reply: QotStockFilter: 3
    code: 00376, name: 云锋金融
    

1  
2  
3  
4  

`int stockFilter(QotStockFilter.Request req);`  
`void onReply_StockFilter(FTAPI_Conn client, int nSerialNo, QotStockFilter.Response rsp);`

*   **介绍**
    
    条件选股
    
*   **参数**
    

    // 使用以下6种结构(BaseFilter、AcumulateFilter、FinancialFilter、BaseData、AcumulateData、FinancialData)的用户请注意，由于属性字段名“field”与C#protocol buf保留的函数名冲突，Futu API将从3.18版本开始将该字段重命名为“fieldName”。请注意修改相应界面中使用的字段名称。
    
    // 简单属性筛选
    message BaseFilter 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    }
    
    // 累积属性筛选
    message AccumulateFilter
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 days = 6; // 近几日，累积时间
    }
    
    // 财务属性筛选
    message FinancialFilter
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 quarter = 6; // FinancialQuarter 财报累积时间
    }
    
    // 形态技术指标属性筛选
    message PatternFilter
    {
    	required int32 fieldName = 1; // PatternField 形态技术指标属性
    	required int32 klType = 2; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
    	optional bool isNoFilter = 3; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        optional int32 consecutivePeriod = 4; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    // 自定义技术指标属性筛选
    message CustomIndicatorFilter
    {
    	required int32 firstFieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required int32 secondFieldName = 2; // CustomIndicatorField 自定义技术指标属性
    	required int32 relativePosition = 3; // RelativePosition 相对位置
    	optional double fieldValue = 4; // 自定义数值
    	required int32 klType = 5; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期	
    	optional bool isNoFilter = 6; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        repeated int32 firstFieldParaList = 7; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	repeated int32 secondFieldParaList = 8; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	optional int32 consecutivePeriod = 9; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    message C2S
    {
        required int32 begin = 1; // 数据起始点
        required int32 num =  2;  // 请求数据个数，最大200        
    	required int32 market= 3; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	// 以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security plate = 4; // 板块
    	repeated BaseFilter baseFilterList = 5; // 简单指标过滤器
    	repeated AccumulateFilter accumulateFilterList = 6; // 累积指标过滤器 累积属性的同一筛选条件数量上限 10 个
    	repeated FinancialFilter financialFilterList = 7; // 财务指标过滤器
    	repeated PatternFilter patternFilterList = 8; // 形态技术指标过滤器
    	repeated CustomIndicatorFilter customIndicatorFilterList = 9; // 自定义技术指标过滤器
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   形态技术指标属性筛选条件参见 [PatternField](./quote_quote.md#159)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   排序方向参见 [SortDir](./quote_quote.md#5471)
>     
> *   相对位置参见 [RelativePosition](./quote_quote.md#2453)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     

*   **返回**

    // 简单属性数据
    message BaseData 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
        required double value = 2;
    }
    
    // 累积属性数据
    message AccumulateData
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
        required double value = 2;
    	required int32 days = 3; // 近几日，累积时间
    }
    
    // 财务属性数据
    message FinancialData
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
        required double value = 2;
    	required int32 quarter = 3; // FinancialQuarter 财报累积时间
    }
    
    // 自定义技术指标属性数据
    message CustomIndicatorData
    {
    	required int32 fieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required double value = 2; 
    	required int32 klType = 3; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
        repeated int32 fieldParaList = 4; // 自定义指标参数 根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    }
    
    // 返回的股票数据
    message StockData  
    {
        required Qot_Common.Security security = 1; // 股票
    	required string name = 2; // 股票名称
        repeated BaseData baseDataList = 3; // 筛选后的简单指标属性数据
    	repeated AccumulateData accumulateDataList = 4; // 筛选后的累积指标属性数据
    	repeated FinancialData financialDataList = 5; // 筛选后的财务指标属性数据
        repeated CustomIndicatorData customIndicatorDataList = 6; // 自定义技术指标属性数据
        // CustomIndicatorFilter 中的 firstFieldName 和 secondFieldName 的数值会在这个列表中分开返回
    }
    
    message S2C
    {
        required bool lastPage = 1; // 是否最后一页了,false:非最后一页,还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; // 该条件请求所有数据的个数
        repeated StockData dataList = 3; // 返回的股票数据列表
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; // RetType,返回结果
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            QotStockFilter.BaseFilter filter = QotStockFilter.BaseFilter.newBuilder()
                    .setFieldName(QotStockFilter.StockField.StockField_CurPrice_VALUE)
                    .setFilterMax(100)
                    .setFilterMax(200)
                    .build();
            QotStockFilter.C2S c2s = QotStockFilter.C2S.newBuilder()
                    .setBegin(0)
                    .setNum(10)
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .addBaseFilterList(filter)
                .build();
            QotStockFilter.Request req = QotStockFilter.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.stockFilter(req);
            System.out.printf("Send QotStockFilter: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_StockFilter(FTAPI_Conn client, int nSerialNo, QotStockFilter.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotStockFilter failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotStockFilter: %s\n", json);
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

*   **Output**

    Send QotStockFilter: 2
    Receive QotStockFilter: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "lastPage": false,
        "allCount": 2431,
        "dataList": [{\
          "security": {\
            "market": 1,\
            "code": "08560"\
          },\
          "name": "世大控股"\
        }, ... {\
          "security": {\
            "market": 1,\
            "code": "02171"\
          },\
          "name": "科济药业-B"\
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

`Futu::u32_t StockFilter(const Qot_StockFilter::Request &stReq);`  
`virtual void OnReply_StockFilter(Futu::u32_t nSerialNo, const Qot_StockFilter::Response &stRsp) = 0;`

*   **介绍**
    
    条件选股
    
*   **参数**
    

    // 使用以下6种结构(BaseFilter、AcumulateFilter、FinancialFilter、BaseData、AcumulateData、FinancialData)的用户请注意，由于属性字段名“field”与C#protocol buf保留的函数名冲突，Futu API将从3.18版本开始将该字段重命名为“fieldName”。请注意修改相应界面中使用的字段名称。
    
    // 简单属性筛选
    message BaseFilter 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    }
    
    // 累积属性筛选
    message AccumulateFilter
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 days = 6; // 近几日，累积时间
    }
    
    // 财务属性筛选
    message FinancialFilter
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 quarter = 6; // FinancialQuarter 财报累积时间
    }
    
    // 形态技术指标属性筛选
    message PatternFilter
    {
    	required int32 fieldName = 1; // PatternField 形态技术指标属性
    	required int32 klType = 2; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
    	optional bool isNoFilter = 3; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        optional int32 consecutivePeriod = 4; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    // 自定义技术指标属性筛选
    message CustomIndicatorFilter
    {
    	required int32 firstFieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required int32 secondFieldName = 2; // CustomIndicatorField 自定义技术指标属性
    	required int32 relativePosition = 3; // RelativePosition 相对位置
    	optional double fieldValue = 4; // 自定义数值
    	required int32 klType = 5; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期	
    	optional bool isNoFilter = 6; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        repeated int32 firstFieldParaList = 7; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	repeated int32 secondFieldParaList = 8; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	optional int32 consecutivePeriod = 9; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    message C2S
    {
        required int32 begin = 1; // 数据起始点
        required int32 num =  2;  // 请求数据个数，最大200        
    	required int32 market= 3; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	// 以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security plate = 4; // 板块
    	repeated BaseFilter baseFilterList = 5; // 简单指标过滤器
    	repeated AccumulateFilter accumulateFilterList = 6; // 累积指标过滤器 累积属性的同一筛选条件数量上限 10 个
    	repeated FinancialFilter financialFilterList = 7; // 财务指标过滤器
    	repeated PatternFilter patternFilterList = 8; // 形态技术指标过滤器
    	repeated CustomIndicatorFilter customIndicatorFilterList = 9; // 自定义技术指标过滤器
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   形态技术指标属性筛选条件参见 [PatternField](./quote_quote.md#159)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   排序方向参见 [SortDir](./quote_quote.md#5471)
>     
> *   相对位置参见 [RelativePosition](./quote_quote.md#2453)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     

*   **返回**

    // 简单属性数据
    message BaseData 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
        required double value = 2;
    }
    
    // 累积属性数据
    message AccumulateData
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
        required double value = 2;
    	required int32 days = 3; // 近几日，累积时间
    }
    
    // 财务属性数据
    message FinancialData
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
        required double value = 2;
    	required int32 quarter = 3; // FinancialQuarter 财报累积时间
    }
    
    // 自定义技术指标属性数据
    message CustomIndicatorData
    {
    	required int32 fieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required double value = 2; 
    	required int32 klType = 3; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
        repeated int32 fieldParaList = 4; // 自定义指标参数 根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    }
    
    // 返回的股票数据
    message StockData  
    {
        required Qot_Common.Security security = 1; // 股票
    	required string name = 2; // 股票名称
        repeated BaseData baseDataList = 3; // 筛选后的简单指标属性数据
    	repeated AccumulateData accumulateDataList = 4; // 筛选后的累积指标属性数据
    	repeated FinancialData financialDataList = 5; // 筛选后的财务指标属性数据
        repeated CustomIndicatorData customIndicatorDataList = 6; // 自定义技术指标属性数据
        // CustomIndicatorFilter 中的 firstFieldName 和 secondFieldName 的数值会在这个列表中分开返回
    }
    
    message S2C
    {
        required bool lastPage = 1; // 是否最后一页了,false:非最后一页,还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; // 该条件请求所有数据的个数
        repeated StockData dataList = 3; // 返回的股票数据列表
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; // RetType,返回结果
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    		Qot_StockFilter::Request req;
    		Qot_StockFilter::C2S *c2s = req.mutable_c2s();
    		c2s->set_begin(0);
    		c2s->set_num(50);
    		c2s->set_market(1);
            
            m_StockFilterSerialNo = m_pQotApi->StockFilter(req);
            cout << "Request StockFilter SerialNo: " << m_StockFilterSerialNo << endl;
    	}
    
    	virtual void OnReply_StockFilter(Futu::u32_t nSerialNo, const Qot_StockFilter::Response &stRsp){
            if(nSerialNo == m_StockFilterSerialNo)
            {
                cout << "OnReply_StockFilter SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_StockFilterSerialNo;
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

*   **Output**

    connect
    Request StockFilter SerialNo: 4
    OnReply_StockFilter SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "lastPage": false,
      "allCount": 2426,
      "dataList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "02930"\
        },\
        "name": "丝路物流控股"\
       },\
    ...\
       {\
        "security": {\
         "market": 1,\
         "code": "01440"\
        },\
        "name": "DEYUN HOLDING"\
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

`StockFilter(req);`

*   **介绍**
    
    条件选股
    
*   **参数**
    

    // 使用以下6种结构(BaseFilter、AcumulateFilter、FinancialFilter、BaseData、AcumulateData、FinancialData)的用户请注意，由于属性字段名“field”与C#protocol buf保留的函数名冲突，Futu API将从3.18版本开始将该字段重命名为“fieldName”。请注意修改相应界面中使用的字段名称。
    
    // 简单属性筛选
    message BaseFilter 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    }
    
    // 累积属性筛选
    message AccumulateFilter
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 days = 6; // 近几日，累积时间
    }
    
    // 财务属性筛选
    message FinancialFilter
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 quarter = 6; // FinancialQuarter 财报累积时间
    }
    
    // 形态技术指标属性筛选
    message PatternFilter
    {
    	required int32 fieldName = 1; // PatternField 形态技术指标属性
    	required int32 klType = 2; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
    	optional bool isNoFilter = 3; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        optional int32 consecutivePeriod = 4; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    // 自定义技术指标属性筛选
    message CustomIndicatorFilter
    {
    	required int32 firstFieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required int32 secondFieldName = 2; // CustomIndicatorField 自定义技术指标属性
    	required int32 relativePosition = 3; // RelativePosition 相对位置
    	optional double fieldValue = 4; // 自定义数值
    	required int32 klType = 5; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期	
    	optional bool isNoFilter = 6; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        repeated int32 firstFieldParaList = 7; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	repeated int32 secondFieldParaList = 8; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	optional int32 consecutivePeriod = 9; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    message C2S
    {
        required int32 begin = 1; // 数据起始点
        required int32 num =  2;  // 请求数据个数，最大200        
    	required int32 market= 3; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	// 以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security plate = 4; // 板块
    	repeated BaseFilter baseFilterList = 5; // 简单指标过滤器
    	repeated AccumulateFilter accumulateFilterList = 6; // 累积指标过滤器 累积属性的同一筛选条件数量上限 10 个
    	repeated FinancialFilter financialFilterList = 7; // 财务指标过滤器
    	repeated PatternFilter patternFilterList = 8; // 形态技术指标过滤器
    	repeated CustomIndicatorFilter customIndicatorFilterList = 9; // 自定义技术指标过滤器
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   形态技术指标属性筛选条件参见 [PatternField](./quote_quote.md#159)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   排序方向参见 [SortDir](./quote_quote.md#5471)
>     
> *   相对位置参见 [RelativePosition](./quote_quote.md#2453)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     

*   **返回**

    // 简单属性数据
    message BaseData 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
        required double value = 2;
    }
    
    // 累积属性数据
    message AccumulateData
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
        required double value = 2;
    	required int32 days = 3; // 近几日，累积时间
    }
    
    // 财务属性数据
    message FinancialData
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
        required double value = 2;
    	required int32 quarter = 3; // FinancialQuarter 财报累积时间
    }
    
    // 自定义技术指标属性数据
    message CustomIndicatorData
    {
    	required int32 fieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required double value = 2; 
    	required int32 klType = 3; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
        repeated int32 fieldParaList = 4; // 自定义指标参数 根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    }
    
    // 返回的股票数据
    message StockData  
    {
        required Qot_Common.Security security = 1; // 股票
    	required string name = 2; // 股票名称
        repeated BaseData baseDataList = 3; // 筛选后的简单指标属性数据
    	repeated AccumulateData accumulateDataList = 4; // 筛选后的累积指标属性数据
    	repeated FinancialData financialDataList = 5; // 筛选后的财务指标属性数据
        repeated CustomIndicatorData customIndicatorDataList = 6; // 自定义技术指标属性数据
        // CustomIndicatorFilter 中的 firstFieldName 和 secondFieldName 的数值会在这个列表中分开返回
    }
    
    message S2C
    {
        required bool lastPage = 1; // 是否最后一页了,false:非最后一页,还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; // 该条件请求所有数据的个数
        repeated StockData dataList = 3; // 返回的股票数据列表
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; // RetType,返回结果
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotStockFilter(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        begin: 0, 
                        num: 2,
                        market: QotMarket.QotMarket_HK_Security,
                    },
                };
    
                websocket.StockFilter(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("StockFilter: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    StockFilter: errCode 0, retMsg , retType 0
    {
      "lastPage": false,
      "allCount": 2436,
      "dataList": [{\
        "security": {\
          "market": 1,\
          "code": "08579"\
        },\
        "name": "万亚企业控股"\
      }, {\
        "security": {\
          "market": 1,\
          "code": "09869"\
        },\
        "name": "海伦司"\
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

提示

*   利用[获取子板块列表函数](./quote_get-plate-list.md)
     获取子板块代码，条件选股支持的板块分别为
    1.  港股的行业板块和概念板块。
    2.  美股的行业板块
    3.  沪深的行业板块，概念板块和地域板块
*   支持的板块指数代码
    
    | 代码  | 说明  |
    | --- | --- |
    | HK.Motherboard | 港股主板 |
    | HK.GEM | 港股创业板 |
    | HK.BK1911 | H 股主板 |
    | HK.BK1912 | H 股创业板 |
    | US.NYSE | 纽交所 |
    | US.AMEX | 美交所 |
    | US.NASDAQ | 纳斯达克 |
    | SH.3000000 | 上海主板 |
    | SZ.3000001 | 深证主板 |
    | SZ.3000004 | 深证创业板 |
    

接口限制

*   港股 BMP 权限不支持条件选股功能
*   每 30 秒内最多请求 10 次条件选股接口
*   每页返回的筛选结果最多 200 个
*   建议筛选条件不超过 250 个，否则可能会出现“业务处理超时没返回”
*   累积属性的同一筛选条件数量上限 10 个
*   如果使用“最新价”等动态数据作为排序字段，在多页获取的间隙，数据的排序有可能发生变化
*   非同类指标不支持比较，仅限于同类指标之间建立比较关系，跨不同类型的指标比较会报错。例如：MA5 和 MA10 可以建立关系。MA5和EMA10不能建立关系。
*   自定义指标属性的同一类筛选条件超出数量上限10个
*   简单属性，财务属性，形态属性不支持对同一字段重复指定筛选条件
*   条件选股暂不支持美股盘前盘后、夜盘，筛选结果均按照盘中数据返回

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_stock_filter(market, filter_list, plate_code=None, begin=0, num=200)`

*   **介绍**
    
    条件选股
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | market | [Market](./quote_quote.md#427) | 市场标识<br>(ℹ️ 不区分沪股和深股，传入沪股或者深股都会返回沪深市场的股票) |
    | filter\_list | list | 筛选条件的列表<br>(ℹ️ 参考下面的表格，列表中元素类型为 SimpleFilter 或 AccumulateFilter 或 FinancialFilter) |
    | plate\_code | str | 板块代码 |
    | begin | int | 数据起始点 |
    | num | int | 请求数据个数 |
    
    *   SimpleFilter 对象相关参数如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | stock\_field | [StockField](./quote_quote.md#860) | 简单属性 |
        | filter\_min | float | 区间下限<br>(ℹ️ 闭区间)  <br>不传默认为 -∞ |
        | filter\_max | float | 区间上限<br>(ℹ️ 闭区间)  <br>不传默认为 +∞ |
        | is\_no\_filter | bool | 该字段是否不需要筛选<br>(ℹ️ True：不筛选)  <br>False：筛选  <br>不传默认不筛选 |
        | sort | [SortDir](./quote_quote.md#5471) | 排序方向<br>(ℹ️ 不传默认为不排序) |
        
    *   AccumulateFilter 对象相关参数如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | stock\_field | [StockField](./quote_quote.md#4370) | 累积属性 |
        | filter\_min | float | 区间下限<br>(ℹ️ 闭区间)  <br>不传默认为 -∞ |
        | filter\_max | float | 区间上限<br>(ℹ️ 闭区间)  <br>不传默认为 +∞ |
        | is\_no\_filter | bool | 该字段是否不需要筛选<br>(ℹ️ True：不筛选)  <br>False：筛选  <br>不传默认不筛选 |
        | sort | [SortDir](./quote_quote.md#5471) | 排序方向<br>(ℹ️ 不传默认为不排序) |
        | days | int | 所筛选的数据的累计天数 |
        
    *   FinancialFilter 对象相关参数如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | stock\_field | [StockField](./quote_quote.md#8542) | 财务属性 |
        | filter\_min | float | 区间下限<br>(ℹ️ 闭区间)  <br>不传默认为 -∞ |
        | filter\_max | float | 区间上限<br>(ℹ️ 闭区间)  <br>不传默认为 +∞ |
        | is\_no\_filter | bool | 该字段是否不需要筛选<br>(ℹ️ True：不筛选)  <br>False：筛选  <br>不传默认不筛选 |
        | sort | [SortDir](./quote_quote.md#5471) | 排序方向<br>(ℹ️ 不传默认为不排序) |
        | quarter | [FinancialQuarter](./quote_quote.md#2253) | 财报累积时间 |
        
    *   CustomIndicatorFilter 对象相关参数如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | stock\_field1 | [StockField](./quote_quote.md#2057) | 自定义技术指标属性 |
        | stock\_field1\_para | list | 自定义技术指标属性参数<br>(ℹ️ 根据指标类型进行传参：)  <br>1\. MA：\[平均移动周期\]  <br>2.EMA：\[指数移动平均周期\]  <br>3.RSI：\[RSI 指标周期\]  <br>4.MACD：\[快速平均线值, 慢速平均线值, DIF值\]  <br>5.BOLL：\[均线周期, 偏移值\]  <br>6.KDJ：\[RSV 周期, K 值计算周期, D 值计算周期\] |
        | relative\_position | [RelativePosition](./quote_quote.md#2453) | 相对位置 |
        | stock\_field2 | [StockField](./quote_quote.md#2057) | 自定义技术指标属性 |
        | stock\_field2\_para | list | 自定义技术指标属性参数<br>(ℹ️ 根据指标类型进行传参：)  <br>1\. MA：\[平均移动周期\]  <br>2.EMA：\[指数移动平均周期\]  <br>3.RSI：\[RSI 指标周期\]  <br>4.MACD：\[快速平均线值, 慢速平均线值, DIF值\]  <br>5.BOLL：\[均线周期, 偏移值\]  <br>6.KDJ：\[RSV 周期, K 值计算周期, D 值计算周期\] |
        | value | float | 自定义数值<br>(ℹ️ 当 stock\_field2 在 [StockField](./quote_quote.md#2057))<br> 中选择自定义数值时，value 为必传参数 |
        | ktype | [KLType](./quote_quote.md#4119) | K线类型 KLType<br>(ℹ️ 仅支持K\_60M，K\_DAY，K\_WEEK，K\_MON 四种时间周期) |
        | consecutive\_period | int | 筛选连续周期（consecutive\_period）都符合条件的数据<br>(ℹ️ 填写范围为\[1,12\]) |
        | is\_no\_filter | bool | 该字段是否不需要筛选<br>(ℹ️ True：不筛选)  <br>False：筛选  <br>不传默认不筛选 |
        
    *   PatternFilter 对象相关参数如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | stock\_field | [StockField](./quote_quote.md#159) | 形态技术指标属性 |
        | ktype | [KLType](./quote_quote.md#4119) | K线类型 KLType （仅支持K\_60M，K\_DAY，K\_WEEK，K\_MON 四种时间周期） |
        | consecutive\_period | int | 筛选连续周期（consecutive\_period）都符合条件的数据<br>(ℹ️ 填写范围为\[1,12\]) |
        | is\_no\_filter | bool | 该字段是否不需要筛选<br>(ℹ️ True：不筛选)  <br>False：筛选  <br>不传默认不筛选 |
        

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | tuple | 当 ret == RET\_OK，返回选股数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   选股数据元组组成如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | last\_page | bool | 是否是最后一页 |
        | all\_count | int | 列表总数量 |
        | stock\_list | list | 选股数据<br>(ℹ️ list 中元素类型是 FilterStockData) |
        
        *   FilterStockData 类型的字段格式：
            
            | 字段  | 类型  | 说明  |
            | --- | --- | --- |
            | stock\_code | str | 股票代码 |
            | stock\_name | str | 股票名字 |
            | cur\_price | float | 最新价 |
            | cur\_price\_to\_highest\_52weeks\_ratio | float | (现价 - 52周最高)/52周最高<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | cur\_price\_to\_lowest\_52weeks\_ratio | float | (现价 - 52周最低)/52周最低<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | high\_price\_to\_highest\_52weeks\_ratio | float | (今日最高 - 52周最高)/52周最高<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | low\_price\_to\_lowest\_52weeks\_ratio | float | (今日最低 - 52周最低)/52周最低<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | volume\_ratio | float | 量比  |
            | bid\_ask\_ratio | float | 委比<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | lot\_price | float | 每手价格 |
            | market\_val | float | 市值  |
            | pe\_annual | float | 市盈率 |
            | pe\_ttm | float | 市盈率 TTM |
            | pb\_rate | float | 市净率 |
            | change\_rate\_5min | float | 五分钟价格涨跌幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | change\_rate\_begin\_year | float | 年初至今价格涨跌幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | ps\_ttm | float | 市销率 TTM<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | pcf\_ttm | float | 市现率 TTM<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | total\_share | float | 总股数<br>(ℹ️ 单位：股) |
            | float\_share | float | 流通股数<br>(ℹ️ 单位：股) |
            | float\_market\_val | float | 流通市值<br>(ℹ️ 单位：元) |
            | change\_rate | float | 涨跌幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | amplitude | float | 振幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | volume | float | 日均成交量 |
            | turnover | float | 日均成交额 |
            | turnover\_rate | float | 换手率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | net\_profit | float | 净利润 |
            | net\_profix\_growth | float | 净利润增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | sum\_of\_business | float | 营业收入 |
            | sum\_of\_business\_growth | float | 营业同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | net\_profit\_rate | float | 净利率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | gross\_profit\_rate | float | 毛利率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | debt\_asset\_rate | float | 资产负债率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | return\_on\_equity\_rate | float | 净资产收益率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | roic | float | 投入资本回报率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | roa\_ttm | float | 资产回报率 TTM<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报) |
            | ebit\_ttm | float | 息税前利润 TTM<br>(ℹ️ 单位：元。仅适用于年报) |
            | ebitda | float | 税息折旧及摊销前利润<br>(ℹ️ 单位：元) |
            | operating\_margin\_ttm | float | 营业利润率 TTM<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报) |
            | ebit\_margin | float | EBIT 利润率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | ebitda\_margin | float | EBITDA 利润率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | financial\_cost\_rate | float | 财务成本率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | operating\_profit\_ttm | float | 营业利润 TTM<br>(ℹ️ 单位：元。仅适用于年报) |
            | shareholder\_net\_profit\_ttm | float | 归属于母公司的净利润<br>(ℹ️ 单位：元。仅适用于年报) |
            | net\_profit\_cash\_cover\_ttm | float | 盈利中的现金收入比例<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报) |
            | current\_ratio | float | 流动比率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | quick\_ratio | float | 速动比率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | current\_asset\_ratio | float | 流动资产率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | current\_debt\_ratio | float | 流动负债率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | equity\_multiplier | float | 权益乘数 |
            | property\_ratio | float | 产权比率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | cash\_and\_cash\_equivalents | float | 现金和现金等价<br>(ℹ️ 单位：元) |
            | total\_asset\_turnover | float | 总资产周转率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | fixed\_asset\_turnover | float | 固定资产周转率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | inventory\_turnover | float | 存货周转率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | operating\_cash\_flow\_ttm | float | 经营活动现金流 TTM<br>(ℹ️ 单位：元。仅适用于年报) |
            | accounts\_receivable | float | 应收账款净额<br>(ℹ️ 单位：元) |
            | ebit\_growth\_rate | float | EBIT 同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | operating\_profit\_growth\_rate | float | 营业利润同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | total\_assets\_growth\_rate | float | 总资产同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | profit\_to\_shareholders\_growth\_rate | float | 归母净利润同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | profit\_before\_tax\_growth\_rate | float | 总利润同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | eps\_growth\_rate | float | EPS 同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | roe\_growth\_rate | float | ROE 同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | roic\_growth\_rate | float | ROIC 同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | nocf\_growth\_rate | float | 经营现金流同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | nocf\_per\_share\_growth\_rate | float | 每股经营现金流同比增长率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | operating\_revenue\_cash\_cover | float | 经营现金收入比<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | operating\_profit\_to\_total\_profit | float | 营业利润占比<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
            | basic\_eps | float | 基本每股收益<br>(ℹ️ 单位：元) |
            | diluted\_eps | float | 稀释每股收益<br>(ℹ️ 单位：元) |
            | nocf\_per\_share | float | 每股经营现金净流量<br>(ℹ️ 单位：元) |
            | price | float | 最新价格 |
            | ma  | float | 简单均线<br>(ℹ️ 根据 MA 参数返回具体的数值) |
            | ma5 | float | 5日简单均线 |
            | ma10 | float | 10日简单均线 |
            | ma20 | float | 20日简单均线 |
            | ma30 | float | 30日简单均线 |
            | ma60 | float | 60日简单均线 |
            | ma120 | float | 120日简单均线 |
            | ma250 | float | 250日简单均线 |
            | rsi | float | RSI的值<br>(ℹ️ 根据 RSI 参数返回具体的数值，RSI 默认参数为12) |
            | ema | float | 指数移动均线<br>(ℹ️ 根据 EMA 参数返回具体的数值) |
            | ema5 | float | 5日指数移动均线 |
            | ema10 | float | 10日指数移动均线 |
            | ema20 | float | 20日指数移动均线 |
            | ema30 | float | 30日指数移动均线 |
            | ema60 | float | 60日指数移动均线 |
            | ema120 | float | 120日指数移动均线 |
            | ema250 | float | 250日指数移动均线 |
            | kdj\_k | float | KDJ 指标的 K 值<br>(ℹ️ 根据 KDJ 参数返回具体的数值，KDJ 默认参数为\[9,3,3\]) |
            | kdj\_d | float | KDJ 指标的 D 值<br>(ℹ️ 根据 KDJ 参数返回具体的数值，KDJ 默认参数为\[9,3,3\]) |
            | kdj\_j | float | KDJ 指标的 J 值<br>(ℹ️ 根据 KDJ 参数返回具体的数值，KDJ 默认参数为\[9,3,3\]) |
            | macd\_diff | float | MACD 指标的 DIFF 值<br>(ℹ️ 根据 MACD 参数返回具体的数值，MACD 默认参数为\[12,26,9\]) |
            | macd\_dea | float | MACD 指标的 DEA 值<br>(ℹ️ 根据 MACD 参数返回具体的数值，MACD 默认参数为\[12,26,9\]) |
            | macd | float | MACD 指标的 MACD 值<br>(ℹ️ 根据 MACD 参数返回具体的数值，MACD 默认参数为\[12,26,9\]) |
            | boll\_upper | float | BOLL 指标的 UPPER 值<br>(ℹ️ 根据 BOLL 参数返回具体的数值，BOLL 默认参数为\[20.2\]) |
            | boll\_middler | float | BOLL 指标的 MIDDLER 值<br>(ℹ️ 根据 BOLL 参数返回具体的数值，BOLL 默认参数为\[20.2\]) |
            | boll\_lower | float | BOLL 指标的 LOWER 值<br>(ℹ️ 根据 BOLL 参数返回具体的数值，BOLL 默认参数为\[20.2\]) |
            
*   **Example**
    

    from moomoo import *
    import time
    
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    simple_filter = SimpleFilter()
    simple_filter.filter_min = 2
    simple_filter.filter_max = 1000
    simple_filter.stock_field = StockField.CUR_PRICE
    simple_filter.is_no_filter = False
    # simple_filter.sort = SortDir.ASCEND
    
    financial_filter = FinancialFilter()
    financial_filter.filter_min = 0.5
    financial_filter.filter_max = 50
    financial_filter.stock_field = StockField.CURRENT_RATIO
    financial_filter.is_no_filter = False
    financial_filter.sort = SortDir.ASCEND
    financial_filter.quarter = FinancialQuarter.ANNUAL
    
    custom_filter = CustomIndicatorFilter()
    custom_filter.ktype = KLType.K_DAY
    custom_filter.stock_field1 = StockField.KDJ_K
    custom_filter.stock_field1_para = [10,4,4]
    custom_filter.stock_field2 = StockField.KDJ_K
    custom_filter.stock_field2_para = [9,3,3]
    custom_filter.relative_position = RelativePosition.MORE
    custom_filter.is_no_filter = False
    
    nBegin = 0
    last_page = False
    ret_list = list()
    while not last_page:
        nBegin += len(ret_list)
        ret, ls = quote_ctx.get_stock_filter(market=Market.HK, filter_list=[simple_filter, financial_filter, custom_filter], begin=nBegin)  # 对香港市场的股票做简单、财务和指标筛选
        if ret == RET_OK:
            last_page, all_count, ret_list = ls
            print('all count = ', all_count)
            for item in ret_list:
                print(item.stock_code)  # 取股票代码
                print(item.stock_name)  # 取股票名称
                print(item[simple_filter])   # 取 simple_filter 对应的变量值
                print(item[financial_filter])   # 取 financial_filter 对应的变量值
                print(item[custom_filter])  # 获取 custom_filter 的数值
        else:
            print('error: ', ls)
        time.sleep(3)  # 加入时间间隔，避免触发限频
    
    quote_ctx.close()  # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  
4  
5  
6  
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

*   **Output**

    39 39 [ stock_code:HK.08103  stock_name:HMVOD视频  cur_price:2.69  current_ratio(annual):4.413 ,  stock_code:HK.00376  stock_name:云锋金融  cur_price:2.96  current_ratio(annual):12.585 ,  stock_code:HK.09995  stock_name:荣昌生物-B  cur_price:92.65  current_ratio(annual):16.054 ,  stock_code:HK.80737  stock_name:湾区发展-R  cur_price:2.8  current_ratio(annual):17.249 ,  stock_code:HK.00737  stock_name:湾区发展  cur_price:3.25  current_ratio(annual):17.249 ,  stock_code:HK.03939  stock_name:万国国际矿业  cur_price:2.22  current_ratio(annual):17.323 ,  stock_code:HK.01055  stock_name:中国南方航空股份  cur_price:5.17  current_ratio(annual):17.529 ,  stock_code:HK.02638  stock_name:港灯-SS  cur_price:7.68  current_ratio(annual):21.255 ,  stock_code:HK.00670  stock_name:中国东方航空股份  cur_price:3.53  current_ratio(annual):25.194 ,  stock_code:HK.01952  stock_name:云顶新耀-B  cur_price:69.5  current_ratio(annual):26.029 ,  stock_code:HK.00089  stock_name:大生地产  cur_price:4.22  current_ratio(annual):26.914 ,  stock_code:HK.00728  stock_name:中国电信  cur_price:2.81  current_ratio(annual):27.651 ,  stock_code:HK.01372  stock_name:比速科技  cur_price:5.1  current_ratio(annual):28.303 ,  stock_code:HK.00753  stock_name:中国国航  cur_price:6.38  current_ratio(annual):31.828 ,  stock_code:HK.01997  stock_name:九龙仓置业  cur_price:43.75  current_ratio(annual):33.239 ,  stock_code:HK.02158  stock_name:医渡科技  cur_price:39.0  current_ratio(annual):34.046 ,  stock_code:HK.02588  stock_name:中银航空租赁  cur_price:77.0  current_ratio(annual):34.531 ,  stock_code:HK.01330  stock_name:绿色动力环保  cur_price:3.36  current_ratio(annual):35.028 ,  stock_code:HK.01525  stock_name:建桥教育  cur_price:6.28  current_ratio(annual):36.989 ,  stock_code:HK.09908  stock_name:嘉兴燃气  cur_price:10.02  current_ratio(annual):37.848 ,  stock_code:HK.06078  stock_name:海吉亚医疗  cur_price:49.8  current_ratio(annual):39.0 ,  stock_code:HK.01071  stock_name:华电国际电力股份  cur_price:2.16  current_ratio(annual):39.507 ,  stock_code:HK.00357  stock_name:美兰空港  cur_price:34.15  current_ratio(annual):39.514 ,  stock_code:HK.00762  stock_name:中国联通  cur_price:5.15  current_ratio(annual):40.74 ,  stock_code:HK.01787  stock_name:山东黄金  cur_price:15.56  current_ratio(annual):41.604 ,  stock_code:HK.00902  stock_name:华能国际电力股份  cur_price:2.66  current_ratio(annual):42.919 ,  stock_code:HK.00934  stock_name:中石化冠德  cur_price:2.96  current_ratio(annual):43.361 ,  stock_code:HK.01117  stock_name:现代牧业  cur_price:2.3  current_ratio(annual):45.037 ,  stock_code:HK.00177  stock_name:江苏宁沪高速公路  cur_price:8.78  current_ratio(annual):45.93 ,  stock_code:HK.01379  stock_name:温岭工量刃具  cur_price:5.71  current_ratio(annual):46.774 ,  stock_code:HK.01876  stock_name:百威亚太  cur_price:22.5  current_ratio(annual):46.917 ,  stock_code:HK.01907  stock_name:中国旭阳集团  cur_price:4.38  current_ratio(annual):47.129 ,  stock_code:HK.02160  stock_name:心通医疗-B  cur_price:15.54  current_ratio(annual):47.384 ,  stock_code:HK.00293  stock_name:国泰航空  cur_price:7.1  current_ratio(annual):47.983 ,  stock_code:HK.00694  stock_name:北京首都机场股份  cur_price:6.34  current_ratio(annual):47.985 ,  stock_code:HK.09922  stock_name:九毛九  cur_price:26.65  current_ratio(annual):48.278 ,  stock_code:HK.01083  stock_name:港华燃气  cur_price:3.39  current_ratio(annual):49.2 ,  stock_code:HK.00291  stock_name:华润啤酒  cur_price:58.0  current_ratio(annual):49.229 ,  stock_code:HK.00306  stock_name:冠忠巴士集团  cur_price:2.29  current_ratio(annual):49.769 ]
    HK.08103
    HMVOD视频
    2.69
    2.69
    4.413
    ...
    HK.00306
    冠忠巴士集团
    2.29
    2.29
    49.769
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

[#](./quote_get-stock-filter.md#2109-2)
 Qot\_StockFilter.proto
-------------------------------------------------------------------------------------------------------

*   **介绍**
    
    条件选股
    
*   **参数**
    

    // 使用以下6种结构(BaseFilter、AcumulateFilter、FinancialFilter、BaseData、AcumulateData、FinancialData)的用户请注意，由于属性字段名“field”与C#protocol buf保留的函数名冲突，moomoo API将从3.18版本开始将该字段重命名为“fieldName”。请注意修改相应界面中使用的字段名称。
    
    // 简单属性筛选
    message BaseFilter 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    }
    
    // 累积属性筛选
    message AccumulateFilter
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 days = 6; // 近几日，累积时间
    }
    
    // 财务属性筛选
    message FinancialFilter
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 quarter = 6; // FinancialQuarter 财报累积时间
    }
    
    // 形态技术指标属性筛选
    message PatternFilter
    {
    	required int32 fieldName = 1; // PatternField 形态技术指标属性
    	required int32 klType = 2; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
    	optional bool isNoFilter = 3; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        optional int32 consecutivePeriod = 4; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    // 自定义技术指标属性筛选
    message CustomIndicatorFilter
    {
    	required int32 firstFieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required int32 secondFieldName = 2; // CustomIndicatorField 自定义技术指标属性
    	required int32 relativePosition = 3; // RelativePosition 相对位置
    	optional double fieldValue = 4; // 自定义数值
    	required int32 klType = 5; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期	
    	optional bool isNoFilter = 6; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        repeated int32 firstFieldParaList = 7; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	repeated int32 secondFieldParaList = 8; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	optional int32 consecutivePeriod = 9; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    message C2S
    {
        required int32 begin = 1; // 数据起始点
        required int32 num =  2;  // 请求数据个数，最大200        
    	required int32 market= 3; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	// 以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security plate = 4; // 板块
    	repeated BaseFilter baseFilterList = 5; // 简单指标过滤器
    	repeated AccumulateFilter accumulateFilterList = 6; // 累积指标过滤器 累积属性的同一筛选条件数量上限 10 个
    	repeated FinancialFilter financialFilterList = 7; // 财务指标过滤器
    	repeated PatternFilter patternFilterList = 8; // 形态技术指标过滤器
    	repeated CustomIndicatorFilter customIndicatorFilterList = 9; // 自定义技术指标过滤器
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   形态技术指标属性筛选条件参见 [PatternField](./quote_quote.md#159)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   排序方向参见 [SortDir](./quote_quote.md#5471)
>     
> *   相对位置参见 [RelativePosition](./quote_quote.md#2453)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     

*   **返回**

    // 简单属性数据
    message BaseData 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
        required double value = 2;
    }
    
    // 累积属性数据
    message AccumulateData
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
        required double value = 2;
    	required int32 days = 3; // 近几日，累积时间
    }
    
    // 财务属性数据
    message FinancialData
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
        required double value = 2;
    	required int32 quarter = 3; // FinancialQuarter 财报累积时间
    }
    
    // 自定义技术指标属性数据
    message CustomIndicatorData
    {
    	required int32 fieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required double value = 2; 
    	required int32 klType = 3; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
        repeated int32 fieldParaList = 4; // 自定义指标参数 根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    }
    
    // 返回的股票数据
    message StockData  
    {
        required Qot_Common.Security security = 1; // 股票
        required string name = 2; // 股票名称
        repeated BaseData baseDataList = 3; // 筛选后的简单指标属性数据
        repeated AccumulateData accumulateDataList = 4; // 筛选后的累积指标属性数据
        repeated FinancialData financialDataList = 5; // 筛选后的财务指标属性数据
        repeated CustomIndicatorData customIndicatorDataList = 6; // 自定义技术指标属性数据
        // CustomIndicatorFilter 中的 firstFieldName 和 secondFieldName 的数值会在这个列表中分开返回
    }
    
    message S2C
    {
        required bool lastPage = 1; // 是否最后一页了,false:非最后一页,还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; // 该条件请求所有数据的个数
        repeated StockData dataList = 3; // 返回的股票数据列表
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; // RetType,返回结果
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3215
    

`uint StockFilter(QotStockFilter.Request req);`  
`virtual void OnReply_StockFilter(MMAPI_Conn client, uint nSerialNo, QotStockFilter.Response rsp);`

*   **介绍**
    
    条件选股
    
*   **参数**
    

    // 使用以下6种结构(BaseFilter、AcumulateFilter、FinancialFilter、BaseData、AcumulateData、FinancialData)的用户请注意，由于属性字段名“field”与C#protocol buf保留的函数名冲突，moomoo API将从3.18版本开始将该字段重命名为“fieldName”。请注意修改相应界面中使用的字段名称。
    
    // 简单属性筛选
    message BaseFilter 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    }
    
    // 累积属性筛选
    message AccumulateFilter
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 days = 6; // 近几日，累积时间
    }
    
    // 财务属性筛选
    message FinancialFilter
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 quarter = 6; // FinancialQuarter 财报累积时间
    }
    
    // 形态技术指标属性筛选
    message PatternFilter
    {
    	required int32 fieldName = 1; // PatternField 形态技术指标属性
    	required int32 klType = 2; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
    	optional bool isNoFilter = 3; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        optional int32 consecutivePeriod = 4; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    // 自定义技术指标属性筛选
    message CustomIndicatorFilter
    {
    	required int32 firstFieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required int32 secondFieldName = 2; // CustomIndicatorField 自定义技术指标属性
    	required int32 relativePosition = 3; // RelativePosition 相对位置
    	optional double fieldValue = 4; // 自定义数值
    	required int32 klType = 5; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期	
    	optional bool isNoFilter = 6; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        repeated int32 firstFieldParaList = 7; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	repeated int32 secondFieldParaList = 8; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	optional int32 consecutivePeriod = 9; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    message C2S
    {
        required int32 begin = 1; // 数据起始点
        required int32 num =  2;  // 请求数据个数，最大200        
    	required int32 market= 3; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	// 以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security plate = 4; // 板块
    	repeated BaseFilter baseFilterList = 5; // 简单指标过滤器
    	repeated AccumulateFilter accumulateFilterList = 6; // 累积指标过滤器 累积属性的同一筛选条件数量上限 10 个
    	repeated FinancialFilter financialFilterList = 7; // 财务指标过滤器
    	repeated PatternFilter patternFilterList = 8; // 形态技术指标过滤器
    	repeated CustomIndicatorFilter customIndicatorFilterList = 9; // 自定义技术指标过滤器
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   形态技术指标属性筛选条件参见 [PatternField](./quote_quote.md#159)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   排序方向参见 [SortDir](./quote_quote.md#5471)
>     
> *   相对位置参见 [RelativePosition](./quote_quote.md#2453)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     

*   **返回**

    // 简单属性数据
    message BaseData 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
        required double value = 2;
    }
    
    // 累积属性数据
    message AccumulateData
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
        required double value = 2;
    	required int32 days = 3; // 近几日，累积时间
    }
    
    // 财务属性数据
    message FinancialData
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
        required double value = 2;
    	required int32 quarter = 3; // FinancialQuarter 财报累积时间
    }
    
    // 自定义技术指标属性数据
    message CustomIndicatorData
    {
    	required int32 fieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required double value = 2; 
    	required int32 klType = 3; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
        repeated int32 fieldParaList = 4; // 自定义指标参数 根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    }
    
    // 返回的股票数据
    message StockData  
    {
        required Qot_Common.Security security = 1; // 股票
    	required string name = 2; // 股票名称
        repeated BaseData baseDataList = 3; // 筛选后的简单指标属性数据
    	repeated AccumulateData accumulateDataList = 4; // 筛选后的累积指标属性数据
    	repeated FinancialData financialDataList = 5; // 筛选后的财务指标属性数据
        repeated CustomIndicatorData customIndicatorDataList = 6; // 自定义技术指标属性数据
        // CustomIndicatorFilter 中的 firstFieldName 和 secondFieldName 的数值会在这个列表中分开返回
    }
    
    message S2C
    {
        required bool lastPage = 1; // 是否最后一页了,false:非最后一页,还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; // 该条件请求所有数据的个数
        repeated StockData dataList = 3; // 返回的股票数据列表
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; // RetType,返回结果
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            QotStockFilter.BaseFilter filter = QotStockFilter.BaseFilter.CreateBuilder()
                    .SetFieldName(QotStockFilter.StockField.StockField_CurPrice)
                    .SetFilterMax(100)
                    .SetFilterMax(200)
                    .Build();
            QotStockFilter.C2S c2s = QotStockFilter.C2S.CreateBuilder()
                    .SetBegin(0)
                    .SetNum(10)
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .AddBaseFilterList(filter)
                .Build();
            QotStockFilter.Request req = QotStockFilter.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.StockFilter(req);
            Console.Write("Send QotStockFilter: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_StockFilter(MMAPI_Conn client, uint nSerialNo, QotStockFilter.Response rsp)
        {
            Console.Write("Reply: QotStockFilter: {0}\n", nSerialNo, rsp.ToString());
            Console.Write("code: {0}, name: {1} \n", rsp.S2C.DataListList[0].Security.Code,
                rsp.S2C.DataListList[0].Name);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825721743616225277
    Send QotStockFilter: 3
    Reply: QotStockFilter: 3
    code: 00376, name: 云锋金融
    

1  
2  
3  
4  

`int stockFilter(QotStockFilter.Request req);`  
`void onReply_StockFilter(MMAPI_Conn client, int nSerialNo, QotStockFilter.Response rsp);`

*   **介绍**
    
    条件选股
    
*   **参数**
    

    // 使用以下6种结构(BaseFilter、AcumulateFilter、FinancialFilter、BaseData、AcumulateData、FinancialData)的用户请注意，由于属性字段名“field”与C#protocol buf保留的函数名冲突，moomoo API将从3.18版本开始将该字段重命名为“fieldName”。请注意修改相应界面中使用的字段名称。
    
    // 简单属性筛选
    message BaseFilter 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    }
    
    // 累积属性筛选
    message AccumulateFilter
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 days = 6; // 近几日，累积时间
    }
    
    // 财务属性筛选
    message FinancialFilter
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 quarter = 6; // FinancialQuarter 财报累积时间
    }
    
    // 形态技术指标属性筛选
    message PatternFilter
    {
    	required int32 fieldName = 1; // PatternField 形态技术指标属性
    	required int32 klType = 2; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
    	optional bool isNoFilter = 3; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        optional int32 consecutivePeriod = 4; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    // 自定义技术指标属性筛选
    message CustomIndicatorFilter
    {
    	required int32 firstFieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required int32 secondFieldName = 2; // CustomIndicatorField 自定义技术指标属性
    	required int32 relativePosition = 3; // RelativePosition 相对位置
    	optional double fieldValue = 4; // 自定义数值
    	required int32 klType = 5; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期	
    	optional bool isNoFilter = 6; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        repeated int32 firstFieldParaList = 7; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	repeated int32 secondFieldParaList = 8; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	optional int32 consecutivePeriod = 9; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    message C2S
    {
        required int32 begin = 1; // 数据起始点
        required int32 num =  2;  // 请求数据个数，最大200        
    	required int32 market= 3; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	// 以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security plate = 4; // 板块
    	repeated BaseFilter baseFilterList = 5; // 简单指标过滤器
    	repeated AccumulateFilter accumulateFilterList = 6; // 累积指标过滤器 累积属性的同一筛选条件数量上限 10 个
    	repeated FinancialFilter financialFilterList = 7; // 财务指标过滤器
    	repeated PatternFilter patternFilterList = 8; // 形态技术指标过滤器
    	repeated CustomIndicatorFilter customIndicatorFilterList = 9; // 自定义技术指标过滤器
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   形态技术指标属性筛选条件参见 [PatternField](./quote_quote.md#159)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   排序方向参见 [SortDir](./quote_quote.md#5471)
>     
> *   相对位置参见 [RelativePosition](./quote_quote.md#2453)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     

*   **返回**

    // 简单属性数据
    message BaseData 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
        required double value = 2;
    }
    
    // 累积属性数据
    message AccumulateData
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
        required double value = 2;
    	required int32 days = 3; // 近几日，累积时间
    }
    
    // 财务属性数据
    message FinancialData
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
        required double value = 2;
    	required int32 quarter = 3; // FinancialQuarter 财报累积时间
    }
    
    // 自定义技术指标属性数据
    message CustomIndicatorData
    {
    	required int32 fieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required double value = 2; 
    	required int32 klType = 3; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
        repeated int32 fieldParaList = 4; // 自定义指标参数 根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    }
    
    // 返回的股票数据
    message StockData  
    {
        required Qot_Common.Security security = 1; // 股票
    	required string name = 2; // 股票名称
        repeated BaseData baseDataList = 3; // 筛选后的简单指标属性数据
    	repeated AccumulateData accumulateDataList = 4; // 筛选后的累积指标属性数据
    	repeated FinancialData financialDataList = 5; // 筛选后的财务指标属性数据
        repeated CustomIndicatorData customIndicatorDataList = 6; // 自定义技术指标属性数据
        // CustomIndicatorFilter 中的 firstFieldName 和 secondFieldName 的数值会在这个列表中分开返回
    }
    
    message S2C
    {
        required bool lastPage = 1; // 是否最后一页了,false:非最后一页,还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; // 该条件请求所有数据的个数
        repeated StockData dataList = 3; // 返回的股票数据列表
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; // RetType,返回结果
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            QotStockFilter.BaseFilter filter = QotStockFilter.BaseFilter.newBuilder()
                    .setFieldName(QotStockFilter.StockField.StockField_CurPrice_VALUE)
                    .setFilterMax(100)
                    .setFilterMax(200)
                    .build();
            QotStockFilter.C2S c2s = QotStockFilter.C2S.newBuilder()
                    .setBegin(0)
                    .setNum(10)
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .addBaseFilterList(filter)
                .build();
            QotStockFilter.Request req = QotStockFilter.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.stockFilter(req);
            System.out.printf("Send QotStockFilter: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_StockFilter(MMAPI_Conn client, int nSerialNo, QotStockFilter.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotStockFilter failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotStockFilter: %s\n", json);
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

*   **Output**

    Send QotStockFilter: 2
    Receive QotStockFilter: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "lastPage": false,
        "allCount": 2431,
        "dataList": [{\
          "security": {\
            "market": 1,\
            "code": "08560"\
          },\
          "name": "世大控股"\
        }, ... {\
          "security": {\
            "market": 1,\
            "code": "02171"\
          },\
          "name": "科济药业-B"\
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

`moomoo::u32_t StockFilter(const Qot_StockFilter::Request &stReq);`  
`virtual void OnReply_StockFilter(moomoo::u32_t nSerialNo, const Qot_StockFilter::Response &stRsp) = 0;`

*   **介绍**
    
    条件选股
    
*   **参数**
    

    // 使用以下6种结构(BaseFilter、AcumulateFilter、FinancialFilter、BaseData、AcumulateData、FinancialData)的用户请注意，由于属性字段名“field”与C#protocol buf保留的函数名冲突，moomoo API将从3.18版本开始将该字段重命名为“fieldName”。请注意修改相应界面中使用的字段名称。
    
    // 简单属性筛选
    message BaseFilter 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    }
    
    // 累积属性筛选
    message AccumulateFilter
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 days = 6; // 近几日，累积时间
    }
    
    // 财务属性筛选
    message FinancialFilter
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 quarter = 6; // FinancialQuarter 财报累积时间
    }
    
    // 形态技术指标属性筛选
    message PatternFilter
    {
    	required int32 fieldName = 1; // PatternField 形态技术指标属性
    	required int32 klType = 2; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
    	optional bool isNoFilter = 3; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        optional int32 consecutivePeriod = 4; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    // 自定义技术指标属性筛选
    message CustomIndicatorFilter
    {
    	required int32 firstFieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required int32 secondFieldName = 2; // CustomIndicatorField 自定义技术指标属性
    	required int32 relativePosition = 3; // RelativePosition 相对位置
    	optional double fieldValue = 4; // 自定义数值
    	required int32 klType = 5; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期	
    	optional bool isNoFilter = 6; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        repeated int32 firstFieldParaList = 7; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	repeated int32 secondFieldParaList = 8; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	optional int32 consecutivePeriod = 9; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    message C2S
    {
        required int32 begin = 1; // 数据起始点
        required int32 num =  2;  // 请求数据个数，最大200        
    	required int32 market= 3; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	// 以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security plate = 4; // 板块
    	repeated BaseFilter baseFilterList = 5; // 简单指标过滤器
    	repeated AccumulateFilter accumulateFilterList = 6; // 累积指标过滤器 累积属性的同一筛选条件数量上限 10 个
    	repeated FinancialFilter financialFilterList = 7; // 财务指标过滤器
    	repeated PatternFilter patternFilterList = 8; // 形态技术指标过滤器
    	repeated CustomIndicatorFilter customIndicatorFilterList = 9; // 自定义技术指标过滤器
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   形态技术指标属性筛选条件参见 [PatternField](./quote_quote.md#159)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   排序方向参见 [SortDir](./quote_quote.md#5471)
>     
> *   相对位置参见 [RelativePosition](./quote_quote.md#2453)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     

*   **返回**

    // 简单属性数据
    message BaseData 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
        required double value = 2;
    }
    
    // 累积属性数据
    message AccumulateData
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
        required double value = 2;
    	required int32 days = 3; // 近几日，累积时间
    }
    
    // 财务属性数据
    message FinancialData
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
        required double value = 2;
    	required int32 quarter = 3; // FinancialQuarter 财报累积时间
    }
    
    // 自定义技术指标属性数据
    message CustomIndicatorData
    {
    	required int32 fieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required double value = 2; 
    	required int32 klType = 3; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
        repeated int32 fieldParaList = 4; // 自定义指标参数 根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    }
    
    // 返回的股票数据
    message StockData  
    {
        required Qot_Common.Security security = 1; // 股票
    	required string name = 2; // 股票名称
        repeated BaseData baseDataList = 3; // 筛选后的简单指标属性数据
    	repeated AccumulateData accumulateDataList = 4; // 筛选后的累积指标属性数据
    	repeated FinancialData financialDataList = 5; // 筛选后的财务指标属性数据
        repeated CustomIndicatorData customIndicatorDataList = 6; // 自定义技术指标属性数据
        // CustomIndicatorFilter 中的 firstFieldName 和 secondFieldName 的数值会在这个列表中分开返回
    }
    
    message S2C
    {
        required bool lastPage = 1; // 是否最后一页了,false:非最后一页,还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; // 该条件请求所有数据的个数
        repeated StockData dataList = 3; // 返回的股票数据列表
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; // RetType,返回结果
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    		Qot_StockFilter::Request req;
    		Qot_StockFilter::C2S *c2s = req.mutable_c2s();
    		c2s->set_begin(0);
    		c2s->set_num(50);
    		c2s->set_market(1);
            
            m_StockFilterSerialNo = m_pQotApi->StockFilter(req);
            cout << "Request StockFilter SerialNo: " << m_StockFilterSerialNo << endl;
    	}
    
    	virtual void OnReply_StockFilter(moomoo::u32_t nSerialNo, const Qot_StockFilter::Response &stRsp){
            if(nSerialNo == m_StockFilterSerialNo)
            {
                cout << "OnReply_StockFilter SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_StockFilterSerialNo;
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

*   **Output**

    connect
    Request StockFilter SerialNo: 4
    OnReply_StockFilter SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "lastPage": false,
      "allCount": 2426,
      "dataList": [\
       {\
        "security": {\
         "market": 1,\
         "code": "02930"\
        },\
        "name": "丝路物流控股"\
       },\
    ...\
       {\
        "security": {\
         "market": 1,\
         "code": "01440"\
        },\
        "name": "DEYUN HOLDING"\
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

`StockFilter(req);`

*   **介绍**
    
    条件选股
    
*   **参数**
    

    // 使用以下6种结构(BaseFilter、AcumulateFilter、FinancialFilter、BaseData、AcumulateData、FinancialData)的用户请注意，由于属性字段名“field”与C#protocol buf保留的函数名冲突，moomoo API将从3.18版本开始将该字段重命名为“fieldName”。请注意修改相应界面中使用的字段名称。
    
    // 简单属性筛选
    message BaseFilter 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    }
    
    // 累积属性筛选
    message AccumulateFilter
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 days = 6; // 近几日，累积时间
    }
    
    // 财务属性筛选
    message FinancialFilter
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
    	optional double filterMin = 2; // 区间下限（闭区间），不传代表下限为 -∞
    	optional double filterMax = 3; // 区间上限（闭区间），不传代表上限为 +∞
    	optional bool isNoFilter = 4; // 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认不筛选
    	optional int32 sortDir = 5; // SortDir 排序方向，默认不排序。
    	required int32 quarter = 6; // FinancialQuarter 财报累积时间
    }
    
    // 形态技术指标属性筛选
    message PatternFilter
    {
    	required int32 fieldName = 1; // PatternField 形态技术指标属性
    	required int32 klType = 2; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
    	optional bool isNoFilter = 3; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        optional int32 consecutivePeriod = 4; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    // 自定义技术指标属性筛选
    message CustomIndicatorFilter
    {
    	required int32 firstFieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required int32 secondFieldName = 2; // CustomIndicatorField 自定义技术指标属性
    	required int32 relativePosition = 3; // RelativePosition 相对位置
    	optional double fieldValue = 4; // 自定义数值
    	required int32 klType = 5; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期	
    	optional bool isNoFilter = 6; // 该字段是否不需要筛选，True代表不筛选，False代表筛选。不传默认为不筛选
        repeated int32 firstFieldParaList = 7; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	repeated int32 secondFieldParaList = 8; // 自定义指标参数。根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    	optional int32 consecutivePeriod = 9; // 筛选连续周期（consecutivePeriod）都符合条件的数据，填写范围为[1,12]
    }
    
    message C2S
    {
        required int32 begin = 1; // 数据起始点
        required int32 num =  2;  // 请求数据个数，最大200        
    	required int32 market= 3; // Qot_Common::QotMarket 股票市场，支持沪股和深股，且沪股和深股不做区分都代表 A 股市场。
    	// 以下为筛选条件，可选字段，不填表示不过滤
    	optional Qot_Common.Security plate = 4; // 板块
    	repeated BaseFilter baseFilterList = 5; // 简单指标过滤器
    	repeated AccumulateFilter accumulateFilterList = 6; // 累积指标过滤器 累积属性的同一筛选条件数量上限 10 个
    	repeated FinancialFilter financialFilterList = 7; // 财务指标过滤器
    	repeated PatternFilter patternFilterList = 8; // 形态技术指标过滤器
    	repeated CustomIndicatorFilter customIndicatorFilterList = 9; // 自定义技术指标过滤器
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   形态技术指标属性筛选条件参见 [PatternField](./quote_quote.md#159)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   排序方向参见 [SortDir](./quote_quote.md#5471)
>     
> *   相对位置参见 [RelativePosition](./quote_quote.md#2453)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     

*   **返回**

    // 简单属性数据
    message BaseData 
    { 
    	required int32 fieldName = 1; // StockField 简单属性
        required double value = 2;
    }
    
    // 累积属性数据
    message AccumulateData
    {
    	required int32 fieldName = 1; // AccumulateField 累积属性
        required double value = 2;
    	required int32 days = 3; // 近几日，累积时间
    }
    
    // 财务属性数据
    message FinancialData
    {
    	required int32 fieldName = 1; // FinancialField 财务属性
        required double value = 2;
    	required int32 quarter = 3; // FinancialQuarter 财报累积时间
    }
    
    // 自定义技术指标属性数据
    message CustomIndicatorData
    {
    	required int32 fieldName = 1; // CustomIndicatorField 自定义技术指标属性
    	required double value = 2; 
    	required int32 klType = 3; // Qot_Common.KLType，K线类型，仅支持K_60M，K_DAY，K_WEEK，K_MON 四种时间周期
        repeated int32 fieldParaList = 4; // 自定义指标参数 根据指标类型进行传参：1. MA：[平均移动周期] 2.EMA：[指数移动平均周期] 3.RSI：[RSI 指标周期] 4.MACD：[快速平均线值, 慢速平均线值, DIF值] 5.BOLL：[均线周期, 偏移值] 6.KDJ：[RSV 周期, K 值计算周期, D 值计算周期]
    }
    
    // 返回的股票数据
    message StockData  
    {
        required Qot_Common.Security security = 1; // 股票
    	required string name = 2; // 股票名称
        repeated BaseData baseDataList = 3; // 筛选后的简单指标属性数据
    	repeated AccumulateData accumulateDataList = 4; // 筛选后的累积指标属性数据
    	repeated FinancialData financialDataList = 5; // 筛选后的财务指标属性数据
        repeated CustomIndicatorData customIndicatorDataList = 6; // 自定义技术指标属性数据
        // CustomIndicatorFilter 中的 firstFieldName 和 secondFieldName 的数值会在这个列表中分开返回
    }
    
    message S2C
    {
        required bool lastPage = 1; // 是否最后一页了,false:非最后一页,还有窝轮记录未返回; true:已是最后一页
    	required int32 allCount = 2; // 该条件请求所有数据的个数
        repeated StockData dataList = 3; // 返回的股票数据列表
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; // RetType,返回结果
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

> *   股票结构参见 [Security](./quote_quote.md#1377)
>     
> *   简单属性筛选条件参见 [StockField](./quote_quote.md#860)
>     
> *   累积属性筛选条件参见 [AccumulateField](./quote_quote.md#4370)
>     
> *   财务属性筛选条件参见 [FinancialField](./quote_quote.md#8542)
>     
> *   财报时间周期参见 [FinancialQuarter](./quote_quote.md#2253)
>     
> *   自定义技术指标属性筛选条件参见 [CustomIndicatorField](./quote_quote.md#2057)
>     
> *   K线类型参见 [KLType](./quote_quote.md#4119)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotStockFilter(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        begin: 0, 
                        num: 2,
                        market: QotMarket.QotMarket_HK_Security,
                    },
                };
    
                websocket.StockFilter(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("StockFilter: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    StockFilter: errCode 0, retMsg , retType 0
    {
      "lastPage": false,
      "allCount": 2436,
      "dataList": [{\
        "security": {\
          "market": 1,\
          "code": "08579"\
        },\
        "name": "万亚企业控股"\
      }, {\
        "security": {\
          "market": 1,\
          "code": "09869"\
        },\
        "name": "海伦司"\
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

提示

*   利用[获取子板块列表函数](./quote_get-plate-list.md)
     获取子板块代码，条件选股支持的板块分别为
    1.  港股的行业板块和概念板块。
    2.  美股的行业板块
    3.  沪深的行业板块，概念板块和地域板块
*   支持的板块指数代码
    
    | 代码  | 说明  |
    | --- | --- |
    | HK.Motherboard | 港股主板 |
    | HK.GEM | 港股创业板 |
    | HK.BK1911 | H 股主板 |
    | HK.BK1912 | H 股创业板 |
    | US.NYSE | 纽交所 |
    | US.AMEX | 美交所 |
    | US.NASDAQ | 纳斯达克 |
    | SH.3000000 | 上海主板 |
    | SZ.3000001 | 深证主板 |
    | SZ.3000004 | 深证创业板 |
    

接口限制

*   每 30 秒内最多请求 10 次条件选股接口
*   每页返回的筛选结果最多 200 个
*   建议筛选条件不超过 250 个，否则可能会出现“业务处理超时没返回”
*   累积属性的同一筛选条件数量上限 10 个
*   如果使用“最新价”等动态数据作为排序字段，在多页获取的间隙，数据的排序有可能发生变化
*   非同类指标不支持比较，仅限于同类指标之间建立比较关系，跨不同类型的指标比较会报错。例如：MA5 和 MA10 可以建立关系。MA5和EMA10不能建立关系。
*   自定义指标属性的同一类筛选条件超出数量上限10个
*   简单属性，财务属性，形态属性不支持对同一字段重复指定筛选条件
*   条件选股暂不支持美股盘前盘后、夜盘，筛选结果均按照盘中数据返回

← [获取期货合约资料](./quote_get-future-info.md) [获取板块内股票列表](./quote_get-plate-stock.md)
 →

[条件选股](./quote_get-stock-filter.md)