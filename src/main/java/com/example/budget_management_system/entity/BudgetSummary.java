package com.example.budget_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "budget_summaries")
public class BudgetSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private BigDecimal totalSpent;
    private BigDecimal remainingAmount;
    private Double percentageUsed;
    private Integer numberOfExpenses;
    
    @OneToOne
    @JoinColumn(name = "budget_id", unique = true, nullable = false)
    @JsonIgnore
    private Budget budget;

    public BudgetSummary() {}

    public BudgetSummary(BigDecimal totalSpent, BigDecimal remainingAmount, Double percentageUsed, Integer numberOfExpenses) {
        this.totalSpent = totalSpent;
        this.remainingAmount = remainingAmount;
        this.percentageUsed = percentageUsed;
        this.numberOfExpenses = numberOfExpenses;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public BigDecimal getTotalSpent() { return totalSpent; }
    public void setTotalSpent(BigDecimal totalSpent) { this.totalSpent = totalSpent; }
    
    public BigDecimal getRemainingAmount() { return remainingAmount; }
    public void setRemainingAmount(BigDecimal remainingAmount) { this.remainingAmount = remainingAmount; }
    
    public Double getPercentageUsed() { return percentageUsed; }
    public void setPercentageUsed(Double percentageUsed) { this.percentageUsed = percentageUsed; }
    
    public Integer getNumberOfExpenses() { return numberOfExpenses; }
    public void setNumberOfExpenses(Integer numberOfExpenses) { this.numberOfExpenses = numberOfExpenses; }
    
    public Budget getBudget() { return budget; }
    public void setBudget(Budget budget) { this.budget = budget; }
}
