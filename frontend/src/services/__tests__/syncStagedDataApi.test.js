import { beforeEach, describe, expect, it, vi } from 'vitest';
import axios from 'axios';
import { fetchIbkrStagedOrders, fetchIbkrStagedTradeConfirms } from '../syncStagedDataApi';

vi.mock('axios', () => ({ default: { get: vi.fn() } }));

const success = (data) => ({ data: { status: 'SUCCESS', data } });

describe('IBKR staged account display', () => {
  beforeEach(() => axios.get.mockReset());

  it.each([
    ['orders', fetchIbkrStagedOrders],
    ['trade-confirms', fetchIbkrStagedTradeConfirms],
  ])('matches %s to their own batch and preserves the report fields', async (resource, fetchRows) => {
    const rows = [
      { id: 1, batchId: 32, accountId: 'REPORT-A', acctAlias: 'Report alias' },
      { id: 2, batchId: 14, accountId: 'REPORT-B', acctAlias: '' },
      { id: 3, batchId: 11, accountId: 'REPORT-C' },
      { id: 4, batchId: 9, accountId: 'REPORT-OLD' },
    ];
    axios.get.mockResolvedValueOnce(success(rows));
    axios.get.mockResolvedValueOnce(success([
      { id: 14, accountId: 'SNAPSHOT-B', brokerAccount: { accountName: 'Demo B' } },
      { id: 32, accountId: 'SNAPSHOT-A', brokerAccount: { accountId: 'CURRENT-A', accountName: 'Demo A' } },
      { id: 11, brokerAccount: { accountId: 'CURRENT-C', accountName: 'Demo C' } },
    ]));

    const result = await fetchRows();

    expect(axios.get).toHaveBeenNthCalledWith(1, `/api/sync/ibkr/${resource}`, { params: {} });
    expect(axios.get).toHaveBeenNthCalledWith(2, '/api/broker-sync/batches', { params: { brokerCode: 'ibkr' } });
    expect(result.data).toEqual([
      { ...rows[0], syncAccountId: 'SNAPSHOT-A', syncAccountName: 'Demo A' },
      { ...rows[1], syncAccountId: 'SNAPSHOT-B', syncAccountName: 'Demo B' },
      { ...rows[2], syncAccountId: undefined, syncAccountName: 'Demo C' },
      { ...rows[3], syncAccountId: undefined, syncAccountName: undefined },
    ]);
  });

  it('keeps filtered report rows available when the batch request fails', async () => {
    const rows = [{ id: 1, batchId: 32, accountId: 'REPORT-A' }];
    axios.get.mockResolvedValueOnce(success(rows));
    axios.get.mockRejectedValueOnce(new Error('Unavailable'));

    const result = await fetchIbkrStagedOrders({ batchId: 32 });

    expect(axios.get).toHaveBeenNthCalledWith(1, '/api/sync/ibkr/orders', { params: { batchId: 32 } });
    expect(result).toEqual({ status: 'SUCCESS', data: rows, accountInfoUnavailable: true });
  });
});
