package app.budget.domain.budget.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
    private int amount;
    @Column(columnDefinition = "decimal(19,2)")
    private double proportion;

    @Builder
    public CategoryEntity(CategoryType categoryType, int amount, double proportion){
        this.categoryType = categoryType;
        this.amount = amount;
        this.proportion = proportion;
    }

    public void setCategoryId(int id){
        this.id = id;
    }
}
