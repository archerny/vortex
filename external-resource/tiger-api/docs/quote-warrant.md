# 窝轮牛熊证

轮证筛选器

[](./quote-warrant.md#%E8%BD%AE%E8%AF%81%E7%AD%9B%E9%80%89%E5%99%A8)

--------------------------------------------------------------------------------------------------------

`QuoteClient.get_warrant_filter(self, symbol, page=None, page_size=None, sort_field_name=None, sort_dir=None, filter_params=None)`

**说明**

获取窝轮牛熊证行情列表数据，支持按不同字段排序和筛选轮证。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbol | str | Yes | 正股股票代码 |
| page | int | No  | 页码，从0开始，默认为0 |
| page\_size | int | No  | 每页数量，默认50 |
| sort\_field\_name | str | No  | 排序字段，默认expireDate（参照返回对象WarrantItem的字段） |
| sort\_dir | tigeropen.common.consts.SortDirection | No  | 排序顺序，枚举 ASC/DESC ，默认 ASC |
| filter\_params | tigeropen.quote.request.model.WarrantFilterParams | No  | 过滤参数 |

其中过滤参数 filter\_params 的字段如下:

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| issuer\_name | str | No  | 发行商（参照返回FilterBounds实例的issuerName字段），默认全部 |
| expire\_ym | str | No  | 到期日，格式为 yyyy-MM |
| state | int | No  | 状态，0 全部, 1 正常, 2 终止交易, 3 等待上市，默认0 |
| warrant\_type | `set[int]` | No  | 类型WarrantType（1:Call, 2: Put, 3: Bull, 4: Bear, 0: All），默认全部 |
| in\_out\_price | `set[int]` | No  | 1：价内（包含平值），-1：价外 |
| lot\_size | `set[int]` | No  | 每手股数 |
| entitlement\_ratio | `set[float]` | No  | 换股比率 |
| strike | `tuple[float, float]` | No  | 行权价区间 |
| effective\_leverage | `tuple[float, float]` | No  | 有效杠杆区间 |
| leverage\_ratio | `tuple[float, float]` | No  | 杠杆比例区间 |
| call\_price | `tuple[float, float]` | No  | 回收价区间 |
| volume | `tuple[int, int]` | No  | 成交量区间 |
| premium | `tuple[float, float]` | No  | 溢价区间 |
| outstanding\_ratio | `tuple[float, float]` | No  | 街货比区间 |
| implied\_volatility | `tuple[float, float]` | No  | 隐含波动率区间 |

单个值的字段可通过 `set_xxx()` 来设置值，set类型的字段可通过 `add_xxx()` 添加值，tuple类型的字段可通过 `set_xxx_range()` 设置值。 参考下方示例

**返回**

`tigeropen.quote.domain.filter.WarrantFilterItem`

结构如下：

Python

    class WarrantFilterItem:
        def __init__(self, items=None, page=None, total_page=None, total_count=None, bounds=None):
            self.items = items
            self.page = page
            self.total_page = total_page
            self.total_count = total_count
            self.bounds = bounds

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| page | int | 页码  |
| totalPage | int | 总页数 |
| totalCount | int | 数据总数 |
| bounds | tigeropen.quote.domain.filter.WarrantFilterBounds | 请求可设置的过滤条件，说明见下文 |
| items | pandas.DataFrame | 字段见下方说明 |

`WarrantFilterBounds`对象结构：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| issuer\_name | `list[str]` | 发行商名称 |
| expire\_date | `list[str]` | 到期日 |
| lot\_size | `list[int]` | 每手股数 |
| entitlement\_ratio | `list<double>` | 换股比率 |
| leverage\_ratio | `dict` | 杠杆比率范围 |
| strike | `dict` | 行权价范围 |
| premium | `dict` | 溢价范围 |
| outstanding\_ratio | `dict` | 街货比范围 |
| implied\_volatility | `dict` | 隐含波动率范围 |
| effective\_leverage | `dict` | 有效杠杆范围 |
| call\_price | `dict` | 回收价范围 |

`items` DataFrame 字段说明：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| symbol | str | 标的股票代码 |
| name | str | 标的名称 |
| type | warrant\_type | 类型，1：认购，2：认沽，3：牛证，4：熊证 |
| sec\_type | str | 合约类型，窝轮：war/牛熊证：iopt |
| market | str | 市场，hk |
| entitlement\_ratio | float | 换股比率 |
| entitlement\_price | float | 换股价 |
| premium | float | 溢价  |
| breakeven\_point | float | 到期盈亏平衡点 |
| call\_price | float | 回收价（仅牛熊证） |
| before\_call\_level | float | 距回收价（百分比，比如**0\_196875**，含义为\_19\_6875%） |
| expire\_date | str | 到期日 |
| last\_trading\_date | str | 最后交易日 |
| state | warrant\_state | 状态，1\_正常, 2\_终止交易, 3\_等待上市 |
| change\_rate | float | 涨跌幅 |
| change | float | 涨跌额 |
| latest\_price | float | 最新价 |
| volume | long | 成交量 |
| outstanding\_ratio | float | 街货比 |
| lot\_size | int | 每手股数 |
| strike | float | 行权价 |
| in\_out\_price | float | 价内（大于\_0）/价外（小于\_0） |
| delta | float | 对冲值 |
| leverage\_ratio | float | 杠杆比率 |
| effective\_leverage | float | 有效杠杆 |
| implied\_volatility | float | 隐含波动率 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.quote.request.model import WarrantFilterParams
    
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    quote_client = QuoteClient(client_config)
    
    filter_params = WarrantFilterParams()
    filter_params.set_issuer_name('摩利')
    filter_params.set_expire_ym('2024-06')
    # 0 All, 1 Normal, 2 Terminate Trades, 3 Waiting to be listed
    filter_params.set_state(1)
    filter_params.add_lot_size(1000)
    filter_params.add_lot_size(5000)
    # -1:out the money, 1: in the money
    filter_params.add_in_out_price(1)
    filter_params.add_in_out_price(-1)
    # 1:Call, 2: Put, 3: Bull,4: Bear, 0: All
    filter_params.add_warrant_type(2)
    filter_params.add_warrant_type(4)
    filter_params.set_premium_range(0, 1)
    filter_params.set_strike_range(100, 500)
    filter_params.set_implied_volatility_range(1, 100)
    result = quote_client.get_warrant_filter('00700', page_size=10,
                                             sort_field_name='implied_volatility',
                                             sort_dir=SortDirection.DESC,
                                             filter_params=filter_params)
    print(result)
    

**返回示例**

    WarrantFilterItem({
    'items':   
        symbol        name type sec_type market  entitlement_ratio  entitlement_price   premium  breakeven_point expire_date last_trading_date   state  change_rate  change  latest_price  volume  amount  outstanding_ratio  lot_size   strike  in_out_price     delta  leverage_ratio  effective_leverage  implied_volatility
        0  27062  腾讯摩利三十沽A.P  Put      WAR     HK             47.483           1.377007  0.605231       148.669993  2023-10-20        2023-10-16  Normal     0.035714   0.001         0.029       0     0.0              0.002      5000  150.047       1.50988 -0.030389      273.491711           -8.311172              69.847, 
    'page': 0, 'total_page': 1, 'total_count': 1, 
    'bounds': FilterBounds({
        'issuer_name': ['东亚', '法巴', '法兴', '高盛', '国君', '海通', '花旗', '汇丰', '麦银', '摩利', '摩通', '瑞通', '瑞信', '瑞银', '星展', '中银'], 
        'expire_date': ['2026-01', '2025-12', '2025-09', '2025-08', '2024-12', '2024-07', '2024-06', '2024-04', '2024-03', '2024-02', '2024-01', '2023-12', '2023-11', '2023-10', '2023-09', '2023-08', '2023-07', '2023-06', '2023-05', '2023-04'], 
        'lot_size': [1000, 5000, 10000, 50000], 
        'entitlement_ratio': [1.053, 47.483, 50.0, 92.166, 94.967, 100.0, 460.829, 474.834, 485.437, 500.0], 
        'leverage_ratio': {'min': 1.391437, 'max': 3056.79342}, 
        'strike': {'min': 111.301, 'max': 717.11}, 
        'premium': {'min': -0.314923, 'max': 0.908085}, 
        'outstanding_ratio': {'min': 0.0, 'max': 1.0}, 
        'implied_volatility': {'min': 0.0, 'max': 131.085}, 
        'effective_leverage': {'min': -46.321801, 'max': 20.189945}, 
        'call_price': {'min': 113.96, 'max': 520.0}})})
    

* * *

获取轮证行情

[](./quote-warrant.md#%E8%8E%B7%E5%8F%96%E8%BD%AE%E8%AF%81%E8%A1%8C%E6%83%85)

------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_warrant_briefs(symbols)`

**说明**

获取窝轮牛熊证实时行情

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | yes | 轮证标的代码,上限为50个 |

**返回**

`pandas.DataFrame`

结构如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | str | 标的代码 |
| name | str | 标的名称 |
| exchange | str | 交易所 |
| market | str | 市场  |
| sec\_type | str | 合约类型 |
| currency | str | 币种  |
| expiry | str | 到期日 yyyy\_mm\_dd |
| strike | str | 行权价 |
| right | str | 方向 (put/call) |
| multiplier | float | 每手数量 |
| last\_trading\_date | int | 最后交易日时间戳 |
| entitlement\_ratio | float | 换股比率 |
| entitlement\_price | float | 换股价 |
| min\_tick | float | 股价最小变动单位 |
| listing\_date | int | 上市日期的时间戳 |
| call\_price | float | 回收价\*（仅牛熊证）\* |
| halted | halted\_status | 是否停牌。 0: 正常\_3: 停牌\_4: 退市\_ |
| underlying\_symbol | str | 底层资产代码 |
| timestamp | int | 时间戳 |
| latest\_price | float | 最新价格 |
| pre\_close | float | 前一交易日收盘价 |
| open | float | 开盘价 |
| high | float | 最高价 |
| low | float | 最低价 |
| volume | int | 成交量 |
| amount | float | 成交额 |
| premium | float | 溢价  |
| outstanding\_ratio | float | 街货比 |
| implied\_volatility | float | 隐含波动率（仅窝轮支持） |
| in\_out\_price | float | 价内/价外 |
| delta | float | 对冲值（仅窝轮支持） |
| leverage\_ratio | float | 杠杆率 |
| breakeven\_point | float | 到期盈亏平衡点 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    briefs = quote_client.get_warrant_briefs(['15792', '58603'])
    print(briefs)

**返回示例**

    symbol        name exchange market sec_type currency      expiry   strike right  multiplier   premium \
     58603  恒指瑞通五三熊R.P  SEHK   HK     IOPT      HKD  2025-03-28  16700.0   PUT     10000.0     \ 
     15792  招行摩通二十沽A.P  SEHK   HK      WAR      HKD  2022-10-31    39.39   PUT      5000.0     \                      
    
    last_trading_date  entitlement_ratio  entitlement_price  min_tick   listing_date  call_price  halted \
        1743004800000            10000.0            1890.00     0.001  1668009600000     16600.0  Normal 
        1666627200000               10.0               2.28     0.001  1651075200000         NaN  Normal 
    
    underlying_symbol      timestamp  latest_price  pre_close   open   high    low    volume     amount \
                  HSI  1681200546054         0.189      0.175  0.188  0.189  0.170    444000    83378.0 
                03968  1681200546030         0.228      0.223  0.227  0.228  0.216  21572000  4795879.0 
    
     outstanding_ratio  in_out_price  leverage_ratio  breakeven_point  implied_volatility   delta
         0.277040             0.0375      0.226661       10.838751         14810.00       NaN     NaN  
         0.058122             0.0011      0.000254       17.280702            37.11    130.577 -0.9844
    
    
