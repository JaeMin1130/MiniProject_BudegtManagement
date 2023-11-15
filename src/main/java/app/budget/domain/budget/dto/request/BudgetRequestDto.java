package app.budget.domain.budget.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BudgetRequestDto {
    private int totalAmount;
    private List<CategoryRequestDto> categoryReqDtoList;

    @Builder
    public BudgetRequestDto(int totalAmount, List<CategoryRequestDto> categoryReqDtoList){
        this.totalAmount = totalAmount;
        this.categoryReqDtoList = categoryReqDtoList;
    }
}
