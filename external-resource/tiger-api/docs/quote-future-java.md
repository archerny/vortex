# 期货

获取期货交易所列表

[](./quote-future-java.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E4%BA%A4%E6%98%93%E6%89%80%E5%88%97%E8%A1%A8)

----------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FutureExchangeRequest**

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| secType | string | es  | 合约类型。"FUT": 期货, "FOP": 期货期权（暂未提供期货期权的行情） |
| lang | string | No  | 语言参数，对返回值中的"name"字段有影响，取值范围为："zh\_CN", "en\_US" |

**返回**

`FutureExchangeItem` 对象列表

`FutureExchangeItem` 对象具体字段说明如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| code | string | 交易所代码 |
| name | string | 交易所名称 |
| zoneId | string | 交易时区 |

**示例**

Java

    FutureExchangeResponse response = client.execute(FutureExchangeRequest.newRequest(SecType.FUT.name()));
    System.out.println(response.getFutureExchangeItems());

**返回示例**

JSON

    [\
      {\
        "code": "SGX",\
        "name": "SGX",\
        "zoneId": "Singapore"\
      },\
      {\
        "code": "HKEX",\
        "name": "HKEX",\
        "zoneId": "Asia/Hong_Kong"\
      },\
      {\
        "code": "CBOE",\
        "name": "CBOE",\
        "zoneId": "America/Chicago"\
      }\
    ]

* * *

获取交易所下的可交易合约

