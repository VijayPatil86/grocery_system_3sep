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
@Table(name = "DAIRY_PRODUCT")
public class DairyProductEntity {
	@Column(name = "DAIRY_PRODUCT_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dairyProductId;

	@Column(name = "DAIRY_PRODUCT_NAME")
	private String dairyProductName;

	@Column(name = "DAIRY_PRODUCT_AVAILABLE")
	private String dairyProductAvailable;

}
