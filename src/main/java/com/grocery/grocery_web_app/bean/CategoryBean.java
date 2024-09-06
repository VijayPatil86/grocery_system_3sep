package com.grocery.grocery_web_app.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString
public class CategoryBean {
	private int categoryId;
	private String categoryName;
	private String categoryAvailable;
}
