# 准备工作

环境要求

[](./prepare-java.md#%E7%8E%AF%E5%A2%83%E8%A6%81%E6%B1%82)

---------------------------------------------------------------------------------------------

*   操作系统要求：
    *   Windows
    *   MacOS
    *   Linux
    *   Solaris
*   编程语言版本要求：Java JDK 1.7（64位）及以上。

安装 Java Development Kit

[](./prepare-java.md#%E5%AE%89%E8%A3%85-java-development-kit)

-------------------------------------------------------------------------------------------------------------------

推荐安装 JDK 1.8+。可在终端输入 `java -version` 检查版本。

Oracle JDKAmazon Corretto（推荐）Adoptium

访问 [Oracle JDK 下载页面](https://www.oracle.com/java/technologies/downloads/)
，选择操作系统对应的安装包，按提示完成安装。

获取 Tiger Open API Java SDK

[](./prepare-java.md#%E8%8E%B7%E5%8F%96-tiger-open-api-java-sdk)

-------------------------------------------------------------------------------------------------------------------------

Maven（推荐）Gradle从源码构建

在项目 `pom.xml` 中添加依赖：

XML

    <dependency>
      <groupId>io.github.tigerbrokers</groupId>
      <artifactId>openapi-java-sdk</artifactId>
      <version>2.4.1</version>
    </dependency>

如果无法下载，尝试添加仓库源：

XML

    <repositories>
      <repository>
        <id>sonatype-public</id>
        <name>sonatype-public</name>
        <url>https://oss.sonatype.org/content/groups/public/</url>
      </repository>
    </repositories>

开发工具

[](./prepare-java.md#%E5%BC%80%E5%8F%91%E5%B7%A5%E5%85%B7)

---------------------------------------------------------------------------------------------

AI 编程工具（推荐）传统 IDE

使用 AI 编程工具可以自动阅读 Tiger OpenAPI 文档，通过自然语言描述需求即可生成可运行的 SDK 代码，极大降低上手门槛。

**使用方式**

1.  用上述任一方式创建好项目并添加 SDK 依赖
2.  将配置文件 `tiger_openapi_config.properties` 放入项目目录
3.  将 [Tiger OpenAPI 文档](https://quant.itigerup.com/openapi/java/overview/introduction)
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

启动后，将文档链接发给 AI 即可，例如："参考 [https://quant.itigerup.com/openapi/java/overview/introduction](https://quant.itigerup.com/openapi/java/overview/introduction)
 帮我获取持仓信息"。

注册开发者信息

[](./prepare-java.md#%E6%B3%A8%E5%86%8C%E5%BC%80%E5%8F%91%E8%80%85%E4%BF%A1%E6%81%AF)

---------------------------------------------------------------------------------------------------------------------------

使用 API 之前请首先开通权限， 个人用户访问 API 官网 [登记开发者身份](https://developer.itigerup.com/profile)
 **推荐使用Chrome浏览器打开**。

**注意：开通 Open API 需要在老虎开户并入金，同时会要求开发人员和用户签署API授权协议。**

随后需要在此页面中完成开发者信息的登记，请填入并提交您的信息。

**注册成功后您可在此页面中获取以下信息：**

*   **tigerId：** 开放平台为每一位开发者分配的唯一 ID，用来标识一个开发者，所有 API 接口的调用都会用到 tigerId。
*   **account：** 用户的资金账号，在请求交易相关接口时需要用到资金账号。具体分为环球账号、综合账号与模拟账号，
    *   **环球资金账号（Global）**：以大写字母 U 开头，如：U12300123，
    *   **综合资金账号（Prime）**：为一串较短的数字（ 5 到 10 位），如：51230321，
    *   **模拟资金账号（Paper）**：17 位数字，如：20191106192858300，

注册开发者信息成功后只会返回已成功入金的资金账号和模拟账号。如果用户的环球账号和综合账号都已成功入金，则都会返回。

**开发者注册页面：** 通过手机号和验证码注册即可。

![](https://files.readme.io/89d19dde6f2d783caf8997eccf0b6ecee8078203740365ff8734c9734ac9b154-image.png)

**开发者信息页面：** 其中 Tiger ID，实盘账户，模拟盘账号，牌照等信息需要在OpenAPI中用到。

![](https://files.readme.io/d33d3527be9b6b996bc1bc07eb57c5b59c7c1ff3834078a9f9b856e244e83800-image.png)

**注意：需要把下图中的私钥部分保存到本地并妥善保管以防泄露，如发现泄露请及时更新。 私钥不会在老虎服务端保存，用户在刷新页面前需要保存好。页面刷新后该私钥会自动消失，如私钥未保存好，可以通过`重新生成`按钮进行替换。**

**重新生成后，下载 tiger\_openapi\_config.properties 文件到本地。把 tiger\_openapi\_config.properties 文件拷贝到 ClientConfig.DEFAULT\_CONFIG.configFilePath 配置的目录下。**

![](https://files.readme.io/f0a2486386b7befbb0fdee16588a6b38acb00d33f20e83a894671b6c8b358f29-image.png)

tiger\_openapi\_config.properties 文件内容格式如下，account 为默认账号，可以在实盘资金账号和模拟盘账号之间替换。

Text

    private_key_pk1=MIICXgIBAAKBgQCodM4fM5cz+tj8SUuxxSbSMFxNHM23hftjAuXr9THweyFtTmA/mxwo9HYEhYjxCgYAJt78xbzw7aiKu+pI45O8il8ns6UL3mT59QlCKu0+FZmF2g54teyqoiol71xcM096D3ss0oJzKGt+btvlBzdcrXM6+pZqbK14ASWGE4uBEQIDAQABAoGAX5iBjFVTngzhbDIQAZ73C9qj1qLc3yPqZ+KyJbgskeLeMlCC8DWOwnMQMjqxXtAoXZexfVKdiT/lIG3JY3m6URzfYwJt5VUD9LeIcyPxuj7Eg2vxlZwJ6slT7DQ6LJCbUe1PUDlKKXYe/wEUNJ3dEJ/eFEhR8vNyFlW8DJj+az0CQQDhQwYUZIcq+9WDcSen5Sm5oS765aMAyWcOdkpokau+fZ/H9NwT0U+jaEHyswZMJBTDJ42iS8HV0MuGdtLJ7JzHAkEAv3Fq1AHHJ1Tyxtf/VWHBRfx304NFlDX87Ji7uyv1t+05ZYVT0U8Q5CUPdBA3QexMBKd1i6BXUH/xOY5aX+UrZwJBAKR7ej79wK0kQh2+TESOUs9W4KUXdNSoO3YV7fM5Gvz3Yepx4xkdZ1dcU3tSObDzT63IfeUaCCmGUnaW4QfMnBUCQQCMOsK6mrbLe7D9sBgK7bkJz73iA+UChG1IzTOuDaxGVpDVaU0VI2pHA1KHfJ1NP44LVrKGXSO4Bo+mzjwqxA3/AkEApA8roUKXtKTqC2rb8hs5AscMpHFUl7ZbVeKX14gAg9QPWxkh8BcB1dljAsrp7irEtq4VezD2Cf40b52Fcx+pvA==
    private_key_pk8=MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKh0zh8zlzP62PxJS7HFJtIwXE0czbeF+2MC5ev1MfB7IW1OYD+bHCj0dgSFiPEKBgAm3vzFvPDtqIq76kjjk7yKXyezpQveZPn1CUIq7T4VmYXaDni17KqiKiXvXFwzT3oPeyzSgnMoa35u2+UHN1ytczr6lmpsrXgBJYYTi4ERAgMBAAECgYBfmIGMVVOeDOFsMhABnvcL2qPWotzfI+pn4rIluCyR4t4yUILwNY7CcxAyOrFe0Chdl7F9Up2JP+UgbcljebpRHN9jAm3lVQP0t4hzI/G6PsSDa/GVnAnqyVPsNDoskJtR7U9QOUopdh7/ARQ0nd0Qn94USFHy83IWVbwMmP5rPQJBAOFDBhRkhyr71YNxJ6flKbmhLvrlowDJZw52SmiRq759n8f03BPRT6NoQfKzBkwkFMMnjaJLwdXQy4Z20snsnMcCQQC/cWrUAccnVPLG1/9VYcFF/HfTg0WUNfzsmLu7K/W37TllhVPRTxDkJQ90EDdB7EwEp3WLoFdQf/E5jlpf5StnAkEApHt6Pv3ArSRCHb5MRI5Sz1bgpRd01Kg7dhXt8zka/Pdh6nHjGR1nV1xTe1I5sPNPrch95RoIKYZSdpbhB8ycFQJBAIw6wrqatst7sP2wGArtuQnPveID5QKEbUjNM64NrEZWkNVpTRUjakcDUod8nU0/jgtWsoZdI7gGj6bOPCrEDf8CQQCkDyuhQpe0pOoLatvyGzkCxwykcVSXtltV4pfXiACD1A9bGSHwFwHV2WMCyunuKsS2rhV7MPYJ/jRvnYVzH6m8
    tiger_id=20150001
    account=12345678
    license=TBHK
    env=PROD

Token（可选）

[](./prepare-java.md#token%E5%8F%AF%E9%80%89)

-------------------------------------------------------------------------------------

**TBHK 牌照(其他牌照用户可以忽略)，需要生成 token，token 失效后需重新生成，并下载tiger\_openapi\_token.properties 文件到本地。把 tiger\_openapi\_token.properties 文件拷贝到ClientConfig.DEFAULT\_CONFIG.configFilePath 配置的目录下。**

![](https://files.readme.io/eca8577964fd08416f65b38a8080b80a7c3e89e6801aa6c5ad1317a0875465ce-image.png)

Token 的有效期为 30 天，如果失效后需要到开发者信息页面重新生成并导出新的 Token 文件。在失效前，可以通过刷新Token 的 API 接口续期。SDK 默认不自动刷新。如配置 `clientConfig.isAutoRefreshToken = true` ，默认机制为：每5天刷新一次 Token，刷新成功后会同时更新本地 `tiger_openapi_token.properties` 文件，可配置自动刷新周期的天数（refreshTokenIntervalDays）和具体时间（refreshTokenTime）。如需自行刷新token，请配置`clientConfig.isAutoRefreshToken = false`。

**额外配置，非必须：**

| 信息  | 是否必填 | 说明  |
| --- | --- | --- |
| IP白名单 | 否   | 只有在白名单内的IP才可以访问API接口，多个IP间以 “;” 分隔，非必填 |
| 回调URL | 否   | 用户应用程序的回调地址，可以用于接收订单、持仓、资产的变更消息。非必填，用户也可以直接通过SDK提供的订阅接口接收回调消息 |

  

机构中心

[](./prepare-java.md#%E6%9C%BA%E6%9E%84%E4%B8%AD%E5%BF%83)

---------------------------------------------------------------------------------------------

机构用户请访问 [机构账户中心](./contact.md#institution_link)

账户开通并注入资金后，可登录机构账户中心的老虎账户，并前往「交易设置 > 开通OpenAPI」完成开通流程。

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

![](https://files.readme.io/51e5c7ae9adcabf602d2a02ff4293906312a88baadbcd7c74e1682e12262551b-image.png) ![](https://files.readme.io/7bccec2c6c18ae5109365c6ce3b882d407b60cd4fa6c90faeef272427be96ce3-image.png)  
  

购买行情（可选）

[](./prepare-java.md#%E8%B4%AD%E4%B9%B0%E8%A1%8C%E6%83%85%E5%8F%AF%E9%80%89)

-------------------------------------------------------------------------------------------------------------------

我们免费提供延迟行情接口，但实时行情需要另外购买。Open API 的行情权限独立与 APP 与 PC 端，如果您已经购买了APP 或 PC 行情，也需要另外购买 Open API 的行情权限以获得实时数据。具体购买方法如下：

**个人客户**

有两种购买方式：

1、登录[个人中心](https://developer.itigerup.com/profile)
购买行情

![](https://files.readme.io/5b28140981316eb41ff5fded829e3325a50ef0c67cc9cab4140e54e900a3ed15-image.png)

2、在手机端APP **Tiger Trade APP - 我的 - 行情权限 - OpenAPI权限** 中进行购买

**机构客户**

在 **机构中心-行情权限** 中进行购买

![](https://files.readme.io/6c5765438e08697b6496c259c9fdfe3abc323cb11be3ff4701e2c03e136d3ccc-image.png)

API 相关配置

[](./prepare-java.md#api-%E7%9B%B8%E5%85%B3%E9%85%8D%E7%BD%AE)

-----------------------------------------------------------------------------------------------------

在正式请求接口前，需要完成API接口调用的相关配置。具体配置信息（包括tigerId，account，license，privateKey等配置，优先使用 tiger\_openapi\_config.properties 文件的值）可以在开发者信息页面查看。

对于港股牌照，tiger\_openapi\_token.properties 是必须的，此文件也需放入configFilePath指定的路径下。

Java

    public static ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
    public static TigerHttpClient client;
    static {
            // 开启日志. log file name: tiger_openapi.2023-02-22.log
            ApiLogger.setEnabled(true, "/data/tiger_openapi/logs/");
            // ApiLogger.setDebugEnabled(false);        // 开启debug级别日志
            // The tiger_openapi_config.properties file is stored in your local directory.
            clientConfig.configFilePath = "/data/tiger_config"; // your local directory
            // clientConfig.isSslSocket = true;         // default is true
            // clientConfig.isAutoGrabPermission = true;// default is true
            // clientConfig.failRetryCounts = 2;        // fail retry count, default is 2
            // clientConfig.timeZone = TimeZoneId.Shanghai; // default time zone
            // clientConfig.language = Language.en_US;  // default language
            // clientConfig.isAutoRefreshToken = false;  // default is false, only support 'TBHK' license
            // clientConfig.refreshTokenIntervalDays = 5; // default is 5; refresh the token every 5 days
            // clientConfig.refreshTokenTime = "12:30:00";  // default is empty, 格式为：HH:mm:ss
            // clientConfig.secretKey = "xxxxxx";// 机构用户私钥
      
            // 原来旧的使用方式（不使用tiger_openapi_config.properties文件），必须配置tigerId, defaultAccount, privateKey三项，如果同时配置了configFilePath路径properties文件配置内容优先
            // clientConfig.tigerId = "your tiger id";
            // clientConfig.defaultAccount = "your account";
            // clientConfig.privateKey = FileUtil.readPrivateKey("/Users/tiger/rsa_private_key_pkcs8.pem");
      			// clientConfig.token = "xx";（TBHK牌照需要配置）
    client = TigerHttpClient.getInstance().clientConfig(clientConfig);
    }

配置说明：

> *   clientConfig.configFilePath: tiger\_openapi\_config.properties文件和tiger\_openapi\_token.properties文件存放目录
> *   clientConfig.tigerId：开发者ID（tiger\_openapi\_config.properties文件配置优先）
> *   clientConfig.defaultAccount：资金账号，可以填综合账号或者模拟账号（tiger\_openapi\_config.properties文件配置优先）
> *   clientConfig.privateKey：注册开发者信息时在页面上生成的RSA私钥（tiger\_openapi\_config.properties文件配置优先）
> *   clientConfig.secretKey：是机构交易员密钥，如果是机构用户，需要配置该信息; 个人用户请不要设置该字段
> *   clientConfig.token = "xx"：是TBHK牌照用户需要配置的两步验证信息
> *   clientConfig.isSslSocket：长连接是否使用SSL
> *   clientConfig.isAutoGrabPermission：是否启动时本设备抢占一次行情
> *   clientConfig.failRetryCounts：API请求失败重试次数，最多不超过5次
> *   clientConfig.timeZone：默认时区，请求参数时使用
> *   clientConfig.language：默认语言，请求参数时使用

如上示例中的clientConfig 和 client 变量都可以配置成全局静态变量，放到单独的配置类中，在需要使用的地方直接引用即可。既可以方便调用，同时也可以降低开销。