[](./quote-future-java.md#%E8%8E%B7%E5%8F%96%E4%BA%A4%E6%98%93%E6%89%80%E4%B8%8B%E7%9A%84%E5%8F%AF%E4%BA%A4%E6%98%93%E5%90%88%E7%BA%A6)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FutureContractByExchCodeRequest**

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| exchangeCode | string | Yes | 交易所代码 |
| lang | string | No  | 语言参数，对返回值中的"name"字段有影响，取值范围为："zh\_CN", "en\_US" |

**返回**

`FutureContractItem` 对象列表

`FutureContractItem` 对象具体字段说明如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| type | string | 期货合约对应的交易品种， 如 CL |
| trade | boolean | 是否可交易 |
| continuous | boolean | 是否连续合约 |
| name | string | 合约的名字，有简体和英文名，根据参数lang来返回 |
| currency | string | 交易的货币 |
| ibCode | string | 交易合约代码，下单时使用。如：CL |
| contractCode | string | 合约代码，如：CL1901 |
| contractMonth | string | 合约的交割月份 |
| lastTradingDate | string | 指合约到期月份最后交易的日期，最后交易日后尚未清算的期货合约，须通过相关『现货商品』或『现金结算』方式平仓，目前大部分期货商品最后交易日通常就是结算日。**有些商品第一通知日跟最后交易日是同一天，如欧元**  <br>对于现金交割的期货只要没过最后交易时间都可以正常开仓，**非现金交割的期货是按照最后交易时间和第一通知日其中较小者，在其前三个交易日开始就限制开仓** |
| lastTradingTimestamp | long | 最后交易日的精确截止时间 |
| firstNoticeDate | string | 第一通知日，指实物交割合约可以进行实物交割的日期，合约在第一通知日后无法开多仓。已有的多仓会在第一通知日之前（通常为前三个交易日）会被强制平仓。**非实物交割合约(如指数合约)该字段为空** |
| lastBiddingCloseTime | long | 竞价截止时间 |
| multiplier | double | 合约乘数，期货价格乘以合约乘数后，才是合约的面值，可以根据实物价格除上合约乘数，估算期货的合理价格 |
| exchange | string | 交易所代码 |
| minTick | double | 期货价格变动的最小报价单位，比如当前期货价格为 2000，minTick为100，则正确的报价包括 2100，2200，而 2005不满足要求 |
| deliveryMode | string | 交割方式 |
| productWorth | string | 合约规模 |
| productType | string | 合约类型 |
| productScale | string | 合约规格 |
| timeZone | string | 时区  |

**请求示例:**

Java

    FutureBatchContractResponse response = client.execute(FutureContractByExchCodeRequest.newRequest("CME"));
    System.out.println(response.getFutureContractItems());

**响应示例：**

JSON

    [\
      {\
        "type": "MEUR",\
        "name": "E-Micro EUR/USD - main",\
        "ibCode": "M6E",\
        "contractCode": "MEURmain",\
        "contractMonth": "",\
        "exchangeCode": "GLOBEX",\
        "multiplier": 12500,\
        "minTick": 0.0001,\
        "lastTradingDate": "",\
        "firstNoticeDate": "",\
        "lastBiddingCloseTime": 0,\
        "currency": "USD",\
        "continuous": false,\
        "trade": true\
      },\
      {\
        "type": "MEUR",\
        "name": "E-Micro EUR/USD - Jun 2022",\
        "ibCode": "M6E",\
        "contractCode": "MEUR2206",\
        "contractMonth": "202206",\
        "exchangeCode": "GLOBEX",\
        "multiplier": 12500,\
        "minTick": 0.0001,\
        "lastTradingDate": "20220613",\
        "firstNoticeDate": "",\
        "lastBiddingCloseTime": 0,\
        "currency": "USD",\
        "continuous": false,\
        "trade": true\
      },\
      {\
        "type": "MEUR",\
        "name": "E-Micro EUR/USD - Mar 2022",\
        "ibCode": "M6E",\
        "contractCode": "MEUR2203",\
        "contractMonth": "202203",\
        "exchangeCode": "GLOBEX",\
        "multiplier": 12500,\
        "minTick": 0.0001,\
        "lastTradingDate": "20220314",\
        "firstNoticeDate": "",\
        "lastBiddingCloseTime": 0,\
        "currency": "USD",\
        "continuous": false,\
        "trade": true\
      },\
      {\
        "type": "CHF",\
        "name": "Swiss Franc - Jun 2022",\
        "ibCode": "CHF",\
        "contractCode": "CHF2206",\
        "contractMonth": "202206",\
        "exchangeCode": "GLOBEX",\
        "multiplier": 125000,\
        "minTick": 0.0001,\
        "lastTradingDate": "20220613",\
        "firstNoticeDate": "",\
        "lastBiddingCloseTime": 0,\
        "currency": "USD",\
        "continuous": false,\
        "trade": true\
      },\
      {\
        "type": "CHF",\
        "name": "Swiss Franc - main",\
        "ibCode": "CHF",\
        "contractCode": "CHFmain",\
        "contractMonth": "",\
        "exchangeCode": "GLOBEX",\
        "multiplier": 125000,\
        "minTick": 0.0001,\
        "lastTradingDate": "",\
        "firstNoticeDate": "",\
        "lastBiddingCloseTime": 0,\
        "currency": "USD",\
        "continuous": false,\
        "trade": true\
      },\
      {\
        "type": "CHF",\
        "name": "Swiss Franc - Dec 2022",\
        "ibCode": "CHF",\
        "contractCode": "CHF2212",\
        "contractMonth": "202212",\
        "exchangeCode": "GLOBEX",\
        "multiplier": 125000,\
        "minTick": 0.0001,\
        "lastTradingDate": "20221219",\
        "firstNoticeDate": "",\
        "lastBiddingCloseTime": 0,\
        "currency": "USD",\
        "continuous": false,\
        "trade": true\
      }\
    ]

* * *

根据代码获取期货合约

[](./quote-future-java.md#%E6%A0%B9%E6%8D%AE%E4%BB%A3%E7%A0%81%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E5%90%88%E7%BA%A6)

--------------------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FutureContractByConCodeRequest**

> 遇到第一通知日、最后交易日如何处理：不管是进入第一通知日还是最后结算日后，主要交易月份都会转移到次月合约，使得将到期月份的流动性变差，因此建议不管是多单还是空单，在第一通知日前与最后交易日临近前，转仓或交易次月合约。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contractCode | string | Yes | 合约的 symbol，如 CN1901 |
| lang | string | No  | 语言参数，对返回值中的"name"字段有影响，取值范围为："zh\_CN", "en\_US" |

**返回**

`FutureContractItem` 对象

`FutureContractItem` 对象具体字段说明如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| type | string | 期货合约对应的交易品种， 如 CL |
| trade | boolean | 是否可交易 |
| continuous | boolean | 是否连续合约 |
| name | string | 合约的名字，有简体和英文名，根据参数lang来返回 |
| currency | string | 交易的货币 |
| ibCode | string | 交易合约代码，下单时使用。如：CL |
| contractCode | string | 合约代码，如，CL1901 |
| contractMonth | string | 合约的交割月份 |
| lastTradingDate | string | 指合约到期月份最后交易的日期，最后交易日后尚未清算的期货合约，须通过相关『现货商品』或『现金结算』方式平仓，目前大部分期货商品最后交易日通常就是结算日。**有些商品第一通知日跟最后交易日是同一天，如欧元**  <br>对于现金交割的期货只要没过最后交易时间都可以正常开仓，**非现金交割的期货是按照最后交易时间和第一通知日其中较小者，在其前三个交易日开始就限制开仓** |
| firstNoticeDate | string | 第一通知日，指实物交割合约可以进行实物交割的日期，合约在第一通知日后无法开多仓。已有的多仓会在第一通知日之前（通常为前三个交易日）会被强制平仓。**非实物交割合约(如指数合约)该字段为空** |
| lastBiddingCloseTime | long | 竞价截止时间 |
| multiplier | double | 合约乘数，期货价格乘以合约乘数后，才是合约的面值，可以根据实物价格除上合约乘数，估算期货的合理价格 |
| exchange | string | 交易所代码 |
| minTick | double | 期货价格变动的最小报价单位，比如当前期货价格为 2000，minTick为100，则正确的报价包括 2100，2200，而 2005不满足要求 |

**示例**

Java

    FutureContractResponse response = client.execute(FutureContractByConCodeRequest.newRequest("CN2203"));
    System.out.println(response.getFutureContractItem());

**响应示例**

JSON

    {
      "type": "CL",
      "name": "Light  Crude Oil - Mar 2022",
      "ibCode": "CL",
      "contractCode": "CL2203",
      "contractMonth": "202203",
      "exchangeCode": "NYMEX",
      "multiplier": "1000",
      "minTick": 0.01,
      "lastTradingDate": "20220222",
      "firstNoticeDate": "20220224",
      "lastBiddingCloseTime": 0,
      "currency": "USD",
      "continuous": false,
      "trade": true
    }

* * *

查询指定品种的当前合约

[](./quote-future-java.md#%E6%9F%A5%E8%AF%A2%E6%8C%87%E5%AE%9A%E5%93%81%E7%A7%8D%E7%9A%84%E5%BD%93%E5%89%8D%E5%90%88%E7%BA%A6)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FutureCurrentContractRequest**

**说明**

查询指定品种的当前合约，即合约主连。

**输入参数：**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| type | string | Yes | 期货合约对应的交易品种， 如 CL |
| lang | string | No  | 语言参数，对返回值中的"name"字段有影响，取值范围为："zh\_CN", "en\_US" |

**返回**

`FutureContractItem` 对象

`FutureContractItem` 对象具体字段说明如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| type | string | 期货合约对应的交易品种， 如 CL |
| trade | boolean | 是否可交易 |
| continuous | boolean | 是否连续合约 |
| name | string | 合约的名字，有简体和英文名，根据参数lang来返回 |
| currency | string | 交易的货币 |
| ibCode | string | 交易合约代码，下单时使用。如：CL |
| contractCode | string | 合约代码，如：CL1901 |
| contractMonth | string | 合约的交割月份 |
| lastTradingDate | string | 指合约到期月份最后交易的日期，最后交易日后尚未清算的期货合约，须通过相关『现货商品』或『现金结算』方式平仓，目前大部分期货商品最后交易日通常就是结算日。**有些商品第一通知日跟最后交易日是同一天，如欧元**  <br>对于现金交割的期货只要没过最后交易时间都可以正常开仓，**非现金交割的期货是按照最后交易时间和第一通知日其中较小者，在其前三个交易日开始就限制开仓** |
| lastTradingTimestamp | long | 最后交易日的精确截止时间 |
| firstNoticeDate | string | 第一通知日，指实物交割合约可以进行实物交割的日期，合约在第一通知日后无法开多仓。已有的多仓会在第一通知日之前（通常为前三个交易日）会被强制平仓。**非实物交割合约(如指数合约)该字段为空** |
| lastBiddingCloseTime | long | 竞价截止时间 |
| multiplier | double | 合约乘数，期货价格乘以合约乘数后，才是合约的面值，可以根据实物价格除上合约乘数，估算期货的合理价格 |
| exchange | string | 交易所代码 |
| minTick | double | 期货价格变动的最小报价单位，比如当前期货价格为 2000，minTick为100，则正确的报价包括 2100，2200，而 2005不满足要求 |
| deliveryMode | string | 交割方式 |
| productWorth | string | 合约规模 |
| productType | string | 合约类型 |
| productScale | string | 合约规格 |
| timeZone | string | 时区  |

**请求示例**

Java

    FutureContractResponse response = client.execute(FutureCurrentContractRequest.newRequest("CL"));
    System.out.println(response.getFutureContractItem());

**返回示例**

JSON

    {
      "type": "CL",
      "name": "Light Crude Oil - Mar 2022",
      "ibCode": "CL",
      "contractCode": "CL2203",
      "contractMonth": "202203",
      "exchangeCode": "NYMEX",
      "multiplier": 1000,
      "minTick": 0.01,
      "lastTradingDate": "20220222",
      "firstNoticeDate": "20220224",
      "lastBiddingCloseTime": 0,
      "currency": "USD",
      "continuous": false,
      "trade": true
    }

* * *

查询指定品种的全部合约

[](./quote-future-java.md#%E6%9F%A5%E8%AF%A2%E6%8C%87%E5%AE%9A%E5%93%81%E7%A7%8D%E7%9A%84%E5%85%A8%E9%83%A8%E5%90%88%E7%BA%A6)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FutureContractsRequest**

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| type | string | es  | 期货合约对应的交易品种， 如：CL |
| lang | string | Yes | 语言参数，对返回值中的"name"字段有影响，取值范围为："zh\_CN", "en\_US" |

**返回**

`FutureContractItem` 对象列表

`FutureContractItem` 对象具体字段说明如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| type | string | 期货合约对应的交易品种， 如：CL |
| trade | boolean | 是否可交易 |
| continuous | boolean | 是否连续合约 |
| name | string | 合约的名字，有简体和英文名，根据参数lang来返回 |
| currency | string | 交易的货币 |
| ibCode | string | 交易合约代码，下单时使用。如：CL |
| contractCode | string | 合约代码，如， CL1901 |
| contractMonth | string | 合约的交割月份 |
| lastTradingDate | string | 指合约到期月份最后交易的日期，最后交易日后尚未清算的期货合约，须通过相关『现货商品』或『现金结算』方式平仓，目前大部分期货商品最后交易日通常就是结算日。**有些商品第一通知日跟最后交易日是同一天，如欧元**  <br>对于现金交割的期货只要没过最后交易时间都可以正常开仓，**非现金交割的期货是按照最后交易时间和第一通知日其中较小者，在其前三个交易日开始就限制开仓** |
| lastTradingTimestamp | long | 最后交易日的精确截止时间 |
| firstNoticeDate | string | 第一通知日，指实物交割合约可以进行实物交割的日期，合约在第一通知日后无法开多仓。已有的多仓会在第一通知日之前（通常为前三个交易日）会被强制平仓。**非实物交割合约(如指数合约)该字段为空** |
| lastBiddingCloseTime | long | 竞价截止时间 |
| multiplier | double | 合约乘数，期货价格乘以合约乘数后，才是合约的面值，可以根据实物价格除上合约乘数，估算期货的合理价格 |
| exchange | string | 交易所代码 |
| minTick | double | 期货价格变动的最小报价单位，比如当前期货价格为 2000，minTick为100，则正确的报价包括 2100，2200，而 2005不满足要求 |
| deliveryMode | string | 交割方式 |
| productWorth | string | 合约规模 |
| productType | string | 合约类型 |
| productScale | string | 合约规格 |
| timeZone | string | 时区  |

**请求示例:**

Java

    FutureContractsResponse contractResponse = client.execute(FutureContractsRequest.newRequest("CN"));
    System.out.println(contractResponse.getFutureContractItems());

**响应示例：**

JSON

    [\
      {\
        "type": "CN",\
        "name": "China A50 Index - Aug 2022",\
        "ibCode": "XINA50",\
        "contractCode": "CN2208",\
        "contractMonth": "202208",\
        "exchangeCode": "SGX",\
        "multiplier": 1,\
        "minTick": 1,\
        "lastTradingDate": "20220830",\
        "firstNoticeDate": "",\
        "lastBiddingCloseTime": 0,\
        "currency": "USD",\
        "continuous": false,\
        "trade": true\
      },\
      {\
        "type": "CN",\
        "name": "China A50 Index - Sep 2022",\
        "ibCode": "XINA50",\
        "contractCode": "CN2209",\
        "contractMonth": "202209",\
        "exchangeCode": "SGX",\
        "multiplier": 1,\
        "minTick": 1,\
        "lastTradingDate": "20220929",\
        "firstNoticeDate": "",\
        "lastBiddingCloseTime": 0,\
        "currency": "USD",\
        "continuous": false,\
        "trade": true\
      },\
      {\
        "type": "CN",\
        "name": "China A50 Index - Oct 2022",\
        "ibCode": "XINA50",\
        "contractCode": "CN2210",\
        "contractMonth": "202210",\
        "exchangeCode": "SGX",\
        "multiplier": 1,\
        "minTick": 1,\
        "lastTradingDate": "20221028",\
        "firstNoticeDate": "",\
        "lastBiddingCloseTime": 0,\
        "currency": "USD",\
        "continuous": false,\
        "trade": true\
      },\
      {\
        "type": "CN",\
        "name": "China A50 Index - Dec 2022",\
        "ibCode": "XINA50",\
        "contractCode": "CN2212",\
        "contractMonth": "202212",\
        "exchangeCode": "SGX",\
        "multiplier": 1,\
        "minTick": 1,\
        "lastTradingDate": "20221229",\
        "firstNoticeDate": "",\
        "lastBiddingCloseTime": 0,\
        "currency": "USD",\
        "continuous": false,\
        "trade": true\
      },\
      {\
        "type": "CN",\
        "name": "China A50 Index - Mar 2023",\
        "ibCode": "XINA50",\
        "contractCode": "CN2303",\
        "contractMonth": "202303",\
        "exchangeCode": "SGX",\
        "multiplier": 1,\
        "minTick": 1,\
        "lastTradingDate": "20230330",\
        "firstNoticeDate": "",\
        "lastBiddingCloseTime": 0,\
        "currency": "USD",\
        "continuous": false,\
        "trade": true\
      }\
    ]

* * *

查询指定品种的连续合约

[](./quote-future-java.md#%E6%9F%A5%E8%AF%A2%E6%8C%87%E5%AE%9A%E5%93%81%E7%A7%8D%E7%9A%84%E8%BF%9E%E7%BB%AD%E5%90%88%E7%BA%A6)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FutureContinuousContractRequest**

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| type | string | Yes | 期货合约对应的交易品种， 如 CL |
| lang | string | Yes | 语言参数，对返回值中的"name"字段有影响，取值范围为："zh\_CN", "en\_US" |

**返回**

`FutureContractItem` 对象

`FutureContractItem` 对象具体字段说明如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| type | string | 期货合约对应的交易品种， 如：CL |
| trade | boolean | 是否可交易 |
| continuous | boolean | 是否连续合约 |
| name | string | 合约的名字，有简体和英文名，根据参数lang来返回 |
| currency | string | 交易的货币 |
| ibCode | string | 交易合约代码，下单时使用。如：CL |
| contractCode | string | 合约代码，如：CL1901 |
| contractMonth | string | 合约的交割月份 |
| lastTradingDate | string | 指合约到期月份最后交易的日期，最后交易日后尚未清算的期货合约，须通过相关『现货商品』或『现金结算』方式平仓，目前大部分期货商品最后交易日通常就是结算日。**有些商品第一通知日跟最后交易日是同一天，如欧元**  <br>对于现金交割的期货只要没过最后交易时间都可以正常开仓，**非现金交割的期货是按照最后交易时间和第一通知日其中较小者，在其前三个交易日开始就限制开仓** |
| lastTradingTimestamp | long | 最后交易日的精确截止时间 |
| firstNoticeDate | string | 第一通知日，指实物交割合约可以进行实物交割的日期，合约在第一通知日后无法开多仓。已有的多仓会在第一通知日之前（通常为前三个交易日）会被强制平仓。**非实物交割合约(如指数合约)该字段为空** |
| lastBiddingCloseTime | long | 竞价截止时间 |
| multiplier | double | 合约乘数，期货价格乘以合约乘数后，才是合约的面值，可以根据实物价格除上合约乘数，估算期货的合理价格 |
| exchange | string | 交易所代码 |
| minTick | double | 期货价格变动的最小报价单位，比如当前期货价格为 2000，minTick为100，则正确的报价包括 2100，2200，而 2005不满足要求 |
| deliveryMode | string | 交割方式 |
| productWorth | string | 合约规模 |
| productType | string | 合约类型 |
| productScale | string | 合约规格 |
| timeZone | string | 时区  |

**请求示例:**

Java

    FutureContractResponse cl = client.execute(FutureContinuousContractRequest.newRequest("CL"));
    System.out.println(cl.getFutureContractItem());

**响应示例：**

JSON

    {
      "type": "ES",
      "name": "E-mini S&P 500 - main",
      "ibCode": "ES",
      "contractCode": "ESmain",
      "contractMonth": "",
      "exchangeCode": "GLOBEX",
      "multiplier": 50,
      "minTick": 0.25,
      "lastTradingDate": "",
      "firstNoticeDate": "",
      "lastBiddingCloseTime": 0,
      "currency": "USD",
      "continuous": false,
      "trade": true
    }

* * *

查询期货主连的历史合约

[](./quote-future-java.md#%E6%9F%A5%E8%AF%A2%E6%9C%9F%E8%B4%A7%E4%B8%BB%E8%BF%9E%E7%9A%84%E5%8E%86%E5%8F%B2%E5%90%88%E7%BA%A6)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FutureHistoryMainContractRequest**

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contractCodes | array | Yes | 期货主合约代码列表，如ESmain |
| beginTime | long | Yes | 开始时间(不包含) |
| endTime | long | Yes | 结束时间(包含) |

**返回**

`FutureHistoryMainContractItem` 对象

`FutureHistoryMainContractItem` 具体字段说明如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| contractCode | string | 期货主合约代码 |
| mainReferItems | array | 主合约的历史合约列表，`FutureHistoryContractItem`字段参考下面说明 |

其中历史合约数据mainReferItems属性如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| time | long | 日期时间戳 |
| referContractCode | string | 主连合约对应的期货合约 |

**请求示例**

Java

    List<String> contractCodes = new ArrayList<>();
    contractCodes.add("ESmain");
    FutureHistoryMainContractRequest request = FutureHistoryMainContractRequest.newRequest(contractCodes,
      "2023-06-01", "2023-10-05", TimeZoneId.NewYork);
    FutureHistoryMainContractResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSONString(response));
    } else {
      System.out.println(response.getMessage());
    }

**响应示例**

JSON

    {
        "code":0,
        "data":[\
            {\
                "contractCode":"ESmain",\
                "mainReferItems":[\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1696453200000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1696366800000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1696280400000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1696021200000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1695934800000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1695848400000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1695762000000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1695675600000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1695416400000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1695330000000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1695243600000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1695157200000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1695070800000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1694811600000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1694725200000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1694638800000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1694552400000\
                    },\
                    {\
                        "referContractCode":"ES2312",\
                        "time":1694466000000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1694206800000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1694120400000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1694034000000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1693947600000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1693602000000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1693515600000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1693429200000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1693342800000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1693256400000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1692997200000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1692910800000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1692824400000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1692738000000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1692651600000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1692392400000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1692306000000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1692219600000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1692133200000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1692046800000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1691787600000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1691701200000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1691614800000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1691528400000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1691442000000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1691182800000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1691096400000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1691010000000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1690923600000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1690837200000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1690578000000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1690491600000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1690405200000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1690318800000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1690232400000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1689973200000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1689886800000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1689800400000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1689714000000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1689627600000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1689368400000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1689282000000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1689195600000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1689109200000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1689022800000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1688763600000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1688677200000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1688590800000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1688404500000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1688158800000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1688072400000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1687986000000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1687899600000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1687813200000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1687554000000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1687467600000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1687381200000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1687294800000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1686949200000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1686862800000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1686776400000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1686690000000\
                    },\
                    {\
                        "referContractCode":"ES2309",\
                        "time":1686603600000\
                    },\
                    {\
                        "referContractCode":"ES2306",\
                        "time":1686344400000\
                    },\
                    {\
                        "referContractCode":"ES2306",\
                        "time":1686258000000\
                    },\
                    {\
                        "referContractCode":"ES2306",\
                        "time":1686171600000\
                    },\
                    {\
                        "referContractCode":"ES2306",\
                        "time":1686085200000\
                    },\
                    {\
                        "referContractCode":"ES2306",\
                        "time":1685998800000\
                    },\
                    {\
                        "referContractCode":"ES2306",\
                        "time":1685739600000\
                    },\
                    {\
                        "referContractCode":"ES2306",\
                        "time":1685653200000\
                    }\
                ]\
            }\
        ],
        "message":"success",
        "sign":"H/m3GVmsGstQNJxnQrF4nNwkJ3EBJEIBTHMdxzvRKVdM5XtB+PhxlVJBSYclHSRkFn13ckgEr13pzGTrGYjM7cT5zXTTNn0wS0WtVsJoycaUoWqr8KT8P6B6cHq0Sj5LVA5nZlNpno33dplWMWNX3urDf6OiGtQ6J9/ZJcZnvxM=",
        "success":true,
        "timestamp":1696499214141
    }

* * *

查询指定期货合约的交易时间

[](./quote-future-java.md#%E6%9F%A5%E8%AF%A2%E6%8C%87%E5%AE%9A%E6%9C%9F%E8%B4%A7%E5%90%88%E7%BA%A6%E7%9A%84%E4%BA%A4%E6%98%93%E6%97%B6%E9%97%B4)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FutureTradingDateRequest**

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contractCode | string | Yes | 期货合约代码，如CL1901 |
| tradingDate | long | Yes | 交易日时间戳 |

**返回**

`FutureTradingDateItem` 对象

`FutureTradingDateItem` 具体字段说明如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| tradingTimes | array | 交易时间 |
| biddingTimes | array | 竞价时间 |
| timeSection | string | 所在交易时区 |

**请求示例**

Java

    FutureTradingDateResponse response = client.execute(FutureTradingDateRequest.newRequest("ES2203", System.currentTimeMillis()));
    System.out.println(response.getFutureTradingDateItem());
    

**响应示例**

Java

    FutureTradingDateItem{biddingTimes=[TimeSection{start=1644873300000, end=1644874200000}], tradingTimes=[TimeSection{start=1644793200000, end=1644873300000}, TimeSection{start=1644874200000, end=1644876000000}], timeSection='America/Chicago'}

* * *

期货实时行情

[](./quote-future-java.md#%E6%9C%9F%E8%B4%A7%E5%AE%9E%E6%97%B6%E8%A1%8C%E6%83%85)

----------------------------------------------------------------------------------------------------------------------

**对应的请求类：FutureRealTimeQuoteRequest**

**说明**

获取期货实时行情

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contractCodes | array | Yes | 合约代码列表，支持查询主连合约，如，CL1901/CLmain |

**返回**

`FutureRealTimeItem` 对象列表

`FutureRealTimeItem` 具体字段说明如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| contractCode | string | 合约代码 |
| latestPrice | double | 最新成交价格 |
| latestSize | double | 最新价的成交量 |
| latestTime | long | 最新价的成交时间 |
| bidPrice | double | 买盘价(一档) |
| bidSize | long | 买盘数量(一档) |
| askPrice | double | 卖盘价(一档) |
| askSize | long | 卖盘数量(一档) |
| volume | long | 当日累计成交手数 |
| openInterest | no  | 未平仓合约数量 |
| openInterestChange | no  | 未平仓合约数量变化 |
| open | double | 开盘价 |
| high | double | 最高价 |
| low | double | 最低价 |
| settlement | double | 结算价，在未生成结算价时返回0 |
| limitUp | double | 涨停价 |
| limitDown | double | 跌停价 |

**示例**

Java

    List<String> contractCodes = new ArrayList<>();
    contractCodes.add("CL1902");
    
    FutureRealTimeQuoteResponse response = client.execute(FutureRealTimeQuoteRequest.newRequest(contractCodes));
    System.out.println(response.getFutureRealTimeItems());

**返回示例**

JSON

    {
        "code": 0,
        "timestamp": 1545102059229,
        "message": "success",
        "data": [{\
            "contractCode": "CN1901",\
            "askPrice": 49.4,\
            "askSize": 2,\
            "bidPrice": 49.39,\
            "bidSize": 4,\
            "latestSize": 1,\
            "latestPrice": 49.39,\
            "volume": -18140,\
            "openInterest": 72189,\
            "openInterestChange": 0,\
            "settlement": 49.88,\
            "high": 49.59,\
            "low": 49.14,\
            "latestTime": 1545102035000,\
            "open": 49.16,\
            "limitUp": 55.88,\
            "limitDown": 43.88\
        }]
    }

* * *

获取期货深度数据

[](./quote-future-java.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E6%B7%B1%E5%BA%A6%E6%95%B0%E6%8D%AE)

------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FutureDepthRequest**

**说明**

获取期货深度数据。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contractCodes | array | Yes | 合约代码列表，支持查询主连合约，如：CL1901/CLmain |

**返回**

`FutureDepthItem` 对象列表

`FutureDepthItem` 具体字段说明如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| contractCode | string | 合约代码 |
| contractId | string | 合约Id |
| ask | `List<FutureDepthAskBidItem>` | 卖盘分档 |
| bid | `List<FutureDepthAskBidItem>` | 买盘分档 |

`FutureDepthAskBidItem`具体字段说明如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| price | BigDecimal | 委托价 |
| volume | Long | 委托量 |

**示例**

Java

    FutureDepthRequest request = FutureDepthRequest.newRequest(Collections.singletonList("XWmain"));
    FutureRealTimeQuoteResponse response = client.execute(request);
    System.out.println(response.getFutureDepthItems());

**返回示例**

JSON

    {
      "code" : 0,
      "message" : "success",
      "timestamp" : 1754983558519,
      "sign" : "mfa4bnsB6ZNezDuPzqiKt7fIz/pVVfB/xcXoCPZh7q+4TXeXvAu6FgGd1NmsCMf10JQNXibes/+ayMrVxdT3VVsOqVfOAqaeyyB6cDISdIajArHlIVc16eCtv2s2ceoAL+XGFpNTJNof9TH0b9SzL5RYZy7xhVBe4MGGeXXdFfs=",
      "data" : [ {\
        "lang" : null,\
        "contractId" : "b1846ff7d24744a3a6b16b11ebeb16ee",\
        "contractCode" : "XWmain",\
        "ask" : [ {\
          "price" : 5.12000,\
          "volume" : 4\
        }, {\
          "price" : 5.12125,\
          "volume" : 5\
        }, {\
          "price" : 5.12250,\
          "volume" : 4\
        }],\
        "bid" : [ {\
          "price" : 5.11625,\
          "volume" : 7\
        }, {\
          "price" : 5.11500,\
          "volume" : 6\
        }, {\
          "price" : 5.11375,\
          "volume" : 4\
        }],\
        "account" : null\
      } ],
      "success" : true
    }

* * *

获取期货逐笔数据

[](./quote-future-java.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7%E9%80%90%E7%AC%94%E6%95%B0%E6%8D%AE)

------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FutureTickRequest**

**说明**

索引每天北京时间凌晨6:00 重置为 0。前一天的逐笔数据会在最早的交易时段（竞价或交易）开始前一分钟清除。新的逐笔数据会在新交易时段开始后重新记录。 该重置每天仅发生一次，即同一交易日内的多个交易时段不会触发额外的重置。

> ⚠️
> 
> **Caution**
> 
> 一旦前一天的逐笔数据被清除，API 将无法再访问这些数据。例如：GC2504 的逐笔数据可在早上 5:59 前访问，但会在早上 6:00 重置为下标 0。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contractCode | string | Yes | 期货合约代码，如：CL1901 |
| beginIndex | long | Yes | 起始索引，首次请求时beginIndex和endIndex可以设置为-1，首次请求会返回最新的逐笔数据，**后续可以传上次返回的最新索引值+1** |
| endIndex | long | Yes | 结束索引，**如果结束索引和起始索引的差值大于1000，会按照最大1000条返回**。当结束索引或起始索引其中一个设置为-1时，会从非-1的一端开始查询，并返回limit条逐笔数据。 |
| limit | Int | No  | 默认为200，最大限制为1000条 |

**begin\_index 和 end\_index参数使用说明**

| 查询方式 | beginIndex | endIndex | 描述  |
| --- | --- | --- | --- |
| 从前往后查逐笔记录 | 具体数值 | \-1 | 举例：beginIndex=10，endIndex=-1，limit=20，返回从10到29的20条记录。 |
| 从后往前查询逐笔记录 | \-1 | 具体数值 | 举例：beginIndex=-1，endIndex=29，limit=20，返回从10到29的20条记录。 |
| 查询最新逐笔记录 | \-1 | \-1 | 返回limit条最新的逐笔记录。 |
| 查询区间索引 | 具体数值 | 具体数值 | 举例：beginIndex=10, endIndex=100 ，会返回包含10到100的 91条记录。如果limit设置为20，则会返回10到29的20条记录。 |

**返回**

`FutureTickBatchItem` 对象

`FutureTickBatchItem`具体字段说明如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| contractCode | string | 合约代码 |
| index | integer | 索引  |
| price | double | 成交价 |
| volume | long | 成交量 |
| time | long | 时间  |

**示例**

Java

    List<String> contractCodes = new ArrayList<>();
    contractCodes.add("CL1902");
    
    FutureTickResponse response = client.execute(FutureTickRequest.newRequest("CL2209", 10L, 100L,20));
    System.out.println(response.getFutureTickItems());

**返回示例**

JSON

    {
    	"code": 0,
    	"data": {
    		"contractCode": "CL2209",
    		"items": [{\
    			"index": 10,\
    			"price": 87.91,\
    			"time": 1660600802000,\
    			"volume": 6\
    		}, {\
    			"index": 11,\
    			"price": 87.90,\
    			"time": 1660600802000,\
    			"volume": 1\
    		}, {\
    			"index": 12,\
    			"price": 87.93,\
    			"time": 1660600802000,\
    			"volume": 1\
    		}, {\
    			"index": 13,\
    			"price": 87.91,\
    			"time": 1660600803000,\
    			"volume": 4\
    		}, {\
    			"index": 14,\
    			"price": 87.89,\
    			"time": 1660600804000,\
    			"volume": 1\
    		}, {\
    			"index": 15,\
    			"price": 87.90,\
    			"time": 1660600804000,\
    			"volume": 1\
    		}, {\
    			"index": 16,\
    			"price": 87.92,\
    			"time": 1660600804000,\
    			"volume": 2\
    		}, {\
    			"index": 17,\
    			"price": 87.91,\
    			"time": 1660600804000,\
    			"volume": 1\
    		}, {\
    			"index": 18,\
    			"price": 87.93,\
    			"time": 1660600805000,\
    			"volume": 8\
    		}, {\
    			"index": 19,\
    			"price": 87.93,\
    			"time": 1660600805000,\
    			"volume": 10\
    		}, {\
    			"index": 20,\
    			"price": 87.93,\
    			"time": 1660600805000,\
    			"volume": 7\
    		}, {\
    			"index": 21,\
    			"price": 87.93,\
    			"time": 1660600805000,\
    			"volume": 8\
    		}, {\
    			"index": 22,\
    			"price": 87.93,\
    			"time": 1660600805000,\
    			"volume": 7\
    		}, {\
    			"index": 23,\
    			"price": 87.95,\
    			"time": 1660600806000,\
    			"volume": 1\
    		}, {\
    			"index": 24,\
    			"price": 87.94,\
    			"time": 1660600806000,\
    			"volume": 1\
    		}, {\
    			"index": 25,\
    			"price": 87.95,\
    			"time": 1660600807000,\
    			"volume": 1\
    		}, {\
    			"index": 26,\
    			"price": 87.98,\
    			"time": 1660600807000,\
    			"volume": 1\
    		}, {\
    			"index": 27,\
    			"price": 87.99,\
    			"time": 1660600807000,\
    			"volume": 1\
    		}, {\
    			"index": 28,\
    			"price": 88.00,\
    			"time": 1660600807000,\
    			"volume": 1\
    		}, {\
    			"index": 29,\
    			"price": 87.97,\
    			"time": 1660600807000,\
    			"volume": 2\
    		}]
    	},
    	"message": "success",
    	"sign": "llqaLoA5mQZN+nx1pGq5/mp1Ds5Z77uCvD+wM4TM7jtyvW",
    	"success": true,
    	"timestamp": 1660620843648
    }

* * *

获取期货K线数据

[](./quote-future-java.md#%E8%8E%B7%E5%8F%96%E6%9C%9F%E8%B4%A7k%E7%BA%BF%E6%95%B0%E6%8D%AE)

----------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：FutureKlineRequest**

**说明**

提供了热门合约近10年的日级别K线数据，以及全部合约2017年8月至今的分钟级数据。

返回结果是从endTime开始按时间倒序的数据集合。

对于1分钟K线，如果在这1分钟内没有成交，这一分钟K线数据会空缺；在最近的这一分钟内有成交后接口才能拉取到这1分钟的K线数据，如果在第50秒时产生第一笔交易，在50秒之前拉取不到最新点的数据。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| contractCodes | array | Yes | 合约代码列表，支持查询主连合约，如，CL1901/CLmain |
| period | string | Yes | K线周期,取值范围:"min", "3min", "5min", "10min","15min", "30min", "45min", "60min","2hour", "3hour", "4hour", "6hour","day", "week", "month" |
| beginTime | long | Yes | 开始时间(包含) |
| endTime | long | Yes | 结束时间(不包含) |
| limit | int | No  | 请求条数限制，默认200，最大限制：1000 |
| pageToken | string | No  | 分页查询token（只支持单个合约，指定endTime的查询），使用pageToken分页拉取数据时其他查询条件不能改变 |

**返回**

`FutureKlineBatchItem` 对象列表

`FutureKlineBatchItem`具体字段说明如下：

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| contractCode | string | 合约代码 |
| nextPageToken | string | 查询下一页的token（只支持单个合约代码，endTime不为null和-1时才有效），如果没有更多数据返回null |
| items | array | K线数组,字段参考下面说明 |

其中K线数据items属性如下：

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| lastTime | long | 最新价的成交时间 |
| volume | long | 成交手数 |
| openInterest | no  | 未平仓合约数量 |
| open | double | 开盘价 |
| close | double | 收盘价 |
| time | long | 时间  |
| high | double | 最高价 |
| low | double | 最低价 |
| settlement | double | 结算价，在未生成结算价时返回0 |

**示例**

Java

    List<String> contractCodes = new ArrayList<>();
    contractCodes.add("CL1901");
    
    FutureKlineResponse response = client.execute(
        FutureKlineRequest.newRequest(contractCodes, FutureKType.min15, 1535634249489L,
            1538807049489L, 200));
    System.out.println(response.getFutureKlineItems());

**返回示例**

JSON

    {
            "code": 0,
            "timestamp": 1545105097358,
            "message": "success",
            "data": [{\
                    "contractCode": "CL1901",\
                    "items": [{\
                                    "lastTime": 1545083998000,\
                                    "volume": 124206,\
                                    "high": 51.87,\
                                    "openInterest": 90329,\
                                    "low": 4901,\
                                    "time": 1545084000000,\
                                    "close": 49.16,\
                                    "open": 51.25,\
                                    "settlement": 49.88\
                            },\
                            {\
                                    "lastTime": 1544824796000,\
                                    "volume": 434074,\
                                    "high": 52.95,\
                                    "openInterest": 131753,\
                                    "low": 5084,\
                                    "time": 1544824800000,\
                                    "close": 51.23,\
                                    "open": 52.83,\
                                    "settlement": 51.2\
                            },\
                            {\
                                    "lastTime": 1544738399000,\
                                    "volume": 593178,\
                                    "high": 53.27,\
                                    "openInterest": 186783,\
                                    "low": 5035,\
                                    "time": 1544738400000,\
                                    "close": 52.85,\
                                    "open": 51.2,\
                                    "settlement": 52.58\
                            }\
                    ]\
            }]
    }

**PageToken示例**

Java

        List<String> contractCodes = new ArrayList<>();
        contractCodes.add("NGmain");
        // pagetoken only for single symbol and specified endTime
        FutureKlineRequest request = FutureKlineRequest.newRequest(contractCodes, FutureKType.day,
            1650920400000L, 1651870900000L, 3);
    
        int count = 1;
        while (true) {
          FutureKlineResponse response = client.execute(request);
          System.out.println("search time:" + count + ", success:" + response.isSuccess() + ", msg:" + response.getMessage());
          if (!response.isSuccess()) {
            break;
          }
          System.out.println(response.getFutureKlineItems());
          if (response.getFutureKlineItems().size() == 0) {
            break;
          }
          String nextPageToken = response.getFutureKlineItems().get(0).getNextPageToken();
          if (nextPageToken == null) {
            break;
          }
          count++;
          // 10 times per minute
          try {
            TimeUnit.SECONDS.sleep(6);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          // set nextPageToken and search next page data
          request.withPageToken(nextPageToken);
        }

  
