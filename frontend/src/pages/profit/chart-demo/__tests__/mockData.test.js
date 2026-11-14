import { describe, expect, it } from 'vitest';
import { AS_OF, EXPIRIES, expiryDate, makeOptionChain, makePayoff, makePerformance, makePriceData, makeSurface, movingAverage, spotPrice } from '../mockData';

describe('chart demo synthetic data', () => {
  it('has deterministic, chronologically ordered OHLC bars with valid bounds', () => {
    const bars = makePriceData('DEMO-A');
    expect(bars).toEqual(makePriceData('DEMO-A'));
    expect(bars.at(-1).time).toBe(AS_OF);
    bars.forEach((bar, i) => {
      expect(bar.low).toBeLessThanOrEqual(Math.min(bar.open, bar.close));
      expect(bar.high).toBeGreaterThanOrEqual(Math.max(bar.open, bar.close));
      expect(bar.low).toBeGreaterThan(0);
      expect(bar.volume).toBeGreaterThan(0);
      if (i) expect(bar.time > bars[i - 1].time).toBe(true);
    });
    expect(movingAverage(bars, 20)).toHaveLength(bars.length - 19);
  });
  it('keeps NAV normalized and drawdown nonpositive', () => {
    const rows = makePerformance('DEMO-A');
    expect(rows[0].nav).toBe(100);
    expect(rows.every((r) => r.drawdown <= 0)).toBe(true);
  });
  it('generates unique strike rows, valid quotes and consistent call/put sides for all sizes', () => {
    for (const symbol of ['DEMO-A', 'DEMO-B']) {
      for (const count of [41, 121, 401]) {
        const rows = makeOptionChain(symbol, 21, count);
        expect(new Set(rows.map((r) => r.key)).size).toBe(count);
        rows.forEach((row) => {
          expect(row.strike).toBeGreaterThan(0);
          for (const side of ['call', 'put']) {
            expect(row[side].bid).toBeGreaterThan(0);
            expect(row[side].ask).toBeGreaterThanOrEqual(row[side].bid);
            expect(row[side].iv).toBeGreaterThan(0);
          }
          expect(row.call.delta).toBeGreaterThanOrEqual(0);
          expect(row.call.delta).toBeLessThanOrEqual(1);
          expect(row.put.delta).toBeCloseTo(row.call.delta - 1, 2);
        });
        expect(Math.min(...rows.map((r) => Math.abs(r.strike - spotPrice(symbol))))).toBeLessThan(0.01);
      }
    }
  });
  it('uses future Friday expiries', () => {
    for (const days of EXPIRIES) {
      const date = expiryDate(days);
      expect(date > AS_OF).toBe(true);
      expect(new Date(`${date}T00:00:00Z`).getUTCDay()).toBe(5);
    }
  });
  it('caps a synthetic debit spread and scales its payoff by contract count', () => {
    const one = makePayoff('DEMO-A', 'call-spread', 1);
    const two = makePayoff('DEMO-A', 'call-spread', 2);
    expect(Math.min(...one.points.map((p) => p[1]))).toBe(-680);
    expect(Math.max(...one.points.map((p) => p[1]))).toBe(1320);
    one.points.forEach((point, i) => expect(two.points[i][1]).toBeCloseTo(point[1] * 2, 1));
  });
  it('provides finite rectangular surfaces for both modes and distinct symbols', () => {
    for (const mode of ['iv', 'payoff']) {
      const surface = makeSurface('DEMO-A', mode);
      expect(surface.z).toHaveLength(surface.y.length);
      surface.z.forEach((row) => {
        expect(row).toHaveLength(surface.x.length);
        expect(row.every(Number.isFinite)).toBe(true);
      });
    }
    expect(makeSurface('DEMO-A').z).not.toEqual(makeSurface('DEMO-B').z);
  });
});
