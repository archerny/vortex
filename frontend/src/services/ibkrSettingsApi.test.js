import { beforeEach, describe, expect, it, vi } from 'vitest';
import axios from 'axios';
import { saveIbkrSettings, fetchIbkrSettings, clearIbkrSettings, settingsError } from './ibkrSettingsApi';

vi.mock('axios', () => ({ default: { get: vi.fn(), put: vi.fn(), delete: vi.fn() } }));
describe('IBKR credential requests', () => {
  beforeEach(() => vi.resetAllMocks());
  it('omits blank tokens to retain the stored value and only sends supported fields', async () => {
    axios.put.mockResolvedValue({ data: { status: 'SUCCESS' } });
    await saveIbkrSettings({ brokerId: 1, queryId: ' 123 ', token: ' ', unrelated: 'ignored' });
    expect(axios.put).toHaveBeenLastCalledWith('/api/broker-sync/ibkr/settings', { brokerId: 1, queryId: '123' });
    await saveIbkrSettings({ brokerId: 1, queryId: '123', token: ' synthetic-token ' });
    expect(axios.put).toHaveBeenLastCalledWith('/api/broker-sync/ibkr/settings', { brokerId: 1, queryId: '123', token: 'synthetic-token' });
  });
  it('uses metadata and clear endpoints without credentials and never displays request errors', async () => {
    const metadata = { tokenConfigured: true, queryId: '123' };
    axios.get.mockResolvedValue({ data: { data: metadata } });
    axios.delete.mockResolvedValue({ data: { data: { tokenConfigured: false } } });
    expect((await fetchIbkrSettings()).data).toEqual(metadata);
    expect((await clearIbkrSettings()).data.tokenConfigured).toBe(false);
    expect(axios.get).toHaveBeenCalledWith('/api/broker-sync/ibkr/settings');
    expect(axios.delete).toHaveBeenCalledWith('/api/broker-sync/ibkr/settings');
    expect(settingsError({ message: 'synthetic-secret', config: { data: 'synthetic-secret' } })).not.toContain('synthetic-secret');
  });
});
