import React, { useState, useEffect } from 'react';
import { Card, Descriptions, Tag, Button, Spin, message, Empty, Table, Modal, Form, Input, DatePicker, Select, InputNumber, Row, Col } from 'antd';
import { EditOutlined, DeleteOutlined, ExclamationCircleFilled } from '@ant-design/icons';
import dayjs from 'dayjs';
import { usePageHeader } from '../../contexts/PageHeaderContext';
import { PageHeaderTitle } from '../../components/PageHeader';
import { useAmountVisibility } from '../../contexts/AmountVisibilityContext';
import { useTradeEditable } from '../../contexts/TradeEditableContext';
import { fetchAllTradeRecords, fetchTradeRecordById, updateTradeRecord, deleteTradeRecord } from '../../services/tradeRecordApi';
import { fetchActiveBrokers } from '../../services/brokerApi';
import { fetchAllStrategies } from '../../services/strategyApi';
import {
  assetTypeMap, tradeTypeMap, tradeTypeColorMap, assetTypeColorMap,
  tradeTriggerMap, tradeTriggerColorMap, triggerRefTypeMap, triggerRefTypeColorMap,
  amountColorMap,
} from '../../constants/tradeConstants';
import getTradeColumns from './TradeColumns';

/* 统一 Descriptions 表格列宽的样式 */
const descriptionsTableStyle = `
  .aligned-descriptions .ant-descriptions-view {
    table-layout: fixed;
  }
  .aligned-descriptions .ant-descriptions-view th.ant-descriptions-item-label {
    width: 12%;
  }
  .aligned-descriptions .ant-descriptions-view td.ant-descriptions-item-content {
    width: 21.33%;
  }
`;

/**
 * 交易记录详情独立页面
 * 通过 usePageHeader 将面包屑、标题等配置上报给 AppLayout，
 * 由 AppLayout 在白色内容卡片外部的灰色背景区域统一渲染。
 */
