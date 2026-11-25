import React, { useMemo, useState } from 'react';
import { InputNumber, Segmented, Select, Space, Tag } from 'antd';
import { use } from 'echarts/core';
import { HeatmapChart, LineChart } from 'echarts/charts';
import { AriaComponent, DataZoomComponent, GridComponent, LegendComponent, MarkLineComponent, TooltipComponent, VisualMapComponent } from 'echarts/components';
import EChart from '../../../components/EChart';
import { chartPalette } from '../../../constants/chartPalette';
import { EXPIRIES, makePayoff, makePerformance, makeRiskCurve, mockIv, spotPrice, STRATEGIES } from './mockData';

use([LineChart, HeatmapChart, GridComponent, TooltipComponent, LegendComponent, DataZoomComponent, VisualMapComponent, MarkLineComponent, AriaComponent]);

const base = {
  animationDuration: 350,
  color: [chartPalette.blue, chartPalette.teal, chartPalette.gold],
  textStyle: { fontFamily: 'inherit', color: chartPalette.muted },
  aria: { enabled: true },
  tooltip: { trigger: 'axis', confine: true },
  grid: { left: 60, right: 36, top: 42, bottom: 52 },
  xAxis: { type: 'value', scale: true, min: 'dataMin', max: 'dataMax', axisLabel: { color: chartPalette.axis }, splitLine: { show: false }, name: '标的价格 ($)', nameLocation: 'middle', nameGap: 30 },
  yAxis: { type: 'value', splitLine: { lineStyle: { color: chartPalette.grid } } },
};
function ChartCard({ title, subtitle, children, extra, wide = false }) {
  return <div className={`demo-chart-card ${wide ? 'demo-span-all' : ''}`}><div className="demo-card-heading"><div><h3>{title}</h3><p>{subtitle}</p></div>{extra}</div>{children}</div>;
}

