package com.hungerbox.vendor_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.hungerbox.vendor_service.dto.request.CafeteriaRequest;
import com.hungerbox.vendor_service.dto.response.CafeteriaResponse;
import com.hungerbox.vendor_service.entity.Cafeteria;

@Mapper(componentModel = "spring")
public interface CafeteriaMapper {
	
	Cafeteria toEntity(CafeteriaRequest cafeteria);

	CafeteriaResponse toResponse(Cafeteria cafeteria);

	List<CafeteriaResponse> toResponse(List<Cafeteria> cafeterias);
}
