# 准备工作

环境要求

[](./prepare-cpp.md#%E7%8E%AF%E5%A2%83%E8%A6%81%E6%B1%82)

--------------------------------------------------------------------------------------------

*   操作系统要求：
    *   Windows
    *   MacOS
    *   Linux
*   编程语言版本要求：C++17 及以上
*   编译器要求：支持 C++17 的编译器（如 GCC 7+、Clang 5+、MSVC 2017+）

依赖库

[](./prepare-cpp.md#%E4%BE%9D%E8%B5%96%E5%BA%93)

----------------------------------------------------------------------------------

C++ SDK 依赖以下第三方库：

| 库   | 用途  |
| --- | --- |
| **cpprestsdk** (C++ REST SDK) | HTTP 请求和 JSON 处理 |
| **OpenSSL** | 加密和签名 |
| **Boost** | 基础库支持 |
| **Protobuf** | 推送数据的序列化与反序列化 |

获取 SDK 并编译

[](./prepare-cpp.md#%E8%8E%B7%E5%8F%96-sdk-%E5%B9%B6%E7%BC%96%E8%AF%91)

----------------------------------------------------------------------------------------------------------------

SDK 源代码托管在 GitHub：[tigerfintech/openapi-cpp-sdk](https://github.com/tigerfintech/openapi-cpp-sdk)

Shell

    git clone https://github.com/tigerfintech/openapi-cpp-sdk.git

macOSLinux (Ubuntu/Debian)Windows

Shell

    # 安装依赖
    brew install boost openssl cpprestsdk protobuf
    
    # 编译
    cd openapi-cpp-sdk
    mkdir build && cd build
    cmake ..
    make

开发工具

[](./prepare-cpp.md#%E5%BC%80%E5%8F%91%E5%B7%A5%E5%85%B7)

--------------------------------------------------------------------------------------------

AI 编程工具（推荐）传统 IDE

使用 AI 编程工具可以自动阅读 Tiger OpenAPI 文档，通过自然语言描述需求即可生成可运行的 SDK 代码，极大降低上手门槛。

**使用方式**

1.  按上述步骤完成 SDK 编译
2.  将配置文件 `tiger_openapi_config.properties` 放入项目目录
3.  将 [Tiger OpenAPI 文档](https://quant.itigerup.com/openapi/cpp/overview/introduction)
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
    cd openapi-cpp-sdk
    claude

启动后，将文档链接发给 AI 即可，例如："参考 [https://docs.itigerup.com/docs/prepare-cpp](https://docs.itigerup.com/docs/prepare-cpp)
 帮我获取持仓信息"。

注册开发者信息

[](./prepare-cpp.md#%E6%B3%A8%E5%86%8C%E5%BC%80%E5%8F%91%E8%80%85%E4%BF%A1%E6%81%AF)

--------------------------------------------------------------------------------------------------------------------------

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

**注意：需要把私钥部分保存到本地并妥善保管以防泄露，如发现泄露请及时更新。 私钥不会在老虎服务端保存，用户在刷新页面前需要保存好。**

**C++ SDK 使用的私钥为 PKCS#8 格式。若使用 SDK 遇到问题时，注意先检查私钥的格式是否正确。**

生成的配置文件 tiger\_openapi\_config.properties 文件内容格式如下，account 为默认账号，可以在实盘资金账号和模拟盘账号之间替换。如果是机构账户，还需要配置 `secret_key` (需在机构中心获取).

    private_key_pk8=MIICeAIBADANBgkqhkiG9w0BAQ...
    tiger_id=20150001
    account=12345678
    license=TBHK
    env=PROD

购买行情（可选）

[](./prepare-cpp.md#%E8%B4%AD%E4%B9%B0%E8%A1%8C%E6%83%85%E5%8F%AF%E9%80%89)

------------------------------------------------------------------------------------------------------------------

我们免费提供延迟行情接口，但实时行情需要另外购买。Open API的行情权限独立与APP与PC端，如果您已经购买了APP或PC行情，也需要另外购买Open API的行情权限以获得实时数据。

API 相关配置

[](./prepare-cpp.md#api-%E7%9B%B8%E5%85%B3%E9%85%8D%E7%BD%AE)

----------------------------------------------------------------------------------------------------

在正式请求接口前，需要完成API接口调用的相关配置。

**方式一：使用配置文件（推荐）**

在开发者网站导出配置文件 `tiger_openapi_config.properties`，放入合适的系统路径。然后通过 `ClientConfig` 构造函数传入路径：

C++

    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    // 通过配置文件初始化（推荐）
    ClientConfig config(false, U("/path/to/your/properties/"));

**方式二：代码中直接配置**

C++

    #include "tigerapi/client_config.h"
    
    using namespace TIGER_API;
    
    ClientConfig config(U("your_tiger_id"), U("your_private_key"), U("your_account"));
    config.license = U("TBSG");
    config.lang = U("zh_CN");

ClientConfig 常用配置项介绍

[](./prepare-cpp.md#clientconfig-%E5%B8%B8%E7%94%A8%E9%85%8D%E7%BD%AE%E9%A1%B9%E4%BB%8B%E7%BB%8D)

----------------------------------------------------------------------------------------------------------------------------------------------------

C++

    // 构造函数
    ClientConfig config(false, U("/path/to/your/properties/"));
    
    // 主要属性
    config.tiger_id;       // Tiger ID
    config.private_key;    // 私钥
    config.account;        // 资金账号
    config.license;        // 牌照，如 "TBSG"
    config.lang;           // 语言，如 "zh_CN"、"en_US"
    config.secret_key;     // 机构账户密钥
    
    // 推送相关配置
    config.use_full_tick;    // 是否使用完整 tick 数据，默认 false
    config.send_interval;    // 发送间隔（毫秒），默认 10000
    config.receive_interval; // 接收间隔（毫秒），默认 10000
