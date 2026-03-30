[#](./ftapi_init.md#3032)
 基础功能
=======================================================================

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   裸协议

| 协议 ID | Protobuf 文件 | 说明  |
| --- | --- | --- |
| 1001 | [InitConnect](./quote_base.md) | 初始化连接 |
| 1002 | [GetGlobalState](./quote_get-global-state.md) | 获取全局状态 |
| 1003 | [Notify](./ftapi_init.md#6884) | 事件通知推送 |
| 1004 | [KeepAlive](./ftapi_protocol.md#2603) | 保活心跳 |

[#](./ftapi_init.md#7518)
 设置接口信息
-------------------------------------------------------------------------

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   裸协议

`set_client_info(client_id, client_ver)`

*   **介绍**
    
    设置调用接口信息, 非必调接口
    
*   **参数**
    
    *   client\_id: client 的标识
    *   client\_ver: client 的版本号

*   **Example**

    from futu import *
    SysConfig.set_client_info("MyFutuAPI", 0)
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    quote_ctx.close()
    

1  
2  
3  
4  

*   **Example**

    from moomoo import *
    SysConfig.set_client_info("MymoomooAPI", 0)
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    quote_ctx.close()
    

1  
2  
3  
4  

`void SetClientInfo(String clientID, int clientVer);`

*   **介绍**
    
    设置调用接口信息, 非必调接口
    
*   **参数**
    
    *   clientID: client 的标识
    *   clientVer: client 的版本号

*   **Example**

    FTAPI_Qot qot = new FTAPI_Qot();
    qot.SetClientInfo("FTAPI4NET_Sample", 1);  //设置客户端信息
    

1  
2  

*   **Example**

    MMAPI_Qot qot = new MMAPI_Qot();
    qot.SetClientInfo("MMAPI4NET_Sample", 1);  //设置客户端信息
    

1  
2  

`void setClientInfo(String clientID, int clientVer);`

*   **介绍**
    
    设置调用接口信息, 非必调接口
    
*   **参数**
    
    *   clientID: client 的标识
    *   clientVer: client 的版本号

*   **Example**

    FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();
    qot.setClientInfo("javaclient", 1);  //设置客户端信息
    

1  
2  

    MMAPI_Conn_Qot qot = new MMAPI_Conn_Qot();
    qot.setClientInfo("javaclient", 1);  //设置客户端信息
    

1  
2  

`void SetClientInfo(const char* szClientID, Futu::i32_t nClientVer);`

*   **介绍**
    
    设置调用接口信息, 非必调接口
    
*   **参数**
    
    *   szClientID: client 的标识
    *   nClientVer: client 的版本号
*   **Example**
    

    FTAPI_Qot *m_pQotApi = FTAPI::CreateQotApi();
    m_pQotApi->SetClientInfo('cpp', 1);
    FTAPI::ReleaseQotApi(m_pQotApi);
    

1  
2  
3  

`void SetClientInfo(const char* szClientID, moomoo::i32_t nClientVer);`

*   **介绍**
    
    设置调用接口信息, 非必调接口
    
*   **参数**
    
    *   szClientID: client 的标识
    *   nClientVer: client 的版本号

    MMAPI_Qot *m_pQotApi = MMAPI::CreateQotApi();
    m_pQotApi->SetClientInfo('cpp', 1);
    MMAPI::ReleaseQotApi(m_pQotApi);
    

1  
2  
3  

InitConnect.proto

    message C2S
    {
        required int32 clientVer = 1; //客户端版本号，clientVer = "."以前的数 * 100 + "."以后的，举例：1.1版本的 clientVer 为1 * 100 + 1 = 101，2.21版本为2 * 100 + 21 = 221
        required string clientID = 2; //客户端唯一标识，无生具体生成规则，客户端自己保证唯一性即可
        optional bool recvNotify = 3; //此连接是否接收市场状态、交易需要重新解锁等等事件通知，true 代表接收，OpenD 就会向此连接推送这些通知，反之 false 代表不接收不推送
        //如果通信要加密，首先得在 OpenD 和客户端都配置 RSA 密钥，不配置始终不加密
        //如果配置了 RSA 密钥且指定的加密算法不为 PacketEncAlgo_None 则加密(即便这里不设置，配置了 RSA 密钥，也会采用默认加密方式)，默认采用 FTAES_ECB 算法
        optional int32 packetEncAlgo = 4; //指定包加密算法，参见 Common.PacketEncAlgo 的枚举定义
        optional int32 pushProtoFmt = 5; //指定这条连接上的推送协议格式，若不指定则使用 push_proto_type 配置项
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

    message C2S
    {
        required int32 clientVer = 1; //客户端版本号，clientVer = "."以前的数 * 100 + "."以后的，举例：1.1版本的 clientVer 为1 * 100 + 1 = 101，2.21版本为2 * 100 + 21 = 221
        required string clientID = 2; //客户端唯一标识，无生具体生成规则，客户端自己保证唯一性即可
        optional bool recvNotify = 3; //此连接是否接收市场状态、交易需要重新解锁等等事件通知，true 代表接收，OpenD 就会向此连接推送这些通知，反之 false 代表不接收不推送
        //如果通信要加密，首先得在 OpenD 和客户端都配置 RSA 密钥，不配置始终不加密
        //如果配置了 RSA 密钥且指定的加密算法不为 PacketEncAlgo_None 则加密(即便这里不设置，配置了 RSA 密钥，也会采用默认加密方式)，默认采用 FTAES_ECB 算法
        optional int32 packetEncAlgo = 4; //指定包加密算法，参见 Common.PacketEncAlgo 的枚举定义
        optional int32 pushProtoFmt = 5; //指定这条连接上的推送协议格式，若不指定则使用 push_proto_type 配置项
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

*   **介绍**
    
    在初始化连接协议中 clientVer、clientID 字段设置该信息。
    

[#](./ftapi_init.md#1515)
 设置协议格式
-------------------------------------------------------------------------

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   裸协议

`set_proto_fmt(proto_fmt)`

*   **介绍**
    
    设置通讯协议 body 格式, 目前支持 Protobuf|Json 两种格式，默认 ProtoBuf, 非必调接口
    
*   **参数**
    
    *   proto\_fmt: 协议格式，参见[ProtoFMT](./ftapi_common.md#1222)
        

    from futu import *
    SysConfig.set_proto_fmt(ProtoFMT.Protobuf)
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    quote_ctx.close()
    

1  
2  
3  
4  

    from moomoo import *
    SysConfig.set_proto_fmt(ProtoFMT.Protobuf)
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    quote_ctx.close()
    

1  
2  
3  
4  

*   **Example**

InitConnect.proto

    message C2S
    {
        required int32 clientVer = 1; //客户端版本号，clientVer = "."以前的数 * 100 + "."以后的，举例：1.1版本的 clientVer 为1 * 100 + 1 = 101，2.21版本为2 * 100 + 21 = 221
        required string clientID = 2; //客户端唯一标识，无生具体生成规则，客户端自己保证唯一性即可
        optional bool recvNotify = 3; //此连接是否接收市场状态、交易需要重新解锁等等事件通知，true 代表接收，OpenD 就会向此连接推送这些通知，反之 false 代表不接收不推送
        //如果通信要加密，首先得在 OpenD 和客户端都配置 RSA 密钥，不配置始终不加密
        //如果配置了 RSA 密钥且指定的加密算法不为 PacketEncAlgo_None 则加密(即便这里不设置，配置了 RSA 密钥，也会采用默认加密方式)，默认采用 FTAES_ECB 算法
        optional int32 packetEncAlgo = 4; //指定包加密算法，参见 Common.PacketEncAlgo 的枚举定义
        optional int32 pushProtoFmt = 5; //指定这条连接上的推送协议格式，若不指定则使用 push_proto_type 配置项
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

InitConnect.proto

    message C2S
    {
        required int32 clientVer = 1; //客户端版本号，clientVer = "."以前的数 * 100 + "."以后的，举例：1.1版本的 clientVer 为1 * 100 + 1 = 101，2.21版本为2 * 100 + 21 = 221
        required string clientID = 2; //客户端唯一标识，无生具体生成规则，客户端自己保证唯一性即可
        optional bool recvNotify = 3; //此连接是否接收市场状态、交易需要重新解锁等等事件通知，true 代表接收，OpenD 就会向此连接推送这些通知，反之 false 代表不接收不推送
        //如果通信要加密，首先得在 OpenD 和客户端都配置 RSA 密钥，不配置始终不加密
        //如果配置了 RSA 密钥且指定的加密算法不为 PacketEncAlgo_None 则加密(即便这里不设置，配置了 RSA 密钥，也会采用默认加密方式)，默认采用 FTAES_ECB 算法
        optional int32 packetEncAlgo = 4; //指定包加密算法，参见 Common.PacketEncAlgo 的枚举定义
        optional int32 pushProtoFmt = 5; //指定这条连接上的推送协议格式，若不指定则使用 push_proto_type 配置项
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

*   **介绍**
    
    在初始化连接协议中 pushProtoFmt 字段指定该连接上推送数据格式。  
    请求数据格式参见[协议头](./ftapi_protocol.md#8205)
    中的 nProtoFmtType 字段。
    

[#](./ftapi_init.md#319)
 对所有连接设置协议加密
-----------------------------------------------------------------------------

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   裸协议

`enable_proto_encrypt(is_encrypt)`

*   **介绍**
    
    对所有连接的请求和返回内容加密。如需了解协议加密流程，详见 [这里](./qa_other.md#4601)
    。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | is\_encrypt | bool | 是否启用加密 |
    

*   **Example**
    
        from futu import *
        SysConfig.enable_proto_encrypt(is_encrypt = True)
        SysConfig.set_init_rsa_file("conn_key.txt")   # rsa 私钥文件路径
        quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
        quote_ctx.close()
        
    
    1  
    2  
    3  
    4  
    5  
    

*   **Example**
    
        from moomoo import *
        SysConfig.enable_proto_encrypt(is_encrypt = True)
        SysConfig.set_init_rsa_file("conn_key.txt")   # rsa 私钥文件路径
        quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
        quote_ctx.close()
        
    
    1  
    2  
    3  
    4  
    5  
    

`start(ip, port, ssl, key)`

*   **介绍**
    
    初始化连接，连接并初始化
    
*   **参数**
    
    *   ip: OpenD 监听的 WebSocket 地址
    *   port: OpenD 监听的 WebSocket 端口
    *   ssl: 是否启用 SSL 加密，参见 [WebSocket 相关](./qa_other.md#6319)
        
    *   key：连接的密钥，否则会连接超时，密钥在在 OpenD 可配置，UI 版本在不指定的情况下会随机指定
*   **Example**
    

    import ftWebSocket from "@/components/ft-websocket/main.js";
    class Example {
        example() {
            this.websocket = new ftWebSocket();
            this.websocket.start("127.0.0.1", 33333, true, '123456');
        }
    }
    

1  
2  
3  
4  
5  
6  
7  

*   **介绍**
    
    初始化连接，连接并初始化
    
*   **参数**
    
    *   ip: OpenD 监听的 WebSocket 地址
    *   port: OpenD 监听的 WebSocket 端口
    *   ssl: 是否启用 SSL 加密，参见 [WebSocket 相关](./qa_other.md#6319)
        
    *   key：连接的密钥，否则会连接超时，密钥在在 OpenD 可配置，UI 版本在不指定的情况下会随机指定

    import mmWebSocket from "@/components/mm-websocket/main.js";
    class Example {
        example() {
            this.websocket = new mmWebSocket();
            this.websocket.start("127.0.0.1", 33333, true, '123456');
        }
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./ftapi_init.md#5641)
 设置私钥路径
-------------------------------------------------------------------------

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   裸协议

`set_init_rsa_file(file)`

*   **介绍**
    
    设置 RSA 私钥文件路径。如需了解协议加密流程，详见 [这里](./qa_other.md#4601)
    。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | file | str | 私钥文件路径 |
    
*   **Example**
    

    from futu import *
    SysConfig.enable_proto_encrypt(is_encrypt = True)
    SysConfig.set_init_rsa_file("conn_key.txt")   # rsa 私钥文件路径
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    quote_ctx.close()
    

1  
2  
3  
4  
5  

    from moomoo import *
    SysConfig.enable_proto_encrypt(is_encrypt = True)
    SysConfig.set_init_rsa_file("conn_key.txt")   # rsa 私钥文件路径
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    quote_ctx.close()
    

1  
2  
3  
4  
5  

`void SetRSAPrivateKey(string key)`

*   **介绍**
    
    设置私钥
    
*   **参数**
    
    *   key: 私钥内容

*   **Example**

    public class Program : FTSPI_Qot, FTSPI_Conn {
        FTAPI_Qot qot = new FTAPI_Qot();
    
        public Program() {
            qot.SetClientInfo("csharp", 1);  //设置客户端信息
            qot.SetConnCallback(this);  //设置连接回调
            qot.SetQotCallback(this);   //设置交易回调
            qot.SetRSAPrivateKey(System.IO.File.ReadAllText(@"d:\rsa1024", Encoding.UTF8)); //设置 rsa 密钥
        }
    
        public void Start() {
            qot.InitConnect("127.0.0.1", (ushort)11111, true); //连接加密
        }
    
        
        public void OnInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Qot onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
            if (errCode != 0)
                return;
    
            QotCommon.Security sec = QotCommon.Security.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetCode("00700")
                    .Build();
            QotSub.C2S c2s = QotSub.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                    .AddSubTypeList((int)QotCommon.SubType.SubType_Basic)
                    .SetIsSubOrUnSub(true)
                    .Build();
            QotSub.Request req = QotSub.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.Sub(req);
            Console.Write("Send QotSub: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_Sub(FTAPI_Conn client, uint nSerialNo, QotSub.Response rsp) {
            Console.Write("Reply: QotSub: {0}  {1}\n", nSerialNo, rsp.ToString());
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
33  
34  
35  
36  
37  
38  
39  
40  
41  
42  
43  
44  
45  
46  
47  
48  
49  
50  
51  
52  
53  
54  
55  
56  

    public class Program : MMSPI_Qot, MMSPI_Conn {
        MMAPI_Qot qot = new MMAPI_Qot();
    
        public Program() {
            qot.SetClientInfo("csharp", 1);  //设置客户端信息
            qot.SetConnCallback(this);  //设置连接回调
            qot.SetQotCallback(this);   //设置交易回调
            qot.SetRSAPrivateKey(System.IO.File.ReadAllText(@"d:\rsa1024", Encoding.UTF8)); //设置 rsa 密钥
        }
    
        public void Start() {
            qot.InitConnect("127.0.0.1", (ushort)11111, true); //连接加密
        }
    
        
        public void OnInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Qot onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
            if (errCode != 0)
                return;
    
            QotCommon.Security sec = QotCommon.Security.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetCode("00700")
                    .Build();
            QotSub.C2S c2s = QotSub.C2S.CreateBuilder()
                    .AddSecurityList(sec)
                    .AddSubTypeList((int)QotCommon.SubType.SubType_Basic)
                    .SetIsSubOrUnSub(true)
                    .Build();
            QotSub.Request req = QotSub.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.Sub(req);
            Console.Write("Send QotSub: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        
        public void OnReply_Sub(MMAPI_Conn client, uint nSerialNo, QotSub.Response rsp) {
            Console.Write("Reply: QotSub: {0}  {1}\n", nSerialNo, rsp.ToString());
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
33  
34  
35  
36  
37  
38  
39  
40  
41  
42  
43  
44  
45  
46  
47  
48  
49  
50  
51  
52  
53  
54  
55  
56  

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6827935070720340213
    Send QotSub: 3
    Reply: QotSub: 3  retType: 0
    retMsg: ""
    errCode: 0
    

1  
2  
3  
4  
5  

`void setRSAPrivateKey(String key)`

*   **介绍**
    
    设置私钥
    
*   **参数**
    
    *   key: 私钥内容

*   **Example**

    public class QotDemo implements FTSPI_Qot, FTSPI_Conn {
        FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();
    
        public QotDemo() {
            qot.setClientInfo("javaclient", 1);  //设置客户端信息
            qot.setConnSpi(this);  //设置连接回调
        }
    
        public void start() throws IOException {
            String rsaKey = "";
            byte[] buf = java.nio.file.Files.readAllBytes(Paths.get("c:\\rsa1024"));
            rsaKey = new String(buf, Charset.forName("UTF-8"));
            qot.setRSAPrivateKey(rsaKey);
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

*   **Example**

    public class QotDemo implements MMSPI_Qot, MMSPI_Conn {
        MMAPI_Conn_Qot qot = new MMAPI_Conn_Qot();
    
        public QotDemo() {
            qot.setClientInfo("javaclient", 1);  //设置客户端信息
            qot.setConnSpi(this);  //设置连接回调
        }
    
        public void start() throws IOException {
            String rsaKey = "";
            byte[] buf = java.nio.file.Files.readAllBytes(Paths.get("c:\\rsa1024"));
            rsaKey = new String(buf, Charset.forName("UTF-8"));
            qot.setRSAPrivateKey(rsaKey);
        }
    
        public static void main(String[] args) throws IOException {
            MMTAPI.init();
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

`void SetRSAPrivateKey(const char* szRSAPrivateKey);`

*   **介绍**
    
    设置私钥
    
*   **参数**
    
    *   strRSAPrivateKey: 私钥内容

*   **Example**

    string strKey;
    ifstream file("key");
    file >> strKey;
    file.close();
    FTAPI_Qot *m_pQotApi = FTAPI::CreateQotApi();
    m_pQotApi->SetRSAPrivateKey(strKey.c_str());
    m_pQotApi->InitConnect("127.0.0.1", 11111, true);
    FTAPI::ReleaseQotApi(m_pQotApi);
    

1  
2  
3  
4  
5  
6  
7  
8  

    string strKey;
    ifstream file("key");
    file >> strKey;
    file.close();
    MMAPI_Qot *m_pQotApi = MMAPI::CreateQotApi();
    m_pQotApi->SetRSAPrivateKey(strKey.c_str());
    m_pQotApi->InitConnect("127.0.0.1", 11111, true);
    MMAPI::ReleaseQotApi(m_pQotApi);
    

1  
2  
3  
4  
5  
6  
7  
8  

`start(ip, port, ssl, key)`

*   **介绍**
    
    初始化连接，连接并初始化
    

*   **参数**
    
    *   ip: OpenD 监听的 WebSocket 地址
    *   port: OpenD 监听的 WebSocket 端口
    *   ssl: 是否启用 SSL 加密，参见 [WebSocket 相关](./qa_other.md#6319)
        
    *   key：连接的密钥，否则会连接超时，密钥在在 OpenD 可配置，UI 版本在不指定的情况下会随机指定
*   **Example**
    

    import ftWebSocket from "@/components/ft-websocket/main.js";
    class Example {
        example() {
            this.websocket = new ftWebSocket();
            this.websocket.start("127.0.0.1", 33333, true, '123456');
        }
    }
    

1  
2  
3  
4  
5  
6  
7  

*   **参数**
    
    *   ip: OpenD 监听的 WebSocket 地址
    *   port: OpenD 监听的 WebSocket 端口
    *   ssl: 是否启用 SSL 加密，参见 [WebSocket 相关](./qa_other.md#6319)
        
    *   key：连接的密钥，否则会连接超时，密钥在在 OpenD 可配置，UI 版本在不指定的情况下会随机指定
*   **Example**
    

    import mmWebSocket from "@/components/mm-websocket/main.js";
    class Example {
        example() {
            this.websocket = new mmWebSocket();
            this.websocket.start("127.0.0.1", 33333, true, '123456');
        }
    }
    

1  
2  
3  
4  
5  
6  
7  

InitConnect.proto

    message C2S
    {
            required int32 clientVer = 1; //客户端版本号，clientVer = "."以前的数 * 100 + "."以后的，举例：1.1版本的 clientVer 为1 * 100 + 1 = 101，2.21版本为2 * 100 + 21 = 221
            required string clientID = 2; //客户端唯一标识，无生具体生成规则，客户端自己保证唯一性即可
            optional bool recvNotify = 3; //此连接是否接收市场状态、交易需要重新解锁等等事件通知，true 代表接收，OpenD 就会向此连接推送这些通知，反之 false 代表不接收不推送
            //如果通信要加密，首先得在 OpenD 和客户端都配置 RSA 密钥，不配置始终不加密
            //如果配置了 RSA 密钥且指定的加密算法不为 PacketEncAlgo_None 则加密(即便这里不设置，配置了 RSA 密钥，也会采用默认加密方式)，默认采用 FTAES_ECB 算法
            optional int32 packetEncAlgo = 4; //指定包加密算法，参见 Common.PacketEncAlgo 的枚举定义
            optional int32 pushProtoFmt = 5; //指定这条连接上的推送协议格式，若不指定则使用 push_proto_type 配置项
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

InitConnect.proto

    message C2S
    {
            required int32 clientVer = 1; //客户端版本号，clientVer = "."以前的数 * 100 + "."以后的，举例：1.1版本的 clientVer 为1 * 100 + 1 = 101，2.21版本为2 * 100 + 21 = 221
            required string clientID = 2; //客户端唯一标识，无生具体生成规则，客户端自己保证唯一性即可
            optional bool recvNotify = 3; //此连接是否接收市场状态、交易需要重新解锁等等事件通知，true 代表接收，OpenD 就会向此连接推送这些通知，反之 false 代表不接收不推送
            //如果通信要加密，首先得在 OpenD 和客户端都配置 RSA 密钥，不配置始终不加密
            //如果配置了 RSA 密钥且指定的加密算法不为 PacketEncAlgo_None 则加密(即便这里不设置，配置了 RSA 密钥，也会采用默认加密方式)，默认采用 FTAES_ECB 算法
            optional int32 packetEncAlgo = 4; //指定包加密算法，参见 Common.PacketEncAlgo 的枚举定义
            optional int32 pushProtoFmt = 5; //指定这条连接上的推送协议格式，若不指定则使用 push_proto_type 配置项
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

*   **介绍**
    
    在初始化连接协议中 packetEncAlgo 字段指定该连接上加密算法。  
    协议加密详情，参见[加密通信流程](./ftapi_protocol.md#2846)
    

[#](./ftapi_init.md#4570)
 设置线程模式
-------------------------------------------------------------------------

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   裸协议

`set_all_thread_daemon(all_daemon)`

*   **介绍**
    
    是否设置所有内部创建的线程为 daemon 线程。
    
    *   若设置为 daemon 线程：主线程退出后，则进程也退出。  
        例如：使用实时回调接口时，需要自己保证主线程存活，否则主线程退出后，进程也退出，您将不会再接收到推送数据。
    *   若设置为非 daemon 线程：主线程退出后，进程不会退出。  
        例如：在创建行情或交易对象后，若不调用 close() 关闭连接，即使主线程退出，进程不会退出。
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | all\_daemon | bool | 是否设置为 daemon 线程<br>(ℹ️ *   True：设置为 daemon 线程)<br>*   False：设置为非 daemon 线程<br>*   默认为 False |
    
*   **Example**
    

    from futu import *
    SysConfig.set_all_thread_daemon(True)
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    # 不调用 quote_ctx.close()，进程也会退出
    

1  
2  
3  
4  

    from moomoo import *
    SysConfig.set_all_thread_daemon(True)
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    # 不调用 quote_ctx.close()，进程也会退出
    

1  
2  
3  
4  

[#](./ftapi_init.md#8035)
 设置回调
-----------------------------------------------------------------------

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   裸协议

`set_handler(handler)`

*   **介绍**
    
    设置异步回调处理对象
    
*   **参数**
    
    *   handler: 回调处理对象
        
        | 类   | 说明  |
        | --- | --- |
        | SysNotifyHandlerBase | [OpenD 通知处理基类](./ftapi_init.md#6884) |
        | StockQuoteHandlerBase | [报价处理基类](./quote_update-stock-quote.md) |
        | OrderBookHandlerBase | [摆盘处理基类](./quote_update-order-book.md) |
        | CurKlineHandlerBase | [实时 K 线处理基类](./quote_update-kl.md) |
        | TickerHandlerBase | [逐笔处理基类](./quote_update-ticker.md) |
        | RTDataHandlerBase | [分时数据处理基类](./quote_update-rt.md) |
        | BrokerHandlerBase | [经济队列处理基类](./quote_update-broker.md) |
        | PriceReminderHandlerBase | [到价提醒处理基类](./quote_update-price-reminder.md) |
        | TradeOrderHandlerBase | [订单处理基类](./trade_update-order.md) |
        | TradeDealHandlerBase | [成交处理基类](./trade_update-order-fill.md) |
        

*   **Example**

    import time
    from futu import *
    class OrderBookTest(OrderBookHandlerBase):
        def on_recv_rsp(self, rsp_str):
            ret_code, data = super(OrderBookTest,self).on_recv_rsp(rsp_str)
            if ret_code != RET_OK:
                print("OrderBookTest: error, msg: %s" % data)
                return RET_ERROR, data
            print("OrderBookTest ", data) # OrderBookTest 自己的处理逻辑
            return RET_OK, data
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = OrderBookTest()
    quote_ctx.set_handler(handler)  # 设置实时摆盘回调
    quote_ctx.subscribe(['HK.00700'], [SubType.ORDER_BOOK])  # 订阅买卖摆盘类型，OpenD 开始持续收到服务器的推送
    time.sleep(15)  #  设置脚本接收 OpenD 的推送持续时间为15秒
    quote_ctx.close()  # 关闭当条连接，OpenD 会在1分钟后自动取消相应股票相应类型的订阅
    

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

    import time
    from moomoo import *
    class OrderBookTest(OrderBookHandlerBase):
        def on_recv_rsp(self, rsp_str):
            ret_code, data = super(OrderBookTest,self).on_recv_rsp(rsp_str)
            if ret_code != RET_OK:
                print("OrderBookTest: error, msg: %s" % data)
                return RET_ERROR, data
            print("OrderBookTest ", data) # OrderBookTest 自己的处理逻辑
            return RET_OK, data
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = OrderBookTest()
    quote_ctx.set_handler(handler)  # 设置实时摆盘回调
    quote_ctx.subscribe(['HK.00700'], [SubType.ORDER_BOOK])  # 订阅买卖摆盘类型，OpenD 开始持续收到服务器的推送
    time.sleep(15)  #  设置脚本接收 OpenD 的推送持续时间为15秒
    quote_ctx.close()  # 关闭当条连接，OpenD 会在1分钟后自动取消相应股票相应类型的订阅
    

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

`void SetConnCallback(FTSPI_Conn connCallback)`  
`void SetQotCallback(FTSPI_Qot callback)`  
`void SetTrdCallback(FTSPI_Trd callback)`

`void SetConnCallback(MMSPI_Conn connCallback)`  
`void SetQotCallback(MMSPI_Qot callback)`  
`void SetTrdCallback(MMSPI_Trd callback)`

*   **介绍**
    
    设置回调
    
*   **参数**
    
    *   callback: 回调函数

*   **Example**

    public class Program : FTSPI_Qot, FTSPI_Conn
        {
            FTAPI_Qot qot = new FTAPI_Qot();
    
            public Program()
            {
                qot.SetClientInfo("csharp", 1);  //设置客户端信息
                qot.SetConnCallback(this);  //设置连接回调
                qot.SetQotCallback(this);   //设置交易回调
            }
    
            public void Start()
            {
                qot.InitConnect("127.0.0.1", (ushort)11111, false); //连接加密
            }
    
    
            public void OnInitConnect(FTAPI_Conn client, long errCode, String desc)
            {
                Console.Write("Qot onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
                if (errCode != 0)
                    return;
            }
    
    
            public void OnDisconnect(FTAPI_Conn client, long errCode)
            {
                Console.Write("Qot onDisConnect: {0}\n", errCode);
            }
    
    
            public static void Main(String[] args)
            {
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
33  
34  
35  
36  
37  
38  
39  
40  
41  
42  

    public class Program : MMSPI_Qot, MMSPI_Conn
        {
            MMAPI_Qot qot = new MMAPI_Qot();
    
            public Program()
            {
                qot.SetClientInfo("csharp", 1);  //设置客户端信息
                qot.SetConnCallback(this);  //设置连接回调
                qot.SetQotCallback(this);   //设置交易回调
            }
    
            public void Start()
            {
                qot.InitConnect("127.0.0.1", (ushort)11111, false); //连接加密
            }
    
    
            public void OnInitConnect(MMAPI_Conn client, long errCode, String desc)
            {
                Console.Write("Qot onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
                if (errCode != 0)
                    return;
            }
    
    
            public void OnDisconnect(MMAPI_Conn client, long errCode)
            {
                Console.Write("Qot onDisConnect: {0}\n", errCode);
            }
    
    
            public static void Main(String[] args)
            {
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
33  
34  
35  
36  
37  
38  
39  
40  
41  
42  

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6827937191858660334
    

1  

`setConnSpi(FTSPI_Conn callback);`  
`void setQotSpi(FTSPI_Qot callback);`  
`void setTrdSpi(FTSPI_Trd callback);`

`setConnSpi(MMSPI_Conn callback);`  
`void setQotSpi(MMSPI_Qot callback);`  
`void setTrdSpi(MMSPI_Trd callback);`

*   **介绍**
    
    设置回调
    
*   **参数**
    
    *   callback: 回调函数

*   **Example**

    public class QotDemo implements FTSPI_Qot, FTSPI_Conn {
        FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();
    
        public QotDemo() {
            qot.setClientInfo("javaclient", 1);  //设置客户端信息
            qot.setConnSpi(this);  //设置连接回调
            qot.setQotSpi(this);   //设置行情回调
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
38  

*   **Example**

    public class QotDemo implements MMSPI_Qot, MMSPI_Conn {
        MMAPI_Conn_Qot qot = new MMAPI_Conn_Qot();
    
        public QotDemo() {
            qot.setClientInfo("javaclient", 1);  //设置客户端信息
            qot.setConnSpi(this);  //设置连接回调
            qot.setQotSpi(this);   //设置行情回调
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
38  

`void RegisterConnSpi(FTSPI_Qot* pSpi);`  
`void RegisterQotSpi(FTSPI_Qot* pSpi);`  
`void RegisterTrdSpi(FTSPI_Qot* pSpi);`

`void RegisterConnSpi(MMSPI_Qot* pSpi);`  
`void RegisterQotSpi(MMSPI_Qot* pSpi);`  
`void RegisterTrdSpi(MMSPI_Qot* pSpi);`

*   **介绍**
    
    设置回调
    
*   **参数**
    
    *   pSpi: 回调函数
*   **Example**
    

    FTAPI_Qot *m_pQotApi = FTAPI::CreateQotApi();
    m_pQotApi->InitConnect("127.0.0.1", 11111, false);
    m_pQotApi->RegisterQotSpi(this);
    m_pQotApi->RegisterConnSpi(this);
    FTAPI::ReleaseQotApi(m_pQotApi);
    

1  
2  
3  
4  
5  

    MMAPI_Qot *m_pQotApi = MMAPI::CreateQotApi();
    m_pQotApi->InitConnect("127.0.0.1", 11111, false);
    m_pQotApi->RegisterQotSpi(this);
    m_pQotApi->RegisterConnSpi(this);
    MMAPI::ReleaseQotApi(m_pQotApi);
    

1  
2  
3  
4  
5  

`onPush(cmd, res)`  
`onlogin(ret, msg)`

*   **介绍**
    
    登录回调以及推送回调
    
*   **返回**
    
    *   cmd: 推送协议 ID
    *   res: 推送协议内容
    *   ret: 是否成功初始化
    *   msg: 失败描述

*   **Example**

    import ftWebSocket from "@/components/ft-websocket/main.js";
    class Example {
        example() {
            this.websocket = new ftWebSocket();
            this.websocket.start("127.0.0.1", 33333, false, null);
            this.websocket.onPush = this.onPush.bind(this);
            this.websocket.onlogin = this.onLogin.bind(this);
        },
        onPush(cmd, res) {
        const obj = ftWebSocket.findCmdObj(cmd);
            if (obj && obj.description) {
                console.log(res);
            }
        },
        onLogin(ret, msg) {
            if (ret) {
                console.log(this.websocket.getConnID());
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

*   **Example**

    import mmWebSocket from "@/components/mm-websocket/main.js";
    class Example {
        example() {
            this.websocket = new mmWebSocket();
            this.websocket.start("127.0.0.1", 33333, false, null);
            this.websocket.onPush = this.onPush.bind(this);
            this.websocket.onlogin = this.onLogin.bind(this);
        },
        onPush(cmd, res) {
        const obj = mmWebSocket.findCmdObj(cmd);
            if (obj && obj.description) {
                console.log(res);
            }
        },
        onLogin(ret, msg) {
            if (ret) {
                console.log(this.websocket.getConnID());
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

[#](./ftapi_init.md#1990)
 获取连接 ID
--------------------------------------------------------------------------

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   裸协议

`get_sync_conn_id()`

*   **介绍**
    
    获取连接 ID，连接初始化成功后才会有值
    
*   **返回**
    
    *   conn\_id: 连接 ID

*   **Example**

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    quote_ctx.get_sync_conn_id()
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  
4  

*   **Example**

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    quote_ctx.get_sync_conn_id()
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  
4  

`uint GetConnectID();`

*   **介绍**
    
    获取连接 ID，连接初始化成功后才会有值
    
*   **返回**
    
    *   connID: 连接 ID

*   **Example**

    public class Program : FTSPI_Qot, FTSPI_Conn
        {
            FTAPI_Qot qot = new FTAPI_Qot();
    
            public Program()
            {
                qot.SetClientInfo("csharp", 1);  //设置客户端信息
                qot.SetConnCallback(this);  //设置连接回调
                qot.SetQotCallback(this);   //设置交易回调
            }
    
            public void Start()
            {
                qot.InitConnect("127.0.0.1", (ushort)11111, false); //连接加密
            }
    
    
            public void OnInitConnect(FTAPI_Conn client, long errCode, String desc)
            {
                Console.Write("Qot onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
                if (errCode != 0)
                    return;
            }
    
    
            public void OnDisconnect(FTAPI_Conn client, long errCode)
            {
                Console.Write("Qot onDisConnect: {0}\n", errCode);
            }
    
            public static void Main(String[] args)
            {
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
33  
34  
35  
36  
37  
38  
39  
40  
41  

*   **Example**

    public class Program : MMSPI_Qot, MMSPI_Conn
        {
            MMAPI_Qot qot = new MMAPI_Qot();
    
            public Program()
            {
                qot.SetClientInfo("csharp", 1);  //设置客户端信息
                qot.SetConnCallback(this);  //设置连接回调
                qot.SetQotCallback(this);   //设置交易回调
            }
    
            public void Start()
            {
                qot.InitConnect("127.0.0.1", (ushort)11111, false); //连接加密
            }
    
    
            public void OnInitConnect(MMAPI_Conn client, long errCode, String desc)
            {
                Console.Write("Qot onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
                if (errCode != 0)
                    return;
            }
    
    
            public void OnDisconnect(MMAPI_Conn client, long errCode)
            {
                Console.Write("Qot onDisConnect: {0}\n", errCode);
            }
    
            public static void Main(String[] args)
            {
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
33  
34  
35  
36  
37  
38  
39  
40  
41  

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6827937791149892052
    

1  

`long getConnectID();`

*   **介绍**
    
    获取连接 ID，连接初始化成功后才会有值
    
*   **返回**
    
    *   connID: 连接 ID

*   **Example**

    public class QotDemo implements FTSPI_Qot, FTSPI_Conn {
        FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();
    
        public QotDemo() {
            qot.setClientInfo("javaclient", 1);  //设置客户端信息
            qot.setConnSpi(this);  //设置连接回调
            qot.setQotSpi(this);   //设置行情回调
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
                    Thread.sleep(1000 * 60);
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
38  

*   **Example**

    public class QotDemo implements MMSPI_Qot, MMSPI_Conn {
        MMAPI_Conn_Qot qot = new MMAPI_Conn_Qot();
    
        public QotDemo() {
            qot.setClientInfo("javaclient", 1);  //设置客户端信息
            qot.setConnSpi(this);  //设置连接回调
            qot.setQotSpi(this);   //设置行情回调
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
                    Thread.sleep(1000 * 60);
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
38  

`Futu::u64_t GetConnectID()`

*   **介绍**
    
    获取连接 ID，连接初始化成功后才会有值
    
*   **返回**
    
    *   nConnID: 连接 ID
*   **Example**
    

    FTAPI_Qot *m_pQotApi = FTAPI::CreateQotApi();
    m_pQotApi->InitConnect("127.0.0.1", 11111, false);
    m_pQotApi->GetConnectID();
    FTAPI::ReleaseQotApi(m_pQotApi);
    

1  
2  
3  
4  

`moomoo::u64_t GetConnectID()`

*   **介绍**
    
    获取连接 ID，连接初始化成功后才会有值
    
*   **返回**
    
    *   nConnID: 连接 ID

    MMAPI_Qot *m_pQotApi = MMAPI::CreateQotApi();
    m_pQotApi->InitConnect("127.0.0.1", 11111, false);
    m_pQotApi->GetConnectID();
    MMAPI::ReleaseQotApi(m_pQotApi);
    

1  
2  
3  
4  

`getConnID()`

*   **介绍**
    
    获取连接 ID，连接初始化成功后才会有值
    
*   **返回**
    
    *   connID: 连接 ID

*   **Example**

    import ftWebSocket from "@/components/ft-websocket/main.js";
    class Example {
        example() {
            this.websocket = new ftWebSocket();
            this.websocket.start("127.0.0.1", 33333, false, null);
            this.websocket.onlogin = this.onLogin.bind(this);
        },
        onLogin(ret, msg) {
        if (ret) {
            this.websocket.getConnID();
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

    import mmWebSocket from "@/components/mm-websocket/main.js";
    class Example {
        example() {
            this.websocket = new mmWebSocket();
            this.websocket.start("127.0.0.1", 33333, false, null);
            this.websocket.onlogin = this.onLogin.bind(this);
        },
        onLogin(ret, msg) {
        if (ret) {
            this.websocket.getConnID();
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

InitConnect.proto

    message S2C
    {
    	required int32 serverVer = 1; //OpenD 的版本号
    	required uint64 loginUserID = 2; //OpenD 登录的用户 ID
    	required uint64 connID = 3; //此连接的连接 ID，连接的唯一标识
    	required string connAESKey = 4; //此连接后续 AES 加密通信的 Key，固定为16字节长字符串
    	required int32 keepAliveInterval = 5; //心跳保活间隔
    	optional string aesCBCiv = 6; //AES 加密通信 CBC 加密模式的 iv，固定为16字节长字符串
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

*   **介绍**
    
    InitConnect 协议回包中的 connID 字段
    

[#](./ftapi_init.md#6884)
 事件通知回调
-------------------------------------------------------------------------

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   裸协议

`SysNotifyHandlerBase`

*   **介绍**
    
    通知 OpenD 一些重要消息，类似连接断开等
    
*   **协议 ID**
    
    1003
    
*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | tuple | 当 ret == RET\_OK 时，返回 **事件通知数据** |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   **事件通知数据** 的格式如下：
        
          
        
        | 参数  | 类型  | 说明  |
        | --- | --- | --- |
        | notify\_type | [SysNotifyType](./ftapi_common.md#5896) | 通知类型 |
        | sub\_type | [ProgramStatusType](./ftapi_common.md#6427) | 子类型。当 notify\_type == SysNotifyType.PROGRAM\_STATUS 时，sub\_type 返回程序状态类型 |
        | [GtwEventType](./ftapi_common.md#7799) | 子类型。当 notify\_type == SysNotifyType.GTW\_EVENT 时，sub\_type 返回 OpenD 事件通知类型 |
        | 0   | 当 notify\_type != SysNotifyType.PROGRAM\_STATUS 且 notify\_type != SysNotifyType.GTW\_EVENT 时，sub\_type 返回 0 |
        | msg | dict | 事件信息。当 notify\_type == SysNotifyType.CONN\_STATUS 时，msg 返回 **连接状态事件信息** 字典 |
        | 事件信息。当 notify\_type == SysNotifyType.QOT\_RIGHT 时，msg 返回 **行情权限事件信息** 字典 |
        
        *   **连接状态事件信息** 字典结构如下（连接状态类型为 bool，True 表示连接正常，False 表示连接断开）:
            
                {
                    'qot_logined': bool1, 
                    'trd_logined': bool2,
                }
                
            
            1  
            2  
            3  
            4  
            
        *   **行情权限事件信息** 字典结构如下（点击了解 [行情权限](./quote_quote.md#2867)
            ）:
            
                {
                    'hk_qot_right': value1,
                    'hk_option_qot_right': value2,
                    'hk_future_qot_right': value3,
                    'us_qot_right': value4,
                    'us_option_qot_right': value5,
                    'us_future_qot_right': value6,  // 已废弃
                    'cn_qot_right': value7,
                	'us_index_qot_right': value8,
                	'us_otc_qot_right': value9,
                	'sg_future_qot_right': value10,
                	'jp_future_qot_right': value11,
                	'us_future_qot_right_cme': value12,
                	'us_future_qot_right_cbot': value13,
                	'us_future_qot_right_nymex': value14,
                	'us_future_qot_right_comex': value15,
                	'us_future_qot_right_cboe': value16,
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
            

*   **Example**

    import time
    from futu import *
    
    
    class SysNotifyTest(SysNotifyHandlerBase):
        def on_recv_rsp(self, rsp_str):
            ret_code, data = super(SysNotifyTest, self).on_recv_rsp(rsp_str)
            notify_type, sub_type, msg = data
            if ret_code != RET_OK:
                logger.debug("SysNotifyTest: error, msg: {}".format(msg))
                return RET_ERROR, data
            if notify_type == SysNotifyType.GTW_EVENT:  # OpenD 事件通知
                print("GTW_EVENT, type: {} msg: {}".format(sub_type, msg))
            elif notify_type == SysNotifyType.PROGRAM_STATUS:  # 程序状态变化通知
                print("PROGRAM_STATUS, type: {} msg: {}".format(sub_type, msg))
            elif notify_type == SysNotifyType.CONN_STATUS:  ## 连接状态变化通知
                print("CONN_STATUS, qot: {}".format(msg['qot_logined']))
                print("CONN_STATUS, trd: {}".format(msg['trd_logined']))
            elif notify_type == SysNotifyType.QOT_RIGHT:  # 行情权限变化通知
                print("QOT_RIGHT, hk: {}".format(msg['hk_qot_right']))
                print("QOT_RIGHT, hk_option: {}".format(msg['hk_option_qot_right']))
                print("QOT_RIGHT, hk_future: {}".format(msg['hk_future_qot_right']))
                print("QOT_RIGHT, us: {}".format(msg['us_qot_right']))
                print("QOT_RIGHT, us_option: {}".format(msg['us_option_qot_right']))
                print("QOT_RIGHT, cn: {}".format(msg['cn_qot_right']))
    			print("QOT_RIGHT, us_index: {}".format(msg['us_index_qot_right']))
    			print("QOT_RIGHT, us_otc: {}".format(msg['us_otc_qot_right']))
    			print("QOT_RIGHT, sg_future: {}".format(msg['sg_future_qot_right']))
    			print("QOT_RIGHT, jp_future: {}".format(msg['jp_future_qot_right']))
                print("QOT_RIGHT, us_future_cme: {}".format(msg['us_future_qot_right_cme']))
                print("QOT_RIGHT, us_future_cbot: {}".format(msg['us_future_qot_right_cbot']))
                print("QOT_RIGHT, us_future_nymex: {}".format(msg['us_future_qot_right_nymex']))
                print("QOT_RIGHT, us_future_comex: {}".format(msg['us_future_qot_right_comex']))
                print("QOT_RIGHT, us_future_cboe: {}".format(msg['us_future_qot_right_cboe']))
            return RET_OK, data
    
    
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = SysNotifyTest()
    quote_ctx.set_handler(handler)  # 设置回调
    time.sleep(15)  # 设置脚本接收 OpenD 的推送持续时间为15秒
    quote_ctx.close()  # 结束后记得关闭当条连接，防止连接条数用尽`
    

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
38  
39  
40  
41  
42  

*   **Example**

    import time
    from moomoo import *
    
    
    class SysNotifyTest(SysNotifyHandlerBase):
        def on_recv_rsp(self, rsp_str):
            ret_code, data = super(SysNotifyTest, self).on_recv_rsp(rsp_str)
            notify_type, sub_type, msg = data
            if ret_code != RET_OK:
                logger.debug("SysNotifyTest: error, msg: {}".format(msg))
                return RET_ERROR, data
            if notify_type == SysNotifyType.GTW_EVENT:  # OpenD 事件通知
                print("GTW_EVENT, type: {} msg: {}".format(sub_type, msg))
            elif notify_type == SysNotifyType.PROGRAM_STATUS:  # 程序状态变化通知
                print("PROGRAM_STATUS, type: {} msg: {}".format(sub_type, msg))
            elif notify_type == SysNotifyType.CONN_STATUS:  ## 连接状态变化通知
                print("CONN_STATUS, qot: {}".format(msg['qot_logined']))
                print("CONN_STATUS, trd: {}".format(msg['trd_logined']))
            elif notify_type == SysNotifyType.QOT_RIGHT:  # 行情权限变化通知
                print("QOT_RIGHT, hk: {}".format(msg['hk_qot_right']))
                print("QOT_RIGHT, hk_option: {}".format(msg['hk_option_qot_right']))
                print("QOT_RIGHT, hk_future: {}".format(msg['hk_future_qot_right']))
                print("QOT_RIGHT, us: {}".format(msg['us_qot_right']))
                print("QOT_RIGHT, us_option: {}".format(msg['us_option_qot_right']))
                print("QOT_RIGHT, cn: {}".format(msg['cn_qot_right']))
    			print("QOT_RIGHT, us_index: {}".format(msg['us_index_qot_right']))
    			print("QOT_RIGHT, us_otc: {}".format(msg['us_otc_qot_right']))
    			print("QOT_RIGHT, sg_future: {}".format(msg['sg_future_qot_right']))
    			print("QOT_RIGHT, jp_future: {}".format(msg['jp_future_qot_right']))
                print("QOT_RIGHT, us_future_cme: {}".format(msg['us_future_qot_right_cme']))
                print("QOT_RIGHT, us_future_cbot: {}".format(msg['us_future_qot_right_cbot']))
                print("QOT_RIGHT, us_future_nymex: {}".format(msg['us_future_qot_right_nymex']))
                print("QOT_RIGHT, us_future_comex: {}".format(msg['us_future_qot_right_comex']))
                print("QOT_RIGHT, us_future_cboe: {}".format(msg['us_future_qot_right_cboe']))
            return RET_OK, data
    
    
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    handler = SysNotifyTest()
    quote_ctx.set_handler(handler)  # 设置回调
    time.sleep(15)  # 设置脚本接收 OpenD 的推送持续时间为15秒
    quote_ctx.close()  # 结束后记得关闭当条连接，防止连接条数用尽`
    

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
38  
39  
40  
41  
42  

`void OnReply_Notify(FTAPI_Conn client, uint nSerialNo, Notify.Response rsp)`

`void OnReply_Notify(MMAPI_Conn client, uint nSerialNo, Notify.Response rsp)`

*   **介绍**
    
    通知 OpenD 一些重要消息，类似连接断开等
    

`void onPush_Notify(FTAPI_Conn client, Notify.Response rsp)`

`void onPush_Notify(MMAPI_Conn client, Notify.Response rsp)`

*   **介绍**
    
    通知 OpenD 一些重要消息，类似连接断开等
    

`virtual void OnPush_Notify(const Notify::Response &stRsp) = 0;`

*   **介绍**
    
    通知 OpenD 一些重要消息，类似连接断开等
    

`onPush(cmd, res)`

*   **介绍**
    
    通知 OpenD 一些重要消息，类似连接断开等
    

*   **Example**

    import ftWebSocket from "@/components/ft-websocket/main.js";
    class Example {
        example() {
            this.websocket = new ftWebSocket();
            this.websocket.start("127.0.0.1", 33333, false, null);
            this.websocket.onPush = this.onPush.bind(this);
            this.websocket.onlogin = this.onLogin.bind(this);
        },
        onPush(cmd, res) {
        const obj = ftWebSocket.findCmdObj(cmd);
            if (obj && obj.description && cmd == 1003) {
                console.log(res);
            }
        },
        onLogin(ret, msg) {
            if (ret) {
                console.log(this.websocket.getConnID());
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

*   **Example**

    import mmWebSocket from "@/components/mm-websocket/main.js";
    class Example {
        example() {
            this.websocket = new mmWebSocket();
            this.websocket.start("127.0.0.1", 33333, false, null);
            this.websocket.onPush = this.onPush.bind(this);
            this.websocket.onlogin = this.onLogin.bind(this);
        },
        onPush(cmd, res) {
        const obj = mmWebSocket.findCmdObj(cmd);
            if (obj && obj.description && cmd == 1003) {
                console.log(res);
            }
        },
        onLogin(ret, msg) {
            if (ret) {
                console.log(this.websocket.getConnID());
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

Notify.proto

    message S2C
    {
    	required int32 type = 1; //通知类型
    	optional GtwEvent event = 2; //事件通息
    	optional ProgramStatus programStatus = 3; //程序状态
    	optional ConnectStatus connectStatus = 4; //连接状态
    	optional QotRight qotRight = 5; //行情权限
    	optional APILevel apiLevel = 6; //用户等级，已在2.10版本之后废弃
    	optional APIQuota apiQuota = 7; //API 额度
    }
    
    message Response
    {
    	required int32 retType = 1 [default = -400]; //RetType,返回结果
    	optional string retMsg = 2;
    	optional int32 errCode = 3;
    	
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

← [交易定义](./trade_trade.md) [通用定义](./ftapi_common.md)
 →

[基础功能](./ftapi_init.md)

*   [设置接口信息](./ftapi_init.md#7518)
    
*   [设置协议格式](./ftapi_init.md#1515)
    
*   [对所有连接设置协议加密](./ftapi_init.md#319)
    
*   [设置私钥路径](./ftapi_init.md#5641)
    
*   [设置线程模式](./ftapi_init.md#4570)
    
*   [设置回调](./ftapi_init.md#8035)
    
*   [获取连接 ID](./ftapi_init.md#1990)
    
*   [事件通知回调](./ftapi_init.md#6884)