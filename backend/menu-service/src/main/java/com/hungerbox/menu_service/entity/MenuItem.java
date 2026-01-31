package com.hungerbox.menu_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "menu_items")

public class MenuItem extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long vendorId;
	private String name;
	private BigDecimal price;
	private Boolean isAvailable;
	private Integer preparationTimeMinutes;
	private Integer calories;
	private Boolean isVeg;
	private Long categoryId;

	public MenuItem() {
		super();
	}

	public MenuItem(Long id, Long vendorId, String name, BigDecimal price, Boolean isAvailable,
			Integer preparationTimeMinutes, Integer calories, Boolean isVeg, Long categoryId) {
		super();
		this.id = id;
		this.vendorId = vendorId;
		this.name = name;
		this.price = price;
		this.isAvailable = isAvailable;
		this.preparationTimeMinutes = preparationTimeMinutes;
		this.calories = calories;
		this.isVeg = isVeg;
		this.categoryId = categoryId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Integer getPreparationTimeMinutes() {
		return preparationTimeMinutes;
	}

	public void setPreparationTimeMinutes(Integer preparationTimeMinutes) {
		this.preparationTimeMinutes = preparationTimeMinutes;
	}

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Boolean getIsVeg() {
		return isVeg;
	}

	public void setIsVeg(Boolean isVeg) {
		this.isVeg = isVeg;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}
