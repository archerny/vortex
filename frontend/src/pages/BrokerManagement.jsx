import React, { useState, useEffect } from 'react';
import { Card, Table, Tag, Button, message, Modal, Form, Input, Select, Space, Descriptions, Typography } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import { fetchAllBrokers, createBroker, updateBroker } from '../services/brokerApi';

// 国家/地区选项
const countryOptions = [
  { label: '🇨🇳 中国大陆', value: 'CN' },
  { label: '🇭🇰 中国香港', value: 'HK' },
  { label: '🇺🇸 美国', value: 'US' },
  { label: '🇳🇿 新西兰', value: 'NZ' },
];

// 国家代码映射（用于展示）
const countryMap = {
  CN: { label: '中国大陆', color: 'red' },
  HK: { label: '中国香港', color: 'magenta' },
  US: { label: '美国', color: 'blue' },
  NZ: { label: '新西兰', color: 'cyan' },
};

const brokerTagStyle = { maxWidth: '100%', marginInlineEnd: 0, whiteSpace: 'normal', overflowWrap: 'anywhere' };

const renderBrokerCode = (code) => code
  ? <Tag color="geekblue" style={brokerTagStyle}>{code}</Tag>
  : <Typography.Text type="secondary">-</Typography.Text>;

const renderCountry = (country) => {
  const { label, color } = countryMap[country] || { label: country, color: 'default' };
  return <Tag color={color} style={brokerTagStyle}>{label}</Tag>;
};

// 券商技术标识符选项；是否支持同步由已接入的适配器决定。
// label 与 value 保持一致，展示为底层 code，便于识别券商及关联适配器。
const brokerCodeOptions = [
  { label: 'ibkr', value: 'ibkr' },
  { label: 'tiger', value: 'tiger' },
  { label: 'futu', value: 'futu' },
  { label: 'schwab', value: 'schwab' },
  { label: 'firstrade', value: 'firstrade' },
  { label: 'csc', value: 'csc' },
];

