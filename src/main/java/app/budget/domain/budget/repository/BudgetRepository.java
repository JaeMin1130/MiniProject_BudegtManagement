package app.budget.domain.budget.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.budget.domain.budget.entity.Budget;
import app.budget.domain.user.entity.User;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    public Optional<Budget> findByUser(User user);
}
