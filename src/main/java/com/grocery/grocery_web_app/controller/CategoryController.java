package com.grocery.grocery_web_app.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.grocery_web_app.bean.CategoryBean;
import com.grocery.grocery_web_app.exception.CategoryException;
import com.grocery.grocery_web_app.service.CategoryService;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {
	final static private Logger LOGGER = LogManager.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@GetMapping(path = "/categories")
	public ResponseEntity<List<CategoryBean>> getAllVisibleCategories() {
		LOGGER.debug("List<CategoryBean> CategoryController.getAllVisiblecategories() --- START");
		List<CategoryBean> allVisibleCategoriesBeans = categoryService.getAllVisibleCategories();
		LOGGER.info("List<CategoryBean> CategoryController.getAllVisiblecategories() --- visible Categories beans are: " +
				allVisibleCategoriesBeans);
		LOGGER.debug("List<CategoryBean> CategoryController.getAllVisiblecategories() --- END");
		return ResponseEntity.ok(allVisibleCategoriesBeans);
	}

	@ExceptionHandler(value = {CategoryException.class})
	public ResponseEntity<Void> handleAllVisibleCategoriesNotAvailableException() {
		LOGGER.debug("ResponseEntity<Void> CategoryController.handleAllVisibleCategoriesNotAvailableException() --- Returning No Content 204");
		return ResponseEntity.noContent().build();
	}
}
