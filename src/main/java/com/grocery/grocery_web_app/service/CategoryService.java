package com.grocery.grocery_web_app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grocery.grocery_web_app.bean.CategoryBean;
import com.grocery.grocery_web_app.entity.CategoryEntity;
import com.grocery.grocery_web_app.exception.CategoryException;
import com.grocery.grocery_web_app.repository.CategoryRepository;

@Service
public class CategoryService {
	final static private Logger LOGGER = LogManager.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<CategoryBean> getAllVisibleCategories() {
		LOGGER.debug("List<CategoryBean> CategoryService.getAllVisibleCategories() --- START");
		List<CategoryEntity> allVisibleCategoriesEntities = categoryRepository.getAllVisibleCategories();
		if(allVisibleCategoriesEntities.size() == 0) {
			LOGGER.info("List<CategoryBean> CategoryService.getAllVisibleCategories() --- visible Categories not available");
			throw new CategoryException();
		}
		LOGGER.info("List<CategoryBean> CategoryService.getAllVisibleCategories() --- visible Categories entities are: " +
				allVisibleCategoriesEntities);
		List<CategoryBean> allVisibleCategoriesBeans = allVisibleCategoriesEntities.stream()
				.map(categoryEntity -> CategoryBean.builder()
						.categoryId(categoryEntity.getCategoryId())
						.categoryName(categoryEntity.getCategoryName()).build())
				.collect(Collectors.toList());
		LOGGER.info("List<CategoryBean> CategoryService.getAllVisibleCategories() --- visible Categories beans are: " +
				allVisibleCategoriesBeans);
		LOGGER.debug("List<CategoryBean> CategoryService.getAllVisibleCategories() --- END");
		return allVisibleCategoriesBeans;
	}
}
