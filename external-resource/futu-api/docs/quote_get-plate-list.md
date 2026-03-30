[#](./quote_get-plate-list.md#436)
 获取板块列表
==================================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_plate_list(market, plate_class)`

*   **介绍**
    
    获取板块列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | market | [Market](./quote_quote.md#427) | 市场标识<br>(ℹ️ 注意：这里不区分沪和深，输入沪或者深都会返回沪深市场的子板块) |
    | plate\_class | [Plate](./quote_quote.md#1362) | 板块分类 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回板块列表数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   板块列表数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 板块代码 |
        | plate\_name | str | 板块名字 |
        | plate\_id | str | 板块 ID |
        
*   **Example**
    

    from futu import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_plate_list(Market.HK, Plate.CONCEPT)
    if ret == RET_OK:
        print(data)
        print(data['plate_name'][0])    # 取第一条的板块名称
        print(data['plate_name'].values.tolist())   # 转为 list
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
10  
11  

*   **Output**

        code plate_name plate_id
    0   HK.BK1000      做空集合股   BK1000
    ..        ...        ...      ...
    77  HK.BK1999       殡葬概念   BK1999
    
    [78 rows x 3 columns]
    做空集合股
    ['做空集合股', '阿里概念股', '雄安概念股', '苹果概念', '一带一路', '5G概念', '夜店股', '粤港澳大湾区', '特斯拉概念股', '啤酒', '疑似财技股', '体育用品', '稀土概念', '人民币升值概念', '抗疫概念', '新股与次新股', '腾讯概念', '云办公', 'SaaS概念', '在线教育', '汽车经销商', '挪威政府全球养老基金持仓', '武汉本地概念股', '核电', '内地医药股', '化妆美容股', '科网股', '公用股', '石油股', '电讯设备', '电力股', '手游股', '婴儿及小童用品股', '百货业股', '收租股', '港口运输股', '电信股', '环保', '煤炭股', '汽车股', '电池', '物流', '内地物业管理股', '农业股', '黄金股', '奢侈品股', '电力设备股', '连锁快餐店', '重型机械股', '食品股', '内险股', '纸业股', '水务股', '奶制品股', '光伏太阳能股', '内房股', '内地教育股', '家电股', '风电股', '蓝筹地产股', '内银股', '航空股', '石化股', '建材水泥股', '中资券商股', '高铁基建股', '燃气股', '公路及铁路股', '钢铁金属股', '华为概念', 'OLED概念', '工业大麻', '香港本地股', '香港零售股', '区块链', '猪肉概念', '节假日概念', '殡葬概念']
    

1  
2  
3  
4  
5  
6  
7  
8  

[#](./quote_get-plate-list.md#5574)
 Qot\_GetPlateSet.proto
---------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取板块列表
    
*   **参数**
    

    message C2S
    {
        required int32 market = 1; //Qot_Common.QotMarket，股票市场
        required int32 plateSetType = 2; //Qot_Common.PlateSetType，板块集合的类型
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

> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   板块集合类型枚举参见 [PlateSetType](./quote_quote.md#1362)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.PlateInfo plateInfoList = 1; //板块集合下的板块信息
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

> *   板块信息结构参见 [PlateInfo](./quote_quote.md#2571)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3204
    

`uint GetPlateSet(QotGetPlateSet.Request req);`  
`virtual void OnReply_GetPlateSet(FTAPI_Conn client, uint nSerialNo, QotGetPlateSet.Response rsp);`

*   **介绍**
    
    获取板块列表
    
*   **参数**
    

    message C2S
    {
        required int32 market = 1; //Qot_Common.QotMarket，股票市场
        required int32 plateSetType = 2; //Qot_Common.PlateSetType，板块集合的类型
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

> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   板块集合类型枚举参见 [PlateSetType](./quote_quote.md#1362)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.PlateInfo plateInfoList = 1; //板块集合下的板块信息
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

> *   板块信息结构参见 [PlateInfo](./quote_quote.md#2571)
>     
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
    
            QotGetPlateSet.C2S c2s = QotGetPlateSet.C2S.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetPlateSetType((int)QotCommon.PlateSetType.PlateSetType_Industry)
                .Build();
            QotGetPlateSet.Request req = QotGetPlateSet.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetPlateSet(req);
            Console.Write("Send QotGetPlateSet: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetPlateSet(FTAPI_Conn client, uint nSerialNo, QotGetPlateSet.Response rsp)
        {
            Console.Write("Reply: QotGetPlateSet: {0}\n", nSerialNo);
            Console.Write("code: {0},  name: {1} \n", rsp.S2C.PlateInfoListList[0].Plate.Code,
                rsp.S2C.PlateInfoListList[0].Name);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825726015499136889
    Send QotGetPlateSet: 3
    Reply: QotGetPlateSet: 3
    code: BK1001,  name: 乳制品
    

1  
2  
3  
4  

`int getPlateSet(QotGetPlateSet.Request req);`  
`void onReply_GetPlateSet(FTAPI_Conn client, int nSerialNo, QotGetPlateSet.Response rsp);`

*   **介绍**
    
    获取板块列表
    
*   **参数**
    

    message C2S
    {
        required int32 market = 1; //Qot_Common.QotMarket，股票市场
        required int32 plateSetType = 2; //Qot_Common.PlateSetType，板块集合的类型
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

> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   板块集合类型枚举参见 [PlateSetType](./quote_quote.md#1362)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.PlateInfo plateInfoList = 1; //板块集合下的板块信息
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

> *   板块信息结构参见 [PlateInfo](./quote_quote.md#2571)
>     
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
    
            QotGetPlateSet.C2S c2s = QotGetPlateSet.C2S.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .setPlateSetType(QotCommon.PlateSetType.PlateSetType_Industry_VALUE)
                .build();
            QotGetPlateSet.Request req = QotGetPlateSet.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getPlateSet(req);
            System.out.printf("Send QotGetPlateSet: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetPlateSet(FTAPI_Conn client, int nSerialNo, QotGetPlateSet.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetPlateSet failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetPlateSet: %s\n", json);
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

*   **Output**

    Send QotGetPlateSet: 2
    Receive QotGetPlateSet: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "plateInfoList": [{\
          "plate": {\
            "market": 1,\
            "code": "BK1001"\
          },\
          "name": "乳制品"\
        }, ... {\
          "plate": {\
            "market": 1,\
            "code": "BK1284"\
          },\
          "name": "中医药"\
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
19  
20  
21  

`Futu::u32_t GetPlateSet(const Qot_GetPlateSet::Request &stReq);`  
`virtual void OnReply_GetPlateSet(Futu::u32_t nSerialNo, const Qot_GetPlateSet::Response &stRsp) = 0;`

*   **介绍**
    
    获取板块列表
    
*   **参数**
    

    message C2S
    {
        required int32 market = 1; //Qot_Common.QotMarket，股票市场
        required int32 plateSetType = 2; //Qot_Common.PlateSetType，板块集合的类型
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

> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   板块集合类型枚举参见 [PlateSetType](./quote_quote.md#1362)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.PlateInfo plateInfoList = 1; //板块集合下的板块信息
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

> *   板块信息结构参见 [PlateInfo](./quote_quote.md#2571)
>     
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
    		Qot_GetPlateSet::Request req;
    		Qot_GetPlateSet::C2S *c2s = req.mutable_c2s();
    		c2s->set_market(1);
    		c2s->set_platesettype(0);
    
            m_GetPlateSetSerialNo = m_pQotApi->GetPlateSet(req);
            cout << "Request GetPlateSet SerialNo: " << m_GetPlateSetSerialNo << endl;
    	}
    
    	virtual void OnReply_GetPlateSet(Futu::u32_t nSerialNo, const Qot_GetPlateSet::Response &stRsp){
            if(nSerialNo == m_GetPlateSetSerialNo)
            {
                cout << "OnReply_GetPlateSet SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Qot *m_pQotApi;
    
        Futu::u32_t m_GetPlateSetSerialNo;
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

*   **Output**

    connect
    Request GetPlateSet SerialNo: 4
    OnReply_GetPlateSet SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "plateInfoList": [\
       {\
        "plate": {\
         "market": 1,\
         "code": "BK1000"\
        },\
        "name": "做空集合股"\
       },\
    ...\
       {\
        "plate": {\
         "market": 1,\
         "code": "BK1999"\
        },\
        "name": "殡葬概念"\
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
23  
24  
25  
26  
27  
28  

`GetPlateSet(req);`

*   **介绍**
    
    获取板块列表
    
*   **参数**
    

    message C2S
    {
        required int32 market = 1; //Qot_Common.QotMarket，股票市场
        required int32 plateSetType = 2; //Qot_Common.PlateSetType，板块集合的类型
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

> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   板块集合类型枚举参见 [PlateSetType](./quote_quote.md#1362)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.PlateInfo plateInfoList = 1; //板块集合下的板块信息
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

> *   板块信息结构参见 [PlateInfo](./quote_quote.md#2571)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    function QotGetPlateSet(){
        const { RetType } = Common
        const { QotMarket, PlateSetType } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        market: QotMarket.QotMarket_HK_Security,
                        plateSetType: PlateSetType.PlateSetType_All,
                    },
                };
    
                websocket.GetPlateSet(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("PlateSet: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    PlateSet: errCode 0, retMsg , retType 0
    {
      "plateInfoList": [{\
        "plate": {\
          "market": 1,\
          "code": "BK1000"\
        },\
        "name": "做空集合股"\
      }, {\
        "plate": {\
          "market": 1,\
          "code": "BK1001"\
        },\
        "name": "乳制品"\
      }, ..., {\
        "plate": {\
          "market": 1,\
          "code": "BK1998"\
        },\
        "name": "节假日概念"\
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
15  
16  
17  
18  
19  
20  
21  
22  
23  

接口限制

*   每 30 秒内最多请求 10 次获取板块列表接口

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`get_plate_list(market, plate_class)`

*   **介绍**
    
    获取板块列表
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | market | [Market](./quote_quote.md#427) | 市场标识<br>(ℹ️ 注意：这里不区分沪和深，输入沪或者深都会返回沪深市场的子板块) |
    | plate\_class | [Plate](./quote_quote.md#1362) | 板块分类 |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK，返回板块列表数据 |
    | str | 当 ret != RET\_OK，返回错误描述 |
    
    *   板块列表数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | code | str | 板块代码 |
        | plate\_name | str | 板块名字 |
        | plate\_id | str | 板块 ID |
        
*   **Example**
    

    from moomoo import *
    quote_ctx = OpenQuoteContext(host='127.0.0.1', port=11111)
    
    ret, data = quote_ctx.get_plate_list(Market.HK, Plate.CONCEPT)
    if ret == RET_OK:
        print(data)
        print(data['plate_name'][0])    # 取第一条的板块名称
        print(data['plate_name'].values.tolist())   # 转为 list
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
10  
11  

*   **Output**

        code plate_name plate_id
    0   HK.BK1000      做空集合股   BK1000
    ..        ...        ...      ...
    77  HK.BK1999       殡葬概念   BK1999
    
    [78 rows x 3 columns]
    做空集合股
    ['做空集合股', '阿里概念股', '雄安概念股', '苹果概念', '一带一路', '5G概念', '夜店股', '粤港澳大湾区', '特斯拉概念股', '啤酒', '疑似财技股', '体育用品', '稀土概念', '人民币升值概念', '抗疫概念', '新股与次新股', '腾讯概念', '云办公', 'SaaS概念', '在线教育', '汽车经销商', '挪威政府全球养老基金持仓', '武汉本地概念股', '核电', '内地医药股', '化妆美容股', '科网股', '公用股', '石油股', '电讯设备', '电力股', '手游股', '婴儿及小童用品股', '百货业股', '收租股', '港口运输股', '电信股', '环保', '煤炭股', '汽车股', '电池', '物流', '内地物业管理股', '农业股', '黄金股', '奢侈品股', '电力设备股', '连锁快餐店', '重型机械股', '食品股', '内险股', '纸业股', '水务股', '奶制品股', '光伏太阳能股', '内房股', '内地教育股', '家电股', '风电股', '蓝筹地产股', '内银股', '航空股', '石化股', '建材水泥股', '中资券商股', '高铁基建股', '燃气股', '公路及铁路股', '钢铁金属股', '华为概念', 'OLED概念', '工业大麻', '香港本地股', '香港零售股', '区块链', '猪肉概念', '节假日概念', '殡葬概念']
    

1  
2  
3  
4  
5  
6  
7  
8  

[#](./quote_get-plate-list.md#5574-2)
 Qot\_GetPlateSet.proto
-----------------------------------------------------------------------------------------------------

*   **介绍**
    
    获取板块列表
    
*   **参数**
    

    message C2S
    {
        required int32 market = 1; //Qot_Common.QotMarket，股票市场
        required int32 plateSetType = 2; //Qot_Common.PlateSetType，板块集合的类型
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

> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   板块集合类型枚举参见 [PlateSetType](./quote_quote.md#1362)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.PlateInfo plateInfoList = 1; //板块集合下的板块信息
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

> *   板块信息结构参见 [PlateInfo](./quote_quote.md#2571)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    3204
    

`uint GetPlateSet(QotGetPlateSet.Request req);`  
`virtual void OnReply_GetPlateSet(MMAPI_Conn client, uint nSerialNo, QotGetPlateSet.Response rsp);`

*   **介绍**
    
    获取板块列表
    
*   **参数**
    

    message C2S
    {
        required int32 market = 1; //Qot_Common.QotMarket，股票市场
        required int32 plateSetType = 2; //Qot_Common.PlateSetType，板块集合的类型
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

> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   板块集合类型枚举参见 [PlateSetType](./quote_quote.md#1362)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.PlateInfo plateInfoList = 1; //板块集合下的板块信息
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

> *   板块信息结构参见 [PlateInfo](./quote_quote.md#2571)
>     
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
    
            QotGetPlateSet.C2S c2s = QotGetPlateSet.C2S.CreateBuilder()
                    .SetMarket((int)QotCommon.QotMarket.QotMarket_HK_Security)
                    .SetPlateSetType((int)QotCommon.PlateSetType.PlateSetType_Industry)
                .Build();
            QotGetPlateSet.Request req = QotGetPlateSet.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = qot.GetPlateSet(req);
            Console.Write("Send QotGetPlateSet: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Qot onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetPlateSet(MMAPI_Conn client, uint nSerialNo, QotGetPlateSet.Response rsp)
        {
            Console.Write("Reply: QotGetPlateSet: {0}\n", nSerialNo);
            Console.Write("code: {0},  name: {1} \n", rsp.S2C.PlateInfoListList[0].Plate.Code,
                rsp.S2C.PlateInfoListList[0].Name);
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

*   **Output**

    Qot onInitConnect: ret=0 desc= connID=6825726015499136889
    Send QotGetPlateSet: 3
    Reply: QotGetPlateSet: 3
    code: BK1001,  name: 乳制品
    

1  
2  
3  
4  

`int getPlateSet(QotGetPlateSet.Request req);`  
`void onReply_GetPlateSet(MMAPI_Conn client, int nSerialNo, QotGetPlateSet.Response rsp);`

*   **介绍**
    
    获取板块列表
    
*   **参数**
    

    message C2S
    {
        required int32 market = 1; //Qot_Common.QotMarket，股票市场
        required int32 plateSetType = 2; //Qot_Common.PlateSetType，板块集合的类型
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

> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   板块集合类型枚举参见 [PlateSetType](./quote_quote.md#1362)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.PlateInfo plateInfoList = 1; //板块集合下的板块信息
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

> *   板块信息结构参见 [PlateInfo](./quote_quote.md#2571)
>     
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
    
            QotGetPlateSet.C2S c2s = QotGetPlateSet.C2S.newBuilder()
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .setPlateSetType(QotCommon.PlateSetType.PlateSetType_Industry_VALUE)
                .build();
            QotGetPlateSet.Request req = QotGetPlateSet.Request.newBuilder().setC2S(c2s).build();
            int seqNo = qot.getPlateSet(req);
            System.out.printf("Send QotGetPlateSet: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetPlateSet(MMAPI_Conn client, int nSerialNo, QotGetPlateSet.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("QotGetPlateSet failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive QotGetPlateSet: %s\n", json);
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

*   **Output**

    Send QotGetPlateSet: 2
    Receive QotGetPlateSet: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "plateInfoList": [{\
          "plate": {\
            "market": 1,\
            "code": "BK1001"\
          },\
          "name": "乳制品"\
        }, ... {\
          "plate": {\
            "market": 1,\
            "code": "BK1284"\
          },\
          "name": "中医药"\
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
19  
20  
21  

`moomoo::u32_t GetPlateSet(const Qot_GetPlateSet::Request &stReq);`  
`virtual void OnReply_GetPlateSet(moomoo::u32_t nSerialNo, const Qot_GetPlateSet::Response &stRsp) = 0;`

*   **介绍**
    
    获取板块列表
    
*   **参数**
    

    message C2S
    {
        required int32 market = 1; //Qot_Common.QotMarket，股票市场
        required int32 plateSetType = 2; //Qot_Common.PlateSetType，板块集合的类型
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

> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   板块集合类型枚举参见 [PlateSetType](./quote_quote.md#1362)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.PlateInfo plateInfoList = 1; //板块集合下的板块信息
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

> *   板块信息结构参见 [PlateInfo](./quote_quote.md#2571)
>     
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
    		Qot_GetPlateSet::Request req;
    		Qot_GetPlateSet::C2S *c2s = req.mutable_c2s();
    		c2s->set_market(1);
    		c2s->set_platesettype(0);
    
            m_GetPlateSetSerialNo = m_pQotApi->GetPlateSet(req);
            cout << "Request GetPlateSet SerialNo: " << m_GetPlateSetSerialNo << endl;
    	}
    
    	virtual void OnReply_GetPlateSet(moomoo::u32_t nSerialNo, const Qot_GetPlateSet::Response &stRsp){
            if(nSerialNo == m_GetPlateSetSerialNo)
            {
                cout << "OnReply_GetPlateSet SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Qot *m_pQotApi;
    
        moomoo::u32_t m_GetPlateSetSerialNo;
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

*   **Output**

    connect
    Request GetPlateSet SerialNo: 4
    OnReply_GetPlateSet SerialNo: 4
    {
     "retType": 0,
     "retMsg": "",
     "errCode": 0,
     "s2c": {
      "plateInfoList": [\
       {\
        "plate": {\
         "market": 1,\
         "code": "BK1000"\
        },\
        "name": "做空集合股"\
       },\
    ...\
       {\
        "plate": {\
         "market": 1,\
         "code": "BK1999"\
        },\
        "name": "殡葬概念"\
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
23  
24  
25  
26  
27  
28  

`GetPlateSet(req);`

*   **介绍**
    
    获取板块列表
    
*   **参数**
    

    message C2S
    {
        required int32 market = 1; //Qot_Common.QotMarket，股票市场
        required int32 plateSetType = 2; //Qot_Common.PlateSetType，板块集合的类型
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

> *   市场类型参见 [QotMarket](./quote_quote.md#427)
>     
> *   板块集合类型枚举参见 [PlateSetType](./quote_quote.md#1362)
>     

*   **返回**

    message S2C
    {
        repeated Qot_Common.PlateInfo plateInfoList = 1; //板块集合下的板块信息
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

> *   板块信息结构参见 [PlateInfo](./quote_quote.md#2571)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    function QotGetPlateSet(){
        const { RetType } = Common
        const { QotMarket, PlateSetType } = Qot_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
    
                const req = {
                    c2s: {
                        market: QotMarket.QotMarket_HK_Security,
                        plateSetType: PlateSetType.PlateSetType_All,
                    },
                };
    
                websocket.GetPlateSet(req)
                .then((res) => {
                    let { errCode, retMsg, retType,s2c } = res
                    console.log("PlateSet: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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

    PlateSet: errCode 0, retMsg , retType 0
    {
      "plateInfoList": [{\
        "plate": {\
          "market": 1,\
          "code": "BK1000"\
        },\
        "name": "做空集合股"\
      }, {\
        "plate": {\
          "market": 1,\
          "code": "BK1001"\
        },\
        "name": "乳制品"\
      }, ..., {\
        "plate": {\
          "market": 1,\
          "code": "BK1998"\
        },\
        "name": "节假日概念"\
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
15  
16  
17  
18  
19  
20  
21  
22  
23  

接口限制

*   每 30 秒内最多请求 10 次获取板块列表接口

← [获取板块内股票列表](./quote_get-plate-stock.md) [获取静态数据](./quote_get-static-info.md)
 →

[获取板块列表](./quote_get-plate-list.md)