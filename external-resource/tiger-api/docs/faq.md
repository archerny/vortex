# 常见问题 FAQ

私钥格式问题

[](./faq.md#%E7%A7%81%E9%92%A5%E6%A0%BC%E5%BC%8F%E9%97%AE%E9%A2%98)

--------------------------------------------------------------------------------------------------------

### 

ValueError: Unable to read this file, version 136 != 0

[](./faq.md#valueerror-unable-to-read-this-file-version-136--0)

**错误信息**

    rsa/key.py:492 _load_pkcs1_der
    ValueError: Unable to read this file, version 136 != 0

**原因** 私钥格式错误

**解决方法**

1.  在[开发者页面](https://developer.itigerup.com/profile)
    重新复制 **PKCS#1** 格式的私钥

**如何区分私钥格式**

| 格式  | 文件头标识 |
| --- | --- |
| PKCS#1（Python 使用） | `-----BEGIN RSA PRIVATE KEY-----` |
| PKCS#8（Java/C++ 使用） | `-----BEGIN PRIVATE KEY-----` |

* * *

### 

Could not deserialize key data

[](./faq.md#could-not-deserialize-key-data)

**错误信息**

    ValueError: ('Could not deserialize key data. The data may be in an incorrect format,
    it may be encrypted with an unsupported algorithm, or it may be an unsupported key type
    (e.g. EC curves with explicit parameters).', [<OpenSSLError(...)>])

**原因**

`cryptography` 库版本兼容性问题。较新版本（如 45.x）可能与当前 SDK 不兼容。

**解决方法**

降级 `cryptography` 库版本：

Bash

    pip install cryptography==42.0.8

* * *

### 

request sign failed. int() argument must be a string

[](./faq.md#request-sign-failed-int-argument-must-be-a-string)

**错误信息**

    TypeError: int() argument must be a string, a bytes-like object or a number, not 'Sequence'

**原因**

密钥格式不正确。可能是从不兼容的工具生成的密钥，或者复制密钥时内容不完整。

**解决方法**

1.  在[开发者页面](https://developer.itigerup.com/profile)
    重新生成密钥

* * *

签名校验失败

[](./faq.md#%E7%AD%BE%E5%90%8D%E6%A0%A1%E9%AA%8C%E5%A4%B1%E8%B4%A5)

--------------------------------------------------------------------------------------------------------

### 

failed to verify signature / sign check error

[](./faq.md#failed-to-verify-signature--sign-check-error)

**错误信息**

    ApiException: code=1000 msg=common param error(failed to verify signature,
    please make sure you use the correct rsa private key, if you use python sdk,
    the private key is in pkcs#1 format)

或

    ApiException: code=1000 msg=common param error(sign check error)

**可能原因**

1.  私钥格式不是 PKCS#1（最常见原因）
2.  私钥与开发者后台的公钥不匹配（如在机构中心重置了密钥但本地未更新）
3.  密钥从不兼容的工具生成
4.  系统环境问题（个别环境下签名行为异常）

**排查步骤**

1.  确认使用的是 PKCS#1 格式私钥（以 `BEGIN RSA PRIVATE KEY` 开头）
2.  在[开发者页面](https://developer.itigerup.com/profile)
    重新生成密钥对，下载新的私钥并上传新的公钥
3.  如果更换机器后恢复正常，说明是系统环境问题
4.  尝试使用在线工具重新生成密钥对

* * *

### 

public key error

[](./faq.md#public-key-error)

**错误信息**

JSON

    {"code":1000,"message":"common param error(public key error)","timestamp":1574386825800}

**原因**

`tiger_id` 未正确传到服务端。

**排查步骤**

1.  确认 `client_config.tiger_id` 已正确设置
2.  如未使用 SDK，检查请求是否发送到了正确的地址（不要发到 sandbox 地址）
3.  如使用配置文件，确认 `tiger_openapi_config.properties` 中 `tiger_id` 字段值正确

* * *

SSL / 证书问题

[](./faq.md#ssl--%E8%AF%81%E4%B9%A6%E9%97%AE%E9%A2%98)

-----------------------------------------------------------------------------------------------

### 

SSL: CERTIFICATE\_VERIFY\_FAILED

[](./faq.md#ssl-certificate_verify_failed)

**错误信息**

    [SSL: CERTIFICATE_VERIFY_FAILED] certificate verify failed:
    self signed certificate in certificate chain (_ssl.c:1056)

或

    response sign verify failed. Verification Failed

**可能原因**

1.  将自己生成的公钥错误地设置为了 `tiger_public_key`（SDK 内部的服务端公钥），导致响应验签失败
2.  系统 SSL 证书过期或缺失
3.  Python 安装未包含根证书

**解决方法**

**方法一**：确认未设置 `tiger_public_key`，该字段由 SDK 内部管理，用户不应修改

**方法二**（Mac）：如果通过安装包方式安装了 Python，进入 `Applications/Python x.x/` 目录，双击运行 `Install Certificates.command`, (最常见解决方法)

**方法三**：安装证书库

Bash

    pip install certifi

如仍未解决：

Bash

    pip install pip-system-certs

**方法四**（临时解决，不推荐用于生产）：

Python

    import ssl
    ssl._create_default_https_context = ssl._create_unverified_context

> ⚠️
> 
> 方法四会跳过 SSL 证书验证，存在安全风险，仅建议在调试阶段临时使用。

* * *

### 

unable to get local issuer certificate

[](./faq.md#unable-to-get-local-issuer-certificate)

**错误信息**

    unable to get local issuer certificate

**解决方法**

Bash

    pip install certifi

或

Bash

    pip install pip-system-certs

Mac 用户也可以尝试运行 `Applications/Python x.x/Install Certificates.command`。

* * *

### 

module 'http.client' has no attribute 'HTTPConnection'

[](./faq.md#module-httpclient-has-no-attribute-httpconnection)

**错误信息**

    attributeError: module 'http.client' has no attribute 'HTTPConnection'

**原因**

系统未安装 OpenSSL，或 Python 编译时未链接 OpenSSL。

**解决方法**

1.  确认系统已安装 OpenSSL：
    
    Bash
    
        python -c "import ssl; print(ssl.OPENSSL_VERSION)"
    
2.  如果报错，需要重新安装 Python 并确保链接了 OpenSSL

* * *

连接与推送问题

[](./faq.md#%E8%BF%9E%E6%8E%A5%E4%B8%8E%E6%8E%A8%E9%80%81%E9%97%AE%E9%A2%98)

------------------------------------------------------------------------------------------------------------------

### 

ConnectFailedException（长连接失败）

[](./faq.md#connectfailedexception%E9%95%BF%E8%BF%9E%E6%8E%A5%E5%A4%B1%E8%B4%A5)

**错误信息**

    stomp.exception.ConnectFailedException

**可能原因**

1.  服务端临时故障
2.  网络不通或被防火墙拦截
3.  SSL 配置问题

**排查步骤**

1.  检查网络连通性
2.  确认 `PushClient` 的 `use_ssl` 参数未被设置为 `False`（默认为 `True`）
3.  稍后重试，如果持续失败请联系技术支持

* * *

### 

Unknown response frame type

[](./faq.md#unknown-response-frame-type)

**错误信息**

    Unknown response frame type: '' (frame length was 3)

**原因**

`PushClient` 的 `use_ssl` 被设置为 `False`，导致通信协议不匹配。

**解决方法**

确保使用 SSL 连接（默认行为），不要设置 `use_ssl=False`。

* * *

### 

OSError: \[WinError 10038\] 在一个非套接字上尝试了一个操作

[](./faq.md#oserror-winerror-10038-%E5%9C%A8%E4%B8%80%E4%B8%AA%E9%9D%9E%E5%A5%97%E6%8E%A5%E5%AD%97%E4%B8%8A%E5%B0%9D%E8%AF%95%E4%BA%86%E4%B8%80%E4%B8%AA%E6%93%8D%E4%BD%9C)

**错误信息**

    OSError: [WinError 10038] 在一个非套接字上尝试了一个操作。

**原因**

在长连接断线重连尚未完成时就调用了发送操作（如 `query_subscribed_quote`）。

**解决方法**

在调用推送相关方法前，确认连接已建立成功。可以通过 `on_connected` 回调来确认连接状态，或在调用前加入连接状态检查：

Python

    if push_client.is_connected():
        push_client.query_subscribed_quote()

* * *

### 

行情退订无效

[](./faq.md#%E8%A1%8C%E6%83%85%E9%80%80%E8%AE%A2%E6%97%A0%E6%95%88)

**错误信息**

    According to your user level, you can only unsubscribe after 1 minute of last subscription

**原因**

订阅后需要等待至少 1 分钟才能退订。

**解决方法**

在上一次订阅操作后等待 1 分钟再进行退订操作。

* * *

账户与权限问题

[](./faq.md#%E8%B4%A6%E6%88%B7%E4%B8%8E%E6%9D%83%E9%99%90%E9%97%AE%E9%A2%98)

------------------------------------------------------------------------------------------------------------------

### 

account is not authorized to the api user

[](./faq.md#account-is-not-authorized-to-the-api-user)

**原因**

`account` 填写有误，或 `client_config` 配置不正确。

**排查步骤**

1.  确认 `client_config.account` 已正确设置
2.  确认账号格式正确：综合账号为 5~10 位数字，环球账号以 U 开头，模拟账号为 17 位数字
3.  如果同时设置了 `standard_account` 和 `paper_account`，确认 `account` 字段也已设置

* * *

### 

unauthorized: Please login in（code=1200）

[](./faq.md#unauthorized-please-login-incode1200)

**错误信息**

    ApiException: code=1200 msg=standard account response error(unauthorized:Please login in)

**可能原因**

1.  `tiger_id` 与 `account` 不匹配
2.  机构用户：该用户未在后台 API 权限中注册

**解决方法**

1.  确认 `tiger_id` 和 `account` 来自同一个开发者账号
2.  机构用户：联系管理员在机构中心的 API 权限中添加该用户

* * *

Windows 常见问题

[](./faq.md#windows-%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)

----------------------------------------------------------------------------------------------------

### 

OSError: \[Errno 22\] Invalid argument（路径错误）

[](./faq.md#oserror-errno-22-invalid-argument%E8%B7%AF%E5%BE%84%E9%94%99%E8%AF%AF)

**错误信息**

    OSError: [Errno 22] Invalid argument: 'D:\\Program Files\\OpenSSL...'

或

    request sign failed. Short octet stream on tag decoding

**原因**

Windows 下文件路径中的反斜杠 `\` 被识别为转义字符。

**解决方法**

在路径字符串前加 `r` 前缀，使用原始字符串：

Python

    client_config.private_key = read_private_key(r'C:\Users\Foo\Desktop\rsa_private_key.pem')

或使用正斜杠：

Python

    client_config.private_key = read_private_key('C:/Users/Foo/Desktop/rsa_private_key.pem')

* * *

数据显示问题

[](./faq.md#%E6%95%B0%E6%8D%AE%E6%98%BE%E7%A4%BA%E9%97%AE%E9%A2%98)

--------------------------------------------------------------------------------------------------------

### 

输出显示 \[5 rows x 9 columns\]，看不到完整数据

[](./faq.md#%E8%BE%93%E5%87%BA%E6%98%BE%E7%A4%BA-5-rows-x-9-columns%E7%9C%8B%E4%B8%8D%E5%88%B0%E5%AE%8C%E6%95%B4%E6%95%B0%E6%8D%AE)

**现象**

调用接口返回 DataFrame 后，print 输出被截断，只显示类似：

    [5 rows x 9 columns]

**原因**

pandas 默认会截断列数、行数和宽度的显示。

**解决方法**

在代码开头添加 pandas 显示设置：

Python

    import pandas as pd
    pd.set_option('display.max_columns', 500)
    pd.set_option('display.max_rows', 5000)
    pd.set_option('display.width', 5000)

* * *

附：密钥格式转换

[](./faq.md#%E9%99%84%E5%AF%86%E9%92%A5%E6%A0%BC%E5%BC%8F%E8%BD%AC%E6%8D%A2)

-------------------------------------------------------------------------------------------------------------------

如需在 PKCS#1 和 PKCS#8 格式之间转换，可使用 OpenSSL 命令行工具：

**生成 PKCS#1 私钥**

Bash

    openssl genrsa -out private_pkcs1.pem 1024

**PKCS#1 转 PKCS#8**

Bash

    openssl pkcs8 -topk8 -inform PEM -in private_pkcs1.pem -outform pem -nocrypt -out private_pkcs8.pem

**PKCS#8 转 PKCS#1**

Bash

    openssl rsa -in private_pkcs8.pem -out private_pkcs1.pem

**从私钥导出公钥**

Bash

    openssl rsa -in private_pkcs1.pem -pubout -out public_key.pem
