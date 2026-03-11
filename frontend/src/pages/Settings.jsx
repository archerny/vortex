import React from 'react';
import { Card, Switch, Typography, Space, Divider, Alert } from 'antd';
import { EditOutlined } from '@ant-design/icons';
import { useTradeEditable } from '../contexts/TradeEditableContext';

const { Text, Paragraph } = Typography;

const Settings = () => {
  const { tradeEditable, setTradeEditable } = useTradeEditable();

  return (
    <Card title="系统设置">
      <Space direction="vertical" size="large" style={{ width: '100%' }}>
        {/* 交易记录编辑权限 */}
        <div>
          <Space align="center" size="middle" style={{ marginBottom: 8 }}>
            <EditOutlined style={{ fontSize: 18, color: '#1890ff' }} />
            <Text strong style={{ fontSize: 16 }}>交易记录编辑权限</Text>
          </Space>
          <Paragraph type="secondary" style={{ marginBottom: 12, marginLeft: 30 }}>
            开启后允许对交易记录执行新增、修改和删除操作；关闭后交易记录将处于只读状态，防止误操作。
          </Paragraph>
          <div style={{ marginLeft: 30 }}>
            <Space align="center">
              <Switch
                checked={tradeEditable}
                onChange={setTradeEditable}
                checkedChildren="已开启"
                unCheckedChildren="已关闭"
              />
              <Text type={tradeEditable ? 'success' : 'secondary'}>
                {tradeEditable ? '当前允许编辑交易记录' : '当前交易记录为只读模式'}
              </Text>
            </Space>
          </div>
          {tradeEditable && (
            <Alert
              message="编辑模式已开启，请注意操作规范，避免误修改历史交易数据。"
              type="warning"
              showIcon
              style={{ marginTop: 12, marginLeft: 30 }}
            />
          )}
        </div>
      </Space>
    </Card>
  );
};

export default Settings;
