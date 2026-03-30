# 选股器

选股器

[](./quote-scanner-java.md#%E9%80%89%E8%82%A1%E5%99%A8)

-----------------------------------------------------------------------------------------

**对应的请求类：MarketScannerRequest**

**说明**

通过不同的技术指标条件来扫描全市场行情，帮助您筛选出满足特定投资需求的标的列表。

技术指标条件包含如下几类：基础指标、累积指标、财务指标，多标签指标，具体参数含义请参考下面的说明。

**参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| market | enum | Yes | US 美股，SG 新股，HK 港股 |
| baseFilterList | `list<BaseFilter>` | No  | 简单指标筛选条件，包括价格（高开低收，最新价等），成交量，股本，市值，涨跌幅，市盈率，换手率等因子。筛选字段含义说明： [筛选字段说明](./appendix-enum-java.md#scanner-base-stock-field) |
| accumulateFilterList | `list<AccumulateFilter>` | No  | 累积指标筛选条件，包括累积涨跌幅，资产增长率，净利润增长率，每股收益，净利润，营业利润，营业收入，ROA（净资产收益率），经营现金流，资产负债率等。累积指标周期可以是：近五分钟，近5日，10日，20日，近半年，一年，两年，五年，一季度报，三季度报，中报等等。筛选字段含义说明：[筛选字段说明](./appendix-enum-java.md#scanner-accumulate-field) |
| financialFilterList | `list<FinancialFilter>` | No  | 财务指标筛选条件，包括毛利，净利率，总负债/股东权益，总负债/总资产，流动比率，资产回报率，净利润，经营现金流，总资产，港股通净买入额，年化收益率等等。财务指标目前只支持LTM（最近 12 个月的年报指标）类型的财报查询。筛选字段含义说明：[筛选字段说明](./appendix-enum-java.md#scanner-financial-field) |
| multiTagsRelationFilterList | `list<MultiTagsRelationFilter>` | No  | 多标签关联关系筛选条件，基于行业，概念，历史股价新高（当天股价和历史价格相比），52周内股价新高（当天股价和最近52周相比），是否为OTC，是否支持期权，股票类型（股票，ETF），是否破发等指标来选股。筛选字段含义说明：[筛选字段说明](./appendix-enum-java.md#scanner-multitag-field) |
| sortFieldData | object | No  | 排序字段对象，主要包含三个属性，如下所示 |
| ∟ fieldName | string | No  | 排序字段名称 |
| ∟ fieldType | string | No  | 排序属性类别，排序属性类别枚举：[字段类别](./quote-scanner-java.md#field-belong-type) |
| ∟ sortDir | string | No  | 排序方向，包括：不排序，升序，降序，排序方向枚举：[排序字段](./appendix-enum-java.md#sort-dir) |
| page | int | Yes | 当前页码（从 0 开始） |
| cursorId | String | Yes | 游标 ID，用于基于游标的分页查询，cursorId 是上一页数据返回的分页标识，获取下一页时需传入（首页传 null ） |
| pageSize | int | Yes | 每页返回的数据量，最大支持配置成：200 |

> 筛选参数中的价格字段对应的币种和该标的所在市场的货币类型保持一致，比如美股：USD，港股：HKD，新股：SGD 等。

### 

筛选字段说明

[](./quote-scanner-java.md#%E7%AD%9B%E9%80%89%E5%AD%97%E6%AE%B5%E8%AF%B4%E6%98%8E)

Java

    /** 基础指标过滤器 **/
    BaseFilter {
        /** StockField 简单属性 */
        private StockField fieldName;
        /** 区间下限（闭区间），不传代表下限为 -∞ */
        private Double filterMin;
        /** 区间上限（闭区间），不传代表上限为 +∞ */
        private Double filterMax;
        /** 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认筛选， */
        /** 该字段作用：1.调试代码时，无法确定是哪个指标生效，可以通过设置该字段逐一确认每个属性筛选出哪些标的。2.只想通过某几个条件筛选，但是其他条件需要查询对应指标值用于展示，可以把展示类指标的isNoFilter 设置为 True，即不筛选。**/
        private boolean isNoFilter = false;
    }
    
    /** 累积指标过滤器 **/
    AccumulateFilter {
      /** AccumulateField 累积属性 */
      private AccumulateField fieldName;
      /** 区间下限（闭区间），不传代表下限为 -∞ 如果为百分位数，不需要加%，例如10%，数值为10即可 */
      private Double filterMin;
      /** 区间上限（闭区间），不传代表上限为 +∞ */
      private Double filterMax;
      /** 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认筛选 */
      /** 该字段作用：1.调试代码时，无法确定是哪个指标生效，可以通过设置该字段逐一确认每个属性筛选出哪些标的。
      2.只想通过某几个条件筛选，但是其他条件需要查询对应指标值用于展示，可以把展示类指标的isNoFilter 设置为 True，即不筛选。**/
      private boolean isNoFilter = false;
      /** 时间周期 AccumulatePeriod 非必传项 */
      private AccumulatePeriod period;
    }
    
    /** 财务指标过滤器 **/
    FinancialFilter {
        /** FinancialField 财务属性 */
        private FinancialField fieldName;
        /** 区间下限（闭区间），不传代表下限为 -∞ 如果为百分位数，不需要加%，例如10%，数值为10即可 */
        private Double filterMin;
        /** 区间上限（闭区间），不传代表上限为 +∞ */
        private Double filterMax;
        /** 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认筛选 */
        /** 该字段作用：1.调试代码时，无法确定是哪个指标生效，可以通过设置该字段逐一确认每个属性筛选出哪些标的。
        2.只想通过某几个条件筛选，但是其他条件需要查询对应指标值用于展示，可以把展示类指标的isNoFilter 设置为 True，即不筛选。**/
        private boolean isNoFilter = false;
        /** FinancialQuarter 财报累积时间 */
        private FinancialPeriod quarter;
    }
    
    /** 多标签指标过滤器 **/
    MultiTagsRelationFilter {
        /** MultiTagField 标签枚举值 */
        private MultiTagField fieldName;
        /** 多个tag列表 */
        private List<String> tagList;
        /** 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认筛选 */
        /** 该字段作用：1.调试代码时，无法确定是哪个指标生效，可以通过设置该字段逐一确认每个属性筛选出哪些标的。
        2.只想通过某几个条件筛选，但是其他条件需要查询对应指标值用于展示，可以把展示类指标的isNoFilter 设置为 True，即不筛选。**/
        private boolean isNoFilter = false;
    }

### 

字段类别

[](./quote-scanner-java.md#%E5%AD%97%E6%AE%B5%E7%B1%BB%E5%88%AB)

Java

    public enum FieldBelongType {
        StockField_Type(1), //基础指标类型
        AccumulateField_Type(2), //累积指标类型
        FinancialField_Type(3), //财务指标类型
        MultiTagField_Type(5); //多标签筛选类型
    }

### 

排序字段

[](./quote-scanner-java.md#%E6%8E%92%E5%BA%8F%E5%AD%97%E6%AE%B5)

Java

    public enum SortDir {
        SortDir_No(0), // 不排序
        SortDir_Ascend(1), // 升序
        SortDir_Descend(2); // 降序
    }

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.MarketScannerResponse` [source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/MarketScannerResponse.java)

其结构如下:

Java

    public class MarketScannerResponse extends TigerResponse {
        @JSONField(name = "data")
        private MarketScannerBatchItem marketScannerBatchItem;
    }
    
    public class MarketScannerBatchItem extends ApiModel {
        /** 请求的页面索引，默认为0 */
        private int page;
        /** 总页数 */
        private int totalPage;
        /** 该条件请求所有数据的个数 */
        private int totalCount;
        /** 每页条数 */
        private int pageSize;
        /** 游标ID，用于基于游标的分页查询 */
        private String cursorId;
        /** 返回的股票数据列表 */
        private List<MarketScannerItem> data;
    }
    
    public class MarketScannerItem implements Serializable {
        /** 市场 */
        private Market market;
        private String symbol;
        /** 筛选后的:简单指标属性数据 */
        private List<MarketIndicatorValue> baseDataList;
        /** 筛选后的:累积指标属性数据 */
        private List<MarketIndicatorValue> accumulateDataList;
        /** 筛选后的:财务指标属性数据 */
        private List<MarketIndicatorValue> financialDataList;
        /** 筛选后的:多标签层面过滤器 */
        private List<MarketIndicatorValue> multiTagDataList;
    }
    
    public class MarketIndicatorValue implements Serializable {
        /** 指标索引 **/
        private Integer index;
        /** 指标名称 **/
        private String name;
        /** 指标值 **/
        private Object value;
    }

**示例**

Java

    //构建基础指标筛选参数列表
    List<BaseFilter> baseFilterList = new ArrayList<>();
    baseFilterList.add(BaseFilter.builder()
                       .fieldName(StockField.StockField_MarketValue)
                       .filterMin(1000000000D)
                       .filterMax(2000000000D)
                       .build());
    
    // 多标签指标
    List<String> conceptTagList = new ArrayList<>();
    // "BK1549" 连锁快餐 BK1541：汽车股  BK1545:内银股，BK1512：特斯拉概念股
    conceptTagList.add("BK1549");
    conceptTagList.add("BK1541");
    conceptTagList.add("BK1545");
    conceptTagList.add("BK1512");
    List<MultiTagsRelationFilter> multiTagsRelationFilter = new ArrayList<>();
    multiTagsRelationFilter.add(MultiTagsRelationFilter.builder()
            .fieldName(MultiTagField.MultiTagField_Concept)
            .tagList(conceptTagList)
            .build());
    // 今天创历史新高 1:是， 0:否
    List<String> todayHistoryHighTagList = new ArrayList<>();
    todayHistoryHighTagList.add("0");
    multiTagsRelationFilter.add(MultiTagsRelationFilter.builder()
            .fieldName(MultiTagField.MultiTagField_Today_HistoryHigh)
            .tagList(todayHistoryHighTagList)
            .build());
    
    //构建选股请求类
    String cursorId = null;
    MarketScannerRequest marketScannerRequest =  MarketScannerRequest.newRequest(
      Market.HK, baseFilterList, null, null, multiTagsRelationFilter, null, 1, 200, null);
    //提交选股请求，并获取返回结果
    MarketScannerResponse marketScannerResponse = client.execute(marketScannerRequest);
    // 用于下一次请求
    cursorId = ((MarketScannerResponse)marketScannerResponse).getMarketScannerBatchItem().getCursorId();
    System.out.println(JSONObject.toJSONString(marketScannerResponse));

**返回示例**

JSON

    {
        "code":0,
        "data":{
            "items":[\
                {\
                    "accumulateDataList":[\
    \
                    ],\
                    "baseDataList":[\
                        {\
                            "index":17,\
                            "name":"marketValue",\
                            "value":1124284984.6\
                        }\
                    ],\
                    "financialDataList":[\
    \
                    ],\
                    "market":"HK",\
                    "multiTagDataList":[\
                        {\
                            "index":21,\
                            "value":"HK"\
                        },\
                        {\
                            "index":2,\
                            "name":"concept",\
                            "value":"BK1549"\
                        },\
                        {\
                            "index":14,\
                            "name":"todayHistoryHigh",\
                            "value":"0"\
                        }\
                    ],\
                    "symbol":"00538"\
                },\
                {\
                    "accumulateDataList":[\
    \
                    ],\
                    "baseDataList":[\
                        {\
                            "index":17,\
                            "name":"marketValue",\
                            "value":1567588638\
                        }\
                    ],\
                    "financialDataList":[\
    \
                    ],\
                    "market":"HK",\
                    "multiTagDataList":[\
                        {\
                            "index":21,\
                            "value":"HK"\
                        },\
                        {\
                            "index":2,\
                            "name":"concept",\
                            "value":"BK1549,BK1602,BK1578"\
                        },\
                        {\
                            "index":14,\
                            "name":"todayHistoryHigh",\
                            "value":"0"\
                        }\
                    ],\
                    "symbol":"00052"\
                }\
            ],
            "page":0,
            "pageSize":10,
            "cursorId": "f987bf0b-e420-40cc-921d-2fd2afd87319",
            "totalCount":2,
            "totalPage":1
        },
        "message":"success",
        "sign":"BItYmJiFiduYBzIiOfwnVN+oqZV7AqE0/EdZk5fBzF9tST5aaLP88ytHjB1SdQaAVU1SepCtYbPMKoVzzm8HzrDb2SjiXonfFqMTQ7xWrjvmCX4oWmDh7IRx8NHJbuappChidGsz7sRUntwmxGg1CYYs6b2ArMo6HH1Ukq+Ncr8=",
        "success":true,
        "timestamp":1681896201613
    }

**示例2**

按 ETF 类型筛选。可选 ETF 类型的标签值：

枚举

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

Java

    List<String> etfTagList = new ArrayList<>();
    etfTagList.add("package_us_v1_etf_hot");
    List<MultiTagsRelationFilter> multiTagsRelationFilter = new ArrayList<>();
    multiTagsRelationFilter.add(MultiTagsRelationFilter.builder()
        .fieldName(MultiTagField.MultiTagField_ETF_TYPE)
        .tagList(etfTagList)
        .build());

* * *

获取多标签关联筛选字段的标签值

[](./quote-scanner-java.md#%E8%8E%B7%E5%8F%96%E5%A4%9A%E6%A0%87%E7%AD%BE%E5%85%B3%E8%81%94%E7%AD%9B%E9%80%89%E5%AD%97%E6%AE%B5%E7%9A%84%E6%A0%87%E7%AD%BE%E5%80%BC)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：MarketScannerTagsRequest**

**说明**

获取多标签关联筛选字段的标签值，暂只支持获取行业和概念标签集合。

**参数**

| 参数  | 类型  | 是否必填 | 说明  |
| --- | --- | --- | --- |
| market | Market | Yes | 常用市场枚举值：US 美股，HK港股，CN A股，枚举值详见： [市场枚举](./appendix-enum-java.md#market) |
| multi\_tag\_field\_list | `List<MultiTagField>` | Yes | 支持的字段枚举值：MultiTagField\_Industry，MultiTagField\_Concept |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.MarketScannerTagsResponse` [source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/MarketScannerTagsResponse.java)

其结构如下:

Java

    public class MarketScannerTagsResponse extends TigerResponse {
      @JSONField(name = "data")
      private List<MarketScannerTagItem> items;
    }

返回数据可用`MarketScannerTagsResponse.getItems()`方法访问，返回为`MarketScannerTagItem`对象列表，其中 `com.tigerbrokers.stock.openapi.client.https.domain.quote.item.MarketScannerTagItem` 属性如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| market | string | 市场代码（US:美股，CN:沪深，HK:港股） |
| multiTagField | string | 多标签字段，与请求参数multi\_tag\_field\_list一致。 |
| tagList | `List<TagValue>` | 多标签字段multiTagField可以用来过滤的标签集合 |

`TagValue`的具体字段可通过对象的属性，如`tagValue.tag`进行访问

**示例**

Java

    List<MultiTagField> multiTagFieldList = new ArrayList<>();
    // 获取行业的标签集合
    multiTagFieldList.add(MultiTagField.MultiTagField_Industry);
    
    MarketScannerTagsRequest request =  MarketScannerTagsRequest.newRequest(
            Market.HK, multiTagFieldList);
    MarketScannerTagsResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSONString(response)));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
        "code":0,
        "data":[\
            {\
                "market":"HK",\
                "multiTagField":"MultiTagField_Industry",\
                "tagList":[\
                    {\
                        "tag":"BK1175",\
                        "value":"铝"\
                    },\
                    {\
                        "tag":"BK1174",\
                        "value":"农用农业机械"\
                    },\
                    {\
                        "tag":"BK1173",\
                        "value":"海港与服务"\
                    },\
                    {\
                        "tag":"BK1172",\
                        "value":"赌场与赌博"\
                    },\
                    {\
                        "tag":"BK1171",\
                        "value":"建筑机械与重型卡车"\
                    },\
                    {\
                        "tag":"BK1170",\
                        "value":"电子元件"\
                    },\
                    {\
                        "tag":"BK1179",\
                        "value":"电子设备和仪器"\
                    },\
                    {\
                        "tag":"BK1178",\
                        "value":"煤与消费用燃料"\
                    },\
                    {\
                        "tag":"BK1177",\
                        "value":"大卖场与超市"\
                    },\
                    {\
                        "tag":"BK1186",\
                        "value":"特殊金融服务"\
                    },\
                    {\
                        "tag":"BK1185",\
                        "value":"公路与铁路"\
                    },\
                    {\
                        "tag":"BK1184",\
                        "value":"人寿与健康保险"\
                    },\
                    {\
                        "tag":"BK1183",\
                        "value":"工业机械"\
                    },\
                    {\
                        "tag":"BK1182",\
                        "value":"多样化房地产投资信托"\
                    },\
                    {\
                        "tag":"BK1181",\
                        "value":"经销商"\
                    },\
                    {\
                        "tag":"BK1180",\
                        "value":"啤酒酿造商"\
                    },\
                    {\
                        "tag":"BK1189",\
                        "value":"医疗保健技术"\
                    },\
                    {\
                        "tag":"BK1188",\
                        "value":"消费信贷"\
                    },\
                    {\
                        "tag":"BK1187",\
                        "value":"安全和报警服务"\
                    },\
                    {\
                        "tag":"BK1153",\
                        "value":"再保险"\
                    },\
                    {\
                        "tag":"BK1152",\
                        "value":"无线电信业务"\
                    },\
                    {\
                        "tag":"BK1151",\
                        "value":"航空货运与物流"\
                    },\
                    {\
                        "tag":"BK1150",\
                        "value":"人力资源与就业服务"\
                    },\
                    {\
                        "tag":"BK1159",\
                        "value":"石油与天然气的炼制和营销"\
                    },\
                    {\
                        "tag":"BK1158",\
                        "value":"综合支持服务"\
                    },\
                    {\
                        "tag":"BK1157",\
                        "value":"纸材料包装"\
                    },\
                    {\
                        "tag":"BK1156",\
                        "value":"资产管理与托管银行"\
                    },\
                    {\
                        "tag":"BK1155",\
                        "value":"信息科技咨询与其它服务"\
                    },\
                    {\
                        "tag":"BK1154",\
                        "value":"新能源发电业者"\
                    },\
                    {\
                        "tag":"BK1164",\
                        "value":"酒店、度假村与豪华游轮"\
                    },\
                    {\
                        "tag":"BK1163",\
                        "value":"半导体产品"\
                    },\
                    {\
                        "tag":"BK1162",\
                        "value":"技术产品经销商"\
                    },\
                    {\
                        "tag":"BK1161",\
                        "value":"生物科技"\
                    },\
                    {\
                        "tag":"BK1160",\
                        "value":"纺织品"\
                    },\
                    {\
                        "tag":"BK1169",\
                        "value":"半导体设备"\
                    },\
                    {\
                        "tag":"BK1168",\
                        "value":"鞋类"\
                    },\
                    {\
                        "tag":"BK1167",\
                        "value":"电子制造服务"\
                    },\
                    {\
                        "tag":"BK1166",\
                        "value":"石油与天然气的储存和运输"\
                    },\
                    {\
                        "tag":"BK1165",\
                        "value":"办公服务与用品"\
                    },\
                    {\
                        "tag":"BK1131",\
                        "value":"金融交易所和数据"\
                    },\
                    {\
                        "tag":"BK1252",\
                        "value":"数据处理及外包服务"\
                    },\
                    {\
                        "tag":"BK1130",\
                        "value":"出版"\
                    },\
                    {\
                        "tag":"BK1251",\
                        "value":"工业房地产信托"\
                    },\
                    {\
                        "tag":"BK1250",\
                        "value":"办公室房地产信托"\
                    },\
                    {\
                        "tag":"BK1139",\
                        "value":"轮胎与橡胶"\
                    },\
                    {\
                        "tag":"BK1138",\
                        "value":"综合性资本市场"\
                    },\
                    {\
                        "tag":"BK1137",\
                        "value":"广告"\
                    },\
                    {\
                        "tag":"BK1258",\
                        "value":"房地产运营公司"\
                    },\
                    {\
                        "tag":"BK1136",\
                        "value":"多元化保险"\
                    },\
                    {\
                        "tag":"BK1257",\
                        "value":"货物陆运"\
                    },\
                    {\
                        "tag":"BK1135",\
                        "value":"建筑材料"\
                    },\
                    {\
                        "tag":"BK1256",\
                        "value":"零售房地产信托"\
                    },\
                    {\
                        "tag":"BK1134",\
                        "value":"互联网服务与基础架构"\
                    },\
                    {\
                        "tag":"BK1255",\
                        "value":"旅客陆运"\
                    },\
                    {\
                        "tag":"BK1133",\
                        "value":"综合电信业务"\
                    },\
                    {\
                        "tag":"BK1254",\
                        "value":"商业和住宅抵押贷款融资"\
                    },\
                    {\
                        "tag":"BK1132",\
                        "value":"食品零售"\
                    },\
                    {\
                        "tag":"BK1253",\
                        "value":"交易和支付处理服务"\
                    },\
                    {\
                        "tag":"BK1142",\
                        "value":"互联网与直销零售"\
                    },\
                    {\
                        "tag":"BK1141",\
                        "value":"生命科学工具和服务"\
                    },\
                    {\
                        "tag":"BK1140",\
                        "value":"环境与设施服务"\
                    },\
                    {\
                        "tag":"BK1149",\
                        "value":"通信设备"\
                    },\
                    {\
                        "tag":"BK1148",\
                        "value":"建筑与工程"\
                    },\
                    {\
                        "tag":"BK1147",\
                        "value":"投资银行业与经纪业"\
                    },\
                    {\
                        "tag":"BK1146",\
                        "value":"金属与玻璃容器"\
                    },\
                    {\
                        "tag":"BK1145",\
                        "value":"广播"\
                    },\
                    {\
                        "tag":"BK1144",\
                        "value":"机动车零配件与设备"\
                    },\
                    {\
                        "tag":"BK1143",\
                        "value":"调查和咨询服务"\
                    },\
                    {\
                        "tag":"BK1119",\
                        "value":"汽车制造商"\
                    },\
                    {\
                        "tag":"BK1118",\
                        "value":"多种化学制品"\
                    },\
                    {\
                        "tag":"BK1239",\
                        "value":"汽车零售"\
                    },\
                    {\
                        "tag":"BK1230",\
                        "value":"工业集团企业"\
                    },\
                    {\
                        "tag":"BK1117",\
                        "value":"系统软件"\
                    },\
                    {\
                        "tag":"BK1238",\
                        "value":"石油与天然气的勘探与生产"\
                    },\
                    {\
                        "tag":"BK1116",\
                        "value":"电气部件与设备"\
                    },\
                    {\
                        "tag":"BK1237",\
                        "value":"包装食品与肉类"\
                    },\
                    {\
                        "tag":"BK1115",\
                        "value":"家庭装饰品"\
                    },\
                    {\
                        "tag":"BK1236",\
                        "value":"铁路"\
                    },\
                    {\
                        "tag":"BK1114",\
                        "value":"服装零售"\
                    },\
                    {\
                        "tag":"BK1235",\
                        "value":"区域性银行"\
                    },\
                    {\
                        "tag":"BK1113",\
                        "value":"化肥与农用药剂"\
                    },\
                    {\
                        "tag":"BK1234",\
                        "value":"白银"\
                    },\
                    {\
                        "tag":"BK1112",\
                        "value":"石油与天然气钻井"\
                    },\
                    {\
                        "tag":"BK1233",\
                        "value":"餐馆"\
                    },\
                    {\
                        "tag":"BK1111",\
                        "value":"摩托车制造商"\
                    },\
                    {\
                        "tag":"BK1232",\
                        "value":"复合型公用事业"\
                    },\
                    {\
                        "tag":"BK1110",\
                        "value":"商品化工"\
                    },\
                    {\
                        "tag":"BK1231",\
                        "value":"综合性银行"\
                    },\
                    {\
                        "tag":"BK1129",\
                        "value":"特种化学制品"\
                    },\
                    {\
                        "tag":"BK1120",\
                        "value":"有线和卫星"\
                    },\
                    {\
                        "tag":"BK1241",\
                        "value":"消闲设施"\
                    },\
                    {\
                        "tag":"BK1240",\
                        "value":"房地产开发"\
                    },\
                    {\
                        "tag":"BK1128",\
                        "value":"电影和娱乐"\
                    },\
                    {\
                        "tag":"BK1249",\
                        "value":"综合零售"\
                    },\
                    {\
                        "tag":"BK1127",\
                        "value":"财产与意外伤害保险"\
                    },\
                    {\
                        "tag":"BK1248",\
                        "value":"酒店及度假村房地产信托"\
                    },\
                    {\
                        "tag":"BK1247",\
                        "value":"药品零售"\
                    },\
                    {\
                        "tag":"BK1125",\
                        "value":"独立电力生产商与能源贸易商"\
                    },\
                    {\
                        "tag":"BK1246",\
                        "value":"综合货品商店"\
                    },\
                    {\
                        "tag":"BK1124",\
                        "value":"贸易公司与经销商"\
                    },\
                    {\
                        "tag":"BK1245",\
                        "value":"工业房地产投资信托"\
                    },\
                    {\
                        "tag":"BK1123",\
                        "value":"航空公司"\
                    },\
                    {\
                        "tag":"BK1122",\
                        "value":"百货商店"\
                    },\
                    {\
                        "tag":"BK1243",\
                        "value":"工业气体"\
                    },\
                    {\
                        "tag":"BK1121",\
                        "value":"食品分销商"\
                    },\
                    {\
                        "tag":"BK1242",\
                        "value":"烟草"\
                    },\
                    {\
                        "tag":"BK1219",\
                        "value":"保健护理服务"\
                    },\
                    {\
                        "tag":"BK1218",\
                        "value":"互助储蓄与抵押信贷金融服务"\
                    },\
                    {\
                        "tag":"BK1217",\
                        "value":"办公房地产投资信托"\
                    },\
                    {\
                        "tag":"BK1216",\
                        "value":"多元化房地产活动"\
                    },\
                    {\
                        "tag":"BK1215",\
                        "value":"特殊消费者服务"\
                    },\
                    {\
                        "tag":"BK1214",\
                        "value":"家用电器"\
                    },\
                    {\
                        "tag":"BK1213",\
                        "value":"消闲用品"\
                    },\
                    {\
                        "tag":"BK1212",\
                        "value":"农产品"\
                    },\
                    {\
                        "tag":"BK1211",\
                        "value":"航天航空与国防"\
                    },\
                    {\
                        "tag":"BK1210",\
                        "value":"林业产品"\
                    },\
                    {\
                        "tag":"BK1109",\
                        "value":"应用软件"\
                    },\
                    {\
                        "tag":"BK1108",\
                        "value":"非传统电信运营商"\
                    },\
                    {\
                        "tag":"BK1229",\
                        "value":"房地产经营公司"\
                    },\
                    {\
                        "tag":"BK1107",\
                        "value":"住宅建筑"\
                    },\
                    {\
                        "tag":"BK1228",\
                        "value":"教育服务"\
                    },\
                    {\
                        "tag":"BK1106",\
                        "value":"商业印刷"\
                    },\
                    {\
                        "tag":"BK1227",\
                        "value":"服装、服饰与奢侈品"\
                    },\
                    {\
                        "tag":"BK1105",\
                        "value":"建筑产品"\
                    },\
                    {\
                        "tag":"BK1226",\
                        "value":"综合性石油与天然气企业"\
                    },\
                    {\
                        "tag":"BK1104",\
                        "value":"互动家庭娱乐"\
                    },\
                    {\
                        "tag":"BK1225",\
                        "value":"钢铁"\
                    },\
                    {\
                        "tag":"BK1103",\
                        "value":"海运"\
                    },\
                    {\
                        "tag":"BK1224",\
                        "value":"专卖店"\
                    },\
                    {\
                        "tag":"BK1102",\
                        "value":"居家用品"\
                    },\
                    {\
                        "tag":"BK1223",\
                        "value":"纸制品"\
                    },\
                    {\
                        "tag":"BK1101",\
                        "value":"家庭装潢零售"\
                    },\
                    {\
                        "tag":"BK1222",\
                        "value":"医疗保健用品"\
                    },\
                    {\
                        "tag":"BK1100",\
                        "value":"医疗保健设备"\
                    },\
                    {\
                        "tag":"BK1221",\
                        "value":"燃气公用事业"\
                    },\
                    {\
                        "tag":"BK1220",\
                        "value":"陆运"\
                    },\
                    {\
                        "tag":"BK1209",\
                        "value":"保健护理机构"\
                    },\
                    {\
                        "tag":"BK1208",\
                        "value":"其它综合性金融服务"\
                    },\
                    {\
                        "tag":"BK1207",\
                        "value":"个人用品"\
                    },\
                    {\
                        "tag":"BK1206",\
                        "value":"铜"\
                    },\
                    {\
                        "tag":"BK1205",\
                        "value":"重型电气设备"\
                    },\
                    {\
                        "tag":"BK1204",\
                        "value":"酒店及度假村房地产投资信托"\
                    },\
                    {\
                        "tag":"BK1203",\
                        "value":"家用器具与特殊消费品"\
                    },\
                    {\
                        "tag":"BK1202",\
                        "value":"石油天然气设备与服务"\
                    },\
                    {\
                        "tag":"BK1201",\
                        "value":"家庭装饰零售"\
                    },\
                    {\
                        "tag":"BK1200",\
                        "value":"软饮料"\
                    },\
                    {\
                        "tag":"BK1098",\
                        "value":"零售业房地产投资信托"\
                    },\
                    {\
                        "tag":"BK1097",\
                        "value":"房地产服务"\
                    },\
                    {\
                        "tag":"BK1096",\
                        "value":"消费电子产品"\
                    },\
                    {\
                        "tag":"BK1095",\
                        "value":"互动媒体与服务"\
                    },\
                    {\
                        "tag":"BK1094",\
                        "value":"电脑与电子产品零售"\
                    },\
                    {\
                        "tag":"BK1099",\
                        "value":"电力公用事业"\
                    },\
                    {\
                        "tag":"BK1197",\
                        "value":"保健护理产品经销商"\
                    },\
                    {\
                        "tag":"BK1196",\
                        "value":"机场服务"\
                    },\
                    {\
                        "tag":"BK1195",\
                        "value":"保险经纪商"\
                    },\
                    {\
                        "tag":"BK1194",\
                        "value":"水公用事业"\
                    },\
                    {\
                        "tag":"BK1193",\
                        "value":"电脑硬件、储存设备及电脑周边"\
                    },\
                    {\
                        "tag":"BK1192",\
                        "value":"酿酒商与葡萄酒商"\
                    },\
                    {\
                        "tag":"BK1191",\
                        "value":"制药"\
                    },\
                    {\
                        "tag":"BK1190",\
                        "value":"多种金属与采矿"\
                    },\
                    {\
                        "tag":"BK1199",\
                        "value":"多领域控股"\
                    },\
                    {\
                        "tag":"BK1198",\
                        "value":"黄金"\
                    }\
                ]\
            }\
        ],
        "message":"success",
        "sign":"HsjJnD0p7sr9Te7HUdVKme48f6plwUEOUvuSVQM8uq9NKyn+xhF2VRTHz5Qc+5e33h5gcHXzRCEyLLLX9byknKgbDMzrFldkGgjaIot4JS06C1hcvhSuzHHBTuL31C4bqwBjhDrH4n3zhUqTajDr1R8V12UxDqgGFSB3ojhzqYA=",
        "success":true,
        "timestamp":1683169980464
    }
