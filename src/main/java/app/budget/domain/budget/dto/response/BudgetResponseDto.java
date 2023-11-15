package app.budget.domain.budget.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BudgetResponseDto {
    private int totalAmount;
    private List<CategoryResponseDto> categoryRespDtoList;

    @Builder
    public BudgetResponseDto(int totalAmount, List<CategoryResponseDto> categoryRespDtoList) {
        this.totalAmount = totalAmount;
        this.categoryRespDtoList = categoryRespDtoList;
    }
}
