package com.hungerbox.vendor_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.hungerbox.vendor_service.dto.request.VendorRequest;
import com.hungerbox.vendor_service.dto.response.VendorResponse;
import com.hungerbox.vendor_service.entity.Vendor;

@Mapper(componentModel = "spring")
public interface VendorMapper {

	Vendor toEntity(VendorRequest req);

	VendorResponse toResponse(Vendor vendor);

	List<VendorResponse> toResponse(List<Vendor> vendors);

}
