package com.grocery.grocery_web_app.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString
public class DairyProductBean {
	private int dairyProductId;
	private String dairyProductName;
}
