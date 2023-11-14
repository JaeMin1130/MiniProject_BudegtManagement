package app.budget.domain.budget.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.budget.domain.budget.dto.request.BudgetRequestDto;
import app.budget.domain.budget.dto.response.BudgetResponseDto;
import app.budget.domain.budget.entity.CategoryType;
import app.budget.domain.budget.service.BudgetService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/budgets")
@RestController
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping("/category")
    public ResponseEntity<CategoryType[]> getCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(CategoryType.values());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BudgetResponseDto> getBudget(@PathVariable String userId) {
        BudgetResponseDto budgetRespDto = budgetService.getBudget(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(budgetRespDto);
    }

    @GetMapping("/suggestion/{totalAmount}")
    public ResponseEntity<BudgetResponseDto> suggestBudget(@PathVariable int totalAmount) {
        BudgetResponseDto budgetRespDto = budgetService.suggestBudget(totalAmount);
        return ResponseEntity.status(HttpStatus.CREATED).body(budgetRespDto);
    }

    @PostMapping
    public ResponseEntity<Void> createBudget(@RequestBody BudgetRequestDto budgetReqDto) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateBudget(@RequestBody BudgetRequestDto budgetReqDto) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
