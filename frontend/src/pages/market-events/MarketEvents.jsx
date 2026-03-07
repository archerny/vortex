import React from 'react';
import { Card, Tabs, Typography } from 'antd';
import {
  SwapOutlined,
  SplitCellsOutlined,
  GiftOutlined,
  AlertOutlined,
} from '@ant-design/icons';
import SymbolChangeTab from './SymbolChangeTab';
import StockSplitTab from './StockSplitTab';
import DividendInKindTab from './DividendInKindTab';

const { Text, Title } = Typography;

/**
 * 市场异动事件主页面
 * 包含三个 Tab：代码变更、拆股事件、实物分红
 */
const MarketEvents = () => {
  const tabItems = [
    {
      key: 'symbolChange',
      label: (
        <span>
          <SwapOutlined style={{ marginRight: 6 }} />
          代码变更
        </span>
      ),
      children: <SymbolChangeTab />,
    },
    {
      key: 'stockSplit',
      label: (
        <span>
          <SplitCellsOutlined style={{ marginRight: 6 }} />
          拆股事件
        </span>
      ),
      children: <StockSplitTab />,
    },
    {
      key: 'dividendInKind',
      label: (
        <span>
          <GiftOutlined style={{ marginRight: 6 }} />
          实物分红
        </span>
      ),
      children: <DividendInKindTab />,
    },
  ];

  return (
    <div>
      {/* 顶部标题栏 */}
      <Card style={{ marginBottom: 16 }} bodyStyle={{ padding: '16px 24px' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
          <AlertOutlined style={{ fontSize: 22, color: '#faad14' }} />
          <div>
            <Title level={5} style={{ margin: 0 }}>市场异动事件</Title>
            <Text type="secondary" style={{ fontSize: 13 }}>
              记录市场上的非正常行为数据，用于追溯交易记录中不可解释的情况（代码变更、拆股、实物分红）
            </Text>
          </div>
        </div>
      </Card>

      {/* Tab 切换区域 */}
      <Tabs defaultActiveKey="symbolChange" items={tabItems} type="card" />
    </div>
  );
};

export default MarketEvents;
