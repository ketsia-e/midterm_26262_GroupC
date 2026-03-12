package com.example.budget_management_system.service;

import com.example.budget_management_system.entity.Budget;
import com.example.budget_management_system.entity.BudgetSummary;
import com.example.budget_management_system.entity.User;
import com.example.budget_management_system.repository.BudgetRepository;
import com.example.budget_management_system.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class BudgetService {
    
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetService(BudgetRepository budgetRepository, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }

    public Budget createBudget(String budgetName, BigDecimal totalAmount, LocalDate startDate, LocalDate endDate, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        Budget budget = new Budget();
        budget.setBudgetName(budgetName);
        budget.setTotalAmount(totalAmount);
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        budget.setUser(user);
        
        // Create and link BudgetSummary
        BudgetSummary summary = new BudgetSummary();
        summary.setTotalSpent(BigDecimal.ZERO);
        summary.setRemainingAmount(totalAmount);
        summary.setPercentageUsed(0.0);
        summary.setNumberOfExpenses(0);
        summary.setBudget(budget);
        
        budget.setBudgetSummary(summary);
        
        return budgetRepository.save(budget);
    }

    // Get all budgets with pagination and sorting by totalAmount descending
    public Page<Budget> getAllBudgets(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("totalAmount").descending());
        return budgetRepository.findAll(pageable);
    }

    // Get budgets by user with pagination
    public Page<Budget> getBudgetsByUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDate").descending());
        return budgetRepository.findByUserId(userId, pageable);
    }
    
    public Budget updateBudget(Long id, String budgetName, BigDecimal totalAmount, LocalDate startDate, LocalDate endDate) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));
        
        budget.setBudgetName(budgetName);
        budget.setTotalAmount(totalAmount);
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        
        // Update BudgetSummary remainingAmount if totalAmount changed
        BudgetSummary summary = budget.getBudgetSummary();
        if (summary != null) {
            summary.setRemainingAmount(totalAmount.subtract(summary.getTotalSpent()));
            if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
                summary.setPercentageUsed(summary.getTotalSpent()
                        .divide(totalAmount, 4, java.math.RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)).doubleValue());
            }
        }
        
        return budgetRepository.save(budget);
    }
    
    public Budget getBudgetById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));
    }
}
