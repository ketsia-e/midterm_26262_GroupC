# CURL COMMANDS FOR VIVA DEMONSTRATION

## 🎯 COPY-PASTE READY COMMANDS

---

## 1️⃣ SHOW LOCATION HIERARCHY

```bash
curl localhost:8080/api/locations
```
**Say**: "This shows all administrative levels in one table with LocationType enum"

---

## 2️⃣ SHOW ALL USERS

```bash
curl localhost:8080/api/users
```
**Say**: "Each user only stores a village location_id"

---

## 3️⃣ GET USERS BY PROVINCE (Demonstrates Hierarchy Navigation)

```bash
curl localhost:8080/api/users/province/code/KIG
```
**Say**: "Even though users only store village, I can retrieve all users in Kigali province by navigating parent relationships through JPQL"

---

## 4️⃣ GET USERS BY VILLAGE

```bash
curl localhost:8080/api/users/location/name/Nyabisindu
```
**Say**: "This shows users in a specific village - same query pattern works for any level"

---

## 5️⃣ GET USERS BY DISTRICT

```bash
curl localhost:8080/api/users/location/name/Gasabo
```
**Say**: "Querying by district - demonstrates flexibility of the hierarchical structure"

---

## 6️⃣ SHOW PAGINATION

```bash
curl localhost:8080/api/users?page=0&size=2&sortBy=username
```
**Say**: "This demonstrates pagination - returns only 2 users with metadata like totalElements and totalPages"

---

## 7️⃣ SHOW ALL BUDGETS

```bash
curl localhost:8080/api/budgets
```
**Say**: "These are the budgets with pagination and sorting by totalAmount descending"

---

## 8️⃣ SHOW BUDGET SUMMARIES (One-to-One Relationship)

```bash
curl localhost:8080/api/budget-summaries
```
**Say**: "Each budget has exactly one summary - this is the One-to-One relationship. Shows calculated data like totalSpent and remainingAmount"

---

## 9️⃣ GET SUMMARY FOR SPECIFIC BUDGET

```bash
curl localhost:8080/api/budget-summaries/budget/1
```
**Say**: "Getting the summary for budget 1 - demonstrates the One-to-One relationship query"

---

## 🔟 SHOW ALL EXPENSES

```bash
curl localhost:8080/api/expenses
```
**Say**: "These expenses demonstrate Many-to-One with Budget and Many-to-Many with Categories"

---

## 1️⃣1️⃣ CREATE NEW USER (Only Village ID Required)

```bash
curl -X POST localhost:8080/api/users -H "Content-Type: application/json" -d "{\"username\":\"viva_demo\",\"email\":\"viva@mail.com\",\"fullName\":\"Viva Demo User\",\"location\":{\"id\":11}}"
```
**Say**: "Creating a user with only village ID 11 (Nyabisindu). The system automatically links to all parent levels"

---

## 1️⃣2️⃣ VERIFY NEW USER IN PROVINCE QUERY

```bash
curl localhost:8080/api/users/province/code/KIG
```
**Say**: "The new user now appears in the province query, proving automatic parent linking works"

---

## 1️⃣3️⃣ VERIFY NEW USER IN VILLAGE QUERY

```bash
curl localhost:8080/api/users/location/name/Nyabisindu
```
**Say**: "And here's the user in the village query"

---

## 1️⃣4️⃣ TRY DUPLICATE USERNAME (Shows existsBy Validation)

```bash
curl -X POST localhost:8080/api/users -H "Content-Type: application/json" -d "{\"username\":\"john_doe\",\"email\":\"duplicate@mail.com\",\"fullName\":\"Duplicate User\",\"location\":{\"id\":11}}"
```
**Say**: "This will fail because existsByUsername() prevents duplicate usernames"

---

## 1️⃣5️⃣ CREATE NEW BUDGET

```bash
curl -X POST localhost:8080/api/budgets -H "Content-Type: application/json" -d "{\"budgetName\":\"Demo Budget\",\"totalAmount\":75000,\"startDate\":\"2024-03-01\",\"endDate\":\"2024-03-31\",\"user\":{\"id\":1}}"
```
**Say**: "Creating a budget for user 1 - demonstrates Many-to-One relationship"

---

## 1️⃣6️⃣ GET BUDGETS FOR USER 1

