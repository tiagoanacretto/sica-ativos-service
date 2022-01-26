# sica-ativos-service
API do módulo de ativos do projeto SICA

http://localhost:8080/swagger-ui.html

## Compilação

`mvn clean install`

## Rodar localmente

`mvn spring-boot:run`

## Rodar a partir de imagem docker

`docker run -p 8080:8080 sicapoc.azurecr.io/sica-ativos-service`

## Variáveis de ambiente disponíveis

| Env-var | Default |
| --- | --- |
| SERVER_PORT | 8080 |
| DATABASE_URL | jdbc:postgresql://localhost:5434/ativos-service |
| DATABASE_USERNAME | postgres |
| DATABASE_PASSWORD | 123 |
| AUTH_URL | http://localhost:8081/auth |