import React, { useState } from 'react';
import { Tabs, Typography } from 'antd';
import { CloudSyncOutlined } from '@ant-design/icons';
import SyncBatchesTab from './batches/SyncBatchesTab';
import StagedDataTab from './staged/StagedDataTab';
import SyncConnectionSettingsTab from './settings/SyncConnectionSettingsTab';

const { Title } = Typography;

/**
 * 同步管理页面
 *
 * 顶层容器，分三个 Tab：
 * - 同步批次：展示历史批次，支持触发新同步
 * - 同步配置：为已有托管账户维护券商连接
 * - 原始数据：查看各券商同步到 staging 表的原始数据（当前仅 IBKR 实现）
 */
const SyncManagement = () => {
  const [activeKey, setActiveKey] = useState('batches');
  const [refreshKey, setRefreshKey] = useState(0);
  const items = [
    {
      key: 'batches',
      label: '同步批次',
      children: <SyncBatchesTab refreshKey={refreshKey} onConfigure={() => setActiveKey('settings')} />,
    },
    {
      key: 'settings',
      label: '同步配置',
      children: <SyncConnectionSettingsTab onSaved={() => setRefreshKey((value) => value + 1)} onDone={() => setActiveKey('batches')} />,
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
      <Tabs activeKey={activeKey} onChange={setActiveKey} items={items} />
    </div>
  );
};

export default SyncManagement;
