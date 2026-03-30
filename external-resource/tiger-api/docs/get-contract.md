# 获取合约

合约介绍

[](./get-contract.md#%E5%90%88%E7%BA%A6%E4%BB%8B%E7%BB%8D)

---------------------------------------------------------------------------------------------

合约是指交易的买卖对象或者标的物（比如一只股票或一只期权），合约是由交易所统一制定的。比如购买老虎证券的股票，可以通过TIGR这个字母代号和市场信息（即market=’US‘，美国市场）来唯一标识。通过合约信息，我们在下单或者获取行情时就可以唯一的确定一个标的物。常见的合约包括股票合约，期权合约，期货合约等。

大多数合约（如：股票，差价合约，指数或外汇）可通过以下**四个基础**属性唯一确定：

*   标的代码 (symbol)：一般美股、英股等合约代码都是英文字母，港股、A股等合约代码是数字，比如老虎证券的symbol是TIGR。
*   合约类型 (security type)：常见合约类型包括：STK（股票），OPT（期权），FUT（期货），CASH（外汇），比如老虎证券股票的合约类型是STK。
*   货币类型 (currency)：常见货币包括 USD（美元），HKD（港币）。
*   交易所 (exchange)：STK类型的合约一般不会用到交易所字段，订单会自动路由，期货合约都用到交易所字段。

还有一些合约（如：期权和期货）由于其性质更复杂，需要一些额外的信息才能唯一标识。

以下是几种常见类型合约，以及其由哪些要素构成。

**股票**

Python

    contract = Contract()
    contract.symbol ="TIGR"
    contract.sec_type ="STK"
    contract.currency ="USD" #not required
    contract.market = "US" #not required

**期权**

老虎API的期权合约支持**两种**方式：

*   一种是四要素方式，即symbol（股票代码），expiry（期权过期日），strike（期权行权价格），right（期权方向）。
    
*   另一种是标准OCC期权合约格式，长度固定为21位。包含四部分：
    
    *   相关的股票或ETF的代码，比如（AAPL），固定占六位字符，不足位数由空格填充
    *   期权到期日，6位数字，格式为：yymmdd
    *   期权类型，取值为 P 或者 C, 表示 put 或 call
    *   期权行权价格，取值为 价格 x 1000, 固定占8位数字，前面不足的位数由0填充

![](https://files.readme.io/d06f43dcd73e14f033bc73784c4984f5b455e6c79df8bfc932f99e1766882898-img_v3_02pn_66aa854b-afc4-4d3e-8c80-4ea1a577babg.png)

* * *

get\_contract 获取单个合约信息

[](./get-contract.md#get_contract-%E8%8E%B7%E5%8F%96%E5%8D%95%E4%B8%AA%E5%90%88%E7%BA%A6%E4%BF%A1%E6%81%AF)

----------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_contract(symbol, sec_type=SecurityType.STK, currency=None, exchange=None, expiry=None, strike=None, put_call=None)`

**说明**

查询交易所需的单个合约信息

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | str | Yes | 股票代码，如 'AAPL' |
| sec\_type | SecurityType | No  | 证券类型，tigeropen.common.consts.SecurityType 枚举，**默认为： SecurityType.STK** |
| currency | Currency | No  | 币种，tigeropen.common.consts.Currency 枚举，如 Currency.USD |
| exchange | str | No  | 交易所，非必填，如 'CBOE' |
| expiry | str | No  | 合约到期日（适用于期货/期权），格式 yyyyMMdd，如 ‘20220130’ |
| strike | float | No  | 行权价（适用于期权） |
| put\_call | str | No  | 看涨看跌（适用于期权），'PUT' 看跌， 'CALL' 看涨 |

**返回**

`tigeropen.trade.domain.contract.Contract` 合约对象, 参见[对象介绍](./appendix-object-detail.md#contract)
。常用属性如下

**对象属性**

| 属性名 | 类型  | 描述  |
| --- | --- | --- |
| identifier | str | 唯一标识，股票identifier和symbol相同，期权为21位标识符，如：'AAPL 220729C00150000'，期货identifier |
| symbol | str | 股票代码，期权合约的symbol为对应标的物代码 |
| sec\_type | str | STK 股票/OPT 期权/FUT 期货/WAR 窝轮/IOPT 牛熊证等，默认 STK |
| name | str | 合约名称 |
| currency | str | 币种，如：USD/HKD/CNH |
| exchange | str | 交易所 |
| expiry | str | **期权和期货专有**，期权或期货过期日 |
| strike | float | **期权专有**，期权的行权价格 |
| multiplier | float | 乘数，每手对应的数量 |
| put\_call | str | **期权专有**，期权方向，CALL 或者 PUT |
| local\_symbol | str | **环球账户专有**，港股用于识别窝轮和牛熊证 |
| short\_margin | float | 做空保证金比例(将废弃，请使用short\_initial\_margin代替) |
| short\_initial\_margin | float | 做空初始保证金比例 |
| short\_maintenance\_margin | float | 做空维持保证金比例（综合账号有值，环球账号合约没有值） |
| short\_fee\_rate | float | 做空费率 |
| shortable | bool | 是否可做空 |
| shortable\_count | int | 做空池剩余 |
| long\_initial\_margin | float | 做多初始保证金 |
| long\_maintenance\_margin | float | 做多维持保证金 |
| contract\_month | str | 合约月份， 如202201，表示2022年1月 |
| primary\_exchange | str | 股票上市交易所 |
| marginable | bool | 是否可融资 |
| market | str | 市场，如：US/HK/CN |
| min\_tick | float | 最小报价单位 |
| tickSizes | `[{"begin":"0","end":"1","tickSize":1.0E-4,"type":"CLOSED"},{"begin":"1","end":"Infinity","tickSize":0.01,"type":"OPEN"}]` | 最小报价单位价格区间，即当挂单价格在begin和end区间时，要满足tickSize要求，begin：价格左区间，end：价格右区间，type：区间类型 OPEN/OPEN\_CLOSED/CLOSED/CLOSED\_OPEN(开区间/左开右闭/闭区间/左闭右开)，tickSize：最小价格单位 |
| trading\_class | str | 合约的交易级别名称 |
| close\_only | bool | 是否只可平仓 |
| status | str | 合约状态. 0 不可交易，1 可交易 |
| continuous | bool | **期货专有**，是否为连续合约 |
| trade | bool | 是否可交易 |
| last\_trading\_date | str | **期货专有**，最后交易日，如 '20211220'，表示2021年12月20日 |
| first\_notice\_date | str | **期货专有**，第一通知日，合约在第一通知日后无法开多仓. 已有的多仓会在第一通知日之前（通常为前三个交易日）被强制平仓，如 '20211222'，表示2021年12月22日 |
| last\_bidding\_close\_time | int | **期货专有**，竞价截止时间戳 |
| is\_etf | bool | 是否是ETF |
| etf\_leverage | int | ETF杠杆倍数，仅当合约为ETF时会存在该值 |
| discounted\_day\_initial\_margin | float | **期货专有**，日内优惠初始保证金比例 |
| discounted\_day\_maintenance\_margin | float | **期货专有**，日内优惠维持保证金比例 |
| discounted\_time\_zone\_code | float | **期货专有**，日内优惠时间时区 |
| discounted\_start\_at | float | **期货专有**，日内优惠开始时间 |
| discounted\_end\_at | float | **期货专有**，日内优惠结束时间 |
| lot\_size | float | 单笔交易中可交易的最小资产数量 |
| support\_overnight\_trading | bool | 是否支持夜盘交易 |
| support\_fractional\_share | bool | 是否支持碎股交易(仅使用综合/模拟账户),港股碎股最小是1股，美股碎股订单价值最小为1USD,碎股的精度是到0.0001 |

> 📘
> 
> **提示**
> 
> print时只会显示部分属性，可以用 `print(contract.to_str())` 打印全部属性

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    from tigeropen.common.consts import SecurityType
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    # Stock
    contract = trade_client.get_contract('AAPL', sec_type=SecurityType.STK)
    # Future
    # contract = trade_client.get_contract('ES2306', sec_type=SecurityType.STK)
    print(contract)
    # 默认只打印 合约symbol/合约类型/合约币种. 使用 to_str 打印全部属性. 更多属性查看请直接指定属性名称， 如 contract.short_margin
    print(contract.to_str())
    
    # 查看做空初始保证金
    print(contract.short_initial_margin)

**返回示例**

    {'contract_id': 1916, 'symbol': 'AAPL', 'currency': 'USD', 'sec_type': 'STK', 'exchange': None, 'origin_symbol': None, 
    'local_symbol': 'AAPL', 'expiry': None, 'strike': None, 'put_call': None, 'multiplier': 1.0, 'name': '苹果', 
    'short_margin': 0.35, 'short_initial_margin': 0.35, 'short_maintenance_margin': 0.3, 'short_fee_rate': None, 
    'shortable': True, 'shortable_count': None, 'long_initial_margin': 0.3, 'long_maintenance_margin': 0.25, 
    'contract_month': None, 'identifier': 'AAPL', 'primary_exchange': 'NASDAQ', 'market': 'US', 'min_tick': None, 
    'tick_sizes': [{'begin': '0', 'end': '1', 'type': 'CLOSED', 'tick_size': 0.0001}, {'begin': '1', 'end': 'Infinity', \
    'type': 'OPEN', 'tick_size': 0.01}], 'trading_class': 'AAPL', 'status': 1, 'marginable': True, 'trade': True, 
    'close_only': False, 'continuous': None, 'last_trading_date': None, 'first_notice_date': None, 
    'last_bidding_close_time': None, 'is_etf': False, 'etf_leverage': None, 'discounted_day_initial_margin': None, 
    'discounted_day_maintenance_margin': None, 'discounted_time_zone_code': None, 'discounted_start_at': None, 
    'discounted_end_at': None, 'categories': None, 'lot_size': 1.0, 'support_overnight_trading': True}

* * *

get\_contracts 获取多个合约信息

[](./get-contract.md#get_contracts-%E8%8E%B7%E5%8F%96%E5%A4%9A%E4%B8%AA%E5%90%88%E7%BA%A6%E4%BF%A1%E6%81%AF)

------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_contracts(symbol, sec_type=SecurityType.STK, currency=None, exchange=None):`

**说明**

查询交易所需的多个合约信息时，以列表形式返回

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | str | Yes | 股票代码，如： 'AAPL'。单次请求上限为50 |
| sec\_type | SecurityType | No  | 证券类型，tigeropen.common.consts.SecurityType 枚举，**默认为：SecurityType.STK** |
| currency | Currency | No  | 币种，tigeropen.common.consts.Currency 枚举，如 Currency.USD |
| exchange | str | No  | 交易所，非必填，如： 'CBOE' |

**返回**

`list`

列表中每一项为合约对象（tigeropen.trade.domain.contract.Contract），参见[对象介绍](./appendix-object-detail.md#contract)
。常用属性如下

**对象属性**

| 属性名 | 描述  |
| --- | --- |
| identifier | 唯一标识，股票identifier和symbol相同，期权为21位标识符，如：'AAPL 220729C00150000'，期货identifier |
| symbol | 股票代码，期权合约的symbol为对应标的物代码 |
| sec\_type | STK 股票/OPT 期权/FUT 期货/WAR 窝轮/IOPT 牛熊证等，默认 STK |
| name | 合约名称 |
| currency | 币种，如：USD/HKD/CNH |
| exchange | 交易所 |
| expiry | **期权和期货专有**，期权或期货过期日 |
| strike | **期权专有**，期权的行权价格 |
| multiplier | 乘数，每手对应的数量 |
| put\_call | **期权专有**，期权方向，CALL 或者 PUT |
| local\_symbol | **环球账户专有**，港股用于识别窝轮和牛熊证 |
| short\_margin | 做空保证金比例(将废弃，请使用short\_initial\_margin代替) |
| short\_initial\_margin | 做空初始保证金比例 |
| short\_maintenance\_margin | 做空维持保证金比例（综合账号有值，环球账号合约没有值） |
| short\_fee\_rate | 做空费率 |
| shortable | 做空池剩余 |
| long\_initial\_margin | 做多初始保证金 |
| long\_maintenance\_margin | 做多维持保证金 |
| contract\_month | 合约月份， 如：202201，表示2022年1月 |
| primary\_exchange | 股票上市交易所 |
| market | 市场 如：US/HK/CN |
| min\_tick | 最小报价单位 |
| tickSizes | **股票专有**，最小报价单位价格区间，即当挂单价格在begin和end区间时，要满足tickSize要求，begin：价格左区间，end：价格右区间，type：区间类型 OPEN/OPEN\_CLOSED/CLOSED/CLOSED\_OPEN(开区间/左开右闭/闭区间/左闭右开)，tickSize：最小价格单位 |
| trading\_class | 合约的交易级别名称 |
| status | 合约状态 |
| continuous | **期货专有**，是否为连续合约 |
| trade | **期货专有**，是否可交易 |
| last\_trading\_date | **期货专有**，最后交易日，如 '20211220'，表示2021年12月20日 |
| first\_notice\_date | **期货专有**，第一通知日，合约在第一通知日后无法开多仓. 已有的多仓会在第一通知日之前（通常为前三个交易日）被强制平仓，如 ：'20211222'，表示2021年12月22日 |
| last\_bidding\_close\_time | **期货专有**，竞价截止时间戳 |
| is\_etf | 是否是ETF |
| etf\_leverage | ETF杠杆倍数，仅当合约为ETF时会存在该值 |
| discounted\_day\_initial\_margin | **Futures only**, Intraday initial margin discount |
| discounted\_day\_maintenance\_margin | **Futures only**, Intraday maintenance margin discount |
| discounted\_time\_zone\_code | **Futures only**, Intraday margin discount period time zone |
| discounted\_start\_at | **Futures only**, Intraday margin discount start time |
| discounted\_end\_at | **Futures only**, Intraday margin discount end time |

> 📘
> 
> **提示**
> 
> print时只会显示部分属性，可以用 `print(contract.to_str())` 打印全部属性

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    contracts = trade_client.get_contracts('AAPL', sec_type=SecurityType.STK)
    print(contracts)
    print(contracts[0].to_str())

**返回示例**

JSON

    【
    {'contract_id': 1916, 'symbol': 'AAPL', 'currency': 'USD', 'sec_type': 'STK', 'exchange': None, 'origin_symbol': None, 
     'local_symbol': 'AAPL', 'expiry': None, 'strike': None, 'put_call': None, 'multiplier': 1.0, 'name': '苹果', 
     'short_margin': None, 'short_initial_margin': None, 'short_maintenance_margin': None, 'short_fee_rate': None, 
     'shortable': None, 'shortable_count': None, 'long_initial_margin': None, 'long_maintenance_margin': None, 
     'contract_month': None, 'identifier': 'AAPL', 'primary_exchange': None, 'market': 'US', 'min_tick': None, 
     'tick_sizes': [{'begin': '0', 'end': '1', 'type': 'CLOSED', 'tick_size': 0.0001}, {'begin': '1', 'end': 'Infinity', \
     'type': 'OPEN', 'tick_size': 0.01}], 'trading_class': 'AAPL', 'status': 1, 'marginable': None, 'trade': True, 
     'close_only': False, 'continuous': None, 'last_trading_date': None, 'first_notice_date': None, 
     'last_bidding_close_time': None, 'is_etf': False, 'etf_leverage': None, 'discounted_day_initial_margin': None, 
     'discounted_day_maintenance_margin': None, 'discounted_time_zone_code': None, 'discounted_start_at': None, 
     'discounted_end_at': None, 'categories': None, 'lot_size': 1.0, 'support_overnight_trading': True}】

* * *

本地生成合约对象

[](./get-contract.md#%E6%9C%AC%E5%9C%B0%E7%94%9F%E6%88%90%E5%90%88%E7%BA%A6%E5%AF%B9%E8%B1%A1)

-------------------------------------------------------------------------------------------------------------------------------------

**股票**

Python

    from tigeropen.common.util.contract_utils import stock_contract
    
    # US 股票
    contract = stock_contract(symbol='TIGR', currency='USD')
    
    # HK 股票
    contract = stock_contract(symbol='00700', currency='HKD')
    
    #SG 股票
    contract = stock_contract(symbol = '1A1.SI',currency = 'SGD')
    
    #AU 股票
    contract = stock_contract(symbol = 'MXT.AU', currency = 'AUD')

**期权**

Python

    from tigeropen.common.util.contract_utils import option_contract, option_contract_by_symbol
    contract = option_contract(identifier='AAPL  190118P00160000')
    # 或
    contract = option_contract_by_symbol('AAPL', '20200110', strike=280.0, put_call='PUT', currency='USD')
    
    
    # 期权代码和四要素之间相互转换
    from tigeropen.common.util.contract_utils import extract_option_info, get_option_identifier
    
    # 利用四要素组成期权代码
    underlying_symbol='AAPL'
    expiry='20200110'
    put_call='PUT'
    strike=280
    identifier = get_option_identifier(underlying_symbol, expiry, put_call, strike)
    # 从期权代码解析四要素 identifier='AAPL  190118P00160000'
    symbol, expiry, put_call, strike = extract_option_info(identifier)
    print(identifier)

**期货**

Python

    # 综合/模拟
    from tigeropen.common.util.contract_utils import future_contract
    contract = future_contract(symbol='CL2312', currency='USD')
    
    # 环球
    from tigeropen.common.util.contract_utils import future_contract
    contract = future_contract(symbol='CL', currency='USD', expiry='20190328', multiplier=1.0, exchange='SGX')
    
    # US 期货
    contract = future_contract(symbol='RB', currency='USD', expiry='20250829', multiplier=1.0, exchange='NYMEX')
    
    # HK 期货
    contract = future_contract(symbol='2318', currency='HKD', expiry='20251230', multiplier=500.0, exchange='HKEX')
    
    # SG 期货
    contract = future_contract(symbol='SSG', currency='SGD', expiry='20250429', multiplier=100.0, exchange='SGX')

**港股窝轮**

Python

    from tigeropen.common.util.contract_utils import war_contract_by_symbol
    contract = war_contract_by_symbol('01810', '20221116', 14.52, 'CALL', local_symbol='14759', multiplier=2000, currency='HKD')

**港股牛熊证**

Python

    from tigeropen.common.util.contract_utils import iopt_contract_by_symbol
    contract = iopt_contract_by_symbol('02318', '20200420', 87.4, 'CALL', local_symbol='63379', currency='HKD')

**基金**

Python

    #US 基金
    contract = fund_contract('IE00B11XZ988.USD')
    
    #HK 基金
    contract = fund_contract('LU0476943708.HKD')
    
    #SG 基金
    contract = fund_contract('LU2023250843.SGD')
    
    #AU 基金
    contract = fund_contract('SG9999015184.AUD')

**数字货币**

Python

    from tigeropen.common.util.contract_utils import cc_contract
    
    contract = cc_contract('BTC')

  
  

get\_derivative\_contracts 获取期权/窝轮/牛熊证合约列表

[](./get-contract.md#get_derivative_contracts-%E8%8E%B7%E5%8F%96%E6%9C%9F%E6%9D%83%E7%AA%9D%E8%BD%AE%E7%89%9B%E7%86%8A%E8%AF%81%E5%90%88%E7%BA%A6%E5%88%97%E8%A1%A8)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_derivative_contracts(symbol, sec_type, expiry, lang=None)` **输入参数：**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | str | Yes | 股票代码列表，仅支持一个symbol |
| sec\_type | SecurityType | Yes | 合约类型，目前支持: OPT 期权/ WAR 港股窝轮/ IOPT 港股牛熊证 |
| expiry | str | Yes | 到期日(yyyyMMdd)，如果是OPT必须有值，如 '20220929' |
| lang | str | No  | 语言支持: zh\_CN,zh\_TW,en\_US，默认: en\_US |

**返回结果：** `list`

列表中每一项为合约对象（tigeropen.trade.domain.contract.Contract），参见[对象介绍](./appendix-object-detail.md#contract)
。常用属性如下

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| symbol | string | 股票代码 |
| name | string | 合约名称 |
| exchange | string | 交易所 |
| market | string | 市场  |
| sec\_type | string | 合约类型 |
| currency | string | 币种  |
| expiry | string | 到期日(期权、窝轮、牛熊证、期货)， 如：20171117 |
| right | string | 期权方向 (期权、窝轮、牛熊证)，PUT/CALL |
| strike | float | 行权价 |
| multiplier | float | 乘数，每手对应的数量 (期权、窝轮、牛熊证、期货) |

**请求示例:**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    contracts = trade_client.get_derivative_contracts('00700', SecurityType.WAR, '20220929')
    print(contracts)

**响应示例：**

JSON

    {
        "symbol": "29298",
        "name": "BPTENCT@EC2509B.C",
        "exchange": "SEHK",
        "market": "HK",
        "sec_type": "WAR",
        "currency": "HKD",
        "expiry": "20250925",
        "strike": "500.5",
        "multiplier": 10000.0,
        "right": "CALL"
    }, {
        "symbol": "29290",
        "name": "CTTENCT@EC2509A.C",
        "exchange": "SEHK",
        "market": "HK",
        "sec_type": "WAR",
        "currency": "HKD",
        "expiry": "20250925",
        "strike": "500.5",
        "multiplier": 10000.0,
        "right": "CALL"
    }
    
