import React, { useCallback, useEffect, useState } from 'react';
import { Alert, Button, Card, Form, Input, Modal, Select, Space, Spin, Tag, Typography, message } from 'antd';
import { fetchAllBrokers, createBroker } from '../../../services/brokerApi';
import { fetchIbkrSettings, saveIbkrSettings, clearIbkrSettings, settingsError } from '../../../services/ibkrSettingsApi';

export default function IbkrSettingsTab({ onSaved, onDone }) {
  const [form] = Form.useForm();
  const [accountForm] = Form.useForm();
  const [view, setView] = useState(null);
  const [brokers, setBrokers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [creating, setCreating] = useState(false);
  const [accountOpen, setAccountOpen] = useState(false);
  const [loadError, setLoadError] = useState('');

  const load = useCallback(async () => {
    setLoading(true);
    const [accounts, settings] = await Promise.allSettled([fetchAllBrokers(), fetchIbkrSettings()]);
    const rows = accounts.status === 'fulfilled' ? accounts.value.data || [] : [];
    setBrokers(rows);
    if (settings.status === 'fulfilled' && accounts.status === 'fulfilled') {
      const data = settings.value.data;
      setView(data); setLoadError('');
      form.setFieldsValue({ brokerId: data.brokerId, queryId: data.queryId || '', token: undefined });
    } else {
      setView(null);
      setLoadError(settingsError(settings.reason || accounts.reason, '无法读取同步配置，请确认后端服务可用'));
      form.setFieldsValue({ token: undefined });
    }
    setLoading(false);
  }, [form]);
  useEffect(() => { load(); }, [load]);

  const submit = async () => {
    try {
      const values = await form.validateFields();
      setSaving(true);
      const result = await saveIbkrSettings(values);
      setView(result.data); form.setFieldValue('token', undefined);
      message.success('IBKR配置已保存，可以新建同步任务');
      onSaved?.(); await load();
    } catch (error) {
      if (!error.errorFields) message.error(settingsError(error));
      // Axios errors contain the write-only Token in request config; never log them.
    } finally { setSaving(false); }
  };

  const clear = () => Modal.confirm({
    title: '清空IBKR同步凭证？',
    content: '清空后需要重新填写Token和Query ID才能同步，已有交易保持原样。',
    okText: '清空凭证', okButtonProps: { danger: true }, cancelText: '取消',
    onOk: async () => {
      try {
        setSaving(true); await clearIbkrSettings();
        message.success('凭证已清空'); onSaved?.(); await load();
      } catch (error) { message.error(settingsError(error)); }
      finally { setSaving(false); }
    },
  });

  const addAccount = async () => {
    try {
      const values = await accountForm.validateFields(); setCreating(true);
      const result = await createBroker({ ...values, brokerCode: 'ibkr', isActive: true });
      const accounts = await fetchAllBrokers(); setBrokers(accounts.data || []);
      form.setFieldValue('brokerId', result.data.id);
      setAccountOpen(false); accountForm.resetFields(); onSaved?.();
      message.success('IBKR记账账户已创建，请继续填写同步凭证');
    } catch (error) { if (!error.errorFields) message.error(settingsError(error, '创建记账账户失败')); }
    finally { setCreating(false); }
  };

  const linked = brokers.find((broker) => broker.brokerCode === 'ibkr');
  const options = brokers.filter((broker) => broker.isActive && (!broker.brokerCode || broker.brokerCode === 'ibkr'))
    .map((broker) => ({ label: broker.brokerName, value: broker.id }));

  return <Spin spinning={loading}>
    <Card title="IBKR同步配置" extra={<Tag color={view?.ready ? 'success' : 'default'}>{view?.ready ? '已配置' : '待配置'}</Tag>}>
      <Space direction="vertical" size="middle" style={{ width: '100%', maxWidth: 680 }}>
        <Alert type="info" showIcon message="连接IBKR历史交易" description="在IBKR Client Portal创建Trade Confirmation Flex Query，包含订单及成交明细，并为单个账户生成报表。保存后，再到同步批次选择IBKR和日期范围。" />
        {loadError && <Alert type="error" showIcon message={loadError} action={<Button size="small" onClick={load}>重试</Button>} />}
        {linked && !linked.isActive && <Alert type="warning" showIcon message="IBKR记账账户已停用，请在券商管理中启用后再同步" />}
        <Form form={form} layout="vertical" onFinish={submit}>
          <Form.Item label="记账账户" name="brokerId" rules={[{ required: true, message: '请选择记账账户' }]} extra="同步后的交易将记入这个账户。">
            <Select options={options} disabled={!!linked} placeholder="选择已有账户，或新建IBKR账户" notFoundContent="暂无可选账户，请先新建IBKR账户" />
          </Form.Item>
          {!linked && <Button style={{ marginBottom: 20 }} onClick={() => { accountForm.setFieldsValue({ brokerName: 'IBKR', country: 'US' }); setAccountOpen(true); }}>新建IBKR账户</Button>}
          <Form.Item label="Flex Token" name="token" rules={[{ required: !view?.tokenConfigured, message: '请输入Flex Token' }]} extra={view?.tokenConfigured ? 'Token已配置，不回显；留空保留，填写新值则替换。' : '请输入IBKR Flex Web Service的Token。'}>
            <Input.Password autoComplete="new-password" placeholder={view?.tokenConfigured ? '留空保留已配置的Token' : '请输入Flex Token'} maxLength={4096} />
          </Form.Item>
          <Form.Item label="Trade Confirmation Query ID" name="queryId" rules={[{ required: true, message: '请输入Query ID' }, { pattern: /^[0-9]{1,64}$/, message: '请输入查询的数字Query ID' }]} extra="填写查询的Query ID，不是查询名称或下载ReferenceCode。">
            <Input inputMode="numeric" autoComplete="off" maxLength={64} placeholder="请输入Query ID" />
          </Form.Item>
          <Space wrap>
            <Button type="primary" onClick={submit} loading={saving}>保存配置</Button>
            <Button onClick={onDone}>前往同步批次</Button>
            <Button danger disabled={saving || (!view?.tokenConfigured && !loadError)} onClick={clear}>清空凭证</Button>
          </Space>
        </Form>
        <Typography.Text type="secondary">Token不会返回到页面。保存配置不自动发起IBKR请求；实际授权结果以同步批次为准。</Typography.Text>
      </Space>
    </Card>
    <Modal title="新建IBKR记账账户" open={accountOpen} onCancel={() => setAccountOpen(false)} onOk={addAccount} confirmLoading={creating} okText="创建账户" cancelText="取消" destroyOnClose>
      <Form form={accountForm} layout="vertical">
        <Form.Item label="账户名称" name="brokerName" rules={[{ required: true, whitespace: true, message: '请输入账户名称' }, { max: 100 }]}><Input /></Form.Item>
        <Form.Item label="国家／地区" name="country" rules={[{ required: true }]}><Select options={[{ label: '美国', value: 'US' }, { label: '中国香港', value: 'HK' }, { label: '中国大陆', value: 'CN' }, { label: '新西兰', value: 'NZ' }]} /></Form.Item>
      </Form>
    </Modal>
  </Spin>;
}
