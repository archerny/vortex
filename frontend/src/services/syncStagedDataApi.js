import axios from 'axios';
import { fetchSyncBatches } from './brokerSyncApi';

/**
 * 原始同步数据查询 API
 *
 * 对应后端 IbkrStagedDataController，提供对 IBKR staging 表的只读访问，
 * 用于在前端展示原始同步数据以便审计和排查。
 */

const BASE_URL = '/api/sync';

const fetchIbkrStagedRows = async (path, params) => {
  const [response, batches] = await Promise.all([
    axios.get(`${BASE_URL}/ibkr/${path}`, { params }),
    fetchSyncBatches({ brokerCode: 'ibkr' }).catch(() => null),
  ]);
  const result = response.data;
  if (!result.data?.length) return result;
  if (batches?.status !== 'SUCCESS') return { ...result, accountInfoUnavailable: true };

  const batchesById = new Map(batches.data.map((batch) => [batch.id, batch]));
  return {
    ...result,
    data: result.data.map((row) => {
      const batch = batchesById.get(row.batchId);
      return {
        ...row,
        syncAccountId: batch?.accountId,
        syncAccountName: batch?.brokerAccount?.accountName,
      };
    }),
  };
};

/**
 * 查询 IBKR 原始订单（ibkr_staged_orders）
 * GET /api/sync/ibkr/orders
 * GET /api/sync/ibkr/orders?batchId=123
 *
 * @param {Object} params
 * @param {number} [params.batchId] - 可选批次 ID 过滤
 */
export const fetchIbkrStagedOrders = (params = {}) => fetchIbkrStagedRows('orders', params);

/**
 * 查询 IBKR 原始成交确认（ibkr_staged_trade_confirms）
 * GET /api/sync/ibkr/trade-confirms
 * GET /api/sync/ibkr/trade-confirms?batchId=123
 *
 * @param {Object} params
 * @param {number} [params.batchId] - 可选批次 ID 过滤
 */
export const fetchIbkrStagedTradeConfirms = (params = {}) => fetchIbkrStagedRows('trade-confirms', params);
