# 命令行工具 (CLI)

Tiger OpenAPI Python SDK 提供了 `tigeropen` 命令行工具，可以直接在终端中查询行情、管理订单、查看账户信息等，无需编写代码。

  

### 

安装

[](./CLI.md#%E5%AE%89%E8%A3%85)

推荐使用一键安装脚本，自动检测并选择最佳安装方式（uv > pipx > pip）：

Shell

    curl -fsSL https://raw.githubusercontent.com/tigerfintech/openapi-python-sdk/master/install.sh | sh

如果您希望手动安装，推荐使用 [uv](https://docs.astral.sh/uv/)
（高速 Python 包管理器）：

Shell

    # 安装 uv（如尚未安装）
    # macOS / Linux:
    curl -LsSf https://astral.sh/uv/install.sh | sh
    # 或通过 pip:
    pip install uv
    
    # 使用 uv 安装 tigeropen
    uv pip install tigeropen

也可以通过 pipx 或 pip 安装：

Shell

    # pipx（自动创建独立环境）
    pipx install tigeropen
    
    # pip（安装到当前环境，适合需要在代码中使用 SDK 的场景）
    pip install tigeropen

安装完成后，在终端运行 `tigeropen` 即可查看使用帮助。

  

### 

配置

[](./CLI.md#%E9%85%8D%E7%BD%AE)

在使用 CLI 前，需要完成 Tiger OpenAPI 的配置。您可以通过以下两种方式进行配置：

交互式配置环境变量

运行以下命令，按照提示输入您的 Tiger ID、Account、Private Key 等信息：

Shell

    tigeropen config init

配置完成后，信息将保存到 `~/.tigeropen/` 目录下。

  

### 

全局选项

[](./CLI.md#%E5%85%A8%E5%B1%80%E9%80%89%E9%A1%B9)

以下选项可以在任意子命令中使用：

| 选项  | 说明  |
| --- | --- |
| `-f, --format` | 输出格式：`table`、`json`、`csv`，默认 `json` |
| `-c, --config-path` | 配置文件目录或文件路径 |
| `-l, --language` | 语言：`en_US`、`zh_CN`、`zh_TW`，默认 `en_US` |
| `-v, --verbose` | 启用详细日志输出 |
| `-h, --help` | 显示帮助信息 |

  

### 

股票查询

[](./CLI.md#%E8%82%A1%E7%A5%A8%E6%9F%A5%E8%AF%A2)

Shell

    # 查询股票实时行情
    tigeropen quote briefs AAPL TSLA
    
    # 包含盘前盘后数据
    tigeropen quote briefs AAPL --hour-trading
    
    # 查询日K线，默认返回最近251条
    tigeropen quote bars AAPL --period day
    
    # 查询5分钟K线，指定数量
    tigeropen quote bars AAPL --period 5min --limit 50
    
    # 指定时间范围
    tigeropen quote bars AAPL --period day --begin-time 2025-01-01 --end-time 2025-03-01
    
    # 查询当日分时数据
    tigeropen quote timeline AAPL
    
    # 查询指定日期的分时数据
    tigeropen quote timeline AAPL --date 2025-03-20
    
    # 逐笔成交
    tigeropen quote ticks AAPL --limit 100
    
    # 盘口数据
    tigeropen quote depth AAPL --market US
    
    # 市场状态
    tigeropen quote market-status --market US
    
    # 股票代码列表
    tigeropen quote symbols --market US

  

### 

期权查询

[](./CLI.md#%E6%9C%9F%E6%9D%83%E6%9F%A5%E8%AF%A2)

Shell

    # 查询期权到期日
    tigeropen quote option expirations AAPL
    
    # 查询期权链
    tigeropen quote option chain AAPL 2025-06-20
    
    # 查询期权行情
    tigeropen quote option briefs "AAPL  250620C00200000"
    
    # 查询期权K线
    tigeropen quote option bars "AAPL  250620C00200000" --period day

  

### 

期货查询

[](./CLI.md#%E6%9C%9F%E8%B4%A7%E6%9F%A5%E8%AF%A2)

Shell

    # 查看可用期货交易所
    tigeropen quote future exchanges
    
    # 查看交易所下的期货合约
    tigeropen quote future contracts CME
    
    # 查询期货行情
    tigeropen quote future briefs CL2509
    
    # 查询期货K线
    tigeropen quote future bars CL2509 --period day

  

### 

资金流向

[](./CLI.md#%E8%B5%84%E9%87%91%E6%B5%81%E5%90%91)

Shell

    # 查询资金流入流出
    tigeropen quote capital flow AAPL --market US --period day
    
    # 查询资金分布
    tigeropen quote capital distribution AAPL --market US

  
  

### 

订单管理

[](./CLI.md#%E8%AE%A2%E5%8D%95%E7%AE%A1%E7%90%86)

Shell

    # 查看订单列表
    tigeropen trade order list
    
    # 按状态筛选（Filled/Cancelled/Submitted）
    tigeropen trade order list --status Filled --market US
    
    # 查看订单详情
    tigeropen trade order get 12345678
    
    # 预览订单
    tigeropen trade order preview --symbol AAPL --action BUY --quantity 100 --limit-price 150.00
    
    # 下单
    tigeropen trade order place --symbol AAPL --action BUY --order-type LMT --quantity 100 --limit-price 150.00
    
    # 修改订单
    tigeropen trade order modify 12345678 --limit-price 151.00
    
    # 撤单
    tigeropen trade order cancel 12345678

  

### 

持仓查询

[](./CLI.md#%E6%8C%81%E4%BB%93%E6%9F%A5%E8%AF%A2)

Shell

    # 查看所有持仓
    tigeropen trade position list
    
    # 按证券类型和市场筛选
    tigeropen trade position list --sec-type STK --market US
    
    # 按标的筛选
    tigeropen trade position list --symbol AAPL

  

### 

成交记录

[](./CLI.md#%E6%88%90%E4%BA%A4%E8%AE%B0%E5%BD%95)

Shell

    tigeropen trade transaction list --symbol AAPL --start-time 2025-01-01 --end-time 2025-03-01

  

### 

账户信息

[](./CLI.md#%E8%B4%A6%E6%88%B7%E4%BF%A1%E6%81%AF)

Shell

    # 查看账户信息
    tigeropen account info
    
    # 查看资产概况
    tigeropen account assets
    
    # 指定币种查看资产
    tigeropen account assets --currency USD
    
    # 查看资产分析
    tigeropen account analytics --start-date 2025-01-01 --end-date 2025-03-01

  

### 

实时推送

[](./CLI.md#%E5%AE%9E%E6%97%B6%E6%8E%A8%E9%80%81)

CLI 支持订阅实时数据流，按 `Ctrl+C` 停止订阅：

Shell

    # 订阅实时行情
    tigeropen push quote AAPL TSLA
    
    # 订阅订单状态变化
    tigeropen push order
    
    # 订阅持仓变化
    tigeropen push position
    
    # 订阅资产变化
    tigeropen push asset

  

### 

配置管理

[](./CLI.md#%E9%85%8D%E7%BD%AE%E7%AE%A1%E7%90%86)

Shell

    # 查看当前配置（私钥信息已脱敏）
    tigeropen config show
    
    # 修改单个配置项
    tigeropen config set tiger_id your_new_tiger_id
    
    # 查看配置文件路径
    tigeropen config path

  

### 

其他命令

[](./CLI.md#%E5%85%B6%E4%BB%96%E5%91%BD%E4%BB%A4)

Shell

    # 查看版本
    tigeropen version
    
    # 卸载
    tigeropen uninstall
    
    # 卸载并移除配置目录
    tigeropen uninstall --remove-config

  

### 

输出格式

[](./CLI.md#%E8%BE%93%E5%87%BA%E6%A0%BC%E5%BC%8F)

CLI 支持三种输出格式，通过 `-f` 参数切换。默认为 `json`。

#### 

JSON 格式（默认）

[](./CLI.md#json-%E6%A0%BC%E5%BC%8F%E9%BB%98%E8%AE%A4)

适合程序处理和管道操作。输出带缩进的 JSON，中文字符直接显示不转义。

Shell

    tigeropen quote briefs AAPL -f json

返回示例：

JSON

    [\
      {\
        "symbol": "AAPL",\
        "open": 217.565,\
        "high": 220.48,\
        "low": 216.23,\
        "close": 220.37,\
        "pre_close": 218.27,\
        "latest_price": 220.37,\
        "latest_time": "2025-03-21 16:00:00",\
        "volume": 34552403,\
        "amount": 7544389505,\
        "status": "NORMAL"\
      }\
    ]

#### 

表格格式

[](./CLI.md#%E8%A1%A8%E6%A0%BC%E6%A0%BC%E5%BC%8F)

适合终端直接阅读，以对齐的列表形式展示。

Shell

    tigeropen quote briefs AAPL -f table

返回示例：

    symbol    open     high      low    close  pre_close  latest_price         latest_time    volume       amount  status
      AAPL  217.565  220.48  216.23   220.37     218.27        220.37  2025-03-21 16:00:00  34552403  7544389505  NORMAL

#### 

CSV 格式

[](./CLI.md#csv-%E6%A0%BC%E5%BC%8F)

适合导入 Excel 等表格工具进行进一步分析。

Shell

    tigeropen quote briefs AAPL -f csv

返回示例：

csv

    symbol,open,high,low,close,pre_close,latest_price,latest_time,volume,amount,status
    AAPL,217.565,220.48,216.23,220.37,218.27,220.37,2025-03-21 16:00:00,34552403,7544389505,NORMAL

#### 

格式选项可放在任意位置

[](./CLI.md#%E6%A0%BC%E5%BC%8F%E9%80%89%E9%A1%B9%E5%8F%AF%E6%94%BE%E5%9C%A8%E4%BB%BB%E6%84%8F%E4%BD%8D%E7%BD%AE)

`-f` 是全局选项，可以放在命令行的任意位置：

Shell

    # 以下写法等效
    tigeropen quote briefs AAPL -f table
    tigeropen -f table quote briefs AAPL
    tigeropen quote briefs AAPL TSLA -f csv

  

### 

频率限制

[](./CLI.md#%E9%A2%91%E7%8E%87%E9%99%90%E5%88%B6)

CLI 的请求频率限制与 Tiger OpenAPI 一致，详情请参阅 [请求频率与限制](./ratelimit.md#/)
 。
