import { describe, it, expect, vi } from 'vitest';
import axios from 'axios';
import { fetchTradeRecordById, createTradeRecord, updateTradeRecord } from '../tradeRecordApi';
import { feeForInput, feeText } from '../../utils/tradeFee';

vi.mock('axios', () => ({ default: { get: vi.fn(), post: vi.fn(), put: vi.fn() } }));

describe('trade fee precision', () => {
  it('preserves the exact fee when editing another field after JSON parsing', async () => {
    const exact = '123456789012.1234567890123456789';
    const response = JSON.parse(`{"status":"SUCCESS","data":{"id":42,"fee":${exact},"feeExact":"${exact}"}}`);
    axios.get.mockResolvedValueOnce({ data: response });
    axios.put.mockResolvedValueOnce({ data: { status: 'SUCCESS' } });

    const { data: record } = await fetchTradeRecordById(42);
    const inputFee = feeForInput(record);
    const displayedFee = feeText(record);
    await updateTradeRecord(record.id, { symbol: 'SYNTH-UPDATED', fee: inputFee });

    expect(inputFee).toBe(exact);
    expect(displayedFee).toBe(exact);
    expect(inputFee).toBe(displayedFee);
    expect(axios.put).toHaveBeenLastCalledWith('/api/trade-records/42', {
      symbol: 'SYNTH-UPDATED', fee: exact,
    });
    expect(response.data.feeExact).toBe(exact);
  });

  it('sends newly entered fees without converting them to binary floating point', async () => {
    const fee = '0.0000000001234567890123456789';
    axios.post.mockResolvedValueOnce({ data: { status: 'SUCCESS' } });
    await createTradeRecord({ symbol: 'SYNTH', fee });
    expect(axios.post).toHaveBeenLastCalledWith('/api/trade-records', { symbol: 'SYNTH', fee });
  });

  it.each([
    [{ fee: 1.25 }, '1.25'],
    [{ fee: 0, feeExact: '0.00000000' }, '0.00000000'],
    [{ fee: null }, '0'],
    [{ fee: '1.12345678' }, '1.12345678'],
  ])('supports zero and legacy fee responses: %j', (record, expected) => {
    expect(feeForInput(record)).toBe(expected);
  });
});
