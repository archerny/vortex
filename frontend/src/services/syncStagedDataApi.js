import axios from 'axios';

/**
 * 原始同步数据查询 API
 *
 * 对应后端 IbkrStagedDataController，提供对 IBKR staging 表的只读访问，
 * 用于在前端展示原始同步数据以便审计和排查。
 */

const BASE_URL = '/api/sync';

/**
 * 查询 IBKR 原始订单（ibkr_staged_orders）
 * GET /api/sync/ibkr/orders
 * GET /api/sync/ibkr/orders?batchId=123
 *
 * @param {Object} params
 * @param {number} [params.batchId] - 可选批次 ID 过滤
 */
export const fetchIbkrStagedOrders = async (params = {}) => {
  const response = await axios.get(`${BASE_URL}/ibkr/orders`, { params });
  return response.data;
};

/**
 * 查询 IBKR 原始成交确认（ibkr_staged_trade_confirms）
 * GET /api/sync/ibkr/trade-confirms
 * GET /api/sync/ibkr/trade-confirms?batchId=123
 *
 * @param {Object} params
 * @param {number} [params.batchId] - 可选批次 ID 过滤
 */
export const fetchIbkrStagedTradeConfirms = async (params = {}) => {
  const response = await axios.get(`${BASE_URL}/ibkr/trade-confirms`, { params });
  return response.data;
};
