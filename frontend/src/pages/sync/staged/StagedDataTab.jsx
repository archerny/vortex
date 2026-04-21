import React, { useState, useEffect } from 'react';
import { Select, Space, Typography, Card, Empty, message } from 'antd';
import { fetchAllBrokers } from '../../../services/brokerApi';
import IbkrStagedPanel from './ibkr/IbkrStagedPanel';

const { Text } = Typography;

/**
 * 原始数据 Tab
 *
 * 用户选择一个券商后，展示该券商的原始同步数据面板。
 * 券商下拉选项来源：/api/brokers（券商管理录入的券商），遵循
 * "券商管理是券商选项的单一数据源" 原则。
 *
 * 当前仅 IBKR 实现了原始数据面板（IBKR 有 staging 表）。其他券商
 * （如 Tiger/Futu/Schwab）尚未实现 staging，选中后展示 Empty 提示。
 */
const StagedDataTab = () => {
  const [brokerOptions, setBrokerOptions] = useState([]);
  const [loadingBrokers, setLoadingBrokers] = useState(false);
  const [selectedBrokerCode, setSelectedBrokerCode] = useState(undefined);

  useEffect(() => {
    const loadBrokers = async () => {
      setLoadingBrokers(true);
      try {
        const result = await fetchAllBrokers();
        const brokers = (result.data || [])
          .filter((b) => b.brokerCode) // 只展示有 brokerCode 的券商
          .map((b) => ({
            label: b.brokerName || b.brokerCode.toUpperCase(),
            value: b.brokerCode,
          }));
        setBrokerOptions(brokers);
      } catch (error) {
        console.error('Failed to load brokers:', error);
        message.error('加载券商列表失败');
      } finally {
        setLoadingBrokers(false);
      }
    };
    loadBrokers();
  }, []);

  // 渲染选中券商的原始数据面板
  const renderBrokerPanel = () => {
    if (!selectedBrokerCode) {
      return (
        <Empty
          description="请选择券商以查看原始同步数据"
          style={{ padding: '48px 0' }}
        />
      );
    }

    // IBKR 专属面板
    if (selectedBrokerCode === 'ibkr') {
      return <IbkrStagedPanel />;
    }

    // 其他券商暂未实现
    return (
      <Empty
        description={`${selectedBrokerCode.toUpperCase()} 暂未实现原始数据查看`}
        style={{ padding: '48px 0' }}
      />
    );
  };

  return (
    <div>
      <Card size="small" style={{ marginBottom: 16 }}>
        <Space>
          <Text>券商：</Text>
          <Select
            allowClear
            placeholder="请选择券商"
            style={{ width: 200 }}
            value={selectedBrokerCode}
            onChange={setSelectedBrokerCode}
            options={brokerOptions}
            loading={loadingBrokers}
            notFoundContent={
              loadingBrokers ? '加载中...' : '请先在券商管理中录入券商'
            }
          />
        </Space>
      </Card>

      {renderBrokerPanel()}
    </div>
  );
};

export default StagedDataTab;
