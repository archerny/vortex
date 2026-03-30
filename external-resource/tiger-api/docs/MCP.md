# MCP Server

您可以在任何支持 [MCP](https://modelcontextprotocol.io/docs/getting-started/intro)
 的平台上使用我们基于 SDK 为 Tiger OpenAPI 构建的 MCP 工具。 如您尚未开通 Tiger OpenAPI，请阅读 [准备工作](./prepare.md#/)
 ，完成权限申请并获取 tiger id、private key 等必要信息。

  

### 

安装 \[uv\]

[](./MCP.md#%E5%AE%89%E8%A3%85-uv)

macOS/LinuxWindows

如果您使用 macOS/Linux 系统，打开「终端」并运行以下命令完成安装：

Shell

    curl -LsSf https://astral.sh/uv/install.sh | sh

备注：\[[uv](https://docs.astral.sh/uv/getting-started/installation/)\
\]是一个 Python 包和环境管理工具，此处用来运行 MCP Server。

  

### 

添加 MCP Server

[](./MCP.md#%E6%B7%BB%E5%8A%A0-mcp-server)

在您使用的工具中找到 MCP 入口，选择**手动配置**，并粘贴以下内容。

个人客户机构客户

请替换 your tiger id、your private key 和 your account id 为您的实际值；另外如果您希望服务器以**只读模式**运行（禁止交易操作），请将 `TIGERMCP_READONLY` 设置为 true，否则为false。(也可使用 TIGEROPEN\_PROPS\_PATH 指定配置文件路径， 代替 TIGER\_ID 等配置)

JSON

    {
      "mcpServers": {
        "tigermcp": {
          "command": "uvx",
          "args": [\
            "--python",\
            "3.13",\
            "tigermcp"\
          ],
          "env": {
            "TIGEROPEN_TIGER_ID": "your tiger id",
            "TIGEROPEN_PRIVATE_KEY": "your private key",
            "TIGEROPEN_ACCOUNT": "your account id",
            "TIGERMCP_READONLY": true
          }
        }
      }
    }

macOS 12 或以下版本运行mcp server错误及解决方案

如系统为macOS 12 或以下版本，可能在运行mcp server时遇到以下错误：

Shell

    /Users/tiger/.cache/uv/archive-v0/5iV7KVbKUQlypQW-eBHBn/bin/tigermcp: line 2: realpath: command not found
    /Users/tiger/.cache/uv/archive-v0/5iV7KVbKUQlypQW-eBHBn/bin/tigermcp: line 2: /Users/tiger/python: No such file or directory
    /Users/tiger/.cache/uv/archive-v0/5iV7KVbKUQlypQW-eBHBn/bin/tigermcp: line 2: exec: /Users/tiger/python: cannot execute: No such file or directory

这是因为缺少`realpath`命令，需要安装。 首先，打开终端，输入`brew -v`查看brew命令是否存在，如果报错命令不存在，则需要先安装：

Shell

    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

然后，用 brew 安装 `coreutils`

Shell

    brew install coreutils

安装完成后，即可再次尝试运行 mcp server。

  

**免责声明**

TIGER MCP仅为用户提供一种衔接AI与TIGER API的工具，实际的输出结果依赖于AI工具特性及所连接的大型语言模型能力，其中推理、分类和数据处理能力的差异以及平台限制条件等因素均可能影响结果的准确性与完整性，具体风险包括但不限于：时间理解偏差、数据分析错误、信息截断缺失和工具调用失效等。  
所有通过使用TIGER MCP所做出的投资决策或其他操作，TIGER API用户应具备相关知识、独立进行评估判断，并完善相关的安全与权限管理，TIGER API用户将自行承担所有相关风险和责任。我们不对相关AI工具及模型输出结果的准确性、完整性、可靠性和及时性做出任何保证，也不对任何因此而引发的法律责任、经济损失或税务问题等承担任何责任。
