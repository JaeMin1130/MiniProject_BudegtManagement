package app.budget.domain.budget.entity;

import java.util.List;

import app.budget.domain.member.entity.MemberEntity;
import app.budget.global.BaseTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "budget")
public class BudgetEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private int id;

    private int totalAmount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // LAZY : 부모엔티티 검색할때 자식엔티티 검색, 조인 안 함
    private MemberEntity memberEntity;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // EAGER : 부모엔티티 검색할때 자식엔티티 검색, 조인 함
    private List<CategoryEntity> categoryEntityList;


    @Builder
    public BudgetEntity(MemberEntity memberEntity, List<CategoryEntity> categoryEntityList, int totalAmount){
        this.memberEntity = memberEntity;
        this.categoryEntityList = categoryEntityList;
        this.totalAmount = totalAmount;
    }

    public void updateBudget(int totalAmount, List<CategoryEntity> categoryEntityList){
        this.totalAmount = totalAmount;
        this.categoryEntityList = categoryEntityList;
    }
}
