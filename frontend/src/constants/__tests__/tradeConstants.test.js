import { describe, it, expect } from 'vitest';
import {
  assetTypeMap,
  assetTypeReverseMap,
  assetTypeColorMap,
  tradeTypeMap,
  tradeTypeReverseMap,
  tradeTypeColorMap,
  amountColorMap,
  currencyOptions,
  currencyColorMap,
  tradeTriggerMap,
  tradeTriggerColorMap,
  triggerRefTypeMap,
  triggerRefTypeColorMap,
} from '../tradeConstants';

describe('tradeConstants - 证券类型映射', () => {
  it('assetTypeMap 应包含所有4种证券类型', () => {
    expect(Object.keys(assetTypeMap)).toHaveLength(4);
    expect(assetTypeMap).toHaveProperty('STOCK');
    expect(assetTypeMap).toHaveProperty('ETF');
    expect(assetTypeMap).toHaveProperty('OPTION_CALL');
    expect(assetTypeMap).toHaveProperty('OPTION_PUT');
  });

  it('assetTypeReverseMap 应与 assetTypeMap 互为反转', () => {
    Object.entries(assetTypeMap).forEach(([key, value]) => {
      expect(assetTypeReverseMap[value]).toBe(key);
    });
  });

  it('assetTypeColorMap 应覆盖所有证券类型', () => {
    Object.keys(assetTypeMap).forEach((key) => {
      expect(assetTypeColorMap).toHaveProperty(key);
    });
  });
});

describe('tradeConstants - 交易类型映射', () => {
  it('tradeTypeMap 应只有 BUY 和 SELL', () => {
    expect(Object.keys(tradeTypeMap)).toHaveLength(2);
    expect(tradeTypeMap.BUY).toBe('买入');
    expect(tradeTypeMap.SELL).toBe('卖出');
  });

  it('tradeTypeReverseMap 应与 tradeTypeMap 互为反转', () => {
    Object.entries(tradeTypeMap).forEach(([key, value]) => {
      expect(tradeTypeReverseMap[value]).toBe(key);
    });
  });

  it('tradeTypeColorMap 应覆盖所有交易类型', () => {
    Object.keys(tradeTypeMap).forEach((key) => {
      expect(tradeTypeColorMap).toHaveProperty(key);
    });
  });

  it('amountColorMap 应覆盖所有交易类型', () => {
    Object.keys(tradeTypeMap).forEach((key) => {
      expect(amountColorMap).toHaveProperty(key);
    });
  });
});

describe('tradeConstants - 币种配置', () => {
  it('currencyOptions 应包含3种币种', () => {
    expect(currencyOptions).toHaveLength(3);
    const values = currencyOptions.map((opt) => opt.value);
    expect(values).toContain('CNY');
    expect(values).toContain('HKD');
    expect(values).toContain('USD');
  });

  it('每个 currencyOption 应有 label 和 value', () => {
    currencyOptions.forEach((opt) => {
      expect(opt).toHaveProperty('label');
      expect(opt).toHaveProperty('value');
      expect(typeof opt.label).toBe('string');
      expect(typeof opt.value).toBe('string');
    });
  });

  it('currencyColorMap 应覆盖所有币种', () => {
    currencyOptions.forEach((opt) => {
      expect(currencyColorMap).toHaveProperty(opt.value);
    });
  });
});

describe('tradeConstants - 交易触发来源映射', () => {
  it('tradeTriggerMap 应包含3种触发类型', () => {
    expect(Object.keys(tradeTriggerMap)).toHaveLength(3);
    expect(tradeTriggerMap).toHaveProperty('MANUAL');
    expect(tradeTriggerMap).toHaveProperty('OPTION');
    expect(tradeTriggerMap).toHaveProperty('MARKET_EVENT');
  });

  it('tradeTriggerColorMap 应覆盖所有触发类型', () => {
    Object.keys(tradeTriggerMap).forEach((key) => {
      expect(tradeTriggerColorMap).toHaveProperty(key);
    });
  });
});

describe('tradeConstants - 触发关联类型映射', () => {
  it('triggerRefTypeMap 应包含7种关联类型', () => {
    expect(Object.keys(triggerRefTypeMap)).toHaveLength(7);
    expect(triggerRefTypeMap).toHaveProperty('NONE');
    expect(triggerRefTypeMap).toHaveProperty('STOCK_SPLIT');
    expect(triggerRefTypeMap).toHaveProperty('SYMBOL_CHANGE');
    expect(triggerRefTypeMap).toHaveProperty('DIVIDEND_IN_KIND');
    expect(triggerRefTypeMap).toHaveProperty('OPTION_EXPIRE');
    expect(triggerRefTypeMap).toHaveProperty('OPTION_EXERCISE');
    expect(triggerRefTypeMap).toHaveProperty('OPTION_ASSIGNED');
  });

  it('triggerRefTypeColorMap 应覆盖所有关联类型', () => {
    Object.keys(triggerRefTypeMap).forEach((key) => {
      expect(triggerRefTypeColorMap).toHaveProperty(key);
    });
  });

  it('期权子类型应包含 OPTION_EXPIRE/OPTION_EXERCISE/OPTION_ASSIGNED', () => {
    const optionRefTypes = ['OPTION_EXPIRE', 'OPTION_EXERCISE', 'OPTION_ASSIGNED'];
    optionRefTypes.forEach((type) => {
      expect(triggerRefTypeMap).toHaveProperty(type);
      expect(typeof triggerRefTypeMap[type]).toBe('string');
    });
  });

  it('市场事件子类型应包含 STOCK_SPLIT/SYMBOL_CHANGE/DIVIDEND_IN_KIND', () => {
    const marketRefTypes = ['STOCK_SPLIT', 'SYMBOL_CHANGE', 'DIVIDEND_IN_KIND'];
    marketRefTypes.forEach((type) => {
      expect(triggerRefTypeMap).toHaveProperty(type);
      expect(typeof triggerRefTypeMap[type]).toBe('string');
    });
  });
});
