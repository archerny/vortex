// Synthetic UI fixture server. No backend, database or broker access.
// Run from the checkout: node e2e/market-event-ui-server.mjs
import { createServer } from '../frontend/node_modules/vite/dist/node/index.js';
import { fileURLToPath } from 'node:url';

const kinds = ['stock-split', 'symbol-change', 'dividend-in-kind'];
const attempts = Object.fromEntries(kinds.map((kind) => [kind, 0]));
const removed = new Set();
const fixture = {
  id: 10, eventDate: '2026-03-10', symbol: 'SYNTH-A', oldSymbol: 'SYNTH-A', newSymbol: 'SYNTH-B',
  dividendSymbol: 'SYNTH-D', currency: 'USD', dividendCurrency: 'HKD', ratioFrom: 1, ratioTo: 2,
  processed: true, description: 'Synthetic UI verification only',
};
const server = await createServer({
  root: fileURLToPath(new URL('../frontend', import.meta.url)),
  configFile: fileURLToPath(new URL('../frontend/vite.config.js', import.meta.url)),
  server: { host: '127.0.0.1', port: 4317, strictPort: true },
  plugins: [{ name: 'synthetic-event-api', configureServer(vite) {
    vite.middlewares.use('/api', (req, res) => {
      res.setHeader('Content-Type', 'application/json; charset=utf-8');
      const kind = req.url.split('/')[2];
      if (req.url === '/test-state') return res.end(JSON.stringify({ attempts, removed: [...removed] }));
      if (req.url.startsWith('/market-events/') && kinds.includes(kind)) {
        if (req.method === 'GET') return res.end(JSON.stringify({ status: 'SUCCESS', data: removed.has(kind) ? [] : [fixture] }));
        if (req.method === 'DELETE' && ++attempts[kind] > 1) {
          removed.add(kind); return res.end(JSON.stringify({ status: 'SUCCESS', message: '事件已撤销' }));
        }
        res.statusCode = 409;
        return res.end(JSON.stringify({ status: 'ERROR', code: 'FOLLOWING_TRADES_EXIST', message: '合成校验：关联证券已有后续交易，请先回退。' }));
      }
      if (req.method !== 'GET') { res.statusCode = 405; return res.end('{}'); }
      res.end(JSON.stringify({ status: 'SUCCESS', data: [] }));
    });
  } }],
});
await server.listen();
server.printUrls();
const stop = async () => { await server.close(); process.exit(0); };
process.on('SIGTERM', stop);
process.on('SIGINT', stop);
