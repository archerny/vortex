import { triggerRefTypeMap } from '../constants/tradeConstants';

export const marketEventErrorMessage = (error, fallback) => {
  const data = error.response?.data;
  const reason = data?.message || fallback;
  if (!data?.code) return reason;
  const details = [
    data.blockingEventId != null && `${triggerRefTypeMap[data.blockingEventType] || '市场事件'} #${data.blockingEventId}`,
    data.eventDate,
    Array.isArray(data.symbols) && data.symbols.join('、'),
  ].filter(Boolean);
  return details.length ? `${reason}（${details.join(' · ')}）` : reason;
};
