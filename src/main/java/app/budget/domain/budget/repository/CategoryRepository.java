package app.budget.domain.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.budget.domain.budget.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
