# 基金

get\_fund\_symbols 获取基金代码列表

[](./quote-fund.md#get_fund_symbols-%E8%8E%B7%E5%8F%96%E5%9F%BA%E9%87%91%E4%BB%A3%E7%A0%81%E5%88%97%E8%A1%A8)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_fund_symbols()`

**说明**

获取所有基金代码列表

**参数**

无

**返回**

`list[str]`

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    result = quote_client.get_fund_symbols()
    print(result)

**返回示例**

    [\
            "IE00B11XZ988.USD",\
            "IE00B7SZLL34.SGD",\
            "LU0790902711.USD",\
            "LU0476943708.HKD",\
            "LU0098860793.USD",\
            "SG9999014039.USD"\
    ]

* * *

get\_fund\_contracts 获取基金合约信息

[](./quote-fund.md#get_fund_contracts-%E8%8E%B7%E5%8F%96%E5%9F%BA%E9%87%91%E5%90%88%E7%BA%A6%E4%BF%A1%E6%81%AF)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_fund_contracts(symbols)`

**说明**

批量获取基金的合约信息

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | Yes | 基金代码列表 如："IE00B11XZ988.USD" / "LU0790902711.USD" |

**返回**

`pandas.DataFrame`

其中数据项字段如下

| 名称  | 示例  | 说明  |
| --- | --- | --- |
| symbol | IE00B464Q616.USD | 基金代码，后缀为货币 |
| name | ASIA STRATEGIC INTEREST BOND FUND "E" (USD) INC MONTHLY | 基金名称 |
| company\_name | PIMCO Global Advisors (Ireland) Limited | 基金名称 |
| market | US  | 市场 /US/HK/CN |
| sec\_type | FUND | 合约类别 |
| currency | USD | USD/HKD/CNH |
| tradeable | true | 是否可交易 |
| sub\_type | Fixed Income | 子类别 |
| dividend\_type | INC | 分红类型 |
| tiger\_vault | false | 是否为老虎钱袋子 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    result = quote_client.get_fund_contracts(symbols=['IE00B11XZ988.USD'])
    print(result)

**返回示例**

                 symbol                                name         company_name    market  \
    0  IE00B11XZ988.USD             PIMCO总回报债券基金 E Acc   太平洋全球顾问有限公司        MF   \  
    1  LU0476943708.HKD        邓普顿环球总收益基金A (Mdis)HKD         富兰克林邓普顿投资      MF  \   
    2  SG9999017602.SGD  United Asian Bond Fund A Acc SGD-H          大华资产管理公司      MF   \       
    
    sec_type     currency  tradeable      sub_type dividend_type  tiger_vault
        FUND          USD       True  Fixed Income           ACC        False
        FUND          HKD       True  Fixed Income           INC        False
        FUND          SGD       True  Fixed Income           ACC        False

* * *

get\_fund\_quote 获取基金最新行情

[](./quote-fund.md#get_fund_quote-%E8%8E%B7%E5%8F%96%E5%9F%BA%E9%87%91%E6%9C%80%E6%96%B0%E8%A1%8C%E6%83%85)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_fund_quote(symbols: list[str])`

**说明**

获取基金最新行情

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | yes | 基金代码,上限为500个 |

**返回**

`pandas.DataFrame`

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | str | 标的代码 |
| close | float | 收市价 |
| timestamp | int | 毫秒单位的时间戳 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    
    quote_client = QuoteClient(client_config)
    result = quote_client.get_fund_quote(['IE00B11XZ988.USD', 'LU0476943708.HKD'])
    print(result)
    print(result.iloc[0]['close'])
    # to python type
    close = float(result.iloc[0]['close'])

**返回示例**

                 symbol  close      timestamp
    0  IE00B11XZ988.USD  25.10  1691596800000
    1  LU0476943708.HKD   5.22  1691596800000

* * *

get\_fund\_history\_quote 获取基金历史行情

[](./quote-fund.md#get_fund_history_quote-%E8%8E%B7%E5%8F%96%E5%9F%BA%E9%87%91%E5%8E%86%E5%8F%B2%E8%A1%8C%E6%83%85)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_fund_history_quote(symbols: list[str], begin_time: int, end_time: int = None, limit: int = None)`

**说明**

获取基金历史行情

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| symbols | `list[str]` | Yes | 基金代码,上限为500个 |
| begin\_time | int | Yes | 开始时间戳，单位:毫秒(ms) |
| end\_time | int | Yes | 结束时间戳，单位:毫秒(ms) |
| limit | int | No  | 请求返回单个标的数据量 |

**返回**

`pandas.DataFrame`

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| symbol | str | 标的代码 |
| nav | float | 净值  |
| time | int | 时间戳 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    result = quote_client.get_fund_history_quote(['LU0476943708.HKD', 'LU0476943708.HKD'], begin_time=1691337600000, end_time=1691596800000)
    print(result)
    if not result.empty:
        print(result.loc[result['symbol']=='LU0476943708.HKD'].iloc[0]['nav'])
    

**返回示例**

                   symbol           time   nav
    0    LU0476943708.HKD  1691596800000  5.22
    1    LU0476943708.HKD  1691510400000  5.22
    2    LU0476943708.HKD  1691424000000  5.20
    3    LU0476943708.HKD  1691337600000  5.25
    
