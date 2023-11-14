package app.budget.domain.budget.dto.request;

import java.util.List;

import lombok.Getter;

@Getter
public class BudgetRequestDto {
    private List<CategoryRequestDto> categoryList;
}
