# 通用

抢占行情权限

[](./quote-common-java.md#%E6%8A%A2%E5%8D%A0%E8%A1%8C%E6%83%85%E6%9D%83%E9%99%90)

----------------------------------------------------------------------------------------------------------------------

**对应的请求类：TigerHttpRequest(MethodName.GRAB\_QUOTE\_PERMISSION)**

**说明**

当同一账号在多台设备同时使用时，行情数据仅在主设备上返回。若需在其他设备上查看行情，需执行”行情权限抢占“，将当前设备设为主设备；若不切换设备，则无需进行此操作。

启动时默认执行一次抢占行情权限，如需配置启动时不执行抢占行情，可在获取 TigerHttpClient 实例前配置`isAutoGrabPermission = false`。

Java

    ClientConfig.DEFAULT_CONFIG.isAutoGrabPermission = false;
    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(ClientConfig.DEFAULT_CONFIG);

**参数**

无

**返回**

| 字段名称 | 类型  | 说明  |
| --- | --- | --- |
| name | string | 权限名称，具体权限取值参见下方说明 |
| expireAt | long | 过期时间，时间戳格式，如为-1，表示无限制 |

name 字段对应的权限名称枚举值说明：

| name字段取值 | 说明  |
| --- | --- |
| usQuoteBasic | 美股L1行情权限 |
| usStockQuoteLv2Totalview | 美股L2行情权限 |
| hkStockQuoteLv2 | 大陆地区用户赠送的港股L2权限 |
| hkStockQuoteLv2Global | 非大陆地区用户购买的港股L2权限 |
| usOptionQuote | 美股期权L1行情权限 |
| CBOEFuturesQuoteLv2 | 芝加哥期权交易所L2权限 |
| HKEXFuturesQuoteLv2 | 香港期货交易所L2权限 |
| SGXFuturesQuoteLv2 | 新加坡交易所L2权限 |
| OSEFuturesQuoteLv2 | 大阪交易所L2权限权限 |

**示例**

Java

    TigerHttpRequest request = new TigerHttpRequest(MethodName.GRAB_QUOTE_PERMISSION);
    String bizContent = AccountParamBuilder.instance()
            .buildJson();
    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);

**返回示例**

JSON

    {
      "code": 0,
      "message": "success",
      "timestamp": 1525938835697,
      "data": [\
        {\
          "name": "usQuoteBasic",\
          "expireAt": 1621931026000\
        }\
      ]
    }

* * *

获取行情权限列表

[](./quote-common-java.md#%E8%8E%B7%E5%8F%96%E8%A1%8C%E6%83%85%E6%9D%83%E9%99%90%E5%88%97%E8%A1%A8)

------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：TigerHttpRequest(MethodName.GET\_QUOTE\_PERMISSION)**

**说明**

查询当前所拥有的行情权限。

**参数**

无

**返回**

| 字段名称 | 类型  | 说明  |
| --- | --- | --- |
| name | string | 权限名称 |
| expireAt | long | 过期时间，时间戳格式，如为 -1，表示无限制 |

**示例**

Java

    TigerHttpRequest request = new TigerHttpRequest(MethodName.GET_QUOTE_PERMISSION);
    String bizContent = AccountParamBuilder.instance()
            .buildJson();
    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);

**返回示例**

JSON

    {
      "code": 0,
      "message": "success",
      "timestamp": 1651734899995,
      "data": [\
        {\
          "name": "usStockQuote",\
          "expireAt": 1698767999000\
        },\
        {\
          "name": "usStockQuoteLv2Arca",\
          "expireAt": 1698767999000\
        },\
        {\
          "name": "usStockQuoteLv2Totalview",\
          "expireAt": 1698767999000\
        },\
        {\
          "name": "hkStockQuoteLv2",\
          "expireAt": 1698767999000\
        },\
        {\
          "name": "usOptionQuote",\
          "expireAt": 1698767999000\
        },\
        {\
          "name": "hkStockQuoteLv2",\
          "expireAt": -1\
        }\
      ]
    }

* * *

刷新token

