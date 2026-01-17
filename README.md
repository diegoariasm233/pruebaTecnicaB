# Price Query

This is a project for querying product price information. It is a modular application based on **Hexagonal Architecture**, using **H2** as an in-memory database with initial data.

## Requirements

- **Java 21**
- **Maven 3.9.9**

## Description

This project follows the **Hexagonal Architecture** pattern, which allows for a clear separation of concerns between the domain and external interfaces (such as databases, APIs, etc.). The database used is **H2**, which is configured to store initial test data when the application is run.

## Setup

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

**URL:** `http://localhost:8080/api/v1/product-price`  
**Method:** `GET`  
**Query Parameters:**
- `productId` (required): The ID of the product.
- `brandId` (required): The ID of the brand.
- `applicationDate` (required): The application date in `YYYY-MM-DDTHH:mm:ss` format.

**Example Request:**
 `curl -X GET "http://localhost:8080/api/v1/product-price?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1" -H "Accept: application/json"`

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