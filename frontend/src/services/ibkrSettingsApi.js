import axios from 'axios';

const URL = '/api/broker-sync/ibkr/settings';
export const fetchIbkrSettings = async () => (await axios.get(URL)).data;
export const saveIbkrSettings = async (values) => (await axios.put(URL, settingsPayload(values))).data;
export const clearIbkrSettings = async () => (await axios.delete(URL)).data;

// Omitted Token means retain the stored value. Never persist it in browser storage.
export const settingsPayload = ({ brokerId, queryId, token }) => ({
  brokerId,
  queryId: queryId?.trim(),
  ...(token?.trim() ? { token: token.trim() } : {}),
});

export const settingsError = (error, fallback = '配置操作失败，请稍后重试') => error?.response?.data?.message || fallback;
