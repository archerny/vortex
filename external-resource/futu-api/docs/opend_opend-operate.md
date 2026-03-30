[#](./opend_opend-operate.md#9189)
 运维命令
================================================================================

通过命令行或者 Telnet 发送命令可以对 OpenD 做运维操作。

通过命令行或者 Telnet 发送命令可以对 OpenD 做运维操作。

命令格式：`cmd -param_key1=param_value1 -param_key2=param_value2`

以 `help -cmd=exit` 为例，介绍Telnet的用法：

1.  在OpenD启动参数中，配置好 Telnet 地址和 Telnet 端口。 ![telnet_GUI](https://openapi.futunn.com/futu-api-doc/assets/img/telnet_GUI.51da2b56.jpg) ![telnet_CMD](https://openapi.futunn.com/futu-api-doc/assets/img/telnet_CMD.b0d3e174.jpg)
2.  启动 OpenD（会同时启动 Telnet）。
3.  通过 Telnet，向 OpenD 发送 `help -cmd=exit` 命令。

    from telnetlib import Telnet
    with Telnet('127.0.0.1', 22222) as tn:  # Telnet 地址为：127.0.0.1，Telnet 端口为：22222
        tn.write(b'help -cmd=exit\r\n')
        reply = b''
        while True:
            msg = tn.read_until(b'\r\n', timeout=0.5)
            reply += msg
            if msg == b'':
                break
        print(reply.decode('gb2312'))
    

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

[#](./opend_opend-operate.md#6520)
 命令帮助
--------------------------------------------------------------------------------

`help -cmd=exit`

查看指定命令详细信息，不指定参数则输出命令列表

*   参数:
    *   cmd: 命令

[#](./opend_opend-operate.md#3261)
 退出程序
--------------------------------------------------------------------------------

`exit`

退出 OpenD 程序

退出 OpenD 程序

[#](./opend_opend-operate.md#2972)
 请求手机验证码
-----------------------------------------------------------------------------------

`req_phone_verify_code`

请求手机验证码，当启用设备锁并初次在该设备登录，要求做安全验证。

*   频率限制:
    *   每60秒内最多请求1次

[#](./opend_opend-operate.md#815)
 输入手机验证码
----------------------------------------------------------------------------------

`input_phone_verify_code -code=123456`

输入手机验证码，并继续登录流程。

*   参数:
    
    *   code: 手机验证码
*   频率限制:
    
    *   每60秒内最多请求10次

[#](./opend_opend-operate.md#5593)
 请求图形验证码
-----------------------------------------------------------------------------------

`req_pic_verify_code`

请求图形验证码，当多次输入错登录密码时，需要输入图形验证码。

*   频率限制:
    *   每60秒内最多请求10次

[#](./opend_opend-operate.md#3827)
 输入图形验证码
-----------------------------------------------------------------------------------

`input_pic_verify_code -code=1234`

输入图形验证码，并继续登录流程。

*   参数:
    
    *   code: 图形验证码
*   频率限制:
    
    *   每60秒内最多请求10次

[#](./opend_opend-operate.md#10)
 重登录
-----------------------------------------------------------------------------

`relogin -login_pwd=123456`

当登录密码修改或中途打开设备锁等情况，要求用户重新登录时，可以使用该命令。只能重登当前帐号，不支持切换帐号。 密码参数主要用于登录密码修改的情况，不指定密码则使用启动时登录密码。

*   参数:
    
    *   login\_pwd: 登录密码明文
        
    *   login\_pwd\_md5: 登录密码密文（32 位 MD5 加密 16 进制）
        
*   频率限制:
    
    *   每小时最多请求10次

[#](./opend_opend-operate.md#8474)
 检测与连接点之间的时延
---------------------------------------------------------------------------------------

`ping`

检测与连接点之前的时延

*   频率限制:
    *   每60秒内最多请求10次

[#](./opend_opend-operate.md#6956)
 展示延迟统计报告
------------------------------------------------------------------------------------

`show_delay_report -detail_report_path=D:/detail.txt -push_count_type=sr2cs`

展示延迟统计报告，包括推送延迟，请求延迟以及下单延迟。每日北京时间 6:00 清理数据。

*   参数:
    *   detail\_report\_path: 文件输出路径（MAC 系统仅支持绝对路径，不支持相对路径），可选参数，若不指定则输出到控制台
        
    *   Paramters: push\_count\_type: 推送延迟的类型(sr2ss，ss2cr，cr2cs，ss2cs，sr2cs)，默认 sr2cs。
        
        *   sr 指服务器接收时间(目前只有港股支持该时间)
        *   ss 指服务器发出时间
        *   cr 指 OpenD 接收时间
        *   cs 指 OpenD 发出时间

*   参数:
    *   detail\_report\_path: 文件输出路径（MAC 系统仅支持绝对路径，不支持相对路径），可选参数，若不指定则输出到控制台
        
    *   Paramters: push\_count\_type: 推送延迟的类型(sr2ss，ss2cr，cr2cs，ss2cs，sr2cs)，默认 sr2cs。
        
        *   sr 指服务器接收时间(目前只有港股支持该时间)
        *   ss 指服务器发出时间
        *   cr 指 OpenD 接收时间
        *   cs 指 OpenD 发出时间

[#](./opend_opend-operate.md#4679)
 关闭 API 连接
-------------------------------------------------------------------------------------

`close_api_conn -conn_id=123456`

关闭某条 API 连接，若不指定则关闭所有

*   参数:
    *   conn\_id: API 连接 ID

[#](./opend_opend-operate.md#3131)
 展示订阅状态
----------------------------------------------------------------------------------

`show_sub_info -conn_id=123456 -sub_info_path=D:/detail.txt`

展示某条连接的订阅状态，若不指定则展示所有

*   参数:
    *   conn\_id: API 连接 ID
        
    *   sub\_info\_path: 文件输出路径（MAC 系统仅支持绝对路径，不支持相对路径），可选参数，若不指定则输出到控制台
        

[#](./opend_opend-operate.md#2397)
 请求最高行情权限
------------------------------------------------------------------------------------

`request_highest_quote_right`

当高级行情权限被其他设备（如：桌面端/手机端）占用时，可使用该命令重新请求最高行情权限（届时，其他处于登录状态的设备将无法使用高级行情）。

*   频率限制:
    *   每60秒内最多请求10次

[#](./opend_opend-operate.md#479)
 升级
-----------------------------------------------------------------------------

`update`

运行该命令，可以一键更新 OpenD

运行该命令，可以一键更新 OpenD

← [命令行 OpenD](./opend_opend-cmd.md) [行情接口总览](./quote_overview.md)
 →

[运维命令](./opend_opend-operate.md)

*   [命令帮助](./opend_opend-operate.md#6520)
    
*   [退出程序](./opend_opend-operate.md#3261)
    
*   [请求手机验证码](./opend_opend-operate.md#2972)
    
*   [输入手机验证码](./opend_opend-operate.md#815)
    
*   [请求图形验证码](./opend_opend-operate.md#5593)
    
*   [输入图形验证码](./opend_opend-operate.md#3827)
    
*   [重登录](./opend_opend-operate.md#10)
    
*   [检测与连接点之间的时延](./opend_opend-operate.md#8474)
    
*   [展示延迟统计报告](./opend_opend-operate.md#6956)
    
*   [关闭 API 连接](./opend_opend-operate.md#4679)
    
*   [展示订阅状态](./opend_opend-operate.md#3131)
    
*   [请求最高行情权限](./opend_opend-operate.md#2397)
    
*   [升级](./opend_opend-operate.md#479)