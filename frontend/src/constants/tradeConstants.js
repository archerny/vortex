// 证券类型：后端枚举 <-> 前端中文
export const assetTypeMap = {
  STOCK: '股票',
  ETF: 'ETF',
  OPTION_CALL: 'CALL期权',
  OPTION_PUT: 'PUT期权',
};
export const assetTypeReverseMap = Object.fromEntries(Object.entries(assetTypeMap).map(([k, v]) => [v, k]));

// 证券类型 Tag 颜色
export const assetTypeColorMap = {
  STOCK: 'blue',
  ETF: 'cyan',
  OPTION_CALL: 'green',
  OPTION_PUT: 'volcano',
};

// 交易类型：后端枚举 <-> 前端中文
export const tradeTypeMap = {
  BUY: '买入',
  SELL: '卖出',
  OPTION_EXPIRE: '期权到期',
  EXERCISE_BUY: '行权买股',
  EXERCISE_SELL: '行权卖股',
};
export const tradeTypeReverseMap = Object.fromEntries(Object.entries(tradeTypeMap).map(([k, v]) => [v, k]));

// 交易类型 Tag 颜色
export const tradeTypeColorMap = {
  BUY: 'green',
  SELL: 'red',
  OPTION_EXPIRE: 'default',
  EXERCISE_BUY: 'cyan',
  EXERCISE_SELL: 'orange',
};

// 金额颜色映射（按交易类型）
export const amountColorMap = {
  BUY: '#cf1322',
  SELL: '#3f8600',
  OPTION_EXPIRE: '#999999',
  EXERCISE_BUY: '#cf1322',
  EXERCISE_SELL: '#3f8600',
};

// 币种选项
export const currencyOptions = [
  { label: '🇨🇳 CNY（人民币）', value: 'CNY' },
  { label: '🇭🇰 HKD（港币）', value: 'HKD' },
  { label: '🇺🇸 USD（美元）', value: 'USD' },
];

// 币种颜色映射
export const currencyColorMap = {
  CNY: 'red',
  HKD: 'green',
  USD: 'purple',
};
