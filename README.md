# Price Query

This project allows querying product price information. It is a **modular application** based on **Hexagonal Architecture**, using **H2** as an in-memory database with initial test data.

---

## Requirements

- **Java 21**
- **Maven 3.9.9** (recommended to use Maven Wrapper)
- **Spring Boot 4.x**

---

## Description

This project follows the **Hexagonal / Clean Architecture** pattern, which separates the application into:

1. **Domain Layer**: contains business entities and rules, independent of frameworks.
2. **Application Layer**: services implementing business use cases.
3. **Infrastructure Layer**: handles REST controllers, database access, and exception translation.

The database used is **H2**, configured to load initial data from `data.sql` when the application starts.

---

## Technologies & Patterns

- **Java 21**: main programming language.
- **Spring Boot 4.x**: framework for building REST APIs and microservices.
- **Maven 3.9.9**: dependency management and build tool.
- **H2 Database**: in-memory database for testing.
- **Hexagonal Architecture**: separates domain, application, and infrastructure.
- **JUnit & Mockito**: unit and integration testing.

---

## Running the Application

You can run the project using **Maven Wrapper** (`mvnw`) to ensure consistent builds:

### Setup

1. Clone this repository:

   ```bash
   git clone https://github.com/diegoariasm233/pruebaTecnicaB.git

2. Compile the project using Maven:

    ```bash
    ./mvnw clean install

3. Run the application:

   If you wish to run the application directly, you can use the following Maven command:

    ```bash
   ./mvnw spring-boot:run -f infrastructure/pom.xml

This will start the application, and the H2 database will be initialized with the preconfigured data defined in the data.sql file.

# Running Tests

If you want to run the tests, you can do so with the following command

```bash
  ./mvnw clean test
```

This will execute all the unit and integration tests for the application.

# Access to the H2 Console
You can access the H2 web console to inspect the database at:

http://localhost:8080/h2-console

## Default Credentials:

 - JDBC URL: jdbc:h2:mem:testdb
 - Username: sa
 - Password: (empty)


## Consuming GET Endpoint: Product Price

The `productPrice` endpoint retrieves the price of a product based on productId, brandId, and application date.

### 1. Endpoint: Product Price

**URL:** `http://localhost:8080/api/v1/prices`  
**Method:** `GET`  
**Query Parameters:**
- `productId` (required): The ID of the product.
- `brandId` (required): The ID of the brand.
- `applicationDate` (required): The application date in `YYYY-MM-DDTHH:mm:ss` format.

**Example Request:**
 `curl -X GET "http://localhost:8080/api/v1/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1" -H "Accept: application/json"`

Response: 
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

---

## Error Handling

If the requested price does not exist, the API returns a **404 Not Found** with a clear error message:

```json
{
    "message": "No price found for the given parameters.",
    "status": 404,
    "error": "Not Found",
    "timestamp": "2026-01-24T12:34:56Z"
}
```

Errors are handled using `@ControllerAdvice` to translate domain exceptions `(PriceNotFoundException)` into proper HTTP responses.

---

## Application Architecture

The project follows a **Hexagonal / Clean Architecture** design:
```
+-------------------+
|    Domain Layer   |
|-------------------|
| Price, Business   |
| Rules             |
+-------------------+
|
v
+-------------------+
| Application Layer |
|-------------------|
| Services / UseCases|
+-------------------+
|
v
+-------------------+
| Infrastructure    |
|-------------------|
| REST Controllers  |
| Repositories      |
| ExceptionHandlers |
+-------------------+
```
**Key Points:**

- **Controllers** only handle HTTP requests and responses.
- **Services** implement business logic and throw domain exceptions when needed.
- **Infrastructure** translates domain exceptions to HTTP responses and handles persistence.

