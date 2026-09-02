run:
	bash start.sh
build:
	./gradlew bootJar
compose-up:
	docker compose up --build
