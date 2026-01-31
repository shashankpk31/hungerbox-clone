package com.hungerbox.vendor_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendors")

public class Vendor extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long cafeteriaId;
	private String name;
	private Boolean isActive;
	private String stallNumber;
	private String contactPerson;
	private Long ownerUserId;

	public Vendor() {
		super();
	}

	public Vendor(Long id, Long cafeteriaId, String name, Boolean isActive, String stallNumber, String contactPerson,
			Long ownerUserId) {
		super();
		this.id = id;
		this.cafeteriaId = cafeteriaId;
		this.name = name;
		this.isActive = isActive;
		this.stallNumber = stallNumber;
		this.contactPerson = contactPerson;
		this.ownerUserId = ownerUserId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCafeteriaId() {
		return cafeteriaId;
	}

	public void setCafeteriaId(Long cafeteriaId) {
		this.cafeteriaId = cafeteriaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getStallNumber() {
		return stallNumber;
	}

	public void setStallNumber(String stallNumber) {
		this.stallNumber = stallNumber;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public Long getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(Long ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

}
