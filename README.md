# Extension Platform

Spring Boot 4 modular monolith implementing a DDD-style staff self-registration module for extension project submission and evaluation flows.

## Overview

This project currently provides:

- `POST /api/staff` to self-register faculty and administrative technical staff
- `GET /api/staff` to list registered staff after authentication
- in-memory persistence using static `Map` structures
- Swagger UI for manual API testing
- BCrypt password hashing before persistence
- default registration roles for proposal submission and review

The registration flow validates:

- required full name, CPF, institutional email, password, campus, education area, and academic degree
- optional social name, gender, Lattes link, and phone
- normalized CPF format with exactly 11 digits
- normalized lowercase email
- password with at least 6 characters
- uniqueness of CPF and email in local in-memory storage
- automatic assignment of `ROLE_COORDINATOR` and `ROLE_REVIEWER`

## Architecture

The code is organized as a modular monolith with DDD-inspired layers:

- `domain`: entities, value-oriented domain objects, enums, repository contract
- `application`: use-case service and business exceptions
- `infrastructure`: in-memory repository implementation
- `interfaces/rest`: HTTP controller, request/response DTOs, exception handler

Main module path:

- [src/main/java/com/testsquad/crud/modules/staffregistration](src/main/java/com/testsquad/crud/modules/staffregistration)

## Requirements

- JDK 17 or higher
- Maven 3.9+

Check your environment:

```powershell
java -version
javac -version
mvn -v
```

## Running the Project

From the project root:

```powershell
mvn spring-boot:run
```

The API will start on:

- `http://localhost:8080`

## Running Tests

```powershell
mvn test
```

## Swagger

After the application starts, open:

- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/swagger-ui/index.html`

OpenAPI raw document:

- `http://localhost:8080/v3/api-docs`

## API Usage

### Create Staff Registration

Request:

```http
POST /api/staff
Content-Type: application/json
```

Example body:

```json
{
  "fullName": "Ana Pereira",
  "socialName": "Ana",
  "cpf": "321.654.987-00",
  "email": "ana.pereira@if.edu.br",
  "password": "secret123",
  "staffType": "FACULTY",
  "campus": "North Zone Campus",
  "educationArea": "Education",
  "academicDegree": "DOCTORATE",
  "gender": "OTHER",
  "lattesLink": "https://lattes.cnpq.br/1234567890123456",
  "phone": "+55 83 99999-9999"
}
```

Example `curl`:

```powershell
curl -X POST http://localhost:8080/api/staff `
  -H "Content-Type: application/json" `
  -d "{\"fullName\":\"Ana Pereira\",\"socialName\":\"Ana\",\"cpf\":\"321.654.987-00\",\"email\":\"ana.pereira@if.edu.br\",\"password\":\"secret123\",\"staffType\":\"FACULTY\",\"campus\":\"North Zone Campus\",\"educationArea\":\"Education\",\"academicDegree\":\"DOCTORATE\",\"gender\":\"OTHER\",\"lattesLink\":\"https://lattes.cnpq.br/1234567890123456\",\"phone\":\"+55 83 99999-9999\"}"
```

Successful response:

```json
{
  "message": "Registration completed successfully. You can now sign in.",
  "redirectTo": "/login",
  "staff": {
    "id": "generated-uuid",
    "fullName": "Ana Pereira",
    "socialName": "Ana",
    "cpf": "32165498700",
    "email": "ana.pereira@if.edu.br",
    "gender": "OTHER",
    "lattesLink": "https://lattes.cnpq.br/1234567890123456",
    "phone": "+55 83 99999-9999",
    "staffType": "FACULTY",
    "campus": "North Zone Campus",
    "academicDegree": "DOCTORATE",
    "educationArea": "Education",
    "roles": [
      "ROLE_COORDINATOR",
      "ROLE_REVIEWER"
    ]
  }
}
```

### List Registered Staff

Request:

```http
GET /api/staff
```

This endpoint is protected. You must first register a user and then authenticate with that registered email and password.

Example with `curl`:

```powershell
curl -u ana.pereira@if.edu.br:secret123 http://localhost:8080/api/staff
```

You can also use the browser login page:

- `http://localhost:8080/login`

## Enum Values

`staffType`

- `FACULTY`
- `ADMINISTRATIVE_TECHNICAL`

`academicDegree`

- `BACHELOR`
- `SPECIALIZATION`
- `MASTERS`
- `DOCTORATE`
- `POSTDOCTORATE`

`gender`

- `FEMALE`
- `MALE`
- `OTHER`
- `PREFER_NOT_TO_SAY`

## Error Cases

The API returns `400 Bad Request` for invalid input, duplicate CPF, or duplicate email.

Examples:

- `CPF already registered: 12345678901`
- `Email already registered: ana.pereira@if.edu.br`
- `CPF must contain 11 digits`
- `Invalid email`
- `Password must contain at least 6 characters`
- `Lattes link must start with http:// or https://`

## Notes

- Persistence is intentionally local and in-memory for this issue.
- Restarting the application clears all registered data.
- The project disables datasource and JPA auto-configuration because no database is used yet.
- The current project exposes a REST API, so the "redirect to login" acceptance criterion is represented by the `redirectTo` field in the success response.
