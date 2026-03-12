package com.example.budget_management_system.repository;

import com.example.budget_management_system.entity.BudgetSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetSummaryRepository extends JpaRepository<BudgetSummary, Long> {
    Optional<BudgetSummary> findByBudgetId(Long budgetId);
}
