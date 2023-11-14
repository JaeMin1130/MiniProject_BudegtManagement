package app.budget.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.budget.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUserId(String userId);
}
