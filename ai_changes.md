COMMIT_MESSAGE: Replace notification and biometric endpoints with CRM endpoint

## Features Added
- Added `GET /api/v1/crm`, an API-key-protected CRM system availability endpoint.
- Removed the standalone notification and biometric availability endpoints.
- Removed notification resources from the generic gym management routes.

## Files Modified
- `src/main/java/com/example/app/controller/GymManagementController.java` — removed `notifications` from supported route types.
- `src/main/resources/application.properties` — set the application port to `20790`; actuator health exposure was already configured.
- `start.sh` — uses the configurable `SERVER_PORT` defaulting to `20790`.
- `Dockerfile` — exposes and starts the service on port `20790`.
- `docker-compose.yml` — maps host port `20790` to container port `20790`.
- `README.md` — removed notification documentation, added CRM endpoint documentation, and updated the port.

## Files Added
- `src/main/java/com/example/app/controller/CrmController.java` — supplies the CRM availability endpoint.

## Files Removed
- `src/main/java/com/example/app/controller/NotificationController.java` — removed notification endpoint.
- `src/main/java/com/example/app/controller/BiometricController.java` — removed biometric endpoint.

## Secrets Moved
- None — no hardcoded secrets were found in Java source.

## DB URLs Resolved
- `jdbc:postgresql://localhost:5432/gen_f972652468a5` -> `jdbc:postgresql://localhost:5432/gen_f972652468a5` (already working).
- `jdbc:postgresql://postgres:5432/gym` -> `jdbc:postgresql://localhost:5432/gen_449e09f49e55_1` (pre-resolved fallback; the Compose-only service address remains unchanged for container networking).

## Compilation Result
PASSED — `./gradlew compileJava -q` and `./gradlew bootJar -q` completed successfully on Java 21.
