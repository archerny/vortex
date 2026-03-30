[#](./quote_quote.md#1495)
 行情定义
========================================================================

[#](./quote_quote.md#4370)
 累积过滤属性
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **StockField**

*   `NONE`
    
    未知
    
*   `CHANGE_RATE`
    
    涨跌幅
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[-10.2, 20.4\] 值区间
    
*   `AMPLITUDE`
    
    振幅
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[0.5, 20.6\] 值区间
    
*   `VOLUME`
    
    日均成交量
    
    *   精确到小数点后 0 位，超出部分会被舍弃
    *   例如填写 \[2000, 70000\] 值区间
    
*   `TURNOVER`
    
    日均成交额
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   例如填写 \[1400, 890000\] 值区间
    
*   `TURNOVER_RATE`
    
    换手率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[2, 30\] 值区间
    

**AccumulateField**

    enum AccumulateField
    {
        AccumulateField_Unknown = 0; // 未知
        AccumulateField_ChangeRate = 1; // 涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10.2,20.4]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        AccumulateField_Amplitude = 2; // 振幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,20.6]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        AccumulateField_Volume = 3; // 日均成交量（精确到小数点后 0 位，超出部分会被舍弃）例如填写[2000,70000]值区间
        AccumulateField_Turnover = 4; // 日均成交额（精确到小数点后 3 位，超出部分会被舍弃）例如填写[1400,890000]值区间
        AccumulateField_TurnoverRate = 5; // 换手率（精确到小数点后 3 位，超出部分会被舍弃）例如填写[2,30]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**AccumulateField**

    enum AccumulateField
    {
        AccumulateField_Unknown = 0; // 未知
        AccumulateField_ChangeRate = 1; // 涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10.2,20.4]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        AccumulateField_Amplitude = 2; // 振幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,20.6]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        AccumulateField_Volume = 3; // 日均成交量（精确到小数点后 0 位，超出部分会被舍弃）例如填写[2000,70000]值区间
        AccumulateField_Turnover = 4; // 日均成交额（精确到小数点后 3 位，超出部分会被舍弃）例如填写[1400,890000]值区间
        AccumulateField_TurnoverRate = 5; // 换手率（精确到小数点后 3 位，超出部分会被舍弃）例如填写[2,30]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**AccumulateField**

    enum AccumulateField
    {
        AccumulateField_Unknown = 0; // 未知
        AccumulateField_ChangeRate = 1; // 涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10.2,20.4]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        AccumulateField_Amplitude = 2; // 振幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,20.6]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        AccumulateField_Volume = 3; // 日均成交量（精确到小数点后 0 位，超出部分会被舍弃）例如填写[2000,70000]值区间
        AccumulateField_Turnover = 4; // 日均成交额（精确到小数点后 3 位，超出部分会被舍弃）例如填写[1400,890000]值区间
        AccumulateField_TurnoverRate = 5; // 换手率（精确到小数点后 3 位，超出部分会被舍弃）例如填写[2,30]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**AccumulateField**

    enum AccumulateField
    {
        AccumulateField_Unknown = 0; // 未知
        AccumulateField_ChangeRate = 1; // 涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10.2,20.4]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        AccumulateField_Amplitude = 2; // 振幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,20.6]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        AccumulateField_Volume = 3; // 日均成交量（精确到小数点后 0 位，超出部分会被舍弃）例如填写[2000,70000]值区间
        AccumulateField_Turnover = 4; // 日均成交额（精确到小数点后 3 位，超出部分会被舍弃）例如填写[1400,890000]值区间
        AccumulateField_TurnoverRate = 5; // 换手率（精确到小数点后 3 位，超出部分会被舍弃）例如填写[2,30]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**AccumulateField**

    enum AccumulateField
    {
        AccumulateField_Unknown = 0; // 未知
        AccumulateField_ChangeRate = 1; // 涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10.2,20.4]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        AccumulateField_Amplitude = 2; // 振幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,20.6]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        AccumulateField_Volume = 3; // 日均成交量（精确到小数点后 0 位，超出部分会被舍弃）例如填写[2000,70000]值区间
        AccumulateField_Turnover = 4; // 日均成交额（精确到小数点后 3 位，超出部分会被舍弃）例如填写[1400,890000]值区间
        AccumulateField_TurnoverRate = 5; // 换手率（精确到小数点后 3 位，超出部分会被舍弃）例如填写[2,30]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

[#](./quote_quote.md#4752)
 资产类别
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **AssetClass**

*   `UNKNOW`
    
    未知
    
*   `STOCK`
    
    股票
    
*   `BOND`
    
    债券
    
*   `COMMODITY`
    
    商品
    
*   `CURRENCY_MARKET`
    
    货币市场
    
*   `FUTURE`
    
    期货
    
*   `SWAP`
    
    掉期（互换）
    

**AssetClass**

    enum AssetClass
    {
        AssetClass_Unknow = 0; //未知
        AssetClass_Stock = 1; //股票
        AssetClass_Bond = 2; //债券
        AssetClass_Commodity = 3; //商品
        AssetClass_CurrencyMarket = 4; //货币市场
        AssetClass_Future = 5; //期货
        AssetClass_Swap = 6; //掉期（互换）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

**AssetClass**

    enum AssetClass
    {
        AssetClass_Unknow = 0; //未知
        AssetClass_Stock = 1; //股票
        AssetClass_Bond = 2; //债券
        AssetClass_Commodity = 3; //商品
        AssetClass_CurrencyMarket = 4; //货币市场
        AssetClass_Future = 5; //期货
        AssetClass_Swap = 6; //掉期（互换）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

**AssetClass**

    enum AssetClass
    {
        AssetClass_Unknow = 0; //未知
        AssetClass_Stock = 1; //股票
        AssetClass_Bond = 2; //债券
        AssetClass_Commodity = 3; //商品
        AssetClass_CurrencyMarket = 4; //货币市场
        AssetClass_Future = 5; //期货
        AssetClass_Swap = 6; //掉期（互换）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

**AssetClass**

    enum AssetClass
    {
        AssetClass_Unknow = 0; //未知
        AssetClass_Stock = 1; //股票
        AssetClass_Bond = 2; //债券
        AssetClass_Commodity = 3; //商品
        AssetClass_CurrencyMarket = 4; //货币市场
        AssetClass_Future = 5; //期货
        AssetClass_Swap = 6; //掉期（互换）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

**AssetClass**

    enum AssetClass
    {
        AssetClass_Unknow = 0; //未知
        AssetClass_Stock = 1; //股票
        AssetClass_Bond = 2; //债券
        AssetClass_Commodity = 3; //商品
        AssetClass_CurrencyMarket = 4; //货币市场
        AssetClass_Future = 5; //期货
        AssetClass_Swap = 6; //掉期（互换）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

[#](./quote_quote.md#1239)
 公司行动
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

**CompanyAct**

    enum CompanyAct
    {
        CompanyAct_None = 0; //无
        CompanyAct_Split = 1; //拆股
        CompanyAct_Join = 2; //合股
        CompanyAct_Bonus = 4; //送股
        CompanyAct_Transfer = 8; //转赠股
        CompanyAct_Allot = 16; //配股    
        CompanyAct_Add = 32; //增发股
        CompanyAct_Dividend = 64; //现金分红
        CompanyAct_SPDividend = 128; //特别股息    
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

**CompanyAct**

    enum CompanyAct
    {
        CompanyAct_None = 0; //无
        CompanyAct_Split = 1; //拆股        
        CompanyAct_Join = 2; //合股
        CompanyAct_Bonus = 4; //送股
        CompanyAct_Transfer = 8; //转赠股
        CompanyAct_Allot = 16; //配股    
        CompanyAct_Add = 32; //增发股
        CompanyAct_Dividend = 64; //现金分红
        CompanyAct_SPDividend = 128; //特别股息    
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

**CompanyAct**

    enum CompanyAct
    {
        CompanyAct_None = 0; //无
        CompanyAct_Split = 1; //拆股        
        CompanyAct_Join = 2; //合股
        CompanyAct_Bonus = 4; //送股
        CompanyAct_Transfer = 8; //转赠股
        CompanyAct_Allot = 16; //配股    
        CompanyAct_Add = 32; //增发股
        CompanyAct_Dividend = 64; //现金分红
        CompanyAct_SPDividend = 128; //特别股息    
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

**CompanyAct**

    enum CompanyAct
    {
        CompanyAct_None = 0; //无
        CompanyAct_Split = 1; //拆股        
        CompanyAct_Join = 2; //合股
        CompanyAct_Bonus = 4; //送股
        CompanyAct_Transfer = 8; //转赠股
        CompanyAct_Allot = 16; //配股    
        CompanyAct_Add = 32; //增发股
        CompanyAct_Dividend = 64; //现金分红
        CompanyAct_SPDividend = 128; //特别股息    
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

**CompanyAct**

    enum CompanyAct
    {
        CompanyAct_None = 0; //无
        CompanyAct_Split = 1; //拆股        
        CompanyAct_Join = 2; //合股
        CompanyAct_Bonus = 4; //送股
        CompanyAct_Transfer = 8; //转赠股
        CompanyAct_Allot = 16; //配股    
        CompanyAct_Add = 32; //增发股
        CompanyAct_Dividend = 64; //现金分红
        CompanyAct_SPDividend = 128; //特别股息    
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

[#](./quote_quote.md#1965)
 暗盘状态
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **DarkStatus**

*   `NONE`
    
    无暗盘交易
    
*   `TRADING`
    
    暗盘交易中
    
*   `END`
    
    暗盘交易结束
    

**DarkStatus**

    enum DarkStatus
    {
        DarkStatus_None = 0; //无暗盘交易
        DarkStatus_Trading = 1; //暗盘交易中
        DarkStatus_End = 2; //暗盘交易结束
    }
    

1  
2  
3  
4  
5  
6  

**DarkStatus**

    enum DarkStatus
    {
        DarkStatus_None = 0; //无暗盘交易
        DarkStatus_Trading = 1; //暗盘交易中
        DarkStatus_End = 2; //暗盘交易结束
    }
    

1  
2  
3  
4  
5  
6  

**DarkStatus**

    enum DarkStatus
    {
        DarkStatus_None = 0; //无暗盘交易
        DarkStatus_Trading = 1; //暗盘交易中
        DarkStatus_End = 2; //暗盘交易结束
    }
    

1  
2  
3  
4  
5  
6  

**DarkStatus**

    enum DarkStatus
    {
        DarkStatus_None = 0; //无暗盘交易
        DarkStatus_Trading = 1; //暗盘交易中
        DarkStatus_End = 2; //暗盘交易结束
    }
    

1  
2  
3  
4  
5  
6  

**DarkStatus**

    enum DarkStatus
    {
        DarkStatus_None = 0; //无暗盘交易
        DarkStatus_Trading = 1; //暗盘交易中
        DarkStatus_End = 2; //暗盘交易结束
    }
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#8542)
 财务过滤属性
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **StockField**

*   `NONE`
    
    未知
    
*   `NET_PROFIT`
    
    净利润
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   例如填写 \[100000000, 2500000000\] 值区间
    
*   `NET_PROFIX_GROWTH`
    
    净利润增长率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[-10, 300\] 值区间
    
*   `SUM_OF_BUSINESS`
    
    营业收入
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   例如填写 \[100000000, 6400000000\] 值区间
    
*   `SUM_OF_BUSINESS_GROWTH`
    
    营收同比增长率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[-5, 200\] 值区间
    
*   `NET_PROFIT_RATE`
    
    净利率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[10, 113\] 值区间
    
*   `GROSS_PROFIT_RATE`
    
    毛利率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[4, 65\] 值区间
    
*   `DEBT_ASSET_RATE`
    
    资产负债率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[5, 470\] 值区间
    
*   `RETURN_ON_EQUITY_RATE`
    
    净资产收益率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[20, 230\] 值区间
    
*   `ROIC`
    
    投入资本回报率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `ROA_TTM`
    
    资产回报率 TTM
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   仅适用于年报
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `EBIT_TTM`
    
    息税前利润 TTM
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[1000000000, 1000000000\] 值区间
    
*   `EBITDA`
    
    税息折旧及摊销前利润
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   单位：元
    *   例如填写 \[1000000000, 1000000000\] 值区间
    
*   `OPERATING_MARGIN_TTM`
    
    营业利润率 TTM
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   仅适用于年报
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `EBIT_MARGIN`
    
    EBIT 利润率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `EBITDA_MARGIN`
    
    EBITDA 利润率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `FINANCIAL_COST_RATE`
    
    财务成本率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `OPERATING_PROFIT_TTM`
    
    营业利润 TTM
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   单位：元
    *   仅适用于年报
    *   例如填写 \[1000000000, 1000000000\] 值区间
    
*   `SHAREHOLDER_NET_PROFIT_TTM`
    
    归属于母公司的净利润
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   单位：元
    *   仅适用于年报
    *   例如填写 \[1000000000, 1000000000\] 值区间
    
*   `NET_PROFIT_CASH_COVER_TTM`
    
    盈利中的现金收入比例
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   仅适用于年报
    *   例如填写 \[1.0, 60.0\] 值区间
    
*   `CURRENT_RATIO`
    
    流动比率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[100, 250\] 值区间
    
*   `QUICK_RATIO`
    
    速动比率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[100, 250\] 值区间
    
*   `CURRENT_ASSET_RATIO`
    
    流动资产率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[100, 250\] 值区间
    
*   `CURRENT_DEBT_RATIO`
    
    流动负债率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[100, 250\] 值区间
    
*   `EQUITY_MULTIPLIER`
    
    权益乘数
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   例如填写 \[100, 180\] 值区间
    
*   `PROPERTY_RATIO`
    
    产权比率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[50, 100\] 值区间
    
*   `CASH_AND_CASH_EQUIVALENTS`
    
    现金和现金等价物
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   单位：元
    *   例如填写 \[1000000000, 1000000000\] 值区间
    
*   `TOTAL_ASSET_TURNOVER`
    
    总资产周转率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[50, 100\] 值区间
    
*   `FIXED_ASSET_TURNOVER`
    
    固定资产周转率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[50, 100\] 值区间
    
*   `INVENTORY_TURNOVER`
    
    存货周转率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[50, 100\] 值区间
    
*   `OPERATING_CASH_FLOW_TTM`
    
    经营活动现金流 TTM
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   单位：元
    *   仅适用于年报
    *   例如填写 \[1000000000, 1000000000\] 值区间
    
*   `ACCOUNTS_RECEIVABLE`
    
    应收账款净额
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   单位：元。
    *   例如填写 \[1000000000, 1000000000\] 值区间
    
*   `EBIT_GROWTH_RATE`
    
    EBIT 同比增长率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `OPERATING_PROFIT_GROWTH_RATE`
    
    营业利润同比增长率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `TOTAL_ASSETS_GROWTH_RATE`
    
    总资产同比增长率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `PROFIT_TO_SHAREHOLDERS_GROWTH_RATE`
    
    归母净利润同比增长率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `PROFIT_BEFORE_TAX_GROWTH_RATE`
    
    总利润同比增长率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `EPS_GROWTH_RATE`
    
    EPS 同比增长率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `ROE_GROWTH_RATE`
    
    ROE 同比增长率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `ROIC_GROWTH_RATE`
    
    ROIC 同比增长率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `NOCF_GROWTH_RATE`
    
    经营现金流同比增长率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `NOCF_PER_SHARE_GROWTH_RATE`
    
    每股经营现金流同比增长率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[1.0, 10.0\] 值区间
    
*   `OPERATING_REVENUE_CASH_COVER`
    
    经营现金收入比
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[10, 100\] 值区间
    
*   `OPERATING_PROFIT_TO_TOTAL_PROFIT`
    
    营业利润占比
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。
    *   例如填写 \[10, 100\] 值区间
    
*   `BASIC_EPS`
    
    基本每股收益
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   单位：元
    *   例如填写 \[0.1, 10\] 值区间
    
*   `DILUTED_EPS`
    
    稀释每股收益
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   单位：元
    *   例如填写 \[0.1, 10\] 值区间
    
*   `NOCF_PER_SHARE`
    
    每股经营现金净流量
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   单位：元
    *   例如填写 \[0.1, 10\] 值区间
    

**FinancialField**

    enum FinancialField
    {
        // 基础财务属性
        FinancialField_Unknown = 0; // 未知
        FinancialField_NetProfit = 1; // 净利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[100000000,2500000000]值区间
        FinancialField_NetProfitGrowth = 2; // 净利润增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10,300]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_SumOfBusiness = 3; // 营业收入 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[100000000,6400000000]值区间
        FinancialField_SumOfBusinessGrowth = 4; // 营收同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[-5,200]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NetProfitRate = 5; // 净利率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,113]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_GrossProfitRate = 6; // 毛利率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[4,65]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_DebtAssetsRate = 7; // 资产负债率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[5,470]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ReturnOnEquityRate = 8; // 净资产收益率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[20,230]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        
        // 盈利能力属性
        FinancialField_ROIC = 9; // 投入资本回报率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROATTM = 10; // 资产回报率(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        FinancialField_EBITTTM = 11; // 息税前利润(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元。仅适用于年报。）
        FinancialField_EBITDA = 12; // 税息折旧及摊销前利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）
        FinancialField_OperatingMarginTTM = 13; // 营业利润率(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        FinancialField_EBITMargin = 14; // EBIT 利润率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EBITDAMargin  = 15; // EBITDA 利润率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_FinancialCostRate = 16; // 财务成本率（精确到小数点后 3 位，超出部分会被舍弃） 例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitTTM  = 17; // 营业利润(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 （单位：元。仅适用于年报。）
        FinancialField_ShareholderNetProfitTTM = 18; // 归属于母公司的净利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 （单位：元。仅适用于年报。）
        FinancialField_NetProfitCashCoverTTM = 19; // 盈利中的现金收入比例 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,60.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        
        // 偿债能力属性
        FinancialField_CurrentRatio = 20; // 流动比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,250] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_QuickRatio = 21; // 速动比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,250] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）    
        
        // 清债能力属性
        FinancialField_CurrentAssetRatio = 22; // 流动资产率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_CurrentDebtRatio = 23; // 流动负债率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EquityMultiplier = 24; // 权益乘数 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,180] 值区间
        FinancialField_PropertyRatio = 25; // 产权比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%） 
        FinancialField_CashAndCashEquivalents = 26; // 现金和现金等价 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）    
        
        // 运营能力属性
        FinancialField_TotalAssetTurnover = 27; // 总资产周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_FixedAssetTurnover = 28; // 固定资产周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_InventoryTurnover = 29; // 存货周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingCashFlowTTM = 30; // 经营活动现金流(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元。仅适用于年报。）
        FinancialField_AccountsReceivable = 31; // 应收账款净额 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 例如填写 [1000000000,1000000000] 值区间 （单位：元）    
        
        // 成长能力属性
        FinancialField_EBITGrowthRate = 32 ; // EBIT 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitGrowthRate = 33; // 营业利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_TotalAssetsGrowthRate = 34; // 总资产同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ProfitToShareholdersGrowthRate = 35; // 归母净利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ProfitBeforeTaxGrowthRate = 36; // 总利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EPSGrowthRate = 37; // EPS 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROEGrowthRate = 38; // ROE 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROICGrowthRate = 39; // ROIC 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NOCFGrowthRate = 40; // 经营现金流同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NOCFPerShareGrowthRate = 41; // 每股经营现金流同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    
        // 现金流属性
        FinancialField_OperatingRevenueCashCover = 42; // 经营现金收入比 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitToTotalProfit = 43; // 营业利润占比 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）     
    
        // 市场表现属性
        FinancialField_BasicEPS = 44; // 基本每股收益 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）
        FinancialField_DilutedEPS = 45; // 稀释每股收益 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）
        FinancialField_NOCFPerShare = 46; // 每股经营现金净流量 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）    
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**FinancialField**

    enum FinancialField
    {
        // 基础财务属性
        FinancialField_Unknown = 0; // 未知
        FinancialField_NetProfit = 1; // 净利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[100000000,2500000000]值区间
        FinancialField_NetProfitGrowth = 2; // 净利润增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10,300]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_SumOfBusiness = 3; // 营业收入 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[100000000,6400000000]值区间
        FinancialField_SumOfBusinessGrowth = 4; // 营收同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[-5,200]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NetProfitRate = 5; // 净利率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,113]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_GrossProfitRate = 6; // 毛利率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[4,65]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_DebtAssetsRate = 7; // 资产负债率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[5,470]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ReturnOnEquityRate = 8; // 净资产收益率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[20,230]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        
        // 盈利能力属性
        FinancialField_ROIC = 9; // 投入资本回报率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROATTM = 10; // 资产回报率(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        FinancialField_EBITTTM = 11; // 息税前利润(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元。仅适用于年报。）
        FinancialField_EBITDA = 12; // 税息折旧及摊销前利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）
        FinancialField_OperatingMarginTTM = 13; // 营业利润率(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        FinancialField_EBITMargin = 14; // EBIT 利润率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EBITDAMargin  = 15; // EBITDA 利润率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_FinancialCostRate = 16; // 财务成本率（精确到小数点后 3 位，超出部分会被舍弃） 例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitTTM  = 17; // 营业利润(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 （单位：元。仅适用于年报。）
        FinancialField_ShareholderNetProfitTTM = 18; // 归属于母公司的净利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 （单位：元。仅适用于年报。）
        FinancialField_NetProfitCashCoverTTM = 19; // 盈利中的现金收入比例 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,60.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        
        // 偿债能力属性
        FinancialField_CurrentRatio = 20; // 流动比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,250] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_QuickRatio = 21; // 速动比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,250] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）    
        
        // 清债能力属性
        FinancialField_CurrentAssetRatio = 22; // 流动资产率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_CurrentDebtRatio = 23; // 流动负债率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EquityMultiplier = 24; // 权益乘数 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,180] 值区间
        FinancialField_PropertyRatio = 25; // 产权比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%） 
        FinancialField_CashAndCashEquivalents = 26; // 现金和现金等价 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）    
        
        // 运营能力属性
        FinancialField_TotalAssetTurnover = 27; // 总资产周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_FixedAssetTurnover = 28; // 固定资产周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_InventoryTurnover = 29; // 存货周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingCashFlowTTM = 30; // 经营活动现金流(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元。仅适用于年报。）
        FinancialField_AccountsReceivable = 31; // 应收账款净额 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 例如填写 [1000000000,1000000000] 值区间 （单位：元）    
        
        // 成长能力属性
        FinancialField_EBITGrowthRate = 32 ; // EBIT 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitGrowthRate = 33; // 营业利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_TotalAssetsGrowthRate = 34; // 总资产同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ProfitToShareholdersGrowthRate = 35; // 归母净利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ProfitBeforeTaxGrowthRate = 36; // 总利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EPSGrowthRate = 37; // EPS 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROEGrowthRate = 38; // ROE 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROICGrowthRate = 39; // ROIC 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NOCFGrowthRate = 40; // 经营现金流同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NOCFPerShareGrowthRate = 41; // 每股经营现金流同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    
        // 现金流属性
        FinancialField_OperatingRevenueCashCover = 42; // 经营现金收入比 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitToTotalProfit = 43; // 营业利润占比 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）     
    
        // 市场表现属性
        FinancialField_BasicEPS = 44; // 基本每股收益 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）
        FinancialField_DilutedEPS = 45; // 稀释每股收益 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）
        FinancialField_NOCFPerShare = 46; // 每股经营现金净流量 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）    
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**FinancialField**

    enum FinancialField
    {
        // 基础财务属性
        FinancialField_Unknown = 0; // 未知
        FinancialField_NetProfit = 1; // 净利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[100000000,2500000000]值区间
        FinancialField_NetProfitGrowth = 2; // 净利润增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10,300]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_SumOfBusiness = 3; // 营业收入 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[100000000,6400000000]值区间
        FinancialField_SumOfBusinessGrowth = 4; // 营收同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[-5,200]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NetProfitRate = 5; // 净利率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,113]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_GrossProfitRate = 6; // 毛利率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[4,65]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_DebtAssetsRate = 7; // 资产负债率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[5,470]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ReturnOnEquityRate = 8; // 净资产收益率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[20,230]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        
        // 盈利能力属性
        FinancialField_ROIC = 9; // 投入资本回报率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROATTM = 10; // 资产回报率(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        FinancialField_EBITTTM = 11; // 息税前利润(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元。仅适用于年报。）
        FinancialField_EBITDA = 12; // 税息折旧及摊销前利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）
        FinancialField_OperatingMarginTTM = 13; // 营业利润率(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        FinancialField_EBITMargin = 14; // EBIT 利润率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EBITDAMargin  = 15; // EBITDA 利润率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_FinancialCostRate = 16; // 财务成本率（精确到小数点后 3 位，超出部分会被舍弃） 例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitTTM  = 17; // 营业利润(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 （单位：元。仅适用于年报。）
        FinancialField_ShareholderNetProfitTTM = 18; // 归属于母公司的净利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 （单位：元。仅适用于年报。）
        FinancialField_NetProfitCashCoverTTM = 19; // 盈利中的现金收入比例 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,60.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        
        // 偿债能力属性
        FinancialField_CurrentRatio = 20; // 流动比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,250] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_QuickRatio = 21; // 速动比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,250] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）    
        
        // 清债能力属性
        FinancialField_CurrentAssetRatio = 22; // 流动资产率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_CurrentDebtRatio = 23; // 流动负债率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EquityMultiplier = 24; // 权益乘数 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,180] 值区间
        FinancialField_PropertyRatio = 25; // 产权比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%） 
        FinancialField_CashAndCashEquivalents = 26; // 现金和现金等价 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）    
        
        // 运营能力属性
        FinancialField_TotalAssetTurnover = 27; // 总资产周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_FixedAssetTurnover = 28; // 固定资产周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_InventoryTurnover = 29; // 存货周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingCashFlowTTM = 30; // 经营活动现金流(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元。仅适用于年报。）
        FinancialField_AccountsReceivable = 31; // 应收账款净额 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 例如填写 [1000000000,1000000000] 值区间 （单位：元）    
        
        // 成长能力属性
        FinancialField_EBITGrowthRate = 32 ; // EBIT 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitGrowthRate = 33; // 营业利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_TotalAssetsGrowthRate = 34; // 总资产同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ProfitToShareholdersGrowthRate = 35; // 归母净利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ProfitBeforeTaxGrowthRate = 36; // 总利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EPSGrowthRate = 37; // EPS 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROEGrowthRate = 38; // ROE 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROICGrowthRate = 39; // ROIC 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NOCFGrowthRate = 40; // 经营现金流同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NOCFPerShareGrowthRate = 41; // 每股经营现金流同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    
        // 现金流属性
        FinancialField_OperatingRevenueCashCover = 42; // 经营现金收入比 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitToTotalProfit = 43; // 营业利润占比 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）     
    
        // 市场表现属性
        FinancialField_BasicEPS = 44; // 基本每股收益 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）
        FinancialField_DilutedEPS = 45; // 稀释每股收益 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）
        FinancialField_NOCFPerShare = 46; // 每股经营现金净流量 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）    
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**FinancialField**

    enum FinancialField
    {
        // 基础财务属性
        FinancialField_Unknown = 0; // 未知
        FinancialField_NetProfit = 1; // 净利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[100000000,2500000000]值区间
        FinancialField_NetProfitGrowth = 2; // 净利润增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10,300]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_SumOfBusiness = 3; // 营业收入 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[100000000,6400000000]值区间
        FinancialField_SumOfBusinessGrowth = 4; // 营收同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[-5,200]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NetProfitRate = 5; // 净利率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,113]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_GrossProfitRate = 6; // 毛利率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[4,65]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_DebtAssetsRate = 7; // 资产负债率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[5,470]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ReturnOnEquityRate = 8; // 净资产收益率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[20,230]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        
        // 盈利能力属性
        FinancialField_ROIC = 9; // 投入资本回报率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROATTM = 10; // 资产回报率(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        FinancialField_EBITTTM = 11; // 息税前利润(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元。仅适用于年报。）
        FinancialField_EBITDA = 12; // 税息折旧及摊销前利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）
        FinancialField_OperatingMarginTTM = 13; // 营业利润率(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        FinancialField_EBITMargin = 14; // EBIT 利润率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EBITDAMargin  = 15; // EBITDA 利润率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_FinancialCostRate = 16; // 财务成本率（精确到小数点后 3 位，超出部分会被舍弃） 例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitTTM  = 17; // 营业利润(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 （单位：元。仅适用于年报。）
        FinancialField_ShareholderNetProfitTTM = 18; // 归属于母公司的净利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 （单位：元。仅适用于年报。）
        FinancialField_NetProfitCashCoverTTM = 19; // 盈利中的现金收入比例 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,60.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        
        // 偿债能力属性
        FinancialField_CurrentRatio = 20; // 流动比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,250] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_QuickRatio = 21; // 速动比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,250] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）    
        
        // 清债能力属性
        FinancialField_CurrentAssetRatio = 22; // 流动资产率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_CurrentDebtRatio = 23; // 流动负债率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EquityMultiplier = 24; // 权益乘数 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,180] 值区间
        FinancialField_PropertyRatio = 25; // 产权比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%） 
        FinancialField_CashAndCashEquivalents = 26; // 现金和现金等价 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）    
        
        // 运营能力属性
        FinancialField_TotalAssetTurnover = 27; // 总资产周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_FixedAssetTurnover = 28; // 固定资产周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_InventoryTurnover = 29; // 存货周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingCashFlowTTM = 30; // 经营活动现金流(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元。仅适用于年报。）
        FinancialField_AccountsReceivable = 31; // 应收账款净额 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 例如填写 [1000000000,1000000000] 值区间 （单位：元）    
        
        // 成长能力属性
        FinancialField_EBITGrowthRate = 32 ; // EBIT 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitGrowthRate = 33; // 营业利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_TotalAssetsGrowthRate = 34; // 总资产同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ProfitToShareholdersGrowthRate = 35; // 归母净利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ProfitBeforeTaxGrowthRate = 36; // 总利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EPSGrowthRate = 37; // EPS 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROEGrowthRate = 38; // ROE 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROICGrowthRate = 39; // ROIC 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NOCFGrowthRate = 40; // 经营现金流同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NOCFPerShareGrowthRate = 41; // 每股经营现金流同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    
        // 现金流属性
        FinancialField_OperatingRevenueCashCover = 42; // 经营现金收入比 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitToTotalProfit = 43; // 营业利润占比 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）     
    
        // 市场表现属性
        FinancialField_BasicEPS = 44; // 基本每股收益 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）
        FinancialField_DilutedEPS = 45; // 稀释每股收益 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）
        FinancialField_NOCFPerShare = 46; // 每股经营现金净流量 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）    
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**FinancialField**

    enum FinancialField
    {
        // 基础财务属性
        FinancialField_Unknown = 0; // 未知
        FinancialField_NetProfit = 1; // 净利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[100000000,2500000000]值区间
        FinancialField_NetProfitGrowth = 2; // 净利润增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10,300]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_SumOfBusiness = 3; // 营业收入 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[100000000,6400000000]值区间
        FinancialField_SumOfBusinessGrowth = 4; // 营收同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[-5,200]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NetProfitRate = 5; // 净利率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,113]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_GrossProfitRate = 6; // 毛利率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[4,65]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_DebtAssetsRate = 7; // 资产负债率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[5,470]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ReturnOnEquityRate = 8; // 净资产收益率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[20,230]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        
        // 盈利能力属性
        FinancialField_ROIC = 9; // 投入资本回报率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROATTM = 10; // 资产回报率(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        FinancialField_EBITTTM = 11; // 息税前利润(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元。仅适用于年报。）
        FinancialField_EBITDA = 12; // 税息折旧及摊销前利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）
        FinancialField_OperatingMarginTTM = 13; // 营业利润率(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        FinancialField_EBITMargin = 14; // EBIT 利润率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EBITDAMargin  = 15; // EBITDA 利润率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_FinancialCostRate = 16; // 财务成本率（精确到小数点后 3 位，超出部分会被舍弃） 例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitTTM  = 17; // 营业利润(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 （单位：元。仅适用于年报。）
        FinancialField_ShareholderNetProfitTTM = 18; // 归属于母公司的净利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 （单位：元。仅适用于年报。）
        FinancialField_NetProfitCashCoverTTM = 19; // 盈利中的现金收入比例 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,60.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）
        
        // 偿债能力属性
        FinancialField_CurrentRatio = 20; // 流动比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,250] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_QuickRatio = 21; // 速动比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,250] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）    
        
        // 清债能力属性
        FinancialField_CurrentAssetRatio = 22; // 流动资产率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_CurrentDebtRatio = 23; // 流动负债率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EquityMultiplier = 24; // 权益乘数 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,180] 值区间
        FinancialField_PropertyRatio = 25; // 产权比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%） 
        FinancialField_CashAndCashEquivalents = 26; // 现金和现金等价 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）    
        
        // 运营能力属性
        FinancialField_TotalAssetTurnover = 27; // 总资产周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_FixedAssetTurnover = 28; // 固定资产周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_InventoryTurnover = 29; // 存货周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingCashFlowTTM = 30; // 经营活动现金流(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元。仅适用于年报。）
        FinancialField_AccountsReceivable = 31; // 应收账款净额 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 例如填写 [1000000000,1000000000] 值区间 （单位：元）    
        
        // 成长能力属性
        FinancialField_EBITGrowthRate = 32 ; // EBIT 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitGrowthRate = 33; // 营业利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_TotalAssetsGrowthRate = 34; // 总资产同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ProfitToShareholdersGrowthRate = 35; // 归母净利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ProfitBeforeTaxGrowthRate = 36; // 总利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_EPSGrowthRate = 37; // EPS 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROEGrowthRate = 38; // ROE 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_ROICGrowthRate = 39; // ROIC 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NOCFGrowthRate = 40; // 经营现金流同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_NOCFPerShareGrowthRate = 41; // 每股经营现金流同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    
        // 现金流属性
        FinancialField_OperatingRevenueCashCover = 42; // 经营现金收入比 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        FinancialField_OperatingProfitToTotalProfit = 43; // 营业利润占比 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）     
    
        // 市场表现属性
        FinancialField_BasicEPS = 44; // 基本每股收益 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）
        FinancialField_DilutedEPS = 45; // 稀释每股收益 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）
        FinancialField_NOCFPerShare = 46; // 每股经营现金净流量 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）    
    }
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#2253)
 财务过滤属性周期
----------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **FinancialQuarter**

*   `NONE`
    
    未知
    
*   `ANNUAL`
    
    年报
    
*   `FIRST_QUARTER`
    
    一季报
    
*   `INTERIM`
    
    中报
    
*   `THIRD_QUARTER`
    
    三季报
    
*   `MOST_RECENT_QUARTER`
    
    最近季报
    

**FinancialQuarter**

    enum FinancialQuarter
    {
        FinancialQuarter_Unknown = 0; // 未知
        FinancialQuarter_Annual = 1; // 年报
        FinancialQuarter_FirstQuarter = 2; // 一季报
        FinancialQuarter_Interim = 3; // 中报
        FinancialQuarter_ThirdQuarter = 4; // 三季报
        FinancialQuarter_MostRecentQuarter = 5; // 最近季报
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**FinancialQuarter**

    enum FinancialQuarter
    {
        FinancialQuarter_Unknown = 0; // 未知
        FinancialQuarter_Annual = 1; // 年报
        FinancialQuarter_FirstQuarter = 2; // 一季报
        FinancialQuarter_Interim = 3; // 中报
        FinancialQuarter_ThirdQuarter = 4; // 三季报
        FinancialQuarter_MostRecentQuarter = 5; // 最近季报
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**FinancialQuarter**

    enum FinancialQuarter
    {
        FinancialQuarter_Unknown = 0; // 未知
        FinancialQuarter_Annual = 1; // 年报
        FinancialQuarter_FirstQuarter = 2; // 一季报
        FinancialQuarter_Interim = 3; // 中报
        FinancialQuarter_ThirdQuarter = 4; // 三季报
        FinancialQuarter_MostRecentQuarter = 5; // 最近季报
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**FinancialQuarter**

    enum FinancialQuarter
    {
        FinancialQuarter_Unknown = 0; // 未知
        FinancialQuarter_Annual = 1; // 年报
        FinancialQuarter_FirstQuarter = 2; // 一季报
        FinancialQuarter_Interim = 3; // 中报
        FinancialQuarter_ThirdQuarter = 4; // 三季报
        FinancialQuarter_MostRecentQuarter = 5; // 最近季报
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**FinancialQuarter**

    enum FinancialQuarter
    {
        FinancialQuarter_Unknown = 0; // 未知
        FinancialQuarter_Annual = 1; // 年报
        FinancialQuarter_FirstQuarter = 2; // 一季报
        FinancialQuarter_Interim = 3; // 中报
        FinancialQuarter_ThirdQuarter = 4; // 三季报
        FinancialQuarter_MostRecentQuarter = 5; // 最近季报
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

[#](./quote_quote.md#2057)
 自定义技术指标属性
-----------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **StockField**

*   `NONE`
    
    未知
    
*   `PRICE`
    
    最新价格
    
*   `MA`
    
    简单均线
    
*   `MA5`
    
    5日简单均线（不建议使用）
    
*   `MA10`
    
    10日简单均线（不建议使用）
    
*   `MA20`
    
    20日简单均线（不建议使用）
    
*   `MA30`
    
    30日简单均线（不建议使用）
    
*   `MA60`
    
    60日简单均线（不建议使用）
    
*   `MA120`
    
    120日简单均线（不建议使用）
    
*   `MA250`
    
    250日简单均线（不建议使用）
    
*   `RSI`
    
    RSI
    
    指标参数的默认值为\[12\]
    
*   `EMA`
    
    指数移动均线
    
*   `EMA5`
    
    5日指数移动均线（不建议使用）
    
*   `EMA10`
    
    10日指数移动均线（不建议使用）
    
*   `EMA20`
    
    20日指数移动均线（不建议使用）
    
*   `EMA30`
    
    30日指数移动均线（不建议使用）
    
*   `EMA60`
    
    60日指数移动均线（不建议使用）
    
*   `EMA120`
    
    120日指数移动均线（不建议使用）
    
*   `EMA250`
    
    250日指数移动均线（不建议使用）
    
*   `KDJ_K`
    
    KDJ 指标的 K 值
    
    指标参数需要根据 KDJ 进行传参。不传则默认为 \[9,3,3\]
    
*   `KDJ_D`
    
    KDJ 指标的 D 值
    
    指标参数需要根据 KDJ 进行传参。不传则默认为 \[9,3,3\]
    
*   `KDJ_J`
    
    KDJ 指标的 J 值
    
    指标参数需要根据 KDJ 进行传参。不传则默认为 \[9,3,3\]
    
*   `MACD_DIFF`
    
    MACD 指标的 DIFF 值
    
    指标参数需要根据 MACD 进行传参。不传则默认为 \[12,26,9\]
    
*   `MACD_DEA`
    
    MACD 指标的 DEA 值
    
    指标参数需要根据 MACD 进行传参。不传则默认为 \[12,26,9\]
    
*   `MACD`
    
    MACD
    
    指标参数需要根据 MACD 进行传参。不传则默认为 \[12,26,9\]
    
*   `BOLL_UPPER`
    
    BOLL 指标的 UPPER 值
    
    指标参数需要根据 BOLL 进行传参。不传则默认为 \[20,2\]
    
*   `BOLL_MIDDLER`
    
    BOLL 指标的 MIDDLER 值
    
    指标参数需要根据 BOLL 进行传参。不传则默认为 \[20,2\]
    
*   `BOLL_LOWER`
    
    BOLL 指标的 LOWER 值
    
    指标参数需要根据 BOLL 进行传参。不传则默认为 \[20,2\]
    
*   `VALUE`
    
    自定义数值（stock\_field1 不支持此字段）
    

**CustomIndicatorField**

    enum CustomIndicatorField
    {
        CustomIndicatorField_Unknown = 0; // 未知
        CustomIndicatorField_Price = 1; // 最新价格 
        CustomIndicatorField_MA5 = 2; // 5日简单均线（不建议使用）
        CustomIndicatorField_MA10 = 3; // 10日简单均线（不建议使用）
        CustomIndicatorField_MA20 = 4; // 20日简单均线（不建议使用） 
        CustomIndicatorField_MA30 = 5; // 30日简单均线（不建议使用） 
        CustomIndicatorField_MA60 = 6; // 60日简单均线（不建议使用） 
        CustomIndicatorField_MA120 = 7; // 120日简单均线（不建议使用）
        CustomIndicatorField_MA250 = 8; // 250日简单均线（不建议使用）
        CustomIndicatorField_RSI = 9; // RSI 指标参数的默认值为[12]
        CustomIndicatorField_EMA5 = 10; // 5日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA10 = 11; // 10日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA20 = 12; // 20日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA30 = 13; // 30日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA60 = 14; // 60日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA120 = 15; // 120日指数移动均线（不建议使用）
        CustomIndicatorField_EMA250 = 16; // 250日指数移动均线（不建议使用）
        CustomIndicatorField_Value = 17; // 自定义数值（stock_field1不支持此字段）
        CustomIndicatorField_MA = 30; // 简单均线
    	CustomIndicatorField_EMA = 40; // 指数移动均线
    	CustomIndicatorField_KDJ_K = 50; // KDJ 指标的 K 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]
    	CustomIndicatorField_KDJ_D = 51; // KDJ 指标的 D 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]
    	CustomIndicatorField_KDJ_J = 52; // KDJ 指标的 J 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]	
    	CustomIndicatorField_MACD_DIFF = 60; // MACD 指标的 DIFF 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_MACD_DEA = 61; // MACD 指标的 DEA 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_MACD = 62; // MACD 指标的 MACD 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_BOLL_UPPER = 70; // BOLL 指标的 UPPER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    	CustomIndicatorField_BOLL_MIDDLER = 71; // BOLL 指标的 MIDDLER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    	CustomIndicatorField_BOLL_LOWER = 72; // BOLL 指标的 LOWER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**CustomIndicatorField**

    enum CustomIndicatorField
    {
        CustomIndicatorField_Unknown = 0; // 未知
        CustomIndicatorField_Price = 1; // 最新价格 
        CustomIndicatorField_MA5 = 2; // 5日简单均线（不建议使用）
        CustomIndicatorField_MA10 = 3; // 10日简单均线（不建议使用）
        CustomIndicatorField_MA20 = 4; // 20日简单均线（不建议使用） 
        CustomIndicatorField_MA30 = 5; // 30日简单均线（不建议使用） 
        CustomIndicatorField_MA60 = 6; // 60日简单均线（不建议使用） 
        CustomIndicatorField_MA120 = 7; // 120日简单均线（不建议使用）
        CustomIndicatorField_MA250 = 8; // 250日简单均线（不建议使用）
        CustomIndicatorField_RSI = 9; // RSI 指标参数的默认值为[12]
        CustomIndicatorField_EMA5 = 10; // 5日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA10 = 11; // 10日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA20 = 12; // 20日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA30 = 13; // 30日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA60 = 14; // 60日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA120 = 15; // 120日指数移动均线（不建议使用）
        CustomIndicatorField_EMA250 = 16; // 250日指数移动均线（不建议使用）
        CustomIndicatorField_Value = 17; // 自定义数值（stock_field1不支持此字段）
        CustomIndicatorField_MA = 30; // 简单均线
    	CustomIndicatorField_EMA = 40; // 指数移动均线
    	CustomIndicatorField_KDJ_K = 50; // KDJ 指标的 K 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]
    	CustomIndicatorField_KDJ_D = 51; // KDJ 指标的 D 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]
    	CustomIndicatorField_KDJ_J = 52; // KDJ 指标的 J 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]	
    	CustomIndicatorField_MACD_DIFF = 60; // MACD 指标的 DIFF 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_MACD_DEA = 61; // MACD 指标的 DEA 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_MACD = 62; // MACD 指标的 MACD 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_BOLL_UPPER = 70; // BOLL 指标的 UPPER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    	CustomIndicatorField_BOLL_MIDDLER = 71; // BOLL 指标的 MIDDLER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    	CustomIndicatorField_BOLL_LOWER = 72; // BOLL 指标的 LOWER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**CustomIndicatorField**

    enum CustomIndicatorField
    {
        CustomIndicatorField_Unknown = 0; // 未知
        CustomIndicatorField_Price = 1; // 最新价格 
        CustomIndicatorField_MA5 = 2; // 5日简单均线（不建议使用）
        CustomIndicatorField_MA10 = 3; // 10日简单均线（不建议使用）
        CustomIndicatorField_MA20 = 4; // 20日简单均线（不建议使用） 
        CustomIndicatorField_MA30 = 5; // 30日简单均线（不建议使用） 
        CustomIndicatorField_MA60 = 6; // 60日简单均线（不建议使用） 
        CustomIndicatorField_MA120 = 7; // 120日简单均线（不建议使用）
        CustomIndicatorField_MA250 = 8; // 250日简单均线（不建议使用）
        CustomIndicatorField_RSI = 9; // RSI 指标参数的默认值为[12]
        CustomIndicatorField_EMA5 = 10; // 5日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA10 = 11; // 10日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA20 = 12; // 20日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA30 = 13; // 30日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA60 = 14; // 60日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA120 = 15; // 120日指数移动均线（不建议使用）
        CustomIndicatorField_EMA250 = 16; // 250日指数移动均线（不建议使用）
        CustomIndicatorField_Value = 17; // 自定义数值（stock_field1不支持此字段）
        CustomIndicatorField_MA = 30; // 简单均线
    	CustomIndicatorField_EMA = 40; // 指数移动均线
    	CustomIndicatorField_KDJ_K = 50; // KDJ 指标的 K 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]
    	CustomIndicatorField_KDJ_D = 51; // KDJ 指标的 D 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]
    	CustomIndicatorField_KDJ_J = 52; // KDJ 指标的 J 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]	
    	CustomIndicatorField_MACD_DIFF = 60; // MACD 指标的 DIFF 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_MACD_DEA = 61; // MACD 指标的 DEA 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_MACD = 62; // MACD 指标的 MACD 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_BOLL_UPPER = 70; // BOLL 指标的 UPPER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    	CustomIndicatorField_BOLL_MIDDLER = 71; // BOLL 指标的 MIDDLER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    	CustomIndicatorField_BOLL_LOWER = 72; // BOLL 指标的 LOWER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**CustomIndicatorField**

    enum CustomIndicatorField
    {
        CustomIndicatorField_Unknown = 0; // 未知
        CustomIndicatorField_Price = 1; // 最新价格 
        CustomIndicatorField_MA5 = 2; // 5日简单均线（不建议使用）
        CustomIndicatorField_MA10 = 3; // 10日简单均线（不建议使用）
        CustomIndicatorField_MA20 = 4; // 20日简单均线（不建议使用） 
        CustomIndicatorField_MA30 = 5; // 30日简单均线（不建议使用） 
        CustomIndicatorField_MA60 = 6; // 60日简单均线（不建议使用） 
        CustomIndicatorField_MA120 = 7; // 120日简单均线（不建议使用）
        CustomIndicatorField_MA250 = 8; // 250日简单均线（不建议使用）
        CustomIndicatorField_RSI = 9; // RSI 指标参数的默认值为[12]
        CustomIndicatorField_EMA5 = 10; // 5日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA10 = 11; // 10日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA20 = 12; // 20日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA30 = 13; // 30日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA60 = 14; // 60日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA120 = 15; // 120日指数移动均线（不建议使用）
        CustomIndicatorField_EMA250 = 16; // 250日指数移动均线（不建议使用）
        CustomIndicatorField_Value = 17; // 自定义数值（stock_field1不支持此字段）
        CustomIndicatorField_MA = 30; // 简单均线
    	CustomIndicatorField_EMA = 40; // 指数移动均线
    	CustomIndicatorField_KDJ_K = 50; // KDJ 指标的 K 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]
    	CustomIndicatorField_KDJ_D = 51; // KDJ 指标的 D 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]
    	CustomIndicatorField_KDJ_J = 52; // KDJ 指标的 J 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]	
    	CustomIndicatorField_MACD_DIFF = 60; // MACD 指标的 DIFF 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_MACD_DEA = 61; // MACD 指标的 DEA 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_MACD = 62; // MACD 指标的 MACD 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_BOLL_UPPER = 70; // BOLL 指标的 UPPER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    	CustomIndicatorField_BOLL_MIDDLER = 71; // BOLL 指标的 MIDDLER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    	CustomIndicatorField_BOLL_LOWER = 72; // BOLL 指标的 LOWER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**CustomIndicatorField**

    enum CustomIndicatorField
    {
        CustomIndicatorField_Unknown = 0; // 未知
        CustomIndicatorField_Price = 1; // 最新价格 
        CustomIndicatorField_MA5 = 2; // 5日简单均线（不建议使用）
        CustomIndicatorField_MA10 = 3; // 10日简单均线（不建议使用）
        CustomIndicatorField_MA20 = 4; // 20日简单均线（不建议使用） 
        CustomIndicatorField_MA30 = 5; // 30日简单均线（不建议使用） 
        CustomIndicatorField_MA60 = 6; // 60日简单均线（不建议使用） 
        CustomIndicatorField_MA120 = 7; // 120日简单均线（不建议使用）
        CustomIndicatorField_MA250 = 8; // 250日简单均线（不建议使用）
        CustomIndicatorField_RSI = 9; // RSI 指标参数的默认值为[12]
        CustomIndicatorField_EMA5 = 10; // 5日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA10 = 11; // 10日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA20 = 12; // 20日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA30 = 13; // 30日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA60 = 14; // 60日指数移动均线（不建议使用） 
        CustomIndicatorField_EMA120 = 15; // 120日指数移动均线（不建议使用）
        CustomIndicatorField_EMA250 = 16; // 250日指数移动均线（不建议使用）
        CustomIndicatorField_Value = 17; // 自定义数值（stock_field1不支持此字段）
        CustomIndicatorField_MA = 30; // 简单均线
    	CustomIndicatorField_EMA = 40; // 指数移动均线
    	CustomIndicatorField_KDJ_K = 50; // KDJ 指标的 K 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]
    	CustomIndicatorField_KDJ_D = 51; // KDJ 指标的 D 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]
    	CustomIndicatorField_KDJ_J = 52; // KDJ 指标的 J 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]	
    	CustomIndicatorField_MACD_DIFF = 60; // MACD 指标的 DIFF 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_MACD_DEA = 61; // MACD 指标的 DEA 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_MACD = 62; // MACD 指标的 MACD 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]
    	CustomIndicatorField_BOLL_UPPER = 70; // BOLL 指标的 UPPER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    	CustomIndicatorField_BOLL_MIDDLER = 71; // BOLL 指标的 MIDDLER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    	CustomIndicatorField_BOLL_LOWER = 72; // BOLL 指标的 LOWER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]
    }
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#2453)
 相对位置
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **RelativePosition**

*   `NONE`
    
    未知
    
*   `MORE`
    
    大于，stock\_field1 位于stock\_field2 的上方
    
*   `LESS`
    
    小于，stock\_field1 位于stock\_field2 的下方
    
*   `CROSS_UP`
    
    升穿，stock\_field1 从下往上穿stock\_field2
    
*   `CROSS_DOWN`
    
    跌穿，stock\_field1 从上往下穿stock\_field2
    

**RelativePosition**

    enum RelativePosition
    {
        RelativePosition_Unknown = 0; // 未知
        RelativePosition_More = 1; // 大于，first位于second的上方
        RelativePosition_Less = 2; // 小于，first位于second的下方
        RelativePosition_CrossUp = 3; // 升穿，first从下往上穿second
        RelativePosition_CrossDown = 4; // 跌穿，first从上往下穿second
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**RelativePosition**

    enum RelativePosition
    {
        RelativePosition_Unknown = 0; // 未知
        RelativePosition_More = 1; // 大于，first位于second的上方
        RelativePosition_Less = 2; // 小于，first位于second的下方
        RelativePosition_CrossUp = 3; // 升穿，first从下往上穿second
        RelativePosition_CrossDown = 4; // 跌穿，first从上往下穿second
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**RelativePosition**

    enum RelativePosition
    {
        RelativePosition_Unknown = 0; // 未知
        RelativePosition_More = 1; // 大于，first位于second的上方
        RelativePosition_Less = 2; // 小于，first位于second的下方
        RelativePosition_CrossUp = 3; // 升穿，first从下往上穿second
        RelativePosition_CrossDown = 4; // 跌穿，first从上往下穿second
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**RelativePosition**

    enum RelativePosition
    {
        RelativePosition_Unknown = 0; // 未知
        RelativePosition_More = 1; // 大于，first位于second的上方
        RelativePosition_Less = 2; // 小于，first位于second的下方
        RelativePosition_CrossUp = 3; // 升穿，first从下往上穿second
        RelativePosition_CrossDown = 4; // 跌穿，first从上往下穿second
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**RelativePosition**

    enum RelativePosition
    {
        RelativePosition_Unknown = 0; // 未知
        RelativePosition_More = 1; // 大于，first位于second的上方
        RelativePosition_Less = 2; // 小于，first位于second的下方
        RelativePosition_CrossUp = 3; // 升穿，first从下往上穿second
        RelativePosition_CrossDown = 4; // 跌穿，first从上往下穿second
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

[#](./quote_quote.md#159)
 形态技术指标属性
---------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **PatternField**

*   `NONE`
    
    未知
    
*   `MA_ALIGNMENT_LONG`
    
    MA多头排列（连续两天MA5>MA10>MA20>MA30>MA60，且当日收盘价大于前一天收盘价）
    
*   `MA_ALIGNMENT_SHORT`
    
    MA空头排列（连续两天MA5<MA10<MA20<MA30<MA60，且当日收盘价小于前一天收盘价）
    
*   `EMA_ALIGNMENT_LONG`
    
    EMA多头排列（连续两天EMA5>EMA10>EMA20>EMA30>EMA60，且当日收盘价大于前一天收盘价）
    
*   `EMA_ALIGNMENT_SHORT`
    
    EMA空头排列（连续两天EMA5<EMA10<EMA20<EMA30<EMA60，且当日收盘价小于前一天收盘价）
    
*   `RSI_GOLD_CROSS_LOW`
    
    RSI低位金叉（50以下，短线RSI上穿长线RSI（前一日短线RSI小于长线RSI，当日短线RSI大于长线RSI））
    
*   `RSI_DEATH_CROSS_HIGH`
    
    RSI高位死叉（50以上，短线RSI下穿长线RSI（前一日短线RSI大于长线RSI，当日短线RSI小于长线RSI））
    
*   `RSI_TOP_DIVERGENCE`
    
    RSI顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的RSI12值<前面波峰的RSI12值）
    
*   `RSI_BOTTOM_DIVERGENCE`
    
    RSI底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE<前面的波谷对应的CLOSE，后面波谷的RSI12值>前面波谷的RSI12值）
    
*   `KDJ_GOLD_CROSS_LOW`
    
    KDJ低位金叉（D值小于或等于30，且前一日K值小于D值，当日K值大于D值）
    
*   `KDJ_DEATH_CROSS_HIGH`
    
    KDJ高位死叉（D值大于或等于70，且前一日K值大于D值，当日K值小于D值）
    
*   `KDJ_TOP_DIVERGENCE`
    
    KDJ顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的J值<前面波峰的J值）
    
*   `KDJ_BOTTOM_DIVERGENCE`
    
    KDJ底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE<前面的波谷对应的CLOSE，后面波谷的J值>前面波谷的J值）
    
*   `MACD_GOLD_CROSS_LOW`
    
    MACD低位金叉（DIFF上穿DEA（前一日DIFF小于DEA，当日DIFF大于DEA））
    
*   `MACD_DEATH_CROSS_HIGH`
    
    MACD高位死叉（DIFF下穿DEA（前一日DIFF大于DEA，当日DIFF小于DEA））
    
*   `MACD_TOP_DIVERGENCE`
    
    MACD顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的macd值<前面波峰的macd值）
    
*   `MACD_BOTTOM_DIVERGENCE`
    
    MACD底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE<前面的波谷对应的CLOSE，后面波谷的macd值>前面波谷的macd值）
    
*   `BOLL_BREAK_UPPER`
    
    BOLL突破上轨（前一日股价低于上轨值，当日股价大于上轨值）
    
*   `BOLL_BREAK_LOWER`
    
    BOLL突破下轨（前一日股价高于下轨值，当日股价小于下轨值）
    
*   `BOLL_CROSS_MIDDLE_UP`
    
    BOLL向上破中轨（前一日股价低于中轨值，当日股价大于中轨值）
    
*   `BOLL_CROSS_MIDDLE_DOWN`
    
    BOLL向下破中轨（前一日股价大于中轨值，当日股价小于中轨值）
    

**PatternField**

    enum PatternField
    {
        PatternField_Unknown = 0 ; // 未知
        PatternField_MAAlignmentLong = 1 ; // MA多头排列（连续两天MA5>MA10>MA20>MA30>MA60，且当日收盘价大于前一天收盘价）
        PatternField_MAAlignmentShort = 2 ; // MA空头排列（连续两天MA5 <MA10 <MA20 <MA30 <MA60，且当日收盘价小于前一天收盘价）
        PatternField_EMAAlignmentLong = 3 ; // EMA多头排列（连续两天EMA5>EMA10>EMA20>EMA30>EMA60，且当日收盘价大于前一天收盘价）
        PatternField_EMAAlignmentShort = 4 ; // EMA空头排列（连续两天EMA5 <EMA10 <EMA20 <EMA30 <EMA60，且当日收盘价小于前一天收盘价）
        PatternField_RSIGoldCrossLow = 5 ; // RSI低位金叉（50以下，短线RSI上穿长线RSI（前一日短线RSI小于长线RSI，当日短线RSI大于长线RSI）） 
        PatternField_RSIDeathCrossHigh = 6 ; // RSI高位死叉（50以上，短线RSI下穿长线RSI（前一日短线RSI大于长线RSI，当日短线RSI小于长线RSI）） 
        PatternField_RSITopDivergence = 7 ; // RSI顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的RSI12值 <前面波峰的RSI12值）
        PatternField_RSIBottomDivergence = 8 ; // RSI底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的RSI12值>前面波谷的RSI12值） 
        PatternField_KDJGoldCrossLow = 9 ; // KDJ低位金叉（KDJ的值都小于或等于30，且前一日K,J值分别小于D值，当日K,J值分别大于D值）
        PatternField_KDJDeathCrossHigh = 10 ; // KDJ高位死叉（KDJ的值都大于或等于70，且前一日K,J值分别大于D值，当日K,J值分别小于D值）
        PatternField_KDJTopDivergence = 11 ; // KDJ顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的J值 <前面波峰的J值）
        PatternField_KDJBottomDivergence = 12 ; // KDJ底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的J值>前面波谷的J值）
        PatternField_MACDGoldCrossLow = 13 ; // MACD低位金叉（DIFF上穿DEA（前一日DIFF小于DEA，当日DIFF大于DEA））
        PatternField_MACDDeathCrossHigh = 14 ; // MACD高位死叉（DIFF下穿DEA（前一日DIFF大于DEA，当日DIFF小于DEA））
        PatternField_MACDTopDivergence = 15 ; // MACD顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的macd值 <前面波峰的macd值）
        PatternField_MACDBottomDivergence = 16 ; // MACD底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的macd值>前面波谷的macd值）
        PatternField_BOLLBreakUpper = 17 ; // BOLL突破上轨（前一日股价低于上轨值，当日股价大于上轨值） 
        PatternField_BOLLLower = 18 ; // BOLL突破下轨（前一日股价高于下轨值，当日股价小于下轨值）
        PatternField_BOLLCrossMiddleUp = 19 ; // BOLL向上破中轨（前一日股价低于中轨值，当日股价大于中轨值）
        PatternField_BOLLCrossMiddleDown = 20 ; // BOLL向下破中轨（前一日股价大于中轨值，当日股价小于中轨值）
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**PatternField**

    enum PatternField
    {
        PatternField_Unknown = 0 ; // 未知
        PatternField_MAAlignmentLong = 1 ; // MA多头排列（连续两天MA5>MA10>MA20>MA30>MA60，且当日收盘价大于前一天收盘价）
        PatternField_MAAlignmentShort = 2 ; // MA空头排列（连续两天MA5 <MA10 <MA20 <MA30 <MA60，且当日收盘价小于前一天收盘价）
        PatternField_EMAAlignmentLong = 3 ; // EMA多头排列（连续两天EMA5>EMA10>EMA20>EMA30>EMA60，且当日收盘价大于前一天收盘价）
        PatternField_EMAAlignmentShort = 4 ; // EMA空头排列（连续两天EMA5 <EMA10 <EMA20 <EMA30 <EMA60，且当日收盘价小于前一天收盘价）
        PatternField_RSIGoldCrossLow = 5 ; // RSI低位金叉（50以下，短线RSI上穿长线RSI（前一日短线RSI小于长线RSI，当日短线RSI大于长线RSI）） 
        PatternField_RSIDeathCrossHigh = 6 ; // RSI高位死叉（50以上，短线RSI下穿长线RSI（前一日短线RSI大于长线RSI，当日短线RSI小于长线RSI）） 
        PatternField_RSITopDivergence = 7 ; // RSI顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的RSI12值 <前面波峰的RSI12值）
        PatternField_RSIBottomDivergence = 8 ; // RSI底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的RSI12值>前面波谷的RSI12值） 
        PatternField_KDJGoldCrossLow = 9 ; // KDJ低位金叉（KDJ的值都小于或等于30，且前一日K,J值分别小于D值，当日K,J值分别大于D值）
        PatternField_KDJDeathCrossHigh = 10 ; // KDJ高位死叉（KDJ的值都大于或等于70，且前一日K,J值分别大于D值，当日K,J值分别小于D值）
        PatternField_KDJTopDivergence = 11 ; // KDJ顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的J值 <前面波峰的J值）
        PatternField_KDJBottomDivergence = 12 ; // KDJ底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的J值>前面波谷的J值）
        PatternField_MACDGoldCrossLow = 13 ; // MACD低位金叉（DIFF上穿DEA（前一日DIFF小于DEA，当日DIFF大于DEA））
        PatternField_MACDDeathCrossHigh = 14 ; // MACD高位死叉（DIFF下穿DEA（前一日DIFF大于DEA，当日DIFF小于DEA））
        PatternField_MACDTopDivergence = 15 ; // MACD顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的macd值 <前面波峰的macd值）
        PatternField_MACDBottomDivergence = 16 ; // MACD底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的macd值>前面波谷的macd值）
        PatternField_BOLLBreakUpper = 17 ; // BOLL突破上轨（前一日股价低于上轨值，当日股价大于上轨值） 
        PatternField_BOLLLower = 18 ; // BOLL突破下轨（前一日股价高于下轨值，当日股价小于下轨值）
        PatternField_BOLLCrossMiddleUp = 19 ; // BOLL向上破中轨（前一日股价低于中轨值，当日股价大于中轨值）
        PatternField_BOLLCrossMiddleDown = 20 ; // BOLL向下破中轨（前一日股价大于中轨值，当日股价小于中轨值）
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**PatternField**

    enum PatternField
    {
        PatternField_Unknown = 0 ; // 未知
        PatternField_MAAlignmentLong = 1 ; // MA多头排列（连续两天MA5>MA10>MA20>MA30>MA60，且当日收盘价大于前一天收盘价）
        PatternField_MAAlignmentShort = 2 ; // MA空头排列（连续两天MA5 <MA10 <MA20 <MA30 <MA60，且当日收盘价小于前一天收盘价）
        PatternField_EMAAlignmentLong = 3 ; // EMA多头排列（连续两天EMA5>EMA10>EMA20>EMA30>EMA60，且当日收盘价大于前一天收盘价）
        PatternField_EMAAlignmentShort = 4 ; // EMA空头排列（连续两天EMA5 <EMA10 <EMA20 <EMA30 <EMA60，且当日收盘价小于前一天收盘价）
        PatternField_RSIGoldCrossLow = 5 ; // RSI低位金叉（50以下，短线RSI上穿长线RSI（前一日短线RSI小于长线RSI，当日短线RSI大于长线RSI）） 
        PatternField_RSIDeathCrossHigh = 6 ; // RSI高位死叉（50以上，短线RSI下穿长线RSI（前一日短线RSI大于长线RSI，当日短线RSI小于长线RSI）） 
        PatternField_RSITopDivergence = 7 ; // RSI顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的RSI12值 <前面波峰的RSI12值）
        PatternField_RSIBottomDivergence = 8 ; // RSI底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的RSI12值>前面波谷的RSI12值） 
        PatternField_KDJGoldCrossLow = 9 ; // KDJ低位金叉（KDJ的值都小于或等于30，且前一日K,J值分别小于D值，当日K,J值分别大于D值）
        PatternField_KDJDeathCrossHigh = 10 ; // KDJ高位死叉（KDJ的值都大于或等于70，且前一日K,J值分别大于D值，当日K,J值分别小于D值）
        PatternField_KDJTopDivergence = 11 ; // KDJ顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的J值 <前面波峰的J值）
        PatternField_KDJBottomDivergence = 12 ; // KDJ底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的J值>前面波谷的J值）
        PatternField_MACDGoldCrossLow = 13 ; // MACD低位金叉（DIFF上穿DEA（前一日DIFF小于DEA，当日DIFF大于DEA））
        PatternField_MACDDeathCrossHigh = 14 ; // MACD高位死叉（DIFF下穿DEA（前一日DIFF大于DEA，当日DIFF小于DEA））
        PatternField_MACDTopDivergence = 15 ; // MACD顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的macd值 <前面波峰的macd值）
        PatternField_MACDBottomDivergence = 16 ; // MACD底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的macd值>前面波谷的macd值）
        PatternField_BOLLBreakUpper = 17 ; // BOLL突破上轨（前一日股价低于上轨值，当日股价大于上轨值） 
        PatternField_BOLLLower = 18 ; // BOLL突破下轨（前一日股价高于下轨值，当日股价小于下轨值）
        PatternField_BOLLCrossMiddleUp = 19 ; // BOLL向上破中轨（前一日股价低于中轨值，当日股价大于中轨值）
        PatternField_BOLLCrossMiddleDown = 20 ; // BOLL向下破中轨（前一日股价大于中轨值，当日股价小于中轨值）
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**PatternField**

    enum PatternField
    {
        PatternField_Unknown = 0 ; // 未知
        PatternField_MAAlignmentLong = 1 ; // MA多头排列（连续两天MA5>MA10>MA20>MA30>MA60，且当日收盘价大于前一天收盘价）
        PatternField_MAAlignmentShort = 2 ; // MA空头排列（连续两天MA5 <MA10 <MA20 <MA30 <MA60，且当日收盘价小于前一天收盘价）
        PatternField_EMAAlignmentLong = 3 ; // EMA多头排列（连续两天EMA5>EMA10>EMA20>EMA30>EMA60，且当日收盘价大于前一天收盘价）
        PatternField_EMAAlignmentShort = 4 ; // EMA空头排列（连续两天EMA5 <EMA10 <EMA20 <EMA30 <EMA60，且当日收盘价小于前一天收盘价）
        PatternField_RSIGoldCrossLow = 5 ; // RSI低位金叉（50以下，短线RSI上穿长线RSI（前一日短线RSI小于长线RSI，当日短线RSI大于长线RSI）） 
        PatternField_RSIDeathCrossHigh = 6 ; // RSI高位死叉（50以上，短线RSI下穿长线RSI（前一日短线RSI大于长线RSI，当日短线RSI小于长线RSI）） 
        PatternField_RSITopDivergence = 7 ; // RSI顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的RSI12值 <前面波峰的RSI12值）
        PatternField_RSIBottomDivergence = 8 ; // RSI底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的RSI12值>前面波谷的RSI12值） 
        PatternField_KDJGoldCrossLow = 9 ; // KDJ低位金叉（KDJ的值都小于或等于30，且前一日K,J值分别小于D值，当日K,J值分别大于D值）
        PatternField_KDJDeathCrossHigh = 10 ; // KDJ高位死叉（KDJ的值都大于或等于70，且前一日K,J值分别大于D值，当日K,J值分别小于D值）
        PatternField_KDJTopDivergence = 11 ; // KDJ顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的J值 <前面波峰的J值）
        PatternField_KDJBottomDivergence = 12 ; // KDJ底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的J值>前面波谷的J值）
        PatternField_MACDGoldCrossLow = 13 ; // MACD低位金叉（DIFF上穿DEA（前一日DIFF小于DEA，当日DIFF大于DEA））
        PatternField_MACDDeathCrossHigh = 14 ; // MACD高位死叉（DIFF下穿DEA（前一日DIFF大于DEA，当日DIFF小于DEA））
        PatternField_MACDTopDivergence = 15 ; // MACD顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的macd值 <前面波峰的macd值）
        PatternField_MACDBottomDivergence = 16 ; // MACD底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的macd值>前面波谷的macd值）
        PatternField_BOLLBreakUpper = 17 ; // BOLL突破上轨（前一日股价低于上轨值，当日股价大于上轨值） 
        PatternField_BOLLLower = 18 ; // BOLL突破下轨（前一日股价高于下轨值，当日股价小于下轨值）
        PatternField_BOLLCrossMiddleUp = 19 ; // BOLL向上破中轨（前一日股价低于中轨值，当日股价大于中轨值）
        PatternField_BOLLCrossMiddleDown = 20 ; // BOLL向下破中轨（前一日股价大于中轨值，当日股价小于中轨值）
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**PatternField**

    enum PatternField
    {
        PatternField_Unknown = 0 ; // 未知
        PatternField_MAAlignmentLong = 1 ; // MA多头排列（连续两天MA5>MA10>MA20>MA30>MA60，且当日收盘价大于前一天收盘价）
        PatternField_MAAlignmentShort = 2 ; // MA空头排列（连续两天MA5 <MA10 <MA20 <MA30 <MA60，且当日收盘价小于前一天收盘价）
        PatternField_EMAAlignmentLong = 3 ; // EMA多头排列（连续两天EMA5>EMA10>EMA20>EMA30>EMA60，且当日收盘价大于前一天收盘价）
        PatternField_EMAAlignmentShort = 4 ; // EMA空头排列（连续两天EMA5 <EMA10 <EMA20 <EMA30 <EMA60，且当日收盘价小于前一天收盘价）
        PatternField_RSIGoldCrossLow = 5 ; // RSI低位金叉（50以下，短线RSI上穿长线RSI（前一日短线RSI小于长线RSI，当日短线RSI大于长线RSI）） 
        PatternField_RSIDeathCrossHigh = 6 ; // RSI高位死叉（50以上，短线RSI下穿长线RSI（前一日短线RSI大于长线RSI，当日短线RSI小于长线RSI）） 
        PatternField_RSITopDivergence = 7 ; // RSI顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的RSI12值 <前面波峰的RSI12值）
        PatternField_RSIBottomDivergence = 8 ; // RSI底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的RSI12值>前面波谷的RSI12值） 
        PatternField_KDJGoldCrossLow = 9 ; // KDJ低位金叉（KDJ的值都小于或等于30，且前一日K,J值分别小于D值，当日K,J值分别大于D值）
        PatternField_KDJDeathCrossHigh = 10 ; // KDJ高位死叉（KDJ的值都大于或等于70，且前一日K,J值分别大于D值，当日K,J值分别小于D值）
        PatternField_KDJTopDivergence = 11 ; // KDJ顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的J值 <前面波峰的J值）
        PatternField_KDJBottomDivergence = 12 ; // KDJ底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的J值>前面波谷的J值）
        PatternField_MACDGoldCrossLow = 13 ; // MACD低位金叉（DIFF上穿DEA（前一日DIFF小于DEA，当日DIFF大于DEA））
        PatternField_MACDDeathCrossHigh = 14 ; // MACD高位死叉（DIFF下穿DEA（前一日DIFF大于DEA，当日DIFF小于DEA））
        PatternField_MACDTopDivergence = 15 ; // MACD顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的macd值 <前面波峰的macd值）
        PatternField_MACDBottomDivergence = 16 ; // MACD底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的macd值>前面波谷的macd值）
        PatternField_BOLLBreakUpper = 17 ; // BOLL突破上轨（前一日股价低于上轨值，当日股价大于上轨值） 
        PatternField_BOLLLower = 18 ; // BOLL突破下轨（前一日股价高于下轨值，当日股价小于下轨值）
        PatternField_BOLLCrossMiddleUp = 19 ; // BOLL向上破中轨（前一日股价低于中轨值，当日股价大于中轨值）
        PatternField_BOLLCrossMiddleDown = 20 ; // BOLL向下破中轨（前一日股价大于中轨值，当日股价小于中轨值）
    }
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#4977)
 自选股分组类型
---------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **UserSecurityGroupType**

*   `NONE`
    
    未知
    
*   `CUSTOM`
    
    自定义分组
    
*   `SYSTEM`
    
    系统分组
    
*   `ALL`
    
    全部分组
    

**GroupType**

    enum GroupType
    {
        GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    

1  
2  
3  
4  
5  
6  
7  

**GroupType**

    enum GroupType
    {
        GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    

1  
2  
3  
4  
5  
6  
7  

**GroupType**

    enum GroupType
    {
        GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    

1  
2  
3  
4  
5  
6  
7  

**GroupType**

    enum GroupType
    {
        GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    

1  
2  
3  
4  
5  
6  
7  

**GroupType**

    enum GroupType
    {
        GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./quote_quote.md#5149)
 指数期权类别
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **IndexOptionType**

*   `NONE`
    
    未知
    
*   `NORMAL`
    
    普通的指数期权
    
*   `SMALL`
    
    小型指数期权
    

**IndexOptionType**

    enum IndexOptionType
    {
        IndexOptionType_Unknown = 0; //未知
        IndexOptionType_Normal = 1; //普通的指数期权
        IndexOptionType_Small = 2; //小型指数期权
    }
    

1  
2  
3  
4  
5  
6  

**IndexOptionType**

    enum IndexOptionType
    {
        IndexOptionType_Unknown = 0; //未知
        IndexOptionType_Normal = 1; //普通的指数期权
        IndexOptionType_Small = 2; //小型指数期权
    }
    

1  
2  
3  
4  
5  
6  

**IndexOptionType**

    enum IndexOptionType
    {
        IndexOptionType_Unknown = 0; //未知
        IndexOptionType_Normal = 1; //普通的指数期权
        IndexOptionType_Small = 2; //小型指数期权
    }
    

1  
2  
3  
4  
5  
6  

**IndexOptionType**

    enum IndexOptionType
    {
        IndexOptionType_Unknown = 0; //未知
        IndexOptionType_Normal = 1; //普通的指数期权
        IndexOptionType_Small = 2; //小型指数期权
    }
    

1  
2  
3  
4  
5  
6  

**IndexOptionType**

    enum IndexOptionType
    {
        IndexOptionType_Unknown = 0; //未知
        IndexOptionType_Normal = 1; //普通的指数期权
        IndexOptionType_Small = 2; //小型指数期权
    }
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#9546)
 上市时段
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **IpoPeriod**

*   `NONE`
    
    未知
    
*   `TODAY`
    
    今日上市
    
*   `TOMORROW`
    
    明日上市
    
*   `NEXTWEEK`
    
    未来一周上市
    
*   `LASTWEEK`
    
    过去一周上市
    
*   `LASTMONTH`
    
    过去一月上市
    

**IpoPeriod**

    enum IpoPeriod
    {
        IpoPeriod_Unknow = 0; //未知
        IpoPeriod_Today = 1; //今日上市
        IpoPeriod_Tomorrow = 2; //明日上市
        IpoPeriod_Nextweek = 3; //未来一周上市
        IpoPeriod_Lastweek = 4; //过去一周上市
        IpoPeriod_Lastmonth = 5; //过去一月上市
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**IpoPeriod**

    enum IpoPeriod
    {
        IpoPeriod_Unknow = 0; //未知
        IpoPeriod_Today = 1; //今日上市
        IpoPeriod_Tomorrow = 2; //明日上市
        IpoPeriod_Nextweek = 3; //未来一周上市
        IpoPeriod_Lastweek = 4; //过去一周上市
        IpoPeriod_Lastmonth = 5; //过去一月上市
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**IpoPeriod**

    enum IpoPeriod
    {
        IpoPeriod_Unknow = 0; //未知
        IpoPeriod_Today = 1; //今日上市
        IpoPeriod_Tomorrow = 2; //明日上市
        IpoPeriod_Nextweek = 3; //未来一周上市
        IpoPeriod_Lastweek = 4; //过去一周上市
        IpoPeriod_Lastmonth = 5; //过去一月上市
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**IpoPeriod**

    enum IpoPeriod
    {
        IpoPeriod_Unknow = 0; //未知
        IpoPeriod_Today = 1; //今日上市
        IpoPeriod_Tomorrow = 2; //明日上市
        IpoPeriod_Nextweek = 3; //未来一周上市
        IpoPeriod_Lastweek = 4; //过去一周上市
        IpoPeriod_Lastmonth = 5; //过去一月上市
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**IpoPeriod**

    enum IpoPeriod
    {
        IpoPeriod_Unknow = 0; //未知
        IpoPeriod_Today = 1; //今日上市
        IpoPeriod_Tomorrow = 2; //明日上市
        IpoPeriod_Nextweek = 3; //未来一周上市
        IpoPeriod_Lastweek = 4; //过去一周上市
        IpoPeriod_Lastmonth = 5; //过去一月上市
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

[#](./quote_quote.md#8363)
 窝轮发行商
-------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **Issuer**

*   `UNKNOW`
    
    未知
    
*   `SG`
    
    法兴
    
*   `BP`
    
    法巴
    
*   `CS`
    
    瑞信
    
*   `CT`
    
    花旗
    
*   `EA`
    
    东亚
    
*   `GS`
    
    高盛
    
*   `HS`
    
    汇丰
    
*   `JP`
    
    摩通
    
*   `MB`
    
    麦银
    
*   `SC`
    
    渣打
    
*   `UB`
    
    瑞银
    
*   `BI`
    
    中银
    
*   `DB`
    
    德银
    
*   `DC`
    
    大和
    
*   `ML`
    
    美林
    
*   `NM`
    
    野村
    
*   `RB`
    
    荷合
    
*   `RS`
    
    苏皇
    
*   `BC`
    
    巴克莱
    
*   `HT`
    
    海通
    
*   `VT`
    
    瑞通
    
*   `KC`
    
    比联
    
*   `MS`
    
    摩利
    
*   `GJ`
    
    国君
    
*   `XZ`
    
    星展
    
*   `HU`
    
    华泰
    
*   `KS`
    
    韩投
    
*   `CI`
    
    信证
    

**Issuer**

    enum Issuer
    {
        Issuer_Unknow = 0; //未知
        Issuer_SG = 1; //法兴
        Issuer_BP = 2; //法巴
        Issuer_CS = 3; //瑞信
        Issuer_CT = 4; //花旗    
        Issuer_EA = 5; //东亚
        Issuer_GS = 6; //高盛
        Issuer_HS = 7; //汇丰
        Issuer_JP = 8; //摩通    
        Issuer_MB = 9; //麦银    
        Issuer_SC = 10; //渣打
        Issuer_UB = 11; //瑞银
        Issuer_BI = 12; //中银
        Issuer_DB = 13; //德银
        Issuer_DC = 14; //大和
        Issuer_ML = 15; //美林
        Issuer_NM = 16; //野村
        Issuer_RB = 17; //荷合
        Issuer_RS = 18; //苏皇    
        Issuer_BC = 19; //巴克莱
        Issuer_HT = 20; //海通
        Issuer_VT = 21; //瑞通
        Issuer_KC = 22; //比联
        Issuer_MS = 23; //摩利
        Issuer_GJ = 24; //国君
        Issuer_XZ = 25; //星展
        Issuer_HU = 26; //华泰
        Issuer_KS = 27; //韩投
        Issuer_CI = 28; //信证
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**Issuer**

    enum Issuer
    {
        Issuer_Unknow = 0; //未知
        Issuer_SG = 1; //法兴
        Issuer_BP = 2; //法巴
        Issuer_CS = 3; //瑞信
        Issuer_CT = 4; //花旗    
        Issuer_EA = 5; //东亚
        Issuer_GS = 6; //高盛
        Issuer_HS = 7; //汇丰
        Issuer_JP = 8; //摩通    
        Issuer_MB = 9; //麦银    
        Issuer_SC = 10; //渣打
        Issuer_UB = 11; //瑞银
        Issuer_BI = 12; //中银
        Issuer_DB = 13; //德银
        Issuer_DC = 14; //大和
        Issuer_ML = 15; //美林
        Issuer_NM = 16; //野村
        Issuer_RB = 17; //荷合
        Issuer_RS = 18; //苏皇    
        Issuer_BC = 19; //巴克莱
        Issuer_HT = 20; //海通
        Issuer_VT = 21; //瑞通
        Issuer_KC = 22; //比联
        Issuer_MS = 23; //摩利
        Issuer_GJ = 24; //国君
        Issuer_XZ = 25; //星展
        Issuer_HU = 26; //华泰
        Issuer_KS = 27; //韩投
        Issuer_CI = 28; //信证
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**Issuer**

    enum Issuer
    {
        Issuer_Unknow = 0; //未知
        Issuer_SG = 1; //法兴
        Issuer_BP = 2; //法巴
        Issuer_CS = 3; //瑞信
        Issuer_CT = 4; //花旗    
        Issuer_EA = 5; //东亚
        Issuer_GS = 6; //高盛
        Issuer_HS = 7; //汇丰
        Issuer_JP = 8; //摩通    
        Issuer_MB = 9; //麦银    
        Issuer_SC = 10; //渣打
        Issuer_UB = 11; //瑞银
        Issuer_BI = 12; //中银
        Issuer_DB = 13; //德银
        Issuer_DC = 14; //大和
        Issuer_ML = 15; //美林
        Issuer_NM = 16; //野村
        Issuer_RB = 17; //荷合
        Issuer_RS = 18; //苏皇    
        Issuer_BC = 19; //巴克莱
        Issuer_HT = 20; //海通
        Issuer_VT = 21; //瑞通
        Issuer_KC = 22; //比联
        Issuer_MS = 23; //摩利
        Issuer_GJ = 24; //国君
        Issuer_XZ = 25; //星展
        Issuer_HU = 26; //华泰
        Issuer_KS = 27; //韩投
        Issuer_CI = 28; //信证
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**Issuer**

    enum Issuer
    {
        Issuer_Unknow = 0; //未知
        Issuer_SG = 1; //法兴
        Issuer_BP = 2; //法巴
        Issuer_CS = 3; //瑞信
        Issuer_CT = 4; //花旗    
        Issuer_EA = 5; //东亚
        Issuer_GS = 6; //高盛
        Issuer_HS = 7; //汇丰
        Issuer_JP = 8; //摩通    
        Issuer_MB = 9; //麦银    
        Issuer_SC = 10; //渣打
        Issuer_UB = 11; //瑞银
        Issuer_BI = 12; //中银
        Issuer_DB = 13; //德银
        Issuer_DC = 14; //大和
        Issuer_ML = 15; //美林
        Issuer_NM = 16; //野村
        Issuer_RB = 17; //荷合
        Issuer_RS = 18; //苏皇    
        Issuer_BC = 19; //巴克莱
        Issuer_HT = 20; //海通
        Issuer_VT = 21; //瑞通
        Issuer_KC = 22; //比联
        Issuer_MS = 23; //摩利
        Issuer_GJ = 24; //国君
        Issuer_XZ = 25; //星展
        Issuer_HU = 26; //华泰
        Issuer_KS = 27; //韩投
        Issuer_CI = 28; //信证
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**Issuer**

    enum Issuer
    {
        Issuer_Unknow = 0; //未知
        Issuer_SG = 1; //法兴
        Issuer_BP = 2; //法巴
        Issuer_CS = 3; //瑞信
        Issuer_CT = 4; //花旗    
        Issuer_EA = 5; //东亚
        Issuer_GS = 6; //高盛
        Issuer_HS = 7; //汇丰
        Issuer_JP = 8; //摩通    
        Issuer_MB = 9; //麦银    
        Issuer_SC = 10; //渣打
        Issuer_UB = 11; //瑞银
        Issuer_BI = 12; //中银
        Issuer_DB = 13; //德银
        Issuer_DC = 14; //大和
        Issuer_ML = 15; //美林
        Issuer_NM = 16; //野村
        Issuer_RB = 17; //荷合
        Issuer_RS = 18; //苏皇    
        Issuer_BC = 19; //巴克莱
        Issuer_HT = 20; //海通
        Issuer_VT = 21; //瑞通
        Issuer_KC = 22; //比联
        Issuer_MS = 23; //摩利
        Issuer_GJ = 24; //国君
        Issuer_XZ = 25; //星展
        Issuer_HU = 26; //华泰
        Issuer_KS = 27; //韩投
        Issuer_CI = 28; //信证
    }
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#481)
 K 线字段
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **KL\_FIELD**

*   `ALL`
    
    所有
    
*   `DATE_TIME`
    
    时间
    
*   `HIGH`
    
    最高价
    
*   `OPEN`
    
    开盘价
    
*   `LOW`
    
    最低价
    
*   `CLOSE`
    
    收盘价
    
*   `LAST_CLOSE`
    
    昨收价
    
*   `TRADE_VOL`
    
    成交量
    
*   `TRADE_VAL`
    
    成交额
    
*   `TURNOVER_RATE`
    
    换手率
    
*   `PE_RATIO`
    
    市盈率
    
*   `CHANGE_RATE`
    
    涨跌幅
    

**KLFields**

    enum KLFields
    {
        KLFields_None = 0; //
        KLFields_High = 1; //最高价
        KLFields_Open = 2; //开盘价
        KLFields_Low = 4; //最低价
        KLFields_Close = 8; //收盘价
        KLFields_LastClose = 16; //昨收价
        KLFields_Volume = 32; //成交量
        KLFields_Turnover = 64; //成交额
        KLFields_TurnoverRate = 128; //换手率
        KLFields_PE = 256; //市盈率
        KLFields_ChangeRate = 512; //涨跌幅
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

**KLFields**

    enum KLFields
    {
        KLFields_None = 0; //
        KLFields_High = 1; //最高价
        KLFields_Open = 2; //开盘价
        KLFields_Low = 4; //最低价
        KLFields_Close = 8; //收盘价
        KLFields_LastClose = 16; //昨收价
        KLFields_Volume = 32; //成交量
        KLFields_Turnover = 64; //成交额
        KLFields_TurnoverRate = 128; //换手率
        KLFields_PE = 256; //市盈率
        KLFields_ChangeRate = 512; //涨跌幅
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

**KLFields**

    enum KLFields
    {
        KLFields_None = 0; //
        KLFields_High = 1; //最高价
        KLFields_Open = 2; //开盘价
        KLFields_Low = 4; //最低价
        KLFields_Close = 8; //收盘价
        KLFields_LastClose = 16; //昨收价
        KLFields_Volume = 32; //成交量
        KLFields_Turnover = 64; //成交额
        KLFields_TurnoverRate = 128; //换手率
        KLFields_PE = 256; //市盈率
        KLFields_ChangeRate = 512; //涨跌幅
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

**KLFields**

    enum KLFields
    {
        KLFields_None = 0; //
        KLFields_High = 1; //最高价
        KLFields_Open = 2; //开盘价
        KLFields_Low = 4; //最低价
        KLFields_Close = 8; //收盘价
        KLFields_LastClose = 16; //昨收价
        KLFields_Volume = 32; //成交量
        KLFields_Turnover = 64; //成交额
        KLFields_TurnoverRate = 128; //换手率
        KLFields_PE = 256; //市盈率
        KLFields_ChangeRate = 512; //涨跌幅
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

**KLFields**

    enum KLFields
    {
        KLFields_None = 0; //
        KLFields_High = 1; //最高价
        KLFields_Open = 2; //开盘价
        KLFields_Low = 4; //最低价
        KLFields_Close = 8; //收盘价
        KLFields_LastClose = 16; //昨收价
        KLFields_Volume = 32; //成交量
        KLFields_Turnover = 64; //成交额
        KLFields_TurnoverRate = 128; //换手率
        KLFields_PE = 256; //市盈率
        KLFields_ChangeRate = 512; //涨跌幅
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

[#](./quote_quote.md#4119)
 K 线类型
-------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **KLType**

*   `NONE`
    
    未知
    
*   `K_1M`
    
    1分 K
    
*   `K_DAY`
    
    日 K
    
*   `K_WEEK`
    
    周 K
    
    期权暂不支持该K线类型
    
*   `K_MON`
    
    月 K
    
    期权暂不支持该K线类型
    
*   `K_YEAR`
    
    年 K
    
    期权暂不支持该K线类型
    
*   `K_5M`
    
    5分 K
    
*   `K_15M`
    
    15分 K
    
*   `K_30M`
    
    30分 K
    
    期权暂不支持该K线类型
    
*   `K_60M`
    
    60分 K
    
*   `K_3M`
    
    3分 K
    
    期权暂不支持该K线类型
    
*   `K_QUARTER`
    
    季 K
    
    期权暂不支持该K线类型
    

**KLType**

    enum KLType
    {
        KLType_Unknown = 0; //未知
        KLType_1Min = 1; //1分 K
        KLType_Day = 2; //日 K
        KLType_Week = 3; //周 K (期权暂不支持)
        KLType_Month = 4; //月 K (期权暂不支持)    
        KLType_Year = 5; //年 K (期权暂不支持)
        KLType_5Min = 6; //5分 K
        KLType_15Min = 7; //15分 K
        KLType_30Min = 8; //30分 K (期权暂不支持)
        KLType_60Min = 9; //60分 K        
        KLType_3Min = 10; //3分 K (期权暂不支持)
        KLType_Quarter = 11; //季 K (期权暂不支持)
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

**KLType**

    enum KLType
    {
        KLType_Unknown = 0; //未知
        KLType_1Min = 1; //1分 K
        KLType_Day = 2; //日 K
        KLType_Week = 3; //周 K (期权暂不支持)
        KLType_Month = 4; //月 K (期权暂不支持)  
        KLType_Year = 5; //年 K (期权暂不支持)
        KLType_5Min = 6; //5分 K
        KLType_15Min = 7; //15分 K
        KLType_30Min = 8; //30分 K (期权暂不支持)
        KLType_60Min = 9; //60分 K        
        KLType_3Min = 10; //3分 K (期权暂不支持)
        KLType_Quarter = 11; //季 K (期权暂不支持)
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

**KLType**

    enum KLType
    {
        KLType_Unknown = 0; //未知
        KLType_1Min = 1; //1分 K
        KLType_Day = 2; //日 K
        KLType_Week = 3; //周 K (期权暂不支持)
        KLType_Month = 4; //月 K (期权暂不支持)    
        KLType_Year = 5; //年 K (期权暂不支持)
        KLType_5Min = 6; //5分 K
        KLType_15Min = 7; //15分 K
        KLType_30Min = 8; //30分 K (期权暂不支持)
        KLType_60Min = 9; //60分 K        
        KLType_3Min = 10; //3分 K (期权暂不支持)
        KLType_Quarter = 11; //季 K (期权暂不支持)
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

**KLType**

    enum KLType
    {
        KLType_Unknown = 0; //未知
        KLType_1Min = 1; //1分 K
        KLType_Day = 2; //日 K
        KLType_Week = 3; //周 K (期权暂不支持)
        KLType_Month = 4; //月 K (期权暂不支持)    
        KLType_Year = 5; //年 K (期权暂不支持)
        KLType_5Min = 6; //5分 K
        KLType_15Min = 7; //15分 K
        KLType_30Min = 8; //30分 K (期权暂不支持)
        KLType_60Min = 9; //60分 K        
        KLType_3Min = 10; //3分 K (期权暂不支持)
        KLType_Quarter = 11; //季 K (期权暂不支持)
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

**KLType**

    enum KLType
    {
        KLType_Unknown = 0; //未知
        KLType_1Min = 1; //1分 K
        KLType_Day = 2; //日 K
        KLType_Week = 3; //周 K (期权暂不支持)
        KLType_Month = 4; //月 K (期权暂不支持)    
        KLType_Year = 5; //年 K (期权暂不支持)
        KLType_5Min = 6; //5分 K
        KLType_15Min = 7; //15分 K
        KLType_30Min = 8; //30分 K (期权暂不支持)
        KLType_60Min = 9; //60分 K        
        KLType_3Min = 10; //3分 K (期权暂不支持)
        KLType_Quarter = 11; //季 K (期权暂不支持)
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

[#](./quote_quote.md#2644)
 周期类型
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **PeriodType**

*   `INTRADAY`
    
    实时
    
*   `DAY`
    
    日
    
*   `WEEK`
    
    周
    
*   `MONTH`
    
    月
    

**PeriodType**

    enum PeriodType
    {
        PeriodType_INTRADAY = 0; //实时
        PeriodType_DAY = 1; //日
        PeriodType_WEEK = 2; //周
        PeriodType_MONTH = 3; //月
    }
    

1  
2  
3  
4  
5  
6  
7  

**PeriodType**

    enum PeriodType
    {
        PeriodType_INTRADAY = 0; //实时
        PeriodType_DAY = 1; //日
        PeriodType_WEEK = 2; //周
        PeriodType_MONTH = 3; //月
    }
    

1  
2  
3  
4  
5  
6  
7  

**PeriodType**

    enum PeriodType
    {
        PeriodType_INTRADAY = 0; //实时
        PeriodType_DAY = 1; //日
        PeriodType_WEEK = 2; //周
        PeriodType_MONTH = 3; //月
    }
    

1  
2  
3  
4  
5  
6  
7  

**PeriodType**

    enum PeriodType
    {
        PeriodType_INTRADAY = 0; //实时
        PeriodType_DAY = 1; //日
        PeriodType_WEEK = 2; //周
        PeriodType_MONTH = 3; //月
    }
    

1  
2  
3  
4  
5  
6  
7  

**PeriodType**

    enum PeriodType
    {
        PeriodType_INTRADAY = 0; //实时
        PeriodType_DAY = 1; //日
        PeriodType_WEEK = 2; //周
        PeriodType_MONTH = 3; //月
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./quote_quote.md#482)
 到价提醒市场状态
---------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **PriceReminderMarketStatus**

*   `NONE`
    
    未知
    
*   `OPEN`
    
    盘中
    
*   `US_PRE`
    
    美股盘前
    
*   `US_AFTER`
    
    美股盘后
    
*   `US_OVERNIGHT`
    
    美股夜盘
    

**MarketStatus**

    enum MarketStatus
    {
        MarketStatus_Unknow = 0;
        MarketStatus_Open = 1; // 盘中
        MarketStatus_USPre = 2;  // 美股盘前
        MarketStatus_USAfter = 3; // 美股盘后
    }
    

1  
2  
3  
4  
5  
6  
7  

**MarketStatus**

    enum MarketStatus
    {
        MarketStatus_Unknow = 0;
        MarketStatus_Open = 1; // 盘中
        MarketStatus_USPre = 2;  // 美股盘前
        MarketStatus_USAfter = 3; // 美股盘后
    }
    

1  
2  
3  
4  
5  
6  
7  

**MarketStatus**

    enum MarketStatus
    {
        MarketStatus_Unknow = 0;
        MarketStatus_Open = 1; // 盘中
        MarketStatus_USPre = 2;  // 美股盘前
        MarketStatus_USAfter = 3; // 美股盘后
    }
    

1  
2  
3  
4  
5  
6  
7  

**MarketStatus**

    enum MarketStatus
    {
        MarketStatus_Unknow = 0;
        MarketStatus_Open = 1; // 盘中
        MarketStatus_USPre = 2;  // 美股盘前
        MarketStatus_USAfter = 3; // 美股盘后
    }
    

1  
2  
3  
4  
5  
6  
7  

**MarketStatus**

    enum MarketStatus
    {
        MarketStatus_Unknow = 0;
        MarketStatus_Open = 1; // 盘中
        MarketStatus_USPre = 2;  // 美股盘前
        MarketStatus_USAfter = 3; // 美股盘后
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./quote_quote.md#3838)
 自选股操作
-------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **ModifyUserSecurityOp**

*   `NONE`
    
    未知
    
*   `ADD`
    
    新增
    
*   `DEL`
    
    删除自选
    
*   `MOVE_OUT`
    
    移出分组
    

**ModifyUserSecurityOp**

    enum ModifyUserSecurityOp
    {
        ModifyUserSecurityOp_Unknown = 0;
        ModifyUserSecurityOp_Add = 1; //新增
        ModifyUserSecurityOp_Del = 2; //删除自选
        ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    

1  
2  
3  
4  
5  
6  
7  

**ModifyUserSecurityOp**

    enum ModifyUserSecurityOp
    {
        ModifyUserSecurityOp_Unknown = 0;
        ModifyUserSecurityOp_Add = 1; //新增
        ModifyUserSecurityOp_Del = 2; //删除自选
        ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    

1  
2  
3  
4  
5  
6  
7  

**ModifyUserSecurityOp**

    enum ModifyUserSecurityOp
    {
        ModifyUserSecurityOp_Unknown = 0;
        ModifyUserSecurityOp_Add = 1; //新增
        ModifyUserSecurityOp_Del = 2; //删除自选
        ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    

1  
2  
3  
4  
5  
6  
7  

**ModifyUserSecurityOp**

    enum ModifyUserSecurityOp
    {
        ModifyUserSecurityOp_Unknown = 0;
        ModifyUserSecurityOp_Add = 1; //新增
        ModifyUserSecurityOp_Del = 2; //删除自选
        ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    

1  
2  
3  
4  
5  
6  
7  

**ModifyUserSecurityOp**

    enum ModifyUserSecurityOp
    {
        ModifyUserSecurityOp_Unknown = 0;
        ModifyUserSecurityOp_Add = 1; //新增
        ModifyUserSecurityOp_Del = 2; //删除自选
        ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./quote_quote.md#7077)
 期权类型（按行权时间）
-------------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **OptionAreaType**

*   `NONE`
    
    未知
    
*   `AMERICAN`
    
    美式
    
*   `EUROPEAN`
    
    欧式
    
*   `BERMUDA`
    
    百慕大
    

**OptionAreaType**

    enum OptionAreaType
    {
        OptionAreaType_Unknown = 0; //未知
        OptionAreaType_American = 1; //美式
        OptionAreaType_European = 2; //欧式
        OptionAreaType_Bermuda = 3; //百慕大
    }
    

1  
2  
3  
4  
5  
6  
7  

**OptionAreaType**

    enum OptionAreaType
    {
        OptionAreaType_Unknown = 0; //未知
        OptionAreaType_American = 1; //美式
        OptionAreaType_European = 2; //欧式
        OptionAreaType_Bermuda = 3; //百慕大
    }
    

1  
2  
3  
4  
5  
6  
7  

**OptionAreaType**

    enum OptionAreaType
    {
        OptionAreaType_Unknown = 0; //未知
        OptionAreaType_American = 1; //美式
        OptionAreaType_European = 2; //欧式
        OptionAreaType_Bermuda = 3; //百慕大
    }
    

1  
2  
3  
4  
5  
6  
7  

**OptionAreaType**

    enum OptionAreaType
    {
        OptionAreaType_Unknown = 0; //未知
        OptionAreaType_American = 1; //美式
        OptionAreaType_European = 2; //欧式
        OptionAreaType_Bermuda = 3; //百慕大
    }
    

1  
2  
3  
4  
5  
6  
7  

**OptionAreaType**

    enum OptionAreaType
    {
        OptionAreaType_Unknown = 0; //未知
        OptionAreaType_American = 1; //美式
        OptionAreaType_European = 2; //欧式
        OptionAreaType_Bermuda = 3; //百慕大
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./quote_quote.md#3227)
 期权价内/外
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **OptionCondType**

*   `ALL`
    
    所有
    
*   `WITHIN`
    
    价内
    
*   `OUTSIDE`
    
    价外
    

**OptionCondType**

    enum OptionCondType
    {
        OptionCondType_Unknow = 0;
        OptionCondType_WithIn = 1; //价内
        OptionCondType_Outside = 2; //价外
    }
    

1  
2  
3  
4  
5  
6  

**OptionCondType**

    enum OptionCondType
    {
        OptionCondType_Unknow = 0;
        OptionCondType_WithIn = 1; //价内
        OptionCondType_Outside = 2; //价外
    }
    

1  
2  
3  
4  
5  
6  

**OptionCondType**

    enum OptionCondType
    {
        OptionCondType_Unknow = 0;
        OptionCondType_WithIn = 1; //价内
        OptionCondType_Outside = 2; //价外
    }
    

1  
2  
3  
4  
5  
6  

**OptionCondType**

    enum OptionCondType
    {
        OptionCondType_Unknow = 0;
        OptionCondType_WithIn = 1; //价内
        OptionCondType_Outside = 2; //价外
    }
    

1  
2  
3  
4  
5  
6  

**OptionCondType**

    enum OptionCondType
    {
        OptionCondType_Unknow = 0;
        OptionCondType_WithIn = 1; //价内
        OptionCondType_Outside = 2; //价外
    }
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#3713)
 期权类型（按方向）
-----------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **OptionType**

*   `ALL`
    
    所有
    
*   `CALL`
    
    看涨期权
    
*   `PUT`
    
    看跌期权
    

**OptionType**

    enum OptionType
    {
        OptionType_Unknown = 0; //未知
        OptionType_Call = 1; //看涨期权
        OptionType_Put = 2; //看跌期权
    };
    

1  
2  
3  
4  
5  
6  

**OptionType**

    enum OptionType
    {
        OptionType_Unknown = 0; //未知
        OptionType_Call = 1; //看涨期权
        OptionType_Put = 2; //看跌期权
    };
    

1  
2  
3  
4  
5  
6  

**OptionType**

    enum OptionType
    {
        OptionType_Unknown = 0; //未知
        OptionType_Call = 1; //看涨期权
        OptionType_Put = 2; //看跌期权
    };
    

1  
2  
3  
4  
5  
6  

**OptionType**

    enum OptionType
    {
        OptionType_Unknown = 0; //未知
        OptionType_Call = 1; //看涨期权
        OptionType_Put = 2; //看跌期权
    };
    

1  
2  
3  
4  
5  
6  

**OptionType**

    enum OptionType
    {
        OptionType_Unknown = 0; //未知
        OptionType_Call = 1; //看涨期权
        OptionType_Put = 2; //看跌期权
    };
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#1362)
 板块集合类型
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **Plate**

*   `ALL`
    
    所有板块
    
*   `INDUSTRY`
    
    行业板块
    
*   `REGION`
    
    地域板块
    
    港美股市场的地域分类数据暂为空
    
*   `CONCEPT`
    
    概念板块
    
*   `OTHER`
    
    其他板块
    
    仅用于 [获取股票所属板块](./quote_get-owner-plate.md)
     接口的返回，不可作为其他接口的请求参数
    

**PlateSetType**

    enum PlateSetType
    {
        PlateSetType_All = 0; //所有板块
        PlateSetType_Industry = 1; //行业板块
        PlateSetType_Region = 2; //地域板块,港美股市场的地域分类数据暂为空
        PlateSetType_Concept = 3; //概念板块
        PlateSetType_Other = 4; //其他板块, 仅用于3207（获取股票所属板块）协议返回,不可作为其他协议的请求参数
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**PlateSetType**

    enum PlateSetType
    {
        PlateSetType_All = 0; //所有板块
        PlateSetType_Industry = 1; //行业板块
        PlateSetType_Region = 2; //地域板块,港美股市场的地域分类数据暂为空
        PlateSetType_Concept = 3; //概念板块
        PlateSetType_Other = 4; //其他板块, 仅用于3207（获取股票所属板块）协议返回,不可作为其他协议的请求参数
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**PlateSetType**

    enum PlateSetType
    {
        PlateSetType_All = 0; //所有板块
        PlateSetType_Industry = 1; //行业板块
        PlateSetType_Region = 2; //地域板块,港美股市场的地域分类数据暂为空
        PlateSetType_Concept = 3; //概念板块
        PlateSetType_Other = 4; //其他板块, 仅用于3207（获取股票所属板块）协议返回,不可作为其他协议的请求参数
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**PlateSetType**

    enum PlateSetType
    {
        PlateSetType_All = 0; //所有板块
        PlateSetType_Industry = 1; //行业板块
        PlateSetType_Region = 2; //地域板块,港美股市场的地域分类数据暂为空
        PlateSetType_Concept = 3; //概念板块
        PlateSetType_Other = 4; //其他板块, 仅用于3207（获取股票所属板块）协议返回,不可作为其他协议的请求参数
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**PlateSetType**

    enum PlateSetType
    {
        PlateSetType_All = 0; //所有板块
        PlateSetType_Industry = 1; //行业板块
        PlateSetType_Region = 2; //地域板块,港美股市场的地域分类数据暂为空
        PlateSetType_Concept = 3; //概念板块
        PlateSetType_Other = 4; //其他板块, 仅用于3207（获取股票所属板块）协议返回,不可作为其他协议的请求参数
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

[#](./quote_quote.md#1059)
 到价提醒频率
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **PriceReminderFreq**

*   `NONE`
    
    未知
    
*   `ALWAYS`
    
    持续提醒
    
*   `ONCE_A_DAY`
    
    每日一次
    
*   `ONCE`
    
    仅提醒一次
    

**PriceReminderFreq**

    enum PriceReminderFreq
    {
        PriceReminderFreq_Unknown = 0; // 未知
        PriceReminderFreq_Always = 1; // 持续提醒
        PriceReminderFreq_OnceADay = 2; // 每日一次
        PriceReminderFreq_OnlyOnce = 3; // 仅提醒一次
    }
    

1  
2  
3  
4  
5  
6  
7  

**PriceReminderFreq**

    enum PriceReminderFreq
    {
        PriceReminderFreq_Unknown = 0; // 未知
        PriceReminderFreq_Always = 1; // 持续提醒
        PriceReminderFreq_OnceADay = 2; // 每日一次
        PriceReminderFreq_OnlyOnce = 3; // 仅提醒一次
    }
    

1  
2  
3  
4  
5  
6  
7  

**PriceReminderFreq**

    enum PriceReminderFreq
    {
        PriceReminderFreq_Unknown = 0; // 未知
        PriceReminderFreq_Always = 1; // 持续提醒
        PriceReminderFreq_OnceADay = 2; // 每日一次
        PriceReminderFreq_OnlyOnce = 3; // 仅提醒一次
    }
    

1  
2  
3  
4  
5  
6  
7  

**PriceReminderFreq**

    enum PriceReminderFreq
    {
        PriceReminderFreq_Unknown = 0; // 未知
        PriceReminderFreq_Always = 1; // 持续提醒
        PriceReminderFreq_OnceADay = 2; // 每日一次
        PriceReminderFreq_OnlyOnce = 3; // 仅提醒一次
    }
    

1  
2  
3  
4  
5  
6  
7  

**PriceReminderFreq**

    enum PriceReminderFreq
    {
        PriceReminderFreq_Unknown = 0; // 未知
        PriceReminderFreq_Always = 1; // 持续提醒
        PriceReminderFreq_OnceADay = 2; // 每日一次
        PriceReminderFreq_OnlyOnce = 3; // 仅提醒一次
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./quote_quote.md#5160)
 到价提醒类型
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **PriceReminderType**

*   `NONE`
    
    未知
    
*   `PRICE_UP`
    
    价格涨到
    
*   `PRICE_DOWN`
    
    价格跌到
    
*   `CHANGE_RATE_UP`
    
    日涨幅超
    
    该字段为百分比字段，设置时填 20 表示 20%
    
*   `CHANGE_RATE_DOWN`
    
    日跌幅超
    
    该字段为百分比字段，设置时填 20 表示 20%
    
*   `FIVE_MIN_CHANGE_RATE_UP`
    
    5 分钟涨幅超
    
    该字段为百分比字段，设置时填 20 表示 20%
    
*   `FIVE_MIN_CHANGE_RATE_DOWN`
    
    5 分钟跌幅超
    
    该字段为百分比字段，设置时填 20 表示 20%
    
*   `VOLUME_UP`
    
    成交量超过
    
*   `TURNOVER_UP`
    
    成交额超过
    
*   `TURNOVER_RATE_UP`
    
    换手率超过
    
    该字段为百分比字段，设置时填 20 表示 20%
    
*   `BID_PRICE_UP`
    
    买一价高于
    
*   `ASK_PRICE_DOWN`
    
    卖一价低于
    
*   `BID_VOL_UP`
    
    买一量高于
    
*   `ASK_VOL_UP`
    
    卖一量高于
    
*   `THREE_MIN_CHANGE_RATE_UP`
    
    3 分钟涨幅超
    
    该字段为百分比字段，设置时填 20 表示 20%
    
*   `THREE_MIN_CHANGE_RATE_DOWN`
    
    3 分钟跌幅超
    
    该字段为百分比字段，设置时填 20 表示 20%
    

**PriceReminderType**

    enum PriceReminderType
    {
        PriceReminderType_Unknown = 0; // 未知
        PriceReminderType_PriceUp = 1; // 价格涨到
        PriceReminderType_PriceDown = 2; // 价格跌到
        PriceReminderType_ChangeRateUp = 3; // 日涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_ChangeRateDown = 4; // 日跌幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_5MinChangeRateUp = 5; // 5 分钟涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_5MinChangeRateDown = 6; // 5 分钟跌幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_VolumeUp = 7; // 成交量超过
        PriceReminderType_TurnoverUp = 8; // 成交额超过
        PriceReminderType_TurnoverRateUp = 9; // 换手率超过（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_BidPriceUp = 10; // 买一价高于
        PriceReminderType_AskPriceDown = 11; // 卖一价低于
        PriceReminderType_BidVolUp = 12; // 买一量高于    
        PriceReminderType_AskVolUp = 13; // 卖一量高于
        PriceReminderType_3MinChangeRateUp = 14; // 3 分钟涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_3MinChangeRateDown = 15; // 3 分钟跌幅超（该字段为百分比字段，设置时填 20 表示 20%）
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**PriceReminderType**

    enum PriceReminderType
    {
        PriceReminderType_Unknown = 0; // 未知
        PriceReminderType_PriceUp = 1; // 价格涨到
        PriceReminderType_PriceDown = 2; // 价格跌到
        PriceReminderType_ChangeRateUp = 3; // 日涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_ChangeRateDown = 4; // 日跌幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_5MinChangeRateUp = 5; // 5 分钟涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_5MinChangeRateDown = 6; // 5 分钟跌幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_VolumeUp = 7; // 成交量超过
        PriceReminderType_TurnoverUp = 8; // 成交额超过
        PriceReminderType_TurnoverRateUp = 9; // 换手率超过（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_BidPriceUp = 10; // 买一价高于
        PriceReminderType_AskPriceDown = 11; // 卖一价低于
        PriceReminderType_BidVolUp = 12; // 买一量高于    
        PriceReminderType_AskVolUp = 13; // 卖一量高于
        PriceReminderType_3MinChangeRateUp = 14; // 3 分钟涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_3MinChangeRateDown = 15; // 3 分钟跌幅超（该字段为百分比字段，设置时填 20 表示 20%）    
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**PriceReminderType**

    enum PriceReminderType
    {
        PriceReminderType_Unknown = 0; // 未知
        PriceReminderType_PriceUp = 1; // 价格涨到
        PriceReminderType_PriceDown = 2; // 价格跌到
        PriceReminderType_ChangeRateUp = 3; // 日涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_ChangeRateDown = 4; // 日跌幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_5MinChangeRateUp = 5; // 5 分钟涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_5MinChangeRateDown = 6; // 5 分钟跌幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_VolumeUp = 7; // 成交量超过
        PriceReminderType_TurnoverUp = 8; // 成交额超过
        PriceReminderType_TurnoverRateUp = 9; // 换手率超过（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_BidPriceUp = 10; // 买一价高于
        PriceReminderType_AskPriceDown = 11; // 卖一价低于
        PriceReminderType_BidVolUp = 12; // 买一量高于    
        PriceReminderType_AskVolUp = 13; // 卖一量高于
        PriceReminderType_3MinChangeRateUp = 14; // 3 分钟涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_3MinChangeRateDown = 15; // 3 分钟跌幅超（该字段为百分比字段，设置时填 20 表示 20%）    
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**PriceReminderType**

    enum PriceReminderType
    {
        PriceReminderType_Unknown = 0; // 未知
        PriceReminderType_PriceUp = 1; // 价格涨到
        PriceReminderType_PriceDown = 2; // 价格跌到
        PriceReminderType_ChangeRateUp = 3; // 日涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_ChangeRateDown = 4; // 日跌幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_5MinChangeRateUp = 5; // 5 分钟涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_5MinChangeRateDown = 6; // 5 分钟跌幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_VolumeUp = 7; // 成交量超过
        PriceReminderType_TurnoverUp = 8; // 成交额超过
        PriceReminderType_TurnoverRateUp = 9; // 换手率超过（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_BidPriceUp = 10; // 买一价高于
        PriceReminderType_AskPriceDown = 11; // 卖一价低于
        PriceReminderType_BidVolUp = 12; // 买一量高于    
        PriceReminderType_AskVolUp = 13; // 卖一量高于
        PriceReminderType_3MinChangeRateUp = 14; // 3 分钟涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_3MinChangeRateDown = 15; // 3 分钟跌幅超（该字段为百分比字段，设置时填 20 表示 20%）    
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**PriceReminderType**

    enum PriceReminderType
    {
        PriceReminderType_Unknown = 0; // 未知
        PriceReminderType_PriceUp = 1; // 价格涨到
        PriceReminderType_PriceDown = 2; // 价格跌到
        PriceReminderType_ChangeRateUp = 3; // 日涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_ChangeRateDown = 4; // 日跌幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_5MinChangeRateUp = 5; // 5 分钟涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_5MinChangeRateDown = 6; // 5 分钟跌幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_VolumeUp = 7; // 成交量超过
        PriceReminderType_TurnoverUp = 8; // 成交额超过
        PriceReminderType_TurnoverRateUp = 9; // 换手率超过（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_BidPriceUp = 10; // 买一价高于
        PriceReminderType_AskPriceDown = 11; // 卖一价低于
        PriceReminderType_BidVolUp = 12; // 买一量高于    
        PriceReminderType_AskVolUp = 13; // 卖一量高于
        PriceReminderType_3MinChangeRateUp = 14; // 3 分钟涨幅超（该字段为百分比字段，设置时填 20 表示 20%）
        PriceReminderType_3MinChangeRateDown = 15; // 3 分钟跌幅超（该字段为百分比字段，设置时填 20 表示 20%）    
    }
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#6407)
 窝轮价内/外
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **PriceType**

*   `UNKNOW`
    
    未知
    
*   `OUTSIDE`
    
    价外，界内证表示界外
    
*   `WITH_IN`
    
    价内，界内证表示界内
    

**PriceType**

    enum PriceType
    {
        PriceType_Unknow = 0;
        PriceType_Outside = 1; //价外，界内证表示界外
        PriceType_WithIn = 2; //价内，界内证表示界内
    }
    

1  
2  
3  
4  
5  
6  

**PriceType**

    enum PriceType
    {
        PriceType_Unknow = 0;
        PriceType_Outside = 1; //价外，界内证表示界外
        PriceType_WithIn = 2; //价内，界内证表示界内
    }
    

1  
2  
3  
4  
5  
6  

**PriceType**

    enum PriceType
    {
        PriceType_Unknow = 0;
        PriceType_Outside = 1; //价外，界内证表示界外
        PriceType_WithIn = 2; //价内，界内证表示界内
    }
    

1  
2  
3  
4  
5  
6  

**PriceType**

    enum PriceType
    {
        PriceType_Unknow = 0;
        PriceType_Outside = 1; //价外，界内证表示界外
        PriceType_WithIn = 2; //价内，界内证表示界内
    }
    

1  
2  
3  
4  
5  
6  

**PriceType**

    enum PriceType
    {
        PriceType_Unknow = 0;
        PriceType_Outside = 1; //价外，界内证表示界外
        PriceType_WithIn = 2; //价内，界内证表示界内
    }
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#7025)
 逐笔推送类型
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **PushDataType**

*   `UNKNOW`
    
    未知
    
*   `REALTIME`
    
    实时推送的数据
    
*   `BYDISCONN`
    
    与富途服务器连接断开期间，拉取补充的数据
    
    最多 50 个
    
*   `CACHE`
    
    非实时非连接断开补充数据
    

**PushDataType**

    enum PushDataType
    {
        PushDataType_Unknow = 0;
        PushDataType_Realtime = 1; //实时推送的数据
        PushDataType_ByDisConn = 2; //对后台行情连接断开期间拉取补充的数据（最多50个）
        PushDataType_Cache = 3; //非实时非连接断开补充数据
    }
    

1  
2  
3  
4  
5  
6  
7  

**PushDataType**

    enum PushDataType
    {
        PushDataType_Unknow = 0;
        PushDataType_Realtime = 1; //实时推送的数据
        PushDataType_ByDisConn = 2; //对后台行情连接断开期间拉取补充的数据（最多50个）
        PushDataType_Cache = 3; //非实时非连接断开补充数据
    }
    

1  
2  
3  
4  
5  
6  
7  

**PushDataType**

    enum PushDataType
    {
        PushDataType_Unknow = 0;
        PushDataType_Realtime = 1; //实时推送的数据
        PushDataType_ByDisConn = 2; //对后台行情连接断开期间拉取补充的数据（最多50个）
        PushDataType_Cache = 3; //非实时非连接断开补充数据
    }
    

1  
2  
3  
4  
5  
6  
7  

**PushDataType**

    enum PushDataType
    {
        PushDataType_Unknow = 0;
        PushDataType_Realtime = 1; //实时推送的数据
        PushDataType_ByDisConn = 2; //对后台行情连接断开期间拉取补充的数据（最多50个）
        PushDataType_Cache = 3; //非实时非连接断开补充数据
    }
    

1  
2  
3  
4  
5  
6  
7  

**PushDataType**

    enum PushDataType
    {
        PushDataType_Unknow = 0;
        PushDataType_Realtime = 1; //实时推送的数据
        PushDataType_ByDisConn = 2; //对后台行情连接断开期间拉取补充的数据（最多50个）
        PushDataType_Cache = 3; //非实时非连接断开补充数据
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./quote_quote.md#427)
 行情市场
-----------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **Market**

*   `NONE`
    
    未知市场
    
*   `HK`
    
    香港市场
    
*   `US`
    
    美国市场
    
*   `SH`
    
    沪股市场
    
*   `SZ`
    
    深股市场
    
*   `SG`
    
    新加坡市场
    
*   `JP`
    
    日本市场
    
*   `AU`
    
    澳大利亚市场
    
*   `CA`
    
    加拿大市场
    
*   `MY`
    
    马来西亚市场
    
*   `FX`
    
    外汇市场
    

**QotMarket**

    enum QotMarket
    {
        QotMarket_Unknown = 0; //未知市场
        QotMarket_HK_Security = 1; //香港市场
        QotMarket_HK_Future = 2; //港期货（已废弃，使用 QotMarket_HK_Security 即可）
        QotMarket_US_Security = 11; //美国市场
        QotMarket_CNSH_Security = 21; //沪股市场
        QotMarket_CNSZ_Security = 22; //深股市场
        QotMarket_SG_Security = 31; //新加坡市场
        QotMarket_JP_Security = 41; //日本市场
        QotMarket_AU_Security = 51; //澳大利亚市场
        QotMarket_MY_Security = 61; //马来西亚市场
        QotMarket_CA_Security = 71; //加拿大市场
        QotMarket_FX_Security = 81; //外汇市场
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

**QotMarket**

    enum QotMarket
    {
        QotMarket_Unknown = 0; //未知市场
        QotMarket_HK_Security = 1; //香港市场
        QotMarket_HK_Future = 2; //港期货（已废弃，使用 QotMarket_HK_Security 即可）
        QotMarket_US_Security = 11; //美国市场
        QotMarket_CNSH_Security = 21; //沪股市场
        QotMarket_CNSZ_Security = 22; //深股市场
        QotMarket_SG_Security = 31; //新加坡市场
        QotMarket_JP_Security = 41; //日本市场
        QotMarket_AU_Security = 51; //澳大利亚市场
        QotMarket_MY_Security = 61; //马来西亚市场
        QotMarket_CA_Security = 71; //加拿大市场
        QotMarket_FX_Security = 81; //外汇市场
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

**QotMarket**

    enum QotMarket
    {
        QotMarket_Unknown = 0; //未知市场
        QotMarket_HK_Security = 1; //香港市场
        QotMarket_HK_Future = 2; //港期货（已废弃，使用 QotMarket_HK_Security 即可）
        QotMarket_US_Security = 11; //美国市场
        QotMarket_CNSH_Security = 21; //沪股市场
        QotMarket_CNSZ_Security = 22; //深股市场
        QotMarket_SG_Security = 31; //新加坡市场
        QotMarket_JP_Security = 41; //日本市场
        QotMarket_AU_Security = 51; //澳大利亚市场
        QotMarket_MY_Security = 61; //马来西亚市场
        QotMarket_CA_Security = 71; //加拿大市场
        QotMarket_FX_Security = 81; //外汇市场
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

**QotMarket**

    enum QotMarket
    {
        QotMarket_Unknown = 0; //未知市场
        QotMarket_HK_Security = 1; //香港市场
        QotMarket_HK_Future = 2; //港期货（已废弃，使用 QotMarket_HK_Security 即可）
        QotMarket_US_Security = 11; //美国市场
        QotMarket_CNSH_Security = 21; //沪股市场
        QotMarket_CNSZ_Security = 22; //深股市场
        QotMarket_SG_Security = 31; //新加坡市场
        QotMarket_JP_Security = 41; //日本市场
        QotMarket_AU_Security = 51; //澳大利亚市场
        QotMarket_MY_Security = 61; //马来西亚市场
        QotMarket_CA_Security = 71; //加拿大市场
        QotMarket_FX_Security = 81; //外汇市场
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

**QotMarket**

    enum QotMarket
    {
        QotMarket_Unknown = 0; //未知市场
        QotMarket_HK_Security = 1; //香港市场
        QotMarket_HK_Future = 2; //港期货（已废弃，使用 QotMarket_HK_Security 即可）
        QotMarket_US_Security = 11; //美国市场
        QotMarket_CNSH_Security = 21; //沪股市场
        QotMarket_CNSZ_Security = 22; //深股市场
        QotMarket_SG_Security = 31; //新加坡市场
        QotMarket_JP_Security = 41; //日本市场
        QotMarket_AU_Security = 51; //澳大利亚市场
        QotMarket_MY_Security = 61; //马来西亚市场
        QotMarket_CA_Security = 71; //加拿大市场
        QotMarket_FX_Security = 81; //外汇市场
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  

[#](./quote_quote.md#1252)
 市场状态
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **MarketState**

各市场状态的对应时段：[点击这里](./qa_quote.md#2090)
了解更多

*   `NONE`
    
    无交易
    
*   `AUCTION`
    
    盘前竞价
    
*   `WAITING_OPEN`
    
    等待开盘
    
*   `MORNING`
    
    早盘
    
*   `REST`
    
    午间休市
    
*   `AFTERNOON`
    
    午盘 / 美股持续交易时段
    
*   `CLOSED`
    
    收盘
    
*   `PRE_MARKET_BEGIN`
    
    美股盘前交易时段
    
*   `PRE_MARKET_END`
    
    美股盘前交易结束
    
*   `AFTER_HOURS_BEGIN`
    
    美股盘后交易时段
    
*   `AFTER_HOURS_END`
    
    美股盘后结束
    
*   `OVERNIGHT`
    
    美股夜盘交易时段
    
*   `NIGHT_OPEN`
    
    夜市交易时段
    
*   `NIGHT_END`
    
    夜市收盘
    
*   `NIGHT`
    
    美指期权夜市交易时段
    
*   `TRADE_AT_LAST`
    
    美指期权盘尾交易时段
    
*   `FUTURE_DAY_OPEN`
    
    日市交易时段
    
*   `FUTURE_DAY_BREAK`
    
    日市休市
    
*   `FUTURE_DAY_CLOSE`
    
    日市收盘
    
*   `FUTURE_DAY_WAIT_OPEN`
    
    期货待开盘
    
*   `HK_CAS`
    
    港股盘后竞价
    
*   `FUTURE_NIGHT_WAIT`
    
    夜市等待开盘（已废弃）
    
*   `FUTURE_AFTERNOON`
    
    期货下午开盘（已废弃）
    
*   `FUTURE_SWITCH_DATE`
    
    美期待开盘
    
*   `FUTURE_OPEN`
    
    美期交易时段
    
*   `FUTURE_BREAK`
    
    美期中盘休息
    
*   `FUTURE_BREAK_OVER`
    
    美期休息后交易时段
    
*   `FUTURE_CLOSE`
    
    美期收盘
    
*   `STIB_AFTER_HOURS_WAIT`
    
    科创板的盘后撮合时段（已废弃）
    
*   `STIB_AFTER_HOURS_BEGIN`
    
    科创板的盘后交易开始（已废弃）
    
*   `STIB_AFTER_HOURS_END`
    
    科创板的盘后交易结束（已废弃）
    

**QotMarketState**

各市场状态的对应时段：[点击这里](./qa_quote.md#2090)
了解更多

    enum QotMarketState
    {
        QotMarketState_None = 0; // 无交易
        QotMarketState_Auction = 1; // 盘前竞价 
        QotMarketState_WaitingOpen = 2; // 等待开盘
        QotMarketState_Morning = 3; // 早盘 
        QotMarketState_Rest = 4; // 午间休市 
        QotMarketState_Afternoon = 5; // 午盘 / 美股持续交易时段
        QotMarketState_Closed = 6; // 收盘
        QotMarketState_PreMarketBegin = 8; // 美股盘前交易时段
        QotMarketState_PreMarketEnd = 9; // 美股盘前交易结束 
        QotMarketState_AfterHoursBegin = 10; // 美股盘后交易时段
        QotMarketState_AfterHoursEnd = 11; // 美股收盘 
        QotMarketState_NightOpen = 13; // 夜市交易时段
        QotMarketState_NightEnd = 14; // 夜市收盘 
        QotMarketState_FutureDayOpen = 15; // 日市交易时段
        QotMarketState_FutureDayBreak = 16; // 日市休市 
        QotMarketState_FutureDayClose = 17; // 日市收盘 
        QotMarketState_FutureDayWaitForOpen = 18; // 期货待开盘 
        QotMarketState_HkCas = 19; // 盘后竞价,港股市场增加 CAS 机制对应的市场状态    
        QotMarketState_FutureNightWait = 20; // 夜市等待开盘（已废弃）
        QotMarketState_FutureAfternoon = 21; // 期货下午开盘（已废弃）
        //美国期货新增加状态
        QotMarketState_FutureSwitchDate = 22; // 美期待开盘
        QotMarketState_FutureOpen = 23; // 美期交易时段
        QotMarketState_FutureBreak = 24; // 美期中盘休息
        QotMarketState_FutureBreakOver = 25; // 美期休息后交易时段
        QotMarketState_FutureClose = 26; // 美期收盘
        //科创板新增状态
        QotMarketState_StibAfterHoursWait = 27; // 科创板的盘后撮合时段（已废弃）
        QotMarketState_StibAfterHoursBegin = 28; // 科创板的盘后交易开始（已废弃）
        QotMarketState_StibAfterHoursEnd = 29; // 科创板的盘后交易结束（已废弃）
        //美指期权新增加状态
        QotMarketState_NIGHT = 32; // 美指期权夜市交易时段
        QotMarketState_TRADE_AT_LAST = 35; // 美指期权盘尾交易时段
        QotMarketState_OVERNIGHT = 37;  // 美股夜盘交易时段
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**QotMarketState**

各市场状态的对应时段：[点击这里](./qa_quote.md#2090)
了解更多

    enum QotMarketState
    {
        QotMarketState_None = 0; // 无交易
        QotMarketState_Auction = 1; // 盘前竞价 
        QotMarketState_WaitingOpen = 2; // 等待开盘
        QotMarketState_Morning = 3; // 早盘 
        QotMarketState_Rest = 4; // 午间休市 
        QotMarketState_Afternoon = 5; // 午盘 / 美股持续交易时段
        QotMarketState_Closed = 6; // 收盘
        QotMarketState_PreMarketBegin = 8; // 美股盘前交易时段
        QotMarketState_PreMarketEnd = 9; // 美股盘前交易结束 
        QotMarketState_AfterHoursBegin = 10; // 美股盘后交易时段
        QotMarketState_AfterHoursEnd = 11; // 美股收盘 
        QotMarketState_NightOpen = 13; // 夜市交易时段
        QotMarketState_NightEnd = 14; // 夜市收盘 
        QotMarketState_FutureDayOpen = 15; // 日市交易时段
        QotMarketState_FutureDayBreak = 16; // 日市休市 
        QotMarketState_FutureDayClose = 17; // 日市收盘 
        QotMarketState_FutureDayWaitForOpen = 18; // 期货待开盘 
        QotMarketState_HkCas = 19; // 盘后竞价,港股市场增加 CAS 机制对应的市场状态    
        QotMarketState_FutureNightWait = 20; // 夜市等待开盘（已废弃）
        QotMarketState_FutureAfternoon = 21; // 期货下午开盘（已废弃）
        //美国期货新增加状态
        QotMarketState_FutureSwitchDate = 22; // 美期待开盘
        QotMarketState_FutureOpen = 23; // 美期交易时段
        QotMarketState_FutureBreak = 24; // 美期中盘休息
        QotMarketState_FutureBreakOver = 25; // 美期休息后交易时段
        QotMarketState_FutureClose = 26; // 美期收盘
        //科创板新增状态
        QotMarketState_StibAfterHoursWait = 27; // 科创板的盘后撮合时段（已废弃）
        QotMarketState_StibAfterHoursBegin = 28; // 科创板的盘后交易开始（已废弃）
        QotMarketState_StibAfterHoursEnd = 29; // 科创板的盘后交易结束（已废弃）
        //美指期权新增加状态
        QotMarketState_NIGHT = 32; // 美指期权夜市交易时段
        QotMarketState_TRADE_AT_LAST = 35; // 美指期权盘尾交易时段
        QotMarketState_OVERNIGHT = 37;  // 美股夜盘交易时段
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**QotMarketState**

各市场状态的对应时段：[点击这里](./qa_quote.md#2090)
了解更多

    enum QotMarketState
    {
        QotMarketState_None = 0; // 无交易
        QotMarketState_Auction = 1; // 盘前竞价 
        QotMarketState_WaitingOpen = 2; // 等待开盘
        QotMarketState_Morning = 3; // 早盘 
        QotMarketState_Rest = 4; // 午间休市 
        QotMarketState_Afternoon = 5; // 午盘 / 美股持续交易时段
        QotMarketState_Closed = 6; // 收盘
        QotMarketState_PreMarketBegin = 8; // 美股盘前交易时段
        QotMarketState_PreMarketEnd = 9; // 美股盘前交易结束 
        QotMarketState_AfterHoursBegin = 10; // 美股盘后交易时段
        QotMarketState_AfterHoursEnd = 11; // 美股收盘 
        QotMarketState_NightOpen = 13; // 夜市交易时段
        QotMarketState_NightEnd = 14; // 夜市收盘 
        QotMarketState_FutureDayOpen = 15; // 日市交易时段
        QotMarketState_FutureDayBreak = 16; // 日市休市 
        QotMarketState_FutureDayClose = 17; // 日市收盘 
        QotMarketState_FutureDayWaitForOpen = 18; // 期货待开盘 
        QotMarketState_HkCas = 19; // 盘后竞价,港股市场增加 CAS 机制对应的市场状态    
        QotMarketState_FutureNightWait = 20; // 夜市等待开盘（已废弃）
        QotMarketState_FutureAfternoon = 21; // 期货下午开盘（已废弃）
        //美国期货新增加状态
        QotMarketState_FutureSwitchDate = 22; // 美期待开盘
        QotMarketState_FutureOpen = 23; // 美期交易时段
        QotMarketState_FutureBreak = 24; // 美期中盘休息
        QotMarketState_FutureBreakOver = 25; // 美期休息后交易时段
        QotMarketState_FutureClose = 26; // 美期收盘
        //科创板新增状态
        QotMarketState_StibAfterHoursWait = 27; // 科创板的盘后撮合时段（已废弃）
        QotMarketState_StibAfterHoursBegin = 28; // 科创板的盘后交易开始（已废弃）
        QotMarketState_StibAfterHoursEnd = 29; // 科创板的盘后交易结束（已废弃）
        //美指期权新增加状态
        QotMarketState_NIGHT = 32; // 美指期权夜市交易时段
        QotMarketState_TRADE_AT_LAST = 35; // 美指期权盘尾交易时段
        QotMarketState_OVERNIGHT = 37;  // 美股夜盘交易时段
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**QotMarketState**

各市场状态的对应时段：[点击这里](./qa_quote.md#2090)
了解更多

    enum QotMarketState
    {
        QotMarketState_None = 0; // 无交易
        QotMarketState_Auction = 1; // 盘前竞价 
        QotMarketState_WaitingOpen = 2; // 等待开盘
        QotMarketState_Morning = 3; // 早盘 
        QotMarketState_Rest = 4; // 午间休市 
        QotMarketState_Afternoon = 5; // 午盘 / 美股持续交易时段
        QotMarketState_Closed = 6; // 收盘
        QotMarketState_PreMarketBegin = 8; // 美股盘前交易时段
        QotMarketState_PreMarketEnd = 9; // 美股盘前交易结束 
        QotMarketState_AfterHoursBegin = 10; // 美股盘后交易时段
        QotMarketState_AfterHoursEnd = 11; // 美股收盘 
        QotMarketState_NightOpen = 13; // 夜市交易时段
        QotMarketState_NightEnd = 14; // 夜市收盘 
        QotMarketState_FutureDayOpen = 15; // 日市交易时段
        QotMarketState_FutureDayBreak = 16; // 日市休市 
        QotMarketState_FutureDayClose = 17; // 日市收盘 
        QotMarketState_FutureDayWaitForOpen = 18; // 期货待开盘 
        QotMarketState_HkCas = 19; // 盘后竞价,港股市场增加 CAS 机制对应的市场状态    
        QotMarketState_FutureNightWait = 20; // 夜市等待开盘（已废弃）
        QotMarketState_FutureAfternoon = 21; // 期货下午开盘（已废弃）
        //美国期货新增加状态
        QotMarketState_FutureSwitchDate = 22; // 美期待开盘
        QotMarketState_FutureOpen = 23; // 美期交易时段
        QotMarketState_FutureBreak = 24; // 美期中盘休息
        QotMarketState_FutureBreakOver = 25; // 美期休息后交易时段
        QotMarketState_FutureClose = 26; // 美期收盘
        //科创板新增状态
        QotMarketState_StibAfterHoursWait = 27; // 科创板的盘后撮合时段（已废弃）
        QotMarketState_StibAfterHoursBegin = 28; // 科创板的盘后交易开始（已废弃）
        QotMarketState_StibAfterHoursEnd = 29; // 科创板的盘后交易结束（已废弃）
        //美指期权新增加状态
        QotMarketState_NIGHT = 32; // 美指期权夜市交易时段
        QotMarketState_TRADE_AT_LAST = 35; // 美指期权盘尾交易时段
        QotMarketState_OVERNIGHT = 37;  // 美股夜盘交易时段
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**QotMarketState**

各市场状态的对应时段：[点击这里](./qa_quote.md#2090)
了解更多

    enum QotMarketState
    {
        QotMarketState_None = 0; // 无交易
        QotMarketState_Auction = 1; // 盘前竞价 
        QotMarketState_WaitingOpen = 2; // 等待开盘
        QotMarketState_Morning = 3; // 早盘 
        QotMarketState_Rest = 4; // 午间休市 
        QotMarketState_Afternoon = 5; // 午盘 / 美股持续交易时段
        QotMarketState_Closed = 6; // 收盘
        QotMarketState_PreMarketBegin = 8; // 美股盘前交易时段
        QotMarketState_PreMarketEnd = 9; // 美股盘前交易结束 
        QotMarketState_AfterHoursBegin = 10; // 美股盘后交易时段
        QotMarketState_AfterHoursEnd = 11; // 美股收盘 
        QotMarketState_NightOpen = 13; // 夜市交易时段
        QotMarketState_NightEnd = 14; // 夜市收盘 
        QotMarketState_FutureDayOpen = 15; // 日市交易时段
        QotMarketState_FutureDayBreak = 16; // 日市休市 
        QotMarketState_FutureDayClose = 17; // 日市收盘 
        QotMarketState_FutureDayWaitForOpen = 18; // 期货待开盘 
        QotMarketState_HkCas = 19; // 盘后竞价,港股市场增加 CAS 机制对应的市场状态    
        QotMarketState_FutureNightWait = 20; // 夜市等待开盘（已废弃）
        QotMarketState_FutureAfternoon = 21; // 期货下午开盘（已废弃）
        //美国期货新增加状态
        QotMarketState_FutureSwitchDate = 22; // 美期待开盘
        QotMarketState_FutureOpen = 23; // 美期交易时段
        QotMarketState_FutureBreak = 24; // 美期中盘休息
        QotMarketState_FutureBreakOver = 25; // 美期休息后交易时段
        QotMarketState_FutureClose = 26; // 美期收盘
        //科创板新增状态
        QotMarketState_StibAfterHoursWait = 27; // 科创板的盘后撮合时段（已废弃）
        QotMarketState_StibAfterHoursBegin = 28; // 科创板的盘后交易开始（已废弃）
        QotMarketState_StibAfterHoursEnd = 29; // 科创板的盘后交易结束（已废弃）
        //美指期权新增加状态
        QotMarketState_NIGHT = 32; // 美指期权夜市交易时段
        QotMarketState_TRADE_AT_LAST = 35; // 美指期权盘尾交易时段
        QotMarketState_OVERNIGHT = 37;  // 美股夜盘交易时段
    }
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#9152)
 美股时段
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **Session**

*   `NONE`
    
    未知
    
*   `RTH`
    
    美股盘中时段
    
*   `ETH`
    
    美股盘中+盘前盘后
    
*   `OVERNIGHT`
    
    美股夜盘时段 (仅用于交易接口)
    
*   `ALL`
    
    美股全时段 (用于行情&交易接口)
    

> **Session**

    enum Session
    {
    	Session_NONE = 0; // 未知
    	Session_RTH = 1; // 盘中
    	Session_ETH = 2; // 盘中+盘前盘后
    	Session_ALL = 3; // 全时段
    	Session_OVERNIGHT = 4; // 夜盘
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

> **Session**

    enum Session
    {
    	Session_NONE = 0; // 未知
    	Session_RTH = 1; // 盘中
    	Session_ETH = 2; // 盘中+盘前盘后
    	Session_ALL = 3; // 全时段
    	Session_OVERNIGHT = 4; // 夜盘
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

> **Session**

    enum Session
    {
    	Session_NONE = 0; // 未知
    	Session_RTH = 1; // 盘中
    	Session_ETH = 2; // 盘中+盘前盘后
    	Session_ALL = 3; // 全时段
    	Session_OVERNIGHT = 4; // 夜盘
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

> **Session**

    enum Session
    {
    	Session_NONE = 0; // 未知
    	Session_RTH = 1; // 盘中
    	Session_ETH = 2; // 盘中+盘前盘后
    	Session_ALL = 3; // 全时段
    	Session_OVERNIGHT = 4; // 夜盘
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

> **Session**

    enum Session
    {
    	Session_NONE = 0; // 未知
    	Session_RTH = 1; // 盘中
    	Session_ETH = 2; // 盘中+盘前盘后
    	Session_ALL = 3; // 全时段
    	Session_OVERNIGHT = 4; // 夜盘
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

[#](./quote_quote.md#2867)
 行情权限
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **QotRight**

*   `UNKNOW`
    
    未知
    
*   `BMP`
    
    BMP（此权限不支持订阅）
    
*   `LEVEL1`
    
    Level1
    
*   `LEVEL2`
    
    Level2
    
*   `SF`
    
    港股 SF 高级全盘行情
    
*   `NO`
    
    无权限
    

**QotRight**

    enum QotRight
    {
        QotRight_Unknow = 0; //未知
        QotRight_Bmp = 1; //BMP（此权限不支持订阅）
        QotRight_Level1 = 2; //Level1
        QotRight_Level2 = 3; //Level2
        QotRight_SF = 4; //SF 高级行情
        QotRight_No = 5; //无权限
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**QotRight**

    enum QotRight
    {
        QotRight_Unknow = 0; //未知
        QotRight_Bmp = 1; //BMP（此权限不支持订阅）
        QotRight_Level1 = 2; //Level1
        QotRight_Level2 = 3; //Level2
        QotRight_SF = 4; //SF 高级行情
        QotRight_No = 5; //无权限
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**QotRight**

    enum QotRight
    {
        QotRight_Unknow = 0; //未知
        QotRight_Bmp = 1; //BMP（此权限不支持订阅）
        QotRight_Level1 = 2; //Level1
        QotRight_Level2 = 3; //Level2
        QotRight_SF = 4; //SF 高级行情
        QotRight_No = 5; //无权限
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**QotRight**

    enum QotRight
    {
        QotRight_Unknow = 0; //未知
        QotRight_Bmp = 1; //BMP（此权限不支持订阅）
        QotRight_Level1 = 2; //Level1
        QotRight_Level2 = 3; //Level2
        QotRight_SF = 4; //SF 高级行情
        QotRight_No = 5; //无权限
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**QotRight**

    enum QotRight
    {
        QotRight_Unknow = 0; //未知
        QotRight_Bmp = 1; //BMP（此权限不支持订阅）
        QotRight_Level1 = 2; //Level1
        QotRight_Level2 = 3; //Level2
        QotRight_SF = 4; //SF 高级行情
        QotRight_No = 5; //无权限
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

[#](./quote_quote.md#2911)
 关联数据类型
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **SecurityReferenceType**

*   `UNKNOW`
    
    未知
    
*   `WARRANT`
    
    正股相关的窝轮
    
*   `FUTURE`
    
    期货主连的相关合约
    

**ReferenceType**

    enum ReferenceType
    {
        ReferenceType_Unknow = 0; 
        ReferenceType_Warrant = 1; //正股相关的窝轮
        ReferenceType_Future = 2; //期货主连的相关合约
    }
    

1  
2  
3  
4  
5  
6  

**ReferenceType**

    enum ReferenceType
    {
        ReferenceType_Unknow = 0; 
        ReferenceType_Warrant = 1; //正股相关的窝轮
        ReferenceType_Future = 2; //期货主连的相关合约
    }
    

1  
2  
3  
4  
5  
6  

**ReferenceType**

    enum ReferenceType
    {
        ReferenceType_Unknow = 0; 
        ReferenceType_Warrant = 1; //正股相关的窝轮
        ReferenceType_Future = 2; //期货主连的相关合约
    }
    

1  
2  
3  
4  
5  
6  

**ReferenceType**

    enum ReferenceType
    {
        ReferenceType_Unknow = 0; 
        ReferenceType_Warrant = 1; //正股相关的窝轮
        ReferenceType_Future = 2; //期货主连的相关合约
    }
    

1  
2  
3  
4  
5  
6  

**ReferenceType**

    enum ReferenceType
    {
        ReferenceType_Unknow = 0; 
        ReferenceType_Warrant = 1; //正股相关的窝轮
        ReferenceType_Future = 2; //期货主连的相关合约
    }
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#6907)
 K 线复权类型
---------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **AuType**

*   `NONE`
    
    不复权
    
*   `QFQ`
    
    前复权
    
*   `HFQ`
    
    后复权
    

**RehabType**

    enum RehabType
    {
        RehabType_None = 0; //不复权
        RehabType_Forward = 1; //前复权
        RehabType_Backward = 2; //后复权
    }
    

1  
2  
3  
4  
5  
6  

**RehabType**

    enum RehabType
    {
        RehabType_None = 0; //不复权
        RehabType_Forward = 1; //前复权
        RehabType_Backward = 2; //后复权
    }
    

1  
2  
3  
4  
5  
6  

**RehabType**

    enum RehabType
    {
        RehabType_None = 0; //不复权
        RehabType_Forward = 1; //前复权
        RehabType_Backward = 2; //后复权
    }
    

1  
2  
3  
4  
5  
6  

**RehabType**

    enum RehabType
    {
        RehabType_None = 0; //不复权
        RehabType_Forward = 1; //前复权
        RehabType_Backward = 2; //后复权
    }
    

1  
2  
3  
4  
5  
6  

**RehabType**

    enum RehabType
    {
        RehabType_None = 0; //不复权
        RehabType_Forward = 1; //前复权
        RehabType_Backward = 2; //后复权
    }
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#9969)
 股票状态
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **SecurityStatus**

*   `NONE`
    
    未知
    
*   `NORMAL`
    
    正常状态
    
*   `LISTING`
    
    待上市
    
*   `PURCHASING`
    
    申购中
    
*   `SUBSCRIBING`
    
    认购中
    
*   `BEFORE_DRAK_TRADE_OPENING`
    
    暗盘开盘前
    
*   `DRAK_TRADING`
    
    暗盘交易中
    
*   `DRAK_TRADE_END`
    
    暗盘已收盘
    
*   `TO_BE_OPEN`
    
    待开盘
    
*   `SUSPENDED`
    
    停牌
    
*   `CALLED`
    
    已收回
    
*   `EXPIRED_LAST_TRADING_DATE`
    
    已过最后交易日
    
*   `EXPIRED`
    
    已过期
    
*   `DELISTED`
    
    已退市
    
*   `CHANGE_TO_TEMPORARY_CODE`
    
    公司行动中，交易关闭，转至临时代码交易
    
*   `TEMPORARY_CODE_TRADE_END`
    
    临时买卖结束，交易关闭
    
*   `CHANGED_PLATE_TRADE_END`
    
    已转板，旧代码交易关闭
    
*   `CHANGED_CODE_TRADE_END`
    
    已换代码，旧代码交易关闭
    
*   `RECOVERABLE_CIRCUIT_BREAKER`
    
    可恢复性熔断
    
*   `UN_RECOVERABLE_CIRCUIT_BREAKER`
    
    不可恢复性熔断
    
*   `AFTER_COMBINATION`
    
    盘后撮合
    
*   `AFTER_TRANSATION`
    
    盘后交易
    

**SecurityStatus**

    enum SecurityStatus
    {
        SecurityStatus_Unknown = 0; //未知
        SecurityStatus_Normal = 1; //正常状态
        SecurityStatus_Listing = 2; //待上市
        SecurityStatus_Purchasing = 3; //申购中
        SecurityStatus_Subscribing = 4; //认购中
        SecurityStatus_BeforeDrakTradeOpening = 5; //暗盘开盘前
        SecurityStatus_DrakTrading = 6; //暗盘交易中
        SecurityStatus_DrakTradeEnd = 7; //暗盘已收盘
        SecurityStatus_ToBeOpen = 8; //待开盘
        SecurityStatus_Suspended = 9; //停牌
        SecurityStatus_Called = 10; //已收回
        SecurityStatus_ExpiredLastTradingDate = 11; //已过最后交易日
        SecurityStatus_Expired = 12; //已过期
        SecurityStatus_Delisted = 13; //已退市
        SecurityStatus_ChangeToTemporaryCode = 14; //公司行动中，交易关闭，转至临时代码交易
        SecurityStatus_TemporaryCodeTradeEnd = 15; //临时买卖结束，交易关闭
        SecurityStatus_ChangedPlateTradeEnd = 16; //已转板，旧代码交易关闭
        SecurityStatus_ChangedCodeTradeEnd = 17; //已换代码，旧代码交易关闭
        SecurityStatus_RecoverableCircuitBreaker = 18; //可恢复性熔断
        SecurityStatus_UnRecoverableCircuitBreaker = 19; //不可恢复性熔断
        SecurityStatus_AfterCombination = 20; //盘后撮合
        SecurityStatus_AfterTransation = 21; //盘后交易
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**SecurityStatus**

    enum SecurityStatus
    {
        SecurityStatus_Unknown = 0; //未知
        SecurityStatus_Normal = 1; //正常状态
        SecurityStatus_Listing = 2; //待上市
        SecurityStatus_Purchasing = 3; //申购中
        SecurityStatus_Subscribing = 4; //认购中
        SecurityStatus_BeforeDrakTradeOpening = 5; //暗盘开盘前
        SecurityStatus_DrakTrading = 6; //暗盘交易中
        SecurityStatus_DrakTradeEnd = 7; //暗盘已收盘
        SecurityStatus_ToBeOpen = 8; //待开盘
        SecurityStatus_Suspended = 9; //停牌
        SecurityStatus_Called = 10; //已收回
        SecurityStatus_ExpiredLastTradingDate = 11; //已过最后交易日
        SecurityStatus_Expired = 12; //已过期
        SecurityStatus_Delisted = 13; //已退市
        SecurityStatus_ChangeToTemporaryCode = 14; //公司行动中，交易关闭，转至临时代码交易
        SecurityStatus_TemporaryCodeTradeEnd = 15; //临时买卖结束，交易关闭
        SecurityStatus_ChangedPlateTradeEnd = 16; //已转板，旧代码交易关闭
        SecurityStatus_ChangedCodeTradeEnd = 17; //已换代码，旧代码交易关闭
        SecurityStatus_RecoverableCircuitBreaker = 18; //可恢复性熔断
        SecurityStatus_UnRecoverableCircuitBreaker = 19; //不可恢复性熔断
        SecurityStatus_AfterCombination = 20; //盘后撮合
        SecurityStatus_AfterTransation = 21; //盘后交易
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**SecurityStatus**

    enum SecurityStatus
    {
        SecurityStatus_Unknown = 0; //未知
        SecurityStatus_Normal = 1; //正常状态
        SecurityStatus_Listing = 2; //待上市
        SecurityStatus_Purchasing = 3; //申购中
        SecurityStatus_Subscribing = 4; //认购中
        SecurityStatus_BeforeDrakTradeOpening = 5; //暗盘开盘前
        SecurityStatus_DrakTrading = 6; //暗盘交易中
        SecurityStatus_DrakTradeEnd = 7; //暗盘已收盘
        SecurityStatus_ToBeOpen = 8; //待开盘
        SecurityStatus_Suspended = 9; //停牌
        SecurityStatus_Called = 10; //已收回
        SecurityStatus_ExpiredLastTradingDate = 11; //已过最后交易日
        SecurityStatus_Expired = 12; //已过期
        SecurityStatus_Delisted = 13; //已退市
        SecurityStatus_ChangeToTemporaryCode = 14; //公司行动中，交易关闭，转至临时代码交易
        SecurityStatus_TemporaryCodeTradeEnd = 15; //临时买卖结束，交易关闭
        SecurityStatus_ChangedPlateTradeEnd = 16; //已转板，旧代码交易关闭
        SecurityStatus_ChangedCodeTradeEnd = 17; //已换代码，旧代码交易关闭
        SecurityStatus_RecoverableCircuitBreaker = 18; //可恢复性熔断
        SecurityStatus_UnRecoverableCircuitBreaker = 19; //不可恢复性熔断
        SecurityStatus_AfterCombination = 20; //盘后撮合
        SecurityStatus_AfterTransation = 21; //盘后交易
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**SecurityStatus**

    enum SecurityStatus
    {
        SecurityStatus_Unknown = 0; //未知
        SecurityStatus_Normal = 1; //正常状态
        SecurityStatus_Listing = 2; //待上市
        SecurityStatus_Purchasing = 3; //申购中
        SecurityStatus_Subscribing = 4; //认购中
        SecurityStatus_BeforeDrakTradeOpening = 5; //暗盘开盘前
        SecurityStatus_DrakTrading = 6; //暗盘交易中
        SecurityStatus_DrakTradeEnd = 7; //暗盘已收盘
        SecurityStatus_ToBeOpen = 8; //待开盘
        SecurityStatus_Suspended = 9; //停牌
        SecurityStatus_Called = 10; //已收回
        SecurityStatus_ExpiredLastTradingDate = 11; //已过最后交易日
        SecurityStatus_Expired = 12; //已过期
        SecurityStatus_Delisted = 13; //已退市
        SecurityStatus_ChangeToTemporaryCode = 14; //公司行动中，交易关闭，转至临时代码交易
        SecurityStatus_TemporaryCodeTradeEnd = 15; //临时买卖结束，交易关闭
        SecurityStatus_ChangedPlateTradeEnd = 16; //已转板，旧代码交易关闭
        SecurityStatus_ChangedCodeTradeEnd = 17; //已换代码，旧代码交易关闭
        SecurityStatus_RecoverableCircuitBreaker = 18; //可恢复性熔断
        SecurityStatus_UnRecoverableCircuitBreaker = 19; //不可恢复性熔断
        SecurityStatus_AfterCombination = 20; //盘后撮合
        SecurityStatus_AfterTransation = 21; //盘后交易
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**SecurityStatus**

    enum SecurityStatus
    {
        SecurityStatus_Unknown = 0; //未知
        SecurityStatus_Normal = 1; //正常状态
        SecurityStatus_Listing = 2; //待上市
        SecurityStatus_Purchasing = 3; //申购中
        SecurityStatus_Subscribing = 4; //认购中
        SecurityStatus_BeforeDrakTradeOpening = 5; //暗盘开盘前
        SecurityStatus_DrakTrading = 6; //暗盘交易中
        SecurityStatus_DrakTradeEnd = 7; //暗盘已收盘
        SecurityStatus_ToBeOpen = 8; //待开盘
        SecurityStatus_Suspended = 9; //停牌
        SecurityStatus_Called = 10; //已收回
        SecurityStatus_ExpiredLastTradingDate = 11; //已过最后交易日
        SecurityStatus_Expired = 12; //已过期
        SecurityStatus_Delisted = 13; //已退市
        SecurityStatus_ChangeToTemporaryCode = 14; //公司行动中，交易关闭，转至临时代码交易
        SecurityStatus_TemporaryCodeTradeEnd = 15; //临时买卖结束，交易关闭
        SecurityStatus_ChangedPlateTradeEnd = 16; //已转板，旧代码交易关闭
        SecurityStatus_ChangedCodeTradeEnd = 17; //已换代码，旧代码交易关闭
        SecurityStatus_RecoverableCircuitBreaker = 18; //可恢复性熔断
        SecurityStatus_UnRecoverableCircuitBreaker = 19; //不可恢复性熔断
        SecurityStatus_AfterCombination = 20; //盘后撮合
        SecurityStatus_AfterTransation = 21; //盘后交易
    }
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#3325)
 股票类型
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **SecurityType**

*   `NONE`
    
    未知
    
*   `BOND`
    
    债券
    
*   `BWRT`
    
    一揽子权证
    
*   `STOCK`
    
    正股
    
*   `ETF`
    
    信托,基金
    
*   `WARRANT`
    
    窝轮
    
*   `IDX`
    
    指数
    
*   `PLATE`
    
    板块
    
*   `DRVT`
    
    期权
    
*   `PLATESET`
    
    板块集
    
*   `FUTURE`
    
    期货
    

**SecurityType**

    enum SecurityType
    {
        SecurityType_Unknown = 0; //未知
        SecurityType_Bond = 1; //债券
        SecurityType_Bwrt = 2; //一揽子权证
        SecurityType_Eqty = 3; //正股
        SecurityType_Trust = 4; //信托,基金
        SecurityType_Warrant = 5; //窝轮
        SecurityType_Index = 6; //指数
        SecurityType_Plate = 7; //板块
        SecurityType_Drvt = 8; //期权
        SecurityType_PlateSet = 9; //板块集
        SecurityType_Future = 10; //期货
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

**SecurityType**

    enum SecurityType
    {
        SecurityType_Unknown = 0; //未知
        SecurityType_Bond = 1; //债券
        SecurityType_Bwrt = 2; //一揽子权证
        SecurityType_Eqty = 3; //正股
        SecurityType_Trust = 4; //信托,基金
        SecurityType_Warrant = 5; //窝轮
        SecurityType_Index = 6; //指数
        SecurityType_Plate = 7; //板块
        SecurityType_Drvt = 8; //期权
        SecurityType_PlateSet = 9; //板块集
        SecurityType_Future = 10; //期货
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

**SecurityType**

    enum SecurityType
    {
        SecurityType_Unknown = 0; //未知
        SecurityType_Bond = 1; //债券
        SecurityType_Bwrt = 2; //一揽子权证
        SecurityType_Eqty = 3; //正股
        SecurityType_Trust = 4; //信托,基金
        SecurityType_Warrant = 5; //窝轮
        SecurityType_Index = 6; //指数
        SecurityType_Plate = 7; //板块
        SecurityType_Drvt = 8; //期权
        SecurityType_PlateSet = 9; //板块集
        SecurityType_Future = 10; //期货
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

**SecurityType**

    enum SecurityType
    {
        SecurityType_Unknown = 0; //未知
        SecurityType_Bond = 1; //债券
        SecurityType_Bwrt = 2; //一揽子权证
        SecurityType_Eqty = 3; //正股
        SecurityType_Trust = 4; //信托,基金
        SecurityType_Warrant = 5; //窝轮
        SecurityType_Index = 6; //指数
        SecurityType_Plate = 7; //板块
        SecurityType_Drvt = 8; //期权
        SecurityType_PlateSet = 9; //板块集
        SecurityType_Future = 10; //期货
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

**SecurityType**

    enum SecurityType
    {
        SecurityType_Unknown = 0; //未知
        SecurityType_Bond = 1; //债券
        SecurityType_Bwrt = 2; //一揽子权证
        SecurityType_Eqty = 3; //正股
        SecurityType_Trust = 4; //信托,基金
        SecurityType_Warrant = 5; //窝轮
        SecurityType_Index = 6; //指数
        SecurityType_Plate = 7; //板块
        SecurityType_Drvt = 8; //期权
        SecurityType_PlateSet = 9; //板块集
        SecurityType_Future = 10; //期货
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

[#](./quote_quote.md#433)
 设置到价提醒操作类型
-----------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **SetPriceReminderOp**

*   `NONE`
    
    未知
    
*   `ADD`
    
    新增
    
*   `DEL`
    
    删除
    
*   `ENABLE`
    
    启用
    
*   `DISABLE`
    
    禁用
    
*   `MODIFY`
    
    修改
    
*   `DEL_ALL`
    
    删除全部（删除指定股票下的所有到价提醒）
    

**SetPriceReminderOp**

    enum SetPriceReminderOp
    {
        SetPriceReminderOp_Unknown = 0;
        SetPriceReminderOp_Add = 1; //新增
        SetPriceReminderOp_Del = 2; //删除
        SetPriceReminderOp_Enable = 3; //启用
        SetPriceReminderOp_Disable = 4; //禁用
        SetPriceReminderOp_Modify = 5; //修改
        SetPriceReminderOp_DelAll = 6; //删除全部（删除指定股票下的所有到价提醒）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

**SetPriceReminderOp**

    enum SetPriceReminderOp
    {
        SetPriceReminderOp_Unknown = 0;
        SetPriceReminderOp_Add = 1; //新增
        SetPriceReminderOp_Del = 2; //删除
        SetPriceReminderOp_Enable = 3; //启用
        SetPriceReminderOp_Disable = 4; //禁用
        SetPriceReminderOp_Modify = 5; //修改
        SetPriceReminderOp_DelAll = 6; //删除全部（删除指定股票下的所有到价提醒）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

**SetPriceReminderOp**

    enum SetPriceReminderOp
    {
        SetPriceReminderOp_Unknown = 0;
        SetPriceReminderOp_Add = 1; //新增
        SetPriceReminderOp_Del = 2; //删除
        SetPriceReminderOp_Enable = 3; //启用
        SetPriceReminderOp_Disable = 4; //禁用
        SetPriceReminderOp_Modify = 5; //修改
        SetPriceReminderOp_DelAll = 6; //删除全部（删除指定股票下的所有到价提醒）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

**SetPriceReminderOp**

    enum SetPriceReminderOp
    {
        SetPriceReminderOp_Unknown = 0;
        SetPriceReminderOp_Add = 1; //新增
        SetPriceReminderOp_Del = 2; //删除
        SetPriceReminderOp_Enable = 3; //启用
        SetPriceReminderOp_Disable = 4; //禁用
        SetPriceReminderOp_Modify = 5; //修改
        SetPriceReminderOp_DelAll = 6; //删除全部（删除指定股票下的所有到价提醒）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

**SetPriceReminderOp**

    enum SetPriceReminderOp
    {
        SetPriceReminderOp_Unknown = 0;
        SetPriceReminderOp_Add = 1; //新增
        SetPriceReminderOp_Del = 2; //删除
        SetPriceReminderOp_Enable = 3; //启用
        SetPriceReminderOp_Disable = 4; //禁用
        SetPriceReminderOp_Modify = 5; //修改
        SetPriceReminderOp_DelAll = 6; //删除全部（删除指定股票下的所有到价提醒）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

[#](./quote_quote.md#5471)
 排序方向
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **SortDir**

*   `NONE`
    
    不排序
    
*   `ASCEND`
    
    升序
    
*   `DESCEND`
    
    降序
    

**SortDir**

    enum SortDir
    {
        SortDir_No = 0; // 不排序
        SortDir_Ascend = 1; // 升序
        SortDir_Descend = 2; // 降序
    }
    

1  
2  
3  
4  
5  
6  

**SortDir**

    enum SortDir
    {
        SortDir_No = 0; // 不排序
        SortDir_Ascend = 1; // 升序
        SortDir_Descend = 2; // 降序
    }
    

1  
2  
3  
4  
5  
6  

**SortDir**

    enum SortDir
    {
        SortDir_No = 0; // 不排序
        SortDir_Ascend = 1; // 升序
        SortDir_Descend = 2; // 降序
    }
    

1  
2  
3  
4  
5  
6  

**SortDir**

    enum SortDir
    {
        SortDir_No = 0; // 不排序
        SortDir_Ascend = 1; // 升序
        SortDir_Descend = 2; // 降序
    }
    

1  
2  
3  
4  
5  
6  

**SortDir**

    enum SortDir
    {
        SortDir_No = 0; // 不排序
        SortDir_Ascend = 1; // 升序
        SortDir_Descend = 2; // 降序
    }
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#2930)
 排序字段
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **SortField**

*   `NONE`
    
    未知
    
*   `CODE`
    
    代码
    
*   `CUR_PRICE`
    
    最新价
    
*   `PRICE_CHANGE_VAL`
    
    涨跌额
    
*   `CHANGE_RATE`
    
    涨跌幅 %
    
*   `STATUS`
    
    状态
    
*   `BID_PRICE`
    
    买入价
    
*   `ASK_PRICE`
    
    卖出价
    
*   `BID_VOL`
    
    买量
    
*   `ASK_VOL`
    
    卖量
    
*   `VOLUME`
    
    成交量
    
*   `TURNOVER`
    
    成交额
    
*   `AMPLITUDE`
    
    振幅 %
    
*   `SCORE`
    
    综合评分
    
*   `PREMIUM`
    
    溢价 %
    
*   `EFFECTIVE_LEVERAGE`
    
    有效杠杆
    
*   `DELTA`
    
    对冲值
    
    仅认购认沽支持该字段
    
*   `IMPLIED_VOLATILITY`
    
    引伸波幅
    
    仅认购认沽支持该字段
    
*   `TYPE`
    
    类型
    
*   `STRIKE_PRICE`
    
    行权价
    
*   `BREAK_EVEN_POINT`
    
    打和点
    
*   `MATURITY_TIME`
    
    到期日
    
*   `LIST_TIME`
    
    上市日期
    
*   `LAST_TRADE_TIME`
    
    最后交易日
    
*   `LEVERAGE`
    
    杠杆比率
    
*   `IN_OUT_MONEY`
    
    价内/价外 %
    
*   `RECOVERY_PRICE`
    
    收回价
    
    仅牛熊证支持该字段
    
*   `CHANGE_PRICE`
    
    换股价
    
*   `CHANGE`
    
    换股比率
    
*   `STREET_RATE`
    
    街货比 %
    
*   `STREET_VOL`
    
    街货量
    
*   `WARRANT_NAME`
    
    窝轮名称
    
*   `ISSUER`
    
    发行人
    
*   `LOT_SIZE`
    
    每手
    
*   `ISSUE_SIZE`
    
    发行量
    
*   `UPPER_STRIKE_PRICE`
    
    上限价
    
    仅用于界内证
    
*   `LOWER_STRIKE_PRICE`
    
    下限价
    
    仅用于界内证
    
*   `INLINE_PRICE_STATUS`
    
    界内界外
    
    仅用于界内证
    
*   `PRE_CUR_PRICE`
    
    盘前最新价
    
*   `AFTER_CUR_PRICE`
    
    盘后最新价
    
*   `PRE_PRICE_CHANGE_VAL`
    
    盘前涨跌额
    
*   `AFTER_PRICE_CHANGE_VAL`
    
    盘后涨跌额
    
*   `PRE_CHANGE_RATE`
    
    盘前涨跌幅 %
    
*   `AFTER_CHANGE_RATE`
    
    盘后涨跌幅 %
    
*   `PRE_AMPLITUDE`
    
    盘前振幅 %
    
*   `AFTER_AMPLITUDE`
    
    盘后振幅 %
    
*   `PRE_TURNOVER`
    
    盘前成交额
    
*   `AFTER_TURNOVER`
    
    盘后成交额
    
*   `LAST_SETTLE_PRICE`
    
    昨结
    
*   `POSITION`
    
    持仓量
    
*   `POSITION_CHANGE`
    
    日增仓
    

**SortField**

    enum SortField
    {
        SortField_Unknow = 0;
        SortField_Code = 1; //代码
        SortField_CurPrice = 2; //最新价
        SortField_PriceChangeVal = 3; //涨跌额
        SortField_ChangeRate = 4; //涨跌幅%
        SortField_Status = 5; //状态
        SortField_BidPrice = 6; //买入价
        SortField_AskPrice = 7; //卖出价
        SortField_BidVol = 8; //买量
        SortField_AskVol = 9; //卖量
        SortField_Volume = 10; //成交量
        SortField_Turnover = 11; //成交额
        SortField_Amplitude = 30; //振幅%
    
        //以下排序字段只支持用于 Qot_GetWarrant 协议
        SortField_Score = 12; //综合评分
        SortField_Premium = 13; //溢价%
        SortField_EffectiveLeverage = 14; //有效杠杆
        SortField_Delta = 15; //对冲值,仅认购认沽支持该字段
        SortField_ImpliedVolatility = 16; //引伸波幅,仅认购认沽支持该字段
        SortField_Type = 17; //类型
        SortField_StrikePrice = 18; //行权价
        SortField_BreakEvenPoint = 19; //打和点
        SortField_MaturityTime = 20; //到期日
        SortField_ListTime = 21; //上市日期
        SortField_LastTradeTime = 22; //最后交易日
        SortField_Leverage = 23; //杠杆比率
        SortField_InOutMoney = 24; //价内/价外%
        SortField_RecoveryPrice = 25; //收回价,仅牛熊证支持该字段
        SortField_ChangePrice = 26; // 换股价
        SortField_Change = 27; //换股比率
        SortField_StreetRate = 28; //街货比%
        SortField_StreetVol = 29; //街货量
        SortField_WarrantName = 31; // 窝轮名称
        SortField_Issuer = 32; //发行人
        SortField_LotSize = 33; // 每手
        SortField_IssueSize = 34; //发行量
        SortField_UpperStrikePrice = 45; //上限价，仅用于界内证
        SortField_LowerStrikePrice = 46; //下限价，仅用于界内证
        SortField_InLinePriceStatus = 47; //界内界外，仅用于界内证
    
        //以下排序字段只支持用于 Qot_GetPlateSecurity 协议，并仅支持美股
        SortField_PreCurPrice = 35; //盘前最新价
        SortField_AfterCurPrice = 36; //盘后最新价
        SortField_PrePriceChangeVal = 37; //盘前涨跌额
        SortField_AfterPriceChangeVal = 38; //盘后涨跌额
        SortField_PreChangeRate = 39; //盘前涨跌幅%
        SortField_AfterChangeRate = 40; //盘后涨跌幅%
        SortField_PreAmplitude = 41; //盘前振幅%
        SortField_AfterAmplitude = 42; //盘后振幅%
        SortField_PreTurnover = 43; //盘前成交额
        SortField_AfterTurnover = 44; //盘后成交额
    
        //以下排序字段只支持用于 Qot_GetPlateSecurity 协议，并仅支持期货
        SortField_LastSettlePrice = 48; //昨结
        SortField_Position = 49; //持仓量
        SortField_PositionChange = 50; //日增仓
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**SortField**

    enum SortField
    {
        SortField_Unknow = 0;
        SortField_Code = 1; //代码
        SortField_CurPrice = 2; //最新价
        SortField_PriceChangeVal = 3; //涨跌额
        SortField_ChangeRate = 4; //涨跌幅%
        SortField_Status = 5; //状态
        SortField_BidPrice = 6; //买入价
        SortField_AskPrice = 7; //卖出价
        SortField_BidVol = 8; //买量
        SortField_AskVol = 9; //卖量
        SortField_Volume = 10; //成交量
        SortField_Turnover = 11; //成交额
        SortField_Amplitude = 30; //振幅%
    
        //以下排序字段只支持用于 Qot_GetWarrant 协议
        SortField_Score = 12; //综合评分
        SortField_Premium = 13; //溢价%
        SortField_EffectiveLeverage = 14; //有效杠杆
        SortField_Delta = 15; //对冲值,仅认购认沽支持该字段
        SortField_ImpliedVolatility = 16; //引伸波幅,仅认购认沽支持该字段
        SortField_Type = 17; //类型
        SortField_StrikePrice = 18; //行权价
        SortField_BreakEvenPoint = 19; //打和点
        SortField_MaturityTime = 20; //到期日
        SortField_ListTime = 21; //上市日期
        SortField_LastTradeTime = 22; //最后交易日
        SortField_Leverage = 23; //杠杆比率
        SortField_InOutMoney = 24; //价内/价外%
        SortField_RecoveryPrice = 25; //收回价,仅牛熊证支持该字段
        SortField_ChangePrice = 26; // 换股价
        SortField_Change = 27; //换股比率
        SortField_StreetRate = 28; //街货比%
        SortField_StreetVol = 29; //街货量
        SortField_WarrantName = 31; // 窝轮名称
        SortField_Issuer = 32; //发行人
        SortField_LotSize = 33; // 每手
        SortField_IssueSize = 34; //发行量
        SortField_UpperStrikePrice = 45; //上限价，仅用于界内证
        SortField_LowerStrikePrice = 46; //下限价，仅用于界内证
        SortField_InLinePriceStatus = 47; //界内界外，仅用于界内证
    
        //以下排序字段只支持用于 Qot_GetPlateSecurity 协议，并仅支持美股
        SortField_PreCurPrice = 35; //盘前最新价
        SortField_AfterCurPrice = 36; //盘后最新价
        SortField_PrePriceChangeVal = 37; //盘前涨跌额
        SortField_AfterPriceChangeVal = 38; //盘后涨跌额
        SortField_PreChangeRate = 39; //盘前涨跌幅%
        SortField_AfterChangeRate = 40; //盘后涨跌幅%
        SortField_PreAmplitude = 41; //盘前振幅%
        SortField_AfterAmplitude = 42; //盘后振幅%
        SortField_PreTurnover = 43; //盘前成交额
        SortField_AfterTurnover = 44; //盘后成交额
    
        //以下排序字段只支持用于 Qot_GetPlateSecurity 协议，并仅支持期货
        SortField_LastSettlePrice = 48; //昨结
        SortField_Position = 49; //持仓量
        SortField_PositionChange = 50; //日增仓
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**SortField**

    enum SortField
    {
        SortField_Unknow = 0;
        SortField_Code = 1; //代码
        SortField_CurPrice = 2; //最新价
        SortField_PriceChangeVal = 3; //涨跌额
        SortField_ChangeRate = 4; //涨跌幅%
        SortField_Status = 5; //状态
        SortField_BidPrice = 6; //买入价
        SortField_AskPrice = 7; //卖出价
        SortField_BidVol = 8; //买量
        SortField_AskVol = 9; //卖量
        SortField_Volume = 10; //成交量
        SortField_Turnover = 11; //成交额
        SortField_Amplitude = 30; //振幅%
    
        //以下排序字段只支持用于 Qot_GetWarrant 协议
        SortField_Score = 12; //综合评分
        SortField_Premium = 13; //溢价%
        SortField_EffectiveLeverage = 14; //有效杠杆
        SortField_Delta = 15; //对冲值,仅认购认沽支持该字段
        SortField_ImpliedVolatility = 16; //引伸波幅,仅认购认沽支持该字段
        SortField_Type = 17; //类型
        SortField_StrikePrice = 18; //行权价
        SortField_BreakEvenPoint = 19; //打和点
        SortField_MaturityTime = 20; //到期日
        SortField_ListTime = 21; //上市日期
        SortField_LastTradeTime = 22; //最后交易日
        SortField_Leverage = 23; //杠杆比率
        SortField_InOutMoney = 24; //价内/价外%
        SortField_RecoveryPrice = 25; //收回价,仅牛熊证支持该字段
        SortField_ChangePrice = 26; // 换股价
        SortField_Change = 27; //换股比率
        SortField_StreetRate = 28; //街货比%
        SortField_StreetVol = 29; //街货量
        SortField_WarrantName = 31; // 窝轮名称
        SortField_Issuer = 32; //发行人
        SortField_LotSize = 33; // 每手
        SortField_IssueSize = 34; //发行量
        SortField_UpperStrikePrice = 45; //上限价，仅用于界内证
        SortField_LowerStrikePrice = 46; //下限价，仅用于界内证
        SortField_InLinePriceStatus = 47; //界内界外，仅用于界内证
    
        //以下排序字段只支持用于 Qot_GetPlateSecurity 协议，并仅支持美股
        SortField_PreCurPrice = 35; //盘前最新价
        SortField_AfterCurPrice = 36; //盘后最新价
        SortField_PrePriceChangeVal = 37; //盘前涨跌额
        SortField_AfterPriceChangeVal = 38; //盘后涨跌额
        SortField_PreChangeRate = 39; //盘前涨跌幅%
        SortField_AfterChangeRate = 40; //盘后涨跌幅%
        SortField_PreAmplitude = 41; //盘前振幅%
        SortField_AfterAmplitude = 42; //盘后振幅%
        SortField_PreTurnover = 43; //盘前成交额
        SortField_AfterTurnover = 44; //盘后成交额
    
        //以下排序字段只支持用于 Qot_GetPlateSecurity 协议，并仅支持期货
        SortField_LastSettlePrice = 48; //昨结
        SortField_Position = 49; //持仓量
        SortField_PositionChange = 50; //日增仓
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**SortField**

    enum SortField
    {
        SortField_Unknow = 0;
        SortField_Code = 1; //代码
        SortField_CurPrice = 2; //最新价
        SortField_PriceChangeVal = 3; //涨跌额
        SortField_ChangeRate = 4; //涨跌幅%
        SortField_Status = 5; //状态
        SortField_BidPrice = 6; //买入价
        SortField_AskPrice = 7; //卖出价
        SortField_BidVol = 8; //买量
        SortField_AskVol = 9; //卖量
        SortField_Volume = 10; //成交量
        SortField_Turnover = 11; //成交额
        SortField_Amplitude = 30; //振幅%
    
        //以下排序字段只支持用于 Qot_GetWarrant 协议
        SortField_Score = 12; //综合评分
        SortField_Premium = 13; //溢价%
        SortField_EffectiveLeverage = 14; //有效杠杆
        SortField_Delta = 15; //对冲值,仅认购认沽支持该字段
        SortField_ImpliedVolatility = 16; //引伸波幅,仅认购认沽支持该字段
        SortField_Type = 17; //类型
        SortField_StrikePrice = 18; //行权价
        SortField_BreakEvenPoint = 19; //打和点
        SortField_MaturityTime = 20; //到期日
        SortField_ListTime = 21; //上市日期
        SortField_LastTradeTime = 22; //最后交易日
        SortField_Leverage = 23; //杠杆比率
        SortField_InOutMoney = 24; //价内/价外%
        SortField_RecoveryPrice = 25; //收回价,仅牛熊证支持该字段
        SortField_ChangePrice = 26; // 换股价
        SortField_Change = 27; //换股比率
        SortField_StreetRate = 28; //街货比%
        SortField_StreetVol = 29; //街货量
        SortField_WarrantName = 31; // 窝轮名称
        SortField_Issuer = 32; //发行人
        SortField_LotSize = 33; // 每手
        SortField_IssueSize = 34; //发行量
        SortField_UpperStrikePrice = 45; //上限价，仅用于界内证
        SortField_LowerStrikePrice = 46; //下限价，仅用于界内证
        SortField_InLinePriceStatus = 47; //界内界外，仅用于界内证
    
        //以下排序字段只支持用于 Qot_GetPlateSecurity 协议，并仅支持美股
        SortField_PreCurPrice = 35; //盘前最新价
        SortField_AfterCurPrice = 36; //盘后最新价
        SortField_PrePriceChangeVal = 37; //盘前涨跌额
        SortField_AfterPriceChangeVal = 38; //盘后涨跌额
        SortField_PreChangeRate = 39; //盘前涨跌幅%
        SortField_AfterChangeRate = 40; //盘后涨跌幅%
        SortField_PreAmplitude = 41; //盘前振幅%
        SortField_AfterAmplitude = 42; //盘后振幅%
        SortField_PreTurnover = 43; //盘前成交额
        SortField_AfterTurnover = 44; //盘后成交额
    
        //以下排序字段只支持用于 Qot_GetPlateSecurity 协议，并仅支持期货
        SortField_LastSettlePrice = 48; //昨结
        SortField_Position = 49; //持仓量
        SortField_PositionChange = 50; //日增仓
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**SortField**

    enum SortField
    {
        SortField_Unknow = 0;
        SortField_Code = 1; //代码
        SortField_CurPrice = 2; //最新价
        SortField_PriceChangeVal = 3; //涨跌额
        SortField_ChangeRate = 4; //涨跌幅%
        SortField_Status = 5; //状态
        SortField_BidPrice = 6; //买入价
        SortField_AskPrice = 7; //卖出价
        SortField_BidVol = 8; //买量
        SortField_AskVol = 9; //卖量
        SortField_Volume = 10; //成交量
        SortField_Turnover = 11; //成交额
        SortField_Amplitude = 30; //振幅%
    
        //以下排序字段只支持用于 Qot_GetWarrant 协议
        SortField_Score = 12; //综合评分
        SortField_Premium = 13; //溢价%
        SortField_EffectiveLeverage = 14; //有效杠杆
        SortField_Delta = 15; //对冲值,仅认购认沽支持该字段
        SortField_ImpliedVolatility = 16; //引伸波幅,仅认购认沽支持该字段
        SortField_Type = 17; //类型
        SortField_StrikePrice = 18; //行权价
        SortField_BreakEvenPoint = 19; //打和点
        SortField_MaturityTime = 20; //到期日
        SortField_ListTime = 21; //上市日期
        SortField_LastTradeTime = 22; //最后交易日
        SortField_Leverage = 23; //杠杆比率
        SortField_InOutMoney = 24; //价内/价外%
        SortField_RecoveryPrice = 25; //收回价,仅牛熊证支持该字段
        SortField_ChangePrice = 26; // 换股价
        SortField_Change = 27; //换股比率
        SortField_StreetRate = 28; //街货比%
        SortField_StreetVol = 29; //街货量
        SortField_WarrantName = 31; // 窝轮名称
        SortField_Issuer = 32; //发行人
        SortField_LotSize = 33; // 每手
        SortField_IssueSize = 34; //发行量
        SortField_UpperStrikePrice = 45; //上限价，仅用于界内证
        SortField_LowerStrikePrice = 46; //下限价，仅用于界内证
        SortField_InLinePriceStatus = 47; //界内界外，仅用于界内证
    
        //以下排序字段只支持用于 Qot_GetPlateSecurity 协议，并仅支持美股
        SortField_PreCurPrice = 35; //盘前最新价
        SortField_AfterCurPrice = 36; //盘后最新价
        SortField_PrePriceChangeVal = 37; //盘前涨跌额
        SortField_AfterPriceChangeVal = 38; //盘后涨跌额
        SortField_PreChangeRate = 39; //盘前涨跌幅%
        SortField_AfterChangeRate = 40; //盘后涨跌幅%
        SortField_PreAmplitude = 41; //盘前振幅%
        SortField_AfterAmplitude = 42; //盘后振幅%
        SortField_PreTurnover = 43; //盘前成交额
        SortField_AfterTurnover = 44; //盘后成交额
    
        //以下排序字段只支持用于 Qot_GetPlateSecurity 协议，并仅支持期货
        SortField_LastSettlePrice = 48; //昨结
        SortField_Position = 49; //持仓量
        SortField_PositionChange = 50; //日增仓
    }
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#860)
 简单过滤属性
-------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **StockField**

*   `NONE`
    
    未知
    
*   `STOCK_CODE`
    
    股票代码，不能填区间上下限值。
    
*   `STOCK_NAME`
    
    股票名称，不能填区间上下限值。
    
*   `CUR_PRICE`
    
    最新价
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   例如填写 \[10, 20\] 值区间
    
*   `CUR_PRICE_TO_HIGHEST52_WEEKS_RATIO`
    
    **(CP - WH52) / WH52**  
    **CP**：现价  
    **WH52**：52 周最高  
    对应 PC 端“离 52 周高点百分比”
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[-30, -10\] 值区间
    
*   `CUR_PRICE_TO_LOWEST52_WEEKS_RATIO`
    
    **(CP - WL52) / WL52**  
    **CP**：现价  
    **WL52**：52 周最低  
    对应 PC 端“离 52 周低点百分比”
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[20, 40\] 值区间
    
*   `HIGH_PRICE_TO_HIGHEST52_WEEKS_RATIO`
    
    **(TH - WH52) / WH52**  
    **TH**：今日最高  
    **WH52**：52 周最高  
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[-3, -1\] 值区间
    
*   `LOW_PRICE_TO_LOWEST52_WEEKS_RATIO`
    
    **(TL - WL52) / WL52**  
    **TL**：今日最低  
    **WL52**：52 周最低  
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[10, 70\] 值区间
    
*   `VOLUME_RATIO`
    
    量比
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   例如填写 \[0.5, 30\] 值区间
    
*   `BID_ASK_RATIO`
    
    委比
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[-20, 80.5\] 值区间
    
*   `LOT_PRICE`
    
    每手价格
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   例如填写 \[40, 100\] 值区间
    
*   `MARKET_VAL`
    
    市值
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   例如填写 \[50000000, 3000000000\] 值区间
    
*   `PE_ANNUAL`
    
    市盈率(静态)
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   例如填写 \[-8, 65.3\] 值区间
    
*   `PE_TTM`
    
    市盈率 TTM
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   例如填写 \[-10, 20.5\] 值区间
    
*   `PB_RATE`
    
    市净率
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   例如填写 \[0.5, 20\] 值区间
    
*   `CHANGE_RATE_5MIN`
    
    五分钟价格涨跌幅
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[-5, 6.3\] 值区间
    
*   `CHANGE_RATE_BEGIN_YEAR`
    
    年初至今价格涨跌幅
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[-50.1, 400.7\] 值区间
    
*   `PS_TTM`
    
    市销率 TTM
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[100, 500\] 值区间
    
*   `PCF_TTM`
    
    市现率 TTM
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   该字段为百分比字段，默认不展示 %，如 20 实际对应 20%
    *   例如填写 \[100, 1000\] 值区间
    
*   `TOTAL_SHARE`
    
    总股数
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   单位：股
    *   例如填写 \[1000000000, 1000000000\] 值区间
    
*   `FLOAT_SHARE`
    
    流通股数
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   单位：股
    *   例如填写 \[1000000000, 1000000000\] 值区间
    
*   `FLOAT_MARKET_VAL`
    
    流通市值
    
    *   精确到小数点后 3 位，超出部分会被舍弃
    *   单位：元
    *   例如填写 \[1000000000, 1000000000\] 值区间
    

**StockField**

    enum StockField  
    {
        StockField_Unknown = 0; // 未知
        StockField_StockCode = 1; // 股票代码，不能填区间上下限值。
        StockField_StockName = 2; // 股票名称，不能填区间上下限值。
        StockField_CurPrice = 3; // 最新价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间
        StockField_CurPriceToHighest52WeeksRatio = 4; // (现价 - 52周最高)/52周最高，对应 PC 端离52周高点百分比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-30,-10]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_CurPriceToLowest52WeeksRatio = 5; // (现价 - 52周最低)/52周最低，对应 PC 端离52周低点百分比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[20,40]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_HighPriceToHighest52WeeksRatio = 6; // (今日最高 - 52周最高)/52周最高（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-3,-1]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_LowPriceToLowest52WeeksRatio = 7; // (今日最低 - 52周最低)/52周最低（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,70]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_VolumeRatio = 8; // 量比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,30]值区间
        StockField_BidAskRatio = 9; // 委比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-20,80.5]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_LotPrice = 10; // 每手价格（精确到小数点后 3 位，超出部分会被舍弃）例如填写[40,100]值区间
        StockField_MarketVal = 11; // 市值（精确到小数点后 3 位，超出部分会被舍弃）例如填写[50000000,3000000000]值区间
        StockField_PeAnnual = 12; // 市盈率(静态)（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-8,65.3]值区间
        StockField_PeTTM = 13; // 市盈率 TTM （精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10,20.5]值区间
        StockField_PbRate = 14; // 市净率（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,20]值区间
        StockField_ChangeRate5min = 15; // 五分钟价格涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-5,6.3]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_ChangeRateBeginYear = 16; // 年初至今价格涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-50.1,400.7]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）    
            
        // 基础量价属性
        StockField_PSTTM = 17; // 市销率(TTM)（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100, 500] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%） 
        StockField_PCFTTM = 18; // 市现率(TTM)（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100, 1000] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_TotalShare = 19; // 总股数（精确到小数点后 0 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 (单位：股)
        StockField_FloatShare = 20; // 流通股数（精确到小数点后 0 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 (单位：股)
        StockField_FloatMarketVal = 21; // 流通市值（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**StockField**

    enum StockField  
    {
        StockField_Unknown = 0; // 未知
        StockField_StockCode = 1; // 股票代码，不能填区间上下限值。
        StockField_StockName = 2; // 股票名称，不能填区间上下限值。
        StockField_CurPrice = 3; // 最新价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间
        StockField_CurPriceToHighest52WeeksRatio = 4; // (现价 - 52周最高)/52周最高，对应 PC 端离52周高点百分比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-30,-10]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_CurPriceToLowest52WeeksRatio = 5; // (现价 - 52周最低)/52周最低，对应 PC 端离52周低点百分比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[20,40]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_HighPriceToHighest52WeeksRatio = 6; // (今日最高 - 52周最高)/52周最高（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-3,-1]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_LowPriceToLowest52WeeksRatio = 7; // (今日最低 - 52周最低)/52周最低（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,70]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_VolumeRatio = 8; // 量比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,30]值区间
        StockField_BidAskRatio = 9; // 委比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-20,80.5]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_LotPrice = 10; // 每手价格（精确到小数点后 3 位，超出部分会被舍弃）例如填写[40,100]值区间
        StockField_MarketVal = 11; // 市值（精确到小数点后 3 位，超出部分会被舍弃）例如填写[50000000,3000000000]值区间
        StockField_PeAnnual = 12; // 市盈率(静态)（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-8,65.3]值区间
        StockField_PeTTM = 13; // 市盈率 TTM（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10,20.5]值区间
        StockField_PbRate = 14; // 市净率（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,20]值区间
        StockField_ChangeRate5min = 15; // 五分钟价格涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-5,6.3]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_ChangeRateBeginYear = 16; // 年初至今价格涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-50.1,400.7]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）    
            
        // 基础量价属性
        StockField_PSTTM = 17; // 市销率(TTM)（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100, 500] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%） 
        StockField_PCFTTM = 18; // 市现率(TTM)（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100, 1000] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_TotalShare = 19; // 总股数（精确到小数点后 0 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 (单位：股)
        StockField_FloatShare = 20; // 流通股数（精确到小数点后 0 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 (单位：股)
        StockField_FloatMarketVal = 21; // 流通市值（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**StockField**

    enum StockField  
    {
        StockField_Unknown = 0; // 未知
        StockField_StockCode = 1; // 股票代码，不能填区间上下限值。
        StockField_StockName = 2; // 股票名称，不能填区间上下限值。
        StockField_CurPrice = 3; // 最新价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间
        StockField_CurPriceToHighest52WeeksRatio = 4; // (现价 - 52周最高)/52周最高，对应 PC 端离52周高点百分比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-30,-10]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_CurPriceToLowest52WeeksRatio = 5; // (现价 - 52周最低)/52周最低，对应 PC 端离52周低点百分比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[20,40]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_HighPriceToHighest52WeeksRatio = 6; // (今日最高 - 52周最高)/52周最高（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-3,-1]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_LowPriceToLowest52WeeksRatio = 7; // (今日最低 - 52周最低)/52周最低（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,70]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_VolumeRatio = 8; // 量比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,30]值区间
        StockField_BidAskRatio = 9; // 委比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-20,80.5]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_LotPrice = 10; // 每手价格（精确到小数点后 3 位，超出部分会被舍弃）例如填写[40,100]值区间
        StockField_MarketVal = 11; // 市值（精确到小数点后 3 位，超出部分会被舍弃）例如填写[50000000,3000000000]值区间
        StockField_PeAnnual = 12; // 市盈率(静态)（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-8,65.3]值区间
        StockField_PeTTM = 13; // 市盈率 TTM（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10,20.5]值区间
        StockField_PbRate = 14; // 市净率（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,20]值区间
        StockField_ChangeRate5min = 15; // 五分钟价格涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-5,6.3]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_ChangeRateBeginYear = 16; // 年初至今价格涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-50.1,400.7]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）    
            
        // 基础量价属性
        StockField_PSTTM = 17; // 市销率(TTM)（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100, 500] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%） 
        StockField_PCFTTM = 18; // 市现率(TTM)（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100, 1000] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_TotalShare = 19; // 总股数（精确到小数点后 0 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 (单位：股)
        StockField_FloatShare = 20; // 流通股数（精确到小数点后 0 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 (单位：股)
        StockField_FloatMarketVal = 21; // 流通市值（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**StockField**

    enum StockField  
    {
        StockField_Unknown = 0; // 未知
        StockField_StockCode = 1; // 股票代码，不能填区间上下限值。
        StockField_StockName = 2; // 股票名称，不能填区间上下限值。
        StockField_CurPrice = 3; // 最新价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间
        StockField_CurPriceToHighest52WeeksRatio = 4; // (现价 - 52周最高)/52周最高，对应 PC 端离52周高点百分比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-30,-10]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_CurPriceToLowest52WeeksRatio = 5; // (现价 - 52周最低)/52周最低，对应 PC 端离52周低点百分比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[20,40]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_HighPriceToHighest52WeeksRatio = 6; // (今日最高 - 52周最高)/52周最高（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-3,-1]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_LowPriceToLowest52WeeksRatio = 7; // (今日最低 - 52周最低)/52周最低（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,70]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_VolumeRatio = 8; // 量比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,30]值区间
        StockField_BidAskRatio = 9; // 委比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-20,80.5]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_LotPrice = 10; // 每手价格（精确到小数点后 3 位，超出部分会被舍弃）例如填写[40,100]值区间
        StockField_MarketVal = 11; // 市值（精确到小数点后 3 位，超出部分会被舍弃）例如填写[50000000,3000000000]值区间
        StockField_PeAnnual = 12; // 市盈率(静态)（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-8,65.3]值区间
        StockField_PeTTM = 13; // 市盈率 TTM（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10,20.5]值区间
        StockField_PbRate = 14; // 市净率（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,20]值区间
        StockField_ChangeRate5min = 15; // 五分钟价格涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-5,6.3]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_ChangeRateBeginYear = 16; // 年初至今价格涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-50.1,400.7]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）    
            
        // 基础量价属性
        StockField_PSTTM = 17; // 市销率(TTM)（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100, 500] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%） 
        StockField_PCFTTM = 18; // 市现率(TTM)（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100, 1000] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_TotalShare = 19; // 总股数（精确到小数点后 0 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 (单位：股)
        StockField_FloatShare = 20; // 流通股数（精确到小数点后 0 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 (单位：股)
        StockField_FloatMarketVal = 21; // 流通市值（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**StockField**

    enum StockField  
    {
        StockField_Unknown = 0; // 未知
        StockField_StockCode = 1; // 股票代码，不能填区间上下限值。
        StockField_StockName = 2; // 股票名称，不能填区间上下限值。
        StockField_CurPrice = 3; // 最新价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间
        StockField_CurPriceToHighest52WeeksRatio = 4; // (现价 - 52周最高)/52周最高，对应 PC 端离52周高点百分比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-30,-10]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_CurPriceToLowest52WeeksRatio = 5; // (现价 - 52周最低)/52周最低，对应 PC 端离52周低点百分比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[20,40]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_HighPriceToHighest52WeeksRatio = 6; // (今日最高 - 52周最高)/52周最高（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-3,-1]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_LowPriceToLowest52WeeksRatio = 7; // (今日最低 - 52周最低)/52周最低（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,70]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_VolumeRatio = 8; // 量比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,30]值区间
        StockField_BidAskRatio = 9; // 委比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-20,80.5]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_LotPrice = 10; // 每手价格（精确到小数点后 3 位，超出部分会被舍弃）例如填写[40,100]值区间
        StockField_MarketVal = 11; // 市值（精确到小数点后 3 位，超出部分会被舍弃）例如填写[50000000,3000000000]值区间
        StockField_PeAnnual = 12; // 市盈率(静态)（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-8,65.3]值区间
        StockField_PeTTM = 13; // 市盈率 TTM（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10,20.5]值区间
        StockField_PbRate = 14; // 市净率（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,20]值区间
        StockField_ChangeRate5min = 15; // 五分钟价格涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-5,6.3]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_ChangeRateBeginYear = 16; // 年初至今价格涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-50.1,400.7]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）    
            
        // 基础量价属性
        StockField_PSTTM = 17; // 市销率(TTM)（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100, 500] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%） 
        StockField_PCFTTM = 18; // 市现率(TTM)（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100, 1000] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        StockField_TotalShare = 19; // 总股数（精确到小数点后 0 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 (单位：股)
        StockField_FloatShare = 20; // 流通股数（精确到小数点后 0 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 (单位：股)
        StockField_FloatMarketVal = 21; // 流通市值（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）
    }
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#5878)
 订阅类型
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **SubType**

*   `NONE`
    
    未知
    
*   `QUOTE`
    
    基础报价
    
*   `ORDER_BOOK`
    
    摆盘
    
*   `TICKER`
    
    逐笔
    
*   `RT_DATA`
    
    分时
    
*   `K_DAY`
    
    日 K
    
*   `K_5M`
    
    5 分 K
    
*   `K_15M`
    
    15 分 K
    
*   `K_30M`
    
    30 分 K
    
*   `K_60M`
    
    60 分 K
    
*   `K_1M`
    
    1 分 K
    
*   `K_WEEK`
    
    周 K
    
*   `K_MON`
    
    月 K
    
*   `BROKER`
    
    经纪队列
    
*   `K_QURATER`
    
    季 K
    
*   `K_YEAR`
    
    年 K
    
*   `K_3M`
    
    3 分 K
    

**SubType**

    enum SubType
    {
        SubType_None = 0;
        SubType_Basic = 1; //基础报价
        SubType_OrderBook = 2; //摆盘
        SubType_Ticker = 4; //逐笔
        SubType_RT = 5; //分时
        SubType_KL_Day = 6; //日 K
        SubType_KL_5Min = 7; //5分 K
        SubType_KL_15Min = 8; //15分 K
        SubType_KL_30Min = 9; //30分 K
        SubType_KL_60Min = 10; //60分 K
        SubType_KL_1Min = 11; //1分 K
        SubType_KL_Week = 12; //周 K
        SubType_KL_Month = 13; //月 K
        SubType_Broker = 14; //经纪队列
        SubType_KL_Qurater = 15; //季 K
        SubType_KL_Year = 16; //年 K
        SubType_KL_3Min = 17; //3分 K
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**SubType**

    enum SubType
    {
        SubType_None = 0;
        SubType_Basic = 1; //基础报价
        SubType_OrderBook = 2; //摆盘
        SubType_Ticker = 4; //逐笔
        SubType_RT = 5; //分时
        SubType_KL_Day = 6; //日 K
        SubType_KL_5Min = 7; //5分 K
        SubType_KL_15Min = 8; //15分 K
        SubType_KL_30Min = 9; //30分 K
        SubType_KL_60Min = 10; //60分 K
        SubType_KL_1Min = 11; //1分 K
        SubType_KL_Week = 12; //周 K
        SubType_KL_Month = 13; //月 K
        SubType_Broker = 14; //经纪队列
        SubType_KL_Qurater = 15; //季 K
        SubType_KL_Year = 16; //年 K
        SubType_KL_3Min = 17; //3分 K
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**SubType**

    enum SubType
    {
        SubType_None = 0;
        SubType_Basic = 1; //基础报价
        SubType_OrderBook = 2; //摆盘
        SubType_Ticker = 4; //逐笔
        SubType_RT = 5; //分时
        SubType_KL_Day = 6; //日 K
        SubType_KL_5Min = 7; //5分 K
        SubType_KL_15Min = 8; //15分 K
        SubType_KL_30Min = 9; //30分 K
        SubType_KL_60Min = 10; //60分 K
        SubType_KL_1Min = 11; //1分 K
        SubType_KL_Week = 12; //周 K
        SubType_KL_Month = 13; //月 K
        SubType_Broker = 14; //经纪队列
        SubType_KL_Qurater = 15; //季 K
        SubType_KL_Year = 16; //年 K
        SubType_KL_3Min = 17; //3分 K
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**SubType**

    enum SubType
    {
        SubType_None = 0;
        SubType_Basic = 1; //基础报价
        SubType_OrderBook = 2; //摆盘
        SubType_Ticker = 4; //逐笔
        SubType_RT = 5; //分时
        SubType_KL_Day = 6; //日 K
        SubType_KL_5Min = 7; //5分 K
        SubType_KL_15Min = 8; //15分 K
        SubType_KL_30Min = 9; //30分 K
        SubType_KL_60Min = 10; //60分 K
        SubType_KL_1Min = 11; //1分 K
        SubType_KL_Week = 12; //周 K
        SubType_KL_Month = 13; //月 K
        SubType_Broker = 14; //经纪队列
        SubType_KL_Qurater = 15; //季 K
        SubType_KL_Year = 16; //年 K
        SubType_KL_3Min = 17; //3分 K
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**SubType**

    enum SubType
    {
        SubType_None = 0;
        SubType_Basic = 1; //基础报价
        SubType_OrderBook = 2; //摆盘
        SubType_Ticker = 4; //逐笔
        SubType_RT = 5; //分时
        SubType_KL_Day = 6; //日 K
        SubType_KL_5Min = 7; //5分 K
        SubType_KL_15Min = 8; //15分 K
        SubType_KL_30Min = 9; //30分 K
        SubType_KL_60Min = 10; //60分 K
        SubType_KL_1Min = 11; //1分 K
        SubType_KL_Week = 12; //周 K
        SubType_KL_Month = 13; //月 K
        SubType_Broker = 14; //经纪队列
        SubType_KL_Qurater = 15; //季 K
        SubType_KL_Year = 16; //年 K
        SubType_KL_3Min = 17; //3分 K
    }
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#8723)
 逐笔成交方向
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **TickerDirect**

*   `NONE`
    
    未知
    
*   `BUY`
    
    外盘
    
    外盘（主动买入），即以卖一价或更高的价格成交股票
    
*   `SELL`
    
    内盘
    
    内盘（主动卖出），即以买一价或更低的价格成交股票
    
*   `NEUTRAL`
    
    中性盘
    
    中性盘，即以买一价与卖一价之间的价格撮合成交
    

**TickerDirection**

    enum TickerDirection
    {
        TickerDirection_Unknown = 0; //未知
        TickerDirection_Bid = 1; //外盘（主动买入），即以卖一价或更高的价格成交股票
        TickerDirection_Ask = 2; //内盘（主动卖出），即以买一价或更低的价格成交股票
        TickerDirection_Neutral = 3; //中性盘，即以买一价与卖一价之间的价格撮合成交
    }
    

1  
2  
3  
4  
5  
6  
7  

**TickerDirection**

    enum TickerDirection
    {
        TickerDirection_Unknown = 0; //未知
        TickerDirection_Bid = 1; //外盘（主动买入），即以卖一价或更高的价格成交股票
        TickerDirection_Ask = 2; //内盘（主动卖出），即以买一价或更低的价格成交股票
        TickerDirection_Neutral = 3; //中性盘，即以买一价与卖一价之间的价格撮合成交
    }
    

1  
2  
3  
4  
5  
6  
7  

**TickerDirection**

    enum TickerDirection
    {
        TickerDirection_Unknown = 0; //未知
        TickerDirection_Bid = 1; //外盘（主动买入），即以卖一价或更高的价格成交股票
        TickerDirection_Ask = 2; //内盘（主动卖出），即以买一价或更低的价格成交股票
        TickerDirection_Neutral = 3; //中性盘，即以买一价与卖一价之间的价格撮合成交
    }
    

1  
2  
3  
4  
5  
6  
7  

**TickerDirection**

    enum TickerDirection
    {
        TickerDirection_Unknown = 0; //未知
        TickerDirection_Bid = 1; //外盘（主动买入），即以卖一价或更高的价格成交股票
        TickerDirection_Ask = 2; //内盘（主动卖出），即以买一价或更低的价格成交股票
        TickerDirection_Neutral = 3; //中性盘，即以买一价与卖一价之间的价格撮合成交
    }
    

1  
2  
3  
4  
5  
6  
7  

**TickerDirection**

    enum TickerDirection
    {
        TickerDirection_Unknown = 0; //未知
        TickerDirection_Bid = 1; //外盘（主动买入），即以卖一价或更高的价格成交股票
        TickerDirection_Ask = 2; //内盘（主动卖出），即以买一价或更低的价格成交股票
        TickerDirection_Neutral = 3; //中性盘，即以买一价与卖一价之间的价格撮合成交
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./quote_quote.md#2358)
 逐笔成交类型
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **TickerType**

*   `UNKNOWN`
    
    未知
    
*   `AUTO_MATCH`
    
    自动对盘
    
*   `LATE`
    
    开市前成交盘
    
*   `NON_AUTO_MATCH`
    
    非自动对盘
    
*   `INTER_AUTO_MATCH`
    
    同一证券商自动对盘
    
*   `INTER_NON_AUTO_MATCH`
    
    同一证券商非自动对盘
    
*   `ODD_LOT`
    
    碎股交易
    
*   `AUCTION`
    
    竞价交易
    
*   `BULK`
    
    批量交易
    
*   `CRASH`
    
    现金交易
    
*   `CROSS_MARKET`
    
    跨市场交易
    
*   `BULK_SOLD`
    
    批量卖出
    
*   `FREE_ON_BOARD`
    
    离价交易
    
*   `RULE127_OR155`
    
    第 127 条交易（纽交所规则）或第 155 条交易
    
*   `DELAY`
    
    延迟交易
    
*   `MARKET_CENTER_CLOSE_PRICE`
    
    中央收市价
    
*   `NEXT_DAY`
    
    隔日交易
    
*   `MARKET_CENTER_OPENING`
    
    中央开盘价交易
    
*   `PRIOR_REFERENCE_PRICE`
    
    前参考价
    
*   `MARKET_CENTER_OPEN_PRICE`
    
    中央开盘价
    
*   `SELLER`
    
    卖方
    
*   `T`
    
    T 类交易（盘前和盘后交易）
    
*   `EXTENDED_TRADING_HOURS`
    
    延长交易时段
    
*   `CONTINGENT`
    
    合单交易
    
*   `AVERAGE_PRICE`
    
    平均价成交
    
*   `OTC_SOLD`
    
    场外售出
    
*   `ODD_LOT_CROSS_MARKET`
    
    碎股跨市场交易
    
*   `DERIVATIVELY_PRICED`
    
    衍生工具定价
    
*   `REOPENINGP_RICED`
    
    再开盘定价
    
*   `CLOSING_PRICED`
    
    收盘定价
    
*   `COMPREHENSIVE_DELAY_PRICE`
    
    综合延迟价格
    
*   `OVERSEAS`
    
    交易的一方不是香港交易所的成员，属于场外交易
    

**TickerType**

    enum TickerType
    {
        TickerType_Unknown = 0; //未知
        TickerType_Automatch = 1; //自动对盘
        TickerType_Late = 2; //开市前成交盘
        TickerType_NoneAutomatch = 3; //非自动对盘
        TickerType_InterAutomatch = 4; //同一证券商自动对盘
        TickerType_InterNoneAutomatch = 5; //同一证券商非自动对盘
        TickerType_OddLot = 6; //碎股交易
        TickerType_Auction = 7; //竞价交易    
        TickerType_Bulk = 8; //批量交易
        TickerType_Crash = 9; //现金交易
        TickerType_CrossMarket = 10; //跨市场交易
        TickerType_BulkSold = 11; //批量卖出
        TickerType_FreeOnBoard = 12; //离价交易
        TickerType_Rule127Or155 = 13; //第127条交易（纽交所规则）或第155条交易
        TickerType_Delay = 14; //延迟交易
        TickerType_MarketCenterClosePrice = 15; //中央收市价
        TickerType_NextDay = 16; //隔日交易
        TickerType_MarketCenterOpening = 17; //中央开盘价交易
        TickerType_PriorReferencePrice = 18; //前参考价
        TickerType_MarketCenterOpenPrice = 19; //中央开盘价
        TickerType_Seller = 20; //卖方
        TickerType_T = 21; //T 类交易(盘前和盘后交易)
        TickerType_ExtendedTradingHours = 22; //延长交易时段
        TickerType_Contingent = 23; //合单交易
        TickerType_AvgPrice = 24; //平均价成交
        TickerType_OTCSold = 25; //场外售出
        TickerType_OddLotCrossMarket = 26; //碎股跨市场交易
        TickerType_DerivativelyPriced = 27; //衍生工具定价
        TickerType_ReOpeningPriced = 28; //再开盘定价
        TickerType_ClosingPriced = 29; //收盘定价
        TickerType_ComprehensiveDelayPrice = 30; //综合延迟价格
        TickerType_Overseas = 31; //交易的一方不是香港交易所的成员，属于场外交易
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**TickerType**

    enum TickerType
    {
        TickerType_Unknown = 0; //未知
        TickerType_Automatch = 1; //自动对盘
        TickerType_Late = 2; //开市前成交盘
        TickerType_NoneAutomatch = 3; //非自动对盘
        TickerType_InterAutomatch = 4; //同一证券商自动对盘
        TickerType_InterNoneAutomatch = 5; //同一证券商非自动对盘
        TickerType_OddLot = 6; //碎股交易
        TickerType_Auction = 7; //竞价交易    
        TickerType_Bulk = 8; //批量交易
        TickerType_Crash = 9; //现金交易
        TickerType_CrossMarket = 10; //跨市场交易
        TickerType_BulkSold = 11; //批量卖出
        TickerType_FreeOnBoard = 12; //离价交易
        TickerType_Rule127Or155 = 13; //第127条交易（纽交所规则）或第155条交易
        TickerType_Delay = 14; //延迟交易
        TickerType_MarketCenterClosePrice = 15; //中央收市价
        TickerType_NextDay = 16; //隔日交易
        TickerType_MarketCenterOpening = 17; //中央开盘价交易
        TickerType_PriorReferencePrice = 18; //前参考价
        TickerType_MarketCenterOpenPrice = 19; //中央开盘价
        TickerType_Seller = 20; //卖方
        TickerType_T = 21; //T 类交易(盘前和盘后交易)
        TickerType_ExtendedTradingHours = 22; //延长交易时段
        TickerType_Contingent = 23; //合单交易
        TickerType_AvgPrice = 24; //平均价成交
        TickerType_OTCSold = 25; //场外售出
        TickerType_OddLotCrossMarket = 26; //碎股跨市场交易
        TickerType_DerivativelyPriced = 27; //衍生工具定价
        TickerType_ReOpeningPriced = 28; //再开盘定价
        TickerType_ClosingPriced = 29; //收盘定价
        TickerType_ComprehensiveDelayPrice = 30; //综合延迟价格
        TickerType_Overseas = 31; //交易的一方不是香港交易所的成员，属于场外交易
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**TickerType**

    enum TickerType
    {
        TickerType_Unknown = 0; //未知
        TickerType_Automatch = 1; //自动对盘
        TickerType_Late = 2; //开市前成交盘
        TickerType_NoneAutomatch = 3; //非自动对盘
        TickerType_InterAutomatch = 4; //同一证券商自动对盘
        TickerType_InterNoneAutomatch = 5; //同一证券商非自动对盘
        TickerType_OddLot = 6; //碎股交易
        TickerType_Auction = 7; //竞价交易    
        TickerType_Bulk = 8; //批量交易
        TickerType_Crash = 9; //现金交易
        TickerType_CrossMarket = 10; //跨市场交易
        TickerType_BulkSold = 11; //批量卖出
        TickerType_FreeOnBoard = 12; //离价交易
        TickerType_Rule127Or155 = 13; //第127条交易（纽交所规则）或第155条交易
        TickerType_Delay = 14; //延迟交易
        TickerType_MarketCenterClosePrice = 15; //中央收市价
        TickerType_NextDay = 16; //隔日交易
        TickerType_MarketCenterOpening = 17; //中央开盘价交易
        TickerType_PriorReferencePrice = 18; //前参考价
        TickerType_MarketCenterOpenPrice = 19; //中央开盘价
        TickerType_Seller = 20; //卖方
        TickerType_T = 21; //T 类交易(盘前和盘后交易)
        TickerType_ExtendedTradingHours = 22; //延长交易时段
        TickerType_Contingent = 23; //合单交易
        TickerType_AvgPrice = 24; //平均价成交
        TickerType_OTCSold = 25; //场外售出
        TickerType_OddLotCrossMarket = 26; //碎股跨市场交易
        TickerType_DerivativelyPriced = 27; //衍生工具定价
        TickerType_ReOpeningPriced = 28; //再开盘定价
        TickerType_ClosingPriced = 29; //收盘定价
        TickerType_ComprehensiveDelayPrice = 30; //综合延迟价格
        TickerType_Overseas = 31; //交易的一方不是香港交易所的成员，属于场外交易
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**TickerType**

    enum TickerType
    {
        TickerType_Unknown = 0; //未知
        TickerType_Automatch = 1; //自动对盘
        TickerType_Late = 2; //开市前成交盘
        TickerType_NoneAutomatch = 3; //非自动对盘
        TickerType_InterAutomatch = 4; //同一证券商自动对盘
        TickerType_InterNoneAutomatch = 5; //同一证券商非自动对盘
        TickerType_OddLot = 6; //碎股交易
        TickerType_Auction = 7; //竞价交易    
        TickerType_Bulk = 8; //批量交易
        TickerType_Crash = 9; //现金交易
        TickerType_CrossMarket = 10; //跨市场交易
        TickerType_BulkSold = 11; //批量卖出
        TickerType_FreeOnBoard = 12; //离价交易
        TickerType_Rule127Or155 = 13; //第127条交易（纽交所规则）或第155条交易
        TickerType_Delay = 14; //延迟交易
        TickerType_MarketCenterClosePrice = 15; //中央收市价
        TickerType_NextDay = 16; //隔日交易
        TickerType_MarketCenterOpening = 17; //中央开盘价交易
        TickerType_PriorReferencePrice = 18; //前参考价
        TickerType_MarketCenterOpenPrice = 19; //中央开盘价
        TickerType_Seller = 20; //卖方
        TickerType_T = 21; //T 类交易(盘前和盘后交易)
        TickerType_ExtendedTradingHours = 22; //延长交易时段
        TickerType_Contingent = 23; //合单交易
        TickerType_AvgPrice = 24; //平均价成交
        TickerType_OTCSold = 25; //场外售出
        TickerType_OddLotCrossMarket = 26; //碎股跨市场交易
        TickerType_DerivativelyPriced = 27; //衍生工具定价
        TickerType_ReOpeningPriced = 28; //再开盘定价
        TickerType_ClosingPriced = 29; //收盘定价
        TickerType_ComprehensiveDelayPrice = 30; //综合延迟价格
        TickerType_Overseas = 31; //交易的一方不是香港交易所的成员，属于场外交易
    }
    

1  
2  
3  
4  
5  
6  
7  
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

**TickerType**

    enum TickerType
    {
        TickerType_Unknown = 0; //未知
        TickerType_Automatch = 1; //自动对盘
        TickerType_Late = 2; //开市前成交盘
        TickerType_NoneAutomatch = 3; //非自动对盘
        TickerType_InterAutomatch = 4; //同一证券商自动对盘
        TickerType_InterNoneAutomatch = 5; //同一证券商非自动对盘
        TickerType_OddLot = 6; //碎股交易
        TickerType_Auction = 7; //竞价交易    
        TickerType_Bulk = 8; //批量交易
        TickerType_Crash = 9; //现金交易
        TickerType_CrossMarket = 10; //跨市场交易
        TickerType_BulkSold = 11; //批量卖出
        TickerType_FreeOnBoard = 12; //离价交易
        TickerType_Rule127Or155 = 13; //第127条交易（纽交所规则）或第155条交易
        TickerType_Delay = 14; //延迟交易
        TickerType_MarketCenterClosePrice = 15; //中央收市价
        TickerType_NextDay = 16; //隔日交易
        TickerType_MarketCenterOpening = 17; //中央开盘价交易
        TickerType_PriorReferencePrice = 18; //前参考价
        TickerType_MarketCenterOpenPrice = 19; //中央开盘价
        TickerType_Seller = 20; //卖方
        TickerType_T = 21; //T 类交易(盘前和盘后交易)
        TickerType_ExtendedTradingHours = 22; //延长交易时段
        TickerType_Contingent = 23; //合单交易
        TickerType_AvgPrice = 24; //平均价成交
        TickerType_OTCSold = 25; //场外售出
        TickerType_OddLotCrossMarket = 26; //碎股跨市场交易
        TickerType_DerivativelyPriced = 27; //衍生工具定价
        TickerType_ReOpeningPriced = 28; //再开盘定价
        TickerType_ClosingPriced = 29; //收盘定价
        TickerType_ComprehensiveDelayPrice = 30; //综合延迟价格
        TickerType_Overseas = 31; //交易的一方不是香港交易所的成员，属于场外交易
    }
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#940)
 交易日查询市场
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **TradeDateMarket**

*   `NONE`
    
    未知
    
*   `HK`
    
    香港市场
    
    *   含股票、ETFs、窝轮、牛熊、期权、非假期交易期货
    *   不含假期交易期货
    
*   `US`
    
    美国市场
    
    *   含股票、ETFs、期权
    *   不含期货
    
      
    
*   `CN`
    
    A 股市场
    
*   `NT`
    
    深（沪）股通
    
*   `ST`
    
    港股通（深、沪）
    
*   `JP_FUTURE`
    
    日本期货
    
*   `SG_FUTURE`
    
    新加坡期货
    

**TradeDateMarket**

    enum TradeDateMarket
    {
        TradeDateMarket_Unknown = 0; //未知
        TradeDateMarket_HK = 1; //香港市场（含股票、ETFs、窝轮、牛熊、期权、非假期交易期货；不含假期交易期货）
        TradeDateMarket_US = 2; //美国市场（含股票、ETFs、期权；不含期货）
        TradeDateMarket_CN = 3; //A 股市场
        TradeDateMarket_NT = 4; //深（沪）股通
        TradeDateMarket_ST = 5; //港股通（深、沪）
        TradeDateMarket_JP_Future = 6; //日本期货
        TradeDateMarket_SG_Future = 7; //新加坡期货
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

**TradeDateMarket**

    enum TradeDateMarket
    {
        TradeDateMarket_Unknown = 0; //未知
        TradeDateMarket_HK = 1; //香港市场（含股票、ETFs、窝轮、牛熊、期权、非假期交易期货；不含假期交易期货）
        TradeDateMarket_US = 2; //美国市场（含股票、ETFs、期权；不含期货）
        TradeDateMarket_CN = 3; //A 股市场
        TradeDateMarket_NT = 4; //深（沪）股通
        TradeDateMarket_ST = 5; //港股通（深、沪）
        TradeDateMarket_JP_Future = 6; //日本期货
        TradeDateMarket_SG_Future = 7; //新加坡期货
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

**TradeDateMarket**

    enum TradeDateMarket
    {
        TradeDateMarket_Unknown = 0; //未知
        TradeDateMarket_HK = 1; //香港市场（含股票、ETFs、窝轮、牛熊、期权、非假期交易期货；不含假期交易期货）
        TradeDateMarket_US = 2; //美国市场（含股票、ETFs、期权；不含期货）
        TradeDateMarket_CN = 3; //A 股市场
        TradeDateMarket_NT = 4; //深（沪）股通
        TradeDateMarket_ST = 5; //港股通（深、沪）
        TradeDateMarket_JP_Future = 6; //日本期货
        TradeDateMarket_SG_Future = 7; //新加坡期货
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

**TradeDateMarket**

    enum TradeDateMarket
    {
        TradeDateMarket_Unknown = 0; //未知
        TradeDateMarket_HK = 1; //香港市场（含股票、ETFs、窝轮、牛熊、期权、非假期交易期货；不含假期交易期货）
        TradeDateMarket_US = 2; //美国市场（含股票、ETFs、期权；不含期货）
        TradeDateMarket_CN = 3; //A 股市场
        TradeDateMarket_NT = 4; //深（沪）股通
        TradeDateMarket_ST = 5; //港股通（深、沪）
        TradeDateMarket_JP_Future = 6; //日本期货
        TradeDateMarket_SG_Future = 7; //新加坡期货
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

**TradeDateMarket**

    enum TradeDateMarket
    {
        TradeDateMarket_Unknown = 0; //未知
        TradeDateMarket_HK = 1; //香港市场（含股票、ETFs、窝轮、牛熊、期权、非假期交易期货；不含假期交易期货）
        TradeDateMarket_US = 2; //美国市场（含股票、ETFs、期权；不含期货）
        TradeDateMarket_CN = 3; //A 股市场
        TradeDateMarket_NT = 4; //深（沪）股通
        TradeDateMarket_ST = 5; //港股通（深、沪）
        TradeDateMarket_JP_Future = 6; //日本期货
        TradeDateMarket_SG_Future = 7; //新加坡期货
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

[#](./quote_quote.md#6676)
 交易日类型
-------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **TradeDateType**

*   `WHOLE`
    
    全天交易
    
*   `MORNING`
    
    上午交易，下午休市
    
*   `AFTERNOON`
    
    下午交易，上午休市
    

**TradeDateType**

    enum TradeDateType
    {
        TradeDateType_Whole = 0; //全天交易
        TradeDateType_Morning = 1; //上午交易，下午休市
        TradeDateType_Afternoon = 2; //下午交易，上午休市
    }            
    

1  
2  
3  
4  
5  
6  

**TradeDateType**

    enum TradeDateType
    {
        TradeDateType_Whole = 0; //全天交易
        TradeDateType_Morning = 1; //上午交易，下午休市
        TradeDateType_Afternoon = 2; //下午交易，上午休市
    }            
    

1  
2  
3  
4  
5  
6  

**TradeDateType**

    enum TradeDateType
    {
        TradeDateType_Whole = 0; //全天交易
        TradeDateType_Morning = 1; //上午交易，下午休市
        TradeDateType_Afternoon = 2; //下午交易，上午休市
    }            
    

1  
2  
3  
4  
5  
6  

**TradeDateType**

    enum TradeDateType
    {
        TradeDateType_Whole = 0; //全天交易
        TradeDateType_Morning = 1; //上午交易，下午休市
        TradeDateType_Afternoon = 2; //下午交易，上午休市
    }            
    

1  
2  
3  
4  
5  
6  

**TradeDateType**

    enum TradeDateType
    {
        TradeDateType_Whole = 0; //全天交易
        TradeDateType_Morning = 1; //上午交易，下午休市
        TradeDateType_Afternoon = 2; //下午交易，上午休市
    }            
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#6556)
 窝轮状态
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **WarrantStatus**

*   `NONE`
    
    未知
    
*   `NORMAL`
    
    正常状态
    
*   `SUSPEND`
    
    停牌
    
*   `STOP_TRADE`
    
    终止交易
    
*   `PENDING_LISTING`
    
    等待上市
    

**WarrantStatus**

    enum WarrantStatus
    {
        WarrantStatus_Unknow = 0; //未知
        WarrantStatus_Normal = 1; //正常状态
        WarrantStatus_Suspend = 2; //停牌
        WarrantStatus_StopTrade = 3; //终止交易
        WarrantStatus_PendingListing = 4; //等待上市
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**WarrantStatus**

    enum WarrantStatus
    {
        WarrantStatus_Unknow = 0; //未知
        WarrantStatus_Normal = 1; //正常状态
        WarrantStatus_Suspend = 2; //停牌
        WarrantStatus_StopTrade = 3; //终止交易
        WarrantStatus_PendingListing = 4; //等待上市
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**WarrantStatus**

    enum WarrantStatus
    {
        WarrantStatus_Unknow = 0; //未知
        WarrantStatus_Normal = 1; //正常状态
        WarrantStatus_Suspend = 2; //停牌
        WarrantStatus_StopTrade = 3; //终止交易
        WarrantStatus_PendingListing = 4; //等待上市
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**WarrantStatus**

    enum WarrantStatus
    {
        WarrantStatus_Unknow = 0; //未知
        WarrantStatus_Normal = 1; //正常状态
        WarrantStatus_Suspend = 2; //停牌
        WarrantStatus_StopTrade = 3; //终止交易
        WarrantStatus_PendingListing = 4; //等待上市
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**WarrantStatus**

    enum WarrantStatus
    {
        WarrantStatus_Unknow = 0; //未知
        WarrantStatus_Normal = 1; //正常状态
        WarrantStatus_Suspend = 2; //停牌
        WarrantStatus_StopTrade = 3; //终止交易
        WarrantStatus_PendingListing = 4; //等待上市
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

[#](./quote_quote.md#926)
 窝轮类型
-----------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **WrtType**

*   `NONE`
    
    未知
    
*   `CALL`
    
    认购窝轮
    
*   `PUT`
    
    认沽窝轮
    
*   `BULL`
    
    牛证
    
*   `BEAR`
    
    熊证
    
*   `INLINE`
    
    界内证
    

**WarrantType**

    enum WarrantType
    {
        WarrantType_Unknown = 0; //未知
        WarrantType_Buy = 1; //认购窝轮
        WarrantType_Sell = 2; //认沽窝轮
        WarrantType_Bull = 3; //牛证
        WarrantType_Bear = 4; //熊证
        WarrantType_InLine = 5; //界内证
    };
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**WarrantType**

    enum WarrantType
    {
        WarrantType_Unknown = 0; //未知
        WarrantType_Buy = 1; //认购窝轮
        WarrantType_Sell = 2; //认沽窝轮
        WarrantType_Bull = 3; //牛证
        WarrantType_Bear = 4; //熊证
        WarrantType_InLine = 5; //界内证
    };
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**WarrantType**

    enum WarrantType
    {
        WarrantType_Unknown = 0; //未知
        WarrantType_Buy = 1; //认购窝轮
        WarrantType_Sell = 2; //认沽窝轮
        WarrantType_Bull = 3; //牛证
        WarrantType_Bear = 4; //熊证
        WarrantType_InLine = 5; //界内证
    };
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**WarrantType**

    enum WarrantType
    {
        WarrantType_Unknown = 0; //未知
        WarrantType_Buy = 1; //认购窝轮
        WarrantType_Sell = 2; //认沽窝轮
        WarrantType_Bull = 3; //牛证
        WarrantType_Bear = 4; //熊证
        WarrantType_InLine = 5; //界内证
    };
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**WarrantType**

    enum WarrantType
    {
        WarrantType_Unknown = 0; //未知
        WarrantType_Buy = 1; //认购窝轮
        WarrantType_Sell = 2; //认沽窝轮
        WarrantType_Bull = 3; //牛证
        WarrantType_Bear = 4; //熊证
        WarrantType_InLine = 5; //界内证
    };
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

[#](./quote_quote.md#6898)
 所属交易所
-------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **ExchType**

*   `NONE`
    
    未知
    
*   `HK_MAINBOARD`
    
    港交所·主板
    
*   `HK_GEMBOARD`
    
    港交所·创业板
    
*   `HK_HKEX`
    
    港交所
    
*   `US_NYSE`
    
    纽交所
    
*   `US_NASDAQ`
    
    纳斯达克
    
*   `US_PINK`
    
    OTC市场
    
*   `US_AMEX`
    
    美交所
    
*   `US_OPTION`
    
    美国
    
    仅美股期权适用
    
*   `US_NYMEX`
    
    NYMEX
    
*   `US_COMEX`
    
    COMEX
    
*   `US_CBOT`
    
    CBOT
    
*   `US_CME`
    
    CME
    
*   `US_CBOE`
    
    CBOE
    
*   `CN_SH`
    
    上交所
    
*   `CN_SZ`
    
    深交所
    
*   `CN_STIB`
    
    科创板
    
*   `SG_SGX`
    
    新交所
    
*   `JP_OSE`
    
    大阪交易所
    

**ExchType**

    enum ExchType
    {
        ExchType_Unknown = 0; //未知
        ExchType_HK_MainBoard = 1; // 港交所·主板
        ExchType_HK_GEMBoard = 2; //港交所·创业板
        ExchType_HK_HKEX = 3; //港交所
        ExchType_US_NYSE = 4; //纽交所
        ExchType_US_Nasdaq = 5; //纳斯达克
        ExchType_US_Pink = 6; //OTC 市场
        ExchType_US_AMEX = 7; //美交所 
        ExchType_US_Option = 8; //美国（仅美股期权适用）
        ExchType_US_NYMEX = 9; //NYMEX 
        ExchType_US_COMEX = 10; //COMEX
        ExchType_US_CBOT = 11; //CBOT
        ExchType_US_CME = 12; //CME
        ExchType_US_CBOE = 13; //CBOE
        ExchType_CN_SH = 14; //上交所  
        ExchType_CN_SZ = 15; //深交所
        ExchType_CN_STIB = 16; //科创板
        ExchType_SG_SGX = 17; //新交所
        ExchType_JP_OSE = 18; //大阪交易所 
    };
    

1  
2  
3  
4  
5  
6  
7  
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

**ExchType**

    enum ExchType
    {
        ExchType_Unknown = 0; //未知
        ExchType_HK_MainBoard = 1; // 港交所·主板
        ExchType_HK_GEMBoard = 2; //港交所·创业板
        ExchType_HK_HKEX = 3; //港交所
        ExchType_US_NYSE = 4; //纽交所
        ExchType_US_Nasdaq = 5; //纳斯达克
        ExchType_US_Pink = 6; //OTC 市场
        ExchType_US_AMEX = 7; //美交所 
        ExchType_US_Option = 8; //美国（仅美股期权适用）
        ExchType_US_NYMEX = 9; //NYMEX 
        ExchType_US_COMEX = 10; //COMEX
        ExchType_US_CBOT = 11; //CBOT
        ExchType_US_CME = 12; //CME
        ExchType_US_CBOE = 13; //CBOE
        ExchType_CN_SH = 14; //上交所  
        ExchType_CN_SZ = 15; //深交所
        ExchType_CN_STIB = 16; //科创板
        ExchType_SG_SGX = 17; //新交所
        ExchType_JP_OSE = 18; //大阪交易所 
    };
    

1  
2  
3  
4  
5  
6  
7  
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

**ExchType**

    enum ExchType
    {
        ExchType_Unknown = 0; //未知
        ExchType_HK_MainBoard = 1; // 港交所·主板
        ExchType_HK_GEMBoard = 2; //港交所·创业板
        ExchType_HK_HKEX = 3; //港交所
        ExchType_US_NYSE = 4; //纽交所
        ExchType_US_Nasdaq = 5; //纳斯达克
        ExchType_US_Pink = 6; //OTC 市场
        ExchType_US_AMEX = 7; //美交所 
        ExchType_US_Option = 8; //美国（仅美股期权适用）
        ExchType_US_NYMEX = 9; //NYMEX 
        ExchType_US_COMEX = 10; //COMEX
        ExchType_US_CBOT = 11; //CBOT
        ExchType_US_CME = 12; //CME
        ExchType_US_CBOE = 13; //CBOE
        ExchType_CN_SH = 14; //上交所  
        ExchType_CN_SZ = 15; //深交所
        ExchType_CN_STIB = 16; //科创板
        ExchType_SG_SGX = 17; //新交所
        ExchType_JP_OSE = 18; //大阪交易所 
    };
    

1  
2  
3  
4  
5  
6  
7  
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

**ExchType**

    enum ExchType
    {
        ExchType_Unknown = 0; //未知
        ExchType_HK_MainBoard = 1; // 港交所·主板
        ExchType_HK_GEMBoard = 2; //港交所·创业板
        ExchType_HK_HKEX = 3; //港交所
        ExchType_US_NYSE = 4; //纽交所
        ExchType_US_Nasdaq = 5; //纳斯达克
        ExchType_US_Pink = 6; //OTC 市场
        ExchType_US_AMEX = 7; //美交所 
        ExchType_US_Option = 8; //美国（仅美股期权适用）
        ExchType_US_NYMEX = 9; //NYMEX 
        ExchType_US_COMEX = 10; //COMEX
        ExchType_US_CBOT = 11; //CBOT
        ExchType_US_CME = 12; //CME
        ExchType_US_CBOE = 13; //CBOE
        ExchType_CN_SH = 14; //上交所  
        ExchType_CN_SZ = 15; //深交所
        ExchType_CN_STIB = 16; //科创板
        ExchType_SG_SGX = 17; //新交所
        ExchType_JP_OSE = 18; //大阪交易所 
    };
    

1  
2  
3  
4  
5  
6  
7  
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

**ExchType**

    enum ExchType
    {
        ExchType_Unknown = 0; //未知
        ExchType_HK_MainBoard = 1; // 港交所·主板
        ExchType_HK_GEMBoard = 2; //港交所·创业板
        ExchType_HK_HKEX = 3; //港交所
        ExchType_US_NYSE = 4; //纽交所
        ExchType_US_Nasdaq = 5; //纳斯达克
        ExchType_US_Pink = 6; //OTC 市场
        ExchType_US_AMEX = 7; //美交所 
        ExchType_US_Option = 8; //美国（仅美股期权适用）
        ExchType_US_NYMEX = 9; //NYMEX 
        ExchType_US_COMEX = 10; //COMEX
        ExchType_US_CBOT = 11; //CBOT
        ExchType_US_CME = 12; //CME
        ExchType_US_CBOE = 13; //CBOE
        ExchType_CN_SH = 14; //上交所  
        ExchType_CN_SZ = 15; //深交所
        ExchType_CN_STIB = 16; //科创板
        ExchType_SG_SGX = 17; //新交所
        ExchType_JP_OSE = 18; //大阪交易所 
    };
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#1377)
 证券标识
------------------------------------------------------------------------

**Security**

    message Security
    {
        required int32 market = 1; //QotMarket，行情市场
        required string code = 2; //代码
    }
    

1  
2  
3  
4  
5  

[#](./quote_quote.md#5151)
 K 线数据
-------------------------------------------------------------------------

**KLine**

    message KLine
    {
        required string time = 1; //时间戳字符串（格式：yyyy-MM-dd HH:mm:ss）
        required bool isBlank = 2; //是否是空内容的点,若为 true 则只有时间信息
        optional double highPrice = 3; //最高价
        optional double openPrice = 4; //开盘价
        optional double lowPrice = 5; //最低价
        optional double closePrice = 6; //收盘价
        optional double lastClosePrice = 7; //昨收价
        optional int64 volume = 8; //成交量
        optional double turnover = 9; //成交额
        optional double turnoverRate = 10; //换手率（该字段为百分比字段，展示为小数表示）
        optional double pe = 11; //市盈率
        optional double changeRate = 12; //涨跌幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double timestamp = 13; //时间戳
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  

[#](./quote_quote.md#2339)
 基础报价的期权特有字段
-------------------------------------------------------------------------------

**OptionBasicQotExData**

    message OptionBasicQotExData
    {
        required double strikePrice = 1; //行权价
        required int32 contractSize = 2; //每份合约数(整型数据)
        optional double contractSizeFloat = 17; //每份合约数（浮点型数据）
        required int32 openInterest = 3; //未平仓合约数
        required double impliedVolatility = 4; //隐含波动率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double premium = 5; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double delta = 6; //希腊值 Delta
        required double gamma = 7; //希腊值 Gamma
        required double vega = 8; //希腊值 Vega
        required double theta = 9; //希腊值 Theta
        required double rho = 10; //希腊值 Rho
        optional int32 netOpenInterest = 11; //净未平仓合约数，仅港股期权适用
        optional int32 expiryDateDistance = 12; //距离到期日天数，负数表示已过期
        optional double contractNominalValue = 13; //合约名义金额，仅港股期权适用
        optional double ownerLotMultiplier = 14; //相等正股手数，指数期权无该字段，仅港股期权适用
        optional int32 optionAreaType = 15; //OptionAreaType，期权类型（按行权时间）
        optional double contractMultiplier = 16; //合约乘数
        optional int32 indexOptionType = 18; //IndexOptionType，指数期权类型
    }    
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#5522)
 基础报价的期货特有字段
-------------------------------------------------------------------------------

**FutureBasicQotExData**

    message FutureBasicQotExData
    {
        required double lastSettlePrice = 1; //昨结
        required int32 position = 2; //持仓量
        required int32 positionChange = 3; //日增仓
        optional int32 expiryDateDistance = 4; //距离到期日天数
    }    
    

1  
2  
3  
4  
5  
6  
7  

[#](./quote_quote.md#8209)
 基础报价
------------------------------------------------------------------------

**BasicQot**

    message BasicQot
    {
        required Security security = 1; //股票
        optional string name = 24; // 股票名称
        required bool isSuspended = 2; //是否停牌
        required string listTime = 3; //上市日期字符串（此字段停止维护，不建议使用，格式：yyyy-MM-dd）
        required double priceSpread = 4; //价差
        required string updateTime = 5; //最新价的更新时间字符串（格式：yyyy-MM-dd HH:mm:ss），对其他字段不适用
        required double highPrice = 6; //最高价
        required double openPrice = 7; //开盘价
        required double lowPrice = 8; //最低价
        required double curPrice = 9; //最新价
        required double lastClosePrice = 10; //昨收价
        required int64 volume = 11; //成交量
        required double turnover = 12; //成交额
        required double turnoverRate = 13; //换手率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double amplitude = 14; //振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional int32 darkStatus = 15; //DarkStatus, 暗盘交易状态	
        optional OptionBasicQotExData optionExData = 16; //期权特有字段
        optional double listTimestamp = 17; //上市日期时间戳（此字段停止维护，不建议使用）
        optional double updateTimestamp = 18; //最新价的更新时间戳，对其他字段不适用
        optional PreAfterMarketData preMarket = 19; //盘前数据
        optional PreAfterMarketData afterMarket = 20; //盘后数据
        optional int32 secStatus = 21; //SecurityStatus, 股票状态
        optional FutureBasicQotExData futureExData = 22; //期货特有字段
    }
    

1  
2  
3  
4  
5  
6  
7  
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

[#](./quote_quote.md#9551)
 盘前盘后数据
--------------------------------------------------------------------------

**PreAfterMarketData**

    //美股支持盘前盘后数据
    //科创板仅支持盘后数据：成交量，成交额
    message PreAfterMarketData
    {
        optional double price = 1;  // 盘前或盘后## 价格
        optional double highPrice = 2;  // 盘前或盘后## 最高价
        optional double lowPrice = 3;  // 盘前或盘后## 最低价
        optional int64 volume = 4;  // 盘前或盘后## 成交量
        optional double turnover = 5;  // 盘前或盘后## 成交额
        optional double changeVal = 6;  // 盘前或盘后## 涨跌额
        optional double changeRate = 7;  // 盘前或盘后## 涨跌幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        optional double amplitude = 8;  // 盘前或盘后## 振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  

[#](./quote_quote.md#9906)
 分时数据
------------------------------------------------------------------------

**TimeShare**

    message TimeShare
    {
        required string time = 1; //时间字符串（格式：yyyy-MM-dd HH:mm:ss）
        required int32 minute = 2; //距离0点过了多少分钟
        required bool isBlank = 3; //是否是空内容的点,若为 true 则只有时间信息
        optional double price = 4; //当前价
        optional double lastClosePrice = 5; //昨收价
        optional double avgPrice = 6; //均价
        optional int64 volume = 7; //成交量
        optional double turnover = 8; //成交额
        optional double timestamp = 9; //时间戳
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

[#](./quote_quote.md#1062)
 证券基本静态信息
----------------------------------------------------------------------------

**SecurityStaticBasic**

    
    message SecurityStaticBasic
    {
        required Qot_Common.Security security = 1; //股票
        required int64 id = 2; //股票 ID
        required int32 lotSize = 3; //每手数量,期权类型表示一份合约的股数
        required int32 secType = 4; //Qot_Common.SecurityType,股票类型
        required string name = 5; //股票名字
        required string listTime = 6; //上市时间字符串（此字段停止维护，不建议使用，格式：yyyy-MM-dd）
        optional bool delisting = 7; //是否退市
        optional double listTimestamp = 8; //上市时间戳（此字段停止维护，不建议使用）
        optional int32 exchType = 9; //Qot_Common.ExchType,所属交易所
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  

[#](./quote_quote.md#145)
 窝轮额外静态信息
---------------------------------------------------------------------------

**WarrantStaticExData**

    message WarrantStaticExData
    {
        required int32 type = 1; //Qot_Common.WarrantType,窝轮类型
        required Qot_Common.Security owner = 2; //所属正股
    }    
    

1  
2  
3  
4  
5  

[#](./quote_quote.md#3224)
 期权额外静态信息
----------------------------------------------------------------------------

**OptionStaticExData**

    message OptionStaticExData
    {
        required int32 type = 1; //Qot_Common.OptionType,期权
        required Qot_Common.Security owner = 2; //标的股
        required string strikeTime = 3; //行权日（格式：yyyy-MM-dd）
        required double strikePrice = 4; //行权价
        required bool suspend = 5; //是否停牌
        required string market = 6; //发行市场名字
        optional double strikeTimestamp = 7; //行权日时间戳
        optional int32 indexOptionType = 8; //Qot_Common.IndexOptionType, 指数期权的类型，仅在指数期权有效
    	optional int32 expirationCycle = 9; // ExpirationCycle，交割周期
        optional int32 optionStandardType = 10; // OptionStandardType，标准期权
        optional int32 optionSettlementMode = 11; // OptionSettlementMode，结算方式
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

[#](./quote_quote.md#8867)
 期货额外静态信息
----------------------------------------------------------------------------

**FutureStaticExData**

    message FutureStaticExData
    {
        required string lastTradeTime = 1; //最后交易日，只有非主连期货合约才有该字段
        optional double lastTradeTimestamp = 2; //最后交易日时间戳，只有非主连期货合约才有该字段
        required bool isMainContract = 3; //是否主连合约
    }    
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#609)
 证券静态信息
-------------------------------------------------------------------------

**SecurityStaticInfo**

    message SecurityStaticInfo
    {
        required SecurityStaticBasic basic = 1; //证券基本静态信息
        optional WarrantStaticExData warrantExData = 2; //窝轮额外静态信息
        optional OptionStaticExData optionExData = 3; //期权额外静态信息
        optional FutureStaticExData futureExData = 4; //期货额外静态信息
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./quote_quote.md#4135)
 买卖经纪
------------------------------------------------------------------------

**Broker**

    message Broker
    {
        required int64 id = 1; //经纪 ID
        required string name = 2; //经纪名称
        required int32 pos = 3; //经纪档位
        
        //以下为港股 SF 行情特有字段
        optional int64 orderID = 4; //交易所订单 ID，与交易接口返回的订单 ID 并不一样
        optional int64 volume = 5; //订单股数
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

[#](./quote_quote.md#9044)
 逐笔成交
------------------------------------------------------------------------

**Ticker**

    message Ticker
    {
        required string time = 1; //时间字符串（格式：yyyy-MM-dd HH:mm:ss）
        required int64 sequence = 2; // 唯一标识
        required int32 dir = 3; //TickerDirection, 买卖方向
        required double price = 4; //价格
        required int64 volume = 5; //成交量
        required double turnover = 6; //成交额
        optional double recvTime = 7; //收到推送数据的本地时间戳，用于定位延迟
        optional int32 type = 8; //TickerType, 逐笔类型
        optional int32 typeSign = 9; //逐笔类型符号
        optional int32 pushDataType = 10; //用于区分推送情况，仅推送时有该字段
        optional double timestamp = 11; //时间戳
    }	
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

[#](./quote_quote.md#25)
 买卖档明细
-----------------------------------------------------------------------

**OrderBookDetail**

    message OrderBookDetail
    {
        required int64 orderID = 1; //交易所订单 ID，与交易接口返回的订单 ID 并不一样
        required int64 volume = 2; //订单股数
    }
    

1  
2  
3  
4  
5  

[#](./quote_quote.md#6364)
 买卖档
-----------------------------------------------------------------------

**OrderBook**

    message OrderBook
    {
        required double price = 1; //委托价格
        required int64 volume = 2; //委托数量
        required int32 orederCount = 3; //委托订单个数
        repeated OrderBookDetail detailList = 4; //订单信息，港股 SF，美股深度摆盘特有
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./quote_quote.md#52)
 持股变动
----------------------------------------------------------------------

**ShareHoldingChange**

    message ShareHoldingChange
    {
        required string holderName = 1; //持有者名称（机构名称 或 基金名称 或 高管姓名）
        required double holdingQty = 2; //当前持股数量
        required double holdingRatio = 3; //当前持股百分比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        required double changeQty = 4; //较上一次变动数量
        required double changeRatio = 5; //较上一次变动百分比（该字段为百分比字段，默认不展示 %，如20实际对应20%。是相对于自身的比例，而不是总的。如总股本1万股，持有100股，持股百分比是1%，卖掉50股，变动比例是50%，而不是0.5%）
        required string time = 6; //发布时间（格式：yyyy-MM-dd HH:mm:ss）
        optional double timestamp = 7; //时间戳
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

[#](./quote_quote.md#2160)
 单个订阅类型信息
----------------------------------------------------------------------------

**SubInfo**

    message SubInfo
    {
        required int32 subType = 1;  //Qot_Common.SubType,订阅类型
        repeated Qot_Common.Security securityList = 2; 	//订阅该类型行情的证券
    }	
    

1  
2  
3  
4  
5  

[#](./quote_quote.md#6578)
 单条连接订阅信息
----------------------------------------------------------------------------

**ConnSubInfo**

    message ConnSubInfo
    {
        repeated SubInfo subInfoList = 1; //该连接订阅信息
        required int32 usedQuota = 2; //该连接已经使用的订阅额度
        required bool isOwnConnData = 3; //用于区分是否是自己连接的数据
    }
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#2571)
 板块信息
------------------------------------------------------------------------

**PlateInfo**

    message PlateInfo
    {
        required Qot_Common.Security plate = 1; //板块
        required string name = 2; //板块名字
        optional int32 plateType = 3; //PlateSetType 板块类型, 仅3207（获取股票所属板块）协议返回该字段
    }
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#7370)
 复权信息
------------------------------------------------------------------------

**Rehab**

    message Rehab
    {
        required string time = 1; //时间字符串（格式：yyyy-MM-dd）
        required int64 companyActFlag = 2; //公司行动(CompanyAct)组合标志位,指定某些字段值是否有效
        required double fwdFactorA = 3; //前复权因子 A
        required double fwdFactorB = 4; //前复权因子 B
        required double bwdFactorA = 5; //后复权因子 A
        required double bwdFactorB = 6; //后复权因子 B
        optional int32 splitBase = 7; //拆股(例如，1拆5，Base 为1，Ert 为5)
        optional int32 splitErt = 8;	
        optional int32 joinBase = 9; //合股(例如，50合1，Base 为50，Ert 为1)
        optional int32 joinErt = 10;	
        optional int32 bonusBase = 11; //送股(例如，10送3, Base 为10,Ert 为3)
        optional int32 bonusErt = 12;	
        optional int32 transferBase = 13; //转赠股(例如，10转3, Base 为10,Ert 为3)
        optional int32 transferErt = 14;	
        optional int32 allotBase = 15; //配股(例如，10送2, 配股价为6.3元, Base 为10, Ert 为2, Price 为6.3)
        optional int32 allotErt = 16;	
        optional double allotPrice = 17;	
        optional int32 addBase = 18; //增发股(例如，10送2, 增发股价为6.3元, Base 为10, Ert 为2, Price 为6.3)
        optional int32 addErt = 19;	
        optional double addPrice = 20;	
        optional double dividend = 21; //现金分红(例如，每10股派现0.5元,则该字段值为0.05)
        optional double spDividend = 22; //特别股息(例如，每10股派特别股息0.5元,则该字段值为0.05)
        optional double timestamp = 23; //时间戳
    }
    

1  
2  
3  
4  
5  
6  
7  
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

> *   公司行动组合标志位参见 [CompanyAct](./quote_quote.md#1239)
>     

[#](./quote_quote.md#2235)
 交割周期
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **ExpirationCycle**

*   `NONE`
    
    未知
    
*   `WEEK`
    
    周期权
    
*   `MONTH`
    
    月期权
    
*   `END_OF_MONTH`
    
    月末期权
    
*   `QUARTERLY`
    
    季期权
    
*   `WEEKMON`
    
    周期权-周一
    
*   `WEEKTUE`
    
    周期权-周二
    
*   `WEEKWED`
    
    周期权-周三
    
*   `WEEKTHU`
    
    周期权-周四
    
*   `WEEKFRI`
    
    周期权-周五
    

**ExpirationCycle**

    enum ExpirationCycle
    {
        ExperationCycle_Unknow = 0; //未知
        ExperationCycle_Week = 1; //周期权
        ExperationCycle_Month = 2; //月期权
    }
    

1  
2  
3  
4  
5  
6  

**ExpirationCycle**

    enum ExpirationCycle
    {
        ExperationCycle_Unknow = 0; //未知
        ExperationCycle_Week = 1; //周期权
        ExperationCycle_Month = 2; //月期权
    	ExpirationCycle_MonthEnd = 3;  // 月末期权
        ExpirationCycle_Quarter = 4; //季度期权
        ExpirationCycle_WeekMon = 11; //周一
        ExpirationCycle_WeekTue = 12; //周二
        ExpirationCycle_WeekWed = 13; //周三
        ExpirationCycle_WeekThu = 14; //周四
        ExpirationCycle_WeekFri = 15; //周五
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  

**ExpirationCycle**

    enum ExpirationCycle
    {
        ExperationCycle_Unknow = 0; //未知
        ExperationCycle_Week = 1; //周期权
        ExperationCycle_Month = 2; //月期权
    	ExpirationCycle_MonthEnd = 3;  // 月末期权
        ExpirationCycle_Quarter = 4; //季度期权
        ExpirationCycle_WeekMon = 11; //周一
        ExpirationCycle_WeekTue = 12; //周二
        ExpirationCycle_WeekWed = 13; //周三
        ExpirationCycle_WeekThu = 14; //周四
        ExpirationCycle_WeekFri = 15; //周五
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  

**ExpirationCycle**

    enum ExpirationCycle
    {
        ExperationCycle_Unknow = 0; //未知
        ExperationCycle_Week = 1; //周期权
        ExperationCycle_Month = 2; //月期权
    	ExpirationCycle_MonthEnd = 3;  // 月末期权
        ExpirationCycle_Quarter = 4; //季度期权
        ExpirationCycle_WeekMon = 11; //周一
        ExpirationCycle_WeekTue = 12; //周二
        ExpirationCycle_WeekWed = 13; //周三
        ExpirationCycle_WeekThu = 14; //周四
        ExpirationCycle_WeekFri = 15; //周五
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  

**ExpirationCycle**

    enum ExpirationCycle
    {
        ExperationCycle_Unknow = 0; //未知
        ExperationCycle_Week = 1; //周期权
        ExperationCycle_Month = 2; //月期权
    	ExpirationCycle_MonthEnd = 3;  // 月末期权
        ExpirationCycle_Quarter = 4; //季度期权
        ExpirationCycle_WeekMon = 11; //周一
        ExpirationCycle_WeekTue = 12; //周二
        ExpirationCycle_WeekWed = 13; //周三
        ExpirationCycle_WeekThu = 14; //周四
        ExpirationCycle_WeekFri = 15; //周五
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  

[#](./quote_quote.md#8952)
 期权标准类型
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **OptionStandardType**

*   `NONE`
    
    未知
    
*   `STANDARD`
    
    标准期权
    
*   `NON_STANDARD`
    
    非标准期权
    

**OptionStandardType**

    enum OptionStandardType
    {
        OptionStandardType_Unknown = 0; //未知
        OptionStandardType_Standard = 1; // 标准
        OptionStandardType_NonStandard = 2; // 非标准
    }
    

1  
2  
3  
4  
5  
6  

**OptionStandardType**

    enum OptionStandardType
    {
        OptionStandardType_Unknown = 0; //未知
        OptionStandardType_Standard = 1; // 标准
        OptionStandardType_NonStandard = 2; // 非标准
    }
    

1  
2  
3  
4  
5  
6  

**OptionStandardType**

    enum OptionStandardType
    {
        OptionStandardType_Unknown = 0; //未知
        OptionStandardType_Standard = 1; // 标准
        OptionStandardType_NonStandard = 2; // 非标准
    }
    

1  
2  
3  
4  
5  
6  

**OptionStandardType**

    enum OptionStandardType
    {
        OptionStandardType_Unknown = 0; //未知
        OptionStandardType_Standard = 1; // 标准
        OptionStandardType_NonStandard = 2; // 非标准
    }
    

1  
2  
3  
4  
5  
6  

**OptionStandardType**

    enum OptionStandardType
    {
        OptionStandardType_Unknown = 0; //未知
        OptionStandardType_Standard = 1; // 标准
        OptionStandardType_NonStandard = 2; // 非标准
    }
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#1550)
 期权结算方式
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **OptionSettlementMode**

*   `NONE`
    
    未知
    
*   `AM`
    
    亚式期权
    
*   `PM`
    
    路径依赖型
    

**OptionSettlementMode**

    enum OptionSettlementMode
    {
        OptionSettlementMode_Unknown = 0; //未知
        OptionSettlementMode_AM = 1; // AM
        OptionSettlementMode_PM = 2; // PM
    }
    

1  
2  
3  
4  
5  
6  

**OptionSettlementMode**

    enum OptionSettlementMode
    {
        OptionSettlementMode_Unknown = 0; //未知
        OptionSettlementMode_AM = 1; // AM
        OptionSettlementMode_PM = 2; // PM
    }
    

1  
2  
3  
4  
5  
6  

**OptionSettlementMode**

    enum OptionSettlementMode
    {
        OptionSettlementMode_Unknown = 0; //未知
        OptionSettlementMode_AM = 1; // AM
        OptionSettlementMode_PM = 2; // PM
    }
    

1  
2  
3  
4  
5  
6  

**OptionSettlementMode**

    enum OptionSettlementMode
    {
        OptionSettlementMode_Unknown = 0; //未知
        OptionSettlementMode_AM = 1; // AM
        OptionSettlementMode_PM = 2; // PM
    }
    

1  
2  
3  
4  
5  
6  

**OptionSettlementMode**

    enum OptionSettlementMode
    {
        OptionSettlementMode_Unknown = 0; //未知
        OptionSettlementMode_AM = 1; // AM
        OptionSettlementMode_PM = 2; // PM
    }
    

1  
2  
3  
4  
5  
6  

[#](./quote_quote.md#1766)
 股票持有者（已废弃）
------------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **StockHolder**

*   `NONE`
    
    未知
    
*   `INSTITUTE`
    
    机构
    
*   `FUND`
    
    基金
    
*   `EXECUTIVE`
    
    高管
    

**HolderCategory**

    enum HolderCategory
    {
        HolderCategory_Unknow = 0; //未知
        HolderCategory_Agency = 1; //机构
        HolderCategory_Fund = 2; //基金
        HolderCategory_SeniorManager = 3; //高管
    }
    

1  
2  
3  
4  
5  
6  
7  

**HolderCategory**

    enum HolderCategory
    {
        HolderCategory_Unknow = 0; //未知
        HolderCategory_Agency = 1; //机构
        HolderCategory_Fund = 2; //基金
        HolderCategory_SeniorManager = 3; //高管
    }
    

1  
2  
3  
4  
5  
6  
7  

**HolderCategory**

    enum HolderCategory
    {
        HolderCategory_Unknow = 0; //未知
        HolderCategory_Agency = 1; //机构
        HolderCategory_Fund = 2; //基金
        HolderCategory_SeniorManager = 3; //高管
    }
    

1  
2  
3  
4  
5  
6  
7  

**HolderCategory**

    enum HolderCategory
    {
        HolderCategory_Unknow = 0; //未知
        HolderCategory_Agency = 1; //机构
        HolderCategory_Fund = 2; //基金
        HolderCategory_SeniorManager = 3; //高管
    }
    

1  
2  
3  
4  
5  
6  
7  

**HolderCategory**

    enum HolderCategory
    {
        HolderCategory_Unknow = 0; //未知
        HolderCategory_Agency = 1; //机构
        HolderCategory_Fund = 2; //基金
        HolderCategory_SeniorManager = 3; //高管
    }
    

1  
2  
3  
4  
5  
6  
7  

← [到价提醒回调](./quote_update-price-reminder.md) [交易接口总览](./trade_overview.md)
 →

[行情定义](./quote_quote.md)