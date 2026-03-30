# 准备工作

环境要求

[](./prepare.md#%E7%8E%AF%E5%A2%83%E8%A6%81%E6%B1%82)

----------------------------------------------------------------------------------------

*   操作系统要求：
    *   Windows
    *   MacOS
    *   Linux
*   编程语言版本要求：Python 3.8及以上

安装 Python

[](./prepare.md#%E5%AE%89%E8%A3%85-python)

----------------------------------------------------------------------------------

建议安装 Python 3.8 及以上版本。可在终端输入 `python3 -V` 检查版本。

官方安装包uv（推荐）Conda

访问 [Python 官方下载页面](https://www.python.org/downloads/)
，选择操作系统对应的安装包，按提示完成安装。

安装 Tiger Open API Python SDK

[](./prepare.md#%E5%AE%89%E8%A3%85-tiger-open-api-python-sdk)

------------------------------------------------------------------------------------------------------------------------

uv（推荐）pip + venvConda从源码安装

[uv](https://docs.astral.sh/uv/)
 是目前最快的 Python 包管理器，自动创建虚拟环境并管理依赖：

Shell

    # 创建项目目录
    mkdir my-tiger-project && cd my-tiger-project
    
    # 初始化项目（自动创建虚拟环境和 pyproject.toml）
    uv init
    uv add tigeropen
    
    # 运行脚本
    uv run python main.py

升级 SDK：

Shell

    uv add tigeropen --upgrade

开发工具

[](./prepare.md#%E5%BC%80%E5%8F%91%E5%B7%A5%E5%85%B7)

----------------------------------------------------------------------------------------

AI 编程工具（推荐）传统 IDE

使用 AI 编程工具可以自动阅读 Tiger OpenAPI 文档，通过自然语言描述需求即可生成可运行的 SDK 代码，极大降低上手门槛。

**使用方式**

1.  用上述任一方式创建好项目环境并安装 `tigeropen`
2.  将配置文件 `tiger_openapi_config.properties` 放入项目目录
3.  将 [Tiger OpenAPI 文档](https://quant.itigerup.com/openapi/py/overview/introduction)
     添加为 AI 工具的上下文（参考下方各工具说明）
4.  用自然语言描述需求，AI 会阅读文档并生成对应的 SDK 代码

例如，你可以直接说："获取 AAPL 最近 30 天的日 K 线数据" 或 "下一个 NVDA 的限价买单"，AI 会参考文档自动生成完整代码。

**Cursor / Trae / Windsurf**

在项目中添加文档作为上下文：

*   **Cursor**：在 Settings > Docs 中添加 Tiger OpenAPI 文档地址，对话时 AI 会自动检索
*   **Trae**：将文档链接或关键页面添加到 Knowledge Base，AI 会基于文档生成代码
*   **Windsurf**：通过 `@docs` 引用在线文档，或将文档内容放入项目目录供 AI 读取

**VS Code + GitHub Copilot**

在 VS Code 中安装 [GitHub Copilot](https://marketplace.visualstudio.com/items?itemName=GitHub.copilot)
 扩展，通过 Chat 面板与 AI 对话生成代码。可使用 `#file` 引用项目中的文档或代码文件作为上下文。

**Claude Code / Kiro**

终端 AI 工具，启动后可直接在对话中引用文档链接，AI 会抓取并理解文档内容：

Shell

    # 安装 Claude Code
    npm install -g @anthropic-ai/claude-code
    
    # 在项目目录启动
    cd my-tiger-project
    claude

启动后，将文档链接发给 AI 即可，例如："参考 [https://quant.itigerup.com/openapi/py/overview/introduction](https://quant.itigerup.com/openapi/py/overview/introduction)
 帮我获取持仓信息"。

注册开发者信息

[](./prepare.md#%E6%B3%A8%E5%86%8C%E5%BC%80%E5%8F%91%E8%80%85%E4%BF%A1%E6%81%AF)

----------------------------------------------------------------------------------------------------------------------

使用API之前请首先开通权限， 个人用户访问API官网[登记开发者身份](https://developer.itigerup.com/profile)
 **推荐使用Chrome浏览器打开**

**注意：开通Open API需要在老虎开户并入金，同时会要求开发人员和用户签署API授权协议**

随后需要在此页面中完成开发者信息的登记，请填入并提交您的信息。

**注册成功后您可在此页面中获取以下信息：**

*   **tiger\_id：** 开放平台为每一位开发者分配的唯一ID，用来标识一个开发者，所有API接口的调用都会用到tiger\_id。
*   **account：** 用户的资金账号，在请求交易相关接口时需要用到资金账号。具体分为环球账号、综合账号与模拟账号，
    *   **环球资金账号（Global）**：以大写字母U开头，如：U12300123，
    *   **综合资金账号（Standard）**：为一串较短的数字（5到10位），如：51230321，
    *   **模拟资金账号（Paper）**：17位数字，如：20191106192858300，

注册开发者信息成功后只会返回已成功入金的资金账号和模拟账号。如果用户的环球账号和综合账号都已成功入金，则都会返回。

**开发者注册页面：** 通过手机号和验证码注册即可

![](https://files.readme.io/12fcf94d8c3021e8adaca823038d372adc474036c0ea958e910414adf214e3aa-img_v3_02pn_e119a2ff-aa72-42a2-8c99-89ace9f8005g.png)

**开发者信息页面：** 其中Tiger ID，实盘账户，模拟盘账号，牌照等信息需要在OpenAPI中用到。

![](https://files.readme.io/8086087959e338de8944573c7b40ccc907c294cd13434e427a4d11918599c394-img_v3_02pn_1be5c8b5-5b6d-4064-9e7d-8d9f86f3064g.png)

**注意：需要把下图中的私钥部分保存到本地并妥善保管以防泄露，如发现泄露请及时更新。 私钥不会在老虎服务端保存，用户在刷新页面前需要保存好。页面刷新后该私钥会自动消失，如私钥未保存好，可以通过`重新生成`按钮进行替换。**

**用户可根据不同SDK的调用需求保存相应类型的私钥，Java 与 Python SDK 使用的私钥格式不同，Java 使用的私钥为 PKCS#8 格式，Python 使用PKCS#1格式。若使用SDK遇到问题时，注意先检查私钥的格式是否正确。**

![](https://files.readme.io/6b8bc477cdb7ed29bf21d161cc8429879185b97716aff9130869de89814fa0b6-img_v3_00pn_c01aebe0-fc7b-4890-847b-c89f27c00e7g.jpg)

生成的配置文件 tiger\_openapi\_config.properties文件内容格式如下，account为默认账号，可以在实盘资金账号和模拟盘账号之间替换。如果是机构账户，还需要配置 `secret_key` (需在机构中心获取).

    private_key_pk1=MIICXgIBAAKBgQCodM4fM5cz+tj8SUuxxSbSMFxNHM23hftjAuXr9THweyFtTmA/mxwo9HYEhYjxCgYAJt78xbzw7aiKu+pI45O8il8ns6UL3mT59QlCKu0+FZmF2g54teyqoiol71xcM096D3ss0oJzKGt+btvlBzdcrXM6+pZqbK14ASWGE4uBEQIDAQABAoGAX5iBjFVTngzhbDIQAZ73C9qj1qLc3yPqZ+KyJbgskeLeMlCC8DWOwnMQMjqxXtAoXZexfVKdiT/lIG3JY3m6URzfYwJt5VUD9LeIcyPxuj7Eg2vxlZwJ6slT7DQ6LJCbUe1PUDlKKXYe/wEUNJ3dEJ/eFEhR8vNyFlW8DJj+az0CQQDhQwYUZIcq+9WDcSen5Sm5oS765aMAyWcOdkpokau+fZ/H9NwT0U+jaEHyswZMJBTDJ42iS8HV0MuGdtLJ7JzHAkEAv3Fq1AHHJ1Tyxtf/VWHBRfx304NFlDX87Ji7uyv1t+05ZYVT0U8Q5CUPdBA3QexMBKd1i6BXUH/xOY5aX+UrZwJBAKR7ej79wK0kQh2+TESOUs9W4KUXdNSoO3YV7fM5Gvz3Yepx4xkdZ1dcU3tSObDzT63IfeUaCCmGUnaW4QfMnBUCQQCMOsK6mrbLe7D9sBgK7bkJz73iA+UChG1IzTOuDaxGVpDVaU0VI2pHA1KHfJ1NP44LVrKGXSO4Bo+mzjwqxA3/AkEApA8roUKXtKTqC2rb8hs5AscMpHFUl7ZbVeKX14gAg9QPWxkh8BcB1dljAsrp7irEtq4VezD2Cf40b52Fcx+pvA==
    private_key_pk8=MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKh0zh8zlzP62PxJS7HFJtIwXE0czbeF+2MC5ev1MfB7IW1OYD+bHCj0dgSFiPEKBgAm3vzFvPDtqIq76kjjk7yKXyezpQveZPn1CUIq7T4VmYXaDni17KqiKiXvXFwzT3oPeyzSgnMoa35u2+UHN1ytczr6lmpsrXgBJYYTi4ERAgMBAAECgYBfmIGMVVOeDOFsMhABnvcL2qPWotzfI+pn4rIluCyR4t4yUILwNY7CcxAyOrFe0Chdl7F9Up2JP+UgbcljebpRHN9jAm3lVQP0t4hzI/G6PsSDa/GVnAnqyVPsNDoskJtR7U9QOUopdh7/ARQ0nd0Qn94USFHy83IWVbwMmP5rPQJBAOFDBhRkhyr71YNxJ6flKbmhLvrlowDJZw52SmiRq759n8f03BPRT6NoQfKzBkwkFMMnjaJLwdXQy4Z20snsnMcCQQC/cWrUAccnVPLG1/9VYcFF/HfTg0WUNfwzsmLu7K/W37TllhVPRTxDkJQ90EDdB7EwEp3WLoFdQf/E5jlpf5StnAkEApHt6Pv3ArSRCHb5MRI5Sz1bgpRd01Kg7dhXt8zka/Pdh6nHjGR1nV1xTe1I5sPNPrch95RoIKYZSdpbhB8ycFQJBAIw6wrqatst7sP2wGArtuQnPveID5QKEbUjNM64NrEZWkNVpTRUjakcDUod8nU0/jgtWsoZdI7gGj6bOPCrEDf8CQQCkDyuhQpe0pOoLatvyGzkCxwykcVSXtltV4pfXiACD1A9bGSHwFwHV2WMCyunuKsS2rhV7MPYJ/jRvnYVzH6m8
    tiger_id=20150001
    account=12345678
    license=TBHK
    env=PROD

#### 

Token（可选）

[](./prepare.md#token%E5%8F%AF%E9%80%89)

![](https://files.readme.io/01efe5a7ce6f9d1a3c4639f6d4de9d817fef990d28dafdb5e08e0591dfa5fac7-img_v3_00pn_7af72a3a-ef2e-47de-93c6-4f0bd27bbcbg.jpg)

**TBHK牌照(其他牌照用户可以忽略)，需要生成token，token失效后需重新生成，并下载tiger\_openapi\_token.properties文件到本地。把tiger\_openapi\_token.properties文件拷贝到ClientConfig props\_path 配置的目录下。**

![](https://docs.itigerup.com/.vuepress/public/zhToken.jpg)

token的有效期为30天，如果失效后需要到开发者信息页面重新生成并导出新的token文件。在失效前，可以通过刷新token的API接口续期。sdk默认不自动刷新。 刷新成功后会同时更新本地 tiger\_openapi\_token.properties 文件。 如需自动刷新token，请配置client\_config.token\_refresh\_duration 为一个大于0的值，时间单位为秒。

**额外配置，非必须：**

| 信息  | 是否必填 | 说明  |
| --- | --- | --- |
| IP白名单 | 否   | 只有在白名单内的IP才可以访问API接口，多个IP间以 “;” 分隔，非必填 |
| 回调URL | 否   | 用户应用程序的回调地址，可以用于接收订单、持仓、资产的变更消息。非必填，用户也可以直接通过SDK提供的订阅接口接收回调消息 |

  

机构中心

[](./prepare.md#%E6%9C%BA%E6%9E%84%E4%B8%AD%E5%BF%83)

----------------------------------------------------------------------------------------

机构用户请访问 [机构账户中心](./contact.md#institution_link)

账户开通并注入资金后，可登录机构账户中心的老虎账户，并前往「交易设置 > 开通OpenAPI」按照步骤完成开通流程。

![](https://files.readme.io/07629b9a5c4027be1a8ee04fbec80e3b68176ee775615d6fc574d0ff0a019385-image.png)

**在基础配置页面可以获取公私钥匙**

*   在开通或者重新生成公私钥时候，您只需点击「获取公私钥」，即可自动生成公私钥信息。
*   如果您不需要我们生成的公私钥，可以选择自定义，把您的公钥复制粘贴进表格完成保存确定即可。

**注意**：需要把私钥部分保存到本地并妥善保管以防泄露，如发现泄露请及时更新。 私钥不会在老虎服务端保存，客户需自行保存或下载，。如客户不慎丢失或遗忘私钥，可重新获取。

![](https://files.readme.io/497520eaca654186c9174a98ec4cfddcf0febbddffb3b896dc4095db93ec3a8e-image.png)

**私钥格式说明：**

*   Java SDK: 需使用 PKCS#8 格式私钥
*   Python SDK: 需使用 PKCS#1 格式私钥

**注意**：SDK调用异常时，请优先检查私钥格式匹配性

![](https://files.readme.io/87d11eb8d1075b81a8ccaa39d575cfba235066a501c7e7291a651ed5ff093c0e-image.png)

生成的配置文件 tiger\_openapi\_config.properties文件内容格式如下，account为默认账号，可以在实盘资金账号和模拟盘账号之间替换。如果是机构账户，还需要配置 `secret_key` (需在机构中心获取).

    private_key_pk1=MIICXgIBAAKBgQCodM4fM5cz+tj8SUuxxSbSMFxNHM23hftjAuXr9THweyFtTmA/mxwo9HYEhYjxCgYAJt78xbzw7aiKu+pI45O8il8ns6UL3mT59QlCKu0+FZmF2g54teyqoiol71xcM096D3ss0oJzKGt+btvlBzdcrXM6+pZqbK14ASWGE4uBEQIDAQABAoGAX5iBjFVTngzhbDIQAZ73C9qj1qLc3yPqZ+KyJbgskeLeMlCC8DWOwnMQMjqxXtAoXZexfVKdiT/lIG3JY3m6URzfYwJt5VUD9LeIcyPxuj7Eg2vxlZwJ6slT7DQ6LJCbUe1PUDlKKXYe/wEUNJ3dEJ/eFEhR8vNyFlW8DJj+az0CQQDhQwYUZIcq+9WDcSen5Sm5oS765aMAyWcOdkpokau+fZ/H9NwT0U+jaEHyswZMJBTDJ42iS8HV0MuGdtLJ7JzHAkEAv3Fq1AHHJ1Tyxtf/VWHBRfx304NFlDX87Ji7uyv1t+05ZYVT0U8Q5CUPdBA3QexMBKd1i6BXUH/xOY5aX+UrZwJBAKR7ej79wK0kQh2+TESOUs9W4KUXdNSoO3YV7fM5Gvz3Yepx4xkdZ1dcU3tSObDzT63IfeUaCCmGUnaW4QfMnBUCQQCMOsK6mrbLe7D9sBgK7bkJz73iA+UChG1IzTOuDaxGVpDVaU0VI2pHA1KHfJ1NP44LVrKGXSO4Bo+mzjwqxA3/AkEApA8roUKXtKTqC2rb8hs5AscMpHFUl7ZbVeKX14gAg9QPWxkh8BcB1dljAsrp7irEtq4VezD2Cf40b52Fcx+pvA==
    private_key_pk8=MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKh0zh8zlzP62PxJS7HFJtIwXE0czbeF+2MC5ev1MfB7IW1OYD+bHCj0dgSFiPEKBgAm3vzFvPDtqIq76kjjk7yKXyezpQveZPn1CUIq7T4VmYXaDni17KqiKiXvXFwzT3oPeyzSgnMoa35u2+UHN1ytczr6lmpsrXgBJYYTi4ERAgMBAAECgYBfmIGMVVOeDOFsMhABnvcL2qPWotzfI+pn4rIluCyR4t4yUILwNY7CcxAyOrFe0Chdl7F9Up2JP+UgbcljebpRHN9jAm3lVQP0t4hzI/G6PsSDa/GVnAnqyVPsNDoskJtR7U9QOUopdh7/ARQ0nd0Qn94USFHy83IWVbwMmP5rPQJBAOFDBhRkhyr71YNxJ6flKbmhLvrlowDJZw52SmiRq759n8f03BPRT6NoQfKzBkwkFMMnjaJLwdXQy4Z20snsnMcCQQC/cWrUAccnVPLG1/9VYcFF/HfTg0WUNfzsmLu7K/W37TllhVPRTxDkJQ90EDdB7EwEp3WLoFdQf/E5jlpf5StnAkEApHt6Pv3ArSRCHb5MRI5Sz1bgpRd01Kg7dhXt8zka/Pdh6nHjGR1nV1xTe1I5sPNPrch95RoIKYZSdpbhB8ycFQJBAIw6wrqatst7sP2wGArtuQnPveID5QKEbUjNM64NrEZWkNVpTRUjakcDUod8nU0/jgtWsoZdI7gGj6bOPCrEDf8CQQCkDyuhQpe0pOoLatvyGzkCxwykcVSXtltV4pfXiACD1A9bGSHwFwHV2WMCyunuKsS2rhV7MPYJ/jRvnYVzH6m8
    tiger_id=20150001
    account=12345678
    license=TBHK
    env=PROD
    secret_key=fcfca571-71db-35c1-9352-9a557cc8258d

**注册成功后您可在用户资料中获取以下信息：**

*   **用户名**：登录机构中心时的名称
*   **User ID**：用户 ID
*   **Tiger ID**：开发者唯一标识符（所有 API 调用的必需参数）
*   **Secret Key**：交易员密钥，机构用户需在 config.properties 配置文件中设置此密钥，用于API请求的身份安全认证
*   **Account ID**：用户的资金账户 ID，在请求交易相关接口时需要用到资金账号，点击页面「编辑」按钮，可查看到用户下面对应的账户 ID（Account ID）

![](https://files.readme.io/a12bab3392870d4becf6ffaf271f9cdc4662fa0e3be4fa4ba5624c30f05e444f-image.png) ![](https://files.readme.io/c646a6674428fdbda148b45c6bd0c7b206a8178b55eb291afd132a0c0aa4b10f-image.png)

**特殊说明**

每个 User ID 对应着一个 Tiger ID，每个 Tiger ID 可以建立一个长连接，如果需要多个长连接，可以通过建立多 个User 来实现，可以前往**用户管理** -**管理用户权限** 添加新的用户，然后再去API权限界面点击**新增用户资料**添加新的用户。

![](https://files.readme.io/e4ebf8a8624ca2faf3836f749194ff2396602ac7031b1612f52db84595759cd9-image.png)

每个User ID对应的API请求权限都受制于管理用户权限里面的权限设置，可以按照角色去限制用户可调用每个账户的查看、交易、资产等权限。

![](https://files.readme.io/51e5c7ae9adcabf602d2a02ff4293906312a88baadbcd7c74e1682e12262551b-image.png)  
![](https://files.readme.io/7bccec2c6c18ae5109365c6ce3b882d407b60cd4fa6c90faeef272427be96ce3-image.png)  

购买行情（可选）

[](./prepare.md#%E8%B4%AD%E4%B9%B0%E8%A1%8C%E6%83%85%E5%8F%AF%E9%80%89)

--------------------------------------------------------------------------------------------------------------

我们免费提供延迟行情接口，但实时行情需要另外购买。Open API的行情权限独立与APP与PC端，如果您已经购买了APP或PC行情，也需要另外购买Open API的行情权限以获得实时数据。具体购买方法如下：

**个人客户**

有两种购买方式：

1、登录[个人中心](https://developer.itigerup.com/profile)
购买行情

![](https://files.readme.io/393f4ee9e6a22a96e906199bab4b1a53320f76397b086574c03fd035d6e33858-img_v3_02pn_ff19980f-f6ff-4701-892e-d033bdc8fb8g.png)

2、在手机端APP **Tiger Trade APP - 我的 - 行情权限 - OpenAPI权限** 中进行购买

**机构客户**

在 **机构中心-行情权限** 中进行购买

![](https://files.readme.io/562223625a866ee0023f6379bb90d867d6ed6ec494e92498184c8f2168bc1f3f-img_v3_02pn_cda427f4-bdfd-41fd-973b-cbcba94c295g.png)

API 相关配置

[](./prepare.md#api-%E7%9B%B8%E5%85%B3%E9%85%8D%E7%BD%AE)

------------------------------------------------------------------------------------------------

在正式请求接口前，需要完成API接口调用的相关配置。具体配置信息（包括tigerId，account，license等）可以在开发者信息页面查看。

共有两种配置方式：

**方式一**

使用配置文件。 在开发者网站导出配置文件 `tiger_openapi_config.properties`， 放入合适的系统路径，如 `/Users/demo/props/` 然后将该路径填入TigerOpenClientConfig的 `props_path` 参数下（也可将配置文件放入程序的当前启动目录，sdk默认会取当前路径）。 使用这种方式，则不需要再代码中配置 tiger\_id, account, private\_key 等信息了。

此外。对于港股牌照，`tiger_openapi_token.properties` 是必须的，此文件也需放入 `props_path`指定的路径下。

Python

    from tigeropen.common.consts import (Language,        # 语言
                                    Market,           # 市场
                                    BarPeriod,        # k线周期
                                    QuoteRight)       # 复权类型
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    from tigeropen.common.util.signature_utils import read_private_key
    from tigeropen.quote.quote_client import QuoteClient
    
    def get_client_config():
        # 港股牌照需用 props_path 参数指定token路径，如 '/Users/xxx/xxx/', 如不指定则取当前路径
        # 必须使用关键字参数指定 props_path
        client_config = TigerOpenClientConfig(props_path='/Users/demo/props/')
        return client_config
    
    # 调用上方定义的函数生成用户配置ClientConfig对象
    client_config = get_client_config()

**方式二**

以查询行情为例，所有行情接口的操作都通过QuoteClient对象的成员方法实现，所以调用相关行情接口之前需要先初始化QuoteClient，具体实现方式如下：

需要先生成client\_config对象，对应以下示例的 client\_config = get\_client\_config() ，然后把该client\_config对象传入QuoteClient，来初始化QuoteClient，再调用QuoteClient具体的方法即可。`TradeClient`，`PushClient` 的初始化方式与此类似。

> ⚠️
> 
> **CAUTION**
> 
> 以下示例中的read\_private\_key('填写私钥PEM文件的路径') 对应的PEM文件需要自行生成。先把开发者页面中的 PKCS#1 格式的私钥复制到本地文件中，再把该文件的完整路径填入这里即可(包含文件名)，例如路径：`/data0/config/private_key.pem`，在 private\_key.pem中保存私钥即可。

Python

    from tigeropen.common.consts import (Language,        # 语言
                                    Market,           # 市场
                                    BarPeriod,        # k线周期
                                    QuoteRight)       # 复权类型
    from tigeropen.tiger_open_config import TigerOpenClientConfig
    from tigeropen.common.util.signature_utils import read_private_key
    from tigeropen.quote.quote_client import QuoteClient
    
    def get_client_config():
        """
        https://quant.itigerup.com/#developer 开发者信息获取
        """
        client_config = TigerOpenClientConfig() 
        # 港股牌照需用 props_path 参数指定token路径，如 props_path='/Users/xxx/xxx/', 如不指定则取当前路径
        # client_config = TigerOpenClientConfig(props_path='.')
        client_config.private_key = read_private_key('填写私钥PEM文件的路径')
        client_config.tiger_id = '替换为tigerid'
        client_config.account = '替换为账户，建议使用模拟账户'
        client_config.license = 'TBSG' # license info
        #机构账户，添加用户密钥
        client_config.secret_key = '替换为用户密钥'
        client_config.language = Language.zh_CN  #可选，不填默认为英语'
        # client_config.timezone = 'US/Eastern' # 可选时区设置
        return client_config
    
    # 调用上方定义的函数生成用户配置ClientConfig对象
    client_config = get_client_config()
    
    # 随后传入配置参数对象来初始化QuoteClient
    quote_client = QuoteClient(client_config)
    
    # 获取 00700 标的对应的行情数据
    stock_price = quote_client.get_stock_briefs(['00700'])

ClientConfig 常用配置项介绍

[](./prepare.md#clientconfig-%E5%B8%B8%E7%94%A8%E9%85%8D%E7%BD%AE%E9%A1%B9%E4%BB%8B%E7%BB%8D)

------------------------------------------------------------------------------------------------------------------------------------------------

各配置项可在 client\_config = TigerOpenClientConfig() 实例化之后，通过client\_config属性设置，如 client\_config.timeout = 60

Python

    # 开发者信息(推荐使用 props_path 的方式配置开发者信息)
    client_config.tiger_id = 1
    client_config.account = '123456'
    client_config.license = 'TBSG'
    client_config.private_key = read_private_key('私钥路径')  # 需 from tigeropen.common.util.signature_utils import read_private_key
    # 私钥也可填字符内容
    client_config.private_key = 'MIICWwIBAAKBgQCSW+.....私钥内容'
    
    # 日志级别及路径
    client_config.log_level = logging.DEBUG  # 需 import logging
    client_config.log_path = '/tmp/tigerapi.log'
    
    # 语言
    client_config.language = 'zh_CN'
    # 时区(如果配置了时区，涉及有时间字符串参数的接口，将会按当作该时区的时间。SDK默认不设置时区，服务端会当北京时间处理)
    client_config.timezone = 'US/Eastern'
    
    # 接口超时时间
    client_config.timeout = 15
    # 超时重试设置
    # 最长重试时间，单位秒
    client_config.retry_max_time = 60
    # 最多重试次数
    client_config.retry_max_tries = 5
    
    # 2FA token 刷新间隔, 单位秒。设置为0则不自动刷新。 默认不刷新
    client_config.token_refresh_duration = 24 * 60 * 60
