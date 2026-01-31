package com.hungerbox.order_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long orderId;
	private Long menuItemId;
	private String addonIds;
	private Integer quantity;
	private BigDecimal itemPrice;
	private BigDecimal totalPrice;

	public OrderItem() {
		super();
	}

	public OrderItem(Long id, Long orderId, Long menuItemId, String addonIds, Integer quantity, BigDecimal itemPrice,
			BigDecimal totalPrice) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.menuItemId = menuItemId;
		this.addonIds = addonIds;
		this.quantity = quantity;
		this.itemPrice = itemPrice;
		this.totalPrice = totalPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}

	public String getAddonIds() {
		return addonIds;
	}

	public void setAddonIds(String addonIds) {
		this.addonIds = addonIds;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

}
