# 期货

get\_future\_exchanges 获取期货交易所列表

[](./quote-future.md#get_future_exchanges-%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E4%BA%A4%E6%98%93%E6%89%80%E5%88%97%E8%A1%A8)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_future_exchanges(sec_type=SecurityType.FUT, lang=None)`

**说明**

获取期货交易所列表

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md)

**参数**

| 参量名 | 类型  | 描述  |
| --- | --- | --- |
| sec\_type | SecurityType | 使用 tigeropen.common.consts.SecurityType 下的枚举，默认 SecurityType.FUT，FUT表示期货，FOP表示期货期权 |
| lang | Language | zh\_CN,zh\_TW,en\_US |

**返回**

`pandas.DataFrame`

结构如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| code | str | 交易所代码 |
| name | str | 交易所名称 |
| zone | str | 交易所所在时区 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    exchanges = quote_client.get_future_exchanges()
    # 打印第一个交易所的代码，名称，时区
    exchange1 = exchanges.iloc[0]
    print(f'code: {exchange1.code}, name: {exchange1.name}, zone: {exchange1.zone}')
    

**返回示例**

          code           name      zone 
    0      SGX    新加坡交易所     Singapore
    1     HKEX     香港交易所    Asia/Hong_Kong
    2  CBOEXBT  芝加哥期权交易所  America/Chicago
    3      OSE     大阪交易所      Asia/Tokyo
    4     CBOE  芝加哥期權交易所  America/Chicago

* * *

get\_future\_contracts 获取交易所下的可交易合约

[](./quote-future.md#get_future_contracts-%E8%8E%B7%E5%8F%96%E4%BA%A4%E6%98%93%E6%89%80%E4%B8%8B%E7%9A%84%E5%8F%AF%E4%BA%A4%E6%98%93%E5%90%88%E7%BA%A6)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_future_contracts(exchange, lang=None)`

**说明**

获取交易所下的可交易合约

注意：当合约接近第一通知日或最后交易日时，市场的交易活跃度通常会从当前到期月份逐步转移至下一个活跃合约月份。 临近到期的合约流动性显著下降，买卖价差扩大，成交量减少，持仓风险增加。因此，无论是多头还是空头持仓，建议您提前进行移仓操作，将头寸转移至次月或下一个主力合约，以确保流动性和价格连续性。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md)

**参数**

| 参量名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| exchange | str | Yes | 交易所代码，如 'CBOE' |
| lang | Language | No  | zh\_CN,zh\_TW,en\_US |

**返回**

`pandas.DataFrame`

结构如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| contract\_code | str | 合约代码，如 VIX2208 |
| type | str | 期货合约对应的交易品种，如 CL |
| symbol | str | 对应的代码 |
| name | str | 期货合约的名称 |
| contract\_month | str | 合约交割月份，如 202208，表示2022年8月 |
| currency | str | 交易的货币类型 |
| exchange | str | 交易所代码 |
| first\_notice\_date | str | 第一通知日，指实物交割合约可以进行实物交割的日期，合约在第一通知日后无法开多仓。已有的多仓会在第一通知日之前（通常为前三个交易日）会被强制平仓。**非实物交割合约(如指数合约)该字段为空** |
| last\_bidding\_close\_time | str | 竞价截止时间 |
| last\_trading\_date | str | 指合约到期月份最后交易的日期，最后交易日后尚未清算的期货合约，须通过相关『现货商品』或『现金结算』方式平仓，目前大部分期货商品最后交易日通常就是结算日。**有些商品第一通知日跟最后交易日是同一天，如欧元**  <br>对于现金交割的期货只要没过最后交易时间都可以正常开仓，**非现金交割的期货是按照最后交易时间和第一通知日其中较小者，在其前三个交易日开始就限制开仓** |
| last\_trading\_timestamp | int | 最后交易日的精确截止时间 |
| trade | bool | 是否可交易 |
| continuous | bool | 是否为连续合约 |
| multiplier | float | 合约乘数，期货价格乘以合约乘数后，才是合约的面值，可以根据实物价格除上合约乘数，估算期货的合理价格 |
| min\_tick | float | 期货价格变动的最小报价单位，比如当前期货价格为 2000，minTick为100，则正确的报价包括 2100，2200，而 2005不满足要求 |
| delivery\_mode | str | 交割方式 |
| product\_worth | str | 合约规模 |
| product\_type | str | 合约类型 |
| product\_scale | str | 合约规格 |
| time\_zone | str | 时区  |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    contracts = quote_client.get_future_contracts('CME')
    
    # 将合约代码设置为pandas DataFrame 索引，并查询字段
    contract_nq2309 = contracts.set_index('contract_code').loc['NQ2309']
    print(contract_nq2309.name)  # 合约名称
    print(contract_nq2309.multiplier)  # 合约乘数
    print(contract_nq2309.last_trading_date)  # 最后交易日
    

