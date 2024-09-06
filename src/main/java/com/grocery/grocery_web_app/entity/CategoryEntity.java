package com.grocery.grocery_web_app.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
@Entity
@Table(name = "CATEGORY")
public class CategoryEntity {
	@Column(name = "CATEGORY_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryId;

	@Column(name = "CATEGORY_NAME")
	private String categoryName;

	@Column(name = "CATEGORY_AVAILABLE")
	private String categoryAvailable;

	@OneToMany
	@JoinColumn(name = "CATEGORY_ID")
	private List<DairyProductEntity> dairyProductEntities;

	@OneToMany
	@JoinColumn(name = "CATEGORY_ID")
	private List<BakeryProductEntity> bakeryProductEntities;
}
