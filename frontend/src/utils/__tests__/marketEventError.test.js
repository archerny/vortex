import { describe, expect, it } from 'vitest';
import { marketEventErrorMessage } from '../marketEventError';

describe('market event conflict feedback', () => {
  it('shows the blocking event, date, securities and server recovery reason', () => {
    const text = marketEventErrorMessage({ response: { data: {
      code: 'HISTORY_LOCKED', message: '请先撤销关联事件', blockingEventId: 7,
      blockingEventType: 'SYMBOL_CHANGE', eventDate: '2026-03-10', symbols: ['SYNTH-A', 'SYNTH-B'],
    } } }, '操作失败');
    expect(text).toContain('请先撤销关联事件');
    expect(text).toContain('#7'); expect(text).toContain('2026-03-10');
    expect(text).toContain('SYNTH-A、SYNTH-B');
  });
  it('keeps sync and system-row conflicts readable without a blocking event', () => {
    expect(marketEventErrorMessage({ response: { data: { code: 'SYNC_IN_PROGRESS', message: '请先完成同步', symbols: [] } } }, '失败'))
      .toBe('请先完成同步');
  });
  it('preserves ordinary API errors and network fallbacks', () => {
    expect(marketEventErrorMessage({ response: { data: { message: '证券代码无效' } } }, '失败')).toBe('证券代码无效');
    expect(marketEventErrorMessage(new Error('network'), '连接失败')).toBe('连接失败');
  });
});
