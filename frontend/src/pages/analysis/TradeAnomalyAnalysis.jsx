import React from 'react';
import { Card, Tabs, Typography } from 'antd';
import {
  AuditOutlined,
  FundOutlined,
  WarningOutlined,
} from '@ant-design/icons';
import PositionSnapshotTab from './PositionSnapshotTab';
import AnomalyAnalysisTab from './AnomalyAnalysisTab';

const { Text, Title } = Typography;

/**
 * 交易数据分析主页面
 * 包含两个 Tab：持仓快照、异常记录分析
 */
const TradeAnomalyAnalysis = () => {
  const tabItems = [
    {
      key: 'position',
      label: (
        <span>
          <FundOutlined style={{ marginRight: 6 }} />
          持仓快照
        </span>
      ),
      children: <PositionSnapshotTab />,
    },
    {
      key: 'anomaly',
      label: (
        <span>
          <WarningOutlined style={{ marginRight: 6 }} />
          异常记录分析
        </span>
      ),
      children: <AnomalyAnalysisTab />,
    },
  ];

  return (
    <div>
      {/* 顶部标题栏 */}
      <Card style={{ marginBottom: 16 }} bodyStyle={{ padding: '16px 24px' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
          <AuditOutlined style={{ fontSize: 22, color: '#1677ff' }} />
          <div>
            <Title level={5} style={{ margin: 0 }}>交易数据分析</Title>
            <Text type="secondary" style={{ fontSize: 13 }}>
              基于交易记录的多维度数据分析能力，后续将持续扩展更多分析功能
            </Text>
          </div>
        </div>
      </Card>

      <Tabs
        type="card"
        items={tabItems}
        defaultActiveKey="position"
      />
    </div>
  );
};

export default TradeAnomalyAnalysis;
