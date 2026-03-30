# 其他订阅事件

连接成功

[](./push-other.md#%E8%BF%9E%E6%8E%A5%E6%88%90%E5%8A%9F)

-------------------------------------------------------------------------------------------

`PushClient.connect_callback`

**说明**

连接成功的回调

**参数**

frame

**返回**

无

**示例**

Python

`from tigeropen.push.push_client import PushClient from tigeropen.tiger_open_config import get_client_config client_config = get_client_config(private_key_path='private key path', tiger_id='your tiger id', account='your account')  # 初始化PushClient protocol, host, port = client_config.socket_host_port push_client = PushClient(host, port, use_ssl=(protocol == 'ssl'))  def connect_callback(frame):     """连接建立成功的回调"""     print('connected')      push_client.connect_callback = connect_callback`

* * *

断开连接

[](./push-other.md#%E6%96%AD%E5%BC%80%E8%BF%9E%E6%8E%A5)

-------------------------------------------------------------------------------------------

`PushClient.disconnect_callback`

**说明**

连接断开的回调

**参数**

无

**返回**

无

**示例**

Python

`def disconnect_callback():     """断线重连"""     for t in range(1, 200):         try:             print('disconnected, reconnecting...')             push_client.connect(client_config.tiger_id, client_config.private_key)         except:             print('connect failed, retry')             time.sleep(t)         else:             print('reconnect success')             break              # 初始化push_client步骤略，同上 push_client.disconnect_callback = disconnect_callback`   
   

* * *

连接出错

[](./push-other.md#%E8%BF%9E%E6%8E%A5%E5%87%BA%E9%94%99)

-------------------------------------------------------------------------------------------

`PushClient.error_callback`

**说明**

连接出错的回调

**参数**

错误信息接收参数

**返回**

无

**示例**

Python

`   def error_callback(frame): 	"""错误回调 	:param content: 错误信息 	""" 	print(frame)  # 初始化push_client步骤略，同上   push_client.error_callback = error_callback   `

* * *

连接被踢

[](./push-other.md#%E8%BF%9E%E6%8E%A5%E8%A2%AB%E8%B8%A2)

-------------------------------------------------------------------------------------------

`PushClient.kickout_callback(frame)`

**说明**

连接被踢的回调。多台设备连接时，旧设备被新设备踢掉时，会收到此回调。

**参数**

被踢错误信息接收参数

**返回**

无

**示例**

Python

`def kickout_callback(frame):     logger.error(f'kickout callback: {frame}')      push_client.kickout_callback = kickout_callback`

  
