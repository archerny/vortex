import React, { useCallback, useEffect, useState } from 'react';
import { Alert, Button, Card, Form, Input, Modal, Space, Spin, Table, Tag, message } from 'antd';
import { ReloadOutlined } from '@ant-design/icons';
import { syncConnectionConfig } from '../../../constants/syncConnectionConfig';
import { clearSyncConnection, connectionError, fetchSyncConnection, fetchSyncConnections, saveSyncConnection } from '../../../services/syncConnectionApi';

const openAccountManagement = () => { window.location.hash = '#/broker-accounts'; };

export default function SyncConnectionSettingsTab({ onSaved }) {
  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [loadError, setLoadError] = useState('');
  const [editingId, setEditingId] = useState(null);
  const [connection, setConnection] = useState(null);
  const [editorLoading, setEditorLoading] = useState(false);
  const [editorError, setEditorError] = useState('');
  const [saving, setSaving] = useState(false);
  const [form] = Form.useForm();

  const load = useCallback(async () => {
    setLoading(true);
    try {
      const result = await fetchSyncConnections();
      setAccounts(result.data || []);
      setLoadError('');
    } catch (error) {
      setLoadError(connectionError(error, '无法读取托管账户，请重试'));
    } finally { setLoading(false); }
  }, []);
  useEffect(() => { load(); }, [load]);

  useEffect(() => {
    if (editingId == null) return;
    let active = true;
    setEditorLoading(true);
    setEditorError('');
    setConnection(null);
    form.resetFields();
    fetchSyncConnection(editingId).then((result) => {
      if (!active) return;
      setConnection(result.data);
      form.setFieldsValue(result.data.values);
    }).catch((error) => {
      if (active) setEditorError(connectionError(error, '无法读取连接配置'));
    }).finally(() => { if (active) setEditorLoading(false); });
    return () => { active = false; };
  }, [editingId, form]);

  const closeEditor = () => {
    setEditingId(null);
    setConnection(null);
    form.resetFields();
  };

  const submit = async () => {
    try {
      const values = await form.validateFields();
      setSaving(true);
      await saveSyncConnection(editingId, connection.brokerCode, values);
      closeEditor();
      message.success('同步连接已保存');
      await load();
      onSaved?.();
    } catch (error) {
      if (!error.errorFields) message.error(connectionError(error));
    } finally { setSaving(false); }
  };

  const clear = () => Modal.confirm({
    title: `清空「${connection.accountName}」的同步连接？`,
    content: '清空后可重新填写连接信息。',
    okText: '清空连接', cancelText: '取消', okButtonProps: { danger: true },
    onOk: async () => {
      try {
        setSaving(true);
        await clearSyncConnection(editingId);
        closeEditor();
        await load();
        onSaved?.();
        message.success('同步连接已清空');
      } catch (error) { message.error(connectionError(error)); }
      finally { setSaving(false); }
    },
  });

  const columns = [
    { title: '账户名称', dataIndex: 'accountName', key: 'accountName' },
    { title: '账户 ID', dataIndex: 'accountId', key: 'accountId', render: (id) => id || <Tag color="orange">待补全</Tag> },
    { title: '券商名称', dataIndex: 'brokerName', key: 'brokerName' },
    { title: '券商代码', dataIndex: 'brokerCode', key: 'brokerCode', render: (code) => syncConnectionConfig[code]?.label || code || '未配置代码' },
    { title: '账户状态', dataIndex: 'active', key: 'active', render: (active) => <Tag color={active ? 'green' : 'default'}>{active ? '启用' : '停用'}</Tag> },
    { title: '同步连接', key: 'connection', render: (_, account) => (
      <Tag color={account.configured ? 'success' : 'default'}>{account.configured ? '已配置' : account.supported ? '待配置' : '暂未支持'}</Tag>
    ) },
    { title: '操作', key: 'actions', render: (_, account) => account.supported ? (
      <Button type="link" onClick={() => setEditingId(account.brokerAccountId)}>{account.configured ? '编辑连接' : '配置连接'}</Button>
    ) : <Button type="link" onClick={openAccountManagement}>管理账户</Button> },
  ];
  const config = syncConnectionConfig[connection?.brokerCode];

  return <Card title="托管账户" extra={<Button icon={<ReloadOutlined />} onClick={load} loading={loading}>刷新</Button>}>
    {loadError && <Alert type="error" showIcon message={loadError} style={{ marginBottom: 16 }} />}
    <Table rowKey="brokerAccountId" dataSource={accounts} columns={columns} loading={loading}
      locale={{ emptyText: '尚未录入托管账户，请前往账户管理添加' }} pagination={{ pageSize: 10 }} />
    <Modal title={connection ? `${connection.accountName} · 同步连接` : '同步连接'} open={editingId != null}
      onCancel={closeEditor} closable={!saving} maskClosable={!saving} keyboard={!saving} destroyOnClose footer={<Space>
        {connection?.configured && <Button danger onClick={clear} disabled={saving}>清空连接</Button>}
        <Button onClick={closeEditor} disabled={saving}>取消</Button>
        <Button type="primary" onClick={submit} loading={saving} disabled={!config || editorLoading}>保存连接</Button>
      </Space>}>
      <Spin spinning={editorLoading}>
        {editorError && <Alert type="error" showIcon message={editorError} action={<Button onClick={closeEditor}>关闭并刷新</Button>} />}
        <Form form={form} layout="vertical" style={{ marginTop: 16 }}>
          {config && <Alert type="info" message={config.description} style={{ marginBottom: 16 }} />}
          {config?.fields.map((field) => {
            const retained = field.secret && connection.configuredSecrets.includes(field.name);
            return <Form.Item key={field.name} name={field.name} label={field.label}
              extra={retained ? '已配置，留空保留，填写新值替换。' : undefined}
              rules={[{ required: !retained, whitespace: !retained, message: `请输入${field.label}` },
                ...(field.pattern ? [{ pattern: field.pattern, message: '请输入数字查询 ID' }] : [])]}>
              {field.multiline ? <Input.TextArea rows={5} autoComplete="off" maxLength={field.maxLength} />
                : field.secret ? <Input.Password autoComplete="new-password" maxLength={field.maxLength} />
                  : <Input autoComplete="off" maxLength={field.maxLength} />}
            </Form.Item>;
          })}
        </Form>
      </Spin>
    </Modal>
  </Card>;
}
