# Budget Management System

A Spring Boot application demonstrating JPA relationships and Rwanda administrative hierarchy.

## ER Diagram

```mermaid
erDiagram
    LOCATIONS {
        bigint id PK
        varchar name
        varchar code UK
        varchar location_type
        bigint parent_id FK
    }

    USERS {
        bigint id PK
        varchar username UK
        varchar email
        varchar full_name
        bigint location_id FK
    }

    BUDGETS {
        bigint id PK
        varchar budget_name
        decimal total_amount
        date start_date
        date end_date
        bigint user_id FK
    }

    BUDGET_SUMMARIES {
        bigint id PK
        decimal total_spent
        decimal remaining_amount
        double percentage_used
        int number_of_expenses
        bigint budget_id FK UK
    }

    EXPENSES {
        bigint id PK
        varchar description
        decimal amount
        date expense_date
        bigint budget_id FK
    }

    CATEGORIES {
        bigint id PK
        varchar category_name UK
        varchar description
    }

    EXPENSE_CATEGORIES {
        bigint expense_id FK
        bigint category_id FK
    }

    LOCATIONS ||--o{ LOCATIONS : "parent"
    LOCATIONS ||--o{ USERS : "has"
    USERS ||--o{ BUDGETS : "owns"
    BUDGETS ||--|| BUDGET_SUMMARIES : "summarized by"
    BUDGETS ||--o{ EXPENSES : "contains"
    EXPENSES }o--o{ CATEGORIES : "expense_categories"
```

## JPA Relationships

| Relationship | Entities |
|---|---|
| One-to-One | Budget ↔ BudgetSummary |
| Many-to-One | User → Location |
| Many-to-One | Budget → User |
| Many-to-One | Expense → Budget |
| One-to-Many | Location → Children (self-referencing) |
| Many-to-Many | Expense ↔ Category |

## Technologies
- Spring Boot 4.0.3
- Spring Data JPA
- PostgreSQL
- Java 21
- Maven

## Setup

1. Create PostgreSQL database named `Budget`
2. Update `application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Budget
spring.datasource.username=postgres
spring.datasource.password=your_password
```
3. Run the application:
```bash
mvn clean install
mvn spring-boot:run
```
> Sample data is automatically seeded on startup via `DataInitializer`

## API Endpoints

### Locations
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/locations` | Get all locations |
| GET | `/api/locations/type/{type}` | Get locations by type (PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE) |

### Users
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/users` | Get all users |
| POST | `/api/users` | Create user |
| GET | `/api/users/province/code/{code}` | Get users by province code |
| GET | `/api/users/location/name/{name}` | Get users by location name |

### Budgets
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/budgets` | Get all budgets |
| POST | `/api/budgets` | Create budget |
| GET | `/api/budgets/{id}` | Get budget by ID |
| PUT | `/api/budgets/{id}` | Update budget |
| GET | `/api/budgets/user/{userId}` | Get budgets by user |

### Expenses
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/expenses` | Get all expenses |
| POST | `/api/expenses` | Create expense |
| GET | `/api/expenses/budget/{budgetId}` | Get expenses by budget |

### Categories
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/categories` | Get all categories |
| GET | `/api/categories/{id}` | Get category by ID |
| POST | `/api/categories` | Create category |

## Sample Request Bodies

**POST /api/users**
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "fullName": "John Doe",
  "locationId": 11
}
```

**POST /api/budgets**
```json
{
  "budgetName": "Monthly Budget",
  "totalAmount": 500000,
  "startDate": "2025-02-01",
  "endDate": "2025-02-28",
  "userId": 1
}
```

**POST /api/expenses**
```json
{
  "description": "Grocery shopping",
  "amount": 45000,
  "expenseDate": "2025-02-05",
  "budgetId": 1,
  "categoryIds": [1, 2]
}
```

**POST /api/categories**
```json
{
  "categoryName": "Food",
  "description": "Food and beverages"
}
```
