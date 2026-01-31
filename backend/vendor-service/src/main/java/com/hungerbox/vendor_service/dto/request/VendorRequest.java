package com.hungerbox.vendor_service.dto.request;

public record VendorRequest(Long cafeteriaId, String name, String stallNumber, String contactPerson, Long ownerUserId) {
}