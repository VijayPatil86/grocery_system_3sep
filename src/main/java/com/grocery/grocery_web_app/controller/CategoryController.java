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
import com.grocery.grocery_web_app.exception.CategoryNotAvailableException;
import com.grocery.grocery_web_app.service.CategoryService;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {
	final static private Logger LOGGER = LogManager.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@GetMapping(path = "/categories")
	public ResponseEntity<List<CategoryBean>> getAllAvailableCategories() {
		LOGGER.debug("List<CategoryBean> CategoryController.getAllAvailableCategories() --- START");
		List<CategoryBean> allAvailableCategoriesBeans = categoryService.getAllAvailableCategories();
		LOGGER.info("List<CategoryBean> CategoryController.getAllAvailableCategories() --- available Categories beans are: " +
				allAvailableCategoriesBeans);
		LOGGER.debug("List<CategoryBean> CategoryController.getAllAvailableCategories() --- END");
		return ResponseEntity.ok(allAvailableCategoriesBeans);
	}

	@ExceptionHandler(value = {CategoryNotAvailableException.class})
	public ResponseEntity<Void> handleCategoryNotAvailableException() {
		LOGGER.debug("ResponseEntity<Void> CategoryController.handleCategoryNotAvailableException() --- Returning No Content 204");
		return ResponseEntity.noContent().build();
	}
}
