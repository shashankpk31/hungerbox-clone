package com.hungerbox.vendor_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.hungerbox.vendor_service.dto.request.OrganizationRequest;
import com.hungerbox.vendor_service.dto.response.OrganizationResponse;
import com.hungerbox.vendor_service.entity.Organization;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

	Organization toEntity(OrganizationRequest req);

	OrganizationResponse toResponse(Organization org);

	List<OrganizationResponse> toResponse(List<Organization> organizations);

}
