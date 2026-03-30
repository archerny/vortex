# 取消或修改订单

取消订单

[](./modify-order-java.md#%E5%8F%96%E6%B6%88%E8%AE%A2%E5%8D%95)

--------------------------------------------------------------------------------------------------

**对应的请求类：TigerHttpRequest(MethodName.CANCEL\_ORDER)**

**说明**

撤销已下的订单。撤单同下单类似，为异步执行，调用本命令后表示撤单请求发送成功，撤单的执行会异步处理 使用 `TradeOrderRequest` 提交订单后，被提交的订单会根据不同情况进入多种状态。已成交或被系统拒绝的订单无法被撤销，只有订单处于已提交或部分成交的状态，才可被撤销。请参考 [TigerHttpRequest(MethodName.ORDERS)](./orderinfo-java.md#orders)
 方法的说明来了解可能出现的订单状态。

对于批量下单，可用 [TigerHttpRequest(MethodName.ACTIVE\_ORDERS)](./orderinfo-java.md#orders)
 取得待成交的订单列表，依次撤单。对于已经请求过撤单的订单，不要重复请求，可在撤单命令执行后，等待一段时间再次检查待成交的订单。

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 用户授权账户：DU575569 |
| id  | long | Yes | 订单号，下单时返回的ID |
| secret\_key | string | No  | 交易员密钥，机构用户专用 |

**返回**

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| id  | long | 唯一单号，可用于查询订单/修改订单/取消订单 |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    TigerHttpRequest request = new TigerHttpRequest(MethodName.CANCEL_ORDER);
    
    String bizContent = TradeParamBuilder.instance()
        .account("DU575569")
        .id(147070683398615040L)
        .buildJson();
    
    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);
    JSONObject data = JSON.parseObject(response.getData());
    Long id = data.getLong("id");

**返回示例**

JSON

    {
      "code": 0,
      "message": null,
      "timestamp": 1525938835697,
      "data": {
          "id":147070683398615040
      }
    }

* * *

修改订单

[](./modify-order-java.md#%E4%BF%AE%E6%94%B9%E8%AE%A2%E5%8D%95)

--------------------------------------------------------------------------------------------------

**对应的请求类：TigerHttpRequest(MethodName.MODIFY\_ORDER)**

**说明**

修改订单，订单类型不支持修改

**参数**

| 参数  | 类型  | 是否必填 | 描述  |
| --- | --- | --- | --- |
| account | string | Yes | 用户授权账户：DU575569 |
| id  | long | Yes | 订单id，下单时返回 |
| total\_quantity | long | No  | 订单数量 (港股，沪港通，窝轮，牛熊证有最小数量限制) |
| total\_quantity\_scale | int | No  | 下单数量的偏移量，默认为0。碎股单的total\_quantity 和 total\_quantity\_scale 结合起来代表真实下单数量，如 total\_quantity=111 total\_quantity\_scale=2，那么，真实 quantity=111\*10^(-2)=1.11 |
| limit\_price | double | No  | 限价，当 order\_type 为LMT,STP,STP\_LMT时该参数必需 |
| aux\_price | double | No  | 股票止损价。当 order\_type 为STP,STP\_LMT时该参数必需。当 order\_type 为 TRAIL时，为跟踪差额 |
| trailing\_percent | double | No  | 跟踪止损单-百分比 。当 order\_type 为 TRAIL时,aux\_price和trailing\_percent两者互斥，优先使用trailing\_percent |
| secret\_key | string | No  | 交易员密钥，机构用户专用 |

**返回**

| 名称  | 类型  | 说明  |
| --- | --- | --- |
| id  | long | 唯一单号，可用于查询订单/修改订单/取消订单 |

**示例**

Java

    TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
          ClientConfig.DEFAULT_CONFIG);
    TigerHttpRequest request = new TigerHttpRequest(MethodName.MODIFY_ORDER);
    
    String bizContent = TradeParamBuilder.instance()
        .account("DU575569")
        .id(147070683398615040L)
        .totalQuantity(200)
        .limitPrice(60.0)
        .buildJson();
    
    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);
    JSONObject data = JSON.parseObject(response.getData());
    Long id = data.getLong("id");

**返回示例**

JSON

    {
      "code": 0,
      "message": null,
      "timestamp": 1525938835697,
      "data": {
          "id":147070683398615040
      }
    }

  
