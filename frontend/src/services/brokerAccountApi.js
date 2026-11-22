import axios from 'axios';

const URL = '/api/broker-accounts';
export const fetchBrokerAccounts = async (brokerId) => (await axios.get(URL, { params: brokerId ? { brokerId } : {} })).data;
export const createBrokerAccount = async (values) => (await axios.post(URL, values)).data;
export const updateBrokerAccount = async (id, values) => (await axios.put(`${URL}/${id}`, values)).data;
export const deleteBrokerAccount = async (id) => (await axios.delete(`${URL}/${id}`)).data;

export const accountLabel = (account) => `${account.broker?.brokerName || account.brokerName || '券商'} · ${account.accountName} · ${account.accountId || '待补全账户ID'}`;
