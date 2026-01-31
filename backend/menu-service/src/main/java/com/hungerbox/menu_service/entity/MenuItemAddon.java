package com.hungerbox.menu_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "menu_item_addons")

public class MenuItemAddon extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long menuItemId;
	private String addonName;
	private BigDecimal extraPrice;

	public MenuItemAddon() {
		super();
	}

	public MenuItemAddon(Long id, Long menuItemId, String addonName, BigDecimal extraPrice) {
		super();
		this.id = id;
		this.menuItemId = menuItemId;
		this.addonName = addonName;
		this.extraPrice = extraPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}

	public String getAddonName() {
		return addonName;
	}

	public void setAddonName(String addonName) {
		this.addonName = addonName;
	}

	public BigDecimal getExtraPrice() {
		return extraPrice;
	}

	public void setExtraPrice(BigDecimal extraPrice) {
		this.extraPrice = extraPrice;
	}

}
