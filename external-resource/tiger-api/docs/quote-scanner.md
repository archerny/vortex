# 选股器

market\_scanner 选股器

[](./quote-scanner.md#market_scanner-%E9%80%89%E8%82%A1%E5%99%A8)

-------------------------------------------------------------------------------------------------------------------

`QuoteClient.market_scanner(self, market: Optional[Union[Market, str]] = Market.US, filters: Optional[List[StockFilter]] = None, sort_field_data: Optional[SortFilterData] = None, page: Optional[int] = 0, page_size: Optional[int] = 100, cursor_id: Optional[str] = None)`

**说明**

通过不同的技术指标条件来扫描全市场行情，帮助您筛选出满足特定投资需求的标的列表。

技术指标条件包含如下几类：基础指标、累积指标、财务指标，多标签指标，具体参数含义请参考下面的说明。

**参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| market | [tigeropen.common.consts.Market](./appendix-object-detail.md#market) | Yes | US 美股，SG 新股，HK港股 |
| filters | list\[StockFilter\] | Yes | 过滤器列表，共有四类，见下方说明 |
| sort\_field\_data | tigeropen.quote.domain.filter.SortFilterData | No  | 排序字段对象，主要属性，如下所示 |
| ∟ field | enum | No  | 排序字段, tigeropen.common.consts.filter\_fields 中的字段枚举， 如 StockField, AccumulateField |
| ∟ sort\_dir | [tigeropen.common.consts.SortDirection](./appendix-enum.md#sortdirection) | No  | 排序方向，包括：不排序，升序，降序 |
| page | int | No  | 当前页码（从0开始). 不推荐使用，请使用 cursor\_id |
| cursor\_id | str | No  | 游标ID，用于基于游标的分页查询，客户端在获取下一页时应将此值作为请求参数传入，首次查询可传递`None` |
| page\_size | int | No  | 每页返回的数据量，最大支持配置成：200 |

StockFilter 参数说明：

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| field | tigeropen.common.consts.filter\_fields.FilterField | Yes | 共四类 field，见下方说明 |
| filter\_min | float | No  | 区间下限（闭区间），不传代表下限为 -∞ 如果为百分位数，不需要加%，例如10%，数值为10即可 |
| filter\_max | float | No  | 区间上限（闭区间），不传代表上限为 +∞ |
| is\_no\_filter | bool | No  | 是否禁用本Filter，如果为True，则本过滤器不生效 |
| accumulate\_period | [tigeropen.common.consts.filter\_fields.AccumulatePeriod](./appendix-enum.md#accumulatefield) | No  | 累计周期枚举，仅当field为 AccumulateField 时需要指定 |
| financial\_period | [tigeropen.common.consts.filter\_fields.FinancialPeriod](./appendix-enum.md#financialfield) | No  | 财务周期枚举，仅当field为 FinancialField 时需要指定 |
| tag\_list | `list[int,str]` | No  | 标签列表，仅当field为MultiTagField时需要指定 |

StockFilter 的field字段有如下枚举类型 (导入路径 `tigeropen.common.consts.filter_fields`)

| 类型  | 说明  |
| --- | --- |
| StockField | 简单指标筛选条件，包括价格（高开低收，最新价等），成交量，股本，市值，涨跌幅，市盈率，换手率等因子。筛选字段含义说明： [筛选字段说明](./appendix-enum.md#/stockfield) |
| AccumulateField | 累积指标筛选条件，包括累积涨跌幅，资产增长率，净利润增长率，每股收益，净利润，营业利润，营业收入，ROA（净资产收益率），经营现金流，资产负债率等。累积指标周期可以是：近五分钟，近5日，10日，20日，近半年，一年，两年，五年，一季度报，三季度报，中报等等。筛选字段含义说明：[筛选字段说明](./appendix-enum.md#accumulatefield) |
| FinancialField | 财务指标筛选条件，包括毛利，净利率，总负债/股东权益，总负债/总资产，流动比率，资产回报率，净利润，经营现金流，总资产，港股通净买入额，年化收益率等等。财务指标目前只支持LTM（最近12个月的年报指标）类型的财报查询。筛选字段含义说明：[筛选字段说明](./appendix-enum.md#financialfield) |
| MultiTagField | 多标签关联关系筛选条件，基于行业，概念，历史股价新高（当天股价和历史价格相比），52周内股价新高（当天股价和最近52周相比），是否为OTC，是否支持期权，股票类型（股票，ETF），是否破发等指标来选股。筛选字段含义说明：[筛选字段说明](./appendix-enum.md#multitagfield) |

> 筛选参数中的价格字段对应的币种和该标的所在市场的货币类型保持一致，比如美股：USD，港股：HKD，新股：SGD 等。

**返回**

`tigeropen.quote.domain.filter.ScannerResult`

结构如下:

Python

    class ScannerResult:
        def __init__(self, page, page_size, total_page, total_count, items, cursor_id):
            # 当前页码，从0开始
            self.page = page
            # 总页数
            self.total_page = total_page
            # 数据总条数
            self.total_count = total_count
            # 分页大小
            self.page_size = page_size
            # 下一页分页id
            self.cursor_id = cursor_id
            # 结果数据列表
            self.items: ScannerResultItem = list()
            # 筛选出的股票symbol列表汇总
            self.symbols = list()
    
    # 其中 items 的每项为：
    class ScannerResultItem:
        def __init__(self, symbol, market, base_data_list=None, accumulate_data_list=None, financial_data_list=None,
                     multi_tag_data_list=None):
            self.symbol = symbol
            self.market = market
            self.field_data = dict()
    
    # 可使用 filter 作为 key 取出对应filter字段的值, 参加下方示例
    

**示例**

Python

    import time
    from datetime import datetime
    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import get_client_config
    from tigeropen.common.consts import TradingSession, Market
    from tigeropen.quote.domain.filter import OptionFilter, StockFilter, SortFilterData
    from tigeropen.common.consts.filter_fields import StockField, AccumulateField, FinancialField, MultiTagField, \
        FinancialPeriod, AccumulatePeriod
    
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    def test_market_scanner():
        # 股票基本数据过滤(is_no_filter为True时表示不启用该过滤器)
        base_filter1 = StockFilter(StockField.FloatShare, filter_min=1e7, filter_max=1e13, is_no_filter=True)
        base_filter2 = StockFilter(StockField.MarketValue, filter_min=1e8, filter_max=1e14, is_no_filter=False)
        # 按财报时间过滤
        base_filter3 = StockFilter(StockField.EarningDate, filter_min=int(datetime.strptime('2021-01-01', '%Y-%m-%d').timestamp() * 1000),
                                       filter_max=int(datetime.strptime('2023-12-31', '%Y-%m-%d').timestamp() * 1000)
                                       , is_no_filter=False)
        # 周期累计数据过滤
        accumulate_filter = StockFilter(AccumulateField.ChangeRate, filter_min=0.01, filter_max=1, is_no_filter=False,
                                        accumulate_period=AccumulatePeriod.Last_Year)
        # 财务数据过滤 (LYR_PE: 静态市盈率，即 Last Year Ratio PE，用最近一个完整年度的每股收益计算)
        financial_filter = StockFilter(FinancialField.LYR_PE, filter_min=1, filter_max=100, is_no_filter=False,
                                       financial_period=FinancialPeriod.LTM)
        # 多标签数据过滤，需要先获取 tag_list， 如果有值则根据情况传值，如果没值则不传 tag_list 参数; 对于布尔类型的字段，一般 tag_list 为空，不需要传值
        tags = quote_client.get_market_scanner_tags(market=Market.US, tag_fields=[MultiTagField.OptionsAvailable])
        if not tags:
            tag_list = []
        else:
            # 需要根据实际情况，换成想要过滤的tag， 如field 为 MultiTagField.Industry 时， tag_list = ['BK4209']
            tag_list = ['BK4209']
        multi_tag_filter = StockFilter(MultiTagField.isOTC, tag_list=tag_list)
    
    
        # 排序字段
        sort_field_data = SortFilterData(StockField.FloatShare, sort_dir=SortDirection.ASC)
    
        cursor_id = None
        page_size = 50
        # 是否为最后一页数据
        is_last_page = False
        # 筛选后的symbol列表
        scanner_result_symbols = set()
    
        while not is_last_page:
            # filters参数里填需要使用的过滤器
            result = quote_client.market_scanner(market=Market.US,
                                                 filters=[base_filter1, base_filter2, \
                                                          # base_filter3,\
                                                          accumulate_filter,\
                                                          financial_filter,\
                                                          multi_tag_filter],
                                                 sort_field_data=sort_field_data,
                                                 cursor_id=cursor_id,
                                                 page_size=page_size)
            print(result)
            if result.total_page:
                for item in result.items:
                    # item的类型为 ScannerResultItem 
                    symbol = item.symbol
                    market = item.market
                    # 可以字典的形式获取某个filter的字段对应的值
                    base_filter1_value = item[base_filter1]
                    accumulate_filter_value = item[accumulate_filter]
                    print(
                        f'page:{result.page}, symbol:{symbol}, base_filter1 value:{base_filter1_value}, accumulate_filter value:{accumulate_filter_value}')
                print(f'current page symbols:{result.symbols}')
                scanner_result_symbols.update(result.symbols)
            time.sleep(10)
            # 处理分页
            if not result.cursor_id:
                is_last_page = True
            else:
                cursor_id = result.cursor_id
    
        print(f'scanned symbols:{scanner_result_symbols}')

**返回示例**

    ScannerResult({'page': 0, 'total_page': 208, 'total_count': 1040, 'page_size': 5, 
        'cursor_id': 'xxxxxx',
      'items': [\
        ScannerResultItem({'symbol': 'DNP', 'market': 'US', \
            'field_data': {\
              <StockField.FloatShare: 13>: 0.0, \
              <StockField.MarketValue: 17>: 3855828898.39, \
              <AccumulateField.ChangeRate: 1>: 0.043925, \
              <FinancialField.LYR_PE: 45>: 7.359675, \
              <MultiTagField.isOTC: 3>: '0'}}), \
        ScannerResultItem({'symbol': 'FEN', 'market': 'US', \
            'field_data': {\
            <StockField.FloatShare: 13>: 0.0, \
            <StockField.MarketValue: 17>: 278571284.64, \
            <AccumulateField.ChangeRate: 1>: 0.063893, \
            <FinancialField.LYR_PE: 45>: 6.45728, \
            <MultiTagField.isOTC: 3>: '0'}}), \
        ScannerResultItem({'symbol': 'FDUS', 'market': 'US', \
            'field_data': {\
                <StockField.FloatShare: 13>: 0.0, \
                <StockField.MarketValue: 17>: 462844356.0, \
                <AccumulateField.ChangeRate: 1>: 0.079202, \
                <FinancialField.LYR_PE: 45>: 3.986464, \
                <MultiTagField.isOTC: 3>: '0'}}), \
        ScannerResultItem({'symbol': 'KYN', 'market': 'US', \
            'field_data': {\
                <StockField.FloatShare: 13>: 0.0, \
                <StockField.MarketValue: 17>: 1181621680.4,\
                 <AccumulateField.ChangeRate: 1>: 0.122898, \
                 <FinancialField.LYR_PE: 45>: 3.268946, \
                 <MultiTagField.isOTC: 3>: '0'}}),\
        ScannerResultItem({'symbol': 'TYG', 'market': 'US', \
            'field_data': {\
                <StockField.FloatShare: 13>: 0.0, \
                <StockField.MarketValue: 17>: 381692896.0,\
                <AccumulateField.ChangeRate: 1>: 0.180812, \
                <FinancialField.LYR_PE: 45>: 2.853998, \
                <MultiTagField.isOTC: 3>: '0'}})], 
      'symbols': ['FEN', 'DNP', 'FDUS', 'KYN', 'TYG']})
    

**示例1**筛选股息率大于 5%， 营收3年复合增长率大于 10% 的股票

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.common.consts import TradingSession
    from tigeropen.quote.domain.filter import OptionFilter, StockFilter, SortFilterData
    from tigeropen.common.consts.filter_fields import StockField, AccumulateField, FinancialField, MultiTagField, \
        FinancialPeriod, AccumulatePeriod
    
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    def test_market_scanner1():
    
        # 股息率大于 5%
        base_filter = StockFilter(StockField.DivideRate, filter_min=0.05)
        # 营业收入三年增长率 或者叫 营收3年复合增长率
        financial_filter = StockFilter(FinancialField.TotalRevenues3YrCagr, filter_min=0.1)
        
        cursor_id = None
        page_size = 50
        # 是否为最后一页数据
        is_last_page = False
        # 筛选后的symbol列表
        scanner_result_symbols = set()
    
        while not is_last_page:
            # filters参数里填需要使用的过滤器
            result = quote_client.market_scanner(market=Market.US,
                                                 filters=[\
                                                     base_filter,\
                                                     financial_filter,\
                                                 ],
                                                 cursor_id=cursor_id,
                                                 page_size=page_size)
            print(result)
            if result.total_page:
                for item in result.items:
                    symbol = item.symbol
                    market = item.market
                    # 可以字典的形式获取某个filter的字段对应的值
                    base_filter_value = item[base_filter]
                    financial_filter_value = item[financial_filter]
                    print(
                        f'page:{result.page}, symbol:{symbol}, base_filter value:{base_filter_value}, financial_filter value:{financial_filter_value}')
                print(f'current page symbols:{result.symbols}')
                scanner_result_symbols.update(result.symbols)
            time.sleep(10)
            # 处理分页
            if result.cursor_id is None:
                is_last_page = True
            else:
                cursor_id = result.cursor_id
    
        print(f'scanned symbols:{scanner_result_symbols}')

**示例2 按 ETF 类型筛选**

tag\_list 里可选的 ETF 类型的标签值

Python

    热门关注: package_us_v1_etf_hot
    银行ETF: package_us_v1_etf_bank
    债券ETF: package_us_v1_etf_bond
    缓冲型: package_us_v1_etf_buffer
    宽基指数型: package_us_v1_etf_index
    杠杆&反向型: package_us_v1_etf_leverage
    行业型: package_us_v1_etf_sector
    单只股票杠杆型: package_us_v1_etf_single_stock
    Cap型: package_us_v1_etf_market_cap
    主题类: package_us_v1_etf_thematic
    国际市场: package_us_v1_etf_international
    成长&价值型: package_us_v1_etf_growth
    大宗商品型: package_us_v1_etf_commodity
    ARK木头姐: package_us_v1_etf_ark
    波动率: package_us_v1_etf_volatility
    汇率型: package_us_v1_etf_currency
    另类投资: package_us_v1_etf_alternative

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.tiger_open_config import get_client_config
    from tigeropen.common.consts import Market
    from tigeropen.quote.domain.filter import StockFilter
    from tigeropen.common.consts.filter_fields import MultiTagField
    
    client_config = get_client_config(private_key_path='私钥路径', tiger_id='your tiger id', account='your account')
    quote_client = QuoteClient(client_config)
    
    def test_market_scanner_etf():
        # 按ETF类型筛选,这里筛选成长型和另类投资型ETF
        multi_tag_filter_etftype = StockFilter(MultiTagField.ETF_TYPE, tag_list=["package_us_v1_etf_growth", "package_us_v1_etf_alternative"])
        
        cursor_id = None
        page_size = 50
        # 是否为最后一页数据
        is_last_page = False
        # 筛选后的symbol列表
        scanner_result_symbols = set()
    
        while not is_last_page:
            result = quote_client.market_scanner(market=Market.US,
                                              filters=[multi_tag_filter_etftype],
                                              cursor_id=cursor_id,
                                              page_size=page_size)
            
            if result.total_page:
                for item in result.items:
                    symbol = item.symbol
                    market = item.market
                    etf_type_value = item[multi_tag_filter_etftype]
                    print(f'page:{result.page}, symbol:{symbol}, etf_type:{etf_type_value}')
                print(f'current page symbols:{result.symbols}')
                scanner_result_symbols.update(result.symbols)
                
            time.sleep(10)
            # 处理分页
            if result.cursor_id is None:
                is_last_page = True
            else:
                cursor_id = result.cursor_id
    
        print(f'scanned symbols:{scanner_result_symbols}')

* * *

get\_market\_scanner\_tags

[](./quote-scanner.md#get_market_scanner_tags)

-------------------------------------------------------------------------------------------------------

`QuoteClient.get_market_scanner_tags(self, market=Market.US, tag_fields=None)`

**说明**

获取多标签关联筛选字段的标签值，暂只支持获取行业和概念标签集合。

**参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| market | [tigeropen.common.consts.Market](./appendix-enum.md#market) | Yes | US 美股，SG 新股，HK港股 |
| tag\_fields | `list[tigeropen.common.consts.filter_fields.MultiTagField]` | Yes | 支持的字段枚举值：MultiTagField.Industry，MultiTagField.Concept |

**返回**

list. 其中每一项如下

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| market | str | 市场代码（US:美股，CN:沪深，HK:港股） |
| multi\_tag\_field | str | 多标签字段 |
| tag\_list | `list[str]` | 多标签字段可以用来过滤的标签集合 |

**示例**

Python

    from tigeropen.quote.quote_client import QuoteClient
    from tigeropen.common.consts import TradingSession, Market
    from tigeropen.common.consts.filter_fields import MultiTagField
    
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    client_config = TigerOpenClientConfig(props_path='/path/to/your/properties/file/')
    
    quote_client = QuoteClient(client_config)
    
    field_list = [ MultiTagField.Concept, MultiTagField.Industry]
    result = quote_client.get_market_scanner_tags(market=Market.US, fields=field_list)
    print(result)

**返回示例**

JSON

    [\
        {\
            "market": "US",\
            "multi_tag_field": "MultiTagField_Concept",\
            "tag_list": [\
                {\
                    "tag": "BK4565",\
                    "value": "NFT概念"\
                },\
                {\
                    "tag": "BK4564",\
                    "value": "太空概念"\
                },\
                {\
                    "tag": "BK4567",\
                    "value": "ESG概念"\
                },\
                {\
                    "tag": "BK4566",\
                    "value": "资本集团"\
                },\
                {\
                    "tag": "BK4568",\
                    "value": "美国抗疫概念"\
                },\
                {\
                    "tag": "BK4561",\
                    "value": "索罗斯持仓"\
                },\
                {\
                    "tag": "BK4560",\
                    "value": "网络安全概念"\
                }\
            ]\
        }\
    ]
