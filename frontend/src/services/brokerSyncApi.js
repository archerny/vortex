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
