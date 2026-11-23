import React from 'react';
import { Tag } from 'antd';
import { assetTypeMap, tradeTypeMap, assetTypeColorMap, tradeTypeColorMap, amountColorMap } from '../../constants/tradeConstants';
import { feeText, compareFees } from '../../utils/tradeFee';
import './TradeTable.css';

export const getTradeTableLayout = (columns) => ({
  className: 'trade-record-table',
  size: 'middle',
  tableLayout: 'fixed',
  scroll: { x: columns.reduce((width, column) => width + column.width, 0) },
  style: { overflowWrap: 'anywhere' },
});

/**
 * 交易记录表格列定义
 * @param {boolean} amountVisible - 是否显示金额
 */
const getTradeColumns = (amountVisible) => [
  {
    title: '账户 ID',
    key: 'accountId',
    width: 96,
    ellipsis: true,
    render: (_, record) => record.brokerAccount?.accountId || '待补全',
  },
  {
    title: '日期',
    dataIndex: 'tradeDate',
    key: 'tradeDate',
    sorter: (a, b) => new Date(a.tradeDate) - new Date(b.tradeDate),
    width: 94,
  },
  {
    title: '类型',
    dataIndex: 'assetType',
    key: 'assetType',
    render: (assetType) => {
      const label = assetTypeMap[assetType] || assetType;
      return <Tag style={{ marginInlineEnd: 0 }} color={assetTypeColorMap[assetType] || 'default'}>{label}</Tag>;
    },
    filters: Object.entries(assetTypeMap).map(([value, text]) => ({ text, value })),
    onFilter: (value, record) => record.assetType === value,
    width: 80,
  },
  {
    title: '代码',
    dataIndex: 'symbol',
    key: 'symbol',
    width: 144,
    ellipsis: true,
  },
  {
    title: '底层证券',
    dataIndex: 'underlyingSymbol',
    key: 'underlyingSymbol',
    render: (underlyingSymbol, record) => underlyingSymbol || record.symbol,
    width: 68,
    ellipsis: true,
  },
  {
    title: '方向',
    dataIndex: 'tradeType',
    key: 'tradeType',
    render: (tradeType) => {
      const label = tradeTypeMap[tradeType] || tradeType;
      return <Tag style={{ marginInlineEnd: 0 }} color={tradeTypeColorMap[tradeType] || 'default'}>{label}</Tag>;
    },
    filters: Object.entries(tradeTypeMap).map(([value, text]) => ({ text, value })),
    onFilter: (value, record) => record.tradeType === value,
    width: 55,
  },
  {
    title: '数量',
    dataIndex: 'quantity',
    key: 'quantity',
    render: (quantity) => amountVisible ? (quantity != null ? quantity.toLocaleString() : '-') : '****',
    sorter: (a, b) => a.quantity - b.quantity,
    width: 55,
  },
  {
    title: '价格',
    dataIndex: 'price',
    key: 'price',
    render: (price) => amountVisible ? (price != null ? Number(price).toFixed(2) : '-') : '****',
    sorter: (a, b) => a.price - b.price,
    width: 55,
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
    width: 78,
  },
  {
    title: '费用',
    dataIndex: 'fee',
    key: 'fee',
    render: (_, record) => amountVisible ? (feeText(record) ?? '-') : '****',
    sorter: compareFees,
    width: 86,
  },
  {
    title: '币种',
    dataIndex: 'currency',
    key: 'currency',
    render: (currency) => (
      <Tag style={{ marginInlineEnd: 0 }} color={currency === 'CNY' ? 'blue' : currency === 'HKD' ? 'green' : 'purple'}>{currency}</Tag>
    ),
    filters: [
      { text: 'CNY', value: 'CNY' },
      { text: 'USD', value: 'USD' },
      { text: 'HKD', value: 'HKD' },
    ],
    onFilter: (value, record) => record.currency === value,
    width: 59,
  },
  {
    title: '操作',
    key: 'action',
    width: 44,
    render: (_, record) => (
      <a href={`#/trade-detail/${record.id}`}>详情</a>
    ),
  },
];

export default getTradeColumns;
