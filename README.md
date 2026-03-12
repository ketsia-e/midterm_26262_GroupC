# Budget Management System

A Spring Boot application demonstrating JPA relationships and Rwanda administrative hierarchy.

## Features
- Location hierarchy (Province → District → Sector → Cell → Village)
- User management with location tracking
- Budget and expense tracking
- Category management with Many-to-Many relationships
- Pagination and sorting support

## Technologies
- Spring Boot 4.0.3
- Spring Data JPA
- PostgreSQL
- Java 21
- Maven

## Setup

1. Create PostgreSQL database named `Budget`
2. Update `application.properties` with your database credentials
3. Run the application:
```bash
mvn clean install
mvn spring-boot:run
```

## API Endpoints

### Locations
- GET `/api/locations` - Get all locations
- GET `/api/locations/type/{type}` - Get locations by type

### Users
- GET `/api/users` - Get all users
- POST `/api/users` - Create user
- GET `/api/users/province/code/{code}` - Get users by province
- GET `/api/users/location/name/{name}` - Get users by location

### Budgets
- GET `/api/budgets` - Get all budgets
- POST `/api/budgets` - Create budget
- GET `/api/budgets/user/{id}` - Get budgets by user

### Expenses
- GET `/api/expenses` - Get all expenses
- POST `/api/expenses` - Create expense
- GET `/api/expenses/budget/{id}` - Get expenses by budget
