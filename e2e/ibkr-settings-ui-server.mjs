// Proxy to the launcher-owned synthetic application. Never uses the default backend.
import { createServer } from '../frontend/node_modules/vite/dist/node/index.js';
import { fileURLToPath } from 'node:url';
const port = Number(process.argv[2]);
if (!Number.isInteger(port) || port < 1024 || port > 65535) throw new Error('Explicit synthetic backend port required');
const server = await createServer({
  root: fileURLToPath(new URL('../frontend', import.meta.url)),
  configFile: fileURLToPath(new URL('../frontend/vite.config.js', import.meta.url)),
  server: { host: '127.0.0.1', port: 4318, strictPort: true, proxy: { '/api': { target: `http://127.0.0.1:${port}` } } },
});
await server.listen();
const stop = async () => { await server.close(); process.exit(0); };
process.on('SIGTERM', stop);
process.on('SIGINT', stop);
