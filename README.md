# E-commerce

## Description

This is a backend application for an e-commerce system developed in Java with Spring Boot, following a Layered Architecture. 

It allows shopping cart management with the following features:
- Create a shopping cart
- Add products to a cart
- Retrieve cart information
- Delete carts manually or automatically after 10 minutes of inactivity
- No database implementation, in-memory storage with predefined products
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
The cart API will be available at: ```http://localhost:8080/cart```

The product available API will be available at: ```http://localhost:8080/products```

---
## API Documentation with Swagger
The API includes Swagger documentation.

Open Swagger UI after running the application: ```http://localhost:8080/swagger-ui/index.html```

---
## API Usage
### API Endpoints

You can use swagger or Postman to send the request.
In the directory ```docs/postman``` you can find the collection and environment used for Postman.

| Method | Endpoint     | Description                   | Example body                                    |
|--------|--------------|-------------------------------|-------------------------------------------------|
| POST   | `/cart`      | Create a cart                 | Empty                                           |
| GET    | `/cart/{id}` | Retrieve cart information     | Empty                                           |
| PUT    | `/cart/{id}` | Update products from the cart | `[{"id": 1,"amount": 5},{"id": 2,"amount": 7}]` |
| DELETE | `/cart/{id}` | Delete a cart                 | Empty                                           |
| GET    | `/products`  | Retrieve available products   | Empty                                           |

Predefined products:
Apple, Banana, Orange, Mango, Pineapple, Watermelon, Papaya, Peach, Kiwi, Avocado

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
The report will be located at: ```target/site/jacoco/index.html```

---
## Project Structure
```
ecommerce
├── .github/workflows   #Pipeline configuration
├── src/main/java/com/onebox/ecommerce
│   ├── controller      # Handles HTTP requests
│   ├── dto             # Data transfer objects for request/response
│   ├── exception       # Custom exceptions and exception handling
│   ├── model           # Entities and data models
│   ├── repository      # Data access layer
│   ├── service         # Business logic
|
├── src/main/resources
│   ├── checkstyle      # Code style configuration
|
├── src/test/java/com/onebox/ecommerce
│   ├── controller      # Controller tests
│   ├── service         # Service tests
|
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
