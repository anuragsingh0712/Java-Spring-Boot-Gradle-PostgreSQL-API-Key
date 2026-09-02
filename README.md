# Gym Management API
Single Spring Boot service for gym records including gyms, branches, members, trainers, workouts, classes, appointments, attendance, and payments. A CRM availability endpoint is also provided.

## Run
`chmod +x start.sh && bash start.sh` starts on port 20790. Swagger: `/docs`; health: `/actuator/health`.

## Security
Create an API key with the configured `X-Admin-Key`, then submit it as `X-API-Key` on business endpoints. API keys are stored as SHA-256 hashes.

## Endpoints
`POST/GET/GET{id}/PUT/DELETE /api/v1/{gyms|branches|members|trainers|workouts|classes|appointments|attendance|payments}`. CRM availability is at `GET /api/v1/crm`. API key administration is at `/api/v1/api-keys`.

## Docker
`docker compose up --build`