**返回示例**

      contract_code symbol  type         name contract_month  multiplier exchange  \
    0       MXP1912    MXP   MXP       比索1912         201912    500000.0   GLOBEX   
    1       MXP1909    MXP   MXP       比索1909         201909    500000.0   GLOBEX   
    2      MEUR1909    M6E  MEUR      微欧元1909         201909     12500.0   GLOBEX   
    3        ES1909     ES    ES  SP500指数1909         201909        50.0   GLOBEX   
    4        ES2003     ES    ES  SP500指数2003         202003        50.0   GLOBEX   
    
      currency first_notice_date last_bidding_close_time last_trading_date  trade  \
    0      USD              None                    None          20191216   True   
    1      USD              None                    None          20190916   True   
    2      USD              None                    None          20190916   True   
    3      USD              None                    None          20190920   True   
    4      USD              None                    None          20200320   True   
    
       continuous  
    0       False  
    1       False  
    2       False  
    3       False  
    4       False  
    
    

* * *

get\_future\_contract 根据代码获取期货合约

[](./quote-future.md#get_future_contract-%E6%A0%B9%E6%8D%AE%E4%BB%A3%E7%A0%81%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E5%90%88%E7%BA%A6)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_future_contract(contract_code, lang=None)`

**说明**

根据代码获取期货合约

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md)

**参数**

| 参量名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contract\_code | str | Yes | 期货合约对应的代码，如 CL2206 |
| lang | Language | No  | zh\_CN,zh\_TW,en\_US |

**返回**

`pandas.DataFrame`

结构如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| contract\_code |     | 合约代码，如 CL2112 |
| type | str | 期货合约对应的交易品种， 如 CL |
| name | str | 期货合约的名称 |
| contract\_month | str | 合约交割月份，如 202112，表示2021年12月交割 |
| currency | str | 交易的货币 |
| first\_notice\_date | str | 第一通知日，指实物交割合约可以进行实物交割的日期，合约在第一通知日后无法开多仓。已有的多仓会在第一通知日之前（通常为前三个交易日）会被强制平仓。**非实物交割合约(如指数合约)该字段为空** |
| last\_bidding\_close\_time | str | 竞价截止时间 |
| last\_trading\_date | str | 指合约到期月份最后交易的日期，最后交易日后尚未清算的期货合约，须通过相关『现货商品』或『现金结算』方式平仓，目前大部分期货商品最后交易日通常就是结算日。**有些商品第一通知日跟最后交易日是同一天，如欧元**  <br>对于现金交割的期货只要没过最后交易时间都可以正常开仓，**非现金交割的期货是按照最后交易时间和第一通知日其中较小者，在其前三个交易日开始就限制开仓** |
| last\_trading\_timestamp | int | 最后交易日的精确截止时间 |
| trade | bool | 是否可交易 |
| continuous | bool | 是否为连续合约 |
| multiplier | float | 合约乘数，期货价格乘以合约乘数后，才是合约的面值，可以根据实物价格除上合约乘数，估算期货的合理价格 |
| min\_tick | float | 期货价格变动的最小报价单位，比如当前期货价格为 2000，minTick为100，则正确的报价包括 2100，2200，而 2005不满足要求 |
| exchange | str | 交易所代码 |
| symbol | str | 合约的代码 |
| product\_type | str | 合约类型 |
| product\_scale | str | 合约规格 |
| time\_zone | str | 时区  |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import get_client_config
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    contract = quote_client.get_future_contract('CHF2512')
    print(contract)

