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
    parser.add_argument('--connections-only', action='store_true')
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
    request_started = threading.Event()
    release_request = threading.Event()

    class Flex(BaseHTTPRequestHandler):
        def log_message(self, *unused):
            pass  # Never print credential URLs, even synthetic ones.

        def do_GET(self):
            query = urllib.parse.parse_qs(urllib.parse.urlsplit(self.path).query)
            calls.append(query)
            if self.path.startswith('/SendRequest'):
                request_started.set()
                if not release_request.wait(15):
                    self.send_error(504)
                    return
                body = '<FlexStatementResponse><Status>Success</Status><ReferenceCode>123456789</ReferenceCode></FlexStatementResponse>'
            else:
                identity = 'SYNTH-IBKR-B' if args.connections_only and query.get('t') == ['synthetic-token-B'] else 'SYNTH-ACCOUNT'
                body = f'<FlexQueryResponse type="TCF"><FlexStatements count="1"><FlexStatement accountId="{identity}"><TradeConfirms/></FlexStatement></FlexStatements></FlexQueryResponse>'
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
        checker = 'AccountConnectionsCheck' if args.connections_only else 'IbkrSettingsCheck'
        subprocess.run(['javac', '-cp', classpath, '-d', str(classes), f'e2e/{checker}.java'], cwd=root, env=env, check=True)
        with (scratch / 'app.log').open('w') as log:
            app = subprocess.Popen(['java', '-Djava.io.tmpdir=/tmp', '-cp', str(classes) + os.pathsep + classpath,
                                    checker, str(scratch), str(port), str(stub.server_port)],
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
            assert all(secret not in text for secret in ('synthetic-token', 'synthetic-private-key', 'v1:')), 'Credential echoed in response'
            return json.loads(text).get('data')

        def sql(statement):
            return subprocess.check_output(['psql', '-XAt', '-h', '127.0.0.1', '-p', str(port), '-U',
                                            'vortex_settings_check', '-d', 'postgres', '-c', statement], text=True).strip()

        if args.connections_only:
            rows = api('GET', '/broker-sync/connections')
            assert len(rows) == 5
            tiger = next(row for row in rows if row['accountId'] == 'SYNTH-TIGER-A')
            tiger_other = next(row for row in rows if row['accountId'] == 'SYNTH-TIGER-B')
            ibkr = next(row for row in rows if row['accountId'] == 'SYNTH-IBKR-A')
            ibkr_other = next(row for row in rows if row['accountId'] == 'SYNTH-IBKR-B')
            assert all(row['ready'] for row in (tiger, tiger_other, ibkr, ibkr_other))
            assert 'privateKey' not in tiger['values'] and 'token' not in ibkr['values']
            update = {'brokerCode': 'tiger', 'credentials': {'tigerId': '300'}}
            assert api('PUT', f"/broker-sync/connections/{tiger['brokerAccountId']}", update)['ready']
            assert sql(f"SELECT credentials->>'privateKey'='synthetic-private-key-A' AND credentials->>'tigerId'='300' FROM broker_sync_connections WHERE broker_account_id={tiger['brokerAccountId']}") == 't'
            assert sql(f"SELECT credentials->>'privateKey'='synthetic-private-key-B' AND credentials->>'tigerId'='200' FROM broker_sync_connections WHERE broker_account_id={tiger_other['brokerAccountId']}") == 't'
            assert not api('DELETE', f"/broker-sync/connections/{ibkr['brokerAccountId']}")['configured']
            assert api('GET', f"/broker-sync/connections/{ibkr_other['brokerAccountId']}")['ready']
            api('GET', '/broker-sync/ibkr/settings', expected=400)
            api('POST', '/broker-sync/trigger', {'brokerCode': 'ibkr'}, expected=400)
            api('POST', '/broker-sync/trigger', {'brokerCode': 'ibkr', 'brokerAccountId': 999999}, expected=400)
            assert not calls, 'Local configuration or validation contacted a broker'
            # A migrated account can remain pending while the user renames or disables it.
            pending_id = int(sql(f"INSERT INTO broker_accounts(broker_id,account_name,account_id) VALUES ({ibkr['brokerId']},'Synthetic pending',NULL) RETURNING id").splitlines()[0])
            pending = api('PUT', f'/broker-accounts/{pending_id}', {'brokerId': ibkr['brokerId'], 'accountName': 'Pending disabled', 'accountId': '', 'isActive': False})
            assert pending['accountId'] is None and not pending['isActive']
            api('POST', '/broker-sync/trigger', {'brokerCode': 'ibkr', 'brokerAccountId': pending_id}, expected=400)
            pending['accountId'] = 'SYNTH-PENDING'; pending['isActive'] = True
            pending = api('PUT', f'/broker-accounts/{pending_id}', pending)
            api('PUT', f'/broker-accounts/{pending_id}', {**pending, 'accountId': ''}, expected=400)
            api('DELETE', f'/broker-accounts/{pending_id}')
            new_account = {'brokerId': ibkr['brokerId'], 'accountId': 'SYNTH-IBKR-C', 'accountName': 'Synthetic third account', 'isActive': True}
            saved = api('POST', '/broker-accounts', new_account, 201)
            api('POST', '/broker-accounts', new_account, 409)
            saved['accountName'] = 'Synthetic renamed account'
            assert api('PUT', f"/broker-accounts/{saved['id']}", saved)['accountName'] == saved['accountName']
            api('DELETE', f"/broker-accounts/{saved['id']}")
            trade = {'brokerAccountId': ibkr['brokerAccountId'], 'tradeDate': '2026-01-01', 'assetType': 'STOCK',
                     'symbol': 'SYNTH-HTTP-TRADE', 'underlyingSymbol': 'SYNTH-HTTP-TRADE', 'tradeType': 'BUY',
                     'quantity': 1, 'price': '10', 'fee': '0.123456789', 'currency': 'USD'}
            saved_trade = api('POST', '/trade-records', trade, 201)
            assert saved_trade['brokerAccount']['accountId'] == 'SYNTH-IBKR-A'
            trade['brokerAccountId'] = ibkr_other['brokerAccountId']
            moved_trade = api('PUT', f"/trade-records/{saved_trade['id']}", trade)
            assert moved_trade['brokerAccountId'] == ibkr_other['brokerAccountId'] and moved_trade['brokerAccount']['accountId'] == 'SYNTH-IBKR-B'
            api('POST', '/trade-records', {**trade, 'brokerId': tiger['brokerId']}, 400)
            assert all(row['brokerAccountId'] == ibkr_other['brokerAccountId'] for row in api('GET', f"/trade-records?brokerAccountId={ibkr_other['brokerAccountId']}"))
            cash = api('POST', '/cash-flow-records', {'brokerAccountId': ibkr_other['brokerAccountId'], 'recordDate': '2026-01-01', 'recordType': 'DEPOSIT', 'amount': '1.23', 'currency': 'USD'}, 201)
            assert cash['brokerAccount']['accountId'] == 'SYNTH-IBKR-B'
            assert all(row['brokerAccountId'] == ibkr_other['brokerAccountId'] for row in api('GET', f"/cash-flow-records?brokerAccountId={ibkr_other['brokerAccountId']}"))
            api('DELETE', f"/broker-accounts/{ibkr_other['brokerAccountId']}", expected=409)
            release_request.set()
            batch = api('POST', '/broker-sync/trigger', {'brokerCode': 'ibkr', 'brokerAccountId': ibkr_other['brokerAccountId'], 'startTime': '2026-01-01', 'endTime': '2026-01-02'})
            assert batch['brokerAccountId'] == ibkr_other['brokerAccountId'] and batch['accountId'] == 'SYNTH-IBKR-B'
            for _ in range(200):
                current = api('GET', f"/broker-sync/batches/{batch['id']}")
                if current['status'] not in ('PENDING', 'PROCESSING'): break
                time.sleep(.1)
            assert current['status'] == 'COMPLETED', current
            assert len(calls) == 2 and all(call['t'] == ['synthetic-token-B'] for call in calls)
            assert calls[0]['q'] == ['456'] and calls[1]['q'] == ['123456789']
            logs = (scratch / 'app.log').read_text()
            assert not any(secret in logs for secret in ('synthetic-token-A', 'synthetic-token-B', 'synthetic-private-key'))
            for line in logs.splitlines():
                if line.startswith('PASS '): print(line, flush=True)
            print('PASS focused account CRUD HTTP, secret retention and account isolation, ambiguity validation and selected-account async Flex request', flush=True)
            return

        assert not api('GET', '/broker-sync/ibkr/settings')['ready']
        broker = api('POST', '/brokers', {'brokerName': 'SYNTH-API', 'country': 'US', 'brokerCode': 'ibkr'}, 201)
        account = api('POST', '/broker-accounts', {'brokerId': broker['id'], 'accountName': 'Synthetic IBKR account', 'accountId': 'SYNTH-ACCOUNT', 'isActive': True}, 201)
        payload = {'brokerId': broker['id'], 'queryId': '12345', 'token': 'synthetic-token-A&/#'}
        assert api('PUT', '/broker-sync/ibkr/settings', payload)['ready']
        assert sql("SELECT credentials->>'token' = 'synthetic-token-A&/#' FROM broker_sync_connections WHERE broker_code='ibkr'") == 't'
        assert len(api('GET', '/broker-sync/brokers')) == 1
        payload.pop('token')
        assert api('PUT', '/broker-sync/ibkr/settings', payload)['tokenConfigured']
        batch = api('POST', '/broker-sync/trigger', {'brokerCode': 'ibkr', 'startTime': '2026-01-01', 'endTime': '2026-01-02'})
        assert request_started.wait(10), 'The first request did not capture its credential pair'
        try:
            assert api('PUT', '/broker-sync/ibkr/settings', payload)['ready']
            assert not api('DELETE', '/broker-sync/ibkr/settings')['ready']
            assert api('PUT', '/broker-sync/ibkr/settings', {**payload, 'token': 'synthetic-token-B'})['ready']
            disabled = {**account, 'isActive': False}
            assert not api('PUT', f"/broker-accounts/{account['id']}", disabled)['isActive']
            api('PUT', f"/broker-accounts/{account['id']}", {**disabled, 'isActive': True})
            # Deleting a separate bound account during the batch is also allowed.
            temporary = api('POST', '/brokers', {'brokerName': 'SYNTH-DELETE', 'country': 'US', 'brokerCode': 'tiger'}, 201)
            api('DELETE', f"/brokers/{temporary['id']}")
        finally:
            release_request.set()
        for _ in range(150):
            current = api('GET', f"/broker-sync/batches/{batch['id']}")
            if current['status'] not in ('PENDING', 'PROCESSING'):
                break
            time.sleep(.1)
        assert current['status'] == 'COMPLETED', current
        assert len(calls) == 2 and all(call['t'] == ['synthetic-token-A&/#'] for call in calls)
        assert calls[0]['q'] == ['12345'] and calls[1]['q'] == ['123456789']
        assert sql("SELECT credentials->>'token' = 'synthetic-token-B' FROM broker_sync_connections WHERE broker_code='ibkr'") == 't'
        # The next request reads the replacement plaintext, while the first report kept its captured pair.
        next_batch = api('POST', '/broker-sync/trigger', {'brokerCode': 'ibkr', 'startTime': '2026-01-03', 'endTime': '2026-01-04'})
        for _ in range(150):
            current = api('GET', f"/broker-sync/batches/{next_batch['id']}")
            if current['status'] not in ('PENDING', 'PROCESSING'):
                break
            time.sleep(.1)
        assert current['status'] == 'COMPLETED', current
        assert len(calls) == 4 and all(call['t'] == ['synthetic-token-B'] for call in calls[2:])
        assert api('GET', '/broker-sync/ibkr/settings')['ready']
        assert not api('DELETE', '/broker-sync/ibkr/settings')['ready']
        # Completed batches retain account ownership; the owned cluster removes these at exit.
        api('DELETE', f"/broker-accounts/{account['id']}", expected=409)
        # Keep another broker available for manual UI checks.
        tiger_broker = api('POST', '/brokers', {'brokerName': 'SYNTH-TIGER', 'country': 'US', 'brokerCode': 'tiger'}, 201)
        api('POST', '/broker-accounts', {'brokerId': tiger_broker['id'], 'accountName': 'Synthetic Tiger account', 'accountId': 'SYNTH-TIGER', 'isActive': True}, 201)
        logs = (scratch / 'app.log').read_text()
        assert 'synthetic-token-' not in logs, 'Token in backend log'
        for line in logs.splitlines():
            if line.startswith('PASS '):
                print(line, flush=True)
        print('PASS plaintext HTTP settings, retained/replaced Token, active-batch edits, account changes and captured/next-request credentials', flush=True)
        if args.serve:
            with (scratch / 'ui.log').open('w') as log:
                ui = subprocess.Popen(['node', 'e2e/ibkr-settings-ui-server.mjs', str(api_port)], cwd=root, env=env, stdout=log, stderr=subprocess.STDOUT)
            print('SYNTHETIC UI http://127.0.0.1:4318/#/sync-management (press Enter to stop)', flush=True)
            input()
    finally:
        release_request.set()
        if ui:
            ui.terminate(); ui.wait(timeout=10)
        if app and app.poll() is None:
            app.communicate(b'\n', timeout=20)
        stub.shutdown(); stub.server_close()
        if (data / 'postmaster.pid').exists():
            subprocess.run([str(bindir / 'pg_ctl'), '-D', str(data), '-m', 'fast', '-w', '-t', '20', 'stop'], env=env, check=True)
        shutil.rmtree(scratch)
        print('Temporary application and PostgreSQL cluster removed.', flush=True)


if __name__ == '__main__':
    main()
