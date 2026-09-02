# Gym Management API
Single Spring Boot service for gym records including gyms, branches, members, trainers, workouts, classes, appointments, attendance, payments, and notifications.

## Run
`chmod +x start.sh && bash start.sh` starts on port 26795. Swagger: `/docs`; health: `/actuator/health`.

## Security
Create an API key with the configured `X-Admin-Key`, then submit it as `X-API-Key` on business endpoints. API keys are stored as SHA-256 hashes.

## Endpoints
`POST/GET/GET{id}/PUT/DELETE /api/v1/{gyms|branches|members|trainers|workouts|classes|appointments|attendance|payments|notifications}`. API key administration is at `/api/v1/api-keys`.

## Docker
`docker compose up --build`
