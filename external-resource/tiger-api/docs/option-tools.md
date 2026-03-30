# 期权工具

### 

期权计算器

[](./option-tools.md#%E6%9C%9F%E6%9D%83%E8%AE%A1%E7%AE%97%E5%99%A8)

在 sdk 代码路径下 `tigeropen/examples/option_helpers/helpers.py` ， 提供了期权计算工具，可用于期权希腊值计算、期权价格计算、隐含波动率计算。 相关算法基于 `quantlib` 库，使用前需要先安装：`pip install quantlib==1.40`

#### 

使用方式1，在代码中引用

[](./option-tools.md#%E4%BD%BF%E7%94%A8%E6%96%B9%E5%BC%8F1%E5%9C%A8%E4%BB%A3%E7%A0%81%E4%B8%AD%E5%BC%95%E7%94%A8)

`FDAmericanDividendOptionHelper` 为美式期权计算类(包括美股期权,港股期权, ETF期权都使用此类) `FDEuropeanDividendOptionHelper` 为欧式期权计算类(指数期权使用此类)

Python

    import quantlib as ql
    from tigeropen.examples.option_helpers.helpers import FDAmericanDividendOptionHelper
    
    
    # 根据期权价格计算隐含波动率：
    ql.Settings.instance().evaluationDate = ql.Date(19, 4, 2022)
    helper = FDAmericanDividendOptionHelper(option_type=ql.Option.Call,
                                            underlying=985,
                                            strike=990,
                                            risk_free_rate=0.017,
                                            dividend_rate=0,
                                            volatility=0, # 隐含波动率临时设置为0
                                            settlement_date=ql.Date(14, 4, 2022),
                                            expiration_date=ql.Date(22, 4, 2022))
    
    # 计算隐含波动率，参数为期权价格，可用盘口价(ask,bid)计算. (ask + bid) / 2
    volatility = helper.implied_volatility(33.6148)
    helper.update_implied_volatility(volatility)
    
    print(f'implied volatility:{volatility}')
    print(f'value:{helper.NPV()}')
    print(f'delta:{helper.delta()}')
    print(f'gamma:{helper.gamma()}')
    print(f'theta:{helper.theta()}')
    print(f'vega:{helper.vega()}')
    print(f'rho:{helper.rho()}')
    
    
    
    
    # 直接使用隐含波动率计算期权价格：
    ql.Settings.instance().evaluationDate = ql.Date(19, 4, 2022)
    helper = FDAmericanDividendOptionHelper(option_type=ql.Option.Call,  # PUT/CALL
                                            underlying=985,  # 结算日股价
                                            strike=990,      # 行权价
                                            risk_free_rate=0.017,  # 无风险利率
                                            dividend_rate=0,       # 股息率
                                            volatility=0.6153,     # 隐含波动率
                                            settlement_date=ql.Date(14, 4, 2022),  # 结算日期
                                            expiration_date=ql.Date(22, 4, 2022))  # 期权到期日
    print(f'value:{helper.NPV()}')
    print(f'delta:{helper.delta()}')
    print(f'gamma:{helper.gamma()}')
    print(f'theta:{helper.theta()}')
    print(f'vega:{helper.vega()}')
    print(f'rho:{helper.rho()}')
    
    

#### 

使用方式2，作为脚本命令调用

[](./option-tools.md#%E4%BD%BF%E7%94%A8%E6%96%B9%E5%BC%8F2%E4%BD%9C%E4%B8%BA%E8%84%9A%E6%9C%AC%E5%91%BD%E4%BB%A4%E8%B0%83%E7%94%A8)

假设将 `tigeropen/examples/option_helpers/helpers.py` 保存在当前目录

Shell

    # 计算期权价格
    python helpers.py -t PUT -e '2022-05-20' -s 2022-04-24 -p 215 -u 215.52 -r 0.0078 -v 0.5919
    
    # 根据期权价格计算隐含波动率. -n 指定期权价格
    python helpers.py -t CALL -e '2022-04-22' -s 2022-04-14 -p 990 -u 985 -r 0.017 -n 33.6148
    # 根据期权盘口数据计算隐含波动率.(使用 ask bid 的均值作为期权价格)
    python helpers.py -t CALL -e '2022-04-22' -s 2022-04-14 -p 990 -u 985 -r 0.017 -a 35 -b 36
    
    # 查看命令帮助
    python helpers.py -h
    

  

### 

期权指标计算工具

[](./option-tools.md#%E6%9C%9F%E6%9D%83%E6%8C%87%E6%A0%87%E8%AE%A1%E7%AE%97%E5%B7%A5%E5%85%B7)

本工具封装好了 sdk 的请求，直接传入期权代码，即可请求并计算期权希腊值，买入盈利概率，卖出年化等指标。

代码路径 `tigeropen/examples/option_helpers/util.py`

示例：

    import quantlib as ql
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    from tigeropen.examples.option_helpers.util import OptionUtil
    
    client_config = TigerOpenClientConfig(props_path='.config/')
    quote_client = QuoteClient(client_config)
    trade_client = TradeClient(client_config)
    
    option_util = OptionUtil(quote_client, trade_client)
    
    # Calculate metrics for specific options
    identifiers = ['TSLA 260220C00385000']
    
    # Example 1: Return as DataFrame
    print("Example 1: Return as DataFrame")
    metrics_df = option_util.get_option_metrics(identifiers, return_type='dataframe')
    print(f"\n{metrics_df}")
    
    # Example 2: Return as List of OptionMetric objects
    print("Example 2: Return as List of OptionMetric objects")
    metrics_list = option_util.get_option_metrics(identifiers, return_type='list')
    for metric in metrics_list:
        print(metric)
        print(f"  Greeks: delta={metric.delta}, gamma={metric.gamma}, "
                        f"theta={metric.theta}, vega={metric.vega}")
        print(f"  Risk: implied_vol={metric.implied_vol}, leverage={metric.leverage_ratio}")
        print(f"  Probability: profit_prob={metric.profit_probability}")
    
    
    
