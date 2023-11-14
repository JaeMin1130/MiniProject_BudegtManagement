package app.budget.domain.budget.entity;

import java.util.List;

import app.budget.domain.user.entity.User;
import app.budget.global.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Budget extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // LAZY : 부모엔티티 검색할때 자식엔티티 검색, 조인 안 함
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Category> categories;

    private Integer totalAmount;
}
