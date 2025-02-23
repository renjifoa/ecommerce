# E-commerce

## Description

This is a backend application for an e-commerce system developed in Java with Spring Boot, following a Layered Architecture. 

It allows shopping cart management with the following features:
- Create a shopping cart
- Add products to a cart
- Retrieve cart information
- Delete carts manually or automatically after 10 minutes of inactivity
- No database implementation, in-memory storage
- Code coverage with JaCoCo
- Review code style with CheckStyle
- API documentation with Swagger
- Basic CI pipeline for executing tests, JaCoCo and CheckStyle
- Basic PostMan collection and environment for testing
---

## Installation and Configuration
### Prerequisites
- Java 17
- Maven

### Install Dependencies
Run the following command in the project's root directory:
```sh
  mvn clean install
```

### Run the Application
```sh
  mvn spring-boot:run
```
The API will be available at: 
`http://localhost:8080/cart`
---

## API Usage
### API Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST   | `/cart` | Create a cart |
| POST   | `/cart/{id}/product` | Add a product to a cart |
| GET    | `/cart/{id}` | Retrieve cart information |
| DELETE | `/cart/{id}` | Delete a cart |

---
## Testing and Code Coverage
### Run Tests with Maven
```sh
  mvn clean test
```

### Generate JaCoCo Coverage Report
```sh
  mvn jacoco:report
```
The report will be located at:
`target/site/jacoco/index.html`
---
## API Documentation with Swagger
The API includes Swagger documentation.

Open Swagger UI after running the application:
```sh
  http://localhost:8080/swagger-ui/index.html
```
---

## Project Structure
```
ecommerce
├── .github/workflows   #Pipeline configuration
├── src/main/java/com/onebox/ecommerce
│   ├── controller      # Handles HTTP requests
│   ├── model           # Entities and data models
│   ├── repository      # Data access layer
│   ├── service         # Business logic
├── src/main/resources
│   ├── checkstyle      # Code style configuration
├── src/test/java/com/onebox/ecommerce
│   ├── controller      # Controller tests
│   ├── service         # Service tests
├── docs/postman        # postman collection and environment
├── pom.xml             # Maven configuration
└── README.md           # Project documentation
```
---

## Technologies Used
- Java 17
- Spring Boot
- Maven
- JaCoCo (Code coverage)
- CheckStyle (Code Style)
- Swagger (API documentation)
- Postman (Manual Testing)
---
