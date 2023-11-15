package app.budget.domain.budget.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.budget.domain.budget.entity.BudgetEntity;
import app.budget.domain.member.entity.MemberEntity;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Integer> {
    public Optional<BudgetEntity> findByMemberEntity(MemberEntity memberEntity);
    public Optional<BudgetEntity> findByMemberEntityAndCreatedDateBefore(MemberEntity memberEntity, LocalDateTime date);
}
