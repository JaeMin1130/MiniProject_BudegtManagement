package app.budget.domain.budget.dto.request;

import app.budget.domain.budget.entity.CategoryType;
import lombok.Getter;

@Getter
public class CategoryRequestDto {

    private CategoryType categoryType;
    private Integer amount;

}
