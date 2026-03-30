[#](./qa_opend.md#8657)
 OpenD 相关
=========================================================================

[#](./qa_opend.md#3374)
 Q1：OpenD 因未完成“问卷评估及协议确认”自动退出
---------------------------------------------------------------------------------------------

A: 您需要进行相关问卷评估及协议确认，才可以使用 OpenD，请先 [前往完成](https://www.futunn.com/about/api-disclaimer?lang=zh-CN)
 。

A: 您需要进行相关问卷评估及协议确认，才可以使用 OpenD，请先 [前往完成](https://www.moomoo.com/zh-cn/about/api-disclaimer)
 。

[#](./qa_opend.md#1063)
 Q2：OpenD 因”程序自带数据不存在“退出
----------------------------------------------------------------------------------------

A: 一般因权限问题导致自带数据拷贝失败，可以尝试将程序目录下 **_Appdata.dat_** 解压后的文件拷贝到程序数据目录下。

*   windows 程序数据目录:`%appdata%/com.futunn.FutuOpenD/F3CNN`
*   非 windows 程序数据目录:`~/.com.futunn.FutuOpenD/F3CNN`

*   windows 程序数据目录:`%appdata%/com.moomoo.OpenD/F3CNN`
*   非 windows 程序数据目录:`~/.com.moomoo.OpenD/F3CNN`

[#](./qa_opend.md#6028)
 Q3：OpenD 服务启动失败
--------------------------------------------------------------------------------

A: 请检查：

1.  是否有其他程序占用所配置的端口；
2.  是否已经有配置了相同端口的 OpenD 在运行。

[#](./qa_opend.md#4568)
 Q4：如何验证手机验证码？
------------------------------------------------------------------------------

A: 在 OpenD 界面上或远程到 Telnet 端口，输入命令`input_phone_verify_code -code=123456`。

提示

*   123456 是收到的手机验证码
*   \-code=123456 前有空格

[#](./qa_opend.md#8275)
 Q5：是否支持其他编程语言？
-------------------------------------------------------------------------------

A: OpenD 有对外提供基于 socket 的协议，目前我们提供并维护 Python，C++，Java，C# 和 JavaScript 接口，[下载入口](https://www.futunn.com/download/OpenAPI)
 。

A: OpenD 有对外提供基于 socket 的协议，目前我们提供并维护 Python，C++，Java，C# 和 JavaScript 接口，[下载入口](https://www.moomoo.com/hans/download/OpenAPI)
 。

如果上述语言仍不能满足您的需求，您可以自行对接 Protobuf 协议。

[#](./qa_opend.md#6993)
 Q6：在同一设备多次验证设备锁
--------------------------------------------------------------------------------

A: 设备标识随机生成并存放于

windows: %appdata%/com.futunn.FutuOpenD/F3CNN/Device.dat 文件中。 非windows: ~/.com.futunn.FutuOpenD/F3CNN/Device.dat

提示

1.  如果文件被删除或损坏，OpenD 会重新生成新设备标识，然后验证设备锁。
2.  另外镜像拷贝部署的用户需要注意，如果多台机器的 Device.dat 内容相同，也会导致这些机器多次验证设备锁。删除 Device.dat 文件即可解决。

A: 设备标识随机生成并存放于

windows: %appdata%/com.moomoo.OpenD/F3CNN/Device.dat 文件中。 非windows: ~/.com.moomoo.OpenD/F3CNN/Device.dat

提示

1.  如果文件被删除或损坏，OpenD 会重新生成新设备标识，然后验证设备锁。
2.  另外镜像拷贝部署的用户需要注意，如果多台机器的 Device.dat 内容相同，也会导致这些机器多次验证设备锁。删除 Device.dat 文件即可解决。

[#](./qa_opend.md#7137)
 Q7：OpenD 是否有提供 Docker 镜像？
------------------------------------------------------------------------------------------

A: 目前没有提供。

[#](./qa_opend.md#603)
 Q8：一个账号可以登录多个 OpenD 吗？
--------------------------------------------------------------------------------------

A: 一个账号可以在多台机器上登录 OpenD 或者其他客户终端，最多允许 10 个 OpenD 终端同时登录。同时有“行情互踢”的限制，只能有一个 OpenD 获得最高权限行情。例如：两个终端登录同一个账号，只能有一个港股 LV2 行情，另一个是港股 BMP 行情。

[#](./qa_opend.md#6774)
 Q9：如何控制 OpenD 和其他客户端（桌面端和移动端）的行情权限？
----------------------------------------------------------------------------------------------------

A: 应交易所的规定，多个终端同时在线会有“行情互踢”的限制，只能有一个终端获得最高权限行情。OpenD 命令行版本的启动参数中，内置了 [auto\_hold\_quote\_right](./opend_opend-cmd.md#8799)
 参数，用于灵活配置行情权限。当该参数选项开启时，OpenD 在行情权限被抢后，会自动抢回。如果 10 秒内再次被抢，则其他终端获得最高行情权限（OpenD 不会再抢）。

[#](./qa_opend.md#7890)
 Q10：如何优先保证 OpenD 行情权限？
---------------------------------------------------------------------------------------

A:

1.  将 OpenD 启动参数 [auto\_hold\_quote\_right](./opend_opend-cmd.md#8799)
     配置为 1；
2.  保证不要在移动端或桌面端富途牛牛上在 10 秒内连续两次抢最高权限（登录算一次，点击“重启行情”算第二次）。

![quote-right-kick](<Base64-Image-Removed>)

[#](./qa_opend.md#9034)
 Q11：如何优先保证移动端（或桌面端）的行情权限？
------------------------------------------------------------------------------------------

A: OpenD 启动参数 [auto\_hold\_quote\_right](./opend_opend-cmd.md#8799)
 设置为 0，移动端或桌面端富途牛牛在 OpenD 之后登录即可。

[#](./qa_opend.md#7890-2)
 Q12：使用可视化 OpenD 记住密码登录，长时间挂机后提示连接断开，需要重新登录？
--------------------------------------------------------------------------------------------------------------

A: 使用可视化 OpenD，如果选择记住密码登录，用的是记录在本地的令牌。由于令牌有时间限制，当令牌过期后，如果出现网络波动或富途后台发布，就可能导致与后台断开连接后无法自动连接上的情况。因此，可视化 OpenD 如果希望长时间挂机，建议手动输入密码登录，由 OpenD 自动处理该情况。

[#](./qa_opend.md#3061)
 Q13：遇到产品缺陷，如何请富途的研发工程师排查日志？
--------------------------------------------------------------------------------------------

A:

1.  与客服沟通问题表现，详述：发生错误的时间、OpenD 版本号、 API 版本号、脚本语言名、接口名或协议号、含详细入参和返回的短代码或截图。
    
2.  客服确认是产品缺陷后，如需进一步日志排查，研发工程师会主动联系。
    
3.  部分问题须提供 OpenD 日志，方便定位确认问题。交易类问题需要 info 日志级别，行情类问题需要 debug 日志级别。日志级别 log\_level 可以在 **_OpenD.xml_** 中 [配置](./opend_opend-cmd.md#8799)
     ，配置后需要重启 OpenD 方能生效，待问题复现后，将该段日志打包发给富途研发工程师。
    

提示

日志路径如下：  
windows：`%appdata%/com.futunn.FutuOpenD/Log`

非 windows：`~/.com.futunn.FutuOpenD/Log`

提示

日志路径如下：  
windows：`%appdata%/com.moomoo.OpenD/Log`

非 windows：`~/.com.moomoo.OpenD/Log`

[#](./qa_opend.md#5926)
 Q14：脚本连接不上 OpenD
---------------------------------------------------------------------------------

A: 请先尝试检查：

1.  脚本连接的端口与 OpenD 配置的端口是否一致。
2.  由于 OpenD 连接上限为 128，是否有无用连接未关闭。
3.  检查监听地址是否正确，如果脚本和 OpenD 不在同一机器，OpenD 监听地址需要设置成 0.0.0.0 。

[#](./qa_opend.md#5349)
 Q15：连接上一段时间后断开
-------------------------------------------------------------------------------

A: 如果是自己对接协议，检查下是否有定时发送心跳维持连接。

[#](./qa_opend.md#8485)
 Q16：Linux 下通过 multiprocessing 模块以多进程方式运行 Python 脚本，连不上 OpenD？
------------------------------------------------------------------------------------------------------------------------------

A: Linux/Mac 环境下以默认方式创建进程后，父进程中 py-futu-api 内部创建的线程将会在子进程中消失，导致程序内部状态错误。  
可以用 spawn 方式来启动进程：

    import multiprocessing as mp
    mp.set_start_method('spawn')
    p = mp.Process(target=func)
    

1  
2  
3  

A: Linux/Mac 环境下以默认方式创建进程后，父进程中 py-moomoo-api 内部创建的线程将会在子进程中消失，导致程序内部状态错误。  
可以用 spawn 方式来启动进程：

    import multiprocessing as mp
    mp.set_start_method('spawn')
    p = mp.Process(target=func)
    

1  
2  
3  

[#](./qa_opend.md#9389)
 Q17：如何在一台电脑同时登录两个 OpenD?
-----------------------------------------------------------------------------------------

A: 可视化 OpenD 不支持，命令行 OpenD 支持。

1.  解压从官网下载的文件，复制整个命令行 OpenD 文件夹（如 OpenD\_5.2.1408\_Windows）得到副本（此处以 Windows 为例，其他系统可采取相同操作）。

![file-page](https://openapi.futunn.com/futu-api-doc/assets/img/nnfile-page.db81cd3e.png)

2.  分别打开两个命令行 OpenD 文件夹配置好两份 OpenD.xml 文件。

第一份配置文件参数：api\_port = 11111，login\_account = 登录账号1，login\_pwd = 登录密码1

第二份配置文件参数：api\_port = 11112，login\_account = 登录账号2，login\_pwd = 登录密码2

![order-page](https://openapi.futunn.com/futu-api-doc/assets/img/nnorder-page.9716528d.png)

3.  配置完成后，分别打开两个 OpenD 程序运行。

![fod-page](https://openapi.futunn.com/futu-api-doc/assets/img/nnfod-page.28f5db58.png)

4.  调用接口时，注意接口的参数`port`（OpenD 监听端口）与 OpenD.xml 文件中的参数`api_port`为对应关系  
    例如：

    from futu import *
    
    # 向账号1登录的 OpenD 进行请求
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111, is_encrypt=False)
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    
    # 向账号2登录的 OpenD 进行请求
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11112, is_encrypt=False)
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

A: 可视化 OpenD 不支持，命令行 OpenD 支持。

1.  解压从官网下载的文件，复制整个命令行 OpenD 文件夹（如 OpenD\_5.2.1408\_Windows）得到副本（此处以 Windows 为例，其他系统可采取相同操作）。

![file-page](https://openapi.futunn.com/futu-api-doc/assets/img/mmfile-page.7ff92ec2.png)

2.  分别打开两个命令行 OpenD 文件夹配置好两份 OpenD.xml 文件。

第一份配置文件参数：api\_port = 11111，login\_account = 登录账号1，login\_pwd = 登录密码1

第二份配置文件参数：api\_port = 11112，login\_account = 登录账号2，login\_pwd = 登录密码2

![order-page](https://openapi.futunn.com/futu-api-doc/assets/img/order-page.5d13af50.png)

3.  配置完成后，分别打开两个 OpenD 程序运行。

![fod-page](https://openapi.futunn.com/futu-api-doc/assets/img/mmfod-page.086dc7c1.png)

4.  调用接口时，注意接口的参数`port`（OpenD 监听端口）与 OpenD.xml 文件中的参数`api_port`为对应关系  
    例如：

    from moomoo import *
    
    # 向账号1登录的 OpenD 进行请求
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111, is_encrypt=False)
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    
    # 向账号2登录的 OpenD 进行请求
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11112, is_encrypt=False)
    quote_ctx.close() # 结束后记得关闭当条连接，防止连接条数用尽
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

[#](./qa_opend.md#1228)
 Q18：行情权限被其他客户端踢掉，如何通过脚本执行抢权限的运维命令？
---------------------------------------------------------------------------------------------------

A：

1.  在OpenD启动参数中，配置好 Telnet 地址和 Telnet 端口。 ![telnet_GUI](https://openapi.futunn.com/futu-api-doc/assets/img/telnet_GUI.51da2b56.jpg) ![telnet_CMD](https://openapi.futunn.com/futu-api-doc/assets/img/telnet_CMD.b0d3e174.jpg)
2.  启动 OpenD（会同时启动 Telnet）。
3.  当发现行情权限被抢之后，您可以参考如下代码示例，通过 Telnet，向 OpenD 发送 `request_highest_quote_right` 命令。

    from telnetlib import Telnet
    with Telnet('127.0.0.1', 22222) as tn:  # Telnet 地址为：127.0.0.1，Telnet 端口为：22222
        tn.write(b'request_highest_quote_right\r\n')
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

[#](./qa_opend.md#1979)
 Q19：OpenD 自动升级失败
---------------------------------------------------------------------------------

A： 通过`update`命令执行 OpenD 自动更新失败，可能的原因：

*   文件被其他进程占用：可以尝试关闭其他 OpenD 进程，或者重启系统后，再次执行 `update` 如果以上仍无法解决，可以通过[官网](https://www.futunn.com/download/OpenAPI?lang=zh-CN)
     自行下载更新。

A： 通过`update`命令执行 OpenD 自动更新失败，可能的原因：

*   文件被其他进程占用：可以尝试关闭其他 OpenD 进程，或者重启系统后，再次执行 `update` 如果以上仍无法解决，可以通过[官网](https://www.moomoo.com/hans/download/OpenAPI)
     自行下载更新。

[#](./qa_opend.md#4529)
 Q20：ubuntu22无法启动可视化 OpenD？
-------------------------------------------------------------------------------------------

A： 在有些Linux发行版（例如Ubuntu 22.04）运行可视化OpenD时，可能会提示：`dlopen(): error loading libfuse.so.2`。 这是因为这些系统没有默认安装libfuse。通常可以手动安装来解决，例如对于Ubuntu22.04，可以在命令行运行：

    sudo apt update
    sudo apt install -y libfuse2
    

1  
2  

安装成功后就可以正常运行可视化OpenD了。详细信息请参考：[https://docs.appimage.org/user-guide/troubleshooting/fuse.html](https://docs.appimage.org/user-guide/troubleshooting/fuse.html)
 。

[#](./qa_opend.md#1092)
 Q21：Linux上如何在后台运行命令行OpenD？
-------------------------------------------------------------------------------------------

A：先切到 FutuOpenD 所在目录，配置好 FutuOpenD.xml 之后，执行如下命令

    nohup ./FutuOpenD &
    

1  

A：先切到 OpenD 所在目录，配置好 OpenD.xml 之后，执行如下命令

    nohup ./moomoo OpenD &
    

1  

← [底层协议介绍](./ftapi_protocol.md) [行情相关](./qa_quote.md)
 →

[OpenD 相关](./qa_opend.md)

*   [Q1：OpenD 因未完成“问卷评估及协议确认”自动退出](./qa_opend.md#3374)
    
*   [Q2：OpenD 因”程序自带数据不存在“退出](./qa_opend.md#1063)
    
*   [Q3：OpenD 服务启动失败](./qa_opend.md#6028)
    
*   [Q4：如何验证手机验证码？](./qa_opend.md#4568)
    
*   [Q5：是否支持其他编程语言？](./qa_opend.md#8275)
    
*   [Q6：在同一设备多次验证设备锁](./qa_opend.md#6993)
    
*   [Q7：OpenD 是否有提供 Docker 镜像？](./qa_opend.md#7137)
    
*   [Q8：一个账号可以登录多个 OpenD 吗？](./qa_opend.md#603)
    
*   [Q9：如何控制 OpenD 和其他客户端（桌面端和移动端）的行情权限？](./qa_opend.md#6774)
    
*   [Q10：如何优先保证 OpenD 行情权限？](./qa_opend.md#7890)
    
*   [Q11：如何优先保证移动端（或桌面端）的行情权限？](./qa_opend.md#9034)
    
*   [Q12：使用可视化 OpenD 记住密码登录，长时间挂机后提示连接断开，需要重新登录？](./qa_opend.md#7890-2)
    
*   [Q13：遇到产品缺陷，如何请富途的研发工程师排查日志？](./qa_opend.md#3061)
    
*   [Q14：脚本连接不上 OpenD](./qa_opend.md#5926)
    
*   [Q15：连接上一段时间后断开](./qa_opend.md#5349)
    
*   [Q16：Linux 下通过 multiprocessing 模块以多进程方式运行 Python 脚本，连不上 OpenD？](./qa_opend.md#8485)
    
*   [Q17：如何在一台电脑同时登录两个 OpenD?](./qa_opend.md#9389)
    
*   [Q18：行情权限被其他客户端踢掉，如何通过脚本执行抢权限的运维命令？](./qa_opend.md#1228)
    
*   [Q19：OpenD 自动升级失败](./qa_opend.md#1979)
    
*   [Q20：ubuntu22无法启动可视化 OpenD？](./qa_opend.md#4529)
    
*   [Q21：Linux上如何在后台运行命令行OpenD？](./qa_opend.md#1092)