package com.grocery.grocery_web_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grocery.grocery_web_app.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
	@Query(value = "SELECT c FROM CategoryEntity c WHERE c.categoryAvailable = 'Y'")
	List<CategoryEntity> getAllAvailableCategories();

	@Query("SELECT c FROM CategoryEntity c LEFT JOIN FETCH c.dairyProductEntities d "
		       + "WHERE c.categoryId = :categoryId "
		       + "AND c.categoryAvailable = 'Y' "
		       + "AND d.dairyProductAvailable = 'Y'")
	CategoryEntity findDairyProductByCategoryId(@Param(value = "categoryId") int categoryId);
}
