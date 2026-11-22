#!/usr/bin/env python3
"""Verify account profile migration and HTTP using a disposable cluster.

Run the documented backend unit tests first (provides compiled code/classpath).
Never reads application-local/dev properties or connects to an existing cluster.
"""
import os
from pathlib import Path
import shutil
import socket
import subprocess
import tempfile
import xml.etree.ElementTree as ET


def main():
    root = Path(__file__).resolve().parents[1]
    env = {**os.environ, "TMPDIR": "/tmp"}
    for name in ("java", "javac", "pg_config"):
        if not shutil.which(name):
            raise RuntimeError(f"Required existing tool is missing: {name}")
    bindir = Path(subprocess.check_output(["pg_config", "--bindir"], text=True).strip())
    for name in ("initdb", "pg_ctl"):
        if not os.access(bindir / name, os.X_OK):
            raise RuntimeError(f"Required existing tool is missing: {bindir / name}")
    report = root / "backend/target/surefire-reports/TEST-com.vortex.service.BrokerAccountServiceTest.xml"
    suite = ET.parse(report).getroot()
    if any(int(suite.get(name, "0")) for name in ("failures", "errors", "skipped")):
        raise RuntimeError("Run the documented backend checks successfully first")
    classpath = suite.find("./properties/property[@name='java.class.path']").get("value")
    for source in (root / "backend/src/main/java").rglob("*.java"):
        compiled = root / "backend/target/classes" / source.relative_to(root / "backend/src/main/java").with_suffix(".class")
        if not compiled.exists() or source.stat().st_mtime > compiled.stat().st_mtime:
            raise RuntimeError(f"Compile current backend code before running: {source.relative_to(root)}")
    scratch = Path(tempfile.mkdtemp(prefix="vortex-account-profiles-", dir="/tmp"))
    data = scratch / "data"
    try:
        subprocess.run([str(bindir / "initdb"), "-D", str(data), "-U", "vortex_profile_check",
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
        compiled = subprocess.run(["javac", "-cp", classpath, "-d", str(classes), "e2e/AccountProfilesCheck.java"],
                                  cwd=root, env=env)
        if compiled.returncode:
            raise RuntimeError(f"Integration checker compilation exited {compiled.returncode}")
        result = subprocess.run(["java", "-Djava.io.tmpdir=/tmp", "-cp", str(classes) + os.pathsep + classpath,
                                 "AccountProfilesCheck", str(port), str(data)], cwd=root, env=env)
        if result.returncode:
            raise RuntimeError(f"Account-profile integration check exited {result.returncode}")
    finally:
        if (data / "postmaster.pid").exists():
            subprocess.run([str(bindir / "pg_ctl"), "-D", str(data), "-m", "fast", "-w", "-t", "20", "stop"],
                           cwd=root, env=env, check=True)
        shutil.rmtree(scratch)
        print("Temporary PostgreSQL cluster stopped and removed.", flush=True)


if __name__ == "__main__":
    main()
