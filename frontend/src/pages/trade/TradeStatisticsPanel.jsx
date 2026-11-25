import React, { lazy, Suspense } from 'react';
import { chartPalette } from '../../constants/chartPalette';
import { assetTypeMap } from '../../constants/tradeConstants';
import { feeText, hasFee } from '../../utils/tradeFee';
import './TradeStatisticsPanel.css';

const TradeStatisticsChart = lazy(() => import('./TradeStatisticsChart'));
const numberFormat = new Intl.NumberFormat('zh-CN');
const currencyTotals = [
  ['totalFeeUSD', 'US$'],
  ['totalFeeCNY', 'CN¥'],
  ['totalFeeHKD', 'HK$'],
];
const chartStyle = {
  animation: false,
  textStyle: { color: chartPalette.muted, fontSize: 12 },
};
const tooltipStyle = {
  confine: true,
  renderMode: 'richText',
  backgroundColor: '#fff',
  borderColor: '#dce5f1',
  textStyle: { color: chartPalette.ink, fontSize: 12 },
};

function distributionOption(entries, total) {
  return {
    ...chartStyle,
    tooltip: {
      ...tooltipStyle,
      trigger: 'item',
      formatter: ({ name, value }) =>
        `${name}  ${numberFormat.format(value)} 次 (${((value / total) * 100).toFixed(1)}%)`,
    },
    series: [{
      type: 'pie',
      radius: ['65%', '93%'],
      label: { show: false },
      itemStyle: { borderColor: '#fff', borderWidth: 3, borderRadius: 4 },
      emphasis: { scaleSize: 4 },
      data: entries.map(({ name, value, color }) => ({ name, value, itemStyle: { color } })),
    }],
  };
}

function rankingOption(symbols) {
  return {
    ...chartStyle,
    tooltip: { ...tooltipStyle, trigger: 'axis', valueFormatter: (value) => `${numberFormat.format(value)} 次` },
    grid: { left: 64, right: 62, top: 2, bottom: 23 },
    xAxis: {
      type: 'value',
      minInterval: 1,
      splitNumber: 4,
      axisLabel: { color: chartPalette.axis, hideOverlap: true },
      splitLine: { lineStyle: { color: chartPalette.grid, type: 'dashed' } },
    },
    yAxis: {
      type: 'category',
      data: symbols.map(({ symbol }) => symbol),
      inverse: true,
      axisTick: { show: false },
      axisLine: { show: false },
      axisLabel: { color: chartPalette.ink, width: 56, overflow: 'truncate' },
    },
    series: [{
      type: 'bar',
      name: '交易次数',
      barMaxWidth: 12,
      showBackground: true,
      backgroundStyle: { color: '#f3f6fb', borderRadius: 4 },
      itemStyle: { color: '#8ca6e3', borderRadius: [0, 4, 4, 0] },
      emphasis: { itemStyle: { color: chartPalette.blue } },
      label: {
        show: true,
        position: 'right',
        color: chartPalette.muted,
        formatter: ({ value }) => `${numberFormat.format(value)} 次`,
      },
      data: symbols.map(({ count }, index) => index === 0 ? { value: count, itemStyle: { color: chartPalette.blue } } : count),
    }],
  };
}

function TradeOverview({ statistics, amountVisible }) {
  const fees = amountVisible
    ? currencyTotals.filter(([field]) => hasFee(statistics, field))
    : [];

  return (
    <section className="trade-stat-card trade-stat-overview">
      <h2>交易概览</h2>
      <dl className="trade-overview-metrics">
        <div>
          <dt>总交易次数</dt>
          <dd className="trade-overview-count">{numberFormat.format(statistics.totalCount || 0)} <span>次</span></dd>
        </div>
        <div>
          <dt>涉及证券数</dt>
          <dd className="trade-overview-count">{numberFormat.format(statistics.distinctSymbolCount || 0)} <span>只</span></dd>
        </div>
        <div className="trade-overview-fee">
          <dt>总交易费用</dt>
          <dd>
            {amountVisible ? (
              fees.length > 0 ? (
                <ul className="trade-fee-values">
                  {fees.map(([field, currency]) => <li key={field}>{currency} {feeText(statistics, field)}</li>)}
                </ul>
              ) : <span className="trade-stat-muted">暂无费用数据</span>
            ) : <strong>****</strong>}
          </dd>
        </div>
      </dl>
    </section>
  );
}

export default function TradeStatisticsPanel({ statistics, amountVisible }) {
  if (!statistics) return null;

  const distribution = [
    { name: assetTypeMap.STOCK, value: statistics.stockCount, color: chartPalette.blue },
    { name: assetTypeMap.OPTION_CALL, value: statistics.optionCallCount, color: chartPalette.teal },
    { name: assetTypeMap.OPTION_PUT, value: statistics.optionPutCount, color: chartPalette.gold },
    { name: assetTypeMap.ETF, value: statistics.etfCount, color: '#859dbe' },
  ].filter(({ value }) => value > 0);
  const distributionTotal = distribution.reduce((total, { value }) => total + value, 0);
  const topSymbols = statistics.topSymbols || [];

  return (
    <div className="trade-statistics">
      <div className="trade-statistics-grid">
        <TradeOverview statistics={statistics} amountVisible={amountVisible} />
        <section className="trade-stat-card trade-stat-distribution">
          <h2>交易类型分布</h2>
          {distribution.length > 0 ? (
            <div className="trade-distribution-body">
              <div className="trade-distribution-plot">
                <Suspense fallback={null}>
                  <TradeStatisticsChart option={distributionOption(distribution, distributionTotal)} label="交易类型占比" height="100%" />
                </Suspense>
                <div className="trade-distribution-total" aria-hidden="true">
                  <strong>{numberFormat.format(distributionTotal)}</strong>
                  <span>次</span>
                </div>
              </div>
              <ul className="trade-distribution-legend" role="list" aria-label="交易类型图例">
                {distribution.map(({ name, value, color }) => (
                  <li key={name}>
                    <span className="trade-legend-swatch" style={{ backgroundColor: color }} aria-hidden="true" />
                    <span className="trade-legend-name">{name}</span>
                    <span className="trade-legend-count">{numberFormat.format(value)} 次</span>
                    <strong>{((value / distributionTotal) * 100).toFixed(1)}%</strong>
                  </li>
                ))}
              </ul>
            </div>
          ) : <p className="trade-stat-empty">暂无交易数据</p>}
        </section>
        <section className="trade-stat-card trade-stat-ranking">
          <h2>热门证券 <span>Top 10</span></h2>
          {topSymbols.length > 0 ? (
            <div className="trade-ranking-plot" style={{ height: Math.max(174, topSymbols.length * 24 + 24) }}>
              <Suspense fallback={null}>
                <TradeStatisticsChart
                  option={rankingOption(topSymbols)}
                  label={`热门证券交易次数：${topSymbols.map(({ symbol, count }) => `${symbol} ${numberFormat.format(count)} 次`).join('、')}`}
                  height="100%"
                />
              </Suspense>
            </div>
          ) : <p className="trade-stat-empty">暂无数据</p>}
        </section>
      </div>
    </div>
  );
}
