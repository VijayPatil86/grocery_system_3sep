package com.grocery.grocery_web_app.exception;

public class CategoryNotAvailableException extends RuntimeException {
	public CategoryNotAvailableException(String message) {
		super(message);
	}
}
