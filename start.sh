#!/usr/bin/env bash
set -euo pipefail
SERVER_PORT="${SERVER_PORT:-20790}"
./gradlew bootJar -q
exec java -jar build/libs/*.jar --server.port="$SERVER_PORT"
