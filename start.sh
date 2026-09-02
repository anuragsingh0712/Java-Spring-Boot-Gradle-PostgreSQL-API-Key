#!/usr/bin/env bash
set -euo pipefail
SERVER_PORT=26795
./gradlew bootJar -q
exec java -jar build/libs/*.jar --server.port=26795
