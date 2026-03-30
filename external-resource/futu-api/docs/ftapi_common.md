[#](./ftapi_common.md#4712)
 通用定义
=========================================================================

[#](./ftapi_common.md#7467)
 接口调用结果
---------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **RET\_CODE**

*   `RET_OK`
    
    成功
    
*   `RET_ERROR`
    
    失败
    

**RetType**

    enum RetType
    {
        RetType_Succeed = 0; //成功
        RetType_Failed = -1; //失败
        RetType_TimeOut = -100; //超时
        RetType_Unknown = -400; //未知结果
    }
    

1  
2  
3  
4  
5  
6  
7  

**RetType**

    enum RetType
    {
        RetType_Succeed = 0; //成功
        RetType_Failed = -1; //失败
        RetType_TimeOut = -100; //超时
        RetType_Unknown = -400; //未知结果
    }
    

1  
2  
3  
4  
5  
6  
7  

**RetType**

    enum RetType
    {
        RetType_Succeed = 0; //成功
        RetType_Failed = -1; //失败
        RetType_TimeOut = -100; //超时
        RetType_Unknown = -400; //未知结果
    }
    

1  
2  
3  
4  
5  
6  
7  

**RetType**

    enum RetType
    {
        RetType_Succeed = 0; //成功
        RetType_Failed = -1; //失败
        RetType_TimeOut = -100; //超时
        RetType_Unknown = -400; //未知结果
    }
    

1  
2  
3  
4  
5  
6  
7  

**RetType**

    enum RetType
    {
        RetType_Succeed = 0; //成功
        RetType_Failed = -1; //失败
        RetType_TimeOut = -100; //超时
        RetType_Unknown = -400; //未知结果
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./ftapi_common.md#1222)
 协议格式
-------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **ProtoFMT**

*   `Protobuf`
    
    Google Protobuf 格式
    
*   `Json`
    
    Json 格式
    

**ProtoFmt**

    enum ProtoFmt
    {
        ProtoFmt_Protobuf = 0; //Google Protobuf 格式
        ProtoFmt_Json = 1; //Json 格式
    }
    

1  
2  
3  
4  
5  

**ProtoFmt**

    enum ProtoFmt
    {
        ProtoFmt_Protobuf = 0; //Google Protobuf 格式
        ProtoFmt_Json = 1; //Json 格式
    }
    

1  
2  
3  
4  
5  

**ProtoFmt**

    enum ProtoFmt
    {
        ProtoFmt_Protobuf = 0; //Google Protobuf 格式
        ProtoFmt_Json = 1; //Json 格式
    }
    

1  
2  
3  
4  
5  

**ProtoFmt**

    enum ProtoFmt
    {
        ProtoFmt_Protobuf = 0; //Google Protobuf 格式
        ProtoFmt_Json = 1; //Json 格式
    }
    

1  
2  
3  
4  
5  

**ProtoFmt**

    enum ProtoFmt
    {
        ProtoFmt_Protobuf = 0; //Google Protobuf 格式
        ProtoFmt_Json = 1; //Json 格式
    }
    

1  
2  
3  
4  
5  

[#](./ftapi_common.md#9178)
 包加密算法
--------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

**PacketEncAlgo**

    enum PacketEncAlgo
    {
        PacketEncAlgo_FTAES_ECB = 0; //修改过的 AES 的 ECB 加密模式
        PacketEncAlgo_None = -1; //不加密
        PacketEncAlgo_AES_ECB = 1; //标准的 AES 的 ECB 加密模式
        PacketEncAlgo_AES_CBC = 2; //标准的 AES 的 CBC 加密模式
    }
    

1  
2  
3  
4  
5  
6  
7  

**PacketEncAlgo**

    enum PacketEncAlgo
    {
        PacketEncAlgo_FTAES_ECB = 0; //修改过的 AES 的 ECB 加密模式
        PacketEncAlgo_None = -1; //不加密
        PacketEncAlgo_AES_ECB = 1; //标准的 AES 的 ECB 加密模式
        PacketEncAlgo_AES_CBC = 2; //标准的 AES 的 CBC 加密模式
    }
    

1  
2  
3  
4  
5  
6  
7  

**PacketEncAlgo**

    enum PacketEncAlgo
    {
        PacketEncAlgo_FTAES_ECB = 0; //修改过的 AES 的 ECB 加密模式
        PacketEncAlgo_None = -1; //不加密
        PacketEncAlgo_AES_ECB = 1; //标准的 AES 的 ECB 加密模式
        PacketEncAlgo_AES_CBC = 2; //标准的 AES 的 CBC 加密模式
    }
    

1  
2  
3  
4  
5  
6  
7  

**PacketEncAlgo**

    enum PacketEncAlgo
    {
        PacketEncAlgo_FTAES_ECB = 0; //修改过的 AES 的 ECB 加密模式
        PacketEncAlgo_None = -1; //不加密
        PacketEncAlgo_AES_ECB = 1; //标准的 AES 的 ECB 加密模式
        PacketEncAlgo_AES_CBC = 2; //标准的 AES 的 CBC 加密模式
    }
    

1  
2  
3  
4  
5  
6  
7  

**PacketEncAlgo**

    enum PacketEncAlgo
    {
        PacketEncAlgo_FTAES_ECB = 0; //修改过的 AES 的 ECB 加密模式
        PacketEncAlgo_None = -1; //不加密
        PacketEncAlgo_AES_ECB = 1; //标准的 AES 的 ECB 加密模式
        PacketEncAlgo_AES_CBC = 2; //标准的 AES 的 CBC 加密模式
    }
    

1  
2  
3  
4  
5  
6  
7  

[#](./ftapi_common.md#6427)
 程序状态类型
---------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **ProgramStatusType**

*   `NONE`
    
    未知
    
*   `LOADED`
    
    已完成必要模块加载
    
*   `LOGING`
    
    登录中
    
*   `NEED_PIC_VERIFY_CODE`
    
    需要图形验证码
    
*   `NEED_PHONE_VERIFY_CODE`
    
    需要手机验证码
    
*   `LOGIN_FAILED`
    
    登录失败
    
*   `FORCE_UPDATE`
    
    客户端版本过低
    
*   `NESSARY_DATA_PREPARING`
    
    正在拉取必要信息
    
*   `NESSARY_DATA_MISSING`
    
    缺少必要信息
    
*   `UN_AGREE_DISCLAIMER`
    
    未同意免责声明
    
*   `READY`
    
    正常可用状态
    
*   `FORCE_LOGOUT`
    
    OpenD 登录后被强制退出登录
    

**ProgramStatusType**

    enum ProgramStatusType
    {
        ProgramStatusType_None = 0;
        ProgramStatusType_Loaded = 1; //已完成类似加载配置,启动服务器等操作,服务器启动之前的状态无需返回
    
        ProgramStatusType_Loging = 2; //登录中
        ProgramStatusType_NeedPicVerifyCode = 3; //需要图形验证码
        ProgramStatusType_NeedPhoneVerifyCode = 4; //需要手机验证码
        ProgramStatusType_LoginFailed = 5; //登录失败,详细原因在描述返回
        ProgramStatusType_ForceUpdate = 6; //客户端版本过低
    
        ProgramStatusType_NessaryDataPreparing = 7; //正在拉取类似免责声明等一些必要信息
        ProgramStatusType_NessaryDataMissing = 8; //缺少必要信息
        ProgramStatusType_UnAgreeDisclaimer = 9; //未同意免责声明
        ProgramStatusType_Ready = 10; //可以接收业务协议收发,正常可用状态
    	
        //OpenD 登录后被强制退出登录，会导致连接全部断开,需要重连后才能得到以下该状态（并且需要在 ui 模式下）
        ProgramStatusType_ForceLogout = 11; //被强制退出登录,例如修改了登录密码,中途打开设备锁等,详细原因在描述返回
    
        ProgramStatusType_DisclaimerPullFailed = 12; //拉取免责声明标志失败
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

**ProgramStatusType**

    enum ProgramStatusType
    {
        ProgramStatusType_None = 0;
        ProgramStatusType_Loaded = 1; //已完成类似加载配置,启动服务器等操作,服务器启动之前的状态无需返回
    
        ProgramStatusType_Loging = 2; //登录中
        ProgramStatusType_NeedPicVerifyCode = 3; //需要图形验证码
        ProgramStatusType_NeedPhoneVerifyCode = 4; //需要手机验证码
        ProgramStatusType_LoginFailed = 5; //登录失败,详细原因在描述返回
        ProgramStatusType_ForceUpdate = 6; //客户端版本过低
    
        ProgramStatusType_NessaryDataPreparing = 7; //正在拉取类似免责声明等一些必要信息
        ProgramStatusType_NessaryDataMissing = 8; //缺少必要信息
        ProgramStatusType_UnAgreeDisclaimer = 9; //未同意免责声明
        ProgramStatusType_Ready = 10; //可以接收业务协议收发,正常可用状态
    	
        //OpenD 登录后被强制退出登录，会导致连接全部断开,需要重连后才能得到以下该状态（并且需要在 ui 模式下）
        ProgramStatusType_ForceLogout = 11; //被强制退出登录,例如修改了登录密码,中途打开设备锁等,详细原因在描述返回
    
        ProgramStatusType_DisclaimerPullFailed = 12; //拉取免责声明标志失败
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

**ProgramStatusType**

    enum ProgramStatusType
    {
        ProgramStatusType_None = 0;
        ProgramStatusType_Loaded = 1; //已完成类似加载配置,启动服务器等操作,服务器启动之前的状态无需返回
    
        ProgramStatusType_Loging = 2; //登录中
        ProgramStatusType_NeedPicVerifyCode = 3; //需要图形验证码
        ProgramStatusType_NeedPhoneVerifyCode = 4; //需要手机验证码
        ProgramStatusType_LoginFailed = 5; //登录失败,详细原因在描述返回
        ProgramStatusType_ForceUpdate = 6; //客户端版本过低
    
        ProgramStatusType_NessaryDataPreparing = 7; //正在拉取类似免责声明等一些必要信息
        ProgramStatusType_NessaryDataMissing = 8; //缺少必要信息
        ProgramStatusType_UnAgreeDisclaimer = 9; //未同意免责声明
        ProgramStatusType_Ready = 10; //可以接收业务协议收发,正常可用状态
    	
        //OpenD 登录后被强制退出登录，会导致连接全部断开,需要重连后才能得到以下该状态（并且需要在 ui 模式下）
        ProgramStatusType_ForceLogout = 11; //被强制退出登录,例如修改了登录密码,中途打开设备锁等,详细原因在描述返回
    
        ProgramStatusType_DisclaimerPullFailed = 12; //拉取免责声明标志失败
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

**ProgramStatusType**

    enum ProgramStatusType
    {
        ProgramStatusType_None = 0;
        ProgramStatusType_Loaded = 1; //已完成类似加载配置,启动服务器等操作,服务器启动之前的状态无需返回
    
        ProgramStatusType_Loging = 2; //登录中
        ProgramStatusType_NeedPicVerifyCode = 3; //需要图形验证码
        ProgramStatusType_NeedPhoneVerifyCode = 4; //需要手机验证码
        ProgramStatusType_LoginFailed = 5; //登录失败,详细原因在描述返回
        ProgramStatusType_ForceUpdate = 6; //客户端版本过低
    
        ProgramStatusType_NessaryDataPreparing = 7; //正在拉取类似免责声明等一些必要信息
        ProgramStatusType_NessaryDataMissing = 8; //缺少必要信息
        ProgramStatusType_UnAgreeDisclaimer = 9; //未同意免责声明
        ProgramStatusType_Ready = 10; //可以接收业务协议收发,正常可用状态
    	
        //OpenD 登录后被强制退出登录，会导致连接全部断开,需要重连后才能得到以下该状态（并且需要在 ui 模式下）
        ProgramStatusType_ForceLogout = 11; //被强制退出登录,例如修改了登录密码,中途打开设备锁等,详细原因在描述返回
    
        ProgramStatusType_DisclaimerPullFailed = 12; //拉取免责声明标志失败
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

**ProgramStatusType**

    enum ProgramStatusType
    {
        ProgramStatusType_None = 0;
        ProgramStatusType_Loaded = 1; //已完成类似加载配置,启动服务器等操作,服务器启动之前的状态无需返回
    
        ProgramStatusType_Loging = 2; //登录中
        ProgramStatusType_NeedPicVerifyCode = 3; //需要图形验证码
        ProgramStatusType_NeedPhoneVerifyCode = 4; //需要手机验证码
        ProgramStatusType_LoginFailed = 5; //登录失败,详细原因在描述返回
        ProgramStatusType_ForceUpdate = 6; //客户端版本过低
    
        ProgramStatusType_NessaryDataPreparing = 7; //正在拉取类似免责声明等一些必要信息
        ProgramStatusType_NessaryDataMissing = 8; //缺少必要信息
        ProgramStatusType_UnAgreeDisclaimer = 9; //未同意免责声明
        ProgramStatusType_Ready = 10; //可以接收业务协议收发,正常可用状态
    	
        //OpenD 登录后被强制退出登录，会导致连接全部断开,需要重连后才能得到以下该状态（并且需要在 ui 模式下）
        ProgramStatusType_ForceLogout = 11; //被强制退出登录,例如修改了登录密码,中途打开设备锁等,详细原因在描述返回
    
        ProgramStatusType_DisclaimerPullFailed = 12; //拉取免责声明标志失败
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

[#](./ftapi_common.md#7799)
 网关事件通知类型
-----------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **GtwEventType**

*   `LocalCfgLoadFailed`
    
    本地配置文件加载失败
    
*   `APISvrRunFailed`
    
    网关监听服务运行失败
    
*   `ForceUpdate`
    
    强制升级网关
    
*   `LoginFailed`
    
    登录富途服务器失败
    
*   `UnAgreeDisclaimer`
    
    未同意免责声明，无法运行
    
*   `LOGIN_FAILED`
    
    登录失败
    
*   `NetCfgMissing`
    
    缺少网络连接配置
    
*   `KickedOut`
    
    登录被踢下线
    
*   `LoginPwdChanged`
    
    登录密码变更
    
*   `BanLogin`
    
    牛牛后台不允许该账号登录
    
*   `NeedPicVerifyCode`
    
    登录需要输入图形验证码
    
*   `NeedPhoneVerifyCode`
    
    登录需要输入手机验证码
    
*   `AppDataNotExist`
    
    程序打包数据丢失
    
*   `NessaryDataMissing`
    
    必要的数据没同步成功
    
*   `TradePwdChanged`
    
    交易密码变更通知
    
*   `EnableDeviceLock`
    
    需启用设备锁
    

**GtwEventType**

    enum GtwEventType
    {
        GtwEventType_None = 0; //正常无错
        GtwEventType_LocalCfgLoadFailed	= 1; //加载本地配置失败
        GtwEventType_APISvrRunFailed = 2; //服务器启动失败
    	  GtwEventType_ForceUpdate = 3; //客户端版本过低
    	  GtwEventType_LoginFailed = 4; //登录失败
    	  GtwEventType_UnAgreeDisclaimer = 5; //未同意免责声明
    	  GtwEventType_NetCfgMissing = 6; //缺少必要网络配置信息;例如控制订阅额度 //已优化，不会再出现该情况
    	  GtwEventType_KickedOut = 7; //牛牛帐号在别处登录
    	  GtwEventType_LoginPwdChanged = 8; //登录密码被修改
    	  GtwEventType_BanLogin = 9; //用户被禁止登录
    	  GtwEventType_NeedPicVerifyCode = 10; //需要图形验证码
    	  GtwEventType_NeedPhoneVerifyCode = 11; //需要手机验证码
    	  GtwEventType_AppDataNotExist = 12; //程序自带数据不存在
    	  GtwEventType_NessaryDataMissing = 13; //缺少必要数据
    	  GtwEventType_TradePwdChanged = 14; //交易密码被修改
    	  GtwEventType_EnableDeviceLock = 15; //启用设备锁
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

**GtwEventType**

    enum GtwEventType
    {
        GtwEventType_None = 0; //正常无错
        GtwEventType_LocalCfgLoadFailed	= 1; //加载本地配置失败
        GtwEventType_APISvrRunFailed = 2; //服务器启动失败
    	  GtwEventType_ForceUpdate = 3; //客户端版本过低
    	  GtwEventType_LoginFailed = 4; //登录失败
    	  GtwEventType_UnAgreeDisclaimer = 5; //未同意免责声明
    	  GtwEventType_NetCfgMissing = 6; //缺少必要网络配置信息;例如控制订阅额度 //已优化，不会再出现该情况
    	  GtwEventType_KickedOut = 7; //牛牛帐号在别处登录
    	  GtwEventType_LoginPwdChanged = 8; //登录密码被修改
    	  GtwEventType_BanLogin = 9; //用户被禁止登录
    	  GtwEventType_NeedPicVerifyCode = 10; //需要图形验证码
    	  GtwEventType_NeedPhoneVerifyCode = 11; //需要手机验证码
    	  GtwEventType_AppDataNotExist = 12; //程序自带数据不存在
    	  GtwEventType_NessaryDataMissing = 13; //缺少必要数据
    	  GtwEventType_TradePwdChanged = 14; //交易密码被修改
    	  GtwEventType_EnableDeviceLock = 15; //启用设备锁
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

**GtwEventType**

    enum GtwEventType
    {
        GtwEventType_None = 0; //正常无错
        GtwEventType_LocalCfgLoadFailed	= 1; //加载本地配置失败
        GtwEventType_APISvrRunFailed = 2; //服务器启动失败
    	  GtwEventType_ForceUpdate = 3; //客户端版本过低
    	  GtwEventType_LoginFailed = 4; //登录失败
    	  GtwEventType_UnAgreeDisclaimer = 5; //未同意免责声明
    	  GtwEventType_NetCfgMissing = 6; //缺少必要网络配置信息;例如控制订阅额度 //已优化，不会再出现该情况
    	  GtwEventType_KickedOut = 7; //牛牛帐号在别处登录
    	  GtwEventType_LoginPwdChanged = 8; //登录密码被修改
    	  GtwEventType_BanLogin = 9; //用户被禁止登录
    	  GtwEventType_NeedPicVerifyCode = 10; //需要图形验证码
    	  GtwEventType_NeedPhoneVerifyCode = 11; //需要手机验证码
    	  GtwEventType_AppDataNotExist = 12; //程序自带数据不存在
    	  GtwEventType_NessaryDataMissing = 13; //缺少必要数据
    	  GtwEventType_TradePwdChanged = 14; //交易密码被修改
    	  GtwEventType_EnableDeviceLock = 15; //启用设备锁
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

**GtwEventType**

    enum GtwEventType
    {
        GtwEventType_None = 0; //正常无错
        GtwEventType_LocalCfgLoadFailed	= 1; //加载本地配置失败
        GtwEventType_APISvrRunFailed = 2; //服务器启动失败
    	  GtwEventType_ForceUpdate = 3; //客户端版本过低
    	  GtwEventType_LoginFailed = 4; //登录失败
    	  GtwEventType_UnAgreeDisclaimer = 5; //未同意免责声明
    	  GtwEventType_NetCfgMissing = 6; //缺少必要网络配置信息;例如控制订阅额度 //已优化，不会再出现该情况
    	  GtwEventType_KickedOut = 7; //牛牛帐号在别处登录
    	  GtwEventType_LoginPwdChanged = 8; //登录密码被修改
    	  GtwEventType_BanLogin = 9; //用户被禁止登录
    	  GtwEventType_NeedPicVerifyCode = 10; //需要图形验证码
    	  GtwEventType_NeedPhoneVerifyCode = 11; //需要手机验证码
    	  GtwEventType_AppDataNotExist = 12; //程序自带数据不存在
    	  GtwEventType_NessaryDataMissing = 13; //缺少必要数据
    	  GtwEventType_TradePwdChanged = 14; //交易密码被修改
    	  GtwEventType_EnableDeviceLock = 15; //启用设备锁
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

**GtwEventType**

    enum GtwEventType
    {
        GtwEventType_None = 0; //正常无错
        GtwEventType_LocalCfgLoadFailed	= 1; //加载本地配置失败
        GtwEventType_APISvrRunFailed = 2; //服务器启动失败
    	  GtwEventType_ForceUpdate = 3; //客户端版本过低
    	  GtwEventType_LoginFailed = 4; //登录失败
    	  GtwEventType_UnAgreeDisclaimer = 5; //未同意免责声明
    	  GtwEventType_NetCfgMissing = 6; //缺少必要网络配置信息;例如控制订阅额度 //已优化，不会再出现该情况
    	  GtwEventType_KickedOut = 7; //牛牛帐号在别处登录
    	  GtwEventType_LoginPwdChanged = 8; //登录密码被修改
    	  GtwEventType_BanLogin = 9; //用户被禁止登录
    	  GtwEventType_NeedPicVerifyCode = 10; //需要图形验证码
    	  GtwEventType_NeedPhoneVerifyCode = 11; //需要手机验证码
    	  GtwEventType_AppDataNotExist = 12; //程序自带数据不存在
    	  GtwEventType_NessaryDataMissing = 13; //缺少必要数据
    	  GtwEventType_TradePwdChanged = 14; //交易密码被修改
    	  GtwEventType_EnableDeviceLock = 15; //启用设备锁
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

[#](./ftapi_common.md#5896)
 系统通知类型
---------------------------------------------------------------------------

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

> **SysNotifyType**

*   `GTW_EVENT`
    
    网关事件
    
*   `PROGRAM_STATUS`
    
    程序状态变化
    
*   `CONN_STATUS`
    
    与后台服务的连接状态变化
    
*   `QOT_RIGHT`
    
    行情权限变化
    

**NotifyType**

    enum NotifyType
    {
        NotifyType_None = 0; //无
    	  NotifyType_GtwEvent = 1; //OpenD 运行事件通知
    	  NotifyType_ProgramStatus = 2; //程序状态
    	  NotifyType_ConnStatus = 3; //连接状态
    	  NotifyType_QotRight = 4; //行情权限
    	  NotifyType_APILevel = 5; //用户等级，已在2.10版本之后废弃
    	  NotifyType_APIQuota = 6; //API 额度
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

**NotifyType**

    enum NotifyType
    {
        NotifyType_None = 0; //无
    	  NotifyType_GtwEvent = 1; //OpenD 运行事件通知
    	  NotifyType_ProgramStatus = 2; //程序状态
    	  NotifyType_ConnStatus = 3; //连接状态
    	  NotifyType_QotRight = 4; //行情权限
    	  NotifyType_APILevel = 5; //用户等级，已在2.10版本之后废弃
    	  NotifyType_APIQuota = 6; //API 额度
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

**NotifyType**

    enum NotifyType
    {
        NotifyType_None = 0; //无
    	  NotifyType_GtwEvent = 1; //OpenD 运行事件通知
    	  NotifyType_ProgramStatus = 2; //程序状态
    	  NotifyType_ConnStatus = 3; //连接状态
    	  NotifyType_QotRight = 4; //行情权限
    	  NotifyType_APILevel = 5; //用户等级，已在2.10版本之后废弃
    	  NotifyType_APIQuota = 6; //API 额度
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

**NotifyType**

    enum NotifyType
    {
        NotifyType_None = 0; //无
    	  NotifyType_GtwEvent = 1; //OpenD 运行事件通知
    	  NotifyType_ProgramStatus = 2; //程序状态
    	  NotifyType_ConnStatus = 3; //连接状态
    	  NotifyType_QotRight = 4; //行情权限
    	  NotifyType_APILevel = 5; //用户等级，已在2.10版本之后废弃
    	  NotifyType_APIQuota = 6; //API 额度
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

**NotifyType**

    enum NotifyType
    {
        NotifyType_None = 0; //无
    	  NotifyType_GtwEvent = 1; //OpenD 运行事件通知
    	  NotifyType_ProgramStatus = 2; //程序状态
    	  NotifyType_ConnStatus = 3; //连接状态
    	  NotifyType_QotRight = 4; //行情权限
    	  NotifyType_APILevel = 5; //用户等级，已在2.10版本之后废弃
    	  NotifyType_APIQuota = 6; //API 额度
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

[#](./ftapi_common.md#4068)
 包唯一标识
--------------------------------------------------------------------------

**PacketID**

    message PacketID
    {
    	  required uint64 connID = 1; //当前 TCP 连接的连接 ID，一条连接的唯一标识，InitConnect 协议会返回
    	  required uint32 serialNo = 2; //自增序列号
    }
    

1  
2  
3  
4  
5  

[#](./ftapi_common.md#6783)
 程序状态
-------------------------------------------------------------------------

**ProgramStatus**

    message ProgramStatus
    {
    	  required ProgramStatusType type = 1; //当前状态
    	  optional string strExtDesc = 2; // 额外描述
    }
    

1  
2  
3  
4  
5  

← [基础功能](./ftapi_init.md) [底层协议介绍](./ftapi_protocol.md)
 →

[通用定义](./ftapi_common.md)

*   [接口调用结果](./ftapi_common.md#7467)
    
*   [协议格式](./ftapi_common.md#1222)
    
*   [包加密算法](./ftapi_common.md#9178)
    
*   [程序状态类型](./ftapi_common.md#6427)
    
*   [网关事件通知类型](./ftapi_common.md#7799)
    
*   [系统通知类型](./ftapi_common.md#5896)
    
*   [包唯一标识](./ftapi_common.md#4068)
    
*   [程序状态](./ftapi_common.md#6783)