import React, { useEffect, useState } from 'react';
import { Alert, Button, Card, Form, Input, Modal, Select, Space, Switch, Table, Tag, message } from 'antd';
import { fetchAllBrokers } from '../services/brokerApi';
import { createBrokerAccount, deleteBrokerAccount, fetchBrokerAccounts, updateBrokerAccount } from '../services/brokerAccountApi';

export default function BrokerAccountsManagement() {
  const [accounts, setAccounts] = useState([]);
  const [brokers, setBrokers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [editing, setEditing] = useState(null);
  const [open, setOpen] = useState(false);
  const [saving, setSaving] = useState(false);
  const [form] = Form.useForm();
  const load = async () => {
    setLoading(true);
    try {
      const [accountResult, brokerResult] = await Promise.all([fetchBrokerAccounts(), fetchAllBrokers()]);
      setAccounts(accountResult.data || []); setBrokers(brokerResult.data || []);
    } catch (error) { message.error(error.response?.data?.message || '无法读取托管账户'); }
    finally { setLoading(false); }
  };
  useEffect(() => { load(); }, []);
  const edit = (account = null) => {
    setEditing(account); setOpen(true);
  };
  useEffect(() => { if (open) { form.resetFields(); form.setFieldsValue(editing || { isActive: true }); } }, [open, editing, form]);
  const save = async () => {
    try {
      const values = await form.validateFields(); setSaving(true);
      if (editing) await updateBrokerAccount(editing.id, values); else await createBrokerAccount(values);
      setOpen(false); message.success('托管账户已保存'); await load();
    } catch (error) { if (!error.errorFields) message.error(error.response?.data?.message || '保存账户失败'); }
    finally { setSaving(false); }
  };
  const remove = (account) => Modal.confirm({ title: `删除账户「${account.accountName}」？`,
    content: '有关联账本或同步批次的账户不能删除，可改为停用。', okText: '删除', cancelText: '取消', okButtonProps: { danger: true },
    onOk: async () => { try { await deleteBrokerAccount(account.id); await load(); }
      catch (error) { message.error(error.response?.data?.message || '删除账户失败'); throw error; } },
  });
  return <Card title="托管账户管理" extra={<Space>
    <Button type="primary" onClick={() => edit()}>新增托管账户</Button>
    <Button onClick={load}>刷新</Button>
  </Space>}>
    <Alert type="info" showIcon message="同一券商可录入多个账户。待补全账户保留历史数据，填写真实账户 ID 后才可同步。" style={{ marginBottom: 16 }} />
    <Table rowKey="id" loading={loading} dataSource={accounts} columns={[
      { title: '账户名称', dataIndex: 'accountName' },
      { title: '账户 ID', dataIndex: 'accountId', render: (id) => id || <Tag color="orange">待补全</Tag> },
      { title: '券商名称', key: 'broker', render: (_, row) => row.broker?.brokerName || brokers.find(b => b.id === row.brokerId)?.brokerName },
      { title: '关联邮箱', dataIndex: 'email', render: (value) => value || '-' },
      { title: '手机号 / 电话', dataIndex: 'phone', render: (value) => value || '-' },
      { title: '账户备注', dataIndex: 'description', ellipsis: true, render: (value) => value || '-' },
      { title: '状态', dataIndex: 'isActive', render: (active) => <Tag color={active ? 'green' : 'default'}>{active ? '启用' : '停用'}</Tag> },
      { title: '操作', key: 'actions', render: (_, row) => <Space><Button type="link" onClick={() => edit(row)}>编辑</Button><Button danger type="link" onClick={() => remove(row)}>删除</Button></Space> },
    ]} />
    <Modal open={open} title={editing ? '编辑托管账户' : '新增托管账户'} onCancel={() => setOpen(false)}
      closable={!saving} maskClosable={!saving} keyboard={!saving} destroyOnClose
      footer={<Space><Button disabled={saving} onClick={() => setOpen(false)}>取消</Button><Button type="primary" loading={saving} onClick={save}>保存账户</Button></Space>}>
      <Form form={form} layout="vertical">
        <Form.Item name="brokerId" label="所属券商" rules={[{ required: true, message: '请选择券商' }]}>
          <Select disabled={!!editing} options={brokers.map(broker => ({ value: broker.id, label: broker.brokerName }))} />
        </Form.Item>
        <Form.Item name="accountName" label="账户名称" rules={[{ required: true, whitespace: true, message: '请输入账户名称' }]}><Input maxLength={100} /></Form.Item>
        <Form.Item name="accountId" label="账户 ID" extra={editing && !editing.accountId ? '可暂时留空以修改名称或启停；补齐真实账户 ID 后才能同步。Tiger 使用资金账号。' : '填写券商提供的真实账户 ID，Tiger 使用资金账号。'}
          rules={[{ required: !editing || !!editing.accountId, whitespace: true, message: '请输入真实账户 ID' }, { pattern: /^\S{1,100}$/, message: '账户 ID 不能包含空白，最多100字符' }]}><Input maxLength={100} autoComplete="off" /></Form.Item>
        <Form.Item name="email" label="关联邮箱" rules={[{ type: 'email', transform: (value) => value?.trim(), message: '请输入有效的邮箱地址' }, { max: 100, message: '邮箱不能超过100个字符' }]}>
          <Input maxLength={100} placeholder="账户关联邮箱（选填）" autoComplete="off" />
        </Form.Item>
        <Form.Item name="phone" label="手机号 / 电话" rules={[{ max: 30, message: '电话号码不能超过30个字符' }]}>
          <Input maxLength={30} placeholder="账户关联手机号或电话，可包含国际区号（选填）" autoComplete="off" />
        </Form.Item>
        <Form.Item name="description" label="账户备注" rules={[{ max: 200, message: '账户备注不能超过200个字符' }]}>
          <Input.TextArea rows={3} maxLength={200} showCount placeholder="账户备注（选填）" />
        </Form.Item>
        <Form.Item name="isActive" label="启用账户" valuePropName="checked"><Switch /></Form.Item>
      </Form>
    </Modal>
  </Card>;
}
