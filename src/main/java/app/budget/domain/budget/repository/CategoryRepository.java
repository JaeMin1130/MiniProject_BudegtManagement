package app.budget.domain.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.budget.domain.budget.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

}
