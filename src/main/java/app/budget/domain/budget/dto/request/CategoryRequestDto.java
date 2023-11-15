package app.budget.domain.budget.dto.request;

import app.budget.domain.budget.entity.CategoryEntity;
import app.budget.domain.budget.entity.CategoryType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryRequestDto {

    private CategoryType categoryType;
    private int amount;

    public CategoryEntity convertIntoEntity(CategoryRequestDto categoryReqDto, int totalAmount) {
        double proportion = (double)categoryReqDto.getAmount() / totalAmount;
        return CategoryEntity.builder()
                .categoryType(categoryReqDto.getCategoryType())
                .amount(categoryReqDto.getAmount())
                .proportion(proportion)
                .build();
    }
}
