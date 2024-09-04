package com.grocery.grocery_web_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.grocery.grocery_web_app.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
	@Query(value = "SELECT c FROM CategoryEntity c WHERE c.categoryVisible = 'Y'")
	List<CategoryEntity> getAllVisibleCategories();
}