```bash
curl localhost:8080/api/budgets/user/1
```
**Say**: "Getting all budgets for user 1 - demonstrates One-to-Many relationship"

---

## 1️⃣7️⃣ CREATE EXPENSE WITH MULTIPLE CATEGORIES (Many-to-Many)

```bash
curl -X POST localhost:8080/api/expenses -H "Content-Type: application/json" -d "{\"description\":\"Dinner at restaurant\",\"amount\":8000,\"expenseDate\":\"2024-01-20\",\"budget\":{\"id\":1},\"categories\":[{\"id\":1},{\"id\":4}]}"
```
**Say**: "Creating an expense with multiple categories (Food and Entertainment) - demonstrates Many-to-Many relationship"

---

## 1️⃣8️⃣ GET EXPENSES FOR BUDGET 1

```bash
curl localhost:8080/api/expenses/budget/1
```
**Say**: "Getting all expenses for budget 1 - demonstrates One-to-Many relationship"

---

## 1️⃣9️⃣ GET LOCATIONS BY TYPE (Province Only)

```bash
curl localhost:8080/api/locations/type/PROVINCE
```
**Say**: "Filtering locations by type - shows only provinces"

---

## 2️⃣0️⃣ GET LOCATIONS BY TYPE (Village Only)

```bash
curl localhost:8080/api/locations/type/VILLAGE
```
**Say**: "Shows only villages - demonstrates the LocationType enum"

---

## 🎯 QUICK REFERENCE - LOCATION IDs

```
Location IDs (for creating users):
11 = Nyabisindu Village
12 = Kamatamu Village
13 = Kimihurura Village
14 = Rebero Village

User IDs (for creating budgets):
1 = john_doe
2 = jane_smith
3 = bob_wilson
4 = alice_brown

Budget IDs (for creating expenses):
1 = Monthly Budget (john_doe)
2 = Travel Budget (john_doe)
3 = Monthly Budget (jane_smith)

Category IDs (for expenses):
1 = Food
2 = Transport
3 = Utilities
4 = Entertainment
```

---

## 🎓 DEMONSTRATION ORDER (Recommended)

### Part 1: Show Structure (3 commands)
1. Show locations hierarchy
2. Show all users
3. Show budgets

### Part 2: Demonstrate Hierarchy Queries (3 commands)
4. Get users by province
5. Get users by district
6. Get users by village

### Part 3: Show Relationships (3 commands)
7. Show budget summaries (One-to-One)
8. Get budgets for user (One-to-Many)
9. Show expenses (Many-to-Many)

### Part 4: Create & Validate (3 commands)
10. Create new user (only village needed)
11. Verify in province query
12. Try duplicate username (fails)

**Total Time: ~5 minutes**

---

## 💡 PRO TIPS

1. **Have these commands ready in a text file** - copy-paste during demo
2. **Test all commands before viva** - make sure they work
3. **Explain while waiting for response** - don't just stare at the screen
4. **Point out the JSON structure** - show the relationships in the response
5. **Be ready to explain the SQL** - know what queries are generated

---

## 🚨 IF SOMETHING GOES WRONG

### Application not running:
```bash
cd c:\Users\HP\Downloads\budget-management-system\budget-management-system
mvn spring-boot:run
```

### Database connection error:
Check PostgreSQL is running and credentials in application.properties

### Port 8080 in use:
```bash
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

---

## 📱 MOBILE-FRIENDLY (One-Line Commands)

```bash
# 1. Locations
curl localhost:8080/api/locations

# 2. Users
curl localhost:8080/api/users

# 3. Users by Province
curl localhost:8080/api/users/province/code/KIG

# 4. Users by Village
curl localhost:8080/api/users/location/name/Nyabisindu

# 5. Pagination
curl localhost:8080/api/users?page=0&size=2

# 6. Budget Summaries
curl localhost:8080/api/budget-summaries

# 7. Create User
curl -X POST localhost:8080/api/users -H "Content-Type: application/json" -d "{\"username\":\"demo\",\"email\":\"demo@mail.com\",\"fullName\":\"Demo User\",\"location\":{\"id\":11}}"

# 8. Verify User
curl localhost:8080/api/users/province/code/KIG
```

---

Good luck! 🎓🚀
