# 查询账户信息

get\_managed\_accounts 获取管理的账号列表

[](./accounts.md#get_managed_accounts-%E8%8E%B7%E5%8F%96%E7%AE%A1%E7%90%86%E7%9A%84%E8%B4%A6%E5%8F%B7%E5%88%97%E8%A1%A8)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_managed_accounts(account=None)`

**说明**

获取关联的资金账号，机构账户返回主账户及所有子账户

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | str | No  | 账号id，选填，**不传则返回所有关联的 account** |

**返回**

`AccountProfile`（tigeropen.trade.domain.profile.AccountProfile）对象构成的列表

每个对象的属性如下：

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| account | 综合账号：50129912，环球：U5755619，模拟账号：20191221901212121 | 交易的资金账户，其中综合账号为5到10位数字，模拟账号为17位数字，环球账号以字母U开头 |
| capability | RegTMargin | 账户类型（CASH: 现金账户，RegTMargin: Reg T 保证金账户，PMGRN: 投资组合保证金） |
| status | Funded | 账号状态，大部分场景会返回已入金(Funded)状态。状态包括： Funded（已入金），Open（已开户），Pending（待开户），Rejected（开户被拒绝），Closed（已注销） |
| account\_type | STANDARD | 账户分类：GLOBAL（环球账号），STANDARD（综合账号），PAPER（模拟账号） |

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    accounts = trade_client.get_managed_accounts()
    # 查看第一个账户的相关属性
    account1 = accounts[0]
    print(account1.account)  # 账户号
    print(account1.account_type)  # 账户分类(综合/模拟)
    print(account1.capability)  # 账户能力(现金/保证金)
    

**返回示例**

    [AccountProfile({'account': 'DU575569', 'capability': None, 'status': 'Funded'})]

* * *

get\_prime\_assets 获取综合/模拟账户资产信息

[](./accounts.md#get_prime_assets-%E8%8E%B7%E5%8F%96%E7%BB%BC%E5%90%88%E6%A8%A1%E6%8B%9F%E8%B4%A6%E6%88%B7%E8%B5%84%E4%BA%A7%E4%BF%A1%E6%81%AF)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_prime_assets(account=None, base_currency=None, consolidated=True)`

**说明**

获取资产信息，适用于综合/模拟账户

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | str | No  | 账户id, **如不指定，则使用 client\_config 中的默认 account** |
| base\_currency | str | No  | 币种  |
| consolidated | bool | No  | 是否展示聚合Segment资产指标，只有SEC和FUND类别资产会聚合。默认为true |

**返回**

`list`

list 中的每个元素是一个 [PortfolioAccount 对象](./appendix-object-detail.md#portfolioaccount)
 。如果只有一个account，list 中只有一个元素。 PortfolioAccount 对象的结构如下。

**PortfolioAccount、Segment 中的字段详细解释请参考[对象信息](./appendix-object-detail.md#portfolioaccount)
**

    PortfolioAccount 对象
    ├── account：账户id
    ├── update_timestamp: 更新时间, 毫秒单位的时间戳
    ├── segments：分品种的账户信息,是以证券类别为 key 的 dict, 值为 Segment 对象
    │   ├── 'S' 表示证券账户，value 是 Segment 对象
    │   │    ├── currency: 币种, 如 USD, HKD
    │   │    ├── capability: 账户类型, 保证金账户: RegTMargin, 现金账户: Cash。
    │   │    ├── category: 交易品种分类 C: (Commodities 期货), S: (Securities 股票)
    │   │    ├── cash_balance: 现金额。
    │   │    ├── cash_available_for_trade: 可用资金，包括现金与融资额度，用作交易最大购买力参考。
    │   │    ├── cash_available_for_withdrawal': 当前账号内可以出金的现金金额
    │   │    ├── gross_position_value: 证券总价值
    │   │    ├── equity_with_loan: 含贷款价值总权益
    │   │    ├── net_liquidation: 总资产;净清算值
    │   │    ├── init_margin: 初始保证金
    │   │    ├── maintain_margin': 维持保证金
    │   │    ├── overnight_margin: 隔夜保证金
    │   │    ├── unrealized_pl: 浮动盈亏
    │   │    ├── realized_pl: 已实现盈亏
    │   │    ├── excess_liquidation: 当前剩余流动性
    │   │    ├── overnight_liquidation: 隔夜剩余流动性
    │   │    ├── buying_power: 购买力
    │   │    ├── leverage: 当前使用的杠杆倍数
    │   │    ├── locked_funds: 锁定资金
    │   │    ├── uncollected: 在途资金
    │   │    ├── currency_assets：按照交易币种区分的账户资产信息，是以币种为 key 的 dict
    │   │    │   ├── 'USD' 表示美元，value 是 CurrencyAsset 对象
    │   │    │   │   ├── currency: 当前的货币币种，常用货币包括： USD-美元，HKD-港币，SGD-新加坡币，CNH-人民币
    │   │    │   │   ├── cash_balance: 可以交易的现金，加上已锁定部分的现金（如已购买但还未成交的股票，还包括其他一些情形也会有锁定现金情况）
    │   │    │   │   ├── cash_available_for_trade: 当前账号内可以交易的现金金额
    │   │    │   │   ├── forex_rate: 当前币种对 base_currency 的汇率。比如 base_currency=USD, currency=HKD, forex_rate=0.128
    │   │    │   ├── 'HKD' 表示港币，value 是 CurrencyAsset 对象
    │   │    └─  └── 'CNH' 表示人民币，value 是 CurrencyAsset 对象
    │   └── 'C' 表示期货账户，value 是 Segment 对象
    │   └── 'F' 表示基金账户，value 是 Segment 对象
    │   └── 'D' 表示数字货币账户，value 是 Segment 对象
    
    
    

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import get_client_config
    client_config = get_client_config(private_key_path='私钥路径', tiger_id='your tiger id', account='your account')
    trade_client = TradeClient(client_config)
    
    portfolio_account = trade_client.get_prime_assets(base_currency='USD')  # 可设置基准币种
    print(portfolio_account)
    
    # 查看账户属性示例
    print(portfolio_account.account)   # 账户id
    print(portfolio_account.segments['S'].buying_power)  # 证券账户购买力
    print(portfolio_account.segments['S'].cash_balance)  # 证券账户现金值
    print(portfolio_account.segments['S'].unrealized_pl)   # 浮动盈亏
    print(portfolio_account.segments['S'].currency_assets['USD'].gross_position_value)  # 以美元计算的证券总价值
    print(portfolio_account.segments['S'].currency_assets['HKD'].gross_position_value)  # 以港币计算的证券总价值
    print(portfolio_account.segments['S'].currency_assets['HKD'].cash_balance)  # 以港币计算的现金值
    
    print(portfolio_account.segments['C'].init_margin)  # 期货账户初始保证金
    print(portfolio_account.segments['C'].maintain_margin)  # 期货账户维持保证金
    print(portfolio_account.segments['C'].currency_assets['USD'].cash_balance)  # 期货账户以美元计算的现金值
    
    

**返回示例**

Text

     PortfolioAccount(
      {
    	'account': '1234567',
    	'update_timestamp': 1638949616442,
    	'segments': {
    		'S': Segment({
    			'currency': 'USD',
    			'capability': 'RegTMargin',
    			'category': 'S',
    			'cash_balance': 111978.7160247,
    			'cash_available_for_trade': 123905.775195,
    			'cash_available_for_withdrawal': 123905.775195,
    			'gross_position_value': 22113.5652986,
    			'equity_with_loan': 134092.2813233,
    			'net_liquidation': 135457.2802984,
    			'init_margin': 9992.3764097,
    			'maintain_margin': 8832.4423281,
    			'overnight_margin': 11607.5876493,
    			'unrealized_pl': -1121.0821891,
    			'realized_pl': -3256.0,
    			'excess_liquidation': 125259.8389952,
    			'overnight_liquidation': 122484.693674,
    			'buying_power': 495623.1007801,
    			'leverage': 0.164693,
    			'currency_assets': {
    				'USD': CurrencyAsset({
    					'currency': 'USD',
    					'cash_balance': 123844.77,
              'cash_available_for_trade': 123792.77,
              'forex_rate': 1.0
    				}),
    				'HKD': CurrencyAsset({
    					'currency': 'HKD',
    					'cash_balance': -92554.07,
              'cash_available_for_trade': -93664.15,
              'forex_rate': 0.128
    				}),
    				'CNH': CurrencyAsset({
    					'currency': 'CNH',
    					'cash_balance': 0.0,
    					'cash_available_for_trade': 0.0
    				})
    			}
    		}),
    		'C': Segment({
    			'currency': 'USD',
    			'capability': 'RegTMargin',
    			'category': 'C',
    			'cash_balance': 3483681.32,
    			'cash_available_for_trade': 3481701.32,
    			'cash_available_for_withdrawal': 3481701.32,
    			'gross_position_value': 1000000.0,
    			'equity_with_loan': 3481881.32,
    			'net_liquidation': 3483681.32,
    			'init_margin': 1980.0,
    			'maintain_margin': 1800.0,
    			'overnight_margin': 1800.0,
    			'unrealized_pl': 932722.41,
    			'realized_pl': -30.7,
    			'excess_liquidation': 3481881.32,
    			'overnight_liquidation': 3481881.32,
    			'buying_power': 0.0,
    			'leverage': 0.0,
    			'currency_assets': {
    				'USD': CurrencyAsset({
    					'currency': 'USD',
    					'cash_balance': 3483681.32,
    					'cash_available_for_trade': 3483681.32
    				}),
    				'HKD': CurrencyAsset({
    					'currency': 'HKD',
    					'cash_balance': 0.0,
    					'cash_available_for_trade': 0.0
    				}),
    				'CNH': CurrencyAsset({
    					'currency': 'CNH',
    					'cash_balance': 0.0,
    					'cash_available_for_trade': 0.0
    				})
    			}
    		})
    	}
    })
    

  

get\_assets 获取环球账户资产

[](./accounts.md#get_assets-%E8%8E%B7%E5%8F%96%E7%8E%AF%E7%90%83%E8%B4%A6%E6%88%B7%E8%B5%84%E4%BA%A7)

--------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_assets(account=None, sub_accounts=None, segment=False, market_value=False)`

**说明**

获取账户资产信息，返回结构主要适用于**环球账户**，综合/模拟账号虽然也可使用此接口，但有较多字段为空，建议使用 [get\_prime\_assets](./accounts.md#get_prime_assets)
 查询综合/模拟账户资产

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | str | No  | 账户id，**如不指定, 则使用 client\_config 中的默认 account** |
| sub\_accounts | `list[str]` | No  | 子账户列表，默认为None |
| segment | bool | No  | 是否返回按照品种（证券、期货）分类的数据，默认 False，为True时，返回一个dict，C表示期货， S表示股票 |
| market\_value | bool | No  | 是否返回按照币种（美元、港币、人民币）分类的数据，默认为 False |
| secret\_key | str | No  | 机构交易员密钥，机构用户专有，**机构用户必填**，需要在client\_config中配置 |

**返回**

`list`

list 中的每个元素是一个 [PortfolioAccount 对象](./appendix-object-detail.md#portfolioaccountglobal)
。如果只有一个account，list 中只有一个元素。 PortfolioAccount(tigeropen.trade.domain.account.PortfolioAccount) 对象的结构如下。

**Account、SecuritySegment、CommoditySegment 中的信息请参考[对象信息](./appendix-object-detail.md#portfolioaccountglobal)
**

    PortfolioAccount 对象
    ├── account：账户id
    ├── summary：当前账户的汇总统计信息。里面的值是一个 Account 对象
    ├── segments：分品种的账户信息,是一个dict
    │   ├── 'S' 表示证券账户，value 是 SecuritySegment 对象
    │   └── 'C' 表示期货账户，value 是 CommoditySegment 对象
    ├── market_value：分币种的账户统计信息，是一个dict
    │   ├── 'USD' 表示美元，value 是 MarketValue 对象
    │   ├── 'HKD' 表示港币，value 是 MarketValue 对象
    └─  └── 'CNH' 表示人民币，value 是 MarketValue 对象

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    portfolio_account = trade_client.get_assets(segment=True, market_value=True)
    print(portfolio_account)
    
    # 查看第一个portfolio的属性
    portfolio1 = portfolio_account[0]
    print(portfolio1.account)  # 账户id
    print(portfolio1.segments['S'].available_funds)  # 证券账户的可用资金
    print(portfolio1.segments['S'].gross_position_value)  # 证券账户的市值
    print(portfolio1.segments['C'].available_funds)  # 期货账户的可用资金(已开通期货账户才会有值)
    print(portfolio1.summary.buying_power)  # 购买力
    print(portfolio1.summary.cash)  # 现金
    

**返回示例**

Text

    [PortfolioAccount({'account': 'DU111111', \
                        'summary': Account({'accrued_cash': 0, 'accrued_dividend': 0, 'available_funds': 948.69, \
                                            'buying_power': 948.69, 'cash': 948.81, 'currency': 'USD', 'cushion': 0.5944, \
                                            'day_trades_remaining': 3, 'equity_with_loan': 1255.69, 'excess_liquidity': 948.81, \
                                            'gross_position_value': 647.53, 'initial_margin_requirement': 307, \
                                            'maintenance_margin_requirement': 307, 'net_liquidation': 1596.34, \
                                            'realized_pnl': 0, 'regt_equity': 1255.81, \
                                            'regt_margin': 153.5, 'sma': 3512.56, \
                                            'timestamp': 1561529631, 'unrealized_pnl': -885.36}), \
    \
                        'segments': defaultdict(<class 'tigeropen.trade.domain.account.Account'>, \
                                {'C': CommoditySegment({'accrued_cash': 0, 'accrued_dividend': 0, \
                                                        'available_funds': 0, 'cash': 0, 'equity_with_loan': 0, 'excess_liquidity': 0, \
                                                        'initial_margin_requirement': 0, 'maintenance_margin_requirement': 0, \
                                                        'net_liquidation': 0, 'timestamp': 1544393719}), \
                                'S': SecuritySegment({'accrued_cash': 0, 'accrued_dividend': 0, \
                                                    'available_funds': 120.73, 'cash': 120.73, 'equity_with_loan': 1292.04, \
                                                    'excess_liquidity': 120.73, 'gross_position_value': 1171.31, \
                                                    'initial_margin_requirement': 1171.31, 'leverage': 0.91, \
                                                    'maintenance_margin_requirement': 1171.31, 'net_liquidation': 1292.04, \
                                                    'regt_equity': 1292.04, 'regt_margin': 585.66, 'sma': 1973.39, 'timestamp': 1545206069})\
                                }), \
    \
                        'market_values': defaultdict(<class 'tigeropen.trade.domain.account.MarketValue'>, \
                                {'CNH': MarketValue({'currency': 'CNH', 'net_liquidation': 0, 'cash_balance': 0, \
                                    '               stock_market_value': 0, 'option_market_value': 0, \
                                                    'warrant_value': 0, 'futures_pnl': 0, 'unrealized_pnl': 0, \
                                                    'realized_pnl': 0, 'exchange_rate': 0.14506, \
                                                    'net_dividend': 0, 'timestamp': 1544078822}), \
                                'HKD': MarketValue({'currency': 'HKD', 'net_liquidation': 0, 'cash_balance': 0, \
                                                    'stock_market_value': 0, 'option_market_value': 0, \
                                                    'warrant_value': 0, 'futures_pnl': 0, 'unrealized_pnl': 0, \
                                                    'realized_pnl': 0, 'exchange_rate': 0.12743, \
                                                    'net_dividend': 0, 'timestamp': 1550158606}), \
                                'USD': MarketValue({'currency': 'USD', 'net_liquidation': 1596.34, \
                                                    'cash_balance': 948.81, 'stock_market_value': 307, \
                                                    'option_market_value': 340.53, 'warrant_value': 0, \
                                                    'futures_pnl': 0, 'unrealized_pnl': -885.36, \
                                                    'realized_pnl': 0, 'exchange_rate': 1, \
                                                    'net_dividend': 0, 'timestamp': 1561519773})}\
                                )}\
                        )]

  

get\_positions 获取持仓数据

[](./accounts.md#get_positions-%E8%8E%B7%E5%8F%96%E6%8C%81%E4%BB%93%E6%95%B0%E6%8D%AE)

------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_positions(account=None, sec_type=SecurityType.STK, currency=Currency.ALL, market=Market.ALL, symbol=None, sub_accounts=None, expiry=None, strike=None, put_call=None)`

**说明**

获取账户的持仓信息

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | str | No  | 账户id，**如不指定, 则使用 client\_config 中的默认 account** |
| sec\_type | SecurityType | No  | 交易品种，包括 STK/OPT/FUT 等， 默认 STK，可以从 [tigeropen.common.consts.SecurityType](./appendix-enum.md#securitytype)<br> 下导入 |
| currency | Currency | No  | 币种，包括 ALL/USD/HKD/CNH 等，默认 ALL，可以从 [tigeropen.common.consts.Currency](./appendix-enum.md#currency)<br> 下导入 |
| market | Market | No  | 市场，包括 ALL/US/HK/CN 等，默认 ALL，可以从 [tigeropen.common.consts.Market](./appendix-enum.md#market)<br> 下导入 |
| symbol | str | No  | 证券代码 |
| sub\_accounts | `list[str]` | No  | 子账户列表 |
| expiry | str | No  | 过期日 (适用于期权)。 形式 'yyyyMMdd'，比如 '220121' |
| strike | float | No  | 行权价 (适用于期权)。比如 100.5 |
| put\_call | str | No  | 看涨或看跌 (适用于期权)。'PUT' 或 'CALL' |

**返回**

持仓对象列表， 类型：`list`

结构如下：

每个元素是一个 [Position 对象](./appendix-object-detail.md#position)
。Position(`tigeropen.trade.domain.position.Position`)对象有如下的属性：

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| account | str | 所属账户 |
| contract | Contract | 合约对象，[tigeropen.trade.domain.contract.Contract](./appendix-object-detail.md#contract) |
| quantity | int | 持仓数量 |
| average\_cost | float | 持仓成本 |
| average\_cost\_by\_average | float | 均价成本 |
| average\_cost\_of\_carry | float | A股方式计算平均持仓成本 (object) |
| market\_price | float | 最新价格（在交易时段，为市场价格。美股非交易时间，综合账号为盘后收盘价，环球账号为盘中收盘价） |
| market\_value | float | 市值  |
| realized\_pnl | float | 已实现盈亏 |
| realized\_pnl\_by\_average | float | 均价成本已实现盈亏 |
| unrealized\_pnl | float | 浮动盈亏 |
| unrealized\_pnl\_by\_average | float | 均价成本浮动盈亏 |
| unrealized\_pnl\_percent | float | 浮动盈亏率 |
| unrealized\_pnl\_percent\_by\_average | float | 均价成本浮动盈亏率 |
| salable\_qty | float | 可卖数量 |

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.common.consts import SecurityType, Currency, Market
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    positions = trade_client.get_positions(sec_type=SecurityType.STK, currency=Currency.ALL, market=Market.ALL)
    
    # 查看持仓属性
    print(len(positions))  # 持仓标的数
    # 第一个持仓合约
    position1 = positions[0]
    print(position1.contract.symbol)  # 合约代码， 如 AAPL，CL2303
    print(position1.contract.sec_type)  # 合约类型， 如 STK， FUT
    print(position1.contract.multiplier)  # 合约手数
    print(position1.average_cost)  # 持仓成本
    print(position1.quantity)  # 持仓数量
    # 第二个持仓标的
    position2 = positions[1]
    print(position2.unrealized_pnl)  # 浮动盈亏
    print(position2.market_value) # 持仓市值
    

**返回示例**

Text

    [contract: BABA/STK/USD, quantity: 1, average_cost: 178.99, market_price: 176.77,\
    contract: BIDU/STK/USD, quantity: 3, average_cost: 265.4633, market_price: 153.45,\
    contract: SPY/STK/USD, quantity: 7, average_cost: 284.9243, market_price: 284.97]

  

get\_analytics\_asset 获取历史资产分析

[](./accounts.md#get_analytics_asset-%E8%8E%B7%E5%8F%96%E5%8E%86%E5%8F%B2%E8%B5%84%E4%BA%A7%E5%88%86%E6%9E%90)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_analytics_asset(account=None, start_date=None, end_date=None, seg_type=None, currency=None, sub_account=None)`

**说明**

获取账户的历史资产分析

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | str | No  | 账户id，如不指定，则使用 client\_config 中的默认 account |
| start\_date | str | No  | 起始日期， 格式 yyyy-MM-dd，如 '2022-01-01'。如不传则使用end\_date往前30天的日期 |
| end\_date | str | No  | 截止日期， 格式 yyyy-MM-dd, 如 '2022-02-01'。如不传则使用当前日期 |
| seg\_type | SegmentType | No  | 账户划分类型，可选值有: SegmentType.SEC 代表证券; SegmentType.FUT 代表期货， 可以从 [tigeropen.common.consts.SegmentType](./appendix-enum.md#segmenttype)<br> 下导入 |
| currency | Currency | No  | 币种，包括 ALL/USD/HKD/CNH 等，可以从 [tigeropen.common.consts.Currency](./appendix-enum.md#currency)<br> 下导入 |
| sub\_account | str | No  | 子账户(仅适用于机构账户)，若传该字段，则返回该子账户的资产 |

**返回**

`dict`

summary 对应值为资产分析汇总，类型 dict，各字段含义：

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| pnl | float | 盈亏金额 |
| pnl\_percentage | float | 收益率 |
| annualized\_return | float | 年化收益率(推算) |
| over\_user\_percentage | float | 超过用户的百分比 overUserPercentage% |

history 对应值为历史资产列表，每项为dict，各字段含义：

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| date | int | 日期时间戳，单位毫秒 |
| pnl | float | 与上一日比的盈亏金额 |
| pnl\_percentage | float | 与上一日比的收益率 |
| asset | float | 总资产金额 |
| cash\_balance | float | 现金余额 |
| gross\_position\_value | float | 市值  |
| deposit | float | 入金金额 |
| withdrawal | float | 出金金额 |

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    result = trade_client.get_analytics_asset(start_date='2021-12-01', end_date='2021-12-07', seg_type=SegmentType.SEC)
    
    # 查看属性
    print(result['summary']['pnl'])  # 盈亏金额
    print(result['summary']['pnl_percentage'])  # 盈亏比例
    for data in result['history']:  # 历史资产
        print(data['date'])  # 历史资产日期
        print(data['pnl'])  # 盈亏

**返回示例**

    {'summary': {'pnl': 691.18, 'pnl_percentage': 0.0, 'annualized_return': 0.0, 'over_user_percentage': 0.0}, 
     'history': [{'date': 1638334800000, 'asset': 48827609.65, 'pnl': 0.0, 'pnl_percentage': 0.0, 'cash_balance': 48811698.59, 'gross_position_value': 15911.06, 'deposit': 0.0, 'withdrawal': 0.0, 'dt': '2021-12-01'},\
                 {'date': 1638421200000, 'asset': 48827687.69, 'pnl': 78.04, 'pnl_percentage': 0.0, 'cash_balance': 48811698.59, 'gross_position_value': 15989.1, 'deposit': 0.0, 'withdrawal': 0.0, 'dt': '2021-12-02'},\
                 {'date': 1638507600000, 'asset': 48827583.18, 'pnl': -26.47, 'pnl_percentage': 0.0, 'cash_balance': 48811698.59, 'gross_position_value': 15884.58, 'deposit': 0.0, 'withdrawal': 0.0, 'dt': '2021-12-03'}, \
                 {'date': 1638766800000, 'asset': 48827804.28, 'pnl': 194.63, 'pnl_percentage': 0.0, 'cash_balance': 48811698.59, 'gross_position_value': 16105.68, 'deposit': 0.0, 'withdrawal': 0.0, 'dt': '2021-12-06'}, \
                 {'date': 1638853200000, 'asset': 48828300.83, 'pnl': 691.18, 'pnl_percentage': 0.0, 'cash_balance': 48811723.0, 'gross_position_value': 16577.82, 'deposit': 0.0, 'withdrawal': 0.0, 'dt': '2021-12-07'}]}

  

get\_segment\_fund\_available 获取可转出资金

[](./accounts.md#get_segment_fund_available-%E8%8E%B7%E5%8F%96%E5%8F%AF%E8%BD%AC%E5%87%BA%E8%B5%84%E9%87%91)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_segment_fund_available(from_segment=None, currency=None)`

**说明**

获取账户对应Segment下的可转出资金(适用于综合或模拟账号)

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| from\_segment | str | Yes | 转出segment，FUT或SEC |
| currency | str | No  | 转出币种，USD或HKD |

**返回**

`List[SegmentFundAvailableItem]` 获取各Segment可转出资金金额列表。每一项为 `tigeropen.trade.domain.account.SegmentFundAvailableItem`

**`SegmentFundAvailableItem`说明：**

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| from\_segment | string | 转出segment，FUT或SEC |
| currency | string | 转出币种，USD或HKD |
| amount | float | 可转资金，单位：对应币种的元 |

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.common.consts import SecurityType, Currency, Market
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    available = trade_client.get_segment_fund_available(from_segment='SEC', currency='HKD')
    print(available)
    
    # 获取金额
    print(available[0].amount)

**返回示例**

    [SegmentFundAvailableItem({'from_segment': 'SEC', 'currency': 'HKD', 'amount': 718859.79})]

  

transfer\_segment\_fund 账号内部资金转账

[](./accounts.md#transfer_segment_fund-%E8%B4%A6%E5%8F%B7%E5%86%85%E9%83%A8%E8%B5%84%E9%87%91%E8%BD%AC%E8%B4%A6)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.transfer_segment_fund(from_segment=None, to_segment=None, amount=None, currency=None)`

**说明**

对账户在不同Segment下的资金转账，如证券Segment转期货Segment (适用于综合或模拟账号)

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| from\_segment | str | Yes | 转出segment，FUT或SEC |
| to\_segment | str | Yes | 转入segment，FUT或SEC，必须与from\_segment不同 |
| currency | str | Yes | 转出币种，USD或HKD |
| amount | float | Yes | 转账金额，单位：对应币种的元 |

**返回**

`tigeropen.trade.domain.account.SegmentFundItem`

**`SegmentFundItem`说明：**

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| id  | int | 转账记录ID |
| from\_segment | str | 转出segment，FUT或SEC |
| to\_segment | str | 转入segment，FUT或SEC |
| currency | str | 转出币种，USD或HKD |
| amount | float | 转账金额，单位：对应币种的元 |
| status | str | 状态(NEW/PROC/SUCC/FAIL/CANC) |
| status\_desc | str | 状态描述(已提交/处理中/已到账/转账失败/已取消) |
| message | str | 失败信息 |
| settled\_at | int | 到账时间戳 |
| updated\_at | int | 更新时间戳 |
| created\_at | int | 创建时间戳 |

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.common.consts import SecurityType, Currency, Market
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    res = trade_client.transfer_segment_fund(from_segment='SEC', to_segment='FUT', amount=100, currency='USD')
    print(res)

**返回示例**

    SegmentFundItem({'id': 30322815980011520, 'from_segment': 'SEC', 'to_segment': 'FUT', 'currency': 'USD',
     'amount': 100.0, 'status': 'NEW', 'status_desc': '已提交', 'message': None, 'settled_at': None,
      'updated_at': 1680243926131, 'created_at': 1680243926131})
    

  

cancel\_segment\_fund 取消账户内部资金转账

[](./accounts.md#cancel_segment_fund-%E5%8F%96%E6%B6%88%E8%B4%A6%E6%88%B7%E5%86%85%E9%83%A8%E8%B5%84%E9%87%91%E8%BD%AC%E8%B4%A6)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.cancel_segment_fund(id=None)`

**说明**

取消提交的资金转账 (适用于综合或模拟账号) 如果转账已成功，则无法取消，可以交换Segment反向转回来。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| id  | int | Yes | 转账记录id |

**返回** `tigeropen.trade.domain.account.SegmentFundItem`

**`SegmentFundItem`说明：**

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| id  | int | 转账记录ID |
| from\_segment | str | 转出segment，FUT或SEC |
| to\_segment | str | 转入segment，FUT或SEC |
| currency | str | 转出币种，USD或HKD |
| amount | float | 转账金额，单位：对应币种的元 |
| status | str | 状态(NEW/PROC/SUCC/FAIL/CANC) |
| status\_desc | str | 状态描述(已提交/处理中/已到账/转账失败/已取消) |
| message | str | 失败信息 |
| settled\_at | int | 到账时间戳 |
| updated\_at | int | 更新时间戳 |
| created\_at | int | 创建时间戳 |

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.common.consts import SecurityType, Currency, Market
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    res = trade_client.cancel_segment_fund(id='请求转账的id')
    print(res)

**返回示例**

    SegmentFundItem({'id': 30322815980011520, 'from_segment': 'SEC', 'to_segment': 'FUT', 'currency': 'USD',
     'amount': 100.0, 'status': 'CANC', 'message': None, 'settled_at': None, 'updated_at': 1680243926131, 'created_at': 1680243926131})
    

  

get\_segment\_fund\_history 账号内部资金转账历史记录

[](./accounts.md#get_segment_fund_history-%E8%B4%A6%E5%8F%B7%E5%86%85%E9%83%A8%E8%B5%84%E9%87%91%E8%BD%AC%E8%B4%A6%E5%8E%86%E5%8F%B2%E8%AE%B0%E5%BD%95)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_segment_fund_history(limit=None)`

**说明**

查询账号不同Segment间的历史转账记录。按时间倒序排列 (适用于综合或模拟账号)

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| limit | int | No  | 返回最近转账记录数。默认为100，最大500 |

**返回** `tigeropen.trade.domain.account.SegmentFundItem`

**`SegmentFundItem`说明：**

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| id  | int | 转账记录ID |
| from\_segment | str | 转出segment，FUT或SEC |
| to\_segment | str | 转入segment，FUT或SEC |
| currency | str | 转出币种，USD或HKD |
| amount | float | 转账金额，单位：对应币种的元 |
| status | str | 状态 (NEW/PROC/SUCC/FAIL/CANC) |
| status\_desc | str | 状态描述(已提交/处理中/已到账/转账失败/已取消) |
| message | str | 失败信息 |
| settled\_at | int | 到账时间戳 |
| updated\_at | int | 更新时间戳 |
| created\_at | int | 创建时间戳 |

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.common.consts import SecurityType, Currency, Market
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    history = trade_client.get_segment_fund_history()
    print(history)
    

**返回示例**

    [SegmentFundItem({'id': 16256385456537600, 'from_segment': 'SEC', 'to_segment': 'FUT', 'currency': 'USD', \
    'amount': 100000.0, 'status': 'SUCC', 'status_desc': '已到账', 'message': None,\
     'settled_at': 1572925581000, 'updated_at': 1572925578000, 'created_at': 1572925578000}), \
     SegmentFundItem({'id': 16256377863667712, 'from_segment': 'SEC', 'to_segment': 'FUT', 'currency': 'USD', \
     'amount': 100000.0, 'status': 'SUCC', 'status_desc': '已到账', 'message': None,\
      'settled_at': 1572925525000, 'updated_at': 1572925520000, 'created_at': 1572925520000}), \
      SegmentFundItem({'id': 15554843128627200, 'from_segment': 'SEC', 'to_segment': 'FUT', 'currency': 'USD',\
       'amount': 100000.0, 'status': 'SUCC', 'status_desc': '已到账', 'message': None, 'settled_at': 1567573240000, \
       'updated_at': 1567573235000, 'created_at': 1567573235000}), \
       SegmentFundItem({'id': 14039478715026432, 'from_segment': 'SEC', 'to_segment': 'FUT', 'currency': 'USD', 'amount': 10000.0, 'status': 'SUCC', 'status_desc': '已到账', 'message': None, 'settled_at': 1556011922000, 'updated_at': 1556011922000, 'created_at': 1556011922000})]

  

get\_estimate\_tradable\_quantity 获取最大可交易数量

[](./accounts.md#get_estimate_tradable_quantity-%E8%8E%B7%E5%8F%96%E6%9C%80%E5%A4%A7%E5%8F%AF%E4%BA%A4%E6%98%93%E6%95%B0%E9%87%8F)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_estimate_tradable_quantity(order, seg_type=None)`

**说明**

查询账户下的某个标的最大可买可卖数量，支持查询股票、期权，暂不支持期货。

**参数**

`Order`对象 ([`tigeropen.trade.domain.order.Order`](./appendix-object-detail.md#order)
)

可用 `tigeropen.common.util.order_utils` 下的工具函数，如 limit\_order(), market\_order()，根据您需要的具体订单类型和参数，在本地生成订单对象。创建方法详见[Order对象-构建方法](./appendix-object-detail.md#order)
部分

只支持限价单或止损单

**返回** `tigeropen.trade.domain.position.TradableQuantityItem` 对象，属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| tradable\_quantity | float | 现金可买可卖数量 (如action为buy，返回为可买数量，反之为可卖数量) |
| financing\_quantity | float | 融资融券可买可卖数量 (不适用于现金账户) |
| position\_quantity | float | 持仓数量 |
| tradable\_position\_quantity | float | 持仓可交易数量 |

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.common.consts import SecurityType, Currency, Market
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    
    contract = stock_contract(symbol='MSFT', currency='USD')
    order = limit_order(account=client_config.account, contract=contract, action='BUY', limit_price=50, quantity=1)
    res = trade_client.get_estimate_tradable_quantity(order)
    print(res)

**返回示例**

    TradableQuantityItem<{'tradable_quantity': 28921.0, 'financing_quantity': 52657.0, 'position_quantity': 0.0, 'tradable_position_quantity': 0}>

  

get\_funding\_history 获取出入金记录

[](./accounts.md#get_funding_history-%E8%8E%B7%E5%8F%96%E5%87%BA%E5%85%A5%E9%87%91%E8%AE%B0%E5%BD%95)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_funding_history(seg_type=None)`

**说明**

查询账户的出入金记录。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| seg\_type | SegmentType | No  | 分段类型 |

**返回** pandas.DataFrame, 字段如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| id  | long | ID  |
| refId | string | 关联业务ID |
| type | int | 资金类型(1：入金；3：出金；20：出金费用；21：出金退款；22：出金失败-退款；23：出金费用-退款) |
| type\_desc | string | 资金类型描述 |
| currency | string | 币种  |
| amount | double | 金额  |
| business\_date | string | 业务日期 |
| completed\_status | bool | 是否结束 |
| created\_at | long | 创建时间戳 |
| updated\_at | long | 更新时间戳 |

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.common.consts import SecurityType, Currency, Market
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    
    res = trade_client.get_funding_history()
    print(res)

**返回示例**

JSON

            id           ref_id  type type_desc currency   amount business_date  completed_status     updated_at     created_at
    0  3000000               26     1        入金      USD   484.88    2017/08/24              True  1503574430000  1503574430000
    1  3000001              123     1        入金      USD  2000.00    2017/12/15              True  1513308908000  1513308908000

  

get\_fund\_details 获取资金明细

[](./accounts.md#get_fund_details-%E8%8E%B7%E5%8F%96%E8%B5%84%E9%87%91%E6%98%8E%E7%BB%86)

-------------------------------------------------------------------------------------------------------------------------------------------------

`TradeClient.get_fund_details(self, seg_types, account=None, fund_type=None, currency=None, start=0, limit=None, start_date=None, end_date=None, secret_key=None, lang=None):`

**说明**

获取资金明细

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| seg\_types | `list[str]` | Yes | 账户划分类型, 可选值有: SegmentType.SEC 代表证券; SegmentType.FUT 代表期货， 可以从 [tigeropen.common.consts.SegmentType](./appendix-enum.md#segmenttype)<br> 下导入。 可用值：'SEC', 'FUT', 'FUND' |
| account | str | No  | 账户id，如不传，使用默认账户。仅支持综合账户 |
| fund\_type | int | No  | 资金类型，包括：ALL 全部、DEPOSIT\_WITHDRAW 出入金、TRADE 交易、FEE 费用、FUNDS\_TRANSFER 资金划拨、FOREX 货币兑换、CORPORATE\_ACTION 公司行动、ACTIVITY\_AWARD 活动、OTHER 其他. 默认 ALL |
| currency | Currency | No  | 币种，包括 USD/HKD/CNH 等, 可以从 [tigeropen.common.consts.Currency](./appendix-enum.md#currency)<br> 下导入 |
| start | int | No  | 起始序号，从 0 开始，比如每页设置limit 50, 前两页共返回了100条，则请求第3页时，start需要传 100, 即从第101条继续查询 |
| limit | int | No  | 最大返回条数，默认 50，最大 100 |
| start\_date | str | No  | 起始日期，格式 'yyyy-MM-dd' |
| end\_date | str | No  | 截止日期，格式 'yyyy-MM-dd' |
| secret\_key | str | No  | 机构密钥 (个人开发者无需填写) |
| lang | [Language](./appendix-enum.md#language) | No  | 支持的语言，可以使用 tigeropen.common.consts.Language 下提供的枚举常量，如 Language.zh\_CN，**默认是Language.en\_US**，参见枚举参数部分 |

**返回** pandas.DataFrame, 字段如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| id  | int | 记录ID |
| desc | str | 描述  |
| currency | str | 币种  |
| seg\_type | str | SegmentType |
| type | str | 资金类型 |
| amount | float | 金额  |
| business\_date | str | 老虎定义的业务日期，所有市场同一交易日的资金变动会记录到同一业务日期中 |
| updated\_at | int | 流水更新时间戳 |
| page | int | 当前页码 |
| limit | int | 每页记录数量 |
| item\_count | int | 总记录数量 |
| page\_count | int | 总页数 |
| timestamp | int | 时间戳 |
| contract\_name | str | 合约名称 |

**示例**

Python

    from tigeropen.trade.trade_client import TradeClient
    from tigeropen.common.consts import SecurityType, Currency, Market
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    trade_client = TradeClient(client_config)
    result = trade_client.get_fund_details(
    	seg_types=[SegmentType.SEC, SegmentType.FUT],
    	start= 0,
    	limit = 50,
    	start_date='2025-03-28',
    	end_date='2025-04-04',
    	fund_type='ALL',
    	# currency = Currency.USD,
    	# lang = Language.en_US,
    )
    print(result)
    
    
    # 分页获取全部
    start = 0
    limit = 50
    final_result = pd.DataFrame()
    while True:
        res = trade_client.get_fund_details(seg_types=[SegmentType.SEC, SegmentType.FUT], start=start, limit=limit,start_date='2025-01-01', end_date='2025-05-01')
        if res.empty:
            break
        start += limit
        final_result = pd.concat([final_result, res], ignore_index=True)
    print(final_result)
    	

**返回示例**

JSON

                      id currency     type          desc contract_name seg_type  amount business_date     updated_at  page  limit  item_count  page_count      timestamp
    0  24924145889681231      USD  公司行动手续费  VFS-DIVIDEND                    SEC   -0.10    2025-04-04  1743762173000     1     50           8           1  1745581047814
    1  24924145889681229      USD       分红  VFS-DIVIDEND                    SEC    0.10    2025-04-04  1743762173000     1     50           8           1  1745581047814
    2  24924145889681217      USD  公司行动手续费  VFS-DIVIDEND                    SEC    0.09    2025-04-04  1743762171000     1     50           8           1  1745581047814
    3  24924145889681215      USD      分红税  VFS-DIVIDEND                    SEC    0.01    2025-04-04  1743762171000     1     50           8           1  1745581047814
    4  24924145889681213      USD       分红  VFS-DIVIDEND                    SEC   -0.10    2025-04-04  1743762171000     1     50           8           1  1745581047814
    5  24924145889678880      USD  公司行动手续费  VFS-DIVIDEND                    SEC   -0.09    2025-03-28  1743392460000     1     50           8           1  1745581047814
    6  24924145889678878      USD      分红税  VFS-DIVIDEND                    SEC   -0.01    2025-03-28  1743392460000     1     50           8           1  1745581047814
    7  24924145889678876      USD       分红  VFS-DIVIDEND                    SEC    0.10    2025-03-28  1743392460000     1     50           8           1  1745581047814
