import React, { useState, useEffect } from 'react';
import { Card, Table, Tag, Button, message, Modal, Form, Input, Select, Tooltip, Spin, Row, Col, Switch } from 'antd';
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

const BrokerManagement = () => {
  const [brokerData, setBrokerData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingBroker, setEditingBroker] = useState(null); // null 表示新增，否则为编辑
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
      console.error('查询券商数据失败:', error);
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
      render: (text) => <span style={{ fontWeight: 'bold' }}>{text}</span>,
    },
    {
      title: '国家/地区',
      dataIndex: 'country',
      key: 'country',
      render: (country) => {
        const info = countryMap[country] || { label: country, color: 'default' };
        return <Tag color={info.color}>{info.label}</Tag>;
      },
      filters: countryOptions.map((item) => ({ text: item.label, value: item.value })),
      onFilter: (value, record) => record.country === value,
    },
    {
      title: '描述',
      dataIndex: 'description',
      key: 'description',
      ellipsis: true,
      render: (text) => (
        <Tooltip title={text}>
          <span style={{ color: '#666' }}>{text || '-'}</span>
        </Tooltip>
      ),
    },
    {
      title: '关联邮箱',
      dataIndex: 'email',
      key: 'email',
      render: (text) => <span style={{ color: '#666' }}>{text || '-'}</span>,
    },
    {
      title: '关联电话',
      dataIndex: 'phone',
      key: 'phone',
      render: (text) => <span style={{ color: '#666' }}>{text || '-'}</span>,
    },
    {
      title: '状态',
      dataIndex: 'isActive',
      key: 'isActive',
      width: 100,
      render: (isActive) => (
        <Tag color={isActive ? 'green' : 'default'}>
          {isActive ? '启用' : '禁用'}
        </Tag>
      ),
      filters: [
        { text: '启用', value: true },
        { text: '禁用', value: false },
      ],
      onFilter: (value, record) => record.isActive === value,
    },
    {
      title: '操作',
      key: 'action',
      width: 80,
      render: (_, record) => (
        <a onClick={() => handleEdit(record)}>更新</a>
      ),
    },
  ];

  // 打开编辑弹窗
  const handleEdit = (record) => {
    setEditingBroker(record);
    form.setFieldsValue({
      brokerName: record.brokerName,
      isActive: record.isActive,
      country: record.country,
      description: record.description,
      email: record.email,
      phone: record.phone,
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
            const result = await updateBroker(editingBroker.id, values);
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
          console.error('操作失败:', error);
          const errorMsg = error.response?.data?.message || '操作失败，请稍后重试';
          message.error(errorMsg);
        } finally {
          setSubmitting(false);
        }
      })
      .catch((errorInfo) => {
        console.log('表单验证失败:', errorInfo);
      });
  };

  return (
    <Card
      title="券商账户管理"
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
        rowClassName={(record) => (!record.isActive ? 'inactive-row' : '')}
      />

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
          <Row gutter={16}>
            <Col flex="auto">
              <Form.Item
                label="券商名称"
                name="brokerName"
                rules={[
                  { required: true, message: '请输入券商名称' },
                  { max: 100, message: '券商名称不能超过100个字符' },
                ]}
              >
                <Input placeholder="请输入券商名称" />
              </Form.Item>
            </Col>
            <Col flex="100px">
              <Form.Item
                label="状态"
                name="isActive"
                valuePropName="checked"
                initialValue={true}
              >
                <Switch checkedChildren="启用" unCheckedChildren="禁用" />
              </Form.Item>
            </Col>
          </Row>

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
            label="描述"
            name="description"
            rules={[{ max: 200, message: '描述不能超过200个字符' }]}
          >
            <Input.TextArea
              placeholder="请输入券商账户描述（选填）"
              rows={3}
              showCount
              maxLength={200}
            />
          </Form.Item>

          <Form.Item
            label="关联邮箱"
            name="email"
            rules={[
              { type: 'email', message: '请输入有效的邮箱地址' },
              { max: 100, message: '邮箱不能超过100个字符' },
            ]}
          >
            <Input placeholder="请输入关联邮箱（选填）" />
          </Form.Item>

          <Form.Item
            label="关联电话"
            name="phone"
            rules={[{ max: 30, message: '电话号码不能超过30个字符' }]}
          >
            <Input placeholder="请输入关联电话（选填）" />
          </Form.Item>
        </Form>
      </Modal>
    </Card>
  );
};

export default BrokerManagement;
