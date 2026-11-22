import axios from 'axios';
import { syncConnectionConfig } from '../constants/syncConnectionConfig';

const URL = '/api/broker-sync/connections';
export const fetchSyncConnections = async () => (await axios.get(URL)).data;
export const fetchSyncConnection = async (brokerId) => (await axios.get(`${URL}/${brokerId}`)).data;
export const clearSyncConnection = async (brokerId) => (await axios.delete(`${URL}/${brokerId}`)).data;

export const saveSyncConnection = async (brokerId, brokerCode, values) => {
  const credentials = {};
  for (const field of syncConnectionConfig[brokerCode].fields) {
    const value = values[field.name]?.trim();
    if (value || !field.secret) credentials[field.name] = value || '';
  }
  return (await axios.put(`${URL}/${brokerId}`, { brokerCode, credentials })).data;
};

export const connectionError = (error, fallback = '连接配置操作失败，请稍后重试') => error?.response?.data?.message || fallback;
