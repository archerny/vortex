 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/unlock.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/unlock.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/unlock.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/unlock.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/unlock.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/unlock.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
*   交易接口
    
    *   [交易接口总览](https://openapi.futunn.com/futu-api-doc/trade/overview.html)
        
    *   [交易对象](https://openapi.futunn.com/futu-api-doc/trade/base.html)
        
    *   账户
        
        *   [获取交易业务账户列表](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html)
            
        *   [解锁交易](https://openapi.futunn.com/futu-api-doc/trade/unlock.html)
            
        
    *   资产持仓
        
    *   订单
        
    *   成交
        
    *   [交易定义](https://openapi.futunn.com/futu-api-doc/trade/trade.html)
        
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/trade/unlock.html#5832)
 解锁交易
=========================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`unlock_trade(password=None, password_md5=None, is_unlock=True)`

*   **介绍**
    
    解锁或锁定交易
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | password | str | 交易密码<br>(ℹ️ 如果 password\_md5 不为空，就使用传入的 password\_md5 解锁；否则使用 password 转 MD5 得到 password\_md5 再解锁) |
    | password\_md5 | str | 交易密码的 32 位 MD5 加密（全小写）<br>(ℹ️ 解锁交易必须要填密码，锁定交易忽略) |
    | is\_unlock | bool | 解锁或锁定<br>(ℹ️ True：解锁  <br>False：锁定) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | msg | NoneType | 当 ret == RET\_OK 时，返回 None |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
*   **Example**
    

    from futu import *
    pwd_unlock = '123456'
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.unlock_trade(pwd_unlock)
    if ret == RET_OK:
        print('unlock success!')
    else:
        print('unlock_trade failed: ', data)
    trd_ctx.close()
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

*   **Output**

    unlock success!
    

1  

[#](https://openapi.futunn.com/futu-api-doc/trade/unlock.html#661)
 Trd\_UnlockTrade.proto
------------------------------------------------------------------------------------------

*   **介绍**
    
    解锁或锁定交易
    
*   **参数**
    

    message C2S
    {
            required bool unlock = 1; //true 解锁交易，false 锁定交易
            optional string pwdMD5 = 2; //交易密码的 32 位 MD5 加密（全小写），解锁交易必须要填密码，锁定交易不需要验证密码，可不填
            optional int32 securityFirm = 3; //券商标识，取值见 Trd_Common.SecurityFirm
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

> *   账户所属券商参见 [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572)
>     

*   **返回**

    message S2C
    {
    
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2005
    

`uint UnlockTrade(TrdUnlockTrade.Request req);`  
`virtual void OnReply_UnlockTrade(FTAPI_Conn client, uint nSerialNo, TrdUnlockTrade.Response rsp);`

*   **介绍**
    
    解锁或锁定交易
    
*   **参数**
    

    message C2S
    {
            required bool unlock = 1; //true 解锁交易，false 锁定交易
            optional string pwdMD5 = 2; //交易密码的 32 位 MD5 加密（全小写），解锁交易必须要填密码，锁定交易不需要验证密码，可不填
            optional int32 securityFirm = 3; //券商标识，取值见 Trd_Common.SecurityFirm
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

> *   账户所属券商参见 [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572)
>     

*   **返回**

    message S2C
    {
    
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : FTSPI_Trd, FTSPI_Conn {
        FTAPI_Trd trd = new FTAPI_Trd();
    
        public Program() {
            trd.SetClientInfo("csharp", 1);  //设置客户端信息
            trd.SetConnCallback(this);  //设置连接回调
            trd.SetTrdCallback(this);   //设置交易回调
        }
    
        public void Start() {
            trd.InitConnect("127.0.0.1", (ushort)11111, false);
        }
    
        
        public void OnInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Trd onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
            if (errCode != 0)
                return;
            
            TrdUnlockTrade.C2S c2s = TrdUnlockTrade.C2S.CreateBuilder()
                    .SetPwdMD5("e10adc3949ba59abbe56e057f20f883e")
                    .SetUnlock(true)
                    .SetSecurityFirm((int)TrdCommon.SecurityFirm.SecurityFirm_FutuSecurities)
                    .Build();
            TrdUnlockTrade.Request req = TrdUnlockTrade.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.UnlockTrade(req);
            Console.Write("Send TrdUnlockTrade: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_UnlockTrade(FTAPI_Conn client, uint nSerialNo, TrdUnlockTrade.Response rsp)
        {
            Console.Write("Reply: TrdUnlockTrade: {0}\n", nSerialNo);
            Console.Write("retMsg: {0}\n", rsp.RetMsg);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6826809697310867898
    Send TrdUnlockTrade: 3
    Reply: TrdUnlockTrade: 3
    retMsg: 
    

1  
2  
3  
4  

`int unlockTrade(TrdUnlockTrade.Request req);`  
`void onReply_UnlockTrade(FTAPI_Conn client, int nSerialNo, TrdUnlockTrade.Response rsp);`

*   **介绍**
    
    解锁或锁定交易
    
*   **参数**
    

    message C2S
    {
            required bool unlock = 1; //true 解锁交易，false 锁定交易
            optional string pwdMD5 = 2; //交易密码的 32 位 MD5 加密（全小写），解锁交易必须要填密码，锁定交易不需要验证密码，可不填
            optional int32 securityFirm = 3; //券商标识，取值见 Trd_Common.SecurityFirm
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

> *   账户所属券商参见 [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572)
>     

*   **返回**

    message S2C
    {
    
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class TrdDemo implements FTSPI_Trd, FTSPI_Conn {
        FTAPI_Conn_Trd trd = new FTAPI_Conn_Trd();
    
        public TrdDemo() {
            trd.setClientInfo("javaclient", 1);  //设置客户端信息
            trd.setConnSpi(this);  //设置连接回调
            trd.setTrdSpi(this);   //设置交易回调
        }
    
        public void start() {
            trd.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Trd onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
            if (errCode != 0)
                return;
            
            TrdUnlockTrade.C2S c2s = TrdUnlockTrade.C2S.newBuilder()
                    .setPwdMD5("e10adc3949ba59abbe56e057f20f883e")
                    .setUnlock(true)
                    .setSecurityFirm(TrdCommon.SecurityFirm.SecurityFirm_FutuSecurities_VALUE)
                    .build();
            TrdUnlockTrade.Request req = TrdUnlockTrade.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.unlockTrade(req);
            System.out.printf("Send TrdUnlockTrade: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_UnlockTrade(FTAPI_Conn client, int nSerialNo, TrdUnlockTrade.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdUnlockTrade failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdUnlockTrade: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public static void main(String[] args) {
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
57  
58  
59  
60  
61  
62  
63  
64  

*   **Output**

    Send TrdUnlockTrade: 2
    Receive TrdUnlockTrade: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0
    }
    

1  
2  
3  
4  
5  
6  

`Futu::u32_t UnlockTrade(const Trd_UnlockTrade::Request &stReq);`  
`virtual void OnReply_UnlockTrade(Futu::u32_t nSerialNo, const Trd_UnlockTrade::Response &stRsp) = 0;`

*   **介绍**
    
    解锁或锁定交易
    
*   **参数**
    

    message C2S
    {
            required bool unlock = 1; //true 解锁交易，false 锁定交易
            optional string pwdMD5 = 2; //交易密码的 32 位 MD5 加密（全小写），解锁交易必须要填密码，锁定交易不需要验证密码，可不填
            optional int32 securityFirm = 3; //券商标识，取值见 Trd_Common.SecurityFirm
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

> *   账户所属券商参见 [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572)
>     

*   **返回**

    message S2C
    {
    
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    class Program : public FTSPI_Qot, public FTSPI_Trd, public FTSPI_Conn
    {
    public:
    
    	Program() {
    		m_pTrdApi = FTAPI::CreateTrdApi();
    		m_pTrdApi->RegisterTrdSpi(this);
    		m_pTrdApi->RegisterConnSpi(this);
    	}
    
    	~Program() {
    		if (m_pTrdApi != nullptr)
    		{
    			m_pTrdApi->UnregisterTrdSpi();
    			m_pTrdApi->UnregisterConnSpi();
    			FTAPI::ReleaseTrdApi(m_pTrdApi);
    			m_pTrdApi = nullptr;
    		}
    	}
    
    	void Start() {
    		m_pTrdApi->InitConnect("127.0.0.1", 11111, false);
    	}
    
    
    	virtual void OnInitConnect(FTAPI_Conn* pConn, Futu::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		// 组包
    		Trd_UnlockTrade::Request req;
    		Trd_UnlockTrade::C2S *c2s = req.mutable_c2s();
    		c2s->set_pwdmd5("e10adc3949ba59abbe56e057f20f883e");
    		c2s->set_unlock(true);
    		c2s->set_securityfirm(Trd_Common::SecurityFirm::SecurityFirm_FutuSecurities);
    
            m_UnlockTradeSerialNo = m_pTrdApi->UnlockTrade(req);
            cout << "Request UnlockTrade SerialNo: " << m_UnlockTradeSerialNo << endl;
    	}
    
    	virtual void OnReply_UnlockTrade(Futu::u32_t nSerialNo, const Trd_UnlockTrade::Response &stRsp){
            if(nSerialNo == m_UnlockTradeSerialNo)
            {
                cout << "OnReply_UnlockTrade SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
        
    protected:
    	FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_UnlockTradeSerialNo;
    };
    
    int32_t main(int32_t argc, char** argv)
    {
    	FTAPI::Init();
    
    	{
    		Program program;
    		program.Start();
    		getchar();
    	}
    
    	protobuf::ShutdownProtobufLibrary();
    	FTAPI::UnInit();
    	return 0;
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
57  
58  
59  
60  
61  
62  
63  
64  
65  
66  
67  
68  
69  
70  
71  
72  

*   **Output**

    connect
    Request UnlockTrade SerialNo: 4
    OnReply_UnlockTrade SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0
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

`UnlockTrade(req);`

*   **介绍**
    
    解锁或锁定交易
    
*   **参数**
    

    message C2S
    {
            required bool unlock = 1; //true 解锁交易，false 锁定交易
            optional string pwdMD5 = 2; //交易密码的 32 位 MD5 加密（全小写），解锁交易必须要填密码，锁定交易不需要验证密码，可不填
            optional int32 securityFirm = 3; //券商标识，取值见 Trd_Common.SecurityFirm
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

> *   账户所属券商参见 [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572)
>     

*   **返回**

    message S2C
    {
    
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function TrdUnlockTrade(){
        const { RetType } = Common
        const { SecurityFirm } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        unlock: true,
                        securityFirm: SecurityFirm.SecurityFirm_FutuSecurities,
                        pwdMD5: "d0970714757783e6cf17b26fb8e2298f", // 设置为自己账号的交易密码MD5
                    },
                };
                websocket.UnlockTrade(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("UnlockTrade: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                    if(retType == RetType.RetType_Succeed){
                        let data = beautify(JSON.stringify(s2c), {
                            indent_size: 2,
                            space_in_empty_paren: true,
                        });
                        console.log(data);
                    }
                })
                .catch((error) => {
                    console.log("error:", error);
                });
            } else {
                console.log("error", msg);
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
        
        //关闭行情连接，连接不再使用之后，要关闭，否则占用不必要资源
        //同时OpenD也限制了最多128条连接
        //也可以一个页面或者一个项目维护一条连接，这里范例请求一次创建一条连接
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 5000); // 5秒后断开
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

*   **Output**

    UnlockTrade: errCode 0, retMsg , retType 0
    null
    stop
    

1  
2  
3  

提示

*   真实账户调用 [下单](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)
     或 [改单撤单](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html)
     接口，需要先解锁交易；模拟账户无需解锁。
*   解锁或锁定交易针是对 OpenD 的操作，只要有一个连接解锁，其他连接都可以调用交易接口。
*   强烈建议，通过外网连接 OpenD 进行实盘交易的客户，使用加密通道，参见 [启用协议加密](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#319)
    。
*   Futu API 不支持富途令牌，如果开通了富途令牌，则会解锁失败，需要关闭令牌功能后再使用 Futu API 解锁。

接口限制

*   单用户ID 每 30 秒内最多请求 10 次解锁交易接口

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`unlock_trade(password=None, password_md5=None, is_unlock=True)`

*   **介绍**
    
    解锁或锁定交易
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | password | str | 交易密码<br>(ℹ️ 如果 password\_md5 不为空，就使用传入的 password\_md5 解锁；否则使用 password 转 MD5 得到 password\_md5 再解锁) |
    | password\_md5 | str | 交易密码的 32 位 MD5 加密（全小写）<br>(ℹ️ 解锁交易必须要填密码，锁定交易忽略) |
    | is\_unlock | bool | 解锁或锁定<br>(ℹ️ True：解锁  <br>False：锁定) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | msg | NoneType | 当 ret == RET\_OK 时，返回 None |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
*   **Example**
    

    from moomoo import *
    pwd_unlock = '123456'
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    ret, data = trd_ctx.unlock_trade(pwd_unlock)
    if ret == RET_OK:
        print('unlock success!')
    else:
        print('unlock_trade failed: ', data)
    trd_ctx.close()
    

1  
2  
3  
4  
5  
6  
7  
8  
9  

*   **Output**

    unlock success!
    

1  

[#](https://openapi.futunn.com/futu-api-doc/trade/unlock.html#661-2)
 Trd\_UnlockTrade.proto
--------------------------------------------------------------------------------------------

*   **介绍**
    
    解锁或锁定交易
    
*   **参数**
    

    message C2S
    {
            required bool unlock = 1; //true 解锁交易，false 锁定交易
            optional string pwdMD5 = 2; //交易密码的 32 位 MD5 加密（全小写），解锁交易必须要填密码，锁定交易不需要验证密码，可不填
            optional int32 securityFirm = 3; //券商标识，取值见 Trd_Common.SecurityFirm
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

> *   账户所属券商参见 [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572)
>     

*   **返回**

    message S2C
    {
    
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    2005
    

`uint UnlockTrade(TrdUnlockTrade.Request req);`  
`virtual void OnReply_UnlockTrade(MMAPI_Conn client, uint nSerialNo, TrdUnlockTrade.Response rsp);`

*   **介绍**
    
    解锁或锁定交易
    
*   **参数**
    

    message C2S
    {
            required bool unlock = 1; //true 解锁交易，false 锁定交易
            optional string pwdMD5 = 2; //交易密码的 32 位 MD5 加密（全小写），解锁交易必须要填密码，锁定交易不需要验证密码，可不填
            optional int32 securityFirm = 3; //券商标识，取值见 Trd_Common.SecurityFirm
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

> *   账户所属券商参见 [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572)
>     

*   **返回**

    message S2C
    {
    
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : MMSPI_Trd, MMSPI_Conn {
        MMAPI_Trd trd = new MMAPI_Trd();
    
        public Program() {
            trd.SetClientInfo("csharp", 1);  //设置客户端信息
            trd.SetConnCallback(this);  //设置连接回调
            trd.SetTrdCallback(this);   //设置交易回调
        }
    
        public void Start() {
            trd.InitConnect("127.0.0.1", (ushort)11111, false);
        }
    
        
        public void OnInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            Console.Write("Trd onInitConnect: ret={0} desc={1} connID={2}\n", errCode, desc, client.GetConnectID());
            if (errCode != 0)
                return;
            
            TrdUnlockTrade.C2S c2s = TrdUnlockTrade.C2S.CreateBuilder()
                    .SetPwdMD5("e10adc3949ba59abbe56e057f20f883e")
                    .SetUnlock(true)
                    .SetSecurityFirm((int)TrdCommon.SecurityFirm.SecurityFirm_FutuSecurities)
                    .Build();
            TrdUnlockTrade.Request req = TrdUnlockTrade.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.UnlockTrade(req);
            Console.Write("Send TrdUnlockTrade: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
        
        public void OnReply_UnlockTrade(MMAPI_Conn client, uint nSerialNo, TrdUnlockTrade.Response rsp)
        {
            Console.Write("Reply: TrdUnlockTrade: {0}\n", nSerialNo);
            Console.Write("retMsg: {0}\n", rsp.RetMsg);
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

*   **Output**

    Trd onInitConnect: ret=0 desc= connID=6826809697310867898
    Send TrdUnlockTrade: 3
    Reply: TrdUnlockTrade: 3
    retMsg: 
    

1  
2  
3  
4  

`int unlockTrade(TrdUnlockTrade.Request req);`  
`void onReply_UnlockTrade(MMAPI_Conn client, int nSerialNo, TrdUnlockTrade.Response rsp);`

*   **介绍**
    
    解锁或锁定交易
    
*   **参数**
    

    message C2S
    {
            required bool unlock = 1; //true 解锁交易，false 锁定交易
            optional string pwdMD5 = 2; //交易密码的 32 位 MD5 加密（全小写），解锁交易必须要填密码，锁定交易不需要验证密码，可不填
            optional int32 securityFirm = 3; //券商标识，取值见 Trd_Common.SecurityFirm
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

> *   账户所属券商参见 [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572)
>     

*   **返回**

    message S2C
    {
    
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class TrdDemo implements MMSPI_Trd, MMSPI_Conn {
        MMAPI_Conn_Trd trd = new MMAPI_Conn_Trd();
    
        public TrdDemo() {
            trd.setClientInfo("javaclient", 1);  //设置客户端信息
            trd.setConnSpi(this);  //设置连接回调
            trd.setTrdSpi(this);   //设置交易回调
        }
    
        public void start() {
            trd.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Trd onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
            if (errCode != 0)
                return;
            
            TrdUnlockTrade.C2S c2s = TrdUnlockTrade.C2S.newBuilder()
                    .setPwdMD5("e10adc3949ba59abbe56e057f20f883e")
                    .setUnlock(true)
                    .setSecurityFirm(TrdCommon.SecurityFirm.SecurityFirm_FutuSecurities_VALUE)
                    .build();
            TrdUnlockTrade.Request req = TrdUnlockTrade.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.unlockTrade(req);
            System.out.printf("Send TrdUnlockTrade: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_UnlockTrade(MMAPI_Conn client, int nSerialNo, TrdUnlockTrade.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdUnlockTrade failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdUnlockTrade: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public static void main(String[] args) {
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
57  
58  
59  
60  
61  
62  
63  
64  

*   **Output**

    Send TrdUnlockTrade: 2
    Receive TrdUnlockTrade: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0
    }
    

1  
2  
3  
4  
5  
6  

`moomoo::u32_t UnlockTrade(const Trd_UnlockTrade::Request &stReq);`  
`virtual void OnReply_UnlockTrade(moomoo::u32_t nSerialNo, const Trd_UnlockTrade::Response &stRsp) = 0;`

*   **介绍**
    
    解锁或锁定交易
    
*   **参数**
    

    message C2S
    {
            required bool unlock = 1; //true 解锁交易，false 锁定交易
            optional string pwdMD5 = 2; //交易密码的 32 位 MD5 加密（全小写），解锁交易必须要填密码，锁定交易不需要验证密码，可不填
            optional int32 securityFirm = 3; //券商标识，取值见 Trd_Common.SecurityFirm
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

> *   账户所属券商参见 [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572)
>     

*   **返回**

    message S2C
    {
    
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    class Program : public MMSPI_Qot, public MMSPI_Trd, public MMSPI_Conn
    {
    public:
    
    	Program() {
    		m_pTrdApi = MMAPI::CreateTrdApi();
    		m_pTrdApi->RegisterTrdSpi(this);
    		m_pTrdApi->RegisterConnSpi(this);
    	}
    
    	~Program() {
    		if (m_pTrdApi != nullptr)
    		{
    			m_pTrdApi->UnregisterTrdSpi();
    			m_pTrdApi->UnregisterConnSpi();
    			MMAPI::ReleaseTrdApi(m_pTrdApi);
    			m_pTrdApi = nullptr;
    		}
    	}
    
    	void Start() {
    		m_pTrdApi->InitConnect("127.0.0.1", 11111, false);
    	}
    
    
    	virtual void OnInitConnect(MMAPI_Conn* pConn, moomoo::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		// 组包
    		Trd_UnlockTrade::Request req;
    		Trd_UnlockTrade::C2S *c2s = req.mutable_c2s();
    		c2s->set_pwdmd5("e10adc3949ba59abbe56e057f20f883e");
    		c2s->set_unlock(true);
    		c2s->set_securityfirm(Trd_Common::SecurityFirm::SecurityFirm_FutuSecurities);
    
            m_UnlockTradeSerialNo = m_pTrdApi->UnlockTrade(req);
            cout << "Request UnlockTrade SerialNo: " << m_UnlockTradeSerialNo << endl;
    	}
    
    	virtual void OnReply_UnlockTrade(moomoo::u32_t nSerialNo, const Trd_UnlockTrade::Response &stRsp){
            if(nSerialNo == m_UnlockTradeSerialNo)
            {
                cout << "OnReply_UnlockTrade SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
        
    protected:
    	MMAPI_Trd *m_pTrdApi;
    
        moomoo::u32_t m_UnlockTradeSerialNo;
    };
    
    int32_t main(int32_t argc, char** argv)
    {
    	MMAPI::Init();
    
    	{
    		Program program;
    		program.Start();
    		getchar();
    	}
    
    	protobuf::ShutdownProtobufLibrary();
    	MMAPI::UnInit();
    	return 0;
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
57  
58  
59  
60  
61  
62  
63  
64  
65  
66  
67  
68  
69  
70  
71  
72  

*   **Output**

    connect
    Request UnlockTrade SerialNo: 4
    OnReply_UnlockTrade SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0
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

`UnlockTrade(req);`

*   **介绍**
    
    解锁或锁定交易
    
*   **参数**
    

    message C2S
    {
            required bool unlock = 1; //true 解锁交易，false 锁定交易
            optional string pwdMD5 = 2; //交易密码的 32 位 MD5 加密（全小写），解锁交易必须要填密码，锁定交易不需要验证密码，可不填
            optional int32 securityFirm = 3; //券商标识，取值见 Trd_Common.SecurityFirm
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

> *   账户所属券商参见 [SecurityFirm](https://openapi.futunn.com/futu-api-doc/trade/trade.html#572)
>     

*   **返回**

    message S2C
    {
    
    }
    
    message Response
    {
            //以下3个字段每条协议都有，注释说明在 InitConnect.proto 中
            required int32 retType = 1 [default = -400];
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

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function TrdUnlockTrade(){
        const { RetType } = Common
        const { SecurityFirm } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        unlock: true,
                        securityFirm: SecurityFirm.SecurityFirm_FutuSecurities,
                        pwdMD5: "d0970714757783e6cf17b26fb8e2298f", // 设置为自己账号的交易密码MD5
                    },
                };
                websocket.UnlockTrade(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("UnlockTrade: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
                    if(retType == RetType.RetType_Succeed){
                        let data = beautify(JSON.stringify(s2c), {
                            indent_size: 2,
                            space_in_empty_paren: true,
                        });
                        console.log(data);
                    }
                })
                .catch((error) => {
                    console.log("error:", error);
                });
            } else {
                console.log("error", msg);
            }
        };
    
        websocket.start(addr, port, enable_ssl, key);
        
        //关闭行情连接，连接不再使用之后，要关闭，否则占用不必要资源
        //同时OpenD也限制了最多128条连接
        //也可以一个页面或者一个项目维护一条连接，这里范例请求一次创建一条连接
        setTimeout(()=>{ 
            websocket.stop();
            console.log("stop");
        }, 5000); // 5秒后断开
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

*   **Output**

    UnlockTrade: errCode 0, retMsg , retType 0
    null
    stop
    

1  
2  
3  

提示

*   真实账户调用 [下单](https://openapi.futunn.com/futu-api-doc/trade/place-order.html)
     或 [改单撤单](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html)
     接口，需要先解锁交易；模拟账户无需解锁。
*   解锁或锁定交易，是针对 OpenD 的操作，只要有一个连接解锁，其他连接都可以调用交易接口。
*   强烈建议，通过外网连接 OpenD 进行实盘交易的客户，使用加密通道，参见 [启用协议加密](https://openapi.futunn.com/futu-api-doc/ftapi/init.html#319)
    。
*   Moomoo API 不支持富途令牌，如果开通了富途令牌，则会解锁失败，需要关闭令牌功能后再使用 Moomoo API 解锁。

接口限制

*   单用户ID 每 30 秒内最多请求 10 次解锁交易接口

← [获取交易业务账户列表](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html) [查询账户资金](https://openapi.futunn.com/futu-api-doc/trade/get-funds.html)
 →

[解锁交易](https://openapi.futunn.com/futu-api-doc/trade/unlock.html)