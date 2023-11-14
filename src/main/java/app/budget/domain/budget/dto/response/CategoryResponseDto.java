package app.budget.domain.budget.dto.response;

import app.budget.domain.budget.entity.Category;
import app.budget.domain.budget.entity.CategoryType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryResponseDto {
    private CategoryType categoryType;
    private Integer amount;
    private Double proportion;

    public static CategoryResponseDto convertIntoDto(Category category) {
        return CategoryResponseDto.builder()
                .categoryType(category.getCategoryType())
                .amount(category.getAmount())
                .proportion(category.getProportion())
                .build();
    }
}
