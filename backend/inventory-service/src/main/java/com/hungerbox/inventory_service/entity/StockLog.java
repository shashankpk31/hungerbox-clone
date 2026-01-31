package com.hungerbox.inventory_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock_log")
public class StockLog extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long inventoryItemId;
	private Integer changeAmount;
	private String reason;

	public StockLog() {

	}

	public StockLog(Long id, Long inventoryItemId, Integer changeAmount, String reason) {
		super();
		this.id = id;
		this.inventoryItemId = inventoryItemId;
		this.changeAmount = changeAmount;
		this.reason = reason;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Integer changeAmount) {
		this.changeAmount = changeAmount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getInventoryItemId(){
		return this.inventoryItemId;
	}

	public void setInventoryItemId(Long id){
		this.inventoryItemId=id;
	}

}
