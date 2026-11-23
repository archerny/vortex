import React from 'react';
import { feeText, hasFee } from '../../utils/tradeFee';
import { Card, Row, Col, Statistic, Divider } from 'antd';
import { PieChart, Pie, Cell, Tooltip, ResponsiveContainer, BarChart, Bar, XAxis, YAxis, CartesianGrid } from 'recharts';
import './TradeStatisticsPanel.css';

/**
 * 交易统计面板组件
 * 包含：交易概览 + 交易类型分布饼图 + Top 10 热门证券柱状图
 */
const TradeStatisticsPanel = ({ statistics, amountVisible }) => {
  if (!statistics) return null;

  // 饼图数据
  const pieData = [
    { type: '股票', value: statistics.stockCount || 0 },
    { type: 'CALL期权', value: statistics.optionCallCount || 0 },
    { type: 'PUT期权', value: statistics.optionPutCount || 0 },
    { type: 'ETF', value: statistics.etfCount || 0 },
  ].filter(item => item.value > 0);
  const pieTotal = pieData.reduce((total, entry) => total + entry.value, 0);

  // 饼图颜色映射
  const PIE_COLORS = {
    '股票': '#1890ff',
    'CALL期权': '#52c41a',
    'PUT期权': '#ff7875',
    'ETF': '#13c2c2',
  };

  // 自定义饼图标签渲染
  const renderPieLabel = ({ cx, cy, midAngle, innerRadius, outerRadius, percent }) => {
    const RADIAN = Math.PI / 180;
    const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
    if (percent * 2 * Math.PI * radius < 44 || outerRadius - innerRadius < 14) return null;

    const x = cx + radius * Math.cos(-midAngle * RADIAN);
    const y = cy + radius * Math.sin(-midAngle * RADIAN);
    return (
      <text x={x} y={y} fill="#fff" textAnchor="middle" dominantBaseline="central" fontSize={12} fontWeight="bold">
        {`${(percent * 100).toFixed(1)}%`}
      </text>
    );
  };

  // 自定义 Tooltip
  const PieTooltipContent = ({ active, payload }) => {
    if (active && payload && payload.length) {
      const data = payload[0];
      const percent = ((data.value / pieTotal) * 100).toFixed(1);
      return (
        <div style={{ background: '#fff', padding: '8px 12px', border: '1px solid #e8e8e8', borderRadius: 4, boxShadow: '0 2px 8px rgba(0,0,0,0.15)' }}>
          <div style={{ color: data.payload.fill || '#333', fontWeight: 500 }}>{data.name}</div>
          <div style={{ color: '#666', fontSize: 13 }}>{`${data.value}次 (${percent}%)`}</div>
        </div>
      );
    }
    return null;
  };

  return (
    <Row gutter={[16, 16]} style={{ marginBottom: 16 }}>
      {/* 左侧：交易概览 */}
      <Col xs={24} lg={6}>
        <Card title="交易概览" size="small" style={{ height: '100%' }}>
          <Statistic
            title="总交易次数"
            value={statistics.totalCount}
            suffix="次"
            valueStyle={{ color: '#1890ff', fontSize: 28 }}
          />
          <Divider style={{ margin: '12px 0' }} />
          <Statistic
            title="涉及证券数"
            value={statistics.distinctSymbolCount || 0}
            suffix="只"
            valueStyle={{ color: '#36cfc9', fontSize: 28 }}
          />
          <Divider style={{ margin: '12px 0' }} />
          <div>
            <div style={{ color: 'rgba(0, 0, 0, 0.45)', marginBottom: 8, fontSize: 14 }}>总交易费用</div>
            {amountVisible ? (
              <div style={{ fontSize: 16 }}>
                {hasFee(statistics, 'totalFeeUSD') && (
                  <div style={{ marginBottom: 4 }}>
                    <span style={{ color: '#9254de', fontWeight: 'bold' }}>US$ {feeText(statistics, 'totalFeeUSD')}</span>
                  </div>
                )}
                {hasFee(statistics, 'totalFeeCNY') && (
                  <div style={{ marginBottom: 4 }}>
                    <span style={{ color: '#69b1ff', fontWeight: 'bold' }}>CN¥ {feeText(statistics, 'totalFeeCNY')}</span>
                  </div>
                )}
                {hasFee(statistics, 'totalFeeHKD') && (
                  <div style={{ marginBottom: 4 }}>
                    <span style={{ color: '#95de64', fontWeight: 'bold' }}>HK$ {feeText(statistics, 'totalFeeHKD')}</span>
                  </div>
                )}
                {!['totalFeeUSD', 'totalFeeCNY', 'totalFeeHKD'].some((field) => hasFee(statistics, field)) && (
                  <span style={{ color: '#999' }}>暂无费用数据</span>
                )}
              </div>
            ) : (
              <span style={{ color: '#999', fontSize: 24, fontWeight: 'bold' }}>****</span>
            )}
          </div>
        </Card>
      </Col>
      {/* 中间：交易类型分布饼图 */}
      <Col xs={24} md={12} lg={9}>
        <Card title="交易类型分布" size="small" style={{ height: '100%' }}>
          {pieData.length > 0 ? (
            <>
              <ResponsiveContainer width="100%" height={216}>
                <PieChart>
                  <Pie
                    data={pieData}
                    dataKey="value"
                    nameKey="type"
                    cx="50%"
                    cy="50%"
                    outerRadius="94%"
                    innerRadius="48%"
                    stroke="#fff"
                    strokeWidth={2}
                    label={renderPieLabel}
                    labelLine={false}
                    isAnimationActive={true}
                  >
                    {pieData.map((entry) => (
                      <Cell key={entry.type} fill={PIE_COLORS[entry.type]} />
                    ))}
                  </Pie>
                  <Tooltip content={<PieTooltipContent />} />
                </PieChart>
              </ResponsiveContainer>
              <ul className="trade-distribution-legend" role="list" aria-label="交易类型图例">
                {pieData.map(({ type, value }) => (
                  <li key={type}>
                    <span aria-hidden="true" style={{ backgroundColor: PIE_COLORS[type] }} />
                    {`${type} ${value}次 (${((value / pieTotal) * 100).toFixed(1)}%)`}
                  </li>
                ))}
              </ul>
            </>
          ) : (
            <div style={{ height: 280, display: 'flex', alignItems: 'center', justifyContent: 'center', color: '#999' }}>
              暂无交易数据
            </div>
          )}
        </Card>
      </Col>
      {/* 右侧：Top 10 热门证券（Recharts 水平条形图） */}
      <Col xs={24} md={12} lg={9}>
        <Card title="Top 10 热门证券" size="small" style={{ height: '100%' }}>
          {statistics.topSymbols && statistics.topSymbols.length > 0 ? (() => {
            const barColors = ['#597ef7', '#73d13d', '#ff7a45', '#36cfc9', '#f759ab',
                               '#ffc53d', '#40a9ff', '#9254de', '#ff4d4f', '#95de64'];
            const barData = statistics.topSymbols.map((item, idx) => ({
              symbol: item.symbol,
              count: item.count,
              fill: barColors[idx % barColors.length],
            }));

            const BarTooltipContent = ({ active, payload }) => {
              if (active && payload && payload.length) {
                const data = payload[0].payload;
                return (
                  <div style={{ background: '#fff', padding: '6px 12px', border: '1px solid #e8e8e8', borderRadius: 4, boxShadow: '0 2px 8px rgba(0,0,0,0.15)' }}>
                    <div style={{ color: '#333', fontWeight: 500, marginBottom: 2 }}>{data.symbol}</div>
                    <div style={{ color: '#666', fontSize: 13 }}>交易次数：<span style={{ color: data.fill, fontWeight: 'bold' }}>{data.count}次</span></div>
                  </div>
                );
              }
              return null;
            };

            return (
              <ResponsiveContainer width="100%" height={280}>
                <BarChart
                  data={barData}
                  layout="vertical"
                  margin={{ top: 4, right: 40, bottom: 4, left: 8 }}
                >
                  <CartesianGrid strokeDasharray="3 3" horizontal={false} stroke="#f0f0f0" />
                  <XAxis
                    type="number"
                    allowDecimals={false}
                    tick={{ fontSize: 12, fill: '#999' }}
                    axisLine={{ stroke: '#e8e8e8' }}
                    tickLine={false}
                  />
                  <YAxis
                    type="category"
                    dataKey="symbol"
                    width={72}
                    tick={{ fontSize: 12, fill: '#333' }}
                    axisLine={false}
                    tickLine={false}
                  />
                  <Tooltip content={<BarTooltipContent />} cursor={{ fill: 'rgba(0,0,0,0.04)' }} />
                  <Bar
                    dataKey="count"
                    radius={[0, 4, 4, 0]}
                    barSize={16}
                    isAnimationActive={true}
                    label={{ position: 'right', fill: '#666', fontSize: 12, formatter: (v) => `${v}次` }}
                  >
                    {barData.map((entry, idx) => (
                      <Cell key={`bar-${idx}`} fill={entry.fill} />
                    ))}
                  </Bar>
                </BarChart>
              </ResponsiveContainer>
            );
          })() : (
            <div style={{ height: 280, display: 'flex', alignItems: 'center', justifyContent: 'center', color: '#999' }}>
              暂无数据
            </div>
          )}
        </Card>
      </Col>
    </Row>
  );
};

export default TradeStatisticsPanel;
