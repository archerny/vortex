 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/ftapi/protocol.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/ftapi/protocol.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/ftapi/protocol.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/ftapi/protocol.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
*   交易接口
    
*   基础接口
    
    *   [基础功能](https://openapi.futunn.com/futu-api-doc/ftapi/init.html)
        
    *   [通用定义](https://openapi.futunn.com/futu-api-doc/ftapi/common.html)
        
    *   [底层协议介绍](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html)
        
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#8395)
 底层协议介绍
=============================================================================

Futu API 是富途为主流的编程语言（Python、Java、C#、C++、JavaScript）封装的 API SDK，以方便您调用，降低策略开发难度。  
这部分主要介绍策略脚本与 OpenD 服务之间通信的底层协议，适用于非上述 5 种编程语言用户，自行对接实现底层裸协议。

moomoo API 是 moomoo 为主流的编程语言（Python、Java、C#、C++、JavaScript）封装的 API SDK，以方便您调用，降低策略开发难度。  
这部分主要介绍策略脚本与 OpenD 服务之间通信的底层协议，适用于非上述 5 种编程语言用户，自行对接实现底层裸协议。

提示

*   如果您使用的编程语言在上述的 5 种主流编程语言之内，可以直接跳过这部分内容。

[#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#2892)
 协议请求流程
-----------------------------------------------------------------------------

*   建立连接
*   初始化连接
*   请求数据或接收推送数据
*   定时发送 KeepAlive 保持连接

![proto-process](https://openapi.futunn.com/futu-api-doc/assets/img/proto-process.74339e68.png)

![proto-process](https://openapi.futunn.com/futu-api-doc/assets/img/mmproto-process.5f87807f.png)

[#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#2042)
 协议设计
---------------------------------------------------------------------------

协议数据包括协议头以及协议体，协议头固定字段，协议体根据具体协议决定。

### [#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#8205)
 协议头

    struct APIProtoHeader
    {
        u8_t szHeaderFlag[2];
        u32_t nProtoID;
        u8_t nProtoFmtType;
        u8_t nProtoVer;
        u32_t nSerialNo;
        u32_t nBodyLen;
        u8_t arrBodySHA1[20];
        u8_t arrReserved[8];
    };
    

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

| 字段  | 说明  |
| --- | --- |
| szHeaderFlag | 包头起始标志，固定为“FT” |
| nProtoID | 协议 ID |
| nProtoFmtType | 协议格式类型，0 为 Protobuf 格式，1 为 Json 格式 |
| nProtoVer | 协议版本，用于迭代兼容，目前填 0 |
| nSerialNo | 包序列号，用于对应请求包和回包，要求递增 |
| nBodyLen | 包体长度 |
| arrBodySHA1 | 包体原始数据(解密后)的 SHA1 哈希值 |
| arrReserved | 保留 8 字节扩展 |

提示

*   u8\_t 表示 8 位无符号整数，u32\_t 表示 32 位无符号整数
*   OpenD 内部处理使用 Protobuf，因此协议格式建议使用 Protobuf，减少 Json 转换开销
*   nProtoFmtType 字段指定了包体的数据类型，回包会回对应类型的数据；推送协议数据类型由 OpenD 配置文件指定
*   **arrBodySHA1 用于校验请求数据在网络传输前后的一致性，必须正确填入**
*   **协议头的二进制流使用的是小端字节序，即一般不需要使用 ntohl 等相关函数转换数据**

### [#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#9850)
 协议体

#### [#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#8785)
 Protobuf 协议请求包体结构

    message C2S
    {
        required int64 req = 1;
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

#### [#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#342)
 Protobuf 协议回应包体结构

    message S2C
    {
        required int64 data = 1;
    }
    
    message Response
    {
        required int32 retType = 1 [default = -400]; //RetType，返回结果
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

| 字段  | 说明  |
| --- | --- |
| c2s | 请求参数结构 |
| req | 请求参数，实际根据协议定义 |
| retType | 请求结果 |
| retMsg | 若请求失败，说明失败原因 |
| errCode | 若请求失败对应错误码 |
| s2c | 回应数据结构，部分协议不返回数据则无该字段 |
| data | 回应数据，实际根据协议定义 |

提示

*   包体格式类型请求包由协议头 nProtoFmtType 指定，OpenD 主动推送格式在 [InitConnect](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#1515)
     设置。
*   原始协议文件格式是以 Protobuf 格式定义，若需要 json 格式传输，建议使用 protobuf3 的接口直接转换成 json。
*   枚举值字段定义使用有符号整形，注释指明对应枚举，枚举一般定义于 Common.proto，Qot\_Common.proto，Trd\_Common.proto 文件中。
*   协议中价格、百分比等数据用浮点类型来传输，直接使用会有精度问题，需要根据精度（如协议中未指明，默认小数点后三位）做四舍五入之后再使用。

[#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#2603)
 心跳保活
---------------------------------------------------------------------------

    syntax = "proto2";
    package KeepAlive;
    option java_package = "com.futu.openapi.pb";
    option go_package = "github.com/futuopen/ftapi4go/pb/keepalive";
    
    import "Common.proto";
    
    message C2S
    {
    	required int64 time = 1; //客户端发包时的格林威治时间戳，单位秒
    }
    
    message S2C
    {
    	required int64 time = 1; //服务器回包时的格林威治时间戳，单位秒
    }
    
    message Request
    {
    	required C2S c2s = 1;
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

    syntax = "proto2";
    package KeepAlive;
    option java_package = "com.moomoo.openapi.pb";
    option go_package = "github.com/moomooopen/mmapi4go/pb/keepalive";
    
    import "Common.proto";
    
    message C2S
    {
    	required int64 time = 1; //客户端发包时的格林威治时间戳，单位秒
    }
    
    message S2C
    {
    	required int64 time = 1; //服务器回包时的格林威治时间戳，单位秒
    }
    
    message Request
    {
    	required C2S c2s = 1;
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

*   **介绍**
    
    心跳保活
    
*   **协议 ID**
    
    1004
    
*   **使用**
    
    根据[初始化链接](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#1990)
    返回的心跳保活间隔时间，向 OpenD 发送保活协议
    

[#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#2846)
 加密通信流程
-----------------------------------------------------------------------------

*   若 OpenD 配置了加密，[InitConnect](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#1515)
     初始化连接协议必须使用 [RSA](https://openapi.futunn.com/futu-api-doc/qa/other.html#4601)
     公钥加密，后续其他协议使用 InitConnect 返回的随机密钥进行 AES 加密通信。
*   OpenD 的加密流程借鉴了 SSL 协议，但考虑到一般是本地部署服务和应用，简化了相关流程，OpenD 与接入 Client 共用了同一个 [RSA](https://openapi.futunn.com/futu-api-doc/qa/other.html#4601)
     私钥文件，请妥善保存和分发私钥文件。
*   可到这个 [网址](http://web.chacuo.net/netrsakeypair)
     在线生成随机 [RSA](https://openapi.futunn.com/futu-api-doc/qa/other.html#4601)
     密钥对，密钥格式必须为 PCKS#1，密钥长度 512，1024 都可以，不要设置密码，将生成的私钥复制保存到文件中，然后将私钥文件路径配置到 [OpenD 配置](https://openapi.futunn.com/futu-api-doc/opend/opend-cmd.html#8799)
     约定的 **rsa\_private\_key** 配置项中。
*   **建议有实盘交易的用户配置加密，避免账户和交易信息泄露。**

![encrypt](https://openapi.futunn.com/futu-api-doc/assets/img/encrypt.7f671e6d.png)

![encrypt](https://openapi.futunn.com/futu-api-doc/assets/img/mmencrypt.9ec67114.png)

[#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#5219)
 RSA 加解密
------------------------------------------------------------------------------

*   [OpenD 配置](https://openapi.futunn.com/futu-api-doc/opend/opend-cmd.html#8799)
     约定 **rsa\_private\_key** 为私钥文件路径
*   OpenD 与接入客户端共用相同的私钥文件
*   RSA 加解密仅用于 InitConnect 请求，用于安全获取其它请求协议的对称加密 Key
*   OpenD 的 [RSA](https://openapi.futunn.com/futu-api-doc/qa/other.html#4601)
     密钥为 1024 位，填充方式 PKCS1，公钥加密，私钥解密，公钥可通过私钥生成
*   Python API 参考实现：[RsaCrypt](https://github.com/FutunnOpen/py-futu-api/tree/master/futu/common/sys_config.py)
     类的 encrypt / decrypt 接口

### [#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#1496)
 发送数据加密

*   RSA 加密规则:若密钥位数是 key\_size，单次加密串的最大长度为 (key\_size)/8 - 11，目前位数 1024，一次加密长度可定为 100。
*   将明文数据分成一个或数个最长 100 字节的小段进行加密，拼接分段加密数据即为最终的 Body 加密数据。

### [#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#3094)
 接收数据解密

*   RSA 解密同样遵循分段规则，对于 1024 位密钥，每小段待解密数据长度为 128 字节。
*   将密文数据分成一个或数个 128 字节长的小段进行解密，拼接分段解密数据即为最终的 Body 解密数据。

[#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#9339)
 AES 加解密
------------------------------------------------------------------------------

*   加密 key 由 InitConnect 协议返回
*   默认使用的是 AES 的 ecb 加密模式。
*   Python API 参考实现: [ConnMng](https://github.com/FutunnOpen/py-futu-api/tree/master/futu/common/conn_mng.py)
     类的 encrypt\_conn\_data / decrypt\_conn\_data 接口

### [#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#1496-2)
 发送数据加密

*   AES 加密要求源数据长度必须是 16 的整数倍，故需补‘0’对齐后再加密，记录 mod\_len 为源数据长度与 16 取模值。
*   因加密前有可能对源数据作修改，故需在加密后的数据尾再增加一个 16 字节的填充数据块，其最后一个字节赋值 mod\_len，其余字节赋值‘0’，将加密数据和额外的填充数据块拼接作为最终要发送协议的 body 数据。

### [#](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#3094-2)
 接收数据解密

*   协议 body 数据，先将最后一个字节取出，记为 mod\_len，然后将 body 截掉尾部 16 字节填充数据块后再解密（与加密填充额外数据块逻辑对应）。
*   mod\_len 为 0 时，上述解密后的数据即为协议返回的 body 数据，否则需截掉尾部(16 - mod\_len)长度的用于填充对齐的数据。

![aes](https://openapi.futunn.com/futu-api-doc/assets/img/aes.96498078.png)

← [通用定义](https://openapi.futunn.com/futu-api-doc/ftapi/common.html) [OpenD 相关](https://openapi.futunn.com/futu-api-doc/qa/opend.html)
 →

[底层协议介绍](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html)

*   [协议请求流程](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#2892)
    
*   [协议设计](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#2042)
    
*   [心跳保活](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#2603)
    
*   [加密通信流程](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#2846)
    
*   [RSA 加解密](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#5219)
    
*   [AES 加解密](https://openapi.futunn.com/futu-api-doc/ftapi/protocol.html#9339)