**返回示例**

      contract_code  continuous contract_month currency  display_multiplier exchange exchange_code first_notice_date  last_bidding_close_time last_trading_date  min_tick  multiplier    name symbol  trade type
    0       CHF2512       False         202512      USD                   1      CME        GLOBEX                                          0          20251215   0.00005    125000.0  瑞朗2512    CHF   True  CHF
    

* * *

get\_current\_future\_contract 查询指定品种的当前合约

[](./quote-future.md#get_current_future_contract-%E6%9F%A5%E8%AF%A2%E6%8C%87%E5%AE%9A%E5%93%81%E7%A7%8D%E7%9A%84%E5%BD%93%E5%89%8D%E5%90%88%E7%BA%A6)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_current_future_contract(future_type, lang=None)`

**说明**

查询指定品种的当前合约，即主力合约

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md)

**参数**

| 参量名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| future\_type | str | Yes | 期货合约对应的交易品种，如 CL |
| lang | Language | No  | zh\_CN,zh\_TW,en\_US |

**返回**

`pandas.DataFrame`

结构如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| contract\_code |     | 合约代码，如 CL2112 |
| type | str | 期货合约对应的交易品种， 如 CL |
| name | str | 期货合约的名称 |
| contract\_month | str | 合约交割月份，如 202112，表示2021年12月交割 |
| currency | str | 交易的货币 |
| first\_notice\_date | str | 第一通知日，指实物交割合约可以进行实物交割的日期，合约在第一通知日后无法开多仓。已有的多仓会在第一通知日之前（通常为前三个交易日）会被强制平仓。**非实物交割合约(如指数合约)该字段为空** |
| last\_bidding\_close\_time | str | 竞价截止时间 |
| last\_trading\_date | str | 指合约到期月份最后交易的日期，最后交易日后尚未清算的期货合约，须通过相关『现货商品』或『现金结算』方式平仓，目前大部分期货商品最后交易日通常就是结算日。**有些商品第一通知日跟最后交易日是同一天，如欧元**  <br>对于现金交割的期货只要没过最后交易时间都可以正常开仓，**非现金交割的期货是按照最后交易时间和第一通知日其中较小者，在其前三个交易日开始就限制开仓** |
| last\_trading\_timestamp | int | 最后交易日的精确截止时间 |
| trade | bool | 是否可交易 |
| continuous | bool | 是否为连续合约 |
| multiplier | float | 合约乘数，期货价格乘以合约乘数后，才是合约的面值，可以根据实物价格除上合约乘数，估算期货的合理价格 |
| min\_tick | float | 期货价格变动的最小报价单位，比如当前期货价格为 2000，minTick为100，则正确的报价包括 2100，2200，而 2005不满足要求 |
| exchange | str | 交易所代码 |
| symbol | str | 合约的代码 |
| delivery\_mode | str | 交割方式 |
| product\_worth | str | 合约规模 |
| product\_type | str | 合约类型 |
| product\_scale | str | 合约规格 |
| time\_zone | str | 时区  |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    contracts = quote_client.get_current_future_contract('ES')

**返回示例**

      contract_code type         name contract_month currency first_notice_date  \
    0        ES1903   ES  SP500指数1903         201903      USD          20190315   
    
       last_bidding_close_time last_trading_date  trade  continuous  
    0                        0          20190315   True       False  
    

* * *

get\_all\_future\_contracts 查询指定品种全部合约

[](./quote-future.md#get_all_future_contracts-%E6%9F%A5%E8%AF%A2%E6%8C%87%E5%AE%9A%E5%93%81%E7%A7%8D%E5%85%A8%E9%83%A8%E5%90%88%E7%BA%A6)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_all_future_contracts(future_type, lang=None)`

**说明**

查询指定品种的所有合约

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md)

**参数**

| 参量名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| future\_type | str | Yes | 期货合约对应的交易品种，如 CL |
| lang | Language | No  | zh\_CN，zh\_TW，en\_US |

**返回**

`pandas.DataFrame`

