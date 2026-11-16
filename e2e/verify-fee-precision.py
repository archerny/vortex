#!/usr/bin/env python3
"""Run fee migration checks in a fresh, disposable PostgreSQL cluster only."""
import os
from pathlib import Path
import shutil
import socket
import subprocess
import tempfile
import xml.etree.ElementTree as ET


def main():
    root = Path(__file__).resolve().parents[1]
    env = os.environ.copy()
    env["TMPDIR"] = "/tmp"
    env["MAVEN_OPTS"] = env.get("MAVEN_OPTS", "") + " -Djava.io.tmpdir=/tmp"
    for name in ("mvn", "java", "javac", "pg_config"):
        if not shutil.which(name):
            raise RuntimeError(f"Required existing tool is missing: {name}")
    bindir = Path(subprocess.check_output(["pg_config", "--bindir"], text=True).strip())
    for name in ("initdb", "pg_ctl"):
        if not os.access(bindir / name, os.X_OK):
            raise RuntimeError(f"Required existing tool is missing: {bindir / name}")

    # Surefire supplies the resolved project classpath; no extra Maven plugin is needed.
    subprocess.run(["mvn", "-o", "-f", "backend/pom.xml",
                    "-Dtest=TradeFeePrecisionTest", "test"], cwd=root, env=env, check=True)
    report = root / "backend/target/surefire-reports/TEST-com.vortex.sync.adapter.ibkr.TradeFeePrecisionTest.xml"
    classpath = ET.parse(report).find("./properties/property[@name='java.class.path']").get("value")
    scratch = Path(tempfile.mkdtemp(prefix="vortex-fee-precision-", dir="/tmp"))
    data = scratch / "data"
    try:
        subprocess.run([str(bindir / "initdb"), "-D", str(data), "-U", "vortex_fee_check",
                        "--no-locale", "--encoding=UTF8", "--auth-local=trust", "--auth-host=trust"],
                       cwd=root, env=env, check=True, stdout=subprocess.DEVNULL)
        with socket.socket() as reservation:
            reservation.bind(("127.0.0.1", 0))
            port = reservation.getsockname()[1]
        subprocess.run([str(bindir / "pg_ctl"), "-D", str(data), "-l", str(scratch / "postgres.log"),
                        "-o", f"-h 127.0.0.1 -p {port} -k {scratch} -c unix_socket_permissions=0700",
                        "-w", "-t", "20", "start"], cwd=root, env=env, check=True)
        classes = scratch / "classes"
        classes.mkdir()
        subprocess.run(["javac", "-cp", classpath, "-d", str(classes),
                        "e2e/FeePrecisionCheck.java"], cwd=root, env=env, check=True)
        subprocess.run(["java", "-Djava.io.tmpdir=/tmp", "-cp", str(classes) + os.pathsep + classpath,
                        "FeePrecisionCheck", str(port), str(data)], cwd=root, env=env, check=True)
    finally:
        # Never delete a cluster's data while its server might still be running.
        if (data / "postmaster.pid").exists():
            subprocess.run([str(bindir / "pg_ctl"), "-D", str(data), "-m", "fast", "-w", "-t", "20", "stop"],
                           cwd=root, env=env, check=True)
        shutil.rmtree(scratch)
        print("Temporary PostgreSQL cluster stopped and removed.", flush=True)


if __name__ == "__main__":
    main()
