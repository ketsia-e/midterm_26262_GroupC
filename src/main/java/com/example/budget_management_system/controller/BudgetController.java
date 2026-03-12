package com.example.budget_management_system.controller;

import com.example.budget_management_system.entity.Budget;
import com.example.budget_management_system.service.BudgetService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestBody Map<String, Object> request) {
        String budgetName = (String) request.get("budgetName");
        BigDecimal totalAmount = new BigDecimal(request.get("totalAmount").toString());
        LocalDate startDate = LocalDate.parse((String) request.get("startDate"));
        LocalDate endDate = LocalDate.parse((String) request.get("endDate"));
        Long userId = Long.valueOf(request.get("userId").toString());
        
        Budget budget = budgetService.createBudget(budgetName, totalAmount, startDate, endDate, userId);
        return ResponseEntity.ok(budget);
    }

    @GetMapping
    public ResponseEntity<Page<Budget>> getAllBudgets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(budgetService.getAllBudgets(page, size));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Budget>> getBudgetsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(budgetService.getBudgetsByUser(userId, page, size));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        String budgetName = (String) request.get("budgetName");
        BigDecimal totalAmount = new BigDecimal(request.get("totalAmount").toString());
        LocalDate startDate = LocalDate.parse((String) request.get("startDate"));
        LocalDate endDate = LocalDate.parse((String) request.get("endDate"));
        
        Budget budget = budgetService.updateBudget(id, budgetName, totalAmount, startDate, endDate);
        return ResponseEntity.ok(budget);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
        Budget budget = budgetService.getBudgetById(id);
        return ResponseEntity.ok(budget);
    }
}
