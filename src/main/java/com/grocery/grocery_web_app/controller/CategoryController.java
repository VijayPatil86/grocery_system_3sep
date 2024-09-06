package com.grocery.grocery_web_app.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.grocery_web_app.bean.CategoryBean;
import com.grocery.grocery_web_app.exception.CategoryNotAvailableException;
import com.grocery.grocery_web_app.exception.ProductForCategoryNotAvailableException;
import com.grocery.grocery_web_app.service.CategoryService;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {
	final static private Logger LOGGER = LogManager.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@GetMapping(path = "/category")
	public ResponseEntity<List<CategoryBean>> getAllAvailableCategories() {
		LOGGER.debug("/api/category --- START");
		LOGGER.debug("CategoryController.getAllAvailableCategories() --- START");
		List<CategoryBean> allAvailableCategoriesBeans = categoryService.getAllAvailableCategories();
		LOGGER.info("List<CategoryBean> CategoryController.getAllAvailableCategories() --- available Categories beans are: " +
				allAvailableCategoriesBeans);
		LOGGER.debug("CategoryController.getAllAvailableCategories() --- END");
		LOGGER.debug("/api/category --- END");
		return ResponseEntity.ok(allAvailableCategoriesBeans);
	}

	@GetMapping(path = "/category/{categoryId}")
	public ResponseEntity<Object> getProductById(@PathVariable(name = "categoryId") int categoryId){
		LOGGER.debug("/api/category/" + categoryId + " --- START");
		LOGGER.debug("CategoryController.getProductById(" + categoryId + ") --- START");
		LOGGER.info("CategoryController.getProductById(" + categoryId + ") --- retrieving products, ");
		CategoryBean categoryBean = categoryService.getDairyProducts(categoryId);
		LOGGER.info("CategoryController.getProductById(" + categoryId + ") --- products are, " + categoryBean);
		LOGGER.debug("CategoryController.getProductById(" + categoryId + ") --- END");
		LOGGER.debug("/api/category/" + categoryId + " --- END");
		return ResponseEntity.ok(categoryBean);
	}

	@ExceptionHandler(value = {CategoryNotAvailableException.class})
	public ResponseEntity<Void> handleCategoryNotAvailableException() {
		LOGGER.debug("ResponseEntity<Void> CategoryController.handleCategoryNotAvailableException() --- Returning No Content 204");
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(value = {ProductForCategoryNotAvailableException.class})
	public ResponseEntity<Void> handleProductForcategoryNotAvailableException() {
		LOGGER.debug("ResponseEntity<Void> CategoryController.handleProductForcategoryNotAvailableException() --- Returning No Content 204");
		return ResponseEntity.noContent().build();
	}
}
