package app.budget.domain.budget.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.budget.domain.budget.dto.request.BudgetRequestDto;
import app.budget.domain.budget.dto.response.BudgetResponseDto;
import app.budget.domain.budget.dto.response.CategoryResponseDto;
import app.budget.domain.budget.entity.Budget;
import app.budget.domain.budget.entity.Category;
import app.budget.domain.budget.entity.CategoryType;
import app.budget.domain.budget.repository.BudgetRepository;
import app.budget.domain.budget.repository.CategoryRepository;
import app.budget.domain.user.entity.User;
import app.budget.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BudgetServiceImpl implements BudgetService {

    private final UserRepository userRepo;
    private final BudgetRepository budgetRepo;
    private final CategoryRepository categoryRepo;

    @Override
    public BudgetResponseDto suggestBudget(int totalAmount) {
        List<Budget> budgetList = budgetRepo.findAll();
        Map<CategoryType, Double> proportionSumMap = calculateProportionSum(budgetList);

        List<CategoryResponseDto> categoryDtoList = new ArrayList<>();
        for (CategoryType categoryType : proportionSumMap.keySet()) {
            double proportion = proportionSumMap.get(categoryType) / budgetList.size();
            int amount = (int) (totalAmount * proportion);
            categoryDtoList.add(CategoryResponseDto.builder()
                    .categoryType(categoryType)
                    .amount(amount)
                    .proportion(proportion)
                    .build());
        }

        return BudgetResponseDto.builder()
                .totalAmount(totalAmount)
                .categories(categoryDtoList)
                .build();
    }

    private Map<CategoryType, Double> calculateProportionSum(List<Budget> budgetList) {
        Map<CategoryType, Double> proportionSumMap = new HashMap<>();
        for (Budget budget : budgetList) {
            List<Category> categoryList = budget.getCategories();
            for (Category category : categoryList) {
                CategoryType categoryType = category.getCategoryType();
                double proportion = category.getProportion();
                proportionSumMap.computeIfPresent(categoryType, (key, value) -> proportionSumMap.get(key) + proportion);
                proportionSumMap.putIfAbsent(categoryType, proportion);
            }
        }
        return proportionSumMap;
    }

    @Override
    public BudgetResponseDto getBudget(String userId) {
        User user = userRepo.findByUserId(userId).get();
        Budget budget = budgetRepo.findByUser(user).get();

        return BudgetResponseDto.builder()
                .totalAmount(budget.getTotalAmount())
                .categories(configDtoList(budget.getCategories()))
                .build();
    }

    private List<CategoryResponseDto> configDtoList(List<Category> categoryList) {
        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<CategoryResponseDto>();
        for (Category category : categoryList) {
            categoryResponseDtoList.add(CategoryResponseDto.convertIntoDto(category));
        }
        return categoryResponseDtoList;
    }

    @Override
    public void createBudget(BudgetRequestDto budgetReqDto) {

    }

    @Override
    public void updateBudget(BudgetRequestDto budgetReqDto) {

    }

}
