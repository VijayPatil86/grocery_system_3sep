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
import com.grocery.grocery_web_app.exception.CategoryNotAvailableException;
import com.grocery.grocery_web_app.repository.CategoryRepository;

@Service
public class CategoryService {
	final static private Logger LOGGER = LogManager.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<CategoryBean> getAllAvailableCategories() {
		LOGGER.debug("List<CategoryBean> CategoryService.getAllAvailableCategories() --- START");
		List<CategoryEntity> allAvailableCategoriesEntities = categoryRepository.getAllAvailableCategories();
		if(allAvailableCategoriesEntities.size() == 0) {
			LOGGER.info("List<CategoryBean> CategoryService.getAllAvailableCategories() --- available Categories not available");
			throw new CategoryNotAvailableException(null);
		}
		LOGGER.info("List<CategoryBean> CategoryService.getAllAvailableCategories() --- available Categories entities are: " +
				allAvailableCategoriesEntities);
		List<CategoryBean> allAvailableCategoriesBeans = allAvailableCategoriesEntities.stream()
				.map(categoryEntity -> CategoryBean.builder()
						.categoryId(categoryEntity.getCategoryId())
						.categoryName(categoryEntity.getCategoryName()).build())
				.collect(Collectors.toList());
		LOGGER.info("List<CategoryBean> CategoryService.getAllAvailableCategories() --- available Categories beans are: " +
				allAvailableCategoriesBeans);
		LOGGER.debug("List<CategoryBean> CategoryService.getAllAvailableCategories() --- END");
		return allAvailableCategoriesBeans;
	}
}
