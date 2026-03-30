 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/modify-user-security.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/modify-user-security.html)
    

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

*   [简体中文](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/quote/modify-user-security.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/quote/modify-user-security.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
    *   [行情接口总览](https://openapi.futunn.com/futu-api-doc/quote/overview.html)
        
    *   [行情对象](https://openapi.futunn.com/futu-api-doc/quote/base.html)
        
    *   实时行情
        
    *   基本数据
        
    *   相关衍生品
        
    *   全市场筛选
        
    *   个性化
        
        *   [获取历史 K 线额度使用明细](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html)
            
        *   [设置到价提醒](https://openapi.futunn.com/futu-api-doc/quote/set-price-reminder.html)
            
        *   [获取到价提醒列表](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html)
            
        *   [获取自选股列表](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html)
            
        *   [获取自选股分组](https://openapi.futunn.com/futu-api-doc/quote/get-user-security-group.html)
            
        *   [修改自选股列表](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html)
            
        *   [到价提醒回调](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html)
            
        
    *   [行情定义](https://openapi.futunn.com/futu-api-doc/quote/quote.html)
        
    
*   交易接口
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html#8208)
 修改自选股列表
==========================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`modify_user_security(group_name, op, code_list)`

*   **介绍**
    
    修改指定分组的自选股列表（系统分组不支持修改）
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | group\_name | str | 需要修改的自选股分组名称 |
    | op  | [ModifyUserSecurityOp](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3838) | 操作类型 |
    | code\_list | list | 股票列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 str |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | msg | str | 当 ret == RET\_OK，返回“success” |
    | 当 ret != RET\_OK，msg 返回错误描述 |
    
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.modify_user_security("A", ModifyUserSecurityOp.ADD, ['HK.00700'])
    if ret == RET_OK:
        print(data) # 返回 success
    else:
        print('error:', data)
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

*   **Output**

    success
    

1  

[#](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html#118)
 Qot\_ModifyUserSecurity.proto
---------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    修改自选股列表
    
*   **参数**
    

    enum ModifyUserSecurityOp
    {
    	ModifyUserSecurityOp_Unknown = 0;
    	ModifyUserSecurityOp_Add = 1; //新增
    	ModifyUserSecurityOp_Del = 2; //删除自选
    	ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    
    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序的首个
    	required int32 op = 2; //ModifyUserSecurityOp，操作类型
    	repeated Qot_Common.Security securityList = 3; //新增、删除或移出该分组下的股票
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
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	
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
13  

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3214
    

`uint ModifyUserSecurity(QotModifyUserSecurity.Request req);`  
`virtual void OnReply_ModifyUserSecurity(FTAPI_Conn client, uint nSerialNo, QotModifyUserSecurity.Response rsp);`

*   **介绍**
    
    修改自选股列表
    
*   **参数**
    

    enum ModifyUserSecurityOp
    {
    	ModifyUserSecurityOp_Unknown = 0;
    	ModifyUserSecurityOp_Add = 1; //新增
    	ModifyUserSecurityOp_Del = 2; //删除自选
    	ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    
    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序的首个
    	required int32 op = 2; //ModifyUserSecurityOp，操作类型
    	repeated Qot_Common.Security securityList = 3; //新增、删除或移出该分组下的股票
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
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	
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
13  

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : FTSPI_Qot, FTSPI_Conn {
        FTAPI_Qot qot = new FTAPI_Qot();
    
        public Program() {
            qot.SetClientInfo("csharp", 1);  //设置客户端信息
            qot.SetConnCallback(this);  //设置连接回调
            qot.SetQotCallback(this);   //设置交易回调
        }
    
        public void Start() {
            qot.InitConnect("127.0.0.1", (ushort)11111, false);
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
            QotModifyUserSecurity.C2S c2s = QotModifyUserSecurity.C2S.CreateBuilder()
                    .SetGroupName("mygroup")
                    .SetOp(QotModifyUserSecurity.ModifyUserSecurityOp.ModifyUserSecurityOp_Add)
                    .AddSecurityList(sec)
                .Build();
            QotModifyUserSecurity.Request req = QotModifyUserSecurity.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.ModifyUserSecurity(req);
            Console.Write("Send QotModifyUserSecurity: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_ModifyUserSecurity(FTAPI_Conn client, uint nSerialNo, QotModifyUserSecurity.Response rsp)
        {
            Console.Write("Reply: QotModifyUserSecurity: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("retMsg: {0}\n", rsp.RetMsg);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826791872291058416
    Send QotModifyUserSecurity: 3
    Reply: QotModifyUserSecurity: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    retMsg:
    

1  
2  
3  
4  
5  
6  
7  

`int modifyUserSecurity(QotModifyUserSecurity.Request req);`  
`void onReply_ModifyUserSecurity(FTAPI_Conn client, int nSerialNo, QotModifyUserSecurity.Response rsp);`

*   **介绍**
    
    修改自选股列表
    
*   **参数**
    

    enum ModifyUserSecurityOp
    {
    	ModifyUserSecurityOp_Unknown = 0;
    	ModifyUserSecurityOp_Add = 1; //新增
    	ModifyUserSecurityOp_Del = 2; //删除自选
    	ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    
    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序的首个
    	required int32 op = 2; //ModifyUserSecurityOp，操作类型
    	repeated Qot_Common.Security securityList = 3; //新增、删除或移出该分组下的股票
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
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	
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
13  

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class QotDemo implements FTSPI_Qot, FTSPI_Conn {
        FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();
    
        public QotDemo() {
            qot.setClientInfo("javaclient", 1);  //设置客户端信息
            qot.setConnSpi(this);  //设置连接回调
            qot.setQotSpi(this);   //设置交易回调
        }
    
        public void start() {
            qot.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(FTAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Qot onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
            if (errCode != 0)
                return;
    
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .setCode("00700")
                    .build();
            QotModifyUserSecurity.C2S c2s = QotModifyUserSecurity.C2S.newBuilder()
                    .setGroupName("mygroup")
                    .setOp(QotModifyUserSecurity.ModifyUserSecurityOp.ModifyUserSecurityOp_Add_VALUE)
                    .addSecurityList(sec)
                .build();
            QotModifyUserSecurity.Request req = QotModifyUserSecurity.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.modifyUserSecurity(req);
            System.out.printf("Send QotModifyUserSecurity: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_ModifyUserSecurity(FTAPI_Conn client, int nSerialNo, QotModifyUserSecurity.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotModifyUserSecurity failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotModifyUserSecurity: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public static void main(String[] args) {
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

*   **Output**

    Send QotModifyUserSecurity: 2
    Receive QotModifyUserSecurity: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
    }
    

1  
2  
3  
4  
5  
6  

`Futu::u32_t ModifyUserSecurity(const Qot_ModifyUserSecurity::Request &stReq);`  
`virtual void OnReply_ModifyUserSecurity(Futu::u32_t nSerialNo, const Qot_ModifyUserSecurity::Response &stRsp) = 0;`

*   **介绍**
    
    修改自选股列表
    
*   **参数**
    

    enum ModifyUserSecurityOp
    {
    	ModifyUserSecurityOp_Unknown = 0;
    	ModifyUserSecurityOp_Add = 1; //新增
    	ModifyUserSecurityOp_Del = 2; //删除自选
    	ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    
    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序的首个
    	required int32 op = 2; //ModifyUserSecurityOp，操作类型
    	repeated Qot_Common.Security securityList = 3; //新增、删除或移出该分组下的股票
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
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	
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
13  

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    class Program : public FTSPI_Qot, public FTSPI_Trd, public FTSPI_Conn
    {
    public:
    
    	Program() {
    		m_pQotApi = FTAPI::CreateQotApi();
    		m_pQotApi->RegisterQotSpi(this);
    		m_pQotApi->RegisterConnSpi(this);
    	}
    
    	~Program() {
    		if (m_pQotApi != nullptr)
    		{
    			m_pQotApi->UnregisterQotSpi();
    			m_pQotApi->UnregisterConnSpi();
    			FTAPI::ReleaseQotApi(m_pQotApi);
    			m_pQotApi = nullptr;
    		}
    	}
    
    	void Start() {
    		m_pQotApi->InitConnect("127.0.0.1", 11111, false);
    	}
    
    
    	virtual void OnInitConnect(FTAPI_Conn* pConn, Futu::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		// 组包
    		Qot_ModifyUserSecurity::Request req;
    		Qot_ModifyUserSecurity::C2S *c2s = req.mutable_c2s();
    		c2s->set_groupname("some_group");
    		c2s->set_op(1);
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
    		m_ModifyUserSecuritySerialNo = m_pQotApi->ModifyUserSecurity(req);
    		cout << "Request ModifyUserSecurity SerialNo: " << m_ModifyUserSecuritySerialNo << endl;
    	}
    
    	virtual void OnReply_ModifyUserSecurity(Futu::u32_t nSerialNo, const Qot_ModifyUserSecurity::Response &stRsp){
            if(nSerialNo == m_ModifyUserSecuritySerialNo)
            {
                cout << "OnReply_ModifyUserSecurity SerialNo:" << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_ModifyUserSecuritySerialNo;
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
73  
74  

*   **Output**

    connect
    Request ModifyUserSecurity SerialNo: 4
    OnReply_ModifyUserSecurity SerialNo: 4
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

`ModifyUserSecurity(req);`

*   **介绍**
    
    修改自选股列表
    
*   **参数**
    

    enum ModifyUserSecurityOp
    {
    	ModifyUserSecurityOp_Unknown = 0;
    	ModifyUserSecurityOp_Add = 1; //新增
    	ModifyUserSecurityOp_Del = 2; //删除自选
    	ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    
    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序的首个
    	required int32 op = 2; //ModifyUserSecurityOp，操作类型
    	repeated Qot_Common.Security securityList = 3; //新增、删除或移出该分组下的股票
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
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	
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
13  

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Qot_ModifyUserSecurity } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotModifyUserSecurity(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        const { ModifyUserSecurityOp } = Qot_ModifyUserSecurity
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        groupName: "testgroup",
                        op: ModifyUserSecurityOp.ModifyUserSecurityOp_Add,
                        securityList: [{\
                            market: QotMarket.QotMarket_HK_Security,\
                            code: "00700",\
                        },],
                    },
                };
    
                websocket.ModifyUserSecurity(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("ModifyUserSecurity: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
52  
53  
54  
55  
56  

*   **Output**

    ModifyUserSecurity: errCode 0, retMsg , retType 0
    null
    stop
    

1  
2  
3  

接口限制

*   每 30 秒内最多请求 10 次修改自选股列表接口
*   仅支持修改自定义分组，不支持修改系统分组
*   “全部”自选股列表的数量存在上限：无交易客户 500 个，有交易客户 2000 个（向其他分组增加自选股时，“全部”列表中也会同步增加）
*   如果有同名的分组，会操作排序在第一个的分组

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`modify_user_security(group_name, op, code_list)`

*   **介绍**
    
    修改指定分组的自选股列表（系统分组不支持修改）
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | group\_name | str | 需要修改的自选股分组名称 |
    | op  | [ModifyUserSecurityOp](https://openapi.futunn.com/futu-api-doc/quote/quote.html#3838) | 操作类型 |
    | code\_list | list | 股票列表<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>list 中元素类型是 str |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467) | 接口调用结果 |
    | msg | str | 当 ret == RET\_OK，返回“success” |
    | 当 ret != RET\_OK，msg 返回错误描述 |
    
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.modify_user_security("A", ModifyUserSecurityOp.ADD, ['HK.00700'])
    if ret == RET_OK:
        print(data) # 返回 success
    else:
        print('error:', data)
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

*   **Output**

    success
    

1  

[#](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html#118-2)
 Qot\_ModifyUserSecurity.proto
-----------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    修改自选股列表
    
*   **参数**
    

    enum ModifyUserSecurityOp
    {
    	ModifyUserSecurityOp_Unknown = 0;
    	ModifyUserSecurityOp_Add = 1; //新增
    	ModifyUserSecurityOp_Del = 2; //删除自选
    	ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    
    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序的首个
    	required int32 op = 2; //ModifyUserSecurityOp，操作类型
    	repeated Qot_Common.Security securityList = 3; //新增、删除或移出该分组下的股票
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
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	
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
13  

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **协议 ID**
    
    3214
    

`uint ModifyUserSecurity(QotModifyUserSecurity.Request req);`  
`virtual void OnReply_ModifyUserSecurity(MMAPI_Conn client, uint nSerialNo, QotModifyUserSecurity.Response rsp);`

*   **介绍**
    
    修改自选股列表
    
*   **参数**
    

    enum ModifyUserSecurityOp
    {
    	ModifyUserSecurityOp_Unknown = 0;
    	ModifyUserSecurityOp_Add = 1; //新增
    	ModifyUserSecurityOp_Del = 2; //删除自选
    	ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    
    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序的首个
    	required int32 op = 2; //ModifyUserSecurityOp，操作类型
    	repeated Qot_Common.Security securityList = 3; //新增、删除或移出该分组下的股票
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
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	
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
13  

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class Program : MMSPI_Qot, MMSPI_Conn {
        MMAPI_Qot qot = new MMAPI_Qot();
    
        public Program() {
            qot.SetClientInfo("csharp", 1);  //设置客户端信息
            qot.SetConnCallback(this);  //设置连接回调
            qot.SetQotCallback(this);   //设置交易回调
        }
    
        public void Start() {
            qot.InitConnect("127.0.0.1", (ushort)11111, false);
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
            QotModifyUserSecurity.C2S c2s = QotModifyUserSecurity.C2S.CreateBuilder()
                    .SetGroupName("mygroup")
                    .SetOp(QotModifyUserSecurity.ModifyUserSecurityOp.ModifyUserSecurityOp_Add)
                    .AddSecurityList(sec)
                .Build();
            QotModifyUserSecurity.Request req = QotModifyUserSecurity.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.ModifyUserSecurity(req);
            Console.Write("Send QotModifyUserSecurity: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_ModifyUserSecurity(MMAPI_Conn client, uint nSerialNo, QotModifyUserSecurity.Response rsp)
        {
            Console.Write("Reply: QotModifyUserSecurity: {0}  {1}\n", nSerialNo, rsp.ToString());
            Console.Write("retMsg: {0}\n", rsp.RetMsg);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826791872291058416
    Send QotModifyUserSecurity: 3
    Reply: QotModifyUserSecurity: 3  retType: 0
    retMsg: ""
    errCode: 0
    
    retMsg:
    

1  
2  
3  
4  
5  
6  
7  

`int modifyUserSecurity(QotModifyUserSecurity.Request req);`  
`void onReply_ModifyUserSecurity(MMAPI_Conn client, int nSerialNo, QotModifyUserSecurity.Response rsp);`

*   **介绍**
    
    修改自选股列表
    
*   **参数**
    

    enum ModifyUserSecurityOp
    {
    	ModifyUserSecurityOp_Unknown = 0;
    	ModifyUserSecurityOp_Add = 1; //新增
    	ModifyUserSecurityOp_Del = 2; //删除自选
    	ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    
    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序的首个
    	required int32 op = 2; //ModifyUserSecurityOp，操作类型
    	repeated Qot_Common.Security securityList = 3; //新增、删除或移出该分组下的股票
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
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	
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
13  

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    public class QotDemo implements MMSPI_Qot, MMSPI_Conn {
        MMAPI_Conn_Qot qot = new MMAPI_Conn_Qot();
    
        public QotDemo() {
            qot.setClientInfo("javaclient", 1);  //设置客户端信息
            qot.setConnSpi(this);  //设置连接回调
            qot.setQotSpi(this);   //设置交易回调
        }
    
        public void start() {
            qot.initConnect("127.0.0.1", (short)11111, false);
        }
    
        @Override
        public void onInitConnect(MMAPI_Conn client, long errCode, String desc)
        {
            System.out.printf("Qot onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
            if (errCode != 0)
                return;
    
            QotCommon.Security sec = QotCommon.Security.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .setCode("00700")
                    .build();
            QotModifyUserSecurity.C2S c2s = QotModifyUserSecurity.C2S.newBuilder()
                    .setGroupName("mygroup")
                    .setOp(QotModifyUserSecurity.ModifyUserSecurityOp.ModifyUserSecurityOp_Add_VALUE)
                    .addSecurityList(sec)
                .build();
            QotModifyUserSecurity.Request req = QotModifyUserSecurity.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.modifyUserSecurity(req);
            System.out.printf("Send QotModifyUserSecurity: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_ModifyUserSecurity(MMAPI_Conn client, int nSerialNo, QotModifyUserSecurity.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotModifyUserSecurity failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotModifyUserSecurity: %s\n", json);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public static void main(String[] args) {
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

*   **Output**

    Send QotModifyUserSecurity: 2
    Receive QotModifyUserSecurity: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
    }
    

1  
2  
3  
4  
5  
6  

`moomoo::u32_t ModifyUserSecurity(const Qot_ModifyUserSecurity::Request &stReq);`  
`virtual void OnReply_ModifyUserSecurity(moomoo::u32_t nSerialNo, const Qot_ModifyUserSecurity::Response &stRsp) = 0;`

*   **介绍**
    
    修改自选股列表
    
*   **参数**
    

    enum ModifyUserSecurityOp
    {
    	ModifyUserSecurityOp_Unknown = 0;
    	ModifyUserSecurityOp_Add = 1; //新增
    	ModifyUserSecurityOp_Del = 2; //删除自选
    	ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    
    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序的首个
    	required int32 op = 2; //ModifyUserSecurityOp，操作类型
    	repeated Qot_Common.Security securityList = 3; //新增、删除或移出该分组下的股票
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
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	
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
13  

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    class Program : public MMSPI_Qot, public MMSPI_Trd, public MMSPI_Conn
    {
    public:
    
    	Program() {
    		m_pQotApi = MMAPI::CreateQotApi();
    		m_pQotApi->RegisterQotSpi(this);
    		m_pQotApi->RegisterConnSpi(this);
    	}
    
    	~Program() {
    		if (m_pQotApi != nullptr)
    		{
    			m_pQotApi->UnregisterQotSpi();
    			m_pQotApi->UnregisterConnSpi();
    			MMAPI::ReleaseQotApi(m_pQotApi);
    			m_pQotApi = nullptr;
    		}
    	}
    
    	void Start() {
    		m_pQotApi->InitConnect("127.0.0.1", 11111, false);
    	}
    
    
    	virtual void OnInitConnect(MMAPI_Conn* pConn, moomoo::i64_t nErrCode, const char* strDesc) {
    		cout << "connect" << endl;
    
    		// 组包
    		Qot_ModifyUserSecurity::Request req;
    		Qot_ModifyUserSecurity::C2S *c2s = req.mutable_c2s();
    		c2s->set_groupname("some_group");
    		c2s->set_op(1);
    		auto secList = c2s->mutable_securitylist();
    		Qot_Common::Security *sec = secList->Add();
    		sec->set_code("00700");
    		sec->set_market(Qot_Common::QotMarket::QotMarket_HK_Security);
    
    		m_ModifyUserSecuritySerialNo = m_pQotApi->ModifyUserSecurity(req);
    		cout << "Request ModifyUserSecurity SerialNo: " << m_ModifyUserSecuritySerialNo << endl;
    	}
    
    	virtual void OnReply_ModifyUserSecurity(moomoo::u32_t nSerialNo, const Qot_ModifyUserSecurity::Response &stRsp){
            if(nSerialNo == m_ModifyUserSecuritySerialNo)
            {
                cout << "OnReply_ModifyUserSecurity SerialNo:" << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_ModifyUserSecuritySerialNo;
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
73  
74  

*   **Output**

    connect
    Request ModifyUserSecurity SerialNo: 4
    OnReply_ModifyUserSecurity SerialNo: 4
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

`ModifyUserSecurity(req);`

*   **介绍**
    
    修改自选股列表
    
*   **参数**
    

    enum ModifyUserSecurityOp
    {
    	ModifyUserSecurityOp_Unknown = 0;
    	ModifyUserSecurityOp_Add = 1; //新增
    	ModifyUserSecurityOp_Del = 2; //删除自选
    	ModifyUserSecurityOp_MoveOut = 3; //移出分组
    }
    
    message C2S
    {
    	required string groupName = 1; //分组名，有同名的返回排序的首个
    	required int32 op = 2; //ModifyUserSecurityOp，操作类型
    	repeated Qot_Common.Security securityList = 3; //新增、删除或移出该分组下的股票
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
17  
18  
19  

> *   股票结构参见 [Security](https://openapi.futunn.com/futu-api-doc/quote/quote.html#1377)
>     

*   **返回**

    message S2C
    {
    	
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
13  

> *   接口调用结果，结构参见 [RetType](https://openapi.futunn.com/futu-api-doc/ftapi/common.html#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Qot_ModifyUserSecurity } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotModifyUserSecurity(){
        const { RetType } = Common
        const { QotMarket } = Qot_Common
        const { ModifyUserSecurityOp } = Qot_ModifyUserSecurity
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        groupName: "testgroup",
                        op: ModifyUserSecurityOp.ModifyUserSecurityOp_Add,
                        securityList: [{\
                            market: QotMarket.QotMarket_HK_Security,\
                            code: "00700",\
                        },],
                    },
                };
    
                websocket.ModifyUserSecurity(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("ModifyUserSecurity: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
52  
53  
54  
55  
56  

*   **Output**

    ModifyUserSecurity: errCode 0, retMsg , retType 0
    null
    stop
    

1  
2  
3  

接口限制

*   每 30 秒内最多请求 10 次修改自选股列表接口
*   仅支持修改自定义分组，不支持修改系统分组
*   “全部”自选股列表的数量存在上限：无交易客户 500 个，有交易客户 2000 个（向其他分组增加自选股时，“全部”列表中也会同步增加）
*   如果有同名的分组，会操作排序在第一个的分组

← [获取自选股分组](https://openapi.futunn.com/futu-api-doc/quote/get-user-security-group.html) [到价提醒回调](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html)
 →

[修改自选股列表](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html)