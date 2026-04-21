import React from 'react';
import { Tabs, Alert } from 'antd';
import IbkrStagedOrdersTable from './IbkrStagedOrdersTable';
import IbkrStagedConfirmsTable from './IbkrStagedConfirmsTable';

/**
 * IBKR 原始数据面板
 *
 * 内嵌 Tabs 展示两张 staging 表：
 * - Trade Orders：ibkr_staged_orders，参与 trade_records 导入
 * - Trade Confirms：ibkr_staged_trade_confirms，仅用于审计对账，不参与导入
 */
const IbkrStagedPanel = () => {
  const items = [
    {
      key: 'orders',
      label: 'Trade Orders',
      children: <IbkrStagedOrdersTable />,
    },
    {
      key: 'trade-confirms',
      label: 'Trade Confirms',
      children: (
        <>
          <Alert
            type="info"
            showIcon
            style={{ marginBottom: 16 }}
            message="此数据仅用于审计对账，不参与交易记录导入"
          />
          <IbkrStagedConfirmsTable />
        </>
      ),
    },
  ];

  return <Tabs defaultActiveKey="orders" items={items} />;
};

export default IbkrStagedPanel;
