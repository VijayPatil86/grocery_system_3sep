package com.grocery.grocery_web_app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {
	final static private Logger LOGGER = LogManager.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@GetMapping(path = "/categories")
	public ResponseEntity<CollectionModel<CategoryBean>> getAllAvailableCategories() {
		LOGGER.debug("/api/category --- START");
		LOGGER.debug("CategoryController.getAllAvailableCategories() --- START");
		List<CategoryBean> allAvailableCategoriesBeans = categoryService.getAllAvailableCategories();
		LOGGER.debug("CategoryController.getAllAvailableCategories() --- calling CategoryController.addHateosLinks()");
		CollectionModel <CategoryBean> collectionModel = addHateoasLinksToAllAvailableCategoriesBeans(allAvailableCategoriesBeans);
		LOGGER.info("List<CategoryBean> CategoryController.getAllAvailableCategories() --- available Categories beans are: " +
				collectionModel);
		LOGGER.debug("CategoryController.getAllAvailableCategories() --- END");
		LOGGER.debug("/api/category --- END");
		return ResponseEntity.ok(collectionModel);
	}

	@GetMapping(path = "/categories/{categoryId}")
	public ResponseEntity<Object> getProductByCategoryId(@PathVariable(name = "categoryId") int categoryId){
		LOGGER.debug("/api/category/" + categoryId + " --- START");
		LOGGER.debug("CategoryController.getProductByCategoryId(" + categoryId + ") --- START");
		LOGGER.info("CategoryController.getProductByCategoryId(" + categoryId + ") --- retrieving products, ");
		CategoryBean categoryBean = categoryService.getProductByCategoryId(categoryId);
		LOGGER.info("CategoryController.getProductByCategoryId(" + categoryId + ") --- products are, " + categoryBean);
		LOGGER.debug("CategoryController.getProductByCategoryId(" + categoryId + ") --- END");
		LOGGER.debug("/api/category/" + categoryId + " --- END");
		return ResponseEntity.ok(categoryBean);
	}

	private CollectionModel<CategoryBean> addHateoasLinksToAllAvailableCategoriesBeans(List<CategoryBean> allAvailableCategoriesBeans) {
		LOGGER.debug("CategoryController.addHateoasLinksToAllAvailableCategoriesBeans() --- START");
		allAvailableCategoriesBeans.stream()
			.map(categoryBean -> categoryBean.add(linkTo(methodOn(CategoryController.class).getProductByCategoryId(categoryBean.getCategoryId())).withRel("href_productByCategoryId")))
			.collect(Collectors.toList());
		CollectionModel<CategoryBean> collectionModel = CollectionModel.of(allAvailableCategoriesBeans);
		collectionModel.add(linkTo(methodOn(CategoryController.class).getAllAvailableCategories()).withSelfRel());
		LOGGER.info("CategoryController.addHateoasLinksToAllAvailableCategoriesBeans() --- allAvailableCategoriesBeans with HATEOAS links: " + collectionModel);
		LOGGER.debug("CategoryController.addHateoasLinksToAllAvailableCategoriesBeans() --- END");
		return collectionModel;
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
