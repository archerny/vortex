[#](./trade_get-funds.md#4346)
 查询账户资金
==============================================================================

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`accinfo_query(trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, refresh_cache=False, currency=Currency.HKD, asset_category=AssetCategory.NONE)`

*   **介绍**
    
    查询交易业务账户的资产净值、证券市值、现金、购买力等资金数据。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。)<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。)<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | refresh\_cache | bool | 是否刷新缓存<br>(ℹ️ *   True：立即向富途服务器重新请求数据，不使用 OpenD 的缓存，此时会受到接口限频的限制)<br>*   False：使用 OpenD 的缓存（特殊情况导致缓存没有及时更新才需要刷新） |
    | currency | [Currency](./trade_trade.md#8019) | 计价货币<br>(ℹ️ *   仅期货账户、综合证券账户适用，其它账户类型会忽略此参数)<br>*   返回的 DataFrame 中，除了明确指明了货币的字段，其它资金相关字段都以此参数换算 |
    | asset\_category | [AssetCategory](./trade_trade.md#4752) | 资产类别<br>(ℹ️ 仅对日本券商生效) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回资金数据 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   资金数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | power | float | 最大购买力<br>(ℹ️ *   此字段是按照 50% 的融资初始保证金率计算得到的 **近似值**。但事实上，每个标的的融资初始保证金率并不相同。我们建议您使用 [查询最大可买可卖](./trade_get-max-trd-qtys.md))<br>     接口返回的 **最大可买** 字段，来判断实际可买入的最大数量。 |
        | max\_power\_short | float | 卖空购买力<br>(ℹ️ *   此字段是按照 60% 的融券保证金率计算得到的 **近似值**。但事实上，每个标的的融券保证金率并不相同。我们建议您使用 [查询最大可买可卖](./trade_get-max-trd-qtys.md))<br>     接口返回的 **可卖空** 字段，来判断实际可卖空的最大数量。 |
        | net\_cash\_power | float | 现金购买力<br>(ℹ️ 已废弃，请使用usd\_net\_cash\_power等字段获取分币种的现金购买力) |
        | total\_assets | float | 总资产净值<br>(ℹ️ 总资产净值 = 证券资产净值 + 基金资产净值 + 债券资产净值) |
        | securities\_assets | float | 证券资产净值<br>(ℹ️ 最低 OpenD 版本要求：8.2.4218) |
        | fund\_assets | float | 基金资产净值<br>(ℹ️ *   综合账户返回结果为总基金资产净值，暂时不支持查询港元基金资产和美元基金资产)<br>*   最低 OpenD 版本要求：8.2.4218 |
        | bond\_assets | float | 债券资产净值<br>(ℹ️ 最低 OpenD 版本要求：8.2.4218) |
        | cash | float | 现金<br>(ℹ️ 已废弃，请使用us\_cash等字段获取分币种的现金) |
        | market\_val | float | 证券市值<br>(ℹ️ 仅证券账户适用) |
        | long\_mv | float | 多头市值 |
        | short\_mv | float | 空头市值 |
        | pending\_asset | float | 在途资产 |
        | interest\_charged\_amount | float | 计息金额 |
        | frozen\_cash | float | 冻结资金 |
        | avl\_withdrawal\_cash | float | 现金可提<br>(ℹ️ 仅证券账户适用) |
        | max\_withdrawal | float | 最大可提<br>(ℹ️ 仅富途证券（香港）的证券账户适用) |
        | currency | [Currency](./trade_trade.md#8019) | 计价货币<br>(ℹ️ 仅综合证券账户、期货账户适用) |
        | available\_funds | float | 可用资金<br>(ℹ️ 仅期货账户适用) |
        | unrealized\_pl | float | 未实现盈亏<br>(ℹ️ 仅期货账户适用) |
        | realized\_pl | float | 已实现盈亏<br>(ℹ️ 仅期货账户适用) |
        | risk\_level | [CltRiskLevel](./trade_trade.md#9239) | 风控状态<br>(ℹ️ 仅期货账户适用。建议统一使用 risk\_status 字段获取证券、期货账户的风险状态) |
        | risk\_status | [CltRiskStatus](./trade_trade.md#3989) | 风险状态<br>(ℹ️ *   证券账户和期货账户均适用)<br>*   共分 9 个等级， `LEVEL1`是最安全，`LEVEL9`是最危险 |
        | initial\_margin | float | 初始保证金 |
        | margin\_call\_margin | float | Margin Call 保证金 |
        | maintenance\_margin | float | 维持保证金 |
        | hk\_cash | float | 港元现金<br>(ℹ️ 此字段表示该币种实际的值，而不是以该币种计价的值) |
        | hk\_avl\_withdrawal\_cash | float | 港元可提<br>(ℹ️ 此字段表示该币种实际的值，而不是以该币种计价的值) |
        | hkd\_net\_cash\_power | float | 港元现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：8.7 |
        | hkd\_assets | float | 港股资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：9.0.5008 |
        | us\_cash | float | 美元现金<br>(ℹ️ 此字段表示该币种实际的值，而不是以该币种计价的值) |
        | us\_avl\_withdrawal\_cash | float | 美元可提<br>(ℹ️ 此字段表示该币种实际的值，而不是以该币种计价的值) |
        | usd\_net\_cash\_power | float | 美元现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：8.7 |
        | usd\_assets | float | 美股资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：9.0.5008 |
        | cn\_cash | float | 人民币现金<br>(ℹ️ 此字段表示该币种实际的值，而不是以该币种计价的值) |
        | cn\_avl\_withdrawal\_cash | float | 人民币可提<br>(ℹ️ 此字段表示该币种实际的值，而不是以该币种计价的值) |
        | cnh\_net\_cash\_power | float | 人民币现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：8.7 |
        | cnh\_assets | float | A股资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：9.0.5008 |
        | jp\_cash | float | 日元现金<br>(ℹ️ *   仅期货账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低 Futu API 版本要求：5.8.2008 |
        | jp\_avl\_withdrawal\_cash | float | 日元可提<br>(ℹ️ *   仅期货账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低 Futu API 版本要求：5.8.2008 |
        | jpy\_net\_cash\_power | float | 日元现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：8.7 |
        | jpy\_assets | float | 日股资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：9.0.5008 |
        | sg\_cash | float | 新元现金<br>(ℹ️ *   仅期货账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值 |
        | sg\_avl\_withdrawal\_cash | float | 新元可提<br>(ℹ️ *   仅期货账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值 |
        | sgd\_net\_cash\_power | float | 新元现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：8.7 |
        | sgd\_assets | float | 新股资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：9.0.5008 |
        | au\_cash | float | 澳元现金<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低 Futu API 版本要求：5.8.2008 |
        | au\_avl\_withdrawal\_cash | float | 澳元可提<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低 Futu API 版本要求：5.8.2008 |
        | aud\_net\_cash\_power | float | 澳元现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：8.7 |
        | aud\_assets | float | 澳股资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：9.0.5008 |
        | ca\_cash | float | 加元现金<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：10.0.6008 |
        | ca\_avl\_withdrawal\_cash | float | 加元可提<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：10.0.6008 |
        | cad\_net\_cash\_power | float | 加元现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：10.0.6008 |
        | cad\_assets | float | 加元资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：10.0.6008 |
        | my\_cash | float | 令吉现金<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：10.0.6008 |
        | my\_avl\_withdrawal\_cash | float | 令吉可提<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：10.0.6008 |
        | myr\_net\_cash\_power | float | 令吉现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：10.0.6008 |
        | myr\_assets | float | 令吉资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：10.0.6008 |
        | is\_pdt | bool | 是否为 PDT 账户<br>(ℹ️ True：是 PDT 账户，False：不是 PDT 账户)  <br>仅moomoo证券(美国)账户适用  <br>最低 OpenD 版本要求：5.8.2008 |
        | pdt\_seq | string | 剩余日内交易次数<br>(ℹ️ 仅moomoo证券(美国)账户适用)  <br>最低 OpenD 版本要求：5.8.2008 |
        | beginning\_dtbp | float | 初始日内交易购买力<br>(ℹ️ 仅被标记为 PDT 的moomoo证券(美国)账户适用)  <br>最低 OpenD 版本要求：5.8.2008 |
        | remaining\_dtbp | float | 剩余日内交易购买力<br>(ℹ️ 仅被标记为 PDT 的moomoo证券(美国)账户适用)  <br>最低 OpenD 版本要求：5.8.2008 |
        | dt\_call\_amount | float | 日内交易待缴金额<br>(ℹ️ 仅被标记为 PDT 的moomoo证券(美国)账户适用)  <br>最低 OpenD 版本要求：5.8.2008 |
        | dt\_status | [DtStatus](./trade_trade.md#1860) | 日内交易限制情况<br>(ℹ️ 仅被标记为 PDT 的moomoo证券(美国)账户适用)  <br>最低 OpenD 版本要求：5.8.2008 |
        
*   **Example**
    

    from futu import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.HK, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUSECURITIES)
    ret, data = trd_ctx.accinfo_query()
    if ret == RET_OK:
        print(data)
        print(data['power'][0])  # 取第一行的购买力
        print(data['power'].values.tolist())  # 转为 list
    else:
        print('accinfo_query error: ', data)
    trd_ctx.close()  # 关闭当条连接
    

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

*   **Output**

    power  max_power_short  net_cash_power  total_assets  securities_assets  fund_assets  bond_assets   cash   market_val      long_mv   short_mv  pending_asset  interest_charged_amount  frozen_cash  avl_withdrawal_cash  max_withdrawal currency available_funds unrealized_pl realized_pl risk_level risk_status  initial_margin  margin_call_margin  maintenance_margin  hk_cash  hk_avl_withdrawal_cash  hkd_net_cash_power  hkd_assets  us_cash  us_avl_withdrawal_cash  usd_net_cash_power  usd_assets  cn_cash  cn_avl_withdrawal_cash  cnh_net_cash_power  cnh_assets  jp_cash  jp_avl_withdrawal_cash  jpy_net_cash_power jpy_assets  sg_cash sg_avl_withdrawal_cash sgd_net_cash_power sgd_assets  au_cash au_avl_withdrawal_cash aud_net_cash_power aud_assets  ca_cash ca_avl_withdrawal_cash cad_net_cash_power cad_assets  my_cash my_avl_withdrawal_cash myr_net_cash_power myr_assets  is_pdt pdt_seq beginning_dtbp remaining_dtbp dt_call_amount dt_status
    0  465453.903307    465453.903307             0.0   289932.0404        197028.2204     92903.82          0.0  25.18  197003.0448  211960.7568 -14957.712            0.0                      0.0    25.930845                  0.0             0.0      HKD             N/A           N/A         N/A        N/A      LEVEL3   219346.648525       288656.787955       181250.967601      0.0                     0.0          13225.7955     0.0   3.24                     0.0           9656.4365      0.0    0.0                     0.0                 0.0    0.0      0.0                     0.0                 0.0     0.0    N/A                    N/A                N/A     0.0    N/A                    N/A                N/A    0.0    N/A                    N/A                N/A    0.0    N/A                    N/A                N/A    0.0        N/A     N/A            N/A            N/A            N/A       N/A
    465453.903307
    [465453.903307]
    

1  
2  
3  
4  

[#](./trade_get-funds.md#5465)
 Trd\_GetFunds.proto
-------------------------------------------------------------------------------------------

*   **介绍**
    
    查询交易业务账户的资产净值、证券市值、现金、购买力等资金数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional bool refreshCache = 2; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 currency = 3;	//货币种类，参见 Trd_Common.Currency。期货和综合证券账户必填，其它账户忽略 
    	optional int32 assetCategory = 4; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   货币类型结构参见 [Currency](./trade_trade.md#8019)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](./trade_trade.md#4752)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.Funds funds = 2; //账户资金
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   账户资金结构参见 [Funds](./trade_trade.md#3175)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    2101
    

`uint GetFunds(TrdGetFunds.Request req);`  
`virtual void OnReply_GetFunds(FTAPI_Conn client, uint nSerialNo, ${proto_name.Response rsp);`

*   **介绍**
    
    查询交易业务账户的资产净值、证券市值、现金、购买力等资金数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional bool refreshCache = 2; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 currency = 3;	//货币种类，参见 Trd_Common.Currency。期货和综合证券账户必填，其它账户忽略 
    	optional int32 assetCategory = 4; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   货币类型结构参见 [Currency](./trade_trade.md#8019)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](./trade_trade.md#4752)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.Funds funds = 2; //账户资金
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   账户资金结构参见 [Funds](./trade_trade.md#3175)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.CreateBuilder()
                    .SetAccID(281756455988247915L)
                    .SetTrdEnv((int)TrdCommon.TrdEnv.TrdEnv_Real)
                    .SetTrdMarket((int)TrdCommon.TrdMarket.TrdMarket_HK)
                    .Build();
            TrdGetFunds.C2S c2s = TrdGetFunds.C2S.CreateBuilder()
                    .SetCurrency((int)TrdCommon.Currency.Currency_HKD)
                    .SetHeader(header)
                    .Build();
            TrdGetFunds.Request req = TrdGetFunds.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetFunds(req);
            Console.Write("Send TrdGetFunds: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(FTAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetFunds(FTAPI_Conn client, uint nSerialNo, TrdGetFunds.Response rsp)
        {
            Console.Write("Reply: TrdGetFunds: {0}\n", nSerialNo);
            Console.Write("OnReply_GetFunds: {0}\n", rsp.S2C.ToJson());
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
52  
53  
54  
55  
56  

*   **Output**

    Trd onInitConnect: ret = 0 desc = connID = 7257654332030703443
    Send TrdGetFunds: 4
    Reply: TrdGetFunds: 4
    OnReply_GetFunds: {
    	"header": {
    		"trdEnv": 1,
    		"accID": 283726802395277157,
    		"trdMarket": 6
    	},
    	"funds": {
    		"power": 3030.61116601,
    		"totalAssets": 152909.6564,
    		"cash": -3586.52,
    		"marketVal": 156496.1717,
    		"frozenCash": 665.78224097,
    		"debtCash": 22533.9124353,
    		"avlWithdrawalCash": 3030.61,
    		"currency": 1,
    		"initialMargin": 113686.06186064694,
    		"maintenanceMargin": 107790.01800335373,
    		"cashInfoList": [{\
    			"currency": 0,\
    			"cash": 0,\
    			"availableBalance": 0,\
    			"netCashPower": 0\
    		}, {\
    			"currency": 1,\
    			"cash": 9190.04,\
    			"availableBalance": 3030.61,\
    			"netCashPower": 3030.61116601\
    		}, {\
    			"currency": 3,\
    			"cash": -836.36,\
    			"availableBalance": 0,\
    			"netCashPower": 0\
    		}, {\
    			"currency": 2,\
    			"cash": 1245.03,\
    			"availableBalance": 0,\
    			"netCashPower": 17.97126495\
    		}, {\
    			"currency": 5,\
    			"cash": -3070.32,\
    			"availableBalance": 0,\
    			"netCashPower": 0\
    		}, {\
    			"currency": 4,\
    			"cash": -68314,\
    			"availableBalance": 0,\
    			"netCashPower": 0\
    		}],
    		"maxPowerShort": 3030.61116601,
    		"netCashPower": 0,
    		"longMv": 111306.2256,
    		"shortMv": -8875.8239,
    		"pendingAsset": 0,
    		"riskStatus": 3,
    		"marginCallMargin": 111801.53345828,
    		"securitiesAssets": 98843.8864,
    		"fundAssets": 54065.77,
    		"bondAssets": 0
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

`int getFunds(TrdGetFunds.Request req);`  
`void onReply_GetFunds(FTAPI_Conn client, int nSerialNo, ${proto_name.Response rsp);`

*   **介绍**
    
    查询交易业务账户的资产净值、证券市值、现金、购买力等资金数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional bool refreshCache = 2; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 currency = 3;	//货币种类，参见 Trd_Common.Currency。期货和综合证券账户必填，其它账户忽略 
    	optional int32 assetCategory = 4; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   货币类型结构参见 [Currency](./trade_trade.md#8019)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](./trade_trade.md#4752)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.Funds funds = 2; //账户资金
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   账户资金结构参见 [Funds](./trade_trade.md#3175)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.newBuilder()
                    .setAccID(281756455988247915L)
                    .setTrdEnv(TrdCommon.TrdEnv.TrdEnv_Real_VALUE)
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)
                    .build();
            TrdGetFunds.C2S c2s = TrdGetFunds.C2S.newBuilder()
                    .setHeader(header)
                    .setCurrency(TrdCommon.Currency.Currency_HKD_VALUE)
                    .build();
            TrdGetFunds.Request req = TrdGetFunds.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getFunds(req);
            System.out.printf("Send TrdGetFunds: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetFunds(FTAPI_Conn client, int nSerialNo, TrdGetFunds.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetFunds failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetFunds: %s\n", json);
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
65  
66  
67  
68  

*   **Output**

    Send TrdGetFunds: 2
    Receive TrdGetFunds: {
      "retType": 0,
      "retMsg": "",
      "errCode": 0,
      "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756455988247915",
          "trdMarket": 1
        },
        "funds": {
            "power": 0.61760332
            "totalAssets": 15222.3336
            "cash": -19.83
            "marketVal": 15242.1666
            "frozenCash": 158.34982707
            "debtCash": 2644.58147475
            "avlWithdrawalCash": 1.24
            "currency": 1
            "initialMargin": 14904.738361057309
            "maintenanceMargin": 14805.9362278707
            "cashInfoList" {
            "currency": 2
            "cash": -95.36
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 0
            "cash": 0.0
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 1
            "cash": -1097.43
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 4
            "cash": -10267.0
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 3
            "cash": 2049.58
            "availableBalance": 0.6
            "netCashPower": 0.60345798
            }
            "cashInfoList": {
            "currency": 5
            "cash": 18.62
            "availableBalance": 0.1
            "netCashPower": 0.10953234
            }
            "maxPowerShort": 0.61760332
            "netCashPower": 0.0
            "longMv": 1436.1127
            "shortMv": -168.096
            "pendingAsset": 0.0
            "riskStatus": 3
            "marginCallMargin": 14835.22139711
            "securitiesAssets": 1248.1836
            "fundAssets": 13974.15
            "bondAssets": 0.0
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
69  
70  
71  

`Futu::u32_t GetFunds(const Trd_GetFunds::Request &stReq);`  
`virtual void OnReply_GetFunds(Futu::u32_t nSerialNo, const Trd_GetFunds::Response &stRsp) = 0;`

*   **介绍**
    
    查询交易业务账户的资产净值、证券市值、现金、购买力等资金数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional bool refreshCache = 2; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 currency = 3;	//货币种类，参见 Trd_Common.Currency。期货和综合证券账户必填，其它账户忽略 
    	optional int32 assetCategory = 4; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   货币类型结构参见 [Currency](./trade_trade.md#8019)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](./trade_trade.md#4752)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.Funds funds = 2; //账户资金
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   账户资金结构参见 [Funds](./trade_trade.md#3175)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    		Trd_GetFunds::Request req;
    		Trd_GetFunds::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    
            m_GetFundsSerialNo = m_pTrdApi->GetFunds(req);
            cout << "Request GetFunds SerialNo: " << m_GetFundsSerialNo << endl;
    	}
    
    	virtual void OnReply_GetFunds(Futu::u32_t nSerialNo, const Trd_GetFunds::Response &stRsp){
            if(nSerialNo == m_GetFundsSerialNo)
            {
                cout << "OnReply_GetFunds SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	FTAPI_Trd *m_pTrdApi;
    
        Futu::u32_t m_GetFundsSerialNo;
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
    Request GetFunds SerialNo: 4
    OnReply_GetFunds SerialNo: 4
    {
        "retType": 0,
        "retMsg": "",
        "errCode": 0,
        "s2c": {
            "header": {
            "trdEnv": 1,
            "accID": "281756455988247915",
            "trdMarket": 1
            },
            "funds": {
                "power": 0.61760332
                "totalAssets": 15222.3336
                "cash": -19.83
                "marketVal": 15242.1666
                "frozenCash": 158.34982707
                "debtCash": 2644.58147475
                "avlWithdrawalCash": 1.24
                "currency": 1
                "initialMargin": 14904.738361057309
                "maintenanceMargin": 14805.9362278707
                "cashInfoList" {
                "currency": 2
                "cash": -95.36
                "availableBalance": 0.0
                "netCashPower": 0.0
                }
                "cashInfoList": {
                "currency": 0
                "cash": 0.0
                "availableBalance": 0.0
                "netCashPower": 0.0
                }
                "cashInfoList": {
                "currency": 1
                "cash": -1097.43
                "availableBalance": 0.0
                "netCashPower": 0.0
                }
                "cashInfoList": {
                "currency": 4
                "cash": -10267.0
                "availableBalance": 0.0
                "netCashPower": 0.0
                }
                "cashInfoList": {
                "currency": 3
                "cash": 2049.58
                "availableBalance": 0.6
                "netCashPower": 0.60345798
                }
                "cashInfoList": {
                "currency": 5
                "cash": 18.62
                "availableBalance": 0.1
                "netCashPower": 0.10953234
                }
                "maxPowerShort": 0.61760332
                "netCashPower": 0.0
                "longMv": 1436.1127
                "shortMv": -168.096
                "pendingAsset": 0.0
                "riskStatus": 3
                "marginCallMargin": 14835.22139711
                "securitiesAssets": 1248.1836
                "fundAssets": 13974.15
                "bondAssets": 0.0
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
69  
70  
71  
72  
73  
74  

`GetFunds(req);`

*   **介绍**
    
    查询交易业务账户的资产净值、证券市值、现金、购买力等资金数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional bool refreshCache = 2; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 currency = 3;	//货币种类，参见 Trd_Common.Currency。期货和综合证券账户必填，其它账户忽略 
    	optional int32 assetCategory = 4; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   货币类型结构参见 [Currency](./trade_trade.md#8019)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](./trade_trade.md#4752)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.Funds funds = 2; //账户资金
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   账户资金结构参见 [Funds](./trade_trade.md#3175)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import ftWebsocket from "futu-api";
    import { ftCmdID } from "futu-api";
    import { Common, Qot_Common, Trd_Common } from "futu-api/proto";
    import beautify from "js-beautify";
    
    TrdGetFunds(){
        const { RetType } = Common
        const { TrdEnv, TrdMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new ftWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.GetAccList({
                    c2s: {
                        userID: 0,
                    },
                }).then((res) => {
                    let { retType, s2c: { accList }  } = res
                    if(retType == RetType.RetType_Succeed){
                        let acc = accList.filter((item)=>{ 
                            return item.trdEnv == TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})
                        })[0]; // 样例取第一个香港市场虚拟环境账户
    
                        const req = {
                            c2s: {
                                header: {
                                    trdEnv: acc.trdEnv,
                                    accID: acc.accID,
                                    trdMarket: TrdMarket.TrdMarket_HK,
                                }
                            },
                        };
    
                        websocket.GetFunds(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetFunds: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
    
                    }
                })
                .catch((error) => {
                    console.log("GetAccList error:", error);
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

    GetFunds: errCode 0, retMsg , retType 0
    {
        "header": {
          "trdEnv": 1,
          "accID": "281756455988247915",
          "trdMarket": 1
        },
        "funds": {
            "power": 0.61760332
            "totalAssets": 15222.3336
            "cash": -19.83
            "marketVal": 15242.1666
            "frozenCash": 158.34982707
            "debtCash": 2644.58147475
            "avlWithdrawalCash": 1.24
            "currency": 1
            "initialMargin": 14904.738361057309
            "maintenanceMargin": 14805.9362278707
            "cashInfoList" {
            "currency": 2
            "cash": -95.36
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 0
            "cash": 0.0
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 1
            "cash": -1097.43
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 4
            "cash": -10267.0
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 3
            "cash": 2049.58
            "availableBalance": 0.6
            "netCashPower": 0.60345798
            }
            "cashInfoList": {
            "currency": 5
            "cash": 18.62
            "availableBalance": 0.1
            "netCashPower": 0.10953234
            }
            "maxPowerShort": 0.61760332
            "netCashPower": 0.0
            "longMv": 1436.1127
            "shortMv": -168.096
            "pendingAsset": 0.0
            "riskStatus": 3
            "marginCallMargin": 14835.22139711
            "securitiesAssets": 1248.1836
            "fundAssets": 13974.15
            "bondAssets": 0.0
        }
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

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询账户资金接口
*   调用此接口，只有在刷新缓存时，才受到限频限制

*   Python
*   Proto
*   C#
*   Java
*   C++
*   JavaScript

`accinfo_query(trd_env=TrdEnv.REAL, acc_id=0, acc_index=0, refresh_cache=False, currency=Currency.HKD, asset_category=AssetCategory.NONE)`

*   **介绍**
    
    查询交易业务账户的资产净值、证券市值、现金、购买力等资金数据。
    
*   **参数**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | trd\_env | [TrdEnv](./trade_trade.md#6374) | 交易环境 |
    | acc\_id | int | 交易业务账户 ID<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。)<br>*   当 acc\_id 传 0 时， 以 acc\_index 指定的账户为准<br>*   当 acc\_id 传 ID 号时（不为 0 ），以 acc\_id 指定的账户为准 |
    | acc\_index | int | 交易业务账户列表中的账户序号<br>(ℹ️ *   acc\_id 和 acc\_index 都可用于指定交易业务账户，二选一即可，推荐使用 acc\_id。acc\_index 会在新开立/注销账户时发生变动，导致您指定的账户与实际交易账户不一致。)<br>*   acc\_index 默认为 0，表示指定第 1 个交易业务账户 |
    | refresh\_cache | bool | 是否刷新缓存<br>(ℹ️ *   True：立即向moomoo 服务器重新请求数据，不使用 OpenD 的缓存，此时会受到接口限频的限制)<br>*   False：使用 OpenD 的缓存（特殊情况导致缓存没有及时更新才需要刷新） |
    | currency | [Currency](./trade_trade.md#8019) | 资金的展示货币<br>(ℹ️ *   仅期货账户、综合证券账户适用，其它账户类型会忽略此参数)<br>*   返回的 DataFrame 中，除了明确指明了货币的字段，其它资金相关字段都以此参数换算 |
    | asset\_category | [AssetCategory](./trade_trade.md#4752) | 资产类别<br>(ℹ️ 仅对日本券商生效) |
    

*   **返回**
    
    | 参数  | 类型  | 说明  |
    | --- | --- | --- |
    | ret | [RET\_CODE](./ftapi_common.md#7467) | 接口调用结果 |
    | data | pd.DataFrame | 当 ret == RET\_OK 时，返回资金数据 |
    | str | 当 ret != RET\_OK 时，返回错误描述 |
    
    *   资金数据格式如下：
        
        | 字段  | 类型  | 说明  |
        | --- | --- | --- |
        | power | float | 最大购买力<br>(ℹ️ *   此字段是按照 50% 的融资初始保证金率计算得到的 **近似值**。但事实上，每个标的的融资初始保证金率并不相同。我们建议您使用 [查询最大可买可卖](./trade_get-max-trd-qtys.md))<br>     接口返回的 **最大可买** 字段，来判断实际可买入的最大数量。 |
        | max\_power\_short | float | 卖空购买力<br>(ℹ️ *   此字段是按照 60% 的融券保证金率计算得到的 **近似值**。但事实上，每个标的的融券保证金率并不相同。我们建议您使用 [查询最大可买可卖](./trade_get-max-trd-qtys.md))<br>     接口返回的 **可卖空** 字段，来判断实际可卖空的最大数量。 |
        | net\_cash\_power | float | 现金购买力<br>(ℹ️ 已废弃，请使用usd\_net\_cash\_power等字段获取分币种的现金购买力) |
        | total\_assets | float | 总资产净值<br>(ℹ️ 总资产净值 = 证券资产净值 + 基金资产净值 + 债券资产净值) |
        | securities\_assets | float | 证券资产净值<br>(ℹ️ 最低 OpenD 版本要求：8.2.4218) |
        | fund\_assets | float | 基金资产净值<br>(ℹ️ *   综合账户返回结果为总基金资产净值，暂时不支持查询港元基金资产和美元基金资产)<br>*   最低 OpenD 版本要求：8.2.4218 |
        | bond\_assets | float | 债券资产净值<br>(ℹ️ 最低 OpenD 版本要求：8.2.4218) |
        | cash | float | 现金<br>(ℹ️ 已废弃，请使用us\_cash等字段获取分币种的现金) |
        | market\_val | float | 证券市值<br>(ℹ️ 仅证券账户适用) |
        | long\_mv | float | 多头市值 |
        | short\_mv | float | 空头市值 |
        | pending\_asset | float | 在途资产 |
        | interest\_charged\_amount | float | 计息金额 |
        | frozen\_cash | float | 冻结资金 |
        | avl\_withdrawal\_cash | float | 现金可提<br>(ℹ️ 仅证券账户适用) |
        | max\_withdrawal | float | 最大可提<br>(ℹ️ 仅富途证券（香港）的证券账户适用) |
        | currency | [Currency](./trade_trade.md#8019) | 计价货币<br>(ℹ️ 仅综合证券账户、期货账户适用) |
        | available\_funds | float | 可用资金<br>(ℹ️ 仅期货账户适用) |
        | unrealized\_pl | float | 未实现盈亏<br>(ℹ️ 仅期货账户适用) |
        | realized\_pl | float | 已实现盈亏<br>(ℹ️ 仅期货账户适用) |
        | risk\_level | [CltRiskLevel](./trade_trade.md#9239) | 风控状态<br>(ℹ️ 仅期货账户适用。建议统一使用 risk\_status 字段获取证券、期货账户的风险状态) |
        | risk\_status | [CltRiskStatus](./trade_trade.md#3989) | 风险状态<br>(ℹ️ *   证券账户和期货账户均适用)<br>*   共分 9 个等级， `LEVEL1`是最安全，`LEVEL9`是最危险 |
        | initial\_margin | float | 初始保证金 |
        | margin\_call\_margin | float | Margin Call 保证金 |
        | maintenance\_margin | float | 维持保证金 |
        | hk\_cash | float | 港元现金<br>(ℹ️ 此字段表示该币种实际的值，而不是以该币种计价的值) |
        | hk\_avl\_withdrawal\_cash | float | 港元可提<br>(ℹ️ 此字段表示该币种实际的值，而不是以该币种计价的值) |
        | hkd\_net\_cash\_power | float | 港元现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：8.7 |
        | hkd\_assets | float | 港股资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：9.0.5008 |
        | us\_cash | float | 美元现金<br>(ℹ️ 此字段表示该币种实际的值，而不是以该币种计价的值) |
        | us\_avl\_withdrawal\_cash | float | 美元可提<br>(ℹ️ 此字段表示该币种实际的值，而不是以该币种计价的值) |
        | usd\_net\_cash\_power | float | 美元现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：8.7 |
        | usd\_assets | float | 美股资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：9.0.5008 |
        | cn\_cash | float | 人民币现金<br>(ℹ️ 此字段表示该币种实际的值，而不是以该币种计价的值) |
        | cn\_avl\_withdrawal\_cash | float | 人民币可提<br>(ℹ️ 此字段表示该币种实际的值，而不是以该币种计价的值) |
        | cnh\_net\_cash\_power | float | 人民币现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：8.7 |
        | cnh\_assets | float | A股资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：9.0.5008 |
        | jp\_cash | float | 日元现金<br>(ℹ️ *   仅期货账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低 Futu API 版本要求：5.8.2008 |
        | jp\_avl\_withdrawal\_cash | float | 日元可提<br>(ℹ️ *   仅期货账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低 Futu API 版本要求：5.8.2008 |
        | jpy\_net\_cash\_power | float | 日元现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：8.7 |
        | jpy\_assets | float | 日股资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：9.0.5008 |
        | sg\_cash | float | 新元现金<br>(ℹ️ *   仅期货账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值 |
        | sg\_avl\_withdrawal\_cash | float | 新元可提<br>(ℹ️ *   仅期货账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值 |
        | sgd\_net\_cash\_power | float | 新元现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：8.7 |
        | sgd\_assets | float | 新股资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：9.0.5008 |
        | au\_cash | float | 澳元现金<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低 Futu API 版本要求：5.8.2008 |
        | au\_avl\_withdrawal\_cash | float | 澳元可提<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低 Futu API 版本要求：5.8.2008 |
        | aud\_net\_cash\_power | float | 澳元现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：8.7 |
        | aud\_assets | float | 澳股资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：9.0.5008 |
        | ca\_cash | float | 加元现金<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：10.0.6008 |
        | ca\_avl\_withdrawal\_cash | float | 加元可提<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：10.0.6008 |
        | cad\_net\_cash\_power | float | 加元现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：10.0.6008 |
        | cad\_assets | float | 加元资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：10.0.6008 |
        | my\_cash | float | 令吉现金<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：10.0.6008 |
        | my\_avl\_withdrawal\_cash | float | 令吉可提<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：10.0.6008 |
        | myr\_net\_cash\_power | float | 令吉现金购买力<br>(ℹ️ *   此字段表示该币种实际的值，而不是以该币种计价的值)<br>*   最低版本要求：10.0.6008 |
        | myr\_assets | float | 令吉资产净值<br>(ℹ️ *   仅综合证券账户适用)<br>*   此字段表示该币种实际的值，而不是以该币种计价的值<br>*   最低版本要求：10.0.6008 |
        | is\_pdt | bool | 是否为 PDT 账户<br>(ℹ️ True：是 PDT 账户，False：不是 PDT 账户)  <br>仅moomoo证券(美国)账户适用  <br>最低 OpenD 版本要求：5.8.2008 |
        | pdt\_seq | string | 剩余日内交易次数<br>(ℹ️ 仅moomoo证券(美国)账户适用)  <br>最低 OpenD 版本要求：5.8.2008 |
        | beginning\_dtbp | float | 初始日内交易购买力<br>(ℹ️ 仅被标记为 PDT 的moomoo证券(美国)账户适用)  <br>最低 OpenD 版本要求：5.8.2008 |
        | remaining\_dtbp | float | 剩余日内交易购买力<br>(ℹ️ 仅被标记为 PDT 的moomoo证券(美国)账户适用)  <br>最低 OpenD 版本要求：5.8.2008 |
        | dt\_call\_amount | float | 日内交易待缴金额<br>(ℹ️ 仅被标记为 PDT 的moomoo证券(美国)账户适用)  <br>最低 OpenD 版本要求：5.8.2008 |
        | dt\_status | [DtStatus](./trade_trade.md#1860) | 日内交易限制情况<br>(ℹ️ 仅被标记为 PDT 的moomoo证券(美国)账户适用)  <br>最低 OpenD 版本要求：5.8.2008 |
        
*   **Example**
    

    from moomoo import *
    trd_ctx = OpenSecTradeContext(filter_trdmarket=TrdMarket.US, host='127.0.0.1', port=11111, security_firm=SecurityFirm.FUTUINC)
    ret, data = trd_ctx.accinfo_query()
    if ret == RET_OK:
        print(data)
        print(data['power'][0])  # 取第一行的购买力
        print(data['power'].values.tolist())  # 转为 list
    else:
        print('accinfo_query error: ', data)
    trd_ctx.close()  # 关闭当条连接
    

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

*   **Output**

    power  max_power_short  net_cash_power  total_assets  securities_assets  fund_assets  bond_assets   cash   market_val      long_mv   short_mv  pending_asset  interest_charged_amount  frozen_cash  avl_withdrawal_cash  max_withdrawal currency available_funds unrealized_pl realized_pl risk_level risk_status  initial_margin  margin_call_margin  maintenance_margin  hk_cash  hk_avl_withdrawal_cash  hkd_net_cash_power  hkd_assets  us_cash  us_avl_withdrawal_cash  usd_net_cash_power  usd_assets  cn_cash  cn_avl_withdrawal_cash  cnh_net_cash_power  cnh_assets  jp_cash  jp_avl_withdrawal_cash  jpy_net_cash_power jpy_assets  sg_cash sg_avl_withdrawal_cash sgd_net_cash_power sgd_assets  au_cash au_avl_withdrawal_cash aud_net_cash_power aud_assets  ca_cash ca_avl_withdrawal_cash cad_net_cash_power cad_assets  my_cash my_avl_withdrawal_cash myr_net_cash_power myr_assets  is_pdt pdt_seq beginning_dtbp remaining_dtbp dt_call_amount dt_status
    0  465453.903307    465453.903307             0.0   289932.0404        197028.2204     92903.82          0.0  25.18  197003.0448  211960.7568 -14957.712            0.0                      0.0    25.930845                  0.0             0.0      HKD             N/A           N/A         N/A        N/A      LEVEL3   219346.648525       288656.787955       181250.967601      0.0                     0.0          13225.7955     0.0   3.24                     0.0           9656.4365      0.0    0.0                     0.0                 0.0    0.0      0.0                     0.0                 0.0     0.0    N/A                    N/A                N/A     0.0    N/A                    N/A                N/A    0.0    N/A                    N/A                N/A    0.0    N/A                    N/A                N/A    0.0        N/A     N/A            N/A            N/A            N/A       N/A
    465453.903307
    [465453.903307]
    

1  
2  
3  
4  

[#](./trade_get-funds.md#5465-2)
 Trd\_GetFunds.proto
---------------------------------------------------------------------------------------------

*   **介绍**
    
    查询交易业务账户的资产净值、证券市值、现金、购买力等资金数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional bool refreshCache = 2; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 currency = 3;	//货币种类，参见 Trd_Common.Currency。期货和综合证券账户必填，其它账户忽略 
    	optional int32 assetCategory = 4; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   货币类型结构参见 [Currency](./trade_trade.md#8019)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](./trade_trade.md#4752)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.Funds funds = 2; //账户资金
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   账户资金结构参见 [Funds](./trade_trade.md#3175)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **协议 ID**
    
    2101
    

`uint GetFunds(TrdGetFunds.Request req);`  
`virtual void OnReply_GetFunds(MMAPI_Conn client, uint nSerialNo, ${proto_name.Response rsp);`

*   **介绍**
    
    查询交易业务账户的资产净值、证券市值、现金、购买力等资金数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional bool refreshCache = 2; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 currency = 3;	//货币种类，参见 Trd_Common.Currency。期货和综合证券账户必填，其它账户忽略 
    	optional int32 assetCategory = 4; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   货币类型结构参见 [Currency](./trade_trade.md#8019)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](./trade_trade.md#4752)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.Funds funds = 2; //账户资金
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   账户资金结构参见 [Funds](./trade_trade.md#3175)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.CreateBuilder()
                    .SetAccID(281756455988247915L)
                    .SetTrdEnv((int)TrdCommon.TrdEnv.TrdEnv_Real)
                    .SetTrdMarket((int)TrdCommon.TrdMarket.TrdMarket_HK)
                    .Build();
            TrdGetFunds.C2S c2s = TrdGetFunds.C2S.CreateBuilder()
                    .SetCurrency((int)TrdCommon.Currency.Currency_HKD)
                    .SetHeader(header)
                    .Build();
            TrdGetFunds.Request req = TrdGetFunds.Request.CreateBuilder().SetC2S(c2s).Build();
            uint seqNo = trd.GetFunds(req);
            Console.Write("Send TrdGetFunds: {0}\n", seqNo);
        }
    
        
        public void OnDisconnect(MMAPI_Conn client, long errCode) {
            Console.Write("Trd onDisConnect: {0}\n", errCode);
        }
    
        public void OnReply_GetFunds(MMAPI_Conn client, uint nSerialNo, TrdGetFunds.Response rsp)
        {
            Console.Write("Reply: TrdGetFunds: {0}\n", nSerialNo);
            Console.Write("OnReply_GetFunds: {0}\n", rsp.S2C.ToJson());
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
52  
53  
54  
55  
56  

*   **Output**

    Trd onInitConnect: ret = 0 desc = connID = 7257654332030703443
    Send TrdGetFunds: 4
    Reply: TrdGetFunds: 4
    OnReply_GetFunds: {
    	"header": {
    		"trdEnv": 1,
    		"accID": 283726802395277157,
    		"trdMarket": 6
    	},
    	"funds": {
    		"power": 3030.61116601,
    		"totalAssets": 152909.6564,
    		"cash": -3586.52,
    		"marketVal": 156496.1717,
    		"frozenCash": 665.78224097,
    		"debtCash": 22533.9124353,
    		"avlWithdrawalCash": 3030.61,
    		"currency": 1,
    		"initialMargin": 113686.06186064694,
    		"maintenanceMargin": 107790.01800335373,
    		"cashInfoList": [{\
    			"currency": 0,\
    			"cash": 0,\
    			"availableBalance": 0,\
    			"netCashPower": 0\
    		}, {\
    			"currency": 1,\
    			"cash": 9190.04,\
    			"availableBalance": 3030.61,\
    			"netCashPower": 3030.61116601\
    		}, {\
    			"currency": 3,\
    			"cash": -836.36,\
    			"availableBalance": 0,\
    			"netCashPower": 0\
    		}, {\
    			"currency": 2,\
    			"cash": 1245.03,\
    			"availableBalance": 0,\
    			"netCashPower": 17.97126495\
    		}, {\
    			"currency": 5,\
    			"cash": -3070.32,\
    			"availableBalance": 0,\
    			"netCashPower": 0\
    		}, {\
    			"currency": 4,\
    			"cash": -68314,\
    			"availableBalance": 0,\
    			"netCashPower": 0\
    		}],
    		"maxPowerShort": 3030.61116601,
    		"netCashPower": 0,
    		"longMv": 111306.2256,
    		"shortMv": -8875.8239,
    		"pendingAsset": 0,
    		"riskStatus": 3,
    		"marginCallMargin": 111801.53345828,
    		"securitiesAssets": 98843.8864,
    		"fundAssets": 54065.77,
    		"bondAssets": 0
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

`int getFunds(TrdGetFunds.Request req);`  
`void onReply_GetFunds(MMAPI_Conn client, int nSerialNo, ${proto_name.Response rsp);`

*   **介绍**
    
    查询交易业务账户的资产净值、证券市值、现金、购买力等资金数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional bool refreshCache = 2; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 currency = 3;	//货币种类，参见 Trd_Common.Currency。期货和综合证券账户必填，其它账户忽略 
    	optional int32 assetCategory = 4; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   货币类型结构参见 [Currency](./trade_trade.md#8019)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](./trade_trade.md#4752)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.Funds funds = 2; //账户资金
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   账户资金结构参见 [Funds](./trade_trade.md#3175)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    
            TrdCommon.TrdHeader header = TrdCommon.TrdHeader.newBuilder()
                    .setAccID(281756455988247915L)
                    .setTrdEnv(TrdCommon.TrdEnv.TrdEnv_Real_VALUE)
                    .setTrdMarket(TrdCommon.TrdMarket.TrdMarket_HK_VALUE)
                    .build();
            TrdGetFunds.C2S c2s = TrdGetFunds.C2S.newBuilder()
                    .setHeader(header)
                    .setCurrency(TrdCommon.Currency.Currency_HKD_VALUE)
                    .build();
            TrdGetFunds.Request req = TrdGetFunds.Request.newBuilder().setC2S(c2s).build();
            int seqNo = trd.getFunds(req);
            System.out.printf("Send TrdGetFunds: %d\n", seqNo);
        }
    
        @Override
        public void onDisconnect(MMAPI_Conn client, long errCode) {
            System.out.printf("Trd onDisConnect: %d\n", errCode);
        }
    
        @Override
        public void onReply_GetFunds(MMAPI_Conn client, int nSerialNo, TrdGetFunds.Response rsp) {
            if (rsp.getRetType() != 0) {
                System.out.printf("TrdGetFunds failed: %s\n", rsp.getRetMsg());
            }
            else {
                try {
                    String json = JsonFormat.printer().print(rsp);
                    System.out.printf("Receive TrdGetFunds: %s\n", json);
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
65  
66  
67  
68  

*   **Output**

    Send TrdGetFunds: 2
    Receive TrdGetFunds: {
        "retType": 0,
        "retMsg": "",
        "errCode": 0,
        "s2c": {
        "header": {
          "trdEnv": 1,
          "accID": "281756455988247915",
          "trdMarket": 1
        },
        "funds": {
            "power": 0.61760332
            "totalAssets": 15222.3336
            "cash": -19.83
            "marketVal": 15242.1666
            "frozenCash": 158.34982707
            "debtCash": 2644.58147475
            "avlWithdrawalCash": 1.24
            "currency": 1
            "initialMargin": 14904.738361057309
            "maintenanceMargin": 14805.9362278707
            "cashInfoList" {
            "currency": 2
            "cash": -95.36
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 0
            "cash": 0.0
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 1
            "cash": -1097.43
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 4
            "cash": -10267.0
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 3
            "cash": 2049.58
            "availableBalance": 0.6
            "netCashPower": 0.60345798
            }
            "cashInfoList": {
            "currency": 5
            "cash": 18.62
            "availableBalance": 0.1
            "netCashPower": 0.10953234
            }
            "maxPowerShort": 0.61760332
            "netCashPower": 0.0
            "longMv": 1436.1127
            "shortMv": -168.096
            "pendingAsset": 0.0
            "riskStatus": 3
            "marginCallMargin": 14835.22139711
            "securitiesAssets": 1248.1836
            "fundAssets": 13974.15
            "bondAssets": 0.0
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
69  
70  
71  

`moomoo::u32_t GetFunds(const Trd_GetFunds::Request &stReq);`  
`virtual void OnReply_GetFunds(moomoo::u32_t nSerialNo, const Trd_GetFunds::Response &stRsp) = 0;`

*   **介绍**
    
    查询交易业务账户的资产净值、证券市值、现金、购买力等资金数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional bool refreshCache = 2; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 currency = 3;	//货币种类，参见 Trd_Common.Currency。期货和综合证券账户必填，其它账户忽略 
    	optional int32 assetCategory = 4; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   货币类型结构参见 [Currency](./trade_trade.md#8019)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](./trade_trade.md#4752)
>     

*   **回调**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.Funds funds = 2; //账户资金
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   账户资金结构参见 [Funds](./trade_trade.md#3175)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
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
    		Trd_GetFunds::Request req;
    		Trd_GetFunds::C2S *c2s = req.mutable_c2s();
    		Trd_Common::TrdHeader *header = c2s->mutable_header();
    		header->set_accid(3637840);
    		header->set_trdenv(0);
    		header->set_trdmarket(1);
    
            m_GetFundsSerialNo = m_pTrdApi->GetFunds(req);
            cout << "Request GetFunds SerialNo: " << m_GetFundsSerialNo << endl;
    	}
    
    	virtual void OnReply_GetFunds(moomoo::u32_t nSerialNo, const Trd_GetFunds::Response &stRsp){
            if(nSerialNo == m_GetFundsSerialNo)
            {
                cout << "OnReply_GetFunds SerialNo: " << nSerialNo << endl;
                // 解析内部结构打印出来
                // ProtoBufToBodyData和UTF8ToLocal函数的定义参见Sample中的tool.h文件
                string resp_str;
                ProtoBufToBodyData(stRsp, resp_str);
                cout << UTF8ToLocal(resp_str) << endl;
            }
    	}
    
    protected:
    	MMAPI_Trd *m_pTrdApi;
    
        moomoo::u32_t m_GetFundsSerialNo;
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
    Request GetFunds SerialNo: 4
    OnReply_GetFunds SerialNo: 4
    {
        "retType": 0,
        "retMsg": "",
        "errCode": 0,
        "s2c": {
            "header": {
            "trdEnv": 1,
            "accID": "281756455988247915",
            "trdMarket": 1
            },
            "funds": {
                "power": 0.61760332
                "totalAssets": 15222.3336
                "cash": -19.83
                "marketVal": 15242.1666
                "frozenCash": 158.34982707
                "debtCash": 2644.58147475
                "avlWithdrawalCash": 1.24
                "currency": 1
                "initialMargin": 14904.738361057309
                "maintenanceMargin": 14805.9362278707
                "cashInfoList" {
                "currency": 2
                "cash": -95.36
                "availableBalance": 0.0
                "netCashPower": 0.0
                }
                "cashInfoList": {
                "currency": 0
                "cash": 0.0
                "availableBalance": 0.0
                "netCashPower": 0.0
                }
                "cashInfoList": {
                "currency": 1
                "cash": -1097.43
                "availableBalance": 0.0
                "netCashPower": 0.0
                }
                "cashInfoList": {
                "currency": 4
                "cash": -10267.0
                "availableBalance": 0.0
                "netCashPower": 0.0
                }
                "cashInfoList": {
                "currency": 3
                "cash": 2049.58
                "availableBalance": 0.6
                "netCashPower": 0.60345798
                }
                "cashInfoList": {
                "currency": 5
                "cash": 18.62
                "availableBalance": 0.1
                "netCashPower": 0.10953234
                }
                "maxPowerShort": 0.61760332
                "netCashPower": 0.0
                "longMv": 1436.1127
                "shortMv": -168.096
                "pendingAsset": 0.0
                "riskStatus": 3
                "marginCallMargin": 14835.22139711
                "securitiesAssets": 1248.1836
                "fundAssets": 13974.15
                "bondAssets": 0.0
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
69  
70  
71  
72  
73  
74  

`GetFunds(req);`

*   **介绍**
    
    查询交易业务账户的资产净值、证券市值、现金、购买力等资金数据。
    
*   **参数**
    

    message C2S
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional bool refreshCache = 2; //立即刷新 OpenD 缓存的此数据，默认不填。true 向服务器获取最新数据更新缓存并返回；flase 或没填则返回 OpenD 缓存的数据，不会向服务器请求。
    	//正常情况下，服务器有更新就会立即推送到 OpenD，OpenD 缓存着数据，API 请求过来，返回同步的缓存数据，一般不需要指定刷新缓存，保证快速返回且减少对服务器的压力
    	//如果遇到丢包等情况，可能出现缓存数据与服务器不一致，用户如果发现数据更新有异样，可指定刷新缓存，解决数据同步的问题。
    	optional int32 currency = 3;	//货币种类，参见 Trd_Common.Currency。期货和综合证券账户必填，其它账户忽略 
    	optional int32 assetCategory = 4; //账户资产类型，JP衍生品账户必填，参考 Trd_Common.TrdAssetCategory
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

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   货币类型结构参见 [Currency](./trade_trade.md#8019)
>     
> *   账户资产类型结构参见 [TrdAssetCategory](./trade_trade.md#4752)
>     

*   **返回**

    message S2C
    {
    	required Trd_Common.TrdHeader header = 1; //交易公共参数头
    	optional Trd_Common.Funds funds = 2; //账户资金
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
15  

> *   交易公共参数头结构参见 [TrdHeader](./trade_trade.md#1138)
>     
> *   账户资金结构参见 [Funds](./trade_trade.md#3175)
>     
> *   接口调用结果，结构参见 [RetType](./ftapi_common.md#7467)
>     

*   **Example**

    import mmWebsocket from "moomoo-api";
    import { mmCmdID } from "moomoo-api";
    import { Common, Qot_Common, Trd_Common } from "moomoo-api/proto";
    import beautify from "js-beautify";
    
    TrdGetFunds(){
        const { RetType } = Common
        const { TrdEnv, TrdMarket } = Trd_Common
        let [addr, port, enable_ssl, key] = ["127.0.0.1", 33333, false, '7522027ccf5a06b1'];
        let websocket = new mmWebsocket();
    
        websocket.onlogin = (ret, msg)=>{
            if (ret) { // 登录成功
                websocket.GetAccList({
                    c2s: {
                        userID: 0,
                    },
                }).then((res) => {
                    let { retType, s2c: { accList }  } = res
                    if(retType == RetType.RetType_Succeed){
                        let acc = accList.filter((item)=>{ 
                            return item.trdEnv == TrdEnv.TrdEnv_Simulate && item.trdMarketAuthList.some((auth)=>{ return auth == TrdMarket.TrdMarket_HK})
                        })[0]; // 样例取第一个香港市场虚拟环境账户
    
                        const req = {
                            c2s: {
                                header: {
                                    trdEnv: acc.trdEnv,
                                    accID: acc.accID,
                                    trdMarket: TrdMarket.TrdMarket_HK,
                                }
                            },
                        };
    
                        websocket.GetFunds(req)
                        .then((res) => {
                            let { errCode, retMsg, retType,s2c } = res
                            console.log("GetFunds: errCode %d, retMsg %s, retType %d", errCode, retMsg, retType); 
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
    
                    }
                })
                .catch((error) => {
                    console.log("GetAccList error:", error);
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

    GetFunds: errCode 0, retMsg , retType 0
    {
        "header": {
          "trdEnv": 1,
          "accID": "281756455988247915",
          "trdMarket": 1
        },
        "funds": {
            "power": 0.61760332
            "totalAssets": 15222.3336
            "cash": -19.83
            "marketVal": 15242.1666
            "frozenCash": 158.34982707
            "debtCash": 2644.58147475
            "avlWithdrawalCash": 1.24
            "currency": 1
            "initialMargin": 14904.738361057309
            "maintenanceMargin": 14805.9362278707
            "cashInfoList" {
            "currency": 2
            "cash": -95.36
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 0
            "cash": 0.0
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 1
            "cash": -1097.43
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 4
            "cash": -10267.0
            "availableBalance": 0.0
            "netCashPower": 0.0
            }
            "cashInfoList": {
            "currency": 3
            "cash": 2049.58
            "availableBalance": 0.6
            "netCashPower": 0.60345798
            }
            "cashInfoList": {
            "currency": 5
            "cash": 18.62
            "availableBalance": 0.1
            "netCashPower": 0.10953234
            }
            "maxPowerShort": 0.61760332
            "netCashPower": 0.0
            "longMv": 1436.1127
            "shortMv": -168.096
            "pendingAsset": 0.0
            "riskStatus": 3
            "marginCallMargin": 14835.22139711
            "securitiesAssets": 1248.1836
            "fundAssets": 13974.15
            "bondAssets": 0.0
        }
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

接口限制

*   同一账户ID(acc\_id) 每 30 秒内最多请求 10 次查询账户资金接口
*   调用此接口，只有在刷新缓存时，才受到限频限制

← [解锁交易](./trade_unlock.md) [查询最大可买可卖](./trade_get-max-trd-qtys.md)
 →

[查询账户资金](./trade_get-funds.md)