结构如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| contract\_code |     | 合约代码，如 CL2112 |
| type | str | 期货合约对应的交易品种， 如 CL |
| name | str | 期货合约的名称 |
| contract\_month | str | 合约交割月份，如 202112，表示2021年12月交割 |
| currency | str | 交易的货币 |
| first\_notice\_date | str | 第一通知日，指实物交割合约可以进行实物交割的日期，合约在第一通知日后无法开多仓。已有的多仓会在第一通知日之前（通常为前三个交易日）会被强制平仓。**非实物交割合约(如指数合约)该字段为空** |
| last\_bidding\_close\_time | str | 竞价截止时间 |
| last\_trading\_date | str | 指合约到期月份最后交易的日期，最后交易日后尚未清算的期货合约，须通过相关『现货商品』或『现金结算』方式平仓，目前大部分期货商品最后交易日通常就是结算日。**有些商品第一通知日跟最后交易日是同一天，如欧元**  <br>对于现金交割的期货只要没过最后交易时间都可以正常开仓，**非现金交割的期货是按照最后交易时间和第一通知日其中较小者，在其前三个交易日开始就限制开仓** |
| last\_trading\_timestamp | int | 最后交易日的精确截止时间 |
| trade | bool | 是否可交易 |
| continuous | bool | 是否为连续合约 |
| multiplier | float | 合约乘数，期货价格乘以合约乘数后，才是合约的面值，可以根据实物价格除上合约乘数，估算期货的合理价格 |
| min\_tick | float | 期货价格变动的最小报价单位，比如当前期货价格为 2000，minTick为100，则正确的报价包括 2100，2200，而 2005不满足要求 |
| exchange | str | 交易所代码 |
| symbol | str | 合约的代码 |
| delivery\_mode | str | 交割方式 |
| product\_worth | str | 合约规模 |
| product\_type | str | 合约类型 |
| product\_scale | str | 合约规格 |
| time\_zone | str | 时区  |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    contracts = quote_client.get_all_future_contracts('CL')

**返回示例**

       contract_code symbol type       name contract_month  multiplier exchange exchange_code currency first_notice_date last_bidding_close_time last_trading_date  trade  continuous  min_tick
    0         CL2304     CL   CL  WTI原油2304         202304      1000.0    NYMEX         NYMEX      USD          20230323                    None          20230321   True       False      0.01
    1         CL2305     CL   CL  WTI原油2305         202305      1000.0    NYMEX         NYMEX      USD          20230424                    None          20230420   True       False      0.01
    2         CL2306     CL   CL  WTI原油2306         202306      1000.0    NYMEX         NYMEX      USD          20230524                    None          20230522   True       False      0.01
    3         CL2307     CL   CL  WTI原油2307         202307      1000.0    NYMEX         NYMEX      USD          20230622                    None          20230620   True       False      0.01
    4         CL2308     CL   CL  WTI原油2308         202308      1000.0    NYMEX         NYMEX      USD          20230724                    None          20230720   True       False      0.01
    5         CL2309     CL   CL  WTI原油2309         202309      1000.0    NYMEX         NYMEX      USD          20230824                    None          20230822   True       False      0.01
    6         CL2310     CL   CL  WTI原油2310         202310      1000.0    NYMEX         NYMEX      USD          20230922  ```

* * *

get\_future\_continuous\_contracts 查询指定品种的连续合约

[](./quote-future.md#get_future_continuous_contracts-%E6%9F%A5%E8%AF%A2%E6%8C%87%E5%AE%9A%E5%93%81%E7%A7%8D%E7%9A%84%E8%BF%9E%E7%BB%AD%E5%90%88%E7%BA%A6)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_future_continuous_contracts(future_type, lang=None)`

**说明**

查询指定品种的连续合约

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md)

**参数**

| 参量名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| future\_type | str | Yes | 期货合约对应的交易品种，如 CL |
| lang | Language | No  | zh\_CN，zh\_TW，en\_US |

**返回**

`pandas.DataFrame`

