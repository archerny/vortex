[#](./quote_get-user-security-group.md#5364)
 获取自选股分组
=============================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_user_security_group(group_type = UserSecurityGroupType.ALL)`

*   **介绍**
    
    获取自选股分组列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | group\_type | [UserSecurityGroupType](./quote_quote.md#4977) | 分组类型 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回自选股分组数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   自选股分组数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | group\_name | str | 分组名 |
        | group\_type | [UserSecurityGroupType](./quote_quote.md#4977) | 分组类型 |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_user_security_group(group_type = UserSecurityGroupType.ALL)
    if ret == RET_OK:
        print(data)
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

            group_name group_type
    0          期权     SYSTEM
    ..         ...        ...
    12          C     CUSTOM
    
    [13 rows x 2 columns]
    

1  
2  
3  
4  
5  
6  

[#](./quote_get-user-security-group.md#2010)
 Qot\_GetUserSecurityGroup.proto
---------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取自选股分组列表
    
*   **参数**
    

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message C2S
    {
    	required int32 groupType = 1; // GroupType，自选股分组类型。
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

*   **返回**

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message GroupData
    {
    	required string groupName = 1; // 自选股分组名字
    	required int32 groupType = 2; // GroupType，自选股分组类型。
    }
    
    message S2C
    {
    	repeated GroupData groupList = 1; // 自选股分组列表
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3222
    

`uint GetUserSecurityGroup(QotGetUserSecurityGroup.Request req);`  
`virtual void OnReply_GetUserSecurityGroup(FTAPI_Conn client, uint nSerialNo, QotGetUserSecurityGroup.Response rsp);`

*   **介绍**
    
    获取自选股分组列表
    
*   **参数**
    

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message C2S
    {
    	required int32 groupType = 1; // GroupType，自选股分组类型。
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

*   **返回**

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message GroupData
    {
    	required string groupName = 1; // 自选股分组名字
    	required int32 groupType = 2; // GroupType，自选股分组类型。
    }
    
    message S2C
    {
    	repeated GroupData groupList = 1; // 自选股分组列表
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            QotGetUserSecurityGroup.C2S c2s = QotGetUserSecurityGroup.C2S.CreateBuilder()
                    .SetGroupType(QotGetUserSecurityGroup.GroupType.GroupType_All)
                .Build();
            QotGetUserSecurityGroup.Request req = QotGetUserSecurityGroup.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetUserSecurityGroup(req);
            Console.Write("Send QotGetUserSecurityGroup: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetUserSecurityGroup(FTAPI_Conn client, uint nSerialNo, QotGetUserSecurityGroup.Response rsp)
        {
            Console.Write("Reply: QotGetUserSecurityGroup: {0}\n", nSerialNo);
            Console.Write("groupName: {0}, groupType: {1} \n", rsp.S2C.GroupListList[0].GroupName, rsp.S2C.GroupListList[0].GroupType);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826789498578847020
    Send QotGetUserSecurityGroup: 3
    Reply: QotGetUserSecurityGroup: 3
    groupName: 全部, groupType: 2
    

1  
2  
3  
4  

`int getUserSecurityGroup(QotGetUserSecurityGroup.Request req);`  
`void onReply_GetUserSecurityGroup(FTAPI_Conn client, int nSerialNo, QotGetUserSecurityGroup.Response rsp);`

*   **介绍**
    
    获取自选股分组列表
    
*   **参数**
    

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message C2S
    {
    	required int32 groupType = 1; // GroupType，自选股分组类型。
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

*   **返回**

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message GroupData
    {
    	required string groupName = 1; // 自选股分组名字
    	required int32 groupType = 2; // GroupType，自选股分组类型。
    }
    
    message S2C
    {
    	repeated GroupData groupList = 1; // 自选股分组列表
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            QotGetUserSecurityGroup.C2S c2s = QotGetUserSecurityGroup.C2S.newBuilder()
                    .setGroupType(QotGetUserSecurityGroup.GroupType.GroupType_All_VALUE)
                .build();
            QotGetUserSecurityGroup.Request req = QotGetUserSecurityGroup.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getUserSecurityGroup(req);
            System.out.printf("Send QotGetUserSecurityGroup: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetUserSecurityGroup(FTAPI_Conn client, int nSerialNo, QotGetUserSecurityGroup.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetUserSecurityGroup failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetUserSecurityGroup: %s\n", json);
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

*   **Output**

    Send QotGetUserSecurityGroup: 2
    Receive QotGetUserSecurityGroup: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "groupList": [{\
          "groupName": "全部",\
          "groupType": 2\
        }, {\
          "groupName": "特别关注",\
          "groupType": 2\
        }, ... {\
          "groupName": "债券",\
          "groupType": 2\
        }]
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

`Futu::u32_t GetUserSecurityGroup(const Qot_GetUserSecurityGroup::Request &stReq);`  
`virtual void OnReply_GetUserSecurityGroup(Futu::u32_t nSerialNo, const Qot_GetUserSecurityGroup::Response &stRsp) = 0;`

*   **介绍**
    
    获取自选股分组列表
    
*   **参数**
    

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message C2S
    {
    	required int32 groupType = 1; // GroupType，自选股分组类型。
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

*   **返回**

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message GroupData
    {
    	required string groupName = 1; // 自选股分组名字
    	required int32 groupType = 2; // GroupType，自选股分组类型。
    }
    
    message S2C
    {
    	repeated GroupData groupList = 1; // 自选股分组列表
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    		Qot_GetUserSecurityGroup::Request req;
    		Qot_GetUserSecurityGroup::C2S *c2s = req.mutable_c2s();
    		c2s->set_grouptype(3);
    
    		m_GetUserSecurityGroupSerialNo = m_pQotApi->GetUserSecurityGroup(req);
    		cout << "Request GetUserSecurityGroup SerialNo: " << m_GetUserSecurityGroupSerialNo << endl;
    	}
    
    	virtual void OnReply_GetUserSecurityGroup(Futu::u32_t nSerialNo, const Qot_GetUserSecurityGroup::Response &stRsp){
            if(nSerialNo == m_GetUserSecurityGroupSerialNo)
            {
                cout << "OnReply_GetUserSecurityGroup SerialNo:" << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetUserSecurityGroupSerialNo;
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

*   **Output**

    connect
    Request GetUserSecurityGroup SerialNo: 4
    OnReply_GetUserSecurityGroup SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "groupList": [\
       {\
        "groupName": "全部",\
        "groupType": 2\
       },\
    ...\
       {\
        "groupName": "债券",\
        "groupType": 2\
       }\
      ]
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

`GetUserSecurityGroup(req);`

*   **介绍**
    
    获取自选股分组列表
    
*   **参数**
    

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message C2S
    {
    	required int32 groupType = 1; // GroupType，自选股分组类型。
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

*   **返回**

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message GroupData
    {
    	required string groupName = 1; // 自选股分组名字
    	required int32 groupType = 2; // GroupType，自选股分组类型。
    }
    
    message S2C
    {
    	repeated GroupData groupList = 1; // 自选股分组列表
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Qot_GetUserSecurityGroup } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetUserSecurityGroup(){
        const { RetType } = Common
        const { GroupType } = Qot_GetUserSecurityGroup
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        groupType: GroupType.GroupType_All,
                    },
                };
                
                websocket.GetUserSecurityGroup(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("UserSecurityGroup: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    UserSecurityGroup: errCode 0, retMsg , retType 0
    {
      "groupList": [{\
        "groupName": "全部",\
        "groupType": 2\
      }, {\
        "groupName": "特别关注",\
        "groupType": 2\
      }, ..., {\
        "groupName": "债券",\
        "groupType": 2\
      }]
    }
    stop
    

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

接口限制

*   每 30 秒内最多请求 10 次获取自选股分组接口

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_user_security_group(group_type = UserSecurityGroupType.ALL)`

*   **介绍**
    
    获取自选股分组列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | group\_type | [UserSecurityGroupType](./quote_quote.md#4977) | 分组类型 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回自选股分组数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   自选股分组数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | group\_name | str | 分组名 |
        | group\_type | [UserSecurityGroupType](./quote_quote.md#4977) | 分组类型 |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_user_security_group(group_type = UserSecurityGroupType.ALL)
    if ret == RET_OK:
        print(data)
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

            group_name group_type
    0          期权     SYSTEM
    ..         ...        ...
    12          C     CUSTOM
    
    [13 rows x 2 columns]
    

1  
2  
3  
4  
5  
6  

[#](./quote_get-user-security-group.md#2010-2)
 Qot\_GetUserSecurityGroup.proto
-----------------------------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取自选股分组列表
    
*   **参数**
    

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message C2S
    {
    	required int32 groupType = 1; // GroupType，自选股分组类型。
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

*   **返回**

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message GroupData
    {
    	required string groupName = 1; // 自选股分组名字
    	required int32 groupType = 2; // GroupType，自选股分组类型。
    }
    
    message S2C
    {
    	repeated GroupData groupList = 1; // 自选股分组列表
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3222
    

`uint GetUserSecurityGroup(QotGetUserSecurityGroup.Request req);`  
`virtual void OnReply_GetUserSecurityGroup(MMAPI_Conn client, uint nSerialNo, QotGetUserSecurityGroup.Response rsp);`

*   **介绍**
    
    获取自选股分组列表
    
*   **参数**
    

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message C2S
    {
    	required int32 groupType = 1; // GroupType，自选股分组类型。
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

*   **返回**

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message GroupData
    {
    	required string groupName = 1; // 自选股分组名字
    	required int32 groupType = 2; // GroupType，自选股分组类型。
    }
    
    message S2C
    {
    	repeated GroupData groupList = 1; // 自选股分组列表
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            QotGetUserSecurityGroup.C2S c2s = QotGetUserSecurityGroup.C2S.CreateBuilder()
                    .SetGroupType(QotGetUserSecurityGroup.GroupType.GroupType_All)
                .Build();
            QotGetUserSecurityGroup.Request req = QotGetUserSecurityGroup.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetUserSecurityGroup(req);
            Console.Write("Send QotGetUserSecurityGroup: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetUserSecurityGroup(MMAPI_Conn client, uint nSerialNo, QotGetUserSecurityGroup.Response rsp)
        {
            Console.Write("Reply: QotGetUserSecurityGroup: {0}\n", nSerialNo);
            Console.Write("groupName: {0}, groupType: {1} \n", rsp.S2C.GroupListList[0].GroupName, rsp.S2C.GroupListList[0].GroupType);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6826789498578847020
    Send QotGetUserSecurityGroup: 3
    Reply: QotGetUserSecurityGroup: 3
    groupName: 全部, groupType: 2
    

1  
2  
3  
4  

`int getUserSecurityGroup(QotGetUserSecurityGroup.Request req);`  
`void onReply_GetUserSecurityGroup(MMAPI_Conn client, int nSerialNo, QotGetUserSecurityGroup.Response rsp);`

*   **介绍**
    
    获取自选股分组列表
    
*   **参数**
    

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message C2S
    {
    	required int32 groupType = 1; // GroupType，自选股分组类型。
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

*   **返回**

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message GroupData
    {
    	required string groupName = 1; // 自选股分组名字
    	required int32 groupType = 2; // GroupType，自选股分组类型。
    }
    
    message S2C
    {
    	repeated GroupData groupList = 1; // 自选股分组列表
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            QotGetUserSecurityGroup.C2S c2s = QotGetUserSecurityGroup.C2S.newBuilder()
                    .setGroupType(QotGetUserSecurityGroup.GroupType.GroupType_All_VALUE)
                .build();
            QotGetUserSecurityGroup.Request req = QotGetUserSecurityGroup.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getUserSecurityGroup(req);
            System.out.printf("Send QotGetUserSecurityGroup: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetUserSecurityGroup(MMAPI_Conn client, int nSerialNo, QotGetUserSecurityGroup.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetUserSecurityGroup failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetUserSecurityGroup: %s\n", json);
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

*   **Output**

    Send QotGetUserSecurityGroup: 2
    Receive QotGetUserSecurityGroup: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "groupList": [{\
          "groupName": "全部",\
          "groupType": 2\
        }, {\
          "groupName": "特别关注",\
          "groupType": 2\
        }, ... {\
          "groupName": "债券",\
          "groupType": 2\
        }]
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

`moomoo::u32_t GetUserSecurityGroup(const Qot_GetUserSecurityGroup::Request &stReq);`  
`virtual void OnReply_GetUserSecurityGroup(moomoo::u32_t nSerialNo, const Qot_GetUserSecurityGroup::Response &stRsp) = 0;`

*   **介绍**
    
    获取自选股分组列表
    
*   **参数**
    

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message C2S
    {
    	required int32 groupType = 1; // GroupType，自选股分组类型。
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

*   **返回**

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message GroupData
    {
    	required string groupName = 1; // 自选股分组名字
    	required int32 groupType = 2; // GroupType，自选股分组类型。
    }
    
    message S2C
    {
    	repeated GroupData groupList = 1; // 自选股分组列表
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    		Qot_GetUserSecurityGroup::Request req;
    		Qot_GetUserSecurityGroup::C2S *c2s = req.mutable_c2s();
    		c2s->set_grouptype(3);
    
    		m_GetUserSecurityGroupSerialNo = m_pQotApi->GetUserSecurityGroup(req);
    		cout << "Request GetUserSecurityGroup SerialNo: " << m_GetUserSecurityGroupSerialNo << endl;
    	}
    
    	virtual void OnReply_GetUserSecurityGroup(moomoo::u32_t nSerialNo, const Qot_GetUserSecurityGroup::Response &stRsp){
            if(nSerialNo == m_GetUserSecurityGroupSerialNo)
            {
                cout << "OnReply_GetUserSecurityGroup SerialNo:" << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetUserSecurityGroupSerialNo;
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

*   **Output**

    connect
    Request GetUserSecurityGroup SerialNo: 4
    OnReply_GetUserSecurityGroup SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "groupList": [\
       {\
        "groupName": "全部",\
        "groupType": 2\
       },\
    ...\
       {\
        "groupName": "债券",\
        "groupType": 2\
       }\
      ]
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

`GetUserSecurityGroup(req);`

*   **介绍**
    
    获取自选股分组列表
    
*   **参数**
    

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message C2S
    {
    	required int32 groupType = 1; // GroupType，自选股分组类型。
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

*   **返回**

    // 自选股的类型
    enum GroupType
    {
    	GroupType_Unknown = 0; // 未知
        GroupType_Custom = 1; // 自定义分组
        GroupType_System = 2; // 系统分组
        GroupType_All = 3; // 全部分组
    }
    
    message GroupData
    {
    	required string groupName = 1; // 自选股分组名字
    	required int32 groupType = 2; // GroupType，自选股分组类型。
    }
    
    message S2C
    {
    	repeated GroupData groupList = 1; // 自选股分组列表
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

> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Qot_GetUserSecurityGroup } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetUserSecurityGroup(){
        const { RetType } = Common
        const { GroupType } = Qot_GetUserSecurityGroup
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        groupType: GroupType.GroupType_All,
                    },
                };
                
                websocket.GetUserSecurityGroup(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("UserSecurityGroup: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

*   **Output**

    UserSecurityGroup: errCode 0, retMsg , retType 0
    {
      "groupList": [{\
        "groupName": "全部",\
        "groupType": 2\
      }, {\
        "groupName": "特别关注",\
        "groupType": 2\
      }, ..., {\
        "groupName": "债券",\
        "groupType": 2\
      }]
    }
    stop
    

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

接口限制

*   每 30 秒内最多请求 10 次获取自选股分组接口

← [获取自选股列表](./quote_get-user-security.md) [修改自选股列表](./quote_modify-user-security.md)
 →

[获取自选股分组](./quote_get-user-security-group.md)