[](./quote-common-java.md#%E5%88%B7%E6%96%B0token)

----------------------------------------------------------------------------------------

**对应的请求类：UserTokenRefreshRequest**

**说明**

**只有香港牌照 TBHK 需要使用 Token（用户牌照可以登录开发者信息页面查看）**。

刷新 Token 接口，只有本地 Token 有效才能刷新成功，如果 Token 已失效，需要到开发者信息注册页面刷新 Token 后导出到本地文件`tiger_openapi_token.properties`后使用。 刷新成功后会同时更新本地`tiger_openapi_token.properties`文件，并更新内存中 ClientConfig 的 Token 值，可配置自动刷新周期的天数（refreshTokenIntervalDays）和具体时间（refreshTokenTime）。如需自行刷新 Token，请配置`ClientConfig.DEFAULT_CONFIG.isAutoRefreshToken = false`，并自行刷新Token。 Token 有效期为 15 天，SDK 默认不刷新。

**参数**

无，每次请求 SDK 会把 Token 自动添加到 Http 请求头部`Authorization`中，服务端会对 Token 进行校验。

**返回**

| 字段名称 | 类型  | 说明  |
| --- | --- | --- |
| tigerId | string | tigerId |
| license | string | 牌照信息 |
| token | string | token字符串 |
| createTime | long | 创建时间 |
| expiredTime | long | 过期时间 |

**示例**

Java

        UserTokenRefreshRequest request = new UserTokenRefreshRequest();
        UserTokenResponse response = TigerHttpClient.getInstance().execute(request);
        if (response.isSuccess()) {
          System.out.println(JSONObject.toJSONString(response));
          List<RefreshTokenCallback> callbackList = TokenManager.getInstance().getCallbackList();
          for (RefreshTokenCallback callback : callbackList) {
            try {
              System.out.println(callback.getClass() + " tokenChange() is called");
              callback.tokenChange(ClientConfig.DEFAULT_CONFIG,
                  ClientConfig.DEFAULT_CONFIG.token,
                  response.getUserToken());
            } catch (Throwable th) {
              th.printStackTrace();
            }
          }
        } else {
          System.out.println("response error:" + response.getMessage());
        }

**返回示例**

JSON

    {
        "code":0,
        "data":{
            "createTime":1676547570673,
            "expiredTime":1677152370673,
            "license":"TBHK",
            "tigerId":"20150001",
            "token":"MTY3NjU0NzU3MDY3MywxNjc3MTUyMzcwNjczCJ/FLxjALbWg0cVW3R4bHg=="
        },
        "message":"success",
        "sign":"Yufg4/bYSeuvZz3b+7MsSAde+lgUCNJskle1zbOSxta66b9h0LjfZAmHNpx1tz4HnPK1AGfS9cdNaC964ts7Uakvr/kKzFEN3y5CfB8UgRsPKSR75z1GwSFq5v6gc/1hEAdrFdcHTmRbHhT362E5GYEuvvlfwIEX60/GYveSHIo=",
        "success":true,
        "timestamp":1676547570681
    }

* * *

获取历史行情额度

[](./quote-common-java.md#%E8%8E%B7%E5%8F%96%E5%8E%86%E5%8F%B2%E8%A1%8C%E6%83%85%E9%A2%9D%E5%BA%A6)

------------------------------------------------------------------------------------------------------------------------------------------

**对应的请求类：KlineQuotaRequest**

**说明**

根据用户等级统计用户已使用和剩余可订阅的 symbol 个数（同一股票的不同期权只占用一个symbol，其他规则可参考[历史行情限制&订阅限制](./permission.md#history_quote_permission)
）

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| with\_details | bool | No  | 是否返回已请求的symbol详情，默认不返回 |

**返回**

`com.tigerbrokers.stock.openapi.client.https.response.quote.KlineQuotaResponse`[source](https://github.com/tigerfintech/openapi-java-sdk/blob/master/src/main/java/com/tigerbrokers/stock/openapi/client/https/response/quote/KlineQuotaResponse.java)

返回数据可通过`KlineQuotaResponse.getQuotaItems()`方法访问，返回`QuotaItem`对象列表，其中`com.tigerbrokers.stock.openapi.client.https.domain.quote.item.QuotaItem` 属性如下:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| used | int | 已使用数量 |
| remain | int | 剩余数量 |
| method | String | api 接口名（kline：股票K线； future\_kline：期货K线； option\_kline：期权K线；) |
| symbolDetails | `List<SymbolDetail>` | 已使用的标的列表，包括每个标的的最后拉取时间 |

`SymbolDetail`类型:

| 字段  | 类型  | 说明  |
| --- | --- | --- |
| code | string | 股票代码 |
| lastRequestTimestamp | string | 最后一次拉取的时间字符串 |

**示例**

Java

    KlineQuotaRequest request = KlineQuotaRequest.newRequest(Boolean.TRUE);
    
    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    KlineQuotaResponse response = client.execute(request);
    if (response.isSuccess()) {
      System.out.println(JSONObject.toJSON(response));
    } else {
      System.out.println("response error:" + response.getMessage());
    }

**返回示例**

JSON

    {
      "code": 0,
      "message": "success",
      "timestamp": 1750851389623,
      "sign": "myCpSB+GFgzlgOMnyIyD6yXib0m5LjKvRq+gT3sARfX4Z6AgNib/s0mpVniQs+H85yP1GlLHmAE/pCCKPNvGKyITynUiPWAIippg/o3Z4W//KlA868LaukA0Y+3fmqB4pnDQgoMH4zdcKEGgYS6X6bTPDCPTWCDAk43rXGJW94g=",
      "quotaItems": [\
        {\
          "remain": 200,\
          "used": 0,\
          "method": "kline",\
          "symbolDetails": []\
        },\
        {\
          "remain": 20,\
          "used": 0,\
          "method": "future_kline",\
          "symbolDetails": []\
        },\
        {\
          "remain": 197,\
          "used": 3,\
          "method": "option_kline",\
          "symbolDetails": [\
            {\
              "code": "TCH.HK",\
              "lastRequestTimestamp": "1750851341848"\
            },\
            {\
              "code": "ALB.HK",\
              "lastRequestTimestamp": "1750851341848"\
            },\
            {\
              "code": "LNI.HK",\
              "lastRequestTimestamp": "1750851341848"\
            }\
          ]\
        }\
      ],
      "success": true
    }
