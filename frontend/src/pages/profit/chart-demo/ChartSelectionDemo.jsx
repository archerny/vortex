import React, { Component, lazy, Suspense, useEffect, useState } from 'react';
import { Alert, Button, Select, Spin, Tabs, Tag } from 'antd';
import { AreaChartOutlined, DotChartOutlined, ExperimentOutlined, TableOutlined } from '@ant-design/icons';
import { usePageHeader } from '../../../contexts/PageHeaderContext';
import { AS_OF, SYMBOLS } from './mockData';
import './chart-demo.css';

const PricePanel = lazy(() => import('./PricePanel'));
const AnalysisPanel = lazy(() => import('./AnalysisPanel'));
const SurfacePanel = lazy(() => import('./SurfacePanel'));
const OptionChainPanel = lazy(() => import('./OptionChainPanel'));

class PanelBoundary extends Component {
  state = { failed: false };
  static getDerivedStateFromError() { return { failed: true }; }
  render() {
    if (this.state.failed) return <Alert type="error" showIcon message="演示面板加载失败" description="请重新加载页面后重试。" action={<Button onClick={() => window.location.reload()}>重新加载</Button>} />;
    return this.props.children;
  }
}

export default function ChartSelectionDemo() {
  const [symbol, setSymbol] = useState(SYMBOLS[0].value);
  const { setPageHeader, clearPageHeader } = usePageHeader();
  useEffect(() => {
    setPageHeader({ breadcrumbs: [{ label: '盈亏分析' }, { label: '技术选型 Demo' }] });
    return clearPageHeader;
  }, [setPageHeader, clearPageHeader]);

  const tools = [
    { key: 'price', title: '股价与交易', library: 'Lightweight Charts', icon: <AreaChartOutlined />, content: <PricePanel symbol={symbol} /> },
    { key: 'analysis', title: '收益与期权分析', library: 'Apache ECharts', icon: <ExperimentOutlined />, content: <AnalysisPanel symbol={symbol} /> },
    { key: 'surface', title: '三维曲面', library: 'Plotly.js', icon: <DotChartOutlined />, content: <SurfacePanel symbol={symbol} /> },
    { key: 'chain', title: '期权链', library: 'Ant Design Table', icon: <TableOutlined />, content: <OptionChainPanel symbol={symbol} /> },
  ];

  return (
    <div className="chart-demo">
      <header className="demo-hero">
        <div>
          <div className="demo-eyebrow">VORTEX / CHART LAB</div>
          <h1>图表实验室 <Tag color="blue">技术选型 Demo</Tag></h1>
          <p>从价格走势到期权曲面，探索四种开源工具的交互与表达。</p>
        </div>
        <div className="demo-context">
          <label htmlFor="demo-symbol">演示标的</label>
          <Select id="demo-symbol" aria-label="演示标的" value={symbol} options={SYMBOLS.map(({ value, label }) => ({ value, label }))} onChange={setSymbol} style={{ width: 218 }} />
          <span>样本时点 {AS_OF} · USD</span>
        </div>
      </header>
      <div className="demo-data-banner"><span className="demo-status-dot" />合成数据沙盒<span>所有价格、报价与曲面均为固定模拟样本，可自由操作。</span></div>
      <Tabs
        className="demo-tabs"
        defaultActiveKey="price"
        destroyOnHidden
        items={tools.map((tool) => ({
          key: tool.key,
          label: <span className="demo-tab-label">{tool.icon}<span>{tool.title}<small>{tool.library}</small></span></span>,
          children: <PanelBoundary key={tool.key}><Suspense fallback={<div className="demo-loading"><Spin /><span>正在加载演示图表…</span></div>}>{tool.content}</Suspense></PanelBoundary>,
        }))}
      />
      <footer className="demo-footer">仅用于前端工具效果评估。期权报价、敏感度及收益曲面均为合成示意，未接入正式定价模型。</footer>
    </div>
  );
}
