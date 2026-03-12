package com.example.budget_management_system.service;

import com.example.budget_management_system.entity.Budget;
import com.example.budget_management_system.entity.BudgetSummary;
import com.example.budget_management_system.entity.Category;
import com.example.budget_management_system.entity.Expense;
import com.example.budget_management_system.repository.BudgetRepository;
import com.example.budget_management_system.repository.BudgetSummaryRepository;
import com.example.budget_management_system.repository.CategoryRepository;
import com.example.budget_management_system.repository.ExpenseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {
    
    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final BudgetSummaryRepository budgetSummaryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, BudgetRepository budgetRepository, 
                         CategoryRepository categoryRepository, BudgetSummaryRepository budgetSummaryRepository) {
        this.expenseRepository = expenseRepository;
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
        this.budgetSummaryRepository = budgetSummaryRepository;
    }

    public Expense createExpense(String description, BigDecimal amount, LocalDate expenseDate, Long budgetId, List<Long> categoryIds) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found with id: " + budgetId));
        
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        
        Expense expense = new Expense(description, amount, expenseDate);
        expense.setBudget(budget);
        expense.setCategories(categories);
        
        Expense savedExpense = expenseRepository.save(expense);
        
        // Update BudgetSummary
        updateBudgetSummary(budgetId);
        
        return savedExpense;
    }
    
    private void updateBudgetSummary(Long budgetId) {
        BudgetSummary summary = budgetSummaryRepository.findByBudgetId(budgetId)
                .orElseThrow(() -> new RuntimeException("BudgetSummary not found for budget: " + budgetId));
        
        Page<Expense> expenses = expenseRepository.findByBudgetId(budgetId, PageRequest.of(0, Integer.MAX_VALUE));
        
        BigDecimal totalSpent = expenses.getContent().stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        Budget budget = summary.getBudget();
        BigDecimal remainingAmount = budget.getTotalAmount().subtract(totalSpent);
        double percentageUsed = totalSpent.divide(budget.getTotalAmount(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).doubleValue();
        
        summary.setTotalSpent(totalSpent);
        summary.setRemainingAmount(remainingAmount);
        summary.setPercentageUsed(percentageUsed);
        summary.setNumberOfExpenses(expenses.getContent().size());
        
        budgetSummaryRepository.save(summary);
    }

    // Get all expenses with pagination and sorting by date
    public Page<Expense> getAllExpenses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("expenseDate").descending());
        return expenseRepository.findAll(pageable);
    }

    // Get expenses by budget with pagination
    public Page<Expense> getExpensesByBudget(Long budgetId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("amount").descending());
        return expenseRepository.findByBudgetId(budgetId, pageable);
    }
}
