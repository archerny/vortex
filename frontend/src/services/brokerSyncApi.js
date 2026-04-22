import axios from 'axios';

const BASE_URL = '/api/broker-sync';

/**
 * 查询同步批次列表
 * GET /api/broker-sync/batches
 *
 * @param {Object} params - 筛选参数
 * @param {string} [params.brokerCode] - 券商标识筛选
 * @param {string} [params.status] - 状态筛选。v2 应用产生的状态为
 *   PENDING / PROCESSING / COMPLETED / FAILED / CLEANUP_FAILED；
 *   历史批次可能仍为 PARTIAL / INTERRUPTED（后端仍可按其筛选，但 UI 不再作为独立入口）。
 */
export const fetchSyncBatches = async (params = {}) => {
  const response = await axios.get(`${BASE_URL}/batches`, { params });
  return response.data;
};

/**
 * 查询单个同步批次详情
 * GET /api/broker-sync/batches/{id}
 *
 * @param {number} id - 批次 ID
 */
export const fetchSyncBatchById = async (id) => {
  const response = await axios.get(`${BASE_URL}/batches/${id}`);
  return response.data;
};

/**
 * 触发券商同步
 * POST /api/broker-sync/trigger
 *
 * @param {Object} data - 同步请求参数
 * @param {string} data.brokerCode - 券商技术标识 (如 ibkr, tiger)
 * @param {string} [data.startTime] - 起始日期 (yyyy-MM-dd)
 * @param {string} [data.endTime] - 截止日期 (yyyy-MM-dd)
 */
export const triggerSync = async (data) => {
  const response = await axios.post(`${BASE_URL}/trigger`, data);
  return response.data;
};

/**
 * 查询支持的券商列表
 * GET /api/broker-sync/brokers
 *
 * @returns {Promise<{data: Array<{brokerCode: string, brokerName: string, country: string, brokerId: number}>}>}
 */
export const fetchSupportedBrokers = async () => {
  const response = await axios.get(`${BASE_URL}/brokers`);
  return response.data;
};
