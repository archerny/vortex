 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/qa/trade.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/qa/trade.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/qa/trade.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/qa/trade.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/qa/trade.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/qa/trade.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
*   交易接口
    
*   基础接口
    
*   Q&A
    
    *   [OpenD 相关](https://openapi.futunn.com/futu-api-doc/qa/opend.html)
        
    *   [行情相关](https://openapi.futunn.com/futu-api-doc/qa/quote.html)
        
    *   [交易相关](https://openapi.futunn.com/futu-api-doc/qa/trade.html)
        
    *   [其他](https://openapi.futunn.com/futu-api-doc/qa/other.html)
        
    

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#6219)
 交易相关
=====================================================================

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#1690)
 Q1：模拟交易相关
--------------------------------------------------------------------------

A:

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#8831)
 概述

模拟交易是在真实的市场环境中，用虚拟资金做交易，不会对您的真实账户的资产造成影响。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#5537)
 交易时间

模拟交易支持在常规交易时段交易，支持美股盘中交易时段、美股盘前盘后时段，不支持美股夜盘、全时段交易和A股港股盘前盘后竞价时段交易。详情可点击 [模拟交易规则](https://support.futunn.com/topic692)
 。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#9939)
 支持品类

Futu API 支持模拟交易的品类请参考 [这里](https://openapi.futunn.com/futu-api-doc/intro/intro.html#1396)
。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#9555)
 解锁

与真实交易不同，模拟交易无需对账户进行解锁，即可下单或改单撤单。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#1935)
 订单

1.  订单类型：限价单和市价单。
2.  改单操作类型：模拟交易不支持使生效、使失效、删除，仅支持修改订单、 撤单。
3.  成交：模拟交易不支持成交相关操作，包括 [查询今日成交](https://openapi.futunn.com/futu-api-doc/trade/get-order-fill-list.html#2621)
    、[查询历史成交](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html#9015)
    、[响应成交推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html#210)
    。
4.  有效期限：模拟交易有效期限仅支持当日有效。
5.  卖空：期权和期货支持卖空。股票仅美股支持卖空。
6.  模拟交易账户不支持查询订单费用。
7.  模拟交易账户不支持查询现金流水。
8.  在组合期权订单场景下，支持持仓查询，暂不支持组合订单查询。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#7877)
 操作平台

1.  移动端：我的 — 模拟交易

![sim-page](https://openapi.futunn.com/futu-api-doc/assets/img/sim-page.7b1c5aae.png)

2.  桌面端：左侧模拟 tab

![sim-page](https://openapi.futunn.com/futu-api-doc/assets/img/create-sim-account.68055113.png)

3.  网页端：[模拟交易界面](https://m-match.futunn.com/simulate/)
    
4.  Futu API：在调用接口时，设置参数交易环境为模拟环境即可。详见 [如何使用 Futu API 进行模拟交易](https://openapi.futunn.com/futu-api-doc/qa/trade.html#8728)
    。
    

提示

*   以上四种方式只是操作平台不同，四种方式操作的模拟账户是共通的。

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#4767)
 如何使用 Futu API 进行模拟交易？

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#7902)
 创建连接

先根据交易品种 [创建相应的连接](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902)
 。当交易品种是股票或期权时，请使用 `OpenSecTradeContext`。当交易品种是期货时，请使用 `OpenFutureTradeContext`。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#5754)
 获取交易业务账户列表

使用 [获取交易业务账户列表](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html#5754)
 查看交易账户（包括模拟账户、真实账户）。以 Python 为例：返回字段交易环境 `trd_env` 为 `SIMULATE`，表示模拟账户。  
获取港股模拟交易账户，需要指定 filter\_trdmarket 为 TrdMarket.HK，此时会返回2个模拟交易账号。其中 sim\_acc\_type = STOCK 为港股模拟账户，sim\_acc\_type = OPTION 为港股期权模拟账户，sim\_acc\_type = FUTURES 为港股期货模拟账户。  
获取美股模拟交易账户，需要指定 filter\_trdmarket 为 TrdMarket.US，sim\_acc\_type = STOCK\_AND\_OPTION 代表美股融资融券模拟账户，可以模拟交易股票和期权。sim\_acc\_type = FUTURES 为美国期货模拟账户。

*   **Example：Stocks and Options**

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    #trd_ctx = OpenFutureTradeContext(host='127.0.0.1', port=11111, is_encrypt=None, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.get_acc_list()
    if ret == RET_OK:
        print(data)
        print(data['acc_id'][0])  # get the first account id
        print(data['acc_id'].values.tolist())  # convert to list format
    else:
        print('get_acc_list error: ', data)
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

                   acc_id   trd_env acc_type          card_num   security_firm  \
    0  281756480572583411      REAL   MARGIN  1001318721909873  FUTUSECURITIES   
    1             9053218  SIMULATE     CASH               N/A             N/A   
    2             9048221  SIMULATE   MARGIN               N/A             N/A   
    
      sim_acc_type  trdmarket_auth  
    0          N/A  [HK, US, HKCC]  
    1        STOCK            [HK]  
    2       OPTION            [HK] 
    

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

*   模拟交易中，区分股票账户和期权账户，股票账户只能交易股票，期权账户只能交易期权；以 Python 为例：返回字段中模拟账户类型 `sim_acc_type` 为 `STOCK`，表示股票账户；为`OPTION`，表示期权账户。

*   **Example: Futures**

    from futu import *
    #trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    trd_ctx = OpenFutureTradeContext(host='127.0.0.1', port=11111, is_encrypt=None, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.get_acc_list()
    if ret == RET_OK:
        print(data)
        print(data['acc_id'][0])  # get the first account id
        print(data['acc_id'].values.tolist())  # convert to list format
    else:
        print('get_acc_list error: ', data)
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

        acc_id   trd_env acc_type card_num security_firm sim_acc_type  \
    0  9497808  SIMULATE   MARGIN      N/A           N/A      FUTURES   
    1  9497809  SIMULATE   MARGIN      N/A           N/A      FUTURES   
    2  9497810  SIMULATE   MARGIN      N/A           N/A      FUTURES   
    3  9497811  SIMULATE   MARGIN      N/A           N/A      FUTURES   
    
              trdmarket_auth  
    0  [FUTURES_SIMULATE_HK]  
    1  [FUTURES_SIMULATE_US]  
    2  [FUTURES_SIMULATE_SG]  
    3  [FUTURES_SIMULATE_JP]  
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#4080)
 下单

使用 [下单接口](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)
 时，设置交易环境为模拟环境即可。以 Python 为例：`trd_env = TrdEnv.SIMULATE`。

*   **Example**

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.place_order(price=510.0, qty=100, code="HK.00700", trd_side=TrdSide.BUY, trd_env=TrdEnv.SIMULATE)
    if ret == RET_OK:
        print(data)
    else:
        print('place_order error: ', data)
    trd_ctx.close()
    

1  
2  
3  
4  
5  
6  
7  
8  

*   **Output**

    	code	stock_name	trd_side	order_type	order_status	order_id	qty	price	create_time	updated_time	dealt_qty	dealt_avg_price	last_err_msg	remark	time_in_force	fill_outside_rth
    0	HK.00700	腾讯控股	BUY	NORMAL	SUBMITTING	4642000476506964749	100.0	510.0	2021-10-09 11:34:54	2021-10-09 11:34:54	0.0	0.0			DAY	N/A
    

1  
2  

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#8313)
 撤单改单

使用 [撤单接口](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html)
 时，设置交易环境为模拟环境即可。以 Python 为例： `trd_env = TrdEnv.SIMULATE`。

*   **Example**

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    order_id = "4642000476506964749"
    ret, data = trd_ctx.modify_order(ModifyOrderOp.CANCEL, order_id, 0, 0, trd_env=TrdEnv.SIMULATE)
    if ret == RET_OK:
        print(data)
    else:
        print('modify_order error: ', data)
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

*   **Output**

        trd_env             order_id
    0  SIMULATE  4642000476506964749
    

1  
2  

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#3352)
 查询历史订单

使用 [查询历史订单接口](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-list.html)
 时，设置交易环境为模拟环境即可。以 Python 为例：`trd_env = TrdEnv.SIMULATE`。

*   **Example**

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.history_order_list_query(trd_env=TrdEnv.SIMULATE)
    if ret == RET_OK:
        print(data)
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

*   **Output**

    	code	stock_name	trd_side	order_type	order_status	order_id	qty	price	create_time	updated_time	dealt_qty	dealt_avg_price	last_err_msg	remark	time_in_force	fill_outside_rth
    0	HK.00700	腾讯控股	BUY	ABSOLUTE_LIMIT	CANCELLED_ALL	4642000476506964749	100.0	510.0	2021-10-09 11:34:54	2021-10-09 11:37:08	0.0	0.0			DAY	N/A
    

1  
2  

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#4373)
 如何重置模拟账户？

目前 Futu API 不支持重置模拟账户，您可在移动端使用复活卡重置指定模拟账户，重置后账户资金将恢复至初始值，历史订单将会被清空。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#1443)
 具体操作

移动端：我的 — 模拟交易 — 我的头像 — 我的道具 — 复活卡。 ![sim-page](https://openapi.futunn.com/futu-api-doc/assets/img/sim-reset.28efd21a.png)

A:

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#8831-2)
 概述

模拟交易是在真实的市场环境中，用虚拟资金做交易，不会对您的真实账户的资产造成影响。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#5537-2)
 交易时间

模拟交易仅支持在常规交易时段交易，不支持在非交易时段、美股盘前盘后时段、A股港股盘前盘后竞价时段交易。详情可点击 [模拟交易规则](https://support.moomoo.com/topic5_689?lang=zh-cn)
 。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#9939-2)
 支持品类

Moomoo API 支持模拟交易的品类请参考 [这里](https://openapi.futunn.com/futu-api-doc/intro/intro.html#1396)
。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#1935-2)
 订单

1.  订单类型：限价单和市价单。
2.  改单操作类型：模拟交易不支持使生效、使失效、删除，仅支持支持修改订单、 撤单。
3.  成交：模拟交易不支持成交相关操作，包括 [查询今日成交](https://openapi.futunn.com/futu-api-doc/trade/get-order-fill-list.html#2621)
    、[查询历史成交](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html#9015)
    、[响应成交推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html#210)
    。
4.  有效期限：模拟交易有效期限仅支持当日有效。
5.  卖空：期权和期货支持卖空。股票仅美股支持卖空。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#7877-2)
 操作平台

1.  移动端：我的 — 模拟交易

![sim-page](https://openapi.futunn.com/futu-api-doc/assets/img/sim-page.7b1c5aae.png)

2.  桌面端：左侧模拟 tab

![sim-page](https://openapi.futunn.com/futu-api-doc/assets/img/create-sim-account.68055113.png)

3.  网页端：[模拟交易界面](https://m-match.moomoo.com/simulate/)
    
4.  Moomoo API：在调用接口时，设置参数交易环境为模拟环境即可。详见 [如何使用 Moomoo API 进行模拟交易](https://openapi.futunn.com/futu-api-doc/qa/trade.html#8728-2)
    。
    

提示

*   以上四种方式只是操作平台不同，四种方式操作的模拟账户是共通的。

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#9443)
 如何使用 Moomoo API 进行模拟交易？

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#7902-2)
 创建连接

先根据交易品种 [创建相应的连接](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902)
 。当交易品种是股票或期权时，请使用 `OpenSecTradeContext`。当交易品种是期货时，请使用 `OpenFutureTradeContext`。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#5754-2)
 获取交易业务账户列表

使用 [获取交易业务账户列表](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html#5754)
 查看交易账户（包括模拟账户、真实账户）。以 Python 为例：返回字段交易环境 `trd_env` 为 `SIMULATE`，表示模拟账户。

*   **Example: Stocks and Options**

    from moomoo import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    #trd_ctx = OpenFutureTradeContext(host='127.0.0.1', port=11111, is_encrypt=None, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.get_acc_list()
    if ret == RET_OK:
        print(data)
        print(data['acc_id'][0])  # get the first account id
        print(data['acc_id'].values.tolist())  # convert to list format
    else:
        print('get_acc_list error: ', data)
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

                   acc_id   trd_env acc_type          card_num   security_firm  \
    0  281756480572583411      REAL   MARGIN  1001318721909873  FUTUSECURITIES   
    1             9053218  SIMULATE     CASH               N/A             N/A   
    2             9048221  SIMULATE   MARGIN               N/A             N/A   
    
      sim_acc_type  trdmarket_auth  
    0          N/A  [HK, US, HKCC]  
    1        STOCK            [HK]  
    2       OPTION            [HK] 
    

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

*   模拟交易中，区分股票账户和期权账户，股票账户只能交易股票，期权账户只能交易期权；以 Python 为例：返回字段中模拟账户类型 `sim_acc_type` 为 `STOCK`，表示股票账户；为`OPTION`，表示期权账户。

*   **Example: Futures**

    from moomoo import *
    #trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    trd_ctx = OpenFutureTradeContext(host='127.0.0.1', port=11111, is_encrypt=None, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.get_acc_list()
    if ret == RET_OK:
        print(data)
        print(data['acc_id'][0])  # get the first account id
        print(data['acc_id'].values.tolist())  # convert to list format
    else:
        print('get_acc_list error: ', data)
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

        acc_id   trd_env acc_type card_num security_firm sim_acc_type  \
    0  9497808  SIMULATE   MARGIN      N/A           N/A      FUTURES   
    1  9497809  SIMULATE   MARGIN      N/A           N/A      FUTURES   
    2  9497810  SIMULATE   MARGIN      N/A           N/A      FUTURES   
    3  9497811  SIMULATE   MARGIN      N/A           N/A      FUTURES   
    
              trdmarket_auth  
    0  [FUTURES_SIMULATE_HK]  
    1  [FUTURES_SIMULATE_US]  
    2  [FUTURES_SIMULATE_SG]  
    3  [FUTURES_SIMULATE_JP]  
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#4080-2)
 下单

使用 [下单接口](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)
 时，设置交易环境为模拟环境即可。以 Python 为例：`trd_env = TrdEnv.SIMULATE`。

*   **Example**

    from moomoo import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.place_order(price=510.0, qty=100, code="HK.00700", trd_side=TrdSide.BUY, trd_env=TrdEnv.SIMULATE)
    if ret == RET_OK:
        print(data)
    else:
        print('place_order error: ', data)
    trd_ctx.close()
    

1  
2  
3  
4  
5  
6  
7  
8  

*   **Output**

    	code	stock_name	trd_side	order_type	order_status	order_id	qty	price	create_time	updated_time	dealt_qty	dealt_avg_price	last_err_msg	remark	time_in_force	fill_outside_rth
    0	HK.00700	腾讯控股	BUY	NORMAL	SUBMITTING	4642000476506964749	100.0	510.0	2021-10-09 11:34:54	2021-10-09 11:34:54	0.0	0.0			DAY	N/A
    

1  
2  

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#8313-2)
 撤单改单

使用 [撤单接口](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html)
 时，设置交易环境为模拟环境即可。以 Python 为例： `trd_env = TrdEnv.SIMULATE`。

*   **Example**

    from moomoo import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    order_id = "4642000476506964749"
    ret, data = trd_ctx.modify_order(ModifyOrderOp.CANCEL, order_id, 0, 0, trd_env=TrdEnv.SIMULATE)
    if ret == RET_OK:
        print(data)
    else:
        print('modify_order error: ', data)
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

*   **Output**

        trd_env             order_id
    0  SIMULATE  4642000476506964749
    

1  
2  

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#3352-2)
 查询历史订单

使用 [查询历史订单接口](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-list.html)
 时，设置交易环境为模拟环境即可。以 Python 为例：`trd_env = TrdEnv.SIMULATE`。

*   **Example**

    from moomoo import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.history_order_list_query(trd_env=TrdEnv.SIMULATE)
    if ret == RET_OK:
        print(data)
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

*   **Output**

    	code	stock_name	trd_side	order_type	order_status	order_id	qty	price	create_time	updated_time	dealt_qty	dealt_avg_price	last_err_msg	remark	time_in_force	fill_outside_rth
    0	HK.00700	腾讯控股	BUY	ABSOLUTE_LIMIT	CANCELLED_ALL	4642000476506964749	100.0	510.0	2021-10-09 11:34:54	2021-10-09 11:37:08	0.0	0.0			DAY	N/A
    

1  
2  

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#4373-2)
 如何重置模拟账户？

目前 Moomoo API 不支持重置模拟账户，您可在移动端使用复活卡重置指定模拟账户，重置后账户资金将恢复至初始值，历史订单将会被清空。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#1443-2)
 具体操作

移动端：我的 — 模拟交易 — 我的头像 — 我的道具 — 复活卡。 ![sim-page](https://openapi.futunn.com/futu-api-doc/assets/img/sim-reset.28efd21a.png)

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#830)
 Q2：是否支持 A 股交易？
------------------------------------------------------------------------------

A: 模拟交易支持 A 股交易。但真实交易仅可通过 A 股通交易部分 A 股，具体详见 [A 股通名单](https://www.hkex.com.hk/Mutual-Market/Stock-Connect/Eligible-Stocks/View-All-Eligible-Securities?sc_lang=zh-HK)
 。

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#3828)
 Q3：各市场支持的交易方向
------------------------------------------------------------------------------

A: 除了期货，其他股票都只支持传入 BUY 和 SELL 两个交易方向。在空仓情况下传入 SELL，产生的订单交易方向是卖空。

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2731)
 Q4：真实交易中，各市场支持的订单类型
------------------------------------------------------------------------------------

A:

| 市场  | 品种  | 限价单 | 市价单 | 竞价限价单 | 竞价市价单 | 绝对限价单 | 特别限价单 | 特别限价且要求  <br>全部成交订单 | 止损市价单 | 止损限价单 | 触及市价单（止盈） | 触及限价单（止盈） | 跟踪止损市价单 | 跟踪止损限价单 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 香港市场 | 证券类产品（含股票、ETFs、  <br>窝轮、牛熊、界内证） | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| 期权  | ✓   | X   | \-  | \-  | \-  | \-  | \-  | X   | ✓   | X   | ✓   | X   | ✓   |
| 期货  | ✓   | ✓   | \-  | ✓   | \-  | \-  | \-  | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| 美国市场 | 证券类产品（含股票、ETFs） | ✓   | ✓   | \-  | \-  | \-  | \-  | \-  | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| 期权  | ✓   | ✓   | \-  | \-  | \-  | \-  | \-  | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| 期货  | ✓   | ✓   | \-  | \-  | \-  | \-  | \-  | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| A 股通市场 | 证券类产品（含股票、ETFs） | ✓   | X   | \-  | \-  | \-  | \-  | \-  | X   | ✓   | X   | ✓   | X   | ✓   |
| 新加坡市场 | 期货  | ✓   | ✓   | \-  | \-  | \-  | \-  | \-  | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| 日本市场 | 期货  | ✓   | ✓   | \-  | \-  | \-  | \-  | \-  | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2239)
 Q5：各市场支持的订单操作
------------------------------------------------------------------------------

A:

*   港股支持改单、撤单、生效、失效、删除
*   美股仅支持改单和撤单
*   A 股通仅支持撤单
*   期货支持改单、撤单、删除

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#9274)
 Q6：OpenD 启动参数 future\_trade\_api\_time\_zone 如何使用？
-------------------------------------------------------------------------------------------------------------------

A：由于期货账户支持交易的品种分布在全球多个交易所，交易所的所属时区各有不同，因此期货交易 API 的时间显示就成为了一个问题。  
OpenD 启动参数中新增了 future\_trade\_api\_time\_zone 这一参数，供全球不同地区的期货交易者灵活指定时区。默认时区为 UTC+8，如果您更习惯美东时间，只需将此参数配置为 UTC-5 即可。

提示

*   此参数仅会对期货交易接口类对象生效。港股交易、美股交易、A 股通交易接口类对象的时区，仍然按照交易所所在的时区进行显示。
*   此参数会影响的接口包括：响应订单推送回调，响应成交推送回调，查询今日订单，查询历史订单，查询当日成交，查询历史成交，下单。

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#7945)
 Q7：通过 API 下的订单，能在 APP 上面看到吗？
---------------------------------------------------------------------------------------------

A：可以看到。  

通过 Futu API 成功发出下单指令后，您可以在 APP 的 **交易** 页面，查看今日订单、订单状态、成交情况等等，也可以在 **消息—订单消息** 中收到成交提醒的通知。

通过 Moomoo API 成功发出下单指令后，您可以在 APP 的 **交易** 页面，查看今日订单、订单状态、成交情况等等，也可以在 **消息—订单消息** 中收到成交提醒的通知。

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#4721)
 Q8：哪些品类支持在非交易时段下单？
-----------------------------------------------------------------------------------

A：所有的订单，都需要在开盘期间才能够成交。  

Futu API 仅对一部分品类，支持了 **非交易时段下单** 的功能（APP 上支持更多品类的非交易时段下单功能）。具体请参考下表：

Moomoo API 仅对一部分品类，支持了 **非交易时段下单** 的功能（APP 上支持更多品类的非交易时段下单功能）。具体请参考下表：

| 市场  | 标的类型 | 模拟交易 | 真实交易 |     |     |     |     |     |     |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Futu HK | Moomoo US | Moomoo SG | Moomoo AU | Moomoo MY | Moomoo CA | Moomoo JP |
| 香港市场 | 股票、ETFs、窝轮、牛熊、界内证 | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | X   | X   |
| 期权<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>含指数期权，需使用期货账户交易 | ✓   | ✓   | X   | X   | X   | X   | X   | X   |
| 期货  | ✓   | ✓   | X   | X   | X   | X   | X   | X   |
| 美国市场 | 股票、ETFs | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| 期权  | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| 期货  | ✓   | ✓   | X   | ✓   | X   | ✓   | X   | X   |
| A 股市场 | A 股通股票 | ✓   | ✓   | ✓   | ✓   | X   | X   | X   | X   |
| 非 A 股通股票 | ✓   | X   | X   | X   | X   | X   | X   | X   |
| 新加坡市场 | 股票、ETFs、窝轮、REITs、DLCs | X   | X   | X   | X   | X   | X   | X   | X   |
| 期货  | ✓   | ✓   | X   | ✓   | X   | X   | X   | X   |
| 日本市场 | 股票、ETFs、REITs | X   | X   | X   | X   | X   | X   | X   | X   |
| 期货  | ✓   | ✓   | X   | X   | X   | X   | X   | X   |
| 澳大利亚市场 | 股票、ETFs | X   | X   | X   | X   | X   | X   | X   | X   |
| 加拿大市场 | 股票  | X   | X   | X   | X   | X   | X   | X   | X   |

::: tip 提示 - ✓：支持非交易时段下单 - X：暂不支持非交易时段下单（或暂不支持交易） :::

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2984)
 Q9：对于下单接口，各订单类型对应的必传参数以及券商对单笔订单的下单限制
-----------------------------------------------------------------------------------------------------

A1: 各订单类型对应的必传参数

| 参数  | 限价单 | 市价单 | 竞价限价单 | 竞价市价单 | 绝对限价单 | 特别限价单 | 特别限价且要求  <br>全部成交订单 | 止损市价单 | 止损限价单 | 触及市价单（止盈） | 触及限价单（止盈） | 跟踪止损市价单 | 跟踪止损限价单 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| price | ✓   |     | ✓   |     | ✓   | ✓   | ✓   |     | ✓   |     | ✓   |     |     |
| qty | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| code | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| trd\_side | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| order\_type | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| trd\_env | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| aux\_price |     |     |     |     |     |     |     | ✓   | ✓   | ✓   | ✓   |     |     |
| trail\_type |     |     |     |     |     |     |     |     |     |     |     | ✓   | ✓   |
| trail\_value |     |     |     |     |     |     |     |     |     |     |     | ✓   | ✓   |
| trail\_spread |     |     |     |     |     |     |     |     |     |     |     |     | ✓   |

`Python 用户` 注意，[place\_order](https://openapi.futunn.com/futu-api-doc/trade/place-order.html#4080)
 并未对 price 设置默认值，对于上述五类订单类型，仍需对 price 传参，price 可以传入任意值。

A2：各券商对单笔订单的股数及金额限制

| 券商  | 品类  | 单笔订单股数上限 | 单笔订单金额上限 |
| --- | --- | --- | --- |
| FUTU HK | A股通 | 1,000,000 股 | ￥5,000,000 |
| 美股  | 500,000 股 | $5,000,000 |
| 香港股票期货/期权 | 3,000 手 | 无限制 |
| moomoo US | 美股  | 500,000 股 | $10,000,000 |
| moomoo SG | 美股  | 500,000 股 | $5,000,000 |
| moomoo AU | 美股  | 无限制 | 无限制 |

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#689)
 Q10：对于改单接口，修改订单时，各订单类型对应的必传参数
---------------------------------------------------------------------------------------------

A:

| 参数  | 限价单 | 市价单 | 竞价限价单 | 竞价市价单 | 绝对限价单 | 特别限价单 | 特别限价且要求  <br>全部成交订单 | 止损市价单 | 止损限价单 | 触及市价单（止盈） | 触及限价单（止盈） | 跟踪止损市价单 | 跟踪止损限价单 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| modify\_order\_op | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| order\_id | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| price | ✓   |     | ✓   |     | ✓   | ✓   | ✓   |     | ✓   |     | ✓   |     |     |
| qty | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| trd\_env | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |
| aux\_price |     |     |     |     |     |     |     | ✓   | ✓   | ✓   | ✓   |     |     |
| trail\_type |     |     |     |     |     |     |     |     |     |     |     | ✓   | ✓   |
| trail\_value |     |     |     |     |     |     |     |     |     |     |     | ✓   | ✓   |
| trail\_spread |     |     |     |     |     |     |     |     |     |     |     |     | ✓   |

`Python 用户` 注意，[modify\_order](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html#7408)
 并未对 price 设置默认值，对于上述五类订单类型，仍需对 price 传参，price 可以传入任意值。

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#4378)
 Q11：交易接口返回“当前证券业务账户尚未同意免责协议”？
----------------------------------------------------------------------------------------------

A：  
点击下方链接完成协议确认，重启 OpenD 即可正常使用交易功能。

| 所属券商 | 协议确认 |
| --- | --- |
| FUTU HK | [点击这里](https://risk-disclosure.futuhk.com/index?agreementNo=HKOT0015) |
| Moomoo US | [点击这里](https://risk-disclosure.us.moomoo.com/index?agreementNo=USOT0027) |
| Moomoo SG | [点击这里](https://risk-disclosure.sg.moomoo.com/index?agreementNo=SGOT0015) |
| Moomoo AU | [点击这里](https://risk-disclosure.au.moomoo.com/index?agreementNo=AUOT0025) |
| Moomoo CA | [点击这里](https://risk-disclosure.ca.moomoo.com/index?agreementNo=CAOT0117) |
| Moomoo MY | [点击这里](https://risk-disclosure.my.moomoo.com/index?agreementNo=MYOT0066) |
| Moomoo JP | [点击这里](https://risk-disclosure.jp.moomoo.com/index?agreementNo=JPOT0140) |

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#9716)
 Q12：典型日内交易者（PDT）相关
-----------------------------------------------------------------------------------

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#8831-3)
 概述

客户使用moomoo证券(美国) 账户进行日内交易时，会受到美国 FINRA 的监管限制（此为美国券商受到的监管要求，与交易股票的所属市场无关。其他国家或地区的券商

![](https://openapi.futunn.com/futu-api-doc/img/tip.png)

如：富途证券(香港)、moomoo证券(新加坡)

的交易账户则不受此限制）。若用户在任意连续的5个交易日内，进行日内交易 3 次以上，则会被标记为典型日内交易者（PDT）。  
更多详情，[点击这里](https://fastsupport.fututrade.com/hans/category11014/scid11017)

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#6374)
 进行日内交易的流程图

![PDT_process](https://openapi.futunn.com/futu-api-doc/assets/img/PDT_process.48289b71.png)

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2782)
 我愿意被标记为 PDT，且不希望程式交易被打断，如何关闭“防止被标记为 PDT”？

A：  
当您在连续的 5 个交易日内，进行第 4 次日内交易时，为了防止您被无意识地标记为 PDT，服务器会对此交易进行拦截。若您主动想被标记为 PDT，并且不希望服务器拦截，可以采取以下措施：  
在 [命令行 OpenD 中配置参数](https://openapi.futunn.com/futu-api-doc/opend/opend-cmd.html#8799)
，将启动参数 `pdt_protection` 的值修改为 0，以关闭“防止被标记为日内交易者”的功能。

![US_para](https://openapi.futunn.com/futu-api-doc/assets/img/US_para.cb012764.png)  
注意：若您被标记 PDT，当您的账户权益小于$25000时，您将无法开仓。

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#5881)
 如何关闭 DTCall 预警提醒？

A：  
您被标记为 PDT 后，需要留意账户的日内交易购买力（DTBP），日内交易超出 DTBP 时将收到日内交易保证金追缴（DTCall）。服务器会在您即将开仓下单超出剩余日内交易购买力前，阻止您的下单。若您仍然希望进行下单，并且不希望服务器拦截，可以采取以下措施：  
在 [命令行 OpenD 中配置参数](https://openapi.futunn.com/futu-api-doc/opend/opend-cmd.html#8799)
，将启动参数 `dtcall_confirmation` 的值修改为 0，以关闭“日内交易保证金追缴预警”的功能。

![US_para2](https://openapi.futunn.com/futu-api-doc/assets/img/US_para2.01f6f10b.png)  
注意：若您开仓订单的市值大于您的剩余日内交易购买力，并且在今日平仓当前标的，您将会收到日内交易保证金追缴通知（Day-Trading Call），只能通过存入资金才能解除。

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#5032)
 如何查看 DTBP 的值？

A：  
通过 [查询账户资金](https://openapi.futunn.com/futu-api-doc/trade/get-funds.html#4346)
 接口，可以获取日内交易相关的返回值，如：剩余日内交易次数、初始日内交易购买力、剩余日内交易购买力等。

客户使用moomoo证券(美国) 账户进行日内交易时，会受到美国 FINRA 的监管限制（此为美国券商受到的监管要求，与交易股票的所属市场无关。其他国家或地区的券商

![](https://openapi.futunn.com/futu-api-doc/img/tip.png)

如：富途证券(香港)、moomoo证券(新加坡)

的交易账户则不受此限制）。若用户在任意连续的5个交易日内，进行日内交易 3 次以上，则会被标记为典型日内交易者（PDT）。  
更多详情，[点击这里](https://www.moomoo.com/us/hans/support/topic4_5?=zh-cn)

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#6374-2)
 进行日内交易的流程图

![PDT_process](https://openapi.futunn.com/futu-api-doc/assets/img/PDT_process.48289b71.png)

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2782-2)
 我愿意被标记为 PDT，且不希望程式交易被打断，如何关闭“防止被标记为 PDT”？

A：  
当您在连续的 5 个交易日内，进行第 4 次日内交易时，为了防止您被无意识地标记为 PDT，服务器会对此交易进行拦截。若您主动想被标记为 PDT，并且不希望服务器拦截，可以采取以下措施：  
在 [命令行 OpenD 中配置参数](https://openapi.futunn.com/futu-api-doc/opend/opend-cmd.html#8799)
，将启动参数 `pdt_protection` 的值修改为 0，以关闭“防止被标记为日内交易者”的功能。

![US_para](https://openapi.futunn.com/futu-api-doc/assets/img/US_para.cb012764.png)  
注意：若您被标记 PDT，当您的账户权益小于$25000时，您将无法开仓。

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#5881-2)
 如何关闭 DTCall 预警提醒？

A：  
您被标记为 PDT 后，需要留意账户的日内交易购买力（DTBP），日内交易超出 DTBP 时将收到日内交易保证金追缴（DTCall）。服务器会在您即将开仓下单超出剩余日内交易购买力前，阻止您的下单。若您仍然希望进行下单，并且不希望服务器拦截，可以采取以下措施：  
在 [命令行 OpenD 中配置参数](https://openapi.futunn.com/futu-api-doc/opend/opend-cmd.html#8799)
，将启动参数 `dtcall_confirmation` 的值修改为 0，以关闭“日内交易保证金追缴预警”的功能。

![US_para2](https://openapi.futunn.com/futu-api-doc/assets/img/US_para2.01f6f10b.png)  
注意：若您开仓订单的市值大于您的剩余日内交易购买力，并且在今日平仓当前标的，您将会收到日内交易保证金追缴通知（Day-Trading Call），只能通过存入资金才能解除。

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#5032-2)
 如何查看 DTBP 的值？

A：  
通过 [查询账户资金](https://openapi.futunn.com/futu-api-doc/trade/get-funds.html#4346)
 接口，可以获取日内交易相关的返回值，如：剩余日内交易次数、初始日内交易购买力、剩余日内交易购买力等。

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#6757)
 Q13：如何跟踪订单成交状态
-------------------------------------------------------------------------------

A: 下单后，可使用以下接口跟踪订单成交状态：

| 交易环境 | 接口  |
| --- | --- |
| 真实交易 | [响应订单推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order.html)<br>，[响应成交推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html) |
| 模拟交易 | [响应订单推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order.html) |

注意：对于非 python 语言用户，在使用上述两个接口之前，需要先进行 [订阅交易推送](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html)

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#8916)
 响应订单推送回调 的特点：

反馈 整个订单 的信息变动。当以下 8 个字段发生变化时，会触发订单推送：  
`订单状态`，`订单价格`，`订单数量`，`成交数量`，`触发价格`，`跟踪类型`，`跟踪金额/百分比`，`指定价差`

因此，当您进行下单、改单，撤单、使生效、使失效操作，或者订单在市场中发生了高级订单被触发、有成交变动的情况，都会触发订单推送。您只需要调用 [响应成交推送回调](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html)
，即可监听这些信息。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#9837)
 响应成交推送回调 的特点：

只反馈 单笔成交 的信息。当以下 1 个字段发生变化时，会触发订单推送：  
`成交状态`

举例：假设一笔限价单订单 900 股，分成了 3 次才完全成交，每次成交分别是：200、300、400 股。  
![example](https://openapi.futunn.com/futu-api-doc/assets/img/example.e686c0de.png)

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#6190)
 Q14：下单接口返回“此产品最小单位为 xxx，请调整至最小单位的整数倍后再提交”？
-----------------------------------------------------------------------------------------------------------

A:  
对于不同市场的标的，交易所有着不同的最小变动单位要求。如果提交的订单价格不符合要求，订单将会被拒绝。各市场价位规则如下：

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#652)
 价位规则

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#790)
 香港市场

以港交所官方说明为准，点击 [这里](https://www.futufin.com/hans/support/topic605?lang=zh-cn)
 。

以港交所官方说明为准，点击 [这里](https://www.moomoo.com/us/hans/support/topic4_304)
 。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#7287)
 A 股市场

股票价位：0.01。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#5540)
 美国市场

股票价位：

| 合约价格 | 价位  |
| --- | --- |
| $1 以下 | $0.0001 |
| $1 以上 | $0.01 |

期权价位：

| 合约价格 | 价位  |
| --- | --- |
| $0.10 - $3.00 | $0.01 或者 $0.05 |
| $3.00 以上 | $0.05 或者 $0.10 |

期货价位：不同合约价位规则不同。可以通过 [获取期货合约资料](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html#7447)
 接口的返回字段 `最小变动的单位` 查看。

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#1771)
 怎么避免订单价格不在价位上？

*   方法一：通过 [获取实时摆盘](https://openapi.futunn.com/futu-api-doc/quote/get-order-book.html)
     接口，获取合法的交易价格。交易所摆盘上的价位一定是合法的价位。
    
*   方法二：通过 [下单](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)
     接口的参数 `价格微调幅度`，将传入价格自动调整到合法的交易价格上。
    
    例如：假设腾讯控股当前市价为 359.600，根据价位规则，对应的最小变动价位为 0.200。
    
    假设您的下单传入订单价格为 359.678，价格微调幅度为 0.0015，代表接受 OpenD 对传入价格自动向上调整到最近的合法价位，且不能超过 0.15%。此情景下，向上最近的合法价格为 359.800，价格实际需要调整的幅度为 0.034%，符合价格微调幅度的要求，因此最终提交的订单价格为 359.800。
    
    若价格微调幅度设置数值小于实际需要调整的幅度，OpenD 自动调整价位失败，订单仍会返回报错“订单价格不在价位上”。
    

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2787)
 Q15：我的购买力足够，为什么下市价单会返回“购买力不足”？
-----------------------------------------------------------------------------------------------

A：

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#4843)
 为什么市价单会提示购买力不足

*   出于风控考量，系统给了市价单较高的购买力系数。在所有订单参数都相同的情况下，选择市价单会比限价单占用更多的购买力。
*   而且对于不同的品种，和不同的市场情况，风控系统会对市价单的购买力系数做动态调整。所以在下市价单时，若您通过最大购买力去计算最大可买数量，计算的结果很可能是不准确的。

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#5129)
 如何计算正确的可买数量

不建议自己计算，您可以通过 [查询最大可买可卖](https://openapi.futunn.com/futu-api-doc/trade/get-max-trd-qtys.html)
 接口获取正确的可买数量。

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#1085)
 如何尽可能买更多

您可以用价格为对价的限价单，替代市价单进行交易。  
其中，对价：买1价（下卖单时）或 卖1价（下买单时）

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#546)
 Q16：API模拟交易下单，为什么移动端看不到？
----------------------------------------------------------------------------------------

A：  
移动端、桌面端、网页端，美股模拟交易账户，已经从【美股模拟账户】升级成为功能更丰富的【美股融资融券账户】。  

Futu API 暂未升级（规划中），目前只能使用旧的【美股模拟账户】，且旧的【美股模拟账户】无法在其他客户端上展示，请谨慎使用。

Moomoo API 暂未升级（规划中），目前只能使用旧的【美股模拟账户】，且旧的【美股模拟账户】无法在其他客户端上展示，请谨慎使用。

[#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#4941)
 Q17：交易接口参数使用说明
-------------------------------------------------------------------------------

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#4791)
 1. 什么是交易对象？

您的平台账号下一般会开设一个保证金综合账户，其中有多个交易子账户（正常有两个，一个综合证券账户，一个综合期货账户；根据需要还可能有综合外汇账户等其他子账户）。一些特殊用户或机构客户可能会在多个券商下开设多个综合账户。  
创建交易对象，是初步筛选子账户的过程。

*   使用 OpenSecTradeContext 创建的交易对象，调用 get\_acc\_list 时只会返回**证券交易账户**
*   使用 OpenFutureTradeContext 创建的交易对象，调用 get\_acc\_list 时只会返回**期货交易账户**

参数 security\_firm 用来筛选对应归属券商的账户，参数 filter\_trdmarket 用来筛选对应交易市场权限的账户。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#916)
 1.1 security\_firm 券商参数

Futu API 目前支持的券商有 [这些](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572)
。  
创建的交易对象，在调用 get\_acc\_list 时，会返回 security\_firm 对应券商的真实账户和所有模拟交易账户（这是因为模拟交易没有券商的概念，所以无论 security\_firm 传什么，都会返回所有的模拟账户）。  
security\_firm 的默认值是 FUTUSECURITIES，FUTU HK 券商账户可以不填此参数，但需要获取其他券商的账户时，需要修改券商参数。

*   **Example 1**

参数 security\_firm 用来筛选对应归属券商的账户，参数 filter\_trdmarket 用来筛选对应交易市场权限的账户。

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#916-2)
 1.1 security\_firm 券商参数

Moomoo API 目前支持的券商有 [这些](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572)
。  
创建的交易对象，在调用 get\_acc\_list 时，会返回 security\_firm 对应券商的真实账户和所有模拟交易账户（这是因为模拟交易没有券商的概念，所以无论 security\_firm 传什么，都会返回所有的模拟账户）。  
security\_firm 的默认值是 FUTUSECURITIES，FUTU HK 券商账户可以不填此参数，但需要获取其他券商的账户时，需要修改券商参数。

*   **Example 1**

\`\`\`python trd\_ctx = OpenSecTradeContext(security\_firm=SecurityFirm.FUTUSECURITIES) ret, data = trd\_ctx.get\_acc\_list() print(data) \`\`\` \* \*\*Output\*\* \`\`\`python acc\_id trd\_env acc\_type uni\_card\_num card\_num security\_firm sim\_acc\_type trdmarket\_auth acc\_status 0 281756478396547854 REAL MARGIN 1001200163530138 1001369091153722 FUTUSECURITIES N/A \[HK, US, HKCC, HKFUND, USFUND\] ACTIVE 1 3450309 SIMULATE CASH N/A N/A N/A STOCK \[HK\] ACTIVE 2 3548731 SIMULATE MARGIN N/A N/A N/A OPTION \[HK\] ACTIVE 3 281756455998014447 REAL MARGIN N/A 1001100320482767 FUTUSECURITIES N/A \[HK\] DISABLED \`\`\`

*   **Example 2**

    trd_ctx = OpenSecTradeContext(security_firm=SecurityFirm.FUTUSG)
    ret, data = trd_ctx.get_acc_list()
    print(data)
    

1  
2  
3  

*   **Output**

        acc_id   trd_env acc_type uni_card_num card_num security_firm sim_acc_type trdmarket_auth acc_status
    0  3450309  SIMULATE     CASH          N/A      N/A           N/A        STOCK           [HK]     ACTIVE
    1  3548731  SIMULATE   MARGIN          N/A      N/A           N/A       OPTION           [HK]     ACTIVE
    

1  
2  
3  

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2014)
 1.2 filter\_trdmarket 交易市场参数

Futu API 目前支持的交易市场有 \[这些\](../trade/trade.html#719)。

Moomoo API 目前支持的交易市场有 \[这些\](../trade/trade.html#719)。

创建的交易对象，在调用 get\_acc\_list 时，会返回所有拥有 filter\_trdmarket 市场交易权限的账户；当 filter\_trdmarket 入参传 NONE 时，不过滤市场，返回所有的账户。 filter\_trdmarket 的默认参数是 HK，在综合账户体系下，这个参数用来筛选不同市场下的模拟交易账户。 \* \*\*Example 1\*\* \`\`\`python trd\_ctx = OpenSecTradeContext(filter\_trdmarket=TrdMarket.US) ret, data = trd\_ctx.get\_acc\_list() print(data) \`\`\` \* \*\*Output\*\* \`\`\`python acc\_id trd\_env acc\_type uni\_card\_num card\_num security\_firm sim\_acc\_type trdmarket\_auth acc\_status 0 281756478396547854 REAL MARGIN 1001200163530138 1001369091153722 FUTUSECURITIES N/A \[HK, US, HKCC, HKFUND, USFUND\] ACTIVE 1 3450310 SIMULATE MARGIN N/A N/A N/A STOCK \[US\] ACTIVE 2 3548732 SIMULATE MARGIN N/A N/A N/A OPTION \[US\] ACTIVE 3 281756460292981743 REAL MARGIN N/A 1001100520714263 FUTUSECURITIES N/A \[US\] DISABLED \`\`\`

*   **Example 2**

    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.NONE)
    ret, data = trd_ctx.get_acc_list()
    print(data)
    

1  
2  
3  

*   **Output**

                    acc_id   trd_env acc_type      uni_card_num          card_num   security_firm sim_acc_type                  trdmarket_auth acc_status
    0   281756478396547854      REAL   MARGIN  1001200163530138  1001369091153722  FUTUSECURITIES          N/A  [HK, US, HKCC, HKFUND, USFUND]     ACTIVE
    1              3450309  SIMULATE     CASH               N/A               N/A             N/A        STOCK                            [HK]     ACTIVE
    2              3450310  SIMULATE   MARGIN               N/A               N/A             N/A        STOCK                            [US]     ACTIVE
    3              3450311  SIMULATE     CASH               N/A               N/A             N/A        STOCK                            [CN]     ACTIVE
    4              3548732  SIMULATE   MARGIN               N/A               N/A             N/A       OPTION                            [US]     ACTIVE
    5              3548731  SIMULATE   MARGIN               N/A               N/A             N/A       OPTION                            [HK]     ACTIVE
    6   281756455998014447      REAL   MARGIN               N/A  1001100320482767  FUTUSECURITIES          N/A                            [HK]   DISABLED
    7   281756460292981743      REAL   MARGIN               N/A  1001100520714263  FUTUSECURITIES          N/A                            [US]   DISABLED
    8   281756468882916335      REAL   MARGIN               N/A  1001100610464507  FUTUSECURITIES          N/A                          [HKCC]   DISABLED
    9   281756507537621999      REAL     CASH               N/A  1001100910390035  FUTUSECURITIES          N/A                        [HKFUND]   DISABLED
    10  281756550487294959      REAL     CASH               N/A  1001101010406844  FUTUSECURITIES          N/A                        [USFUND]   DISABLED
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  

提示

当 filter\_trdmarket 入参NONE时，可以返回所有的交易账户。其中第0行是真实账户，1~5行均为模拟交易账户，6~10行是已失效的真实账户。这些失效账户都是单市场账户，现已被综合账户替代。但历史订单和历史成交还在这些已失效的账户中，可以通过这些账户来查询。  
OpenFutureTradeContext 对象中没有 filter\_trdmarket 参数，只有 security\_firm 参数，功能与 OpenSecTradeContext 一样。

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#7975)
 2. 交易接口参数

在使用具体的交易接口（如下单、查询订单列表）时，接口中的 `trd_env`, `acc_index` 和 `acc_id` 参数，会先筛选确认一个唯一的账户，对此账户实施对应的接口行为。 ![acc-select](https://openapi.futunn.com/futu-api-doc/assets/img/acc-select.a93409b7.jpg)

总结

1.  根据 trd\_env 筛选出真实账户还是模拟账户
2.  在筛选结果中，优先选择 acc\_id 指定的账户
3.  如果 acc\_id 为0，则通过 acc\_index选取对应账号
4.  报错场景：指定的 acc\_id 不存在，或 acc\_index 超出范围

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#5838)
 3. 应用举例

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#362)
 3.1 综合证券账户实盘下单

    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.NONE, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.unlock_trade("123123")
    if ret == RET_OK:
        print("解锁成功")
        ret, data = trd_ctx.place_order(45, 200, 'HK.00700', TrdSide.BUY,
                                        order_type=OrderType.NORMAL,
                                        trd_env=TrdEnv.REAL,  # 和默认参数一样，可以不填
                                        acc_id=0)  # 和默认参数一样，可以不填
        print(data)
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2050)
 3.2 综合期货账户查询实盘订单列表

    trd_ctx = OpenFutureTradeContext(security_firm=SecurityFirm.FUTUSECURITIES)
    
    ret, data = trd_ctx.order_list_query(trd_env=TrdEnv.REAL,   # 和默认参数一样，可以不填
                                         acc_id=0)  # 和默认参数一样，可以不填
    print(data)
    

1  
2  
3  
4  
5  

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#1002)
 3.3 港股模拟现金账户查询账户资金

    # filter_trdmarket 填 TrdMarket.HK
    # trd_env 填 TrdEnv.SIMULATE
    # acc_index 填 0
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK)
    ret, data = trd_ctx.accinfo_query(trd_env=TrdEnv.SIMULATE, acc_index=0)
    print(data)
    

1  
2  
3  
4  
5  
6  

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#3554)
 3.4 美股模拟保证金账户下单期权

    # 通过 filter_trdmarket 和 trd_env 筛选完之后只剩两个账户
    # 第0个是美股现金账户（交易股票）,第1个是美股保证金账户（交易期权）
    # acc_index 填 1 指定美股保证金账户
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US)
    ret, data = trd_ctx.place_order(10, 1, code="US.AAPL250618P550000",trd_side=TrdSide.BUY,
                                    trd_env=TrdEnv.SIMULATE,
                                    acc_index=1)
    print(data)
    

1  
2  
3  
4  
5  
6  
7  
8  

#### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#9972)
 3.5 日本期货模拟账户查询最大可买卖

    # 将 get_acc_list 的结果打印出来，可以看到日本期货模拟账户的 acc_id 是 6271199
    # 请求最大可买卖接口时传入这个 acc_id 
    trd_ctx = OpenFutureTradeContext()
    ret, data = trd_ctx.acctradinginfo_query(order_type=OrderType.NORMAL,
                                             price=5000,
                                             trd_env=TrdEnv.SIMULATE,
                                             acc_id=6271199,
                                             code="JP.NK225main")
    print(data)
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

### [#](https://openapi.futunn.com/futu-api-doc/qa/trade.html#5200)
 4. API 中的账户如何与 APP/桌面端对应

![card-app](https://openapi.futunn.com/futu-api-doc/assets/img/card-app.49c05761.png) APP 上的账户仅显示卡号后 4 位数字，我们将 [get\_acc\_list](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html)
 的返回结果打印出来后，有 uni\_card\_num 列和 card\_num 列，分别对应综合账户的卡号，和单币种账户（已废弃）的卡号。通过卡号后 4 位数就能把 API 中获取到的账号与 APP 上对应起来了。

← [行情相关](https://openapi.futunn.com/futu-api-doc/qa/quote.html) [其他](https://openapi.futunn.com/futu-api-doc/qa/other.html)
 →

[交易相关](https://openapi.futunn.com/futu-api-doc/qa/trade.html)

*   [Q1：模拟交易相关](https://openapi.futunn.com/futu-api-doc/qa/trade.html#1690)
    
*   [Q2：是否支持 A 股交易？](https://openapi.futunn.com/futu-api-doc/qa/trade.html#830)
    
*   [Q3：各市场支持的交易方向](https://openapi.futunn.com/futu-api-doc/qa/trade.html#3828)
    
*   [Q4：真实交易中，各市场支持的订单类型](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2731)
    
*   [Q5：各市场支持的订单操作](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2239)
    
*   [Q6：OpenD 启动参数 futuretradeapitimezone 如何使用？](https://openapi.futunn.com/futu-api-doc/qa/trade.html#9274)
    
*   [Q7：通过 API 下的订单，能在 APP 上面看到吗？](https://openapi.futunn.com/futu-api-doc/qa/trade.html#7945)
    
*   [Q8：哪些品类支持在非交易时段下单？](https://openapi.futunn.com/futu-api-doc/qa/trade.html#4721)
    
*   [Q9：对于下单接口，各订单类型对应的必传参数以及券商对单笔订单的下单限制](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2984)
    
*   [Q10：对于改单接口，修改订单时，各订单类型对应的必传参数](https://openapi.futunn.com/futu-api-doc/qa/trade.html#689)
    
*   [Q11：交易接口返回“当前证券业务账户尚未同意免责协议”？](https://openapi.futunn.com/futu-api-doc/qa/trade.html#4378)
    
*   [Q12：典型日内交易者（PDT）相关](https://openapi.futunn.com/futu-api-doc/qa/trade.html#9716)
    
*   [Q13：如何跟踪订单成交状态](https://openapi.futunn.com/futu-api-doc/qa/trade.html#6757)
    
*   [Q14：下单接口返回“此产品最小单位为 xxx，请调整至最小单位的整数倍后再提交”？](https://openapi.futunn.com/futu-api-doc/qa/trade.html#6190)
    
*   [Q15：我的购买力足够，为什么下市价单会返回“购买力不足”？](https://openapi.futunn.com/futu-api-doc/qa/trade.html#2787)
    
*   [Q16：API模拟交易下单，为什么移动端看不到？](https://openapi.futunn.com/futu-api-doc/qa/trade.html#546)
    
*   [Q17：交易接口参数使用说明](https://openapi.futunn.com/futu-api-doc/qa/trade.html#4941)