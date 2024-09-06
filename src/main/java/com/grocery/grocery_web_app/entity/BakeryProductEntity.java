package com.grocery.grocery_web_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
@Entity
@Table(name = "BAKERY_PRODUCT")
public class BakeryProductEntity {
	@Column(name = "BAKERY_PRODUCT_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bakeryProductId;

	@Column(name = "BAKERY_PRODUCT_NAME")
	private String bakeryProductName;

	@Column(name = "BAKERY_PRODUCT_AVAILABLE")
	private String bakeryProductAvailable;

}
