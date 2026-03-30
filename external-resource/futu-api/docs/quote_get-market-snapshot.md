# 获取快照 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quote/get-market-snapshot.html

[#](https://openapi.futunn.com/futu-api-doc/quote/get-market-snapshot.html#273)
 获取快照
=====================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_market_snapshot(code_list)`

*   **介绍**
    
    获取快照数据
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 股票代码列表<br>(ℹ️ 每次最多可请求 400 个标的  <br>list 内元素类型为 str) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回股票快照数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   股票快照数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | update\_time | str | 当前价更新时间<br>(ℹ️ 格式：yyyy-MM-dd HH:mm:ss  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | last\_price | float | 最新价格 |
        | open\_price | float | 今日开盘价 |
        | high\_price | float | 最高价格 |
        | low\_price | float | 最低价格 |
        | prev\_close\_price | float | 昨收盘价格 |
        | volume | int | 成交数量 |
        | turnover | float | 成交金额 |
        | turnover\_rate | float | 换手率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | suspension | bool | 是否停牌<br>(ℹ️ True：停牌) |
        | listing\_date | str | 上市日期<br>(ℹ️ 格式：yyyy-MM-dd) |
        | equity\_valid | bool | 是否正股<br>(ℹ️ 此字段返回为 True 时，以下正股相关字段才有合法数值) |
        | issued\_shares | int | 总股本 |
        | total\_market\_val | float | 总市值<br>(ℹ️ 单位：元) |
        | net\_asset | int | 资产净值 |
        | net\_profit | int | 净利润 |
        | earning\_per\_share | float | 每股盈利 |
        | outstanding\_shares | int | 流通股本 |
        | net\_asset\_per\_share | float | 每股净资产 |
        | circular\_market\_val | float | 流通市值<br>(ℹ️ 单位：元) |
        | ey\_ratio | float | 收益率<br>(ℹ️ 该字段为比例字段，默认不展示 %) |
        | pe\_ratio | float | 市盈率<br>(ℹ️ 该字段为比例字段，默认不展示 %) |
        | pb\_ratio | float | 市净率<br>(ℹ️ 该字段为比例字段，默认不展示 %) |
        | pe\_ttm\_ratio | float | 市盈率 TTM<br>(ℹ️ 该字段为比例字段，默认不展示 %) |
        | dividend\_ttm | float | 股息 TTM，派息 |
        | dividend\_ratio\_ttm | float | 股息率 TTM<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | dividend\_lfy | float | 股息 LFY，上一年度派息 |
        | dividend\_lfy\_ratio | float | 股息率 LFY<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | stock\_owner | str | 窝轮所属正股的代码或期权的标的股代码 |
        | wrt\_valid | bool | 是否是窝轮<br>(ℹ️ 此字段返回为 True 时，以下窝轮相关字段才有合法数值) |
        | wrt\_conversion\_ratio | float | 换股比率 |
        | wrt\_type | [WrtType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926) | 窝轮类型 |
        | wrt\_strike\_price | float | 行使价格 |
        | wrt\_maturity\_date | str | 格式化窝轮到期时间 |
        | wrt\_end\_trade | str | 格式化窝轮最后交易时间 |
        | wrt\_leverage | float | 杠杆比率<br>(ℹ️ 单位：倍) |
        | wrt\_ipop | float | 价内/价外<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | wrt\_break\_even\_point | float | 打和点 |
        | wrt\_conversion\_price | float | 换股价 |
        | wrt\_price\_recovery\_ratio | float | 正股距收回价<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | wrt\_score | float | 窝轮综合评分 |
        | wrt\_code | str | 窝轮对应的正股（此字段已废除，修改为 stock\_owner） |
        | wrt\_recovery\_price | float | 窝轮收回价 |
        | wrt\_street\_vol | float | 窝轮街货量 |
        | wrt\_issue\_vol | float | 窝轮发行量 |
        | wrt\_street\_ratio | float | 窝轮街货占比<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | wrt\_delta | float | 窝轮对冲值 |
        | wrt\_implied\_volatility | float | 窝轮引伸波幅 |
        | wrt\_premium | float | 窝轮溢价<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | wrt\_upper\_strike\_price | float | 上限价<br>(ℹ️ 仅界内证支持该字段) |
        | wrt\_lower\_strike\_price | float | 下限价<br>(ℹ️ 仅界内证支持该字段) |
        | wrt\_inline\_price\_status | [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407) | 界内界外<br>(ℹ️ 仅界内证支持该字段) |
        | wrt\_issuer\_code | str | 发行人代码 |
        | option\_valid | bool | 是否是期权<br>(ℹ️ 此字段返回为 True 时，以下期权相关字段才有合法数值) |
        | option\_type | [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713) | 期权类型 |
        | strike\_time | str | 期权行权日<br>(ℹ️ 格式：yyyy-MM-dd  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | option\_strike\_price | float | 行权价 |
        | option\_contract\_size | float | 每份合约数 |
        | option\_open\_interest | int | 总未平仓合约数 |
        | option\_implied\_volatility | float | 隐含波动率 |
        | option\_premium | float | 溢价  |
        | option\_delta | float | 希腊值 Delta |
        | option\_gamma | float | 希腊值 Gamma |
        | option\_vega | float | 希腊值 Vega |
        | option\_theta | float | 希腊值 Theta |
        | option\_rho | float | 希腊值 Rho |
        | index\_option\_type | [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149) | 指数期权类型 |
        | option\_net\_open\_interest | int | 净未平仓合约数<br>(ℹ️ 仅港股期权适用) |
        | option\_expiry\_date\_distance | int | 距离到期日天数<br>(ℹ️ 负数表示已过期) |
        | option\_contract\_nominal\_value | float | 合约名义金额<br>(ℹ️ 仅港股期权适用) |
        | option\_owner\_lot\_multiplier | float | 相等正股手数<br>(ℹ️ 指数期权无该字段，仅港股期权适用) |
        | option\_area\_type | [OptionAreaType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7077) | 期权类型（按行权时间） |
        | option\_contract\_multiplier | float | 合约乘数 |
        | plate\_valid | bool | 是否为板块类型<br>(ℹ️ 此字段返回为 True 时，以下板块相关字段才有合法数值) |
        | plate\_raise\_count | int | 板块类型上涨支数 |
        | plate\_fall\_count | int | 板块类型下跌支数 |
        | plate\_equal\_count | int | 板块类型平盘支数 |
        | index\_valid | bool | 是否有指数类型<br>(ℹ️ 此字段返回为 True 时，以下指数相关字段才有合法数值) |
        | index\_raise\_count | int | 指数类型上涨支数 |
        | index\_fall\_count | int | 指数类型下跌支数 |
        | index\_equal\_count | int | 指数类型平盘支数 |
        | lot\_size | int | 每手股数，股票期权表示每份合约的股数<br>(ℹ️ 指数期权无该字段<br><br>，期货表示合约乘数) |
        | price\_spread | float | 当前向上的摆盘价差<br>(ℹ️ 即摆盘数据的卖一价相邻档位的报价差) |
        | ask\_price | float | 卖价  |
        | bid\_price | float | 买价  |
        | ask\_vol | float | 卖量  |
        | bid\_vol | float | 买量  |
        | enable\_margin | bool | 是否可融资（已废弃）<br>(ℹ️ 请使用 [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)<br> 接口获取) |
        | mortgage\_ratio | float | 股票抵押率（已废弃） |
        | long\_margin\_initial\_ratio | float | 融资初始保证金率（已废弃）<br>(ℹ️ 请使用 [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)<br> 接口获取) |
        | enable\_short\_sell | bool | 是否可卖空（已废弃）<br>(ℹ️ 请使用 [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)<br> 接口获取) |
        | short\_sell\_rate | float | 卖空参考利率（已废弃）<br>(ℹ️ 请使用 [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)<br> 接口获取) |
        | short\_available\_volume | int | 剩余可卖空数量（已废弃）<br>(ℹ️ 请使用 [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)<br> 接口获取) |
        | short\_margin\_initial\_ratio | float | 卖空（融券）初始保证金率（已废弃）<br>(ℹ️ 请使用 [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)<br> 接口获取) |
        | sec\_status | [SecurityStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9969) | 股票状态 |
        | amplitude | float | 振幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | avg\_price | float | 平均价 |
        | bid\_ask\_ratio | float | 委比<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | volume\_ratio | float | 量比  |
        | highest52weeks\_price | float | 52 周最高价 |
        | lowest52weeks\_price | float | 52 周最低价 |
        | highest\_history\_price | float | 历史最高价 |
        | lowest\_history\_price | float | 历史最低价 |
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
        | after\_volume | int | 盘后成交量<br>(ℹ️ 科创板支持该数据) |
        | after\_turnover | float | 盘后成交额<br>(ℹ️ 科创板支持该数据) |
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
        | future\_valid | bool | 是否期货 |
        | future\_last\_settle\_price | float | 昨结  |
        | future\_position | float | 持仓量 |
        | future\_position\_change | float | 日增仓 |
        | future\_main\_contract | bool | 是否主连合约 |
        | future\_last\_trade\_time | str | 最后交易时间<br>(ℹ️ 主连，当月，下月等期货没有该字段) |
        | trust\_valid | bool | 是否基金 |
        | trust\_dividend\_yield | float | 股息率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | trust\_aum | float | 资产规模<br>(ℹ️ 单位：元) |
        | trust\_outstanding\_units | int | 总发行量 |
        | trust\_netAssetValue | float | 单位净值 |
        | trust\_premium | float | 溢价<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | trust\_assetClass | [AssetClass](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4752) | 资产类别 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_market_snapshot(['HK.00700', 'US.AAPL'])
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

    code  name              update_time  last_price  open_price  high_price  low_price  prev_close_price     volume      turnover  turnover_rate  suspension listing_date  lot_size  price_spread  stock_owner  ask_price  bid_price  ask_vol  bid_vol  enable_margin  mortgage_ratio  long_margin_initial_ratio  enable_short_sell  short_sell_rate  short_available_volume  short_margin_initial_ratio  amplitude  avg_price  bid_ask_ratio  volume_ratio  highest52weeks_price  lowest52weeks_price  highest_history_price  lowest_history_price  close_price_5min  after_volume  after_turnover sec_status  equity_valid  issued_shares  total_market_val     net_asset    net_profit  earning_per_share  outstanding_shares  circular_market_val  net_asset_per_share  ey_ratio  pe_ratio  pb_ratio  pe_ttm_ratio  dividend_ttm  dividend_ratio_ttm  dividend_lfy  dividend_lfy_ratio  wrt_valid  wrt_conversion_ratio wrt_type  wrt_strike_price  wrt_maturity_date  wrt_end_trade  wrt_recovery_price  wrt_street_vol  \
    0  HK.00700  腾讯控股      2025-04-07 16:09:07      435.40      441.80      462.40     431.00            497.80  123364114  5.499476e+10          1.341       False   2004-06-16       100          0.20          NaN      435.4     435.20   281300    17300            NaN             NaN                        NaN                NaN              NaN                     NaN                         NaN      6.308    445.792        -68.499         5.627             547.00000           294.400000             706.100065            -13.202011            431.60             0    0.000000e+00     NORMAL          True     9202391012      4.006721e+12  1.051300e+12  2.095753e+11             22.774          9202391012         4.006721e+12              114.242     0.199    19.118     3.811        19.118          3.48                0.80          3.48               0.799      False                   NaN      N/A               NaN                NaN            NaN                 NaN             NaN   
    1   US.AAPL    苹果  2025-04-07 05:30:43.301      188.38      193.89      199.88     187.34            203.19  125910913  2.424473e+10          0.838       False   1980-12-12         1          0.01          NaN      180.8     180.48       29      400            NaN             NaN                        NaN                NaN              NaN                     NaN                         NaN      6.172    192.554         86.480         2.226             259.81389           163.300566             259.813890              0.053580            188.93       3151311    5.930968e+08     NORMAL          True    15022073000      2.829858e+12  6.675809e+10  9.133420e+10              6.080         15016677308         2.828842e+12                4.444     1.417    30.983    42.389        29.901          0.99                0.53          0.98               0.520      False                   NaN      N/A               NaN                NaN            NaN                 NaN             NaN   
    
       wrt_issue_vol  wrt_street_ratio  wrt_delta  wrt_implied_volatility  wrt_premium  wrt_leverage  wrt_ipop  wrt_break_even_point  wrt_conversion_price  wrt_price_recovery_ratio  wrt_score  wrt_upper_strike_price  wrt_lower_strike_price wrt_inline_price_status  wrt_issuer_code  option_valid option_type  strike_time  option_strike_price  option_contract_size  option_open_interest  option_implied_volatility  option_premium  option_delta  option_gamma  option_vega  option_theta  option_rho  option_net_open_interest  option_expiry_date_distance  option_contract_nominal_value  option_owner_lot_multiplier option_area_type  option_contract_multiplier index_option_type  index_valid  index_raise_count  index_fall_count  index_equal_count  plate_valid  plate_raise_count  plate_fall_count  plate_equal_count  future_valid  future_last_settle_price  future_position  future_position_change  future_main_contract  future_last_trade_time  trust_valid  trust_dividend_yield  trust_aum  \
    0            NaN               NaN        NaN                     NaN          NaN           NaN       NaN                   NaN                   NaN                       NaN        NaN                     NaN                     NaN                     N/A              NaN         False         N/A          NaN                  NaN                   NaN                   NaN                        NaN             NaN           NaN           NaN          NaN           NaN         NaN                       NaN                          NaN                            NaN                          NaN              N/A                         NaN               N/A        False                NaN               NaN                NaN        False                NaN               NaN                NaN         False                       NaN              NaN                     NaN                   NaN                     NaN        False                   NaN        NaN   
    1            NaN               NaN        NaN                     NaN          NaN           NaN       NaN                   NaN                   NaN                       NaN        NaN                     NaN                     NaN                     N/A              NaN         False         N/A          NaN                  NaN                   NaN                   NaN                        NaN             NaN           NaN           NaN          NaN           NaN         NaN                       NaN                          NaN                            NaN                          NaN              N/A                         NaN               N/A        False                NaN               NaN                NaN        False                NaN               NaN                NaN         False                       NaN              NaN                     NaN                   NaN                     NaN        False                   NaN        NaN   
    
       trust_outstanding_units  trust_netAssetValue  trust_premium trust_assetClass pre_price pre_high_price pre_low_price pre_volume pre_turnover pre_change_val pre_change_rate pre_amplitude after_price after_high_price after_low_price after_change_val after_change_rate after_amplitude overnight_price overnight_high_price overnight_low_price overnight_volume overnight_turnover overnight_change_val overnight_change_rate overnight_amplitude  
    0                      NaN                  NaN            NaN              N/A       N/A            N/A           N/A        N/A          N/A            N/A             N/A           N/A         N/A              N/A             N/A              N/A               N/A             N/A             N/A                  N/A                 N/A              N/A                N/A                  N/A                   N/A                 N/A  
    1                      NaN                  NaN            NaN              N/A    180.68         181.98        177.47     276016  49809244.83           -7.7          -4.087         2.394       186.6          188.639          186.44            -1.78            -0.944          1.1673          176.94                186.5               174.4           533115        94944250.56               -11.44                -6.072              6.4231  
    HK.00700
    ['HK.00700', 'US.AAPL']
    
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-market-snapshot.html#338)
 Qot\_GetSecuritySnapshot.proto
---------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取快照数据
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

     // 正股类型额外数据
    message EquitySnapshotExData
    {
        required int64 issuedShares = 1; // 总股本
    	required double issuedMarketVal = 2; // 总市值 =总股本*当前价格
        required double netAsset = 3; // 资产净值
        required double netProfit = 4; // 盈利（亏损）
        required double earningsPershare = 5; // 每股盈利
        required int64 outstandingShares = 6; // 流通股本
    	required double outstandingMarketVal = 7; // 流通市值 =流通股本*当前价格
        required double netAssetPershare = 8; // 每股净资产
        required double eyRate = 9; // 收益率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double peRate = 10; // 市盈率
        required double pbRate = 11; // 市净率
    	required double peTTMRate = 12; // 市盈率 TTM
    	optional double dividendTTM = 13; // 股息 TTM，派息
        optional double dividendRatioTTM = 14; // 股息率 TTM（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double dividendLFY = 15; // 股息 LFY，上一年度派息
        optional double dividendLFYRatio = 16; // 股息率 LFY（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
     // 窝轮类型额外数据
    message WarrantSnapshotExData
    {
        required double conversionRate = 1; //换股比率
        required int32 warrantType = 2; //Qot_Common.WarrantType，窝轮类型
        required double strikePrice = 3; //行使价
        required string maturityTime = 4; //到期日时间字符串
        required string endTradeTime = 5; //最后交易日时间字符串
        required Qot_Common.Security owner = 6; //所属正股 
        required double recoveryPrice = 7; //收回价，仅牛熊证支持该字段
        required int64 streetVolumn = 8; //街货量
        required int64 issueVolumn = 9; //发行量
        required double streetRate = 10; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double delta = 11; //对冲值，仅认购认沽支持该字段
        required double impliedVolatility = 12; //引申波幅，仅认购认沽支持该字段
        required double premium = 13; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double maturityTimestamp = 14; //到期日时间戳
        optional double endTradeTimestamp = 15; //最后交易日时间戳
        optional double leverage = 16;  // 杠杆比率（倍）
        optional double ipop = 17; // 价内/价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double breakEvenPoint = 18; // 打和点
        optional double conversionPrice = 19;  // 换股价
        optional double priceRecoveryRatio = 20; // 正股距收回价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double score = 21; // 综合评分
        optional double upperStrikePrice = 22; //上限价，仅界内证支持该字段
        optional double lowerStrikePrice = 23; //下限价，仅界内证支持该字段
        optional int32 inLinePriceStatus = 24; //Qot_Common.PriceType，界内界外，仅界内证支持该字段
        optional string issuerCode = 25; //发行人代码
    }
    
     // 期权类型额外数据
    message OptionSnapshotExData
    {
    	required int32 type = 1; //Qot_Common.OptionType，期权类型（按方向）
    	required Qot_Common.Security owner = 2; //标的股
    	required string strikeTime = 3; //行权日时间字符串（格式：yyyy-MM-dd）
        required double strikePrice = 4; //行权价
    	required int32 contractSize = 5; //每份合约数(整型数据)
    	optional double contractSizeFloat = 22; //每份合约数（浮点型数据）
    	required int32 openInterest = 6; //未平仓合约数
    	required double impliedVolatility = 7; //隐含波动率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double premium = 8; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double delta = 9; //希腊值 Delta
        required double gamma = 10; //希腊值 Gamma
    	required double vega = 11; //希腊值 Vega
        required double theta = 12; //希腊值 Theta
        required double rho = 13; //希腊值 Rho
    	optional double strikeTimestamp = 14; //行权日时间戳  
    	optional int32 indexOptionType = 15; //Qot_Common.IndexOptionType，指数期权类型
    	optional int32 netOpenInterest = 16; //净未平仓合约数，仅港股期权适用	
    	optional int32 expiryDateDistance = 17; //距离到期日天数，负数表示已过期
    	optional double contractNominalValue = 18; //合约名义金额，仅港股期权适用
    	optional double ownerLotMultiplier = 19; //相等正股手数，指数期权无该字段，仅港股期权适用
    	optional int32 optionAreaType = 20; //Qot_Common.OptionAreaType，期权类型（按行权时间）
    	optional double contractMultiplier = 21; //合约乘数
    }
    
    // 指数类型额外数据
    message IndexSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    // 板块类型额外数据
    message PlateSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    //期货类型额外数据
    message FutureSnapshotExData
    {
    	required double lastSettlePrice = 1; //昨结
    	required int32 position = 2; //持仓量
    	required int32 positionChange = 3; //日增仓
    	required string lastTradeTime = 4; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 5; //最后交易日时间戳，只有非主连期货合约才有该字段
    	required bool isMainContract = 6; //是否主连合约
    }
    
    //基金类型额外数据
    message TrustSnapshotExData
    {
    	required double dividendYield = 1; //股息率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double aum = 2; //资产规模
    	required int64 outstandingUnits = 3; //总发行量
    	required double netAssetValue = 4; //单位净值
    	required double premium = 5; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int32 assetClass = 6; //Qot_Common.AssetClass，资产类别
    }
    
     //基本快照数据
    message SnapshotBasicData
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 41; //股票名称
    	required int32 type = 2; //Qot_Common.SecurityType，证券类型
    	required bool isSuspend = 3; //是否停牌
    	required string listTime = 4; //上市时间字符串（格式：yyyy-MM-dd）
    	required int32 lotSize = 5; //每手数量
    	required double priceSpread = 6; //价差
    	required string updateTime = 7; //更新时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	required double highPrice = 8; //最高价
    	required double openPrice = 9; //开盘价
    	required double lowPrice = 10; //最低价
    	required double lastClosePrice = 11; //昨收价
    	required double curPrice = 12; //最新价
    	required int64 volume = 13; //成交量
    	required double turnover = 14; //成交额
    	required double turnoverRate = 15; //换手率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double listTimestamp = 16; //上市时间戳
    	optional double updateTimestamp = 17; //更新时间戳
    	optional double askPrice = 18;//卖价
        optional double bidPrice = 19;//买价
    	optional int64 askVol = 20;//卖量
        optional int64 bidVol = 21;//买量
    	optional bool enableMargin = 22; // 是否可融资（已废弃，请使用 获取融资融券数据 接口获取）
        optional double mortgageRatio = 23; // 股票抵押率（已废弃，请使用 获取融资融券数据 接口获取）
        optional double longMarginInitialRatio = 24; // 融资初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
        optional bool enableShortSell = 25; // 是否可卖空（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortSellRate = 26; // 卖空参考利率（已废弃，请使用 获取融资融券数据 接口获取）
        optional int64 shortAvailableVolume = 27; // 剩余可卖空数量（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortMarginInitialRatio = 28; // 卖空（融券）初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
    	optional double amplitude = 29; // 振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double avgPrice = 30; // 平均价
    	optional double bidAskRatio = 31; // 委比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double volumeRatio = 32;  // 量比
    	optional double highest52WeeksPrice = 33;  // 52周最高价
        optional double lowest52WeeksPrice = 34;  // 52周最低价
    	optional double highestHistoryPrice = 35;  // 历史最高价
        optional double lowestHistoryPrice = 36;  // 历史最低价
    	optional Qot_Common.PreAfterMarketData preMarket = 37; //Qot_Common::PreAfterMarketData 盘前数据
    	optional Qot_Common.PreAfterMarketData afterMarket = 38; //Qot_Common::PreAfterMarketData 盘后数据
        optional Qot_Common.PreAfterMarketData overnight = 42; //Qot_Common::PreAfterMarketData 夜盘数据
    	optional int32 secStatus = 39; //Qot_Common::SecurityStatus 股票状态
    	optional double closePrice5Minute = 40; //5分钟收盘价
    }
    
    message Snapshot
    {
    	required SnapshotBasicData basic = 1; //快照基本数据
    	optional EquitySnapshotExData equityExData = 2; //正股快照额外数据
    	optional WarrantSnapshotExData warrantExData = 3; //窝轮快照额外数据
    	optional OptionSnapshotExData optionExData = 4; //期权快照额外数据
    	optional IndexSnapshotExData indexExData = 5; //指数快照额外数据
    	optional PlateSnapshotExData plateExData = 6; //板块快照额外数据
    	optional FutureSnapshotExData futureExData = 7; //期货类型额外数据
    	optional TrustSnapshotExData trustExData = 8; //基金类型额外数据
    }
    
    message S2C
    {
    	repeated Snapshot snapshotList = 1; //股票快照
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
141  
142  
143  
144  
145  
146  
147  
148  
149  
150  
151  
152  
153  
154  
155  
156  
157  
158  
159  
160  
161  
162  
163  
164  
165  
166  
167  
168  
169  
170  
171  
172  
173  
174  
175  
176  
177  
178  
179  
180  
181  
182  
183  
184  
185  
186  
187  
188  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3203
    

`uint GetSecuritySnapshot(QotGetSecuritySnapshot.Request req);`  
`virtual void OnReply_GetSecuritySnapshot(FTAPI_Conn client, uint nSerialNo, QotGetSecuritySnapshot.Response rsp);`

*   **介绍**
    
    获取快照数据
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

     // 正股类型额外数据
    message EquitySnapshotExData
    {
        required int64 issuedShares = 1; // 总股本
    	required double issuedMarketVal = 2; // 总市值 =总股本*当前价格
        required double netAsset = 3; // 资产净值
        required double netProfit = 4; // 盈利（亏损）
        required double earningsPershare = 5; // 每股盈利
        required int64 outstandingShares = 6; // 流通股本
    	required double outstandingMarketVal = 7; // 流通市值 =流通股本*当前价格
        required double netAssetPershare = 8; // 每股净资产
        required double eyRate = 9; // 收益率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double peRate = 10; // 市盈率
        required double pbRate = 11; // 市净率
    	required double peTTMRate = 12; // 市盈率 TTM
    	optional double dividendTTM = 13; // 股息 TTM，派息
        optional double dividendRatioTTM = 14; // 股息率 TTM（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double dividendLFY = 15; // 股息 LFY，上一年度派息
        optional double dividendLFYRatio = 16; // 股息率 LFY（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
     // 窝轮类型额外数据
    message WarrantSnapshotExData
    {
        required double conversionRate = 1; //换股比率
        required int32 warrantType = 2; //Qot_Common.WarrantType，窝轮类型
        required double strikePrice = 3; //行使价
        required string maturityTime = 4; //到期日时间字符串
        required string endTradeTime = 5; //最后交易日时间字符串
        required Qot_Common.Security owner = 6; //所属正股 
        required double recoveryPrice = 7; //收回价，仅牛熊证支持该字段
        required int64 streetVolumn = 8; //街货量
        required int64 issueVolumn = 9; //发行量
        required double streetRate = 10; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double delta = 11; //对冲值，仅认购认沽支持该字段
        required double impliedVolatility = 12; //引申波幅，仅认购认沽支持该字段
        required double premium = 13; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double maturityTimestamp = 14; //到期日时间戳
        optional double endTradeTimestamp = 15; //最后交易日时间戳
        optional double leverage = 16;  // 杠杆比率（倍）
        optional double ipop = 17; // 价内/价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double breakEvenPoint = 18; // 打和点
        optional double conversionPrice = 19;  // 换股价
        optional double priceRecoveryRatio = 20; // 正股距收回价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double score = 21; // 综合评分
        optional double upperStrikePrice = 22; //上限价，仅界内证支持该字段
        optional double lowerStrikePrice = 23; //下限价，仅界内证支持该字段
        optional int32 inLinePriceStatus = 24; //Qot_Common.PriceType，界内界外，仅界内证支持该字段
        optional string issuerCode = 25; //发行人代码
    }
    
     // 期权类型额外数据
    message OptionSnapshotExData
    {
    	required int32 type = 1; //Qot_Common.OptionType，期权类型（按方向）
    	required Qot_Common.Security owner = 2; //标的股
    	required string strikeTime = 3; //行权日时间字符串（格式：yyyy-MM-dd）
        required double strikePrice = 4; //行权价
    	required int32 contractSize = 5; //每份合约数(整型数据)
    	optional double contractSizeFloat = 22; //每份合约数（浮点型数据）
    	required int32 openInterest = 6; //未平仓合约数
    	required double impliedVolatility = 7; //隐含波动率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double premium = 8; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double delta = 9; //希腊值 Delta
        required double gamma = 10; //希腊值 Gamma
    	required double vega = 11; //希腊值 Vega
        required double theta = 12; //希腊值 Theta
        required double rho = 13; //希腊值 Rho
    	optional double strikeTimestamp = 14; //行权日时间戳  
    	optional int32 indexOptionType = 15; //Qot_Common.IndexOptionType，指数期权类型
    	optional int32 netOpenInterest = 16; //净未平仓合约数，仅港股期权适用	
    	optional int32 expiryDateDistance = 17; //距离到期日天数，负数表示已过期
    	optional double contractNominalValue = 18; //合约名义金额，仅港股期权适用
    	optional double ownerLotMultiplier = 19; //相等正股手数，指数期权无该字段，仅港股期权适用
    	optional int32 optionAreaType = 20; //Qot_Common.OptionAreaType，期权类型（按行权时间）
    	optional double contractMultiplier = 21; //合约乘数
    }
    
    // 指数类型额外数据
    message IndexSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    // 板块类型额外数据
    message PlateSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    //期货类型额外数据
    message FutureSnapshotExData
    {
    	required double lastSettlePrice = 1; //昨结
    	required int32 position = 2; //持仓量
    	required int32 positionChange = 3; //日增仓
    	required string lastTradeTime = 4; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 5; //最后交易日时间戳，只有非主连期货合约才有该字段
    	required bool isMainContract = 6; //是否主连合约
    }
    
    //基金类型额外数据
    message TrustSnapshotExData
    {
    	required double dividendYield = 1; //股息率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double aum = 2; //资产规模
    	required int64 outstandingUnits = 3; //总发行量
    	required double netAssetValue = 4; //单位净值
    	required double premium = 5; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int32 assetClass = 6; //Qot_Common.AssetClass，资产类别
    }
    
     //基本快照数据
    message SnapshotBasicData
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 41; //股票名称
    	required int32 type = 2; //Qot_Common.SecurityType，证券类型
    	required bool isSuspend = 3; //是否停牌
    	required string listTime = 4; //上市时间字符串（格式：yyyy-MM-dd）
    	required int32 lotSize = 5; //每手数量
    	required double priceSpread = 6; //价差
    	required string updateTime = 7; //更新时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	required double highPrice = 8; //最高价
    	required double openPrice = 9; //开盘价
    	required double lowPrice = 10; //最低价
    	required double lastClosePrice = 11; //昨收价
    	required double curPrice = 12; //最新价
    	required int64 volume = 13; //成交量
    	required double turnover = 14; //成交额
    	required double turnoverRate = 15; //换手率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double listTimestamp = 16; //上市时间戳
    	optional double updateTimestamp = 17; //更新时间戳
    	optional double askPrice = 18;//卖价
        optional double bidPrice = 19;//买价
    	optional int64 askVol = 20;//卖量
        optional int64 bidVol = 21;//买量
    	optional bool enableMargin = 22; // 是否可融资（已废弃，请使用 获取融资融券数据 接口获取）
        optional double mortgageRatio = 23; // 股票抵押率（已废弃，请使用 获取融资融券数据 接口获取）
        optional double longMarginInitialRatio = 24; // 融资初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
        optional bool enableShortSell = 25; // 是否可卖空（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortSellRate = 26; // 卖空参考利率（已废弃，请使用 获取融资融券数据 接口获取）
        optional int64 shortAvailableVolume = 27; // 剩余可卖空数量（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortMarginInitialRatio = 28; // 卖空（融券）初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
    	optional double amplitude = 29; // 振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double avgPrice = 30; // 平均价
    	optional double bidAskRatio = 31; // 委比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double volumeRatio = 32;  // 量比
    	optional double highest52WeeksPrice = 33;  // 52周最高价
        optional double lowest52WeeksPrice = 34;  // 52周最低价
    	optional double highestHistoryPrice = 35;  // 历史最高价
        optional double lowestHistoryPrice = 36;  // 历史最低价
    	optional Qot_Common.PreAfterMarketData preMarket = 37; //Qot_Common::PreAfterMarketData 盘前数据
    	optional Qot_Common.PreAfterMarketData afterMarket = 38; //Qot_Common::PreAfterMarketData 盘后数据
        optional Qot_Common.PreAfterMarketData overnight = 42; //Qot_Common::PreAfterMarketData 夜盘数据
    	optional int32 secStatus = 39; //Qot_Common::SecurityStatus 股票状态
    	optional double closePrice5Minute = 40; //5分钟收盘价
    }
    
    message Snapshot
    {
    	required SnapshotBasicData basic = 1; //快照基本数据
    	optional EquitySnapshotExData equityExData = 2; //正股快照额外数据
    	optional WarrantSnapshotExData warrantExData = 3; //窝轮快照额外数据
    	optional OptionSnapshotExData optionExData = 4; //期权快照额外数据
    	optional IndexSnapshotExData indexExData = 5; //指数快照额外数据
    	optional PlateSnapshotExData plateExData = 6; //板块快照额外数据
    	optional FutureSnapshotExData futureExData = 7; //期货类型额外数据
    	optional TrustSnapshotExData trustExData = 8; //基金类型额外数据
    }
    
    message S2C
    {
    	repeated Snapshot snapshotList = 1; //股票快照
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
141  
142  
143  
144  
145  
146  
147  
148  
149  
150  
151  
152  
153  
154  
155  
156  
157  
158  
159  
160  
161  
162  
163  
164  
165  
166  
167  
168  
169  
170  
171  
172  
173  
174  
175  
176  
177  
178  
179  
180  
181  
182  
183  
184  
185  
186  
187  
188  

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
    
            QotCommon.Security sec = QotCommon.Security.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetCode("00700")
                    .Build();
            QotGetSecuritySnapshot.C2S c2s = QotGetSecuritySnapshot.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                .Build();
            QotGetSecuritySnapshot.Request req = QotGetSecuritySnapshot.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetSecuritySnapshot(req);
            Console.Write("Send QotGetSecuritySnapshot: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetSecuritySnapshot(FTAPI_Conn client, uint nSerialNo, QotGetSecuritySnapshot.Response rsp)
        {
            Console.Write("Reply: QotGetSecuritySnapshot: {0} \n", nSerialNo);
            Console.Write("basic price: {0}\n", rsp.S2C.SnapshotListList[0].Basic.CurPrice);
            Console.Write("equityExData issuedShares: {0}\n", rsp.S2C.SnapshotListList[0].EquityExData.IssuedShares);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825619056039643630
    Send QotGetSecuritySnapshot: 3
    Reply: QotGetSecuritySnapshot: 3
    basic price: 474.2
    equityExData issuedShares: 9595900007
    

1  
2  
3  
4  
5  

`int getSecuritySnapshot(QotGetSecuritySnapshot.Request req);`  
`void onReply_GetSecuritySnapshot(FTAPI_Conn client, int nSerialNo, QotGetSecuritySnapshot.Response rsp);`

*   **介绍**
    
    获取快照数据
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

     // 正股类型额外数据
    message EquitySnapshotExData
    {
        required int64 issuedShares = 1; // 总股本
    	required double issuedMarketVal = 2; // 总市值 =总股本*当前价格
        required double netAsset = 3; // 资产净值
        required double netProfit = 4; // 盈利（亏损）
        required double earningsPershare = 5; // 每股盈利
        required int64 outstandingShares = 6; // 流通股本
    	required double outstandingMarketVal = 7; // 流通市值 =流通股本*当前价格
        required double netAssetPershare = 8; // 每股净资产
        required double eyRate = 9; // 收益率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double peRate = 10; // 市盈率
        required double pbRate = 11; // 市净率
    	required double peTTMRate = 12; // 市盈率 TTM
    	optional double dividendTTM = 13; // 股息 TTM，派息
        optional double dividendRatioTTM = 14; // 股息率 TTM（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double dividendLFY = 15; // 股息 LFY，上一年度派息
        optional double dividendLFYRatio = 16; // 股息率 LFY（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
     // 窝轮类型额外数据
    message WarrantSnapshotExData
    {
        required double conversionRate = 1; //换股比率
        required int32 warrantType = 2; //Qot_Common.WarrantType，窝轮类型
        required double strikePrice = 3; //行使价
        required string maturityTime = 4; //到期日时间字符串
        required string endTradeTime = 5; //最后交易日时间字符串
        required Qot_Common.Security owner = 6; //所属正股 
        required double recoveryPrice = 7; //收回价，仅牛熊证支持该字段
        required int64 streetVolumn = 8; //街货量
        required int64 issueVolumn = 9; //发行量
        required double streetRate = 10; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double delta = 11; //对冲值，仅认购认沽支持该字段
        required double impliedVolatility = 12; //引申波幅，仅认购认沽支持该字段
        required double premium = 13; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double maturityTimestamp = 14; //到期日时间戳
        optional double endTradeTimestamp = 15; //最后交易日时间戳
        optional double leverage = 16;  // 杠杆比率（倍）
        optional double ipop = 17; // 价内/价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double breakEvenPoint = 18; // 打和点
        optional double conversionPrice = 19;  // 换股价
        optional double priceRecoveryRatio = 20; // 正股距收回价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double score = 21; // 综合评分
        optional double upperStrikePrice = 22; //上限价，仅界内证支持该字段
        optional double lowerStrikePrice = 23; //下限价，仅界内证支持该字段
        optional int32 inLinePriceStatus = 24; //Qot_Common.PriceType，界内界外，仅界内证支持该字段
        optional string issuerCode = 25; //发行人代码
    }
    
     // 期权类型额外数据
    message OptionSnapshotExData
    {
    	required int32 type = 1; //Qot_Common.OptionType，期权类型（按方向）
    	required Qot_Common.Security owner = 2; //标的股
    	required string strikeTime = 3; //行权日时间字符串（格式：yyyy-MM-dd）
        required double strikePrice = 4; //行权价
    	required int32 contractSize = 5; //每份合约数(整型数据)
    	optional double contractSizeFloat = 22; //每份合约数（浮点型数据）
    	required int32 openInterest = 6; //未平仓合约数
    	required double impliedVolatility = 7; //隐含波动率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double premium = 8; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double delta = 9; //希腊值 Delta
        required double gamma = 10; //希腊值 Gamma
    	required double vega = 11; //希腊值 Vega
        required double theta = 12; //希腊值 Theta
        required double rho = 13; //希腊值 Rho
    	optional double strikeTimestamp = 14; //行权日时间戳  
    	optional int32 indexOptionType = 15; //Qot_Common.IndexOptionType，指数期权类型
    	optional int32 netOpenInterest = 16; //净未平仓合约数，仅港股期权适用	
    	optional int32 expiryDateDistance = 17; //距离到期日天数，负数表示已过期
    	optional double contractNominalValue = 18; //合约名义金额，仅港股期权适用
    	optional double ownerLotMultiplier = 19; //相等正股手数，指数期权无该字段，仅港股期权适用
    	optional int32 optionAreaType = 20; //Qot_Common.OptionAreaType，期权类型（按行权时间）
    	optional double contractMultiplier = 21; //合约乘数
    }
    
    // 指数类型额外数据
    message IndexSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    // 板块类型额外数据
    message PlateSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    //期货类型额外数据
    message FutureSnapshotExData
    {
    	required double lastSettlePrice = 1; //昨结
    	required int32 position = 2; //持仓量
    	required int32 positionChange = 3; //日增仓
    	required string lastTradeTime = 4; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 5; //最后交易日时间戳，只有非主连期货合约才有该字段
    	required bool isMainContract = 6; //是否主连合约
    }
    
    //基金类型额外数据
    message TrustSnapshotExData
    {
    	required double dividendYield = 1; //股息率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double aum = 2; //资产规模
    	required int64 outstandingUnits = 3; //总发行量
    	required double netAssetValue = 4; //单位净值
    	required double premium = 5; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int32 assetClass = 6; //Qot_Common.AssetClass，资产类别
    }
    
     //基本快照数据
    message SnapshotBasicData
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 41; //股票名称
    	required int32 type = 2; //Qot_Common.SecurityType，证券类型
    	required bool isSuspend = 3; //是否停牌
    	required string listTime = 4; //上市时间字符串（格式：yyyy-MM-dd）
    	required int32 lotSize = 5; //每手数量
    	required double priceSpread = 6; //价差
    	required string updateTime = 7; //更新时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	required double highPrice = 8; //最高价
    	required double openPrice = 9; //开盘价
    	required double lowPrice = 10; //最低价
    	required double lastClosePrice = 11; //昨收价
    	required double curPrice = 12; //最新价
    	required int64 volume = 13; //成交量
    	required double turnover = 14; //成交额
    	required double turnoverRate = 15; //换手率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double listTimestamp = 16; //上市时间戳
    	optional double updateTimestamp = 17; //更新时间戳
    	optional double askPrice = 18;//卖价
        optional double bidPrice = 19;//买价
    	optional int64 askVol = 20;//卖量
        optional int64 bidVol = 21;//买量
    	optional bool enableMargin = 22; // 是否可融资（已废弃，请使用 获取融资融券数据 接口获取）
        optional double mortgageRatio = 23; // 股票抵押率（已废弃，请使用 获取融资融券数据 接口获取）
        optional double longMarginInitialRatio = 24; // 融资初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
        optional bool enableShortSell = 25; // 是否可卖空（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortSellRate = 26; // 卖空参考利率（已废弃，请使用 获取融资融券数据 接口获取）
        optional int64 shortAvailableVolume = 27; // 剩余可卖空数量（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortMarginInitialRatio = 28; // 卖空（融券）初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
    	optional double amplitude = 29; // 振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double avgPrice = 30; // 平均价
    	optional double bidAskRatio = 31; // 委比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double volumeRatio = 32;  // 量比
    	optional double highest52WeeksPrice = 33;  // 52周最高价
        optional double lowest52WeeksPrice = 34;  // 52周最低价
    	optional double highestHistoryPrice = 35;  // 历史最高价
        optional double lowestHistoryPrice = 36;  // 历史最低价
    	optional Qot_Common.PreAfterMarketData preMarket = 37; //Qot_Common::PreAfterMarketData 盘前数据
    	optional Qot_Common.PreAfterMarketData afterMarket = 38; //Qot_Common::PreAfterMarketData 
        optional Qot_Common.PreAfterMarketData overnight = 42; //Qot_Common::PreAfterMarketData 夜盘数据
    	optional int32 secStatus = 39; //Qot_Common::SecurityStatus 股票状态
    	optional double closePrice5Minute = 40; //5分钟收盘价
    }
    
    message Snapshot
    {
    	required SnapshotBasicData basic = 1; //快照基本数据
    	optional EquitySnapshotExData equityExData = 2; //正股快照额外数据
    	optional WarrantSnapshotExData warrantExData = 3; //窝轮快照额外数据
    	optional OptionSnapshotExData optionExData = 4; //期权快照额外数据
    	optional IndexSnapshotExData indexExData = 5; //指数快照额外数据
    	optional PlateSnapshotExData plateExData = 6; //板块快照额外数据
    	optional FutureSnapshotExData futureExData = 7; //期货类型额外数据
    	optional TrustSnapshotExData trustExData = 8; //基金类型额外数据
    }
    
    message S2C
    {
    	repeated Snapshot snapshotList = 1; //股票快照
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
141  
142  
143  
144  
145  
146  
147  
148  
149  
150  
151  
152  
153  
154  
155  
156  
157  
158  
159  
160  
161  
162  
163  
164  
165  
166  
167  
168  
169  
170  
171  
172  
173  
174  
175  
176  
177  
178  
179  
180  
181  
182  
183  
184  
185  
186  
187  
188  

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
    
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .setCode("00700")
                    .build();
            QotGetSecuritySnapshot.C2S c2s = QotGetSecuritySnapshot.C2S.newBuilder()
                    .addSecurityList(sec)
                .build();
            QotGetSecuritySnapshot.Request req = QotGetSecuritySnapshot.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getSecuritySnapshot(req);
            System.out.printf("Send QotGetSecuritySnapshot: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetSecuritySnapshot(FTAPI_Conn client, int nSerialNo, QotGetSecuritySnapshot.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetSecuritySnapshot failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetSecuritySnapshot: %s\n", json);
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

*   **Output**

    Send QotGetSecuritySnapshot: 2
    Receive QotGetSecuritySnapshot: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "snapshotList": [{\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "00700"\
            },\
            "type": 3,\
            "isSuspend": false,\
            "listTime": "2004-06-16",\
            "lotSize": 100,\
            "priceSpread": 0.5,\
            "updateTime": "2021-06-24 16:08:14",\
            "highPrice": 587.5,\
            "openPrice": 584.0,\
            "lowPrice": 580.0,\
            "lastClosePrice": 582.5,\
            "curPrice": 583.0,\
            "volume": "10947302",\
            "turnover": 6.387238277E9,\
            "turnoverRate": 0.114,\
            "listTimestamp": 1.0873152E9,\
            "updateTimestamp": 1.624522094E9,\
            "askPrice": 583.5,\
            "bidPrice": 583.0,\
            "askVol": "142300",\
            "bidVol": "52800",\
            "enableMargin": true,\
            "mortgageRatio": 0.0,\
            "longMarginInitialRatio": 35.0,\
            "enableShortSell": true,\
            "shortSellRate": 0.9,\
            "shortAvailableVolume": "2006700",\
            "shortMarginInitialRatio": 60.0,\
            "amplitude": 1.288,\
            "avgPrice": 583.453,\
            "bidAskRatio": -5.273,\
            "volumeRatio": 0.553,\
            "highest52WeeksPrice": 773.9,\
            "lowest52WeeksPrice": 479.4,\
            "highestHistoryPrice": 773.9,\
            "lowestHistoryPrice": -6.381,\
            "secStatus": 1,\
            "closePrice5Minute": 583.5\
          },\
          "equityExData": {\
            "issuedShares": "9595206625",\
            "issuedMarketVal": 5.594005462375E12,\
            "netAsset": 9.17675966408375E11,\
            "netProfit": 1.90378494646625E11,\
            "earningsPershare": 19.841,\
            "outstandingShares": "9595206625",\
            "outstandingMarketVal": 5.594005462375E12,\
            "netAssetPershare": 95.639,\
            "eyRate": 0.232,\
            "peRate": 29.383,\
            "pbRate": 6.095,\
            "peTTMRate": 26.249,\
            "dividendTTM": 1.6,\
            "dividendRatioTTM": 0.27,\
            "dividendLFY": 1.6,\
            "dividendLFYRatio": 0.274\
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

`Futu::u32_t GetSecuritySnapshot(const Qot_GetSecuritySnapshot::Request &stReq);`  
`virtual void OnReply_GetSecuritySnapshot(Futu::u32_t nSerialNo, const Qot_GetSecuritySnapshot::Response &stRsp) = 0;`

*   **介绍**
    
    获取快照数据
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

     // 正股类型额外数据
    message EquitySnapshotExData
    {
        required int64 issuedShares = 1; // 总股本
    	required double issuedMarketVal = 2; // 总市值 =总股本*当前价格
        required double netAsset = 3; // 资产净值
        required double netProfit = 4; // 盈利（亏损）
        required double earningsPershare = 5; // 每股盈利
        required int64 outstandingShares = 6; // 流通股本
    	required double outstandingMarketVal = 7; // 流通市值 =流通股本*当前价格
        required double netAssetPershare = 8; // 每股净资产
        required double eyRate = 9; // 收益率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double peRate = 10; // 市盈率
        required double pbRate = 11; // 市净率
    	required double peTTMRate = 12; // 市盈率 TTM
    	optional double dividendTTM = 13; // 股息 TTM，派息
        optional double dividendRatioTTM = 14; // 股息率 TTM（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double dividendLFY = 15; // 股息 LFY，上一年度派息
        optional double dividendLFYRatio = 16; // 股息率 LFY（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
     // 窝轮类型额外数据
    message WarrantSnapshotExData
    {
        required double conversionRate = 1; //换股比率
        required int32 warrantType = 2; //Qot_Common.WarrantType，窝轮类型
        required double strikePrice = 3; //行使价
        required string maturityTime = 4; //到期日时间字符串
        required string endTradeTime = 5; //最后交易日时间字符串
        required Qot_Common.Security owner = 6; //所属正股 
        required double recoveryPrice = 7; //收回价，仅牛熊证支持该字段
        required int64 streetVolumn = 8; //街货量
        required int64 issueVolumn = 9; //发行量
        required double streetRate = 10; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double delta = 11; //对冲值，仅认购认沽支持该字段
        required double impliedVolatility = 12; //引申波幅，仅认购认沽支持该字段
        required double premium = 13; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double maturityTimestamp = 14; //到期日时间戳
        optional double endTradeTimestamp = 15; //最后交易日时间戳
        optional double leverage = 16;  // 杠杆比率（倍）
        optional double ipop = 17; // 价内/价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double breakEvenPoint = 18; // 打和点
        optional double conversionPrice = 19;  // 换股价
        optional double priceRecoveryRatio = 20; // 正股距收回价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double score = 21; // 综合评分
        optional double upperStrikePrice = 22; //上限价，仅界内证支持该字段
        optional double lowerStrikePrice = 23; //下限价，仅界内证支持该字段
        optional int32 inLinePriceStatus = 24; //Qot_Common.PriceType，界内界外，仅界内证支持该字段
        optional string issuerCode = 25; //发行人代码
    }
    
     // 期权类型额外数据
    message OptionSnapshotExData
    {
    	required int32 type = 1; //Qot_Common.OptionType，期权类型（按方向）
    	required Qot_Common.Security owner = 2; //标的股
    	required string strikeTime = 3; //行权日时间字符串（格式：yyyy-MM-dd）
        required double strikePrice = 4; //行权价
    	required int32 contractSize = 5; //每份合约数(整型数据)
    	optional double contractSizeFloat = 22; //每份合约数（浮点型数据）
    	required int32 openInterest = 6; //未平仓合约数
    	required double impliedVolatility = 7; //隐含波动率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double premium = 8; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double delta = 9; //希腊值 Delta
        required double gamma = 10; //希腊值 Gamma
    	required double vega = 11; //希腊值 Vega
        required double theta = 12; //希腊值 Theta
        required double rho = 13; //希腊值 Rho
    	optional double strikeTimestamp = 14; //行权日时间戳  
    	optional int32 indexOptionType = 15; //Qot_Common.IndexOptionType，指数期权类型
    	optional int32 netOpenInterest = 16; //净未平仓合约数，仅港股期权适用	
    	optional int32 expiryDateDistance = 17; //距离到期日天数，负数表示已过期
    	optional double contractNominalValue = 18; //合约名义金额，仅港股期权适用
    	optional double ownerLotMultiplier = 19; //相等正股手数，指数期权无该字段，仅港股期权适用
    	optional int32 optionAreaType = 20; //Qot_Common.OptionAreaType，期权类型（按行权时间）
    	optional double contractMultiplier = 21; //合约乘数
    }
    
    // 指数类型额外数据
    message IndexSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    // 板块类型额外数据
    message PlateSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    //期货类型额外数据
    message FutureSnapshotExData
    {
    	required double lastSettlePrice = 1; //昨结
    	required int32 position = 2; //持仓量
    	required int32 positionChange = 3; //日增仓
    	required string lastTradeTime = 4; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 5; //最后交易日时间戳，只有非主连期货合约才有该字段
    	required bool isMainContract = 6; //是否主连合约
    }
    
    //基金类型额外数据
    message TrustSnapshotExData
    {
    	required double dividendYield = 1; //股息率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double aum = 2; //资产规模
    	required int64 outstandingUnits = 3; //总发行量
    	required double netAssetValue = 4; //单位净值
    	required double premium = 5; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int32 assetClass = 6; //Qot_Common.AssetClass，资产类别
    }
    
     //基本快照数据
    message SnapshotBasicData
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 41; //股票名称
    	required int32 type = 2; //Qot_Common.SecurityType，证券类型
    	required bool isSuspend = 3; //是否停牌
    	required string listTime = 4; //上市时间字符串（格式：yyyy-MM-dd）
    	required int32 lotSize = 5; //每手数量
    	required double priceSpread = 6; //价差
    	required string updateTime = 7; //更新时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	required double highPrice = 8; //最高价
    	required double openPrice = 9; //开盘价
    	required double lowPrice = 10; //最低价
    	required double lastClosePrice = 11; //昨收价
    	required double curPrice = 12; //最新价
    	required int64 volume = 13; //成交量
    	required double turnover = 14; //成交额
    	required double turnoverRate = 15; //换手率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double listTimestamp = 16; //上市时间戳
    	optional double updateTimestamp = 17; //更新时间戳
    	optional double askPrice = 18;//卖价
        optional double bidPrice = 19;//买价
    	optional int64 askVol = 20;//卖量
        optional int64 bidVol = 21;//买量
    	optional bool enableMargin = 22; // 是否可融资（已废弃，请使用 获取融资融券数据 接口获取）
        optional double mortgageRatio = 23; // 股票抵押率（已废弃，请使用 获取融资融券数据 接口获取）
        optional double longMarginInitialRatio = 24; // 融资初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
        optional bool enableShortSell = 25; // 是否可卖空（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortSellRate = 26; // 卖空参考利率（已废弃，请使用 获取融资融券数据 接口获取）
        optional int64 shortAvailableVolume = 27; // 剩余可卖空数量（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortMarginInitialRatio = 28; // 卖空（融券）初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
    	optional double amplitude = 29; // 振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double avgPrice = 30; // 平均价
    	optional double bidAskRatio = 31; // 委比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double volumeRatio = 32;  // 量比
    	optional double highest52WeeksPrice = 33;  // 52周最高价
        optional double lowest52WeeksPrice = 34;  // 52周最低价
    	optional double highestHistoryPrice = 35;  // 历史最高价
        optional double lowestHistoryPrice = 36;  // 历史最低价
    	optional Qot_Common.PreAfterMarketData preMarket = 37; //Qot_Common::PreAfterMarketData 盘前数据
    	optional Qot_Common.PreAfterMarketData afterMarket = 38; //Qot_Common::PreAfterMarketData 
        optional Qot_Common.PreAfterMarketData overnight = 42; //Qot_Common::PreAfterMarketData 夜盘数据
    	optional int32 secStatus = 39; //Qot_Common::SecurityStatus 股票状态
    	optional double closePrice5Minute = 40; //5分钟收盘价
    }
    
    message Snapshot
    {
    	required SnapshotBasicData basic = 1; //快照基本数据
    	optional EquitySnapshotExData equityExData = 2; //正股快照额外数据
    	optional WarrantSnapshotExData warrantExData = 3; //窝轮快照额外数据
    	optional OptionSnapshotExData optionExData = 4; //期权快照额外数据
    	optional IndexSnapshotExData indexExData = 5; //指数快照额外数据
    	optional PlateSnapshotExData plateExData = 6; //板块快照额外数据
    	optional FutureSnapshotExData futureExData = 7; //期货类型额外数据
    	optional TrustSnapshotExData trustExData = 8; //基金类型额外数据
    }
    
    message S2C
    {
    	repeated Snapshot snapshotList = 1; //股票快照
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
141  
142  
143  
144  
145  
146  
147  
148  
149  
150  
151  
152  
153  
154  
155  
156  
157  
158  
159  
160  
161  
162  
163  
164  
165  
166  
167  
168  
169  
170  
171  
172  
173  
174  
175  
176  
177  
178  
179  
180  
181  
182  
183  
184  
185  
186  
187  
188  

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
    		Qot_GetSecuritySnapshot::Request req;
    		Qot_GetSecuritySnapshot::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetSecuritySnapshotSerialNo = m_pQotApi->GetSecuritySnapshot(req);
            cout << "Request GetSecuritySnapshot SerialNo: " << m_GetSecuritySnapshotSerialNo << endl;
    	}
    
    	virtual void OnReply_GetSecuritySnapshot(Futu::u32_t nSerialNo, const Qot_GetSecuritySnapshot::Response &stRsp){
            if(nSerialNo == m_GetSecuritySnapshotSerialNo)
            {
                cout << "OnReply_GetSecuritySnapshot SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetSecuritySnapshotSerialNo;
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
    Request GetSecuritySnapshot SerialNo: 4
    OnReply_GetSecuritySnapshot SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "snapshotList": [\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "00700"\
         },\
         "type": 3,\
         "isSuspend": false,\
         "listTime": "2004-06-16",\
         "lotSize": 100,\
         "priceSpread": 0.5,\
         "updateTime": "2021-06-09 11:01:40",\
         "highPrice": 605.5,\
         "openPrice": 600,\
         "lowPrice": 597.5,\
         "lastClosePrice": 601,\
         "curPrice": 604,\
         "volume": "2771395",\
         "turnover": 1668044020,\
         "turnoverRate": 0.029,\
         "listTimestamp": 1087315200,\
         "updateTimestamp": 1623207700,\
         "askPrice": 604.5,\
         "bidPrice": 604,\
         "askVol": "21500",\
         "bidVol": "9500",\
         "enableMargin": true,\
         "mortgageRatio": 0,\
         "longMarginInitialRatio": 35,\
         "enableShortSell": true,\
         "shortSellRate": 0.9,\
         "shortAvailableVolume": "1849300",\
         "shortMarginInitialRatio": 60,\
         "amplitude": 1.331,\
         "avgPrice": 601.878,\
         "bidAskRatio": -9.111,\
         "volumeRatio": 0.643,\
         "highest52WeeksPrice": 773.9,\
         "lowest52WeeksPrice": 430.6,\
         "highestHistoryPrice": 773.9,\
         "lowestHistoryPrice": -6.381,\
         "secStatus": 1,\
         "closePrice5Minute": 603.5\
        },\
        "equityExData": {\
         "issuedShares": "9595206625",\
         "issuedMarketVal": 5795504801500,\
         "netAsset": 917675966408.375,\
         "netProfit": 190378494646.625,\
         "earningsPershare": 19.841,\
         "outstandingShares": "9595206625",\
         "outstandingMarketVal": 5795504801500,\
         "netAssetPershare": 95.639,\
         "eyRate": 0.232,\
         "peRate": 30.442,\
         "pbRate": 6.315,\
         "peTTMRate": 27.194,\
         "dividendTTM": 1.6,\
         "dividendRatioTTM": 0.26,\
         "dividendLFY": 1.6,\
         "dividendLFYRatio": 0.264\
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

`GetSecuritySnapshot(req);`

*   **介绍**
    
    获取快照数据
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

     // 正股类型额外数据
    message EquitySnapshotExData
    {
        required int64 issuedShares = 1; // 总股本
    	required double issuedMarketVal = 2; // 总市值 =总股本*当前价格
        required double netAsset = 3; // 资产净值
        required double netProfit = 4; // 盈利（亏损）
        required double earningsPershare = 5; // 每股盈利
        required int64 outstandingShares = 6; // 流通股本
    	required double outstandingMarketVal = 7; // 流通市值 =流通股本*当前价格
        required double netAssetPershare = 8; // 每股净资产
        required double eyRate = 9; // 收益率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double peRate = 10; // 市盈率
        required double pbRate = 11; // 市净率
    	required double peTTMRate = 12; // 市盈率 TTM
    	optional double dividendTTM = 13; // 股息 TTM，派息
        optional double dividendRatioTTM = 14; // 股息率 TTM（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double dividendLFY = 15; // 股息 LFY，上一年度派息
        optional double dividendLFYRatio = 16; // 股息率 LFY（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
     // 窝轮类型额外数据
    message WarrantSnapshotExData
    {
        required double conversionRate = 1; //换股比率
        required int32 warrantType = 2; //Qot_Common.WarrantType，窝轮类型
        required double strikePrice = 3; //行使价
        required string maturityTime = 4; //到期日时间字符串
        required string endTradeTime = 5; //最后交易日时间字符串
        required Qot_Common.Security owner = 6; //所属正股 
        required double recoveryPrice = 7; //收回价，仅牛熊证支持该字段
        required int64 streetVolumn = 8; //街货量
        required int64 issueVolumn = 9; //发行量
        required double streetRate = 10; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double delta = 11; //对冲值，仅认购认沽支持该字段
        required double impliedVolatility = 12; //引申波幅，仅认购认沽支持该字段
        required double premium = 13; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double maturityTimestamp = 14; //到期日时间戳
        optional double endTradeTimestamp = 15; //最后交易日时间戳
        optional double leverage = 16;  // 杠杆比率（倍）
        optional double ipop = 17; // 价内/价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double breakEvenPoint = 18; // 打和点
        optional double conversionPrice = 19;  // 换股价
        optional double priceRecoveryRatio = 20; // 正股距收回价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double score = 21; // 综合评分
        optional double upperStrikePrice = 22; //上限价，仅界内证支持该字段
        optional double lowerStrikePrice = 23; //下限价，仅界内证支持该字段
        optional int32 inLinePriceStatus = 24; //Qot_Common.PriceType，界内界外，仅界内证支持该字段
        optional string issuerCode = 25; //发行人代码
    }
    
     // 期权类型额外数据
    message OptionSnapshotExData
    {
    	required int32 type = 1; //Qot_Common.OptionType，期权类型（按方向）
    	required Qot_Common.Security owner = 2; //标的股
    	required string strikeTime = 3; //行权日时间字符串（格式：yyyy-MM-dd）
        required double strikePrice = 4; //行权价
    	required int32 contractSize = 5; //每份合约数(整型数据)
    	optional double contractSizeFloat = 22; //每份合约数（浮点型数据）
    	required int32 openInterest = 6; //未平仓合约数
    	required double impliedVolatility = 7; //隐含波动率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double premium = 8; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double delta = 9; //希腊值 Delta
        required double gamma = 10; //希腊值 Gamma
    	required double vega = 11; //希腊值 Vega
        required double theta = 12; //希腊值 Theta
        required double rho = 13; //希腊值 Rho
    	optional double strikeTimestamp = 14; //行权日时间戳  
    	optional int32 indexOptionType = 15; //Qot_Common.IndexOptionType，指数期权类型
    	optional int32 netOpenInterest = 16; //净未平仓合约数，仅港股期权适用	
    	optional int32 expiryDateDistance = 17; //距离到期日天数，负数表示已过期
    	optional double contractNominalValue = 18; //合约名义金额，仅港股期权适用
    	optional double ownerLotMultiplier = 19; //相等正股手数，指数期权无该字段，仅港股期权适用
    	optional int32 optionAreaType = 20; //Qot_Common.OptionAreaType，期权类型（按行权时间）
    	optional double contractMultiplier = 21; //合约乘数
    }
    
    // 指数类型额外数据
    message IndexSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    // 板块类型额外数据
    message PlateSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    //期货类型额外数据
    message FutureSnapshotExData
    {
    	required double lastSettlePrice = 1; //昨结
    	required int32 position = 2; //持仓量
    	required int32 positionChange = 3; //日增仓
    	required string lastTradeTime = 4; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 5; //最后交易日时间戳，只有非主连期货合约才有该字段
    	required bool isMainContract = 6; //是否主连合约
    }
    
    //基金类型额外数据
    message TrustSnapshotExData
    {
    	required double dividendYield = 1; //股息率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double aum = 2; //资产规模
    	required int64 outstandingUnits = 3; //总发行量
    	required double netAssetValue = 4; //单位净值
    	required double premium = 5; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int32 assetClass = 6; //Qot_Common.AssetClass，资产类别
    }
    
     //基本快照数据
    message SnapshotBasicData
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 41; //股票名称
    	required int32 type = 2; //Qot_Common.SecurityType，证券类型
    	required bool isSuspend = 3; //是否停牌
    	required string listTime = 4; //上市时间字符串（格式：yyyy-MM-dd）
    	required int32 lotSize = 5; //每手数量
    	required double priceSpread = 6; //价差
    	required string updateTime = 7; //更新时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	required double highPrice = 8; //最高价
    	required double openPrice = 9; //开盘价
    	required double lowPrice = 10; //最低价
    	required double lastClosePrice = 11; //昨收价
    	required double curPrice = 12; //最新价
    	required int64 volume = 13; //成交量
    	required double turnover = 14; //成交额
    	required double turnoverRate = 15; //换手率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double listTimestamp = 16; //上市时间戳
    	optional double updateTimestamp = 17; //更新时间戳
    	optional double askPrice = 18;//卖价
        optional double bidPrice = 19;//买价
    	optional int64 askVol = 20;//卖量
        optional int64 bidVol = 21;//买量
    	optional bool enableMargin = 22; // 是否可融资（已废弃，请使用 获取融资融券数据 接口获取）
        optional double mortgageRatio = 23; // 股票抵押率（已废弃，请使用 获取融资融券数据 接口获取）
        optional double longMarginInitialRatio = 24; // 融资初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
        optional bool enableShortSell = 25; // 是否可卖空（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortSellRate = 26; // 卖空参考利率（已废弃，请使用 获取融资融券数据 接口获取）
        optional int64 shortAvailableVolume = 27; // 剩余可卖空数量（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortMarginInitialRatio = 28; // 卖空（融券）初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
    	optional double amplitude = 29; // 振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double avgPrice = 30; // 平均价
    	optional double bidAskRatio = 31; // 委比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double volumeRatio = 32;  // 量比
    	optional double highest52WeeksPrice = 33;  // 52周最高价
        optional double lowest52WeeksPrice = 34;  // 52周最低价
    	optional double highestHistoryPrice = 35;  // 历史最高价
        optional double lowestHistoryPrice = 36;  // 历史最低价
    	optional Qot_Common.PreAfterMarketData preMarket = 37; //Qot_Common::PreAfterMarketData 盘前数据
    	optional Qot_Common.PreAfterMarketData afterMarket = 38; //Qot_Common::PreAfterMarketData 
        optional Qot_Common.PreAfterMarketData overnight = 42; //Qot_Common::PreAfterMarketData 夜盘数据
    	optional int32 secStatus = 39; //Qot_Common::SecurityStatus 股票状态
    	optional double closePrice5Minute = 40; //5分钟收盘价
    }
    
    message Snapshot
    {
    	required SnapshotBasicData basic = 1; //快照基本数据
    	optional EquitySnapshotExData equityExData = 2; //正股快照额外数据
    	optional WarrantSnapshotExData warrantExData = 3; //窝轮快照额外数据
    	optional OptionSnapshotExData optionExData = 4; //期权快照额外数据
    	optional IndexSnapshotExData indexExData = 5; //指数快照额外数据
    	optional PlateSnapshotExData plateExData = 6; //板块快照额外数据
    	optional FutureSnapshotExData futureExData = 7; //期货类型额外数据
    	optional TrustSnapshotExData trustExData = 8; //基金类型额外数据
    }
    
    message S2C
    {
    	repeated Snapshot snapshotList = 1; //股票快照
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
141  
142  
143  
144  
145  
146  
147  
148  
149  
150  
151  
152  
153  
154  
155  
156  
157  
158  
159  
160  
161  
162  
163  
164  
165  
166  
167  
168  
169  
170  
171  
172  
173  
174  
175  
176  
177  
178  
179  
180  
181  
182  
183  
184  
185  
186  
187  
188  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetSecuritySnapshot(){
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
                    },
                };
                websocket.GetSecuritySnapshot(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("Snapshot: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                    if(retType == RetType.RetType_Succeed){
                        let snapshot = beautify(JSON.stringify(s2c), {
                            indent_size: 2,
                            space_in_empty_paren: true,
                        });
                        console.log(snapshot);
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

*   **Output**

    Snapshot: errCode 0, retMsg , retType 0
    {
      "snapshotList": [{\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "type": 3,\
          "isSuspend": false,\
          "listTime": "2004-06-16",\
          "lotSize": 100,\
          "priceSpread": 0.2,\
          "updateTime": "2021-09-09 16:08:17",\
          "highPrice": 511.5,\
          "openPrice": 509,\
          "lowPrice": 479,\
          "lastClosePrice": 524.5,\
          "curPrice": 480,\
          "volume": "54090872",\
          "turnover": 26716845932,\
          "turnoverRate": 0.563,\
          "listTimestamp": 1087315200,\
          "updateTimestamp": 1631174897,\
          "askPrice": 480.4,\
          "bidPrice": 480,\
          "askVol": "700",\
          "bidVol": "55300",\
          "enableMargin": true,\
          "mortgageRatio": 0,\
          "longMarginInitialRatio": 35,\
          "enableShortSell": true,\
          "shortSellRate": 0.92,\
          "shortAvailableVolume": "3059000",\
          "shortMarginInitialRatio": 50,\
          "amplitude": 6.196,\
          "avgPrice": 493.925,\
          "bidAskRatio": 22.703,\
          "volumeRatio": 1.775,\
          "highest52WeeksPrice": 773.9,\
          "lowest52WeeksPrice": 412.2,\
          "highestHistoryPrice": 773.9,\
          "lowestHistoryPrice": -6.381,\
          "secStatus": 1,\
          "closePrice5Minute": 483.4\
        },\
        "equityExData": {\
          "issuedShares": "9599810645",\
          "issuedMarketVal": 4607909109600,\
          "netAsset": 1016063158288.09,\
          "netProfit": 190383444711.64,\
          "earningsPershare": 19.832,\
          "outstandingShares": "9599810645",\
          "outstandingMarketVal": 4607909109600,\
          "netAssetPershare": 105.842,\
          "eyRate": 0.22,\
          "peRate": 24.203,\
          "pbRate": 4.535,\
          "peTTMRate": 20.532,\
          "dividendTTM": 1.599,\
          "dividendRatioTTM": 0.33,\
          "dividendLFY": 1.599,\
          "dividendLFYRatio": 0.333\
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

接口限制

*   每 30 秒内最多请求 60 次快照。
*   每次请求，接口参数 **股票代码列表** 支持传入的标的数量上限是 400 个。
*   港股 BMP 权限下，单次请求的香港证券（含窝轮、牛熊、界内证）快照数量上限是 20 个。
*   港股期权期货 BMP 权限下，单次请求的香港期货和期权的快照数量上限是 20 个。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_market_snapshot(code_list)`

*   **介绍**
    
    获取快照数据
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | code\_list | list | 股票代码列表<br>(ℹ️ 每次最多可请求 400 个标的  <br>list 内元素类型为 str) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回股票快照数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   股票快照数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 股票代码 |
        | name | str | 股票名称 |
        | update\_time | str | 当前价更新时间<br>(ℹ️ 格式：yyyy-MM-dd HH:mm:ss  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | last\_price | float | 最新价格 |
        | open\_price | float | 今日开盘价 |
        | high\_price | float | 最高价格 |
        | low\_price | float | 最低价格 |
        | prev\_close\_price | float | 昨收盘价格 |
        | volume | int | 成交数量 |
        | turnover | float | 成交金额 |
        | turnover\_rate | float | 换手率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | suspension | bool | 是否停牌<br>(ℹ️ True：停牌) |
        | listing\_date | str | 上市日期<br>(ℹ️ 格式：yyyy-MM-dd) |
        | equity\_valid | bool | 是否正股<br>(ℹ️ 此字段返回为 True 时，以下正股相关字段才有合法数值) |
        | issued\_shares | int | 总股本 |
        | total\_market\_val | float | 总市值<br>(ℹ️ 单位：元) |
        | net\_asset | int | 资产净值 |
        | net\_profit | int | 净利润 |
        | earning\_per\_share | float | 每股盈利 |
        | outstanding\_shares | int | 流通股本 |
        | net\_asset\_per\_share | float | 每股净资产 |
        | circular\_market\_val | float | 流通市值<br>(ℹ️ 单位：元) |
        | ey\_ratio | float | 收益率<br>(ℹ️ 该字段为比例字段，默认不展示 %) |
        | pe\_ratio | float | 市盈率<br>(ℹ️ 该字段为比例字段，默认不展示 %) |
        | pb\_ratio | float | 市净率<br>(ℹ️ 该字段为比例字段，默认不展示 %) |
        | pe\_ttm\_ratio | float | 市盈率 TTM<br>(ℹ️ 该字段为比例字段，默认不展示 %) |
        | dividend\_ttm | float | 股息 TTM，派息 |
        | dividend\_ratio\_ttm | float | 股息率 TTM<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | dividend\_lfy | float | 股息 LFY，上一年度派息 |
        | dividend\_lfy\_ratio | float | 股息率 LFY<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | stock\_owner | str | 窝轮所属正股的代码或期权的标的股代码 |
        | wrt\_valid | bool | 是否是窝轮<br>(ℹ️ 此字段返回为 True 时，以下窝轮相关字段才有合法数值) |
        | wrt\_conversion\_ratio | float | 换股比率 |
        | wrt\_type | [WrtType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#926) | 窝轮类型 |
        | wrt\_strike\_price | float | 行使价格 |
        | wrt\_maturity\_date | str | 格式化窝轮到期时间 |
        | wrt\_end\_trade | str | 格式化窝轮最后交易时间 |
        | wrt\_leverage | float | 杠杆比率<br>(ℹ️ 单位：倍) |
        | wrt\_ipop | float | 价内/价外<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | wrt\_break\_even\_point | float | 打和点 |
        | wrt\_conversion\_price | float | 换股价 |
        | wrt\_price\_recovery\_ratio | float | 正股距收回价<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | wrt\_score | float | 窝轮综合评分 |
        | wrt\_code | str | 窝轮对应的正股（此字段已废除，修改为 stock\_owner） |
        | wrt\_recovery\_price | float | 窝轮收回价 |
        | wrt\_street\_vol | float | 窝轮街货量 |
        | wrt\_issue\_vol | float | 窝轮发行量 |
        | wrt\_street\_ratio | float | 窝轮街货占比<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | wrt\_delta | float | 窝轮对冲值 |
        | wrt\_implied\_volatility | float | 窝轮引伸波幅 |
        | wrt\_premium | float | 窝轮溢价<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | wrt\_upper\_strike\_price | float | 上限价<br>(ℹ️ 仅界内证支持该字段) |
        | wrt\_lower\_strike\_price | float | 下限价<br>(ℹ️ 仅界内证支持该字段) |
        | wrt\_inline\_price\_status | [PriceType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#6407) | 界内界外<br>(ℹ️ 仅界内证支持该字段) |
        | wrt\_issuer\_code | str | 发行人代码 |
        | option\_valid | bool | 是否是期权<br>(ℹ️ 此字段返回为 True 时，以下期权相关字段才有合法数值) |
        | option\_type | [OptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3713) | 期权类型 |
        | strike\_time | str | 期权行权日<br>(ℹ️ 格式：yyyy-MM-dd  <br>港股和 A 股市场默认是北京时间，美股市场默认是美东时间) |
        | option\_strike\_price | float | 行权价 |
        | option\_contract\_size | float | 每份合约数 |
        | option\_open\_interest | int | 总未平仓合约数 |
        | option\_implied\_volatility | float | 隐含波动率 |
        | option\_premium | float | 溢价  |
        | option\_delta | float | 希腊值 Delta |
        | option\_gamma | float | 希腊值 Gamma |
        | option\_vega | float | 希腊值 Vega |
        | option\_theta | float | 希腊值 Theta |
        | option\_rho | float | 希腊值 Rho |
        | index\_option\_type | [IndexOptionType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#5149) | 指数期权类型 |
        | option\_net\_open\_interest | int | 净未平仓合约数<br>(ℹ️ 仅港股期权适用) |
        | option\_expiry\_date\_distance | int | 距离到期日天数<br>(ℹ️ 负数表示已过期) |
        | option\_contract\_nominal\_value | float | 合约名义金额<br>(ℹ️ 仅港股期权适用) |
        | option\_owner\_lot\_multiplier | float | 相等正股手数<br>(ℹ️ 指数期权无该字段，仅港股期权适用) |
        | option\_area\_type | [OptionAreaType](https://openapi.futunn.com/futu-api-doc/quote/quote.html#7077) | 期权类型（按行权时间） |
        | option\_contract\_multiplier | float | 合约乘数 |
        | plate\_valid | bool | 是否为板块类型<br>(ℹ️ 此字段返回为 True 时，以下板块相关字段才有合法数值) |
        | plate\_raise\_count | int | 板块类型上涨支数 |
        | plate\_fall\_count | int | 板块类型下跌支数 |
        | plate\_equal\_count | int | 板块类型平盘支数 |
        | index\_valid | bool | 是否有指数类型<br>(ℹ️ 此字段返回为 True 时，以下指数相关字段才有合法数值) |
        | index\_raise\_count | int | 指数类型上涨支数 |
        | index\_fall\_count | int | 指数类型下跌支数 |
        | index\_equal\_count | int | 指数类型平盘支数 |
        | lot\_size | int | 每手股数，股票期权表示每份合约的股数<br>(ℹ️ 指数期权无该字段<br><br>，期货表示合约乘数) |
        | price\_spread | float | 当前向上的摆盘价差<br>(ℹ️ 即摆盘数据的卖一价相邻档位的报价差) |
        | ask\_price | float | 卖价  |
        | bid\_price | float | 买价  |
        | ask\_vol | float | 卖量  |
        | bid\_vol | float | 买量  |
        | enable\_margin | bool | 是否可融资（已废弃）<br>(ℹ️ 请使用 [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)<br> 接口获取) |
        | mortgage\_ratio | float | 股票抵押率（已废弃） |
        | long\_margin\_initial\_ratio | float | 融资初始保证金率（已废弃）<br>(ℹ️ 请使用 [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)<br> 接口获取) |
        | enable\_short\_sell | bool | 是否可卖空（已废弃）<br>(ℹ️ 请使用 [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)<br> 接口获取) |
        | short\_sell\_rate | float | 卖空参考利率（已废弃）<br>(ℹ️ 请使用 [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)<br> 接口获取) |
        | short\_available\_volume | int | 剩余可卖空数量（已废弃）<br>(ℹ️ 请使用 [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)<br> 接口获取) |
        | short\_margin\_initial\_ratio | float | 卖空（融券）初始保证金率（已废弃）<br>(ℹ️ 请使用 [获取融资融券数据](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html)<br> 接口获取) |
        | sec\_status | [SecurityStatus](https://openapi.futunn.com/futu-api-doc/quote/quote.html#9969) | 股票状态 |
        | amplitude | float | 振幅<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | avg\_price | float | 平均价 |
        | bid\_ask\_ratio | float | 委比<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | volume\_ratio | float | 量比  |
        | highest52weeks\_price | float | 52 周最高价 |
        | lowest52weeks\_price | float | 52 周最低价 |
        | highest\_history\_price | float | 历史最高价 |
        | lowest\_history\_price | float | 历史最低价 |
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
        | after\_volume | int | 盘后成交量<br>(ℹ️ 科创板支持该数据) |
        | after\_turnover | float | 盘后成交额<br>(ℹ️ 科创板支持该数据) |
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
        | future\_valid | bool | 是否期货 |
        | future\_last\_settle\_price | float | 昨结  |
        | future\_position | float | 持仓量 |
        | future\_position\_change | float | 日增仓 |
        | future\_main\_contract | bool | 是否主连合约 |
        | future\_last\_trade\_time | str | 最后交易时间<br>(ℹ️ 主连，当月，下月等期货没有该字段) |
        | trust\_valid | bool | 是否基金 |
        | trust\_dividend\_yield | float | 股息率<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | trust\_aum | float | 资产规模<br>(ℹ️ 单位：元) |
        | trust\_outstanding\_units | int | 总发行量 |
        | trust\_netAssetValue | float | 单位净值 |
        | trust\_premium | float | 溢价<br>(ℹ️ 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%) |
        | trust\_assetClass | [AssetClass](https://openapi.futunn.com/futu-api-doc/quote/quote.html#4752) | 资产类别 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_market_snapshot(['HK.00700', 'US.AAPL'])
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

    code  name              update_time  last_price  open_price  high_price  low_price  prev_close_price     volume      turnover  turnover_rate  suspension listing_date  lot_size  price_spread  stock_owner  ask_price  bid_price  ask_vol  bid_vol  enable_margin  mortgage_ratio  long_margin_initial_ratio  enable_short_sell  short_sell_rate  short_available_volume  short_margin_initial_ratio  amplitude  avg_price  bid_ask_ratio  volume_ratio  highest52weeks_price  lowest52weeks_price  highest_history_price  lowest_history_price  close_price_5min  after_volume  after_turnover sec_status  equity_valid  issued_shares  total_market_val     net_asset    net_profit  earning_per_share  outstanding_shares  circular_market_val  net_asset_per_share  ey_ratio  pe_ratio  pb_ratio  pe_ttm_ratio  dividend_ttm  dividend_ratio_ttm  dividend_lfy  dividend_lfy_ratio  wrt_valid  wrt_conversion_ratio wrt_type  wrt_strike_price  wrt_maturity_date  wrt_end_trade  wrt_recovery_price  wrt_street_vol  \
    0  HK.00700  腾讯控股      2025-04-07 16:09:07      435.40      441.80      462.40     431.00            497.80  123364114  5.499476e+10          1.341       False   2004-06-16       100          0.20          NaN      435.4     435.20   281300    17300            NaN             NaN                        NaN                NaN              NaN                     NaN                         NaN      6.308    445.792        -68.499         5.627             547.00000           294.400000             706.100065            -13.202011            431.60             0    0.000000e+00     NORMAL          True     9202391012      4.006721e+12  1.051300e+12  2.095753e+11             22.774          9202391012         4.006721e+12              114.242     0.199    19.118     3.811        19.118          3.48                0.80          3.48               0.799      False                   NaN      N/A               NaN                NaN            NaN                 NaN             NaN   
    1   US.AAPL    苹果  2025-04-07 05:30:43.301      188.38      193.89      199.88     187.34            203.19  125910913  2.424473e+10          0.838       False   1980-12-12         1          0.01          NaN      180.8     180.48       29      400            NaN             NaN                        NaN                NaN              NaN                     NaN                         NaN      6.172    192.554         86.480         2.226             259.81389           163.300566             259.813890              0.053580            188.93       3151311    5.930968e+08     NORMAL          True    15022073000      2.829858e+12  6.675809e+10  9.133420e+10              6.080         15016677308         2.828842e+12                4.444     1.417    30.983    42.389        29.901          0.99                0.53          0.98               0.520      False                   NaN      N/A               NaN                NaN            NaN                 NaN             NaN   
    
       wrt_issue_vol  wrt_street_ratio  wrt_delta  wrt_implied_volatility  wrt_premium  wrt_leverage  wrt_ipop  wrt_break_even_point  wrt_conversion_price  wrt_price_recovery_ratio  wrt_score  wrt_upper_strike_price  wrt_lower_strike_price wrt_inline_price_status  wrt_issuer_code  option_valid option_type  strike_time  option_strike_price  option_contract_size  option_open_interest  option_implied_volatility  option_premium  option_delta  option_gamma  option_vega  option_theta  option_rho  option_net_open_interest  option_expiry_date_distance  option_contract_nominal_value  option_owner_lot_multiplier option_area_type  option_contract_multiplier index_option_type  index_valid  index_raise_count  index_fall_count  index_equal_count  plate_valid  plate_raise_count  plate_fall_count  plate_equal_count  future_valid  future_last_settle_price  future_position  future_position_change  future_main_contract  future_last_trade_time  trust_valid  trust_dividend_yield  trust_aum  \
    0            NaN               NaN        NaN                     NaN          NaN           NaN       NaN                   NaN                   NaN                       NaN        NaN                     NaN                     NaN                     N/A              NaN         False         N/A          NaN                  NaN                   NaN                   NaN                        NaN             NaN           NaN           NaN          NaN           NaN         NaN                       NaN                          NaN                            NaN                          NaN              N/A                         NaN               N/A        False                NaN               NaN                NaN        False                NaN               NaN                NaN         False                       NaN              NaN                     NaN                   NaN                     NaN        False                   NaN        NaN   
    1            NaN               NaN        NaN                     NaN          NaN           NaN       NaN                   NaN                   NaN                       NaN        NaN                     NaN                     NaN                     N/A              NaN         False         N/A          NaN                  NaN                   NaN                   NaN                        NaN             NaN           NaN           NaN          NaN           NaN         NaN                       NaN                          NaN                            NaN                          NaN              N/A                         NaN               N/A        False                NaN               NaN                NaN        False                NaN               NaN                NaN         False                       NaN              NaN                     NaN                   NaN                     NaN        False                   NaN        NaN   
    
       trust_outstanding_units  trust_netAssetValue  trust_premium trust_assetClass pre_price pre_high_price pre_low_price pre_volume pre_turnover pre_change_val pre_change_rate pre_amplitude after_price after_high_price after_low_price after_change_val after_change_rate after_amplitude overnight_price overnight_high_price overnight_low_price overnight_volume overnight_turnover overnight_change_val overnight_change_rate overnight_amplitude  
    0                      NaN                  NaN            NaN              N/A       N/A            N/A           N/A        N/A          N/A            N/A             N/A           N/A         N/A              N/A             N/A              N/A               N/A             N/A             N/A                  N/A                 N/A              N/A                N/A                  N/A                   N/A                 N/A  
    1                      NaN                  NaN            NaN              N/A    180.68         181.98        177.47     276016  49809244.83           -7.7          -4.087         2.394       186.6          188.639          186.44            -1.78            -0.944          1.1673          176.94                186.5               174.4           533115        94944250.56               -11.44                -6.072              6.4231  
    HK.00700
    ['HK.00700', 'US.AAPL']
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  

[#](https://openapi.futunn.com/futu-api-doc/quote/get-market-snapshot.html#338-2)
 Qot\_GetSecuritySnapshot.proto
-----------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取快照数据
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

     // 正股类型额外数据
    message EquitySnapshotExData
    {
        required int64 issuedShares = 1; // 总股本
    	required double issuedMarketVal = 2; // 总市值 =总股本*当前价格
        required double netAsset = 3; // 资产净值
        required double netProfit = 4; // 盈利（亏损）
        required double earningsPershare = 5; // 每股盈利
        required int64 outstandingShares = 6; // 流通股本
    	required double outstandingMarketVal = 7; // 流通市值 =流通股本*当前价格
        required double netAssetPershare = 8; // 每股净资产
        required double eyRate = 9; // 收益率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double peRate = 10; // 市盈率
        required double pbRate = 11; // 市净率
    	required double peTTMRate = 12; // 市盈率 TTM
    	optional double dividendTTM = 13; // 股息 TTM，派息
        optional double dividendRatioTTM = 14; // 股息率 TTM（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double dividendLFY = 15; // 股息 LFY，上一年度派息
        optional double dividendLFYRatio = 16; // 股息率 LFY（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
     // 窝轮类型额外数据
    message WarrantSnapshotExData
    {
        required double conversionRate = 1; //换股比率
        required int32 warrantType = 2; //Qot_Common.WarrantType，窝轮类型
        required double strikePrice = 3; //行使价
        required string maturityTime = 4; //到期日时间字符串
        required string endTradeTime = 5; //最后交易日时间字符串
        required Qot_Common.Security owner = 6; //所属正股 
        required double recoveryPrice = 7; //收回价，仅牛熊证支持该字段
        required int64 streetVolumn = 8; //街货量
        required int64 issueVolumn = 9; //发行量
        required double streetRate = 10; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double delta = 11; //对冲值，仅认购认沽支持该字段
        required double impliedVolatility = 12; //引申波幅，仅认购认沽支持该字段
        required double premium = 13; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double maturityTimestamp = 14; //到期日时间戳
        optional double endTradeTimestamp = 15; //最后交易日时间戳
        optional double leverage = 16;  // 杠杆比率（倍）
        optional double ipop = 17; // 价内/价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double breakEvenPoint = 18; // 打和点
        optional double conversionPrice = 19;  // 换股价
        optional double priceRecoveryRatio = 20; // 正股距收回价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double score = 21; // 综合评分
        optional double upperStrikePrice = 22; //上限价，仅界内证支持该字段
        optional double lowerStrikePrice = 23; //下限价，仅界内证支持该字段
        optional int32 inLinePriceStatus = 24; //Qot_Common.PriceType，界内界外，仅界内证支持该字段
        optional string issuerCode = 25; //发行人代码
    }
    
     // 期权类型额外数据
    message OptionSnapshotExData
    {
    	required int32 type = 1; //Qot_Common.OptionType，期权类型（按方向）
    	required Qot_Common.Security owner = 2; //标的股
    	required string strikeTime = 3; //行权日时间字符串（格式：yyyy-MM-dd）
        required double strikePrice = 4; //行权价
    	required int32 contractSize = 5; //每份合约数(整型数据)
    	optional double contractSizeFloat = 22; //每份合约数（浮点型数据）
    	required int32 openInterest = 6; //未平仓合约数
    	required double impliedVolatility = 7; //隐含波动率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double premium = 8; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double delta = 9; //希腊值 Delta
        required double gamma = 10; //希腊值 Gamma
    	required double vega = 11; //希腊值 Vega
        required double theta = 12; //希腊值 Theta
        required double rho = 13; //希腊值 Rho
    	optional double strikeTimestamp = 14; //行权日时间戳  
    	optional int32 indexOptionType = 15; //Qot_Common.IndexOptionType，指数期权类型
    	optional int32 netOpenInterest = 16; //净未平仓合约数，仅港股期权适用	
    	optional int32 expiryDateDistance = 17; //距离到期日天数，负数表示已过期
    	optional double contractNominalValue = 18; //合约名义金额，仅港股期权适用
    	optional double ownerLotMultiplier = 19; //相等正股手数，指数期权无该字段，仅港股期权适用
    	optional int32 optionAreaType = 20; //Qot_Common.OptionAreaType，期权类型（按行权时间）
    	optional double contractMultiplier = 21; //合约乘数
    }
    
    // 指数类型额外数据
    message IndexSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    // 板块类型额外数据
    message PlateSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    //期货类型额外数据
    message FutureSnapshotExData
    {
    	required double lastSettlePrice = 1; //昨结
    	required int32 position = 2; //持仓量
    	required int32 positionChange = 3; //日增仓
    	required string lastTradeTime = 4; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 5; //最后交易日时间戳，只有非主连期货合约才有该字段
    	required bool isMainContract = 6; //是否主连合约
    }
    
    //基金类型额外数据
    message TrustSnapshotExData
    {
    	required double dividendYield = 1; //股息率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double aum = 2; //资产规模
    	required int64 outstandingUnits = 3; //总发行量
    	required double netAssetValue = 4; //单位净值
    	required double premium = 5; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int32 assetClass = 6; //Qot_Common.AssetClass，资产类别
    }
    
     //基本快照数据
    message SnapshotBasicData
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 41; //股票名称
    	required int32 type = 2; //Qot_Common.SecurityType，证券类型
    	required bool isSuspend = 3; //是否停牌
    	required string listTime = 4; //上市时间字符串（格式：yyyy-MM-dd）
    	required int32 lotSize = 5; //每手数量
    	required double priceSpread = 6; //价差
    	required string updateTime = 7; //更新时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	required double highPrice = 8; //最高价
    	required double openPrice = 9; //开盘价
    	required double lowPrice = 10; //最低价
    	required double lastClosePrice = 11; //昨收价
    	required double curPrice = 12; //最新价
    	required int64 volume = 13; //成交量
    	required double turnover = 14; //成交额
    	required double turnoverRate = 15; //换手率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double listTimestamp = 16; //上市时间戳
    	optional double updateTimestamp = 17; //更新时间戳
    	optional double askPrice = 18;//卖价
        optional double bidPrice = 19;//买价
    	optional int64 askVol = 20;//卖量
        optional int64 bidVol = 21;//买量
    	optional bool enableMargin = 22; // 是否可融资（已废弃，请使用 获取融资融券数据 接口获取）
        optional double mortgageRatio = 23; // 股票抵押率（已废弃，请使用 获取融资融券数据 接口获取）
        optional double longMarginInitialRatio = 24; // 融资初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
        optional bool enableShortSell = 25; // 是否可卖空（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortSellRate = 26; // 卖空参考利率（已废弃，请使用 获取融资融券数据 接口获取）
        optional int64 shortAvailableVolume = 27; // 剩余可卖空数量（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortMarginInitialRatio = 28; // 卖空（融券）初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
    	optional double amplitude = 29; // 振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double avgPrice = 30; // 平均价
    	optional double bidAskRatio = 31; // 委比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double volumeRatio = 32;  // 量比
    	optional double highest52WeeksPrice = 33;  // 52周最高价
        optional double lowest52WeeksPrice = 34;  // 52周最低价
    	optional double highestHistoryPrice = 35;  // 历史最高价
        optional double lowestHistoryPrice = 36;  // 历史最低价
    	optional Qot_Common.PreAfterMarketData preMarket = 37; //Qot_Common::PreAfterMarketData 盘前数据
    	optional Qot_Common.PreAfterMarketData afterMarket = 38; //Qot_Common::PreAfterMarketData 盘后数据
        optional Qot_Common.PreAfterMarketData overnight = 42; //Qot_Common::PreAfterMarketData 夜盘数据
    	optional int32 secStatus = 39; //Qot_Common::SecurityStatus 股票状态
    	optional double closePrice5Minute = 40; //5分钟收盘价
    }
    
    message Snapshot
    {
    	required SnapshotBasicData basic = 1; //快照基本数据
    	optional EquitySnapshotExData equityExData = 2; //正股快照额外数据
    	optional WarrantSnapshotExData warrantExData = 3; //窝轮快照额外数据
    	optional OptionSnapshotExData optionExData = 4; //期权快照额外数据
    	optional IndexSnapshotExData indexExData = 5; //指数快照额外数据
    	optional PlateSnapshotExData plateExData = 6; //板块快照额外数据
    	optional FutureSnapshotExData futureExData = 7; //期货类型额外数据
    	optional TrustSnapshotExData trustExData = 8; //基金类型额外数据
    }
    
    message S2C
    {
    	repeated Snapshot snapshotList = 1; //股票快照
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
141  
142  
143  
144  
145  
146  
147  
148  
149  
150  
151  
152  
153  
154  
155  
156  
157  
158  
159  
160  
161  
162  
163  
164  
165  
166  
167  
168  
169  
170  
171  
172  
173  
174  
175  
176  
177  
178  
179  
180  
181  
182  
183  
184  
185  
186  
187  
188  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3203
    

`uint GetSecuritySnapshot(QotGetSecuritySnapshot.Request req);`  
`virtual void OnReply_GetSecuritySnapshot(MMAPI_Conn client, uint nSerialNo, QotGetSecuritySnapshot.Response rsp);`

*   **介绍**
    
    获取快照数据
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

     // 正股类型额外数据
    message EquitySnapshotExData
    {
        required int64 issuedShares = 1; // 总股本
    	required double issuedMarketVal = 2; // 总市值 =总股本*当前价格
        required double netAsset = 3; // 资产净值
        required double netProfit = 4; // 盈利（亏损）
        required double earningsPershare = 5; // 每股盈利
        required int64 outstandingShares = 6; // 流通股本
    	required double outstandingMarketVal = 7; // 流通市值 =流通股本*当前价格
        required double netAssetPershare = 8; // 每股净资产
        required double eyRate = 9; // 收益率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double peRate = 10; // 市盈率
        required double pbRate = 11; // 市净率
    	required double peTTMRate = 12; // 市盈率 TTM
    	optional double dividendTTM = 13; // 股息 TTM，派息
        optional double dividendRatioTTM = 14; // 股息率 TTM（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double dividendLFY = 15; // 股息 LFY，上一年度派息
        optional double dividendLFYRatio = 16; // 股息率 LFY（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
     // 窝轮类型额外数据
    message WarrantSnapshotExData
    {
        required double conversionRate = 1; //换股比率
        required int32 warrantType = 2; //Qot_Common.WarrantType，窝轮类型
        required double strikePrice = 3; //行使价
        required string maturityTime = 4; //到期日时间字符串
        required string endTradeTime = 5; //最后交易日时间字符串
        required Qot_Common.Security owner = 6; //所属正股 
        required double recoveryPrice = 7; //收回价，仅牛熊证支持该字段
        required int64 streetVolumn = 8; //街货量
        required int64 issueVolumn = 9; //发行量
        required double streetRate = 10; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double delta = 11; //对冲值，仅认购认沽支持该字段
        required double impliedVolatility = 12; //引申波幅，仅认购认沽支持该字段
        required double premium = 13; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double maturityTimestamp = 14; //到期日时间戳
        optional double endTradeTimestamp = 15; //最后交易日时间戳
        optional double leverage = 16;  // 杠杆比率（倍）
        optional double ipop = 17; // 价内/价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double breakEvenPoint = 18; // 打和点
        optional double conversionPrice = 19;  // 换股价
        optional double priceRecoveryRatio = 20; // 正股距收回价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double score = 21; // 综合评分
        optional double upperStrikePrice = 22; //上限价，仅界内证支持该字段
        optional double lowerStrikePrice = 23; //下限价，仅界内证支持该字段
        optional int32 inLinePriceStatus = 24; //Qot_Common.PriceType，界内界外，仅界内证支持该字段
        optional string issuerCode = 25; //发行人代码
    }
    
     // 期权类型额外数据
    message OptionSnapshotExData
    {
    	required int32 type = 1; //Qot_Common.OptionType，期权类型（按方向）
    	required Qot_Common.Security owner = 2; //标的股
    	required string strikeTime = 3; //行权日时间字符串（格式：yyyy-MM-dd）
        required double strikePrice = 4; //行权价
    	required int32 contractSize = 5; //每份合约数(整型数据)
    	optional double contractSizeFloat = 22; //每份合约数（浮点型数据）
    	required int32 openInterest = 6; //未平仓合约数
    	required double impliedVolatility = 7; //隐含波动率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double premium = 8; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double delta = 9; //希腊值 Delta
        required double gamma = 10; //希腊值 Gamma
    	required double vega = 11; //希腊值 Vega
        required double theta = 12; //希腊值 Theta
        required double rho = 13; //希腊值 Rho
    	optional double strikeTimestamp = 14; //行权日时间戳  
    	optional int32 indexOptionType = 15; //Qot_Common.IndexOptionType，指数期权类型
    	optional int32 netOpenInterest = 16; //净未平仓合约数，仅港股期权适用	
    	optional int32 expiryDateDistance = 17; //距离到期日天数，负数表示已过期
    	optional double contractNominalValue = 18; //合约名义金额，仅港股期权适用
    	optional double ownerLotMultiplier = 19; //相等正股手数，指数期权无该字段，仅港股期权适用
    	optional int32 optionAreaType = 20; //Qot_Common.OptionAreaType，期权类型（按行权时间）
    	optional double contractMultiplier = 21; //合约乘数
    }
    
    // 指数类型额外数据
    message IndexSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    // 板块类型额外数据
    message PlateSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    //期货类型额外数据
    message FutureSnapshotExData
    {
    	required double lastSettlePrice = 1; //昨结
    	required int32 position = 2; //持仓量
    	required int32 positionChange = 3; //日增仓
    	required string lastTradeTime = 4; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 5; //最后交易日时间戳，只有非主连期货合约才有该字段
    	required bool isMainContract = 6; //是否主连合约
    }
    
    //基金类型额外数据
    message TrustSnapshotExData
    {
    	required double dividendYield = 1; //股息率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double aum = 2; //资产规模
    	required int64 outstandingUnits = 3; //总发行量
    	required double netAssetValue = 4; //单位净值
    	required double premium = 5; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int32 assetClass = 6; //Qot_Common.AssetClass，资产类别
    }
    
     //基本快照数据
    message SnapshotBasicData
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 41; //股票名称
    	required int32 type = 2; //Qot_Common.SecurityType，证券类型
    	required bool isSuspend = 3; //是否停牌
    	required string listTime = 4; //上市时间字符串（格式：yyyy-MM-dd）
    	required int32 lotSize = 5; //每手数量
    	required double priceSpread = 6; //价差
    	required string updateTime = 7; //更新时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	required double highPrice = 8; //最高价
    	required double openPrice = 9; //开盘价
    	required double lowPrice = 10; //最低价
    	required double lastClosePrice = 11; //昨收价
    	required double curPrice = 12; //最新价
    	required int64 volume = 13; //成交量
    	required double turnover = 14; //成交额
    	required double turnoverRate = 15; //换手率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double listTimestamp = 16; //上市时间戳
    	optional double updateTimestamp = 17; //更新时间戳
    	optional double askPrice = 18;//卖价
        optional double bidPrice = 19;//买价
    	optional int64 askVol = 20;//卖量
        optional int64 bidVol = 21;//买量
    	optional bool enableMargin = 22; // 是否可融资（已废弃，请使用 获取融资融券数据 接口获取）
        optional double mortgageRatio = 23; // 股票抵押率（已废弃，请使用 获取融资融券数据 接口获取）
        optional double longMarginInitialRatio = 24; // 融资初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
        optional bool enableShortSell = 25; // 是否可卖空（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortSellRate = 26; // 卖空参考利率（已废弃，请使用 获取融资融券数据 接口获取）
        optional int64 shortAvailableVolume = 27; // 剩余可卖空数量（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortMarginInitialRatio = 28; // 卖空（融券）初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
    	optional double amplitude = 29; // 振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double avgPrice = 30; // 平均价
    	optional double bidAskRatio = 31; // 委比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double volumeRatio = 32;  // 量比
    	optional double highest52WeeksPrice = 33;  // 52周最高价
        optional double lowest52WeeksPrice = 34;  // 52周最低价
    	optional double highestHistoryPrice = 35;  // 历史最高价
        optional double lowestHistoryPrice = 36;  // 历史最低价
    	optional Qot_Common.PreAfterMarketData preMarket = 37; //Qot_Common::PreAfterMarketData 盘前数据
    	optional Qot_Common.PreAfterMarketData afterMarket = 38; //Qot_Common::PreAfterMarketData 
        optional Qot_Common.PreAfterMarketData overnight = 42; //Qot_Common::PreAfterMarketData 夜盘数据
    	optional int32 secStatus = 39; //Qot_Common::SecurityStatus 股票状态
    	optional double closePrice5Minute = 40; //5分钟收盘价
    }
    
    message Snapshot
    {
    	required SnapshotBasicData basic = 1; //快照基本数据
    	optional EquitySnapshotExData equityExData = 2; //正股快照额外数据
    	optional WarrantSnapshotExData warrantExData = 3; //窝轮快照额外数据
    	optional OptionSnapshotExData optionExData = 4; //期权快照额外数据
    	optional IndexSnapshotExData indexExData = 5; //指数快照额外数据
    	optional PlateSnapshotExData plateExData = 6; //板块快照额外数据
    	optional FutureSnapshotExData futureExData = 7; //期货类型额外数据
    	optional TrustSnapshotExData trustExData = 8; //基金类型额外数据
    }
    
    message S2C
    {
    	repeated Snapshot snapshotList = 1; //股票快照
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
141  
142  
143  
144  
145  
146  
147  
148  
149  
150  
151  
152  
153  
154  
155  
156  
157  
158  
159  
160  
161  
162  
163  
164  
165  
166  
167  
168  
169  
170  
171  
172  
173  
174  
175  
176  
177  
178  
179  
180  
181  
182  
183  
184  
185  
186  
187  
188  

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
    
            QotCommon.Security sec = QotCommon.Security.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetCode("00700")
                    .Build();
            QotGetSecuritySnapshot.C2S c2s = QotGetSecuritySnapshot.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                .Build();
            QotGetSecuritySnapshot.Request req = QotGetSecuritySnapshot.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetSecuritySnapshot(req);
            Console.Write("Send QotGetSecuritySnapshot: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetSecuritySnapshot(MMAPI_Conn client, uint nSerialNo, QotGetSecuritySnapshot.Response rsp)
        {
            Console.Write("Reply: QotGetSecuritySnapshot: {0} \n", nSerialNo);
            Console.Write("basic price: {0}\n", rsp.S2C.SnapshotListList[0].Basic.CurPrice);
            Console.Write("equityExData issuedShares: {0}\n", rsp.S2C.SnapshotListList[0].EquityExData.IssuedShares);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825619056039643630
    Send QotGetSecuritySnapshot: 3
    Reply: QotGetSecuritySnapshot: 3
    basic price: 474.2
    equityExData issuedShares: 9595900007
    

1  
2  
3  
4  
5  

`int getSecuritySnapshot(QotGetSecuritySnapshot.Request req);`  
`void onReply_GetSecuritySnapshot(MMAPI_Conn client, int nSerialNo, QotGetSecuritySnapshot.Response rsp);`

*   **介绍**
    
    获取快照数据
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

     // 正股类型额外数据
    message EquitySnapshotExData
    {
        required int64 issuedShares = 1; // 总股本
    	required double issuedMarketVal = 2; // 总市值 =总股本*当前价格
        required double netAsset = 3; // 资产净值
        required double netProfit = 4; // 盈利（亏损）
        required double earningsPershare = 5; // 每股盈利
        required int64 outstandingShares = 6; // 流通股本
    	required double outstandingMarketVal = 7; // 流通市值 =流通股本*当前价格
        required double netAssetPershare = 8; // 每股净资产
        required double eyRate = 9; // 收益率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double peRate = 10; // 市盈率
        required double pbRate = 11; // 市净率
    	required double peTTMRate = 12; // 市盈率 TTM
    	optional double dividendTTM = 13; // 股息 TTM，派息
        optional double dividendRatioTTM = 14; // 股息率 TTM（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double dividendLFY = 15; // 股息 LFY，上一年度派息
        optional double dividendLFYRatio = 16; // 股息率 LFY（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
     // 窝轮类型额外数据
    message WarrantSnapshotExData
    {
        required double conversionRate = 1; //换股比率
        required int32 warrantType = 2; //Qot_Common.WarrantType，窝轮类型
        required double strikePrice = 3; //行使价
        required string maturityTime = 4; //到期日时间字符串
        required string endTradeTime = 5; //最后交易日时间字符串
        required Qot_Common.Security owner = 6; //所属正股 
        required double recoveryPrice = 7; //收回价，仅牛熊证支持该字段
        required int64 streetVolumn = 8; //街货量
        required int64 issueVolumn = 9; //发行量
        required double streetRate = 10; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double delta = 11; //对冲值，仅认购认沽支持该字段
        required double impliedVolatility = 12; //引申波幅，仅认购认沽支持该字段
        required double premium = 13; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double maturityTimestamp = 14; //到期日时间戳
        optional double endTradeTimestamp = 15; //最后交易日时间戳
        optional double leverage = 16;  // 杠杆比率（倍）
        optional double ipop = 17; // 价内/价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double breakEvenPoint = 18; // 打和点
        optional double conversionPrice = 19;  // 换股价
        optional double priceRecoveryRatio = 20; // 正股距收回价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double score = 21; // 综合评分
        optional double upperStrikePrice = 22; //上限价，仅界内证支持该字段
        optional double lowerStrikePrice = 23; //下限价，仅界内证支持该字段
        optional int32 inLinePriceStatus = 24; //Qot_Common.PriceType，界内界外，仅界内证支持该字段
        optional string issuerCode = 25; //发行人代码
    }
    
     // 期权类型额外数据
    message OptionSnapshotExData
    {
    	required int32 type = 1; //Qot_Common.OptionType，期权类型（按方向）
    	required Qot_Common.Security owner = 2; //标的股
    	required string strikeTime = 3; //行权日时间字符串（格式：yyyy-MM-dd）
        required double strikePrice = 4; //行权价
    	required int32 contractSize = 5; //每份合约数(整型数据)
    	optional double contractSizeFloat = 22; //每份合约数（浮点型数据）
    	required int32 openInterest = 6; //未平仓合约数
    	required double impliedVolatility = 7; //隐含波动率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double premium = 8; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double delta = 9; //希腊值 Delta
        required double gamma = 10; //希腊值 Gamma
    	required double vega = 11; //希腊值 Vega
        required double theta = 12; //希腊值 Theta
        required double rho = 13; //希腊值 Rho
    	optional double strikeTimestamp = 14; //行权日时间戳  
    	optional int32 indexOptionType = 15; //Qot_Common.IndexOptionType，指数期权类型
    	optional int32 netOpenInterest = 16; //净未平仓合约数，仅港股期权适用	
    	optional int32 expiryDateDistance = 17; //距离到期日天数，负数表示已过期
    	optional double contractNominalValue = 18; //合约名义金额，仅港股期权适用
    	optional double ownerLotMultiplier = 19; //相等正股手数，指数期权无该字段，仅港股期权适用
    	optional int32 optionAreaType = 20; //Qot_Common.OptionAreaType，期权类型（按行权时间）
    	optional double contractMultiplier = 21; //合约乘数
    }
    
    // 指数类型额外数据
    message IndexSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    // 板块类型额外数据
    message PlateSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    //期货类型额外数据
    message FutureSnapshotExData
    {
    	required double lastSettlePrice = 1; //昨结
    	required int32 position = 2; //持仓量
    	required int32 positionChange = 3; //日增仓
    	required string lastTradeTime = 4; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 5; //最后交易日时间戳，只有非主连期货合约才有该字段
    	required bool isMainContract = 6; //是否主连合约
    }
    
    //基金类型额外数据
    message TrustSnapshotExData
    {
    	required double dividendYield = 1; //股息率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double aum = 2; //资产规模
    	required int64 outstandingUnits = 3; //总发行量
    	required double netAssetValue = 4; //单位净值
    	required double premium = 5; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int32 assetClass = 6; //Qot_Common.AssetClass，资产类别
    }
    
     //基本快照数据
    message SnapshotBasicData
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 41; //股票名称
    	required int32 type = 2; //Qot_Common.SecurityType，证券类型
    	required bool isSuspend = 3; //是否停牌
    	required string listTime = 4; //上市时间字符串（格式：yyyy-MM-dd）
    	required int32 lotSize = 5; //每手数量
    	required double priceSpread = 6; //价差
    	required string updateTime = 7; //更新时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	required double highPrice = 8; //最高价
    	required double openPrice = 9; //开盘价
    	required double lowPrice = 10; //最低价
    	required double lastClosePrice = 11; //昨收价
    	required double curPrice = 12; //最新价
    	required int64 volume = 13; //成交量
    	required double turnover = 14; //成交额
    	required double turnoverRate = 15; //换手率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double listTimestamp = 16; //上市时间戳
    	optional double updateTimestamp = 17; //更新时间戳
    	optional double askPrice = 18;//卖价
        optional double bidPrice = 19;//买价
    	optional int64 askVol = 20;//卖量
        optional int64 bidVol = 21;//买量
    	optional bool enableMargin = 22; // 是否可融资（已废弃，请使用 获取融资融券数据 接口获取）
        optional double mortgageRatio = 23; // 股票抵押率（已废弃，请使用 获取融资融券数据 接口获取）
        optional double longMarginInitialRatio = 24; // 融资初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
        optional bool enableShortSell = 25; // 是否可卖空（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortSellRate = 26; // 卖空参考利率（已废弃，请使用 获取融资融券数据 接口获取）
        optional int64 shortAvailableVolume = 27; // 剩余可卖空数量（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortMarginInitialRatio = 28; // 卖空（融券）初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
    	optional double amplitude = 29; // 振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double avgPrice = 30; // 平均价
    	optional double bidAskRatio = 31; // 委比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double volumeRatio = 32;  // 量比
    	optional double highest52WeeksPrice = 33;  // 52周最高价
        optional double lowest52WeeksPrice = 34;  // 52周最低价
    	optional double highestHistoryPrice = 35;  // 历史最高价
        optional double lowestHistoryPrice = 36;  // 历史最低价
    	optional Qot_Common.PreAfterMarketData preMarket = 37; //Qot_Common::PreAfterMarketData 盘前数据
    	optional Qot_Common.PreAfterMarketData afterMarket = 38; //Qot_Common::PreAfterMarketData 盘后数据
        optional Qot_Common.PreAfterMarketData overnight = 42; //Qot_Common::PreAfterMarketData 夜盘数据
    	optional int32 secStatus = 39; //Qot_Common::SecurityStatus 股票状态
    	optional double closePrice5Minute = 40; //5分钟收盘价
    }
    
    message Snapshot
    {
    	required SnapshotBasicData basic = 1; //快照基本数据
    	optional EquitySnapshotExData equityExData = 2; //正股快照额外数据
    	optional WarrantSnapshotExData warrantExData = 3; //窝轮快照额外数据
    	optional OptionSnapshotExData optionExData = 4; //期权快照额外数据
    	optional IndexSnapshotExData indexExData = 5; //指数快照额外数据
    	optional PlateSnapshotExData plateExData = 6; //板块快照额外数据
    	optional FutureSnapshotExData futureExData = 7; //期货类型额外数据
    	optional TrustSnapshotExData trustExData = 8; //基金类型额外数据
    }
    
    message S2C
    {
    	repeated Snapshot snapshotList = 1; //股票快照
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
141  
142  
143  
144  
145  
146  
147  
148  
149  
150  
151  
152  
153  
154  
155  
156  
157  
158  
159  
160  
161  
162  
163  
164  
165  
166  
167  
168  
169  
170  
171  
172  
173  
174  
175  
176  
177  
178  
179  
180  
181  
182  
183  
184  
185  
186  
187  
188  

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
    
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .setCode("00700")
                    .build();
            QotGetSecuritySnapshot.C2S c2s = QotGetSecuritySnapshot.C2S.newBuilder()
                    .addSecurityList(sec)
                .build();
            QotGetSecuritySnapshot.Request req = QotGetSecuritySnapshot.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getSecuritySnapshot(req);
            System.out.printf("Send QotGetSecuritySnapshot: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetSecuritySnapshot(MMAPI_Conn client, int nSerialNo, QotGetSecuritySnapshot.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetSecuritySnapshot failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetSecuritySnapshot: %s\n", json);
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

*   **Output**

    Send QotGetSecuritySnapshot: 2
    Receive QotGetSecuritySnapshot: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "snapshotList": [{\
          "basic": {\
            "security": {\
              "market": 1,\
              "code": "00700"\
            },\
            "type": 3,\
            "isSuspend": false,\
            "listTime": "2004-06-16",\
            "lotSize": 100,\
            "priceSpread": 0.5,\
            "updateTime": "2021-06-24 16:08:14",\
            "highPrice": 587.5,\
            "openPrice": 584.0,\
            "lowPrice": 580.0,\
            "lastClosePrice": 582.5,\
            "curPrice": 583.0,\
            "volume": "10947302",\
            "turnover": 6.387238277E9,\
            "turnoverRate": 0.114,\
            "listTimestamp": 1.0873152E9,\
            "updateTimestamp": 1.624522094E9,\
            "askPrice": 583.5,\
            "bidPrice": 583.0,\
            "askVol": "142300",\
            "bidVol": "52800",\
            "enableMargin": true,\
            "mortgageRatio": 0.0,\
            "longMarginInitialRatio": 35.0,\
            "enableShortSell": true,\
            "shortSellRate": 0.9,\
            "shortAvailableVolume": "2006700",\
            "shortMarginInitialRatio": 60.0,\
            "amplitude": 1.288,\
            "avgPrice": 583.453,\
            "bidAskRatio": -5.273,\
            "volumeRatio": 0.553,\
            "highest52WeeksPrice": 773.9,\
            "lowest52WeeksPrice": 479.4,\
            "highestHistoryPrice": 773.9,\
            "lowestHistoryPrice": -6.381,\
            "secStatus": 1,\
            "closePrice5Minute": 583.5\
          },\
          "equityExData": {\
            "issuedShares": "9595206625",\
            "issuedMarketVal": 5.594005462375E12,\
            "netAsset": 9.17675966408375E11,\
            "netProfit": 1.90378494646625E11,\
            "earningsPershare": 19.841,\
            "outstandingShares": "9595206625",\
            "outstandingMarketVal": 5.594005462375E12,\
            "netAssetPershare": 95.639,\
            "eyRate": 0.232,\
            "peRate": 29.383,\
            "pbRate": 6.095,\
            "peTTMRate": 26.249,\
            "dividendTTM": 1.6,\
            "dividendRatioTTM": 0.27,\
            "dividendLFY": 1.6,\
            "dividendLFYRatio": 0.274\
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

`moomoo::u32_t GetSecuritySnapshot(const Qot_GetSecuritySnapshot::Request &stReq);`  
`virtual void OnReply_GetSecuritySnapshot(moomoo::u32_t nSerialNo, const Qot_GetSecuritySnapshot::Response &stRsp) = 0;`

*   **介绍**
    
    获取快照数据
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

     // 正股类型额外数据
    message EquitySnapshotExData
    {
        required int64 issuedShares = 1; // 总股本
    	required double issuedMarketVal = 2; // 总市值 =总股本*当前价格
        required double netAsset = 3; // 资产净值
        required double netProfit = 4; // 盈利（亏损）
        required double earningsPershare = 5; // 每股盈利
        required int64 outstandingShares = 6; // 流通股本
    	required double outstandingMarketVal = 7; // 流通市值 =流通股本*当前价格
        required double netAssetPershare = 8; // 每股净资产
        required double eyRate = 9; // 收益率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double peRate = 10; // 市盈率
        required double pbRate = 11; // 市净率
    	required double peTTMRate = 12; // 市盈率 TTM
    	optional double dividendTTM = 13; // 股息 TTM，派息
        optional double dividendRatioTTM = 14; // 股息率 TTM（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double dividendLFY = 15; // 股息 LFY，上一年度派息
        optional double dividendLFYRatio = 16; // 股息率 LFY（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
     // 窝轮类型额外数据
    message WarrantSnapshotExData
    {
        required double conversionRate = 1; //换股比率
        required int32 warrantType = 2; //Qot_Common.WarrantType，窝轮类型
        required double strikePrice = 3; //行使价
        required string maturityTime = 4; //到期日时间字符串
        required string endTradeTime = 5; //最后交易日时间字符串
        required Qot_Common.Security owner = 6; //所属正股 
        required double recoveryPrice = 7; //收回价，仅牛熊证支持该字段
        required int64 streetVolumn = 8; //街货量
        required int64 issueVolumn = 9; //发行量
        required double streetRate = 10; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double delta = 11; //对冲值，仅认购认沽支持该字段
        required double impliedVolatility = 12; //引申波幅，仅认购认沽支持该字段
        required double premium = 13; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double maturityTimestamp = 14; //到期日时间戳
        optional double endTradeTimestamp = 15; //最后交易日时间戳
        optional double leverage = 16;  // 杠杆比率（倍）
        optional double ipop = 17; // 价内/价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double breakEvenPoint = 18; // 打和点
        optional double conversionPrice = 19;  // 换股价
        optional double priceRecoveryRatio = 20; // 正股距收回价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double score = 21; // 综合评分
        optional double upperStrikePrice = 22; //上限价，仅界内证支持该字段
        optional double lowerStrikePrice = 23; //下限价，仅界内证支持该字段
        optional int32 inLinePriceStatus = 24; //Qot_Common.PriceType，界内界外，仅界内证支持该字段
        optional string issuerCode = 25; //发行人代码
    }
    
     // 期权类型额外数据
    message OptionSnapshotExData
    {
    	required int32 type = 1; //Qot_Common.OptionType，期权类型（按方向）
    	required Qot_Common.Security owner = 2; //标的股
    	required string strikeTime = 3; //行权日时间字符串（格式：yyyy-MM-dd）
        required double strikePrice = 4; //行权价
    	required int32 contractSize = 5; //每份合约数(整型数据)
    	optional double contractSizeFloat = 22; //每份合约数（浮点型数据）
    	required int32 openInterest = 6; //未平仓合约数
    	required double impliedVolatility = 7; //隐含波动率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double premium = 8; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double delta = 9; //希腊值 Delta
        required double gamma = 10; //希腊值 Gamma
    	required double vega = 11; //希腊值 Vega
        required double theta = 12; //希腊值 Theta
        required double rho = 13; //希腊值 Rho
    	optional double strikeTimestamp = 14; //行权日时间戳  
    	optional int32 indexOptionType = 15; //Qot_Common.IndexOptionType，指数期权类型
    	optional int32 netOpenInterest = 16; //净未平仓合约数，仅港股期权适用	
    	optional int32 expiryDateDistance = 17; //距离到期日天数，负数表示已过期
    	optional double contractNominalValue = 18; //合约名义金额，仅港股期权适用
    	optional double ownerLotMultiplier = 19; //相等正股手数，指数期权无该字段，仅港股期权适用
    	optional int32 optionAreaType = 20; //Qot_Common.OptionAreaType，期权类型（按行权时间）
    	optional double contractMultiplier = 21; //合约乘数
    }
    
    // 指数类型额外数据
    message IndexSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    // 板块类型额外数据
    message PlateSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    //期货类型额外数据
    message FutureSnapshotExData
    {
    	required double lastSettlePrice = 1; //昨结
    	required int32 position = 2; //持仓量
    	required int32 positionChange = 3; //日增仓
    	required string lastTradeTime = 4; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 5; //最后交易日时间戳，只有非主连期货合约才有该字段
    	required bool isMainContract = 6; //是否主连合约
    }
    
    //基金类型额外数据
    message TrustSnapshotExData
    {
    	required double dividendYield = 1; //股息率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double aum = 2; //资产规模
    	required int64 outstandingUnits = 3; //总发行量
    	required double netAssetValue = 4; //单位净值
    	required double premium = 5; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int32 assetClass = 6; //Qot_Common.AssetClass，资产类别
    }
    
     //基本快照数据
    message SnapshotBasicData
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 41; //股票名称
    	required int32 type = 2; //Qot_Common.SecurityType，证券类型
    	required bool isSuspend = 3; //是否停牌
    	required string listTime = 4; //上市时间字符串（格式：yyyy-MM-dd）
    	required int32 lotSize = 5; //每手数量
    	required double priceSpread = 6; //价差
    	required string updateTime = 7; //更新时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	required double highPrice = 8; //最高价
    	required double openPrice = 9; //开盘价
    	required double lowPrice = 10; //最低价
    	required double lastClosePrice = 11; //昨收价
    	required double curPrice = 12; //最新价
    	required int64 volume = 13; //成交量
    	required double turnover = 14; //成交额
    	required double turnoverRate = 15; //换手率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double listTimestamp = 16; //上市时间戳
    	optional double updateTimestamp = 17; //更新时间戳
    	optional double askPrice = 18;//卖价
        optional double bidPrice = 19;//买价
    	optional int64 askVol = 20;//卖量
        optional int64 bidVol = 21;//买量
    	optional bool enableMargin = 22; // 是否可融资（已废弃，请使用 获取融资融券数据 接口获取）
        optional double mortgageRatio = 23; // 股票抵押率（已废弃，请使用 获取融资融券数据 接口获取）
        optional double longMarginInitialRatio = 24; // 融资初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
        optional bool enableShortSell = 25; // 是否可卖空（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortSellRate = 26; // 卖空参考利率（已废弃，请使用 获取融资融券数据 接口获取）
        optional int64 shortAvailableVolume = 27; // 剩余可卖空数量（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortMarginInitialRatio = 28; // 卖空（融券）初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
    	optional double amplitude = 29; // 振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double avgPrice = 30; // 平均价
    	optional double bidAskRatio = 31; // 委比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double volumeRatio = 32;  // 量比
    	optional double highest52WeeksPrice = 33;  // 52周最高价
        optional double lowest52WeeksPrice = 34;  // 52周最低价
    	optional double highestHistoryPrice = 35;  // 历史最高价
        optional double lowestHistoryPrice = 36;  // 历史最低价
    	optional Qot_Common.PreAfterMarketData preMarket = 37; //Qot_Common::PreAfterMarketData 盘前数据
    	optional Qot_Common.PreAfterMarketData afterMarket = 38; //Qot_Common::PreAfterMarketData 盘后数据
        optional Qot_Common.PreAfterMarketData overnight = 42; //Qot_Common::PreAfterMarketData 夜盘数据
    	optional int32 secStatus = 39; //Qot_Common::SecurityStatus 股票状态
    	optional double closePrice5Minute = 40; //5分钟收盘价
    }
    
    message Snapshot
    {
    	required SnapshotBasicData basic = 1; //快照基本数据
    	optional EquitySnapshotExData equityExData = 2; //正股快照额外数据
    	optional WarrantSnapshotExData warrantExData = 3; //窝轮快照额外数据
    	optional OptionSnapshotExData optionExData = 4; //期权快照额外数据
    	optional IndexSnapshotExData indexExData = 5; //指数快照额外数据
    	optional PlateSnapshotExData plateExData = 6; //板块快照额外数据
    	optional FutureSnapshotExData futureExData = 7; //期货类型额外数据
    	optional TrustSnapshotExData trustExData = 8; //基金类型额外数据
    }
    
    message S2C
    {
    	repeated Snapshot snapshotList = 1; //股票快照
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
141  
142  
143  
144  
145  
146  
147  
148  
149  
150  
151  
152  
153  
154  
155  
156  
157  
158  
159  
160  
161  
162  
163  
164  
165  
166  
167  
168  
169  
170  
171  
172  
173  
174  
175  
176  
177  
178  
179  
180  
181  
182  
183  
184  
185  
186  
187  
188  

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
    		Qot_GetSecuritySnapshot::Request req;
    		Qot_GetSecuritySnapshot::C2S *c2s = req.mutable_c2s();
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
            m_GetSecuritySnapshotSerialNo = m_pQotApi->GetSecuritySnapshot(req);
            cout << "Request GetSecuritySnapshot SerialNo: " << m_GetSecuritySnapshotSerialNo << endl;
    	}
    
    	virtual void OnReply_GetSecuritySnapshot(moomoo::u32_t nSerialNo, const Qot_GetSecuritySnapshot::Response &stRsp){
            if(nSerialNo == m_GetSecuritySnapshotSerialNo)
            {
                cout << "OnReply_GetSecuritySnapshot SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetSecuritySnapshotSerialNo;
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
    Request GetSecuritySnapshot SerialNo: 4
    OnReply_GetSecuritySnapshot SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "snapshotList": [\
       {\
        "basic": {\
         "security": {\
          "market": 1,\
          "code": "00700"\
         },\
         "type": 3,\
         "isSuspend": false,\
         "listTime": "2004-06-16",\
         "lotSize": 100,\
         "priceSpread": 0.5,\
         "updateTime": "2021-06-09 11:01:40",\
         "highPrice": 605.5,\
         "openPrice": 600,\
         "lowPrice": 597.5,\
         "lastClosePrice": 601,\
         "curPrice": 604,\
         "volume": "2771395",\
         "turnover": 1668044020,\
         "turnoverRate": 0.029,\
         "listTimestamp": 1087315200,\
         "updateTimestamp": 1623207700,\
         "askPrice": 604.5,\
         "bidPrice": 604,\
         "askVol": "21500",\
         "bidVol": "9500",\
         "enableMargin": true,\
         "mortgageRatio": 0,\
         "longMarginInitialRatio": 35,\
         "enableShortSell": true,\
         "shortSellRate": 0.9,\
         "shortAvailableVolume": "1849300",\
         "shortMarginInitialRatio": 60,\
         "amplitude": 1.331,\
         "avgPrice": 601.878,\
         "bidAskRatio": -9.111,\
         "volumeRatio": 0.643,\
         "highest52WeeksPrice": 773.9,\
         "lowest52WeeksPrice": 430.6,\
         "highestHistoryPrice": 773.9,\
         "lowestHistoryPrice": -6.381,\
         "secStatus": 1,\
         "closePrice5Minute": 603.5\
        },\
        "equityExData": {\
         "issuedShares": "9595206625",\
         "issuedMarketVal": 5795504801500,\
         "netAsset": 917675966408.375,\
         "netProfit": 190378494646.625,\
         "earningsPershare": 19.841,\
         "outstandingShares": "9595206625",\
         "outstandingMarketVal": 5795504801500,\
         "netAssetPershare": 95.639,\
         "eyRate": 0.232,\
         "peRate": 30.442,\
         "pbRate": 6.315,\
         "peTTMRate": 27.194,\
         "dividendTTM": 1.6,\
         "dividendRatioTTM": 0.26,\
         "dividendLFY": 1.6,\
         "dividendLFYRatio": 0.264\
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

`GetSecuritySnapshot(req);`

*   **介绍**
    
    获取快照数据
    
*   **参数**
    

    message C2S
    {
    	repeated Qot_Common.Security securityList = 1; //股票
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

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

     // 正股类型额外数据
    message EquitySnapshotExData
    {
        required int64 issuedShares = 1; // 总股本
    	required double issuedMarketVal = 2; // 总市值 =总股本*当前价格
        required double netAsset = 3; // 资产净值
        required double netProfit = 4; // 盈利（亏损）
        required double earningsPershare = 5; // 每股盈利
        required int64 outstandingShares = 6; // 流通股本
    	required double outstandingMarketVal = 7; // 流通市值 =流通股本*当前价格
        required double netAssetPershare = 8; // 每股净资产
        required double eyRate = 9; // 收益率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double peRate = 10; // 市盈率
        required double pbRate = 11; // 市净率
    	required double peTTMRate = 12; // 市盈率 TTM
    	optional double dividendTTM = 13; // 股息 TTM，派息
        optional double dividendRatioTTM = 14; // 股息率 TTM（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double dividendLFY = 15; // 股息 LFY，上一年度派息
        optional double dividendLFYRatio = 16; // 股息率 LFY（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    
     // 窝轮类型额外数据
    message WarrantSnapshotExData
    {
        required double conversionRate = 1; //换股比率
        required int32 warrantType = 2; //Qot_Common.WarrantType，窝轮类型
        required double strikePrice = 3; //行使价
        required string maturityTime = 4; //到期日时间字符串
        required string endTradeTime = 5; //最后交易日时间字符串
        required Qot_Common.Security owner = 6; //所属正股 
        required double recoveryPrice = 7; //收回价，仅牛熊证支持该字段
        required int64 streetVolumn = 8; //街货量
        required int64 issueVolumn = 9; //发行量
        required double streetRate = 10; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double delta = 11; //对冲值，仅认购认沽支持该字段
        required double impliedVolatility = 12; //引申波幅，仅认购认沽支持该字段
        required double premium = 13; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double maturityTimestamp = 14; //到期日时间戳
        optional double endTradeTimestamp = 15; //最后交易日时间戳
        optional double leverage = 16;  // 杠杆比率（倍）
        optional double ipop = 17; // 价内/价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double breakEvenPoint = 18; // 打和点
        optional double conversionPrice = 19;  // 换股价
        optional double priceRecoveryRatio = 20; // 正股距收回价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double score = 21; // 综合评分
        optional double upperStrikePrice = 22; //上限价，仅界内证支持该字段
        optional double lowerStrikePrice = 23; //下限价，仅界内证支持该字段
        optional int32 inLinePriceStatus = 24; //Qot_Common.PriceType，界内界外，仅界内证支持该字段
        optional string issuerCode = 25; //发行人代码
    }
    
     // 期权类型额外数据
    message OptionSnapshotExData
    {
    	required int32 type = 1; //Qot_Common.OptionType，期权类型（按方向）
    	required Qot_Common.Security owner = 2; //标的股
    	required string strikeTime = 3; //行权日时间字符串（格式：yyyy-MM-dd）
        required double strikePrice = 4; //行权价
    	required int32 contractSize = 5; //每份合约数(整型数据)
    	optional double contractSizeFloat = 22; //每份合约数（浮点型数据）
    	required int32 openInterest = 6; //未平仓合约数
    	required double impliedVolatility = 7; //隐含波动率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double premium = 8; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double delta = 9; //希腊值 Delta
        required double gamma = 10; //希腊值 Gamma
    	required double vega = 11; //希腊值 Vega
        required double theta = 12; //希腊值 Theta
        required double rho = 13; //希腊值 Rho
    	optional double strikeTimestamp = 14; //行权日时间戳  
    	optional int32 indexOptionType = 15; //Qot_Common.IndexOptionType，指数期权类型
    	optional int32 netOpenInterest = 16; //净未平仓合约数，仅港股期权适用	
    	optional int32 expiryDateDistance = 17; //距离到期日天数，负数表示已过期
    	optional double contractNominalValue = 18; //合约名义金额，仅港股期权适用
    	optional double ownerLotMultiplier = 19; //相等正股手数，指数期权无该字段，仅港股期权适用
    	optional int32 optionAreaType = 20; //Qot_Common.OptionAreaType，期权类型（按行权时间）
    	optional double contractMultiplier = 21; //合约乘数
    }
    
    // 指数类型额外数据
    message IndexSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    // 板块类型额外数据
    message PlateSnapshotExData
    {
        required int32 raiseCount = 1;  // 上涨支数
        required int32 fallCount = 2;  // 下跌支数
        required int32 equalCount = 3;  // 平盘支数
    }
    
    //期货类型额外数据
    message FutureSnapshotExData
    {
    	required double lastSettlePrice = 1; //昨结
    	required int32 position = 2; //持仓量
    	required int32 positionChange = 3; //日增仓
    	required string lastTradeTime = 4; //最后交易日，只有非主连期货合约才有该字段
    	optional double lastTradeTimestamp = 5; //最后交易日时间戳，只有非主连期货合约才有该字段
    	required bool isMainContract = 6; //是否主连合约
    }
    
    //基金类型额外数据
    message TrustSnapshotExData
    {
    	required double dividendYield = 1; //股息率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required double aum = 2; //资产规模
    	required int64 outstandingUnits = 3; //总发行量
    	required double netAssetValue = 4; //单位净值
    	required double premium = 5; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	required int32 assetClass = 6; //Qot_Common.AssetClass，资产类别
    }
    
     //基本快照数据
    message SnapshotBasicData
    {
    	required Qot_Common.Security security = 1; //证券
        optional string name = 41; //股票名称
    	required int32 type = 2; //Qot_Common.SecurityType，证券类型
    	required bool isSuspend = 3; //是否停牌
    	required string listTime = 4; //上市时间字符串（格式：yyyy-MM-dd）
    	required int32 lotSize = 5; //每手数量
    	required double priceSpread = 6; //价差
    	required string updateTime = 7; //更新时间字符串（格式：yyyy-MM-dd HH:mm:ss）
    	required double highPrice = 8; //最高价
    	required double openPrice = 9; //开盘价
    	required double lowPrice = 10; //最低价
    	required double lastClosePrice = 11; //昨收价
    	required double curPrice = 12; //最新价
    	required int64 volume = 13; //成交量
    	required double turnover = 14; //成交额
    	required double turnoverRate = 15; //换手率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double listTimestamp = 16; //上市时间戳
    	optional double updateTimestamp = 17; //更新时间戳
    	optional double askPrice = 18;//卖价
        optional double bidPrice = 19;//买价
    	optional int64 askVol = 20;//卖量
        optional int64 bidVol = 21;//买量
    	optional bool enableMargin = 22; // 是否可融资（已废弃，请使用 获取融资融券数据 接口获取）
        optional double mortgageRatio = 23; // 股票抵押率（已废弃，请使用 获取融资融券数据 接口获取）
        optional double longMarginInitialRatio = 24; // 融资初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
        optional bool enableShortSell = 25; // 是否可卖空（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortSellRate = 26; // 卖空参考利率（已废弃，请使用 获取融资融券数据 接口获取）
        optional int64 shortAvailableVolume = 27; // 剩余可卖空数量（已废弃，请使用 获取融资融券数据 接口获取）
        optional double shortMarginInitialRatio = 28; // 卖空（融券）初始保证金率（已废弃，请使用 获取融资融券数据 接口获取）
    	optional double amplitude = 29; // 振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double avgPrice = 30; // 平均价
    	optional double bidAskRatio = 31; // 委比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    	optional double volumeRatio = 32;  // 量比
    	optional double highest52WeeksPrice = 33;  // 52周最高价
        optional double lowest52WeeksPrice = 34;  // 52周最低价
    	optional double highestHistoryPrice = 35;  // 历史最高价
        optional double lowestHistoryPrice = 36;  // 历史最低价
    	optional Qot_Common.PreAfterMarketData preMarket = 37; //Qot_Common::PreAfterMarketData 盘前数据
    	optional Qot_Common.PreAfterMarketData afterMarket = 38; //Qot_Common::PreAfterMarketData 盘后数据
        optional Qot_Common.PreAfterMarketData overnight = 42; //Qot_Common::PreAfterMarketData 夜盘数据
    	optional int32 secStatus = 39; //Qot_Common::SecurityStatus 股票状态
    	optional double closePrice5Minute = 40; //5分钟收盘价
    }
    
    message Snapshot
    {
    	required SnapshotBasicData basic = 1; //快照基本数据
    	optional EquitySnapshotExData equityExData = 2; //正股快照额外数据
    	optional WarrantSnapshotExData warrantExData = 3; //窝轮快照额外数据
    	optional OptionSnapshotExData optionExData = 4; //期权快照额外数据
    	optional IndexSnapshotExData indexExData = 5; //指数快照额外数据
    	optional PlateSnapshotExData plateExData = 6; //板块快照额外数据
    	optional FutureSnapshotExData futureExData = 7; //期货类型额外数据
    	optional TrustSnapshotExData trustExData = 8; //基金类型额外数据
    }
    
    message S2C
    {
    	repeated Snapshot snapshotList = 1; //股票快照
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
141  
142  
143  
144  
145  
146  
147  
148  
149  
150  
151  
152  
153  
154  
155  
156  
157  
158  
159  
160  
161  
162  
163  
164  
165  
166  
167  
168  
169  
170  
171  
172  
173  
174  
175  
176  
177  
178  
179  
180  
181  
182  
183  
184  
185  
186  
187  
188  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     
> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetSecuritySnapshot(){
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
                    },
                };
                websocket.GetSecuritySnapshot(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("Snapshot: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                    if(retType == RetType.RetType_Succeed){
                        let snapshot = beautify(JSON.stringify(s2c), {
                            indent_size: 2,
                            space_in_empty_paren: true,
                        });
                        console.log(snapshot);
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

*   **Output**

    Snapshot: errCode 0, retMsg , retType 0
    {
      "snapshotList": [{\
        "basic": {\
          "security": {\
            "market": 1,\
            "code": "00700"\
          },\
          "type": 3,\
          "isSuspend": false,\
          "listTime": "2004-06-16",\
          "lotSize": 100,\
          "priceSpread": 0.2,\
          "updateTime": "2021-09-09 16:08:17",\
          "highPrice": 511.5,\
          "openPrice": 509,\
          "lowPrice": 479,\
          "lastClosePrice": 524.5,\
          "curPrice": 480,\
          "volume": "54090872",\
          "turnover": 26716845932,\
          "turnoverRate": 0.563,\
          "listTimestamp": 1087315200,\
          "updateTimestamp": 1631174897,\
          "askPrice": 480.4,\
          "bidPrice": 480,\
          "askVol": "700",\
          "bidVol": "55300",\
          "enableMargin": true,\
          "mortgageRatio": 0,\
          "longMarginInitialRatio": 35,\
          "enableShortSell": true,\
          "shortSellRate": 0.92,\
          "shortAvailableVolume": "3059000",\
          "shortMarginInitialRatio": 50,\
          "amplitude": 6.196,\
          "avgPrice": 493.925,\
          "bidAskRatio": 22.703,\
          "volumeRatio": 1.775,\
          "highest52WeeksPrice": 773.9,\
          "lowest52WeeksPrice": 412.2,\
          "highestHistoryPrice": 773.9,\
          "lowestHistoryPrice": -6.381,\
          "secStatus": 1,\
          "closePrice5Minute": 483.4\
        },\
        "equityExData": {\
          "issuedShares": "9599810645",\
          "issuedMarketVal": 4607909109600,\
          "netAsset": 1016063158288.09,\
          "netProfit": 190383444711.64,\
          "earningsPershare": 19.832,\
          "outstandingShares": "9599810645",\
          "outstandingMarketVal": 4607909109600,\
          "netAssetPershare": 105.842,\
          "eyRate": 0.22,\
          "peRate": 24.203,\
          "pbRate": 4.535,\
          "peTTMRate": 20.532,\
          "dividendTTM": 1.599,\
          "dividendRatioTTM": 0.33,\
          "dividendLFY": 1.599,\
          "dividendLFYRatio": 0.333\
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

接口限制

*   每 30 秒内最多请求 60 次快照。
*   每次请求，接口参数 **股票代码列表** 支持传入的标的数量上限是 400 个。

← [实时经纪队列回调](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html) [获取实时报价](https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html)
 →