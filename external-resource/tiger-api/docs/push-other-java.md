# 其他订阅事件

连接回调

[](./push-other-java.md#%E8%BF%9E%E6%8E%A5%E5%9B%9E%E8%B0%83)

------------------------------------------------------------------------------------------------

**说明**

长连接建立或断开时的回调

**回调接口**

Java

    void connectionClosed() // 连接已关闭
    void connectAck() // 连接成功
    void connectionKickout(int errorCode, String errorMsg) // 连接被另一个连接踢掉
    void hearBeat(String s) // 连接心跳回调

**示例**

Java

    package com.tigerbrokers.stock.openapi.demo;
    
    import com.alibaba.fastjson.JSONObject;
    import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
    
    
    public class DefaultApiComposeCallback implements ApiComposeCallback {
    
      @Override
      public void connectionClosed() {
        System.out.println("connection closed.");
      }
    
      @Override
      public void connectionKickout(int errorCode, String errorMsg) {
        System.out.println(errorMsg + " and the connection is closed.");
      }
    
      @Override
      public void connectionAck() {
        System.out.println("connect ack.");
      }
    
      @Override
      public void hearBeat(String s) {
    
      }
    
      @Override
      public void serverHeartBeatTimeOut(String s) {
    
      }
    }

* * *

异常回调

[](./push-other-java.md#%E5%BC%82%E5%B8%B8%E5%9B%9E%E8%B0%83)

------------------------------------------------------------------------------------------------

**说明**

订阅异常时的回调

**回调接口**

Java

    void error(String errorMsg)
    void error(int id, int errorCode, String errorMsg)

**示例**

Java

    package com.tigerbrokers.stock.openapi.demo;
    
    import com.alibaba.fastjson.JSONObject;
    import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
    
    
    public class DefaultApiComposeCallback implements ApiComposeCallback {
    
      @Override
      public void error(String errorMsg) {
        System.out.println("receive error:" + errorMsg);
      }
    
      @Override
      public void error(int id, int errorCode, String errorMsg) {
        System.out.println("receive error id:" + id + ",errorCode:" + errorCode + ",errorMsg:" + errorMsg);
      }
    }
