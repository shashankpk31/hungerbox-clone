package com.hungerbox.vendor_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.hungerbox.vendor_service.dto.request.OfficeRequest;
import com.hungerbox.vendor_service.dto.response.OfficeResponse;
import com.hungerbox.vendor_service.entity.Office;

@Mapper(componentModel = "spring")
public interface OfficeMapper {

	Office toEntity(OfficeRequest office);
	OfficeResponse toResponse(Office office);
	List<OfficeResponse> toResponse(List<Office> offices);

}
