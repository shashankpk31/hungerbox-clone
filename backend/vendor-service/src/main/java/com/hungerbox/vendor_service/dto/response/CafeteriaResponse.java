package com.hungerbox.vendor_service.dto.response;

public class CafeteriaResponse {
	private Long id;
	private String name;
	private Integer floorNumber;
	private Boolean isActive;

	public CafeteriaResponse() {
		super();
	}

	public CafeteriaResponse(Long id, String name, Integer floorNumber, Boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.floorNumber = floorNumber;
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}