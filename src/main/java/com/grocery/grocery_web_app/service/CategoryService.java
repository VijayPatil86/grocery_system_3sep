package com.grocery.grocery_web_app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grocery.grocery_web_app.bean.BakeryProductBean;
import com.grocery.grocery_web_app.bean.CategoryBean;
import com.grocery.grocery_web_app.bean.DairyProductBean;
import com.grocery.grocery_web_app.entity.CategoryEntity;
import com.grocery.grocery_web_app.exception.CategoryNotAvailableException;
import com.grocery.grocery_web_app.exception.ProductForCategoryNotAvailableException;
import com.grocery.grocery_web_app.repository.CategoryRepository;

@Service
public class CategoryService {
	final static private Logger LOGGER = LogManager.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<CategoryBean> getAllAvailableCategories() {
		LOGGER.debug("CategoryService.getAllAvailableCategories() --- START");
		List<CategoryEntity> allAvailableCategoriesEntities = categoryRepository.getAllAvailableCategories();
		if(allAvailableCategoriesEntities.size() == 0) {
			LOGGER.info("CategoryService.getAllAvailableCategories() --- available Categories not available");
			throw new CategoryNotAvailableException(null);
		}
		LOGGER.info("List<CategoryBean> CategoryService.getAllAvailableCategories() --- available Categories entities are: " +
				allAvailableCategoriesEntities);
		List<CategoryBean> allAvailableCategoriesBeans = allAvailableCategoriesEntities.stream()
				.map(categoryEntity -> CategoryBean.builder()
						.categoryId(categoryEntity.getCategoryId())
						.categoryName(categoryEntity.getCategoryName())
						.build())
				.collect(Collectors.toList());
		LOGGER.info("CategoryService.getAllAvailableCategories() --- available Categories beans are: " +
				allAvailableCategoriesBeans);
		LOGGER.debug("CategoryService.getAllAvailableCategories() --- END");
		return allAvailableCategoriesBeans;
	}

	public CategoryBean getProductByCategoryId(int categoryId) {
		LOGGER.debug("CategoryService.getProductByCategoryId(" + categoryId + ") --- START");
		CategoryEntity categoryEntity = null;
		CategoryBean categoryBean = null;
		switch(categoryId) {
			case 1: {
				LOGGER.info("CategoryService.getProductByCategoryId(" + categoryId + ") --- retrieving dairy products, ");
				categoryEntity = categoryRepository.findDairyProductByCategoryId(categoryId);
				categoryBean = buildCategoryBeanFromDairyProductBean(categoryId, categoryEntity);
				break;
			}
			case 2: {
				LOGGER.info("CategoryService.getProductByCategoryId(" + categoryId + ") --- retrieving bakery products, ");
				categoryEntity = categoryRepository.findBakeryProductByCategoryId(categoryId);
				categoryBean = buildCategoryBeanFromBakeryProductBean(categoryId, categoryEntity);
				break;
			}
		}
		LOGGER.debug("CategoryService.getProductByCategoryId(" + categoryId + ") --- END");
		return categoryBean;
	}

	private CategoryBean buildCategoryBeanFromDairyProductBean(int categoryId, CategoryEntity categoryEntity) {
		LOGGER.debug("CategoryService.buildCategoryBeanFromDairyProductBean(" + categoryId + ") --- START");
		if(categoryEntity == null || categoryEntity.getDairyProductEntities() == null || categoryEntity.getDairyProductEntities().size() == 0) {
			LOGGER.debug("CategoryService.buildCategoryBeanFromDairyProductBean(" + categoryId + ") Dairy products for category " + categoryId + " not available");
			throw new ProductForCategoryNotAvailableException();
		}
		LOGGER.info("CategoryService.buildCategoryBeanFromDairyProductBean(" + categoryId + ") --- Dairy products entities are," + categoryEntity);
		List<DairyProductBean> diaryProductBeans = categoryEntity.getDairyProductEntities().stream()
				.map(dairyProductEntity -> DairyProductBean.builder()
						.dairyProductId(dairyProductEntity.getDairyProductId())
						.dairyProductName(dairyProductEntity.getDairyProductName())
						.build())
				.collect(Collectors.toList());
		LOGGER.info("CategoryService.buildCategoryBeanFromDairyProductBean(" + categoryId + ") --- Dairy products beans are," + diaryProductBeans);
		CategoryBean categoryBean = CategoryBean.builder()
				.categoryId(categoryEntity.getCategoryId())
				.categoryName(categoryEntity.getCategoryName())
				.dairyProductBeans(diaryProductBeans)
				.build();
		LOGGER.info("CategoryService.buildCategoryBeanFromDairyProductBean(" + categoryId + ") --- Category bean with Dairy product is," + categoryBean);
		LOGGER.debug("CategoryService.buildCategoryBeanFromDairyProductBean(" + categoryId + ") --- END");
		return categoryBean;
	}

	private CategoryBean buildCategoryBeanFromBakeryProductBean(int categoryId, CategoryEntity categoryEntity) {
		LOGGER.debug("CategoryService.buildCategoryBeanFromBakeryProductBean(" + categoryId + ") --- START");
		if(categoryEntity == null || categoryEntity.getBakeryProductEntities() == null || categoryEntity.getBakeryProductEntities().size() == 0) {
			LOGGER.debug("CategoryService.buildCategoryBeanFromBakeryProductBean(" + categoryId + ") Bakery products for category " + categoryId + " not available");
			throw new ProductForCategoryNotAvailableException();
		}
		LOGGER.info("CategoryService.buildCategoryBeanFromBakeryProductBean(" + categoryId + ") --- Bakery products entities are," + categoryEntity);
		List<BakeryProductBean> bakeryProductBeans = categoryEntity.getBakeryProductEntities().stream()
			.map(bakeryProductEntity -> BakeryProductBean.builder()
				.bakeryProductId(bakeryProductEntity.getBakeryProductId())
				.bakeryProductName(bakeryProductEntity.getBakeryProductName())
				.build())
			.collect(Collectors.toList());
		CategoryBean bakeryProductcategoryBean = CategoryBean.builder()
				.categoryId(categoryEntity.getCategoryId())
				.categoryName(categoryEntity.getCategoryName())
				.bakeryProductBeans(bakeryProductBeans)
				.build();
		LOGGER.info("CategoryService.buildCategoryBeanFromBakeryProductBean(" + categoryId + ") --- Category bean with Bakery product is," + bakeryProductcategoryBean);
		LOGGER.debug("CategoryService.buildCategoryBeanFromBakeryProductBean(" + categoryId + ") --- END");
		return bakeryProductcategoryBean;
	}
}
