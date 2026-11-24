import { Grid } from 'antd';

export const useIbkrStagedLeadingColumns = () => {
  const { lg } = Grid.useBreakpoint();
  const fixed = lg ? 'left' : undefined;

  return [
    {
      title: '同步账户名称', dataIndex: 'syncAccountName', key: 'syncAccountName',
      width: 160, fixed, ellipsis: true, render: (value) => value || '-',
    },
    {
      title: '同步账户 ID', dataIndex: 'syncAccountId', key: 'syncAccountId',
      width: 140, fixed, ellipsis: true, render: (value) => value || '-',
    },
    { title: 'Trade Date', dataIndex: 'tradeDate', key: 'tradeDate', width: 110, fixed },
    { title: 'Symbol', dataIndex: 'symbol', key: 'symbol', width: 110, fixed },
  ];
};
