import React, { useState, useEffect, useCallback } from 'react';
import { Table, Space, Typography, Card, Button, InputNumber, Tooltip, message } from 'antd';
import { ReloadOutlined, SearchOutlined } from '@ant-design/icons';
import dayjs from 'dayjs';
import { fetchIbkrStagedTradeConfirms } from '../../../../services/syncStagedDataApi';
import { useIbkrStagedLeadingColumns } from './useIbkrStagedLeadingColumns';

const { Text } = Typography;

/**
 * IBKR Staged Trade Confirms 表格
 *
 * 显示同步账户和 ibkr_staged_trade_confirms 的字段。
 */
const IbkrStagedConfirmsTable = () => {
  const [confirms, setConfirms] = useState([]);
  const [loading, setLoading] = useState(false);
  const [batchIdInput, setBatchIdInput] = useState(null);
  const [appliedBatchId, setAppliedBatchId] = useState(null);
  const leadingColumns = useIbkrStagedLeadingColumns();

  const loadConfirms = useCallback(async (batchId) => {
    setLoading(true);
    try {
      const params = {};
      if (batchId !== null && batchId !== undefined) {
        params.batchId = batchId;
      }
      const result = await fetchIbkrStagedTradeConfirms(params);
      setConfirms(result.data || []);
      if (result.accountInfoUnavailable) message.warning('无法加载同步账户信息');
    } catch (error) {
      console.error('Failed to load IBKR staged trade confirms:', error);
      message.error('加载 IBKR 原始成交确认失败');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    loadConfirms(appliedBatchId);
  }, [loadConfirms, appliedBatchId]);

  const handleQuery = () => {
    setAppliedBatchId(batchIdInput);
  };

  const handleReset = () => {
    setBatchIdInput(null);
    setAppliedBatchId(null);
  };

  const handleRefresh = () => {
    loadConfirms(appliedBatchId);
  };

  const formatTime = (time) => {
    if (!time) return '-';
    return dayjs(time).format('YYYY-MM-DD HH:mm:ss');
  };

  const columns = [
    ...leadingColumns,
    // ============ Management fields ============
    { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
    { title: 'Batch ID', dataIndex: 'batchId', key: 'batchId', width: 90 },
    // ============ IBKR TradeConfirm business fields (37) ============
    { title: 'IBKR Account ID', dataIndex: 'accountId', key: 'accountId', width: 140 },
    { title: 'IBKR Acct Alias', dataIndex: 'acctAlias', key: 'acctAlias', width: 140 },
    { title: 'Currency', dataIndex: 'currency', key: 'currency', width: 90 },
    { title: 'Asset Category', dataIndex: 'assetCategory', key: 'assetCategory', width: 120 },
    {
      title: 'Description',
      dataIndex: 'description',
      key: 'description',
      width: 200,
      ellipsis: true,
      render: (v) => (v ? <Tooltip title={v}>{v}</Tooltip> : '-'),
    },
    { title: 'Conid', dataIndex: 'conid', key: 'conid', width: 110 },
    { title: 'Security ID', dataIndex: 'securityId', key: 'securityId', width: 140 },
    { title: 'Security ID Type', dataIndex: 'securityIdType', key: 'securityIdType', width: 130 },
    { title: 'Multiplier', dataIndex: 'multiplier', key: 'multiplier', width: 100 },
    { title: 'Strike', dataIndex: 'strike', key: 'strike', width: 100 },
    { title: 'Expiry', dataIndex: 'expiry', key: 'expiry', width: 110 },
    { title: 'Put/Call', dataIndex: 'putCall', key: 'putCall', width: 90 },
    { title: 'Transaction Type', dataIndex: 'transactionType', key: 'transactionType', width: 140 },
    { title: 'Trade ID', dataIndex: 'tradeId', key: 'tradeId', width: 140 },
    { title: 'Order ID', dataIndex: 'orderId', key: 'orderId', width: 140 },
    { title: 'Exec ID', dataIndex: 'execId', key: 'execId', width: 140 },
    { title: 'Brokerage Order ID', dataIndex: 'brokerageOrderId', key: 'brokerageOrderId', width: 160 },
    { title: 'Order Reference', dataIndex: 'orderReference', key: 'orderReference', width: 140 },
    { title: 'Order Time', dataIndex: 'orderTime', key: 'orderTime', width: 170 },
    { title: 'Date Time', dataIndex: 'dateTime', key: 'dateTime', width: 170 },
    { title: 'Settle Date', dataIndex: 'settleDate', key: 'settleDate', width: 110 },
    { title: 'Exchange', dataIndex: 'exchange', key: 'exchange', width: 110 },
    { title: 'Buy/Sell', dataIndex: 'buySell', key: 'buySell', width: 90 },
    { title: 'Quantity', dataIndex: 'quantity', key: 'quantity', width: 100, align: 'right' },
    { title: 'Price', dataIndex: 'price', key: 'price', width: 110, align: 'right' },
    { title: 'Amount', dataIndex: 'amount', key: 'amount', width: 120, align: 'right' },
    { title: 'Proceeds', dataIndex: 'proceeds', key: 'proceeds', width: 120, align: 'right' },
    { title: 'Net Cash', dataIndex: 'netCash', key: 'netCash', width: 120, align: 'right' },
    { title: 'Commission', dataIndex: 'commission', key: 'commission', width: 110, align: 'right' },
    { title: 'Commission Currency', dataIndex: 'commissionCurrency', key: 'commissionCurrency', width: 150 },
    { title: 'Trade Charge', dataIndex: 'tradeCharge', key: 'tradeCharge', width: 120, align: 'right' },
    { title: 'Code', dataIndex: 'code', key: 'code', width: 100 },
    { title: 'Order Type', dataIndex: 'orderType', key: 'orderType', width: 110 },
    { title: 'Trader ID', dataIndex: 'traderId', key: 'traderId', width: 120 },
    { title: 'Is API Order', dataIndex: 'isApiOrder', key: 'isApiOrder', width: 110 },
    // ============ BaseEntity timestamps ============
    {
      title: 'Created At',
      dataIndex: 'createdAt',
      key: 'createdAt',
      width: 170,
      render: formatTime,
    },
    {
      title: 'Updated At',
      dataIndex: 'updatedAt',
      key: 'updatedAt',
      width: 170,
      render: formatTime,
    },
  ];

  return (
    <div>
      <Card size="small" style={{ marginBottom: 16 }}>
        <Space size="middle">
          <Space>
            <Text>批次 ID：</Text>
            <InputNumber
              placeholder="可选"
              style={{ width: 140 }}
              value={batchIdInput}
              onChange={setBatchIdInput}
              min={1}
              controls={false}
            />
          </Space>
          <Button type="primary" icon={<SearchOutlined />} onClick={handleQuery}>
            查询
          </Button>
          <Button onClick={handleReset}>重置</Button>
          <Button icon={<ReloadOutlined />} onClick={handleRefresh} loading={loading}>
            刷新
          </Button>
        </Space>
      </Card>

      <Table
        columns={columns}
        dataSource={confirms}
        rowKey="id"
        loading={loading}
        pagination={{
          defaultPageSize: 50,
          showSizeChanger: true,
          pageSizeOptions: ['20', '50', '100', '200'],
          showTotal: (total) => `共 ${total} 条`,
        }}
        scroll={{ x: 'max-content' }}
        size="small"
      />
    </div>
  );
};

export default IbkrStagedConfirmsTable;
