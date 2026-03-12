package com.example.budget_management_system.controller;

import com.example.budget_management_system.entity.Expense;
import com.example.budget_management_system.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Map<String, Object> request) {
        String description = (String) request.get("description");
        BigDecimal amount = new BigDecimal(request.get("amount").toString());
        LocalDate expenseDate = LocalDate.parse((String) request.get("expenseDate"));
        Long budgetId = Long.valueOf(request.get("budgetId").toString());
        
        @SuppressWarnings("unchecked")
        List<Integer> categoryIdsInt = (List<Integer>) request.get("categoryIds");
        List<Long> categoryIds = categoryIdsInt.stream().map(Long::valueOf).toList();
        
        Expense expense = expenseService.createExpense(description, amount, expenseDate, budgetId, categoryIds);
        return ResponseEntity.ok(expense);
    }

    @GetMapping
    public ResponseEntity<Page<Expense>> getAllExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(expenseService.getAllExpenses(page, size));
    }

    @GetMapping("/budget/{budgetId}")
    public ResponseEntity<Page<Expense>> getExpensesByBudget(
            @PathVariable Long budgetId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(expenseService.getExpensesByBudget(budgetId, page, size));
    }
}