const BrokerManagement = () => {
  const [brokerData, setBrokerData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingBroker, setEditingBroker] = useState(null); // null 表示新增，否则为编辑
  const [detailBroker, setDetailBroker] = useState(null);
  const [submitting, setSubmitting] = useState(false);
  const [form] = Form.useForm();

  // 加载券商数据
  const loadBrokers = async () => {
    setLoading(true);
    try {
      const result = await fetchAllBrokers();
      if (result.status === 'SUCCESS') {
        const list = (result.data || []).map((item) => ({
          ...item,
          key: String(item.id),
        }));
        setBrokerData(list);
      } else {
        message.error(result.message || '查询券商数据失败');
      }
    } catch (error) {
      console.error('Failed to fetch broker data:', error);
      message.error('查询券商数据失败，请检查后端服务是否启动');
    } finally {
      setLoading(false);
    }
  };

  // 组件加载时获取数据
  useEffect(() => {
    loadBrokers();
  }, []);

  // 表格列定义
  const columns = [
    {
      title: '券商名称',
      dataIndex: 'brokerName',
      key: 'brokerName',
      width: 176,
      ellipsis: true,
      render: (name) => <strong>{name}</strong>,
    },
    {
      title: '券商代码',
      dataIndex: 'brokerCode',
      key: 'brokerCode',
      width: 112,
      render: renderBrokerCode,
    },
    {
      title: '国家/地区',
      dataIndex: 'country',
      key: 'country',
      width: 128,
      render: renderCountry,
      filters: countryOptions.map((item) => ({ text: item.label, value: item.value })),
      onFilter: (value, record) => record.country === value,
    },
    {
      title: '券商简介',
      dataIndex: 'introduction',
      key: 'introduction',
      ellipsis: { showTitle: false },
      render: (value) => value || '-',
    },
    {
      title: '操作',
      key: 'action',
      width: 124,
      render: (_, record) => (
        <Space size={12}>
          <Button type="link" size="small" style={{ paddingInline: 0 }} onClick={() => setDetailBroker(record)}>详情</Button>
          <Button type="link" size="small" style={{ paddingInline: 0 }} onClick={() => handleEdit(record)}>更新</Button>
        </Space>
      ),
    },
  ];

  // 打开编辑弹窗
  const handleEdit = (record) => {
    setEditingBroker(record);
    form.setFieldsValue({
      brokerName: record.brokerName,
      brokerCode: record.brokerCode,
      introduction: record.introduction,
      country: record.country,
    });
    setIsModalOpen(true);
  };

  // 打开新增弹窗
  const handleAdd = () => {
    setEditingBroker(null);
    form.resetFields();
    setIsModalOpen(true);
  };

  // 取消弹窗
  const handleCancel = () => {
    setIsModalOpen(false);
    setEditingBroker(null);
    form.resetFields();
  };

  // 提交表单
  const handleSubmit = () => {
    form.validateFields()
      .then(async (values) => {
        setSubmitting(true);
        try {
          if (editingBroker) {
            // 编辑模式 - 调用后端更新 API
              const result = await updateBroker(editingBroker.id, { ...values, brokerCode: values.brokerCode || null });
            if (result.status === 'SUCCESS') {
              message.success(`券商「${values.brokerName}」更新成功！`);
              loadBrokers();
            } else {
              message.error(result.message || '更新券商失败');
            }
          } else {
            // 新增模式 - 调用后端 API
            const result = await createBroker(values);
            if (result.status === 'SUCCESS') {
              message.success(`券商「${values.brokerName}」添加成功！`);
              loadBrokers(); // 重新加载数据
            } else {
              message.error(result.message || '新增券商失败');
            }
          }
          setIsModalOpen(false);
          setEditingBroker(null);
          form.resetFields();
        } catch (error) {
          console.error('Operation failed:', error);
          const errorMsg = error.response?.data?.message || '操作失败，请稍后重试';
          message.error(errorMsg);
        } finally {
          setSubmitting(false);
        }
      })
      .catch((errorInfo) => {
        console.log('Form validation failed:', errorInfo);
      });
  };

  return (
    <Card
      title="券商管理"
      extra={
        <Button type="primary" icon={<PlusOutlined />} onClick={handleAdd}>
          新增券商
        </Button>
      }
    >
      <Table
        columns={columns}
        dataSource={brokerData}
        loading={loading}
        rowKey="id"
        pagination={{ pageSize: 10 }}
        scroll={{ x: 720 }}
      />

      <Modal
        title="券商详情"
        open={Boolean(detailBroker)}
        onCancel={() => setDetailBroker(null)}
        footer={<Button onClick={() => setDetailBroker(null)}>关闭</Button>}
        width={600}
      >
        {detailBroker && (
          <Descriptions bordered size="small" layout="vertical" column={2}>
            <Descriptions.Item label="券商名称" span={2}>
              <span style={{ overflowWrap: 'anywhere' }}>{detailBroker.brokerName}</span>
            </Descriptions.Item>
            <Descriptions.Item label="券商代码">{renderBrokerCode(detailBroker.brokerCode)}</Descriptions.Item>
            <Descriptions.Item label="国家/地区">{renderCountry(detailBroker.country)}</Descriptions.Item>
            <Descriptions.Item label="券商简介" span={2}>
              <span style={{ whiteSpace: 'pre-wrap', overflowWrap: 'anywhere' }}>{detailBroker.introduction || '-'}</span>
            </Descriptions.Item>
          </Descriptions>
        )}
      </Modal>

      <Modal
        title={editingBroker ? '编辑券商信息' : '新增券商'}
        open={isModalOpen}
        onCancel={handleCancel}
        footer={[
          <Button key="cancel" onClick={handleCancel}>
            取消
          </Button>,
          <Button key="submit" type="primary" loading={submitting} onClick={handleSubmit}>
            {editingBroker ? '保存' : '提交'}
          </Button>,
        ]}
        width={600}
        destroyOnClose
      >
        <Form
          form={form}
          layout="vertical"
          style={{ marginTop: 20 }}
        >
          <Form.Item label="券商名称" name="brokerName"
            rules={[{ required: true, message: '请输入券商名称' }, { max: 100, message: '券商名称不能超过100个字符' }]}>
            <Input placeholder="请输入券商名称" />
          </Form.Item>

          <Form.Item
            label="国家/地区"
            name="country"
            rules={[{ required: true, message: '请选择所属国家/地区' }]}
          >
            <Select
              placeholder="请选择所属国家/地区"
              options={countryOptions}
              showSearch
              optionFilterProp="label"
            />
          </Form.Item>

          <Form.Item
            label="券商代码"
            name="brokerCode"
            tooltip="用于识别券商；填写代码后，仍需该券商已接入才能同步。"
          >
            <Select
              placeholder="请选择券商代码（选填）"
              options={brokerCodeOptions}
              allowClear
              showSearch
              optionFilterProp="label"
            />
          </Form.Item>

          <Form.Item label="券商简介" name="introduction" rules={[{ max: 500, message: '券商简介不能超过500个字符' }]}>
            <Input.TextArea placeholder="介绍券商的基本情况（选填）" rows={4} showCount maxLength={500} />
          </Form.Item>
        </Form>
      </Modal>
    </Card>
  );
};

export default BrokerManagement;
