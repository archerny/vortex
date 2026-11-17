import React, { useState } from 'react';
import { Alert, Button, Modal, message } from 'antd';
import { marketEventErrorMessage } from '../../utils/marketEventError';

const MarketEventUndoButton = ({ event, remove, onSuccess }) => {
  const [open, setOpen] = useState(false);
  const [busy, setBusy] = useState(false);
  const [error, setError] = useState('');

  const undo = async () => {
    setBusy(true);
    setError('');
    try {
      const result = await remove(event.id);
      if (result.status !== 'SUCCESS') throw new Error(result.message || '撤销失败');
      setOpen(false);
      message.success('事件已撤销');
      await onSuccess();
    } catch (failure) {
      setError(marketEventErrorMessage(failure, failure.message || '撤销失败'));
    } finally {
      setBusy(false);
    }
  };

  return <>
    <Button danger size="small" onClick={() => { setError(''); setOpen(true); }}>撤销事件</Button>
    <Modal title="撤销市场事件" open={open} confirmLoading={busy} okText="确认撤销" cancelText="取消"
      okButtonProps={{ danger: true }} cancelButtonProps={{ disabled: busy }}
      closable={!busy} maskClosable={!busy} keyboard={!busy}
      onOk={undo} onCancel={() => { if (!busy) setOpen(false); }}>
      <p>撤销 {event.eventDate} 的事件将移除本事件生成的交易，并解除本事件的历史限制。</p>
      <p>如有同日或更晚的关联交易、事件，请先手动回退。其他事件施加的限制仍然有效。</p>
      {error && <Alert type="error" showIcon message={error} />}
    </Modal>
  </>;
};

export default MarketEventUndoButton;
