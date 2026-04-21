import React from 'react';
import { Tabs, Typography } from 'antd';
import { CloudSyncOutlined } from '@ant-design/icons';
import SyncBatchesTab from './batches/SyncBatchesTab';
import StagedDataTab from './staged/StagedDataTab';

const { Title } = Typography;

/**
 * 同步管理页面
 *
 * 顶层容器，分两个 Tab：
 * - 同步批次：展示 broker_sync_batches 历史批次，支持触发新同步和恢复中断批次
 * - 原始数据：查看各券商同步到 staging 表的原始数据（当前仅 IBKR 实现）
 */
const SyncManagement = () => {
  const items = [
    {
      key: 'batches',
      label: '同步批次',
      children: <SyncBatchesTab />,
    },
    {
      key: 'staged-data',
      label: '原始数据',
      children: <StagedDataTab />,
    },
  ];

  return (
    <div>
      <Title level={4} style={{ marginTop: 0, marginBottom: 16 }}>
        <CloudSyncOutlined style={{ marginRight: 8 }} />
        同步管理
      </Title>
      <Tabs defaultActiveKey="batches" items={items} />
    </div>
  );
};

export default SyncManagement;
