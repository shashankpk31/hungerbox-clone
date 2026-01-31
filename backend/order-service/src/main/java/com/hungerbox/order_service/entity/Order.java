package com.hungerbox.order_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private Long vendorId;
	private BigDecimal totalAmount;
	private String status;
	private String pickupOtp;
	private Long cafeteriaId;
	private Long officeId;

	public Order() {
		super();
	}

	public Order(Long id, Long userId, Long vendorId, BigDecimal totalAmount, String status, String pickupOtp,
			Long cafeteriaId, Long officeId) {
		super();
		this.id = id;
		this.userId = userId;
		this.vendorId = vendorId;
		this.totalAmount = totalAmount;
		this.status = status;
		this.pickupOtp = pickupOtp;
		this.cafeteriaId = cafeteriaId;
		this.officeId = officeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPickupOtp() {
		return pickupOtp;
	}

	public void setPickupOtp(String pickupOtp) {
		this.pickupOtp = pickupOtp;
	}

	public Long getCafeteriaId() {
		return cafeteriaId;
	}

	public void setCafeteriaId(Long cafeteriaId) {
		this.cafeteriaId = cafeteriaId;
	}

	public Long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}

}
