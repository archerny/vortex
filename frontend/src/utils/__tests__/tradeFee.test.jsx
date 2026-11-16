import React from 'react';
import { renderToStaticMarkup } from 'react-dom/server';
import { describe, expect, it } from 'vitest';
import { feeText, hasFee, compareFees } from '../tradeFee';
import getTradeColumns from '../../pages/trade/TradeColumns';
import TradeStatisticsPanel from '../../pages/trade/TradeStatisticsPanel';

describe('exact trade fee display', () => {
  const exact = '123456789012.1234567890123456789';

  it('renders the exact list value and preserves the amount visibility switch', () => {
    const record = { fee: Number(exact), feeExact: exact };
    const visible = getTradeColumns(true, {}, {}, () => {}).find((column) => column.key === 'fee');
    const hidden = getTradeColumns(false, {}, {}, () => {}).find((column) => column.key === 'fee');
    expect(visible.render(record.fee, record)).toBe(exact);
    expect(hidden.render(record.fee, record)).toBe('****');
    expect(visible.render(null, { fee: null })).toBe('-');
  });

  it('renders full currency totals, including values that underflow JavaScript numbers', () => {
    const tiny = `0.${'0'.repeat(399)}1`;
    const statistics = { totalCount: 0, totalFeeUSD: Number(exact), totalFeeUSDExact: exact,
      totalFeeCNY: 0, totalFeeCNYExact: tiny, totalFeeHKD: 0, totalFeeHKDExact: '0.00' };
    const html = renderToStaticMarkup(<TradeStatisticsPanel statistics={statistics} amountVisible />);
    expect(html).toContain(exact);
    expect(html).toContain(tiny);
    expect(html).not.toContain('暂无费用数据');
    const hidden = renderToStaticMarkup(<TradeStatisticsPanel statistics={statistics} amountVisible={false} />);
    expect(hidden).not.toContain(exact);
    expect(hidden).toContain('****');
  });

  it('keeps the zero fee message when all currency totals are zero', () => {
    const html = renderToStaticMarkup(<TradeStatisticsPanel
      statistics={{ totalFeeUSDExact: '0.00000000', totalFeeCNYExact: '0', totalFeeHKDExact: '0.00' }} amountVisible />);
    expect(html).toContain('暂无费用数据');
  });

  it.each([
    ['1.1234567890123456788', '1.1234567890123456789', -1],
    ['123456789012.99999999', '123456789013', -1],
    ['0.000000001', '0.00000001', -1],
    ['1.20', '1.2', 0],
    ['1e-8', '0.000000009', 1],
    ['10.01', '9.99', 1],
  ])('sorts %s against %s using exact decimal values', (left, right, expected) => {
    const column = getTradeColumns(true, {}, {}, () => {}).find((item) => item.key === 'fee');
    expect(column.sorter({ feeExact: left }, { feeExact: right })).toBe(expected);
    expect(compareFees({ feeExact: right }, { feeExact: left })).toBe(expected === 0 ? 0 : -expected);
  });

  it.each([
    [{ totalFeeUSD: 1.25 }, '1.25', true],
    [{ totalFeeUSDExact: '1.12345678' }, '1.12345678', true],
    [{ totalFeeUSD: 0 }, '0', false],
    [{ totalFeeUSDExact: '0E-8' }, '0E-8', false],
    [{}, null, false],
  ])('supports exact totals and legacy responses: %j', (record, expected, present) => {
    expect(feeText(record, 'totalFeeUSD')).toBe(expected);
    expect(hasFee(record, 'totalFeeUSD')).toBe(present);
  });
});