const TradeRecordDetail = ({ recordId, onBack }) => {
  const { amountVisible } = useAmountVisibility();
  const { tradeEditable } = useTradeEditable();
  const { setPageHeader, clearPageHeader } = usePageHeader();
  const [record, setRecord] = useState(null);
  const [loading, setLoading] = useState(true);
  const [brokerMap, setBrokerMap] = useState({});
  const [strategyMap, setStrategyMap] = useState({});
  const [relatedRecords, setRelatedRecords] = useState([]);
  const [brokerList, setBrokerList] = useState([]);
  const [strategyList, setStrategyList] = useState([]);

  // 编辑相关状态
  const [editModalVisible, setEditModalVisible] = useState(false);
  const [editSubmitting, setEditSubmitting] = useState(false);
  const [editForm] = Form.useForm();

  // 面包屑配置
  const breadcrumbs = [
    { label: '交易记录', href: '#/trades' },
    { label: '交易详情' },
  ];

  // 上报面包屑到 AppLayout（灰色背景区域渲染）
  useEffect(() => {
    setPageHeader({ breadcrumbs });
    return () => clearPageHeader();
  }, []);

  useEffect(() => {
    loadData();
  }, [recordId]);

  /**
   * 根据当前交易记录，从所有记录中计算关联交易
   * 1. 期权交易(OPTION_CALL/OPTION_PUT)：查找同一 symbol 的所有交易
   * 2. 股票交易(STOCK) + 触发来源为期权(OPTION)：
   *    先通过 triggerRefId 向后端获取触发源期权交易记录，
   *    再根据该期权的 symbol 从全量数据中查找所有同 symbol 的交易记录
   */
  const computeRelatedRecords = async (currentRecord, allRecords) => {
    if (!currentRecord || !allRecords || allRecords.length === 0) return [];

    const { id, assetType, symbol, tradeTrigger, triggerRefId } = currentRecord;

    // 场景1：期权交易 → 查找同一证券代码(symbol)的所有交易
    if (assetType === 'OPTION_CALL' || assetType === 'OPTION_PUT') {
      if (!symbol) return [];
      return allRecords.filter(
        (r) => r.symbol === symbol
      );
    }

    // 场景2：股票交易 + 触发来源为期权 → 先找到触发源期权，再找同 symbol 的全部交易
    if (assetType === 'STOCK' && tradeTrigger === 'OPTION' && triggerRefId && triggerRefId !== 0) {
      try {
        // 通过 triggerRefId 向后端获取触发源期权交易记录
        const refResult = await fetchTradeRecordById(triggerRefId);
        if (refResult.status === 'SUCCESS' && refResult.data) {
          const optionSymbol = refResult.data.symbol;
          // 根据期权 symbol 从全量数据中查找所有同 symbol 的交易记录
          if (optionSymbol) {
            return allRecords.filter((r) => r.symbol === optionSymbol);
          }
        }
      } catch (error) {
        console.error('获取触发源期权交易失败:', error);
      }
      // 降级：如果请求失败，仍按原逻辑只展示 triggerRefId 对应的记录
      return allRecords.filter((r) => r.id === triggerRefId);
    }

    return [];
  };

  const loadData = async () => {
    setLoading(true);
    try {
      const [recordsResult, brokersResult, strategiesResult] = await Promise.all([
        fetchAllTradeRecords(),
        fetchActiveBrokers(),
        fetchAllStrategies(),
      ]);

      if (brokersResult.status === 'SUCCESS') {
        const brokersData = brokersResult.data || [];
        const map = {};
        brokersData.forEach((b) => { map[b.id] = b.brokerName; });
        setBrokerMap(map);
        setBrokerList(brokersData);
      }

      if (strategiesResult.status === 'SUCCESS') {
        const strategiesData = strategiesResult.data || [];
        const map = {};
        strategiesData.forEach((s) => { map[s.id] = s.strategyName; });
        setStrategyMap(map);
        setStrategyList(strategiesData);
      }

      if (recordsResult.status === 'SUCCESS') {
        const allRecords = recordsResult.data || [];
        const found = allRecords.find((item) => String(item.id) === String(recordId));
        setRecord(found || null);
        if (found) {
          const related = await computeRelatedRecords(found, allRecords);
          setRelatedRecords(related);
        } else {
          setRelatedRecords([]);
          message.warning('未找到该交易记录');
        }
      } else {
        message.error(recordsResult.message || '查询交易记录失败');
      }
    } catch (error) {
      console.error('加载详情失败:', error);
      message.error('加载详情失败，请检查后端服务');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: 300 }}>
        <Spin size="large" tip="加载中..." />
      </div>
    );
  }

  if (!record) {
    return (
      <Card>
        <Empty description="未找到该交易记录">
          <Button type="primary" onClick={() => { window.location.hash = '#/trades'; }}>返回交易记录列表</Button>
        </Empty>
      </Card>
    );
  }

  // 查看关联交易详情 - 跳转到对应的详情页
  const handleViewRelatedDetail = (relatedRecord) => {
    window.location.hash = `#/trade-detail/${relatedRecord.id}`;
  };

  /** 打开编辑弹窗，回填当前记录数据 */
  const handleEdit = () => {
    editForm.setFieldsValue({
      tradeDate: record.tradeDate ? dayjs(record.tradeDate) : null,
      brokerId: record.brokerId,
      assetType: record.assetType,
      symbol: record.symbol,
      name: record.name || '',
      underlyingSymbol: record.underlyingSymbol || '',
      tradeType: record.tradeType,
      quantity: record.quantity,
      price: record.price != null ? Number(record.price) : null,
      fee: record.fee != null ? Number(record.fee) : 0,
      currency: record.currency,
      strategyId: record.strategyId || undefined,
      tradeTrigger: record.tradeTrigger || 'MANUAL',
      triggerRefType: record.triggerRefType || 'NONE',
      triggerRefId: record.triggerRefId || 0,
    });
    setEditModalVisible(true);
  };

  /** 提交编辑 */
  const handleEditSubmit = async () => {
    try {
      const values = await editForm.validateFields();
      setEditSubmitting(true);

      const payload = {
        tradeDate: values.tradeDate.format('YYYY-MM-DD'),
        brokerId: values.brokerId,
        assetType: values.assetType,
        symbol: values.symbol,
        name: values.name || null,
        underlyingSymbol: values.underlyingSymbol,
        tradeType: values.tradeType,
        quantity: values.quantity,
        price: values.price,
        amount: null, // 由后端自动计算
        fee: values.fee || 0,
        currency: values.currency,
        strategyId: values.strategyId || null,
        tradeTrigger: values.tradeTrigger,
        triggerRefType: values.triggerRefType,
        triggerRefId: values.triggerRefId || 0,
      };

      const result = await updateTradeRecord(record.id, payload);
      if (result.status === 'SUCCESS') {
        message.success('交易记录更新成功');
        setEditModalVisible(false);
        loadData(); // 重新加载数据
      } else {
        message.error(result.message || '更新失败');
      }
    } catch (error) {
      if (error.errorFields) {
        console.error('表单验证失败:', error);
      } else {
        console.error('更新交易记录失败:', error);
        const errorMsg = error.response?.data?.message || '更新失败，请稍后重试';
        message.error(errorMsg);
      }
    } finally {
      setEditSubmitting(false);
    }
  };

  /** 删除交易记录 */
  const handleDelete = async () => {
    try {
      const result = await deleteTradeRecord(record.id);
      if (result.status === 'SUCCESS') {
        message.success('交易记录已删除');
        window.location.hash = '#/trades';
      } else {
        message.error(result.message || '删除失败');
      }
    } catch (error) {
      console.error('删除交易记录失败:', error);
      const errorMsg = error.response?.data?.message || '删除失败，请稍后重试';
      message.error(errorMsg);
    }
  };

  // 构建标题栏右侧的标签内容（需在 handleEdit / handleDelete 定义之后）
  const titleExtra = record ? (
    <>
      <Tag color={assetTypeColorMap[record.assetType] || 'default'}>
        {assetTypeMap[record.assetType] || record.assetType}
      </Tag>
      <Tag color={tradeTypeColorMap[record.tradeType] || 'default'}>
        {tradeTypeMap[record.tradeType] || record.tradeType}
      </Tag>
      {tradeEditable && (
        <>
          <Button
            type="primary"
            icon={<EditOutlined />}
            size="small"
            onClick={handleEdit}
            style={{ marginLeft: 8 }}
          >
            编辑
          </Button>
          <Button
            danger
            icon={<DeleteOutlined />}
            size="small"
            onClick={() => {
              Modal.confirm({
                title: '⚠️ 确认删除交易记录',
                icon: <ExclamationCircleFilled style={{ color: '#ff4d4f' }} />,
                content: (
                  <div>
                    <p style={{ marginBottom: 8 }}>此操作<strong style={{ color: '#ff4d4f' }}>不可撤销</strong>，删除后数据将永久丢失。</p>
                    <div style={{ background: '#fff2f0', border: '1px solid #ffccc7', borderRadius: 6, padding: '8px 12px', fontSize: 13 }}>
                      <div>ID：<strong>{record?.id}</strong></div>
                      <div>交易日期：<strong>{record?.tradeDate}</strong></div>
                      <div>证券代码：<strong>{record?.symbol}</strong></div>
                      <div>交易类型：<strong>{tradeTypeMap[record?.tradeType] || record?.tradeType}</strong></div>
                    </div>
                    <p style={{ marginTop: 8, color: '#999', fontSize: 12 }}>请确认以上信息无误后再执行删除。</p>
                  </div>
                ),
                okText: '确认删除',
                okButtonProps: { danger: true },
                cancelText: '取消',
                centered: true,
                onOk: handleDelete,
              });
            }}
          >
            删除
          </Button>
        </>
      )}
    </>
  ) : null;

  // 关联交易表格列（复用 TradeColumns）
  const relatedColumns = getTradeColumns(amountVisible, brokerMap, strategyMap, handleViewRelatedDetail);

  return (
    <Card>
      <style>{descriptionsTableStyle}</style>
      {/* 标题栏：返回箭头 + 标题 + 标签（在白色内容卡片内部渲染） */}
      <PageHeaderTitle
        title="交易记录详情"
        breadcrumbs={breadcrumbs}
        onBack={onBack}
        extra={titleExtra}
      />
      {/* 基本信息 */}
      <Descriptions
        title="基本信息"
        bordered
        column={{ xxl: 3, xl: 3, lg: 3, md: 2, sm: 1, xs: 1 }}
        size="middle"
        className="aligned-descriptions"
        style={{ marginBottom: 24 }}
      >
        <Descriptions.Item label="记录ID">{record.id}</Descriptions.Item>
        <Descriptions.Item label="交易日期">{record.tradeDate}</Descriptions.Item>
        <Descriptions.Item label="券商">
          {brokerMap[record.brokerId] || `ID:${record.brokerId}`}
        </Descriptions.Item>
        <Descriptions.Item label="证券类型">
          <Tag color={assetTypeColorMap[record.assetType] || 'default'}>
            {assetTypeMap[record.assetType] || record.assetType}
          </Tag>
        </Descriptions.Item>
        <Descriptions.Item label="证券代码">{record.symbol}</Descriptions.Item>
        <Descriptions.Item label="底层证券名称">{record.name || '-'}</Descriptions.Item>
        <Descriptions.Item label="底层证券代码">{record.underlyingSymbol}</Descriptions.Item>
        <Descriptions.Item label="交易类型">
          <Tag color={tradeTypeColorMap[record.tradeType] || 'default'}>
            {tradeTypeMap[record.tradeType] || record.tradeType}
          </Tag>
        </Descriptions.Item>
        <Descriptions.Item label="币种">
          <Tag color={record.currency === 'CNY' ? 'blue' : record.currency === 'HKD' ? 'green' : 'purple'}>
            {record.currency}
          </Tag>
        </Descriptions.Item>
      </Descriptions>

      {/* 交易数据 */}
      <Descriptions
        title="交易数据"
        bordered
        column={{ xxl: 3, xl: 3, lg: 3, md: 2, sm: 1, xs: 1 }}
        size="middle"
        className="aligned-descriptions"
        style={{ marginBottom: 24 }}
      >
        <Descriptions.Item label="数量">
          {amountVisible ? (record.quantity != null ? record.quantity.toLocaleString() : '-') : '****'}
        </Descriptions.Item>
        <Descriptions.Item label="成交价格">
          {amountVisible ? (record.price != null ? Number(record.price).toFixed(4) : '-') : '****'}
        </Descriptions.Item>
        <Descriptions.Item label="成交金额">
          {amountVisible
            ? (record.amount != null
              ? Number(record.amount).toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 })
              : '-')
            : '****'}
        </Descriptions.Item>
        <Descriptions.Item label="交易费用">
          {amountVisible ? (record.fee != null ? Number(record.fee).toFixed(2) : '-') : '****'}
        </Descriptions.Item>
      </Descriptions>

      {/* 触发与策略信息 */}
      <Descriptions
        title="触发与策略"
        bordered
        column={{ xxl: 3, xl: 3, lg: 3, md: 2, sm: 1, xs: 1 }}
        size="middle"
        className="aligned-descriptions"
        style={{ marginBottom: relatedRecords.length > 0 ? 24 : 0 }}
      >
        <Descriptions.Item label="所属策略">
          {record.strategyId ? (strategyMap[record.strategyId] || `ID:${record.strategyId}`) : '-'}
        </Descriptions.Item>
        <Descriptions.Item label="触发来源">
          <Tag color={tradeTriggerColorMap[record.tradeTrigger] || 'default'}>
            {tradeTriggerMap[record.tradeTrigger] || record.tradeTrigger || '-'}
          </Tag>
        </Descriptions.Item>
        <Descriptions.Item label="触发关联类型">
          {triggerRefTypeMap[record.triggerRefType] || record.triggerRefType || '-'}
        </Descriptions.Item>
        <Descriptions.Item label="触发关联ID">
          {record.triggerRefId && record.triggerRefId !== 0 ? record.triggerRefId : '-'}
        </Descriptions.Item>
      </Descriptions>

      {/* 相关交易 - 仅在有关联交易时显示 */}
      {relatedRecords.length > 0 && (
        <div>
          <h4 style={{ fontSize: 16, fontWeight: 500, marginBottom: 16 }}>
            相关交易
            <span style={{ fontSize: 13, color: '#999', fontWeight: 'normal', marginLeft: 8 }}>
              {(record.assetType === 'OPTION_CALL' || record.assetType === 'OPTION_PUT')
                ? `证券代码 ${record.symbol} 的全部交易记录`
              : `触发本交易的期权（${relatedRecords.length > 0 ? relatedRecords[0].symbol : ''}）全部交易记录`
              }
            </span>
          </h4>
          <Table
            columns={relatedColumns}
            dataSource={relatedRecords}
            rowKey="id"
            size="middle"
            pagination={relatedRecords.length > 10 ? { pageSize: 10 } : false}
          />
        </div>
      )}

      {/* 编辑交易记录弹窗 */}
      <Modal
        title="编辑交易记录"
        open={editModalVisible}
        onCancel={() => setEditModalVisible(false)}
        footer={[
          <Button key="cancel" onClick={() => setEditModalVisible(false)}>
            取消
          </Button>,
          <Button key="submit" type="primary" loading={editSubmitting} onClick={handleEditSubmit}>
            保存
          </Button>,
        ]}
        width={750}
        destroyOnClose
      >
        <Form
          form={editForm}
          layout="vertical"
          style={{ marginTop: 20 }}
        >
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item label="交易日期" name="tradeDate" rules={[{ required: true, message: '请选择交易日期' }]}>
                <DatePicker style={{ width: '100%' }} placeholder="选择日期" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item label="券商" name="brokerId" rules={[{ required: true, message: '请选择券商' }]}>
                <Select placeholder="请选择券商">
                  {brokerList.map((b) => (
                    <Select.Option key={b.id} value={b.id}>{b.brokerName}</Select.Option>
                  ))}
                </Select>
              </Form.Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col span={8}>
              <Form.Item label="证券类型" name="assetType" rules={[{ required: true, message: '请选择证券类型' }]}>
                <Select>
                  {Object.entries(assetTypeMap).map(([value, label]) => (
                    <Select.Option key={value} value={value}>{label}</Select.Option>
                  ))}
                </Select>
              </Form.Item>
            </Col>
            <Col span={8}>
              <Form.Item label="证券代码" name="symbol" rules={[{ required: true, message: '请输入证券代码' }]}>
                <Input placeholder="如 AAPL、TSLA 240119C210" />
              </Form.Item>
            </Col>
            <Col span={8}>
              <Form.Item label="交易类型" name="tradeType" rules={[{ required: true, message: '请选择交易类型' }]}>
                <Select>
                  {Object.entries(tradeTypeMap).map(([value, label]) => (
                    <Select.Option key={value} value={value}>{label}</Select.Option>
                  ))}
                </Select>
              </Form.Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item label="底层证券名称" name="name">
                <Input placeholder="如 苹果公司、特斯拉" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item label="底层证券代码" name="underlyingSymbol" rules={[{ required: true, message: '请输入底层证券代码' }]}>
                <Input placeholder="如 TSLA、AAPL" />
              </Form.Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col span={8}>
              <Form.Item label="数量" name="quantity" rules={[{ required: true, message: '请输入数量' }]}>
                <InputNumber min={1} style={{ width: '100%' }} placeholder="交易数量" />
              </Form.Item>
            </Col>
            <Col span={8}>
              <Form.Item label="成交价格" name="price" rules={[{ required: true, message: '请输入价格' }]}>
                <InputNumber min={0} step={0.01} style={{ width: '100%' }} placeholder="成交价格" />
              </Form.Item>
            </Col>
            <Col span={8}>
              <Form.Item label="交易费用" name="fee">
                <InputNumber min={0} step={0.01} style={{ width: '100%' }} placeholder="手续费（选填）" />
              </Form.Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item label="币种" name="currency" rules={[{ required: true, message: '请选择币种' }]}>
                <Select>
                  <Select.Option value="CNY">CNY - 人民币</Select.Option>
                  <Select.Option value="USD">USD - 美元</Select.Option>
                  <Select.Option value="HKD">HKD - 港币</Select.Option>
                </Select>
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item label="所属策略" name="strategyId">
                <Select allowClear placeholder="请选择策略（选填）">
                  {strategyList.map((s) => (
                    <Select.Option key={s.id} value={s.id}>{s.strategyName}</Select.Option>
                  ))}
                </Select>
              </Form.Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col span={8}>
              <Form.Item label="触发来源" name="tradeTrigger">
                <Select>
                  {Object.entries(tradeTriggerMap).map(([value, label]) => (
                    <Select.Option key={value} value={value}>{label}</Select.Option>
                  ))}
                </Select>
              </Form.Item>
            </Col>
            <Col span={8}>
              <Form.Item label="触发关联类型" name="triggerRefType">
                <Select>
                  {Object.entries(triggerRefTypeMap).map(([value, label]) => (
                    <Select.Option key={value} value={value}>{label}</Select.Option>
                  ))}
                </Select>
              </Form.Item>
            </Col>
            <Col span={8}>
              <Form.Item label="触发关联ID" name="triggerRefId">
                <InputNumber min={0} style={{ width: '100%' }} placeholder="关联的交易记录ID" />
              </Form.Item>
            </Col>
          </Row>
        </Form>
      </Modal>
    </Card>
  );
};

export default TradeRecordDetail;