结构如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| contract\_code | str | 合约代码，如 CLmain |
| continuous | bool | 是否为连续合约 |
| contract\_month | str | 合约交割月份，如 202112，表示2021年12月交割 |
| currency | str | 交易的货币 |
| display\_multiplier | float | 合约乘数，期货价格乘以合约乘数后，才是合约的面值，可以根据实物价格除上合约乘数，估算期货的合理价格 |
| exchange | str | 交易所 |
| exchange\_code | str | 交易所代码 |
| first\_notice\_date | str | 第一通知日，指实物交割合约可以进行实物交割的日期，合约在第一通知日后无法开多仓。已有的多仓会在第一通知日之前（通常为前三个交易日）会被强制平仓。**非实物交割合约(如指数合约)该字段为空** |
| last\_bidding\_close\_time | str | 竞价截止时间 |
| last\_trading\_date | str | 指合约到期月份最后交易的日期，最后交易日后尚未清算的期货合约，须通过相关『现货商品』或『现金结算』方式平仓，目前大部分期货商品最后交易日通常就是结算日。**有些商品第一通知日跟最后交易日是同一天，如欧元**  <br>对于现金交割的期货只要没过最后交易时间都可以正常开仓，**非现金交割的期货是按照最后交易时间和第一通知日其中较小者，在其前三个交易日开始就限制开仓** |
| last\_trading\_timestamp | int | 最后交易日的精确截止时间 |
| min\_tick | float | 期货价格变动的最小报价单位，比如当前期货价格为 2000，minTick为100，则正确的报价包括 2100，2200，而 2005不满足要求 |
| multiplier | float | 合约乘数，期货价格乘以合约乘数后，才是合约的面值，可以根据实物价格除上合约乘数，估算期货的合理价格 |
| name | str | 期货合约的名称 |
| symbol | str | 期货合约的简称 |
| trade | bool | 是否可交易 |
| type | str | 期货合约对应的交易品种， 如 CL |
| delivery\_mode | str | 交割方式 |
| product\_worth | str | 合约规模 |
| product\_type | str | 合约类型 |
| product\_scale | str | 合约规格 |
| time\_zone | str | 时区  |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    contracts = quote_client.get_future_continuous_contracts('CL')

**返回示例**

     contract_code  continuous contract_month currency  display_multiplier exchange exchange_code first_notice_date  last_bidding_close_time last_trading_date  min_tick  multiplier     name symbol  trade type
    0        CLmain       False                     USD                   1    NYMEX         NYMEX                                          0                        0.01      1000.0  WTI原油主连     CL   True   CL
    

* * *

get\_future\_trading\_times 查询指定合约的交易时间

[](./quote-future.md#get_future_trading_times-%E6%9F%A5%E8%AF%A2%E6%8C%87%E5%AE%9A%E5%90%88%E7%BA%A6%E7%9A%84%E4%BA%A4%E6%98%93%E6%97%B6%E9%97%B4)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`get_future_trading_times(identifier, trading_date=None)`

**说明**

查询指定合约的交易时间

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md)

**参数**

| 参量名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| identifier | str | Yes | 合约代码，如CL1901 |
| trading\_date | int | Yes | 指定交易日的毫秒单位时间戳，如 1643346000000 |

**返回**

`pandas.DataFrame`

结构如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| start | int | 交易开始时间 |
| end | int | 交易结束时间 |
| trading | bool | 是否为连续交易 |
| bidding | bool | 是否为竞价交易 |
| zone | str | 时区  |

**示例**

Python

    import pandas as pd
    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    times = quote_client.get_future_trading_times('CN1901', trading_date=1545049282852)
    # 转换 start、end 的格式
    times['zone_start'] = pd.to_datetime(times['start'], unit='ms').dt.tz_localize('UTC').dt.tz_convert(times['zone'][0])
    times['zone_end'] = pd.to_datetime(times['end'], unit='ms').dt.tz_localize('UTC').dt.tz_convert(times['zone'][0])
    print(times.to_string())

**返回示例**

               start            end  trading  bidding       zone  
    0  1545036600000  1545037200000    False     True  Singapore   
    1  1545093900000  1545094800000    False     True  Singapore   
    2  1545121800000  1545122100000    False     True  Singapore   
    3  1545037200000  1545079500000     True    False  Singapore   
    4  1545094800000  1545121800000     True    False  Singapore   

* * *

get\_future\_brief 获取期货最新行情

