# 行情对象 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quote/base.html

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#3219)
 行情对象
=======================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#7902)
 创建连接
-----------------------------------------------------------------------

`OpenQuoteContext(host='127.0.0.1', port=11111, is_encrypt=None)`

*   **介绍**
    
    创建并初始化行情连接
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | host | str | OpenD 监听的 IP 地址 |
    | port | int | OpenD 监听的端口 |
    | is\_encrypt | bool | 是否启用加密<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   默认为 None，表示使用 [enable\_proto\_encrypt](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#319)<br>     的设置<br>*   True：强制加密  <br>    False：强制不加密 |
    
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111, is_encrypt=False)
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#1360)
 关闭连接
-----------------------------------------------------------------------

`close()`

*   **介绍**
    
    关闭行情接口类对象。默认情况下，Futu API 内部创建的线程会阻止进程退出，只有当所有 Context 都 close 后，进程才能正常退出。但通过 [set\_all\_thread\_daemon](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#4570)
     可以设置所有内部线程为 daemon 线程，这时即使没有调用 Context 的 close，进程也可以正常退出。
    
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#9943)
 启动
---------------------------------------------------------------------

`start()`

*   **介绍**
    
    启动异步接收推送数据
    

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#9952)
 停止
---------------------------------------------------------------------

`stop()`

*   **介绍**
    
    停止异步接收推送数据
    

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#7613)
 InitConnect.proto
------------------------------------------------------------------------------------

*   **介绍**
    
    初始化连接协议
    
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    1001
    

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#7902-2)
 创建连接
-------------------------------------------------------------------------

`bool InitConnect(String ip, short port, bool isEnableEncrypt)`

*   **介绍**
    
    初始化连接，连接并初始化
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ip  | str | OpenD 监听地址 |
    | port | int | OpenD 监听端口 |
    | bEnableEncrypt | bool | 是否启用加密 |
    
*   **返回**
    
    *   ret：是否启动了执行，不代表连接结果，结果通过 OnInitConnect 回调
*   **Example**
    

    public class Program : FTSPI_Qot, FTSPI_Conn {
        FTAPI_Qot qot = new FTAPI_Qot();
    
        public Program() {
            qot.SetClientInfo("csharp", 1);  //设置客户端信息
            qot.SetConnCallback(this);  //设置连接回调
        }
    
        public void Start() {
            qot.InitConnect("127.0.0.1", (ushort)11111, false);
        }
    
        
        public void OnInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Qot onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public static void Main(String[] args) {
            FTAPI.Init();
            Program qot = new Program();
            qot.Start();
    
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
31  
32  

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825318240991318111
    

1  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#8436)
 销毁连接
-----------------------------------------------------------------------

`void Close()`

*   **介绍**
    
    销毁连接
    
*   **Example**
    

    FTAPI_Qot qot = new FTAPI_Qot();
    qot.InitConnect("127.0.0.1", (ushort)11111, false);
    qot.Close();
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#7902-3)
 创建连接
-------------------------------------------------------------------------

`boolean initConnect(String ip, short port, boolean isEnableEncrypt)`

*   **介绍**
    
    初始化连接，连接并初始化
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ip  | str | OpenD 监听地址 |
    | port | int | OpenD 监听端口 |
    | bEnableEncrypt | bool | 是否启用加密 |
    
*   **返回**
    
    *   ret：是否启动了执行，不代表连接结果，结果通过 onInitConnect 回调
*   **Example**
    

    public class QotDemo implements FTSPI_Qot, FTSPI_Conn {
        FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();
    
        public QotDemo() {
            qot.setClientInfo("javaclient", 1);  //设置客户端信息
            qot.setConnSpi(this);  //设置连接回调
        }
    
        public void start() throws IOException {
            qot.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Qot onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        public static void main(String[] args) throws IOException {
            FTAPI.init();
            QotDemo qot = new QotDemo();
            qot.start();
    
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

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#8436-2)
 销毁连接
-------------------------------------------------------------------------

`void close()`

*   **介绍**
    
    销毁连接
    
*   **Example**
    

    FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();
    qot.initConnect("127.0.0.1", (short)11111, false);
    qot.close();
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#7902-4)
 创建连接
-------------------------------------------------------------------------

`FTAPI_Qot* CreateQotApi();`  
`bool InitConnect(const char* szIPAddr, Futu::u16_t nPort, bool bEnableEncrypt);`

*   **介绍**
    
    创建，初始化连接
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | szIPAddr | str | OpenD 监听地址 |
    | nPort | int | OpenD 监听端口 |
    | bEnableEncrypt | bool | 是否启用加密 |
    
*   **Example**
    

    FTAPI_Qot *m_pQotApi = FTAPI::CreateQotApi();
    m_pQotApi->InitConnect("127.0.0.1", 11111, false);
    FTAPI::ReleaseQotApi(m_pQotApi);
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#8436-3)
 销毁连接
-------------------------------------------------------------------------

`void ReleaseTrdApi(FTAPI_Qot* pTrd);`

*   **介绍**
    
    销毁连接
    
*   **返回**
    
    *   pQot：连接实例
*   **Example**
    

    FTAPI_Qot *m_pQotApi = FTAPI::CreateQotApi();
    m_pQotApi->InitConnect("127.0.0.1", 11111, false);
    FTAPI::ReleaseQotApi(m_pQotApi);
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#7902-5)
 创建连接
-------------------------------------------------------------------------

`start(ip, port, ssl, key)`

*   **介绍**
    
    初始化连接，连接并初始化
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ip  | str | OpenD 监听的 WebSocket 地址 |
    | port | int | OpenD 监听的 WebSocket 端口 |
    | ssl | bool | 是否启用 SSL 加密，参见 [WebSocket 相关](https://openapi.futunn.com/futu-api-doc/qa/other.html#6319) |
    | key | str | 连接的密钥，否则会连接超时，密钥在在 OpenD 可配置，可视化版本在不指定的情况下会随机指定 |
    
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

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#1360-2)
 关闭连接
-------------------------------------------------------------------------

`stop()`

*   **介绍**
    
    关闭连接
    
*   **Example**
    

    import ftWebSocket from "@/components/ft-websocket/main.js";
    class Example {
        example() {
            this.websocket = new ftWebSocket();
            this.websocket.start("127.0.0.1", 33333, false, null);
            this.websocket.stop();
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

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#7902-6)
 创建连接
-------------------------------------------------------------------------

`OpenQuoteContext(host='127.0.0.1', port=11111, is_encrypt=None)`

*   **介绍**
    
    创建并初始化行情连接
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | host | str | OpenD 监听的 IP 地址 |
    | port | int | OpenD 监听的端口 |
    | is\_encrypt | bool | 是否启用加密<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>*   默认为 None，表示使用 [enable\_proto\_encrypt](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#319)<br>     的设置<br>*   True：强制加密  <br>    False：强制不加密 |
    
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111, is_encrypt=False)
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#1360-3)
 关闭连接
-------------------------------------------------------------------------

`close()`

*   **介绍**
    
    关闭行情接口类对象。默认情况下，moomoo API 内部创建的线程会阻止进程退出，只有当所有 Context 都 close 后，进程才能正常退出。但通过 [set\_all\_thread\_daemon](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#4570)
     可以设置所有内部线程为 daemon 线程，这时即使没有调用 Context 的 close，进程也可以正常退出。
    
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#9943-2)
 启动
-----------------------------------------------------------------------

`start()`

*   **介绍**
    
    启动异步接收推送数据
    

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#9952-2)
 停止
-----------------------------------------------------------------------

`stop()`

*   **介绍**
    
    停止异步接收推送数据
    

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#7613-2)
 InitConnect.proto
--------------------------------------------------------------------------------------

*   **介绍**
    
    初始化连接协议
    
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    1001
    

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#7902-7)
 创建连接
-------------------------------------------------------------------------

`bool InitConnect(String ip, short port, bool isEnableEncrypt)`

*   **介绍**
    
    初始化连接，连接并初始化
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ip  | str | OpenD 监听地址 |
    | port | int | OpenD 监听端口 |
    | bEnableEncrypt | bool | 是否启用加密 |
    
*   **返回**
    
    *   ret：是否启动了执行，不代表连接结果，结果通过 OnInitConnect 回调
*   **Example**
    

    public class Program : MMSPI_Qot, MMSPI_Conn {
        MMAPI_Qot qot = new MMAPI_Qot();
    
        public Program() {
            qot.SetClientInfo("csharp", 1);  //设置客户端信息
            qot.SetConnCallback(this);  //设置连接回调
        }
    
        public void Start() {
            qot.InitConnect("127.0.0.1", (ushort)11111, false);
        }
    
        
        public void OnInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Qot onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public static void Main(String[] args) {
            MMAPI.Init();
            Program qot = new Program();
            qot.Start();
    
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
31  
32  

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825318240991318111
    

1  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#8436-4)
 销毁连接
-------------------------------------------------------------------------

`void Close()`

*   **介绍**
    
    销毁连接
    
*   **Example**
    

    MMAPI_Qot qot = new MMAPI_Qot();
    qot.InitConnect("127.0.0.1", (ushort)11111, false);
    qot.Close();
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#7902-8)
 创建连接
-------------------------------------------------------------------------

`boolean initConnect(String ip, short port, boolean isEnableEncrypt)`

*   **介绍**
    
    初始化连接，连接并初始化
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ip  | str | OpenD 监听地址 |
    | port | int | OpenD 监听端口 |
    | bEnableEncrypt | bool | 是否启用加密 |
    
*   **返回**
    
    *   ret：是否启动了执行，不代表连接结果，结果通过 onInitConnect 回调
*   **Example**
    

    public class QotDemo implements MMSPI_Qot, MMSPI_Conn {
        MMAPI_Conn_Qot qot = new FTAPI_Conn_Qot();
    
        public QotDemo() {
            qot.setClientInfo("javaclient", 1);  //设置客户端信息
            qot.setConnSpi(this);  //设置连接回调
        }
    
        public void start() throws IOException {
            qot.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Qot onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        public static void main(String[] args) throws IOException {
            MMAPI.init();
            QotDemo qot = new QotDemo();
            qot.start();
    
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

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#8436-5)
 销毁连接
-------------------------------------------------------------------------

`void close()`

*   **介绍**
    
    销毁连接
    
*   **Example**
    

    MMAPI_Conn_Qot qot = new MMAPI_Conn_Qot();
    qot.initConnect("127.0.0.1", (short)11111, false);
    qot.close();
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#7902-9)
 创建连接
-------------------------------------------------------------------------

`MMAPI_Qot* CreateQotApi();`  
`bool InitConnect(const char* szIPAddr, moomoo::u16_t nPort, bool bEnableEncrypt);`

*   **介绍**
    
    创建，初始化连接
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | szIPAddr | str | OpenD 监听地址 |
    | nPort | int | OpenD 监听端口 |
    | bEnableEncrypt | bool | 是否启用加密 |
    
*   **Example**
    

    MMAPI_Qot *m_pQotApi = MMAPI::CreateQotApi();
    m_pQotApi->InitConnect("127.0.0.1", 11111, false);
    MMAPI::ReleaseQotApi(m_pQotApi);
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#8436-6)
 销毁连接
-------------------------------------------------------------------------

`void ReleaseTrdApi(MMAPI_Qot* pTrd);`

*   **介绍**
    
    销毁连接
    
*   **返回**
    
    *   pQot：连接实例
*   **Example**
    

    MMAPI_Qot *m_pQotApi = MMAPI::CreateQotApi();
    m_pQotApi->InitConnect("127.0.0.1", 11111, false);
    MMAPI::ReleaseQotApi(m_pQotApi);
    

1  
2  
3  

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#7902-10)
 创建连接
--------------------------------------------------------------------------

`start(ip, port, ssl, key)`

*   **介绍**
    
    初始化连接，连接并初始化
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ip  | str | OpenD 监听的 WebSocket 地址 |
    | port | int | OpenD 监听的 WebSocket 端口 |
    | ssl | bool | 是否启用 SSL 加密，参见 [WebSocket 相关](https://openapi.futunn.com/futu-api-doc/qa/other.html#6319) |
    | key | str | 连接的密钥，否则会连接超时，密钥在在 OpenD 可配置，可视化版本在不指定的情况下会随机指定 |
    
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

[#](https://openapi.futunn.com/futu-api-doc/quote/base.html#1360-4)
 关闭连接
-------------------------------------------------------------------------

`stop()`

*   **介绍**
    
    关闭连接
    
*   **Example**
    

    import mmWebSocket from "@/components/mm-websocket/main.js";
    class Example {
        example() {
            this.websocket = new mmWebSocket();
            this.websocket.start("127.0.0.1", 33333, false, null);
            this.websocket.stop();
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

← [行情接口总览](https://openapi.futunn.com/futu-api-doc/quote/overview.html) [订阅反订阅](https://openapi.futunn.com/futu-api-doc/quote/sub.html)
 →