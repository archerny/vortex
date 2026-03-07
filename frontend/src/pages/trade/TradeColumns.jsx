import React from 'react';
import { Tag } from 'antd';
import { assetTypeMap, tradeTypeMap, assetTypeColorMap, tradeTypeColorMap, amountColorMap } from '../../constants/tradeConstants';

/**
 * 交易记录表格列定义
 * @param {boolean} amountVisible - 是否显示金额
 * @param {object} brokerMap - 券商ID→名称映射
 * @param {object} strategyMap - 策略ID→名称映射
 * @param {function} onViewDetail - 查看详情回调
 */
const getTradeColumns = (amountVisible, brokerMap, strategyMap, onViewDetail) => [
  {
    title: '日期',
    dataIndex: 'tradeDate',
    key: 'tradeDate',
    sorter: (a, b) => new Date(a.tradeDate) - new Date(b.tradeDate),
    width: 110,
  },
  {
    title: '类型',
    dataIndex: 'assetType',
    key: 'assetType',
    render: (assetType) => {
      const label = assetTypeMap[assetType] || assetType;
      return <Tag color={assetTypeColorMap[assetType] || 'default'}>{label}</Tag>;
    },
    filters: Object.entries(assetTypeMap).map(([value, text]) => ({ text, value })),
    onFilter: (value, record) => record.assetType === value,
    width: 90,
  },
  {
    title: '代码',
    dataIndex: 'symbol',
    key: 'symbol',
    ellipsis: true,
    width: 160,
  },
  {
    title: '底层证券',
    dataIndex: 'name',
    key: 'name',
    render: (name, record) => {
      const code = record.underlyingSymbol || record.symbol;
      const displayName = name ? `${code}(${name})` : code;
      return <span title={displayName} style={{ whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis', display: 'block' }}>{displayName}</span>;
    },
    width: 140,
    ellipsis: true,
  },
  {
    title: '方向',
    dataIndex: 'tradeType',
    key: 'tradeType',
    render: (tradeType) => {
      const label = tradeTypeMap[tradeType] || tradeType;
      return <Tag color={tradeTypeColorMap[tradeType] || 'default'}>{label}</Tag>;
    },
    filters: Object.entries(tradeTypeMap).map(([value, text]) => ({ text, value })),
    onFilter: (value, record) => record.tradeType === value,
    width: 90,
  },
  {
    title: '数量',
    dataIndex: 'quantity',
    key: 'quantity',
    render: (quantity) => amountVisible ? (quantity != null ? quantity.toLocaleString() : '-') : '****',
    sorter: (a, b) => a.quantity - b.quantity,
    width: 80,
  },
  {
    title: '价格',
    dataIndex: 'price',
    key: 'price',
    render: (price) => amountVisible ? (price != null ? Number(price).toFixed(2) : '-') : '****',
    sorter: (a, b) => a.price - b.price,
    width: 90,
  },
  {
    title: '金额',
    dataIndex: 'amount',
    key: 'amount',
    render: (amount, record) => {
      if (!amountVisible) return <span style={{ fontWeight: 'bold', color: '#999' }}>****</span>;
      return (
        <span style={{
          color: amountColorMap[record.tradeType] || '#000000',
          fontWeight: 'bold'
        }}>
          {amount != null ? Number(amount).toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 }) : '-'}
        </span>
      );
    },
    sorter: (a, b) => a.amount - b.amount,
    width: 120,
  },
  {
    title: '费用',
    dataIndex: 'fee',
    key: 'fee',
    render: (fee) => amountVisible ? (fee != null ? Number(fee).toFixed(2) : '-') : '****',
    sorter: (a, b) => a.fee - b.fee,
    width: 80,
  },
  {
    title: '币种',
    dataIndex: 'currency',
    key: 'currency',
    render: (currency) => (
      <Tag color={currency === 'CNY' ? 'blue' : currency === 'HKD' ? 'green' : 'purple'}>{currency}</Tag>
    ),
    filters: [
      { text: 'CNY', value: 'CNY' },
      { text: 'USD', value: 'USD' },
      { text: 'HKD', value: 'HKD' },
    ],
    onFilter: (value, record) => record.currency === value,
    width: 80,
  },
  {
    title: '操作',
    key: 'action',
    width: 60,
    render: (_, record) => (
      <a onClick={() => onViewDetail(record)}>详情</a>
    ),
  },
];

export default getTradeColumns;
