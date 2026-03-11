import React, { createContext, useContext, useState } from 'react';

const STORAGE_KEY = 'trade_editable';

/**
 * 交易记录可编辑性上下文
 * 用于全局控制交易记录是否允许编辑操作（新增、修改、删除）
 */
const TradeEditableContext = createContext({
  tradeEditable: false,
  setTradeEditable: () => {},
});

/**
 * 交易记录可编辑性 Provider
 * 开关状态通过 localStorage 持久化
 */
export const TradeEditableProvider = ({ children }) => {
  const [tradeEditable, setTradeEditableState] = useState(() => {
    try {
      return localStorage.getItem(STORAGE_KEY) === 'true';
    } catch {
      return false;
    }
  });

  const setTradeEditable = (value) => {
    setTradeEditableState(value);
    try {
      localStorage.setItem(STORAGE_KEY, String(value));
    } catch {
      // 忽略 localStorage 写入失败
    }
  };

  return (
    <TradeEditableContext.Provider value={{ tradeEditable, setTradeEditable }}>
      {children}
    </TradeEditableContext.Provider>
  );
};

/**
 * 自定义 Hook：获取交易记录可编辑状态
 */
export const useTradeEditable = () => {
  return useContext(TradeEditableContext);
};

export default TradeEditableContext;
