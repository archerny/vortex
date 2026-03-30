# 错误代码

所有 API 请求均返回以下公共参数，用于描述请求结果：

*   **code**：业务状态码
*   **message**：状态描述

| code | message | message detail | 备注  |
| --- | --- | --- | --- |
| 0   | success |     | 请求成功 |
| 1   | server error |     | 当遇到请求参数无法处理时，或服务端本身服务异常时，会返回该状态码 |
| 2   | network read time out |     | 请求网络超时，需要检测网络状况如何，可以考虑和OpenAPI的服务就近部署，或使用稳定的专线服务 |
| 4   | access forbidden |     | 拒绝访问，可能的原因：  <br>1.如果设置的IP白名单，但是发起请求的机器并不在白名单内  <br>2.可能由于账号的频繁请求，或错误请求过多，导致被加入了黑名单  <br>3.长连接验证签名失败  <br>4.长连接订阅的标的过多，超过了限额  <br>5.机构开发者查询子账户数据时，未传secret\_key |
|     | \-- | You don't have permission to subscribe quotes, please purchase api quotes permission first | 长链接订阅时，没有权限，可订阅数量为0 |
|     | \-- | According to your user level, you can only subscribe to xxx symbols | 长链接订阅时，超过了可订阅标的数量 |
| 5   | rate limit error |     | 请求频率超过限制，这种情况下返回的http状态码是429，同时在错误消息中会返回单位时间内的限制次数说明 |
| 1000 | common param error |     | 解析公共参数出现会导致返回该导致，可能的原因：  <br>1.请求的方法不支持(method参数错误)  <br>2.请求的URL地址错误  <br>3.请求的参数不是标准JSON格式  <br>4.公共参数（非bizContent）校验出错，比如timestamp格式错误，字段为空，sign签名字段校验出错等 |
|     | \-- | invalid symbols | 标的代码有误 |
|     | \-- | the current requested method does not support | 请求的api方法有误。一般出现在调用了不支持的sdk方法，或者未用sdk，写错了api方法 |
|     | \-- | time parse error, support time format is 'yyyy-MM-dd HH:mm:ss' | 日期参数格式有误 |
|     | \-- | failed to verify signature, please make sure you use the correct rsa private key | 签名错误, 一般是密钥有误 |
|     | \-- | request parameters cannot be empty | 请求参数不能为空 |
|     | \-- | field 'xxx' cannot be empty | 字段不能为空 |
|     | \-- | failed to get developer information | 未获取到开发者信息。一般是tigerid不存在或请求到了错误的环境(比如正式的tigerid请求到了sandbox，或US的tigerid请求到了非US) |
|     | \-- | get device information error | 设备id有误 |
| 1010 | biz param error |     | 解析bizContent参数出错，可能的原因：  <br>业务参数校验出错（比如begin\_time格式错误，sec\_type不支持） |
|     | \-- | failed to parse parameters in 'biz\_content' | biz\_content 参数内容有误 |
|     | \-- | field 'secret\_key' or 'account' invalid | 业务参数错误：机构账户secret\_key 或account 错误 |
|     | \-- | 'market' xxxx not supported, all supported market include:\[HK\] | 市场不支持 |
|     | \-- | 'page\_token' is used in the wrong way, when this parameter is used, other parameters cannot be changed | 分页参数page\_token 使用有误，使用page\_token 时，应该保持除该参数之外的其他参数不变 |
|     | \-- | field 'page\_token' is illegal, can't be parsed | page\_token 有误 |
|     | \-- | option symbol format error | 期权identifier格式有误 |
|     | \-- | sec\_type xxx error, current contract interface supported sec\_type include:\[STK, OPT, FUT\] | sec type 类型错误 |
|     | \-- | symbols cannot be empty and cannot exceed xxx | 标的为空或数量超过限制 |
| 1100 | global account response error |     | 环球账号交易错误，比如：  <br>1.TRADE DUPLICATE ORDER ID：交易订单号重复  <br>2.TRADE ORDER NOT ALLOWED：当前不允许下单 |
| 1200 | prime account response error |     | 综合账号交易错误，比如：  <br>1.**BAD\_REQUEST:Orders cannot be place at this moment**:当前时间无法进行下单操作  <br>2.**BAD\_REQUEST:You cannot place market or stop order during pre-market and after-hours trading**：美股盘前盘后阶段无法下市价单和止损单  <br>3.**The order quantity you entered exceeds your currently available position**：下单数量超过了可交易数量  <br>4.**bad\_request:We don’t support trading of this stock now**：不支持该只标的交易 |
| 1300 | paper account response error |     | 模拟账号交易错误，错误描述和综合账号返回基本一致 |
| 2100 | stock response error |     | 股票行情相关错误 |
| 2200 | option response error |     | 期权行情相关错误 |
| 2300 | futures response error |     | 期货行情相关错误 |
| 2400 |     | user token error | hk 牌照token错误 |
|     |     | user token can not be empty | token 为空 |
|     |     | user token expired invalid | token 过期 |
|     |     | user token invalid | token 失效 |
| 3xxx | subscribe error |     | 订阅数据相关错误，可能的原因：  <br>1.订阅时的tigerId异常  <br>2.订阅时发生了服务端异常  <br>3.不支持的行情提供商参数  <br>4.不支持的订阅类型  <br>5.订阅数超过了限制要求 |
| 4000 | permission denied |     | 访问权限不足，访问被拒绝，可能的原因：  <br>1.请求K线行情的时间段超过了被允许的范围  <br>2.请求分时数据的时间段超过了被允许的范围  <br>3.请求行情的设备不在允许范围内（多台设备同时抢占时只支持一个设备使用行情）  <br>4.行情权限不符合要求 |
| 4001 | kick out by a new connection |     | 长连接通道已被新建立的连接踢出 |

  