export default function AnalysisPanel({ symbol }) {
  const [period, setPeriod] = useState(120);
  const [strategy, setStrategy] = useState('call-spread');
  const [contracts, setContracts] = useState(1);
  const [metric, setMetric] = useState('delta');
  const spot = spotPrice(symbol);
  const payoff = useMemo(() => makePayoff(symbol, strategy, contracts), [symbol, strategy, contracts]);
  const performance = useMemo(() => {
    const rows = makePerformance(symbol, period);
    return {
      ...base,
      legend: { top: 0, data: ['组合净值', '参考基准', '回撤'] },
      xAxis: { type: 'category', data: rows.map((r) => r.date), boundaryGap: false, axisLabel: { formatter: (v) => v.slice(5) } },
      yAxis: [{ ...base.yAxis, name: '净值（起点 100）', scale: true }, { ...base.yAxis, name: '回撤 %', max: 0, min: Math.min(-5, Math.floor(Math.min(...rows.map((r) => r.drawdown)) - 1)), splitLine: { show: false } }],
      grid: { left: 62, right: 52, top: 54, bottom: 54 },
      dataZoom: [{ type: 'inside' }, { type: 'slider', height: 15, bottom: 5, borderColor: '#e7edf5' }],
      series: [
        { name: '组合净值', type: 'line', showSymbol: false, data: rows.map((r) => r.nav), lineStyle: { width: 2.5 } },
        { name: '参考基准', type: 'line', showSymbol: false, data: rows.map((r) => r.benchmark), lineStyle: { type: 'dashed', width: 1.5 }, itemStyle: { color: '#9aa8bc' } },
        { name: '回撤', type: 'line', yAxisIndex: 1, showSymbol: false, data: rows.map((r) => r.drawdown), lineStyle: { width: 1, color: '#e6a09b' }, itemStyle: { color: '#e6a09b' }, areaStyle: { color: '#f5d8d5', opacity: 0.4 } },
      ],
    };
  }, [symbol, period]);
  const payoffOption = useMemo(() => ({
    ...base,
    yAxis: { ...base.yAxis, name: '到期收益 ($)' },
    series: [{ name: '到期收益', type: 'line', showSymbol: false, data: payoff.points, lineStyle: { width: 3 }, areaStyle: { opacity: 0.07 },
      markLine: { silent: true, symbol: 'none', label: { position: 'insideEndTop' }, lineStyle: { color: '#9ba9bb', type: 'dashed' }, data: [{ yAxis: 0, label: { formatter: '盈亏平衡线' } }, { xAxis: spot, label: { formatter: '当前模拟价' } }] },
    }],
  }), [payoff, spot]);
  const riskOption = useMemo(() => ({
    ...base,
    legend: { top: 0, data: ['7 天', '21 天', '49 天'] },
    yAxis: { ...base.yAxis, name: metric === 'delta' ? 'Delta（无量纲）' : 'Gamma / $' },
    series: [7, 21, 49].map((days) => ({ name: `${days} 天`, type: 'line', showSymbol: false, data: makeRiskCurve(symbol, days, metric), lineStyle: { width: 2 } })),
  }), [symbol, metric]);
  const smileOption = useMemo(() => {
    const strikes = Array.from({ length: 31 }, (_, i) => Number((spot * (0.7 + i * 0.02)).toFixed(2)));
    return {
      ...base,
      legend: { top: 0 },
      xAxis: { ...base.xAxis, name: '行权价 ($)' },
      yAxis: { ...base.yAxis, name: 'IV (%)', scale: true },
      series: [7, 49, 140].map((days) => ({ name: `${days} 天`, type: 'line', smooth: true, showSymbol: false, data: strikes.map((strike) => [strike, Number((mockIv(symbol, strike, days) * 100).toFixed(2))]) })),
    };
  }, [symbol, spot]);
  const heatmapOption = useMemo(() => {
    const strikes = Array.from({ length: 13 }, (_, i) => Number((spot * (0.76 + i * 0.04)).toFixed(1)));
    const data = EXPIRIES.flatMap((days, y) => strikes.map((strike, x) => [x, y, Number((mockIv(symbol, strike, days) * 100).toFixed(1))]));
    return {
      ...base,
      tooltip: { position: 'top', confine: true, formatter: ({ value }) => `行权价 $${strikes[value[0]]}<br/>到期剩余 ${EXPIRIES[value[1]]} 天<br/>合成 IV ${value[2]}%` },
      grid: { left: 74, right: 30, top: 15, bottom: 80 },
      xAxis: { type: 'category', data: strikes, name: '行权价 ($)', nameLocation: 'middle', nameGap: 25, splitArea: { show: true } },
      yAxis: { type: 'category', data: EXPIRIES.map((d) => `${d} 天`), name: '到期剩余', splitArea: { show: true } },
      visualMap: { min: Math.floor(Math.min(...data.map((d) => d[2]))), max: Math.ceil(Math.max(...data.map((d) => d[2]))), calculable: true, orient: 'horizontal', left: 'center', bottom: 0, itemWidth: 12, itemHeight: 180, text: ['IV 高', 'IV 低'], inRange: { color: ['#eef7fb', '#b8d8ed', '#5d9ccc', '#315b9d', '#223d6a'] } },
      series: [{ name: '合成 IV', type: 'heatmap', data, label: { show: true, formatter: ({ value }) => `${value[2]}%`, fontSize: 11 }, emphasis: { itemStyle: { borderColor: '#182e52', borderWidth: 2 } } }],
    };
  }, [symbol, spot]);

  return <section>
    <div className="demo-panel-heading"><div><h2>把收益、风险与波动率放在一起</h2><p>试试图例开关、区间缩放和参数调整，观察不同图形的表达能力。</p></div><Tag>Apache ECharts</Tag></div>
    <div className="demo-analysis-grid">
      <ChartCard title="组合净值与回撤" subtitle="合成净值归一至 100，回撤使用右侧坐标轴。" extra={<Segmented size="small" value={period} onChange={setPeriod} options={[{ label: '60 日', value: 60 }, { label: '120 日', value: 120 }, { label: '220 日', value: 220 }]} />}>
        <EChart option={performance} label="模拟组合净值、参考基准和回撤" />
      </ChartCard>
      <ChartCard title="策略到期收益" subtitle={`示例行权价 $${payoff.strike} · 期权乘数 100 · 不计费用`} extra={<Tag color="blue">USD</Tag>}>
        <div className="demo-inline-controls"><Select aria-label="收益策略" value={strategy} onChange={setStrategy} options={STRATEGIES} style={{ width: 165 }} /><Space>合约数<InputNumber aria-label="合约数" value={contracts} onChange={(v) => setContracts(Math.max(1, Math.min(20, v || 1)))} min={1} max={20} precision={0} style={{ width: 64 }} /></Space></div>
        <EChart option={payoffOption} height={277} label="模拟期权策略到期收益曲线" />
      </ChartCard>
      <ChartCard title="期权敏感度示意" subtitle="对比不同剩余期限；曲线为合成样本。" extra={<Segmented size="small" value={metric} onChange={setMetric} options={[{ label: 'Delta', value: 'delta' }, { label: 'Gamma', value: 'gamma' }]} />}>
        <EChart option={riskOption} label={`不同期限的合成 ${metric} 曲线`} />
      </ChartCard>
      <ChartCard title="波动率微笑与期限比较" subtitle="同一行权价下，对比三种到期剩余期限的合成 IV。">
        <EChart option={smileOption} label="多期限合成隐含波动率微笑" />
      </ChartCard>
      <ChartCard title="波动率热力图" subtitle="横向看行权价，纵向看期限；拖动底部色阶手柄筛选 IV 区间。" wide>
        <EChart option={heatmapOption} height={345} label="行权价与期限的合成隐含波动率热力图" />
      </ChartCard>
    </div>
  </section>;
}
