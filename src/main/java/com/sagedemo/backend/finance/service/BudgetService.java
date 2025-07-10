package com.sagedemo.backend.finance.service;

import com.sagedemo.backend.finance.entity.Budget;
import com.sagedemo.backend.finance.repository.BudgetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }
    @Transactional
    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }
    public Budget updateBudget(Long id, Budget updated) {
        return budgetRepository.findById(id).map(budget -> {
            budget.setName(updated.getName());
            budget.setAmount(updated.getAmount());
            budget.setStatus(updated.getStatus());
            budget.setPeriod(updated.getPeriod());
            return budgetRepository.save(budget);
        }).orElseThrow(() -> new RuntimeException("Budget not found"));
    }
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }
}