常见错误排查指南

[](./error-code.md#%E5%B8%B8%E8%A7%81%E9%94%99%E8%AF%AF%E6%8E%92%E6%9F%A5%E6%8C%87%E5%8D%97)

-----------------------------------------------------------------------------------------------------------------------------------

### 

连接与认证

[](./error-code.md#%E8%BF%9E%E6%8E%A5%E4%B8%8E%E8%AE%A4%E8%AF%81)

| 错误现象 | 可能原因 | 排查步骤 |
| --- | --- | --- |
| `failed to verify signature` | 私钥与开发者后台的公钥不匹配 | 1\. 确认使用的是 PEM 格式私钥文件 2. 检查私钥是否包含完整的 `-----BEGIN RSA PRIVATE KEY-----` 头尾 3. 重新生成密钥对并上传公钥 |
| `failed to verify signature` | 私钥格式与 SDK 不匹配 | **Python SDK 需使用 PKCS#1 格式**（头部为 `BEGIN RSA PRIVATE KEY`），**Java SDK 需使用 PKCS#8 格式**（头部为 `BEGIN PRIVATE KEY`）。在开发者页面生成密钥时，请下载对应格式的私钥 |
| `failed to get developer information` | tiger\_id 不存在或环境不匹配 | 1\. 确认 tiger\_id 正确 2. 确认未将正式环境的 tiger\_id 用于 sandbox 环境（反之亦然） 3. 确认未将 US 区域的 tiger\_id 用于非 US 区域 |
| `access forbidden` (code=4) | IP 白名单/签名失败/订阅超限 | 1\. 检查请求 IP 是否在白名单内 2. 检查长连接签名 3. 检查订阅标的数量是否超过限额 4. 机构用户检查是否传入了 `secret_key` |
| `get device information error` | 设备 ID 异常 | 检查 SDK 版本是否过旧，升级到最新版本 |
| `kick out by a new connection` (code=4001) | 同一账号在多台设备建立长连接 | 同一时间只允许一个长连接，新连接会踢出旧连接。如需切换设备，使用 `grab_quote_permission()` 抢占权限 |
| `network read time out` (code=2) | 网络超时 | 1\. 检查本地网络连通性 2. 考虑将程序部署在离 API 服务器较近的机房（如香港、新加坡） 3. 调大超时时间：Python 使用 `client_config.timeout`，Java 使用 `clientConfig.failRetryCounts` |

### 

Token 认证（TBHK 牌照）

[](./error-code.md#token-%E8%AE%A4%E8%AF%81tbhk-%E7%89%8C%E7%85%A7)

| 错误现象 | 可能原因 | 排查步骤 |
| --- | --- | --- |
| `user token expired invalid` (code=2400) | Token 已过期（有效期 30 天） | 前往[开发者信息页面](https://developer.itigerup.com/profile)<br>重新生成 Token 并下载 `tiger_openapi_token.properties` 文件 |
| `user token can not be empty` (code=2400) | 未配置 Token 文件 | 将 `tiger_openapi_token.properties` 文件放入配置目录（Python: `props_path` 指定的路径；Java: `configFilePath` 指定的路径） |
| `user token invalid` (code=2400) | Token 已失效 | 重新生成 Token。建议开启自动刷新：Python 设置 `client_config.token_refresh_duration = 86400`（单位秒），Java 设置 `clientConfig.isAutoRefreshToken = true` |

### 

频率限制

[](./error-code.md#%E9%A2%91%E7%8E%87%E9%99%90%E5%88%B6)

| 错误现象 | 可能原因 | 排查步骤 |
| --- | --- | --- |
| `rate limit error` (code=5, HTTP 429) | 请求频率超过限制 | 1\. 错误信息中会返回限制次数说明 2. 使用退避策略：首次等待 1 秒，每次重试翻倍（1s → 2s → 4s），最多重试 3 次 3. 合并批量请求（如 `get_stock_briefs` 一次传入多个 symbol）以减少调用次数 4. 详见[行情权限与限制](./permission.md) |

> 💡
> 
> **频率限制恢复建议**
> 
> 收到 HTTP 429 后，请不要立即重试。推荐使用指数退避策略：
> 
> Python
> 
>     import time
>     for attempt in range(3):
>         try:
>             result = quote_client.get_stock_briefs(['AAPL'])
>             break
>         except Exception as e:
>             if 'rate limit' in str(e).lower():
>                 wait = 2 ** attempt  # 1s, 2s, 4s
>                 time.sleep(wait)
>             else:
>                 raise

### 

行情权限

[](./error-code.md#%E8%A1%8C%E6%83%85%E6%9D%83%E9%99%90)

| 错误现象 | 可能原因 | 排查步骤 |
| --- | --- | --- |
| `permission denied` (code=4000) | 当前设备无行情权限 | 调用 `grab_quote_permission()` 抢占行情权限至当前设备。注意 Python SDK 2.0.9+ 初始化 QuoteClient 时默认会自动抢占 |
| `You don't have permission to subscribe quotes` | 未购买行情权限或可订阅数量为 0 | 前往[开发者信息页面](https://developer.itigerup.com/profile)<br>或手机端 Tiger Trade APP 购买对应市场的行情权限 |
| `According to your user level, you can only subscribe to xxx symbols` | 订阅标的数量超过等级限制 | 检查当前等级对应的订阅额度上限，可调用 `get_kline_quota()` 查看已用和剩余额度。提升等级需满足总资产或成交额要求，详见[历史行情限制](./permission.md#history_quote_permission) |
| 请求 K 线返回空数据 | 历史数据拉取额度耗尽 | 调用 `get_kline_quota()` 检查剩余额度。同一 Symbol 30 天内的重复请求不消耗额度 |

### 

参数错误

[](./error-code.md#%E5%8F%82%E6%95%B0%E9%94%99%E8%AF%AF)

| 错误现象 | 可能原因 | 排查步骤 |
| --- | --- | --- |
| `common param error` (code=1000) | 公共参数解析失败 | 1\. 检查请求 URL 和 method 参数是否正确 2. 确认请求体为标准 JSON 格式 3. 检查 timestamp、sign 等公共字段格式 |
| `invalid symbols` (code=1000) | 标的代码格式有误 | 确认 symbol 格式：美股如 `AAPL`，港股如 `00700`（5位数字），期权需使用 identifier 格式 |
| `biz param error` (code=1010) | 业务参数校验失败 | 1\. 检查日期格式是否为 `yyyy-MM-dd HH:mm:ss` 2. 检查 `sec_type` 是否为支持的值（STK/OPT/FUT） 3. 检查 `symbols` 是否为空或超过数量限制 |
| `option symbol format error` (code=1010) | 期权 identifier 格式错误 | 确认期权 identifier 格式正确，可先通过 `get_option_chain` 获取正确的 identifier |
| `page_token is used in the wrong way` (code=1010) | 分页参数使用不当 | 使用 `page_token` 翻页时，除 `page_token` 外的其他参数必须与首次请求保持一致 |

### 

交易错误

[](./error-code.md#%E4%BA%A4%E6%98%93%E9%94%99%E8%AF%AF)

| 错误现象 | 可能原因 | 排查步骤 |
| --- | --- | --- |
| `Orders cannot be placed at this moment` (code=1200) | 非交易时段下单 | 确认当前是否在该市场的交易时段内，参考[订单与交易规则](./trade-rules.md) |
| `market or stop order during pre-market` (code=1200) | 盘前盘后使用了不支持的订单类型 | 美股盘前盘后仅支持限价单（LMT），不支持市价单（MKT）和止损单（STP） |
| `exceeds your currently available position` (code=1200) | 下单数量超过可交易数量 | 检查持仓数量和可卖数量，注意 T+1 结算的品种需等待结算完成 |
| `TRADE DUPLICATE ORDER ID` (code=1100) | 订单号重复 | 环球账号交易时出现，检查是否重复提交了相同的订单 |
| `We don't support trading of this stock now` (code=1200) | 标的不支持交易 | 该标的可能已停牌或不在可交易范围内，确认标的交易状态 |

### 

订阅推送错误

[](./error-code.md#%E8%AE%A2%E9%98%85%E6%8E%A8%E9%80%81%E9%94%99%E8%AF%AF)

| 错误现象 | 可能原因 | 排查步骤 |
| --- | --- | --- |
| `subscribe error` (code=3xxx) | 订阅异常 | 1\. 检查 tiger\_id 是否正确 2. 检查订阅标的数量是否超过限额 3. 确认使用了正确的行情提供商参数和订阅类型 |
| 推送回调未触发 | 未正确设置回调函数 | 确认已注册回调函数并调用了对应的 subscribe 方法。确认长连接处于活跃状态（未被踢出） |

  

安全最佳实践

[](./error-code.md#%E5%AE%89%E5%85%A8%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5)

---------------------------------------------------------------------------------------------------------------

### 

私钥格式与管理

[](./error-code.md#%E7%A7%81%E9%92%A5%E6%A0%BC%E5%BC%8F%E4%B8%8E%E7%AE%A1%E7%90%86)

**私钥格式对照**

不同 SDK 需要使用不同格式的 RSA 私钥，格式不匹配会导致签名验证失败：

| SDK | 私钥格式 | 文件头标识 | 配置文件字段 |
| --- | --- | --- | --- |
| Python | PKCS#1 | `-----BEGIN RSA PRIVATE KEY-----` | `private_key_pk1` |
| Java | PKCS#8 | `-----BEGIN PRIVATE KEY-----` | `private_key_pk8` |
| C++ | PKCS#8 | `-----BEGIN PRIVATE KEY-----` | `private_key_pk8` |

> ⚠️
> 
> 如果遇到 `failed to verify signature` 错误，请首先确认私钥格式与所用 SDK 匹配。在开发者页面生成密钥时，两种格式的私钥均会提供，请下载对应格式。

**私钥存储建议**

*   将私钥文件存放在安全目录中，设置文件权限为仅当前用户可读：
    
    Bash
    
        chmod 600 /path/to/private_key.pem
    
*   不要将私钥文件或配置文件提交到代码仓库。建议在 `.gitignore` 中添加以下规则：
    
        *.pem
        tiger_openapi_config.properties
        tiger_openapi_token.properties
    
*   生产环境中，建议通过环境变量传入私钥内容，避免私钥文件直接存放在服务器磁盘上：
    
    Python
    
        import os
        client_config = TigerOpenClientConfig()
        client_config.private_key = os.environ.get('TIGER_PRIVATE_KEY')
        client_config.tiger_id = os.environ.get('TIGER_ID')
        client_config.account = os.environ.get('TIGER_ACCOUNT')
    
*   对于更高安全要求的场景，建议使用密钥管理服务（如 AWS Secrets Manager、HashiCorp Vault、阿里云 KMS）存储和管理私钥
*   定期轮换密钥对：在[开发者后台](https://developer.itigerup.com/profile)
    生成新的密钥对。建议至少每 6 个月轮换一次

### 

Token 管理（TBHK 牌照）

[](./error-code.md#token-%E7%AE%A1%E7%90%86tbhk-%E7%89%8C%E7%85%A7)

TBHK 牌照用户需要额外管理 Token，Token 有效期为 30 天，过期后需重新生成：

*   **推荐开启自动刷新**，避免 Token 过期导致服务中断：
    
    PythonJava
    
        # Python: 每天自动刷新一次 Token
        client_config.token_refresh_duration = 24 * 60 * 60
    
        // Java: 开启自动刷新，默认每5天刷新一次
        clientConfig.isAutoRefreshToken = true;
        // 可选：自定义刷新间隔和时间
        // clientConfig.refreshTokenIntervalDays = 3;
        // clientConfig.refreshTokenTime = "03:00:00";
    
*   Token 文件 `tiger_openapi_token.properties` 需与主配置文件放在同一目录下
*   自动刷新成功后 SDK 会同时更新本地的 Token 文件

### 

生产环境部署建议

[](./error-code.md#%E7%94%9F%E4%BA%A7%E7%8E%AF%E5%A2%83%E9%83%A8%E7%BD%B2%E5%BB%BA%E8%AE%AE)

*   **IP 白名单**：在[开发者后台](https://developer.itigerup.com/profile)
    配置 IP 白名单，仅允许生产服务器的 IP 访问 API。多个 IP 之间用 `;` 分隔
*   **日志安全**：避免在日志中打印完整的请求/响应内容，尤其是包含账户信息、持仓数据的内容。建议在生产环境关闭 debug 级别日志：
    
    PythonJava
    
        # Python: 设置日志级别为 WARNING 或以上
        import logging
        client_config.log_level = logging.WARNING
    
        // Java: 关闭 debug 日志
        ApiLogger.setDebugEnabled(false);
    
*   **secret\_key 保管**：机构用户的 `secret_key` 用于 API 身份认证，切勿在客户端代码中硬编码或提交到代码仓库。建议通过环境变量或密钥管理服务注入
*   **网络安全**：SDK 默认使用 SSL/TLS 加密传输（Java: `clientConfig.isSslSocket = true`），请勿关闭此选项
*   **异常监控**：建议对以下关键事件设置告警监控：
    *   签名验证连续失败（可能密钥泄露后被替换）
    *   Token 过期未及时刷新
    *   频率限制（code=5）频繁触发
    *   长连接被踢出（code=4001）

  
