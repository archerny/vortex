import React, { useState, useEffect, useCallback } from 'react';
import { Table, Tag, Select, Space, Typography, Tooltip, Card, Button, message, Modal, DatePicker, Form } from 'antd';
import { ReloadOutlined, CloudSyncOutlined, PlusOutlined } from '@ant-design/icons';
import dayjs from 'dayjs';
import duration from 'dayjs/plugin/duration';
import { fetchSyncBatches, triggerSync, fetchSupportedBrokers } from '../../services/brokerSyncApi';

dayjs.extend(duration);

const { Title, Text } = Typography;

/**
 * 同步管理页面
 *
 * 展示 broker_sync_batches 的历史同步批次记录。
 * 支持按券商和状态筛选，默认按 startedAt 倒序排列。
 */
const SyncManagement = () => {
  const [batches, setBatches] = useState([]);
  const [loading, setLoading] = useState(false);
  const [filters, setFilters] = useState({ brokerName: undefined, status: undefined });

  // New sync modal state
  const [modalOpen, setModalOpen] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [brokerOptions, setBrokerOptions] = useState([]);
  const [form] = Form.useForm();

  const loadBatches = useCallback(async () => {
    setLoading(true);
    try {
      const params = {};
      if (filters.brokerName) params.brokerName = filters.brokerName;
      if (filters.status) params.status = filters.status;
      const result = await fetchSyncBatches(params);
      setBatches(result.data || []);
    } catch (error) {
      console.error('Failed to load sync batches:', error);
      message.error('加载同步批次失败');
    } finally {
      setLoading(false);
    }
  }, [filters]);

  useEffect(() => {
    loadBatches();
  }, [loadBatches]);

  // Load supported brokers when modal opens
  const openModal = async () => {
    setModalOpen(true);
    try {
      const result = await fetchSupportedBrokers();
      const brokers = (result.data || []).map((name) => ({
        label: name.toUpperCase(),
        value: name,
      }));
      setBrokerOptions(brokers);
    } catch (error) {
      console.error('Failed to load supported brokers:', error);
      message.error('加载券商列表失败');
    }
  };

  // Submit sync trigger
  const handleSyncSubmit = async () => {
    try {
      const values = await form.validateFields();
      setSubmitting(true);

      const payload = { brokerName: values.brokerName };
      if (values.dateRange && values.dateRange.length === 2) {
        payload.startTime = values.dateRange[0].format('YYYY-MM-DD');
        payload.endTime = values.dateRange[1].format('YYYY-MM-DD');
      }

      const result = await triggerSync(payload);
      if (result.status === 'SUCCESS') {
        message.success('同步任务已提交，请稍后刷新查看结果');
        setModalOpen(false);
        form.resetFields();
        loadBatches();
      } else {
        message.error(result.message || '提交同步任务失败');
      }
    } catch (error) {
      if (error.response?.data?.message) {
        message.error(error.response.data.message);
      } else if (error.errorFields) {
        // Form validation error, do nothing
      } else {
        console.error('Sync trigger failed:', error);
        message.error('同步请求失败');
      }
    } finally {
      setSubmitting(false);
    }
  };

  // 状态标签颜色映射
  const statusTagColor = {
    PENDING: 'blue',
    IMPORTING: 'orange',
    COMPLETED: 'green',
    FAILED: 'red',
  };

  // 状态中文映射
  const statusLabel = {
    PENDING: '待处理',
    IMPORTING: '导入中',
    COMPLETED: '已完成',
    FAILED: '失败',
  };

  // 券商标签颜色映射
  const brokerTagColor = {
    ibkr: 'purple',
    tiger: 'orange',
    futu: 'cyan',
    schwab: 'blue',
  };

  // 计算耗时
  const calculateDuration = (startedAt, completedAt) => {
    if (!startedAt || !completedAt) return '-';
    const start = dayjs(startedAt);
    const end = dayjs(completedAt);
    const diffMs = end.diff(start);

    if (diffMs < 1000) return `${diffMs}ms`;
    if (diffMs < 60000) return `${(diffMs / 1000).toFixed(1)}s`;

    const dur = dayjs.duration(diffMs);
    const minutes = Math.floor(dur.asMinutes());
    const seconds = dur.seconds();
    return `${minutes}m ${seconds}s`;
  };

  // 格式化时间
  const formatTime = (time) => {
    if (!time) return '-';
    return dayjs(time).format('YYYY-MM-DD HH:mm:ss');
  };

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      width: 70,
    },
    {
      title: '券商',
      dataIndex: 'brokerName',
      key: 'brokerName',
      width: 100,
      render: (name) => (
        <Tag color={brokerTagColor[name] || 'default'}>
          {name ? name.toUpperCase() : '-'}
        </Tag>
      ),
    },
    {
      title: '同步日期范围',
      key: 'dateRange',
      width: 200,
      render: (_, record) => (
        <Text>
          {record.syncDateFrom} ~ {record.syncDateTo}
        </Text>
      ),
    },
    {
      title: '总记录数',
      dataIndex: 'totalCount',
      key: 'totalCount',
      width: 90,
      align: 'center',
    },
    {
      title: '已导入',
      dataIndex: 'importedCount',
      key: 'importedCount',
      width: 80,
      align: 'center',
      render: (count) => (
        <Text style={{ color: count > 0 ? '#52c41a' : undefined }}>{count}</Text>
      ),
    },
    {
      title: '已跳过',
      dataIndex: 'skippedCount',
      key: 'skippedCount',
      width: 80,
      align: 'center',
      render: (count) => (
        <Text type={count > 0 ? 'secondary' : undefined}>{count}</Text>
      ),
    },
    {
      title: '失败',
      dataIndex: 'failedCount',
      key: 'failedCount',
      width: 70,
      align: 'center',
      render: (count) => (
        <Text style={{ color: count > 0 ? '#ff4d4f' : undefined, fontWeight: count > 0 ? 600 : undefined }}>
          {count}
        </Text>
      ),
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      width: 90,
      render: (status) => (
        <Tag color={statusTagColor[status] || 'default'}>
          {statusLabel[status] || status}
        </Tag>
      ),
    },
    {
      title: '开始时间',
      dataIndex: 'startedAt',
      key: 'startedAt',
      width: 170,
      render: formatTime,
    },
    {
      title: '完成时间',
      dataIndex: 'completedAt',
      key: 'completedAt',
      width: 170,
      render: formatTime,
    },
    {
      title: '耗时',
      key: 'duration',
      width: 90,
      render: (_, record) => calculateDuration(record.startedAt, record.completedAt),
    },
    {
      title: '错误信息',
      dataIndex: 'errorMessage',
      key: 'errorMessage',
      ellipsis: true,
      render: (msg) =>
        msg ? (
          <Tooltip title={msg}>
            <Text type="danger" ellipsis style={{ maxWidth: 200 }}>
              {msg}
            </Text>
          </Tooltip>
        ) : (
          '-'
        ),
    },
  ];

  return (
    <div>
      <div style={{ marginBottom: 16, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <Title level={4} style={{ margin: 0 }}>
          <CloudSyncOutlined style={{ marginRight: 8 }} />
          同步管理
        </Title>
        <Space>
          <Button type="primary" icon={<PlusOutlined />} onClick={openModal}>
            新建同步
          </Button>
          <Button icon={<ReloadOutlined />} onClick={loadBatches} loading={loading}>
            刷新
          </Button>
        </Space>
      </div>

      <Card size="small" style={{ marginBottom: 16 }}>
        <Space size="middle">
          <Space>
            <Text>券商：</Text>
            <Select
              allowClear
              placeholder="全部券商"
              style={{ width: 140 }}
              value={filters.brokerName}
              onChange={(value) => setFilters((prev) => ({ ...prev, brokerName: value }))}
              options={[
                { label: 'IBKR', value: 'ibkr' },
                { label: 'Tiger', value: 'tiger' },
                { label: 'Futu', value: 'futu' },
                { label: 'Schwab', value: 'schwab' },
              ]}
            />
          </Space>
          <Space>
            <Text>状态：</Text>
            <Select
              allowClear
              placeholder="全部状态"
              style={{ width: 140 }}
              value={filters.status}
              onChange={(value) => setFilters((prev) => ({ ...prev, status: value }))}
              options={[
                { label: '待处理', value: 'PENDING' },
                { label: '导入中', value: 'IMPORTING' },
                { label: '已完成', value: 'COMPLETED' },
                { label: '失败', value: 'FAILED' },
              ]}
            />
          </Space>
        </Space>
      </Card>

      <Table
        columns={columns}
        dataSource={batches}
        rowKey="id"
        loading={loading}
        pagination={{
          defaultPageSize: 20,
          showSizeChanger: true,
          showTotal: (total) => `共 ${total} 条`,
        }}
        scroll={{ x: 1400 }}
        size="middle"
      />

      <Modal
        title="新建同步任务"
        open={modalOpen}
        onCancel={() => {
          setModalOpen(false);
          form.resetFields();
        }}
        onOk={handleSyncSubmit}
        confirmLoading={submitting}
        okText="开始同步"
        cancelText="取消"
        destroyOnClose
      >
        <Form form={form} layout="vertical" style={{ marginTop: 16 }}>
          <Form.Item
            name="brokerName"
            label="券商"
            rules={[{ required: true, message: '请选择券商' }]}
          >
            <Select
              placeholder="请选择券商"
              options={brokerOptions}
              loading={brokerOptions.length === 0}
            />
          </Form.Item>
          <Form.Item
            name="dateRange"
            label="日期范围"
            rules={[{ required: true, message: '请选择日期范围' }]}
          >
            <DatePicker.RangePicker style={{ width: '100%' }} />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default SyncManagement;
