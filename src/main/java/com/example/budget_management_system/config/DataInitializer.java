package com.example.budget_management_system.config;

import com.example.budget_management_system.entity.*;
import com.example.budget_management_system.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;

    public DataInitializer(LocationRepository locationRepository, UserRepository userRepository,
                          BudgetRepository budgetRepository, CategoryRepository categoryRepository,
                          ExpenseRepository expenseRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) {
        if (locationRepository.count() > 0) return;

        Location kigali = new Location("Kigali City", "KIG", LocationType.PROVINCE, null);
        Location eastern = new Location("Eastern Province", "EP", LocationType.PROVINCE, null);
        locationRepository.saveAll(Arrays.asList(kigali, eastern));

        Location gasabo = new Location("Gasabo", "GAS", LocationType.DISTRICT, kigali);
        Location kicukiro = new Location("Kicukiro", "KIC", LocationType.DISTRICT, kigali);
        locationRepository.saveAll(Arrays.asList(gasabo, kicukiro));

        Location kimironko = new Location("Kimironko", "KIM", LocationType.SECTOR, gasabo);
        Location remera = new Location("Remera", "REM", LocationType.SECTOR, gasabo);
        Location gikondo = new Location("Gikondo", "GIK", LocationType.SECTOR, kicukiro);
        locationRepository.saveAll(Arrays.asList(kimironko, remera, gikondo));

        Location biryogo = new Location("Biryogo", "BIR", LocationType.CELL, kimironko);
        Location kibagabaga = new Location("Kibagabaga", "KIB", LocationType.CELL, remera);
        Location gatenga = new Location("Gatenga", "GAT", LocationType.CELL, gikondo);
        locationRepository.saveAll(Arrays.asList(biryogo, kibagabaga, gatenga));

        Location nyabisindu = new Location("Nyabisindu", "NYA", LocationType.VILLAGE, biryogo);
        Location kamatamu = new Location("Kamatamu", "KAM", LocationType.VILLAGE, biryogo);
        Location kimihurura = new Location("Kimihurura", "KMH", LocationType.VILLAGE, kibagabaga);
        Location rebero = new Location("Rebero", "REB", LocationType.VILLAGE, gatenga);
        locationRepository.saveAll(Arrays.asList(nyabisindu, kamatamu, kimihurura, rebero));

        Category food = new Category("Food", "Food and beverages");
        Category transport = new Category("Transport", "Transportation expenses");
        Category utilities = new Category("Utilities", "Utility bills");
        Category entertainment = new Category("Entertainment", "Entertainment and leisure");
        categoryRepository.saveAll(Arrays.asList(food, transport, utilities, entertainment));

        User user1 = new User("john_doe", "john@example.com", "John Doe");
        user1.setLocation(nyabisindu);
        User user2 = new User("jane_smith", "jane@example.com", "Jane Smith");
        user2.setLocation(kamatamu);
        User user3 = new User("bob_wilson", "bob@example.com", "Bob Wilson");
        user3.setLocation(kimihurura);
        User user4 = new User("alice_brown", "alice@example.com", "Alice Brown");
        user4.setLocation(rebero);
        userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));

        Budget budget1 = new Budget("Monthly Budget", new BigDecimal("50000"), 
                                   LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));
        budget1.setUser(user1);
        Budget budget2 = new Budget("Travel Budget", new BigDecimal("100000"), 
                                   LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 28));
        budget2.setUser(user1);
        Budget budget3 = new Budget("Monthly Budget", new BigDecimal("60000"), 
                                   LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));
        budget3.setUser(user2);
        budgetRepository.saveAll(Arrays.asList(budget1, budget2, budget3));

        Expense expense1 = new Expense("Grocery shopping", new BigDecimal("5000"), LocalDate.now());
        expense1.setBudget(budget1);
        expense1.setCategories(Arrays.asList(food));
        Expense expense2 = new Expense("Uber ride", new BigDecimal("1500"), LocalDate.now());
        expense2.setBudget(budget1);
        expense2.setCategories(Arrays.asList(transport));
        Expense expense3 = new Expense("Restaurant dinner", new BigDecimal("3000"), LocalDate.now());
        expense3.setBudget(budget1);
        expense3.setCategories(Arrays.asList(food, entertainment));
        Expense expense4 = new Expense("Electricity bill", new BigDecimal("4000"), LocalDate.now());
        expense4.setBudget(budget3);
        expense4.setCategories(Arrays.asList(utilities));
        expenseRepository.saveAll(Arrays.asList(expense1, expense2, expense3, expense4));

        System.out.println("Rwanda administrative structure initialized successfully!");
        System.out.println("Unified Location entity with LocationType enum");
        System.out.println("Sample data: 2 Provinces, 2 Districts, 3 Sectors, 3 Cells, 4 Villages, 4 Users");
    }
}
