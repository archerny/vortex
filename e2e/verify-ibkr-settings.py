#!/usr/bin/env python3
"""Real DB/API checks; --serve holds a synthetic UI for manual browser verification.

Uses only existing tools, owns its PostgreSQL cluster, and never loads project
profile credentials. Requires successful current backend tests for the classpath.
"""
import argparse
import json
import os
from pathlib import Path
import shutil
import socket
import subprocess
import tempfile
import threading
import time
import urllib.error
import urllib.parse
import urllib.request
import xml.etree.ElementTree as ET
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--serve', action='store_true')
    args = parser.parse_args()
    root = Path(__file__).resolve().parents[1]
    env = {**os.environ, 'TMPDIR': '/tmp'}
    for name in ('java', 'javac', 'pg_config', 'psql', 'node'):
        if not shutil.which(name):
            raise RuntimeError(f'Required existing tool is missing: {name}')
    bindir = Path(subprocess.check_output(['pg_config', '--bindir'], text=True).strip())
    suite = ET.parse(root / 'backend/target/surefire-reports/TEST-com.vortex.sync.adapter.ibkr.IbkrSyncSettingsServiceTest.xml').getroot()
    if any(int(suite.get(name, '0')) for name in ('failures', 'errors', 'skipped')):
        raise RuntimeError('Run current backend tests successfully first')
    classpath = suite.find("./properties/property[@name='java.class.path']").get('value')
    for source in (root / 'backend/src/main/java').rglob('*.java'):
        compiled = root / 'backend/target/classes' / source.relative_to(root / 'backend/src/main/java').with_suffix('.class')
        if not compiled.exists() or source.stat().st_mtime > compiled.stat().st_mtime:
            raise RuntimeError(f'Compile current code first: {source.relative_to(root)}')
    calls = []

    class Flex(BaseHTTPRequestHandler):
        def log_message(self, *unused):
            pass  # Never print credential URLs, even synthetic ones.

        def do_GET(self):
            query = urllib.parse.parse_qs(urllib.parse.urlsplit(self.path).query)
            calls.append(query)
            if self.path.startswith('/SendRequest'):
                body = '<FlexStatementResponse><Status>Success</Status><ReferenceCode>123456789</ReferenceCode></FlexStatementResponse>'
            else:
                body = '<FlexQueryResponse type="TCF"><FlexStatements count="1"><FlexStatement accountId="SYNTH-ACCOUNT"><TradeConfirms/></FlexStatement></FlexStatements></FlexQueryResponse>'
            self.send_response(200)
            self.end_headers()
            self.wfile.write(body.encode())

    scratch = Path(tempfile.mkdtemp(prefix='vortex-ibkr-settings-', dir='/tmp'))
    data = scratch / 'data'
    app = ui = None
    stub = ThreadingHTTPServer(('127.0.0.1', 0), Flex)
    threading.Thread(target=stub.serve_forever, daemon=True).start()
    try:
        subprocess.run([str(bindir / 'initdb'), '-D', str(data), '-U', 'vortex_settings_check', '--no-locale',
                        '--encoding=UTF8', '--auth-local=trust', '--auth-host=trust'], env=env, check=True, stdout=subprocess.DEVNULL)
        with socket.socket() as reserve:
            reserve.bind(('127.0.0.1', 0))
            port = reserve.getsockname()[1]
        subprocess.run([str(bindir / 'pg_ctl'), '-D', str(data), '-l', str(scratch / 'postgres.log'), '-o',
                        f'-h 127.0.0.1 -p {port} -k {scratch} -c unix_socket_permissions=0700', '-w', 'start'], env=env, check=True)
        classes = scratch / 'classes'
        classes.mkdir()
        subprocess.run(['javac', '-cp', classpath, '-d', str(classes), 'e2e/IbkrSettingsCheck.java'], cwd=root, env=env, check=True)
        with (scratch / 'app.log').open('w') as log:
            app = subprocess.Popen(['java', '-Djava.io.tmpdir=/tmp', '-cp', str(classes) + os.pathsep + classpath,
                                    'IbkrSettingsCheck', str(scratch), str(port), str(stub.server_port)],
                                   stdin=subprocess.PIPE, stdout=log, stderr=subprocess.STDOUT, cwd=root, env=env)
        for _ in range(600):
            if (scratch / 'ready').exists():
                break
            if app.poll() is not None:
                raise RuntimeError((scratch / 'app.log').read_text())
            time.sleep(.1)
        else:
            raise RuntimeError('Synthetic application startup timed out')
        api_port = int((scratch / 'ready').read_text())

        def api(method, path, body=None, expected=200):
            request = urllib.request.Request(f'http://127.0.0.1:{api_port}/api' + path, method=method,
                                            data=json.dumps(body).encode() if body is not None else None,
                                            headers={'Content-Type': 'application/json'})
            try:
                response = urllib.request.urlopen(request, timeout=15)
            except urllib.error.HTTPError as error:
                response = error
            text = response.read().decode()
            assert response.code == expected, (method, path, response.code, text)
            assert 'synthetic-token' not in text and 'v1:' not in text, 'Credential echoed in response'
            return json.loads(text).get('data')

        def sql(statement):
            return subprocess.check_output(['psql', '-XAt', '-h', '127.0.0.1', '-p', str(port), '-U',
                                            'vortex_settings_check', '-d', 'postgres', '-c', statement], text=True).strip()

        assert not api('GET', '/broker-sync/ibkr/settings')['ready']
        broker = api('POST', '/brokers', {'brokerName': 'SYNTH-API', 'country': 'US', 'isActive': True}, 201)
        payload = {'brokerId': broker['id'], 'queryId': '12345', 'token': 'synthetic-token-A&/#'}
        assert api('PUT', '/broker-sync/ibkr/settings', payload)['ready']
        assert sql("SELECT flex_token_ciphertext LIKE 'v1:%' AND flex_token_ciphertext NOT LIKE '%synthetic%' FROM ibkr_sync_settings") == 't'
        assert len(api('GET', '/broker-sync/brokers')) == 1
        payload.pop('token')
        assert api('PUT', '/broker-sync/ibkr/settings', payload)['tokenConfigured']
        batch = api('POST', '/broker-sync/trigger', {'brokerCode': 'ibkr', 'startTime': '2026-01-01', 'endTime': '2026-01-02'})
        api('PUT', '/broker-sync/ibkr/settings', payload, 409)
        api('DELETE', '/broker-sync/ibkr/settings', expected=409)
        disabled = {**broker, 'brokerCode': 'ibkr', 'isActive': False}
        api('PUT', f"/brokers/{broker['id']}", disabled, 409)
        api('DELETE', f"/brokers/{broker['id']}", expected=409)
        for _ in range(150):
            current = api('GET', f"/broker-sync/batches/{batch['id']}")
            if current['status'] not in ('PENDING', 'PROCESSING'):
                break
            time.sleep(.1)
        assert current['status'] == 'COMPLETED', current
        assert len(calls) == 2 and all(call['t'] == ['synthetic-token-A&/#'] for call in calls)
        assert calls[0]['q'] == ['12345'] and calls[1]['q'] == ['123456789']
        key = scratch / 'keys/ibkr.key'
        backup = key.with_suffix('.saved')
        key.rename(backup)
        api('GET', '/broker-sync/ibkr/settings', expected=500)
        api('PUT', '/broker-sync/ibkr/settings', {**payload, 'token': 'synthetic-token-replacement'}, 500)
        assert not key.exists(), 'Missing key silently replaced'
        backup.rename(key)
        assert api('GET', '/broker-sync/ibkr/settings')['ready']
        assert not api('DELETE', '/broker-sync/ibkr/settings')['ready']
        api('DELETE', f"/brokers/{broker['id']}")
        # Preserve another broker to verify an IBKR settings failure cannot hide it in the UI.
        api('POST', '/brokers', {'brokerName': 'SYNTH-TIGER', 'country': 'US', 'isActive': True, 'brokerCode': 'tiger'}, 201)
        logs = (scratch / 'app.log').read_text()
        assert 'synthetic-token-' not in logs, 'Token in backend log'
        for line in logs.splitlines():
            if line.startswith('PASS '):
                print(line, flush=True)
        print('PASS real HTTP configuration/binding, ciphertext, retained Token, empty TCF sync, active-batch guards and lost-key recovery', flush=True)
        if args.serve:
            with (scratch / 'ui.log').open('w') as log:
                ui = subprocess.Popen(['node', 'e2e/ibkr-settings-ui-server.mjs', str(api_port)], cwd=root, env=env, stdout=log, stderr=subprocess.STDOUT)
            print('SYNTHETIC UI http://127.0.0.1:4318/#/sync-management (press Enter to stop)', flush=True)
            input()
    finally:
        if ui:
            ui.terminate(); ui.wait(timeout=10)
        if app and app.poll() is None:
            app.communicate(b'\n', timeout=20)
        stub.shutdown(); stub.server_close()
        if (data / 'postmaster.pid').exists():
            subprocess.run([str(bindir / 'pg_ctl'), '-D', str(data), '-m', 'fast', '-w', '-t', '20', 'stop'], env=env, check=True)
        shutil.rmtree(scratch)
        print('Temporary application, key files and PostgreSQL cluster removed.', flush=True)


if __name__ == '__main__':
    main()
