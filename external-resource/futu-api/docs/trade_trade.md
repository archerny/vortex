[#](./trade_trade.md#318)
 交易定义
=======================================================================

[#](./trade_trade.md#9239)
 账户风控状态
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **CltRiskLevel**

*   `NONE`
    
    未知
    
*   `SAFE`
    
    安全
    
*   `WARNING`
    
    预警
    
*   `DANGER`
    
    危险
    
*   `ABSOLUTE_SAFE`
    
    绝对安全
    
*   `OPT_DANGER`
    
    危险
    
    期权相关
    

提示

*   查询期货账户的风险状态，建议使用 risk\_status 字段， 返回结果详见 [CltRiskStatus](./trade_trade.md#3989)
    

**CltRiskLevel**

    enum CltRiskLevel
    {
        CltRiskLevel_Unknown = -1;        // 未知
        CltRiskLevel_Safe = 0;          // 安全
        CltRiskLevel_Warning = 1;       // 预警
        CltRiskLevel_Danger = 2;        // 危险
        CltRiskLevel_AbsoluteSafe = 3;  // 绝对安全
        CltRiskLevel_OptDanger = 4;     // 危险（期权相关）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**CltRiskLevel**

    enum CltRiskLevel
    {
        CltRiskLevel_Unknown = -1;        // 未知
        CltRiskLevel_Safe = 0;          // 安全
        CltRiskLevel_Warning = 1;       // 预警
        CltRiskLevel_Danger = 2;        // 危险
        CltRiskLevel_AbsoluteSafe = 3;  // 绝对安全
        CltRiskLevel_OptDanger = 4;     // 危险（期权相关）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**CltRiskLevel**

    enum CltRiskLevel
    {
        CltRiskLevel_Unknown = -1;        // 未知
        CltRiskLevel_Safe = 0;          // 安全
        CltRiskLevel_Warning = 1;       // 预警
        CltRiskLevel_Danger = 2;        // 危险
        CltRiskLevel_AbsoluteSafe = 3;  // 绝对安全
        CltRiskLevel_OptDanger = 4;     // 危险（期权相关）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**CltRiskLevel**

    enum CltRiskLevel
    {
        CltRiskLevel_Unknown = -1;        // 未知
        CltRiskLevel_Safe = 0;          // 安全
        CltRiskLevel_Warning = 1;       // 预警
        CltRiskLevel_Danger = 2;        // 危险
        CltRiskLevel_AbsoluteSafe = 3;  // 绝对安全
        CltRiskLevel_OptDanger = 4;     // 危险（期权相关）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**CltRiskLevel**

    enum CltRiskLevel
    {
        CltRiskLevel_Unknown = -1;        // 未知
        CltRiskLevel_Safe = 0;          // 安全
        CltRiskLevel_Warning = 1;       // 预警
        CltRiskLevel_Danger = 2;        // 危险
        CltRiskLevel_AbsoluteSafe = 3;  // 绝对安全
        CltRiskLevel_OptDanger = 4;     // 危险（期权相关）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

[#](./trade_trade.md#8019)
 货币类型
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **Currency**

*   `NONE`
    
    未知货币
    
*   `HKD`
    
    港元
    
*   `USD`
    
    美元
    
*   `CNH`
    
    离岸人民币
    
*   `JPY`
    
    日元
    
*   `SGD`
    
    新元
    
*   `AUD`
    
    澳元
    
*   `CAD`
    
    加拿大元
    
*   `MYR`
    
    马来西亚林吉特
    

**Currency**

    enum Currency
    {
        Currency_Unknown = 0;  //未知货币
        Currency_HKD = 1;   // 港元
        Currency_USD = 2;   // 美元
        Currency_CNH = 3;   // 离岸人民币
        Currency_JPY = 4;   // 日元
        Currency_SGD = 5;   // 新元
    	  Currency_AUD = 6;   // 澳元
        Currency_CAD = 7; // 加拿大元
        Currency_MYR = 8; // 马来西亚林吉特
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

**Currency**

    enum Currency
    {
        Currency_Unknown = 0;  //未知货币
        Currency_HKD = 1;   // 港元
        Currency_USD = 2;   // 美元
        Currency_CNH = 3;   // 离岸人民币
        Currency_JPY = 4;   // 日元
        Currency_SGD = 5;   // 新元
    	  Currency_AUD = 6;   // 澳元
        Currency_CAD = 7; // 加拿大元
        Currency_MYR = 8; // 马来西亚林吉特
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

**Currency**

    enum Currency
    {
        Currency_Unknown = 0;  //未知货币
        Currency_HKD = 1;   // 港元
        Currency_USD = 2;   // 美元
        Currency_CNH = 3;   // 离岸人民币
        Currency_JPY = 4;   // 日元
        Currency_SGD = 5;   // 新元
    	  Currency_AUD = 6;   // 澳元
        Currency_CAD = 7; // 加拿大元
        Currency_MYR = 8; // 马来西亚林吉特
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

**Currency**

    enum Currency
    {
        Currency_Unknown = 0;  //未知货币
        Currency_HKD = 1;   // 港元
        Currency_USD = 2;   // 美元
        Currency_CNH = 3;   // 离岸人民币
        Currency_JPY = 4;   // 日元
        Currency_SGD = 5;   // 新元
    	  Currency_AUD = 6;   // 澳元
        Currency_CAD = 7; // 加拿大元
        Currency_MYR = 8; // 马来西亚林吉特
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

**Currency**

    enum Currency
    {
        Currency_Unknown = 0;  //未知货币
        Currency_HKD = 1;   // 港元
        Currency_USD = 2;   // 美元
        Currency_CNH = 3;   // 离岸人民币
        Currency_JPY = 4;   // 日元
        Currency_SGD = 5;   // 新元
    	  Currency_AUD = 6;   // 澳元、
        Currency_CAD = 7; // 加拿大元
        Currency_MYR = 8; // 马来西亚林吉特
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

[#](./trade_trade.md#5644)
 跟踪类型
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

**TrailType**

*   `NONE`
    
    未知
    
*   `RATIO`
    
    比例
    
*   `AMOUNT`
    
    金额
    

    enum TrailType
    {
    	TrailType_Unknown = 0; //未知类型
    	TrailType_Ratio = 1; //比例
    	TrailType_Amount = 2; //金额
    }
    

1  
2  
3  
4  
5  
6  

    enum TrailType
    {
    	TrailType_Unknown = 0; //未知类型
    	TrailType_Ratio = 1; //比例
    	TrailType_Amount = 2; //金额
    }
    

1  
2  
3  
4  
5  
6  

    enum TrailType
    {
    	TrailType_Unknown = 0; //未知类型
    	TrailType_Ratio = 1; //比例
    	TrailType_Amount = 2; //金额
    }
    

1  
2  
3  
4  
5  
6  

    enum TrailType
    {
    	TrailType_Unknown = 0; //未知类型
    	TrailType_Ratio = 1; //比例
    	TrailType_Amount = 2; //金额
    }
    

1  
2  
3  
4  
5  
6  

    enum TrailType
    {
    	TrailType_Unknown = 0; //未知类型
    	TrailType_Ratio = 1; //比例
    	TrailType_Amount = 2; //金额
    }
    

1  
2  
3  
4  
5  
6  

[#](./trade_trade.md#2969)
 修改订单操作
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **ModifyOrderOp**

*   `NONE`
    
    未知操作
    
*   `NORMAL`
    
    修改订单
    
*   `CANCEL`
    
    撤单
    
    未成交订单将直接从交易所撮合队列中撤销。
    
*   `DISABLE`
    
    使失效
    
    *   指让订单失效，对交易所来说，DISABLE 的效果等同于 CANCEL。
    *   订单「失效」后，未成交订单将直接从交易所撮合队列中撤出，但订单信息（如价格和数量）会继续保留在富途服务器，您随时可以重新 ENABLE 它。
    
*   `ENABLE`
    
    使生效
    
    *   指让处于失效状态的订单重新生效。对交易所来说，ENABLE 等同于下一笔新订单。
    *   订单重新「生效」后，将按照原来的价格数量重新提交到交易所，并按照价格优先、时间优先顺序重新排队。
    
*   `DELETE`
    
    删除
    
    指对已撤单/下单失败的订单进行隐藏操作。
    

**ModifyOrderOp**

    enum ModifyOrderOp
    {
        //港股支持全部操作，美股目前仅支持 ModifyOrderOp_Normal 和 ModifyOrderOp_Cancel
        ModifyOrderOp_Unknown = 0; //未知操作
        ModifyOrderOp_Normal = 1; //修改订单的价格、数量，即以前的改单
        ModifyOrderOp_Cancel = 2; //撤单。未成交订单将直接从交易所撮合队列中撤销。
        ModifyOrderOp_Disable = 3; //使失效。对交易所来说，「失效」的效果等同于 「撤单」。订单「失效」后，未成交订单将直接从交易所撮合队列中撤出，但订单信息（如价格和数量）会继续保留在富途服务器，您随时可以重新使它生效。
        ModifyOrderOp_Enable = 4; //使生效。对交易所来说，「生效」等同于下一笔新订单。订单重新「生效」后，将按照原来的价格数量重新提交到交易所，并按照价格优先、时间优先顺序重新排队。
        ModifyOrderOp_Delete = 5; //删除。指对已撤单/下单失败的订单进行隐藏操作。
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

**ModifyOrderOp**

    enum ModifyOrderOp
    {
        //港股支持全部操作，美股目前仅支持 ModifyOrderOp_Normal 和 ModifyOrderOp_Cancel
        ModifyOrderOp_Unknown = 0; //未知操作
        ModifyOrderOp_Normal = 1; //修改订单的价格、数量，即以前的改单
        ModifyOrderOp_Cancel = 2; //撤单。未成交订单将直接从交易所撮合队列中撤销。
        ModifyOrderOp_Disable = 3; //使失效。对交易所来说，「失效」的效果等同于 「撤单」。订单「失效」后，未成交订单将直接从交易所撮合队列中撤出，但订单信息（如价格和数量）会继续保留在富途服务器，您随时可以重新使它生效。
        ModifyOrderOp_Enable = 4; //使生效。对交易所来说，「生效」等同于下一笔新订单。订单重新「生效」后，将按照原来的价格数量重新提交到交易所，并按照价格优先、时间优先顺序重新排队。
        ModifyOrderOp_Delete = 5; //删除。指对已撤单/下单失败的订单进行隐藏操作。
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

**ModifyOrderOp**

    enum ModifyOrderOp
    {
        //港股支持全部操作，美股目前仅支持 ModifyOrderOp_Normal 和 ModifyOrderOp_Cancel
        ModifyOrderOp_Unknown = 0; //未知操作
        ModifyOrderOp_Normal = 1; //修改订单的价格、数量，即以前的改单
        ModifyOrderOp_Cancel = 2; //撤单。未成交订单将直接从交易所撮合队列中撤销。
        ModifyOrderOp_Disable = 3; //使失效。对交易所来说，「失效」的效果等同于 「撤单」。订单「失效」后，未成交订单将直接从交易所撮合队列中撤出，但订单信息（如价格和数量）会继续保留在富途服务器，您随时可以重新使它生效。
        ModifyOrderOp_Enable = 4; //使生效。对交易所来说，「生效」等同于下一笔新订单。订单重新「生效」后，将按照原来的价格数量重新提交到交易所，并按照价格优先、时间优先顺序重新排队。
        ModifyOrderOp_Delete = 5; //删除。指对已撤单/下单失败的订单进行隐藏操作。
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

**ModifyOrderOp**

    enum ModifyOrderOp
    {
        //港股支持全部操作，美股目前仅支持 ModifyOrderOp_Normal 和 ModifyOrderOp_Cancel
        ModifyOrderOp_Unknown = 0; //未知操作
        ModifyOrderOp_Normal = 1; //修改订单的价格、数量，即以前的改单
        ModifyOrderOp_Cancel = 2; //撤单。未成交订单将直接从交易所撮合队列中撤销。
        ModifyOrderOp_Disable = 3; //使失效。对交易所来说，「失效」的效果等同于 「撤单」。订单「失效」后，未成交订单将直接从交易所撮合队列中撤出，但订单信息（如价格和数量）会继续保留在富途服务器，您随时可以重新使它生效。
        ModifyOrderOp_Enable = 4; //使生效。对交易所来说，「生效」等同于下一笔新订单。订单重新「生效」后，将按照原来的价格数量重新提交到交易所，并按照价格优先、时间优先顺序重新排队。
        ModifyOrderOp_Delete = 5; //删除。指对已撤单/下单失败的订单进行隐藏操作。
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

**ModifyOrderOp**

    enum ModifyOrderOp
    {
        //港股支持全部操作，美股目前仅支持 ModifyOrderOp_Normal 和 ModifyOrderOp_Cancel
        ModifyOrderOp_Unknown = 0; //未知操作
        ModifyOrderOp_Normal = 1; //修改订单的价格、数量，即以前的改单
        ModifyOrderOp_Cancel = 2; //撤单。未成交订单将直接从交易所撮合队列中撤销。
        ModifyOrderOp_Disable = 3; //使失效。对交易所来说，「失效」的效果等同于 「撤单」。订单「失效」后，未成交订单将直接从交易所撮合队列中撤出，但订单信息（如价格和数量）会继续保留在富途服务器，您随时可以重新使它生效。
        ModifyOrderOp_Enable = 4; //使生效。对交易所来说，「生效」等同于下一笔新订单。订单重新「生效」后，将按照原来的价格数量重新提交到交易所，并按照价格优先、时间优先顺序重新排队。
        ModifyOrderOp_Delete = 5; //删除。指对已撤单/下单失败的订单进行隐藏操作。
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  

[#](./trade_trade.md#8317)
 成交状态
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **DealStatus**

*   `OK`
    
    正常
    
*   `CANCELLED`
    
    成交被取消
    
*   `CHANGED`
    
    成交被更改
    

**OrderFillStatus**

    enum OrderFillStatus
    {
        OrderFillStatus_OK = 0; //正常
        OrderFillStatus_Cancelled = 1; //成交被取消
        OrderFillStatus_Changed = 2; //成交被更改
    }
    

1  
2  
3  
4  
5  
6  

**OrderFillStatus**

    enum OrderFillStatus
    {
        OrderFillStatus_OK = 0; //正常
        OrderFillStatus_Cancelled = 1; //成交被取消
        OrderFillStatus_Changed = 2; //成交被更改
    }
    

1  
2  
3  
4  
5  
6  

**OrderFillStatus**

    enum OrderFillStatus
    {
        OrderFillStatus_OK = 0; //正常
        OrderFillStatus_Cancelled = 1; //成交被取消
        OrderFillStatus_Changed = 2; //成交被更改
    }
    

1  
2  
3  
4  
5  
6  

**OrderFillStatus**

    enum OrderFillStatus
    {
        OrderFillStatus_OK = 0; //正常
        OrderFillStatus_Cancelled = 1; //成交被取消
        OrderFillStatus_Changed = 2; //成交被更改
    }
    

1  
2  
3  
4  
5  
6  

**OrderFillStatus**

    enum OrderFillStatus
    {
        OrderFillStatus_OK = 0; //正常
        OrderFillStatus_Cancelled = 1; //成交被取消
        OrderFillStatus_Changed = 2; //成交被更改
    }
    

1  
2  
3  
4  
5  
6  

[#](./trade_trade.md#797)
 订单状态
-----------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **OrderStatus**

*   `NONE`
    
    未知状态
    
*   `WAITING_SUBMIT`
    
    待提交
    
    富途服务器已经收到指令，正在准备提交给上游交易所
    
*   `SUBMITTING`
    
    提交中
    
    富途服务器已将指令发送给上游交易所，上游交易所处理中
    
*   `SUBMITTED`
    
    已提交，等待成交
    
    已经成功提交给上游交易所
    
*   `FILLED_PART`
    
    部分成交
    
    剩余部分仍未撤单。您可选择执行撤单，或者继续等待全部成交
    
*   `FILLED_ALL`
    
    全部已成交
    
*   `CANCELLED_PART`
    
    部分成交，剩余部分已撤单
    
*   `CANCELLED_ALL`
    
    全部已撤单，无成交
    
*   `FAILED`
    
    下单失败，服务拒绝
    
*   `DISABLED`
    
    已失效
    
    您主动执行失效操作后的订单状态，失效订单不会提交到上游交易所
    
*   `DELETED`
    
    已删除，无成交的订单才能删除
    
    您主动执行删除订单操作后的订单状态
    

**OrderStatus**

    enum OrderStatus
    {
        OrderStatus_Unknown = -1; //未知状态
        OrderStatus_WaitingSubmit = 1; //待提交
        OrderStatus_Submitting = 2; //提交中
        OrderStatus_Submitted = 5; //已提交，等待成交
        OrderStatus_Filled_Part = 10; //部分成交
        OrderStatus_Filled_All = 11; //全部已成
        OrderStatus_Cancelled_Part = 14; //部分成交，剩余部分已撤单
        OrderStatus_Cancelled_All = 15; //全部已撤单，无成交
        OrderStatus_Failed = 21; //下单失败，服务拒绝
        OrderStatus_Disabled = 22; //已失效
        OrderStatus_Deleted = 23; //已删除，无成交的订单才能删除
        OrderStatus_FillCancelled = 24; //成交被撤销（一般遇不到，意思是已经成交的订单被回滚撤销，成交无效变为废单）
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

**OrderStatus**

    enum OrderStatus
    {
        OrderStatus_Unknown = -1; //未知状态
        OrderStatus_WaitingSubmit = 1; //等待提交
        OrderStatus_Submitting = 2; //提交中
        OrderStatus_Submitted = 5; //已提交，等待成交
        OrderStatus_Filled_Part = 10; //部分成交
        OrderStatus_Filled_All = 11; //全部已成
        OrderStatus_Cancelled_Part = 14; //部分成交，剩余部分已撤单
        OrderStatus_Cancelled_All = 15; //全部已撤单，无成交
        OrderStatus_Failed = 21; //下单失败，服务拒绝
        OrderStatus_Disabled = 22; //已失效
        OrderStatus_Deleted = 23; //已删除，无成交的订单才能删除
        OrderStatus_FillCancelled = 24; //成交被撤销（一般遇不到，意思是已经成交的订单被回滚撤销，成交无效变为废单）
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

**OrderStatus**

    enum OrderStatus
    {
        OrderStatus_Unknown = -1; //未知状态
        OrderStatus_WaitingSubmit = 1; //等待提交
        OrderStatus_Submitting = 2; //提交中
        OrderStatus_Submitted = 5; //已提交，等待成交
        OrderStatus_Filled_Part = 10; //部分成交
        OrderStatus_Filled_All = 11; //全部已成
        OrderStatus_Cancelled_Part = 14; //部分成交，剩余部分已撤单
        OrderStatus_Cancelled_All = 15; //全部已撤单，无成交
        OrderStatus_Failed = 21; //下单失败，服务拒绝
        OrderStatus_Disabled = 22; //已失效
        OrderStatus_Deleted = 23; //已删除，无成交的订单才能删除
        OrderStatus_FillCancelled = 24; //成交被撤销（一般遇不到，意思是已经成交的订单被回滚撤销，成交无效变为废单）
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

**OrderStatus**

    enum OrderStatus
    {
        OrderStatus_Unknown = -1; //未知状态
        OrderStatus_WaitingSubmit = 1; //等待提交
        OrderStatus_Submitting = 2; //提交中
        OrderStatus_Submitted = 5; //已提交，等待成交
        OrderStatus_Filled_Part = 10; //部分成交
        OrderStatus_Filled_All = 11; //全部已成
        OrderStatus_Cancelled_Part = 14; //部分成交，剩余部分已撤单
        OrderStatus_Cancelled_All = 15; //全部已撤单，无成交
        OrderStatus_Failed = 21; //下单失败，服务拒绝
        OrderStatus_Disabled = 22; //已失效
        OrderStatus_Deleted = 23; //已删除，无成交的订单才能删除
        OrderStatus_FillCancelled = 24; //成交被撤销（一般遇不到，意思是已经成交的订单被回滚撤销，成交无效变为废单）
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

**OrderStatus**

    enum OrderStatus
    {
        OrderStatus_Unknown = -1; //未知状态
        OrderStatus_WaitingSubmit = 1; //等待提交
        OrderStatus_Submitting = 2; //提交中
        OrderStatus_Submitted = 5; //已提交，等待成交
        OrderStatus_Filled_Part = 10; //部分成交
        OrderStatus_Filled_All = 11; //全部已成
        OrderStatus_Cancelled_Part = 14; //部分成交，剩余部分已撤单
        OrderStatus_Cancelled_All = 15; //全部已撤单，无成交
        OrderStatus_Failed = 21; //下单失败，服务拒绝
        OrderStatus_Disabled = 22; //已失效
        OrderStatus_Deleted = 23; //已删除，无成交的订单才能删除
        OrderStatus_FillCancelled = 24; //成交被撤销（一般遇不到，意思是已经成交的订单被回滚撤销，成交无效变为废单）
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

[#](./trade_trade.md#4181)
 订单类型
------------------------------------------------------------------------

提示

*   [实盘交易中，各个品类支持的订单类型](./qa_trade.md#2731)
    
*   模拟交易中，仅支持限价单(NORMAL)和市价单(MARKET)。

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **OrderType**

*   `NONE`
    
    未知类型
    
*   `NORMAL`
    
    限价单
    
*   `MARKET`
    
    市价单
    
*   `ABSOLUTE_LIMIT`
    
    绝对限价订单
    
    只有价格完全匹配才成交，否则下单失败
    
    *   举例：下一笔价格为 5 元的绝对限价买单，卖方的价格必须也是 5 元才能成交，卖方即使低于 5 元也不能成交，下单失败。卖出同理
    
*   `AUCTION`
    
    竞价市价单
    
    仅港股早盘竞价和收盘竞价有效
    
*   `AUCTION_LIMIT`
    
    竞价限价单
    
    仅早盘竞价和收盘竞价有效，参与竞价，且要求满足指定价格才会成交
    
*   `SPECIAL_LIMIT`
    
    特别限价单
    
    成交规则同增强限价订单，且部分成交后，交易所自动撤销订单
    
*   `SPECIAL_LIMIT_ALL`
    
    特别限价且要求全部成交订单
    
    全部成交，否则自动撤单
    
*   `STOP`
    
    止损市价单
    
*   `STOP_LIMIT`
    
    止损限价单
    
*   `MARKET_IF_TOUCHED`
    
    触及市价单（止盈）
    
*   `LIMIT_IF_TOUCHED`
    
    触及限价单（止盈）
    
*   `TRAILING_STOP`
    
    跟踪止损市价单
    
*   `TRAILING_STOP_LIMIT`
    
    跟踪止损限价单
    
*   `TWAP_LIMIT`
    
    时间加权限价算法单（港股和美股）
    
    算法订单只支持订单查询，不支持交易。
    
*   `TWAP`
    
    时间加权市价算法单（仅美股）
    
    算法订单只支持订单查询，不支持交易。
    
*   `VWAP_LIMIT`
    
    成交量加权限价算法单（港股和美股）
    
    算法订单只支持订单查询，不支持交易。
    
*   `VWAP`
    
    成交量加权市价算法单（仅美股）
    
    算法订单只支持订单查询，不支持交易。
    

**OrderType**

    enum OrderType
    {
        OrderType_Unknown = 0; //未知类型
        OrderType_Normal = 1; //限价单
        OrderType_Market = 2; //市价单
        OrderType_AbsoluteLimit = 5; //绝对限价订单（仅港股），只有价格完全匹配才成交，否则下单失败。举例：下一笔价格为 5 元的绝对限价买单，卖方的价格必须也是5元才能成交，卖方即使低于 5 元也不能成交，下单失败。卖出同理
        OrderType_Auction = 6; //竞价订单（仅港股），仅港股早盘竞价和收盘竞价有效
        OrderType_AuctionLimit = 7; //竞价限价订单（仅港股），仅早盘竞价和收盘竞价有效，参与竞价，且要求满足指定价格才会成交
        OrderType_SpecialLimit = 8; //特别限价订单（仅港股），成交规则同增强限价订单，且部分成交后，交易所自动撤销订单
        OrderType_SpecialLimit_All = 9; //特别限价且要求全部成交订单（仅港股）。全部成交，否则自动撤单
        OrderType_Stop = 10; // 止损市价单
        OrderType_StopLimit = 11; // 止损限价单
        OrderType_MarketifTouched = 12; // 触及市价单（止盈）
        OrderType_LimitifTouched = 13; // 触及限价单（止盈）
        OrderType_TrailingStop = 14; // 跟踪止损市价单
        OrderType_TrailingStopLimit = 15; // 跟踪止损限价单
        OrderType_TWAP  = 16; // 时间加权市价算法单（仅美股）
        OrderType_TWAP_LIMIT = 17; // 时间加权限价算法单 （港股和美股）
        OrderType_VWAP  = 18; // 成交量加权市价算法单（仅美股）
        OrderType_VWAP_LIMIT  = 19; // 成交量加权限价算法单（港股和美股）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  

**OrderType**

    enum OrderType
    {
        OrderType_Unknown = 0; //未知类型
        OrderType_Normal = 1; //限价单
        OrderType_Market = 2; //市价单
        OrderType_AbsoluteLimit = 5; //绝对限价订单（仅港股），只有价格完全匹配才成交，否则下单失败。举例：下一笔价格为 5 元的绝对限价买单，卖方的价格必须也是5元才能成交，卖方即使低于 5 元也不能成交，下单失败。卖出同理
        OrderType_Auction = 6; //竞价订单（仅港股），仅港股早盘竞价和收盘竞价有效
        OrderType_AuctionLimit = 7; //竞价限价订单（仅港股），仅早盘竞价和收盘竞价有效，参与竞价，且要求满足指定价格才会成交
        OrderType_SpecialLimit = 8; //特别限价订单（仅港股），成交规则同增强限价订单，且部分成交后，交易所自动撤销订单
        OrderType_SpecialLimit_All = 9; //特别限价且要求全部成交订单（仅港股）。全部成交，否则自动撤单
        OrderType_Stop = 10; // 止损市价单
        OrderType_StopLimit = 11; // 止损限价单
        OrderType_MarketifTouched = 12; // 触及市价单（止盈）
        OrderType_LimitifTouched = 13; // 触及限价单（止盈）
        OrderType_TrailingStop = 14; // 跟踪止损市价单
        OrderType_TrailingStopLimit = 15; // 跟踪止损限价单
        OrderType_TWAP  = 16; // 时间加权市价算法单（仅美股）
        OrderType_TWAP_LIMIT = 17; // 时间加权限价算法单 （港股和美股）
        OrderType_VWAP  = 18; // 成交量加权市价算法单（仅美股）
        OrderType_VWAP_LIMIT  = 19; // 成交量加权限价算法单（港股和美股）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  

**OrderType**

    enum OrderType
    {
        OrderType_Unknown = 0; //未知类型
        OrderType_Normal = 1; //限价单
        OrderType_Market = 2; //市价单
        OrderType_AbsoluteLimit = 5; //绝对限价订单（仅港股），只有价格完全匹配才成交，否则下单失败。举例：下一笔价格为 5 元的绝对限价买单，卖方的价格必须也是5元才能成交，卖方即使低于 5 元也不能成交，下单失败。卖出同理
        OrderType_Auction = 6; //竞价订单（仅港股），仅港股早盘竞价和收盘竞价有效
        OrderType_AuctionLimit = 7; //竞价限价订单（仅港股），仅早盘竞价和收盘竞价有效，参与竞价，且要求满足指定价格才会成交
        OrderType_SpecialLimit = 8; //特别限价订单（仅港股），成交规则同增强限价订单，且部分成交后，交易所自动撤销订单
        OrderType_SpecialLimit_All = 9; //特别限价且要求全部成交订单（仅港股）。全部成交，否则自动撤单
        OrderType_Stop = 10; // 止损市价单
        OrderType_StopLimit = 11; // 止损限价单
        OrderType_MarketifTouched = 12; // 触及市价单（止盈）
        OrderType_LimitifTouched = 13; // 触及限价单（止盈）
        OrderType_TrailingStop = 14; // 跟踪止损市价单
        OrderType_TrailingStopLimit = 15; // 跟踪止损限价单
        OrderType_TWAP  = 16; // 时间加权市价算法单（仅美股）
        OrderType_TWAP_LIMIT = 17; // 时间加权限价算法单 （港股和美股）
        OrderType_VWAP  = 18; // 成交量加权市价算法单（仅美股）
        OrderType_VWAP_LIMIT  = 19; // 成交量加权限价算法单（港股和美股）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  

**OrderType**

    enum OrderType
    {
        OrderType_Unknown = 0; //未知类型
        OrderType_Normal = 1; //限价单
        OrderType_Market = 2; //市价单
        OrderType_AbsoluteLimit = 5; //绝对限价订单（仅港股），只有价格完全匹配才成交，否则下单失败。举例：下一笔价格为 5 元的绝对限价买单，卖方的价格必须也是5元才能成交，卖方即使低于 5 元也不能成交，下单失败。卖出同理
        OrderType_Auction = 6; //竞价订单（仅港股），仅港股早盘竞价和收盘竞价有效
        OrderType_AuctionLimit = 7; //竞价限价订单（仅港股），仅早盘竞价和收盘竞价有效，参与竞价，且要求满足指定价格才会成交
        OrderType_SpecialLimit = 8; //特别限价订单（仅港股），成交规则同增强限价订单，且部分成交后，交易所自动撤销订单
        OrderType_SpecialLimit_All = 9; //特别限价且要求全部成交订单（仅港股）。全部成交，否则自动撤单
        OrderType_Stop = 10; // 止损市价单
        OrderType_StopLimit = 11; // 止损限价单
        OrderType_MarketifTouched = 12; // 触及市价单（止盈）
        OrderType_LimitifTouched = 13; // 触及限价单（止盈）
        OrderType_TrailingStop = 14; // 跟踪止损市价单
        OrderType_TrailingStopLimit = 15; // 跟踪止损限价单
        OrderType_TWAP  = 16; // 时间加权市价算法单（仅美股）
        OrderType_TWAP_LIMIT = 17; // 时间加权限价算法单 （港股和美股）
        OrderType_VWAP  = 18; // 成交量加权市价算法单（仅美股）
        OrderType_VWAP_LIMIT  = 19; // 成交量加权限价算法单（港股和美股）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  

**OrderType**

    enum OrderType
    {
        OrderType_Unknown = 0; //未知类型
        OrderType_Normal = 1; //限价单
        OrderType_Market = 2; //市价单
        OrderType_AbsoluteLimit = 5; //绝对限价订单（仅港股），只有价格完全匹配才成交，否则下单失败。举例：下一笔价格为 5 元的绝对限价买单，卖方的价格必须也是5元才能成交，卖方即使低于 5 元也不能成交，下单失败。卖出同理
        OrderType_Auction = 6; //竞价订单（仅港股），仅港股早盘竞价和收盘竞价有效
        OrderType_AuctionLimit = 7; //竞价限价订单（仅港股），仅早盘竞价和收盘竞价有效，参与竞价，且要求满足指定价格才会成交
        OrderType_SpecialLimit = 8; //特别限价订单（仅港股），成交规则同增强限价订单，且部分成交后，交易所自动撤销订单
        OrderType_SpecialLimit_All = 9; //特别限价且要求全部成交订单（仅港股）。全部成交，否则自动撤单
        OrderType_Stop = 10; // 止损市价单
        OrderType_StopLimit = 11; // 止损限价单
        OrderType_MarketifTouched = 12; // 触及市价单（止盈）
        OrderType_LimitifTouched = 13; // 触及限价单（止盈）
        OrderType_TrailingStop = 14; // 跟踪止损市价单
        OrderType_TrailingStopLimit = 15; // 跟踪止损限价单
        OrderType_TWAP  = 16; // 时间加权市价算法单（仅美股）
        OrderType_TWAP_LIMIT = 17; // 时间加权限价算法单 （港股和美股）
        OrderType_VWAP  = 18; // 成交量加权市价算法单（仅美股）
        OrderType_VWAP_LIMIT  = 19; // 成交量加权限价算法单（港股和美股）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  

[#](./trade_trade.md#2972)
 持仓方向
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **PositionSide**

*   `NONE`
    
    未知方向
    
*   `LONG`
    
    多仓
    
    默认情况是多仓
    
*   `SHORT`
    
    空仓
    

**PositionSide**

    enum PositionSide
    {
        PositionSide_Long = 0; //多仓，默认情况是多仓
        PositionSide_Unknown = -1; //未知方向
        PositionSide_Short = 1; //空仓
    };
    

1  
2  
3  
4  
5  
6  

**PositionSide**

    enum PositionSide
    {
        PositionSide_Long = 0; //多仓，默认情况是多仓
        PositionSide_Unknown = -1; //未知方向
        PositionSide_Short = 1; //空仓
    };
    

1  
2  
3  
4  
5  
6  

**PositionSide**

    enum PositionSide
    {
        PositionSide_Long = 0; //多仓，默认情况是多仓
        PositionSide_Unknown = -1; //未知方向
        PositionSide_Short = 1; //空仓
    };
    

1  
2  
3  
4  
5  
6  

**PositionSide**

    enum PositionSide
    {
        PositionSide_Long = 0; //多仓，默认情况是多仓
        PositionSide_Unknown = -1; //未知方向
        PositionSide_Short = 1; //空仓
    };
    

1  
2  
3  
4  
5  
6  

**PositionSide**

    enum PositionSide
    {
        PositionSide_Long = 0; //多仓，默认情况是多仓
        PositionSide_Unknown = -1; //未知方向
        PositionSide_Short = 1; //空仓
    };
    

1  
2  
3  
4  
5  
6  

[#](./trade_trade.md#3974)
 账户类型
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **TrdAccType**

*   `NONE`
    
    未知类型
    
*   `CASH`
    
    现金账户
    
*   `MARGIN`
    
    保证金账户
    
*   `TFSA`
    
    加拿大免税账户
    
*   `RRSP`
    
    加拿大注册退休账户
    
*   `SRRSP`
    
    加拿大配偶退休账户
    
*   `DERIVATIVE`
    
    日本衍生品账户
    

**TrdAccType**

    enum TrdAccType
    {
        TrdAccType_Unknown = 0; //未知类型
        TrdAccType_Cash = 1;    //现金账户
        TrdAccType_Margin = 2;  //保证金账户
        TrdAccType_TFSA = 3;    //加拿大免税账户
        TrdAccType_RRSP = 4;    //加拿大注册退休账户
        TrdAccType_SRRSP = 5;    //加拿大配偶退休账户
        TrdAccType_Derivatives = 6;    //日本衍生品账户
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

**TrdAccType**

    enum TrdAccType
    {
        TrdAccType_Unknown = 0; //未知类型
        TrdAccType_Cash = 1;    //现金账户
        TrdAccType_Margin = 2;  //保证金账户
        TrdAccType_TFSA = 3;    //加拿大免税账户
        TrdAccType_RRSP = 4;    //加拿大注册退休账户
        TrdAccType_SRRSP = 5;    //加拿大配偶退休账户
        TrdAccType_Derivatives = 6;    //日本衍生品账户
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

**TrdAccType**

    enum TrdAccType
    {
        TrdAccType_Unknown = 0; //未知类型
        TrdAccType_Cash = 1;    //现金账户
        TrdAccType_Margin = 2;  //保证金账户
        TrdAccType_TFSA = 3;    //加拿大免税账户
        TrdAccType_RRSP = 4;    //加拿大注册退休账户
        TrdAccType_SRRSP = 5;    //加拿大配偶退休账户
        TrdAccType_Derivatives = 6;    //日本衍生品账户
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

**TrdAccType**

    enum TrdAccType
    {
        TrdAccType_Unknown = 0; //未知类型
        TrdAccType_Cash = 1;    //现金账户
        TrdAccType_Margin = 2;  //保证金账户
        TrdAccType_TFSA = 3;    //加拿大免税账户
        TrdAccType_RRSP = 4;    //加拿大注册退休账户
        TrdAccType_SRRSP = 5;    //加拿大配偶退休账户
        TrdAccType_Derivatives = 6;    //日本衍生品账户
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

**TrdAccType**

    enum TrdAccType
    {
        TrdAccType_Unknown = 0; //未知类型
        TrdAccType_Cash = 1;    //现金账户
        TrdAccType_Margin = 2;  //保证金账户
        TrdAccType_TFSA = 3;    //加拿大免税账户
        TrdAccType_RRSP = 4;    //加拿大注册退休账户
        TrdAccType_SRRSP = 5;    //加拿大配偶退休账户
        TrdAccType_Derivatives = 6;    //日本衍生品账户
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

[#](./trade_trade.md#6374)
 交易环境
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **TrdEnv**

*   `SIMULATE`
    
    模拟环境
    
*   `REAL`
    
    真实环境
    

**TrdEnv**

    enum TrdEnv
    {
        TrdEnv_Simulate = 0; //模拟环境
        TrdEnv_Real = 1; //真实环境
    }
    

1  
2  
3  
4  
5  

**TrdEnv**

    enum TrdEnv
    {
        TrdEnv_Simulate = 0; //模拟环境
        TrdEnv_Real = 1; //真实环境
    }
    

1  
2  
3  
4  
5  

**TrdEnv**

    enum TrdEnv
    {
        TrdEnv_Simulate = 0; //模拟环境
        TrdEnv_Real = 1; //真实环境
    }
    

1  
2  
3  
4  
5  

**TrdEnv**

    enum TrdEnv
    {
        TrdEnv_Simulate = 0; //模拟环境
        TrdEnv_Real = 1; //真实环境
    }
    

1  
2  
3  
4  
5  

**TrdEnv**

    enum TrdEnv
    {
        TrdEnv_Simulate = 0; //模拟环境
        TrdEnv_Real = 1; //真实环境
    }
    

1  
2  
3  
4  
5  

[#](./trade_trade.md#719)
 交易市场
-----------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **TrdMarket**

*   `NONE`
    
    未知市场
    
*   `HK`
    
    香港市场
    
*   `US`
    
    美国市场
    
*   `CN`
    
    A 股市场
    
    A 股市场仅支持模拟交易，不支持实盘交易
    
*   `HKCC`
    
    香港 A 股通市场
    
    *   A 股通市场仅支持实盘交易，不支持模拟交易
    *   A 股通只能交易沪股通、深股通股票，具体以港交所 [A 股通名单](https://www.hkex.com.hk/mutual-market/stock-connect/eligible-stocks/view-all-eligible-securities?sc_lang=zh-HK)
         为准
    
*   `FUTURES`
    
    期货市场
    
*   `FUTURES_SIMULATE_US`
    
    美国期货模拟市场
    
    最低 OpenD 版本要求：7.7.3908
    
*   `FUTURES_SIMULATE_HK`
    
    香港期货模拟市场
    
    最低 OpenD 版本要求：7.7.3908
    
*   `FUTURES_SIMULATE_SG`
    
    新加坡期货模拟市场
    
    最低 OpenD 版本要求：7.7.3908
    
*   `FUTURES_SIMULATE_JP`
    
    日本期货模拟市场
    
    最低 OpenD 版本要求：7.7.3908
    
*   `HKFUND`
    
    香港基金市场
    
    最低 OpenD 版本要求：8.2.4218
    
*   `USFUND`
    
    美国基金市场
    
    最低 OpenD 版本要求：8.2.4218
    
*   `SG`
    
    新加坡市场
    
    最低 OpenD 版本要求：9.0.5008
    
*   `JP`
    
    日本市场
    
    最低 OpenD 版本要求：9.0.5008
    
*   `AU`
    
    澳大利亚市场
    
    最低 OpenD 版本要求：9.0.5008
    
*   `MY`
    
    马来西亚市场
    
    最低 OpenD 版本要求：9.0.5008
    
*   `CA`
    
    加拿大市场
    
    最低 OpenD 版本要求：9.0.5008
    

**TrdMarket**

    enum TrdMarket
    {
        TrdMarket_Unknown = 0; //未知市场
        TrdMarket_HK = 1; //香港市场（证券、期权）
        TrdMarket_US = 2; //美国市场（证券、期权）
        TrdMarket_CN = 3; //A 股市场（仅用于模拟交易）
        TrdMarket_HKCC = 4; //A 股通市场（股票）
        TrdMarket_Futures = 5; //期货市场（环球期货）
        TrdMarket_SG = 6; //新加坡市场
        TrdMarket_AU = 8; //澳洲市场
        TrdMarket_Futures_Simulate_HK = 10; //香港期货模拟市场
        TrdMarket_Futures_Simulate_US = 11; //美国期货模拟市场
        TrdMarket_Futures_Simulate_SG = 12; //新加坡期货模拟市场
        TrdMarket_Futures_Simulate_JP = 13; //日本期货模拟市场
        TrdMarket_JP = 15; //日本市场
        TrdMarket_MY = 111; //马来西亚市场
        TrdMarket_CA = 112; //加拿大市场
        TrdMarket_HK_Fund = 113; //香港基金市场
        TrdMarket_US_Fund = 123; //美国基金市场
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  

**TrdMarket**

    enum TrdMarket
    {
        TrdMarket_Unknown = 0; //未知市场
        TrdMarket_HK = 1; //香港市场（证券、期权）
        TrdMarket_US = 2; //美国市场（证券、期权）
        TrdMarket_CN = 3; //A 股市场（仅用于模拟交易）
        TrdMarket_HKCC = 4; //A 股通市场（股票）
        TrdMarket_Futures = 5; //期货市场（环球期货）
        TrdMarket_SG = 6; //新加坡市场
        TrdMarket_AU = 8; //澳洲市场
        TrdMarket_Futures_Simulate_HK = 10; //香港期货模拟市场
        TrdMarket_Futures_Simulate_US = 11; //美国期货模拟市场
        TrdMarket_Futures_Simulate_SG = 12; //新加坡期货模拟市场
        TrdMarket_Futures_Simulate_JP = 13; //日本期货模拟市场
        TrdMarket_JP = 15; //日本市场
        TrdMarket_MY = 111; //马来西亚市场
        TrdMarket_CA = 112; //加拿大市场
        TrdMarket_HK_Fund = 113; //香港基金市场
        TrdMarket_US_Fund = 123; //美国基金市场	
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  

**TrdMarket**

    enum TrdMarket
    {
        TrdMarket_Unknown = 0; //未知市场
        TrdMarket_HK = 1; //香港市场（证券、期权）
        TrdMarket_US = 2; //美国市场（证券、期权）
        TrdMarket_CN = 3; //A 股市场（仅用于模拟交易）
        TrdMarket_HKCC = 4; //A 股通市场（股票）
        TrdMarket_Futures = 5; //期货市场（环球期货）
        TrdMarket_SG = 6; //新加坡市场
        TrdMarket_AU = 8; //澳洲市场
        TrdMarket_Futures_Simulate_HK = 10; //香港期货模拟市场
        TrdMarket_Futures_Simulate_US = 11; //美国期货模拟市场
        TrdMarket_Futures_Simulate_SG = 12; //新加坡期货模拟市场
        TrdMarket_Futures_Simulate_JP = 13; //日本期货模拟市场
        TrdMarket_JP = 15; //日本市场
        TrdMarket_MY = 111; //马来西亚市场
        TrdMarket_CA = 112; //加拿大市场
        TrdMarket_HK_Fund = 113; //香港基金市场
        TrdMarket_US_Fund = 123; //美国基金市场	
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  

**TrdMarket**

    enum TrdMarket
    {
        TrdMarket_Unknown = 0; //未知市场
        TrdMarket_HK = 1; //香港市场（证券、期权）
        TrdMarket_US = 2; //美国市场（证券、期权）
        TrdMarket_CN = 3; //A 股市场（仅用于模拟交易）
        TrdMarket_HKCC = 4; //A 股通市场（股票）
        TrdMarket_Futures = 5; //期货市场（环球期货）
        TrdMarket_SG = 6; //新加坡市场
        TrdMarket_AU = 8; //澳洲市场
        TrdMarket_Futures_Simulate_HK = 10; //香港期货模拟市场
        TrdMarket_Futures_Simulate_US = 11; //美国期货模拟市场
        TrdMarket_Futures_Simulate_SG = 12; //新加坡期货模拟市场
        TrdMarket_Futures_Simulate_JP = 13; //日本期货模拟市场
        TrdMarket_JP = 15; //日本市场
        TrdMarket_MY = 111; //马来西亚市场
        TrdMarket_CA = 112; //加拿大市场
        TrdMarket_HK_Fund = 113; //香港基金市场
        TrdMarket_US_Fund = 123; //美国基金市场	
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  

**TrdMarket**

    enum TrdMarket
    {
        TrdMarket_Unknown = 0; //未知市场
        TrdMarket_HK = 1; //香港市场（证券、期权）
        TrdMarket_US = 2; //美国市场（证券、期权）
        TrdMarket_CN = 3; //A 股市场（仅用于模拟交易）
        TrdMarket_HKCC = 4; //A 股通市场（股票）
        TrdMarket_Futures = 5; //期货市场（环球期货）
        TrdMarket_SG = 6; //新加坡市场
        TrdMarket_AU = 8; //澳洲市场
        TrdMarket_Futures_Simulate_HK = 10; //香港期货模拟市场
        TrdMarket_Futures_Simulate_US = 11; //美国期货模拟市场
        TrdMarket_Futures_Simulate_SG = 12; //新加坡期货模拟市场
        TrdMarket_Futures_Simulate_JP = 13; //日本期货模拟市场
        TrdMarket_JP = 15; //日本市场
        TrdMarket_MY = 111; //马来西亚市场
        TrdMarket_CA = 112; //加拿大市场
        TrdMarket_HK_Fund = 113; //香港基金市场
        TrdMarket_US_Fund = 123; //美国基金市场	
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  

[#](./trade_trade.md#121)
 账户状态
-----------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **TrdAccStatus**

*   `ACTIVE`
    
    生效账户
    
*   `DISABLED`
    
    失效账户
    

**TrdAccStatus**

    enum TrdAccStatus
    {
        TrdAccStatus_Active = 0; //生效账户
        TrdAccStatus_Disabled = 1; //失效账户
    }
    

1  
2  
3  
4  
5  

**TrdAccStatus**

    enum TrdAccStatus
    {
        TrdAccStatus_Active = 0; //生效账户
        TrdAccStatus_Disabled = 1; //失效账户
    }
    

1  
2  
3  
4  
5  

**TrdAccStatus**

    enum TrdAccStatus
    {
        TrdAccStatus_Active = 0; //生效账户
        TrdAccStatus_Disabled = 1; //失效账户
    }
    

1  
2  
3  
4  
5  

**TrdAccStatus**

    enum TrdAccStatus
    {
        TrdAccStatus_Active = 0; //生效账户
        TrdAccStatus_Disabled = 1; //失效账户
    }
    

1  
2  
3  
4  
5  

**TrdAccStatus**

    enum TrdAccStatus
    {
        TrdAccStatus_Active = 0; //生效账户
        TrdAccStatus_Disabled = 1; //失效账户
    }
    

1  
2  
3  
4  
5  

[#](./trade_trade.md#6395)
 账户结构
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **TrdAccRole**

*   `NONE`
    
    未知
    
*   `MASTER`
    
    主账户
    
*   `NORMAL`
    
    普通账户
    
*   `IPO`
    
    马来西亚IPO账户
    

**TrdAccRole**

    enum TrdAccRole
    {
        TrdAccRole_Unknown = 0; //未知
        TrdAccRole_Normal = 1; //普通账户
        TrdAccRole_Master = 2; //主账户
        TrdAccRole_IPO = 3; //马来西亚IPO账户
    }
    

1  
2  
3  
4  
5  
6  
7  

**TrdAccRole**

    enum TrdAccRole
    {
        TrdAccRole_Unknown = 0; //未知
        TrdAccRole_Normal = 1; //普通账户
        TrdAccRole_Master = 2; //主账户
        TrdAccRole_IPO = 3; //马来西亚IPO账户
    }
    

1  
2  
3  
4  
5  
6  
7  

**TrdAccRole**

    enum TrdAccRole
    {
        TrdAccRole_Unknown = 0; //未知
        TrdAccRole_Normal = 1; //普通账户
        TrdAccRole_Master = 2; //主账户
        TrdAccRole_IPO = 3; //马来西亚IPO账户
    }
    

1  
2  
3  
4  
5  
6  
7  

**TrdAccRole**

    enum TrdAccRole
    {
        TrdAccRole_Unknown = 0; //未知
        TrdAccRole_Normal = 1; //普通账户
        TrdAccRole_Master = 2; //主账户
        TrdAccRole_IPO = 3; //马来西亚IPO账户
    }
    

1  
2  
3  
4  
5  
6  
7  

**TrdAccRole**

    enum TrdAccRole
    {
        TrdAccRole_Unknown = 0; //未知
        TrdAccRole_Normal = 1; //普通账户
        TrdAccRole_Master = 2; //主账户
        TrdAccRole_IPO = 3; //马来西亚IPO账户
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./trade_trade.md#5084)
 交易证券市场
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

**TrdSecMarket**

    enum TrdSecMarket
    {
        TrdSecMarket_Unknown = 0; //未知市场
        TrdSecMarket_HK = 1; //香港市场（股票、窝轮、牛熊、期权、期货等）
        TrdSecMarket_US = 2; //美国市场（股票、期权、期货等）
        TrdSecMarket_CN_SH = 31; //沪股市场（股票）
        TrdSecMarket_CN_SZ = 32; //深股市场（股票）
        TrdSecMarket_SG = 41;  //新加坡市场（期货）  
        TrdSecMarket_JP = 51;  //日本市场（期货）  
        TrdSecMarket_AU = 61; // 澳大利亚  
        TrdSecMarket_MY = 71; // 马来西亚  
        TrdSecMarket_CA = 81; // 加拿大  
        TrdSecMarket_FX = 91; // 外汇  
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

**TrdSecMarket**

    enum TrdSecMarket
    {
        TrdSecMarket_Unknown = 0; //未知市场
        TrdSecMarket_HK = 1; //香港市场（股票、窝轮、牛熊、期权、期货等）
        TrdSecMarket_US = 2; //美国市场（股票、期权、期货等）
        TrdSecMarket_CN_SH = 31; //沪股市场（股票）
        TrdSecMarket_CN_SZ = 32; //深股市场（股票）
        TrdSecMarket_SG = 41;  //新加坡市场（期货）  
        TrdSecMarket_JP = 51;  //日本市场（期货）  
        TrdSecMarket_AU = 61; // 澳大利亚  
        TrdSecMarket_MY = 71; // 马来西亚  
        TrdSecMarket_CA = 81; // 加拿大  
        TrdSecMarket_FX = 91; // 外汇  
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

**TrdSecMarket**

    enum TrdSecMarket
    {
        TrdSecMarket_Unknown = 0; //未知市场
        TrdSecMarket_HK = 1; //香港市场（股票、窝轮、牛熊、期权、期货等）
        TrdSecMarket_US = 2; //美国市场（股票、期权、期货等）
        TrdSecMarket_CN_SH = 31; //沪股市场（股票）
        TrdSecMarket_CN_SZ = 32; //深股市场（股票）
        TrdSecMarket_SG = 41;  //新加坡市场（期货）  
        TrdSecMarket_JP = 51;  //日本市场（期货）  
        TrdSecMarket_AU = 61; // 澳大利亚  
        TrdSecMarket_MY = 71; // 马来西亚  
        TrdSecMarket_CA = 81; // 加拿大  
        TrdSecMarket_FX = 91; // 外汇  
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

**TrdSecMarket**

    enum TrdSecMarket
    {
        TrdSecMarket_Unknown = 0; //未知市场
        TrdSecMarket_HK = 1; //香港市场（股票、窝轮、牛熊、期权、期货等）
        TrdSecMarket_US = 2; //美国市场（股票、期权、期货等）
        TrdSecMarket_CN_SH = 31; //沪股市场（股票）
        TrdSecMarket_CN_SZ = 32; //深股市场（股票）
        TrdSecMarket_SG = 41;  //新加坡市场（期货）  
        TrdSecMarket_JP = 51;  //日本市场（期货）  
        TrdSecMarket_AU = 61; // 澳大利亚  
        TrdSecMarket_MY = 71; // 马来西亚  
        TrdSecMarket_CA = 81; // 加拿大  
        TrdSecMarket_FX = 91; // 外汇  
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

**TrdSecMarket**

    enum TrdSecMarket
    {
        TrdSecMarket_Unknown = 0; //未知市场
        TrdSecMarket_HK = 1; //香港市场（股票、窝轮、牛熊、期权、期货等）
        TrdSecMarket_US = 2; //美国市场（股票、期权、期货等）
        TrdSecMarket_CN_SH = 31; //沪股市场（股票）
        TrdSecMarket_CN_SZ = 32; //深股市场（股票）
        TrdSecMarket_SG = 41;  //新加坡市场（期货）  
        TrdSecMarket_JP = 51;  //日本市场（期货）  
        TrdSecMarket_AU = 61; // 澳大利亚  
        TrdSecMarket_MY = 71; // 马来西亚  
        TrdSecMarket_CA = 81; // 加拿大  
        TrdSecMarket_FX = 91; // 外汇  
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

[#](./trade_trade.md#5815)
 交易方向
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **TrdSide**

*   `NONE`
    
    未知方向
    
*   `BUY`
    
    买入
    
*   `SELL`
    
    卖出
    
*   `SELL_SHORT`
    
    卖空
    
    *   日本券商适用
    *   其他券商仅用于订单列表展示，不建议作为下单的方向
    
*   `BUY_BACK`
    
    买回
    
    *   日本券商适用
    *   其他券商仅用于订单列表展示，不建议作为下单的方向
    

**TrdSide**

    enum TrdSide
    {
        //客户端下单只传 Buy 或 Sell 即可，SellShort 是美股订单时服务器返回有此方向，BuyBack 目前不存在，但也不排除服务器会传
        TrdSide_Unknown = 0; //未知方向
        TrdSide_Buy = 1; //买入
        TrdSide_Sell = 2; //卖出
        TrdSide_SellShort = 3; //卖空
        TrdSide_BuyBack = 4; //买回
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**TrdSide**

    enum TrdSide
    {
        //客户端下单只传 Buy 或 Sell 即可，SellShort 是美股订单时服务器返回有此方向，BuyBack 目前不存在，但也不排除服务器会传
        TrdSide_Unknown = 0; //未知方向
        TrdSide_Buy = 1; //买入
        TrdSide_Sell = 2; //卖出
        TrdSide_SellShort = 3; //卖空
        TrdSide_BuyBack = 4; //买回
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**TrdSide**

    enum TrdSide
    {
        //客户端下单只传 Buy 或 Sell 即可，SellShort 是美股订单时服务器返回有此方向，BuyBack 目前不存在，但也不排除服务器会传
        TrdSide_Unknown = 0; //未知方向
        TrdSide_Buy = 1; //买入
        TrdSide_Sell = 2; //卖出
        TrdSide_SellShort = 3; //卖空
        TrdSide_BuyBack = 4; //买回
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**TrdSide**

    enum TrdSide
    {
        //客户端下单只传 Buy 或 Sell 即可，SellShort 是美股订单时服务器返回有此方向，BuyBack 目前不存在，但也不排除服务器会传
        TrdSide_Unknown = 0; //未知方向
        TrdSide_Buy = 1; //买入
        TrdSide_Sell = 2; //卖出
        TrdSide_SellShort = 3; //卖空
        TrdSide_BuyBack = 4; //买回
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

**TrdSide**

    enum TrdSide
    {
        //客户端下单只传 Buy 或 Sell 即可，SellShort 是美股订单时服务器返回有此方向，BuyBack 目前不存在，但也不排除服务器会传
        TrdSide_Unknown = 0; //未知方向
        TrdSide_Buy = 1; //买入
        TrdSide_Sell = 2; //卖出
        TrdSide_SellShort = 3; //卖空
        TrdSide_BuyBack = 4; //买回
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

提示

**下单** 接口的交易方向 ，建议仅使用 `买入` 和 `卖出` 两个方向作为入参。  
`卖空` 和 `买回` 仅适用于日本券商，其他券商仅用于 **查询今日订单** ，**查询历史订单** ，**响应订单推送回调** ，**查询当日成交** ，**查询历史成交** ，**响应成交推送回调** 接口的返回字段展示。

[#](./trade_trade.md#4241)
 订单有效期
-------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **TimeInForce**

*   `DAY`
    
    当日有效
    
*   `GTC`
    
    撤单前有效
    

**TimeInForce**

    enum TimeInForce
    {
        TimeInForce_DAY = 0;       // 当日有效
        TimeInForce_GTC = 1;       // 撤单前有效，最多持续90自然日。
    }
    

1  
2  
3  
4  
5  

**TimeInForce**

    enum TimeInForce
    {
        TimeInForce_DAY = 0;       // 当日有效
        TimeInForce_GTC = 1;       // 撤单前有效，最多持续90自然日。
    }
    

1  
2  
3  
4  
5  

**TimeInForce**

    enum TimeInForce
    {
        TimeInForce_DAY = 0;       // 当日有效
        TimeInForce_GTC = 1;       // 撤单前有效，最多持续90自然日。
    }
    

1  
2  
3  
4  
5  

**TimeInForce**

    enum TimeInForce
    {
        TimeInForce_DAY = 0;       // 当日有效
        TimeInForce_GTC = 1;       // 撤单前有效，最多持续90自然日。
    }
    

1  
2  
3  
4  
5  

**TimeInForce**

    enum TimeInForce
    {
        TimeInForce_DAY = 0;       // 当日有效
        TimeInForce_GTC = 1;       // 撤单前有效，最多持续90自然日。
    }
    

1  
2  
3  
4  
5  

[#](./trade_trade.md#572)
 账户所属券商
-------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **SecurityFirm**

*   `NONE`
    
    未知
    
*   `FUTUSECURITIES`
    
    富途证券（香港）
    
*   `FUTUINC`
    
    moomoo证券(美国)
    
*   `FUTUSG`  
    moomoo证券(新加坡)
    
*   `FUTUAU`  
    moomoo证券(澳大利亚)
    
*   `FUTUCA`  
    moomoo证券(加拿大)
    
*   `FUTUMY`  
    moomoo证券(马来西亚)
    
*   `FUTUJP`  
    moomoo证券(日本)
    

**SecurityFirm**

    enum SecurityFirm
    {
        SecurityFirm_Unknown = 0;        //未知
        SecurityFirm_FutuSecurities = 1; //富途证券（香港）
        SecurityFirm_FutuInc = 2;        //moomoo证券(美国)
        SecurityFirm_FutuSG = 3;        //moomoo证券(新加坡)
        SecurityFirm_FutuAU = 4;         //moomoo证券(澳大利亚)
        SecurityFirm_FutuCA = 5;         //富途证券（加拿大）
        SecurityFirm_FutuMY = 6;         //富途证券（马来西亚）
        SecurityFirm_FutuJP = 7;         //富途证券（日本）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

**SecurityFirm**

    enum SecurityFirm
    {
        SecurityFirm_Unknown = 0;        //未知
        SecurityFirm_FutuSecurities = 1; //富途证券（香港）
        SecurityFirm_FutuInc = 2;        //moomoo证券(美国)
        SecurityFirm_FutuSG = 3;        //moomoo证券(新加坡)
        SecurityFirm_FutuAU = 4;         //moomoo证券(澳大利亚)
        SecurityFirm_FutuCA = 5;         //富途证券（加拿大）
        SecurityFirm_FutuMY = 6;         //富途证券（马来西亚）
        SecurityFirm_FutuJP = 7;         //富途证券（日本）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

**SecurityFirm**

    enum SecurityFirm
    {
        SecurityFirm_Unknown = 0;        //未知
        SecurityFirm_FutuSecurities = 1; //富途证券（香港）
        SecurityFirm_FutuInc = 2;        //moomoo证券(美国)
        SecurityFirm_FutuSG = 3;        //moomoo证券(新加坡)
        SecurityFirm_FutuAU = 4;         //moomoo证券(澳大利亚)
        SecurityFirm_FutuCA = 5;         //富途证券（加拿大）
        SecurityFirm_FutuMY = 6;         //富途证券（马来西亚）
        SecurityFirm_FutuJP = 7;         //富途证券（日本）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

**SecurityFirm**

    enum SecurityFirm
    {
        SecurityFirm_Unknown = 0;        //未知
        SecurityFirm_FutuSecurities = 1; //富途证券（香港）
        SecurityFirm_FutuInc = 2;        //moomoo证券(美国)
        SecurityFirm_FutuSG = 3;        //moomoo证券(新加坡)
        SecurityFirm_FutuAU = 4;         //moomoo证券(澳大利亚)
        SecurityFirm_FutuCA = 5;         //富途证券（加拿大）
        SecurityFirm_FutuMY = 6;         //富途证券（马来西亚）
        SecurityFirm_FutuJP = 7;         //富途证券（日本）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

**SecurityFirm**

    enum SecurityFirm
    {
        SecurityFirm_Unknown = 0;        //未知
        SecurityFirm_FutuSecurities = 1; //富途证券（香港）
        SecurityFirm_FutuInc = 2;        //moomoo证券(美国)
        SecurityFirm_FutuSG = 3;        //moomoo证券(新加坡)
        SecurityFirm_FutuAU = 4;         //moomoo证券(澳大利亚)
        SecurityFirm_FutuCA = 5;         //富途证券（加拿大）
        SecurityFirm_FutuMY = 6;         //富途证券（马来西亚）
        SecurityFirm_FutuJP = 7;         //富途证券（日本）
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

[#](./trade_trade.md#6449)
 模拟交易账户类型
----------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

**SimAccType**

*   `NONE`
    
    未知
    
*   `STOCK`
    
    股票模拟账户
    
*   `OPTION`
    
    期权模拟账户
    
*   `FUTURES`
    
    期货模拟账户
    
*   `STOCK_AND_OPTION`
    
    美股融资融券模拟账户
    

**SimAccType**

    enum SimAccType
    {
        SimAccType_Unknown = 0;		//未知
        SimAccType_Stock = 1;		//股票模拟账户（仅用于交易证券类产品，不支持交易期权）
        SimAccType_Option = 2;      //期权模拟账户（仅用于交易期权，不支持交易股票证券类产品）
        SimAccType_Futures = 3;      //期货模拟账户
        SimAccType_StockAndOption = 4;      //美股融资融券模拟账户
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**SimAccType**

    enum SimAccType
    {
        SimAccType_Unknown = 0;		//未知
        SimAccType_Stock = 1;		//股票模拟账户（仅用于交易证券类产品，不支持交易期权）
        SimAccType_Option = 2;      //期权模拟账户（仅用于交易期权，不支持交易股票证券类产品）
        SimAccType_Futures = 3;      //期货模拟账户
        SimAccType_StockAndOption = 4;      //美股融资融券模拟账户
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**SimAccType**

    enum SimAccType
    {
        SimAccType_Unknown = 0;		//未知
        SimAccType_Stock = 1;		//股票模拟账户（仅用于交易证券类产品，不支持交易期权）
        SimAccType_Option = 2;      //期权模拟账户（仅用于交易期权，不支持交易股票证券类产品）
        SimAccType_Futures = 3;      //期货模拟账户
        SimAccType_StockAndOption = 4;      //美股融资融券模拟账户
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**SimAccType**

    enum SimAccType
    {
        SimAccType_Unknown = 0;		//未知
        SimAccType_Stock = 1;		//股票模拟账户（仅用于交易证券类产品，不支持交易期权）
        SimAccType_Option = 2;      //期权模拟账户（仅用于交易期权，不支持交易股票证券类产品）
        SimAccType_Futures = 3;      //期货模拟账户
        SimAccType_StockAndOption = 4;      //美股融资融券模拟账户
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

**SimAccType**

    enum SimAccType
    {
        SimAccType_Unknown = 0;		//未知
        SimAccType_Stock = 1;		//股票模拟账户（仅用于交易证券类产品，不支持交易期权）
        SimAccType_Option = 2;      //期权模拟账户（仅用于交易期权，不支持交易股票证券类产品）
        SimAccType_Futures = 3;      //期货模拟账户
        SimAccType_StockAndOption = 4;      //美股融资融券模拟账户
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

[#](./trade_trade.md#3989)
 风险状态
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **CltRiskStatus**

*   `NONE`
    
    未知
    
*   `LEVEL1`
    
    非常安全
    
*   `LEVEL2`
    
    安全
    
*   `LEVEL3`
    
    较安全
    
*   `LEVEL4`
    
    较低风险
    
*   `LEVEL5`
    
    中等风险
    
*   `LEVEL6`
    
    偏高风险
    
*   `LEVEL7`
    
    预警
    
*   `LEVEL8`
    
    危险
    
*   `LEVEL9`
    
    危险
    

**CltRiskStatus**

    enum CltRiskStatus
    {
      CltRiskStatus_Level1 = 0;  //非常安全
      CltRiskStatus_Level2 = 1;  //安全
      CltRiskStatus_Level3 = 2;  //较安全
      CltRiskStatus_Level4 = 3;  //较低风险
      CltRiskStatus_Level5 = 4;  //中等风险
      CltRiskStatus_Level6 = 5;  //较高风险
      CltRiskStatus_Level7 = 6;  //预警
      CltRiskStatus_Level8 = 7;  //预警
      CltRiskStatus_Level9 = 8;  //预警
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

**CltRiskStatus**

    enum CltRiskStatus
    {
      CltRiskStatus_Level1 = 0;  //非常安全
      CltRiskStatus_Level2 = 1;  //安全
      CltRiskStatus_Level3 = 2;  //较安全
      CltRiskStatus_Level4 = 3;  //较低风险
      CltRiskStatus_Level5 = 4;  //中等风险
      CltRiskStatus_Level6 = 5;  //较高风险
      CltRiskStatus_Level7 = 6;  //预警
      CltRiskStatus_Level8 = 7;  //预警
      CltRiskStatus_Level9 = 8;  //预警
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

**CltRiskStatus**

    enum CltRiskStatus
    {
      CltRiskStatus_Level1 = 0;  //非常安全
      CltRiskStatus_Level2 = 1;  //安全
      CltRiskStatus_Level3 = 2;  //较安全
      CltRiskStatus_Level4 = 3;  //较低风险
      CltRiskStatus_Level5 = 4;  //中等风险
      CltRiskStatus_Level6 = 5;  //较高风险
      CltRiskStatus_Level7 = 6;  //预警
      CltRiskStatus_Level8 = 7;  //预警
      CltRiskStatus_Level9 = 8;  //预警
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

**CltRiskStatus**

    enum CltRiskStatus
    {
      CltRiskStatus_Level1 = 0;  //非常安全
      CltRiskStatus_Level2 = 1;  //安全
      CltRiskStatus_Level3 = 2;  //较安全
      CltRiskStatus_Level4 = 3;  //较低风险
      CltRiskStatus_Level5 = 4;  //中等风险
      CltRiskStatus_Level6 = 5;  //较高风险
      CltRiskStatus_Level7 = 6;  //预警
      CltRiskStatus_Level8 = 7;  //预警
      CltRiskStatus_Level9 = 8;  //预警
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

**CltRiskStatus**

    enum CltRiskStatus
    {
      CltRiskStatus_Level1 = 0;  //非常安全
      CltRiskStatus_Level2 = 1;  //安全
      CltRiskStatus_Level3 = 2;  //较安全
      CltRiskStatus_Level4 = 3;  //较低风险
      CltRiskStatus_Level5 = 4;  //中等风险
      CltRiskStatus_Level6 = 5;  //较高风险
      CltRiskStatus_Level7 = 6;  //预警
      CltRiskStatus_Level8 = 7;  //预警
      CltRiskStatus_Level9 = 8;  //预警
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

[#](./trade_trade.md#1860)
 日内交易限制情况
----------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **DtStatus**

*   `NONE`
    
    未知
    
*   `Unlimited`
    
    无限次
    
    当前可以无限次日内交易，注意留意剩余日内交易购买力
    
*   `EM_Call`
    
    EM-Call
    
    当前状态不能新建仓位，需要补充资产净值至$25000以上，否则会被禁止新建仓位90天
    
*   `DT_Call`
    
    DT-Call
    
    当前状态有未补平的日内交易追缴金额（DT Call），需要在5个交易日内足额入金来补平 DT Call，否则会被禁止新建仓位，直到足额存入资金才会解禁
    

**DTStatus**

    enum DTStatus
    {
    	DTStatus_Unknown = 0; 		//未知
    	DTStatus_Unlimited = 1;		//无限次(当前可以无限次日内交易，注意留意剩余日内交易购买力)
    	DTStatus_EMCall = 2;		//EM Call(当前状态不能新建仓位，需要补充资产净值至$25000以上，否则会被禁止新建仓位90天)
    	DTStatus_DTCall = 3;		//DT Call(当前状态有未补平的日内交易追缴金额（DTCall），需要在5个交易日内足额入金来补平 DTCall，否则会被禁止新建仓位，直到足额存入资金才会解禁)
    }
    

1  
2  
3  
4  
5  
6  
7  

**DTStatus**

    enum DTStatus
    {
    	DTStatus_Unknown = 0; 		//未知
    	DTStatus_Unlimited = 1;		//无限次(当前可以无限次日内交易，注意留意剩余日内交易购买力)
    	DTStatus_EMCall = 2;		//EM Call(当前状态不能新建仓位，需要补充资产净值至$25000以上，否则会被禁止新建仓位90天)
    	DTStatus_DTCall = 3;		//DT Call(当前状态有未补平的日内交易追缴金额（DTCall），需要在5个交易日内足额入金来补平 DTCall，否则会被禁止新建仓位，直到足额存入资金才会解禁)
    }
    

1  
2  
3  
4  
5  
6  
7  

**DTStatus**

    enum DTStatus
    {
    	DTStatus_Unknown = 0; 		//未知
    	DTStatus_Unlimited = 1;		//无限次(当前可以无限次日内交易，注意留意剩余日内交易购买力)
    	DTStatus_EMCall = 2;		//EM Call(当前状态不能新建仓位，需要补充资产净值至$25000以上，否则会被禁止新建仓位90天)
    	DTStatus_DTCall = 3;		//DT Call(当前状态有未补平的日内交易追缴金额（DTCall），需要在5个交易日内足额入金来补平 DTCall，否则会被禁止新建仓位，直到足额存入资金才会解禁)
    }
    

1  
2  
3  
4  
5  
6  
7  

**DTStatus**

    enum DTStatus
    {
    	DTStatus_Unknown = 0; 		//未知
    	DTStatus_Unlimited = 1;		//无限次(当前可以无限次日内交易，注意留意剩余日内交易购买力)
    	DTStatus_EMCall = 2;		//EM Call(当前状态不能新建仓位，需要补充资产净值至$25000以上，否则会被禁止新建仓位90天)
    	DTStatus_DTCall = 3;		//DT Call(当前状态有未补平的日内交易追缴金额（DTCall），需要在5个交易日内足额入金来补平 DTCall，否则会被禁止新建仓位，直到足额存入资金才会解禁)
    }
    

1  
2  
3  
4  
5  
6  
7  

**DTStatus**

    enum DTStatus
    {
    	DTStatus_Unknown = 0; 		//未知
    	DTStatus_Unlimited = 1;		//无限次(当前可以无限次日内交易，注意留意剩余日内交易购买力)
    	DTStatus_EMCall = 2;		//EM Call(当前状态不能新建仓位，需要补充资产净值至$25000以上，否则会被禁止新建仓位90天)
    	DTStatus_DTCall = 3;		//DT Call(当前状态有未补平的日内交易追缴金额（DTCall），需要在5个交易日内足额入金来补平 DTCall，否则会被禁止新建仓位，直到足额存入资金才会解禁)
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./trade_trade.md#7573)
 现金流方向
-------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **CashFlowDirection**

*   `NONE`
    
    未知
    
*   `IN`
    
    现金流入
    
*   `OUT`
    
    现金流出
    

**TrdCashFlowDirection**

    enum TrdCashFlowDirection
    {
    	TrdCashFlowDirection_Unknown = 0; //未知
    	TrdCashFlowDirection_In = 1; //现金流入
    	TrdCashFlowDirection_Out = 2; //现金流出
    }
    

1  
2  
3  
4  
5  
6  

**TrdCashFlowDirection**

    enum TrdCashFlowDirection
    {
    	TrdCashFlowDirection_Unknown = 0; //未知
    	TrdCashFlowDirection_In = 1; //现金流入
    	TrdCashFlowDirection_Out = 2; //现金流出
    }
    

1  
2  
3  
4  
5  
6  

**TrdCashFlowDirection**

    enum TrdCashFlowDirection
    {
    	TrdCashFlowDirection_Unknown = 0; //未知
    	TrdCashFlowDirection_In = 1; //现金流入
    	TrdCashFlowDirection_Out = 2; //现金流出
    }
    

1  
2  
3  
4  
5  
6  

**TrdCashFlowDirection**

    enum TrdCashFlowDirection
    {
    	TrdCashFlowDirection_Unknown = 0; //未知
    	TrdCashFlowDirection_In = 1; //现金流入
    	TrdCashFlowDirection_Out = 2; //现金流出
    }
    

1  
2  
3  
4  
5  
6  

**TrdCashFlowDirection**

    enum TrdCashFlowDirection
    {
    	TrdCashFlowDirection_Unknown = 0; //未知
    	TrdCashFlowDirection_In = 1; //现金流入
    	TrdCashFlowDirection_Out = 2; //现金流出
    }
    

1  
2  
3  
4  
5  
6  

[#](./trade_trade.md#6112)
 日本子账户类型
---------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **SubAccType**

*   `NONE`
    
    未知
    
*   `JP_GENERAL`
    
    一般-Long
    
*   `JP_TOKUTEI`
    
    特定-Long
    
*   `JP_NISA_GENERAL`
    
    一般NISA
    
*   `JP_NISA_TSUMITATE`
    
    累计NISA
    
*   `JP_GENERAL_SHORT`
    
    一般-short
    
*   `JP_TOKUTEI_SHORT`
    
    特定-short
    
*   `JP_HONPO_GENERAL`
    
    本国信用交易抵押品-一般
    
*   `JP_GAIKOKU_GENERAL`
    
    外国信用交易抵押品-一般
    
*   `JP_HONPO_TOKUTEI`
    
    本国信用交易抵押品-特定
    
*   `JP_GAIKOKU_TOKUTEI`
    
    外国信用交易抵押品-特定
    
*   `JP_DERIVATIVE_LONG`
    
    衍生品子账户-Long
    
*   `JP_DERIVATIVE_SHORT`
    
    衍生品子账户-Short
    
*   `JP_HONPO_DERIVATIVE_GENERAL`
    
    本国衍生品证据金子账户-一般
    
*   `JP_GAIKOKU_DERIVATIVE_GENERAL`
    
    外国衍生品证据金子账户-一般
    
*   `JP_HONPO_DERIVATIVE_TOKUTEI`
    
    本国衍生品证据金子账户-特定
    
*   `JP_GAIKOKU_DERIVATIVE_TOKUTEI`
    
    外国衍生品证据金子账户-特定
    

**TrdSubAccType**

    enum TrdSubAccType
    {
    	TrdSubAccType_None = 0; //未知
    	TrdSubAccType_JP_GENERAL = 1; // 一般-Long
    	TrdSubAccType_JP_TOKUTEI = 2; // 特定-Long
    	TrdSubAccType_JP_NISA_GENERAL = 3; // 一般NISA
    	TrdSubAccType_JP_NISA_TSUMITATE = 4; // 累计NISA
    
    	TrdSubAccType_JP_GENERAL_SHORT = 5; // 一般-short
    	TrdSubAccType_JP_TOKUTEI_SHORT = 6; // 特定-short
    	TrdSubAccType_JP_HONPO_GENERAL = 7; // 本国信用交易抵押品-一般
    	TrdSubAccType_JP_GAIKOKU_GENERAL = 8; // 外国信用交易抵押品-一般
    	TrdSubAccType_JP_HONPO_TOKUTEI = 9; // 本国信用交易抵押品-特定
    	TrdSubAccType_JP_GAIKOKU_TOKUTEI = 10; // 外国信用交易抵押品-特定
    
    	TrdSubAccType_JP_DERIVATIVE_LONG = 11; // 衍生品子账户-Long
    	TrdSubAccType_JP_DERIVATIVE_SHORT = 12; // 衍生品子账户-Short
    	TrdSubAccType_JP_HONPO_DERIVATIVE_GENERAL = 13; // 本国衍生品证据金子账户-一般
    	TrdSubAccType_JP_GAIKOKU_DERIVATIVE_GENERAL = 14; // 外国衍生品证据金子账户-一般
    	TrdSubAccType_JP_HONPO_DERIVATIVE_TOKUTEI = 15; // 本国衍生品证据金子账户-特定
    	TrdSubAccType_JP_GAIKOKU_DERIVATIVE_TOKUTEI = 16; // 外国衍生品证据金子账户-特定
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  

**TrdSubAccType**

    enum TrdSubAccType
    {
    	TrdSubAccType_None = 0; //未知
    	TrdSubAccType_JP_GENERAL = 1; // 一般-Long
    	TrdSubAccType_JP_TOKUTEI = 2; // 特定-Long
    	TrdSubAccType_JP_NISA_GENERAL = 3; // 一般NISA
    	TrdSubAccType_JP_NISA_TSUMITATE = 4; // 累计NISA
    
    	TrdSubAccType_JP_GENERAL_SHORT = 5; // 一般-short
    	TrdSubAccType_JP_TOKUTEI_SHORT = 6; // 特定-short
    	TrdSubAccType_JP_HONPO_GENERAL = 7; // 本国信用交易抵押品-一般
    	TrdSubAccType_JP_GAIKOKU_GENERAL = 8; // 外国信用交易抵押品-一般
    	TrdSubAccType_JP_HONPO_TOKUTEI = 9; // 本国信用交易抵押品-特定
    	TrdSubAccType_JP_GAIKOKU_TOKUTEI = 10; // 外国信用交易抵押品-特定
    
    	TrdSubAccType_JP_DERIVATIVE_LONG = 11; // 衍生品子账户-Long
    	TrdSubAccType_JP_DERIVATIVE_SHORT = 12; // 衍生品子账户-Short
    	TrdSubAccType_JP_HONPO_DERIVATIVE_GENERAL = 13; // 本国衍生品证据金子账户-一般
    	TrdSubAccType_JP_GAIKOKU_DERIVATIVE_GENERAL = 14; // 外国衍生品证据金子账户-一般
    	TrdSubAccType_JP_HONPO_DERIVATIVE_TOKUTEI = 15; // 本国衍生品证据金子账户-特定
    	TrdSubAccType_JP_GAIKOKU_DERIVATIVE_TOKUTEI = 16; // 外国衍生品证据金子账户-特定
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  

**TrdSubAccType**

    enum TrdSubAccType
    {
    	TrdSubAccType_None = 0; //未知
    	TrdSubAccType_JP_GENERAL = 1; // 一般-Long
    	TrdSubAccType_JP_TOKUTEI = 2; // 特定-Long
    	TrdSubAccType_JP_NISA_GENERAL = 3; // 一般NISA
    	TrdSubAccType_JP_NISA_TSUMITATE = 4; // 累计NISA
    
    	TrdSubAccType_JP_GENERAL_SHORT = 5; // 一般-short
    	TrdSubAccType_JP_TOKUTEI_SHORT = 6; // 特定-short
    	TrdSubAccType_JP_HONPO_GENERAL = 7; // 本国信用交易抵押品-一般
    	TrdSubAccType_JP_GAIKOKU_GENERAL = 8; // 外国信用交易抵押品-一般
    	TrdSubAccType_JP_HONPO_TOKUTEI = 9; // 本国信用交易抵押品-特定
    	TrdSubAccType_JP_GAIKOKU_TOKUTEI = 10; // 外国信用交易抵押品-特定
    
    	TrdSubAccType_JP_DERIVATIVE_LONG = 11; // 衍生品子账户-Long
    	TrdSubAccType_JP_DERIVATIVE_SHORT = 12; // 衍生品子账户-Short
    	TrdSubAccType_JP_HONPO_DERIVATIVE_GENERAL = 13; // 本国衍生品证据金子账户-一般
    	TrdSubAccType_JP_GAIKOKU_DERIVATIVE_GENERAL = 14; // 外国衍生品证据金子账户-一般
    	TrdSubAccType_JP_HONPO_DERIVATIVE_TOKUTEI = 15; // 本国衍生品证据金子账户-特定
    	TrdSubAccType_JP_GAIKOKU_DERIVATIVE_TOKUTEI = 16; // 外国衍生品证据金子账户-特定
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  

**TrdCashFlowDirection**

**TrdSubAccType**

    enum TrdSubAccType
    {
    	TrdSubAccType_None = 0; //未知
    	TrdSubAccType_JP_GENERAL = 1; // 一般-Long
    	TrdSubAccType_JP_TOKUTEI = 2; // 特定-Long
    	TrdSubAccType_JP_NISA_GENERAL = 3; // 一般NISA
    	TrdSubAccType_JP_NISA_TSUMITATE = 4; // 累计NISA
    
    	TrdSubAccType_JP_GENERAL_SHORT = 5; // 一般-short
    	TrdSubAccType_JP_TOKUTEI_SHORT = 6; // 特定-short
    	TrdSubAccType_JP_HONPO_GENERAL = 7; // 本国信用交易抵押品-一般
    	TrdSubAccType_JP_GAIKOKU_GENERAL = 8; // 外国信用交易抵押品-一般
    	TrdSubAccType_JP_HONPO_TOKUTEI = 9; // 本国信用交易抵押品-特定
    	TrdSubAccType_JP_GAIKOKU_TOKUTEI = 10; // 外国信用交易抵押品-特定
    
    	TrdSubAccType_JP_DERIVATIVE_LONG = 11; // 衍生品子账户-Long
    	TrdSubAccType_JP_DERIVATIVE_SHORT = 12; // 衍生品子账户-Short
    	TrdSubAccType_JP_HONPO_DERIVATIVE_GENERAL = 13; // 本国衍生品证据金子账户-一般
    	TrdSubAccType_JP_GAIKOKU_DERIVATIVE_GENERAL = 14; // 外国衍生品证据金子账户-一般
    	TrdSubAccType_JP_HONPO_DERIVATIVE_TOKUTEI = 15; // 本国衍生品证据金子账户-特定
    	TrdSubAccType_JP_GAIKOKU_DERIVATIVE_TOKUTEI = 16; // 外国衍生品证据金子账户-特定
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  

**TrdSubAccType**

    enum TrdSubAccType
    {
    	TrdSubAccType_None = 0; //未知
    	TrdSubAccType_JP_GENERAL = 1; // 一般-Long
    	TrdSubAccType_JP_TOKUTEI = 2; // 特定-Long
    	TrdSubAccType_JP_NISA_GENERAL = 3; // 一般NISA
    	TrdSubAccType_JP_NISA_TSUMITATE = 4; // 累计NISA
    
    	TrdSubAccType_JP_GENERAL_SHORT = 5; // 一般-short
    	TrdSubAccType_JP_TOKUTEI_SHORT = 6; // 特定-short
    	TrdSubAccType_JP_HONPO_GENERAL = 7; // 本国信用交易抵押品-一般
    	TrdSubAccType_JP_GAIKOKU_GENERAL = 8; // 外国信用交易抵押品-一般
    	TrdSubAccType_JP_HONPO_TOKUTEI = 9; // 本国信用交易抵押品-特定
    	TrdSubAccType_JP_GAIKOKU_TOKUTEI = 10; // 外国信用交易抵押品-特定
    
    	TrdSubAccType_JP_DERIVATIVE_LONG = 11; // 衍生品子账户-Long
    	TrdSubAccType_JP_DERIVATIVE_SHORT = 12; // 衍生品子账户-Short
    	TrdSubAccType_JP_HONPO_DERIVATIVE_GENERAL = 13; // 本国衍生品证据金子账户-一般
    	TrdSubAccType_JP_GAIKOKU_DERIVATIVE_GENERAL = 14; // 外国衍生品证据金子账户-一般
    	TrdSubAccType_JP_HONPO_DERIVATIVE_TOKUTEI = 15; // 本国衍生品证据金子账户-特定
    	TrdSubAccType_JP_GAIKOKU_DERIVATIVE_TOKUTEI = 16; // 外国衍生品证据金子账户-特定
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  

[#](./trade_trade.md#4752)
 资产类别
------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **AssetCategory**

*   `NONE`
    
    未知
    
*   `JP`
    
    本国
    
*   `US`
    
    外国
    

**TrdAssetCategory**

    enum TrdAssetCategory
    {
    	TrdAssetCategory_Unknown = 0; 	//未知
    	TrdAssetCategory_JP = 1;	    //本国
    	TrdAssetCategory_US = 2;	    //外国
    }
    

1  
2  
3  
4  
5  
6  

**TrdAssetCategory**

    enum TrdAssetCategory
    {
    	TrdAssetCategory_Unknown = 0; 	//未知
    	TrdAssetCategory_JP = 1;	    //本国
    	TrdAssetCategory_US = 2;	    //外国
    }
    

1  
2  
3  
4  
5  
6  

**TrdAssetCategory**

    enum TrdAssetCategory
    {
    	TrdAssetCategory_Unknown = 0; 	//未知
    	TrdAssetCategory_JP = 1;	    //本国
    	TrdAssetCategory_US = 2;	    //外国
    }
    

1  
2  
3  
4  
5  
6  

**TrdAssetCategory**

    enum TrdAssetCategory
    {
    	TrdAssetCategory_Unknown = 0; 	//未知
    	TrdAssetCategory_JP = 1;	    //本国
    	TrdAssetCategory_US = 2;	    //外国
    }
    

1  
2  
3  
4  
5  
6  

**TrdAssetCategory**

    enum TrdAssetCategory
    {
    	TrdAssetCategory_Unknown = 0; 	//未知
    	TrdAssetCategory_JP = 1;	    //本国
    	TrdAssetCategory_US = 2;	    //外国
    }
    

1  
2  
3  
4  
5  
6  

[#](./trade_trade.md#6504)
 交易品类
------------------------------------------------------------------------

**TrdCategory**

    enum TrdCategory
    {
        TrdCategory_Unknown = 0; //未知品类
        TrdCategory_Security = 1; //证券
        TrdCategory_Future = 2; //期货
    }
    

1  
2  
3  
4  
5  
6  

[#](./trade_trade.md#8386)
 账户现金信息
--------------------------------------------------------------------------

**AccCashInfo**

    message AccCashInfo
    {
        optional int32 currency = 1;        // 货币类型，取值参考 Currency
        optional double cash = 2;           // 现金结余
        optional double availableBalance = 3;   // 现金可提金额
        optional double netCashPower = 4;		// 现金购买力
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./trade_trade.md#2147)
 分市场资产信息
---------------------------------------------------------------------------

**AccMarketInfo**

    message AccCashInfo
    {
        optional int32 trdMarket = 1;        // 交易市场, 参见TrdMarket的枚举定义
        optional double assets = 2;          // 分市场资产信息
    }
    

1  
2  
3  
4  
5  

[#](./trade_trade.md#1138)
 交易协议公共参数头
-----------------------------------------------------------------------------

**TrdHeader**

    message TrdHeader
    {
      required int32 trdEnv = 1; //交易环境, 参见 TrdEnv 的枚举定义
      required uint64 accID = 2; //业务账号, 业务账号与交易环境、市场权限需要匹配，否则会返回错误
      required int32 trdMarket = 3; //交易市场, 参见 TrdMarket 的枚举定义
      optional int32 jpAccType = 4; //JP子账户类型，取值见 TrdSubAccType
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./trade_trade.md#8680)
 交易业务账户
--------------------------------------------------------------------------

**TrdAcc**

    message TrdAcc
    {
      required int32 trdEnv = 1; //交易环境，参见 TrdEnv 的枚举定义
      required uint64 accID = 2; //业务账号
      repeated int32 trdMarketAuthList = 3; //业务账户支持的交易市场权限，即此账户能交易那些市场, 可拥有多个交易市场权限，目前仅单个，取值参见 TrdMarket 的枚举定义
      optional int32 accType = 4;   //账户类型，取值见 TrdAccType
      optional string cardNum = 5;  //卡号
      optional int32 securityFirm = 6; //所属券商，取值见SecurityFirm
      optional int32 simAccType = 7; //模拟交易账号类型，取值见SimAccType
      optional string uniCardNum = 8;  //所属综合账户卡号
      optional int32 accStatus = 9; //账号状态，取值见TrdAccStatus
      optional int32 accRole = 10; //账号分类，是不是主账号，取值见TrdAccRole
      repeated int32 jpAccType = 11; //JP子账户类型，取值见 TrdSubAccType
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  

[#](./trade_trade.md#3175)
 账户资金
------------------------------------------------------------------------

**Funds**

    message Funds
    {
      required double power = 1; //最大购买力（此字段是按照 50% 的融资初始保证金率计算得到的 近似值。但事实上，每个标的的融资初始保证金率并不相同。我们建议您使用 查询最大可买可卖 接口返回的 最大可买 字段，来判断实际可买入的最大数量）
      required double totalAssets = 2; //资产净值
      required double cash = 3; //现金（仅单币种账户使用此字段，综合账户请使用 cashInfoList 获取分币种现金）
      required double marketVal = 4; //证券市值, 仅证券账户适用
      required double frozenCash = 5; //冻结资金
      required double debtCash = 6; //计息金额
      required double avlWithdrawalCash = 7; //现金可提（仅单币种账户使用此字段，综合账户请使用 cashInfoList 获取分币种现金可提）
    
      optional int32 currency = 8;            //币种，本结构体资金相关的货币类型，取值参见 Currency，期货和综合证券账户适用
      optional double availableFunds = 9;     //可用资金，期货适用
      optional double unrealizedPL = 10;      //未实现盈亏，期货适用
      optional double realizedPL = 11;        //已实现盈亏，期货适用
      optional int32 riskLevel = 12;           //风控状态，参见 CltRiskLevel, 期货适用。建议统一使用 riskStatus 字段获取证券、期货账户的风险状态
      optional double initialMargin = 13;      //初始保证金
      optional double maintenanceMargin = 14;  //维持保证金
      repeated AccCashInfo cashInfoList = 15;  //分币种的现金、现金可提和现金购买力（仅综合账户适用）
      optional double maxPowerShort = 16; //卖空购买力（此字段是按照 60% 的融券保证金率计算得到的近似值。但事实上，每个标的的融券保证金率并不相同。我们建议您使用 查询最大可买可卖 接口返回的 可卖空 字段，来判断实际可卖空的最大数量。）
      optional double netCashPower = 17;  //现金购买力（仅单币种账户使用此字段，综合账户请使用 cashInfoList 获取分币种现金购买力）
      optional double longMv = 18;        //多头市值
      optional double shortMv = 19;       //空头市值
      optional double pendingAsset = 20;  //在途资产
      optional double maxWithdrawal = 21;          //融资可提，仅证券账户适用
      optional int32 riskStatus = 22;              //风险状态，参见 CltRiskStatus，共分 9 个等级，LEVEL1是最安全，LEVEL9是最危险
      optional double marginCallMargin = 23;       //	Margin Call 保证金
    
      optional bool isPdt = 24;				//是否PDT账户，仅moomoo证券(美国)账户适用
      optional string pdtSeq = 25;			//剩余日内交易次数，仅被标记为 PDT 的moomoo证券(美国)账户适用
      optional double beginningDTBP = 26;		//初始日内交易购买力，仅被标记为 PDT 的moomoo证券(美国)账户适用
      optional double remainingDTBP = 27;		//剩余日内交易购买力，仅被标记为 PDT 的moomoo证券(美国)账户适用
      optional double dtCallAmount = 28;		//日内交易待缴金额，仅被标记为 PDT 的moomoo证券(美国)账户适用
      optional int32 dtStatus = 29;				//日内交易限制情况，取值见 DTStatus。仅被标记为 PDT 的moomoo证券(美国)账户适用
      
      optional double securitiesAssets = 30; // 证券资产净值
      optional double fundAssets = 31; // 基金资产净值
      optional double bondAssets = 32; // 债券资产净值
    
      repeated AccMarketInfo marketInfoList = 33; //分市场资产信息
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
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

[#](./trade_trade.md#3117)
 账户持仓
------------------------------------------------------------------------

**Position**

    message Position
    {
        required uint64 positionID = 1;     //持仓 ID，一条持仓的唯一标识
        required int32 positionSide = 2;    //持仓方向，参见 PositionSide 的枚举定义
        required string code = 3;           //代码
        required string name = 4;           //名称
        required double qty = 5;            //持有数量，2位精度，期权单位是"张"，下同
        required double canSellQty = 6;     //可用数量，是指持有的可平仓的数量。可用数量=持有数量-冻结数量。期权和期货的单位是“张”。
        required double price = 7;          //市价，3位精度，期货为2位精度
        optional double costPrice = 8;      //摊薄成本价（证券账户），平均开仓价（期货账户）。证券无精度限制，期货为2位精度，如果没传，代表此时此值无效
        required double val = 9;            //市值，3位精度, 期货此字段值为0
        required double plVal = 10;         //盈亏金额，3位精度，期货为2位精度
        optional double plRatio = 11;       //盈亏百分比(平均成本价模式)，无精度限制，如果没传，代表此时此值无效
        optional int32 secMarket = 12;      //证券所属市场，参见 TrdSecMarket 的枚举定义
        
    	//以下是此持仓今日统计
        optional double td_plVal = 21;      //今日盈亏金额，3位精度，下同, 期货为2位精度
        optional double td_trdVal = 22;     //今日交易额，期货不适用
        optional double td_buyVal = 23;     //今日买入总额，期货不适用
        optional double td_buyQty = 24;     //今日买入总量，期货不适用
        optional double td_sellVal = 25;    //今日卖出总额，期货不适用
        optional double td_sellQty = 26;    //今日卖出总量，期货不适用
    
        optional double unrealizedPL = 28;       //未实现盈亏（仅期货账户适用）
        optional double realizedPL = 29;         //已实现盈亏（仅期货账户适用）	
        optional int32 currency = 30;        // 货币类型，取值参考 Currency
        optional int32 trdMarket = 31;  //交易市场, 参见 TrdMarket 的枚举定义
    
        optional double dilutedCostPrice = 32;      //摊薄成本价，仅支持证券账户使用
        optional double averageCostPrice = 33;      //平均成本价，模拟交易证券账户不适用
        optional double averagePlRatio = 34;        //盈亏百分比(平均成本价模式)，无精度限制，如果没传，代表此时此值无效
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
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

[#](./trade_trade.md#1935)
 订单
----------------------------------------------------------------------

**Order**

    message Order
    {
        required int32 trdSide = 1; //交易方向, 参见 TrdSide 的枚举定义
        required int32 orderType = 2; //订单类型, 参见 OrderType 的枚举定义
        required int32 orderStatus = 3; //订单状态, 参见 OrderStatus 的枚举定义
        required uint64 orderID = 4; //订单号
        required string orderIDEx = 5; //扩展订单号(仅查问题时备用)
        required string code = 6; //代码
        required string name = 7; //名称
        required double qty = 8; //订单数量，2位精度，期权单位是"张"
        optional double price = 9; //订单价格，3位精度
        required string createTime = 10; //创建时间，严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传
        required string updateTime = 11; //最后更新时间，严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传
        optional double fillQty = 12; //成交数量，2位精度，期权单位是"张"
        optional double fillAvgPrice = 13; //成交均价，无精度限制
        optional string lastErrMsg = 14; //最后的错误描述，如果有错误，会有此描述最后一次错误的原因，无错误为空
        optional int32 secMarket = 15; //证券所属市场，参见 TrdSecMarket 的枚举定义
        optional double createTimestamp = 16; //创建时间戳
        optional double updateTimestamp = 17; //最后更新时间戳
        optional string remark = 18; //用户备注字符串，最大长度64字节
        optional double auxPrice = 21; //触发价格
        optional int32 trailType = 22; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
        optional double trailValue = 23; //跟踪金额/百分比
        optional double trailSpread = 24; //指定价差
        optional int32 currency = 25;        // 货币类型，取值参考 Currency
        optional int32 trdMarket = 26;  //交易市场, 参见TrdMarket的枚举定义
        optional int32 session = 27; //美股订单时段, 参见Common.Session的枚举定义
        optional int32 jpAccType = 28; //JP子账户类型，取值见 TrdSubAccType
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
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

[#](./trade_trade.md#627)
 订单费用条目
-------------------------------------------------------------------------

**OrderFeeItem**

    message OrderFeeItem
    {
        optional string title = 1; //费用名字
        optional double value = 2; //费用金额
    }
    

1  
2  
3  
4  
5  

[#](./trade_trade.md#1085)
 订单费用
------------------------------------------------------------------------

**OrderFee**

    message OrderFee
    {
        required string orderIDEx = 1; //扩展订单号
        optional double feeAmount = 2; //费用总额
        repeated OrderFeeItem feeList = 3; //费用明细
    }
    

1  
2  
3  
4  
5  
6  

[#](./trade_trade.md#1253)
 成交
----------------------------------------------------------------------

**OrderFill**

    message OrderFill
    {
    	required int32 trdSide = 1; //交易方向, 参见 TrdSide 的枚举定义
        required uint64 fillID = 2; //成交号
        required string fillIDEx = 3; //扩展成交号(仅查问题时备用)
        optional uint64 orderID = 4; //订单号
        optional string orderIDEx = 5; //扩展订单号(仅查问题时备用)
        required string code = 6; //代码
        required string name = 7; //名称
        required double qty = 8; //成交数量，2位精度，期权单位是"张"
        required double price = 9; //成交价格，3位精度
        required string createTime = 10; //创建时间（成交时间），严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传
        optional int32 counterBrokerID = 11; //对手经纪号，港股有效
        optional string counterBrokerName = 12; //对手经纪名称，港股有效
        optional int32 secMarket = 13; //证券所属市场，参见 TrdSecMarket 的枚举定义
        optional double createTimestamp = 14; //创建时间戳
        optional double updateTimestamp = 15; //最后更新时间戳
        optional int32 status = 16; //成交状态, 参见 OrderFillStatus 的枚举定义
        optional int32 trdMarket = 17;  //交易市场, 参见TrdMarket的枚举定义
        optional int32 jpAccType = 18; //JP子账户类型，取值见 TrdSubAccType
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  

[#](./trade_trade.md#8065)
 最大可交易数量
---------------------------------------------------------------------------

**MaxTrdQtys**

    message MaxTrdQtys
    {
    	//因目前服务器实现的问题，卖空需要先卖掉多头持仓才能再卖空，是分开两步卖的，买回来同样是逆向两步；而看多的买是可以现金加融资一起一步买的，请注意这个差异
    	required double maxCashBuy = 1;             //现金可买（期权的单位是“张”，期货账户不适用）
        optional double maxCashAndMarginBuy = 2;    //最大可买（期权的单位是“张”，期货账户不适用）
        required double maxPositionSell = 3;        //持仓可卖（期权的单位是“张”）
        optional double maxSellShort = 4;           //可卖空（期权的单位是“张”，期货账户不适用）
        optional double maxBuyBack = 5;             //平仓需买入（当持有净空仓时，必须先买回空头持仓的股数，才能再继续买多。期货、期权的单位是“张”）
        optional double longRequiredIM = 6;         //买 1 张合约所带来的初始保证金变动。仅期货和期权适用。无持仓时，返回 买入 1 张的初始保证金占用（正数）。有多仓时，返回 买入1 张的初始保证金占用（正数）。有空仓时，返回 买回 1 张的初始保证金释放（负数）。
        optional double shortRequiredIM = 7;        //卖 1 张合约所带来的初始保证金变动。仅期货和期权适用。无持仓时，返回 卖空 1 张的初始保证金占用（正数）。 有多仓时，返回卖出1 张的初始保证金占用（正数）。有空仓时，返回 卖空1 张的初始保证金释放（正数）。
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

[#](./trade_trade.md#9378)
 现金流水数据
--------------------------------------------------------------------------

**FlowSummaryInfo**

    message FlowSummaryInfo
    {
    	optional string clearingDate = 1; //清算日期
    	optional string settlementDate = 2; //结算日期
    	optional int32 currency = 3; //币种
    	optional string cashFlowType = 4; //现金流类型
    	optional int32 cashFlowDirection = 5; //现金流方向 TrdCashFlowDirection
    	optional double cashFlowAmount = 6; //金额
    	optional string cashFlowRemark = 7; //备注
    	optional uint64 cashFlowID = 8; //现金流 ID
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

[#](./trade_trade.md#3894)
 过滤条件
------------------------------------------------------------------------

**TrdFilterConditions**

    message TrdFilterConditions
    {
      repeated string codeList = 1; //代码过滤，只返回包含这些代码的数据，没传不过滤
      repeated uint64 idList = 2; //ID 主键过滤，只返回包含这些 ID 的数据，没传不过滤，订单是 orderID、成交是 fillID、持仓是 positionID
      optional string beginTime = 3; //开始时间，严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传，对持仓无效，拉历史数据必须填
      optional string endTime = 4; //结束时间，严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传，对持仓无效，拉历史数据必须填
      repeated string orderIDExList = 5; // 服务器订单ID列表，可以用来替代orderID列表，二选一
      optional int32 filterMarket = 6; //指定交易市场, 参见TrdMarket的枚举定义
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

← [响应成交推送回调](./trade_update-order-fill.md) [基础功能](./ftapi_init.md)
 →

[交易定义](./trade_trade.md)