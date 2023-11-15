package app.budget.domain.budget.dto.response;

import app.budget.domain.budget.entity.CategoryEntity;
import app.budget.domain.budget.entity.CategoryType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryResponseDto {
    private CategoryType categoryType;
    private int amount;
    private double proportion;

    public static CategoryResponseDto convertIntoDto(CategoryEntity category) {
        return CategoryResponseDto.builder()
                .categoryType(category.getCategoryType())
                .amount(category.getAmount())
                .proportion(category.getProportion())
                .build();
    }
}
