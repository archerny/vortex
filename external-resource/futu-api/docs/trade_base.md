 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/base.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/base.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/base.html)
    

下载

*   [PDF](https://openapi.futunn.com/pdfs/Futu-API-Doc-zh-Python.pdf)
    
*   [Markdown](https://openapi.futunn.com/mds/Futu-API-Doc-zh-Python.md)
    
*   [Skills](https://openapi.futunn.com/skills/opend-skills.zip)
    

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/base.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/base.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/base.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
*   交易接口
    
    *   [交易接口总览](https://openapi.futunn.com/futu-api-doc/trade/overview.html)
        
    *   [交易对象](https://openapi.futunn.com/futu-api-doc/trade/base.html)
        
    *   账户
        
    *   资产持仓
        
    *   订单
        
    *   成交
        
    *   [交易定义](https://openapi.futunn.com/futu-api-doc/trade/trade.html)
        
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#7823)
 交易对象
=======================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902)
 创建连接
-----------------------------------------------------------------------

`OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, is_encrypt=None, security_firm=SecurityFirm.FUTUSECURITIES)`

`OpenFutureTradeContext(host='127.0.0.1', port=11111, is_encrypt=None, security_firm=SecurityFirm.FUTUSECURITIES)`

*   **介绍**
    
    根据交易品类，选择账户，并创建对应的交易对象。
    
    | 实例  | 账户  |
    | --- | --- |
    | OpenSecTradeContext | 证券账户<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>股票、ETFs、窝轮牛熊、股票及指数的期权使用此账户 |
    | OpenFutureTradeContext | 期货账户<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货、期货期权使用此账户 |
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | filter\_trdmarket | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 筛选对应交易市场权限的账户<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   此参数仅对 OpenSecTradeContext 适用<br>*   此参数仅用于筛选账户，不影响交易连接 |
    | host | str | OpenD 监听的 IP 地址 |
    | port | int | OpenD 监听的 IP 端口 |
    | is\_encrypt | bool | 是否启用加密<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>默认 None 表示：使用 [enable\_proto\_encrypt](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#319)<br> 的设置 |
    | security\_firm | [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572) | 所属券商 |
    
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, is_encrypt=None, security_firm=SecurityFirm.FUTUSECURITIES)
    trd_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#1360)
 关闭连接
-----------------------------------------------------------------------

`close()`

*   **介绍**
    
    关闭交易对象。默认情况下，Futu API 内部创建的线程会阻止进程退出，只有当所有 Context 都 close 后，进程才能正常退出。但通过 [set\_all\_thread\_daemon](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#4570)
     可以设置所有内部线程为 daemon 线程，这时即使没有调用 Context 的 close，进程也可以正常退出。
    
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111)
    trd_ctx.close()  # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902-2)
 创建连接
-------------------------------------------------------------------------

*   **介绍**
    
    创建交易对象并初始化交易连接
    
*   **参数**
    

    message C2S
    {
    	required int32 clientVer = 1; //客户端版本号，clientVer = "."以前的数 * 100 + "."以后的，举例：1.1版本的 clientVer 为1 * 100 + 1 = 101，2.21版本为2 * 100 + 21 = 221
    	required string clientID = 2; //客户端唯一标识，无生具体生成规则，客户端自己保证唯一性即可
    	optional bool recvNotify = 3; //此连接是否接收市场状态、交易需要重新解锁等等事件通知，true 代表接收，OpenD 就会向此连接推送这些通知，反之 false 代表不接收不推送
    	//如果通信要加密，首先得在 OpenD 和客户端都配置 RSA 密钥，不配置始终不加密
    	//如果配置了 RSA 密钥且指定的加密算法不为 PacketEncAlgo_None 则加密(即便这里不设置，配置了 RSA 密钥，也会采用默认加密方式)，默认采用 FTAES_ECB 算法
    	optional int32 packetEncAlgo = 4; //指定包加密算法，参见 Common.PacketEncAlgo 的枚举定义
    	optional int32 pushProtoFmt = 5; //指定这条连接上的推送协议格式，若不指定则使用 push_proto_type 配置项
    	optional string programmingLanguage = 6; //接口编程语言，用于统计语言偏好
    }
    
    message Request
    {
    	required C2S c2s = 1;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  

*   **返回**

    message S2C
    {
    	required int32 serverVer = 1; //OpenD 的版本号
    	required uint64 loginUserID = 2; //OpenD 登录的用户 ID
    	required uint64 connID = 3; //此连接的连接 ID，连接的唯一标识
    	required string connAESKey = 4; //此连接后续 AES 加密通信的 Key，固定为16字节长字符串
    	required int32 keepAliveInterval = 5; //心跳保活间隔
    	optional string aesCBCiv = 6; //AES 加密通信 CBC 加密模式的 iv，固定为16字节长字符串
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; //返回结果，参见 Common.RetType 的枚举定义
    	optional string retMsg = 2; //返回结果描述
    	optional int32 errCode = 3; //错误码，客户端一般通过 retType 和 RetMsg 来判断结果和详情，errCode 只做日志记录，仅在个别协议失败时对账用
    	
    	optional S2C s2c = 4;
    }
    
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    1001
    

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902-3)
 创建连接
-------------------------------------------------------------------------

`bool InitConnect(String ip, short port, bool isEnableEncrypt)`

*   **介绍**
    
    创建交易对象并初始化交易连接
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ip  | str | penD 监听地址 |
    | port | int | OpenD 监听端口 |
    | bEnableEncrypt | bool | 是否启用加密 |
    
*   **返回**
    
    *   ret：是否启动了执行。不代表连接结果，结果通过 OnInitConnect 回调
*   **Example**
    

    public class Program : FTSPI_Trd, FTSPI_Conn {
        FTAPI_Trd trd = new FTAPI_Trd();
    
        public Program() {
            trd.SetClientInfo("csharp", 1);  //设置客户端信息
            trd.SetConnCallback(this);  //设置连接回调
        }
    
        public void Start() {
            trd.InitConnect("127.0.0.1", (ushort)11111, false);
        }
        
        public void OnInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Trd onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
        }
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public static void Main(String[] args) {
            FTAPI.Init();
            Program trd = new Program();
            trd.Start();
    
            while (true)
                Thread.Sleep(1000 * 600);
        }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  
30  

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6826804060126078029
    

1  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#8436)
 销毁连接
-----------------------------------------------------------------------

`void Close()`

*   **介绍**
    
    销毁连接
    
*   **Example**
    

    FTAPI_Trd trd = new FTAPI_Trd();
    trd.InitConnect("127.0.0.1", (ushort)11111, false);
    trd.Close();
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902-4)
 创建连接
-------------------------------------------------------------------------

`boolean initConnect(String ip, short port, boolean isEnableEncrypt)`

*   **介绍**
    
    创建交易对象并初始化交易连接
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ip  | str | OpenD 监听地址 |
    | port | int | OpenD 监听端口 |
    | bEnableEncrypt | bool | 是否启用加密 |
    
*   **返回**
    
    *   ret：是否启动了执行。不代表连接结果，结果通过 onInitConnect 回调
*   **Example**
    

    public class TrdDemo implements FTSPI_Trd, FTSPI_Conn {
        FTAPI_Conn_Trd trd = new FTAPI_Conn_Trd();
    
        public TrdDemo() {
            trd.setClientInfo("javaclient", 1);  //设置客户端信息
            trd.setConnSpi(this);  //设置连接回调
        }
    
        public void start() throws IOException {
            trd.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Trd onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        public static void main(String[] args) throws IOException {
            FTAPI.init();
            TrdDemo trd = new TrdDemo();
            trd.start();
    
            while (true) {
                try {
                    Thread.sleep(1000 * 600);
                } catch (InterruptedException exc) {
    
                }
            }
        }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  
30  
31  
32  
33  
34  
35  
36  
37  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#8436-2)
 销毁连接
-------------------------------------------------------------------------

`void close()`

*   **介绍**
    
    销毁连接
    
*   **Example**
    

    FTAPI_Conn_Trd trd = new FTAPI_Conn_Trd();
    trd.initConnect("127.0.0.1", (short)11111, false);
    trd.close();
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902-5)
 创建连接
-------------------------------------------------------------------------

`FTAPI_Trd* CreateTrdApi();`  
`bool InitConnect(const char* szIPAddr, Futu::u16_t nPort, bool bEnableEncrypt);`

*   **介绍**
    
    创建交易对象并初始化交易连接
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | szIPAddr | str | OpenD 监听地址 |
    | nPort | int | OpenD 监听端口 |
    | bEnableEncrypt | bool | 是否启用加密 |
    
*   **Example**
    

    FTAPI_Trd *m_pTrdApi = FTAPI::CreateTrdApi();
    m_pTrdApi->InitConnect("127.0.0.1", 11111, false);
    FTAPI::ReleaseTrdApi(m_pTrdApi);
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#8436-3)
 销毁连接
-------------------------------------------------------------------------

`void ReleaseTrdApi(FTAPI_Trd* pTrd);`

*   **介绍**
    
    销毁连接
    
*   **返回**
    
    *   pTrd：连接实例
*   **Example**
    

    FTAPI_Trd *m_pTrdApi = FTAPI::CreateTrdApi();
    m_pTrdApi->InitConnect("127.0.0.1", 11111, false);
    FTAPI::ReleaseTrdApi(m_pTrdApi);
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902-6)
 创建连接
-------------------------------------------------------------------------

`start(ip, port, ssl, key)`

*   **介绍**
    
    创建交易对象并初始化交易连接
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ip  | str | OpenD 监听的 WebSocket 地址 |
    | port | int | OpenD 监听的 WebSocket 端口 |
    | ssl | bool | 是否启用 SSL 加密，参见 [WebSocket 相关](https://openapi.futunn.com/futu-api-doc/qa/other.html#6319) |
    | key | str | 连接的密钥（未传会出现连接超时），可以在 OpenD 的启动参数中配置。可视化 OpenD 在未配置 WebSocket 密钥的情况下会随机指定 |
    
*   **Example**
    

    import ftWebSocket from "@/components/ft-websocket/main.js";
    class Example {
        example() {
            this.websocket = new ftWebSocket();
            this.websocket.start("127.0.0.1", 33333, false, null);
        }
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#1360-2)
 关闭连接
-------------------------------------------------------------------------

`close()`

*   **介绍**
    
    关闭连接
    
*   **Example**
    

    import ftWebSocket from "@/components/ft-websocket/main.js";
    class Example {
        example() {
            this.websocket = new ftWebSocket();
            this.websocket.start("127.0.0.1", 33333, false, null);
            this.websocket.close();
        }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902-7)
 创建连接
-------------------------------------------------------------------------

`OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, is_encrypt=None, security_firm=SecurityFirm.FUTUSECURITIES)`

`OpenFutureTradeContext(host='127.0.0.1', port=11111, is_encrypt=None, security_firm=SecurityFirm.FUTUSECURITIES)`

*   **介绍**
    
    根据交易品类，选择账户，并创建对应的交易对象。
    
    | 实例  | 账户  |
    | --- | --- |
    | OpenSecTradeContext | 证券账户<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>股票、ETFs、窝轮牛熊、股票及指数的期权使用此账户 |
    | OpenFutureTradeContext | 期货账户<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>期货、期货期权使用此账户 |
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | filter\_trdmarket | [TrdMarket](https://openapi.futunn.com/futu-api-doc/trade/trade.html#719) | 筛选对应交易市场权限的账户<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   此参数仅对 OpenSecTradeContext 适用<br>*   此参数仅用于筛选账户，不影响交易连接 |
    | host | str | OpenD 监听的 IP 地址 |
    | port | int | OpenD 监听的 IP 端口 |
    | is\_encrypt | bool | 是否启用加密<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>默认 None 表示：使用 [enable\_proto\_encrypt](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#319)<br> 的设置 |
    | security\_firm | [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572) | 所属券商 |
    
*   **Example**
    

    from moomoo import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, is_encrypt=None, security_firm=SecurityFirm.FUTUINC)
    trd_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#1360-3)
 关闭连接
-------------------------------------------------------------------------

`close()`

*   **介绍**
    
    关闭交易对象。默认情况下，moomoo API 内部创建的线程会阻止进程退出，只有当所有 Context 都 close 后，进程才能正常退出。但通过 [set\_all\_thread\_daemon](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#4570)
     可以设置所有内部线程为 daemon 线程，这时即使没有调用 Context 的 close，进程也可以正常退出。
    
*   **Example**
    

    from moomoo import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, is_encrypt=None, security_firm=SecurityFirm.FUTUINC)
    trd_ctx.close()  # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902-8)
 创建连接
-------------------------------------------------------------------------

*   **介绍**
    
    创建交易对象并初始化交易连接
    
*   **参数**
    

    message C2S
    {
    	required int32 clientVer = 1; //客户端版本号，clientVer = "."以前的数 * 100 + "."以后的，举例：1.1版本的 clientVer 为1 * 100 + 1 = 101，2.21版本为2 * 100 + 21 = 221
    	required string clientID = 2; //客户端唯一标识，无生具体生成规则，客户端自己保证唯一性即可
    	optional bool recvNotify = 3; //此连接是否接收市场状态、交易需要重新解锁等等事件通知，true 代表接收，OpenD 就会向此连接推送这些通知，反之 false 代表不接收不推送
    	//如果通信要加密，首先得在 OpenD 和客户端都配置 RSA 密钥，不配置始终不加密
    	//如果配置了 RSA 密钥且指定的加密算法不为 PacketEncAlgo_None 则加密(即便这里不设置，配置了 RSA 密钥，也会采用默认加密方式)，默认采用 FTAES_ECB 算法
    	optional int32 packetEncAlgo = 4; //指定包加密算法，参见 Common.PacketEncAlgo 的枚举定义
    	optional int32 pushProtoFmt = 5; //指定这条连接上的推送协议格式，若不指定则使用 push_proto_type 配置项
    	optional string programmingLanguage = 6; //接口编程语言，用于统计语言偏好
    }
    
    message Request
    {
    	required C2S c2s = 1;
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  

*   **返回**

    message S2C
    {
    	required int32 serverVer = 1; //OpenD 的版本号
    	required uint64 loginUserID = 2; //OpenD 登录的牛牛用户 ID
    	required uint64 connID = 3; //此连接的连接 ID，连接的唯一标识
    	required string connAESKey = 4; //此连接后续 AES 加密通信的 Key，固定为16字节长字符串
    	required int32 keepAliveInterval = 5; //心跳保活间隔
    	optional string aesCBCiv = 6; //AES 加密通信 CBC 加密模式的 iv，固定为16字节长字符串
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; //返回结果，参见 Common.RetType 的枚举定义
    	optional string retMsg = 2; //返回结果描述
    	optional int32 errCode = 3; //错误码，客户端一般通过 retType 和 RetMsg 来判断结果和详情，errCode 只做日志记录，仅在个别协议失败时对账用
    	
    	optional S2C s2c = 4;
    }
    
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    1001
    

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902-9)
 创建连接
-------------------------------------------------------------------------

`bool InitConnect(String ip, short port, bool isEnableEncrypt)`

*   **介绍**
    
    创建交易对象并初始化交易连接
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ip  | str | OpenD 监听地址 |
    | port | int | OpenD 监听端口 |
    | bEnableEncrypt | bool | 是否启用加密 |
    
*   **返回**
    
    *   ret：是否启动了执行。不代表连接结果，结果通过 OnInitConnect 回调
*   **Example**
    

    public class Program : MMSPI_Trd, MMSPI_Conn {
        MMAPI_Trd trd = new MMAPI_Trd();
    
        public Program() {
            trd.SetClientInfo("csharp", 1);  //设置客户端信息
            trd.SetConnCallback(this);  //设置连接回调
        }
    
        public void Start() {
            trd.InitConnect("127.0.0.1", (ushort)11111, false);
        }
        
        public void OnInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Trd onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
        }
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public static void Main(String[] args) {
            MMAPI.Init();
            Program trd = new Program();
            trd.Start();
    
            while (true)
                Thread.Sleep(1000 * 600);
        }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  
30  

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6826804060126078029
    

1  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#8436-4)
 销毁连接
-------------------------------------------------------------------------

`void Close()`

*   **介绍**
    
    销毁连接
    
*   **Example**
    

    MMAPI_Trd trd = new MMAPI_Trd();
    trd.InitConnect("127.0.0.1", (ushort)11111, false);
    trd.Close();
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902-10)
 创建连接
--------------------------------------------------------------------------

`boolean initConnect(String ip, short port, boolean isEnableEncrypt)`

*   **介绍**
    
    创建交易对象并初始化交易连接
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ip  | str | OpenD 监听地址 |
    | port | int | OpenD 监听端口 |
    | bEnableEncrypt | bool | 是否启用加密 |
    
*   **返回**
    
    *   ret：是否启动了执行。不代表连接结果，结果通过 onInitConnect 回调
*   **Example**
    

    public class TrdDemo implements MMSPI_Trd, MMSPI_Conn {
        MMAPI_Conn_Trd trd = new MMAPI_Conn_Trd();
    
        public TrdDemo() {
            trd.setClientInfo("javaclient", 1);  //设置客户端信息
            trd.setConnSpi(this);  //设置连接回调
        }
    
        public void start() throws IOException {
            trd.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Trd onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        public static void main(String[] args) throws IOException {
            MMAPI.init();
            TrdDemo trd = new TrdDemo();
            trd.start();
    
            while (true) {
                try {
                    Thread.sleep(1000 * 600);
                } catch (InterruptedException exc) {
    
                }
            }
        }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  
30  
31  
32  
33  
34  
35  
36  
37  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#8436-5)
 销毁连接
-------------------------------------------------------------------------

`void close()`

*   **介绍**
    
    销毁连接
    
*   **Example**
    

    MMAPI_Conn_Trd trd = new MMAPI_Conn_Trd();
    trd.initConnect("127.0.0.1", (short)11111, false);
    trd.close();
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902-11)
 创建连接
--------------------------------------------------------------------------

`MMAPI_Trd* CreateTrdApi();`  
`bool InitConnect(const char* szIPAddr, moomoo::u16_t nPort, bool bEnableEncrypt);`

*   **介绍**
    
    创建交易对象并初始化交易连接
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | szIPAddr | str | OpenD 监听地址 |
    | nPort | int | OpenD 监听端口 |
    | bEnableEncrypt | bool | 是否启用加密 |
    
*   **Example**
    

    MMAPI_Trd *m_pTrdApi = MMAPI::CreateTrdApi();
    m_pTrdApi->InitConnect("127.0.0.1", 11111, false);
    MMAPI::ReleaseTrdApi(m_pTrdApi);
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#8436-6)
 销毁连接
-------------------------------------------------------------------------

`void ReleaseTrdApi(MMAPI_Trd* pTrd);`

*   **介绍**
    
    销毁连接
    
*   **返回**
    
    *   pTrd：连接实例
*   **Example**
    

    MMAPI_Trd *m_pTrdApi = MMAPI::CreateTrdApi();
    m_pTrdApi->InitConnect("127.0.0.1", 11111, false);
    MMAPI::ReleaseTrdApi(m_pTrdApi);
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#7902-12)
 创建连接
--------------------------------------------------------------------------

`start(ip, port, ssl, key)`

*   **介绍**
    
    创建交易对象并初始化交易连接
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ip  | str | OpenD 监听的 WebSocket 地址 |
    | port | int | OpenD 监听的 WebSocket 端口 |
    | ssl | bool | 是否启用 SSL 加密，参见 [WebSocket 相关](https://openapi.futunn.com/futu-api-doc/qa/other.html#6319) |
    | key | str | 连接的密钥（未传会出现连接超时），可以在 OpenD 的启动参数中配置。可视化 OpenD 在未配置 WebSocket 密钥的情况下会随机指定 |
    
*   **Example**
    

    import mmWebSocket from "@/components/mm-websocket/main.js";
    class Example {
        example() {
            this.websocket = new mmWebSocket();
            this.websocket.start("127.0.0.1", 33333, false, null);
        }
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](https://openapi.futunn.com/futu-api-doc/trade/base.html#1360-4)
 关闭连接
-------------------------------------------------------------------------

`close()`

*   **介绍**
    
    关闭连接
    
*   **Example**
    

    import mmWebSocket from "@/components/mm-websocket/main.js";
    class Example {
        example() {
            this.websocket = new mmWebSocket();
            this.websocket.start("127.0.0.1", 33333, false, null);
            this.websocket.close();
        }
    }
    

1  
2  
3  
4  
5  
6  
7  
8  

← [交易接口总览](https://openapi.futunn.com/futu-api-doc/trade/overview.html) [获取交易业务账户列表](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html)
 →

[交易对象](https://openapi.futunn.com/futu-api-doc/trade/base.html)