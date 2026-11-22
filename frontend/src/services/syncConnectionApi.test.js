import { beforeEach, describe, expect, it, vi } from 'vitest';
import axios from 'axios';
import { clearSyncConnection, connectionError, fetchSyncConnection, fetchSyncConnections, saveSyncConnection } from './syncConnectionApi';

vi.mock('axios', () => ({ default: { get: vi.fn(), put: vi.fn(), delete: vi.fn() } }));

describe('managed account sync connections', () => {
  beforeEach(() => {
    vi.resetAllMocks();
    axios.put.mockResolvedValue({ data: { status: 'SUCCESS' } });
  });
  it('saves only the selected account and the matching broker fields', async () => {
    await saveSyncConnection(7, 'ibkr', { token: ' synthetic-token ', queryId: ' 123 ', account: 'ignored' });
    expect(axios.put).toHaveBeenLastCalledWith('/api/broker-sync/connections/7', {
      brokerCode: 'ibkr', credentials: { token: 'synthetic-token', queryId: '123' },
    });
    await saveSyncConnection(9, 'tiger', { tigerId: ' 100 ', account: ' SYNTH ', privateKey: ' ', queryId: 'ignored' });
    expect(axios.put).toHaveBeenLastCalledWith('/api/broker-sync/connections/9', {
      brokerCode: 'tiger', credentials: { tigerId: '100' },
    });
  });
  it('retains multiline private keys and uses account-scoped read and clear requests', async () => {
    await saveSyncConnection(9, 'tiger', { tigerId: '100', account: 'SYNTH', privateKey: ' synthetic\nprivate-key ' });
    expect(axios.put.mock.calls[0][1].credentials.privateKey).toBe('synthetic\nprivate-key');
    axios.get.mockResolvedValue({ data: { data: [] } });
    axios.delete.mockResolvedValue({ data: { data: { configured: false } } });
    await fetchSyncConnections(); await fetchSyncConnection(9); await clearSyncConnection(9);
    expect(axios.get).toHaveBeenCalledWith('/api/broker-sync/connections');
    expect(axios.get).toHaveBeenCalledWith('/api/broker-sync/connections/9');
    expect(axios.delete).toHaveBeenCalledWith('/api/broker-sync/connections/9');
    expect(connectionError({ message: 'synthetic-private-key' })).not.toContain('synthetic-private-key');
  });
});
