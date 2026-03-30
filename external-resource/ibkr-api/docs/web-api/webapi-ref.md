Web API Reference
=================

> 数据来源: [IB REST API OpenAPI Spec](https://api.ibkr.com/gw/api/v3/api-docs) | 版本: 2.28.0 | 规范: OpenAPI 3.0.0
>
> 联系: api@interactivebrokers.com

## 服务器环境

- **Production**: `https://api.ibkr.com`
- **Sandbox**: `https://qa.interactivebrokers.com`

## 本地 OpenAPI 规范

完整的 OpenAPI 3.0 规范已保存在 [openapi-spec.json](./openapi-spec.json)。
该文件包含全部 API 端点定义、请求/响应 Schema、认证方式等信息，可导入 Swagger UI、Postman 等工具使用。

- **API 端点数**: 152
- **数据模型数**: 426

## API 端点概览

### Other

#### Account Management Accounts

| Method | Path | Summary |
|--------|------|---------|
| `GET` | `/gw/api/v1/accounts` | Retrieve Processed Application |
| `POST` | `/gw/api/v1/accounts` | Create Account |
| `PATCH` | `/gw/api/v1/accounts` | Update Account |
| `POST` | `/gw/api/v1/accounts/documents` | Submit General Agreements And Disclosures |
| `GET` | `/gw/api/v1/accounts/login-messages` | Get Login Messages |
| `GET` | `/gw/api/v1/accounts/status` | Get Status Of Accounts |
| `GET` | `/gw/api/v1/accounts/{accountId}/details` | Get Account Information |
| `GET` | `/gw/api/v1/accounts/{accountId}/kyc` | Retrieve Au10Tix URL |
| `GET` | `/gw/api/v1/accounts/{accountId}/login-messages` | Get Login Message By Account |
| `GET` | `/gw/api/v1/accounts/{accountId}/status` | Get Status By Account |
| `GET` | `/gw/api/v1/accounts/{accountId}/tasks` | Get Registration Tasks |

#### Account Management Banking

| Method | Path | Summary |
|--------|------|---------|
| `POST` | `/gw/api/v1/bank-instructions` | Manage Bank Instructions |
| `POST` | `/gw/api/v1/bank-instructions/query` | View Bank Instructions |
| `POST` | `/gw/api/v1/bank-instructions:bulk` | Creates Multiple Banking Instructions(ach, Delete, Micro-amount, Predefined-destination-instruction) |
| `GET` | `/gw/api/v1/client-instructions/{clientInstructionId}` | Get Status For ClientInstructionId |
| `POST` | `/gw/api/v1/external-asset-transfers` | Transfer Positions Externally (ACATS, ATON, FOP, DWAC, Complex Asset Transfer) |
| `POST` | `/gw/api/v1/external-asset-transfers:bulk` | Creates Multiple External Asset Transfers (Fop, DWAC And Complex Asset Transfer) |
| `POST` | `/gw/api/v1/external-cash-transfers` | Transfer Cash Externally |
| `POST` | `/gw/api/v1/external-cash-transfers/query` | View Cash Balances |
| `POST` | `/gw/api/v1/external-cash-transfers:bulk` | Creates Multiple External Cash Transfers (Deposit And Withdraw Fund) |
| `GET` | `/gw/api/v1/instruction-sets/{instructionSetId}` | Get Status For InstructionSetId |
| `POST` | `/gw/api/v1/instructions/cancel` | Cancel Request |
| `POST` | `/gw/api/v1/instructions/cancel:bulk` | Creates Multiple Cancel Instructions |
| `POST` | `/gw/api/v1/instructions/query` | Get Transaction History |
| `GET` | `/gw/api/v1/instructions/{instructionId}` | Get Status For InstructionId |
| `POST` | `/gw/api/v1/internal-asset-transfers` | Transfer Positions Internally |
| `POST` | `/gw/api/v1/internal-asset-transfers:bulk` | Creates Multiple Internal Asset Transfers Between The Provided Account Id Pairs |
| `POST` | `/gw/api/v1/internal-cash-transfers` | Transfer Cash Internally |
| `POST` | `/gw/api/v1/internal-cash-transfers:bulk` | Creates Multiple Internal Cash Transfers Between The Provided Account Id Pairs |

#### Account Management Reports

| Method | Path | Summary |
|--------|------|---------|
| `POST` | `/gw/api/v1/statements` | Generates Statements In Supported Formats Based On Request Parameters. |
| `GET` | `/gw/api/v1/statements/available` | Fetch Available Daily, Monthly, And Annual Report Dates For An Account Id |
| `POST` | `/gw/api/v1/tax-documents` | Fetch Tax Forms In Supported Formats Based On Request Parameters. |
| `GET` | `/gw/api/v1/tax-documents/available` | Fetch List Of Available Tax Reports/forms/documents For A Specified Account And Tax Year |
| `POST` | `/gw/api/v1/trade-confirmations` | Fetch Trade Confirmations In Supported Formats Based On Request Parameters. |
| `GET` | `/gw/api/v1/trade-confirmations/available` | Fetch List Of Available Trade Confirmation Dates, For A Specific Account Id |

#### Account Management Utilities

| Method | Path | Summary |
|--------|------|---------|
| `POST` | `/gw/api/v1/balances/query` | View Cash Balances |
| `GET` | `/gw/api/v1/enumerations/complex-asset-transfer` | Get A List Of Participating Brokers For The Given Asset Type |
| `GET` | `/gw/api/v1/enumerations/{enumerationType}` | Get Enumerations |
| `GET` | `/gw/api/v1/forms` | Get Forms |
| `GET` | `/gw/api/v1/forms/required-forms` | Get Required Forms |
| `GET` | `/gw/api/v1/participating-banks` | Get Participating Banks |
| `GET` | `/gw/api/v1/requests` | Get Requests' Details By Timeframe |
| `GET` | `/gw/api/v1/requests/{requestId}/status` | Get Status Of A Request |
| `GET` | `/gw/api/v1/validations/usernames/{username}` | Verify User Availability |

#### Authorization SSO Sessions

| Method | Path | Summary |
|--------|------|---------|
| `POST` | `/gw/api/v1/sso-browser-sessions` | Create SSO Browser Session. |
| `POST` | `/gw/api/v1/sso-sessions` | Create A New SSO Session On Behalf Of An End-user. |

#### Authorization Token

| Method | Path | Summary |
|--------|------|---------|
| `POST` | `/oauth2/api/v1/token` | Create Access Token |

#### PreTrade Compliance Restrictions

| Method | Path | Summary |
|--------|------|---------|
| `POST` | `/gw/api/v1/restrictions` | Apply PTC CSV |
| `POST` | `/gw/api/v1/restrictions/verify` | Verify PTC CSV |

#### Trading Accounts

| Method | Path | Summary |
|--------|------|---------|
| `GET` | `/acesws/{accountId}/signatures-and-owners` | List Account Signatures And Owners |
| `POST` | `/iserver/account` | Switch Selected Account |
| `GET` | `/iserver/account/pnl/partitioned` | Account Profit And Loss |
| `GET` | `/iserver/account/search/{searchPattern}` | Search Dynamic Accounts |
| `GET` | `/iserver/account/{accountId}/summary` | Summary Of Account Values |
| `GET` | `/iserver/account/{accountId}/summary/available_funds` | Summary Of Available Funds |
| `GET` | `/iserver/account/{accountId}/summary/balances` | Summary Of Account Balances |
| `GET` | `/iserver/account/{accountId}/summary/margins` | Summary Of Account Margin Usage |
| `GET` | `/iserver/account/{accountId}/summary/market_value` | Summary Of Account Market Value |
| `GET` | `/iserver/accounts` | List All Tradable Accounts |
| `POST` | `/iserver/dynaccount` | Set Active Dynamic Account |

#### Trading Alerts

| Method | Path | Summary |
|--------|------|---------|
| `GET` | `/iserver/account/alert/{alertId}` | Details Of A Specific Alert |
| `GET` | `/iserver/account/mta` | Details Of A Mobile Trading Alert |
| `POST` | `/iserver/account/{accountId}/alert` | Create Or Modify Alert |
| `POST` | `/iserver/account/{accountId}/alert/activate` | Activate Or Deactivate An Alert |
| `DELETE` | `/iserver/account/{accountId}/alert/{alertId}` | Delete An Alert |
| `GET` | `/iserver/account/{accountId}/alerts` | List All Alerts |

#### Trading Contracts

| Method | Path | Summary |
|--------|------|---------|
| `GET` | `/contract/trading-schedule` | Trading Schedule (NEW) |
| `POST` | `/iserver/contract/rules` | Search Contract Rules |
| `GET` | `/iserver/contract/{conid}/algos` | Search Algos For An Instrument |
| `GET` | `/iserver/contract/{conid}/info` | General Instrument Information |
| `GET` | `/iserver/contract/{conid}/info-and-rules` | Instrument Info And Market Rules |
| `GET` | `/iserver/currency/pairs` | Available Currency Pairs |
| `GET` | `/iserver/exchangerate` | Currency Exchange Rate |
| `GET` | `/iserver/secdef/bond-filters` | Search Bond Filter Information |
| `GET` | `/iserver/secdef/info` | Instrument Attributes Detail |
| `GET` | `/iserver/secdef/search` | Search Instruments By Symbol |
| `POST` | `/iserver/secdef/search` | Search Instruments By Symbol |
| `GET` | `/iserver/secdef/strikes` | Search Strikes For An Underlier |
| `GET` | `/trsrv/all-conids` | List All Stock Conids By Exchange |
| `GET` | `/trsrv/futures` | Search Futures By Symbol |
| `GET` | `/trsrv/secdef` | Instrument Definition Detail |
| `GET` | `/trsrv/secdef/schedule` | Trading Schedule By Symbol |
| `GET` | `/trsrv/stocks` | Search Stocks By Symbol |

#### Trading Event Contracts

| Method | Path | Summary |
|--------|------|---------|
| `GET` | `/forecast/category/tree` | Event Contract Categories |
| `GET` | `/forecast/contract/details` | Event Contract Details |
| `GET` | `/forecast/contract/market` | Provides All Contracts For Given Underlying Market. |
| `GET` | `/forecast/contract/rules` | Event Contract Rules |
| `GET` | `/forecast/contract/schedules` | Event Contract Schedules |

#### Trading FA Allocation Management

| Method | Path | Summary |
|--------|------|---------|
| `POST` | `/fa/fa-preset/get` | Get Model Preset |
| `POST` | `/fa/fa-preset/save` | Set Model Preset |
| `POST` | `/fa/model/accounts-details` | Get Models Accounts |
| `POST` | `/fa/model/invest-divest` | Invest Account Into Model |
| `POST` | `/fa/model/invest-divest-positions` | Summary Of Accounts Invested In The Model |
| `POST` | `/fa/model/list` | Request All Models |
| `POST` | `/fa/model/positions` | Request Model Positions |
| `POST` | `/fa/model/save` | Set Model Allocations |
| `POST` | `/fa/model/submit-transfers` | Submit Transfers |
| `POST` | `/fa/model/summary` | Request Model Summary |
| `GET` | `/iserver/account/allocation/accounts` | List Allocatable Subaccounts |
| `GET` | `/iserver/account/allocation/group` | List All Allocation Groups |
| `PUT` | `/iserver/account/allocation/group` | Modify Allocation Group |
| `POST` | `/iserver/account/allocation/group` | Add Allocation Group |
| `POST` | `/iserver/account/allocation/group/delete` | Delete An Allocation Group |
| `POST` | `/iserver/account/allocation/group/single` | Retrieve Single Allocation Group |
| `GET` | `/iserver/account/allocation/presets` | Retrieve Allocation Presets |
| `POST` | `/iserver/account/allocation/presets` | Set Allocation Preset |

#### Trading FYIs and Notifications

| Method | Path | Summary |
|--------|------|---------|
| `GET` | `/fyi/deliveryoptions` | Get Delivery Options |
| `POST` | `/fyi/deliveryoptions/device` | Toggle Delivery To A Device |
| `PUT` | `/fyi/deliveryoptions/email` | Toggle Email Delivery |
| `DELETE` | `/fyi/deliveryoptions/{deviceId}` | Delete A Device |
| `GET` | `/fyi/disclaimer/{typecode}` | Get Disclaimers By FYI Type |
| `PUT` | `/fyi/disclaimer/{typecode}` | Mark FYI Disclaimer Read |
| `GET` | `/fyi/notifications` | List All Notifications |
| `PUT` | `/fyi/notifications/{notificationID}` | Mark Notification Read |
| `GET` | `/fyi/settings` | Get Notification Settings |
| `POST` | `/fyi/settings/{typecode}` | Modify FYI Notifications |
| `GET` | `/fyi/unreadnumber` | Get Number Of Unread Notifications |

#### Trading Market Data

| Method | Path | Summary |
|--------|------|---------|
| `GET` | `/iserver/marketdata/history` | Historical OHLC Bar Data |
| `GET` | `/iserver/marketdata/snapshot` | Live Market Data Snapshot |
| `POST` | `/iserver/marketdata/unsubscribe` | Close A Backend Data Stream |
| `GET` | `/iserver/marketdata/unsubscribeall` | Close All Backend Data Streams |

#### Trading OAuth 1.0a

| Method | Path | Summary |
|--------|------|---------|
| `POST` | `/oauth/access_token` | Generate An Access Token |
| `POST` | `/oauth/live_session_token` | Generate A Live Session Token |
| `POST` | `/oauth/request_token` | Obtain A Request Token |

#### Trading Orders

| Method | Path | Summary |
|--------|------|---------|
| `GET` | `/iserver/account/order/status/{orderId}` | Status Of A Single Order |
| `GET` | `/iserver/account/orders` | List Open Orders |
| `GET` | `/iserver/account/trades` | Trade History |
| `POST` | `/iserver/account/{accountId}/order/{orderId}` | Modify Open Order |
| `DELETE` | `/iserver/account/{accountId}/order/{orderId}` | Cancel An Open Order |
| `POST` | `/iserver/account/{accountId}/orders` | Submit New Order |
| `POST` | `/iserver/account/{accountId}/orders/whatif` | New Order Preview |
| `POST` | `/iserver/notification` | Dismiss Server Prompt |
| `POST` | `/iserver/questions/suppress` | Suppress Order Reply Messages |
| `POST` | `/iserver/questions/suppress/reset` | Reset Order Reply Message Suppression |
| `POST` | `/iserver/reply/{replyId}` | Confirm Order Reply Message |

#### Trading Portfolio

| Method | Path | Summary |
|--------|------|---------|
| `GET` | `/portfolio/accounts` | List All Accounts |
| `GET` | `/portfolio/positions/{conid}` | All Account Positions In An Instrument |
| `GET` | `/portfolio/subaccounts` | List All Subaccounts |
| `GET` | `/portfolio/{accountId}/allocation` | Account Allocations |
| `GET` | `/portfolio/{accountId}/ledger` | Account Ledger |
| `GET` | `/portfolio/{accountId}/meta` | Account Attributes |
| `POST` | `/portfolio/{accountId}/positions/invalidate` | Refresh Position Cache |
| `GET` | `/portfolio/{accountId}/positions/{pageId}` | Account Positions |
| `GET` | `/portfolio/{accountId}/summary` | Account Portfolio Summary |
| `GET` | `/portfolio/{accountid}/position/{conid}` | Account Position In An Instrument |

#### Trading Portfolio Analyst

| Method | Path | Summary |
|--------|------|---------|
| `POST` | `/pa/allperiods` | Account Performance (All Time Periods) |
| `POST` | `/pa/performance` | Account Performance |
| `POST` | `/pa/transactions` | Transaction History |

#### Trading Scanner

| Method | Path | Summary |
|--------|------|---------|
| `GET` | `/iserver/scanner/params` | Get Valid IServer Scanner Parameters |
| `POST` | `/iserver/scanner/run` | Run An IServer Market Scanner |

#### Trading Session

| Method | Path | Summary |
|--------|------|---------|
| `POST` | `/iserver/auth/ssodh/init` | Initialize Brokerage Session |
| `POST` | `/iserver/auth/status` | Brokerage Session Status |
| `POST` | `/logout` | Terminate Web API Session |
| `GET` | `/sso/validate` | Validate SSO Web API Session |
| `POST` | `/tickle` | Brokerage Keep-Alive Ping |

#### Trading Watchlists

| Method | Path | Summary |
|--------|------|---------|
| `GET` | `/iserver/watchlist` | Return A Single Saved Watchlist |
| `POST` | `/iserver/watchlist` | Create A Watchlist |
| `DELETE` | `/iserver/watchlist` | Delete A Saved Watchlist |
| `GET` | `/iserver/watchlists` | Return All Saved Watchlists |

#### Trading Websocket

| Method | Path | Summary |
|--------|------|---------|
| `GET` | `/ws` | Open Websocket |

#### Utilities Echo

| Method | Path | Summary |
|--------|------|---------|
| `GET` | `/gw/api/v1/echo/https` | Echo A Request With HTTPS Security Policy Back After Validation. |
| `POST` | `/gw/api/v1/echo/signed-jwt` | Echo A Request With Signed JWT Security Policy Back After Validation. |
