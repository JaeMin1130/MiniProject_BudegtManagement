package app.budget.domain.budget.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BudgetResponseDto {
    private Integer totalAmount;
    private List<CategoryResponseDto> categories;

    @Builder
    public BudgetResponseDto(int totalAmount, List<CategoryResponseDto> categories) {
        this.totalAmount = totalAmount;
        this.categories = categories;
    }
}
