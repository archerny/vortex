// Fixed, fictional samples for rendering demos only. No account or market data.
export const AS_OF = '2026-10-30';
export const SYMBOLS = [
  { value: 'DEMO-A', label: 'DEMO-A · 示例科技', base: 180, volatility: 0.28, phase: 0.4 },
  { value: 'DEMO-B', label: 'DEMO-B · 示例能源', base: 85, volatility: 0.38, phase: 2.2 },
];
export const EXPIRIES = [7, 21, 49, 77, 140];
const round = (value, places = 2) => Number(value.toFixed(places));
export const symbolInfo = (symbol) => SYMBOLS.find((item) => item.value === symbol) || SYMBOLS[0];

export function expiryDate(days) {
  const date = new Date(`${AS_OF}T00:00:00Z`);
  date.setUTCDate(date.getUTCDate() + days);
  return date.toISOString().slice(0, 10);
}

export function makePriceData(symbol, count = 220) {
  const config = symbolInfo(symbol);
  const dates = [];
  const date = new Date(`${AS_OF}T00:00:00Z`);
  while (dates.length < count) {
    if (date.getUTCDay() !== 0 && date.getUTCDay() !== 6) dates.unshift(date.toISOString().slice(0, 10));
    date.setUTCDate(date.getUTCDate() - 1);
  }
  return dates.map((time, i) => {
    const wave = (j) => config.base * (0.84 + j * 0.0008 + 0.045 * Math.sin(j / 18 + config.phase) + 0.018 * Math.cos(j / 5));
    const open = round(wave(i) + Math.sin(i * 2.3) * config.base * 0.005);
    const close = round(wave(i + 1) + Math.cos(i * 1.7) * config.base * 0.006);
    return {
      time, open, close,
      high: round(Math.max(open, close) + config.base * (0.005 + 0.006 * Math.abs(Math.sin(i)))),
      low: round(Math.min(open, close) - config.base * (0.005 + 0.006 * Math.abs(Math.cos(i)))),
      volume: Math.round(900000 + 1100000 * Math.abs(Math.sin(i / 7)) + 600000 * Math.abs(Math.cos(i * 1.7))),
    };
  });
}

const spots = new Map();
export const spotPrice = (symbol) => {
  if (!spots.has(symbol)) spots.set(symbol, makePriceData(symbol).at(-1).close);
  return spots.get(symbol);
};

export function movingAverage(bars, period) {
  return bars.flatMap((bar, i) => i < period - 1 ? [] : [{
    time: bar.time,
    value: round(bars.slice(i - period + 1, i + 1).reduce((sum, item) => sum + item.close, 0) / period),
  }]);
}

export function makePerformance(symbol, count = 120) {
  const bars = makePriceData(symbol).slice(-count);
  let peak = 100;
  return bars.map((bar, i) => {
    const nav = round(100 * bar.close / bars[0].close + 1.6 * Math.sin(i / 11));
    peak = Math.max(peak, nav);
    return { date: bar.time, nav, benchmark: round(100 + i * 0.035 + 1.7 * Math.sin(i / 21)), drawdown: round((nav / peak - 1) * 100) };
  });
}

export function mockIv(symbol, strike, days, skew = 1) {
  const m = strike / spotPrice(symbol) - 1;
  return Math.max(0.08, symbolInfo(symbol).volatility + 1.4 * m * m - 0.09 * skew * m + 0.035 * Math.sqrt(days / 365));
}

export function makeOptionChain(symbol, days = 21, count = 121) {
  const spot = spotPrice(symbol);
  const config = symbolInfo(symbol);
  return Array.from({ length: count }, (_, i) => {
    const strike = round(spot * (0.65 + i / (count - 1) * 0.7));
    const m = strike / spot - 1;
    const iv = mockIv(symbol, strike, days);
    // Deliberately synthetic prices and sensitivities, not an option pricing model.
    const timeValue = spot * 0.035 * Math.sqrt(days / 30) * Math.exp(-7 * Math.abs(m)) * iv / config.volatility;
    const callDelta = 1 / (1 + Math.exp(13 * m / Math.sqrt(days / 30)));
    const gamma = 0.045 * Math.exp(-40 * m * m) / Math.sqrt(days / 30);
    const quote = (intrinsic, delta, seed) => {
      const mid = intrinsic + timeValue;
      return {
        bid: round(Math.max(0.01, mid - 0.08)), ask: round(Math.max(0.02, mid + 0.08)),
        iv: round(iv * 100), delta: round(delta, 3), gamma: round(gamma, 4),
        theta: round(-timeValue / days / 2, 3), vega: round(spot * 0.001 * Math.sqrt(days / 30) * Math.exp(-8 * m * m), 3),
        volume: Math.round(30 + 2400 * Math.exp(-20 * m * m) * Math.abs(Math.sin(i * 1.7 + seed))),
        openInterest: Math.round(300 + 18000 * Math.exp(-15 * m * m) * (0.3 + 0.7 * Math.abs(Math.cos(i * 0.7 + seed)))),
      };
    };
    return {
      key: `${symbol}-${days}-${strike}`, strike,
      call: quote(Math.max(spot - strike, 0), callDelta, 1),
      put: quote(Math.max(strike - spot, 0), callDelta - 1, 2),
    };
  });
}

export const STRATEGIES = [
  { value: 'call-spread', label: '牛市看涨价差' },
  { value: 'long-call', label: '买入看涨期权' },
  { value: 'strangle', label: '买入宽跨式' },
];

export function makePayoff(symbol, strategy = 'call-spread', contracts = 1) {
  const spot = spotPrice(symbol);
  const strike = Math.round(spot / 5) * 5;
  const width = 20;
  const premium = strategy === 'call-spread' ? 6.8 : strategy === 'long-call' ? 8.5 : 10.2;
  const points = Array.from({ length: 101 }, (_, i) => {
    const price = round(spot * (0.6 + i * 0.008));
    const intrinsic = strategy === 'call-spread'
      ? Math.min(Math.max(price - strike, 0), width)
      : strategy === 'long-call'
        ? Math.max(price - strike, 0)
        : Math.max(price - strike - 10, 0) + Math.max(strike - 10 - price, 0);
    return [price, round((intrinsic - premium) * 100 * contracts)];
  });
  return { strike, width, premium, points };
}

export function makeRiskCurve(symbol, days = 21, metric = 'delta') {
  const spot = spotPrice(symbol);
  return Array.from({ length: 81 }, (_, i) => {
    const price = round(spot * (0.65 + i * 0.00875));
    const m = price / spot - 1;
    return [price, metric === 'delta'
      ? 1 / (1 + Math.exp(-13 * m / Math.sqrt(days / 30)))
      : 0.045 * Math.exp(-40 * m * m) / Math.sqrt(days / 30)];
  });
}

export function makeSurface(symbol, mode = 'iv', skew = 1) {
  const spot = spotPrice(symbol);
  const x = Array.from({ length: 35 }, (_, i) => round(spot * (0.65 + i / 34 * 0.7)));
  const y = [7, 14, 21, 30, 45, 60, 90, 120, 150, 180];
  const strike = Math.round(spot / 5) * 5;
  const z = y.map((days) => x.map((price) => mode === 'iv'
    ? round(mockIv(symbol, price, days, skew) * 100)
    : round((Math.max(price - strike, 0) + spot * 0.04 * Math.sqrt(days / 180) * Math.exp(-5 * Math.abs(price / spot - 1)) - 8.5) * 100)));
  return { x, y, z };
}
