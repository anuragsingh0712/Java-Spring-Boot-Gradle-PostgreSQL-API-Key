@echo off
set SERVER_PORT=26795
call gradlew.bat bootJar -q
java -jar build\libs\app-0.1.0.jar --server.port=26795
