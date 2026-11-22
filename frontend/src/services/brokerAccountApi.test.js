import { beforeEach, expect, it, vi } from 'vitest';
import axios from 'axios';
import { accountLabel, createBrokerAccount, deleteBrokerAccount, fetchBrokerAccounts, updateBrokerAccount } from './brokerAccountApi';

vi.mock('axios', () => ({ default: { get: vi.fn(), post: vi.fn(), put: vi.fn(), delete: vi.fn() } }));
beforeEach(() => {
  vi.resetAllMocks();
  for (const method of ['get', 'post', 'put', 'delete']) axios[method].mockResolvedValue({ data: { status: 'SUCCESS' } });
});
it('keeps the broker key, local account key and real account ID separate', async () => {
  const account = { brokerId: 3, accountId: 'SYNTH-ACCOUNT-B', accountName: 'Second account', isActive: true,
    email: 'second@example.test', phone: '+1 202 555 0102', description: 'Synthetic account note' };
  await createBrokerAccount(account); await updateBrokerAccount(12, account); await deleteBrokerAccount(12);
  expect(axios.post).toHaveBeenCalledWith('/api/broker-accounts', account);
  expect(axios.put).toHaveBeenCalledWith('/api/broker-accounts/12', account);
  expect(axios.delete).toHaveBeenCalledWith('/api/broker-accounts/12');
});
it('reads accounts separately from the broker catalogue and distinguishes same-broker choices', async () => {
  await fetchBrokerAccounts(3);
  expect(axios.get).toHaveBeenCalledWith('/api/broker-accounts', { params: { brokerId: 3 } });
  const broker = { brokerName: 'Synthetic broker' };
  expect(accountLabel({ broker, accountName: 'First', accountId: 'SYNTH-A' })).toBe('Synthetic broker · First · SYNTH-A');
  expect(accountLabel({ broker, accountName: 'Second', accountId: 'SYNTH-B' })).toBe('Synthetic broker · Second · SYNTH-B');
  expect(accountLabel({ broker, accountName: 'Pending', accountId: null })).toContain('待补全账户ID');
});
