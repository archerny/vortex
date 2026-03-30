[#](./opend_opend-cmd.md#132)
 命令行 OpenD
================================================================================

### [#](./opend_opend-cmd.md#495)
 第一步 下载

命令行 OpenD 支持 Windows、MacOS、CentOS、Ubuntu 四种系统（点击完成下载）。

*   OpenD - [Windows](https://www.futunn.com/download/fetch-lasted-link?name=opend-windows)
     、[MacOS](https://www.futunn.com/download/fetch-lasted-link?name=opend-macos)
     、[CentOS](https://www.futunn.com/download/fetch-lasted-link?name=opend-centos)
     、[Ubuntu](https://www.futunn.com/download/fetch-lasted-link?name=opend-ubuntu)
    

### [#](./opend_opend-cmd.md#1466)
 第二步 解压

*   解压上一步下载的文件，在文件夹中找到 OpenD 配置文件 FutuOpenD.xml 和程序打包数据文件 Appdata.dat。
    *   FutuOpenD.xml 用于配置 OpenD 程序启动参数，若不存在则程序无法正常启动。
    *   Appdata.dat 是程序需要用到的一些数据量较大的信息，打包数据减少启动下载该数据的耗时，若不存在则程序无法正常启动。
*   命令行 OpenD 支持用户自定义文件路径，详见 [命令行启动参数](./opend_opend-cmd.md#465)
    。

### [#](./opend_opend-cmd.md#8799)
 第三步 参数配置

*   打开并编辑配置文件 FutuOpenD.xml，如下图所示。普通使用仅需修改账号和登录密码，其他高阶选项可以根据下表的提示进行修改。

![xml-config](https://openapi.futunn.com/futu-api-doc/assets/img/xml.00cc6239.png)

**配置项列表**：

| 配置项 | 说明  |
| --- | --- |
| ip  | 监听地址<br>(ℹ️ 可填：*   127.0.0.1（监听来自本地的连接）)<br>*   0.0.0.0（监听来自所有网卡的连接）<br>*   本机某个网卡地址<br>不设置则默认 127.0.0.1 |
| api\_port | API 协议接收端口<br>(ℹ️ 不设置则默认 11111)  <br>也可通过 [命令行启动参数](./opend_opend-cmd.md#465)<br> 指定 |
| login\_account | 登录帐号<br>(ℹ️ 支持平台ID、邮箱、手机号登录，可通过 [命令行启动参数](./opend_opend-cmd.md#465))<br> 指定  <br>*   平台ID：输入牛牛号<br>*   邮箱：xxxx@xx.com 格式<br>*   手机号：区号+手机号，例 +86 xxxxxxxx |
| login\_pwd | 登录密码明文<br>(ℹ️ *   也可使用登录密码密文输入)<br>*   也可通过 [命令行启动参数](./opend_opend-cmd.md#465)<br>     指定 |
| login\_pwd\_md5 | 登录密码密文（32 位 MD5 加密 16 进制）<br>(ℹ️ *   如果密文明文都存在，则只使用密文)<br>*   也可使用登录密码明文输入 |
| lang | 中英语言<br>(ℹ️ 可填：)  <br>*   chs：简体中文<br>*   en：英文 |
| log\_level | OpenD 日志级别<br>(ℹ️ 可填：)  <br>*   no（无日志）<br>*   debug（最详细）<br>*   info（次详细）<br>不设置则默认 info 级别 |
| push\_proto\_type | 推送协议类型<br>(ℹ️ 推送类协议通过该配置决定包体格式，可填：*   0（pb 格式）)<br>*   1（json 格式）<br>不设置则默认 pb 格式 |
| qot\_push\_frequency | API 订阅数据推送频率控制<br>(ℹ️ *   单位：毫秒)<br>*   目前不包括 K 线和分时<br>*   不设置则默认不限频 |
| telnet\_ip | 远程操作命令监听地址<br>(ℹ️ 不设置则默认 127.0.0.1) |
| telnet\_port | 远程操作命令监听端口<br>(ℹ️ 不设置则不启用远程命令) |
| rsa\_private\_key | API 协议 [RSA](./qa_other.md#4601)<br> 加密私钥（PKCS#1）文件绝对路径<br>(ℹ️ 不设置则协议不加密) |
| price\_reminder\_push | 是否接收到价提醒推送<br>(ℹ️ 可填：*   0：不接收)<br>*   1：接收（需在脚本中设置到价提醒回调函数 [set\_handler](./ftapi_init.md#8035)<br>    ）<br>不设置则默认接收 |
| auto\_hold\_quote\_right | 被踢后是否自动抢权限<br>(ℹ️ 可填：*   0：否)<br>*   1：是（OpenD 在行情权限被抢后，会自动抢回。如果 10 秒内再次被抢，则其他终端获得最高行情权限，OpenD 不会再抢）<br>不设置则默认自动抢权限 |
| future\_trade\_api\_time\_zone | 期货交易 API 时区<br>(ℹ️ *   使用期货账户调用 **交易 API** 时，涉及的时间按照此时区规则)<br>*   也可通过 [命令行启动参数](./opend_opend-cmd.md#465)<br>     指定 |
| websocket\_ip | WebSocket 服务监听地址<br>(ℹ️ 可填：)  <br>*   127.0.0.1（监听来自本地的连接）<br>*   0.0.0.0（监听来自所有网卡的连接）<br>不设置则默认 127.0.0.1 |
| websocket\_port | WebSocket 服务监听端口<br>(ℹ️ 不设置则不启用 Websocket) |
| websocket\_key\_md5 | 密钥密文（32 位 MD5 加密 16 进制）<br>(ℹ️ JavaScript 脚本连接时，用于判断是否可信连接) |
| websocket\_private\_key | WebSocket 证书私钥文件路径<br>(ℹ️ *   私钥不可设置密码)<br>*   需要和证书同时配置<br>*   不配置则不启用 Websocket |
| websocket\_cert | WebSocket 证书文件路径<br>(ℹ️ *   需要和证书同时配置)<br>*   不配置则不启用 Websocket |
| pdt\_protection | 是否开启 防止被标记为日内交易者 的功能<br>(ℹ️ **FUTU US 专用参数**)  <br>可填：*   0：否<br>*   1：是（开启功能后，我们会在您将要被标记 PDT 时阻止您的下单，但不确保您一定不被标记。若您被标记 PDT，当您的账户权益小于$25000时，您将无法开仓。）<br>不设置则默认开启功能 |
| dtcall\_confirmation | 是否开启 日内交易保证金追缴预警 的功能<br>(ℹ️ **FUTU US 专用参数**)  <br>可填：*   0：否<br>*   1：是（开启功能后，我们会在您即将开仓下单超出剩余日内交易购买力前阻止您的下单。提醒您当前开仓订单的市值大于您的剩余日内交易购买力，若您在今日平仓当前标的，您将会收到日内交易保证金追缴通知（Day-Trading Call），只能通过存入资金才能解除。）<br>不设置则默认开启功能 |

提示

*   为保证您的证券业务账户安全，如果监听地址不是本地，您必须配置私钥才能使用交易接口。行情接口不受此限制。
    
*   当 WebSocket 监听地址不是本地，需配置 SSL 才可以启动，且证书私钥生成不可设置密码。
    
*   密文是明文经过 32 位 MD5 加密后用 16 进制表示的数据，搜索在线 MD5 加密（注意，通过第三方网站计算可能有记录撞库的风险）或下载 MD5 计算工具可计算得到。32 位 MD5 密文如下图红框区域（e10adc3949ba59abbe56e057f20f883e）：
    
    ![md5.png](<Base64-Image-Removed>)
    
*   OpenD 默认读取同目录下的 FutuOpenD.xml。在 MacOS 上，由于系统保护机制，OpenD.app 在运行时会被分配一个随机路径，导致无法找到原本的路径。此时有以下方法：
    
    *   执行 tar 包下的 fixrun.sh
    *   用命令行参数`-cfg_file`指定配置文件路径，见下面说明
*   日志级别默认 info 级别，在系统开发阶段，不建议关闭日志或者将日志修改到 warning，error，fatal 级别，防止出现问题时无法定位。
    

### [#](./opend_opend-cmd.md#465)
 第四步 命令行启动

*   在命令行中切到前面解压文件夹 OpenD 文件所在的目录，使用如下命令启动，即可以 FutuOpenD.xml 配置文件中的参数启动。
    *   Windows：`FutuOpenD`
    *   Linux：`./FutuOpenD`
    *   MacOS：`./FutuOpenD.app/Contents/MacOS/FutuOpenD`

命令行启动参数

*   命令行中也可以携带参数启动，部分参数会与 FutuOpenD.xml 配置文件相同。传参格式：`-key=value` ![startup-command-param.png](<Base64-Image-Removed>)  
    例如：
    
    *   Windows：`FutuOpenD.exe -login_account=100000 -login_pwd=123456 -lang=en`
    *   Linux：`FutuOpenD -login_account=100000 -login_pwd=123456 -lang=en`
    *   MacOS：`./FutuOpenD.app/Contents/MacOS/FutuOpenD -login_account=100000 -login_pwd=123456 -lang=en`
*   相同参数若同时存在于命令行与配置文件，命令行参数优先。具体参数详见如下表格：
    

**参数列表**：

| 配置项 | 说明  |
| --- | --- |
| login\_account | 登录帐号<br>(ℹ️ 也可通过配置文件指定) |
| login\_pwd | 登录密码明文<br>(ℹ️ *   也可使用登录密码密文输入)<br>*   也可通过配置文件指定 |
| login\_pwd\_md5 | 登录密码密文（32 位 MD5 加密 16 进制）<br>(ℹ️ *   如果密文明文都存在，则只使用密文)<br>*   也可使用登录密码明文输入 |
| cfg\_file | OpenD 配置文件绝对路径<br>(ℹ️ 不设置则使用程序所在目录下的 OpenD.xml) |
| console | 是否显示控制台<br>(ℹ️ *   0：不显示)<br>*   1：显示<br>不设置则默认显示控制台 |
| lang | 中英语言<br>(ℹ️ *   chs：简体中文)<br>*   en：英文 |
| api\_ip | API 服务监听地址 |
| api\_port | API 协议接收端口 |
| help | 输出命令行启动参数，并退出程序 |
| log\_level | OpenD 日志级别<br>(ℹ️ *   no（无日志）)<br>*   debug（最详细）<br>*   info（次详细） |
| no\_monitor | 是否启动守护进程<br>(ℹ️ *   0：启动)<br>*   1：不启动 |
| websocket\_ip | WebSocket 服务监听地址<br>(ℹ️ 可填：)  <br>*   127.0.0.1（监听来自本地的连接）<br>*   0.0.0.0（监听来自所有网卡的连接） |
| websocket\_port | WebSocket 服务监听端口<br>(ℹ️ 不设置则不启用 Websocket) |
| websocket\_private\_key | WebSocket 证书私钥文件路径<br>(ℹ️ *   私钥不可设置密码)<br>*   需要和证书同时配置<br>*   不配置则不启用 Websocket |
| websocket\_cert | WebSocket 证书文件路径<br>(ℹ️ *   需要和证书同时配置)<br>*   不配置则不启用 Websocket |
| websocket\_key\_md5 | 密钥密文（32 位 MD5 加密 16 进制）<br>(ℹ️ JavaScript 脚本连接时，用于判断是否可信连接) |
| price\_reminder\_push | 是否接收到价提醒推送<br>(ℹ️ 可填：*   0：不接收)<br>*   1：接收（需在脚本中设置到价提醒回调函数 [set\_handler](./ftapi_init.md#8035)<br>    ）<br>不设置则默认接收 |
| auto\_hold\_quote\_right | 被踢后是否自动抢权限<br>(ℹ️ 可填：*   0：否)<br>*   1：是（OpenD 在行情权限被抢后，会自动抢回。如果 10 秒内再次被抢，则其他终端获得最高行情权限，OpenD 不会再抢）<br>不设置则默认自动抢权限 |
| future\_trade\_api\_time\_zone | 期货交易 API 时区<br>(ℹ️ 使用期货账户调用 **交易 API** 时，涉及的时间按照此时区规则) |

### [#](./opend_opend-cmd.md#495-2)
 第一步 下载

*   命令行 OpenD 支持 Windows、MacOS、CentOS、Ubuntu 四种系统。
*   您可以通过 [moomoo 官网](https://www.moomoo.com/download/OpenAPI)
     下载。 ![download-page](https://openapi.futunn.com/futu-api-doc/assets/img/mmdownload-page.edb5c5d4.png)

### [#](./opend_opend-cmd.md#1466-2)
 第二步 解压

*   解压上一步下载的文件，在文件夹中找到 OpenD 配置文件 OpenD.xml 和程序打包数据文件 Appdata.dat。
    *   OpenD.xml 用于配置 OpenD 程序启动参数，若不存在则程序无法正常启动。
    *   Appdata.dat 是程序需要用到的一些数据量较大的信息，打包数据减少启动下载该数据的耗时，若不存在则程序无法正常启动。
*   命令行 OpenD 支持用户自定义文件路径，详见 [命令行启动参数](./opend_opend-cmd.md#465)
    。

### [#](./opend_opend-cmd.md#8799-2)
 第三步 参数配置

*   打开并编辑配置文件 OpenD.xml，如下图所示。普通使用仅需修改账号和登录密码，其他高阶选项可以根据下表的提示进行修改。

![xml-config](https://openapi.futunn.com/futu-api-doc/assets/img/mmxml.5faae8a3.png)

**配置项列表**：

| 配置项 | 说明  |
| --- | --- |
| ip  | 监听地址<br>(ℹ️ 可填：*   127.0.0.1（监听来自本地的连接）)<br>*   0.0.0.0（监听来自所有网卡的连接）<br>*   本机某个网卡地址<br>不设置则默认 127.0.0.1 |
| api\_port | API 协议接收端口<br>(ℹ️ 不设置则默认 11111)  <br>也可通过 [命令行启动参数](./opend_opend-cmd.md#465)<br> 指定 |
| login\_account | 登录帐号<br>(ℹ️ 支持平台ID、邮箱、手机号登录，可通过 [命令行启动参数](./opend_opend-cmd.md#465))<br> 指定  <br>*   平台ID：输入moomoo号<br>*   邮箱：xxxx@xx.com 格式<br>*   手机号：区号+手机号，例 +1 xxxxxxxx |
| login\_pwd | 登录密码明文<br>(ℹ️ *   也可使用登录密码密文输入)<br>*   也可通过 [命令行启动参数](./opend_opend-cmd.md#465)<br>     指定 |
| login\_pwd\_md5 | 登录密码密文（32 位 MD5 加密 16 进制）<br>(ℹ️ *   如果密文明文都存在，则只使用密文)<br>*   也可使用登录密码明文输入 |
| lang | 中英语言<br>(ℹ️ 可填：)  <br>*   chs：简体中文<br>*   en：英文 |
| log\_level | OpenD 日志级别<br>(ℹ️ 可填：)  <br>*   no（无日志）<br>*   debug（最详细）<br>*   info（次详细）<br>不设置则默认 info 级别 |
| push\_proto\_type | 推送协议类型<br>(ℹ️ 推送类协议通过该配置决定包体格式，可填：*   0（pb 格式）)<br>*   1（json 格式）<br>不设置则默认 pb 格式 |
| qot\_push\_frequency | API 订阅数据推送频率控制<br>(ℹ️ *   单位：毫秒)<br>*   目前不包括 K 线和分时<br>*   不设置则默认不限频 |
| telnet\_ip | 远程操作命令监听地址<br>(ℹ️ 不设置则默认 127.0.0.1) |
| telnet\_port | 远程操作命令监听端口<br>(ℹ️ 不设置则不启用远程命令) |
| rsa\_private\_key | API 协议 [RSA](./qa_other.md#4601)<br> 加密私钥（PKCS#1）文件绝对路径<br>(ℹ️ 不设置则协议不加密) |
| price\_reminder\_push | 是否接收到价提醒推送<br>(ℹ️ 可填：*   0：不接收)<br>*   1：接收（需在脚本中设置到价提醒回调函数 [set\_handler](./ftapi_init.md#8035)<br>    ）<br>不设置则默认接收 |
| auto\_hold\_quote\_right | 被踢后是否自动抢权限<br>(ℹ️ 可填：*   0：否)<br>*   1：是（OpenD 在行情权限被抢后，会自动抢回。如果 10 秒内再次被抢，则其他终端获得最高行情权限，OpenD 不会再抢）<br>不设置则默认自动抢权限 |
| future\_trade\_api\_time\_zone | 期货交易 API 时区<br>(ℹ️ *   使用期货账户调用 **交易 API** 时，涉及的时间按照此时区规则)<br>*   也可通过 [命令行启动参数](./opend_opend-cmd.md#465)<br>     指定 |
| websocket\_ip | WebSocket 服务监听地址<br>(ℹ️ 可填：)  <br>*   127.0.0.1（监听来自本地的连接）<br>*   0.0.0.0（监听来自所有网卡的连接）<br>不设置则默认 127.0.0.1 |
| websocket\_port | WebSocket 服务监听端口<br>(ℹ️ 不设置则不启用 Websocket) |
| websocket\_key\_md5 | 密钥密文（32 位 MD5 加密 16 进制）<br>(ℹ️ JavaScript 脚本连接时，用于判断是否可信连接) |
| websocket\_private\_key | WebSocket 证书私钥文件路径<br>(ℹ️ *   私钥不可设置密码)<br>*   需要和证书同时配置<br>*   不配置则不启用 Websocket |
| websocket\_cert | WebSocket 证书文件路径<br>(ℹ️ *   需要和证书同时配置)<br>*   不配置则不启用 Websocket |
| pdt\_protection | 是否开启 防止被标记为日内交易者 的功能<br>(ℹ️ **FUTU US 专用参数**)  <br>可填：*   0：否<br>*   1：是（开启功能后，我们会在您将要被标记 PDT 时阻止您的下单，但不确保您一定不被标记。若您被标记 PDT，当您的账户权益小于$25000时，您将无法开仓。）<br>不设置则默认开启功能 |
| dtcall\_confirmation | 是否开启 日内交易保证金追缴预警 的功能<br>(ℹ️ **FUTU US 专用参数**)  <br>可填：*   0：否<br>*   1：是（开启功能后，我们会在您即将开仓下单超出剩余日内交易购买力前阻止您的下单。提醒您当前开仓订单的市值大于您的剩余日内交易购买力，若您在今日平仓当前标的，您将会收到日内交易保证金追缴通知（Day-Trading Call），只能通过存入资金才能解除。）<br>不设置则默认开启功能 |

提示

*   为保证您的证券业务账户安全，如果监听地址不是本地，您必须配置私钥才能使用交易接口。行情接口不受此限制。
    
*   当 WebSocket 监听地址不是本地，需配置 SSL 才可以启动，且证书私钥生成不可设置密码。
    
*   密文是明文经过 32 位 MD5 加密后用 16 进制表示的数据，搜索在线 MD5 加密（注意，通过第三方网站计算可能有记录撞库的风险）或下载 MD5 计算工具可计算得到。32 位 MD5 密文如下图红框区域（e10adc3949ba59abbe56e057f20f883e）：
    
    ![md5.png](<Base64-Image-Removed>)
    
*   OpenD 默认读取同目录下的 OpenD.xml。在 MacOS 上，由于系统保护机制，OpenD.app 在运行时会被分配一个随机路径，导致无法找到原本的路径。此时有以下方法：
    
    *   执行 tar 包下的 fixrun.sh
    *   用命令行参数`-cfg_file`指定配置文件路径，见下面说明
*   日志级别默认 info 级别，在系统开发阶段，不建议关闭日志或者将日志修改到 warning，error，fatal 级别，防止出现问题时无法定位。
    

### [#](./opend_opend-cmd.md#465-2)
 第四步 命令行启动

*   在命令行中切到前面解压文件夹 OpenD 文件所在的目录，使用如下命令启动，即可以 OpenD.xml 配置文件中的参数启动。
    *   Windows：`OpenD`
    *   Linux：`./OpenD`
    *   MacOS：`./OpenD.app/Contents/MacOS/OpenD`

命令行启动参数

*   命令行中也可以携带参数启动，部分参数会与 OpenD.xml 配置文件相同。传参格式：`-key=value` ![startup-command-param.png](<Base64-Image-Removed>)  
    例如：
    
    *   Windows：`OpenD.exe -login_account=100000 -login_pwd=123456 -lang=en`
    *   Linux：`OpenD -login_account=100000 -login_pwd=123456 -lang=en`
    *   MacOS：`./OpenD.app/Contents/MacOS/OpenD -login_account=100000 -login_pwd=123456 -lang=en`
*   相同参数若同时存在于命令行与配置文件，命令行参数优先。具体参数详见如下表格：
    

**参数列表**：

| 配置项 | 说明  |
| --- | --- |
| login\_account | 登录帐号<br>(ℹ️ 也可通过配置文件指定) |
| login\_pwd | 登录密码明文<br>(ℹ️ *   也可使用登录密码密文输入)<br>*   也可通过配置文件指定 |
| login\_pwd\_md5 | 登录密码密文（32 位 MD5 加密 16 进制）<br>(ℹ️ *   如果密文明文都存在，则只使用密文)<br>*   也可使用登录密码明文输入 |
| cfg\_file | OpenD 配置文件绝对路径<br>(ℹ️ 不设置则使用程序所在目录下的 OpenD.xml) |
| console | 是否显示控制台<br>(ℹ️ *   0：不显示)<br>*   1：显示<br>不设置则默认显示控制台 |
| lang | 中英语言<br>(ℹ️ *   chs：简体中文)<br>*   en：英文 |
| api\_ip | API 服务监听地址 |
| api\_port | API 协议接收端口 |
| help | 输出命令行启动参数，并退出程序 |
| log\_level | OpenD 日志级别<br>(ℹ️ *   no（无日志）)<br>*   debug（最详细）<br>*   info（次详细） |
| no\_monitor | 是否启动守护进程<br>(ℹ️ *   0：启动)<br>*   1：不启动 |
| websocket\_ip | WebSocket 服务监听地址<br>(ℹ️ 可填：)  <br>*   127.0.0.1（监听来自本地的连接）<br>*   0.0.0.0（监听来自所有网卡的连接） |
| websocket\_port | WebSocket 服务监听端口<br>(ℹ️ 不设置则不启用 Websocket) |
| websocket\_private\_key | WebSocket 证书私钥文件路径<br>(ℹ️ *   私钥不可设置密码)<br>*   需要和证书同时配置<br>*   不配置则不启用 Websocket |
| websocket\_cert | WebSocket 证书文件路径<br>(ℹ️ *   需要和证书同时配置)<br>*   不配置则不启用 Websocket |
| websocket\_key\_md5 | 密钥密文（32 位 MD5 加密 16 进制）<br>(ℹ️ JavaScript 脚本连接时，用于判断是否可信连接) |
| price\_reminder\_push | 是否接收到价提醒推送<br>(ℹ️ 可填：*   0：不接收)<br>*   1：接收（需在脚本中设置到价提醒回调函数 [set\_handler](./ftapi_init.md#8035)<br>    ）<br>不设置则默认接收 |
| auto\_hold\_quote\_right | 被踢后是否自动抢权限<br>(ℹ️ 可填：*   0：否)<br>*   1：是（OpenD 在行情权限被抢后，会自动抢回。如果 10 秒内再次被抢，则其他终端获得最高行情权限，OpenD 不会再抢）<br>不设置则默认自动抢权限 |
| future\_trade\_api\_time\_zone | 期货交易 API 时区<br>(ℹ️ 使用期货账户调用 **交易 API** 时，涉及的时间按照此时区规则) |

← [概述](./opend_opend-intro.md) [运维命令](./opend_opend-operate.md)
 →

[命令行 OpenD](./opend_opend-cmd.md)