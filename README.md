# Price Query

> A modular Java application that returns product price information using Hexagonal Architecture and an in-memory H2 database.

[![Java](https://img.shields.io/badge/Java-21-blue)](#)
[![Maven](https://img.shields.io/badge/Maven-3.9.9-blue)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen)](#)
[![H2](https://img.shields.io/badge/Database-H2-informational)](#)

Table of Contents
- [About](#about)
- [Status](#status)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
    - [Run the application](#run-the-application)
    - [Run tests](#run-tests)
    - [H2 Console](#h2-console)
    - [API: Product Price (GET)](#api-product-price-get)
- [Error handling](#error-handling)
- [Architecture](#architecture)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)
- [Maintainers](#maintainers)
- [Acknowledgements](#acknowledgements)

## About

Price Query is a small modular service that retrieves product price information given a productId, brandId and an application date. It is implemented using Hexagonal (Ports & Adapters) / Clean Architecture principles so that domain logic is isolated from frameworks and infrastructure.

## Status

Stable for development and testing with in-memory data (H2). This repository contains a sample dataset loaded at startup for integration and manual testing.

## Features

- Hexagonal / Clean Architecture separation (Domain, Application, Infrastructure)
- H2 in-memory database with initial data (`data.sql`)
- REST API to lookup price by product, brand and application date
- Unit + integration tests using JUnit and Mockito

## Prerequisites

- Java 21
- Maven 3.9.9 (recommended: use the Maven Wrapper included in the repo)

## Installation

1. Clone the repository:

```bash
  git clone https://github.com/diegoariasm233/pruebaTecnicaB.git
  cd pruebaTecnicaB
```

2. Build the project (uses the included Maven Wrapper):

Linux / macOS:
```bash
  ./mvnw clean install
```

Windows (PowerShell / cmd):
```powershell
.\mvnw.cmd clean install
```

## Usage

### Run the application

Start the application using the Spring Boot plugin in the infrastructure module (the main runnable module):

Linux / macOS:
```bash
  ./mvnw -f infrastructure/pom.xml spring-boot:run
```

Windows:
```powershell
.\mvnw.cmd -f infrastructure/pom.xml spring-boot:run
```

The application starts on `http://localhost:8080` by default. The H2 database is initialized with the `data.sql` file included in the project.

### Run tests

Execute the full test suite:

Linux / macOS:
```bash
  ./mvnw clean test
```

Windows:
```powershell
.\mvnw.cmd clean test
```

### H2 Console

Access the H2 web console at:

```
http://localhost:8080/h2-console
```

Default credentials:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## API: Product Price (GET)

Retrieve the price for a product for a given brand and application date.

- URL: `/api/v1/prices`
- Method: `GET`
- Query parameters:
    - `productId` (required): numeric product id (e.g., `35455`)
    - `brandId` (required): numeric brand id (e.g., `1`)
    - `applicationDate` (required): ISO-8601 datetime `YYYY-MM-DDTHH:mm:ss` (e.g., `2020-06-14T16:00:00`)

Example request (curl):

```bash
curl -X GET "http://localhost:8080/api/v1/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1" \
  -H "Accept: application/json"
```

Example successful response (200):

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45
}
```

## Error handling

If no matching price is found, the API returns `404 Not Found` with a JSON error payload:

Example (404):
```json
{
  "message": "No price found for the given parameters.",
  "status": 404,
  "error": "Not Found",
  "timestamp": "2026-01-24T12:34:56Z"
}
```

The project translates domain exceptions (for example `PriceNotFoundException`) to HTTP responses using `@ControllerAdvice`.

## Architecture

This service is organized following Hexagonal / Clean Architecture:

```
+-------------------+
|    Domain Layer   |
|-------------------|
| Entities, Value   |
| Objects, Rules    |
+-------------------+
         |
         v
+-------------------+
| Application Layer |
|-------------------|
| Use cases /       |
| Services          |
+-------------------+
         |
         v
+-------------------+
| Infrastructure    |
|-------------------|
| REST Controllers  |
| Repositories (JPA)|
| Exception handlers|
+-------------------+
```

Guidelines:
- Keep domain logic framework-agnostic.
- Ports define interfaces for repositories and external systems.
- Adapters implement ports (e.g., Spring Data JPA repositories, controllers).

## Development

- Follow the project's package/module layout (domain / application / infrastructure).
- Run `./mvnw -f infrastructure/pom.xml spring-boot:run` to start the app locally.
- Use the included `data.sql` to inspect the preloaded dataset during development.
- Add unit tests near the code they validate and integration tests that boot the Spring context when necessary.

## Contributing

PRs accepted.

## License

[MIT © Diego Arias.](./LICENSE)

## Maintainers

- Diego Arias — owner of this fork / maintainer for this exercise

## Acknowledgements

- This README follows the recommendations from [standard-readme](https://github.com/RichardLitt/standard-readme).
