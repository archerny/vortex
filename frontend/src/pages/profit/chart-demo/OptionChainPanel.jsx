import React, { useMemo, useRef, useState } from 'react';
import { Button, Descriptions, Segmented, Select, Space, Switch, Table, Tag } from 'antd';
import { AimOutlined } from '@ant-design/icons';
import { EXPIRIES, expiryDate, makeOptionChain, spotPrice } from './mockData';

export default function OptionChainPanel({ symbol }) {
  const [days, setDays] = useState(21);
  const [count, setCount] = useState(121);
  const [range, setRange] = useState('all');
  const [showGreeks, setShowGreeks] = useState(false);
  const [selectedStrike, setSelectedStrike] = useState(null);
  const [side, setSide] = useState('call');
  const table = useRef(null);
  const spot = spotPrice(symbol);
  const chain = useMemo(() => makeOptionChain(symbol, days, count), [symbol, days, count]);
  const visibleRows = useMemo(() => chain.filter((row) => range === 'all' || Math.abs(row.strike / spot - 1) <= 0.1), [chain, range, spot]);
  const atm = visibleRows.reduce((a, b) => Math.abs(a.strike - spot) < Math.abs(b.strike - spot) ? a : b);
  const selected = visibleRows.find((row) => row.strike === selectedStrike) || atm;
  const quote = selected[side];
  const columns = useMemo(() => {
    const sideColumns = (type) => {
      const numberColumn = (title, field, width, decimals = 2) => ({
        title, dataIndex: [type, field], key: `${type}-${field}`, width, align: 'right',
        sorter: (a, b) => a[type][field] - b[type][field],
        render: (value) => <span className="demo-number">{decimals === 0 ? value.toLocaleString('en-US') : value.toFixed(decimals)}{field === 'iv' ? '%' : ''}</span>,
        onCell: (record) => ({ className: (type === 'call' ? record.strike < spot : record.strike > spot) ? 'demo-itm-cell' : '' }),
      });
      const values = [numberColumn('买价', 'bid', 64), numberColumn('卖价', 'ask', 64), numberColumn('IV', 'iv', 68), numberColumn('Delta', 'delta', 68, 3)];
      if (showGreeks) values.push(numberColumn('Gamma', 'gamma', 78, 4), numberColumn('Theta', 'theta', 78, 3), numberColumn('Vega', 'vega', 78, 3));
      values.push(numberColumn('成交量', 'volume', 68, 0), {
        title: '未平仓', dataIndex: [type, 'openInterest'], key: `${type}-oi`, width: 76, align: 'right',
        sorter: (a, b) => a[type].openInterest - b[type].openInterest,
        render: (value) => <span className="demo-oi-cell"><i style={{ width: `${Math.min(100, value / 200)}%` }} /><span>{value.toLocaleString('en-US')}</span></span>,
      });
      return { title: <span className={`demo-side-title demo-${type}`}>{type === 'call' ? 'CALL · 看涨期权' : 'PUT · 看跌期权'}</span>, children: values };
    };
    return [sideColumns('call'), {
      title: '行权价 ($)', key: 'strike', dataIndex: 'strike', width: 94, align: 'center',
      sorter: (a, b) => a.strike - b.strike,
      render: (value) => <strong className="demo-strike">{value.toFixed(2)}{value === atm.strike && <small>ATM</small>}</strong>,
      onCell: () => ({ className: 'demo-strike-cell' }),
    }, sideColumns('put')];
  }, [spot, showGreeks, atm.strike]);
  const focusAtm = () => {
    setSelectedStrike(atm.strike);
    table.current?.scrollTo({ key: atm.key });
  };

  return <section>
    <div className="demo-panel-heading"><div><h2>一张期权链，串起报价与风险</h2><p>切换到期日、排序报价或选择一行，查看合成合约的详细指标。</p></div><Tag>Ant Design Table</Tag></div>
    <div className="demo-chain-controls">
      <Space wrap size="middle">
        <div className="demo-field"><label htmlFor="demo-expiry">到期日</label><Select id="demo-expiry" aria-label="期权到期日" value={days} onChange={setDays} options={EXPIRIES.map((value) => ({ value, label: `${expiryDate(value)} · ${value} 天` }))} style={{ width: 205 }} /></div>
        <div className="demo-field"><label htmlFor="demo-row-count">链规模</label><Select id="demo-row-count" aria-label="期权链规模" value={count} onChange={setCount} options={[41, 121, 401].map((value) => ({ value, label: `${value} 档行权价` }))} style={{ width: 145 }} /></div>
        <div className="demo-field"><span>行权价范围</span><Segmented aria-label="行权价范围" value={range} onChange={setRange} options={[{ label: '全部', value: 'all' }, { label: '近平值 ±10%', value: 'near' }]} /></div>
      </Space>
      <div className="demo-chain-price"><span>{symbol} 模拟现价</span><strong>${spot.toFixed(2)}</strong></div>
    </div>
    <div className="demo-chart-card demo-chain-card">
      <div className="demo-toolbar"><Space wrap><Tag color="blue">{visibleRows.length} 档 / {visibleRows.length * 2} 份合约</Tag><span className="demo-switch-label"><Switch size="small" checked={showGreeks} onChange={setShowGreeks} aria-label="显示更多 Greeks" /> 更多 Greeks</span><span className="demo-table-legend"><i />实值区域</span></Space><Button icon={<AimOutlined />} onClick={focusAtm}>定位平值</Button></div>
      <Table
        ref={table}
        aria-label="合成期权链"
        className="demo-chain-table"
        columns={columns}
        dataSource={visibleRows}
        rowKey="key"
        virtual
        size="small"
        pagination={false}
        scroll={{ x: showGreeks ? 1414 : 946, y: 420 }}
        rowSelection={{ type: 'radio', selectedRowKeys: [selected.key], onChange: (_, rows) => setSelectedStrike(rows[0].strike), columnWidth: 36, getCheckboxProps: (row) => ({ 'aria-label': `选择行权价 ${row.strike.toFixed(2)}` }) }}
        onRow={(row) => ({ onClick: () => setSelectedStrike(row.strike) })}
      />
      <div className="demo-chart-caption">点击表头排序 · 横向滚动查看更多报价 · 纵向虚拟滚动 · 选择任意行查看下方明细</div>
    </div>
    <div className="demo-chart-card demo-contract-detail">
      <div className="demo-card-heading"><div><h3>所选合约 <Tag color="blue">{side.toUpperCase()}</Tag></h3><p>{symbol} / {expiryDate(days)} / 行权价 ${selected.strike.toFixed(2)}</p></div><Segmented aria-label="合约方向" value={side} onChange={setSide} options={[{ label: 'CALL', value: 'call' }, { label: 'PUT', value: 'put' }]} /></div>
      <Descriptions size="small" column={{ xs: 2, sm: 3, lg: 5 }} items={[
        { key: 'bid', label: '买价', children: `$${quote.bid.toFixed(2)}` },
        { key: 'ask', label: '卖价', children: `$${quote.ask.toFixed(2)}` },
        { key: 'iv', label: 'IV', children: `${quote.iv.toFixed(2)}%` },
        { key: 'delta', label: 'Delta', children: quote.delta.toFixed(3) },
        { key: 'gamma', label: 'Gamma', children: quote.gamma.toFixed(4) },
        { key: 'theta', label: 'Theta / 天', children: quote.theta.toFixed(3) },
        { key: 'vega', label: 'Vega', children: quote.vega.toFixed(3) },
        { key: 'volume', label: '成交量', children: quote.volume.toLocaleString('en-US') },
        { key: 'oi', label: '未平仓', children: quote.openInterest.toLocaleString('en-US') },
        { key: 'multiplier', label: '合约乘数', children: '100' },
      ]} />
    </div>
  </section>;
}
