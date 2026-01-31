package com.hungerbox.vendor_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.hungerbox.vendor_service.dto.request.LocationRequest;
import com.hungerbox.vendor_service.dto.response.LocationResponse;
import com.hungerbox.vendor_service.entity.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

	Location toEntity(LocationRequest locationReq);

	LocationResponse toResponse(Location location);

	List<LocationResponse> toResponse(List<Location> locations);

}
