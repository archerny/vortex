ProtoBuf Reference
==================

Python

*   [Introduction](./protobuf-reference.md#intro)
    
*   [Important Protobuf Functions](./protobuf-reference.md#functions)
    *   [CopyFrom](./protobuf-reference.md#copy-from)
        
*   [ApiConfig](./protobuf-reference.md#api-config)
    
*   [ApiSettingsConfig](./protobuf-reference.md#api-settings-config)
    
*   [ConfigRequest](./protobuf-reference.md#config-request)
    
*   [ConfigResponse](./protobuf-reference.md#config-response)
    
*   [LockAndExit](./protobuf-reference.md#lock-and-exit)
    
*   [MessageConfig](./protobuf-reference.md#message-config)
    
*   [OrdersConfig](./protobuf-reference.md#orders-config)
    
*   [OrdersSmartRoutingConfig](./protobuf-reference.md#order-smart-routing-config)
    
*   [UpdateConfigRequest](./protobuf-reference.md#update-config-request)
    
*   [UpdateConfigResponse](./protobuf-reference.md#update-config-response)
    
*   [UpdateConfigWarning](./protobuf-reference.md#update-config-warning)
    

Introduction

--------------------------------------

Beginning with TWSAPI version 10.35.01, Interactive Brokers has begun to produce the TWS API using Google’s Protocol Buffer. As a result, many new objects are produced using protocol buffer classes directly that developers will need to utilize and understand in order to produce effective code that can access all systems provided by the TWSAPI.

Users unfamiliar with Protocol Buffers should visit the official development page for more insight on what it is, what it may offer, and context for the built-in systems it provides.

[Protocol Buffer Documentatino](https://protobuf.dev/)

Important Protobuf Functions

------------------------------------------------------

When interacting directly with protobuf classes, proto-specific functions need to be used.

### CopyFrom

#### CopyFrom

A standard method of the Message class of protobuf. Is used to copy items in a field from one object to another.  
See [See Details](https://protobuf.dev/getting-started/pythontutorial/#:~:text=the%20value%202.-,Standard%20Message%20Methods,-Each%20message%20class)

*   Python


```
**settings:** `ApiSettingsConfig` – Container object for API settings.
```


```
**trustedIPs**: `List<String>` – Contains a collection of IP addresses that are authorized to establish API connections without additional authentication.
```


```
reqId: `Integer` – Request identifier
```


```
**orders**: `OrdersConfig`– Container object for the Orders settings.
```


```
**autoLogoffType:** `String` – Declare if platform should logout or restart and continue running.
```


```
**enabled**: `Boolean` – Controls whether the specified message type is active and will be transmitted to the API client.
```


```
**smartRouting**: `OrdersSmartRoutingConfig` – Container object declaring how Smart Routed orders should behave.
```


```
**doNotRouteToDarkPools**: `Boolean` – Prevents the smart routing system from directing orders to dark pool venues for execution.
```


```
**resetApiOrderSequence**: `Boolean` – Determine if the Order ID sequence should be reset to 0.
```


```
Success:
```


```
Failed:
```


```
**message**: `String` – The content of the warning message.
```


```
updateConfigWarning = UpdateConfigWarning()
updateConfigWarning.messageId = 131
messageConfigProto.enabled = False
messageConfigProto.enabled = False
```
