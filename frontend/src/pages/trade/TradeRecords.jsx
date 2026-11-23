import React, { useState, useEffect } from 'react';
import { Card, Table, Button, message, Modal, Form, Input, DatePicker, Select, InputNumber, Row, Col } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import dayjs from 'dayjs';
import { useAmountVisibility } from '../../contexts/AmountVisibilityContext';
import { fetchAllTradeRecords, createTradeRecord, fetchTradeStatistics } from '../../services/tradeRecordApi';
import { fetchBrokerAccounts, accountLabel } from '../../services/brokerAccountApi';
import { fetchAllStrategies } from '../../services/strategyApi';
import { assetTypeMap, tradeTypeMap } from '../../constants/tradeConstants';
import { marketEventErrorMessage } from '../../utils/marketEventError';
import getTradeColumns, { getTradeTableLayout } from './TradeColumns';
import TradeStatisticsPanel from './TradeStatisticsPanel';

// 新增表单中仅展示 BUY / SELL，不再展示旧枚举值
const newTradeTypeOptions = {
  BUY: '买入',
  SELL: '卖出',
};

const TradeRecords = () => {
  const [tradeData, setTradeData] = useState([]);
  const [accountList, setAccountList] = useState([]);
  const [strategyList, setStrategyList] = useState([]);
  const [loading, setLoading] = useState(false);
  const [statistics, setStatistics] = useState(null);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [selectedAsset, setSelectedAsset] = useState(null);
  const [selectedAccountId, setSelectedAccountId] = useState(null);
  const [selectedStrategy, setSelectedStrategy] = useState(null);
  const [selectedTradeType, setSelectedTradeType] = useState('BUY');
  const [pageSize, setPageSize] = useState(10);
  const [form] = Form.useForm();
  const { amountVisible } = useAmountVisibility();

  // 加载统计数据
  const loadStatistics = async () => {
    try {
      const result = await fetchTradeStatistics();
      if (result.status === 'SUCCESS') {
        setStatistics(result.data);
      }
    } catch (error) {
      console.error('Failed to fetch trade statistics:', error);
    }
  };

  // 加载交易记录数据
  const loadTradeRecords = async () => {
    setLoading(true);
    try {
      const result = await fetchAllTradeRecords();
      if (result.status === 'SUCCESS') {
        const list = (result.data || []).map((item) => ({
          ...item,
          key: String(item.id),
        }));
        setTradeData(list);
      } else {
        message.error(result.message || '查询交易记录失败');
      }
    } catch (error) {
      console.error('Failed to fetch trade records:', error);
      message.error('查询交易记录失败，请检查后端服务是否启动');
    } finally {
      setLoading(false);
    }
  };

  // 加载托管账户列表
  const loadAccounts = async () => {
    try {
      const result = await fetchBrokerAccounts();
      if (result.status === 'SUCCESS') {
        setAccountList(result.data || []);
      }
    } catch (error) {
      console.error('Failed to fetch broker accounts:', error);
    }
  };

  // 加载策略列表
  const loadStrategies = async () => {
    try {
      const result = await fetchAllStrategies();
      if (result.status === 'SUCCESS') {
        setStrategyList(result.data || []);
      }
    } catch (error) {
      console.error('Failed to fetch strategy list:', error);
    }
  };

  // 组件加载时获取数据
  useEffect(() => {
    loadTradeRecords();
    loadAccounts();
    loadStrategies();
    loadStatistics();
  }, []);

  const tradeColumns = getTradeColumns(amountVisible);

  const handleAddRecord = () => {
    setIsModalVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
    form.resetFields();
    setSelectedTradeType('BUY');
  };

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields();

      // 期权证券代码格式校验
      if (values.assetType === 'OPTION_CALL' || values.assetType === 'OPTION_PUT') {
        const optionFlag = values.assetType === 'OPTION_CALL' ? 'C' : 'P';
        const optionLabel = values.assetType === 'OPTION_CALL' ? 'CALL期权' : 'PUT期权';
        const underlying = values.underlyingSymbol?.trim();
        const symbol = values.symbol?.trim();
        const expectedPattern = new RegExp(`^${underlying.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')}-(\\d{8})-${optionFlag}[\\d.]+$`);

        if (!expectedPattern.test(symbol)) {
          message.error(`${optionLabel}的证券代码格式不正确，应为：${underlying}-YYYYMMDD-${optionFlag}价格，例如：${underlying}-20250321-${optionFlag}150`);
          return;
        }

        const dateStr = symbol.match(new RegExp(`^${underlying.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')}-(\\d{8})-${optionFlag}`))[1];
        const dateObj = dayjs(dateStr, 'YYYYMMDD', true);
        if (!dateObj.isValid()) {
          message.error(`证券代码中的日期部分 ${dateStr} 不是合法日期，请检查`);
          return;
        }
      }

      setSubmitting(true);

      const isOption = values.assetType === 'OPTION_CALL' || values.assetType === 'OPTION_PUT';
      const multiplier = isOption ? 100 : 1;
      const price = values.price;
      const fee = values.fee;
      const payload = {
        tradeDate: values.date.format('YYYY-MM-DD'),
        brokerAccountId: values.brokerAccountId,
        assetType: values.assetType,
        symbol: values.symbol,
        underlyingSymbol: values.underlyingSymbol,
        tradeType: values.tradeType,
        quantity: values.quantity,
        price: price,
        amount: values.quantity * price * multiplier,
        fee: fee,
        currency: values.currency,
        strategyId: values.strategyId || null,
        tradeTrigger: 'MANUAL',
      };

      const result = await createTradeRecord(payload);
      if (result.status === 'SUCCESS') {
        message.success('交易记录添加成功！');
        setIsModalVisible(false);
        form.resetFields();
        setSelectedTradeType('BUY');
        loadTradeRecords();
        loadStatistics();
      } else {
        message.error(result.message || '新增交易记录失败');
      }
    } catch (error) {
      if (error.errorFields) {
        console.error('Form validation failed:', error);
      } else {
        console.error('Failed to create trade record:', error);
        const errorMsg = marketEventErrorMessage(error, '新增交易记录失败，请稍后重试');
        message.error(errorMsg);
      }
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      {/* 统计数据区域 */}
      <TradeStatisticsPanel statistics={statistics} amountVisible={amountVisible} />

    <Card
      title="交易记录"
      extra={<Button type="primary" icon={<PlusOutlined />} onClick={handleAddRecord}>新增记录</Button>}
    >
      <Row gutter={[16, 12]} style={{ marginBottom: 16 }}>
        <Col xs={24} md={8}>
          <div style={{ marginBottom: 8 }}>托管账户</div>
          <Select aria-label="托管账户筛选" allowClear placeholder="全部账户" style={{ width: '100%' }} value={selectedAccountId}
            onChange={setSelectedAccountId} options={accountList.map(account => ({ value: account.id, label: accountLabel(account) }))} />
        </Col>
        <Col xs={24} md={8}>
          <div style={{ marginBottom: 8 }}>所属策略</div>
          <Select
            aria-label="所属策略筛选"
            allowClear
            placeholder="全部策略"
            style={{ width: '100%' }}
            value={selectedStrategy}
            onChange={(value) => setSelectedStrategy(value || null)}
          >
            {strategyList.map((s) => (
              <Select.Option key={s.id} value={s.id}>{s.strategyName}</Select.Option>
            ))}
          </Select>
        </Col>
        <Col xs={24} md={8}>
          <div style={{ marginBottom: 8 }}>底层证券</div>
          <Input
            aria-label="底层证券筛选"
            allowClear
            placeholder="输入证券代码，如 AAPL"
            style={{ width: '100%' }}
            value={selectedAsset}
            onChange={(e) => setSelectedAsset(e.target.value || null)}
          />
        </Col>
      </Row>
      <Table
        {...getTradeTableLayout(tradeColumns)}
        columns={tradeColumns}
        dataSource={tradeData.filter((item) => {
          if (selectedAccountId && item.brokerAccountId !== selectedAccountId) return false;
          if (selectedStrategy && item.strategyId !== selectedStrategy) return false;
          if (selectedAsset) {
            const underlying = (item.underlyingSymbol || item.symbol || '').toUpperCase();
            if (!underlying.includes(selectedAsset.toUpperCase())) return false;
          }
          return true;
        })}
        loading={loading}
        rowKey="id"
        pagination={{
          pageSize,
          showSizeChanger: true,
          pageSizeOptions: ['10', '20', '50', '100'],
          onShowSizeChange: (_, size) => setPageSize(size),
        }}
      />

      <Modal
        title="新增交易记录"
        open={isModalVisible}
        onCancel={handleCancel}
        footer={[
          <Button key="cancel" onClick={handleCancel}>
            取消
          </Button>,
          <Button key="submit" type="primary" loading={submitting} onClick={handleSubmit}>
            提交
          </Button>,
        ]}
        width={700}
        destroyOnClose
      >
        <Form
          form={form}
          layout="vertical"
          initialValues={{
            currency: 'USD',
            tradeType: 'BUY',
            assetType: 'STOCK',
          }}
          style={{ marginTop: 20 }}
        >
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                label="交易日期"
                name="date"
                rules={[{ required: true, message: '请选择交易日期' }]}
              >
                <DatePicker style={{ width: '100%' }} placeholder="选择日期" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                label="托管账户"
                name="brokerAccountId"
                rules={[{ required: true, message: '请选择托管账户' }]}
              >
                <Select placeholder="请选择托管账户">
                  {accountList.map((b) => (
                    <Select.Option key={b.id} value={b.id}>{accountLabel(b)}</Select.Option>
                  ))}
                </Select>
              </Form.Item>
            </Col>
          </Row>

          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                label="证券类型"
                name="assetType"
                rules={[{ required: true, message: '请选择证券类型' }]}
              >
                <Select>
                  {Object.entries(assetTypeMap).map(([value, label]) => (
                    <Select.Option key={value} value={value}>{label}</Select.Option>
                  ))}
                </Select>
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                label="交易类型"
                name="tradeType"
                rules={[{ required: true, message: '请选择交易类型' }]}
              >
                <Select
                  onChange={(value) => {
                    setSelectedTradeType(value);
                  }}
                >
                  {Object.entries(newTradeTypeOptions).map(([value, label]) => (
                    <Select.Option key={value} value={value}>{label}</Select.Option>
                  ))}
                </Select>
              </Form.Item>
            </Col>
          </Row>

          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                label="证券代码"
                name="symbol"
                rules={[{ required: true, message: '请输入证券代码' }]}
              >
                <Input placeholder="例如：AAPL 或 600519" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                label="底层证券代码"
                name="underlyingSymbol"
                rules={[{ required: true, message: '请输入底层证券代码' }]}
                extra="用于关联分析期权与正股收益，例如：TSLA、AAPL"
              >
                <Input placeholder="例如：TSLA、AAPL" />
              </Form.Item>
            </Col>
          </Row>

          <Row gutter={16}>
            <Col span={8}>
              <Form.Item
                label="数量"
                name="quantity"
                rules={[
                  { required: true, message: '请输入交易数量' },
                  { type: 'number', min: 1, message: '数量必须大于0' },
                ]}
              >
                <InputNumber style={{ width: '100%' }} placeholder="请输入数量" min={1} />
              </Form.Item>
            </Col>
                <Col span={8}>
                  <Form.Item
                    label="成交价格"
                    name="price"
                    rules={[
                      { required: true, message: '请输入成交价格' },
                      { type: 'number', min: 0, message: '价格不能为负' },
                    ]}
                  >
                    <InputNumber style={{ width: '100%' }} placeholder="请输入价格" min={0} precision={4} />
                  </Form.Item>
                </Col>
                <Col span={8}>
                  <Form.Item
                    label="交易费用"
                    name="fee"
                    rules={[
                      { required: true, message: '请输入交易费用' },
                    ]}
                  >
                    <InputNumber stringMode style={{ width: '100%' }} placeholder="请输入费用" min="0" step="0.01" />
                  </Form.Item>
                </Col>
          </Row>

          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                label="币种"
                name="currency"
                rules={[{ required: true, message: '请选择币种' }]}
              >
                <Select>
                  <Select.Option value="CNY">CNY - 人民币</Select.Option>
                  <Select.Option value="USD">USD - 美元</Select.Option>
                  <Select.Option value="HKD">HKD - 港币</Select.Option>
                </Select>
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                label="所属策略"
                name="strategyId"
              >
                <Select allowClear placeholder="请选择策略（选填）">
                  {strategyList.map((s) => (
                    <Select.Option key={s.id} value={s.id}>{s.strategyName}</Select.Option>
                  ))}
                </Select>
              </Form.Item>
            </Col>
          </Row>
        </Form>
      </Modal>


    </Card>
    </div>
  );
};

export default TradeRecords;
