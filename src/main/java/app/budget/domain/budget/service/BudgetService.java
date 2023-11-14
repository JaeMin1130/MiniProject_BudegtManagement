package app.budget.domain.budget.service;

import app.budget.domain.budget.dto.request.BudgetRequestDto;
import app.budget.domain.budget.dto.response.BudgetResponseDto;

public interface BudgetService {
    public BudgetResponseDto suggestBudget(int totalAmount);

    public BudgetResponseDto getBudget(String userId);

    public void createBudget(BudgetRequestDto budgetReqDto);

    public void updateBudget(BudgetRequestDto budgetReqDto);
}
