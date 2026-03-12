package com.example.budget_management_system.service;

import com.example.budget_management_system.entity.Budget;
import com.example.budget_management_system.repository.BudgetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {
    
    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public Budget createBudget(Budget budget) {
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
}