[](./quote-future.md#get_future_brief-%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E6%9C%80%E6%96%B0%E8%A1%8C%E6%83%85)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_future_brief(identifiers)`

**说明**

获取期货最新行情，包括盘口数据，最新成交数据等

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| identifiers | list | Yes | 期货代码列表，如 \['CL2201'\] |

**返回**

`pandas.DataFrame`

各 column 含义如下:

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| identifier | str | 期货代码 |
| ask\_price | float | 卖价  |
| ask\_size | int | 卖量  |
| bid\_price | float | 买价  |
| bid\_size | int | 买量  |
| pre\_close | float | 前收价 |
| latest\_price | float | 最新价 |
| latest\_size | int | 最新成交量 |
| latest\_time | int | 最新价成交时间 |
| volume | int | 当日累计成交手数 |
| open\_interest | int | 未平仓合约数量 |
| open\_interest\_change | int | 未平仓合约数量变化 |
| open | float | 开盘价 |
| high | float | 最高价 |
| low | float | 最低价 |
| limit\_up | float | 涨停价 |
| limit\_down | float | 跌停价 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    briefs = quote_client.get_future_brief(['CN1901', 'MXP1903'])
    print(briefs.to_string())

**返回示例**

      identifier    ask_price  ask_size    bid_price  bid_size pre_close  \
    0     CN1901  10677.50000        13  10675.00000       145      None   
    1    MXP1903      0.05219         9      0.05218         3      None   
    
       latest_price  latest_size    latest_time  volume  open_interest  \
    0   10677.50000            2  1547519736000  111642         863308   
    1       0.05219            4  1547519636000    1706         190126   
    
              open         high          low    limit_up  limit_down  
    0  10607.50000  10707.50000  10572.50000  11670.0000   9550.0000  
    1      0.05223      0.05223      0.05216      0.0562      0.0482  

* * *

get\_future\_depth 获取期货深度行情

[](./quote-future.md#get_future_depth-%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E6%B7%B1%E5%BA%A6%E8%A1%8C%E6%83%85)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_future_depth(identifiers)`

**说明**

获取期货深度行情，需要 sdk 版本 3.4.6 及以上

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| identifiers | list\[str\] | Yes | 期货代码列表，如 \['CL2201'\] |

**返回**

`dict`

结构如下：

| 参量名 | 类型  | 描述  |
| --- | --- | --- |
| identifier | str | 期货代码 |
| ask | list\[tuple\] | 卖盘信息 |
| bids | list\[tuple\] | 买盘信息 |

`asks` 和`bids` 中的每一项为一个元组，元组的元素组成为`(price, volume)`

若是单个标的，dict 数据结构如上所述；若是多个标的，dict 嵌套两层，以期货符号为key。 参见返回示例

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    result = self.client.get_future_depth(identifiers=['ES2509'])

**返回示例**

单个标的

      {
      "identifier": "CL2509",
      "asks": [\
          {"price": 63.07, "size": 10},\
          {"price": 63.08, "size": 5},\
          ...\
      ],
      "bids": [\
          {"price": 63.06, "size": 7},\
          {"price": 63.05, "size": 12},\
          ...\
      ]
    }

多个标的

      {
        "CL2509": {
            "asks": [\
                {"price": 63.07, "size": 10},\
                {"price": 63.08, "size": 5},\
                ...\
            ],
            "bids": [\
                {"price": 63.06, "size": 7},\
                {"price": 63.05, "size": 12},\
                ...\
            ]
        },
        "ES2509": {
            "asks": [\
                {"price": 6469.5, "size": 11},\
                {"price": 6469.75, "size": 14},\
                ...\
            ],
            "bids": [\
                {"price": 6469.25, "size": 8},\
                {"price": 6469.0, "size": 10},\
                ...\
            ]
        }
    }

* * *

get\_future\_trade\_ticks 获取期货逐笔成交

[](./quote-future.md#get_future_trade_ticks-%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E9%80%90%E7%AC%94%E6%88%90%E4%BA%A4)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_future_trade_ticks(identifier, begin_index=0, end_index=30, limit=1000)`

**说明**

索引每天北京时间凌晨6:00 重置为 0。前一天的逐笔数据会在最早的交易时段（竞价或交易）开始前一分钟清除。新的逐笔数据会在新交易时段开始后重新记录。 该重置每天仅发生一次，即同一交易日内的多个交易时段不会触发额外的重置。

> ⚠️
> 
> **Caution**
> 
> 一旦前一天的逐笔数据被清除，API 将无法再访问这些数据。
> 
> 例如：GC2504 的逐笔数据可在早上 5:59 前访问，但会在早上 6:00 重置为下标 0。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| identifier | str | Yes | 期货代码 |
| begin\_index | int | No  | 起始索引 |
| end\_index | int | No  | 结束索引 |
| limit | int | No  | 返回条数限制，最多支持返回1000条逐笔记录 |

**返回**

`pandas.DataFrame`

各 column 含义如下：

| 参数名 | 类型  | 描述  |
| --- | --- | --- |
| index | int | 索引值 |
| time | int | 成交时间，精确到毫秒的时间戳 |
| price | float | 成交价格 |
| volume | int | 成交量 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    ticks = quote_client.get_future_trade_ticks('CN1901')
    print(ticks)

**返回示例**

       identifier  index           time        price  volume
    0      CN1901      0  1547456400000  10607.50000       5
    1      CN1901      1  1547456402000  10605.00000       6
    2      CN1901      2  1547456407000  10602.50000       4
    3      CN1901      3  1547456407000  10605.00000       2
    4      CN1901      4  1547456424000  10602.50000       5

* * *

get\_future\_bars 获取期货K线

[](./quote-future.md#get_future_bars-%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7k%E7%BA%BF)

-------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_future_bars(identifiers, period=BarPeriod.DAY, begin_time=-1, end_time=-1, limit=1000, page_token=None)`

**说明**

提供了热门合约近10年的日级别K线数据，以及全部合约2017年8月至今的分钟级数据。

返回结果是从endTime开始按时间倒序的数据集合。

对于1分钟K线，如果在这1分钟内没有成交，这一分钟K线数据会空缺；在最近的这一分钟内有成交后接口才能拉取到这1分钟的K线数据，如果在第50秒时产生第一笔交易，在50秒之前拉取不到最新点的数据。

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| identifiers | list | Yes | 期货代码列表 |
| begin\_time | int | No  | 开始时间，毫秒时间戳，查询结果包含该时间点，默认为：-1，**begin\_time和end\_time默认都传-1时，会按照limit设置的条数返回** |
| end\_time | int | No  | 截至时间， 毫秒时间戳，查询结果不包含该时间点，默认为：-1 |
| period | BarPeriod | No  | 获取的K线周期。**默认 BarPeriod.DAY**，可以使用 tigeropen.common.consts.BarPeriod 下提供的枚举常量，如 BarPeriod.DAY。 'day'/'week'/'month'/'year'/'1min'/'5min'/'15min'/'30min'/'60min' |
| limit | int | No  | 请求条数限制，默认1000，最大限制：1000 |
| page\_token | str | No  | 分页token，记录分页的位置，上次请求返回的 next\_page\_token 可传入作为下次请求的起始标志，使用pageToken分页拉取数据时其他查询条件不能改变 |

**返回** `pandas.DataFrame`

各column 含义如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| identifier | str | 期货合约代码 |
| time | int | Bar对应的时间戳, 即Bar的结束时间。Bar的切割方式与交易所一致，以CN1901举例，T日的17:00至T+1日的16:30的数据会被合成一个日级Bar。 |
| latest\_time | int | Bar 最后的更新时间 |
| open | float | 开盘价 |
| high | float | 最高价 |
| low | float | 最低价 |
| close | float | 收盘价 |
| settlement | float | 结算价，在未生成结算价时返回0 |
| volume | int | 成交量 |
| open\_interest | int | 未平仓合约数量 |
| next\_page\_token | str | 下一页的page token |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    bars = quote_client.get_future_bars(['CN1901', 'MXP1903'], 
                                              begin_time=-1, 
                                              end_time=1545105097358)
    print(bars.head().to_string())

**返回示例**

      identifier           time    latest_time     open     high      low    close  settlement  volume  open_interest next_page_token
    0     CN1901  1545035400000  1545035700002  11022.5  11097.5  10935.0  10940.0     10935.0    3872          15148            None
    1     CN1901  1544776200000  1544776168000  11182.5  11197.5  11002.5  11030.0     11042.5    1379          14895            None
    2     CN1901  1544689800000  1544689765000  11012.5  11252.5  11012.5  11212.5     11202.5    4586          12870            None
    3     CN1901  1544603400000  1544603321185  10940.0  11070.0  10940.0  11035.0     11032.5    3514          12237            None
    4     CN1901  1544517000000  1544516945000  10895.0  10962.5  10815.0  10942.5     10927.5    2378          10575            None

* * *

get\_future\_bar\_by\_page 分页获取期货K线

[](./quote-future.md#get_future_bar_by_page-%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7k%E7%BA%BF)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

`QuoteClient.get_future_bars_by_page(identifier, period=BarPeriod.DAY, begin_time=-1, end_time=-1, total=10000, page_size=1000, time_interval=2)`

**说明**

分页获取期货K线

**请求频率**

频率限制请参考：[接口请求限制](./ratelimit.md)

**参数**

| 参数名 | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| identifier | str | Yes | 期货代码, 每次只能查单个标的 |
| period | BarPeriod | No  | 获取的K线周期。默认 BarPeriod.DAY，可以使用 tigeropen.common.consts.BarPeriod 下提供的枚举常量，如 BarPeriod.DAY。 'day'/'week'/'month'/'year'/'1min'/'5min'/'15min'/'30min'/'60min' |
| begin\_time | int | No  | 开始时间，毫秒时间戳，查询结果包含该时间点，默认：-1，**begin\_time和end\_time默认都传-1时，会按照limit设置的条数返回** |
| end\_time | int | No  | 截至时间， 毫秒时间戳，查询结果不包含该时间点，默认：-1 |
| total | int | No  | 请求bar的总数，默认是：10000 |
| page\_size | int | No  | 每页bar的数量，默认是：1000 |
| time\_interval | int | No  | 每次请求的时间间隔，单位秒，默认是：2秒 |

**返回** `pandas.DataFrame`

各column 含义如下：

| COLUMN | 类型  | 描述  |
| --- | --- | --- |
| identifier | str | 期货合约代码 |
| time | int | Bar对应的时间戳, 即Bar的结束时间。Bar的切割方式与交易所一致，以CN1901举例，T日的17:00至T+1日的16:30的数据会被合成一个日级Bar。 |
| latest\_time | int | Bar 最后的更新时间 |
| open | float | 开盘价 |
| high | float | 最高价 |
| low | float | 最低价 |
| close | float | 收盘价 |
| settlement | float | 结算价，在未生成结算价时返回0 |
| volume | int | 成交量 |
| open\_interest | int | 未平仓合约数量 |
| next\_page\_token | str | 下一页的page token |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    bars = quote_client.get_future_bars_by_page('CLmain',
                                                end_time=1648526400000,
                                                total=10,
                                                page_size=4)
    print(bars.head().to_string())
    

**返回示例**

      identifier           time    latest_time    open    high     low   close  settlement  volume  open_interest                                                       next_page_token
    0     CLmain  1647378000000  1647377999000  102.28  102.58   93.53   95.18       96.44  297127         141240  ZnV0dXJlX2tsaW5lfENMbWFpbnxkYXl8MTY0ODUyNjQwMDAwMHwxNjQ3Mzc3OTcwMDAw
    1     CLmain  1647464400000  1647464399000   95.23   99.22   94.07   95.36       95.04  220577         119614  ZnV0dXJlX2tsaW5lfENMbWFpbnxkYXl8MTY0ODUyNjQwMDAwMHwxNjQ3Mzc3OTcwMDAw
    2     CLmain  1647550800000  1647550797000   95.34  104.24   94.85  103.62      102.98  129667          97283  ZnV0dXJlX2tsaW5lfENMbWFpbnxkYXl8MTY0ODUyNjQwMDAwMHwxNjQ3NTUwNzcwMDAw
    3     CLmain  1647637200000  1647637192000  103.62  106.28  102.30  105.10      104.70   32312         280113  ZnV0dXJlX2tsaW5lfENMbWFpbnxkYXl8MTY0ODUyNjQwMDAwMHwxNjQ3NTUwNzcwMDAw
    4     CLmain  1647896400000  1647896399000  103.62  111.08  102.47  110.93      109.97  187356         280043  ZnV0dXJlX2tsaW5lfENMbWFpbnxkYXl8MTY0ODUyNjQwMDAwMHwxNjQ3NTUwNzcwMDAw
