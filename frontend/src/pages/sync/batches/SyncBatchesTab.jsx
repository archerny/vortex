import React, { useState, useEffect, useCallback } from 'react';
import { Table, Tag, Select, Space, Typography, Tooltip, Card, Button, message, Modal, DatePicker, Form } from 'antd';
import { ReloadOutlined, PlusOutlined } from '@ant-design/icons';
import dayjs from 'dayjs';
import duration from 'dayjs/plugin/duration';
import { fetchSyncBatches, triggerSync, fetchSupportedBrokers } from '../../../services/brokerSyncApi';
import { fetchAllBrokers } from '../../../services/brokerApi';

dayjs.extend(duration);

const { Text } = Typography;

/**
 * 同步批次 Tab（原 SyncManagement 主体内容）
 *
 * 展示 broker_sync_batches 的历史同步批次记录。
 * 支持按券商和状态筛选，默认按 startedAt 倒序排列。
 *
 * 券商下拉选项来源：/api/brokers（券商管理录入的券商），遵循
 * "券商管理是券商选项的单一数据源" 原则。
 */
const SyncBatchesTab = () => {
  const [batches, setBatches] = useState([]);
  const [loading, setLoading] = useState(false);
  const [filters, setFilters] = useState({ brokerCode: undefined, status: undefined });

  // 筛选用的券商下拉选项（来自券商管理）
  const [filterBrokerOptions, setFilterBrokerOptions] = useState([]);

  // New sync modal state
  const [modalOpen, setModalOpen] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [syncBrokerOptions, setSyncBrokerOptions] = useState([]);
  const [form] = Form.useForm();

  const loadBatches = useCallback(async () => {
    setLoading(true);
    try {
      const params = {};
      if (filters.brokerCode) params.brokerCode = filters.brokerCode;
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

  // 初次加载：同时拉批次 + 筛选下拉的券商列表
  useEffect(() => {
    loadBatches();
  }, [loadBatches]);

  useEffect(() => {
    const loadFilterBrokers = async () => {
      try {
        const result = await fetchAllBrokers();
        const brokers = (result.data || [])
          .filter((b) => b.brokerCode) // 筛选只包含有 brokerCode 的券商（可参与同步）
          .map((b) => ({
            label: b.brokerName || b.brokerCode.toUpperCase(),
            value: b.brokerCode,
          }));
        setFilterBrokerOptions(brokers);
      } catch (error) {
        console.error('Failed to load brokers for filter:', error);
      }
    };
    loadFilterBrokers();
  }, []);

  // Load supported brokers (for triggering new sync) when modal opens
  const openModal = async () => {
    setModalOpen(true);
    try {
      const result = await fetchSupportedBrokers();
      const brokers = (result.data || []).map((info) => ({
        label: info.brokerName || info.brokerCode.toUpperCase(),
        value: info.brokerCode,
      }));
      setSyncBrokerOptions(brokers);
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

      const payload = { brokerCode: values.brokerCode };
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
      const data = error.response?.data;
      // 409 Conflict: an active batch is blocking this request.
      // Show a richer modal with the conflicting batch id / status and
      // CLEANUP_FAILED guidance when applicable.
      if (error.response?.status === 409 && data?.conflictingBatchId != null) {
        const conflictStatus = data.conflictingStatus;
        const conflictBatchId = data.conflictingBatchId;
        Modal.warning({
          title: '无法启动新的同步',
          content: (
            <div>
              <p style={{ marginBottom: 8 }}>{data.message}</p>
              <p style={{ marginBottom: 8 }}>
                冲突批次：
                <Tag color={statusTagColor[conflictStatus] || 'default'} style={{ marginLeft: 8 }}>
                  #{conflictBatchId} {statusLabel[conflictStatus] || conflictStatus}
                </Tag>
              </p>
              {conflictStatus === 'CLEANUP_FAILED' && (
                <p style={{ color: '#fa8c16', marginBottom: 0 }}>
                  该批次自动清理失败，需人工确认残留数据后将其状态改为 FAILED，才能重新触发同步。
                </p>
              )}
            </div>
          ),
          okText: '知道了',
        });
        return;
      }
      if (data?.message) {
        message.error(data.message);
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
  // v2 应用产生的终态：COMPLETED / FAILED / CLEANUP_FAILED；
  // PARTIAL / INTERRUPTED 保留仅用于显示历史批次（v1 遗留数据）。
  const statusTagColor = {
    PENDING: 'blue',
    PROCESSING: 'orange',
    COMPLETED: 'green',
    FAILED: 'red',
    CLEANUP_FAILED: 'magenta',
    PARTIAL: 'gold',
    INTERRUPTED: 'volcano',
  };

  // 状态中文映射
  const statusLabel = {
    PENDING: '待处理',
    PROCESSING: '处理中',
    COMPLETED: '已完成',
    FAILED: '失败',
    CLEANUP_FAILED: '清理失败',
    PARTIAL: '部分完成',
    INTERRUPTED: '已中断',
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
      dataIndex: 'brokerCode',
      key: 'brokerCode',
      width: 100,
      render: (code) => (
        <Tag color={brokerTagColor[code] || 'default'}>
          {code ? code.toUpperCase() : '-'}
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
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      width: 100,
      render: (status, record) => {
        const tooltipParts = [];
        if (record.phase) tooltipParts.push(`阶段: ${record.phase}`);
        if (status === 'CLEANUP_FAILED') {
          tooltipParts.push('自动清理失败，需人工确认残留数据后将状态改为 FAILED 才能重试');
        }
        const tooltipTitle = tooltipParts.length > 0 ? tooltipParts.join('；') : undefined;
        return (
          <Tooltip title={tooltipTitle}>
            <Tag color={statusTagColor[status] || 'default'}>
              {statusLabel[status] || status}
            </Tag>
          </Tooltip>
        );
      },
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
      <div style={{ marginBottom: 16, display: 'flex', justifyContent: 'flex-end' }}>
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
              style={{ width: 160 }}
              value={filters.brokerCode}
              onChange={(value) => setFilters((prev) => ({ ...prev, brokerCode: value }))}
              options={filterBrokerOptions}
              notFoundContent="请先在券商管理中录入券商"
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
                { label: '处理中', value: 'PROCESSING' },
                { label: '已完成', value: 'COMPLETED' },
                { label: '失败', value: 'FAILED' },
                { label: '清理失败', value: 'CLEANUP_FAILED' },
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
        scroll={{ x: 1410 }}
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
            name="brokerCode"
            label="券商"
            rules={[{ required: true, message: '请选择券商' }]}
          >
            <Select
              placeholder="请选择券商"
              options={syncBrokerOptions}
              loading={syncBrokerOptions.length === 0}
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

export default SyncBatchesTab;
