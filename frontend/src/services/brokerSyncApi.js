import axios from 'axios';

const BASE_URL = '/api/broker-sync';

/**
 * 查询同步批次列表
 * GET /api/broker-sync/batches
 *
 * @param {Object} params - 筛选参数
 * @param {string} [params.brokerName] - 券商名称筛选
 * @param {string} [params.status] - 状态筛选 (PENDING, IMPORTING, COMPLETED, FAILED)
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
 * @param {string} data.brokerName - 券商标识 (如 ibkr, tiger)
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
 */
export const fetchSupportedBrokers = async () => {
  const response = await axios.get(`${BASE_URL}/brokers`);
  return response.data;
};
