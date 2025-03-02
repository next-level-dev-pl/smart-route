## DOCKER COMPOSE DEPENDENCY

### Requirement
Basic knowledge about [Docker technolog](https://docs.docker.com/get-started/introduction/).

## Docker Compose as a Local Orchestration Tool

Docker Compose is a utility for orchestrating Docker containers in a local development environment when working with a Spring Boot application. It helps by:

✅ Defining multiple services (e.g., database) in a single docker-compose.yml file.

✅ Managing dependencies (e.g., ensuring PostgreSQL starts before the Spring Boot app).

✅ Providing an isolated environment without affecting your system’s global state.

✅ Simplifying setup with a single command (docker-compose up).

## How Does It Work?

### Without Docker Compose

If you don't use Docker Compose, you have to remember to start the database manually before main app using command like this:

```dockerfile
docker run -d --name my_postgres_container \
-p 5432:5432 \
-e POSTGRES_DB=myExtraSecretDbName \
-e POSTGRES_USER=myExtraSecretUsername \
-e POSTGRES_PASSWORD=myExtraSecretPassword \
postgres:16.3
```

Your Spring Boot application (running outside Docker) would then connect to this database using:
```yaml
datasource:
    url: jdbc:postgresql://localhost:5432/myExtraSecretDbName
```

### With Docker Compose
Instead of running the command manually, you can define the database service in a docker-compose.yml file and then, start PostgreSQL with a single command:
```md
docker-compose up -d
```
Now, your Spring Boot application can connect to the database just like before, but you don’t have to worry about manually starting or stopping PostgreSQL.

### What is even better?
Spring Boot 3.1+ introduced built-in support for Docker Compose. 
You can enable this feature in your application.yml:

```yaml
  docker:
    compose:
      enabled: true
      file: docker-compose.yml
```

With this config docker compose will start automatically when you start your main app
even without command <i> docker-compose up -d</i> . 
Your app will handle everything. Just start your Spring Boot app as usual using your IDE or Gradle/Maven commands.
