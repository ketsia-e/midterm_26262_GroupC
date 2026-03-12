package com.example.budget_management_system.repository;

import com.example.budget_management_system.entity.Budget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Page<Budget> findByUserId(Long userId, Pageable pageable);
}
