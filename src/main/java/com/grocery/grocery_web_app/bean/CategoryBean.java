package com.grocery.grocery_web_app.bean;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString
public class CategoryBean extends RepresentationModel<CategoryBean> {
	private int categoryId;
	private String categoryName;
	private List<DairyProductBean> dairyProductBeans;
	private List<BakeryProductBean> bakeryProductBeans;
}
