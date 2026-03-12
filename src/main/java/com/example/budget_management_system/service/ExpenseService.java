package com.example.budget_management_system.service;

import com.example.budget_management_system.entity.Expense;
import com.example.budget_management_system.repository.ExpenseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
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
