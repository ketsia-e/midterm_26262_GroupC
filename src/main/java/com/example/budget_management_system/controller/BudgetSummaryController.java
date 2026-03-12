package com.example.budget_management_system.controller;

import com.example.budget_management_system.entity.BudgetSummary;
import com.example.budget_management_system.repository.BudgetSummaryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget-summaries")
public class BudgetSummaryController {
    
    private final BudgetSummaryRepository budgetSummaryRepository;

    public BudgetSummaryController(BudgetSummaryRepository budgetSummaryRepository) {
        this.budgetSummaryRepository = budgetSummaryRepository;
    }

    @PostMapping
    public ResponseEntity<BudgetSummary> createSummary(@RequestBody BudgetSummary budgetSummary) {
        return ResponseEntity.ok(budgetSummaryRepository.save(budgetSummary));
    }

    @GetMapping
    public ResponseEntity<List<BudgetSummary>> getAllSummaries() {
        return ResponseEntity.ok(budgetSummaryRepository.findAll());
    }

    @GetMapping("/budget/{budgetId}")
    public ResponseEntity<BudgetSummary> getSummaryByBudgetId(@PathVariable Long budgetId) {
        return budgetSummaryRepository.findByBudgetId(budgetId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetSummary> getSummaryById(@PathVariable Long id) {
        return budgetSummaryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
