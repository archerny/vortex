import React, { useEffect, useMemo, useRef, useState } from 'react';
import { Button, Segmented, Space, Switch, Tag } from 'antd';
import { ReloadOutlined } from '@ant-design/icons';
import { CandlestickSeries, ColorType, createChart, createSeriesMarkers, HistogramSeries, LineSeries } from 'lightweight-charts';
import { makePriceData, movingAverage } from './mockData';

export default function PricePanel({ symbol }) {
  const [range, setRange] = useState(120);
  const [showAverage, setShowAverage] = useState(true);
  const [hover, setHover] = useState(null);
  const container = useRef(null);
  const chartRef = useRef(null);
  const bars = useMemo(() => makePriceData(symbol).slice(-range), [symbol, range]);
  const last = bars.at(-1);
  const change = ((last.close / bars[0].close - 1) * 100);

  useEffect(() => {
    const host = container.current;
    const chart = createChart(host, {
      width: host.clientWidth, height: 484,
      layout: { background: { type: ColorType.Solid, color: '#ffffff' }, textColor: '#66758a', fontFamily: 'inherit', attributionLogo: true },
      grid: { vertLines: { color: '#f1f4f8' }, horzLines: { color: '#f1f4f8' } },
      rightPriceScale: { borderColor: '#e8edf4' },
      timeScale: { borderColor: '#e8edf4', rightOffset: 5, barSpacing: 7 },
      crosshair: { mode: 0 },
    });
    chartRef.current = chart;
    const candles = chart.addSeries(CandlestickSeries, {
      upColor: '#16a085', downColor: '#e26561', wickUpColor: '#16a085', wickDownColor: '#e26561', borderVisible: false,
    });
    candles.setData(bars.map(({ time, open, high, low, close }) => ({ time, open, high, low, close })));
    const averages = [[5, '#3c70ed'], [20, '#d7a642']];
    averages.forEach(([period, color]) => {
      const series = chart.addSeries(LineSeries, { color, lineWidth: 2, priceLineVisible: false, lastValueVisible: false, visible: showAverage, title: `MA${period}` });
      series.setData(movingAverage(bars, period));
    });
    const volume = chart.addSeries(HistogramSeries, { priceFormat: { type: 'volume' }, priceLineVisible: false, lastValueVisible: false }, 1);
    volume.setData(bars.map((bar) => ({ time: bar.time, value: bar.volume, color: bar.close >= bar.open ? '#91d6c5' : '#f0b5b2' })));
    chart.panes()[1].setHeight(108);
    createSeriesMarkers(candles, [
      { time: bars[Math.floor(bars.length * 0.28)].time, position: 'belowBar', color: '#16a085', shape: 'arrowUp', text: '模拟买入' },
      { time: bars[Math.floor(bars.length * 0.72)].time, position: 'aboveBar', color: '#e26561', shape: 'arrowDown', text: '模拟卖出' },
    ]);
    chart.subscribeCrosshairMove((event) => {
      const candle = event.seriesData.get(candles);
      const time = event.time && typeof event.time === 'object'
        ? `${event.time.year}-${String(event.time.month).padStart(2, '0')}-${String(event.time.day).padStart(2, '0')}`
        : event.time;
      setHover(candle ? { ...candle, time } : null);
    });
    chart.timeScale().fitContent();
    const observer = new ResizeObserver(() => { if (host.clientWidth > 0) chart.applyOptions({ width: host.clientWidth }); });
    observer.observe(host);
    return () => { observer.disconnect(); chart.remove(); chartRef.current = null; };
  }, [bars, showAverage]);

  useEffect(() => setHover(null), [bars]);
  const display = hover || last;

  return (
    <section>
      <div className="demo-panel-heading"><div><h2>让价格走势回到主角</h2><p>K 线、均线与成交量共享时间轴，买卖点直接落在价格上。</p></div><Tag>TradingView Lightweight Charts</Tag></div>
      <div className="demo-metric-grid">
        <div><span>模拟收盘价</span><strong>${last.close.toFixed(2)}</strong><small>{symbol}</small></div>
        <div><span>区间涨跌幅</span><strong className={change >= 0 ? 'demo-positive' : 'demo-negative'}>{change >= 0 ? '+' : ''}{change.toFixed(2)}%</strong><small>所选 {bars.length} 个样本日</small></div>
        <div><span>样本成交量</span><strong>{(last.volume / 1000000).toFixed(2)}<em>M</em></strong><small>股 / 日</small></div>
        <div><span>叠加指标</span><strong className="demo-indicators">MA5 <i /> MA20</strong><small>十字光标查看每日价格</small></div>
      </div>
      <div className="demo-chart-card">
        <div className="demo-toolbar">
          <Space wrap><Segmented aria-label="股价样本区间" value={range} onChange={setRange} options={[{ label: '近 60 日', value: 60 }, { label: '近 120 日', value: 120 }, { label: '全部样本', value: 220 }]} /><span className="demo-switch-label"><Switch checked={showAverage} onChange={setShowAverage} size="small" aria-label="显示均线" /> 显示均线</span></Space>
          <Button icon={<ReloadOutlined />} onClick={() => chartRef.current?.timeScale().fitContent()}>重置视图</Button>
        </div>
        <div className="demo-ohlc"><b>{display.time || last.time}</b><span>开 <strong>{display.open?.toFixed(2)}</strong></span><span>高 <strong>{display.high?.toFixed(2)}</strong></span><span>低 <strong>{display.low?.toFixed(2)}</strong></span><span>收 <strong>{display.close?.toFixed(2)}</strong></span></div>
        <div ref={container} className="demo-price-chart" role="img" aria-label={`${symbol} 合成日线与成交量图`} />
        <div className="demo-chart-caption">滚轮缩放 · 拖动平移 · 移动鼠标查看十字光标 · 拖动分隔线调整窗格</div>
      </div>
      <div className="demo-attribution">TradingView Lightweight Charts™ · Copyright (с) 2025 TradingView, Inc. <a href="https://www.tradingview.com/" target="_blank" rel="noreferrer">TradingView</a></div>
    </section>
  );
}
